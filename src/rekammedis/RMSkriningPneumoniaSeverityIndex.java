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
public final class RMSkriningPneumoniaSeverityIndex extends javax.swing.JDialog {
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
    public RMSkriningPneumoniaSeverityIndex(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Petugas","Nama Petugas","Tanggal","Umur","N.U",
            "Panti Jompo","N.P.J","Gagal Jantung","N.G.J","Penyakit Hati","N.P.H","Ginjal Kronis","N.G.K","Kanker","N.K",
            "Penyakit Serebrovaskular","N.P.S","Disorientasi Mental","N.D.M","Frekuensi Napas","N.F.N","TD Sistolik","N.T.S",
            "Suhu","N.S","Nadi","N.N","pH Darah","N.pH","Natrium","N.Na","BUN","N.B","PaO₂","N.P","Glukosa","N.G","Efusi Pleura","N.E.P",
            "Hematokrit","N.H","Total Skor","Kelas","Skor","Mortalitas","Rekomendasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 51; i++) {
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
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(35);
            }else if(i==9){
                column.setPreferredWidth(30);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(35);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(35);
            }else if(i==14){
                column.setPreferredWidth(73);
            }else if(i==15){
                column.setPreferredWidth(35);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(35);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(35);
            }else if(i==20){
                column.setPreferredWidth(130);
            }else if(i==21){
                column.setPreferredWidth(35);
            }else if(i==22){
                column.setPreferredWidth(102);
            }else if(i==23){
                column.setPreferredWidth(35);
            }else if(i==24){
                column.setPreferredWidth(90);
            }else if(i==25){
                column.setPreferredWidth(35);
            }else if(i==26){
                column.setPreferredWidth(65);
            }else if(i==27){
                column.setPreferredWidth(35);
            }else if(i==28){
                column.setPreferredWidth(55);
            }else if(i==29){
                column.setPreferredWidth(35);
            }else if(i==30){
                column.setPreferredWidth(55);
            }else if(i==31){
                column.setPreferredWidth(35);
            }else if(i==32){
                column.setPreferredWidth(55);
            }else if(i==33){
                column.setPreferredWidth(35);
            }else if(i==34){
                column.setPreferredWidth(55);
            }else if(i==35){
                column.setPreferredWidth(35);
            }else if(i==36){
                column.setPreferredWidth(55);
            }else if(i==37){
                column.setPreferredWidth(35);
            }else if(i==38){
                column.setPreferredWidth(55);
            }else if(i==39){
                column.setPreferredWidth(35);
            }else if(i==40){
                column.setPreferredWidth(55);
            }else if(i==41){
                column.setPreferredWidth(35);
            }else if(i==42){
                column.setPreferredWidth(65);
            }else if(i==43){
                column.setPreferredWidth(35);
            }else if(i==44){
                column.setPreferredWidth(65);
            }else if(i==45){
                column.setPreferredWidth(35);
            }else if(i==46){
                column.setPreferredWidth(58);
            }else if(i==47){
                column.setPreferredWidth(37);
            }else if(i==48){
                column.setPreferredWidth(140);
            }else if(i==49){
                column.setPreferredWidth(57);
            }else if(i==50){
                column.setPreferredWidth(120);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        SkorInterpretasi.setDocument(new batasInput((byte)40).getKata(SkorInterpretasi));
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
        MnSkriningPSI = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
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
        jSeparator1 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        Jk = new widget.TextBox();
        Kelas = new widget.TextBox();
        jLabel22 = new widget.Label();
        jLabel93 = new widget.Label();
        SkorInterpretasi = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel5 = new widget.Label();
        jLabel27 = new widget.Label();
        TinggalDiPanti = new widget.ComboBox();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel11 = new widget.Label();
        Umur = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel10 = new widget.Label();
        jLabel13 = new widget.Label();
        NilaiUmur = new widget.TextBox();
        jLabel14 = new widget.Label();
        NilaiTinggalDiPanti = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel102 = new widget.Label();
        jLabel17 = new widget.Label();
        GagalJantung = new widget.ComboBox();
        jLabel15 = new widget.Label();
        jLabel20 = new widget.Label();
        NilaiGagalJantung = new widget.TextBox();
        jLabel23 = new widget.Label();
        PenyakitHati = new widget.ComboBox();
        jLabel24 = new widget.Label();
        NilaiPenyakitHati = new widget.TextBox();
        jLabel26 = new widget.Label();
        GinjalKronis = new widget.ComboBox();
        jLabel25 = new widget.Label();
        jLabel28 = new widget.Label();
        NilaiGinjalKronis = new widget.TextBox();
        jLabel29 = new widget.Label();
        KankerNeoplasma = new widget.ComboBox();
        jLabel30 = new widget.Label();
        NilaiKankerNeoplasma = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        PenyakitSerebrovaskular = new widget.ComboBox();
        jLabel33 = new widget.Label();
        NilaiPenyakitSerebrovaskular = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel103 = new widget.Label();
        jLabel35 = new widget.Label();
        DisorentasiMental = new widget.ComboBox();
        jLabel34 = new widget.Label();
        jLabel36 = new widget.Label();
        NilaiDisorentasiMental = new widget.TextBox();
        jLabel37 = new widget.Label();
        FrekuensiNapas = new widget.ComboBox();
        jLabel38 = new widget.Label();
        NilaiFrekuensiNapas = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        TDSistolik = new widget.ComboBox();
        jLabel41 = new widget.Label();
        NilaiTDSistolik = new widget.TextBox();
        jLabel42 = new widget.Label();
        Suhu = new widget.ComboBox();
        jLabel43 = new widget.Label();
        NilaiSuhu = new widget.TextBox();
        jLabel45 = new widget.Label();
        Nadi = new widget.ComboBox();
        jLabel44 = new widget.Label();
        jLabel46 = new widget.Label();
        NilaiNadi = new widget.TextBox();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel104 = new widget.Label();
        jLabel48 = new widget.Label();
        pHDarah = new widget.ComboBox();
        jLabel47 = new widget.Label();
        jLabel49 = new widget.Label();
        NilaipHDarah = new widget.TextBox();
        jLabel50 = new widget.Label();
        Natrium = new widget.ComboBox();
        jLabel51 = new widget.Label();
        NilaiNatrium = new widget.TextBox();
        jLabel53 = new widget.Label();
        BUN = new widget.ComboBox();
        jLabel52 = new widget.Label();
        jLabel54 = new widget.Label();
        NilaiBUN = new widget.TextBox();
        jLabel55 = new widget.Label();
        PaO = new widget.ComboBox();
        jLabel56 = new widget.Label();
        NilaiPaO = new widget.TextBox();
        jLabel58 = new widget.Label();
        Glukosa = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel59 = new widget.Label();
        NilaiGlukosa = new widget.TextBox();
        jLabel60 = new widget.Label();
        EfusiPleura = new widget.ComboBox();
        jLabel61 = new widget.Label();
        NilaiEfusiPleura = new widget.TextBox();
        jLabel63 = new widget.Label();
        Hematokrit = new widget.ComboBox();
        jLabel62 = new widget.Label();
        jLabel64 = new widget.Label();
        NilaiHematokrit = new widget.TextBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel105 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        TotalSkorPSI = new widget.TextBox();
        jLabel67 = new widget.Label();
        Mortalitas = new widget.TextBox();
        jLabel94 = new widget.Label();
        jLabel95 = new widget.Label();
        Rekomendasi = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningPSI.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningPSI.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningPSI.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningPSI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningPSI.setText("Formulir Skrining PSI");
        MnSkriningPSI.setName("MnSkriningPSI"); // NOI18N
        MnSkriningPSI.setPreferredSize(new java.awt.Dimension(200, 26));
        MnSkriningPSI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningPSIActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningPSI);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Pneumonia Severity Index ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(462, 1300));
        internalFrame1.setRequestFocusEnabled(false);
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-10-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-10-2025" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 446));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 583));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-10-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(80, 40, 90, 23);

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

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("I. DEMOGRAFI");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 70, 490, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("VI. HASIL SKRINING/INTERPRETASI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 530, 380, 23);

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(67, 90, 50, 23);

        Kelas.setEditable(false);
        Kelas.setFocusTraversalPolicyProvider(true);
        Kelas.setName("Kelas"); // NOI18N
        FormInput.add(Kelas);
        Kelas.setBounds(81, 550, 40, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Kelas");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(44, 550, 40, 23);

        jLabel93.setText("Skor :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(130, 550, 50, 23);

        SkorInterpretasi.setEditable(false);
        SkorInterpretasi.setFocusTraversalPolicyProvider(true);
        SkorInterpretasi.setName("SkorInterpretasi"); // NOI18N
        FormInput.add(SkorInterpretasi);
        SkorInterpretasi.setBounds(184, 550, 100, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("JK");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(44, 90, 40, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 90, 63, 23);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 550, 77, 23);

        TinggalDiPanti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        TinggalDiPanti.setName("TinggalDiPanti"); // NOI18N
        TinggalDiPanti.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TinggalDiPantiItemStateChanged(evt);
            }
        });
        TinggalDiPanti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalDiPantiKeyPressed(evt);
            }
        });
        FormInput.add(TinggalDiPanti);
        TinggalDiPanti.setBounds(605, 90, 90, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 530, 807, 1);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel11.setText("Tinggal Di Panti Jompo :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(451, 90, 150, 23);

        Umur.setEditable(false);
        Umur.setFocusTraversalPolicyProvider(true);
        Umur.setName("Umur"); // NOI18N
        FormInput.add(Umur);
        Umur.setBounds(178, 90, 50, 23);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("poin");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(583, 500, 50, 23);

        jLabel10.setText("Umur :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(124, 90, 50, 23);

        jLabel13.setText("Nilai :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(262, 90, 50, 23);

        NilaiUmur.setEditable(false);
        NilaiUmur.setFocusTraversalPolicyProvider(true);
        NilaiUmur.setName("NilaiUmur"); // NOI18N
        FormInput.add(NilaiUmur);
        NilaiUmur.setBounds(316, 90, 40, 23);

        jLabel14.setText("Nilai :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(695, 90, 50, 23);

        NilaiTinggalDiPanti.setEditable(false);
        NilaiTinggalDiPanti.setFocusTraversalPolicyProvider(true);
        NilaiTinggalDiPanti.setName("NilaiTinggalDiPanti"); // NOI18N
        FormInput.add(NilaiTinggalDiPanti);
        NilaiTinggalDiPanti.setBounds(749, 90, 40, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 120, 807, 1);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("II. RIWAYAT PENYAKIT");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 120, 490, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 140, 170, 23);

        GagalJantung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        GagalJantung.setName("GagalJantung"); // NOI18N
        GagalJantung.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GagalJantungItemStateChanged(evt);
            }
        });
        GagalJantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GagalJantungKeyPressed(evt);
            }
        });
        FormInput.add(GagalJantung);
        GagalJantung.setBounds(174, 140, 100, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Gagal Jantung Kongestif");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(44, 140, 150, 23);

        jLabel20.setText("Nilai :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(276, 140, 50, 23);

        NilaiGagalJantung.setEditable(false);
        NilaiGagalJantung.setFocusTraversalPolicyProvider(true);
        NilaiGagalJantung.setName("NilaiGagalJantung"); // NOI18N
        FormInput.add(NilaiGagalJantung);
        NilaiGagalJantung.setBounds(330, 140, 40, 23);

        jLabel23.setText("Penyakit Hati :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(441, 140, 150, 23);

        PenyakitHati.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        PenyakitHati.setName("PenyakitHati"); // NOI18N
        PenyakitHati.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PenyakitHatiItemStateChanged(evt);
            }
        });
        PenyakitHati.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitHatiKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitHati);
        PenyakitHati.setBounds(595, 140, 100, 23);

        jLabel24.setText("Nilai :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(695, 140, 50, 23);

        NilaiPenyakitHati.setEditable(false);
        NilaiPenyakitHati.setFocusTraversalPolicyProvider(true);
        NilaiPenyakitHati.setName("NilaiPenyakitHati"); // NOI18N
        FormInput.add(NilaiPenyakitHati);
        NilaiPenyakitHati.setBounds(749, 140, 40, 23);

        jLabel26.setText(":");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 170, 156, 23);

        GinjalKronis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        GinjalKronis.setName("GinjalKronis"); // NOI18N
        GinjalKronis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GinjalKronisItemStateChanged(evt);
            }
        });
        GinjalKronis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GinjalKronisKeyPressed(evt);
            }
        });
        FormInput.add(GinjalKronis);
        GinjalKronis.setBounds(160, 170, 100, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Penyakit Ginjal Kronis");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(44, 170, 150, 23);

        jLabel28.setText("Nilai :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(262, 170, 50, 23);

        NilaiGinjalKronis.setEditable(false);
        NilaiGinjalKronis.setFocusTraversalPolicyProvider(true);
        NilaiGinjalKronis.setName("NilaiGinjalKronis"); // NOI18N
        FormInput.add(NilaiGinjalKronis);
        NilaiGinjalKronis.setBounds(316, 170, 40, 23);

        jLabel29.setText("Kanker (Neoplasma) :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(441, 170, 150, 23);

        KankerNeoplasma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        KankerNeoplasma.setName("KankerNeoplasma"); // NOI18N
        KankerNeoplasma.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KankerNeoplasmaItemStateChanged(evt);
            }
        });
        KankerNeoplasma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KankerNeoplasmaKeyPressed(evt);
            }
        });
        FormInput.add(KankerNeoplasma);
        KankerNeoplasma.setBounds(595, 170, 100, 23);

        jLabel30.setText("Nilai :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(695, 170, 50, 23);

        NilaiKankerNeoplasma.setEditable(false);
        NilaiKankerNeoplasma.setFocusTraversalPolicyProvider(true);
        NilaiKankerNeoplasma.setName("NilaiKankerNeoplasma"); // NOI18N
        FormInput.add(NilaiKankerNeoplasma);
        NilaiKankerNeoplasma.setBounds(749, 170, 40, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Penyakit Serebrovaskular");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(44, 200, 150, 23);

        jLabel32.setText(":");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 200, 173, 23);

        PenyakitSerebrovaskular.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        PenyakitSerebrovaskular.setName("PenyakitSerebrovaskular"); // NOI18N
        PenyakitSerebrovaskular.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PenyakitSerebrovaskularItemStateChanged(evt);
            }
        });
        PenyakitSerebrovaskular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitSerebrovaskularKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitSerebrovaskular);
        PenyakitSerebrovaskular.setBounds(177, 200, 100, 23);

        jLabel33.setText("Nilai :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(279, 200, 50, 23);

        NilaiPenyakitSerebrovaskular.setEditable(false);
        NilaiPenyakitSerebrovaskular.setFocusTraversalPolicyProvider(true);
        NilaiPenyakitSerebrovaskular.setName("NilaiPenyakitSerebrovaskular"); // NOI18N
        FormInput.add(NilaiPenyakitSerebrovaskular);
        NilaiPenyakitSerebrovaskular.setBounds(333, 200, 40, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 230, 807, 1);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("III. PEMERIKSAAN FISIK");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 230, 490, 23);

        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 250, 145, 23);

        DisorentasiMental.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        DisorentasiMental.setName("DisorentasiMental"); // NOI18N
        DisorentasiMental.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DisorentasiMentalItemStateChanged(evt);
            }
        });
        DisorentasiMental.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DisorentasiMentalKeyPressed(evt);
            }
        });
        FormInput.add(DisorentasiMental);
        DisorentasiMental.setBounds(149, 250, 90, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Disorientasi Mental");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(44, 250, 150, 23);

        jLabel36.setText("Nilai :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(241, 250, 50, 23);

        NilaiDisorentasiMental.setEditable(false);
        NilaiDisorentasiMental.setFocusTraversalPolicyProvider(true);
        NilaiDisorentasiMental.setName("NilaiDisorentasiMental"); // NOI18N
        FormInput.add(NilaiDisorentasiMental);
        NilaiDisorentasiMental.setBounds(295, 250, 40, 23);

        jLabel37.setText("Frekuensi Napas ≥ 30 /menit :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(451, 250, 150, 23);

        FrekuensiNapas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FrekuensiNapas.setName("FrekuensiNapas"); // NOI18N
        FrekuensiNapas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FrekuensiNapasItemStateChanged(evt);
            }
        });
        FrekuensiNapas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiNapasKeyPressed(evt);
            }
        });
        FormInput.add(FrekuensiNapas);
        FrekuensiNapas.setBounds(605, 250, 90, 23);

        jLabel38.setText("Nilai :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(695, 250, 50, 23);

        NilaiFrekuensiNapas.setEditable(false);
        NilaiFrekuensiNapas.setFocusTraversalPolicyProvider(true);
        NilaiFrekuensiNapas.setName("NilaiFrekuensiNapas"); // NOI18N
        FormInput.add(NilaiFrekuensiNapas);
        NilaiFrekuensiNapas.setBounds(749, 250, 40, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("TD Sistolik < 90 mmHg");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(44, 280, 150, 23);

        jLabel40.setText(":");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 280, 162, 23);

        TDSistolik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        TDSistolik.setName("TDSistolik"); // NOI18N
        TDSistolik.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TDSistolikItemStateChanged(evt);
            }
        });
        TDSistolik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDSistolikKeyPressed(evt);
            }
        });
        FormInput.add(TDSistolik);
        TDSistolik.setBounds(166, 280, 90, 23);

        jLabel41.setText("Nilai :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(258, 280, 50, 23);

        NilaiTDSistolik.setEditable(false);
        NilaiTDSistolik.setFocusTraversalPolicyProvider(true);
        NilaiTDSistolik.setName("NilaiTDSistolik"); // NOI18N
        FormInput.add(NilaiTDSistolik);
        NilaiTDSistolik.setBounds(312, 280, 40, 23);

        jLabel42.setText("Suhu < 35°C Atau ≥ 40°C :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(451, 280, 150, 23);

        Suhu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SuhuItemStateChanged(evt);
            }
        });
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(605, 280, 90, 23);

        jLabel43.setText("Nilai :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(695, 280, 50, 23);

        NilaiSuhu.setEditable(false);
        NilaiSuhu.setFocusTraversalPolicyProvider(true);
        NilaiSuhu.setName("NilaiSuhu"); // NOI18N
        FormInput.add(NilaiSuhu);
        NilaiSuhu.setBounds(749, 280, 40, 23);

        jLabel45.setText(":");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 310, 140, 23);

        Nadi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NadiItemStateChanged(evt);
            }
        });
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(144, 310, 90, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Nadi ≥ 125 /menit");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(44, 310, 150, 23);

        jLabel46.setText("Nilai :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(236, 310, 50, 23);

        NilaiNadi.setEditable(false);
        NilaiNadi.setFocusTraversalPolicyProvider(true);
        NilaiNadi.setName("NilaiNadi"); // NOI18N
        FormInput.add(NilaiNadi);
        NilaiNadi.setBounds(290, 310, 40, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 340, 807, 1);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("IV. LABORATORIUM & RADIOLOGI");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(10, 340, 490, 23);

        jLabel48.setText(":");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 360, 131, 23);

        pHDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        pHDarah.setName("pHDarah"); // NOI18N
        pHDarah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pHDarahItemStateChanged(evt);
            }
        });
        pHDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pHDarahKeyPressed(evt);
            }
        });
        FormInput.add(pHDarah);
        pHDarah.setBounds(135, 360, 90, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("pH Darah < 7.35");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(44, 360, 110, 23);

        jLabel49.setText("Nilai :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(227, 360, 50, 23);

        NilaipHDarah.setEditable(false);
        NilaipHDarah.setFocusTraversalPolicyProvider(true);
        NilaipHDarah.setName("NilaipHDarah"); // NOI18N
        FormInput.add(NilaipHDarah);
        NilaipHDarah.setBounds(281, 360, 40, 23);

        jLabel50.setText("Natrium < 130 mEq/L :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(451, 360, 150, 23);

        Natrium.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Natrium.setName("Natrium"); // NOI18N
        Natrium.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NatriumItemStateChanged(evt);
            }
        });
        Natrium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NatriumKeyPressed(evt);
            }
        });
        FormInput.add(Natrium);
        Natrium.setBounds(605, 360, 90, 23);

        jLabel51.setText("Nilai :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(695, 360, 50, 23);

        NilaiNatrium.setEditable(false);
        NilaiNatrium.setFocusTraversalPolicyProvider(true);
        NilaiNatrium.setName("NilaiNatrium"); // NOI18N
        FormInput.add(NilaiNatrium);
        NilaiNatrium.setBounds(749, 360, 40, 23);

        jLabel53.setText(":");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 390, 131, 23);

        BUN.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BUN.setName("BUN"); // NOI18N
        BUN.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BUNItemStateChanged(evt);
            }
        });
        BUN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BUNKeyPressed(evt);
            }
        });
        FormInput.add(BUN);
        BUN.setBounds(135, 390, 90, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("BUN ≥ 30 mg/dL");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(44, 390, 150, 23);

        jLabel54.setText("Nilai :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(227, 390, 50, 23);

        NilaiBUN.setEditable(false);
        NilaiBUN.setFocusTraversalPolicyProvider(true);
        NilaiBUN.setName("NilaiBUN"); // NOI18N
        FormInput.add(NilaiBUN);
        NilaiBUN.setBounds(281, 390, 40, 23);

        jLabel55.setText("PaO₂ < 60 mmHg / SaO₂ < 90% :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(371, 390, 230, 23);

        PaO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PaO.setName("PaO"); // NOI18N
        PaO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PaOItemStateChanged(evt);
            }
        });
        PaO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PaOKeyPressed(evt);
            }
        });
        FormInput.add(PaO);
        PaO.setBounds(605, 390, 90, 23);

        jLabel56.setText("Nilai :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(695, 390, 50, 23);

        NilaiPaO.setEditable(false);
        NilaiPaO.setFocusTraversalPolicyProvider(true);
        NilaiPaO.setName("NilaiPaO"); // NOI18N
        FormInput.add(NilaiPaO);
        NilaiPaO.setBounds(749, 390, 40, 23);

        jLabel58.setText(":");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 420, 156, 23);

        Glukosa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Glukosa.setName("Glukosa"); // NOI18N
        Glukosa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GlukosaItemStateChanged(evt);
            }
        });
        Glukosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GlukosaKeyPressed(evt);
            }
        });
        FormInput.add(Glukosa);
        Glukosa.setBounds(160, 420, 90, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Glukosa ≥ 250 mg/dL");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(44, 420, 150, 23);

        jLabel59.setText("Nilai :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(252, 420, 50, 23);

        NilaiGlukosa.setEditable(false);
        NilaiGlukosa.setFocusTraversalPolicyProvider(true);
        NilaiGlukosa.setName("NilaiGlukosa"); // NOI18N
        FormInput.add(NilaiGlukosa);
        NilaiGlukosa.setBounds(306, 420, 40, 23);

        jLabel60.setText("Efusi Pleura (Foto Toraks) :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(431, 420, 170, 23);

        EfusiPleura.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        EfusiPleura.setName("EfusiPleura"); // NOI18N
        EfusiPleura.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EfusiPleuraItemStateChanged(evt);
            }
        });
        EfusiPleura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EfusiPleuraKeyPressed(evt);
            }
        });
        FormInput.add(EfusiPleura);
        EfusiPleura.setBounds(605, 420, 90, 23);

        jLabel61.setText("Nilai :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(695, 420, 50, 23);

        NilaiEfusiPleura.setEditable(false);
        NilaiEfusiPleura.setFocusTraversalPolicyProvider(true);
        NilaiEfusiPleura.setName("NilaiEfusiPleura"); // NOI18N
        FormInput.add(NilaiEfusiPleura);
        NilaiEfusiPleura.setBounds(749, 420, 40, 23);

        jLabel63.setText(":");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 450, 143, 23);

        Hematokrit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Hematokrit.setName("Hematokrit"); // NOI18N
        Hematokrit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HematokritItemStateChanged(evt);
            }
        });
        Hematokrit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HematokritKeyPressed(evt);
            }
        });
        FormInput.add(Hematokrit);
        Hematokrit.setBounds(147, 450, 90, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Hematokrit < 30%");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(44, 450, 150, 23);

        jLabel64.setText("Nilai :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(239, 450, 50, 23);

        NilaiHematokrit.setEditable(false);
        NilaiHematokrit.setFocusTraversalPolicyProvider(true);
        NilaiHematokrit.setName("NilaiHematokrit"); // NOI18N
        FormInput.add(NilaiHematokrit);
        NilaiHematokrit.setBounds(293, 450, 40, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 480, 807, 1);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("V. TOTAL SKOR PNEUMONIA SEVERITY INDEX");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(10, 480, 490, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Total Skor = Demografi + Riwayat Penyakit + Pemeriksaan Fisik + Laboratorium & Radiologi");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(44, 500, 480, 23);

        jLabel66.setText("=");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 500, 506, 23);

        TotalSkorPSI.setEditable(false);
        TotalSkorPSI.setFocusTraversalPolicyProvider(true);
        TotalSkorPSI.setName("TotalSkorPSI"); // NOI18N
        FormInput.add(TotalSkorPSI);
        TotalSkorPSI.setBounds(510, 500, 70, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Tahun");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(231, 90, 50, 23);

        Mortalitas.setEditable(false);
        Mortalitas.setFocusTraversalPolicyProvider(true);
        Mortalitas.setName("Mortalitas"); // NOI18N
        FormInput.add(Mortalitas);
        Mortalitas.setBounds(376, 550, 75, 23);

        jLabel94.setText("Mortalitas :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(302, 550, 70, 23);

        jLabel95.setText("Rekomendasi :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(464, 550, 90, 23);

        Rekomendasi.setEditable(false);
        Rekomendasi.setFocusTraversalPolicyProvider(true);
        Rekomendasi.setName("Rekomendasi"); // NOI18N
        FormInput.add(Rekomendasi);
        Rekomendasi.setBounds(558, 550, 231, 23);

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
        }else if(Rekomendasi.getText().trim().equals("")){
            Valid.textKosong(Rekomendasi,"Rekomendasi");
        }else if(TotalSkorPSI.getText().trim().equals("")){
            Valid.textKosong(TotalSkorPSI,"Total Skor");
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
            Valid.pindah(evt,Hematokrit,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
        TotalNilai();
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
        }else if(Rekomendasi.getText().trim().equals("")){
            Valid.textKosong(Rekomendasi,"Rekomendasi");
        }else if(TotalSkorPSI.getText().trim().equals("")){
            Valid.textKosong(TotalSkorPSI,"Total Skor");
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.U</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Panti Jompo</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.J</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Jantung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.G.J</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakit Hati</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.H</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ginjal Kronis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.G.K</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kanker</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakit Serebrovaskular</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.S</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Disorientasi Mental</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.D.M</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Frekuensi Napas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.F.N</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TD Sistolik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.T.S</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.S</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.N</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>pH Darah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.pH</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Natrium</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.Na</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BUN</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.B</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PaO₂</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Glukosa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.G</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Efusi Pleura</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.E.P</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hematokrit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.H</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total Skor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mortalitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rekomendasi</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,50).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataSkriningPSI.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA SEKRINING PNEUMONIA SEVERITY INDEX<br><br></font>"+        
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

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Detik,Jk);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningPSIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningPSIActionPerformed
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
            Valid.MyReportqry("rptFormulirSkriningPSI.jasper","report","::[ Formulir Skrining Pneumonia Severity Index ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_pneumonia_severity_index.nip,petugas.nama,skrining_pneumonia_severity_index.tanggal,"+
                    "if(reg_periksa.sttsumur='Th',reg_periksa.umurdaftar,'0') as umur,skrining_pneumonia_severity_index.nilai_umur,skrining_pneumonia_severity_index.tinggal_di_panti_jompo,skrining_pneumonia_severity_index.nilai_tinggal_di_panti_jompo,"+
                    "skrining_pneumonia_severity_index.gagal_jantung,skrining_pneumonia_severity_index.nilai_gagal_jantung,skrining_pneumonia_severity_index.penyakit_hati,skrining_pneumonia_severity_index.nilai_penyakit_hati,skrining_pneumonia_severity_index.penyakit_ginjal,"+
                    "skrining_pneumonia_severity_index.nilai_penyakit_ginjal,skrining_pneumonia_severity_index.kanker,skrining_pneumonia_severity_index.nilai_kanker,skrining_pneumonia_severity_index.penyakit_serebrovaskuler,skrining_pneumonia_severity_index.nilai_penyakit_serebrovaskuler,"+
                    "skrining_pneumonia_severity_index.disorentasi_mental,skrining_pneumonia_severity_index.nilai_disorentasi_mental,skrining_pneumonia_severity_index.frekuensi_napas,skrining_pneumonia_severity_index.nilai_frekuensi_napas,skrining_pneumonia_severity_index.td_sistolik,"+
                    "skrining_pneumonia_severity_index.nilai_td_sistolik,skrining_pneumonia_severity_index.suhu,skrining_pneumonia_severity_index.nilai_suhu,skrining_pneumonia_severity_index.nadi,skrining_pneumonia_severity_index.nilai_nadi,skrining_pneumonia_severity_index.ph_darah,"+
                    "skrining_pneumonia_severity_index.nilai_ph_darah,skrining_pneumonia_severity_index.natrium,skrining_pneumonia_severity_index.nilai_natrium,skrining_pneumonia_severity_index.bun,skrining_pneumonia_severity_index.nilai_bun,skrining_pneumonia_severity_index.pao,"+
                    "skrining_pneumonia_severity_index.nilai_pao,skrining_pneumonia_severity_index.glukosa,skrining_pneumonia_severity_index.nilai_glukosa,skrining_pneumonia_severity_index.efusi_pleura,skrining_pneumonia_severity_index.nilai_efusi_pleura,skrining_pneumonia_severity_index.hematokrit,"+
                    "skrining_pneumonia_severity_index.nilai_hematokrit,skrining_pneumonia_severity_index.total_skor,skrining_pneumonia_severity_index.kelas,skrining_pneumonia_severity_index.skor_interpretasi,skrining_pneumonia_severity_index.mortalitas,skrining_pneumonia_severity_index.rekomendasi "+
                    "from skrining_pneumonia_severity_index inner join reg_periksa on skrining_pneumonia_severity_index.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_pneumonia_severity_index.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningPSIActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TinggalDiPantiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalDiPantiKeyPressed
        Valid.pindah(evt,btnPetugas,GagalJantung);
    }//GEN-LAST:event_TinggalDiPantiKeyPressed

    private void GagalJantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GagalJantungKeyPressed
        Valid.pindah(evt,TinggalDiPanti,PenyakitHati);
    }//GEN-LAST:event_GagalJantungKeyPressed

    private void PenyakitHatiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitHatiKeyPressed
        Valid.pindah(evt,GagalJantung,GinjalKronis);
    }//GEN-LAST:event_PenyakitHatiKeyPressed

    private void GinjalKronisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GinjalKronisKeyPressed
        Valid.pindah(evt,PenyakitHati,KankerNeoplasma);
    }//GEN-LAST:event_GinjalKronisKeyPressed

    private void KankerNeoplasmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KankerNeoplasmaKeyPressed
        Valid.pindah(evt,GinjalKronis,PenyakitSerebrovaskular);
    }//GEN-LAST:event_KankerNeoplasmaKeyPressed

    private void PenyakitSerebrovaskularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitSerebrovaskularKeyPressed
        Valid.pindah(evt,KankerNeoplasma,DisorentasiMental);
    }//GEN-LAST:event_PenyakitSerebrovaskularKeyPressed

    private void DisorentasiMentalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DisorentasiMentalKeyPressed
        Valid.pindah(evt,PenyakitSerebrovaskular,FrekuensiNapas);
    }//GEN-LAST:event_DisorentasiMentalKeyPressed

    private void FrekuensiNapasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiNapasKeyPressed
        Valid.pindah(evt,DisorentasiMental,TDSistolik);
    }//GEN-LAST:event_FrekuensiNapasKeyPressed

    private void TDSistolikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDSistolikKeyPressed
        Valid.pindah(evt,FrekuensiNapas,Suhu);
    }//GEN-LAST:event_TDSistolikKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,TDSistolik,Nadi);
    }//GEN-LAST:event_SuhuKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,Suhu,pHDarah);
    }//GEN-LAST:event_NadiKeyPressed

    private void pHDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pHDarahKeyPressed
        Valid.pindah(evt,Nadi,Natrium);
    }//GEN-LAST:event_pHDarahKeyPressed

    private void NatriumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NatriumKeyPressed
        Valid.pindah(evt,pHDarah,BUN);
    }//GEN-LAST:event_NatriumKeyPressed

    private void BUNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BUNKeyPressed
        Valid.pindah(evt,Natrium,PaO);
    }//GEN-LAST:event_BUNKeyPressed

    private void PaOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PaOKeyPressed
        Valid.pindah(evt,BUN,Glukosa);
    }//GEN-LAST:event_PaOKeyPressed

    private void GlukosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GlukosaKeyPressed
        Valid.pindah(evt,PaO,EfusiPleura);
    }//GEN-LAST:event_GlukosaKeyPressed

    private void EfusiPleuraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EfusiPleuraKeyPressed
        Valid.pindah(evt,Glukosa,Hematokrit);
    }//GEN-LAST:event_EfusiPleuraKeyPressed

    private void HematokritKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HematokritKeyPressed
        Valid.pindah(evt,EfusiPleura,BtnSimpan);
    }//GEN-LAST:event_HematokritKeyPressed

    private void TinggalDiPantiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TinggalDiPantiItemStateChanged
        if(TinggalDiPanti.getSelectedIndex()==0){
            NilaiTinggalDiPanti.setText("0");
        }else{
            NilaiTinggalDiPanti.setText("10");
        }
        TotalNilai();
    }//GEN-LAST:event_TinggalDiPantiItemStateChanged

    private void GagalJantungItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GagalJantungItemStateChanged
        if(GagalJantung.getSelectedIndex()==0){
            NilaiGagalJantung.setText("0");
        }else{
            NilaiGagalJantung.setText("10");
        }
        TotalNilai();
    }//GEN-LAST:event_GagalJantungItemStateChanged

    private void GinjalKronisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GinjalKronisItemStateChanged
        if(GinjalKronis.getSelectedIndex()==0){
            NilaiGinjalKronis.setText("0");
        }else{
            NilaiGinjalKronis.setText("10");
        }
        TotalNilai();
    }//GEN-LAST:event_GinjalKronisItemStateChanged

    private void PenyakitSerebrovaskularItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PenyakitSerebrovaskularItemStateChanged
        if(PenyakitSerebrovaskular.getSelectedIndex()==0){
            NilaiPenyakitSerebrovaskular.setText("0");
        }else{
            NilaiPenyakitSerebrovaskular.setText("10");
        }
        TotalNilai();
    }//GEN-LAST:event_PenyakitSerebrovaskularItemStateChanged

    private void PenyakitHatiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PenyakitHatiItemStateChanged
        if(PenyakitHati.getSelectedIndex()==0){
            NilaiPenyakitHati.setText("0");
        }else{
            NilaiPenyakitHati.setText("20");
        }
        TotalNilai();
    }//GEN-LAST:event_PenyakitHatiItemStateChanged

    private void KankerNeoplasmaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KankerNeoplasmaItemStateChanged
        if(KankerNeoplasma.getSelectedIndex()==0){
            NilaiKankerNeoplasma.setText("0");
        }else{
            NilaiKankerNeoplasma.setText("30");
        }
        TotalNilai();
    }//GEN-LAST:event_KankerNeoplasmaItemStateChanged

    private void DisorentasiMentalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DisorentasiMentalItemStateChanged
        if(DisorentasiMental.getSelectedIndex()==0){
            NilaiDisorentasiMental.setText("0");
        }else{
            NilaiDisorentasiMental.setText("20");
        }
        TotalNilai();
    }//GEN-LAST:event_DisorentasiMentalItemStateChanged

    private void TDSistolikItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TDSistolikItemStateChanged
        if(TDSistolik.getSelectedIndex()==0){
            NilaiTDSistolik.setText("0");
        }else{
            NilaiTDSistolik.setText("20");
        }
        TotalNilai();
    }//GEN-LAST:event_TDSistolikItemStateChanged

    private void NadiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NadiItemStateChanged
        if(Nadi.getSelectedIndex()==0){
            NilaiNadi.setText("0");
        }else{
            NilaiNadi.setText("10");
        }
        TotalNilai();
    }//GEN-LAST:event_NadiItemStateChanged

    private void FrekuensiNapasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FrekuensiNapasItemStateChanged
        if(FrekuensiNapas.getSelectedIndex()==0){
            NilaiFrekuensiNapas.setText("0");
        }else{
            NilaiFrekuensiNapas.setText("20");
        }
        TotalNilai();
    }//GEN-LAST:event_FrekuensiNapasItemStateChanged

    private void SuhuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SuhuItemStateChanged
        if(Suhu.getSelectedIndex()==0){
            NilaiSuhu.setText("0");
        }else{
            NilaiSuhu.setText("15");
        }
        TotalNilai();
    }//GEN-LAST:event_SuhuItemStateChanged

    private void pHDarahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pHDarahItemStateChanged
        if(pHDarah.getSelectedIndex()==0){
            NilaipHDarah.setText("0");
        }else{
            NilaipHDarah.setText("30");
        }
        TotalNilai();
    }//GEN-LAST:event_pHDarahItemStateChanged

    private void BUNItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BUNItemStateChanged
        if(BUN.getSelectedIndex()==0){
            NilaiBUN.setText("0");
        }else{
            NilaiBUN.setText("20");
        }
        TotalNilai();
    }//GEN-LAST:event_BUNItemStateChanged

    private void GlukosaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GlukosaItemStateChanged
        if(Glukosa.getSelectedIndex()==0){
            NilaiGlukosa.setText("0");
        }else{
            NilaiGlukosa.setText("10");
        }
        TotalNilai();
    }//GEN-LAST:event_GlukosaItemStateChanged

    private void HematokritItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HematokritItemStateChanged
        if(Hematokrit.getSelectedIndex()==0){
            NilaiHematokrit.setText("0");
        }else{
            NilaiHematokrit.setText("10");
        }
        TotalNilai();
    }//GEN-LAST:event_HematokritItemStateChanged

    private void NatriumItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NatriumItemStateChanged
        if(Natrium.getSelectedIndex()==0){
            NilaiNatrium.setText("0");
        }else{
            NilaiNatrium.setText("20");
        }
        TotalNilai();
    }//GEN-LAST:event_NatriumItemStateChanged

    private void PaOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PaOItemStateChanged
        if(PaO.getSelectedIndex()==0){
            NilaiPaO.setText("0");
        }else{
            NilaiPaO.setText("10");
        }
        TotalNilai();
    }//GEN-LAST:event_PaOItemStateChanged

    private void EfusiPleuraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EfusiPleuraItemStateChanged
        if(EfusiPleura.getSelectedIndex()==0){
            NilaiEfusiPleura.setText("0");
        }else{
            NilaiEfusiPleura.setText("10");
        }
        TotalNilai();
    }//GEN-LAST:event_EfusiPleuraItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningPneumoniaSeverityIndex dialog = new RMSkriningPneumoniaSeverityIndex(new javax.swing.JFrame(), true);
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
    private widget.ComboBox BUN;
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
    private widget.ComboBox DisorentasiMental;
    private widget.ComboBox EfusiPleura;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox FrekuensiNapas;
    private widget.ComboBox GagalJantung;
    private widget.ComboBox GinjalKronis;
    private widget.ComboBox Glukosa;
    private widget.ComboBox Hematokrit;
    private widget.ComboBox Jam;
    private widget.TextBox Jk;
    private widget.ComboBox KankerNeoplasma;
    private widget.TextBox KdPetugas;
    private widget.TextBox Kelas;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningPSI;
    private widget.TextBox Mortalitas;
    private widget.ComboBox Nadi;
    private widget.ComboBox Natrium;
    private widget.TextBox NilaiBUN;
    private widget.TextBox NilaiDisorentasiMental;
    private widget.TextBox NilaiEfusiPleura;
    private widget.TextBox NilaiFrekuensiNapas;
    private widget.TextBox NilaiGagalJantung;
    private widget.TextBox NilaiGinjalKronis;
    private widget.TextBox NilaiGlukosa;
    private widget.TextBox NilaiHematokrit;
    private widget.TextBox NilaiKankerNeoplasma;
    private widget.TextBox NilaiNadi;
    private widget.TextBox NilaiNatrium;
    private widget.TextBox NilaiPaO;
    private widget.TextBox NilaiPenyakitHati;
    private widget.TextBox NilaiPenyakitSerebrovaskular;
    private widget.TextBox NilaiSuhu;
    private widget.TextBox NilaiTDSistolik;
    private widget.TextBox NilaiTinggalDiPanti;
    private widget.TextBox NilaiUmur;
    private widget.TextBox NilaipHDarah;
    private widget.TextBox NmPetugas;
    private widget.ComboBox PaO;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PenyakitHati;
    private widget.ComboBox PenyakitSerebrovaskular;
    private widget.TextBox Rekomendasi;
    private widget.ScrollPane Scroll;
    private widget.TextBox SkorInterpretasi;
    private widget.ComboBox Suhu;
    private widget.TextBox TCari;
    private widget.ComboBox TDSistolik;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.ComboBox TinggalDiPanti;
    private widget.TextBox TotalSkorPSI;
    private widget.TextBox Umur;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
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
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
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
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private widget.ComboBox pHDarah;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_pneumonia_severity_index.nip,petugas.nama,skrining_pneumonia_severity_index.tanggal,"+
                    "if(reg_periksa.sttsumur='Th',reg_periksa.umurdaftar,'0') as umur,skrining_pneumonia_severity_index.nilai_umur,skrining_pneumonia_severity_index.tinggal_di_panti_jompo,skrining_pneumonia_severity_index.nilai_tinggal_di_panti_jompo,"+
                    "skrining_pneumonia_severity_index.gagal_jantung,skrining_pneumonia_severity_index.nilai_gagal_jantung,skrining_pneumonia_severity_index.penyakit_hati,skrining_pneumonia_severity_index.nilai_penyakit_hati,skrining_pneumonia_severity_index.penyakit_ginjal,"+
                    "skrining_pneumonia_severity_index.nilai_penyakit_ginjal,skrining_pneumonia_severity_index.kanker,skrining_pneumonia_severity_index.nilai_kanker,skrining_pneumonia_severity_index.penyakit_serebrovaskuler,skrining_pneumonia_severity_index.nilai_penyakit_serebrovaskuler,"+
                    "skrining_pneumonia_severity_index.disorentasi_mental,skrining_pneumonia_severity_index.nilai_disorentasi_mental,skrining_pneumonia_severity_index.frekuensi_napas,skrining_pneumonia_severity_index.nilai_frekuensi_napas,skrining_pneumonia_severity_index.td_sistolik,"+
                    "skrining_pneumonia_severity_index.nilai_td_sistolik,skrining_pneumonia_severity_index.suhu,skrining_pneumonia_severity_index.nilai_suhu,skrining_pneumonia_severity_index.nadi,skrining_pneumonia_severity_index.nilai_nadi,skrining_pneumonia_severity_index.ph_darah,"+
                    "skrining_pneumonia_severity_index.nilai_ph_darah,skrining_pneumonia_severity_index.natrium,skrining_pneumonia_severity_index.nilai_natrium,skrining_pneumonia_severity_index.bun,skrining_pneumonia_severity_index.nilai_bun,skrining_pneumonia_severity_index.pao,"+
                    "skrining_pneumonia_severity_index.nilai_pao,skrining_pneumonia_severity_index.glukosa,skrining_pneumonia_severity_index.nilai_glukosa,skrining_pneumonia_severity_index.efusi_pleura,skrining_pneumonia_severity_index.nilai_efusi_pleura,skrining_pneumonia_severity_index.hematokrit,"+
                    "skrining_pneumonia_severity_index.nilai_hematokrit,skrining_pneumonia_severity_index.total_skor,skrining_pneumonia_severity_index.kelas,skrining_pneumonia_severity_index.skor_interpretasi,skrining_pneumonia_severity_index.mortalitas,skrining_pneumonia_severity_index.rekomendasi "+
                    "from skrining_pneumonia_severity_index inner join reg_periksa on skrining_pneumonia_severity_index.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_pneumonia_severity_index.nip=petugas.nip "+
                    "where skrining_pneumonia_severity_index.tanggal between ? and ? order by skrining_pneumonia_severity_index.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_pneumonia_severity_index.nip,petugas.nama,skrining_pneumonia_severity_index.tanggal,"+
                    "if(reg_periksa.sttsumur='Th',reg_periksa.umurdaftar,'0') as umur,skrining_pneumonia_severity_index.nilai_umur,skrining_pneumonia_severity_index.tinggal_di_panti_jompo,skrining_pneumonia_severity_index.nilai_tinggal_di_panti_jompo,"+
                    "skrining_pneumonia_severity_index.gagal_jantung,skrining_pneumonia_severity_index.nilai_gagal_jantung,skrining_pneumonia_severity_index.penyakit_hati,skrining_pneumonia_severity_index.nilai_penyakit_hati,skrining_pneumonia_severity_index.penyakit_ginjal,"+
                    "skrining_pneumonia_severity_index.nilai_penyakit_ginjal,skrining_pneumonia_severity_index.kanker,skrining_pneumonia_severity_index.nilai_kanker,skrining_pneumonia_severity_index.penyakit_serebrovaskuler,skrining_pneumonia_severity_index.nilai_penyakit_serebrovaskuler,"+
                    "skrining_pneumonia_severity_index.disorentasi_mental,skrining_pneumonia_severity_index.nilai_disorentasi_mental,skrining_pneumonia_severity_index.frekuensi_napas,skrining_pneumonia_severity_index.nilai_frekuensi_napas,skrining_pneumonia_severity_index.td_sistolik,"+
                    "skrining_pneumonia_severity_index.nilai_td_sistolik,skrining_pneumonia_severity_index.suhu,skrining_pneumonia_severity_index.nilai_suhu,skrining_pneumonia_severity_index.nadi,skrining_pneumonia_severity_index.nilai_nadi,skrining_pneumonia_severity_index.ph_darah,"+
                    "skrining_pneumonia_severity_index.nilai_ph_darah,skrining_pneumonia_severity_index.natrium,skrining_pneumonia_severity_index.nilai_natrium,skrining_pneumonia_severity_index.bun,skrining_pneumonia_severity_index.nilai_bun,skrining_pneumonia_severity_index.pao,"+
                    "skrining_pneumonia_severity_index.nilai_pao,skrining_pneumonia_severity_index.glukosa,skrining_pneumonia_severity_index.nilai_glukosa,skrining_pneumonia_severity_index.efusi_pleura,skrining_pneumonia_severity_index.nilai_efusi_pleura,skrining_pneumonia_severity_index.hematokrit,"+
                    "skrining_pneumonia_severity_index.nilai_hematokrit,skrining_pneumonia_severity_index.total_skor,skrining_pneumonia_severity_index.kelas,skrining_pneumonia_severity_index.skor_interpretasi,skrining_pneumonia_severity_index.mortalitas,skrining_pneumonia_severity_index.rekomendasi "+
                    "from skrining_pneumonia_severity_index inner join reg_periksa on skrining_pneumonia_severity_index.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_pneumonia_severity_index.nip=petugas.nip "+
                    "where skrining_pneumonia_severity_index.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_pneumonia_severity_index.nip like ? or petugas.nama like ? or skrining_pneumonia_severity_index.rekomendasi like ?) "+
                    "order by skrining_pneumonia_severity_index.tanggal ");
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
                    ps.setString(8,"%"+TCari.getText()+"%");

                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),
                        rs.getString("umur"),rs.getString("nilai_umur"),rs.getString("tinggal_di_panti_jompo"),rs.getString("nilai_tinggal_di_panti_jompo"),rs.getString("gagal_jantung"),rs.getString("nilai_gagal_jantung"),
                        rs.getString("penyakit_hati"),rs.getString("nilai_penyakit_hati"),rs.getString("penyakit_ginjal"),rs.getString("nilai_penyakit_ginjal"),rs.getString("kanker"),rs.getString("nilai_kanker"),
                        rs.getString("penyakit_serebrovaskuler"),rs.getString("nilai_penyakit_serebrovaskuler"),rs.getString("disorentasi_mental"),rs.getString("nilai_disorentasi_mental"),rs.getString("frekuensi_napas"),
                        rs.getString("nilai_frekuensi_napas"),rs.getString("td_sistolik"),rs.getString("nilai_td_sistolik"),rs.getString("suhu"),rs.getString("nilai_suhu"),rs.getString("nadi"),rs.getString("nilai_nadi"),
                        rs.getString("ph_darah"),rs.getString("nilai_ph_darah"),rs.getString("natrium"),rs.getString("nilai_natrium"),rs.getString("bun"),rs.getString("nilai_bun"),rs.getString("pao"),rs.getString("nilai_pao"),
                        rs.getString("glukosa"),rs.getString("nilai_glukosa"),rs.getString("efusi_pleura"),rs.getString("nilai_efusi_pleura"),rs.getString("hematokrit"),rs.getString("nilai_hematokrit"),rs.getString("total_skor"),
                        rs.getString("kelas"),rs.getString("skor_interpretasi"),rs.getString("mortalitas"),rs.getString("rekomendasi"),
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
        TinggalDiPanti.setSelectedIndex(0);
        NilaiTinggalDiPanti.setText("0");
        GagalJantung.setSelectedIndex(0);
        NilaiGagalJantung.setText("0");
        PenyakitHati.setSelectedIndex(0);
        NilaiPenyakitHati.setText("0");
        GinjalKronis.setSelectedIndex(0);
        NilaiGinjalKronis.setText("0");
        KankerNeoplasma.setSelectedIndex(0);
        NilaiKankerNeoplasma.setText("0");
        PenyakitSerebrovaskular.setSelectedIndex(0);
        NilaiPenyakitSerebrovaskular.setText("0");
        DisorentasiMental.setSelectedIndex(0);
        NilaiDisorentasiMental.setText("0");
        FrekuensiNapas.setSelectedIndex(0);
        NilaiFrekuensiNapas.setText("0");
        TDSistolik.setSelectedIndex(0);
        NilaiTDSistolik.setText("0");
        Suhu.setSelectedIndex(0);
        NilaiSuhu.setText("0");
        Nadi.setSelectedIndex(0);
        NilaiNadi.setText("0");
        pHDarah.setSelectedIndex(0);
        NilaipHDarah.setText("0");
        Natrium.setSelectedIndex(0);
        NilaiNatrium.setText("0");
        BUN.setSelectedIndex(0);
        NilaiBUN.setText("0");
        PaO.setSelectedIndex(0);
        NilaiPaO.setText("0");
        Glukosa.setSelectedIndex(0);
        NilaiGlukosa.setText("0");
        EfusiPleura.setSelectedIndex(0);
        NilaiEfusiPleura.setText("0");
        Hematokrit.setSelectedIndex(0);
        NilaiHematokrit.setText("0");
        Kelas.setText("I");
        SkorInterpretasi.setText("Ditentukan Algoritma Awal");
        Mortalitas.setText("<0.4%");
        Rekomendasi.setText("Rawat Jalan");
        Tanggal.setDate(new Date());
        Jk.requestFocus();
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
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NilaiUmur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TinggalDiPanti.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NilaiTinggalDiPanti.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            GagalJantung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NilaiGagalJantung.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            PenyakitHati.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NilaiPenyakitHati.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            GinjalKronis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NilaiGinjalKronis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KankerNeoplasma.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NilaiKankerNeoplasma.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            PenyakitSerebrovaskular.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NilaiPenyakitSerebrovaskular.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            DisorentasiMental.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NilaiDisorentasiMental.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            FrekuensiNapas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            NilaiFrekuensiNapas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            TDSistolik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            NilaiTDSistolik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Suhu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            NilaiSuhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Nadi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            NilaiNadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            pHDarah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            NilaipHDarah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Natrium.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            NilaiNatrium.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            BUN.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            NilaiBUN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            PaO.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            NilaiPaO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Glukosa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            NilaiGlukosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            EfusiPleura.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            NilaiEfusiPleura.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Hematokrit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            NilaiHematokrit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            TotalSkorPSI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Kelas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            SkorInterpretasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Mortalitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Rekomendasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
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
                    if(rs.getString("sttsumur").equals("Th")){
                        Umur.setText(rs.getString("umurdaftar"));
                    }else{
                        Umur.setText("0");
                    }
                    if(Jk.getText().equals("L")){
                        NilaiUmur.setText(Umur.getText());
                    }else{
                        NilaiUmur.setText((rs.getInt("umurdaftar")-10)+"");
                    }
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
        TotalNilai();
    }
    
    private void TotalNilai(){
        try{
            if((!NilaiUmur.getText().equals(""))&&(!NilaiTinggalDiPanti.getText().equals(""))&&(!NilaiGagalJantung.getText().equals(""))&&(!NilaiGinjalKronis.getText().equals(""))&&
                    (!NilaiPenyakitSerebrovaskular.getText().equals(""))&&(!NilaiPenyakitHati.getText().equals(""))&&(!NilaiKankerNeoplasma.getText().equals(""))&&(!NilaiDisorentasiMental.getText().equals(""))&&
                    (!NilaiTDSistolik.getText().equals(""))&&(!NilaiNadi.getText().equals(""))&&(!NilaiFrekuensiNapas.getText().equals(""))&&(!NilaiSuhu.getText().equals(""))&&(!NilaipHDarah.getText().equals(""))&&
                    (!NilaiBUN.getText().equals(""))&&(!NilaiGlukosa.getText().equals(""))&&(!NilaiHematokrit.getText().equals(""))&&(!NilaiNatrium.getText().equals(""))&&(!NilaiPaO.getText().equals(""))&&(!NilaiEfusiPleura.getText().equals(""))){
                TotalSkorPSI.setText(Integer.toString(
                    Integer.parseInt(NilaiUmur.getText())+
                    Integer.parseInt(NilaiTinggalDiPanti.getText())+
                    Integer.parseInt(NilaiGagalJantung.getText())+
                    Integer.parseInt(NilaiGinjalKronis.getText())+
                    Integer.parseInt(NilaiPenyakitSerebrovaskular.getText())+
                    Integer.parseInt(NilaiPenyakitHati.getText())+
                    Integer.parseInt(NilaiKankerNeoplasma.getText())+
                    Integer.parseInt(NilaiDisorentasiMental.getText())+
                    Integer.parseInt(NilaiTDSistolik.getText())+
                    Integer.parseInt(NilaiNadi.getText())+
                    Integer.parseInt(NilaiFrekuensiNapas.getText())+
                    Integer.parseInt(NilaiSuhu.getText())+
                    Integer.parseInt(NilaipHDarah.getText())+
                    Integer.parseInt(NilaiBUN.getText())+
                    Integer.parseInt(NilaiGlukosa.getText())+
                    Integer.parseInt(NilaiHematokrit.getText())+
                    Integer.parseInt(NilaiNatrium.getText())+
                    Integer.parseInt(NilaiPaO.getText())+
                    Integer.parseInt(NilaiEfusiPleura.getText())
                ));
            }
                
            if((Integer.parseInt(Umur.getText())<50)&&(NilaiGagalJantung.getText().equals("0"))&&(NilaiGinjalKronis.getText().equals("0"))&&(NilaiPenyakitSerebrovaskular.getText().equals("0"))&&
                    (NilaiPenyakitHati.getText().equals("0"))&&(NilaiKankerNeoplasma.getText().equals("0"))&&(NilaiDisorentasiMental.getText().equals("0"))&&(NilaiTDSistolik.getText().equals("0"))&&
                    (NilaiNadi.getText().equals("0"))&&(NilaiFrekuensiNapas.getText().equals("0"))&&(NilaiSuhu.getText().equals("0"))){
                Kelas.setText("I");
                SkorInterpretasi.setText("Ditentukan Algoritma Awal");
                Mortalitas.setText("<0.4%");
                Rekomendasi.setText("Rawat Jalan");
            }else {
                if(Integer.parseInt(TotalSkorPSI.getText())<=70){
                    Kelas.setText("II");
                    SkorInterpretasi.setText("≤70");
                    Mortalitas.setText("~0.6%");
                    Rekomendasi.setText("Rawat Jalan");
                }else if((Integer.parseInt(TotalSkorPSI.getText())>=71)&&(Integer.parseInt(TotalSkorPSI.getText())<=90)){
                    Kelas.setText("III");
                    SkorInterpretasi.setText("71–90");
                    Mortalitas.setText("~2.8%");
                    Rekomendasi.setText("Observasi/Rawat Inap Singkat");
                }else if((Integer.parseInt(TotalSkorPSI.getText())>=91)&&(Integer.parseInt(TotalSkorPSI.getText())<=130)){
                    Kelas.setText("IV");
                    SkorInterpretasi.setText("91–130");
                    Mortalitas.setText("~8–9%");
                    Rekomendasi.setText("Rawat Inap");
                }else if(Integer.parseInt(TotalSkorPSI.getText())>130){
                    Kelas.setText("V");
                    SkorInterpretasi.setText(">130");
                    Mortalitas.setText("~27–30%");
                    Rekomendasi.setText("Rawat Inap, Pertimbangkan ICU");
                }
            }
        }catch (Exception e) {
            TotalSkorPSI.setText("0");
            Kelas.setText("I");
            SkorInterpretasi.setText("Ditentukan Algoritma Awal");
            Mortalitas.setText("<0.4%");
            Rekomendasi.setText("Rawat Jalan");
            System.out.println("Notif : "+e);
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
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
        BtnSimpan.setEnabled(akses.getskrining_pneumonia_severity_index());
        BtnHapus.setEnabled(akses.getskrining_pneumonia_severity_index());
        BtnEdit.setEnabled(akses.getskrining_pneumonia_severity_index());
        BtnPrint.setEnabled(akses.getskrining_pneumonia_severity_index()); 
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
        if(Sequel.mengedittf("skrining_pneumonia_severity_index","no_rawat=?","no_rawat=?,tanggal=?,nilai_umur=?,tinggal_di_panti_jompo=?,nilai_tinggal_di_panti_jompo=?,gagal_jantung=?,nilai_gagal_jantung=?,penyakit_hati=?,nilai_penyakit_hati=?,"+
                "penyakit_ginjal=?,nilai_penyakit_ginjal=?,kanker=?,nilai_kanker=?,penyakit_serebrovaskuler=?,nilai_penyakit_serebrovaskuler=?,disorentasi_mental=?,nilai_disorentasi_mental=?,frekuensi_napas=?,nilai_frekuensi_napas=?,td_sistolik=?,"+
                "nilai_td_sistolik=?,suhu=?,nilai_suhu=?,nadi=?,nilai_nadi=?,ph_darah=?,nilai_ph_darah=?,natrium=?,nilai_natrium=?,bun=?,nilai_bun=?,pao=?,nilai_pao=?,glukosa=?,nilai_glukosa=?,efusi_pleura=?,nilai_efusi_pleura=?,hematokrit=?,"+
                "nilai_hematokrit=?,total_skor=?,kelas=?,skor_interpretasi=?,mortalitas=?,rekomendasi=?,nip=?",46,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                NilaiUmur.getText(),TinggalDiPanti.getSelectedItem().toString(),NilaiTinggalDiPanti.getText(),GagalJantung.getSelectedItem().toString(),NilaiGagalJantung.getText(),PenyakitHati.getSelectedItem().toString(),NilaiPenyakitHati.getText(), 
                GinjalKronis.getSelectedItem().toString(),NilaiGinjalKronis.getText(),KankerNeoplasma.getSelectedItem().toString(),NilaiKankerNeoplasma.getText(),PenyakitSerebrovaskular.getSelectedItem().toString(),NilaiPenyakitSerebrovaskular.getText(), 
                DisorentasiMental.getSelectedItem().toString(),NilaiDisorentasiMental.getText(),FrekuensiNapas.getSelectedItem().toString(),NilaiFrekuensiNapas.getText(),TDSistolik.getSelectedItem().toString(),NilaiTDSistolik.getText(), 
                Suhu.getSelectedItem().toString(),NilaiSuhu.getText(),Nadi.getSelectedItem().toString(),NilaiNadi.getText(),pHDarah.getSelectedItem().toString(),NilaipHDarah.getText(),Natrium.getSelectedItem().toString(),NilaiNatrium.getText(), 
                BUN.getSelectedItem().toString(),NilaiBUN.getText(),PaO.getSelectedItem().toString(),NilaiPaO.getText(),Glukosa.getSelectedItem().toString(),NilaiGlukosa.getText(),EfusiPleura.getSelectedItem().toString(),NilaiEfusiPleura.getText(), 
                Hematokrit.getSelectedItem().toString(),NilaiHematokrit.getText(),TotalSkorPSI.getText(),Kelas.getText(),SkorInterpretasi.getText(),Mortalitas.getText(),Rekomendasi.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(NilaiUmur.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(TinggalDiPanti.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(NilaiTinggalDiPanti.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(GagalJantung.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(NilaiGagalJantung.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(PenyakitHati.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(NilaiPenyakitHati.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(GinjalKronis.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(NilaiGinjalKronis.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(KankerNeoplasma.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(NilaiKankerNeoplasma.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(PenyakitSerebrovaskular.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(NilaiPenyakitSerebrovaskular.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(DisorentasiMental.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(NilaiDisorentasiMental.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(FrekuensiNapas.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(NilaiFrekuensiNapas.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(TDSistolik.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(NilaiTDSistolik.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(Suhu.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(NilaiSuhu.getText(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(Nadi.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(NilaiNadi.getText(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(pHDarah.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(NilaipHDarah.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(Natrium.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(NilaiNatrium.getText(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(BUN.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(NilaiBUN.getText(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(PaO.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(NilaiPaO.getText(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(Glukosa.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(NilaiGlukosa.getText(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(EfusiPleura.getSelectedItem().toString(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(NilaiEfusiPleura.getText(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(Hematokrit.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
               tbObat.setValueAt(NilaiHematokrit.getText(),tbObat.getSelectedRow(),45);
               tbObat.setValueAt(TotalSkorPSI.getText(),tbObat.getSelectedRow(),46);
               tbObat.setValueAt(Kelas.getText(),tbObat.getSelectedRow(),47);
               tbObat.setValueAt(SkorInterpretasi.getText(),tbObat.getSelectedRow(),48);
               tbObat.setValueAt(Mortalitas.getText(),tbObat.getSelectedRow(),49);
               tbObat.setValueAt(Rekomendasi.getText(),tbObat.getSelectedRow(),50);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_pneumonia_severity_index where no_rawat=?",1,new String[]{
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
        if(Sequel.menyimpantf("skrining_pneumonia_severity_index","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",45,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            NilaiUmur.getText(),TinggalDiPanti.getSelectedItem().toString(),NilaiTinggalDiPanti.getText(),GagalJantung.getSelectedItem().toString(),NilaiGagalJantung.getText(),PenyakitHati.getSelectedItem().toString(),NilaiPenyakitHati.getText(), 
            GinjalKronis.getSelectedItem().toString(),NilaiGinjalKronis.getText(),KankerNeoplasma.getSelectedItem().toString(),NilaiKankerNeoplasma.getText(),PenyakitSerebrovaskular.getSelectedItem().toString(),NilaiPenyakitSerebrovaskular.getText(), 
            DisorentasiMental.getSelectedItem().toString(),NilaiDisorentasiMental.getText(),FrekuensiNapas.getSelectedItem().toString(),NilaiFrekuensiNapas.getText(),TDSistolik.getSelectedItem().toString(),NilaiTDSistolik.getText(), 
            Suhu.getSelectedItem().toString(),NilaiSuhu.getText(),Nadi.getSelectedItem().toString(),NilaiNadi.getText(),pHDarah.getSelectedItem().toString(),NilaipHDarah.getText(),Natrium.getSelectedItem().toString(),NilaiNatrium.getText(), 
            BUN.getSelectedItem().toString(),NilaiBUN.getText(),PaO.getSelectedItem().toString(),NilaiPaO.getText(),Glukosa.getSelectedItem().toString(),NilaiGlukosa.getText(),EfusiPleura.getSelectedItem().toString(),NilaiEfusiPleura.getText(), 
            Hematokrit.getSelectedItem().toString(),NilaiHematokrit.getText(),TotalSkorPSI.getText(),Kelas.getText(),SkorInterpretasi.getText(),Mortalitas.getText(),Rekomendasi.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                Umur.getText(),NilaiUmur.getText(),TinggalDiPanti.getSelectedItem().toString(),NilaiTinggalDiPanti.getText(),GagalJantung.getSelectedItem().toString(),NilaiGagalJantung.getText(),PenyakitHati.getSelectedItem().toString(),NilaiPenyakitHati.getText(), 
                GinjalKronis.getSelectedItem().toString(),NilaiGinjalKronis.getText(),KankerNeoplasma.getSelectedItem().toString(),NilaiKankerNeoplasma.getText(),PenyakitSerebrovaskular.getSelectedItem().toString(),NilaiPenyakitSerebrovaskular.getText(), 
                DisorentasiMental.getSelectedItem().toString(),NilaiDisorentasiMental.getText(),FrekuensiNapas.getSelectedItem().toString(),NilaiFrekuensiNapas.getText(),TDSistolik.getSelectedItem().toString(),NilaiTDSistolik.getText(), 
                Suhu.getSelectedItem().toString(),NilaiSuhu.getText(),Nadi.getSelectedItem().toString(),NilaiNadi.getText(),pHDarah.getSelectedItem().toString(),NilaipHDarah.getText(),Natrium.getSelectedItem().toString(),NilaiNatrium.getText(), 
                BUN.getSelectedItem().toString(),NilaiBUN.getText(),PaO.getSelectedItem().toString(),NilaiPaO.getText(),Glukosa.getSelectedItem().toString(),NilaiGlukosa.getText(),EfusiPleura.getSelectedItem().toString(),NilaiEfusiPleura.getText(), 
                Hematokrit.getSelectedItem().toString(),NilaiHematokrit.getText(),TotalSkorPSI.getText(),Kelas.getText(),SkorInterpretasi.getText(),Mortalitas.getText(),Rekomendasi.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
}

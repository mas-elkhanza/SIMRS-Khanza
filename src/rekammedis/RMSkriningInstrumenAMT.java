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
public final class RMSkriningInstrumenAMT extends javax.swing.JDialog {
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
    public RMSkriningInstrumenAMT(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Petugas","Nama Petugas","Tanggal",
            "AMT1","N.AMT1","AMT2","N.AMT2","AMT3","N.AMT3","AMT4","N.AMT4","AMT5","N.AMT5",
            "AMT6","N.AMT6","AMT7","N.AMT7","AMT8","N.AMT8","AMT9","N.AMT9","AMT10","N.AMT10",
            "N.Total","Kesimpulan"
            
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 30; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(85);
            }else if(i==4){
                column.setPreferredWidth(35);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(85);
            }else if(i==9){
                column.setPreferredWidth(55);
            }else if(i==10){
                column.setPreferredWidth(85);
            }else if(i==11){
                column.setPreferredWidth(55);
            }else if(i==12){
                column.setPreferredWidth(85);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(85);
            }else if(i==15){
                column.setPreferredWidth(55);
            }else if(i==16){
                column.setPreferredWidth(85);
            }else if(i==17){
                column.setPreferredWidth(55);
            }else if(i==18){
                column.setPreferredWidth(85);
            }else if(i==19){
                column.setPreferredWidth(55);
            }else if(i==20){
                column.setPreferredWidth(85);
            }else if(i==21){
                column.setPreferredWidth(55);
            }else if(i==22){
                column.setPreferredWidth(85);
            }else if(i==23){
                column.setPreferredWidth(55);
            }else if(i==24){
                column.setPreferredWidth(85);
            }else if(i==25){
                column.setPreferredWidth(55);
            }else if(i==26){
                column.setPreferredWidth(85);
            }else if(i==27){
                column.setPreferredWidth(60);
            }else if(i==28){
                column.setPreferredWidth(60);
            }else if(i==29){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        Kesimpulan.setDocument(new batasInput((int)100).getKata(Kesimpulan));
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
        MnSkriningInstrumenAMT = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
        Jk = new widget.TextBox();
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
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel99 = new widget.Label();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        jLabel102 = new widget.Label();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        jLabel106 = new widget.Label();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        jLabel109 = new widget.Label();
        AMT1 = new widget.ComboBox();
        AMT2 = new widget.ComboBox();
        AMT3 = new widget.ComboBox();
        AMT4 = new widget.ComboBox();
        AMT5 = new widget.ComboBox();
        AMT6 = new widget.ComboBox();
        AMT7 = new widget.ComboBox();
        AMT8 = new widget.ComboBox();
        AMT9 = new widget.ComboBox();
        AMT10 = new widget.ComboBox();
        jLabel130 = new widget.Label();
        NilaiAMT1 = new widget.TextBox();
        jLabel141 = new widget.Label();
        jLabel142 = new widget.Label();
        jLabel143 = new widget.Label();
        jLabel144 = new widget.Label();
        jLabel145 = new widget.Label();
        jLabel146 = new widget.Label();
        jLabel147 = new widget.Label();
        jLabel148 = new widget.Label();
        jLabel149 = new widget.Label();
        NilaiAMT2 = new widget.TextBox();
        NilaiAMT3 = new widget.TextBox();
        NilaiAMT4 = new widget.TextBox();
        NilaiAMT5 = new widget.TextBox();
        NilaiAMT6 = new widget.TextBox();
        NilaiAMT7 = new widget.TextBox();
        NilaiAMT8 = new widget.TextBox();
        NilaiAMT9 = new widget.TextBox();
        NilaiAMT10 = new widget.TextBox();
        jLabel160 = new widget.Label();
        jLabel161 = new widget.Label();
        Kesimpulan = new widget.TextBox();
        jLabel162 = new widget.Label();
        TotalNilai = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel110 = new widget.Label();
        jLabel111 = new widget.Label();
        jLabel112 = new widget.Label();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        jLabel115 = new widget.Label();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        jLabel119 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel163 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningInstrumenAMT.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningInstrumenAMT.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningInstrumenAMT.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningInstrumenAMT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningInstrumenAMT.setText("Formulir Skrining AMT");
        MnSkriningInstrumenAMT.setName("MnSkriningInstrumenAMT"); // NOI18N
        MnSkriningInstrumenAMT.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSkriningInstrumenAMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningInstrumenAMTActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningInstrumenAMT);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Abbrevieted Mental Test (AMT) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-09-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-09-2025" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 436));
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
        FormInput.setPreferredSize(new java.awt.Dimension(830, 473));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-09-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

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
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
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

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. DETEKSI DINI FUNGSI KOGNITIF DENGAN MENGGUNAKAN ABBREVIATED MENTAL TEST");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(11, 70, 700, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("1.");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(44, 90, 25, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("2.");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(44, 120, 25, 23);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("3.");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(44, 150, 25, 23);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("4.");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(44, 180, 25, 23);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("5.");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(44, 210, 25, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("6.");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(44, 240, 25, 23);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("7.");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(44, 270, 25, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("8.");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(44, 300, 25, 23);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("9.");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(44, 330, 25, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("10.");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(44, 360, 25, 23);

        AMT1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT1.setName("AMT1"); // NOI18N
        AMT1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT1ItemStateChanged(evt);
            }
        });
        AMT1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT1KeyPressed(evt);
            }
        });
        FormInput.add(AMT1);
        AMT1.setBounds(620, 90, 80, 23);

        AMT2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT2.setName("AMT2"); // NOI18N
        AMT2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT2ItemStateChanged(evt);
            }
        });
        AMT2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT2KeyPressed(evt);
            }
        });
        FormInput.add(AMT2);
        AMT2.setBounds(620, 120, 80, 23);

        AMT3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT3.setName("AMT3"); // NOI18N
        AMT3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT3ItemStateChanged(evt);
            }
        });
        AMT3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT3KeyPressed(evt);
            }
        });
        FormInput.add(AMT3);
        AMT3.setBounds(620, 150, 80, 23);

        AMT4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT4.setName("AMT4"); // NOI18N
        AMT4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT4ItemStateChanged(evt);
            }
        });
        AMT4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT4KeyPressed(evt);
            }
        });
        FormInput.add(AMT4);
        AMT4.setBounds(620, 180, 80, 23);

        AMT5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT5.setName("AMT5"); // NOI18N
        AMT5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT5ItemStateChanged(evt);
            }
        });
        AMT5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT5KeyPressed(evt);
            }
        });
        FormInput.add(AMT5);
        AMT5.setBounds(620, 210, 80, 23);

        AMT6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT6.setName("AMT6"); // NOI18N
        AMT6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT6ItemStateChanged(evt);
            }
        });
        AMT6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT6KeyPressed(evt);
            }
        });
        FormInput.add(AMT6);
        AMT6.setBounds(620, 240, 80, 23);

        AMT7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT7.setName("AMT7"); // NOI18N
        AMT7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT7ItemStateChanged(evt);
            }
        });
        AMT7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT7KeyPressed(evt);
            }
        });
        FormInput.add(AMT7);
        AMT7.setBounds(620, 270, 80, 23);

        AMT8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT8.setName("AMT8"); // NOI18N
        AMT8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT8ItemStateChanged(evt);
            }
        });
        AMT8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT8KeyPressed(evt);
            }
        });
        FormInput.add(AMT8);
        AMT8.setBounds(620, 300, 80, 23);

        AMT9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT9.setName("AMT9"); // NOI18N
        AMT9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT9ItemStateChanged(evt);
            }
        });
        AMT9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT9KeyPressed(evt);
            }
        });
        FormInput.add(AMT9);
        AMT9.setBounds(620, 330, 80, 23);

        AMT10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Salah", "Benar" }));
        AMT10.setName("AMT10"); // NOI18N
        AMT10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AMT10ItemStateChanged(evt);
            }
        });
        AMT10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AMT10KeyPressed(evt);
            }
        });
        FormInput.add(AMT10);
        AMT10.setBounds(620, 360, 80, 23);

        jLabel130.setText("Nilai :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(700, 90, 40, 23);

        NilaiAMT1.setHighlighter(null);
        NilaiAMT1.setName("NilaiAMT1"); // NOI18N
        FormInput.add(NilaiAMT1);
        NilaiAMT1.setBounds(744, 90, 45, 23);

        jLabel141.setText("Nilai :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(700, 120, 40, 23);

        jLabel142.setText("Nilai :");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(700, 150, 40, 23);

        jLabel143.setText("Nilai :");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(700, 180, 40, 23);

        jLabel144.setText("Nilai :");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(700, 210, 40, 23);

        jLabel145.setText("Nilai :");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(700, 240, 40, 23);

        jLabel146.setText("Nilai :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(700, 270, 40, 23);

        jLabel147.setText("Nilai :");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(700, 300, 40, 23);

        jLabel148.setText("Nilai :");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(700, 330, 40, 23);

        jLabel149.setText("Nilai :");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(700, 360, 40, 23);

        NilaiAMT2.setHighlighter(null);
        NilaiAMT2.setName("NilaiAMT2"); // NOI18N
        FormInput.add(NilaiAMT2);
        NilaiAMT2.setBounds(744, 120, 45, 23);

        NilaiAMT3.setHighlighter(null);
        NilaiAMT3.setName("NilaiAMT3"); // NOI18N
        FormInput.add(NilaiAMT3);
        NilaiAMT3.setBounds(744, 150, 45, 23);

        NilaiAMT4.setHighlighter(null);
        NilaiAMT4.setName("NilaiAMT4"); // NOI18N
        FormInput.add(NilaiAMT4);
        NilaiAMT4.setBounds(744, 180, 45, 23);

        NilaiAMT5.setHighlighter(null);
        NilaiAMT5.setName("NilaiAMT5"); // NOI18N
        FormInput.add(NilaiAMT5);
        NilaiAMT5.setBounds(744, 210, 45, 23);

        NilaiAMT6.setHighlighter(null);
        NilaiAMT6.setName("NilaiAMT6"); // NOI18N
        FormInput.add(NilaiAMT6);
        NilaiAMT6.setBounds(744, 240, 45, 23);

        NilaiAMT7.setHighlighter(null);
        NilaiAMT7.setName("NilaiAMT7"); // NOI18N
        FormInput.add(NilaiAMT7);
        NilaiAMT7.setBounds(744, 270, 45, 23);

        NilaiAMT8.setHighlighter(null);
        NilaiAMT8.setName("NilaiAMT8"); // NOI18N
        FormInput.add(NilaiAMT8);
        NilaiAMT8.setBounds(744, 300, 45, 23);

        NilaiAMT9.setHighlighter(null);
        NilaiAMT9.setName("NilaiAMT9"); // NOI18N
        FormInput.add(NilaiAMT9);
        NilaiAMT9.setBounds(744, 330, 45, 23);

        NilaiAMT10.setHighlighter(null);
        NilaiAMT10.setName("NilaiAMT10"); // NOI18N
        FormInput.add(NilaiAMT10);
        NilaiAMT10.setBounds(744, 360, 45, 23);

        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel160.setText("Kesan/Kesimpulan");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(44, 440, 100, 23);

        jLabel161.setText(":");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(0, 440, 140, 23);

        Kesimpulan.setHighlighter(null);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        FormInput.add(Kesimpulan);
        Kesimpulan.setBounds(144, 440, 645, 23);

        jLabel162.setText("Total Nilai AMT :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(610, 390, 130, 23);

        TotalNilai.setHighlighter(null);
        TotalNilai.setName("TotalNilai"); // NOI18N
        FormInput.add(TotalNilai);
        TotalNilai.setBounds(744, 390, 45, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("Berapa umur Anda ?");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(62, 90, 450, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("Jam berapa sekarang ?");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(62, 120, 450, 23);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Dimana alamat rumah Anda ?");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(62, 150, 450, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Tahun berapa sekarang ?");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(62, 180, 450, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Saat ini Kita sedang ada di mana ?");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(62, 210, 490, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Apa nama keluarga Anda ?");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(62, 240, 450, 23);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Tahun berapa indonesia merdeka ?");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(62, 270, 480, 23);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Siapa nama Presiden/Kepala Negara saat ini ?");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(62, 300, 450, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("Tahun berapa Anda lahir ?");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(62, 330, 450, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("Hitung mundur dari 20 ke 1");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(62, 360, 450, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 420, 807, 1);

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("II. INTERPRETASI");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(10, 420, 200, 23);

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
            Valid.pindah(evt,Kesimpulan,BtnBatal);
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NamaPasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>KodePetugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NamaPetugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>AMT10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.AMT10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.Total</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataSkriningInstrumenAMT.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SEKRINING INSTRUMEN AMT<br><br></font>"+        
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

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Alergi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }*/
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed

    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningInstrumenAMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningInstrumenAMTActionPerformed
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
            Valid.MyReportqry("rptFormulirSkriningInstrumenAMT.jasper","report","::[ Formulir Skrining Instrumen AMT ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_instrumen_amt.nip,petugas.nama,skrining_instrumen_amt.tanggal,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "skrining_instrumen_amt.pernyataanamt1,skrining_instrumen_amt.nilai_amt1,skrining_instrumen_amt.pernyataanamt2,skrining_instrumen_amt.nilai_amt2,skrining_instrumen_amt.pernyataanamt3,"+
                    "skrining_instrumen_amt.nilai_amt3,skrining_instrumen_amt.pernyataanamt4,skrining_instrumen_amt.nilai_amt4,skrining_instrumen_amt.pernyataanamt5,skrining_instrumen_amt.nilai_amt5,"+
                    "skrining_instrumen_amt.pernyataanamt6,skrining_instrumen_amt.nilai_amt6,skrining_instrumen_amt.pernyataanamt7,skrining_instrumen_amt.nilai_amt7,skrining_instrumen_amt.pernyataanamt8,"+
                    "skrining_instrumen_amt.nilai_amt8,skrining_instrumen_amt.pernyataanamt9,skrining_instrumen_amt.nilai_amt9,skrining_instrumen_amt.pernyataanamt10,skrining_instrumen_amt.nilai_amt10,"+
                    "skrining_instrumen_amt.nilai_total_amt,skrining_instrumen_amt.kesimpulan from skrining_instrumen_amt inner join reg_periksa on skrining_instrumen_amt.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_instrumen_amt.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningInstrumenAMTActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void AMT1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT1KeyPressed
       Valid.pindah(evt,btnPetugas,AMT2);
    }//GEN-LAST:event_AMT1KeyPressed

    private void AMT2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT2KeyPressed
        Valid.pindah(evt,AMT1,AMT3);
    }//GEN-LAST:event_AMT2KeyPressed

    private void AMT3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT3KeyPressed
        Valid.pindah(evt,AMT2,AMT4);
    }//GEN-LAST:event_AMT3KeyPressed

    private void AMT4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT4KeyPressed
        Valid.pindah(evt,AMT3,AMT5);
    }//GEN-LAST:event_AMT4KeyPressed

    private void AMT5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT5KeyPressed
        Valid.pindah(evt,AMT4,AMT6);
    }//GEN-LAST:event_AMT5KeyPressed

    private void AMT6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT6KeyPressed
        Valid.pindah(evt,AMT5,AMT7);
    }//GEN-LAST:event_AMT6KeyPressed

    private void AMT7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT7KeyPressed
        Valid.pindah(evt,AMT6,AMT8);
    }//GEN-LAST:event_AMT7KeyPressed

    private void AMT8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT8KeyPressed
        Valid.pindah(evt,AMT7,AMT9);
    }//GEN-LAST:event_AMT8KeyPressed

    private void AMT9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT9KeyPressed
        Valid.pindah(evt,AMT8,AMT10);
    }//GEN-LAST:event_AMT9KeyPressed

    private void AMT10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AMT10KeyPressed
        Valid.pindah(evt,AMT9,Kesimpulan);
    }//GEN-LAST:event_AMT10KeyPressed

    private void AMT1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT1ItemStateChanged
        NilaiAMT1.setText(Integer.toString(AMT1.getSelectedIndex()));
        isTotal();
    }//GEN-LAST:event_AMT1ItemStateChanged

    private void AMT2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT2ItemStateChanged
        NilaiAMT2.setText(Integer.toString(AMT2.getSelectedIndex()));
        isTotal();
    }//GEN-LAST:event_AMT2ItemStateChanged

    private void AMT3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT3ItemStateChanged
       NilaiAMT3.setText(Integer.toString(AMT3.getSelectedIndex()));
       isTotal();
    }//GEN-LAST:event_AMT3ItemStateChanged

    private void AMT4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT4ItemStateChanged
        NilaiAMT4.setText(Integer.toString(AMT4.getSelectedIndex()));
        isTotal();
    }//GEN-LAST:event_AMT4ItemStateChanged

    private void AMT5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT5ItemStateChanged
        NilaiAMT5.setText(Integer.toString(AMT5.getSelectedIndex()));
        isTotal();
    }//GEN-LAST:event_AMT5ItemStateChanged

    private void AMT6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT6ItemStateChanged
       NilaiAMT6.setText(Integer.toString(AMT6.getSelectedIndex()));
       isTotal();
    }//GEN-LAST:event_AMT6ItemStateChanged

    private void AMT7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT7ItemStateChanged
        NilaiAMT7.setText(Integer.toString(AMT7.getSelectedIndex()));
        isTotal();
    }//GEN-LAST:event_AMT7ItemStateChanged

    private void AMT8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT8ItemStateChanged
        NilaiAMT8.setText(Integer.toString(AMT8.getSelectedIndex()));
        isTotal();
    }//GEN-LAST:event_AMT8ItemStateChanged

    private void AMT9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT9ItemStateChanged
        NilaiAMT9.setText(Integer.toString(AMT9.getSelectedIndex()));
        isTotal();
    }//GEN-LAST:event_AMT9ItemStateChanged

    private void AMT10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AMT10ItemStateChanged
        NilaiAMT10.setText(Integer.toString(AMT10.getSelectedIndex()));
        isTotal();
    }//GEN-LAST:event_AMT10ItemStateChanged

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        Valid.pindah(evt,AMT10,BtnSimpan);
    }//GEN-LAST:event_KesimpulanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningInstrumenAMT dialog = new RMSkriningInstrumenAMT(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AMT1;
    private widget.ComboBox AMT10;
    private widget.ComboBox AMT2;
    private widget.ComboBox AMT3;
    private widget.ComboBox AMT4;
    private widget.ComboBox AMT5;
    private widget.ComboBox AMT6;
    private widget.ComboBox AMT7;
    private widget.ComboBox AMT8;
    private widget.ComboBox AMT9;
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
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox Kesimpulan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningInstrumenAMT;
    private widget.TextBox NilaiAMT1;
    private widget.TextBox NilaiAMT10;
    private widget.TextBox NilaiAMT2;
    private widget.TextBox NilaiAMT3;
    private widget.TextBox NilaiAMT4;
    private widget.TextBox NilaiAMT5;
    private widget.TextBox NilaiAMT6;
    private widget.TextBox NilaiAMT7;
    private widget.TextBox NilaiAMT8;
    private widget.TextBox NilaiAMT9;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalNilai;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel130;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel16;
    private widget.Label jLabel160;
    private widget.Label jLabel161;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_instrumen_amt.nip,petugas.nama,skrining_instrumen_amt.tanggal,"+
                    "skrining_instrumen_amt.pernyataanamt1,skrining_instrumen_amt.nilai_amt1,skrining_instrumen_amt.pernyataanamt2,skrining_instrumen_amt.nilai_amt2,skrining_instrumen_amt.pernyataanamt3,"+
                    "skrining_instrumen_amt.nilai_amt3,skrining_instrumen_amt.pernyataanamt4,skrining_instrumen_amt.nilai_amt4,skrining_instrumen_amt.pernyataanamt5,skrining_instrumen_amt.nilai_amt5,"+
                    "skrining_instrumen_amt.pernyataanamt6,skrining_instrumen_amt.nilai_amt6,skrining_instrumen_amt.pernyataanamt7,skrining_instrumen_amt.nilai_amt7,skrining_instrumen_amt.pernyataanamt8,"+
                    "skrining_instrumen_amt.nilai_amt8,skrining_instrumen_amt.pernyataanamt9,skrining_instrumen_amt.nilai_amt9,skrining_instrumen_amt.pernyataanamt10,skrining_instrumen_amt.nilai_amt10,"+
                    "skrining_instrumen_amt.nilai_total_amt,skrining_instrumen_amt.kesimpulan from skrining_instrumen_amt inner join reg_periksa on skrining_instrumen_amt.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_instrumen_amt.nip=petugas.nip "+
                    "where skrining_instrumen_amt.tanggal between ? and ? order by skrining_instrumen_amt.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_instrumen_amt.nip,petugas.nama,skrining_instrumen_amt.tanggal,"+
                    "skrining_instrumen_amt.pernyataanamt1,skrining_instrumen_amt.nilai_amt1,skrining_instrumen_amt.pernyataanamt2,skrining_instrumen_amt.nilai_amt2,skrining_instrumen_amt.pernyataanamt3,"+
                    "skrining_instrumen_amt.nilai_amt3,skrining_instrumen_amt.pernyataanamt4,skrining_instrumen_amt.nilai_amt4,skrining_instrumen_amt.pernyataanamt5,skrining_instrumen_amt.nilai_amt5,"+
                    "skrining_instrumen_amt.pernyataanamt6,skrining_instrumen_amt.nilai_amt6,skrining_instrumen_amt.pernyataanamt7,skrining_instrumen_amt.nilai_amt7,skrining_instrumen_amt.pernyataanamt8,"+
                    "skrining_instrumen_amt.nilai_amt8,skrining_instrumen_amt.pernyataanamt9,skrining_instrumen_amt.nilai_amt9,skrining_instrumen_amt.pernyataanamt10,skrining_instrumen_amt.nilai_amt10,"+
                    "skrining_instrumen_amt.nilai_total_amt,skrining_instrumen_amt.kesimpulan from skrining_instrumen_amt inner join reg_periksa on skrining_instrumen_amt.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_instrumen_amt.nip=petugas.nip "+
                    "where skrining_instrumen_amt.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_instrumen_amt.nip like ? or petugas.nama like ?) "+
                    "order by skrining_instrumen_amt.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),
                        rs.getString("pernyataanamt1"),rs.getString("nilai_amt1"),rs.getString("pernyataanamt2"),rs.getString("nilai_amt2"),rs.getString("pernyataanamt3"),rs.getString("nilai_amt3"),rs.getString("pernyataanamt4"),
                        rs.getString("nilai_amt4"),rs.getString("pernyataanamt5"),rs.getString("nilai_amt5"),rs.getString("pernyataanamt6"),rs.getString("nilai_amt6"),rs.getString("pernyataanamt7"),rs.getString("nilai_amt7"),
                        rs.getString("pernyataanamt8"),rs.getString("nilai_amt8"),rs.getString("pernyataanamt9"),rs.getString("nilai_amt9"),rs.getString("pernyataanamt10"),rs.getString("nilai_amt10"),rs.getString("nilai_total_amt"),
                        rs.getString("kesimpulan")
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
        AMT1.setSelectedIndex(0);
        NilaiAMT1.setText("0");
        AMT2.setSelectedIndex(0);
        NilaiAMT2.setText("0");
        AMT3.setSelectedIndex(0);
        NilaiAMT3.setText("0");
        AMT4.setSelectedIndex(0);
        NilaiAMT4.setText("0");
        AMT5.setSelectedIndex(0);
        NilaiAMT5.setText("0");
        AMT6.setSelectedIndex(0);
        NilaiAMT6.setText("0");
        AMT7.setSelectedIndex(0);
        NilaiAMT7.setText("0");
        AMT8.setSelectedIndex(0);
        NilaiAMT8.setText("0");
        AMT9.setSelectedIndex(0);
        NilaiAMT9.setText("0");
        AMT10.setSelectedIndex(0);
        NilaiAMT10.setText("0");
        TotalNilai.setText("0");
        Kesimpulan.setText("Gangguan Ingatan Berat");
        Tanggal.setDate(new Date());
        AMT1.requestFocus();
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
            AMT1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NilaiAMT1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            AMT2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NilaiAMT2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            AMT3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NilaiAMT3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            AMT4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NilaiAMT4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            AMT5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NilaiAMT5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            AMT6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NilaiAMT6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            AMT7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NilaiAMT7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            AMT8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NilaiAMT8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            AMT9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            NilaiAMT9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            AMT10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            NilaiAMT10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            TotalNilai.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
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
        BtnSimpan.setEnabled(akses.getskrining_instrumen_amt());
        BtnHapus.setEnabled(akses.getskrining_instrumen_amt());
        BtnEdit.setEnabled(akses.getskrining_instrumen_amt());
        BtnPrint.setEnabled(akses.getskrining_instrumen_amt()); 
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
        if(Sequel.mengedittf("skrining_instrumen_amt","no_rawat=?","no_rawat=?,tanggal=?,nip=?,pernyataanamt1=?,nilai_amt1=?,pernyataanamt2=?,nilai_amt2=?,pernyataanamt3=?,nilai_amt3=?,pernyataanamt4=?,nilai_amt4=?,pernyataanamt5=?,nilai_amt5=?,"+
                "pernyataanamt6=?,nilai_amt6=?,pernyataanamt7=?,nilai_amt7=?,pernyataanamt8=?,nilai_amt8=?,pernyataanamt9=?,nilai_amt9=?,pernyataanamt10=?,nilai_amt10=?,nilai_total_amt=?,kesimpulan=?",26,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdPetugas.getText(),
                AMT1.getSelectedItem().toString(),NilaiAMT1.getText(),AMT2.getSelectedItem().toString(),NilaiAMT2.getText(),AMT3.getSelectedItem().toString(),NilaiAMT3.getText(),AMT4.getSelectedItem().toString(),NilaiAMT4.getText(), 
                AMT5.getSelectedItem().toString(),NilaiAMT5.getText(),AMT6.getSelectedItem().toString(),NilaiAMT6.getText(),AMT7.getSelectedItem().toString(),NilaiAMT7.getText(),AMT8.getSelectedItem().toString(),NilaiAMT8.getText(), 
                AMT9.getSelectedItem().toString(),NilaiAMT9.getText(),AMT10.getSelectedItem().toString(),NilaiAMT10.getText(),TotalNilai.getText(),Kesimpulan.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(AMT1.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(NilaiAMT1.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(AMT2.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(NilaiAMT2.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(AMT3.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(NilaiAMT3.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(AMT4.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(NilaiAMT4.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(AMT5.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(NilaiAMT5.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(AMT6.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(NilaiAMT6.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(AMT7.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(NilaiAMT7.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(AMT8.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(NilaiAMT8.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(AMT9.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(NilaiAMT9.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(AMT10.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(NilaiAMT10.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(TotalNilai.getText(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(Kesimpulan.getText(),tbObat.getSelectedRow(),29);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_instrumen_amt where no_rawat=?",1,new String[]{
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
        if(Sequel.menyimpantf("skrining_instrumen_amt","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",25,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdPetugas.getText(),
            AMT1.getSelectedItem().toString(),NilaiAMT1.getText(),AMT2.getSelectedItem().toString(),NilaiAMT2.getText(),AMT3.getSelectedItem().toString(),NilaiAMT3.getText(),AMT4.getSelectedItem().toString(),NilaiAMT4.getText(), 
            AMT5.getSelectedItem().toString(),NilaiAMT5.getText(),AMT6.getSelectedItem().toString(),NilaiAMT6.getText(),AMT7.getSelectedItem().toString(),NilaiAMT7.getText(),AMT8.getSelectedItem().toString(),NilaiAMT8.getText(), 
            AMT9.getSelectedItem().toString(),NilaiAMT9.getText(),AMT10.getSelectedItem().toString(),NilaiAMT10.getText(),TotalNilai.getText(),Kesimpulan.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                AMT1.getSelectedItem().toString(),NilaiAMT1.getText(),AMT2.getSelectedItem().toString(),NilaiAMT2.getText(),AMT3.getSelectedItem().toString(),NilaiAMT3.getText(),AMT4.getSelectedItem().toString(),NilaiAMT4.getText(), 
                AMT5.getSelectedItem().toString(),NilaiAMT5.getText(),AMT6.getSelectedItem().toString(),NilaiAMT6.getText(),AMT7.getSelectedItem().toString(),NilaiAMT7.getText(),AMT8.getSelectedItem().toString(),NilaiAMT8.getText(), 
                AMT9.getSelectedItem().toString(),NilaiAMT9.getText(),AMT10.getSelectedItem().toString(),NilaiAMT10.getText(),TotalNilai.getText(),Kesimpulan.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
    
    private void isTotal() {
        try {
            TotalNilai.setText(""+(
                    Integer.parseInt(NilaiAMT1.getText())+Integer.parseInt(NilaiAMT2.getText())+Integer.parseInt(NilaiAMT3.getText())+Integer.parseInt(NilaiAMT4.getText())+Integer.parseInt(NilaiAMT5.getText())+
                    Integer.parseInt(NilaiAMT6.getText())+Integer.parseInt(NilaiAMT7.getText())+Integer.parseInt(NilaiAMT8.getText())+Integer.parseInt(NilaiAMT9.getText())+Integer.parseInt(NilaiAMT10.getText())
            ));
            if((Integer.parseInt(TotalNilai.getText())>=0)&&(Integer.parseInt(TotalNilai.getText())<=3)){
                Kesimpulan.setText("Gangguan Ingatan Berat");
            }else if((Integer.parseInt(TotalNilai.getText())>=4)&&(Integer.parseInt(TotalNilai.getText())<=7)){
                Kesimpulan.setText("Gangguan Ingatan Sedang");
            }else if((Integer.parseInt(TotalNilai.getText())>=8)&&(Integer.parseInt(TotalNilai.getText())<=10)){
                Kesimpulan.setText("Normal");
            }
        } catch (Exception e) {
            Kesimpulan.setText("Gangguan Ingatan Berat");
        }
    }
}

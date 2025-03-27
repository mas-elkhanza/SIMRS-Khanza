/*
 * Kontribusi RSUD Prembun
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
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
import java.sql.SQLException;
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
public final class RMPenilaianLanjutanSkriningFungsional extends javax.swing.JDialog {
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
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianLanjutanSkriningFungsional(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Tgl.Lahir","JK","Tanggal","Skala Barthel 1","N.B. 1","Skala Barthel 2","N.B. 2","Skala Barthel 3","N.B. 3","Skala Barthel 4",
            "N.B. 4","Skala Barthel 5","N.B. 5","Skala Barthel 6","N.B. 6","Skala Barthel 7","N.B. 7","Skala Barthel 8","N.B. 8","Skala Barthel 9","N.B. 9","Skala Barthel 10",
            "N.B. 10","Total","NIP","Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 29; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(40);
            }else if(i==8){
                column.setPreferredWidth(85);
            }else if(i==9){
                column.setPreferredWidth(40);
            }else if(i==10){
                column.setPreferredWidth(85);
            }else if(i==11){
                column.setPreferredWidth(40);
            }else if(i==12){
                column.setPreferredWidth(85);
            }else if(i==13){
                column.setPreferredWidth(40);
            }else if(i==14){
                column.setPreferredWidth(85);
            }else if(i==15){
                column.setPreferredWidth(40);
            }else if(i==16){
                column.setPreferredWidth(85);
            }else if(i==17){
                column.setPreferredWidth(40);
            }else if(i==18){
                column.setPreferredWidth(85);
            }else if(i==19){
                column.setPreferredWidth(40);
            }else if(i==20){
                column.setPreferredWidth(85);
            }else if(i==21){
                column.setPreferredWidth(40);
            }else if(i==22){
                column.setPreferredWidth(85);
            }else if(i==23){
                column.setPreferredWidth(40);
            }else if(i==24){
                column.setPreferredWidth(85);
            }else if(i==25){
                column.setPreferredWidth(40);
            }else if(i==26){
                column.setPreferredWidth(40);
            }else if(i==27){
                column.setPreferredWidth(85);
            }else if(i==28){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
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
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
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
        jam();
        
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
        MnPenilaianLanjutanRisikoJatuh = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
        LoadHTML = new widget.editorpane();
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
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        SkalaResiko1 = new widget.ComboBox();
        jLabel218 = new widget.Label();
        NilaiResiko1 = new widget.TextBox();
        jLabel220 = new widget.Label();
        SkalaResiko2 = new widget.ComboBox();
        jLabel222 = new widget.Label();
        NilaiResiko2 = new widget.TextBox();
        jLabel223 = new widget.Label();
        SkalaResiko3 = new widget.ComboBox();
        jLabel225 = new widget.Label();
        NilaiResiko3 = new widget.TextBox();
        jLabel226 = new widget.Label();
        SkalaResiko4 = new widget.ComboBox();
        jLabel228 = new widget.Label();
        NilaiResiko4 = new widget.TextBox();
        jLabel229 = new widget.Label();
        SkalaResiko5 = new widget.ComboBox();
        jLabel231 = new widget.Label();
        NilaiResiko5 = new widget.TextBox();
        SkalaResiko6 = new widget.ComboBox();
        jLabel234 = new widget.Label();
        NilaiResiko6 = new widget.TextBox();
        jLabel235 = new widget.Label();
        NilaiResikoTotal = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel232 = new widget.Label();
        jLabel236 = new widget.Label();
        SkalaResiko7 = new widget.ComboBox();
        jLabel239 = new widget.Label();
        NilaiResiko7 = new widget.TextBox();
        SkalaResiko8 = new widget.ComboBox();
        jLabel240 = new widget.Label();
        NilaiResiko8 = new widget.TextBox();
        SkalaResiko9 = new widget.ComboBox();
        jLabel241 = new widget.Label();
        NilaiResiko9 = new widget.TextBox();
        SkalaResiko10 = new widget.ComboBox();
        jLabel242 = new widget.Label();
        NilaiResiko10 = new widget.TextBox();
        jLabel230 = new widget.Label();
        jLabel233 = new widget.Label();
        jLabel243 = new widget.Label();
        jLabel244 = new widget.Label();
        jLabel58 = new widget.Label();
        TingkatResiko = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianLanjutanRisikoJatuh.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianLanjutanRisikoJatuh.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianLanjutanRisikoJatuh.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianLanjutanRisikoJatuh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianLanjutanRisikoJatuh.setText("Formulir Penilaian Lanjutan Skrining Fungsional");
        MnPenilaianLanjutanRisikoJatuh.setName("MnPenilaianLanjutanRisikoJatuh"); // NOI18N
        MnPenilaianLanjutanRisikoJatuh.setPreferredSize(new java.awt.Dimension(290, 26));
        MnPenilaianLanjutanRisikoJatuh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianLanjutanRisikoJatuhActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianLanjutanRisikoJatuh);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Lanjutan Skrining Fungsional ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 423));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 80, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 136, 23);

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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(84, 40, 90, 23);

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
        jLabel16.setBounds(0, 40, 80, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(178, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(243, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(308, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(373, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        FormInput.add(NIP);
        NIP.setBounds(474, 40, 94, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(570, 40, 187, 23);

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

        SkalaResiko1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tak Terkendali/Tak Teratur (Perlu Pencahar)", "Kadang-kadang Tak Terkendali (1x Seminggu)", "Terkendali Teratur" }));
        SkalaResiko1.setName("SkalaResiko1"); // NOI18N
        SkalaResiko1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko1ItemStateChanged(evt);
            }
        });
        SkalaResiko1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko1);
        SkalaResiko1.setBounds(340, 90, 330, 23);

        jLabel218.setText("Nilai :");
        jLabel218.setName("jLabel218"); // NOI18N
        FormInput.add(jLabel218);
        jLabel218.setBounds(675, 90, 50, 23);

        NilaiResiko1.setEditable(false);
        NilaiResiko1.setFocusTraversalPolicyProvider(true);
        NilaiResiko1.setName("NilaiResiko1"); // NOI18N
        FormInput.add(NilaiResiko1);
        NilaiResiko1.setBounds(729, 90, 60, 23);

        jLabel220.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel220.setText("2. Mengendalikan Rangsang BAK");
        jLabel220.setName("jLabel220"); // NOI18N
        FormInput.add(jLabel220);
        jLabel220.setBounds(34, 120, 300, 23);

        SkalaResiko2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tak Terkendali/Pakai Kateter", "Kadang-kadang Tak Terkendali (Hanya 1x/24 Jam )", "Mandiri" }));
        SkalaResiko2.setName("SkalaResiko2"); // NOI18N
        SkalaResiko2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko2ItemStateChanged(evt);
            }
        });
        SkalaResiko2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko2);
        SkalaResiko2.setBounds(340, 120, 330, 23);

        jLabel222.setText("Nilai :");
        jLabel222.setName("jLabel222"); // NOI18N
        FormInput.add(jLabel222);
        jLabel222.setBounds(675, 120, 50, 23);

        NilaiResiko2.setEditable(false);
        NilaiResiko2.setFocusTraversalPolicyProvider(true);
        NilaiResiko2.setName("NilaiResiko2"); // NOI18N
        FormInput.add(NilaiResiko2);
        NilaiResiko2.setBounds(729, 120, 60, 23);

        jLabel223.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel223.setText("3. Membersihkan Diri (Seka, Sisir, Sikat Gigi)");
        jLabel223.setName("jLabel223"); // NOI18N
        FormInput.add(jLabel223);
        jLabel223.setBounds(34, 150, 300, 23);

        SkalaResiko3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Butuh Pertolongan Orang Lain", "Mandiri" }));
        SkalaResiko3.setName("SkalaResiko3"); // NOI18N
        SkalaResiko3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko3ItemStateChanged(evt);
            }
        });
        SkalaResiko3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko3KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko3);
        SkalaResiko3.setBounds(340, 150, 330, 23);

        jLabel225.setText("Nilai :");
        jLabel225.setName("jLabel225"); // NOI18N
        FormInput.add(jLabel225);
        jLabel225.setBounds(675, 150, 50, 23);

        NilaiResiko3.setEditable(false);
        NilaiResiko3.setFocusTraversalPolicyProvider(true);
        NilaiResiko3.setName("NilaiResiko3"); // NOI18N
        FormInput.add(NilaiResiko3);
        NilaiResiko3.setBounds(729, 150, 60, 23);

        jLabel226.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel226.setText("4. Menggunakan WC (In/Out, Lepas Pakaian/Celana, Siram)");
        jLabel226.setName("jLabel226"); // NOI18N
        FormInput.add(jLabel226);
        jLabel226.setBounds(34, 180, 300, 23);

        SkalaResiko4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tergantung Pertolongan Orang Lain", "Perlu Pertolongan Pada Beberapa Kegiatan Tetapi Dapat Mengerjakan Sendiri Beberapa Kegiatan Yang Lain", "Mandiri" }));
        SkalaResiko4.setName("SkalaResiko4"); // NOI18N
        SkalaResiko4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko4ItemStateChanged(evt);
            }
        });
        SkalaResiko4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko4KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko4);
        SkalaResiko4.setBounds(340, 180, 330, 23);

        jLabel228.setText("Nilai :");
        jLabel228.setName("jLabel228"); // NOI18N
        FormInput.add(jLabel228);
        jLabel228.setBounds(675, 180, 50, 23);

        NilaiResiko4.setEditable(false);
        NilaiResiko4.setFocusTraversalPolicyProvider(true);
        NilaiResiko4.setName("NilaiResiko4"); // NOI18N
        FormInput.add(NilaiResiko4);
        NilaiResiko4.setBounds(729, 180, 60, 23);

        jLabel229.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel229.setText("10. Mandi");
        jLabel229.setName("jLabel229"); // NOI18N
        FormInput.add(jLabel229);
        jLabel229.setBounds(34, 360, 300, 23);

        SkalaResiko5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mampu", "Perlu Ditolong Memotong Makanan", "Mandiri" }));
        SkalaResiko5.setName("SkalaResiko5"); // NOI18N
        SkalaResiko5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko5ItemStateChanged(evt);
            }
        });
        SkalaResiko5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko5KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko5);
        SkalaResiko5.setBounds(340, 210, 330, 23);

        jLabel231.setText("Nilai :");
        jLabel231.setName("jLabel231"); // NOI18N
        FormInput.add(jLabel231);
        jLabel231.setBounds(675, 210, 50, 23);

        NilaiResiko5.setEditable(false);
        NilaiResiko5.setFocusTraversalPolicyProvider(true);
        NilaiResiko5.setName("NilaiResiko5"); // NOI18N
        FormInput.add(NilaiResiko5);
        NilaiResiko5.setBounds(729, 210, 60, 23);

        SkalaResiko6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mampu", "Perlu Banyak Bantuan Untuk Bisa Duduk (2 Orang)", "Bantuan Minimal 1 Orang", "Mandiri" }));
        SkalaResiko6.setName("SkalaResiko6"); // NOI18N
        SkalaResiko6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko6ItemStateChanged(evt);
            }
        });
        SkalaResiko6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko6KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko6);
        SkalaResiko6.setBounds(340, 240, 330, 23);

        jLabel234.setText("Nilai :");
        jLabel234.setName("jLabel234"); // NOI18N
        FormInput.add(jLabel234);
        jLabel234.setBounds(675, 240, 50, 23);

        NilaiResiko6.setEditable(false);
        NilaiResiko6.setFocusTraversalPolicyProvider(true);
        NilaiResiko6.setName("NilaiResiko6"); // NOI18N
        FormInput.add(NilaiResiko6);
        NilaiResiko6.setBounds(729, 240, 60, 23);

        jLabel235.setText("Total :");
        jLabel235.setName("jLabel235"); // NOI18N
        FormInput.add(jLabel235);
        jLabel235.setBounds(675, 390, 50, 23);

        NilaiResikoTotal.setEditable(false);
        NilaiResikoTotal.setFocusTraversalPolicyProvider(true);
        NilaiResikoTotal.setName("NilaiResikoTotal"); // NOI18N
        FormInput.add(NilaiResikoTotal);
        NilaiResikoTotal.setBounds(729, 390, 60, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 810, 1);

        jLabel232.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel232.setText("5. Makan");
        jLabel232.setName("jLabel232"); // NOI18N
        FormInput.add(jLabel232);
        jLabel232.setBounds(34, 210, 300, 23);

        jLabel236.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel236.setText("1. Mengendalikan Rangsang BAB");
        jLabel236.setName("jLabel236"); // NOI18N
        FormInput.add(jLabel236);
        jLabel236.setBounds(34, 90, 300, 23);

        SkalaResiko7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mampu", "Bisa (Pindah) Dengan Kursi Roda", "Berjalan Dengan Bantuan 1 Orang", "Mandiri" }));
        SkalaResiko7.setName("SkalaResiko7"); // NOI18N
        SkalaResiko7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko7ItemStateChanged(evt);
            }
        });
        SkalaResiko7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko7KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko7);
        SkalaResiko7.setBounds(340, 270, 330, 23);

        jLabel239.setText("Nilai :");
        jLabel239.setName("jLabel239"); // NOI18N
        FormInput.add(jLabel239);
        jLabel239.setBounds(675, 270, 50, 23);

        NilaiResiko7.setEditable(false);
        NilaiResiko7.setFocusTraversalPolicyProvider(true);
        NilaiResiko7.setName("NilaiResiko7"); // NOI18N
        FormInput.add(NilaiResiko7);
        NilaiResiko7.setBounds(729, 270, 60, 23);

        SkalaResiko8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tergantung Orang Lain", "Sebagian Dibantu (Misal Mengancing Baju)", "Mandiri" }));
        SkalaResiko8.setName("SkalaResiko8"); // NOI18N
        SkalaResiko8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko8ItemStateChanged(evt);
            }
        });
        SkalaResiko8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko8KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko8);
        SkalaResiko8.setBounds(340, 300, 330, 23);

        jLabel240.setText("Nilai :");
        jLabel240.setName("jLabel240"); // NOI18N
        FormInput.add(jLabel240);
        jLabel240.setBounds(675, 300, 50, 23);

        NilaiResiko8.setEditable(false);
        NilaiResiko8.setFocusTraversalPolicyProvider(true);
        NilaiResiko8.setName("NilaiResiko8"); // NOI18N
        FormInput.add(NilaiResiko8);
        NilaiResiko8.setBounds(729, 300, 60, 23);

        SkalaResiko9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mampu", "Butuh Pertolongan", "Mandiri" }));
        SkalaResiko9.setName("SkalaResiko9"); // NOI18N
        SkalaResiko9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko9ItemStateChanged(evt);
            }
        });
        SkalaResiko9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko9KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko9);
        SkalaResiko9.setBounds(340, 330, 330, 23);

        jLabel241.setText("Nilai :");
        jLabel241.setName("jLabel241"); // NOI18N
        FormInput.add(jLabel241);
        jLabel241.setBounds(675, 330, 50, 23);

        NilaiResiko9.setEditable(false);
        NilaiResiko9.setFocusTraversalPolicyProvider(true);
        NilaiResiko9.setName("NilaiResiko9"); // NOI18N
        FormInput.add(NilaiResiko9);
        NilaiResiko9.setBounds(729, 330, 60, 23);

        SkalaResiko10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tergantung Orang Lain", "Mandiri" }));
        SkalaResiko10.setName("SkalaResiko10"); // NOI18N
        SkalaResiko10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko10ItemStateChanged(evt);
            }
        });
        SkalaResiko10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko10KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko10);
        SkalaResiko10.setBounds(340, 360, 330, 23);

        jLabel242.setText("Nilai :");
        jLabel242.setName("jLabel242"); // NOI18N
        FormInput.add(jLabel242);
        jLabel242.setBounds(675, 360, 50, 23);

        NilaiResiko10.setEditable(false);
        NilaiResiko10.setFocusTraversalPolicyProvider(true);
        NilaiResiko10.setName("NilaiResiko10"); // NOI18N
        FormInput.add(NilaiResiko10);
        NilaiResiko10.setBounds(729, 360, 60, 23);

        jLabel230.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel230.setText("6. Berubah Sikap Dari Berbaring Ke Duduk");
        jLabel230.setName("jLabel230"); // NOI18N
        FormInput.add(jLabel230);
        jLabel230.setBounds(34, 240, 300, 23);

        jLabel233.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel233.setText("7. Berpindah/Berjalan");
        jLabel233.setName("jLabel233"); // NOI18N
        FormInput.add(jLabel233);
        jLabel233.setBounds(34, 270, 300, 23);

        jLabel243.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel243.setText("8. Mengenakan Pakaian");
        jLabel243.setName("jLabel243"); // NOI18N
        FormInput.add(jLabel243);
        jLabel243.setBounds(34, 300, 300, 23);

        jLabel244.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel244.setText("9. Naik Turun Anak Tangga");
        jLabel244.setName("jLabel244"); // NOI18N
        FormInput.add(jLabel244);
        jLabel244.setBounds(34, 330, 300, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Skala Barthel :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(14, 70, 120, 23);

        TingkatResiko.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko.setText("Keterangan : Ketergantungan Total (0-4)");
        TingkatResiko.setToolTipText("");
        TingkatResiko.setName("TingkatResiko"); // NOI18N
        FormInput.add(TingkatResiko);
        TingkatResiko.setBounds(34, 390, 580, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(Sequel.menyimpantf("penilaian_lanjutan_skrining_fungsional","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",24,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                SkalaResiko1.getSelectedItem().toString(),NilaiResiko1.getText(),SkalaResiko2.getSelectedItem().toString(),NilaiResiko2.getText(),
                SkalaResiko3.getSelectedItem().toString(),NilaiResiko3.getText(),SkalaResiko4.getSelectedItem().toString(),NilaiResiko4.getText(), 
                SkalaResiko5.getSelectedItem().toString(),NilaiResiko5.getText(),SkalaResiko6.getSelectedItem().toString(),NilaiResiko6.getText(), 
                SkalaResiko7.getSelectedItem().toString(),NilaiResiko7.getText(),SkalaResiko8.getSelectedItem().toString(),NilaiResiko8.getText(), 
                SkalaResiko9.getSelectedItem().toString(),NilaiResiko9.getText(),SkalaResiko10.getSelectedItem().toString(),NilaiResiko10.getText(),
                NilaiResikoTotal.getText(),NIP.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    SkalaResiko1.getSelectedItem().toString(),NilaiResiko1.getText(),SkalaResiko2.getSelectedItem().toString(),NilaiResiko2.getText(),SkalaResiko3.getSelectedItem().toString(),NilaiResiko3.getText(),
                    SkalaResiko4.getSelectedItem().toString(),NilaiResiko4.getText(),SkalaResiko5.getSelectedItem().toString(),NilaiResiko5.getText(),SkalaResiko6.getSelectedItem().toString(),NilaiResiko6.getText(),
                    SkalaResiko7.getSelectedItem().toString(),NilaiResiko7.getText(),SkalaResiko8.getSelectedItem().toString(),NilaiResiko8.getText(),SkalaResiko9.getSelectedItem().toString(),NilaiResiko9.getText(),
                    SkalaResiko10.getSelectedItem().toString(),NilaiResiko10.getText(),NilaiResikoTotal.getText(),NIP.getText(),NamaPetugas.getText()
                });
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
            }  
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,SkalaResiko10,BtnBatal);
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
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString())){
                    hapus();
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
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString())){
                        ganti();
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.R.M.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>JK</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skala Barthel 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.B. 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Total</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("PenilaianSkriningFungsional.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN LANJUTAN SKRINING FUNGSIONAL<br><br></font>"+        
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
            TCari.setText("");
            tampil();
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

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Detik,SkalaResiko1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnPenilaianLanjutanRisikoJatuhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianLanjutanRisikoJatuhActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),28).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),27).toString():finger)+"\n"+Tanggal.getSelectedItem());
            Valid.MyReportqry("rptFormulirPenilaianLanjutanSkriningFungsional.jasper","report","::[ Formulir Penilaian Lanjutan Risiko Jatuh Psikiatri ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_lanjutan_skrining_fungsional.tanggal,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala1,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai1,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala2,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai2,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala3,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai3,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala4,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai4,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala5,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai5,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala6,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai6,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala7,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai7,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala8,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai8,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala9,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai9,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala10,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai10,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_totalnilai,"+
                    "penilaian_lanjutan_skrining_fungsional.nip,petugas.nama "+
                    "from penilaian_lanjutan_skrining_fungsional inner join reg_periksa on penilaian_lanjutan_skrining_fungsional.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_lanjutan_skrining_fungsional.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianLanjutanRisikoJatuhActionPerformed

    private void SkalaResiko1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko1ItemStateChanged
        if(SkalaResiko1.getSelectedIndex()==0){
            NilaiResiko1.setText("0");
        }else if(SkalaResiko1.getSelectedIndex()==1){
            NilaiResiko1.setText("1");
        }else{
            NilaiResiko1.setText("2");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko1ItemStateChanged

    private void SkalaResiko1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko1KeyPressed
        Valid.pindah(evt,btnPetugas,SkalaResiko2);
    }//GEN-LAST:event_SkalaResiko1KeyPressed

    private void SkalaResiko2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko2ItemStateChanged
        if(SkalaResiko2.getSelectedIndex()==0){
            NilaiResiko2.setText("0");
        }else if(SkalaResiko2.getSelectedIndex()==1){
            NilaiResiko2.setText("1");
        }else{
            NilaiResiko2.setText("2");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko2ItemStateChanged

    private void SkalaResiko2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko2KeyPressed
        Valid.pindah(evt,SkalaResiko1,SkalaResiko3);
    }//GEN-LAST:event_SkalaResiko2KeyPressed

    private void SkalaResiko3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko3ItemStateChanged
        if(SkalaResiko3.getSelectedIndex()==0){
            NilaiResiko3.setText("0");
        }else{
            NilaiResiko3.setText("1");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko3ItemStateChanged

    private void SkalaResiko3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko3KeyPressed
        Valid.pindah(evt,SkalaResiko2,SkalaResiko4);
    }//GEN-LAST:event_SkalaResiko3KeyPressed

    private void SkalaResiko4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko4ItemStateChanged
        if(SkalaResiko4.getSelectedIndex()==0){
            NilaiResiko4.setText("0");
        }else if(SkalaResiko4.getSelectedIndex()==1){
            NilaiResiko4.setText("1");
        }else{
            NilaiResiko4.setText("2");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko4ItemStateChanged

    private void SkalaResiko4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko4KeyPressed
        Valid.pindah(evt,SkalaResiko3,SkalaResiko5);
    }//GEN-LAST:event_SkalaResiko4KeyPressed

    private void SkalaResiko5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko5ItemStateChanged
        if(SkalaResiko5.getSelectedIndex()==0){
            NilaiResiko5.setText("0");
        }else if(SkalaResiko5.getSelectedIndex()==1){
            NilaiResiko5.setText("1");
        }else{
            NilaiResiko5.setText("2");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko5ItemStateChanged

    private void SkalaResiko5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko5KeyPressed
        Valid.pindah(evt,SkalaResiko4,SkalaResiko6);
    }//GEN-LAST:event_SkalaResiko5KeyPressed

    private void SkalaResiko6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko6ItemStateChanged
        if(SkalaResiko6.getSelectedIndex()==0){
            NilaiResiko6.setText("0");
        }else if(SkalaResiko6.getSelectedIndex()==1){
            NilaiResiko6.setText("1");
        }else if(SkalaResiko6.getSelectedIndex()==2){
            NilaiResiko6.setText("2");
        }else{
            NilaiResiko6.setText("3");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko6ItemStateChanged

    private void SkalaResiko6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko6KeyPressed
        Valid.pindah(evt,SkalaResiko5,SkalaResiko7);
    }//GEN-LAST:event_SkalaResiko6KeyPressed

    private void SkalaResiko7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko7ItemStateChanged
         if(SkalaResiko7.getSelectedIndex()==0){
            NilaiResiko7.setText("0");
        }else if(SkalaResiko7.getSelectedIndex()==1){
            NilaiResiko7.setText("1");
        }else if(SkalaResiko7.getSelectedIndex()==2){
            NilaiResiko7.setText("2");
        }else{
            NilaiResiko7.setText("3");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko7ItemStateChanged

    private void SkalaResiko7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko7KeyPressed
        Valid.pindah(evt,SkalaResiko6,SkalaResiko8);
    }//GEN-LAST:event_SkalaResiko7KeyPressed

    private void SkalaResiko8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko8ItemStateChanged
        if(SkalaResiko8.getSelectedIndex()==0){
            NilaiResiko8.setText("0");
        }else if(SkalaResiko8.getSelectedIndex()==1){
            NilaiResiko8.setText("1");
        }else{
            NilaiResiko8.setText("2");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko8ItemStateChanged

    private void SkalaResiko8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko8KeyPressed
        Valid.pindah(evt,SkalaResiko7,SkalaResiko9);
    }//GEN-LAST:event_SkalaResiko8KeyPressed

    private void SkalaResiko9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko9ItemStateChanged
        if(SkalaResiko9.getSelectedIndex()==0){
            NilaiResiko9.setText("0");
        }else if(SkalaResiko9.getSelectedIndex()==1){
            NilaiResiko9.setText("1");
        }else{
            NilaiResiko9.setText("2");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko9ItemStateChanged

    private void SkalaResiko9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko9KeyPressed
        Valid.pindah(evt,SkalaResiko8,SkalaResiko10);
    }//GEN-LAST:event_SkalaResiko9KeyPressed

    private void SkalaResiko10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko10ItemStateChanged
        if(SkalaResiko10.getSelectedIndex()==0){
            NilaiResiko10.setText("0");
        }else{
            NilaiResiko10.setText("1");
        }
        isTotalResikoJatuh();     // TODO add your handling code here:
    }//GEN-LAST:event_SkalaResiko10ItemStateChanged

    private void SkalaResiko10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko10KeyPressed
        Valid.pindah(evt,SkalaResiko9,BtnSimpan);
    }//GEN-LAST:event_SkalaResiko10KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianLanjutanSkriningFungsional dialog = new RMPenilaianLanjutanSkriningFungsional(new javax.swing.JFrame(), true);
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
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnPenilaianLanjutanRisikoJatuh;
    private widget.TextBox NIP;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NilaiResiko1;
    private widget.TextBox NilaiResiko10;
    private widget.TextBox NilaiResiko2;
    private widget.TextBox NilaiResiko3;
    private widget.TextBox NilaiResiko4;
    private widget.TextBox NilaiResiko5;
    private widget.TextBox NilaiResiko6;
    private widget.TextBox NilaiResiko7;
    private widget.TextBox NilaiResiko8;
    private widget.TextBox NilaiResiko9;
    private widget.TextBox NilaiResikoTotal;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SkalaResiko1;
    private widget.ComboBox SkalaResiko10;
    private widget.ComboBox SkalaResiko2;
    private widget.ComboBox SkalaResiko3;
    private widget.ComboBox SkalaResiko4;
    private widget.ComboBox SkalaResiko5;
    private widget.ComboBox SkalaResiko6;
    private widget.ComboBox SkalaResiko7;
    private widget.ComboBox SkalaResiko8;
    private widget.ComboBox SkalaResiko9;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.Label TingkatResiko;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel218;
    private widget.Label jLabel220;
    private widget.Label jLabel222;
    private widget.Label jLabel223;
    private widget.Label jLabel225;
    private widget.Label jLabel226;
    private widget.Label jLabel228;
    private widget.Label jLabel229;
    private widget.Label jLabel230;
    private widget.Label jLabel231;
    private widget.Label jLabel232;
    private widget.Label jLabel233;
    private widget.Label jLabel234;
    private widget.Label jLabel235;
    private widget.Label jLabel236;
    private widget.Label jLabel239;
    private widget.Label jLabel240;
    private widget.Label jLabel241;
    private widget.Label jLabel242;
    private widget.Label jLabel243;
    private widget.Label jLabel244;
    private widget.Label jLabel4;
    private widget.Label jLabel58;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_lanjutan_skrining_fungsional.tanggal,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala1,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai1,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala2,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai2,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala3,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai3,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala4,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai4,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala5,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai5,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala6,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai6,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala7,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai7,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala8,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai8,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala9,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai9,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala10,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai10,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_totalnilai,"+
                    "penilaian_lanjutan_skrining_fungsional.nip,petugas.nama "+
                    "from penilaian_lanjutan_skrining_fungsional inner join reg_periksa on penilaian_lanjutan_skrining_fungsional.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_lanjutan_skrining_fungsional.nip=petugas.nip where "+
                    "penilaian_lanjutan_skrining_fungsional.tanggal between ? and ? order by penilaian_lanjutan_skrining_fungsional.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_lanjutan_skrining_fungsional.tanggal,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala1,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai1,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala2,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai2,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala3,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai3,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala4,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai4,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala5,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai5,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala6,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai6,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala7,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai7,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala8,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai8,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala9,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai9,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_skala10,penilaian_lanjutan_skrining_fungsional.penilaian_skrining_nilai10,"+
                    "penilaian_lanjutan_skrining_fungsional.penilaian_skrining_totalnilai,"+
                    "penilaian_lanjutan_skrining_fungsional.nip,petugas.nama "+
                    "from penilaian_lanjutan_skrining_fungsional inner join reg_periksa on penilaian_lanjutan_skrining_fungsional.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_lanjutan_skrining_fungsional.nip=petugas.nip where "+
                    "penilaian_lanjutan_skrining_fungsional.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_lanjutan_skrining_fungsional.nip like ? or petugas.nama like ?) "+
                    "order by penilaian_lanjutan_skrining_fungsional.tanggal ");
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
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal"),
                        rs.getString("penilaian_skrining_skala1"),rs.getString("penilaian_skrining_nilai1"),rs.getString("penilaian_skrining_skala2"),rs.getString("penilaian_skrining_nilai2"),
                        rs.getString("penilaian_skrining_skala3"),rs.getString("penilaian_skrining_nilai3"),rs.getString("penilaian_skrining_skala4"),rs.getString("penilaian_skrining_nilai4"),
                        rs.getString("penilaian_skrining_skala5"),rs.getString("penilaian_skrining_nilai5"),rs.getString("penilaian_skrining_skala6"),rs.getString("penilaian_skrining_nilai6"),
                        rs.getString("penilaian_skrining_skala7"),rs.getString("penilaian_skrining_nilai7"),rs.getString("penilaian_skrining_skala8"),rs.getString("penilaian_skrining_nilai8"),
                        rs.getString("penilaian_skrining_skala9"),rs.getString("penilaian_skrining_nilai9"),rs.getString("penilaian_skrining_skala10"),rs.getString("penilaian_skrining_nilai10"),
                        rs.getString("penilaian_skrining_totalnilai"),rs.getString("nip"),rs.getString("nama")
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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void emptTeks() {
        Tanggal.setDate(new Date());
        SkalaResiko1.setSelectedIndex(0);
        NilaiResiko1.setText("0");
        SkalaResiko2.setSelectedIndex(0);
        NilaiResiko2.setText("0");
        SkalaResiko3.setSelectedIndex(0);
        NilaiResiko3.setText("0");
        SkalaResiko4.setSelectedIndex(0);
        NilaiResiko4.setText("0");
        SkalaResiko5.setSelectedIndex(0);
        NilaiResiko5.setText("0");
        SkalaResiko6.setSelectedIndex(0);
        NilaiResiko6.setText("0");
        SkalaResiko7.setSelectedIndex(0);
        NilaiResiko7.setText("0");
        SkalaResiko8.setSelectedIndex(0);
        NilaiResiko8.setText("0");
        SkalaResiko9.setSelectedIndex(0);
        NilaiResiko9.setText("0");
        SkalaResiko10.setSelectedIndex(0);
        NilaiResiko10.setText("0");
        NilaiResikoTotal.setText("0");
        SkalaResiko1.requestFocus();
    } 
    

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            SkalaResiko1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NilaiResiko1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            SkalaResiko2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NilaiResiko2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            SkalaResiko3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NilaiResiko3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            SkalaResiko4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NilaiResiko4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            SkalaResiko5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NilaiResiko5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            SkalaResiko6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NilaiResiko6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            SkalaResiko7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NilaiResiko7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            SkalaResiko8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NilaiResiko8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            SkalaResiko9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NilaiResiko9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            SkalaResiko10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            NilaiResiko10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            NilaiResikoTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(14,16));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(17,19));
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        try {
            ps=koneksi.prepareStatement("select pasien.nm_pasien,pasien.jk,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir from pasien where pasien.no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }finally {
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
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>618){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,446));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_lanjutan_skrining_fungsional());
        BtnHapus.setEnabled(akses.getpenilaian_lanjutan_skrining_fungsional());
        BtnEdit.setEnabled(akses.getpenilaian_lanjutan_skrining_fungsional());
        BtnPrint.setEnabled(akses.getpenilaian_lanjutan_skrining_fungsional()); 
        if(akses.getjml2()>=1){
            NIP.setEditable(false);
            btnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
            if(NamaPetugas.getText().equals("")){
                NIP.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
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
        if(Sequel.mengedittf("penilaian_lanjutan_skrining_fungsional","tanggal=? and no_rawat=?","no_rawat=?,tanggal=?,penilaian_skrining_skala1=?,penilaian_skrining_nilai1=?,"+
                "penilaian_skrining_skala2=?,penilaian_skrining_nilai2=?,penilaian_skrining_skala3=?,penilaian_skrining_nilai3=?,penilaian_skrining_skala4=?,"+
                "penilaian_skrining_nilai4=?,penilaian_skrining_skala5=?,penilaian_skrining_nilai5=?,penilaian_skrining_skala6=?,penilaian_skrining_nilai6=?,"+
                "penilaian_skrining_skala7=?,penilaian_skrining_nilai7=?,penilaian_skrining_skala8=?,penilaian_skrining_nilai8=?,"+
                "penilaian_skrining_skala9=?,penilaian_skrining_nilai9=?,penilaian_skrining_skala10=?,penilaian_skrining_nilai10=?,"+
                "penilaian_skrining_totalnilai=?,nip=?",26,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                SkalaResiko1.getSelectedItem().toString(),NilaiResiko1.getText(),SkalaResiko2.getSelectedItem().toString(),NilaiResiko2.getText(),
                SkalaResiko3.getSelectedItem().toString(),NilaiResiko3.getText(),SkalaResiko4.getSelectedItem().toString(),NilaiResiko4.getText(), 
                SkalaResiko5.getSelectedItem().toString(),NilaiResiko5.getText(),SkalaResiko6.getSelectedItem().toString(),NilaiResiko6.getText(), 
                SkalaResiko7.getSelectedItem().toString(),NilaiResiko7.getText(),SkalaResiko8.getSelectedItem().toString(),NilaiResiko8.getText(), 
                SkalaResiko9.getSelectedItem().toString(),NilaiResiko9.getText(),SkalaResiko10.getSelectedItem().toString(),NilaiResiko10.getText(),
                NilaiResikoTotal.getText(),NIP.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(SkalaResiko1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(NilaiResiko1.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(SkalaResiko2.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NilaiResiko2.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(SkalaResiko3.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(NilaiResiko3.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(SkalaResiko4.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(NilaiResiko4.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(SkalaResiko5.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(NilaiResiko5.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(SkalaResiko6.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(NilaiResiko6.getText(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(SkalaResiko7.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(NilaiResiko7.getText(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(SkalaResiko8.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(NilaiResiko8.getText(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(SkalaResiko9.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(NilaiResiko9.getText(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(SkalaResiko10.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(NilaiResiko10.getText(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(NilaiResikoTotal.getText(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),28);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_lanjutan_skrining_fungsional where tanggal=? and no_rawat=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            emptTeks();
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void isTotalResikoJatuh(){
        try {
            NilaiResikoTotal.setText((Integer.parseInt(NilaiResiko1.getText())+Integer.parseInt(NilaiResiko2.getText())+Integer.parseInt(NilaiResiko3.getText())+Integer.parseInt(NilaiResiko4.getText())+Integer.parseInt(NilaiResiko5.getText())+Integer.parseInt(NilaiResiko6.getText())+Integer.parseInt(NilaiResiko7.getText())+Integer.parseInt(NilaiResiko8.getText())+Integer.parseInt(NilaiResiko9.getText())+Integer.parseInt(NilaiResiko10.getText()))+"");
            if(Integer.parseInt(NilaiResikoTotal.getText())<=4){
                TingkatResiko.setText("Keterangan : Ketergantungan Total (0-4)");
            }else if(Integer.parseInt(NilaiResikoTotal.getText())>4 && Integer.parseInt(NilaiResikoTotal.getText())<9){
                TingkatResiko.setText("Keterangan : Ketergantungan Berat (5-8)");
            }else if(Integer.parseInt(NilaiResikoTotal.getText())>8 && Integer.parseInt(NilaiResikoTotal.getText())<12){
                TingkatResiko.setText("Keterangan : Ketergantungan Sedang (9-11)");
            }else if(Integer.parseInt(NilaiResikoTotal.getText())>11 && Integer.parseInt(NilaiResikoTotal.getText())<20){
                TingkatResiko.setText("Keterangan : Ketergantungan Ringan (12-19)");
            }else if(Integer.parseInt(NilaiResikoTotal.getText())>19){
                TingkatResiko.setText("Keterangan : Mandiri (20)");
            }
        } catch (Exception e) {
            NilaiResikoTotal.setText("0");
            TingkatResiko.setText("Keterangan : Ketergantungan Total (0-4)");
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public final class RMPenilaianTambahanPerilakuKekerasan extends javax.swing.JDialog {
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
    public RMPenilaianTambahanPerilakuKekerasan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Tgl.Lahir","JK","Tanggal","Faktor Statik 1","Skor Statik 1","Faktor Statik 2","Skor Statik 2",
            "Faktor Statik 3","Skor Statik 3","Faktor Statik 4","Skor Statik 4","Faktor Statik 5","Skor Statik 5","Faktor Statik 6","Skor Statik 6",
            "Faktor Statik 7","Skor Statik 7","Faktor Statik 8","Skor Statik 8","Faktor Statik 9","Skor Statik 9","Jml Skor Statik",
            "Faktor Dinamis 1","Skor Dinamis 1", "Faktor Dinamis 2","Skor Dinamis 2","Faktor Dinamis 3","Skor Dinamis 3","Faktor Dinamis 4","Skor Dinamis 4",
            "Faktor Dinamis 5","Skor Dinamis 5","Faktor Dinamis 6","Skor Dinamis 6","Faktor Dinamis 7","Skor Dinamis 7","Faktor Dinamis 8","Skor Dinamis 8",
            "Faktor Dinamis 9","Skor Dinamis 9","Jml Skor Dinamis","Faktor-faktor Pencegahan","Total Skor","Level Skor","NIP","Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 49; i++) {
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
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(80);
            }else if(i==15){
                column.setPreferredWidth(70);
            }else if(i==16){
                column.setPreferredWidth(80);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(70);
            }else if(i==20){
                column.setPreferredWidth(80);
            }else if(i==21){
                column.setPreferredWidth(70);
            }else if(i==22){
                column.setPreferredWidth(80);
            }else if(i==23){
                column.setPreferredWidth(70);
            }else if(i==24){
                column.setPreferredWidth(80);
            }else if(i==25){
                column.setPreferredWidth(90);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(90);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(90);
            }else if(i==30){
                column.setPreferredWidth(80);
            }else if(i==31){
                column.setPreferredWidth(90);
            }else if(i==32){
                column.setPreferredWidth(80);
            }else if(i==33){
                column.setPreferredWidth(90);
            }else if(i==34){
                column.setPreferredWidth(80);
            }else if(i==35){
                column.setPreferredWidth(90);
            }else if(i==36){
                column.setPreferredWidth(80);
            }else if(i==37){
                column.setPreferredWidth(90);
            }else if(i==38){
                column.setPreferredWidth(80);
            }else if(i==39){
                column.setPreferredWidth(90);
            }else if(i==40){
                column.setPreferredWidth(80);
            }else if(i==41){
                column.setPreferredWidth(90);
            }else if(i==42){
                column.setPreferredWidth(80);
            }else if(i==43){
                column.setPreferredWidth(90);
            }else if(i==44){
                column.setPreferredWidth(200);
            }else if(i==45){
                column.setPreferredWidth(60);
            }else if(i==46){
                column.setPreferredWidth(75);
            }else if(i==47){
                column.setPreferredWidth(80);
            }else if(i==48){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
        FaktorPencegahan.setDocument(new batasInput((int)500).getKata(FaktorPencegahan));
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
        MnPenilaianTambahanPerilakuKekerasan = new javax.swing.JMenuItem();
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
        jLabel57 = new widget.Label();
        jLabel217 = new widget.Label();
        FaktorStatik1 = new widget.ComboBox();
        jLabel218 = new widget.Label();
        SkorStatik1 = new widget.TextBox();
        jLabel220 = new widget.Label();
        FaktorStatik2 = new widget.ComboBox();
        jLabel222 = new widget.Label();
        SkorStatik2 = new widget.TextBox();
        jLabel223 = new widget.Label();
        FaktorStatik3 = new widget.ComboBox();
        jLabel225 = new widget.Label();
        SkorStatik3 = new widget.TextBox();
        jLabel226 = new widget.Label();
        FaktorStatik4 = new widget.ComboBox();
        jLabel228 = new widget.Label();
        SkorStatik4 = new widget.TextBox();
        jLabel229 = new widget.Label();
        FaktorStatik5 = new widget.ComboBox();
        jLabel231 = new widget.Label();
        SkorStatik5 = new widget.TextBox();
        jLabel232 = new widget.Label();
        FaktorStatik6 = new widget.ComboBox();
        jLabel234 = new widget.Label();
        SkorStatik6 = new widget.TextBox();
        jLabel235 = new widget.Label();
        TotalDinamis = new widget.TextBox();
        jLabel30 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        FaktorPencegahan = new widget.TextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel236 = new widget.Label();
        FaktorStatik7 = new widget.ComboBox();
        jLabel238 = new widget.Label();
        SkorStatik7 = new widget.TextBox();
        SkorDinamis7 = new widget.TextBox();
        jLabel239 = new widget.Label();
        SkorDinamis6 = new widget.TextBox();
        jLabel237 = new widget.Label();
        jLabel233 = new widget.Label();
        SkorDinamis5 = new widget.TextBox();
        SkorDinamis4 = new widget.TextBox();
        jLabel230 = new widget.Label();
        jLabel227 = new widget.Label();
        SkorDinamis3 = new widget.TextBox();
        SkorDinamis2 = new widget.TextBox();
        jLabel224 = new widget.Label();
        SkorDinamis1 = new widget.TextBox();
        jLabel219 = new widget.Label();
        FaktorDinamis1 = new widget.ComboBox();
        FaktorDinamis2 = new widget.ComboBox();
        FaktorDinamis3 = new widget.ComboBox();
        FaktorDinamis4 = new widget.ComboBox();
        FaktorDinamis5 = new widget.ComboBox();
        FaktorDinamis6 = new widget.ComboBox();
        FaktorDinamis7 = new widget.ComboBox();
        jLabel240 = new widget.Label();
        jLabel241 = new widget.Label();
        jLabel242 = new widget.Label();
        jLabel243 = new widget.Label();
        jLabel244 = new widget.Label();
        jLabel221 = new widget.Label();
        jLabel245 = new widget.Label();
        jLabel58 = new widget.Label();
        TotalStatik = new widget.TextBox();
        jLabel246 = new widget.Label();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel31 = new widget.Label();
        SkorTotal = new widget.Label();
        Level = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel247 = new widget.Label();
        FaktorStatik8 = new widget.ComboBox();
        jLabel248 = new widget.Label();
        SkorStatik8 = new widget.TextBox();
        jLabel249 = new widget.Label();
        FaktorDinamis8 = new widget.ComboBox();
        jLabel250 = new widget.Label();
        SkorDinamis8 = new widget.TextBox();
        jLabel251 = new widget.Label();
        FaktorStatik9 = new widget.ComboBox();
        jLabel252 = new widget.Label();
        SkorStatik9 = new widget.TextBox();
        jLabel253 = new widget.Label();
        FaktorDinamis9 = new widget.ComboBox();
        jLabel254 = new widget.Label();
        SkorDinamis9 = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianTambahanPerilakuKekerasan.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianTambahanPerilakuKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianTambahanPerilakuKekerasan.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianTambahanPerilakuKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianTambahanPerilakuKekerasan.setText("Formulir Pengkajian Tambahan Perilaku Kekerasan");
        MnPenilaianTambahanPerilakuKekerasan.setName("MnPenilaianTambahanPerilakuKekerasan"); // NOI18N
        MnPenilaianTambahanPerilakuKekerasan.setPreferredSize(new java.awt.Dimension(290, 26));
        MnPenilaianTambahanPerilakuKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianTambahanPerilakuKekerasanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianTambahanPerilakuKekerasan);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Tambahan Perilaku Kekerasan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-04-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-04-2023" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 456));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 493));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-04-2023" }));
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

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Faktor Statik :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(14, 70, 80, 23);

        jLabel217.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel217.setText("Insiden Kekerasan Baru-baru Ini");
        jLabel217.setName("jLabel217"); // NOI18N
        FormInput.add(jLabel217);
        jLabel217.setBounds(24, 90, 190, 23);

        FaktorStatik1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorStatik1.setName("FaktorStatik1"); // NOI18N
        FaktorStatik1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorStatik1ItemStateChanged(evt);
            }
        });
        FaktorStatik1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorStatik1KeyPressed(evt);
            }
        });
        FormInput.add(FaktorStatik1);
        FaktorStatik1.setBounds(208, 90, 105, 23);

        jLabel218.setText("Skor :");
        jLabel218.setName("jLabel218"); // NOI18N
        FormInput.add(jLabel218);
        jLabel218.setBounds(315, 90, 40, 23);

        SkorStatik1.setEditable(false);
        SkorStatik1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStatik1.setText("0");
        SkorStatik1.setFocusTraversalPolicyProvider(true);
        SkorStatik1.setName("SkorStatik1"); // NOI18N
        FormInput.add(SkorStatik1);
        SkorStatik1.setBounds(359, 90, 35, 23);

        jLabel220.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel220.setText("Riwayat Penggunaan Senjata");
        jLabel220.setName("jLabel220"); // NOI18N
        FormInput.add(jLabel220);
        jLabel220.setBounds(24, 120, 190, 23);

        FaktorStatik2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorStatik2.setName("FaktorStatik2"); // NOI18N
        FaktorStatik2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorStatik2ItemStateChanged(evt);
            }
        });
        FaktorStatik2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorStatik2KeyPressed(evt);
            }
        });
        FormInput.add(FaktorStatik2);
        FaktorStatik2.setBounds(208, 120, 105, 23);

        jLabel222.setText("Skor :");
        jLabel222.setName("jLabel222"); // NOI18N
        FormInput.add(jLabel222);
        jLabel222.setBounds(315, 120, 40, 23);

        SkorStatik2.setEditable(false);
        SkorStatik2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStatik2.setText("0");
        SkorStatik2.setFocusTraversalPolicyProvider(true);
        SkorStatik2.setName("SkorStatik2"); // NOI18N
        FormInput.add(SkorStatik2);
        SkorStatik2.setBounds(359, 120, 35, 23);

        jLabel223.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel223.setText("Laki-laki");
        jLabel223.setName("jLabel223"); // NOI18N
        FormInput.add(jLabel223);
        jLabel223.setBounds(24, 150, 190, 23);

        FaktorStatik3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorStatik3.setName("FaktorStatik3"); // NOI18N
        FaktorStatik3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorStatik3ItemStateChanged(evt);
            }
        });
        FaktorStatik3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorStatik3KeyPressed(evt);
            }
        });
        FormInput.add(FaktorStatik3);
        FaktorStatik3.setBounds(208, 150, 105, 23);

        jLabel225.setText("Skor :");
        jLabel225.setName("jLabel225"); // NOI18N
        FormInput.add(jLabel225);
        jLabel225.setBounds(315, 150, 40, 23);

        SkorStatik3.setEditable(false);
        SkorStatik3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStatik3.setText("0");
        SkorStatik3.setFocusTraversalPolicyProvider(true);
        SkorStatik3.setName("SkorStatik3"); // NOI18N
        FormInput.add(SkorStatik3);
        SkorStatik3.setBounds(359, 150, 35, 23);

        jLabel226.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel226.setText("Usia Dibawah 35 Tahun");
        jLabel226.setName("jLabel226"); // NOI18N
        FormInput.add(jLabel226);
        jLabel226.setBounds(24, 180, 190, 23);

        FaktorStatik4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorStatik4.setName("FaktorStatik4"); // NOI18N
        FaktorStatik4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorStatik4ItemStateChanged(evt);
            }
        });
        FaktorStatik4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorStatik4KeyPressed(evt);
            }
        });
        FormInput.add(FaktorStatik4);
        FaktorStatik4.setBounds(208, 180, 105, 23);

        jLabel228.setText("Skor :");
        jLabel228.setName("jLabel228"); // NOI18N
        FormInput.add(jLabel228);
        jLabel228.setBounds(315, 180, 40, 23);

        SkorStatik4.setEditable(false);
        SkorStatik4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStatik4.setText("0");
        SkorStatik4.setFocusTraversalPolicyProvider(true);
        SkorStatik4.setName("SkorStatik4"); // NOI18N
        FormInput.add(SkorStatik4);
        SkorStatik4.setBounds(359, 180, 35, 23);

        jLabel229.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel229.setText("Riwayat Kriminal");
        jLabel229.setName("jLabel229"); // NOI18N
        FormInput.add(jLabel229);
        jLabel229.setBounds(24, 210, 190, 23);

        FaktorStatik5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorStatik5.setName("FaktorStatik5"); // NOI18N
        FaktorStatik5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorStatik5ItemStateChanged(evt);
            }
        });
        FaktorStatik5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorStatik5KeyPressed(evt);
            }
        });
        FormInput.add(FaktorStatik5);
        FaktorStatik5.setBounds(208, 210, 105, 23);

        jLabel231.setText("Skor :");
        jLabel231.setName("jLabel231"); // NOI18N
        FormInput.add(jLabel231);
        jLabel231.setBounds(315, 210, 40, 23);

        SkorStatik5.setEditable(false);
        SkorStatik5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStatik5.setText("0");
        SkorStatik5.setFocusTraversalPolicyProvider(true);
        SkorStatik5.setName("SkorStatik5"); // NOI18N
        FormInput.add(SkorStatik5);
        SkorStatik5.setBounds(359, 210, 35, 23);

        jLabel232.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel232.setText("Riwayat Tindakan Berbahaya");
        jLabel232.setName("jLabel232"); // NOI18N
        FormInput.add(jLabel232);
        jLabel232.setBounds(24, 240, 190, 23);

        FaktorStatik6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorStatik6.setName("FaktorStatik6"); // NOI18N
        FaktorStatik6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorStatik6ItemStateChanged(evt);
            }
        });
        FaktorStatik6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorStatik6KeyPressed(evt);
            }
        });
        FormInput.add(FaktorStatik6);
        FaktorStatik6.setBounds(208, 240, 105, 23);

        jLabel234.setText("Skor :");
        jLabel234.setName("jLabel234"); // NOI18N
        FormInput.add(jLabel234);
        jLabel234.setBounds(315, 240, 40, 23);

        SkorStatik6.setEditable(false);
        SkorStatik6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStatik6.setText("0");
        SkorStatik6.setFocusTraversalPolicyProvider(true);
        SkorStatik6.setName("SkorStatik6"); // NOI18N
        FormInput.add(SkorStatik6);
        SkorStatik6.setBounds(359, 240, 35, 23);

        jLabel235.setText("Jumlah Skor :");
        jLabel235.setName("jLabel235"); // NOI18N
        FormInput.add(jLabel235);
        jLabel235.setBounds(675, 360, 70, 23);

        TotalDinamis.setEditable(false);
        TotalDinamis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TotalDinamis.setText("0");
        TotalDinamis.setFocusTraversalPolicyProvider(true);
        TotalDinamis.setName("TotalDinamis"); // NOI18N
        FormInput.add(TotalDinamis);
        TotalDinamis.setBounds(749, 360, 40, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Faktor-faktor Pencegahan :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(14, 390, 310, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        FaktorPencegahan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        FaktorPencegahan.setColumns(20);
        FaktorPencegahan.setRows(5);
        FaktorPencegahan.setName("FaktorPencegahan"); // NOI18N
        FaktorPencegahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorPencegahanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(FaktorPencegahan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(24, 410, 765, 43);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 810, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 390, 810, 1);

        jLabel236.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel236.setText("Kekerasan Masa Kanak-kanak");
        jLabel236.setName("jLabel236"); // NOI18N
        FormInput.add(jLabel236);
        jLabel236.setBounds(24, 270, 190, 23);

        FaktorStatik7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorStatik7.setName("FaktorStatik7"); // NOI18N
        FaktorStatik7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorStatik7ItemStateChanged(evt);
            }
        });
        FaktorStatik7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorStatik7KeyPressed(evt);
            }
        });
        FormInput.add(FaktorStatik7);
        FaktorStatik7.setBounds(208, 270, 105, 23);

        jLabel238.setText("Skor :");
        jLabel238.setName("jLabel238"); // NOI18N
        FormInput.add(jLabel238);
        jLabel238.setBounds(315, 270, 40, 23);

        SkorStatik7.setEditable(false);
        SkorStatik7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStatik7.setText("0");
        SkorStatik7.setFocusTraversalPolicyProvider(true);
        SkorStatik7.setName("SkorStatik7"); // NOI18N
        FormInput.add(SkorStatik7);
        SkorStatik7.setBounds(359, 270, 35, 23);

        SkorDinamis7.setEditable(false);
        SkorDinamis7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDinamis7.setText("0");
        SkorDinamis7.setFocusTraversalPolicyProvider(true);
        SkorDinamis7.setName("SkorDinamis7"); // NOI18N
        FormInput.add(SkorDinamis7);
        SkorDinamis7.setBounds(754, 270, 35, 23);

        jLabel239.setText("Skor :");
        jLabel239.setName("jLabel239"); // NOI18N
        FormInput.add(jLabel239);
        jLabel239.setBounds(710, 270, 40, 23);

        SkorDinamis6.setEditable(false);
        SkorDinamis6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDinamis6.setText("0");
        SkorDinamis6.setFocusTraversalPolicyProvider(true);
        SkorDinamis6.setName("SkorDinamis6"); // NOI18N
        FormInput.add(SkorDinamis6);
        SkorDinamis6.setBounds(754, 240, 35, 23);

        jLabel237.setText("Skor :");
        jLabel237.setName("jLabel237"); // NOI18N
        FormInput.add(jLabel237);
        jLabel237.setBounds(710, 240, 40, 23);

        jLabel233.setText("Skor :");
        jLabel233.setName("jLabel233"); // NOI18N
        FormInput.add(jLabel233);
        jLabel233.setBounds(710, 210, 40, 23);

        SkorDinamis5.setEditable(false);
        SkorDinamis5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDinamis5.setText("0");
        SkorDinamis5.setFocusTraversalPolicyProvider(true);
        SkorDinamis5.setName("SkorDinamis5"); // NOI18N
        FormInput.add(SkorDinamis5);
        SkorDinamis5.setBounds(754, 210, 35, 23);

        SkorDinamis4.setEditable(false);
        SkorDinamis4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDinamis4.setText("0");
        SkorDinamis4.setFocusTraversalPolicyProvider(true);
        SkorDinamis4.setName("SkorDinamis4"); // NOI18N
        FormInput.add(SkorDinamis4);
        SkorDinamis4.setBounds(754, 180, 35, 23);

        jLabel230.setText("Skor :");
        jLabel230.setName("jLabel230"); // NOI18N
        FormInput.add(jLabel230);
        jLabel230.setBounds(710, 180, 40, 23);

        jLabel227.setText("Skor :");
        jLabel227.setName("jLabel227"); // NOI18N
        FormInput.add(jLabel227);
        jLabel227.setBounds(710, 150, 40, 23);

        SkorDinamis3.setEditable(false);
        SkorDinamis3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDinamis3.setText("0");
        SkorDinamis3.setFocusTraversalPolicyProvider(true);
        SkorDinamis3.setName("SkorDinamis3"); // NOI18N
        FormInput.add(SkorDinamis3);
        SkorDinamis3.setBounds(754, 150, 35, 23);

        SkorDinamis2.setEditable(false);
        SkorDinamis2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDinamis2.setText("0");
        SkorDinamis2.setFocusTraversalPolicyProvider(true);
        SkorDinamis2.setName("SkorDinamis2"); // NOI18N
        FormInput.add(SkorDinamis2);
        SkorDinamis2.setBounds(754, 120, 35, 23);

        jLabel224.setText("Skor :");
        jLabel224.setName("jLabel224"); // NOI18N
        FormInput.add(jLabel224);
        jLabel224.setBounds(710, 120, 40, 23);

        SkorDinamis1.setEditable(false);
        SkorDinamis1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDinamis1.setText("0");
        SkorDinamis1.setFocusTraversalPolicyProvider(true);
        SkorDinamis1.setName("SkorDinamis1"); // NOI18N
        FormInput.add(SkorDinamis1);
        SkorDinamis1.setBounds(754, 90, 35, 23);

        jLabel219.setText("Skor :");
        jLabel219.setName("jLabel219"); // NOI18N
        FormInput.add(jLabel219);
        jLabel219.setBounds(710, 90, 40, 23);

        FaktorDinamis1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorDinamis1.setName("FaktorDinamis1"); // NOI18N
        FaktorDinamis1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorDinamis1ItemStateChanged(evt);
            }
        });
        FaktorDinamis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorDinamis1KeyPressed(evt);
            }
        });
        FormInput.add(FaktorDinamis1);
        FaktorDinamis1.setBounds(605, 90, 105, 23);

        FaktorDinamis2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorDinamis2.setName("FaktorDinamis2"); // NOI18N
        FaktorDinamis2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorDinamis2ItemStateChanged(evt);
            }
        });
        FaktorDinamis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorDinamis2KeyPressed(evt);
            }
        });
        FormInput.add(FaktorDinamis2);
        FaktorDinamis2.setBounds(605, 120, 105, 23);

        FaktorDinamis3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorDinamis3.setName("FaktorDinamis3"); // NOI18N
        FaktorDinamis3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorDinamis3ItemStateChanged(evt);
            }
        });
        FaktorDinamis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorDinamis3KeyPressed(evt);
            }
        });
        FormInput.add(FaktorDinamis3);
        FaktorDinamis3.setBounds(605, 150, 105, 23);

        FaktorDinamis4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorDinamis4.setName("FaktorDinamis4"); // NOI18N
        FaktorDinamis4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorDinamis4ItemStateChanged(evt);
            }
        });
        FaktorDinamis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorDinamis4KeyPressed(evt);
            }
        });
        FormInput.add(FaktorDinamis4);
        FaktorDinamis4.setBounds(605, 180, 105, 23);

        FaktorDinamis5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorDinamis5.setName("FaktorDinamis5"); // NOI18N
        FaktorDinamis5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorDinamis5ItemStateChanged(evt);
            }
        });
        FaktorDinamis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorDinamis5KeyPressed(evt);
            }
        });
        FormInput.add(FaktorDinamis5);
        FaktorDinamis5.setBounds(605, 210, 105, 23);

        FaktorDinamis6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorDinamis6.setName("FaktorDinamis6"); // NOI18N
        FaktorDinamis6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorDinamis6ItemStateChanged(evt);
            }
        });
        FaktorDinamis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorDinamis6KeyPressed(evt);
            }
        });
        FormInput.add(FaktorDinamis6);
        FaktorDinamis6.setBounds(605, 240, 105, 23);

        FaktorDinamis7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorDinamis7.setName("FaktorDinamis7"); // NOI18N
        FaktorDinamis7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorDinamis7ItemStateChanged(evt);
            }
        });
        FaktorDinamis7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorDinamis7KeyPressed(evt);
            }
        });
        FormInput.add(FaktorDinamis7);
        FaktorDinamis7.setBounds(605, 270, 105, 23);

        jLabel240.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel240.setText("Perilaku Seksual Yang Tidak Wajar");
        jLabel240.setName("jLabel240"); // NOI18N
        FormInput.add(jLabel240);
        jLabel240.setBounds(415, 270, 194, 23);

        jLabel241.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel241.setText("Kesenangan Ide/Tindakan Kekerasan");
        jLabel241.setName("jLabel241"); // NOI18N
        FormInput.add(jLabel241);
        jLabel241.setBounds(415, 240, 194, 23);

        jLabel242.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel242.setText("Kemarahan, Frustasi Atau Agitasi");
        jLabel242.setName("jLabel242"); // NOI18N
        FormInput.add(jLabel242);
        jLabel242.setBounds(415, 210, 194, 23);

        jLabel243.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel243.setText("Perintah Halusinasi Untuk Kekerasan");
        jLabel243.setName("jLabel243"); // NOI18N
        FormInput.add(jLabel243);
        jLabel243.setBounds(415, 180, 194, 23);

        jLabel244.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel244.setText("Ide Paranoid Atau Lainnya");
        jLabel244.setName("jLabel244"); // NOI18N
        FormInput.add(jLabel244);
        jLabel244.setBounds(415, 150, 194, 23);

        jLabel221.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel221.setText("Akses Melakukan Tindakan Kekerasan");
        jLabel221.setName("jLabel221"); // NOI18N
        FormInput.add(jLabel221);
        jLabel221.setBounds(415, 120, 194, 23);

        jLabel245.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel245.setText("Mengekspresikan Ide Melukai Orang ");
        jLabel245.setName("jLabel245"); // NOI18N
        FormInput.add(jLabel245);
        jLabel245.setBounds(415, 90, 194, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Faktor Dinamis :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(405, 70, 80, 23);

        TotalStatik.setEditable(false);
        TotalStatik.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TotalStatik.setText("0");
        TotalStatik.setFocusTraversalPolicyProvider(true);
        TotalStatik.setName("TotalStatik"); // NOI18N
        FormInput.add(TotalStatik);
        TotalStatik.setBounds(354, 360, 40, 23);

        jLabel246.setText("Jumlah Skor :");
        jLabel246.setName("jLabel246"); // NOI18N
        FormInput.add(jLabel246);
        jLabel246.setBounds(280, 360, 70, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 460, 810, 1);

        jLabel31.setText(":");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 460, 197, 23);

        SkorTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SkorTotal.setText("0");
        SkorTotal.setName("SkorTotal"); // NOI18N
        FormInput.add(SkorTotal);
        SkorTotal.setBounds(203, 460, 50, 23);

        Level.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Level.setText("Level");
        Level.setName("Level"); // NOI18N
        FormInput.add(Level);
        Level.setBounds(350, 460, 160, 23);

        jLabel34.setText("Level :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(285, 460, 60, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Total Skor Risiko Perilaku Kekerasan");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(14, 460, 190, 23);

        jLabel247.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel247.setText("Kurangnya Peran Dalam Hidup");
        jLabel247.setName("jLabel247"); // NOI18N
        FormInput.add(jLabel247);
        jLabel247.setBounds(24, 300, 190, 23);

        FaktorStatik8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorStatik8.setName("FaktorStatik8"); // NOI18N
        FaktorStatik8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorStatik8ItemStateChanged(evt);
            }
        });
        FaktorStatik8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorStatik8KeyPressed(evt);
            }
        });
        FormInput.add(FaktorStatik8);
        FaktorStatik8.setBounds(208, 300, 105, 23);

        jLabel248.setText("Skor :");
        jLabel248.setName("jLabel248"); // NOI18N
        FormInput.add(jLabel248);
        jLabel248.setBounds(315, 300, 40, 23);

        SkorStatik8.setEditable(false);
        SkorStatik8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStatik8.setText("0");
        SkorStatik8.setFocusTraversalPolicyProvider(true);
        SkorStatik8.setName("SkorStatik8"); // NOI18N
        FormInput.add(SkorStatik8);
        SkorStatik8.setBounds(359, 300, 35, 23);

        jLabel249.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel249.setText("Berkurang/Hilangnya Kontrol Diri");
        jLabel249.setName("jLabel249"); // NOI18N
        FormInput.add(jLabel249);
        jLabel249.setBounds(415, 300, 194, 23);

        FaktorDinamis8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorDinamis8.setName("FaktorDinamis8"); // NOI18N
        FaktorDinamis8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorDinamis8ItemStateChanged(evt);
            }
        });
        FaktorDinamis8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorDinamis8KeyPressed(evt);
            }
        });
        FormInput.add(FaktorDinamis8);
        FaktorDinamis8.setBounds(605, 300, 105, 23);

        jLabel250.setText("Skor :");
        jLabel250.setName("jLabel250"); // NOI18N
        FormInput.add(jLabel250);
        jLabel250.setBounds(710, 300, 40, 23);

        SkorDinamis8.setEditable(false);
        SkorDinamis8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDinamis8.setText("0");
        SkorDinamis8.setFocusTraversalPolicyProvider(true);
        SkorDinamis8.setName("SkorDinamis8"); // NOI18N
        FormInput.add(SkorDinamis8);
        SkorDinamis8.setBounds(754, 300, 35, 23);

        jLabel251.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel251.setText("Riwayat Penggunaan NAPZA");
        jLabel251.setName("jLabel251"); // NOI18N
        FormInput.add(jLabel251);
        jLabel251.setBounds(24, 330, 190, 23);

        FaktorStatik9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorStatik9.setName("FaktorStatik9"); // NOI18N
        FaktorStatik9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorStatik9ItemStateChanged(evt);
            }
        });
        FaktorStatik9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorStatik9KeyPressed(evt);
            }
        });
        FormInput.add(FaktorStatik9);
        FaktorStatik9.setBounds(208, 330, 105, 23);

        jLabel252.setText("Skor :");
        jLabel252.setName("jLabel252"); // NOI18N
        FormInput.add(jLabel252);
        jLabel252.setBounds(315, 330, 40, 23);

        SkorStatik9.setEditable(false);
        SkorStatik9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorStatik9.setText("0");
        SkorStatik9.setFocusTraversalPolicyProvider(true);
        SkorStatik9.setName("SkorStatik9"); // NOI18N
        FormInput.add(SkorStatik9);
        SkorStatik9.setBounds(359, 330, 35, 23);

        jLabel253.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel253.setText("Penggunaan Napza");
        jLabel253.setName("jLabel253"); // NOI18N
        FormInput.add(jLabel253);
        jLabel253.setBounds(415, 330, 194, 23);

        FaktorDinamis9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        FaktorDinamis9.setName("FaktorDinamis9"); // NOI18N
        FaktorDinamis9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorDinamis9ItemStateChanged(evt);
            }
        });
        FaktorDinamis9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorDinamis9KeyPressed(evt);
            }
        });
        FormInput.add(FaktorDinamis9);
        FaktorDinamis9.setBounds(605, 330, 105, 23);

        jLabel254.setText("Skor :");
        jLabel254.setName("jLabel254"); // NOI18N
        FormInput.add(jLabel254);
        jLabel254.setBounds(710, 330, 40, 23);

        SkorDinamis9.setEditable(false);
        SkorDinamis9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SkorDinamis9.setText("0");
        SkorDinamis9.setFocusTraversalPolicyProvider(true);
        SkorDinamis9.setName("SkorDinamis9"); // NOI18N
        FormInput.add(SkorDinamis9);
        SkorDinamis9.setBounds(754, 330, 35, 23);

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
            Valid.textKosong(TNoRw,"pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(Sequel.menyimpantf("penilaian_tambahan_perilaku_kekerasan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",44,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),NIP.getText(),
                FaktorStatik1.getSelectedItem().toString(),SkorStatik1.getText(),FaktorStatik2.getSelectedItem().toString(),SkorStatik2.getText(),FaktorStatik3.getSelectedItem().toString(),SkorStatik3.getText(),
                FaktorStatik4.getSelectedItem().toString(),SkorStatik4.getText(),FaktorStatik5.getSelectedItem().toString(),SkorStatik5.getText(),FaktorStatik6.getSelectedItem().toString(),SkorStatik6.getText(), 
                FaktorStatik7.getSelectedItem().toString(),SkorStatik7.getText(),FaktorStatik8.getSelectedItem().toString(),SkorStatik8.getText(),FaktorStatik9.getSelectedItem().toString(),SkorStatik9.getText(),
                TotalStatik.getText(),FaktorDinamis1.getSelectedItem().toString(),SkorDinamis1.getText(),FaktorDinamis2.getSelectedItem().toString(),SkorDinamis2.getText(),FaktorDinamis3.getSelectedItem().toString(),
                SkorDinamis3.getText(),FaktorDinamis4.getSelectedItem().toString(),SkorDinamis4.getText(),FaktorDinamis5.getSelectedItem().toString(),SkorDinamis5.getText(),FaktorDinamis6.getSelectedItem().toString(),
                SkorDinamis6.getText(),FaktorDinamis7.getSelectedItem().toString(),SkorDinamis7.getText(),FaktorDinamis8.getSelectedItem().toString(),SkorDinamis8.getText(),FaktorDinamis9.getSelectedItem().toString(),
                SkorDinamis9.getText(),TotalDinamis.getText(),FaktorPencegahan.getText(),SkorTotal.getText(),Level.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    FaktorStatik1.getSelectedItem().toString(),SkorStatik1.getText(),FaktorStatik2.getSelectedItem().toString(),SkorStatik2.getText(),FaktorStatik3.getSelectedItem().toString(),SkorStatik3.getText(),
                    FaktorStatik4.getSelectedItem().toString(),SkorStatik4.getText(),FaktorStatik5.getSelectedItem().toString(),SkorStatik5.getText(),FaktorStatik6.getSelectedItem().toString(),SkorStatik6.getText(), 
                    FaktorStatik7.getSelectedItem().toString(),SkorStatik7.getText(),FaktorStatik8.getSelectedItem().toString(),SkorStatik8.getText(),FaktorStatik9.getSelectedItem().toString(),SkorStatik9.getText(),
                    TotalStatik.getText(),FaktorDinamis1.getSelectedItem().toString(),SkorDinamis1.getText(),FaktorDinamis2.getSelectedItem().toString(),SkorDinamis2.getText(),FaktorDinamis3.getSelectedItem().toString(),
                    SkorDinamis3.getText(),FaktorDinamis4.getSelectedItem().toString(),SkorDinamis4.getText(),FaktorDinamis5.getSelectedItem().toString(),SkorDinamis5.getText(),FaktorDinamis6.getSelectedItem().toString(),
                    SkorDinamis6.getText(),FaktorDinamis7.getSelectedItem().toString(),SkorDinamis7.getText(),FaktorDinamis8.getSelectedItem().toString(),SkorDinamis8.getText(),FaktorDinamis9.getSelectedItem().toString(),
                    SkorDinamis9.getText(),TotalDinamis.getText(),FaktorPencegahan.getText(),SkorTotal.getText(),Level.getText(),NIP.getText(),NamaPetugas.getText()
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
            Valid.pindah(evt,FaktorPencegahan,BtnBatal);
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
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString())){
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
            Valid.textKosong(TNoRw,"pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString())){
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Statik 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Statik 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Statik 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Statik 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Statik 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Statik 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Statik 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Statik 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Statik 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Statik 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Statik 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Statik 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Statik 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Statik 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Statik 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Statik 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Statik 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Statik 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jml Skor Statik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Dinamis 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Dinamis 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Dinamis 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Dinamis 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Dinamis 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Dinamis 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Dinamis 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Dinamis 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Dinamis 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Dinamis 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Dinamis 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Dinamis 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Dinamis 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Dinamis 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Dinamis 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Dinamis 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor Dinamis 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Skor Dinamis 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jml Skor Dinamis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Faktor-faktor Pencegahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Total Skor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Level Skor</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianTambahanPerilakuKekerasan.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENGKAJIAN TAMBAHAN PERILAKU KEKERASAN<br><br></font>"+        
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
        Valid.pindah(evt,Detik,FaktorStatik1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnPenilaianTambahanPerilakuKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianTambahanPerilakuKekerasanActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),48).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),47).toString():finger)+"\n"+Tanggal.getSelectedItem());
            Valid.MyReportqry("rptFormulirPenilaianTambahanPerilakuKekerasan.jasper","report","::[ Formulir Pengkajian Tambahan Perilaku Kekerasan ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_tambahan_perilaku_kekerasan.tanggal,date_format(reg_periksa.tgl_registrasi,'%d-%m-%Y')as tgl_registrasi,reg_periksa.jam_reg,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_insiden_kekerasan_baru_ini,penilaian_tambahan_perilaku_kekerasan.statik_skorinsiden_kekerasan_baru_ini,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_riwayat_penggunaan_senjata,penilaian_tambahan_perilaku_kekerasan.statik_skorriwayat_penggunaan_senjata,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_laki_laki,penilaian_tambahan_perilaku_kekerasan.statik_skorlaki_laki,penilaian_tambahan_perilaku_kekerasan.statik_usia_dibawah_35,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_skorusia_dibawah_35,penilaian_tambahan_perilaku_kekerasan.statik_riwayat_kriminal,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_skorriwayat_kriminal,penilaian_tambahan_perilaku_kekerasan.statik_ide_kekerasan,penilaian_tambahan_perilaku_kekerasan.statik_skoride_kekerasan,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_kekerasan_anak_anak,penilaian_tambahan_perilaku_kekerasan.statik_skorkekerasan_anak_anak,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_peran_dalam_hidup,penilaian_tambahan_perilaku_kekerasan.statik_skorperan_dalam_hidup,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_penggunaan_napza,penilaian_tambahan_perilaku_kekerasan.statik_skorpenggunaan_napza,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_skortotal,penilaian_tambahan_perilaku_kekerasan.dinamis_ide_melukai_orang_lain,penilaian_tambahan_perilaku_kekerasan.dinamis_skoride_melukai_orang_lain,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_akses_kekerasan,penilaian_tambahan_perilaku_kekerasan.dinamis_skorakses_kekerasan,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_ide_paranoid,penilaian_tambahan_perilaku_kekerasan.dinamis_skoride_paranoid,penilaian_tambahan_perilaku_kekerasan.dinamis_perintah_halusinasi,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorperintah_halusinasi,penilaian_tambahan_perilaku_kekerasan.dinamis_frustasi_agitasi,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorfrustasi_agitasi,penilaian_tambahan_perilaku_kekerasan.dinamis_kesenangan_kekerasan,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorkesenangan_kekerasan,penilaian_tambahan_perilaku_kekerasan.dinamis_seksual_tidak_wajar,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorseksual_tidak_wajar,penilaian_tambahan_perilaku_kekerasan.dinamis_hilangnya_kontrol_diri,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorhilangnya_kontrol_diri,penilaian_tambahan_perilaku_kekerasan.dinamis_pengguaan_napza,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorpengguaan_napza,penilaian_tambahan_perilaku_kekerasan.dinamis_skortotal,penilaian_tambahan_perilaku_kekerasan.faktor_faktor_pencegahan,"+
                    "penilaian_tambahan_perilaku_kekerasan.total_skor,penilaian_tambahan_perilaku_kekerasan.level_skor,penilaian_tambahan_perilaku_kekerasan.nip,petugas.nama,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from penilaian_tambahan_perilaku_kekerasan inner join reg_periksa on penilaian_tambahan_perilaku_kekerasan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join petugas on penilaian_tambahan_perilaku_kekerasan.nip=petugas.nip inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianTambahanPerilakuKekerasanActionPerformed

    private void FaktorStatik1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorStatik1ItemStateChanged
        if(FaktorStatik1.getSelectedIndex()==1){
            SkorStatik1.setText("1");
        }else{
            SkorStatik1.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorStatik1ItemStateChanged

    private void FaktorStatik1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorStatik1KeyPressed
        Valid.pindah(evt,btnPetugas,FaktorStatik2);
    }//GEN-LAST:event_FaktorStatik1KeyPressed

    private void FaktorStatik2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorStatik2ItemStateChanged
        if(FaktorStatik2.getSelectedIndex()==1){
            SkorStatik2.setText("1");
        }else{
            SkorStatik2.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorStatik2ItemStateChanged

    private void FaktorStatik2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorStatik2KeyPressed
        Valid.pindah(evt,FaktorStatik1,FaktorStatik3);
    }//GEN-LAST:event_FaktorStatik2KeyPressed

    private void FaktorStatik3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorStatik3ItemStateChanged
        if(FaktorStatik3.getSelectedIndex()==1){
            SkorStatik3.setText("1");
        }else{
            SkorStatik3.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorStatik3ItemStateChanged

    private void FaktorStatik3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorStatik3KeyPressed
        Valid.pindah(evt,FaktorStatik2,FaktorStatik4);
    }//GEN-LAST:event_FaktorStatik3KeyPressed

    private void FaktorStatik4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorStatik4ItemStateChanged
        if(FaktorStatik4.getSelectedIndex()==1){
            SkorStatik4.setText("1");
        }else{
            SkorStatik4.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorStatik4ItemStateChanged

    private void FaktorStatik4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorStatik4KeyPressed
        Valid.pindah(evt,FaktorStatik3,FaktorStatik5);
    }//GEN-LAST:event_FaktorStatik4KeyPressed

    private void FaktorStatik5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorStatik5ItemStateChanged
        if(FaktorStatik5.getSelectedIndex()==1){
            SkorStatik5.setText("1");
        }else{
            SkorStatik5.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorStatik5ItemStateChanged

    private void FaktorStatik5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorStatik5KeyPressed
        Valid.pindah(evt,FaktorStatik4,FaktorStatik6);
    }//GEN-LAST:event_FaktorStatik5KeyPressed

    private void FaktorStatik6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorStatik6ItemStateChanged
        if(FaktorStatik6.getSelectedIndex()==1){
            SkorStatik6.setText("1");
        }else{
            SkorStatik6.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorStatik6ItemStateChanged

    private void FaktorStatik6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorStatik6KeyPressed
        Valid.pindah(evt,FaktorStatik5,FaktorStatik7);
    }//GEN-LAST:event_FaktorStatik6KeyPressed

    private void FaktorPencegahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorPencegahanKeyPressed
        Valid.pindah2(evt,FaktorDinamis7,BtnSimpan);
    }//GEN-LAST:event_FaktorPencegahanKeyPressed

    private void FaktorStatik7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorStatik7ItemStateChanged
        if(FaktorStatik7.getSelectedIndex()==1){
            SkorStatik7.setText("1");
        }else{
            SkorStatik7.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorStatik7ItemStateChanged

    private void FaktorStatik7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorStatik7KeyPressed
        Valid.pindah(evt,FaktorStatik6,FaktorStatik8);
    }//GEN-LAST:event_FaktorStatik7KeyPressed

    private void FaktorDinamis1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorDinamis1ItemStateChanged
        if(FaktorDinamis1.getSelectedIndex()==1){
            SkorDinamis1.setText("2");
        }else{
            SkorDinamis1.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorDinamis1ItemStateChanged

    private void FaktorDinamis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorDinamis1KeyPressed
        Valid.pindah(evt,FaktorStatik9,FaktorDinamis2);
    }//GEN-LAST:event_FaktorDinamis1KeyPressed

    private void FaktorDinamis2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorDinamis2ItemStateChanged
        if(FaktorDinamis2.getSelectedIndex()==1){
            SkorDinamis2.setText("2");
        }else{
            SkorDinamis2.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorDinamis2ItemStateChanged

    private void FaktorDinamis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorDinamis2KeyPressed
        Valid.pindah(evt,FaktorDinamis1,FaktorDinamis3);
    }//GEN-LAST:event_FaktorDinamis2KeyPressed

    private void FaktorDinamis3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorDinamis3ItemStateChanged
        if(FaktorDinamis3.getSelectedIndex()==1){
            SkorDinamis3.setText("2");
        }else{
            SkorDinamis3.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorDinamis3ItemStateChanged

    private void FaktorDinamis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorDinamis3KeyPressed
        Valid.pindah(evt,FaktorDinamis2,FaktorDinamis4);
    }//GEN-LAST:event_FaktorDinamis3KeyPressed

    private void FaktorDinamis4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorDinamis4ItemStateChanged
        if(FaktorDinamis4.getSelectedIndex()==1){
            SkorDinamis4.setText("2");
        }else{
            SkorDinamis4.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorDinamis4ItemStateChanged

    private void FaktorDinamis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorDinamis4KeyPressed
        Valid.pindah(evt,FaktorDinamis3,FaktorDinamis5);
    }//GEN-LAST:event_FaktorDinamis4KeyPressed

    private void FaktorDinamis5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorDinamis5ItemStateChanged
        if(FaktorDinamis5.getSelectedIndex()==1){
            SkorDinamis5.setText("2");
        }else{
            SkorDinamis5.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorDinamis5ItemStateChanged

    private void FaktorDinamis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorDinamis5KeyPressed
        Valid.pindah(evt,FaktorDinamis4,FaktorDinamis6);
    }//GEN-LAST:event_FaktorDinamis5KeyPressed

    private void FaktorDinamis6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorDinamis6ItemStateChanged
        if(FaktorDinamis6.getSelectedIndex()==1){
            SkorDinamis6.setText("2");
        }else{
            SkorDinamis6.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorDinamis6ItemStateChanged

    private void FaktorDinamis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorDinamis6KeyPressed
        Valid.pindah(evt,FaktorDinamis5,FaktorDinamis7);
    }//GEN-LAST:event_FaktorDinamis6KeyPressed

    private void FaktorDinamis7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorDinamis7ItemStateChanged
        if(FaktorDinamis7.getSelectedIndex()==1){
            SkorDinamis7.setText("2");
        }else{
            SkorDinamis7.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorDinamis7ItemStateChanged

    private void FaktorDinamis7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorDinamis7KeyPressed
        Valid.pindah(evt,FaktorDinamis6,FaktorDinamis8);
    }//GEN-LAST:event_FaktorDinamis7KeyPressed

    private void FaktorStatik8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorStatik8ItemStateChanged
        if(FaktorStatik8.getSelectedIndex()==1){
            SkorStatik8.setText("1");
        }else{
            SkorStatik8.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorStatik8ItemStateChanged

    private void FaktorStatik8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorStatik8KeyPressed
        Valid.pindah(evt,FaktorStatik7,FaktorStatik9);
    }//GEN-LAST:event_FaktorStatik8KeyPressed

    private void FaktorDinamis8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorDinamis8ItemStateChanged
        if(FaktorDinamis8.getSelectedIndex()==1){
            SkorDinamis8.setText("2");
        }else{
            SkorDinamis8.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorDinamis8ItemStateChanged

    private void FaktorDinamis8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorDinamis8KeyPressed
        Valid.pindah(evt,FaktorDinamis7,FaktorDinamis9);
    }//GEN-LAST:event_FaktorDinamis8KeyPressed

    private void FaktorStatik9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorStatik9ItemStateChanged
        if(FaktorStatik9.getSelectedIndex()==1){
            SkorStatik9.setText("1");
        }else{
            SkorStatik9.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorStatik9ItemStateChanged

    private void FaktorStatik9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorStatik9KeyPressed
        Valid.pindah(evt,FaktorStatik8,FaktorDinamis1);
    }//GEN-LAST:event_FaktorStatik9KeyPressed

    private void FaktorDinamis9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorDinamis9ItemStateChanged
        if(FaktorDinamis9.getSelectedIndex()==1){
            SkorDinamis9.setText("2");
        }else{
            SkorDinamis9.setText("0");
        }
        isTotalSkor();
    }//GEN-LAST:event_FaktorDinamis9ItemStateChanged

    private void FaktorDinamis9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorDinamis9KeyPressed
        Valid.pindah(evt,FaktorDinamis8,FaktorPencegahan);
    }//GEN-LAST:event_FaktorDinamis9KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianTambahanPerilakuKekerasan dialog = new RMPenilaianTambahanPerilakuKekerasan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox FaktorDinamis1;
    private widget.ComboBox FaktorDinamis2;
    private widget.ComboBox FaktorDinamis3;
    private widget.ComboBox FaktorDinamis4;
    private widget.ComboBox FaktorDinamis5;
    private widget.ComboBox FaktorDinamis6;
    private widget.ComboBox FaktorDinamis7;
    private widget.ComboBox FaktorDinamis8;
    private widget.ComboBox FaktorDinamis9;
    private widget.TextArea FaktorPencegahan;
    private widget.ComboBox FaktorStatik1;
    private widget.ComboBox FaktorStatik2;
    private widget.ComboBox FaktorStatik3;
    private widget.ComboBox FaktorStatik4;
    private widget.ComboBox FaktorStatik5;
    private widget.ComboBox FaktorStatik6;
    private widget.ComboBox FaktorStatik7;
    private widget.ComboBox FaktorStatik8;
    private widget.ComboBox FaktorStatik9;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.Label LCount;
    private widget.Label Level;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnPenilaianTambahanPerilakuKekerasan;
    private widget.TextBox NIP;
    private widget.TextBox NamaPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox SkorDinamis1;
    private widget.TextBox SkorDinamis2;
    private widget.TextBox SkorDinamis3;
    private widget.TextBox SkorDinamis4;
    private widget.TextBox SkorDinamis5;
    private widget.TextBox SkorDinamis6;
    private widget.TextBox SkorDinamis7;
    private widget.TextBox SkorDinamis8;
    private widget.TextBox SkorDinamis9;
    private widget.TextBox SkorStatik1;
    private widget.TextBox SkorStatik2;
    private widget.TextBox SkorStatik3;
    private widget.TextBox SkorStatik4;
    private widget.TextBox SkorStatik5;
    private widget.TextBox SkorStatik6;
    private widget.TextBox SkorStatik7;
    private widget.TextBox SkorStatik8;
    private widget.TextBox SkorStatik9;
    private widget.Label SkorTotal;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalDinamis;
    private widget.TextBox TotalStatik;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel217;
    private widget.Label jLabel218;
    private widget.Label jLabel219;
    private widget.Label jLabel220;
    private widget.Label jLabel221;
    private widget.Label jLabel222;
    private widget.Label jLabel223;
    private widget.Label jLabel224;
    private widget.Label jLabel225;
    private widget.Label jLabel226;
    private widget.Label jLabel227;
    private widget.Label jLabel228;
    private widget.Label jLabel229;
    private widget.Label jLabel230;
    private widget.Label jLabel231;
    private widget.Label jLabel232;
    private widget.Label jLabel233;
    private widget.Label jLabel234;
    private widget.Label jLabel235;
    private widget.Label jLabel236;
    private widget.Label jLabel237;
    private widget.Label jLabel238;
    private widget.Label jLabel239;
    private widget.Label jLabel240;
    private widget.Label jLabel241;
    private widget.Label jLabel242;
    private widget.Label jLabel243;
    private widget.Label jLabel244;
    private widget.Label jLabel245;
    private widget.Label jLabel246;
    private widget.Label jLabel247;
    private widget.Label jLabel248;
    private widget.Label jLabel249;
    private widget.Label jLabel250;
    private widget.Label jLabel251;
    private widget.Label jLabel252;
    private widget.Label jLabel253;
    private widget.Label jLabel254;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel4;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_tambahan_perilaku_kekerasan.tanggal,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_insiden_kekerasan_baru_ini,penilaian_tambahan_perilaku_kekerasan.statik_skorinsiden_kekerasan_baru_ini,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_riwayat_penggunaan_senjata,penilaian_tambahan_perilaku_kekerasan.statik_skorriwayat_penggunaan_senjata,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_laki_laki,penilaian_tambahan_perilaku_kekerasan.statik_skorlaki_laki,penilaian_tambahan_perilaku_kekerasan.statik_usia_dibawah_35,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_skorusia_dibawah_35,penilaian_tambahan_perilaku_kekerasan.statik_riwayat_kriminal,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_skorriwayat_kriminal,penilaian_tambahan_perilaku_kekerasan.statik_ide_kekerasan,penilaian_tambahan_perilaku_kekerasan.statik_skoride_kekerasan,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_kekerasan_anak_anak,penilaian_tambahan_perilaku_kekerasan.statik_skorkekerasan_anak_anak,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_peran_dalam_hidup,penilaian_tambahan_perilaku_kekerasan.statik_skorperan_dalam_hidup,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_penggunaan_napza,penilaian_tambahan_perilaku_kekerasan.statik_skorpenggunaan_napza,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_skortotal,penilaian_tambahan_perilaku_kekerasan.dinamis_ide_melukai_orang_lain,penilaian_tambahan_perilaku_kekerasan.dinamis_skoride_melukai_orang_lain,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_akses_kekerasan,penilaian_tambahan_perilaku_kekerasan.dinamis_skorakses_kekerasan,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_ide_paranoid,penilaian_tambahan_perilaku_kekerasan.dinamis_skoride_paranoid,penilaian_tambahan_perilaku_kekerasan.dinamis_perintah_halusinasi,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorperintah_halusinasi,penilaian_tambahan_perilaku_kekerasan.dinamis_frustasi_agitasi,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorfrustasi_agitasi,penilaian_tambahan_perilaku_kekerasan.dinamis_kesenangan_kekerasan,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorkesenangan_kekerasan,penilaian_tambahan_perilaku_kekerasan.dinamis_seksual_tidak_wajar,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorseksual_tidak_wajar,penilaian_tambahan_perilaku_kekerasan.dinamis_hilangnya_kontrol_diri,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorhilangnya_kontrol_diri,penilaian_tambahan_perilaku_kekerasan.dinamis_pengguaan_napza,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorpengguaan_napza,penilaian_tambahan_perilaku_kekerasan.dinamis_skortotal,penilaian_tambahan_perilaku_kekerasan.faktor_faktor_pencegahan,"+
                    "penilaian_tambahan_perilaku_kekerasan.total_skor,penilaian_tambahan_perilaku_kekerasan.level_skor,penilaian_tambahan_perilaku_kekerasan.nip,petugas.nama "+
                    "from penilaian_tambahan_perilaku_kekerasan inner join reg_periksa on penilaian_tambahan_perilaku_kekerasan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_tambahan_perilaku_kekerasan.nip=petugas.nip where "+
                    "penilaian_tambahan_perilaku_kekerasan.tanggal between ? and ? order by penilaian_tambahan_perilaku_kekerasan.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penilaian_tambahan_perilaku_kekerasan.tanggal,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_insiden_kekerasan_baru_ini,penilaian_tambahan_perilaku_kekerasan.statik_skorinsiden_kekerasan_baru_ini,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_riwayat_penggunaan_senjata,penilaian_tambahan_perilaku_kekerasan.statik_skorriwayat_penggunaan_senjata,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_laki_laki,penilaian_tambahan_perilaku_kekerasan.statik_skorlaki_laki,penilaian_tambahan_perilaku_kekerasan.statik_usia_dibawah_35,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_skorusia_dibawah_35,penilaian_tambahan_perilaku_kekerasan.statik_riwayat_kriminal,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_skorriwayat_kriminal,penilaian_tambahan_perilaku_kekerasan.statik_ide_kekerasan,penilaian_tambahan_perilaku_kekerasan.statik_skoride_kekerasan,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_kekerasan_anak_anak,penilaian_tambahan_perilaku_kekerasan.statik_skorkekerasan_anak_anak,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_peran_dalam_hidup,penilaian_tambahan_perilaku_kekerasan.statik_skorperan_dalam_hidup,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_penggunaan_napza,penilaian_tambahan_perilaku_kekerasan.statik_skorpenggunaan_napza,"+
                    "penilaian_tambahan_perilaku_kekerasan.statik_skortotal,penilaian_tambahan_perilaku_kekerasan.dinamis_ide_melukai_orang_lain,penilaian_tambahan_perilaku_kekerasan.dinamis_skoride_melukai_orang_lain,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_akses_kekerasan,penilaian_tambahan_perilaku_kekerasan.dinamis_skorakses_kekerasan,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_ide_paranoid,penilaian_tambahan_perilaku_kekerasan.dinamis_skoride_paranoid,penilaian_tambahan_perilaku_kekerasan.dinamis_perintah_halusinasi,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorperintah_halusinasi,penilaian_tambahan_perilaku_kekerasan.dinamis_frustasi_agitasi,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorfrustasi_agitasi,penilaian_tambahan_perilaku_kekerasan.dinamis_kesenangan_kekerasan,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorkesenangan_kekerasan,penilaian_tambahan_perilaku_kekerasan.dinamis_seksual_tidak_wajar,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorseksual_tidak_wajar,penilaian_tambahan_perilaku_kekerasan.dinamis_hilangnya_kontrol_diri,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorhilangnya_kontrol_diri,penilaian_tambahan_perilaku_kekerasan.dinamis_pengguaan_napza,"+
                    "penilaian_tambahan_perilaku_kekerasan.dinamis_skorpengguaan_napza,penilaian_tambahan_perilaku_kekerasan.dinamis_skortotal,penilaian_tambahan_perilaku_kekerasan.faktor_faktor_pencegahan,"+
                    "penilaian_tambahan_perilaku_kekerasan.total_skor,penilaian_tambahan_perilaku_kekerasan.level_skor,penilaian_tambahan_perilaku_kekerasan.nip,petugas.nama "+
                    "from penilaian_tambahan_perilaku_kekerasan inner join reg_periksa on penilaian_tambahan_perilaku_kekerasan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on penilaian_tambahan_perilaku_kekerasan.nip=petugas.nip where "+
                    "penilaian_tambahan_perilaku_kekerasan.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_tambahan_perilaku_kekerasan.nip like ? or petugas.nama like ?) "+
                    "order by penilaian_tambahan_perilaku_kekerasan.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal"),
                        rs.getString("statik_insiden_kekerasan_baru_ini"),rs.getString("statik_skorinsiden_kekerasan_baru_ini"),rs.getString("statik_riwayat_penggunaan_senjata"),rs.getString("statik_skorriwayat_penggunaan_senjata"),
                        rs.getString("statik_laki_laki"),rs.getString("statik_skorlaki_laki"),rs.getString("statik_usia_dibawah_35"),rs.getString("statik_skorusia_dibawah_35"),rs.getString("statik_riwayat_kriminal"),
                        rs.getString("statik_skorriwayat_kriminal"),rs.getString("statik_ide_kekerasan"),rs.getString("statik_skoride_kekerasan"),rs.getString("statik_kekerasan_anak_anak"),rs.getString("statik_skorkekerasan_anak_anak"),
                        rs.getString("statik_peran_dalam_hidup"),rs.getString("statik_skorperan_dalam_hidup"),rs.getString("statik_penggunaan_napza"),rs.getString("statik_skorpenggunaan_napza"),rs.getString("statik_skortotal"),
                        rs.getString("dinamis_ide_melukai_orang_lain"),rs.getString("dinamis_skoride_melukai_orang_lain"),rs.getString("dinamis_akses_kekerasan"),rs.getString("dinamis_skorakses_kekerasan"),rs.getString("dinamis_ide_paranoid"),
                        rs.getString("dinamis_skoride_paranoid"),rs.getString("dinamis_perintah_halusinasi"),rs.getString("dinamis_skorperintah_halusinasi"),rs.getString("dinamis_frustasi_agitasi"),rs.getString("dinamis_skorfrustasi_agitasi"),
                        rs.getString("dinamis_kesenangan_kekerasan"),rs.getString("dinamis_skorkesenangan_kekerasan"),rs.getString("dinamis_seksual_tidak_wajar"),rs.getString("dinamis_skorseksual_tidak_wajar"),
                        rs.getString("dinamis_hilangnya_kontrol_diri"),rs.getString("dinamis_skorhilangnya_kontrol_diri"),rs.getString("dinamis_pengguaan_napza"),rs.getString("dinamis_skorpengguaan_napza"),rs.getString("dinamis_skortotal"),
                        rs.getString("faktor_faktor_pencegahan"),rs.getString("total_skor"),rs.getString("level_skor"),rs.getString("nip"),rs.getString("nama")
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
        FaktorStatik1.setSelectedIndex(0);
        SkorStatik1.setText("0");
        FaktorStatik2.setSelectedIndex(0);
        SkorStatik2.setText("0");
        FaktorStatik3.setSelectedIndex(0);
        SkorStatik3.setText("0");
        FaktorStatik4.setSelectedIndex(0);
        SkorStatik4.setText("0");
        FaktorStatik5.setSelectedIndex(0);
        SkorStatik5.setText("0");
        FaktorStatik6.setSelectedIndex(0);
        SkorStatik6.setText("0");
        FaktorStatik7.setSelectedIndex(0);
        SkorStatik7.setText("0");
        FaktorStatik8.setSelectedIndex(0);
        SkorStatik8.setText("0");
        FaktorStatik9.setSelectedIndex(0);
        SkorStatik9.setText("0");
        TotalStatik.setText("0");
        FaktorDinamis1.setSelectedIndex(0);
        SkorDinamis1.setText("0");
        FaktorDinamis2.setSelectedIndex(0);
        SkorDinamis2.setText("0");
        FaktorDinamis3.setSelectedIndex(0);
        SkorDinamis3.setText("0");
        FaktorDinamis4.setSelectedIndex(0);
        SkorDinamis4.setText("0");
        FaktorDinamis5.setSelectedIndex(0);
        SkorDinamis5.setText("0");
        FaktorDinamis6.setSelectedIndex(0);
        SkorDinamis6.setText("0");
        FaktorDinamis7.setSelectedIndex(0);
        SkorDinamis7.setText("0");
        FaktorDinamis8.setSelectedIndex(0);
        SkorDinamis8.setText("0");
        FaktorDinamis9.setSelectedIndex(0);
        SkorDinamis9.setText("0");
        TotalDinamis.setText("0");
        SkorTotal.setText("0");
        Level.setText("Rendah(<7)");
        FaktorPencegahan.setText("");
        FaktorStatik1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            
            FaktorStatik1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            SkorStatik1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            FaktorStatik2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            SkorStatik2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            FaktorStatik3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            SkorStatik3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            FaktorStatik4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            SkorStatik4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            FaktorStatik5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            SkorStatik5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            FaktorStatik6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            SkorStatik6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            FaktorStatik7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            SkorStatik7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            FaktorStatik8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            SkorStatik8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            FaktorStatik9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            SkorStatik9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            TotalStatik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            FaktorDinamis1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            SkorDinamis1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            FaktorDinamis2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            SkorDinamis2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            FaktorDinamis3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            SkorDinamis3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            FaktorDinamis4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            SkorDinamis4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            FaktorDinamis5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            SkorDinamis5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            FaktorDinamis6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            SkorDinamis6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            FaktorDinamis7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            SkorDinamis7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            FaktorDinamis8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            SkorDinamis8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            FaktorDinamis9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            SkorDinamis9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            TotalDinamis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            FaktorPencegahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            SkorTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Level.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            
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
            ps=koneksi.prepareStatement("select pasien.nm_pasien,pasien.jk,pasien.tgl_lahir as lahir from pasien where pasien.no_rkm_medis=?");
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
            if(internalFrame1.getHeight()>628){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,456));
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
        BtnSimpan.setEnabled(akses.getpenilaian_tambahan_perilaku_kekerasan());
        BtnHapus.setEnabled(akses.getpenilaian_tambahan_perilaku_kekerasan());
        BtnEdit.setEnabled(akses.getpenilaian_tambahan_perilaku_kekerasan());
        BtnPrint.setEnabled(akses.getpenilaian_tambahan_perilaku_kekerasan()); 
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
        if(Sequel.mengedittf("penilaian_tambahan_perilaku_kekerasan","no_rawat=?","no_rawat=?,tanggal=?,nip=?,statik_insiden_kekerasan_baru_ini=?,statik_skorinsiden_kekerasan_baru_ini=?,statik_riwayat_penggunaan_senjata=?,"+
                "statik_skorriwayat_penggunaan_senjata=?,statik_laki_laki=?,statik_skorlaki_laki=?,statik_usia_dibawah_35=?,statik_skorusia_dibawah_35=?,statik_riwayat_kriminal=?,statik_skorriwayat_kriminal=?,statik_ide_kekerasan=?,"+
                "statik_skoride_kekerasan=?,statik_kekerasan_anak_anak=?,statik_skorkekerasan_anak_anak=?,statik_peran_dalam_hidup=?,statik_skorperan_dalam_hidup=?,statik_penggunaan_napza=?,statik_skorpenggunaan_napza=?,statik_skortotal=?,"+
                "dinamis_ide_melukai_orang_lain=?,dinamis_skoride_melukai_orang_lain=?,dinamis_akses_kekerasan=?,dinamis_skorakses_kekerasan=?,dinamis_ide_paranoid=?,dinamis_skoride_paranoid=?,dinamis_perintah_halusinasi=?,"+
                "dinamis_skorperintah_halusinasi=?,dinamis_frustasi_agitasi=?,dinamis_skorfrustasi_agitasi=?,dinamis_kesenangan_kekerasan=?,dinamis_skorkesenangan_kekerasan=?,dinamis_seksual_tidak_wajar=?,dinamis_skorseksual_tidak_wajar=?,"+
                "dinamis_hilangnya_kontrol_diri=?,dinamis_skorhilangnya_kontrol_diri=?,dinamis_pengguaan_napza=?,dinamis_skorpengguaan_napza=?,dinamis_skortotal=?,faktor_faktor_pencegahan=?,total_skor=?,level_skor=?",45,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),NIP.getText(),
                FaktorStatik1.getSelectedItem().toString(),SkorStatik1.getText(),FaktorStatik2.getSelectedItem().toString(),SkorStatik2.getText(),FaktorStatik3.getSelectedItem().toString(),SkorStatik3.getText(),
                FaktorStatik4.getSelectedItem().toString(),SkorStatik4.getText(),FaktorStatik5.getSelectedItem().toString(),SkorStatik5.getText(),FaktorStatik6.getSelectedItem().toString(),SkorStatik6.getText(), 
                FaktorStatik7.getSelectedItem().toString(),SkorStatik7.getText(),FaktorStatik8.getSelectedItem().toString(),SkorStatik8.getText(),FaktorStatik9.getSelectedItem().toString(),SkorStatik9.getText(),
                TotalStatik.getText(),FaktorDinamis1.getSelectedItem().toString(),SkorDinamis1.getText(),FaktorDinamis2.getSelectedItem().toString(),SkorDinamis2.getText(),FaktorDinamis3.getSelectedItem().toString(),
                SkorDinamis3.getText(),FaktorDinamis4.getSelectedItem().toString(),SkorDinamis4.getText(),FaktorDinamis5.getSelectedItem().toString(),SkorDinamis5.getText(),FaktorDinamis6.getSelectedItem().toString(),
                SkorDinamis6.getText(),FaktorDinamis7.getSelectedItem().toString(),SkorDinamis7.getText(),FaktorDinamis8.getSelectedItem().toString(),SkorDinamis8.getText(),FaktorDinamis9.getSelectedItem().toString(),
                SkorDinamis9.getText(),TotalDinamis.getText(),FaktorPencegahan.getText(),SkorTotal.getText(),Level.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(FaktorStatik1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(SkorStatik1.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(FaktorStatik2.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(SkorStatik2.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(FaktorStatik3.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(SkorStatik3.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(FaktorStatik4.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(SkorStatik4.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(FaktorStatik5.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(SkorStatik5.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(FaktorStatik6.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(SkorStatik6.getText(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(FaktorStatik7.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(SkorStatik7.getText(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(FaktorStatik8.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(SkorStatik8.getText(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(FaktorStatik9.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(SkorStatik9.getText(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(TotalStatik.getText(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(FaktorDinamis1.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(SkorDinamis1.getText(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(FaktorDinamis2.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(SkorDinamis2.getText(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(FaktorDinamis3.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(SkorDinamis3.getText(),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(FaktorDinamis4.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(SkorDinamis4.getText(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(FaktorDinamis5.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
            tbObat.setValueAt(SkorDinamis5.getText(),tbObat.getSelectedRow(),34);
            tbObat.setValueAt(FaktorDinamis6.getSelectedItem().toString(),tbObat.getSelectedRow(),35);
            tbObat.setValueAt(SkorDinamis6.getText(),tbObat.getSelectedRow(),36);
            tbObat.setValueAt(FaktorDinamis7.getSelectedItem().toString(),tbObat.getSelectedRow(),37);
            tbObat.setValueAt(SkorDinamis7.getText(),tbObat.getSelectedRow(),38);
            tbObat.setValueAt(FaktorDinamis8.getSelectedItem().toString(),tbObat.getSelectedRow(),39);
            tbObat.setValueAt(SkorDinamis8.getText(),tbObat.getSelectedRow(),40);
            tbObat.setValueAt(FaktorDinamis9.getSelectedItem().toString(),tbObat.getSelectedRow(),41);
            tbObat.setValueAt(SkorDinamis9.getText(),tbObat.getSelectedRow(),42);
            tbObat.setValueAt(TotalDinamis.getText(),tbObat.getSelectedRow(),43);
            tbObat.setValueAt(FaktorPencegahan.getText(),tbObat.getSelectedRow(),44);
            tbObat.setValueAt(SkorTotal.getText(),tbObat.getSelectedRow(),45);
            tbObat.setValueAt(Level.getText(),tbObat.getSelectedRow(),46);
            tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),47);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),48);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_tambahan_perilaku_kekerasan where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            emptTeks();
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void isTotalSkor(){
        try {
            TotalStatik.setText((Integer.parseInt(SkorStatik1.getText())+Integer.parseInt(SkorStatik2.getText())+Integer.parseInt(SkorStatik3.getText())+Integer.parseInt(SkorStatik4.getText())+Integer.parseInt(SkorStatik5.getText())+Integer.parseInt(SkorStatik6.getText())+Integer.parseInt(SkorStatik7.getText())+Integer.parseInt(SkorStatik8.getText())+Integer.parseInt(SkorStatik9.getText()))+"");
            TotalDinamis.setText((Integer.parseInt(SkorDinamis1.getText())+Integer.parseInt(SkorDinamis2.getText())+Integer.parseInt(SkorDinamis3.getText())+Integer.parseInt(SkorDinamis4.getText())+Integer.parseInt(SkorDinamis5.getText())+Integer.parseInt(SkorDinamis6.getText())+Integer.parseInt(SkorDinamis7.getText())+Integer.parseInt(SkorDinamis8.getText())+Integer.parseInt(SkorDinamis9.getText()))+"");
            SkorTotal.setText((Integer.parseInt(TotalStatik.getText())+Integer.parseInt(TotalDinamis.getText()))+"");
            if(Integer.parseInt(SkorTotal.getText())<7){
                Level.setText("Rendah(<7)");
            }else if(Integer.parseInt(SkorTotal.getText())<=14){
                Level.setText("Sedang(7-14)");
            }else if(Integer.parseInt(SkorTotal.getText())>14){
                Level.setText("Tinggi(>14)");
            }
        } catch (Exception e) {
            SkorTotal.setText("0");
            Level.setText("Rendah(<7)");
        }
    }
}

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
public final class RMSkriningRisikoKankerParu extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningRisikoKankerParu(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Umur","Kode Petugas","Nama Petugas","Tanggal",
            "Jenis Kelamin","N.A.1","Usia/Umur","N.A.2","Pernah Didiagnosis Kanker","N.A.3",
            "Ada Keluarga Kanker","N.A.4","Riwayat Merokok","N.A.5","Riwayat Bekerja Karsinogenik","N.A.6",
            "Lingkungan Tinggal Berpolusi","N.A.7","Rumah Tidak Sehat","N.A.8","Pernah Paru Kronis","N.A.9",
            "Total Skor","Hasil Skrining","Keterangan"
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
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(45);
            }else if(i==10){
                column.setPreferredWidth(78);
            }else if(i==11){
                column.setPreferredWidth(45);
            }else if(i==12){
                column.setPreferredWidth(165);
            }else if(i==13){
                column.setPreferredWidth(45);
            }else if(i==14){
                column.setPreferredWidth(115);
            }else if(i==15){
                column.setPreferredWidth(45);
            }else if(i==16){
                column.setPreferredWidth(210);
            }else if(i==17){
                column.setPreferredWidth(45);
            }else if(i==18){
                column.setPreferredWidth(152);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(155);
            }else if(i==21){
                column.setPreferredWidth(45);
            }else if(i==22){
                column.setPreferredWidth(121);
            }else if(i==23){
                column.setPreferredWidth(45);
            }else if(i==24){
                column.setPreferredWidth(195);
            }else if(i==25){
                column.setPreferredWidth(45);
            }else if(i==26){
                column.setPreferredWidth(60);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Keterangan.setDocument(new batasInput((byte)50).getKata(Keterangan));
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
        MnSkriningRisikoKankerParu = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
        Umur = new widget.TextBox();
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
        JenisKelamin = new widget.ComboBox();
        jLabel92 = new widget.Label();
        Usia = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel73 = new widget.Label();
        TotalHasil = new widget.TextBox();
        NilaiJenisKelamin = new widget.TextBox();
        NilaiUsia = new widget.TextBox();
        PernahMenderitaKanker = new widget.ComboBox();
        jLabel70 = new widget.Label();
        NilaiPernahMenderitaKanker = new widget.TextBox();
        jLabel71 = new widget.Label();
        NilaiKeluargaKanker = new widget.TextBox();
        KeluargaKanker = new widget.ComboBox();
        jLabel148 = new widget.Label();
        HasilSkrining = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        RiwayatMerokok = new widget.ComboBox();
        jLabel72 = new widget.Label();
        NilaiRiwayatMerokok = new widget.TextBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        RiwayatBekerja = new widget.ComboBox();
        jLabel74 = new widget.Label();
        NilaiRiwayatBekerja = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        LingkunganTinggal = new widget.ComboBox();
        jLabel89 = new widget.Label();
        NilaiLingkunganTinggal = new widget.TextBox();
        jLabel90 = new widget.Label();
        DalamRumah = new widget.ComboBox();
        jLabel91 = new widget.Label();
        NilaiDalamRumah = new widget.TextBox();
        jLabel93 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        jLabel149 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel95 = new widget.Label();
        PernahParu = new widget.ComboBox();
        jLabel96 = new widget.Label();
        NilaiPernahParu = new widget.TextBox();
        jLabel97 = new widget.Label();
        jLabel98 = new widget.Label();
        jLabel100 = new widget.Label();
        jLabel102 = new widget.Label();
        jLabel150 = new widget.Label();
        Keterangan = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningRisikoKankerParu.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningRisikoKankerParu.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningRisikoKankerParu.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningRisikoKankerParu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningRisikoKankerParu.setText("Formulir Skrining Risiko Kanker Paru");
        MnSkriningRisikoKankerParu.setName("MnSkriningRisikoKankerParu"); // NOI18N
        MnSkriningRisikoKankerParu.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningRisikoKankerParu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningRisikoKankerParuActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningRisikoKankerParu);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Umur.setEditable(false);
        Umur.setFocusTraversalPolicyProvider(true);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Risiko Kanker Paru ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-07-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-07-2024" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 413));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-07-2024" }));
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

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        JenisKelamin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        JenisKelamin.setSelectedIndex(1);
        JenisKelamin.setName("JenisKelamin"); // NOI18N
        JenisKelamin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisKelaminItemStateChanged(evt);
            }
        });
        JenisKelamin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKelaminKeyPressed(evt);
            }
        });
        FormInput.add(JenisKelamin);
        JenisKelamin.setBounds(585, 90, 110, 23);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(690, 90, 50, 23);

        Usia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "> 65 Tahun", "45 - 65 Tahun", "< 45 Tahun" }));
        Usia.setName("Usia"); // NOI18N
        Usia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                UsiaItemStateChanged(evt);
            }
        });
        Usia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaKeyPressed(evt);
            }
        });
        FormInput.add(Usia);
        Usia.setBounds(575, 120, 120, 23);

        jLabel69.setText("Nilai :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(690, 120, 50, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(670, 380, 70, 23);

        TotalHasil.setEditable(false);
        TotalHasil.setText("0");
        TotalHasil.setFocusTraversalPolicyProvider(true);
        TotalHasil.setName("TotalHasil"); // NOI18N
        FormInput.add(TotalHasil);
        TotalHasil.setBounds(744, 380, 45, 23);

        NilaiJenisKelamin.setEditable(false);
        NilaiJenisKelamin.setText("0");
        NilaiJenisKelamin.setFocusTraversalPolicyProvider(true);
        NilaiJenisKelamin.setName("NilaiJenisKelamin"); // NOI18N
        FormInput.add(NilaiJenisKelamin);
        NilaiJenisKelamin.setBounds(744, 90, 45, 23);

        NilaiUsia.setEditable(false);
        NilaiUsia.setText("0");
        NilaiUsia.setFocusTraversalPolicyProvider(true);
        NilaiUsia.setName("NilaiUsia"); // NOI18N
        FormInput.add(NilaiUsia);
        NilaiUsia.setBounds(744, 120, 45, 23);

        PernahMenderitaKanker.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya, Pernah > 5 Tahun Yang Lalu", "Ya, Pernah < 5 Tahun Yang Lalu", "Tidak Pernah" }));
        PernahMenderitaKanker.setName("PernahMenderitaKanker"); // NOI18N
        PernahMenderitaKanker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PernahMenderitaKankerItemStateChanged(evt);
            }
        });
        PernahMenderitaKanker.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernahMenderitaKankerKeyPressed(evt);
            }
        });
        FormInput.add(PernahMenderitaKanker);
        PernahMenderitaKanker.setBounds(487, 150, 208, 23);

        jLabel70.setText("Nilai :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(690, 150, 50, 23);

        NilaiPernahMenderitaKanker.setEditable(false);
        NilaiPernahMenderitaKanker.setText("0");
        NilaiPernahMenderitaKanker.setFocusTraversalPolicyProvider(true);
        NilaiPernahMenderitaKanker.setName("NilaiPernahMenderitaKanker"); // NOI18N
        FormInput.add(NilaiPernahMenderitaKanker);
        NilaiPernahMenderitaKanker.setBounds(744, 150, 45, 23);

        jLabel71.setText("Nilai :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(690, 180, 50, 23);

        NilaiKeluargaKanker.setEditable(false);
        NilaiKeluargaKanker.setText("0");
        NilaiKeluargaKanker.setFocusTraversalPolicyProvider(true);
        NilaiKeluargaKanker.setName("NilaiKeluargaKanker"); // NOI18N
        FormInput.add(NilaiKeluargaKanker);
        NilaiKeluargaKanker.setBounds(744, 180, 45, 23);

        KeluargaKanker.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya, Kanker Paru", "Ya, Kanker Jenis Lain", "Tidak Ada" }));
        KeluargaKanker.setSelectedIndex(2);
        KeluargaKanker.setName("KeluargaKanker"); // NOI18N
        KeluargaKanker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KeluargaKankerItemStateChanged(evt);
            }
        });
        KeluargaKanker.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluargaKankerKeyPressed(evt);
            }
        });
        FormInput.add(KeluargaKanker);
        KeluargaKanker.setBounds(541, 180, 154, 23);

        jLabel148.setText("Hasil Skrining :");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(436, 380, 90, 23);

        HasilSkrining.setEditable(false);
        HasilSkrining.setFocusTraversalPolicyProvider(true);
        HasilSkrining.setName("HasilSkrining"); // NOI18N
        HasilSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilSkriningKeyPressed(evt);
            }
        });
        FormInput.add(HasilSkrining);
        HasilSkrining.setBounds(530, 380, 120, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. ANAMNESIS");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 200, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1.");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(44, 90, 20, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Jenis Kelamin Anda ?");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(57, 90, 430, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("2.");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(44, 120, 20, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Usia / umur Anda sekarang ?");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(57, 120, 430, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Apakah pernah didiagnosis menderita kanker ?");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(57, 150, 420, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("3.");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 150, 20, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("4.");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(44, 180, 20, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Apakah ada keluarga (ayah/ibu/saudara kandung) menderita kanker ?");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(57, 180, 470, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("5.");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(44, 210, 20, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Riwayat merokok/paparan asap rokok (rokok kretek/rokok putih/vape/");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(57, 205, 380, 23);

        RiwayatMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Perokok Aktif, Masih Merokok 1 Tahun Ini", "Bekas Perokok, Berhenti < 15 Tahun", "Perokok Pasif", "Tidak Merokok" }));
        RiwayatMerokok.setSelectedIndex(2);
        RiwayatMerokok.setName("RiwayatMerokok"); // NOI18N
        RiwayatMerokok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RiwayatMerokokItemStateChanged(evt);
            }
        });
        RiwayatMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatMerokokKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatMerokok);
        RiwayatMerokok.setBounds(445, 210, 250, 23);

        jLabel72.setText("Nilai :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(690, 210, 50, 23);

        NilaiRiwayatMerokok.setEditable(false);
        NilaiRiwayatMerokok.setText("0");
        NilaiRiwayatMerokok.setFocusTraversalPolicyProvider(true);
        NilaiRiwayatMerokok.setName("NilaiRiwayatMerokok"); // NOI18N
        FormInput.add(NilaiRiwayatMerokok);
        NilaiRiwayatMerokok.setBounds(744, 210, 45, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("6.");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(44, 240, 20, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Riwayat bekerja di lingkungan yang mengandung zat karsinogen (pertambangan/pabrik/");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(57, 235, 470, 23);

        RiwayatBekerja.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak Yakin/Ragu-ragu", "Tidak" }));
        RiwayatBekerja.setSelectedIndex(2);
        RiwayatBekerja.setName("RiwayatBekerja"); // NOI18N
        RiwayatBekerja.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RiwayatBekerjaItemStateChanged(evt);
            }
        });
        RiwayatBekerja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatBekerjaKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatBekerja);
        RiwayatBekerja.setBounds(532, 240, 163, 23);

        jLabel74.setText("Nilai :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(690, 240, 50, 23);

        NilaiRiwayatBekerja.setEditable(false);
        NilaiRiwayatBekerja.setText("0");
        NilaiRiwayatBekerja.setFocusTraversalPolicyProvider(true);
        NilaiRiwayatBekerja.setName("NilaiRiwayatBekerja"); // NOI18N
        FormInput.add(NilaiRiwayatBekerja);
        NilaiRiwayatBekerja.setBounds(744, 240, 45, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Lingkungan tempat tinggal berpolusi tinggi (lingkungan pabrik/pertambangan/tempat");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(57, 265, 470, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("7.");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(44, 270, 20, 23);

        LingkunganTinggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak Yakin/Ragu-ragu", "Tidak" }));
        LingkunganTinggal.setSelectedIndex(2);
        LingkunganTinggal.setName("LingkunganTinggal"); // NOI18N
        LingkunganTinggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LingkunganTinggalItemStateChanged(evt);
            }
        });
        LingkunganTinggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkunganTinggalKeyPressed(evt);
            }
        });
        FormInput.add(LingkunganTinggal);
        LingkunganTinggal.setBounds(532, 270, 163, 23);

        jLabel89.setText("Nilai :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(690, 270, 50, 23);

        NilaiLingkunganTinggal.setEditable(false);
        NilaiLingkunganTinggal.setText("0");
        NilaiLingkunganTinggal.setFocusTraversalPolicyProvider(true);
        NilaiLingkunganTinggal.setName("NilaiLingkunganTinggal"); // NOI18N
        FormInput.add(NilaiLingkunganTinggal);
        NilaiLingkunganTinggal.setBounds(744, 270, 45, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Lingkungan dalam rumah yang tidak sehat (ventilasi buruk/atap dari asbes/lantai tanah/");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(57, 295, 470, 23);

        DalamRumah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak Yakin/Ragu-ragu", "Tidak" }));
        DalamRumah.setSelectedIndex(2);
        DalamRumah.setName("DalamRumah"); // NOI18N
        DalamRumah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DalamRumahItemStateChanged(evt);
            }
        });
        DalamRumah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DalamRumahKeyPressed(evt);
            }
        });
        FormInput.add(DalamRumah);
        DalamRumah.setBounds(532, 300, 163, 23);

        jLabel91.setText("Nilai :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(690, 300, 50, 23);

        NilaiDalamRumah.setEditable(false);
        NilaiDalamRumah.setText("0");
        NilaiDalamRumah.setFocusTraversalPolicyProvider(true);
        NilaiDalamRumah.setName("NilaiDalamRumah"); // NOI18N
        FormInput.add(NilaiDalamRumah);
        NilaiDalamRumah.setBounds(744, 300, 45, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("8.");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(44, 300, 20, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 360, 807, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("II. INTERPRETASI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 360, 200, 23);

        jLabel149.setText(":");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(0, 380, 107, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("9.");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(44, 330, 20, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("Pernah didiagnosis/diobati penyakit paru kronik");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(57, 330, 390, 23);

        PernahParu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya, Pernah Tuberkolosis (TBC)", "Ya, Pernah Penyakit Paru Kronik(PPOK)", "Tidak" }));
        PernahParu.setSelectedIndex(2);
        PernahParu.setName("PernahParu"); // NOI18N
        PernahParu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PernahParuItemStateChanged(evt);
            }
        });
        PernahParu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PernahParuKeyPressed(evt);
            }
        });
        FormInput.add(PernahParu);
        PernahParu.setBounds(455, 330, 240, 23);

        jLabel96.setText("Nilai :");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(690, 330, 50, 23);

        NilaiPernahParu.setEditable(false);
        NilaiPernahParu.setText("0");
        NilaiPernahParu.setFocusTraversalPolicyProvider(true);
        NilaiPernahParu.setName("NilaiPernahParu"); // NOI18N
        FormInput.add(NilaiPernahParu);
        NilaiPernahParu.setBounds(744, 330, 45, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("shisya/cerutu/rokok linting, dll)");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(57, 218, 380, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("bengkel/garmen/laboratorium kimia/galangan kapal, dII)");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(57, 248, 470, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("buangan sampah/tepi jalan besar)");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(57, 278, 470, 23);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("dapur kayu bakar/dapur breket/menggunakan rutin obat nyamuk bakar/semprot, dll)");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(57, 308, 470, 23);

        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel150.setText("Keterangan");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(44, 380, 90, 23);

        Keterangan.setFocusTraversalPolicyProvider(true);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(111, 380, 310, 23);

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
                StringBuilder htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'><td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Kelamin</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.1</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Usia/Umur</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.2</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pernah Didiagnosis Kanker</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.3</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ada Keluarga Kanker</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.4</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Merokok</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.5</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Bekerja Karsinogenik</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.6</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lingkungan Tinggal Berpolusi</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.7</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rumah Tidak Sehat</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.8</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pernah Paru Kronis</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.A.9</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total Skor</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Skrining</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td></tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append("<tr class='isi'><td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td><td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td></tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );
                htmlContent=null;

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

                File f = new File("DataSkriningRisikoKankerParu.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SEKRINING RISIKO KANKER PARU<br><br></font>"+        
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
        Valid.pindah(evt,TCari,JenisKelamin);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningRisikoKankerParuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningRisikoKankerParuActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirSkriningRisikoKankerParu.jasper","report","::[ Formulir Skrining Risiko Kanker Paru ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_risiko_kanker_paru.nip,"+
                    "petugas.nama,skrining_risiko_kanker_paru.tanggal,skrining_risiko_kanker_paru.jenis_kelamin,skrining_risiko_kanker_paru.nilai_jenis_kelamin,skrining_risiko_kanker_paru.umur,"+
                    "skrining_risiko_kanker_paru.nilai_umur,skrining_risiko_kanker_paru.pernah_kanker,skrining_risiko_kanker_paru.nilai_pernah_kanker,skrining_risiko_kanker_paru.ada_keluarga_kanker,"+
                    "skrining_risiko_kanker_paru.nilai_ada_keluarga_kanker,skrining_risiko_kanker_paru.riwayat_rokok,skrining_risiko_kanker_paru.nilai_riwayat_rokok,skrining_risiko_kanker_paru.riwayat_bekerja_mengandung_karsinogen,"+
                    "skrining_risiko_kanker_paru.nilai_riwayat_bekerja_mengandung_karsinogen,skrining_risiko_kanker_paru.lingkungan_tinggal_polusi_tinggi,skrining_risiko_kanker_paru.nilai_lingkungan_tinggal_polusi_tinggi,"+
                    "skrining_risiko_kanker_paru.lingkungan_rumah_tidak_sehat,skrining_risiko_kanker_paru.nilai_lingkungan_rumah_tidak_sehat,skrining_risiko_kanker_paru.pernah_paru_kronik,"+
                    "skrining_risiko_kanker_paru.nilai_pernah_paru_kronik,skrining_risiko_kanker_paru.total_skor,skrining_risiko_kanker_paru.hasil_skrining,skrining_risiko_kanker_paru.keterangan "+
                    "from skrining_risiko_kanker_paru inner join reg_periksa on skrining_risiko_kanker_paru.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_risiko_kanker_paru.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningRisikoKankerParuActionPerformed

    private void JenisKelaminItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisKelaminItemStateChanged
        if(JenisKelamin.getSelectedIndex()==0){
            NilaiJenisKelamin.setText("3");
        }else{
            NilaiJenisKelamin.setText("1");
        }
        isTotal();
    }//GEN-LAST:event_JenisKelaminItemStateChanged

    private void JenisKelaminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKelaminKeyPressed
        Valid.pindah(evt,TCari,Usia);
    }//GEN-LAST:event_JenisKelaminKeyPressed

    private void UsiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_UsiaItemStateChanged
        NilaiUsia.setText((3-Usia.getSelectedIndex())+"");
        isTotal();
    }//GEN-LAST:event_UsiaItemStateChanged

    private void UsiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaKeyPressed
        Valid.pindah(evt,JenisKelamin,PernahMenderitaKanker);
    }//GEN-LAST:event_UsiaKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void PernahMenderitaKankerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PernahMenderitaKankerItemStateChanged
        NilaiPernahMenderitaKanker.setText((3-PernahMenderitaKanker.getSelectedIndex())+"");
        isTotal();
    }//GEN-LAST:event_PernahMenderitaKankerItemStateChanged

    private void PernahMenderitaKankerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PernahMenderitaKankerKeyPressed
        Valid.pindah(evt,Usia,KeluargaKanker);
    }//GEN-LAST:event_PernahMenderitaKankerKeyPressed

    private void KeluargaKankerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KeluargaKankerItemStateChanged
        NilaiKeluargaKanker.setText((3-KeluargaKanker.getSelectedIndex())+"");
        isTotal();
    }//GEN-LAST:event_KeluargaKankerItemStateChanged

    private void KeluargaKankerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluargaKankerKeyPressed
        Valid.pindah(evt,PernahMenderitaKanker,RiwayatMerokok);
    }//GEN-LAST:event_KeluargaKankerKeyPressed

    private void HasilSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilSkriningKeyPressed
        //Valid.pindah(evt,Lapor,SG1);
    }//GEN-LAST:event_HasilSkriningKeyPressed

    private void RiwayatMerokokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RiwayatMerokokItemStateChanged
        NilaiRiwayatMerokok.setText((3-RiwayatMerokok.getSelectedIndex())+"");
        isTotal();
    }//GEN-LAST:event_RiwayatMerokokItemStateChanged

    private void RiwayatMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatMerokokKeyPressed
        Valid.pindah(evt,KeluargaKanker,RiwayatBekerja);
    }//GEN-LAST:event_RiwayatMerokokKeyPressed

    private void RiwayatBekerjaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RiwayatBekerjaItemStateChanged
        NilaiRiwayatBekerja.setText((3-RiwayatBekerja.getSelectedIndex())+"");
        isTotal();
    }//GEN-LAST:event_RiwayatBekerjaItemStateChanged

    private void RiwayatBekerjaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatBekerjaKeyPressed
        Valid.pindah(evt,RiwayatMerokok,LingkunganTinggal);
    }//GEN-LAST:event_RiwayatBekerjaKeyPressed

    private void LingkunganTinggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LingkunganTinggalItemStateChanged
        NilaiLingkunganTinggal.setText((3-LingkunganTinggal.getSelectedIndex())+"");
        isTotal();
    }//GEN-LAST:event_LingkunganTinggalItemStateChanged

    private void LingkunganTinggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkunganTinggalKeyPressed
        Valid.pindah(evt,RiwayatBekerja,DalamRumah);
    }//GEN-LAST:event_LingkunganTinggalKeyPressed

    private void DalamRumahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DalamRumahItemStateChanged
        NilaiDalamRumah.setText((3-DalamRumah.getSelectedIndex())+"");
        isTotal();
    }//GEN-LAST:event_DalamRumahItemStateChanged

    private void DalamRumahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DalamRumahKeyPressed
        Valid.pindah(evt,LingkunganTinggal,PernahParu);
    }//GEN-LAST:event_DalamRumahKeyPressed

    private void PernahParuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PernahParuItemStateChanged
        NilaiPernahParu.setText((3-PernahParu.getSelectedIndex())+"");
        isTotal();
    }//GEN-LAST:event_PernahParuItemStateChanged

    private void PernahParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PernahParuKeyPressed
        Valid.pindah(evt,DalamRumah,Keterangan);
    }//GEN-LAST:event_PernahParuKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,PernahParu,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningRisikoKankerParu dialog = new RMSkriningRisikoKankerParu(new javax.swing.JFrame(), true);
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
    private widget.ComboBox DalamRumah;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HasilSkrining;
    private widget.ComboBox Jam;
    private widget.ComboBox JenisKelamin;
    private widget.TextBox KdPetugas;
    private widget.ComboBox KeluargaKanker;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.ComboBox LingkunganTinggal;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningRisikoKankerParu;
    private widget.TextBox NilaiDalamRumah;
    private widget.TextBox NilaiJenisKelamin;
    private widget.TextBox NilaiKeluargaKanker;
    private widget.TextBox NilaiLingkunganTinggal;
    private widget.TextBox NilaiPernahMenderitaKanker;
    private widget.TextBox NilaiPernahParu;
    private widget.TextBox NilaiRiwayatBekerja;
    private widget.TextBox NilaiRiwayatMerokok;
    private widget.TextBox NilaiUsia;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PernahMenderitaKanker;
    private widget.ComboBox PernahParu;
    private widget.ComboBox RiwayatBekerja;
    private widget.ComboBox RiwayatMerokok;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalHasil;
    private widget.TextBox Umur;
    private widget.ComboBox Usia;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel150;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
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
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_risiko_kanker_paru.nip,"+
                    "petugas.nama,skrining_risiko_kanker_paru.tanggal,skrining_risiko_kanker_paru.jenis_kelamin,skrining_risiko_kanker_paru.nilai_jenis_kelamin,skrining_risiko_kanker_paru.umur,"+
                    "skrining_risiko_kanker_paru.nilai_umur,skrining_risiko_kanker_paru.pernah_kanker,skrining_risiko_kanker_paru.nilai_pernah_kanker,skrining_risiko_kanker_paru.ada_keluarga_kanker,"+
                    "skrining_risiko_kanker_paru.nilai_ada_keluarga_kanker,skrining_risiko_kanker_paru.riwayat_rokok,skrining_risiko_kanker_paru.nilai_riwayat_rokok,skrining_risiko_kanker_paru.riwayat_bekerja_mengandung_karsinogen,"+
                    "skrining_risiko_kanker_paru.nilai_riwayat_bekerja_mengandung_karsinogen,skrining_risiko_kanker_paru.lingkungan_tinggal_polusi_tinggi,skrining_risiko_kanker_paru.nilai_lingkungan_tinggal_polusi_tinggi,"+
                    "skrining_risiko_kanker_paru.lingkungan_rumah_tidak_sehat,skrining_risiko_kanker_paru.nilai_lingkungan_rumah_tidak_sehat,skrining_risiko_kanker_paru.pernah_paru_kronik,"+
                    "skrining_risiko_kanker_paru.nilai_pernah_paru_kronik,skrining_risiko_kanker_paru.total_skor,skrining_risiko_kanker_paru.hasil_skrining,skrining_risiko_kanker_paru.keterangan "+
                    "from skrining_risiko_kanker_paru inner join reg_periksa on skrining_risiko_kanker_paru.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_risiko_kanker_paru.nip=petugas.nip "+
                    "where skrining_risiko_kanker_paru.tanggal between ? and ? order by skrining_risiko_kanker_paru.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_risiko_kanker_paru.nip,"+
                    "petugas.nama,skrining_risiko_kanker_paru.tanggal,skrining_risiko_kanker_paru.jenis_kelamin,skrining_risiko_kanker_paru.nilai_jenis_kelamin,skrining_risiko_kanker_paru.umur,"+
                    "skrining_risiko_kanker_paru.nilai_umur,skrining_risiko_kanker_paru.pernah_kanker,skrining_risiko_kanker_paru.nilai_pernah_kanker,skrining_risiko_kanker_paru.ada_keluarga_kanker,"+
                    "skrining_risiko_kanker_paru.nilai_ada_keluarga_kanker,skrining_risiko_kanker_paru.riwayat_rokok,skrining_risiko_kanker_paru.nilai_riwayat_rokok,skrining_risiko_kanker_paru.riwayat_bekerja_mengandung_karsinogen,"+
                    "skrining_risiko_kanker_paru.nilai_riwayat_bekerja_mengandung_karsinogen,skrining_risiko_kanker_paru.lingkungan_tinggal_polusi_tinggi,skrining_risiko_kanker_paru.nilai_lingkungan_tinggal_polusi_tinggi,"+
                    "skrining_risiko_kanker_paru.lingkungan_rumah_tidak_sehat,skrining_risiko_kanker_paru.nilai_lingkungan_rumah_tidak_sehat,skrining_risiko_kanker_paru.pernah_paru_kronik,"+
                    "skrining_risiko_kanker_paru.nilai_pernah_paru_kronik,skrining_risiko_kanker_paru.total_skor,skrining_risiko_kanker_paru.hasil_skrining,skrining_risiko_kanker_paru.keterangan "+
                    "from skrining_risiko_kanker_paru inner join reg_periksa on skrining_risiko_kanker_paru.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_risiko_kanker_paru.nip=petugas.nip "+
                    "where skrining_risiko_kanker_paru.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_risiko_kanker_paru.nip like ? or petugas.nama like ?) "+
                    "order by skrining_risiko_kanker_paru.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("jenis_kelamin"),rs.getString("nilai_jenis_kelamin"),rs.getString("umur"),rs.getString("nilai_umur"),
                        rs.getString("pernah_kanker"),rs.getString("nilai_pernah_kanker"),rs.getString("ada_keluarga_kanker"),rs.getString("nilai_ada_keluarga_kanker"),rs.getString("riwayat_rokok"),
                        rs.getString("nilai_riwayat_rokok"),rs.getString("riwayat_bekerja_mengandung_karsinogen"),rs.getString("nilai_riwayat_bekerja_mengandung_karsinogen"),
                        rs.getString("lingkungan_tinggal_polusi_tinggi"),rs.getString("nilai_lingkungan_tinggal_polusi_tinggi"),rs.getString("lingkungan_rumah_tidak_sehat"),
                        rs.getString("nilai_lingkungan_rumah_tidak_sehat"),rs.getString("pernah_paru_kronik"),rs.getString("nilai_pernah_paru_kronik"),rs.getString("total_skor"),
                        rs.getString("hasil_skrining"),rs.getString("keterangan")
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
        Tanggal.setDate(new Date());
        JenisKelamin.setSelectedIndex(1);
        NilaiJenisKelamin.setText("1");
        Usia.setSelectedIndex(2);
        NilaiUsia.setText("1");
        PernahMenderitaKanker.setSelectedIndex(2);
        NilaiPernahMenderitaKanker.setText("1");
        KeluargaKanker.setSelectedIndex(2);
        NilaiKeluargaKanker.setText("1");
        RiwayatMerokok.setSelectedIndex(2);
        NilaiRiwayatMerokok.setText("1");
        RiwayatBekerja.setSelectedIndex(2);
        NilaiRiwayatBekerja.setText("1");
        LingkunganTinggal.setSelectedIndex(2);
        NilaiLingkunganTinggal.setText("1");
        DalamRumah.setSelectedIndex(2);
        NilaiDalamRumah.setText("1");
        PernahParu.setSelectedIndex(2);
        NilaiPernahParu.setText("1");
        TotalHasil.setText("9");
        HasilSkrining.setText("Risiko Ringan");
        JenisKelamin.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            JenisKelamin.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NilaiJenisKelamin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Usia.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NilaiUsia.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            PernahMenderitaKanker.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NilaiPernahMenderitaKanker.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KeluargaKanker.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NilaiKeluargaKanker.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            RiwayatMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NilaiRiwayatMerokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            RiwayatBekerja.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NilaiRiwayatBekerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            LingkunganTinggal.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NilaiLingkunganTinggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            DalamRumah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NilaiDalamRumah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            PernahParu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            NilaiPernahParu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            HasilSkrining.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,"+
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
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
                    if(rs.getString("jk").equals("L")){
                        JenisKelamin.setSelectedIndex(0);
                    }else{
                        JenisKelamin.setSelectedIndex(1);
                    }
                    
                    if(rs.getInt("umurdaftar")>65){
                        Usia.setSelectedIndex(0);
                    }else if((rs.getInt("umurdaftar")>44)&&(rs.getInt("umurdaftar")<=65)){
                        Usia.setSelectedIndex(1);
                    }else if(rs.getInt("umurdaftar")<45){
                        Usia.setSelectedIndex(2);
                    }
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
            if(internalFrame1.getHeight()>608){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,436));
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
        BtnSimpan.setEnabled(akses.getskrining_risiko_kanker_paru());
        BtnHapus.setEnabled(akses.getskrining_risiko_kanker_paru());
        BtnEdit.setEnabled(akses.getskrining_risiko_kanker_paru());
        BtnPrint.setEnabled(akses.getskrining_risiko_kanker_paru()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
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
        if(Sequel.mengedittf("skrining_risiko_kanker_paru","no_rawat=?","no_rawat=?,tanggal=?,jenis_kelamin=?,nilai_jenis_kelamin=?,umur=?,nilai_umur=?,pernah_kanker=?,nilai_pernah_kanker=?,"+
                "ada_keluarga_kanker=?,nilai_ada_keluarga_kanker=?,riwayat_rokok=?,nilai_riwayat_rokok=?,riwayat_bekerja_mengandung_karsinogen=?,nilai_riwayat_bekerja_mengandung_karsinogen=?,"+
                "lingkungan_tinggal_polusi_tinggi=?,nilai_lingkungan_tinggal_polusi_tinggi=?,lingkungan_rumah_tidak_sehat=?,nilai_lingkungan_rumah_tidak_sehat=?,pernah_paru_kronik=?,"+
                "nilai_pernah_paru_kronik=?,total_skor=?,hasil_skrining=?,keterangan=?,nip=?",25,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            JenisKelamin.getSelectedItem().toString(),NilaiJenisKelamin.getText(),Usia.getSelectedItem().toString(),NilaiUsia.getText(), 
            PernahMenderitaKanker.getSelectedItem().toString(),NilaiPernahMenderitaKanker.getText(),KeluargaKanker.getSelectedItem().toString(),NilaiKeluargaKanker.getText(), 
            RiwayatMerokok.getSelectedItem().toString(),NilaiRiwayatMerokok.getText(),RiwayatBekerja.getSelectedItem().toString(),NilaiRiwayatBekerja.getText(), 
            LingkunganTinggal.getSelectedItem().toString(),NilaiLingkunganTinggal.getText(),DalamRumah.getSelectedItem().toString(),NilaiDalamRumah.getText(), 
            PernahParu.getSelectedItem().toString(),NilaiPernahParu.getText(),TotalHasil.getText(),HasilSkrining.getText(),Keterangan.getText(),KdPetugas.getText(),
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(JenisKelamin.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(NilaiJenisKelamin.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(Usia.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(NilaiUsia.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(PernahMenderitaKanker.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(NilaiPernahMenderitaKanker.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(KeluargaKanker.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(NilaiKeluargaKanker.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(RiwayatMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(NilaiRiwayatMerokok.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(RiwayatBekerja.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(NilaiRiwayatBekerja.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(LingkunganTinggal.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(NilaiLingkunganTinggal.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(DalamRumah.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(NilaiDalamRumah.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(PernahParu.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(NilaiPernahParu.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(TotalHasil.getText(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(HasilSkrining.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),28);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_risiko_kanker_paru where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void isTotal() {
        try {
            TotalHasil.setText(""+(Integer.parseInt(NilaiJenisKelamin.getText())+Integer.parseInt(NilaiUsia.getText())+Integer.parseInt(NilaiPernahMenderitaKanker.getText())+Integer.parseInt(NilaiKeluargaKanker.getText())+Integer.parseInt(NilaiRiwayatMerokok.getText())+Integer.parseInt(NilaiRiwayatBekerja.getText())+Integer.parseInt(NilaiLingkunganTinggal.getText())+Integer.parseInt(NilaiDalamRumah.getText())+Integer.parseInt(NilaiPernahParu.getText())));
            if(Integer.parseInt(TotalHasil.getText())>16){
                HasilSkrining.setText("Risiko Berat");
            }else if(Integer.parseInt(TotalHasil.getText())>11){
                HasilSkrining.setText("Risiko Sedang");
            }else{
                HasilSkrining.setText("Risiko Ringan");
            }
        } catch (Exception e) {
            HasilSkrining.setText("Risiko Ringan");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("skrining_risiko_kanker_paru","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",24,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            JenisKelamin.getSelectedItem().toString(),NilaiJenisKelamin.getText(),Usia.getSelectedItem().toString(),NilaiUsia.getText(), 
            PernahMenderitaKanker.getSelectedItem().toString(),NilaiPernahMenderitaKanker.getText(),KeluargaKanker.getSelectedItem().toString(),NilaiKeluargaKanker.getText(), 
            RiwayatMerokok.getSelectedItem().toString(),NilaiRiwayatMerokok.getText(),RiwayatBekerja.getSelectedItem().toString(),NilaiRiwayatBekerja.getText(), 
            LingkunganTinggal.getSelectedItem().toString(),NilaiLingkunganTinggal.getText(),DalamRumah.getSelectedItem().toString(),NilaiDalamRumah.getText(), 
            PernahParu.getSelectedItem().toString(),NilaiPernahParu.getText(),TotalHasil.getText(),HasilSkrining.getText(),Keterangan.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Umur.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                JenisKelamin.getSelectedItem().toString(),NilaiJenisKelamin.getText(),Usia.getSelectedItem().toString(),NilaiUsia.getText(),PernahMenderitaKanker.getSelectedItem().toString(),NilaiPernahMenderitaKanker.getText(),KeluargaKanker.getSelectedItem().toString(),
                NilaiKeluargaKanker.getText(),RiwayatMerokok.getSelectedItem().toString(),NilaiRiwayatMerokok.getText(),RiwayatBekerja.getSelectedItem().toString(),NilaiRiwayatBekerja.getText(),LingkunganTinggal.getSelectedItem().toString(),NilaiLingkunganTinggal.getText(),
                DalamRumah.getSelectedItem().toString(),NilaiDalamRumah.getText(),PernahParu.getSelectedItem().toString(),NilaiPernahParu.getText(),TotalHasil.getText(),HasilSkrining.getText(),Keterangan.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
    
}

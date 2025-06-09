/*
  Dilarang keras memperjualbelikan/mengambil keuntungan dari Software 
  ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
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
public final class RMChecklistPemberianFibrinolitik extends javax.swing.JDialog {
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
    public RMChecklistPemberianFibrinolitik(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Petugas","Nama Petugas","Tanggal",
            "Kontra Indikasi 1","Keterangan Kontra Indikasi 1","Kontra Indikasi 2","Keterangan Kontra Indikasi 2",
            "Kontra Indikasi 3","Keterangan Kontra Indikasi 3","Kontra Indikasi 4","Keterangan Kontra Indikasi 4",
            "Kontra Indikasi 5","Keterangan Kontra Indikasi 5","Kontra Indikasi 6","Keterangan Kontra Indikasi 6",
            "Kontra Indikasi 7","Keterangan Kontra Indikasi 7","Kontra Indikasi 8","Keterangan Kontra Indikasi 8",
            "Kontra Indikasi 9","Keterangan Kontra Indikasi 9","Kontra Indikasi 10","Keterangan Kontra Indikasi 10",
            "Risiko Tinggi 1","Keterangan Risiko Tinggi 1","Risiko Tinggi 2","Keterangan Risiko Tinggi 2",
            "Risiko Tinggi 3","Keterangan Risiko Tinggi 3","Risiko Tinggi 4","Keterangan Risiko Tinggi 4",
            "Risiko Tinggi 5","Keterangan Risiko Tinggi 5","Kesimpulan","EKG Pre Streptase","EKG Post Streptase",
            "Cek Troponin"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 42; i++) {
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
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(95);
            }else if(i==9){
                column.setPreferredWidth(152);
            }else if(i==10){
                column.setPreferredWidth(95);
            }else if(i==11){
                column.setPreferredWidth(152);
            }else if(i==12){
                column.setPreferredWidth(95);
            }else if(i==13){
                column.setPreferredWidth(152);
            }else if(i==14){
                column.setPreferredWidth(95);
            }else if(i==15){
                column.setPreferredWidth(152);
            }else if(i==16){
                column.setPreferredWidth(95);
            }else if(i==17){
                column.setPreferredWidth(152);
            }else if(i==18){
                column.setPreferredWidth(95);
            }else if(i==19){
                column.setPreferredWidth(152);
            }else if(i==20){
                column.setPreferredWidth(95);
            }else if(i==21){
                column.setPreferredWidth(152);
            }else if(i==22){
                column.setPreferredWidth(95);
            }else if(i==23){
                column.setPreferredWidth(152);
            }else if(i==24){
                column.setPreferredWidth(95);
            }else if(i==25){
                column.setPreferredWidth(152);
            }else if(i==26){
                column.setPreferredWidth(100);
            }else if(i==27){
                column.setPreferredWidth(157);
            }else if(i==28){
                column.setPreferredWidth(82);
            }else if(i==29){
                column.setPreferredWidth(141);
            }else if(i==30){
                column.setPreferredWidth(82);
            }else if(i==31){
                column.setPreferredWidth(141);
            }else if(i==32){
                column.setPreferredWidth(82);
            }else if(i==33){
                column.setPreferredWidth(141);
            }else if(i==34){
                column.setPreferredWidth(82);
            }else if(i==35){
                column.setPreferredWidth(141);
            }else if(i==36){
                column.setPreferredWidth(82);
            }else if(i==37){
                column.setPreferredWidth(141);
            }else if(i==38){
                column.setPreferredWidth(250);
            }else if(i==39){
                column.setPreferredWidth(150);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        Kesimpulan.setDocument(new batasInput((int)300).getKata(Kesimpulan));
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
        MnChecklistPemberianFibrinolitik = new javax.swing.JMenuItem();
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
        KontraIndikasi1 = new widget.ComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel78 = new widget.Label();
        Kesimpulan = new widget.TextBox();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel124 = new widget.Label();
        KeteranganKontraIndikasi1 = new widget.TextBox();
        jLabel79 = new widget.Label();
        KontraIndikasi4 = new widget.ComboBox();
        KeteranganKontraIndikasi4 = new widget.TextBox();
        KeteranganKontraIndikasi7 = new widget.TextBox();
        KontraIndikasi7 = new widget.ComboBox();
        jLabel81 = new widget.Label();
        KeteranganKontraIndikasi5 = new widget.TextBox();
        KontraIndikasi5 = new widget.ComboBox();
        jLabel82 = new widget.Label();
        jLabel84 = new widget.Label();
        KontraIndikasi6 = new widget.ComboBox();
        KeteranganKontraIndikasi6 = new widget.TextBox();
        jLabel86 = new widget.Label();
        KontraIndikasi8 = new widget.ComboBox();
        KeteranganKontraIndikasi8 = new widget.TextBox();
        jLabel88 = new widget.Label();
        KontraIndikasi9 = new widget.ComboBox();
        KeteranganKontraIndikasi9 = new widget.TextBox();
        jLabel90 = new widget.Label();
        KontraIndikasi2 = new widget.ComboBox();
        KeteranganKontraIndikasi2 = new widget.TextBox();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        jLabel102 = new widget.Label();
        jLabel91 = new widget.Label();
        KontraIndikasi3 = new widget.ComboBox();
        KeteranganKontraIndikasi3 = new widget.TextBox();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        jLabel106 = new widget.Label();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        jLabel89 = new widget.Label();
        jLabel109 = new widget.Label();
        KontraIndikasi10 = new widget.ComboBox();
        KeteranganKontraIndikasi10 = new widget.TextBox();
        jLabel110 = new widget.Label();
        jLabel80 = new widget.Label();
        RisikoTinggi1 = new widget.ComboBox();
        KeteranganRisikoTinggi1 = new widget.TextBox();
        jLabel83 = new widget.Label();
        jLabel111 = new widget.Label();
        RisikoTinggi2 = new widget.ComboBox();
        KeteranganRisikoTinggi2 = new widget.TextBox();
        jLabel112 = new widget.Label();
        jLabel85 = new widget.Label();
        RisikoTinggi3 = new widget.ComboBox();
        KeteranganRisikoTinggi3 = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel113 = new widget.Label();
        RisikoTinggi4 = new widget.ComboBox();
        KeteranganRisikoTinggi4 = new widget.TextBox();
        jLabel92 = new widget.Label();
        jLabel114 = new widget.Label();
        RisikoTinggi5 = new widget.ComboBox();
        KeteranganRisikoTinggi5 = new widget.TextBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel125 = new widget.Label();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel126 = new widget.Label();
        EkgPreStrep = new widget.TextBox();
        jLabel115 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel116 = new widget.Label();
        jLabel20 = new widget.Label();
        EkgPostStrep = new widget.TextBox();
        CekTroponin = new widget.TextBox();
        jLabel117 = new widget.Label();
        jLabel22 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnChecklistPemberianFibrinolitik.setBackground(new java.awt.Color(255, 255, 254));
        MnChecklistPemberianFibrinolitik.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnChecklistPemberianFibrinolitik.setForeground(new java.awt.Color(50, 50, 50));
        MnChecklistPemberianFibrinolitik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnChecklistPemberianFibrinolitik.setText("Formulir Check List Pemberian Fibrinolitik");
        MnChecklistPemberianFibrinolitik.setName("MnChecklistPemberianFibrinolitik"); // NOI18N
        MnChecklistPemberianFibrinolitik.setPreferredSize(new java.awt.Dimension(250, 26));
        MnChecklistPemberianFibrinolitik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnChecklistPemberianFibrinolitikActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnChecklistPemberianFibrinolitik);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Pemberian Fibrinolitik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-11-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-11-2024" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 355));
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
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 506));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(810, 723));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-11-2024" }));
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

        KontraIndikasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi1.setName("KontraIndikasi1"); // NOI18N
        KontraIndikasi1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi1ItemStateChanged(evt);
            }
        });
        KontraIndikasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi1KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi1);
        KontraIndikasi1.setBounds(600, 90, 80, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. PASIEN KONTRA INDIKASI UNTUK PEMBERIAN FIBRINOLISTIK");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(11, 70, 590, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("TB Sistolitik >180-200 mmHg Atau Diastolik >100-110 mmHg");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(62, 90, 430, 23);

        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        FormInput.add(Kesimpulan);
        Kesimpulan.setBounds(44, 580, 745, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 390, 807, 1);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("II. PASIEN RISIKO TINGGI UNTUK PEMBERIAN FIBRINOLITIK");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(11, 390, 540, 23);

        KeteranganKontraIndikasi1.setName("KeteranganKontraIndikasi1"); // NOI18N
        KeteranganKontraIndikasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi1KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi1);
        KeteranganKontraIndikasi1.setBounds(683, 90, 106, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Riwayat Trauma Tertutup Signifikan Pada Kepala / Wajah Dalam 3 Minggu Terakhir");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(62, 180, 490, 23);

        KontraIndikasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi4.setName("KontraIndikasi4"); // NOI18N
        KontraIndikasi4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi4ItemStateChanged(evt);
            }
        });
        KontraIndikasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi4KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi4);
        KontraIndikasi4.setBounds(600, 180, 80, 23);

        KeteranganKontraIndikasi4.setName("KeteranganKontraIndikasi4"); // NOI18N
        KeteranganKontraIndikasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi4KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi4);
        KeteranganKontraIndikasi4.setBounds(683, 180, 106, 23);

        KeteranganKontraIndikasi7.setName("KeteranganKontraIndikasi7"); // NOI18N
        KeteranganKontraIndikasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi7KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi7);
        KeteranganKontraIndikasi7.setBounds(683, 270, 106, 23);

        KontraIndikasi7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi7.setName("KontraIndikasi7"); // NOI18N
        KontraIndikasi7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi7ItemStateChanged(evt);
            }
        });
        KontraIndikasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi7KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi7);
        KontraIndikasi7.setBounds(600, 270, 80, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Riwayat Perdarahan Intrakranial");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(62, 270, 410, 23);

        KeteranganKontraIndikasi5.setName("KeteranganKontraIndikasi5"); // NOI18N
        KeteranganKontraIndikasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi5KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi5);
        KeteranganKontraIndikasi5.setBounds(683, 210, 106, 23);

        KontraIndikasi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi5.setName("KontraIndikasi5"); // NOI18N
        KontraIndikasi5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi5ItemStateChanged(evt);
            }
        });
        KontraIndikasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi5KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi5);
        KontraIndikasi5.setBounds(600, 210, 80, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Stroke Iskemik >3 Jam Atau <3 Bulan");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(62, 210, 470, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Penyakit Sistemik Serius (Misalnya Kanker Tingkat Lanjut, Penyakit Hati, Atau Gagal Ginjal Berat)");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(62, 360, 520, 23);

        KontraIndikasi6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi6.setName("KontraIndikasi6"); // NOI18N
        KontraIndikasi6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi6ItemStateChanged(evt);
            }
        });
        KontraIndikasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi6KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi6);
        KontraIndikasi6.setBounds(600, 240, 80, 23);

        KeteranganKontraIndikasi6.setName("KeteranganKontraIndikasi6"); // NOI18N
        KeteranganKontraIndikasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi6KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi6);
        KeteranganKontraIndikasi6.setBounds(683, 240, 106, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Trauma Mayor, Pembedahan(Termasuk Bedah Laser Mata), Perdarahan Gigi/Gusi Dalam 2-4 Minggu Terakhir");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(62, 240, 550, 23);

        KontraIndikasi8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi8.setName("KontraIndikasi8"); // NOI18N
        KontraIndikasi8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi8ItemStateChanged(evt);
            }
        });
        KontraIndikasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi8KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi8);
        KontraIndikasi8.setBounds(600, 300, 80, 23);

        KeteranganKontraIndikasi8.setName("KeteranganKontraIndikasi8"); // NOI18N
        KeteranganKontraIndikasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi8KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi8);
        KeteranganKontraIndikasi8.setBounds(683, 300, 106, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Perdarahan, Masalah Pembekuan/Penggunaan Antikoagulan");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(62, 300, 510, 23);

        KontraIndikasi9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi9.setName("KontraIndikasi9"); // NOI18N
        KontraIndikasi9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi9ItemStateChanged(evt);
            }
        });
        KontraIndikasi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi9KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi9);
        KontraIndikasi9.setBounds(600, 330, 80, 23);

        KeteranganKontraIndikasi9.setName("KeteranganKontraIndikasi9"); // NOI18N
        KeteranganKontraIndikasi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi9KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi9);
        KeteranganKontraIndikasi9.setBounds(683, 330, 106, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Perbedaan TD Sistolik Lengan Kanan Dan Kiri >15 mmHg");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(62, 120, 470, 23);

        KontraIndikasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi2.setName("KontraIndikasi2"); // NOI18N
        KontraIndikasi2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi2ItemStateChanged(evt);
            }
        });
        KontraIndikasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi2KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi2);
        KontraIndikasi2.setBounds(600, 120, 80, 23);

        KeteranganKontraIndikasi2.setName("KeteranganKontraIndikasi2"); // NOI18N
        KeteranganKontraIndikasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi2KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi2);
        KeteranganKontraIndikasi2.setBounds(683, 120, 106, 23);

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

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Riwayat Penyakit System Saraf Pusat Struktural");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(62, 150, 470, 23);

        KontraIndikasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi3.setName("KontraIndikasi3"); // NOI18N
        KontraIndikasi3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi3ItemStateChanged(evt);
            }
        });
        KontraIndikasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi3KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi3);
        KontraIndikasi3.setBounds(600, 150, 80, 23);

        KeteranganKontraIndikasi3.setName("KeteranganKontraIndikasi3"); // NOI18N
        KeteranganKontraIndikasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi3KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi3);
        KeteranganKontraIndikasi3.setBounds(683, 150, 106, 23);

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

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Wanita Hamil");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(62, 330, 530, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("10.");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(44, 360, 25, 23);

        KontraIndikasi10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KontraIndikasi10.setName("KontraIndikasi10"); // NOI18N
        KontraIndikasi10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KontraIndikasi10ItemStateChanged(evt);
            }
        });
        KontraIndikasi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraIndikasi10KeyPressed(evt);
            }
        });
        FormInput.add(KontraIndikasi10);
        KontraIndikasi10.setBounds(600, 360, 80, 23);

        KeteranganKontraIndikasi10.setName("KeteranganKontraIndikasi10"); // NOI18N
        KeteranganKontraIndikasi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKontraIndikasi10KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKontraIndikasi10);
        KeteranganKontraIndikasi10.setBounds(683, 360, 106, 23);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("1.");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(44, 410, 25, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("HR > 100x/menit Dan TD Sistolik <100 mmhg");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(62, 410, 240, 23);

        RisikoTinggi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RisikoTinggi1.setName("RisikoTinggi1"); // NOI18N
        RisikoTinggi1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RisikoTinggi1ItemStateChanged(evt);
            }
        });
        RisikoTinggi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoTinggi1KeyPressed(evt);
            }
        });
        FormInput.add(RisikoTinggi1);
        RisikoTinggi1.setBounds(600, 410, 80, 23);

        KeteranganRisikoTinggi1.setName("KeteranganRisikoTinggi1"); // NOI18N
        KeteranganRisikoTinggi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRisikoTinggi1KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRisikoTinggi1);
        KeteranganRisikoTinggi1.setBounds(683, 410, 106, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Edema Paru (Rales)");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(62, 440, 430, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("2.");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(44, 440, 25, 23);

        RisikoTinggi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RisikoTinggi2.setName("RisikoTinggi2"); // NOI18N
        RisikoTinggi2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RisikoTinggi2ItemStateChanged(evt);
            }
        });
        RisikoTinggi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoTinggi2KeyPressed(evt);
            }
        });
        FormInput.add(RisikoTinggi2);
        RisikoTinggi2.setBounds(600, 440, 80, 23);

        KeteranganRisikoTinggi2.setName("KeteranganRisikoTinggi2"); // NOI18N
        KeteranganRisikoTinggi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRisikoTinggi2KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRisikoTinggi2);
        KeteranganRisikoTinggi2.setBounds(683, 440, 106, 23);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("3.");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(44, 470, 25, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Tanda-tanda Syok (Dingin, Lembab)");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(62, 470, 430, 23);

        RisikoTinggi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RisikoTinggi3.setName("RisikoTinggi3"); // NOI18N
        RisikoTinggi3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RisikoTinggi3ItemStateChanged(evt);
            }
        });
        RisikoTinggi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoTinggi3KeyPressed(evt);
            }
        });
        FormInput.add(RisikoTinggi3);
        RisikoTinggi3.setBounds(600, 470, 80, 23);

        KeteranganRisikoTinggi3.setName("KeteranganRisikoTinggi3"); // NOI18N
        KeteranganRisikoTinggi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRisikoTinggi3KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRisikoTinggi3);
        KeteranganRisikoTinggi3.setBounds(683, 470, 106, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Kontradiksi Terapi Fibrinolitik");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(62, 500, 430, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("4.");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(44, 500, 25, 23);

        RisikoTinggi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RisikoTinggi4.setName("RisikoTinggi4"); // NOI18N
        RisikoTinggi4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RisikoTinggi4ItemStateChanged(evt);
            }
        });
        RisikoTinggi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoTinggi4KeyPressed(evt);
            }
        });
        FormInput.add(RisikoTinggi4);
        RisikoTinggi4.setBounds(600, 500, 80, 23);

        KeteranganRisikoTinggi4.setName("KeteranganRisikoTinggi4"); // NOI18N
        KeteranganRisikoTinggi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRisikoTinggi4KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRisikoTinggi4);
        KeteranganRisikoTinggi4.setBounds(683, 500, 106, 23);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("Memerlukan RJP");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(62, 530, 430, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("5.");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(44, 530, 25, 23);

        RisikoTinggi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RisikoTinggi5.setName("RisikoTinggi5"); // NOI18N
        RisikoTinggi5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RisikoTinggi5ItemStateChanged(evt);
            }
        });
        RisikoTinggi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoTinggi5KeyPressed(evt);
            }
        });
        FormInput.add(RisikoTinggi5);
        RisikoTinggi5.setBounds(600, 530, 80, 23);

        KeteranganRisikoTinggi5.setName("KeteranganRisikoTinggi5"); // NOI18N
        KeteranganRisikoTinggi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRisikoTinggi5KeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRisikoTinggi5);
        KeteranganRisikoTinggi5.setBounds(683, 530, 106, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 560, 807, 1);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("III. KESIMPULAN");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(11, 560, 650, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 610, 807, 1);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("IV. PERSYARATAN");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(11, 610, 650, 23);

        EkgPreStrep.setName("EkgPreStrep"); // NOI18N
        EkgPreStrep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkgPreStrepKeyPressed(evt);
            }
        });
        FormInput.add(EkgPreStrep);
        EkgPreStrep.setBounds(143, 630, 646, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("EKG Pre Streptase");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(44, 630, 110, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 630, 139, 23);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("EKG Post Streptase");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(44, 660, 110, 23);

        jLabel20.setText(":");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 660, 144, 23);

        EkgPostStrep.setName("EkgPostStrep"); // NOI18N
        EkgPostStrep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkgPostStrepKeyPressed(evt);
            }
        });
        FormInput.add(EkgPostStrep);
        EkgPostStrep.setBounds(148, 660, 641, 23);

        CekTroponin.setName("CekTroponin"); // NOI18N
        CekTroponin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CekTroponinKeyPressed(evt);
            }
        });
        FormInput.add(CekTroponin);
        CekTroponin.setBounds(119, 690, 670, 23);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Cek Troponin ");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(44, 690, 110, 23);

        jLabel22.setText(":");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 690, 115, 23);

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
            Valid.pindah(evt,CekTroponin,BtnBatal);
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontra Indikasi 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kontra Indikasi 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Tinggi 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Risiko Tinggi 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Tinggi 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Risiko Tinggi 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Tinggi 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Risiko Tinggi 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Tinggi 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Risiko Tinggi 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Tinggi 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Risiko Tinggi 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>EKG Pre Streptase</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>EKG Post Streptase</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='5000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataChecklistPemberianFibrinolitik.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='5000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECKLIST PEMBERIAN FIBRINOLITIK<br><br></font>"+        
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
        Valid.pindah(evt,Detik,btnPetugas);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnChecklistPemberianFibrinolitikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnChecklistPemberianFibrinolitikActionPerformed
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
            Valid.MyReportqry("rptFormulirChecklistPemberianFibrinolitik.jasper","report","::[ Formulir Check List Pemberian Fibrinolitik ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_pemberian_fibrinolitik.nip,petugas.nama,checklist_pemberian_fibrinolitik.tanggal,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi1,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi1,checklist_pemberian_fibrinolitik.kontra_indikasi2,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi2,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi3,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi3,checklist_pemberian_fibrinolitik.kontra_indikasi4,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi4,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi5,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi5,checklist_pemberian_fibrinolitik.kontra_indikasi6,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi6,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi7,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi7,checklist_pemberian_fibrinolitik.kontra_indikasi8,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi8,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi9,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi9,checklist_pemberian_fibrinolitik.kontra_indikasi10,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi10,"+
                    "checklist_pemberian_fibrinolitik.risiko_tinggi1,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi1,checklist_pemberian_fibrinolitik.risiko_tinggi2,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi2,"+
                    "checklist_pemberian_fibrinolitik.risiko_tinggi3,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi3,checklist_pemberian_fibrinolitik.risiko_tinggi4,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi4,"+
                    "checklist_pemberian_fibrinolitik.risiko_tinggi5,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi5,checklist_pemberian_fibrinolitik.kesimpulan,checklist_pemberian_fibrinolitik.persyaratan_ekg_pre_streptase,"+
                    "checklist_pemberian_fibrinolitik.persyaratan_ekg_post_streptase,checklist_pemberian_fibrinolitik.cek_troponin from checklist_pemberian_fibrinolitik inner join reg_periksa on checklist_pemberian_fibrinolitik.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on checklist_pemberian_fibrinolitik.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnChecklistPemberianFibrinolitikActionPerformed

    private void KontraIndikasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi1KeyPressed
        Valid.pindah(evt,btnPetugas,KeteranganKontraIndikasi1);
    }//GEN-LAST:event_KontraIndikasi1KeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        Valid.pindah(evt,KeteranganRisikoTinggi5,EkgPreStrep);
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void KeteranganKontraIndikasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi1KeyPressed
        Valid.pindah(evt,KontraIndikasi1,KontraIndikasi2);
    }//GEN-LAST:event_KeteranganKontraIndikasi1KeyPressed

    private void KontraIndikasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi4KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi3,KeteranganKontraIndikasi4);
    }//GEN-LAST:event_KontraIndikasi4KeyPressed

    private void KeteranganKontraIndikasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi4KeyPressed
        Valid.pindah(evt,KontraIndikasi4,KontraIndikasi5);
    }//GEN-LAST:event_KeteranganKontraIndikasi4KeyPressed

    private void KeteranganKontraIndikasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi7KeyPressed
        Valid.pindah(evt,KontraIndikasi7,KontraIndikasi8);
    }//GEN-LAST:event_KeteranganKontraIndikasi7KeyPressed

    private void KontraIndikasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi7KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi6,KeteranganKontraIndikasi7);
    }//GEN-LAST:event_KontraIndikasi7KeyPressed

    private void KeteranganKontraIndikasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi5KeyPressed
        Valid.pindah(evt,KontraIndikasi5,KontraIndikasi6);
    }//GEN-LAST:event_KeteranganKontraIndikasi5KeyPressed

    private void KontraIndikasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi5KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi4,KeteranganKontraIndikasi5);
    }//GEN-LAST:event_KontraIndikasi5KeyPressed

    private void KontraIndikasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi6KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi5,KeteranganKontraIndikasi6);
    }//GEN-LAST:event_KontraIndikasi6KeyPressed

    private void KeteranganKontraIndikasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi6KeyPressed
        Valid.pindah(evt,KontraIndikasi6,KontraIndikasi7);
    }//GEN-LAST:event_KeteranganKontraIndikasi6KeyPressed

    private void KontraIndikasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi8KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi7,KeteranganKontraIndikasi8);
    }//GEN-LAST:event_KontraIndikasi8KeyPressed

    private void KeteranganKontraIndikasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi8KeyPressed
        Valid.pindah(evt,KontraIndikasi8,KontraIndikasi9);
    }//GEN-LAST:event_KeteranganKontraIndikasi8KeyPressed

    private void KontraIndikasi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi9KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi8,KeteranganKontraIndikasi9);
    }//GEN-LAST:event_KontraIndikasi9KeyPressed

    private void KeteranganKontraIndikasi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi9KeyPressed
        Valid.pindah(evt,KontraIndikasi9,KontraIndikasi10);
    }//GEN-LAST:event_KeteranganKontraIndikasi9KeyPressed

    private void KontraIndikasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi2KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi1,KeteranganKontraIndikasi2);
    }//GEN-LAST:event_KontraIndikasi2KeyPressed

    private void KeteranganKontraIndikasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi2KeyPressed
        Valid.pindah(evt,KontraIndikasi2,KontraIndikasi3);
    }//GEN-LAST:event_KeteranganKontraIndikasi2KeyPressed

    private void KontraIndikasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi3KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi2,KeteranganKontraIndikasi3);
    }//GEN-LAST:event_KontraIndikasi3KeyPressed

    private void KeteranganKontraIndikasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi3KeyPressed
        Valid.pindah(evt,KontraIndikasi3,KontraIndikasi4);
    }//GEN-LAST:event_KeteranganKontraIndikasi3KeyPressed

    private void KontraIndikasi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraIndikasi10KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi9,KeteranganKontraIndikasi10);
    }//GEN-LAST:event_KontraIndikasi10KeyPressed

    private void KeteranganKontraIndikasi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKontraIndikasi10KeyPressed
        Valid.pindah(evt,KontraIndikasi10,RisikoTinggi1);
    }//GEN-LAST:event_KeteranganKontraIndikasi10KeyPressed

    private void RisikoTinggi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoTinggi1KeyPressed
        Valid.pindah(evt,KeteranganKontraIndikasi10,KeteranganRisikoTinggi1);
    }//GEN-LAST:event_RisikoTinggi1KeyPressed

    private void KeteranganRisikoTinggi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRisikoTinggi1KeyPressed
        Valid.pindah(evt,RisikoTinggi1,RisikoTinggi2);
    }//GEN-LAST:event_KeteranganRisikoTinggi1KeyPressed

    private void RisikoTinggi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoTinggi2KeyPressed
        Valid.pindah(evt,KeteranganRisikoTinggi1,KeteranganRisikoTinggi2);
    }//GEN-LAST:event_RisikoTinggi2KeyPressed

    private void KeteranganRisikoTinggi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRisikoTinggi2KeyPressed
        Valid.pindah(evt,RisikoTinggi2,RisikoTinggi3);
    }//GEN-LAST:event_KeteranganRisikoTinggi2KeyPressed

    private void RisikoTinggi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoTinggi3KeyPressed
        Valid.pindah(evt,KeteranganRisikoTinggi2,KeteranganRisikoTinggi3);
    }//GEN-LAST:event_RisikoTinggi3KeyPressed

    private void KeteranganRisikoTinggi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRisikoTinggi3KeyPressed
        Valid.pindah(evt,RisikoTinggi3,RisikoTinggi4);
    }//GEN-LAST:event_KeteranganRisikoTinggi3KeyPressed

    private void RisikoTinggi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoTinggi4KeyPressed
        Valid.pindah(evt,KeteranganRisikoTinggi3,KeteranganRisikoTinggi4);
    }//GEN-LAST:event_RisikoTinggi4KeyPressed

    private void KeteranganRisikoTinggi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRisikoTinggi4KeyPressed
        Valid.pindah(evt,RisikoTinggi4,RisikoTinggi5);
    }//GEN-LAST:event_KeteranganRisikoTinggi4KeyPressed

    private void RisikoTinggi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoTinggi5KeyPressed
        Valid.pindah(evt,KeteranganRisikoTinggi4,KeteranganRisikoTinggi5);
    }//GEN-LAST:event_RisikoTinggi5KeyPressed

    private void KeteranganRisikoTinggi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRisikoTinggi5KeyPressed
        Valid.pindah(evt,RisikoTinggi5,Kesimpulan);
    }//GEN-LAST:event_KeteranganRisikoTinggi5KeyPressed

    private void EkgPreStrepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkgPreStrepKeyPressed
        Valid.pindah(evt,Kesimpulan,EkgPostStrep);
    }//GEN-LAST:event_EkgPreStrepKeyPressed

    private void EkgPostStrepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkgPostStrepKeyPressed
        Valid.pindah(evt,EkgPreStrep,CekTroponin);
    }//GEN-LAST:event_EkgPostStrepKeyPressed

    private void CekTroponinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CekTroponinKeyPressed
        Valid.pindah(evt,EkgPostStrep,BtnSimpan);
    }//GEN-LAST:event_CekTroponinKeyPressed

    private void KontraIndikasi1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi1ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi1ItemStateChanged

    private void KontraIndikasi2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi2ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi2ItemStateChanged

    private void KontraIndikasi3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi3ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi3ItemStateChanged

    private void KontraIndikasi4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi4ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi4ItemStateChanged

    private void KontraIndikasi5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi5ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi5ItemStateChanged

    private void KontraIndikasi6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi6ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi6ItemStateChanged

    private void KontraIndikasi7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi7ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi7ItemStateChanged

    private void KontraIndikasi8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi8ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi8ItemStateChanged

    private void KontraIndikasi9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi9ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi9ItemStateChanged

    private void KontraIndikasi10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KontraIndikasi10ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_KontraIndikasi10ItemStateChanged

    private void RisikoTinggi1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RisikoTinggi1ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_RisikoTinggi1ItemStateChanged

    private void RisikoTinggi2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RisikoTinggi2ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_RisikoTinggi2ItemStateChanged

    private void RisikoTinggi3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RisikoTinggi3ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_RisikoTinggi3ItemStateChanged

    private void RisikoTinggi4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RisikoTinggi4ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_RisikoTinggi4ItemStateChanged

    private void RisikoTinggi5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RisikoTinggi5ItemStateChanged
        HasilKesimpulan();
    }//GEN-LAST:event_RisikoTinggi5ItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistPemberianFibrinolitik dialog = new RMChecklistPemberianFibrinolitik(new javax.swing.JFrame(), true);
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
    private widget.TextBox CekTroponin;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.TextBox EkgPostStrep;
    private widget.TextBox EkgPreStrep;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Jam;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox Kesimpulan;
    private widget.TextBox KeteranganKontraIndikasi1;
    private widget.TextBox KeteranganKontraIndikasi10;
    private widget.TextBox KeteranganKontraIndikasi2;
    private widget.TextBox KeteranganKontraIndikasi3;
    private widget.TextBox KeteranganKontraIndikasi4;
    private widget.TextBox KeteranganKontraIndikasi5;
    private widget.TextBox KeteranganKontraIndikasi6;
    private widget.TextBox KeteranganKontraIndikasi7;
    private widget.TextBox KeteranganKontraIndikasi8;
    private widget.TextBox KeteranganKontraIndikasi9;
    private widget.TextBox KeteranganRisikoTinggi1;
    private widget.TextBox KeteranganRisikoTinggi2;
    private widget.TextBox KeteranganRisikoTinggi3;
    private widget.TextBox KeteranganRisikoTinggi4;
    private widget.TextBox KeteranganRisikoTinggi5;
    private widget.ComboBox KontraIndikasi1;
    private widget.ComboBox KontraIndikasi10;
    private widget.ComboBox KontraIndikasi2;
    private widget.ComboBox KontraIndikasi3;
    private widget.ComboBox KontraIndikasi4;
    private widget.ComboBox KontraIndikasi5;
    private widget.ComboBox KontraIndikasi6;
    private widget.ComboBox KontraIndikasi7;
    private widget.ComboBox KontraIndikasi8;
    private widget.ComboBox KontraIndikasi9;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnChecklistPemberianFibrinolitik;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox RisikoTinggi1;
    private widget.ComboBox RisikoTinggi2;
    private widget.ComboBox RisikoTinggi3;
    private widget.ComboBox RisikoTinggi4;
    private widget.ComboBox RisikoTinggi5;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.Button btnPetugas;
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
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
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
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_pemberian_fibrinolitik.nip,petugas.nama,checklist_pemberian_fibrinolitik.tanggal,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi1,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi1,checklist_pemberian_fibrinolitik.kontra_indikasi2,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi2,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi3,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi3,checklist_pemberian_fibrinolitik.kontra_indikasi4,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi4,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi5,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi5,checklist_pemberian_fibrinolitik.kontra_indikasi6,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi6,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi7,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi7,checklist_pemberian_fibrinolitik.kontra_indikasi8,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi8,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi9,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi9,checklist_pemberian_fibrinolitik.kontra_indikasi10,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi10,"+
                    "checklist_pemberian_fibrinolitik.risiko_tinggi1,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi1,checklist_pemberian_fibrinolitik.risiko_tinggi2,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi2,"+
                    "checklist_pemberian_fibrinolitik.risiko_tinggi3,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi3,checklist_pemberian_fibrinolitik.risiko_tinggi4,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi4,"+
                    "checklist_pemberian_fibrinolitik.risiko_tinggi5,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi5,checklist_pemberian_fibrinolitik.kesimpulan,checklist_pemberian_fibrinolitik.persyaratan_ekg_pre_streptase,"+
                    "checklist_pemberian_fibrinolitik.persyaratan_ekg_post_streptase,checklist_pemberian_fibrinolitik.cek_troponin from checklist_pemberian_fibrinolitik inner join reg_periksa on checklist_pemberian_fibrinolitik.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on checklist_pemberian_fibrinolitik.nip=petugas.nip "+
                    "where checklist_pemberian_fibrinolitik.tanggal between ? and ? order by checklist_pemberian_fibrinolitik.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_pemberian_fibrinolitik.nip,petugas.nama,checklist_pemberian_fibrinolitik.tanggal,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi1,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi1,checklist_pemberian_fibrinolitik.kontra_indikasi2,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi2,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi3,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi3,checklist_pemberian_fibrinolitik.kontra_indikasi4,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi4,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi5,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi5,checklist_pemberian_fibrinolitik.kontra_indikasi6,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi6,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi7,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi7,checklist_pemberian_fibrinolitik.kontra_indikasi8,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi8,"+
                    "checklist_pemberian_fibrinolitik.kontra_indikasi9,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi9,checklist_pemberian_fibrinolitik.kontra_indikasi10,checklist_pemberian_fibrinolitik.keterangan_kontra_indikasi10,"+
                    "checklist_pemberian_fibrinolitik.risiko_tinggi1,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi1,checklist_pemberian_fibrinolitik.risiko_tinggi2,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi2,"+
                    "checklist_pemberian_fibrinolitik.risiko_tinggi3,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi3,checklist_pemberian_fibrinolitik.risiko_tinggi4,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi4,"+
                    "checklist_pemberian_fibrinolitik.risiko_tinggi5,checklist_pemberian_fibrinolitik.keterangan_risiko_tinggi5,checklist_pemberian_fibrinolitik.kesimpulan,checklist_pemberian_fibrinolitik.persyaratan_ekg_pre_streptase,"+
                    "checklist_pemberian_fibrinolitik.persyaratan_ekg_post_streptase,checklist_pemberian_fibrinolitik.cek_troponin from checklist_pemberian_fibrinolitik inner join reg_periksa on checklist_pemberian_fibrinolitik.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on checklist_pemberian_fibrinolitik.nip=petugas.nip "+
                    "where checklist_pemberian_fibrinolitik.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or checklist_pemberian_fibrinolitik.nip like ? or petugas.nama like ?) "+
                    "order by checklist_pemberian_fibrinolitik.tanggal ");
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
                        rs.getString("kontra_indikasi1"),rs.getString("keterangan_kontra_indikasi1"),rs.getString("kontra_indikasi2"),rs.getString("keterangan_kontra_indikasi2"),rs.getString("kontra_indikasi3"),
                        rs.getString("keterangan_kontra_indikasi3"),rs.getString("kontra_indikasi4"),rs.getString("keterangan_kontra_indikasi4"),rs.getString("kontra_indikasi5"),rs.getString("keterangan_kontra_indikasi5"),
                        rs.getString("kontra_indikasi6"),rs.getString("keterangan_kontra_indikasi6"),rs.getString("kontra_indikasi7"),rs.getString("keterangan_kontra_indikasi7"),rs.getString("kontra_indikasi8"),
                        rs.getString("keterangan_kontra_indikasi8"),rs.getString("kontra_indikasi9"),rs.getString("keterangan_kontra_indikasi9"),rs.getString("kontra_indikasi10"),rs.getString("keterangan_kontra_indikasi10"),
                        rs.getString("risiko_tinggi1"),rs.getString("keterangan_risiko_tinggi1"),rs.getString("risiko_tinggi2"),rs.getString("keterangan_risiko_tinggi2"),rs.getString("risiko_tinggi3"),
                        rs.getString("keterangan_risiko_tinggi3"),rs.getString("risiko_tinggi4"),rs.getString("keterangan_risiko_tinggi4"),rs.getString("risiko_tinggi5"),rs.getString("keterangan_risiko_tinggi5"),
                        rs.getString("kesimpulan"),rs.getString("persyaratan_ekg_pre_streptase"),rs.getString("persyaratan_ekg_post_streptase"),rs.getString("cek_troponin")
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
        KontraIndikasi1.setSelectedIndex(0);
        Kesimpulan.setText("Aman untuk diberikan streptokinase.");
        KontraIndikasi1.setSelectedIndex(0);
        KeteranganKontraIndikasi1.setText("");
        KontraIndikasi2.setSelectedIndex(0);
        KeteranganKontraIndikasi2.setText("");
        KontraIndikasi3.setSelectedIndex(0);
        KeteranganKontraIndikasi3.setText("");
        KontraIndikasi4.setSelectedIndex(0);
        KeteranganKontraIndikasi4.setText("");
        KontraIndikasi5.setSelectedIndex(0);
        KeteranganKontraIndikasi5.setText("");
        KontraIndikasi6.setSelectedIndex(0);
        KeteranganKontraIndikasi6.setText("");
        KontraIndikasi7.setSelectedIndex(0);
        KeteranganKontraIndikasi7.setText("");
        KontraIndikasi8.setSelectedIndex(0);
        KeteranganKontraIndikasi8.setText("");
        KontraIndikasi9.setSelectedIndex(0);
        KeteranganKontraIndikasi9.setText("");
        KontraIndikasi10.setSelectedIndex(0);
        KeteranganKontraIndikasi10.setText("");
        RisikoTinggi1.setSelectedIndex(0);
        KeteranganRisikoTinggi1.setText("");
        RisikoTinggi2.setSelectedIndex(0);
        KeteranganRisikoTinggi2.setText("");
        RisikoTinggi3.setSelectedIndex(0);
        KeteranganRisikoTinggi3.setText("");
        RisikoTinggi4.setSelectedIndex(0);
        KeteranganRisikoTinggi4.setText("");
        RisikoTinggi5.setSelectedIndex(0);
        KeteranganRisikoTinggi5.setText("");
        Kesimpulan.setText("");
        EkgPreStrep.setText("");
        EkgPostStrep.setText("");
        CekTroponin.setText("");
        Tanggal.setDate(new Date());
        KontraIndikasi1.requestFocus();
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
            KontraIndikasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KeteranganKontraIndikasi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KontraIndikasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KeteranganKontraIndikasi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            KontraIndikasi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KeteranganKontraIndikasi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KontraIndikasi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            KeteranganKontraIndikasi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KontraIndikasi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            KeteranganKontraIndikasi5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KontraIndikasi6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            KeteranganKontraIndikasi6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KontraIndikasi7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            KeteranganKontraIndikasi7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KontraIndikasi8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            KeteranganKontraIndikasi8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KontraIndikasi9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KeteranganKontraIndikasi9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KontraIndikasi10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            KeteranganKontraIndikasi10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            RisikoTinggi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            KeteranganRisikoTinggi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            RisikoTinggi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            KeteranganRisikoTinggi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            RisikoTinggi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            KeteranganRisikoTinggi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            RisikoTinggi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            KeteranganRisikoTinggi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            RisikoTinggi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            KeteranganRisikoTinggi5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            EkgPreStrep.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            EkgPostStrep.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            CekTroponin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
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
        BtnSimpan.setEnabled(akses.getchecklist_pemberian_fibrinolitik());
        BtnHapus.setEnabled(akses.getchecklist_pemberian_fibrinolitik());
        BtnEdit.setEnabled(akses.getchecklist_pemberian_fibrinolitik());
        BtnPrint.setEnabled(akses.getchecklist_pemberian_fibrinolitik()); 
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
        if(Sequel.mengedittf("checklist_pemberian_fibrinolitik","no_rawat=?","no_rawat=?,tanggal=?,nip=?,kontra_indikasi1=?,keterangan_kontra_indikasi1=?,kontra_indikasi2=?,keterangan_kontra_indikasi2=?,"+
                "kontra_indikasi3=?,keterangan_kontra_indikasi3=?,kontra_indikasi4=?,keterangan_kontra_indikasi4=?,kontra_indikasi5=?,keterangan_kontra_indikasi5=?,kontra_indikasi6=?,keterangan_kontra_indikasi6=?,"+
                "kontra_indikasi7=?,keterangan_kontra_indikasi7=?,kontra_indikasi8=?,keterangan_kontra_indikasi8=?,kontra_indikasi9=?,keterangan_kontra_indikasi9=?,kontra_indikasi10=?,keterangan_kontra_indikasi10=?,"+
                "risiko_tinggi1=?,keterangan_risiko_tinggi1=?,risiko_tinggi2=?,keterangan_risiko_tinggi2=?,risiko_tinggi3=?,keterangan_risiko_tinggi3=?,risiko_tinggi4=?,keterangan_risiko_tinggi4=?,risiko_tinggi5=?,"+
                "keterangan_risiko_tinggi5=?,kesimpulan=?,persyaratan_ekg_pre_streptase=?,persyaratan_ekg_post_streptase=?,cek_troponin=?",38,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdPetugas.getText(),
                KontraIndikasi1.getSelectedItem().toString(),KeteranganKontraIndikasi1.getText(),KontraIndikasi2.getSelectedItem().toString(),KeteranganKontraIndikasi2.getText(),
                KontraIndikasi3.getSelectedItem().toString(),KeteranganKontraIndikasi3.getText(),KontraIndikasi4.getSelectedItem().toString(),KeteranganKontraIndikasi4.getText(),
                KontraIndikasi5.getSelectedItem().toString(),KeteranganKontraIndikasi5.getText(),KontraIndikasi6.getSelectedItem().toString(),KeteranganKontraIndikasi6.getText(),
                KontraIndikasi7.getSelectedItem().toString(),KeteranganKontraIndikasi7.getText(),KontraIndikasi8.getSelectedItem().toString(),KeteranganKontraIndikasi8.getText(),
                KontraIndikasi9.getSelectedItem().toString(),KeteranganKontraIndikasi9.getText(),KontraIndikasi10.getSelectedItem().toString(),KeteranganKontraIndikasi10.getText(),
                RisikoTinggi1.getSelectedItem().toString(),KeteranganRisikoTinggi1.getText(),RisikoTinggi2.getSelectedItem().toString(),KeteranganRisikoTinggi2.getText(),
                RisikoTinggi3.getSelectedItem().toString(),KeteranganRisikoTinggi3.getText(),RisikoTinggi4.getSelectedItem().toString(),KeteranganRisikoTinggi4.getText(),
                RisikoTinggi5.getSelectedItem().toString(),KeteranganRisikoTinggi5.getText(),Kesimpulan.getText(),EkgPreStrep.getText(),EkgPostStrep.getText(),CekTroponin.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(KontraIndikasi1.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(KeteranganKontraIndikasi1.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(KontraIndikasi2.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(KeteranganKontraIndikasi2.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(KontraIndikasi3.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(KeteranganKontraIndikasi3.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(KontraIndikasi4.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(KeteranganKontraIndikasi4.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(KontraIndikasi5.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(KeteranganKontraIndikasi5.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(KontraIndikasi6.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(KeteranganKontraIndikasi6.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(KontraIndikasi7.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(KeteranganKontraIndikasi7.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(KontraIndikasi8.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(KeteranganKontraIndikasi8.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(KontraIndikasi9.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(KeteranganKontraIndikasi9.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(KontraIndikasi10.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(KeteranganKontraIndikasi10.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(RisikoTinggi1.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(KeteranganRisikoTinggi1.getText(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(RisikoTinggi2.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(KeteranganRisikoTinggi2.getText(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(RisikoTinggi3.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(KeteranganRisikoTinggi3.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(RisikoTinggi4.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(KeteranganRisikoTinggi4.getText(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(RisikoTinggi5.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(KeteranganRisikoTinggi5.getText(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(Kesimpulan.getText(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(EkgPreStrep.getText(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(EkgPostStrep.getText(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(CekTroponin.getText(),tbObat.getSelectedRow(),41);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_pemberian_fibrinolitik where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void HasilKesimpulan(){
        if(KontraIndikasi1.getSelectedItem().toString().equals("Tidak")&&KontraIndikasi2.getSelectedItem().toString().equals("Tidak")&&
            KontraIndikasi3.getSelectedItem().toString().equals("Tidak")&&KontraIndikasi4.getSelectedItem().toString().equals("Tidak")&&
            KontraIndikasi5.getSelectedItem().toString().equals("Tidak")&&KontraIndikasi6.getSelectedItem().toString().equals("Tidak")&&
            KontraIndikasi7.getSelectedItem().toString().equals("Tidak")&&KontraIndikasi8.getSelectedItem().toString().equals("Tidak")&&
            KontraIndikasi9.getSelectedItem().toString().equals("Tidak")&&KontraIndikasi10.getSelectedItem().toString().equals("Tidak")&&
            RisikoTinggi1.getSelectedItem().toString().equals("Tidak")&&RisikoTinggi2.getSelectedItem().toString().equals("Tidak")&&
            RisikoTinggi3.getSelectedItem().toString().equals("Tidak")&&RisikoTinggi4.getSelectedItem().toString().equals("Tidak")&&
            RisikoTinggi5.getSelectedItem().toString().equals("Tidak")){
            Kesimpulan.setText("Aman untuk diberikan streptokinase.");
        }else{
            Kesimpulan.setText("Tidak aman untuk diberikan streptokinase.");    
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("checklist_pemberian_fibrinolitik","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",37,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdPetugas.getText(),
            KontraIndikasi1.getSelectedItem().toString(),KeteranganKontraIndikasi1.getText(),KontraIndikasi2.getSelectedItem().toString(),KeteranganKontraIndikasi2.getText(),
            KontraIndikasi3.getSelectedItem().toString(),KeteranganKontraIndikasi3.getText(),KontraIndikasi4.getSelectedItem().toString(),KeteranganKontraIndikasi4.getText(),
            KontraIndikasi5.getSelectedItem().toString(),KeteranganKontraIndikasi5.getText(),KontraIndikasi6.getSelectedItem().toString(),KeteranganKontraIndikasi6.getText(),
            KontraIndikasi7.getSelectedItem().toString(),KeteranganKontraIndikasi7.getText(),KontraIndikasi8.getSelectedItem().toString(),KeteranganKontraIndikasi8.getText(),
            KontraIndikasi9.getSelectedItem().toString(),KeteranganKontraIndikasi9.getText(),KontraIndikasi10.getSelectedItem().toString(),KeteranganKontraIndikasi10.getText(),
            RisikoTinggi1.getSelectedItem().toString(),KeteranganRisikoTinggi1.getText(),RisikoTinggi2.getSelectedItem().toString(),KeteranganRisikoTinggi2.getText(),
            RisikoTinggi3.getSelectedItem().toString(),KeteranganRisikoTinggi3.getText(),RisikoTinggi4.getSelectedItem().toString(),KeteranganRisikoTinggi4.getText(),
            RisikoTinggi5.getSelectedItem().toString(),KeteranganRisikoTinggi5.getText(),Kesimpulan.getText(),EkgPreStrep.getText(),EkgPostStrep.getText(),CekTroponin.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                KontraIndikasi1.getSelectedItem().toString(),KeteranganKontraIndikasi1.getText(),KontraIndikasi2.getSelectedItem().toString(),KeteranganKontraIndikasi2.getText(),
                KontraIndikasi3.getSelectedItem().toString(),KeteranganKontraIndikasi3.getText(),KontraIndikasi4.getSelectedItem().toString(),KeteranganKontraIndikasi4.getText(),
                KontraIndikasi5.getSelectedItem().toString(),KeteranganKontraIndikasi5.getText(),KontraIndikasi6.getSelectedItem().toString(),KeteranganKontraIndikasi6.getText(),
                KontraIndikasi7.getSelectedItem().toString(),KeteranganKontraIndikasi7.getText(),KontraIndikasi8.getSelectedItem().toString(),KeteranganKontraIndikasi8.getText(),
                KontraIndikasi9.getSelectedItem().toString(),KeteranganKontraIndikasi9.getText(),KontraIndikasi10.getSelectedItem().toString(),KeteranganKontraIndikasi10.getText(),
                RisikoTinggi1.getSelectedItem().toString(),KeteranganRisikoTinggi1.getText(),RisikoTinggi2.getSelectedItem().toString(),KeteranganRisikoTinggi2.getText(),
                RisikoTinggi3.getSelectedItem().toString(),KeteranganRisikoTinggi3.getText(),RisikoTinggi4.getSelectedItem().toString(),KeteranganRisikoTinggi4.getText(),
                RisikoTinggi5.getSelectedItem().toString(),KeteranganRisikoTinggi5.getText(),Kesimpulan.getText(),EkgPreStrep.getText(),EkgPostStrep.getText(),CekTroponin.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }
    }
}

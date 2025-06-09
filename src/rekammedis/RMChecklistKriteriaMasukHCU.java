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
public final class RMChecklistKriteriaMasukHCU extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private String finger="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMChecklistKriteriaMasukHCU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","Gangguan Sirkulasi Atau Pre Dan Pasca Operasi (Syok Hypovolemic)",
            "Hypertensi Emergency","HR 60x/menit (Tiidak Stabil Hasil EKG Gambaran Mengancam Nyawa)","Gagal Jantung Acute","Menggunakan Inotropik / Vasoaktif Gent",
            "MAP < 60 mmHg","R <8 x/ menit > 25 x/menit","Trauma Thorax / Peumothorax","Oxigenisasi Tidak Cukup Dari Hasil AGD","Kesadaran Dengan GCS >= 7",
            "Temperatur <35 C / >38 C","Trauma Kepala Sedang - Berat","Kejang Yang Tidak Memerlukan Ventilator / Cerebro Vasculer / Neoromusculer / Infeksi Syaraf",
            "Gangguan Elektrolit (Na, Ca,CI, Mg, Cal) & Asam Basa","Hypeglikemia & Hypoglikemia, Ketoasidosis Metabolic","Pendarahan Saluran Pencernaan Tanpa Hypotensi & Repon Dengan Cairan",
            "Pengobatan Keracunan","Penyulit Pasca Pembedahan","Pasca Pembedahan Hemodinamik Stabil Tetapi Masih Perlu Resusitasi Cairan","Gangguan Imunologi (Reaksi Alergi, Steven Jhonson) dll",
            "DIC, Anemia Berat, Reaksi Penolakan Transfusi Darah","Semua Infeksi Yang Menyebabkan Penurunan Kesadaran & Tidak Memerlukan Ventilator","NIP/Kode Dokter","DPJP/Dokter Jaga/IGD"
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
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==28){
                column.setPreferredWidth(90);
            }else if(i==29){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
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
        MnKriteriaMasukHCU = new javax.swing.JMenuItem();
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
        Kardiologi5 = new widget.ComboBox();
        jLabel58 = new widget.Label();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        Kardiologi6 = new widget.ComboBox();
        jLabel61 = new widget.Label();
        Kardiologi2 = new widget.ComboBox();
        jLabel62 = new widget.Label();
        Kardiologi3 = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        Kardiologi1 = new widget.ComboBox();
        jLabel66 = new widget.Label();
        Kardiologi4 = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        Pernapasan1 = new widget.ComboBox();
        Pernapasan2 = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        Pernapasan3 = new widget.ComboBox();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel55 = new widget.Label();
        jLabel73 = new widget.Label();
        Syaraf1 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        jLabel74 = new widget.Label();
        Syaraf2 = new widget.ComboBox();
        Syaraf3 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        Syaraf4 = new widget.ComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel56 = new widget.Label();
        Pencernaan1 = new widget.ComboBox();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        Pencernaan2 = new widget.ComboBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Pencernaan3 = new widget.ComboBox();
        jLabel83 = new widget.Label();
        Pencernaan4 = new widget.ComboBox();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel57 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        Pembedahan1 = new widget.ComboBox();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        Pembedahan2 = new widget.ComboBox();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        Hematologi1 = new widget.ComboBox();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        Hematologi2 = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel92 = new widget.Label();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        Infeksi = new widget.ComboBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKriteriaMasukHCU.setBackground(new java.awt.Color(255, 255, 254));
        MnKriteriaMasukHCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKriteriaMasukHCU.setForeground(new java.awt.Color(50, 50, 50));
        MnKriteriaMasukHCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKriteriaMasukHCU.setText("Formulir Check List Kriteria Masuk HCU");
        MnKriteriaMasukHCU.setName("MnKriteriaMasukHCU"); // NOI18N
        MnKriteriaMasukHCU.setPreferredSize(new java.awt.Dimension(260, 26));
        MnKriteriaMasukHCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKriteriaMasukHCUActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKriteriaMasukHCU);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Kriteria Masuk HCU ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2023" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 603));
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

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2023 21:36:33" }));
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

        jLabel23.setText("DPJP / Dokter Jaga / IGD :");
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

        Kardiologi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiologi5.setSelectedIndex(1);
        Kardiologi5.setName("Kardiologi5"); // NOI18N
        Kardiologi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiologi5KeyPressed(evt);
            }
        });
        FormInput.add(Kardiologi5);
        Kardiologi5.setBounds(247, 150, 90, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Menggunakan Inotropik / Vasoaktif Gent");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(40, 150, 210, 23);

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
        jLabel53.setText("I. SISTEM KARDIOLOGI");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 150, 243, 23);

        jLabel60.setText("MAP < 60 mmHg :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(391, 150, 110, 23);

        Kardiologi6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiologi6.setSelectedIndex(1);
        Kardiologi6.setName("Kardiologi6"); // NOI18N
        Kardiologi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiologi6KeyPressed(evt);
            }
        });
        FormInput.add(Kardiologi6);
        Kardiologi6.setBounds(505, 150, 90, 23);

        jLabel61.setText("Hypertensi Emergency :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(545, 90, 150, 23);

        Kardiologi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiologi2.setSelectedIndex(1);
        Kardiologi2.setName("Kardiologi2"); // NOI18N
        Kardiologi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiologi2KeyPressed(evt);
            }
        });
        FormInput.add(Kardiologi2);
        Kardiologi2.setBounds(699, 90, 90, 23);

        jLabel62.setText(":");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 120, 382, 23);

        Kardiologi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiologi3.setSelectedIndex(1);
        Kardiologi3.setName("Kardiologi3"); // NOI18N
        Kardiologi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiologi3KeyPressed(evt);
            }
        });
        FormInput.add(Kardiologi3);
        Kardiologi3.setBounds(386, 120, 90, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("HR 60x/menit (Tiidak Stabil Hasil EKG Gambaran Mengancam Nyawa)");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(40, 120, 380, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 90, 376, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Gangguan Sirkulasi Atau Pre Dan Pasca Operasi (Syok Hypovolemic)");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(40, 90, 341, 23);

        Kardiologi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiologi1.setSelectedIndex(1);
        Kardiologi1.setName("Kardiologi1"); // NOI18N
        Kardiologi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiologi1KeyPressed(evt);
            }
        });
        FormInput.add(Kardiologi1);
        Kardiologi1.setBounds(380, 90, 90, 23);

        jLabel66.setText("Gagal Jantung Acute :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(545, 120, 150, 23);

        Kardiologi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiologi4.setSelectedIndex(1);
        Kardiologi4.setName("Kardiologi4"); // NOI18N
        Kardiologi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiologi4KeyPressed(evt);
            }
        });
        FormInput.add(Kardiologi4);
        Kardiologi4.setBounds(699, 120, 90, 23);

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
        jLabel54.setText("II. SISTEM PERNAFASAN");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 180, 180, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("R <8 x/ menit > 25 x/menit (Adanya Gangguan Pada Ventilasi : Hypoxia / Hypercapnia / Sumbatan Jalan Nafas / Oedema Paru Acute)");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(40, 200, 660, 23);

        jLabel68.setText(":");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 200, 695, 23);

        Pernapasan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pernapasan1.setSelectedIndex(1);
        Pernapasan1.setName("Pernapasan1"); // NOI18N
        Pernapasan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Pernapasan1KeyPressed(evt);
            }
        });
        FormInput.add(Pernapasan1);
        Pernapasan1.setBounds(699, 200, 90, 23);

        Pernapasan2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pernapasan2.setSelectedIndex(1);
        Pernapasan2.setName("Pernapasan2"); // NOI18N
        Pernapasan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Pernapasan2KeyPressed(evt);
            }
        });
        FormInput.add(Pernapasan2);
        Pernapasan2.setBounds(200, 230, 90, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Trauma Thorax / Peumothorax");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(40, 230, 170, 23);

        jLabel70.setText(":");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 230, 196, 23);

        jLabel71.setText("Oxigenisasi Tidak Cukup Dari Hasil AGD :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(335, 230, 260, 23);

        Pernapasan3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pernapasan3.setSelectedIndex(1);
        Pernapasan3.setName("Pernapasan3"); // NOI18N
        Pernapasan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Pernapasan3KeyPressed(evt);
            }
        });
        FormInput.add(Pernapasan3);
        Pernapasan3.setBounds(599, 230, 90, 23);

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
        jLabel55.setText("III. SISTEM SYARAF");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 260, 180, 23);

        jLabel73.setText(":");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(0, 280, 189, 23);

        Syaraf1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Syaraf1.setSelectedIndex(1);
        Syaraf1.setName("Syaraf1"); // NOI18N
        Syaraf1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Syaraf1KeyPressed(evt);
            }
        });
        FormInput.add(Syaraf1);
        Syaraf1.setBounds(193, 280, 90, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Kesadaran Dengan GCS >= 7");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(40, 280, 170, 23);

        jLabel74.setText("Temperatur <35 C / >38 C :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(283, 280, 150, 23);

        Syaraf2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Syaraf2.setSelectedIndex(1);
        Syaraf2.setName("Syaraf2"); // NOI18N
        Syaraf2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Syaraf2KeyPressed(evt);
            }
        });
        FormInput.add(Syaraf2);
        Syaraf2.setBounds(437, 280, 90, 23);

        Syaraf3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Syaraf3.setSelectedIndex(1);
        Syaraf3.setName("Syaraf3"); // NOI18N
        Syaraf3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Syaraf3KeyPressed(evt);
            }
        });
        FormInput.add(Syaraf3);
        Syaraf3.setBounds(699, 280, 90, 23);

        jLabel75.setText("Trauma Kepala Sedang - Berat :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(525, 280, 170, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Kejang Yang Tidak Memerlukan Ventilator / Cerebro Vasculer / Neoromusculer / Infeksi Syaraf");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(40, 310, 470, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 310, 504, 23);

        Syaraf4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Syaraf4.setSelectedIndex(1);
        Syaraf4.setName("Syaraf4"); // NOI18N
        Syaraf4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Syaraf4KeyPressed(evt);
            }
        });
        FormInput.add(Syaraf4);
        Syaraf4.setBounds(508, 310, 90, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 340, 810, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 340, 810, 1);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. SISTEM PENCERNAAN DAN ENDOKRIN");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 340, 240, 23);

        Pencernaan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pencernaan1.setSelectedIndex(1);
        Pencernaan1.setName("Pencernaan1"); // NOI18N
        Pencernaan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Pencernaan1KeyPressed(evt);
            }
        });
        FormInput.add(Pencernaan1);
        Pencernaan1.setBounds(313, 360, 90, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Gangguan Elektrolit (Na, Ca,CI, Mg, Cal) & Asam Basa");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(40, 360, 290, 23);

        jLabel79.setText(":");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(0, 360, 309, 23);

        jLabel80.setText("Hypeglikemia & Hypoglikemia, Ketoasidosis Metabolic :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(405, 360, 290, 23);

        Pencernaan2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pencernaan2.setSelectedIndex(1);
        Pencernaan2.setName("Pencernaan2"); // NOI18N
        Pencernaan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Pencernaan2KeyPressed(evt);
            }
        });
        FormInput.add(Pencernaan2);
        Pencernaan2.setBounds(699, 360, 90, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Pendarahan Saluran Pencernaan Tanpa Hypotensi & Repon Dengan Cairan");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(40, 390, 370, 23);

        jLabel82.setText(":");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(0, 390, 409, 23);

        Pencernaan3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pencernaan3.setSelectedIndex(1);
        Pencernaan3.setName("Pencernaan3"); // NOI18N
        Pencernaan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Pencernaan3KeyPressed(evt);
            }
        });
        FormInput.add(Pencernaan3);
        Pencernaan3.setBounds(413, 390, 90, 23);

        jLabel83.setText("Pengobatan Keracunan :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(565, 390, 130, 23);

        Pencernaan4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pencernaan4.setSelectedIndex(1);
        Pencernaan4.setName("Pencernaan4"); // NOI18N
        Pencernaan4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Pencernaan4KeyPressed(evt);
            }
        });
        FormInput.add(Pencernaan4);
        Pencernaan4.setBounds(699, 390, 90, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 420, 810, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 420, 810, 1);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("V. PEMBEDAHAN");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(10, 420, 240, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Penyulit Pasca Pembedahan : Digestif / Orthopedi / Urologi / Vasculer / Plastik / Kebidanan (Eklamsia Pre Operasi & Pasca Bedah) Dll");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(40, 440, 660, 23);

        jLabel85.setText(":");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 440, 695, 23);

        Pembedahan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pembedahan1.setSelectedIndex(1);
        Pembedahan1.setName("Pembedahan1"); // NOI18N
        Pembedahan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Pembedahan1KeyPressed(evt);
            }
        });
        FormInput.add(Pembedahan1);
        Pembedahan1.setBounds(699, 440, 90, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Pasca Pembedahan Hemodinamik Stabil Tetapi Masih Perlu Resusitasi Cairan");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(40, 470, 390, 23);

        jLabel87.setText(":");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(0, 470, 422, 23);

        Pembedahan2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Pembedahan2.setSelectedIndex(1);
        Pembedahan2.setName("Pembedahan2"); // NOI18N
        Pembedahan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Pembedahan2KeyPressed(evt);
            }
        });
        FormInput.add(Pembedahan2);
        Pembedahan2.setBounds(426, 470, 90, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 500, 810, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 500, 810, 1);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("VI. GANGGUAN HEMATOLOGI");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(10, 500, 240, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Gangguan Imunologi (Reaksi Alergi, Steven Jhonson) dll");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(40, 520, 290, 23);

        Hematologi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Hematologi1.setSelectedIndex(1);
        Hematologi1.setName("Hematologi1"); // NOI18N
        Hematologi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hematologi1KeyPressed(evt);
            }
        });
        FormInput.add(Hematologi1);
        Hematologi1.setBounds(324, 520, 90, 23);

        jLabel90.setText(":");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(0, 520, 320, 23);

        jLabel91.setText("DIC, Anemia Berat, Reaksi Penolakan Transfusi Darah :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(415, 520, 280, 23);

        Hematologi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Hematologi2.setSelectedIndex(1);
        Hematologi2.setName("Hematologi2"); // NOI18N
        Hematologi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hematologi2KeyPressed(evt);
            }
        });
        FormInput.add(Hematologi2);
        Hematologi2.setBounds(699, 520, 90, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 550, 810, 1);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 550, 810, 1);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("VII. PENYAKIT INFEKSI");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(10, 550, 240, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("Semua Infeksi Yang Menyebabkan Penurunan Kesadaran & Tidak Memerlukan Ventilator : DBD Thrombositopenia, Sepsis, Tetanus, Dll");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(40, 570, 660, 23);

        jLabel94.setText(":");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(0, 570, 695, 23);

        Infeksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Infeksi.setSelectedIndex(1);
        Infeksi.setName("Infeksi"); // NOI18N
        Infeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InfeksiKeyPressed(evt);
            }
        });
        FormInput.add(Infeksi);
        Infeksi.setBounds(699, 570, 90, 23);

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
            Valid.pindah(evt,Infeksi,BtnBatal);
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
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString())){
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
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString())){
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
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Sirkulasi Atau Pre Dan Pasca Operasi (Syok Hypovolemic)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hypertensi Emergency</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>HR 60x/menit (Tiidak Stabil Hasil EKG Gambaran Mengancam Nyawa)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Jantung Acute</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Menggunakan Inotropik / Vasoaktif Gent</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>MAP < 60 mmHg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R <8 x/ menit > 25 x/menit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Trauma Thorax / Peumothorax</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Oxigenisasi Tidak Cukup Dari Hasil AGD</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesadaran Dengan GCS >= 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Temperatur <35 C / >38 C</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Trauma Kepala Sedang - Berat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kejang Yang Tidak Memerlukan Ventilator / Cerebro Vasculer / Neoromusculer / Infeksi Syaraf</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Elektrolit (Na, Ca,CI, Mg, Cal) & Asam Basa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hypeglikemia & Hypoglikemia, Ketoasidosis Metabolic</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pendarahan Saluran Pencernaan Tanpa Hypotensi & Repon Dengan Cairan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pengobatan Keracunan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyulit Pasca Pembedahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Pembedahan Hemodinamik Stabil Tetapi Masih Perlu Resusitasi Cairan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Imunologi (Reaksi Alergi, Steven Jhonson) dll</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DIC, Anemia Berat, Reaksi Penolakan Transfusi Darah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Semua Infeksi Yang Menyebabkan Penurunan Kesadaran & Tidak Memerlukan Ventilator</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP/Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DPJP/Dokter Jaga/IGD</b></td>"+
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
                      "<table width='3500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataChecklistKriteriaMasukHCU.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECK LIST KRITERIA MASUK HCU<br><br></font>"+        
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

    private void MnKriteriaMasukHCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKriteriaMasukHCUActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),29).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),28).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirChecklistKriteriaMasukHCU.jasper","report","::[ Formulir Check List Kriteria Masuk HCU ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_hcu.tanggal,"+
                    "checklist_kriteria_masuk_hcu.kardiologi1,checklist_kriteria_masuk_hcu.kardiologi2,checklist_kriteria_masuk_hcu.kardiologi3,"+
                    "checklist_kriteria_masuk_hcu.kardiologi4,checklist_kriteria_masuk_hcu.kardiologi5,checklist_kriteria_masuk_hcu.kardiologi6,"+
                    "checklist_kriteria_masuk_hcu.pernapasan1,checklist_kriteria_masuk_hcu.pernapasan2,checklist_kriteria_masuk_hcu.pernapasan3,"+
                    "checklist_kriteria_masuk_hcu.syaraf1,checklist_kriteria_masuk_hcu.syaraf2,checklist_kriteria_masuk_hcu.syaraf3,checklist_kriteria_masuk_hcu.syaraf4,"+
                    "checklist_kriteria_masuk_hcu.pencernaan1,checklist_kriteria_masuk_hcu.pencernaan2,checklist_kriteria_masuk_hcu.pencernaan3,"+
                    "checklist_kriteria_masuk_hcu.pencernaan4,checklist_kriteria_masuk_hcu.pembedahan1,checklist_kriteria_masuk_hcu.pembedahan2,"+
                    "checklist_kriteria_masuk_hcu.hematologi1,checklist_kriteria_masuk_hcu.hematologi2,checklist_kriteria_masuk_hcu.infeksi,"+
                    "checklist_kriteria_masuk_hcu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_hcu inner join reg_periksa on checklist_kriteria_masuk_hcu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_hcu.nik "+
                    "where checklist_kriteria_masuk_hcu.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_kriteria_masuk_hcu.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnKriteriaMasukHCUActionPerformed

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
       Valid.pindah(evt,Tanggal,Kardiologi1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void Kardiologi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiologi5KeyPressed
       Valid.pindah(evt,Kardiologi4,Kardiologi6);
    }//GEN-LAST:event_Kardiologi5KeyPressed

    private void Kardiologi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiologi6KeyPressed
        Valid.pindah(evt,Kardiologi5,Pernapasan1);
    }//GEN-LAST:event_Kardiologi6KeyPressed

    private void Kardiologi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiologi2KeyPressed
        Valid.pindah(evt,Kardiologi1,Kardiologi3);
    }//GEN-LAST:event_Kardiologi2KeyPressed

    private void Kardiologi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiologi3KeyPressed
        Valid.pindah(evt,Kardiologi2,Kardiologi4);
    }//GEN-LAST:event_Kardiologi3KeyPressed

    private void Kardiologi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiologi1KeyPressed
        Valid.pindah(evt,btnPetugas,Kardiologi2);
    }//GEN-LAST:event_Kardiologi1KeyPressed

    private void Kardiologi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiologi4KeyPressed
        Valid.pindah(evt,Kardiologi3,Kardiologi5);
    }//GEN-LAST:event_Kardiologi4KeyPressed

    private void Pernapasan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Pernapasan1KeyPressed
        Valid.pindah(evt,Kardiologi6,Pernapasan2);
    }//GEN-LAST:event_Pernapasan1KeyPressed

    private void Pernapasan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Pernapasan2KeyPressed
        Valid.pindah(evt,Pernapasan1,Pernapasan3);
    }//GEN-LAST:event_Pernapasan2KeyPressed

    private void Pernapasan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Pernapasan3KeyPressed
        Valid.pindah(evt,Pernapasan2,Syaraf1);
    }//GEN-LAST:event_Pernapasan3KeyPressed

    private void Syaraf1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Syaraf1KeyPressed
        Valid.pindah(evt,Pernapasan3,Syaraf2);
    }//GEN-LAST:event_Syaraf1KeyPressed

    private void Syaraf2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Syaraf2KeyPressed
        Valid.pindah(evt,Syaraf1,Syaraf3);
    }//GEN-LAST:event_Syaraf2KeyPressed

    private void Syaraf3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Syaraf3KeyPressed
        Valid.pindah(evt,Syaraf2,Syaraf4);
    }//GEN-LAST:event_Syaraf3KeyPressed

    private void Syaraf4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Syaraf4KeyPressed
        Valid.pindah(evt,Syaraf3,Pencernaan1);
    }//GEN-LAST:event_Syaraf4KeyPressed

    private void Pencernaan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Pencernaan1KeyPressed
        Valid.pindah(evt,Syaraf4,Pencernaan2);
    }//GEN-LAST:event_Pencernaan1KeyPressed

    private void Pencernaan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Pencernaan2KeyPressed
        Valid.pindah(evt,Pencernaan1,Pencernaan3);
    }//GEN-LAST:event_Pencernaan2KeyPressed

    private void Pencernaan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Pencernaan3KeyPressed
        Valid.pindah(evt,Pencernaan2,Pencernaan4);
    }//GEN-LAST:event_Pencernaan3KeyPressed

    private void Pencernaan4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Pencernaan4KeyPressed
        Valid.pindah(evt,Pencernaan3,Pembedahan1);
    }//GEN-LAST:event_Pencernaan4KeyPressed

    private void Pembedahan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Pembedahan1KeyPressed
        Valid.pindah(evt,Pencernaan4,Pembedahan2);
    }//GEN-LAST:event_Pembedahan1KeyPressed

    private void Pembedahan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Pembedahan2KeyPressed
        Valid.pindah(evt,Pembedahan1,Hematologi1);
    }//GEN-LAST:event_Pembedahan2KeyPressed

    private void Hematologi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hematologi1KeyPressed
        Valid.pindah(evt,Pembedahan2,Hematologi2);
    }//GEN-LAST:event_Hematologi1KeyPressed

    private void Hematologi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hematologi2KeyPressed
        Valid.pindah(evt,Hematologi1,Infeksi);
    }//GEN-LAST:event_Hematologi2KeyPressed

    private void InfeksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InfeksiKeyPressed
        Valid.pindah(evt,Hematologi2,BtnSimpan);
    }//GEN-LAST:event_InfeksiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistKriteriaMasukHCU dialog = new RMChecklistKriteriaMasukHCU(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Hematologi1;
    private widget.ComboBox Hematologi2;
    private widget.ComboBox Infeksi;
    private widget.TextBox JK;
    private widget.ComboBox Kardiologi1;
    private widget.ComboBox Kardiologi2;
    private widget.ComboBox Kardiologi3;
    private widget.ComboBox Kardiologi4;
    private widget.ComboBox Kardiologi5;
    private widget.ComboBox Kardiologi6;
    private widget.TextBox KodePetugas;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnKriteriaMasukHCU;
    private widget.TextBox NamaPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Pembedahan1;
    private widget.ComboBox Pembedahan2;
    private widget.ComboBox Pencernaan1;
    private widget.ComboBox Pencernaan2;
    private widget.ComboBox Pencernaan3;
    private widget.ComboBox Pencernaan4;
    private widget.ComboBox Pernapasan1;
    private widget.ComboBox Pernapasan2;
    private widget.ComboBox Pernapasan3;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Syaraf1;
    private widget.ComboBox Syaraf2;
    private widget.ComboBox Syaraf3;
    private widget.ComboBox Syaraf4;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_hcu.tanggal,"+
                    "checklist_kriteria_masuk_hcu.kardiologi1,checklist_kriteria_masuk_hcu.kardiologi2,checklist_kriteria_masuk_hcu.kardiologi3,"+
                    "checklist_kriteria_masuk_hcu.kardiologi4,checklist_kriteria_masuk_hcu.kardiologi5,checklist_kriteria_masuk_hcu.kardiologi6,"+
                    "checklist_kriteria_masuk_hcu.pernapasan1,checklist_kriteria_masuk_hcu.pernapasan2,checklist_kriteria_masuk_hcu.pernapasan3,"+
                    "checklist_kriteria_masuk_hcu.syaraf1,checklist_kriteria_masuk_hcu.syaraf2,checklist_kriteria_masuk_hcu.syaraf3,checklist_kriteria_masuk_hcu.syaraf4,"+
                    "checklist_kriteria_masuk_hcu.pencernaan1,checklist_kriteria_masuk_hcu.pencernaan2,checklist_kriteria_masuk_hcu.pencernaan3,"+
                    "checklist_kriteria_masuk_hcu.pencernaan4,checklist_kriteria_masuk_hcu.pembedahan1,checklist_kriteria_masuk_hcu.pembedahan2,"+
                    "checklist_kriteria_masuk_hcu.hematologi1,checklist_kriteria_masuk_hcu.hematologi2,checklist_kriteria_masuk_hcu.infeksi,"+
                    "checklist_kriteria_masuk_hcu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_hcu inner join reg_periksa on checklist_kriteria_masuk_hcu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_hcu.nik "+
                    "where checklist_kriteria_masuk_hcu.tanggal between ? and ? order by checklist_kriteria_masuk_hcu.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_hcu.tanggal,"+
                    "checklist_kriteria_masuk_hcu.kardiologi1,checklist_kriteria_masuk_hcu.kardiologi2,checklist_kriteria_masuk_hcu.kardiologi3,"+
                    "checklist_kriteria_masuk_hcu.kardiologi4,checklist_kriteria_masuk_hcu.kardiologi5,checklist_kriteria_masuk_hcu.kardiologi6,"+
                    "checklist_kriteria_masuk_hcu.pernapasan1,checklist_kriteria_masuk_hcu.pernapasan2,checklist_kriteria_masuk_hcu.pernapasan3,"+
                    "checklist_kriteria_masuk_hcu.syaraf1,checklist_kriteria_masuk_hcu.syaraf2,checklist_kriteria_masuk_hcu.syaraf3,checklist_kriteria_masuk_hcu.syaraf4,"+
                    "checklist_kriteria_masuk_hcu.pencernaan1,checklist_kriteria_masuk_hcu.pencernaan2,checklist_kriteria_masuk_hcu.pencernaan3,"+
                    "checklist_kriteria_masuk_hcu.pencernaan4,checklist_kriteria_masuk_hcu.pembedahan1,checklist_kriteria_masuk_hcu.pembedahan2,"+
                    "checklist_kriteria_masuk_hcu.hematologi1,checklist_kriteria_masuk_hcu.hematologi2,checklist_kriteria_masuk_hcu.infeksi,"+
                    "checklist_kriteria_masuk_hcu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_hcu inner join reg_periksa on checklist_kriteria_masuk_hcu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_hcu.nik "+
                    "where checklist_kriteria_masuk_hcu.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or pegawai.nama like ? or checklist_kriteria_masuk_hcu.nik like ?) order by checklist_kriteria_masuk_hcu.tanggal ");
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
                        rs.getString("tanggal"),rs.getString("kardiologi1"),rs.getString("kardiologi2"),rs.getString("kardiologi3"),rs.getString("kardiologi4"),
                        rs.getString("kardiologi5"),rs.getString("kardiologi6"),rs.getString("pernapasan1"),rs.getString("pernapasan2"),rs.getString("pernapasan3"),
                        rs.getString("syaraf1"),rs.getString("syaraf2"),rs.getString("syaraf3"),rs.getString("syaraf4"),rs.getString("pencernaan1"),
                        rs.getString("pencernaan2"),rs.getString("pencernaan3"),rs.getString("pencernaan4"),rs.getString("pembedahan1"),rs.getString("pembedahan2"),
                        rs.getString("hematologi1"),rs.getString("hematologi2"),rs.getString("infeksi"),rs.getString("nik"),rs.getString("nama")
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
        Kardiologi1.setSelectedItem("Tidak");
        Kardiologi2.setSelectedItem("Tidak");
        Kardiologi3.setSelectedItem("Tidak");
        Kardiologi4.setSelectedItem("Tidak");
        Kardiologi5.setSelectedItem("Tidak");
        Kardiologi6.setSelectedItem("Tidak");
        Pernapasan1.setSelectedItem("Tidak");
        Pernapasan2.setSelectedItem("Tidak");
        Pernapasan3.setSelectedItem("Tidak");
        Syaraf1.setSelectedItem("Tidak");
        Syaraf2.setSelectedItem("Tidak");
        Syaraf3.setSelectedItem("Tidak");
        Syaraf4.setSelectedItem("Tidak");
        Pencernaan1.setSelectedItem("Tidak");
        Pencernaan2.setSelectedItem("Tidak");
        Pencernaan3.setSelectedItem("Tidak");
        Pencernaan4.setSelectedItem("Tidak");
        Pembedahan1.setSelectedItem("Tidak");
        Pembedahan2.setSelectedItem("Tidak");
        Hematologi1.setSelectedItem("Tidak");
        Hematologi2.setSelectedItem("Tidak");
        Infeksi.setSelectedItem("Tidak");
        Tanggal.setDate(new Date());
        Kardiologi1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Kardiologi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Kardiologi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Kardiologi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Kardiologi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Kardiologi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Kardiologi6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Pernapasan1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Pernapasan2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Pernapasan3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Syaraf1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Syaraf2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Syaraf3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Syaraf4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Pencernaan1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Pencernaan2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Pencernaan3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Pencernaan4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Pembedahan1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Pembedahan2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Hematologi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Hematologi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Infeksi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-182));
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
        BtnSimpan.setEnabled(akses.getchecklist_kriteria_masuk_hcu());
        BtnHapus.setEnabled(akses.getchecklist_kriteria_masuk_hcu());
        BtnEdit.setEnabled(akses.getchecklist_kriteria_masuk_hcu());
        BtnPrint.setEnabled(akses.getchecklist_kriteria_masuk_hcu()); 
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
        if(Sequel.mengedittf("checklist_kriteria_masuk_hcu","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,kardiologi1=?,kardiologi2=?,kardiologi3=?,kardiologi4=?,kardiologi5=?,kardiologi6=?,pernapasan1=?,pernapasan2=?,"+
                "pernapasan3=?,syaraf1=?,syaraf2=?,syaraf3=?,syaraf4=?,pencernaan1=?,pencernaan2=?,pencernaan3=?,pencernaan4=?,pembedahan1=?,pembedahan2=?,hematologi1=?,hematologi2=?,infeksi=?,nik=?",27,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Kardiologi1.getSelectedItem().toString(),
                Kardiologi2.getSelectedItem().toString(),Kardiologi3.getSelectedItem().toString(),Kardiologi4.getSelectedItem().toString(),Kardiologi5.getSelectedItem().toString(),
                Kardiologi6.getSelectedItem().toString(),Pernapasan1.getSelectedItem().toString(),Pernapasan2.getSelectedItem().toString(),Pernapasan3.getSelectedItem().toString(),
                Syaraf1.getSelectedItem().toString(),Syaraf2.getSelectedItem().toString(),Syaraf3.getSelectedItem().toString(),Syaraf4.getSelectedItem().toString(),
                Pencernaan1.getSelectedItem().toString(),Pencernaan2.getSelectedItem().toString(),Pencernaan3.getSelectedItem().toString(),Pencernaan4.getSelectedItem().toString(),
                Pembedahan1.getSelectedItem().toString(),Pembedahan2.getSelectedItem().toString(),Hematologi1.getSelectedItem().toString(),Hematologi2.getSelectedItem().toString(),
                Infeksi.getSelectedItem().toString(),KodePetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(Kardiologi1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Kardiologi2.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(Kardiologi3.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Kardiologi4.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Kardiologi5.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Kardiologi6.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Pernapasan1.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Pernapasan2.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(Pernapasan3.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(Syaraf1.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(Syaraf2.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(Syaraf3.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(Syaraf4.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(Pencernaan1.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(Pencernaan2.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(Pencernaan3.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(Pencernaan4.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(Pembedahan1.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(Pembedahan2.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(Hematologi1.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(Hematologi2.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(Infeksi.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),29);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_kriteria_masuk_hcu where no_rawat=? and tanggal=?",2,new String[]{
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
        if(Sequel.menyimpantf("checklist_kriteria_masuk_hcu","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",25,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Kardiologi1.getSelectedItem().toString(),
            Kardiologi2.getSelectedItem().toString(),Kardiologi3.getSelectedItem().toString(),Kardiologi4.getSelectedItem().toString(),Kardiologi5.getSelectedItem().toString(),
            Kardiologi6.getSelectedItem().toString(),Pernapasan1.getSelectedItem().toString(),Pernapasan2.getSelectedItem().toString(),Pernapasan3.getSelectedItem().toString(),
            Syaraf1.getSelectedItem().toString(),Syaraf2.getSelectedItem().toString(),Syaraf3.getSelectedItem().toString(),Syaraf4.getSelectedItem().toString(),
            Pencernaan1.getSelectedItem().toString(),Pencernaan2.getSelectedItem().toString(),Pencernaan3.getSelectedItem().toString(),Pencernaan4.getSelectedItem().toString(),
            Pembedahan1.getSelectedItem().toString(),Pembedahan2.getSelectedItem().toString(),Hematologi1.getSelectedItem().toString(),Hematologi2.getSelectedItem().toString(),
            Infeksi.getSelectedItem().toString(),KodePetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                Kardiologi1.getSelectedItem().toString(),Kardiologi2.getSelectedItem().toString(),Kardiologi3.getSelectedItem().toString(),Kardiologi4.getSelectedItem().toString(),
                Kardiologi5.getSelectedItem().toString(),Kardiologi6.getSelectedItem().toString(),Pernapasan1.getSelectedItem().toString(),Pernapasan2.getSelectedItem().toString(),
                Pernapasan3.getSelectedItem().toString(),Syaraf1.getSelectedItem().toString(),Syaraf2.getSelectedItem().toString(),Syaraf3.getSelectedItem().toString(),Syaraf4.getSelectedItem().toString(),
                Pencernaan1.getSelectedItem().toString(),Pencernaan2.getSelectedItem().toString(),Pencernaan3.getSelectedItem().toString(),Pencernaan4.getSelectedItem().toString(),
                Pembedahan1.getSelectedItem().toString(),Pembedahan2.getSelectedItem().toString(),Hematologi1.getSelectedItem().toString(),Hematologi2.getSelectedItem().toString(),
                Infeksi.getSelectedItem().toString(),KodePetugas.getText(),NamaPetugas.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
}

package permintaan;

import fungsi.WarnaTable4;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import rekammedis.RMLayananKedokteranFisikRehabilitasi;
import rekammedis.RMLayananProgramKFR;
import rekammedis.RMPenilaianFisioterapi;
import rekammedis.RMRiwayatPerawatan;
import simrskhanza.DlgRawatJalan;

/**
 *
 * @author dosen
 */
public class DlgCariPermintaanLayananProgramKFR extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String finger="";
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgCariPermintaanLayananProgramKFR(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Cara Bayar","Tgl.Permintaan",
                "Diagnosa Medis","Permintaan & Evaluasi/Tata Laksana KFR","Ke","Kode Bayar","Kode Poli"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(83);
            }else if(i==1){
                column.setPreferredWidth(83);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(63);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(81);
            }else if(i==8){
                column.setPreferredWidth(220);
            }else if(i==9){
                column.setPreferredWidth(220);
            }else if(i==10){
                column.setPreferredWidth(25);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable4());

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
        
        WindowInput.setSize(735,245);
        WindowInput.setLocationRelativeTo(null);  
              
        ChkAccor.setSelected(false);
        isMenu();
        
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        WindowInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        BtnCloseIn = new widget.Button();
        jLabel19 = new widget.Label();
        BtnSimpanJawaban = new widget.Button();
        BtnBatalJawaban = new widget.Button();
        NoPermintaanJawaban = new widget.TextBox();
        label1 = new widget.Label();
        TanggalJawab = new widget.Tanggal();
        jLabel42 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        JawabanKonsultasi = new widget.TextArea();
        label2 = new widget.Label();
        JawabanDiagnosaKerja = new widget.TextBox();
        label3 = new widget.Label();
        LoadHTML = new widget.editorpane();
        Popup = new javax.swing.JPopupMenu();
        Tinggi100 = new javax.swing.JMenuItem();
        Tinggi200 = new javax.swing.JMenuItem();
        Tinggi250 = new javax.swing.JMenuItem();
        Tinggi300 = new javax.swing.JMenuItem();
        Tinggi350 = new javax.swing.JMenuItem();
        Tinggi400 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelCari = new widget.panelisi();
        R1 = new widget.RadioButton();
        LCount1 = new widget.Label();
        R2 = new widget.RadioButton();
        LCount2 = new widget.Label();
        R3 = new widget.RadioButton();
        DTPCari1 = new widget.Tanggal();
        jLabel25 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        BtnRiwayatPasien = new widget.Button();
        BtnAwalFisioTerapi = new widget.Button();
        BtnSOAPTindakan = new widget.Button();
        BtnDetailPermintaan = new widget.Button();
        BtnRiwayatProgram = new widget.Button();

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Jawaban Permintaan Konsultasi Medik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('U');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+U");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(620, 195, 100, 30);

        jLabel19.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame2.add(jLabel19);
        jLabel19.setBounds(-10, 175, 850, 14);

        BtnSimpanJawaban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanJawaban.setMnemonic('S');
        BtnSimpanJawaban.setText("Simpan");
        BtnSimpanJawaban.setToolTipText("Alt+S");
        BtnSimpanJawaban.setName("BtnSimpanJawaban"); // NOI18N
        BtnSimpanJawaban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanJawabanActionPerformed(evt);
            }
        });
        BtnSimpanJawaban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanJawabanKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnSimpanJawaban);
        BtnSimpanJawaban.setBounds(14, 195, 100, 30);

        BtnBatalJawaban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatalJawaban.setMnemonic('B');
        BtnBatalJawaban.setText("Batal");
        BtnBatalJawaban.setToolTipText("Alt+B");
        BtnBatalJawaban.setName("BtnBatalJawaban"); // NOI18N
        BtnBatalJawaban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalJawabanActionPerformed(evt);
            }
        });
        BtnBatalJawaban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalJawabanKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnBatalJawaban);
        BtnBatalJawaban.setBounds(117, 195, 100, 30);

        NoPermintaanJawaban.setEditable(false);
        NoPermintaanJawaban.setName("NoPermintaanJawaban"); // NOI18N
        internalFrame2.add(NoPermintaanJawaban);
        NoPermintaanJawaban.setBounds(99, 20, 109, 23);

        label1.setText("Tanggal :");
        label1.setName("label1"); // NOI18N
        internalFrame2.add(label1);
        label1.setBounds(210, 20, 55, 23);

        TanggalJawab.setForeground(new java.awt.Color(50, 70, 50));
        TanggalJawab.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-03-2025 10:16:57" }));
        TanggalJawab.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalJawab.setName("TanggalJawab"); // NOI18N
        TanggalJawab.setOpaque(false);
        TanggalJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJawabKeyPressed(evt);
            }
        });
        internalFrame2.add(TanggalJawab);
        TanggalJawab.setBounds(269, 20, 130, 23);

        jLabel42.setText("Jawaban :");
        jLabel42.setName("jLabel42"); // NOI18N
        internalFrame2.add(jLabel42);
        jLabel42.setBounds(0, 50, 95, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        JawabanKonsultasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        JawabanKonsultasi.setColumns(20);
        JawabanKonsultasi.setRows(9);
        JawabanKonsultasi.setTabSize(20);
        JawabanKonsultasi.setName("JawabanKonsultasi"); // NOI18N
        JawabanKonsultasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JawabanKonsultasiKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(JawabanKonsultasi);

        internalFrame2.add(scrollPane2);
        scrollPane2.setBounds(99, 50, 621, 123);

        label2.setText("No.Permintaan :");
        label2.setName("label2"); // NOI18N
        internalFrame2.add(label2);
        label2.setBounds(0, 20, 95, 23);

        JawabanDiagnosaKerja.setName("JawabanDiagnosaKerja"); // NOI18N
        JawabanDiagnosaKerja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JawabanDiagnosaKerjaKeyPressed(evt);
            }
        });
        internalFrame2.add(JawabanDiagnosaKerja);
        JawabanDiagnosaKerja.setBounds(495, 20, 225, 23);

        label3.setText("Diagnosa Kerja :");
        label3.setName("label3"); // NOI18N
        internalFrame2.add(label3);
        label3.setBounds(401, 20, 90, 23);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Popup.setName("Popup"); // NOI18N

        Tinggi100.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi100.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi100.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi100.setText("Tinggi Baris 100");
        Tinggi100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi100.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi100.setName("Tinggi100"); // NOI18N
        Tinggi100.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi100ActionPerformed(evt);
            }
        });
        Popup.add(Tinggi100);

        Tinggi200.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi200.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi200.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi200.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi200.setText("Tinggi Baris 200");
        Tinggi200.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi200.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi200.setName("Tinggi200"); // NOI18N
        Tinggi200.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi200.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi200ActionPerformed(evt);
            }
        });
        Popup.add(Tinggi200);

        Tinggi250.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi250.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi250.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi250.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi250.setText("Tinggi Baris 250");
        Tinggi250.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi250.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi250.setName("Tinggi250"); // NOI18N
        Tinggi250.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi250.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi250ActionPerformed(evt);
            }
        });
        Popup.add(Tinggi250);

        Tinggi300.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi300.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi300.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi300.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi300.setText("Tinggi Baris 300");
        Tinggi300.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi300.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi300.setName("Tinggi300"); // NOI18N
        Tinggi300.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi300.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi300ActionPerformed(evt);
            }
        });
        Popup.add(Tinggi300);

        Tinggi350.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi350.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi350.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi350.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi350.setText("Tinggi Baris 350");
        Tinggi350.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi350.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi350.setName("Tinggi350"); // NOI18N
        Tinggi350.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi350.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi350ActionPerformed(evt);
            }
        });
        Popup.add(Tinggi350);

        Tinggi400.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi400.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi400.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi400.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi400.setText("Tinggi Baris 400");
        Tinggi400.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi400.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi400.setName("Tinggi400"); // NOI18N
        Tinggi400.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi400.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi400ActionPerformed(evt);
            }
        });
        Popup.add(Tinggi400);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Layanan Program Kedokteran Fisik & Rehabilitasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan pilih data dengan mengeklik pada table");
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.setRowHeight(100);
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
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Layani");
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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/130.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Selesai Program");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(145, 30));
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

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/129.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Perpanjang Program");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(175, 30));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(370, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
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
        panelGlass10.add(BtnCari);

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
        panelGlass10.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass10.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Menunggu");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelCari.add(R1);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(28, 23));
        panelCari.add(LCount1);

        buttonGroup1.add(R2);
        R2.setText("Sudah Terlayani");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(125, 23));
        panelCari.add(R2);

        LCount2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount2.setName("LCount2"); // NOI18N
        LCount2.setPreferredSize(new java.awt.Dimension(28, 23));
        panelCari.add(LCount2);

        buttonGroup1.add(R3);
        R3.setText("Tgl.Permintaan :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(117, 23));
        panelCari.add(R3);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-03-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari1ItemStateChanged(evt);
            }
        });
        panelCari.add(DTPCari1);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("s.d");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel25);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-03-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari2ItemStateChanged(evt);
            }
        });
        panelCari.add(DTPCari2);

        jPanel3.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(190, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout());

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 255, 248)));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.EAST);

        ScrollMenu.setBorder(null);
        ScrollMenu.setName("ScrollMenu"); // NOI18N
        ScrollMenu.setOpaque(true);

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnRiwayatPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayatPasien.setText("Riwayat Perawatan");
        BtnRiwayatPasien.setFocusPainted(false);
        BtnRiwayatPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayatPasien.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayatPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayatPasien.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayatPasien.setName("BtnRiwayatPasien"); // NOI18N
        BtnRiwayatPasien.setPreferredSize(new java.awt.Dimension(170, 23));
        BtnRiwayatPasien.setRoundRect(false);
        BtnRiwayatPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatPasienActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRiwayatPasien);

        BtnAwalFisioTerapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalFisioTerapi.setText("Penilaian Awal Fisioterapi");
        BtnAwalFisioTerapi.setFocusPainted(false);
        BtnAwalFisioTerapi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalFisioTerapi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalFisioTerapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalFisioTerapi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalFisioTerapi.setName("BtnAwalFisioTerapi"); // NOI18N
        BtnAwalFisioTerapi.setPreferredSize(new java.awt.Dimension(170, 23));
        BtnAwalFisioTerapi.setRoundRect(false);
        BtnAwalFisioTerapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalFisioTerapiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalFisioTerapi);

        BtnSOAPTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSOAPTindakan.setText("SOAP & Tindakan");
        BtnSOAPTindakan.setFocusPainted(false);
        BtnSOAPTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSOAPTindakan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSOAPTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSOAPTindakan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSOAPTindakan.setName("BtnSOAPTindakan"); // NOI18N
        BtnSOAPTindakan.setPreferredSize(new java.awt.Dimension(170, 23));
        BtnSOAPTindakan.setRoundRect(false);
        BtnSOAPTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSOAPTindakanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSOAPTindakan);

        BtnDetailPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnDetailPermintaan.setText("Detail Permintaan Layanan");
        BtnDetailPermintaan.setFocusPainted(false);
        BtnDetailPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDetailPermintaan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDetailPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDetailPermintaan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDetailPermintaan.setName("BtnDetailPermintaan"); // NOI18N
        BtnDetailPermintaan.setPreferredSize(new java.awt.Dimension(170, 23));
        BtnDetailPermintaan.setRoundRect(false);
        BtnDetailPermintaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDetailPermintaanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnDetailPermintaan);

        BtnRiwayatProgram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayatProgram.setText("Riwayat Program KFR");
        BtnRiwayatProgram.setFocusPainted(false);
        BtnRiwayatProgram.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayatProgram.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayatProgram.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayatProgram.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayatProgram.setName("BtnRiwayatProgram"); // NOI18N
        BtnRiwayatProgram.setPreferredSize(new java.awt.Dimension(170, 23));
        BtnRiwayatProgram.setRoundRect(false);
        BtnRiwayatProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatProgramActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRiwayatProgram);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.WEST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMLayananProgramKFR form=new RMLayananProgramKFR(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),new Date());
                form.Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
                form.NoPermintaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                form.PermintaanTerapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data...!!!!");
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
         
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(Sequel.queryu2tf("delete from konsultasi_medik where no_permintaan=?",1,new String[]{
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        } 
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInput.dispose();
        dokter.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
            dokter.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}*/
}//GEN-LAST:event_BtnKeluarKeyPressed

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
            //Valid.pindah(evt, BtnCari, NmPasien);
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void DTPCari2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari2ItemStateChanged
        R3.setSelected(true);
    }//GEN-LAST:event_DTPCari2ItemStateChanged

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari1ItemStateChanged
        R3.setSelected(true);
    }//GEN-LAST:event_DTPCari1ItemStateChanged

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnDetailPermintaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDetailPermintaanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMLayananKedokteranFisikRehabilitasi form=new RMLayananKedokteranFisikRehabilitasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),Valid.SetTgl2(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()));
                form.setTampil();
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnDetailPermintaanActionPerformed

    private void BtnRiwayatProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatProgramActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMLayananProgramKFR form=new RMLayananProgramKFR(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),new Date());
                form.tampil();
                form.Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
                form.NoPermintaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                form.PermintaanTerapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
                form.DTPCari1.setDate(Valid.SetTgl2(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()));
                form.TCari.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnRiwayatProgramActionPerformed

    private void BtnRiwayatPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatPasienActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbObat.getSelectedRow()== -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data kamar inap pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRiwayatPasienActionPerformed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        dokter.dispose();
        WindowInput.dispose();
    }//GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal, NoPermintaanJawaban);}
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpanJawabanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanJawabanActionPerformed
        
    }//GEN-LAST:event_BtnSimpanJawabanActionPerformed

    private void BtnSimpanJawabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanJawabanKeyPressed
        Valid.pindah(evt,JawabanKonsultasi,BtnBatal);
    }//GEN-LAST:event_BtnSimpanJawabanKeyPressed

    private void BtnBatalJawabanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalJawabanActionPerformed
        TanggalJawab.setDate(new Date());
        JawabanDiagnosaKerja.setText("");
        JawabanKonsultasi.setText("");
    }//GEN-LAST:event_BtnBatalJawabanActionPerformed

    private void BtnBatalJawabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalJawabanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
    }//GEN-LAST:event_BtnBatalJawabanKeyPressed

    private void TanggalJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalJawabKeyPressed
        Valid.pindah(evt,BtnSimpanJawaban,JawabanDiagnosaKerja);
    }//GEN-LAST:event_TanggalJawabKeyPressed

    private void JawabanKonsultasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JawabanKonsultasiKeyPressed
        
    }//GEN-LAST:event_JawabanKonsultasiKeyPressed

    private void JawabanDiagnosaKerjaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JawabanDiagnosaKerjaKeyPressed
        Valid.pindah(evt,TanggalJawab,JawabanKonsultasi);
    }//GEN-LAST:event_JawabanDiagnosaKerjaKeyPressed

    private void BtnSOAPTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSOAPTindakanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
                dlgrwjl.isCek();
                dlgrwjl.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgrwjl.setLocationRelativeTo(internalFrame1); 
                dlgrwjl.SetPj(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
                dlgrwjl.SetPoli(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
                dlgrwjl.emptTeks();
                dlgrwjl.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),DTPCari1.getDate(),DTPCari2.getDate());   
                dlgrwjl.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnSOAPTindakanActionPerformed

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
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kebiasaan Makan Manis</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Aktifitas Fisik</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Istirahat Cukup</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Merokok</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Alkohol/Merokok Keluarga</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penggunaan Steroid</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BB(Kg)</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TB(Cm)</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>IMT</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kasifikasi IMT</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>LP(Cm)</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko L.P.</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Obesitas</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                    "<table width='1700px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataSkriningObesitas.html");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                    "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                    "<table width='1700px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                    "<tr class='isi2'>"+
                    "<td valign='top' align='center'>"+
                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                    "<font size='2' face='Tahoma'>DATA SEKRINING OBESITAS<br><br></font>"+
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
            //Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAwalFisioTerapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalFisioTerapiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianFisioterapi form=new RMPenilaianFisioterapi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),Valid.SetTgl2(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()));
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnAwalFisioTerapiActionPerformed

    private void Tinggi100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi100ActionPerformed
        tbObat.setRowHeight(100);
    }//GEN-LAST:event_Tinggi100ActionPerformed

    private void Tinggi200ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi200ActionPerformed
        tbObat.setRowHeight(200);
    }//GEN-LAST:event_Tinggi200ActionPerformed

    private void Tinggi250ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi250ActionPerformed
        tbObat.setRowHeight(250);
    }//GEN-LAST:event_Tinggi250ActionPerformed

    private void Tinggi300ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi300ActionPerformed
        tbObat.setRowHeight(300);
    }//GEN-LAST:event_Tinggi300ActionPerformed

    private void Tinggi350ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi350ActionPerformed
        tbObat.setRowHeight(350);
    }//GEN-LAST:event_Tinggi350ActionPerformed

    private void Tinggi400ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi400ActionPerformed
        tbObat.setRowHeight(400);
    }//GEN-LAST:event_Tinggi400ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPermintaanLayananProgramKFR dialog = new DlgCariPermintaanLayananProgramKFR(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAwalFisioTerapi;
    private widget.Button BtnBatal;
    private widget.Button BtnBatalJawaban;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn;
    private widget.Button BtnDetailPermintaan;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRiwayatPasien;
    private widget.Button BtnRiwayatProgram;
    private widget.Button BtnSOAPTindakan;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanJawaban;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox JawabanDiagnosaKerja;
    private widget.TextArea JawabanKonsultasi;
    private widget.Label LCount;
    private widget.Label LCount1;
    private widget.Label LCount2;
    private widget.editorpane LoadHTML;
    private widget.TextBox NoPermintaanJawaban;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPopupMenu Popup;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox TCari;
    private widget.Tanggal TanggalJawab;
    private javax.swing.JMenuItem Tinggi100;
    private javax.swing.JMenuItem Tinggi200;
    private javax.swing.JMenuItem Tinggi250;
    private javax.swing.JMenuItem Tinggi300;
    private javax.swing.JMenuItem Tinggi350;
    private javax.swing.JMenuItem Tinggi400;
    private javax.swing.JDialog WindowInput;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel19;
    private widget.Label jLabel25;
    private widget.Label jLabel42;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.Label label1;
    private widget.Label label2;
    private widget.Label label3;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {     
        Valid.tabelKosong(tabMode);
        try{ 
            if(R1.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select layanan_kedokteran_fisik_rehabilitasi.no_rawat as nopermintaan,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penjab.png_jawab,layanan_kedokteran_fisik_rehabilitasi.tanggal,reg_periksasaatini.no_rawat,"+
                        "layanan_kedokteran_fisik_rehabilitasi.diagnosa_medis,layanan_kedokteran_fisik_rehabilitasi.tatalaksana,layanan_kedokteran_fisik_rehabilitasi.evaluasi,reg_periksa.kd_pj,reg_periksa.kd_poli from reg_periksa "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join layanan_kedokteran_fisik_rehabilitasi on reg_periksa.no_rawat=layanan_kedokteran_fisik_rehabilitasi.no_rawat "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join reg_periksa as reg_periksasaatini on reg_periksasaatini.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksasaatini.tgl_registrasi=current_date() "+
                        "and reg_periksasaatini.no_rawat not in (select layanan_program_kfr.no_rawat from layanan_program_kfr where DATE_FORMAT(layanan_program_kfr.tanggal,'%Y-%m-%d')=current_date()) "+
                        "and layanan_kedokteran_fisik_rehabilitasi.status_program='Belum Selesai'");
                rs=ps.executeQuery();
            }
            
            try {
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("nopermintaan"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),rs.getString("png_jawab"),rs.getString("tanggal"),rs.getString("diagnosa_medis"),
                        rs.getString("tatalaksana")+". "+rs.getString("evaluasi"),Sequel.cariIsi("select count(layanan_program_kfr.no_rawat_layanan)+1 from layanan_program_kfr where layanan_program_kfr.no_rawat_layanan=?",rs.getString("nopermintaan")),
                        rs.getString("kd_pj"),rs.getString("kd_poli")
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
        NoPermintaanJawaban.setText("");
        JawabanDiagnosaKerja.setText("");
        JawabanKonsultasi.setText("");
        TanggalJawab.setDate(new Date());
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            
        }
    }
    
   
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getkonsultasi_medik());
        BtnHapus.setEnabled(akses.getkonsultasi_medik());
        //BtnPrint.setEnabled(akses.getkonsultasi_medik());
        BtnDetailPermintaan.setEnabled(akses.getjawaban_konsultasi_medik());
        BtnRiwayatPasien.setEnabled(akses.getresume_pasien());
        //BtnEdit.setEnabled(akses.getkonsultasi_medik());   
        /*if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
                dispose();
            }
        }*/
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(190,HEIGHT));
            FormMenu.setVisible(true); 
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){  
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);    
            ChkAccor.setVisible(true);
        }
    }
    

    private void ganti() {
        /*if(Sequel.mengedittf("konsultasi_medik","no_permintaan=?","no_permintaan=?,no_rawat=?,tanggal=?,jenis_permintaan=?,kd_dokter=?,kd_dokter_dikonsuli=?,diagnosa_kerja=?,uraian_konsultasi=?",9,new String[]{
                NoPermintaan.getText(),NoRw.getText(),Valid.SetTgl(TanggalPermintaan.getSelectedItem()+"")+" "+TanggalPermintaan.getSelectedItem().toString().substring(11,19),
                Permintaan.getSelectedItem().toString(),KdDokter.getText(),KdDokterDikonsuli.getText(),DiagnosaKerja.getText(),UraianKonsultasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                tampil();
                emptTeks();
        }*/
    }
    
}

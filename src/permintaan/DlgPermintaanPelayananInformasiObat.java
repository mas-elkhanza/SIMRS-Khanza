package permintaan;

import fungsi.BackgroundMusic;
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
import static java.awt.image.ImageObserver.HEIGHT;
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
import rekammedis.RMRiwayatPerawatan;

/**
 *
 * @author dosen
 */
public class DlgPermintaanPelayananInformasiObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,nilai_detik,bookingbaru=0;
    private String alarm="",nol_detik,detik,sql="",finger="";
    private boolean aktif=false;
    private BackgroundMusic music;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgPermintaanPelayananInformasiObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","No.Rawat","No.RM","Nama Pasien","J.K.","Umur","No.Telp","Cara Bayar","Tanggal Pertanyaan","Metode",
                "Penanya","Status Penanya","No.Telp Penanya","Jenis Pertanyaan","Ket.Jenis Pertanyaan","Uraian Pertanyaan","Jawaban Pertanyaan",
                "Referensi Jawaban","Penyampaian","Metode Jawaban","Tanggal Jawaban","NIP","Nama Apoteker"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
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
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(115);
            }else if(i==9){
                column.setPreferredWidth(47);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(120);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else if(i==15){
                column.setPreferredWidth(220);
            }else if(i==16){
                column.setPreferredWidth(220);
            }else if(i==17){
                column.setPreferredWidth(220);
            }else if(i==18){
                column.setPreferredWidth(92);
            }else if(i==19){
                column.setPreferredWidth(92);
            }else if(i==20){
                column.setPreferredWidth(115);
            }else if(i==21){
                column.setPreferredWidth(90);
            }else if(i==22){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        NoRw.setDocument(new batasInput((byte)17).getKata(NoRw));
        NoPermintaan.setDocument(new batasInput((byte)20).getKata(NoPermintaan));
        Penanya.setDocument(new batasInput((int)70).getKata(Penanya));
        NoTelp.setDocument(new batasInput((int)30).getKata(NoTelp));
        KeteranganJenisPertanyaan.setDocument(new batasInput((int)30).getKata(KeteranganJenisPertanyaan));
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
        
        try {
            alarm=koneksiDB.ALARMAPOTEK();
        } catch (Exception e) {
            alarm="no";
        }
        
        if(alarm.equals("yes")){
            jam();
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
        
        WindowInput.setSize(735,245);
        WindowInput.setLocationRelativeTo(null);  
        
        ChkInput.setSelected(false);
        isForm();        
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
        KdPetugas = new widget.TextBox();
        label8 = new widget.Label();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        TanggalJawab = new widget.Tanggal();
        jLabel42 = new widget.Label();
        MetodeJawab = new widget.ComboBox();
        scrollPane2 = new widget.ScrollPane();
        Referensi = new widget.TextArea();
        label2 = new widget.Label();
        PenyampaianJawaban = new widget.ComboBox();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Jawaban = new widget.TextArea();
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
        jLabel15 = new widget.Label();
        R2 = new widget.RadioButton();
        DTPCari1 = new widget.Tanggal();
        jLabel25 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        NoRw = new widget.TextBox();
        NmPasien = new widget.TextBox();
        NoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        NoTelp = new widget.TextBox();
        jLabel8 = new widget.Label();
        NoPermintaan = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel11 = new widget.Label();
        Penanya = new widget.TextBox();
        KeteranganJenisPertanyaan = new widget.TextBox();
        TanggalPermintaan = new widget.Tanggal();
        jLabel39 = new widget.Label();
        Metode = new widget.ComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel40 = new widget.Label();
        StatusPenanya = new widget.ComboBox();
        jLabel12 = new widget.Label();
        jLabel41 = new widget.Label();
        JenisPertanyaan = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel30 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        UraianPertanyaan = new widget.TextArea();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        BtnRiwayatPasien = new widget.Button();
        BtnJawabanApoteker = new widget.Button();
        BtnDokumentasiPIO = new widget.Button();

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Jawaban Permintaan Pelayanan Informasi Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
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
        NoPermintaanJawaban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPermintaanJawabanKeyPressed(evt);
            }
        });
        internalFrame2.add(NoPermintaanJawaban);
        NoPermintaanJawaban.setBounds(99, 20, 130, 23);

        label1.setText("Tanggal Jawaban :");
        label1.setName("label1"); // NOI18N
        internalFrame2.add(label1);
        label1.setBounds(235, 20, 110, 23);

        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        internalFrame2.add(KdPetugas);
        KdPetugas.setBounds(295, 50, 130, 23);

        label8.setText("Apoteker :");
        label8.setName("label8"); // NOI18N
        internalFrame2.add(label8);
        label8.setBounds(221, 50, 70, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        internalFrame2.add(NmPetugas);
        NmPetugas.setBounds(427, 50, 270, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('3');
        btnPetugas.setToolTipText("Alt+3");
        btnPetugas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        internalFrame2.add(btnPetugas);
        btnPetugas.setBounds(695, 50, 25, 23);

        TanggalJawab.setForeground(new java.awt.Color(50, 70, 50));
        TanggalJawab.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-12-2022 07:17:14" }));
        TanggalJawab.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalJawab.setName("TanggalJawab"); // NOI18N
        TanggalJawab.setOpaque(false);
        TanggalJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalJawabKeyPressed(evt);
            }
        });
        internalFrame2.add(TanggalJawab);
        TanggalJawab.setBounds(349, 20, 130, 23);

        jLabel42.setText("Referensi :");
        jLabel42.setName("jLabel42"); // NOI18N
        internalFrame2.add(jLabel42);
        jLabel42.setBounds(0, 130, 95, 23);

        MetodeJawab.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lisan", "Tertulis", "Telepon" }));
        MetodeJawab.setName("MetodeJawab"); // NOI18N
        MetodeJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MetodeJawabKeyPressed(evt);
            }
        });
        internalFrame2.add(MetodeJawab);
        MetodeJawab.setBounds(99, 50, 100, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Referensi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Referensi.setColumns(20);
        Referensi.setRows(5);
        Referensi.setName("Referensi"); // NOI18N
        Referensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReferensiKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Referensi);

        internalFrame2.add(scrollPane2);
        scrollPane2.setBounds(99, 130, 621, 43);

        label2.setText("No.Permintaan :");
        label2.setName("label2"); // NOI18N
        internalFrame2.add(label2);
        label2.setBounds(0, 20, 95, 23);

        PenyampaianJawaban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Segera", "Dalam 24 Jam", "Lebih Dari 24 Jam" }));
        PenyampaianJawaban.setName("PenyampaianJawaban"); // NOI18N
        PenyampaianJawaban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyampaianJawabanKeyPressed(evt);
            }
        });
        internalFrame2.add(PenyampaianJawaban);
        PenyampaianJawaban.setBounds(580, 20, 140, 23);

        jLabel43.setText("Penyampaian :");
        jLabel43.setName("jLabel43"); // NOI18N
        internalFrame2.add(jLabel43);
        jLabel43.setBounds(486, 20, 90, 23);

        jLabel44.setText("Metode Jawab :");
        jLabel44.setName("jLabel44"); // NOI18N
        internalFrame2.add(jLabel44);
        jLabel44.setBounds(0, 50, 95, 23);

        jLabel45.setText("Jawaban :");
        jLabel45.setName("jLabel45"); // NOI18N
        internalFrame2.add(jLabel45);
        jLabel45.setBounds(0, 80, 95, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Jawaban.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Jawaban.setColumns(20);
        Jawaban.setRows(5);
        Jawaban.setName("Jawaban"); // NOI18N
        Jawaban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JawabanKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(Jawaban);

        internalFrame2.add(scrollPane3);
        scrollPane3.setBounds(99, 80, 621, 43);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Pelayanan Informasi Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Menunggu Jawaban Apoteker");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(195, 23));
        panelCari.add(R1);

        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(40, 23));
        panelCari.add(jLabel15);

        buttonGroup1.add(R2);
        R2.setText("Sudah Dijawab Apoteker :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(160, 23));
        panelCari.add(R2);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-12-2022" }));
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
        jLabel25.setPreferredSize(new java.awt.Dimension(30, 23));
        panelCari.add(jLabel25);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-12-2022" }));
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

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 233));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(190, 77));
        FormInput.setLayout(null);

        NoRw.setEditable(false);
        NoRw.setHighlighter(null);
        NoRw.setName("NoRw"); // NOI18N
        NoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRwKeyPressed(evt);
            }
        });
        FormInput.add(NoRw);
        NoRw.setBounds(73, 10, 125, 23);

        NmPasien.setEditable(false);
        NmPasien.setHighlighter(null);
        NmPasien.setName("NmPasien"); // NOI18N
        FormInput.add(NmPasien);
        NmPasien.setBounds(288, 10, 330, 23);

        NoRM.setEditable(false);
        NoRM.setHighlighter(null);
        NoRM.setName("NoRM"); // NOI18N
        FormInput.add(NoRM);
        NoRM.setBounds(200, 10, 86, 23);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 69, 23);

        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        NoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTelpKeyPressed(evt);
            }
        });
        FormInput.add(NoTelp);
        NoTelp.setBounds(73, 110, 110, 23);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 40, 69, 23);

        NoPermintaan.setHighlighter(null);
        NoPermintaan.setName("NoPermintaan"); // NOI18N
        NoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPermintaanKeyPressed(evt);
            }
        });
        FormInput.add(NoPermintaan);
        NoPermintaan.setBounds(488, 40, 130, 23);

        jLabel9.setText("No.Permintaan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(394, 40, 90, 23);

        jLabel11.setText("Penanya :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 80, 69, 23);

        Penanya.setHighlighter(null);
        Penanya.setName("Penanya"); // NOI18N
        Penanya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenanyaKeyPressed(evt);
            }
        });
        FormInput.add(Penanya);
        Penanya.setBounds(73, 80, 330, 23);

        KeteranganJenisPertanyaan.setHighlighter(null);
        KeteranganJenisPertanyaan.setName("KeteranganJenisPertanyaan"); // NOI18N
        KeteranganJenisPertanyaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganJenisPertanyaanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganJenisPertanyaan);
        KeteranganJenisPertanyaan.setBounds(468, 110, 150, 23);

        TanggalPermintaan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPermintaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-12-2022 07:17:13" }));
        TanggalPermintaan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPermintaan.setName("TanggalPermintaan"); // NOI18N
        TanggalPermintaan.setOpaque(false);
        TanggalPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPermintaanKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPermintaan);
        TanggalPermintaan.setBounds(73, 40, 130, 23);

        jLabel39.setText("Metode :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(228, 40, 50, 23);

        Metode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lisan", "Tertulis", "Telepon" }));
        Metode.setName("Metode"); // NOI18N
        Metode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MetodeKeyPressed(evt);
            }
        });
        FormInput.add(Metode);
        Metode.setBounds(282, 40, 95, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 635, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 635, 1);

        jLabel40.setText("Status :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(414, 80, 50, 23);

        StatusPenanya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasien", "Keluarga Pasien", "Petugas Kesehatan" }));
        StatusPenanya.setName("StatusPenanya"); // NOI18N
        StatusPenanya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusPenanyaKeyPressed(evt);
            }
        });
        FormInput.add(StatusPenanya);
        StatusPenanya.setBounds(468, 80, 150, 23);

        jLabel12.setText("No.Telp :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 110, 69, 23);

        jLabel41.setText("Jenis Pertanyaan :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(191, 110, 100, 23);

        JenisPertanyaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Identifikasi Obat", "Interaksi Obat", "Harga Obat", "Kontraindikasi", "Cara Pemakaian", "Stabilitas", "Dosis", "Keracunan", "Efek Samping Obat", "Penggunaan Terapeutik", "Farmakokinetika", "Farmakodinamika", "Ketersediaan Obat", "Lain-lain" }));
        JenisPertanyaan.setName("JenisPertanyaan"); // NOI18N
        JenisPertanyaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPertanyaanKeyPressed(evt);
            }
        });
        FormInput.add(JenisPertanyaan);
        JenisPertanyaan.setBounds(295, 110, 170, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 140, 635, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 140, 635, 1);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Uraian Pertanyaan :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(16, 140, 125, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        UraianPertanyaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        UraianPertanyaan.setColumns(20);
        UraianPertanyaan.setRows(5);
        UraianPertanyaan.setName("UraianPertanyaan"); // NOI18N
        UraianPertanyaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UraianPertanyaanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(UraianPertanyaan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(16, 160, 602, 43);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(145, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout());

        ChkAccor.setBackground(new java.awt.Color(255,250,250));
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
        BtnRiwayatPasien.setPreferredSize(new java.awt.Dimension(130, 23));
        BtnRiwayatPasien.setRoundRect(false);
        BtnRiwayatPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatPasienActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRiwayatPasien);

        BtnJawabanApoteker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnJawabanApoteker.setText("Jawaban Apoteker");
        BtnJawabanApoteker.setFocusPainted(false);
        BtnJawabanApoteker.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnJawabanApoteker.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnJawabanApoteker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnJawabanApoteker.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnJawabanApoteker.setName("BtnJawabanApoteker"); // NOI18N
        BtnJawabanApoteker.setPreferredSize(new java.awt.Dimension(130, 23));
        BtnJawabanApoteker.setRoundRect(false);
        BtnJawabanApoteker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJawabanApotekerActionPerformed(evt);
            }
        });
        FormMenu.add(BtnJawabanApoteker);

        BtnDokumentasiPIO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnDokumentasiPIO.setText("Dokumentasi PIO");
        BtnDokumentasiPIO.setFocusPainted(false);
        BtnDokumentasiPIO.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDokumentasiPIO.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDokumentasiPIO.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDokumentasiPIO.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDokumentasiPIO.setName("BtnDokumentasiPIO"); // NOI18N
        BtnDokumentasiPIO.setPreferredSize(new java.awt.Dimension(130, 23));
        BtnDokumentasiPIO.setRoundRect(false);
        BtnDokumentasiPIO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokumentasiPIOActionPerformed(evt);
            }
        });
        FormMenu.add(BtnDokumentasiPIO);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.WEST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRwKeyPressed
        //Valid.pindah(evt,Status,KdDokter);
        
}//GEN-LAST:event_NoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRw.getText().trim().equals("")||NoRM.getText().trim().equals("")||NmPasien.getText().trim().equals("")){
            Valid.textKosong(TCari,"Pasien");
        }else if(NoPermintaan.getText().trim().equals("")){
            Valid.textKosong(NoPermintaan,"No.Permintaan");
        }else if(Penanya.getText().trim().equals("")){
            Valid.textKosong(Penanya,"Penanya");
        }else if(NoTelp.getText().trim().equals("")){
            Valid.textKosong(NoTelp,"No.Telp");
        }else if(UraianPertanyaan.getText().trim().equals("")){
            Valid.textKosong(UraianPertanyaan,"Uraian Pertanyaan");
        }else{
            if(Sequel.menyimpantf("pelayanan_informasi_obat","?,?,?,?,?,?,?,?,?,?","No.Permintaan",10,new String[]{
                NoPermintaan.getText(),NoRw.getText(),Valid.SetTgl(TanggalPermintaan.getSelectedItem()+"")+" "+TanggalPermintaan.getSelectedItem().toString().substring(11,19),
                Metode.getSelectedItem().toString(),Penanya.getText(),StatusPenanya.getSelectedItem().toString(),NoTelp.getText(),JenisPertanyaan.getSelectedItem().toString(),
                KeteranganJenisPertanyaan.getText(),UraianPertanyaan.getText()
            })==true){
                if(akses.getjawaban_pio_apoteker()==true){
                    i=JOptionPane.showConfirmDialog(null, "Apakah akan langsung diberikan jawaban..????","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if(i==JOptionPane.YES_OPTION){
                        NoPermintaanJawaban.setText(NoPermintaan.getText());
                        WindowInput.setAlwaysOnTop(false);
                        WindowInput.setVisible(true);
                    }else{
                        R1.setSelected(true);
                        tampil();
                        emptTeks();
                    }
                }else{
                    R1.setSelected(true);
                    tampil();
                    emptTeks();
                }   
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           //Valid.pindah(evt,Catatan,BtnBatal);
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
            if(Sequel.queryu2tf("delete from pelayanan_informasi_obat where no_permintaan=?",1,new String[]{
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
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInput.dispose();
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
            petugas.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                if(R1.isSelected()==true){
                    ps=koneksi.prepareStatement(
                        "select pelayanan_informasi_obat.no_permintaan,pelayanan_informasi_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,"+
                        "reg_periksa.sttsumur,pasien.no_tlp,penjab.png_jawab,pelayanan_informasi_obat.tanggal,pelayanan_informasi_obat.metode,pelayanan_informasi_obat.penanya,"+
                        "pelayanan_informasi_obat.status_penanya,pelayanan_informasi_obat.no_telp_penanya,pelayanan_informasi_obat.jenis_pertanyaan,pelayanan_informasi_obat.keterangan_jenis_pertanyaan,"+
                        "pelayanan_informasi_obat.uraian_pertanyaan from pelayanan_informasi_obat inner join reg_periksa on pelayanan_informasi_obat.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj  "+
                        "where pelayanan_informasi_obat.no_permintaan not in (select DISTINCT jawaban_pio_apoteker.no_permintaan from jawaban_pio_apoteker) "+
                        (TCari.getText().equals("")?"":"and (pelayanan_informasi_obat.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? "+
                        "or penjab.png_jawab like ? or pelayanan_informasi_obat.no_permintaan like ? or pelayanan_informasi_obat.metode like ? or pelayanan_informasi_obat.penanya like ? "+
                        "or pelayanan_informasi_obat.status_penanya like ?)")+" order by pelayanan_informasi_obat.tanggal");
                    try {
                        htmlContent = new StringBuilder();
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Telp</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Bayar</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal Pertanyaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Metode</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penanya</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Penanya</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Telp Penanya</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Pertanyaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ket.Jenis Pertanyaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Uraian Pertanyaan</b></td>"+
                            "</tr>"
                        );
                        if(!TCari.getText().equals("")){
                            ps.setString(1,"%"+TCari.getText().trim()+"%");
                            ps.setString(2,"%"+TCari.getText().trim()+"%");
                            ps.setString(3,"%"+TCari.getText().trim()+"%");
                            ps.setString(4,"%"+TCari.getText().trim()+"%");
                            ps.setString(5,"%"+TCari.getText().trim()+"%");
                            ps.setString(6,"%"+TCari.getText().trim()+"%");
                            ps.setString(7,"%"+TCari.getText().trim()+"%");
                            ps.setString(8,"%"+TCari.getText().trim()+"%");
                        }
                        rs=ps.executeQuery();
                        while(rs.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                   "<td valign='top'>"+rs.getString("no_permintaan")+"</td>"+
                                   "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                   "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                   "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                   "<td valign='top'>"+rs.getString("jk")+"</td>"+
                                   "<td valign='top'>"+rs.getString("umurdaftar")+" "+rs.getString("sttsumur")+"</td>"+
                                   "<td valign='top'>"+rs.getString("no_tlp")+"</td>"+
                                   "<td valign='top'>"+rs.getString("png_jawab")+"</td>"+
                                   "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                                   "<td valign='top'>"+rs.getString("metode")+"</td>"+
                                   "<td valign='top'>"+rs.getString("penanya")+"</td>"+
                                   "<td valign='top'>"+rs.getString("status_penanya")+"</td>"+
                                   "<td valign='top'>"+rs.getString("no_telp_penanya")+"</td>"+
                                   "<td valign='top'>"+rs.getString("jenis_pertanyaan")+"</td>"+
                                   "<td valign='top'>"+rs.getString("keterangan_jenis_pertanyaan")+"</td>"+
                                   "<td valign='top'>"+rs.getString("uraian_pertanyaan")+"</td>"+
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

                        File f = new File("PermintaanInformasiObat.html");            
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                        bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                    "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                    "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr class='isi2'>"+
                                            "<td valign='top' align='center'>"+
                                                "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                "<font size='2' face='Tahoma'>DATA PERMINTAAN PELAYANAN INFORMASI OBAT BELUM TERLAYANI<br><br></font>"+        
                                            "</td>"+
                                       "</tr>"+
                                    "</table>")
                        );
                        bw.close();                         
                        Desktop.getDesktop().browse(f.toURI());
                    } catch (Exception e) {
                        System.out.println("Notif Kamar : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }
                }else if(R2.isSelected()==true){
                    ps=koneksi.prepareStatement(
                        "select pelayanan_informasi_obat.no_permintaan,pelayanan_informasi_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,"+
                        "reg_periksa.sttsumur,pasien.no_tlp,penjab.png_jawab,pelayanan_informasi_obat.tanggal,pelayanan_informasi_obat.metode,pelayanan_informasi_obat.penanya,"+
                        "pelayanan_informasi_obat.status_penanya,pelayanan_informasi_obat.no_telp_penanya,pelayanan_informasi_obat.jenis_pertanyaan,pelayanan_informasi_obat.keterangan_jenis_pertanyaan,"+
                        "pelayanan_informasi_obat.uraian_pertanyaan,jawaban_pio_apoteker.tanggal_jawab,jawaban_pio_apoteker.metode,jawaban_pio_apoteker.penyampaian_jawaban,"+
                        "jawaban_pio_apoteker.jawaban,jawaban_pio_apoteker.referensi,jawaban_pio_apoteker.nip,petugas.nama from pelayanan_informasi_obat "+
                        "inner join reg_periksa on pelayanan_informasi_obat.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join jawaban_pio_apoteker on jawaban_pio_apoteker.no_permintaan=pelayanan_informasi_obat.no_permintaan "+
                        "inner join petugas on jawaban_pio_apoteker.nip=petugas.nip where jawaban_pio_apoteker.tanggal_jawab between ? and ? "+
                        (TCari.getText().equals("")?"":"and (pelayanan_informasi_obat.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? "+
                        "or penjab.png_jawab like ? or pelayanan_informasi_obat.no_permintaan like ? or pelayanan_informasi_obat.metode like ? or pelayanan_informasi_obat.penanya like ? "+
                        "or pelayanan_informasi_obat.status_penanya like ?)")+" order by pelayanan_informasi_obat.tanggal");
                    try {
                        htmlContent = new StringBuilder();
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Telp</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Bayar</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal Pertanyaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Metode</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penanya</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Penanya</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Telp Penanya</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Pertanyaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ket.Jenis Pertanyaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Uraian Pertanyaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jawaban Pertanyaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Referensi Jawaban</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyampaian Jawaban</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Metode Jawaban</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal Jawaban</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Apoteker</b></td>"+
                            "</tr>"
                        );
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        if(!TCari.getText().equals("")){
                            ps.setString(3,"%"+TCari.getText().trim()+"%");
                            ps.setString(4,"%"+TCari.getText().trim()+"%");
                            ps.setString(5,"%"+TCari.getText().trim()+"%");
                            ps.setString(6,"%"+TCari.getText().trim()+"%");
                            ps.setString(7,"%"+TCari.getText().trim()+"%");
                            ps.setString(8,"%"+TCari.getText().trim()+"%");
                            ps.setString(9,"%"+TCari.getText().trim()+"%");
                            ps.setString(10,"%"+TCari.getText().trim()+"%");
                        }
                        rs=ps.executeQuery();
                        while(rs.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                   "<td valign='top'>"+rs.getString("no_permintaan")+"</td>"+
                                   "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                   "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                   "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                   "<td valign='top'>"+rs.getString("jk")+"</td>"+
                                   "<td valign='top'>"+rs.getString("umurdaftar")+" "+rs.getString("sttsumur")+"</td>"+
                                   "<td valign='top'>"+rs.getString("no_tlp")+"</td>"+
                                   "<td valign='top'>"+rs.getString("png_jawab")+"</td>"+
                                   "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                                   "<td valign='top'>"+rs.getString("metode")+"</td>"+
                                   "<td valign='top'>"+rs.getString("penanya")+"</td>"+
                                   "<td valign='top'>"+rs.getString("status_penanya")+"</td>"+
                                   "<td valign='top'>"+rs.getString("no_telp_penanya")+"</td>"+
                                   "<td valign='top'>"+rs.getString("jenis_pertanyaan")+"</td>"+
                                   "<td valign='top'>"+rs.getString("keterangan_jenis_pertanyaan")+"</td>"+
                                   "<td valign='top'>"+rs.getString("uraian_pertanyaan")+"</td>"+
                                   "<td valign='top'>"+rs.getString("jawaban")+"</td>"+
                                   "<td valign='top'>"+rs.getString("referensi")+"</td>"+
                                   "<td valign='top'>"+rs.getString("penyampaian_jawaban")+"</td>"+
                                   "<td valign='top'>"+rs.getString("metode")+"</td>"+
                                   "<td valign='top'>"+rs.getString("tanggal_jawab")+"</td>"+
                                   "<td valign='top'>"+rs.getString("nip")+"</td>"+
                                   "<td valign='top'>"+rs.getString("nama")+"</td>"+
                                "</tr>");
                        }
                        LoadHTML.setText(
                            "<html>"+
                              "<table width='2100px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                        File f = new File("PermintaanInformasiObat.html");            
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                        bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                    "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                    "<table width='2100px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr class='isi2'>"+
                                            "<td valign='top' align='center'>"+
                                                "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                "<font size='2' face='Tahoma'>DATA PERMINTAAN PELAYANAN INFORMASI OBAT BELUM TERLAYANI<br><br></font>"+        
                                            "</td>"+
                                       "</tr>"+
                                    "</table>")
                        );
                        bw.close();                         
                        Desktop.getDesktop().browse(f.toURI());
                    } catch (Exception e) {
                        System.out.println("Notif Kamar : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }
                }
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
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
            Valid.pindah(evt, BtnCari, NmPasien);
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

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoRw.getText().trim().equals("")||NoRM.getText().trim().equals("")||NmPasien.getText().trim().equals("")){
            Valid.textKosong(TCari,"Pasien");
        }else if(NoPermintaan.getText().trim().equals("")){
            Valid.textKosong(NoPermintaan,"No.Permintaan");
        }else if(Penanya.getText().trim().equals("")){
            Valid.textKosong(Penanya,"Penanya");
        }else if(NoTelp.getText().trim().equals("")){
            Valid.textKosong(NoTelp,"No.Telp");
        }else if(UraianPertanyaan.getText().trim().equals("")){
            Valid.textKosong(UraianPertanyaan,"Uraian Pertanyaan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(!tbObat.getValueAt(tbObat.getSelectedRow(),21).toString().equals("")){
                        JOptionPane.showMessageDialog(null,"Sudah ada jawaban apoteker, data tidak bisa diubah ..!!");
                    }else{
                        ganti();
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
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        aktif=true;
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void DTPCari2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari2ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPCari2ItemStateChanged

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari1ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPCari1ItemStateChanged

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        aktif=false;
    }//GEN-LAST:event_formWindowClosed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnJawabanApotekerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJawabanApotekerActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                NoPermintaanJawaban.setText(NoPermintaan.getText());
                WindowInput.setAlwaysOnTop(false);
                WindowInput.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnJawabanApotekerActionPerformed

    private void BtnDokumentasiPIOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokumentasiPIOActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                if(!tbObat.getValueAt(tbObat.getSelectedRow(),21).toString().equals("")){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());  
                    finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),22).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),21).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString())); 
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDokumentasiPelayananInformasiObat.jasper","report","::[ Dokumentasi Pelayanan Informasi Obat ]::",
                            "select pasien.tgl_lahir,pelayanan_informasi_obat.no_permintaan,pelayanan_informasi_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,"+
                            "reg_periksa.sttsumur,pasien.no_tlp,penjab.png_jawab,pelayanan_informasi_obat.tanggal,pelayanan_informasi_obat.metode,pelayanan_informasi_obat.penanya,"+
                            "pelayanan_informasi_obat.status_penanya,pelayanan_informasi_obat.no_telp_penanya,pelayanan_informasi_obat.jenis_pertanyaan,pelayanan_informasi_obat.keterangan_jenis_pertanyaan,"+
                            "pelayanan_informasi_obat.uraian_pertanyaan,jawaban_pio_apoteker.tanggal_jawab,jawaban_pio_apoteker.metode as metodejawab,jawaban_pio_apoteker.penyampaian_jawaban,"+
                            "jawaban_pio_apoteker.jawaban,jawaban_pio_apoteker.referensi,jawaban_pio_apoteker.nip,petugas.nama from pelayanan_informasi_obat "+
                            "inner join reg_periksa on pelayanan_informasi_obat.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join jawaban_pio_apoteker on jawaban_pio_apoteker.no_permintaan=pelayanan_informasi_obat.no_permintaan "+
                            "inner join petugas on jawaban_pio_apoteker.nip=petugas.nip where pelayanan_informasi_obat.no_permintaan='"+NoPermintaan.getText()+"' ",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, belum ada jawaban apoteker...!!!!");
                }   
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnDokumentasiPIOActionPerformed

    private void BtnRiwayatPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatPasienActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
                resume.setNoRm(NoRM.getText(),NmPasien.getText());
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnRiwayatPasienActionPerformed

    private void TanggalPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPermintaanKeyPressed
        Valid.pindah2(evt,TCari,Metode);
    }//GEN-LAST:event_TanggalPermintaanKeyPressed

    private void MetodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MetodeKeyPressed
        Valid.pindah(evt,TanggalPermintaan,NoPermintaan);
    }//GEN-LAST:event_MetodeKeyPressed

    private void StatusPenanyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusPenanyaKeyPressed
        Valid.pindah(evt,Penanya,NoTelp);
    }//GEN-LAST:event_StatusPenanyaKeyPressed

    private void JenisPertanyaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPertanyaanKeyPressed
        Valid.pindah(evt,NoTelp,KeteranganJenisPertanyaan);
    }//GEN-LAST:event_JenisPertanyaanKeyPressed

    private void UraianPertanyaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UraianPertanyaanKeyPressed
        Valid.pindah2(evt,KeteranganJenisPertanyaan,BtnSimpan);
    }//GEN-LAST:event_UraianPertanyaanKeyPressed

    private void NoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPermintaanKeyPressed
        Valid.pindah(evt,Metode,Penanya);
    }//GEN-LAST:event_NoPermintaanKeyPressed

    private void PenanyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenanyaKeyPressed
        Valid.pindah(evt,NoPermintaan,StatusPenanya);
    }//GEN-LAST:event_PenanyaKeyPressed

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed
        Valid.pindah(evt,StatusPenanya,JenisPertanyaan);
    }//GEN-LAST:event_NoTelpKeyPressed

    private void KeteranganJenisPertanyaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganJenisPertanyaanKeyPressed
        Valid.pindah(evt,JenisPertanyaan,UraianPertanyaan);
    }//GEN-LAST:event_KeteranganJenisPertanyaanKeyPressed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        petugas.dispose();
        WindowInput.dispose();
    }//GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal, NoPermintaanJawaban);}
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpanJawabanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanJawabanActionPerformed
        if(NoRw.getText().trim().equals("")||NoRM.getText().trim().equals("")||NmPasien.getText().trim().equals("")){
            Valid.textKosong(TCari,"Pasien");
        }else if(NoPermintaan.getText().trim().equals("")){
            Valid.textKosong(NoPermintaan,"No.Permintaan");
        }else if(Penanya.getText().trim().equals("")){
            Valid.textKosong(Penanya,"Penanya");
        }else if(NoTelp.getText().trim().equals("")){
            Valid.textKosong(NoTelp,"No.Telp");
        }else if(UraianPertanyaan.getText().trim().equals("")){
            Valid.textKosong(UraianPertanyaan,"Uraian Pertanyaan");
        }else if(NoPermintaanJawaban.getText().trim().equals("")){
            Valid.textKosong(NoPermintaanJawaban,"No.Permintaan Jawaban");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(NmPetugas,"Petugas Apoteker");
        }else if(Jawaban.getText().trim().equals("")){
            Valid.textKosong(Jawaban,"Jawaban Apoteker");
        }else if(Referensi.getText().trim().equals("")){
            Valid.textKosong(Referensi,"Referensi Jawaban");
        }else{
            if(Sequel.menyimpantf("jawaban_pio_apoteker","?,?,?,?,?,?,?",7,new String[]{
                    NoPermintaan.getText(),Valid.SetTgl(TanggalJawab.getSelectedItem()+"")+" "+TanggalJawab.getSelectedItem().toString().substring(11,19),
                    MetodeJawab.getSelectedItem().toString(),PenyampaianJawaban.getSelectedItem().toString(),Jawaban.getText(),Referensi.getText(),
                    KdPetugas.getText()
                },"no_permintaan=?","tanggal_jawab=?,metode=?,penyampaian_jawaban=?,jawaban=?,referensi=?,nip=?",7,new String[]{
                    Valid.SetTgl(TanggalJawab.getSelectedItem()+"")+" "+TanggalJawab.getSelectedItem().toString().substring(11,19),
                    MetodeJawab.getSelectedItem().toString(),PenyampaianJawaban.getSelectedItem().toString(),Jawaban.getText(),Referensi.getText(),
                    KdPetugas.getText(),NoPermintaan.getText()
                })==true){
                    R2.setSelected(true);
                    tampil();
                    emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan atau mengedit jawaban pelayanan informasi obat...!!!");
                R1.setSelected(true);
                tampil();
                emptTeks();
            }
        }
    }//GEN-LAST:event_BtnSimpanJawabanActionPerformed

    private void BtnSimpanJawabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanJawabanKeyPressed
        //Valid.pindah(evt,cmbDtk,BtnBatal);
    }//GEN-LAST:event_BtnSimpanJawabanKeyPressed

    private void BtnBatalJawabanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalJawabanActionPerformed
        TanggalJawab.setDate(new Date());
        PenyampaianJawaban.setSelectedIndex(0);
        MetodeJawab.setSelectedIndex(0);
        Jawaban.setText("");
        Referensi.setText("");
    }//GEN-LAST:event_BtnBatalJawabanActionPerformed

    private void BtnBatalJawabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalJawabanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
    }//GEN-LAST:event_BtnBatalJawabanKeyPressed

    private void NoPermintaanJawabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPermintaanJawabanKeyPressed
        
    }//GEN-LAST:event_NoPermintaanJawabanKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void TanggalJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalJawabKeyPressed

    private void MetodeJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MetodeJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MetodeJawabKeyPressed

    private void ReferensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReferensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReferensiKeyPressed

    private void PenyampaianJawabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyampaianJawabanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyampaianJawabanKeyPressed

    private void JawabanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JawabanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JawabanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaanPelayananInformasiObat dialog = new DlgPermintaanPelayananInformasiObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatalJawaban;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn;
    private widget.Button BtnDokumentasiPIO;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnJawabanApoteker;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRiwayatPasien;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanJawaban;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.TextArea Jawaban;
    private widget.ComboBox JenisPertanyaan;
    private widget.TextBox KdPetugas;
    private widget.TextBox KeteranganJenisPertanyaan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Metode;
    private widget.ComboBox MetodeJawab;
    private widget.TextBox NmPasien;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoPermintaan;
    private widget.TextBox NoPermintaanJawaban;
    private widget.TextBox NoRM;
    private widget.TextBox NoRw;
    private widget.TextBox NoTelp;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Penanya;
    private widget.ComboBox PenyampaianJawaban;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.TextArea Referensi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollMenu;
    private widget.ComboBox StatusPenanya;
    private widget.TextBox TCari;
    private widget.Tanggal TanggalJawab;
    private widget.Tanggal TanggalPermintaan;
    private widget.TextArea UraianPertanyaan;
    private javax.swing.JDialog WindowInput;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel25;
    private widget.Label jLabel30;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private widget.Label label1;
    private widget.Label label2;
    private widget.Label label8;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {     
        Valid.tabelKosong(tabMode);
        try{ 
            if(R1.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select pelayanan_informasi_obat.no_permintaan,pelayanan_informasi_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.no_tlp,penjab.png_jawab,pelayanan_informasi_obat.tanggal,pelayanan_informasi_obat.metode,pelayanan_informasi_obat.penanya,"+
                    "pelayanan_informasi_obat.status_penanya,pelayanan_informasi_obat.no_telp_penanya,pelayanan_informasi_obat.jenis_pertanyaan,pelayanan_informasi_obat.keterangan_jenis_pertanyaan,"+
                    "pelayanan_informasi_obat.uraian_pertanyaan from pelayanan_informasi_obat inner join reg_periksa on pelayanan_informasi_obat.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj  "+
                    "where pelayanan_informasi_obat.no_permintaan not in (select DISTINCT jawaban_pio_apoteker.no_permintaan from jawaban_pio_apoteker) "+
                    (TCari.getText().equals("")?"":"and (pelayanan_informasi_obat.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? "+
                    "or penjab.png_jawab like ? or pelayanan_informasi_obat.no_permintaan like ? or pelayanan_informasi_obat.metode like ? or pelayanan_informasi_obat.penanya like ? "+
                    "or pelayanan_informasi_obat.status_penanya like ?)")+" order by pelayanan_informasi_obat.tanggal");
                try {
                    if(!TCari.getText().equals("")){
                        ps.setString(1,"%"+TCari.getText().trim()+"%");
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                        ps.setString(7,"%"+TCari.getText().trim()+"%");
                        ps.setString(8,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new String[]{
                            rs.getString("no_permintaan"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                            rs.getString("no_tlp"),rs.getString("png_jawab"),rs.getString("tanggal"),rs.getString("metode"),rs.getString("penanya"),rs.getString("status_penanya"),rs.getString("no_telp_penanya"),
                            rs.getString("jenis_pertanyaan"),rs.getString("keterangan_jenis_pertanyaan"),rs.getString("uraian_pertanyaan"),"","","","","","",""
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif Kamar : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }else if(R2.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select pelayanan_informasi_obat.no_permintaan,pelayanan_informasi_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.no_tlp,penjab.png_jawab,pelayanan_informasi_obat.tanggal,pelayanan_informasi_obat.metode,pelayanan_informasi_obat.penanya,"+
                    "pelayanan_informasi_obat.status_penanya,pelayanan_informasi_obat.no_telp_penanya,pelayanan_informasi_obat.jenis_pertanyaan,pelayanan_informasi_obat.keterangan_jenis_pertanyaan,"+
                    "pelayanan_informasi_obat.uraian_pertanyaan,jawaban_pio_apoteker.tanggal_jawab,jawaban_pio_apoteker.metode as metodejawab,jawaban_pio_apoteker.penyampaian_jawaban,"+
                    "jawaban_pio_apoteker.jawaban,jawaban_pio_apoteker.referensi,jawaban_pio_apoteker.nip,petugas.nama from pelayanan_informasi_obat "+
                    "inner join reg_periksa on pelayanan_informasi_obat.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join jawaban_pio_apoteker on jawaban_pio_apoteker.no_permintaan=pelayanan_informasi_obat.no_permintaan "+
                    "inner join petugas on jawaban_pio_apoteker.nip=petugas.nip where jawaban_pio_apoteker.tanggal_jawab between ? and ? "+
                    (TCari.getText().equals("")?"":"and (pelayanan_informasi_obat.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? "+
                    "or penjab.png_jawab like ? or pelayanan_informasi_obat.no_permintaan like ? or pelayanan_informasi_obat.metode like ? or pelayanan_informasi_obat.penanya like ? "+
                    "or pelayanan_informasi_obat.status_penanya like ?)")+" order by pelayanan_informasi_obat.tanggal");
                try {
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    if(!TCari.getText().equals("")){
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                        ps.setString(7,"%"+TCari.getText().trim()+"%");
                        ps.setString(8,"%"+TCari.getText().trim()+"%");
                        ps.setString(9,"%"+TCari.getText().trim()+"%");
                        ps.setString(10,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new String[]{
                            rs.getString("no_permintaan"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                            rs.getString("no_tlp"),rs.getString("png_jawab"),rs.getString("tanggal"),rs.getString("metode"),rs.getString("penanya"),rs.getString("status_penanya"),rs.getString("no_telp_penanya"),
                            rs.getString("jenis_pertanyaan"),rs.getString("keterangan_jenis_pertanyaan"),rs.getString("uraian_pertanyaan"),rs.getString("jawaban"),rs.getString("referensi"),rs.getString("penyampaian_jawaban"),
                            rs.getString("metodejawab"),rs.getString("tanggal_jawab"),rs.getString("nip"),rs.getString("nama")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif Kamar : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }


    public void emptTeks() {
        NoRw.setText("");
        NoRM.setText("");
        NmPasien.setText("");
        NoTelp.setText("");
        KeteranganJenisPertanyaan.setText("");
        UraianPertanyaan.setText("");
        Penanya.setText("");
        TanggalPermintaan.setDate(new Date());
        Metode.setSelectedIndex(0);
        StatusPenanya.setSelectedIndex(0);
        JenisPertanyaan.setSelectedIndex(0);
        NoPermintaanJawaban.setText("");
        TanggalJawab.setDate(new Date());
        PenyampaianJawaban.setSelectedIndex(0);
        MetodeJawab.setSelectedIndex(0);
        Jawaban.setText("");
        Referensi.setText("");
        autoNomor();
        Metode.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoPermintaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            NoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
            NoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            NmPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Metode.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Penanya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            StatusPenanya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            JenisPertanyaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KeteranganJenisPertanyaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            UraianPertanyaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Jawaban.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Referensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            PenyampaianJawaban.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            MetodeJawab.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Valid.SetTgl2(TanggalPermintaan,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Valid.SetTgl2(TanggalJawab,tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
        }
    }
    
    public void setNoRm(String norwt,String norm,String nama) {
        NoRw.setText(norwt);
        NoRM.setText(norm);
        NmPasien.setText(nama);
        TCari.setText(norwt);
        ChkInput.setSelected(true);
        aktif=false;
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,233));
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
        BtnSimpan.setEnabled(akses.getpelayanan_informasi_obat());
        BtnHapus.setEnabled(akses.getpelayanan_informasi_obat());
        BtnPrint.setEnabled(akses.getpelayanan_informasi_obat());
        BtnJawabanApoteker.setEnabled(akses.getjawaban_pio_apoteker());
        BtnRiwayatPasien.setEnabled(akses.getresume_pasien());
        BtnEdit.setEnabled(akses.getpelayanan_informasi_obat());   
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
    }

    private void jam(){
        ActionListener taskPerformer = (ActionEvent e) -> {
            if(aktif==true){
                nol_detik = "";
                Date now = Calendar.getInstance().getTime();
                nilai_detik = now.getSeconds();
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                detik = nol_detik + Integer.toString(nilai_detik);
                if(detik.equals("05")){
                    bookingbaru=Sequel.cariInteger("select count(*) from pelayanan_informasi_obat where pelayanan_informasi_obat.no_permintaan not in (select DISTINCT jawaban_pio_apoteker.no_permintaan from jawaban_pio_apoteker) ");
                    if(bookingbaru>0){
                        try {
                            music = new BackgroundMusic("./suara/alarm.mp3");
                            music.start();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        
                        i=JOptionPane.showConfirmDialog(null, "Ada permintaan pelayanan informasi obat baru, apa mau ditampilkan????","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if(i==JOptionPane.YES_OPTION){
                            R1.setSelected(true);
                            TCari.setText("");
                            tampil();
                        }
                    }
                }
            }                
        };
        new Timer(1000, taskPerformer).start();
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(145,HEIGHT));
            FormMenu.setVisible(true); 
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){  
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);    
            ChkAccor.setVisible(true);
        }
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pelayanan_informasi_obat.no_permintaan,4),signed)),0) from pelayanan_informasi_obat where pelayanan_informasi_obat.tanggal='"+Valid.SetTgl(TanggalPermintaan.getSelectedItem()+"")+"' ","PIO"+Valid.SetTgl(TanggalPermintaan.getSelectedItem()+"").replaceAll("-",""),4,NoPermintaan);           
    }

    private void ganti() {
        if(Sequel.mengedittf("pelayanan_informasi_obat","no_permintaan=?","no_permintaan=?,no_rawat=?,tanggal=?,metode=?,penanya=?,status_penanya=?,no_telp_penanya=?,jenis_pertanyaan=?,keterangan_jenis_pertanyaan=?,uraian_pertanyaan=?",11,new String[]{
                NoPermintaan.getText(),NoRw.getText(),Valid.SetTgl(TanggalPermintaan.getSelectedItem()+"")+" "+TanggalPermintaan.getSelectedItem().toString().substring(11,19),
                Metode.getSelectedItem().toString(),Penanya.getText(),StatusPenanya.getSelectedItem().toString(),NoTelp.getText(),JenisPertanyaan.getSelectedItem().toString(),
                KeteranganJenisPertanyaan.getText(),UraianPertanyaan.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                tampil();
                emptTeks();
        }
    }
    
}

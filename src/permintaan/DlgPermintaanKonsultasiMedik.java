package permintaan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
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
import kepegawaian.DlgCariDokter;
import rekammedis.RMRiwayatPerawatan;

/**
 *
 * @author dosen
 */
public class DlgPermintaanKonsultasiMedik extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String finger="",sql="";
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgPermintaanKonsultasiMedik(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","No.Rawat","No.RM","Nama Pasien","J.K.","Umur","No.Telp","Cara Bayar","Tanggal Konsultasi","Permintaan",
                "Kode Dokter Konsul","Nama Dokter Konsul","Kode Dokter Dikonsuli","Nama Dokter Dikonsuli","Diagnosa Kerja Konsul",
                "Uraian Konsultasi","Tanggal Jawaban","Diagnosa Kerja Dikonsuli","Jawaban Konsultasi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
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
                column.setPreferredWidth(120);
            }else if(i==10){
                column.setPreferredWidth(102);
            }else if(i==11){
                column.setPreferredWidth(160);
            }else if(i==12){
                column.setPreferredWidth(115);
            }else if(i==13){
                column.setPreferredWidth(160);
            }else if(i==14){
                column.setPreferredWidth(200);
            }else if(i==15){
                column.setPreferredWidth(350);
            }else if(i==16){
                column.setPreferredWidth(115);
            }else if(i==17){
                column.setPreferredWidth(200);
            }else if(i==18){
                column.setPreferredWidth(350);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        NoRw.setDocument(new batasInput((byte)17).getKata(NoRw));
        NoPermintaan.setDocument(new batasInput((byte)20).getKata(NoPermintaan));
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    if(i==1){
                        KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        BtnDokter.requestFocus();
                    }else if(i==2){
                        KdDokterDikonsuli.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NmDokterDikonsuli.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        BtnDokterDIkonsuli.requestFocus();
                    }
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
        TanggalJawab = new widget.Tanggal();
        jLabel42 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        JawabanKonsultasi = new widget.TextArea();
        label2 = new widget.Label();
        JawabanDiagnosaKerja = new widget.TextBox();
        label3 = new widget.Label();
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
        jLabel8 = new widget.Label();
        NoPermintaan = new widget.TextBox();
        jLabel9 = new widget.Label();
        TanggalPermintaan = new widget.Tanggal();
        jLabel39 = new widget.Label();
        Permintaan = new widget.ComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel30 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        UraianKonsultasi = new widget.TextArea();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        BtnDokterDIkonsuli = new widget.Button();
        NmDokterDikonsuli = new widget.TextBox();
        KdDokterDikonsuli = new widget.TextBox();
        label15 = new widget.Label();
        label16 = new widget.Label();
        jLabel31 = new widget.Label();
        DiagnosaKerja = new widget.TextBox();
        jLabel32 = new widget.Label();
        jLabel10 = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        BtnRiwayatPasien = new widget.Button();
        BtnJawabanDikonsuli = new widget.Button();
        BtnDokumentasiKonsul = new widget.Button();

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
        TanggalJawab.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-07-2024 17:18:46" }));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Konsultasi Medik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        R1.setText("Menunggu Jawaban Konsultasi");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(195, 23));
        panelCari.add(R1);

        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(30, 23));
        panelCari.add(jLabel15);

        buttonGroup1.add(R2);
        R2.setText("Konsultasi Sudah Dijawab :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(170, 23));
        panelCari.add(R2);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-07-2024" }));
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

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-07-2024" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 293));
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

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 69, 23);

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
        NoPermintaan.setBounds(509, 40, 109, 23);

        jLabel9.setText("No.Permintaan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(415, 40, 90, 23);

        TanggalPermintaan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPermintaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-07-2024 17:18:46" }));
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

        jLabel39.setText("Permintaan :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(199, 40, 77, 23);

        Permintaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Konsultasi", "Evaluasi", "Rawat Bersama", "Alih Rawat", "Pre/Post Operasi" }));
        Permintaan.setName("Permintaan"); // NOI18N
        Permintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PermintaanKeyPressed(evt);
            }
        });
        FormInput.add(Permintaan);
        Permintaan.setBounds(280, 40, 135, 23);

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
        jLabel30.setText("Diagnosa Kerja");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(16, 150, 90, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        UraianKonsultasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        UraianKonsultasi.setColumns(20);
        UraianKonsultasi.setRows(5);
        UraianKonsultasi.setName("UraianKonsultasi"); // NOI18N
        UraianKonsultasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UraianKonsultasiKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(UraianKonsultasi);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(16, 200, 602, 63);

        label14.setText("Dokter Yang Konsul :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 80, 129, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(133, 80, 123, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(258, 80, 330, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(590, 80, 28, 23);

        BtnDokterDIkonsuli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterDIkonsuli.setMnemonic('2');
        BtnDokterDIkonsuli.setToolTipText("Alt+2");
        BtnDokterDIkonsuli.setName("BtnDokterDIkonsuli"); // NOI18N
        BtnDokterDIkonsuli.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterDIkonsuli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterDIkonsuliActionPerformed(evt);
            }
        });
        BtnDokterDIkonsuli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterDIkonsuliKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokterDIkonsuli);
        BtnDokterDIkonsuli.setBounds(590, 110, 28, 23);

        NmDokterDikonsuli.setEditable(false);
        NmDokterDikonsuli.setName("NmDokterDikonsuli"); // NOI18N
        NmDokterDikonsuli.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokterDikonsuli);
        NmDokterDikonsuli.setBounds(258, 110, 330, 23);

        KdDokterDikonsuli.setEditable(false);
        KdDokterDikonsuli.setName("KdDokterDikonsuli"); // NOI18N
        KdDokterDikonsuli.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokterDikonsuli);
        KdDokterDikonsuli.setBounds(133, 110, 123, 23);

        label15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label15.setText("Dokter Yang Dikonsuli");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(16, 110, 130, 23);

        label16.setText(":");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(0, 110, 129, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Uraian Konsultasi :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(16, 180, 125, 23);

        DiagnosaKerja.setHighlighter(null);
        DiagnosaKerja.setName("DiagnosaKerja"); // NOI18N
        DiagnosaKerja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKerjaKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaKerja);
        DiagnosaKerja.setBounds(100, 150, 518, 23);

        jLabel32.setText(":");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 150, 96, 23);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("No.Rawat");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(16, 10, 69, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(145, 43));
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
        BtnRiwayatPasien.setPreferredSize(new java.awt.Dimension(130, 23));
        BtnRiwayatPasien.setRoundRect(false);
        BtnRiwayatPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatPasienActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRiwayatPasien);

        BtnJawabanDikonsuli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnJawabanDikonsuli.setText("Jawaban Konsul");
        BtnJawabanDikonsuli.setFocusPainted(false);
        BtnJawabanDikonsuli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnJawabanDikonsuli.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnJawabanDikonsuli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnJawabanDikonsuli.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnJawabanDikonsuli.setName("BtnJawabanDikonsuli"); // NOI18N
        BtnJawabanDikonsuli.setPreferredSize(new java.awt.Dimension(130, 23));
        BtnJawabanDikonsuli.setRoundRect(false);
        BtnJawabanDikonsuli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJawabanDikonsuliActionPerformed(evt);
            }
        });
        FormMenu.add(BtnJawabanDikonsuli);

        BtnDokumentasiKonsul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnDokumentasiKonsul.setText("Dokumentasi Konsul");
        BtnDokumentasiKonsul.setFocusPainted(false);
        BtnDokumentasiKonsul.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDokumentasiKonsul.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDokumentasiKonsul.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDokumentasiKonsul.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDokumentasiKonsul.setName("BtnDokumentasiKonsul"); // NOI18N
        BtnDokumentasiKonsul.setPreferredSize(new java.awt.Dimension(130, 23));
        BtnDokumentasiKonsul.setRoundRect(false);
        BtnDokumentasiKonsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokumentasiKonsulActionPerformed(evt);
            }
        });
        FormMenu.add(BtnDokumentasiKonsul);

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
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Konsul");
        }else if(KdDokter.getText().equals(KdDokterDikonsuli.getText())){
           JOptionPane.showMessageDialog(null,"Maaf, Dokter yang dikonsuli jangan diri sendiri...!");
        }else if(KdDokterDikonsuli.getText().trim().equals("")||NmDokterDikonsuli.getText().trim().equals("")){
            Valid.textKosong(BtnDokterDIkonsuli,"Dokter Dikonsuli");
        }else if(DiagnosaKerja.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKerja,"Diagnosa Kerja");
        }else if(UraianKonsultasi.getText().trim().equals("")){
            Valid.textKosong(UraianKonsultasi,"Uraian Konsultasi");
        }else{
            if(Sequel.menyimpantf("konsultasi_medik","?,?,?,?,?,?,?,?","No.Permintaan",8,new String[]{
                NoPermintaan.getText(),NoRw.getText(),Valid.SetTgl(TanggalPermintaan.getSelectedItem()+"")+" "+TanggalPermintaan.getSelectedItem().toString().substring(11,19),
                Permintaan.getSelectedItem().toString(),KdDokter.getText(),KdDokterDikonsuli.getText(),DiagnosaKerja.getText(),UraianKonsultasi.getText()
            })==true){
                R1.setSelected(true);
                tampil();
                emptTeks();
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
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInput.dispose();
        dokter.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
            dokter.dispose();
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal Konsultasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Permintaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter Konsul</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Konsul</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter Dikonsuli</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Dikonsuli</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosa Kerja Konsul</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Uraian Konsultasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal Jawaban</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosa Kerja Dikonsuli</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jawaban Konsultasi</b></td>"+
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

                File f = new File("DataKonsultasiMedik.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA KONSULTASI MEDIK<br><br></font>"+        
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
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Konsul");
        }else if(KdDokterDikonsuli.getText().trim().equals("")||NmDokterDikonsuli.getText().trim().equals("")){
            Valid.textKosong(BtnDokterDIkonsuli,"Dokter Dikonsuli");
        }else if(KdDokter.getText().equals(KdDokterDikonsuli.getText())){
           JOptionPane.showMessageDialog(null,"Maaf, Dokter yang dikonsuli jangan diri sendiri...!");
        }else if(DiagnosaKerja.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKerja,"Diagnosa Kerja");
        }else if(UraianKonsultasi.getText().trim().equals("")){
            Valid.textKosong(UraianKonsultasi,"Uraian Konsultasi");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(!tbObat.getValueAt(tbObat.getSelectedRow(),18).toString().equals("")){
                        JOptionPane.showMessageDialog(null,"Sudah ada jawaban dokter yang dikonsuli, data tidak bisa diubah ..!!");
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
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void DTPCari2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari2ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPCari2ItemStateChanged

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari1ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPCari1ItemStateChanged

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnJawabanDikonsuliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJawabanDikonsuliActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                if(akses.getkode().equals("Admin Utama")){
                    NoPermintaanJawaban.setText(NoPermintaan.getText());
                    WindowInput.setAlwaysOnTop(false);
                    WindowInput.setVisible(true);
                    JawabanDiagnosaKerja.requestFocus();
                }else{
                    if(KdDokterDikonsuli.getText().equals(akses.getkode())){
                        NoPermintaanJawaban.setText(NoPermintaan.getText());
                        WindowInput.setAlwaysOnTop(false);
                        WindowInput.setVisible(true);
                        JawabanDiagnosaKerja.requestFocus();
                    }else{
                        JOptionPane.showMessageDialog(null,"Maaf, hanya bisa dijawab oleh dokter yang dikonsuli...!!!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnJawabanDikonsuliActionPerformed

    private void BtnDokumentasiKonsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokumentasiKonsulActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                if(!tbObat.getValueAt(tbObat.getSelectedRow(),18).toString().equals("")){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());  
                    finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),10).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())); 
                    finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
                    param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),12).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()));
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDokumentasiKonsultasiMedik.jasper","report","::[ Dokumentasi Konsultasi Medik ]::",
                        "select konsultasi_medik.no_permintaan,konsultasi_medik.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,"+
                        "reg_periksa.sttsumur,pasien.no_tlp,penjab.png_jawab,konsultasi_medik.tanggal as tanggalkonsultasi,konsultasi_medik.jenis_permintaan,pasien.tgl_lahir,"+
                        "konsultasi_medik.kd_dokter,dokterkonsul.nm_dokter as dokterkonsul,konsultasi_medik.kd_dokter_dikonsuli,dokterdikonsuli.nm_dokter as dokterdikonsuli,"+
                        "konsultasi_medik.diagnosa_kerja as diagnosakerjakonsul,konsultasi_medik.uraian_konsultasi,jawaban_konsultasi_medik.tanggal as tanggaljawaban,"+
                        "jawaban_konsultasi_medik.diagnosa_kerja as diagnosakerjajawaban,jawaban_konsultasi_medik.uraian_jawaban "+
                        "from konsultasi_medik inner join reg_periksa on konsultasi_medik.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj  "+
                        "inner join dokter as dokterkonsul on konsultasi_medik.kd_dokter=dokterkonsul.kd_dokter "+
                        "inner join dokter as dokterdikonsuli on konsultasi_medik.kd_dokter_dikonsuli=dokterdikonsuli.kd_dokter "+
                        "inner join jawaban_konsultasi_medik on jawaban_konsultasi_medik.no_permintaan=konsultasi_medik.no_permintaan "+
                        "where konsultasi_medik.no_permintaan='"+NoPermintaan.getText()+"' ",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, belum ada jawaban dokter yang dikonsuli...!!!!");
                }   
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnDokumentasiKonsulActionPerformed

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
        Valid.pindah2(evt,TCari,Permintaan);
    }//GEN-LAST:event_TanggalPermintaanKeyPressed

    private void PermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PermintaanKeyPressed
        Valid.pindah(evt,TanggalPermintaan,NoPermintaan);
    }//GEN-LAST:event_PermintaanKeyPressed

    private void UraianKonsultasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UraianKonsultasiKeyPressed
        Valid.pindah2(evt,DiagnosaKerja,BtnSimpan);
    }//GEN-LAST:event_UraianKonsultasiKeyPressed

    private void NoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPermintaanKeyPressed
        Valid.pindah(evt,Permintaan,BtnDokter);
    }//GEN-LAST:event_NoPermintaanKeyPressed

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
        if(NoRw.getText().trim().equals("")||NoRM.getText().trim().equals("")||NmPasien.getText().trim().equals("")){
            Valid.textKosong(TCari,"Pasien");
        }else if(NoPermintaan.getText().trim().equals("")){
            Valid.textKosong(NoPermintaan,"No.Permintaan");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Konsul");
        }else if(KdDokterDikonsuli.getText().trim().equals("")||NmDokterDikonsuli.getText().trim().equals("")){
            Valid.textKosong(BtnDokterDIkonsuli,"Dokter Dikonsuli");
        }else if(DiagnosaKerja.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKerja,"Diagnosa Kerja");
        }else if(UraianKonsultasi.getText().trim().equals("")){
            Valid.textKosong(UraianKonsultasi,"Uraian Konsultasi");
        }else if(JawabanDiagnosaKerja.getText().trim().equals("")){
            Valid.textKosong(JawabanDiagnosaKerja,"Jawaban Diagnosa Kerja");
        }else if(NoPermintaanJawaban.getText().trim().equals("")){
            Valid.textKosong(NoPermintaanJawaban,"No.Permintaan dijawab");
        }else if(JawabanKonsultasi.getText().trim().equals("")){
            Valid.textKosong(JawabanKonsultasi,"Jawaban Konsultasi");
        }else if(KdDokter.getText().equals(KdDokterDikonsuli.getText())){
           JOptionPane.showMessageDialog(null,"Maaf, Dokter yang dikonsuli jangan diri sendiri...!");
        }else{
            if(Sequel.menyimpantf("jawaban_konsultasi_medik","?,?,?,?",4,new String[]{
                    NoPermintaan.getText(),Valid.SetTgl(TanggalJawab.getSelectedItem()+"")+" "+TanggalJawab.getSelectedItem().toString().substring(11,19),JawabanDiagnosaKerja.getText(),JawabanKonsultasi.getText()
                },"no_permintaan=?","tanggal=?,diagnosa_kerja=?,uraian_jawaban=?",4,new String[]{
                    Valid.SetTgl(TanggalJawab.getSelectedItem()+"")+" "+TanggalJawab.getSelectedItem().toString().substring(11,19),JawabanDiagnosaKerja.getText(),JawabanKonsultasi.getText(),NoPermintaan.getText()
                })==true){
                    R2.setSelected(true);
                    tampil();
                    emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan atau mengedit jawaban konsultasi medik...!!!");
                R1.setSelected(true);
                tampil();
                emptTeks();
            }
        }
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
        Valid.pindah2(evt,DiagnosaKerja,BtnSimpanJawaban);
    }//GEN-LAST:event_JawabanKonsultasiKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        i=1;
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,Permintaan,BtnDokterDIkonsuli);
        }
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterDIkonsuliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterDIkonsuliActionPerformed
        i=2;
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterDIkonsuliActionPerformed

    private void BtnDokterDIkonsuliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterDIkonsuliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnDokterDIkonsuliActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnDokter,DiagnosaKerja);
        }
    }//GEN-LAST:event_BtnDokterDIkonsuliKeyPressed

    private void DiagnosaKerjaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKerjaKeyPressed
        Valid.pindah(evt,BtnDokterDIkonsuli,UraianKonsultasi);
    }//GEN-LAST:event_DiagnosaKerjaKeyPressed

    private void JawabanDiagnosaKerjaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JawabanDiagnosaKerjaKeyPressed
        Valid.pindah(evt,TanggalJawab,JawabanKonsultasi);
    }//GEN-LAST:event_JawabanDiagnosaKerjaKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaanKonsultasiMedik dialog = new DlgPermintaanKonsultasiMedik(new javax.swing.JFrame(), true);
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
    private widget.Button BtnDokter;
    private widget.Button BtnDokterDIkonsuli;
    private widget.Button BtnDokumentasiKonsul;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnJawabanDikonsuli;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRiwayatPasien;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanJawaban;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaKerja;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox JawabanDiagnosaKerja;
    private widget.TextArea JawabanKonsultasi;
    private widget.TextBox KdDokter;
    private widget.TextBox KdDokterDikonsuli;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox NmDokter;
    private widget.TextBox NmDokterDikonsuli;
    private widget.TextBox NmPasien;
    private widget.TextBox NoPermintaan;
    private widget.TextBox NoPermintaanJawaban;
    private widget.TextBox NoRM;
    private widget.TextBox NoRw;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Permintaan;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox TCari;
    private widget.Tanggal TanggalJawab;
    private widget.Tanggal TanggalPermintaan;
    private widget.TextArea UraianKonsultasi;
    private javax.swing.JDialog WindowInput;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel10;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel25;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel39;
    private widget.Label jLabel42;
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
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label2;
    private widget.Label label3;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {     
        Valid.tabelKosong(tabMode);
        try{ 
            sql="";
            if(akses.getjml2()>=1){
                sql="(konsultasi_medik.kd_dokter='"+akses.getkode()+"' or konsultasi_medik.kd_dokter_dikonsuli='"+akses.getkode()+"') and ";
            }
            
            if(R1.isSelected()==true){
               ps=koneksi.prepareStatement(
                    "select konsultasi_medik.no_permintaan,konsultasi_medik.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.no_tlp,penjab.png_jawab,konsultasi_medik.tanggal as tanggalkonsultasi,konsultasi_medik.jenis_permintaan,"+
                    "konsultasi_medik.kd_dokter,dokterkonsul.nm_dokter as dokterkonsul,konsultasi_medik.kd_dokter_dikonsuli,dokterdikonsuli.nm_dokter as dokterdikonsuli,"+
                    "konsultasi_medik.diagnosa_kerja as diagnosakerjakonsul,konsultasi_medik.uraian_konsultasi from konsultasi_medik inner join reg_periksa on konsultasi_medik.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj  "+
                    "inner join dokter as dokterkonsul on konsultasi_medik.kd_dokter=dokterkonsul.kd_dokter "+
                    "inner join dokter as dokterdikonsuli on konsultasi_medik.kd_dokter_dikonsuli=dokterdikonsuli.kd_dokter "+
                    "where "+sql+"konsultasi_medik.no_permintaan not in (select jawaban_konsultasi_medik.no_permintaan from jawaban_konsultasi_medik) "+
                    (TCari.getText().equals("")?"":"and (konsultasi_medik.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? "+
                    "or penjab.png_jawab like ? or konsultasi_medik.no_permintaan like ? or konsultasi_medik.jenis_permintaan like ?)")+" order by konsultasi_medik.tanggal");
                try {
                    if(!TCari.getText().equals("")){
                        ps.setString(1,"%"+TCari.getText().trim()+"%");
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{
                            rs.getString("no_permintaan"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                            rs.getString("no_tlp"),rs.getString("png_jawab"),rs.getString("tanggalkonsultasi"),rs.getString("jenis_permintaan"),rs.getString("kd_dokter"),rs.getString("dokterkonsul"),
                            rs.getString("kd_dokter_dikonsuli"),rs.getString("dokterdikonsuli"),rs.getString("diagnosakerjakonsul"),rs.getString("uraian_konsultasi"),"","",""
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
                    "select konsultasi_medik.no_permintaan,konsultasi_medik.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.no_tlp,penjab.png_jawab,konsultasi_medik.tanggal as tanggalkonsultasi,konsultasi_medik.jenis_permintaan,"+
                    "konsultasi_medik.kd_dokter,dokterkonsul.nm_dokter as dokterkonsul,konsultasi_medik.kd_dokter_dikonsuli,dokterdikonsuli.nm_dokter as dokterdikonsuli,"+
                    "konsultasi_medik.diagnosa_kerja as diagnosakerjakonsul,konsultasi_medik.uraian_konsultasi,jawaban_konsultasi_medik.tanggal as tanggaljawaban,"+
                    "jawaban_konsultasi_medik.diagnosa_kerja as diagnosakerjajawaban,jawaban_konsultasi_medik.uraian_jawaban "+
                    "from konsultasi_medik inner join reg_periksa on konsultasi_medik.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj  "+
                    "inner join dokter as dokterkonsul on konsultasi_medik.kd_dokter=dokterkonsul.kd_dokter "+
                    "inner join dokter as dokterdikonsuli on konsultasi_medik.kd_dokter_dikonsuli=dokterdikonsuli.kd_dokter "+
                    "inner join jawaban_konsultasi_medik on jawaban_konsultasi_medik.no_permintaan=konsultasi_medik.no_permintaan "+
                    "where "+sql+"jawaban_konsultasi_medik.tanggal between ? and ? "+
                    (TCari.getText().equals("")?"":"and (konsultasi_medik.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? "+
                    "or penjab.png_jawab like ? or konsultasi_medik.no_permintaan like ? or konsultasi_medik.jenis_permintaan like ?)")+" order by konsultasi_medik.tanggal");
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
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{
                            rs.getString("no_permintaan"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                            rs.getString("no_tlp"),rs.getString("png_jawab"),rs.getString("tanggalkonsultasi"),rs.getString("jenis_permintaan"),rs.getString("kd_dokter"),rs.getString("dokterkonsul"),
                            rs.getString("kd_dokter_dikonsuli"),rs.getString("dokterdikonsuli"),rs.getString("diagnosakerjakonsul"),rs.getString("uraian_konsultasi"),rs.getString("tanggaljawaban"),
                            rs.getString("diagnosakerjajawaban"),rs.getString("uraian_jawaban")
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
        UraianKonsultasi.setText("");
        TanggalPermintaan.setDate(new Date());
        Permintaan.setSelectedIndex(0);
        KdDokterDikonsuli.setText("");
        NmDokterDikonsuli.setText("");
        DiagnosaKerja.setText("");
        UraianKonsultasi.setText("");
        NoPermintaanJawaban.setText("");
        JawabanDiagnosaKerja.setText("");
        JawabanKonsultasi.setText("");
        TanggalJawab.setDate(new Date());
        autoNomor();
        Permintaan.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoPermintaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            NoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
            NoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            NmPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Permintaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            KdDokterDikonsuli.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NmDokterDikonsuli.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            DiagnosaKerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            UraianKonsultasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            JawabanDiagnosaKerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            JawabanKonsultasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Valid.SetTgl2(TanggalPermintaan,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Valid.SetTgl2(TanggalJawab,tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
        }
    }
    
    public void setNoRm(String norwt,String norm,String nama) {
        NoRw.setText(norwt);
        NoRM.setText(norm);
        NmPasien.setText(nama);
        TCari.setText(norwt);
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,293));
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
        BtnSimpan.setEnabled(akses.getkonsultasi_medik());
        BtnHapus.setEnabled(akses.getkonsultasi_medik());
        BtnPrint.setEnabled(akses.getkonsultasi_medik());
        BtnJawabanDikonsuli.setEnabled(akses.getjawaban_konsultasi_medik());
        BtnRiwayatPasien.setEnabled(akses.getresume_pasien());
        BtnEdit.setEnabled(akses.getkonsultasi_medik());   
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
                dispose();
            }
        }
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
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(konsultasi_medik.no_permintaan,4),signed)),0) from konsultasi_medik where left(konsultasi_medik.tanggal,10)='"+Valid.SetTgl(TanggalPermintaan.getSelectedItem()+"")+"' ","KM"+Valid.SetTgl(TanggalPermintaan.getSelectedItem()+"").replaceAll("-",""),4,NoPermintaan);           
    }

    private void ganti() {
        if(Sequel.mengedittf("konsultasi_medik","no_permintaan=?","no_permintaan=?,no_rawat=?,tanggal=?,jenis_permintaan=?,kd_dokter=?,kd_dokter_dikonsuli=?,diagnosa_kerja=?,uraian_konsultasi=?",9,new String[]{
                NoPermintaan.getText(),NoRw.getText(),Valid.SetTgl(TanggalPermintaan.getSelectedItem()+"")+" "+TanggalPermintaan.getSelectedItem().toString().substring(11,19),
                Permintaan.getSelectedItem().toString(),KdDokter.getText(),KdDokterDikonsuli.getText(),DiagnosaKerja.getText(),UraianKonsultasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                tampil();
                emptTeks();
        }
    }
    
}

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
public final class RMChecklistKriteriaMasukICU extends javax.swing.JDialog {
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
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMChecklistKriteriaMasukICU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","Pasca Operasi Dengan Gangguan Nafas Atau Hipotensi","Gagal Nafas","Gagal Jantung Dengan Tanda Bendungan Paru",
            "Gangguan Asam Basa / Elektrolit","Gagal Ginjal Dengan Tanda Bendungan Paru","Syok Karena Perdarahan Anafilaksis","Pasca Operasi Besar","Kejang Berulang","Gangguan Kesadaran",
            "Dehidrasi Berat","Gangguan Jalan Nafas","Arimia Jantung","Asma Akut Berat","Diabetes Yang Memerlukan Terapi Insulin Kontinyu","Penyakit Keganasan Dengan Metastasis",
            "Pasien Geriatrik Dengan Fungsi Hidup Sebelumnya Minimal","Pasien Dengan GCS 3","Pasien Jantung, Penyakit Paru Terminal Disertai Komplikasi Penyakit Akut Berat","Nadi < 40 atau >150 (x/menit)",
            "SBP < 80 mmHg Atau 20 mmHg Di Bawah SBP Pasien","MAP < 60 mmHg","DBP > 120 mmHg","R > 35 x/menit","Na < 110 meq/L Atau > 170 meq/L","Ca > 15 mg/dl","GDS > 800 mg/dl",
            "K < 2 meq/L Atau 7meq/L","PaO2 < 50 mmHg","PH < 7,1 Atau 7,7","Perbedaan Cerebrovaskuler, SAH, Atau Contusion Dengan Gangguan Kesadaran Atau Neorologi",
            "Ruptor Organ Dalam, Kandung Kemih, Hati, Varices Esophagus Atau Uterus Dengan Gangguan Hemodinamik","Pupil Anisokor","Obstruksi Jalan Nafas","Anuria","Kejang Berulang",
            "Tamponade Jantung","Coma","Sianosis","Luka Bakar > 10 % BSA","NIP/Kode Dokter","DPJP/Dokter Jaga/IGD"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 47; i++) {
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
            }else if(i==45){
                column.setPreferredWidth(90);
            }else if(i==46){
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
        MnKriteriaMasukICU = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        JK = new widget.TextBox();
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
        Prioritas1_5 = new widget.ComboBox();
        jLabel58 = new widget.Label();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        Prioritas1_6 = new widget.ComboBox();
        jLabel61 = new widget.Label();
        Prioritas1_2 = new widget.ComboBox();
        jLabel62 = new widget.Label();
        Prioritas1_3 = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        Prioritas1_1 = new widget.ComboBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        Prioritas1_4 = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel67 = new widget.Label();
        Prioritas2_1 = new widget.ComboBox();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        Prioritas2_3 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        Prioritas2_2 = new widget.ComboBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        Prioritas2_4 = new widget.ComboBox();
        jLabel73 = new widget.Label();
        Prioritas2_5 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        Prioritas2_6 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        Prioritas2_8 = new widget.ComboBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        Prioritas2_7 = new widget.ComboBox();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel55 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        Prioritas3_1 = new widget.ComboBox();
        jLabel80 = new widget.Label();
        Prioritas3_2 = new widget.ComboBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Prioritas3_3 = new widget.ComboBox();
        jLabel83 = new widget.Label();
        Prioritas3_4 = new widget.ComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel56 = new widget.Label();
        jLabel84 = new widget.Label();
        TandaVital1 = new widget.ComboBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        TandaVital2 = new widget.ComboBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        TandaVital3 = new widget.ComboBox();
        jLabel89 = new widget.Label();
        TandaVital4 = new widget.ComboBox();
        jLabel90 = new widget.Label();
        TandaVital5 = new widget.ComboBox();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        Laborat1 = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel91 = new widget.Label();
        jLabel92 = new widget.Label();
        jLabel93 = new widget.Label();
        Laborat5 = new widget.ComboBox();
        jLabel94 = new widget.Label();
        Laborat4 = new widget.ComboBox();
        Laborat3 = new widget.ComboBox();
        jLabel95 = new widget.Label();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        Laborat2 = new widget.ComboBox();
        jLabel98 = new widget.Label();
        Laborat6 = new widget.ComboBox();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        Radiologi1 = new widget.ComboBox();
        jLabel102 = new widget.Label();
        jLabel103 = new widget.Label();
        Radiologi2 = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        jLabel106 = new widget.Label();
        Kinis1 = new widget.ComboBox();
        jLabel107 = new widget.Label();
        Kinis2 = new widget.ComboBox();
        jLabel108 = new widget.Label();
        Kinis3 = new widget.ComboBox();
        jLabel109 = new widget.Label();
        Kinis4 = new widget.ComboBox();
        jLabel110 = new widget.Label();
        Kinis5 = new widget.ComboBox();
        jLabel111 = new widget.Label();
        jLabel112 = new widget.Label();
        Kinis6 = new widget.ComboBox();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        Kinis7 = new widget.ComboBox();
        jLabel115 = new widget.Label();
        Kinis8 = new widget.ComboBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKriteriaMasukICU.setBackground(new java.awt.Color(255, 255, 254));
        MnKriteriaMasukICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKriteriaMasukICU.setForeground(new java.awt.Color(50, 50, 50));
        MnKriteriaMasukICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKriteriaMasukICU.setText("Formulir Checklist Kriteria Masuk ICU");
        MnKriteriaMasukICU.setName("MnKriteriaMasukICU"); // NOI18N
        MnKriteriaMasukICU.setPreferredSize(new java.awt.Dimension(260, 26));
        MnKriteriaMasukICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKriteriaMasukICUActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKriteriaMasukICU);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Kriteria Masuk ICU ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-06-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-06-2023" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 723));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-06-2023 15:30:18" }));
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

        Prioritas1_5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas1_5.setSelectedIndex(1);
        Prioritas1_5.setName("Prioritas1_5"); // NOI18N
        Prioritas1_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas1_5KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas1_5);
        Prioritas1_5.setBounds(266, 150, 90, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Gagal Ginjal Dengan Tanda Bendungan Paru");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(40, 150, 230, 23);

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
        jLabel53.setText("I. PRIORITAS 1");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 150, 262, 23);

        jLabel60.setText("Syok Karena Perdarahan Anafilaksis :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(490, 150, 205, 23);

        Prioritas1_6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas1_6.setSelectedIndex(1);
        Prioritas1_6.setName("Prioritas1_6"); // NOI18N
        Prioritas1_6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas1_6KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas1_6);
        Prioritas1_6.setBounds(699, 150, 90, 23);

        jLabel61.setText("Gagal Nafas :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(495, 90, 200, 23);

        Prioritas1_2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas1_2.setSelectedIndex(1);
        Prioritas1_2.setName("Prioritas1_2"); // NOI18N
        Prioritas1_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas1_2KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas1_2);
        Prioritas1_2.setBounds(699, 90, 90, 23);

        jLabel62.setText(":");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 120, 273, 23);

        Prioritas1_3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas1_3.setSelectedIndex(1);
        Prioritas1_3.setName("Prioritas1_3"); // NOI18N
        Prioritas1_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas1_3KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas1_3);
        Prioritas1_3.setBounds(277, 120, 90, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Gagal Jantung Dengan Tanda Bendungan Paru");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(40, 120, 250, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 90, 314, 23);

        Prioritas1_1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas1_1.setSelectedIndex(1);
        Prioritas1_1.setName("Prioritas1_1"); // NOI18N
        Prioritas1_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas1_1KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas1_1);
        Prioritas1_1.setBounds(318, 90, 90, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Pasca Operasi Dengan Gangguan Nafas Atau Hipotensi");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(40, 90, 290, 23);

        jLabel66.setText("Gangguan Asam Basa / Elektrolit :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(495, 120, 200, 23);

        Prioritas1_4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas1_4.setSelectedIndex(1);
        Prioritas1_4.setName("Prioritas1_4"); // NOI18N
        Prioritas1_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas1_4KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas1_4);
        Prioritas1_4.setBounds(699, 120, 90, 23);

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
        jLabel54.setText("II. PRIORITAS 2");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 180, 180, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Pasca Operasi Besar");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(40, 200, 120, 23);

        Prioritas2_1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas2_1.setSelectedIndex(1);
        Prioritas2_1.setName("Prioritas2_1"); // NOI18N
        Prioritas2_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas2_1KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas2_1);
        Prioritas2_1.setBounds(151, 200, 90, 23);

        jLabel68.setText(":");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 200, 147, 23);

        jLabel69.setText("Gangguan Kesadaran :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(555, 200, 140, 23);

        Prioritas2_3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas2_3.setSelectedIndex(1);
        Prioritas2_3.setName("Prioritas2_3"); // NOI18N
        Prioritas2_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas2_3KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas2_3);
        Prioritas2_3.setBounds(699, 200, 90, 23);

        jLabel70.setText("Kejang Berulang :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(280, 200, 140, 23);

        Prioritas2_2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas2_2.setSelectedIndex(1);
        Prioritas2_2.setName("Prioritas2_2"); // NOI18N
        Prioritas2_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas2_2KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas2_2);
        Prioritas2_2.setBounds(424, 200, 90, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Dehidrasi Berat");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(40, 230, 100, 23);

        jLabel72.setText(":");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 230, 122, 23);

        Prioritas2_4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas2_4.setSelectedIndex(1);
        Prioritas2_4.setName("Prioritas2_4"); // NOI18N
        Prioritas2_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas2_4KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas2_4);
        Prioritas2_4.setBounds(126, 230, 90, 23);

        jLabel73.setText("Gangguan Jalan Nafas :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(280, 230, 140, 23);

        Prioritas2_5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas2_5.setSelectedIndex(1);
        Prioritas2_5.setName("Prioritas2_5"); // NOI18N
        Prioritas2_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas2_5KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas2_5);
        Prioritas2_5.setBounds(424, 230, 90, 23);

        jLabel74.setText("Arimia Jantung :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(555, 230, 140, 23);

        Prioritas2_6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas2_6.setSelectedIndex(1);
        Prioritas2_6.setName("Prioritas2_6"); // NOI18N
        Prioritas2_6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas2_6KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas2_6);
        Prioritas2_6.setBounds(699, 230, 90, 23);

        jLabel75.setText("Diabetes Yang Memerlukan Terapi Insulin Kontinyu :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(255, 260, 310, 23);

        Prioritas2_8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas2_8.setSelectedIndex(1);
        Prioritas2_8.setName("Prioritas2_8"); // NOI18N
        Prioritas2_8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas2_8KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas2_8);
        Prioritas2_8.setBounds(569, 260, 90, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Asma Akut Berat");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(40, 260, 90, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 260, 128, 23);

        Prioritas2_7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas2_7.setSelectedIndex(1);
        Prioritas2_7.setName("Prioritas2_7"); // NOI18N
        Prioritas2_7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas2_7KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas2_7);
        Prioritas2_7.setBounds(132, 260, 90, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 290, 810, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 290, 810, 1);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("III. PRIORITAS 3");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 290, 180, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Penyakit Keganasan Dengan Metastasis");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(40, 310, 210, 23);

        jLabel79.setText(":");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(0, 310, 240, 23);

        Prioritas3_1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas3_1.setSelectedIndex(1);
        Prioritas3_1.setName("Prioritas3_1"); // NOI18N
        Prioritas3_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas3_1KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas3_1);
        Prioritas3_1.setBounds(244, 310, 90, 23);

        jLabel80.setText("Pasien Geriatrik Dengan Fungsi Hidup Sebelumnya Minimal :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(375, 310, 320, 23);

        Prioritas3_2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas3_2.setSelectedIndex(1);
        Prioritas3_2.setName("Prioritas3_2"); // NOI18N
        Prioritas3_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas3_2KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas3_2);
        Prioritas3_2.setBounds(699, 310, 90, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Pasien Dengan GCS 3");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(40, 340, 120, 23);

        jLabel82.setText(":");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(0, 340, 151, 23);

        Prioritas3_3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas3_3.setSelectedIndex(1);
        Prioritas3_3.setName("Prioritas3_3"); // NOI18N
        Prioritas3_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas3_3KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas3_3);
        Prioritas3_3.setBounds(155, 340, 90, 23);

        jLabel83.setText("Pasien Jantung, Penyakit Paru Terminal Disertai Komplikasi Penyakit Akut Berat :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(255, 340, 440, 23);

        Prioritas3_4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prioritas3_4.setSelectedIndex(1);
        Prioritas3_4.setName("Prioritas3_4"); // NOI18N
        Prioritas3_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prioritas3_4KeyPressed(evt);
            }
        });
        FormInput.add(Prioritas3_4);
        Prioritas3_4.setBounds(699, 340, 90, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 370, 810, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 370, 810, 1);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. KRITERIA FISIOLOGIS TANDA-TANDA VITAL");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 370, 350, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Nadi < 40 atau >150 (x/menit)");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(40, 390, 160, 23);

        TandaVital1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TandaVital1.setSelectedIndex(1);
        TandaVital1.setName("TandaVital1"); // NOI18N
        TandaVital1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TandaVital1KeyPressed(evt);
            }
        });
        FormInput.add(TandaVital1);
        TandaVital1.setBounds(201, 390, 90, 23);

        jLabel85.setText(":");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 390, 197, 23);

        jLabel86.setText("SBP < 80 mmHg Atau 20 mmHg Di Bawah SBP Pasien :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(375, 390, 320, 23);

        TandaVital2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TandaVital2.setSelectedIndex(1);
        TandaVital2.setName("TandaVital2"); // NOI18N
        TandaVital2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TandaVital2KeyPressed(evt);
            }
        });
        FormInput.add(TandaVital2);
        TandaVital2.setBounds(699, 390, 90, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("MAP < 60 mmHg");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(40, 420, 100, 23);

        jLabel88.setText(":");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(0, 420, 128, 23);

        TandaVital3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TandaVital3.setSelectedIndex(1);
        TandaVital3.setName("TandaVital3"); // NOI18N
        TandaVital3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TandaVital3KeyPressed(evt);
            }
        });
        FormInput.add(TandaVital3);
        TandaVital3.setBounds(132, 420, 90, 23);

        jLabel89.setText("DBP > 120 mmHg :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(280, 420, 140, 23);

        TandaVital4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TandaVital4.setSelectedIndex(1);
        TandaVital4.setName("TandaVital4"); // NOI18N
        TandaVital4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TandaVital4KeyPressed(evt);
            }
        });
        FormInput.add(TandaVital4);
        TandaVital4.setBounds(424, 420, 90, 23);

        jLabel90.setText("R > 35 x/menit :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(555, 420, 140, 23);

        TandaVital5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TandaVital5.setSelectedIndex(1);
        TandaVital5.setName("TandaVital5"); // NOI18N
        TandaVital5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TandaVital5KeyPressed(evt);
            }
        });
        FormInput.add(TandaVital5);
        TandaVital5.setBounds(699, 420, 90, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 450, 810, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 450, 810, 1);

        Laborat1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Laborat1.setSelectedIndex(1);
        Laborat1.setName("Laborat1"); // NOI18N
        Laborat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Laborat1KeyPressed(evt);
            }
        });
        FormInput.add(Laborat1);
        Laborat1.setBounds(221, 470, 90, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("V. KRITERIA FISIOLOGIS LABORATORIUM");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(10, 450, 350, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Na < 110 meq/L Atau > 170 meq/L");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(40, 470, 190, 23);

        jLabel92.setText(":");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(0, 470, 217, 23);

        jLabel93.setText("PaO2 < 50 mmHg :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(325, 500, 120, 23);

        Laborat5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Laborat5.setSelectedIndex(1);
        Laborat5.setName("Laborat5"); // NOI18N
        Laborat5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Laborat5KeyPressed(evt);
            }
        });
        FormInput.add(Laborat5);
        Laborat5.setBounds(449, 500, 90, 23);

        jLabel94.setText("GDS > 800 mg/dl :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(555, 470, 140, 23);

        Laborat4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Laborat4.setSelectedIndex(1);
        Laborat4.setName("Laborat4"); // NOI18N
        Laborat4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Laborat4KeyPressed(evt);
            }
        });
        FormInput.add(Laborat4);
        Laborat4.setBounds(176, 500, 90, 23);

        Laborat3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Laborat3.setSelectedIndex(1);
        Laborat3.setName("Laborat3"); // NOI18N
        Laborat3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Laborat3KeyPressed(evt);
            }
        });
        FormInput.add(Laborat3);
        Laborat3.setBounds(699, 470, 90, 23);

        jLabel95.setText(":");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(0, 500, 172, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("K < 2 meq/L Atau 7meq/L");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(40, 500, 150, 23);

        jLabel97.setText("Ca > 15 mg/dl :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(325, 470, 120, 23);

        Laborat2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Laborat2.setSelectedIndex(1);
        Laborat2.setName("Laborat2"); // NOI18N
        Laborat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Laborat2KeyPressed(evt);
            }
        });
        FormInput.add(Laborat2);
        Laborat2.setBounds(449, 470, 90, 23);

        jLabel98.setText("PH < 7,1 Atau 7,7 :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(555, 500, 140, 23);

        Laborat6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Laborat6.setSelectedIndex(1);
        Laborat6.setName("Laborat6"); // NOI18N
        Laborat6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Laborat6KeyPressed(evt);
            }
        });
        FormInput.add(Laborat6);
        Laborat6.setBounds(699, 500, 90, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 530, 810, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 530, 810, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("VI. KRITERIA FISIOLOGIS RADIOLOGI");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 530, 350, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("Perbedaan Cerebrovaskuler, SAH, Atau Contusion Dengan Gangguan Kesadaran Atau Neorologi");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(40, 550, 490, 23);

        jLabel101.setText(":");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(0, 550, 510, 23);

        Radiologi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Radiologi1.setSelectedIndex(1);
        Radiologi1.setName("Radiologi1"); // NOI18N
        Radiologi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Radiologi1KeyPressed(evt);
            }
        });
        FormInput.add(Radiologi1);
        Radiologi1.setBounds(514, 550, 90, 23);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("Ruptor Organ Dalam, Kandung Kemih, Hati, Varices Esophagus Atau Uterus Dengan Gangguan Hemodinamik");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(40, 580, 550, 23);

        jLabel103.setText(":");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(0, 580, 574, 23);

        Radiologi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Radiologi2.setSelectedIndex(1);
        Radiologi2.setName("Radiologi2"); // NOI18N
        Radiologi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Radiologi2KeyPressed(evt);
            }
        });
        FormInput.add(Radiologi2);
        Radiologi2.setBounds(578, 580, 90, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 610, 810, 1);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 610, 810, 1);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("VII. KRITERIA FISILOGIS KLINIS");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(10, 610, 350, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("Pupil Anisokor");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(40, 630, 90, 23);

        jLabel106.setText(":");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(0, 630, 117, 23);

        Kinis1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kinis1.setSelectedIndex(1);
        Kinis1.setName("Kinis1"); // NOI18N
        Kinis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kinis1KeyPressed(evt);
            }
        });
        FormInput.add(Kinis1);
        Kinis1.setBounds(121, 630, 90, 23);

        jLabel107.setText("Obstruksi Jalan Nafas :");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(305, 630, 140, 23);

        Kinis2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kinis2.setSelectedIndex(1);
        Kinis2.setName("Kinis2"); // NOI18N
        Kinis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kinis2KeyPressed(evt);
            }
        });
        FormInput.add(Kinis2);
        Kinis2.setBounds(449, 630, 90, 23);

        jLabel108.setText("Anuria :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(635, 630, 60, 23);

        Kinis3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kinis3.setSelectedIndex(1);
        Kinis3.setName("Kinis3"); // NOI18N
        Kinis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kinis3KeyPressed(evt);
            }
        });
        FormInput.add(Kinis3);
        Kinis3.setBounds(699, 630, 90, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("Kejang Berulang");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(40, 660, 90, 23);

        Kinis4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kinis4.setSelectedIndex(1);
        Kinis4.setName("Kinis4"); // NOI18N
        Kinis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kinis4KeyPressed(evt);
            }
        });
        FormInput.add(Kinis4);
        Kinis4.setBounds(130, 660, 90, 23);

        jLabel110.setText("Tamponade Jantung :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(305, 660, 140, 23);

        Kinis5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kinis5.setSelectedIndex(1);
        Kinis5.setName("Kinis5"); // NOI18N
        Kinis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kinis5KeyPressed(evt);
            }
        });
        FormInput.add(Kinis5);
        Kinis5.setBounds(449, 660, 90, 23);

        jLabel111.setText(":");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(0, 660, 126, 23);

        jLabel112.setText("Coma :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(635, 660, 60, 23);

        Kinis6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kinis6.setSelectedIndex(1);
        Kinis6.setName("Kinis6"); // NOI18N
        Kinis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kinis6KeyPressed(evt);
            }
        });
        FormInput.add(Kinis6);
        Kinis6.setBounds(699, 660, 90, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Sianosis");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(40, 690, 50, 23);

        jLabel114.setText(":");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(0, 690, 88, 23);

        Kinis7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kinis7.setSelectedIndex(1);
        Kinis7.setName("Kinis7"); // NOI18N
        Kinis7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kinis7KeyPressed(evt);
            }
        });
        FormInput.add(Kinis7);
        Kinis7.setBounds(92, 690, 90, 23);

        jLabel115.setText("Luka Bakar > 10 % BSA :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(305, 690, 140, 23);

        Kinis8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kinis8.setSelectedIndex(1);
        Kinis8.setName("Kinis8"); // NOI18N
        Kinis8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kinis8KeyPressed(evt);
            }
        });
        FormInput.add(Kinis8);
        Kinis8.setBounds(449, 690, 90, 23);

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
            if(Sequel.menyimpantf("checklist_kriteria_masuk_icu","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",42,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Prioritas1_1.getSelectedItem().toString(),
                Prioritas1_2.getSelectedItem().toString(),Prioritas1_3.getSelectedItem().toString(),Prioritas1_4.getSelectedItem().toString(),Prioritas1_5.getSelectedItem().toString(),
                Prioritas1_6.getSelectedItem().toString(),Prioritas2_1.getSelectedItem().toString(),Prioritas2_2.getSelectedItem().toString(),Prioritas2_3.getSelectedItem().toString(),
                Prioritas2_4.getSelectedItem().toString(),Prioritas2_5.getSelectedItem().toString(),Prioritas2_6.getSelectedItem().toString(),Prioritas2_7.getSelectedItem().toString(),
                Prioritas2_8.getSelectedItem().toString(),Prioritas3_1.getSelectedItem().toString(),Prioritas3_2.getSelectedItem().toString(),Prioritas3_3.getSelectedItem().toString(),
                Prioritas3_4.getSelectedItem().toString(),TandaVital1.getSelectedItem().toString(),TandaVital2.getSelectedItem().toString(),TandaVital3.getSelectedItem().toString(),
                TandaVital4.getSelectedItem().toString(),TandaVital5.getSelectedItem().toString(),Laborat1.getSelectedItem().toString(),Laborat2.getSelectedItem().toString(),
                Laborat3.getSelectedItem().toString(),Laborat4.getSelectedItem().toString(),Laborat5.getSelectedItem().toString(),Laborat6.getSelectedItem().toString(),
                Radiologi1.getSelectedItem().toString(),Radiologi2.getSelectedItem().toString(),Kinis1.getSelectedItem().toString(),Kinis2.getSelectedItem().toString(),
                Kinis3.getSelectedItem().toString(),Kinis4.getSelectedItem().toString(),Kinis5.getSelectedItem().toString(),Kinis6.getSelectedItem().toString(),Kinis7.getSelectedItem().toString(),
                Kinis8.getSelectedItem().toString(),KodePetugas.getText()
            })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    Prioritas1_1.getSelectedItem().toString(),Prioritas1_2.getSelectedItem().toString(),Prioritas1_3.getSelectedItem().toString(),Prioritas1_4.getSelectedItem().toString(),Prioritas1_5.getSelectedItem().toString(),
                    Prioritas1_6.getSelectedItem().toString(),Prioritas2_1.getSelectedItem().toString(),Prioritas2_2.getSelectedItem().toString(),Prioritas2_3.getSelectedItem().toString(),Prioritas2_4.getSelectedItem().toString(),
                    Prioritas2_5.getSelectedItem().toString(),Prioritas2_6.getSelectedItem().toString(),Prioritas2_7.getSelectedItem().toString(),Prioritas2_8.getSelectedItem().toString(),Prioritas3_1.getSelectedItem().toString(),
                    Prioritas3_2.getSelectedItem().toString(),Prioritas3_3.getSelectedItem().toString(),Prioritas3_4.getSelectedItem().toString(),TandaVital1.getSelectedItem().toString(),TandaVital2.getSelectedItem().toString(),
                    TandaVital3.getSelectedItem().toString(),TandaVital4.getSelectedItem().toString(),TandaVital5.getSelectedItem().toString(),Laborat1.getSelectedItem().toString(),Laborat2.getSelectedItem().toString(),
                    Laborat3.getSelectedItem().toString(),Laborat4.getSelectedItem().toString(),Laborat5.getSelectedItem().toString(),Laborat6.getSelectedItem().toString(),Radiologi1.getSelectedItem().toString(),
                    Radiologi2.getSelectedItem().toString(),Kinis1.getSelectedItem().toString(),Kinis2.getSelectedItem().toString(),Kinis3.getSelectedItem().toString(),Kinis4.getSelectedItem().toString(),Kinis5.getSelectedItem().toString(),
                    Kinis6.getSelectedItem().toString(),Kinis7.getSelectedItem().toString(),Kinis8.getSelectedItem().toString(),KodePetugas.getText(),NamaPetugas.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            } 
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Infeksi,BtnBatal);
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
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString())){
                    hapus();
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
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString())){
                        ganti();
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Operasi Dengan Gangguan Nafas Atau Hipotensi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Nafas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Jantung Dengan Tanda Bendungan Paru</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Asam Basa / Elektrolit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Ginjal Dengan Tanda Bendungan Paru</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Syok Karena Perdarahan Anafilaksis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Operasi Besar</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kejang Berulang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dehidrasi Berat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Jalan Nafas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Arimia Jantung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asma Akut Berat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diabetes Yang Memerlukan Terapi Insulin Kontinyu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakit Keganasan Dengan Metastasis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasien Geriatrik Dengan Fungsi Hidup Sebelumnya Minimal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasien Dengan GCS 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasien Jantung, Penyakit Paru Terminal Disertai Komplikasi Penyakit Akut Berat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi < 40 atau >150 (x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SBP < 80 mmHg Atau 20 mmHg Di Bawah SBP Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>MAP < 60 mmHg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DBP > 120 mmHg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R > 35 x/menit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Na < 110 meq/L Atau > 170 meq/L</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ca > 15 mg/dl</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>GDS > 800 mg/dl</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>K < 2 meq/L Atau 7meq/L</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PaO2 < 50 mmHg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PH < 7,1 Atau 7,7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perbedaan Cerebrovaskuler, SAH, Atau Contusion Dengan Gangguan Kesadaran Atau Neorologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ruptor Organ Dalam, Kandung Kemih, Hati, Varices Esophagus Atau Uterus Dengan Gangguan Hemodinamik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pupil Anisokor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obstruksi Jalan Nafas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anuria</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kejang Berulang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tamponade Jantung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Coma</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sianosis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Luka Bakar > 10 % BSA</b></td>"+
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
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='5100px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataChecklistKriteriaMasukICU.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='5100px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECK LIST KRITERIA MASUK ICU<br><br></font>"+        
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

    private void MnKriteriaMasukICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKriteriaMasukICUActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),46).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),45).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirChecklistKriteriaMasukICU.jasper","report","::[ Formulir Check List Kriteria Masuk ICU ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_icu.tanggal,"+
                    "checklist_kriteria_masuk_icu.prioritas1_1,checklist_kriteria_masuk_icu.prioritas1_2,checklist_kriteria_masuk_icu.prioritas1_3,"+
                    "checklist_kriteria_masuk_icu.prioritas1_4,checklist_kriteria_masuk_icu.prioritas1_5,checklist_kriteria_masuk_icu.prioritas1_6,"+
                    "checklist_kriteria_masuk_icu.prioritas2_1,checklist_kriteria_masuk_icu.prioritas2_2,checklist_kriteria_masuk_icu.prioritas2_3,"+
                    "checklist_kriteria_masuk_icu.prioritas2_4,checklist_kriteria_masuk_icu.prioritas2_5,checklist_kriteria_masuk_icu.prioritas2_6,"+
                    "checklist_kriteria_masuk_icu.prioritas2_7,checklist_kriteria_masuk_icu.prioritas2_8,checklist_kriteria_masuk_icu.prioritas3_1,"+
                    "checklist_kriteria_masuk_icu.prioritas3_2,checklist_kriteria_masuk_icu.prioritas3_3,checklist_kriteria_masuk_icu.prioritas3_4,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_1,checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_2,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_3,checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_4,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_5,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_1,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_3,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_4,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_5,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_6,checklist_kriteria_masuk_icu.kriteria_fisiologis_radiologi_1,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_radiologi_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_1,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_3,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_4,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_5,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_6,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_7,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_8,checklist_kriteria_masuk_icu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_icu inner join reg_periksa on checklist_kriteria_masuk_icu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_icu.nik "+
                    "where checklist_kriteria_masuk_icu.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_kriteria_masuk_icu.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnKriteriaMasukICUActionPerformed

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
       Valid.pindah(evt,Tanggal,Prioritas1_1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void Prioritas1_5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas1_5KeyPressed
       Valid.pindah(evt,Prioritas1_4,Prioritas1_6);
    }//GEN-LAST:event_Prioritas1_5KeyPressed

    private void Prioritas1_6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas1_6KeyPressed
        //Valid.pindah(evt,Kardiologi5,Pernapasan1);
    }//GEN-LAST:event_Prioritas1_6KeyPressed

    private void Prioritas1_2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas1_2KeyPressed
        Valid.pindah(evt,Prioritas1_1,Prioritas1_3);
    }//GEN-LAST:event_Prioritas1_2KeyPressed

    private void Prioritas1_3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas1_3KeyPressed
        Valid.pindah(evt,Prioritas1_2,Prioritas1_4);
    }//GEN-LAST:event_Prioritas1_3KeyPressed

    private void Prioritas1_1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas1_1KeyPressed
        Valid.pindah(evt,btnPetugas,Prioritas1_2);
    }//GEN-LAST:event_Prioritas1_1KeyPressed

    private void Prioritas1_4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas1_4KeyPressed
        Valid.pindah(evt,Prioritas1_3,Prioritas1_5);
    }//GEN-LAST:event_Prioritas1_4KeyPressed

    private void Prioritas2_1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas2_1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas2_1KeyPressed

    private void Prioritas2_3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas2_3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas2_3KeyPressed

    private void Prioritas2_2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas2_2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas2_2KeyPressed

    private void Prioritas2_4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas2_4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas2_4KeyPressed

    private void Prioritas2_5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas2_5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas2_5KeyPressed

    private void Prioritas2_6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas2_6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas2_6KeyPressed

    private void Prioritas2_8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas2_8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas2_8KeyPressed

    private void Prioritas2_7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas2_7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas2_7KeyPressed

    private void Prioritas3_1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas3_1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas3_1KeyPressed

    private void Prioritas3_2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas3_2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas3_2KeyPressed

    private void Prioritas3_3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas3_3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas3_3KeyPressed

    private void Prioritas3_4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prioritas3_4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prioritas3_4KeyPressed

    private void TandaVital1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TandaVital1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TandaVital1KeyPressed

    private void TandaVital2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TandaVital2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TandaVital2KeyPressed

    private void TandaVital3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TandaVital3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TandaVital3KeyPressed

    private void TandaVital4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TandaVital4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TandaVital4KeyPressed

    private void TandaVital5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TandaVital5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TandaVital5KeyPressed

    private void Laborat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Laborat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Laborat1KeyPressed

    private void Laborat5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Laborat5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Laborat5KeyPressed

    private void Laborat3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Laborat3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Laborat3KeyPressed

    private void Laborat4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Laborat4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Laborat4KeyPressed

    private void Laborat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Laborat2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Laborat2KeyPressed

    private void Laborat6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Laborat6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Laborat6KeyPressed

    private void Radiologi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Radiologi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Radiologi1KeyPressed

    private void Radiologi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Radiologi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Radiologi2KeyPressed

    private void Kinis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kinis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kinis1KeyPressed

    private void Kinis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kinis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kinis2KeyPressed

    private void Kinis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kinis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kinis3KeyPressed

    private void Kinis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kinis4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kinis4KeyPressed

    private void Kinis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kinis5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kinis5KeyPressed

    private void Kinis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kinis6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kinis6KeyPressed

    private void Kinis7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kinis7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kinis7KeyPressed

    private void Kinis8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kinis8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kinis8KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistKriteriaMasukICU dialog = new RMChecklistKriteriaMasukICU(new javax.swing.JFrame(), true);
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
    private widget.TextBox JK;
    private widget.ComboBox Kinis1;
    private widget.ComboBox Kinis2;
    private widget.ComboBox Kinis3;
    private widget.ComboBox Kinis4;
    private widget.ComboBox Kinis5;
    private widget.ComboBox Kinis6;
    private widget.ComboBox Kinis7;
    private widget.ComboBox Kinis8;
    private widget.TextBox KodePetugas;
    private widget.Label LCount;
    private widget.ComboBox Laborat1;
    private widget.ComboBox Laborat2;
    private widget.ComboBox Laborat3;
    private widget.ComboBox Laborat4;
    private widget.ComboBox Laborat5;
    private widget.ComboBox Laborat6;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnKriteriaMasukICU;
    private widget.TextBox NamaPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Prioritas1_1;
    private widget.ComboBox Prioritas1_2;
    private widget.ComboBox Prioritas1_3;
    private widget.ComboBox Prioritas1_4;
    private widget.ComboBox Prioritas1_5;
    private widget.ComboBox Prioritas1_6;
    private widget.ComboBox Prioritas2_1;
    private widget.ComboBox Prioritas2_2;
    private widget.ComboBox Prioritas2_3;
    private widget.ComboBox Prioritas2_4;
    private widget.ComboBox Prioritas2_5;
    private widget.ComboBox Prioritas2_6;
    private widget.ComboBox Prioritas2_7;
    private widget.ComboBox Prioritas2_8;
    private widget.ComboBox Prioritas3_1;
    private widget.ComboBox Prioritas3_2;
    private widget.ComboBox Prioritas3_3;
    private widget.ComboBox Prioritas3_4;
    private widget.ComboBox Radiologi1;
    private widget.ComboBox Radiologi2;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.ComboBox TandaVital1;
    private widget.ComboBox TandaVital2;
    private widget.ComboBox TandaVital3;
    private widget.ComboBox TandaVital4;
    private widget.ComboBox TandaVital5;
    private widget.Tanggal Tanggal;
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
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_icu.tanggal,"+
                    "checklist_kriteria_masuk_icu.prioritas1_1,checklist_kriteria_masuk_icu.prioritas1_2,checklist_kriteria_masuk_icu.prioritas1_3,"+
                    "checklist_kriteria_masuk_icu.prioritas1_4,checklist_kriteria_masuk_icu.prioritas1_5,checklist_kriteria_masuk_icu.prioritas1_6,"+
                    "checklist_kriteria_masuk_icu.prioritas2_1,checklist_kriteria_masuk_icu.prioritas2_2,checklist_kriteria_masuk_icu.prioritas2_3,"+
                    "checklist_kriteria_masuk_icu.prioritas2_4,checklist_kriteria_masuk_icu.prioritas2_5,checklist_kriteria_masuk_icu.prioritas2_6,"+
                    "checklist_kriteria_masuk_icu.prioritas2_7,checklist_kriteria_masuk_icu.prioritas2_8,checklist_kriteria_masuk_icu.prioritas3_1,"+
                    "checklist_kriteria_masuk_icu.prioritas3_2,checklist_kriteria_masuk_icu.prioritas3_3,checklist_kriteria_masuk_icu.prioritas3_4,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_1,checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_2,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_3,checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_4,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_5,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_1,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_3,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_4,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_5,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_6,checklist_kriteria_masuk_icu.kriteria_fisiologis_radiologi_1,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_radiologi_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_1,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_3,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_4,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_5,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_6,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_7,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_8,checklist_kriteria_masuk_icu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_icu inner join reg_periksa on checklist_kriteria_masuk_icu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_icu.nik "+
                    "where checklist_kriteria_masuk_icu.tanggal between ? and ? order by checklist_kriteria_masuk_icu.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_icu.tanggal,"+
                    "checklist_kriteria_masuk_icu.prioritas1_1,checklist_kriteria_masuk_icu.prioritas1_2,checklist_kriteria_masuk_icu.prioritas1_3,"+
                    "checklist_kriteria_masuk_icu.prioritas1_4,checklist_kriteria_masuk_icu.prioritas1_5,checklist_kriteria_masuk_icu.prioritas1_6,"+
                    "checklist_kriteria_masuk_icu.prioritas2_1,checklist_kriteria_masuk_icu.prioritas2_2,checklist_kriteria_masuk_icu.prioritas2_3,"+
                    "checklist_kriteria_masuk_icu.prioritas2_4,checklist_kriteria_masuk_icu.prioritas2_5,checklist_kriteria_masuk_icu.prioritas2_6,"+
                    "checklist_kriteria_masuk_icu.prioritas2_7,checklist_kriteria_masuk_icu.prioritas2_8,checklist_kriteria_masuk_icu.prioritas3_1,"+
                    "checklist_kriteria_masuk_icu.prioritas3_2,checklist_kriteria_masuk_icu.prioritas3_3,checklist_kriteria_masuk_icu.prioritas3_4,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_1,checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_2,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_3,checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_4,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_tanda_vital_5,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_1,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_3,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_4,checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_5,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_laborat_6,checklist_kriteria_masuk_icu.kriteria_fisiologis_radiologi_1,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_radiologi_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_1,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_2,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_3,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_4,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_5,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_6,checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_7,"+
                    "checklist_kriteria_masuk_icu.kriteria_fisiologis_klinis_8,checklist_kriteria_masuk_icu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_icu inner join reg_periksa on checklist_kriteria_masuk_icu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_icu.nik "+
                    "where checklist_kriteria_masuk_icu.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or pegawai.nama like ? or checklist_kriteria_masuk_icu.nik like ?) order by checklist_kriteria_masuk_icu.tanggal ");
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
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("prioritas1_1"),rs.getString("prioritas1_2"),rs.getString("prioritas1_3"),rs.getString("prioritas1_4"),
                        rs.getString("prioritas1_5"),rs.getString("prioritas1_6"),rs.getString("prioritas2_1"),rs.getString("prioritas2_2"),rs.getString("prioritas2_3"),
                        rs.getString("prioritas2_4"),rs.getString("prioritas2_5"),rs.getString("prioritas2_6"),rs.getString("prioritas2_7"),rs.getString("prioritas2_8"),
                        rs.getString("prioritas3_1"),rs.getString("prioritas3_2"),rs.getString("prioritas3_3"),rs.getString("prioritas3_4"),rs.getString("kriteria_fisiologis_tanda_vital_1"),
                        rs.getString("kriteria_fisiologis_tanda_vital_2"),rs.getString("kriteria_fisiologis_tanda_vital_3"),rs.getString("kriteria_fisiologis_tanda_vital_4"),
                        rs.getString("kriteria_fisiologis_tanda_vital_5"),rs.getString("kriteria_fisiologis_laborat_1"),rs.getString("kriteria_fisiologis_laborat_2"),
                        rs.getString("kriteria_fisiologis_laborat_3"),rs.getString("kriteria_fisiologis_laborat_4"),rs.getString("kriteria_fisiologis_laborat_5"),
                        rs.getString("kriteria_fisiologis_laborat_6"),rs.getString("kriteria_fisiologis_radiologi_1"),rs.getString("kriteria_fisiologis_radiologi_2"),
                        rs.getString("kriteria_fisiologis_klinis_1"),rs.getString("kriteria_fisiologis_klinis_2"),rs.getString("kriteria_fisiologis_klinis_3"),
                        rs.getString("kriteria_fisiologis_klinis_4"),rs.getString("kriteria_fisiologis_klinis_5"),rs.getString("kriteria_fisiologis_klinis_6"),
                        rs.getString("kriteria_fisiologis_klinis_7"),rs.getString("kriteria_fisiologis_klinis_8"),rs.getString("nik"),rs.getString("nama")
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
        Prioritas1_1.setSelectedIndex(1);
        Prioritas1_2.setSelectedIndex(1);
        Prioritas1_3.setSelectedIndex(1);
        Prioritas1_4.setSelectedIndex(1);
        Prioritas1_5.setSelectedIndex(1);
        Prioritas1_6.setSelectedIndex(1);
        Prioritas2_1.setSelectedIndex(1);
        Prioritas2_2.setSelectedIndex(1);
        Prioritas2_3.setSelectedIndex(1);
        Prioritas2_4.setSelectedIndex(1);
        Prioritas2_5.setSelectedIndex(1);
        Prioritas2_6.setSelectedIndex(1);
        Prioritas2_7.setSelectedIndex(1);
        Prioritas2_8.setSelectedIndex(1);
        Prioritas3_1.setSelectedIndex(1);
        Prioritas3_2.setSelectedIndex(1);
        Prioritas3_3.setSelectedIndex(1);
        Prioritas3_4.setSelectedIndex(1);
        TandaVital1.setSelectedIndex(1);
        TandaVital2.setSelectedIndex(1);
        TandaVital3.setSelectedIndex(1);
        TandaVital4.setSelectedIndex(1);
        TandaVital5.setSelectedIndex(1);
        Laborat1.setSelectedIndex(1);
        Laborat2.setSelectedIndex(1);
        Laborat3.setSelectedIndex(1);
        Laborat4.setSelectedIndex(1);
        Laborat5.setSelectedIndex(1);
        Laborat6.setSelectedIndex(1);
        Radiologi1.setSelectedIndex(1);
        Radiologi2.setSelectedIndex(1);
        Kinis1.setSelectedIndex(1);
        Kinis2.setSelectedIndex(1);
        Kinis3.setSelectedIndex(1);
        Kinis4.setSelectedIndex(1);
        Kinis5.setSelectedIndex(1);
        Kinis6.setSelectedIndex(1);
        Kinis7.setSelectedIndex(1);
        Kinis8.setSelectedIndex(1);
        Tanggal.setDate(new Date());
        Prioritas1_1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Prioritas1_1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Prioritas1_2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Prioritas1_3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Prioritas1_4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Prioritas1_5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Prioritas1_6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Prioritas2_1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Prioritas2_2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Prioritas2_3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Prioritas2_4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Prioritas2_5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Prioritas2_6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Prioritas2_7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Prioritas2_8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Prioritas3_1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Prioritas3_2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Prioritas3_3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Prioritas3_4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            TandaVital1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            TandaVital2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            TandaVital3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            TandaVital4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            TandaVital5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Laborat1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Laborat2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Laborat3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Laborat4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Laborat5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Laborat6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Radiologi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Radiologi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Kinis1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Kinis2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Kinis3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Kinis4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Kinis5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Kinis6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Kinis7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Kinis8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
        BtnSimpan.setEnabled(akses.getchecklist_kriteria_masuk_icu());
        BtnHapus.setEnabled(akses.getchecklist_kriteria_masuk_icu());
        BtnEdit.setEnabled(akses.getchecklist_kriteria_masuk_icu());
        BtnPrint.setEnabled(akses.getchecklist_kriteria_masuk_icu()); 
        if(akses.getjml2()>=1){
            btnPetugas.setEnabled(false);
            KodePetugas.setText(akses.getkode());
            NamaPetugas.setText(pegawai.tampil3(akses.getkode()));
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("checklist_kriteria_masuk_icu","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,prioritas1_1=?,prioritas1_2=?,prioritas1_3=?,prioritas1_4=?,prioritas1_5=?,prioritas1_6=?,"+
                "prioritas2_1=?,prioritas2_2=?,prioritas2_3=?,prioritas2_4=?,prioritas2_5=?,prioritas2_6=?,prioritas2_7=?,prioritas2_8=?,prioritas3_1=?,prioritas3_2=?,prioritas3_3=?,prioritas3_4=?,"+
                "kriteria_fisiologis_tanda_vital_1=?,kriteria_fisiologis_tanda_vital_2=?,kriteria_fisiologis_tanda_vital_3=?,kriteria_fisiologis_tanda_vital_4=?,kriteria_fisiologis_tanda_vital_5=?,"+
                "kriteria_fisiologis_laborat_1=?,kriteria_fisiologis_laborat_2=?,kriteria_fisiologis_laborat_3=?,kriteria_fisiologis_laborat_4=?,kriteria_fisiologis_laborat_5=?,kriteria_fisiologis_laborat_6=?,"+
                "kriteria_fisiologis_radiologi_1=?,kriteria_fisiologis_radiologi_2=?,kriteria_fisiologis_klinis_1=?,kriteria_fisiologis_klinis_2=?,kriteria_fisiologis_klinis_3=?,kriteria_fisiologis_klinis_4=?,"+
                "kriteria_fisiologis_klinis_5=?,kriteria_fisiologis_klinis_6=?,kriteria_fisiologis_klinis_7=?,kriteria_fisiologis_klinis_8=?,nik=?",44,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Prioritas1_1.getSelectedItem().toString(),
                Prioritas1_2.getSelectedItem().toString(),Prioritas1_3.getSelectedItem().toString(),Prioritas1_4.getSelectedItem().toString(),Prioritas1_5.getSelectedItem().toString(),
                Prioritas1_6.getSelectedItem().toString(),Prioritas2_1.getSelectedItem().toString(),Prioritas2_2.getSelectedItem().toString(),Prioritas2_3.getSelectedItem().toString(),
                Prioritas2_4.getSelectedItem().toString(),Prioritas2_5.getSelectedItem().toString(),Prioritas2_6.getSelectedItem().toString(),Prioritas2_7.getSelectedItem().toString(),
                Prioritas2_8.getSelectedItem().toString(),Prioritas3_1.getSelectedItem().toString(),Prioritas3_2.getSelectedItem().toString(),Prioritas3_3.getSelectedItem().toString(),
                Prioritas3_4.getSelectedItem().toString(),TandaVital1.getSelectedItem().toString(),TandaVital2.getSelectedItem().toString(),TandaVital3.getSelectedItem().toString(),
                TandaVital4.getSelectedItem().toString(),TandaVital5.getSelectedItem().toString(),Laborat1.getSelectedItem().toString(),Laborat2.getSelectedItem().toString(),
                Laborat3.getSelectedItem().toString(),Laborat4.getSelectedItem().toString(),Laborat5.getSelectedItem().toString(),Laborat6.getSelectedItem().toString(),
                Radiologi1.getSelectedItem().toString(),Radiologi2.getSelectedItem().toString(),Kinis1.getSelectedItem().toString(),Kinis2.getSelectedItem().toString(),
                Kinis3.getSelectedItem().toString(),Kinis4.getSelectedItem().toString(),Kinis5.getSelectedItem().toString(),Kinis6.getSelectedItem().toString(),Kinis7.getSelectedItem().toString(),
                Kinis8.getSelectedItem().toString(),KodePetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(Prioritas1_1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Prioritas1_2.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(Prioritas1_3.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Prioritas1_4.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Prioritas1_5.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Prioritas1_6.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Prioritas2_1.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Prioritas2_2.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(Prioritas2_3.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(Prioritas2_4.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(Prioritas2_5.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(Prioritas2_6.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(Prioritas2_7.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(Prioritas2_8.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(Prioritas3_1.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(Prioritas3_2.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(Prioritas3_3.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(Prioritas3_4.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(TandaVital1.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(TandaVital2.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(TandaVital3.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(TandaVital4.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(TandaVital5.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(Laborat1.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(Laborat2.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(Laborat3.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(Laborat4.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(Laborat5.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
            tbObat.setValueAt(Laborat6.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
            tbObat.setValueAt(Radiologi1.getSelectedItem().toString(),tbObat.getSelectedRow(),35);
            tbObat.setValueAt(Radiologi2.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
            tbObat.setValueAt(Kinis1.getSelectedItem().toString(),tbObat.getSelectedRow(),37);
            tbObat.setValueAt(Kinis2.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
            tbObat.setValueAt(Kinis3.getSelectedItem().toString(),tbObat.getSelectedRow(),39);
            tbObat.setValueAt(Kinis4.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
            tbObat.setValueAt(Kinis5.getSelectedItem().toString(),tbObat.getSelectedRow(),41);
            tbObat.setValueAt(Kinis6.getSelectedItem().toString(),tbObat.getSelectedRow(),42);
            tbObat.setValueAt(Kinis7.getSelectedItem().toString(),tbObat.getSelectedRow(),43);
            tbObat.setValueAt(Kinis8.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),45);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),46);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_kriteria_masuk_icu where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
}

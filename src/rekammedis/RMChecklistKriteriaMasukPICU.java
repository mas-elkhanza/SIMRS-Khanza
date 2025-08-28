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
public final class RMChecklistKriteriaMasukPICU extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMChecklistKriteriaMasukPICU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","Membutuhkan Monitoring & Terapi Intensif Secara Berkelanjutan",
            "Membutuhkan Dukungan ≥ 1 Organ Vital","Pasien Dengan Kondisi Mengancam Jiwa Yang Masih Berpotensi Reversibel","Gagal Napas Akut (Misal: ARDS, Status Asmatik)",
            "Hipoksemia Berat (PaO₂ < 60 mmHg Dengan FiO₂ > 0,6)","Butuh Ventilasi Mekanik Invasif/Non-invasif","Hiperkarbia Berat (PaCO₂ > 60 mmHg Dengan pH < 7,25)",
            "Syok Refrakter (Septik, Kardiogenik, Hipovolemik, Anafilaksis)","Gagal Jantung Berat","Gangguan Irama Jantung Yang Mengancam Nyawa","Pasca Resusitasi Jantung Paru",
            "Trauma Kepala Berat Dengan Gangguan Hemodinamik/Napas","Kejang Berulang / Status Epileptikus","Penurunan Kesadaran (GCS ≤ 8 Atau Koma)","Edema Serebri, Perdarahan Intrakranial",
            "Pasca Operasi Mayor Dengan Risiko Komplikasi Tinggi","Pasca Transplantasi Organ","Pasca Operasi Jantung / Thoraks Kompleks","Gangguan Metabolik / Elektrolit Yang Mengancam Jiwa",
            "Intoksikasi Berat","Sepsis Berat Dengan Disfungsi Organ Multipel","Keputusan","Keterangan/Catatan","NIP/Kode Dokter","DPJP/Dokter Jaga/IGD"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 31; i++) {
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
            }else if(i==29){
                column.setPreferredWidth(90);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        Keterangan.setDocument(new batasInput((int)50).getKata(Keterangan));
        
        
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
        MnKriteriaMasukPICU = new javax.swing.JMenuItem();
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
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        jLabel61 = new widget.Label();
        KriteriaUmum2 = new widget.ComboBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        KriteriaUmum1 = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel59 = new widget.Label();
        jLabel92 = new widget.Label();
        Keputusan = new widget.ComboBox();
        jLabel91 = new widget.Label();
        jLabel9 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        KriteriaUmum3 = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        Respirasi1 = new widget.ComboBox();
        Respirasi2 = new widget.ComboBox();
        jLabel62 = new widget.Label();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        Respirasi3 = new widget.ComboBox();
        Respirasi4 = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        Kardiovaskuler1 = new widget.ComboBox();
        Kardiovaskuler2 = new widget.ComboBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        Kardiovaskuler3 = new widget.ComboBox();
        Kardiovaskuler4 = new widget.ComboBox();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Neurologis1 = new widget.ComboBox();
        Neurologis2 = new widget.ComboBox();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        Neurologis3 = new widget.ComboBox();
        Neurologis4 = new widget.ComboBox();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        Bedah1 = new widget.ComboBox();
        Bedah2 = new widget.ComboBox();
        jLabel90 = new widget.Label();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        Bedah3 = new widget.ComboBox();
        jLabel95 = new widget.Label();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        Lainlain1 = new widget.ComboBox();
        Lainlain2 = new widget.ComboBox();
        jLabel98 = new widget.Label();
        jLabel100 = new widget.Label();
        Lainlain3 = new widget.ComboBox();
        jLabel99 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKriteriaMasukPICU.setBackground(new java.awt.Color(255, 255, 254));
        MnKriteriaMasukPICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKriteriaMasukPICU.setForeground(new java.awt.Color(50, 50, 50));
        MnKriteriaMasukPICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKriteriaMasukPICU.setText("Formulir Checklist Kriteria Masuk PICU");
        MnKriteriaMasukPICU.setName("MnKriteriaMasukPICU"); // NOI18N
        MnKriteriaMasukPICU.setPreferredSize(new java.awt.Dimension(260, 26));
        MnKriteriaMasukPICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKriteriaMasukPICUActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKriteriaMasukPICU);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Kriteria Masuk PICU ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-08-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-08-2025" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 623));
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

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-08-2025 18:07:51" }));
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
        jLabel53.setText("I. KRITERIA UMUM");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        jLabel61.setText("Membutuhkan Dukungan ≥ 1 Organ Vital : ");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(455, 90, 250, 23);

        KriteriaUmum2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KriteriaUmum2.setSelectedIndex(1);
        KriteriaUmum2.setName("KriteriaUmum2"); // NOI18N
        KriteriaUmum2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KriteriaUmum2KeyPressed(evt);
            }
        });
        FormInput.add(KriteriaUmum2);
        KriteriaUmum2.setBounds(709, 90, 80, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 90, 361, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Membutuhkan Monitoring & Terapi Intensif Secara Berkelanjutan");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(40, 90, 341, 23);

        KriteriaUmum1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KriteriaUmum1.setSelectedIndex(1);
        KriteriaUmum1.setName("KriteriaUmum1"); // NOI18N
        KriteriaUmum1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KriteriaUmum1KeyPressed(evt);
            }
        });
        FormInput.add(KriteriaUmum1);
        KriteriaUmum1.setBounds(365, 90, 80, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 570, 810, 1);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 570, 810, 1);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("III. KEPUTUSAN & KETERANGAN");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(10, 570, 210, 23);

        jLabel92.setText(":");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(0, 590, 98, 23);

        Keputusan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diterima Di PICU", "Tidak Diterima - Dirawat Di Ruang Lain" }));
        Keputusan.setName("Keputusan"); // NOI18N
        Keputusan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeputusanKeyPressed(evt);
            }
        });
        FormInput.add(Keputusan);
        Keputusan.setBounds(102, 590, 240, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Keputusan");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(40, 590, 160, 23);

        jLabel9.setText("Keterangan/Catatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(385, 590, 130, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(519, 590, 270, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("Pasien Dengan Kondisi Mengancam Jiwa Yang Masih Berpotensi Reversibel");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(40, 120, 380, 23);

        jLabel67.setText(":");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 120, 409, 23);

        KriteriaUmum3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KriteriaUmum3.setSelectedIndex(1);
        KriteriaUmum3.setName("KriteriaUmum3"); // NOI18N
        KriteriaUmum3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KriteriaUmum3KeyPressed(evt);
            }
        });
        FormInput.add(KriteriaUmum3);
        KriteriaUmum3.setBounds(413, 120, 80, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 150, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 150, 810, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("II. KRITERIA KHUSUS");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 150, 180, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("1. Respirasi");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(40, 170, 380, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Gagal Napas Akut (Misal: ARDS, Status Asmatik)");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(60, 190, 250, 23);

        jLabel70.setText(":");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 190, 302, 23);

        Respirasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Respirasi1.setSelectedIndex(1);
        Respirasi1.setName("Respirasi1"); // NOI18N
        Respirasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Respirasi1KeyPressed(evt);
            }
        });
        FormInput.add(Respirasi1);
        Respirasi1.setBounds(306, 190, 80, 23);

        Respirasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Respirasi2.setSelectedIndex(1);
        Respirasi2.setName("Respirasi2"); // NOI18N
        Respirasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Respirasi2KeyPressed(evt);
            }
        });
        FormInput.add(Respirasi2);
        Respirasi2.setBounds(709, 190, 80, 23);

        jLabel62.setText("Hipoksemia Berat (PaO₂ < 60 mmHg Dengan FiO₂ > 0,6) :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(385, 190, 320, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Butuh Ventilasi Mekanik Invasif/Non-invasif");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(60, 220, 250, 23);

        jLabel72.setText(":");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 220, 278, 23);

        Respirasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Respirasi3.setSelectedIndex(1);
        Respirasi3.setName("Respirasi3"); // NOI18N
        Respirasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Respirasi3KeyPressed(evt);
            }
        });
        FormInput.add(Respirasi3);
        Respirasi3.setBounds(282, 220, 80, 23);

        Respirasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Respirasi4.setSelectedIndex(1);
        Respirasi4.setName("Respirasi4"); // NOI18N
        Respirasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Respirasi4KeyPressed(evt);
            }
        });
        FormInput.add(Respirasi4);
        Respirasi4.setBounds(709, 220, 80, 23);

        jLabel63.setText("Hiperkarbia Berat (PaCO₂ > 60 mmHg Dengan pH < 7,25) :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(385, 220, 320, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("2. Kardiovaskular");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(40, 250, 380, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Syok Refrakter (Septik, Kardiogenik, Hipovolemik, Anafilaksis)");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(60, 270, 310, 23);

        jLabel75.setText(":");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(0, 270, 366, 23);

        Kardiovaskuler1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiovaskuler1.setSelectedIndex(1);
        Kardiovaskuler1.setName("Kardiovaskuler1"); // NOI18N
        Kardiovaskuler1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiovaskuler1KeyPressed(evt);
            }
        });
        FormInput.add(Kardiovaskuler1);
        Kardiovaskuler1.setBounds(370, 270, 80, 23);

        Kardiovaskuler2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiovaskuler2.setSelectedIndex(1);
        Kardiovaskuler2.setName("Kardiovaskuler2"); // NOI18N
        Kardiovaskuler2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiovaskuler2KeyPressed(evt);
            }
        });
        FormInput.add(Kardiovaskuler2);
        Kardiovaskuler2.setBounds(709, 270, 80, 23);

        jLabel76.setText("Gagal Jantung Berat :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(575, 270, 130, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("Gangguan Irama Jantung Yang Mengancam Nyawa");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(60, 300, 310, 23);

        jLabel78.setText(":");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(0, 300, 312, 23);

        Kardiovaskuler3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiovaskuler3.setSelectedIndex(1);
        Kardiovaskuler3.setName("Kardiovaskuler3"); // NOI18N
        Kardiovaskuler3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiovaskuler3KeyPressed(evt);
            }
        });
        FormInput.add(Kardiovaskuler3);
        Kardiovaskuler3.setBounds(316, 300, 80, 23);

        Kardiovaskuler4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardiovaskuler4.setSelectedIndex(1);
        Kardiovaskuler4.setName("Kardiovaskuler4"); // NOI18N
        Kardiovaskuler4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardiovaskuler4KeyPressed(evt);
            }
        });
        FormInput.add(Kardiovaskuler4);
        Kardiovaskuler4.setBounds(709, 300, 80, 23);

        jLabel79.setText("Pasca Resusitasi Jantung Paru :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(525, 300, 180, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("3. Neurologis");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(40, 330, 380, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Trauma Kepala Berat Dengan Gangguan Hemodinamik/Napas");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(60, 350, 310, 23);

        jLabel82.setText(":");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(0, 350, 364, 23);

        Neurologis1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Neurologis1.setSelectedIndex(1);
        Neurologis1.setName("Neurologis1"); // NOI18N
        Neurologis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Neurologis1KeyPressed(evt);
            }
        });
        FormInput.add(Neurologis1);
        Neurologis1.setBounds(368, 350, 80, 23);

        Neurologis2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Neurologis2.setSelectedIndex(1);
        Neurologis2.setName("Neurologis2"); // NOI18N
        Neurologis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Neurologis2KeyPressed(evt);
            }
        });
        FormInput.add(Neurologis2);
        Neurologis2.setBounds(709, 350, 80, 23);

        jLabel83.setText("Kejang Berulang / Status Epileptikus :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(505, 350, 200, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Penurunan Kesadaran (GCS ≤ 8 Atau Koma)");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(60, 380, 250, 23);

        jLabel85.setText(":");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 380, 280, 23);

        Neurologis3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Neurologis3.setSelectedIndex(1);
        Neurologis3.setName("Neurologis3"); // NOI18N
        Neurologis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Neurologis3KeyPressed(evt);
            }
        });
        FormInput.add(Neurologis3);
        Neurologis3.setBounds(284, 380, 80, 23);

        Neurologis4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Neurologis4.setSelectedIndex(1);
        Neurologis4.setName("Neurologis4"); // NOI18N
        Neurologis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Neurologis4KeyPressed(evt);
            }
        });
        FormInput.add(Neurologis4);
        Neurologis4.setBounds(709, 380, 80, 23);

        jLabel86.setText("Edema Serebri, Perdarahan Intrakranial :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(475, 380, 230, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("4. Bedah / Pasca Operasi");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(40, 410, 380, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Pasca Operasi Mayor Dengan Risiko Komplikasi Tinggi");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(60, 430, 270, 23);

        jLabel89.setText(":");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 430, 328, 23);

        Bedah1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Bedah1.setSelectedIndex(1);
        Bedah1.setName("Bedah1"); // NOI18N
        Bedah1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Bedah1KeyPressed(evt);
            }
        });
        FormInput.add(Bedah1);
        Bedah1.setBounds(332, 430, 80, 23);

        Bedah2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Bedah2.setSelectedIndex(1);
        Bedah2.setName("Bedah2"); // NOI18N
        Bedah2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Bedah2KeyPressed(evt);
            }
        });
        FormInput.add(Bedah2);
        Bedah2.setBounds(709, 430, 80, 23);

        jLabel90.setText("Pasca Transplantasi Organ :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(505, 430, 200, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("Pasca Operasi Jantung / Thoraks Kompleks");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(60, 460, 250, 23);

        jLabel94.setText(":");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(0, 460, 275, 23);

        Bedah3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Bedah3.setSelectedIndex(1);
        Bedah3.setName("Bedah3"); // NOI18N
        Bedah3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Bedah3KeyPressed(evt);
            }
        });
        FormInput.add(Bedah3);
        Bedah3.setBounds(279, 460, 80, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("5. Lain-lain");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(40, 490, 380, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("Gangguan Metabolik / Elektrolit Yang Mengancam Jiwa");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(60, 510, 280, 23);

        jLabel97.setText(":");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(0, 510, 332, 23);

        Lainlain1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Lainlain1.setSelectedIndex(1);
        Lainlain1.setName("Lainlain1"); // NOI18N
        Lainlain1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lainlain1KeyPressed(evt);
            }
        });
        FormInput.add(Lainlain1);
        Lainlain1.setBounds(336, 510, 80, 23);

        Lainlain2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Lainlain2.setSelectedIndex(1);
        Lainlain2.setName("Lainlain2"); // NOI18N
        Lainlain2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lainlain2KeyPressed(evt);
            }
        });
        FormInput.add(Lainlain2);
        Lainlain2.setBounds(709, 510, 80, 23);

        jLabel98.setText("Intoksikasi Berat :");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(505, 510, 200, 23);

        jLabel100.setText(":");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(0, 540, 289, 23);

        Lainlain3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Lainlain3.setSelectedIndex(1);
        Lainlain3.setName("Lainlain3"); // NOI18N
        Lainlain3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Lainlain3KeyPressed(evt);
            }
        });
        FormInput.add(Lainlain3);
        Lainlain3.setBounds(293, 540, 80, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("Sepsis Berat Dengan Disfungsi Organ Multipel");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(60, 540, 280, 23);

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
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString())){
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
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString())){
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
                StringBuilder htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Membutuhkan Monitoring & Terapi Intensif Secara Berkelanjutan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Membutuhkan Dukungan ≥ 1 Organ Vital</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasien Dengan Kondisi Mengancam Jiwa Yang Masih Berpotensi Reversibel</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Napas Akut (Misal: ARDS, Status Asmatik)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hipoksemia Berat (PaO₂ < 60 mmHg Dengan FiO₂ > 0,6)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Butuh Ventilasi Mekanik Invasif/Non-invasif</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hiperkarbia Berat (PaCO₂ > 60 mmHg Dengan pH < 7,25)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Syok Refrakter (Septik, Kardiogenik, Hipovolemik, Anafilaksis)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gagal Jantung Berat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Irama Jantung Yang Mengancam Nyawa</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Resusitasi Jantung Paru</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Trauma Kepala Berat Dengan Gangguan Hemodinamik/Napas</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kejang Berulang / Status Epileptikus</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penurunan Kesadaran (GCS ≤ 8 Atau Koma)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Edema Serebri, Perdarahan Intrakranial</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Operasi Mayor Dengan Risiko Komplikasi Tinggi</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Transplantasi Organ</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Operasi Jantung / Thoraks Kompleks</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Metabolik / Elektrolit Yang Mengancam Jiwa</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Intoksikasi Berat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sepsis Berat Dengan Disfungsi Organ Multipel</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keputusan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan/Catatan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP/Kode Dokter</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DPJP/Dokter Jaga/IGD</b></td>").append(
                    "</tr>"
                );
                
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>").append(
                           "<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").append( 
                            "<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,29).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,30).toString()).append("</td>").append(
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='3600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataChecklistKriteriaMasukPICU.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECK LIST KRITERIA MASUK PICU<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
                htmlContent=null;
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

    private void MnKriteriaMasukPICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKriteriaMasukPICUActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),30).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),29).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirChecklistKriteriaMasukPICU.jasper","report","::[ Formulir Check List Kriteria Masuk PICU ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_picu.tanggal,"+
                    "checklist_kriteria_masuk_picu.kriteriaumum1,checklist_kriteria_masuk_picu.kriteriaumum2,checklist_kriteria_masuk_picu.kriteriaumum3,"+
                    "checklist_kriteria_masuk_picu.respirasi1,checklist_kriteria_masuk_picu.respirasi2,checklist_kriteria_masuk_picu.respirasi3,"+
                    "checklist_kriteria_masuk_picu.respirasi4,checklist_kriteria_masuk_picu.kardio1,checklist_kriteria_masuk_picu.kardio2,"+
                    "checklist_kriteria_masuk_picu.kardio3,checklist_kriteria_masuk_picu.kardio4,checklist_kriteria_masuk_picu.neuro1,"+
                    "checklist_kriteria_masuk_picu.neuro2,checklist_kriteria_masuk_picu.neuro3,checklist_kriteria_masuk_picu.neuro4,"+
                    "checklist_kriteria_masuk_picu.bedah1,checklist_kriteria_masuk_picu.bedah2,checklist_kriteria_masuk_picu.bedah3,"+
                    "checklist_kriteria_masuk_picu.kondisilain1,checklist_kriteria_masuk_picu.kondisilain2,checklist_kriteria_masuk_picu.kondisilain3,"+
                    "checklist_kriteria_masuk_picu.keputusan,checklist_kriteria_masuk_picu.keterangan,checklist_kriteria_masuk_picu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_picu inner join reg_periksa on checklist_kriteria_masuk_picu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_picu.nik "+
                    "where checklist_kriteria_masuk_picu.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_kriteria_masuk_picu.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnKriteriaMasukPICUActionPerformed

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
       Valid.pindah(evt,Tanggal,KriteriaUmum1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void KriteriaUmum2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KriteriaUmum2KeyPressed
        Valid.pindah(evt,KriteriaUmum1,KriteriaUmum3);
    }//GEN-LAST:event_KriteriaUmum2KeyPressed

    private void KriteriaUmum1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KriteriaUmum1KeyPressed
        Valid.pindah(evt,btnPetugas,KriteriaUmum2);
    }//GEN-LAST:event_KriteriaUmum1KeyPressed

    private void KeputusanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeputusanKeyPressed
        Valid.pindah(evt,Lainlain3,Keterangan);
    }//GEN-LAST:event_KeputusanKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,Keputusan,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void KriteriaUmum3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KriteriaUmum3KeyPressed
        Valid.pindah(evt,KriteriaUmum2,Respirasi1);
    }//GEN-LAST:event_KriteriaUmum3KeyPressed

    private void Lainlain3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lainlain3KeyPressed
        Valid.pindah(evt,Lainlain2,Keputusan);
    }//GEN-LAST:event_Lainlain3KeyPressed

    private void Lainlain2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lainlain2KeyPressed
        Valid.pindah(evt,Lainlain1,Lainlain3);
    }//GEN-LAST:event_Lainlain2KeyPressed

    private void Lainlain1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Lainlain1KeyPressed
        Valid.pindah(evt,Bedah3,Lainlain2);
    }//GEN-LAST:event_Lainlain1KeyPressed

    private void Bedah3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Bedah3KeyPressed
        Valid.pindah(evt,Bedah2,Lainlain1);
    }//GEN-LAST:event_Bedah3KeyPressed

    private void Bedah2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Bedah2KeyPressed
        Valid.pindah(evt,Bedah1,Bedah3);
    }//GEN-LAST:event_Bedah2KeyPressed

    private void Bedah1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Bedah1KeyPressed
        Valid.pindah(evt,Neurologis4,Bedah2);
    }//GEN-LAST:event_Bedah1KeyPressed

    private void Neurologis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Neurologis4KeyPressed
        Valid.pindah(evt,Neurologis3,Bedah1);
    }//GEN-LAST:event_Neurologis4KeyPressed

    private void Neurologis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Neurologis3KeyPressed
        Valid.pindah(evt,Neurologis2,Neurologis4);
    }//GEN-LAST:event_Neurologis3KeyPressed

    private void Neurologis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Neurologis2KeyPressed
        Valid.pindah(evt,Neurologis1,Neurologis3);
    }//GEN-LAST:event_Neurologis2KeyPressed

    private void Neurologis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Neurologis1KeyPressed
        Valid.pindah(evt,Kardiovaskuler4,Neurologis2);
    }//GEN-LAST:event_Neurologis1KeyPressed

    private void Kardiovaskuler4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiovaskuler4KeyPressed
        Valid.pindah(evt,Kardiovaskuler3,Neurologis1);
    }//GEN-LAST:event_Kardiovaskuler4KeyPressed

    private void Kardiovaskuler3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiovaskuler3KeyPressed
        Valid.pindah(evt,Kardiovaskuler2,Kardiovaskuler4);
    }//GEN-LAST:event_Kardiovaskuler3KeyPressed

    private void Kardiovaskuler2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiovaskuler2KeyPressed
        Valid.pindah(evt,Kardiovaskuler1,Kardiovaskuler3);
    }//GEN-LAST:event_Kardiovaskuler2KeyPressed

    private void Kardiovaskuler1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardiovaskuler1KeyPressed
        Valid.pindah(evt,Respirasi4,Kardiovaskuler2);
    }//GEN-LAST:event_Kardiovaskuler1KeyPressed

    private void Respirasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Respirasi4KeyPressed
        Valid.pindah(evt,Respirasi3,Kardiovaskuler1);
    }//GEN-LAST:event_Respirasi4KeyPressed

    private void Respirasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Respirasi3KeyPressed
        Valid.pindah(evt,Respirasi2,Respirasi4);
    }//GEN-LAST:event_Respirasi3KeyPressed

    private void Respirasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Respirasi2KeyPressed
        Valid.pindah(evt,Respirasi1,Respirasi3);
    }//GEN-LAST:event_Respirasi2KeyPressed

    private void Respirasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Respirasi1KeyPressed
        Valid.pindah(evt,KriteriaUmum2,Respirasi2);
    }//GEN-LAST:event_Respirasi1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistKriteriaMasukPICU dialog = new RMChecklistKriteriaMasukPICU(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Bedah1;
    private widget.ComboBox Bedah2;
    private widget.ComboBox Bedah3;
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
    private widget.ComboBox Kardiovaskuler1;
    private widget.ComboBox Kardiovaskuler2;
    private widget.ComboBox Kardiovaskuler3;
    private widget.ComboBox Kardiovaskuler4;
    private widget.ComboBox Keputusan;
    private widget.TextBox Keterangan;
    private widget.TextBox KodePetugas;
    private widget.ComboBox KriteriaUmum1;
    private widget.ComboBox KriteriaUmum2;
    private widget.ComboBox KriteriaUmum3;
    private widget.Label LCount;
    private widget.ComboBox Lainlain1;
    private widget.ComboBox Lainlain2;
    private widget.ComboBox Lainlain3;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnKriteriaMasukPICU;
    private widget.TextBox NamaPetugas;
    private widget.ComboBox Neurologis1;
    private widget.ComboBox Neurologis2;
    private widget.ComboBox Neurologis3;
    private widget.ComboBox Neurologis4;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Respirasi1;
    private widget.ComboBox Respirasi2;
    private widget.ComboBox Respirasi3;
    private widget.ComboBox Respirasi4;
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
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
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
    private widget.Label jLabel9;
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
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_picu.tanggal,"+
                    "checklist_kriteria_masuk_picu.kriteriaumum1,checklist_kriteria_masuk_picu.kriteriaumum2,checklist_kriteria_masuk_picu.kriteriaumum3,"+
                    "checklist_kriteria_masuk_picu.respirasi1,checklist_kriteria_masuk_picu.respirasi2,checklist_kriteria_masuk_picu.respirasi3,"+
                    "checklist_kriteria_masuk_picu.respirasi4,checklist_kriteria_masuk_picu.kardio1,checklist_kriteria_masuk_picu.kardio2,"+
                    "checklist_kriteria_masuk_picu.kardio3,checklist_kriteria_masuk_picu.kardio4,checklist_kriteria_masuk_picu.neuro1,"+
                    "checklist_kriteria_masuk_picu.neuro2,checklist_kriteria_masuk_picu.neuro3,checklist_kriteria_masuk_picu.neuro4,"+
                    "checklist_kriteria_masuk_picu.bedah1,checklist_kriteria_masuk_picu.bedah2,checklist_kriteria_masuk_picu.bedah3,"+
                    "checklist_kriteria_masuk_picu.kondisilain1,checklist_kriteria_masuk_picu.kondisilain2,checklist_kriteria_masuk_picu.kondisilain3,"+
                    "checklist_kriteria_masuk_picu.keputusan,checklist_kriteria_masuk_picu.keterangan,checklist_kriteria_masuk_picu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_picu inner join reg_periksa on checklist_kriteria_masuk_picu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_picu.nik "+
                    "where checklist_kriteria_masuk_picu.tanggal between ? and ? order by checklist_kriteria_masuk_picu.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_picu.tanggal,"+
                    "checklist_kriteria_masuk_picu.kriteriaumum1,checklist_kriteria_masuk_picu.kriteriaumum2,checklist_kriteria_masuk_picu.kriteriaumum3,"+
                    "checklist_kriteria_masuk_picu.respirasi1,checklist_kriteria_masuk_picu.respirasi2,checklist_kriteria_masuk_picu.respirasi3,"+
                    "checklist_kriteria_masuk_picu.respirasi4,checklist_kriteria_masuk_picu.kardio1,checklist_kriteria_masuk_picu.kardio2,"+
                    "checklist_kriteria_masuk_picu.kardio3,checklist_kriteria_masuk_picu.kardio4,checklist_kriteria_masuk_picu.neuro1,"+
                    "checklist_kriteria_masuk_picu.neuro2,checklist_kriteria_masuk_picu.neuro3,checklist_kriteria_masuk_picu.neuro4,"+
                    "checklist_kriteria_masuk_picu.bedah1,checklist_kriteria_masuk_picu.bedah2,checklist_kriteria_masuk_picu.bedah3,"+
                    "checklist_kriteria_masuk_picu.kondisilain1,checklist_kriteria_masuk_picu.kondisilain2,checklist_kriteria_masuk_picu.kondisilain3,"+
                    "checklist_kriteria_masuk_picu.keputusan,checklist_kriteria_masuk_picu.keterangan,checklist_kriteria_masuk_picu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_picu inner join reg_periksa on checklist_kriteria_masuk_picu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_picu.nik "+
                    "where checklist_kriteria_masuk_picu.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or pegawai.nama like ? or checklist_kriteria_masuk_picu.nik like ?) order by checklist_kriteria_masuk_picu.tanggal ");
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
                        rs.getString("tanggal"),rs.getString("kriteriaumum1"),rs.getString("kriteriaumum2"),rs.getString("kriteriaumum3"),rs.getString("respirasi1"),
                        rs.getString("respirasi2"),rs.getString("respirasi3"),rs.getString("respirasi4"),rs.getString("kardio1"),rs.getString("kardio2"),
                        rs.getString("kardio3"),rs.getString("kardio4"),rs.getString("neuro1"),rs.getString("neuro2"),rs.getString("neuro3"),rs.getString("neuro4"),
                        rs.getString("bedah1"),rs.getString("bedah2"),rs.getString("bedah3"),rs.getString("kondisilain1"),rs.getString("kondisilain2"),
                        rs.getString("kondisilain3"),rs.getString("keputusan"),rs.getString("keterangan"),rs.getString("nik"),rs.getString("nama")
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
        KriteriaUmum1.setSelectedItem("Tidak");
        KriteriaUmum2.setSelectedItem("Tidak");
        KriteriaUmum3.setSelectedItem("Tidak");
        Respirasi1.setSelectedItem("Tidak");
        Respirasi2.setSelectedItem("Tidak");
        Respirasi3.setSelectedItem("Tidak");
        Respirasi4.setSelectedItem("Tidak");
        Kardiovaskuler1.setSelectedItem("Tidak");
        Kardiovaskuler2.setSelectedItem("Tidak");
        Kardiovaskuler3.setSelectedItem("Tidak");
        Kardiovaskuler4.setSelectedItem("Tidak");
        Neurologis1.setSelectedItem("Tidak");
        Neurologis2.setSelectedItem("Tidak");
        Neurologis3.setSelectedItem("Tidak");
        Neurologis4.setSelectedItem("Tidak");
        Bedah1.setSelectedItem("Tidak");
        Bedah2.setSelectedItem("Tidak");
        Bedah3.setSelectedItem("Tidak");
        Lainlain1.setSelectedItem("Tidak");
        Lainlain2.setSelectedItem("Tidak");
        Lainlain3.setSelectedItem("Tidak");
        Keputusan.setSelectedItem("Tidak");
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        KriteriaUmum1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            KriteriaUmum1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            KriteriaUmum2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KriteriaUmum3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Respirasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Respirasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Respirasi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Respirasi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Kardiovaskuler1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Kardiovaskuler2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Kardiovaskuler3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Kardiovaskuler4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Neurologis1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Neurologis2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Neurologis3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Neurologis4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Bedah1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Bedah2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Bedah3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Lainlain1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Lainlain2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Lainlain3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Keputusan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
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
        BtnSimpan.setEnabled(akses.getkriteria_masuk_picu());
        BtnHapus.setEnabled(akses.getkriteria_masuk_picu());
        BtnEdit.setEnabled(akses.getkriteria_masuk_picu());
        BtnPrint.setEnabled(akses.getkriteria_masuk_picu()); 
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
        if(Sequel.mengedittf("checklist_kriteria_masuk_picu","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,kriteriaumum1=?,kriteriaumum2=?,kriteriaumum3=?,respirasi1=?,respirasi2=?,respirasi3=?,"+
                "respirasi4=?,kardio1=?,kardio2=?,kardio3=?,kardio4=?,neuro1=?,neuro2=?,neuro3=?,neuro4=?,bedah1=?,bedah2=?,bedah3=?,kondisilain1=?,kondisilain2=?,kondisilain3=?,keputusan=?,"+
                "keterangan=?,nik=?",28,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KriteriaUmum1.getSelectedItem().toString(),
                KriteriaUmum2.getSelectedItem().toString(),KriteriaUmum3.getSelectedItem().toString(),Respirasi1.getSelectedItem().toString(),Respirasi2.getSelectedItem().toString(),
                Respirasi3.getSelectedItem().toString(),Respirasi4.getSelectedItem().toString(),Kardiovaskuler1.getSelectedItem().toString(),Kardiovaskuler2.getSelectedItem().toString(),
                Kardiovaskuler3.getSelectedItem().toString(),Kardiovaskuler4.getSelectedItem().toString(),Neurologis1.getSelectedItem().toString(),Neurologis2.getSelectedItem().toString(),
                Neurologis3.getSelectedItem().toString(),Neurologis4.getSelectedItem().toString(),Bedah1.getSelectedItem().toString(),Bedah2.getSelectedItem().toString(),
                Bedah3.getSelectedItem().toString(),Lainlain1.getSelectedItem().toString(),Lainlain2.getSelectedItem().toString(),Lainlain3.getSelectedItem().toString(),
                Keputusan.getSelectedItem().toString(),Keterangan.getText(),KodePetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(KriteriaUmum1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(KriteriaUmum2.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(KriteriaUmum3.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Respirasi1.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Respirasi2.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Respirasi3.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Respirasi4.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Kardiovaskuler1.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(Kardiovaskuler2.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(Kardiovaskuler3.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(Kardiovaskuler4.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(Neurologis1.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(Neurologis2.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(Neurologis3.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(Neurologis4.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(Bedah1.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(Bedah2.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(Bedah3.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(Lainlain1.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(Lainlain2.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(Lainlain3.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(Keputusan.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),30);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_kriteria_masuk_picu where no_rawat=? and tanggal=?",2,new String[]{
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
        if(Sequel.menyimpantf("checklist_kriteria_masuk_picu","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",26,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KriteriaUmum1.getSelectedItem().toString(),
            KriteriaUmum2.getSelectedItem().toString(),KriteriaUmum3.getSelectedItem().toString(),Respirasi1.getSelectedItem().toString(),Respirasi2.getSelectedItem().toString(),
            Respirasi3.getSelectedItem().toString(),Respirasi4.getSelectedItem().toString(),Kardiovaskuler1.getSelectedItem().toString(),Kardiovaskuler2.getSelectedItem().toString(),
            Kardiovaskuler3.getSelectedItem().toString(),Kardiovaskuler4.getSelectedItem().toString(),Neurologis1.getSelectedItem().toString(),Neurologis2.getSelectedItem().toString(),
            Neurologis3.getSelectedItem().toString(),Neurologis4.getSelectedItem().toString(),Bedah1.getSelectedItem().toString(),Bedah2.getSelectedItem().toString(),
            Bedah3.getSelectedItem().toString(),Lainlain1.getSelectedItem().toString(),Lainlain2.getSelectedItem().toString(),Lainlain3.getSelectedItem().toString(),
            Keputusan.getSelectedItem().toString(),Keterangan.getText(),KodePetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                KriteriaUmum1.getSelectedItem().toString(),KriteriaUmum2.getSelectedItem().toString(),KriteriaUmum3.getSelectedItem().toString(),Respirasi1.getSelectedItem().toString(),Respirasi2.getSelectedItem().toString(),
                Respirasi3.getSelectedItem().toString(),Respirasi4.getSelectedItem().toString(),Kardiovaskuler1.getSelectedItem().toString(),Kardiovaskuler2.getSelectedItem().toString(),
                Kardiovaskuler3.getSelectedItem().toString(),Kardiovaskuler4.getSelectedItem().toString(),Neurologis1.getSelectedItem().toString(),Neurologis2.getSelectedItem().toString(),
                Neurologis3.getSelectedItem().toString(),Neurologis4.getSelectedItem().toString(),Bedah1.getSelectedItem().toString(),Bedah2.getSelectedItem().toString(),
                Bedah3.getSelectedItem().toString(),Lainlain1.getSelectedItem().toString(),Lainlain2.getSelectedItem().toString(),Lainlain3.getSelectedItem().toString(),
                Keputusan.getSelectedItem().toString(),Keterangan.getText(),KodePetugas.getText(),NamaPetugas.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }
    }
}

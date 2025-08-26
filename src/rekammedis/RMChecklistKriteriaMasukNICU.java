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
public final class RMChecklistKriteriaMasukNICU extends javax.swing.JDialog {
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
    public RMChecklistKriteriaMasukNICU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","Distres Napas Berat (Grunting, Retraksi, Takipnea > 70x/menit)",
            "Apnea Berulang/Apnea Dengan Bradikardia","Kebutuhan CPAP Atau Ventilasi Mekanik","Saturasi O₂ < 90% Dengan Oksigen Suplementasi",
            "Berat Lahir < 2000 g (Khususnya < 1500 g : VLBW)","Usia Kehamilan < 35 minggu (Khususnya < 32 minggu)","Hipotermia (< 36°C) Yang Tidak Membaik Dengan Penghangatan Biasa",
            "Syok Refrakter Resusitasi Awal","Bradikardia Berat (< 80x/menit)","Sianosis Sentral Menetap","Kejang","GCS/Kesadaran Bayi Sangat Rendah",
            "Ensefalopati Hipoksik Iskemik Sedang–Berat","Hipoglikemia (< 40 mg/dL) Atau Hiperglikemia Berat","Dugaan Atau Terkonfirmasi Sepsis Neonatal",
            "Asidosis Metabolik Berat (pH < 7,2)","Kelainan Kongenital Berat Memerlukan Stabilisasi Intensif","Dari Ibu Dengan Riwayat Komplikasi Perinatal Berat",
            "Pasca Operasi Besar Neonatus","Ikterus Berat Memerlukan Fototerapi Intensif Atau Transfusi Tukar",
            "Keputusan","Keterangan/Catatan","NIP/Kode Dokter","DPJP/Dokter Jaga/IGD"
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
        MnKriteriaMasukNICU = new javax.swing.JMenuItem();
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
        Respirasi2 = new widget.ComboBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        Respirasi1 = new widget.ComboBox();
        Respirasi4 = new widget.ComboBox();
        jLabel62 = new widget.Label();
        Respirasi3 = new widget.ComboBox();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel63 = new widget.Label();
        Prematur2 = new widget.ComboBox();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        Prematur1 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        Prematur3 = new widget.ComboBox();
        jLabel71 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel55 = new widget.Label();
        jLabel72 = new widget.Label();
        Kardio3 = new widget.ComboBox();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        Kardio1 = new widget.ComboBox();
        jLabel76 = new widget.Label();
        Kardio2 = new widget.ComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel56 = new widget.Label();
        jLabel75 = new widget.Label();
        Neuro3 = new widget.ComboBox();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        Neuro1 = new widget.ComboBox();
        jLabel79 = new widget.Label();
        Neuro2 = new widget.ComboBox();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel57 = new widget.Label();
        jLabel80 = new widget.Label();
        Metabolik2 = new widget.ComboBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Metabolik1 = new widget.ComboBox();
        Metabolik3 = new widget.ComboBox();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel58 = new widget.Label();
        jLabel83 = new widget.Label();
        Kondisilain2 = new widget.ComboBox();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        Kondisilain1 = new widget.ComboBox();
        jLabel88 = new widget.Label();
        Kondisilain4 = new widget.ComboBox();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        Kondisilain3 = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel59 = new widget.Label();
        jLabel92 = new widget.Label();
        Keputusan = new widget.ComboBox();
        jLabel91 = new widget.Label();
        jLabel9 = new widget.Label();
        Keterangan = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKriteriaMasukNICU.setBackground(new java.awt.Color(255, 255, 254));
        MnKriteriaMasukNICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKriteriaMasukNICU.setForeground(new java.awt.Color(50, 50, 50));
        MnKriteriaMasukNICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKriteriaMasukNICU.setText("Formulir Checklist Kriteria Masuk NICU");
        MnKriteriaMasukNICU.setName("MnKriteriaMasukNICU"); // NOI18N
        MnKriteriaMasukNICU.setPreferredSize(new java.awt.Dimension(260, 26));
        MnKriteriaMasukNICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKriteriaMasukNICUActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKriteriaMasukNICU);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Kriteria Masuk NICU ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-08-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-08-2025" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 543));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-08-2025 08:13:34" }));
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
        jLabel53.setText("I. RESPIRASI");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        jLabel61.setText("Apnea Berulang/Apnea Dengan Bradikardia :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(445, 90, 260, 23);

        Respirasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Respirasi2.setSelectedIndex(1);
        Respirasi2.setName("Respirasi2"); // NOI18N
        Respirasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Respirasi2KeyPressed(evt);
            }
        });
        FormInput.add(Respirasi2);
        Respirasi2.setBounds(709, 90, 80, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 90, 355, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Distres Napas Berat (Grunting, Retraksi, Takipnea > 70x/menit)");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(40, 90, 341, 23);

        Respirasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Respirasi1.setSelectedIndex(1);
        Respirasi1.setName("Respirasi1"); // NOI18N
        Respirasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Respirasi1KeyPressed(evt);
            }
        });
        FormInput.add(Respirasi1);
        Respirasi1.setBounds(359, 90, 80, 23);

        Respirasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Respirasi4.setSelectedIndex(1);
        Respirasi4.setName("Respirasi4"); // NOI18N
        Respirasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Respirasi4KeyPressed(evt);
            }
        });
        FormInput.add(Respirasi4);
        Respirasi4.setBounds(709, 120, 80, 23);

        jLabel62.setText("Saturasi O₂ < 90% Dengan Oksigen Suplementasi :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(435, 120, 270, 23);

        Respirasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Respirasi3.setSelectedIndex(1);
        Respirasi3.setName("Respirasi3"); // NOI18N
        Respirasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Respirasi3KeyPressed(evt);
            }
        });
        FormInput.add(Respirasi3);
        Respirasi3.setBounds(247, 120, 80, 23);

        jLabel66.setText(":");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 120, 243, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Kebutuhan CPAP Atau Ventilasi Mekanik");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(40, 120, 230, 23);

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
        jLabel54.setText("II.  PREMATURITAS & BERAT BADAN");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 150, 210, 23);

        jLabel63.setText("Usia Kehamilan < 35 minggu (Khususnya < 32 minggu) :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(415, 170, 290, 23);

        Prematur2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prematur2.setSelectedIndex(1);
        Prematur2.setName("Prematur2"); // NOI18N
        Prematur2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prematur2KeyPressed(evt);
            }
        });
        FormInput.add(Prematur2);
        Prematur2.setBounds(709, 170, 80, 23);

        jLabel68.setText(":");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 170, 294, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Berat Lahir < 2000 g (Khususnya < 1500 g : VLBW)");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(40, 170, 260, 23);

        Prematur1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prematur1.setSelectedIndex(1);
        Prematur1.setName("Prematur1"); // NOI18N
        Prematur1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prematur1KeyPressed(evt);
            }
        });
        FormInput.add(Prematur1);
        Prematur1.setBounds(298, 170, 80, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("Hipotermia (< 36°C) Yang Tidak Membaik Dengan Penghangatan Biasa");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(40, 200, 370, 23);

        Prematur3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Prematur3.setSelectedIndex(1);
        Prematur3.setName("Prematur3"); // NOI18N
        Prematur3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prematur3KeyPressed(evt);
            }
        });
        FormInput.add(Prematur3);
        Prematur3.setBounds(395, 200, 80, 23);

        jLabel71.setText(":");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(0, 200, 391, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 230, 810, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 230, 810, 1);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("III. KONDISI KARDIOVASKULAR");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 230, 210, 23);

        jLabel72.setText("Sianosis Sentral Menetap :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(555, 250, 150, 23);

        Kardio3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardio3.setSelectedIndex(1);
        Kardio3.setName("Kardio3"); // NOI18N
        Kardio3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardio3KeyPressed(evt);
            }
        });
        FormInput.add(Kardio3);
        Kardio3.setBounds(709, 250, 80, 23);

        jLabel73.setText(":");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(0, 250, 198, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Syok Refrakter Resusitasi Awal");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(40, 250, 170, 23);

        Kardio1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardio1.setSelectedIndex(1);
        Kardio1.setName("Kardio1"); // NOI18N
        Kardio1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardio1KeyPressed(evt);
            }
        });
        FormInput.add(Kardio1);
        Kardio1.setBounds(202, 250, 80, 23);

        jLabel76.setText("Bradikardia Berat (< 80x/menit) :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(288, 250, 180, 23);

        Kardio2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kardio2.setSelectedIndex(1);
        Kardio2.setName("Kardio2"); // NOI18N
        Kardio2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kardio2KeyPressed(evt);
            }
        });
        FormInput.add(Kardio2);
        Kardio2.setBounds(472, 250, 80, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 280, 810, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 280, 810, 1);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. NEUROLOGI");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 280, 210, 23);

        jLabel75.setText("Ensefalopati Hipoksik Iskemik Sedang–Berat :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(445, 300, 260, 23);

        Neuro3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Neuro3.setSelectedIndex(1);
        Neuro3.setName("Neuro3"); // NOI18N
        Neuro3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Neuro3KeyPressed(evt);
            }
        });
        FormInput.add(Neuro3);
        Neuro3.setBounds(709, 300, 80, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 300, 80, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Kejang");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(40, 300, 70, 23);

        Neuro1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Neuro1.setSelectedIndex(1);
        Neuro1.setName("Neuro1"); // NOI18N
        Neuro1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Neuro1KeyPressed(evt);
            }
        });
        FormInput.add(Neuro1);
        Neuro1.setBounds(84, 300, 80, 23);

        jLabel79.setText("GCS/Kesadaran Bayi Sangat Rendah :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(173, 300, 200, 23);

        Neuro2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Neuro2.setSelectedIndex(1);
        Neuro2.setName("Neuro2"); // NOI18N
        Neuro2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Neuro2KeyPressed(evt);
            }
        });
        FormInput.add(Neuro2);
        Neuro2.setBounds(377, 300, 80, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 330, 810, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 330, 810, 1);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("V. METABOLIK & INFEKSI");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(10, 330, 210, 23);

        jLabel80.setText("Dugaan Atau Terkonfirmasi Sepsis Neonatal :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(445, 350, 260, 23);

        Metabolik2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Metabolik2.setSelectedIndex(1);
        Metabolik2.setName("Metabolik2"); // NOI18N
        Metabolik2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metabolik2KeyPressed(evt);
            }
        });
        FormInput.add(Metabolik2);
        Metabolik2.setBounds(709, 350, 80, 23);

        jLabel81.setText(":");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(0, 350, 302, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Hipoglikemia (< 40 mg/dL) Atau Hiperglikemia Berat");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(40, 350, 280, 23);

        Metabolik1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Metabolik1.setSelectedIndex(1);
        Metabolik1.setName("Metabolik1"); // NOI18N
        Metabolik1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metabolik1KeyPressed(evt);
            }
        });
        FormInput.add(Metabolik1);
        Metabolik1.setBounds(306, 350, 80, 23);

        Metabolik3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Metabolik3.setSelectedIndex(1);
        Metabolik3.setName("Metabolik3"); // NOI18N
        Metabolik3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Metabolik3KeyPressed(evt);
            }
        });
        FormInput.add(Metabolik3);
        Metabolik3.setBounds(223, 380, 80, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Asidosis Metabolik Berat (pH < 7,2)");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(40, 380, 190, 23);

        jLabel85.setText(":");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 380, 219, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 410, 810, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 410, 810, 1);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("VI. KONDISI LAIN");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(10, 410, 210, 23);

        jLabel83.setText("Dari Ibu Dengan Riwayat Komplikasi Perinatal Berat :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(425, 430, 280, 23);

        Kondisilain2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kondisilain2.setSelectedIndex(1);
        Kondisilain2.setName("Kondisilain2"); // NOI18N
        Kondisilain2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kondisilain2KeyPressed(evt);
            }
        });
        FormInput.add(Kondisilain2);
        Kondisilain2.setBounds(709, 430, 80, 23);

        jLabel86.setText(":");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(0, 430, 329, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Kelainan Kongenital Berat Memerlukan Stabilisasi Intensif");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(40, 430, 300, 23);

        Kondisilain1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kondisilain1.setSelectedIndex(1);
        Kondisilain1.setName("Kondisilain1"); // NOI18N
        Kondisilain1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kondisilain1KeyPressed(evt);
            }
        });
        FormInput.add(Kondisilain1);
        Kondisilain1.setBounds(333, 430, 80, 23);

        jLabel88.setText("Ikterus Berat Memerlukan Fototerapi Intensif Atau Transfusi Tukar :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(325, 460, 380, 23);

        Kondisilain4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kondisilain4.setSelectedIndex(1);
        Kondisilain4.setName("Kondisilain4"); // NOI18N
        Kondisilain4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kondisilain4KeyPressed(evt);
            }
        });
        FormInput.add(Kondisilain4);
        Kondisilain4.setBounds(709, 460, 80, 23);

        jLabel89.setText(":");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 460, 195, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Pasca Operasi Besar Neonatus");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(40, 460, 160, 23);

        Kondisilain3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Kondisilain3.setSelectedIndex(1);
        Kondisilain3.setName("Kondisilain3"); // NOI18N
        Kondisilain3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kondisilain3KeyPressed(evt);
            }
        });
        FormInput.add(Kondisilain3);
        Kondisilain3.setBounds(199, 460, 80, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 490, 810, 1);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 490, 810, 1);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("VII. KEPUTUSAN & KETERANGAN");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(10, 490, 210, 23);

        jLabel92.setText(":");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(0, 510, 98, 23);

        Keputusan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diterima Di NICU", "Tidak Diterima - Dirawat Di Ruang Perawatan Bayi Lain" }));
        Keputusan.setName("Keputusan"); // NOI18N
        Keputusan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeputusanKeyPressed(evt);
            }
        });
        FormInput.add(Keputusan);
        Keputusan.setBounds(102, 510, 320, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Keputusan");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(40, 510, 160, 23);

        jLabel9.setText("Keterangan/Catatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(415, 510, 130, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(549, 510, 240, 23);

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
                StringBuilder htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Distres Napas Berat (Grunting, Retraksi, Takipnea > 70x/menit)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Apnea Berulang/Apnea Dengan Bradikardia</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kebutuhan CPAP Atau Ventilasi Mekanik</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Saturasi O₂ < 90% Dengan Oksigen Suplementasi</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Berat Lahir < 2000 g (Khususnya < 1500 g : VLBW)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Usia Kehamilan < 35 minggu (Khususnya < 32 minggu)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hipotermia (< 36°C) Yang Tidak Membaik Dengan Penghangatan Biasa</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Syok Refrakter Resusitasi Awal</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bradikardia Berat (< 80x/menit)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sianosis Sentral Menetap</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kejang</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>GCS/Kesadaran Bayi Sangat Rendah</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ensefalopati Hipoksik Iskemik Sedang–Berat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hipoglikemia (< 40 mg/dL) Atau Hiperglikemia Berat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dugaan Atau Terkonfirmasi Sepsis Neonatal</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asidosis Metabolik Berat (pH < 7,2)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelainan Kongenital Berat Memerlukan Stabilisasi Intensif</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dari Ibu Dengan Riwayat Komplikasi Perinatal Berat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasca Operasi Besar Neonatus</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ikterus Berat Memerlukan Fototerapi Intensif Atau Transfusi Tukar</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keputusan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan/Catatan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP/Kode Dokter</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DPJP/Dokter Jaga/IGD</td>").append(
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

                File f = new File("DataChecklistKriteriaMasukNICU.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECK LIST KRITERIA MASUK NICU<br><br></font>"+        
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

    private void MnKriteriaMasukNICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKriteriaMasukNICUActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),29).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),28).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirChecklistKriteriaMasukNICU.jasper","report","::[ Formulir Check List Kriteria Masuk NICU ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_nicu.tanggal,"+
                    "checklist_kriteria_masuk_nicu.respirasi1,checklist_kriteria_masuk_nicu.respirasi2,checklist_kriteria_masuk_nicu.respirasi3,"+
                    "checklist_kriteria_masuk_nicu.respirasi4,checklist_kriteria_masuk_nicu.prematur1,checklist_kriteria_masuk_nicu.prematur2,"+
                    "checklist_kriteria_masuk_nicu.prematur3,checklist_kriteria_masuk_nicu.kardio1,checklist_kriteria_masuk_nicu.kardio2,"+
                    "checklist_kriteria_masuk_nicu.kardio3,checklist_kriteria_masuk_nicu.neuro1,checklist_kriteria_masuk_nicu.neuro2,"+
                    "checklist_kriteria_masuk_nicu.neuro3,checklist_kriteria_masuk_nicu.metabolik1,checklist_kriteria_masuk_nicu.metabolik2,"+
                    "checklist_kriteria_masuk_nicu.metabolik3,checklist_kriteria_masuk_nicu.kondisilain1,checklist_kriteria_masuk_nicu.kondisilain2,"+
                    "checklist_kriteria_masuk_nicu.kondisilain3,checklist_kriteria_masuk_nicu.kondisilain4,checklist_kriteria_masuk_nicu.keputusan,"+
                    "checklist_kriteria_masuk_nicu.keterangan,checklist_kriteria_masuk_nicu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_nicu inner join reg_periksa on checklist_kriteria_masuk_nicu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_nicu.nik "+
                    "where checklist_kriteria_masuk_nicu.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_kriteria_masuk_nicu.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnKriteriaMasukNICUActionPerformed

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
       Valid.pindah(evt,Tanggal,Respirasi1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void Respirasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Respirasi2KeyPressed
        Valid.pindah(evt,Respirasi1,Respirasi3);
    }//GEN-LAST:event_Respirasi2KeyPressed

    private void Respirasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Respirasi1KeyPressed
        Valid.pindah(evt,btnPetugas,Respirasi2);
    }//GEN-LAST:event_Respirasi1KeyPressed

    private void Respirasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Respirasi4KeyPressed
        Valid.pindah(evt,Respirasi3,Prematur1);
    }//GEN-LAST:event_Respirasi4KeyPressed

    private void Respirasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Respirasi3KeyPressed
        Valid.pindah(evt,Respirasi2,Respirasi4);
    }//GEN-LAST:event_Respirasi3KeyPressed

    private void Prematur2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prematur2KeyPressed
        Valid.pindah(evt,Prematur1,Prematur3);
    }//GEN-LAST:event_Prematur2KeyPressed

    private void Prematur1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prematur1KeyPressed
        Valid.pindah(evt,Respirasi4,Prematur2);
    }//GEN-LAST:event_Prematur1KeyPressed

    private void Prematur3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prematur3KeyPressed
        Valid.pindah(evt,Prematur2,Kardio1);
    }//GEN-LAST:event_Prematur3KeyPressed

    private void Kardio3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardio3KeyPressed
        Valid.pindah(evt,Kardio2,Neuro1);
    }//GEN-LAST:event_Kardio3KeyPressed

    private void Kardio1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardio1KeyPressed
        Valid.pindah(evt,Prematur3,Kardio2);
    }//GEN-LAST:event_Kardio1KeyPressed

    private void Kardio2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kardio2KeyPressed
        Valid.pindah(evt,Kardio1,Kardio3);
    }//GEN-LAST:event_Kardio2KeyPressed

    private void Neuro3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Neuro3KeyPressed
        Valid.pindah(evt,Neuro2,Metabolik1);
    }//GEN-LAST:event_Neuro3KeyPressed

    private void Neuro1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Neuro1KeyPressed
        Valid.pindah(evt,Kardio3,Neuro2);
    }//GEN-LAST:event_Neuro1KeyPressed

    private void Neuro2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Neuro2KeyPressed
        Valid.pindah(evt,Neuro1,Neuro3);
    }//GEN-LAST:event_Neuro2KeyPressed

    private void Metabolik2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metabolik2KeyPressed
        Valid.pindah(evt,Metabolik1,Metabolik3);
    }//GEN-LAST:event_Metabolik2KeyPressed

    private void Metabolik1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metabolik1KeyPressed
        Valid.pindah(evt,Neuro3,Metabolik2);
    }//GEN-LAST:event_Metabolik1KeyPressed

    private void Metabolik3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Metabolik3KeyPressed
        Valid.pindah(evt,Metabolik2,Kondisilain1);
    }//GEN-LAST:event_Metabolik3KeyPressed

    private void Kondisilain2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kondisilain2KeyPressed
        Valid.pindah(evt,Kondisilain1,Kondisilain3);
    }//GEN-LAST:event_Kondisilain2KeyPressed

    private void Kondisilain1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kondisilain1KeyPressed
        Valid.pindah(evt,Metabolik3,Kondisilain2);
    }//GEN-LAST:event_Kondisilain1KeyPressed

    private void Kondisilain4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kondisilain4KeyPressed
        Valid.pindah(evt,Kondisilain3,Keputusan);
    }//GEN-LAST:event_Kondisilain4KeyPressed

    private void Kondisilain3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kondisilain3KeyPressed
        Valid.pindah(evt,Kondisilain2,Kondisilain4);
    }//GEN-LAST:event_Kondisilain3KeyPressed

    private void KeputusanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeputusanKeyPressed
        Valid.pindah(evt,Kondisilain4,Keterangan);
    }//GEN-LAST:event_KeputusanKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,Keputusan,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistKriteriaMasukNICU dialog = new RMChecklistKriteriaMasukNICU(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Kardio1;
    private widget.ComboBox Kardio2;
    private widget.ComboBox Kardio3;
    private widget.ComboBox Keputusan;
    private widget.TextBox Keterangan;
    private widget.TextBox KodePetugas;
    private widget.ComboBox Kondisilain1;
    private widget.ComboBox Kondisilain2;
    private widget.ComboBox Kondisilain3;
    private widget.ComboBox Kondisilain4;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Metabolik1;
    private widget.ComboBox Metabolik2;
    private widget.ComboBox Metabolik3;
    private javax.swing.JMenuItem MnKriteriaMasukNICU;
    private widget.TextBox NamaPetugas;
    private widget.ComboBox Neuro1;
    private widget.ComboBox Neuro2;
    private widget.ComboBox Neuro3;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Prematur1;
    private widget.ComboBox Prematur2;
    private widget.ComboBox Prematur3;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_nicu.tanggal,"+
                    "checklist_kriteria_masuk_nicu.respirasi1,checklist_kriteria_masuk_nicu.respirasi2,checklist_kriteria_masuk_nicu.respirasi3,"+
                    "checklist_kriteria_masuk_nicu.respirasi4,checklist_kriteria_masuk_nicu.prematur1,checklist_kriteria_masuk_nicu.prematur2,"+
                    "checklist_kriteria_masuk_nicu.prematur3,checklist_kriteria_masuk_nicu.kardio1,checklist_kriteria_masuk_nicu.kardio2,"+
                    "checklist_kriteria_masuk_nicu.kardio3,checklist_kriteria_masuk_nicu.neuro1,checklist_kriteria_masuk_nicu.neuro2,"+
                    "checklist_kriteria_masuk_nicu.neuro3,checklist_kriteria_masuk_nicu.metabolik1,checklist_kriteria_masuk_nicu.metabolik2,"+
                    "checklist_kriteria_masuk_nicu.metabolik3,checklist_kriteria_masuk_nicu.kondisilain1,checklist_kriteria_masuk_nicu.kondisilain2,"+
                    "checklist_kriteria_masuk_nicu.kondisilain3,checklist_kriteria_masuk_nicu.kondisilain4,checklist_kriteria_masuk_nicu.keputusan,"+
                    "checklist_kriteria_masuk_nicu.keterangan,checklist_kriteria_masuk_nicu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_nicu inner join reg_periksa on checklist_kriteria_masuk_nicu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_nicu.nik "+
                    "where checklist_kriteria_masuk_nicu.tanggal between ? and ? order by checklist_kriteria_masuk_nicu.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_masuk_nicu.tanggal,"+
                    "checklist_kriteria_masuk_nicu.respirasi1,checklist_kriteria_masuk_nicu.respirasi2,checklist_kriteria_masuk_nicu.respirasi3,"+
                    "checklist_kriteria_masuk_nicu.respirasi4,checklist_kriteria_masuk_nicu.prematur1,checklist_kriteria_masuk_nicu.prematur2,"+
                    "checklist_kriteria_masuk_nicu.prematur3,checklist_kriteria_masuk_nicu.kardio1,checklist_kriteria_masuk_nicu.kardio2,"+
                    "checklist_kriteria_masuk_nicu.kardio3,checklist_kriteria_masuk_nicu.neuro1,checklist_kriteria_masuk_nicu.neuro2,"+
                    "checklist_kriteria_masuk_nicu.neuro3,checklist_kriteria_masuk_nicu.metabolik1,checklist_kriteria_masuk_nicu.metabolik2,"+
                    "checklist_kriteria_masuk_nicu.metabolik3,checklist_kriteria_masuk_nicu.kondisilain1,checklist_kriteria_masuk_nicu.kondisilain2,"+
                    "checklist_kriteria_masuk_nicu.kondisilain3,checklist_kriteria_masuk_nicu.kondisilain4,checklist_kriteria_masuk_nicu.keputusan,"+
                    "checklist_kriteria_masuk_nicu.keterangan,checklist_kriteria_masuk_nicu.nik,pegawai.nama "+
                    "from checklist_kriteria_masuk_nicu inner join reg_periksa on checklist_kriteria_masuk_nicu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_masuk_nicu.nik "+
                    "where checklist_kriteria_masuk_nicu.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or pegawai.nama like ? or checklist_kriteria_masuk_nicu.nik like ?) order by checklist_kriteria_masuk_nicu.tanggal ");
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
                        rs.getString("tanggal"),rs.getString("respirasi1"),rs.getString("respirasi2"),rs.getString("respirasi3"),rs.getString("respirasi4"),
                        rs.getString("prematur1"),rs.getString("prematur2"),rs.getString("prematur3"),rs.getString("kardio1"),rs.getString("kardio2"),
                        rs.getString("kardio3"),rs.getString("neuro1"),rs.getString("neuro2"),rs.getString("neuro3"),rs.getString("metabolik1"),
                        rs.getString("metabolik2"),rs.getString("metabolik3"),rs.getString("kondisilain1"),rs.getString("kondisilain2"),
                        rs.getString("kondisilain3"),rs.getString("kondisilain4"),rs.getString("keputusan"),rs.getString("keterangan"),
                        rs.getString("nik"),rs.getString("nama")
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
        Respirasi1.setSelectedItem("Tidak");
        Respirasi2.setSelectedItem("Tidak");
        Respirasi3.setSelectedItem("Tidak");
        Respirasi4.setSelectedItem("Tidak");
        Prematur1.setSelectedItem("Tidak");
        Prematur2.setSelectedItem("Tidak");
        Prematur3.setSelectedItem("Tidak");
        Kardio1.setSelectedItem("Tidak");
        Kardio2.setSelectedItem("Tidak");
        Kardio3.setSelectedItem("Tidak");
        Neuro1.setSelectedItem("Tidak");
        Neuro2.setSelectedItem("Tidak");
        Neuro3.setSelectedItem("Tidak");
        Metabolik1.setSelectedItem("Tidak");
        Metabolik2.setSelectedItem("Tidak");
        Metabolik3.setSelectedItem("Tidak");
        Kondisilain1.setSelectedItem("Tidak");
        Kondisilain2.setSelectedItem("Tidak");
        Kondisilain3.setSelectedItem("Tidak");
        Kondisilain4.setSelectedItem("Tidak");
        Keputusan.setSelectedItem("Tidak");
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        Respirasi1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Respirasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Respirasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Respirasi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Respirasi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Prematur1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Prematur2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Prematur3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Kardio1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Kardio2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Kardio3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Neuro1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Neuro2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Neuro3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Metabolik1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Metabolik2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Metabolik3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Kondisilain1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Kondisilain2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Kondisilain3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Kondisilain4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Keputusan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
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
        BtnSimpan.setEnabled(akses.getkriteria_masuk_nicu());
        BtnHapus.setEnabled(akses.getkriteria_masuk_nicu());
        BtnEdit.setEnabled(akses.getkriteria_masuk_nicu());
        BtnPrint.setEnabled(akses.getkriteria_masuk_nicu()); 
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
        if(Sequel.mengedittf("checklist_kriteria_masuk_nicu","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,respirasi1=?,respirasi2=?,respirasi3=?,respirasi4=?,prematur1=?,prematur2=?,"+
                "prematur3=?,kardio1=?,kardio2=?,kardio3=?,neuro1=?,neuro2=?,neuro3=?,metabolik1=?,metabolik2=?,metabolik3=?,kondisilain1=?,kondisilain2=?,kondisilain3=?,kondisilain4=?,"+
                "keputusan=?,keterangan=?,nik=?",27,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Respirasi1.getSelectedItem().toString(),
                Respirasi2.getSelectedItem().toString(),Respirasi3.getSelectedItem().toString(),Respirasi4.getSelectedItem().toString(),Prematur1.getSelectedItem().toString(),
                Prematur2.getSelectedItem().toString(),Prematur3.getSelectedItem().toString(),Kardio1.getSelectedItem().toString(),Kardio2.getSelectedItem().toString(),
                Kardio3.getSelectedItem().toString(),Neuro1.getSelectedItem().toString(),Neuro2.getSelectedItem().toString(),Neuro3.getSelectedItem().toString(),
                Metabolik1.getSelectedItem().toString(),Metabolik2.getSelectedItem().toString(),Metabolik3.getSelectedItem().toString(),Kondisilain1.getSelectedItem().toString(),
                Kondisilain2.getSelectedItem().toString(),Kondisilain3.getSelectedItem().toString(),Kondisilain4.getSelectedItem().toString(),Keputusan.getSelectedItem().toString(),
                Keterangan.getText(),KodePetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(Respirasi1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Respirasi2.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(Respirasi3.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Respirasi4.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Prematur1.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Prematur2.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Prematur3.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Kardio1.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(Kardio2.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(Kardio3.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(Neuro1.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(Neuro2.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(Neuro3.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(Metabolik1.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(Metabolik2.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(Metabolik3.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(Kondisilain1.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(Kondisilain2.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(Kondisilain3.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(Kondisilain4.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(Keputusan.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),29);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_kriteria_masuk_nicu where no_rawat=? and tanggal=?",2,new String[]{
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
        if(Sequel.menyimpantf("checklist_kriteria_masuk_nicu","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",25,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Respirasi1.getSelectedItem().toString(),
            Respirasi2.getSelectedItem().toString(),Respirasi3.getSelectedItem().toString(),Respirasi4.getSelectedItem().toString(),Prematur1.getSelectedItem().toString(),
            Prematur2.getSelectedItem().toString(),Prematur3.getSelectedItem().toString(),Kardio1.getSelectedItem().toString(),Kardio2.getSelectedItem().toString(),
            Kardio3.getSelectedItem().toString(),Neuro1.getSelectedItem().toString(),Neuro2.getSelectedItem().toString(),Neuro3.getSelectedItem().toString(),
            Metabolik1.getSelectedItem().toString(),Metabolik2.getSelectedItem().toString(),Metabolik3.getSelectedItem().toString(),Kondisilain1.getSelectedItem().toString(),
            Kondisilain2.getSelectedItem().toString(),Kondisilain3.getSelectedItem().toString(),Kondisilain4.getSelectedItem().toString(),Keputusan.getSelectedItem().toString(),
            Keterangan.getText(),KodePetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                Respirasi1.getSelectedItem().toString(),Respirasi2.getSelectedItem().toString(),Respirasi3.getSelectedItem().toString(),Respirasi4.getSelectedItem().toString(),
                Prematur1.getSelectedItem().toString(),Prematur2.getSelectedItem().toString(),Prematur3.getSelectedItem().toString(),Kardio1.getSelectedItem().toString(),
                Kardio2.getSelectedItem().toString(),Kardio3.getSelectedItem().toString(),Neuro1.getSelectedItem().toString(),Neuro2.getSelectedItem().toString(),Neuro3.getSelectedItem().toString(),
                Metabolik1.getSelectedItem().toString(),Metabolik2.getSelectedItem().toString(),Metabolik3.getSelectedItem().toString(),Kondisilain1.getSelectedItem().toString(),
                Kondisilain2.getSelectedItem().toString(),Kondisilain3.getSelectedItem().toString(),Kondisilain4.getSelectedItem().toString(),Keputusan.getSelectedItem().toString(),
                Keterangan.getText(),KodePetugas.getText(),NamaPetugas.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }
    }
}

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
public final class RMSkriningMerokokUsiaSekolahRemaja extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private MasterCariSekolah sekolah=new MasterCariSekolah(null,false);
    private String finger="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningMerokokUsiaSekolahRemaja(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Umur","Kelas","Kode Sekolah","Asal Sekolah",
                "Kode Petugas","Nama Petugas","Tanggal","Apakah Anda Merokok","Jml.Rokok","Satuan Rokok", 
                "Jenis Rokok Yang Digunakan","Keterangan Jenis Rokok","Usia Merokok","Alasan Mulai Merokok", 
                "Keterangan Alasan Mulai Merokok","Lama Merokok","Biasanya Mendapatkan Rokok","Keterangan Biasanya Mendapatkan Rokok", 
                "Ingin Berhenti","Alasan Ingin Berhenti","Keterangan Alasan Ingin Berhenti","Tahu Dampak Kesehatan Merokok", 
                "Dampak Merokok Yang Diketahui","Pintu Masuk Narkoba","Melihat Orang Merokok Di Sekolah","Paling Sering Merokok Di Sekolah", 
                "Keterangan Paling Sering Merokok Di Sekolah","Anggota Keluarga Di Rumah Merokok","Teman Dekat Merokok", 
                "Pemeriksaan Kadar CO", "Hasil Pemeriksaan CO Pernapasan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 36; i++) {
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
                column.setPreferredWidth(30);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(40);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(170);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(115);
            }else if(i==12){
                column.setPreferredWidth(200);
            }else if(i==13){
                column.setPreferredWidth(65);
            }else if(i==14){
                column.setPreferredWidth(75);
            }else if(i==15){
                column.setPreferredWidth(300);
            }else if(i==16){
                column.setPreferredWidth(170);
            }else if(i==17){
                column.setPreferredWidth(77);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(180);
            }else if(i==20){
                column.setPreferredWidth(80);
            }else if(i==21){
                column.setPreferredWidth(160);
            }else if(i==22){
                column.setPreferredWidth(214);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(180);
            }else if(i==26){
                column.setPreferredWidth(176);
            }else if(i==27){
                column.setPreferredWidth(170);
            }else if(i==28){
                column.setPreferredWidth(110);
            }else if(i==29){
                column.setPreferredWidth(165);
            }else if(i==30){
                column.setPreferredWidth(170);
            }else if(i==31){
                column.setPreferredWidth(230);
            }else if(i==32){
                column.setPreferredWidth(190);
            }else if(i==33){
                column.setPreferredWidth(120);
            }else if(i==34){
                column.setPreferredWidth(120);
            }else if(i==35){
                column.setPreferredWidth(175);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
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
        
        sekolah.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(sekolah.getTable().getSelectedRow()!= -1){                   
                    KdAsalSekolah.setText(sekolah.getTable().getValueAt(sekolah.getTable().getSelectedRow(),0).toString());
                    NmAsalSekolah.setText(sekolah.getTable().getValueAt(sekolah.getTable().getSelectedRow(),1).toString());
                }  
                btnAsalSekolah.requestFocus();
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
        MnSkriningMerokok = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
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
        jLabel12 = new widget.Label();
        Umur = new widget.TextBox();
        Kelas = new widget.ComboBox();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel20 = new widget.Label();
        KdAsalSekolah = new widget.TextBox();
        btnAsalSekolah = new widget.Button();
        NmAsalSekolah = new widget.TextBox();
        ApakahAndaMerokok = new widget.ComboBox();
        jLabel15 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        SatuanRokok = new widget.ComboBox();
        JumlahRokok = new widget.TextBox();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        KeteranganJenisRokokDigunakan = new widget.TextBox();
        JenisRokokDigunakan = new widget.ComboBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        UsiaMulaiMerokok = new widget.TextBox();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        AlasanUtamaMerokok = new widget.ComboBox();
        KeteranganAlasanUtamaMerokok = new widget.TextBox();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        LamaMerokok = new widget.TextBox();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        CaraMendapatkanRokok = new widget.ComboBox();
        KeteranganCaraMendapatkanRokok = new widget.TextBox();
        jLabel91 = new widget.Label();
        jLabel92 = new widget.Label();
        KeinginanBerhentiMerokok = new widget.ComboBox();
        KeteranganAlasanUtamaBerhentiMerokok = new widget.TextBox();
        AlasanUtamaBerhentiMerokok = new widget.ComboBox();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        jLabel95 = new widget.Label();
        jLabel96 = new widget.Label();
        TahuDampakKesehatanMerokok = new widget.ComboBox();
        jLabel97 = new widget.Label();
        jLabel98 = new widget.Label();
        DampakKesehatanMerokok = new widget.ComboBox();
        jLabel101 = new widget.Label();
        jLabel102 = new widget.Label();
        PintuMasukNarkoba = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        MerokokDiPendidikan = new widget.ComboBox();
        jLabel106 = new widget.Label();
        jLabel107 = new widget.Label();
        YangPalingSeringMerokokDiSekolah = new widget.ComboBox();
        KeteranganYangPalingSeringMerokokDiSekolah = new widget.TextBox();
        jLabel108 = new widget.Label();
        jLabel109 = new widget.Label();
        AnggotaKeluargaMerokok = new widget.ComboBox();
        jLabel110 = new widget.Label();
        TemanDekatMerokok = new widget.ComboBox();
        jLabel111 = new widget.Label();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel112 = new widget.Label();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        DilakukanPemeriksaanCO = new widget.ComboBox();
        jLabel115 = new widget.Label();
        HasilPemeriksaanCO = new widget.TextBox();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningMerokok.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningMerokok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningMerokok.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningMerokok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningMerokok.setText("Formulir Skrining Merokok Usia Sekolah & Remaja");
        MnSkriningMerokok.setName("MnSkriningMerokok"); // NOI18N
        MnSkriningMerokok.setPreferredSize(new java.awt.Dimension(300, 26));
        MnSkriningMerokok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningMerokokActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningMerokok);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kuesioner Skrining Perilaku Merokok Bagi Anak Usia Sekolah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-07-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-07-2024" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 446));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-07-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 70, 90, 23);

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
        jLabel16.setBounds(0, 70, 75, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 70, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 70, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 70, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 70, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 70, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 70, 94, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(570, 70, 187, 23);

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
        btnPetugas.setBounds(761, 70, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel12.setText("Umur :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 40, 75, 23);

        Umur.setEditable(false);
        Umur.setFocusTraversalPolicyProvider(true);
        Umur.setName("Umur"); // NOI18N
        Umur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurKeyPressed(evt);
            }
        });
        FormInput.add(Umur);
        Umur.setBounds(79, 40, 50, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        Kelas.setName("Kelas"); // NOI18N
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormInput.add(Kelas);
        Kelas.setBounds(303, 40, 62, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("a.");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(44, 120, 20, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Apakah Anda merokok ? (Bila jawaban 3 dan 4, langsung ke bagian Bagian II)");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(57, 120, 430, 23);

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(187, 40, 48, 23);

        jLabel14.setText("Kelas :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(259, 40, 40, 23);

        jLabel20.setText("Asal Sekolah :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(380, 40, 90, 23);

        KdAsalSekolah.setEditable(false);
        KdAsalSekolah.setHighlighter(null);
        KdAsalSekolah.setName("KdAsalSekolah"); // NOI18N
        FormInput.add(KdAsalSekolah);
        KdAsalSekolah.setBounds(474, 40, 51, 23);

        btnAsalSekolah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAsalSekolah.setMnemonic('2');
        btnAsalSekolah.setToolTipText("ALt+2");
        btnAsalSekolah.setName("btnAsalSekolah"); // NOI18N
        btnAsalSekolah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsalSekolahActionPerformed(evt);
            }
        });
        btnAsalSekolah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAsalSekolahKeyPressed(evt);
            }
        });
        FormInput.add(btnAsalSekolah);
        btnAsalSekolah.setBounds(761, 40, 28, 23);

        NmAsalSekolah.setEditable(false);
        NmAsalSekolah.setName("NmAsalSekolah"); // NOI18N
        FormInput.add(NmAsalSekolah);
        NmAsalSekolah.setBounds(527, 40, 230, 23);

        ApakahAndaMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya, Setiap Hari", "Ya, Kadang-kadang", "Pernah Mencoba Walau Hanya 1 Hisapan", "Tidak Merokok/Tidak Pernah Mencoba" }));
        ApakahAndaMerokok.setName("ApakahAndaMerokok"); // NOI18N
        ApakahAndaMerokok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ApakahAndaMerokokItemStateChanged(evt);
            }
        });
        ApakahAndaMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ApakahAndaMerokokKeyPressed(evt);
            }
        });
        FormInput.add(ApakahAndaMerokok);
        ApakahAndaMerokok.setBounds(539, 120, 250, 23);

        jLabel15.setText("J.K. :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(143, 40, 40, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 807, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. PERILAKU MEROKOK");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 100, 200, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("b.");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(44, 150, 20, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Berapa jumlah batang rokok yang Anda hisap setiap hari atau minggu ?");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(57, 150, 470, 23);

        SatuanRokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Batang/Hari", "Batang/Minggu", " " }));
        SatuanRokok.setName("SatuanRokok"); // NOI18N
        SatuanRokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SatuanRokokKeyPressed(evt);
            }
        });
        FormInput.add(SatuanRokok);
        SatuanRokok.setBounds(669, 150, 120, 23);

        JumlahRokok.setHighlighter(null);
        JumlahRokok.setName("JumlahRokok"); // NOI18N
        JumlahRokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahRokokKeyPressed(evt);
            }
        });
        FormInput.add(JumlahRokok);
        JumlahRokok.setBounds(616, 150, 50, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Jika jawaban a adalah 1 dan 2, jenis rokok yang digunakan ?");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(57, 180, 310, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("c.");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 180, 20, 23);

        KeteranganJenisRokokDigunakan.setHighlighter(null);
        KeteranganJenisRokokDigunakan.setName("KeteranganJenisRokokDigunakan"); // NOI18N
        KeteranganJenisRokokDigunakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganJenisRokokDigunakanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganJenisRokokDigunakan);
        KeteranganJenisRokokDigunakan.setBounds(699, 180, 90, 23);

        JenisRokokDigunakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rokok Konvensional : Rokok Filter/Putih, Kretek, Tingwe, dll", "Rokok Elektronik : Vape, IQOS, dll", "Keduanya", "Lainnya", " " }));
        JenisRokokDigunakan.setName("JenisRokokDigunakan"); // NOI18N
        JenisRokokDigunakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisRokokDigunakanKeyPressed(evt);
            }
        });
        FormInput.add(JenisRokokDigunakan);
        JenisRokokDigunakan.setBounds(356, 180, 340, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Berapa usia Anda mulai merokok ?");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(57, 210, 310, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("d.");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(44, 210, 20, 23);

        UsiaMulaiMerokok.setHighlighter(null);
        UsiaMulaiMerokok.setName("UsiaMulaiMerokok"); // NOI18N
        UsiaMulaiMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaMulaiMerokokKeyPressed(evt);
            }
        });
        FormInput.add(UsiaMulaiMerokok);
        UsiaMulaiMerokok.setBounds(699, 210, 57, 23);

        jLabel83.setText("Tahun");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(749, 210, 40, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("e.");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(44, 240, 20, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Apa alasan utama Anda mulai merokok ?");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(57, 240, 310, 23);

        AlasanUtamaMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ikut-ikutan Teman", "Pengaruh Keluarga", "Rasa Ingin Tahu", "Terpaksa Oleh Teman/Lingkungan", "Mengisi Waktu Luang", "Menghilangkan Stress", "Lainnya", " " }));
        AlasanUtamaMerokok.setName("AlasanUtamaMerokok"); // NOI18N
        AlasanUtamaMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanUtamaMerokokKeyPressed(evt);
            }
        });
        FormInput.add(AlasanUtamaMerokok);
        AlasanUtamaMerokok.setBounds(441, 240, 215, 23);

        KeteranganAlasanUtamaMerokok.setHighlighter(null);
        KeteranganAlasanUtamaMerokok.setName("KeteranganAlasanUtamaMerokok"); // NOI18N
        KeteranganAlasanUtamaMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAlasanUtamaMerokokKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAlasanUtamaMerokok);
        KeteranganAlasanUtamaMerokok.setBounds(659, 240, 130, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Sudah berapa lama Anda merokok ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(57, 270, 310, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("f.");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(44, 270, 20, 23);

        LamaMerokok.setHighlighter(null);
        LamaMerokok.setName("LamaMerokok"); // NOI18N
        LamaMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaMerokokKeyPressed(evt);
            }
        });
        FormInput.add(LamaMerokok);
        LamaMerokok.setBounds(702, 270, 57, 23);

        jLabel88.setText("Bulan");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(749, 270, 40, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("g.");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(44, 300, 20, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Bagaimana biasanya (paling sering) kamu mendapatkan rokok ?");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(57, 300, 340, 23);

        CaraMendapatkanRokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Beli Batangan", "Beli Bungkusan", "Dapat Dari Teman/Saudara/Keluarga", "Lainnya", " " }));
        CaraMendapatkanRokok.setName("CaraMendapatkanRokok"); // NOI18N
        CaraMendapatkanRokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraMendapatkanRokokKeyPressed(evt);
            }
        });
        FormInput.add(CaraMendapatkanRokok);
        CaraMendapatkanRokok.setBounds(426, 300, 230, 23);

        KeteranganCaraMendapatkanRokok.setHighlighter(null);
        KeteranganCaraMendapatkanRokok.setName("KeteranganCaraMendapatkanRokok"); // NOI18N
        KeteranganCaraMendapatkanRokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganCaraMendapatkanRokokKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganCaraMendapatkanRokok);
        KeteranganCaraMendapatkanRokok.setBounds(659, 300, 130, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Apakah ada keinginan Anda untuk berhenti merokok ? (Jika Tidak, langsung ke Bagian II)");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(57, 330, 480, 23);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("h.");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(44, 330, 20, 23);

        KeinginanBerhentiMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak", " " }));
        KeinginanBerhentiMerokok.setName("KeinginanBerhentiMerokok"); // NOI18N
        KeinginanBerhentiMerokok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KeinginanBerhentiMerokokItemStateChanged(evt);
            }
        });
        KeinginanBerhentiMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeinginanBerhentiMerokokKeyPressed(evt);
            }
        });
        FormInput.add(KeinginanBerhentiMerokok);
        KeinginanBerhentiMerokok.setBounds(709, 330, 80, 23);

        KeteranganAlasanUtamaBerhentiMerokok.setHighlighter(null);
        KeteranganAlasanUtamaBerhentiMerokok.setName("KeteranganAlasanUtamaBerhentiMerokok"); // NOI18N
        KeteranganAlasanUtamaBerhentiMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAlasanUtamaBerhentiMerokokKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAlasanUtamaBerhentiMerokok);
        KeteranganAlasanUtamaBerhentiMerokok.setBounds(659, 360, 130, 23);

        AlasanUtamaBerhentiMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kondisi Kesehatan", "Motivasi Diri Sendiri", "Disarankan Orangtua/Guru/Teman", "Tidak Mampu Beli/Mahal", "Lainnya", " " }));
        AlasanUtamaBerhentiMerokok.setName("AlasanUtamaBerhentiMerokok"); // NOI18N
        AlasanUtamaBerhentiMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanUtamaBerhentiMerokokKeyPressed(evt);
            }
        });
        FormInput.add(AlasanUtamaBerhentiMerokok);
        AlasanUtamaBerhentiMerokok.setBounds(438, 360, 218, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("Jika Ya apa alasan utama Anda mau berhenti merokok ?");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(57, 360, 340, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("i.");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(44, 360, 20, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 390, 807, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("II. PENGETAHUAN TENTANG ROKOK");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 390, 260, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("a.");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(44, 410, 20, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("Apakah Anda tahu dampak kesehatan dari merokok ?");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(57, 410, 480, 23);

        TahuDampakKesehatanMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TahuDampakKesehatanMerokok.setName("TahuDampakKesehatanMerokok"); // NOI18N
        TahuDampakKesehatanMerokok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TahuDampakKesehatanMerokokItemStateChanged(evt);
            }
        });
        TahuDampakKesehatanMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TahuDampakKesehatanMerokokKeyPressed(evt);
            }
        });
        FormInput.add(TahuDampakKesehatanMerokok);
        TahuDampakKesehatanMerokok.setBounds(709, 410, 80, 23);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("Jika Ya, pilihlah menurut Anda dampak kesehatan dari merokok yang Anda ketahui ?");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(57, 440, 480, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("b.");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(44, 440, 20, 23);

        DampakKesehatanMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kecanduan", "Batuk", "Gigi Kuning & Mulut Berbau", "Sistem Pernapasan Terganggu", " " }));
        DampakKesehatanMerokok.setName("DampakKesehatanMerokok"); // NOI18N
        DampakKesehatanMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DampakKesehatanMerokokKeyPressed(evt);
            }
        });
        FormInput.add(DampakKesehatanMerokok);
        DampakKesehatanMerokok.setBounds(589, 440, 200, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("Apakah Anda tahu merokok menjadi pintu masuk penggunaan narkoba ?");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(57, 470, 480, 23);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("c.");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(44, 470, 20, 23);

        PintuMasukNarkoba.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PintuMasukNarkoba.setName("PintuMasukNarkoba"); // NOI18N
        PintuMasukNarkoba.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PintuMasukNarkobaKeyPressed(evt);
            }
        });
        FormInput.add(PintuMasukNarkoba);
        PintuMasukNarkoba.setBounds(709, 470, 80, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 500, 807, 1);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("III. SUMBER PAPARAN PERILAKU MEROKOK");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 500, 260, 23);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Apakah Anda pernah melihat orang yang merokok di satuan pendidikan?");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(57, 520, 480, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("a.");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(44, 520, 20, 23);

        MerokokDiPendidikan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MerokokDiPendidikan.setName("MerokokDiPendidikan"); // NOI18N
        MerokokDiPendidikan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MerokokDiPendidikanItemStateChanged(evt);
            }
        });
        MerokokDiPendidikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerokokDiPendidikanKeyPressed(evt);
            }
        });
        FormInput.add(MerokokDiPendidikan);
        MerokokDiPendidikan.setBounds(709, 520, 80, 23);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("b.");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(44, 550, 20, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("Jika Ya sebutkan orang yang paling sering Anda lihat merokok di satuan pendidikan !");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(57, 550, 430, 23);

        YangPalingSeringMerokokDiSekolah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teman/Kakak Kelas", "Guru/Kepala Sekolah", "Satpam/Supir/Penjaga Kantin", "Lainnya", " " }));
        YangPalingSeringMerokokDiSekolah.setName("YangPalingSeringMerokokDiSekolah"); // NOI18N
        YangPalingSeringMerokokDiSekolah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YangPalingSeringMerokokDiSekolahKeyPressed(evt);
            }
        });
        FormInput.add(YangPalingSeringMerokokDiSekolah);
        YangPalingSeringMerokokDiSekolah.setBounds(476, 550, 195, 23);

        KeteranganYangPalingSeringMerokokDiSekolah.setHighlighter(null);
        KeteranganYangPalingSeringMerokokDiSekolah.setName("KeteranganYangPalingSeringMerokokDiSekolah"); // NOI18N
        KeteranganYangPalingSeringMerokokDiSekolah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganYangPalingSeringMerokokDiSekolahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganYangPalingSeringMerokokDiSekolah);
        KeteranganYangPalingSeringMerokokDiSekolah.setBounds(674, 550, 115, 23);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("c.");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(44, 580, 20, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("Apakah ada anggota keluarga di rumah yang merokok ?");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(57, 580, 480, 23);

        AnggotaKeluargaMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        AnggotaKeluargaMerokok.setName("AnggotaKeluargaMerokok"); // NOI18N
        AnggotaKeluargaMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnggotaKeluargaMerokokKeyPressed(evt);
            }
        });
        FormInput.add(AnggotaKeluargaMerokok);
        AnggotaKeluargaMerokok.setBounds(709, 580, 80, 23);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("Apakah teman-teman dekatmu banyak yang merokok ?");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(57, 610, 480, 23);

        TemanDekatMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TemanDekatMerokok.setName("TemanDekatMerokok"); // NOI18N
        TemanDekatMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TemanDekatMerokokKeyPressed(evt);
            }
        });
        FormInput.add(TemanDekatMerokok);
        TemanDekatMerokok.setBounds(709, 610, 80, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("d.");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(44, 610, 20, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 640, 807, 1);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("IV. PEMERIKSAAN KADAR CO PERNAPASAN");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(10, 640, 380, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("a.");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(44, 660, 20, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Apakah dilakukan pemeriksaan kadar CO pernapasan?");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(57, 660, 480, 23);

        DilakukanPemeriksaanCO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak, Alat & BMHP Tidak Tersedia", "Tidak, BMHP Tidak Tersedia" }));
        DilakukanPemeriksaanCO.setName("DilakukanPemeriksaanCO"); // NOI18N
        DilakukanPemeriksaanCO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DilakukanPemeriksaanCOKeyPressed(evt);
            }
        });
        FormInput.add(DilakukanPemeriksaanCO);
        DilakukanPemeriksaanCO.setBounds(571, 660, 218, 23);

        jLabel115.setText("Ppm");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(749, 690, 40, 23);

        HasilPemeriksaanCO.setHighlighter(null);
        HasilPemeriksaanCO.setName("HasilPemeriksaanCO"); // NOI18N
        HasilPemeriksaanCO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilPemeriksaanCOKeyPressed(evt);
            }
        });
        FormInput.add(HasilPemeriksaanCO);
        HasilPemeriksaanCO.setBounds(708, 690, 57, 23);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Hasil pemeriksaan kadar CO pernapasan");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(57, 690, 310, 23);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("b.");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(44, 690, 20, 23);

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
            Valid.textKosong(btnPetugas,"Petugas");
        }else if(KdAsalSekolah.getText().trim().equals("")||NmAsalSekolah.getText().trim().equals("")){
            Valid.textKosong(btnAsalSekolah,"Petugas");
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
            Valid.pindah(evt,HasilPemeriksaanCO,BtnBatal);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString(),Sequel.ambiltanggalsekarang())==true){
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
            Valid.textKosong(btnPetugas,"Petugas");
        }else if(KdAsalSekolah.getText().trim().equals("")||NmAsalSekolah.getText().trim().equals("")){
            Valid.textKosong(btnAsalSekolah,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sekolah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asal Sekolah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Apakah Anda Merokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Rokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Satuan Rokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Rokok Yang Digunakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Jenis Rokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Usia Merokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alasan Mulai Merokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Alasan Mulai Merokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lama Merokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Biasanya Mendapatkan Rokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Biasanya Mendapatkan Rokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ingin Berhenti</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alasan Ingin Berhenti</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Alasan Ingin Berhenti</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tahu Dampak Kesehatan Merokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dampak Merokok Yang Diketahui</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pintu Masuk Narkoba</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Melihat Orang Merokok Di Sekolah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Paling Sering Merokok Di Sekolah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Paling Sering Merokok Di Sekolah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anggota Keluarga Di Rumah Merokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Teman Dekat Merokok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Kadar CO</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Pemeriksaan CO Pernapasan</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataSkriningMerokokUsiaSekolahRemaja.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SEKRINING PERILAKU MEROKOK BAGI USIA SEKOLAH DAN REMAJA<br><br></font>"+        
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
        Valid.pindah(evt,Detik,Umur);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningMerokokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningMerokokActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),9).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirSkriningMerokokUsiaRemaja.jasper","report","::[ Formulir Skrining Perilaku Merokok Bagi Anak Usia Sekolah ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "skrining_perilaku_merokok_sekolah_remaja.kelas,skrining_perilaku_merokok_sekolah_remaja.kd_sekolah,master_sekolah.nm_sekolah,"+
                    "skrining_perilaku_merokok_sekolah_remaja.nip,petugas.nama,skrining_perilaku_merokok_sekolah_remaja.tanggal,"+
                    "skrining_perilaku_merokok_sekolah_remaja.apakah_anda_merokok,skrining_perilaku_merokok_sekolah_remaja.jumlah_batang_rokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.jumlah_batang_rokok_hariminggu,skrining_perilaku_merokok_sekolah_remaja.jenis_rokok_yang_digunakan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.jenis_rokok_yang_digunakan_keterangan,skrining_perilaku_merokok_sekolah_remaja.usia_mulai_merokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.alasan_mulai_merokok,skrining_perilaku_merokok_sekolah_remaja.alasan_mulai_merokok_keterangan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.sudah_berapa_lama_merokok,skrining_perilaku_merokok_sekolah_remaja.bagaimana_biasanya_mendapatkan_rokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.bagaimana_biasanya_mendapatkan_rokok_keterangan,skrining_perilaku_merokok_sekolah_remaja.keinginan_berhenti_merokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.alasan_utama_berhenti_merokok,skrining_perilaku_merokok_sekolah_remaja.alasan_utama_berhenti_merokok_keterangan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.tahu_dampak_kesehatan_merokok,skrining_perilaku_merokok_sekolah_remaja.dampak_kesehatan_dari_merokok_yang_diketahui,"+
                    "skrining_perilaku_merokok_sekolah_remaja.tahu_merokok_pintu_masuk_narkoba,skrining_perilaku_merokok_sekolah_remaja.melihat_orang_merokok_di_sekolah,"+
                    "skrining_perilaku_merokok_sekolah_remaja.orang_yang_paling_sering_merokok_disekolah,skrining_perilaku_merokok_sekolah_remaja.orang_yang_paling_sering_merokok_disekolah_keterangan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.ada_anggota_keluarga_di_rumah_yang_merokok,skrining_perilaku_merokok_sekolah_remaja.teman_dekat_banyakyang_merokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.dilakukan_pemeriksaan_kadar_co_pernapasan,skrining_perilaku_merokok_sekolah_remaja.hasil_pemeriksaan_co_pernapasan "+
                    "from skrining_perilaku_merokok_sekolah_remaja inner join reg_periksa on skrining_perilaku_merokok_sekolah_remaja.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_perilaku_merokok_sekolah_remaja.nip=petugas.nip "+
                    "inner join master_sekolah on master_sekolah.kd_sekolah=skrining_perilaku_merokok_sekolah_remaja.kd_sekolah "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningMerokokActionPerformed

    private void UmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurKeyPressed
        //Valid.pindah(evt,btnPetugas,TBPB);
    }//GEN-LAST:event_UmurKeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt,TCari,btnAsalSekolah);
    }//GEN-LAST:event_KelasKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void btnAsalSekolahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsalSekolahActionPerformed
        sekolah.emptTeks();
        sekolah.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        sekolah.setLocationRelativeTo(internalFrame1);
        sekolah.setVisible(true);
    }//GEN-LAST:event_btnAsalSekolahActionPerformed

    private void btnAsalSekolahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAsalSekolahKeyPressed
        Valid.pindah(evt,Kelas,ApakahAndaMerokok);
    }//GEN-LAST:event_btnAsalSekolahKeyPressed

    private void JumlahRokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahRokokKeyPressed
        Valid.pindah(evt,ApakahAndaMerokok,SatuanRokok);
    }//GEN-LAST:event_JumlahRokokKeyPressed

    private void KeteranganJenisRokokDigunakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganJenisRokokDigunakanKeyPressed
        Valid.pindah(evt,JenisRokokDigunakan,UsiaMulaiMerokok);
    }//GEN-LAST:event_KeteranganJenisRokokDigunakanKeyPressed

    private void UsiaMulaiMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaMulaiMerokokKeyPressed
        Valid.pindah(evt,KeteranganJenisRokokDigunakan,AlasanUtamaMerokok);
    }//GEN-LAST:event_UsiaMulaiMerokokKeyPressed

    private void KeteranganAlasanUtamaMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAlasanUtamaMerokokKeyPressed
        Valid.pindah(evt,AlasanUtamaMerokok,LamaMerokok);
    }//GEN-LAST:event_KeteranganAlasanUtamaMerokokKeyPressed

    private void LamaMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaMerokokKeyPressed
        Valid.pindah(evt,KeteranganAlasanUtamaMerokok,CaraMendapatkanRokok);
    }//GEN-LAST:event_LamaMerokokKeyPressed

    private void KeteranganCaraMendapatkanRokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganCaraMendapatkanRokokKeyPressed
        Valid.pindah(evt,CaraMendapatkanRokok,KeinginanBerhentiMerokok);
    }//GEN-LAST:event_KeteranganCaraMendapatkanRokokKeyPressed

    private void KeteranganAlasanUtamaBerhentiMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAlasanUtamaBerhentiMerokokKeyPressed
        Valid.pindah(evt,AlasanUtamaBerhentiMerokok,TahuDampakKesehatanMerokok);
    }//GEN-LAST:event_KeteranganAlasanUtamaBerhentiMerokokKeyPressed

    private void KeteranganYangPalingSeringMerokokDiSekolahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganYangPalingSeringMerokokDiSekolahKeyPressed
        Valid.pindah(evt,MerokokDiPendidikan,AnggotaKeluargaMerokok);
    }//GEN-LAST:event_KeteranganYangPalingSeringMerokokDiSekolahKeyPressed

    private void HasilPemeriksaanCOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilPemeriksaanCOKeyPressed
        Valid.pindah(evt,DilakukanPemeriksaanCO,BtnSimpan);
    }//GEN-LAST:event_HasilPemeriksaanCOKeyPressed

    private void ApakahAndaMerokokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ApakahAndaMerokokItemStateChanged
        if(ApakahAndaMerokok.getSelectedIndex()>1){
            JumlahRokok.setText("");
            SatuanRokok.setSelectedIndex(2);
            JenisRokokDigunakan.setSelectedIndex(4);
            KeteranganJenisRokokDigunakan.setText("");
            UsiaMulaiMerokok.setText("");
            AlasanUtamaMerokok.setSelectedIndex(7);
            KeteranganAlasanUtamaMerokok.setText("");
            LamaMerokok.setText("");
            CaraMendapatkanRokok.setSelectedIndex(4);
            KeteranganCaraMendapatkanRokok.setText("");
            KeinginanBerhentiMerokok.setSelectedIndex(2);
            AlasanUtamaBerhentiMerokok.setSelectedIndex(5);
            KeteranganAlasanUtamaBerhentiMerokok.setText("");
        }else{
            SatuanRokok.setSelectedIndex(0);
            JenisRokokDigunakan.setSelectedIndex(0);
            AlasanUtamaMerokok.setSelectedIndex(0);
            CaraMendapatkanRokok.setSelectedIndex(0);
            KeinginanBerhentiMerokok.setSelectedIndex(0);
            AlasanUtamaBerhentiMerokok.setSelectedIndex(0);
        }
    }//GEN-LAST:event_ApakahAndaMerokokItemStateChanged

    private void KeinginanBerhentiMerokokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KeinginanBerhentiMerokokItemStateChanged
        if(KeinginanBerhentiMerokok.getSelectedIndex()==1){
            AlasanUtamaBerhentiMerokok.setSelectedIndex(5);
            KeteranganAlasanUtamaBerhentiMerokok.setText("");
        }else{
            AlasanUtamaBerhentiMerokok.setSelectedIndex(0);
        }
    }//GEN-LAST:event_KeinginanBerhentiMerokokItemStateChanged

    private void SatuanRokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SatuanRokokKeyPressed
        Valid.pindah(evt,JumlahRokok,JenisRokokDigunakan);
    }//GEN-LAST:event_SatuanRokokKeyPressed

    private void AlasanUtamaMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanUtamaMerokokKeyPressed
        Valid.pindah(evt,UsiaMulaiMerokok,KeteranganAlasanUtamaMerokok);
    }//GEN-LAST:event_AlasanUtamaMerokokKeyPressed

    private void CaraMendapatkanRokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraMendapatkanRokokKeyPressed
        Valid.pindah(evt,LamaMerokok,KeteranganCaraMendapatkanRokok);
    }//GEN-LAST:event_CaraMendapatkanRokokKeyPressed

    private void KeinginanBerhentiMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeinginanBerhentiMerokokKeyPressed
        if(KeinginanBerhentiMerokok.getSelectedIndex()==1){
            Valid.pindah(evt,KeteranganCaraMendapatkanRokok,TahuDampakKesehatanMerokok);
        }else{
            Valid.pindah(evt,KeteranganCaraMendapatkanRokok,AlasanUtamaBerhentiMerokok);
        }
    }//GEN-LAST:event_KeinginanBerhentiMerokokKeyPressed

    private void AlasanUtamaBerhentiMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanUtamaBerhentiMerokokKeyPressed
        Valid.pindah(evt,KeinginanBerhentiMerokok,KeteranganAlasanUtamaBerhentiMerokok);
    }//GEN-LAST:event_AlasanUtamaBerhentiMerokokKeyPressed

    private void TahuDampakKesehatanMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TahuDampakKesehatanMerokokKeyPressed
        if(TahuDampakKesehatanMerokok.getSelectedIndex()==1){
            Valid.pindah(evt,KeteranganAlasanUtamaBerhentiMerokok,PintuMasukNarkoba);
        }else{
            Valid.pindah(evt,KeteranganAlasanUtamaBerhentiMerokok,DampakKesehatanMerokok);
        }
    }//GEN-LAST:event_TahuDampakKesehatanMerokokKeyPressed

    private void TahuDampakKesehatanMerokokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TahuDampakKesehatanMerokokItemStateChanged
        if(TahuDampakKesehatanMerokok.getSelectedIndex()==1){
            DampakKesehatanMerokok.setSelectedIndex(4);
        }else{
            DampakKesehatanMerokok.setSelectedIndex(0);
        }
    }//GEN-LAST:event_TahuDampakKesehatanMerokokItemStateChanged

    private void DampakKesehatanMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DampakKesehatanMerokokKeyPressed
        Valid.pindah(evt,TahuDampakKesehatanMerokok,PintuMasukNarkoba);
    }//GEN-LAST:event_DampakKesehatanMerokokKeyPressed

    private void PintuMasukNarkobaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PintuMasukNarkobaKeyPressed
        Valid.pindah(evt,DampakKesehatanMerokok,MerokokDiPendidikan);
    }//GEN-LAST:event_PintuMasukNarkobaKeyPressed

    private void MerokokDiPendidikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerokokDiPendidikanKeyPressed
        if(MerokokDiPendidikan.getSelectedIndex()==1){
            Valid.pindah(evt,PintuMasukNarkoba,AnggotaKeluargaMerokok);
        }else{
            Valid.pindah(evt,PintuMasukNarkoba,YangPalingSeringMerokokDiSekolah);
        }
    }//GEN-LAST:event_MerokokDiPendidikanKeyPressed

    private void YangPalingSeringMerokokDiSekolahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YangPalingSeringMerokokDiSekolahKeyPressed
        Valid.pindah(evt,MerokokDiPendidikan,KeteranganYangPalingSeringMerokokDiSekolah);
    }//GEN-LAST:event_YangPalingSeringMerokokDiSekolahKeyPressed

    private void AnggotaKeluargaMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnggotaKeluargaMerokokKeyPressed
        Valid.pindah(evt,KeteranganYangPalingSeringMerokokDiSekolah,TemanDekatMerokok);
    }//GEN-LAST:event_AnggotaKeluargaMerokokKeyPressed

    private void TemanDekatMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TemanDekatMerokokKeyPressed
        Valid.pindah(evt,AnggotaKeluargaMerokok,DilakukanPemeriksaanCO);
    }//GEN-LAST:event_TemanDekatMerokokKeyPressed

    private void DilakukanPemeriksaanCOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DilakukanPemeriksaanCOKeyPressed
        Valid.pindah(evt,TemanDekatMerokok,HasilPemeriksaanCO);
    }//GEN-LAST:event_DilakukanPemeriksaanCOKeyPressed

    private void JenisRokokDigunakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisRokokDigunakanKeyPressed
        Valid.pindah(evt,SatuanRokok,KeteranganJenisRokokDigunakan);
    }//GEN-LAST:event_JenisRokokDigunakanKeyPressed

    private void ApakahAndaMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ApakahAndaMerokokKeyPressed
        if(ApakahAndaMerokok.getSelectedIndex()>1){
            Valid.pindah(evt,btnAsalSekolah,TahuDampakKesehatanMerokok);
        }else{
            Valid.pindah(evt,btnAsalSekolah,JumlahRokok);
        }
    }//GEN-LAST:event_ApakahAndaMerokokKeyPressed

    private void MerokokDiPendidikanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MerokokDiPendidikanItemStateChanged
        if(MerokokDiPendidikan.getSelectedIndex()==1){
            YangPalingSeringMerokokDiSekolah.setSelectedIndex(4);
        }else{
            YangPalingSeringMerokokDiSekolah.setSelectedIndex(0);
        }
    }//GEN-LAST:event_MerokokDiPendidikanItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningMerokokUsiaSekolahRemaja dialog = new RMSkriningMerokokUsiaSekolahRemaja(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AlasanUtamaBerhentiMerokok;
    private widget.ComboBox AlasanUtamaMerokok;
    private widget.ComboBox AnggotaKeluargaMerokok;
    private widget.ComboBox ApakahAndaMerokok;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CaraMendapatkanRokok;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox DampakKesehatanMerokok;
    private widget.ComboBox Detik;
    private widget.ComboBox DilakukanPemeriksaanCO;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HasilPemeriksaanCO;
    private widget.ComboBox Jam;
    private widget.ComboBox JenisRokokDigunakan;
    private widget.TextBox Jk;
    private widget.TextBox JumlahRokok;
    private widget.TextBox KdAsalSekolah;
    private widget.TextBox KdPetugas;
    private widget.ComboBox KeinginanBerhentiMerokok;
    private widget.ComboBox Kelas;
    private widget.TextBox KeteranganAlasanUtamaBerhentiMerokok;
    private widget.TextBox KeteranganAlasanUtamaMerokok;
    private widget.TextBox KeteranganCaraMendapatkanRokok;
    private widget.TextBox KeteranganJenisRokokDigunakan;
    private widget.TextBox KeteranganYangPalingSeringMerokokDiSekolah;
    private widget.Label LCount;
    private widget.TextBox LamaMerokok;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private widget.ComboBox MerokokDiPendidikan;
    private javax.swing.JMenuItem MnSkriningMerokok;
    private widget.TextBox NmAsalSekolah;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PintuMasukNarkoba;
    private widget.ComboBox SatuanRokok;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.ComboBox TahuDampakKesehatanMerokok;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.ComboBox TemanDekatMerokok;
    private widget.TextBox TglLahir;
    private widget.TextBox Umur;
    private widget.TextBox UsiaMulaiMerokok;
    private widget.ComboBox YangPalingSeringMerokokDiSekolah;
    private widget.Button btnAsalSekolah;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
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
    private widget.Label jLabel12;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "skrining_perilaku_merokok_sekolah_remaja.kelas,skrining_perilaku_merokok_sekolah_remaja.kd_sekolah,master_sekolah.nm_sekolah,"+
                    "skrining_perilaku_merokok_sekolah_remaja.nip,petugas.nama,skrining_perilaku_merokok_sekolah_remaja.tanggal,"+
                    "skrining_perilaku_merokok_sekolah_remaja.apakah_anda_merokok,skrining_perilaku_merokok_sekolah_remaja.jumlah_batang_rokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.jumlah_batang_rokok_hariminggu,skrining_perilaku_merokok_sekolah_remaja.jenis_rokok_yang_digunakan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.jenis_rokok_yang_digunakan_keterangan,skrining_perilaku_merokok_sekolah_remaja.usia_mulai_merokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.alasan_mulai_merokok,skrining_perilaku_merokok_sekolah_remaja.alasan_mulai_merokok_keterangan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.sudah_berapa_lama_merokok,skrining_perilaku_merokok_sekolah_remaja.bagaimana_biasanya_mendapatkan_rokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.bagaimana_biasanya_mendapatkan_rokok_keterangan,skrining_perilaku_merokok_sekolah_remaja.keinginan_berhenti_merokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.alasan_utama_berhenti_merokok,skrining_perilaku_merokok_sekolah_remaja.alasan_utama_berhenti_merokok_keterangan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.tahu_dampak_kesehatan_merokok,skrining_perilaku_merokok_sekolah_remaja.dampak_kesehatan_dari_merokok_yang_diketahui,"+
                    "skrining_perilaku_merokok_sekolah_remaja.tahu_merokok_pintu_masuk_narkoba,skrining_perilaku_merokok_sekolah_remaja.melihat_orang_merokok_di_sekolah,"+
                    "skrining_perilaku_merokok_sekolah_remaja.orang_yang_paling_sering_merokok_disekolah,skrining_perilaku_merokok_sekolah_remaja.orang_yang_paling_sering_merokok_disekolah_keterangan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.ada_anggota_keluarga_di_rumah_yang_merokok,skrining_perilaku_merokok_sekolah_remaja.teman_dekat_banyakyang_merokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.dilakukan_pemeriksaan_kadar_co_pernapasan,skrining_perilaku_merokok_sekolah_remaja.hasil_pemeriksaan_co_pernapasan "+
                    "from skrining_perilaku_merokok_sekolah_remaja inner join reg_periksa on skrining_perilaku_merokok_sekolah_remaja.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_perilaku_merokok_sekolah_remaja.nip=petugas.nip "+
                    "inner join master_sekolah on master_sekolah.kd_sekolah=skrining_perilaku_merokok_sekolah_remaja.kd_sekolah "+
                    "where skrining_perilaku_merokok_sekolah_remaja.tanggal between ? and ? order by skrining_perilaku_merokok_sekolah_remaja.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "skrining_perilaku_merokok_sekolah_remaja.kelas,skrining_perilaku_merokok_sekolah_remaja.kd_sekolah,master_sekolah.nm_sekolah,"+
                    "skrining_perilaku_merokok_sekolah_remaja.nip,petugas.nama,skrining_perilaku_merokok_sekolah_remaja.tanggal,"+
                    "skrining_perilaku_merokok_sekolah_remaja.apakah_anda_merokok,skrining_perilaku_merokok_sekolah_remaja.jumlah_batang_rokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.jumlah_batang_rokok_hariminggu,skrining_perilaku_merokok_sekolah_remaja.jenis_rokok_yang_digunakan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.jenis_rokok_yang_digunakan_keterangan,skrining_perilaku_merokok_sekolah_remaja.usia_mulai_merokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.alasan_mulai_merokok,skrining_perilaku_merokok_sekolah_remaja.alasan_mulai_merokok_keterangan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.sudah_berapa_lama_merokok,skrining_perilaku_merokok_sekolah_remaja.bagaimana_biasanya_mendapatkan_rokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.bagaimana_biasanya_mendapatkan_rokok_keterangan,skrining_perilaku_merokok_sekolah_remaja.keinginan_berhenti_merokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.alasan_utama_berhenti_merokok,skrining_perilaku_merokok_sekolah_remaja.alasan_utama_berhenti_merokok_keterangan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.tahu_dampak_kesehatan_merokok,skrining_perilaku_merokok_sekolah_remaja.dampak_kesehatan_dari_merokok_yang_diketahui,"+
                    "skrining_perilaku_merokok_sekolah_remaja.tahu_merokok_pintu_masuk_narkoba,skrining_perilaku_merokok_sekolah_remaja.melihat_orang_merokok_di_sekolah,"+
                    "skrining_perilaku_merokok_sekolah_remaja.orang_yang_paling_sering_merokok_disekolah,skrining_perilaku_merokok_sekolah_remaja.orang_yang_paling_sering_merokok_disekolah_keterangan,"+
                    "skrining_perilaku_merokok_sekolah_remaja.ada_anggota_keluarga_di_rumah_yang_merokok,skrining_perilaku_merokok_sekolah_remaja.teman_dekat_banyakyang_merokok,"+
                    "skrining_perilaku_merokok_sekolah_remaja.dilakukan_pemeriksaan_kadar_co_pernapasan,skrining_perilaku_merokok_sekolah_remaja.hasil_pemeriksaan_co_pernapasan "+
                    "from skrining_perilaku_merokok_sekolah_remaja inner join reg_periksa on skrining_perilaku_merokok_sekolah_remaja.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_perilaku_merokok_sekolah_remaja.nip=petugas.nip "+
                    "inner join master_sekolah on master_sekolah.kd_sekolah=skrining_perilaku_merokok_sekolah_remaja.kd_sekolah "+
                    "where skrining_perilaku_merokok_sekolah_remaja.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or master_sekolah.nm_sekolah like ? or skrining_perilaku_merokok_sekolah_remaja.nip like ? or petugas.nama like ?) "+
                    "order by skrining_perilaku_merokok_sekolah_remaja.tanggal ");
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
                    ps.setString(8,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("kelas"),rs.getString("kd_sekolah"),rs.getString("nm_sekolah"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("apakah_anda_merokok"),rs.getString("jumlah_batang_rokok"),
                        rs.getString("jumlah_batang_rokok_hariminggu"),rs.getString("jenis_rokok_yang_digunakan"),rs.getString("jenis_rokok_yang_digunakan_keterangan"),
                        rs.getString("usia_mulai_merokok"),rs.getString("alasan_mulai_merokok"),rs.getString("alasan_mulai_merokok_keterangan"),rs.getString("sudah_berapa_lama_merokok"),
                        rs.getString("bagaimana_biasanya_mendapatkan_rokok"),rs.getString("bagaimana_biasanya_mendapatkan_rokok_keterangan"),rs.getString("keinginan_berhenti_merokok"),
                        rs.getString("alasan_utama_berhenti_merokok"),rs.getString("alasan_utama_berhenti_merokok_keterangan"),rs.getString("tahu_dampak_kesehatan_merokok"),
                        rs.getString("dampak_kesehatan_dari_merokok_yang_diketahui"),rs.getString("tahu_merokok_pintu_masuk_narkoba"),rs.getString("melihat_orang_merokok_di_sekolah"),
                        rs.getString("orang_yang_paling_sering_merokok_disekolah"),rs.getString("orang_yang_paling_sering_merokok_disekolah_keterangan"),
                        rs.getString("ada_anggota_keluarga_di_rumah_yang_merokok"),rs.getString("teman_dekat_banyakyang_merokok"),rs.getString("dilakukan_pemeriksaan_kadar_co_pernapasan"),
                        rs.getString("hasil_pemeriksaan_co_pernapasan")
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
        Kelas.setSelectedIndex(0);
        KdAsalSekolah.setText("");
        NmAsalSekolah.setText("");
        Tanggal.setDate(new Date());
        ApakahAndaMerokok.setSelectedIndex(0);
        JumlahRokok.setText("");
        SatuanRokok.setSelectedIndex(0);
        JenisRokokDigunakan.setSelectedIndex(0);
        KeteranganJenisRokokDigunakan.setText("");
        UsiaMulaiMerokok.setText("");
        AlasanUtamaMerokok.setSelectedIndex(0);
        KeteranganAlasanUtamaMerokok.setText("");
        LamaMerokok.setText("");
        CaraMendapatkanRokok.setSelectedIndex(0);
        KeteranganCaraMendapatkanRokok.setText("");
        KeinginanBerhentiMerokok.setSelectedIndex(0);
        AlasanUtamaBerhentiMerokok.setSelectedIndex(0);
        KeteranganAlasanUtamaBerhentiMerokok.setText("");
        TahuDampakKesehatanMerokok.setSelectedIndex(0);
        DampakKesehatanMerokok.setSelectedIndex(0);
        PintuMasukNarkoba.setSelectedIndex(0);
        MerokokDiPendidikan.setSelectedIndex(0);
        YangPalingSeringMerokokDiSekolah.setSelectedIndex(0);
        KeteranganYangPalingSeringMerokokDiSekolah.setText("");
        AnggotaKeluargaMerokok.setSelectedIndex(0);
        TemanDekatMerokok.setSelectedIndex(0);
        DilakukanPemeriksaanCO.setSelectedIndex(0);
        HasilPemeriksaanCO.setText("");
        Kelas.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Kelas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            KdAsalSekolah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NmAsalSekolah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(17,19));
            ApakahAndaMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            JumlahRokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            SatuanRokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            JenisRokokDigunakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KeteranganJenisRokokDigunakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            UsiaMulaiMerokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            AlasanUtamaMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            KeteranganAlasanUtamaMerokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            LamaMerokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            CaraMendapatkanRokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KeteranganCaraMendapatkanRokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            KeinginanBerhentiMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            AlasanUtamaBerhentiMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KeteranganAlasanUtamaBerhentiMerokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            TahuDampakKesehatanMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            DampakKesehatanMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            PintuMasukNarkoba.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            MerokokDiPendidikan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            YangPalingSeringMerokokDiSekolah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            KeteranganYangPalingSeringMerokokDiSekolah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            AnggotaKeluargaMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            TemanDekatMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            DilakukanPemeriksaanCO.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            HasilPemeriksaanCO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, pasien.jk,pasien.tgl_lahir,"+
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
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-172));
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
        BtnSimpan.setEnabled(akses.getskrining_perilaku_merokok_sekolah_remaja());
        BtnHapus.setEnabled(akses.getskrining_perilaku_merokok_sekolah_remaja());
        BtnEdit.setEnabled(akses.getskrining_perilaku_merokok_sekolah_remaja());
        BtnPrint.setEnabled(akses.getskrining_perilaku_merokok_sekolah_remaja()); 
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
        if(Sequel.mengedittf("skrining_perilaku_merokok_sekolah_remaja","no_rawat=?","no_rawat=?,tanggal=?,kd_sekolah=?,kelas=?,apakah_anda_merokok=?,jumlah_batang_rokok=?,jumlah_batang_rokok_hariminggu=?,jenis_rokok_yang_digunakan=?,"+
                "jenis_rokok_yang_digunakan_keterangan=?,usia_mulai_merokok=?,alasan_mulai_merokok=?,alasan_mulai_merokok_keterangan=?,sudah_berapa_lama_merokok=?,bagaimana_biasanya_mendapatkan_rokok=?,bagaimana_biasanya_mendapatkan_rokok_keterangan=?,"+
                "keinginan_berhenti_merokok=?,alasan_utama_berhenti_merokok=?,alasan_utama_berhenti_merokok_keterangan=?,tahu_dampak_kesehatan_merokok=?,dampak_kesehatan_dari_merokok_yang_diketahui=?,tahu_merokok_pintu_masuk_narkoba=?,"+
                "melihat_orang_merokok_di_sekolah=?,orang_yang_paling_sering_merokok_disekolah=?,orang_yang_paling_sering_merokok_disekolah_keterangan=?,ada_anggota_keluarga_di_rumah_yang_merokok=?,teman_dekat_banyakyang_merokok=?,"+
                "dilakukan_pemeriksaan_kadar_co_pernapasan=?,hasil_pemeriksaan_co_pernapasan=?,nip=?",30,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdAsalSekolah.getText(),
                Kelas.getSelectedItem().toString(),ApakahAndaMerokok.getSelectedItem().toString(),JumlahRokok.getText(),SatuanRokok.getSelectedItem().toString(),JenisRokokDigunakan.getSelectedItem().toString(), 
                KeteranganJenisRokokDigunakan.getText(),UsiaMulaiMerokok.getText(),AlasanUtamaMerokok.getSelectedItem().toString(),KeteranganAlasanUtamaMerokok.getText(), 
                LamaMerokok.getText(),CaraMendapatkanRokok.getSelectedItem().toString(),KeteranganCaraMendapatkanRokok.getText(),KeinginanBerhentiMerokok.getSelectedItem().toString(),
                AlasanUtamaBerhentiMerokok.getSelectedItem().toString(),KeteranganAlasanUtamaBerhentiMerokok.getText(),TahuDampakKesehatanMerokok.getSelectedItem().toString(), 
                DampakKesehatanMerokok.getSelectedItem().toString(),PintuMasukNarkoba.getSelectedItem().toString(),MerokokDiPendidikan.getSelectedItem().toString(),
                YangPalingSeringMerokokDiSekolah.getSelectedItem().toString(),KeteranganYangPalingSeringMerokokDiSekolah.getText(),AnggotaKeluargaMerokok.getSelectedItem().toString(), 
                TemanDekatMerokok.getSelectedItem().toString(),DilakukanPemeriksaanCO.getSelectedItem().toString(),HasilPemeriksaanCO.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(Kelas.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(KdAsalSekolah.getText(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(NmAsalSekolah.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(ApakahAndaMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(JumlahRokok.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(SatuanRokok.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(JenisRokokDigunakan.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(KeteranganJenisRokokDigunakan.getText(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(UsiaMulaiMerokok.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(AlasanUtamaMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(KeteranganAlasanUtamaMerokok.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(LamaMerokok.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(CaraMendapatkanRokok.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(KeteranganCaraMendapatkanRokok.getText(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(KeinginanBerhentiMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(AlasanUtamaBerhentiMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(KeteranganAlasanUtamaBerhentiMerokok.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(TahuDampakKesehatanMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(DampakKesehatanMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(PintuMasukNarkoba.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(MerokokDiPendidikan.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(YangPalingSeringMerokokDiSekolah.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(KeteranganYangPalingSeringMerokokDiSekolah.getText(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(AnggotaKeluargaMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(TemanDekatMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(DilakukanPemeriksaanCO.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(HasilPemeriksaanCO.getText(),tbObat.getSelectedRow(),35);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_perilaku_merokok_sekolah_remaja where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void simpan() {
        if(Sequel.menyimpantf("skrining_perilaku_merokok_sekolah_remaja","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",29,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdAsalSekolah.getText(),
            Kelas.getSelectedItem().toString(),ApakahAndaMerokok.getSelectedItem().toString(),JumlahRokok.getText(),SatuanRokok.getSelectedItem().toString(),JenisRokokDigunakan.getSelectedItem().toString(), 
            KeteranganJenisRokokDigunakan.getText(),UsiaMulaiMerokok.getText(),AlasanUtamaMerokok.getSelectedItem().toString(),KeteranganAlasanUtamaMerokok.getText(), 
            LamaMerokok.getText(),CaraMendapatkanRokok.getSelectedItem().toString(),KeteranganCaraMendapatkanRokok.getText(),KeinginanBerhentiMerokok.getSelectedItem().toString(),
            AlasanUtamaBerhentiMerokok.getSelectedItem().toString(),KeteranganAlasanUtamaBerhentiMerokok.getText(),TahuDampakKesehatanMerokok.getSelectedItem().toString(), 
            DampakKesehatanMerokok.getSelectedItem().toString(),PintuMasukNarkoba.getSelectedItem().toString(),MerokokDiPendidikan.getSelectedItem().toString(),
            YangPalingSeringMerokokDiSekolah.getSelectedItem().toString(),KeteranganYangPalingSeringMerokokDiSekolah.getText(),AnggotaKeluargaMerokok.getSelectedItem().toString(), 
            TemanDekatMerokok.getSelectedItem().toString(),DilakukanPemeriksaanCO.getSelectedItem().toString(),HasilPemeriksaanCO.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new String[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),Umur.getText(),Kelas.getSelectedItem().toString(),KdAsalSekolah.getText(),NmAsalSekolah.getText(),KdPetugas.getText(),
                NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),ApakahAndaMerokok.getSelectedItem().toString(),
                JumlahRokok.getText(),SatuanRokok.getSelectedItem().toString(),JenisRokokDigunakan.getSelectedItem().toString(),KeteranganJenisRokokDigunakan.getText(),UsiaMulaiMerokok.getText(),
                AlasanUtamaMerokok.getSelectedItem().toString(), KeteranganAlasanUtamaMerokok.getText(),LamaMerokok.getText(),CaraMendapatkanRokok.getSelectedItem().toString(),KeteranganCaraMendapatkanRokok.getText(), 
                KeinginanBerhentiMerokok.getSelectedItem().toString(),AlasanUtamaBerhentiMerokok.getSelectedItem().toString(),KeteranganAlasanUtamaBerhentiMerokok.getText(),TahuDampakKesehatanMerokok.getSelectedItem().toString(), 
                DampakKesehatanMerokok.getSelectedItem().toString(),PintuMasukNarkoba.getSelectedItem().toString(),MerokokDiPendidikan.getSelectedItem().toString(),YangPalingSeringMerokokDiSekolah.getSelectedItem().toString(), 
                KeteranganYangPalingSeringMerokokDiSekolah.getText(),AnggotaKeluargaMerokok.getSelectedItem().toString(),TemanDekatMerokok.getSelectedItem().toString(),DilakukanPemeriksaanCO.getSelectedItem().toString(), 
                HasilPemeriksaanCO.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
    
}

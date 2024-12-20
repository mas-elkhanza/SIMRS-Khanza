/*
 * By Mas Elkhanza
 */


package rekammedis;

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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianPsikologiKlinis extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianPsikologiKlinis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","NIP","Psikolog","Tanggal","Dikirim Dari","Tujuan","Anamnesis","Keterangan Anamnesis",
            "Keluhan Utama","Riwayat Penyakit","Riwayat Keluhan","Permasalahan Saat Ini","Alasan","Ekspektasi","Riwayat Hidup Singkat","Penampilan",
            "Ekspresi Wajah","Suasana Hati","Tingkah Laku","Fungsi Umum","Fungsi Intelektual","Pengalaman","Lainnya","Delusi","Proses Pikiran",
            "Halusinasi","Afek","Insight","Kesadaran","Orientasi","Atensi","Kontrol Impuls","Tgl.Pelaksanaan","Nama Tes","Hasil Tes","Dinamika Psikologis",
            "Diagnosa Psikologis","Manifestasi Fungsi Psikologis","Rencana Intervensi","Tahapan Intervensi 1","Target Terapi 1","Tahapan Intervensi 2",
            "Target Terapi 2","Tahapan Intervensi 3","Target Terapi 3","Tahapan Intervensi 4","Target Terapi 4","Tahapan Intervensi 5","Target Terapi 5",
            "Tahapan Intervensi 6","Target Terapi 6","Tahapan Intervensi 7","Target Terapi 7","Evaluasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
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
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(72);
            }else if(i==9){
                column.setPreferredWidth(62);
            }else if(i==10){
                column.setPreferredWidth(85);
            }else if(i==11){
                column.setPreferredWidth(165);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KetAlloAuto.setDocument(new batasInput((int)200).getKata(KetAlloAuto));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        RiwayatPenyakit.setDocument(new batasInput((int)1000).getKata(RiwayatPenyakit));
        RiwayatKeluhan.setDocument(new batasInput((int)1000).getKata(RiwayatKeluhan));
        AlasanPermasalahan.setDocument(new batasInput((int)100).getKata(AlasanPermasalahan));
        EkspektasiMasalah.setDocument(new batasInput((int)100).getKata(EkspektasiMasalah));
        RiwayatHidupSingkat.setDocument(new batasInput((int)1000).getKata(RiwayatHidupSingkat));
        Penampilan.setDocument(new batasInput((int)150).getKata(Penampilan));
        EkspresiWajah.setDocument(new batasInput((int)150).getKata(EkspresiWajah));
        SuasanaHati.setDocument(new batasInput((int)150).getKata(SuasanaHati));
        TingkahLaku.setDocument(new batasInput((int)150).getKata(TingkahLaku));
        FungsiUmum.setDocument(new batasInput((int)150).getKata(FungsiUmum));
        FungsiIntelektual.setDocument(new batasInput((int)150).getKata(FungsiIntelektual));
        Pengalaman.setDocument(new batasInput((int)150).getKata(Pengalaman));
        Lainnya.setDocument(new batasInput((int)150).getKata(Lainnya));
        Delusi.setDocument(new batasInput((int)150).getKata(Delusi));
        ProsesPikiran.setDocument(new batasInput((int)150).getKata(ProsesPikiran));
        Halusinasi.setDocument(new batasInput((int)150).getKata(Halusinasi));
        Afek.setDocument(new batasInput((int)150).getKata(Afek));
        Insight.setDocument(new batasInput((int)150).getKata(Insight));
        Kesadaran.setDocument(new batasInput((int)150).getKata(Kesadaran));
        Orientasi.setDocument(new batasInput((int)150).getKata(Orientasi));
        Atensi.setDocument(new batasInput((int)150).getKata(Atensi));
        KontrolImpuls.setDocument(new batasInput((int)150).getKata(KontrolImpuls));
        NamaTes.setDocument(new batasInput((int)100).getKata(NamaTes));
        HasilTes.setDocument(new batasInput((int)200).getKata(HasilTes));
        DinamikaPsikologis.setDocument(new batasInput((int)1000).getKata(DinamikaPsikologis));
        DiagnosaPsikologis.setDocument(new batasInput((int)1000).getKata(DiagnosaPsikologis));
        ManifestasiFungsiPsikologis.setDocument(new batasInput((int)1000).getKata(ManifestasiFungsiPsikologis));
        RencanaIntervensi.setDocument(new batasInput((int)1000).getKata(RencanaIntervensi));
        TahapanIntevensi1.setDocument(new batasInput((int)100).getKata(TahapanIntevensi1));
        TargetTerapi1.setDocument(new batasInput((int)100).getKata(TargetTerapi1));
        TahapanIntevensi2.setDocument(new batasInput((int)100).getKata(TahapanIntevensi2));
        TargetTerapi2.setDocument(new batasInput((int)100).getKata(TargetTerapi2));
        TahapanIntevensi3.setDocument(new batasInput((int)100).getKata(TahapanIntevensi3));
        TargetTerapi3.setDocument(new batasInput((int)100).getKata(TargetTerapi3));
        TahapanIntevensi4.setDocument(new batasInput((int)100).getKata(TahapanIntevensi4));
        TargetTerapi4.setDocument(new batasInput((int)100).getKata(TargetTerapi4));
        TahapanIntevensi5.setDocument(new batasInput((int)100).getKata(TahapanIntevensi5));
        TargetTerapi5.setDocument(new batasInput((int)100).getKata(TargetTerapi5));
        TahapanIntevensi6.setDocument(new batasInput((int)100).getKata(TahapanIntevensi6));
        TargetTerapi6.setDocument(new batasInput((int)100).getKata(TargetTerapi6));
        TahapanIntevensi7.setDocument(new batasInput((int)100).getKata(TahapanIntevensi7));
        TargetTerapi7.setDocument(new batasInput((int)100).getKata(TargetTerapi7));
        Evaluasi.setDocument(new batasInput((int)1000).getKata(Evaluasi));
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
                    KdPetugas.requestFocus();
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

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel99 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        DinamikaPsikologis = new widget.TextArea();
        jLabel34 = new widget.Label();
        PermasalahanSaatIni = new widget.ComboBox();
        jLabel42 = new widget.Label();
        Dikirimdari = new widget.ComboBox();
        jLabel56 = new widget.Label();
        TujuanPemeriksaan = new widget.ComboBox();
        jLabel57 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        KetAlloAuto = new javax.swing.JTextArea();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel36 = new widget.Label();
        Informasi = new widget.ComboBox();
        TglAsuhan = new widget.Tanggal();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel113 = new widget.Label();
        scrollPane19 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel114 = new widget.Label();
        scrollPane20 = new widget.ScrollPane();
        RiwayatPenyakit = new widget.TextArea();
        jLabel115 = new widget.Label();
        scrollPane21 = new widget.ScrollPane();
        RiwayatKeluhan = new widget.TextArea();
        jLabel58 = new widget.Label();
        jLabel9 = new widget.Label();
        AlasanPermasalahan = new widget.TextBox();
        jLabel12 = new widget.Label();
        EkspektasiMasalah = new widget.TextBox();
        jLabel116 = new widget.Label();
        scrollPane22 = new widget.ScrollPane();
        RiwayatHidupSingkat = new widget.TextArea();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        jLabel119 = new widget.Label();
        Penampilan = new widget.TextBox();
        jLabel59 = new widget.Label();
        EkspresiWajah = new widget.TextBox();
        jLabel120 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel121 = new widget.Label();
        jLabel61 = new widget.Label();
        SuasanaHati = new widget.TextBox();
        jLabel122 = new widget.Label();
        jLabel62 = new widget.Label();
        TingkahLaku = new widget.TextBox();
        jLabel123 = new widget.Label();
        jLabel63 = new widget.Label();
        FungsiUmum = new widget.TextBox();
        jLabel124 = new widget.Label();
        jLabel64 = new widget.Label();
        FungsiIntelektual = new widget.TextBox();
        jLabel65 = new widget.Label();
        Pengalaman = new widget.TextBox();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel66 = new widget.Label();
        Lainnya = new widget.TextBox();
        jLabel127 = new widget.Label();
        jLabel67 = new widget.Label();
        Delusi = new widget.TextBox();
        jLabel128 = new widget.Label();
        jLabel68 = new widget.Label();
        ProsesPikiran = new widget.TextBox();
        jLabel129 = new widget.Label();
        Halusinasi = new widget.TextBox();
        jLabel130 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel131 = new widget.Label();
        Afek = new widget.TextBox();
        jLabel70 = new widget.Label();
        jLabel132 = new widget.Label();
        jLabel71 = new widget.Label();
        Insight = new widget.TextBox();
        jLabel72 = new widget.Label();
        Kesadaran = new widget.TextBox();
        jLabel133 = new widget.Label();
        jLabel134 = new widget.Label();
        jLabel73 = new widget.Label();
        Orientasi = new widget.TextBox();
        jLabel74 = new widget.Label();
        Atensi = new widget.TextBox();
        jLabel135 = new widget.Label();
        jLabel136 = new widget.Label();
        jLabel75 = new widget.Label();
        KontrolImpuls = new widget.TextBox();
        jLabel137 = new widget.Label();
        TanggalPelaksanaan = new widget.Tanggal();
        jLabel76 = new widget.Label();
        jLabel138 = new widget.Label();
        jLabel13 = new widget.Label();
        NamaTes = new widget.TextBox();
        jLabel139 = new widget.Label();
        jLabel77 = new widget.Label();
        HasilTes = new widget.TextBox();
        jLabel101 = new widget.Label();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        scrollPane15 = new widget.ScrollPane();
        DiagnosaPsikologis = new widget.TextArea();
        jLabel102 = new widget.Label();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        scrollPane16 = new widget.ScrollPane();
        ManifestasiFungsiPsikologis = new widget.TextArea();
        jLabel103 = new widget.Label();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        scrollPane17 = new widget.ScrollPane();
        RencanaIntervensi = new widget.TextArea();
        jLabel104 = new widget.Label();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel140 = new widget.Label();
        TahapanIntevensi1 = new widget.TextBox();
        TargetTerapi1 = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        jSeparator20 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        jLabel141 = new widget.Label();
        jLabel142 = new widget.Label();
        TahapanIntevensi3 = new widget.TextBox();
        TargetTerapi3 = new widget.TextBox();
        jLabel143 = new widget.Label();
        TahapanIntevensi2 = new widget.TextBox();
        TargetTerapi2 = new widget.TextBox();
        jLabel144 = new widget.Label();
        TahapanIntevensi4 = new widget.TextBox();
        TargetTerapi4 = new widget.TextBox();
        jLabel145 = new widget.Label();
        TahapanIntevensi5 = new widget.TextBox();
        TargetTerapi5 = new widget.TextBox();
        jLabel146 = new widget.Label();
        TahapanIntevensi6 = new widget.TextBox();
        TargetTerapi6 = new widget.TextBox();
        jLabel147 = new widget.Label();
        TahapanIntevensi7 = new widget.TextBox();
        TargetTerapi7 = new widget.TextBox();
        jLabel105 = new widget.Label();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        scrollPane18 = new widget.ScrollPane();
        Evaluasi = new widget.TextArea();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Penilaian Psikolog");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Psikologi Klinis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnAll);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1823));
        FormInput.setLayout(null);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. PEMERIKSAAN PSIKOLOGIS");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 130, 200, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 861, 880, 0);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("II. DINAMIKA PSIKOLOGIS");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 1150, 180, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        DinamikaPsikologis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DinamikaPsikologis.setColumns(20);
        DinamikaPsikologis.setRows(5);
        DinamikaPsikologis.setName("DinamikaPsikologis"); // NOI18N
        DinamikaPsikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DinamikaPsikologisKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(DinamikaPsikologis);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 1170, 810, 53);

        jLabel34.setText("Keterangan Anamnesis :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(220, 70, 150, 23);

        PermasalahanSaatIni.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sangat Serius", "Serius", "Cukup Serius" }));
        PermasalahanSaatIni.setName("PermasalahanSaatIni"); // NOI18N
        PermasalahanSaatIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PermasalahanSaatIniKeyPressed(evt);
            }
        });
        FormInput.add(PermasalahanSaatIni);
        PermasalahanSaatIni.setBounds(153, 400, 120, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Permasalah Saat Ini");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(44, 400, 115, 23);

        Dikirimdari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ruang Rawat", "Poliklinik", "Rehabilitasi", "After Care", "Dokter" }));
        Dikirimdari.setName("Dikirimdari"); // NOI18N
        Dikirimdari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DikirimdariKeyPressed(evt);
            }
        });
        FormInput.add(Dikirimdari);
        Dikirimdari.setBounds(84, 100, 128, 23);

        jLabel56.setText("Dikirim Dari :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(0, 100, 80, 23);

        TujuanPemeriksaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Klinik", "Bimbingan", "Forensik", " " }));
        TujuanPemeriksaan.setName("TujuanPemeriksaan"); // NOI18N
        TujuanPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TujuanPemeriksaanKeyPressed(evt);
            }
        });
        FormInput.add(TujuanPemeriksaan);
        TujuanPemeriksaan.setBounds(744, 40, 110, 23);

        jLabel57.setText("Tujuan Pemeriksaan :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(620, 40, 120, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        KetAlloAuto.setColumns(20);
        KetAlloAuto.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        KetAlloAuto.setRows(5);
        KetAlloAuto.setName("KetAlloAuto"); // NOI18N
        KetAlloAuto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAlloAutoKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(KetAlloAuto);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(374, 70, 480, 53);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(319, 10, 250, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 100, 23);

        label14.setText("Psikolog :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 80, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(84, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(186, 40, 185, 23);

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
        BtnDokter.setBounds(373, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 80, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(415, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel36.setText("Anamnesis :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 70, 80, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(84, 70, 128, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2024 10:00:37" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(476, 40, 130, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 130, 880, 1);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 1150, 880, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 1150, 880, 1);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Keluhan Utama :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(44, 150, 320, 23);

        scrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane19.setName("scrollPane19"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(10);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane19.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane19);
        scrollPane19.setBounds(44, 170, 810, 63);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Riwayat Penyakit :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(44, 240, 320, 23);

        scrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane20.setName("scrollPane20"); // NOI18N

        RiwayatPenyakit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakit.setColumns(20);
        RiwayatPenyakit.setRows(10);
        RiwayatPenyakit.setName("RiwayatPenyakit"); // NOI18N
        RiwayatPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitKeyPressed(evt);
            }
        });
        scrollPane20.setViewportView(RiwayatPenyakit);

        FormInput.add(scrollPane20);
        scrollPane20.setBounds(44, 260, 810, 53);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Riwayat Keluhan :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(44, 320, 320, 23);

        scrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane21.setName("scrollPane21"); // NOI18N

        RiwayatKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatKeluhan.setColumns(20);
        RiwayatKeluhan.setRows(10);
        RiwayatKeluhan.setName("RiwayatKeluhan"); // NOI18N
        RiwayatKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatKeluhanKeyPressed(evt);
            }
        });
        scrollPane21.setViewportView(RiwayatKeluhan);

        FormInput.add(scrollPane21);
        scrollPane21.setBounds(44, 340, 810, 53);

        jLabel58.setText(":");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 400, 149, 23);

        jLabel9.setText("Alasan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(277, 400, 50, 23);

        AlasanPermasalahan.setHighlighter(null);
        AlasanPermasalahan.setName("AlasanPermasalahan"); // NOI18N
        AlasanPermasalahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanPermasalahanKeyPressed(evt);
            }
        });
        FormInput.add(AlasanPermasalahan);
        AlasanPermasalahan.setBounds(331, 400, 225, 23);

        jLabel12.setText("Ekspektasi :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(555, 400, 70, 23);

        EkspektasiMasalah.setHighlighter(null);
        EkspektasiMasalah.setName("EkspektasiMasalah"); // NOI18N
        EkspektasiMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkspektasiMasalahKeyPressed(evt);
            }
        });
        FormInput.add(EkspektasiMasalah);
        EkspektasiMasalah.setBounds(629, 400, 225, 23);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Riwayat Hidup Singkat :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(44, 430, 320, 23);

        scrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane22.setName("scrollPane22"); // NOI18N

        RiwayatHidupSingkat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatHidupSingkat.setColumns(20);
        RiwayatHidupSingkat.setRows(10);
        RiwayatHidupSingkat.setName("RiwayatHidupSingkat"); // NOI18N
        RiwayatHidupSingkat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatHidupSingkatKeyPressed(evt);
            }
        });
        scrollPane22.setViewportView(RiwayatHidupSingkat);

        FormInput.add(scrollPane22);
        scrollPane22.setBounds(44, 450, 810, 43);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Observasi :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(44, 500, 320, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("Kondisi Psikologis :");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(64, 520, 320, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("- Penampilan");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(84, 540, 80, 23);

        Penampilan.setHighlighter(null);
        Penampilan.setName("Penampilan"); // NOI18N
        Penampilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenampilanKeyPressed(evt);
            }
        });
        FormInput.add(Penampilan);
        Penampilan.setBounds(159, 540, 695, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 540, 155, 23);

        EkspresiWajah.setHighlighter(null);
        EkspresiWajah.setName("EkspresiWajah"); // NOI18N
        EkspresiWajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkspresiWajahKeyPressed(evt);
            }
        });
        FormInput.add(EkspresiWajah);
        EkspresiWajah.setBounds(176, 570, 678, 23);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("- Ekspresi Wajah");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(84, 570, 110, 23);

        jLabel60.setText(":");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 570, 172, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("- Suasana Hati");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(84, 600, 90, 23);

        jLabel61.setText(":");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 600, 162, 23);

        SuasanaHati.setHighlighter(null);
        SuasanaHati.setName("SuasanaHati"); // NOI18N
        SuasanaHati.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuasanaHatiKeyPressed(evt);
            }
        });
        FormInput.add(SuasanaHati);
        SuasanaHati.setBounds(166, 600, 688, 23);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("- Tingkah Laku");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(84, 630, 90, 23);

        jLabel62.setText(":");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 630, 162, 23);

        TingkahLaku.setHighlighter(null);
        TingkahLaku.setName("TingkahLaku"); // NOI18N
        TingkahLaku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TingkahLakuKeyPressed(evt);
            }
        });
        FormInput.add(TingkahLaku);
        TingkahLaku.setBounds(166, 630, 688, 23);

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel123.setText("- Fungsi Umum");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(84, 660, 90, 23);

        jLabel63.setText(":");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 660, 164, 23);

        FungsiUmum.setHighlighter(null);
        FungsiUmum.setName("FungsiUmum"); // NOI18N
        FungsiUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FungsiUmumKeyPressed(evt);
            }
        });
        FormInput.add(FungsiUmum);
        FungsiUmum.setBounds(168, 660, 686, 23);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("- Fungsi Intelektual");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(84, 690, 110, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 690, 185, 23);

        FungsiIntelektual.setHighlighter(null);
        FungsiIntelektual.setName("FungsiIntelektual"); // NOI18N
        FungsiIntelektual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FungsiIntelektualKeyPressed(evt);
            }
        });
        FormInput.add(FungsiIntelektual);
        FungsiIntelektual.setBounds(189, 690, 665, 23);

        jLabel65.setText(":");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 720, 158, 23);

        Pengalaman.setHighlighter(null);
        Pengalaman.setName("Pengalaman"); // NOI18N
        Pengalaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengalamanKeyPressed(evt);
            }
        });
        FormInput.add(Pengalaman);
        Pengalaman.setBounds(162, 720, 692, 23);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("- Pengalaman");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(84, 720, 110, 23);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("- Lainnya");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(84, 750, 110, 23);

        jLabel66.setText(":");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 750, 135, 23);

        Lainnya.setHighlighter(null);
        Lainnya.setName("Lainnya"); // NOI18N
        Lainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainnyaKeyPressed(evt);
            }
        });
        FormInput.add(Lainnya);
        Lainnya.setBounds(139, 750, 715, 23);

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("Kondisi Patologis :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(64, 780, 320, 23);

        jLabel67.setText(":");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 800, 169, 23);

        Delusi.setHighlighter(null);
        Delusi.setName("Delusi"); // NOI18N
        Delusi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DelusiKeyPressed(evt);
            }
        });
        FormInput.add(Delusi);
        Delusi.setBounds(173, 800, 681, 23);

        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel128.setText("- Delusi/Waham");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(84, 800, 110, 23);

        jLabel68.setText(":");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 830, 166, 23);

        ProsesPikiran.setHighlighter(null);
        ProsesPikiran.setName("ProsesPikiran"); // NOI18N
        ProsesPikiran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsesPikiranKeyPressed(evt);
            }
        });
        FormInput.add(ProsesPikiran);
        ProsesPikiran.setBounds(170, 830, 684, 23);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("- Proses Pikiran");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(84, 830, 110, 23);

        Halusinasi.setHighlighter(null);
        Halusinasi.setName("Halusinasi"); // NOI18N
        Halusinasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HalusinasiKeyPressed(evt);
            }
        });
        FormInput.add(Halusinasi);
        Halusinasi.setBounds(151, 860, 703, 23);

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel130.setText("- Halusinasi");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(84, 860, 110, 23);

        jLabel69.setText(":");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 860, 147, 23);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel131.setText("- Afek");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(84, 890, 50, 23);

        Afek.setHighlighter(null);
        Afek.setName("Afek"); // NOI18N
        Afek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AfekKeyPressed(evt);
            }
        });
        FormInput.add(Afek);
        Afek.setBounds(125, 890, 729, 23);

        jLabel70.setText(":");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 890, 121, 23);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("- Insight");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(84, 920, 60, 23);

        jLabel71.setText(":");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(0, 920, 132, 23);

        Insight.setHighlighter(null);
        Insight.setName("Insight"); // NOI18N
        Insight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InsightKeyPressed(evt);
            }
        });
        FormInput.add(Insight);
        Insight.setBounds(136, 920, 718, 23);

        jLabel72.setText(":");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 950, 149, 23);

        Kesadaran.setHighlighter(null);
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(153, 950, 701, 23);

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel133.setText("- Kesadaran");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(84, 950, 80, 23);

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setText("- Orientasi Ruang/Waktu/Tempat");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(84, 980, 170, 23);

        jLabel73.setText(":");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(0, 980, 253, 23);

        Orientasi.setHighlighter(null);
        Orientasi.setName("Orientasi"); // NOI18N
        Orientasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OrientasiKeyPressed(evt);
            }
        });
        FormInput.add(Orientasi);
        Orientasi.setBounds(257, 980, 597, 23);

        jLabel74.setText(":");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(0, 1010, 128, 23);

        Atensi.setHighlighter(null);
        Atensi.setName("Atensi"); // NOI18N
        Atensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AtensiKeyPressed(evt);
            }
        });
        FormInput.add(Atensi);
        Atensi.setBounds(132, 1010, 722, 23);

        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel135.setText("- Atensi");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(84, 1010, 80, 23);

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("- Kontrol Impuls");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(84, 1040, 80, 23);

        jLabel75.setText(":");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(0, 1040, 169, 23);

        KontrolImpuls.setHighlighter(null);
        KontrolImpuls.setName("KontrolImpuls"); // NOI18N
        KontrolImpuls.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontrolImpulsKeyPressed(evt);
            }
        });
        FormInput.add(KontrolImpuls);
        KontrolImpuls.setBounds(173, 1040, 681, 23);

        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel137.setText("Psikotes :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(44, 1070, 320, 23);

        TanggalPelaksanaan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPelaksanaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2024" }));
        TanggalPelaksanaan.setDisplayFormat("dd-MM-yyyy");
        TanggalPelaksanaan.setName("TanggalPelaksanaan"); // NOI18N
        TanggalPelaksanaan.setOpaque(false);
        TanggalPelaksanaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPelaksanaanKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPelaksanaan);
        TanggalPelaksanaan.setBounds(178, 1090, 90, 23);

        jLabel76.setText(":");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(0, 1090, 174, 23);

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("Tanggal Pelaksanaan");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(64, 1090, 130, 23);

        jLabel13.setText("Nama Tes :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(290, 1090, 70, 23);

        NamaTes.setHighlighter(null);
        NamaTes.setName("NamaTes"); // NOI18N
        NamaTes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaTesKeyPressed(evt);
            }
        });
        FormInput.add(NamaTes);
        NamaTes.setBounds(364, 1090, 490, 23);

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("Hasil");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(64, 1120, 130, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 1120, 94, 23);

        HasilTes.setHighlighter(null);
        HasilTes.setName("HasilTes"); // NOI18N
        HasilTes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilTesKeyPressed(evt);
            }
        });
        FormInput.add(HasilTes);
        HasilTes.setBounds(98, 1120, 756, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("III. DIAGNOSA PSIKOLOGIS");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 1230, 180, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 1230, 880, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 1230, 880, 1);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        DiagnosaPsikologis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaPsikologis.setColumns(20);
        DiagnosaPsikologis.setRows(5);
        DiagnosaPsikologis.setName("DiagnosaPsikologis"); // NOI18N
        DiagnosaPsikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPsikologisKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(DiagnosaPsikologis);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 1250, 810, 53);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("IV. MANIFESTASI FUNGSI PSIKOLOGIS");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 1310, 220, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 1310, 880, 1);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 1310, 880, 1);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        ManifestasiFungsiPsikologis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ManifestasiFungsiPsikologis.setColumns(20);
        ManifestasiFungsiPsikologis.setRows(5);
        ManifestasiFungsiPsikologis.setName("ManifestasiFungsiPsikologis"); // NOI18N
        ManifestasiFungsiPsikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ManifestasiFungsiPsikologisKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(ManifestasiFungsiPsikologis);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(44, 1330, 810, 53);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("V. RENCANA INTERVENSI");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 1390, 220, 23);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 1390, 880, 1);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1390, 880, 1);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        RencanaIntervensi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RencanaIntervensi.setColumns(20);
        RencanaIntervensi.setRows(5);
        RencanaIntervensi.setName("RencanaIntervensi"); // NOI18N
        RencanaIntervensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaIntervensiKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(RencanaIntervensi);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(44, 1410, 810, 53);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("VI. PELAKSANAAN INTERVENSI");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(10, 1470, 220, 23);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(0, 1470, 880, 1);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 1470, 880, 1);

        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel140.setText("Sesi");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(44, 1490, 30, 23);

        TahapanIntevensi1.setHighlighter(null);
        TahapanIntevensi1.setName("TahapanIntevensi1"); // NOI18N
        TahapanIntevensi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TahapanIntevensi1KeyPressed(evt);
            }
        });
        FormInput.add(TahapanIntevensi1);
        TahapanIntevensi1.setBounds(75, 1520, 380, 23);

        TargetTerapi1.setHighlighter(null);
        TargetTerapi1.setName("TargetTerapi1"); // NOI18N
        TargetTerapi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TargetTerapi1KeyPressed(evt);
            }
        });
        FormInput.add(TargetTerapi1);
        TargetTerapi1.setBounds(474, 1520, 380, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Tahapan Intervensi");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(75, 1490, 380, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Target Terapi");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(474, 1490, 380, 23);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(44, 1511, 810, 1);

        jSeparator21.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator21.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator21.setName("jSeparator21"); // NOI18N
        FormInput.add(jSeparator21);
        jSeparator21.setBounds(44, 1511, 810, 1);

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setText("1.");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(44, 1520, 30, 23);

        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel142.setText("3.");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(44, 1580, 30, 23);

        TahapanIntevensi3.setHighlighter(null);
        TahapanIntevensi3.setName("TahapanIntevensi3"); // NOI18N
        TahapanIntevensi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TahapanIntevensi3KeyPressed(evt);
            }
        });
        FormInput.add(TahapanIntevensi3);
        TahapanIntevensi3.setBounds(75, 1580, 380, 23);

        TargetTerapi3.setHighlighter(null);
        TargetTerapi3.setName("TargetTerapi3"); // NOI18N
        TargetTerapi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TargetTerapi3KeyPressed(evt);
            }
        });
        FormInput.add(TargetTerapi3);
        TargetTerapi3.setBounds(474, 1580, 380, 23);

        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel143.setText("2.");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(44, 1550, 30, 23);

        TahapanIntevensi2.setHighlighter(null);
        TahapanIntevensi2.setName("TahapanIntevensi2"); // NOI18N
        TahapanIntevensi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TahapanIntevensi2KeyPressed(evt);
            }
        });
        FormInput.add(TahapanIntevensi2);
        TahapanIntevensi2.setBounds(75, 1550, 380, 23);

        TargetTerapi2.setHighlighter(null);
        TargetTerapi2.setName("TargetTerapi2"); // NOI18N
        TargetTerapi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TargetTerapi2KeyPressed(evt);
            }
        });
        FormInput.add(TargetTerapi2);
        TargetTerapi2.setBounds(474, 1550, 380, 23);

        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel144.setText("4.");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(44, 1610, 30, 23);

        TahapanIntevensi4.setHighlighter(null);
        TahapanIntevensi4.setName("TahapanIntevensi4"); // NOI18N
        TahapanIntevensi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TahapanIntevensi4KeyPressed(evt);
            }
        });
        FormInput.add(TahapanIntevensi4);
        TahapanIntevensi4.setBounds(75, 1610, 380, 23);

        TargetTerapi4.setHighlighter(null);
        TargetTerapi4.setName("TargetTerapi4"); // NOI18N
        TargetTerapi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TargetTerapi4KeyPressed(evt);
            }
        });
        FormInput.add(TargetTerapi4);
        TargetTerapi4.setBounds(474, 1610, 380, 23);

        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel145.setText("5.");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(44, 1640, 30, 23);

        TahapanIntevensi5.setHighlighter(null);
        TahapanIntevensi5.setName("TahapanIntevensi5"); // NOI18N
        TahapanIntevensi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TahapanIntevensi5KeyPressed(evt);
            }
        });
        FormInput.add(TahapanIntevensi5);
        TahapanIntevensi5.setBounds(75, 1640, 380, 23);

        TargetTerapi5.setHighlighter(null);
        TargetTerapi5.setName("TargetTerapi5"); // NOI18N
        TargetTerapi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TargetTerapi5KeyPressed(evt);
            }
        });
        FormInput.add(TargetTerapi5);
        TargetTerapi5.setBounds(474, 1640, 380, 23);

        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel146.setText("6.");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(44, 1670, 30, 23);

        TahapanIntevensi6.setHighlighter(null);
        TahapanIntevensi6.setName("TahapanIntevensi6"); // NOI18N
        TahapanIntevensi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TahapanIntevensi6KeyPressed(evt);
            }
        });
        FormInput.add(TahapanIntevensi6);
        TahapanIntevensi6.setBounds(75, 1670, 380, 23);

        TargetTerapi6.setHighlighter(null);
        TargetTerapi6.setName("TargetTerapi6"); // NOI18N
        TargetTerapi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TargetTerapi6KeyPressed(evt);
            }
        });
        FormInput.add(TargetTerapi6);
        TargetTerapi6.setBounds(474, 1670, 380, 23);

        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel147.setText("7.");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(44, 1700, 30, 23);

        TahapanIntevensi7.setHighlighter(null);
        TahapanIntevensi7.setName("TahapanIntevensi7"); // NOI18N
        TahapanIntevensi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TahapanIntevensi7KeyPressed(evt);
            }
        });
        FormInput.add(TahapanIntevensi7);
        TahapanIntevensi7.setBounds(75, 1700, 380, 23);

        TargetTerapi7.setHighlighter(null);
        TargetTerapi7.setName("TargetTerapi7"); // NOI18N
        TargetTerapi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TargetTerapi7KeyPressed(evt);
            }
        });
        FormInput.add(TargetTerapi7);
        TargetTerapi7.setBounds(474, 1700, 380, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("VII. EVALUASI");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(10, 1730, 220, 23);

        jSeparator22.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator22.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator22.setName("jSeparator22"); // NOI18N
        FormInput.add(jSeparator22);
        jSeparator22.setBounds(0, 1730, 880, 1);

        jSeparator23.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator23.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator23.setName("jSeparator23"); // NOI18N
        FormInput.add(jSeparator23);
        jSeparator23.setBounds(0, 1730, 880, 1);

        scrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane18.setName("scrollPane18"); // NOI18N

        Evaluasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Evaluasi.setColumns(20);
        Evaluasi.setRows(5);
        Evaluasi.setName("Evaluasi"); // NOI18N
        Evaluasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EvaluasiKeyPressed(evt);
            }
        });
        scrollPane18.setViewportView(Evaluasi);

        FormInput.add(scrollPane18);
        scrollPane18.setBounds(44, 1750, 810, 63);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RiwayatPenyakit.getText().trim().equals("")){
            Valid.textKosong(RiwayatPenyakit,"Riwayat Penyakit");
        }else if(DinamikaPsikologis.getText().trim().equals("")){
            Valid.textKosong(DinamikaPsikologis,"Dinamika Psikologis");
        }else if(DiagnosaPsikologis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaPsikologis,"Diagnosa Psikologis");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,DinamikaPsikologis,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
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
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh psikolog yang bersangkutan..!!");
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RiwayatPenyakit.getText().trim().equals("")){
            Valid.textKosong(RiwayatPenyakit,"Riwayat Penyakit");
        }else if(DinamikaPsikologis.getText().trim().equals("")){
            Valid.textKosong(DinamikaPsikologis,"Dinamika Psikologis");
        }else if(DiagnosaPsikologis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaPsikologis,"Diagnosa Psikologis");
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
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh psikolog yang bersangkutan..!!");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='72px'><b>Dikirim Dari</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='62px'><b>Tujuan Pemeriksaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Informasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='165px'><b>Keterangan Informasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='71px'><b>Rupa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='79px'><b>Bentuk Tubuh</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='103px'><b>Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='99px'><b>Pakaian</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Penyampaian/Ekspresi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='196px'><b>Berbicara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='185px'><b>Penggunaan Kata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Ciri Yang Menyolok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Hasil Psikotes</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Kepribadian</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Psikodinamika</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Kesimpulan Psikolog</b></td>"+
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
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2900' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianPsikolog.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN PSIKOLOG<br><br></font>"+        
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
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
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void DinamikaPsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DinamikaPsikologisKeyPressed
        Valid.pindah2(evt,HasilTes,DiagnosaPsikologis);
    }//GEN-LAST:event_DinamikaPsikologisKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianPsikolog.jasper","report","::[ Laporan Penilaian Psikolog ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_psikologi_klinis.tanggal,"+
                        "penilaian_psikologi_klinis.nip,penilaian_psikologi_klinis.anamnesis,penilaian_psikologi_klinis.dikirim_dari,penilaian_psikologi_klinis.tujuan_pemeriksaan,penilaian_psikologi_klinis.ket_anamnesis,penilaian_psikologi_klinis.rupa,penilaian_psikologi_klinis.bentuk_tubuh,penilaian_psikologi_klinis.tindakan,"+
                        "penilaian_psikologi_klinis.pakaian,penilaian_psikologi_klinis.ekspresi,penilaian_psikologi_klinis.berbicara,penilaian_psikologi_klinis.penggunaan_kata,penilaian_psikologi_klinis.ciri_menyolok,penilaian_psikologi_klinis.hasil_psikotes,penilaian_psikologi_klinis.kepribadian,penilaian_psikologi_klinis.psikodinamika,penilaian_psikologi_klinis.kesimpulan_psikolog,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_psikologi_klinis on reg_periksa.no_rawat=penilaian_psikologi_klinis.no_rawat "+
                        "inner join petugas on penilaian_psikologi_klinis.nip=petugas.nip where penilaian_psikologi_klinis.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void PermasalahanSaatIniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PermasalahanSaatIniKeyPressed
        Valid.pindah(evt,RiwayatKeluhan,AlasanPermasalahan);
    }//GEN-LAST:event_PermasalahanSaatIniKeyPressed

    private void DikirimdariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DikirimdariKeyPressed
        Valid.pindah(evt,KetAlloAuto,KeluhanUtama);
    }//GEN-LAST:event_DikirimdariKeyPressed

    private void TujuanPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TujuanPemeriksaanKeyPressed
        Valid.pindah(evt,TglAsuhan,Informasi);
    }//GEN-LAST:event_TujuanPemeriksaanKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,DinamikaPsikologis,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt,TujuanPemeriksaan,KetAlloAuto);
    }//GEN-LAST:event_InformasiKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,BtnDokter,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void KetAlloAutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAlloAutoKeyPressed
        Valid.pindah2(evt,Informasi,Dikirimdari);
    }//GEN-LAST:event_KetAlloAutoKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Dikirimdari,RiwayatPenyakit);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RiwayatPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RiwayatKeluhan);
    }//GEN-LAST:event_RiwayatPenyakitKeyPressed

    private void RiwayatKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatKeluhanKeyPressed
        Valid.pindah2(evt,RiwayatPenyakit,PermasalahanSaatIni);
    }//GEN-LAST:event_RiwayatKeluhanKeyPressed

    private void RiwayatHidupSingkatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatHidupSingkatKeyPressed
        Valid.pindah2(evt,EkspektasiMasalah,Penampilan);
    }//GEN-LAST:event_RiwayatHidupSingkatKeyPressed

    private void TanggalPelaksanaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPelaksanaanKeyPressed
        Valid.pindah2(evt,KontrolImpuls,NamaTes);
    }//GEN-LAST:event_TanggalPelaksanaanKeyPressed

    private void DiagnosaPsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaPsikologisKeyPressed
        Valid.pindah2(evt,DinamikaPsikologis,ManifestasiFungsiPsikologis);
    }//GEN-LAST:event_DiagnosaPsikologisKeyPressed

    private void ManifestasiFungsiPsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ManifestasiFungsiPsikologisKeyPressed
        Valid.pindah2(evt,DiagnosaPsikologis,RencanaIntervensi);
    }//GEN-LAST:event_ManifestasiFungsiPsikologisKeyPressed

    private void RencanaIntervensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaIntervensiKeyPressed
        Valid.pindah2(evt,ManifestasiFungsiPsikologis,TahapanIntevensi1);
    }//GEN-LAST:event_RencanaIntervensiKeyPressed

    private void EvaluasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EvaluasiKeyPressed
        Valid.pindah(evt,TargetTerapi7,BtnSimpan);
    }//GEN-LAST:event_EvaluasiKeyPressed

    private void AlasanPermasalahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanPermasalahanKeyPressed
        Valid.pindah(evt,PermasalahanSaatIni,EkspektasiMasalah);
    }//GEN-LAST:event_AlasanPermasalahanKeyPressed

    private void EkspektasiMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkspektasiMasalahKeyPressed
        Valid.pindah(evt,AlasanPermasalahan,RiwayatHidupSingkat);
    }//GEN-LAST:event_EkspektasiMasalahKeyPressed

    private void PenampilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenampilanKeyPressed
        Valid.pindah(evt,RiwayatHidupSingkat,EkspresiWajah);
    }//GEN-LAST:event_PenampilanKeyPressed

    private void EkspresiWajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkspresiWajahKeyPressed
        Valid.pindah(evt,Penampilan,SuasanaHati);
    }//GEN-LAST:event_EkspresiWajahKeyPressed

    private void SuasanaHatiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuasanaHatiKeyPressed
        Valid.pindah(evt,EkspresiWajah,TingkahLaku);
    }//GEN-LAST:event_SuasanaHatiKeyPressed

    private void TingkahLakuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TingkahLakuKeyPressed
        Valid.pindah(evt,SuasanaHati,FungsiUmum);
    }//GEN-LAST:event_TingkahLakuKeyPressed

    private void FungsiUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FungsiUmumKeyPressed
        Valid.pindah(evt,TingkahLaku,FungsiIntelektual);
    }//GEN-LAST:event_FungsiUmumKeyPressed

    private void FungsiIntelektualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FungsiIntelektualKeyPressed
        Valid.pindah(evt,FungsiUmum,Pengalaman);
    }//GEN-LAST:event_FungsiIntelektualKeyPressed

    private void PengalamanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengalamanKeyPressed
        Valid.pindah(evt,FungsiIntelektual,Lainnya);
    }//GEN-LAST:event_PengalamanKeyPressed

    private void LainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainnyaKeyPressed
        Valid.pindah(evt,Pengalaman,Delusi);
    }//GEN-LAST:event_LainnyaKeyPressed

    private void DelusiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DelusiKeyPressed
        Valid.pindah(evt,Lainnya,ProsesPikiran);
    }//GEN-LAST:event_DelusiKeyPressed

    private void ProsesPikiranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsesPikiranKeyPressed
        Valid.pindah(evt,Delusi,Halusinasi);
    }//GEN-LAST:event_ProsesPikiranKeyPressed

    private void HalusinasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HalusinasiKeyPressed
        Valid.pindah(evt,ProsesPikiran,Afek);
    }//GEN-LAST:event_HalusinasiKeyPressed

    private void AfekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AfekKeyPressed
        Valid.pindah(evt,Halusinasi,Insight);
    }//GEN-LAST:event_AfekKeyPressed

    private void InsightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InsightKeyPressed
        Valid.pindah(evt,Afek,Kesadaran);
    }//GEN-LAST:event_InsightKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,Insight,Orientasi);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void OrientasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OrientasiKeyPressed
        Valid.pindah(evt,Kesadaran,Atensi);
    }//GEN-LAST:event_OrientasiKeyPressed

    private void AtensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AtensiKeyPressed
        Valid.pindah(evt,Orientasi,KontrolImpuls);
    }//GEN-LAST:event_AtensiKeyPressed

    private void KontrolImpulsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontrolImpulsKeyPressed
        Valid.pindah(evt,Atensi,TanggalPelaksanaan);
    }//GEN-LAST:event_KontrolImpulsKeyPressed

    private void NamaTesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaTesKeyPressed
        Valid.pindah(evt,TanggalPelaksanaan,HasilTes);
    }//GEN-LAST:event_NamaTesKeyPressed

    private void HasilTesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilTesKeyPressed
        Valid.pindah(evt,NamaTes,DinamikaPsikologis);
    }//GEN-LAST:event_HasilTesKeyPressed

    private void TahapanIntevensi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TahapanIntevensi1KeyPressed
        Valid.pindah(evt,RencanaIntervensi,TargetTerapi1);
    }//GEN-LAST:event_TahapanIntevensi1KeyPressed

    private void TahapanIntevensi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TahapanIntevensi2KeyPressed
        Valid.pindah(evt,TargetTerapi1,TargetTerapi2);
    }//GEN-LAST:event_TahapanIntevensi2KeyPressed

    private void TahapanIntevensi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TahapanIntevensi3KeyPressed
        Valid.pindah(evt,TargetTerapi2,TargetTerapi3);
    }//GEN-LAST:event_TahapanIntevensi3KeyPressed

    private void TahapanIntevensi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TahapanIntevensi4KeyPressed
        Valid.pindah(evt,TargetTerapi3,TargetTerapi4);
    }//GEN-LAST:event_TahapanIntevensi4KeyPressed

    private void TahapanIntevensi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TahapanIntevensi5KeyPressed
        Valid.pindah(evt,TargetTerapi4,TargetTerapi5);
    }//GEN-LAST:event_TahapanIntevensi5KeyPressed

    private void TahapanIntevensi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TahapanIntevensi6KeyPressed
        Valid.pindah(evt,TargetTerapi5,TargetTerapi6);
    }//GEN-LAST:event_TahapanIntevensi6KeyPressed

    private void TahapanIntevensi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TahapanIntevensi7KeyPressed
        Valid.pindah(evt,TargetTerapi6,TargetTerapi7);
    }//GEN-LAST:event_TahapanIntevensi7KeyPressed

    private void TargetTerapi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TargetTerapi1KeyPressed
        Valid.pindah(evt,TahapanIntevensi1,TahapanIntevensi2);
    }//GEN-LAST:event_TargetTerapi1KeyPressed

    private void TargetTerapi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TargetTerapi2KeyPressed
        Valid.pindah(evt,TahapanIntevensi2,TahapanIntevensi3);
    }//GEN-LAST:event_TargetTerapi2KeyPressed

    private void TargetTerapi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TargetTerapi3KeyPressed
        Valid.pindah(evt,TahapanIntevensi3,TahapanIntevensi4);
    }//GEN-LAST:event_TargetTerapi3KeyPressed

    private void TargetTerapi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TargetTerapi4KeyPressed
        Valid.pindah(evt,TahapanIntevensi4,TahapanIntevensi5);
    }//GEN-LAST:event_TargetTerapi4KeyPressed

    private void TargetTerapi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TargetTerapi5KeyPressed
        Valid.pindah(evt,TahapanIntevensi5,TahapanIntevensi6);
    }//GEN-LAST:event_TargetTerapi5KeyPressed

    private void TargetTerapi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TargetTerapi6KeyPressed
        Valid.pindah(evt,TahapanIntevensi6,TahapanIntevensi7);
    }//GEN-LAST:event_TargetTerapi6KeyPressed

    private void TargetTerapi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TargetTerapi7KeyPressed
        Valid.pindah(evt,TahapanIntevensi7,Evaluasi);
    }//GEN-LAST:event_TargetTerapi7KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianPsikologiKlinis dialog = new RMPenilaianPsikologiKlinis(new javax.swing.JFrame(), true);
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
    private widget.TextBox Afek;
    private widget.TextBox AlasanPermasalahan;
    private widget.TextBox Atensi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Delusi;
    private widget.TextArea DiagnosaPsikologis;
    private widget.ComboBox Dikirimdari;
    private widget.TextArea DinamikaPsikologis;
    private widget.TextBox EkspektasiMasalah;
    private widget.TextBox EkspresiWajah;
    private widget.TextArea Evaluasi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox FungsiIntelektual;
    private widget.TextBox FungsiUmum;
    private widget.TextBox Halusinasi;
    private widget.TextBox HasilTes;
    private widget.ComboBox Informasi;
    private widget.TextBox Insight;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox Kesadaran;
    private javax.swing.JTextArea KetAlloAuto;
    private widget.TextBox KontrolImpuls;
    private widget.Label LCount;
    private widget.TextBox Lainnya;
    private widget.editorpane LoadHTML;
    private widget.TextArea ManifestasiFungsiPsikologis;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NamaTes;
    private widget.TextBox NmPetugas;
    private widget.TextBox Orientasi;
    private widget.TextBox Penampilan;
    private widget.TextBox Pengalaman;
    private widget.ComboBox PermasalahanSaatIni;
    private widget.TextBox ProsesPikiran;
    private widget.TextArea RencanaIntervensi;
    private widget.TextArea RiwayatHidupSingkat;
    private widget.TextArea RiwayatKeluhan;
    private widget.TextArea RiwayatPenyakit;
    private widget.ScrollPane Scroll;
    private widget.TextBox SuasanaHati;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TahapanIntevensi1;
    private widget.TextBox TahapanIntevensi2;
    private widget.TextBox TahapanIntevensi3;
    private widget.TextBox TahapanIntevensi4;
    private widget.TextBox TahapanIntevensi5;
    private widget.TextBox TahapanIntevensi6;
    private widget.TextBox TahapanIntevensi7;
    private widget.Tanggal TanggalPelaksanaan;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TargetTerapi1;
    private widget.TextBox TargetTerapi2;
    private widget.TextBox TargetTerapi3;
    private widget.TextBox TargetTerapi4;
    private widget.TextBox TargetTerapi5;
    private widget.TextBox TargetTerapi6;
    private widget.TextBox TargetTerapi7;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox TingkahLaku;
    private widget.ComboBox TujuanPemeriksaan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel11;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel12;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel13;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel138;
    private widget.Label jLabel139;
    private widget.Label jLabel14;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel42;
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
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane18;
    private widget.ScrollPane scrollPane19;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane21;
    private widget.ScrollPane scrollPane22;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_psikologi_klinis.tanggal,"+
                        "penilaian_psikologi_klinis.nip,penilaian_psikologi_klinis.anamnesis,penilaian_psikologi_klinis.dikirim_dari,penilaian_psikologi_klinis.tujuan_pemeriksaan,penilaian_psikologi_klinis.ket_anamnesis,penilaian_psikologi_klinis.rupa,penilaian_psikologi_klinis.bentuk_tubuh,penilaian_psikologi_klinis.tindakan,"+
                        "penilaian_psikologi_klinis.pakaian,penilaian_psikologi_klinis.ekspresi,penilaian_psikologi_klinis.berbicara,penilaian_psikologi_klinis.penggunaan_kata,penilaian_psikologi_klinis.ciri_menyolok,penilaian_psikologi_klinis.hasil_psikotes,penilaian_psikologi_klinis.kepribadian,penilaian_psikologi_klinis.psikodinamika,penilaian_psikologi_klinis.kesimpulan_psikolog,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_psikologi_klinis on reg_periksa.no_rawat=penilaian_psikologi_klinis.no_rawat "+
                        "inner join petugas on penilaian_psikologi_klinis.nip=petugas.nip where "+
                        "penilaian_psikologi_klinis.tanggal between ? and ? order by penilaian_psikologi_klinis.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_psikologi_klinis.tanggal,"+
                        "penilaian_psikologi_klinis.nip,penilaian_psikologi_klinis.anamnesis,penilaian_psikologi_klinis.dikirim_dari,penilaian_psikologi_klinis.tujuan_pemeriksaan,penilaian_psikologi_klinis.ket_anamnesis,penilaian_psikologi_klinis.rupa,penilaian_psikologi_klinis.bentuk_tubuh,penilaian_psikologi_klinis.tindakan,"+
                        "penilaian_psikologi_klinis.pakaian,penilaian_psikologi_klinis.ekspresi,penilaian_psikologi_klinis.berbicara,penilaian_psikologi_klinis.penggunaan_kata,penilaian_psikologi_klinis.ciri_menyolok,penilaian_psikologi_klinis.hasil_psikotes,penilaian_psikologi_klinis.kepribadian,penilaian_psikologi_klinis.psikodinamika,penilaian_psikologi_klinis.kesimpulan_psikolog,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_psikologi_klinis on reg_periksa.no_rawat=penilaian_psikologi_klinis.no_rawat "+
                        "inner join petugas on penilaian_psikologi_klinis.nip=petugas.nip where "+
                        "penilaian_psikologi_klinis.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_psikologi_klinis.nip like ? or petugas.nama like ?) order by penilaian_psikologi_klinis.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),
                        rs.getString("dikirim_dari"),rs.getString("tujuan_pemeriksaan"),rs.getString("anamnesis"),rs.getString("ket_anamnesis"),rs.getString("rupa"),rs.getString("bentuk_tubuh"),rs.getString("tindakan"),
                        rs.getString("pakaian"),rs.getString("ekspresi"),rs.getString("berbicara"),rs.getString("penggunaan_kata"),rs.getString("ciri_menyolok"),rs.getString("hasil_psikotes"),rs.getString("kepribadian"),rs.getString("psikodinamika"),rs.getString("kesimpulan_psikolog")                     
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
        Informasi.setSelectedIndex(0);
        Dikirimdari.setSelectedIndex(0);
        TujuanPemeriksaan.setSelectedIndex(0);
        KetAlloAuto.setText("");
        KeluhanUtama.setText("");
        RiwayatPenyakit.setText("");
        RiwayatKeluhan.setText("");
        PermasalahanSaatIni.setSelectedIndex(0);
        AlasanPermasalahan.setText("");
        EkspektasiMasalah.setText("");
        RiwayatHidupSingkat.setText("");
        Penampilan.setText("");
        EkspresiWajah.setText("");
        SuasanaHati.setText("");
        TingkahLaku.setText("");
        FungsiUmum.setText("");
        FungsiIntelektual.setText("");
        Pengalaman.setText("");
        Lainnya.setText("");
        Delusi.setText("");
        ProsesPikiran.setText("");
        Halusinasi.setText("");
        Afek.setText("");
        Insight.setText("");
        Kesadaran.setText("");
        Orientasi.setText("");
        Atensi.setText("");
        KontrolImpuls.setText("");
        TanggalPelaksanaan.setDate(new Date());
        NamaTes.setText("");
        HasilTes.setText("");
        DinamikaPsikologis.setText("");
        DiagnosaPsikologis.setText("");
        ManifestasiFungsiPsikologis.setText("");
        RencanaIntervensi.setText("");
        TahapanIntevensi1.setText("");
        TargetTerapi1.setText("");
        TahapanIntevensi2.setText("");
        TargetTerapi2.setText("");
        TahapanIntevensi3.setText("");
        TargetTerapi3.setText("");
        TahapanIntevensi4.setText("");
        TargetTerapi4.setText("");
        TahapanIntevensi5.setText("");
        TargetTerapi5.setText("");
        TahapanIntevensi6.setText("");
        TargetTerapi6.setText("");
        TahapanIntevensi7.setText("");
        TargetTerapi7.setText("");
        Evaluasi.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        TujuanPemeriksaan.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Dikirimdari.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            TujuanPemeriksaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KetAlloAuto.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            RiwayatPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            RiwayatKeluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            PermasalahanSaatIni.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            AlasanPermasalahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
            EkspektasiMasalah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()); 
            RiwayatHidupSingkat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            Penampilan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString()); 
            EkspresiWajah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString()); 
            SuasanaHati.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString()); 
            TingkahLaku.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString()); 
            FungsiUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString()); 
            FungsiIntelektual.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString()); 
            Pengalaman.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString()); 
            Lainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString()); 
            Delusi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString()); 
            ProsesPikiran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString()); 
            Halusinasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString()); 
            Afek.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString()); 
            Insight.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString()); 
            Kesadaran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString()); 
            Orientasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString()); 
            Atensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString()); 
            KontrolImpuls.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString()); 
            NamaTes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString()); 
            HasilTes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString()); 
            DinamikaPsikologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString()); 
            DiagnosaPsikologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString()); 
            ManifestasiFungsiPsikologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString()); 
            RencanaIntervensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString()); 
            TahapanIntevensi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString()); 
            TargetTerapi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString()); 
            TahapanIntevensi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString()); 
            TargetTerapi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString()); 
            TahapanIntevensi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString()); 
            TargetTerapi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString()); 
            TahapanIntevensi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString()); 
            TargetTerapi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString()); 
            TahapanIntevensi5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString()); 
            TargetTerapi5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString()); 
            TahapanIntevensi6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString()); 
            TargetTerapi6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString()); 
            TahapanIntevensi7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString()); 
            TargetTerapi7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString()); 
            Evaluasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString()); 
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Valid.SetTgl(TanggalPelaksanaan,tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_psikologi_klinis());
        BtnHapus.setEnabled(akses.getpenilaian_psikologi_klinis());
        BtnEdit.setEnabled(akses.getpenilaian_psikologi_klinis());
        BtnEdit.setEnabled(akses.getpenilaian_psikologi_klinis());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }  
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                TglAsuhan.setEditable(false);
                TglAsuhan.setEnabled(false);
            }
        }
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_psikologi_klinis where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_psikologi_klinis","no_rawat=?","no_rawat=?,tanggal=?,nip=?,anamnesis=?,dikirim_dari=?,tujuan_pemeriksaan=?,ket_anamnesis=?,keluhan_utama=?,riwayat_penyakit=?,riwayat_keluhan=?,"+
            "permasalahan_saat_ini=?,permasalahan_alasan=?,permasalahan_ekspektasi=?,riwayat_hidup_singkat=?,kondisi_psikologis_penampilan=?,kondisi_psikologis_ekspresi_wajah=?,kondisi_psikologis_suasana_hati=?,"+
            "kondisi_psikologis_tingkah_laku=?,kondisi_psikologis_fungsi_umum=?,kondisi_psikologis_fungsi_intelektual=?,kondisi_psikologis_pengalaman=?,kondisi_psikologis_lainnya=?,kondisi_patologis_delusi=?,"+
            "kondisi_patologis_proses_pikiran=?,kondisi_patologis_halusinasi=?,kondisi_patologis_afek=?,kondisi_patologis_insight=?,kondisi_patologis_kesadaran=?,kondisi_patologis_orientasi=?,kondisi_patologis_atensi=?,"+
            "kondisi_patologis_kontrol_impuls=?,psikotes_tanggal_pelaksanaan=?,psikotes_nama_tes=?,psikotes_hasil=?,dinamika_psikologis=?,diagnosa_psikologis=?,manifestasi_fungsi_psikologis=?,rencana_intervensi=?,"+
            "tahapan_intervensi1=?,target_terapi1=?,tahapan_intervensi2=?,target_terapi2=?,tahapan_intervensi3=?,target_terapi3=?,tahapan_intervensi4=?,target_terapi4=?,tahapan_intervensi5=?,target_terapi5=?,"+
            "tahapan_intervensi6=?,target_terapi6=?,tahapan_intervensi7=?,target_terapi7=?,evaluasi=?",54,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdPetugas.getText(),Informasi.getSelectedItem().toString(),
                Dikirimdari.getSelectedItem().toString(),TujuanPemeriksaan.getSelectedItem().toString(),KetAlloAuto.getText(),KeluhanUtama.getText(),RiwayatPenyakit.getText(),RiwayatKeluhan.getText(),
                PermasalahanSaatIni.getSelectedItem().toString(),AlasanPermasalahan.getText(),EkspektasiMasalah.getText(),RiwayatHidupSingkat.getText(),Penampilan.getText(),EkspresiWajah.getText(),
                SuasanaHati.getText(),TingkahLaku.getText(),FungsiUmum.getText(),FungsiIntelektual.getText(),Pengalaman.getText(),Lainnya.getText(),Delusi.getText(),ProsesPikiran.getText(),
                Halusinasi.getText(),Afek.getText(),Insight.getText(),Kesadaran.getText(),Orientasi.getText(),Atensi.getText(),KontrolImpuls.getText(),Valid.SetTgl(TanggalPelaksanaan.getSelectedItem()+""),
                NamaTes.getText(),HasilTes.getText(),DinamikaPsikologis.getText(),DiagnosaPsikologis.getText(),ManifestasiFungsiPsikologis.getText(),RencanaIntervensi.getText(),TahapanIntevensi1.getText(),
                TargetTerapi1.getText(),TahapanIntevensi2.getText(),TargetTerapi2.getText(),TahapanIntevensi3.getText(),TargetTerapi3.getText(),TahapanIntevensi4.getText(),TargetTerapi4.getText(),
                TahapanIntevensi5.getText(),TargetTerapi5.getText(),TahapanIntevensi6.getText(),TargetTerapi6.getText(),TahapanIntevensi7.getText(),TargetTerapi7.getText(),Evaluasi.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(Dikirimdari.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(TujuanPemeriksaan.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(Informasi.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(KetAlloAuto.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(RiwayatPenyakit.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(RiwayatKeluhan.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(PermasalahanSaatIni.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(AlasanPermasalahan.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(EkspektasiMasalah.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(RiwayatHidupSingkat.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(Penampilan.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(EkspresiWajah.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(SuasanaHati.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(TingkahLaku.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(FungsiUmum.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(FungsiIntelektual.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(Pengalaman.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(Lainnya.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(Delusi.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(ProsesPikiran.getText(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(Halusinasi.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(Afek.getText(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(Insight.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(Kesadaran.getText(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(Orientasi.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(Atensi.getText(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(KontrolImpuls.getText(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(Valid.SetTgl(TanggalPelaksanaan.getSelectedItem()+""),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(NamaTes.getText(),tbObat.getSelectedRow(),37);
                tbObat.setValueAt(HasilTes.getText(),tbObat.getSelectedRow(),38);
                tbObat.setValueAt(DinamikaPsikologis.getText(),tbObat.getSelectedRow(),39);
                tbObat.setValueAt(DiagnosaPsikologis.getText(),tbObat.getSelectedRow(),40);
                tbObat.setValueAt(ManifestasiFungsiPsikologis.getText(),tbObat.getSelectedRow(),41);
                tbObat.setValueAt(RencanaIntervensi.getText(),tbObat.getSelectedRow(),42);
                tbObat.setValueAt(TahapanIntevensi1.getText(),tbObat.getSelectedRow(),43);
                tbObat.setValueAt(TargetTerapi1.getText(),tbObat.getSelectedRow(),44);
                tbObat.setValueAt(TahapanIntevensi2.getText(),tbObat.getSelectedRow(),45);
                tbObat.setValueAt(TargetTerapi2.getText(),tbObat.getSelectedRow(),46);
                tbObat.setValueAt(TahapanIntevensi3.getText(),tbObat.getSelectedRow(),47);
                tbObat.setValueAt(TargetTerapi3.getText(),tbObat.getSelectedRow(),48);
                tbObat.setValueAt(TahapanIntevensi4.getText(),tbObat.getSelectedRow(),49);
                tbObat.setValueAt(TargetTerapi4.getText(),tbObat.getSelectedRow(),50);
                tbObat.setValueAt(TahapanIntevensi5.getText(),tbObat.getSelectedRow(),51);
                tbObat.setValueAt(TargetTerapi5.getText(),tbObat.getSelectedRow(),52);
                tbObat.setValueAt(TahapanIntevensi6.getText(),tbObat.getSelectedRow(),53);
                tbObat.setValueAt(TargetTerapi6.getText(),tbObat.getSelectedRow(),54);
                tbObat.setValueAt(TahapanIntevensi7.getText(),tbObat.getSelectedRow(),55);
                tbObat.setValueAt(TargetTerapi7.getText(),tbObat.getSelectedRow(),56);
                tbObat.setValueAt(Evaluasi.getText(),tbObat.getSelectedRow(),57);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("penilaian_psikologi_klinis","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",53,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdPetugas.getText(),Informasi.getSelectedItem().toString(),
                Dikirimdari.getSelectedItem().toString(),TujuanPemeriksaan.getSelectedItem().toString(),KetAlloAuto.getText(),KeluhanUtama.getText(),RiwayatPenyakit.getText(),RiwayatKeluhan.getText(),
                PermasalahanSaatIni.getSelectedItem().toString(),AlasanPermasalahan.getText(),EkspektasiMasalah.getText(),RiwayatHidupSingkat.getText(),Penampilan.getText(),EkspresiWajah.getText(),
                SuasanaHati.getText(),TingkahLaku.getText(),FungsiUmum.getText(),FungsiIntelektual.getText(),Pengalaman.getText(),Lainnya.getText(),Delusi.getText(),ProsesPikiran.getText(),
                Halusinasi.getText(),Afek.getText(),Insight.getText(),Kesadaran.getText(),Orientasi.getText(),Atensi.getText(),KontrolImpuls.getText(),Valid.SetTgl(TanggalPelaksanaan.getSelectedItem()+""),
                NamaTes.getText(),HasilTes.getText(),DinamikaPsikologis.getText(),DiagnosaPsikologis.getText(),ManifestasiFungsiPsikologis.getText(),RencanaIntervensi.getText(),TahapanIntevensi1.getText(),
                TargetTerapi1.getText(),TahapanIntevensi2.getText(),TargetTerapi2.getText(),TahapanIntevensi3.getText(),TargetTerapi3.getText(),TahapanIntevensi4.getText(),TargetTerapi4.getText(),
                TahapanIntevensi5.getText(),TargetTerapi5.getText(),TahapanIntevensi6.getText(),TargetTerapi6.getText(),TahapanIntevensi7.getText(),TargetTerapi7.getText(),Evaluasi.getText()
            })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),
                    Dikirimdari.getSelectedItem().toString(),TujuanPemeriksaan.getSelectedItem().toString(),Informasi.getSelectedItem().toString(),KetAlloAuto.getText(),KeluhanUtama.getText(),RiwayatPenyakit.getText(),RiwayatKeluhan.getText(),
                    PermasalahanSaatIni.getSelectedItem().toString(),AlasanPermasalahan.getText(),EkspektasiMasalah.getText(),RiwayatHidupSingkat.getText(),Penampilan.getText(),EkspresiWajah.getText(),SuasanaHati.getText(),TingkahLaku.getText(),
                    FungsiUmum.getText(),FungsiIntelektual.getText(),Pengalaman.getText(),Lainnya.getText(),Delusi.getText(),ProsesPikiran.getText(),Halusinasi.getText(),Afek.getText(),Insight.getText(),Kesadaran.getText(),Orientasi.getText(),
                    Atensi.getText(),KontrolImpuls.getText(),Valid.SetTgl(TanggalPelaksanaan.getSelectedItem()+""),NamaTes.getText(),HasilTes.getText(),DinamikaPsikologis.getText(),DiagnosaPsikologis.getText(),ManifestasiFungsiPsikologis.getText(),
                    RencanaIntervensi.getText(),TahapanIntevensi1.getText(),TargetTerapi1.getText(),TahapanIntevensi2.getText(),TargetTerapi2.getText(),TahapanIntevensi3.getText(),TargetTerapi3.getText(),TahapanIntevensi4.getText(),TargetTerapi4.getText(),
                    TahapanIntevensi5.getText(),TargetTerapi5.getText(),TahapanIntevensi6.getText(),TargetTerapi6.getText(),TahapanIntevensi7.getText(),TargetTerapi7.getText(),Evaluasi.getText()
                });
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
        }
    }
}

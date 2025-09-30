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
import kepegawaian.DlgCariDokter;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalMedisRalanUrologi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalMedisRalanUrologi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Keluhan Utama","Riwayat Penyakit Sekarang","Riwayat Penyakit Keluarga","Riwayat Penyakit Dahulu",
            "Riwayat Penggunakan Obat","Riwayat Kebiasaan","Riwayat Operasi Urologi","Riwayat Alergi","TD(mmHg)","BB(Kg)","TB(Cm)","Suhu","Nadi(x/menit)","RR(x/menit)","Keadaan Umum","Nyeri","Status Nutrisi",
            "Thoraks","Keterangan Thoraks","Abdomen","Keterangan Abdomen","Ekstremitas","Keterangan Ekstrimitas","Nyeri ketok CVA","Genitalia Eksternal","Colok Dubur","Status Kelainan Lainnya","Urinalisis",
            "Darah (Hb, Ureum, Kreatinin, Elektrolit)","USG Urologi","Radiologi (IVP, CT, MRI)","Penunjang Lainnya","Asesmen Kerja","Asesmen Banding","Permasalahan","Terapi/Pengobatan","Tindakan/Rencana Tindakan","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 48; i++) {
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
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(250);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else if(i==12){
                column.setPreferredWidth(200);
            }else if(i==13){
                column.setPreferredWidth(200);
            }else if(i==14){
                column.setPreferredWidth(200);
            }else if(i==15){
                column.setPreferredWidth(200);
            }else if(i==16){
                column.setPreferredWidth(200);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(60);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(45);
            }else if(i==21){
                column.setPreferredWidth(45);
            }else if(i==22){
                column.setPreferredWidth(75);
            }else if(i==23){
                column.setPreferredWidth(68);
            }else if(i==24){
                column.setPreferredWidth(87);
            }else if(i==25){
                column.setPreferredWidth(150);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(130);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(130);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(130);
            }else if(i==33){
                column.setPreferredWidth(130);
            }else if(i==34){
                column.setPreferredWidth(130);
            }else if(i==35){
                column.setPreferredWidth(130);
            }else if(i==36){
                column.setPreferredWidth(200);
            }else if(i==37){
                column.setPreferredWidth(200);
            }else if(i==38){
                column.setPreferredWidth(200);
            }else if(i==39){
                column.setPreferredWidth(200);
            }else if(i==40){
                column.setPreferredWidth(200);
            }else if(i==41){
                column.setPreferredWidth(200);
            }else if(i==42){
                column.setPreferredWidth(200);
            }else if(i==43){
                column.setPreferredWidth(200);
            }else if(i==44){
                column.setPreferredWidth(250);
            }else if(i==45){
                column.setPreferredWidth(250);
            }else if(i==46){
                column.setPreferredWidth(250);
            }else if(i==47){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)2000).getKata(RPS));
        RPD.setDocument(new batasInput((int)1000).getKata(RPD));
        RPK.setDocument(new batasInput((int)1000).getKata(RPK));
        RPO.setDocument(new batasInput((int)1000).getKata(RPO));
        RiwayatKebiasaan.setDocument(new batasInput((int)1000).getKata(RiwayatKebiasaan));
        RiwayatOperasiUrologi.setDocument(new batasInput((int)1000).getKata(RiwayatOperasiUrologi));
        Alergi.setDocument(new batasInput((int)50).getKata(Alergi));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        Nyeri.setDocument(new batasInput((byte)50).getKata(Nyeri));
        StatusNutrisi.setDocument(new batasInput((byte)50).getKata(StatusNutrisi));
        KeteranganThoraks.setDocument(new batasInput((byte)50).getKata(KeteranganThoraks));
        KeteranganAbdomen.setDocument(new batasInput((byte)50).getKata(KeteranganAbdomen));
        KeteranganEkstrimitas.setDocument(new batasInput((byte)50).getKata(KeteranganEkstrimitas));
        NyeriKetokCVA.setDocument(new batasInput((int)100).getKata(NyeriKetokCVA));
        GenitaliaEksternal.setDocument(new batasInput((int)100).getKata(GenitaliaEksternal));
        ColokDubur.setDocument(new batasInput((int)100).getKata(ColokDubur));
        Lainnya.setDocument(new batasInput((int)1000).getKata(Lainnya));
        Urinalisis.setDocument(new batasInput((int)500).getKata(Urinalisis));
        Darah.setDocument(new batasInput((int)500).getKata(Darah));
        USGUrologi.setDocument(new batasInput((int)500).getKata(USGUrologi));
        Radiologi.setDocument(new batasInput((int)500).getKata(Radiologi));
        PenunjangLainnya.setDocument(new batasInput((int)500).getKata(PenunjangLainnya));
        Diagnosis.setDocument(new batasInput((int)500).getKata(Diagnosis));
        Diagnosis2.setDocument(new batasInput((int)500).getKata(Diagnosis2));
        Permasalahan.setDocument(new batasInput((int)500).getKata(Permasalahan));
        Terapi.setDocument(new batasInput((int)500).getKata(Terapi));
        Tindakan.setDocument(new batasInput((int)500).getKata(Tindakan));
        Edukasi.setDocument(new batasInput((int)500).getKata(Edukasi));
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
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDokter.requestFocus();
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
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        Anamnesis = new widget.ComboBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel101 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel95 = new widget.Label();
        jLabel9 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        Alergi = new widget.TextBox();
        jLabel37 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel40 = new widget.Label();
        Thoraks = new widget.ComboBox();
        jLabel47 = new widget.Label();
        jLabel46 = new widget.Label();
        Abdomen = new widget.ComboBox();
        jLabel49 = new widget.Label();
        Ekstrimitas = new widget.ComboBox();
        KeteranganThoraks = new widget.TextBox();
        KeteranganAbdomen = new widget.TextBox();
        KeteranganEkstrimitas = new widget.TextBox();
        scrollPane5 = new widget.ScrollPane();
        Lainnya = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        scrollPane12 = new widget.ScrollPane();
        USGUrologi = new widget.TextArea();
        jLabel105 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Urinalisis = new widget.TextArea();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        Darah = new widget.TextArea();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel106 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        scrollPane16 = new widget.ScrollPane();
        Diagnosis2 = new widget.TextArea();
        jLabel82 = new widget.Label();
        jLabel85 = new widget.Label();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel107 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        jLabel108 = new widget.Label();
        jLabel112 = new widget.Label();
        scrollPane20 = new widget.ScrollPane();
        Permasalahan = new widget.TextArea();
        scrollPane19 = new widget.ScrollPane();
        Tindakan = new widget.TextArea();
        jLabel113 = new widget.Label();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel109 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        jLabel14 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel15 = new widget.Label();
        TB = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        StatusNutrisi = new widget.TextBox();
        jLabel29 = new widget.Label();
        Nyeri = new widget.TextBox();
        jLabel39 = new widget.Label();
        Keadaan = new widget.ComboBox();
        jLabel41 = new widget.Label();
        jLabel32 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        RiwayatOperasiUrologi = new widget.TextArea();
        jLabel34 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RiwayatKebiasaan = new widget.TextArea();
        jLabel50 = new widget.Label();
        NyeriKetokCVA = new widget.TextBox();
        GenitaliaEksternal = new widget.TextBox();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        ColokDubur = new widget.TextBox();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        PenunjangLainnya = new widget.TextArea();
        scrollPane13 = new widget.ScrollPane();
        Radiologi = new widget.TextArea();
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
        MnPenilaianMedis.setText("Laporan Pengkajian Medis");
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Awal Medis Rawat Jalan Urologi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
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
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1043));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 40, 180, 23);

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
        BtnDokter.setBounds(348, 40, 28, 23);

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
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. STATUS KELAINAN & TEMUAN UROLOGI SPESIFIK");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 370, 270, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 40, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 290, 880, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("IV. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 540, 190, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-09-2025 20:50:33" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(436, 40, 130, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 290, 180, 23);

        jLabel9.setText("Riwayat Penyakit Keluarga :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 140, 180, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(129, 90, 310, 43);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(440, 90, 150, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(594, 140, 260, 43);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(440, 140, 150, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RPK);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(184, 140, 255, 43);

        jLabel33.setText("Keluhan Utama :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 90, 125, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(RPS);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(594, 90, 260, 43);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(594, 240, 260, 23);

        jLabel37.setText("Riwayat Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(470, 240, 120, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 370, 880, 1);

        jLabel40.setText("Thoraks :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 390, 109, 23);

        Thoraks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thoraks.setSelectedIndex(2);
        Thoraks.setName("Thoraks"); // NOI18N
        Thoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraksKeyPressed(evt);
            }
        });
        FormInput.add(Thoraks);
        Thoraks.setBounds(113, 390, 128, 23);

        jLabel47.setText("Lainnya :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(480, 420, 60, 23);

        jLabel46.setText("Abdomen :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 420, 109, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setSelectedIndex(2);
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(113, 420, 128, 23);

        jLabel49.setText("Ekstremitas :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 450, 109, 23);

        Ekstrimitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstrimitas.setSelectedIndex(2);
        Ekstrimitas.setName("Ekstrimitas"); // NOI18N
        Ekstrimitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstrimitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstrimitas);
        Ekstrimitas.setBounds(113, 450, 128, 23);

        KeteranganThoraks.setFocusTraversalPolicyProvider(true);
        KeteranganThoraks.setName("KeteranganThoraks"); // NOI18N
        KeteranganThoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganThoraksKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganThoraks);
        KeteranganThoraks.setBounds(245, 390, 211, 23);

        KeteranganAbdomen.setFocusTraversalPolicyProvider(true);
        KeteranganAbdomen.setName("KeteranganAbdomen"); // NOI18N
        KeteranganAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAbdomen);
        KeteranganAbdomen.setBounds(245, 420, 211, 23);

        KeteranganEkstrimitas.setFocusTraversalPolicyProvider(true);
        KeteranganEkstrimitas.setName("KeteranganEkstrimitas"); // NOI18N
        KeteranganEkstrimitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEkstrimitasKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEkstrimitas);
        KeteranganEkstrimitas.setBounds(245, 450, 211, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Lainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Lainnya.setColumns(20);
        Lainnya.setRows(13);
        Lainnya.setName("Lainnya"); // NOI18N
        Lainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainnyaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Lainnya);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(544, 420, 310, 113);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 540, 880, 1);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        USGUrologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        USGUrologi.setColumns(20);
        USGUrologi.setRows(5);
        USGUrologi.setName("USGUrologi"); // NOI18N
        USGUrologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGUrologiKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(USGUrologi);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(594, 580, 260, 43);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("USG Urologi :");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(594, 560, 190, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Urinalisis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Urinalisis.setColumns(20);
        Urinalisis.setRows(5);
        Urinalisis.setName("Urinalisis"); // NOI18N
        Urinalisis.setPreferredSize(new java.awt.Dimension(102, 52));
        Urinalisis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrinalisisKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Urinalisis);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(44, 580, 260, 43);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Urinalisis :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 560, 150, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Darah (Hb, Ureum, Kreatinin, Elektrolit) :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(319, 560, 230, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        Darah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Darah.setColumns(20);
        Darah.setRows(5);
        Darah.setName("Darah"); // NOI18N
        Darah.setPreferredSize(new java.awt.Dimension(102, 52));
        Darah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(Darah);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(319, 580, 260, 43);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 700, 880, 1);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("V. DIAGNOSIS/ASESMEN");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(10, 700, 190, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Diagnosis);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 740, 400, 43);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        Diagnosis2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis2.setColumns(20);
        Diagnosis2.setRows(3);
        Diagnosis2.setName("Diagnosis2"); // NOI18N
        Diagnosis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosis2KeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Diagnosis2);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(454, 740, 400, 43);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Asesmen Kerja :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(44, 720, 150, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Asesmen Banding :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(454, 720, 150, 23);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(0, 790, 880, 1);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("VI. PERMASALAHAN & TATALAKSANA");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 790, 190, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        Terapi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Terapi.setColumns(20);
        Terapi.setRows(3);
        Terapi.setName("Terapi"); // NOI18N
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(Terapi);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(454, 830, 400, 43);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Terapi/Pengobatan :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(454, 810, 190, 20);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Permasalahan :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(44, 810, 190, 20);

        scrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane20.setName("scrollPane20"); // NOI18N

        Permasalahan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Permasalahan.setColumns(20);
        Permasalahan.setRows(3);
        Permasalahan.setName("Permasalahan"); // NOI18N
        Permasalahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PermasalahanKeyPressed(evt);
            }
        });
        scrollPane20.setViewportView(Permasalahan);

        FormInput.add(scrollPane20);
        scrollPane20.setBounds(44, 830, 400, 43);

        scrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane19.setName("scrollPane19"); // NOI18N

        Tindakan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tindakan.setColumns(20);
        Tindakan.setRows(3);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        scrollPane19.setViewportView(Tindakan);

        FormInput.add(scrollPane19);
        scrollPane19.setBounds(44, 900, 810, 43);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Tindakan/Rencana Tindakan :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(44, 880, 320, 20);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 950, 880, 1);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("VII. EDUKASI");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 950, 190, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Edukasi);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 970, 810, 63);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        RPO.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPO.setColumns(20);
        RPO.setRows(5);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(RPO);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(184, 190, 255, 43);

        jLabel14.setText("Riwayat Penggunaan Obat :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 190, 180, 23);

        jLabel12.setText("TB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(321, 310, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(234, 310, 50, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(287, 310, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(670, 310, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(617, 310, 50, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(573, 310, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(445, 310, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(489, 310, 50, 23);

        jLabel22.setText(":");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 310, 64, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(68, 310, 76, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(542, 310, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(147, 310, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(815, 310, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(762, 310, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(718, 310, 40, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("TD");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(44, 310, 30, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Cm");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(408, 310, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(355, 310, 50, 23);

        jLabel27.setText("BB :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(200, 310, 30, 23);

        jLabel28.setText("Status Nutrisi :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(550, 340, 100, 23);

        StatusNutrisi.setFocusTraversalPolicyProvider(true);
        StatusNutrisi.setName("StatusNutrisi"); // NOI18N
        StatusNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(StatusNutrisi);
        StatusNutrisi.setBounds(654, 340, 200, 23);

        jLabel29.setText(":");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 340, 127, 23);

        Nyeri.setFocusTraversalPolicyProvider(true);
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(335, 340, 200, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Keadaan Umum");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(44, 340, 90, 23);

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sehat", "Sakit Ringan", "Sakit Sedang", "Sakit Berat" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        FormInput.add(Keadaan);
        Keadaan.setBounds(131, 340, 118, 23);

        jLabel41.setText("Nyeri :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(281, 340, 50, 23);

        jLabel32.setText("Riwayat Operasi Urologi :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 240, 169, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        RiwayatOperasiUrologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatOperasiUrologi.setColumns(20);
        RiwayatOperasiUrologi.setRows(5);
        RiwayatOperasiUrologi.setName("RiwayatOperasiUrologi"); // NOI18N
        RiwayatOperasiUrologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatOperasiUrologiKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(RiwayatOperasiUrologi);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(173, 240, 266, 43);

        jLabel34.setText("Riwayat Kebiasaan :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(440, 190, 150, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RiwayatKebiasaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatKebiasaan.setColumns(20);
        RiwayatKebiasaan.setRows(5);
        RiwayatKebiasaan.setName("RiwayatKebiasaan"); // NOI18N
        RiwayatKebiasaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatKebiasaanKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RiwayatKebiasaan);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(594, 190, 260, 43);

        jLabel50.setText("Nyeri Ketok CVA :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 480, 144, 23);

        NyeriKetokCVA.setFocusTraversalPolicyProvider(true);
        NyeriKetokCVA.setName("NyeriKetokCVA"); // NOI18N
        NyeriKetokCVA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKetokCVAKeyPressed(evt);
            }
        });
        FormInput.add(NyeriKetokCVA);
        NyeriKetokCVA.setBounds(148, 480, 308, 23);

        GenitaliaEksternal.setFocusTraversalPolicyProvider(true);
        GenitaliaEksternal.setName("GenitaliaEksternal"); // NOI18N
        GenitaliaEksternal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GenitaliaEksternalKeyPressed(evt);
            }
        });
        FormInput.add(GenitaliaEksternal);
        GenitaliaEksternal.setBounds(148, 510, 308, 23);

        jLabel51.setText("Genitalia Eksternal :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 510, 144, 23);

        jLabel52.setText("Colok Dubur :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(460, 390, 80, 23);

        ColokDubur.setFocusTraversalPolicyProvider(true);
        ColokDubur.setName("ColokDubur"); // NOI18N
        ColokDubur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ColokDuburKeyPressed(evt);
            }
        });
        FormInput.add(ColokDubur);
        ColokDubur.setBounds(544, 390, 310, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Radiologi (IVP, CT, MRI) :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(44, 630, 150, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Penunjang Lainnya :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(319, 630, 230, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        PenunjangLainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PenunjangLainnya.setColumns(20);
        PenunjangLainnya.setRows(5);
        PenunjangLainnya.setName("PenunjangLainnya"); // NOI18N
        PenunjangLainnya.setPreferredSize(new java.awt.Dimension(102, 52));
        PenunjangLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangLainnyaKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(PenunjangLainnya);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(319, 650, 535, 43);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Radiologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Radiologi.setColumns(20);
        Radiologi.setRows(5);
        Radiologi.setName("Radiologi"); // NOI18N
        Radiologi.setPreferredSize(new java.awt.Dimension(102, 52));
        Radiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadiologiKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Radiologi);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(44, 650, 260, 43);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pengkajian", internalFrame2);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-09-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-09-2025" }));
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

        TabRawat.addTab("Data Pengkajian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Penggunaan Obat");
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
            Valid.pindah(evt,Edukasi,BtnBatal);
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
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
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Penggunaan Obat");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anamnesis</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hubungan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keluhan Utama</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Sekarang</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Keluarga</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Dahulu</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penggunakan Obat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Kebiasaan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Operasi Urologi :</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Alergi</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TD(mmHg)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BB(Kg)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TB(Cm)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi(x/menit)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>RR(x/menit)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Umum</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Nutrisi</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Thoraks</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Thoraks</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Abdomen</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Abdomen</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ekstremitas</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Ekstrimitas</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri ketok CVA</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Genitalia Eksternal</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Colok Dubur</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Kelainan Lainnya</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Urinalisis</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Darah (Hb, Ureum, Kreatinin, Elektrolit)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>USG Urologi</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Radiologi (IVP, CT, MRI)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penunjang Lainnya</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asesmen Kerja</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asesmen Banding</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Permasalahan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Terapi/Pengobatan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindakan/Rencana Tindakan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Edukasi</b></td>").append(
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
                            "<td valign='top'>").append(tbObat.getValueAt(i,31).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,32).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,33).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,34).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,35).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,36).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,37).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,38).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,39).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,40).toString()).append("</td>").append( 
                            "<td valign='top'>").append(tbObat.getValueAt(i,41).toString()).append("</td>").append( 
                            "<td valign='top'>").append(tbObat.getValueAt(i,42).toString()).append("</td>").append( 
                            "<td valign='top'>").append(tbObat.getValueAt(i,43).toString()).append("</td>").append( 
                            "<td valign='top'>").append(tbObat.getValueAt(i,44).toString()).append("</td>").append( 
                            "<td valign='top'>").append(tbObat.getValueAt(i,45).toString()).append("</td>").append( 
                            "<td valign='top'>").append(tbObat.getValueAt(i,46).toString()).append("</td>").append( 
                            "<td valign='top'>").append(tbObat.getValueAt(i,47).toString()).append("</td>").append( 
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4300px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );
                htmlContent=null;

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

                File f = new File("DataPenilaianAwalMedisRalanUrologi.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4300px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENGKAJIAN AWAL MEDIS RAWAT JALAN UROLOGI<br><br></font>"+        
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

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        
    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Edukasi,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,Edukasi,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,Anamnesis,KeluhanUtama);
    }//GEN-LAST:event_HubunganKeyPressed

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
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalanUrologi.jasper","report","::[ Laporan Pengkajian Awal Medis Rawat Jalan Urologi ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_urologi.tanggal,"+
                "penilaian_medis_ralan_urologi.kd_dokter,penilaian_medis_ralan_urologi.anamnesis,penilaian_medis_ralan_urologi.hubungan,penilaian_medis_ralan_urologi.keluhan_utama,penilaian_medis_ralan_urologi.rps,"+
                "penilaian_medis_ralan_urologi.rpk,penilaian_medis_ralan_urologi.rpd,penilaian_medis_ralan_urologi.rpo,penilaian_medis_ralan_urologi.riwayat_kebiasaan,penilaian_medis_ralan_urologi.riwayat_operasi_urologi,"+
                "penilaian_medis_ralan_urologi.alergi,penilaian_medis_ralan_urologi.td,penilaian_medis_ralan_urologi.bb,penilaian_medis_ralan_urologi.tb,penilaian_medis_ralan_urologi.suhu,penilaian_medis_ralan_urologi.nadi,"+
                "penilaian_medis_ralan_urologi.rr,penilaian_medis_ralan_urologi.keadaan_umum,penilaian_medis_ralan_urologi.nyeri,penilaian_medis_ralan_urologi.status_nutrisi,penilaian_medis_ralan_urologi.thoraks,"+
                "penilaian_medis_ralan_urologi.keterangan_thoraks,penilaian_medis_ralan_urologi.abdomen,penilaian_medis_ralan_urologi.keterangan_abdomen,penilaian_medis_ralan_urologi.ekstrimitas,"+
                "penilaian_medis_ralan_urologi.keterangan_ekstrimitas,penilaian_medis_ralan_urologi.nyeri_ketok_cva,penilaian_medis_ralan_urologi.genitalia_eksternal,penilaian_medis_ralan_urologi.colok_dubur,"+
                "penilaian_medis_ralan_urologi.lainnya,penilaian_medis_ralan_urologi.urinalisis,penilaian_medis_ralan_urologi.darah,penilaian_medis_ralan_urologi.usg_urologi,penilaian_medis_ralan_urologi.radiologi,"+
                "penilaian_medis_ralan_urologi.penunjang_lain,penilaian_medis_ralan_urologi.diagnosis,penilaian_medis_ralan_urologi.diagnosis2,penilaian_medis_ralan_urologi.permasalahan,penilaian_medis_ralan_urologi.terapi,"+
                "penilaian_medis_ralan_urologi.tindakan,penilaian_medis_ralan_urologi.edukasi,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ralan_urologi on reg_periksa.no_rawat=penilaian_medis_ralan_urologi.no_rawat "+
                "inner join dokter on penilaian_medis_ralan_urologi.kd_dokter=dokter.kd_dokter where penilaian_medis_ralan_urologi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Hubungan,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt,RPK,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah2(evt,RPS,RPD);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RPK);
    }//GEN-LAST:event_RPSKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RiwayatOperasiUrologi,TD);
    }//GEN-LAST:event_AlergiKeyPressed

    private void ThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraksKeyPressed
        Valid.pindah(evt,StatusNutrisi,KeteranganThoraks);
    }//GEN-LAST:event_ThoraksKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        Valid.pindah(evt,KeteranganThoraks,KeteranganAbdomen);
    }//GEN-LAST:event_AbdomenKeyPressed

    private void EkstrimitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstrimitasKeyPressed
        Valid.pindah(evt,KeteranganAbdomen,KeteranganEkstrimitas);
    }//GEN-LAST:event_EkstrimitasKeyPressed

    private void KeteranganThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganThoraksKeyPressed
        Valid.pindah(evt,Thoraks,Abdomen);
    }//GEN-LAST:event_KeteranganThoraksKeyPressed

    private void KeteranganAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAbdomenKeyPressed
        Valid.pindah(evt,Abdomen,Ekstrimitas);
    }//GEN-LAST:event_KeteranganAbdomenKeyPressed

    private void KeteranganEkstrimitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEkstrimitasKeyPressed
        Valid.pindah(evt,Ekstrimitas,NyeriKetokCVA);
    }//GEN-LAST:event_KeteranganEkstrimitasKeyPressed

    private void LainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainnyaKeyPressed
        Valid.pindah2(evt,ColokDubur,Urinalisis);
    }//GEN-LAST:event_LainnyaKeyPressed

    private void USGUrologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_USGUrologiKeyPressed
        Valid.pindah2(evt,Darah,Radiologi);
    }//GEN-LAST:event_USGUrologiKeyPressed

    private void UrinalisisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrinalisisKeyPressed
        Valid.pindah2(evt,Lainnya,Darah);
    }//GEN-LAST:event_UrinalisisKeyPressed

    private void DarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahKeyPressed
        Valid.pindah2(evt,Urinalisis,USGUrologi);
    }//GEN-LAST:event_DarahKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah2(evt,PenunjangLainnya,Diagnosis2);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void Diagnosis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosis2KeyPressed
        Valid.pindah2(evt,Diagnosis,Permasalahan);
    }//GEN-LAST:event_Diagnosis2KeyPressed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        Valid.pindah2(evt,Permasalahan,Tindakan);
    }//GEN-LAST:event_TerapiKeyPressed

    private void PermasalahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PermasalahanKeyPressed
        Valid.pindah2(evt,Diagnosis2,Terapi);
    }//GEN-LAST:event_PermasalahanKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah2(evt,Terapi,Edukasi);
    }//GEN-LAST:event_TindakanKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,Tindakan,BtnSimpan);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt,RPD,RiwayatKebiasaan);
    }//GEN-LAST:event_RPOKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TD,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,Suhu,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,TB,Nadi);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Alergi,BB);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Keadaan);
    }//GEN-LAST:event_RRKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,Suhu);
    }//GEN-LAST:event_TBKeyPressed

    private void StatusNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusNutrisiKeyPressed
        Valid.pindah(evt,Nyeri,Thoraks);
    }//GEN-LAST:event_StatusNutrisiKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,Keadaan,StatusNutrisi);
    }//GEN-LAST:event_NyeriKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt,RR,Nyeri);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void RiwayatOperasiUrologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatOperasiUrologiKeyPressed
        Valid.pindah2(evt,RiwayatKebiasaan,Alergi);
    }//GEN-LAST:event_RiwayatOperasiUrologiKeyPressed

    private void RiwayatKebiasaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatKebiasaanKeyPressed
        Valid.pindah2(evt,RPO,RiwayatOperasiUrologi);
    }//GEN-LAST:event_RiwayatKebiasaanKeyPressed

    private void NyeriKetokCVAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKetokCVAKeyPressed
        Valid.pindah(evt,KeteranganEkstrimitas,GenitaliaEksternal);
    }//GEN-LAST:event_NyeriKetokCVAKeyPressed

    private void GenitaliaEksternalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GenitaliaEksternalKeyPressed
        Valid.pindah(evt,NyeriKetokCVA,ColokDubur);
    }//GEN-LAST:event_GenitaliaEksternalKeyPressed

    private void ColokDuburKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ColokDuburKeyPressed
        Valid.pindah(evt,GenitaliaEksternal,Lainnya);
    }//GEN-LAST:event_ColokDuburKeyPressed

    private void PenunjangLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangLainnyaKeyPressed
        Valid.pindah2(evt,Radiologi,Diagnosis);
    }//GEN-LAST:event_PenunjangLainnyaKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        Valid.pindah2(evt,USGUrologi,PenunjangLainnya);
    }//GEN-LAST:event_RadiologiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRalanUrologi dialog = new RMPenilaianAwalMedisRalanUrologi(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Abdomen;
    private widget.TextBox Alergi;
    private widget.ComboBox Anamnesis;
    private widget.TextBox BB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox ColokDubur;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Darah;
    private widget.TextArea Diagnosis;
    private widget.TextArea Diagnosis2;
    private widget.TextArea Edukasi;
    private widget.ComboBox Ekstrimitas;
    private widget.PanelBiasa FormInput;
    private widget.TextBox GenitaliaEksternal;
    private widget.TextBox Hubungan;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.ComboBox Keadaan;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KeteranganAbdomen;
    private widget.TextBox KeteranganEkstrimitas;
    private widget.TextBox KeteranganThoraks;
    private widget.Label LCount;
    private widget.TextArea Lainnya;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox Nyeri;
    private widget.TextBox NyeriKetokCVA;
    private widget.TextArea PenunjangLainnya;
    private widget.TextArea Permasalahan;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextArea Radiologi;
    private widget.TextArea RiwayatKebiasaan;
    private widget.TextArea RiwayatOperasiUrologi;
    private widget.ScrollPane Scroll;
    private widget.TextBox StatusNutrisi;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextArea Terapi;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox Thoraks;
    private widget.TextArea Tindakan;
    private widget.TextArea USGUrologi;
    private widget.TextArea Urinalisis;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane19;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_urologi.tanggal,"+
                        "penilaian_medis_ralan_urologi.kd_dokter,penilaian_medis_ralan_urologi.anamnesis,penilaian_medis_ralan_urologi.hubungan,penilaian_medis_ralan_urologi.keluhan_utama,penilaian_medis_ralan_urologi.rps,"+
                        "penilaian_medis_ralan_urologi.rpk,penilaian_medis_ralan_urologi.rpd,penilaian_medis_ralan_urologi.rpo,penilaian_medis_ralan_urologi.riwayat_kebiasaan,penilaian_medis_ralan_urologi.riwayat_operasi_urologi,"+
                        "penilaian_medis_ralan_urologi.alergi,penilaian_medis_ralan_urologi.td,penilaian_medis_ralan_urologi.bb,penilaian_medis_ralan_urologi.tb,penilaian_medis_ralan_urologi.suhu,penilaian_medis_ralan_urologi.nadi,"+
                        "penilaian_medis_ralan_urologi.rr,penilaian_medis_ralan_urologi.keadaan_umum,penilaian_medis_ralan_urologi.nyeri,penilaian_medis_ralan_urologi.status_nutrisi,penilaian_medis_ralan_urologi.thoraks,"+
                        "penilaian_medis_ralan_urologi.keterangan_thoraks,penilaian_medis_ralan_urologi.abdomen,penilaian_medis_ralan_urologi.keterangan_abdomen,penilaian_medis_ralan_urologi.ekstrimitas,"+
                        "penilaian_medis_ralan_urologi.keterangan_ekstrimitas,penilaian_medis_ralan_urologi.nyeri_ketok_cva,penilaian_medis_ralan_urologi.genitalia_eksternal,penilaian_medis_ralan_urologi.colok_dubur,"+
                        "penilaian_medis_ralan_urologi.lainnya,penilaian_medis_ralan_urologi.urinalisis,penilaian_medis_ralan_urologi.darah,penilaian_medis_ralan_urologi.usg_urologi,penilaian_medis_ralan_urologi.radiologi,"+
                        "penilaian_medis_ralan_urologi.penunjang_lain,penilaian_medis_ralan_urologi.diagnosis,penilaian_medis_ralan_urologi.diagnosis2,penilaian_medis_ralan_urologi.permasalahan,penilaian_medis_ralan_urologi.terapi,"+
                        "penilaian_medis_ralan_urologi.tindakan,penilaian_medis_ralan_urologi.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_urologi on reg_periksa.no_rawat=penilaian_medis_ralan_urologi.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_urologi.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_urologi.tanggal between ? and ? order by penilaian_medis_ralan_urologi.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_urologi.tanggal,"+
                        "penilaian_medis_ralan_urologi.kd_dokter,penilaian_medis_ralan_urologi.anamnesis,penilaian_medis_ralan_urologi.hubungan,penilaian_medis_ralan_urologi.keluhan_utama,penilaian_medis_ralan_urologi.rps,"+
                        "penilaian_medis_ralan_urologi.rpk,penilaian_medis_ralan_urologi.rpd,penilaian_medis_ralan_urologi.rpo,penilaian_medis_ralan_urologi.riwayat_kebiasaan,penilaian_medis_ralan_urologi.riwayat_operasi_urologi,"+
                        "penilaian_medis_ralan_urologi.alergi,penilaian_medis_ralan_urologi.td,penilaian_medis_ralan_urologi.bb,penilaian_medis_ralan_urologi.tb,penilaian_medis_ralan_urologi.suhu,penilaian_medis_ralan_urologi.nadi,"+
                        "penilaian_medis_ralan_urologi.rr,penilaian_medis_ralan_urologi.keadaan_umum,penilaian_medis_ralan_urologi.nyeri,penilaian_medis_ralan_urologi.status_nutrisi,penilaian_medis_ralan_urologi.thoraks,"+
                        "penilaian_medis_ralan_urologi.keterangan_thoraks,penilaian_medis_ralan_urologi.abdomen,penilaian_medis_ralan_urologi.keterangan_abdomen,penilaian_medis_ralan_urologi.ekstrimitas,"+
                        "penilaian_medis_ralan_urologi.keterangan_ekstrimitas,penilaian_medis_ralan_urologi.nyeri_ketok_cva,penilaian_medis_ralan_urologi.genitalia_eksternal,penilaian_medis_ralan_urologi.colok_dubur,"+
                        "penilaian_medis_ralan_urologi.lainnya,penilaian_medis_ralan_urologi.urinalisis,penilaian_medis_ralan_urologi.darah,penilaian_medis_ralan_urologi.usg_urologi,penilaian_medis_ralan_urologi.radiologi,"+
                        "penilaian_medis_ralan_urologi.penunjang_lain,penilaian_medis_ralan_urologi.diagnosis,penilaian_medis_ralan_urologi.diagnosis2,penilaian_medis_ralan_urologi.permasalahan,penilaian_medis_ralan_urologi.terapi,"+
                        "penilaian_medis_ralan_urologi.tindakan,penilaian_medis_ralan_urologi.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_urologi on reg_periksa.no_rawat=penilaian_medis_ralan_urologi.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_urologi.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_urologi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan_urologi.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_urologi.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpk"),rs.getString("rpd"),rs.getString("rpo"),rs.getString("riwayat_kebiasaan"),
                        rs.getString("riwayat_operasi_urologi"),rs.getString("alergi"),rs.getString("td"),rs.getString("bb"),rs.getString("tb"),rs.getString("suhu"),rs.getString("nadi"),rs.getString("rr"),rs.getString("keadaan_umum"),
                        rs.getString("nyeri"),rs.getString("status_nutrisi"),rs.getString("thoraks"),rs.getString("keterangan_thoraks"),rs.getString("abdomen"),rs.getString("keterangan_abdomen"),rs.getString("ekstrimitas"),
                        rs.getString("keterangan_ekstrimitas"),rs.getString("nyeri_ketok_cva"),rs.getString("genitalia_eksternal"),rs.getString("colok_dubur"),rs.getString("lainnya"),rs.getString("urinalisis"),rs.getString("darah"),
                        rs.getString("usg_urologi"),rs.getString("radiologi"),rs.getString("penunjang_lain"),rs.getString("diagnosis"),rs.getString("diagnosis2"),rs.getString("permasalahan"),rs.getString("terapi"),
                        rs.getString("tindakan"),rs.getString("edukasi")
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
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        KeluhanUtama.setText("");
        RPS.setText("");
        RPD.setText("");
        RPK.setText("");
        RPO.setText("");
        RiwayatKebiasaan.setText("");
        RiwayatOperasiUrologi.setText("");
        Alergi.setText("");
        TD.setText("");
        Nadi.setText("");
        Suhu.setText("");
        RR.setText("");
        BB.setText("");
        Nyeri.setText("");
        StatusNutrisi.setText("");
        TB.setText("");
        Thoraks.setSelectedIndex(2);
        Abdomen.setSelectedIndex(2);
        Ekstrimitas.setSelectedIndex(2);
        KeteranganThoraks.setText("");
        KeteranganAbdomen.setText("");
        KeteranganEkstrimitas.setText("");
        NyeriKetokCVA.setText("");
        GenitaliaEksternal.setText("");
        ColokDubur.setText("");
        Lainnya.setText("");
        Urinalisis.setText("");
        Darah.setText("");
        Radiologi.setText("");
        PenunjangLainnya.setText("");
        USGUrologi.setText("");
        Radiologi.setText("");
        PenunjangLainnya.setText("");
        Diagnosis.setText("");
        Diagnosis2.setText("");
        Permasalahan.setText("");
        Terapi.setText("");
        Tindakan.setText("");
        Edukasi.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            RiwayatKebiasaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            RiwayatOperasiUrologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Keadaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Nyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            StatusNutrisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Thoraks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            KeteranganThoraks.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Abdomen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            KeteranganAbdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Ekstrimitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            KeteranganEkstrimitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            NyeriKetokCVA.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            GenitaliaEksternal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            ColokDubur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Lainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Urinalisis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Darah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            USGUrologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Radiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            PenunjangLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Diagnosis2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Permasalahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
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
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ralan_urologi());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ralan_urologi());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan_urologi());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
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
        if(Sequel.queryu2tf("delete from penilaian_medis_ralan_urologi where no_rawat=?",1,new String[]{
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
        if(Sequel.mengedittf("penilaian_medis_ralan_urologi","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpk=?,rpd=?,rpo=?,riwayat_kebiasaan=?,riwayat_operasi_urologi=?,alergi=?,"+
                "td=?,bb=?,tb=?,suhu=?,nadi=?,rr=?,keadaan_umum=?,nyeri=?,status_nutrisi=?,thoraks=?,keterangan_thoraks=?,abdomen=?,keterangan_abdomen=?,ekstrimitas=?,keterangan_ekstrimitas=?,nyeri_ketok_cva=?,genitalia_eksternal=?,"+
                "colok_dubur=?,lainnya=?,urinalisis=?,darah=?,usg_urologi=?,radiologi=?,penunjang_lain=?,diagnosis=?,diagnosis2=?,permasalahan=?,terapi=?,tindakan=?,edukasi=?",44,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                KeluhanUtama.getText(),RPS.getText(),RPK.getText(),RPD.getText(),RPO.getText(),RiwayatKebiasaan.getText(),RiwayatOperasiUrologi.getText(),Alergi.getText(),TD.getText(),BB.getText(),TB.getText(),
                Suhu.getText(),Nadi.getText(),RR.getText(),Keadaan.getSelectedItem().toString(),Nyeri.getText(),StatusNutrisi.getText(),Thoraks.getSelectedItem().toString(),KeteranganThoraks.getText(),
                Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Ekstrimitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),NyeriKetokCVA.getText(),GenitaliaEksternal.getText(),
                ColokDubur.getText(),Lainnya.getText(),Urinalisis.getText(),Darah.getText(),USGUrologi.getText(),Radiologi.getText(),PenunjangLainnya.getText(),Diagnosis.getText(),Diagnosis2.getText(),
                Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Anamnesis.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(Hubungan.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(RPS.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(RPK.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(RPD.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(RPO.getText(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(RiwayatKebiasaan.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(RiwayatOperasiUrologi.getText(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(Alergi.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(Keadaan.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(Nyeri.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(StatusNutrisi.getText(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(Thoraks.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(KeteranganThoraks.getText(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(Abdomen.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(KeteranganAbdomen.getText(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(Ekstrimitas.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(KeteranganEkstrimitas.getText(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(NyeriKetokCVA.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(GenitaliaEksternal.getText(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(ColokDubur.getText(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(Lainnya.getText(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(Urinalisis.getText(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(Darah.getText(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(USGUrologi.getText(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(Radiologi.getText(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(PenunjangLainnya.getText(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(Diagnosis.getText(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(Diagnosis2.getText(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(Permasalahan.getText(),tbObat.getSelectedRow(),44);
               tbObat.setValueAt(Terapi.getText(),tbObat.getSelectedRow(),45);
               tbObat.setValueAt(Tindakan.getText(),tbObat.getSelectedRow(),46);
               tbObat.setValueAt(Edukasi.getText(),tbObat.getSelectedRow(),47);
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("penilaian_medis_ralan_urologi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",43,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                KeluhanUtama.getText(),RPS.getText(),RPK.getText(),RPD.getText(),RPO.getText(),RiwayatKebiasaan.getText(),RiwayatOperasiUrologi.getText(),Alergi.getText(),TD.getText(),BB.getText(),TB.getText(),
                Suhu.getText(),Nadi.getText(),RR.getText(),Keadaan.getSelectedItem().toString(),Nyeri.getText(),StatusNutrisi.getText(),Thoraks.getSelectedItem().toString(),KeteranganThoraks.getText(),
                Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Ekstrimitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),NyeriKetokCVA.getText(),GenitaliaEksternal.getText(),
                ColokDubur.getText(),Lainnya.getText(),Urinalisis.getText(),Darah.getText(),USGUrologi.getText(),Radiologi.getText(),PenunjangLainnya.getText(),Diagnosis.getText(),Diagnosis2.getText(),
                Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPK.getText(),RPD.getText(),RPO.getText(),RiwayatKebiasaan.getText(),RiwayatOperasiUrologi.getText(),Alergi.getText(),TD.getText(),BB.getText(),TB.getText(),Suhu.getText(),Nadi.getText(),RR.getText(),Keadaan.getSelectedItem().toString(),Nyeri.getText(),
                    StatusNutrisi.getText(),Thoraks.getSelectedItem().toString(),KeteranganThoraks.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Ekstrimitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),NyeriKetokCVA.getText(),GenitaliaEksternal.getText(),
                    ColokDubur.getText(),Lainnya.getText(),Urinalisis.getText(),Darah.getText(),USGUrologi.getText(),Radiologi.getText(),PenunjangLainnya.getText(),Diagnosis.getText(),Diagnosis2.getText(),Permasalahan.getText(),Terapi.getText(),Tindakan.getText(),Edukasi.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
        }
    }
}

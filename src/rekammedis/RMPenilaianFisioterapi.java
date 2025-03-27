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
import kepegawaian.DlgCariPegawai;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianFisioterapi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPegawai petugas=new DlgCariPegawai(null,false);
    private String finger=""; 
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianFisioterapi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Tanggal","Informasi","Keluhan Utama","Riwayat Peyakit Sekarang","Riwayat Penyakit Dahulu & Penyerta", 
            "TD(mmHg)","HR(x/menit)","RR(x/menit)","Suhu(°C)","Nyeri Tekan","Nyeri Gerak","Nyeri Diam","Palpasi","Luas Gerak Sendi","Kekuatan Otot","Statis","Dinamis","Kognitif",
            "Auskultasi","Alat Bantu","Ket Alat Bantu","Prothesa","Keteranga Prothesa","Deformitas","Keterangan Deformitas","Resiko Jatuh","Keterangan Resiko Jatuh","ADL","Fungsional Lain",
            "Keterangan Fisik","Pemeriksaan Musculoskeletal","Pemeriksaan Neuromuscular","Pemeriksaan Cardiopulmonal","Pemeriksaan Integument","Pengukuran Musculoskeletal", 
            "Pengukuran Neuromuscular","Pengukuran Cardiopulmonal","Pengukuran Integument","Pemeriksaan Penunjang", "Diagnosis Fisio", "Rencana Intervensi Fisioterapi",
            "NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 48; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(270);
            }else if(i==8){
                column.setPreferredWidth(184);
            }else if(i==9){
                column.setPreferredWidth(184);
            }else if(i==10){
                column.setPreferredWidth(62);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(53);
            }else if(i==14){
                column.setPreferredWidth(70);
            }else if(i==15){
                column.setPreferredWidth(70);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(130);
            }else if(i==18){
                column.setPreferredWidth(130);
            }else if(i==19){
                column.setPreferredWidth(130);
            }else if(i==20){
                column.setPreferredWidth(130);
            }else if(i==21){
                column.setPreferredWidth(130);
            }else if(i==22){
                column.setPreferredWidth(130);
            }else if(i==23){
                column.setPreferredWidth(130);
            }else if(i==24){
                column.setPreferredWidth(60);
            }else if(i==25){
                column.setPreferredWidth(100);
            }else if(i==26){
                column.setPreferredWidth(53);
            }else if(i==27){
                column.setPreferredWidth(105);
            }else if(i==28){
                column.setPreferredWidth(62);
            }else if(i==29){
                column.setPreferredWidth(120);
            }else if(i==30){
                column.setPreferredWidth(70);
            }else if(i==31){
                column.setPreferredWidth(130);
            }else if(i==32){
                column.setPreferredWidth(55);
            }else if(i==33){
                column.setPreferredWidth(120);
            }else if(i==34){
                column.setPreferredWidth(270);
            }else if(i==35){
                column.setPreferredWidth(160);
            }else if(i==36){
                column.setPreferredWidth(160);
            }else if(i==37){
                column.setPreferredWidth(160);
            }else if(i==38){
                column.setPreferredWidth(160);
            }else if(i==39){
                column.setPreferredWidth(160);
            }else if(i==40){
                column.setPreferredWidth(160);
            }else if(i==41){
                column.setPreferredWidth(160);
            }else if(i==42){
                column.setPreferredWidth(160);
            }else if(i==43){
                column.setPreferredWidth(200);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(200);
            }else if(i==46){
                column.setPreferredWidth(80);
            }else if(i==47){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KeluhanUtama.setDocument(new batasInput((int)150).getKata(KeluhanUtama));
        RiwayatPenyakitSekarang.setDocument(new batasInput((int)100).getKata(RiwayatPenyakitSekarang));
        RiwayatPenyakitDahulu.setDocument(new batasInput((int)100).getKata(RiwayatPenyakitDahulu));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        HR.setDocument(new batasInput((byte)5).getKata(HR));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        NyeriTekan.setDocument(new batasInput((byte)5).getKata(NyeriTekan));
        NyeriGerak.setDocument(new batasInput((byte)5).getKata(NyeriGerak));
        NyeriDiam.setDocument(new batasInput((byte)5).getKata(NyeriDiam));
        Palpasi.setDocument(new batasInput((byte)50).getKata(Palpasi));
        LuasGerakSendi.setDocument(new batasInput((byte)50).getKata(LuasGerakSendi));
        KekuatanOtot.setDocument(new batasInput((byte)50).getKata(KekuatanOtot));
        Statis.setDocument(new batasInput((byte)50).getKata(Statis));
        Dinamis.setDocument(new batasInput((byte)50).getKata(Dinamis));
        Kognitif.setDocument(new batasInput((byte)50).getKata(Kognitif));
        Auskultasi.setDocument(new batasInput((byte)50).getKata(Auskultasi));
        KetBantu.setDocument(new batasInput((byte)50).getKata(KetBantu));
        KetProthesa.setDocument(new batasInput((byte)50).getKata(KetProthesa));
        KetDeformitas.setDocument(new batasInput((byte)50).getKata(KetDeformitas));
        KetResikoJatuh.setDocument(new batasInput((byte)50).getKata(KetResikoJatuh));
        LainlainFungsioal.setDocument(new batasInput((byte)70).getKata(LainlainFungsioal));
        KetFisik.setDocument(new batasInput((int)2000).getKata(KetFisik));
        PemeriksaanMuscu.setDocument(new batasInput((int)200).getKata(PemeriksaanMuscu));
        PemeriksaanNeuro.setDocument(new batasInput((int)200).getKata(PemeriksaanNeuro));
        PemeriksaanCardio.setDocument(new batasInput((int)200).getKata(PemeriksaanCardio));
        PemeriksaanInte.setDocument(new batasInput((int)200).getKata(PemeriksaanInte));
        PengukuranMuscu.setDocument(new batasInput((int)200).getKata(PengukuranMuscu));
        PengukuranNeuro.setDocument(new batasInput((int)200).getKata(PengukuranNeuro));
        PengukuranCardio.setDocument(new batasInput((int)200).getKata(PengukuranCardio));
        PengukuranInte.setDocument(new batasInput((int)200).getKata(PengukuranInte));
        Penunjang.setDocument(new batasInput((int)500).getKata(Penunjang));
        Diagnosis.setDocument(new batasInput((int)100).getKata(Diagnosis));
        Rencana.setDocument(new batasInput((int)200).getKata(Rencana));
        
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
        MnPenilaianFisio = new javax.swing.JMenuItem();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        NyeriGerak = new widget.TextBox();
        NyeriDiam = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        HR = new widget.TextBox();
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
        jLabel36 = new widget.Label();
        Informasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel28 = new widget.Label();
        NyeriTekan = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RiwayatPenyakitSekarang = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RiwayatPenyakitDahulu = new widget.TextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        PanelWall = new usu.widget.glass.PanelGlass();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel29 = new widget.Label();
        Palpasi = new widget.TextBox();
        jLabel32 = new widget.Label();
        LuasGerakSendi = new widget.TextBox();
        jLabel33 = new widget.Label();
        KekuatanOtot = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        Statis = new widget.TextBox();
        Dinamis = new widget.TextBox();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        Kognitif = new widget.TextBox();
        jLabel39 = new widget.Label();
        Auskultasi = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel55 = new widget.Label();
        AlatBantu = new widget.ComboBox();
        KetBantu = new widget.TextBox();
        jLabel50 = new widget.Label();
        Prothesa = new widget.ComboBox();
        KetProthesa = new widget.TextBox();
        jLabel56 = new widget.Label();
        Deformitas = new widget.ComboBox();
        KetDeformitas = new widget.TextBox();
        jLabel51 = new widget.Label();
        ResikoJatuh = new widget.ComboBox();
        KetResikoJatuh = new widget.TextBox();
        jLabel57 = new widget.Label();
        ADL = new widget.ComboBox();
        jLabel52 = new widget.Label();
        LainlainFungsioal = new widget.TextBox();
        jLabel41 = new widget.Label();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        scrollPane8 = new widget.ScrollPane();
        KetFisik = new widget.TextArea();
        jLabel42 = new widget.Label();
        jLabel47 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        PemeriksaanMuscu = new widget.TextArea();
        jLabel48 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        PemeriksaanNeuro = new widget.TextArea();
        jLabel49 = new widget.Label();
        jLabel58 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        PemeriksaanCardio = new widget.TextArea();
        scrollPane7 = new widget.ScrollPane();
        PemeriksaanInte = new widget.TextArea();
        jLabel43 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        PengukuranNeuro = new widget.TextArea();
        scrollPane10 = new widget.ScrollPane();
        PengukuranMuscu = new widget.TextArea();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        PengukuranInte = new widget.TextArea();
        scrollPane12 = new widget.ScrollPane();
        PengukuranCardio = new widget.TextArea();
        jSeparator14 = new javax.swing.JSeparator();
        scrollPane13 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        jLabel101 = new widget.Label();
        jSeparator15 = new javax.swing.JSeparator();
        scrollPane14 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jLabel102 = new widget.Label();
        jSeparator16 = new javax.swing.JSeparator();
        scrollPane15 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        jLabel103 = new widget.Label();
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

        MnPenilaianFisio.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianFisio.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianFisio.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianFisio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianFisio.setText("Laporan Penilaian Fisioterapi");
        MnPenilaianFisio.setName("MnPenilaianFisio"); // NOI18N
        MnPenilaianFisio.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianFisio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianFisioActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianFisio);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Fisioterapi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1393));
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

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(176, 40, 180, 23);

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
        BtnDokter.setBounds(358, 40, 28, 23);

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

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(395, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel12.setText("Nyeri Gerak :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(541, 250, 70, 23);

        NyeriGerak.setFocusTraversalPolicyProvider(true);
        NyeriGerak.setName("NyeriGerak"); // NOI18N
        NyeriGerak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriGerakKeyPressed(evt);
            }
        });
        FormInput.add(NyeriGerak);
        NyeriGerak.setBounds(615, 250, 50, 23);

        NyeriDiam.setFocusTraversalPolicyProvider(true);
        NyeriDiam.setName("NyeriDiam"); // NOI18N
        NyeriDiam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriDiamKeyPressed(evt);
            }
        });
        FormInput.add(NyeriDiam);
        NyeriDiam.setBounds(804, 250, 50, 23);

        jLabel15.setText("Nyeri Diam :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(730, 250, 70, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(582, 220, 50, 23);

        HR.setFocusTraversalPolicyProvider(true);
        HR.setName("HR"); // NOI18N
        HR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HRKeyPressed(evt);
            }
        });
        FormInput.add(HR);
        HR.setBounds(539, 220, 40, 23);

        jLabel17.setText("HR :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(495, 220, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(755, 220, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(799, 220, 40, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(365, 220, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(399, 220, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(842, 220, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(462, 220, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(708, 220, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(665, 220, 40, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(621, 220, 40, 23);

        jLabel36.setText("Informasi didapat dari :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(592, 40, 130, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(726, 40, 128, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. RIWAYAT KESEHATAN");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-11-2023 06:55:51" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(456, 40, 130, 23);

        jLabel28.setText("Nyeri Tekan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(369, 250, 70, 23);

        NyeriTekan.setFocusTraversalPolicyProvider(true);
        NyeriTekan.setName("NyeriTekan"); // NOI18N
        NyeriTekan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriTekanKeyPressed(evt);
            }
        });
        FormInput.add(NyeriTekan);
        NyeriTekan.setBounds(443, 250, 50, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel9.setText("Riwayat Penyakit Dahulu & Penyerta :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(420, 140, 200, 23);

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
        scrollPane1.setBounds(129, 90, 725, 43);

        jLabel30.setText("Keluhan Utama :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 90, 125, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RiwayatPenyakitSekarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitSekarang.setColumns(20);
        RiwayatPenyakitSekarang.setRows(5);
        RiwayatPenyakitSekarang.setName("RiwayatPenyakitSekarang"); // NOI18N
        RiwayatPenyakitSekarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitSekarangKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RiwayatPenyakitSekarang);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(186, 140, 230, 53);

        jLabel31.setText("Riwayat Penyakit Sekarang :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 140, 182, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RiwayatPenyakitDahulu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitDahulu.setColumns(20);
        RiwayatPenyakitDahulu.setRows(5);
        RiwayatPenyakitDahulu.setName("RiwayatPenyakitDahulu"); // NOI18N
        RiwayatPenyakitDahulu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitDahuluKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RiwayatPenyakitDahulu);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(624, 140, 230, 53);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 200, 880, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("II. PEMERIKSAAN FISIK");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 200, 180, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(40, 220, 320, 130);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(365, 220, 1, 140);

        jLabel29.setText("Palpasi :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(368, 280, 49, 23);

        Palpasi.setFocusTraversalPolicyProvider(true);
        Palpasi.setName("Palpasi"); // NOI18N
        Palpasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PalpasiKeyPressed(evt);
            }
        });
        FormInput.add(Palpasi);
        Palpasi.setBounds(421, 280, 433, 23);

        jLabel32.setText("Luas Gerak Sendi :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(367, 310, 98, 23);

        LuasGerakSendi.setFocusTraversalPolicyProvider(true);
        LuasGerakSendi.setName("LuasGerakSendi"); // NOI18N
        LuasGerakSendi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LuasGerakSendiKeyPressed(evt);
            }
        });
        FormInput.add(LuasGerakSendi);
        LuasGerakSendi.setBounds(469, 310, 385, 23);

        jLabel33.setText("Kekuatan Otot (MMT) :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(367, 340, 118, 23);

        KekuatanOtot.setFocusTraversalPolicyProvider(true);
        KekuatanOtot.setName("KekuatanOtot"); // NOI18N
        KekuatanOtot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KekuatanOtotKeyPressed(evt);
            }
        });
        FormInput.add(KekuatanOtot);
        KekuatanOtot.setBounds(489, 340, 365, 23);

        jLabel34.setText("Inspeksi :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 360, 91, 23);

        jLabel35.setText("Statis :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 380, 115, 23);

        Statis.setFocusTraversalPolicyProvider(true);
        Statis.setName("Statis"); // NOI18N
        Statis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatisKeyPressed(evt);
            }
        });
        FormInput.add(Statis);
        Statis.setBounds(119, 380, 310, 23);

        Dinamis.setFocusTraversalPolicyProvider(true);
        Dinamis.setName("Dinamis"); // NOI18N
        Dinamis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DinamisKeyPressed(evt);
            }
        });
        FormInput.add(Dinamis);
        Dinamis.setBounds(119, 410, 310, 23);

        jLabel37.setText("Dinamis :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 410, 115, 23);

        jLabel38.setText("Kognitif :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(470, 380, 70, 23);

        Kognitif.setFocusTraversalPolicyProvider(true);
        Kognitif.setName("Kognitif"); // NOI18N
        Kognitif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KognitifKeyPressed(evt);
            }
        });
        FormInput.add(Kognitif);
        Kognitif.setBounds(544, 380, 310, 23);

        jLabel39.setText("Auskultasi :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(470, 410, 70, 23);

        Auskultasi.setFocusTraversalPolicyProvider(true);
        Auskultasi.setName("Auskultasi"); // NOI18N
        Auskultasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AuskultasiKeyPressed(evt);
            }
        });
        FormInput.add(Auskultasi);
        Auskultasi.setBounds(544, 410, 310, 23);

        jLabel40.setText("Keterangan Fisik :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 550, 132, 23);

        jLabel55.setText("Alat Bantu :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 460, 120, 23);

        AlatBantu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu);
        AlatBantu.setBounds(124, 460, 90, 23);

        KetBantu.setFocusTraversalPolicyProvider(true);
        KetBantu.setName("KetBantu"); // NOI18N
        KetBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBantuKeyPressed(evt);
            }
        });
        FormInput.add(KetBantu);
        KetBantu.setBounds(218, 460, 215, 23);

        jLabel50.setText("Prothesa :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(481, 460, 60, 23);

        Prothesa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Prothesa.setName("Prothesa"); // NOI18N
        Prothesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProthesaKeyPressed(evt);
            }
        });
        FormInput.add(Prothesa);
        Prothesa.setBounds(545, 460, 90, 23);

        KetProthesa.setFocusTraversalPolicyProvider(true);
        KetProthesa.setName("KetProthesa"); // NOI18N
        KetProthesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProthesaKeyPressed(evt);
            }
        });
        FormInput.add(KetProthesa);
        KetProthesa.setBounds(639, 460, 215, 23);

        jLabel56.setText("Deformitas :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(0, 490, 120, 23);

        Deformitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Deformitas.setName("Deformitas"); // NOI18N
        Deformitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DeformitasKeyPressed(evt);
            }
        });
        FormInput.add(Deformitas);
        Deformitas.setBounds(124, 490, 90, 23);

        KetDeformitas.setFocusTraversalPolicyProvider(true);
        KetDeformitas.setName("KetDeformitas"); // NOI18N
        KetDeformitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDeformitasKeyPressed(evt);
            }
        });
        FormInput.add(KetDeformitas);
        KetDeformitas.setBounds(218, 490, 215, 23);

        jLabel51.setText("Resiko Jatuh :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(451, 490, 90, 23);

        ResikoJatuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ResikoJatuh.setName("ResikoJatuh"); // NOI18N
        ResikoJatuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResikoJatuhKeyPressed(evt);
            }
        });
        FormInput.add(ResikoJatuh);
        ResikoJatuh.setBounds(545, 490, 90, 23);

        KetResikoJatuh.setFocusTraversalPolicyProvider(true);
        KetResikoJatuh.setName("KetResikoJatuh"); // NOI18N
        KetResikoJatuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetResikoJatuhKeyPressed(evt);
            }
        });
        FormInput.add(KetResikoJatuh);
        KetResikoJatuh.setBounds(639, 490, 215, 23);

        jLabel57.setText("Aktivitas Kehidupan Sehari-hari ( ADL ) :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(30, 520, 225, 23);

        ADL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Dibantu" }));
        ADL.setName("ADL"); // NOI18N
        ADL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLKeyPressed(evt);
            }
        });
        FormInput.add(ADL);
        ADL.setBounds(259, 520, 174, 23);

        jLabel52.setText("Lain-lain :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(451, 520, 90, 23);

        LainlainFungsioal.setFocusTraversalPolicyProvider(true);
        LainlainFungsioal.setName("LainlainFungsioal"); // NOI18N
        LainlainFungsioal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainlainFungsioalKeyPressed(evt);
            }
        });
        FormInput.add(LainlainFungsioal);
        LainlainFungsioal.setBounds(545, 520, 309, 23);

        jLabel41.setText("Kemampuan Fungsional :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 440, 167, 23);

        PanelWall1.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/fisiobody.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(55, 570, 290, 243);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        KetFisik.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetFisik.setColumns(20);
        KetFisik.setRows(5);
        KetFisik.setName("KetFisik"); // NOI18N
        KetFisik.setPreferredSize(new java.awt.Dimension(182, 92));
        KetFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetFisikKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(KetFisik);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(364, 570, 490, 243);

        jLabel42.setText("Pemeriksaan Sistemik Khusus  :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 820, 197, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("a. Musculoskeletal :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(60, 840, 182, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        PemeriksaanMuscu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanMuscu.setColumns(20);
        PemeriksaanMuscu.setRows(5);
        PemeriksaanMuscu.setName("PemeriksaanMuscu"); // NOI18N
        PemeriksaanMuscu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanMuscuKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(PemeriksaanMuscu);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(60, 860, 375, 43);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("b. Neuromuscular : ");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(479, 840, 182, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        PemeriksaanNeuro.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanNeuro.setColumns(20);
        PemeriksaanNeuro.setRows(5);
        PemeriksaanNeuro.setName("PemeriksaanNeuro"); // NOI18N
        PemeriksaanNeuro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanNeuroKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(PemeriksaanNeuro);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(479, 860, 375, 43);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("c. CardioPulmonal :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(60, 910, 182, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("d. Integument :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(479, 910, 182, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        PemeriksaanCardio.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanCardio.setColumns(20);
        PemeriksaanCardio.setRows(5);
        PemeriksaanCardio.setName("PemeriksaanCardio"); // NOI18N
        PemeriksaanCardio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanCardioKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(PemeriksaanCardio);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(60, 930, 375, 43);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        PemeriksaanInte.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanInte.setColumns(20);
        PemeriksaanInte.setRows(5);
        PemeriksaanInte.setName("PemeriksaanInte"); // NOI18N
        PemeriksaanInte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanInteKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(PemeriksaanInte);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(479, 930, 375, 43);

        jLabel43.setText("Pengukuran Khusus :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 980, 145, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("a. Musculoskeletal :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(60, 1000, 182, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("b. Neuromuscular : ");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(479, 1000, 182, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        PengukuranNeuro.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PengukuranNeuro.setColumns(20);
        PengukuranNeuro.setRows(5);
        PengukuranNeuro.setName("PengukuranNeuro"); // NOI18N
        PengukuranNeuro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengukuranNeuroKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(PengukuranNeuro);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(479, 1020, 375, 43);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        PengukuranMuscu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PengukuranMuscu.setColumns(20);
        PengukuranMuscu.setRows(5);
        PengukuranMuscu.setName("PengukuranMuscu"); // NOI18N
        PengukuranMuscu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengukuranMuscuKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(PengukuranMuscu);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(60, 1020, 375, 43);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("c. CardioPulmonal :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(60, 1070, 182, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("d. Integument :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(479, 1070, 182, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        PengukuranInte.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PengukuranInte.setColumns(20);
        PengukuranInte.setRows(5);
        PengukuranInte.setName("PengukuranInte"); // NOI18N
        PengukuranInte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengukuranInteKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(PengukuranInte);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(479, 1090, 375, 43);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        PengukuranCardio.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PengukuranCardio.setColumns(20);
        PengukuranCardio.setRows(5);
        PengukuranCardio.setName("PengukuranCardio"); // NOI18N
        PengukuranCardio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengukuranCardioKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(PengukuranCardio);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(60, 1090, 375, 43);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 1140, 880, 1);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(40);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Penunjang);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(44, 1160, 810, 63);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("III. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 1140, 190, 23);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 1230, 880, 1);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(40);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Diagnosis);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 1250, 810, 43);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("IV. DIAGNOSIS FISIOTERAPI");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 1230, 190, 23);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 1300, 880, 1);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Rencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rencana.setColumns(20);
        Rencana.setRows(40);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Rencana);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 1320, 810, 63);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("V. RENCANA INTERVENSI FISIOTERAPI");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 1300, 330, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-11-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-11-2023" }));
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
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RiwayatPenyakitSekarang.getText().trim().equals("")){
            Valid.textKosong(RiwayatPenyakitSekarang,"Riwayat Penyakit Sekarang");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
           if(Sequel.menyimpantf("penilaian_fisioterapi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",43,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),KeluhanUtama.getText(),
                    RiwayatPenyakitSekarang.getText(),RiwayatPenyakitDahulu.getText(),TD.getText(),HR.getText(),RR.getText(),Suhu.getText(),NyeriTekan.getText(),NyeriGerak.getText(),NyeriDiam.getText(),
                    Palpasi.getText(),LuasGerakSendi.getText(),KekuatanOtot.getText(),Statis.getText(),Dinamis.getText(),Kognitif.getText(),Auskultasi.getText(),AlatBantu.getSelectedItem().toString(),
                    KetBantu.getText(),Prothesa.getSelectedItem().toString(),KetProthesa.getText(),Deformitas.getSelectedItem().toString(),KetDeformitas.getText(),ResikoJatuh.getSelectedItem().toString(),
                    KetResikoJatuh.getText(),ADL.getSelectedItem().toString(),LainlainFungsioal.getText(),KetFisik.getText(),PemeriksaanMuscu.getText(),PemeriksaanNeuro.getText(),PemeriksaanCardio.getText(),
                    PemeriksaanInte.getText(),PengukuranMuscu.getText(),PengukuranNeuro.getText(),PengukuranCardio.getText(),PengukuranInte.getText(),Penunjang.getText(),Diagnosis.getText(),Rencana.getText(),
                    KdPetugas.getText()
                })==true){
                    tabMode.addRow(new Object[]{
                        TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Jk.getText(),TglLahir.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),
                        Informasi.getSelectedItem().toString(),KeluhanUtama.getText(),RiwayatPenyakitSekarang.getText(),RiwayatPenyakitDahulu.getText(),TD.getText(),HR.getText(),RR.getText(),Suhu.getText(),NyeriTekan.getText(),
                        NyeriGerak.getText(),NyeriDiam.getText(),Palpasi.getText(),LuasGerakSendi.getText(),KekuatanOtot.getText(),Statis.getText(),Dinamis.getText(),Kognitif.getText(),Auskultasi.getText(),AlatBantu.getSelectedItem().toString(),
                        KetBantu.getText(),Prothesa.getSelectedItem().toString(),KetProthesa.getText(),Deformitas.getSelectedItem().toString(),KetDeformitas.getText(),ResikoJatuh.getSelectedItem().toString(),KetResikoJatuh.getText(),
                        ADL.getSelectedItem().toString(),LainlainFungsioal.getText(),KetFisik.getText(),PemeriksaanMuscu.getText(),PemeriksaanNeuro.getText(),PemeriksaanCardio.getText(),PemeriksaanInte.getText(),PengukuranMuscu.getText(),
                        PengukuranNeuro.getText(),PengukuranCardio.getText(),PengukuranInte.getText(),Penunjang.getText(),Diagnosis.getText(),Rencana.getText(),KdPetugas.getText(),NmPetugas.getText()
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
            Valid.pindah(evt,Rencana,BtnBatal);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString())){
                    hapus();
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD(mmHg)");
        }else if(HR.getText().trim().equals("")){
            Valid.textKosong(HR,"Nadi(x/menit)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu(C)");
        }else if(NyeriTekan.getText().trim().equals("")){
            Valid.textKosong(NyeriTekan,"GCS");
        }else if(NyeriGerak.getText().trim().equals("")){
            Valid.textKosong(NyeriGerak,"BB(Kg)");
        }else if(NyeriDiam.getText().trim().equals("")){
            Valid.textKosong(NyeriDiam,"TB(Cm)");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Informasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='270px'><b>Keluhan Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='184px'><b>Riwayat Peyakit Sekarang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='184px'><b>Riwayat Penyakit Dahulu & Penyerta</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>TD</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='30px'><b>HR</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='30px'><b>RR</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'><b>Suhu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>Nyeri Tekan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>Nyeri Gerak</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>Nyeri Diam</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Palpasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Luas Gerak Sendi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Kekuatan Otot</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Statis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Dinamis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Kognitif</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Auskultasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>Alat Bantu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Ket Alat Bantu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='53px'><b>Prothesa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>Keteranga Prothesa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='62px'><b>Deformitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Keterangan Deformitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>Resiko Jatuh</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Keterangan Resiko Jatuh</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>ADL</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Fungsional Lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='270px'><b>Keterangan Fisik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pemeriksaan Musculoskeletal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pemeriksaan Neuromuscular</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pemeriksaan Cardiopulmonal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pemeriksaan Integument</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pengukuran Musculoskeletal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pengukuran Neuromuscular</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pengukuran Cardiopulmonal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pengukuran Integument</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Pemeriksaan Penunjang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Diagnosis Fisio</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Rencana Intervensi Fisioterapi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Petugas</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,47).toString()+"</td>"+
                        "</tr>");
                }

                LoadHTML.setText(
                    "<html>"+
                      "<table width='5500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianAwalMedisRanap.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='5500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL FISIOTERAPI<br><br></font>"+        
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

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void NyeriGerakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriGerakKeyPressed
        Valid.pindah(evt,NyeriTekan,NyeriDiam);
    }//GEN-LAST:event_NyeriGerakKeyPressed

    private void NyeriDiamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriDiamKeyPressed
        Valid.pindah(evt,NyeriGerak,Palpasi);
    }//GEN-LAST:event_NyeriDiamKeyPressed

    private void HRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HRKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_HRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,NyeriTekan);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Informasi,HR);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,HR,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt,TglAsuhan,KeluhanUtama);
    }//GEN-LAST:event_InformasiKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void NyeriTekanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriTekanKeyPressed
        Valid.pindah(evt,Suhu,NyeriGerak);
    }//GEN-LAST:event_NyeriTekanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Informasi,RiwayatPenyakitSekarang);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RiwayatPenyakitSekarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitSekarangKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RiwayatPenyakitDahulu);
    }//GEN-LAST:event_RiwayatPenyakitSekarangKeyPressed

    private void RiwayatPenyakitDahuluKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitDahuluKeyPressed
        Valid.pindah2(evt,RiwayatPenyakitSekarang,TD);
    }//GEN-LAST:event_RiwayatPenyakitDahuluKeyPressed

    private void PalpasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PalpasiKeyPressed
        Valid.pindah(evt,NyeriDiam,LuasGerakSendi);
    }//GEN-LAST:event_PalpasiKeyPressed

    private void LuasGerakSendiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LuasGerakSendiKeyPressed
        Valid.pindah(evt,Palpasi,KekuatanOtot);
    }//GEN-LAST:event_LuasGerakSendiKeyPressed

    private void KekuatanOtotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KekuatanOtotKeyPressed
        Valid.pindah(evt,LuasGerakSendi,Statis);
    }//GEN-LAST:event_KekuatanOtotKeyPressed

    private void StatisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatisKeyPressed
        Valid.pindah(evt,KekuatanOtot,Dinamis);
    }//GEN-LAST:event_StatisKeyPressed

    private void DinamisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DinamisKeyPressed
        Valid.pindah(evt,Statis,Kognitif);
    }//GEN-LAST:event_DinamisKeyPressed

    private void KognitifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KognitifKeyPressed
        Valid.pindah(evt,Dinamis,Auskultasi);
    }//GEN-LAST:event_KognitifKeyPressed

    private void AuskultasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AuskultasiKeyPressed
        Valid.pindah(evt,Kognitif,AlatBantu);
    }//GEN-LAST:event_AuskultasiKeyPressed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantuKeyPressed
        Valid.pindah(evt,Auskultasi,KetBantu);
    }//GEN-LAST:event_AlatBantuKeyPressed

    private void KetBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBantuKeyPressed
        Valid.pindah(evt,AlatBantu,Prothesa);
    }//GEN-LAST:event_KetBantuKeyPressed

    private void ProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProthesaKeyPressed
        Valid.pindah(evt,KetBantu,KetProthesa);
    }//GEN-LAST:event_ProthesaKeyPressed

    private void KetProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProthesaKeyPressed
        Valid.pindah(evt,Prothesa,Deformitas);
    }//GEN-LAST:event_KetProthesaKeyPressed

    private void DeformitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DeformitasKeyPressed
        Valid.pindah(evt,KetProthesa,KetDeformitas);
    }//GEN-LAST:event_DeformitasKeyPressed

    private void KetDeformitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetDeformitasKeyPressed
        Valid.pindah(evt,Deformitas,ResikoJatuh);
    }//GEN-LAST:event_KetDeformitasKeyPressed

    private void ResikoJatuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResikoJatuhKeyPressed
        Valid.pindah(evt,KetDeformitas,KetResikoJatuh);
    }//GEN-LAST:event_ResikoJatuhKeyPressed

    private void KetResikoJatuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetResikoJatuhKeyPressed
        Valid.pindah(evt,ResikoJatuh,ADL);
    }//GEN-LAST:event_KetResikoJatuhKeyPressed

    private void ADLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLKeyPressed
        Valid.pindah(evt,KetResikoJatuh,LainlainFungsioal);
    }//GEN-LAST:event_ADLKeyPressed

    private void LainlainFungsioalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainlainFungsioalKeyPressed
        Valid.pindah(evt,ADL,KetFisik);
    }//GEN-LAST:event_LainlainFungsioalKeyPressed

    private void KetFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetFisikKeyPressed
        Valid.pindah2(evt,LainlainFungsioal,PemeriksaanMuscu);
    }//GEN-LAST:event_KetFisikKeyPressed

    private void PemeriksaanMuscuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanMuscuKeyPressed
        Valid.pindah2(evt,KetFisik,PemeriksaanNeuro);
    }//GEN-LAST:event_PemeriksaanMuscuKeyPressed

    private void PemeriksaanNeuroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanNeuroKeyPressed
        Valid.pindah2(evt,PemeriksaanMuscu,PemeriksaanCardio);
    }//GEN-LAST:event_PemeriksaanNeuroKeyPressed

    private void PemeriksaanCardioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanCardioKeyPressed
        Valid.pindah2(evt,PemeriksaanNeuro,PemeriksaanInte);
    }//GEN-LAST:event_PemeriksaanCardioKeyPressed

    private void PemeriksaanInteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanInteKeyPressed
        Valid.pindah2(evt,PemeriksaanCardio,PengukuranMuscu);
    }//GEN-LAST:event_PemeriksaanInteKeyPressed

    private void PengukuranNeuroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengukuranNeuroKeyPressed
        Valid.pindah2(evt,PengukuranMuscu,PengukuranCardio);
    }//GEN-LAST:event_PengukuranNeuroKeyPressed

    private void PengukuranMuscuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengukuranMuscuKeyPressed
        Valid.pindah2(evt,PemeriksaanInte,PengukuranNeuro);
    }//GEN-LAST:event_PengukuranMuscuKeyPressed

    private void PengukuranInteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengukuranInteKeyPressed
        Valid.pindah2(evt,PengukuranCardio,Penunjang);
    }//GEN-LAST:event_PengukuranInteKeyPressed

    private void PengukuranCardioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengukuranCardioKeyPressed
        Valid.pindah2(evt,PengukuranNeuro,PengukuranInte);
    }//GEN-LAST:event_PengukuranCardioKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        Valid.pindah2(evt,PengukuranInte,Diagnosis);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        Valid.pindah2(evt,Penunjang,Rencana);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        Valid.pindah2(evt,Diagnosis,BtnSimpan);
    }//GEN-LAST:event_RencanaKeyPressed

    private void MnPenilaianFisioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianFisioActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            param.put("nyeri",Sequel.cariGambar("select gambar.nyeri from gambar"));
            try {
                param.put("lokalis",getClass().getResource("/picture/fisiobody.png").openStream());
            } catch (Exception e) {
            }   
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),47).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),46).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()));

            Valid.MyReportqry("rptCetakPenilaianAwalFisioterapi.jasper","report","::[ Laporan Penilaian Awal Fisioterapi ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_fisioterapi.tanggal,penilaian_fisioterapi.informasi,penilaian_fisioterapi.keluhan_utama,"+
                "penilaian_fisioterapi.rps,penilaian_fisioterapi.rpd,penilaian_fisioterapi.td,penilaian_fisioterapi.hr,penilaian_fisioterapi.rr,penilaian_fisioterapi.suhu,penilaian_fisioterapi.nyeri_tekan,penilaian_fisioterapi.nyeri_gerak,"+
                "penilaian_fisioterapi.nyeri_diam,penilaian_fisioterapi.palpasi,penilaian_fisioterapi.luas_gerak_sendi,penilaian_fisioterapi.kekuatan_otot,penilaian_fisioterapi.statis,penilaian_fisioterapi.dinamis,penilaian_fisioterapi.kognitif,"+
                "penilaian_fisioterapi.auskultasi,penilaian_fisioterapi.alat_bantu,penilaian_fisioterapi.ket_bantu,penilaian_fisioterapi.prothesa,penilaian_fisioterapi.ket_pro,penilaian_fisioterapi.deformitas,penilaian_fisioterapi.ket_deformitas,"+
                "penilaian_fisioterapi.resikojatuh,penilaian_fisioterapi.ket_resikojatuh,penilaian_fisioterapi.adl,penilaian_fisioterapi.lainlain_fungsional,penilaian_fisioterapi.ket_fisik,penilaian_fisioterapi.pemeriksaan_musculoskeletal,"+
                "penilaian_fisioterapi.pemeriksaan_neuromuscular,penilaian_fisioterapi.pemeriksaan_cardiopulmonal,penilaian_fisioterapi.pemeriksaan_integument,penilaian_fisioterapi.pengukuran_musculoskeletal,penilaian_fisioterapi.pengukuran_neuromuscular,"+
                "penilaian_fisioterapi.pengukuran_cardiopulmonal,penilaian_fisioterapi.pengukuran_integument,penilaian_fisioterapi.penunjang,penilaian_fisioterapi.diagnosis_fisio,penilaian_fisioterapi.rencana_terapi,penilaian_fisioterapi.nip,pegawai.nama "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_fisioterapi on reg_periksa.no_rawat=penilaian_fisioterapi.no_rawat "+
                "inner join pegawai on penilaian_fisioterapi.nip=pegawai.nik where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianFisioActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianFisioterapi dialog = new RMPenilaianFisioterapi(new javax.swing.JFrame(), true);
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
    private widget.ComboBox ADL;
    private widget.ComboBox AlatBantu;
    private widget.TextBox Auskultasi;
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
    private widget.ComboBox Deformitas;
    private widget.TextArea Diagnosis;
    private widget.TextBox Dinamis;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HR;
    private widget.ComboBox Informasi;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox KekuatanOtot;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetBantu;
    private widget.TextBox KetDeformitas;
    private widget.TextArea KetFisik;
    private widget.TextBox KetProthesa;
    private widget.TextBox KetResikoJatuh;
    private widget.TextBox Kognitif;
    private widget.Label LCount;
    private widget.TextBox LainlainFungsioal;
    private widget.editorpane LoadHTML;
    private widget.TextBox LuasGerakSendi;
    private javax.swing.JMenuItem MnPenilaianFisio;
    private widget.TextBox NmPetugas;
    private widget.TextBox NyeriDiam;
    private widget.TextBox NyeriGerak;
    private widget.TextBox NyeriTekan;
    private widget.TextBox Palpasi;
    private usu.widget.glass.PanelGlass PanelWall;
    private usu.widget.glass.PanelGlass PanelWall1;
    private widget.TextArea PemeriksaanCardio;
    private widget.TextArea PemeriksaanInte;
    private widget.TextArea PemeriksaanMuscu;
    private widget.TextArea PemeriksaanNeuro;
    private widget.TextArea PengukuranCardio;
    private widget.TextArea PengukuranInte;
    private widget.TextArea PengukuranMuscu;
    private widget.TextArea PengukuranNeuro;
    private widget.TextArea Penunjang;
    private widget.ComboBox Prothesa;
    private widget.TextBox RR;
    private widget.TextArea Rencana;
    private widget.ComboBox ResikoJatuh;
    private widget.TextArea RiwayatPenyakitDahulu;
    private widget.TextArea RiwayatPenyakitSekarang;
    private widget.ScrollPane Scroll;
    private widget.TextBox Statis;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
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
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator9;
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
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_fisioterapi.tanggal,penilaian_fisioterapi.informasi,penilaian_fisioterapi.keluhan_utama,"+
                        "penilaian_fisioterapi.rps,penilaian_fisioterapi.rpd,penilaian_fisioterapi.td,penilaian_fisioterapi.hr,penilaian_fisioterapi.rr,penilaian_fisioterapi.suhu,penilaian_fisioterapi.nyeri_tekan,penilaian_fisioterapi.nyeri_gerak,"+
                        "penilaian_fisioterapi.nyeri_diam,penilaian_fisioterapi.palpasi,penilaian_fisioterapi.luas_gerak_sendi,penilaian_fisioterapi.kekuatan_otot,penilaian_fisioterapi.statis,penilaian_fisioterapi.dinamis,penilaian_fisioterapi.kognitif,"+
                        "penilaian_fisioterapi.auskultasi,penilaian_fisioterapi.alat_bantu,penilaian_fisioterapi.ket_bantu,penilaian_fisioterapi.prothesa,penilaian_fisioterapi.ket_pro,penilaian_fisioterapi.deformitas,penilaian_fisioterapi.ket_deformitas,"+
                        "penilaian_fisioterapi.resikojatuh,penilaian_fisioterapi.ket_resikojatuh,penilaian_fisioterapi.adl,penilaian_fisioterapi.lainlain_fungsional,penilaian_fisioterapi.ket_fisik,penilaian_fisioterapi.pemeriksaan_musculoskeletal,"+
                        "penilaian_fisioterapi.pemeriksaan_neuromuscular,penilaian_fisioterapi.pemeriksaan_cardiopulmonal,penilaian_fisioterapi.pemeriksaan_integument,penilaian_fisioterapi.pengukuran_musculoskeletal,penilaian_fisioterapi.pengukuran_neuromuscular,"+
                        "penilaian_fisioterapi.pengukuran_cardiopulmonal,penilaian_fisioterapi.pengukuran_integument,penilaian_fisioterapi.penunjang,penilaian_fisioterapi.diagnosis_fisio,penilaian_fisioterapi.rencana_terapi,penilaian_fisioterapi.nip,pegawai.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_fisioterapi on reg_periksa.no_rawat=penilaian_fisioterapi.no_rawat "+
                        "inner join pegawai on penilaian_fisioterapi.nip=pegawai.nik where "+
                        "penilaian_fisioterapi.tanggal between ? and ? order by penilaian_fisioterapi.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_fisioterapi.tanggal,penilaian_fisioterapi.informasi,penilaian_fisioterapi.keluhan_utama,"+
                        "penilaian_fisioterapi.rps,penilaian_fisioterapi.rpd,penilaian_fisioterapi.td,penilaian_fisioterapi.hr,penilaian_fisioterapi.rr,penilaian_fisioterapi.suhu,penilaian_fisioterapi.nyeri_tekan,penilaian_fisioterapi.nyeri_gerak,"+
                        "penilaian_fisioterapi.nyeri_diam,penilaian_fisioterapi.palpasi,penilaian_fisioterapi.luas_gerak_sendi,penilaian_fisioterapi.kekuatan_otot,penilaian_fisioterapi.statis,penilaian_fisioterapi.dinamis,penilaian_fisioterapi.kognitif,"+
                        "penilaian_fisioterapi.auskultasi,penilaian_fisioterapi.alat_bantu,penilaian_fisioterapi.ket_bantu,penilaian_fisioterapi.prothesa,penilaian_fisioterapi.ket_pro,penilaian_fisioterapi.deformitas,penilaian_fisioterapi.ket_deformitas,"+
                        "penilaian_fisioterapi.resikojatuh,penilaian_fisioterapi.ket_resikojatuh,penilaian_fisioterapi.adl,penilaian_fisioterapi.lainlain_fungsional,penilaian_fisioterapi.ket_fisik,penilaian_fisioterapi.pemeriksaan_musculoskeletal,"+
                        "penilaian_fisioterapi.pemeriksaan_neuromuscular,penilaian_fisioterapi.pemeriksaan_cardiopulmonal,penilaian_fisioterapi.pemeriksaan_integument,penilaian_fisioterapi.pengukuran_musculoskeletal,penilaian_fisioterapi.pengukuran_neuromuscular,"+
                        "penilaian_fisioterapi.pengukuran_cardiopulmonal,penilaian_fisioterapi.pengukuran_integument,penilaian_fisioterapi.penunjang,penilaian_fisioterapi.diagnosis_fisio,penilaian_fisioterapi.rencana_terapi,penilaian_fisioterapi.nip,pegawai.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_fisioterapi on reg_periksa.no_rawat=penilaian_fisioterapi.no_rawat "+
                        "inner join pegawai on penilaian_fisioterapi.nip=pegawai.nik where penilaian_fisioterapi.tanggal between ? and ? and "+
                        "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_fisioterapi.nip like ? or pegawai.nama like ?) order by penilaian_fisioterapi.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),
                        rs.getString("tanggal"),rs.getString("informasi"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),
                        rs.getString("td"),rs.getString("hr"),rs.getString("rr"),rs.getString("suhu"),rs.getString("nyeri_tekan"),rs.getString("nyeri_gerak"),
                        rs.getString("nyeri_diam"),rs.getString("palpasi"),rs.getString("luas_gerak_sendi"),rs.getString("kekuatan_otot"),rs.getString("statis"),
                        rs.getString("dinamis"),rs.getString("kognitif"),rs.getString("auskultasi"),rs.getString("alat_bantu"),rs.getString("ket_bantu"),
                        rs.getString("prothesa"),rs.getString("ket_pro"),rs.getString("deformitas"),rs.getString("ket_deformitas"),rs.getString("resikojatuh"),
                        rs.getString("ket_resikojatuh"),rs.getString("adl"),rs.getString("lainlain_fungsional"),rs.getString("ket_fisik"),
                        rs.getString("pemeriksaan_musculoskeletal"),rs.getString("pemeriksaan_neuromuscular"),rs.getString("pemeriksaan_cardiopulmonal"),
                        rs.getString("pemeriksaan_integument"),rs.getString("pengukuran_musculoskeletal"),rs.getString("pengukuran_neuromuscular"),
                        rs.getString("pengukuran_cardiopulmonal"),rs.getString("pengukuran_integument"),rs.getString("penunjang"),rs.getString("diagnosis_fisio"),
                        rs.getString("rencana_terapi"),rs.getString("nip"),rs.getString("nama")
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
        TglAsuhan.setDate(new Date());
        Informasi.setSelectedIndex(0);
        KeluhanUtama.setText("");
        RiwayatPenyakitDahulu.setText("");
        RiwayatPenyakitSekarang.setText("");
        TD.setText("");
        HR.setText("");
        RR.setText("");
        Suhu.setText("");
        NyeriTekan.setText("");
        NyeriGerak.setText("");
        NyeriDiam.setText("");
        Palpasi.setText("");
        LuasGerakSendi.setText("");
        KekuatanOtot.setText("");
        Statis.setText("");
        Kognitif.setText("");
        Dinamis.setText("");
        Auskultasi.setText("");
        AlatBantu.setSelectedIndex(0);
        KetBantu.setText("");
        Deformitas.setSelectedIndex(0);
        KetDeformitas.setText("");
        Prothesa.setSelectedIndex(0);
        KetProthesa.setText("");
        ADL.setSelectedIndex(0);
        LainlainFungsioal.setText("");
        KetFisik.setText("");
        PemeriksaanMuscu.setText("");
        PemeriksaanNeuro.setText("");
        PemeriksaanCardio.setText("");
        PemeriksaanInte.setText("");
        PengukuranMuscu.setText("");
        PengukuranNeuro.setText("");
        PengukuranCardio.setText("");
        PengukuranInte.setText("");
        Penunjang.setText("");
        Diagnosis.setText("");
        Rencana.setText("");
        
        TabRawat.setSelectedIndex(0);
        Informasi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()); 
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            RiwayatPenyakitSekarang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            RiwayatPenyakitDahulu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
            HR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            NyeriTekan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            NyeriGerak.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            NyeriDiam.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
            Palpasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()); 
            LuasGerakSendi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            KekuatanOtot.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());  
            Statis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());  
            Dinamis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());  
            Kognitif.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());  
            Auskultasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString()); 
            AlatBantu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString()); 
            KetBantu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString()); 
            Prothesa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString()); 
            KetProthesa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString()); 
            Deformitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString()); 
            KetDeformitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            ResikoJatuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString()); 
            KetResikoJatuh.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());  
            ADL.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString()); 
            LainlainFungsioal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString()); 
            KetFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());  
            PemeriksaanMuscu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString()); 
            PemeriksaanNeuro.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString()); 
            PemeriksaanCardio.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString()); 
            PemeriksaanInte.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString()); 
            PengukuranMuscu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString()); 
            PengukuranNeuro.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString()); 
            PengukuranCardio.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString()); 
            PengukuranInte.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString()); 
            Penunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString()); 
            Diagnosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString()); 
            Rencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString()); 
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    Jk.setText(rs.getString("jk"));
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
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_fisioterapi());
        BtnHapus.setEnabled(akses.getpenilaian_fisioterapi());
        BtnEdit.setEnabled(akses.getpenilaian_fisioterapi());
        BtnEdit.setEnabled(akses.getpenilaian_fisioterapi());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
    
    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_fisioterapi where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_fisioterapi","no_rawat=?","no_rawat=?,tanggal=?,informasi=?,keluhan_utama=?,rps=?,rpd=?,td=?,hr=?,rr=?,suhu=?,nyeri_tekan=?,nyeri_gerak=?,nyeri_diam=?,palpasi=?,luas_gerak_sendi=?,kekuatan_otot=?,statis=?,dinamis=?,kognitif=?,auskultasi=?,alat_bantu=?,ket_bantu=?,prothesa=?,ket_pro=?,deformitas=?,ket_deformitas=?,resikojatuh=?,ket_resikojatuh=?,adl=?,lainlain_fungsional=?,ket_fisik=?,pemeriksaan_musculoskeletal=?,pemeriksaan_neuromuscular=?,pemeriksaan_cardiopulmonal=?,pemeriksaan_integument=?,pengukuran_musculoskeletal=?,pengukuran_neuromuscular=?,pengukuran_cardiopulmonal=?,pengukuran_integument=?,penunjang=?,diagnosis_fisio=?,rencana_terapi=?,nip=?",44,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),KeluhanUtama.getText(),
                RiwayatPenyakitSekarang.getText(),RiwayatPenyakitDahulu.getText(),TD.getText(),HR.getText(),RR.getText(),Suhu.getText(),NyeriTekan.getText(),NyeriGerak.getText(),NyeriDiam.getText(),
                Palpasi.getText(),LuasGerakSendi.getText(),KekuatanOtot.getText(),Statis.getText(),Dinamis.getText(),Kognitif.getText(),Auskultasi.getText(),AlatBantu.getSelectedItem().toString(),
                KetBantu.getText(),Prothesa.getSelectedItem().toString(),KetProthesa.getText(),Deformitas.getSelectedItem().toString(),KetDeformitas.getText(),ResikoJatuh.getSelectedItem().toString(),
                KetResikoJatuh.getText(),ADL.getSelectedItem().toString(),LainlainFungsioal.getText(),KetFisik.getText(),PemeriksaanMuscu.getText(),PemeriksaanNeuro.getText(),PemeriksaanCardio.getText(),
                PemeriksaanInte.getText(),PengukuranMuscu.getText(),PengukuranNeuro.getText(),PengukuranCardio.getText(),PengukuranInte.getText(),Penunjang.getText(),Diagnosis.getText(),Rencana.getText(),
                KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Informasi.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(RiwayatPenyakitSekarang.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(RiwayatPenyakitDahulu.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(HR.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(NyeriTekan.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(NyeriGerak.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(NyeriDiam.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(Palpasi.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(LuasGerakSendi.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(KekuatanOtot.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(Statis.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(Dinamis.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(Kognitif.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(Auskultasi.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(AlatBantu.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(KetBantu.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(Prothesa.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(KetProthesa.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(Deformitas.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(KetDeformitas.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(ResikoJatuh.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(KetResikoJatuh.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(ADL.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(LainlainFungsioal.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(KetFisik.getText(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(PemeriksaanMuscu.getText(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(PemeriksaanNeuro.getText(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(PemeriksaanCardio.getText(),tbObat.getSelectedRow(),37);
                tbObat.setValueAt(PemeriksaanInte.getText(),tbObat.getSelectedRow(),38);
                tbObat.setValueAt(PengukuranMuscu.getText(),tbObat.getSelectedRow(),39);
                tbObat.setValueAt(PengukuranNeuro.getText(),tbObat.getSelectedRow(),40);
                tbObat.setValueAt(PengukuranCardio.getText(),tbObat.getSelectedRow(),41);
                tbObat.setValueAt(PengukuranInte.getText(),tbObat.getSelectedRow(),42);
                tbObat.setValueAt(Penunjang.getText(),tbObat.getSelectedRow(),43);
                tbObat.setValueAt(Diagnosis.getText(),tbObat.getSelectedRow(),44);
                tbObat.setValueAt(Rencana.getText(),tbObat.getSelectedRow(),45);
                tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),46);
                tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),47);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }
}

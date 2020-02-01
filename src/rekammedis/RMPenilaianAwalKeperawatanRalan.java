/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalKeperawatanRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String informasi, alat_bantu, prothesa, adl, status_psiko, hub_keluarga, tinggal_dengan, ekonomi, edukasi, berjalan_a, berjalan_b, berjalan_c, hasil, lapor, sg1, nilai1, sg2, nilai2, nyeri, provokes, quality, menyebar, skala_nyeri, nyeri_hilang, pada_dokter, masalah_keperawatan;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Tgl.Asuhan","Informasi","TD(mmHg)","Nadi(x/menit)","RR(x/menit)","Suhu(C)",
            "BB(Kg)","TB(Cm)","BMI(Kg/m2)","Keluhan Utama","RPD","RPK","RPO","Alergi","Alat Bantu","Ket. Alat Bantu",
            "Prothesa","Ket. Prothesa","ADL","Stts Psikologi","Ket. Psikologi","Hubungan Pasien","Tinggal Dengan","Ket. Tinggal","Ekonomi",
            "Edukasi","Ket. Edukasi","Cara Berjalan A","Cara Berjalan B","Cara Berjalan C","Hasil","Lapor Dokter","Ket. Lapor","Skrining Gizi 1","Nilai 1",
            "Skrining Gizi 2","Nilai 2","Total Hasil","Tingkat Nyeri","Provokes","Ket. Provokes","Quality","Ket. Quality","Region","Menyebar",
            "Skala Nyeri","Durasi","Nyeri Hilang","Ket. Hilang Nyeri","Ke Dokter","Ket. Ke Dokter","Masalah Keperawatan","Ket. Masalah Keperawatan","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 60; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(25);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(41);
            }else if(i==7){
                column.setPreferredWidth(44);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(52);
            }else if(i==10){
                column.setPreferredWidth(43);
            }else if(i==11){
                column.setPreferredWidth(58);
            }else if(i==12){
                column.setPreferredWidth(68);
            }else if(i==13){
                column.setPreferredWidth(50);
            }else if(i==14){
                column.setPreferredWidth(50);
            }else if(i==15){
                column.setPreferredWidth(55);
            }else if(i==16){
                column.setPreferredWidth(58);
            }else if(i==17){
                column.setPreferredWidth(180);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(38);
            }else if(i==20){
                column.setPreferredWidth(55);
            }else if(i==21){
                column.setPreferredWidth(44);
            }else if(i==22){
                column.setPreferredWidth(42);
            }else if(i==23){
                column.setPreferredWidth(42);
            }else if(i==24){
                column.setPreferredWidth(38);
            }else if(i==25){
                column.setPreferredWidth(53);
            }else if(i==26){
                column.setPreferredWidth(180);
            }else if(i==27){
                column.setPreferredWidth(180);
            }else if(i==28){
                column.setPreferredWidth(180);
            }else if(i==29){
                column.setPreferredWidth(180);
            }else if(i==30){
                column.setPreferredWidth(180);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(150);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(150);
            }else if(i==36){
                column.setPreferredWidth(150);
            }else if(i==37){
                column.setPreferredWidth(150);
            }else if(i==38){
                column.setPreferredWidth(150);
            }else if(i==39){
                column.setPreferredWidth(150);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(150);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(150);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(150);
            }else if(i==46){
                column.setPreferredWidth(150);
            }else if(i==47){
                column.setPreferredWidth(150);
            }else if(i==48){
                column.setPreferredWidth(150);
            }else if(i==49){
                column.setPreferredWidth(150);
            }else if(i==50){
                column.setPreferredWidth(150);
            }else if(i==51){
                column.setPreferredWidth(150);
            }else if(i==52){
                column.setPreferredWidth(150);
            }else if(i==53){
                column.setPreferredWidth(150);
            }else if(i==54){
                column.setPreferredWidth(150);
            }else if(i==55){
                column.setPreferredWidth(150);
            }else if(i==56){
                column.setPreferredWidth(150);
            }else if(i==57){
                column.setPreferredWidth(150);
            }else if(i==58){
                column.setPreferredWidth(150);
            }else if(i==59){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TD.setDocument(new batasInput((byte)5).getKata(TD));
        NADI.setDocument(new batasInput((byte)5).getKata(NADI));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        SUHU.setDocument(new batasInput((byte)5).getKata(SUHU));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        BMI.setDocument(new batasInput((byte)5).getKata(BMI));
        TKeluhan.setDocument(new batasInput((int)2000).getKata(TKeluhan));
        TRPD.setDocument(new batasInput((int)1000).getKata(TRPD));
        TRPK.setDocument(new batasInput((int)1000).getKata(TRPK));
        TRPO.setDocument(new batasInput((int)1000).getKata(TRPO));
        Alergi.setDocument(new batasInput((int)100).getKata(Alergi));
        KetBantu.setDocument(new batasInput((int)100).getKata(KetBantu));
        KetPro.setDocument(new batasInput((int)100).getKata(KetPro));
        KetPsiko.setDocument(new batasInput((int)100).getKata(KetPsiko));
        KetTinggal.setDocument(new batasInput((int)100).getKata(KetTinggal));
        KetEdukasi.setDocument(new batasInput((int)100).getKata(KetEdukasi));
        KetLapor.setDocument(new batasInput((int)100).getKata(KetLapor));
        TotalHasil.setDocument(new batasInput((byte)5).getKata(TotalHasil));
        KetProvokes.setDocument(new batasInput((int)100).getKata(KetProvokes));
        KetQuality.setDocument(new batasInput((int)100).getKata(KetQuality));
        Lokasi.setDocument(new batasInput((int)100).getKata(Lokasi));
        Durasi.setDocument(new batasInput((byte)5).getKata(Durasi));
        KetNyeri.setDocument(new batasInput((int)100).getKata(KetNyeri));
        KetDokter.setDocument(new batasInput((int)100).getKata(KetDokter));
        TRencana.setDocument(new batasInput((int)2000).getKata(TRencana));
        
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
        
        ChkInput.setSelected(false);
        isForm();
      
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
        MnAsuhanGizi = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
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
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        TB = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        NADI = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        SUHU = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel14 = new widget.Label();
        BMI = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel52 = new widget.Label();
        Informasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        TKeluhan = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        TRPD = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        TRPK = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        TRPO = new widget.TextArea();
        AlatBantu = new widget.ComboBox();
        KetBantu = new widget.TextBox();
        jLabel54 = new widget.Label();
        Prothesa = new widget.ComboBox();
        KetPro = new widget.TextBox();
        jLabel55 = new widget.Label();
        ADL = new widget.ComboBox();
        jLabel57 = new widget.Label();
        StatusPsiko = new widget.ComboBox();
        KetPsiko = new widget.TextBox();
        jLabel58 = new widget.Label();
        HubunganKeluarga = new widget.ComboBox();
        jLabel59 = new widget.Label();
        TinggalDengan = new widget.ComboBox();
        KetTinggal = new widget.TextBox();
        jLabel60 = new widget.Label();
        Ekonomi = new widget.ComboBox();
        jLabel61 = new widget.Label();
        Edukasi = new widget.ComboBox();
        KetEdukasi = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        Lapor = new widget.ComboBox();
        ATS = new widget.ComboBox();
        BJM = new widget.ComboBox();
        jLabel67 = new widget.Label();
        Hasil = new widget.ComboBox();
        jLabel68 = new widget.Label();
        SG2 = new widget.ComboBox();
        KetLapor = new widget.TextBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        SG1 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        Nilai1 = new widget.ComboBox();
        MSA = new widget.ComboBox();
        jLabel73 = new widget.Label();
        Nilai2 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        Nyeri = new widget.ComboBox();
        jLabel79 = new widget.Label();
        Provokes = new widget.ComboBox();
        KetProvokes = new widget.TextBox();
        jLabel80 = new widget.Label();
        Quality = new widget.ComboBox();
        KetQuality = new widget.TextBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Lokasi = new widget.TextBox();
        jLabel83 = new widget.Label();
        Menyebar = new widget.ComboBox();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        SkalaNyeri = new widget.ComboBox();
        jLabel86 = new widget.Label();
        Durasi = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        NyeriHilang = new widget.ComboBox();
        KetNyeri = new widget.TextBox();
        jLabel89 = new widget.Label();
        PadaDokter = new widget.ComboBox();
        KetDokter = new widget.TextBox();
        MasalahKeperawatan = new widget.ComboBox();
        jLabel91 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        TRencana = new widget.TextArea();
        jLabel92 = new widget.Label();
        TotalHasil = new widget.TextBox();
        TglAsuhan = new widget.Tanggal();
        jLabel28 = new widget.Label();
        SUHU1 = new widget.TextBox();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel51 = new widget.Label();
        Prothesa1 = new widget.ComboBox();
        KetPro1 = new widget.TextBox();
        jLabel56 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel95 = new widget.Label();
        StatusPsiko1 = new widget.ComboBox();
        KetPsiko1 = new widget.TextBox();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        jLabel63 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel74 = new widget.Label();
        PanelWall = new usu.widget.glass.PanelGlass();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnAsuhanGizi.setBackground(new java.awt.Color(255, 255, 254));
        MnAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAsuhanGizi.setForeground(new java.awt.Color(50, 50, 50));
        MnAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAsuhanGizi.setText("Laporan Asesmen Keperawatan");
        MnAsuhanGizi.setName("MnAsuhanGizi"); // NOI18N
        MnAsuhanGizi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAsuhanGiziActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnAsuhanGizi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-02-2020" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-02-2020" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 448));
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

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1500));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 141, 23);

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

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
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

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setText("Riwayat Pengobatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(440, 240, 150, 23);

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

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 140, 70, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(74, 140, 60, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(137, 140, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(250, 140, 60, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(206, 140, 40, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(313, 90, 50, 23);

        NADI.setFocusTraversalPolicyProvider(true);
        NADI.setName("NADI"); // NOI18N
        NADI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NADIKeyPressed(evt);
            }
        });
        FormInput.add(NADI);
        NADI.setBounds(250, 90, 60, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(206, 90, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(566, 90, 40, 23);

        SUHU.setFocusTraversalPolicyProvider(true);
        SUHU.setName("SUHU"); // NOI18N
        SUHU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SUHUKeyPressed(evt);
            }
        });
        FormInput.add(SUHU);
        SUHU.setBounds(610, 90, 60, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 90, 70, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TDActionPerformed(evt);
            }
        });
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(74, 90, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(673, 90, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(137, 90, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(313, 140, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(485, 90, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(422, 90, 60, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(378, 90, 40, 23);

        jLabel14.setText("BMI :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(378, 140, 40, 23);

        BMI.setFocusTraversalPolicyProvider(true);
        BMI.setName("BMI"); // NOI18N
        BMI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BMIKeyPressed(evt);
            }
        });
        FormInput.add(BMI);
        BMI.setBounds(422, 140, 60, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Kg/m2");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(485, 140, 50, 23);

        jLabel36.setText("Informasi didapat dari :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(592, 40, 130, 23);

        jLabel37.setText("Alergi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 290, 175, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(179, 290, 260, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("2. Apakah nafsu makan berkurang karena tidak nafsu makan ?");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(40, 740, 460, 23);

        jLabel50.setText("Prothesa :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(476, 340, 60, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("VIII. PENILIAIAN TINGKAT NYERI");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(10, 800, 380, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InformasiActionPerformed(evt);
            }
        });
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(726, 40, 128, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. KEADAAN UMUM");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        TKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKeluhan.setColumns(20);
        TKeluhan.setRows(5);
        TKeluhan.setName("TKeluhan"); // NOI18N
        TKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeluhanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(TKeluhan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(179, 190, 260, 43);

        jLabel30.setText("Keluhan Utama :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 190, 175, 20);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        TRPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TRPD.setColumns(20);
        TRPD.setRows(5);
        TRPD.setName("TRPD"); // NOI18N
        TRPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(TRPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(179, 240, 260, 43);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 240, 175, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        TRPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TRPK.setColumns(20);
        TRPK.setRows(5);
        TRPK.setName("TRPK"); // NOI18N
        TRPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRPKKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TRPK);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(594, 190, 260, 42);

        jLabel32.setText("Riwayat Penyakit Keluarga :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(440, 190, 150, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        TRPO.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TRPO.setColumns(20);
        TRPO.setRows(5);
        TRPO.setName("TRPO"); // NOI18N
        TRPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRPOKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(TRPO);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(594, 240, 260, 42);

        AlatBantu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlatBantuActionPerformed(evt);
            }
        });
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu);
        AlatBantu.setBounds(124, 340, 90, 23);

        KetBantu.setFocusTraversalPolicyProvider(true);
        KetBantu.setName("KetBantu"); // NOI18N
        KetBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBantuKeyPressed(evt);
            }
        });
        FormInput.add(KetBantu);
        KetBantu.setBounds(218, 340, 220, 23);

        jLabel54.setText("a. Hubungan pasien dengan anggota keluarga :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(35, 470, 250, 23);

        Prothesa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Prothesa.setName("Prothesa"); // NOI18N
        Prothesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProthesaActionPerformed(evt);
            }
        });
        Prothesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProthesaKeyPressed(evt);
            }
        });
        FormInput.add(Prothesa);
        Prothesa.setBounds(540, 340, 90, 23);

        KetPro.setFocusTraversalPolicyProvider(true);
        KetPro.setName("KetPro"); // NOI18N
        KetPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProKeyPressed(evt);
            }
        });
        FormInput.add(KetPro);
        KetPro.setBounds(634, 340, 220, 23);

        jLabel55.setText("Alat Bantu :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 340, 120, 23);

        ADL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Dibantu" }));
        ADL.setName("ADL"); // NOI18N
        ADL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ADLActionPerformed(evt);
            }
        });
        ADL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLKeyPressed(evt);
            }
        });
        FormInput.add(ADL);
        ADL.setBounds(724, 370, 130, 23);

        jLabel57.setText("Aktivitas Kehidupan Sehari-hari ( ADL ) :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(440, 370, 280, 23);

        StatusPsiko.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tenang", "Takut", "Cemas", "Depresi", "Lain-Lain" }));
        StatusPsiko.setName("StatusPsiko"); // NOI18N
        StatusPsiko.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusPsikoActionPerformed(evt);
            }
        });
        StatusPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusPsikoKeyPressed(evt);
            }
        });
        FormInput.add(StatusPsiko);
        StatusPsiko.setBounds(134, 420, 110, 23);

        KetPsiko.setFocusTraversalPolicyProvider(true);
        KetPsiko.setName("KetPsiko"); // NOI18N
        KetPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPsikoKeyPressed(evt);
            }
        });
        FormInput.add(KetPsiko);
        KetPsiko.setBounds(248, 420, 606, 23);

        jLabel58.setText("Edukasi diberikan kepada :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 530, 171, 23);

        HubunganKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Tidak Baik" }));
        HubunganKeluarga.setName("HubunganKeluarga"); // NOI18N
        HubunganKeluarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HubunganKeluargaActionPerformed(evt);
            }
        });
        HubunganKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(HubunganKeluarga);
        HubunganKeluarga.setBounds(289, 470, 100, 23);

        jLabel59.setText("Status Sosial dan ekonomi :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 450, 176, 23);

        TinggalDengan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sendiri", "Orang Tua", "Suami / Istri", "Lainnya" }));
        TinggalDengan.setName("TinggalDengan"); // NOI18N
        TinggalDengan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TinggalDenganActionPerformed(evt);
            }
        });
        TinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalDenganKeyPressed(evt);
            }
        });
        FormInput.add(TinggalDengan);
        TinggalDengan.setBounds(497, 470, 110, 23);

        KetTinggal.setFocusTraversalPolicyProvider(true);
        KetTinggal.setName("KetTinggal"); // NOI18N
        KetTinggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetTinggalKeyPressed(evt);
            }
        });
        FormInput.add(KetTinggal);
        KetTinggal.setBounds(610, 470, 85, 23);

        jLabel60.setText("b. Tinggal dengan :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(393, 470, 100, 23);

        Ekonomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Cukup", "Kurang" }));
        Ekonomi.setName("Ekonomi"); // NOI18N
        Ekonomi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EkonomiActionPerformed(evt);
            }
        });
        Ekonomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkonomiKeyPressed(evt);
            }
        });
        FormInput.add(Ekonomi);
        Ekonomi.setBounds(770, 470, 84, 23);

        jLabel61.setText("c. Ekonomi :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(695, 470, 71, 23);

        Edukasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasien", "Keluarga" }));
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdukasiActionPerformed(evt);
            }
        });
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        FormInput.add(Edukasi);
        Edukasi.setBounds(175, 530, 101, 23);

        KetEdukasi.setFocusTraversalPolicyProvider(true);
        KetEdukasi.setName("KetEdukasi"); // NOI18N
        KetEdukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetEdukasiKeyPressed(evt);
            }
        });
        FormInput.add(KetEdukasi);
        KetEdukasi.setBounds(280, 530, 574, 23);

        jLabel64.setText("Jam dilaporkan :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(665, 660, 90, 23);

        jLabel65.setText("a. Tidak seimbang / sempoyongan / limbung :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(35, 600, 250, 23);

        jLabel66.setText("b. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain) :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(370, 600, 400, 23);

        Lapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Lapor.setName("Lapor"); // NOI18N
        Lapor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LaporActionPerformed(evt);
            }
        });
        Lapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaporKeyPressed(evt);
            }
        });
        FormInput.add(Lapor);
        Lapor.setBounds(563, 660, 80, 23);

        ATS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ATS.setName("ATS"); // NOI18N
        ATS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATSActionPerformed(evt);
            }
        });
        ATS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ATSKeyPressed(evt);
            }
        });
        FormInput.add(ATS);
        ATS.setBounds(289, 600, 80, 23);

        BJM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BJM.setName("BJM"); // NOI18N
        BJM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BJMKeyPressed(evt);
            }
        });
        FormInput.add(BJM);
        BJM.setBounds(774, 600, 80, 23);

        jLabel67.setText("Menyebar");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(700, 880, 50, 23);

        Hasil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak beresiko (tidak ditemukan a dan b)", "Resiko rendah (ditemukan a/b)", "Resiko tinggi (ditemukan a dan b)" }));
        Hasil.setName("Hasil"); // NOI18N
        Hasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HasilActionPerformed(evt);
            }
        });
        Hasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilKeyPressed(evt);
            }
        });
        FormInput.add(Hasil);
        Hasil.setBounds(76, 660, 280, 23);

        jLabel68.setText("Hasil :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 660, 72, 23);

        SG2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        SG2.setName("SG2"); // NOI18N
        SG2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SG2ActionPerformed(evt);
            }
        });
        SG2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG2KeyPressed(evt);
            }
        });
        FormInput.add(SG2);
        SG2.setBounds(520, 740, 160, 23);

        KetLapor.setFocusTraversalPolicyProvider(true);
        KetLapor.setName("KetLapor"); // NOI18N
        KetLapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLaporKeyPressed(evt);
            }
        });
        FormInput.add(KetLapor);
        KetLapor.setBounds(759, 660, 95, 23);

        jLabel69.setText("Nilai :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(695, 740, 75, 23);

        jLabel70.setText("Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja / benda lain sebagai penopang :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 630, 559, 23);

        SG1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Tidak Yakin", "Ya, 1-5 Kg", "Ya, 6-10Kg", "Ya, 11-15Kg", "Ya, >15Kg" }));
        SG1.setName("SG1"); // NOI18N
        SG1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SG1ActionPerformed(evt);
            }
        });
        SG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG1KeyPressed(evt);
            }
        });
        FormInput.add(SG1);
        SG1.setBounds(520, 710, 160, 23);

        jLabel72.setText("Cara Berjalan :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 580, 114, 23);

        Nilai1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "2", "1", "2", "3", "4" }));
        Nilai1.setName("Nilai1"); // NOI18N
        Nilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nilai1ActionPerformed(evt);
            }
        });
        Nilai1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nilai1KeyPressed(evt);
            }
        });
        FormInput.add(Nilai1);
        Nilai1.setBounds(774, 710, 80, 23);

        MSA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MSA.setName("MSA"); // NOI18N
        MSA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MSAKeyPressed(evt);
            }
        });
        FormInput.add(MSA);
        MSA.setBounds(563, 630, 80, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(695, 770, 75, 23);

        Nilai2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1" }));
        Nilai2.setName("Nilai2"); // NOI18N
        Nilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nilai2ActionPerformed(evt);
            }
        });
        Nilai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nilai2KeyPressed(evt);
            }
        });
        FormInput.add(Nilai2);
        Nilai2.setBounds(774, 740, 80, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1. Apakah ada penurunan berat badan yang tidak diinginkan selama 6 bulan terakhir ?");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(40, 710, 460, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Nyeri", "Nyeri Akut", "Nyeri Kronis" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NyeriActionPerformed(evt);
            }
        });
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(335, 820, 130, 23);

        jLabel79.setText("Rencana Keperawatan :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(20, 1100, 120, 23);

        Provokes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Proses Penyakit", "Benturan", "Lain-Lain" }));
        Provokes.setName("Provokes"); // NOI18N
        Provokes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProvokesActionPerformed(evt);
            }
        });
        Provokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProvokesKeyPressed(evt);
            }
        });
        FormInput.add(Provokes);
        Provokes.setBounds(534, 820, 130, 23);

        KetProvokes.setFocusTraversalPolicyProvider(true);
        KetProvokes.setName("KetProvokes"); // NOI18N
        KetProvokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProvokesKeyPressed(evt);
            }
        });
        FormInput.add(KetProvokes);
        KetProvokes.setBounds(668, 820, 186, 23);

        jLabel80.setText("Provokes :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(470, 820, 60, 23);

        Quality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seperti Tertusuk", "Berdenyut", "Teriris", "Tertindih", "Tertiban", "Lain-Lain" }));
        Quality.setName("Quality"); // NOI18N
        Quality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QualityActionPerformed(evt);
            }
        });
        Quality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QualityKeyPressed(evt);
            }
        });
        FormInput.add(Quality);
        Quality.setBounds(390, 850, 140, 23);

        KetQuality.setFocusTraversalPolicyProvider(true);
        KetQuality.setName("KetQuality"); // NOI18N
        KetQuality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetQualityKeyPressed(evt);
            }
        });
        FormInput.add(KetQuality);
        KetQuality.setBounds(530, 850, 130, 23);

        jLabel81.setText("Quality :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(330, 850, 60, 23);

        jLabel82.setText("Region :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(320, 880, 60, 23);

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LokasiActionPerformed(evt);
            }
        });
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(430, 880, 150, 23);

        jLabel83.setText("Lokasi");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(390, 880, 30, 23);

        Menyebar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Menyebar.setName("Menyebar"); // NOI18N
        Menyebar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenyebarActionPerformed(evt);
            }
        });
        Menyebar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyebarKeyPressed(evt);
            }
        });
        FormInput.add(Menyebar);
        Menyebar.setBounds(750, 880, 60, 23);

        jLabel84.setText("Menit");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(420, 910, 40, 23);

        jLabel85.setText("Skala Nyeri");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(740, 850, 60, 23);

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkalaNyeriActionPerformed(evt);
            }
        });
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(800, 850, 60, 23);

        jLabel86.setText("Diberitahukan Pada Dokter :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(570, 990, 150, 23);

        Durasi.setFocusTraversalPolicyProvider(true);
        Durasi.setName("Durasi"); // NOI18N
        Durasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DurasiActionPerformed(evt);
            }
        });
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(380, 910, 50, 23);

        jLabel87.setText("Durasi :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(340, 910, 40, 23);

        jLabel88.setText("Severity :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(680, 850, 60, 23);

        NyeriHilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Istirahat", "Medengar Musik", "Minum Obat" }));
        NyeriHilang.setName("NyeriHilang"); // NOI18N
        NyeriHilang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NyeriHilangActionPerformed(evt);
            }
        });
        NyeriHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriHilangKeyPressed(evt);
            }
        });
        FormInput.add(NyeriHilang);
        NyeriHilang.setBounds(130, 990, 130, 23);

        KetNyeri.setFocusTraversalPolicyProvider(true);
        KetNyeri.setName("KetNyeri"); // NOI18N
        KetNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNyeriKeyPressed(evt);
            }
        });
        FormInput.add(KetNyeri);
        KetNyeri.setBounds(270, 990, 260, 23);

        jLabel89.setText("Nyeri Hilang Bila :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(30, 990, 90, 23);

        PadaDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PadaDokter.setName("PadaDokter"); // NOI18N
        PadaDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PadaDokterActionPerformed(evt);
            }
        });
        PadaDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PadaDokterKeyPressed(evt);
            }
        });
        FormInput.add(PadaDokter);
        PadaDokter.setBounds(730, 990, 60, 23);

        KetDokter.setFocusTraversalPolicyProvider(true);
        KetDokter.setName("KetDokter"); // NOI18N
        KetDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDokterKeyPressed(evt);
            }
        });
        FormInput.add(KetDokter);
        KetDokter.setBounds(800, 990, 80, 23);

        MasalahKeperawatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Nyeri", "Pola Tidur", "Mobilitas / Aktivitas", "Integritas Kulit", "Infeksi", "Resiko Injury / Cedera", "Nutrisi", "Eliminasi", "Pengetahuan / Komunikasi", "Keseimbangan Cairan Dan Elektrolit", "Suhu Tubuh", "Perfusi Jaringan", "Konflik Peran", "Jalan Napas / Pertukaran Gas", "Perawatan Luka", "Lainnya" }));
        MasalahKeperawatan.setName("MasalahKeperawatan"); // NOI18N
        MasalahKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MasalahKeperawatanActionPerformed(evt);
            }
        });
        MasalahKeperawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MasalahKeperawatanKeyPressed(evt);
            }
        });
        FormInput.add(MasalahKeperawatan);
        MasalahKeperawatan.setBounds(150, 1070, 350, 23);

        jLabel91.setText("Masalah Keperawatan :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(20, 1070, 120, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        TRencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TRencana.setColumns(20);
        TRencana.setRows(5);
        TRencana.setName("TRencana"); // NOI18N
        TRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRencanaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(TRencana);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(150, 1100, 360, 130);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(695, 710, 75, 23);

        TotalHasil.setFocusTraversalPolicyProvider(true);
        TotalHasil.setName("TotalHasil"); // NOI18N
        TotalHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHasilKeyPressed(evt);
            }
        });
        FormInput.add(TotalHasil);
        TotalHasil.setBounds(774, 770, 80, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-02-2020 16:31:00" }));
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

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(700, 90, 90, 23);

        SUHU1.setFocusTraversalPolicyProvider(true);
        SUHU1.setName("SUHU1"); // NOI18N
        SUHU1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SUHU1KeyPressed(evt);
            }
        });
        FormInput.add(SUHU1);
        SUHU1.setBounds(794, 90, 60, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("II. STATUS NUTRISI");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(10, 120, 180, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("III. RIWAYAT KESEHATAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 170, 180, 23);

        jLabel51.setText("Cacat Fisik :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 370, 120, 23);

        Prothesa1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Prothesa1.setName("Prothesa1"); // NOI18N
        Prothesa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Prothesa1ActionPerformed(evt);
            }
        });
        Prothesa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Prothesa1KeyPressed(evt);
            }
        });
        FormInput.add(Prothesa1);
        Prothesa1.setBounds(124, 370, 90, 23);

        KetPro1.setFocusTraversalPolicyProvider(true);
        KetPro1.setName("KetPro1"); // NOI18N
        KetPro1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPro1KeyPressed(evt);
            }
        });
        FormInput.add(KetPro1);
        KetPro1.setBounds(218, 370, 220, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. FUNGSIONAL");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 320, 230, 23);

        jLabel62.setText("Status Psikologis :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 420, 130, 23);

        jLabel95.setText("Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(0, 500, 366, 23);

        StatusPsiko1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        StatusPsiko1.setName("StatusPsiko1"); // NOI18N
        StatusPsiko1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusPsiko1ActionPerformed(evt);
            }
        });
        StatusPsiko1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusPsiko1KeyPressed(evt);
            }
        });
        FormInput.add(StatusPsiko1);
        StatusPsiko1.setBounds(370, 500, 110, 23);

        KetPsiko1.setFocusTraversalPolicyProvider(true);
        KetPsiko1.setName("KetPsiko1"); // NOI18N
        KetPsiko1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPsiko1KeyPressed(evt);
            }
        });
        FormInput.add(KetPsiko1);
        KetPsiko1.setBounds(484, 500, 370, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("V. RIWAYAT PSIKO-SOSIAL, SPIRITUAL DAN BUDAYA");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(10, 400, 380, 23);

        jLabel97.setText("Dilaporkan kepada dokter ?");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(369, 660, 190, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("VI. PENILAIAN RESIKO JATUH");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(10, 560, 380, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 120, 880, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 170, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 320, 880, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 400, 880, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 560, 880, 1);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 690, 880, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 800, 880, 1);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("VII. SKRINING GIZI");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(10, 690, 380, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(40, 820, 290, 150);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            //Valid.pindah(evt,TCari,Kejadian);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(Informasi.getSelectedItem().toString().equals("")){
            Valid.textKosong(Informasi,"1");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD(mmHg)");
        }else if(NADI.getText().trim().equals("")){
            Valid.textKosong(NADI,"Nadi(x/menit)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(SUHU.getText().trim().equals("")){
            Valid.textKosong(SUHU,"Suhu(C)");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"BB(Kg)");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"TB(Cm)");
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI(Kg/m2)");
        }else if(TKeluhan.getText().trim().equals("")){
            Valid.textKosong(TKeluhan,"Keluhan Utama");
        }else if(TRPD.getText().trim().equals("")){
            Valid.textKosong(TRPD,"RPD");
        }else if(TRPK.getText().trim().equals("")){
            Valid.textKosong(TRPK,"RPK");
        }else if(TRPO.getText().trim().equals("")){
            Valid.textKosong(TRPO,"RPO");
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(AlatBantu.getSelectedItem().toString().equals("")){
            Valid.textKosong(AlatBantu,"2");
        }else if(KetBantu.getText().trim().equals("")){
            Valid.textKosong(KetBantu,"Ket. Alat Bantu");
        }else if(Prothesa.getSelectedItem().toString().equals("")){
            Valid.textKosong(Prothesa,"3");
        }else if(KetPro.getText().trim().equals("")){
            Valid.textKosong(KetPro,"Ket. Prothesa");
        }else if(ADL.getSelectedItem().toString().equals("")){
            Valid.textKosong(ADL,"4");
        }else if(StatusPsiko.getSelectedItem().toString().equals("")){
            Valid.textKosong(StatusPsiko,"5");
        }else if(KetPsiko.getText().trim().equals("")){
            Valid.textKosong(KetPsiko,"Ket. Psikologi");
        }else if(HubunganKeluarga.getSelectedItem().toString().equals("")){
            Valid.textKosong(HubunganKeluarga,"6");
        }else if(TinggalDengan.getSelectedItem().toString().equals("")){
            Valid.textKosong(TinggalDengan,"7");
        }else if(KetTinggal.getText().trim().equals("")){
            Valid.textKosong(KetTinggal,"Ket. Tinggal");
        }else if(Ekonomi.getSelectedItem().toString().equals("")){
            Valid.textKosong(Ekonomi,"8");
        }else if(Edukasi.getSelectedItem().toString().equals("")){
            Valid.textKosong(Edukasi,"9");
        }else if(KetEdukasi.getText().trim().equals("")){
            Valid.textKosong(KetEdukasi,"Ket. Edukasi");
        }else if(ATS.getSelectedItem().toString().equals("")){
            Valid.textKosong(ATS,"10");
        }else if(BJM.getSelectedItem().toString().equals("")){
            Valid.textKosong(BJM,"11");
        }else if(MSA.getSelectedItem().toString().equals("")){
            Valid.textKosong(MSA,"12");
        }else if(Hasil.getSelectedItem().toString().equals("")){
            Valid.textKosong(Hasil,"13");
        }else if(Lapor.getSelectedItem().toString().equals("")){
            Valid.textKosong(Lapor,"14");
        }else if(KetLapor.getText().trim().equals("")){
            Valid.textKosong(KetLapor,"Ket. Lapor");
        }else if(SG1.getSelectedItem().toString().equals("")){
            Valid.textKosong(SG1,"15");
        }else if(Nilai1.getSelectedItem().toString().equals("")){
            Valid.textKosong(Nilai1,"16");
        }else if(SG2.getSelectedItem().toString().equals("")){
            Valid.textKosong(SG2,"17");
        }else if(Nilai2.getSelectedItem().toString().equals("")){
            Valid.textKosong(Nilai2,"18");
        }else if(TotalHasil.getText().trim().equals("")){
            Valid.textKosong(TotalHasil,"Total Hasil");
        }else if(Nyeri.getSelectedItem().toString().equals("")){
            Valid.textKosong(Nyeri,"19");
        }else if(Provokes.getSelectedItem().toString().equals("")){
            Valid.textKosong(Provokes,"20");
        }else if(KetProvokes.getText().trim().equals("")){
            Valid.textKosong(KetProvokes,"Ket. Provokes");
        }else if(Quality.getSelectedItem().toString().equals("")){
            Valid.textKosong(Quality,"21");
        }else if(KetQuality.getText().trim().equals("")){
            Valid.textKosong(KetQuality,"Ket. Quality");
        }else if(Menyebar.getSelectedItem().toString().equals("")){
            Valid.textKosong(Menyebar,"22");
        }else if(SkalaNyeri.getSelectedItem().toString().equals("")){
            Valid.textKosong(SkalaNyeri,"23");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Region");
        }else if(Durasi.getText().trim().equals("")){
            Valid.textKosong(Durasi,"Durasi");
        }else if(NyeriHilang.getSelectedItem().toString().equals("")){
            Valid.textKosong(NyeriHilang,"24");
        }else if(KetNyeri.getText().trim().equals("")){
            Valid.textKosong(KetNyeri,"Ket. Hilang Nyeri");
        }else if(PadaDokter.getSelectedItem().toString().equals("")){
            Valid.textKosong(PadaDokter,"25");
        }else if(KetDokter.getText().trim().equals("")){
            Valid.textKosong(KetDokter,"Ket. Ke Dokter");
        }else if(MasalahKeperawatan.getSelectedItem().toString().equals("")){
            Valid.textKosong(MasalahKeperawatan,"26");
        }else if(TRencana.getText().trim().equals("")){
            Valid.textKosong(TRencana,"Ket. Masalah Keperawatan");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
            if(Sequel.menyimpantf("ass_perawat","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",55,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),TD.getText(),NADI.getText(),RR.getText(), 
                    SUHU.getText(),BB.getText(),TB.getText(),BMI.getText(),TKeluhan.getText(),TRPD.getText(),TRPK.getText(),TRPO.getText(),Alergi.getText(),AlatBantu.getSelectedItem().toString(),
                    KetBantu.getText(),Prothesa.getSelectedItem().toString(),KetPro.getText(),ADL.getSelectedItem().toString(),StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(), 
                    HubunganKeluarga.getSelectedItem().toString(),TinggalDengan.getSelectedItem().toString(),KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),Edukasi.getSelectedItem().toString(),
                    KetEdukasi.getText(),ATS.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),Hasil.getSelectedItem().toString(),Lapor.getSelectedItem().toString(),
                    KetLapor.getText(),SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),TotalHasil.getText(),
                    Nyeri.getSelectedItem().toString(),Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),
                    Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),
                    PadaDokter.getSelectedItem().toString(),KetDokter.getText(),MasalahKeperawatan.getSelectedItem().toString(),TRencana.getText(),KdPetugas.getText()
                })==true){
                    tampil();
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Obat2an,BtnBatal);
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
            if(Sequel.queryu2tf("delete from ass_perawat where no_rawat=? and tanggal=?",2,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
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
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(Informasi.getSelectedItem().toString().equals("")){
            Valid.textKosong(Informasi,"1");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD(mmHg)");
        }else if(NADI.getText().trim().equals("")){
            Valid.textKosong(NADI,"Nadi(x/menit)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(SUHU.getText().trim().equals("")){
            Valid.textKosong(SUHU,"Suhu(C)");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"BB(Kg)");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"TB(Cm)");
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI(Kg/m2)");
        }else if(TKeluhan.getText().trim().equals("")){
            Valid.textKosong(TKeluhan,"Keluhan Utama");
        }else if(TRPD.getText().trim().equals("")){
            Valid.textKosong(TRPD,"RPD");
        }else if(TRPK.getText().trim().equals("")){
            Valid.textKosong(TRPK,"RPK");
        }else if(TRPO.getText().trim().equals("")){
            Valid.textKosong(TRPO,"RPO");
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(AlatBantu.getSelectedItem().toString().equals("")){
            Valid.textKosong(AlatBantu,"2");
        }else if(KetBantu.getText().trim().equals("")){
            Valid.textKosong(KetBantu,"Ket. Alat Bantu");
        }else if(Prothesa.getSelectedItem().toString().equals("")){
            Valid.textKosong(Prothesa,"3");
        }else if(KetPro.getText().trim().equals("")){
            Valid.textKosong(KetPro,"Ket. Prothesa");
        }else if(ADL.getSelectedItem().toString().equals("")){
            Valid.textKosong(ADL,"4");
        }else if(StatusPsiko.getSelectedItem().toString().equals("")){
            Valid.textKosong(StatusPsiko,"5");
        }else if(KetPsiko.getText().trim().equals("")){
            Valid.textKosong(KetPsiko,"Ket. Psikologi");
        }else if(HubunganKeluarga.getSelectedItem().toString().equals("")){
            Valid.textKosong(HubunganKeluarga,"6");
        }else if(TinggalDengan.getSelectedItem().toString().equals("")){
            Valid.textKosong(TinggalDengan,"7");
        }else if(KetTinggal.getText().trim().equals("")){
            Valid.textKosong(KetTinggal,"Ket. Tinggal");
        }else if(Ekonomi.getSelectedItem().toString().equals("")){
            Valid.textKosong(Ekonomi,"8");
        }else if(Edukasi.getSelectedItem().toString().equals("")){
            Valid.textKosong(Edukasi,"9");
        }else if(KetEdukasi.getText().trim().equals("")){
            Valid.textKosong(KetEdukasi,"Ket. Edukasi");
        }else if(ATS.getSelectedItem().toString().equals("")){
            Valid.textKosong(ATS,"10");
        }else if(BJM.getSelectedItem().toString().equals("")){
            Valid.textKosong(BJM,"11");
        }else if(MSA.getSelectedItem().toString().equals("")){
            Valid.textKosong(MSA,"12");
        }else if(Hasil.getSelectedItem().toString().equals("")){
            Valid.textKosong(Hasil,"13");
        }else if(Lapor.getSelectedItem().toString().equals("")){
            Valid.textKosong(Lapor,"14");
        }else if(KetLapor.getText().trim().equals("")){
            Valid.textKosong(KetLapor,"Ket. Lapor");
        }else if(SG1.getSelectedItem().toString().equals("")){
            Valid.textKosong(SG1,"15");
        }else if(Nilai1.getSelectedItem().toString().equals("")){
            Valid.textKosong(Nilai1,"16");
        }else if(SG2.getSelectedItem().toString().equals("")){
            Valid.textKosong(SG2,"17");
        }else if(Nilai2.getSelectedItem().toString().equals("")){
            Valid.textKosong(Nilai2,"18");
        }else if(TotalHasil.getText().trim().equals("")){
            Valid.textKosong(TotalHasil,"Total Hasil");
        }else if(Nyeri.getSelectedItem().toString().equals("")){
            Valid.textKosong(Nyeri,"19");
        }else if(Provokes.getSelectedItem().toString().equals("")){
            Valid.textKosong(Provokes,"20");
        }else if(KetProvokes.getText().trim().equals("")){
            Valid.textKosong(KetProvokes,"Ket. Provokes");
        }else if(Quality.getSelectedItem().toString().equals("")){
            Valid.textKosong(Quality,"21");
        }else if(KetQuality.getText().trim().equals("")){
            Valid.textKosong(KetQuality,"Ket. Quality");
        }else if(Menyebar.getSelectedItem().toString().equals("")){
            Valid.textKosong(Menyebar,"22");
        }else if(SkalaNyeri.getSelectedItem().toString().equals("")){
            Valid.textKosong(SkalaNyeri,"23");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Region");
        }else if(Durasi.getText().trim().equals("")){
            Valid.textKosong(Durasi,"Durasi");
        }else if(NyeriHilang.getSelectedItem().toString().equals("")){
            Valid.textKosong(NyeriHilang,"24");
        }else if(KetNyeri.getText().trim().equals("")){
            Valid.textKosong(KetNyeri,"Ket. Hilang Nyeri");
        }else if(PadaDokter.getSelectedItem().toString().equals("")){
            Valid.textKosong(PadaDokter,"25");
        }else if(KetDokter.getText().trim().equals("")){
            Valid.textKosong(KetDokter,"Ket. Ke Dokter");
        }else if(MasalahKeperawatan.getSelectedItem().toString().equals("")){
            Valid.textKosong(MasalahKeperawatan,"26");
        }else if(TRencana.getText().trim().equals("")){
            Valid.textKosong(TRencana,"Ket. Masalah Keperawatan");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
               if(Sequel.mengedittf("ass_perawat","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,informasi=?,td=?,nadi=?,rr=?,suhu=?,bb=?,tb=?,bmi=?,keluhan_utama=?,rpd=?,rpk=?,rpo=?,alergi=?,alat_bantu=?,ket_bantu=?,prothesa=?,ket_pro=?,adl=?,status_psiko=?,ket_psiko=?,hub_keluarga=?,tinggal_dengan=?,ket_tinggal=?,ekonomi=?,edukasi=?,ket_edukasi=?,berjalan_a=?,berjalan_b=?,berjalan_c=?,hasil=?,lapor=?,ket_lapor=?,sg1=?,nilai1=?,sg2=?,nilai2=?,total_hasil=?,nyeri=?,provokes=?,ket_provokes=?,quality=?,ket_quality=?,lokasi=?,menyebar=?,skala_nyeri=?,durasi=?,nyeri_hilang=?,ket_nyeri=?,pada_dokter=?,ket_dokter=?,masalah_keperawatan=?,rencana=?,nip=?",57,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),TD.getText(),NADI.getText(),RR.getText(), 
                    SUHU.getText(),BB.getText(),TB.getText(),BMI.getText(),TKeluhan.getText(),TRPD.getText(),TRPK.getText(),TRPO.getText(),Alergi.getText(),AlatBantu.getSelectedItem().toString(),
                    KetBantu.getText(),Prothesa.getSelectedItem().toString(),KetPro.getText(),ADL.getSelectedItem().toString(),StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(), 
                    HubunganKeluarga.getSelectedItem().toString(),TinggalDengan.getSelectedItem().toString(),KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),Edukasi.getSelectedItem().toString(),
                    KetEdukasi.getText(),ATS.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),Hasil.getSelectedItem().toString(),Lapor.getSelectedItem().toString(),
                    KetLapor.getText(),SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),TotalHasil.getText(),
                    Nyeri.getSelectedItem().toString(),Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),
                    Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),
                    PadaDokter.getSelectedItem().toString(),KetDokter.getText(),MasalahKeperawatan.getSelectedItem().toString(),TRencana.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
                    })==true){
                        tampil();
                        emptTeks();
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
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                if(TCari.getText().equals("")){
                    Valid.MyReportqry("rptDataAsuhanGiziPasien.jasper","report","::[ Data Asesmen Keperawatan ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,ass_perawat.tanggal,"+
                        "ass_perawat.informasi,ass_perawat.td,ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,"+
                        "ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,ass_perawat.bmi,ass_perawat.keluhan_utama,"+
                        "ass_perawat.rpd,ass_perawat.rpk,ass_perawat.rpo,ass_perawat.alergi,ass_perawat.alat_bantu,ass_perawat.ket_bantu,ass_perawat.prothesa,"+
                        "ass_perawat.ket_pro,ass_perawat.adl,ass_perawat.status_psiko,ass_perawat.ket_psiko,ass_perawat.hub_keluarga,ass_perawat.tinggal_dengan,"+
                        "ass_perawat.ket_tinggal,ass_perawat.ekonomi,ass_perawat.edukasi,ass_perawat.ket_edukasi,ass_perawat.berjalan_a,ass_perawat.berjalan_b,"+
                        "ass_perawat.berjalan_c,ass_perawat.hasil,ass_perawat.lapor,ass_perawat.ket_lapor,ass_perawat.sg1,ass_perawat.nilai1,ass_perawat.sg2,ass_perawat.nilai2,"+
                        "ass_perawat.total_hasil,ass_perawat.nyeri,ass_perawat.provokes,ass_perawat.ket_provokes,ass_perawat.quality,ass_perawat.ket_quality,ass_perawat.lokasi,ass_perawat.menyebar,"+
                        "ass_perawat.skala_nyeri,ass_perawat.durasi,ass_perawat.nyeri_hilang,ass_perawat.ket_nyeri,ass_perawat.pada_dokter,ass_perawat.ket_dokter,ass_perawat.masalah_keperawatan,ass_perawat.rencana,"+
                        "ass_perawat.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join ass_perawat on reg_periksa.no_rawat=ass_perawat.no_rawat "+
                        "inner join petugas on ass_perawat.nip=petugas.nip where "+
                        "ass_perawat.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by ass_perawat.tanggal",param);
                }else{
                    Valid.MyReportqry("rptDataAsuhanGiziPasien.jasper","report","::[ Data Asesmen Keperawatan ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,ass_perawat.tanggal,"+
                        "ass_perawat.informasi,ass_perawat.td,ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,"+
                        "ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,ass_perawat.bmi,ass_perawat.keluhan_utama,"+
                        "ass_perawat.rpd,ass_perawat.rpk,ass_perawat.rpo,ass_perawat.alergi,ass_perawat.alat_bantu,ass_perawat.ket_bantu,ass_perawat.prothesa,"+
                        "ass_perawat.ket_pro,ass_perawat.adl,ass_perawat.status_psiko,ass_perawat.ket_psiko,ass_perawat.hub_keluarga,ass_perawat.tinggal_dengan,"+
                        "ass_perawat.ket_tinggal,ass_perawat.ekonomi,ass_perawat.edukasi,ass_perawat.ket_edukasi,ass_perawat.berjalan_a,ass_perawat.berjalan_b,"+
                        "ass_perawat.berjalan_c,ass_perawat.hasil,ass_perawat.lapor,ass_perawat.ket_lapor,ass_perawat.sg1,ass_perawat.nilai1,ass_perawat.sg2,ass_perawat.nilai2,"+
                        "ass_perawat.total_hasil,ass_perawat.nyeri,ass_perawat.provokes,ass_perawat.ket_provokes,ass_perawat.quality,ass_perawat.ket_quality,ass_perawat.lokasi,ass_perawat.menyebar,"+
                        "ass_perawat.skala_nyeri,ass_perawat.durasi,ass_perawat.nyeri_hilang,ass_perawat.ket_nyeri,ass_perawat.pada_dokter,ass_perawat.ket_dokter,ass_perawat.masalah_keperawatan,ass_perawat.rencana,"+
                        "ass_perawat.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join ass_perawat on reg_periksa.no_rawat=ass_perawat.no_rawat "+
                        "inner join petugas on ass_perawat.nip=petugas.nip where "+
                        "ass_perawat.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                        "ass_perawat.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        "ass_perawat.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "ass_perawat.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and ass_perawat.nip like '%"+TCari.getText().trim()+"%' or "+
                        //"asuhan_gizi.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and asuhan_gizi.diagnosis like '%"+TCari.getText().trim()+"%' or "+
                        "ass_perawat.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and petugas.nama like '%"+TCari.getText().trim()+"%' order by ass_perawat.tanggal",param);
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
                    ChkInput.setSelected(true);
                    isForm(); 
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void MnAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAsuhanGiziActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            //param.put("diagnosa",DiagnosaMasukRanap.getText());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptCetakAsuhanGizi.jasper","report","::[ Laporan Asesmen Keperawatan ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,ass_perawat.tanggal,"+
                        "ass_perawat.informasi,ass_perawat.td,ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,"+
                        "ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,ass_perawat.bmi,ass_perawat.keluhan_utama,"+
                        "ass_perawat.rpd,ass_perawat.rpk,ass_perawat.rpo,ass_perawat.alergi,ass_perawat.alat_bantu,ass_perawat.ket_bantu,ass_perawat.prothesa,"+
                        "ass_perawat.ket_pro,ass_perawat.adl,ass_perawat.status_psiko,ass_perawat.ket_psiko,ass_perawat.hub_keluarga,ass_perawat.tinggal_dengan,"+
                        "ass_perawat.ket_tinggal,ass_perawat.ekonomi,ass_perawat.edukasi,ass_perawat.ket_edukasi,ass_perawat.berjalan_a,ass_perawat.berjalan_b,"+
                        "ass_perawat.berjalan_c,ass_perawat.hasil,ass_perawat.lapor,ass_perawat.ket_lapor,ass_perawat.sg1,ass_perawat.nilai1,ass_perawat.sg2,ass_perawat.nilai2,"+
                        "ass_perawat.total_hasil,ass_perawat.nyeri,ass_perawat.provokes,ass_perawat.ket_provokes,ass_perawat.quality,ass_perawat.ket_quality,ass_perawat.lokasi,ass_perawat.menyebar,"+
                        "ass_perawat.skala_nyeri,ass_perawat.durasi,ass_perawat.nyeri_hilang,ass_perawat.ket_nyeri,ass_perawat.pada_dokter,ass_perawat.ket_dokter,ass_perawat.masalah_keperawatan,ass_perawat.rencana,"+
                        "ass_perawat.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join ass_perawat on reg_periksa.no_rawat=ass_perawat.no_rawat "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "inner join petugas on ass_perawat.nip=petugas.nip where ass_perawat.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnAsuhanGiziActionPerformed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TglAsuhan,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,NADI);
    }//GEN-LAST:event_TBKeyPressed

    private void NADIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NADIKeyPressed
        Valid.pindah(evt,TB,SUHU);
    }//GEN-LAST:event_NADIKeyPressed

    private void SUHUKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SUHUKeyPressed
        Valid.pindah(evt,NADI,TD);
    }//GEN-LAST:event_SUHUKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,SUHU,RR);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,TD,BMI);
    }//GEN-LAST:event_RRKeyPressed

    private void BMIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BMIKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BMIKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlergiKeyPressed

    private void InformasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InformasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InformasiActionPerformed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InformasiKeyPressed

    private void TDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TDActionPerformed

    private void TKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKeluhanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKeluhanKeyPressed

    private void TRPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRPDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRPDKeyPressed

    private void TRPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRPKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRPKKeyPressed

    private void TRPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRPOKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRPOKeyPressed

    private void AlatBantuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlatBantuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlatBantuActionPerformed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlatBantuKeyPressed

    private void KetBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBantuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetBantuKeyPressed

    private void ProthesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProthesaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProthesaActionPerformed

    private void ProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProthesaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProthesaKeyPressed

    private void KetProKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetProKeyPressed

    private void ADLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ADLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ADLActionPerformed

    private void ADLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ADLKeyPressed

    private void StatusPsikoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusPsikoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusPsikoActionPerformed

    private void StatusPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusPsikoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusPsikoKeyPressed

    private void KetPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPsikoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetPsikoKeyPressed

    private void HubunganKeluargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HubunganKeluargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HubunganKeluargaActionPerformed

    private void HubunganKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeluargaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HubunganKeluargaKeyPressed

    private void TinggalDenganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TinggalDenganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TinggalDenganActionPerformed

    private void TinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalDenganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TinggalDenganKeyPressed

    private void KetTinggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetTinggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetTinggalKeyPressed

    private void EkonomiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EkonomiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EkonomiActionPerformed

    private void EkonomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkonomiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EkonomiKeyPressed

    private void EdukasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdukasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdukasiActionPerformed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdukasiKeyPressed

    private void KetEdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetEdukasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetEdukasiKeyPressed

    private void LaporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LaporActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LaporActionPerformed

    private void LaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaporKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LaporKeyPressed

    private void ATSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATSActionPerformed

    private void ATSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ATSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ATSKeyPressed

    private void BJMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BJMKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BJMKeyPressed

    private void HasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HasilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HasilActionPerformed

    private void HasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HasilKeyPressed

    private void SG2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SG2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG2ActionPerformed

    private void SG2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG2KeyPressed

    private void KetLaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLaporKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetLaporKeyPressed

    private void SG1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SG1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG1ActionPerformed

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG1KeyPressed

    private void Nilai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nilai1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nilai1ActionPerformed

    private void Nilai1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nilai1KeyPressed

    private void MSAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MSAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MSAKeyPressed

    private void Nilai2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nilai2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nilai2ActionPerformed

    private void Nilai2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nilai2KeyPressed

    private void NyeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NyeriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NyeriActionPerformed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NyeriKeyPressed

    private void ProvokesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProvokesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProvokesActionPerformed

    private void ProvokesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProvokesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProvokesKeyPressed

    private void KetProvokesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProvokesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetProvokesKeyPressed

    private void QualityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QualityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_QualityActionPerformed

    private void QualityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QualityKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_QualityKeyPressed

    private void KetQualityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetQualityKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetQualityKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LokasiKeyPressed

    private void MenyebarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenyebarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenyebarActionPerformed

    private void MenyebarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenyebarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenyebarKeyPressed

    private void SkalaNyeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkalaNyeriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNyeriActionPerformed

    private void SkalaNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaNyeriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaNyeriKeyPressed

    private void DurasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DurasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DurasiKeyPressed

    private void DurasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DurasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DurasiActionPerformed

    private void NyeriHilangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NyeriHilangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NyeriHilangActionPerformed

    private void NyeriHilangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriHilangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NyeriHilangKeyPressed

    private void KetNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNyeriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetNyeriKeyPressed

    private void LokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LokasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LokasiActionPerformed

    private void PadaDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PadaDokterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PadaDokterActionPerformed

    private void PadaDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PadaDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PadaDokterKeyPressed

    private void KetDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetDokterKeyPressed

    private void MasalahKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MasalahKeperawatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MasalahKeperawatanActionPerformed

    private void MasalahKeperawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MasalahKeperawatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MasalahKeperawatanKeyPressed

    private void TRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRencanaKeyPressed

    private void TotalHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHasilKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void SUHU1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SUHU1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SUHU1KeyPressed

    private void Prothesa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Prothesa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prothesa1ActionPerformed

    private void Prothesa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Prothesa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prothesa1KeyPressed

    private void KetPro1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPro1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetPro1KeyPressed

    private void StatusPsiko1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusPsiko1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusPsiko1ActionPerformed

    private void StatusPsiko1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusPsiko1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusPsiko1KeyPressed

    private void KetPsiko1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPsiko1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetPsiko1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanRalan dialog = new RMPenilaianAwalKeperawatanRalan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox ATS;
    private widget.ComboBox AlatBantu;
    private widget.TextBox Alergi;
    private widget.TextBox BB;
    private widget.ComboBox BJM;
    private widget.TextBox BMI;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Durasi;
    private widget.ComboBox Edukasi;
    private widget.ComboBox Ekonomi;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Hasil;
    private widget.ComboBox HubunganKeluarga;
    private widget.ComboBox Informasi;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox KetBantu;
    private widget.TextBox KetDokter;
    private widget.TextBox KetEdukasi;
    private widget.TextBox KetLapor;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetPro;
    private widget.TextBox KetPro1;
    private widget.TextBox KetProvokes;
    private widget.TextBox KetPsiko;
    private widget.TextBox KetPsiko1;
    private widget.TextBox KetQuality;
    private widget.TextBox KetTinggal;
    private widget.Label LCount;
    private widget.ComboBox Lapor;
    private widget.TextBox Lokasi;
    private widget.ComboBox MSA;
    private widget.ComboBox MasalahKeperawatan;
    private widget.ComboBox Menyebar;
    private javax.swing.JMenuItem MnAsuhanGizi;
    private widget.TextBox NADI;
    private widget.ComboBox Nilai1;
    private widget.ComboBox Nilai2;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Nyeri;
    private widget.ComboBox NyeriHilang;
    private widget.ComboBox PadaDokter;
    private javax.swing.JPanel PanelInput;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.ComboBox Prothesa;
    private widget.ComboBox Prothesa1;
    private widget.ComboBox Provokes;
    private widget.ComboBox Quality;
    private widget.TextBox RR;
    private widget.ComboBox SG1;
    private widget.ComboBox SG2;
    private widget.TextBox SUHU;
    private widget.TextBox SUHU1;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SkalaNyeri;
    private widget.ComboBox StatusPsiko;
    private widget.ComboBox StatusPsiko1;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextArea TKeluhan;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextArea TRPD;
    private widget.TextArea TRPK;
    private widget.TextArea TRPO;
    private widget.TextArea TRencana;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TinggalDengan;
    private widget.TextBox TotalHasil;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
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
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel43;
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
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
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
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            /*"No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Tgl.Asuhan","Informasi","TD(mmHg)","Nadi(x/menit)","RR(x/menit)","Suhu(C)",
            "BB(Kg)","TB(Cm)","BMI(Kg/m2)","Keluhan Utama","RPD","RPK","RPO","Alergi","Alat Bantu","Ket. Alat Bantu",
            "Prothesa","Ket. Prothesa","ADL","Stts Psikologi","Ket. Psikologi","Hubungan Pasien","Tinggal Dengan","Ket. Tinggal","Ekonomi",
            "Edukasi","Ket. Edukasi","Cara Berjalan A","Cara Berjalan B","Cara Berjalan C","Hasil","Lapor Dokter","Ket. Lapor","Skrining Gizi 1","Nilai 1",
            "Skrining Gizi 2","Nilai 2","Total Hasil","Tingkat Nyeri","Provokes","Ket. Provokes","Quality","Ket. Quality","Region","Menyebar",
            "Skala Nyeri","Durasi","Nyeri Hilang","Ket. Hilang Nyeri","Ke Dokter","Ket. Ke Dokter","Masalah Keperawatan","Ket. Masalah Keperawatan","NIP","Nama Petugas"*/
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,ass_perawat.tanggal,"+
                        "ass_perawat.informasi,ass_perawat.td,ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,"+
                        "ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,ass_perawat.bmi,ass_perawat.keluhan_utama,"+
                        "ass_perawat.rpd,ass_perawat.rpk,ass_perawat.rpo,ass_perawat.alergi,ass_perawat.alat_bantu,ass_perawat.ket_bantu,ass_perawat.prothesa,"+
                        "ass_perawat.ket_pro,ass_perawat.adl,ass_perawat.status_psiko,ass_perawat.ket_psiko,ass_perawat.hub_keluarga,ass_perawat.tinggal_dengan,"+
                        "ass_perawat.ket_tinggal,ass_perawat.ekonomi,ass_perawat.edukasi,ass_perawat.ket_edukasi,ass_perawat.berjalan_a,ass_perawat.berjalan_b,"+
                        "ass_perawat.berjalan_c,ass_perawat.hasil,ass_perawat.lapor,ass_perawat.ket_lapor,ass_perawat.sg1,ass_perawat.nilai1,ass_perawat.sg2,ass_perawat.nilai2,"+
                        "ass_perawat.total_hasil,ass_perawat.nyeri,ass_perawat.provokes,ass_perawat.ket_provokes,ass_perawat.quality,ass_perawat.ket_quality,ass_perawat.lokasi,ass_perawat.menyebar,"+
                        "ass_perawat.skala_nyeri,ass_perawat.durasi,ass_perawat.nyeri_hilang,ass_perawat.ket_nyeri,ass_perawat.pada_dokter,ass_perawat.ket_dokter,ass_perawat.masalah_keperawatan,ass_perawat.rencana,"+
                        "ass_perawat.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join ass_perawat on reg_periksa.no_rawat=ass_perawat.no_rawat "+
                        "inner join petugas on ass_perawat.nip=petugas.nip where "+
                        "ass_perawat.tanggal between ? and ? order by ass_perawat.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,ass_perawat.tanggal,"+
                        "ass_perawat.informasi,ass_perawat.td,ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,"+
                        "ass_perawat.nadi,ass_perawat.rr,ass_perawat.suhu,ass_perawat.bb,ass_perawat.tb,ass_perawat.bmi,ass_perawat.keluhan_utama,"+
                        "ass_perawat.rpd,ass_perawat.rpk,ass_perawat.rpo,ass_perawat.alergi,ass_perawat.alat_bantu,ass_perawat.ket_bantu,ass_perawat.prothesa,"+
                        "ass_perawat.ket_pro,ass_perawat.adl,ass_perawat.status_psiko,ass_perawat.ket_psiko,ass_perawat.hub_keluarga,ass_perawat.tinggal_dengan,"+
                        "ass_perawat.ket_tinggal,ass_perawat.ekonomi,ass_perawat.edukasi,ass_perawat.ket_edukasi,ass_perawat.berjalan_a,ass_perawat.berjalan_b,"+
                        "ass_perawat.berjalan_c,ass_perawat.hasil,ass_perawat.lapor,ass_perawat.ket_lapor,ass_perawat.sg1,ass_perawat.nilai1,ass_perawat.sg2,ass_perawat.nilai2,"+
                        "ass_perawat.total_hasil,ass_perawat.nyeri,ass_perawat.provokes,ass_perawat.ket_provokes,ass_perawat.quality,ass_perawat.ket_quality,ass_perawat.lokasi,ass_perawat.menyebar,"+
                        "ass_perawat.skala_nyeri,ass_perawat.durasi,ass_perawat.nyeri_hilang,ass_perawat.ket_nyeri,ass_perawat.pada_dokter,ass_perawat.ket_dokter,ass_perawat.masalah_keperawatan,ass_perawat.rencana,"+
                        "ass_perawat.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join ass_perawat on reg_periksa.no_rawat=ass_perawat.no_rawat "+
                        "inner join petugas on ass_perawat.nip=petugas.nip where "+
                        "ass_perawat.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                        "ass_perawat.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                        "ass_perawat.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                        "ass_perawat.tanggal between ? and ? and ass_perawat.nip like ? or "+
                        //"ass_perawat.tanggal between ? and ? and ass_perawat.diagnosis like ? or "+
                        "ass_perawat.tanggal between ? and ? and petugas.nama like ? order by ass_perawat.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(15,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),
                        rs.getString("tanggal"),rs.getString("informasi"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),
                        rs.getString("bb"),rs.getString("tb"),rs.getString("bmi"),rs.getString("keluhan_utama"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),
                        rs.getString("alergi"),rs.getString("alat_bantu"),rs.getString("ket_bantu"),rs.getString("prothesa"),rs.getString("ket_pro"),
                        rs.getString("adl"),rs.getString("status_psiko"),rs.getString("ket_psiko"),rs.getString("hub_keluarga"),rs.getString("tinggal_dengan"),
                        rs.getString("ket_tinggal"),rs.getString("ekonomi"),rs.getString("edukasi"),rs.getString("ket_edukasi"),rs.getString("berjalan_a"),rs.getString("berjalan_b"),
                        rs.getString("berjalan_c"),rs.getString("hasil"),rs.getString("lapor"),rs.getString("ket_lapor"),rs.getString("sg1"),rs.getString("nilai1"),rs.getString("sg2"),
                        rs.getString("nilai2"),rs.getString("total_hasil"),rs.getString("nyeri"),rs.getString("provokes"),rs.getString("ket_provokes"),rs.getString("quality"),rs.getString("ket_quality"),
                        rs.getString("lokasi"),rs.getString("menyebar"),rs.getString("skala_nyeri"),rs.getString("durasi"),rs.getString("nyeri_hilang"),rs.getString("ket_nyeri"),rs.getString("pada_dokter"),
                        rs.getString("ket_dokter"),rs.getString("masalah_keperawatan"),rs.getString("rencana"),rs.getString("nip"),rs.getString("nama")
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
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        TglAsuhan.setDate(new Date());
        Informasi.requestFocus();
        TD.setText("");
        NADI.setText("");
        RR.setText("");
        SUHU.setText("");
        BB.setText("");
        TB.setText("");
        BMI.setText("");
        TKeluhan.setText("");
        TRPD.setText("");
        TRPK.setText("");
        TRPO.setText("");
        Alergi.setText("");
        AlatBantu.requestFocus();
        KetBantu.setText("");
        Prothesa.requestFocus();
        KetPro.setText("");
        ADL.requestFocus();
        StatusPsiko.requestFocus();
        KetPsiko.setText("");
        HubunganKeluarga.requestFocus();
        TinggalDengan.requestFocus();
        KetTinggal.setText("");
        Ekonomi.requestFocus();
        Edukasi.requestFocus();
        KetEdukasi.setText("");
        ATS.requestFocus();
        BJM.requestFocus();
        MSA.requestFocus();
        Hasil.requestFocus();
        Lapor.requestFocus();
        KetLapor.setText("");
        SG1.requestFocus();
        Nilai1.requestFocus();
        SG2.requestFocus();
        Nilai2.requestFocus(); 
        TotalHasil.setText("");
        Nyeri.requestFocus();
        Provokes.requestFocus();
        KetProvokes.setText("");
        Quality.requestFocus();
        KetQuality.setText("");
        Lokasi.setText("");
        Menyebar.requestFocus();
        SkalaNyeri.requestFocus();
        Durasi.setText("");
        NyeriHilang.requestFocus();
        KetNyeri.setText("");
        PadaDokter.requestFocus();
        KetDokter.setText("");
        MasalahKeperawatan.requestFocus();
        TRencana.setText("");
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());  
            isRawat();
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NADI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            SUHU.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            BMI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            TKeluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            TRPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TRPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TRPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            AlatBantu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KetBantu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Prothesa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KetPro.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            ADL.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            StatusPsiko.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KetPsiko.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            HubunganKeluarga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            TinggalDengan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            KetTinggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Ekonomi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Edukasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            KetEdukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            ATS.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            BJM.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            MSA.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Hasil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Lapor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            KetLapor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            SG1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Nilai1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            SG2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Nilai2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString()); 
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Provokes.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            KetProvokes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Quality.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            KetQuality.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Menyebar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            SkalaNyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Durasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            NyeriHilang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            KetNyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            PadaDokter.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            KetDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            MasalahKeperawatan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            TRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            
            Valid.SetTgl(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
         //Sequel.cariIsi("select diagnosa_awal from kamar_inap where diagnosa_awal<>'' and no_rawat=? ",DiagnosaMasukRanap,TNoRw.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        Sequel.cariIsi("select if(jk='L','Laki-Laki','Perempuan') from pasien where no_rkm_medis=? ",Jk,TNoRM.getText());
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-122));
            scrollInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            scrollInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        BtnPrint.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());  
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas,KdPetugas.getText());
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }
    
}

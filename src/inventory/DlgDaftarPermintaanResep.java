package inventory;
import bridging.BPJSDataSEP;
import fungsi.BackgroundMusic;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import permintaan.DlgPermintaanPelayananInformasiObat;
import rekammedis.RMKonselingFarmasi;
import rekammedis.RMRiwayatPerawatan;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgInputResepPulang;

public class DlgDaftarPermintaanResep extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3,tabMode4,tabMode5,tabMode6,tabMode7,tabMode8;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private String bangsal="",aktifkanparsial="no",kamar="",alarm="",DEPOAKTIFOBAT="",rincianobat="",
            formalarm="",nol_detik,detik,NoResep="",TglPeresepan="",JamPeresepan="",finger="",
            NoRawat="",NoRM="",Pasien="",DokterPeresep="",Status="",KodeDokter="",Ruang="",KodeRuang="";
    private int jmlparsial=0,nilai_detik,resepbaru=0,i=0;
    private BackgroundMusic music;
    private boolean aktif=false,semua,ceksukses=false;
    
    /** Creates new form 
     * @param parent
     * @param modal */
    public DlgDaftarPermintaanResep(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Resep","Tgl.Peresepan","Jam Peresepan","No.Rawat","No.RM","Pasien","Dokter Peresep",
                "Status","Kode Dokter","Poli/Unit","Kode Poli","Jenis Bayar","Tgl.Validasi","Jam Validasi",
                "Tgl.Penyerahan","Jam Penyerahan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbResepRalan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResepRalan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResepRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i <16; i++) {
            TableColumn column = tbResepRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(190);
            }else if(i==6){
                column.setPreferredWidth(190);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(140);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(120);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(85);
            }else if(i==15){
                column.setPreferredWidth(90);
            }
        }
        tbResepRalan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Resep","Tgl.Resep","Poli/Unit","Status","Pasien","Dokter Peresep"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailResepRalan.setModel(tabMode2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDetailResepRalan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailResepRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 6; i++) {
            TableColumn column = tbDetailResepRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(350);
            }else if(i==5){
                column.setPreferredWidth(190);
            }
        }
        tbDetailResepRalan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode3=new DefaultTableModel(null,new Object[]{
                "No.Resep","Tgl.Peresepan","Jam Peresepan","No.Rawat","No.RM","Pasien","Dokter Peresep",
                "Status","Kode Dokter","Ruang/Kamar","Kode Bangsal","Jenis Bayar","Tgl.Validasi","Jam Validasi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbResepRanap.setModel(tabMode3);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResepRanap.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResepRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i <14; i++) {
            TableColumn column = tbResepRanap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(190);
            }else if(i==6){
                column.setPreferredWidth(190);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(140);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(120);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(70);
            }
        }
        tbResepRanap.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode4=new DefaultTableModel(null,new Object[]{
                "No.Resep","Tgl.Resep","Ruang/Kamar","Status","Pasien","Dokter Peresep"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailResepRanap.setModel(tabMode4);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDetailResepRanap.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailResepRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 6; i++) {
            TableColumn column = tbDetailResepRanap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(350);
            }else if(i==5){
                column.setPreferredWidth(190);
            }
        }
        tbDetailResepRanap.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode5=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","Tanggal","Jam","No.Rawat","No.RM","Pasien","Dokter Yang Meminta",
                "Status","Kode Dokter","Ruang/Kamar","Kode Bangsal","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPermintaanStok.setModel(tabMode5);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPermintaanStok.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanStok.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i <12; i++) {
            TableColumn column = tbPermintaanStok.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(95);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(190);
            }else if(i==6){
                column.setPreferredWidth(190);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(140);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(120);
            }
        }
        tbPermintaanStok.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode6=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","Tanggal","Ruang/Kamar","Status","Pasien","Dokter Yang Meminta"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailPermintaanStok.setModel(tabMode6);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDetailPermintaanStok.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailPermintaanStok.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 6; i++) {
            TableColumn column = tbDetailPermintaanStok.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(95);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(350);
            }else if(i==5){
                column.setPreferredWidth(1030);
            }
        }
        tbDetailPermintaanStok.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode7=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","Tanggal","Jam","No.Rawat","No.RM","Pasien","Dokter Yang Meminta",
                "Status","Kode Dokter","Ruang/Kamar","Kode Bangsal","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPermintaanResepPulang.setModel(tabMode7);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPermintaanResepPulang.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanResepPulang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i <12; i++) {
            TableColumn column = tbPermintaanResepPulang.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(95);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(190);
            }else if(i==6){
                column.setPreferredWidth(190);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(140);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(120);
            }
        }
        tbPermintaanResepPulang.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode8=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","Tanggal","Ruang/Kamar","Status","Pasien","Dokter Yang Meminta"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailPermintaanResepPulang.setModel(tabMode8);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDetailPermintaanResepPulang.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailPermintaanResepPulang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 6; i++) {
            TableColumn column = tbDetailPermintaanResepPulang.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(95);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(350);
            }else if(i==5){
                column.setPreferredWidth(270);
            }
        }
        tbDetailPermintaanResepPulang.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        pilihTab();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        pilihTab();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        pilihTab();
                    }
                }
            });
        }
        
        try {
            aktifkanparsial=koneksiDB.AKTIFKANBILLINGPARSIAL();
            alarm=koneksiDB.ALARMAPOTEK();
            formalarm=koneksiDB.FORMALARMAPOTEK();
        } catch (Exception ex) {
            aktifkanparsial="no";
            alarm="no";
            formalarm="ralan + ranap";
        }
        
        if(alarm.equals("yes")){
            jam();
        }
        
        try {
            DEPOAKTIFOBAT = koneksiDB.DEPOAKTIFOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            DEPOAKTIFOBAT = "";
        }
        
        ChkAccor.setSelected(false);
        isMenu();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi2 = new widget.panelisi();
        jLabel20 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnTambah = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPenyerahan = new widget.Button();
        BtnPrint = new widget.Button();
        BtnRekap = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        TabPilihRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        TabRawatJalan = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbResepRalan = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbDetailResepRalan = new widget.Table();
        panelGlass8 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        TabRawatInap = new javax.swing.JTabbedPane();
        scrollPane3 = new widget.ScrollPane();
        tbResepRanap = new widget.Table();
        scrollPane4 = new widget.ScrollPane();
        tbDetailResepRanap = new widget.Table();
        scrollPane5 = new widget.ScrollPane();
        tbPermintaanStok = new widget.Table();
        scrollPane6 = new widget.ScrollPane();
        tbDetailPermintaanStok = new widget.Table();
        scrollPane7 = new widget.ScrollPane();
        tbPermintaanResepPulang = new widget.Table();
        scrollPane8 = new widget.ScrollPane();
        tbDetailPermintaanResepPulang = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        CrDokter2 = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        jLabel17 = new widget.Label();
        Kamar = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        BtnRiwayat = new widget.Button();
        BtnPemberianObat = new widget.Button();
        BtnPiutangObat = new widget.Button();
        BtnTelaahResep = new widget.Button();
        BtnResepAwal = new widget.Button();
        BtnResepLuar = new widget.Button();
        BtnKonselingFarmasi = new widget.Button();
        BtnInformasiObat = new widget.Button();
        BtnSEPBPJS = new widget.Button();
        BtnObat23HariBPJS = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Peresepan Obat Oleh Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel20.setText("Tgl.Peresepan :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi2.add(jLabel20);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-09-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi2.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(24, 23));
        panelisi2.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-09-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi2.add(DTPCari2);

        jLabel12.setText("Status :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Belum Terlayani", "Sudah Terlayani" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(130, 23));
        panelisi2.add(cmbStatus);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi2.add(BtnCari);

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
        panelisi2.add(BtnAll);

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('S');
        BtnTambah.setText("Validasi");
        BtnTambah.setToolTipText("Alt+S");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        BtnTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTambahKeyPressed(evt);
            }
        });
        panelisi1.add(BtnTambah);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('U');
        BtnEdit.setText("Ubah");
        BtnEdit.setToolTipText("Alt+U");
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
        panelisi1.add(BtnEdit);

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
        panelisi1.add(BtnHapus);

        BtnPenyerahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnPenyerahan.setMnemonic('H');
        BtnPenyerahan.setText("Penyerahan");
        BtnPenyerahan.setToolTipText("Alt+H");
        BtnPenyerahan.setName("BtnPenyerahan"); // NOI18N
        BtnPenyerahan.setPreferredSize(new java.awt.Dimension(115, 30));
        BtnPenyerahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenyerahanActionPerformed(evt);
            }
        });
        BtnPenyerahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPenyerahanKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPenyerahan);

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
        panelisi1.add(BtnPrint);

        BtnRekap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/preview-16x16.png"))); // NOI18N
        BtnRekap.setMnemonic('R');
        BtnRekap.setText("Rekap");
        BtnRekap.setToolTipText("Alt+R");
        BtnRekap.setName("BtnRekap"); // NOI18N
        BtnRekap.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRekap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRekapActionPerformed(evt);
            }
        });
        BtnRekap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRekapKeyPressed(evt);
            }
        });
        panelisi1.add(BtnRekap);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(43, 23));
        panelisi1.add(LCount);

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
        panelisi1.add(BtnKeluar);

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabPilihRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabPilihRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabPilihRawat.setName("TabPilihRawat"); // NOI18N
        TabPilihRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPilihRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout());

        TabRawatJalan.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatJalan.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatJalan.setName("TabRawatJalan"); // NOI18N
        TabRawatJalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatJalanMouseClicked(evt);
            }
        });

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbResepRalan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbResepRalan.setName("tbResepRalan"); // NOI18N
        tbResepRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepRalanMouseClicked(evt);
            }
        });
        tbResepRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepRalanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbResepRalan);

        TabRawatJalan.addTab("Resep Rawat Jalan", scrollPane1);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbDetailResepRalan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailResepRalan.setName("tbDetailResepRalan"); // NOI18N
        scrollPane2.setViewportView(tbDetailResepRalan);

        TabRawatJalan.addTab("Detail Rawat Jalan", scrollPane2);

        internalFrame2.add(TabRawatJalan, java.awt.BorderLayout.CENTER);

        panelGlass8.setBorder(null);
        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass8.add(jLabel14);

        CrDokter.setEditable(false);
        CrDokter.setName("CrDokter"); // NOI18N
        CrDokter.setPreferredSize(new java.awt.Dimension(295, 23));
        panelGlass8.add(CrDokter);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('6');
        BtnSeek3.setToolTipText("ALt+6");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnSeek3);

        jLabel16.setText("Unit :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(295, 23));
        panelGlass8.add(CrPoli);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnSeek4);

        internalFrame2.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.addTab("Rawat Jalan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        TabRawatInap.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatInap.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatInap.setName("TabRawatInap"); // NOI18N
        TabRawatInap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatInapMouseClicked(evt);
            }
        });

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbResepRanap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbResepRanap.setName("tbResepRanap"); // NOI18N
        tbResepRanap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepRanapMouseClicked(evt);
            }
        });
        tbResepRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepRanapKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(tbResepRanap);

        TabRawatInap.addTab("Resep Rawat Inap", scrollPane3);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbDetailResepRanap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailResepRanap.setName("tbDetailResepRanap"); // NOI18N
        scrollPane4.setViewportView(tbDetailResepRanap);

        TabRawatInap.addTab("Detail Rawat Inap", scrollPane4);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane5.setName("scrollPane5"); // NOI18N
        scrollPane5.setOpaque(true);

        tbPermintaanStok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPermintaanStok.setName("tbPermintaanStok"); // NOI18N
        tbPermintaanStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPermintaanStokMouseClicked(evt);
            }
        });
        tbPermintaanStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPermintaanStokKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(tbPermintaanStok);

        TabRawatInap.addTab("Permintaan Stok Pasien", scrollPane5);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane6.setName("scrollPane6"); // NOI18N
        scrollPane6.setOpaque(true);

        tbDetailPermintaanStok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailPermintaanStok.setName("tbDetailPermintaanStok"); // NOI18N
        scrollPane6.setViewportView(tbDetailPermintaanStok);

        TabRawatInap.addTab("Detail Stok Pasien", scrollPane6);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane7.setName("scrollPane7"); // NOI18N
        scrollPane7.setOpaque(true);

        tbPermintaanResepPulang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPermintaanResepPulang.setName("tbPermintaanResepPulang"); // NOI18N
        tbPermintaanResepPulang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPermintaanResepPulangMouseClicked(evt);
            }
        });
        tbPermintaanResepPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPermintaanResepPulangKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(tbPermintaanResepPulang);

        TabRawatInap.addTab("Permintaan Resep Pulang", scrollPane7);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane8.setName("scrollPane8"); // NOI18N
        scrollPane8.setOpaque(true);

        tbDetailPermintaanResepPulang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailPermintaanResepPulang.setName("tbDetailPermintaanResepPulang"); // NOI18N
        scrollPane8.setViewportView(tbDetailPermintaanResepPulang);

        TabRawatInap.addTab("Detail Resep Pulang", scrollPane8);

        internalFrame3.add(TabRawatInap, java.awt.BorderLayout.CENTER);

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel15);

        CrDokter2.setEditable(false);
        CrDokter2.setName("CrDokter2"); // NOI18N
        CrDokter2.setPreferredSize(new java.awt.Dimension(295, 23));
        panelGlass9.add(CrDokter2);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnSeek5);

        jLabel17.setText("Ruang/Kamar :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel17);

        Kamar.setEditable(false);
        Kamar.setName("Kamar"); // NOI18N
        Kamar.setPreferredSize(new java.awt.Dimension(295, 23));
        panelGlass9.add(Kamar);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('5');
        BtnSeek6.setToolTipText("ALt+5");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnSeek6);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.addTab("Rawat Inap", internalFrame3);

        internalFrame1.add(TabPilihRawat, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(155, 43));
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
        FormMenu.setPreferredSize(new java.awt.Dimension(137, 230));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayat.setText("Riwayat Pasien");
        BtnRiwayat.setFocusPainted(false);
        BtnRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayat.setName("BtnRiwayat"); // NOI18N
        BtnRiwayat.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnRiwayat.setRoundRect(false);
        BtnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRiwayat);

        BtnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemberianObat.setText("Obat Tervalidasi");
        BtnPemberianObat.setFocusPainted(false);
        BtnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemberianObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemberianObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemberianObat.setName("BtnPemberianObat"); // NOI18N
        BtnPemberianObat.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnPemberianObat.setRoundRect(false);
        BtnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemberianObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPemberianObat);

        BtnPiutangObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPiutangObat.setText("Piutang Obat");
        BtnPiutangObat.setFocusPainted(false);
        BtnPiutangObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPiutangObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPiutangObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPiutangObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPiutangObat.setName("BtnPiutangObat"); // NOI18N
        BtnPiutangObat.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnPiutangObat.setRoundRect(false);
        BtnPiutangObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPiutangObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPiutangObat);

        BtnTelaahResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTelaahResep.setText("Telaah Resep");
        BtnTelaahResep.setFocusPainted(false);
        BtnTelaahResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTelaahResep.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTelaahResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTelaahResep.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTelaahResep.setName("BtnTelaahResep"); // NOI18N
        BtnTelaahResep.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnTelaahResep.setRoundRect(false);
        BtnTelaahResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTelaahResepActionPerformed(evt);
            }
        });
        FormMenu.add(BtnTelaahResep);

        BtnResepAwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepAwal.setText("Cetak Resep Awal");
        BtnResepAwal.setFocusPainted(false);
        BtnResepAwal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepAwal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepAwal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepAwal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepAwal.setName("BtnResepAwal"); // NOI18N
        BtnResepAwal.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnResepAwal.setRoundRect(false);
        BtnResepAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepAwalActionPerformed(evt);
            }
        });
        FormMenu.add(BtnResepAwal);

        BtnResepLuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepLuar.setText("Resep Luar");
        BtnResepLuar.setFocusPainted(false);
        BtnResepLuar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepLuar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepLuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepLuar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepLuar.setName("BtnResepLuar"); // NOI18N
        BtnResepLuar.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnResepLuar.setRoundRect(false);
        BtnResepLuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepLuarActionPerformed(evt);
            }
        });
        FormMenu.add(BtnResepLuar);

        BtnKonselingFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKonselingFarmasi.setText("Konseling Farmasi");
        BtnKonselingFarmasi.setFocusPainted(false);
        BtnKonselingFarmasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKonselingFarmasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKonselingFarmasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKonselingFarmasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKonselingFarmasi.setName("BtnKonselingFarmasi"); // NOI18N
        BtnKonselingFarmasi.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnKonselingFarmasi.setRoundRect(false);
        BtnKonselingFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKonselingFarmasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKonselingFarmasi);

        BtnInformasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInformasiObat.setText("Informasi Obat");
        BtnInformasiObat.setFocusPainted(false);
        BtnInformasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInformasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInformasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInformasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInformasiObat.setName("BtnInformasiObat"); // NOI18N
        BtnInformasiObat.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnInformasiObat.setRoundRect(false);
        BtnInformasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInformasiObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnInformasiObat);

        BtnSEPBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSEPBPJS.setText("Data SEP BPJS");
        BtnSEPBPJS.setFocusPainted(false);
        BtnSEPBPJS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSEPBPJS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSEPBPJS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSEPBPJS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSEPBPJS.setName("BtnSEPBPJS"); // NOI18N
        BtnSEPBPJS.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnSEPBPJS.setRoundRect(false);
        BtnSEPBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSEPBPJSActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSEPBPJS);

        BtnObat23HariBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnObat23HariBPJS.setText("Obat 23 Hari BPJS");
        BtnObat23HariBPJS.setFocusPainted(false);
        BtnObat23HariBPJS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnObat23HariBPJS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat23HariBPJS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnObat23HariBPJS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnObat23HariBPJS.setName("BtnObat23HariBPJS"); // NOI18N
        BtnObat23HariBPJS.setPreferredSize(new java.awt.Dimension(135, 23));
        BtnObat23HariBPJS.setRoundRect(false);
        BtnObat23HariBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat23HariBPJSActionPerformed(evt);
            }
        });
        FormMenu.add(BtnObat23HariBPJS);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.WEST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            pilihTab();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbResepRalan.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        pilihTab();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            pilihTab();
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbResepRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepRalanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
                if(akses.getberi_obat()==true){
                    BtnTambahActionPerformed(null);
                }
            }
        }
}//GEN-LAST:event_tbResepRalanMouseClicked

    private void tbResepRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepRalanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
                if(akses.getberi_obat()==true){
                    BtnTambahActionPerformed(null);
                }                    
            }
        }
}//GEN-LAST:event_tbResepRalanKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){            
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                   // BtnBatal.requestFocus();
                }else if(tabMode.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    
                    for(i=0;i<tabMode.getRowCount();i++){  
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,2).toString(),
                            tabMode.getValueAt(i,3).toString(),tabMode.getValueAt(i,4).toString(),tabMode.getValueAt(i,5).toString(),
                            tabMode.getValueAt(i,6).toString(),tabMode.getValueAt(i,7).toString(),tabMode.getValueAt(i,8).toString(),
                            tabMode.getValueAt(i,9).toString(),tabMode.getValueAt(i,10).toString(),tabMode.getValueAt(i,11).toString(),
                            "","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                    }
                    
                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDaftarPermintaanResep.jasper","report","::[ Laporan Daftar Permintaan Resep ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }   
            }else if(TabRawatJalan.getSelectedIndex()==1){
                if(tabMode2.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode2.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    
                    for(int i=0;i<tabMode2.getRowCount();i++){  
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,tabMode2.getValueAt(i,0).toString(),tabMode2.getValueAt(i,1).toString(),tabMode2.getValueAt(i,2).toString(),
                            tabMode2.getValueAt(i,3).toString(),tabMode2.getValueAt(i,4).toString(),tabMode2.getValueAt(i,5).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                    }
                    
                    Map<String, Object> param = new HashMap<>();  
                        param.put("namars",akses.getnamars());
                        param.put("alamatrs",akses.getalamatrs());
                        param.put("kotars",akses.getkabupatenrs());
                        param.put("propinsirs",akses.getpropinsirs());
                        param.put("kontakrs",akses.getkontakrs());
                        param.put("emailrs",akses.getemailrs());   
                        param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDaftarResep.jasper","report","::[ Daftar Resep Obat ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){            
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                   // BtnBatal.requestFocus();
                }else if(tabMode3.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    
                    for(int i=0;i<tabMode3.getRowCount();i++){  
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,tabMode3.getValueAt(i,0).toString(),tabMode3.getValueAt(i,1).toString(),tabMode3.getValueAt(i,2).toString(),
                            tabMode3.getValueAt(i,3).toString(),tabMode3.getValueAt(i,4).toString(),tabMode3.getValueAt(i,5).toString(),
                            tabMode3.getValueAt(i,6).toString(),tabMode3.getValueAt(i,7).toString(),tabMode3.getValueAt(i,8).toString(),
                            tabMode3.getValueAt(i,9).toString(),"",tabMode3.getValueAt(i,11).toString(),
                            "","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                    }
                    
                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDaftarPermintaanResep2.jasper","report","::[ Laporan Daftar Permintaan Resep ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }   
            }else if(TabRawatInap.getSelectedIndex()==1){
                if(tabMode4.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode4.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    
                    for(int i=0;i<tabMode4.getRowCount();i++){  
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,tabMode4.getValueAt(i,0).toString(),tabMode4.getValueAt(i,1).toString(),tabMode4.getValueAt(i,2).toString(),
                            tabMode4.getValueAt(i,3).toString(),tabMode4.getValueAt(i,4).toString(),tabMode4.getValueAt(i,5).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                    }
                    
                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDaftarResep2.jasper","report","::[ Daftar Resep Obat ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(tabMode5.getRowCount()==0){            
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                   // BtnBatal.requestFocus();
                }else if(tabMode5.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    
                    for(int i=0;i<tabMode5.getRowCount();i++){  
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,tabMode5.getValueAt(i,0).toString(),tabMode5.getValueAt(i,1).toString(),tabMode5.getValueAt(i,2).toString(),
                            tabMode5.getValueAt(i,3).toString(),tabMode5.getValueAt(i,4).toString(),tabMode5.getValueAt(i,5).toString(),
                            tabMode5.getValueAt(i,6).toString(),tabMode5.getValueAt(i,7).toString(),tabMode5.getValueAt(i,8).toString(),
                            tabMode5.getValueAt(i,9).toString(),"",tabMode5.getValueAt(i,11).toString(),
                            "","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                    }
                    
                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDaftarPermintaanResep3.jasper","report","::[ Laporan Daftar Permintaan Stok Pasien ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }   
            }else if(TabRawatInap.getSelectedIndex()==3){
                if(tabMode6.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode6.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    
                    for(int i=0;i<tabMode6.getRowCount();i++){  
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,tabMode6.getValueAt(i,0).toString(),tabMode6.getValueAt(i,1).toString(),tabMode6.getValueAt(i,2).toString(),
                            tabMode6.getValueAt(i,3).toString(),tabMode6.getValueAt(i,4).toString(),tabMode6.getValueAt(i,5).toString().replaceAll("✓","v").replaceAll("✕","x"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                    }
                    
                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDaftarResep3.jasper","report","::[ Daftar Permintaan Stok Obat Pasien ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatInap.getSelectedIndex()==4){
                if(tabMode7.getRowCount()==0){            
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                   // BtnBatal.requestFocus();
                }else if(tabMode7.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    
                    for(int i=0;i<tabMode7.getRowCount();i++){  
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,tabMode7.getValueAt(i,0).toString(),tabMode7.getValueAt(i,1).toString(),tabMode7.getValueAt(i,2).toString(),
                            tabMode7.getValueAt(i,3).toString(),tabMode7.getValueAt(i,4).toString(),tabMode7.getValueAt(i,5).toString(),
                            tabMode7.getValueAt(i,6).toString(),tabMode7.getValueAt(i,7).toString(),tabMode7.getValueAt(i,8).toString(),
                            tabMode7.getValueAt(i,9).toString(),"",tabMode7.getValueAt(i,11).toString(),
                            "","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                    }
                    
                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDaftarPermintaanResepPulang.jasper","report","::[ Laporan Daftar Permintaan Resep Pulang ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }   
            }else if(TabRawatInap.getSelectedIndex()==5){
                if(tabMode8.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode8.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    
                    for(int i=0;i<tabMode8.getRowCount();i++){  
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,tabMode8.getValueAt(i,0).toString(),tabMode8.getValueAt(i,1).toString(),tabMode8.getValueAt(i,2).toString(),
                            tabMode8.getValueAt(i,3).toString(),tabMode8.getValueAt(i,4).toString(),tabMode8.getValueAt(i,5).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                    }
                    
                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptDaftarResepPulang.jasper","report","::[ Daftar Permintaan Resep Pulang ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }        
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt,BtnEdit,BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            CrDokter.setText("");
            CrPoli.setText("");
            TCari.setText("");
            pilihRalan();
        }else if(TabPilihRawat.getSelectedIndex()==1){
            CrDokter2.setText("");
            Kamar.setText("");
            TCari.setText("");
            pilihRanap();
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(akses.getberi_obat()==true){
                    if(tabMode.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            JOptionPane.showMessageDialog(rootPane,"Resep sudah tervalidasi ..!!");
                        }else {
                            jmlparsial=0;
                            if(aktifkanparsial.equals("yes")){
                                jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",NoRawat));
                            }
                            if(jmlparsial>0){
                                Sequel.queryu("delete from antriapotek2");
                                Sequel.queryu("insert into antriapotek2 values('"+NoResep+"','1','"+NoRawat+"')");
                                panggilform();
                            }else{
                                if(Sequel.cariRegistrasi(NoRawat)>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                                }else{ 
                                    Sequel.queryu("delete from antriapotek2");
                                    Sequel.queryu("insert into antriapotek2 values('"+NoResep+"','1','"+NoRawat+"')");
                                    panggilform();                             
                                }
                            }               
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk mengvalidasi...!!!!");
                    TCari.requestFocus();
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(akses.getberi_obat()==true){
                    if(tabMode3.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            JOptionPane.showMessageDialog(rootPane,"Resep sudah tervalidasi ..!!");
                        }else {                           
                            if(Sequel.cariRegistrasi(NoRawat)>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                            }else{ 
                                panggilform2();                             
                            }                
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk mengvalidasi...!!!!");
                    TCari.requestFocus();
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(akses.getstok_obat_pasien()==true){
                    if(tabMode5.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data permintaan stok pasien yang mau divalidasi..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            JOptionPane.showMessageDialog(rootPane,"Permintaan Stok Pasien sudah tervalidasi ..!!");
                        }else {                           
                            if(Sequel.cariRegistrasi(NoRawat)>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                            }else{ 
                                panggilform3();                             
                            }                
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk mengvalidasi...!!!!");
                    TCari.requestFocus();
                }
            } else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Stok...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                if(akses.getresep_pulang()==true){
                    if(tabMode7.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data permintaan resep pulang yang mau divalidasi..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            JOptionPane.showMessageDialog(rootPane,"Permintaan Resep Pulang sudah tervalidasi ..!!");
                        }else {                           
                            if(Sequel.cariRegistrasi(NoRawat)>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                            }else{ 
                                panggilform4();                             
                            }                
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk mengvalidasi...!!!!");
                    TCari.requestFocus();
                }
            } else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Resep Pulang...!!!!");
                TCari.requestFocus();
            }      
        }            
}//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnTambahActionPerformed(null);
        }else{
           Valid.pindah(evt,BtnEdit,BtnKeluar);
        }
}//GEN-LAST:event_BtnTambahKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void TabRawatJalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatJalanMouseClicked
        pilihRalan();
    }//GEN-LAST:event_TabRawatJalanMouseClicked

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter yang mau diubah..!!");
                }else{
                    if(Status.equals("Sudah Terlayani")){
                        JOptionPane.showMessageDialog(rootPane,"Resep sudah tervalidasi ..!!");
                    }else {
                        DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                        resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        resep.setLocationRelativeTo(internalFrame1);
                        resep.MatikanJam();
                        resep.setNoRm(NoRawat,Valid.SetTgl2(TglPeresepan),JamPeresepan.substring(0,2),JamPeresepan.substring(3,5),JamPeresepan.substring(6,8),KodeDokter,DokterPeresep,"ralan");
                        resep.isCek();
                        resep.tampilobat3(NoResep);
                        TeksKosong();
                        resep.setVisible(true);                         
                    }                    
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                }else{
                    if(Status.equals("Sudah Terlayani")){
                        JOptionPane.showMessageDialog(rootPane,"Resep sudah tervalidasi ..!!");
                    }else {
                        kamar=Sequel.cariIsi("select kd_bangsal from kamar inner join kamar_inap on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                "where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1 ",NoRawat); 
                        bangsal=Sequel.cariIsi("select set_depo_ranap.kd_depo from set_depo_ranap where set_depo_ranap.kd_bangsal=?",kamar);
                        if(bangsal.equals("")){
                            if(Sequel.cariIsi("select set_lokasi.asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                                akses.setkdbangsal(kamar);
                            }else{
                                akses.setkdbangsal(Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi"));
                            }
                        }else{
                            akses.setkdbangsal(bangsal);
                        }
                        DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                        resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        resep.setLocationRelativeTo(internalFrame1);
                        resep.MatikanJam();
                        resep.setNoRm(NoRawat,Valid.SetTgl2(TglPeresepan),JamPeresepan.substring(0,2),JamPeresepan.substring(3,5),JamPeresepan.substring(6,8),KodeDokter,DokterPeresep,"ranap");
                        resep.isCek();
                        resep.tampilobat3(NoResep);
                        TeksKosong();
                        resep.setVisible(true);                          
                    }                    
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(tabMode5.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data permintaan stok yang mau divalidasi..!!");
                }else{
                    if(Status.equals("Sudah Terlayani")){
                        JOptionPane.showMessageDialog(rootPane,"Permintaan stok sudah tervalidasi ..!!");
                    }else {
                        kamar=Sequel.cariIsi("select kd_bangsal from kamar inner join kamar_inap on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                "where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1 ",NoRawat); 
                        bangsal=Sequel.cariIsi("select set_depo_ranap.kd_depo from set_depo_ranap where set_depo_ranap.kd_bangsal=?",kamar);
                        if(bangsal.equals("")){
                            if(Sequel.cariIsi("select set_lokasi.asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                                akses.setkdbangsal(kamar);
                            }else{
                                akses.setkdbangsal(Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi"));
                            }
                        }else{
                            akses.setkdbangsal(bangsal);
                        }
                        DlgPermintaanStokPasien permintaan=new DlgPermintaanStokPasien(null,false);
                        permintaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        permintaan.setLocationRelativeTo(internalFrame1);
                        permintaan.isCek();
                        permintaan.setNoRm(NoRawat,DTPCari1.getDate()); 
                        permintaan.tampilObat2(NoResep);
                        permintaan.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                        TeksKosong();
                        permintaan.setVisible(true);                          
                    }                    
                }
            }else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Stok...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                if(tabMode7.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data permintaan stok yang mau divalidasi..!!");
                }else{
                    if(Status.equals("Sudah Terlayani")){
                        JOptionPane.showMessageDialog(rootPane,"Permintaan stok sudah tervalidasi ..!!");
                    }else {
                        kamar=Sequel.cariIsi("select kd_bangsal from kamar inner join kamar_inap on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                "where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1 ",NoRawat); 
                        bangsal=Sequel.cariIsi("select set_depo_ranap.kd_depo from set_depo_ranap where set_depo_ranap.kd_bangsal=?",kamar);
                        if(bangsal.equals("")){
                            if(Sequel.cariIsi("select set_lokasi.asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                                akses.setkdbangsal(kamar);
                            }else{
                                akses.setkdbangsal(Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi"));
                            }
                        }else{
                            akses.setkdbangsal(bangsal);
                        }
                        DlgPermintaanResepPulang permintaan=new DlgPermintaanResepPulang(null,false);
                        permintaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        permintaan.setLocationRelativeTo(internalFrame1);
                        permintaan.isCek();
                        permintaan.setNoRm(NoRawat,DTPCari1.getDate()); 
                        permintaan.tampilObat2(NoResep);
                        permintaan.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                        TeksKosong();
                        permintaan.setVisible(true);                          
                    }                    
                }
            }else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Resep...!!!!");
                TCari.requestFocus();
            }  
        }
            
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnTambah, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnRekapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRekapActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgResepObat resep=new DlgResepObat(null,false);
        resep.tampil2();
        resep.emptTeks();
        resep.isCek();
        resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        resep.setLocationRelativeTo(internalFrame1);
        resep.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnRekapActionPerformed

    private void BtnRekapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRekapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRekapKeyPressed

    private void TabPilihRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPilihRawatMouseClicked
        pilihTab();
    }//GEN-LAST:event_TabPilihRawatMouseClicked

    private void tbResepRanapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepRanapMouseClicked
        if(tabMode3.getRowCount()!=0){
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
                if(akses.getberi_obat()==true){
                    BtnTambahActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbResepRanapMouseClicked

    private void tbResepRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepRanapKeyPressed
        if(tabMode3.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
                if(akses.getberi_obat()==true){
                    BtnTambahActionPerformed(null);
                }                    
            }
        }
    }//GEN-LAST:event_tbResepRanapKeyPressed

    private void TabRawatInapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatInapMouseClicked
        pilihRanap();
    }//GEN-LAST:event_TabRawatInapMouseClicked

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        DlgCariDokter dokter=new DlgCariDokter(null,false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){ 
                    if(TabPilihRawat.getSelectedIndex()==0){
                        CrDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter.requestFocus();
                    }else if(TabPilihRawat.getSelectedIndex()==1){
                        CrDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter2.requestFocus();
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
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        DlgCariPoli poli=new DlgCariPoli(null,false);
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){   
                    CrPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    CrPoli.requestFocus();
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
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        DlgCariDokter dokter=new DlgCariDokter(null,false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){ 
                    if(TabPilihRawat.getSelectedIndex()==0){
                        CrDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter.requestFocus();
                    }else if(TabPilihRawat.getSelectedIndex()==1){
                        CrDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter2.requestFocus();
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
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        DlgCariBangsal ruang=new DlgCariBangsal(null,false);
        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruang.getTable().getSelectedRow()!= -1){   
                    Kamar.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),1).toString());  
                    Kamar.requestFocus();
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
        ruang.isCek();
        ruang.emptTeks();        
        ruang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(akses.getresep_dokter()==true){
                    if(tabMode.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter yang mau dihapus..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            JOptionPane.showMessageDialog(rootPane,"Resep sudah tervalidasi ..!!");
                        }else {
                            if(Sequel.meghapustf("resep_obat","no_resep",NoResep)==true){
                                TeksKosong();
                                if(tbResepRalan.getSelectedRow()!= -1){
                                    tabMode.removeRow(tbResepRalan.getSelectedRow());
                                }
                            }   
                        }                    
                    }
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(akses.getresep_dokter()==true){
                    if(tabMode3.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter yang mau dihapus..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            JOptionPane.showMessageDialog(rootPane,"Resep sudah tervalidasi ..!!");
                        }else {
                            if(Sequel.meghapustf("resep_obat","no_resep",NoResep)==true){
                                TeksKosong();
                                if(tbResepRanap.getSelectedRow()!= -1){
                                    tabMode3.removeRow(tbResepRanap.getSelectedRow());
                                }
                            } 
                        }                    
                    }
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(akses.getpermintaan_stok_obat_pasien()==true){
                    if(tabMode5.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data permintaan stok yang mau dihapus..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            JOptionPane.showMessageDialog(rootPane,"permintaan stok sudah tervalidasi ..!!");
                        }else {
                            if(Sequel.meghapustf("permintaan_stok_obat_pasien","no_permintaan",NoResep)==true){
                                TeksKosong();
                                if(tbPermintaanStok.getSelectedRow()!= -1){
                                    tabMode5.removeRow(tbPermintaanStok.getSelectedRow());
                                }
                            }
                        }                    
                    }
                }
            }else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Stok...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                if(akses.getpermintaan_resep_pulang()==true){
                    if(tabMode7.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data permintaan stok yang mau dihapus..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            JOptionPane.showMessageDialog(rootPane,"permintaan resep pulang sudah tervalidasi ..!!");
                        }else {
                            if(Sequel.meghapustf("permintaan_resep_pulang","no_permintaan",NoResep)==true){
                                TeksKosong();
                                if(tbPermintaanResepPulang.getSelectedRow()!= -1){
                                    tabMode7.removeRow(tbPermintaanResepPulang.getSelectedRow());
                                }
                            }
                        }                    
                    }
                }
            }else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Resep Pulag...!!!!");
                TCari.requestFocus();
            }   
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        aktif=true;
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        aktif=false;
    }//GEN-LAST:event_formWindowDeactivated

    private void tbPermintaanStokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPermintaanStokMouseClicked
        if(tabMode5.getRowCount()!=0){
            try {
                getData3();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
                if(akses.getstok_obat_pasien()==true){
                    BtnTambahActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbPermintaanStokMouseClicked

    private void tbPermintaanStokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPermintaanStokKeyPressed
        if(tabMode5.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData3();
                } catch (java.lang.NullPointerException e) {
                }
                if(akses.getstok_obat_pasien()==true){
                    BtnTambahActionPerformed(null);
                }                    
            }
        }
    }//GEN-LAST:event_tbPermintaanStokKeyPressed

    private void BtnPenyerahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenyerahanActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(akses.getberi_obat()==true){
                    if(tabMode.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter yang mau diserahkan..!!");
                    }else{
                        Sequel.queryu("delete from antriapotek3");
                        Sequel.queryu("insert into antriapotek3 values('"+NoResep+"','1','"+NoRawat+"')");
                        Sequel.queryu("delete from bukti_penyerahan_resep_obat where no_resep='"+NoResep+"'");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk mengvalidasi...!!!!");
                    TCari.requestFocus();
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Maaf, Penyerahan resep hanya untuk rawat jalan..!!!!");
            TCari.requestFocus();
        }
    }//GEN-LAST:event_BtnPenyerahanActionPerformed

    private void BtnPenyerahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPenyerahanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPenyerahanActionPerformed(null);
        }else{
           Valid.pindah(evt,BtnHapus,BtnPrint);
        }
    }//GEN-LAST:event_BtnPenyerahanKeyPressed

    private void tbPermintaanResepPulangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPermintaanResepPulangMouseClicked
        if(tabMode7.getRowCount()!=0){
            try {
                getData4();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
                if(akses.getresep_pulang()==true){
                    BtnTambahActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbPermintaanResepPulangMouseClicked

    private void tbPermintaanResepPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPermintaanResepPulangKeyPressed
        if(tabMode7.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData4();
                } catch (java.lang.NullPointerException e) {
                }
                if(akses.getresep_pulang()==true){
                    BtnTambahActionPerformed(null);
                }                    
            }
        }
    }//GEN-LAST:event_tbPermintaanResepPulangKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemberianObatActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(akses.getberi_obat()==true){
                    if(tabMode.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter yang sudah divalidasi..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgPemberianObat form=new DlgPemberianObat(null,false);
                            form.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                            form.setLocationRelativeTo(internalFrame1);
                            form.isCek();
                            form.setNoRm3(NoRawat,DTPCari1.getDate(),DTPCari2.getDate(),"ralan");
                            form.tampilPO3();
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang sudah divalidasi..!!");
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk melihat data yang sudah divalidasi...!!!!");
                    TCari.requestFocus();
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(akses.getberi_obat()==true){
                    if(tabMode3.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter yang sudah divalidasi..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgPemberianObat form=new DlgPemberianObat(null,false);
                            form.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                            form.setLocationRelativeTo(internalFrame1);
                            form.isCek();
                            form.setNoRm2(NoRawat,DTPCari1.getDate(),DTPCari2.getDate(),"ranap");
                            form.tampilPO3();
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang sudah divalidasi..!!");
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk melihat data yang sudah divalidasi...!!!!");
                    TCari.requestFocus();
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(akses.getstok_obat_pasien()==true){
                    if(tabMode5.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data permintaan stok pasien yang sudah divalidasi..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgStokPasien opname=new DlgStokPasien(null,false);
                            opname.isCek();
                            opname.setNoRm2(NoRawat,DTPCari1.getDate(),DTPCari2.getDate());
                            opname.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            opname.setLocationRelativeTo(internalFrame1);
                            opname.setAlwaysOnTop(false);
                            opname.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang sudah divalidasi..!!");
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk melihat data yang sudah divalidasi...!!!!");
                    TCari.requestFocus();
                }
            } else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Stok...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                if(akses.getresep_pulang()==true){
                    if(tabMode7.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data permintaan resep pulang yang sudah divalidasi..!!");
                    }else{
                        if(Status.equals("Sudah Terlayani")){
                            DlgResepPulang reseppulang=new DlgResepPulang(null,false);
                            reseppulang.isCek();
                            reseppulang.setNoRm(NoRawat,NoRM,Pasien,DTPCari1.getDate(),DTPCari2.getDate());
                            reseppulang.tampil2();
                            reseppulang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            reseppulang.setLocationRelativeTo(internalFrame1);
                            reseppulang.setVisible(true);
                        }else {                           
                            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang sudah divalidasi..!!");               
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk mengvalidasi...!!!!");
                    TCari.requestFocus();
                }
            } else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Resep Pulang...!!!!");
                TCari.requestFocus();
            }      
        }
    }//GEN-LAST:event_BtnPemberianObatActionPerformed

    private void BtnPiutangObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPiutangObatActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPiutang penjualan=new DlgPiutang(null,false);
                    penjualan.isCek();
                    penjualan.emptTeks();
                    penjualan.setPasien(NoRawat,NoRM,Pasien);  
                    penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    penjualan.setLocationRelativeTo(internalFrame1);
                    penjualan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPiutang penjualan=new DlgPiutang(null,false);
                    penjualan.isCek();
                    penjualan.emptTeks();
                    penjualan.setPasien(NoRawat,NoRM,Pasien);  
                    penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    penjualan.setLocationRelativeTo(internalFrame1);
                    penjualan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(tabMode5.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPiutang penjualan=new DlgPiutang(null,false);
                    penjualan.isCek();
                    penjualan.emptTeks();
                    penjualan.setPasien(NoRawat,NoRM,Pasien);  
                    penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    penjualan.setLocationRelativeTo(internalFrame1);
                    penjualan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Stok...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                if(tabMode7.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPiutang penjualan=new DlgPiutang(null,false);
                    penjualan.isCek();
                    penjualan.emptTeks();
                    penjualan.setPasien(NoRawat,NoRM,Pasien);  
                    penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    penjualan.setLocationRelativeTo(internalFrame1);
                    penjualan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Resep Pulang...!!!!");
                TCari.requestFocus();
            }      
        }
    }//GEN-LAST:event_BtnPiutangObatActionPerformed

    private void BtnKonselingFarmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKonselingFarmasiActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dilakukan konseling..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    RMKonselingFarmasi penjualan=new RMKonselingFarmasi(null,false);
                    penjualan.isCek();
                    penjualan.emptTeks();
                    penjualan.setNoRm(NoRawat,DTPCari2.getDate());  
                    penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    penjualan.setLocationRelativeTo(internalFrame1);
                    penjualan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dilakukan konseling..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    RMKonselingFarmasi penjualan=new RMKonselingFarmasi(null,false);
                    penjualan.isCek();
                    penjualan.emptTeks();
                    penjualan.setNoRm(NoRawat,DTPCari2.getDate());  
                    penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    penjualan.setLocationRelativeTo(internalFrame1);
                    penjualan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(tabMode5.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dilakukan konseling..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    RMKonselingFarmasi penjualan=new RMKonselingFarmasi(null,false);
                    penjualan.isCek();
                    penjualan.emptTeks();
                    penjualan.setNoRm(NoRawat,DTPCari2.getDate());  
                    penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    penjualan.setLocationRelativeTo(internalFrame1);
                    penjualan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            } else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }      
        }
    }//GEN-LAST:event_BtnKonselingFarmasiActionPerformed

    private void BtnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(akses.getberi_obat()==true){
                    if(tabMode.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
                    }else{
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
                        resume.setNoRm(NoRM,Pasien);
                        resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        resume.setLocationRelativeTo(internalFrame1);
                        resume.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk melihat data yang sudah divalidasi...!!!!");
                    TCari.requestFocus();
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(akses.getberi_obat()==true){
                    if(tabMode3.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
                    }else{
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
                        resume.setNoRm(NoRM,Pasien);
                        resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        resume.setLocationRelativeTo(internalFrame1);
                        resume.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk melihat data yang sudah divalidasi...!!!!");
                    TCari.requestFocus();
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(akses.getstok_obat_pasien()==true){
                    if(tabMode5.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
                    }else{
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
                        resume.setNoRm(NoRM,Pasien);
                        resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        resume.setLocationRelativeTo(internalFrame1);
                        resume.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk melihat data yang sudah divalidasi...!!!!");
                    TCari.requestFocus();
                }
            } else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Stok...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                if(akses.getresep_pulang()==true){
                    if(tabMode7.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                        TCari.requestFocus();
                    }else if(NoRawat.equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
                    }else{
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
                        resume.setNoRm(NoRM,Pasien);
                        resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        resume.setLocationRelativeTo(internalFrame1);
                        resume.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Anda tidak punya hak akses untuk mengvalidasi...!!!!");
                    TCari.requestFocus();
                }
            } else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Resep Pulang...!!!!");
                TCari.requestFocus();
            }      
        }
    }//GEN-LAST:event_BtnRiwayatActionPerformed

    private void BtnInformasiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInformasiObatActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanPelayananInformasiObat form=new DlgPermintaanPelayananInformasiObat(null,false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.emptTeks();
                    form.setNoRm(NoRawat,NoRM,Pasien);
                    form.tampil();
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanPelayananInformasiObat form=new DlgPermintaanPelayananInformasiObat(null,false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setNoRm(NoRawat,NoRM,Pasien);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(tabMode5.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanPelayananInformasiObat form=new DlgPermintaanPelayananInformasiObat(null,false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setNoRm(NoRawat,NoRM,Pasien);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Stok...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                if(tabMode7.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanPelayananInformasiObat form=new DlgPermintaanPelayananInformasiObat(null,false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setNoRm(NoRawat,NoRM,Pasien);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Resep Pulang...!!!!");
                TCari.requestFocus();
            }      
        }
    }//GEN-LAST:event_BtnInformasiObatActionPerformed

    private void BtnSEPBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSEPBPJSActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien terlebih dahulu..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSDataSEP dlgki=new BPJSDataSEP(null,false);
                    dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgki.setLocationRelativeTo(internalFrame1);
                    dlgki.isCek();
                    dlgki.setNoRm3(NoRawat,DTPCari1.getDate());  
                    dlgki.tampil();
                    dlgki.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, hanya untuk rawat jalan kebutuhan klaim 23 hari...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Maaf, hanya untuk rawat jalan kebutuhan klaim 23 hari...!!!!");     
        }
    }//GEN-LAST:event_BtnSEPBPJSActionPerformed

    private void BtnObat23HariBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat23HariBPJSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat23HariBPJSActionPerformed

    private void BtnResepLuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepLuarActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dibuatkan resep luar..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    InventoryResepLuar resep=new InventoryResepLuar(null,false);
                    resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.setNoRm(NoRawat,KodeDokter,DokterPeresep,NoRM+" "+Pasien);
                    resep.isCek();
                    resep.setVisible(true); 
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dibuatkan resep luar..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    InventoryResepLuar resep=new InventoryResepLuar(null,false);
                    resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.setNoRm(NoRawat,KodeDokter,DokterPeresep,NoRM+" "+Pasien);
                    resep.isCek();
                    resep.setVisible(true); 
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(tabMode5.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dibuatkan resep luar..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    InventoryResepLuar resep=new InventoryResepLuar(null,false);
                    resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.setNoRm(NoRawat,KodeDokter,DokterPeresep,NoRM+" "+Pasien);
                    resep.isCek();
                    resep.setVisible(true); 
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Stok...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                if(tabMode7.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dibuatkan resep luar..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    InventoryResepLuar resep=new InventoryResepLuar(null,false);
                    resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.setNoRm(NoRawat,KodeDokter,DokterPeresep,NoRM+" "+Pasien);
                    resep.isCek();
                    resep.setVisible(true); 
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Permintaan Resep Pulang...!!!!");
                TCari.requestFocus();
            }      
        }
    }//GEN-LAST:event_BtnResepLuarActionPerformed

    private void BtnResepAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepAwalActionPerformed
         if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    try {
                        i=0;
                        ps=koneksi.prepareStatement(
                            "select databarang.nama_brng,resep_dokter.aturan_pakai,resep_dokter.jml,kodesatuan.satuan "+
                            "from resep_obat inner join resep_dokter on resep_obat.no_resep=resep_dokter.no_resep "+
                            "inner join databarang on databarang.kode_brng=resep_dokter.kode_brng "+
                            "inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat "+
                            "where resep_obat.no_resep=? and resep_dokter.aturan_pakai<>''");
                        try {
                            ps.setString(1,NoResep);
                            rs=ps.executeQuery();
                            while(rs.next()){
                                Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                    ""+i,rs.getString("nama_brng"),rs.getString("aturan_pakai"),rs.getString("jml"),rs.getString("satuan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 1 : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }

                        ps=koneksi.prepareStatement(
                            "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,resep_dokter_racikan.aturan_pakai,"+
                            "resep_dokter_racikan.jml_dr,metode_racik.nm_racik " +
                            "from resep_obat inner join resep_dokter_racikan on resep_dokter_racikan.no_resep=resep_obat.no_resep "+
                            "inner join metode_racik on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where resep_obat.no_resep=?");
                        try {
                            ps.setString(1,NoResep);
                            rs=ps.executeQuery();
                            while(rs.next()){
                                rincianobat="";
                                ps2=koneksi.prepareStatement(
                                    "select databarang.nama_brng,resep_dokter_racikan_detail.jml from resep_dokter_racikan_detail "+
                                    "inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                    "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                                try {
                                    ps2.setString(1,NoResep);
                                    ps2.setString(2,rs.getString("no_racik"));
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        rincianobat=rs2.getString("nama_brng")+" "+rs2.getString("jml")+","+rincianobat;
                                    }                                
                                } catch (Exception e) {
                                    System.out.println("Notifikasi Detail Racikan : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                                rincianobat = rincianobat.substring(0,rincianobat.length() - 1);

                                Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                    ""+i,rs.getString("nama_racik")+" ("+rincianobat+")",rs.getString("aturan_pakai"),rs.getString("jml_dr"),rs.getString("nm_racik"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif Racikan : "+e);
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

                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("penanggung",Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",NoRawat)));               
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("tanggal",TglPeresepan);
                    param.put("norawat",NoRawat);
                    param.put("pasien",Pasien);
                    param.put("norm",NoRM);
                    param.put("peresep",DokterPeresep);
                    param.put("noresep",NoResep);
                    finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KodeDokter);
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+DokterPeresep+"\nID "+(finger.equals("")?KodeDokter:finger)+"\n"+Valid.SetTgl3(TglPeresepan));  
                    param.put("jam",JamPeresepan);
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptLembarObat2.jasper","report","::[ Lembar Pemberian Obat ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dimasukkan piutang..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
                    try {
                        i=0;
                        ps=koneksi.prepareStatement(
                            "select databarang.nama_brng,resep_dokter.aturan_pakai,resep_dokter.jml,kodesatuan.satuan "+
                            "from resep_obat inner join resep_dokter on resep_obat.no_resep=resep_dokter.no_resep "+
                            "inner join databarang on databarang.kode_brng=resep_dokter.kode_brng "+
                            "inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat "+
                            "where resep_obat.no_resep=? and resep_dokter.aturan_pakai<>''");
                        try {
                            ps.setString(1,NoResep);
                            rs=ps.executeQuery();
                            while(rs.next()){
                                Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                    ""+i,rs.getString("nama_brng"),rs.getString("aturan_pakai"),rs.getString("jml"),rs.getString("satuan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 1 : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }

                        ps=koneksi.prepareStatement(
                            "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,resep_dokter_racikan.aturan_pakai,"+
                            "resep_dokter_racikan.jml_dr,metode_racik.nm_racik " +
                            "from resep_obat inner join resep_dokter_racikan on resep_dokter_racikan.no_resep=resep_obat.no_resep "+
                            "inner join metode_racik on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where resep_obat.no_resep=?");
                        try {
                            ps.setString(1,NoResep);
                            rs=ps.executeQuery();
                            while(rs.next()){
                                rincianobat="";
                                ps2=koneksi.prepareStatement(
                                    "select databarang.nama_brng,resep_dokter_racikan_detail.jml from resep_dokter_racikan_detail "+
                                    "inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                    "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                                try {
                                    ps2.setString(1,NoResep);
                                    ps2.setString(2,rs.getString("no_racik"));
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        rincianobat=rs2.getString("nama_brng")+" "+rs2.getString("jml")+","+rincianobat;
                                    }                                
                                } catch (Exception e) {
                                    System.out.println("Notifikasi Detail Racikan : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                                rincianobat = rincianobat.substring(0,rincianobat.length() - 1);

                                Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                    ""+i,rs.getString("nama_racik")+" ("+rincianobat+")",rs.getString("aturan_pakai"),rs.getString("jml_dr"),rs.getString("nm_racik"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif Racikan : "+e);
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

                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("penanggung",Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",NoRawat)));               
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("tanggal",TglPeresepan);
                    param.put("norawat",NoRawat);
                    param.put("pasien",Pasien);
                    param.put("norm",NoRM);
                    param.put("peresep",DokterPeresep);
                    param.put("noresep",NoResep);
                    finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KodeDokter);
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+DokterPeresep+"\nID "+(finger.equals("")?KodeDokter:finger)+"\n"+Valid.SetTgl3(TglPeresepan));  
                    param.put("jam",JamPeresepan);
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptLembarObat2.jasper","report","::[ Lembar Pemberian Obat ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka daftar resep Rawat Jalan/Rawat Inap...!!!!");
                TCari.requestFocus();
            }    
        }
    }//GEN-LAST:event_BtnResepAwalActionPerformed

    private void BtnTelaahResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTelaahResepActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dilakukan konseling..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    InventoryTelaahResep aplikasi=new InventoryTelaahResep(null,false);
                    aplikasi.emptTeks();
                    aplikasi.isCek();
                    aplikasi.setNoRm(NoResep,NoRawat,DTPCari2.getDate());
                    aplikasi.tampil();
                    aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aplikasi.setLocationRelativeTo(internalFrame1);
                    aplikasi.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dilakukan konseling..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    InventoryTelaahResep aplikasi=new InventoryTelaahResep(null,false);
                    aplikasi.emptTeks();
                    aplikasi.isCek();
                    aplikasi.setNoRm(NoResep,NoRawat,DTPCari2.getDate());
                    aplikasi.tampil();
                    aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aplikasi.setLocationRelativeTo(internalFrame1);
                    aplikasi.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==2){
                if(tabMode5.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                }else if(NoRawat.equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data pasien yang mau dilakukan konseling..!!");
                }else{
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    InventoryTelaahResep aplikasi=new InventoryTelaahResep(null,false);
                    aplikasi.emptTeks();
                    aplikasi.isCek();
                    aplikasi.setNoRm(NoResep,NoRawat,DTPCari2.getDate());
                    aplikasi.tampil();
                    aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aplikasi.setLocationRelativeTo(internalFrame1);
                    aplikasi.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else if(TabRawatInap.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }else if(TabRawatInap.getSelectedIndex()==4){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            } else if(TabRawatInap.getSelectedIndex()==5){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }      
        }
    }//GEN-LAST:event_BtnTelaahResepActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDaftarPermintaanResep dialog = new DlgDaftarPermintaanResep(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnInformasiObat;
    private widget.Button BtnKeluar;
    private widget.Button BtnKonselingFarmasi;
    private widget.Button BtnObat23HariBPJS;
    private widget.Button BtnPemberianObat;
    private widget.Button BtnPenyerahan;
    private widget.Button BtnPiutangObat;
    private widget.Button BtnPrint;
    private widget.Button BtnRekap;
    private widget.Button BtnResepAwal;
    private widget.Button BtnResepLuar;
    private widget.Button BtnRiwayat;
    private widget.Button BtnSEPBPJS;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnTambah;
    private widget.Button BtnTelaahResep;
    private widget.CekBox ChkAccor;
    private widget.TextBox CrDokter;
    private widget.TextBox CrDokter2;
    private widget.TextBox CrPoli;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox Kamar;
    private widget.Label LCount;
    private widget.PanelBiasa PanelAccor;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabPilihRawat;
    private javax.swing.JTabbedPane TabRawatInap;
    private javax.swing.JTabbedPane TabRawatJalan;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel12;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private javax.swing.JPanel jPanel2;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.Table tbDetailPermintaanResepPulang;
    private widget.Table tbDetailPermintaanStok;
    private widget.Table tbDetailResepRalan;
    private widget.Table tbDetailResepRanap;
    private widget.Table tbPermintaanResepPulang;
    private widget.Table tbPermintaanStok;
    private widget.Table tbResepRalan;
    private widget.Table tbResepRanap;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        if(ceksukses==false){
            ceksukses=true;
            Valid.tabelKosong(tabMode);
            new SwingWorker<Void, Object[]>() {
                @Override
                protected Void doInBackground() {
                    try{  
                        semua=CrDokter.getText().trim().equals("")&&CrPoli.getText().trim().equals("")&&TCari.getText().trim().equals("");
                        if(DEPOAKTIFOBAT.equals("")){
                            ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                                    " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,"+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,poliklinik.nm_poli, "+
                                    " reg_periksa.kd_poli,penjab.png_jawab,if(resep_obat.tgl_perawatan='0000-00-00','',resep_obat.tgl_perawatan) as tgl_perawatan,"+
                                    " if(resep_obat.jam='00:00:00','',resep_obat.jam) as jam,"+
                                    " if(resep_obat.tgl_penyerahan='0000-00-00','',resep_obat.tgl_penyerahan) as tgl_penyerahan,"+
                                    " if(resep_obat.jam_penyerahan='00:00:00','',resep_obat.jam_penyerahan) as jam_penyerahan from resep_obat "+
                                    " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                                    " inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " where resep_obat.tgl_peresepan<>'0000-00-00' and resep_obat.status='ralan' and resep_obat.tgl_peresepan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and "+
                                    "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                                    "penjab.png_jawab like ?)")+" order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
                        }else{
                            ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                                    " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,"+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,poliklinik.nm_poli, "+
                                    " reg_periksa.kd_poli,penjab.png_jawab,if(resep_obat.tgl_perawatan='0000-00-00','',resep_obat.tgl_perawatan) as tgl_perawatan,"+
                                    " if(resep_obat.jam='00:00:00','',resep_obat.jam) as jam,"+
                                    " if(resep_obat.tgl_penyerahan='0000-00-00','',resep_obat.tgl_penyerahan) as tgl_penyerahan,"+
                                    " if(resep_obat.jam_penyerahan='00:00:00','',resep_obat.jam_penyerahan) as jam_penyerahan from resep_obat "+
                                    " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                                    " inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join set_depo_ralan on set_depo_ralan.kd_poli=reg_periksa.kd_poli "+
                                    " where set_depo_ralan.kd_bangsal='"+DEPOAKTIFOBAT+"' and resep_obat.tgl_peresepan<>'0000-00-00' and resep_obat.status='ralan' and resep_obat.tgl_peresepan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and "+
                                    "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                                    "penjab.png_jawab like ?)")+" order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
                        }

                        try{
                            ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                            ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                            if(!semua){
                                ps.setString(3,"%"+CrDokter.getText().trim()+"%");
                                ps.setString(4,"%"+CrPoli.getText().trim()+"%");
                                ps.setString(5,"%"+TCari.getText()+"%");
                                ps.setString(6,"%"+TCari.getText()+"%");
                                ps.setString(7,"%"+TCari.getText()+"%");
                                ps.setString(8,"%"+TCari.getText()+"%");
                                ps.setString(9,"%"+TCari.getText()+"%");
                            } 
                            rs=ps.executeQuery();
                            i=0;
                            if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                                while(rs.next()){
                                    Object[] row = new Object[]{
                                        rs.getString("no_resep"),rs.getString("tgl_peresepan"),rs.getString("jam_peresepan"),rs.getString("no_rawat"),
                                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                        rs.getString("kd_dokter"),rs.getString("nm_poli"),rs.getString("kd_poli"),rs.getString("png_jawab"),
                                        rs.getString("tgl_perawatan"),rs.getString("jam"),rs.getString("tgl_penyerahan"),rs.getString("jam_penyerahan")
                                    };  
                                    i++;
                                    publish(row);
                                }  
                            }else{
                                while(rs.next()){
                                    if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                                        Object[] row = new Object[]{
                                            rs.getString("no_resep"),rs.getString("tgl_peresepan"),rs.getString("jam_peresepan"),rs.getString("no_rawat"),
                                            rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                            rs.getString("kd_dokter"),rs.getString("nm_poli"),rs.getString("kd_poli"),rs.getString("png_jawab"),
                                            rs.getString("tgl_perawatan"),rs.getString("jam"),rs.getString("tgl_penyerahan"),rs.getString("jam_penyerahan")
                                        };
                                        i++;
                                        publish(row);
                                    }                    
                                }  
                            }   
                        } catch(Exception ex){
                            System.out.println("Notifikasi RS : "+ex);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }                
                    }catch(Exception e){
                        System.out.println("Notifikasi PS : "+e);
                    } 
                    return null;
                }
                
                @Override
                protected void process(List<Object[]> data) {
                    for (Object[] row : data) {
                        tabMode.addRow(row);
                    }
                }

                @Override
                protected void done() {
                    LCount.setText(""+i);
                    ceksukses = false;
                }
            }.execute();
        }   
    }

    public void emptTeks() {
        TCari.setText("");
        TCari.requestFocus();
    }

    private void getData() {
        if(tbResepRalan.getSelectedRow()!= -1){
            NoResep=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),0).toString();
            TglPeresepan=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),1).toString();
            JamPeresepan=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),2).toString();
            NoRawat=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),3).toString();
            NoRM=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),4).toString();
            Pasien=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),5).toString();
            DokterPeresep=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),6).toString();
            Status=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),7).toString();
            KodeDokter=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),8).toString();
            Ruang=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),9).toString();
            KodeRuang=tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(),10).toString();
        }
    }
    
    private void getData2() {
        if(tbResepRanap.getSelectedRow()!= -1){
            NoResep=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),0).toString();
            TglPeresepan=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),1).toString();
            JamPeresepan=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),2).toString();
            NoRawat=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),3).toString();
            NoRM=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),4).toString();
            Pasien=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),5).toString();
            DokterPeresep=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),6).toString();
            Status=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),7).toString();
            KodeDokter=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),8).toString();
            Ruang=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),9).toString();
            KodeRuang=tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(),10).toString();
        }
    }

    private void getData3() {
        if(tbPermintaanStok.getSelectedRow()!= -1){
            NoResep=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),0).toString();
            TglPeresepan=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),1).toString();
            JamPeresepan=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),2).toString();
            NoRawat=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),3).toString();
            NoRM=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),4).toString();
            Pasien=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),5).toString();
            DokterPeresep=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),6).toString();
            Status=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),7).toString();
            KodeDokter=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),8).toString();
            Ruang=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),9).toString();
            KodeRuang=tbPermintaanStok.getValueAt(tbPermintaanStok.getSelectedRow(),10).toString();
        }
    }
    
    private void getData4() {
        if(tbPermintaanResepPulang.getSelectedRow()!= -1){
            NoResep=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),0).toString();
            TglPeresepan=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),1).toString();
            JamPeresepan=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),2).toString();
            NoRawat=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),3).toString();
            NoRM=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),4).toString();
            Pasien=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),5).toString();
            DokterPeresep=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),6).toString();
            Status=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),7).toString();
            KodeDokter=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),8).toString();
            Ruang=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),9).toString();
            KodeRuang=tbPermintaanResepPulang.getValueAt(tbPermintaanResepPulang.getSelectedRow(),10).toString();
        }
    }
    
    public JTable getTable(){
        return tbResepRalan;
    }
    
    public void isCek(){
        BtnEdit.setEnabled(akses.getresep_dokter());
        BtnPrint.setEnabled(akses.getresep_dokter());
        BtnRekap.setEnabled(akses.getresep_obat());
        BtnPiutangObat.setEnabled(akses.getpiutang_obat());
        BtnKonselingFarmasi.setEnabled(akses.getkonseling_farmasi());
        BtnInformasiObat.setEnabled(akses.getpelayanan_informasi_obat());
        BtnRiwayat.setEnabled(akses.getresume_pasien()); 
        BtnSEPBPJS.setEnabled(akses.getbpjs_sep()); 
        BtnResepLuar.setEnabled(akses.getresep_luar()); 
        BtnObat23HariBPJS.setEnabled(akses.getbpjs_obat_23hari_apotek()); 
        BtnTelaahResep.setEnabled(akses.gettelaah_resep()); 
    }
    
    public void setCari(String cari){
        TCari.setText(cari);
    }

    private void tampil2() {
        Valid.tabelKosong(tabMode2);
        try{  
            semua=CrDokter.getText().trim().equals("")&&CrPoli.getText().trim().equals("")&&TCari.getText().trim().equals("");
            if(DEPOAKTIFOBAT.equals("")){
                ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                        " poliklinik.nm_poli,resep_obat.status as status_asal,penjab.png_jawab from resep_obat "+
                        " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                        " inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " where resep_obat.tgl_peresepan<>'0000-00-00' and resep_obat.status='ralan' and resep_obat.tgl_peresepan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and "+
                        "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penjab.png_jawab like ?) ")+"order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
            }else{
                ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                        " poliklinik.nm_poli,resep_obat.status as status_asal,penjab.png_jawab from resep_obat "+
                        " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                        " inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " inner join set_depo_ralan on set_depo_ralan.kd_poli=reg_periksa.kd_poli "+
                        " where set_depo_ralan.kd_bangsal='"+DEPOAKTIFOBAT+"' and resep_obat.tgl_peresepan<>'0000-00-00' and resep_obat.status='ralan' and resep_obat.tgl_peresepan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and "+
                        "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penjab.png_jawab like ?) ")+"order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
            }
                
            try{
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter.getText().trim()+"%");
                    ps.setString(4,"%"+CrPoli.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                
                rs=ps.executeQuery();
                i=0;
                if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                    while(rs.next()){
                        tabMode2.addRow(new Object[]{
                            rs.getString("no_resep"),rs.getString("tgl_peresepan")+" "+rs.getString("jam_peresepan"),
                            rs.getString("nm_poli"),rs.getString("status"),
                            rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                            rs.getString("nm_dokter")
                        });
                        tabMode2.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                        ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"+
                            "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "+
                            "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,rs.getString("no_resep"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode2.addRow(new Object[]{
                                    "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("aturan_pakai")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        ps2=koneksi.prepareStatement(
                                "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                                "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                                "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                                "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                                "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                                "resep_dokter_racikan.no_resep=? ");
                        try {
                            ps2.setString(1,rs.getString("no_resep"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode2.addRow(new Object[]{
                                    "","",rs2.getString("jml_dr")+" "+rs2.getString("metode"),"No.Racik : "+rs2.getString("no_racik"),rs2.getString("nama_racik"),rs2.getString("aturan_pakai")
                                });
                                ps3=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"+
                                    "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                    "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                                try {
                                    ps3.setString(1,rs.getString("no_resep"));
                                    ps3.setString(2,rs2.getString("no_racik"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        tabMode2.addRow(new Object[]{
                                            "","","   "+rs3.getString("jml")+" "+rs3.getString("kode_sat"),"   "+rs3.getString("kode_brng"),"   "+rs3.getString("nama_brng"),""
                                        });
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi 3 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        i++;
                    }
                }else{
                    while(rs.next()){
                        if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                            tabMode2.addRow(new Object[]{
                                rs.getString("no_resep"),rs.getString("tgl_peresepan")+" "+rs.getString("jam_peresepan"),
                                rs.getString("nm_poli"),rs.getString("status"),
                                rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                                rs.getString("nm_dokter")
                            });
                            tabMode2.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                            ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"+
                                "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "+
                                "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1,rs.getString("no_resep"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode2.addRow(new Object[]{
                                        "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("aturan_pakai")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            ps2=koneksi.prepareStatement(
                                    "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                                    "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                                    "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                                    "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                                    "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                                    "resep_dokter_racikan.no_resep=? ");
                            try {
                                ps2.setString(1,rs.getString("no_resep"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode2.addRow(new Object[]{
                                        "","",rs2.getString("jml_dr")+" "+rs2.getString("metode"),"No.Racik : "+rs2.getString("no_racik"),rs2.getString("nama_racik"),rs2.getString("aturan_pakai")
                                    });
                                    ps3=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"+
                                        "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                        "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                                    try {
                                        ps3.setString(1,rs.getString("no_resep"));
                                        ps3.setString(2,rs2.getString("no_racik"));
                                        rs3=ps3.executeQuery();
                                        while(rs3.next()){
                                            tabMode2.addRow(new Object[]{
                                                "","","   "+rs3.getString("jml")+" "+rs3.getString("kode_sat"),"   "+rs3.getString("kode_brng"),"   "+rs3.getString("nama_brng"),""
                                            });
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notifikasi 3 : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                        if(ps3!=null){
                                            ps3.close();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            i++;
                        }
                    }
                }
                LCount.setText(""+i);
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }  
    }

    private void panggilform() {
        DlgCariObat dlgobt=new DlgCariObat(null,false);
        dlgobt.setNoRm(NoRawat,NoRM,Pasien,TglPeresepan,JamPeresepan);
        dlgobt.isCek();
        dlgobt.tampilobat3(NoResep);
        dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dlgobt.setLocationRelativeTo(internalFrame1);
        TeksKosong();
        dlgobt.setVisible(true);   
    }
    
    private void TeksKosong(){
        NoResep="";
        TglPeresepan="";
        JamPeresepan="";
        NoRawat="";
        NoRM="";
        Pasien="";
        DokterPeresep="";
        Status="";
        KodeDokter="";
        Ruang="";
        KodeRuang="";
    }
    
    private void panggilform2() {
        kamar=KodeRuang;
        bangsal=Sequel.cariIsi("select set_depo_ranap.kd_depo from set_depo_ranap where set_depo_ranap.kd_bangsal=?",kamar);
        if(bangsal.equals("")){
            if(Sequel.cariIsi("select set_lokasi.asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                akses.setkdbangsal(kamar);
            }else{
                akses.setkdbangsal(Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi"));
            }
        }else{
            akses.setkdbangsal(bangsal);
        }
        DlgCariObat2 dlgobt2=new DlgCariObat2(null,false);
        dlgobt2.setNoRm(NoRawat,NoRM,Pasien,Valid.SetTgl2(TglPeresepan));
        dlgobt2.isCek();
        dlgobt2.tampilobat3(NoResep);
        dlgobt2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dlgobt2.setLocationRelativeTo(internalFrame1);
        TeksKosong();
        dlgobt2.setVisible(true);         
    }
    
    private void panggilform3() {
        kamar=KodeRuang;
        bangsal=Sequel.cariIsi("select set_depo_ranap.kd_depo from set_depo_ranap where set_depo_ranap.kd_bangsal=?",kamar);
        if(bangsal.equals("")){
            if(Sequel.cariIsi("select set_lokasi.asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                akses.setkdbangsal(kamar);
            }else{
                akses.setkdbangsal(Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi"));
            }
        }else{
            akses.setkdbangsal(bangsal);
        }
        DlgInputStokPasien dlgstok=new DlgInputStokPasien(null,false);
        dlgstok.setNoRm(NoRawat,NoRM+" "+Pasien);
        dlgstok.isCek();
        dlgstok.tampil3(NoResep);
        dlgstok.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dlgstok.setLocationRelativeTo(internalFrame1);
        TeksKosong();
        dlgstok.setVisible(true);         
    }
    
    private void panggilform4() {
        kamar=KodeRuang;
        bangsal=Sequel.cariIsi("select set_depo_ranap.kd_depo from set_depo_ranap where set_depo_ranap.kd_bangsal=?",kamar);
        if(bangsal.equals("")){
            if(Sequel.cariIsi("select set_lokasi.asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                akses.setkdbangsal(kamar);
            }else{
                akses.setkdbangsal(Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi"));
            }
        }else{
            akses.setkdbangsal(bangsal);
        }
        DlgInputResepPulang dlgresepulang=new DlgInputResepPulang(null,false);
        dlgresepulang.setNoRm(NoRawat,NoRM,Pasien,"-",DTPCari1.getSelectedItem().toString(),Sequel.cariIsi("select current_time()"));
        dlgresepulang.isCek();
        dlgresepulang.tampil3(NoResep);
        dlgresepulang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dlgresepulang.setLocationRelativeTo(internalFrame1);
        TeksKosong();
        dlgresepulang.setVisible(true);         
    }
    
    private void pilihTab(){
        if(TabPilihRawat.getSelectedIndex()==0){
            pilihRalan();
        }else if(TabPilihRawat.getSelectedIndex()==1){
            pilihRanap();
        }
    }
    
    private void pilihRalan(){
        if(TabRawatJalan.getSelectedIndex()==0){
            tampil();
        }else if(TabRawatJalan.getSelectedIndex()==1){
            tampil2();
        }
    }
    
    private void pilihRanap(){
        if(TabRawatInap.getSelectedIndex()==0){
            tampil3();
        }else if(TabRawatInap.getSelectedIndex()==1){
            tampil4();
        }else if(TabRawatInap.getSelectedIndex()==2){
            tampil5();
        }else if(TabRawatInap.getSelectedIndex()==3){
            tampil6();
        }else if(TabRawatInap.getSelectedIndex()==4){
            tampil7();
        }else if(TabRawatInap.getSelectedIndex()==5){
            tampil8();
        }
    }
    
    private void tampil3() {
        if(ceksukses==false){
            ceksukses=true;
            Valid.tabelKosong(tabMode3);
            new SwingWorker<Void, Object[]>() {
                @Override
                protected Void doInBackground() {
                    try{  
                        semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
                        if(DEPOAKTIFOBAT.equals("")){
                            ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                                    " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab,"+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','',resep_obat.tgl_perawatan) as tgl_perawatan,"+
                                    " if(resep_obat.jam='00:00:00','',resep_obat.jam) as jam from resep_obat  "+
                                    " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " where resep_obat.tgl_peresepan<>'0000-00-00' and kamar_inap.stts_pulang='-' and resep_obat.status='ranap' and resep_obat.tgl_peresepan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by resep_obat.no_resep order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
                        }else{
                            ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                                    " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab,"+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','',resep_obat.tgl_perawatan) as tgl_perawatan,"+
                                    " if(resep_obat.jam='00:00:00','',resep_obat.jam) as jam from resep_obat  "+
                                    " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                                    " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and resep_obat.tgl_peresepan<>'0000-00-00' and kamar_inap.stts_pulang='-' and resep_obat.status='ranap' and resep_obat.tgl_peresepan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by resep_obat.no_resep order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
                        }

                        try{
                            ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                            ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                            if(!semua){
                                ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                                ps.setString(4,"%"+Kamar.getText().trim()+"%");
                                ps.setString(5,"%"+TCari.getText()+"%");
                                ps.setString(6,"%"+TCari.getText()+"%");
                                ps.setString(7,"%"+TCari.getText()+"%");
                                ps.setString(8,"%"+TCari.getText()+"%");
                                ps.setString(9,"%"+TCari.getText()+"%");
                            }

                            rs=ps.executeQuery();
                            i=0;
                            if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                                while(rs.next()){
                                    Object[] row = new Object[]{
                                        rs.getString("no_resep"),rs.getString("tgl_peresepan"),rs.getString("jam_peresepan"),rs.getString("no_rawat"),
                                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                        rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab"),
                                        rs.getString("tgl_perawatan"),rs.getString("jam")
                                    }; 
                                    i++;
                                    publish(row);
                                }
                            }else{
                                while(rs.next()){
                                    if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                                        Object[] row = new Object[]{
                                            rs.getString("no_resep"),rs.getString("tgl_peresepan"),rs.getString("jam_peresepan"),rs.getString("no_rawat"),
                                            rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                            rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab"),
                                            rs.getString("tgl_perawatan"),rs.getString("jam")
                                        }; 
                                        i++;
                                        publish(row);
                                    }                  
                                } 
                            }               
                        } catch(Exception ex){
                            System.out.println("Notifikasi : "+ex);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }      

                        if(DEPOAKTIFOBAT.equals("")){
                            ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                                    " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab,"+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','',resep_obat.tgl_perawatan) as tgl_perawatan,"+
                                    " if(resep_obat.jam='00:00:00','',resep_obat.jam) as jam from resep_obat  "+
                                    " inner join ranap_gabung on ranap_gabung.no_rawat2=resep_obat.no_rawat "+
                                    " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " where resep_obat.tgl_peresepan<>'0000-00-00' and kamar_inap.stts_pulang='-' and resep_obat.status='ranap' and resep_obat.tgl_peresepan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by resep_obat.no_resep order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
                        }else{
                            ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                                    " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab,"+
                                    " if(resep_obat.tgl_perawatan='0000-00-00','',resep_obat.tgl_perawatan) as tgl_perawatan,"+
                                    " if(resep_obat.jam='00:00:00','',resep_obat.jam) as jam from resep_obat  "+
                                    " inner join ranap_gabung on ranap_gabung.no_rawat2=resep_obat.no_rawat "+
                                    " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                                    " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and resep_obat.tgl_peresepan<>'0000-00-00' and kamar_inap.stts_pulang='-' and resep_obat.status='ranap' and resep_obat.tgl_peresepan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by resep_obat.no_resep order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
                        }

                        try{
                            ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                            ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                            if(!semua){
                                ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                                ps.setString(4,"%"+Kamar.getText().trim()+"%");
                                ps.setString(5,"%"+TCari.getText()+"%");
                                ps.setString(6,"%"+TCari.getText()+"%");
                                ps.setString(7,"%"+TCari.getText()+"%");
                                ps.setString(8,"%"+TCari.getText()+"%");
                                ps.setString(9,"%"+TCari.getText()+"%");
                            }

                            rs=ps.executeQuery();
                            if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                                while(rs.next()){
                                    Object[] row = new Object[]{
                                        rs.getString("no_resep"),rs.getString("tgl_peresepan"),rs.getString("jam_peresepan"),rs.getString("no_rawat"),
                                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                        rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab"),
                                        rs.getString("tgl_perawatan"),rs.getString("jam")
                                    };   
                                    i++;
                                    publish(row);
                                } 
                            }else{
                                while(rs.next()){
                                    if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                                        Object[] row = new Object[]{
                                            rs.getString("no_resep"),rs.getString("tgl_peresepan"),rs.getString("jam_peresepan"),rs.getString("no_rawat"),
                                            rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                            rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab"),
                                            rs.getString("tgl_perawatan"),rs.getString("jam")
                                        };  
                                        i++;
                                        publish(row);
                                    }                  
                                }
                            }   
                        } catch(Exception ex){
                            System.out.println("Notifikasi : "+ex);
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
                    return null;
                }
                
                @Override
                protected void process(List<Object[]> data) {
                    for (Object[] row : data) {
                        tabMode3.addRow(row);
                    }
                }

                @Override
                protected void done() {
                    LCount.setText(""+i);
                    ceksukses = false;
                }
            }.execute(); 
        } 
    }
    
    private void tampil4() {
        Valid.tabelKosong(tabMode4);
        try{  
            semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
            if(DEPOAKTIFOBAT.equals("")){
                ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,resep_obat.status as status_asal,penjab.png_jawab from resep_obat "+
                        " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " where resep_obat.tgl_peresepan<>'0000-00-00' and kamar_inap.stts_pulang='-' and resep_obat.status='ranap' and resep_obat.tgl_peresepan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by resep_obat.no_resep order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
            }else{
                ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,resep_obat.status as status_asal,penjab.png_jawab from resep_obat "+
                        " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                        " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and resep_obat.tgl_peresepan<>'0000-00-00' and kamar_inap.stts_pulang='-' and resep_obat.status='ranap' and resep_obat.tgl_peresepan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by resep_obat.no_resep order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
            }
                
            try{
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                i=0;
                if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                    while(rs.next()){
                        tabMode4.addRow(new Object[]{
                            rs.getString("no_resep"),rs.getString("tgl_peresepan")+" "+rs.getString("jam_peresepan"),
                            rs.getString("nm_bangsal"),rs.getString("status"),
                            rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                            rs.getString("nm_dokter")
                        });
                        tabMode4.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                        ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"+
                            "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "+
                            "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,rs.getString("no_resep"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode4.addRow(new Object[]{
                                    "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("aturan_pakai")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        ps2=koneksi.prepareStatement(
                                "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                                "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                                "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                                "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                                "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                                "resep_dokter_racikan.no_resep=? ");
                        try {
                            ps2.setString(1,rs.getString("no_resep"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode4.addRow(new Object[]{
                                    "","",rs2.getString("jml_dr")+" "+rs2.getString("metode"),"No.Racik : "+rs2.getString("no_racik"),rs2.getString("nama_racik"),rs2.getString("aturan_pakai")
                                });
                                ps3=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"+
                                    "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                    "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                                try {
                                    ps3.setString(1,rs.getString("no_resep"));
                                    ps3.setString(2,rs2.getString("no_racik"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        tabMode4.addRow(new Object[]{
                                            "","","   "+rs3.getString("jml")+" "+rs3.getString("kode_sat"),"   "+rs3.getString("kode_brng"),"   "+rs3.getString("nama_brng"),""
                                        });
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi 3 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        i++;
                    }
                }else{
                    while(rs.next()){
                        if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                            tabMode4.addRow(new Object[]{
                                rs.getString("no_resep"),rs.getString("tgl_peresepan")+" "+rs.getString("jam_peresepan"),
                                rs.getString("nm_bangsal"),rs.getString("status"),
                                rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                                rs.getString("nm_dokter")
                            });
                            tabMode4.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                            ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"+
                                "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "+
                                "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1,rs.getString("no_resep"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode4.addRow(new Object[]{
                                        "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("aturan_pakai")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            ps2=koneksi.prepareStatement(
                                    "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                                    "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                                    "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                                    "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                                    "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                                    "resep_dokter_racikan.no_resep=? ");
                            try {
                                ps2.setString(1,rs.getString("no_resep"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode4.addRow(new Object[]{
                                        "","",rs2.getString("jml_dr")+" "+rs2.getString("metode"),"No.Racik : "+rs2.getString("no_racik"),rs2.getString("nama_racik"),rs2.getString("aturan_pakai")
                                    });
                                    ps3=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"+
                                        "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                        "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                                    try {
                                        ps3.setString(1,rs.getString("no_resep"));
                                        ps3.setString(2,rs2.getString("no_racik"));
                                        rs3=ps3.executeQuery();
                                        while(rs3.next()){
                                            tabMode4.addRow(new Object[]{
                                                "","","   "+rs3.getString("jml")+" "+rs3.getString("kode_sat"),"   "+rs3.getString("kode_brng"),"   "+rs3.getString("nama_brng"),""
                                            });
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notifikasi 3 : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                        if(ps3!=null){
                                            ps3.close();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            i++;
                        }
                    }
                }
                    
                LCount.setText(""+i++);
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            } 
            
            if(DEPOAKTIFOBAT.equals("")){
                ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,resep_obat.status as status_asal,penjab.png_jawab from resep_obat "+
                        " inner join ranap_gabung on ranap_gabung.no_rawat2=resep_obat.no_rawat "+
                        " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " where resep_obat.tgl_peresepan<>'0000-00-00' and kamar_inap.stts_pulang='-' and resep_obat.status='ranap' and resep_obat.tgl_peresepan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by resep_obat.no_resep order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
            }else{
                ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_obat.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,resep_obat.status as status_asal,penjab.png_jawab from resep_obat "+
                        " inner join ranap_gabung on ranap_gabung.no_rawat2=resep_obat.no_rawat "+
                        " inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                        " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and resep_obat.tgl_peresepan<>'0000-00-00' and kamar_inap.stts_pulang='-' and resep_obat.status='ranap' and resep_obat.tgl_peresepan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by resep_obat.no_resep order by resep_obat.tgl_peresepan desc,resep_obat.jam_peresepan desc");
            }
                
            try{
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                i=0;
                if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                    while(rs.next()){
                        tabMode4.addRow(new Object[]{
                            rs.getString("no_resep"),rs.getString("tgl_peresepan")+" "+rs.getString("jam_peresepan"),
                            rs.getString("nm_bangsal"),rs.getString("status"),
                            rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                            rs.getString("nm_dokter")
                        });
                        tabMode4.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                        ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"+
                            "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "+
                            "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,rs.getString("no_resep"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode4.addRow(new Object[]{
                                    "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("aturan_pakai")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        ps2=koneksi.prepareStatement(
                                "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                                "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                                "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                                "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                                "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                                "resep_dokter_racikan.no_resep=? ");
                        try {
                            ps2.setString(1,rs.getString("no_resep"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode4.addRow(new Object[]{
                                    "","",rs2.getString("jml_dr")+" "+rs2.getString("metode"),"No.Racik : "+rs2.getString("no_racik"),rs2.getString("nama_racik"),rs2.getString("aturan_pakai")
                                });
                                ps3=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"+
                                    "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                    "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                                try {
                                    ps3.setString(1,rs.getString("no_resep"));
                                    ps3.setString(2,rs2.getString("no_racik"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        tabMode4.addRow(new Object[]{
                                            "","","   "+rs3.getString("jml")+" "+rs3.getString("kode_sat"),"   "+rs3.getString("kode_brng"),"   "+rs3.getString("nama_brng"),""
                                        });
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi 3 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        i++;
                    }
                }else{
                    while(rs.next()){
                        if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                            tabMode4.addRow(new Object[]{
                                rs.getString("no_resep"),rs.getString("tgl_perawatan")+" "+rs.getString("jam"),
                                rs.getString("nm_bangsal"),rs.getString("status"),
                                rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                                rs.getString("nm_dokter")
                            });
                            tabMode4.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                            ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"+
                                "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "+
                                "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1,rs.getString("no_resep"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode4.addRow(new Object[]{
                                        "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("aturan_pakai")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            ps2=koneksi.prepareStatement(
                                    "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                                    "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                                    "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                                    "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                                    "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                                    "resep_dokter_racikan.no_resep=? ");
                            try {
                                ps2.setString(1,rs.getString("no_resep"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode4.addRow(new Object[]{
                                        "","",rs2.getString("jml_dr")+" "+rs2.getString("metode"),"No.Racik : "+rs2.getString("no_racik"),rs2.getString("nama_racik"),rs2.getString("aturan_pakai")
                                    });
                                    ps3=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"+
                                        "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                        "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                                    try {
                                        ps3.setString(1,rs.getString("no_resep"));
                                        ps3.setString(2,rs2.getString("no_racik"));
                                        rs3=ps3.executeQuery();
                                        while(rs3.next()){
                                            tabMode4.addRow(new Object[]{
                                                "","","   "+rs3.getString("jml")+" "+rs3.getString("kode_sat"),"   "+rs3.getString("kode_brng"),"   "+rs3.getString("nama_brng"),""
                                            });
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notifikasi 3 : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                        if(ps3!=null){
                                            ps3.close();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            i++;
                        }
                    }
                }
                    
                LCount.setText(""+i++);
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }  
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }  
    }
    
    private void tampil5() {
        if(ceksukses==false){
            ceksukses=true;
            Valid.tabelKosong(tabMode5);
            new SwingWorker<Void, Object[]>() {
                @Override
                protected Void doInBackground() {
                    try{  
                        semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
                        if(DEPOAKTIFOBAT.equals("")){
                            ps=koneksi.prepareStatement("select permintaan_stok_obat_pasien.no_permintaan,permintaan_stok_obat_pasien.tgl_permintaan,permintaan_stok_obat_pasien.jam,"+
                                    " permintaan_stok_obat_pasien.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,permintaan_stok_obat_pasien.kd_dokter,dokter.nm_dokter, "+
                                    " if(permintaan_stok_obat_pasien.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab from permintaan_stok_obat_pasien  "+
                                    " inner join reg_periksa on permintaan_stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on permintaan_stok_obat_pasien.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " where kamar_inap.stts_pulang='-' and permintaan_stok_obat_pasien.tgl_permintaan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(permintaan_stok_obat_pasien.no_permintaan like ? or permintaan_stok_obat_pasien.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by permintaan_stok_obat_pasien.no_permintaan order by permintaan_stok_obat_pasien.tgl_permintaan desc,permintaan_stok_obat_pasien.jam desc");
                        }else{
                            ps=koneksi.prepareStatement("select permintaan_stok_obat_pasien.no_permintaan,permintaan_stok_obat_pasien.tgl_permintaan,permintaan_stok_obat_pasien.jam,"+
                                    " permintaan_stok_obat_pasien.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,permintaan_stok_obat_pasien.kd_dokter,dokter.nm_dokter, "+
                                    " if(permintaan_stok_obat_pasien.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab from permintaan_stok_obat_pasien  "+
                                    " inner join reg_periksa on permintaan_stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on permintaan_stok_obat_pasien.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                                    " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and kamar_inap.stts_pulang='-' and permintaan_stok_obat_pasien.tgl_permintaan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(permintaan_stok_obat_pasien.no_permintaan like ? or permintaan_stok_obat_pasien.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by permintaan_stok_obat_pasien.no_permintaan order by permintaan_stok_obat_pasien.tgl_permintaan desc,permintaan_stok_obat_pasien.jam desc");
                        }

                        try{
                            ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                            ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                            if(!semua){
                                ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                                ps.setString(4,"%"+Kamar.getText().trim()+"%");
                                ps.setString(5,"%"+TCari.getText()+"%");
                                ps.setString(6,"%"+TCari.getText()+"%");
                                ps.setString(7,"%"+TCari.getText()+"%");
                                ps.setString(8,"%"+TCari.getText()+"%");
                                ps.setString(9,"%"+TCari.getText()+"%");
                            }

                            rs=ps.executeQuery();
                            i=0;
                            if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                                while(rs.next()){
                                    Object[] row = new Object[]{
                                        rs.getString("no_permintaan"),rs.getString("tgl_permintaan"),rs.getString("jam"),rs.getString("no_rawat"),
                                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                        rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab")
                                    };  
                                    i++;
                                    publish(row);
                                } 
                            }else{
                                while(rs.next()){
                                    if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                                        Object[] row = new Object[]{
                                            rs.getString("no_permintaan"),rs.getString("tgl_permintaan"),rs.getString("jam"),rs.getString("no_rawat"),
                                            rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                            rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab")
                                        }; 
                                        i++;
                                        publish(row);
                                    }                  
                                }
                            }               
                        } catch(Exception ex){
                            System.out.println("Notifikasi : "+ex);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }      

                        if(DEPOAKTIFOBAT.equals("")){
                            ps=koneksi.prepareStatement("select permintaan_stok_obat_pasien.no_permintaan,permintaan_stok_obat_pasien.tgl_permintaan,permintaan_stok_obat_pasien.jam,"+
                                    " permintaan_stok_obat_pasien.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,permintaan_stok_obat_pasien.kd_dokter,dokter.nm_dokter, "+
                                    " if(permintaan_stok_obat_pasien.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab from permintaan_stok_obat_pasien  "+
                                    " inner join ranap_gabung on ranap_gabung.no_rawat2=permintaan_stok_obat_pasien.no_rawat "+
                                    " inner join reg_periksa on permintaan_stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on permintaan_stok_obat_pasien.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " where kamar_inap.stts_pulang='-' and permintaan_stok_obat_pasien.tgl_permintaan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(permintaan_stok_obat_pasien.no_permintaan like ? or permintaan_stok_obat_pasien.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by permintaan_stok_obat_pasien.no_permintaan order by permintaan_stok_obat_pasien.tgl_permintaan desc,permintaan_stok_obat_pasien.jam desc");
                        }else{
                            ps=koneksi.prepareStatement("select permintaan_stok_obat_pasien.no_permintaan,permintaan_stok_obat_pasien.tgl_permintaan,permintaan_stok_obat_pasien.jam,"+
                                    " permintaan_stok_obat_pasien.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,permintaan_stok_obat_pasien.kd_dokter,dokter.nm_dokter, "+
                                    " if(permintaan_stok_obat_pasien.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab from permintaan_stok_obat_pasien  "+
                                    " inner join ranap_gabung on ranap_gabung.no_rawat2=permintaan_stok_obat_pasien.no_rawat "+
                                    " inner join reg_periksa on permintaan_stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on permintaan_stok_obat_pasien.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                                    " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and kamar_inap.stts_pulang='-' and permintaan_stok_obat_pasien.tgl_permintaan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(permintaan_stok_obat_pasien.no_permintaan like ? or permintaan_stok_obat_pasien.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by permintaan_stok_obat_pasien.no_permintaan order by permintaan_stok_obat_pasien.tgl_permintaan desc,permintaan_stok_obat_pasien.jam desc");
                        }

                        try{
                            ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                            ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                            if(!semua){
                                ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                                ps.setString(4,"%"+Kamar.getText().trim()+"%");
                                ps.setString(5,"%"+TCari.getText()+"%");
                                ps.setString(6,"%"+TCari.getText()+"%");
                                ps.setString(7,"%"+TCari.getText()+"%");
                                ps.setString(8,"%"+TCari.getText()+"%");
                                ps.setString(9,"%"+TCari.getText()+"%");
                            }

                            rs=ps.executeQuery();
                            if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                                while(rs.next()){
                                    Object[] row = new Object[]{
                                        rs.getString("no_permintaan"),rs.getString("tgl_permintaan"),rs.getString("jam"),rs.getString("no_rawat"),
                                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                        rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab")
                                    }; 
                                    i++;
                                    publish(row);
                                } 
                            }else{
                                while(rs.next()){
                                    if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                                        Object[] row = new Object[]{
                                            rs.getString("no_permintaan"),rs.getString("tgl_permintaan"),rs.getString("jam"),rs.getString("no_rawat"),
                                            rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                            rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab")
                                        };  
                                        i++;
                                        publish(row);  
                                    }                  
                                }
                            }      
                        } catch(Exception ex){
                            System.out.println("Notifikasi : "+ex);
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
                    return null;
                }
                
                @Override
                protected void process(List<Object[]> data) {
                    for (Object[] row : data) {
                        tabMode5.addRow(row);
                    }
                }

                @Override
                protected void done() {
                    LCount.setText(""+i);
                    ceksukses = false;
                }
            }.execute(); 
        }   
    }
    
    private void tampil6() {
        Valid.tabelKosong(tabMode6);
        try{  
            semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
            if(DEPOAKTIFOBAT.equals("")){
                ps=koneksi.prepareStatement("select permintaan_stok_obat_pasien.no_permintaan,permintaan_stok_obat_pasien.tgl_permintaan,permintaan_stok_obat_pasien.jam,permintaan_stok_obat_pasien.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,permintaan_stok_obat_pasien.kd_dokter,dokter.nm_dokter,if(permintaan_stok_obat_pasien.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,permintaan_stok_obat_pasien.status as status_asal,penjab.png_jawab from permintaan_stok_obat_pasien "+
                        " inner join reg_periksa on permintaan_stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on permintaan_stok_obat_pasien.kd_dokter=dokter.kd_dokter "+
                        " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " where kamar_inap.stts_pulang='-' and permintaan_stok_obat_pasien.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_stok_obat_pasien.no_permintaan like ? or permintaan_stok_obat_pasien.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by permintaan_stok_obat_pasien.no_permintaan order by permintaan_stok_obat_pasien.tgl_permintaan desc,permintaan_stok_obat_pasien.jam desc");
            }else{
                ps=koneksi.prepareStatement("select permintaan_stok_obat_pasien.no_permintaan,permintaan_stok_obat_pasien.tgl_permintaan,permintaan_stok_obat_pasien.jam,permintaan_stok_obat_pasien.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,permintaan_stok_obat_pasien.kd_dokter,dokter.nm_dokter,if(permintaan_stok_obat_pasien.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,permintaan_stok_obat_pasien.status as status_asal,penjab.png_jawab from permintaan_stok_obat_pasien "+
                        " inner join reg_periksa on permintaan_stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on permintaan_stok_obat_pasien.kd_dokter=dokter.kd_dokter "+
                        " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                        " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and kamar_inap.stts_pulang='-' and permintaan_stok_obat_pasien.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_stok_obat_pasien.no_permintaan like ? or permintaan_stok_obat_pasien.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by permintaan_stok_obat_pasien.no_permintaan order by permintaan_stok_obat_pasien.tgl_permintaan desc,permintaan_stok_obat_pasien.jam desc");
            }
                
            try{
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                i=0;
                if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                    while(rs.next()){
                        tabMode6.addRow(new Object[]{
                            rs.getString("no_permintaan"),rs.getString("tgl_permintaan")+" "+rs.getString("jam"),
                            rs.getString("nm_bangsal"),rs.getString("status"),
                            rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                            rs.getString("nm_dokter")
                        });
                        tabMode6.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                        ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detail_permintaan_stok_obat_pasien.jml,"+
                            "databarang.kode_sat,detail_permintaan_stok_obat_pasien.aturan_pakai,detail_permintaan_stok_obat_pasien.jam00,"+
                            "detail_permintaan_stok_obat_pasien.jam01,detail_permintaan_stok_obat_pasien.jam02,detail_permintaan_stok_obat_pasien.jam03,"+
                            "detail_permintaan_stok_obat_pasien.jam04,detail_permintaan_stok_obat_pasien.jam05,detail_permintaan_stok_obat_pasien.jam06,"+
                            "detail_permintaan_stok_obat_pasien.jam07,detail_permintaan_stok_obat_pasien.jam08,detail_permintaan_stok_obat_pasien.jam09,"+
                            "detail_permintaan_stok_obat_pasien.jam10,detail_permintaan_stok_obat_pasien.jam11,detail_permintaan_stok_obat_pasien.jam12,"+
                            "detail_permintaan_stok_obat_pasien.jam13,detail_permintaan_stok_obat_pasien.jam14,detail_permintaan_stok_obat_pasien.jam15,"+
                            "detail_permintaan_stok_obat_pasien.jam16,detail_permintaan_stok_obat_pasien.jam17,detail_permintaan_stok_obat_pasien.jam18,"+
                            "detail_permintaan_stok_obat_pasien.jam19,detail_permintaan_stok_obat_pasien.jam20,detail_permintaan_stok_obat_pasien.jam21,"+
                            "detail_permintaan_stok_obat_pasien.jam22,detail_permintaan_stok_obat_pasien.jam23 "+
                            "from detail_permintaan_stok_obat_pasien inner join databarang on detail_permintaan_stok_obat_pasien.kode_brng=databarang.kode_brng "+
                            "where detail_permintaan_stok_obat_pasien.no_permintaan=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,rs.getString("no_permintaan"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode6.addRow(new Object[]{
                                    "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                    "00 : "+rs2.getString("jam00").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "01 : "+rs2.getString("jam01").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "02 : "+rs2.getString("jam02").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "03 : "+rs2.getString("jam03").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "04 : "+rs2.getString("jam04").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "05 : "+rs2.getString("jam05").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "06 : "+rs2.getString("jam06").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "07 : "+rs2.getString("jam07").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "08 : "+rs2.getString("jam08").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "09 : "+rs2.getString("jam09").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "10 : "+rs2.getString("jam10").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "11 : "+rs2.getString("jam11").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "12 : "+rs2.getString("jam12").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "13 : "+rs2.getString("jam13").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "14 : "+rs2.getString("jam14").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "15 : "+rs2.getString("jam15").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "16 : "+rs2.getString("jam16").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "17 : "+rs2.getString("jam17").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "18 : "+rs2.getString("jam18").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "19 : "+rs2.getString("jam19").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "20 : "+rs2.getString("jam20").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "21 : "+rs2.getString("jam21").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "22 : "+rs2.getString("jam22").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "23 : "+rs2.getString("jam23").replaceAll("true","✓").replaceAll("false","✕")+"  |  "+
                                    rs2.getString("aturan_pakai")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        i++;
                    }
                }else{
                    while(rs.next()){
                        if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                            tabMode6.addRow(new Object[]{
                                rs.getString("no_permintaan"),rs.getString("tgl_permintaan")+" "+rs.getString("jam"),
                                rs.getString("nm_bangsal"),rs.getString("status"),
                                rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                                rs.getString("nm_dokter")
                            });
                            tabMode6.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                            ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detail_permintaan_stok_obat_pasien.jml,"+
                                "databarang.kode_sat,detail_permintaan_stok_obat_pasien.aturan_pakai,detail_permintaan_stok_obat_pasien.jam00,"+
                                "detail_permintaan_stok_obat_pasien.jam01,detail_permintaan_stok_obat_pasien.jam02,detail_permintaan_stok_obat_pasien.jam03,"+
                                "detail_permintaan_stok_obat_pasien.jam04,detail_permintaan_stok_obat_pasien.jam05,detail_permintaan_stok_obat_pasien.jam06,"+
                                "detail_permintaan_stok_obat_pasien.jam07,detail_permintaan_stok_obat_pasien.jam08,detail_permintaan_stok_obat_pasien.jam09,"+
                                "detail_permintaan_stok_obat_pasien.jam10,detail_permintaan_stok_obat_pasien.jam11,detail_permintaan_stok_obat_pasien.jam12,"+
                                "detail_permintaan_stok_obat_pasien.jam13,detail_permintaan_stok_obat_pasien.jam14,detail_permintaan_stok_obat_pasien.jam15,"+
                                "detail_permintaan_stok_obat_pasien.jam16,detail_permintaan_stok_obat_pasien.jam17,detail_permintaan_stok_obat_pasien.jam18,"+
                                "detail_permintaan_stok_obat_pasien.jam19,detail_permintaan_stok_obat_pasien.jam20,detail_permintaan_stok_obat_pasien.jam21,"+
                                "detail_permintaan_stok_obat_pasien.jam22,detail_permintaan_stok_obat_pasien.jam23 from detail_permintaan_stok_obat_pasien inner join databarang on "+
                                "detail_permintaan_stok_obat_pasien.kode_brng=databarang.kode_brng where detail_permintaan_stok_obat_pasien.no_permintaan=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1,rs.getString("no_permintaan"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode6.addRow(new Object[]{
                                        "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        "00 : "+rs2.getString("jam00").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "01 : "+rs2.getString("jam01").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "02 : "+rs2.getString("jam02").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "03 : "+rs2.getString("jam03").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "04 : "+rs2.getString("jam04").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "05 : "+rs2.getString("jam05").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "06 : "+rs2.getString("jam06").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "07 : "+rs2.getString("jam07").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "08 : "+rs2.getString("jam08").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "09 : "+rs2.getString("jam09").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "10 : "+rs2.getString("jam10").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "11 : "+rs2.getString("jam11").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "12 : "+rs2.getString("jam12").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "13 : "+rs2.getString("jam13").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "14 : "+rs2.getString("jam14").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "15 : "+rs2.getString("jam15").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "16 : "+rs2.getString("jam16").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "17 : "+rs2.getString("jam17").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "18 : "+rs2.getString("jam18").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "19 : "+rs2.getString("jam19").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "20 : "+rs2.getString("jam20").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "21 : "+rs2.getString("jam21").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "22 : "+rs2.getString("jam22").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "23 : "+rs2.getString("jam23").replaceAll("true","✓").replaceAll("false","✕")+"  |  "+
                                        rs2.getString("aturan_pakai")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            i++;
                        }
                    }
                }
                    
                LCount.setText(""+i++);
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }             
            
            if(DEPOAKTIFOBAT.equals("")){
                ps=koneksi.prepareStatement("select permintaan_stok_obat_pasien.no_permintaan,permintaan_stok_obat_pasien.tgl_permintaan,permintaan_stok_obat_pasien.jam,permintaan_stok_obat_pasien.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,permintaan_stok_obat_pasien.kd_dokter,dokter.nm_dokter,if(permintaan_stok_obat_pasien.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,permintaan_stok_obat_pasien.status as status_asal,penjab.png_jawab from permintaan_stok_obat_pasien "+
                        " inner join ranap_gabung on ranap_gabung.no_rawat2=permintaan_stok_obat_pasien.no_rawat "+
                        " inner join reg_periksa on permintaan_stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on permintaan_stok_obat_pasien.kd_dokter=dokter.kd_dokter "+
                        " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " where kamar_inap.stts_pulang='-' and permintaan_stok_obat_pasien.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_stok_obat_pasien.no_permintaan like ? or permintaan_stok_obat_pasien.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by permintaan_stok_obat_pasien.no_permintaan order by permintaan_stok_obat_pasien.tgl_permintaan desc,permintaan_stok_obat_pasien.jam desc");
            }else{
                ps=koneksi.prepareStatement("select permintaan_stok_obat_pasien.no_permintaan,permintaan_stok_obat_pasien.tgl_permintaan,permintaan_stok_obat_pasien.jam,permintaan_stok_obat_pasien.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,permintaan_stok_obat_pasien.kd_dokter,dokter.nm_dokter,if(permintaan_stok_obat_pasien.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,permintaan_stok_obat_pasien.status as status_asal,penjab.png_jawab from permintaan_stok_obat_pasien "+
                        " inner join ranap_gabung on ranap_gabung.no_rawat2=permintaan_stok_obat_pasien.no_rawat "+
                        " inner join reg_periksa on permintaan_stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on permintaan_stok_obat_pasien.kd_dokter=dokter.kd_dokter "+
                        " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                        " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and kamar_inap.stts_pulang='-' and permintaan_stok_obat_pasien.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_stok_obat_pasien.no_permintaan like ? or permintaan_stok_obat_pasien.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by permintaan_stok_obat_pasien.no_permintaan order by permintaan_stok_obat_pasien.tgl_permintaan desc,permintaan_stok_obat_pasien.jam desc");
            }
                
            try{
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                i=0;
                if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                    while(rs.next()){
                        tabMode6.addRow(new Object[]{
                            rs.getString("no_permintaan"),rs.getString("tgl_permintaan")+" "+rs.getString("jam"),
                            rs.getString("nm_bangsal"),rs.getString("status"),
                            rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                            rs.getString("nm_dokter")
                        });
                        tabMode6.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                        ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detail_permintaan_stok_obat_pasien.jml,"+
                            "databarang.kode_sat,detail_permintaan_stok_obat_pasien.aturan_pakai,detail_permintaan_stok_obat_pasien.jam00,"+
                            "detail_permintaan_stok_obat_pasien.jam01,detail_permintaan_stok_obat_pasien.jam02,detail_permintaan_stok_obat_pasien.jam03,"+
                            "detail_permintaan_stok_obat_pasien.jam04,detail_permintaan_stok_obat_pasien.jam05,detail_permintaan_stok_obat_pasien.jam06,"+
                            "detail_permintaan_stok_obat_pasien.jam07,detail_permintaan_stok_obat_pasien.jam08,detail_permintaan_stok_obat_pasien.jam09,"+
                            "detail_permintaan_stok_obat_pasien.jam10,detail_permintaan_stok_obat_pasien.jam11,detail_permintaan_stok_obat_pasien.jam12,"+
                            "detail_permintaan_stok_obat_pasien.jam13,detail_permintaan_stok_obat_pasien.jam14,detail_permintaan_stok_obat_pasien.jam15,"+
                            "detail_permintaan_stok_obat_pasien.jam16,detail_permintaan_stok_obat_pasien.jam17,detail_permintaan_stok_obat_pasien.jam18,"+
                            "detail_permintaan_stok_obat_pasien.jam19,detail_permintaan_stok_obat_pasien.jam20,detail_permintaan_stok_obat_pasien.jam21,"+
                            "detail_permintaan_stok_obat_pasien.jam22,detail_permintaan_stok_obat_pasien.jam23 from detail_permintaan_stok_obat_pasien inner join databarang on "+
                            "detail_permintaan_stok_obat_pasien.kode_brng=databarang.kode_brng where detail_permintaan_stok_obat_pasien.no_permintaan=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,rs.getString("no_permintaan"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode6.addRow(new Object[]{
                                    "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                    "00 : "+rs2.getString("jam00").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "01 : "+rs2.getString("jam01").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "02 : "+rs2.getString("jam02").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "03 : "+rs2.getString("jam03").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "04 : "+rs2.getString("jam04").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "05 : "+rs2.getString("jam05").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "06 : "+rs2.getString("jam06").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "07 : "+rs2.getString("jam07").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "08 : "+rs2.getString("jam08").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "09 : "+rs2.getString("jam09").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "10 : "+rs2.getString("jam10").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "11 : "+rs2.getString("jam11").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "12 : "+rs2.getString("jam12").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "13 : "+rs2.getString("jam13").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "14 : "+rs2.getString("jam14").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "15 : "+rs2.getString("jam15").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "16 : "+rs2.getString("jam16").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "17 : "+rs2.getString("jam17").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "18 : "+rs2.getString("jam18").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "19 : "+rs2.getString("jam19").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "20 : "+rs2.getString("jam20").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "21 : "+rs2.getString("jam21").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "22 : "+rs2.getString("jam22").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                    "23 : "+rs2.getString("jam23").replaceAll("true","✓").replaceAll("false","✕")+"  |  "+
                                    rs2.getString("aturan_pakai")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        i++;
                    }
                }else{
                    while(rs.next()){
                        if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                            tabMode6.addRow(new Object[]{
                                rs.getString("no_permintaan"),rs.getString("tgl_permintaan")+" "+rs.getString("jam"),
                                rs.getString("nm_bangsal"),rs.getString("status"),
                                rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                                rs.getString("nm_dokter")
                            });
                            tabMode6.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                            ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detail_permintaan_stok_obat_pasien.jml,"+
                                "databarang.kode_sat,detail_permintaan_stok_obat_pasien.aturan_pakai,detail_permintaan_stok_obat_pasien.jam00,"+
                                "detail_permintaan_stok_obat_pasien.jam01,detail_permintaan_stok_obat_pasien.jam02,detail_permintaan_stok_obat_pasien.jam03,"+
                                "detail_permintaan_stok_obat_pasien.jam04,detail_permintaan_stok_obat_pasien.jam05,detail_permintaan_stok_obat_pasien.jam06,"+
                                "detail_permintaan_stok_obat_pasien.jam07,detail_permintaan_stok_obat_pasien.jam08,detail_permintaan_stok_obat_pasien.jam09,"+
                                "detail_permintaan_stok_obat_pasien.jam10,detail_permintaan_stok_obat_pasien.jam11,detail_permintaan_stok_obat_pasien.jam12,"+
                                "detail_permintaan_stok_obat_pasien.jam13,detail_permintaan_stok_obat_pasien.jam14,detail_permintaan_stok_obat_pasien.jam15,"+
                                "detail_permintaan_stok_obat_pasien.jam16,detail_permintaan_stok_obat_pasien.jam17,detail_permintaan_stok_obat_pasien.jam18,"+
                                "detail_permintaan_stok_obat_pasien.jam19,detail_permintaan_stok_obat_pasien.jam20,detail_permintaan_stok_obat_pasien.jam21,"+
                                "detail_permintaan_stok_obat_pasien.jam22,detail_permintaan_stok_obat_pasien.jam23 from detail_permintaan_stok_obat_pasien inner join databarang on "+
                                "detail_permintaan_stok_obat_pasien.kode_brng=databarang.kode_brng where detail_permintaan_stok_obat_pasien.no_permintaan=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1,rs.getString("no_permintaan"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode6.addRow(new Object[]{
                                        "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        "00 : "+rs2.getString("jam00").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "01 : "+rs2.getString("jam01").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "02 : "+rs2.getString("jam02").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "03 : "+rs2.getString("jam03").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "04 : "+rs2.getString("jam04").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "05 : "+rs2.getString("jam05").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "06 : "+rs2.getString("jam06").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "07 : "+rs2.getString("jam07").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "08 : "+rs2.getString("jam08").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "09 : "+rs2.getString("jam09").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "10 : "+rs2.getString("jam10").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "11 : "+rs2.getString("jam11").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "12 : "+rs2.getString("jam12").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "13 : "+rs2.getString("jam13").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "14 : "+rs2.getString("jam14").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "15 : "+rs2.getString("jam15").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "16 : "+rs2.getString("jam16").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "17 : "+rs2.getString("jam17").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "18 : "+rs2.getString("jam18").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "19 : "+rs2.getString("jam19").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "20 : "+rs2.getString("jam20").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "21 : "+rs2.getString("jam21").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "22 : "+rs2.getString("jam22").replaceAll("true","✓").replaceAll("false","✕")+"  "+
                                        "23 : "+rs2.getString("jam23").replaceAll("true","✓").replaceAll("false","✕")+"  |  "+
                                        rs2.getString("aturan_pakai")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            i++;
                        }
                    }
                }
                    
                LCount.setText(""+i++);
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }   
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }  
    }
    
    private void tampil7() {
        if(ceksukses==false){
            ceksukses=true;
            Valid.tabelKosong(tabMode7);
            new SwingWorker<Void, Object[]>() {
                @Override
                protected Void doInBackground() {
                    try{  
                        semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
                        if(DEPOAKTIFOBAT.equals("")){
                            ps=koneksi.prepareStatement("select permintaan_resep_pulang.no_permintaan,permintaan_resep_pulang.tgl_permintaan,permintaan_resep_pulang.jam,"+
                                    " permintaan_resep_pulang.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,permintaan_resep_pulang.kd_dokter,dokter.nm_dokter, "+
                                    " if(permintaan_resep_pulang.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab from permintaan_resep_pulang  "+
                                    " inner join reg_periksa on permintaan_resep_pulang.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on permintaan_resep_pulang.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " where kamar_inap.stts_pulang='-' and permintaan_resep_pulang.tgl_permintaan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(permintaan_resep_pulang.no_permintaan like ? or permintaan_resep_pulang.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by permintaan_resep_pulang.no_permintaan order by permintaan_resep_pulang.tgl_permintaan desc,permintaan_resep_pulang.jam desc");
                        }else{
                            ps=koneksi.prepareStatement("select permintaan_resep_pulang.no_permintaan,permintaan_resep_pulang.tgl_permintaan,permintaan_resep_pulang.jam,"+
                                    " permintaan_resep_pulang.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,permintaan_resep_pulang.kd_dokter,dokter.nm_dokter, "+
                                    " if(permintaan_resep_pulang.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab from permintaan_resep_pulang  "+
                                    " inner join reg_periksa on permintaan_resep_pulang.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on permintaan_resep_pulang.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                                    " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and kamar_inap.stts_pulang='-' and permintaan_resep_pulang.tgl_permintaan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(permintaan_resep_pulang.no_permintaan like ? or permintaan_resep_pulang.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by permintaan_resep_pulang.no_permintaan order by permintaan_resep_pulang.tgl_permintaan desc,permintaan_resep_pulang.jam desc");
                        }

                        try{
                            ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                            ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                            if(!semua){
                                ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                                ps.setString(4,"%"+Kamar.getText().trim()+"%");
                                ps.setString(5,"%"+TCari.getText()+"%");
                                ps.setString(6,"%"+TCari.getText()+"%");
                                ps.setString(7,"%"+TCari.getText()+"%");
                                ps.setString(8,"%"+TCari.getText()+"%");
                                ps.setString(9,"%"+TCari.getText()+"%");
                            }

                            rs=ps.executeQuery();
                            i=0;
                            if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                                while(rs.next()){
                                    Object[] row = new Object[]{
                                        rs.getString("no_permintaan"),rs.getString("tgl_permintaan"),rs.getString("jam"),rs.getString("no_rawat"),
                                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                        rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab")
                                    }; 
                                    i++;
                                    publish(row);
                                }
                            }else{
                                while(rs.next()){
                                    if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                                        Object[] row = new Object[]{
                                            rs.getString("no_permintaan"),rs.getString("tgl_permintaan"),rs.getString("jam"),rs.getString("no_rawat"),
                                            rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                            rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab")
                                        }; 
                                        i++;
                                        publish(row);  
                                    }                  
                                } 
                            }               
                        } catch(Exception ex){
                            System.out.println("Notifikasi : "+ex);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }      

                        if(DEPOAKTIFOBAT.equals("")){
                            ps=koneksi.prepareStatement("select permintaan_resep_pulang.no_permintaan,permintaan_resep_pulang.tgl_permintaan,permintaan_resep_pulang.jam,"+
                                    " permintaan_resep_pulang.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,permintaan_resep_pulang.kd_dokter,dokter.nm_dokter, "+
                                    " if(permintaan_resep_pulang.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab from permintaan_resep_pulang  "+
                                    " inner join ranap_gabung on ranap_gabung.no_rawat2=permintaan_resep_pulang.no_rawat "+
                                    " inner join reg_periksa on permintaan_resep_pulang.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on permintaan_resep_pulang.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " where kamar_inap.stts_pulang='-' and permintaan_resep_pulang.tgl_permintaan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(permintaan_resep_pulang.no_permintaan like ? or permintaan_resep_pulang.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by permintaan_resep_pulang.no_permintaan order by permintaan_resep_pulang.tgl_permintaan desc,permintaan_resep_pulang.jam desc");
                        }else{
                            ps=koneksi.prepareStatement("select permintaan_resep_pulang.no_permintaan,permintaan_resep_pulang.tgl_permintaan,permintaan_resep_pulang.jam,"+
                                    " permintaan_resep_pulang.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,permintaan_resep_pulang.kd_dokter,dokter.nm_dokter, "+
                                    " if(permintaan_resep_pulang.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                                    " bangsal.nm_bangsal,kamar.kd_bangsal,penjab.png_jawab from permintaan_resep_pulang  "+
                                    " inner join ranap_gabung on ranap_gabung.no_rawat2=permintaan_resep_pulang.no_rawat "+
                                    " inner join reg_periksa on permintaan_resep_pulang.no_rawat=reg_periksa.no_rawat "+
                                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    " inner join dokter on permintaan_resep_pulang.kd_dokter=dokter.kd_dokter "+
                                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                    " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                                    " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                                    " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and kamar_inap.stts_pulang='-' and permintaan_resep_pulang.tgl_permintaan between ? and ? "+
                                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                                    "(permintaan_resep_pulang.no_permintaan like ? or permintaan_resep_pulang.no_rawat like ? or "+
                                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                                    " group by permintaan_resep_pulang.no_permintaan order by permintaan_resep_pulang.tgl_permintaan desc,permintaan_resep_pulang.jam desc");
                        }

                        try{
                            ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                            ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                            if(!semua){
                                ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                                ps.setString(4,"%"+Kamar.getText().trim()+"%");
                                ps.setString(5,"%"+TCari.getText()+"%");
                                ps.setString(6,"%"+TCari.getText()+"%");
                                ps.setString(7,"%"+TCari.getText()+"%");
                                ps.setString(8,"%"+TCari.getText()+"%");
                                ps.setString(9,"%"+TCari.getText()+"%");
                            }

                            rs=ps.executeQuery();
                            if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                                while(rs.next()){
                                    Object[] row = new Object[]{
                                        rs.getString("no_permintaan"),rs.getString("tgl_permintaan"),rs.getString("jam"),rs.getString("no_rawat"),
                                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                        rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab")
                                    }; 
                                    i++;
                                    publish(row);           
                                }
                            }else{
                                while(rs.next()){
                                    if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                                        Object[] row = new Object[]{
                                            rs.getString("no_permintaan"),rs.getString("tgl_permintaan"),rs.getString("jam"),rs.getString("no_rawat"),
                                            rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("status"),
                                            rs.getString("kd_dokter"),rs.getString("nm_bangsal"),rs.getString("kd_bangsal"),rs.getString("png_jawab")
                                        }; 
                                        i++;
                                        publish(row);   
                                    }                  
                                } 
                            }
                        } catch(Exception ex){
                            System.out.println("Notifikasi : "+ex);
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
                    return null;
                }
                
                @Override
                protected void process(List<Object[]> data) {
                    for (Object[] row : data) {
                        tabMode7.addRow(row);
                    }
                }

                @Override
                protected void done() {
                    LCount.setText(""+i);
                    ceksukses = false;
                }
            }.execute();
        }   
    }
    
    private void tampil8() {
        Valid.tabelKosong(tabMode8);
        try{  
            semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
            if(DEPOAKTIFOBAT.equals("")){
                ps=koneksi.prepareStatement("select permintaan_resep_pulang.no_permintaan,permintaan_resep_pulang.tgl_permintaan,permintaan_resep_pulang.jam,permintaan_resep_pulang.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,permintaan_resep_pulang.kd_dokter,dokter.nm_dokter,if(permintaan_resep_pulang.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,permintaan_resep_pulang.status as status_asal,penjab.png_jawab from permintaan_resep_pulang "+
                        " inner join reg_periksa on permintaan_resep_pulang.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on permintaan_resep_pulang.kd_dokter=dokter.kd_dokter "+
                        " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " where kamar_inap.stts_pulang='-' and permintaan_resep_pulang.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_resep_pulang.no_permintaan like ? or permintaan_resep_pulang.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by permintaan_resep_pulang.no_permintaan order by permintaan_resep_pulang.tgl_permintaan desc,permintaan_resep_pulang.jam desc");
            }else{
                ps=koneksi.prepareStatement("select permintaan_resep_pulang.no_permintaan,permintaan_resep_pulang.tgl_permintaan,permintaan_resep_pulang.jam,permintaan_resep_pulang.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,permintaan_resep_pulang.kd_dokter,dokter.nm_dokter,if(permintaan_resep_pulang.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,permintaan_resep_pulang.status as status_asal,penjab.png_jawab from permintaan_resep_pulang "+
                        " inner join reg_periksa on permintaan_resep_pulang.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on permintaan_resep_pulang.kd_dokter=dokter.kd_dokter "+
                        " inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                        " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and kamar_inap.stts_pulang='-' and permintaan_resep_pulang.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_resep_pulang.no_permintaan like ? or permintaan_resep_pulang.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by permintaan_resep_pulang.no_permintaan order by permintaan_resep_pulang.tgl_permintaan desc,permintaan_resep_pulang.jam desc");
            }
                
            try{
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                i=0;
                if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                    while(rs.next()){
                        tabMode8.addRow(new Object[]{
                            rs.getString("no_permintaan"),rs.getString("tgl_permintaan")+" "+rs.getString("jam"),
                            rs.getString("nm_bangsal"),rs.getString("status"),
                            rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                            rs.getString("nm_dokter")
                        });
                        tabMode8.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                        ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detail_permintaan_resep_pulang.jml,"+
                            "databarang.kode_sat,detail_permintaan_resep_pulang.dosis "+
                            "from detail_permintaan_resep_pulang inner join databarang on detail_permintaan_resep_pulang.kode_brng=databarang.kode_brng "+
                            "where detail_permintaan_resep_pulang.no_permintaan=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,rs.getString("no_permintaan"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode8.addRow(new Object[]{
                                    "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("dosis")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        i++;
                    }
                }else{
                    while(rs.next()){
                        if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                            tabMode8.addRow(new Object[]{
                                rs.getString("no_permintaan"),rs.getString("tgl_permintaan")+" "+rs.getString("jam"),
                                rs.getString("nm_bangsal"),rs.getString("status"),
                                rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                                rs.getString("nm_dokter")
                            });
                            tabMode8.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                            ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detail_permintaan_resep_pulang.jml,"+
                                "databarang.kode_sat,detail_permintaan_resep_pulang.dosis from detail_permintaan_resep_pulang inner join databarang on "+
                                "detail_permintaan_resep_pulang.kode_brng=databarang.kode_brng where detail_permintaan_resep_pulang.no_permintaan=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1,rs.getString("no_permintaan"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode8.addRow(new Object[]{
                                        "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("dosis")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            i++;
                        }
                    }
                }
                    
                LCount.setText(""+i++);
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }             
            
            if(DEPOAKTIFOBAT.equals("")){
                ps=koneksi.prepareStatement("select permintaan_resep_pulang.no_permintaan,permintaan_resep_pulang.tgl_permintaan,permintaan_resep_pulang.jam,permintaan_resep_pulang.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,permintaan_resep_pulang.kd_dokter,dokter.nm_dokter,if(permintaan_resep_pulang.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,permintaan_resep_pulang.status as status_asal,penjab.png_jawab from permintaan_resep_pulang "+
                        " inner join ranap_gabung on ranap_gabung.no_rawat2=permintaan_resep_pulang.no_rawat "+
                        " inner join reg_periksa on permintaan_resep_pulang.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on permintaan_resep_pulang.kd_dokter=dokter.kd_dokter "+
                        " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " where kamar_inap.stts_pulang='-' and permintaan_resep_pulang.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_resep_pulang.no_permintaan like ? or permintaan_resep_pulang.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by permintaan_resep_pulang.no_permintaan order by permintaan_resep_pulang.tgl_permintaan desc,permintaan_resep_pulang.jam desc");
            }else{
                ps=koneksi.prepareStatement("select permintaan_resep_pulang.no_permintaan,permintaan_resep_pulang.tgl_permintaan,permintaan_resep_pulang.jam,permintaan_resep_pulang.no_rawat,pasien.no_rkm_medis,"+
                        " pasien.nm_pasien,permintaan_resep_pulang.kd_dokter,dokter.nm_dokter,if(permintaan_resep_pulang.status='Belum','Belum Terlayani','Sudah Terlayani') as status,"+
                        " bangsal.nm_bangsal,permintaan_resep_pulang.status as status_asal,penjab.png_jawab from permintaan_resep_pulang "+
                        " inner join ranap_gabung on ranap_gabung.no_rawat2=permintaan_resep_pulang.no_rawat "+
                        " inner join reg_periksa on permintaan_resep_pulang.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join dokter on permintaan_resep_pulang.kd_dokter=dokter.kd_dokter "+
                        " inner join kamar_inap on ranap_gabung.no_rawat=kamar_inap.no_rawat "+
                        " inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                        " inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        " inner join set_depo_ranap on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                        " where set_depo_ranap.kd_depo='"+DEPOAKTIFOBAT+"' and kamar_inap.stts_pulang='-' and permintaan_resep_pulang.tgl_permintaan between ? and ? "+
                        (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                        "(permintaan_resep_pulang.no_permintaan like ? or permintaan_resep_pulang.no_rawat like ? or "+
                        "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penjab.png_jawab like ?)")+
                        " group by permintaan_resep_pulang.no_permintaan order by permintaan_resep_pulang.tgl_permintaan desc,permintaan_resep_pulang.jam desc");
            }
                
            try{
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                i=0;
                if(cmbStatus.getSelectedItem().toString().equals("Semua")){
                    while(rs.next()){
                        tabMode8.addRow(new Object[]{
                            rs.getString("no_permintaan"),rs.getString("tgl_permintaan")+" "+rs.getString("jam"),
                            rs.getString("nm_bangsal"),rs.getString("status"),
                            rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                            rs.getString("nm_dokter")
                        });
                        tabMode8.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                        ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detail_permintaan_resep_pulang.jml,"+
                            "databarang.kode_sat,detail_permintaan_resep_pulang.dosis from detail_permintaan_resep_pulang inner join databarang on "+
                            "detail_permintaan_resep_pulang.kode_brng=databarang.kode_brng where detail_permintaan_resep_pulang.no_permintaan=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,rs.getString("no_permintaan"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode8.addRow(new Object[]{
                                    "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("dosis")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        i++;
                    }
                }else{
                    while(rs.next()){
                        if(rs.getString("status").equals(cmbStatus.getSelectedItem().toString())){
                            tabMode8.addRow(new Object[]{
                                rs.getString("no_permintaan"),rs.getString("tgl_permintaan")+" "+rs.getString("jam"),
                                rs.getString("nm_bangsal"),rs.getString("status"),
                                rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")",
                                rs.getString("nm_dokter")
                            });
                            tabMode8.addRow(new Object[]{"","","Jumlah","Kode Obat","Nama Obat","Aturan Pakai"});                
                            ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detail_permintaan_resep_pulang.jml,"+
                                "databarang.kode_sat,detail_permintaan_resep_pulang.dosis from detail_permintaan_resep_pulang inner join databarang on "+
                                "detail_permintaan_resep_pulang.kode_brng=databarang.kode_brng where detail_permintaan_resep_pulang.no_permintaan=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1,rs.getString("no_permintaan"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode8.addRow(new Object[]{
                                        "","",rs2.getString("jml")+" "+rs2.getString("kode_sat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("dosis")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            i++;
                        }
                    }
                }
                    
                LCount.setText(""+i++);
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }   
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
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
                    if(formalarm.contains("ralan")){
                        tampil();
                    }
                }else if(detik.equals("15")){
                    if(formalarm.contains("ranap")){
                        tampil3();
                    }
                }else if(detik.equals("25")){
                    if(formalarm.contains("ranap")){
                        tampil5();
                    }
                }else if(detik.equals("35")){
                    if(formalarm.contains("ranap")){
                        tampil7();
                    }
                }else if(detik.equals("45")){
                    resepbaru=0;
                    if(formalarm.contains("ralan")){
                        for(i=0;i<tbResepRalan.getRowCount();i++){
                            if(tbResepRalan.getValueAt(i,7).toString().equals("Belum Terlayani")){
                                resepbaru++;
                            }
                        }
                    }

                    if(formalarm.contains("ranap")){
                        for(i=0;i<tbResepRanap.getRowCount();i++){
                            if(tbResepRanap.getValueAt(i,7).toString().equals("Belum Terlayani")){
                                resepbaru++;
                            }
                        }
                        for(i=0;i<tbPermintaanStok.getRowCount();i++){
                            if(tbPermintaanStok.getValueAt(i,7).toString().equals("Belum Terlayani")){
                                resepbaru++;
                            }
                        }
                        for(i=0;i<tbPermintaanResepPulang.getRowCount();i++){
                            if(tbPermintaanResepPulang.getValueAt(i,7).toString().equals("Belum Terlayani")){
                                resepbaru++;
                            }
                        }
                    }

                    if(resepbaru>0){
                        try {
                            music = new BackgroundMusic("./suara/alarm.mp3");
                            music.start();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }                
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(155,HEIGHT));
            FormMenu.setVisible(true); 
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){  
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);    
            ChkAccor.setVisible(true);
        }
    }
}

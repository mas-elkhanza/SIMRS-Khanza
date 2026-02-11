/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import fungsi.sekuel;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

/**
 *
 * @author dosen
 */
public final class MasterCariTemplatePemeriksaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDiagnosa,tabModeProsedur,tabModeRadiologi,tabModePK,tabModeDetailPK,
                tabModePA,tabModeMB,tabModeDetailMB,tabModeObatUmum,tabModeObatRacikan,tabModeDetailObatRacikan,
                TabModeTindakan;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0;
    private String la="",ld="",pa="",pd="",kodedokter="",tanggaldilakukan="",jamdilakukan="",noperawatan="",norm="",nomor="",
            Suspen_Piutang_Tindakan_Ralan="",Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",
            Beban_KSO_Tindakan_Ralan="",Utang_KSO_Tindakan_Ralan="",Beban_Jasa_Sarana_Tindakan_Ralan="",Utang_Jasa_Sarana_Tindakan_Ralan="",
            HPP_BHP_Tindakan_Ralan="",Persediaan_BHP_Tindakan_Ralan="",Beban_Jasa_Menejemen_Tindakan_Ralan="",Utang_Jasa_Menejemen_Tindakan_Ralan="";
    private boolean sukses=true;
    private double ttljmdokter=0,ttlkso=0,ttljasasarana=0,ttlbhp=0,ttlmenejemen=0,ttlpendapatan=0;
    private Jurnal jur=new Jurnal();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public MasterCariTemplatePemeriksaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"No.Template","Kode Dokter","Nama Dokter","Subjek","Objek","Asesmen","Plan","Instruksi","Evaluasi"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(200);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDiagnosa=new DefaultTableModel(null,new Object[]{"Kode","Nama Penyakit","Ciri-ciri Penyakit","Keterangan","Ktg.Penyakit","Ciri-ciri Umum","Urut"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDiagnosa.setModel(tabModeDiagnosa);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbDiagnosa.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDiagnosa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i= 0; i < 7; i++) {
            TableColumn column = tbDiagnosa.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(232);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(85);
            }else if(i==6){
                column.setPreferredWidth(30);
            }
        }
        tbDiagnosa.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeProsedur=new DefaultTableModel(null,new Object[]{"Kode","Deskripsi Panjang","Deskripsi Pendek","Urut","Jml"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbProsedur.setModel(tabModeProsedur);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbProsedur.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbProsedur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbProsedur.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(372);
            }else if(i==2){
                column.setPreferredWidth(210);
            }else if(i==3){
                column.setPreferredWidth(30);
            }else if(i==4){
                column.setPreferredWidth(25);
            }
        }
        tbProsedur.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRadiologi=new DefaultTableModel(null,new Object[]{"Kode Periksa","Nama Pemeriksaan"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanRadiologi.setModel(tabModeRadiologi);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPermintaanRadiologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 2; i++) {
            TableColumn column = tbPermintaanRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(490);
            }
        }
        tbPermintaanRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePK=new DefaultTableModel(null,new Object[]{"Kode Periksa","Nama Pemeriksaan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanPK.setModel(tabModePK);

        tbPermintaanPK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 2; i++) {
            TableColumn column = tbPermintaanPK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanPK.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailPK=new DefaultTableModel(null,new Object[]{"Pemeriksaan","Satuan","Nilai Rujukan","id_template","Kode Jenis"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }              
        };
        
        tbDetailPK.setModel(tabModeDetailPK);
        tbDetailPK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbDetailPK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(326);
            }else if(i==1){
                column.setPreferredWidth(50);
            }else if(i==2){
                column.setPreferredWidth(315);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }
        }
        
        tbDetailPK.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePA=new DefaultTableModel(null,new Object[]{"Kode Periksa","Nama Pemeriksaan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanPA.setModel(tabModePA);

        tbPermintaanPA.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanPA.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 2; i++) {
            TableColumn column = tbPermintaanPA.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanPA.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeMB=new DefaultTableModel(null,new Object[]{"Kode Periksa","Nama Pemeriksaan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanMB.setModel(tabModeMB);

        tbPermintaanMB.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanMB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(i = 0; i < 2; i++) {
            TableColumn column = tbPermintaanMB.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanMB.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMB=new DefaultTableModel(null,new Object[]{"Pemeriksaan","Satuan","Nilai Rujukan","id_template","Kode Jenis"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }              
        };
        
        tbDetailMB.setModel(tabModeDetailMB);
        tbDetailMB.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailMB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbDetailMB.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(326);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);  
            }else if(i==2){
                column.setPreferredWidth(315);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }
        }
        tbDetailMB.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeObatUmum=new DefaultTableModel(null,new Object[]{
                "Jumlah","Kode Barang","Nama Barang","Satuan","Komposisi","Jenis Obat","Aturan Pakai","I.F.","Kapasitas"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObatNonRacikan.setModel(tabModeObatUmum);
        tbObatNonRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatNonRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 9; i++) {
            TableColumn column = tbObatNonRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(45);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(130);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }                
        }
        tbObatNonRacikan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeObatRacikan=new DefaultTableModel(null,new Object[]{"No","Nama Racikan","Kode Racik","Metode Racik","Jml.Racik","Aturan Pakai","Keterangan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObatRacikan.setModel(tabModeObatRacikan);
        tbObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(250);
            }
        }
        tbObatRacikan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Kode Barang","Nama Barang","Satuan","Jenis Obat","Kps","P1","/","P2","Kandungan","Jml","I.F.","Komposisi"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}            
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        tbDetailObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 13; i++) {
            TableColumn column = tbDetailObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(45);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(25);
            }else if(i==7){
                column.setMinWidth(11);
                column.setMaxWidth(11);
            }else if(i==8){
                column.setPreferredWidth(25);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==10){
                column.setPreferredWidth(40);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(150);
            }
        }

        tbDetailObatRacikan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakan=new DefaultTableModel(null,new Object[]{
                "Kode","Nama Perawatan/Tindakan","Kategori","Tarif/Biaya","Bagian RS","BHP","JM Dokter","KSO","Menejemen"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan.setModel(TabModeTindakan);
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 9; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(380);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
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
        Scroll = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnTambah = new widget.Button();
        BtnKeluar = new widget.Button();
        scrollPane2 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel40 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Subjek = new widget.TextArea();
        jLabel41 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Objek = new widget.TextArea();
        jLabel42 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Asesmen = new widget.TextArea();
        jLabel43 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Plan = new widget.TextArea();
        jLabel44 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        Instruksi = new widget.TextArea();
        jLabel45 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        Evaluasi = new widget.TextArea();
        Scroll1 = new widget.ScrollPane();
        tbDiagnosa = new widget.Table();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        Scroll2 = new widget.ScrollPane();
        tbProsedur = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbPermintaanRadiologi = new widget.Table();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        Scroll4 = new widget.ScrollPane();
        tbPermintaanPK = new widget.Table();
        Scroll5 = new widget.ScrollPane();
        tbDetailPK = new widget.Table();
        jLabel17 = new widget.Label();
        Scroll6 = new widget.ScrollPane();
        tbPermintaanPA = new widget.Table();
        jLabel18 = new widget.Label();
        Scroll7 = new widget.ScrollPane();
        tbPermintaanMB = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbDetailMB = new widget.Table();
        jLabel19 = new widget.Label();
        Scroll9 = new widget.ScrollPane();
        tbObatNonRacikan = new widget.Table();
        jLabel20 = new widget.Label();
        Scroll10 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        Scroll11 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();
        jLabel21 = new widget.Label();
        Scroll12 = new widget.ScrollPane();
        tbTindakan = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Template Pemeriksaan Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(310, 402));

        tbDokter.setAutoCreateRowSorter(true);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tbDokter);

        internalFrame1.add(Scroll, java.awt.BorderLayout.WEST);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(312, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
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
        panelisi3.add(BtnAll);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi3.add(BtnSimpan);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(LCount);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        scrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), "Detail Template :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane2.setName("scrollPane2"); // NOI18N

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(730, 2733));
        FormInput.setLayout(null);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Subjek :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(16, 10, 410, 20);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Subjek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Subjek.setColumns(20);
        Subjek.setRows(7);
        Subjek.setName("Subjek"); // NOI18N
        scrollPane3.setViewportView(Subjek);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(16, 30, 700, 73);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Objek :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(16, 110, 410, 20);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Objek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Objek.setColumns(20);
        Objek.setRows(7);
        Objek.setName("Objek"); // NOI18N
        scrollPane4.setViewportView(Objek);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(16, 130, 700, 73);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Asesmen :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(16, 210, 410, 20);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Asesmen.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Asesmen.setColumns(20);
        Asesmen.setRows(7);
        Asesmen.setName("Asesmen"); // NOI18N
        scrollPane5.setViewportView(Asesmen);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(16, 230, 700, 73);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Plan :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(16, 310, 410, 20);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Plan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Plan.setColumns(20);
        Plan.setRows(7);
        Plan.setName("Plan"); // NOI18N
        scrollPane6.setViewportView(Plan);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(16, 330, 700, 73);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Instruksi :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(16, 410, 410, 20);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Instruksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Instruksi.setColumns(20);
        Instruksi.setRows(7);
        Instruksi.setName("Instruksi"); // NOI18N
        scrollPane7.setViewportView(Instruksi);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(16, 430, 700, 73);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Evaluasi :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(16, 510, 410, 20);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Evaluasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Evaluasi.setColumns(20);
        Evaluasi.setRows(7);
        Evaluasi.setName("Evaluasi"); // NOI18N
        scrollPane8.setViewportView(Evaluasi);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(16, 530, 700, 73);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbDiagnosa.setName("tbDiagnosa"); // NOI18N
        Scroll1.setViewportView(tbDiagnosa);

        FormInput.add(Scroll1);
        Scroll1.setBounds(16, 630, 700, 123);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Diagnosa :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(16, 610, 68, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Prosedur :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(16, 760, 68, 23);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbProsedur.setName("tbProsedur"); // NOI18N
        Scroll2.setViewportView(tbProsedur);

        FormInput.add(Scroll2);
        Scroll2.setBounds(16, 780, 700, 123);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPermintaanRadiologi.setName("tbPermintaanRadiologi"); // NOI18N
        Scroll3.setViewportView(tbPermintaanRadiologi);

        FormInput.add(Scroll3);
        Scroll3.setBounds(16, 930, 700, 123);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Permintaan Radiologi :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(16, 910, 120, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Permintaan Laborat Patologi Klinis :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(16, 1060, 190, 23);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbPermintaanPK.setName("tbPermintaanPK"); // NOI18N
        Scroll4.setViewportView(tbPermintaanPK);

        FormInput.add(Scroll4);
        Scroll4.setBounds(16, 1080, 700, 123);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbDetailPK.setName("tbDetailPK"); // NOI18N
        Scroll5.setViewportView(tbDetailPK);

        FormInput.add(Scroll5);
        Scroll5.setBounds(16, 1210, 700, 223);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Permintaan Laborat Patologi Anatomi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(16, 1440, 250, 23);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbPermintaanPA.setName("tbPermintaanPA"); // NOI18N
        Scroll6.setViewportView(tbPermintaanPA);

        FormInput.add(Scroll6);
        Scroll6.setBounds(16, 1460, 700, 133);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Permintaan Laborat Mikrobiologi & Bio Molekuler :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(16, 1600, 270, 23);

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbPermintaanMB.setName("tbPermintaanMB"); // NOI18N
        Scroll7.setViewportView(tbPermintaanMB);

        FormInput.add(Scroll7);
        Scroll7.setBounds(16, 1620, 700, 113);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbDetailMB.setName("tbDetailMB"); // NOI18N
        Scroll8.setViewportView(tbDetailMB);

        FormInput.add(Scroll8);
        Scroll8.setBounds(16, 1740, 700, 223);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Obat Non Racikan :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(16, 1970, 270, 23);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbObatNonRacikan.setName("tbObatNonRacikan"); // NOI18N
        Scroll9.setViewportView(tbObatNonRacikan);

        FormInput.add(Scroll9);
        Scroll9.setBounds(16, 1990, 700, 223);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Obat Racikan :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(16, 2220, 270, 23);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        Scroll10.setViewportView(tbObatRacikan);

        FormInput.add(Scroll10);
        Scroll10.setBounds(16, 2240, 700, 103);

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        Scroll11.setViewportView(tbDetailObatRacikan);

        FormInput.add(Scroll11);
        Scroll11.setBounds(16, 2350, 700, 223);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Tindakan :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(16, 2580, 120, 23);

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll12.setName("Scroll12"); // NOI18N
        Scroll12.setOpaque(true);

        tbTindakan.setName("tbTindakan"); // NOI18N
        Scroll12.setViewportView(tbTindakan);

        FormInput.add(Scroll12);
        Scroll12.setBounds(16, 2600, 700, 123);

        scrollPane2.setViewportView(FormInput);

        internalFrame1.add(scrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        runBackground(() ->tampil());
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
        runBackground(() ->tampil());
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        MasterTemplatePemeriksaanDokter form=new MasterTemplatePemeriksaanDokter(null,false);
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.emptTeks();
        form.isCek();
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());   
        
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        runBackground(() ->tampilDetailTemplate());
    }//GEN-LAST:event_tbDokterMouseClicked

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Asesmen.getText().trim().equals("")){
            Valid.textKosong(Asesmen,"Asesmen");
        }else{
            if(tbDokter.getSelectedRow()>-1){
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("pemeriksaan_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",21,new String[]{
                        noperawatan,tanggaldilakukan,jamdilakukan,"","","","","","","","","Compos Mentis",Subjek.getText(),Objek.getText(),"","",Plan.getText(),Asesmen.getText(),Instruksi.getText(),Evaluasi.getText(),kodedokter}
                    )==true){
                    for(i=0;i<tbDiagnosa.getRowCount();i++){ 
                        if(Sequel.cariInteger(
                                "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien inner join reg_periksa on diagnosa_pasien.no_rawat=reg_periksa.no_rawat inner join pasien "+
                                "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where pasien.no_rkm_medis='"+norm+"' and diagnosa_pasien.kd_penyakit='"+tbDiagnosa.getValueAt(i,1).toString()+"'")>0){
                            if(Sequel.menyimpantf2("diagnosa_pasien","?,?,?,?,?","Penyakit "+tbDiagnosa.getValueAt(i,1).toString(),5,new String[]{
                                    noperawatan,tbDiagnosa.getValueAt(i,0).toString(),"Ralan",tbDiagnosa.getValueAt(i,6).toString(),"Lama"
                                })==false){
                                sukses=false;
                            }
                        }else{
                            if(Sequel.menyimpantf2("diagnosa_pasien","?,?,?,?,?","Penyakit "+tbDiagnosa.getValueAt(i,1).toString(),5,new String[]{
                                    noperawatan,tbDiagnosa.getValueAt(i,0).toString(),"Ralan",tbDiagnosa.getValueAt(i,6).toString(),"Baru"
                                })==false){
                                sukses=false;
                            }
                        } 
                    }
                    
                    for(i=0;i<tbProsedur.getRowCount();i++){ 
                        if(Sequel.menyimpantf2("prosedur_pasien","?,?,?,?,?","Prosedur "+tbProsedur.getValueAt(i,1).toString(),5,new String[]{
                                noperawatan,tbProsedur.getValueAt(i,0).toString(),"Ralan",tbProsedur.getValueAt(i,3).toString(),tbProsedur.getValueAt(i,4).toString()
                            })==false){
                            sukses=false;
                        }
                    }
                    
                    if(tabModeRadiologi.getRowCount()>0){
                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_radiologi.noorder,4),signed)),0) from permintaan_radiologi where permintaan_radiologi.tgl_permintaan='"+tanggaldilakukan+"'","PR"+tanggaldilakukan.replaceAll("-",""),4);
                        if(Sequel.menyimpantf2("permintaan_radiologi","?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan Radiologi",12,new String[]{
                                nomor,noperawatan,tanggaldilakukan,jamdilakukan,"0000-00-00","00:00:00","0000-00-00","00:00:00",kodedokter,"ralan","-",Asesmen.getText()
                            })==true){
                            for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){ 
                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_radiologi","?,?,?","Permintaan Radiologi "+tbPermintaanRadiologi.getValueAt(i,1).toString(),3,new String[]{
                                        nomor,tbPermintaanRadiologi.getValueAt(i,0).toString(),"Belum"
                                    })==false){
                                    sukses=false;
                                }             
                            } 
                        }else{
                            sukses=false;
                        }
                    }
                    
                    if(tabModePK.getRowCount()>0){
                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_lab.noorder,4),signed)),0) from permintaan_lab where permintaan_lab.tgl_permintaan='"+tanggaldilakukan+"' ","PK"+tanggaldilakukan.replaceAll("-",""),4);   
                        if(Sequel.menyimpantf2("permintaan_lab","?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",12,new String[]{
                                nomor,noperawatan,tanggaldilakukan,jamdilakukan,"0000-00-00","00:00:00","0000-00-00","00:00:00",kodedokter,"ralan","-",Asesmen.getText()
                            })==true){
                            for(i=0;i<tbPermintaanPK.getRowCount();i++){ 
                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_lab","?,?,?","Permintaan Lab "+tbPermintaanPK.getValueAt(i,1).toString(),3,new String[]{
                                        nomor,tbPermintaanPK.getValueAt(i,0).toString(),"Belum"
                                    })==false){
                                    sukses=false;
                                }                  
                            } 

                            for(i=0;i<tbDetailPK.getRowCount();i++){ 
                                if(!tbDetailPK.getValueAt(i,3).toString().equals("")){                                
                                    if(Sequel.menyimpantf2("permintaan_detail_permintaan_lab","?,?,?,?","Detail Permintaan Lab "+tbDetailPK.getValueAt(i,0).toString().replaceAll("   ",""),4,new String[]{
                                            nomor,tbDetailPK.getValueAt(i,4).toString(),tbDetailPK.getValueAt(i,3).toString(),"Belum"
                                        })==false){
                                        sukses=false;
                                    }
                                }                        
                            }
                        }else{
                            sukses=false;
                        }
                    }
                    
                    if(tabModePA.getRowCount()>0){
                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_labpa.noorder,4),signed)),0) from permintaan_labpa where permintaan_labpa.tgl_permintaan='"+tanggaldilakukan+"' ","PA"+tanggaldilakukan.replaceAll("-",""),4); 
                        if(Sequel.menyimpantf2("permintaan_labpa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",20,new String[]{
                                nomor,noperawatan,tanggaldilakukan,jamdilakukan,"0000-00-00","00:00:00","0000-00-00","00:00:00",kodedokter,"ralan","-",Asesmen.getText(),tanggaldilakukan,"-","-","-","-","0000-00-00","-","-"
                            })==true){
                            for(i=0;i<tbPermintaanPA.getRowCount();i++){ 
                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_labpa","?,?,?","Pemeriksaan Lab PA "+tbPermintaanPA.getValueAt(i,1).toString(),3,new String[]{
                                        nomor,tbPermintaanPA.getValueAt(i,0).toString(),"Belum"
                                    })==false){
                                    sukses=false;
                                }                
                            } 
                        }else{
                            sukses=false;
                        }
                    }
                    
                    if(tabModeMB.getRowCount()>0){
                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_labmb.noorder,4),signed)),0) from permintaan_labmb where permintaan_labmb.tgl_permintaan='"+tanggaldilakukan+"' ","MB"+tanggaldilakukan.replaceAll("-",""),4);  
                        if(Sequel.menyimpantf2("permintaan_labmb","?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",12,new String[]{
                                nomor,noperawatan,tanggaldilakukan,jamdilakukan,"0000-00-00","00:00:00","0000-00-00","00:00:00",kodedokter,"ralan","-",Asesmen.getText()
                            })==true){
                            for(i=0;i<tbPermintaanMB.getRowCount();i++){ 
                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_labmb","?,?,?","Permintaan Lab MB"+tbPermintaanMB.getValueAt(i,1).toString(),3,new String[]{
                                        nomor,tbPermintaanMB.getValueAt(i,0).toString(),"Belum"
                                    })==false){
                                    sukses=false;
                                }                 
                            } 

                            for(i=0;i<tbDetailMB.getRowCount();i++){ 
                                if(!tbDetailMB.getValueAt(i,3).toString().equals("")){                                
                                    if(Sequel.menyimpantf2("permintaan_detail_permintaan_labmb","?,?,?,?","Detail Permintaan Lab MB "+tbDetailMB.getValueAt(i,0).toString().replaceAll("   ",""),4,new String[]{
                                            nomor,tbDetailMB.getValueAt(i,4).toString(),tbDetailMB.getValueAt(i,3).toString(),"Belum"
                                        })==false){
                                        sukses=false;
                                    }
                                }                        
                            }
                        }else{
                            sukses=false;
                        }
                    }
                    
                    if((tabModeObatUmum.getRowCount()>0)||(tabModeObatRacikan.getRowCount()>0)){
                        nomor=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(resep_obat.no_resep,4),signed)),0) from resep_obat where resep_obat.tgl_peresepan='"+tanggaldilakukan+"' or resep_obat.tgl_perawatan='"+tanggaldilakukan+"'",tanggaldilakukan.replaceAll("-",""),4);    
                        if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                                nomor,"0000-00-00","00:00:00",noperawatan,kodedokter,tanggaldilakukan,jamdilakukan,"ralan","0000-00-00","00:00:00"
                            })==true){
                            for(i=0;i<tbObatNonRacikan.getRowCount();i++){ 
                                if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,0).toString())>0){ 
                                    if(Sequel.menyimpantf2("resep_dokter","?,?,?,?","data",4,new String[]{
                                            nomor,tbObatNonRacikan.getValueAt(i,1).toString(),tbObatNonRacikan.getValueAt(i,0).toString(),tbObatNonRacikan.getValueAt(i,6).toString()
                                        })==false){
                                        sukses=false;
                                    } 
                                }
                            }
                            
                            for(i=0;i<tbObatRacikan.getRowCount();i++){ 
                                if(Valid.SetAngka(tbObatRacikan.getValueAt(i,4).toString())>0){ 
                                    if(Sequel.menyimpantf2("resep_dokter_racikan","?,?,?,?,?,?,?","resep obat racikan",7,new String[]{
                                            nomor,tbObatRacikan.getValueAt(i,0).toString(),tbObatRacikan.getValueAt(i,1).toString(),
                                            tbObatRacikan.getValueAt(i,2).toString(),tbObatRacikan.getValueAt(i,4).toString(),
                                            tbObatRacikan.getValueAt(i,5).toString(),tbObatRacikan.getValueAt(i,6).toString()
                                        })==false){
                                        sukses=false;
                                    } 
                                }
                            }
                            
                            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                                if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,10).toString())>0){
                                    if(Sequel.menyimpantf2("resep_dokter_racikan_detail","?,?,?,?,?,?,?","resep dokter racikan detail",7,new String[]{
                                            nomor,tbDetailObatRacikan.getValueAt(i,0).toString(),tbDetailObatRacikan.getValueAt(i,1).toString(),tbDetailObatRacikan.getValueAt(i,6).toString(),
                                            tbDetailObatRacikan.getValueAt(i,8).toString(),tbDetailObatRacikan.getValueAt(i,9).toString(),tbDetailObatRacikan.getValueAt(i,10).toString()
                                        })==false){
                                        sukses=false;
                                    } 
                                }
                            }
                        }else{
                            sukses=false;
                        }
                    }
                    
                    if(TabModeTindakan.getRowCount()>0){
                        ttljmdokter=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                        for(i=0;i<tbTindakan.getRowCount();i++){ 
                            if(Sequel.menyimpantf2("rawat_jl_dr","?,?,?,?,?,?,?,?,?,?,?,?","Tindakan/Pemeriksaan "+tbTindakan.getValueAt(i,1).toString(),12,new String[]{
                                    noperawatan,tbTindakan.getValueAt(i,0).toString(),kodedokter,tanggaldilakukan,jamdilakukan,tbTindakan.getValueAt(i,4).toString(), 
                                    tbTindakan.getValueAt(i,5).toString(),tbTindakan.getValueAt(i,6).toString(),tbTindakan.getValueAt(i,7).toString(), 
                                    tbTindakan.getValueAt(i,8).toString(),tbTindakan.getValueAt(i,3).toString(),"Belum"
                                })==true){
                                ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTindakan.getValueAt(i,3).toString());
                                ttljasasarana=ttljasasarana+Double.parseDouble(tbTindakan.getValueAt(i,4).toString());
                                ttlbhp=ttlbhp+Double.parseDouble(tbTindakan.getValueAt(i,5).toString());
                                ttljmdokter=ttljmdokter+Double.parseDouble(tbTindakan.getValueAt(i,6).toString());
                                ttlkso=ttlkso+Double.parseDouble(tbTindakan.getValueAt(i,7).toString());
                                ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTindakan.getValueAt(i,8).toString());
                            }else{
                                sukses=false;
                            } 
                        }
                        
                        if(sukses==true){
                            Sequel.queryu("delete from tampjurnal");    
                            if(ttlpendapatan>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }    
                                if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }                             
                            }
                            if(ttljmdokter>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }       
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }                               
                            }
                            if(ttlkso>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }       
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }                              
                            }
                            if(ttljasasarana>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }     
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }                              
                            }
                            if(ttlbhp>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }      
                                if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }                           
                            }
                            if(ttlmenejemen>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }       
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                                    sukses=false;
                                }                            
                            }
                            if(sukses==true){
                                sukses=jur.simpanJurnal(noperawatan,"U","TINDAKAN RAWAT JALAN PASIEN "+noperawatan+" DIPOSTING OLEH "+akses.getkode());
                            }     
                        }
                    }
                }else{
                    sukses=false;
                }
                
                if(sukses==true){
                    Sequel.Commit();
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, pemrosesan dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
                if(sukses==true){
                    dispose();
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data template pemeriksaan terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Subjek,BtnTambah);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            ps=koneksi.prepareStatement(
                "select set_akun_ralan.Suspen_Piutang_Tindakan_Ralan,set_akun_ralan.Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Beban_KSO_Tindakan_Ralan,set_akun_ralan.Utang_KSO_Tindakan_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Utang_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Menejemen_Tindakan_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Menejemen_Tindakan_Ralan,set_akun_ralan.HPP_BHP_Tindakan_Ralan,set_akun_ralan.Persediaan_BHP_Tindakan_Ralan from set_akun_ralan");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    Suspen_Piutang_Tindakan_Ralan=rs.getString("Suspen_Piutang_Tindakan_Ralan");
                    Tindakan_Ralan=rs.getString("Tindakan_Ralan");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Beban_KSO_Tindakan_Ralan=rs.getString("Beban_KSO_Tindakan_Ralan");
                    Utang_KSO_Tindakan_Ralan=rs.getString("Utang_KSO_Tindakan_Ralan");
                    Beban_Jasa_Sarana_Tindakan_Ralan=rs.getString("Beban_Jasa_Sarana_Tindakan_Ralan");
                    Utang_Jasa_Sarana_Tindakan_Ralan=rs.getString("Utang_Jasa_Sarana_Tindakan_Ralan");
                    Beban_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");
                    Utang_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");
                    HPP_BHP_Tindakan_Ralan=rs.getString("HPP_BHP_Tindakan_Ralan");
                    Persediaan_BHP_Tindakan_Ralan=rs.getString("Persediaan_BHP_Tindakan_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
            });
        } 
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MasterCariTemplatePemeriksaan dialog = new MasterCariTemplatePemeriksaan(new javax.swing.JFrame(), true);
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
    private widget.TextArea Asesmen;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextArea Evaluasi;
    private widget.PanelBiasa FormInput;
    private widget.TextArea Instruksi;
    private widget.Label LCount;
    private widget.TextArea Objek;
    private widget.TextArea Plan;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.TextArea Subjek;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    public widget.Table tbDetailMB;
    public widget.Table tbDetailObatRacikan;
    public widget.Table tbDetailPK;
    public widget.Table tbDiagnosa;
    private widget.Table tbDokter;
    public widget.Table tbObatNonRacikan;
    public widget.Table tbObatRacikan;
    public widget.Table tbPermintaanMB;
    public widget.Table tbPermintaanPA;
    public widget.Table tbPermintaanPK;
    public widget.Table tbPermintaanRadiologi;
    public widget.Table tbProsedur;
    public widget.Table tbTindakan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select template_pemeriksaan_dokter.no_template,template_pemeriksaan_dokter.kd_dokter,dokter.nm_dokter,"+
                    "template_pemeriksaan_dokter.keluhan,template_pemeriksaan_dokter.pemeriksaan,template_pemeriksaan_dokter.penilaian,"+
                    "template_pemeriksaan_dokter.rencana,template_pemeriksaan_dokter.instruksi,template_pemeriksaan_dokter.evaluasi "+
                    "from template_pemeriksaan_dokter inner join dokter on dokter.kd_dokter=template_pemeriksaan_dokter.kd_dokter "+
                    "where template_pemeriksaan_dokter.kd_dokter=? "+(TCari.getText().equals("")?"":"and (template_pemeriksaan_dokter.no_template like ? or "+
                    "template_pemeriksaan_dokter.keluhan like ? or template_pemeriksaan_dokter.pemeriksaan like ? or "+
                    "template_pemeriksaan_dokter.penilaian like ? or template_pemeriksaan_dokter.rencana like ? or "+
                    "template_pemeriksaan_dokter.instruksi like ? or template_pemeriksaan_dokter.evaluasi like ?) ")+
                    "order by template_pemeriksaan_dokter.no_template");
                
            try {
                ps.setString(1,kodedokter);
                if(!TCari.getText().equals("")){
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
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
                        rs.getString("no_template"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("keluhan"),rs.getString("pemeriksaan"),rs.getString("penilaian"),rs.getString("rencana"),rs.getString("instruksi"),rs.getString("evaluasi")
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
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
    
    public void tampil2() {
        runBackground(() ->tampil());
    }
    
    public void emptTeks() {
        TCari.requestFocus();
    }

    public JTable getTable(){
        return tbDokter;
    }
    
    public void setDokter(String kode,String tanggal, String jam,String norawat,String nomorrm){
        this.kodedokter=kode;
        this.tanggaldilakukan=tanggal;
        this.jamdilakukan=jam;
        this.noperawatan=norawat;
        this.norm=nomorrm;
    }
    
    public void isCek(){        
        BtnTambah.setEnabled(akses.gettemplate_pemeriksaan());
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }

    private void tampilDetailTemplate() {
        if(tabMode.getRowCount()!=0){
            if(tbDokter.getSelectedRow()>-1){
                try {
                    Subjek.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString());
                    Objek.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),4).toString());
                    Asesmen.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),5).toString());
                    Plan.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),6).toString());
                    Instruksi.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),7).toString());
                    Evaluasi.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),8).toString());
                    
                    Valid.tabelKosong(tabModeDiagnosa);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                            "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum,template_pemeriksaan_dokter_penyakit.urut from template_pemeriksaan_dokter_penyakit "+
                            "inner join penyakit on penyakit.kd_penyakit=template_pemeriksaan_dokter_penyakit.kd_penyakit "+
                            "inner join kategori_penyakit on penyakit.kd_ktg=kategori_penyakit.kd_ktg where "+
                            "template_pemeriksaan_dokter_penyakit.no_template=? order by template_pemeriksaan_dokter_penyakit.urut");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeDiagnosa.addRow(new Object[]{
                                rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)
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
                    
                    Valid.tabelKosong(tabModeProsedur);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_prosedur.kode,icd9.deskripsi_panjang,icd9.deskripsi_pendek,template_pemeriksaan_dokter_prosedur.urut,template_pemeriksaan_dokter_prosedur.jumlah "+
                            "from template_pemeriksaan_dokter_prosedur inner join icd9 on template_pemeriksaan_dokter_prosedur.kode=icd9.kode where template_pemeriksaan_dokter_prosedur.no_template=? "+
                            "order by template_pemeriksaan_dokter_prosedur.urut");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeProsedur.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
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
                    
                    Valid.tabelKosong(tabModeRadiologi);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan from template_pemeriksaan_dokter_permintaan_radiologi "+
                            "inner join jns_perawatan_radiologi on template_pemeriksaan_dokter_permintaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_radiologi.no_template=?");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeRadiologi.addRow(new Object[]{rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")});
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
                    
                    Valid.tabelKosong(tabModePK);
                    Valid.tabelKosong(tabModeDetailPK);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_dokter_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='PK'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModePK.addRow(new Object[]{rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")});
                            try {
                                tabModeDetailPK.addRow(new Object[]{rs.getString("nm_perawatan"),"","","",""});
                                ps2=koneksi.prepareStatement(
                                        "select template_pemeriksaan_dokter_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa "+
                                        "from template_pemeriksaan_dokter_detail_permintaan_lab inner join template_laboratorium on template_pemeriksaan_dokter_detail_permintaan_lab.id_template=template_laboratorium.id_template where template_pemeriksaan_dokter_detail_permintaan_lab.no_template=? and "+
                                        "template_pemeriksaan_dokter_detail_permintaan_lab.kd_jenis_prw=? order by template_laboratorium.urut");
                                ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                ps2.setString(2,rs.getString("kd_jenis_prw"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    la="";ld="";pa="";pd="";
                                    if(!rs2.getString("nilai_rujukan_ld").equals("")){
                                        ld="LD : "+rs2.getString("nilai_rujukan_ld");
                                    }
                                    if(!rs2.getString("nilai_rujukan_la").equals("")){
                                        la=", LA : "+rs2.getString("nilai_rujukan_la");
                                    }
                                    if(!rs2.getString("nilai_rujukan_pa").equals("")){
                                        pd=", PD : "+rs2.getString("nilai_rujukan_pd");
                                    }
                                    if(!rs2.getString("nilai_rujukan_pd").equals("")){
                                        pa=" PA : "+rs2.getString("nilai_rujukan_pa");
                                    }
                                    tabModeDetailPK.addRow(new Object[]{
                                        "   "+rs2.getString("Pemeriksaan"),rs2.getString("satuan"),ld+la+pd+pa,rs2.getString("id_template"),rs.getString("kd_jenis_prw")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
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
                    
                    Valid.tabelKosong(tabModePA);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_dokter_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='PA'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModePA.addRow(new Object[]{rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")});
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
                    
                    Valid.tabelKosong(tabModeMB);
                    Valid.tabelKosong(tabModeDetailMB);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_dokter_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='MB'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeMB.addRow(new Object[]{rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")});
                            try {
                                tabModeDetailMB.addRow(new Object[]{rs.getString("nm_perawatan"),"","","",""});
                                ps2=koneksi.prepareStatement(
                                        "select template_pemeriksaan_dokter_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa "+
                                        "from template_pemeriksaan_dokter_detail_permintaan_lab inner join template_laboratorium on template_pemeriksaan_dokter_detail_permintaan_lab.id_template=template_laboratorium.id_template where template_pemeriksaan_dokter_detail_permintaan_lab.no_template=? and "+
                                        "template_pemeriksaan_dokter_detail_permintaan_lab.kd_jenis_prw=? order by template_laboratorium.urut");
                                ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                ps2.setString(2,rs.getString("kd_jenis_prw"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    la="";ld="";pa="";pd="";
                                    if(!rs2.getString("nilai_rujukan_ld").equals("")){
                                        ld="LD : "+rs2.getString("nilai_rujukan_ld");
                                    }
                                    if(!rs2.getString("nilai_rujukan_la").equals("")){
                                        la=", LA : "+rs2.getString("nilai_rujukan_la");
                                    }
                                    if(!rs2.getString("nilai_rujukan_pa").equals("")){
                                        pd=", PD : "+rs2.getString("nilai_rujukan_pd");
                                    }
                                    if(!rs2.getString("nilai_rujukan_pd").equals("")){
                                        pa=" PA : "+rs2.getString("nilai_rujukan_pa");
                                    }
                                    tabModeDetailMB.addRow(new Object[]{
                                        "   "+rs2.getString("Pemeriksaan"),rs2.getString("satuan"),ld+la+pd+pa,rs2.getString("id_template"),rs.getString("kd_jenis_prw")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
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
                    
                    Valid.tabelKosong(tabModeObatUmum);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_resep.kode_brng,databarang.nama_brng,kodesatuan.satuan,template_pemeriksaan_dokter_resep.jml,template_pemeriksaan_dokter_resep.aturan_pakai,jenis.nama,industrifarmasi.nama_industri, "+
                            "databarang.kapasitas,databarang.letak_barang from template_pemeriksaan_dokter_resep inner join databarang on template_pemeriksaan_dokter_resep.kode_brng=databarang.kode_brng inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat "+
                            "inner join jenis on databarang.kdjns=jenis.kdjns inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri where template_pemeriksaan_dokter_resep.no_template=? order by databarang.nama_brng");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeObatUmum.addRow(new Object[]{
                                rs.getString("jml"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("satuan"),rs.getString("letak_barang"),rs.getString("nama"),rs.getString("aturan_pakai"),rs.getString("nama_industri"),rs.getDouble("kapasitas")
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
                    
                    Valid.tabelKosong(tabModeObatRacikan);
                    Valid.tabelKosong(tabModeDetailObatRacikan);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_resep_racikan.no_racik,template_pemeriksaan_dokter_resep_racikan.kd_racik,template_pemeriksaan_dokter_resep_racikan.nama_racik,metode_racik.nm_racik,template_pemeriksaan_dokter_resep_racikan.jml_dr,template_pemeriksaan_dokter_resep_racikan.aturan_pakai,"+
                            "template_pemeriksaan_dokter_resep_racikan.keterangan from template_pemeriksaan_dokter_resep_racikan inner join metode_racik on metode_racik.kd_racik=template_pemeriksaan_dokter_resep_racikan.kd_racik where template_pemeriksaan_dokter_resep_racikan.no_template=? "+
                            "order by template_pemeriksaan_dokter_resep_racikan.no_racik");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeObatRacikan.addRow(new Object[]{
                                rs.getString("no_racik"),rs.getString("nama_racik"),rs.getString("kd_racik"),rs.getString("nm_racik"),rs.getString("jml_dr"),rs.getString("aturan_pakai"),rs.getString("keterangan")
                            });
                            try {
                                ps2=koneksi.prepareStatement(
                                        "select template_pemeriksaan_dokter_resep_racikan_detail.kode_brng,databarang.nama_brng,kodesatuan.satuan,template_pemeriksaan_dokter_resep_racikan_detail.jml,jenis.nama,"+
                                        "databarang.kapasitas,template_pemeriksaan_dokter_resep_racikan_detail.p1,template_pemeriksaan_dokter_resep_racikan_detail.p2,template_pemeriksaan_dokter_resep_racikan_detail.kandungan,"+
                                        "industrifarmasi.nama_industri,databarang.letak_barang from template_pemeriksaan_dokter_resep_racikan_detail inner join databarang on template_pemeriksaan_dokter_resep_racikan_detail.kode_brng=databarang.kode_brng "+
                                        "inner join jenis on databarang.kdjns=jenis.kdjns inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri "+
                                        "where template_pemeriksaan_dokter_resep_racikan_detail.no_template=? and template_pemeriksaan_dokter_resep_racikan_detail.no_racik=? order by template_pemeriksaan_dokter_resep_racikan_detail.kode_brng");
                                ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                ps2.setString(2,rs.getString("no_racik"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rs.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("satuan"),
                                        rs2.getString("nama"),rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),rs2.getString("kandungan"),
                                        rs2.getDouble("jml"),rs2.getString("nama_industri"),rs2.getString("letak_barang")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
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

                    Valid.tabelKosong(TabModeTindakan);
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_tindakan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,jns_perawatan.total_byrdr,jns_perawatan.bhp,jns_perawatan.material,"+
                            "jns_perawatan.tarif_tindakandr,jns_perawatan.kso,jns_perawatan.menejemen from template_pemeriksaan_dokter_tindakan inner join jns_perawatan "+
                            "on template_pemeriksaan_dokter_tindakan.kd_jenis_prw=jns_perawatan.kd_jenis_prw inner join kategori_perawatan on kategori_perawatan.kd_kategori=jns_perawatan.kd_kategori "+
                            "where template_pemeriksaan_dokter_tindakan.no_template=?");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            TabModeTindakan.addRow(new Object[]{
                                rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan"),rs.getString("nm_kategori"),rs.getDouble("total_byrdr"),
                                rs.getDouble("material"),rs.getDouble("bhp"),rs.getDouble("tarif_tindakandr"),rs.getDouble("kso"),rs.getDouble("menejemen")
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
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }
}

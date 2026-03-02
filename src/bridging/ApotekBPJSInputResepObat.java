/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable2;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.riwayatobat;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import simrskhanza.DlgCariBangsal;
import widget.Button;

/**
 *
 * @author dosen
 */
public final class ApotekBPJSInputResepObat extends javax.swing.JDialog {
    private final DefaultTableModel tabModeobat,tabModeObatRacikan,tabModeDetailObatRacikan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psobat,psstok,ps2;
    private ResultSet rsobat,rsstok,rs2;
    private double sisacari=0,y=0,kenaikan=0,ttl=0,ppnobat=0;
    private int i=0,row2,r;
    private Jurnal jur=new Jurnal();
    private String JADIKANPIUTANGAPOTEKBPJS,signa1="1",utc="",link=koneksiDB.URLAPIAPOTEKBPJS(),signa2="1",requestJson="",URL="",bangsal="",bangsaldefault=Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi limit 1"),tampilkan_ppnobat_ralan="",
                   hppfarmasi="",DEPOAKTIFOBAT="";
    private DlgCariBangsal lokasidepo;
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();
    private WarnaTable2 warna3=new WarnaTable2();
    private riwayatobat Trackobat=new riwayatobat();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private String[] arrSplit;
    private boolean sukses=true;
    private ApiApotekBPJS api = new ApiApotekBPJS();
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public ApotekBPJSInputResepObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);
        
        try {
            JADIKANPIUTANGAPOTEKBPJS = koneksiDB.JADIKANPIUTANGAPOTEKBPJS();
        } catch (Exception e) {
            System.out.println("E : "+e);
            JADIKANPIUTANGAPOTEKBPJS = "no";
        }

        tabModeobat=new DefaultTableModel(null,new Object[]{
                "Jml","Kode Barang","Nama Barang","Signa 1","Signa 2","Hari","Jml.Piutang","Harga Beli",
                "Harga Jual","Stok","Aturan Pakai","Kode Obat RS","Satuan","No.Batch","No.Faktur","Kadaluarsa"
            }){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==3)||(colIndex==4)||(colIndex==5)||(colIndex==6)||(colIndex==10)||(colIndex==13)||(colIndex==14)||(colIndex==15)) {
                    a=true;
                }
                return a;
            }
            
            Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
            };
             
            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        
        tbObat.setModel(tabModeobat);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 16; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(85);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(44);
            }else if(i==4){
                column.setPreferredWidth(44);
            }else if(i==5){
                column.setPreferredWidth(35);
            }else if(i==6){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(66);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==7){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(80);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==8){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(80);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==9){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(45);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==10){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(85);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(70);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==14){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(90);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==15){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(65);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }                
        }
        warna.kolom=6;
        tbObat.setDefaultRenderer(Object.class,warna);
        
        tabModeObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Nama Racikan","Kode Racik","Metode Racik","Jml.Racik","Aturan Pakai","Keterangan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                return true;
             }
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
                column.setPreferredWidth(160);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(57);
            }else if(i==5){
                column.setPreferredWidth(85);
            }else if(i==6){
                column.setPreferredWidth(200);
            }
        }

        warna2.kolom=4;
        tbObatRacikan.setDefaultRenderer(Object.class,warna2);
        
        tabModeDetailObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Jml","Kode Barang","Nama Barang","Signa 1","Signa 2","Hari","Jml.Piutang",
                "Harga Beli","Harga Jual","Stok","Kode Obat RS","Kode Satuan","No.Batch","No.Faktur","Kadaluarsa"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==1) || (colIndex==4) || (colIndex==5) || (colIndex==6)|| (colIndex==7)|| (colIndex==13)|| (colIndex==14)|| (colIndex==15)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        tbDetailObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 16; i++) {
            TableColumn column = tbDetailObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(35);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(44);
            }else if(i==5){
                column.setPreferredWidth(44);
            }else if(i==6){
                column.setPreferredWidth(35);
            }else if(i==7){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(66);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==8){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(80);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==9){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(80);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==10){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(45);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(70);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==14){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(90);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            }else if(i==15){
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    column.setPreferredWidth(65);
                }else{
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                }
            } 
        }

        warna3.kolom=7;
        tbDetailObatRacikan.setDefaultRenderer(Object.class,warna3);
        
        try {
            hppfarmasi=koneksiDB.HPPFARMASI();
        } catch (Exception e) {
            hppfarmasi="dasar";
        }
        
        if(JADIKANPIUTANGAPOTEKBPJS.equals("no")){
            LabelTotal.setVisible(false);
            LTotal.setVisible(false);
            LabelPPN.setVisible(false);
            LPpn.setVisible(false);
            LabelTotalTagihan.setVisible(false);
            LTotalTagihan.setVisible(false);
            LabelDepo.setVisible(false);
            nmgudang.setVisible(false);
            BtnGudang.setVisible(false);
            
            try {
                DEPOAKTIFOBAT = koneksiDB.DEPOAKTIFOBAT();
            } catch (Exception e) {
                System.out.println("E : "+e);
                DEPOAKTIFOBAT = "";
            }
        }
        
        tampilkan_ppnobat_ralan=Sequel.cariIsi("select set_nota.tampilkan_ppnobat_ralan from set_nota"); 
        
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

        Kd2 = new widget.TextBox();
        TNoRM = new widget.TextBox();
        Tanggal = new widget.TextBox();
        KdPj = new widget.TextBox();
        kdgudang = new widget.TextBox();
        NmPasien = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        LabelTotal = new widget.Label();
        LTotal = new widget.Label();
        LabelPPN = new widget.Label();
        LPpn = new widget.Label();
        LabelTotalTagihan = new widget.Label();
        LTotalTagihan = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        CariDataObat = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel8 = new widget.Label();
        TglResep = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        jLabel20 = new widget.Label();
        NmPoli = new widget.TextBox();
        KdPoli = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel15 = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        jLabel14 = new widget.Label();
        NoResep = new widget.TextBox();
        Iterasi = new javax.swing.JComboBox<>();
        jLabel16 = new widget.Label();
        JnsObat = new javax.swing.JComboBox<>();
        jLabel17 = new widget.Label();
        jLabel4 = new widget.Label();
        NoKartu = new widget.TextBox();
        NoSEP = new widget.TextBox();
        jLabel18 = new widget.Label();
        TNoRW = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel3 = new widget.Label();
        TglSEP = new widget.TextBox();
        jLabel19 = new widget.Label();
        LabelDepo = new widget.Label();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N

        kdgudang.setEditable(false);
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });

        NmPasien.setHighlighter(null);
        NmPasien.setName("NmPasien"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat Apotek BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        LabelTotal.setText("Total :");
        LabelTotal.setName("LabelTotal"); // NOI18N
        LabelTotal.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi3.add(LabelTotal);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(121, 23));
        panelisi3.add(LTotal);

        LabelPPN.setText("PPN :");
        LabelPPN.setName("LabelPPN"); // NOI18N
        LabelPPN.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(LabelPPN);

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi3.add(LPpn);

        LabelTotalTagihan.setText("Total+PPN :");
        LabelTotalTagihan.setName("LabelTotalTagihan"); // NOI18N
        LabelTotalTagihan.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(LabelTotalTagihan);

        LTotalTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotalTagihan.setText("0");
        LTotalTagihan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotalTagihan.setName("LTotalTagihan"); // NOI18N
        LTotalTagihan.setPreferredSize(new java.awt.Dimension(200, 23));
        panelisi3.add(LTotalTagihan);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi3.add(BtnHapus);

        CariDataObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        CariDataObat.setToolTipText("");
        CariDataObat.setName("CariDataObat"); // NOI18N
        CariDataObat.setPreferredSize(new java.awt.Dimension(28, 23));
        CariDataObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CariDataObatActionPerformed(evt);
            }
        });
        panelisi3.add(CariDataObat);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

        jLabel8.setText("Pelayanan :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel8);
        jLabel8.setBounds(330, 100, 70, 23);

        TglResep.setEditable(false);
        TglResep.setHighlighter(null);
        TglResep.setName("TglResep"); // NOI18N
        FormInput.add(TglResep);
        TglResep.setBounds(84, 70, 135, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2026" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(404, 100, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(497, 100, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(562, 100, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(627, 100, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(692, 100, 22, 23);

        jLabel20.setText("Tgl.Resep :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel20);
        jLabel20.setBounds(10, 70, 70, 23);

        NmPoli.setEditable(false);
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(161, 130, 165, 23);

        KdPoli.setEditable(false);
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormInput.add(KdPoli);
        KdPoli.setBounds(84, 130, 75, 23);

        jLabel13.setText("Asal Poli :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 130, 80, 23);

        jLabel15.setText("Dokter DPJP :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 100, 80, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setHighlighter(null);
        KdDPJP.setName("KdDPJP"); // NOI18N
        FormInput.add(KdDPJP);
        KdDPJP.setBounds(84, 100, 75, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setHighlighter(null);
        NmDPJP.setName("NmDPJP"); // NOI18N
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(161, 100, 165, 23);

        jLabel14.setText("No.Resep :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(226, 70, 70, 23);

        NoResep.setEditable(false);
        NoResep.setHighlighter(null);
        NoResep.setName("NoResep"); // NOI18N
        FormInput.add(NoResep);
        NoResep.setBounds(300, 70, 138, 23);

        Iterasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Iterasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0. Tanpa Iterasi", "1. Dengan Iterasi" }));
        Iterasi.setName("Iterasi"); // NOI18N
        FormInput.add(Iterasi);
        Iterasi.setBounds(386, 130, 135, 23);

        jLabel16.setText("Iterasi :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(330, 130, 52, 23);

        JnsObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        JnsObat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Obat PRB", "2. Obat Kronis Belum Stabil", "3. Obat Kemoterapi" }));
        JnsObat.setSelectedIndex(1);
        JnsObat.setName("JnsObat"); // NOI18N
        FormInput.add(JnsObat);
        JnsObat.setBounds(524, 70, 190, 23);

        jLabel17.setText("Jenis Obat :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(440, 70, 80, 23);

        jLabel4.setText("No.Kartu :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 40, 80, 23);

        NoKartu.setEditable(false);
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        FormInput.add(NoKartu);
        NoKartu.setBounds(84, 40, 150, 23);

        NoSEP.setEditable(false);
        NoSEP.setHighlighter(null);
        NoSEP.setName("NoSEP"); // NOI18N
        FormInput.add(NoSEP);
        NoSEP.setBounds(529, 40, 185, 23);

        jLabel18.setText("No.SEP :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(475, 40, 50, 23);

        TNoRW.setEditable(false);
        TNoRW.setHighlighter(null);
        TNoRW.setName("TNoRW"); // NOI18N
        FormInput.add(TNoRW);
        TNoRW.setBounds(84, 10, 120, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(206, 10, 508, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 80, 23);

        TglSEP.setEditable(false);
        TglSEP.setHighlighter(null);
        TglSEP.setName("TglSEP"); // NOI18N
        FormInput.add(TglSEP);
        TglSEP.setBounds(327, 40, 135, 23);

        jLabel19.setText("Tanggal SEP :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(243, 40, 80, 23);

        LabelDepo.setText("Depo :");
        LabelDepo.setName("LabelDepo"); // NOI18N
        LabelDepo.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(LabelDepo);
        LabelDepo.setBounds(520, 130, 42, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmgudang);
        nmgudang.setBounds(566, 130, 118, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        FormInput.add(BtnGudang);
        BtnGudang.setBounds(686, 130, 28, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbObatPropertyChange(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        TabRawat.addTab("Umum", Scroll);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(454, 90));

        tbObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        Scroll1.setViewportView(tbObatRacikan);

        jPanel3.add(Scroll1, java.awt.BorderLayout.PAGE_START);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDetailObatRacikan.setAutoCreateRowSorter(true);
        tbDetailObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        tbDetailObatRacikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailObatRacikanMouseClicked(evt);
            }
        });
        tbDetailObatRacikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDetailObatRacikanPropertyChange(evt);
            }
        });
        tbDetailObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailObatRacikanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbDetailObatRacikan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Racikan", jPanel3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tbObat.getRowCount()!=0){
            if(tbObat.getSelectedRow()!= -1){
                i=tbObat.getSelectedColumn();
                if((i==3)||(i==4)||(i==5)){
                    Double signasatu=Valid.SetAngka(tabModeobat.getValueAt(tbObat.getSelectedRow(),3).toString());
                    Double signadua=Valid.SetAngka(tabModeobat.getValueAt(tbObat.getSelectedRow(),4).toString());
                    Double hari=Valid.SetAngka(tabModeobat.getValueAt(tbObat.getSelectedRow(),5).toString());
                    tbObat.setValueAt((signadua*signasatu*hari),tbObat.getSelectedRow(),0);
                }else if(i==6){
                    if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                        sisacari=0;
                        try {
                            psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                            try {
                                psstok.setString(1,kdgudang.getText());
                                psstok.setString(2,tabModeobat.getValueAt(tbObat.getSelectedRow(),11).toString());
                                rsstok=psstok.executeQuery();
                                if(rsstok.next()){
                                    sisacari=rsstok.getDouble(1);
                                }                                
                            } catch (Exception e) {
                                sisacari=0;
                                System.out.println("Notifikasi : "+e);
                            }finally{
                                if(rsstok != null){
                                    rsstok.close();
                                }
                                if(psstok != null){
                                    psstok.close();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        }
                        tbObat.setValueAt(sisacari,tbObat.getSelectedRow(),9);
                    }
                }
            }  
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tbObat.getRowCount()!=0){
            if(tbObat.getSelectedRow()!= -1){
                if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)){
                    i=tbObat.getSelectedColumn();
                    if((i==3)||(i==4)||(i==5)){
                        Double signasatu=Valid.SetAngka(tabModeobat.getValueAt(tbObat.getSelectedRow(),3).toString());
                        Double signadua=Valid.SetAngka(tabModeobat.getValueAt(tbObat.getSelectedRow(),4).toString());
                        Double hari=Valid.SetAngka(tabModeobat.getValueAt(tbObat.getSelectedRow(),5).toString());
                        tbObat.setValueAt((signadua*signasatu*hari),tbObat.getSelectedRow(),0);
                    }else if(i==6){
                        if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                            sisacari=0;
                            try {
                                psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                                try {
                                    psstok.setString(1,kdgudang.getText());
                                    psstok.setString(2,tabModeobat.getValueAt(tbObat.getSelectedRow(),11).toString());
                                    rsstok=psstok.executeQuery();
                                    if(rsstok.next()){
                                        sisacari=rsstok.getDouble(1);
                                    }                                
                                } catch (Exception e) {
                                    sisacari=0;
                                    System.out.println("Notifikasi : "+e);
                                }finally{
                                    if(rsstok != null){
                                        rsstok.close();
                                    }
                                    if(psstok != null){
                                        psstok.close();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            }
                            tbObat.setValueAt(sisacari,tbObat.getSelectedRow(),9);
                        }
                    }
                }
            }  
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed
    
private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if(TNoRW.getText().trim().equals("")){
        Valid.textKosong(TNoRW,"No Rawat");
    }else if(NoSEP.getText().trim().equals("")){
        Valid.textKosong(NoSEP,"Nomor Sep");                                      
    }else if(KdDPJP.getText().trim().equals("")){
        Valid.textKosong(KdDPJP,"Dokter");                                      
    }else if(KdPoli.getText().trim().equals("")){
        Valid.textKosong(KdPoli,"Poliklinik");                                      
    }else if(NoResep.getText().trim().equals("")){
        Valid.textKosong(NoResep,"Nomor Resep");                                      
    }else if(NoKartu.getText().trim().equals("")){
        Valid.textKosong(NoKartu,"Nomor Kartu");                                      
    }else{
        sukses=true;
        if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
            if(nmgudang.getText().trim().equals("")){
                Valid.textKosong(BtnGudang,"Lokasi Depo");                                      
            }
            sukses=false;
        }
        
        if(sukses==true){
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                    if(ttl>0){
                        if(Sequel.menyimpantf2("piutang","'"+TNoRW.getText()+"','"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"','"+(akses.getkode().equals("Admin Utama")?"-":akses.getkode())+"','"+TNoRM.getText()+
                                "','"+NmPasien.getText()+"','Obat Apotek BPJS','Rawat Jalan','"+ppnobat+"','0','0','"+ttl+"','Umum','"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"','"+kdgudang.getText()+"'","No.Nota")==true){
                            row2=tabModeobat.getRowCount();
                            for(r=0;r<row2;r++){ 
                                try {
                                    Double piutang=Valid.SetAngka(tabModeobat.getValueAt(r,6).toString());
                                    if(piutang>0){
                                        Double totalpiutang=piutang*Valid.SetAngka(tabModeobat.getValueAt(r,8).toString());
                                        if(Sequel.menyimpantf2("detailpiutang","'"+TNoRW.getText()+"','"+tabModeobat.getValueAt(r,11).toString()+"','"+tabModeobat.getValueAt(r,12).toString()+"','"+tabModeobat.getValueAt(r,8).toString()+"','"+tabModeobat.getValueAt(r,7).toString()+"',"+
                                                "'"+piutang+"','"+totalpiutang+"','0','0','"+totalpiutang+"','"+tabModeobat.getValueAt(r,13).toString()+"','"+tabModeobat.getValueAt(r,14).toString()+"','"+tabModeobat.getValueAt(r,10).toString()+"'","Obat/BHP/Alkes"
                                            )==true){
                                                Trackobat.catatRiwayat(tabModeobat.getValueAt(r,11).toString(),0,piutang,"Piutang",akses.getkode(),kdgudang.getText(),"Simpan","","",TNoRW.getText()+" "+TPasien.getText());
                                                Sequel.menyimpan("gudangbarang","'"+tabModeobat.getValueAt(r,11).toString()+"','"+kdgudang.getText()+"','-"+piutang+"','',''","stok=stok-'"+piutang+"'","kode_brng='"+tabModeobat.getValueAt(r,11).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_faktur=''");
                                        }else{
                                           sukses=false;
                                        } 
                                    }  
                                } catch (Exception e) {
                                    sukses=false;
                                }                           
                            }
                            
                            if(sukses==true){
                                row2=tabModeDetailObatRacikan.getRowCount();
                                for(r=0;r<row2;r++){ 
                                    try {
                                        Double piutang=Valid.SetAngka(tabModeDetailObatRacikan.getValueAt(r,7).toString());
                                        if(piutang>0){
                                            Double totalpiutang=piutang*Valid.SetAngka(tabModeobat.getValueAt(r,9).toString());
                                            if(Sequel.menyimpantf2("detailpiutang","'"+TNoRW.getText()+"','"+tabModeobat.getValueAt(r,11).toString()+"','"+tabModeobat.getValueAt(r,12).toString()+"','"+tabModeobat.getValueAt(r,9).toString()+"','"+tabModeobat.getValueAt(r,8).toString()+"','"+piutang+"',"+
                                                    "'"+totalpiutang+"','0','0','"+totalpiutang+"','"+tabModeobat.getValueAt(r,13).toString()+"','"+tabModeobat.getValueAt(r,14).toString()+"','"+tabModeobat.getValueAt(r,4).toString()+"x"+tabModeobat.getValueAt(r,5).toString()+"'","Obat/BHP/Alkes"
                                                )==true){
                                                Trackobat.catatRiwayat(tabModeobat.getValueAt(r,11).toString(),0,piutang,"Piutang",akses.getkode(),kdgudang.getText(),"Simpan","","",TNoRW.getText()+" "+TNoRW.getText()+" "+TPasien.getText());
                                                Sequel.menyimpan("gudangbarang","'"+tabModeobat.getValueAt(r,11).toString()+"','"+kdgudang.getText()+"','-"+piutang+"','',''","stok=stok-'"+piutang+"'","kode_brng='"+tabModeobat.getValueAt(r,11).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_faktur=''");
                                            }else{
                                               sukses=false;
                                            }
                                        }
                                    } catch (Exception e) {
                                        sukses=false;
                                    }    
                                }
                            }
                        }else{
                            sukses=false;
                        }
                    }
                }
                
                if(sukses==true){
                    try {  
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                        headers.add("x-cons-id", koneksiDB.CONSIDAPIAPOTEKBPJS());
                        utc = String.valueOf(api.GetUTCdatetimeAsString());
                        headers.add("x-timestamp", utc);
                        headers.add("x-signature", api.getHmac(utc));
                        headers.add("user_key", koneksiDB.USERKEYAPIAPOTEKBPJS());
                        requestEntity = new HttpEntity(headers);
                        URL = link + "/sjpresep/v3/insert";
                        System.out.println("URL : "+URL);
                        requestJson = "{"+ 
                                            "\"TGLSJP\":\""+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+ "\","+ 
                                            "\"REFASALSJP\":\""+NoSEP.getText()+"\","+ 
                                            "\"POLIRSP\": \""+KdPoli.getText()+"\","+ 
                                            "\"KDJNSOBAT\":\""+JnsObat.getSelectedItem().toString().substring(0, 1)+"\","+ 
                                            "\"NORESEP\":\""+NoResep.getText()+"\", "+ 
                                            "\"IDUSERSJP\":\""+akses.getkode()+"\","+ 
                                            "\"TGLRSP\":\""+TglResep.getText()+"\","+ 
                                            "\"TGLPELRSP\":\""+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"\","+ 
                                            "\"KdDokter\":\""+KdDPJP.getText()+"\","+ 
                                            "\"iterasi\":\""+Iterasi.getSelectedItem().toString().substring(0,1)+ "\""+ 
                                      "}";
                        System.out.println("Resep : "+requestJson);
                        requestEntity = new HttpEntity(requestJson, headers);
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                        nameNode = root.path("metaData");
                        if (nameNode.path("code").asText().equals("200")) {
                            response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc));
                            System.out.println("Response JSON : "+response);
                            if(Sequel.menyimpantf2("bridging_resep_apotek_bpjs", "?,?,?,?,?,?,?,?,?,?,?","Data Apotek Online", 12,new String[]{
                                NoSEP.getText(),response.path("noApotik").asText(),TglSEP.getText(),JnsObat.getSelectedItem().toString(),akses.getkode(),NoResep.getText(),TglResep.getText(),
                                Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),Iterasi.getSelectedItem().toString(),
                                response.path("faskesAsal").asText(),response.path("byTagRsp").asText(), response.path("byVerRsp").asText()
                            })==true){
                                URL = link + "/obatnonracikan/v3/insert";
                                System.out.println("URL : "+URL);
                                for(i=0;i<tbObat.getRowCount();i++){ 
                                    if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){   
                                        try {
                                            requestJson = "{"+ 
                                                             "\"NOSJP\":\""+response.path("noApotik").asText()+"\","+
                                                             "\"NORESEP\":\""+NoResep.getText()+"\","+ 
                                                             "\"KDOBT\": \""+tbObat.getValueAt(i,1).toString()+"\","+ 
                                                             "\"NMOBAT\": \""+tbObat.getValueAt(i,2).toString()+"\","+ 
                                                             "\"SIGNA1OBT\":"+tbObat.getValueAt(i,3).toString()+","+ 
                                                             "\"SIGNA2OBT\":"+tbObat.getValueAt(i,4).toString()+","+
                                                             "\"JMLOBT\":"+tbObat.getValueAt(i,0).toString()+","+
                                                             "\"JHO\":"+tbObat.getValueAt(i,5).toString()+","+ 
                                                             "\"CatKhsObt\":\"OK\""+
                                                          "}";
                                            System.out.println("Obat Non Racikan : "+requestJson);
                                            requestEntity = new HttpEntity(requestJson, headers);
                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                            nameNode = root.path("metaData");
                                            System.out.println("Respon JSON : "+nameNode);
                                            if(nameNode.path("code").asText().equals("200")) {
                                                if(Sequel.menyimpantf("bridging_resep_apotek_bpjs_nonracikan","?,?,?,?,?,?,?","Obat Non Racikan BPJS",7,new String[]{
                                                    response.path("noApotik").asText(),NoResep.getText(),tbObat.getValueAt(i,1).toString(),tbObat.getValueAt(i,3).toString(),tbObat.getValueAt(i,4).toString(),tbObat.getValueAt(i,0).toString(),tbObat.getValueAt(i,5).toString()
                                                })==false) {
                                                    sukses=false;
                                                }
                                            }else {
                                                sukses=false;
                                                JOptionPane.showMessageDialog(rootPane,"Respon Server BPJS : "+nameNode.path("message").asText());
                                            }
                                        } catch (Exception ex) {
                                            sukses=false;
                                            System.out.println("Notifikasi : " + ex);
                                            if (ex.toString().contains("UnknownHostException")) {
                                                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                                            }
                                        }            
                                    }
                                } 

                                URL = link + "/obatracikan/v3/insert";
                                System.out.println("URL : "+URL);
                                for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,1).toString())>0){
                                        try {
                                            requestJson = "{"+
                                                            "\"NOSJP\":\""+response.path("noApotik").asText()+"\","+
                                                            "\"NORESEP\":\""+NoResep.getText()+"\","+
                                                            "\"JNSROBT\":\"No."+tbDetailObatRacikan.getValueAt(i,0).toString()+"\","+
                                                            "\"KDOBT\":\""+tbDetailObatRacikan.getValueAt(i,2).toString()+"\","+
                                                            "\"NMOBAT\":\""+tbDetailObatRacikan.getValueAt(i,3).toString()+"\","+
                                                            "\"SIGNA1OBT\":"+tbDetailObatRacikan.getValueAt(i,4).toString()+","+
                                                            "\"SIGNA2OBT\":"+tbDetailObatRacikan.getValueAt(i,5).toString()+","+
                                                            "\"PERMINTAAN\":"+tbDetailObatRacikan.getValueAt(i,1).toString()+","+
                                                            "\"JMLOBT\":"+tbDetailObatRacikan.getValueAt(i,1).toString()+","+
                                                            "\"JHO\":"+tbDetailObatRacikan.getValueAt(i,6).toString()+","+
                                                            "\"CatKhsObt\":\"RACIKAN Ke "+NoResep.getText()+"\""+
                                                          "}";
                                            System.out.println("Obat Racikan : "+requestJson);
                                            requestEntity = new HttpEntity(requestJson, headers);
                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                            nameNode = root.path("metaData");
                                            System.out.println("Respon JSON : "+nameNode);
                                            if(nameNode.path("code").asText().equals("200")) {
                                                if(Sequel.menyimpantf("bridging_resep_apotek_bpjs_racikan","?,?,?,?,?,?,?,?,?","Obat Racikan",9,new String[]{
                                                    response.path("noApotik").asText(),"No."+tbDetailObatRacikan.getValueAt(i,0).toString(),NoResep.getText(),tbDetailObatRacikan.getValueAt(i,2).toString(), 
                                                    tbDetailObatRacikan.getValueAt(i,4).toString(),tbDetailObatRacikan.getValueAt(i,5).toString(),tbDetailObatRacikan.getValueAt(i,1).toString(),tbDetailObatRacikan.getValueAt(i,1).toString(), 
                                                    tbDetailObatRacikan.getValueAt(i,6).toString()
                                                })==false) {
                                                    sukses=false;
                                                }
                                            }else {
                                                sukses=false;
                                                JOptionPane.showMessageDialog(rootPane,"Respon Server BPJS : "+nameNode.path("message").asText());
                                            }
                                        } catch (Exception ex) {
                                            sukses=false;
                                            System.out.println("Notifikasi : " + ex);
                                            if (ex.toString().contains("UnknownHostException")) {
                                                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                                            }
                                        }  
                                    }
                                }
                                
                                if(sukses==true){
                                    if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                                        if(Sequel.menyimpantf2("bridging_resep_apotek_bpjs_piutang", "?,?","Mapping Piutang Obat BPJS",2,new String[]{
                                            response.path("noApotik").asText(),TNoRW.getText()
                                        })==false){
                                            sukses=false;
                                        }
                                    }
                                }
                            }else{
                                sukses=false;
                                JOptionPane.showMessageDialog(rootPane,"Gagal menyimpan data resep apotek BPJS ke server lokal..!!!!!");
                            }
                        }else{
                            sukses=false;
                            JOptionPane.showMessageDialog(rootPane, nameNode.path("message").asText());
                        } 
                    } catch (Exception ex) {
                        sukses=false;
                        System.out.println(ex);  
                        if (ex.toString().contains("UnknownHostException")) {
                            JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                        }
                    }
                }
                
                if(sukses==true){
                    if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                        Sequel.queryu("delete from tampjurnal");
                        if(Sequel.menyimpantf2("tampjurnal","'"+Sequel.cariIsi("select set_akun.Piutang_Obat from set_akun")+"','PIUTANG OBAT','"+ttl+"','0'","Rekening")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf2("tampjurnal","'"+Sequel.cariIsi("select set_akun.Kontra_Piutang_Obat from set_akun")+"','PERSEDIAAN','0','"+ttl+"'","Rekening")==false){
                            sukses=false;
                        }
                        if(sukses==true){
                            sukses=jur.simpanJurnal(TNoRW.getText(),"U","PIUTANG DI "+nmgudang.getText().toUpperCase()+", OLEH "+akses.getkode()); 
                        }  
                    }   
                }
                
                if(sukses==true){
                    Sequel.Commit();
                }else{
                    sukses=false;
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                if(sukses==true){
                    dispose();
                }
            } 
        }                 
    }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,BtnKeluar,cmbJam);
    }//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,BtnGudang);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if(this.isVisible()==true){
            hitungObat();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void tbDetailObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanKeyPressed
        if(tbDetailObatRacikan.getRowCount()!=0){
            if(tbDetailObatRacikan.getSelectedRow()!= -1){
                if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)){
                    i=tbDetailObatRacikan.getSelectedColumn();
                    if((i==4)||(i==5)||(i==6)){
                        Double signasatu=Valid.SetAngka(tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),4).toString());
                        Double signadua=Valid.SetAngka(tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),5).toString());
                        Double hari=Valid.SetAngka(tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),6).toString());
                        tbDetailObatRacikan.setValueAt((signadua*signasatu*hari),tbDetailObatRacikan.getSelectedRow(),1);
                    }else if(i==7){
                        if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                            sisacari=0;
                            try {
                                psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                                try {
                                    psstok.setString(1,kdgudang.getText());
                                    psstok.setString(2,tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString());
                                    rsstok=psstok.executeQuery();
                                    if(rsstok.next()){
                                        sisacari=rsstok.getDouble(1);
                                    }                                
                                } catch (Exception e) {
                                    sisacari=0;
                                    System.out.println("Notifikasi : "+e);
                                }finally{
                                    if(rsstok != null){
                                        rsstok.close();
                                    }
                                    if(psstok != null){
                                        psstok.close();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            }
                            tbDetailObatRacikan.setValueAt(sisacari,tbDetailObatRacikan.getSelectedRow(),10);
                        }
                    }
                }
            }  
        }
    }//GEN-LAST:event_tbDetailObatRacikanKeyPressed

    private void tbDetailObatRacikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanPropertyChange
        if(this.isVisible()==true){
            hitungObat();
        }
    }//GEN-LAST:event_tbDetailObatRacikanPropertyChange

    private void tbDetailObatRacikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanMouseClicked
        if(tbDetailObatRacikan.getRowCount()!=0){
            if(tbDetailObatRacikan.getSelectedRow()!= -1){
                i=tbDetailObatRacikan.getSelectedColumn();
                if((i==4)||(i==5)||(i==6)){
                    Double signasatu=Valid.SetAngka(tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),4).toString());
                    Double signadua=Valid.SetAngka(tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),5).toString());
                    Double hari=Valid.SetAngka(tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),6).toString());
                    tbDetailObatRacikan.setValueAt((signadua*signasatu*hari),tbDetailObatRacikan.getSelectedRow(),1);
                }else if(i==7){
                    if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                        sisacari=0;
                        try {
                            psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                            try {
                                psstok.setString(1,kdgudang.getText());
                                psstok.setString(2,tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString());
                                rsstok=psstok.executeQuery();
                                if(rsstok.next()){
                                    sisacari=rsstok.getDouble(1);
                                }                                
                            } catch (Exception e) {
                                sisacari=0;
                                System.out.println("Notifikasi : "+e);
                            }finally{
                                if(rsstok != null){
                                    rsstok.close();
                                }
                                if(psstok != null){
                                    psstok.close();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        }
                        tbDetailObatRacikan.setValueAt(sisacari,tbDetailObatRacikan.getSelectedRow(),10);
                    }
                }
            }  
        }
    }//GEN-LAST:event_tbDetailObatRacikanMouseClicked

    private void CariDataObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CariDataObatActionPerformed
        ApotekBPJSDaftarResepObat daftar=new ApotekBPJSDaftarResepObat(null,true);
        daftar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        daftar.setLocationRelativeTo(internalFrame1);
        daftar.setVisible(true);
    }//GEN-LAST:event_CariDataObatActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObat.getSelectedRow()!= -1) {
            int reply = JOptionPane.showConfirmDialog(rootPane,"Yakin mau dihapus obat "+tbObat.getValueAt(tbObat.getSelectedRow(), 3)+" ("+tbObat.getValueAt(tbObat.getSelectedRow(), 1)+") ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                tabModeobat.removeRow(tbObat.getSelectedRow());
            }
        }
        
        if (tbDetailObatRacikan.getSelectedRow()!= -1) {
            int reply = JOptionPane.showConfirmDialog(rootPane,"Yakin mau dihapus obat RACIKAN "+tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 3)+" ("+tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 1)+") ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                tabModeDetailObatRacikan.removeRow(tbDetailObatRacikan.getSelectedRow());
            }
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnKeluar, CariDataObat);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
            BtnSimpan.requestFocus();
            break;
            case KeyEvent.VK_UP:
            BtnGudangActionPerformed(null);
            break;
        }
    }//GEN-LAST:event_kdgudangKeyPressed

    private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
        if (lokasidepo == null || !lokasidepo.isDisplayable()) {
            lokasidepo=new DlgCariBangsal(null,false);
            lokasidepo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            lokasidepo.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(lokasidepo.getTable().getSelectedRow()!= -1){                   
                        kdgudang.setText(lokasidepo.getTable().getValueAt(lokasidepo.getTable().getSelectedRow(),0).toString());
                        nmgudang.setText(lokasidepo.getTable().getValueAt(lokasidepo.getTable().getSelectedRow(),1).toString());
                    } 
                    kdgudang.requestFocus();
                    lokasidepo=null;
                }
            }); 

            lokasidepo.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            lokasidepo.setLocationRelativeTo(internalFrame1);
        }
        if (lokasidepo == null) return;
        if (!lokasidepo.isVisible()) {
            lokasidepo.isCek();    
            lokasidepo.emptTeks();
        }

        if (lokasidepo.isVisible()) {
            lokasidepo.toFront();
            return;
        }
        lokasidepo.setVisible(true);
    }//GEN-LAST:event_BtnGudangActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ApotekBPJSInputResepObat dialog = new ApotekBPJSInputResepObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnGudang;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button CariDataObat;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private javax.swing.JComboBox<String> Iterasi;
    private javax.swing.JComboBox<String> JnsObat;
    private widget.TextBox Kd2;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdPj;
    private widget.TextBox KdPoli;
    private widget.Label LPpn;
    private widget.Label LTotal;
    private widget.Label LTotalTagihan;
    private widget.Label LabelDepo;
    private widget.Label LabelPPN;
    private widget.Label LabelTotal;
    private widget.Label LabelTotalTagihan;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmPasien;
    private widget.TextBox NmPoli;
    private widget.TextBox NoKartu;
    private widget.TextBox NoResep;
    private widget.TextBox NoSEP;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRW;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox Tanggal;
    private widget.TextBox TglResep;
    private widget.TextBox TglSEP;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdgudang;
    private widget.TextBox nmgudang;
    private widget.panelisi panelisi3;
    private widget.Table tbDetailObatRacikan;
    private widget.Table tbObat;
    private widget.Table tbObatRacikan;
    // End of variables declaration//GEN-END:variables
    
    public void emptTeksobat() {
        Kd2.setText(""); 
    }

    public JTextField getTextField(){
        return Kd2;
    }

    public JTable getTable(){
        return tbObat;
    }
    
    public Button getButton(){
        return BtnSimpan;
    }
    
    public void setNoRm(String norwt,String norm,String nama,String tanggal,String jam,String resep,String no_sep,String no_kartu,String kdpolitujuan,String nmpolitujuan,String kddpjp,String nmdpdjp,String tglsep) {  
        TNoRW.setText(norwt);
        TNoRM.setText(norm);
        NmPasien.setText(nama);
        TPasien.setText(norm+" "+nama);
        TglResep.setText(tanggal+" "+jam);
        NoResep.setText(resep);
        NoKartu.setText(no_kartu);
        TglSEP.setText(tglsep+" "+Sequel.cariIsi("select reg_periksa.jam_reg from reg_periksa where no_rawat=?",norwt));
        NoSEP.setText(no_sep);
        KdDPJP.setText(kddpjp);
        NmDPJP.setText(nmdpdjp);
        KdPoli.setText(kdpolitujuan);
        NmPoli.setText(nmpolitujuan);
        KdPj.setText(Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",norwt));
        kenaikan=Sequel.cariIsiAngka("select (set_harga_obat_ralan.hargajual/100) from set_harga_obat_ralan where set_harga_obat_ralan.kd_pj=?",KdPj.getText());
        
        if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
            if(!DEPOAKTIFOBAT.equals("")){
                kdgudang.setText(DEPOAKTIFOBAT);
                nmgudang.setText(Sequel.CariBangsal(DEPOAKTIFOBAT));
            }else{
                bangsal=Sequel.cariIsi("select set_depo_ralan.kd_bangsal from set_depo_ralan where set_depo_ralan.kd_poli=?",Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",norwt));
                if(bangsal.equals("")){
                    bangsal=bangsaldefault;
                }     
                kdgudang.setText(bangsal);
                nmgudang.setText(Sequel.CariBangsal(kdgudang.getText()));
            }
        }
            
        
        try {
            Valid.tabelKosong(tabModeobat);
            Valid.tabelKosong(tabModeObatRacikan);
            Valid.tabelKosong(tabModeDetailObatRacikan);
            if(kenaikan>0){
                psobat=koneksi.prepareStatement(
                    "select maping_obat_apotek_bpjs.kode_brng_apotek_bpjs,maping_obat_apotek_bpjs.nama_brng_apotek_bpjs,databarang.kode_brng,databarang.kode_sat,databarang.expire,"+
                    "(databarang.h_beli+(databarang.h_beli*?)) as harga,databarang."+hppfarmasi+" as dasar,resep_dokter.jml,resep_dokter.aturan_pakai from databarang "+
                    "inner join resep_dokter on resep_dokter.kode_brng=databarang.kode_brng "+
                    "inner join maping_obat_apotek_bpjs on databarang.kode_brng=maping_obat_apotek_bpjs.kode_brng "+
                    "where resep_dokter.no_resep=? and databarang.status='1'"
                );
                try{
                    psobat.setDouble(1,kenaikan);
                    psobat.setString(2,resep);
                    rsobat=psobat.executeQuery();
                    while(rsobat.next()){
                        double jumlahpiutang=0,jumlahralan=0;
                        if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                            sisacari=0;
                            psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                            try {
                                psstok.setString(1,kdgudang.getText());
                                psstok.setString(2,rsobat.getString("kode_brng"));
                                rsstok=psstok.executeQuery();
                                if(rsstok.next()){
                                    sisacari=rsstok.getDouble(1);
                                }                                
                            } catch (Exception e) {
                                sisacari=0;
                                System.out.println("Notifikasi : "+e);
                            }finally{
                                if(rsstok != null){
                                    rsstok.close();
                                }
                                if(psstok != null){
                                    psstok.close();
                                }
                            }
                            
                            jumlahralan=Sequel.cariIsiAngka("select detail_pemberian_obat.jml from detail_pemberian_obat where detail_pemberian_obat.no_rawat='"+norwt+"' and detail_pemberian_obat.kode_brng='"+rsobat.getString("kode_brng")+"' and detail_pemberian_obat.tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"'");
                            if((rsobat.getDouble("jml")-jumlahralan)>sisacari){
                                jumlahpiutang=sisacari;
                                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            }else{
                                jumlahpiutang=rsobat.getDouble("jml")-jumlahralan;
                            }
                        }   
                        
                        arrSplit = rsobat.getString("aturan_pakai").toLowerCase().split("x");
                        signa1="1";
                        try {
                            if(!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")){
                                signa1=arrSplit[0].replaceAll("[^0-9.]+", "");
                            }
                        } catch (Exception e) {
                            signa1="1";
                        }
                        signa2="1";
                        try {
                            if(!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")){
                                signa2=arrSplit[1].replaceAll("[^0-9.]+", "");
                            }
                        } catch (Exception e) {
                            signa2="1";
                        } 
                        
                        tabModeobat.addRow(new Object[] {
                           rsobat.getString("jml"),rsobat.getString("kode_brng_apotek_bpjs"),rsobat.getString("nama_brng_apotek_bpjs"),signa1,signa2,"30",jumlahpiutang,rsobat.getDouble("dasar"),Valid.roundUp(rsobat.getDouble("harga"),100),sisacari,rsobat.getString("aturan_pakai"),rsobat.getString("kode_brng"),rsobat.getString("kode_sat"),"","",rsobat.getString("expire")
                        });  
                    }  
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }
                    if(psobat != null){
                        psobat.close();
                    }
                }                                                      
            }else{   
                psobat=koneksi.prepareStatement(
                    "select maping_obat_apotek_bpjs.kode_brng_apotek_bpjs,maping_obat_apotek_bpjs.nama_brng_apotek_bpjs,databarang.kode_brng,databarang.kode_sat,databarang.expire,"+
                    "databarang.ralan as harga,databarang."+hppfarmasi+" as dasar,resep_dokter.jml,resep_dokter.aturan_pakai from databarang "+
                    "inner join resep_dokter on resep_dokter.kode_brng=databarang.kode_brng "+
                    "inner join maping_obat_apotek_bpjs on databarang.kode_brng=maping_obat_apotek_bpjs.kode_brng "+
                    "where resep_dokter.no_resep=? and databarang.status='1'"
                );
                try{
                    psobat.setString(1,resep);
                    rsobat=psobat.executeQuery();
                    while(rsobat.next()){
                        double jumlahpiutang=0,jumlahralan=0;
                        if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                            sisacari=0;
                            psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                            try {
                                psstok.setString(1,kdgudang.getText());
                                psstok.setString(2,rsobat.getString("kode_brng"));
                                rsstok=psstok.executeQuery();
                                if(rsstok.next()){
                                    sisacari=rsstok.getDouble(1);
                                }                                
                            } catch (Exception e) {
                                sisacari=0;
                                System.out.println("Notifikasi : "+e);
                            }finally{
                                if(rsstok != null){
                                    rsstok.close();
                                }
                                if(psstok != null){
                                    psstok.close();
                                }
                            }
                            
                            jumlahralan=Sequel.cariIsiAngka("select detail_pemberian_obat.jml from detail_pemberian_obat where detail_pemberian_obat.no_rawat='"+norwt+"' and detail_pemberian_obat.kode_brng='"+rsobat.getString("kode_brng")+"' and detail_pemberian_obat.tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"'");
                            if((rsobat.getDouble("jml")-jumlahralan)>sisacari){
                                jumlahpiutang=sisacari;
                                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            }else{
                                jumlahpiutang=rsobat.getDouble("jml")-jumlahralan;
                            }
                        }   
                        
                        arrSplit = rsobat.getString("aturan_pakai").toLowerCase().split("x");
                        signa1="1";
                        try {
                            if(!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")){
                                signa1=arrSplit[0].replaceAll("[^0-9.]+", "");
                            }
                        } catch (Exception e) {
                            signa1="1";
                        }
                        signa2="1";
                        try {
                            if(!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")){
                                signa2=arrSplit[1].replaceAll("[^0-9.]+", "");
                            }
                        } catch (Exception e) {
                            signa2="1";
                        } 
                        
                        tabModeobat.addRow(new Object[] {
                           rsobat.getString("jml"),rsobat.getString("kode_brng_apotek_bpjs"),rsobat.getString("nama_brng_apotek_bpjs"),signa1,signa2,"30",jumlahpiutang,rsobat.getDouble("dasar"),Valid.roundUp(rsobat.getDouble("harga"),100),sisacari,rsobat.getString("aturan_pakai"),rsobat.getString("kode_brng"),rsobat.getString("kode_sat"),"","",rsobat.getString("expire")
                        });              
                    }
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }

                    if(psobat != null){
                        psobat.close();
                    }
                }
            } 
            
            psobat=koneksi.prepareStatement(
                    "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                    "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                    "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                    "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                    "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                    "resep_dokter_racikan.no_resep=? ");
            try {
                psobat.setString(1,resep);
                rsobat=psobat.executeQuery();
                if(rsobat.next()){
                    TabRawat.setSelectedIndex(1);
                    do{
                        tabModeObatRacikan.addRow(new Object[]{
                            rsobat.getString("no_racik"),rsobat.getString("nama_racik"),rsobat.getString("kd_racik"),
                            rsobat.getString("metode"),rsobat.getString("jml_dr"),rsobat.getString("aturan_pakai"),
                            rsobat.getString("keterangan")
                        });
                        
                        arrSplit = rsobat.getString("aturan_pakai").toLowerCase().split("x");
                        signa1="1";
                        try {
                            if(!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")){
                                signa1=arrSplit[0].replaceAll("[^0-9.]+", "");
                            }
                        } catch (Exception e) {
                            signa1="1";
                        }
                        signa2="1";
                        try {
                            if(!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")){
                                signa2=arrSplit[1].replaceAll("[^0-9.]+", "");
                            }
                        } catch (Exception e) {
                            signa2="1";
                        }
                        
                        if(kenaikan>0){
                            ps2=koneksi.prepareStatement(
                                "select maping_obat_apotek_bpjs.kode_brng_apotek_bpjs,maping_obat_apotek_bpjs.nama_brng_apotek_bpjs,resep_dokter_racikan_detail.jml,"+
                                "databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,databarang."+hppfarmasi+" as dasar,databarang.kode_brng,databarang.expire "+
                                "from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                "inner join maping_obat_apotek_bpjs on databarang.kode_brng=maping_obat_apotek_bpjs.kode_brng "+
                                "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=?"
                            );
                            try {
                                ps2.setDouble(1,kenaikan);
                                ps2.setString(2,resep);
                                ps2.setString(3,rsobat.getString("no_racik"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    double jumlahpiutang=0,jumlahralan=0;
                                    if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                                        sisacari=0;
                                        psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                                        try {
                                            psstok.setString(1,kdgudang.getText());
                                            psstok.setString(2,rs2.getString("kode_brng"));
                                            rsstok=psstok.executeQuery();
                                            if(rsstok.next()){
                                                sisacari=rsstok.getDouble(1);
                                            }                                
                                        } catch (Exception e) {
                                            sisacari=0;
                                            System.out.println("Notifikasi : "+e);
                                        }finally{
                                            if(rsstok != null){
                                                rsstok.close();
                                            }
                                            if(psstok != null){
                                                psstok.close();
                                            }
                                        }
                                        jumlahralan=Sequel.cariIsiAngka("select detail_pemberian_obat.jml from detail_pemberian_obat where detail_pemberian_obat.no_rawat='"+norwt+"' and detail_pemberian_obat.kode_brng='"+rs2.getString("kode_brng")+"' and detail_pemberian_obat.tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"'");
                                        if((rs2.getDouble("jml")-jumlahralan)>sisacari){
                                            jumlahpiutang=sisacari;
                                            JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                        }else{
                                            jumlahpiutang=(rs2.getDouble("jml")-jumlahralan);
                                        } 
                                    }
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rsobat.getString("no_racik"),rs2.getDouble("jml"),rs2.getString("kode_brng_apotek_bpjs"),rs2.getString("nama_brng_apotek_bpjs"),signa1,signa2,"30",jumlahpiutang,rs2.getDouble("dasar"),Valid.roundUp(rs2.getDouble("harga"),100),sisacari,rs2.getString("kode_brng"),rs2.getString("kode_sat"),"","",rs2.getString("expire")                                    
                                    });                   
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                        }else{
                            ps2=koneksi.prepareStatement(
                                "select maping_obat_apotek_bpjs.kode_brng_apotek_bpjs,maping_obat_apotek_bpjs.nama_brng_apotek_bpjs,resep_dokter_racikan_detail.jml,"+
                                "databarang.kode_sat,databarang.ralan as harga,databarang."+hppfarmasi+" as dasar,databarang.kode_brng,databarang.expire "+
                                "from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                "inner join maping_obat_apotek_bpjs on databarang.kode_brng=maping_obat_apotek_bpjs.kode_brng "+
                                "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=?"
                            );
                            try {
                                ps2.setString(1,resep);
                                ps2.setString(2,rsobat.getString("no_racik"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    double jumlahpiutang=0,jumlahralan=0;
                                    if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                                        sisacari=0;
                                        psstok=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                                        try {
                                            psstok.setString(1,kdgudang.getText());
                                            psstok.setString(2,rs2.getString("kode_brng"));
                                            rsstok=psstok.executeQuery();
                                            if(rsstok.next()){
                                                sisacari=rsstok.getDouble(1);
                                            }                                
                                        } catch (Exception e) {
                                            sisacari=0;
                                            System.out.println("Notifikasi : "+e);
                                        }finally{
                                            if(rsstok != null){
                                                rsstok.close();
                                            }
                                            if(psstok != null){
                                                psstok.close();
                                            }
                                        }
                                        jumlahralan=Sequel.cariIsiAngka("select detail_pemberian_obat.jml from detail_pemberian_obat where detail_pemberian_obat.no_rawat='"+norwt+"' and detail_pemberian_obat.kode_brng='"+rs2.getString("kode_brng")+"' and detail_pemberian_obat.tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"'");
                                        if((rs2.getDouble("jml")-jumlahralan)>sisacari){
                                            jumlahpiutang=sisacari;
                                            JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                        }else{
                                            jumlahpiutang=(rs2.getDouble("jml")-jumlahralan);
                                        } 
                                    }
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rsobat.getString("no_racik"),rs2.getDouble("jml"),rs2.getString("kode_brng_apotek_bpjs"),rs2.getString("nama_brng_apotek_bpjs"),signa1,signa2,"30",jumlahpiutang,rs2.getDouble("dasar"),Valid.roundUp(rs2.getDouble("harga"),100),sisacari,rs2.getString("kode_brng"),rs2.getString("kode_sat"),"","",rs2.getString("expire")                                    
                                    });                            
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                        }
                    }while(rsobat.next());  
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 2 : "+e);
            } finally{
                if(rsobat!=null){
                    rsobat.close();
                }
                if(psobat!=null){
                    psobat.close();
                }
            }
            hitungObat();
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        } 
    }
    
    private void hitungObat() {
        ttl=0;
        y=0;
        row2=tabModeobat.getRowCount();
        for(r=0;r<row2;r++){ 
            try {
                if(Double.parseDouble(tabModeobat.getValueAt(r,6).toString())>0){
                    try {                
                        y=Math.round(Double.parseDouble(tabModeobat.getValueAt(r,6).toString())*Double.parseDouble(tabModeobat.getValueAt(r,8).toString()));                                                
                    } catch (Exception e) {
                        y=0;
                    }
                    ttl=ttl+y;
                    if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                        Double piutang=Valid.SetAngka(tabModeobat.getValueAt(r,6).toString());
                        Double stok=Valid.SetAngka(tabModeobat.getValueAt(r,9).toString());
                        if(piutang>stok){
                            JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            tbObat.setValueAt(stok,r,6);
                        }
                    }
                }  
            } catch (Exception e) {
            }                           
        }
        row2=tabModeDetailObatRacikan.getRowCount();
        for(r=0;r<row2;r++){ 
            try {
                if(Double.parseDouble(tabModeDetailObatRacikan.getValueAt(r,7).toString())>0){
                    try {
                        y=Math.round(Double.parseDouble(tabModeDetailObatRacikan.getValueAt(r,7).toString())*Double.parseDouble(tabModeDetailObatRacikan.getValueAt(r,9).toString()));
                    } catch (Exception e) {
                        y=0;
                    }
                    ttl=ttl+y;
                    if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                        Double piutang=Valid.SetAngka(tabModeDetailObatRacikan.getValueAt(r,7).toString());
                        Double stok=Valid.SetAngka(tabModeDetailObatRacikan.getValueAt(r,10).toString());
                        if(piutang>stok){
                            JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            tbDetailObatRacikan.setValueAt(stok,r,7);
                        }
                    }
                }
            } catch (Exception e) {
            }    
        }
        LTotal.setText(Valid.SetAngka(ttl));
        ppnobat=0;
        if(tampilkan_ppnobat_ralan.equals("Yes")){
            ppnobat=Math.round(ttl*0.11);
            ttl=ttl+ppnobat;
            LPpn.setText(Valid.SetAngka(ppnobat));
            LTotalTagihan.setText(Valid.SetAngka(ttl));
        }
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
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
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
}

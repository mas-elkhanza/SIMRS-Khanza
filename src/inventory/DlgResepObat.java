/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgResepObat.java
 *
 * Created on 31 Mei 10, 11:27:40
 */

package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
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
public final class DlgResepObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabmodeUbahRacikan,tabmodeUbahRacikan2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,psracikan;
    private ResultSet rs,rs2,rsracikan;
    private DlgCariDokter dokter;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date = new Date();
    private String now=dateFormat.format(date),lembarobat="",status="",rincianobat="",finger="";
    private double total=0,jumlahtotal=0;
    private int i=0,getno=0;
    private DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    private String TANGGALMUNDUR="yes",norawat="";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgResepObat 
     *@param parent
     *@param modal*/
    public DlgResepObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"No.Resep","Tgl.Resep","Pasien","Dokter Peresep"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbResep.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(300);
            }else if(i==3){
                column.setPreferredWidth(200);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabmodeUbahRacikan=new DefaultTableModel(null,new Object[]{
                "Tgl.Rawat","Jam Rawat","No.Rawat","Kode Barang","Nama Barang","Aturan Pakai"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==5) {
                    a=true;
                }
                return a;
             }
         };
        tbTambahan.setModel(tabmodeUbahRacikan);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbTambahan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTambahan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbTambahan.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(250);
            }else if(i==5){
                column.setPreferredWidth(200);
            }
        }
        tbTambahan.setDefaultRenderer(Object.class, new WarnaTable());

        tabmodeUbahRacikan2=new DefaultTableModel(null,new Object[]{
                "Tgl.Rawat","Jam Rawat","No.Rawat","No.Racik","Nama Racik","Aturan Pakai"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==5) {
                    a=true;
                }
                return a;
             }
         };
        tbTambahan1.setModel(tabmodeUbahRacikan2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbTambahan1.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTambahan1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbTambahan1.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(250);
            }else if(i==5){
                column.setPreferredWidth(200);
            }
        }
        tbTambahan1.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        NoResep.setDocument(new batasInput((byte)10).getKata(NoResep));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
         
        ChkInput.setSelected(false);
        isForm();
        
        try {
            lembarobat=koneksiDB.CETAKRINCIANOBAT();
        } catch (Exception ex) {
            lembarobat="";
        }
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
        
        Valid.SetTgl2(DTPCari1,format.format(new Date())+" 00:00:00");
        Valid.SetTgl2(DTPCari2,format.format(new Date())+" 23:59:59"); 
        
        ChkAccor.setSelected(false);
        isPhoto();
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup2 = new javax.swing.JPopupMenu();
        ppResepObat = new javax.swing.JMenuItem();
        ppResepObat1 = new javax.swing.JMenuItem();
        ppResepObat2 = new javax.swing.JMenuItem();
        ppLabelDataObat = new javax.swing.JMenuItem();
        ppLembarObat = new javax.swing.JMenuItem();
        ppLembarObat1 = new javax.swing.JMenuItem();
        ppLembarObat2 = new javax.swing.JMenuItem();
        ppUbahAturanPakai = new javax.swing.JMenuItem();
        ppUbahAturanPakai1 = new javax.swing.JMenuItem();
        WindowInput3 = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbTambahan = new widget.Table();
        panelisi1 = new widget.panelisi();
        label15 = new widget.Label();
        NoResepUbah = new widget.TextBox();
        BtnSimpan3 = new widget.Button();
        BtnKeluar1 = new widget.Button();
        WindowInput4 = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbTambahan1 = new widget.Table();
        panelisi2 = new widget.panelisi();
        label16 = new widget.Label();
        NoResepUbah1 = new widget.TextBox();
        BtnSimpan4 = new widget.Button();
        BtnKeluar2 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbResep = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
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
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel13 = new widget.Label();
        btnDokter = new widget.Button();
        jLabel11 = new widget.Label();
        NoResep = new widget.TextBox();
        jLabel8 = new widget.Label();
        DTPBeri = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkRM = new widget.CekBox();
        TNoRm = new widget.TextBox();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        TabData = new javax.swing.JTabbedPane();
        FormTelaah = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        BtnTelaah = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();

        Popup2.setName("Popup2"); // NOI18N

        ppResepObat.setBackground(new java.awt.Color(255, 255, 254));
        ppResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppResepObat.setForeground(new java.awt.Color(50, 50, 50));
        ppResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppResepObat.setText("Cetak Aturan Pakai Model 1");
        ppResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppResepObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppResepObat.setName("ppResepObat"); // NOI18N
        ppResepObat.setPreferredSize(new java.awt.Dimension(225, 25));
        ppResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppResepObatActionPerformed(evt);
            }
        });
        Popup2.add(ppResepObat);

        ppResepObat1.setBackground(new java.awt.Color(255, 255, 254));
        ppResepObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppResepObat1.setForeground(new java.awt.Color(50, 50, 50));
        ppResepObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppResepObat1.setText("Cetak Aturan Pakai Model 2");
        ppResepObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppResepObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppResepObat1.setName("ppResepObat1"); // NOI18N
        ppResepObat1.setPreferredSize(new java.awt.Dimension(225, 25));
        ppResepObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppResepObat1ActionPerformed(evt);
            }
        });
        Popup2.add(ppResepObat1);

        ppResepObat2.setBackground(new java.awt.Color(255, 255, 254));
        ppResepObat2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppResepObat2.setForeground(new java.awt.Color(50, 50, 50));
        ppResepObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppResepObat2.setText("Cetak Aturan Pakai Model 3");
        ppResepObat2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppResepObat2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppResepObat2.setName("ppResepObat2"); // NOI18N
        ppResepObat2.setPreferredSize(new java.awt.Dimension(225, 25));
        ppResepObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppResepObat2ActionPerformed(evt);
            }
        });
        Popup2.add(ppResepObat2);

        ppLabelDataObat.setBackground(new java.awt.Color(255, 255, 254));
        ppLabelDataObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppLabelDataObat.setForeground(new java.awt.Color(50, 50, 50));
        ppLabelDataObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppLabelDataObat.setText("Label Daftar Obat");
        ppLabelDataObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppLabelDataObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppLabelDataObat.setName("ppLabelDataObat"); // NOI18N
        ppLabelDataObat.setPreferredSize(new java.awt.Dimension(225, 25));
        ppLabelDataObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppLabelDataObatActionPerformed(evt);
            }
        });
        Popup2.add(ppLabelDataObat);

        ppLembarObat.setBackground(new java.awt.Color(255, 255, 254));
        ppLembarObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppLembarObat.setForeground(new java.awt.Color(50, 50, 50));
        ppLembarObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppLembarObat.setText("Lembar Pemberian Obat 1");
        ppLembarObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppLembarObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppLembarObat.setName("ppLembarObat"); // NOI18N
        ppLembarObat.setPreferredSize(new java.awt.Dimension(225, 25));
        ppLembarObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppLembarObatActionPerformed(evt);
            }
        });
        Popup2.add(ppLembarObat);

        ppLembarObat1.setBackground(new java.awt.Color(255, 255, 254));
        ppLembarObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppLembarObat1.setForeground(new java.awt.Color(50, 50, 50));
        ppLembarObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppLembarObat1.setText("Lembar Pemberian Obat 2");
        ppLembarObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppLembarObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppLembarObat1.setName("ppLembarObat1"); // NOI18N
        ppLembarObat1.setPreferredSize(new java.awt.Dimension(225, 25));
        ppLembarObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppLembarObat1ActionPerformed(evt);
            }
        });
        Popup2.add(ppLembarObat1);

        ppLembarObat2.setBackground(new java.awt.Color(255, 255, 254));
        ppLembarObat2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppLembarObat2.setForeground(new java.awt.Color(50, 50, 50));
        ppLembarObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppLembarObat2.setText("Lembar Pemberian Obat 3");
        ppLembarObat2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppLembarObat2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppLembarObat2.setName("ppLembarObat2"); // NOI18N
        ppLembarObat2.setPreferredSize(new java.awt.Dimension(225, 25));
        ppLembarObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppLembarObat2ActionPerformed(evt);
            }
        });
        Popup2.add(ppLembarObat2);

        ppUbahAturanPakai.setBackground(new java.awt.Color(255, 255, 254));
        ppUbahAturanPakai.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUbahAturanPakai.setForeground(new java.awt.Color(50, 50, 50));
        ppUbahAturanPakai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUbahAturanPakai.setText("Ubah Aturan Pakai Obat Umum");
        ppUbahAturanPakai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUbahAturanPakai.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUbahAturanPakai.setName("ppUbahAturanPakai"); // NOI18N
        ppUbahAturanPakai.setPreferredSize(new java.awt.Dimension(225, 25));
        ppUbahAturanPakai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUbahAturanPakaiActionPerformed(evt);
            }
        });
        Popup2.add(ppUbahAturanPakai);

        ppUbahAturanPakai1.setBackground(new java.awt.Color(255, 255, 254));
        ppUbahAturanPakai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUbahAturanPakai1.setForeground(new java.awt.Color(50, 50, 50));
        ppUbahAturanPakai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUbahAturanPakai1.setText("Ubah Aturan Pakai Obat Racikan");
        ppUbahAturanPakai1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUbahAturanPakai1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUbahAturanPakai1.setName("ppUbahAturanPakai1"); // NOI18N
        ppUbahAturanPakai1.setPreferredSize(new java.awt.Dimension(225, 25));
        ppUbahAturanPakai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUbahAturanPakai1ActionPerformed(evt);
            }
        });
        Popup2.add(ppUbahAturanPakai1);

        WindowInput3.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput3.setName("WindowInput3"); // NOI18N
        WindowInput3.setUndecorated(true);
        WindowInput3.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Aturan Pakai Obat Umum ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbTambahan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTambahan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTambahan.setName("tbTambahan"); // NOI18N
        tbTambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTambahanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbTambahan);

        internalFrame4.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label15.setText("No.Resep :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label15);

        NoResepUbah.setEditable(false);
        NoResepUbah.setName("NoResepUbah"); // NOI18N
        NoResepUbah.setPreferredSize(new java.awt.Dimension(150, 23));
        NoResepUbah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepUbahKeyPressed(evt);
            }
        });
        panelisi1.add(NoResepUbah);

        BtnSimpan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan3.setMnemonic('S');
        BtnSimpan3.setText("Simpan");
        BtnSimpan3.setToolTipText("Alt+S");
        BtnSimpan3.setName("BtnSimpan3"); // NOI18N
        BtnSimpan3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan3ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnSimpan3);

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnKeluar1);

        internalFrame4.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowInput3.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        WindowInput4.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput4.setName("WindowInput4"); // NOI18N
        WindowInput4.setUndecorated(true);
        WindowInput4.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Aturan Pakai Obat Racik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbTambahan1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTambahan1.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTambahan1.setName("tbTambahan1"); // NOI18N
        tbTambahan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTambahan1KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbTambahan1);

        internalFrame5.add(scrollPane2, java.awt.BorderLayout.CENTER);

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label16.setText("No.Resep :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(label16);

        NoResepUbah1.setEditable(false);
        NoResepUbah1.setName("NoResepUbah1"); // NOI18N
        NoResepUbah1.setPreferredSize(new java.awt.Dimension(150, 23));
        NoResepUbah1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepUbah1KeyPressed(evt);
            }
        });
        panelisi2.add(NoResepUbah1);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        panelisi2.add(BtnSimpan4);

        BtnKeluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar2.setMnemonic('K');
        BtnKeluar2.setText("Keluar");
        BtnKeluar2.setToolTipText("Alt+K");
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        panelisi2.add(BtnKeluar2);

        internalFrame5.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        WindowInput4.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Resep Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbResep.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbResep.setComponentPopupMenu(Popup2);
        tbResep.setName("tbResep"); // NOI18N
        tbResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepMouseClicked(evt);
            }
        });
        tbResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbResepKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbResep);

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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 30));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(52, 30));
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
        jLabel19.setPreferredSize(new java.awt.Dimension(53, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-02-2026 13:30:02" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(132, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-02-2026 13:30:02" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(132, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 107));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(98, 12, 120, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(313, 12, 410, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(98, 72, 120, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(220, 72, 471, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 95, 23);

        jLabel13.setText("Dokter Peresep :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 72, 95, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('3');
        btnDokter.setToolTipText("Alt+3");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(695, 72, 28, 23);

        jLabel11.setText("No.Resep :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(457, 42, 100, 23);

        NoResep.setHighlighter(null);
        NoResep.setName("NoResep"); // NOI18N
        NoResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepKeyPressed(evt);
            }
        });
        FormInput.add(NoResep);
        NoResep.setBounds(560, 42, 138, 23);

        jLabel8.setText("Tgl.Resep :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 42, 95, 23);

        DTPBeri.setForeground(new java.awt.Color(50, 70, 50));
        DTPBeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-02-2026" }));
        DTPBeri.setDisplayFormat("dd-MM-yyyy");
        DTPBeri.setName("DTPBeri"); // NOI18N
        DTPBeri.setOpaque(false);
        DTPBeri.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPBeri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPBeriItemStateChanged(evt);
            }
        });
        DTPBeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPBeriKeyPressed(evt);
            }
        });
        FormInput.add(DTPBeri);
        DTPBeri.setBounds(98, 42, 120, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(220, 42, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(285, 42, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(350, 42, 62, 23);

        ChkRM.setBorder(null);
        ChkRM.setSelected(true);
        ChkRM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRM.setName("ChkRM"); // NOI18N
        ChkRM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRMItemStateChanged(evt);
            }
        });
        FormInput.add(ChkRM);
        ChkRM.setBounds(701, 42, 23, 23);

        TNoRm.setHighlighter(null);
        TNoRm.setName("TNoRm"); // NOI18N
        TNoRm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRmKeyPressed(evt);
            }
        });
        FormInput.add(TNoRm);
        TNoRm.setBounds(220, 12, 91, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        TabData.setBackground(new java.awt.Color(254, 255, 254));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        FormTelaah.setBackground(new java.awt.Color(255, 255, 255));
        FormTelaah.setBorder(null);
        FormTelaah.setName("FormTelaah"); // NOI18N
        FormTelaah.setPreferredSize(new java.awt.Dimension(115, 73));
        FormTelaah.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        BtnTelaah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTelaah.setMnemonic('U');
        BtnTelaah.setText("Telaah");
        BtnTelaah.setToolTipText("Alt+U");
        BtnTelaah.setName("BtnTelaah"); // NOI18N
        BtnTelaah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTelaah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTelaahActionPerformed(evt);
            }
        });
        FormPass3.add(BtnTelaah);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        FormTelaah.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormTelaah.add(Scroll5, java.awt.BorderLayout.CENTER);

        TabData.addTab("Telaah Resep & Obat", FormTelaah);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(null);
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass2.setBackground(new java.awt.Color(255, 255, 255));
        FormPass2.setBorder(null);
        FormPass2.setName("FormPass2"); // NOI18N
        FormPass2.setPreferredSize(new java.awt.Dimension(115, 40));

        BtnRefreshPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto.setMnemonic('U');
        BtnRefreshPhoto.setText("Refresh");
        BtnRefreshPhoto.setToolTipText("Alt+U");
        BtnRefreshPhoto.setName("BtnRefreshPhoto"); // NOI18N
        BtnRefreshPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(BtnRefreshPhoto);

        FormPhoto.add(FormPass2, java.awt.BorderLayout.PAGE_END);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabData.addTab("Photo Penyerahan Resep", FormPhoto);

        PanelAccor.add(TabData, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(NoResep.getText().trim().equals("")){
            Valid.textKosong(NoResep,"No.Resep");
        }else{
            if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                    NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                    cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                    TNoRw.getText(),KdDokter.getText(),"0000-00-00","00:00:00",status,"0000-00-00","00:00:00"
                })==true){
                runBackground(() -> tampil());
                if(lembarobat.equals("yes")){
                    ppLembarObatActionPerformed(null);
                }
                emptTeks();
            }else{
                autoresep2();
                if(Sequel.menyimpantf("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                        NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        TNoRw.getText(),KdDokter.getText(),"0000-00-00","00:00:00",status,"0000-00-00","00:00:00"
                    })==true){
                    runBackground(() -> tampil());
                    if(lembarobat.equals("yes")){
                        ppLembarObatActionPerformed(null);
                    }
                    emptTeks();
                }
            }                    
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,NmDokter,BtnBatal);
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
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.Klik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
           Sequel.meghapus("resep_obat","no_resep",NoResep.getText());
           Sequel.meghapus("resep_dokter","no_resep",NoResep.getText());
           runBackground(() -> tampil());
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal,BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
            Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
            
            for(i=0;i<tabMode.getRowCount();i++){  
                Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                    ""+i,tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,2).toString(),
                    tabMode.getValueAt(i,3).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
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
            Valid.MyReportqry("rptResep.jasper","report","::[ Daftar Pemberian Obat Resep ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
        runBackground(() -> tampil());
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
        runBackground(() -> tampil());        
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            runBackground(() -> tampil());
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, NmDokter);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbResepMouseClicked

    private void tbResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbResepKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,NoResep);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void DTPBeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPBeriKeyPressed
        try {
            if(getno==0){
                autoresep();
            }
        } catch (Exception e) {
        }
        Valid.pindah(evt,TNoRw,cmbJam);
    }//GEN-LAST:event_DTPBeriKeyPressed

    private void NoResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepKeyPressed
        Valid.pindah(evt,cmbDtk,KdDokter);
    }//GEN-LAST:event_NoResepKeyPressed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        Valid.pindah(evt,KdDokter,BtnSimpan);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){        
                         KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                         NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }  
                    KdDokter.requestFocus();
                    dokter=null;
                }
            });
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
        }   
        if (dokter == null) return;
        dokter.isCek();
        if (dokter.isVisible()) {
            dokter.toFront();
            return;
        }
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,NoResep,BtnSimpan);
        }
    }//GEN-LAST:event_KdDokterKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TPasien,TNoRw.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            TCari.requestFocus();
        }else{
            Valid.pindah(evt,KdDokter,DTPBeri);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPBeri,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void ChkRMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRMItemStateChanged
        if(ChkRM.isSelected()==true){
           NoResep.setEditable(false);
           NoResep.setBackground(new Color(245,250,240));
           try {
                if(getno==0){
                    autoresep();
                }
           } catch (Exception e) {
           }
        }else if(ChkRM.isSelected()==false){
           NoResep.setEditable(true);
           NoResep.setBackground(new Color(250,255,245));
           NoResep.setText("");
        }
    }//GEN-LAST:event_ChkRMItemStateChanged

    private void ppResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppResepObatActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Klik No Resep untuk mencetak aturan pakai...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();  
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            if(Sequel.cariInteger(
                    "select count(*) from resep_obat inner join "+
                    "aturan_pakai on resep_obat.no_rawat=aturan_pakai.no_rawat and "+
                    "resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and " +
                    "resep_obat.jam=aturan_pakai.jam where resep_obat.no_resep=? and aturan_pakai.aturan<>''",NoResep.getText())>0){
                Valid.MyReportqry("rptItemResep.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,pasien.tgl_lahir, "+
                    "resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,databarang.nama_brng,"+
                    "aturan_pakai.aturan,detail_pemberian_obat.jml,kodesatuan.satuan,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from resep_obat inner join reg_periksa inner join pasien inner join "+
                    "aturan_pakai inner join databarang inner join detail_pemberian_obat "+
                    "inner join kodesatuan on resep_obat.no_rawat=reg_periksa.no_rawat  "+
                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                    "databarang.kode_brng=aturan_pakai.kode_brng and "+
                    "detail_pemberian_obat.kode_brng=databarang.kode_brng " +
                    "and resep_obat.no_rawat=aturan_pakai.no_rawat and "+
                    "resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and " +
                    "resep_obat.jam=aturan_pakai.jam and resep_obat.no_rawat=detail_pemberian_obat.no_rawat "+
                    "and resep_obat.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and " +
                    "resep_obat.jam=detail_pemberian_obat.jam and kodesatuan.kode_sat=databarang.kode_sat "+
                    "where resep_obat.no_resep='"+NoResep.getText()+"' and aturan_pakai.aturan<>''",param);
            }
            
            if(Sequel.cariInteger(
                    "select count(*) from resep_obat inner join "+
                    "obat_racikan on resep_obat.no_rawat=obat_racikan.no_rawat and "+
                    "resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and " +
                    "resep_obat.jam=obat_racikan.jam where resep_obat.no_resep=? and obat_racikan.aturan_pakai<>''",NoResep.getText())>0){
                Valid.MyReportqry("rptItemResep2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,pasien.tgl_lahir," +
                    "resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,obat_racikan.nama_racik," +
                    "obat_racikan.aturan_pakai,obat_racikan.jml_dr,metode_racik.nm_racik,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur " +
                    "from resep_obat inner join reg_periksa inner join pasien inner join " +
                    "obat_racikan inner join metode_racik on resep_obat.no_rawat=reg_periksa.no_rawat " +
                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "and obat_racikan.kd_racik=metode_racik.kd_racik " +
                    "and resep_obat.no_rawat=obat_racikan.no_rawat and " +
                    "resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and " +
                    "resep_obat.jam=obat_racikan.jam and resep_obat.no_rawat=obat_racikan.no_rawat "+
                    "where resep_obat.no_resep='"+NoResep.getText()+"'",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppResepObatActionPerformed

    private void ppLembarObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppLembarObatActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Klik No Resep untuk mencetak aturan pakai...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();  
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("emailrs",akses.getemailrs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("penanggung",Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText())));               
            param.put("propinsirs",akses.getpropinsirs());
            param.put("tanggal",Valid.SetTgl(DTPBeri.getSelectedItem()+""));
            param.put("norawat",TNoRw.getText());
            param.put("pasien",TPasien.getText());
            param.put("norm",TNoRm.getText());
            param.put("peresep",NmDokter.getText());
            param.put("noresep",NoResep.getText());
            param.put("jam",cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            
            Valid.MyReport("rptLembarObat.jasper",param,"::[ Lembar Pemberian Obat ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppLembarObatActionPerformed

    private void TNoRmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRmKeyPressed

    private void NoResepUbahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepUbahKeyPressed
        Valid.pindah(evt, BtnKeluar, BtnSimpan);
    }//GEN-LAST:event_NoResepUbahKeyPressed

    private void BtnSimpan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan3ActionPerformed
        if(NoResepUbah.getText().trim().equals("")||(tbTambahan.getRowCount()<=0)){
            Valid.textKosong(NoResepUbah,"Data");
        }else{
            
            for(i=0;i<tbTambahan.getRowCount();i++){
                Sequel.queryu2("delete from aturan_pakai where tgl_perawatan=? and jam=? and no_rawat=? and kode_brng=?",4,new String[]{
                    tbTambahan.getValueAt(i,0).toString(),tbTambahan.getValueAt(i,1).toString(),
                    tbTambahan.getValueAt(i,2).toString(),tbTambahan.getValueAt(i,3).toString()
                });
                if(!tbTambahan.getValueAt(i,5).toString().equals("")){
                    Sequel.menyimpan("aturan_pakai","?,?,?,?,?",5,new String[]{
                        tbTambahan.getValueAt(i,0).toString(),tbTambahan.getValueAt(i,1).toString(),
                        tbTambahan.getValueAt(i,2).toString(),tbTambahan.getValueAt(i,3).toString(),
                        tbTambahan.getValueAt(i,5).toString()
                    });
                }
            }
            
            runBackground(() -> tampil());
            WindowInput3.dispose();
        }
    }//GEN-LAST:event_BtnSimpan3ActionPerformed

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        WindowInput3.dispose();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void ppUbahAturanPakaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUbahAturanPakaiActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Klik No Resep untuk ubah aturan pakai...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            NoResepUbah.setText(NoResep.getText());
            runBackground(() -> tampilresep());
            WindowInput3.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            WindowInput3.setLocationRelativeTo(internalFrame1);
            WindowInput3.setVisible(true);
        }
    }//GEN-LAST:event_ppUbahAturanPakaiActionPerformed

    private void ppUbahAturanPakai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUbahAturanPakai1ActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Klik No Resep untuk ubah aturan pakai...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            NoResepUbah1.setText(NoResep.getText());
            runBackground(() -> tampilresep2());
            WindowInput4.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            WindowInput4.setLocationRelativeTo(internalFrame1);
            WindowInput4.setVisible(true);
        }
    }//GEN-LAST:event_ppUbahAturanPakai1ActionPerformed

    private void tbTambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTambahanKeyPressed
        if(tbTambahan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbTambahan.getSelectedColumn();
                if(i==5){
                    DlgCariAturanPakai aturanpakai=new DlgCariAturanPakai(null,false);
                    aturanpakai.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {}
                        @Override
                        public void windowClosing(WindowEvent e) {}
                        @Override
                        public void windowClosed(WindowEvent e) {
                            if(aturanpakai.getTable().getSelectedRow()!= -1){ 
                                tbTambahan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbTambahan.getSelectedRow(),5);
                                tbTambahan.requestFocus();
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
                }
            }
        }            
    }//GEN-LAST:event_tbTambahanKeyPressed

    private void tbTambahan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTambahan1KeyPressed
        if(tbTambahan1.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbTambahan1.getSelectedColumn();
                if(i==5){
                    DlgCariAturanPakai aturanpakai=new DlgCariAturanPakai(null,false);
                    aturanpakai.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {}
                        @Override
                        public void windowClosing(WindowEvent e) {}
                        @Override
                        public void windowClosed(WindowEvent e) {
                            if(aturanpakai.getTable().getSelectedRow()!= -1){ 
                                tbTambahan1.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbTambahan1.getSelectedRow(),5);
                                tbTambahan1.requestFocus();
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
                }
            }
        } 
    }//GEN-LAST:event_tbTambahan1KeyPressed

    private void NoResepUbah1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepUbah1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoResepUbah1KeyPressed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
       if(NoResepUbah1.getText().trim().equals("")||(tbTambahan1.getRowCount()<=0)){
            Valid.textKosong(NoResepUbah1,"Data");
        }else{
            
            for(i=0;i<tbTambahan1.getRowCount();i++){
                if(!tbTambahan1.getValueAt(i,5).toString().equals("")){
                    Sequel.queryu2("update obat_racikan set aturan_pakai=? where tgl_perawatan=? and jam=? and no_rawat=? and no_racik=?",5,new String[]{
                        tbTambahan1.getValueAt(i,5).toString(),tbTambahan1.getValueAt(i,0).toString(),tbTambahan1.getValueAt(i,1).toString(),
                        tbTambahan1.getValueAt(i,2).toString(),tbTambahan1.getValueAt(i,3).toString()
                    });
                }
            }
            
            runBackground(() -> tampil());
            WindowInput4.dispose();
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
        WindowInput4.dispose();
    }//GEN-LAST:event_BtnKeluar2ActionPerformed

    private void ppResepObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppResepObat1ActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Klik No Resep untuk mencetak aturan pakai...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();  
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            if(Sequel.cariInteger(
                    "select count(*) from resep_obat inner join "+
                    "aturan_pakai on resep_obat.no_rawat=aturan_pakai.no_rawat and "+
                    "resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and " +
                    "resep_obat.jam=aturan_pakai.jam where resep_obat.no_resep=? and aturan_pakai.aturan<>''",NoResep.getText())>0){
                Valid.MyReportqry("rptItemResep3.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,pasien.tgl_lahir, "+
                    "resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,databarang.nama_brng,"+
                    "aturan_pakai.aturan,detail_pemberian_obat.jml,kodesatuan.satuan,jenis.nama as jenis,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from resep_obat inner join reg_periksa inner join pasien inner join "+
                    "aturan_pakai inner join databarang inner join detail_pemberian_obat "+
                    "inner join kodesatuan inner join jenis on resep_obat.no_rawat=reg_periksa.no_rawat  "+
                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                    "databarang.kode_brng=aturan_pakai.kode_brng and databarang.kdjns=jenis.kdjns and "+
                    "detail_pemberian_obat.kode_brng=databarang.kode_brng " +
                    "and resep_obat.no_rawat=aturan_pakai.no_rawat and "+
                    "resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and " +
                    "resep_obat.jam=aturan_pakai.jam and resep_obat.no_rawat=detail_pemberian_obat.no_rawat "+
                    "and resep_obat.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and " +
                    "resep_obat.jam=detail_pemberian_obat.jam and kodesatuan.kode_sat=databarang.kode_sat "+
                    "where resep_obat.no_resep='"+NoResep.getText()+"' and aturan_pakai.aturan<>''",param);
            }
            
            if(Sequel.cariInteger(
                    "select count(*) from resep_obat inner join "+
                    "obat_racikan on resep_obat.no_rawat=obat_racikan.no_rawat and "+
                    "resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and " +
                    "resep_obat.jam=obat_racikan.jam where resep_obat.no_resep=? and obat_racikan.aturan_pakai<>''",NoResep.getText())>0){
                Valid.MyReportqry("rptItemResep2.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,pasien.tgl_lahir," +
                    "resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,obat_racikan.nama_racik," +
                    "obat_racikan.aturan_pakai,obat_racikan.jml_dr,metode_racik.nm_racik,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur " +
                    "from resep_obat inner join reg_periksa inner join pasien inner join " +
                    "obat_racikan inner join metode_racik on resep_obat.no_rawat=reg_periksa.no_rawat " +
                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "and obat_racikan.kd_racik=metode_racik.kd_racik " +
                    "and resep_obat.no_rawat=obat_racikan.no_rawat and " +
                    "resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and " +
                    "resep_obat.jam=obat_racikan.jam and resep_obat.no_rawat=obat_racikan.no_rawat "+
                    "where resep_obat.no_resep='"+NoResep.getText()+"'",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppResepObat1ActionPerformed

    private void ppResepObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppResepObat2ActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Klik No Resep untuk mencetak aturan pakai...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();  
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            if(Sequel.cariInteger(
                    "select count(*) from resep_obat inner join "+
                    "aturan_pakai on resep_obat.no_rawat=aturan_pakai.no_rawat and "+
                    "resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and " +
                    "resep_obat.jam=aturan_pakai.jam where resep_obat.no_resep=? and aturan_pakai.aturan<>''",NoResep.getText())>0){
                Valid.MyReportqry("rptItemResep5.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,pasien.tgl_lahir, "+
                    "resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,databarang.nama_brng,"+
                    "aturan_pakai.aturan,detail_pemberian_obat.jml,kodesatuan.satuan,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from resep_obat inner join reg_periksa inner join pasien inner join "+
                    "aturan_pakai inner join databarang inner join detail_pemberian_obat "+
                    "inner join kodesatuan on resep_obat.no_rawat=reg_periksa.no_rawat  "+
                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                    "databarang.kode_brng=aturan_pakai.kode_brng and "+
                    "detail_pemberian_obat.kode_brng=databarang.kode_brng " +
                    "and resep_obat.no_rawat=aturan_pakai.no_rawat and "+
                    "resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and " +
                    "resep_obat.jam=aturan_pakai.jam and resep_obat.no_rawat=detail_pemberian_obat.no_rawat "+
                    "and resep_obat.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and " +
                    "resep_obat.jam=detail_pemberian_obat.jam and kodesatuan.kode_sat=databarang.kode_sat "+
                    "where resep_obat.no_resep='"+NoResep.getText()+"' and aturan_pakai.aturan<>''",param);
            }
            
            if(Sequel.cariInteger(
                    "select count(*) from resep_obat inner join "+
                    "obat_racikan on resep_obat.no_rawat=obat_racikan.no_rawat and "+
                    "resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and " +
                    "resep_obat.jam=obat_racikan.jam where resep_obat.no_resep=? and obat_racikan.aturan_pakai<>''",NoResep.getText())>0){
                Valid.MyReportqry("rptItemResep6.jasper","report","::[ Aturan Pakai Obat ]::",
                    "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,pasien.tgl_lahir," +
                    "resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,obat_racikan.nama_racik," +
                    "obat_racikan.aturan_pakai,obat_racikan.jml_dr,metode_racik.nm_racik,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur " +
                    "from resep_obat inner join reg_periksa inner join pasien inner join " +
                    "obat_racikan inner join metode_racik on resep_obat.no_rawat=reg_periksa.no_rawat " +
                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "and obat_racikan.kd_racik=metode_racik.kd_racik " +
                    "and resep_obat.no_rawat=obat_racikan.no_rawat and " +
                    "resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and " +
                    "resep_obat.jam=obat_racikan.jam and resep_obat.no_rawat=obat_racikan.no_rawat "+
                    "where resep_obat.no_resep='"+NoResep.getText()+"'",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppResepObat2ActionPerformed

    private void tbResepKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepKeyReleased
        if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbResepKeyReleased

    private void ppLembarObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppLembarObat1ActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Klik No Resep untuk mencetak aturan pakai...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
            try {
                i=0;
                ps=koneksi.prepareStatement(
                    "select databarang.nama_brng,aturan_pakai.aturan,detail_pemberian_obat.jml,kodesatuan.satuan "+
                    "from resep_obat inner join reg_periksa inner join "+
                    "aturan_pakai inner join databarang inner join detail_pemberian_obat "+
                    "inner join kodesatuan on resep_obat.no_rawat=reg_periksa.no_rawat  "+
                    "and databarang.kode_brng=aturan_pakai.kode_brng and "+
                    "detail_pemberian_obat.kode_brng=databarang.kode_brng " +
                    "and resep_obat.no_rawat=aturan_pakai.no_rawat and "+
                    "resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and " +
                    "resep_obat.jam=aturan_pakai.jam and resep_obat.no_rawat=detail_pemberian_obat.no_rawat "+
                    "and resep_obat.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and " +
                    "resep_obat.jam=detail_pemberian_obat.jam and kodesatuan.kode_sat=databarang.kode_sat "+
                    "where resep_obat.no_resep=? and aturan_pakai.aturan<>''");
                try {
                    ps.setString(1,NoResep.getText());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,rs.getString("nama_brng"),rs.getString("aturan"),rs.getString("jml"),rs.getString("satuan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
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
                
                psracikan=koneksi.prepareStatement(
                    "select obat_racikan.no_racik,obat_racikan.nama_racik,obat_racikan.tgl_perawatan,obat_racikan.jam," +
                    "obat_racikan.no_rawat,obat_racikan.aturan_pakai,obat_racikan.jml_dr,metode_racik.nm_racik " +
                    "from resep_obat inner join reg_periksa inner join " +
                    "obat_racikan inner join metode_racik on resep_obat.no_rawat=reg_periksa.no_rawat " +
                    "and obat_racikan.kd_racik=metode_racik.kd_racik " +
                    "and resep_obat.no_rawat=obat_racikan.no_rawat and " +
                    "resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and " +
                    "resep_obat.jam=obat_racikan.jam and resep_obat.no_rawat=obat_racikan.no_rawat "+
                    "where resep_obat.no_resep=?");
                try {
                    psracikan.setString(1,NoResep.getText());
                    rsracikan=psracikan.executeQuery();
                    while(rsracikan.next()){
                        rincianobat="";
                        ps2=koneksi.prepareStatement(
                            "select databarang.nama_brng,detail_pemberian_obat.jml from "+
                            "detail_pemberian_obat inner join databarang inner join detail_obat_racikan "+
                            "on detail_pemberian_obat.kode_brng=databarang.kode_brng and "+
                            "detail_pemberian_obat.kode_brng=detail_obat_racikan.kode_brng and "+
                            "detail_pemberian_obat.tgl_perawatan=detail_obat_racikan.tgl_perawatan and "+
                            "detail_pemberian_obat.jam=detail_obat_racikan.jam and "+
                            "detail_pemberian_obat.no_rawat=detail_obat_racikan.no_rawat "+
                            "where detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? and "+
                            "detail_pemberian_obat.no_rawat=? and detail_obat_racikan.no_racik=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,rsracikan.getString("tgl_perawatan"));
                            ps2.setString(2,rsracikan.getString("jam"));
                            ps2.setString(3,rsracikan.getString("no_rawat"));
                            ps2.setString(4,rsracikan.getString("no_racik"));
                            rs2=ps2.executeQuery();
                            total=0;
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
                            ""+i,rsracikan.getString("nama_racik")+" ("+rincianobat+")",rsracikan.getString("aturan_pakai"),rsracikan.getString("jml_dr"),rsracikan.getString("nm_racik"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                        i++;
                    }
                } catch (Exception e) {
                    System.out.println("Notif Racikan : "+e);
                } finally{
                    if(rsracikan!=null){
                        rsracikan.close();
                    }
                    if(psracikan!=null){
                        psracikan.close();
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
            param.put("penanggung",Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText())));               
            param.put("propinsirs",akses.getpropinsirs());
            param.put("tanggal",Valid.SetTgl(DTPBeri.getSelectedItem()+""));
            param.put("norawat",TNoRw.getText());
            param.put("pasien",TPasien.getText());
            param.put("norm",TNoRm.getText());
            param.put("peresep",NmDokter.getText());
            param.put("noresep",NoResep.getText());
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDokter.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmDokter.getText()+"\nID "+(finger.equals("")?KdDokter.getText():finger)+"\n"+DTPBeri.getSelectedItem());  
            param.put("jam",cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            
            Valid.MyReportqry("rptLembarObat2.jasper","report","::[ Lembar Pemberian Obat ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppLembarObat1ActionPerformed

    private void DTPBeriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPBeriItemStateChanged
        try {
            if(getno==0){
                autoresep();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_DTPBeriItemStateChanged

    private void ppLabelDataObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppLabelDataObatActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Klik No Resep untuk mencetak aturan pakai...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
            try {
                i=0;
                ps=koneksi.prepareStatement(
                    "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,databarang.kode_sat, "+
                    "detail_pemberian_obat.kode_brng,detail_pemberian_obat.jml,detail_pemberian_obat.total,"+
                    "databarang.nama_brng,detail_pemberian_obat.no_rawat from detail_pemberian_obat inner join databarang "+
                    "on detail_pemberian_obat.kode_brng=databarang.kode_brng inner join resep_obat on resep_obat.no_rawat=detail_pemberian_obat.no_rawat "+
                    "and detail_pemberian_obat.tgl_perawatan=resep_obat.tgl_perawatan and detail_pemberian_obat.jam=resep_obat.jam "+
                    "where resep_obat.no_resep=?");
                try {
                    ps.setString(1,NoResep.getText());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,rs.getString("nama_brng"),Sequel.cariIsi("select aturan from aturan_pakai where tgl_perawatan='"+rs.getString("tgl_perawatan")+"' and jam='"+rs.getString("jam")+"' and no_rawat='"+rs.getString("no_rawat")+"' and kode_brng='"+rs.getString("kode_brng")+"'"),rs.getString("jml"),Valid.SetAngka(rs.getDouble("total")),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                        i++;
                    }
                } catch (Exception e) {
                    System.out.println("Notif 2 : "+e);
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
            param.put("penanggung",Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText())));               
            param.put("propinsirs",akses.getpropinsirs());
            param.put("tanggal",Valid.SetTgl(DTPBeri.getSelectedItem()+""));
            param.put("norawat",TNoRw.getText());
            param.put("pasien",TPasien.getText());
            param.put("norm",TNoRm.getText());
            param.put("peresep",NmDokter.getText());
            param.put("noresep",NoResep.getText());
            param.put("poli",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?",Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText())));   
            param.put("jam",cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            
            Valid.MyReportqry("rptLabelDaftarObat.jasper","report","::[ Label Daftar Obat ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppLabelDataObatActionPerformed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbResep.getSelectedRow()!= -1){
            if(NoResep.getText().equals("")){
                Valid.textKosong(TCari,"No.Resep");
            }else{
                isPhoto();
                TabDataMouseClicked(null);
            }
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Resep..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(TabData.getSelectedIndex()==0){
            panggilTelaah();
        }else if(TabData.getSelectedIndex()==1){
            panggilPhoto();
        }
    }//GEN-LAST:event_TabDataMouseClicked

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        panggilTelaah();
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void BtnTelaahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTelaahActionPerformed
        if(tbResep.getSelectedRow()!= -1){
            if(NoResep.getText().equals("")){
                Valid.textKosong(TCari,"No.Resep");
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                InventoryTelaahResep aplikasi=new InventoryTelaahResep(null,false);
                aplikasi.emptTeks();
                aplikasi.isCek();
                aplikasi.setNoRm(NoResep.getText(),TNoRw.getText(),DTPCari2.getDate());
                aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                aplikasi.setLocationRelativeTo(internalFrame1);
                aplikasi.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Resep..!!!");
        }
    }//GEN-LAST:event_BtnTelaahActionPerformed

    private void ppLembarObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppLembarObat2ActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Klik No Resep untuk mencetak aturan pakai...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.queryu("delete from temporary_resep where temp37='"+akses.getalamatip()+"'");
            try {
                i=0;
                ps=koneksi.prepareStatement(
                    "select databarang.nama_brng,aturan_pakai.aturan,detail_pemberian_obat.jml,kodesatuan.satuan "+
                    "from resep_obat inner join reg_periksa inner join "+
                    "aturan_pakai inner join databarang inner join detail_pemberian_obat "+
                    "inner join kodesatuan on resep_obat.no_rawat=reg_periksa.no_rawat  "+
                    "and databarang.kode_brng=aturan_pakai.kode_brng and "+
                    "detail_pemberian_obat.kode_brng=databarang.kode_brng " +
                    "and resep_obat.no_rawat=aturan_pakai.no_rawat and "+
                    "resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and " +
                    "resep_obat.jam=aturan_pakai.jam and resep_obat.no_rawat=detail_pemberian_obat.no_rawat "+
                    "and resep_obat.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and " +
                    "resep_obat.jam=detail_pemberian_obat.jam and kodesatuan.kode_sat=databarang.kode_sat "+
                    "where resep_obat.no_resep=? and aturan_pakai.aturan<>''");
                try {
                    ps.setString(1,NoResep.getText());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            ""+i,rs.getString("nama_brng"),rs.getString("aturan"),rs.getString("jml"),rs.getString("satuan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
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
                
                psracikan=koneksi.prepareStatement(
                    "select obat_racikan.no_racik,obat_racikan.nama_racik,obat_racikan.tgl_perawatan,obat_racikan.jam," +
                    "obat_racikan.no_rawat,obat_racikan.aturan_pakai,obat_racikan.jml_dr,metode_racik.nm_racik " +
                    "from resep_obat inner join reg_periksa inner join " +
                    "obat_racikan inner join metode_racik on resep_obat.no_rawat=reg_periksa.no_rawat " +
                    "and obat_racikan.kd_racik=metode_racik.kd_racik " +
                    "and resep_obat.no_rawat=obat_racikan.no_rawat and " +
                    "resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and " +
                    "resep_obat.jam=obat_racikan.jam and resep_obat.no_rawat=obat_racikan.no_rawat "+
                    "where resep_obat.no_resep=?");
                try {
                    psracikan.setString(1,NoResep.getText());
                    rsracikan=psracikan.executeQuery();
                    while(rsracikan.next()){
                        rincianobat="";
                        ps2=koneksi.prepareStatement(
                            "select databarang.nama_brng,detail_pemberian_obat.jml from "+
                            "detail_pemberian_obat inner join databarang inner join detail_obat_racikan "+
                            "on detail_pemberian_obat.kode_brng=databarang.kode_brng and "+
                            "detail_pemberian_obat.kode_brng=detail_obat_racikan.kode_brng and "+
                            "detail_pemberian_obat.tgl_perawatan=detail_obat_racikan.tgl_perawatan and "+
                            "detail_pemberian_obat.jam=detail_obat_racikan.jam and "+
                            "detail_pemberian_obat.no_rawat=detail_obat_racikan.no_rawat "+
                            "where detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? and "+
                            "detail_pemberian_obat.no_rawat=? and detail_obat_racikan.no_racik=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,rsracikan.getString("tgl_perawatan"));
                            ps2.setString(2,rsracikan.getString("jam"));
                            ps2.setString(3,rsracikan.getString("no_rawat"));
                            ps2.setString(4,rsracikan.getString("no_racik"));
                            rs2=ps2.executeQuery();
                            total=0;
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
                            ""+i,rsracikan.getString("nama_racik")+" ("+rincianobat+")",rsracikan.getString("aturan_pakai"),rsracikan.getString("jml_dr"),rsracikan.getString("nm_racik"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",akses.getalamatip()
                        });
                        i++;
                    }
                } catch (Exception e) {
                    System.out.println("Notif Racikan : "+e);
                } finally{
                    if(rsracikan!=null){
                        rsracikan.close();
                    }
                    if(psracikan!=null){
                        psracikan.close();
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
            param.put("penanggung",Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText())));               
            param.put("propinsirs",akses.getpropinsirs());
            param.put("tanggal",Valid.SetTgl(DTPBeri.getSelectedItem()+""));
            param.put("norawat",TNoRw.getText());
            param.put("pasien",TPasien.getText());
            param.put("norm",TNoRm.getText());
            param.put("peresep",NmDokter.getText());
            if(akses.getkode().equals("Admin Utama")){
                param.put("diserahkanoleh","Petugas Farmasi");
            }else{
                param.put("diserahkanoleh",Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",akses.getkode()));
            }
                
            param.put("noresep",NoResep.getText());
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDokter.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmDokter.getText()+"\nID "+(finger.equals("")?KdDokter.getText():finger)+"\n"+DTPBeri.getSelectedItem());  
            param.put("jam",cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            param.put("photo","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penyerahanresep/"+Sequel.cariIsi("select bukti_penyerahan_resep_obat.photo from bukti_penyerahan_resep_obat where bukti_penyerahan_resep_obat.no_resep=?",NoResep.getText()));
            
            Valid.MyReportqry("rptLembarObat3.jasper","report","::[ Lembar Pemberian Obat ]::","select * from temporary_resep where temporary_resep.temp37='"+akses.getalamatip()+"' order by temporary_resep.no",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppLembarObat2ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        norawat="";
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() -> tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() -> tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() -> tampil());
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
            DlgResepObat dialog = new DlgResepObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluar2;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan3;
    private widget.Button BtnSimpan4;
    private widget.Button BtnTelaah;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkRM;
    private widget.Tanggal DTPBeri;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.PanelBiasa FormTelaah;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.TextBox NmDokter;
    private widget.TextBox NoResep;
    private widget.TextBox NoResepUbah;
    private widget.TextBox NoResepUbah1;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private widget.TextBox TNoRm;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabData;
    private javax.swing.JDialog WindowInput3;
    private javax.swing.JDialog WindowInput4;
    private widget.Button btnDokter;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.Label label15;
    private widget.Label label16;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private javax.swing.JMenuItem ppLabelDataObat;
    private javax.swing.JMenuItem ppLembarObat;
    private javax.swing.JMenuItem ppLembarObat1;
    private javax.swing.JMenuItem ppLembarObat2;
    private javax.swing.JMenuItem ppResepObat;
    private javax.swing.JMenuItem ppResepObat1;
    private javax.swing.JMenuItem ppResepObat2;
    private javax.swing.JMenuItem ppUbahAturanPakai;
    private javax.swing.JMenuItem ppUbahAturanPakai1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbResep;
    private widget.Table tbTambahan;
    private widget.Table tbTambahan1;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{  
            ps=koneksi.prepareStatement(
                "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,"+
                "resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter "+
                "from resep_obat inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                "where concat(resep_obat.tgl_perawatan,' ',resep_obat.jam) between ? and ? "+
                (norawat.equals("")?"":"and resep_obat.no_rawat='"+norawat+"' ")+
                (TCari.getText().trim().equals("")?"":"and (resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ")+
                "order by resep_obat.tgl_perawatan,resep_obat.jam");
            try{
                ps.setString(1,Valid.SetTglJam(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTglJam(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                jumlahtotal=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_resep"),rs.getString("tgl_perawatan")+" "+rs.getString("jam"),
                        rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("nm_dokter")
                    });
                    tabMode.addRow(new Object[]{"","Nama Obat","Jumlah x Harga + Embalase + Tuslah = Total","Aturan Pakai"});                
                    ps2=koneksi.prepareStatement(
                        "select databarang.kode_brng,databarang.nama_brng,detail_pemberian_obat.jml,detail_pemberian_obat.biaya_obat,detail_pemberian_obat.embalase,detail_pemberian_obat.tuslah,detail_pemberian_obat.total "+
                        "from detail_pemberian_obat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? and detail_pemberian_obat.no_rawat=? "+
                        "and databarang.kode_brng not in (select detail_obat_racikan.kode_brng from detail_obat_racikan where detail_obat_racikan.tgl_perawatan=? and detail_obat_racikan.jam=? and detail_obat_racikan.no_rawat=?) "+
                        "order by databarang.kode_brng");
                    try {
                        ps2.setString(1,rs.getString("tgl_perawatan"));
                        ps2.setString(2,rs.getString("jam"));
                        ps2.setString(3,rs.getString("no_rawat"));
                        ps2.setString(4,rs.getString("tgl_perawatan"));
                        ps2.setString(5,rs.getString("jam"));
                        ps2.setString(6,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        total=0;
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                "",rs2.getString("nama_brng"),rs2.getString("jml")+"  x  "+Valid.SetAngka(rs2.getDouble("biaya_obat"))+
                                " + "+Valid.SetAngka(rs2.getDouble("embalase"))+" + "+Valid.SetAngka(rs2.getDouble("tuslah"))+" = "+Valid.SetAngka(rs2.getDouble("total")),
                                Sequel.cariIsi("select aturan_pakai.aturan from aturan_pakai where aturan_pakai.tgl_perawatan='"+rs.getString("tgl_perawatan")+"' and "+
                                "aturan_pakai.jam='"+rs.getString("jam")+"' and aturan_pakai.no_rawat='"+rs.getString("no_rawat")+"' and aturan_pakai.kode_brng='"+rs2.getString("kode_brng")+"'")
                            });
                            total=total+rs2.getDouble("total");
                            jumlahtotal=jumlahtotal+rs2.getDouble("total");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    psracikan=koneksi.prepareStatement(
                            "select obat_racikan.no_racik,obat_racikan.nama_racik,obat_racikan.kd_racik,metode_racik.nm_racik as metode,obat_racikan.jml_dr,obat_racikan.aturan_pakai,obat_racikan.keterangan "+
                            "from obat_racikan inner join metode_racik on obat_racikan.kd_racik=metode_racik.kd_racik where obat_racikan.tgl_perawatan=? and obat_racikan.jam=? and obat_racikan.no_rawat=? ");
                    try {
                        psracikan.setString(1,rs.getString("tgl_perawatan"));
                        psracikan.setString(2,rs.getString("jam"));
                        psracikan.setString(3,rs.getString("no_rawat"));
                        rsracikan=psracikan.executeQuery();
                        while(rsracikan.next()){
                            tabMode.addRow(new Object[]{
                                "",rsracikan.getString("no_racik")+". "+rsracikan.getString("nama_racik"),
                                rsracikan.getString("jml_dr")+" "+rsracikan.getString("metode")+", Keterangan : "+rsracikan.getString("keterangan"),
                                rsracikan.getString("aturan_pakai")
                            });
                            
                            ps2=koneksi.prepareStatement(
                                "select databarang.kode_brng,databarang.nama_brng,detail_pemberian_obat.jml,detail_pemberian_obat.biaya_obat,detail_pemberian_obat.embalase,detail_pemberian_obat.tuslah,detail_pemberian_obat.total "+
                                "from detail_pemberian_obat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng inner join detail_obat_racikan on detail_pemberian_obat.kode_brng=detail_obat_racikan.kode_brng and "+
                                "detail_pemberian_obat.tgl_perawatan=detail_obat_racikan.tgl_perawatan and detail_pemberian_obat.jam=detail_obat_racikan.jam and detail_pemberian_obat.no_rawat=detail_obat_racikan.no_rawat "+
                                "where detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? and detail_pemberian_obat.no_rawat=? and detail_obat_racikan.no_racik=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1,rs.getString("tgl_perawatan"));
                                ps2.setString(2,rs.getString("jam"));
                                ps2.setString(3,rs.getString("no_rawat"));
                                ps2.setString(4,rsracikan.getString("no_racik"));
                                rs2=ps2.executeQuery();
                                total=0;
                                while(rs2.next()){
                                    tabMode.addRow(new Object[]{
                                        "","   "+rs2.getString("nama_brng"),rs2.getString("jml")+"  x  "+Valid.SetAngka(rs2.getDouble("biaya_obat"))+
                                        " + "+Valid.SetAngka(rs2.getDouble("embalase"))+" + "+Valid.SetAngka(rs2.getDouble("tuslah"))+" = "+Valid.SetAngka(rs2.getDouble("total")),
                                        ""
                                    });
                                    total=total+rs2.getDouble("total");
                                    jumlahtotal=jumlahtotal+rs2.getDouble("total");
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
                        }
                    } catch (Exception e) {
                        System.out.println("Notif Racikan : "+e);
                    } finally{
                        if(rsracikan!=null){
                            rsracikan.close();
                        }
                        if(psracikan!=null){
                            psracikan.close();
                        }
                    }
                    if(total>0){
                        tabMode.addRow(new Object[]{"","","Total Biaya Resep = "+ Valid.SetAngka(total),""}); 
                    }
                }                
                rs.last();
                if(rs.getRow()>0){
                    tabMode.addRow(new Object[]{">>","Jumlah Total Biaya Resep",Valid.SetAngka(jumlahtotal),""}); 
                }
                LCount.setText(""+rs.getRow());
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
    
    public void tampil2() {
        runBackground(() -> tampil());
    }

    public void emptTeks() {
        KdDokter.setText("");
        NmDokter.setText("");
        NoResep.setText("");
        DTPBeri.setDate(new Date());
        cmbJam.setSelectedItem(now.substring(11,13));
        cmbMnt.setSelectedItem(now.substring(14,16));
        cmbDtk.setSelectedItem(now.substring(17,19)); 
        NoResep.requestFocus();
        autoresep();        
    }

    private void getData() {
        if(tbResep.getSelectedRow()!= -1){
            getno=1;
            NoResep.setText(tbResep.getValueAt(tbResep.getSelectedRow(),0).toString()); 
            if(!NoResep.getText().equals("")){
                Sequel.cariIsi("select resep_obat.no_rawat from resep_obat where resep_obat.no_resep=?",TNoRw,NoResep.getText());
                Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",TNoRm,TNoRw.getText());
                Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",TPasien,TNoRm.getText());
                Sequel.cariIsi("select resep_obat.kd_dokter from resep_obat where resep_obat.no_resep=?",KdDokter,NoResep.getText());
                NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
                cmbJam.setSelectedItem(tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(11,13));
                cmbMnt.setSelectedItem(tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(14,16));
                cmbDtk.setSelectedItem(tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(17,19));
                Valid.SetTgl(DTPBeri,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(0,10));  
                
                TabDataMouseClicked(null);
            }
            getno=0;
        }
    }
   
    public void setNoRm(String norwt,Date tgl1,Date tgl2,String jam,String menit,String detik,String status) {
        TNoRw.setText(norwt);
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",TNoRm,TNoRw.getText());
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",TPasien,TNoRm.getText());
        norawat=norwt; 
        DTPBeri.setDate(tgl1);
        Valid.SetTgl2(DTPCari1,format.format(tgl1)+" 00:00:00");
        Valid.SetTgl2(DTPCari2,format.format(tgl2)+" 23:59:59"); 
        cmbJam.setSelectedItem(jam);
        cmbMnt.setSelectedItem(menit);
        cmbDtk.setSelectedItem(detik);
        ChkInput.setSelected(true);
        this.status=status;
        isForm();
    }
    
    public void setNoRm(String norwt,Date tgl1,Date tgl2,String jam,String menit,String detik,String kodedokter,String namadokter,String status) {
        TNoRw.setText(norwt);
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",TNoRm,TNoRw.getText());
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",TPasien,TNoRm.getText());
        norawat=norwt;       
        DTPBeri.setDate(tgl1);
        Valid.SetTgl2(DTPCari1,format.format(tgl1)+" 00:00:00");
        Valid.SetTgl2(DTPCari2,format.format(tgl2)+" 23:59:59"); 
        cmbJam.setSelectedItem(jam);
        cmbMnt.setSelectedItem(menit);
        cmbDtk.setSelectedItem(detik);
        ChkInput.setSelected(true);
        isForm();
        KdDokter.setText(kodedokter);
        NmDokter.setText(namadokter);
        this.status=status;
    }
    
    public void setDokterRalan(){
        Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",KdDokter,TNoRw.getText());
        NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,128));
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
        BtnSimpan.setEnabled(akses.getresep_obat());
        BtnHapus.setEnabled(akses.getresep_obat());
        BtnPrint.setEnabled(akses.getresep_obat());
        BtnTelaah.setEnabled(akses.gettelaah_resep());
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                DTPBeri.setEditable(false);
                DTPBeri.setEnabled(false);
                cmbJam.setEnabled(false);
                cmbMnt.setEnabled(false);
                cmbDtk.setEnabled(false);
                ChkRM.setEnabled(false);
                NoResep.setEnabled(false);
            }
        }
    }

    private void tampilresep() {
        Valid.tabelKosong(tabmodeUbahRacikan);
        try {
            try {
                ps2=koneksi.prepareStatement(
                        "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,detail_pemberian_obat.no_rawat,"+
                        "detail_pemberian_obat.kode_brng,databarang.nama_brng from detail_pemberian_obat "+
                        "inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng where "+
                        "detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? and "+
                        "detail_pemberian_obat.no_rawat=? and databarang.kode_brng not in (select kode_brng from detail_obat_racikan where tgl_perawatan=? and jam=? and no_rawat=?)");
                ps2.setString(1,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(0,10));
                ps2.setString(2,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(11,13)+":"+tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(14,16)+":"+tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(17,19));
                ps2.setString(3,TNoRw.getText());
                ps2.setString(4,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(0,10));
                ps2.setString(5,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(11,13)+":"+tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(14,16)+":"+tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(17,19));
                ps2.setString(6,TNoRw.getText());
                rs2=ps2.executeQuery();
                while(rs2.next()){
                    tabmodeUbahRacikan.addRow(new Object[]{
                        rs2.getString("tgl_perawatan"),rs2.getString("jam"),rs2.getString("no_rawat"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                        Sequel.cariIsi("select aturan from aturan_pakai where tgl_perawatan='"+rs2.getString("tgl_perawatan")+"' and "+
                            "jam='"+rs2.getString("jam")+"' and no_rawat='"+rs2.getString("no_rawat")+"' and kode_brng='"+rs2.getString("kode_brng")+"'")
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
    }

    private void tampilresep2() {
        Valid.tabelKosong(tabmodeUbahRacikan2);
        try {
            try {
                ps2=koneksi.prepareStatement(
                        "select obat_racikan.tgl_perawatan,obat_racikan.jam,obat_racikan.no_rawat,"+
                        "obat_racikan.no_racik,obat_racikan.nama_racik,"+
                        "obat_racikan.aturan_pakai from obat_racikan where "+
                        "obat_racikan.tgl_perawatan=? and obat_racikan.jam=? "+
                        "and obat_racikan.no_rawat=?");
                ps2.setString(1,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(0,10));
                ps2.setString(2,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(11,13)+":"+tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(14,16)+":"+tbResep.getValueAt(tbResep.getSelectedRow(),1).toString().substring(17,19));
                ps2.setString(3,TNoRw.getText());
                rs2=ps2.executeQuery();
                while(rs2.next()){
                    tabmodeUbahRacikan2.addRow(new Object[]{
                        rs2.getString("tgl_perawatan"),rs2.getString("jam"),rs2.getString("no_rawat"),
                        rs2.getString("no_racik"),rs2.getString("nama_racik"),rs2.getString("aturan_pakai")
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    private void autoresep() {
        if(ChkRM.isSelected()==true){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(resep_obat.no_resep,4),signed)),0) from resep_obat where resep_obat.tgl_peresepan='"+Valid.SetTgl(DTPBeri.getSelectedItem()+"")+"'",
                DTPBeri.getSelectedItem().toString().substring(6,10)+DTPBeri.getSelectedItem().toString().substring(3,5)+DTPBeri.getSelectedItem().toString().substring(0,2),4,NoResep); 
        }
    }
    
    private void autoresep2() {
        if(ChkRM.isSelected()==true){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(resep_obat.no_resep,4),signed)),0) from resep_obat where resep_obat.tgl_peresepan='"+Valid.SetTgl(DTPBeri.getSelectedItem()+"")+"' or resep_obat.tgl_perawatan='"+Valid.SetTgl(DTPBeri.getSelectedItem()+"")+"'",
                DTPBeri.getSelectedItem().toString().substring(6,10)+DTPBeri.getSelectedItem().toString().substring(3,5)+DTPBeri.getSelectedItem().toString().substring(0,2),4,NoResep);  
        } 
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(internalFrame1.getWidth()-500,HEIGHT));
            TabData.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            TabData.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }
    
    private void panggilPhoto() {
        if((TabData.isVisible()==true)&&(TabData.getSelectedIndex()==1)){
            try {
                ps=koneksi.prepareStatement("select bukti_penyerahan_resep_obat.photo from bukti_penyerahan_resep_obat where bukti_penyerahan_resep_obat.no_resep=?");
                try {
                    ps.setString(1,NoResep.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penyerahanresep/"+rs.getString("photo")+"' alt='photo' width='550' height='500'/></center></body></html>");
                        }  
                    }else{
                        LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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

    private void panggilTelaah() {
        if((TabData.isVisible()==true)&&(TabData.getSelectedIndex()==0)){
            try {
                ps=koneksi.prepareStatement(
                        "select telaah_farmasi.no_resep,telaah_farmasi.resep_identifikasi_pasien,telaah_farmasi.resep_ket_identifikasi_pasien,"+
                        "telaah_farmasi.resep_tepat_obat,telaah_farmasi.resep_ket_tepat_obat,telaah_farmasi.resep_tepat_dosis,telaah_farmasi.resep_ket_tepat_dosis,"+
                        "telaah_farmasi.resep_tepat_cara_pemberian,telaah_farmasi.resep_ket_tepat_cara_pemberian,telaah_farmasi.resep_tepat_waktu_pemberian,"+
                        "telaah_farmasi.resep_ket_tepat_waktu_pemberian,telaah_farmasi.resep_ada_tidak_duplikasi_obat,telaah_farmasi.resep_ket_ada_tidak_duplikasi_obat,"+
                        "telaah_farmasi.resep_interaksi_obat,telaah_farmasi.resep_ket_interaksi_obat,telaah_farmasi.resep_kontra_indikasi_obat,"+
                        "telaah_farmasi.resep_ket_kontra_indikasi_obat,telaah_farmasi.obat_tepat_pasien,telaah_farmasi.obat_tepat_obat,telaah_farmasi.obat_tepat_dosis,"+
                        "telaah_farmasi.obat_tepat_cara_pemberian,telaah_farmasi.obat_tepat_waktu_pemberian,telaah_farmasi.nip,petugas.nama "+
                        "from telaah_farmasi inner join petugas on telaah_farmasi.nip=petugas.nip where telaah_farmasi.no_resep=?");
                try {
                    ps.setString(1,NoResep.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        LoadHTML2.setText(
                            "<html>"+
                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center' colspan='3'>TELAAH RESEP</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='5%'>No</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='75%'>Pengkajian</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='20%'>Ya/Tidak</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>1</td>"+
                                        "<td valign='middle' align='left'>Tepat Identifikasi Pasien</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("resep_identifikasi_pasien")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'></td>"+
                                        "<td valign='middle' align='left' colspan='2'>Keterangan : "+rs.getString("resep_ket_identifikasi_pasien")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>2</td>"+        
                                        "<td valign='middle' align='left'>Tepat Obat</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("resep_tepat_obat")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'></td>"+          
                                        "<td valign='middle' align='left' colspan='2'>Keterangan : "+rs.getString("resep_ket_tepat_obat")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>3</td>"+        
                                        "<td valign='middle' align='left'>Tepat Dosis</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("resep_tepat_dosis")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'></td>"+
                                        "<td valign='middle' align='left' colspan='2'>Keterangan : "+rs.getString("resep_ket_tepat_dosis")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>4</td>"+        
                                        "<td valign='middle' align='left'>Tepat Cara Pemberian</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("resep_tepat_cara_pemberian")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'></td>"+
                                        "<td valign='middle' align='left' colspan='2'>Keterangan : "+rs.getString("resep_ket_tepat_cara_pemberian")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                       "<td valign='middle' align='center'>5</td>"+         
                                       "<td valign='middle' align='left'>Tepat Waktu Pemberian</td>"+
                                       "<td valign='middle' align='center'>"+rs.getString("resep_tepat_waktu_pemberian")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'></td>"+
                                        "<td valign='middle' align='left' colspan='2'>Keterangan : "+rs.getString("resep_ket_tepat_waktu_pemberian")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>6</td>"+        
                                        "<td valign='middle' align='left'>Ada Tidak Duplikasi Obat</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("resep_ada_tidak_duplikasi_obat")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'></td>"+          
                                        "<td valign='middle' align='left' colspan='2'>Keterangan : "+rs.getString("resep_ket_ada_tidak_duplikasi_obat")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>7</td>"+        
                                        "<td valign='middle' align='left'>Interaksi Obat</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("resep_interaksi_obat")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'></td>"+           
                                        "<td valign='middle' align='left' colspan='2'>Keterangan : "+rs.getString("resep_ket_interaksi_obat")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>8</td>"+
                                        "<td valign='middle' align='left'>Kontra Indikasi Obat</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("resep_kontra_indikasi_obat")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'></td>"+
                                        "<td valign='middle' align='left' colspan='2'>Keterangan : "+rs.getString("resep_ket_kontra_indikasi_obat")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center' colspan='3'><br>TELAAH OBAT ( VERIFIKASI OBAT )</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='5%'>No</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='75%'>Pengkajian</td>"+
                                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='20%'>Ya/Tidak</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>1</td>"+
                                        "<td valign='middle' align='left'>Tepat Pasien</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("obat_tepat_pasien")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>2</td>"+
                                        "<td valign='middle' align='left'>Tepat Obat</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("obat_tepat_obat")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>3</td>"+
                                        "<td valign='middle' align='left'>Tepat Dosis</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("obat_tepat_dosis")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>4</td>"+
                                        "<td valign='middle' align='left'>Tepat Cara Pemberian</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("obat_tepat_cara_pemberian")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center'>5</td>"+
                                        "<td valign='middle' align='left'>Tepat Waktu Pemberian</td>"+
                                        "<td valign='middle' align='center'>"+rs.getString("obat_tepat_waktu_pemberian")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='middle' align='center' colspan='3'><br>Petugas Farmasi : "+rs.getString("nip")+" "+rs.getString("nama")+"</td>"+
                                    "</tr>"+
                                "</table>"+
                            "</html>"
                        );
                    }else{
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
}

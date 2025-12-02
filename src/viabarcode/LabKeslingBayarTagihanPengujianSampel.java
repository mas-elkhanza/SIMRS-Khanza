/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPerawatan.java
 *
 * Created on May 23, 2010, 6:36:30 PM
 */

package viabarcode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import fungsi.koneksiDB;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

/**
 *
 * @author dosen
 */
public final class LabKeslingBayarTagihanPengujianSampel extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeAkunBayar;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,jml=0,countbayar=0;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    public boolean berhasil=false;
    private double total=0,bayar=0,besarppn=0,tagihanppn=0,kekurangan=0,itembayar;
    private String Suspen_Piutang_Pelayanan_Lab_Kesling,pilih="",notakesling="";
    private File file;
    private FileWriter fileWriter;
    private WarnaTable2 warna=new WarnaTable2();
    private String[] Nama_Akun_Bayar,Kode_Rek_Bayar,Bayar,PPN_Persen,PPN_Besar;

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public LabKeslingBayarTagihanPengujianSampel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
              "No.","Nama Parameter","Jumlah","Biaya (Rp)","Total Biaya (Rp)"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbValidasi.setModel(tabMode);        
        
        tbValidasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbValidasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 5; i++) {
            TableColumn column = tbValidasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(28);
            }else if(i==1){
                column.setPreferredWidth(370);
            }else if(i==2){
                column.setPreferredWidth(45);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(120);
            }
        }
        tbValidasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeAkunBayar=new DefaultTableModel(null,new Object[]{"Nama Akun","Kode Rek","Bayar","PPN(%)","PPN(Rp)"}){             
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==2)) {
                    a=true;
                }
                return a;
            }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbAkunBayar.setModel(tabModeAkunBayar);

        tbAkunBayar.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbAkunBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbAkunBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(295);
            }else if(i==1){
                //column.setPreferredWidth(70);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(112);
            }else if(i==3){
                column.setPreferredWidth(60);
            }else if(i==4){
                column.setPreferredWidth(90);
            }
        }
        warna.kolom=2;
        tbAkunBayar.setDefaultRenderer(Object.class,warna);

        NoBayar.setDocument(new batasInput((byte)20).getKata(NoBayar));
        DibayarOleh.setDocument(new batasInput((int)60).getKata(DibayarOleh)); 
        
        try {
            ps=koneksi.prepareStatement(
                "select set_akun2.Suspen_Piutang_Pelayanan_Lab_Kesling from set_akun2"
            );
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    Suspen_Piutang_Pelayanan_Lab_Kesling=rs.getString("Suspen_Piutang_Pelayanan_Lab_Kesling");
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
        
        try {
            ps=koneksi.prepareStatement(
                "select set_nota.cetaknotasimpanlabkesling,set_nota.tampilkan_tombol_nota_labkesling from set_nota"
            );
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    notakesling=rs.getString("cetaknotasimpanlabkesling");
                    if(rs.getString("tampilkan_tombol_nota_labkesling").equals("Yes")){
                        BtnNota.setVisible(true);
                    }else{
                        if(akses.getkode().equals("Admin Utama")){
                            BtnNota.setVisible(true);
                        }else{
                            BtnNota.setVisible(false);
                        }            
                    }
                }
            } catch (Exception e) {
                notakesling="No";
                if(akses.getkode().equals("Admin Utama")){
                    BtnNota.setVisible(true);
                }else{
                    BtnNota.setVisible(false);
                } 
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
        
        ChkJln.setSelected(true);
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

        PenerimaanSampel = new widget.TextBox();
        TitikSampel = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnNota = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new widget.PanelBiasa();
        KodePelanggan = new widget.TextBox();
        NamaPelanggan = new widget.TextBox();
        TanggalValidasi = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        jLabel15 = new widget.Label();
        jLabel4 = new widget.Label();
        NoPermintaan = new widget.TextBox();
        jLabel7 = new widget.Label();
        jLabel18 = new widget.Label();
        KodeSampel = new widget.TextBox();
        NamaSampel = new widget.TextBox();
        jLabel5 = new widget.Label();
        NoBayar = new widget.TextBox();
        jLabel6 = new widget.Label();
        DibayarOleh = new widget.TextBox();
        TabData = new javax.swing.JTabbedPane();
        Scroll2 = new widget.ScrollPane();
        tbValidasi = new widget.Table();
        scrollPane8 = new widget.ScrollPane();
        panelBayar = new widget.panelisi();
        TtlSemua = new widget.TextBox();
        TKembali = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        tbAkunBayar = new widget.Table();
        jLabel10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCariBayar = new widget.Button();
        BtnAll = new widget.Button();
        jLabel12 = new widget.Label();
        TagihanPPn = new widget.TextBox();

        PenerimaanSampel.setHighlighter(null);
        PenerimaanSampel.setName("PenerimaanSampel"); // NOI18N
        PenerimaanSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenerimaanSampelKeyPressed(evt);
            }
        });

        TitikSampel.setHighlighter(null);
        TitikSampel.setName("TitikSampel"); // NOI18N
        TitikSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TitikSampelKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Bayar Tagihan Pengujian Sampel Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        BtnNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnNota.setMnemonic('T');
        BtnNota.setText("Nota");
        BtnNota.setToolTipText("Alt+T");
        BtnNota.setName("BtnNota"); // NOI18N
        BtnNota.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNotaActionPerformed(evt);
            }
        });
        BtnNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNotaKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnNota);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setText("Lihat");
        BtnCari.setToolTipText("Alt+C");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnCari);

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

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 103));
        PanelInput.setLayout(null);

        KodePelanggan.setEditable(false);
        KodePelanggan.setHighlighter(null);
        KodePelanggan.setName("KodePelanggan"); // NOI18N
        PanelInput.add(KodePelanggan);
        KodePelanggan.setBounds(87, 70, 80, 23);

        NamaPelanggan.setEditable(false);
        NamaPelanggan.setHighlighter(null);
        NamaPelanggan.setName("NamaPelanggan"); // NOI18N
        PanelInput.add(NamaPelanggan);
        NamaPelanggan.setBounds(169, 70, 233, 23);

        TanggalValidasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2025" }));
        TanggalValidasi.setDisplayFormat("dd-MM-yyyy");
        TanggalValidasi.setName("TanggalValidasi"); // NOI18N
        TanggalValidasi.setOpaque(false);
        TanggalValidasi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalValidasiItemStateChanged(evt);
            }
        });
        TanggalValidasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalValidasiKeyPressed(evt);
            }
        });
        PanelInput.add(TanggalValidasi);
        TanggalValidasi.setBounds(87, 40, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        PanelInput.add(CmbJam);
        CmbJam.setBounds(181, 40, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        PanelInput.add(CmbMenit);
        CmbMenit.setBounds(247, 40, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        PanelInput.add(CmbDetik);
        CmbDetik.setBounds(313, 40, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        PanelInput.add(ChkJln);
        ChkJln.setBounds(379, 40, 23, 23);

        jLabel15.setText("Tgl.Bayar :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(0, 40, 83, 23);

        jLabel4.setText("No.Permintaan :");
        jLabel4.setName("jLabel4"); // NOI18N
        PanelInput.add(jLabel4);
        jLabel4.setBounds(426, 40, 90, 23);

        NoPermintaan.setEditable(false);
        NoPermintaan.setHighlighter(null);
        NoPermintaan.setName("NoPermintaan"); // NOI18N
        PanelInput.add(NoPermintaan);
        NoPermintaan.setBounds(520, 40, 160, 23);

        jLabel7.setText("Pelanggan :");
        jLabel7.setName("jLabel7"); // NOI18N
        PanelInput.add(jLabel7);
        jLabel7.setBounds(0, 70, 83, 23);

        jLabel18.setText("Sampel :");
        jLabel18.setName("jLabel18"); // NOI18N
        PanelInput.add(jLabel18);
        jLabel18.setBounds(405, 70, 60, 23);

        KodeSampel.setEditable(false);
        KodeSampel.setHighlighter(null);
        KodeSampel.setName("KodeSampel"); // NOI18N
        PanelInput.add(KodeSampel);
        KodeSampel.setBounds(469, 70, 55, 23);

        NamaSampel.setEditable(false);
        NamaSampel.setHighlighter(null);
        NamaSampel.setName("NamaSampel"); // NOI18N
        PanelInput.add(NamaSampel);
        NamaSampel.setBounds(526, 70, 154, 23);

        jLabel5.setText("No.Bayar/Nota :");
        jLabel5.setName("jLabel5"); // NOI18N
        PanelInput.add(jLabel5);
        jLabel5.setBounds(426, 10, 90, 23);

        NoBayar.setHighlighter(null);
        NoBayar.setName("NoBayar"); // NOI18N
        NoBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBayarKeyPressed(evt);
            }
        });
        PanelInput.add(NoBayar);
        NoBayar.setBounds(520, 10, 160, 23);

        jLabel6.setText("Dibayar Oleh :");
        jLabel6.setName("jLabel6"); // NOI18N
        PanelInput.add(jLabel6);
        jLabel6.setBounds(0, 10, 83, 23);

        DibayarOleh.setHighlighter(null);
        DibayarOleh.setName("DibayarOleh"); // NOI18N
        DibayarOleh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DibayarOlehKeyPressed(evt);
            }
        });
        PanelInput.add(DibayarOleh);
        DibayarOleh.setBounds(87, 10, 315, 23);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabData.setBackground(new java.awt.Color(255, 255, 253));
        TabData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbValidasi.setName("tbValidasi"); // NOI18N
        Scroll2.setViewportView(tbValidasi);

        TabData.addTab("Data Tagihan", Scroll2);

        scrollPane8.setBorder(null);
        scrollPane8.setName("scrollPane8"); // NOI18N
        scrollPane8.setOpaque(true);

        panelBayar.setBorder(null);
        panelBayar.setName("panelBayar"); // NOI18N
        panelBayar.setPreferredSize(new java.awt.Dimension(100, 353));
        panelBayar.setLayout(null);

        TtlSemua.setEditable(false);
        TtlSemua.setText("0");
        TtlSemua.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TtlSemua.setHighlighter(null);
        TtlSemua.setName("TtlSemua"); // NOI18N
        TtlSemua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TtlSemuaKeyPressed(evt);
            }
        });
        panelBayar.add(TtlSemua);
        TtlSemua.setBounds(105, 10, 225, 23);

        TKembali.setEditable(false);
        TKembali.setText("0");
        TKembali.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TKembali.setHighlighter(null);
        TKembali.setName("TKembali"); // NOI18N
        panelBayar.add(TKembali);
        TKembali.setBounds(105, 320, 225, 23);

        jLabel8.setText("Bayar : Rp.");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel8);
        jLabel8.setBounds(19, 40, 85, 23);

        jLabel9.setText("Total Tagihan : Rp.");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel9);
        jLabel9.setBounds(0, 10, 104, 23);

        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbAkunBayar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAkunBayar.setToolTipText("");
        tbAkunBayar.setName("tbAkunBayar"); // NOI18N
        tbAkunBayar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbAkunBayarPropertyChange(evt);
            }
        });
        tbAkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAkunBayarKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(tbAkunBayar);

        panelBayar.add(scrollPane3);
        scrollPane3.setBounds(105, 65, 573, 250);

        jLabel10.setText("Kembali : Rp.");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel10);
        jLabel10.setBounds(19, 320, 85, 23);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(340, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelBayar.add(TCari);
        TCari.setBounds(105, 40, 517, 23);

        BtnCariBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariBayar.setMnemonic('3');
        BtnCariBayar.setToolTipText("Alt+3");
        BtnCariBayar.setName("BtnCariBayar"); // NOI18N
        BtnCariBayar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariBayarActionPerformed(evt);
            }
        });
        BtnCariBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariBayarKeyPressed(evt);
            }
        });
        panelBayar.add(BtnCariBayar);
        BtnCariBayar.setBounds(625, 40, 25, 23);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
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
        panelBayar.add(BtnAll);
        BtnAll.setBounds(653, 40, 25, 23);

        jLabel12.setText("Tagihan + PPN : Rp.");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel12);
        jLabel12.setBounds(342, 10, 110, 23);

        TagihanPPn.setEditable(false);
        TagihanPPn.setText("0");
        TagihanPPn.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TagihanPPn.setHighlighter(null);
        TagihanPPn.setName("TagihanPPn"); // NOI18N
        panelBayar.add(TagihanPPn);
        TagihanPPn.setBounds(453, 10, 225, 23);

        scrollPane8.setViewportView(panelBayar);

        TabData.addTab("Pembayaran", scrollPane8);

        internalFrame1.add(TabData, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnCari,NoBayar);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
    LabKeslingCariValidasiPengujianSampel form=new LabKeslingCariValidasiPengujianSampel(null,false);
    form.isCek();
    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    form.setLocationRelativeTo(this);
    form.setVisible(true);
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void TanggalValidasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalValidasiKeyPressed
        //Valid.pindah(evt, KdPJ, NoBayar);
    }//GEN-LAST:event_TanggalValidasiKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt, NoBayar,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(DibayarOleh.getText().equals("")){
            Valid.textKosong(DibayarOleh,"Pihak Pelanggan Yang Membayar");
        }else if(KodePelanggan.getText().equals("")||NamaPelanggan.getText().equals("")){
            Valid.textKosong(NoBayar,"Pelanggan");
        }else if(NoPermintaan.getText().equals("")){
            Valid.textKosong(NoPermintaan,"Nomor Permintaan");
        }else if(NoBayar.getText().equals("")){
            Valid.textKosong(NoBayar,"Nomor Verifikasi");
        }else if(KodeSampel.getText().equals("")||NamaSampel.getText().equals("")){
            Valid.textKosong(NoBayar,"Sampel");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(NoBayar,"Data Pembayaran");
        }else if(Sequel.cariInteger("select count(labkesling_pembayaran_pengujian_sampel.no_permintaan) from labkesling_pembayaran_pengujian_sampel where labkesling_pembayaran_pengujian_sampel.no_permintaan=? ",NoPermintaan.getText())>0){
            JOptionPane.showMessageDialog(null,"Maaf, data tagihan pengujian dengan No.Permintaan tersebut sudah pernah disimpan...!!!");
        }else{
            if(kekurangan<0){
                JOptionPane.showMessageDialog(null,"Maaf, pembayaran tagihan masih kurang ...!!!");
            }else if(kekurangan>0){
                if(countbayar>1){
                    JOptionPane.showMessageDialog(null,"Maaf, kembali harus bernilai 0 untuk cara bayar lebih dari 1...!!!");
                }else{
                    isSimpan();
                }                        
            }else if(kekurangan==0){
                isSimpan();
            } 
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,NoBayar,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(DibayarOleh.getText().equals("")){
            Valid.textKosong(DibayarOleh,"Pihak Pelanggan Yang Membayar");
        }else if(KodePelanggan.getText().equals("")||NamaPelanggan.getText().equals("")){
            Valid.textKosong(NoBayar,"Pelanggan");
        }else if(NoPermintaan.getText().equals("")){
            Valid.textKosong(NoPermintaan,"Nomor Permintaan");
        }else if(NoBayar.getText().equals("")){
            Valid.textKosong(NoBayar,"Nomor Verifikasi");
        }else if(KodeSampel.getText().equals("")||NamaSampel.getText().equals("")){
            Valid.textKosong(NoBayar,"Sampel");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(NoBayar,"Data Pembayaran");
        }else{
            Sequel.queryu("delete from temporary_bayar_labkesling");
            jml=0;
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","No.Nota",": "+NoBayar.getText(),"","","","","","","","","","","","","","Header",akses.getkode()
            }); 
            jml++;
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","No.Permintaan",": "+NoPermintaan.getText(),"","","","","","","","","","","","","","Header",akses.getkode()
            }); 
            jml++;
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","Nama Pelanggan",": "+NamaPelanggan.getText(),"","","","","","","","","","","","","","Header",akses.getkode()
            }); 
            jml++;
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","Penerimaan Sample",": "+Valid.SetTgl5(PenerimaanSampel.getText()),"","","","","","","","","","","","","","Header",akses.getkode()
            }); 
            jml++;
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","Tanggal Uji",": "+Sequel.cariIsi("select concat(date_format(labkesling_verifikasi_pengujian_sampel.mulai_pengujian,'%d/%m/%Y %H:%i:%s'),' s.d. ',date_format(labkesling_verifikasi_pengujian_sampel.selesai_pengujian,'%d/%m/%Y %H:%i:%s')) from labkesling_verifikasi_pengujian_sampel where labkesling_verifikasi_pengujian_sampel.no_permintaan=?",NoPermintaan.getText()),"","","","","","","","","","","","","","Header",akses.getkode()
            }); 
            jml++;
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","Jenis Sampel",": "+NamaSampel.getText(),"","","","","","","","","","","","","","Header",akses.getkode()
            }); 
            jml++;
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","Titik Sampling",": "+TitikSampel.getText(),"","","","","","","","","","","","","","Header",akses.getkode()
            }); 
            jml++;
            for(i=0;i<tbValidasi.getRowCount();i++){ 
                Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                    jml+"",tbValidasi.getValueAt(i,0).toString(),tbValidasi.getValueAt(i,1).toString(),tbValidasi.getValueAt(i,2).toString(),tbValidasi.getValueAt(i,3).toString(),tbValidasi.getValueAt(i,4).toString(),"","","","","","","","","","","Tagihan",akses.getkode()
                });    
                jml++;
            }
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","Total Biaya Pemeriksaan","","","",TtlSemua.getText(),"","","","","","","","","","","Total Tagihan",akses.getkode()
            });    
            jml++;
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","PPN","","","",Valid.SetAngka(besarppn),"","","","","","","","","","","Total Tagihan",akses.getkode()
            });    
            jml++;
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","Total Pembayaran","","","",TagihanPPn.getText(),"","","","","","","","","","","Total Tagihan",akses.getkode()
            });    
            jml++;
            for(i=0;i<tbAkunBayar.getRowCount();i++){
                if(Valid.SetAngka(tbAkunBayar.getValueAt(i,2).toString())>0){
                    Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                        jml+"","- "+tbAkunBayar.getValueAt(i,0).toString(),"","","",Valid.SetAngka((Valid.SetAngka(tbAkunBayar.getValueAt(i,2).toString())+Valid.SetAngka(tbAkunBayar.getValueAt(i,4).toString()))),"","","","","","","","","","","Total Tagihan",akses.getkode()
                    });
                    jml++;
                } 
            }
            Sequel.menyimpan("temporary_bayar_labkesling","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                jml+"","Dibayar Lunas Oleh ",DibayarOleh.getText(),"","","","","","","","","","","","","","Dibayar",akses.getkode()
            });    
            jml++;  
            
            i = 0;
            try{
                pilih = (String)JOptionPane.showInputDialog(null,"Silahkan pilih nota yang mau dicetak!","Nota",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Nota", "Kwitansi", "Nota & Kwitansi"},"Nota");
                switch (pilih) {
                    case "Nota":
                          i=1;
                          break;
                    case "Kwitansi":
                          i=2;
                          break;
                    case "Nota & Kwitansi":
                          i=3;
                          break;
               }
            }catch(Exception e){
                i=0;
            }
            if(i>0){ 
                switch (i) {
                    case 1:
                        Valid.panggilUrl("billing/NotaLabkesLing.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+TanggalValidasi.getSelectedItem().toString().replaceAll(" ","_")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());
                        break;
                    case 2:
                        Valid.panggilUrl("billing/KwitansiLabkesling.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+TanggalValidasi.getSelectedItem().toString().replaceAll(" ","_")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());
                        break;
                    case 3:
                        Valid.panggilUrl("billing/NotaLabkesLing.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+TanggalValidasi.getSelectedItem().toString().replaceAll(" ","_")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());
                        Valid.panggilUrl("billing/KwitansiLabkesling.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+TanggalValidasi.getSelectedItem().toString().replaceAll(" ","_")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());
                        break;
                    default:
                        break;
                }
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnNotaActionPerformed

    private void BtnNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNotaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnNotaActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnCari);
        }
    }//GEN-LAST:event_BtnNotaKeyPressed

    private void TanggalValidasiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalValidasiItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalValidasiItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        try {
            if(Valid.daysOld("./cache/akunbayar.iyem")>30){
                tampilAkunBayar();
            }else{
                tampilAkunBayar2();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void NoBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBayarKeyPressed
        Valid.pindah(evt, BtnKeluar,DibayarOleh);
    }//GEN-LAST:event_NoBayarKeyPressed

    private void DibayarOlehKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DibayarOlehKeyPressed
        Valid.pindah(evt,NoBayar,BtnSimpan);
    }//GEN-LAST:event_DibayarOlehKeyPressed

    private void PenerimaanSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenerimaanSampelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenerimaanSampelKeyPressed

    private void TitikSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TitikSampelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TitikSampelKeyPressed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        
    }//GEN-LAST:event_TabDataMouseClicked

    private void TtlSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TtlSemuaKeyPressed
       // Valid.pindah(evt,BtnKeluar,BtnNota);
    }//GEN-LAST:event_TtlSemuaKeyPressed

    private void tbAkunBayarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAkunBayarPropertyChange
        if(this.isVisible()==true){
              isKembali();
        }
    }//GEN-LAST:event_tbAkunBayarPropertyChange

    private void tbAkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAkunBayarKeyPressed
        if(tabModeAkunBayar.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbAkunBayar.getRowCount()!=0){
                    if((tbAkunBayar.getSelectedColumn()==2)||(tbAkunBayar.getSelectedColumn()==3)||(tbAkunBayar.getSelectedColumn()==4)){
                        if(!tabModeAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),2).toString().equals("")){
                            tbAkunBayar.setValueAt(
                                    Valid.roundUp((Valid.SetAngka(tbAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),3).toString())/100)*
                                    Valid.SetAngka(tbAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),2).toString()),100),tbAkunBayar.getSelectedRow(),4);
                        }else{
                            tbAkunBayar.setValueAt("",tbAkunBayar.getSelectedRow(),4);                        
                        }                            
                    }
                }
                isKembali();
            }
        }
    }//GEN-LAST:event_tbAkunBayarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariBayarActionPerformed(null);
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariBayarActionPerformed
        tampilAkunBayar2();
        isKembali();
    }//GEN-LAST:event_BtnCariBayarActionPerformed

    private void BtnCariBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariBayarKeyPressed

    }//GEN-LAST:event_BtnCariBayarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        tampilAkunBayar();
        isKembali();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari,BtnSimpan);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingBayarTagihanPengujianSampel dialog = new LabKeslingBayarTagihanPengujianSampel(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCariBayar;
    private widget.Button BtnKeluar;
    private widget.Button BtnNota;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox DibayarOleh;
    private widget.TextBox KodePelanggan;
    private widget.TextBox KodeSampel;
    private widget.TextBox NamaPelanggan;
    private widget.TextBox NamaSampel;
    private widget.TextBox NoBayar;
    private widget.TextBox NoPermintaan;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox PenerimaanSampel;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    public widget.TextBox TKembali;
    private javax.swing.JTabbedPane TabData;
    private widget.TextBox TagihanPPn;
    private widget.Tanggal TanggalValidasi;
    private widget.TextBox TitikSampel;
    private widget.TextBox TtlSemua;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel12;
    private widget.Label jLabel15;
    private widget.Label jLabel18;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.panelisi panelBayar;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane8;
    private widget.Table tbAkunBayar;
    private widget.Table tbValidasi;
    // End of variables declaration//GEN-END:variables
    
    
    private void tampil() {   
        try {
            Valid.tabelKosong(tabMode);
            myObj = new FileReader("./cache/bayartagihansampellabkesling.iyem");
            root = mapper.readTree(myObj);
            response = root.path("bayartagihansampellabkesling");
            if(response.isArray()){
                total=0;
                i=1;
                for(JsonNode list:response){
                    tabMode.addRow(new Object[]{
                        i,list.path("NamaParameter").asText(),"1",list.path("Total").asDouble(),list.path("Total").asDouble()
                    });
                    total=total+list.path("Total").asDouble();
                    i++;
                }
                TtlSemua.setText(Valid.SetAngka3(total));
            }
            myObj.close();
        } catch (Exception ex) {
            if(ex.toString().contains("java.io.FileNotFoundException")){
                tampil();
            }else{
                System.out.println("Notifikasi : "+ex);
            }
        }
    }
    
    public void setData(String nopermintaan,String kodepelanggan,String namapelanggan,String kodesampel,String namasampel,String titiksampel,String tanggalpenerimaan) {
        NoPermintaan.setText(nopermintaan);
        KodePelanggan.setText(kodepelanggan);
        NamaPelanggan.setText(namapelanggan);
        KodeSampel.setText(kodesampel);
        NamaSampel.setText(namasampel);
        TitikSampel.setText(titiksampel);
        PenerimaanSampel.setText(tanggalpenerimaan);
        autoNomor();
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
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMenit.getSelectedIndex();
                    nilai_detik =CmbDetik.getSelectedIndex();
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
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    public void isCek(){        
        BtnSimpan.setEnabled(akses.getpembayaran_pengujian_sampel_lab_kesehatan_lingkungan());
        BtnNota.setEnabled(akses.getpembayaran_pengujian_sampel_lab_kesehatan_lingkungan());
    }
  
    private void autoNomor() {
        if(!KodeSampel.getText().trim().equals("")){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(labkesling_pembayaran_pengujian_sampel.no_pembayaran,5),signed)),0) from labkesling_pembayaran_pengujian_sampel where date_format(labkesling_pembayaran_pengujian_sampel.tanggal,'%Y-%m-%d')='"+Valid.SetTgl(TanggalValidasi.getSelectedItem()+"")+"'","LKL"+TanggalValidasi.getSelectedItem().toString().substring(6,10)+TanggalValidasi.getSelectedItem().toString().substring(3,5)+TanggalValidasi.getSelectedItem().toString().substring(0,2),5,NoBayar);   
        }        
    }
    
    private void tampilAkunBayar() {         
        try{      
             Valid.tabelKosong(tabModeAkunBayar);
             file=new File("./cache/akunbayar.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             StringBuilder iyembuilder = new StringBuilder();
             ps=koneksi.prepareStatement("select * from akun_bayar order by akun_bayar.nama_bayar");
             try{
                 rs=ps.executeQuery();
                 while(rs.next()){      
                     tabModeAkunBayar.addRow(new Object[]{rs.getString(1),rs.getString(2),"",rs.getDouble(3),""});
                     iyembuilder.append("{\"NamaAkun\":\"").append(rs.getString(1).replaceAll("\"","")).append("\",\"KodeRek\":\"").append(rs.getString(2)).append("\",\"PPN\":\"").append(rs.getDouble(3)).append("\"},");
                 }
             }catch (Exception e) {
                 System.out.println("Notifikasi : "+e);
             } finally{
                 if(rs != null){
                     rs.close();
                 } 
                 if(ps != null){
                     ps.close();
                 } 
             }
             
             if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"akunbayar\":["+iyembuilder+"]}");
                fileWriter.flush();
             }
            
             fileWriter.close();
             iyembuilder=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilAkunBayar2() {         
         try{           
             jml=0;
             for(i=0;i<tbAkunBayar.getRowCount();i++){
                if(!tbAkunBayar.getValueAt(i,2).toString().equals("")){
                    jml++;
                }
             }
             
             Nama_Akun_Bayar=new String[jml];
             Kode_Rek_Bayar=new String[jml];
             Bayar=new String[jml];
             PPN_Persen=new String[jml];
             PPN_Besar=new String[jml];
             
             jml=0;
             for(i=0;i<tbAkunBayar.getRowCount();i++){
                if(!tbAkunBayar.getValueAt(i,2).toString().equals("")){
                    Nama_Akun_Bayar[jml]=tbAkunBayar.getValueAt(i,0).toString();
                    Kode_Rek_Bayar[jml]=tbAkunBayar.getValueAt(i,1).toString();
                    Bayar[jml]=tbAkunBayar.getValueAt(i,2).toString();
                    PPN_Persen[jml]=tbAkunBayar.getValueAt(i,3).toString();
                    PPN_Besar[jml]=tbAkunBayar.getValueAt(i,4).toString();
                    jml++;
                }
             }
             
             Valid.tabelKosong(tabModeAkunBayar);
             
             for(i=0;i<jml;i++){
                tabModeAkunBayar.addRow(new Object[] {
                    Nama_Akun_Bayar[i],Kode_Rek_Bayar[i],Bayar[i],PPN_Persen[i],PPN_Besar[i]
                });
             }
             
             Nama_Akun_Bayar=null;
             Kode_Rek_Bayar=null;
             Bayar=null;
             PPN_Persen=null;
             PPN_Besar=null;
             
             myObj = new FileReader("./cache/akunbayar.iyem");
             root = mapper.readTree(myObj);
             response = root.path("akunbayar");
             if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("NamaAkun").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                        tabModeAkunBayar.addRow(new Object[]{
                            list.path("NamaAkun").asText(),list.path("KodeRek").asText(),"",list.path("PPN").asText(),""
                        });
                    }
                }
             }
             myObj.close();
         }catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
         }
    }
    
    private void isKembali(){
        bayar=0;besarppn=0;tagihanppn=0;countbayar=0;kekurangan=0;
        
        for(i=0;i<tabModeAkunBayar.getRowCount();i++){ 
            if(!tabModeAkunBayar.getValueAt(i,2).toString().equals("")){
                countbayar++;
                try {
                    bayar=bayar+Double.parseDouble(tabModeAkunBayar.getValueAt(i,2).toString()); 
                } catch (Exception e) {
                    bayar=bayar+0;
                }               
            }  
            
            if(!tabModeAkunBayar.getValueAt(i,4).toString().equals("")){
                try {
                    besarppn=besarppn+Valid.roundUp(Double.parseDouble(tabModeAkunBayar.getValueAt(i,4).toString()),100); 
                } catch (Exception e) {
                    besarppn=besarppn+0;
                }               
            }   
        }
        
        tagihanppn=besarppn+total;
        TagihanPPn.setText(Valid.SetAngka3(tagihanppn));
        
        kekurangan=(bayar+besarppn)-tagihanppn;
        
        TKembali.setText(Valid.SetAngka3(kekurangan));  
    }
    
    private void isSimpan(){
        int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ChkJln.setSelected(false);
            berhasil=true; 
            try { 
                if(notakesling.equals("Yes")){
                    BtnNotaActionPerformed(null);
                }
                Sequel.AutoComitFalse();
                if(Sequel.menyimpantf2("labkesling_pembayaran_pengujian_sampel","?,?,?,?","No.Bayar/Nota",4,new String[]{
                        NoPermintaan.getText(),NoBayar.getText(),Valid.SetTgl(TanggalValidasi.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),DibayarOleh.getText()
                    })==true){
                    Sequel.queryu2("delete from tampjurnal");
                    itembayar=0;besarppn=0;
                    for(i=0;i<tbAkunBayar.getRowCount();i++){
                        if(Valid.SetAngka(tbAkunBayar.getValueAt(i,2).toString())>0){
                            try {
                                itembayar=Double.parseDouble(tbAkunBayar.getValueAt(i,2).toString()); 
                            } catch (Exception e) {
                                itembayar=0;
                            }    

                            if(!tbAkunBayar.getValueAt(i,4).toString().equals("")){
                                try {
                                    besarppn=Valid.roundUp(Double.parseDouble(tbAkunBayar.getValueAt(i,4).toString()),100); 
                                } catch (Exception e) {
                                    besarppn=0;
                                }               
                            }  
                            
                            if(Sequel.menyimpantf2("labkesling_detail_pembayaran_pengujian_sampel","?,?,?,?","Akun bayar",4,new String[]{
                                    NoBayar.getText(),tbAkunBayar.getValueAt(i,0).toString(),Double.toString(besarppn),Double.toString(itembayar)
                                })==true){
                                if(Sequel.menyimpantf("tampjurnal","'"+tbAkunBayar.getValueAt(i,1).toString()+"','"+tbAkunBayar.getValueAt(i,0).toString()+"','"+Double.toString(itembayar)+"','0'","debet=debet+'"+Double.toString(itembayar)+"'","kd_rek='"+tbAkunBayar.getValueAt(i,1).toString()+"'")==false){
                                    berhasil=false;
                                }
                            }else{
                                berhasil=false;
                            }
                        }
                    }
                    
                    if(berhasil==true){
                        if(total>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Pelayanan_Lab_Kesling+"','Suspen Piutang Pelayanan Lab Kesling','0','"+total+"'","kredit=kredit+'"+total+"'","kd_rek='"+Suspen_Piutang_Pelayanan_Lab_Kesling+"'")==false){
                                berhasil=false;
                            }
                        }
                    }
                    
                    if(berhasil==true){
                        berhasil=jur.simpanJurnal(NoBayar.getText(),"U","PEMBAYARAN PELAYANAN LABORATORIUM KESEHATAN LINGKUNGAN "+NamaPelanggan.getText()+" DIPOSTING OLEH "+akses.getkode());
                        if(Sequel.menyimpantf2("tagihan_sadewa","'"+NoBayar.getText()+"','"+KodePelanggan.getText()+"','"+NamaPelanggan.getText().replaceAll("'","")+"','"+Sequel.cariIsi("select labkesling_pelanggan.alamat from labkesling_pelanggan where labkesling_pelanggan.kode_pelanggan=?",KodePelanggan.getText()).replaceAll("'","")+"','"+Valid.SetTgl(TanggalValidasi.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem()+"','Pelunasan','"+total+"','"+total+"','Sudah','"+akses.getkode()+"'","No.Bayar")==false){
                            berhasil=false;
                        }
                    }
                }else{
                    berhasil=false;
                } 
                
                if(berhasil==true){
                    Sequel.queryu("update labkesling_validasi_pengujian_sampel set status='Sudah Bayar' where no_permintaan='"+NoPermintaan.getText()+"'");
                    Sequel.Commit();
                    JOptionPane.showMessageDialog(null,"Proses simpan selesai...!");     
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
            } catch (Exception e) {
                System.out.println(e);
            }  
            ChkJln.setSelected(true);    
            this.setCursor(Cursor.getDefaultCursor());
            if(berhasil==true){
                if(notakesling.equals("Yes")){
                    this.dispose();
                }
            }
        }
    }
}

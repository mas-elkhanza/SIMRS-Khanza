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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileReader;
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
import kepegawaian.DlgCariPetugas;
import java.util.HashMap;
import java.util.Map;
import keuangan.Jurnal;

/**
 *
 * @author dosen
 */
public final class LabKeslingBayarTagihanPengujianSampel extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public boolean berhasil=false;
    private double jasa_sarana=0,paket_bhp=0,jasa_pj_lab=0,jasa_pj_pengujian=0,jasa_verifikator=0,jasa_petugas=0,kso=0,jasa_menejemen=0,total=0;
    private String Suspen_Piutang_Pelayanan_Lab_Kesling,Pendapatan_Pelayanan_Lab_Kesling,Beban_Jasa_Sarana_Pelayanan_Lab_Kesling,Utang_Jasa_sarana_Pelayanan_Lab_Kesling, 
                   HPP_BHP_Pelayanan_Lab_Kesling,Persediaan_BHP_Pelayanan_Lab_Kesling,Beban_Jasa_PJLab_Pelayanan_Lab_Kesling,Utang_Jasa_PJLab_Pelayanan_Lab_Kesling,
                   Beban_Jasa_PJPengujian_Pelayanan_Lab_Kesling,Utang_Jasa_PJPengujian_Pelayanan_Lab_Kesling,Beban_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling, 
                   Utang_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling,Beban_Jasa_Analis_Pelayanan_Lab_Kesling,Utang_Jasa_Analis_Pelayanan_Lab_Kesling,Beban_KSO_Pelayanan_Lab_Kesling, 
                   Utang_KSO_Pelayanan_Lab_Kesling,Beban_Jasa_Menejemen_Pelayanan_Lab_Kesling,Utang_Jasa_Menejemen_Pelayanan_Lab_Kesling;

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public LabKeslingBayarTagihanPengujianSampel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
              "Kode","Nama Parameter","Satuan","Hasil Pemeriksaan","Keterangan","Nilai Normal","Metode Pengujian","Kategori","No.Penugasan","NIP Analis","Nama Analis","NIP PJ","Nama PJ Pengujian",
              "Jasa Sarana","Paket BHP","Jasa PJ Lab","Jasa PJ Pengujian","Jasa Verifikator","Jasa Petugas","KSO","Jasa Menejemen","Total"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==3)||(colIndex==4)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbValidasi.setModel(tabMode);        
        
        tbValidasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbValidasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 22; i++) {
            TableColumn column = tbValidasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(55);
            }else if(i==1){
                column.setPreferredWidth(160);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(97);
            }else if(i==4){
                column.setPreferredWidth(140);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(130);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbValidasi.setDefaultRenderer(Object.class, new WarnaTable());

        NoBayar.setDocument(new batasInput((byte)20).getKata(NoBayar));
        DibayarOleh.setDocument(new batasInput((int)60).getKata(DibayarOleh)); 
        
        try {
            ps=koneksi.prepareStatement(
                "select set_akun2.Suspen_Piutang_Pelayanan_Lab_Kesling,set_akun2.Pendapatan_Pelayanan_Lab_Kesling,set_akun2.Beban_Jasa_Sarana_Pelayanan_Lab_Kesling,"+
                "set_akun2.Utang_Jasa_sarana_Pelayanan_Lab_Kesling,set_akun2.HPP_BHP_Pelayanan_Lab_Kesling,set_akun2.Persediaan_BHP_Pelayanan_Lab_Kesling,"+
                "set_akun2.Beban_Jasa_PJLab_Pelayanan_Lab_Kesling,set_akun2.Utang_Jasa_PJLab_Pelayanan_Lab_Kesling,set_akun2.Beban_Jasa_PJPengujian_Pelayanan_Lab_Kesling,"+
                "set_akun2.Utang_Jasa_PJPengujian_Pelayanan_Lab_Kesling,set_akun2.Beban_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling,set_akun2.Utang_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling,"+
                "set_akun2.Beban_Jasa_Analis_Pelayanan_Lab_Kesling,set_akun2.Utang_Jasa_Analis_Pelayanan_Lab_Kesling,set_akun2.Beban_KSO_Pelayanan_Lab_Kesling,"+
                "set_akun2.Utang_KSO_Pelayanan_Lab_Kesling,set_akun2.Beban_Jasa_Menejemen_Pelayanan_Lab_Kesling,set_akun2.Utang_Jasa_Menejemen_Pelayanan_Lab_Kesling from set_akun2"
            );
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    Suspen_Piutang_Pelayanan_Lab_Kesling=rs.getString("Suspen_Piutang_Pelayanan_Lab_Kesling");
                    Pendapatan_Pelayanan_Lab_Kesling=rs.getString("Pendapatan_Pelayanan_Lab_Kesling");
                    Beban_Jasa_Sarana_Pelayanan_Lab_Kesling=rs.getString("Beban_Jasa_Sarana_Pelayanan_Lab_Kesling");
                    Utang_Jasa_sarana_Pelayanan_Lab_Kesling=rs.getString("Utang_Jasa_sarana_Pelayanan_Lab_Kesling");
                    HPP_BHP_Pelayanan_Lab_Kesling=rs.getString("HPP_BHP_Pelayanan_Lab_Kesling");
                    Persediaan_BHP_Pelayanan_Lab_Kesling=rs.getString("Persediaan_BHP_Pelayanan_Lab_Kesling");
                    Beban_Jasa_PJLab_Pelayanan_Lab_Kesling=rs.getString("Beban_Jasa_PJLab_Pelayanan_Lab_Kesling");
                    Utang_Jasa_PJLab_Pelayanan_Lab_Kesling=rs.getString("Utang_Jasa_PJLab_Pelayanan_Lab_Kesling");
                    Beban_Jasa_PJPengujian_Pelayanan_Lab_Kesling=rs.getString("Beban_Jasa_PJPengujian_Pelayanan_Lab_Kesling");
                    Utang_Jasa_PJPengujian_Pelayanan_Lab_Kesling=rs.getString("Utang_Jasa_PJPengujian_Pelayanan_Lab_Kesling");
                    Beban_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling=rs.getString("Beban_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling");
                    Utang_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling=rs.getString("Utang_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling");
                    Beban_Jasa_Analis_Pelayanan_Lab_Kesling=rs.getString("Beban_Jasa_Analis_Pelayanan_Lab_Kesling");
                    Utang_Jasa_Analis_Pelayanan_Lab_Kesling=rs.getString("Utang_Jasa_Analis_Pelayanan_Lab_Kesling");
                    Beban_KSO_Pelayanan_Lab_Kesling=rs.getString("Beban_KSO_Pelayanan_Lab_Kesling");
                    Utang_KSO_Pelayanan_Lab_Kesling=rs.getString("Utang_KSO_Pelayanan_Lab_Kesling");
                    Beban_Jasa_Menejemen_Pelayanan_Lab_Kesling=rs.getString("Beban_Jasa_Menejemen_Pelayanan_Lab_Kesling");
                    Utang_Jasa_Menejemen_Pelayanan_Lab_Kesling=rs.getString("Utang_Jasa_Menejemen_Pelayanan_Lab_Kesling");
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

        Rentang = new widget.TextBox();
        NoVerifikasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnPrint = new widget.Button();
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

        Rentang.setHighlighter(null);
        Rentang.setName("Rentang"); // NOI18N
        Rentang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RentangKeyPressed(evt);
            }
        });

        NoVerifikasi.setHighlighter(null);
        NoVerifikasi.setName("NoVerifikasi"); // NOI18N
        NoVerifikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoVerifikasiKeyPressed(evt);
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

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Nota");
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

        Scroll2.setBorder(null);
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
        panelBayar.setPreferredSize(new java.awt.Dimension(100, 303));
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
        TtlSemua.setBounds(105, 10, 230, 23);

        TKembali.setEditable(false);
        TKembali.setText("0");
        TKembali.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TKembali.setHighlighter(null);
        TKembali.setName("TKembali"); // NOI18N
        panelBayar.add(TKembali);
        TKembali.setBounds(105, 270, 230, 23);

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
        scrollPane3.setBounds(105, 65, 573, 200);

        jLabel10.setText("Kembali : Rp.");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel10);
        jLabel10.setBounds(19, 270, 85, 23);

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
        /*if(KdPJ.getText().equals("")||NmPJ.getText().equals("")){
            Valid.textKosong(btnPJ,"Penanggung Jawab Laborat");
        }else if(KodePelanggan.getText().equals("")||NamaPelanggan.getText().equals("")){
            Valid.textKosong(NoBayar,"Pelanggan");
        }else if(KodeVerifikator.getText().equals("")||NamaVerifikator.getText().equals("")){
            Valid.textKosong(NoBayar,"Penanggung Jawab Verifikasi");
        }else if(NoPermintaan.getText().equals("")){
            Valid.textKosong(NoPermintaan,"Nomor Permintaan");
        }else if(NoBayar.getText().equals("")){
            Valid.textKosong(NoBayar,"Nomor Validasi");
        }else if(KodeSampel.getText().equals("")||NamaSampel.getText().equals("")){
            Valid.textKosong(NoBayar,"Sampel");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(NoBayar,"Data Validasi");
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                ChkJln.setSelected(false);
                try {                    
                    Sequel.AutoComitFalse();
                    berhasil=true;
                    if(Sequel.menyimpantf2("labkesling_validasi_pengujian_sampel","?,?,?,?,?,?,'Belum Bayar'","No.Validasi",6,new String[]{
                            NoPermintaan.getText(),NoBayar.getText(),KdPJ.getText(),KodeVerifikator.getText(),Valid.SetTgl(TanggalValidasi.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),DibayarOleh.getText()
                        })==true){
                        jasa_sarana=0;paket_bhp=0;jasa_pj_lab=0;jasa_pj_pengujian=0;jasa_verifikator=0;jasa_petugas=0;kso=0;jasa_menejemen=0;total=0;
                        for(i=0;i<tbValidasi.getRowCount();i++){
                            if(Sequel.menyimpantf2("labkesling_detail_validasi_pengujian_sampel","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Verifikasi",16,new String[]{
                                NoBayar.getText(),tbValidasi.getValueAt(i,11).toString(),tbValidasi.getValueAt(i,9).toString(),tbValidasi.getValueAt(i,0).toString(),tbValidasi.getValueAt(i,5).toString(),tbValidasi.getValueAt(i,3).toString(),
                                tbValidasi.getValueAt(i,4).toString(),tbValidasi.getValueAt(i,13).toString(),tbValidasi.getValueAt(i,14).toString(),tbValidasi.getValueAt(i,15).toString(),tbValidasi.getValueAt(i,16).toString(),
                                tbValidasi.getValueAt(i,17).toString(),tbValidasi.getValueAt(i,18).toString(),tbValidasi.getValueAt(i,19).toString(),tbValidasi.getValueAt(i,20).toString(),tbValidasi.getValueAt(i,21).toString()
                            })==false){
                                berhasil=false;
                            }else{
                                jasa_sarana=jasa_sarana+Double.parseDouble(tbValidasi.getValueAt(i,13).toString());
                                paket_bhp=paket_bhp+Double.parseDouble(tbValidasi.getValueAt(i,14).toString());
                                jasa_pj_lab=jasa_pj_lab+Double.parseDouble(tbValidasi.getValueAt(i,15).toString());
                                jasa_pj_pengujian=jasa_pj_pengujian+Double.parseDouble(tbValidasi.getValueAt(i,16).toString());
                                jasa_verifikator=jasa_verifikator+Double.parseDouble(tbValidasi.getValueAt(i,17).toString());
                                jasa_petugas=jasa_petugas+Double.parseDouble(tbValidasi.getValueAt(i,18).toString());
                                kso=kso+Double.parseDouble(tbValidasi.getValueAt(i,19).toString());
                                jasa_menejemen=jasa_menejemen+Double.parseDouble(tbValidasi.getValueAt(i,20).toString());
                                total=total+Double.parseDouble(tbValidasi.getValueAt(i,21).toString());
                            }                
                        } 
                        if(berhasil==true){
                            Sequel.queryu("delete from tampjurnal");  
                            if(jasa_sarana>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Pelayanan_Lab_Kesling+"','Beban Jasa Sarana Pelayanan Lab Kesling','"+jasa_sarana+"','0'","debet=debet+'"+(jasa_sarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }     
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_sarana_Pelayanan_Lab_Kesling+"','Utang Jasa Sarana Pelayanan Lab Kesling','0','"+jasa_sarana+"'","kredit=kredit+'"+(jasa_sarana)+"'","kd_rek='"+Utang_Jasa_sarana_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }                              
                            }
                            if(paket_bhp>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Pelayanan_Lab_Kesling+"','HPP BHP Pelayanan Lab Kesling','"+paket_bhp+"','0'","debet=debet+'"+(paket_bhp)+"'","kd_rek='"+HPP_BHP_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }     
                                if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Pelayanan_Lab_Kesling+"','Persediaan BHP Pelayanan Lab Kesling','0','"+paket_bhp+"'","kredit=kredit+'"+(paket_bhp)+"'","kd_rek='"+Persediaan_BHP_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }                              
                            }
                            if(jasa_pj_lab>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_PJLab_Pelayanan_Lab_Kesling+"','Beban Jasa PJ Lab Pelayanan Lab Kesling','"+jasa_pj_lab+"','0'","debet=debet+'"+(jasa_pj_lab)+"'","kd_rek='"+Beban_Jasa_PJLab_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }     
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_PJLab_Pelayanan_Lab_Kesling+"','Utang Jasa PJ Lab Pelayanan Lab Kesling','0','"+jasa_pj_lab+"'","kredit=kredit+'"+(jasa_pj_lab)+"'","kd_rek='"+Utang_Jasa_PJLab_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }                             
                            }
                            if(jasa_pj_pengujian>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_PJPengujian_Pelayanan_Lab_Kesling+"','Beban Jasa PJ Pengujian Pelayanan Lab Kesling','"+jasa_pj_pengujian+"','0'","debet=debet+'"+(jasa_pj_pengujian)+"'","kd_rek='"+Beban_Jasa_PJPengujian_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }      
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_PJPengujian_Pelayanan_Lab_Kesling+"','Utang Jasa PJ Pengujian Pelayanan Lab Kesling','0','"+jasa_pj_pengujian+"'","kredit=kredit+'"+(jasa_pj_pengujian)+"'","kd_rek='"+Utang_Jasa_PJPengujian_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }                              
                            }
                            if(jasa_verifikator>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling+"','Beban Jasa PJ Verifikasi Pelayanan Lab Kesling','"+jasa_verifikator+"','0'","debet=debet+'"+(jasa_verifikator)+"'","kd_rek='"+Beban_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }      
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling+"','Utang Jasa PJ Verifikasi Pelayanan Lab Kesling','0','"+jasa_verifikator+"'","kredit=kredit+'"+(jasa_verifikator)+"'","kd_rek='"+Utang_Jasa_PJVerifikasi_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }                             
                            }
                            if(jasa_petugas>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Analis_Pelayanan_Lab_Kesling+"','Beban Jasa Analis Pelayanan Lab Kesling','"+jasa_petugas+"','0'","debet=debet+'"+(jasa_petugas)+"'","kd_rek='"+Beban_Jasa_Analis_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }     
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Analis_Pelayanan_Lab_Kesling+"','Utang Jasa Analis Pelayanan Lab Kesling','0','"+jasa_petugas+"'","kredit=kredit+'"+(jasa_petugas)+"'","kd_rek='"+Utang_Jasa_Analis_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }                             
                            }
                            if(kso>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Pelayanan_Lab_Kesling+"','Beban KSO Pelayanan Lab Kesling','"+kso+"','0'","debet=debet+'"+(kso)+"'","kd_rek='"+Beban_KSO_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }     
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Pelayanan_Lab_Kesling+"','Utang KSO Pelayanan Lab Kesling','0','"+kso+"'","kredit=kredit+'"+(kso)+"'","kd_rek='"+Utang_KSO_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }                             
                            }
                            if(jasa_menejemen>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Pelayanan_Lab_Kesling+"','Beban Jasa Menejemen Pelayanan Lab Kesling','"+jasa_menejemen+"','0'","debet=debet+'"+(jasa_menejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }     
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Pelayanan_Lab_Kesling+"','Utang Jasa Menejemen Pelayanan Lab Kesling','0','"+jasa_menejemen+"'","kredit=kredit+'"+(jasa_menejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }                             
                            }
                            if(total>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Pelayanan_Lab_Kesling+"','Suspen Piutang Pelayanan Lab Kesling','"+total+"','0'","debet=debet+'"+(total)+"'","kd_rek='"+Suspen_Piutang_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }    
                                if(Sequel.menyimpantf("tampjurnal","'"+Pendapatan_Pelayanan_Lab_Kesling+"','Pendapatan Pelayanan Lab Kesling','0','"+total+"'","kredit=kredit+'"+(total)+"'","kd_rek='"+Pendapatan_Pelayanan_Lab_Kesling+"'")==false){
                                    berhasil=false;
                                }                                
                            }
                            if(berhasil==true){
                                berhasil=jur.simpanJurnal(NoBayar.getText(),"U","PELAYANAN LABORATORIUM KESEHATAN LINGKUNGAN "+NamaPelanggan.getText()+" DIPOSTING OLEH "+akses.getkode()); 
                            }  
                        }
                    }else{
                        berhasil=false;
                    }   
                    
                    if(berhasil==true){
                        Sequel.queryu("update labkesling_verifikasi_pengujian_sampel set status='Sudah Divalidasi' where no_verifikasi='"+NoVerifikasi.getText()+"'");
                        Sequel.Commit();
                        JOptionPane.showMessageDialog(null,"Proses simpan selesai...!");
                        Valid.tabelKosong(tabMode);
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
            }  
        }*/
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,NoBayar,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        /*this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(KdPJ.getText().equals("")||NmPJ.getText().equals("")){
            Valid.textKosong(btnPJ,"Penanggung Jawab Verifikasi");
        }else if(KodePelanggan.getText().equals("")||NamaPelanggan.getText().equals("")){
            Valid.textKosong(NoBayar,"Pelanggan");
        }else if(NoPermintaan.getText().equals("")){
            Valid.textKosong(NoPermintaan,"Nomor Permintaan");
        }else if(NoBayar.getText().equals("")){
            Valid.textKosong(NoBayar,"Nomor Verifikasi");
        }else if(KodeSampel.getText().equals("")||NamaSampel.getText().equals("")){
            Valid.textKosong(NoBayar,"Sampel");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(NoBayar,"Data Verifikasi");
        }else{
            Sequel.queryu("delete from temporary");
            for(i=0;i<tbValidasi.getRowCount();i++){ 
                Sequel.menyimpan("temporary","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                    "0",tbValidasi.getValueAt(i,1).toString(),tbValidasi.getValueAt(i,2).toString(),tbValidasi.getValueAt(i,3).toString(),tbValidasi.getValueAt(i,5).toString(),tbValidasi.getValueAt(i,6).toString(),tbValidasi.getValueAt(i,7).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                });               
            }
            
            Map<String, Object> param = new HashMap<>();
            param.put("nomorpermintaan",NoPermintaan.getText());
            param.put("novalidasi",NoBayar.getText());
            param.put("namapelanggan",NamaPelanggan.getText());
            param.put("alamatpelanggan",Sequel.cariIsi("select labkesling_pelanggan.alamat from labkesling_pelanggan where labkesling_pelanggan.kode_pelanggan=?",KodePelanggan.getText()));
            param.put("jenisampel",NamaSampel.getText());
            param.put("bakumutu",Sequel.cariIsi("select labkesling_master_sampel.baku_mutu from labkesling_master_sampel where labkesling_master_sampel.kode_sampel=?",KodeSampel.getText()));
            param.put("titiksampling",Sequel.cariIsi("select labkesling_permintaan_pengujian_sampel.lokasi_sampling from labkesling_permintaan_pengujian_sampel where labkesling_permintaan_pengujian_sampel.no_permintaan=?",NoPermintaan.getText()));
            param.put("disamplingoleh",Sequel.cariIsi("select labkesling_permintaan_pengujian_sampel.sampling_dilakukan_oleh from labkesling_permintaan_pengujian_sampel where labkesling_permintaan_pengujian_sampel.no_permintaan=?",NoPermintaan.getText()));
            param.put("waktusampling",Sequel.cariIsi("select date_format(labkesling_permintaan_pengujian_sampel.waktu_sampling,'%d/%m/%Y %H:%i:%s') from labkesling_permintaan_pengujian_sampel where labkesling_permintaan_pengujian_sampel.no_permintaan=?",NoPermintaan.getText()));
            param.put("waktuterimasampel",Sequel.cariIsi("select date_format(labkesling_permintaan_pengujian_sampel.waktu_diterima,'%d/%m/%Y %H:%i:%s') from labkesling_permintaan_pengujian_sampel where labkesling_permintaan_pengujian_sampel.no_permintaan=?",NoPermintaan.getText()));
            param.put("rentangwaktu",Rentang.getText());
            param.put("tanggalvalidasi",TanggalValidasi.getSelectedItem().toString());
            param.put("pjlaborat",NmPJ.getText());
            param.put("kodepjlaborat",KdPJ.getText());
            param.put("namapjpengujian",NamaVerifikator.getText());
            param.put("kodepjpengujian",KodeVerifikator.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdPJ.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmPJ.getText()+"\nID "+(finger.equals("")?KdPJ.getText():finger)+"\n"+TanggalValidasi.getSelectedItem()); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KodeVerifikator.getText());
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NamaVerifikator.getText()+"\nID "+(finger.equals("")?KodeVerifikator.getText():finger)+"\n"+TanggalValidasi.getSelectedItem()); 
            Valid.MyReport("rptValidasiPengujianSampelLaboratKesling.jasper","report","::[ Laporan Hasil Uji Laboratorium ]::",param);            
        }
        this.setCursor(Cursor.getDefaultCursor());*/
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnBatal,BtnCari);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void TanggalValidasiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalValidasiItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalValidasiItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void NoBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBayarKeyPressed
        Valid.pindah(evt, BtnKeluar,DibayarOleh);
    }//GEN-LAST:event_NoBayarKeyPressed

    private void DibayarOlehKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DibayarOlehKeyPressed
        Valid.pindah(evt,NoBayar,BtnSimpan);
    }//GEN-LAST:event_DibayarOlehKeyPressed

    private void RentangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RentangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RentangKeyPressed

    private void NoVerifikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoVerifikasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoVerifikasiKeyPressed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        
    }//GEN-LAST:event_TabDataMouseClicked

    private void TtlSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TtlSemuaKeyPressed
       // Valid.pindah(evt,BtnKeluar,BtnNota);
    }//GEN-LAST:event_TtlSemuaKeyPressed

    private void tbAkunBayarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAkunBayarPropertyChange
        
    }//GEN-LAST:event_tbAkunBayarPropertyChange

    private void tbAkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAkunBayarKeyPressed
       
    }//GEN-LAST:event_tbAkunBayarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariBayarActionPerformed(null);
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariBayarActionPerformed
        
    }//GEN-LAST:event_BtnCariBayarActionPerformed

    private void BtnCariBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariBayarKeyPressed

    }//GEN-LAST:event_BtnCariBayarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        
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
    private widget.Button BtnPrint;
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
    private widget.TextBox NoVerifikasi;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox Rentang;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    public widget.TextBox TKembali;
    private javax.swing.JTabbedPane TabData;
    private widget.Tanggal TanggalValidasi;
    private widget.TextBox TtlSemua;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
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
            myObj = new FileReader("./cache/validasipengujiansampellabkesling.iyem");
            root = mapper.readTree(myObj);
            response = root.path("validasipengujiansampellabkesling");
            if(response.isArray()){
                for(JsonNode list:response){
                    tabMode.addRow(new Object[]{
                        list.path("Kode").asText(),list.path("NamaParameter").asText(),list.path("Satuan").asText(),
                        list.path("HasilPemeriksaan").asText(),list.path("Keterangan").asText(),list.path("NilaiNormal").asText(),
                        list.path("MetodePengujian").asText(),list.path("Kategori").asText(),list.path("NoPenugasan").asText(),
                        list.path("NIPAnalis").asText(),list.path("NamaAnalis").asText(),list.path("NIPPJPengujian").asText(),
                        list.path("NamaPJPengujian").asText(),list.path("JasaSarana").asText(),list.path("PaketBHP").asText(),
                        list.path("JasaPJLab").asText(),list.path("JasaPJPengujian").asText(),list.path("JasaVerifikator").asText(),
                        list.path("JasaPetugas").asText(),list.path("KSO").asText(),list.path("JasaMenejemen").asText(),
                        list.path("Total").asText()
                    });
                }
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
    
    public void setData(String nopermintaan,String kodepelanggan,String namapelanggan,String kodesampel,String namasampel,String kodeverifikator,String namaverifikator,String rentang,String noverifikasi) {
        NoPermintaan.setText(nopermintaan);
        KodePelanggan.setText(kodepelanggan);
        NamaPelanggan.setText(namapelanggan);
        KodeSampel.setText(kodesampel);
        NamaSampel.setText(namasampel);
        //KodeVerifikator.setText(kodeverifikator);
        //NamaVerifikator.setText(namaverifikator);
        Rentang.setText(rentang);
        NoVerifikasi.setText(noverifikasi);
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
        BtnPrint.setEnabled(akses.getpembayaran_pengujian_sampel_lab_kesehatan_lingkungan());
    }
  
    private void autoNomor() {
        if(!KodeSampel.getText().trim().equals("")){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(labkesling_validasi_pengujian_sampel.no_validasi,5),signed)),0) from labkesling_validasi_pengujian_sampel inner join labkesling_permintaan_pengujian_sampel on labkesling_permintaan_pengujian_sampel.no_permintaan=labkesling_validasi_pengujian_sampel.no_permintaan where date_format(labkesling_validasi_pengujian_sampel.tanggal,'%Y')='"+TanggalValidasi.getSelectedItem().toString().substring(6,10)+"' and labkesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"'",KodeSampel.getText()+"/"+TanggalValidasi.getSelectedItem().toString().substring(6,10)+"/LHU/",5,NoBayar);   
        }        
    }
}

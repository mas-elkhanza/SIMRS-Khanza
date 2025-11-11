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
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dosen
 */
public final class LabKeslingPenugasanPengujianSampel extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean[] pilih; 
    private String[] Kode,NamaParameter,MetodePengujian,Satuan,Kategori;
    private int jml=0,i=0,index=0;
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public LabKeslingPenugasanPengujianSampel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Object[] row={"P","Kode","Nama Parameter","Metode Pengujian","Satuan","Kategori"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaan.setModel(tabMode);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPermintaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 6; i++) {
            TableColumn column = tbPermintaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(300);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }
        }
        tbPermintaan.setDefaultRenderer(Object.class, new WarnaTable());

        TNoPermintaan.setDocument(new batasInput((byte)20).getKata(TNoPermintaan));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCariPeriksa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariPeriksa.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariPeriksa.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariPeriksa.getText().length()>2){
                        tampil2();
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
                KdPetugas.requestFocus();
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
        
        ChkJln.setSelected(true);
        jam();
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

        KegiatanUsaha = new widget.TextBox();
        PersonalDihubungi = new widget.TextBox();
        KontakPelanggan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnPrint = new widget.Button();
        label11 = new widget.Label();
        LCount = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        PanelInput = new widget.PanelBiasa();
        KodePelanggan = new widget.TextBox();
        NamaPelanggan = new widget.TextBox();
        AlamatPelanggan = new widget.TextBox();
        jLabel9 = new widget.Label();
        WaktuDiterima = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel15 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoPermintaan = new widget.TextBox();
        jLabel7 = new widget.Label();
        jLabel18 = new widget.Label();
        KodeSampel = new widget.TextBox();
        NamaSampel = new widget.TextBox();
        BakuMutu = new widget.TextBox();
        jLabel5 = new widget.Label();
        TNoPermintaan1 = new widget.TextBox();
        jLabel10 = new widget.Label();
        KdPetugas1 = new widget.TextBox();
        NmPetugas1 = new widget.TextBox();
        btnPetugas1 = new widget.Button();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelisi5 = new widget.panelisi();
        jLabel19 = new widget.Label();
        KategoriParameter = new widget.ComboBox();
        label10 = new widget.Label();
        TCariPeriksa = new widget.TextBox();
        btnCariPeriksa = new widget.Button();
        BtnAllPeriksa = new widget.Button();
        BtnTambah = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbPermintaan = new widget.Table();

        KegiatanUsaha.setEditable(false);
        KegiatanUsaha.setHighlighter(null);
        KegiatanUsaha.setName("KegiatanUsaha"); // NOI18N

        PersonalDihubungi.setEditable(false);
        PersonalDihubungi.setHighlighter(null);
        PersonalDihubungi.setName("PersonalDihubungi"); // NOI18N

        KontakPelanggan.setEditable(false);
        KontakPelanggan.setHighlighter(null);
        KontakPelanggan.setName("KontakPelanggan"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penugasan Pengujian Sampel Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label11.setText("Record :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(label11);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setText("Cari");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setOpaque(false);
        FormInput.setPreferredSize(new java.awt.Dimension(560, 186));
        FormInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setSelected(true);
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
        FormInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        PanelInput.setBorder(null);
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 163));
        PanelInput.setLayout(null);

        KodePelanggan.setEditable(false);
        KodePelanggan.setHighlighter(null);
        KodePelanggan.setName("KodePelanggan"); // NOI18N
        KodePelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePelangganKeyPressed(evt);
            }
        });
        PanelInput.add(KodePelanggan);
        KodePelanggan.setBounds(94, 70, 90, 23);

        NamaPelanggan.setEditable(false);
        NamaPelanggan.setHighlighter(null);
        NamaPelanggan.setName("NamaPelanggan"); // NOI18N
        PanelInput.add(NamaPelanggan);
        NamaPelanggan.setBounds(186, 70, 220, 23);

        AlamatPelanggan.setEditable(false);
        AlamatPelanggan.setHighlighter(null);
        AlamatPelanggan.setName("AlamatPelanggan"); // NOI18N
        PanelInput.add(AlamatPelanggan);
        AlamatPelanggan.setBounds(408, 70, 270, 23);

        jLabel9.setText("P.J.Pengujian :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(0, 10, 90, 23);

        WaktuDiterima.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-11-2025" }));
        WaktuDiterima.setDisplayFormat("dd-MM-yyyy");
        WaktuDiterima.setName("WaktuDiterima"); // NOI18N
        WaktuDiterima.setOpaque(false);
        WaktuDiterima.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                WaktuDiterimaItemStateChanged(evt);
            }
        });
        WaktuDiterima.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuDiterimaKeyPressed(evt);
            }
        });
        PanelInput.add(WaktuDiterima);
        WaktuDiterima.setBounds(94, 40, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        PanelInput.add(CmbJam);
        CmbJam.setBounds(188, 40, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        PanelInput.add(CmbMenit);
        CmbMenit.setBounds(254, 40, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        PanelInput.add(CmbDetik);
        CmbDetik.setBounds(320, 40, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        PanelInput.add(ChkJln);
        ChkJln.setBounds(386, 40, 23, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        PanelInput.add(KdPetugas);
        KdPetugas.setBounds(94, 10, 115, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setHighlighter(null);
        NmPetugas.setName("NmPetugas"); // NOI18N
        PanelInput.add(NmPetugas);
        NmPetugas.setBounds(211, 10, 200, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('4');
        btnPetugas.setToolTipText("ALt+4");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        PanelInput.add(btnPetugas);
        btnPetugas.setBounds(413, 10, 28, 23);

        jLabel15.setText("Tgl.Penugasan :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(0, 40, 90, 23);

        jLabel4.setText("No.Permintaan :");
        jLabel4.setName("jLabel4"); // NOI18N
        PanelInput.add(jLabel4);
        jLabel4.setBounds(446, 40, 100, 23);

        TNoPermintaan.setHighlighter(null);
        TNoPermintaan.setName("TNoPermintaan"); // NOI18N
        TNoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPermintaanKeyPressed(evt);
            }
        });
        PanelInput.add(TNoPermintaan);
        TNoPermintaan.setBounds(550, 40, 130, 23);

        jLabel7.setText("Pelanggan :");
        jLabel7.setName("jLabel7"); // NOI18N
        PanelInput.add(jLabel7);
        jLabel7.setBounds(0, 70, 90, 23);

        jLabel18.setText("Kode Sampel :");
        jLabel18.setName("jLabel18"); // NOI18N
        PanelInput.add(jLabel18);
        jLabel18.setBounds(0, 100, 90, 23);

        KodeSampel.setEditable(false);
        KodeSampel.setHighlighter(null);
        KodeSampel.setName("KodeSampel"); // NOI18N
        KodeSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeSampelKeyPressed(evt);
            }
        });
        PanelInput.add(KodeSampel);
        KodeSampel.setBounds(94, 100, 65, 23);

        NamaSampel.setEditable(false);
        NamaSampel.setHighlighter(null);
        NamaSampel.setName("NamaSampel"); // NOI18N
        PanelInput.add(NamaSampel);
        NamaSampel.setBounds(161, 100, 180, 23);

        BakuMutu.setEditable(false);
        BakuMutu.setHighlighter(null);
        BakuMutu.setName("BakuMutu"); // NOI18N
        PanelInput.add(BakuMutu);
        BakuMutu.setBounds(343, 100, 340, 23);

        jLabel5.setText("No.Penugasan :");
        jLabel5.setName("jLabel5"); // NOI18N
        PanelInput.add(jLabel5);
        jLabel5.setBounds(446, 10, 100, 23);

        TNoPermintaan1.setHighlighter(null);
        TNoPermintaan1.setName("TNoPermintaan1"); // NOI18N
        TNoPermintaan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPermintaan1KeyPressed(evt);
            }
        });
        PanelInput.add(TNoPermintaan1);
        TNoPermintaan1.setBounds(550, 10, 130, 23);

        jLabel10.setText("Analis :");
        jLabel10.setName("jLabel10"); // NOI18N
        PanelInput.add(jLabel10);
        jLabel10.setBounds(0, 130, 90, 23);

        KdPetugas1.setEditable(false);
        KdPetugas1.setName("KdPetugas1"); // NOI18N
        KdPetugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugas1KeyPressed(evt);
            }
        });
        PanelInput.add(KdPetugas1);
        KdPetugas1.setBounds(94, 130, 115, 23);

        NmPetugas1.setEditable(false);
        NmPetugas1.setHighlighter(null);
        NmPetugas1.setName("NmPetugas1"); // NOI18N
        PanelInput.add(NmPetugas1);
        NmPetugas1.setBounds(211, 130, 200, 23);

        btnPetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas1.setMnemonic('4');
        btnPetugas1.setToolTipText("ALt+4");
        btnPetugas1.setName("btnPetugas1"); // NOI18N
        btnPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugas1ActionPerformed(evt);
            }
        });
        PanelInput.add(btnPetugas1);
        btnPetugas1.setBounds(413, 130, 28, 23);

        scrollInput.setViewportView(PanelInput);

        FormInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel19.setText("Kategori Parameter :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi5.add(jLabel19);

        KategoriParameter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Fisika", "Kimia", "Mikrobiologi" }));
        KategoriParameter.setName("KategoriParameter"); // NOI18N
        KategoriParameter.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi5.add(KategoriParameter);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi5.add(label10);

        TCariPeriksa.setToolTipText("Alt+C");
        TCariPeriksa.setName("TCariPeriksa"); // NOI18N
        TCariPeriksa.setPreferredSize(new java.awt.Dimension(281, 23));
        TCariPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariPeriksaActionPerformed(evt);
            }
        });
        TCariPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPeriksaKeyPressed(evt);
            }
        });
        panelisi5.add(TCariPeriksa);

        btnCariPeriksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        btnCariPeriksa.setMnemonic('1');
        btnCariPeriksa.setToolTipText("Alt+1");
        btnCariPeriksa.setName("btnCariPeriksa"); // NOI18N
        btnCariPeriksa.setPreferredSize(new java.awt.Dimension(28, 23));
        btnCariPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPeriksaActionPerformed(evt);
            }
        });
        btnCariPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariPeriksaKeyPressed(evt);
            }
        });
        panelisi5.add(btnCariPeriksa);

        BtnAllPeriksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllPeriksa.setMnemonic('2');
        BtnAllPeriksa.setToolTipText("Alt+2");
        BtnAllPeriksa.setName("BtnAllPeriksa"); // NOI18N
        BtnAllPeriksa.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllPeriksaActionPerformed(evt);
            }
        });
        BtnAllPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllPeriksaKeyPressed(evt);
            }
        });
        panelisi5.add(BtnAllPeriksa);

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
        panelisi5.add(BtnTambah);

        jPanel3.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPermintaan.setName("tbPermintaan"); // NOI18N
        tbPermintaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPermintaanMouseClicked(evt);
            }
        });
        tbPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPermintaanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbPermintaan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        isReset();
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnCari,TCariPeriksa);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
    LabKeslingCariPermintaanPengujianSampel form=new LabKeslingCariPermintaanPengujianSampel(null,false);
    form.isCek();
    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    form.setLocationRelativeTo(this);
    form.setVisible(true);
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void WaktuDiterimaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuDiterimaKeyPressed
        Valid.pindah(evt, KdPetugas, TCariPeriksa);
    }//GEN-LAST:event_WaktuDiterimaKeyPressed

    private void TCariPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPeriksaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnCariPeriksaActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            btnCariPeriksa.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TNoPermintaan.requestFocus();
        }
    }//GEN-LAST:event_TCariPeriksaKeyPressed

    private void btnCariPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPeriksaActionPerformed
        if(KodeSampel.getText().trim().equals("")||NamaSampel.getText().trim().equals("")){
            //Valid.textKosong(BtnSampel,"Kode Sampel");
        }else{
            tampil2();
        }
    }//GEN-LAST:event_btnCariPeriksaActionPerformed

    private void btnCariPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariPeriksaKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            if(KodeSampel.getText().trim().equals("")||NamaSampel.getText().trim().equals("")){
                Valid.textKosong(BtnSampel,"Kode Sampel");
            }else{
                tampil2();
            }
        }else{
            Valid.pindah(evt, TCariPeriksa, BtnAllPeriksa);
        }*/
    }//GEN-LAST:event_btnCariPeriksaKeyPressed

    private void BtnAllPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPeriksaActionPerformed
        /*if(KodeSampel.getText().trim().equals("")||NamaSampel.getText().trim().equals("")){
            Valid.textKosong(BtnSampel,"Kode Sampel");
        }else{
            TCariPeriksa.setText("");
            tampil();
            tampil2();
        }*/
    }//GEN-LAST:event_BtnAllPeriksaActionPerformed

    private void BtnAllPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllPeriksaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllPeriksaActionPerformed(null);
        }else{
            Valid.pindah(evt, btnCariPeriksa, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllPeriksaKeyPressed

    private void tbPermintaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPermintaanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
               // getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPermintaanMouseClicked

    private void tbPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPermintaanKeyPressed
        if(tbPermintaan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    int row=tbPermintaan.getSelectedColumn();
                    if((row!=0)||(row!=20)){
                        if(tbPermintaan.getSelectedRow()>-1){
                            tbPermintaan.setValueAt(true,tbPermintaan.getSelectedRow(),0);
                        }
                        TCariPeriksa.setText("");
                        TCariPeriksa.requestFocus();
                    }
                    //getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                   // getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPermintaanKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt, KodeSampel,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        /*jml=0;
        for(i=0;i<tbPermintaan.getRowCount();i++){
            if(tbPermintaan.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        if(KdPetugas.getText().equals("")||NmPetugas.getText().equals("")){
            Valid.textKosong(btnPetugas,"Penerima Sampel");
        }else if(KodePelanggan.getText().equals("")||NamaPelanggan.getText().equals("")){
            Valid.textKosong(btnPelanggan,"Pelanggan");
        }else if(TNoPermintaan.getText().equals("")){
            Valid.textKosong(TNoPermintaan,"Nomor Permintaan");
        }else if(LokasiSampling.getText().equals("")){
            Valid.textKosong(LokasiSampling,"Lokasi Sampling");
        }else if(DeskripsiSampling.getText().equals("")){
            Valid.textKosong(DeskripsiSampling,"Deskripsi Sampling");
        }else if(JenisSampel.getText().equals("")){
            Valid.textKosong(JenisSampel,"Jenis Sampel");
        }else if(JmlSampel.getText().equals("")){
            Valid.textKosong(JmlSampel,"Jumlah Sampel");
        }else if(DisamplingOleh.getText().equals("")){
            Valid.textKosong(DisamplingOleh,"Sampling Dilakukan Oleh");
        }else if(VolumeSampel.getText().equals("")){
            Valid.textKosong(VolumeSampel,"Volume Sampel");
        }else if(WadahSampel.getText().equals("")){
            Valid.textKosong(WadahSampel,"Wadah Sampel");
        }else if(KondisiWadah.getText().equals("")){
            Valid.textKosong(KondisiWadah,"Kondisi Wadah");
        }else if(KodeSampel.getText().equals("")||NamaSampel.getText().equals("")){
            Valid.textKosong(BtnSampel,"Sampel");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(TCariPeriksa,"Data Permintaan");
        }else if(jml==0){
            Valid.textKosong(TCariPeriksa,"Data Permintaan");
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                ChkJln.setSelected(false);
                try {                    
                    koneksi.setAutoCommit(false);
                    if(Sequel.menyimpantf2("laborat_kesling_permintaan_pengujian_sampel","?,?,?,?,?,?,?,?,?,?,?,?,?,?,'Permintaan Baru'","No.Permintaan",14,new String[]{
                            TNoPermintaan.getText(),KodePelanggan.getText(),KdPetugas.getText(),Valid.SetTgl(WaktuSampling.getSelectedItem()+"")+" "+WaktuSampling.getSelectedItem().toString().substring(11,19),
                            Valid.SetTgl(WaktuDiterima.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),LokasiSampling.getText(),DeskripsiSampling.getText(),
                            JenisSampel.getText(),JmlSampel.getText(),DisamplingOleh.getText(),VolumeSampel.getText(),WadahSampel.getText(),KondisiWadah.getText(),KodeSampel.getText()
                        })==true){
                        for(i=0;i<tbPermintaan.getRowCount();i++){ 
                            if(tbPermintaan.getValueAt(i,0).toString().equals("true")){
                                Sequel.menyimpan2("laborat_kesling_detail_permintaan_pengujian_sampel","?,?","pemeriksaan radiologi",2,new String[]{
                                    TNoPermintaan.getText(),tbPermintaan.getValueAt(i,1).toString()
                                });
                            }                        
                        } 
                        isReset();
                        emptTeks();
                    }else{
                        autoNomor();
                        if(Sequel.menyimpantf2("laborat_kesling_permintaan_pengujian_sampel","?,?,?,?,?,?,?,?,?,?,?,?,?,?,'Permintaan Baru'","No.Permintaan",14,new String[]{
                                TNoPermintaan.getText(),KodePelanggan.getText(),KdPetugas.getText(),Valid.SetTgl(WaktuSampling.getSelectedItem()+"")+" "+WaktuSampling.getSelectedItem().toString().substring(11,19),
                                Valid.SetTgl(WaktuDiterima.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),LokasiSampling.getText(),DeskripsiSampling.getText(),
                                JenisSampel.getText(),JmlSampel.getText(),DisamplingOleh.getText(),VolumeSampel.getText(),WadahSampel.getText(),KondisiWadah.getText(),KodeSampel.getText()
                            })==true){
                            for(i=0;i<tbPermintaan.getRowCount();i++){ 
                                if(tbPermintaan.getValueAt(i,0).toString().equals("true")){
                                    Sequel.menyimpan2("laborat_kesling_detail_permintaan_pengujian_sampel","?,?","pemeriksaan radiologi",2,new String[]{
                                        TNoPermintaan.getText(),tbPermintaan.getValueAt(i,1).toString()
                                    });
                                }                        
                            } 
                            isReset();
                            emptTeks();
                        }else{
                            autoNomor();
                            if(Sequel.menyimpantf2("laborat_kesling_permintaan_pengujian_sampel","?,?,?,?,?,?,?,?,?,?,?,?,?,?,'Permintaan Baru'","No.Permintaan",14,new String[]{
                                    TNoPermintaan.getText(),KodePelanggan.getText(),KdPetugas.getText(),Valid.SetTgl(WaktuSampling.getSelectedItem()+"")+" "+WaktuSampling.getSelectedItem().toString().substring(11,19),
                                    Valid.SetTgl(WaktuDiterima.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),LokasiSampling.getText(),DeskripsiSampling.getText(),
                                    JenisSampel.getText(),JmlSampel.getText(),DisamplingOleh.getText(),VolumeSampel.getText(),WadahSampel.getText(),KondisiWadah.getText(),KodeSampel.getText()
                                })==true){
                                for(i=0;i<tbPermintaan.getRowCount();i++){ 
                                    if(tbPermintaan.getValueAt(i,0).toString().equals("true")){
                                        Sequel.menyimpan2("laborat_kesling_detail_permintaan_pengujian_sampel","?,?","pemeriksaan radiologi",2,new String[]{
                                            TNoPermintaan.getText(),tbPermintaan.getValueAt(i,1).toString()
                                        });
                                    }                        
                                } 
                                isReset();
                                emptTeks();
                            }else{
                                autoNomor();
                                if(Sequel.menyimpantf2("laborat_kesling_permintaan_pengujian_sampel","?,?,?,?,?,?,?,?,?,?,?,?,?,?,'Permintaan Baru'","No.Permintaan",14,new String[]{
                                        TNoPermintaan.getText(),KodePelanggan.getText(),KdPetugas.getText(),Valid.SetTgl(WaktuSampling.getSelectedItem()+"")+" "+WaktuSampling.getSelectedItem().toString().substring(11,19),
                                        Valid.SetTgl(WaktuDiterima.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),LokasiSampling.getText(),DeskripsiSampling.getText(),
                                        JenisSampel.getText(),JmlSampel.getText(),DisamplingOleh.getText(),VolumeSampel.getText(),WadahSampel.getText(),KondisiWadah.getText(),KodeSampel.getText()
                                    })==true){
                                    for(i=0;i<tbPermintaan.getRowCount();i++){ 
                                        if(tbPermintaan.getValueAt(i,0).toString().equals("true")){
                                            Sequel.menyimpan2("laborat_kesling_detail_permintaan_pengujian_sampel","?,?","pemeriksaan radiologi",2,new String[]{
                                                TNoPermintaan.getText(),tbPermintaan.getValueAt(i,1).toString()
                                            });
                                        }                        
                                    } 
                                    isReset();
                                    emptTeks();
                                }
                            } 
                        } 
                    }   
                    koneksi.setAutoCommit(true);                    
                    JOptionPane.showMessageDialog(null,"Proses simpan selesai...!");
                } catch (Exception e) {
                    System.out.println(e);
                }    
                ChkJln.setSelected(true);            
            }  
        }*/
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnPrint);
        }
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,TCariPeriksa,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else{            
            Valid.pindah(evt,TCariPeriksa,WaktuDiterima);
        }
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        /*this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        jml=0;
        for(i=0;i<tbPermintaan.getRowCount();i++){
            if(tbPermintaan.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        if(KdPetugas.getText().equals("")||NmPetugas.getText().equals("")){
            Valid.textKosong(btnPetugas,"Penerima Sampel");
        }else if(KodePelanggan.getText().equals("")||NamaPelanggan.getText().equals("")){
            Valid.textKosong(btnPelanggan,"Pelanggan");
        }else if(TNoPermintaan.getText().equals("")){
            Valid.textKosong(TNoPermintaan,"Nomor Permintaan");
        }else if(LokasiSampling.getText().equals("")){
            Valid.textKosong(LokasiSampling,"Lokasi Sampling");
        }else if(DeskripsiSampling.getText().equals("")){
            Valid.textKosong(DeskripsiSampling,"Deskripsi Sampling");
        }else if(JenisSampel.getText().equals("")){
            Valid.textKosong(JenisSampel,"Jenis Sampel");
        }else if(JmlSampel.getText().equals("")){
            Valid.textKosong(JmlSampel,"Jumlah Sampel");
        }else if(DisamplingOleh.getText().equals("")){
            Valid.textKosong(DisamplingOleh,"Sampling Dilakukan Oleh");
        }else if(VolumeSampel.getText().equals("")){
            Valid.textKosong(VolumeSampel,"Volume Sampel");
        }else if(WadahSampel.getText().equals("")){
            Valid.textKosong(WadahSampel,"Wadah Sampel");
        }else if(KondisiWadah.getText().equals("")){
            Valid.textKosong(KondisiWadah,"Kondisi Wadah");
        }else if(KodeSampel.getText().equals("")||NamaSampel.getText().equals("")){
            Valid.textKosong(BtnSampel,"Sampel");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(TCariPeriksa,"Data Permintaan");
        }else if(jml==0){
            Valid.textKosong(TCariPeriksa,"Data Permintaan");
        }else{
            Sequel.queryu("delete from temporary");
            for(i=0;i<tbPermintaan.getRowCount();i++){ 
                if(tbPermintaan.getValueAt(i,0).toString().equals("true")){
                    Sequel.menyimpan("temporary","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                        "0",tbPermintaan.getValueAt(i,1).toString(),tbPermintaan.getValueAt(i,2).toString(),tbPermintaan.getValueAt(i,3).toString(),tbPermintaan.getValueAt(i,5).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                    });
                }                
            }
            
            Map<String, Object> param = new HashMap<>();
            param.put("namapelanggan",NamaPelanggan.getText());
            param.put("alamatpelanggan",AlamatPelanggan.getText());
            param.put("kegiatanusaha",KegiatanUsaha.getText());
            param.put("personaldihubungi",PersonalDihubungi.getText());
            param.put("kontakpelanggan",KontakPelanggan.getText());
            param.put("lokasisampling",LokasiSampling.getText());
            param.put("waktusampling",WaktuSampling.getSelectedItem());
            param.put("jenissampel",JenisSampel.getText());
            param.put("jumlahsampel",JmlSampel.getText());
            param.put("volumesampel",VolumeSampel.getText());
            param.put("wadahsampel",WadahSampel.getText());
            param.put("kondisiwadah",KondisiWadah.getText());
            param.put("kodesampel",KodeSampel.getText()+" "+NamaSampel.getText());
            param.put("petugassampling",DisamplingOleh.getText());
            param.put("waktupenerimaan",WaktuDiterima.getSelectedItem()+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
            param.put("deskripsisampel",DeskripsiSampling.getText());
            param.put("bakumutu",BakuMutu.getText());
            param.put("petugaspelayanan",NmPetugas.getText());
            param.put("nomorpermintaan",TNoPermintaan.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdPetugas.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmPetugas.getText()+"\nID "+(finger.equals("")?KdPetugas.getText():finger)+"\n"+WaktuDiterima.getSelectedItem()); 
            Valid.MyReport("rptPermintaanPengujianSampelLaboratKesling.jasper","report","::[ Permintaan Pengujian Sampel Laborat Kesehatan Lingkungan ]::",param);            
        }
        this.setCursor(Cursor.getDefaultCursor());*/
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal,BtnCari);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariPeriksaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariPeriksaActionPerformed

    private void TNoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPermintaanKeyPressed
        //Valid.pindah(evt,TCariPeriksa,btnPelanggan);
    }//GEN-LAST:event_TNoPermintaanKeyPressed

    private void WaktuDiterimaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_WaktuDiterimaItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_WaktuDiterimaItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/permintaanpengujiansampellabkesling.iyem")>30){
                tampil();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        LabKeslingNilaiNormalBakuMutu form=new LabKeslingNilaiNormalBakuMutu(null,false);
        form.emptTeks();
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());

    }//GEN-LAST:event_BtnTambahActionPerformed

    private void KodePelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePelangganKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPelangganActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoPermintaan,LokasiSampling);
        }*/
    }//GEN-LAST:event_KodePelangganKeyPressed

    private void KodeSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeSampelKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSampelActionPerformed(null);
        }else{            
            Valid.pindah(evt,KondisiWadah,BtnSimpan);
        }*/
    }//GEN-LAST:event_KodeSampelKeyPressed

    private void TNoPermintaan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPermintaan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoPermintaan1KeyPressed

    private void KdPetugas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugas1KeyPressed

    private void btnPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPetugas1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingPenugasanPengujianSampel dialog = new LabKeslingPenugasanPengujianSampel(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatPelanggan;
    private widget.TextBox BakuMutu;
    private widget.Button BtnAllPeriksa;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private javax.swing.JPanel FormInput;
    private widget.ComboBox KategoriParameter;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas1;
    private widget.TextBox KegiatanUsaha;
    private widget.TextBox KodePelanggan;
    private widget.TextBox KodeSampel;
    private widget.TextBox KontakPelanggan;
    private widget.Label LCount;
    private widget.TextBox NamaPelanggan;
    private widget.TextBox NamaSampel;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas1;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox PersonalDihubungi;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCariPeriksa;
    private widget.TextBox TNoPermintaan;
    private widget.TextBox TNoPermintaan1;
    private widget.Tanggal WaktuDiterima;
    private widget.Button btnCariPeriksa;
    private widget.Button btnPetugas;
    private widget.Button btnPetugas1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel15;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private widget.Label label10;
    private widget.Label label11;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi5;
    private widget.ScrollPane scrollInput;
    private widget.Table tbPermintaan;
    // End of variables declaration//GEN-END:variables
    
    
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            file=new File("./cache/permintaanpengujiansampellabkesling.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement(
                "select laborat_kesling_parameter_pengujian.kode_parameter,laborat_kesling_parameter_pengujian.nama_parameter,laborat_kesling_parameter_pengujian.metode_pengujian,laborat_kesling_parameter_pengujian.satuan,laborat_kesling_parameter_pengujian.kategori,laborat_kesling_nilai_normal_baku_mutu.kode_sampel "+
                "from laborat_kesling_parameter_pengujian inner join laborat_kesling_nilai_normal_baku_mutu on laborat_kesling_nilai_normal_baku_mutu.kode_parameter=laborat_kesling_parameter_pengujian.kode_parameter order by laborat_kesling_nilai_normal_baku_mutu.kode_sampel,laborat_kesling_parameter_pengujian.kode_parameter"
            );
            try{           
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"NamaParameter\":\"").append(rs.getString(2)).append("\",\"MetodePengujian\":\"").append(rs.getString(3)).append("\",\"Satuan\":\"").append(rs.getString(4)).append("\",\"Kategori\":\"").append(rs.getString(5)).append("\",\"Sampel\":\"").append(rs.getString(6)).append("\"},");
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }

            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"permintaanpengujiansampellabkesling\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    private void tampil2() {   
        try {
            jml=0;
            for(i=0;i<tbPermintaan.getRowCount();i++){
                if(tbPermintaan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih = new boolean[jml];
            Kode = new String[jml];
            NamaParameter = new String[jml];
            MetodePengujian = new String[jml];
            Satuan = new String[jml];
            Kategori = new String[jml];
            
            index=0; 
            for(i=0;i<tbPermintaan.getRowCount();i++){
                if(tbPermintaan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    Kode[index]=tbPermintaan.getValueAt(i,1).toString();
                    NamaParameter[index]=tbPermintaan.getValueAt(i,2).toString();
                    MetodePengujian[index]=tbPermintaan.getValueAt(i,3).toString();
                    Satuan[index]=tbPermintaan.getValueAt(i,4).toString();
                    Kategori[index]=tbPermintaan.getValueAt(i,5).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabMode);
            for(i=0;i<jml;i++){                
                tabMode.addRow(new Object[] {pilih[i],Kode[i],NamaParameter[i],MetodePengujian[i],Satuan[i],Kategori[i]});
            }  
            
            pilih=null;
            Kode=null;
            NamaParameter=null;
            
            myObj = new FileReader("./cache/permintaanpengujiansampellabkesling.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanpengujiansampellabkesling");
            if(response.isArray()){
                if(TCariPeriksa.getText().trim().equals("")){
                    if(KategoriParameter.getSelectedItem().toString().equals("Semua")){
                        for(JsonNode list:response){
                            if(list.path("Sampel").asText().equals(KodeSampel.getText())){
                                tabMode.addRow(new Object[]{
                                    false,list.path("Kode").asText(),list.path("NamaParameter").asText(),list.path("MetodePengujian").asText(),list.path("Satuan").asText(),list.path("Kategori").asText()
                                });
                            }
                        }
                    }else{
                        for(JsonNode list:response){
                            if(list.path("Sampel").asText().equals(KodeSampel.getText())&&list.path("Kategori").asText().equals(KategoriParameter.getSelectedItem().toString())){
                                tabMode.addRow(new Object[]{
                                    false,list.path("Kode").asText(),list.path("NamaParameter").asText(),list.path("MetodePengujian").asText(),list.path("Satuan").asText(),list.path("Kategori").asText()
                                });
                            }
                        }
                    }   
                }else{
                    if(KategoriParameter.getSelectedItem().toString().equals("Semua")){
                        for(JsonNode list:response){
                            if(list.path("Sampel").asText().equals(KodeSampel.getText())&&(list.path("Kode").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase())||list.path("NamaParameter").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase())||list.path("MetodePengujian").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase()))){
                                tabMode.addRow(new Object[]{
                                    false,list.path("Kode").asText(),list.path("NamaParameter").asText(),list.path("MetodePengujian").asText(),list.path("Satuan").asText(),list.path("Kategori").asText()
                                });
                            }
                        }
                    }else{
                        for(JsonNode list:response){
                            if(list.path("Sampel").asText().equals(KodeSampel.getText())&&(list.path("Kode").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase())||list.path("NamaParameter").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase())||list.path("MetodePengujian").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase()))){
                                if(list.path("Kategori").asText().equals(KategoriParameter.getSelectedItem().toString())){
                                    tabMode.addRow(new Object[]{
                                        false,list.path("Kode").asText(),list.path("NamaParameter").asText(),list.path("MetodePengujian").asText(),list.path("Satuan").asText(),list.path("Kategori").asText()
                                    });
                                }
                            }
                        }
                    }
                        
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
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void isReset(){
        jml=tbPermintaan.getRowCount();
        for(i=0;i<jml;i++){ 
            tbPermintaan.setValueAt(false,i,0);
        }
        Valid.tabelKosong(tabMode);
    }
    
    public void emptTeks() {
        TNoPermintaan.requestFocus();
        KodePelanggan.setText("");
        NamaPelanggan.setText("");
        AlamatPelanggan.setText("");
        KegiatanUsaha.setText("");
        PersonalDihubungi.setText("");
        KontakPelanggan.setText("");
        KodeSampel.setText("");
        NamaSampel.setText("");
        BakuMutu.setText("");
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
        BtnSimpan.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
        BtnPrint.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
        BtnTambah.setEnabled(akses.getnilai_normal_baku_mutu_lab_kesehatan_lingkungan());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }  
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>417){
                ChkInput.setVisible(false);
                FormInput.setPreferredSize(new Dimension(WIDTH,245));
                scrollInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                FormInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
                scrollInput.setVisible(true);      
                ChkInput.setVisible(true);
            }
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            FormInput.setPreferredSize(new Dimension(WIDTH,20));
            scrollInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
  
    private void autoNomor() {
        if(!KodeSampel.getText().trim().equals("")){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(laborat_kesling_permintaan_pengujian_sampel.no_permintaan,5),signed)),0) from laborat_kesling_permintaan_pengujian_sampel where date_format(laborat_kesling_permintaan_pengujian_sampel.waktu_diterima,'%Y')='"+WaktuDiterima.getSelectedItem().toString().substring(6,10)+"' and laborat_kesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"'",KodeSampel.getText()+"/"+Valid.SetTgl(WaktuDiterima.getSelectedItem()+"").replaceAll("-","")+"/",5,TNoPermintaan);   
        }        
    }

}

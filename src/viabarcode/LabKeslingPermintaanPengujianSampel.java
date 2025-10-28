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

/**
 *
 * @author dosen
 */
public final class LabKeslingPermintaanPengujianSampel extends javax.swing.JDialog {
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
    public LabKeslingPermintaanPengujianSampel(java.awt.Frame parent, boolean modal) {
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
        LokasiSampling.setDocument(new batasInput((int)100).getKata(LokasiSampling));
        DeskripsiSampling.setDocument(new batasInput((int)100).getKata(DeskripsiSampling));
        JenisSampel.setDocument(new batasInput((int)30).getKata(JenisSampel));
        JmlSampel.setDocument(new batasInput((int)20).getKata(JmlSampel));
        DisamplingOleh.setDocument(new batasInput((int)30).getKata(DisamplingOleh));
        VolumeSampel.setDocument(new batasInput((int)20).getKata(VolumeSampel));
        WadahSampel.setDocument(new batasInput((int)30).getKata(WadahSampel));
        KondisiWadah.setDocument(new batasInput((int)40).getKata(KondisiWadah));
        
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
        LokasiSampling = new widget.TextBox();
        jLabel5 = new widget.Label();
        DeskripsiSampling = new widget.TextBox();
        jLabel6 = new widget.Label();
        btnPelanggan = new widget.Button();
        jLabel7 = new widget.Label();
        jLabel16 = new widget.Label();
        WaktuSampling = new widget.Tanggal();
        jLabel8 = new widget.Label();
        JenisSampel = new widget.TextBox();
        JmlSampel = new widget.TextBox();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        VolumeSampel = new widget.TextBox();
        WadahSampel = new widget.TextBox();
        jLabel13 = new widget.Label();
        KondisiWadah = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel17 = new widget.Label();
        DisamplingOleh = new widget.TextBox();
        jLabel18 = new widget.Label();
        KodeSampel = new widget.TextBox();
        NamaSampel = new widget.TextBox();
        BtnSampel = new widget.Button();
        BakuMutu = new widget.TextBox();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Pengujian Sampel Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(560, 245));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 213));
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
        KodePelanggan.setBounds(104, 70, 90, 23);

        NamaPelanggan.setEditable(false);
        NamaPelanggan.setHighlighter(null);
        NamaPelanggan.setName("NamaPelanggan"); // NOI18N
        PanelInput.add(NamaPelanggan);
        NamaPelanggan.setBounds(196, 70, 210, 23);

        AlamatPelanggan.setEditable(false);
        AlamatPelanggan.setHighlighter(null);
        AlamatPelanggan.setName("AlamatPelanggan"); // NOI18N
        PanelInput.add(AlamatPelanggan);
        AlamatPelanggan.setBounds(408, 70, 242, 23);

        jLabel9.setText("Sampel Diterima :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(0, 10, 100, 23);

        WaktuDiterima.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-10-2025" }));
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
        WaktuDiterima.setBounds(104, 40, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        PanelInput.add(CmbJam);
        CmbJam.setBounds(198, 40, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        PanelInput.add(CmbMenit);
        CmbMenit.setBounds(264, 40, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        PanelInput.add(CmbDetik);
        CmbDetik.setBounds(330, 40, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        PanelInput.add(ChkJln);
        ChkJln.setBounds(400, 40, 23, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        PanelInput.add(KdPetugas);
        KdPetugas.setBounds(104, 10, 105, 23);

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

        jLabel15.setText("Waktu Diterima :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(0, 40, 100, 23);

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

        LokasiSampling.setHighlighter(null);
        LokasiSampling.setName("LokasiSampling"); // NOI18N
        LokasiSampling.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiSamplingKeyPressed(evt);
            }
        });
        PanelInput.add(LokasiSampling);
        LokasiSampling.setBounds(104, 100, 230, 23);

        jLabel5.setText("Lokasi Sampling :");
        jLabel5.setName("jLabel5"); // NOI18N
        PanelInput.add(jLabel5);
        jLabel5.setBounds(0, 100, 100, 23);

        DeskripsiSampling.setHighlighter(null);
        DeskripsiSampling.setName("DeskripsiSampling"); // NOI18N
        DeskripsiSampling.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DeskripsiSamplingKeyPressed(evt);
            }
        });
        PanelInput.add(DeskripsiSampling);
        DeskripsiSampling.setBounds(450, 100, 230, 23);

        jLabel6.setText("Deskripsi Sampel :");
        jLabel6.setName("jLabel6"); // NOI18N
        PanelInput.add(jLabel6);
        jLabel6.setBounds(341, 100, 105, 23);

        btnPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPelanggan.setMnemonic('4');
        btnPelanggan.setToolTipText("ALt+4");
        btnPelanggan.setName("btnPelanggan"); // NOI18N
        btnPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPelangganActionPerformed(evt);
            }
        });
        btnPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPelangganKeyPressed(evt);
            }
        });
        PanelInput.add(btnPelanggan);
        btnPelanggan.setBounds(652, 70, 28, 23);

        jLabel7.setText("Pelanggan :");
        jLabel7.setName("jLabel7"); // NOI18N
        PanelInput.add(jLabel7);
        jLabel7.setBounds(0, 70, 100, 23);

        jLabel16.setText("Waktu Sampling :");
        jLabel16.setName("jLabel16"); // NOI18N
        PanelInput.add(jLabel16);
        jLabel16.setBounds(446, 10, 100, 23);

        WaktuSampling.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-10-2025 13:05:20" }));
        WaktuSampling.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        WaktuSampling.setName("WaktuSampling"); // NOI18N
        WaktuSampling.setOpaque(false);
        PanelInput.add(WaktuSampling);
        WaktuSampling.setBounds(550, 10, 130, 23);

        jLabel8.setText("Jenis Sampel :");
        jLabel8.setName("jLabel8"); // NOI18N
        PanelInput.add(jLabel8);
        jLabel8.setBounds(0, 130, 100, 23);

        JenisSampel.setHighlighter(null);
        JenisSampel.setName("JenisSampel"); // NOI18N
        JenisSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisSampelKeyPressed(evt);
            }
        });
        PanelInput.add(JenisSampel);
        JenisSampel.setBounds(104, 130, 140, 23);

        JmlSampel.setHighlighter(null);
        JmlSampel.setName("JmlSampel"); // NOI18N
        JmlSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JmlSampelKeyPressed(evt);
            }
        });
        PanelInput.add(JmlSampel);
        JmlSampel.setBounds(330, 130, 59, 23);

        jLabel11.setText("Jml Sampel :");
        jLabel11.setName("jLabel11"); // NOI18N
        PanelInput.add(jLabel11);
        jLabel11.setBounds(246, 130, 80, 23);

        jLabel12.setText("Volume Sampel :");
        jLabel12.setName("jLabel12"); // NOI18N
        PanelInput.add(jLabel12);
        jLabel12.setBounds(0, 160, 100, 23);

        VolumeSampel.setHighlighter(null);
        VolumeSampel.setName("VolumeSampel"); // NOI18N
        VolumeSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VolumeSampelKeyPressed(evt);
            }
        });
        PanelInput.add(VolumeSampel);
        VolumeSampel.setBounds(104, 160, 100, 23);

        WadahSampel.setHighlighter(null);
        WadahSampel.setName("WadahSampel"); // NOI18N
        WadahSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WadahSampelKeyPressed(evt);
            }
        });
        PanelInput.add(WadahSampel);
        WadahSampel.setBounds(268, 160, 130, 23);

        jLabel13.setText("Wadah :");
        jLabel13.setName("jLabel13"); // NOI18N
        PanelInput.add(jLabel13);
        jLabel13.setBounds(204, 160, 60, 23);

        KondisiWadah.setHighlighter(null);
        KondisiWadah.setName("KondisiWadah"); // NOI18N
        KondisiWadah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiWadahKeyPressed(evt);
            }
        });
        PanelInput.add(KondisiWadah);
        KondisiWadah.setBounds(500, 160, 180, 23);

        jLabel14.setText("Kondisi Wadah :");
        jLabel14.setName("jLabel14"); // NOI18N
        PanelInput.add(jLabel14);
        jLabel14.setBounds(406, 160, 90, 23);

        jLabel17.setText("Sampling Dilakukan Oleh :");
        jLabel17.setName("jLabel17"); // NOI18N
        PanelInput.add(jLabel17);
        jLabel17.setBounds(396, 130, 140, 23);

        DisamplingOleh.setHighlighter(null);
        DisamplingOleh.setName("DisamplingOleh"); // NOI18N
        DisamplingOleh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DisamplingOlehKeyPressed(evt);
            }
        });
        PanelInput.add(DisamplingOleh);
        DisamplingOleh.setBounds(540, 130, 140, 23);

        jLabel18.setText("Kode Sampel :");
        jLabel18.setName("jLabel18"); // NOI18N
        PanelInput.add(jLabel18);
        jLabel18.setBounds(0, 190, 100, 23);

        KodeSampel.setEditable(false);
        KodeSampel.setHighlighter(null);
        KodeSampel.setName("KodeSampel"); // NOI18N
        KodeSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeSampelKeyPressed(evt);
            }
        });
        PanelInput.add(KodeSampel);
        KodeSampel.setBounds(104, 190, 65, 23);

        NamaSampel.setEditable(false);
        NamaSampel.setHighlighter(null);
        NamaSampel.setName("NamaSampel"); // NOI18N
        PanelInput.add(NamaSampel);
        NamaSampel.setBounds(171, 190, 170, 23);

        BtnSampel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSampel.setMnemonic('4');
        BtnSampel.setToolTipText("ALt+4");
        BtnSampel.setName("BtnSampel"); // NOI18N
        BtnSampel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSampelActionPerformed(evt);
            }
        });
        BtnSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSampelKeyPressed(evt);
            }
        });
        PanelInput.add(BtnSampel);
        BtnSampel.setBounds(652, 190, 28, 23);

        BakuMutu.setEditable(false);
        BakuMutu.setHighlighter(null);
        BakuMutu.setName("BakuMutu"); // NOI18N
        PanelInput.add(BakuMutu);
        BakuMutu.setBounds(343, 190, 307, 23);

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
    form.setSize(this.getWidth(),this.getHeight());
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
            Valid.textKosong(BtnSampel,"Kode Sampel");
        }else{
            tampil2();
        }
    }//GEN-LAST:event_btnCariPeriksaActionPerformed

    private void btnCariPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariPeriksaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            if(KodeSampel.getText().trim().equals("")||NamaSampel.getText().trim().equals("")){
                Valid.textKosong(BtnSampel,"Kode Sampel");
            }else{
                tampil2();
            }
        }else{
            Valid.pindah(evt, TCariPeriksa, BtnAllPeriksa);
        }
    }//GEN-LAST:event_btnCariPeriksaKeyPressed

    private void BtnAllPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPeriksaActionPerformed
        if(KodeSampel.getText().trim().equals("")||NamaSampel.getText().trim().equals("")){
            Valid.textKosong(BtnSampel,"Kode Sampel");
        }else{
            TCariPeriksa.setText("");
            tampil();
            tampil2();
        }
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
        }
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
        for(i=0;i<tbPemeriksaan.getRowCount();i++){
            if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodePerujuk.getText().equals("")||NmPerujuk.getText().equals("")){
            Valid.textKosong(KodePerujuk,"Dokter Pengirim");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(TCariPeriksa,"Data Permintaan");
        }else if(jml==0){
            Valid.textKosong(TCariPeriksa,"Data Permintaan");
        }else{
            
            Sequel.queryu("delete from temporary_laborat_kesling_permintaan_pengujian_sampel");
            for(i=0;i<tbPemeriksaan.getRowCount();i++){ 
                if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                    Sequel.menyimpan("temporary_laborat_kesling_permintaan_pengujian_sampel","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                        "0",tbPemeriksaan.getValueAt(i,1).toString(),
                        tbPemeriksaan.getValueAt(i,2).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                    });
                }                
            }
            
            Map<String, Object> param = new HashMap<>();
            param.put("noperiksa",TNoPermintaan.getText());
            param.put("norm",TNoRM.getText());
            param.put("pekerjaan",Sequel.cariIsi("select pasien.pekerjaan from pasien where pasien.no_rkm_medis=?",TNoRM.getText()));
            param.put("noktp",Sequel.cariIsi("select pasien.no_ktp from pasien where pasien.no_rkm_medis=?",TNoRM.getText()));
            param.put("namapasien",TPasien.getText());
            param.put("jkel",Jk.getText());
            param.put("umur",Umur.getText());
            param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TNoRM.getText()));
            param.put("pengirim",NmPerujuk.getText());
            param.put("tanggal",Tanggal.getSelectedItem());
            param.put("alamat",Alamat.getText());
            param.put("kamar",kamar);
            param.put("namakamar",namakamar);
            param.put("informasitambahan",InformasiTambahan.getText());
            param.put("diagnosa",DiagnosisKlinis.getText());
            param.put("jam",CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KodePerujuk.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmPerujuk.getText()+"\nID "+(finger.equals("")?KodePerujuk.getText():finger)+"\n"+Tanggal.getSelectedItem()); 
            
            Valid.MyReport("rptPermintaanRadiologi.jasper","report","::[ Permintaan Radiologi ]::",param);            
            ChkJln.setSelected(false);
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
        Valid.pindah(evt,TCariPeriksa,btnPelanggan);
    }//GEN-LAST:event_TNoPermintaanKeyPressed

    private void LokasiSamplingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiSamplingKeyPressed
        Valid.pindah(evt,KodePelanggan,DeskripsiSampling);
    }//GEN-LAST:event_LokasiSamplingKeyPressed

    private void DeskripsiSamplingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DeskripsiSamplingKeyPressed
        Valid.pindah(evt,LokasiSampling,JenisSampel);
    }//GEN-LAST:event_DeskripsiSamplingKeyPressed

    private void WaktuDiterimaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_WaktuDiterimaItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_WaktuDiterimaItemStateChanged

    private void btnPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPelangganActionPerformed
        LabKeslingPelanggan pelanggan=new LabKeslingPelanggan(null,false);
        pelanggan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pelanggan.getTable().getSelectedRow()!= -1){
                    KodePelanggan.setText(pelanggan.getTable().getValueAt(pelanggan.getTable().getSelectedRow(),0).toString());
                    NamaPelanggan.setText(pelanggan.getTable().getValueAt(pelanggan.getTable().getSelectedRow(),1).toString());
                    AlamatPelanggan.setText(pelanggan.getTable().getValueAt(pelanggan.getTable().getSelectedRow(),2).toString());
                }  
                LokasiSampling.requestFocus();
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
        
        pelanggan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pelanggan.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        pelanggan.isCek();
        pelanggan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pelanggan.setLocationRelativeTo(internalFrame1);
        pelanggan.setVisible(true);
    }//GEN-LAST:event_btnPelangganActionPerformed

    private void JenisSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisSampelKeyPressed
        Valid.pindah(evt,DeskripsiSampling,JmlSampel);
    }//GEN-LAST:event_JenisSampelKeyPressed

    private void JmlSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JmlSampelKeyPressed
        Valid.pindah(evt,JenisSampel,DisamplingOleh);
    }//GEN-LAST:event_JmlSampelKeyPressed

    private void VolumeSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VolumeSampelKeyPressed
        Valid.pindah(evt,DisamplingOleh,WadahSampel);
    }//GEN-LAST:event_VolumeSampelKeyPressed

    private void WadahSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WadahSampelKeyPressed
        Valid.pindah(evt,VolumeSampel,KondisiWadah);
    }//GEN-LAST:event_WadahSampelKeyPressed

    private void KondisiWadahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiWadahKeyPressed
        Valid.pindah(evt,WadahSampel,KondisiWadah);
    }//GEN-LAST:event_KondisiWadahKeyPressed

    private void DisamplingOlehKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DisamplingOlehKeyPressed
        Valid.pindah(evt,JmlSampel,VolumeSampel);
    }//GEN-LAST:event_DisamplingOlehKeyPressed

    private void BtnSampelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSampelActionPerformed
        LabKeslingCariMasterSampelBakuMutu sampel=new LabKeslingCariMasterSampelBakuMutu(null,false);
        sampel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(sampel.getTable().getSelectedRow()!= -1){
                    KodeSampel.setText(sampel.getTable().getValueAt(sampel.getTable().getSelectedRow(),0).toString());
                    NamaSampel.setText(sampel.getTable().getValueAt(sampel.getTable().getSelectedRow(),1).toString());
                    BakuMutu.setText(sampel.getTable().getValueAt(sampel.getTable().getSelectedRow(),2).toString());
                    tampil2();
                }  
                BtnSampel.requestFocus();
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
        
        sampel.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    sampel.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        sampel.isCek();
        sampel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        sampel.setLocationRelativeTo(internalFrame1);
        sampel.setVisible(true);
    }//GEN-LAST:event_BtnSampelActionPerformed

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

    private void btnPelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPelangganKeyPressed
        Valid.pindah(evt,TNoPermintaan,LokasiSampling);
    }//GEN-LAST:event_btnPelangganKeyPressed

    private void KodePelangganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePelangganKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPelangganActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoPermintaan,LokasiSampling);
        }
    }//GEN-LAST:event_KodePelangganKeyPressed

    private void KodeSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeSampelKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSampelActionPerformed(null);
        }else{            
            Valid.pindah(evt,KondisiWadah,BtnSimpan);
        }
    }//GEN-LAST:event_KodeSampelKeyPressed

    private void BtnSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSampelKeyPressed
        Valid.pindah(evt,KondisiWadah,BtnSimpan);
    }//GEN-LAST:event_BtnSampelKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingPermintaanPengujianSampel dialog = new LabKeslingPermintaanPengujianSampel(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSampel;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox DeskripsiSampling;
    private widget.TextBox DisamplingOleh;
    private javax.swing.JPanel FormInput;
    private widget.TextBox JenisSampel;
    private widget.TextBox JmlSampel;
    private widget.ComboBox KategoriParameter;
    private widget.TextBox KdPetugas;
    private widget.TextBox KodePelanggan;
    private widget.TextBox KodeSampel;
    private widget.TextBox KondisiWadah;
    private widget.Label LCount;
    private widget.TextBox LokasiSampling;
    private widget.TextBox NamaPelanggan;
    private widget.TextBox NamaSampel;
    private widget.TextBox NmPetugas;
    private widget.PanelBiasa PanelInput;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCariPeriksa;
    private widget.TextBox TNoPermintaan;
    private widget.TextBox VolumeSampel;
    private widget.TextBox WadahSampel;
    private widget.Tanggal WaktuDiterima;
    private widget.Tanggal WaktuSampling;
    private widget.Button btnCariPeriksa;
    private widget.Button btnPelanggan;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
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
                "from laborat_kesling_parameter_pengujian inner join laborat_kesling_nilai_normal_baku_mutu on laborat_kesling_nilai_normal_baku_mutu.kode_parameter=laborat_kesling_parameter_pengujian.kode_parameter order by laborat_kesling_nilai_normal_baku_mutu.kode_sampel"
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
        LokasiSampling.setText("");
        DeskripsiSampling.setText("");
        JenisSampel.setText("");
        JmlSampel.setText("");
        DisamplingOleh.setText("");
        VolumeSampel.setText("");
        WadahSampel.setText("");
        KondisiWadah.setText("");
        KodeSampel.setText("");
        NamaSampel.setText("");
        BakuMutu.setText("");
        autoNomor();
        LokasiSampling.requestFocus();
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
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(laborat_kesling_permintaan_pengujian_sampel.no_permintaan,4),signed)),0) from laborat_kesling_permintaan_pengujian_sampel where date_format(laborat_kesling_permintaan_pengujian_sampel.waktu_diterima,'%Y-%m-%d')='"+Valid.SetTgl(WaktuDiterima.getSelectedItem()+"")+"' ","PSL"+Valid.SetTgl(WaktuDiterima.getSelectedItem()+"").replaceAll("-",""),4,TNoPermintaan);           
    }

}

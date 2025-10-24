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

import permintaan.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kepegawaian.DlgCariDokter;
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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class LabKeslingPermintaanPengujianSampel extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psset_tarif,pspemeriksaan;
    private ResultSet rs,rsset_tarif;
    private boolean[] pilih; 
    private String[] kode,nama;
    private int jml=0,i=0,index=0,jmlparsial=0;
    private String kelas_radiologi="Yes",kelas="",cara_bayar_radiologi="Yes",kamar,namakamar,status="",
            norawatibu="",aktifkanparsial="no",finger="";
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public LabKeslingPermintaanPengujianSampel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Object[] row={"P","Kode Periksa","Nama Pemeriksaan"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPemeriksaan.setModel(tabMode);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 3; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(130);
            }else if(i==2){
                column.setPreferredWidth(480);
            }
        }
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KodePerujuk.setDocument(new batasInput((byte)20).getKata(KodePerujuk));
        TNoPermintaan.setDocument(new batasInput((byte)15).getKata(TNoPermintaan));
        InformasiTambahan.setDocument(new batasInput((int)60).getKata(InformasiTambahan));
        DiagnosisKlinis.setDocument(new batasInput((int)80).getKata(DiagnosisKlinis));
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
        jLabel10 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        PanelInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel9 = new widget.Label();
        Tanggal = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        KodePerujuk = new widget.TextBox();
        NmPerujuk = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel15 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoPermintaan = new widget.TextBox();
        InformasiTambahan = new widget.TextBox();
        jLabel5 = new widget.Label();
        DiagnosisKlinis = new widget.TextBox();
        jLabel6 = new widget.Label();
        btnDokter1 = new widget.Button();
        jLabel7 = new widget.Label();
        jLabel16 = new widget.Label();
        Tanggal1 = new widget.Tanggal();
        jLabel8 = new widget.Label();
        DiagnosisKlinis1 = new widget.TextBox();
        DiagnosisKlinis2 = new widget.TextBox();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        DiagnosisKlinis3 = new widget.TextBox();
        DiagnosisKlinis4 = new widget.TextBox();
        jLabel13 = new widget.Label();
        DiagnosisKlinis5 = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel17 = new widget.Label();
        DiagnosisKlinis6 = new widget.TextBox();
        jLabel18 = new widget.Label();
        TNoRw1 = new widget.TextBox();
        TNoRM1 = new widget.TextBox();
        btnDokter2 = new widget.Button();
        TNoRM2 = new widget.TextBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelisi5 = new widget.panelisi();
        jLabel19 = new widget.Label();
        Kategori = new widget.ComboBox();
        label10 = new widget.Label();
        TCariPeriksa = new widget.TextBox();
        btnCariPeriksa = new widget.Button();
        BtnAllPeriksa = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

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

        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(153, 30));
        panelGlass8.add(jLabel10);

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

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        PanelInput.add(TNoRw);
        TNoRw.setBounds(104, 70, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        PanelInput.add(TNoRM);
        TNoRM.setBounds(196, 70, 210, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        PanelInput.add(TPasien);
        TPasien.setBounds(408, 70, 242, 23);

        jLabel9.setText("Sampel Diterima :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(0, 10, 100, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-10-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalItemStateChanged(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        PanelInput.add(Tanggal);
        Tanggal.setBounds(104, 40, 90, 23);

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

        KodePerujuk.setName("KodePerujuk"); // NOI18N
        KodePerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePerujukKeyPressed(evt);
            }
        });
        PanelInput.add(KodePerujuk);
        KodePerujuk.setBounds(104, 10, 105, 23);

        NmPerujuk.setEditable(false);
        NmPerujuk.setHighlighter(null);
        NmPerujuk.setName("NmPerujuk"); // NOI18N
        PanelInput.add(NmPerujuk);
        NmPerujuk.setBounds(211, 10, 200, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('4');
        btnDokter.setToolTipText("ALt+4");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        PanelInput.add(btnDokter);
        btnDokter.setBounds(413, 10, 28, 23);

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

        InformasiTambahan.setHighlighter(null);
        InformasiTambahan.setName("InformasiTambahan"); // NOI18N
        InformasiTambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiTambahanKeyPressed(evt);
            }
        });
        PanelInput.add(InformasiTambahan);
        InformasiTambahan.setBounds(104, 100, 230, 23);

        jLabel5.setText("Lokasi Sampling :");
        jLabel5.setName("jLabel5"); // NOI18N
        PanelInput.add(jLabel5);
        jLabel5.setBounds(0, 100, 100, 23);

        DiagnosisKlinis.setHighlighter(null);
        DiagnosisKlinis.setName("DiagnosisKlinis"); // NOI18N
        DiagnosisKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKlinisKeyPressed(evt);
            }
        });
        PanelInput.add(DiagnosisKlinis);
        DiagnosisKlinis.setBounds(450, 100, 230, 23);

        jLabel6.setText("Deskripsi Sampel :");
        jLabel6.setName("jLabel6"); // NOI18N
        PanelInput.add(jLabel6);
        jLabel6.setBounds(341, 100, 105, 23);

        btnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter1.setMnemonic('4');
        btnDokter1.setToolTipText("ALt+4");
        btnDokter1.setName("btnDokter1"); // NOI18N
        btnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokter1ActionPerformed(evt);
            }
        });
        PanelInput.add(btnDokter1);
        btnDokter1.setBounds(652, 70, 28, 23);

        jLabel7.setText("Pelanggan :");
        jLabel7.setName("jLabel7"); // NOI18N
        PanelInput.add(jLabel7);
        jLabel7.setBounds(0, 70, 100, 23);

        jLabel16.setText("Waktu Sampling :");
        jLabel16.setName("jLabel16"); // NOI18N
        PanelInput.add(jLabel16);
        jLabel16.setBounds(446, 10, 100, 23);

        Tanggal1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2025 13:10:25" }));
        Tanggal1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.setOpaque(false);
        Tanggal1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Tanggal1ItemStateChanged(evt);
            }
        });
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        PanelInput.add(Tanggal1);
        Tanggal1.setBounds(550, 10, 130, 23);

        jLabel8.setText("Jenis Sampel :");
        jLabel8.setName("jLabel8"); // NOI18N
        PanelInput.add(jLabel8);
        jLabel8.setBounds(0, 130, 100, 23);

        DiagnosisKlinis1.setHighlighter(null);
        DiagnosisKlinis1.setName("DiagnosisKlinis1"); // NOI18N
        DiagnosisKlinis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKlinis1KeyPressed(evt);
            }
        });
        PanelInput.add(DiagnosisKlinis1);
        DiagnosisKlinis1.setBounds(104, 130, 140, 23);

        DiagnosisKlinis2.setHighlighter(null);
        DiagnosisKlinis2.setName("DiagnosisKlinis2"); // NOI18N
        DiagnosisKlinis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKlinis2KeyPressed(evt);
            }
        });
        PanelInput.add(DiagnosisKlinis2);
        DiagnosisKlinis2.setBounds(330, 130, 60, 23);

        jLabel11.setText("Jml Sampel :");
        jLabel11.setName("jLabel11"); // NOI18N
        PanelInput.add(jLabel11);
        jLabel11.setBounds(246, 130, 80, 23);

        jLabel12.setText("Volume Sampel :");
        jLabel12.setName("jLabel12"); // NOI18N
        PanelInput.add(jLabel12);
        jLabel12.setBounds(0, 160, 100, 23);

        DiagnosisKlinis3.setHighlighter(null);
        DiagnosisKlinis3.setName("DiagnosisKlinis3"); // NOI18N
        DiagnosisKlinis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKlinis3KeyPressed(evt);
            }
        });
        PanelInput.add(DiagnosisKlinis3);
        DiagnosisKlinis3.setBounds(104, 160, 100, 23);

        DiagnosisKlinis4.setHighlighter(null);
        DiagnosisKlinis4.setName("DiagnosisKlinis4"); // NOI18N
        DiagnosisKlinis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKlinis4KeyPressed(evt);
            }
        });
        PanelInput.add(DiagnosisKlinis4);
        DiagnosisKlinis4.setBounds(268, 160, 130, 23);

        jLabel13.setText("Wadah :");
        jLabel13.setName("jLabel13"); // NOI18N
        PanelInput.add(jLabel13);
        jLabel13.setBounds(204, 160, 60, 23);

        DiagnosisKlinis5.setHighlighter(null);
        DiagnosisKlinis5.setName("DiagnosisKlinis5"); // NOI18N
        DiagnosisKlinis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKlinis5KeyPressed(evt);
            }
        });
        PanelInput.add(DiagnosisKlinis5);
        DiagnosisKlinis5.setBounds(500, 160, 180, 23);

        jLabel14.setText("Kondisi Wadah :");
        jLabel14.setName("jLabel14"); // NOI18N
        PanelInput.add(jLabel14);
        jLabel14.setBounds(406, 160, 90, 23);

        jLabel17.setText("Sampling Dilakukan Oleh :");
        jLabel17.setName("jLabel17"); // NOI18N
        PanelInput.add(jLabel17);
        jLabel17.setBounds(396, 130, 140, 23);

        DiagnosisKlinis6.setHighlighter(null);
        DiagnosisKlinis6.setName("DiagnosisKlinis6"); // NOI18N
        DiagnosisKlinis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKlinis6KeyPressed(evt);
            }
        });
        PanelInput.add(DiagnosisKlinis6);
        DiagnosisKlinis6.setBounds(540, 130, 140, 23);

        jLabel18.setText("Kode Sampel :");
        jLabel18.setName("jLabel18"); // NOI18N
        PanelInput.add(jLabel18);
        jLabel18.setBounds(0, 190, 100, 23);

        TNoRw1.setEditable(false);
        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        PanelInput.add(TNoRw1);
        TNoRw1.setBounds(104, 190, 65, 23);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        PanelInput.add(TNoRM1);
        TNoRM1.setBounds(171, 190, 190, 23);

        btnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter2.setMnemonic('4');
        btnDokter2.setToolTipText("ALt+4");
        btnDokter2.setName("btnDokter2"); // NOI18N
        btnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokter2ActionPerformed(evt);
            }
        });
        PanelInput.add(btnDokter2);
        btnDokter2.setBounds(652, 190, 28, 23);

        TNoRM2.setEditable(false);
        TNoRM2.setHighlighter(null);
        TNoRM2.setName("TNoRM2"); // NOI18N
        PanelInput.add(TNoRM2);
        TNoRM2.setBounds(363, 190, 287, 23);

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

        Kategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Fisika", "Kimia", "Mikrobiologi" }));
        Kategori.setName("Kategori"); // NOI18N
        Kategori.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi5.add(Kategori);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi5.add(label10);

        TCariPeriksa.setToolTipText("Alt+C");
        TCariPeriksa.setName("TCariPeriksa"); // NOI18N
        TCariPeriksa.setPreferredSize(new java.awt.Dimension(313, 23));
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

        jPanel3.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        tbPemeriksaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanMouseClicked(evt);
            }
        });
        tbPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPemeriksaanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbPemeriksaan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
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
    DlgCariPermintaanRadiologi form=new DlgCariPermintaanRadiologi(null,false);
    form.isCek();
    form.setPasien(TNoRw.getText());
    form.setSize(this.getWidth(),this.getHeight());
    form.setLocationRelativeTo(this);
    form.setVisible(true);
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, KodePerujuk, TCariPeriksa);
    }//GEN-LAST:event_TanggalKeyPressed

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
        tampil2();
    }//GEN-LAST:event_btnCariPeriksaActionPerformed

    private void btnCariPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariPeriksaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil2();
        }else{
            Valid.pindah(evt, TCariPeriksa, BtnAllPeriksa);
        }
    }//GEN-LAST:event_btnCariPeriksaKeyPressed

    private void BtnAllPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPeriksaActionPerformed
        TCariPeriksa.setText("");
        tampil();
        tampil2();
    }//GEN-LAST:event_BtnAllPeriksaActionPerformed

    private void BtnAllPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllPeriksaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllPeriksaActionPerformed(null);
        }else{
            Valid.pindah(evt, btnCariPeriksa, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllPeriksaKeyPressed

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
               // getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPemeriksaanMouseClicked

    private void tbPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyPressed
        if(tbPemeriksaan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    int row=tbPemeriksaan.getSelectedColumn();
                    if((row!=0)||(row!=20)){
                        if(tbPemeriksaan.getSelectedRow()>-1){
                            tbPemeriksaan.setValueAt(true,tbPemeriksaan.getSelectedRow(),0);
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
    }//GEN-LAST:event_tbPemeriksaanKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt, DiagnosisKlinis,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        /*jml=0;
        for(i=0;i<tbPemeriksaan.getRowCount();i++){
            if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodePerujuk.getText().equals("")||NmPerujuk.getText().equals("")){
            Valid.textKosong(KodePerujuk,"Dokter Perujuk");
        }else if(InformasiTambahan.getText().equals("")){
            Valid.textKosong(InformasiTambahan,"Informasi Tambahan");
        }else if(DiagnosisKlinis.getText().equals("")){
            Valid.textKosong(DiagnosisKlinis,"Diagnosis Klinis");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(TCariPeriksa,"Data Permintaan");
        }else if(jml==0){
            Valid.textKosong(TCariPeriksa,"Data Permintaan");
        }else{
            jmlparsial=0;
            if(aktifkanparsial.equals("yes")){
                jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",Penjab.getText());
            }
            if(jmlparsial>0){    
                simpan(); 
            }else{
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCariPeriksa.requestFocus();
                }else{
                    simpan();              
                }
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

    private void KodePerujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePerujukKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{            
            Valid.pindah(evt,TCariPeriksa,Tanggal);
        }
    }//GEN-LAST:event_KodePerujukKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        DlgCariDokter dokter=new DlgCariDokter(null,false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KodePerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmPerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KodePerujuk.requestFocus();
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
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

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
            
            Sequel.queryu("delete from temporary_permintaan_radiologi");
            for(i=0;i<tbPemeriksaan.getRowCount();i++){ 
                if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                    Sequel.menyimpan("temporary_permintaan_radiologi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
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
        Valid.pindah(evt,TCariPeriksa,InformasiTambahan);
    }//GEN-LAST:event_TNoPermintaanKeyPressed

    private void InformasiTambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiTambahanKeyPressed
        Valid.pindah(evt,TNoPermintaan,DiagnosisKlinis);
    }//GEN-LAST:event_InformasiTambahanKeyPressed

    private void DiagnosisKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKlinisKeyPressed
        Valid.pindah(evt,InformasiTambahan,BtnSimpan);
    }//GEN-LAST:event_DiagnosisKlinisKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalItemStateChanged

    private void btnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDokter1ActionPerformed

    private void Tanggal1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Tanggal1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal1ItemStateChanged

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void DiagnosisKlinis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKlinis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosisKlinis1KeyPressed

    private void DiagnosisKlinis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKlinis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosisKlinis2KeyPressed

    private void DiagnosisKlinis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKlinis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosisKlinis3KeyPressed

    private void DiagnosisKlinis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKlinis4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosisKlinis4KeyPressed

    private void DiagnosisKlinis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKlinis5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosisKlinis5KeyPressed

    private void DiagnosisKlinis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKlinis6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosisKlinis6KeyPressed

    private void btnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDokter2ActionPerformed

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
    private widget.Button BtnAllPeriksa;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox DiagnosisKlinis;
    private widget.TextBox DiagnosisKlinis1;
    private widget.TextBox DiagnosisKlinis2;
    private widget.TextBox DiagnosisKlinis3;
    private widget.TextBox DiagnosisKlinis4;
    private widget.TextBox DiagnosisKlinis5;
    private widget.TextBox DiagnosisKlinis6;
    private javax.swing.JPanel FormInput;
    private widget.TextBox InformasiTambahan;
    private widget.ComboBox Kategori;
    private widget.TextBox KodePerujuk;
    private widget.TextBox NmPerujuk;
    private widget.PanelBiasa PanelInput;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCariPeriksa;
    private widget.TextBox TNoPermintaan;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRM2;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw1;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tanggal1;
    private widget.Button btnCariPeriksa;
    private widget.Button btnDokter;
    private widget.Button btnDokter1;
    private widget.Button btnDokter2;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private widget.Label label10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi5;
    private widget.ScrollPane scrollInput;
    private widget.Table tbPemeriksaan;
    // End of variables declaration//GEN-END:variables
    
    
    private void tampil() {         
        /*try{
            file=new File("./cache/permintaanradiologi.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
        
            pspemeriksaan=koneksi.prepareStatement(
                    "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.kd_pj,jns_perawatan_radiologi.kelas from jns_perawatan_radiologi where jns_perawatan_radiologi.status='1' order by jns_perawatan_radiologi.kd_jenis_prw");
            try {
                rs=pspemeriksaan.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"KodePeriksa\":\"").append(rs.getString(1)).append("\",\"NamaPemeriksaan\":\"").append(rs.getString(2).replaceAll("\"","")).append("\",\"KodePJ\":\"").append(rs.getString(3)).append("\",\"Kelas\":\"").append(rs.getString(4)).append("\"},");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pspemeriksaan!=null){
                    pspemeriksaan.close();
                }
            }
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"permintaanradiologi\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }*/
    }
    
    private void tampil2() {         
       /* try{
            jml=0;
            for(i=0;i<tbPemeriksaan.getRowCount();i++){
                if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml];
            kode=new String[jml];
            nama=new String[jml];
            
            index=0; 
            for(i=0;i<tbPemeriksaan.getRowCount();i++){
                if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPemeriksaan.getValueAt(i,1).toString();
                    nama[index]=tbPemeriksaan.getValueAt(i,2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabMode);
            for(i=0;i<jml;i++){                
                tabMode.addRow(new Object[] {pilih[i],kode[i],nama[i]});
            }  
            
            pilih=null;
            kode=null;
            nama=null;
        
            myObj = new FileReader("./cache/permintaanradiologi.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanradiologi");
            if(cara_bayar_radiologi.equals("Yes")&&kelas_radiologi.equals("No")){
                if(response.isArray()){
                    if(TCariPeriksa.getText().trim().equals("")){
                        for(JsonNode list:response){
                            if((list.path("KodePJ").asText().equals(Penjab.getText())||list.path("KodePJ").asText().equals("-"))){
                                tabMode.addRow(new Object[]{
                                    false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                                });
                            }
                        }
                    }else{
                        for(JsonNode list:response){
                            if((list.path("KodePJ").asText().equals(Penjab.getText())||list.path("KodePJ").asText().equals("-"))&&(list.path("KodePeriksa").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase()))){
                                tabMode.addRow(new Object[]{
                                    false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                                });
                            }
                        }
                    }
                }
            }else if(cara_bayar_radiologi.equals("No")&&kelas_radiologi.equals("No")){
                if(response.isArray()){
                    if(TCariPeriksa.getText().trim().equals("")){
                        for(JsonNode list:response){
                            tabMode.addRow(new Object[]{
                                false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                            });
                        }
                    }else{
                        for(JsonNode list:response){
                            if(list.path("KodePeriksa").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase())){
                                tabMode.addRow(new Object[]{
                                    false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                                });
                            }
                        }
                    }
                }
            }else if(cara_bayar_radiologi.equals("Yes")&&kelas_radiologi.equals("Yes")){
                if(response.isArray()){
                    if(TCariPeriksa.getText().trim().equals("")){
                        for(JsonNode list:response){
                            if((list.path("Kelas").asText().equals(kelas.trim())||list.path("Kelas").asText().equals("-"))&&(list.path("KodePJ").asText().equals(Penjab.getText())||list.path("KodePJ").asText().equals("-"))){
                                tabMode.addRow(new Object[]{
                                    false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                                });
                            }
                        }
                    }else{
                        for(JsonNode list:response){
                            if((list.path("Kelas").asText().equals(kelas.trim())||list.path("Kelas").asText().equals("-"))&&(list.path("KodePJ").asText().equals(Penjab.getText())||list.path("KodePJ").asText().equals("-"))&&(list.path("KodePeriksa").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase()))){
                                tabMode.addRow(new Object[]{
                                    false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                                });
                            }
                        }
                    }
                }
            }else if(cara_bayar_radiologi.equals("No")&&kelas_radiologi.equals("Yes")){
                if(response.isArray()){
                    if(TCariPeriksa.getText().trim().equals("")){
                        for(JsonNode list:response){
                            if(list.path("Kelas").asText().equals(kelas.trim())||list.path("Kelas").asText().equals("-")){
                                tabMode.addRow(new Object[]{
                                    false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                                });
                            }
                        }
                    }else{
                        for(JsonNode list:response){
                            if((list.path("Kelas").asText().equals(kelas.trim())||list.path("Kelas").asText().equals("-"))&&(list.path("KodePeriksa").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(TCariPeriksa.getText().toLowerCase()))){
                                tabMode.addRow(new Object[]{
                                    false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                                });
                            }
                        }
                    }
                }
            }   
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }*/
    }
    
    public void isReset(){
        jml=tbPemeriksaan.getRowCount();
        for(i=0;i<jml;i++){ 
            tbPemeriksaan.setValueAt(false,i,0);
        }
        Valid.tabelKosong(tabMode);
    }
    
    public void emptTeks() {
        TCariPeriksa.setText("");
        TNoPermintaan.requestFocus();
        autoNomor();
    }
    
    private void isRawat(){
        if(status.equals("Ranap")){
            norawatibu=Sequel.cariIsi("select ranap_gabung.no_rawat from ranap_gabung where ranap_gabung.no_rawat2=?",TNoRw.getText());
            if(!norawatibu.equals("")){
                kamar=Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1",norawatibu);
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where kamar_inap.no_rawat=? "+
                    "and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norawatibu);
            }else{
                kamar=Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1",TNoRw.getText());
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where kamar_inap.no_rawat=? "+
                    "and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
            } 
            namakamar=kamar+", "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                    " where kamar.kd_kamar=? ",kamar);            
            kamar="Kamar";
        }else if(status.equals("Ralan")){
            kelas="Rawat Jalan";
            kamar="Poli";
            namakamar=Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                    "where reg_periksa.no_rawat=?",TNoRw.getText());
        }
    }

    private void isPsien(){
        /*try {
            pspemeriksaan=koneksi.prepareStatement(
                "select reg_periksa.no_rkm_medis,reg_periksa.kd_pj,reg_periksa.kd_dokter,dokter.nm_dokter,pasien.nm_pasien,pasien.jk,pasien.umur,"+
                "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where no_rawat=?");
            try {
                pspemeriksaan.setString(1,TNoRw.getText());
                rs=pspemeriksaan.executeQuery();
                while(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    Penjab.setText(rs.getString("kd_pj"));
                    KodePerujuk.setText(rs.getString("kd_dokter"));
                    NmPerujuk.setText(rs.getString("nm_dokter"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umur"));
                    Alamat.setText(rs.getString("alamat"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pspemeriksaan!=null){
                    pspemeriksaan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }*/
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

    public void setNoRm(String norwt,String posisi){
        TNoRw.setText(norwt);
        this.status=posisi;        
        isRawat();
        isPsien();
        try {
            if(Valid.daysOld("./cache/permintaanradiologi.iyem")<3){
                tampil2();
            }
        } catch (Exception e) {
        }
    }
    
    public void setNoRm(String norwt,String posisi,String kddokter,String nmdokter) {
        TNoRw.setText(norwt);
        this.status=posisi;
        isRawat();
        isPsien();
        try {
            if(Valid.daysOld("./cache/permintaanradiologi.iyem")<3){
                tampil2();
            }
        } catch (Exception e) {
        }
        KodePerujuk.setText(kddokter);
        NmPerujuk.setText(nmdokter);
    }
    
    public void isCek(){        
        BtnSimpan.setEnabled(akses.getpermintaan_radiologi());
        BtnPrint.setEnabled(akses.getpermintaan_radiologi());
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
    
    public void setDokterPerujuk(String kodeperujuk,String namaperujuk){
        KodePerujuk.setText(kodeperujuk);
        NmPerujuk.setText(namaperujuk);
    }

    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(permintaan_radiologi.noorder,4),signed)),0) from permintaan_radiologi where permintaan_radiologi.tgl_permintaan='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ","PR"+Valid.SetTgl(Tanggal.getSelectedItem()+"").replaceAll("-",""),4,TNoPermintaan);           
    }

    private void simpan() {
        int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            ChkJln.setSelected(false);
            try {                    
                koneksi.setAutoCommit(false);
                //autoNomor();
                if(Sequel.menyimpantf2("permintaan_radiologi","?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",12,new String[]{
                        TNoPermintaan.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),
                        CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(), 
                        "0000-00-00","00:00:00","0000-00-00","00:00:00",KodePerujuk.getText(),status.replaceAll("R","r"),
                        InformasiTambahan.getText(),DiagnosisKlinis.getText()
                    })==true){
                    for(i=0;i<tbPemeriksaan.getRowCount();i++){ 
                        if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                            Sequel.menyimpan2("permintaan_pemeriksaan_radiologi","?,?,?","pemeriksaan radiologi",3,new String[]{
                                TNoPermintaan.getText(),tbPemeriksaan.getValueAt(i,1).toString(),"Belum"
                            });
                        }                        
                    } 
                    isReset();
                    emptTeks();
                }else{
                    autoNomor();
                    if(Sequel.menyimpantf2("permintaan_radiologi","?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",12,new String[]{
                            TNoPermintaan.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),
                            CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(), 
                            "0000-00-00","00:00:00","0000-00-00","00:00:00",KodePerujuk.getText(),status.replaceAll("R","r"),
                            InformasiTambahan.getText(),DiagnosisKlinis.getText()
                        })==true){
                        for(i=0;i<tbPemeriksaan.getRowCount();i++){ 
                            if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                                Sequel.menyimpan2("permintaan_pemeriksaan_radiologi","?,?,?","pemeriksaan lab",3,new String[]{
                                    TNoPermintaan.getText(),tbPemeriksaan.getValueAt(i,1).toString(),"Belum"
                                });
                            }                        
                        } 
                        isReset();
                        emptTeks();
                    }else{
                        autoNomor();
                        if(Sequel.menyimpantf2("permintaan_radiologi","?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",12,new String[]{
                                TNoPermintaan.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),
                                CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(), 
                                "0000-00-00","00:00:00","0000-00-00","00:00:00",KodePerujuk.getText(),status.replaceAll("R","r"),
                                InformasiTambahan.getText(),DiagnosisKlinis.getText()
                            })==true){
                            for(i=0;i<tbPemeriksaan.getRowCount();i++){ 
                                if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                                    Sequel.menyimpan2("permintaan_pemeriksaan_radiologi","?,?,?","pemeriksaan lab",3,new String[]{
                                        TNoPermintaan.getText(),tbPemeriksaan.getValueAt(i,1).toString(),"Belum"
                                    });
                                }                        
                            } 
                            isReset();
                            emptTeks();
                        }else{
                            autoNomor();
                            if(Sequel.menyimpantf2("permintaan_radiologi","?,?,?,?,?,?,?,?,?,?,?,?","No.Permintaan",12,new String[]{
                                    TNoPermintaan.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),
                                    CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(), 
                                    "0000-00-00","00:00:00","0000-00-00","00:00:00",KodePerujuk.getText(),status.replaceAll("R","r"),
                                    InformasiTambahan.getText(),DiagnosisKlinis.getText()
                                })==true){
                                for(i=0;i<tbPemeriksaan.getRowCount();i++){ 
                                    if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                                        Sequel.menyimpan2("permintaan_pemeriksaan_radiologi","?,?,?","pemeriksaan lab",3,new String[]{
                                            TNoPermintaan.getText(),tbPemeriksaan.getValueAt(i,1).toString(),"Belum"
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

}

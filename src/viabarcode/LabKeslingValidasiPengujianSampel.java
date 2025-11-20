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
public final class LabKeslingValidasiPengujianSampel extends javax.swing.JDialog {
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
    public LabKeslingValidasiPengujianSampel(java.awt.Frame parent, boolean modal) {
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

        TNoValidasi.setDocument(new batasInput((byte)20).getKata(TNoValidasi));
        Catatan.setDocument(new batasInput((int)100).getKata(Catatan));
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){      
                    KdPJ.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPJ.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    KdPJ.requestFocus();  
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
        FormInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        PanelInput = new widget.PanelBiasa();
        KodePelanggan = new widget.TextBox();
        NamaPelanggan = new widget.TextBox();
        jLabel9 = new widget.Label();
        TanggalValidasi = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        KdPJ = new widget.TextBox();
        NmPJ = new widget.TextBox();
        btnPJ = new widget.Button();
        jLabel15 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoPermintaan = new widget.TextBox();
        jLabel7 = new widget.Label();
        jLabel18 = new widget.Label();
        KodeSampel = new widget.TextBox();
        NamaSampel = new widget.TextBox();
        jLabel5 = new widget.Label();
        TNoValidasi = new widget.TextBox();
        jLabel6 = new widget.Label();
        Catatan = new widget.TextBox();
        jLabel8 = new widget.Label();
        KodeVerifikator = new widget.TextBox();
        NamaVerifikator = new widget.TextBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        Scroll2 = new widget.ScrollPane();
        tbValidasi = new widget.Table();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnPrint = new widget.Button();
        label11 = new widget.Label();
        LCount = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Validasi Pengujian Sampel Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setOpaque(false);
        FormInput.setPreferredSize(new java.awt.Dimension(560, 158));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 103));
        PanelInput.setLayout(null);

        KodePelanggan.setEditable(false);
        KodePelanggan.setHighlighter(null);
        KodePelanggan.setName("KodePelanggan"); // NOI18N
        PanelInput.add(KodePelanggan);
        KodePelanggan.setBounds(84, 70, 80, 23);

        NamaPelanggan.setEditable(false);
        NamaPelanggan.setHighlighter(null);
        NamaPelanggan.setName("NamaPelanggan"); // NOI18N
        PanelInput.add(NamaPelanggan);
        NamaPelanggan.setBounds(166, 70, 206, 23);

        jLabel9.setText("P.J.Laborat :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(0, 10, 80, 23);

        TanggalValidasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-11-2025" }));
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
        TanggalValidasi.setBounds(84, 40, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        PanelInput.add(CmbJam);
        CmbJam.setBounds(178, 40, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        PanelInput.add(CmbMenit);
        CmbMenit.setBounds(244, 40, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        PanelInput.add(CmbDetik);
        CmbDetik.setBounds(310, 40, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        PanelInput.add(ChkJln);
        ChkJln.setBounds(376, 40, 23, 23);

        KdPJ.setEditable(false);
        KdPJ.setName("KdPJ"); // NOI18N
        KdPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPJKeyPressed(evt);
            }
        });
        PanelInput.add(KdPJ);
        KdPJ.setBounds(84, 10, 115, 23);

        NmPJ.setEditable(false);
        NmPJ.setHighlighter(null);
        NmPJ.setName("NmPJ"); // NOI18N
        PanelInput.add(NmPJ);
        NmPJ.setBounds(201, 10, 200, 23);

        btnPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPJ.setMnemonic('4');
        btnPJ.setToolTipText("ALt+4");
        btnPJ.setName("btnPJ"); // NOI18N
        btnPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPJActionPerformed(evt);
            }
        });
        PanelInput.add(btnPJ);
        btnPJ.setBounds(403, 10, 28, 23);

        jLabel15.setText("Tgl.Validasi :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(0, 40, 80, 23);

        jLabel4.setText("No.Permintaan :");
        jLabel4.setName("jLabel4"); // NOI18N
        PanelInput.add(jLabel4);
        jLabel4.setBounds(446, 40, 90, 23);

        TNoPermintaan.setEditable(false);
        TNoPermintaan.setHighlighter(null);
        TNoPermintaan.setName("TNoPermintaan"); // NOI18N
        PanelInput.add(TNoPermintaan);
        TNoPermintaan.setBounds(540, 40, 140, 23);

        jLabel7.setText("Pelanggan :");
        jLabel7.setName("jLabel7"); // NOI18N
        PanelInput.add(jLabel7);
        jLabel7.setBounds(0, 70, 80, 23);

        jLabel18.setText("Sampel :");
        jLabel18.setName("jLabel18"); // NOI18N
        PanelInput.add(jLabel18);
        jLabel18.setBounds(385, 70, 60, 23);

        KodeSampel.setEditable(false);
        KodeSampel.setHighlighter(null);
        KodeSampel.setName("KodeSampel"); // NOI18N
        PanelInput.add(KodeSampel);
        KodeSampel.setBounds(449, 70, 55, 23);

        NamaSampel.setEditable(false);
        NamaSampel.setHighlighter(null);
        NamaSampel.setName("NamaSampel"); // NOI18N
        PanelInput.add(NamaSampel);
        NamaSampel.setBounds(506, 70, 174, 23);

        jLabel5.setText("No.Validasi :");
        jLabel5.setName("jLabel5"); // NOI18N
        PanelInput.add(jLabel5);
        jLabel5.setBounds(446, 10, 90, 23);

        TNoValidasi.setHighlighter(null);
        TNoValidasi.setName("TNoValidasi"); // NOI18N
        TNoValidasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoValidasiKeyPressed(evt);
            }
        });
        PanelInput.add(TNoValidasi);
        TNoValidasi.setBounds(540, 10, 140, 23);

        jLabel6.setText("Catatan :");
        jLabel6.setName("jLabel6"); // NOI18N
        PanelInput.add(jLabel6);
        jLabel6.setBounds(385, 100, 60, 23);

        Catatan.setHighlighter(null);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        PanelInput.add(Catatan);
        Catatan.setBounds(449, 100, 231, 23);

        jLabel8.setText("P.J.Verifikasi :");
        jLabel8.setName("jLabel8"); // NOI18N
        PanelInput.add(jLabel8);
        jLabel8.setBounds(0, 100, 80, 23);

        KodeVerifikator.setEditable(false);
        KodeVerifikator.setHighlighter(null);
        KodeVerifikator.setName("KodeVerifikator"); // NOI18N
        PanelInput.add(KodeVerifikator);
        KodeVerifikator.setBounds(84, 100, 80, 23);

        NamaVerifikator.setEditable(false);
        NamaVerifikator.setHighlighter(null);
        NamaVerifikator.setName("NamaVerifikator"); // NOI18N
        PanelInput.add(NamaVerifikator);
        NamaVerifikator.setBounds(166, 100, 206, 23);

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

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbValidasi.setName("tbValidasi"); // NOI18N
        Scroll2.setViewportView(tbValidasi);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

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
        LCount.setPreferredSize(new java.awt.Dimension(65, 23));
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        autoNomor();
        for(i=0;i<tbValidasi.getRowCount();i++){
            tbValidasi.setValueAt("",i,3);
            tbValidasi.setValueAt("",i,4);
        }
        Catatan.setText("");
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnCari,TNoValidasi);}
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalValidasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalValidasiKeyPressed
        Valid.pindah(evt, KdPJ, TNoValidasi);
    }//GEN-LAST:event_TanggalValidasiKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt, TNoValidasi,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(KdPJ.getText().equals("")||NmPJ.getText().equals("")){
            Valid.textKosong(btnPJ,"Penanggung Jawab Laborat");
        }else if(KodePelanggan.getText().equals("")||NamaPelanggan.getText().equals("")){
            Valid.textKosong(TNoValidasi,"Pelanggan");
        }else if(KodeVerifikator.getText().equals("")||NamaVerifikator.getText().equals("")){
            Valid.textKosong(TNoValidasi,"Penanggung Jawab Verifikasi");
        }else if(TNoPermintaan.getText().equals("")){
            Valid.textKosong(TNoPermintaan,"Nomor Permintaan");
        }else if(TNoValidasi.getText().equals("")){
            Valid.textKosong(TNoValidasi,"Nomor Validasi");
        }else if(KodeSampel.getText().equals("")||NamaSampel.getText().equals("")){
            Valid.textKosong(TNoValidasi,"Sampel");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(TNoValidasi,"Data Validasi");
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                ChkJln.setSelected(false);
                try {                    
                    Sequel.AutoComitFalse();
                    berhasil=true;
                    if(Sequel.menyimpantf2("laborat_kesling_validasi_pengujian_sampel","?,?,?,?,?,?,'Belum Bayar'","No.Validasi",6,new String[]{
                            TNoPermintaan.getText(),TNoValidasi.getText(),KdPJ.getText(),KodeVerifikator.getText(),Valid.SetTgl(TanggalValidasi.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),Catatan.getText()
                        })==true){
                        jasa_sarana=0;paket_bhp=0;jasa_pj_lab=0;jasa_pj_pengujian=0;jasa_verifikator=0;jasa_petugas=0;kso=0;jasa_menejemen=0;total=0;
                        for(i=0;i<tbValidasi.getRowCount();i++){
                            if(Sequel.menyimpantf2("laborat_kesling_detail_validasi_pengujian_sampel","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Verifikasi",16,new String[]{
                                TNoValidasi.getText(),tbValidasi.getValueAt(i,11).toString(),tbValidasi.getValueAt(i,9).toString(),tbValidasi.getValueAt(i,0).toString(),tbValidasi.getValueAt(i,5).toString(),tbValidasi.getValueAt(i,3).toString(),
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
                                berhasil=jur.simpanJurnal(TNoValidasi.getText(),"U","PELAYANAN LABORATORIUM KESEHATAN LINGKUNGAN "+NamaPelanggan.getText()+" DIPOSTING OLEH "+akses.getkode()); 
                            }  
                        }
                    }else{
                        berhasil=false;
                    }   
                    
                    if(berhasil==true){
                        Sequel.queryu("update laborat_kesling_verifikasi_pengujian_sampel set status='Sudah Divalidasi' where no_verifikasi='"+NoVerifikasi.getText()+"'");
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
            Valid.pindah(evt,TNoValidasi,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void KdPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPJKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPJActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoValidasi,TanggalValidasi);
        }
    }//GEN-LAST:event_KdPJKeyPressed

    private void btnPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPJActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPJActionPerformed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(KdPJ.getText().equals("")||NmPJ.getText().equals("")){
            Valid.textKosong(btnPJ,"Penanggung Jawab Verifikasi");
        }else if(KodePelanggan.getText().equals("")||NamaPelanggan.getText().equals("")){
            Valid.textKosong(TNoValidasi,"Pelanggan");
        }else if(TNoPermintaan.getText().equals("")){
            Valid.textKosong(TNoPermintaan,"Nomor Permintaan");
        }else if(TNoValidasi.getText().equals("")){
            Valid.textKosong(TNoValidasi,"Nomor Verifikasi");
        }else if(KodeSampel.getText().equals("")||NamaSampel.getText().equals("")){
            Valid.textKosong(TNoValidasi,"Sampel");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(TNoValidasi,"Data Verifikasi");
        }else{
            Sequel.queryu("delete from temporary");
            for(i=0;i<tbValidasi.getRowCount();i++){ 
                Sequel.menyimpan("temporary","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                    "0",tbValidasi.getValueAt(i,1).toString(),tbValidasi.getValueAt(i,2).toString(),tbValidasi.getValueAt(i,3).toString(),tbValidasi.getValueAt(i,5).toString(),tbValidasi.getValueAt(i,6).toString(),tbValidasi.getValueAt(i,7).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                });               
            }
            
            Map<String, Object> param = new HashMap<>();
            param.put("nomorpermintaan",TNoPermintaan.getText());
            param.put("novalidasi",TNoValidasi.getText());
            param.put("namapelanggan",NamaPelanggan.getText());
            param.put("alamatpelanggan",Sequel.cariIsi("select laborat_kesling_pelanggan.alamat from laborat_kesling_pelanggan where laborat_kesling_pelanggan.kode_pelanggan=?",KodePelanggan.getText()));
            param.put("jenisampel",NamaSampel.getText());
            param.put("bakumutu",Sequel.cariIsi("select laborat_kesling_master_sampel.baku_mutu from laborat_kesling_master_sampel where laborat_kesling_master_sampel.kode_sampel=?",KodeSampel.getText()));
            param.put("titiksampling",Sequel.cariIsi("select laborat_kesling_permintaan_pengujian_sampel.lokasi_sampling from laborat_kesling_permintaan_pengujian_sampel where laborat_kesling_permintaan_pengujian_sampel.no_permintaan=?",TNoPermintaan.getText()));
            param.put("disamplingoleh",Sequel.cariIsi("select laborat_kesling_permintaan_pengujian_sampel.sampling_dilakukan_oleh from laborat_kesling_permintaan_pengujian_sampel where laborat_kesling_permintaan_pengujian_sampel.no_permintaan=?",TNoPermintaan.getText()));
            param.put("waktusampling",Sequel.cariIsi("select date_format(laborat_kesling_permintaan_pengujian_sampel.waktu_sampling,'%d/%m/%Y %H:%i:%s') from laborat_kesling_permintaan_pengujian_sampel where laborat_kesling_permintaan_pengujian_sampel.no_permintaan=?",TNoPermintaan.getText()));
            param.put("waktuterimasampel",Sequel.cariIsi("select date_format(laborat_kesling_permintaan_pengujian_sampel.waktu_diterima,'%d/%m/%Y %H:%i:%s') from laborat_kesling_permintaan_pengujian_sampel where laborat_kesling_permintaan_pengujian_sampel.no_permintaan=?",TNoPermintaan.getText()));
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
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal,BtnCari);
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

    private void TNoValidasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoValidasiKeyPressed
        Valid.pindah(evt, BtnKeluar,Catatan);
    }//GEN-LAST:event_TNoValidasiKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt,TNoValidasi,BtnSimpan);
    }//GEN-LAST:event_CatatanKeyPressed

    private void RentangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RentangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RentangKeyPressed

    private void NoVerifikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoVerifikasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoVerifikasiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingValidasiPengujianSampel dialog = new LabKeslingValidasiPengujianSampel(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox Catatan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private javax.swing.JPanel FormInput;
    private widget.TextBox KdPJ;
    private widget.TextBox KodePelanggan;
    private widget.TextBox KodeSampel;
    private widget.TextBox KodeVerifikator;
    private widget.Label LCount;
    private widget.TextBox NamaPelanggan;
    private widget.TextBox NamaSampel;
    private widget.TextBox NamaVerifikator;
    private widget.TextBox NmPJ;
    private widget.TextBox NoVerifikasi;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox Rentang;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TNoPermintaan;
    private widget.TextBox TNoValidasi;
    private widget.Tanggal TanggalValidasi;
    private widget.Button btnPJ;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel15;
    private widget.Label jLabel18;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private widget.Label label11;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollInput;
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
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void setData(String nopermintaan,String kodepelanggan,String namapelanggan,String kodesampel,String namasampel,String kodeverifikator,String namaverifikator,String rentang,String noverifikasi) {
        TNoPermintaan.setText(nopermintaan);
        KodePelanggan.setText(kodepelanggan);
        NamaPelanggan.setText(namapelanggan);
        KodeSampel.setText(kodesampel);
        NamaSampel.setText(namasampel);
        KodeVerifikator.setText(kodeverifikator);
        NamaVerifikator.setText(namaverifikator);
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
        BtnSimpan.setEnabled(akses.getverifikasi_pengujian_sampel_lab_kesehatan_lingkungan());
        BtnPrint.setEnabled(akses.getverifikasi_pengujian_sampel_lab_kesehatan_lingkungan());
        if(akses.getjml2()>=1){
            KdPJ.setEditable(false);
            btnPJ.setEnabled(false);
            KdPJ.setText(akses.getkode());
            NmPJ.setText(petugas.tampil3(KdPJ.getText()));
            if(NmPJ.getText().equals("")){
                KdPJ.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }  
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            FormInput.setPreferredSize(new Dimension(WIDTH,158));
            scrollInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            FormInput.setPreferredSize(new Dimension(WIDTH,20));
            scrollInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
  
    private void autoNomor() {
        if(!KodeSampel.getText().trim().equals("")){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(laborat_kesling_validasi_pengujian_sampel.no_validasi,5),signed)),0) from laborat_kesling_validasi_pengujian_sampel inner join laborat_kesling_permintaan_pengujian_sampel on laborat_kesling_permintaan_pengujian_sampel.no_permintaan=laborat_kesling_validasi_pengujian_sampel.no_permintaan where date_format(laborat_kesling_validasi_pengujian_sampel.tanggal,'%Y')='"+TanggalValidasi.getSelectedItem().toString().substring(6,10)+"' and laborat_kesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"'",KodeSampel.getText()+"/"+TanggalValidasi.getSelectedItem().toString().substring(6,10)+"/LHU/",5,TNoValidasi);   
        }        
    }
}

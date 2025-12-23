/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package keuangan;

import bridging.MandiriCariKodeTransaksiTujuanTransfer;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.SwingUtilities;

/**
 *
 * @author dosen
 */
public final class KeuanganBayarBebanHutangLain extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();    
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private double total=0,sisahutang=0;
    private PreparedStatement ps;
    private ResultSet rs;
    private String koderekening="",kontraakun="",namakontraakun="",Host_to_Host_Bank_Mandiri="",Akun_Biaya_Mandiri="",kodemcm="",norekening="";
    private boolean sukses=true;
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public KeuanganBayarBebanHutangLain(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        DlgBayarMandiri.setSize(562,163);

        tabMode=new DefaultTableModel(null,new Object[]{
            "Tgl.Bayar","Kode","Pemberi Hutang","Cicilan(Rp)","Keterangan","No.Hutang","Kode Akun","Akun Bayar","No.Bukti"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(120);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoHutang.setDocument(new batasInput((byte)17).getKata(NoHutang));
        Cicilan.setDocument(new batasInput((byte)15).getOnlyAngka(Cicilan));
        Sisa.setDocument(new batasInput((byte)15).getOnlyAngka(Sisa));
        NoBukti.setDocument(new batasInput((byte)30).getOnlyAngka(NoBukti));
        Keterangan.setDocument(new batasInput((byte)100).getKata(Keterangan));
        KdPemberiHutang.setDocument(new batasInput((byte)5).getKata(KdPemberiHutang));
        NoRekening.setDocument(new batasInput((byte)20).getKata(NoRekening));
        RekeningAtasNama.setDocument(new batasInput((byte)60).getKata(RekeningAtasNama));
        KotaAtasNamaRekening.setDocument(new batasInput((byte)20).getKata(KotaAtasNamaRekening));
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
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
        
        Cicilan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(sisahutang>0){
                    Sisa.setText(Valid.SetAngka(sisahutang));
                    if(!Cicilan.getText().equals("")){                           
                         Sisa.setText(Valid.SetAngka(sisahutang-Double.parseDouble(Cicilan.getText())));                           
                    }
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if(sisahutang>0){
                    Sisa.setText(Valid.SetAngka(sisahutang));
                    if(!Cicilan.getText().equals("")){                           
                         Sisa.setText(Valid.SetAngka(sisahutang-Double.parseDouble(Cicilan.getText())));                           
                    }
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                if(sisahutang>0){
                    Sisa.setText(Valid.SetAngka(sisahutang));
                    if(!Cicilan.getText().equals("")){                           
                         Sisa.setText(Valid.SetAngka(sisahutang-Double.parseDouble(Cicilan.getText())));                           
                    }
                }
            }
        });
        
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

        Kd2 = new widget.TextBox();
        DlgBayarMandiri = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        jLabel99 = new widget.Label();
        BtnKeluarMandiri = new widget.Button();
        BtnSimpanMandiri = new widget.Button();
        NoRekening = new widget.TextBox();
        RekeningAtasNama = new widget.TextBox();
        KotaAtasNamaRekening = new widget.TextBox();
        BtnPetugas1 = new widget.Button();
        jLabel102 = new widget.Label();
        BiayaTransaksi = new widget.TextBox();
        KodeMetode = new widget.TextBox();
        MetodePembayaran = new widget.TextBox();
        jLabel103 = new widget.Label();
        KodeBank = new widget.TextBox();
        BankTujuan = new widget.TextBox();
        KodeTransaksi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        label12 = new widget.Label();
        LTotal = new widget.Label();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        NoHutang = new widget.TextBox();
        label36 = new widget.Label();
        Keterangan = new widget.TextBox();
        label35 = new widget.Label();
        Cicilan = new widget.TextBox();
        label16 = new widget.Label();
        KdPemberiHutang = new widget.TextBox();
        NmPemberiHutang = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        label38 = new widget.Label();
        Sisa = new widget.TextBox();
        BtnPeminjam = new widget.Button();
        jLabel10 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        BtnCari1 = new widget.Button();
        BtnAll1 = new widget.Button();
        NoBukti = new widget.TextBox();
        label37 = new widget.Label();

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        DlgBayarMandiri.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgBayarMandiri.setName("DlgBayarMandiri"); // NOI18N
        DlgBayarMandiri.setUndecorated(true);
        DlgBayarMandiri.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Pembayaran Pihak Ke 3 Bank Mandir ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        jLabel99.setText("No.Rekening :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelBiasa2.add(jLabel99);
        jLabel99.setBounds(0, 10, 80, 23);

        BtnKeluarMandiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarMandiri.setMnemonic('U');
        BtnKeluarMandiri.setText("Batal");
        BtnKeluarMandiri.setToolTipText("Alt+U");
        BtnKeluarMandiri.setName("BtnKeluarMandiri"); // NOI18N
        BtnKeluarMandiri.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarMandiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarMandiriActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluarMandiri);
        BtnKeluarMandiri.setBounds(442, 100, 100, 30);

        BtnSimpanMandiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanMandiri.setMnemonic('S');
        BtnSimpanMandiri.setText("Simpan");
        BtnSimpanMandiri.setToolTipText("Alt+S");
        BtnSimpanMandiri.setName("BtnSimpanMandiri"); // NOI18N
        BtnSimpanMandiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanMandiriActionPerformed(evt);
            }
        });
        BtnSimpanMandiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanMandiriKeyPressed(evt);
            }
        });
        panelBiasa2.add(BtnSimpanMandiri);
        BtnSimpanMandiri.setBounds(10, 100, 100, 30);

        NoRekening.setToolTipText("No.Rekening");
        NoRekening.setHighlighter(null);
        NoRekening.setName("NoRekening"); // NOI18N
        panelBiasa2.add(NoRekening);
        NoRekening.setBounds(84, 10, 130, 23);

        RekeningAtasNama.setToolTipText("Rekening Atas Nama");
        RekeningAtasNama.setHighlighter(null);
        RekeningAtasNama.setName("RekeningAtasNama"); // NOI18N
        panelBiasa2.add(RekeningAtasNama);
        RekeningAtasNama.setBounds(217, 10, 200, 23);

        KotaAtasNamaRekening.setToolTipText("Kota Atas Nama Rekening");
        KotaAtasNamaRekening.setHighlighter(null);
        KotaAtasNamaRekening.setName("KotaAtasNamaRekening"); // NOI18N
        panelBiasa2.add(KotaAtasNamaRekening);
        KotaAtasNamaRekening.setBounds(420, 10, 120, 23);

        BtnPetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas1.setMnemonic('1');
        BtnPetugas1.setToolTipText("ALt+1");
        BtnPetugas1.setName("BtnPetugas1"); // NOI18N
        BtnPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugas1ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPetugas1);
        BtnPetugas1.setBounds(512, 40, 28, 23);

        jLabel102.setText("Metode :");
        jLabel102.setName("jLabel102"); // NOI18N
        panelBiasa2.add(jLabel102);
        jLabel102.setBounds(0, 40, 80, 23);

        BiayaTransaksi.setEditable(false);
        BiayaTransaksi.setHighlighter(null);
        BiayaTransaksi.setName("BiayaTransaksi"); // NOI18N
        panelBiasa2.add(BiayaTransaksi);
        BiayaTransaksi.setBounds(399, 40, 110, 23);

        KodeMetode.setEditable(false);
        KodeMetode.setHighlighter(null);
        KodeMetode.setName("KodeMetode"); // NOI18N
        panelBiasa2.add(KodeMetode);
        KodeMetode.setBounds(84, 40, 64, 23);

        MetodePembayaran.setEditable(false);
        MetodePembayaran.setHighlighter(null);
        MetodePembayaran.setName("MetodePembayaran"); // NOI18N
        panelBiasa2.add(MetodePembayaran);
        MetodePembayaran.setBounds(151, 40, 245, 23);

        jLabel103.setText("Bank Tujuan :");
        jLabel103.setName("jLabel103"); // NOI18N
        panelBiasa2.add(jLabel103);
        jLabel103.setBounds(0, 70, 80, 23);

        KodeBank.setEditable(false);
        KodeBank.setHighlighter(null);
        KodeBank.setName("KodeBank"); // NOI18N
        panelBiasa2.add(KodeBank);
        KodeBank.setBounds(84, 70, 64, 23);

        BankTujuan.setEditable(false);
        BankTujuan.setHighlighter(null);
        BankTujuan.setName("BankTujuan"); // NOI18N
        panelBiasa2.add(BankTujuan);
        BankTujuan.setBounds(151, 70, 245, 23);

        KodeTransaksi.setEditable(false);
        KodeTransaksi.setHighlighter(null);
        KodeTransaksi.setName("KodeTransaksi"); // NOI18N
        panelBiasa2.add(KodeTransaksi);
        KodeTransaksi.setBounds(399, 70, 141, 23);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgBayarMandiri.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Bayar Beban Hutang Lain ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tgl.Bayar :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(27, 23));
        panelisi3.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(Tgl2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(LCount);

        jPanel1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnSimpan);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('1');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+1");
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
        panelisi1.add(BtnAll);

        label12.setText("Total :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(55, 30));
        panelisi1.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(140, 30));
        panelisi1.add(LTotal);

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

        jPanel1.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 154));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 194));
        FormInput.setLayout(null);

        label34.setText("No.Hutang :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label34);
        label34.setBounds(0, 10, 75, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label32);
        label32.setBounds(0, 40, 75, 23);

        NoHutang.setHighlighter(null);
        NoHutang.setName("NoHutang"); // NOI18N
        NoHutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoHutangKeyPressed(evt);
            }
        });
        FormInput.add(NoHutang);
        NoHutang.setBounds(79, 10, 160, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label36);
        label36.setBounds(0, 100, 75, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(79, 100, 631, 23);

        label35.setText("Cicilan :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label35);
        label35.setBounds(301, 40, 79, 23);

        Cicilan.setHighlighter(null);
        Cicilan.setName("Cicilan"); // NOI18N
        Cicilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CicilanKeyPressed(evt);
            }
        });
        FormInput.add(Cicilan);
        Cicilan.setBounds(384, 40, 120, 23);

        label16.setText("Pemberi Hutang :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(280, 10, 100, 23);

        KdPemberiHutang.setEditable(false);
        KdPemberiHutang.setName("KdPemberiHutang"); // NOI18N
        KdPemberiHutang.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPemberiHutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPemberiHutangKeyPressed(evt);
            }
        });
        FormInput.add(KdPemberiHutang);
        KdPemberiHutang.setBounds(384, 10, 90, 23);

        NmPemberiHutang.setEditable(false);
        NmPemberiHutang.setName("NmPemberiHutang"); // NOI18N
        NmPemberiHutang.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPemberiHutang);
        NmPemberiHutang.setBounds(476, 10, 204, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 110, 23);

        label38.setText("Sisa Hutang :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label38);
        label38.setBounds(510, 40, 77, 23);

        Sisa.setHighlighter(null);
        Sisa.setName("Sisa"); // NOI18N
        Sisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SisaKeyPressed(evt);
            }
        });
        FormInput.add(Sisa);
        Sisa.setBounds(590, 40, 120, 23);

        BtnPeminjam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPeminjam.setMnemonic('1');
        BtnPeminjam.setToolTipText("ALt+1");
        BtnPeminjam.setName("BtnPeminjam"); // NOI18N
        BtnPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPeminjamActionPerformed(evt);
            }
        });
        FormInput.add(BtnPeminjam);
        BtnPeminjam.setBounds(682, 10, 28, 23);

        jLabel10.setText("Akun Bayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(301, 70, 79, 23);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        FormInput.add(AkunBayar);
        AkunBayar.setBounds(384, 70, 296, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCari1);
        BtnCari1.setBounds(241, 10, 28, 23);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnAll1.setMnemonic('M');
        BtnAll1.setToolTipText("Alt+M");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnAll1);
        BtnAll1.setBounds(682, 70, 28, 23);

        NoBukti.setHighlighter(null);
        NoBukti.setName("NoBukti"); // NOI18N
        NoBukti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBuktiKeyPressed(evt);
            }
        });
        FormInput.add(NoBukti);
        NoBukti.setBounds(79, 70, 190, 23);

        label37.setText("No.Bukti :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label37);
        label37.setBounds(0, 70, 75, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NoHutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoHutangKeyPressed
        Valid.pindah(evt,TCari,NoHutang);
}//GEN-LAST:event_NoHutangKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoHutang.getText().trim().equals("")){
            Valid.textKosong(NoHutang,"No.Hutang");
        }else if(Cicilan.getText().trim().equals("")||Cicilan.getText().trim().equals("0")){
            Valid.textKosong(Cicilan,"Besar Cicilan");
        }else if(NmPemberiHutang.getText().trim().equals("")){
            Valid.textKosong(KdPemberiHutang,"Pemberi Hutang");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(AkunBayar.getSelectedItem()==null){
            Valid.textKosong(AkunBayar,"Akun Bayar");
        }else if(NoBukti.getText().trim().equals("")){
            Valid.textKosong(NoBukti,"No.Bukti");
        }else{         
            if(Sequel.cariInteger("select count(beban_hutang_lain.no_hutang) from beban_hutang_lain where beban_hutang_lain.no_hutang=? and beban_hutang_lain.kode_pemberi_hutang=?",NoHutang.getText(),KdPemberiHutang.getText())>0){
                sisahutang=(Sequel.cariIsiAngka("SELECT beban_hutang_lain.sisahutang FROM beban_hutang_lain where beban_hutang_lain.no_hutang=?",NoHutang.getText())-Double.parseDouble(Cicilan.getText()));
                if(sisahutang>0){
                    koderekening="";
                    try {
                        myObj = new FileReader("./cache/akunbayarhutang.iyem");
                        root = mapper.readTree(myObj);
                        response = root.path("akunbayarhutang");
                        if(response.isArray()){
                           for(JsonNode list:response){
                               if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                                    koderekening=list.path("KodeRek").asText();  
                               }
                           }
                        }
                        myObj.close();
                    } catch (Exception e) {
                        sukses=false;
                    } 
                    if(koderekening.equals("")){
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan akun bayar, silahkan hubungi administrator..!!");
                    }else{
                        if(koderekening.equals(Host_to_Host_Bank_Mandiri)){
                            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pembayaran_pihak_ke3_bankmandiri.nomor_pembayaran,6),signed)),0) from pembayaran_pihak_ke3_bankmandiri where left(pembayaran_pihak_ke3_bankmandiri.tgl_pembayaran,10)='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",kodemcm+"14"+Tanggal.getSelectedItem().toString().replaceAll("-",""),6,NoBukti); 
                            DlgBayarMandiri.setLocationRelativeTo(internalFrame1);
                            DlgBayarMandiri.setVisible(true);
                        }else{
                            kontraakun="";
                            namakontraakun="";
                            try {
                                myObj = new FileReader("./cache/pemberihutang.iyem");
                                root = mapper.readTree(myObj);
                                response = root.path("pemberihutang");
                                if(response.isArray()){
                                   for(JsonNode list:response){
                                       if(list.path("Kode").asText().equals(KdPemberiHutang.getText())){
                                            kontraakun=list.path("KodeRekening").asText();  
                                            namakontraakun=list.path("NamaRekening").asText();  
                                       }
                                   }
                                }
                                myObj.close();
                            } catch (Exception e) {
                                sukses=false;
                            }

                            Sequel.AutoComitFalse();
                            sukses=true;
                            if(Sequel.menyimpantf("bayar_beban_hutang_lain","?,?,?,?,?,?,?,?","Pembayaran",8,new String[]{
                                    Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPemberiHutang.getText(),Cicilan.getText(),
                                    Keterangan.getText(),NoHutang.getText(),koderekening,AkunBayar.getSelectedItem().toString(),
                                    NoBukti.getText()
                                })==true){
                                    if(sisahutang<=1){
                                        Sequel.mengedit("beban_hutang_lain","no_hutang='"+NoHutang.getText()+"'","status='Sudah Lunas'");
                                    }   
                                    Sequel.mengedit("beban_hutang_lain","no_hutang='"+NoHutang.getText()+"'","sisahutang=sisahutang-"+Cicilan.getText());
                                    Sequel.queryu("delete from tampjurnal");                    
                                    if(Sequel.menyimpantf2("tampjurnal","'"+kontraakun+"','"+namakontraakun+"','"+Cicilan.getText()+"','0'","Rekening")==false){
                                        sukses=false;
                                    }    
                                    if(Sequel.menyimpantf2("tampjurnal","'"+koderekening+"','"+AkunBayar.getSelectedItem()+"','0','"+Cicilan.getText()+"'","Rekening")==false){
                                        sukses=false;
                                    } 
                                    if(sukses==true){
                                        sukses=jur.simpanJurnal(NoBukti.getText(),"U","BAYAR BEBAN HUTANG LAIN NO.HUTANG "+NoHutang.getText()+", OLEH "+akses.getkode());
                                    }                   
                            }else{
                                sukses=false;
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
                                tabMode.addRow(new Object[]{
                                    Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPemberiHutang.getText(),NmPemberiHutang.getText(),Double.parseDouble(Cicilan.getText()),Keterangan.getText(),NoHutang.getText(),koderekening,AkunBayar.getSelectedItem().toString(),NoBukti.getText()
                                });
                                LCount.setText(""+tabMode.getRowCount());
                                total=total+Double.parseDouble(Cicilan.getText());
                                LTotal.setText(Valid.SetAngka(total));
                                emptTeks();
                            }
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(rootPane,"Maaf sudah dilakukan pembayaran..!!!");
                    TCari.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null,"No.Hutang untuk Pemberi Hutang "+KdPemberiHutang.getText()+" "+NmPemberiHutang.getText()+" tidak ditemukan......!!!");
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Keterangan,BtnKeluar);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbKamar.getSelectedRow()>-1){
            Sequel.AutoComitFalse();
            sukses=true;

            if(Sequel.queryu2tf("delete from bayar_beban_hutang_lain where tgl_bayar=? and kode_pemberi_hutang=? and no_hutang=? and kd_rek=? and nama_bayar=?", 5,new String[]{
                tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString(),
                tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()
            })==true){
                kontraakun="";
                namakontraakun="";
                try {
                    myObj = new FileReader("./cache/pemberihutang.iyem");
                    root = mapper.readTree(myObj);
                    response = root.path("pemberihutang");
                    if(response.isArray()){
                       for(JsonNode list:response){
                           if(list.path("Kode").asText().equals(tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString())){
                                kontraakun=list.path("KodeRekening").asText();  
                                namakontraakun=list.path("NamaRekening").asText();  
                           }
                       }
                    }
                    myObj.close();
                } catch (Exception e) {
                    sukses=false;
                }
                Sequel.mengedit("beban_hutang_lain","no_hutang='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()+"'","status='Belum Lunas', sisahutang=sisahutang+"+tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString());                      
                Sequel.queryu("delete from tampjurnal");                    
                if(Sequel.menyimpantf2("tampjurnal","'"+kontraakun+"','"+namakontraakun+"','0','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString()+"'","Rekening")==false){
                    sukses=false;
                }   
                if(Sequel.menyimpantf2("tampjurnal","'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString()+"','0'","Rekening")==false){
                    sukses=false;
                } 
                if(sukses==true){
                    sukses=jur.simpanJurnal(NoBukti.getText(),"U","PEMBATALAN BAYAR BEBAN HUTANG LAIN NO.HUTANG "+NoHutang.getText()+", OLEH "+akses.getkode());
                }     
            }else{
                sukses=false;
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
                total=total-Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString());
                LTotal.setText(Valid.SetAngka(total));
                tabMode.removeRow(tbKamar.getSelectedRow());
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!");
        }   
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnKeluar, BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnKeluar.requestFocus();
        }else if(tabMode.getRowCount()!=0){     
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());        
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));   
            Valid.MyReportqry("rptBayarBebanHutangLain.jasper","report","::[ Bayar Beban Hutang Lain ]::",
                "select bayar_beban_hutang_lain.tgl_bayar, bayar_beban_hutang_lain.kode_pemberi_hutang,pemberi_hutang_lain.nama_pemberi_hutang, bayar_beban_hutang_lain.besar_cicilan,"+
                "bayar_beban_hutang_lain.keterangan, bayar_beban_hutang_lain.no_hutang,bayar_beban_hutang_lain.nama_bayar,bayar_beban_hutang_lain.no_bukti from bayar_beban_hutang_lain "+
                "inner join pemberi_hutang_lain on bayar_beban_hutang_lain.kode_pemberi_hutang=pemberi_hutang_lain.kode_pemberi_hutang where "+
                "bayar_beban_hutang_lain.tgl_bayar between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and bayar_beban_hutang_lain.no_hutang like '%"+TCari.getText()+"%' or "+
                "bayar_beban_hutang_lain.tgl_bayar between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and bayar_beban_hutang_lain.kode_pemberi_hutang like '%"+TCari.getText()+"%' or "+
                "bayar_beban_hutang_lain.tgl_bayar between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and pemberi_hutang_lain.nama_pemberi_hutang like '%"+TCari.getText()+"%' or "+
                "bayar_beban_hutang_lain.tgl_bayar between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and bayar_beban_hutang_lain.tgl_bayar like '%"+TCari.getText()+"%' "+
                "order by bayar_beban_hutang_lain.tgl_bayar,bayar_beban_hutang_lain.kode_pemberi_hutang,bayar_beban_hutang_lain.no_hutang ",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
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
        runBackground(() ->tampil());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbKamarKeyPressed

private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
   Valid.pindah(evt,AkunBayar,BtnSimpan);
}//GEN-LAST:event_KeteranganKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NoHutang,KdPemberiHutang);
    }//GEN-LAST:event_TanggalKeyPressed

    private void CicilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CicilanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sisa.setText(Valid.SetAngka(sisahutang));
            if(!Cicilan.getText().equals("")){                           
                 Sisa.setText(Valid.SetAngka(sisahutang-Double.parseDouble(Cicilan.getText())));                           
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sisa.setText(Valid.SetAngka(sisahutang));
            if(!Cicilan.getText().equals("")){                           
                 Sisa.setText(Valid.SetAngka(sisahutang-Double.parseDouble(Cicilan.getText())));                           
            }
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sisa.setText(Valid.SetAngka(sisahutang));
            if(!Cicilan.getText().equals("")){                           
                 Sisa.setText(Valid.SetAngka(sisahutang-Double.parseDouble(Cicilan.getText())));                           
            }
            AkunBayar.requestFocus();
        }
    }//GEN-LAST:event_CicilanKeyPressed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        runBackground(() ->tampil());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void SisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SisaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SisaKeyPressed

private void BtnPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPeminjamActionPerformed
        kontraakun="";
        namakontraakun="";
        DlgCariPemberiHutang pemberihutang=new DlgCariPemberiHutang(null,false);
        pemberihutang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pemberihutang.getTable().getSelectedRow()!= -1){
                    KdPemberiHutang.setText(pemberihutang.getTable().getValueAt(pemberihutang.getTable().getSelectedRow(),0).toString());
                    NmPemberiHutang.setText(pemberihutang.getTable().getValueAt(pemberihutang.getTable().getSelectedRow(),1).toString());
                    kontraakun=pemberihutang.getTable().getValueAt(pemberihutang.getTable().getSelectedRow(),4).toString();
                    namakontraakun=pemberihutang.getTable().getValueAt(pemberihutang.getTable().getSelectedRow(),5).toString();
                    BtnPeminjam.requestFocus();
                }      
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {pemberihutang.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        pemberihutang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pemberihutang.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        pemberihutang.emptTeks();
        pemberihutang.isCek();
        pemberihutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pemberihutang.setLocationRelativeTo(internalFrame1);
        pemberihutang.setAlwaysOnTop(false);
        pemberihutang.setVisible(true);
}//GEN-LAST:event_BtnPeminjamActionPerformed

    private void AkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkunBayarKeyPressed
        Valid.pindah(evt,NoBukti,Keterangan);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        if(NoHutang.getText().trim().equals("")){
            Valid.textKosong(NoHutang,"No.Hutang");
        }else{
            if(Sequel.cariInteger("select count(no_hutang) from beban_hutang_lain where no_hutang=?",NoHutang.getText())>0){
                KdPemberiHutang.setText(Sequel.cariIsi("select kode_pemberi_hutang from beban_hutang_lain where no_hutang=?",NoHutang.getText()));
                NmPemberiHutang.setText(Sequel.cariIsi("select nama_pemberi_hutang from pemberi_hutang_lain where kode_pemberi_hutang=?",KdPemberiHutang.getText()));
                sisahutang=Sequel.cariIsiAngka("SELECT sisahutang FROM beban_hutang_lain where no_hutang=?",NoHutang.getText());
                Sisa.setText(Valid.SetAngka(Valid.roundUp(sisahutang,100)));
            }else{
                JOptionPane.showMessageDialog(null,"No.Hutang tidak ditemukan......!!!");
            }
        }
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/akunbankmandiri.iyem")<30){
                tampilAkunBankMandiri2();
            }else{
                tampilAkunBankMandiri();
            }
        } catch (Exception e) {
        }
        
        try {
            if(Valid.daysOld("./cache/akunbayarhutang.iyem")<8){
                runBackground(() ->tampilAkunBayar2());
            }else{
                runBackground(() ->tampilAkunBayar());
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void KdPemberiHutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPemberiHutangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPeminjamActionPerformed(null);
        }else{
            Valid.pindah(evt,AkunBayar,NoBukti);
        }
    }//GEN-LAST:event_KdPemberiHutangKeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        tampilAkunBankMandiri();
        runBackground(() ->tampilAkunBayar());
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void NoBuktiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBuktiKeyPressed
        Valid.pindah(evt,Cicilan,AkunBayar);
    }//GEN-LAST:event_NoBuktiKeyPressed

    private void BtnKeluarMandiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarMandiriActionPerformed
        NoRekening.setText("");
        RekeningAtasNama.setText("");
        KotaAtasNamaRekening.setText("");
        KodeMetode.setText("");
        MetodePembayaran.setText("");
        BiayaTransaksi.setText("0");
        KodeBank.setText("");
        BankTujuan.setText("");
        KodeTransaksi.setText("");
        DlgBayarMandiri.dispose();
    }//GEN-LAST:event_BtnKeluarMandiriActionPerformed

    private void BtnSimpanMandiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanMandiriActionPerformed
        if(NoRekening.getText().trim().equals("")){
            Valid.textKosong(NoRekening,"No.Rekening Tujuan");
        }else if(RekeningAtasNama.getText().trim().equals("")){
            Valid.textKosong(RekeningAtasNama,"Rekening Atas Nama");
        }else if(KotaAtasNamaRekening.getText().trim().equals("")){
            Valid.textKosong(KotaAtasNamaRekening,"Kota Atas Nama Rekening");
        }else if(KodeMetode.getText().trim().equals("")){
            Valid.textKosong(KodeMetode,"Kode Metode Pembayaran");
        }else if(MetodePembayaran.getText().trim().equals("")){
            Valid.textKosong(MetodePembayaran,"Metode Pembayaran");
        }else if(BiayaTransaksi.getText().trim().equals("")){
            Valid.textKosong(BiayaTransaksi,"Biaya Traksasi");
        }else if(KodeBank.getText().trim().equals("")){
            Valid.textKosong(KodeBank,"Kode Bank Tujuan");
        }else if(BankTujuan.getText().trim().equals("")){
            Valid.textKosong(BankTujuan,"Bank Tujuan");
        }else{
            try {
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf("bayar_beban_hutang_lain","?,?,?,?,?,?,?,?","Pembayaran",8,new String[]{
                        Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPemberiHutang.getText(),Cicilan.getText(),
                        Keterangan.getText(),NoHutang.getText(),koderekening,AkunBayar.getSelectedItem().toString(),
                        NoBukti.getText()
                    })==true){
                    if(sisahutang<=1){
                        Sequel.mengedit("beban_hutang_lain","no_hutang='"+NoHutang.getText()+"'","status='Sudah Lunas'");
                    }   
                    Sequel.mengedit("beban_hutang_lain","no_hutang='"+NoHutang.getText()+"'","sisahutang=sisahutang-"+Cicilan.getText());
                    Sequel.queryu("delete from tampjurnal");                    
                    if(Sequel.menyimpantf2("tampjurnal","'"+kontraakun+"','"+namakontraakun+"','"+Cicilan.getText()+"','0'","Rekening")==false){
                        sukses=false;
                    }    
                    if(Sequel.menyimpantf2("tampjurnal","'"+koderekening+"','"+AkunBayar.getSelectedItem()+"','0','"+Cicilan.getText()+"'","Rekening")==false){
                        sukses=false;
                    } 
                    if(sukses==true){
                        sukses=jur.simpanJurnal(NoBukti.getText(),"U","BAYAR BEBAN HUTANG LAIN NO.HUTANG "+NoHutang.getText()+", OLEH "+akses.getkode());
                    }  
                    if(sukses==true){
                        if(Sequel.menyimpantf("pembayaran_pihak_ke3_bankmandiri","?,now(),?,?,?,?,?,?,?,?,?,?,?","No.Bukti", 12,new String[]{
                            NoBukti.getText(),norekening,NoRekening.getText(),RekeningAtasNama.getText(),KotaAtasNamaRekening.getText(),Cicilan.getText(),NoHutang.getText(),KodeMetode.getText(),KodeBank.getText(),KodeTransaksi.getText(),"Bayar Beban Hutang Lain","Baru"
                        })==false){
                            sukses=false;
                        }
                    }
                }else{
                    sukses=false;
                }  

                if(sukses==true){
                    Sequel.Commit();
                    BtnCariActionPerformed(evt);
                    emptTeks();
                    DlgBayarMandiri.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
            }catch (Exception ex) {
                System.out.println("Notif Simpan Mandiri : "+ex);
            }
        }
    }//GEN-LAST:event_BtnSimpanMandiriActionPerformed

    private void BtnSimpanMandiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanMandiriKeyPressed

    }//GEN-LAST:event_BtnSimpanMandiriKeyPressed

    private void BtnPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugas1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MandiriCariKodeTransaksiTujuanTransfer kodetransaksibank=new MandiriCariKodeTransaksiTujuanTransfer(null, false);
        kodetransaksibank.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kodetransaksibank.getTable().getSelectedRow()!= -1){
                    KodeMetode.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),0).toString());
                    MetodePembayaran.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),1).toString());
                    BiayaTransaksi.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),2).toString());
                    KodeBank.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),3).toString());
                    BankTujuan.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),4).toString());
                    KodeTransaksi.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),5).toString());
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

        kodetransaksibank.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kodetransaksibank.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        kodetransaksibank.setCari(BankTujuan.getText());
        kodetransaksibank.isCek();
        kodetransaksibank.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kodetransaksibank.setLocationRelativeTo(internalFrame1);
        kodetransaksibank.setAlwaysOnTop(false);
        kodetransaksibank.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPetugas1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganBayarBebanHutangLain dialog = new KeuanganBayarBebanHutangLain(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AkunBayar;
    private widget.TextBox BankTujuan;
    private widget.TextBox BiayaTransaksi;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarMandiri;
    private widget.Button BtnPeminjam;
    private widget.Button BtnPetugas1;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanMandiri;
    private widget.CekBox ChkInput;
    private widget.TextBox Cicilan;
    private javax.swing.JDialog DlgBayarMandiri;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.TextBox KdPemberiHutang;
    private widget.TextBox Keterangan;
    private widget.TextBox KodeBank;
    private widget.TextBox KodeMetode;
    private widget.TextBox KodeTransaksi;
    private widget.TextBox KotaAtasNamaRekening;
    private widget.Label LCount;
    private widget.Label LTotal;
    private widget.TextBox MetodePembayaran;
    private widget.TextBox NmPemberiHutang;
    private widget.TextBox NoBukti;
    private widget.TextBox NoHutang;
    private widget.TextBox NoRekening;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox RekeningAtasNama;
    private widget.ScrollPane Scroll;
    private widget.TextBox Sisa;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label32;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label9;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            ps=koneksi.prepareStatement(
                    "select bayar_beban_hutang_lain.tgl_bayar, bayar_beban_hutang_lain.kode_pemberi_hutang,pemberi_hutang_lain.nama_pemberi_hutang, bayar_beban_hutang_lain.besar_cicilan,"+
                    "bayar_beban_hutang_lain.keterangan, bayar_beban_hutang_lain.no_hutang,bayar_beban_hutang_lain.kd_rek,bayar_beban_hutang_lain.nama_bayar,bayar_beban_hutang_lain.no_bukti "+
                    "from bayar_beban_hutang_lain inner join pemberi_hutang_lain on bayar_beban_hutang_lain.kode_pemberi_hutang=pemberi_hutang_lain.kode_pemberi_hutang where "+
                    "bayar_beban_hutang_lain.tgl_bayar between ? and ? "+(TCari.getText().trim().equals("")?"":"and (bayar_beban_hutang_lain.no_hutang like ? or bayar_beban_hutang_lain.kode_pemberi_hutang like ? or "+
                    "pemberi_hutang_lain.nama_pemberi_hutang like ?) ")+"order by bayar_beban_hutang_lain.tgl_bayar,bayar_beban_hutang_lain.kode_pemberi_hutang");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                total=0;
                while(rs.next()){                
                    total=total+rs.getDouble(4);
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif :"+e);
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
        LTotal.setText(Valid.SetAngka(total));
    }
    
    public void tampil2() {
        runBackground(() ->tampil());
    }

    public void emptTeks() {
        Kd2.setText("");
        Cicilan.setText("0");
        KdPemberiHutang.setText("");
        NmPemberiHutang.setText("");
        Sisa.setText("0");
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        sisahutang=0;
        NoHutang.requestFocus();
    }
    
    public void setData(String nonota,String kodepemberihutang,String namapemberihutang){
        NoHutang.setText(nonota);
        KdPemberiHutang.setText(kodepemberihutang);
        NmPemberiHutang.setText(namapemberihutang);
        TCari.setText(nonota);
        ChkInput.setSelected(true);
        isForm();
        sisahutang=Sequel.cariIsiAngka("SELECT beban_hutang_lain.sisahutang FROM beban_hutang_lain where beban_hutang_lain.no_hutang=?",NoHutang.getText());
        Sisa.setText(Valid.SetAngka(Valid.roundUp(sisahutang,100)));
        if(sisahutang<=0){
            JOptionPane.showMessageDialog(null,"Hutang sudah lunas..!!");
        }
    }

    private void getData() {
        int row=tbKamar.getSelectedRow();
        if(row!= -1){
            KdPemberiHutang.setText(tbKamar.getValueAt(row,1).toString());
            NmPemberiHutang.setText(tbKamar.getValueAt(row,2).toString());
            Cicilan.setText(tbKamar.getValueAt(row,3).toString());
            Keterangan.setText(tbKamar.getValueAt(row,4).toString());
            NoHutang.setText(tbKamar.getValueAt(row,5).toString());
            NoBukti.setText(tbKamar.getValueAt(row,8).toString());
            Valid.SetTgl(Tanggal,tbKamar.getValueAt(row,0).toString());
        }
    }

    public JTextField getTextField(){
        return NoHutang;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbayar_beban_hutang_lain());
        BtnHapus.setEnabled(akses.getbayar_beban_hutang_lain());
        BtnPrint.setEnabled(akses.getbayar_beban_hutang_lain());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,154));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    private void tampilAkunBayar() {         
         try{      
             file=new File("./cache/akunbayarhutang.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             StringBuilder iyembuilder = new StringBuilder();
             ps=koneksi.prepareStatement("select * from akun_bayar_hutang order by akun_bayar_hutang.nama_bayar");
             try{
                 rs=ps.executeQuery();
                 AkunBayar.removeAllItems();
                 while(rs.next()){    
                     AkunBayar.addItem(rs.getString(1).replaceAll("\"",""));
                     iyembuilder.append("{\"NamaAkun\":\"").append(rs.getString(1).replaceAll("\"","")).append("\",\"KodeRek\":\"").append(rs.getString(2)).append("\"},");
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
                fileWriter.write("{\"akunbayarhutang\":["+iyembuilder+"]}");
                fileWriter.flush();
             }
            
             fileWriter.close();
             iyembuilder=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilAkunBayar2() {
        try {
            myObj = new FileReader("./cache/akunbayarhutang.iyem");
            root = mapper.readTree(myObj);
            response = root.path("akunbayarhutang");
            if(response.isArray()){
                for(JsonNode list:response){
                    AkunBayar.addItem(list.path("NamaAkun").asText().replaceAll("\"",""));
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
    
    private void tampilAkunBankMandiri() { 
        try{     
            ps=koneksi.prepareStatement(
                    "select set_akun_mandiri.kd_rek,set_akun_mandiri.kd_rek_biaya,set_akun_mandiri.kode_mcm,set_akun_mandiri.no_rekening from set_akun_mandiri");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    file=new File("./cache/akunbankmandiri.iyem");
                    file.createNewFile();
                    fileWriter = new FileWriter(file);
                    Host_to_Host_Bank_Mandiri=rs.getString("kd_rek");
                    Akun_Biaya_Mandiri=rs.getString("kd_rek_biaya");
                    kodemcm=rs.getString("kode_mcm");
                    norekening=rs.getString("no_rekening");
                    fileWriter.write("{\"akunbankmandiri\":\""+Host_to_Host_Bank_Mandiri+"\",\"kodemcm\":\""+kodemcm+"\",\"akunbiayabankmandiri\":\""+Akun_Biaya_Mandiri+"\",\"norekening\":\""+norekening+"\"}");
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (Exception e) {
                Host_to_Host_Bank_Mandiri="";
                Akun_Biaya_Mandiri="";
                kodemcm="";
                norekening="";
                System.out.println("Notif Set Nota : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
             Host_to_Host_Bank_Mandiri="";
             Akun_Biaya_Mandiri="";
             kodemcm="";
             norekening="";
        }
    }
    
    private void tampilAkunBankMandiri2() { 
        try{      
             myObj = new FileReader("./cache/akunbankmandiri.iyem");
             root = mapper.readTree(myObj);
             response = root.path("akunbankmandiri");
             Host_to_Host_Bank_Mandiri=response.asText();
             response = root.path("akunbiayabankmandiri");
             Akun_Biaya_Mandiri=response.asText();
             response = root.path("kodemcm");
             kodemcm=response.asText();
             response = root.path("norekening");
             norekening=response.asText();
             myObj.close();
        } catch (Exception e) {
             Host_to_Host_Bank_Mandiri="";
             Akun_Biaya_Mandiri="";
             kodemcm="";
             norekening="";
        }
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        ceksukses = true;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        executor.submit(() -> {
            try {
                task.run();
            } finally {
                ceksukses = false;
                SwingUtilities.invokeLater(() -> {
                    this.setCursor(Cursor.getDefaultCursor());
                });
            }
        });
    }
}

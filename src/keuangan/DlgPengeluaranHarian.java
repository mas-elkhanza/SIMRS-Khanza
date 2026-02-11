/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgResepObat.java
 *
 * Created on 31 Mei 10, 11:27:40
 */

package keuangan;
import bridging.MandiriCariKodeTransaksiTujuanTransfer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kepegawaian.DlgCariPetugas;
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
import java.awt.event.WindowAdapter;
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
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 * @author perpustakaan
 */
public final class DlgPengeluaranHarian extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private PreparedStatement ps,psakun;
    private ResultSet rs;
    private DlgCariPetugas petugas;
    private double total=0;
    private boolean sukses=true;
    private String nopengajuanbiaya="",akun="",kontrakun="";
    private int i=0;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private String Host_to_Host_Bank_Mandiri="",Akun_Biaya_Mandiri="",kodemcm="",norekening="";

    /** Creates new form DlgResepObat 
     *@param parent
     *@param modal*/
    public DlgPengeluaranHarian(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        DlgBayarMandiri.setSize(562,193);

        Object[] row={"Nomor","Tanggal","Kategori","Petugas","Pengeluaran","Keterangan","Kode","NIP"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbResep.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(150);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(350);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());

        Keterangan.setDocument(new batasInput((byte)70).getKata(Keterangan));
        KdPtg.setDocument(new batasInput((byte)20).getKata(KdPtg));
        Pengeluaran.setDocument(new batasInput((byte)15).getOnlyAngka(Pengeluaran));
        NoRekening.setDocument(new batasInput((byte)20).getKata(NoRekening));
        RekeningAtasNama.setDocument(new batasInput((byte)60).getKata(RekeningAtasNama));
        KotaAtasNamaRekening.setDocument(new batasInput((byte)20).getKata(KotaAtasNamaRekening));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        ChkInput.setSelected(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DlgBayarMandiri = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        jLabel99 = new widget.Label();
        BtnKeluarMandiri = new widget.Button();
        BtnSimpanMandiri = new widget.Button();
        NoRekening = new widget.TextBox();
        RekeningAtasNama = new widget.TextBox();
        KotaAtasNamaRekening = new widget.TextBox();
        BtnMetode = new widget.Button();
        jLabel102 = new widget.Label();
        BiayaTransaksi = new widget.TextBox();
        KodeMetode = new widget.TextBox();
        MetodePembayaran = new widget.TextBox();
        jLabel103 = new widget.Label();
        KodeBank = new widget.TextBox();
        BankTujuan = new widget.TextBox();
        KodeTransaksi = new widget.TextBox();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
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
        Keterangan = new widget.TextBox();
        KdPtg = new widget.TextBox();
        NmPtg = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel13 = new widget.Label();
        btnPetugas = new widget.Button();
        jLabel14 = new widget.Label();
        KdKategori = new widget.TextBox();
        NmKategori = new widget.TextBox();
        btnKategori = new widget.Button();
        Tanggal = new widget.Tanggal();
        jLabel8 = new widget.Label();
        jLabel11 = new widget.Label();
        Pengeluaran = new widget.TextBox();
        jLabel12 = new widget.Label();
        Nomor = new widget.TextBox();
        ChkInput = new widget.CekBox();

        DlgBayarMandiri.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgBayarMandiri.setName("DlgBayarMandiri"); // NOI18N
        DlgBayarMandiri.setUndecorated(true);
        DlgBayarMandiri.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Pembayaran Pihak Ke 3 Bank Mandiri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        jLabel99.setText("Kota Bank Tujuan :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelBiasa2.add(jLabel99);
        jLabel99.setBounds(226, 10, 120, 23);

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
        BtnKeluarMandiri.setBounds(442, 130, 100, 30);

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
        BtnSimpanMandiri.setBounds(10, 130, 100, 30);

        NoRekening.setHighlighter(null);
        NoRekening.setName("NoRekening"); // NOI18N
        NoRekening.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRekeningKeyPressed(evt);
            }
        });
        panelBiasa2.add(NoRekening);
        NoRekening.setBounds(84, 10, 130, 23);

        RekeningAtasNama.setHighlighter(null);
        RekeningAtasNama.setName("RekeningAtasNama"); // NOI18N
        RekeningAtasNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RekeningAtasNamaKeyPressed(evt);
            }
        });
        panelBiasa2.add(RekeningAtasNama);
        RekeningAtasNama.setBounds(84, 40, 456, 23);

        KotaAtasNamaRekening.setHighlighter(null);
        KotaAtasNamaRekening.setName("KotaAtasNamaRekening"); // NOI18N
        KotaAtasNamaRekening.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KotaAtasNamaRekeningKeyPressed(evt);
            }
        });
        panelBiasa2.add(KotaAtasNamaRekening);
        KotaAtasNamaRekening.setBounds(350, 10, 190, 23);

        BtnMetode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMetode.setMnemonic('1');
        BtnMetode.setToolTipText("ALt+1");
        BtnMetode.setName("BtnMetode"); // NOI18N
        BtnMetode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMetodeActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnMetode);
        BtnMetode.setBounds(512, 70, 28, 23);

        jLabel102.setText("Metode :");
        jLabel102.setName("jLabel102"); // NOI18N
        panelBiasa2.add(jLabel102);
        jLabel102.setBounds(0, 70, 80, 23);

        BiayaTransaksi.setEditable(false);
        BiayaTransaksi.setHighlighter(null);
        BiayaTransaksi.setName("BiayaTransaksi"); // NOI18N
        panelBiasa2.add(BiayaTransaksi);
        BiayaTransaksi.setBounds(399, 70, 110, 23);

        KodeMetode.setEditable(false);
        KodeMetode.setHighlighter(null);
        KodeMetode.setName("KodeMetode"); // NOI18N
        panelBiasa2.add(KodeMetode);
        KodeMetode.setBounds(84, 70, 64, 23);

        MetodePembayaran.setEditable(false);
        MetodePembayaran.setHighlighter(null);
        MetodePembayaran.setName("MetodePembayaran"); // NOI18N
        panelBiasa2.add(MetodePembayaran);
        MetodePembayaran.setBounds(151, 70, 245, 23);

        jLabel103.setText("Bank Tujuan :");
        jLabel103.setName("jLabel103"); // NOI18N
        panelBiasa2.add(jLabel103);
        jLabel103.setBounds(0, 100, 80, 23);

        KodeBank.setEditable(false);
        KodeBank.setHighlighter(null);
        KodeBank.setName("KodeBank"); // NOI18N
        panelBiasa2.add(KodeBank);
        KodeBank.setBounds(84, 100, 64, 23);

        BankTujuan.setEditable(false);
        BankTujuan.setHighlighter(null);
        BankTujuan.setName("BankTujuan"); // NOI18N
        panelBiasa2.add(BankTujuan);
        BankTujuan.setBounds(151, 100, 245, 23);

        KodeTransaksi.setEditable(false);
        KodeTransaksi.setHighlighter(null);
        KodeTransaksi.setName("KodeTransaksi"); // NOI18N
        panelBiasa2.add(KodeTransaksi);
        KodeTransaksi.setBounds(399, 100, 141, 23);

        jLabel100.setText("Atas Nama :");
        jLabel100.setName("jLabel100"); // NOI18N
        panelBiasa2.add(jLabel100);
        jLabel100.setBounds(0, 40, 80, 23);

        jLabel101.setText("No.Rekening :");
        jLabel101.setName("jLabel101"); // NOI18N
        panelBiasa2.add(jLabel101);
        jLabel101.setBounds(0, 10, 80, 23);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengeluaran Harian ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbResep.setAutoCreateRowSorter(true);
        tbResep.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(312, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(440, 128));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 77));
        FormInput.setLayout(null);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(83, 72, 354, 23);

        KdPtg.setHighlighter(null);
        KdPtg.setName("KdPtg"); // NOI18N
        KdPtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPtgKeyPressed(evt);
            }
        });
        FormInput.add(KdPtg);
        KdPtg.setBounds(83, 42, 100, 23);

        NmPtg.setEditable(false);
        NmPtg.setHighlighter(null);
        NmPtg.setName("NmPtg"); // NOI18N
        FormInput.add(NmPtg);
        NmPtg.setBounds(185, 42, 221, 23);

        jLabel3.setText("Keterangan :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 72, 80, 23);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 42, 80, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('3');
        btnPetugas.setToolTipText("Alt+3");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(409, 42, 28, 23);

        jLabel14.setText("Ketegori :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 12, 80, 23);

        KdKategori.setEditable(false);
        KdKategori.setHighlighter(null);
        KdKategori.setName("KdKategori"); // NOI18N
        KdKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKategoriKeyPressed(evt);
            }
        });
        FormInput.add(KdKategori);
        KdKategori.setBounds(83, 12, 70, 23);

        NmKategori.setEditable(false);
        NmKategori.setHighlighter(null);
        NmKategori.setName("NmKategori"); // NOI18N
        FormInput.add(NmKategori);
        NmKategori.setBounds(155, 12, 251, 23);

        btnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKategori.setMnemonic('3');
        btnKategori.setToolTipText("Alt+3");
        btnKategori.setName("btnKategori"); // NOI18N
        btnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriActionPerformed(evt);
            }
        });
        btnKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKategoriKeyPressed(evt);
            }
        });
        FormInput.add(btnKategori);
        btnKategori.setBounds(409, 12, 28, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2026 12:21:23" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(100, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(545, 10, 154, 23);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(470, 10, 73, 23);

        jLabel11.setText("Pengeluaran : Rp.");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(470, 70, 93, 23);

        Pengeluaran.setText("0");
        Pengeluaran.setHighlighter(null);
        Pengeluaran.setName("Pengeluaran"); // NOI18N
        Pengeluaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengeluaranKeyPressed(evt);
            }
        });
        FormInput.add(Pengeluaran);
        Pengeluaran.setBounds(565, 70, 134, 23);

        jLabel12.setText("Nomor :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(470, 40, 73, 23);

        Nomor.setHighlighter(null);
        Nomor.setName("Nomor"); // NOI18N
        Nomor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorKeyPressed(evt);
            }
        });
        FormInput.add(Nomor);
        Nomor.setBounds(545, 40, 154, 23);

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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(KdPtg.getText().trim().equals("")||NmPtg.getText().trim().equals("")){
            Valid.textKosong(KdPtg,"Petugas Keuangan");
        }else if(KdKategori.getText().trim().equals("")||NmKategori.getText().trim().equals("")){
            Valid.textKosong(KdKategori,"Kategori Pemasukkan");
        }else if(Nomor.getText().trim().equals("")){
            Valid.textKosong(Nomor,"Nomor Transaksi");
        }else if(Pengeluaran.getText().trim().equals("")||Pengeluaran.getText().trim().equals("0")){
            Valid.textKosong(Pengeluaran,"Pengeluaran");
        }else{
            akun="";
            kontrakun="";
            try {
                psakun=koneksi.prepareStatement(
                    "select kategori_pengeluaran_harian.kd_rek,kategori_pengeluaran_harian.kd_rek2 from kategori_pengeluaran_harian where kategori_pengeluaran_harian.kode_kategori=?");
                try {
                    psakun.setString(1,KdKategori.getText());
                    rs=psakun.executeQuery();
                    if(rs.next()){
                        akun=rs.getString("kd_rek");
                        kontrakun=rs.getString("kd_rek2");
                    }else{
                        akun="";
                        kontrakun="";
                    } 
                } catch (Exception e) {
                    akun="";
                    kontrakun="";
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psakun!=null){
                        psakun.close();
                    }
                }
            } catch (Exception e) {
                akun="";
                kontrakun="";
                System.out.println("Notif : "+e);
            }
            if(kontrakun.equals("")){
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan akun bayar, silahkan hubungi administrator..!!");
            }else{
                if(kontrakun.equals(Host_to_Host_Bank_Mandiri)){
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pembayaran_pihak_ke3_bankmandiri.nomor_pembayaran,6),signed)),0) from pembayaran_pihak_ke3_bankmandiri where left(pembayaran_pihak_ke3_bankmandiri.tgl_pembayaran,10)='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",kodemcm+"14"+Tanggal.getSelectedItem().toString().substring(0,10).replaceAll("-",""),6,Nomor); 
                    RekeningAtasNama.setText("");
                    KotaAtasNamaRekening.setText("");
                    NoRekening.setText("");
                    NoRekening.requestFocus();
                    DlgBayarMandiri.setLocationRelativeTo(internalFrame1);
                    DlgBayarMandiri.setVisible(true);
                }else{
                    Sequel.AutoComitFalse();
                    sukses=true;    
                    if(Sequel.menyimpantf2("pengeluaran_harian","?,?,?,?,?,?","Pengeluaran",6,new String[]{
                        Nomor.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                        KdKategori.getText(),Pengeluaran.getText(),KdPtg.getText(),Keterangan.getText()
                    })==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{akun,"Akun",Pengeluaran.getText(),"0"})==false){
                            sukses=false;
                        }
                        if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{kontrakun,"Kontra","0",Pengeluaran.getText()})==false){
                            sukses=false;
                        } 
                        if(sukses==true){
                            sukses=jur.simpanJurnal(Nomor.getText(),"U","PENGELUARAN HARIAN"+", OLEH "+akses.getkode());
                        }
                    }else{
                        sukses=false;
                    }   

                    if(sukses==true){
                        if(!nopengajuanbiaya.equals("")){
                            Sequel.queryu("update pengajuan_biaya set status='Divalidasi' where no_pengajuan='"+nopengajuanbiaya+"'");
                        }
                        Sequel.Commit();
                    }else{
                        sukses=false;
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }

                    Sequel.AutoComitTrue();
                    if(sukses==true){
                        tabMode.addRow(new Object[]{
                            Nomor.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdKategori.getText()+" "+NmKategori.getText(),NmPtg.getText(),Valid.SetAngka(Pengeluaran.getText()),Keterangan.getText(),KdKategori.getText(),KdPtg.getText()
                        });
                        emptTeks();
                        hitung();
                    }
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Pengeluaran,BtnBatal);
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
             Keterangan.requestFocus();
        }else if(Keterangan.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(Keterangan.getText().trim().equals(""))){
            if(tbResep.getSelectedRow()>-1){
                Sequel.AutoComitFalse();
                sukses=true;     

                if(Sequel.queryu2tf("delete from pengeluaran_harian where no_keluar=?",1,new String[]{
                    tbResep.getValueAt(tbResep.getSelectedRow(),0).toString()
                })==true){
                    try {
                        psakun=koneksi.prepareStatement(
                            "select kategori_pengeluaran_harian.kd_rek,kategori_pengeluaran_harian.kd_rek2 from kategori_pengeluaran_harian where kategori_pengeluaran_harian.kode_kategori=?");
                        try {
                            psakun.setString(1,KdKategori.getText());
                            rs=psakun.executeQuery();
                            if(rs.next()){
                                total=0;
                                if(rs.getString("kd_rek2").equals(Host_to_Host_Bank_Mandiri)){
                                    total=Sequel.cariIsiAngka("select metode_pembayaran_bankmandiri.biaya_transaksi from metode_pembayaran_bankmandiri inner join pembayaran_pihak_ke3_bankmandiri on pembayaran_pihak_ke3_bankmandiri.kode_metode=metode_pembayaran_bankmandiri.kode_metode where pembayaran_pihak_ke3_bankmandiri.nomor_pembayaran=?",tbResep.getValueAt(tbResep.getSelectedRow(),0).toString());
                                    Sequel.meghapus("pembayaran_pihak_ke3_bankmandiri","nomor_pembayaran",tbResep.getValueAt(tbResep.getSelectedRow(),0).toString());
                                }
                                Sequel.queryu("delete from tampjurnal");
                                if(total>0){
                                    if(Sequel.menyimpantf2("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                        Akun_Biaya_Mandiri,"BIAYA TRANSAKSI","0",total+""
                                    })==false){
                                        sukses=false;
                                    }
                                }
                                if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{rs.getString("kd_rek"),"Akun","0",Pengeluaran.getText()})==false){
                                    sukses=false;
                                }
                                if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{rs.getString("kd_rek2"),"Kontra Akun",(Valid.SetAngka(Pengeluaran.getText())+total)+"","0"})==false){
                                    sukses=false;
                                }
                                if(sukses==true){
                                    sukses=jur.simpanJurnal(Nomor.getText(),"U","PEMBATALAN PENGELUARAN HARIAN"+", OLEH "+akses.getkode());
                                }
                            } 
                        } catch (Exception e) {
                            sukses=false;
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(psakun!=null){
                                psakun.close();
                            }
                        } 
                    } catch (Exception e) {
                        sukses=false;
                        System.out.println("Notifikasi : "+e);
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
                    tabMode.removeRow(tbResep.getSelectedRow());
                    emptTeks();
                    hitung();
                }
            }                
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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptPengeluaranHarian.jasper","report","::[ Data Pengeluaran Harian ]::",
                "select pengeluaran_harian.no_keluar,pengeluaran_harian.tanggal, pengeluaran_harian.keterangan, pengeluaran_harian.biaya, pengeluaran_harian.nip, "+
                "petugas.nama,pengeluaran_harian.kode_kategori,kategori_pengeluaran_harian.nama_kategori "+
                "from pengeluaran_harian inner join petugas inner join kategori_pengeluaran_harian on pengeluaran_harian.nip=petugas.nip "+
                "and pengeluaran_harian.kode_kategori=kategori_pengeluaran_harian.kode_kategori where pengeluaran_harian.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' "+
                (TCari.getText().trim().equals("")?"":"and (pengeluaran_harian.keterangan like '%"+TCari.getText().trim()+"%' or pengeluaran_harian.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                "pengeluaran_harian.kode_kategori like '%"+TCari.getText().trim()+"%' or kategori_pengeluaran_harian.nama_kategori like '%"+TCari.getText().trim()+"%' or pengeluaran_harian.no_keluar like '%"+TCari.getText().trim()+"%') ")+
                "order by pengeluaran_harian.tanggal",param);
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

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        runBackground(() ->tampil());        
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            runBackground(() ->tampil());
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, NmPtg);
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
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbResepKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,Keterangan,Pengeluaran);
    }//GEN-LAST:event_TanggalKeyPressed

    private void PengeluaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengeluaranKeyPressed
        Valid.pindah(evt,Tanggal,BtnSimpan);
    }//GEN-LAST:event_PengeluaranKeyPressed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,KdPtg,BtnSimpan);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){        
                        KdPtg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPtg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPtg.requestFocus();
                    } 
                    petugas=null;
                }
            });

            petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            petugas.setLocationRelativeTo(internalFrame1);
        }
            
        if (petugas == null) return;
        if (!petugas.isVisible()) {
            petugas.isCek();    
            petugas.emptTeks();
        }  
        if (petugas.isVisible()) {
            petugas.toFront();
            return;
        }    
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void KdPtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPtgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPtg.setText(Sequel.CariPetugas(KdPtg.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,KdKategori,Keterangan);
        }
    }//GEN-LAST:event_KdPtgKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,KdPtg,Tanggal);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void KdKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKategoriKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKategoriActionPerformed(null);
        }else{            
            Valid.pindah(evt,TCari,KdPtg);
        }
    }//GEN-LAST:event_KdKategoriKeyPressed

    private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
        DlgCariKategoriPengeluaran kategori=new DlgCariKategoriPengeluaran(null,false);
        kategori.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kategori.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kategori.getTabel().getSelectedRow()!= -1){        
                    KdKategori.setText(kategori.getTabel().getValueAt(kategori.getTabel().getSelectedRow(),0).toString());
                    NmKategori.setText(kategori.getTabel().getValueAt(kategori.getTabel().getSelectedRow(),1).toString());
                    KdKategori.requestFocus();
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
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setVisible(true);
    }//GEN-LAST:event_btnKategoriActionPerformed

    private void btnKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKategoriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKategoriKeyPressed

    private void NomorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomorKeyPressed

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
            Sequel.AutoComitFalse();
            sukses=true;    
            if(Sequel.menyimpantf2("pengeluaran_harian","?,?,?,?,?,?","Pengeluaran",6,new String[]{
                Nomor.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                KdKategori.getText(),Pengeluaran.getText(),KdPtg.getText(),Keterangan.getText()
            })==true){
                Sequel.queryu("delete from tampjurnal");
                if(Valid.SetInteger(BiayaTransaksi.getText())>0){
                    if(Sequel.menyimpantf2("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                        Akun_Biaya_Mandiri,"BIAYA TRANSAKSI",BiayaTransaksi.getText(),"0"
                    })==false){
                        sukses=false;
                    }
                }
                if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{akun,"Akun",Pengeluaran.getText(),"0"})==false){
                    sukses=false;
                }
                if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{kontrakun,"Kontra","0",(Valid.SetAngka(BiayaTransaksi.getText())+Valid.SetAngka(Pengeluaran.getText()))+""})==false){
                    sukses=false;
                } 
                if(sukses==true){
                    sukses=jur.simpanJurnal(Nomor.getText(),"U","PENGELUARAN HARIAN"+", OLEH "+akses.getkode());
                }   
                if(sukses==true){
                    if(Sequel.menyimpantf("pembayaran_pihak_ke3_bankmandiri","?,now(),?,?,?,?,?,?,?,?,?,?,?","No.Bukti", 12,new String[]{
                            Nomor.getText(),norekening,NoRekening.getText(),RekeningAtasNama.getText(),KotaAtasNamaRekening.getText(),Pengeluaran.getText(),Nomor.getText(),KodeMetode.getText(),KodeBank.getText(),KodeTransaksi.getText(),"Pengeluaran Harian","Baru"
                        })==false){
                        sukses=false;
                    }
                }
            }else{
                sukses=false;
            }   

            if(sukses==true){
                if(!nopengajuanbiaya.equals("")){
                    Sequel.queryu("update pengajuan_biaya set status='Divalidasi' where no_pengajuan='"+nopengajuanbiaya+"'");
                }
                Sequel.Commit();
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            if(sukses==true){
                tabMode.addRow(new Object[]{
                    Nomor.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdKategori.getText()+" "+NmKategori.getText(),NmPtg.getText(),Valid.SetAngka(Pengeluaran.getText()),Keterangan.getText(),KdKategori.getText(),KdPtg.getText()
                });
                DlgBayarMandiri.dispose();
                emptTeks();
                hitung();
            }
        }
    }//GEN-LAST:event_BtnSimpanMandiriActionPerformed

    private void BtnSimpanMandiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanMandiriKeyPressed

    }//GEN-LAST:event_BtnSimpanMandiriKeyPressed

    private void BtnMetodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMetodeActionPerformed
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
    }//GEN-LAST:event_BtnMetodeActionPerformed

    private void NoRekeningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRekeningKeyPressed
        Valid.pindah(evt,BtnKeluarMandiri,KotaAtasNamaRekening);
    }//GEN-LAST:event_NoRekeningKeyPressed

    private void KotaAtasNamaRekeningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KotaAtasNamaRekeningKeyPressed
        Valid.pindah(evt,NoRekening,RekeningAtasNama);
    }//GEN-LAST:event_KotaAtasNamaRekeningKeyPressed

    private void RekeningAtasNamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RekeningAtasNamaKeyPressed
        Valid.pindah(evt,KotaAtasNamaRekening,BtnMetode);
    }//GEN-LAST:event_RekeningAtasNamaKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(Valid.daysOld("./cache/akunbankmandiri.iyem")<30){
            runBackground(() ->tampilAkunBankMandiri2());
        }else{
            runBackground(() ->tampilAkunBankMandiri());
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
            DlgPengeluaranHarian dialog = new DlgPengeluaranHarian(new javax.swing.JFrame(), true);
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
    private widget.TextBox BankTujuan;
    private widget.TextBox BiayaTransaksi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarMandiri;
    private widget.Button BtnMetode;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanMandiri;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JDialog DlgBayarMandiri;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdKategori;
    private widget.TextBox KdPtg;
    private widget.TextBox Keterangan;
    private widget.TextBox KodeBank;
    private widget.TextBox KodeMetode;
    private widget.TextBox KodeTransaksi;
    private widget.TextBox KotaAtasNamaRekening;
    private widget.Label LCount;
    private widget.TextBox MetodePembayaran;
    private widget.TextBox NmKategori;
    private widget.TextBox NmPtg;
    private widget.TextBox NoRekening;
    private widget.TextBox Nomor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pengeluaran;
    private widget.TextBox RekeningAtasNama;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnKategori;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbResep;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{     
            ps=koneksi.prepareStatement(
                    "select pengeluaran_harian.no_keluar,pengeluaran_harian.tanggal, pengeluaran_harian.keterangan, pengeluaran_harian.biaya, pengeluaran_harian.nip, "+
                    "petugas.nama,pengeluaran_harian.kode_kategori,kategori_pengeluaran_harian.nama_kategori "+
                    "from pengeluaran_harian inner join petugas inner join kategori_pengeluaran_harian on pengeluaran_harian.nip=petugas.nip "+
                    "and pengeluaran_harian.kode_kategori=kategori_pengeluaran_harian.kode_kategori where pengeluaran_harian.tanggal between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (pengeluaran_harian.keterangan like ? or pengeluaran_harian.nip like ? or petugas.nama like ? or "+
                    "pengeluaran_harian.kode_kategori like ? or kategori_pengeluaran_harian.nama_kategori like ? or pengeluaran_harian.no_keluar like ?) ")+
                    "order by pengeluaran_harian.tanggal");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().trim().equals("")){
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
                        rs.getString("no_keluar"),rs.getString("tanggal"),rs.getString("kode_kategori")+" "+rs.getString("nama_kategori"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getDouble("biaya"),rs.getString("keterangan"),
                        rs.getString("kode_kategori"),rs.getString("nip")
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
            hitung();                      
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }        
    }
    
    private void hitung(){
        total=0;
        for(i=0;i<tabMode.getRowCount();i++){
            if(!tbResep.getValueAt(i,0).toString().equals("")){
                total=total+Valid.SetAngka(tbResep.getValueAt(i,4).toString());
            }else{
                tabMode.removeRow(i);
                i--;
            }
        }
        LCount.setText(""+tabMode.getRowCount());
        if(total>0){
            tabMode.addRow(new Object[]{"",">> Total Pengeluaran :","","",total,"","",""}); 
        }        
    }

    public void emptTeks() {
        Pengeluaran.setText("0");
        Keterangan.setText("");
        KdKategori.setText("");
        NmKategori.setText("");
        Tanggal.setDate(new Date());
        KdKategori.requestFocus();
        nopengajuanbiaya="";
        autoNomor();
    }

    private void getData() {
        if(tbResep.getSelectedRow()!= -1){
            Nomor.setText(tbResep.getValueAt(tbResep.getSelectedRow(),0).toString());
            NmKategori.setText(tbResep.getValueAt(tbResep.getSelectedRow(),2).toString().replaceAll(tbResep.getValueAt(tbResep.getSelectedRow(),6).toString()+" ",""));
            NmPtg.setText(tbResep.getValueAt(tbResep.getSelectedRow(),3).toString().replaceAll(tbResep.getValueAt(tbResep.getSelectedRow(),7).toString()+" ",""));
            Pengeluaran.setText(tbResep.getValueAt(tbResep.getSelectedRow(),4).toString());
            Keterangan.setText(tbResep.getValueAt(tbResep.getSelectedRow(),5).toString());
            KdKategori.setText(tbResep.getValueAt(tbResep.getSelectedRow(),6).toString());
            KdPtg.setText(tbResep.getValueAt(tbResep.getSelectedRow(),7).toString());
            Valid.SetTgl(Tanggal,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString());
        }
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
        if(akses.getjml2()>=1){
            KdPtg.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPtg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getpengeluaran());
            BtnHapus.setEnabled(akses.getpengeluaran());
            BtnPrint.setEnabled(akses.getpengeluaran());
            NmPtg.setText(Sequel.CariPetugas(KdPtg.getText()));
        }   
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pengeluaran_harian.no_keluar,3),signed)),0) from pengeluaran_harian where pengeluaran_harian.tanggal like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"%' ",
                "PH"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,Nomor); 
    }
    
    public void setPengajuan(String nopengajuan,String keterangan,String pengeluaran){
        nopengajuanbiaya=nopengajuan;
        Nomor.setText(nopengajuan);
        Keterangan.setText(keterangan);
        Pengeluaran.setText(pengeluaran);
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
    
    private void tampilAkunBankMandiri() { 
        try{     
            ps=koneksi.prepareStatement(
                    "select set_akun_mandiri.kd_rek,set_akun_mandiri.kd_rek_biaya,set_akun_mandiri.kode_mcm,set_akun_mandiri.no_rekening from set_akun_mandiri");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    File file;
                    FileWriter fileWriter;
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
                System.out.println("Notif Nota : "+e);
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
             ObjectMapper mapper = new ObjectMapper();
             JsonNode root;
             JsonNode response;
             FileReader myObj;
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
}

/*
 * Catatan Durante Anestesi (Durante Operasi) - SIMRS Khanza (RS Asri Medika)
 * Tabel : catatan_durante_anestesi (sudah ada di database, kunci: no_rawat + tgl_perawatan + jam_rawat)
 * Hak akses : akses.getcatatan_durante_anestesi()
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author RS Asri Medika
 */
public final class RMDataCatatanDuranteAnestesi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas;
    private DlgCariDokter dokter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private String TANGGALMUNDUR="yes";
    private String finger="";
    private GrafikTandaVital grafik;
    private javax.swing.JLabel lblRingkasan;
    private static final int KOLOM_DOKTER=27;
    private static final int KOLOM_NIP=29;

    /** Creates new form RMDataCatatanDuranteAnestesi
     * @param parent
     * @param modal */
    public RMDataCatatanDuranteAnestesi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Umur","JK","Tgl.Lahir","Tgl.Obser","Jam Obser","Air","O2","N2O","Sevo","ISO","TD Sistole","TD Diastole","Nadi(x/mnt)","Resp(x/mnt)","SpO2(%)","Suhu(°C)","Kejadian","NaCl(cc)","RL(cc)","RA(cc)","Darah(cc)","Perdarahan(cc)","Urine(cc)","Keluar Lain-lain",
            "Kode Dokter","Nama Dokter","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] lebar={105,65,160,35,20,65,65,60,40,40,40,45,45,65,70,70,70,50,55,115,55,50,50,60,80,55,110,80,160,90,160};
        for (i = 0; i < lebar.length; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            column.setPreferredWidth(lebar[i]);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        Air.setDocument(new batasInput((byte)8).getKata(Air));
        O2.setDocument(new batasInput((byte)8).getKata(O2));
        N2O.setDocument(new batasInput((byte)8).getKata(N2O));
        Sevo.setDocument(new batasInput((byte)10).getKata(Sevo));
        ISO.setDocument(new batasInput((byte)10).getKata(ISO));
        Sistole.setDocument(new batasInput((byte)5).getKata(Sistole));
        Diastole.setDocument(new batasInput((byte)5).getKata(Diastole));
        Denyut.setDocument(new batasInput((byte)5).getKata(Denyut));
        Resp.setDocument(new batasInput((byte)5).getKata(Resp));
        Spo2.setDocument(new batasInput((byte)5).getKata(Spo2));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        NaCl.setDocument(new batasInput((byte)10).getKata(NaCl));
        RL.setDocument(new batasInput((byte)10).getKata(RL));
        RA.setDocument(new batasInput((byte)10).getKata(RA));
        Darah.setDocument(new batasInput((byte)10).getKata(Darah));
        Perdarahan.setDocument(new batasInput((byte)10).getKata(Perdarahan));
        Urine.setDocument(new batasInput((byte)10).getKata(Urine));
        LainKeluar.setDocument(new batasInput((byte)50).getKata(LainKeluar));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));

        // tab grafik (dibuat lewat kode supaya komponen kustom tidak merusak .form)
        grafik=new GrafikTandaVital(GrafikTandaVital.MODE_DURANTE);
        lblRingkasan=new javax.swing.JLabel(" ");
        lblRingkasan.setFont(new Font("Tahoma",0,11));
        lblRingkasan.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,8,4,8));
        javax.swing.JPanel panelGrafik=new javax.swing.JPanel(new BorderLayout(1,1));
        panelGrafik.setOpaque(false);
        panelGrafik.add(new javax.swing.JScrollPane(grafik),BorderLayout.CENTER);
        panelGrafik.add(lblRingkasan,BorderLayout.PAGE_END);
        TabData.addTab("Grafik",panelGrafik);
        TabData.addChangeListener(new javax.swing.event.ChangeListener(){
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                if(TabData.getSelectedIndex()==1){
                    tampilGrafik();
                }
            }
        });

        ChkInput.setSelected(false);
        isForm();
        jam();

        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new widget.PopupMenu();
        MnFormulirDurante = new widget.MenuItem();
        JK = new widget.TextBox();
        Umur = new widget.TextBox();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        TabData = new widget.TabPane();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
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
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel101 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel102 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel103 = new widget.Label();
        Tanggal = new widget.Tanggal();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel104 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel105 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel106 = new widget.Label();
        Air = new widget.TextBox();
        O2 = new widget.TextBox();
        N2O = new widget.TextBox();
        jLabel107 = new widget.Label();
        Sevo = new widget.TextBox();
        ISO = new widget.TextBox();
        jLabel108 = new widget.Label();
        Event = new widget.ComboBox();
        jLabel109 = new widget.Label();
        Sistole = new widget.TextBox();
        jLabel110 = new widget.Label();
        Diastole = new widget.TextBox();
        jLabel111 = new widget.Label();
        Denyut = new widget.TextBox();
        jLabel112 = new widget.Label();
        Resp = new widget.TextBox();
        jLabel113 = new widget.Label();
        Spo2 = new widget.TextBox();
        jLabel114 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel115 = new widget.Label();
        jLabel116 = new widget.Label();
        NaCl = new widget.TextBox();
        jLabel117 = new widget.Label();
        RL = new widget.TextBox();
        jLabel118 = new widget.Label();
        RA = new widget.TextBox();
        jLabel119 = new widget.Label();
        Darah = new widget.TextBox();
        jLabel120 = new widget.Label();
        jLabel121 = new widget.Label();
        jLabel122 = new widget.Label();
        Perdarahan = new widget.TextBox();
        jLabel123 = new widget.Label();
        Urine = new widget.TextBox();
        jLabel124 = new widget.Label();
        LainKeluar = new widget.TextBox();
        jLabel125 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnFormulirDurante.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulirDurante.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulirDurante.setForeground(new java.awt.Color(50, 50, 50));
        MnFormulirDurante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulirDurante.setText("Formulir Catatan Durante Anestesi");
        MnFormulirDurante.setPreferredSize(new java.awt.Dimension(280, 26));
        MnFormulirDurante.setName("MnFormulirDurante"); // NOI18N
        MnFormulirDurante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirDuranteActionPerformed(evt);
            }
        });

        jPopupMenu1.add(MnFormulirDurante);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Durante Anestesi (Durante Operasi) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setName("TabData"); // NOI18N

        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));
        Scroll.setName("Scroll"); // NOI18N

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });

        Scroll.setViewportView(tbObat);

        TabData.addTab("Data", Scroll);

        internalFrame1.add(TabData, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.setName("BtnBatal"); // NOI18N
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
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.setName("BtnHapus"); // NOI18N
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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });

        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.setName("BtnPrint"); // NOI18N
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

        jLabel7.setText("Record :");
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        jLabel7.setName("jLabel7"); // NOI18N

        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        LCount.setName("LCount"); // NOI18N

        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.setName("BtnKeluar"); // NOI18N
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

        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        jLabel19.setName("jLabel19"); // NOI18N

        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        DTPCari1.setName("DTPCari1"); // NOI18N

        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        jLabel21.setName("jLabel21"); // NOI18N

        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        DTPCari2.setName("DTPCari2"); // NOI18N

        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        jLabel6.setName("jLabel6"); // NOI18N

        panelGlass9.add(jLabel6);

        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.setName("TCari"); // NOI18N
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });

        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.setName("BtnCari"); // NOI18N
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.setName("BtnAll"); // NOI18N
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

        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 245));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setLayout(null);

        jLabel101.setText("No.Rawat :");
        jLabel101.setName("jLabel101"); // NOI18N

        FormInput.add(jLabel101);
        jLabel101.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });

        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 145, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });

        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });

        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        jLabel102.setText("Tgl.Lahir :");
        jLabel102.setName("jLabel102"); // NOI18N

        FormInput.add(jLabel102);
        jLabel102.setBounds(625, 10, 62, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N

        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel103.setText("Tanggal :");
        jLabel103.setName("jLabel103"); // NOI18N

        FormInput.add(jLabel103);
        jLabel103.setBounds(0, 40, 70, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-06-2026" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setOpaque(false);
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });

        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 40, 95, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });

        FormInput.add(Jam);
        Jam.setBounds(172, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });

        FormInput.add(Menit);
        Menit.setBounds(236, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });

        FormInput.add(Detik);
        Detik.setBounds(300, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N

        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(373, 40, 23, 23);

        jLabel104.setText("Petugas :");
        jLabel104.setName("jLabel104"); // NOI18N

        FormInput.add(jLabel104);
        jLabel104.setBounds(400, 40, 60, 23);

        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });

        FormInput.add(KdPetugas);
        KdPetugas.setBounds(462, 40, 90, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setHighlighter(null);
        NmPetugas.setName("NmPetugas"); // NOI18N

        FormInput.add(NmPetugas);
        NmPetugas.setBounds(554, 40, 200, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
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
        btnPetugas.setBounds(756, 40, 28, 23);

        jLabel105.setText("Ahli Anestesi :");
        jLabel105.setName("jLabel105"); // NOI18N

        FormInput.add(jLabel105);
        jLabel105.setBounds(0, 70, 95, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });

        FormInput.add(KdDokter);
        KdDokter.setBounds(99, 70, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N

        FormInput.add(NmDokter);
        NmDokter.setBounds(191, 70, 250, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });

        FormInput.add(BtnDokter);
        BtnDokter.setBounds(443, 70, 28, 23);

        jLabel106.setText("Air/O2/N2O :");
        jLabel106.setName("jLabel106"); // NOI18N

        FormInput.add(jLabel106);
        jLabel106.setBounds(0, 100, 95, 23);

        Air.setHighlighter(null);
        Air.setName("Air"); // NOI18N
        Air.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AirKeyPressed(evt);
            }
        });

        FormInput.add(Air);
        Air.setBounds(99, 100, 45, 23);

        O2.setHighlighter(null);
        O2.setName("O2"); // NOI18N
        O2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                O2KeyPressed(evt);
            }
        });

        FormInput.add(O2);
        O2.setBounds(146, 100, 45, 23);

        N2O.setHighlighter(null);
        N2O.setName("N2O"); // NOI18N
        N2O.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                N2OKeyPressed(evt);
            }
        });

        FormInput.add(N2O);
        N2O.setBounds(193, 100, 45, 23);

        jLabel107.setText("Sevo/ISO :");
        jLabel107.setName("jLabel107"); // NOI18N

        FormInput.add(jLabel107);
        jLabel107.setBounds(243, 100, 70, 23);

        Sevo.setHighlighter(null);
        Sevo.setName("Sevo"); // NOI18N
        Sevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SevoKeyPressed(evt);
            }
        });

        FormInput.add(Sevo);
        Sevo.setBounds(317, 100, 45, 23);

        ISO.setHighlighter(null);
        ISO.setName("ISO"); // NOI18N
        ISO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ISOKeyPressed(evt);
            }
        });

        FormInput.add(ISO);
        ISO.setBounds(364, 100, 45, 23);

        jLabel108.setText("Kejadian :");
        jLabel108.setName("jLabel108"); // NOI18N

        FormInput.add(jLabel108);
        jLabel108.setBounds(414, 100, 65, 23);

        Event.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Awal Anestesi", "Akhir Anestesi", "Awal Operasi", "Akhir Operasi", "Intubasi/Extubasi" }));
        Event.setName("Event"); // NOI18N
        Event.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EventKeyPressed(evt);
            }
        });

        FormInput.add(Event);
        Event.setBounds(483, 100, 170, 23);

        jLabel109.setText("TD (mmHg) :");
        jLabel109.setName("jLabel109"); // NOI18N

        FormInput.add(jLabel109);
        jLabel109.setBounds(0, 130, 95, 23);

        Sistole.setHighlighter(null);
        Sistole.setName("Sistole"); // NOI18N
        Sistole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistoleKeyPressed(evt);
            }
        });

        FormInput.add(Sistole);
        Sistole.setBounds(99, 130, 45, 23);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText("/");
        jLabel110.setName("jLabel110"); // NOI18N

        FormInput.add(jLabel110);
        jLabel110.setBounds(144, 130, 10, 23);

        Diastole.setHighlighter(null);
        Diastole.setName("Diastole"); // NOI18N
        Diastole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiastoleKeyPressed(evt);
            }
        });

        FormInput.add(Diastole);
        Diastole.setBounds(154, 130, 45, 23);

        jLabel111.setText("Nadi :");
        jLabel111.setName("jLabel111"); // NOI18N

        FormInput.add(jLabel111);
        jLabel111.setBounds(203, 130, 45, 23);

        Denyut.setHighlighter(null);
        Denyut.setName("Denyut"); // NOI18N
        Denyut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DenyutKeyPressed(evt);
            }
        });

        FormInput.add(Denyut);
        Denyut.setBounds(252, 130, 45, 23);

        jLabel112.setText("Resp :");
        jLabel112.setName("jLabel112"); // NOI18N

        FormInput.add(jLabel112);
        jLabel112.setBounds(300, 130, 40, 23);

        Resp.setHighlighter(null);
        Resp.setName("Resp"); // NOI18N
        Resp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespKeyPressed(evt);
            }
        });

        FormInput.add(Resp);
        Resp.setBounds(344, 130, 45, 23);

        jLabel113.setText("SpO2 :");
        jLabel113.setName("jLabel113"); // NOI18N

        FormInput.add(jLabel113);
        jLabel113.setBounds(393, 130, 40, 23);

        Spo2.setHighlighter(null);
        Spo2.setName("Spo2"); // NOI18N
        Spo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Spo2KeyPressed(evt);
            }
        });

        FormInput.add(Spo2);
        Spo2.setBounds(437, 130, 45, 23);

        jLabel114.setText("Suhu :");
        jLabel114.setName("jLabel114"); // NOI18N

        FormInput.add(jLabel114);
        jLabel114.setBounds(486, 130, 40, 23);

        Suhu.setHighlighter(null);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });

        FormInput.add(Suhu);
        Suhu.setBounds(530, 130, 45, 23);

        jLabel115.setText("Cairan Masuk :");
        jLabel115.setName("jLabel115"); // NOI18N

        FormInput.add(jLabel115);
        jLabel115.setBounds(0, 160, 95, 23);

        jLabel116.setText("NaCl");
        jLabel116.setName("jLabel116"); // NOI18N

        FormInput.add(jLabel116);
        jLabel116.setBounds(99, 160, 32, 23);

        NaCl.setHighlighter(null);
        NaCl.setName("NaCl"); // NOI18N
        NaCl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NaClKeyPressed(evt);
            }
        });

        FormInput.add(NaCl);
        NaCl.setBounds(133, 160, 55, 23);

        jLabel117.setText("RL");
        jLabel117.setName("jLabel117"); // NOI18N

        FormInput.add(jLabel117);
        jLabel117.setBounds(192, 160, 22, 23);

        RL.setHighlighter(null);
        RL.setName("RL"); // NOI18N
        RL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RLKeyPressed(evt);
            }
        });

        FormInput.add(RL);
        RL.setBounds(216, 160, 55, 23);

        jLabel118.setText("RA");
        jLabel118.setName("jLabel118"); // NOI18N

        FormInput.add(jLabel118);
        jLabel118.setBounds(275, 160, 22, 23);

        RA.setHighlighter(null);
        RA.setName("RA"); // NOI18N
        RA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RAKeyPressed(evt);
            }
        });

        FormInput.add(RA);
        RA.setBounds(299, 160, 55, 23);

        jLabel119.setText("Darah");
        jLabel119.setName("jLabel119"); // NOI18N

        FormInput.add(jLabel119);
        jLabel119.setBounds(358, 160, 38, 23);

        Darah.setHighlighter(null);
        Darah.setName("Darah"); // NOI18N
        Darah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeyPressed(evt);
            }
        });

        FormInput.add(Darah);
        Darah.setBounds(398, 160, 55, 23);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("cc");
        jLabel120.setName("jLabel120"); // NOI18N

        FormInput.add(jLabel120);
        jLabel120.setBounds(456, 160, 25, 23);

        jLabel121.setText("Cairan Keluar :");
        jLabel121.setName("jLabel121"); // NOI18N

        FormInput.add(jLabel121);
        jLabel121.setBounds(0, 190, 95, 23);

        jLabel122.setText("Perdarahan");
        jLabel122.setName("jLabel122"); // NOI18N

        FormInput.add(jLabel122);
        jLabel122.setBounds(99, 190, 70, 23);

        Perdarahan.setHighlighter(null);
        Perdarahan.setName("Perdarahan"); // NOI18N
        Perdarahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerdarahanKeyPressed(evt);
            }
        });

        FormInput.add(Perdarahan);
        Perdarahan.setBounds(171, 190, 55, 23);

        jLabel123.setText("Urine");
        jLabel123.setName("jLabel123"); // NOI18N

        FormInput.add(jLabel123);
        jLabel123.setBounds(230, 190, 38, 23);

        Urine.setHighlighter(null);
        Urine.setName("Urine"); // NOI18N
        Urine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrineKeyPressed(evt);
            }
        });

        FormInput.add(Urine);
        Urine.setBounds(270, 190, 55, 23);

        jLabel124.setText("Lain-lain");
        jLabel124.setName("jLabel124"); // NOI18N

        FormInput.add(jLabel124);
        jLabel124.setBounds(329, 190, 55, 23);

        LainKeluar.setHighlighter(null);
        LainKeluar.setName("LainKeluar"); // NOI18N
        LainKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainKeluarKeyPressed(evt);
            }
        });

        FormInput.add(LainKeluar);
        LainKeluar.setBounds(386, 190, 150, 23);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("cc");
        jLabel125.setName("jLabel125"); // NOI18N

        FormInput.add(jLabel125);
        jLabel125.setBounds(539, 190, 25, 23);

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
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setName("ChkInput"); // NOI18N
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

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,Tanggal);
        }
    }//GEN-LAST:event_TNoRwKeyPressed
    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Ahli Anestesi");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                    simpan();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed
    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,LainKeluar,BtnBatal);
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
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPetugas.getText().equals(v(KOLOM_NIP))){
                    if(Sequel.cekTanggal48jam(v(6)+" "+v(7),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnHapusActionPerformed
    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed
    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Ahli Anestesi");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(v(KOLOM_NIP))){
                        if(Sequel.cekTanggal48jam(v(6)+" "+v(7),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed
    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed
    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed
    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed
    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            isiParamRS(param);
            String tgl1=Valid.SetTgl(DTPCari1.getSelectedItem()+"");
            String tgl2=Valid.SetTgl(DTPCari2.getSelectedItem()+"");
            String cari=TCari.getText().trim();
            Valid.MyReportqry("rptDataCatatanDuranteAnestesi.jasper","report","::[ Data Catatan Durante Anestesi (Durante Operasi) ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,catatan_durante_anestesi.*,dokter.nm_dokter,petugas.nama "+
                "from catatan_durante_anestesi inner join reg_periksa on catatan_durante_anestesi.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on catatan_durante_anestesi.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on catatan_durante_anestesi.nip=petugas.nip where "+
                "catatan_durante_anestesi.tgl_perawatan between '"+tgl1+"' and '"+tgl2+"' "+
                (cari.equals("")?"":"and (reg_periksa.no_rawat like '%"+cari+"%' or pasien.no_rkm_medis like '%"+cari+"%' or pasien.nm_pasien like '%"+cari+"%' "+
                "or catatan_durante_anestesi.nip like '%"+cari+"%' or petugas.nama like '%"+cari+"%' or dokter.nm_dokter like '%"+cari+"%') ")+
                "order by catatan_durante_anestesi.no_rawat,catatan_durante_anestesi.tgl_perawatan,catatan_durante_anestesi.jam_rawat",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed
    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
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
            Valid.pindah(evt, BtnCari, TPasien);
        }
    }//GEN-LAST:event_BtnAllKeyPressed
    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
    }//GEN-LAST:event_TanggalKeyPressed
    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed
    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbObatMouseClicked
    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyPressed
    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed
    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed
    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed
    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed
    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnDokter.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_KdPetugasKeyPressed
    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }
                    KdPetugas.requestFocus();
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
    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Detik,BtnDokter);
    }//GEN-LAST:event_btnPetugasKeyPressed
    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            btnPetugas.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Air.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnDokterActionPerformed(null);
        }
    }//GEN-LAST:event_KdDokterKeyPressed
    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
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
        if (!dokter.isVisible()) {
            dokter.isCek();
            dokter.emptTeks();
        }
        if (dokter.isVisible()) {
            dokter.toFront();
            return;
        }
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed
    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,btnPetugas,Air);
    }//GEN-LAST:event_BtnDokterKeyPressed
    private void MnFormulirDuranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirDuranteActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            isiParamRS(param);
            String norawat=v(0);
            String tgl=v(6);
            isiParamFormulir(param,norawat,tgl);
            Valid.MyReportqry("rptFormulirCatatanDuranteAnestesi.jasper","report","::[ Formulir Catatan Durante Anestesi (Durante Operasi) ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,"+
                "catatan_durante_anestesi.*,dokter.nm_dokter,petugas.nama from catatan_durante_anestesi "+
                "inner join reg_periksa on catatan_durante_anestesi.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on catatan_durante_anestesi.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on catatan_durante_anestesi.nip=petugas.nip "+
                "where catatan_durante_anestesi.no_rawat='"+norawat+"' and catatan_durante_anestesi.tgl_perawatan='"+tgl+"' order by catatan_durante_anestesi.jam_rawat",param);
        }
    }//GEN-LAST:event_MnFormulirDuranteActionPerformed
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
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
    private void AirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AirKeyPressed
        Valid.pindah(evt,BtnDokter,O2);
    }//GEN-LAST:event_AirKeyPressed
    private void O2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_O2KeyPressed
        Valid.pindah(evt,Air,N2O);
    }//GEN-LAST:event_O2KeyPressed
    private void N2OKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_N2OKeyPressed
        Valid.pindah(evt,O2,Sevo);
    }//GEN-LAST:event_N2OKeyPressed
    private void SevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SevoKeyPressed
        Valid.pindah(evt,N2O,ISO);
    }//GEN-LAST:event_SevoKeyPressed
    private void ISOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ISOKeyPressed
        Valid.pindah(evt,Sevo,Sistole);
    }//GEN-LAST:event_ISOKeyPressed
    private void SistoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistoleKeyPressed
        Valid.pindah(evt,ISO,Diastole);
    }//GEN-LAST:event_SistoleKeyPressed
    private void DiastoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiastoleKeyPressed
        Valid.pindah(evt,Sistole,Denyut);
    }//GEN-LAST:event_DiastoleKeyPressed
    private void DenyutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DenyutKeyPressed
        Valid.pindah(evt,Diastole,Resp);
    }//GEN-LAST:event_DenyutKeyPressed
    private void RespKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespKeyPressed
        Valid.pindah(evt,Denyut,Spo2);
    }//GEN-LAST:event_RespKeyPressed
    private void Spo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Spo2KeyPressed
        Valid.pindah(evt,Resp,Suhu);
    }//GEN-LAST:event_Spo2KeyPressed
    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,Spo2,Event);
    }//GEN-LAST:event_SuhuKeyPressed
    private void EventKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EventKeyPressed
        Valid.pindah(evt,Suhu,NaCl);
    }//GEN-LAST:event_EventKeyPressed
    private void NaClKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NaClKeyPressed
        Valid.pindah(evt,Event,RL);
    }//GEN-LAST:event_NaClKeyPressed
    private void RLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RLKeyPressed
        Valid.pindah(evt,NaCl,RA);
    }//GEN-LAST:event_RLKeyPressed
    private void RAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RAKeyPressed
        Valid.pindah(evt,RL,Darah);
    }//GEN-LAST:event_RAKeyPressed
    private void DarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahKeyPressed
        Valid.pindah(evt,RA,Perdarahan);
    }//GEN-LAST:event_DarahKeyPressed
    private void PerdarahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerdarahanKeyPressed
        Valid.pindah(evt,Darah,Urine);
    }//GEN-LAST:event_PerdarahanKeyPressed
    private void UrineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrineKeyPressed
        Valid.pindah(evt,Perdarahan,LainKeluar);
    }//GEN-LAST:event_UrineKeyPressed
    private void LainKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainKeluarKeyPressed
        Valid.pindah(evt,Urine,BtnSimpan);
    }//GEN-LAST:event_LainKeluarKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataCatatanDuranteAnestesi dialog = new RMDataCatatanDuranteAnestesi(new javax.swing.JFrame(), true);
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
    private widget.TextBox Air;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Darah;
    private widget.TextBox Denyut;
    private widget.ComboBox Detik;
    private widget.TextBox Diastole;
    private widget.ComboBox Event;
    private widget.PanelBiasa FormInput;
    private widget.TextBox ISO;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.TextBox LainKeluar;
    private widget.ComboBox Menit;
    private widget.MenuItem MnFormulirDurante;
    private widget.TextBox N2O;
    private widget.TextBox NaCl;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugas;
    private widget.TextBox O2;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Perdarahan;
    private widget.TextBox RA;
    private widget.TextBox RL;
    private widget.TextBox Resp;
    private widget.ScrollPane Scroll;
    private widget.TextBox Sevo;
    private widget.TextBox Sistole;
    private widget.TextBox Spo2;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TabPane TabData;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox Umur;
    private widget.TextBox Urine;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.PopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    /** isi nilai sel pada baris terpilih, aman terhadap null */
    private String v(int kolom) {
        Object o=tbObat.getValueAt(tbObat.getSelectedRow(),kolom);
        return o==null?"":o.toString();
    }

    private static String nz(String s) {
        return s==null?"":s;
    }

    /** ambil bagian ke-idx dari teks bergaris miring (mis. "2/1/0" -> Air=2, O2=1, N2O=0) */
    private static String bagian(String s,int idx) {
        if(s==null) return "";
        String[] p=s.split("/",-1);
        return idx<p.length?p[idx].trim():"";
    }

    /** gabungkan beberapa nilai menjadi "a/b/c"; semua kosong -> kosong */
    private static String gabung(String... a) {
        boolean kosong=true;
        StringBuilder sb=new StringBuilder();
        for(int k=0;k<a.length;k++){
            String t=a[k]==null?"":a[k].trim().replace("/","");
            if(!t.equals("")) kosong=false;
            sb.append(k>0?"/":"").append(t);
        }
        return kosong?"":sb.toString();
    }

    private static Double angka(String s) {
        if(s==null||s.trim().equals("")) return null;
        try {
            return Double.valueOf(s.trim().replace(',','.'));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void isiParamRS(Map<String, Object> param) {
        param.put("namars",akses.getnamars());
        param.put("alamatrs",akses.getalamatrs());
        param.put("kotars",akses.getkabupatenrs());
        param.put("propinsirs",akses.getpropinsirs());
        param.put("kontakrs",akses.getkontakrs());
        param.put("emailrs",akses.getemailrs());
        param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
    }

    private String teksTTE(String nik,String nama) {
        finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",nik);
        return "Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+nama+"\nID "+(finger.equals("")?nik:finger);
    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            String cari=TCari.getText().trim();
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                "pasien.jk,pasien.tgl_lahir,catatan_durante_anestesi.*,dokter.nm_dokter,petugas.nama from catatan_durante_anestesi "+
                "inner join reg_periksa on catatan_durante_anestesi.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on catatan_durante_anestesi.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on catatan_durante_anestesi.nip=petugas.nip where "+
                "catatan_durante_anestesi.tgl_perawatan between ? and ? "+
                (cari.equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or catatan_durante_anestesi.nip like ? or petugas.nama like ? or dokter.nm_dokter like ?) ")+
                "order by catatan_durante_anestesi.tgl_perawatan,catatan_durante_anestesi.jam_rawat");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!cari.equals("")){
                    for(int k=3;k<=8;k++){
                        ps.setString(k,"%"+cari+"%");
                    }
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),rs.getDate("tgl_lahir"),
                        rs.getString("tgl_perawatan"),rs.getString("jam_rawat"),
                        bagian(rs.getString("air_o2_n2o"),0),
                        bagian(rs.getString("air_o2_n2o"),1),
                        bagian(rs.getString("air_o2_n2o"),2),
                        bagian(rs.getString("sevo_iso"),0),
                        bagian(rs.getString("sevo_iso"),1),
                        rs.getString("sistole"),
                        rs.getString("diastole"),
                        rs.getString("denyut"),
                        rs.getString("respirasi"),
                        rs.getString("spo2"),
                        rs.getString("suhu"),
                        rs.getString("keterangan_event"),
                        rs.getString("cairan_masuk_nacl"),
                        rs.getString("cairan_masuk_rl"),
                        rs.getString("cairan_masuk_ra"),
                        rs.getString("cairan_masuk_darah"),
                        rs.getString("cairan_keluar_perdarahan"),
                        rs.getString("cairan_keluar_urine"),
                        rs.getString("cairan_keluar_lain"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("nip"),rs.getString("nama")
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        Air.setText("");
        O2.setText("");
        N2O.setText("");
        Sevo.setText("");
        ISO.setText("");
        Sistole.setText("");
        Diastole.setText("");
        Denyut.setText("");
        Resp.setText("");
        Spo2.setText("");
        Suhu.setText("");
        Event.setSelectedIndex(0);
        NaCl.setText("");
        RL.setText("");
        RA.setText("");
        Darah.setText("");
        Perdarahan.setText("");
        Urine.setText("");
        LainKeluar.setText("");
        Tanggal.setDate(new Date());
        Air.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(v(0));
            TNoRM.setText(v(1));
            TPasien.setText(v(2));
            Umur.setText(v(3));
            JK.setText(v(4));
            TglLahir.setText(v(5));
            Jam.setSelectedItem(v(7).substring(0,2));
            Menit.setSelectedItem(v(7).substring(3,5));
            Detik.setSelectedItem(v(7).substring(6,8));
            Air.setText(v(8));
            O2.setText(v(9));
            N2O.setText(v(10));
            Sevo.setText(v(11));
            ISO.setText(v(12));
            Sistole.setText(v(13));
            Diastole.setText(v(14));
            Denyut.setText(v(15));
            Resp.setText(v(16));
            Spo2.setText(v(17));
            Suhu.setText(v(18));
            Event.setSelectedItem(v(19));
            NaCl.setText(v(20));
            RL.setText(v(21));
            RA.setText(v(22));
            Darah.setText(v(23));
            Perdarahan.setText(v(24));
            Urine.setText(v(25));
            LainKeluar.setText(v(26));
            KdDokter.setText(v(KOLOM_DOKTER));
            NmDokter.setText(v(KOLOM_DOKTER+1));
            Valid.SetTgl(Tanggal,v(6));
            if(TabData.getSelectedIndex()==1){
                tampilGrafik();
            }
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
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

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
        runBackground(() ->tampil());
    }

    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,245));
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
        BtnSimpan.setEnabled(akses.getcatatan_durante_anestesi());
        BtnHapus.setEnabled(akses.getcatatan_durante_anestesi());
        BtnEdit.setEnabled(akses.getcatatan_durante_anestesi());
        BtnPrint.setEnabled(akses.getcatatan_durante_anestesi());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }

        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
        }
    }

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";

                Date now = Calendar.getInstance().getTime();

                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
                }

                if (nilai_jam <= 9) {
                    nol_jam = "0";
                }
                if (nilai_menit <= 9) {
                    nol_menit = "0";
                }
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        new Timer(1000, taskPerformer).start();
    }

    /** satu baris tabel dari isian form */
    private Object[] barisBaru() {
        return new Object[]{
            TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),TglLahir.getText(),
            Valid.SetTgl(Tanggal.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            Air.getText(),
            O2.getText(),
            N2O.getText(),
            Sevo.getText(),
            ISO.getText(),
            Sistole.getText(),
            Diastole.getText(),
            Denyut.getText(),
            Resp.getText(),
            Spo2.getText(),
            Suhu.getText(),
            Event.getSelectedItem(),
            NaCl.getText(),
            RL.getText(),
            RA.getText(),
            Darah.getText(),
            Perdarahan.getText(),
            Urine.getText(),
            LainKeluar.getText(),
            KdDokter.getText(),NmDokter.getText(),KdPetugas.getText(),NmPetugas.getText()
        };
    }

    private void ganti() {
        if(Sequel.mengedittf("catatan_durante_anestesi","tgl_perawatan=? and jam_rawat=? and no_rawat=?",
            "no_rawat=?,tgl_perawatan=?,jam_rawat=?,air_o2_n2o=?,sevo_iso=?,sistole=?,diastole=?,denyut=?,respirasi=?,spo2=?,suhu=?,cairan_masuk_nacl=?,cairan_masuk_rl=?,cairan_masuk_ra=?,cairan_masuk_darah=?,cairan_keluar_perdarahan=?,cairan_keluar_urine=?,cairan_keluar_lain=?,keterangan_event=?,kd_dokter=?,nip=?",24,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                gabung(Air.getText(),O2.getText(),N2O.getText()),
                gabung(Sevo.getText(),ISO.getText()),
                Sistole.getText(),
                Diastole.getText(),
                Denyut.getText(),
                Resp.getText(),
                Spo2.getText(),
                Suhu.getText(),
                NaCl.getText(),
                RL.getText(),
                RA.getText(),
                Darah.getText(),
                Perdarahan.getText(),
                Urine.getText(),
                LainKeluar.getText(),
                Event.getSelectedItem().toString(),
            KdDokter.getText(),KdPetugas.getText(),
            v(6),v(7),v(0)
        })==true){
            Object[] b=barisBaru();
            for(int c=0;c<b.length;c++){
                tbObat.setValueAt(b[c],tbObat.getSelectedRow(),c);
            }
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from catatan_durante_anestesi where tgl_perawatan=? and jam_rawat=? and no_rawat=?",3,new String[]{
            v(6),v(7),v(0)
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("catatan_durante_anestesi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",21,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                gabung(Air.getText(),O2.getText(),N2O.getText()),
                gabung(Sevo.getText(),ISO.getText()),
                Sistole.getText(),
                Diastole.getText(),
                Denyut.getText(),
                Resp.getText(),
                Spo2.getText(),
                Suhu.getText(),
                NaCl.getText(),
                RL.getText(),
                RA.getText(),
                Darah.getText(),
                Perdarahan.getText(),
                Urine.getText(),
                LainKeluar.getText(),
                Event.getSelectedItem().toString(),
                KdDokter.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(barisBaru());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }
    }

    /** titik-titik grafik dari database untuk satu no.rawat pada satu tanggal */
    private List<GrafikTandaVital.Titik> ambilTitik(String norawat,String tgl) {
        List<GrafikTandaVital.Titik> hasil=new ArrayList<>();
        try {
            PreparedStatement ps2=koneksi.prepareStatement("select * from catatan_durante_anestesi where no_rawat=? and tgl_perawatan=? order by jam_rawat");
            try {
                ps2.setString(1,norawat);
                ps2.setString(2,tgl);
                ResultSet rs2=ps2.executeQuery();
                while(rs2.next()){
                    GrafikTandaVital.Titik t=new GrafikTandaVital.Titik(LocalDateTime.parse(rs2.getString("tgl_perawatan").substring(0,10)+"T"+rs2.getString("jam_rawat").substring(0,8)));
                    t.sistole=angka(rs2.getString("sistole"));
                    t.diastole=angka(rs2.getString("diastole"));
                    t.nadi=angka(rs2.getString("denyut"));
                    t.resp=angka(rs2.getString("respirasi"));
                    t.spo2=angka(rs2.getString("spo2"));
                    t.suhu=angka(rs2.getString("suhu"));
                    t.air=bagian(rs2.getString("air_o2_n2o"),0);
                    t.o2=bagian(rs2.getString("air_o2_n2o"),1);
                    t.n2o=bagian(rs2.getString("air_o2_n2o"),2);
                    t.sevo=bagian(rs2.getString("sevo_iso"),0);
                    t.iso=bagian(rs2.getString("sevo_iso"),1);
                    String ev=nz(rs2.getString("keterangan_event"));
                    t.kejadian=ev.equals("-")?"":ev;
                    hasil.add(t);
                }
                rs2.close();
            } finally {
                ps2.close();
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        return hasil;
    }

    private void tampilGrafik() {
        if(TNoRw.getText().trim().equals("")){
            grafik.setData(new ArrayList<GrafikTandaVital.Titik>());
            lblRingkasan.setText(" ");
            return;
        }
        String tgl=Valid.SetTgl(Tanggal.getSelectedItem()+"");
        List<GrafikTandaVital.Titik> data=ambilTitik(TNoRw.getText(),tgl);
        grafik.setMulai(mulaiAnestesi(data));
        grafik.setData(data);
        lblRingkasan.setText("<html>"+ringkasan(TNoRw.getText(),tgl).replace("\n","<br>")+"</html>");
    }

    private String skalar(String sql,String... p) {
        String hasil="";
        try {
            PreparedStatement ps3=koneksi.prepareStatement(sql);
            try {
                for(int k=0;k<p.length;k++){
                    ps3.setString(k+1,p[k]);
                }
                ResultSet rs3=ps3.executeQuery();
                if(rs3.next()){
                    hasil=nz(rs3.getString(1));
                }
                rs3.close();
            } finally {
                ps3.close();
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        return hasil;
    }

    private static String tglIndo(String tgl) {
        return tgl.length()>=10?tgl.substring(8,10)+"-"+tgl.substring(5,7)+"-"+tgl.substring(0,4):tgl;
    }
    private static LocalDateTime mulaiAnestesi(List<GrafikTandaVital.Titik> data) {
        for(GrafikTandaVital.Titik t:data){
            if(GrafikTandaVital.AWAL_ANESTESI.equals(t.kejadian)){
                return t.waktu;
            }
        }
        return null;
    }

    /** total cairan (cc) sepanjang tindakan: NaCl, RL, RA, Darah, Perdarahan, Urine */
    private String[] jumlah(String norawat,String tgl) {
        String[] kol={"cairan_masuk_nacl","cairan_masuk_rl","cairan_masuk_ra","cairan_masuk_darah","cairan_keluar_perdarahan","cairan_keluar_urine"};
        String[] hasil=new String[kol.length];
        for(int k=0;k<kol.length;k++){
            String s=skalar("select coalesce(sum(cast(replace(catatan_durante_anestesi."+kol[k]+",',','.') as decimal(12,2))),0) from catatan_durante_anestesi where no_rawat=? and tgl_perawatan=?",norawat,tgl);
            try {
                hasil[k]=new java.math.BigDecimal(s).stripTrailingZeros().toPlainString();
            } catch (Exception e) {
                hasil[k]="0";
            }
        }
        return hasil;
    }

    private String[] waktuAnestesi(String norawat,String tgl) {
        String mulai=skalar("select min(jam_rawat) from catatan_durante_anestesi where no_rawat=? and tgl_perawatan=? and keterangan_event='Awal Anestesi'",norawat,tgl);
        String selesai=skalar("select max(jam_rawat) from catatan_durante_anestesi where no_rawat=? and tgl_perawatan=? and keterangan_event='Akhir Anestesi'",norawat,tgl);
        String lama="";
        if(!mulai.equals("")&&!selesai.equals("")){
            Duration d=Duration.between(java.time.LocalTime.parse(mulai.substring(0,8)),java.time.LocalTime.parse(selesai.substring(0,8)));
            if(d.isNegative()){
                d=d.plusHours(24);
            }
            lama=(d.toHours()>0?d.toHours()+" jam ":"")+(d.toMinutes()%60)+" menit";
        }
        return new String[]{mulai.equals("")?"":mulai.substring(0,5),selesai.equals("")?"":selesai.substring(0,5),lama};
    }

    private String ringkasan(String norawat,String tgl) {
        String[] w=waktuAnestesi(norawat,tgl);
        String[] j=jumlah(norawat,tgl);
        return "Tgl. Operasi : "+tglIndo(tgl)+"   |   Mulai anestesi : "+w[0]+"   |   Selesai : "+w[1]+"   |   Lama anestesi : "+w[2]+
               "\nCairan masuk (cc) : NaCl "+j[0]+", RL "+j[1]+", RA "+j[2]+", Darah "+j[3]+
               "   |   Cairan keluar (cc) : Perdarahan "+j[4]+", Urine "+j[5];
    }

    /** parameter report formulir : gambar grafik, ringkasan, dokter + tanda tangan elektronik */
    private void isiParamFormulir(Map<String, Object> param,String norawat,String tgl) {
        List<GrafikTandaVital.Titik> data=ambilTitik(norawat,tgl);
        GrafikTandaVital g=new GrafikTandaVital(GrafikTandaVital.MODE_DURANTE);
        g.setMulai(mulaiAnestesi(data));
        g.setData(data);
        param.put("gambar",g.render(820,670));
        String[] w=waktuAnestesi(norawat,tgl);
        String[] j=jumlah(norawat,tgl);
        param.put("tgloperasi",tglIndo(tgl));
        param.put("mulai",w[0]);
        param.put("selesai",w[1]);
        param.put("lama",w[2]);
        param.put("nacl",j[0]);
        param.put("rl",j[1]);
        param.put("ra",j[2]);
        param.put("darah",j[3]);
        param.put("perdarahan",j[4]);
        param.put("urine",j[5]);
        param.put("lainlain",skalar("select group_concat(distinct catatan_durante_anestesi.cairan_keluar_lain separator ', ') from catatan_durante_anestesi where no_rawat=? and tgl_perawatan=? and catatan_durante_anestesi.cairan_keluar_lain<>''",norawat,tgl));
        String kd=skalar("select kd_dokter from catatan_durante_anestesi where no_rawat=? and tgl_perawatan=? order by jam_rawat desc limit 1",norawat,tgl);
        String nm=skalar("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",kd);
        param.put("dokter",nm);
        param.put("finger",teksTTE(kd,nm));
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

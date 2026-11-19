/*
 * Catatan Observasi Ruang Pulih - SIMRS Khanza (RS Asri Medika)
 * Tabel : catatan_observasi_ruang_pulih (sudah ada di database, kunci: no_rawat + tgl_perawatan + jam_rawat)
 * Hak akses : akses.getcatatan_observasi_ruang_pulih()
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
public final class RMDataCatatanObservasiRuangPulih extends javax.swing.JDialog {
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
    private static final int KOLOM_DOKTER=19;
    private static final int KOLOM_NIP=21;

    /** Creates new form RMDataCatatanObservasiRuangPulih
     * @param parent
     * @param modal */
    public RMDataCatatanObservasiRuangPulih(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Umur","JK","Tgl.Lahir","Tgl.Obser","Jam Obser","TD Sistole","TD Diastole","Nadi(x/mnt)","Resp(x/mnt)","Suhu(°C)","SpO2(%)","Perintah: Awasi","Kesakitan diberi","Mual/muntah diberi","Program Cairan/Obat","Perhatian/Perintah Khusus",
            "Kode Dokter","Nama Dokter","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] lebar={105,65,160,35,20,65,65,60,65,70,70,70,55,50,200,180,180,250,250,80,160,90,160};
        for (i = 0; i < lebar.length; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            column.setPreferredWidth(lebar[i]);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        Sistole.setDocument(new batasInput((byte)5).getKata(Sistole));
        Diastole.setDocument(new batasInput((byte)5).getKata(Diastole));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        Resp.setDocument(new batasInput((byte)5).getKata(Resp));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        Spo2.setDocument(new batasInput((byte)5).getKata(Spo2));
        Awasi.setDocument(new batasInput((byte)100).getKata(Awasi));
        Nyeri.setDocument(new batasInput((byte)100).getKata(Nyeri));
        Mual.setDocument(new batasInput((byte)100).getKata(Mual));
        Cairan.setDocument(new batasInput((int)300).getKata(Cairan));
        Perhatian.setDocument(new batasInput((int)300).getKata(Perhatian));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));

        // tab grafik (dibuat lewat kode supaya komponen kustom tidak merusak .form)
        grafik=new GrafikTandaVital(GrafikTandaVital.MODE_PULIH);
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
        MnFormulirRuangPulih = new widget.MenuItem();
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
        jLabel126 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel127 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel128 = new widget.Label();
        Tanggal = new widget.Tanggal();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel129 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel130 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel131 = new widget.Label();
        Sistole = new widget.TextBox();
        jLabel132 = new widget.Label();
        Diastole = new widget.TextBox();
        jLabel133 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel134 = new widget.Label();
        Resp = new widget.TextBox();
        jLabel135 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel136 = new widget.Label();
        Spo2 = new widget.TextBox();
        jLabel137 = new widget.Label();
        jLabel138 = new widget.Label();
        Awasi = new widget.TextBox();
        jLabel139 = new widget.Label();
        Nyeri = new widget.TextBox();
        jLabel140 = new widget.Label();
        Mual = new widget.TextBox();
        jLabel141 = new widget.Label();
        Cairan = new widget.TextBox();
        jLabel142 = new widget.Label();
        Perhatian = new widget.TextBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnFormulirRuangPulih.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulirRuangPulih.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulirRuangPulih.setForeground(new java.awt.Color(50, 50, 50));
        MnFormulirRuangPulih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulirRuangPulih.setText("Formulir Catatan Observasi Ruang Pulih");
        MnFormulirRuangPulih.setPreferredSize(new java.awt.Dimension(280, 26));
        MnFormulirRuangPulih.setName("MnFormulirRuangPulih"); // NOI18N
        MnFormulirRuangPulih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirRuangPulihActionPerformed(evt);
            }
        });

        jPopupMenu1.add(MnFormulirRuangPulih);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Observasi Ruang Pulih ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 320));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setPreferredSize(new java.awt.Dimension(100, 300));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setLayout(null);

        jLabel126.setText("No.Rawat :");
        jLabel126.setName("jLabel126"); // NOI18N

        FormInput.add(jLabel126);
        jLabel126.setBounds(0, 10, 70, 23);

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

        jLabel127.setText("Tgl.Lahir :");
        jLabel127.setName("jLabel127"); // NOI18N

        FormInput.add(jLabel127);
        jLabel127.setBounds(625, 10, 62, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N

        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel128.setText("Tanggal :");
        jLabel128.setName("jLabel128"); // NOI18N

        FormInput.add(jLabel128);
        jLabel128.setBounds(0, 40, 70, 23);

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

        jLabel129.setText("Petugas :");
        jLabel129.setName("jLabel129"); // NOI18N

        FormInput.add(jLabel129);
        jLabel129.setBounds(400, 40, 60, 23);

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

        jLabel130.setText("Dokter Anestesi :");
        jLabel130.setName("jLabel130"); // NOI18N

        FormInput.add(jLabel130);
        jLabel130.setBounds(0, 70, 95, 23);

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

        jLabel131.setText("TD (mmHg) :");
        jLabel131.setName("jLabel131"); // NOI18N

        FormInput.add(jLabel131);
        jLabel131.setBounds(0, 100, 95, 23);

        Sistole.setHighlighter(null);
        Sistole.setName("Sistole"); // NOI18N
        Sistole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistoleKeyPressed(evt);
            }
        });

        FormInput.add(Sistole);
        Sistole.setBounds(99, 100, 45, 23);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel132.setText("/");
        jLabel132.setName("jLabel132"); // NOI18N

        FormInput.add(jLabel132);
        jLabel132.setBounds(144, 100, 10, 23);

        Diastole.setHighlighter(null);
        Diastole.setName("Diastole"); // NOI18N
        Diastole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiastoleKeyPressed(evt);
            }
        });

        FormInput.add(Diastole);
        Diastole.setBounds(154, 100, 45, 23);

        jLabel133.setText("Nadi :");
        jLabel133.setName("jLabel133"); // NOI18N

        FormInput.add(jLabel133);
        jLabel133.setBounds(203, 100, 45, 23);

        Nadi.setHighlighter(null);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });

        FormInput.add(Nadi);
        Nadi.setBounds(252, 100, 45, 23);

        jLabel134.setText("Resp :");
        jLabel134.setName("jLabel134"); // NOI18N

        FormInput.add(jLabel134);
        jLabel134.setBounds(300, 100, 40, 23);

        Resp.setHighlighter(null);
        Resp.setName("Resp"); // NOI18N
        Resp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespKeyPressed(evt);
            }
        });

        FormInput.add(Resp);
        Resp.setBounds(344, 100, 45, 23);

        jLabel135.setText("Suhu :");
        jLabel135.setName("jLabel135"); // NOI18N

        FormInput.add(jLabel135);
        jLabel135.setBounds(393, 100, 40, 23);

        Suhu.setHighlighter(null);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });

        FormInput.add(Suhu);
        Suhu.setBounds(437, 100, 45, 23);

        jLabel136.setText("SpO2 :");
        jLabel136.setName("jLabel136"); // NOI18N

        FormInput.add(jLabel136);
        jLabel136.setBounds(486, 100, 40, 23);

        Spo2.setHighlighter(null);
        Spo2.setName("Spo2"); // NOI18N
        Spo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Spo2KeyPressed(evt);
            }
        });

        FormInput.add(Spo2);
        Spo2.setBounds(530, 100, 45, 23);

        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel137.setText("PERINTAH DI RUANGAN");
        jLabel137.setName("jLabel137"); // NOI18N

        FormInput.add(jLabel137);
        jLabel137.setBounds(0, 130, 130, 23);

        jLabel138.setText("Awasi :");
        jLabel138.setName("jLabel138"); // NOI18N

        FormInput.add(jLabel138);
        jLabel138.setBounds(0, 155, 95, 23);

        Awasi.setHighlighter(null);
        Awasi.setName("Awasi"); // NOI18N
        Awasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AwasiKeyPressed(evt);
            }
        });

        FormInput.add(Awasi);
        Awasi.setBounds(99, 155, 685, 23);

        jLabel139.setText("Kesakitan diberi :");
        jLabel139.setName("jLabel139"); // NOI18N

        FormInput.add(jLabel139);
        jLabel139.setBounds(0, 183, 95, 23);

        Nyeri.setHighlighter(null);
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });

        FormInput.add(Nyeri);
        Nyeri.setBounds(99, 183, 685, 23);

        jLabel140.setText("Mual/Muntah :");
        jLabel140.setName("jLabel140"); // NOI18N

        FormInput.add(jLabel140);
        jLabel140.setBounds(0, 211, 95, 23);

        Mual.setHighlighter(null);
        Mual.setName("Mual"); // NOI18N
        Mual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MualKeyPressed(evt);
            }
        });

        FormInput.add(Mual);
        Mual.setBounds(99, 211, 685, 23);

        jLabel141.setText("Cairan/Obat :");
        jLabel141.setName("jLabel141"); // NOI18N

        FormInput.add(jLabel141);
        jLabel141.setBounds(0, 239, 95, 23);

        Cairan.setHighlighter(null);
        Cairan.setName("Cairan"); // NOI18N
        Cairan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CairanKeyPressed(evt);
            }
        });

        FormInput.add(Cairan);
        Cairan.setBounds(99, 239, 685, 23);

        jLabel142.setText("Perhatian :");
        jLabel142.setName("jLabel142"); // NOI18N

        FormInput.add(jLabel142);
        jLabel142.setBounds(0, 267, 95, 23);

        Perhatian.setHighlighter(null);
        Perhatian.setName("Perhatian"); // NOI18N
        Perhatian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerhatianKeyPressed(evt);
            }
        });

        FormInput.add(Perhatian);
        Perhatian.setBounds(99, 267, 685, 23);

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
            Valid.textKosong(KdDokter,"Dokter Anestesi");
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
            Valid.pindah(evt,Perhatian,BtnBatal);
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
            Valid.textKosong(KdDokter,"Dokter Anestesi");
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
            Valid.MyReportqry("rptDataCatatanObservasiRuangPulih.jasper","report","::[ Data Catatan Observasi Ruang Pulih ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,catatan_observasi_ruang_pulih.*,dokter.nm_dokter,petugas.nama "+
                "from catatan_observasi_ruang_pulih inner join reg_periksa on catatan_observasi_ruang_pulih.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on catatan_observasi_ruang_pulih.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on catatan_observasi_ruang_pulih.nip=petugas.nip where "+
                "catatan_observasi_ruang_pulih.tgl_perawatan between '"+tgl1+"' and '"+tgl2+"' "+
                (cari.equals("")?"":"and (reg_periksa.no_rawat like '%"+cari+"%' or pasien.no_rkm_medis like '%"+cari+"%' or pasien.nm_pasien like '%"+cari+"%' "+
                "or catatan_observasi_ruang_pulih.nip like '%"+cari+"%' or petugas.nama like '%"+cari+"%' or dokter.nm_dokter like '%"+cari+"%') ")+
                "order by catatan_observasi_ruang_pulih.no_rawat,catatan_observasi_ruang_pulih.tgl_perawatan,catatan_observasi_ruang_pulih.jam_rawat",param);
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
            Sistole.requestFocus();
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
        Valid.pindah(evt,btnPetugas,Sistole);
    }//GEN-LAST:event_BtnDokterKeyPressed
    private void MnFormulirRuangPulihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirRuangPulihActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            isiParamRS(param);
            String norawat=v(0);
            String tgl=v(6);
            isiParamFormulir(param,norawat,tgl);
            Valid.MyReportqry("rptFormulirCatatanObservasiRuangPulih.jasper","report","::[ Formulir Catatan Observasi Ruang Pulih ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,"+
                "catatan_observasi_ruang_pulih.*,dokter.nm_dokter,petugas.nama from catatan_observasi_ruang_pulih "+
                "inner join reg_periksa on catatan_observasi_ruang_pulih.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on catatan_observasi_ruang_pulih.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on catatan_observasi_ruang_pulih.nip=petugas.nip "+
                "where catatan_observasi_ruang_pulih.no_rawat='"+norawat+"' and catatan_observasi_ruang_pulih.tgl_perawatan='"+tgl+"' order by catatan_observasi_ruang_pulih.jam_rawat",param);
        }
    }//GEN-LAST:event_MnFormulirRuangPulihActionPerformed
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
    private void SistoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistoleKeyPressed
        Valid.pindah(evt,BtnDokter,Diastole);
    }//GEN-LAST:event_SistoleKeyPressed
    private void DiastoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiastoleKeyPressed
        Valid.pindah(evt,Sistole,Nadi);
    }//GEN-LAST:event_DiastoleKeyPressed
    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,Diastole,Resp);
    }//GEN-LAST:event_NadiKeyPressed
    private void RespKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RespKeyPressed
    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,Resp,Spo2);
    }//GEN-LAST:event_SuhuKeyPressed
    private void Spo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Spo2KeyPressed
        Valid.pindah(evt,Suhu,Awasi);
    }//GEN-LAST:event_Spo2KeyPressed
    private void AwasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AwasiKeyPressed
        Valid.pindah(evt,Spo2,Nyeri);
    }//GEN-LAST:event_AwasiKeyPressed
    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,Awasi,Mual);
    }//GEN-LAST:event_NyeriKeyPressed
    private void MualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MualKeyPressed
        Valid.pindah(evt,Nyeri,Cairan);
    }//GEN-LAST:event_MualKeyPressed
    private void CairanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CairanKeyPressed
        Valid.pindah(evt,Mual,Perhatian);
    }//GEN-LAST:event_CairanKeyPressed
    private void PerhatianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerhatianKeyPressed
        Valid.pindah(evt,Cairan,BtnSimpan);
    }//GEN-LAST:event_PerhatianKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataCatatanObservasiRuangPulih dialog = new RMDataCatatanObservasiRuangPulih(new javax.swing.JFrame(), true);
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
    private widget.TextBox Awasi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox Cairan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.TextBox Diastole;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.ComboBox Menit;
    private widget.MenuItem MnFormulirRuangPulih;
    private widget.TextBox Mual;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugas;
    private widget.TextBox Nyeri;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Perhatian;
    private widget.TextBox Resp;
    private widget.ScrollPane Scroll;
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
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel138;
    private widget.Label jLabel139;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
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
                "pasien.jk,pasien.tgl_lahir,catatan_observasi_ruang_pulih.*,dokter.nm_dokter,petugas.nama from catatan_observasi_ruang_pulih "+
                "inner join reg_periksa on catatan_observasi_ruang_pulih.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on catatan_observasi_ruang_pulih.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on catatan_observasi_ruang_pulih.nip=petugas.nip where "+
                "catatan_observasi_ruang_pulih.tgl_perawatan between ? and ? "+
                (cari.equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or catatan_observasi_ruang_pulih.nip like ? or petugas.nama like ? or dokter.nm_dokter like ?) ")+
                "order by catatan_observasi_ruang_pulih.tgl_perawatan,catatan_observasi_ruang_pulih.jam_rawat");
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
                        rs.getString("sistole"),
                        rs.getString("diastole"),
                        rs.getString("nadi"),
                        rs.getString("respirasi"),
                        rs.getString("suhu"),
                        rs.getString("spo2"),
                        rs.getString("perintah_ruangan_awasi"),
                        rs.getString("perintah_ruangan_nyeri"),
                        rs.getString("perintah_ruangan_mual"),
                        rs.getString("program_cairan_obat"),
                        rs.getString("perhatian_khusus"),
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
        Sistole.setText("");
        Diastole.setText("");
        Nadi.setText("");
        Resp.setText("");
        Suhu.setText("");
        Spo2.setText("");
        Tanggal.setDate(new Date());
        Sistole.requestFocus();
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
            Sistole.setText(v(8));
            Diastole.setText(v(9));
            Nadi.setText(v(10));
            Resp.setText(v(11));
            Suhu.setText(v(12));
            Spo2.setText(v(13));
            Awasi.setText(v(14));
            Nyeri.setText(v(15));
            Mual.setText(v(16));
            Cairan.setText(v(17));
            Perhatian.setText(v(18));
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,320));
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
        BtnSimpan.setEnabled(akses.getcatatan_observasi_ruang_pulih());
        BtnHapus.setEnabled(akses.getcatatan_observasi_ruang_pulih());
        BtnEdit.setEnabled(akses.getcatatan_observasi_ruang_pulih());
        BtnPrint.setEnabled(akses.getcatatan_observasi_ruang_pulih());
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
            Sistole.getText(),
            Diastole.getText(),
            Nadi.getText(),
            Resp.getText(),
            Suhu.getText(),
            Spo2.getText(),
            Awasi.getText(),
            Nyeri.getText(),
            Mual.getText(),
            Cairan.getText(),
            Perhatian.getText(),
            KdDokter.getText(),NmDokter.getText(),KdPetugas.getText(),NmPetugas.getText()
        };
    }

    private void ganti() {
        if(Sequel.mengedittf("catatan_observasi_ruang_pulih","tgl_perawatan=? and jam_rawat=? and no_rawat=?",
            "no_rawat=?,tgl_perawatan=?,jam_rawat=?,sistole=?,diastole=?,nadi=?,respirasi=?,suhu=?,spo2=?,perintah_ruangan_awasi=?,perintah_ruangan_nyeri=?,perintah_ruangan_mual=?,program_cairan_obat=?,perhatian_khusus=?,kd_dokter=?,nip=?",19,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                Sistole.getText(),
                Diastole.getText(),
                Nadi.getText(),
                Resp.getText(),
                Suhu.getText(),
                Spo2.getText(),
                Awasi.getText(),
                Nyeri.getText(),
                Mual.getText(),
                Cairan.getText(),
                Perhatian.getText(),
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
        if(Sequel.queryu2tf("delete from catatan_observasi_ruang_pulih where tgl_perawatan=? and jam_rawat=? and no_rawat=?",3,new String[]{
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
        if(Sequel.menyimpantf("catatan_observasi_ruang_pulih","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",16,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                Sistole.getText(),
                Diastole.getText(),
                Nadi.getText(),
                Resp.getText(),
                Suhu.getText(),
                Spo2.getText(),
                Awasi.getText(),
                Nyeri.getText(),
                Mual.getText(),
                Cairan.getText(),
                Perhatian.getText(),
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
            PreparedStatement ps2=koneksi.prepareStatement("select * from catatan_observasi_ruang_pulih where no_rawat=? and tgl_perawatan=? order by jam_rawat");
            try {
                ps2.setString(1,norawat);
                ps2.setString(2,tgl);
                ResultSet rs2=ps2.executeQuery();
                while(rs2.next()){
                    GrafikTandaVital.Titik t=new GrafikTandaVital.Titik(LocalDateTime.parse(rs2.getString("tgl_perawatan").substring(0,10)+"T"+rs2.getString("jam_rawat").substring(0,8)));
                    t.sistole=angka(rs2.getString("sistole"));
                    t.diastole=angka(rs2.getString("diastole"));
                    t.nadi=angka(rs2.getString("nadi"));
                    t.resp=angka(rs2.getString("respirasi"));
                    t.spo2=angka(rs2.getString("spo2"));
                    t.suhu=angka(rs2.getString("suhu"));
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
    /** perintah di ruangan (diambil dari pencatatan terakhir pada tanggal tsb) */
    private String[] perintahTerakhir(String norawat,String tgl) {
        String[] kol={"perintah_ruangan_awasi","perintah_ruangan_nyeri","perintah_ruangan_mual","program_cairan_obat","perhatian_khusus"};
        String[] hasil=new String[kol.length];
        for(int k=0;k<kol.length;k++){
            hasil[k]=skalar("select "+kol[k]+" from catatan_observasi_ruang_pulih where no_rawat=? and tgl_perawatan=? and "+kol[k]+"<>'' order by jam_rawat desc limit 1",norawat,tgl);
        }
        return hasil;
    }

    private String ringkasan(String norawat,String tgl) {
        String[] p=perintahTerakhir(norawat,tgl);
        return "PERINTAH DI RUANGAN   Awasi : "+p[0]+"   |   Kesakitan diberi : "+p[1]+"   |   Mual/muntah diberi : "+p[2]+
               "\nProgram cairan/obat : "+p[3]+"   |   Perhatian/perintah khusus : "+p[4];
    }

    /** parameter report formulir : gambar grafik, perintah di ruangan, tanda tangan elektronik */
    private void isiParamFormulir(Map<String, Object> param,String norawat,String tgl) {
        List<GrafikTandaVital.Titik> data=ambilTitik(norawat,tgl);
        GrafikTandaVital g=new GrafikTandaVital(GrafikTandaVital.MODE_PULIH);
        g.setData(data);
        param.put("gambar",g.render(900,540));
        String[] p=perintahTerakhir(norawat,tgl);
        param.put("awasi",p[0]);
        param.put("nyeri",p[1]);
        param.put("mual",p[2]);
        param.put("cairan",p[3]);
        param.put("perhatian",p[4]);
        String kd=skalar("select kd_dokter from catatan_observasi_ruang_pulih where no_rawat=? and tgl_perawatan=? order by jam_rawat desc limit 1",norawat,tgl);
        String nm=skalar("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",kd);
        param.put("dokter",nm);
        param.put("finger",teksTTE(kd,nm));
        String nip=skalar("select nip from catatan_observasi_ruang_pulih where no_rawat=? and tgl_perawatan=? order by jam_rawat desc limit 1",norawat,tgl);
        String nmp=skalar("select petugas.nama from petugas where petugas.nip=?",nip);
        param.put("petugas",nmp);
        param.put("finger2",teksTTE(nip,nmp));
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

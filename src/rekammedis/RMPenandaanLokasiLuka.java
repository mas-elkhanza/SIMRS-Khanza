/*
 * Penandaan Lokasi Luka (RM.29.1) - SIMRS Khanza RS Asri Medika
 * Tabel : penandaan_lokasi_luka (sudah ada, kunci no_rawat + tanggal)
 *         penandaan_lokasi_luka_goresan (baru, lihat sql/01_tabel_baru.sql) : coretan spidol per gambar
 * Hak akses : akses.getpenandaan_lokasi_luka()
 * Penandaan dilakukan dengan menggambar langsung di atas gambar (mouse / stylus / layar sentuh).
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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


/**
 *
 * @author RS Asri Medika
 */
public final class RMPenandaanLokasiLuka extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private boolean sukses=true;
    private String TANGGALMUNDUR="yes";
    private String finger="";
    private PanelGambarTubuh kanvas;
    private widget.PanelBiasa PanelAccor;
    private widget.PanelBiasa FormPhoto;
    private widget.CekBox ChkAccor;
    private widget.Button btnAmbil;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSurat;
    private javax.swing.JLabel lblStatusSetuju;
    private javax.swing.JLabel lblFotoSetuju;
    private int nomorMuatFoto=0;

    /** Creates new form RMPenandaanLokasiLuka
     * @param parent
     * @param modal */
    public RMPenandaanLokasiLuka(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Umur","JK","Tgl.Lahir","Tanggal","Jam","Tindakan Bedah",
            "Pasien/Yang Mewakili","Kode Dokter","Dokter Bedah","Lokasi Yang Ditandai","Persetujuan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] lebar={105,65,160,35,20,65,70,60,200,150,80,160,260,80};
        for (i = 0; i < lebar.length; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            column.setPreferredWidth(lebar[i]);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        Tindakan.setDocument(new batasInput((int)200).getKata(Tindakan));
        Wali.setDocument(new batasInput((int)100).getKata(Wali));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));

        // tab penandaan gambar (kanvas dibuat lewat kode agar komponen kustom tidak merusak .form)
        kanvas=new PanelGambarTubuh();
        javax.swing.JPanel panelAlat=new javax.swing.JPanel(new FlowLayout(FlowLayout.LEFT,4,3));
        panelAlat.setOpaque(false);
        javax.swing.ButtonGroup grup=new javax.swing.ButtonGroup();
        javax.swing.JToggleButton btnMerah=alat("Merah",new Color(255,0,0),grup);
        javax.swing.JToggleButton btnBiru=alat("Biru",new Color(0,60,200),grup);
        javax.swing.JToggleButton btnHitam=alat("Hitam",new Color(0,0,0),grup);
        final javax.swing.JToggleButton btnHapusGoresan=new javax.swing.JToggleButton("Penghapus");
        btnHapusGoresan.setFont(new Font("Tahoma",0,11));
        grup.add(btnHapusGoresan);
        btnHapusGoresan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                kanvas.setModeHapus(true);
            }
        });
        btnMerah.setSelected(true);
        final javax.swing.JComboBox<String> cmbTebal=new javax.swing.JComboBox<>(new String[]{"Spidol halus","Spidol sedang","Spidol tebal"});
        cmbTebal.setSelectedIndex(1);
        cmbTebal.setFont(new Font("Tahoma",0,11));
        kanvas.setTebalTingkat(2);
        cmbTebal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                kanvas.setTebalTingkat(cmbTebal.getSelectedIndex()+1);
            }
        });
        javax.swing.JButton btnUndo=new javax.swing.JButton("Batalkan Coretan Terakhir");
        btnUndo.setFont(new Font("Tahoma",0,11));
        btnUndo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                kanvas.undo();
            }
        });
        javax.swing.JButton btnBersih=new javax.swing.JButton("Hapus Semua Coretan");
        btnBersih.setFont(new Font("Tahoma",0,11));
        btnBersih.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!kanvas.isKosong()&&JOptionPane.showConfirmDialog(rootPane,"Hapus semua coretan pada gambar ?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                    kanvas.hapusSemua();
                }
            }
        });
        javax.swing.JLabel lblPetunjuk=new javax.swing.JLabel("  Tandai lokasi luka dengan klik/seret mouse atau sentuh layar seperti memakai spidol.");
        lblPetunjuk.setFont(new Font("Tahoma",2,11));
        panelAlat.add(btnMerah);
        panelAlat.add(btnBiru);
        panelAlat.add(btnHitam);
        panelAlat.add(btnHapusGoresan);
        panelAlat.add(cmbTebal);
        panelAlat.add(btnUndo);
        panelAlat.add(btnBersih);
        panelAlat.add(lblPetunjuk);
        javax.swing.JPanel panelGambar=new javax.swing.JPanel(new BorderLayout(1,1));
        panelGambar.setOpaque(false);
        panelGambar.add(panelAlat,BorderLayout.PAGE_START);
        javax.swing.JScrollPane skrol=new javax.swing.JScrollPane(kanvas);
        skrol.getVerticalScrollBar().setUnitIncrement(24);
        panelGambar.add(skrol,BorderLayout.CENTER);
        TabData.addTab("Penandaan Gambar",panelGambar);
        initPanelPersetujuan();

        ChkInput.setSelected(false);
        isForm();
        jam();

        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
    }

    /** tombol warna spidol */
    private javax.swing.JToggleButton alat(String teks,final Color warna,javax.swing.ButtonGroup grup) {
        javax.swing.JToggleButton b=new javax.swing.JToggleButton(teks);
        b.setFont(new Font("Tahoma",1,11));
        b.setForeground(warna);
        grup.add(b);
        b.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                kanvas.setWarna(warna);
                kanvas.setModeHapus(false);
            }
        });
        return b;
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
        MnFormulirPenandaan = new widget.MenuItem();
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
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel105 = new widget.Label();
        Tindakan = new widget.TextBox();
        jLabel106 = new widget.Label();
        Wali = new widget.TextBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnFormulirPenandaan.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulirPenandaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulirPenandaan.setForeground(new java.awt.Color(50, 50, 50));
        MnFormulirPenandaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulirPenandaan.setText("Formulir Penandaan Lokasi Luka");
        MnFormulirPenandaan.setPreferredSize(new java.awt.Dimension(280, 26));
        MnFormulirPenandaan.setName("MnFormulirPenandaan"); // NOI18N
        MnFormulirPenandaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirPenandaanActionPerformed(evt);
            }
        });

        jPopupMenu1.add(MnFormulirPenandaan);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penandaan Lokasi Luka ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 185));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setLayout(null);

        jLabel101.setText("No.Rawat :");
        jLabel101.setName("jLabel101"); // NOI18N

        FormInput.add(jLabel101);
        jLabel101.setBounds(0, 10, 115, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });

        FormInput.add(TNoRw);
        TNoRw.setBounds(119, 10, 145, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });

        FormInput.add(TNoRM);
        TNoRM.setBounds(267, 10, 112, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });

        FormInput.add(TPasien);
        TPasien.setBounds(381, 10, 285, 23);

        jLabel102.setText("Tgl.Lahir :");
        jLabel102.setName("jLabel102"); // NOI18N

        FormInput.add(jLabel102);
        jLabel102.setBounds(670, 10, 62, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N

        FormInput.add(TglLahir);
        TglLahir.setBounds(734, 10, 100, 23);

        jLabel103.setText("Tanggal :");
        jLabel103.setName("jLabel103"); // NOI18N

        FormInput.add(jLabel103);
        jLabel103.setBounds(0, 40, 115, 23);

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
        Tanggal.setBounds(119, 40, 95, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });

        FormInput.add(Jam);
        Jam.setBounds(217, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });

        FormInput.add(Menit);
        Menit.setBounds(281, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });

        FormInput.add(Detik);
        Detik.setBounds(345, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N

        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(418, 40, 23, 23);

        jLabel104.setText("Dokter Bedah :");
        jLabel104.setName("jLabel104"); // NOI18N

        FormInput.add(jLabel104);
        jLabel104.setBounds(0, 70, 115, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });

        FormInput.add(KdDokter);
        KdDokter.setBounds(119, 70, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N

        FormInput.add(NmDokter);
        NmDokter.setBounds(211, 70, 250, 23);

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
        BtnDokter.setBounds(463, 70, 28, 23);

        jLabel105.setText("Tindakan Bedah :");
        jLabel105.setName("jLabel105"); // NOI18N

        FormInput.add(jLabel105);
        jLabel105.setBounds(0, 100, 115, 23);

        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });

        FormInput.add(Tindakan);
        Tindakan.setBounds(119, 100, 685, 23);

        jLabel106.setText("Pasien/Wali :");
        jLabel106.setName("jLabel106"); // NOI18N

        FormInput.add(jLabel106);
        jLabel106.setBounds(0, 130, 115, 23);

        Wali.setHighlighter(null);
        Wali.setName("Wali"); // NOI18N
        Wali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaliKeyPressed(evt);
            }
        });

        FormInput.add(Wali);
        Wali.setBounds(119, 130, 685, 23);

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
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter Bedah");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan Bedah");
        }else if(kanvas.isKosong()){
            JOptionPane.showMessageDialog(rootPane,"Belum ada lokasi yang ditandai pada gambar..!!");
            TabData.setSelectedIndex(1);
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),tanggalLengkap())==true){
                    simpan();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed
    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Wali,BtnBatal);
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
                if(Sequel.cekTanggal48jam(v(6)+" "+v(7),Sequel.ambiltanggalsekarang())==true){
                    hapus();
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
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter Bedah");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan Bedah");
        }else if(kanvas.isKosong()){
            JOptionPane.showMessageDialog(rootPane,"Belum ada lokasi yang ditandai pada gambar..!!");
            TabData.setSelectedIndex(1);
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(v(13).equals("Sudah")&&JOptionPane.showConfirmDialog(rootPane,"Data ini sudah memiliki bukti persetujuan pasien/wali.\nMengubah data TIDAK mengubah bukti yang lama. Jika isi penandaan berubah,\nambil ulang persetujuan lewat panah di sisi kanan form. Lanjutkan menyimpan perubahan ?","Konfirmasi",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION){
                    return;
                }
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(Sequel.cekTanggal48jam(v(6)+" "+v(7),Sequel.ambiltanggalsekarang())==true){
                        if(TanggalRegistrasi.getText().equals("")){
                            TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                        }
                        if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),tanggalLengkap())==true){
                            ganti();
                        }
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
            Valid.MyReportqry("rptDataPenandaanLokasiLuka.jasper","report","::[ Data Penandaan Lokasi Luka ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,penandaan_lokasi_luka.tanggal,"+
                "penandaan_lokasi_luka.tindakan_bedah,penandaan_lokasi_luka.nama_wali,penandaan_lokasi_luka.lokasi_gambar,"+
                "penandaan_lokasi_luka.kd_dokter,dokter.nm_dokter from penandaan_lokasi_luka "+
                "inner join reg_periksa on penandaan_lokasi_luka.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on penandaan_lokasi_luka.kd_dokter=dokter.kd_dokter where "+
                "date(penandaan_lokasi_luka.tanggal) between '"+tgl1+"' and '"+tgl2+"' "+
                (cari.equals("")?"":"and (reg_periksa.no_rawat like '%"+cari+"%' or pasien.no_rkm_medis like '%"+cari+"%' or pasien.nm_pasien like '%"+cari+"%' "+
                "or penandaan_lokasi_luka.tindakan_bedah like '%"+cari+"%' or dokter.nm_dokter like '%"+cari+"%') ")+
                "order by penandaan_lokasi_luka.tanggal",param);
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
        Valid.pindah(evt,Menit,BtnDokter);
    }//GEN-LAST:event_DetikKeyPressed
    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Tindakan.requestFocus();
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
        Valid.pindah(evt,Detik,Tindakan);
    }//GEN-LAST:event_BtnDokterKeyPressed
    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt,BtnDokter,Wali);
    }//GEN-LAST:event_TindakanKeyPressed
    private void WaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaliKeyPressed
        Valid.pindah(evt,Tindakan,BtnSimpan);
    }//GEN-LAST:event_WaliKeyPressed
    private void MnFormulirPenandaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirPenandaanActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            isiParamRS(param);
            String norawat=v(0);
            String tgl=v(6)+" "+v(7);
            PanelGambarTubuh p=new PanelGambarTubuh();
            p.setSemuaGoresan(ambilGoresan(norawat,tgl));
            param.put("gambar",p.renderKomposit(900));
            param.put("dokter",v(11));
            param.put("finger",teksTTE(v(10),v(11),v(6)+" "+v(7)));
            Valid.MyReportqry("rptFormulirPenandaanLokasiLuka.jasper","report","::[ Formulir Penandaan Lokasi Luka ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,"+
                "penandaan_lokasi_luka.tanggal,penandaan_lokasi_luka.tindakan_bedah,penandaan_lokasi_luka.nama_wali,penandaan_lokasi_luka.lokasi_gambar,"+
                "penandaan_lokasi_luka.kd_dokter,dokter.nm_dokter,"+
                "concat('http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penandaanlokasiluka/',bukti_penandaan_lokasi_luka.photo) as photo "+
                "from penandaan_lokasi_luka "+
                "inner join reg_periksa on penandaan_lokasi_luka.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on penandaan_lokasi_luka.kd_dokter=dokter.kd_dokter "+
                "left join bukti_penandaan_lokasi_luka on bukti_penandaan_lokasi_luka.no_rawat=penandaan_lokasi_luka.no_rawat "+
                "and bukti_penandaan_lokasi_luka.tanggal=penandaan_lokasi_luka.tanggal "+
                "where penandaan_lokasi_luka.no_rawat='"+norawat+"' and penandaan_lokasi_luka.tanggal='"+tgl+"'",param);
        }
    }//GEN-LAST:event_MnFormulirPenandaanActionPerformed
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
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenandaanLokasiLuka dialog = new RMPenandaanLokasiLuka(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.ComboBox Menit;
    private widget.MenuItem MnFormulirPenandaan;
    private widget.TextBox NmDokter;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TabPane TabData;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.TextBox Umur;
    private widget.TextBox Wali;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
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

    private String v(int kolom) {
        Object o=tbObat.getValueAt(tbObat.getSelectedRow(),kolom);
        return o==null?"":o.toString();
    }

    private String tanggalLengkap() {
        return Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem();
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

    /** waktu = "yyyy-MM-dd HH:mm:ss" (tanggal & jam penandaan) -> dicetak "dd-MM-yyyy HH:mm:ss" pada teks TTE. */
    private String teksTTE(String nik,String nama,String waktu) {
        finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",nik);
        String wkt="";
        if(waktu!=null&&waktu.trim().length()>=10){
            wkt="\n"+Valid.SetTgl3(waktu.trim())+(waktu.trim().length()>=19?" "+waktu.trim().substring(11,19):"");
        }
        return "Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+nama+"\nID "+(finger.equals("")?nik:finger)+wkt;
    }

    /** coretan satu penandaan : peta kode gambar -> teks goresan */
    private Map<String,String> ambilGoresan(String norawat,String tanggal) {
        Map<String,String> hasil=new HashMap<>();
        try {
            PreparedStatement ps2=koneksi.prepareStatement("select gambar,goresan from penandaan_lokasi_luka_goresan where no_rawat=? and tanggal=?");
            try {
                ps2.setString(1,norawat);
                ps2.setString(2,tanggal);
                ResultSet rs2=ps2.executeQuery();
                while(rs2.next()){
                    hasil.put(rs2.getString("gambar"),rs2.getString("goresan"));
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

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            String cari=TCari.getText().trim();
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                "pasien.jk,pasien.tgl_lahir,penandaan_lokasi_luka.tanggal,penandaan_lokasi_luka.tindakan_bedah,penandaan_lokasi_luka.nama_wali,"+
                "penandaan_lokasi_luka.kd_dokter,dokter.nm_dokter,penandaan_lokasi_luka.lokasi_gambar,"+
                "if(ifnull(bukti_penandaan_lokasi_luka.photo,'')='','Belum','Sudah') as setuju from penandaan_lokasi_luka "+
                "inner join reg_periksa on penandaan_lokasi_luka.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on penandaan_lokasi_luka.kd_dokter=dokter.kd_dokter "+
                "left join bukti_penandaan_lokasi_luka on bukti_penandaan_lokasi_luka.no_rawat=penandaan_lokasi_luka.no_rawat "+
                "and bukti_penandaan_lokasi_luka.tanggal=penandaan_lokasi_luka.tanggal where "+
                "date(penandaan_lokasi_luka.tanggal) between ? and ? "+
                (cari.equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penandaan_lokasi_luka.tindakan_bedah like ? or dokter.nm_dokter like ?) ")+
                "order by penandaan_lokasi_luka.tanggal");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!cari.equals("")){
                    for(int k=3;k<=7;k++){
                        ps.setString(k,"%"+cari+"%");
                    }
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    String tgl=rs.getString("tanggal");
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),rs.getDate("tgl_lahir"),
                        tgl.substring(0,10),tgl.substring(11,19),rs.getString("tindakan_bedah"),rs.getString("nama_wali"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("lokasi_gambar"),rs.getString("setuju")
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
        Tindakan.setText("");
        Wali.setText("");
        kanvas.hapusSemua();
        Tanggal.setDate(new Date());
        Tindakan.requestFocus();
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
            Tindakan.setText(v(8));
            Wali.setText(v(9));
            KdDokter.setText(v(10));
            NmDokter.setText(v(11));
            Valid.SetTgl(Tanggal,v(6));
            kanvas.setSemuaGoresan(ambilGoresan(v(0),v(6)+" "+v(7)));
            if(FormPhoto!=null&&FormPhoto.isVisible()){
                tampilBukti();
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
                    if(Wali.getText().trim().equals("")){
                        Wali.setText(rs.getString("nm_pasien"));
                    }
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,185));
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
        BtnSimpan.setEnabled(akses.getpenandaan_lokasi_luka());
        BtnHapus.setEnabled(akses.getpenandaan_lokasi_luka());
        BtnEdit.setEnabled(akses.getpenandaan_lokasi_luka());
        BtnPrint.setEnabled(akses.getpenandaan_lokasi_luka());
        kanvas.setBisaEdit(akses.getpenandaan_lokasi_luka());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
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

    /** simpan coretan tiap gambar yang ditandai; true bila semua berhasil */
    private boolean simpanGoresan(String norawat,String tanggal) {
        for(int k=0;k<PanelGambarTubuh.KODE.length;k++){
            if(kanvas.adaGoresan(k)){
                if(Sequel.menyimpantf2("penandaan_lokasi_luka_goresan","?,?,?,?",4,new String[]{
                    norawat,tanggal,PanelGambarTubuh.KODE[k],kanvas.getGoresan(k)
                })==false){
                    return false;
                }
            }
        }
        return true;
    }

    private Object[] barisBaru() {
        return new Object[]{
            TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),TglLahir.getText(),
            Valid.SetTgl(Tanggal.getSelectedItem()+""),Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            Tindakan.getText(),Wali.getText(),KdDokter.getText(),NmDokter.getText(),kanvas.getRingkasan(),
            statusPersetujuan(TNoRw.getText(),tanggalLengkap())
        };
    }

    // ------------------------------------------------------------------ persetujuan pasien/wali (panel samping berpanah)
    // Pola sama dengan Pelaksanaan Informasi & Edukasi: SIMRS mengisi antripenandaanlokasiluka,
    // pasien/wali membuka webapps/penandaanlokasiluka (tablet/PC berkamera), foto bukti disimpan
    // di bukti_penandaan_lokasi_luka dan ditampilkan di sini serta pada formulir cetak.
    private javax.swing.ImageIcon ikonPanah(String lokasi) {
        java.net.URL u=getClass().getResource(lokasi);
        return u==null?null:new javax.swing.ImageIcon(u);
    }

    /** Panel samping dengan panah (seperti Pelaksanaan Informasi & Edukasi): foto bukti + tombol Ambil/Refresh. */
    private void initPanelPersetujuan() {
        PanelAccor=new widget.PanelBiasa();
        PanelAccor.setBackground(new java.awt.Color(255,255,255));
        PanelAccor.setName("PanelAccor");
        PanelAccor.setLayout(new BorderLayout(1,1));

        ChkAccor=new widget.CekBox();
        ChkAccor.setBackground(new java.awt.Color(255,250,250));
        ChkAccor.setIcon(ikonPanah("/picture/kiri.png"));
        ChkAccor.setSelected(false);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor");
        ChkAccor.setToolTipText("Persetujuan pasien/wali (foto bukti)");
        ChkAccor.setPreferredSize(new java.awt.Dimension(15,20));
        ChkAccor.setRolloverIcon(ikonPanah("/picture/kiri.png"));
        ChkAccor.setRolloverSelectedIcon(ikonPanah("/picture/kanan.png"));
        ChkAccor.setSelectedIcon(ikonPanah("/picture/kanan.png"));
        ChkAccor.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ChkAccorActionPerformed();
            }
        });
        PanelAccor.add(ChkAccor,BorderLayout.WEST);

        FormPhoto=new widget.PanelBiasa();
        FormPhoto.setBackground(new java.awt.Color(255,255,255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1,1,1,1)," Bukti Persetujuan Pasien/Wali : ",javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,javax.swing.border.TitledBorder.DEFAULT_POSITION,new Font("Tahoma",0,11),new java.awt.Color(50,50,50)));
        FormPhoto.setName("FormPhoto");
        FormPhoto.setLayout(new BorderLayout());

        lblStatusSetuju=new javax.swing.JLabel("  Alur : Ambil, pasien/wali setuju di tablet, lalu Refresh");
        lblStatusSetuju.setFont(new Font("Tahoma",2,11));
        FormPhoto.add(lblStatusSetuju,BorderLayout.PAGE_START);

        lblFotoSetuju=new javax.swing.JLabel("Pilih data penandaan pada tabel",javax.swing.SwingConstants.CENTER);
        lblFotoSetuju.setFont(new Font("Tahoma",0,11));
        lblFotoSetuju.setForeground(new java.awt.Color(67,67,67));
        widget.ScrollPane skrol=new widget.ScrollPane();
        skrol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,255,255)));
        skrol.setName("ScrollBukti");
        skrol.setOpaque(true);
        skrol.setPreferredSize(new java.awt.Dimension(200,200));
        skrol.setViewportView(lblFotoSetuju);
        FormPhoto.add(skrol,BorderLayout.CENTER);

        widget.PanelBiasa tombol=new widget.PanelBiasa();
        tombol.setBackground(new java.awt.Color(255,255,255));
        tombol.setBorder(null);
        tombol.setName("FormPassBukti");
        tombol.setPreferredSize(new java.awt.Dimension(115,40));

        btnAmbil=new widget.Button();
        btnAmbil.setIcon(ikonPanah("/picture/plus_16.png"));
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Kirim data terpilih ke layar pasien/wali (webapps/penandaanlokasiluka)");
        btnAmbil.setName("btnAmbil");
        btnAmbil.setPreferredSize(new java.awt.Dimension(100,30));
        btnAmbil.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ambilPersetujuan();
            }
        });
        tombol.add(btnAmbil);

        BtnRefreshPhoto1=new widget.Button();
        BtnRefreshPhoto1.setIcon(ikonPanah("/picture/refresh.png"));
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Muat ulang foto bukti persetujuan");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1");
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100,30));
        BtnRefreshPhoto1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tbObat.getSelectedRow()>-1){
                    tampilBukti();
                }else{
                    JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data penandaan terlebih dahulu..!!");
                }
            }
        });
        tombol.add(BtnRefreshPhoto1);

        BtnSurat=new widget.Button();
        BtnSurat.setIcon(ikonPanah("/picture/item (copy).png"));
        BtnSurat.setMnemonic('U');
        BtnSurat.setText("Surat");
        BtnSurat.setToolTipText("Alt+U : cetak Surat Persetujuan Penandaan Lokasi Luka");
        BtnSurat.setName("BtnSurat");
        BtnSurat.setPreferredSize(new java.awt.Dimension(100,30));
        BtnSurat.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                cetakSurat();
            }
        });
        tombol.add(BtnSurat);

        FormPhoto.add(tombol,BorderLayout.PAGE_END);
        PanelAccor.add(FormPhoto,BorderLayout.CENTER);
        internalFrame1.add(PanelAccor,BorderLayout.EAST);

        ChkAccor.setSelected(false);
        isPhoto();
    }

    private void isPhoto() {
        ChkAccor.setVisible(false);
        if(ChkAccor.isSelected()){
            PanelAccor.setPreferredSize(new java.awt.Dimension(480,10));
            FormPhoto.setVisible(true);
        }else{
            PanelAccor.setPreferredSize(new java.awt.Dimension(15,10));
            FormPhoto.setVisible(false);
        }
        ChkAccor.setVisible(true);
        PanelAccor.revalidate();
    }

    private void ChkAccorActionPerformed() {
        if(tbObat.getSelectedRow()!=-1){
            isPhoto();
            tampilBukti();
        }else{
            ChkAccor.setSelected(false);
            isPhoto();
            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data penandaan pada tabel terlebih dahulu..!!!");
        }
    }

    /** Kirim data terpilih ke antrian layar pasien/wali. */
    private void ambilPersetujuan() {
        if(tbObat.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data penandaan pada tabel terlebih dahulu..!!");
            return;
        }
        String norawat=v(0);
        String tgl=v(6)+" "+v(7);
        if(!bacaPhoto(norawat,tgl).equals("")){
            if(JOptionPane.showConfirmDialog(rootPane,"Bukti persetujuan untuk data ini sudah ada.\nAmbil ulang dan ganti bukti yang lama ?","Konfirmasi",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION){
                return;
            }
        }
        Sequel.queryu2("delete from antripenandaanlokasiluka");
        if(Sequel.queryu2tf("insert into antripenandaanlokasiluka values(?,?)",2,new String[]{norawat,tgl})==true){
            Sequel.queryu2("delete from bukti_penandaan_lokasi_luka where no_rawat=? and tanggal=?",2,new String[]{norawat,tgl});
            tabMode.setValueAt("Belum",tbObat.getSelectedRow(),13);
            lblFotoSetuju.setIcon(null);
            lblFotoSetuju.setText("Menunggu pasien/wali menyatakan persetujuan ...");
            lblStatusSetuju.setText("  Status : data sudah dikirim ke layar pasien/wali.");
            JOptionPane.showMessageDialog(rootPane,"Data sudah dikirim ke layar pasien/wali.\nMinta pasien/wali membuka menu \"Penandaan Lokasi Luka\" di portal webapps,\nlalu setelah selesai klik Refresh pada panel ini.");
        }else{
            JOptionPane.showMessageDialog(rootPane,"Gagal mengirim data ke layar pasien/wali, pastikan tabel antripenandaanlokasiluka sudah dibuat...!!");
        }
    }

    /** Nama file foto bukti (relatif terhadap folder webapp), "" jika belum ada. */
    private String bacaPhoto(String norawat,String tgl) {
        String hasil="";
        try {
            PreparedStatement p2=koneksi.prepareStatement("select bukti_penandaan_lokasi_luka.photo from bukti_penandaan_lokasi_luka where bukti_penandaan_lokasi_luka.no_rawat=? and bukti_penandaan_lokasi_luka.tanggal=?");
            try {
                p2.setString(1,norawat);
                p2.setString(2,tgl);
                ResultSet r2=p2.executeQuery();
                if(r2.next()){
                    hasil=r2.getString(1)==null?"":r2.getString(1);
                }
                r2.close();
            } finally {
                p2.close();
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        return hasil;
    }

    /** Tanggal & jam pasien/wali menyatakan persetujuan ("dd-MM-yyyy HH:mm:ss"), "" bila belum ada / kolom belum dibuat (sql/03). */
    private String waktuPersetujuan(String norawat,String tgl) {
        String hasil="";
        try {
            PreparedStatement p2=koneksi.prepareStatement("select date_format(bukti_penandaan_lokasi_luka.waktu_persetujuan,'%d-%m-%Y %H:%i:%s') from bukti_penandaan_lokasi_luka where bukti_penandaan_lokasi_luka.no_rawat=? and bukti_penandaan_lokasi_luka.tanggal=?");
            try {
                p2.setString(1,norawat);
                p2.setString(2,tgl);
                ResultSet r2=p2.executeQuery();
                if(r2.next()){
                    hasil=r2.getString(1)==null?"":r2.getString(1);
                }
                r2.close();
            } finally {
                p2.close();
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        return hasil;
    }

    /** Tombol Surat (meniru Surat Persetujuan Penolakan Tindakan) : syarat bukti foto persetujuan sudah ada. */
    private void cetakSurat() {
        if(tbObat.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
            return;
        }
        String norawat=v(0);
        String tgl=v(6)+" "+v(7);
        String photo=bacaPhoto(norawat,tgl);
        if(photo.equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan ambil photo bukti persetujuan penandaan lokasi luka terlebih dahulu..!!!!");
            return;
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Map<String, Object> param = new HashMap<>();
        isiParamRS(param);
        PanelGambarTubuh p=new PanelGambarTubuh();
        p.setSemuaGoresan(ambilGoresan(norawat,tgl));
        param.put("gambar",p.renderKomposit(900));
        param.put("finger",teksTTE(v(10),v(11),tgl));
        param.put("photo_penerima","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penandaanlokasiluka/"+photo);
        param.put("waktu_persetujuan",waktuPersetujuan(norawat,tgl));
        Valid.MyReportqry("rptSuratPenandaanLokasiLuka.jasper","report","::[ Surat Persetujuan Penandaan Lokasi Luka ]::",
            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tmp_lahir,"+
            "concat(pasien.alamat,', ',ifnull(kelurahan.nm_kel,''),', ',ifnull(kecamatan.nm_kec,''),', ',ifnull(kabupaten.nm_kab,''),', ',ifnull(propinsi.nm_prop,'')) as alamat,"+
            "reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,penandaan_lokasi_luka.tanggal,penandaan_lokasi_luka.tindakan_bedah,"+
            "penandaan_lokasi_luka.nama_wali,penandaan_lokasi_luka.lokasi_gambar,penandaan_lokasi_luka.kd_dokter,dokter.nm_dokter "+
            "from penandaan_lokasi_luka "+
            "inner join reg_periksa on penandaan_lokasi_luka.no_rawat=reg_periksa.no_rawat "+
            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
            "inner join dokter on penandaan_lokasi_luka.kd_dokter=dokter.kd_dokter "+
            "left join kelurahan on pasien.kd_kel=kelurahan.kd_kel left join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
            "left join kabupaten on pasien.kd_kab=kabupaten.kd_kab left join propinsi on pasien.kd_prop=propinsi.kd_prop "+
            "where penandaan_lokasi_luka.no_rawat='"+norawat+"' and penandaan_lokasi_luka.tanggal='"+tgl+"'",param);
        this.setCursor(Cursor.getDefaultCursor());
    }

    private String statusPersetujuan(String norawat,String tgl) {
        return bacaPhoto(norawat,tgl).equals("")?"Belum":"Sudah";
    }

    /** Muat foto bukti untuk baris terpilih (di latar belakang agar layar tidak membeku). */
    private void tampilBukti() {
        if(!FormPhoto.isVisible()){
            return;
        }
        final int nomor=++nomorMuatFoto;
        lblFotoSetuju.setIcon(null);
        if(tbObat.getSelectedRow()==-1){
            lblFotoSetuju.setText("Pilih data penandaan pada tabel terlebih dahulu");
            return;
        }
        String photo=bacaPhoto(v(0),v(6)+" "+v(7));
        tabMode.setValueAt(photo.equals("")?"Belum":"Sudah",tbObat.getSelectedRow(),13);
        if(photo.equals("")){
            lblFotoSetuju.setText("Belum ada bukti persetujuan pasien/wali");
            lblStatusSetuju.setText("  Status : belum ada bukti persetujuan.");
            return;
        }
        final String alamat="http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penandaanlokasiluka/"+photo;
        lblFotoSetuju.setText("Memuat foto ...");
        String wktSetuju=waktuPersetujuan(v(0),v(6)+" "+v(7));
        lblStatusSetuju.setText("  Status : sudah ada bukti persetujuan"+(wktSetuju.equals("")?".":" ("+wktSetuju+")."));
        new javax.swing.SwingWorker<BufferedImage,Void>(){
            @Override
            protected BufferedImage doInBackground() throws Exception {
                java.net.URLConnection uc=new java.net.URL(alamat).openConnection();
                uc.setConnectTimeout(5000);
                uc.setReadTimeout(8000);
                uc.setUseCaches(false);
                try (java.io.InputStream in=uc.getInputStream()) {
                    return javax.imageio.ImageIO.read(in);
                }
            }

            @Override
            protected void done() {
                if(nomor!=nomorMuatFoto){
                    return;
                }
                try {
                    BufferedImage gbr=get();
                    if(gbr==null){
                        throw new Exception("bukan gambar");
                    }
                    int w=Math.min(gbr.getWidth(),430);
                    int h=(int)Math.round(gbr.getHeight()*(w/(double)gbr.getWidth()));
                    lblFotoSetuju.setText("");
                    lblFotoSetuju.setIcon(new javax.swing.ImageIcon(gbr.getScaledInstance(w,h,java.awt.Image.SCALE_SMOOTH)));
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                    lblFotoSetuju.setText("Foto tidak dapat dimuat dari server web : "+alamat);
                }
            }
        }.execute();
    }

    private void simpan() {
        Sequel.AutoComitFalse();
        sukses=true;
        String tgl=tanggalLengkap();
        if(Sequel.menyimpantf2("penandaan_lokasi_luka","?,?,?,?,?,?",6,new String[]{
            TNoRw.getText(),tgl,Tindakan.getText(),KdDokter.getText(),Wali.getText(),kanvas.getRingkasan()
        })==true){
            sukses=simpanGoresan(TNoRw.getText(),tgl);
        }else{
            sukses=false;
        }
        if(sukses==true){
            Sequel.Commit();
            tabMode.addRow(barisBaru());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            sukses=false;
            JOptionPane.showMessageDialog(null,"Gagal menyimpan, kemungkinan sudah ada data yang sama pada tanggal & jam tersebut...!!");
            Sequel.RollBack();
        }
        Sequel.AutoComitTrue();
    }

    private void ganti() {
        Sequel.AutoComitFalse();
        sukses=true;
        String tglLama=v(6)+" "+v(7);
        String tgl=tanggalLengkap();
        Sequel.queryu2("delete from penandaan_lokasi_luka_goresan where no_rawat=? and tanggal=?",2,new String[]{v(0),tglLama});
        if(Sequel.mengedittf("penandaan_lokasi_luka","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,tindakan_bedah=?,kd_dokter=?,nama_wali=?,lokasi_gambar=?",8,new String[]{
            TNoRw.getText(),tgl,Tindakan.getText(),KdDokter.getText(),Wali.getText(),kanvas.getRingkasan(),v(0),tglLama
        })==true){
            sukses=simpanGoresan(TNoRw.getText(),tgl);
        }else{
            sukses=false;
        }
        if(sukses==true){
            Sequel.Commit();
            Object[] b=barisBaru();
            for(int c=0;c<b.length;c++){
                tbObat.setValueAt(b[c],tbObat.getSelectedRow(),c);
            }
            emptTeks();
        }else{
            sukses=false;
            JOptionPane.showMessageDialog(null,"Gagal mengganti data...!!");
            Sequel.RollBack();
        }
        Sequel.AutoComitTrue();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penandaan_lokasi_luka where no_rawat=? and tanggal=?",2,new String[]{
            v(0),v(6)+" "+v(7)
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
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

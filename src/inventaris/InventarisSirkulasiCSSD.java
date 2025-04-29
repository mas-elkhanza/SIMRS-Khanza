

package inventaris;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public class InventarisSirkulasiCSSD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String aksi="";

    /** Creates new form DlgKamarInap
     * @param parent
     * @param modal */
    public InventarisSirkulasiCSSD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Sirkulasi","No.Inventaris","Kode Barang","Nama Barang","Produsen","Type/Merk","Thn.Produksi",
                "ISBN","Jenis Barang","Keterangan Diambil","NIP Pengambil","Petugas Pengambil","Tgl & Jam Ambil",
                "Keterangan Sterilisasi","NIP Penyeteril","Petugas Penyeteril","Tgl & Jam Starilisasi",
                "Keterangan Kembali","NIP Pengembali","Petugas Pengembali","Tgl & Jam Kembali"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamIn.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKamIn.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamIn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 5; i++) {
            TableColumn column = tbKamIn.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }
        }
        tbKamIn.setDefaultRenderer(Object.class, new WarnaTable());

        Keterangan.setDocument(new batasInput((byte)50).getKata(Keterangan));
        NoSirkulasi.setDocument(new batasInput((byte)13).getKata(NoSirkulasi));
        nip.setDocument(new batasInput((byte)20).getKata(nip));
        NoInventaris.setDocument(new batasInput((byte)30).getKata(NoInventaris));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
     
        
        WindowInput.setSize(735,245);
        WindowInput.setLocationRelativeTo(null);  
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    nama_petugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }   
                nip.requestFocus();
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
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
    }

    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        BtnCloseIn = new widget.Button();
        jLabel19 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        NoInventaris = new widget.TextBox();
        label1 = new widget.Label();
        Status = new widget.TextBox();
        Merk = new widget.TextBox();
        Keterangan = new widget.TextBox();
        LblTgl = new widget.Label();
        label7 = new widget.Label();
        nama_barang = new widget.TextBox();
        btnInv = new widget.Button();
        Jenis = new widget.TextBox();
        label11 = new widget.Label();
        NoSirkulasi = new widget.TextBox();
        label6 = new widget.Label();
        nip = new widget.TextBox();
        label8 = new widget.Label();
        nama_petugas = new widget.TextBox();
        btnPtg = new widget.Button();
        label12 = new widget.Label();
        Tanggal = new widget.Tanggal();
        LblTgl1 = new widget.Label();
        TOut = new widget.TextBox();
        TIn = new widget.TextBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        internalFrame1 = new widget.InternalFrame();
        PanelCariUtama = new javax.swing.JPanel();
        panelGlass10 = new widget.panelisi();
        BtnIn = new widget.Button();
        BtnSteril = new widget.Button();
        BtnOut = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass11 = new widget.panelisi();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        TglPinjam1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        TglPinjam2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbKamIn = new widget.Table();

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Transaksi Sirkulasi Barang CSSD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('U');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+U");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(620, 195, 100, 30);

        jLabel19.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame2.add(jLabel19);
        jLabel19.setBounds(-10, 175, 850, 14);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
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
        internalFrame2.add(BtnSimpan);
        BtnSimpan.setBounds(14, 195, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Batal");
        BtnBatal.setToolTipText("Alt+B");
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
        internalFrame2.add(BtnBatal);
        BtnBatal.setBounds(117, 195, 100, 30);

        NoInventaris.setName("NoInventaris"); // NOI18N
        NoInventaris.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoInventarisKeyPressed(evt);
            }
        });
        internalFrame2.add(NoInventaris);
        NoInventaris.setBounds(103, 25, 130, 23);

        label1.setText("No.Inventaris :");
        label1.setName("label1"); // NOI18N
        internalFrame2.add(label1);
        label1.setBounds(0, 25, 100, 23);

        Status.setEditable(false);
        Status.setName("Status"); // NOI18N
        internalFrame2.add(Status);
        Status.setBounds(103, 85, 150, 23);

        Merk.setEditable(false);
        Merk.setName("Merk"); // NOI18N
        internalFrame2.add(Merk);
        Merk.setBounds(425, 55, 260, 23);

        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        internalFrame2.add(Keterangan);
        Keterangan.setBounds(103, 115, 582, 23);

        LblTgl.setText("Tanggal :");
        LblTgl.setName("LblTgl"); // NOI18N
        internalFrame2.add(LblTgl);
        LblTgl.setBounds(255, 85, 70, 23);

        label7.setText("Keterangan :");
        label7.setName("label7"); // NOI18N
        internalFrame2.add(label7);
        label7.setBounds(0, 115, 100, 23);

        nama_barang.setEditable(false);
        nama_barang.setName("nama_barang"); // NOI18N
        internalFrame2.add(nama_barang);
        nama_barang.setBounds(235, 25, 450, 23);

        btnInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInv.setMnemonic('1');
        btnInv.setToolTipText("Alt+1");
        btnInv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInv.setName("btnInv"); // NOI18N
        btnInv.setPreferredSize(new java.awt.Dimension(100, 30));
        btnInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvActionPerformed(evt);
            }
        });
        internalFrame2.add(btnInv);
        btnInv.setBounds(690, 25, 25, 23);

        Jenis.setEditable(false);
        Jenis.setName("Jenis"); // NOI18N
        internalFrame2.add(Jenis);
        Jenis.setBounds(103, 55, 260, 23);

        label11.setText("Jenis :");
        label11.setName("label11"); // NOI18N
        internalFrame2.add(label11);
        label11.setBounds(0, 55, 100, 23);

        NoSirkulasi.setName("NoSirkulasi"); // NOI18N
        NoSirkulasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSirkulasiKeyPressed(evt);
            }
        });
        internalFrame2.add(NoSirkulasi);
        NoSirkulasi.setBounds(548, 85, 137, 23);

        label6.setText("Status :");
        label6.setName("label6"); // NOI18N
        internalFrame2.add(label6);
        label6.setBounds(0, 85, 100, 23);

        nip.setName("nip"); // NOI18N
        nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nipKeyPressed(evt);
            }
        });
        internalFrame2.add(nip);
        nip.setBounds(103, 145, 130, 23);

        label8.setText("Petugas :");
        label8.setName("label8"); // NOI18N
        internalFrame2.add(label8);
        label8.setBounds(0, 145, 100, 23);

        nama_petugas.setEditable(false);
        nama_petugas.setName("nama_petugas"); // NOI18N
        internalFrame2.add(nama_petugas);
        nama_petugas.setBounds(235, 145, 450, 23);

        btnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPtg.setMnemonic('3');
        btnPtg.setToolTipText("Alt+3");
        btnPtg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPtg.setName("btnPtg"); // NOI18N
        btnPtg.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPtgActionPerformed(evt);
            }
        });
        internalFrame2.add(btnPtg);
        btnPtg.setBounds(690, 145, 25, 23);

        label12.setText("Merk :");
        label12.setName("label12"); // NOI18N
        internalFrame2.add(label12);
        label12.setBounds(362, 55, 60, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-04-2025 03:55:36" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        internalFrame2.add(Tanggal);
        Tanggal.setBounds(328, 85, 130, 23);

        LblTgl1.setText("No.Sirkulasi :");
        LblTgl1.setName("LblTgl1"); // NOI18N
        internalFrame2.add(LblTgl1);
        LblTgl1.setBounds(465, 85, 80, 23);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        TOut.setEditable(false);
        TOut.setForeground(new java.awt.Color(255, 255, 255));
        TOut.setHighlighter(null);
        TOut.setName("TOut"); // NOI18N
        TOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOutKeyPressed(evt);
            }
        });

        TIn.setEditable(false);
        TIn.setForeground(new java.awt.Color(255, 255, 255));
        TIn.setHighlighter(null);
        TIn.setName("TIn"); // NOI18N
        TIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInKeyPressed(evt);
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Sirkulasi Barang CSSD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 100));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        BtnIn.setMnemonic('M');
        BtnIn.setText("Ambil");
        BtnIn.setToolTipText("Alt+M");
        BtnIn.setName("BtnIn"); // NOI18N
        BtnIn.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInActionPerformed(evt);
            }
        });
        BtnIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnInKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnIn);

        BtnSteril.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edittrash.png"))); // NOI18N
        BtnSteril.setMnemonic('M');
        BtnSteril.setText("Steril");
        BtnSteril.setToolTipText("Alt+M");
        BtnSteril.setName("BtnSteril"); // NOI18N
        BtnSteril.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSteril.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSterilActionPerformed(evt);
            }
        });
        BtnSteril.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSterilKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnSteril);

        BtnOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/36.png"))); // NOI18N
        BtnOut.setMnemonic('K');
        BtnOut.setText("Kembali");
        BtnOut.setToolTipText("Alt+K");
        BtnOut.setName("BtnOut"); // NOI18N
        BtnOut.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOutActionPerformed(evt);
            }
        });
        BtnOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOutKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnOut);

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
        panelGlass10.add(BtnHapus);

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
        panelGlass10.add(BtnPrint);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount);

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
        panelGlass10.add(BtnKeluar);

        PanelCariUtama.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Belum Kembali");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass11.add(R1);

        buttonGroup1.add(R2);
        R2.setText("Tgl.Ambil :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass11.add(R2);

        TglPinjam1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-04-2025" }));
        TglPinjam1.setDisplayFormat("dd-MM-yyyy");
        TglPinjam1.setName("TglPinjam1"); // NOI18N
        TglPinjam1.setOpaque(false);
        TglPinjam1.setPreferredSize(new java.awt.Dimension(90, 23));
        TglPinjam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPinjam1KeyPressed(evt);
            }
        });
        panelGlass11.add(TglPinjam1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass11.add(jLabel22);

        TglPinjam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-04-2025" }));
        TglPinjam2.setDisplayFormat("dd-MM-yyyy");
        TglPinjam2.setName("TglPinjam2"); // NOI18N
        TglPinjam2.setOpaque(false);
        TglPinjam2.setPreferredSize(new java.awt.Dimension(90, 23));
        TglPinjam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPinjam2KeyPressed(evt);
            }
        });
        panelGlass11.add(TglPinjam2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass11.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass11.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        panelGlass11.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
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
        panelGlass11.add(BtnAll);

        PanelCariUtama.add(panelGlass11, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamIn.setAutoCreateRowSorter(true);
        tbKamIn.setName("tbKamIn"); // NOI18N
        tbKamIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamInMouseClicked(evt);
            }
        });
        tbKamIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamInKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbKamInKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbKamIn);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInActionPerformed
        aksi="ambil";
        NoSirkulasi.setEditable(true);
        btnInv.setEnabled(true);
        emptTeks();
        WindowInput.setAlwaysOnTop(false);
        WindowInput.setVisible(true);
}//GEN-LAST:event_BtnInActionPerformed

    private void BtnInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnInActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnOut);
        }
}//GEN-LAST:event_BtnInKeyPressed

    private void BtnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOutActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Tabel kosong. Tidak ada data yang bisa Anda edit..!!!!");
            BtnIn.requestFocus();
        }else if(Merk.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data inventaris_ambil_cssd yang akan dikembalikan dengan menklik data pada tabel...!!!");
            tbKamIn.requestFocus();
        }else if(! TOut.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Inventaris sudah dikembalikan pada tanggal "+TOut.getText()+"...!!!");
            tbKamIn.requestFocus();
        }else if((! Merk.getText().trim().equals(""))&&(TOut.getText().trim().equals(""))){
            NoInventaris.setEditable(false);
            Sequel.cariIsi("select inventaris.status_barang from inventaris where inventaris.no_inventaris='"+NoInventaris.getText()+"'",Status);
            Keterangan.setEditable(false);
            NoSirkulasi.setEditable(false);
            btnInv.setEnabled(false);
            WindowInput.setAlwaysOnTop(false);
            WindowInput.setVisible(true);
        }
}//GEN-LAST:event_BtnOutActionPerformed

    private void BtnOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOutKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnOutActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnIn,BtnHapus);
        }
}//GEN-LAST:event_BtnOutKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            NoInventaris.requestFocus();
        }else if(nama_barang.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(TOut.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Data sirkulasi dengan No.Inventaris "+NoInventaris.getText()+" belum kembali. Data belum bisa anda hapus...!!!!");
        }else if(!(nama_barang.getText().trim().equals(""))){
            try{
                Sequel.queryu("delete from inventaris_ambil_cssd where peminjam='"+Keterangan.getText()+"' and no_inventaris='"+NoInventaris.getText()+"' and tgl_pinjam='"+TIn.getText()+"'");
                tampil();
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }

        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnOut,BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInput.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
                /*inventariscari="";
                tglcari="";
                
                if(ChkTanggal.isSelected()==true){
                    tglcari=" inventaris_ambil_cssd.tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and ";
                }
                
                Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptSirkulasiInventaris.jasper","report","::[ Data Sirkulasi Inventaris ]::","select inventaris_ambil_cssd.no_inventaris,"+
                           "inventaris.kode_barang,"+
                           "inventaris_barang.nama_barang,"+
                           "inventaris_produsen.nama_produsen,"+
                           "inventaris_merk.nama_merk,"+
                           "inventaris_barang.thn_produksi, "+
                           "inventaris_barang.isbn,"+
                           "inventaris_kategori.nama_kategori,"+
                           "cssd_barang.jenis_barang,"+
                           "inventaris_ambil_cssd.peminjam,"+
                           "inventaris_ambil_cssd.tlp,"+
                           "inventaris_ambil_cssd.tgl_pinjam,"+
                           "inventaris_ambil_cssd.tgl_kembali,"+
                           "petugas.nama "+
                           "from inventaris_ambil_cssd inner join inventaris inner join inventaris_barang inner join inventaris_produsen "+
                           "inner join inventaris_merk inner join inventaris_kategori inner join cssd_barang inner join petugas "+
                           "on inventaris_ambil_cssd.no_inventaris=inventaris.no_inventaris "+
                           "and inventaris_barang.kode_barang=inventaris.kode_barang "+
                           "and inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen "+
                           "and inventaris_barang.id_merk=inventaris_merk.id_merk "+
                           "and inventaris_barang.id_kategori=inventaris_kategori.id_kategori "+
                           "and inventaris_barang.id_jenis=cssd_barang.id_jenis "+
                           "and petugas.nip=inventaris_ambil_cssd.nip "+
                           "where "+inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_ambil_cssd.no_inventaris like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_barang.kode_barang like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_barang.nama_barang like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_produsen.nama_produsen like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_merk.nama_merk like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_barang.thn_produksi like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_barang.isbn like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" cssd_barang.jenis_barang like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_ambil_cssd.peminjam like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                           inventariscari+" inventaris_ambil_cssd.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" inventaris_ambil_cssd.tlp like '%"+TCari.getText().trim()+"%' "+
                           " order by inventaris_ambil_cssd.tgl_pinjam",param);*/

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
        tampil();
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
        //ChkTanggal.setSelected(false);
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnIn);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
     // Valid.pindah(evt,kdkamar,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowInput.dispose();
    }//GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal, NoInventaris);}
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoInventaris.getText().trim().equals("")||nama_barang.getText().trim().equals("")){
            Valid.textKosong(NoInventaris,"Inventaris");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Peminjam");
        }else if(nip.getText().trim().equals("")||nama_petugas.getText().trim().equals("")){
            Valid.textKosong(nip,"Petugas");
        }else {
            if(aksi.equals("ambil")){
                if(!Status.getText().trim().equals("Ada")){
                    JOptionPane.showMessageDialog(null,"Maaf, Barang "+Status.getText()+"...!!!");
                    NoInventaris.requestFocus();
                }else if(Status.getText().trim().equals("Ada")){
                    if(Sequel.menyimpantf("inventaris_ambil_cssd","'"+Keterangan.getText()+"','"+NoSirkulasi.getText()+"','"+NoInventaris.getText()+"','"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19)+"','"+nip.getText()+"'","No.Sirkulasi")==true){
                        Sequel.queryu("update inventaris set status_barang='Dipinjam' where no_inventaris='"+NoInventaris.getText()+"'");
                        NoInventaris.requestFocus();  
                        emptTeks();
                        tampil();
                    }   
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        //Valid.pindah(evt,cmbDtk,BtnBatal);
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(NoInventaris.isEditable()==true){
            emptTeks();
        }else if(NoInventaris.isEditable()==false){
            emptTeks();
            WindowInput.dispose();
        }
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void TInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TInKeyPressed

    private void DTPTglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTglItemStateChanged
        
    }//GEN-LAST:event_DTPTglItemStateChanged

    private void TglPinjam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam1KeyPressed
        //Valid.pindah(evt,InventarisCari,TglPinjam2);
}//GEN-LAST:event_TglPinjam1KeyPressed

    private void TglPinjam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam2KeyPressed
        //Valid.pindah(evt,TglPinjam1,InventarisCari);
}//GEN-LAST:event_TglPinjam2KeyPressed

    private void TOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOutKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_TOutKeyPressed

    private void tbKamInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamInMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
            
        }
}//GEN-LAST:event_tbKamInMouseClicked

    private void tbKamInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.requestFocus();
            }                    
        }
}//GEN-LAST:event_tbKamInKeyPressed

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    tampil();
}//GEN-LAST:event_formWindowOpened

private void NoInventarisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoInventarisKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        BtnCloseIn.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        isInventaris();
        Keterangan.requestFocus();
    } else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnInvActionPerformed(null);
    }
}//GEN-LAST:event_NoInventarisKeyPressed

private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,NoInventaris,NoSirkulasi);
}//GEN-LAST:event_KeteranganKeyPressed

private void btnInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvActionPerformed
    InventarisCariBarangCSSD inventaris=new InventarisCariBarangCSSD(null,false);
    inventaris.addWindowListener(new WindowListener() {
        @Override
        public void windowOpened(WindowEvent e) {}
        @Override
        public void windowClosing(WindowEvent e) {}
        @Override
        public void windowClosed(WindowEvent e) {
            if(inventaris.getTable().getSelectedRow()!= -1){                   
                NoInventaris.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),0).toString());
                nama_barang.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),1).toString()+", "+inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),2).toString());
                Merk.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),4).toString());
                Jenis.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),8).toString());
                Status.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),12).toString());                        
                NoInventaris.requestFocus();
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

    inventaris.getTable().addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                inventaris.dispose();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {}
    });
    inventaris.isCek();
    inventaris.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    inventaris.setLocationRelativeTo(internalFrame1);
    inventaris.setAlwaysOnTop(false);
    inventaris.setVisible(true);        
}//GEN-LAST:event_btnInvActionPerformed

private void nipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nipKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        nama_petugas.setText(petugas.tampil3(nip.getText()));
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        nama_petugas.setText(petugas.tampil3(nip.getText()));
        NoSirkulasi.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        nama_petugas.setText(petugas.tampil3(nip.getText()));
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnPtgActionPerformed(null);
    }
}//GEN-LAST:event_nipKeyPressed

private void btnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPtgActionPerformed
    petugas.isCek();
    petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    petugas.setLocationRelativeTo(internalFrame1);
    petugas.setAlwaysOnTop(false);
    petugas.setVisible(true);
}//GEN-LAST:event_btnPtgActionPerformed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
    //Valid.pindah(evt,InventarisCari,TglPinjam2);
}//GEN-LAST:event_TanggalKeyPressed

private void NoSirkulasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSirkulasiKeyPressed
    Valid.pindah(evt,Keterangan,nip);
}//GEN-LAST:event_NoSirkulasiKeyPressed

    private void tbKamInKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }                  
        }
    }//GEN-LAST:event_tbKamInKeyReleased

    private void BtnSterilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSterilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Tabel kosong. Tidak ada data yang bisa Anda edit..!!!!");
            BtnIn.requestFocus();
        }else if(Merk.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data barang inventaris CSSD yang akan diseterilisasi dengan menklik data pada tabel...!!!");
            tbKamIn.requestFocus();
        }else if(! TOut.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, barang CSSD sudah dikembalikan pada tanggal "+TOut.getText()+"...!!!");
            tbKamIn.requestFocus();
        }else if((! Merk.getText().trim().equals(""))&&(TOut.getText().trim().equals(""))){
            NoInventaris.setEditable(false);
            Sequel.cariIsi("select inventaris.status_barang from inventaris where inventaris.no_inventaris='"+NoInventaris.getText()+"'",Status);
            Keterangan.setEditable(false);
            NoSirkulasi.setEditable(false);
            btnInv.setEnabled(false);
            WindowInput.setAlwaysOnTop(false);
            WindowInput.setVisible(true);
        }
    }//GEN-LAST:event_BtnSterilActionPerformed

    private void BtnSterilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSterilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSterilKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventarisSirkulasiCSSD dialog = new InventarisSirkulasiCSSD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn;
    private widget.Button BtnHapus;
    private widget.Button BtnIn;
    private widget.Button BtnKeluar;
    private widget.Button BtnOut;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSteril;
    private widget.TextBox Jenis;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LblTgl;
    private widget.Label LblTgl1;
    private widget.TextBox Merk;
    private widget.TextBox NoInventaris;
    private widget.TextBox NoSirkulasi;
    private javax.swing.JPanel PanelCariUtama;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.ScrollPane Scroll;
    private widget.TextBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TIn;
    private widget.TextBox TOut;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TglPinjam1;
    private widget.Tanggal TglPinjam2;
    private javax.swing.JDialog WindowInput;
    private widget.Button btnInv;
    private widget.Button btnPtg;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel19;
    private widget.Label jLabel22;
    private widget.Label jLabel6;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label6;
    private widget.Label label7;
    private widget.Label label8;
    private widget.TextBox nama_barang;
    private widget.TextBox nama_petugas;
    private widget.TextBox nip;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.Table tbKamIn;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            /*"No.Sirkulasi","No.Inventaris","Kode Barang","Nama Barang","Produsen","Type/Merk","Thn.Produksi",
                "ISBN","Jenis Barang","Keterangan Diambil","NIP Pengambil","Petugas Pengambil","Tgl & Jam Ambil",
                "Keterangan Sterilisasi","NIP Penyeteril","Petugas Penyeteril","Tgl & Jam Starilisasi",
                "Keterangan Kembali","NIP Pengembali","Petugas Pengembali","Tgl & Jam Kembali"*/
            if(R1.isSelected()==true){
                ps=koneksi.prepareStatement(
                       "select inventaris_ambil_cssd.no_sirkulasi,inventaris_ambil_cssd.no_inventaris,inventaris.kode_barang,inventaris_barang.nama_barang,inventaris_produsen.nama_produsen,"+
                       "inventaris_merk.nama_merk,inventaris_barang.thn_produksi,inventaris_barang.isbn,cssd_barang.jenis_barang,inventaris_ambil_cssd.keterangan_ambil,inventaris_ambil_cssd.tgl_ambil,"+
                       "inventaris_ambil_cssd.nip as nippengambil,petugasambil.nama as namapengambil,inventaris_sterilisasi_cssd.keterangan_strerilisasi,inventaris_sterilisasi_cssd.tgl_sterilisasi,"+
                       "inventaris_sterilisasi_cssd.nip as nippenyeteril,petugaspenyeteril.nama as namapenyeteril from inventaris_ambil_cssd inner join cssd_barang on inventaris_ambil_cssd.no_inventaris=cssd_barang.no_inventaris "+
                       "inner join inventaris on cssd_barang.no_inventaris=inventaris.no_inventaris inner join inventaris_barang on inventaris_barang.kode_barang=inventaris.kode_barang "+
                       "inner join inventaris_produsen on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen inner join inventaris_merk on inventaris_barang.id_merk=inventaris_merk.id_merk "+
                       "inner join inventaris_kategori on inventaris_barang.id_kategori=inventaris_kategori.id_kategori inner join petugas as petugasambil on petugasambil.nip=inventaris_ambil_cssd.nip "+
                       "left join inventaris_sterilisasi_cssd on inventaris_sterilisasi_cssd.no_sirkulasi=inventaris_ambil_cssd.no_sirkulasi left join petugas as petugaspenyeteril on petugaspenyeteril.nip=inventaris_sterilisasi_cssd.nip "+
                       "where inventaris_ambil_cssd.no_sirkulasi not in (select inventaris_kembali_cssd.no_sirkulasi from inventaris_kembali_cssd) "+
                       (TCari.getText().trim().equals("")?"":" and (inventaris_ambil_cssd.no_sirkulasi like ? or inventaris_ambil_cssd.no_inventaris like ? or "+
                       "inventaris.kode_barang like ? or inventaris_barang.nama_barang like ?) ")+"order by inventaris_ambil_cssd.tgl_ambil");
                try {
                    if(!TCari.getText().trim().equals("")){
                        ps.setString(1,"%"+TCari.getText().trim()+"%");
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{
                            rs.getString("no_sirkulasi"),rs.getString("no_inventaris"),rs.getString("kode_barang"),rs.getString("nama_barang"),rs.getString("nama_produsen"),
                            rs.getString("nama_merk"),rs.getString("thn_produksi").substring(0,4),rs.getString("isbn"),rs.getString("jenis_barang"),rs.getString("keterangan_ambil"),
                            rs.getString("nippengambil"),rs.getString("namapengambil"),rs.getString("tgl_ambil"),rs.getString("keterangan_strerilisasi"),rs.getString("nippenyeteril"),
                            rs.getString("namapenyeteril"),rs.getString("tgl_sterilisasi"),"","","",""
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
            }else if(R2.isSelected()==true){
                ps=koneksi.prepareStatement(
                       "select inventaris_ambil_cssd.no_sirkulasi,inventaris_ambil_cssd.no_inventaris,inventaris.kode_barang,inventaris_barang.nama_barang,inventaris_produsen.nama_produsen,"+
                       "inventaris_merk.nama_merk,inventaris_barang.thn_produksi,inventaris_barang.isbn,cssd_barang.jenis_barang,inventaris_ambil_cssd.keterangan_ambil,inventaris_ambil_cssd.tgl_ambil,"+
                       "inventaris_ambil_cssd.nip as nippengambil,petugasambil.nama as namapengambil,inventaris_sterilisasi_cssd.keterangan_strerilisasi,inventaris_sterilisasi_cssd.tgl_sterilisasi,"+
                       "inventaris_sterilisasi_cssd.nip as nippenyeteril,petugaspenyeteril.nama as namapenyeteril from inventaris_ambil_cssd inner join cssd_barang on inventaris_ambil_cssd.no_inventaris=cssd_barang.no_inventaris "+
                       "inner join inventaris on cssd_barang.no_inventaris=inventaris.no_inventaris inner join inventaris_barang on inventaris_barang.kode_barang=inventaris.kode_barang "+
                       "inner join inventaris_produsen on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen inner join inventaris_merk on inventaris_barang.id_merk=inventaris_merk.id_merk "+
                       "inner join inventaris_kategori on inventaris_barang.id_kategori=inventaris_kategori.id_kategori left join petugas as petugasambil on petugasambil.nip=inventaris_ambil_cssd.nip "+
                       "left join inventaris_sterilisasi_cssd on inventaris_sterilisasi_cssd.no_sirkulasi=inventaris_ambil_cssd.no_sirkulasi inner join petugas as petugaspenyeteril on petugaspenyeteril.nip=inventaris_sterilisasi_cssd.nip "+
                       "where inventaris_ambil_cssd.tgl_ambil between ? and ? "+
                       (TCari.getText().trim().equals("")?"":" and (inventaris_ambil_cssd.no_sirkulasi like ? or inventaris_ambil_cssd.no_inventaris like ? or "+
                       "inventaris.kode_barang like ? or inventaris_barang.nama_barang like ?) ")+"order by inventaris_ambil_cssd.tgl_ambil");
                try {
                    ps.setString(1,Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+" 23:59:59");
                    if(!TCari.getText().trim().equals("")){
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{
                            rs.getString("no_sirkulasi"),rs.getString("no_inventaris"),rs.getString("kode_barang"),rs.getString("nama_barang"),rs.getString("nama_produsen"),
                            rs.getString("nama_merk"),rs.getString("thn_produksi").substring(0,4),rs.getString("isbn"),rs.getString("jenis_barang"),rs.getString("keterangan_ambil"),
                            rs.getString("nippengambil"),rs.getString("namapengambil"),rs.getString("tgl_ambil")
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
            }  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {       
        NoInventaris.setText("");
        nama_barang.setText("");
        Jenis.setText("");
        Merk.setText("");
        Status.setText("");
        Keterangan.setText("");
        NoSirkulasi.setText("");
        Tanggal.setDate(new Date());
        NoInventaris.requestFocus();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(inventaris_ambil_cssd.no_sirkulasi,3),signed)),0) from inventaris_ambil_cssd where DATE_FORMAT(inventaris_ambil_cssd.tgl_ambil, '%Y-%m-%d')='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "AC"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoSirkulasi);
    }

    private void getData() {
        TOut.setText("");
        TIn.setText("");
        if(tbKamIn.getSelectedRow()!= -1){
            NoSirkulasi.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString());
            NoInventaris.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),1).toString());
            nama_barang.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),2).toString()+", "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),3).toString());
            Jenis.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString());
            Merk.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString());
            //TIn.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),11).toString());
            TOut.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),20).toString());            
        }
    }

    
    public void isCek(){
        if(akses.getjml2()>=1){
            nip.setEditable(false);
            btnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.getsirkulasi_cssd());
            BtnIn.setEnabled(akses.getsirkulasi_cssd());
            BtnOut.setEnabled(akses.getsirkulasi_cssd());
            BtnSteril.setEnabled(akses.getsirkulasi_cssd());
            nip.setText(akses.getkode());
            nama_petugas.setText(petugas.tampil3(nip.getText()));
        } 
    }
    
    public void isInventaris(){
        try {
            ps=koneksi.prepareStatement(
               "select inventaris.no_inventaris,inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
               "inventaris_merk.nama_merk,cssd_barang.jenis_barang,inventaris.status_barang "+
               "from inventaris inner join inventaris_barang inner join cssd_barang inner join inventaris_merk "+
               "on inventaris_barang.id_merk=inventaris_merk.id_merk and inventaris_barang.id_jenis=cssd_barang.id_jenis "+
               "and inventaris_barang.kode_barang=inventaris.kode_barang where inventaris.no_inventaris=?");
            try{
                ps.setString(1,NoInventaris.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    nama_barang.setText(rs.getString("kode_barang")+", "+rs.getString("nama_barang"));
                    Merk.setText(rs.getString("nama_merk"));
                    Jenis.setText(rs.getString("jenis_barang"));
                    Status.setText(rs.getString("status_barang"));
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
        } catch (SQLException ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
}

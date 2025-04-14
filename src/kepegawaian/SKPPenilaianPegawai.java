/*
  Dilarang keras memperjualbelikan/mengambil keuntungan dari Software 
  ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package kepegawaian;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import fungsi.sekuel;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SKPPenilaianPegawai extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private sekuel Sequel=new sekuel();
    private int jml=0,i=0,row=0,index=0;
    private String[] KodeKriteria,Kriteria;
    private Boolean[] Ya,Tidak;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private DlgCariSKPKategoriPenilaian kategori=new DlgCariSKPKategoriPenilaian(null,false);
    private boolean sukses=true;
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public SKPPenilaianPegawai(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] judul={"Kode Kriteria","Kriteria","Ya","Tidak"};
        tabMode=new DefaultTableModel(null,judul){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==2)||(colIndex==3)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.Boolean.class,java.lang.Boolean.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(570);
            }else if(i==2){
                column.setPreferredWidth(24);
            }else if(i==3){
                column.setPreferredWidth(37);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoPenilaian.setDocument(new batasInput((byte)20).getKata(NoPenilaian));    
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
            });
        }
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){    
                    if(i==1){
                        KdPenilai.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                        NmPenilai.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                    }else if(i==2){
                        KdDInilai.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                        NmDinilai.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                    }
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
        
        pegawai.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pegawai.dispose();
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
                if(kategori.getTable().getSelectedRow()!= -1){
                    KdKategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),0).toString());
                    NmKategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),1).toString());
                    btnKategori.requestFocus();
                    tampil2();
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
        
        kategori.getTable().addKeyListener(new KeyListener() {
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
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoPenilaian = new widget.TextBox();
        label11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        label13 = new widget.Label();
        KdPenilai = new widget.TextBox();
        NmPenilai = new widget.TextBox();
        btnPenilai = new widget.Button();
        label17 = new widget.Label();
        KdDInilai = new widget.TextBox();
        NmDinilai = new widget.TextBox();
        btnDinilai = new widget.Button();
        label16 = new widget.Label();
        Keterangan = new widget.TextBox();
        jPanel1 = new javax.swing.JPanel();
        panelisi4 = new widget.panelisi();
        jLabel9 = new widget.Label();
        KdKategori = new widget.TextBox();
        btnKategori = new widget.Button();
        NmKategori = new widget.TextBox();
        jLabel10 = new widget.Label();
        Sasaran = new widget.ComboBox();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(180, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Petugas/Dokter Dalam Implementasi Sasaran Keselamatan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan pilih penilaian Ya / Tidak");
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label15.setText("No.Penilaian :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 82, 23);

        NoPenilaian.setName("NoPenilaian"); // NOI18N
        NoPenilaian.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPenilaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPenilaianKeyPressed(evt);
            }
        });
        panelisi3.add(NoPenilaian);
        NoPenilaian.setBounds(86, 10, 130, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 82, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
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
        panelisi3.add(Tanggal);
        Tanggal.setBounds(86, 40, 130, 23);

        label13.setText("Yang Menilai :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(236, 10, 90, 23);

        KdPenilai.setEditable(false);
        KdPenilai.setName("KdPenilai"); // NOI18N
        KdPenilai.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPenilai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPenilaiKeyPressed(evt);
            }
        });
        panelisi3.add(KdPenilai);
        KdPenilai.setBounds(330, 10, 130, 23);

        NmPenilai.setEditable(false);
        NmPenilai.setName("NmPenilai"); // NOI18N
        NmPenilai.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmPenilai);
        NmPenilai.setBounds(462, 10, 224, 23);

        btnPenilai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenilai.setMnemonic('2');
        btnPenilai.setToolTipText("Alt+2");
        btnPenilai.setName("btnPenilai"); // NOI18N
        btnPenilai.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPenilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenilaiActionPerformed(evt);
            }
        });
        panelisi3.add(btnPenilai);
        btnPenilai.setBounds(688, 10, 28, 23);

        label17.setText("Yang Dinilai :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label17);
        label17.setBounds(236, 40, 90, 23);

        KdDInilai.setEditable(false);
        KdDInilai.setName("KdDInilai"); // NOI18N
        KdDInilai.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDInilai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDInilaiKeyPressed(evt);
            }
        });
        panelisi3.add(KdDInilai);
        KdDInilai.setBounds(330, 40, 130, 23);

        NmDinilai.setEditable(false);
        NmDinilai.setName("NmDinilai"); // NOI18N
        NmDinilai.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmDinilai);
        NmDinilai.setBounds(462, 40, 224, 23);

        btnDinilai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDinilai.setMnemonic('2');
        btnDinilai.setToolTipText("Alt+2");
        btnDinilai.setName("btnDinilai"); // NOI18N
        btnDinilai.setPreferredSize(new java.awt.Dimension(28, 23));
        btnDinilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDinilaiActionPerformed(evt);
            }
        });
        panelisi3.add(btnDinilai);
        btnDinilai.setBounds(688, 40, 28, 23);

        label16.setText("Keterangan :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label16);
        label16.setBounds(0, 70, 82, 23);

        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.setPreferredSize(new java.awt.Dimension(207, 23));
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi3.add(Keterangan);
        Keterangan.setBounds(86, 70, 600, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        jLabel9.setText("Kategori :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelisi4.add(jLabel9);
        jLabel9.setBounds(0, 10, 62, 23);

        KdKategori.setEditable(false);
        KdKategori.setHighlighter(null);
        KdKategori.setName("KdKategori"); // NOI18N
        panelisi4.add(KdKategori);
        KdKategori.setBounds(66, 10, 70, 23);

        btnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKategori.setMnemonic('1');
        btnKategori.setToolTipText("Alt+1");
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
        panelisi4.add(btnKategori);
        btnKategori.setBounds(340, 10, 28, 23);

        NmKategori.setEditable(false);
        NmKategori.setName("NmKategori"); // NOI18N
        panelisi4.add(NmKategori);
        NmKategori.setBounds(138, 10, 200, 23);

        jLabel10.setText("Sasaran :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi4.add(jLabel10);
        jLabel10.setBounds(370, 10, 60, 23);

        Sasaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "1. Mengidentifikasi Pasien Dengan Benar", "2. Meningkatkan Komunikasi Yang Efektif", "3. Meningkatkan Keamanan Obat-obatan Yang Harus Diwaspadai", "4. Memastikan Lokasi Pembedahan Yang Benar, Prosedur Yang Benar, Pembedahan Pada Pasien Yang Benar", "5. Mengurangi Risiko Infeksi Akibat Perawatan Kesehatan", "6. Mengurangi Risiko Cidera Pasien Akibat Terjatuh" }));
        Sasaran.setName("Sasaran"); // NOI18N
        Sasaran.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SasaranItemStateChanged(evt);
            }
        });
        Sasaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SasaranKeyPressed(evt);
            }
        });
        panelisi4.add(Sasaran);
        Sasaran.setBounds(434, 10, 282, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
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

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(225, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
        BtnAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        panelisi1.add(BtnAll);

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
        panelisi1.add(BtnTambah);

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
        panelisi1.add(BtnCari);

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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SKPCariPenilaianPegawai form=new SKPCariPenilaianPegawai(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        pegawai.dispose();
        kategori.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnSimpan,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        jml=0;
        row=tbDokter.getRowCount();
        for(i=0;i<row;i++){
            if(tbDokter.getValueAt(i,2).toString().equals("true")||tbDokter.getValueAt(i,3).toString().equals("true")){
                jml++;
            }
        }
        if(NoPenilaian.getText().trim().equals("")){
            Valid.textKosong(NoPenilaian,"No.Penilaian");
        }else if(KdPenilai.getText().trim().equals("")||NmPenilai.getText().trim().equals("")){
            Valid.textKosong(btnPenilai,"Yang Menilai");
        }else if(KdDInilai.getText().trim().equals("")||NmDinilai.getText().trim().equals("")){
            Valid.textKosong(btnDinilai,"Yang Dinilai");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(jml==0){
            Valid.textKosong(TCari,"Data Penilaian");
        }else{
            index = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (index == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("skp_penilaian","?,?,?,?,?,?","No.Permintaan",6,new String[]{
                    NoPenilaian.getText(),KdPenilai.getText(),KdDInilai.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Keterangan.getText(),"Proses Penilaian"
                    })==true){
                    row=tbDokter.getRowCount();
                    for(i=0;i<row;i++){
                        if(tbDokter.getValueAt(i,2).toString().equals("true")||tbDokter.getValueAt(i,3).toString().equals("true")){
                            if(Sequel.menyimpantf2("skp_detail_penilaian","?,?,?","Data",3,new String[]{
                                    NoPenilaian.getText(),tbDokter.getValueAt(i,0).toString(),(tbDokter.getValueAt(i,2).toString().equals("true")?"Ya":"Tidak")
                                })==false){
                                sukses=false;
                            }
                        }
                    }
                }else{
                    sukses=false;
                }
                if(sukses==true){
                    Sequel.Commit();
                    JOptionPane.showMessageDialog(null,"Proses simpan selesai...!");
                    for(i=0;i<tbDokter.getRowCount();i++){ 
                        tbDokter.setValueAt(false,i,2);
                        tbDokter.setValueAt(false,i,3);
                    }
                    autoNomor();
                    TCari.requestFocus();
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue(); 
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,TCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil2();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil2();
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for(i=0;i<tbDokter.getRowCount();i++){ 
        tbDokter.setValueAt(false,i,2);
        tbDokter.setValueAt(false,i,3);
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/skppenilaianpegawai.iyem")<30){
                tampil2();
            }else{
                tampil();
                tampil2();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SKPKriteriaPenilaian form=new SKPKriteriaPenilaian(null, false);
        form.emptTeks();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void btnPenilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenilaiActionPerformed
        i=1;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPenilaiActionPerformed

    private void KdPenilaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPenilaiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPenilai.setText(pegawai.tampil3(KdPenilai.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            NoPenilaian.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            KdDInilai.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPenilaiActionPerformed(null);
        }
    }//GEN-LAST:event_KdPenilaiKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NoPenilaian,Keterangan);
    }//GEN-LAST:event_TanggalKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalItemStateChanged

    private void NoPenilaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPenilaianKeyPressed
        Valid.pindah(evt, BtnSimpan, KdPenilai);
    }//GEN-LAST:event_NoPenilaianKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        KdKategori.setText("");
        NmKategori.setText("");
        Sasaran.setSelectedIndex(0);
        tampil();
        tampil2();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void KdDInilaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDInilaiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmDinilai.setText(pegawai.tampil3(KdDInilai.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KdDInilai.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDinilaiActionPerformed(null);
        }
    }//GEN-LAST:event_KdDInilaiKeyPressed

    private void btnDinilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDinilaiActionPerformed
        i=2;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnDinilaiActionPerformed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt, KdDInilai, TCari);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setVisible(true);
    }//GEN-LAST:event_btnKategoriActionPerformed

    private void btnKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKategoriKeyPressed
        Valid.pindah(evt,KdKategori,BtnSimpan);
    }//GEN-LAST:event_btnKategoriKeyPressed

    private void SasaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SasaranKeyPressed
        Valid.pindah(evt,btnKategori,TCari);
    }//GEN-LAST:event_SasaranKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                if(tbDokter.getSelectedRow()!= -1){
                    if(tbDokter.getSelectedColumn()==2){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString().equals("true")){
                            tbDokter.setValueAt(false,tbDokter.getSelectedRow(),3);
                        }
                    }
                    if(tbDokter.getSelectedColumn()==3){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString().equals("true")){
                            tbDokter.setValueAt(false,tbDokter.getSelectedRow(),2);
                        }
                    }
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void SasaranItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SasaranItemStateChanged
        tampil2();
    }//GEN-LAST:event_SasaranItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SKPPenilaianPegawai dialog = new SKPPenilaianPegawai(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox KdDInilai;
    private widget.TextBox KdKategori;
    private widget.TextBox KdPenilai;
    private widget.TextBox Keterangan;
    private widget.TextBox NmDinilai;
    private widget.TextBox NmKategori;
    private widget.TextBox NmPenilai;
    private widget.TextBox NoPenilaian;
    private javax.swing.JPopupMenu Popup;
    private widget.ComboBox Sasaran;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnDinilai;
    private widget.Button btnKategori;
    private widget.Button btnPenilai;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try{
            file=new File("./cache/skppenilaianpegawai.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement(
                "select skp_kriteria_penilaian.kode_kriteria,skp_kriteria_penilaian.nama_kriteria,skp_kategori_penilaian.nama_kategori,skp_kategori_penilaian.sasaran "+
                "from skp_kriteria_penilaian inner join skp_kategori_penilaian on skp_kategori_penilaian.kode_kategori=skp_kriteria_penilaian.kode_kategori "+
                "order by skp_kategori_penilaian.sasaran,skp_kriteria_penilaian.kode_kategori");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"KodeKriteria\":\"").append(rs.getString(1)).append("\",\"Kriteria\":\"").append(rs.getString(2)).append("\",\"Kategori\":\"").append(rs.getString(3)).append("\",\"Sasaran\":\"").append(rs.getString(4)).append("\"},");
                } 
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }     
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"skppenilaianpegawai\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    private void tampil2() {
        try{
            row=tbDokter.getRowCount();
            jml=0;
            for(i=0;i<row;i++){
                if(tbDokter.getValueAt(i,2).toString().equals("true")||tbDokter.getValueAt(i,3).toString().equals("true")){
                    jml++;
                }
            }

            KodeKriteria=new String[jml];
            Kriteria=new String[jml];
            Ya=new Boolean[jml];
            Tidak=new Boolean[jml];
            index=0;        
            for(i=0;i<row;i++){
                if(tbDokter.getValueAt(i,2).toString().equals("true")||tbDokter.getValueAt(i,3).toString().equals("true")){
                    KodeKriteria[index]=tbDokter.getValueAt(i,0).toString();
                    Kriteria[index]=tbDokter.getValueAt(i,1).toString();
                    if(tbDokter.getValueAt(i,2).toString().equals("true")){
                        Ya[index]=true;
                    }else if(tbDokter.getValueAt(i,2).toString().equals("false")){
                        Ya[index]=false;
                    }
                    if(tbDokter.getValueAt(i,3).toString().equals("true")){
                        Tidak[index]=true;
                    }else if(tbDokter.getValueAt(i,3).toString().equals("false")){
                        Tidak[index]=false;
                    }
                    index++;
                }
            }
            Valid.tabelKosong(tabMode);
            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[]{KodeKriteria[i],Kriteria[i],Ya[i],Tidak[i]});
            }
            KodeKriteria=null;
            Kriteria=null;
            Ya=null;
            Tidak=null;
            
            myObj = new FileReader("./cache/skppenilaianpegawai.iyem");
            root = mapper.readTree(myObj);
            response = root.path("skppenilaianpegawai");
            if(response.isArray()){
                if(TCari.getText().trim().equals("")&&KdKategori.getText().trim().equals("")&&Sasaran.getSelectedItem().toString().equals("")){
                    for(JsonNode list:response){
                        tabMode.addRow(new Object[]{
                            list.path("KodeKriteria").asText(),list.path("Kriteria").asText(),false,false
                        });
                    }
                }else{
                    for(JsonNode list:response){
                        if(list.path("KodeKriteria").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("Kriteria").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                            if(list.path("Kategori").asText().toLowerCase().contains(NmKategori.getText().toLowerCase())){
                                if(list.path("Sasaran").asText().toLowerCase().contains(Sasaran.getSelectedItem().toString().substring(0,1).replaceAll("S",""))){
                                    tabMode.addRow(new Object[]{
                                        list.path("KodeKriteria").asText(),list.path("Kriteria").asText(),false,false
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            KdPenilai.setEditable(false);
            btnPenilai.setEnabled(false);
            KdPenilai.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getskp_penilaian());
            BtnTambah.setEnabled(akses.getskp_kriteria_penilaian());
            NmPenilai.setText(pegawai.tampil3(KdPenilai.getText()));
        }        
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(skp_penilaian.nomor_penilaian,3),signed)),0) from skp_penilaian where left(skp_penilaian.tanggal,10)='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "SKP"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),4,NoPenilaian); 
    }

 
}

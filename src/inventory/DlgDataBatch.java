/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package inventory;

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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DlgDataBatch extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0,row=0;
    private DlgBarang barang=new DlgBarang(null,false);
    public DlgDataBatch(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        
        tabMode = new DefaultTableModel(null,new Object[]{
            "P", "Kode Barang", "Nama Barang", "No.Batch", "No.Faktur", "Tgl.Datang","Kadaluwarsa",
            "Hrg.Beli(Rp)", "Ralan(Rp)", "Ranap K1(Rp)", "Ranap K2(Rp)", "Ranap K3(Rp)",
            "Kelas Utama/BPJS(Rp)", "Ranap VIP(Rp)", "Ranap VVIP(Rp)", "Beli Luar(Rp)",
            "Jual Bebas(Rp)", "Karyawan(Rp)","Asal Barang","Jml.Beli","Sisa"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            }else if (i == 1) {
                column.setPreferredWidth(85);
            }else if (i == 2) {
                column.setPreferredWidth(165);
            }else if (i == 3) {
                column.setPreferredWidth(75);
            }else if (i == 4) {
                column.setPreferredWidth(90);
            }else if (i == 5) {
                column.setPreferredWidth(70);
            }else if (i == 6) {
                column.setPreferredWidth(70);
            }else if (i == 7) {
                column.setPreferredWidth(85);
            }else if (i == 8) {
                column.setPreferredWidth(85);
            }else if (i == 9) {
                column.setPreferredWidth(85);
            }else if (i == 10) {
                column.setPreferredWidth(85);
            }else if (i == 11) {
                column.setPreferredWidth(85);
            }else if (i == 12) {
                column.setPreferredWidth(85);
            }else if (i == 13) {
                column.setPreferredWidth(85);
            }else if (i == 14) {
                column.setPreferredWidth(85);
            }else if (i == 15) {
                column.setPreferredWidth(85);
            }else if (i == 16) {
                column.setPreferredWidth(85);
            }else if (i == 17) {
                column.setPreferredWidth(85);
            }else if (i == 18) {
                column.setPreferredWidth(75);
            }else if (i == 19) {
                column.setPreferredWidth(55);
            }else if (i == 20) {
                column.setPreferredWidth(55);
            } 
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());        
            

        Kd.setDocument(new batasInput((byte) 15).getKata(Kd));
        Nm.setDocument(new batasInput((byte) 80).getKata(Nm));
        beli.setDocument(new batasInput((byte) 15).getKata(beli));
        ralan.setDocument(new batasInput((byte) 15).getKata(ralan));
        kelas1.setDocument(new batasInput((byte) 15).getKata(kelas1));
        kelas2.setDocument(new batasInput((byte) 15).getKata(kelas2));
        kelas3.setDocument(new batasInput((byte) 15).getKata(kelas3));
        utama.setDocument(new batasInput((byte) 15).getKata(utama));
        kelasvip.setDocument(new batasInput((byte) 15).getKata(kelasvip));
        kelasvvip.setDocument(new batasInput((byte) 15).getKata(kelasvvip));
        jualbebas.setDocument(new batasInput((byte) 15).getKata(jualbebas));
        beliluar.setDocument(new batasInput((byte) 15).getKata(beliluar));
        karyawan.setDocument(new batasInput((byte) 15).getKata(karyawan));
        NoFaktur.setDocument(new batasInput((byte) 20).getKata(NoFaktur));
        NoBatch.setDocument(new batasInput((byte) 20).getKata(NoBatch));
        JmlBeli.setDocument(new batasInput((byte) 10).getKata(JmlBeli));
        Sisa.setDocument(new batasInput((byte) 10).getKata(Sisa));

        ChkInput.setSelected(false);
        isForm();

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
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        Kd.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                        Nm.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                    }    
                    Kd.requestFocus();
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
        
        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    barang.dispose();
                } 
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi2 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label21 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        Kd = new widget.TextBox();
        Nm = new widget.TextBox();
        label18 = new widget.Label();
        label26 = new widget.Label();
        kelas1 = new widget.TextBox();
        label28 = new widget.Label();
        kelas2 = new widget.TextBox();
        label31 = new widget.Label();
        label27 = new widget.Label();
        ralan = new widget.TextBox();
        label14 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label29 = new widget.Label();
        beli = new widget.TextBox();
        kelas3 = new widget.TextBox();
        label30 = new widget.Label();
        label33 = new widget.Label();
        utama = new widget.TextBox();
        kelasvip = new widget.TextBox();
        label34 = new widget.Label();
        label35 = new widget.Label();
        kelasvvip = new widget.TextBox();
        label36 = new widget.Label();
        beliluar = new widget.TextBox();
        jualbebas = new widget.TextBox();
        label37 = new widget.Label();
        label38 = new widget.Label();
        karyawan = new widget.TextBox();
        DTPExpired = new widget.Tanggal();
        label15 = new widget.Label();
        AsalBarang = new widget.ComboBox();
        NoBatch = new widget.TextBox();
        label16 = new widget.Label();
        label17 = new widget.Label();
        TanggalDatang = new widget.Tanggal();
        label19 = new widget.Label();
        JmlBeli = new widget.TextBox();
        label20 = new widget.Label();
        Sisa = new widget.TextBox();
        BtnIF = new widget.Button();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Batch Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tgl.Datang :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(73, 23));
        panelisi2.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi2.add(Tgl1);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label21.setText("s.d.");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(25, 23));
        panelisi2.add(label21);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi2.add(Tgl2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        panelisi2.add(BtnCari);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(LCount);

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
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
        panelisi1.add(BtnBatal);

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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelisi1.add(BtnEdit);

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
        panelisi1.add(BtnAll);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setAutoCreateRowSorter(true);
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
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDokterKeyReleased(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 248));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(660, 197));
        FormInput.setLayout(null);

        label12.setText("Kode Barang :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label12);
        label12.setBounds(0, 12, 96, 23);

        Kd.setEditable(false);
        Kd.setName("Kd"); // NOI18N
        Kd.setPreferredSize(new java.awt.Dimension(207, 23));
        Kd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKeyPressed(evt);
            }
        });
        FormInput.add(Kd);
        Kd.setBounds(100, 12, 130, 23);

        Nm.setEditable(false);
        Nm.setName("Nm"); // NOI18N
        Nm.setPreferredSize(new java.awt.Dimension(207, 23));
        Nm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKeyPressed(evt);
            }
        });
        FormInput.add(Nm);
        Nm.setBounds(100, 42, 370, 23);

        label18.setText("Nama Barang :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label18);
        label18.setBounds(0, 42, 96, 23);

        label26.setText("Hrg Rnp Kelas 1 : Rp.");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label26);
        label26.setBounds(230, 162, 129, 23);

        kelas1.setName("kelas1"); // NOI18N
        kelas1.setPreferredSize(new java.awt.Dimension(207, 23));
        kelas1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelas1MouseMoved(evt);
            }
        });
        kelas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelas1MouseExited(evt);
            }
        });
        kelas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelas1KeyPressed(evt);
            }
        });
        FormInput.add(kelas1);
        kelas1.setBounds(360, 162, 110, 23);

        label28.setText("Hrg Rnp Kelas 2 : Rp.");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label28);
        label28.setBounds(230, 192, 129, 23);

        kelas2.setName("kelas2"); // NOI18N
        kelas2.setPreferredSize(new java.awt.Dimension(207, 23));
        kelas2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelas2MouseMoved(evt);
            }
        });
        kelas2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelas2MouseExited(evt);
            }
        });
        kelas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelas2KeyPressed(evt);
            }
        });
        FormInput.add(kelas2);
        kelas2.setBounds(360, 192, 110, 23);

        label31.setText("Asal Barang :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label31);
        label31.setBounds(0, 72, 96, 23);

        label27.setText("Hrg Ralan : Rp.");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label27);
        label27.setBounds(0, 192, 107, 23);

        ralan.setName("ralan"); // NOI18N
        ralan.setPreferredSize(new java.awt.Dimension(207, 23));
        ralan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ralanMouseMoved(evt);
            }
        });
        ralan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ralanMouseExited(evt);
            }
        });
        ralan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ralanKeyPressed(evt);
            }
        });
        FormInput.add(ralan);
        ralan.setBounds(110, 192, 110, 23);

        label14.setText("No.Faktur :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(85, 23));
        FormInput.add(label14);
        label14.setBounds(0, 102, 96, 23);

        NoFaktur.setHighlighter(null);
        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        FormInput.add(NoFaktur);
        NoFaktur.setBounds(100, 102, 100, 23);

        label29.setText("Harga Beli : Rp.");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label29);
        label29.setBounds(0, 162, 107, 23);

        beli.setName("beli"); // NOI18N
        beli.setPreferredSize(new java.awt.Dimension(207, 23));
        beli.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                beliMouseMoved(evt);
            }
        });
        beli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                beliMouseExited(evt);
            }
        });
        beli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                beliKeyPressed(evt);
            }
        });
        FormInput.add(beli);
        beli.setBounds(110, 162, 110, 23);

        kelas3.setName("kelas3"); // NOI18N
        kelas3.setPreferredSize(new java.awt.Dimension(207, 23));
        kelas3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelas3MouseMoved(evt);
            }
        });
        kelas3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelas3MouseExited(evt);
            }
        });
        kelas3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelas3KeyPressed(evt);
            }
        });
        FormInput.add(kelas3);
        kelas3.setBounds(660, 12, 110, 23);

        label30.setText("Hrg Rnp Kelas 3 : Rp.");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label30);
        label30.setBounds(500, 12, 159, 23);

        label33.setText("Hrg Rnp Utama/BPJS : Rp.");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label33);
        label33.setBounds(500, 42, 159, 23);

        utama.setName("utama"); // NOI18N
        utama.setPreferredSize(new java.awt.Dimension(207, 23));
        utama.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                utamaMouseMoved(evt);
            }
        });
        utama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                utamaMouseExited(evt);
            }
        });
        utama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                utamaKeyPressed(evt);
            }
        });
        FormInput.add(utama);
        utama.setBounds(660, 42, 110, 23);

        kelasvip.setName("kelasvip"); // NOI18N
        kelasvip.setPreferredSize(new java.awt.Dimension(207, 23));
        kelasvip.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelasvipMouseMoved(evt);
            }
        });
        kelasvip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelasvipMouseExited(evt);
            }
        });
        kelasvip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasvipKeyPressed(evt);
            }
        });
        FormInput.add(kelasvip);
        kelasvip.setBounds(660, 72, 110, 23);

        label34.setText("Hrg Rnp Kelas VIP : Rp.");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label34);
        label34.setBounds(500, 72, 159, 23);

        label35.setText("Hrg Rnp Kelas VVIP : Rp.");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label35);
        label35.setBounds(500, 102, 159, 23);

        kelasvvip.setName("kelasvvip"); // NOI18N
        kelasvvip.setPreferredSize(new java.awt.Dimension(207, 23));
        kelasvvip.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelasvvipMouseMoved(evt);
            }
        });
        kelasvvip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelasvvipMouseExited(evt);
            }
        });
        kelasvvip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasvvipKeyPressed(evt);
            }
        });
        FormInput.add(kelasvvip);
        kelasvvip.setBounds(660, 102, 110, 23);

        label36.setText("Hrg Jika Beli dari Apotek Lain : Rp.");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label36);
        label36.setBounds(480, 132, 179, 23);

        beliluar.setName("beliluar"); // NOI18N
        beliluar.setPreferredSize(new java.awt.Dimension(207, 23));
        beliluar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                beliluarMouseMoved(evt);
            }
        });
        beliluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                beliluarMouseExited(evt);
            }
        });
        beliluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                beliluarKeyPressed(evt);
            }
        });
        FormInput.add(beliluar);
        beliluar.setBounds(660, 132, 110, 23);

        jualbebas.setName("jualbebas"); // NOI18N
        jualbebas.setPreferredSize(new java.awt.Dimension(207, 23));
        jualbebas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jualbebasMouseMoved(evt);
            }
        });
        jualbebas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jualbebasMouseExited(evt);
            }
        });
        jualbebas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jualbebasKeyPressed(evt);
            }
        });
        FormInput.add(jualbebas);
        jualbebas.setBounds(660, 162, 110, 23);

        label37.setText("Hrg Jual Obat Bebas : Rp.");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label37);
        label37.setBounds(480, 162, 179, 23);

        label38.setText("Hrg Karyawan : Rp.");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label38);
        label38.setBounds(480, 192, 179, 23);

        karyawan.setName("karyawan"); // NOI18N
        karyawan.setPreferredSize(new java.awt.Dimension(207, 23));
        karyawan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                karyawanMouseMoved(evt);
            }
        });
        karyawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                karyawanMouseExited(evt);
            }
        });
        karyawan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                karyawanKeyPressed(evt);
            }
        });
        FormInput.add(karyawan);
        karyawan.setBounds(660, 192, 110, 23);

        DTPExpired.setForeground(new java.awt.Color(50, 70, 50));
        DTPExpired.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-02-2019" }));
        DTPExpired.setDisplayFormat("dd-MM-yyyy");
        DTPExpired.setName("DTPExpired"); // NOI18N
        DTPExpired.setOpaque(false);
        DTPExpired.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPExpiredKeyPressed(evt);
            }
        });
        FormInput.add(DTPExpired);
        DTPExpired.setBounds(370, 102, 100, 23);

        label15.setText("Kadaluwarsa :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(85, 23));
        FormInput.add(label15);
        label15.setBounds(267, 102, 100, 23);

        AsalBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Penerimaan", "Pengadaan" }));
        AsalBarang.setName("AsalBarang"); // NOI18N
        AsalBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalBarangKeyPressed(evt);
            }
        });
        FormInput.add(AsalBarang);
        AsalBarang.setBounds(100, 72, 130, 23);

        NoBatch.setHighlighter(null);
        NoBatch.setName("NoBatch"); // NOI18N
        NoBatch.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                NoBatchMouseMoved(evt);
            }
        });
        NoBatch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                NoBatchMouseExited(evt);
            }
        });
        NoBatch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBatchKeyPressed(evt);
            }
        });
        FormInput.add(NoBatch);
        NoBatch.setBounds(370, 12, 100, 23);

        label16.setText("No.Batch :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(85, 23));
        FormInput.add(label16);
        label16.setBounds(277, 12, 90, 23);

        label17.setText("Tgl.Datang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(85, 23));
        FormInput.add(label17);
        label17.setBounds(267, 72, 100, 23);

        TanggalDatang.setForeground(new java.awt.Color(50, 70, 50));
        TanggalDatang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-02-2019" }));
        TanggalDatang.setDisplayFormat("dd-MM-yyyy");
        TanggalDatang.setName("TanggalDatang"); // NOI18N
        TanggalDatang.setOpaque(false);
        TanggalDatang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalDatangKeyPressed(evt);
            }
        });
        FormInput.add(TanggalDatang);
        TanggalDatang.setBounds(370, 72, 100, 23);

        label19.setText("Jml.Beli :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(85, 23));
        FormInput.add(label19);
        label19.setBounds(0, 132, 96, 23);

        JmlBeli.setHighlighter(null);
        JmlBeli.setName("JmlBeli"); // NOI18N
        JmlBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JmlBeliKeyPressed(evt);
            }
        });
        FormInput.add(JmlBeli);
        JmlBeli.setBounds(100, 132, 100, 23);

        label20.setText("Sisa :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(85, 23));
        FormInput.add(label20);
        label20.setBounds(267, 132, 100, 23);

        Sisa.setHighlighter(null);
        Sisa.setName("Sisa"); // NOI18N
        Sisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SisaKeyPressed(evt);
            }
        });
        FormInput.add(Sisa);
        Sisa.setBounds(370, 132, 100, 23);

        BtnIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnIF.setMnemonic('1');
        BtnIF.setToolTipText("Alt+1");
        BtnIF.setName("BtnIF"); // NOI18N
        BtnIF.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIFActionPerformed(evt);
            }
        });
        BtnIF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnIFKeyPressed(evt);
            }
        });
        FormInput.add(BtnIF);
        BtnIF.setBounds(231, 12, 25, 23);

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

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void NmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKeyPressed
        Valid.pindah(evt, NoBatch,AsalBarang);
}//GEN-LAST:event_NmKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for (i = 0; i < tbDokter.getRowCount(); i++) {
            if (tbDokter.getValueAt(i, 0).toString().equals("true")) {
                Sequel.queryu2("delete from data_batch where no_batch=? and kode_brng=?",2,new String[]{
                    tbDokter.getValueAt(i, 3).toString(),tbDokter.getValueAt(i, 1).toString()
                });
            }
        }
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (Kd.getText().trim().equals("")) {
            Valid.textKosong(Kd, "Kode Barang");
        } else if (Nm.getText().trim().equals("")) {
            Valid.textKosong(Nm, "Nama Barang");
        } else if (Sisa.getText().trim().equals("")) {
            Valid.textKosong(Sisa, "Sisa Barang");
        } else if (beli.getText().trim().equals("")) {
            Valid.textKosong(beli, "Harga Beli");
        } else if (ralan.getText().trim().equals("")) {
            Valid.textKosong(ralan, "Harga Ralan");
        } else if (kelas1.getText().trim().equals("")) {
            Valid.textKosong(kelas1, "Harga Ranap Kelas 1");
        } else if (kelas2.getText().trim().equals("")) {
            Valid.textKosong(kelas2, "Harga Ranap Kelas 2");
        } else if (kelas3.getText().trim().equals("")) {
            Valid.textKosong(kelas3, "Harga Ranap Kelas 3");
        } else if (utama.getText().trim().equals("")) {
            Valid.textKosong(utama, "Harga Ranap Kelas Utama");
        } else if (kelasvip.getText().trim().equals("")) {
            Valid.textKosong(kelasvip, "Harga Ranap Kelas VIP");
        } else if (kelasvvip.getText().trim().equals("")) {
            Valid.textKosong(kelasvvip, "Harga Ranap Kelas VVIP");
        } else if (beliluar.getText().trim().equals("")) {
            Valid.textKosong(beliluar, "Harga Jika Beli dari Apotek Lain");
        } else if (jualbebas.getText().trim().equals("")) {
            Valid.textKosong(jualbebas, "Harga Jual Obat Bebas");
        } else if (karyawan.getText().trim().equals("")) {
            Valid.textKosong(karyawan, "Harga Karyawan");
        } else if (JmlBeli.getText().trim().equals("") ) {
            Valid.textKosong(JmlBeli, "Jml Beli");
        } else if (NoFaktur.getText().trim().equals("")) {
            Valid.textKosong(NoFaktur, "NoFaktur");
        } else if (NoBatch.getText().trim().equals("")) {
            Valid.textKosong(NoBatch, "Kategori");
        } else {
            if(tbDokter.getSelectedRow()!= -1){
                if(Sequel.mengedittf("data_batch","no_batch=? and kode_brng=?","no_batch=?,kode_brng=?,tgl_beli=?,tgl_kadaluarsa=?,asal=?,no_faktur=?,h_beli=?,ralan=?,kelas1=?,kelas2=?,kelas3=?,utama=?,vip=?,vvip=?,beliluar=?,jualbebas=?,karyawan=?,jumlahbeli=?,sisa=?",21,new String[]{
                        NoBatch.getText(),Kd.getText(),Valid.SetTgl(TanggalDatang.getSelectedItem()+ ""),Valid.SetTgl(DTPExpired.getSelectedItem() + ""),
                        AsalBarang.getSelectedItem().toString(),NoFaktur.getText(),beli.getText(),ralan.getText(),kelas1.getText(),kelas2.getText(), 
                        kelas3.getText(),utama.getText(),kelasvip.getText(),kelasvvip.getText(),beliluar.getText(),jualbebas.getText(),karyawan.getText(),
                        JmlBeli.getText(),Sisa.getText(),tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString()
                    })==true){
                        if (tabMode.getRowCount() != 0) {
                            tampil();
                        }
                        emptTeks();
                }   
            }                
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptDataBatch.jasper", "report", "::[ Data Batch ]::", 
                "select data_batch.kode_brng, databarang.nama_brng, "
                + " data_batch.no_faktur,data_batch.no_batch,data_batch.tgl_beli, data_batch.h_beli,"
                + " data_batch.ralan,data_batch.kelas1,data_batch.kelas2,data_batch.kelas3,"
                + " data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.jualbebas,"
                + " data_batch.karyawan,data_batch.tgl_kadaluarsa, data_batch.asal,data_batch.jumlahbeli,data_batch.sisa "
                + " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "
                + " where data_batch.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and data_batch.kode_brng like '%" + TCari.getText().trim() + "%' or "
                + " data_batch.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and databarang.nama_brng like '%" + TCari.getText().trim() + "%' or "
                + " data_batch.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and data_batch.no_batch like '%" + TCari.getText().trim() + "%' or "
                + " data_batch.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and data_batch.no_faktur like '%" + TCari.getText().trim() + "%' or "
                + " data_batch.tgl_beli between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and data_batch.asal like '%" + TCari.getText().trim() + "%' order by databarang.nama_brng", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnAll, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (Kd.getText().trim().equals("")) {
            Valid.textKosong(Kd, "Kode Barang");
        } else if (Nm.getText().trim().equals("")) {
            Valid.textKosong(Nm, "Nama Barang");
        } else if (Sisa.getText().trim().equals("")) {
            Valid.textKosong(Sisa, "Sisa Barang");
        } else if (beli.getText().trim().equals("")) {
            Valid.textKosong(beli, "Harga Beli");
        } else if (ralan.getText().trim().equals("")) {
            Valid.textKosong(ralan, "Harga Ralan");
        } else if (kelas1.getText().trim().equals("")) {
            Valid.textKosong(kelas1, "Harga Ranap Kelas 1");
        } else if (kelas2.getText().trim().equals("")) {
            Valid.textKosong(kelas2, "Harga Ranap Kelas 2");
        } else if (kelas3.getText().trim().equals("")) {
            Valid.textKosong(kelas3, "Harga Ranap Kelas 3");
        } else if (utama.getText().trim().equals("")) {
            Valid.textKosong(utama, "Harga Ranap Kelas Utama");
        } else if (kelasvip.getText().trim().equals("")) {
            Valid.textKosong(kelasvip, "Harga Ranap Kelas VIP");
        } else if (kelasvvip.getText().trim().equals("")) {
            Valid.textKosong(kelasvvip, "Harga Ranap Kelas VVIP");
        } else if (beliluar.getText().trim().equals("")) {
            Valid.textKosong(beliluar, "Harga Jika Beli dari Apotek Lain");
        } else if (jualbebas.getText().trim().equals("")) {
            Valid.textKosong(jualbebas, "Harga Jual Obat Bebas");
        } else if (karyawan.getText().trim().equals("")) {
            Valid.textKosong(karyawan, "Harga Karyawan");
        } else if (JmlBeli.getText().trim().equals("") ) {
            Valid.textKosong(JmlBeli, "Jml Beli");
        } else if (NoFaktur.getText().trim().equals("")) {
            Valid.textKosong(NoFaktur, "NoFaktur");
        } else if (NoBatch.getText().trim().equals("")) {
            Valid.textKosong(NoBatch, "Kategori");
        } else {
            if(Sequel.menyimpantf("data_batch", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Kode Barang", 19, new String[]{
                    NoBatch.getText(),Kd.getText(),Valid.SetTgl(TanggalDatang.getSelectedItem()+ ""),Valid.SetTgl(DTPExpired.getSelectedItem() + ""),
                    AsalBarang.getSelectedItem().toString(),NoFaktur.getText(),beli.getText(),ralan.getText(),kelas1.getText(),kelas2.getText(), 
                    kelas3.getText(),utama.getText(),kelasvip.getText(),kelasvvip.getText(),beliluar.getText(),jualbebas.getText(),karyawan.getText(),
                    JmlBeli.getText(),Sisa.getText()
                })==true){
                    tampil();
                    emptTeks();
            }                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, karyawan, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm();
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
            ChkInput.setSelected(true);
            isForm();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void kelas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelas1KeyPressed
        Valid.pindah(evt, ralan, kelas2);
    }//GEN-LAST:event_kelas1KeyPressed

    private void kelas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelas2KeyPressed
        Valid.pindah(evt, kelas1, kelas3);
    }//GEN-LAST:event_kelas2KeyPressed

private void ralanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ralanKeyPressed
    Valid.pindah(evt, beli, kelas1);
}//GEN-LAST:event_ralanKeyPressed

private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
    Valid.pindah(evt, TanggalDatang, DTPExpired);
}//GEN-LAST:event_NoFakturKeyPressed

    private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKeyPressed
        Valid.pindah(evt, kelas2,NoBatch, TCari);
    }//GEN-LAST:event_KdKeyPressed

    private void ralanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanMouseExited
        if (ralan.getText().equals("")) {
            ralan.setText("0");
        }
    }//GEN-LAST:event_ralanMouseExited

    private void kelas1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas1MouseExited
        if (kelas1.getText().equals("")) {
            kelas1.setText("0");
        }
    }//GEN-LAST:event_kelas1MouseExited

    private void kelas2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas2MouseExited
        if (kelas2.getText().equals("")) {
            kelas2.setText("0");
        }
    }//GEN-LAST:event_kelas2MouseExited

    private void ralanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanMouseMoved
        if (ralan.getText().equals("0") || ralan.getText().equals("0.0")) {
            ralan.setText("");
        }
    }//GEN-LAST:event_ralanMouseMoved

    private void kelas1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas1MouseMoved
        if (kelas1.getText().equals("0") || kelas1.getText().equals("0.0")) {
            kelas1.setText("");
        }
    }//GEN-LAST:event_kelas1MouseMoved

    private void kelas2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas2MouseMoved
        if (kelas2.getText().equals("0") || kelas2.getText().equals("0.0")) {
            kelas2.setText("");
        }
    }//GEN-LAST:event_kelas2MouseMoved

    private void beliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliMouseExited
        if (beli.getText().equals("")) {
            beli.setText("0");
        }
    }//GEN-LAST:event_beliMouseExited

    private void beliMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliMouseMoved
        if (beli.getText().equals("0")) {
            beli.setText("");
        }
    }//GEN-LAST:event_beliMouseMoved

    private void beliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            ralan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sisa.requestFocus();
        }
    }//GEN-LAST:event_beliKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
    isForm();
}//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kelas3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas3MouseMoved
        if (kelas3.getText().equals("0") || kelas3.getText().equals("0.0")) {
            kelas3.setText("");
        }
    }//GEN-LAST:event_kelas3MouseMoved

    private void kelas3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas3MouseExited
        if (kelas3.getText().equals("")) {
            kelas3.setText("0");
        }
    }//GEN-LAST:event_kelas3MouseExited

    private void kelas3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelas3KeyPressed
        Valid.pindah(evt, kelas2, utama);
    }//GEN-LAST:event_kelas3KeyPressed

    private void utamaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_utamaMouseMoved
        if (utama.getText().equals("0") || utama.getText().equals("0.0")) {
            utama.setText("");
        }
    }//GEN-LAST:event_utamaMouseMoved

    private void utamaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_utamaMouseExited
        if (utama.getText().equals("")) {
            utama.setText("0");
        }
    }//GEN-LAST:event_utamaMouseExited

    private void utamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_utamaKeyPressed
        Valid.pindah(evt, kelas3, kelasvip);
    }//GEN-LAST:event_utamaKeyPressed

    private void kelasvipMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelasvipMouseMoved
        if (kelasvip.getText().equals("0") || kelasvip.getText().equals("0.0")) {
            kelasvip.setText("");
        }
    }//GEN-LAST:event_kelasvipMouseMoved

    private void kelasvipMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelasvipMouseExited
        if (kelasvip.getText().equals("")) {
            kelasvip.setText("0");
        }
    }//GEN-LAST:event_kelasvipMouseExited

    private void kelasvipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasvipKeyPressed
        Valid.pindah(evt, utama, kelasvvip);
    }//GEN-LAST:event_kelasvipKeyPressed

    private void kelasvvipMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelasvvipMouseMoved
        if (kelasvvip.getText().equals("0") || kelasvvip.getText().equals("0.0")) {
            kelasvvip.setText("");
        }
    }//GEN-LAST:event_kelasvvipMouseMoved

    private void kelasvvipMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelasvvipMouseExited
        if (kelasvvip.getText().equals("")) {
            kelasvvip.setText("0");
        }
    }//GEN-LAST:event_kelasvvipMouseExited

    private void kelasvvipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasvvipKeyPressed
        Valid.pindah(evt, kelasvip, beliluar);
    }//GEN-LAST:event_kelasvvipKeyPressed

    private void beliluarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarMouseMoved
        if (beliluar.getText().equals("0") || beliluar.getText().equals("0.0")) {
            beliluar.setText("");
        }
    }//GEN-LAST:event_beliluarMouseMoved

    private void beliluarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarMouseExited
        if (beliluar.getText().equals("")) {
            beliluar.setText("0");
        }
    }//GEN-LAST:event_beliluarMouseExited

    private void beliluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beliluarKeyPressed
        Valid.pindah(evt, kelasvvip, jualbebas);
    }//GEN-LAST:event_beliluarKeyPressed

    private void jualbebasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasMouseMoved
        if (jualbebas.getText().equals("0") || jualbebas.getText().equals("0.0")) {
            jualbebas.setText("");
        }
    }//GEN-LAST:event_jualbebasMouseMoved

    private void jualbebasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasMouseExited
        if (jualbebas.getText().equals("")) {
            jualbebas.setText("0");
        }
    }//GEN-LAST:event_jualbebasMouseExited

    private void jualbebasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jualbebasKeyPressed
        Valid.pindah(evt, beliluar, karyawan);
    }//GEN-LAST:event_jualbebasKeyPressed

    private void karyawanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanMouseMoved
        if (karyawan.getText().equals("0") || karyawan.getText().equals("0.0")) {
            karyawan.setText("");
        }
    }//GEN-LAST:event_karyawanMouseMoved

    private void karyawanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanMouseExited
        if (karyawan.getText().equals("")) {
            karyawan.setText("0");
        }
    }//GEN-LAST:event_karyawanMouseExited

    private void karyawanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_karyawanKeyPressed
        Valid.pindah(evt, jualbebas, BtnSimpan);
    }//GEN-LAST:event_karyawanKeyPressed

    private void DTPExpiredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPExpiredKeyPressed
        Valid.pindah(evt, NoFaktur,JmlBeli);
    }//GEN-LAST:event_DTPExpiredKeyPressed

    private void tbDokterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyReleased

    private void AsalBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalBarangKeyPressed
        Valid.pindah(evt,NoBatch,TanggalDatang);
    }//GEN-LAST:event_AsalBarangKeyPressed

    private void NoBatchMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NoBatchMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_NoBatchMouseMoved

    private void NoBatchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NoBatchMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_NoBatchMouseExited

    private void NoBatchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBatchKeyPressed
        Valid.pindah(evt, BtnIF,AsalBarang);
    }//GEN-LAST:event_NoBatchKeyPressed

    private void TanggalDatangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalDatangKeyPressed
        Valid.pindah(evt, AsalBarang,NoFaktur);
    }//GEN-LAST:event_TanggalDatangKeyPressed

    private void JmlBeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JmlBeliKeyPressed
        Valid.pindah(evt, DTPExpired,Sisa);
    }//GEN-LAST:event_JmlBeliKeyPressed

    private void SisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SisaKeyPressed
        Valid.pindah(evt, JmlBeli,beli);
    }//GEN-LAST:event_SisaKeyPressed

    private void BtnIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIFActionPerformed
        barang.aktifkanbatch="no";
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_BtnIFActionPerformed

    private void BtnIFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnIFKeyPressed
        Valid.pindah(evt, TCari,NoBatch);
    }//GEN-LAST:event_BtnIFKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDataBatch dialog = new DlgDataBatch(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AsalBarang;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnIF;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPExpired;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JmlBeli;
    private widget.TextBox Kd;
    private widget.Label LCount;
    private widget.TextBox Nm;
    private widget.TextBox NoBatch;
    private widget.TextBox NoFaktur;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Sisa;
    private widget.TextBox TCari;
    private widget.Tanggal TanggalDatang;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.TextBox beli;
    private widget.TextBox beliluar;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel2;
    private widget.TextBox jualbebas;
    private widget.TextBox karyawan;
    private widget.TextBox kelas1;
    private widget.TextBox kelas2;
    private widget.TextBox kelas3;
    private widget.TextBox kelasvip;
    private widget.TextBox kelasvvip;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.TextBox ralan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    private widget.TextBox utama;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement("select data_batch.kode_brng, databarang.nama_brng, "
                + " data_batch.no_faktur,data_batch.no_batch,data_batch.tgl_beli, data_batch.h_beli,"
                + " data_batch.ralan,data_batch.kelas1,data_batch.kelas2,data_batch.kelas3,"
                + " data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.jualbebas,"
                + " data_batch.karyawan,data_batch.tgl_kadaluarsa, data_batch.asal,data_batch.jumlahbeli,data_batch.sisa "
                + " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "
                + " where data_batch.tgl_beli between ? and ? and data_batch.kode_brng like ? or "
                + " data_batch.tgl_beli between ? and ? and databarang.nama_brng like ? or "
                + " data_batch.tgl_beli between ? and ? and data_batch.no_batch like ? or "
                + " data_batch.tgl_beli between ? and ? and data_batch.no_faktur like ? or "
                + " data_batch.tgl_beli between ? and ? and data_batch.asal like ? order by databarang.nama_brng");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9, "%" + TCari.getText().trim() + "%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12, "%" + TCari.getText().trim() + "%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        false, rs.getString("kode_brng"),rs.getString("nama_brng"),
                        rs.getString("no_batch"),rs.getString("no_faktur"),
                        rs.getString("tgl_beli"),rs.getString("tgl_kadaluarsa"),
                        rs.getDouble("h_beli"),rs.getDouble("ralan"),rs.getDouble("kelas1"),
                        rs.getDouble("kelas2"),rs.getDouble("kelas3"),rs.getDouble("utama"),
                        rs.getDouble("vip"),rs.getDouble("vvip"),rs.getDouble("beliluar"),
                        rs.getDouble("jualbebas"),rs.getDouble("karyawan"),rs.getString("asal"),
                        rs.getDouble("jumlahbeli"),rs.getDouble("sisa")
                    });
                }
                LCount.setText("" + tabMode.getRowCount());
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally{
                if(rs != null){
                    rs.close();
                }

                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void emptTeks() {
        Kd.setText("");
        Nm.setText("");
        beli.setText("0");
        kelas2.setText("0");
        kelas1.setText("0");
        ralan.setText("0");
        kelas3.setText("0");
        kelasvip.setText("0");
        kelasvvip.setText("0");
        utama.setText("0");
        jualbebas.setText("0");
        beliluar.setText("0");
        karyawan.setText("0");
        Sisa.setText("0");
        JmlBeli.setText("0");
        NoFaktur.setText("");
        NoBatch.setText("");
        BtnIF.requestFocus();
    }

    private void getData() {
        row = tbDokter.getSelectedRow();
        if (row != -1) {
            Kd.setText(tbDokter.getValueAt(row, 1).toString());
            Nm.setText(tbDokter.getValueAt(row, 2).toString());
            NoBatch.setText(tbDokter.getValueAt(row, 3).toString());
            NoFaktur.setText(tbDokter.getValueAt(row, 4).toString());
            Valid.SetTgl(TanggalDatang, tbDokter.getValueAt(row,5).toString());
            Valid.SetTgl(DTPExpired, tbDokter.getValueAt(row,6).toString());
            beli.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 7).toString())));
            ralan.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 8).toString())));
            kelas1.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 9).toString())));
            kelas2.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 10).toString())));
            kelas3.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 11).toString())));
            utama.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 12).toString())));
            kelasvip.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 13).toString())));
            kelasvvip.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 14).toString())));
            beliluar.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 15).toString())));
            jualbebas.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 16).toString())));
            karyawan.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 17).toString())));
            AsalBarang.setSelectedItem(tbDokter.getValueAt(row,18).toString());
            JmlBeli.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 19).toString())));
            Sisa.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 20).toString())));
        }
    }

    public JTable getTable() {
        return tbDokter;
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 248));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    
            
    public void isCek() {
        TCari.requestFocus();
        BtnSimpan.setEnabled(akses.getdata_batch());
        BtnHapus.setEnabled(akses.getdata_batch());
        BtnEdit.setEnabled(akses.getdata_batch());
        BtnPrint.setEnabled(akses.getdata_batch());
    }

}

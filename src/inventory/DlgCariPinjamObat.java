package inventory;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

/**
 *
 * @author RSUI HA
 */
public class DlgCariPinjamObat extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private riwayatobat Trackobat = new riwayatobat();
    private Connection koneksi = koneksiDB.condb();
    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     *
     */
    public DlgSuplier suplier = new DlgSuplier(null, false);
    /**
     *
     */
    public DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    /**
     *
     */
    public DlgBarang barang = new DlgBarang(null, false);
    private PreparedStatement ps, ps2, pscaripesan, psdetailpinjamobat;
    private ResultSet rs, rs2;
    private double tagihan = 0;
    private Jurnal jur = new Jurnal();
    private String aktifkanbatch = "no";
    private boolean sukses = true;
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, true);
    private String tgl = "", sql = "";

    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy");

    /**
     * Creates new form DlgProgramStudi
     *
     * @param frame
     * @param bln
     */
    public DlgCariPinjamObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : " + e);
            aktifkanbatch = "no";
        }

        Object[] row = {"No.Faktur", "Suplier", "Petugas", "Barang",
            "Satuan", "Jml.Pinjam"};
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 6; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(120);
            } else if (i == 1) {
                column.setPreferredWidth(120);
            } else if (i == 2) {
                column.setPreferredWidth(180);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else {
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

//        Object[] row3 = {"No.Faktur", "Kode Barang", "Nama Barang", "Kode Satuan", "Bangsal", "Jenis", "Jumlah", "Harga"};
//        tabMode3 = new DefaultTableModel(null, row3) {
//            @Override
//            public boolean isCellEditable(int rowIndex, int colIndex) {
//                return false;
//            }
//            Class[] types = new Class[]{
//                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
//                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
//            };
//
//            @Override
//            public Class getColumnClass(int columnIndex) {
//                return types[columnIndex];
//            }
//        };
//        tbKamar2.setModel(tabMode3);
//        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
//        tbKamar2.setPreferredScrollableViewportSize(new Dimension(500, 500));
//        tbKamar2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//
//        for (int i = 0; i < 8; i++) {
//            TableColumn column = tbKamar2.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(80);
//            } else if (i == 1) {
//                column.setPreferredWidth(125);
//            } else if (i == 2) {
//                column.setPreferredWidth(180);
//            } else if (i == 3) {
//                column.setPreferredWidth(155);
//            } else if (i == 4) {
//                column.setPreferredWidth(155);
//            } else if (i == 5) {
//                column.setPreferredWidth(80);
//            } else if (i == 6) {
//                column.setPreferredWidth(80);
//            } else if (i == 7) {
//                column.setPreferredWidth(65);
//            }
//        }
//        tbKamar2.setDefaultRenderer(Object.class, new WarnaTable());
        NoFaktur.setDocument(new batasInput((byte) 25).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte) 5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte) 25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte) 15).getKata(kdbar));
//        KdIF.setDocument(new batasInput((byte) 15).getKata(KdIF));
//        kdjenis.setDocument(new batasInput((byte) 4).getKata(kdjenis));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

//        bangsal.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {
//            }
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//            }
//
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if (akses.getform().equals("DlgCariPemesanan")) {
//                    if (bangsal.getTable().getSelectedRow() != -1) {
//                        kdBangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
//                        nmBangsal1.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString());
//                        kdBangsal.requestFocus();
//
//                    }
//                }
//            }
//
//            @Override
//            public void windowIconified(WindowEvent e) {
//            }
//
//            @Override
//            public void windowDeiconified(WindowEvent e) {
//            }
//
//            @Override
//            public void windowActivated(WindowEvent e) {
//            }
//
//            @Override
//            public void windowDeactivated(WindowEvent e) {
//            }
//        });
        suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgCariPemesanan")) {
                    if (suplier.getTable().getSelectedRow() != -1) {
                        kdsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(), 0).toString());
                        nmsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(), 1).toString());
                    }
                    kdsup.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgCariPemesanan")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        suplier.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgCariPemesanan")) {
                    if (petugas.getTable().getSelectedRow() != -1) {
                        kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                    }
                    kdptg.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgCariPemesanan")) {
                    if (barang.getTable().getSelectedRow() != -1) {
                        kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(), 1).toString());
                        nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(), 2).toString());
                    }
                    kdbar.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgCariPemesanan")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        barang.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppHapus = new javax.swing.JMenuItem();
        ppBayar = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label11 = new widget.Label();
        TglBeli1 = new widget.Tanggal();
        label12 = new widget.Label();
        TglBeli2 = new widget.Tanggal();
        label16 = new widget.Label();
        kdsup = new widget.TextBox();
        nmsup = new widget.TextBox();
        btnSuplier = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label13 = new widget.Label();
        kdptg = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPetugas = new widget.Button();
        tabPane1 = new widget.TabPane();
        panelGlass1 = new widget.panelGlass();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50, 50, 50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Data Pinjam Obat");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(195, 26));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        ppBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBayar.setText("Kembalikan Obat");
        ppBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBayar.setName("ppBayar"); // NOI18N
        ppBayar.setPreferredSize(new java.awt.Dimension(195, 26));
        ppBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBayarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBayar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Peminjaman Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelisi1.add(BtnCari);

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(120, 30));
        panelisi1.add(LTotal);

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

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(295, 10, 90, 23);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);
        kdbar.setBounds(389, 10, 110, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(501, 10, 230, 23);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang.setMnemonic('4');
        btnBarang.setToolTipText("Alt+4");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        panelisi4.add(btnBarang);
        btnBarang.setBounds(734, 10, 28, 23);

        label11.setText("Tgl.Pinjam :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label11);
        label11.setBounds(10, 10, 80, 23);

        TglBeli1.setDisplayFormat("dd-MM-yyyy");
        TglBeli1.setName("TglBeli1"); // NOI18N
        TglBeli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli1KeyPressed(evt);
            }
        });
        panelisi4.add(TglBeli1);
        TglBeli1.setBounds(90, 10, 95, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label12);
        label12.setBounds(190, 10, 27, 23);

        TglBeli2.setDisplayFormat("dd-MM-yyyy");
        TglBeli2.setName("TglBeli2"); // NOI18N
        TglBeli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli2KeyPressed(evt);
            }
        });
        panelisi4.add(TglBeli2);
        TglBeli2.setBounds(220, 10, 95, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label16);
        label16.setBounds(770, 10, 60, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi4.add(kdsup);
        kdsup.setBounds(830, 10, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmsup);
        nmsup.setBounds(910, 10, 260, 23);

        btnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSuplier.setMnemonic('1');
        btnSuplier.setToolTipText("Alt+1");
        btnSuplier.setName("btnSuplier"); // NOI18N
        btnSuplier.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierActionPerformed(evt);
            }
        });
        panelisi4.add(btnSuplier);
        btnSuplier.setBounds(1180, 10, 28, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 50));
        panelisi3.setLayout(null);

        label15.setText("No.Pinjam :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 80, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(84, 10, 219, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(300, 10, 80, 23);

        kdptg.setEnabled(false);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(380, 10, 80, 23);

        nmptg.setEditable(false);
        nmptg.setEnabled(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(460, 10, 250, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setEnabled(false);
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(720, 10, 28, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        tabPane1.setName("tabPane1"); // NOI18N

        panelGlass1.setName("panelGlass1"); // NOI18N
        panelGlass1.setLayout(new java.awt.BorderLayout());

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
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
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        panelGlass1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        tabPane1.addTab("Peminjaman Obat", panelGlass1);

        internalFrame1.add(tabPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        suplier.dispose();
        petugas.dispose();
        barang.industri.dispose();
        barang.jenis.dispose();
        barang.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, kdbar);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed
    /*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        akses.setform("DlgCariPemesanan");
        suplier.emptTeks();
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgCariPemesanan");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void TglBeli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli1KeyPressed
        Valid.pindah(evt, NoFaktur, kdsup);
    }//GEN-LAST:event_TglBeli1KeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("DlgCariPemesanan");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup, kdsup.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup, kdsup.getText());
            NoFaktur.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup, kdsup.getText());
            kdptg.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnSuplierActionPerformed(null);
        }
    }//GEN-LAST:event_kdsupKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnKeluar, kdsup);
    }//GEN-LAST:event_NoFakturKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
            kdsup.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
            kdbar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar, kdbar.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar, kdbar.getText());
            TCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void TglBeli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBeli2KeyPressed

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
        switch (tabPane1.getSelectedIndex()) {
            case 0:
                tampil();
                break;
//            case 1:
//                tampil2();
//                break;
            default:
                break;
        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        NoFaktur.setText("");
        kdbar.setText("");
        nmbar.setText("");
        kdsup.setText("");
        nmsup.setText("");
        kdptg.setText("");
        nmptg.setText("");
        tampil();
//        tampil2();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        switch (tabPane1.getSelectedIndex()) {
            case 0:
                cetakPemensan(evt);
                break;
//            case 1:
//                CetakPenerimaanPerItem();
//                break;
            default:
                break;
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void cetakPemensan(ActionEvent evt) throws HeadlessException {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptPinjamObat.jasper", "report", "::[ Transaksi Penerimaan Barang ]::",
                    "SELECT detailpinjamobat.`no_faktur`,detailpinjamobat.`kode_brng`,detailpinjamobat.`jumlah`,"
                    + "detailpinjamobat.`kadaluarsa`,petugas.`nip`,databarang.`nama_brng`,kodesatuan.`kode_sat`,"
                    + "datasuplier.`kode_suplier`,datasuplier.`nama_suplier`,datasuplier.`alamat`,petugas.nama "
                    + "FROM detailpinjamobat "
                    + "INNER JOIN databarang ON detailpinjamobat.`kode_brng`=databarang.`kode_brng` "
                    + "INNER JOIN kodesatuan ON detailpinjamobat.`kode_sat`=kodesatuan.`kode_sat` "
                    + "INNER JOIN pinjam_obat ON detailpinjamobat.`no_faktur`=pinjam_obat.`no_faktur` "
                    + "INNER JOIN datasuplier ON pinjam_obat.`kode_suplier`=datasuplier.`kode_suplier` "
                    + "INNER JOIN petugas ON pinjam_obat.`nip`=petugas.`nip` where tgl_pinjam between '"+Valid.SetDateToString(TglBeli1.getDate())+"' and '"+Valid.SetDateToString(TglBeli2.getDate())+"'", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

//    private void CetakPenerimaanPerItem() {
//        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        if (tabMode3.getRowCount() == 0) {
//            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
//            BtnKeluar.requestFocus();
//        } else {
//            Map<String, Object> param = new HashMap<>();
//            param.put("namars", akses.getnamars());
//            param.put("alamatrs", akses.getalamatrs());
//            param.put("kotars", akses.getkabupatenrs());
//            param.put("propinsirs", akses.getpropinsirs());
//            param.put("kontakrs", akses.getkontakrs());
//            param.put("emailrs", akses.getemailrs());
//            param.put("logo", Sequel.cariGambar("select logo from setting"));
//            param.put("periode", sdf.format(TglBeli1.getDate()) + " s/d " + sdf.format(TglBeli2.getDate()));
//            if (TCari.getText().trim().equals("") && kdBangsal.getText().trim().equals("") && kdjenis.getText().trim().equals("")) {
//                sql = "SELECT pinjam_obat.no_faktur, detailpinjamobat.kode_brng, databarang.nama_brng, detailpinjamobat.kode_sat, bangsal.nm_bangsal, sum(detailpinjamobat.jumlah) as jumlah,detailpinjamobat.h_pesan,jenis.nama,datasuplier.nama_suplier "
//                        + "FROM `pinjam_obat` join detailpinjamobat on detailpinjamobat.no_faktur=pinjam_obat.no_faktur join bangsal on bangsal.kd_bangsal = pinjam_obat.kd_bangsal join databarang on databarang.kode_brng = detailpinjamobat.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns inner join datasuplier on pinjam_obat.kode_suplier=datasuplier.kode_suplier "
//                        + "where pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' group by detailpinjamobat.kode_brng";
//            } else {
////                tgl = " mutasibarang.tanggal between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and concat(databarang.kdjns,jenis.nama) like '%" + kdjenis.getText() + nmjns.getText().trim() + "%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%" + kdkategori.getText() + nmkategori.getText().trim() + "%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%" + kdgolongan.getText() + nmgolongan.getText().trim() + "%' ";
//                sql = "SELECT pinjam_obat.no_faktur, detailpinjamobat.kode_brng, databarang.nama_brng, detailpinjamobat.kode_sat, bangsal.nm_bangsal, sum(detailpinjamobat.jumlah) as jumlah,detailpinjamobat.h_pesan,jenis.nama,datasuplier.nama_suplier "
//                        + "FROM `pinjam_obat` join detailpinjamobat on detailpinjamobat.no_faktur=pinjam_obat.no_faktur join bangsal on bangsal.kd_bangsal = pinjam_obat.kd_bangsal join databarang on databarang.kode_brng = detailpinjamobat.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns inner join datasuplier on pinjam_obat.kode_suplier=datasuplier.kode_suplier "
//                        + "where bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and pinjam_obat.no_faktur like '%" + TCari.getText() + "%' or "
//                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and detailpinjamobat.kode_brng like '%" + TCari.getText() + "%' or "
//                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and databarang.nama_brng like '%" + TCari.getText() + "%' or "
//                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and detailpinjamobat.kode_sat like '%" + TCari.getText() + "%' or "
//                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and bangsal.nm_bangsal like '%" + TCari.getText() + "%' "
//                        + "group by detailpinjamobat.kode_brng";
//            }
//
//            Valid.MyReportqry("rptPenerimaanPerItem.jasper", "report", "::[ Transaksi Mutasi Obat/Alkes/BHP ]::", sql, param);
//
//        }
//        this.setCursor(Cursor.getDefaultCursor());
//    }

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnAll, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
    if (tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().trim().equals("")) {
        Valid.textKosong(TCari, "No.Faktur");
    } else {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, yakin mau dihapus...??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                pscaripesan = koneksi.prepareStatement("select no_faktur, kd_bangsal, status from pinjam_obat where no_faktur=?");
                try {
                    pscaripesan.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                    rs = pscaripesan.executeQuery();
                    if (rs.next()) {
                        Sequel.AutoComitFalse();
                        sukses = true;
                        psdetailpinjamobat = koneksi.prepareStatement("select kode_brng,jumlah,no_faktur from detailpinjamobat where no_faktur=? ");
                        try {
                            psdetailpinjamobat.setString(1, rs.getString(1));
                            rs2 = psdetailpinjamobat.executeQuery();
                            while (rs2.next()) {
                                if (aktifkanbatch.equals("yes")) {
                                    Trackobat.catatRiwayat(rs2.getString("kode_brng"), 0, rs2.getDouble("jumlah2"), "Penerimaan", akses.getkode(), rs.getString("kd_bangsal"), "Hapus", rs2.getString("no_batch"), rs2.getString("no_faktur"));
                                    Sequel.menyimpan("gudangbarang", "'" + rs2.getString("kode_brng") + "','" + rs.getString("kd_bangsal") + "','-" + rs2.getString("jumlah2") + "','" + rs2.getString("no_batch") + "','" + rs2.getString("no_faktur") + "'",
                                            "stok=stok-'" + rs2.getString("jumlah2") + "'", "kode_brng='" + rs2.getString("kode_brng") + "' and kd_bangsal='" + rs.getString("kd_bangsal") + "' and no_batch='" + rs2.getString("no_batch") + "' and no_faktur='" + rs2.getString("no_faktur") + "'");
                                    Sequel.queryu3("delete from data_batch where kode_brng=? and no_batch=? and no_faktur=?", 3, new String[]{
                                        rs2.getString("kode_brng"), rs2.getString("no_batch"), rs2.getString("no_faktur")
                                    });
                                } else {
                                    Trackobat.catatRiwayat(rs2.getString("kode_brng"), 0, 0, "Penerimaan", akses.getkode(), rs.getString("kd_bangsal"), "Hapus", "", "");
                                    Sequel.menyimpan("gudangbarang", "'" + rs2.getString("kode_brng") + "','" + rs.getString("kd_bangsal") + "','-" + rs2.getString("jumlah") + "','',''",
                                            "stok=stok-'" + rs2.getString("jumlah") + "'", "kode_brng='" + rs2.getString("kode_brng") + "' and kd_bangsal='" + rs.getString("kd_bangsal") + "' and no_batch='' and no_faktur=''");
//                                    Sequel.queryu3("delete from data_batch where kode_brng=? and no_batch=? and no_faktur=?", 3, new String[]{
//                                        rs2.getString("kode_brng"), "", ""
//                                    });
                                }
                            }
                            sukses = true;
                        } catch (Exception e) {
                            sukses = false;
                            System.out.println("Notif 2 : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psdetailpinjamobat != null) {
                                psdetailpinjamobat.close();
                            }
                        }

//                        if (sukses == true) {
//                            Sequel.queryu("delete from tampjurnal");
//                            Sequel.menyimpan("tampjurnal", "?,?,?,?", "Rekening", 4, new String[]{
//                                Sequel.cariIsi("select Pemesanan_Obat from set_akun"), "PERSEDIAAN BARANG", "0", rs.getString("tagihan")
//                            });
//                            Sequel.menyimpan("tampjurnal", "?,?,?,?", "Rekening", 4, new String[]{
//                                Sequel.cariIsi("select Kontra_Pemesanan_Obat from set_akun"), "HUTANG USAHA", rs.getString("tagihan"), "0"
//                            });
//                            sukses = jur.simpanJurnal(rs.getString("no_faktur"), Sequel.cariIsi("select current_date()"), "U", "BATAL TRANSAKSI PENERIMAAN BARANG DI " + Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", rs.getString("kd_bangsal")).toUpperCase() + ", OLEH " + akses.getkode());
//                        }
                        if (sukses == true) {
                            Sequel.queryu2("delete from pinjam_obat where no_faktur=?", 1, new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString()});
                            Sequel.Commit();
                        } else {
                            sukses = false;
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();
                    }
                    if (sukses == true) {
                        tampil();
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (pscaripesan != null) {
                        pscaripesan.close();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ppBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBayarActionPerformed
        if (tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().trim().equals("")) {
            Valid.textKosong(TCari, "No.Faktur");
        } else {
            if (Sequel.cariIsi("select status from pinjam_obat where no_faktur='"
                    + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString() + "'").equals("Dipinjam")) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgBayarPinjamObat bayarpesan = new DlgBayarPinjamObat(null, false);
//            bayarpesan.setData(tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                bayarpesan.tampil();
                bayarpesan.isCek();
                bayarpesan.isCek(tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                bayarpesan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                bayarpesan.setLocationRelativeTo(internalFrame1);
                bayarpesan.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            } else {
                JOptionPane.showMessageDialog(rootPane, "Barang Sudah dikembalikan.");
            }
        }
    }//GEN-LAST:event_ppBayarActionPerformed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        NoFaktur.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());       // TODO add your handling code here:
    }//GEN-LAST:event_tbDokterMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPinjamObat dialog = new DlgCariPinjamObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Label LTotal;
    private widget.TextBox NoFaktur;
    private widget.TextBox TCari;
    private widget.Tanggal TglBeli1;
    private widget.Tanggal TglBeli2;
    private widget.Button btnBarang;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelGlass panelGlass1;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppBayar;
    private javax.swing.JMenuItem ppHapus;
    private widget.ScrollPane scrollPane1;
    private widget.TabPane tabPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement("select pinjam_obat.tgl_pinjam,pinjam_obat.no_faktur,"
                    + "pinjam_obat.kode_suplier,datasuplier.nama_suplier,"
                    + "pinjam_obat.nip,petugas.nama,bangsal.nm_bangsal,"
                    + "pinjam_obat.status "
                    + " from pinjam_obat inner join datasuplier inner join petugas inner join bangsal  "
                    + " inner join detailpinjamobat inner join databarang inner join kodesatuan "
                    + " inner join jenis inner join industrifarmasi "
                    + " on detailpinjamobat.kode_brng=databarang.kode_brng "
                    + " and detailpinjamobat.kode_sat=kodesatuan.kode_sat "
                    + " and pinjam_obat.kd_bangsal=bangsal.kd_bangsal "
                    + " and pinjam_obat.no_faktur=detailpinjamobat.no_faktur "
                    + " and pinjam_obat.kode_suplier=datasuplier.kode_suplier "
                    + " and pinjam_obat.nip=petugas.nip and databarang.kdjns=jenis.kdjns"
                    + " where pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and pinjam_obat.no_faktur like ? or "
                    + " pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and pinjam_obat.kode_suplier like ? or "
                    + " pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and datasuplier.nama_suplier like ? or "
                    + " pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and pinjam_obat.nip like ? or "
                    + " pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and petugas.nama like ? or "
                    + " pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and bangsal.nm_bangsal like ? or "
                    + " pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and detailpinjamobat.kode_brng like ? or "
                    + " pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and databarang.nama_brng like ? or "
                    + " pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and detailpinjamobat.kode_sat like ? or "
                    + " pinjam_obat.tgl_pinjam between ? and ? and pinjam_obat.no_faktur like ? and datasuplier.nama_suplier like ? and petugas.nama like ?  and databarang.nama_brng like ? and jenis.nama like ? "
                    + " group by pinjam_obat.no_faktur order by pinjam_obat.tgl_pinjam,pinjam_obat.no_faktur ");
            try {
                ps.setString(1, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(3, "%" + NoFaktur.getText() + "%");
                ps.setString(4, "%" + nmsup.getText() + "%");
                ps.setString(5, "%" + nmptg.getText() + "%");
//                ps.setString(6, "%" + nmjenis.getText() + "%");
                ps.setString(6, "%" + nmbar.getText() + "%");
                ps.setString(7, "%" + TCari.getText() + "%");
                ps.setString(8, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(9, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(10, "%" + NoFaktur.getText() + "%");
                ps.setString(11, "%" + nmsup.getText() + "%");
                ps.setString(12, "%" + nmptg.getText() + "%");
//                ps.setString(14, "%" + nmjenis.getText() + "%");
                ps.setString(13, "%" + nmbar.getText() + "%");

                ps.setString(14, "%" + TCari.getText() + "%");
                ps.setString(15, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(16, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(17, "%" + NoFaktur.getText() + "%");
                ps.setString(18, "%" + nmsup.getText() + "%");
                ps.setString(19, "%" + nmptg.getText() + "%");
//                ps.setString(22, "%" + nmjenis.getText() + "%");
                ps.setString(20, "%" + nmbar.getText() + "%");

                ps.setString(21, "%" + TCari.getText() + "%");
                ps.setString(22, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(23, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(24, "%" + NoFaktur.getText() + "%");
                ps.setString(25, "%" + nmsup.getText() + "%");
                ps.setString(26, "%" + nmptg.getText() + "%");
//                ps.setString(30, "%" + nmjenis.getText() + "%");
                ps.setString(27, "%" + nmbar.getText() + "%");

                ps.setString(28, "%" + TCari.getText() + "%");
                ps.setString(29, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(30, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(31, "%" + NoFaktur.getText() + "%");
                ps.setString(32, "%" + nmsup.getText() + "%");
                ps.setString(33, "%" + nmptg.getText() + "%");
//                ps.setString(38, "%" + nmjenis.getText() + "%");
                ps.setString(34, "%" + nmbar.getText() + "%");

                ps.setString(35, "%" + TCari.getText() + "%");
                ps.setString(36, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(37, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(38, "%" + NoFaktur.getText() + "%");
                ps.setString(39, "%" + nmsup.getText() + "%");
                ps.setString(40, "%" + nmptg.getText() + "%");
//                ps.setString(46, "%" + nmjenis.getText() + "%");
                ps.setString(41, "%" + nmbar.getText() + "%");

                ps.setString(42, "%" + TCari.getText() + "%");
                ps.setString(43, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(44, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(45, "%" + NoFaktur.getText() + "%");
                ps.setString(46, "%" + nmsup.getText() + "%");
                ps.setString(47, "%" + nmptg.getText() + "%");
//                ps.setString(54, "%" + nmjenis.getText() + "%");
                ps.setString(48, "%" + nmbar.getText() + "%");

                ps.setString(49, "%" + TCari.getText() + "%");
                ps.setString(50, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(51, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(52, "%" + NoFaktur.getText() + "%");
                ps.setString(53, "%" + nmsup.getText() + "%");
                ps.setString(54, "%" + nmptg.getText() + "%");
//                ps.setString(62, "%" + nmjenis.getText() + "%");
                ps.setString(55, "%" + nmbar.getText() + "%");

                ps.setString(56, "%" + TCari.getText() + "%");
                ps.setString(57, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(58, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(59, "%" + NoFaktur.getText() + "%");
                ps.setString(60, "%" + nmsup.getText() + "%");
                ps.setString(61, "%" + nmptg.getText() + "%");
//                ps.setString(70, "%" + nmjenis.getText() + "%");
                ps.setString(62, "%" + nmbar.getText() + "%");

                ps.setString(63, "%" + TCari.getText() + "%");
                ps.setString(64, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
                ps.setString(65, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
                ps.setString(66, "%" + NoFaktur.getText() + "%");
                ps.setString(67, "%" + nmsup.getText() + "%");
                ps.setString(68, "%" + nmptg.getText() + "%");
//                ps.setString(78, "%" + nmjenis.getText() + "%");
                ps.setString(69, "%" + nmbar.getText() + "%");

                ps.setString(70, "%" + TCari.getText() + "%");
//                ps.setString(81, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
//                ps.setString(82, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
//                ps.setString(83, "%" + NoFaktur.getText() + "%");
//                ps.setString(84, "%" + nmsup.getText() + "%");
//                ps.setString(85, "%" + nmptg.getText() + "%");
//                ps.setString(86, "%" + nmjenis.getText() + "%");
//                ps.setString(87, "%" + nmbar.getText() + "%");
//                
//                ps.setString(88, "%" + TCari.getText() + "%");
//                ps.setString(100, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
//                ps.setString(101, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
//                ps.setString(102, "%" + NoFaktur.getText() + "%");
//                ps.setString(103, "%" + nmsup.getText() + "%");
//                ps.setString(104, "%" + nmptg.getText() + "%");
//                ps.setString(105, "%" + nmjenis.getText() + "%");
//                ps.setString(106, "%" + nmbar.getText() + "%");
//                ps.setString(107, "%" + NmIF.getText() + "%");
//                ps.setString(108, "%" + TCari.getText() + "%");
//                ps.setString(109, Valid.SetTgl(TglBeli1.getSelectedItem() + ""));
//                ps.setString(110, Valid.SetTgl(TglBeli2.getSelectedItem() + ""));
//                ps.setString(111, "%" + NoFaktur.getText() + "%");
//                ps.setString(112, "%" + nmsup.getText() + "%");
//                ps.setString(113, "%" + nmptg.getText() + "%");
//                ps.setString(114, "%" + nmjenis.getText() + "%");
//                ps.setString(115, "%" + nmbar.getText() + "%");
//                ps.setString(116, "%" + NmIF.getText() + "%");
//                ps.setString(117, "%" + TCari.getText() + "%");
                rs = ps.executeQuery();
                tagihan = 0;
                while (rs.next()) {
                    tabMode.addRow(new Object[]{rs.getString(2), rs.getString(3) + ", " + rs.getString(4),
                        rs.getString(5) + ", " + rs.getString(6), "Pengadaan di " + rs.getString(7) + " :", " "
                    });

                    ps2 = koneksi.prepareStatement("select detailpinjamobat.kode_brng,databarang.nama_brng, "
                            + "detailpinjamobat.kode_sat,kodesatuan.satuan,detailpinjamobat.jumlah,detailpinjamobat.kadaluarsa "
                            + "from detailpinjamobat inner join databarang inner join kodesatuan inner join jenis inner join industrifarmasi "
                            + " on detailpinjamobat.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                            + " and databarang.kode_industri=industrifarmasi.kode_industri and detailpinjamobat.kode_sat=kodesatuan.kode_sat where "
                            + " detailpinjamobat.no_faktur=? and databarang.nama_brng like ? and detailpinjamobat.kode_brng like ? or "
                            + " detailpinjamobat.no_faktur=? and databarang.nama_brng like ? and databarang.nama_brng like ? or "
                            + " detailpinjamobat.no_faktur=? and databarang.nama_brng like ? and detailpinjamobat.kode_sat like ? or "
                            //                            + " detailpinjamobat.no_faktur=? and jenis.nama like ? and databarang.nama_brng like ? and detailpinjamobat.no_batch like ? or "
                            //                            + " detailpinjamobat.no_faktur=? and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? or "
                            + " detailpinjamobat.no_faktur=? and databarang.nama_brng like ? and jenis.nama like ? "
                            + "order by detailpinjamobat.kode_brng ");

                    try {
                        ps2.setString(1, rs.getString(2));
//                        ps2.setString(2, "%" + nmjenis.getText() + "%");
                        ps2.setString(2, "%" + nmbar.getText() + "%");
                        ps2.setString(3, "%" + TCari.getText() + "%");
                        ps2.setString(4, rs.getString(2));
//                        ps2.setString(6, "%" + nmjenis.getText() + "%");
                        ps2.setString(5, "%" + nmbar.getText() + "%");
                        ps2.setString(6, "%" + TCari.getText() + "%");
                        ps2.setString(7, rs.getString(2));
//                        ps2.setString(10, "%" + nmjenis.getText() + "%");
                        ps2.setString(8, "%" + nmbar.getText() + "%");
                        ps2.setString(9, "%" + TCari.getText() + "%");
                        ps2.setString(10, rs.getString(2));
//                        ps2.setString(14, "%" + nmjenis.getText() + "%");
                        ps2.setString(11, "%" + nmbar.getText() + "%");
                        ps2.setString(12, "%" + TCari.getText() + "%");
//                        ps2.setString(17, rs.getString(2));
//                        ps2.setString(18, "%" + nmjenis.getText() + "%");
//                        ps2.setString(19, "%" + nmbar.getText() + "%");
//                        ps2.setString(20, "%" + TCari.getText() + "%");
//                        ps2.setString(21, rs.getString(2));
//                        ps2.setString(22, "%" + nmjenis.getText() + "%");
//                        ps2.setString(23, "%" + nmbar.getText() + "%");
//                        ps2.setString(24, "%" + TCari.getText() + "%");
                        rs2 = ps2.executeQuery();
                        int no = 1;
                        while (rs2.next()) {
                            tabMode.addRow(new Object[]{no + "", "Exp : " + rs2.getString("kadaluarsa"), " ", rs2.getString(1) + ", " + rs2.getString(2),
                                rs2.getString(3) + ", " + rs2.getString(4),
                                rs2.getString(5)});
                            no++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                    tabMode.addRow(new Object[]{" ", "Tgl.Pinjam", ": " + rs.getString("tgl_pinjam"), " ", " "});
//                    tabMode.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", ""});
//                    tabMode.addRow(new Object[]{"", "Jth.Tempo", ": " + rs.getString("tgl_tempo"), "", "", "", "", "", "PPN", ":", Valid.SetAngka(rs.getDouble("ppn"))});
                    tabMode.addRow(new Object[]{" ", "Status Pinjam", ": " + rs.getString("status"), " ", " "});
//                    tagihan = tagihan + rs.getDouble("tagihan");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
//            LTotal.setText(Valid.SetAngka(tagihan));
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    /**
     *
     */
    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdbar.requestFocus();
    }

    /**
     *
     */
    public void isCek() {
        BtnPrint.setEnabled(akses.getpemesanan_obat());
        if (akses.getkode().equals("Admin Utama")) {
            ppHapus.setEnabled(true);
        } else {
            ppHapus.setEnabled(false);
        }
        ppBayar.setEnabled(akses.getpemesanan_obat());
        ppHapus.setEnabled(akses.getpemesanan_obat());
        kdptg.setText(akses.getkode());
        Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
    }
//
//    public void tampil2() {
//        Valid.tabelKosong(tabMode3);
//        try {
////            nilaitotal = 0;
//            if (TCari.getText().trim().equals("") && kdBangsal.getText().trim().equals("") && kdjenis.getText().trim().equals("")) {
//                ps3 = koneksi.prepareStatement(
//                        "SELECT pinjam_obat.no_faktur, detailpinjamobat.kode_brng, databarang.nama_brng, detailpinjamobat.kode_sat, bangsal.nm_bangsal, sum(detailpinjamobat.jumlah) as jumlah,detailpinjamobat.h_pesan,jenis.nama,datasuplier.nama_suplier "
//                        + "FROM `pinjam_obat` join detailpinjamobat on detailpinjamobat.no_faktur=pinjam_obat.no_faktur join bangsal on bangsal.kd_bangsal = pinjam_obat.kd_bangsal join databarang on databarang.kode_brng = detailpinjamobat.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns inner join datasuplier on  pinjam_obat.kode_suplier=datasuplier.kode_suplier "
//                        + "where pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' group by detailpinjamobat.kode_brng"
//                );
//            } else {
////                tgl = " mutasibarang.tanggal between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and concat(databarang.kdjns,jenis.nama) like '%" + kdjenis.getText() + nmjns.getText().trim() + "%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%" + kdkategori.getText() + nmkategori.getText().trim() + "%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%" + kdgolongan.getText() + nmgolongan.getText().trim() + "%' ";
//                ps3 = koneksi.prepareStatement(
//                        "SELECT pinjam_obat.no_faktur, detailpinjamobat.kode_brng, databarang.nama_brng, detailpinjamobat.kode_sat, bangsal.nm_bangsal, sum(detailpinjamobat.jumlah) as jumlah,detailpinjamobat.h_pesan,jenis.nama,datasuplier.nama_suplier "
//                        + "FROM `pinjam_obat` join detailpinjamobat on detailpinjamobat.no_faktur=pinjam_obat.no_faktur join bangsal on bangsal.kd_bangsal = pinjam_obat.kd_bangsal join databarang on databarang.kode_brng = detailpinjamobat.kode_brng inner join jenis on databarang.kdjns=jenis.kdjns inner join datasuplier on  pinjam_obat.kode_suplier=datasuplier.kode_suplier "
//                        + "where bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and pinjam_obat.no_faktur like '%" + TCari.getText() + "%' or "
//                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and detailpinjamobat.kode_brng like '%" + TCari.getText() + "%' or "
//                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and databarang.nama_brng like '%" + TCari.getText() + "%' or "
//                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and detailpinjamobat.kode_sat like '%" + TCari.getText() + "%' or "
//                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pinjam_obat.tgl_faktur between '" + Valid.SetTgl(TglBeli1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(TglBeli2.getSelectedItem() + "") + " 23:59:59' and jenis.kdjns like '%" + kdjenis.getText() + "%' and bangsal.nm_bangsal like '%" + TCari.getText() + "%' "
//                        + "group by detailpinjamobat.kode_brng"
//                );
//            }
//
//            try {
//                rs3 = ps3.executeQuery();
//                while (rs3.next()) {
////                    nilaitotal = nilaitotal + rs3.getDouble(7);
//                    tabMode3.addRow(new Object[]{
//                        rs3.getString(1), rs3.getString(2), rs3.getString(3), rs3.getString(4),
//                        rs3.getString(5), rs3.getString(8), rs3.getDouble(6), rs3.getDouble(7)
//                    });
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            } finally {
//                if (rs3 != null) {
//                    rs3.close();
//                }
//                if (ps3 != null) {
//                    ps3.close();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Notifikasi : " + e);
//        }
//        LTotal.setText("" + tabMode3.getRowCount());
//    }
}

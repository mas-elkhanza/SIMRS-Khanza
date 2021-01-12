/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */
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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;

/**
 *
 * @author dosen
 */
public final class DlgPindahGudang extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabMode2, tabMode3;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private riwayatobat Trackobat = new riwayatobat();
    private PreparedStatement ps, ps2, ps3;
    private ResultSet rs, rs2, rs3;
    private String tgl = "", sql = "";
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private double nilaitotal = 0;
    private String aktifkanbatch = "no";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy");

    int pilihan = 0;
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, true);

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgPindahGudang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(628, 674);

        Object[] row = {"Dari", "", "Ke", "", "Barang", "", "Jml", "Harga", "Total", "Tgl.Mutasi", "Keterangan", "No.Batch", "No.Faktur"};
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 13; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(40);
            } else if (i == 1) {
                column.setPreferredWidth(115);
            } else if (i == 2) {
                column.setPreferredWidth(40);
            } else if (i == 3) {
                column.setPreferredWidth(115);
            } else if (i == 4) {
                column.setPreferredWidth(85);
            } else if (i == 5) {
                column.setPreferredWidth(170);
            } else if (i == 6) {
                column.setPreferredWidth(35);
            } else if (i == 7) {
                column.setPreferredWidth(75);
            } else if (i == 8) {
                column.setPreferredWidth(85);
            } else if (i == 9) {
                column.setPreferredWidth(120);
            } else if (i == 10) {
                column.setPreferredWidth(150);
            } else if (i == 11) {
                column.setPreferredWidth(70);
            } else if (i == 12) {
                column.setPreferredWidth(100);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] row2 = {"Kode Barang", "Nama Barang", "Kode Satuan", "Dari", "Bangsal Tujuan", "Jumlah", "Harga"};
        tabMode2 = new DefaultTableModel(null, row2) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbKamar1.setModel(tabMode2);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamar1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbKamar1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(80);
            } else if (i == 1) {
                column.setPreferredWidth(155);
            } else if (i == 2) {
                column.setPreferredWidth(80);
            } else if (i == 3) {
                column.setPreferredWidth(155);
            } else if (i == 4) {
                column.setPreferredWidth(155);
            } else if (i == 5) {
                column.setPreferredWidth(80);
            } else if (i == 6) {
                column.setPreferredWidth(65);
            }
        }
        tbKamar1.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] row3 = {"No.Faktur", "Kode Barang", "Nama Barang", "Kode Satuan", "Bangsal", "Jumlah", "Harga"};
        tabMode3 = new DefaultTableModel(null, row3) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbKamar2.setModel(tabMode3);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar2.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamar2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbKamar2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(80);
            } else if (i == 1) {
                column.setPreferredWidth(125);
            } else if (i == 2) {
                column.setPreferredWidth(180);
            } else if (i == 3) {
                column.setPreferredWidth(155);
            } else if (i == 4) {
                column.setPreferredWidth(155);
            } else if (i == 5) {
                column.setPreferredWidth(80);
            } else if (i == 6) {
                column.setPreferredWidth(65);
            }
        }
        tbKamar2.setDefaultRenderer(Object.class, new WarnaTable());

        kddari.setDocument(new batasInput((byte) 10).getKata(kddari));
        kdke.setDocument(new batasInput((byte) 10).getKata(kddari));
        jumlah.setDocument(new batasInput((byte) 10).getKata(jumlah));
        Keterangan.setDocument(new batasInput((byte) 60).getKata(Keterangan));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil("");
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil("");
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil("");
                    }
                }
            });
        }
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgPindahGudang")) {
                    if (bangsal.getTable().getSelectedRow() != -1) {
                        if (pilihan == 2) {
                            kdke.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                            nmke.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString());
                            kdke.requestFocus();
                        } else if (pilihan == 1) {
                            kddari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                            nmdari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString());
                            kddari.requestFocus();
                        } else if (pilihan == 3) {
                            kdBangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                            nmBangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString());
                            kddari.requestFocus();
                        } else if (pilihan == 4) {
                            kdBangsal1.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                            nmBangsal1.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString());
                            kddari.requestFocus();
                        }
                    }
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

        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    kdjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 0).toString());
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
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

        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    kdgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 0).toString());
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }

                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kategori.getTable().getSelectedRow() != -1) {
                    kdkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 0).toString());
                    nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : " + e);
            aktifkanbatch = "no";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelisi4 = new widget.panelisi();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        label17 = new widget.Label();
        kddari = new widget.TextBox();
        nmdari = new widget.TextBox();
        btnDari = new widget.Button();
        jumlah = new widget.TextBox();
        label37 = new widget.Label();
        Keterangan = new widget.TextBox();
        label18 = new widget.Label();
        kdke = new widget.TextBox();
        nmke = new widget.TextBox();
        btnKe = new widget.Button();
        label39 = new widget.Label();
        label19 = new widget.Label();
        kdbarang = new widget.TextBox();
        nmbarang = new widget.TextBox();
        harga = new widget.TextBox();
        total = new widget.TextBox();
        nobatch = new widget.TextBox();
        nofaktur = new widget.TextBox();
        kdjenis = new widget.TextBox();
        kdkategori = new widget.TextBox();
        kdgolongan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        label13 = new widget.Label();
        LTotalMutasi = new widget.Label();
        BtnCetak = new widget.Button();
        BtnKeluar = new widget.Button();
        tabPane1 = new widget.TabPane();
        panelGlass1 = new widget.panelGlass();
        panelBiasa1 = new widget.PanelBiasa();
        label20 = new widget.Label();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label22 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label23 = new widget.Label();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelGlass2 = new widget.panelGlass();
        panelBiasa2 = new widget.PanelBiasa();
        label21 = new widget.Label();
        kdBangsal = new widget.TextBox();
        nmBangsal = new widget.TextBox();
        BtnJenis1 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbKamar1 = new widget.Table();
        panelGlass3 = new widget.panelGlass();
        panelBiasa3 = new widget.PanelBiasa();
        label24 = new widget.Label();
        kdBangsal1 = new widget.TextBox();
        nmBangsal1 = new widget.TextBox();
        BtnJenis2 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbKamar2 = new widget.Table();

        panelisi4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 133));
        panelisi4.setLayout(null);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(458, 10, 70, 23);

        Tanggal.setEditable(false);
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);
        Tanggal.setBounds(532, 10, 180, 23);

        label17.setText("Dari :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 10, 55, 23);

        kddari.setName("kddari"); // NOI18N
        kddari.setPreferredSize(new java.awt.Dimension(80, 23));
        kddari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddariKeyPressed(evt);
            }
        });
        panelisi4.add(kddari);
        kddari.setBounds(59, 10, 90, 23);

        nmdari.setEditable(false);
        nmdari.setName("nmdari"); // NOI18N
        nmdari.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmdari);
        nmdari.setBounds(151, 10, 257, 23);

        btnDari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDari.setMnemonic('1');
        btnDari.setToolTipText("Alt+1");
        btnDari.setName("btnDari"); // NOI18N
        btnDari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnDari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDariActionPerformed(evt);
            }
        });
        panelisi4.add(btnDari);
        btnDari.setBounds(410, 10, 28, 23);

        jumlah.setHighlighter(null);
        jumlah.setName("jumlah"); // NOI18N
        panelisi4.add(jumlah);
        jumlah.setBounds(530, 70, 100, 23);

        label37.setText("Jumlah :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label37);
        label37.setBounds(458, 70, 70, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi4.add(Keterangan);
        Keterangan.setBounds(532, 40, 190, 23);

        label18.setText("Ke :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label18);
        label18.setBounds(0, 40, 55, 23);

        kdke.setName("kdke"); // NOI18N
        kdke.setPreferredSize(new java.awt.Dimension(80, 23));
        kdke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkeKeyPressed(evt);
            }
        });
        panelisi4.add(kdke);
        kdke.setBounds(59, 40, 90, 23);

        nmke.setEditable(false);
        nmke.setName("nmke"); // NOI18N
        nmke.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmke);
        nmke.setBounds(151, 40, 257, 23);

        btnKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKe.setMnemonic('1');
        btnKe.setToolTipText("Alt+1");
        btnKe.setName("btnKe"); // NOI18N
        btnKe.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeActionPerformed(evt);
            }
        });
        panelisi4.add(btnKe);
        btnKe.setBounds(410, 40, 28, 23);

        label39.setText("Keterangan :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(458, 40, 70, 23);

        label19.setText("Barang :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label19);
        label19.setBounds(0, 70, 55, 23);

        kdbarang.setName("kdbarang"); // NOI18N
        kdbarang.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(kdbarang);
        kdbarang.setBounds(59, 70, 90, 23);

        nmbarang.setEditable(false);
        nmbarang.setName("nmbarang"); // NOI18N
        nmbarang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbarang);
        nmbarang.setBounds(151, 70, 257, 23);

        harga.setHighlighter(null);
        harga.setName("harga"); // NOI18N
        panelisi4.add(harga);
        harga.setBounds(590, 100, 100, 23);

        total.setHighlighter(null);
        total.setName("total"); // NOI18N
        panelisi4.add(total);
        total.setBounds(440, 100, 100, 23);

        nobatch.setName("nobatch"); // NOI18N
        nobatch.setPreferredSize(new java.awt.Dimension(80, 23));
        nobatch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nobatchKeyPressed(evt);
            }
        });
        panelisi4.add(nobatch);
        nobatch.setBounds(59, 40, 90, 23);

        nofaktur.setName("nofaktur"); // NOI18N
        nofaktur.setPreferredSize(new java.awt.Dimension(80, 23));
        nofaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nofakturKeyPressed(evt);
            }
        });
        panelisi4.add(nofaktur);
        nofaktur.setBounds(59, 40, 90, 23);

        kdjenis.setEditable(false);
        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(75, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });

        kdkategori.setEditable(false);
        kdkategori.setName("kdkategori"); // NOI18N
        kdkategori.setPreferredSize(new java.awt.Dimension(75, 23));
        kdkategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkategoriKeyPressed(evt);
            }
        });

        kdgolongan.setEditable(false);
        kdgolongan.setName("kdgolongan"); // NOI18N
        kdgolongan.setPreferredSize(new java.awt.Dimension(75, 23));
        kdgolongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgolonganKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Mutasi Antar Gudang Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout());

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel19.setText("Tanggal Mutasi :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-01-2021" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(105, 23));
        panelisi3.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelisi3.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-01-2021" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(105, 23));
        panelisi3.add(DTPCari2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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
        panelisi3.add(BtnCari);

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
        panelisi3.add(BtnAll);

        jPanel1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(100, 30));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(100, 30));
        panelisi1.add(LCount);

        label13.setText("Nilai Mutasi Barang :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(125, 30));
        panelisi1.add(label13);

        LTotalMutasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotalMutasi.setText("0");
        LTotalMutasi.setName("LTotalMutasi"); // NOI18N
        LTotalMutasi.setPreferredSize(new java.awt.Dimension(205, 30));
        panelisi1.add(LTotalMutasi);

        BtnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnCetak.setMnemonic('C');
        BtnCetak.setText("Cetak");
        BtnCetak.setToolTipText("Alt+C");
        BtnCetak.setName("BtnCetak"); // NOI18N
        BtnCetak.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCetakActionPerformed(evt);
            }
        });
        BtnCetak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCetakKeyPressed(evt);
            }
        });
        panelisi1.add(BtnCetak);

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

        tabPane1.setName("tabPane1"); // NOI18N
        tabPane1.setPreferredSize(new java.awt.Dimension(800, 100));

        panelGlass1.setName("panelGlass1"); // NOI18N
        panelGlass1.setLayout(new java.awt.BorderLayout());

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(1023, 47));
        panelBiasa1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 10));

        label20.setText("Jenis :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(40, 23));
        panelBiasa1.add(label20);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(192, 23));
        panelBiasa1.add(nmjns);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnJenis);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        panelBiasa1.add(label22);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(192, 23));
        panelBiasa1.add(nmkategori);

        BtnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKategori.setMnemonic('2');
        BtnKategori.setToolTipText("Alt+2");
        BtnKategori.setName("BtnKategori"); // NOI18N
        BtnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKategoriActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnKategori);

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        panelBiasa1.add(label23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(192, 23));
        panelBiasa1.add(nmgolongan);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnGolongan);

        panelGlass1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

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

        panelGlass1.add(Scroll, java.awt.BorderLayout.CENTER);

        tabPane1.addTab("Mutasi Antar Gudang", panelGlass1);

        panelGlass2.setName("panelGlass2"); // NOI18N
        panelGlass2.setLayout(new java.awt.BorderLayout());

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setPreferredSize(new java.awt.Dimension(1023, 47));
        panelBiasa2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 10));

        label21.setText("Bangsal Asal");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(80, 23));
        panelBiasa2.add(label21);

        kdBangsal.setEditable(false);
        kdBangsal.setName("kdBangsal"); // NOI18N
        kdBangsal.setPreferredSize(new java.awt.Dimension(90, 23));
        panelBiasa2.add(kdBangsal);

        nmBangsal.setEditable(false);
        nmBangsal.setName("nmBangsal"); // NOI18N
        nmBangsal.setPreferredSize(new java.awt.Dimension(192, 23));
        panelBiasa2.add(nmBangsal);

        BtnJenis1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis1.setMnemonic('2');
        BtnJenis1.setToolTipText("Alt+2");
        BtnJenis1.setName("BtnJenis1"); // NOI18N
        BtnJenis1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenis1ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnJenis1);

        panelGlass2.add(panelBiasa2, java.awt.BorderLayout.PAGE_START);

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbKamar1.setName("tbKamar1"); // NOI18N
        tbKamar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamar1MouseClicked(evt);
            }
        });
        tbKamar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamar1KeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbKamar1);

        panelGlass2.add(Scroll1, java.awt.BorderLayout.CENTER);

        tabPane1.addTab("Mutasi Per Item", panelGlass2);

        panelGlass3.setName("panelGlass3"); // NOI18N
        panelGlass3.setLayout(new java.awt.BorderLayout());

        panelBiasa3.setName("panelBiasa3"); // NOI18N
        panelBiasa3.setPreferredSize(new java.awt.Dimension(1023, 47));
        panelBiasa3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 10));

        label24.setText("Bangsal Asal");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(80, 23));
        panelBiasa3.add(label24);

        kdBangsal1.setEditable(false);
        kdBangsal1.setName("kdBangsal1"); // NOI18N
        kdBangsal1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelBiasa3.add(kdBangsal1);

        nmBangsal1.setEditable(false);
        nmBangsal1.setName("nmBangsal1"); // NOI18N
        nmBangsal1.setPreferredSize(new java.awt.Dimension(192, 23));
        panelBiasa3.add(nmBangsal1);

        BtnJenis2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis2.setMnemonic('2');
        BtnJenis2.setToolTipText("Alt+2");
        BtnJenis2.setName("BtnJenis2"); // NOI18N
        BtnJenis2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenis2ActionPerformed(evt);
            }
        });
        panelBiasa3.add(BtnJenis2);

        panelGlass3.add(panelBiasa3, java.awt.BorderLayout.PAGE_START);

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbKamar2.setName("tbKamar2"); // NOI18N
        tbKamar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamar2MouseClicked(evt);
            }
        });
        tbKamar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamar2KeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbKamar2);

        panelGlass3.add(Scroll2, java.awt.BorderLayout.CENTER);

        tabPane1.addTab("Penerimaan Per Item", panelGlass3);

        internalFrame1.add(tabPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (Sequel.queryu2tf("delete from mutasibarang where kd_bangsaldari=? and kd_bangsalke=? and tanggal=? and kode_brng=? and no_batch=? and no_faktur=?", 6, new String[]{
            kddari.getText(), kdke.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Tanggal.getSelectedItem().toString().substring(11, 19), kdbarang.getText(), nobatch.getText(), nofaktur.getText()
        }) == true) {
            if (aktifkanbatch.equals("yes")) {
                Trackobat.catatRiwayat(kdbarang.getText(), Valid.SetAngka(jumlah.getText()), 0, "Mutasi", akses.getkode(), kddari.getText(), "Hapus", nobatch.getText(), nofaktur.getText());
                Sequel.menyimpan("gudangbarang", "'" + kdbarang.getText() + "','" + kddari.getText() + "','" + jumlah.getText() + "','" + nobatch.getText() + "','" + nofaktur.getText() + "'",
                        "stok=stok+" + jumlah.getText() + "", "kode_brng='" + kdbarang.getText() + "' and kd_bangsal='" + kddari.getText() + "' and no_batch='" + nobatch.getText() + "' and no_faktur='" + nofaktur.getText() + "'");
                Trackobat.catatRiwayat(kdbarang.getText(), 0, Valid.SetAngka(jumlah.getText()), "Mutasi", akses.getkode(), kdke.getText(), "Hapus", nobatch.getText(), nofaktur.getText());
                Sequel.menyimpan("gudangbarang", "'" + kdbarang.getText() + "','" + kdke.getText() + "','-" + jumlah.getText() + "','" + nobatch.getText() + "','" + nofaktur.getText() + "'",
                        "stok=stok-" + jumlah.getText() + "", "kode_brng='" + kdbarang.getText() + "' and kd_bangsal='" + kdke.getText() + "' and no_batch='" + nobatch.getText() + "' and no_faktur='" + nofaktur.getText() + "'");
            } else {
                Trackobat.catatRiwayat(kdbarang.getText(), Valid.SetAngka(jumlah.getText()), 0, "Mutasi", akses.getkode(), kddari.getText(), "Hapus", "", "");
                Sequel.menyimpan("gudangbarang", "'" + kdbarang.getText() + "','" + kddari.getText() + "','" + jumlah.getText() + "','',''",
                        "stok=stok+" + jumlah.getText() + "", "kode_brng='" + kdbarang.getText() + "' and kd_bangsal='" + kddari.getText() + "' and no_batch='' and no_faktur=''");
                Trackobat.catatRiwayat(kdbarang.getText(), 0, Valid.SetAngka(jumlah.getText()), "Mutasi", akses.getkode(), kdke.getText(), "Hapus", "", "");
                Sequel.menyimpan("gudangbarang", "'" + kdbarang.getText() + "','" + kdke.getText() + "','-" + jumlah.getText() + "','',''",
                        "stok=stok-" + jumlah.getText() + "", "kode_brng='" + kdbarang.getText() + "' and kd_bangsal='" + kdke.getText() + "' and no_batch='' and no_faktur=''");
            }
            BtnCariActionPerformed(evt);
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnKeluar, BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

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

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbKamar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TampilkanData();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbKamarKeyPressed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdgolongan.setText("");
        kdjenis.setText("");
        kdkategori.setText("");
        nmgolongan.setText("");
        nmjns.setText("");
        nmkategori.setText("");
        tampil(" order by mutasibarang.tanggal");
    }//GEN-LAST:event_BtnAllActionPerformed

private void BtnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCetakActionPerformed
    switch (tabPane1.getSelectedIndex()) {
        case 0:
            CetakMutasiBarang();
            break;
        case 1:
            CetakMutasiPerItem();
            break;
        case 2:
            CetakPenerimaanPerItem();
            break;
        default:
            break;
    }
}//GEN-LAST:event_BtnCetakActionPerformed

    private void CetakMutasiBarang() throws HeadlessException {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            BtnKeluar.requestFocus();
        } else {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("periode", sdf.format(DTPCari1.getDate()) + " s/d " + sdf.format(DTPCari2.getDate()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            if (TCari.getText().trim().equals("") && nmjns.getText().trim().equals("") && nmkategori.getText().trim().equals("") && nmgolongan.getText().trim().equals("")) {
                sql = "select mutasibarang.kd_bangsaldari,bangsaldari.nm_bangsal as dari,"
                        + "mutasibarang.kd_bangsalke,bangsalke.nm_bangsal as ke, "
                        + "mutasibarang.kode_brng,databarang.nama_brng,mutasibarang.jml,mutasibarang.harga,(mutasibarang.jml*mutasibarang.harga)  as total,"
                        + "mutasibarang.tanggal,mutasibarang.keterangan,mutasibarang.no_batch,mutasibarang.no_faktur "
                        + "from mutasibarang inner join databarang on mutasibarang.kode_brng=databarang.kode_brng "
                        + "inner join bangsal as bangsaldari on mutasibarang.kd_bangsaldari=bangsaldari.kd_bangsal "
                        + "inner join bangsal as bangsalke on mutasibarang.kd_bangsalke=bangsalke.kd_bangsal "
                        + "inner join jenis on databarang.kdjns=jenis.kdjns "
                        + "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode where "
                        + " mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' order by mutasibarang.tanggal";
            } else {
                tgl = " mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and concat(databarang.kdjns,jenis.nama) like '%" + kdjenis.getText() + nmjns.getText().trim() + "%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%" + kdkategori.getText() + nmkategori.getText().trim() + "%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%" + kdgolongan.getText() + nmgolongan.getText().trim() + "%' ";
                sql = "select mutasibarang.kd_bangsaldari,bangsaldari.nm_bangsal as dari,"
                        + "mutasibarang.kd_bangsalke,bangsalke.nm_bangsal as ke, "
                        + "mutasibarang.kode_brng,databarang.nama_brng,mutasibarang.jml,mutasibarang.harga,(mutasibarang.jml*mutasibarang.harga)  as total,"
                        + "mutasibarang.tanggal,mutasibarang.keterangan,mutasibarang.no_batch,mutasibarang.no_faktur "
                        + "from mutasibarang inner join databarang on mutasibarang.kode_brng=databarang.kode_brng "
                        + "inner join bangsal as bangsaldari on mutasibarang.kd_bangsaldari=bangsaldari.kd_bangsal "
                        + "inner join bangsal as bangsalke on mutasibarang.kd_bangsalke=bangsalke.kd_bangsal "
                        + "inner join jenis on databarang.kdjns=jenis.kdjns "
                        + "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode where "
                        + tgl + " and mutasibarang.kd_bangsaldari like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and bangsaldari.nm_bangsal like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.kd_bangsalke like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and bangsalke.nm_bangsal like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.kode_brng like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and databarang.nama_brng like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.no_batch like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.no_faktur like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.tanggal like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.keterangan like '%" + TCari.getText().trim() + "%' order by mutasibarang.tanggal";
            }

            Valid.MyReportqry("rptMutasiObat.jasper", "report", "::[ Transaksi Mutasi Obat/Alkes/BHP ]::", sql, param);

        }
        this.setCursor(Cursor.getDefaultCursor());
    }

private void BtnCetakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCetakKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnCetakKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        BtnHapus.setEnabled(akses.getmutasi_barang());
    }//GEN-LAST:event_formWindowActivated

    private void btnKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeActionPerformed
        akses.setform("DlgPindahGudang");
        pilihan = 2;
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnKeActionPerformed

    private void kdkeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='" + kdke.getText() + "'", nmke);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='" + kdke.getText() + "'", nmke);
            kddari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='" + kdke.getText() + "'", nmke);
            TCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnKeActionPerformed(null);
        }
    }//GEN-LAST:event_kdkeKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt, Tanggal, jumlah);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void btnDariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDariActionPerformed
        pilihan = 1;
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnDariActionPerformed

    private void kddariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='" + kddari.getText() + "'", kdke);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='" + kddari.getText() + "'", kdke);
            TCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal='" + kddari.getText() + "'", kdke);
            kdke.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnDariActionPerformed(null);
        }
    }//GEN-LAST:event_kddariKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, Keterangan, TCari);
    }//GEN-LAST:event_TanggalKeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdjenisKeyPressed

    private void kdkategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkategoriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdkategoriKeyPressed

    private void kdgolonganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgolonganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdgolonganKeyPressed

    private void nobatchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nobatchKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nobatchKeyPressed

    private void nofakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nofakturKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nofakturKeyPressed

    private void BtnJenis1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenis1ActionPerformed
        // TODO add your handling code here:
        akses.setform("DlgPindahGudang");
        pilihan = 3;
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnJenis1ActionPerformed

    private void tbKamar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbKamar1MouseClicked

    private void tbKamar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbKamar1KeyPressed

    private void BtnJenis2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenis2ActionPerformed
        // TODO add your handling code here:
        akses.setform("DlgPindahGudang");
        pilihan = 4;
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnJenis2ActionPerformed

    private void tbKamar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbKamar2MouseClicked

    private void tbKamar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbKamar2KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPindahGudang dialog = new DlgPindahGudang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCetak;
    private widget.Button BtnGolongan;
    private widget.Button BtnHapus;
    private widget.Button BtnJenis;
    private widget.Button BtnJenis1;
    private widget.Button BtnJenis2;
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LTotalMutasi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnDari;
    private widget.Button btnKe;
    private widget.TextBox harga;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox jumlah;
    private widget.TextBox kdBangsal;
    private widget.TextBox kdBangsal1;
    private widget.TextBox kdbarang;
    private widget.TextBox kddari;
    private widget.TextBox kdgolongan;
    private widget.TextBox kdjenis;
    private widget.TextBox kdkategori;
    private widget.TextBox kdke;
    private widget.Label label10;
    private widget.Label label13;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label32;
    private widget.Label label37;
    private widget.Label label39;
    private widget.Label label9;
    private widget.TextBox nmBangsal;
    private widget.TextBox nmBangsal1;
    private widget.TextBox nmbarang;
    private widget.TextBox nmdari;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.TextBox nmke;
    private widget.TextBox nobatch;
    private widget.TextBox nofaktur;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.PanelBiasa panelBiasa3;
    private widget.panelGlass panelGlass1;
    private widget.panelGlass panelGlass2;
    private widget.panelGlass panelGlass3;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.TabPane tabPane1;
    private widget.Table tbKamar;
    private widget.Table tbKamar1;
    private widget.Table tbKamar2;
    private widget.TextBox total;
    // End of variables declaration//GEN-END:variables

    /**
     *
     * @param order
     */
    public void tampil(String order) {
        Valid.tabelKosong(tabMode);
        try {
            nilaitotal = 0;
            if (TCari.getText().trim().equals("") && nmjns.getText().trim().equals("") && nmkategori.getText().trim().equals("") && nmgolongan.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "select mutasibarang.kd_bangsaldari,bangsaldari.nm_bangsal as dari,"
                        + "mutasibarang.kd_bangsalke,bangsalke.nm_bangsal as ke, "
                        + "mutasibarang.kode_brng,databarang.nama_brng,mutasibarang.jml,mutasibarang.harga,(mutasibarang.jml*mutasibarang.harga)  as total,"
                        + "mutasibarang.tanggal,mutasibarang.keterangan,mutasibarang.no_batch,mutasibarang.no_faktur "
                        + "from mutasibarang inner join databarang on mutasibarang.kode_brng=databarang.kode_brng "
                        + "inner join bangsal as bangsaldari on mutasibarang.kd_bangsaldari=bangsaldari.kd_bangsal "
                        + "inner join bangsal as bangsalke on mutasibarang.kd_bangsalke=bangsalke.kd_bangsal "
                        + "inner join jenis on databarang.kdjns=jenis.kdjns "
                        + "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode where "
                        + "mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59'  " + order);
            } else {
                tgl = " mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and concat(databarang.kdjns,jenis.nama) like '%" + kdjenis.getText() + nmjns.getText().trim() + "%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%" + kdkategori.getText() + nmkategori.getText().trim() + "%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%" + kdgolongan.getText() + nmgolongan.getText().trim() + "%' ";
                ps = koneksi.prepareStatement(
                        "select mutasibarang.kd_bangsaldari,bangsaldari.nm_bangsal as dari,"
                        + "mutasibarang.kd_bangsalke,bangsalke.nm_bangsal as ke, "
                        + "mutasibarang.kode_brng,databarang.nama_brng,mutasibarang.jml,mutasibarang.harga,(mutasibarang.jml*mutasibarang.harga)  as total,"
                        + "mutasibarang.tanggal,mutasibarang.keterangan,mutasibarang.no_batch,mutasibarang.no_faktur "
                        + "from mutasibarang inner join databarang on mutasibarang.kode_brng=databarang.kode_brng "
                        + "inner join bangsal as bangsaldari on mutasibarang.kd_bangsaldari=bangsaldari.kd_bangsal "
                        + "inner join bangsal as bangsalke on mutasibarang.kd_bangsalke=bangsalke.kd_bangsal "
                        + "inner join jenis on databarang.kdjns=jenis.kdjns "
                        + "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode where "
                        + tgl + " and mutasibarang.kd_bangsaldari like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and bangsaldari.nm_bangsal like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.kd_bangsalke like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and bangsalke.nm_bangsal like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.kode_brng like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and databarang.nama_brng like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.no_batch like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.no_faktur like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.tanggal like '%" + TCari.getText().trim() + "%' or "
                        + tgl + " and mutasibarang.keterangan like '%" + TCari.getText().trim() + "%' " + order);
            }

            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    nilaitotal = nilaitotal + rs.getDouble(9);
                    tabMode.addRow(new Object[]{
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8),
                        rs.getDouble(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
        LTotalMutasi.setText(df2.format(nilaitotal));
    }

    public void tampil2() {
        Valid.tabelKosong(tabMode2);
        try {
            nilaitotal = 0;
            if (TCari.getText().trim().equals("") && kdBangsal.getText().trim().equals("")) {
                ps2 = koneksi.prepareStatement(
                        "SELECT mutasibarang.kode_brng, databarang.nama_brng, databarang.kode_sat, asal.nm_bangsal as bangsal_asal, tujuan.nm_bangsal as bangsal_tujuan, sum(mutasibarang.jml) as jumlah, mutasibarang.harga "
                        + "FROM `mutasibarang` join databarang on databarang.kode_brng = mutasibarang.kode_brng join bangsal as asal on mutasibarang.kd_bangsaldari = asal.kd_bangsal join bangsal as tujuan on mutasibarang.kd_bangsalke = tujuan.kd_bangsal "
                        + "where mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' "
                        + "GROUP by mutasibarang.kode_brng, asal.kd_bangsal, tujuan.kd_bangsal ORDER by databarang.nama_brng"
                );
            } else {
//                tgl = " mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and concat(databarang.kdjns,jenis.nama) like '%" + kdjenis.getText() + nmjns.getText().trim() + "%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%" + kdkategori.getText() + nmkategori.getText().trim() + "%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%" + kdgolongan.getText() + nmgolongan.getText().trim() + "%' ";
                ps2 = koneksi.prepareStatement(
                        "SELECT mutasibarang.kode_brng, databarang.nama_brng, databarang.kode_sat, asal.nm_bangsal as bangsal_asal, tujuan.nm_bangsal as bangsal_tujuan, sum(mutasibarang.jml) as jumlah, mutasibarang.harga "
                        + "FROM `mutasibarang` join databarang on databarang.kode_brng = mutasibarang.kode_brng join bangsal as asal on mutasibarang.kd_bangsaldari = asal.kd_bangsal join bangsal as tujuan on mutasibarang.kd_bangsalke = tujuan.kd_bangsal "
                        + "where asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and mutasibarang.kode_brng like '%" + TCari.getText() + "%' or "
                        + " asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and databarang.nama_brng like '%" + TCari.getText() + "%' or "
                        + " asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and databarang.kode_sat like '%" + TCari.getText() + "%' or "
                        + " asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and asal.nm_bangsal like '%" + TCari.getText() + "%' or "
                        + " asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and tujuan.nm_bangsal like '%" + TCari.getText() + "%' "
                        + "GROUP by mutasibarang.kode_brng, asal.kd_bangsal, tujuan.kd_bangsal ORDER by databarang.nama_brng"
                );
            }

            try {
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    nilaitotal = nilaitotal + rs2.getDouble(7);
                    tabMode2.addRow(new Object[]{
                        rs2.getString(1), rs2.getString(2), rs2.getString(3), rs2.getString(4),
                        rs2.getString(5), rs2.getDouble(6), rs2.getDouble(7)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode2.getRowCount());
        LTotalMutasi.setText(df2.format(nilaitotal));
    }

    public void tampil3() {
        Valid.tabelKosong(tabMode3);
        try {
            nilaitotal = 0;
            if (TCari.getText().trim().equals("") && kdBangsal1.getText().trim().equals("")) {
                ps3 = koneksi.prepareStatement(
                        "SELECT pemesanan.no_faktur, detailpesan.kode_brng, databarang.nama_brng, detailpesan.kode_sat, bangsal.nm_bangsal, sum(detailpesan.jumlah) as jumlah,detailpesan.h_pesan "
                        + "FROM `pemesanan` join detailpesan on detailpesan.no_faktur=pemesanan.no_faktur join bangsal on bangsal.kd_bangsal = pemesanan.kd_bangsal join databarang on databarang.kode_brng = detailpesan.kode_brng "
                        + "where pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' group by detailpesan.kode_brng"
                );
            } else {
//                tgl = " mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and concat(databarang.kdjns,jenis.nama) like '%" + kdjenis.getText() + nmjns.getText().trim() + "%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%" + kdkategori.getText() + nmkategori.getText().trim() + "%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%" + kdgolongan.getText() + nmgolongan.getText().trim() + "%' ";
                ps3 = koneksi.prepareStatement(
                        "SELECT pemesanan.no_faktur, detailpesan.kode_brng, databarang.nama_brng, detailpesan.kode_sat, bangsal.nm_bangsal, sum(detailpesan.jumlah) as jumlah,detailpesan.h_pesan "
                        + "FROM `pemesanan` join detailpesan on detailpesan.no_faktur=pemesanan.no_faktur join bangsal on bangsal.kd_bangsal = pemesanan.kd_bangsal join databarang on databarang.kode_brng = detailpesan.kode_brng "
                        + "where bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and pemesanan.no_faktur like '%" + TCari.getText() + "%' or "
                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and detailpesan.kode_brng like '%" + TCari.getText() + "%' or "
                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and databarang.nama_brng like '%" + TCari.getText() + "%' or "
                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and detailpesan.kode_sat like '%" + TCari.getText() + "%' or "
                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and bangsal.nm_bangsal like '%" + TCari.getText() + "%' "
                        + "group by detailpesan.kode_brng"
                );
            }

            try {
                rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    nilaitotal = nilaitotal + rs3.getDouble(7);
                    tabMode3.addRow(new Object[]{
                        rs3.getString(1), rs3.getString(2), rs3.getString(3), rs3.getString(4),
                        rs3.getString(5), rs3.getDouble(6), rs3.getDouble(7)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs3 != null) {
                    rs3.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode3.getRowCount());
        LTotalMutasi.setText(df2.format(nilaitotal));
    }

    private void getData() {
        int row = tbKamar.getSelectedRow();
        if (row != -1) {
            kddari.setText(tabMode.getValueAt(row, 0).toString());
            nmdari.setText(tabMode.getValueAt(row, 1).toString());
            kdke.setText(tabMode.getValueAt(row, 2).toString());
            nmke.setText(tabMode.getValueAt(row, 3).toString());
            kdbarang.setText(tabMode.getValueAt(row, 4).toString());
            nmbarang.setText(tabMode.getValueAt(row, 5).toString());
            jumlah.setText(tabMode.getValueAt(row, 6).toString());
            harga.setText(tabMode.getValueAt(row, 7).toString());
            total.setText(tabMode.getValueAt(row, 8).toString());
            Keterangan.setText(tabMode.getValueAt(row, 10).toString());
            nobatch.setText(tabMode.getValueAt(row, 11).toString());
            nofaktur.setText(tabMode.getValueAt(row, 12).toString());
            Valid.SetTgl2(Tanggal, tabMode.getValueAt(row, 9).toString());
        }
    }

    private void TampilkanData() {
        switch (tabPane1.getSelectedIndex()) {
            case 0:
                tampil(" order by mutasibarang.tanggal");
                break;
            case 1:
                tampil2();
                break;
            case 2:
                tampil3();
                break;
            default:
                break;
        }
    }

    private void CetakMutasiPerItem() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            BtnKeluar.requestFocus();
        } else {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("periode", sdf.format(DTPCari1.getDate()) + " s/d " + sdf.format(DTPCari2.getDate()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            if (TCari.getText().trim().equals("") && kdBangsal.getText().trim().equals("")) {
                sql = "SELECT mutasibarang.kode_brng, databarang.nama_brng, databarang.kode_sat, asal.nm_bangsal as bangsal_asal, tujuan.nm_bangsal as bangsal_tujuan, sum(mutasibarang.jml) as jumlah, mutasibarang.harga "
                        + "FROM `mutasibarang` join databarang on databarang.kode_brng = mutasibarang.kode_brng join bangsal as asal on mutasibarang.kd_bangsaldari = asal.kd_bangsal join bangsal as tujuan on mutasibarang.kd_bangsalke = tujuan.kd_bangsal "
                        + "where mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' "
                        + "GROUP by mutasibarang.kode_brng, asal.kd_bangsal, tujuan.kd_bangsal ORDER by databarang.nama_brng";
            } else {
//                tgl = " mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and concat(databarang.kdjns,jenis.nama) like '%" + kdjenis.getText() + nmjns.getText().trim() + "%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%" + kdkategori.getText() + nmkategori.getText().trim() + "%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%" + kdgolongan.getText() + nmgolongan.getText().trim() + "%' ";
                sql = "SELECT mutasibarang.kode_brng, databarang.nama_brng, databarang.kode_sat, asal.nm_bangsal as bangsal_asal, tujuan.nm_bangsal as bangsal_tujuan, sum(mutasibarang.jml) as jumlah, mutasibarang.harga "
                        + "FROM `mutasibarang` join databarang on databarang.kode_brng = mutasibarang.kode_brng join bangsal as asal on mutasibarang.kd_bangsaldari = asal.kd_bangsal join bangsal as tujuan on mutasibarang.kd_bangsalke = tujuan.kd_bangsal "
                        + "where asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and mutasibarang.kode_brng like '%" + TCari.getText() + "%' or "
                        + " asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and databarang.nama_brng like '%" + TCari.getText() + "%' or "
                        + " asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and databarang.kode_sat like '%" + TCari.getText() + "%' or "
                        + " asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and asal.nm_bangsal like '%" + TCari.getText() + "%' or "
                        + " asal.nm_bangsal like '%" + nmBangsal.getText() + "%' and mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and tujuan.nm_bangsal like '%" + TCari.getText() + "%' "
                        + "GROUP by mutasibarang.kode_brng, asal.kd_bangsal, tujuan.kd_bangsal ORDER by databarang.nama_brng";
            }

            Valid.MyReportqry("rptMutasiPerItem.jasper", "report", "::[ Transaksi Mutasi Obat/Alkes/BHP ]::", sql, param);

        }
        this.setCursor(Cursor.getDefaultCursor()); //To change body of generated methods, choose Tools | Templates.
    }

    private void CetakPenerimaanPerItem() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode3.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            BtnKeluar.requestFocus();
        } else {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("periode", sdf.format(DTPCari1.getDate()) + " s/d " + sdf.format(DTPCari2.getDate()));
            if (TCari.getText().trim().equals("") && kdBangsal.getText().trim().equals("")) {
                sql = "SELECT pemesanan.no_faktur, detailpesan.kode_brng, databarang.nama_brng, detailpesan.kode_sat, bangsal.nm_bangsal, sum(detailpesan.jumlah) as jumlah,detailpesan.h_pesan "
                        + "FROM `pemesanan` join detailpesan on detailpesan.no_faktur=pemesanan.no_faktur join bangsal on bangsal.kd_bangsal = pemesanan.kd_bangsal join databarang on databarang.kode_brng = detailpesan.kode_brng "
                        + "where pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' group by detailpesan.kode_brng";
            } else {
//                tgl = " mutasibarang.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and concat(databarang.kdjns,jenis.nama) like '%" + kdjenis.getText() + nmjns.getText().trim() + "%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%" + kdkategori.getText() + nmkategori.getText().trim() + "%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%" + kdgolongan.getText() + nmgolongan.getText().trim() + "%' ";
                sql = "SELECT pemesanan.no_faktur, detailpesan.kode_brng, databarang.nama_brng, detailpesan.kode_sat, bangsal.nm_bangsal, sum(detailpesan.jumlah) as jumlah,detailpesan.h_pesan "
                        + "FROM `pemesanan` join detailpesan on detailpesan.no_faktur=pemesanan.no_faktur join bangsal on bangsal.kd_bangsal = pemesanan.kd_bangsal join databarang on databarang.kode_brng = detailpesan.kode_brng "
                        + "where bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and pemesanan.no_faktur like '%" + TCari.getText() + "%' or "
                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and detailpesan.kode_brng like '%" + TCari.getText() + "%' or "
                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and databarang.nama_brng like '%" + TCari.getText() + "%' or "
                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and detailpesan.kode_sat like '%" + TCari.getText() + "%' or "
                        + " bangsal.nm_bangsal  like '%" + nmBangsal1.getText() + "%' and pemesanan.tgl_faktur between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59' and bangsal.nm_bangsal like '%" + TCari.getText() + "%' "
                        + "group by detailpesan.kode_brng";
            }

            Valid.MyReportqry("rptPenerimaanPerItem.jasper", "report", "::[ Transaksi Mutasi Obat/Alkes/BHP ]::", sql, param);

        }
        this.setCursor(Cursor.getDefaultCursor());
    }
}

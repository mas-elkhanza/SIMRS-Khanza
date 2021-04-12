package inventory;

import fungsi.WarnaTable2;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

/**
 *
 * @author RSUI HA
 */
public class DlgPinjamObat extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Jurnal jur = new Jurnal();
    private riwayatobat Trackobat = new riwayatobat();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, false);
    private DlgCariPinjamObat form = new DlgCariPinjamObat(null, false);
    private DlgCariDataKonversi datakonversi = new DlgCariDataKonversi(null, false);
    private double hargakonversi = 0, meterai = 0, ttl = 0, y = 0, w = 0, ttldisk = 0,
            sbttl = 0, ppn = 0, jmlkonversi = 0, hargappn = 0, hargadiskon = 0;
    private int jml = 0, i = 0, row = 0, index = 0;
    private String[] kodebarang, namabarang, satuan, satuanbeli, kadaluwarsa, nobatch;
    private boolean[] ganti;
    private double[] harga, jumlah, subtotal, diskon, besardiskon, jmltotal, jmlstok, hpp, isi, jmlbesar,
            ralan, kelas1, kelas2, kelas3, utama, vip, vvip, beliluar, jualbebas, karyawan, dasar;
    private WarnaTable2 warna = new WarnaTable2();

    /**
     *
     */
    public boolean tampikan = true;
    private boolean sukses = true;
    private String aktifkanbatch = "no", pengaturanharga = Sequel.cariIsi("select setharga from set_harga_obat"),
            hargadasar = Sequel.cariIsi("select hargadasar from set_harga_obat");

    /**
     * Creates new form DlgProgramStudi
     *
     * @param parent
     * @param modal
     */
    public DlgPinjamObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        TglPesan.setDate(new Date());

        TglPesan.addPropertyChangeListener("date", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                autoNomor();
            }
        });
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : " + e);
            aktifkanbatch = "no";
        }

        kdgudang.setText("GD");
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());

        tabMode = new DefaultTableModel(null, new Object[]{
            "Jml", "Satuan Beli", "Kode Barang", "Nama Barang", "Satuan", "G", "Kadaluwarsa"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0) || (colIndex == 5) || (colIndex == 6)) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.Boolean.class, java.lang.String.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(42);
            } else if (i == 1) {
                column.setPreferredWidth(65);
            } else if (i == 2) {
                column.setPreferredWidth(85);
            } else if (i == 3) {
                column.setPreferredWidth(190);
            } else if (i == 4) {
                column.setPreferredWidth(50);
            } else if (i == 5) {
                column.setPreferredWidth(22);
            } else if (i == 6) {
                column.setPreferredWidth(75);
            } else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        warna.kolom = 0;
        tbDokter.setDefaultRenderer(Object.class, warna);

        NoFaktur.setDocument(new batasInput((byte) 20).getKata(NoFaktur));
//        NoOrder.setDocument(new batasInput((byte)20).getKata(NoOrder));
        kdsup.setDocument(new batasInput((byte) 5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte) 25).getKata(kdptg));
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

        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgPemesanan")) {
                    if (bangsal.getTable().getSelectedRow() != -1) {
                        kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                        nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString());
                    }
                }
                kdgudang.requestFocus();
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

        form.suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgPemesanan")) {
                    if (form.suplier.getTable().getSelectedRow() != -1) {
                        kdsup.setText(form.suplier.getTable().getValueAt(form.suplier.getTable().getSelectedRow(), 0).toString());
                        nmsup.setText(form.suplier.getTable().getValueAt(form.suplier.getTable().getSelectedRow(), 1).toString());
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

        form.suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgPemesanan")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        form.suplier.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        form.petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgPemesanan")) {
                    if (form.petugas.getTable().getSelectedRow() != -1) {
                        kdptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(), 0).toString());
                        nmptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(), 1).toString());
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

        datakonversi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (datakonversi.getTable().getSelectedRow() != -1) {
                    tbDokter.setValueAt(datakonversi.getTable().getValueAt(datakonversi.getTable().getSelectedRow(), 1).toString(), tbDokter.getSelectedRow(), 1);
                    try {
                        tbDokter.setValueAt(Double.parseDouble(datakonversi.getTable().getValueAt(datakonversi.getTable().getSelectedRow(), 3).toString()), tbDokter.getSelectedRow(), 25);
                    } catch (Exception er) {
                        JOptionPane.showMessageDialog(null, "Gagal mengambil nilai konversi, nilai barang satuan kecil dianggap bernilai 1..!!");
                        tbDokter.setValueAt(1, tbDokter.getSelectedRow(), 25);
                    }

                    try {
                        tbDokter.setValueAt(Double.parseDouble(datakonversi.getTable().getValueAt(datakonversi.getTable().getSelectedRow(), 0).toString()), tbDokter.getSelectedRow(), 26);
                    } catch (Exception er) {
                        JOptionPane.showMessageDialog(null, "Gagal mengambil nilai konversi, nilai barang satuan besar dianggap bernilai 1..!!");
                        tbDokter.setValueAt(1, tbDokter.getSelectedRow(), 26);
                    }
                }
                tbDokter.requestFocus();
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

        datakonversi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    datakonversi.dispose();
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

        Kd2 = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnTambah = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label11 = new widget.Label();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label20 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        btnGudang = new widget.Button();
        TglPesan = new widget.Tanggal1();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Peminjaman Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
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
        tbDokter.setToolTipText("Masukkan jumlah geser ke kanan");
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDokterPropertyChange(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 107));
        panelisi1.setLayout(null);

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
        panelisi1.add(BtnSimpan);
        BtnSimpan.setBounds(10, 60, 100, 30);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label10);
        label10.setBounds(110, 65, 75, 23);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);
        TCari.setBounds(190, 65, 280, 23);

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
        BtnCari1.setBounds(472, 65, 28, 23);

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
        BtnCari.setBounds(560, 62, 100, 30);

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
        BtnKeluar.setBounds(670, 62, 100, 30);

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
        BtnTambah.setBounds(501, 65, 28, 23);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label15.setText("No.Pinjam :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 80, 23);

        NoFaktur.setEnabled(false);
        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(80, 10, 230, 23);

        label11.setText("Tgl.Pinjam :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 80, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(337, 40, 70, 23);

        kdsup.setEditable(false);
        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(409, 10, 80, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(327, 10, 80, 23);

        kdptg.setEditable(false);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(409, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(491, 10, 240, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(491, 40, 240, 23);

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
        panelisi3.add(btnSuplier);
        btnSuplier.setBounds(734, 10, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(734, 40, 28, 23);

        label20.setText("Lokasi :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label20);
        label20.setBounds(0, 70, 80, 23);

        kdgudang.setEditable(false);
        kdgudang.setEnabled(false);
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(80, 70, 80, 23);

        nmgudang.setEditable(false);
        nmgudang.setEnabled(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(160, 70, 240, 23);

        btnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnGudang.setMnemonic('2');
        btnGudang.setToolTipText("Alt+2");
        btnGudang.setEnabled(false);
        btnGudang.setName("btnGudang"); // NOI18N
        btnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGudangActionPerformed(evt);
            }
        });
        panelisi3.add(btnGudang);
        btnGudang.setBounds(400, 70, 28, 23);

        TglPesan.setName("TglPesan"); // NOI18N
        panelisi3.add(TglPesan);
        TglPesan.setBounds(80, 40, 117, 28);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.emptTeks();
        form.isCek();
        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        form.suplier.dispose();
        form.petugas.dispose();
        bangsal.dispose();
        form.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnSimpan, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed
    /*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (aktifkanbatch.equals("yes")) {
            row = 0;
            jml = tbDokter.getRowCount();
            for (i = 0; i < jml; i++) {
                if ((Valid.SetAngka(tbDokter.getValueAt(i, 0).toString()) > 0)) {
                    row++;
                }
            }
        }

        if (NoFaktur.getText().trim().equals("")) {
            Valid.textKosong(NoFaktur, "No.Faktur");
        } else if (nmsup.getText().trim().equals("")) {
            Valid.textKosong(kdsup, "Supplier");
        } else if (nmptg.getText().trim().equals("")) {
            Valid.textKosong(kdptg, "Petugas");
        } else if (nmgudang.getText().trim().equals("")) {
            Valid.textKosong(kdgudang, "Lokasi");
        } else if (aktifkanbatch.equals("yes") && (row > 0)) {
            Valid.textKosong(TCari, "No.Batch");
        } else if (tbDokter.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses = true;
                if (Sequel.menyimpantf2("pinjam_obat", "?,?,?,?,?,?,?", "No.Faktur", 7, new String[]{
                    NoFaktur.getText(), kdsup.getText(), kdptg.getText(), Valid.SetDateToString(TglPesan.getDate()),
                    "" + sbttl, kdgudang.getText(), "Dipinjam"
                }) == true) {
                    jml = tbDokter.getRowCount();
                    for (i = 0; i < jml; i++) {
//                        setKonversi(i);
                        try {
                            if (Valid.SetAngka(tbDokter.getValueAt(i, 0).toString()) > 0) {
                                if (Sequel.menyimpantf2("detailpinjamobat", "?,?,?,?,?", "Transaksi Pinjam Obat", 5, new String[]{
                                    NoFaktur.getText(),
                                    tbDokter.getValueAt(i, 2).toString(),
                                    tbDokter.getValueAt(i, 1).toString(),
                                    tbDokter.getValueAt(i, 0).toString(),
                                    Valid.SetTgl(tbDokter.getValueAt(i, 6).toString() + "")
                                }) == true) {
                                    if (aktifkanbatch.equals("yes")) {
                                        Trackobat.catatRiwayat(tbDokter.getValueAt(i, 2).toString(), Valid.SetAngka(tbDokter.getValueAt(i, 12).toString()), 0, "Pinjam Obat", akses.getkode(), kdgudang.getText(), "Simpan", "", NoFaktur.getText());
                                        Sequel.menyimpan("gudangbarang", "'" + tbDokter.getValueAt(i, 2).toString() + "','" + kdgudang.getText() + "','" + tbDokter.getValueAt(i, 0).toString() + "','','" + NoFaktur.getText() + "'",
                                                "stok=stok+'" + tbDokter.getValueAt(i, 0).toString() + "'", "kode_brng='" + tbDokter.getValueAt(i, 2).toString() + "' and kd_bangsal='" + kdgudang.getText() + "' and no_faktur='" + NoFaktur.getText() + "'");
                                    } else {
                                        Trackobat.catatRiwayat(tbDokter.getValueAt(i, 2).toString(), Valid.SetAngka(tbDokter.getValueAt(i, 0).toString()), 0, "Pinjam Obat", akses.getkode(), kdgudang.getText(), "Simpan", "", "");
                                        Sequel.menyimpan("gudangbarang", "'" + tbDokter.getValueAt(i, 2).toString() + "','" + kdgudang.getText() + "','" + tbDokter.getValueAt(i, 0).toString() + "','',''",
                                                "stok=stok+'" + tbDokter.getValueAt(i, 0).toString() + "'", "kode_brng='" + tbDokter.getValueAt(i, 2).toString() + "' and kd_bangsal='" + kdgudang.getText() + "' and no_batch='' and no_faktur=''");
                                    }

//                                    simpanbatch();
                                } else {
                                    sukses = false;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        }
                    }
                    if (sukses == true) {
                        Sequel.queryu("delete from tampjurnal");
                        Sequel.menyimpan2("tampjurnal", "?,?,?,?", 4, new String[]{Sequel.cariIsi("select Pemesanan_Obat from set_akun"), "PERSEDIAAN BARANG", "" + (ttl + ppn + meterai), "0"});
                        Sequel.menyimpan2("tampjurnal", "?,?,?,?", 4, new String[]{Sequel.cariIsi("select Kontra_Pemesanan_Obat from set_akun"), "HUTANG USAHA", "0", "" + (ttl + ppn + meterai)});
                        sukses = jur.simpanJurnal(NoFaktur.getText(), Valid.SetDateToString(TglPesan.getDate()), "U", "PENERIMAAN BARANG DI " + nmgudang.getText().toUpperCase() + ", OLEH " + akses.getkode());
                    }
                } else {
                    sukses = false;
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan No.Faktur sudah ada sebelumnya...!!");
                }

                if (sukses == true) {
                    Sequel.Commit();
                    jml = tbDokter.getRowCount();
                    if (aktifkanbatch.equals("yes")) {
                        for (i = 0; i < jml; i++) {
                            tbDokter.setValueAt("", i, 0);
                            tbDokter.setValueAt(true, i, 5);
                        }
                    } else {
                        for (i = 0; i < jml; i++) {
                            tbDokter.setValueAt("", i, 0);
                            tbDokter.setValueAt(false, i, 5);
                        }
                    }
//                    getData();
                    JOptionPane.showMessageDialog(rootPane, "Berhasil Menyimpan Data.");

                } else {
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                autoNomor();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnKeluar, TCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        tampil();
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        BtnCari1.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
        kdsup.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        tbDokter.requestFocus();
    }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
    tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        tampil();
    } else {
        Valid.pindah(evt, BtnSimpan, BtnKeluar);
    }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for (i = 0; i < tbDokter.getRowCount(); i++) {
        tbDokter.setValueAt("", i, 0);
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
    if (tbDokter.getRowCount() != 0) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DELETE:
                    try {
                if (tbDokter.getSelectedColumn() == 0) {
                    tbDokter.setValueAt(null, tbDokter.getSelectedRow(), 0);
                }
            } catch (Exception e) {
            }
            break;
            case KeyEvent.VK_BACK_SPACE:
                    try {
                if (tbDokter.getSelectedColumn() == 0) {
                    if (tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().equals("0") || tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().equals("0.0") || tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().equals("0,0")) {
                        tbDokter.setValueAt("", tbDokter.getSelectedRow(), 0);
                    }
                }
            } catch (Exception e) {
            }
            break;
            case KeyEvent.VK_SHIFT:
                TCari.setText("");
                TCari.requestFocus();
                break;
            case KeyEvent.VK_SPACE:
                if (tbDokter.getSelectedColumn() == 1) {
                    y = 0;
                    try {
                        y = Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                    } catch (Exception e) {
                        y = 0;
                    }
                    if (y > 0) {
                        datakonversi.setSatuanKecil(tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                        datakonversi.isCek();
                        datakonversi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight());
                        datakonversi.setLocationRelativeTo(internalFrame1);
                        datakonversi.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Silahkan masukkan jumlah pembelian terelebih dahulu..!!");
                    }
                }
                break;
            default:
                break;
        }
    }
}//GEN-LAST:event_tbDokterKeyPressed

private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
    Valid.pindah(evt, BtnSimpan, kdsup);
}//GEN-LAST:event_NoFakturKeyPressed

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

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
        Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
        kdsup.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
        kdgudang.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        btnPetugasActionPerformed(null);
    }
}//GEN-LAST:event_kdptgKeyPressed

private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
    akses.setform("DlgPemesanan");
    form.suplier.emptTeks();
    form.suplier.isCek();
    form.suplier.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    form.suplier.setLocationRelativeTo(internalFrame1);
    form.suplier.setAlwaysOnTop(false);
    form.suplier.setVisible(true);
}//GEN-LAST:event_btnSuplierActionPerformed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
    akses.setform("DlgPemesanan");
    form.petugas.emptTeks();
    form.petugas.isCek();
    form.petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    form.petugas.setLocationRelativeTo(internalFrame1);
    form.petugas.setAlwaysOnTop(false);
    form.petugas.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
        kdptg.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
        TCari.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        btnGudangActionPerformed(null);
    }
}//GEN-LAST:event_kdgudangKeyPressed

private void btnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGudangActionPerformed
    akses.setform("DlgPemesanan");
    bangsal.isCek();
    bangsal.emptTeks();
    bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    bangsal.setLocationRelativeTo(internalFrame1);
    bangsal.setAlwaysOnTop(false);
    bangsal.setVisible(true);
}//GEN-LAST:event_btnGudangActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (tampikan == true) {
            tampil();
        }
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarang barang = new DlgBarang(null, false);
        if (!nmgudang.getText().trim().equals("")) {
            akses.setform("tampil3");
            barang.setLokasi(kdgudang.getText(), nmgudang.getText());
        } else {
            akses.setform("tampil");
        }
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());

    }//GEN-LAST:event_BtnTambahActionPerformed

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if (this.isVisible() == true) {
//            getData();                
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked

    }//GEN-LAST:event_tbDokterMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPinjamObat dialog = new DlgPinjamObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Kd2;
    private widget.TextBox NoFaktur;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal1 TglPesan;
    private widget.Button btnGudang;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdgudang;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label20;
    private widget.TextBox nmgudang;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        row = tbDokter.getRowCount();
        jml = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Double.parseDouble(tbDokter.getValueAt(i, 0).toString()) > 0) {
                    jml++;
                }
            } catch (Exception e) {
                jml = jml + 0;
            }
        }

        kodebarang = null;
        namabarang = null;
        satuan = null;
        satuanbeli = null;
        kadaluwarsa = null;
        nobatch = null;
        harga = null;
        jumlah = null;
        subtotal = null;
        diskon = null;
        besardiskon = null;
        jmltotal = null;
        jmlstok = null;
        hpp = null;
        ralan = null;
        kelas1 = null;
        kelas2 = null;
        kelas3 = null;
        utama = null;
        vip = null;
        vvip = null;
        beliluar = null;
        jualbebas = null;
        karyawan = null;
        ganti = null;
        kodebarang = new String[jml];
        namabarang = new String[jml];
        satuan = new String[jml];
        satuanbeli = new String[jml];
        kadaluwarsa = new String[jml];
        nobatch = new String[jml];
        harga = new double[jml];
        jumlah = new double[jml];
        subtotal = new double[jml];
        diskon = new double[jml];
        besardiskon = new double[jml];
        jmltotal = new double[jml];
        jmlstok = new double[jml];
        hpp = new double[jml];
        ralan = new double[jml];
        kelas1 = new double[jml];
        kelas2 = new double[jml];
        kelas3 = new double[jml];
        utama = new double[jml];
        vip = new double[jml];
        vvip = new double[jml];
        beliluar = new double[jml];
        jualbebas = new double[jml];
        karyawan = new double[jml];
        isi = new double[jml];
        jmlbesar = new double[jml];
        dasar = new double[jml];
        ganti = new boolean[jml];

        index = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Valid.SetAngka(tbDokter.getValueAt(i, 0).toString()) > 0) {
                    jumlah[index] = Double.parseDouble(tbDokter.getValueAt(i, 0).toString());
                    satuanbeli[index] = tbDokter.getValueAt(i, 1).toString();
                    kodebarang[index] = tbDokter.getValueAt(i, 2).toString();
                    namabarang[index] = tbDokter.getValueAt(i, 3).toString();
                    satuan[index] = tbDokter.getValueAt(i, 4).toString();
                    ganti[index] = Boolean.parseBoolean(tbDokter.getValueAt(i, 5).toString());
                    kadaluwarsa[index] = tbDokter.getValueAt(i, 6).toString();
                    index++;
                }
            } catch (Exception e) {
                System.out.println("e " + e);
            }
        }
        Valid.tabelKosong(tabMode);
        for (i = 0; i < jml; i++) {
            tabMode.addRow(new Object[]{
                jumlah[i], satuanbeli[i], kodebarang[i], namabarang[i], satuan[i], ganti[i], kadaluwarsa[i]
            });
        }

        try {
            ps = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,databarang.kode_sat, databarang.h_beli, "
                    + " ifnull(date_format(databarang.expire,'%d-%m-%Y'),'00-00-0000'),databarang.kode_satbesar,databarang.isi, "
                    + " (databarang.h_beli*databarang.isi) as hargabesar from databarang inner join jenis on databarang.kdjns=jenis.kdjns "
                    + " where databarang.status='1' and databarang.kode_brng like ? or "
                    + " databarang.status='1' and databarang.nama_brng like ? or "
                    + " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            try {
                ps.setString(1, "%" + TCari.getText().trim() + "%");
                ps.setString(2, "%" + TCari.getText().trim() + "%");
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                if (aktifkanbatch.equals("yes")) {
                    while (rs.next()) {
                        tabMode.addRow(new Object[]{
                            "", rs.getString(6), rs.getString(1),
                            rs.getString(2), rs.getString(3), true,
                            rs.getString(5)
//                                ,rs.getDouble(8),0,0,0,0,0,"",
//                            0,0,0,0,0,0,0,0,0,0,0,rs.getDouble(7),1,0
                        });
                    }
                } else {
                    while (rs.next()) {
                        tabMode.addRow(new Object[]{
                            "", rs.getString(6), rs.getString(1),
                            rs.getString(2), rs.getString(3), false,
                            rs.getString(5)
//                                ,rs.getDouble(8),0,0,0,0,0,"",
//                            0,0,0,0,0,0,0,0,0,0,0,rs.getDouble(7),1,0
                        });
                    }
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
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    /**
     *
     */
    public void isCek() {
        autoNomor();
        TCari.requestFocus();
        if (akses.getjml2() >= 1) {
            kdptg.setEditable(false);
            btnPetugas.setEnabled(false);
            kdptg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getpemesanan_obat());
            BtnTambah.setEnabled(akses.getobat());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
        } else {
            kdptg.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
        }
    }

    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_faktur,3),signed)),0) from pinjam_obat where tgl_pinjam='" + Valid.SetDateToString(TglPesan.getDate()) + "'", "PB" + Valid.SetDateToString(TglPesan.getDate()).substring(0, 4) + Valid.SetDateToString(TglPesan.getDate()).substring(5, 7) + Valid.SetDateToString(TglPesan.getDate()).substring(8, 10), 3, NoFaktur);
    }

    /**
     *
     * @param nofaktur
     */
    public void tampil(String nofaktur) {
        NoFaktur.setText(nofaktur);
        kdsup.setText(Sequel.cariIsi("select kode_suplier from surat_pemesanan_medis where no_pemesanan=?", nofaktur));
        nmsup.setText(Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", kdsup.getText()));
        meterai = Sequel.cariIsiAngka("select meterai from surat_pemesanan_medis where no_pemesanan=?", nofaktur);
        ppn = Sequel.cariIsiAngka("select ppn from surat_pemesanan_medis where no_pemesanan=?", nofaktur);
        try {
            Valid.tabelKosong(tabMode);
            ps = koneksi.prepareStatement(
                    "select detail_surat_pemesanan_medis.kode_brng, databarang.nama_brng,"
                    + "detail_surat_pemesanan_medis.kode_sat,detail_surat_pemesanan_medis.h_pesan, "
                    + "ifnull(date_format(databarang.expire,'%d-%m-%Y'),'00-00-0000'),"
                    + "detail_surat_pemesanan_medis.jumlah,detail_surat_pemesanan_medis.subtotal,"
                    + "detail_surat_pemesanan_medis.dis,detail_surat_pemesanan_medis.besardis,"
                    + "detail_surat_pemesanan_medis.total,detail_surat_pemesanan_medis.jumlah2,databarang.kode_sat as satbar, "
                    + "databarang.isi from databarang inner join jenis inner join detail_surat_pemesanan_medis "
                    + "on databarang.kdjns=jenis.kdjns and detail_surat_pemesanan_medis.kode_brng=databarang.kode_brng "
                    + " where detail_surat_pemesanan_medis.no_pemesanan=? order by databarang.nama_brng");
            try {
                ps.setString(1, nofaktur);
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        rs.getDouble("jumlah"), rs.getString(3), rs.getString(1),
                        rs.getString(2), rs.getString("satbar"), false, rs.getString(5)
//                        ,rs.getDouble(4),rs.getDouble("subtotal"),rs.getDouble("dis"),
//                        rs.getDouble("besardis"),rs.getDouble("total"),rs.getDouble("jumlah2"),"",
//                        0,0,0,0,0,0,0,0,0,0,0,rs.getDouble("isi"),1,0
                    });
                }
//                getData();
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
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }
    /*
    private void simpanbatch() {
        if ((!tbDokter.getValueAt(i, 13).toString().equals("")) && (!tbDokter.getValueAt(i, 14).toString().equals(""))
                && (!tbDokter.getValueAt(i, 15).toString().equals("")) && (!tbDokter.getValueAt(i, 16).toString().equals(""))
                && (!tbDokter.getValueAt(i, 17).toString().equals("")) && (!tbDokter.getValueAt(i, 18).toString().equals(""))
                && (!tbDokter.getValueAt(i, 19).toString().equals("")) && (!tbDokter.getValueAt(i, 20).toString().equals(""))
                && (!tbDokter.getValueAt(i, 21).toString().equals("")) && (!tbDokter.getValueAt(i, 22).toString().equals(""))
                && (!tbDokter.getValueAt(i, 23).toString().equals("")) && (!tbDokter.getValueAt(i, 24).toString().equals(""))
                && (!tbDokter.getValueAt(i, 27).toString().equals(""))) {
            Sequel.menyimpan2("data_batch", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Batch", 20, new String[]{
                tbDokter.getValueAt(i, 13).toString(), tbDokter.getValueAt(i, 2).toString(), Valid.SetTgl(TglPesan.getSelectedItem() + ""), Valid.SetTgl(tbDokter.getValueAt(i, 6).toString()), "Penerimaan", NoFaktur.getText(),
                tbDokter.getValueAt(i, 27).toString(), tbDokter.getValueAt(i, 24).toString(), tbDokter.getValueAt(i, 14).toString(), tbDokter.getValueAt(i, 15).toString(), tbDokter.getValueAt(i, 16).toString(), tbDokter.getValueAt(i, 17).toString(),
                tbDokter.getValueAt(i, 18).toString(), tbDokter.getValueAt(i, 19).toString(), tbDokter.getValueAt(i, 20).toString(), tbDokter.getValueAt(i, 21).toString(), tbDokter.getValueAt(i, 22).toString(), tbDokter.getValueAt(i, 23).toString(),
                tbDokter.getValueAt(i, 12).toString(), tbDokter.getValueAt(i, 12).toString()
            });
        }
        if (akses.getobat() == true) {
            if (tbDokter.getValueAt(i, 5).toString().equals("true")) {
                Sequel.mengedit("databarang", "kode_brng=?", "expire=?", 2, new String[]{
                    Valid.SetTgl(tbDokter.getValueAt(i, 6).toString()), tbDokter.getValueAt(i, 2).toString()
                });
            }
        }
    }
     */
}

/*
 * DlgMappingKamarInap.java
 *
 * Mapping BANGSAL -> KELOMPOK RAWAT INAP (RANAP ATAS, RANAP BAWAH, ICU, NICU, PICU, HCU).
 * Dipanggil dari tombol "MAPPING KAMAR" di DlgKamarInap (Daftar Pasien Rawat Inap).
 * Data disimpan di tabel mapping_kelompok_ranap (lihat 07_mapping_kelompok_ranap.sql).
 * Aturan: mapping manual menang; bila belum dimapping, bangsal otomatis masuk HCU/ICU/NICU/PICU jika NAMA bangsalnya memuat kata itu;
 * selain itu masuk "KAMAR LAINNYA" (hanya kamar aktif).
 */

package simrskhanza;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.koneksiDB;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public final class DlgMappingKamarInap extends javax.swing.JDialog {
    /** Daftar kelompok (urutan = urutan tombol di DlgKamarInap). Harus sama dengan ENUM tabel mapping_kelompok_ranap. */
    public static final String[] KELOMPOK = {"RANAP ATAS", "RANAP BAWAH", "ICU", "NICU", "PICU", "HCU"};
    /** Bangsal tanpa kelompok tetapi punya kamar aktif -> masuk tombol KAMAR LAINNYA di DlgKamarInap. */
    private static final String LAINNYA = "KAMAR LAINNYA";
    /** Bangsal tanpa kelompok dan semua kamarnya nonaktif (tidak masuk kelompok mana pun, termasuk KAMAR LAINNYA). */
    private static final String BELUM = "BELUM DIMAPPING (kamar nonaktif)";
    /** Kolom tersembunyi pada model tabel: isi mapping manual dan isi aturan nama. */
    private static final int KOL_MANUAL = 5;
    private static final int KOL_OTOMATIS = 6;

    /** Aturan otomatis dari NAMA bangsal (dipakai bila bangsal belum dimapping manual).
     *  Urutan penting: NICU dan PICU harus dicek sebelum ICU karena namanya memuat "ICU". */
    public static final String SQL_DARI_NAMA = "case when bangsal.nm_bangsal like '%NICU%' then 'NICU' "
            + "when bangsal.nm_bangsal like '%PICU%' then 'PICU' "
            + "when bangsal.nm_bangsal like '%HCU%' then 'HCU' "
            + "when bangsal.nm_bangsal like '%ICU%' then 'ICU' end";
    /** Kelompok efektif sebuah bangsal = mapping manual (tabel mapping_kelompok_ranap) ; bila tidak ada -> dari nama ; bila tidak cocok -> null.
     *  Memakai alias tabel "bangsal" (sama dengan query di DlgKamarInap). */
    public static final String SQL_KELOMPOK = "ifnull((select mapping_kelompok_ranap.kelompok from mapping_kelompok_ranap where mapping_kelompok_ranap.kd_bangsal=bangsal.kd_bangsal)," + SQL_DARI_NAMA + ")";

    private final DefaultTableModel tabMode;
    private final Connection koneksi = koneksiDB.condb();
    private final validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;

    /** Cek tabel mapping ada dan hitung jumlah bangsal (yang punya kamar) pada satu kelompok, termasuk yang masuk otomatis karena kesamaan nama.
     *  @return -1 bila tabel mapping_kelompok_ranap belum dibuat, selain itu jumlah bangsal pada kelompok tsb. */
    public static int jumlahBangsalKelompok(Connection con, String kelompok) {
        try (PreparedStatement p = con.prepareStatement("select count(bangsal.kd_bangsal) from bangsal where exists(select kamar.kd_kamar from kamar where kamar.kd_bangsal=bangsal.kd_bangsal) and " + SQL_KELOMPOK + "=?")) {
            p.setString(1, kelompok);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? r.getInt(1) : 0;
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            return -1;
        }
    }

    /**
     * Creates new form DlgMappingKamarInap
     * @param parent
     * @param modal
     */
    public DlgMappingKamarInap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(780, 480);

        tabMode = new DefaultTableModel(null, new Object[]{"Kode Bangsal", "Nama Bangsal", "Jml Kamar", "Kamar Aktif", "Kelompok", "Manual", "Otomatis"}) {
            @Override public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbBangsal.setModel(tabMode);
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbBangsal.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        for (i = 0; i < 5; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(90);
            } else if (i == 1) {
                column.setPreferredWidth(190);
            } else if (i == 2) {
                column.setPreferredWidth(75);
            } else if (i == 3) {
                column.setPreferredWidth(80);
            } else if (i == 4) {
                column.setPreferredWidth(270);
            }
        }
        // dua kolom bantu (mapping manual & aturan nama) disembunyikan dari tampilan, datanya tetap ada di model (tabMode)
        tbBangsal.removeColumn(tbBangsal.getColumnModel().getColumn(KOL_OTOMATIS));
        tbBangsal.removeColumn(tbBangsal.getColumnModel().getColumn(KOL_MANUAL));
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        CmbKelompok.setModel(new DefaultComboBoxModel(KELOMPOK));
        String[] tampilan = new String[KELOMPOK.length + 2];
        tampilan[0] = "Semua";
        tampilan[1] = LAINNYA;
        System.arraycopy(KELOMPOK, 0, tampilan, 2, KELOMPOK.length);
        CmbTampil.setModel(new DefaultComboBoxModel(tampilan));

        // tombol ESC menutup jendela
        getRootPane().registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /** Hak akses: hanya user yang boleh mengelola Kamar yang boleh mengubah mapping. */
    public void isCek() {
        BtnTerapkan.setEnabled(akses.getkamar());
        BtnHapus.setEnabled(akses.getkamar());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        internalFrame1 = new widget.InternalFrame();
        panelisi1 = new widget.panelisi();
        label1 = new widget.Label();
        CmbKelompok = new widget.ComboBox();
        BtnTerapkan = new widget.Button();
        BtnHapus = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label2 = new widget.Label();
        CmbTampil = new widget.ComboBox();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Mapping Kamar Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label1.setText("Kelompok :");
        label1.setName("label1"); // NOI18N
        label1.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi1.add(label1);

        CmbKelompok.setName("CmbKelompok"); // NOI18N
        CmbKelompok.setPreferredSize(new java.awt.Dimension(130, 23));
        panelisi1.add(CmbKelompok);

        BtnTerapkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnTerapkan.setMnemonic('T');
        BtnTerapkan.setText("Terapkan ke baris terpilih");
        BtnTerapkan.setToolTipText("Alt+T. Pilih satu atau beberapa bangsal pada tabel (Ctrl/Shift), pilih kelompok, lalu klik tombol ini. Bangsal yang sudah punya kelompok harus dihapus mappingnya dulu");
        BtnTerapkan.setName("BtnTerapkan"); // NOI18N
        BtnTerapkan.setPreferredSize(new java.awt.Dimension(250, 25));
        BtnTerapkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTerapkanActionPerformed(evt);
            }
        });
        panelisi1.add(BtnTerapkan);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus mapping");
        BtnHapus.setToolTipText("Alt+H. Hapus mapping manual bangsal yang dipilih. Bangsal kembali ke KAMAR LAINNYA (atau ke HCU/ICU/NICU/PICU bila namanya cocok) dan baru boleh dimasukkan ke kelompok lain");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(150, 25));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelisi1.add(BtnHapus);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_START);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setAutoCreateRowSorter(false);
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.setToolTipText("Pilih satu atau beberapa bangsal (Ctrl/Shift + klik), lalu pilih kelompok dan klik Terapkan");
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(130, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        panelisi3.add(BtnAll);

        label2.setText("Tampilkan :");
        label2.setName("label2"); // NOI18N
        label2.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label2);

        CmbTampil.setName("CmbTampil"); // NOI18N
        CmbTampil.setPreferredSize(new java.awt.Dimension(130, 23));
        CmbTampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbTampilActionPerformed(evt);
            }
        });
        panelisi3.add(CmbTampil);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi3.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 25));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            tbBangsal.requestFocus();
        }
    }

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        tampil();
    }

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {
        TCari.setText("");
        if (CmbTampil.getSelectedIndex() == 0) {
            tampil();
        } else {
            CmbTampil.setSelectedIndex(0); // memicu CmbTampilActionPerformed -> tampil()
        }
    }

    private void CmbTampilActionPerformed(java.awt.event.ActionEvent evt) {
        if (tabMode != null) {
            tampil();
        }
    }

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void BtnTerapkanActionPerformed(java.awt.event.ActionEvent evt) {
        simpan(false);
    }

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        simpan(true);
    }

    /** Kelompok yang berlaku untuk satu baris tabel: mapping manual, bila tidak ada -> aturan nama, bila tidak ada -> "" (KAMAR LAINNYA / nonaktif). */
    private String kelompokBaris(int baris) {
        String manual = String.valueOf(tabMode.getValueAt(baris, KOL_MANUAL));
        if (!manual.equals("")) {
            return manual;
        }
        return String.valueOf(tabMode.getValueAt(baris, KOL_OTOMATIS));
    }

    /**
     * Terapkan kelompok terpilih (atau hapus mapping) ke semua bangsal yang dipilih pada tabel.
     * Aturan: satu bangsal hanya boleh berada di satu kelompok. Bangsal yang sudah punya kelompok
     * (mapping manual atau otomatis dari nama) tidak bisa dipindah ke kelompok lain; mappingnya harus
     * dihapus dulu (setelah dihapus bangsal kembali ke KAMAR LAINNYA, atau ke kelompok otomatis dari nama).
     */
    private void simpan(boolean hapus) {
        int[] baris = tbBangsal.getSelectedRows();
        if (baris.length == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih dulu bangsal pada tabel (boleh lebih dari satu dengan Ctrl/Shift)...!!!!");
            tbBangsal.requestFocus();
            return;
        }
        if (hapus) {
            hapusMapping(baris);
        } else {
            terapkanMapping(baris, CmbKelompok.getSelectedItem().toString());
        }
    }

    private void terapkanMapping(int[] baris, String kelompok) {
        // 1) periksa semua baris dulu; bila ada yang terkunci kelompok lain, tidak ada yang diubah (semua atau tidak sama sekali)
        StringBuilder terkunci = new StringBuilder();
        int jmlTerkunci = 0;
        boolean adaOtomatis = false;
        java.util.List<String> kodeBaru = new java.util.ArrayList<>();
        for (int b = 0; b < baris.length; b++) {
            String kd = tabMode.getValueAt(baris[b], 0).toString();
            String nm = tabMode.getValueAt(baris[b], 1).toString();
            String ada = kelompokBaris(baris[b]);
            if (ada.equals("")) {
                kodeBaru.add(kd);                       // belum punya kelompok -> boleh
            } else if (!ada.equals(kelompok)) {
                jmlTerkunci++;
                if (jmlTerkunci <= 15) {
                    boolean otomatis = String.valueOf(tabMode.getValueAt(baris[b], KOL_MANUAL)).equals("");
                    terkunci.append("\n- ").append(nm).append(" (").append(kd).append(") : sudah di ").append(ada).append(otomatis ? " (otomatis dari nama)" : "");
                }
                if (String.valueOf(tabMode.getValueAt(baris[b], KOL_MANUAL)).equals("")) {
                    adaOtomatis = true;
                }
            }                                           // sudah di kelompok yang sama -> tidak ada yang perlu disimpan
        }
        if (jmlTerkunci > 0) {
            if (jmlTerkunci > 15) {
                terkunci.append("\n- ... dan ").append(jmlTerkunci - 15).append(" bangsal lainnya");
            }
            JOptionPane.showMessageDialog(null, "Maaf, bangsal berikut sudah masuk kelompok lain dan tidak bisa langsung dipindah ke " + kelompok + " :" + terkunci
                    + "\n\nHapus dulu mappingnya (tombol 'Hapus mapping'), setelah itu bangsal kembali ke KAMAR LAINNYA dan bisa dimasukkan ke kelompok baru."
                    + (adaOtomatis ? "\nBangsal bertanda 'otomatis dari nama' tidak punya mapping manual; kelompoknya mengikuti nama bangsal (HCU/ICU/NICU/PICU)." : "")
                    + "\n\nTidak ada data yang diubah...!!!!");
            tbBangsal.requestFocus();
            return;
        }
        // 2) simpan; INSERT biasa (tanpa menimpa) supaya mapping milik kelompok lain tidak tertimpa, juga bila baru diubah dari komputer lain
        int sukses = 0;
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            for (String kd : kodeBaru) {
                ps = koneksi.prepareStatement("insert into mapping_kelompok_ranap(kd_bangsal,kelompok) values(?,?)");
                try {
                    ps.setString(1, kd);
                    ps.setString(2, kelompok);
                    ps.executeUpdate();
                    sukses++;
                } finally {
                    if (ps != null) {
                        ps.close();
                    }
                }
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Notifikasi : " + e);
            if (e.getErrorCode() == 1062 || (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate"))) {
                JOptionPane.showMessageDialog(null, "Maaf, ada bangsal yang ternyata sudah punya kelompok (mungkin baru dimapping dari komputer lain).\nTersimpan " + sukses + " bangsal. Daftar dimuat ulang, hapus dulu mapping lama bila ingin memindahkan...!!!!");
            } else {
                pesanTabel(e);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            pesanTabel(e);
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
        tampil();
    }

    private void hapusMapping(int[] baris) {
        int sukses = 0;
        int otomatis = 0;
        int kosong = 0;
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            for (int b = 0; b < baris.length; b++) {
                String kd = tabMode.getValueAt(baris[b], 0).toString();
                if (!String.valueOf(tabMode.getValueAt(baris[b], KOL_MANUAL)).equals("")) {
                    ps = koneksi.prepareStatement("delete from mapping_kelompok_ranap where kd_bangsal=?");
                    try {
                        ps.setString(1, kd);
                        ps.executeUpdate();
                        sukses++;
                    } finally {
                        if (ps != null) {
                            ps.close();
                        }
                    }
                } else if (!String.valueOf(tabMode.getValueAt(baris[b], KOL_OTOMATIS)).equals("")) {
                    otomatis++;
                } else {
                    kosong++;
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            pesanTabel(e);
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
        if (sukses > 0) {
            tampil();
        }
        if (otomatis > 0 || kosong > 0) {
            String m = (sukses > 0 ? "Mapping " + sukses + " bangsal sudah dihapus.\n" : "Maaf, tidak ada mapping yang dihapus.\n");
            if (otomatis > 0) {
                m += "\n" + otomatis + " bangsal masuk kelompok otomatis dari nama (HCU/ICU/NICU/PICU), tidak ada mapping manual yang bisa dihapus.";
            }
            if (kosong > 0) {
                m += "\n" + kosong + " bangsal memang belum punya kelompok (sudah di KAMAR LAINNYA / kamar nonaktif).";
            }
            JOptionPane.showMessageDialog(null, m);
        }
    }

    private void pesanTabel(Exception e) {
        String m = e.getMessage() == null ? "" : e.getMessage();
        if (m.contains("doesn't exist") || m.contains("does not exist")) {
            JOptionPane.showMessageDialog(null, "Maaf, tabel mapping_kelompok_ranap belum ada di database.\nSilahkan jalankan dulu file 07_mapping_kelompok_ranap.sql...!!!!");
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, mapping gagal disimpan...!!!!\n" + m);
        }
    }

    /** Muat daftar bangsal (yang punya kamar) beserta kelompoknya. */
    public void tampil() {
        Valid.tabelKosong(tabMode);
        String pilih = CmbTampil.getSelectedItem() == null ? "Semua" : CmbTampil.getSelectedItem().toString();
        String syarat = "";
        String having = "";
        boolean pakaiKelompok = false;
        if (pilih.equals(LAINNYA)) {
            // belum dimapping, namanya tidak cocok aturan nama, dan punya kamar aktif
            syarat = " and mapping_kelompok_ranap.kd_bangsal is null and " + SQL_DARI_NAMA + " is null ";
            having = " having sum(kamar.statusdata='1')>0 ";
        } else if (!pilih.equals("Semua")) {
            syarat = " and ifnull(mapping_kelompok_ranap.kelompok," + SQL_DARI_NAMA + ")=? ";
            pakaiKelompok = true;
        }
        try {
            ps = koneksi.prepareStatement(
                    "select bangsal.kd_bangsal,bangsal.nm_bangsal,count(kamar.kd_kamar) as jml,sum(kamar.statusdata='1') as aktif,"
                    + "ifnull(mapping_kelompok_ranap.kelompok,'') as kelompok,ifnull(" + SQL_DARI_NAMA + ",'') as dari_nama "
                    + "from bangsal inner join kamar on kamar.kd_bangsal=bangsal.kd_bangsal "
                    + "left join mapping_kelompok_ranap on mapping_kelompok_ranap.kd_bangsal=bangsal.kd_bangsal "
                    + "where (bangsal.kd_bangsal like ? or bangsal.nm_bangsal like ?) " + syarat
                    + "group by bangsal.kd_bangsal,bangsal.nm_bangsal,mapping_kelompok_ranap.kelompok" + having + " order by bangsal.nm_bangsal");
            try {
                int p = 1;
                ps.setString(p++, "%" + TCari.getText().trim() + "%");
                ps.setString(p++, "%" + TCari.getText().trim() + "%");
                if (pakaiKelompok) {
                    ps.setString(p++, pilih);
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    String manual = rs.getString("kelompok") == null ? "" : rs.getString("kelompok");
                    String otomatis = rs.getString("dari_nama") == null ? "" : rs.getString("dari_nama");
                    String tampilKel;
                    if (!manual.equals("")) {
                        tampilKel = manual;                                  // mapping manual (menang atas aturan nama)
                    } else if (!otomatis.equals("")) {
                        tampilKel = otomatis + " (otomatis dari nama)";
                    } else if (rs.getInt("aktif") > 0) {
                        tampilKel = LAINNYA;                                 // belum dimapping, ada kamar aktif
                    } else {
                        tampilKel = BELUM;
                    }
                    tabMode.addRow(new Object[]{
                        rs.getString("kd_bangsal"), rs.getString("nm_bangsal"), rs.getString("jml"), rs.getString("aktif"), tampilKel, manual, otomatis
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
                pesanTabel(e);
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
    }

    // Variables declaration
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnTerapkan;
    private widget.ComboBox CmbKelompok;
    private widget.ComboBox CmbTampil;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label2;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.Table tbBangsal;
    // End of variables declaration
}

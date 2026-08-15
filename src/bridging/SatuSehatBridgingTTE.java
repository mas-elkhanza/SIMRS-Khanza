/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;

import bridging.SatuSehatSignatureState.Status;
import com.fasterxml.jackson.databind.JsonNode;
import fungsi.akses;
import fungsi.koneksiDB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Dialog TTE (Tanda Tangan Elektronik) gaya master-detail SatuSehatBundle.
 * KIRI: daftar pasien/kunjungan yang Encounter-nya SUDAH terkirim ke SATUSEHAT
 *       (satu_sehat_encounter.id_encounter terisi), dengan filter tanggal + pencarian.
 * KANAN: begitu pasien dipilih, daftar berkas RME-nya (Resume/SPRI/...) beserta status TTE;
 *        berkas yang belum di-TTE bisa ditandatangani (buat Provenance+Task -> QR popup).
 *
 * Penandatanganan dilakukan di SATUSEHAT Mobile (SSM), bukan di sini. Jaringan di SwingWorker.
 */
public class SatuSehatBridgingTTE extends JDialog {

    /** Model berkas kandidat TTE (dirakit {@link SatuSehatSignatureAssembler} dari tabel satu_sehat_*). */
    /**
     * SATU BARIS TANDA TANGAN: satu dokumen dilihat dari sudut SATU penanda.
     *
     * Sejak TTE multi-signature (1 Agustus 2026) satu dokumen bisa menghasilkan beberapa
     * DokumenTte — satu per penanda — dengan {@code targetRef} yang sama tetapi
     * {@code signerIhs}, {@code peran}, dan {@code urutan} berbeda. Itulah sebabnya kolom
     * "Peran" ada di tabel: tanpa itu dua baris dokumen yang sama terlihat seperti duplikat.
     *
     * Model dokumennya ({@code single}/{@code paralel}/{@code serial}) menentukan apakah
     * baris ke-2 boleh dikerjakan bersamaan atau harus menunggu — lihat {@link #terkunci}.
     */
    public static class DokumenTte {
        public String jenis;
        public String targetRef;
        public String display;
        public String mulaiUtc;
        public String selesaiUtc;
        public String idProvenance = "";
        public String taskUuid = "";
        public String signer = "";     // nama pihak yang harus menandatangani baris ini
        public String signerIhs = "";  // IHS Practitioner penanda (agent Provenance)
        public Status status = Status.BUAT;

        /** Peran penanda pada baris ini: author/attester/verifier/performer/enterer. */
        public String peran = "author";
        /** Giliran tanda tangan. Model serial: 1,2,...; single & paralel: semuanya 1. */
        public int urutan = 1;
        /** Model TTE dokumen ini, menentukan cara Provenance & Task dibentuk. */
        public SatuSehatTteModel.Model model = SatuSehatTteModel.Model.SINGLE;
        /**
         * true bila baris ini BELUM boleh dikerjakan karena menunggu giliran sebelumnya
         * (hanya terjadi pada model serial). Baris terkunci tak bisa dicentang.
         */
        public boolean terkunci = false;
        /** Alasan terkunci, ditampilkan di kolom Status supaya operator tak menebak. */
        public String alasanKunci = "";
        /** Model serial: id Provenance giliran sebelumnya yang harus ditunjuk entity[]. */
        public String idProvenanceSebelumnya = "";

        public DokumenTte(String jenis, String targetRef, String display) {
            this.jenis = jenis;
            this.targetRef = targetRef;
            this.display = display;
        }

        /** Kunci baris: satu dokumen bisa muncul beberapa kali, dibedakan penandanya. */
        public String kunci() {
            return targetRef + "|" + signerIhs;
        }

        /** Label peran untuk kolom tabel, mis. "1 · Author". */
        public String labelPeran() {
            String p = peran.substring(0, 1).toUpperCase() + peran.substring(1);
            return model == SatuSehatTteModel.Model.SERIAL ? (urutan + " · " + p) : p;
        }
    }

    /**
     * Asal-usul pembaruan status, dicetak sebagai penanda di setiap baris log. Tanpa ini semua
     * jalur menghasilkan baris yang identik sehingga mustahil menilai apakah webhook benar-benar
     * bekerja atau sebenarnya polling yang menyelamatkan.
     */
    private enum Pemicu {
        WEBHOOK,      // notifikasi taskSignatureDone dari satu_sehat_task_webhook
        MANUAL,       // operator menekan tombol Update Status
        QR_SESSION;   // polling selama dialog QR terbuka

        String tag() {
            return String.format("[%-9s]", name().replace('_', '-'));
        }
    }

    private static final String SEMUA_BERKAS = "-- Semua Berkas --";
    private static final String SEMUA_DOKTER = "-- Semua Dokter --";
    private static final String SEMUA_STATUS = "-- Semua Status --";
    /** Urutan pilihan Filter Status; teksnya WAJIB sama persis dengan SatuSehatSignatureState.label(). */
    private static final String[] STATUS_PILIHAN =
            {"Sudah TTE", "Buat TTE", "Belum TTE", "Sedang Diproses", "Ditolak"};
    private static final String[] KOL_PASIEN =
            {"Tgl Registrasi", "No. Rawat", "No. RM", "Nama Pasien", "Dokter IGD", "DPJP", "ID Encounter", "Rawat"};
    // Kolom "Peran" WAJIB ada sejak TTE multi-signature: satu berkas bisa muncul beberapa kali,
    // satu baris per penanda. Tanpa kolom ini dua baris berkas yang sama terlihat seperti duplikat.
    private static final String[] KOL_DOK =
            {"", "Jenis Berkas", "Peran", "Penanda", "Referensi SATUSEHAT", "Status TTE"};
    private static final int QR_PX = 240;
    private static final int TINGGI_LOG_PX = 150;   // tinggi panel Log TTE, diukur dari bawah dialog
    private static final Color HIJAU = new Color(0, 140, 70);
    private static final Color ORANYE = new Color(180, 120, 20);
    private static final Color MERAH = new Color(190, 50, 45);

    private final Connection koneksi = koneksiDB.condb();
    private final SatuSehatProvenance provSender = new SatuSehatProvenance();
    private final SatuSehatTask taskSender = new SatuSehatTask();
    private final SatuSehatBundleProvenance bundleSender = new SatuSehatBundleProvenance();
    /** Jalur multi-penanda (model paralel & serial); lihat SatuSehatSignatureFlow. */
    private final SatuSehatSignatureFlow flowSender = new SatuSehatSignatureFlow();
    private final SatuSehatProvenanceStore store = new SatuSehatProvenanceStore();
    private final SatuSehatSignatureAssembler perakit = new SatuSehatSignatureAssembler();
    private final SatuSehatSignatureVerifier verifier = new SatuSehatSignatureVerifier();
    private final QrRenderer qr = QrRendererFactory.buat();

    /**
     * Hak akses user yang login atas fitur TTE (kolom {@code user.satu_sehat_tanda_tangan_elektronik},
     * hak yang sama yang menampilkan tombol menu TTE di frmUtama).
     *
     * Sengaja dibaca sejak konstruktor — bukan hanya di {@link #isCek()} — karena tombol Tandatangani
     * dan Update Status dihidupkan/dimatikan ulang di banyak tempat mengikuti pilihan pasien; kalau
     * hak akses hanya diterapkan sekali lewat isCek(), pemilihan pasien berikutnya akan menyalakan
     * kembali tombol untuk user yang tidak berhak.
     */
    private boolean aksesTte = akses.getsatu_sehat_tanda_tangan_elektronik();

    // Komponen visual dideklarasikan GUI Builder di blok                                   
    // (widget.TextBox extends JTextField, widget.Label extends JLabel, widget.Button extends
    // JButton, widget.Table extends JTable), jadi seluruh kode logika di bawah tetap memakai API
    // Swing biasa. Yang tersisa di sini hanya field NON-visual.
    private DefaultTableModel modelPasien;
    private final List<Object[]> semuaBaris = new ArrayList<>(); // cache hasil query utk filter dokter

    private boolean filterBerkasDiisi = false;                    // cegah reentrancy saat isi combo berkas
    private DefaultTableModel modelDok;
    private javax.swing.table.TableRowSorter<DefaultTableModel> sorterDok; // filter view berkas (model tetap 1:1 curDokumen)

    // Konteks pasien terpilih.
    private String curNoRawat = "";
    private String curNamaPasien = "";
    private String curIdPractitioner = "";
    private String curNamaDpjp = "";
    private String curIdEncounter = "";
    private List<DokumenTte> curDokumen = new ArrayList<>();

    // Popup QR + timer.
    private JDialog popupQr;
    private JLabel popupGambar;
    private JLabel popupInfo;     // hitung mundur "Kode diperbarui dalam N detik"
    private JLabel popupTunggu;   // baris status kecil di bawah hitung mundur
    private Timer timerQr;
    private Timer timerPoll;
    // Pemroses sinyal webhook: jedanya jauh lebih rapat karena hanya query DB lokal. Inilah yang
    // membuat webhook terasa "seketika" dibanding polling latar.
    private Timer timerSinyal;
    private volatile boolean sinyalBerjalan = false;
    private static final int JEDA_SINYAL_MS = 15_000;   // 15 detik
    private static final int MAKS_SINYAL_PER_PUTARAN = 30;
    private String taskAktif = "";
    private long qrIssuedSec = 0L;
    // Basis deep-link applink SSM ("https://applink[-stg...]/ssm/tte"). Di-resolve sekali tiap
    // popup dibuka — bukan tiap detik — karena koneksiDB membaca ulang setting/database.xml
    // pada setiap pemanggilan.
    private String basisApplink = "";

    public SatuSehatBridgingTTE(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();   // tata letak dari GUI Builder (SatuSehatBridgingTTE.form)
        bangunUi();         // sisanya: model tabel, renderer, sorter, ukuran & divider
        muatPasien();
        // Sinyal webhook diproses lebih cepat: query lokal, murah, dan inilah nilai lebih webhook.
        timerSinyal = new Timer(JEDA_SINYAL_MS, e -> prosesSinyalWebhook());
        timerSinyal.setInitialDelay(JEDA_SINYAL_MS);
        timerSinyal.start();
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Pola form Khanza: pemanggil menjalankan isCek() sesudah dialog dibuat supaya tombol aksi
     * mengikuti hak akses user yang login — di sini hak {@code satu_sehat_tanda_tangan_elektronik}.
     * Tanpa hak itu dialog tetap boleh dibuka untuk MELIHAT status & memverifikasi berkas, tetapi
     * tidak boleh menerbitkan Provenance/Task ke SATUSEHAT.
     */
    public void isCek() {
        aksesTte = akses.getsatu_sehat_tanda_tangan_elektronik();
        aktifkanTandatangani(!curDokumen.isEmpty());
        aktifkanUpdate(!curDokumen.isEmpty());
    }

    /** Hidupkan tombol Tandatangani hanya bila datanya siap DAN user berhak. */
    private void aktifkanTandatangani(boolean siap) {
        tombolTandatangani.setEnabled(siap && aksesTte);
    }

    /** Hidupkan tombol Update Status hanya bila datanya siap DAN user berhak. */
    private void aktifkanUpdate(boolean siap) {
        tombolUpdate.setEnabled(siap && aksesTte);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppPilihSemua = new javax.swing.JMenuItem();
        ppHapusSemua = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        splitUtama = new javax.swing.JSplitPane();
        splitTabel = new javax.swing.JSplitPane();
        spKiri = new widget.ScrollPane();
        tabelPasien = new widget.Table();
        panelKanan = new javax.swing.JPanel();
        panelKananAtas = new javax.swing.JPanel();
        labelDok = new widget.Label();
        panelFilterBerkas = new javax.swing.JPanel();
        jLabel1 = new widget.Label();
        cbBerkas = new widget.ComboBox();
        jLabel2 = new widget.Label();
        cbDokterSign = new widget.ComboBox();
        jLabel3 = new widget.Label();
        cbStatus = new widget.ComboBox();
        spKanan = new widget.ScrollPane();
        tabelDok = new widget.Table();
        spLog = new widget.ScrollPane();
        taLog = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        panelFilter = new widget.panelisi();
        jLabel4 = new widget.Label();
        tglDari = new widget.Tanggal();
        jLabel5 = new widget.Label();
        tglSampai = new widget.Tanggal();
        jLabel6 = new widget.Label();
        txtCari = new widget.TextBox();
        tombolCari = new widget.Button();
        jLabel7 = new widget.Label();
        txtFilterDokter = new widget.TextBox();
        tombolCariDokter = new widget.Button();
        tombolSemuaDokter = new widget.Button();
        panelTombol = new widget.panelisi();
        labelJmlPasien = new widget.Label();
        tombolTandatangani = new widget.Button();
        tombolUpdate = new widget.Button();
        tombolSalinLog = new widget.Button();
        tombolTutup = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppPilihSemua.setText("Pilih Semua");
        ppPilihSemua.setName("ppPilihSemua"); // NOI18N
        ppPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPilihSemua);

        ppHapusSemua.setText("Hapus Semua");
        ppHapusSemua.setName("ppHapusSemua"); // NOI18N
        ppHapusSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusSemuaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusSemua);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tanda Tangan Elektronik (TTE) - SATUSEHAT");
        setUndecorated(true);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tanda Tangan Elektronik (TTE) SATUSEHAT ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        splitUtama.setDividerSize(6);
        splitUtama.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitUtama.setResizeWeight(1.0);
        splitUtama.setName("splitUtama"); // NOI18N

        splitTabel.setDividerSize(6);
        splitTabel.setResizeWeight(0.55);
        splitTabel.setMinimumSize(new java.awt.Dimension(100, 200));
        splitTabel.setName("splitTabel"); // NOI18N

        spKiri.setName("spKiri"); // NOI18N

        tabelPasien.setName("tabelPasien"); // NOI18N
        tabelPasien.setRowHeight(24);
        spKiri.setViewportView(tabelPasien);

        splitTabel.setLeftComponent(spKiri);

        panelKanan.setName("panelKanan"); // NOI18N
        panelKanan.setLayout(new java.awt.BorderLayout(4, 4));

        panelKananAtas.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8));
        panelKananAtas.setName("panelKananAtas"); // NOI18N
        panelKananAtas.setLayout(new java.awt.BorderLayout());

        labelDok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelDok.setText("Pilih pasien di kiri untuk melihat berkas.");
        labelDok.setName("labelDok"); // NOI18N
        panelKananAtas.add(labelDok, java.awt.BorderLayout.NORTH);

        panelFilterBerkas.setOpaque(false);
        panelFilterBerkas.setName("panelFilterBerkas"); // NOI18N
        panelFilterBerkas.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Berkas");
        jLabel1.setName("jLabel1"); // NOI18N
        panelFilterBerkas.add(jLabel1);

        cbBerkas.setName("cbBerkas"); // NOI18N
        cbBerkas.setPreferredSize(new java.awt.Dimension(175, 23));
        cbBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterActionPerformed(evt);
            }
        });
        panelFilterBerkas.add(cbBerkas);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Dokter");
        jLabel2.setName("jLabel2"); // NOI18N
        panelFilterBerkas.add(jLabel2);

        cbDokterSign.setName("cbDokterSign"); // NOI18N
        cbDokterSign.setPreferredSize(new java.awt.Dimension(185, 23));
        cbDokterSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterActionPerformed(evt);
            }
        });
        panelFilterBerkas.add(cbDokterSign);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Status");
        jLabel3.setName("jLabel3"); // NOI18N
        panelFilterBerkas.add(jLabel3);

        cbStatus.setName("cbStatus"); // NOI18N
        cbStatus.setPreferredSize(new java.awt.Dimension(140, 23));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFilterActionPerformed(evt);
            }
        });
        panelFilterBerkas.add(cbStatus);

        panelKananAtas.add(panelFilterBerkas, java.awt.BorderLayout.SOUTH);

        panelKanan.add(panelKananAtas, java.awt.BorderLayout.NORTH);

        spKanan.setComponentPopupMenu(jPopupMenu1);
        spKanan.setName("spKanan"); // NOI18N

        tabelDok.setComponentPopupMenu(jPopupMenu1);
        tabelDok.setName("tabelDok"); // NOI18N
        tabelDok.setRowHeight(26);
        tabelDok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDokMouseClicked(evt);
            }
        });
        spKanan.setViewportView(tabelDok);

        panelKanan.add(spKanan, java.awt.BorderLayout.CENTER);

        splitTabel.setRightComponent(panelKanan);

        splitUtama.setTopComponent(splitTabel);

        spLog.setBorder(javax.swing.BorderFactory.createTitledBorder("Log TTE"));
        spLog.setMinimumSize(new java.awt.Dimension(100, 120));
        spLog.setName("spLog"); // NOI18N
        spLog.setPreferredSize(new java.awt.Dimension(100, 130));

        taLog.setColumns(20);
        taLog.setEditable(false);
        taLog.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        taLog.setRows(5);
        taLog.setName("taLog"); // NOI18N
        spLog.setViewportView(taLog);

        splitUtama.setBottomComponent(spLog);

        internalFrame1.add(splitUtama, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 88));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelFilter.setName("panelFilter"); // NOI18N
        panelFilter.setPreferredSize(new java.awt.Dimension(44, 44));
        panelFilter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Tanggal");
        jLabel4.setName("jLabel4"); // NOI18N
        panelFilter.add(jLabel4);

        tglDari.setDisplayFormat("dd-MM-yyyy");
        tglDari.setName("tglDari"); // NOI18N
        tglDari.setPreferredSize(new java.awt.Dimension(95, 23));
        panelFilter.add(tglDari);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("s/d");
        jLabel5.setName("jLabel5"); // NOI18N
        panelFilter.add(jLabel5);

        tglSampai.setDisplayFormat("dd-MM-yyyy");
        tglSampai.setName("tglSampai"); // NOI18N
        tglSampai.setPreferredSize(new java.awt.Dimension(95, 23));
        panelFilter.add(tglSampai);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Kata Kunci");
        jLabel6.setName("jLabel6"); // NOI18N
        panelFilter.add(jLabel6);

        txtCari.setName("txtCari"); // NOI18N
        txtCari.setPreferredSize(new java.awt.Dimension(140, 23));
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        panelFilter.add(txtCari);

        tombolCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        tombolCari.setMnemonic('C');
        tombolCari.setText("Cari");
        tombolCari.setToolTipText("Alt+C");
        tombolCari.setName("tombolCari"); // NOI18N
        tombolCari.setPreferredSize(new java.awt.Dimension(85, 30));
        tombolCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolCariActionPerformed(evt);
            }
        });
        panelFilter.add(tombolCari);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Dokter");
        jLabel7.setName("jLabel7"); // NOI18N
        panelFilter.add(jLabel7);

        txtFilterDokter.setEditable(false);
        txtFilterDokter.setName("txtFilterDokter"); // NOI18N
        txtFilterDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        panelFilter.add(txtFilterDokter);

        tombolCariDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        tombolCariDokter.setMnemonic('D');
        tombolCariDokter.setText("Dokter");
        tombolCariDokter.setToolTipText("Alt+D");
        tombolCariDokter.setName("tombolCariDokter"); // NOI18N
        tombolCariDokter.setPreferredSize(new java.awt.Dimension(95, 30));
        tombolCariDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolCariDokterActionPerformed(evt);
            }
        });
        panelFilter.add(tombolCariDokter);

        tombolSemuaDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        tombolSemuaDokter.setMnemonic('S');
        tombolSemuaDokter.setText("Semua");
        tombolSemuaDokter.setToolTipText("Alt+S");
        tombolSemuaDokter.setName("tombolSemuaDokter"); // NOI18N
        tombolSemuaDokter.setPreferredSize(new java.awt.Dimension(95, 30));
        tombolSemuaDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolSemuaDokterActionPerformed(evt);
            }
        });
        panelFilter.add(tombolSemuaDokter);

        jPanel3.add(panelFilter, java.awt.BorderLayout.PAGE_START);

        panelTombol.setName("panelTombol"); // NOI18N
        panelTombol.setPreferredSize(new java.awt.Dimension(44, 44));
        panelTombol.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        labelJmlPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelJmlPasien.setName("labelJmlPasien"); // NOI18N
        labelJmlPasien.setPreferredSize(new java.awt.Dimension(110, 23));
        panelTombol.add(labelJmlPasien);

        tombolTandatangani.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        tombolTandatangani.setMnemonic('T');
        tombolTandatangani.setText("Tandatangani");
        tombolTandatangani.setToolTipText("Alt+T");
        tombolTandatangani.setEnabled(false);
        tombolTandatangani.setName("tombolTandatangani"); // NOI18N
        tombolTandatangani.setPreferredSize(new java.awt.Dimension(135, 30));
        tombolTandatangani.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolTandatanganiActionPerformed(evt);
            }
        });
        panelTombol.add(tombolTandatangani);

        tombolUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        tombolUpdate.setMnemonic('U');
        tombolUpdate.setText("Update Status");
        tombolUpdate.setToolTipText("Alt+U");
        tombolUpdate.setEnabled(false);
        tombolUpdate.setName("tombolUpdate"); // NOI18N
        tombolUpdate.setPreferredSize(new java.awt.Dimension(135, 30));
        tombolUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolUpdateActionPerformed(evt);
            }
        });
        panelTombol.add(tombolUpdate);

        tombolSalinLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        tombolSalinLog.setMnemonic('L');
        tombolSalinLog.setText("Salin Log");
        tombolSalinLog.setToolTipText("Alt+L");
        tombolSalinLog.setName("tombolSalinLog"); // NOI18N
        tombolSalinLog.setPreferredSize(new java.awt.Dimension(110, 30));
        tombolSalinLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolSalinLogActionPerformed(evt);
            }
        });
        panelTombol.add(tombolSalinLog);

        tombolTutup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        tombolTutup.setMnemonic('K');
        tombolTutup.setText("Keluar");
        tombolTutup.setToolTipText("Alt+K");
        tombolTutup.setName("tombolTutup"); // NOI18N
        tombolTutup.setPreferredSize(new java.awt.Dimension(100, 30));
        tombolTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolTutupActionPerformed(evt);
            }
        });
        panelTombol.add(tombolTutup);

        jPanel3.add(panelTombol, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // Enter di kolom pencarian = klik Cari (kebiasaan operator di form Khanza lain).
        muatPasien();
    }//GEN-LAST:event_txtCariActionPerformed

    private void tombolCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolCariActionPerformed
        muatPasien();
    }//GEN-LAST:event_tombolCariActionPerformed

    private void tombolCariDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolCariDokterActionPerformed
        pilihFilterDokter();
    }//GEN-LAST:event_tombolCariDokterActionPerformed

    private void tombolSemuaDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolSemuaDokterActionPerformed
        txtFilterDokter.setText("");
        terapkanFilterDokter();
    }//GEN-LAST:event_tombolSemuaDokterActionPerformed

    /** Dipakai bersama oleh ketiga combo filter (Berkas, Dokter, Status) — filternya digabung DAN. */
    private void cbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFilterActionPerformed
        terapkanFilterBerkas();
    }//GEN-LAST:event_cbFilterActionPerformed

    private void tombolTandatanganiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolTandatanganiActionPerformed
        aksiTandatangani();
    }//GEN-LAST:event_tombolTandatanganiActionPerformed

    private void tombolUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolUpdateActionPerformed
        aksiUpdateStatus();
    }//GEN-LAST:event_tombolUpdateActionPerformed

    private void tombolSalinLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolSalinLogActionPerformed
        salinLog();
    }//GEN-LAST:event_tombolSalinLogActionPerformed

    private void tombolTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolTutupActionPerformed
        dispose();
    }//GEN-LAST:event_tombolTutupActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        setSemuaCentang(true);
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    private void ppHapusSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusSemuaActionPerformed
        setSemuaCentang(false);
    }//GEN-LAST:event_ppHapusSemuaActionPerformed

    /** Flow 5 (Verifikasi): klik dua kali baris yang "Sudah TTE" -> panel verifikasi tanda tangan. */
    private void tabelDokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDokMouseClicked
        if (evt.getClickCount() != 2) {
            return;
        }
        int viewRow = tabelDok.rowAtPoint(evt.getPoint());
        if (viewRow < 0) {
            return;
        }
        int r = tabelDok.convertRowIndexToModel(viewRow); // view -> model (sorter/filter aktif)
        if (r < 0 || r >= curDokumen.size()) {
            return;
        }
        DokumenTte d = curDokumen.get(r);
        if (d.status == Status.SUDAH) {
            verifikasiDokumen(d);
        } else {
            JOptionPane.showMessageDialog(SatuSehatBridgingTTE.this,
                    "Berkas ini belum ditandatangani.\n"
                    + "Verifikasi hanya untuk berkas berstatus \"Sudah TTE\".");
        }
    }//GEN-LAST:event_tabelDokMouseClicked

    /**
     * frmUtama memanggil setSize() SETELAH konstruktor selesai, jadi menghitung divider sekali di
     * konstruktor SELALU memakai ukuran lama (1240x640) dan panel log jadi kelewat tinggi. Karena
     * itu divider dipasang ulang setiap dialog berubah ukuran.
     */
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        aturDivider(splitUtama, splitTabel);
    }//GEN-LAST:event_formComponentResized

    /**
     * Sisa pembangunan UI yang memang tak bisa digambar GUI Builder: model tabel beserta aturan
     * sel-nya, renderer warna status, sorter untuk RowFilter, lebar kolom, dan tanggal awal filter.
     */
    private void bangunUi() {
        // Font Tahoma 11 plain: seragam dengan seluruh form Khanza (SatuSehatBundle dkk).
        Font plain = new Font("Tahoma", Font.PLAIN, 11);

        LocalDate now = LocalDate.now();
        // Rentang tanggal awal (30 hari terakhir) dihitung saat dialog dibuka, jadi tak bisa
        // ditaruh sebagai properti di .form.
        tglDari.setDate(java.sql.Date.valueOf(now.minusDays(30)));
        tglSampai.setDate(java.sql.Date.valueOf(now));

        // ---- KIRI: daftar pasien ----
        modelPasien = new DefaultTableModel(KOL_PASIEN, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabelPasien.setModel(modelPasien);
        tabelPasien.getTableHeader().setFont(plain);
        tabelPasien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelPasien.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onPilihPasien();
            }
        });

        // ---- KANAN: dokumen berkas ----
        modelDok = new DefaultTableModel(KOL_DOK, 0) {
            @Override public Class<?> getColumnClass(int c) {
                return c == 0 ? Boolean.class : String.class;
            }
            @Override public boolean isCellEditable(int r, int c) {
                // Baris yang menunggu giliran (model serial) tidak bisa dicentang: Provenance
                // berikutnya baru sah dibuat setelah pendahulunya ditandatangani.
                return c == 0 && r < curDokumen.size() && !curDokumen.get(r).terkunci;
            }
        };
        tabelDok.setModel(modelDok);
        tabelDok.getTableHeader().setFont(plain);
        // Sorter dipakai HANYA untuk RowFilter (Filter Berkas); sort dimatikan agar model tetap 1:1 curDokumen.
        sorterDok = new javax.swing.table.TableRowSorter<>(modelDok);
        for (int i = 0; i < KOL_DOK.length; i++) {
            sorterDok.setSortable(i, false);
        }
        tabelDok.setRowSorter(sorterDok);
        tabelDok.getColumnModel().getColumn(0).setMaxWidth(30);
        tabelDok.getColumnModel().getColumn(1).setPreferredWidth(155); // Jenis Berkas
        tabelDok.getColumnModel().getColumn(2).setPreferredWidth(95);  // Peran (mis. "1 · Author")
        tabelDok.getColumnModel().getColumn(3).setPreferredWidth(150); // Penanda
        tabelDok.getColumnModel().getColumn(4).setPreferredWidth(200); // Referensi SATUSEHAT
        tabelDok.getColumnModel().getColumn(5).setPreferredWidth(150); // Status TTE
        tabelDok.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                    boolean foc, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                String s = String.valueOf(v);
                if (!sel) {
                    if (s.startsWith("Sudah")) {
                        comp.setForeground(HIJAU);
                    } else if (s.startsWith("Ditolak")) {
                        comp.setForeground(MERAH);
                    } else if (s.startsWith("Menunggu")) {
                        // Baris yang belum boleh dikerjakan: abu-abu, supaya jelas ia bukan
                        // pekerjaan yang tertunda karena kelalaian operator.
                        comp.setForeground(new Color(120, 120, 120));
                    } else {
                        comp.setForeground(ORANYE);
                    }
                }
                return comp;
            }
        });

        // Isi ketiga combo filter PALING AKHIR: listener-nya sudah terpasang sejak initComponents(),
        // jadi setiap addItem() memanggil terapkanFilterBerkas() yang butuh sorterDok sudah ada.
        // Filter Berkas by jenis (mis. hanya "Expertise Laboratorium") -> RowFilter view, model tetap.
        cbBerkas.addItem(SEMUA_BERKAS);
        // Filter Dokter: DIISI DARI KOLOM "Dokter Sign" milik daftar dokumen pasien ini —
        // bukan dari master dokter, dan bukan filter dokter di bar bawah (yang menyaring PASIEN).
        cbDokterSign.addItem(SEMUA_DOKTER);
        // Filter Status: daftar tetap (Sudah/Buat/Belum/Sedang Diproses/Ditolak) mengikuti
        // SatuSehatSignatureState.label() supaya teksnya sama persis dengan yang tampil di kolom.
        cbStatus.addItem(SEMUA_STATUS);
        for (String s : STATUS_PILIHAN) {
            cbStatus.addItem(s);
        }

        // pack() dari GUI Builder memakai preferred size; ukuran kerja dialog dipatok di sini.
        // frmUtama tetap boleh menimpanya lewat setSize() setelah konstruktor selesai.
        setSize(1240, 640);
        setLocationRelativeTo(getParent());
        javax.swing.SwingUtilities.invokeLater(() -> aturDivider(splitUtama, splitTabel));
    }

    /** Salin seluruh isi panel log ke clipboard - memudahkan lapor masalah ke IT/Kemenkes. */
    private void salinLog() {
        String isi = (taLog == null) ? "" : taLog.getText();
        if (isi.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Log masih kosong.");
            return;
        }
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new java.awt.datatransfer.StringSelection(isi), null);
        logInfo("Log disalin ke clipboard (" + isi.split("\n").length + " baris).");
    }

    // ====================== LOG TTE ======================
    // Format konsisten dgn SatuSehatBundle: "yyyy-MM-dd HH:mm:ss  SATUSEHAT-TTE  LEVEL  pesan"
    // supaya mudah dibaca, di-grep, dan bisa disalin utuh saat melapor.
    private static final java.time.format.DateTimeFormatter LOG_TS =
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private void log(String level, String pesan) {
        String baris = java.time.LocalDateTime.now().format(LOG_TS)
                + "  SATUSEHAT-TTE  " + String.format("%-5s", level) + "  " + pesan;
        System.out.println(baris);
        if (taLog != null) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                taLog.append(baris + "\n");
                taLog.setCaretPosition(taLog.getDocument().getLength());
            });
        }
    }
    private void logInfo(String pesan)   { log("INFO", pesan); }
    private void logSukses(String pesan) { log("OK", pesan); }
    private void logWarn(String pesan)   { log("WARN", pesan); }
    private void logError(String pesan)  { log("ERROR", pesan); }

    private JLabel label(String teks, Font f) {
        JLabel l = new JLabel(teks);
        l.setFont(f);
        return l;
    }

    /**
     * Baca widget.Tanggal sebagai "yyyy-MM-dd" untuk parameter query. widget.Tanggal turunan
     * JComboBox yang menyimpan java.util.Date, sedangkan query TTE memakai format ISO — konversi
     * dipusatkan di sini agar tidak tercecer. Bila kosong/gagal, jatuh ke tanggal hari ini supaya
     * query tetap sah (bukan string kosong yang bikin SQL error).
     */
    private String tglIso(widget.Tanggal t) {
        try {
            java.util.Date d = (t == null) ? null : t.getDate();
            if (d != null) {
                return new java.text.SimpleDateFormat("yyyy-MM-dd").format(d);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi DlgTTE tglIso : " + e);
        }
        return java.time.LocalDate.now().toString();
    }

    /** Muat daftar pasien yang Encounter-nya sudah terkirim ke SATUSEHAT (id_encounter terisi). */
    private void muatPasien() {
        final String dari = tglIso(tglDari);
        final String sampai = tglIso(tglSampai);
        final String cari = txtCari.getText().trim();
        labelJmlPasien.setText("  memuat...");
        new SwingWorker<Integer, Void>() {
            private final List<Object[]> baris = new ArrayList<>();
            @Override protected Integer doInBackground() {
                try {
                    jalankanQueryPasien(baris, dari, sampai, cari, true);
                } catch (Exception e) {
                    // dpjp_ranap bisa tak ada di skema tertentu -> ulangi tanpa subquery DPJP.
                    System.out.println("Notifikasi DlgTTE muatPasien (retry tanpa DPJP) : " + e);
                    baris.clear();
                    try {
                        jalankanQueryPasien(baris, dari, sampai, cari, false);
                    } catch (Exception e2) {
                        System.out.println("Notifikasi DlgTTE muatPasien : " + e2);
                    }
                }
                return baris.size();
            }
            @Override protected void done() {
                semuaBaris.clear();
                semuaBaris.addAll(baris);
                terapkanFilterDokter();
                modelDok.setRowCount(0);
                curDokumen = new ArrayList<>();
                isiComboBerkas(); // reset Filter Berkas ke "Semua" (curDokumen kosong)
                tombolTandatangani.setEnabled(false);
                tombolUpdate.setEnabled(false);
                labelDok.setText("Pilih pasien di kiri untuk melihat berkas.");
            }
        }.execute();
    }

    /**
     * Pra-isi pencarian dengan no_rawat lalu muat daftar. Dipanggil dari panel Preview Klaim
     * (SatuSehatBundle) agar operator langsung diarahkan ke pasien yang sedang ditinjau untuk TTE.
     */
    public void praFilter(String noRawat) {
        if (noRawat == null || noRawat.trim().equals("")) return;
        try {
            tglDari.setDate(java.sql.Date.valueOf(java.time.LocalDate.now().minusYears(2)));
        } catch (Exception e) {
            System.out.println("Notifikasi DlgTTE praFilter tanggal : " + e);
        }
        txtCari.setText(noRawat.trim());
        muatPasien();
    }

    /** Jalankan query pasien; pakaiDpjp=true menyertakan subquery dpjp_ranap (DPJP asli). */
    private void jalankanQueryPasien(List<Object[]> out, String dari, String sampai, String cari,
                                     boolean pakaiDpjp) throws Exception {
        boolean pakaiCari = !cari.equals("");
        String kolDpjp = pakaiDpjp
                ? "ifnull((select group_concat(distinct d2.nm_dokter separator ', ') "
                  + "from dpjp_ranap dj join dokter d2 on d2.kd_dokter=dj.kd_dokter "
                  + "where dj.no_rawat=rp.no_rawat), '') as dpjp, "
                : "'' as dpjp, ";
        String sql = "select rp.tgl_registrasi, rp.jam_reg, rp.no_rawat, rp.no_rkm_medis, p.nm_pasien, "
                + "ifnull(dr.nm_dokter, ifnull(pg.nama,'')) as dokter_igd, "
                + kolDpjp
                + "ifnull(se.id_encounter,'') as id_encounter, rp.status_lanjut "
                + "from reg_periksa rp "
                + "inner join pasien p on p.no_rkm_medis=rp.no_rkm_medis "
                + "inner join satu_sehat_encounter se on se.no_rawat=rp.no_rawat "
                + "left join dokter dr on dr.kd_dokter=rp.kd_dokter "
                + "left join pegawai pg on pg.nik=rp.kd_dokter "
                + "where ifnull(se.id_encounter,'')<>'' and rp.tgl_registrasi between ? and ? "
                + (pakaiCari ? "and (rp.no_rawat like ? or rp.no_rkm_medis like ? or p.nm_pasien like ?) " : "")
                + "order by rp.tgl_registrasi desc, rp.jam_reg desc limit 500";
        PreparedStatement p = koneksi.prepareStatement(sql);
        p.setString(1, dari);
        p.setString(2, sampai);
        if (pakaiCari) {
            p.setString(3, "%" + cari + "%");
            p.setString(4, "%" + cari + "%");
            p.setString(5, "%" + cari + "%");
        }
        ResultSet r = p.executeQuery();
        while (r.next()) {
            String dokterIgd = nz(r.getString("dokter_igd"));
            String dpjp = nz(r.getString("dpjp"));
            if (dpjp.equals("")) {
                dpjp = dokterIgd; // fallback: bila DPJP tak tercatat, tampilkan dokter registrasi.
            }
            out.add(new Object[]{
                nz(r.getString("tgl_registrasi")) + " " + nz(r.getString("jam_reg")),
                nz(r.getString("no_rawat")), nz(r.getString("no_rkm_medis")),
                nz(r.getString("nm_pasien")), dokterIgd, dpjp,
                nz(r.getString("id_encounter")), nz(r.getString("status_lanjut"))
            });
        }
        r.close();
        p.close();
    }

    /** Buka DlgCariDokter (standar Khanza) -> set nama dokter sebagai filter pasien. */
    private void pilihFilterDokter() {
        try {
            kepegawaian.DlgCariDokter dlg = new kepegawaian.DlgCariDokter(null, true);
            dlg.setLocationRelativeTo(this);
            dlg.setVisible(true); // modal: blok sampai dipilih (klik dua kali) / ditutup
            javax.swing.JTable t = dlg.getTable();
            if (t != null && t.getSelectedRow() != -1) {
                Object nm = t.getValueAt(t.getSelectedRow(), 1); // kolom 1 = nm_dokter
                txtFilterDokter.setText(nm == null ? "" : nm.toString().trim());
                terapkanFilterDokter();
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi DlgTTE pilihFilterDokter : " + ex);
        }
    }

    /** Terapkan Filter Dokter ke tabel pasien (client-side dari cache semuaBaris; kosong = semua). */
    private void terapkanFilterDokter() {
        String pilih = (txtFilterDokter == null) ? "" : txtFilterDokter.getText().trim();
        modelPasien.setRowCount(0);
        for (Object[] b : semuaBaris) {
            boolean cocok = pilih.equals("")
                    || String.valueOf(b[4]).equals(pilih)
                    || String.valueOf(b[5]).contains(pilih);
            if (cocok) {
                modelPasien.addRow(b);
            }
        }
        labelJmlPasien.setText("  " + modelPasien.getRowCount() + " pasien");
    }

    /** Isi combo Filter Berkas dari jenis berkas pasien terpilih + reset ke "Semua". */
    private void isiComboBerkas() {
        if (cbBerkas == null) {
            return;
        }
        filterBerkasDiisi = true;
        java.util.LinkedHashSet<String> jenis = new java.util.LinkedHashSet<>();
        for (DokumenTte d : curDokumen) {
            jenis.add(nz(d.jenis));
        }
        cbBerkas.removeAllItems();
        cbBerkas.addItem(SEMUA_BERKAS);
        for (String j : jenis) {
            cbBerkas.addItem(j);
        }
        cbBerkas.setSelectedItem(SEMUA_BERKAS);

        // Filter Dokter diisi dari kolom "Dokter Sign" berkas pasien INI saja — sesuai permintaan
        // operator: yang dicari adalah dokter yang benar-benar muncul di daftar dokumen, bukan
        // seluruh master dokter. Nama kosong dilewati agar tak ada entri hampa di combo.
        if (cbDokterSign != null) {
            java.util.LinkedHashSet<String> dokter = new java.util.LinkedHashSet<>();
            for (DokumenTte d : curDokumen) {
                String nm = nz(d.signer).trim();
                if (!nm.equals("")) {
                    dokter.add(nm);
                }
            }
            cbDokterSign.removeAllItems();
            cbDokterSign.addItem(SEMUA_DOKTER);
            for (String nm : dokter) {
                cbDokterSign.addItem(nm);
            }
            cbDokterSign.setSelectedItem(SEMUA_DOKTER);
        }
        if (cbStatus != null) {
            cbStatus.setSelectedItem(SEMUA_STATUS);
        }
        filterBerkasDiisi = false;
        terapkanFilterBerkas();
    }

    /** Terapkan Filter Berkas ke VIEW tabel berkas (RowFilter; model tetap 1:1 curDokumen). */
    private void terapkanFilterBerkas() {
        if (filterBerkasDiisi || sorterDok == null) {
            return;
        }
        final String pilihBerkas = terpilih(cbBerkas, SEMUA_BERKAS);
        final String pilihDokter = terpilih(cbDokterSign, SEMUA_DOKTER);
        final String pilihStatus = terpilih(cbStatus, SEMUA_STATUS);

        boolean semua = pilihBerkas.equals(SEMUA_BERKAS)
                && pilihDokter.equals(SEMUA_DOKTER)
                && pilihStatus.equals(SEMUA_STATUS);
        if (semua) {
            sorterDok.setRowFilter(null);
            perbaruiRingkasanFilter();
            return;
        }
        // Ketiga filter digabung dengan DAN: baris tampil hanya bila lolos semuanya.
        sorterDok.setRowFilter(new javax.swing.RowFilter<DefaultTableModel, Integer>() {
            @Override public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                if (!pilihBerkas.equals(SEMUA_BERKAS)
                        && !pilihBerkas.equals(String.valueOf(entry.getValue(1)))) {   // 1 = Jenis Berkas
                    return false;
                }
                if (!pilihDokter.equals(SEMUA_DOKTER)
                        && !pilihDokter.equals(String.valueOf(entry.getValue(3)))) {   // 3 = Penanda
                    return false;
                }
                if (!pilihStatus.equals(SEMUA_STATUS)
                        && !pilihStatus.equals(String.valueOf(entry.getValue(5)))) {   // 5 = Status TTE
                    return false;
                }
                return true;
            }
        });
        perbaruiRingkasanFilter();
    }

    /** Nilai combo terpilih; {@code bawaan} bila combo belum dibangun/kosong. */
    private String terpilih(javax.swing.JComboBox<?> cb, String bawaan) {
        Object o = (cb == null) ? null : cb.getSelectedItem();
        return (o == null) ? bawaan : String.valueOf(o);
    }

    /**
     * Tampilkan berapa berkas yang sedang terlihat setelah difilter, supaya operator tak bingung
     * saat tabel tiba-tiba kosong padahal pasiennya memang punya berkas.
     */
    private void perbaruiRingkasanFilter() {
        if (labelDok == null || tabelDok == null || curNamaPasien.equals("")) {
            return;
        }
        int terlihat = tabelDok.getRowCount();
        int total = curDokumen.size();
        int sudah = 0;
        for (DokumenTte d : curDokumen) {
            if (d.status == Status.SUDAH) {
                sudah++;
            }
        }
        String dasar = "Berkas " + curNamaPasien + ": " + sudah + " sudah / " + (total - sudah)
                + " belum TTE (" + total + " tanda tangan, " + jumlahBerkas() + " berkas)";
        labelDok.setText(terlihat == total
                ? dasar
                : dasar + "   (tampil " + terlihat + " dari " + total + " - filter aktif)");
    }

    /** Pilih/Hapus semua centang untuk baris berkas yang SEDANG TERLIHAT (mengikuti Filter Berkas). */
    private void setSemuaCentang(boolean nilai) {
        int rows = tabelDok.getRowCount(); // view rows (terfilter)
        for (int viewRow = 0; viewRow < rows; viewRow++) {
            int modelRow = tabelDok.convertRowIndexToModel(viewRow);
            if (modelRow < 0 || modelRow >= curDokumen.size()) {
                continue;
            }
            // Jangan centang baris yang sudah TTE maupun yang masih menunggu giliran penanda
            // sebelumnya (model serial); menghapus centang tetap boleh untuk semua baris.
            DokumenTte dd = curDokumen.get(modelRow);
            if (nilai && (dd.status == Status.SUDAH || dd.terkunci)) {
                continue;
            }
            modelDok.setValueAt(nilai, modelRow, 0);
        }
    }

    /** Pasien dipilih -> rakit & tampilkan berkas RME-nya di kanan. */
    private void onPilihPasien() {
        int row = tabelPasien.getSelectedRow();
        if (row < 0) {
            return;
        }
        final String noRawat = String.valueOf(modelPasien.getValueAt(row, 1));
        final String namaPasien = String.valueOf(modelPasien.getValueAt(row, 3));
        labelDok.setText("Memuat berkas " + namaPasien + "...");
        tombolTandatangani.setEnabled(false);
        tombolUpdate.setEnabled(false);
        new SwingWorker<SatuSehatSignatureAssembler.Hasil, Void>() {
            @Override protected SatuSehatSignatureAssembler.Hasil doInBackground() {
                SatuSehatSignatureAssembler.Hasil h = perakit.rakit(noRawat);
                for (DokumenTte d : h.dokumen) {
                    try {
                        // Kunci status = (dokumen, PENANDA). Memakai store.ambil(noRawat,target)
                        // saja akan menyalin status penanda lain ke baris ini.
                        SatuSehatProvenanceStore.Baris b = store.ambil(noRawat, d.targetRef, d.signerIhs);
                        if (b != null) {
                            d.idProvenance = b.idProvenance;
                            d.taskUuid = b.taskUuid;
                            d.idProvenanceSebelumnya = b.idProvenanceSebelumnya;
                            d.status = SatuSehatSignatureState.uiState(b.taskUuid, mapEnumKeStatus(b.status), false);
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi DlgTTE muatStatus : " + ex);
                    }
                }
                return h;
            }
            @Override protected void done() {
                try {
                    SatuSehatSignatureAssembler.Hasil h = get();
                    curNoRawat = noRawat;
                    curNamaPasien = namaPasien;
                    curIdPractitioner = h.idPractitioner;
                    curNamaDpjp = h.namaDpjp;
                    curIdEncounter = h.idEncounter;
                    curDokumen = h.dokumen;
                    isiDokTabel(namaPasien);
                    aktifkanTandatangani(!curDokumen.isEmpty());
                    aktifkanUpdate(!curDokumen.isEmpty());
                } catch (Exception ex) {
                    labelDok.setText("Gagal memuat berkas: " + pesan(ex));
                    System.out.println("Notifikasi DlgTTE onPilihPasien : " + ex);
                }
            }
        }.execute();
    }

    private void isiDokTabel(String namaPasien) {
        hitungKunciGiliran();
        modelDok.setRowCount(0);
        int sudah = 0;
        int menunggu = 0;
        for (DokumenTte d : curDokumen) {
            // Default TIDAK tercentang -> operator memilih sendiri (checkbox / klik kanan "Pilih Semua").
            modelDok.addRow(new Object[]{Boolean.FALSE, d.jenis, d.labelPeran(), nz(d.signer),
                d.targetRef, labelStatusBaris(d)});
            if (d.status == Status.SUDAH) {
                sudah++;
            } else if (d.terkunci) {
                menunggu++;
            }
        }
        isiComboBerkas(); // segarkan Filter Berkas sesuai jenis dokumen pasien ini
        if (curDokumen.isEmpty()) {
            labelDok.setText("Berkas " + namaPasien + ": belum ada dokumen (Resume/SPRI) yang terkirim ke SATUSEHAT.");
        } else {
            // Hitungan dibaca sebagai TANDA TANGAN, bukan berkas: satu berkas yang butuh dua
            // tanda tangan menyumbang dua baris, dan menyebutnya "1 berkas" akan menyesatkan.
            int belum = curDokumen.size() - sudah - menunggu;
            StringBuilder t = new StringBuilder();
            t.append("Berkas ").append(namaPasien).append(": ")
             .append(sudah).append(" sudah / ").append(belum).append(" belum");
            if (menunggu > 0) {
                t.append(" / ").append(menunggu).append(" menunggu giliran");
            }
            t.append(" (").append(curDokumen.size()).append(" tanda tangan, ")
             .append(jumlahBerkas()).append(" berkas)");
            if (sudah > 0) {
                t.append("   — klik dua kali baris \"Sudah TTE\" untuk verifikasi");
            }
            labelDok.setText(t.toString());
        }
    }

    /** Jumlah BERKAS unik (bukan baris tanda tangan) yang sedang ditampilkan. */
    private int jumlahBerkas() {
        java.util.Set<String> unik = new java.util.HashSet<>();
        for (DokumenTte d : curDokumen) {
            unik.add(d.targetRef);
        }
        return unik.size();
    }

    /**
     * Tandai baris model SERIAL yang belum boleh dikerjakan karena giliran sebelumnya belum
     * selesai. Tanpa ini operator bisa mencentang tanda tangan ke-2 lebih dulu, lalu Provenance-nya
     * dibuat tanpa rantai entity[] ke pendahulunya — rusak tanpa pesan galat dari server.
     */
    private void hitungKunciGiliran() {
        for (DokumenTte d : curDokumen) {
            d.terkunci = false;
            d.alasanKunci = "";
            if (d.model != SatuSehatTteModel.Model.SERIAL || d.urutan <= 1 || d.status == Status.SUDAH) {
                continue;
            }
            for (DokumenTte lain : curDokumen) {
                if (!lain.targetRef.equals(d.targetRef) || lain.urutan >= d.urutan) {
                    continue;
                }
                if (lain.status != Status.SUDAH) {
                    d.terkunci = true;
                    d.alasanKunci = "menunggu " + (nz(lain.signer).equals("") ? "giliran " + lain.urutan : lain.signer);
                }
            }
        }
    }

    /** Label kolom Status: baris terkunci menyebut siapa yang ditunggu, bukan sekadar "Belum TTE". */
    private String labelStatusBaris(DokumenTte d) {
        if (d.terkunci) {
            return "Menunggu " + d.alasanKunci.replace("menunggu ", "");
        }
        return SatuSehatSignatureState.label(d.status);
    }

    private String mapEnumKeStatus(String statusDb) {
        if (statusDb == null) {
            return "";
        }
        switch (statusDb.trim().toLowerCase()) {
            case "signed":                        // hasil per-dokumen Task.output -> setara completed
            case "completed":   return "completed";
            case "failed":                        // gagal TTE per-dokumen -> setara rejected (bisa diulang)
            case "rejected":    return "rejected";
            case "in-progress": return "in-progress";
            case "requested":   return "requested";
            default:            return "";
        }
    }

    /** true bila ada berkas tercentang yang modelnya bukan single (paralel/serial). */
    private boolean adaMultiPenanda(List<DokumenTte> grup) {
        for (DokumenTte d : grup) {
            if (d.model != SatuSehatTteModel.Model.SINGLE) {
                return true;
            }
        }
        return false;
    }

    /**
     * targetRef -> SELURUH penanda berkas itu (bukan hanya yang tercentang). Dipakai model paralel
     * yang menaruh semua penanda sebagai agent dalam SATU Provenance, sehingga penanda kedua tinggal
     * memakai Provenance yang sama alih-alih membuat yang baru.
     */
    private Map<String, List<SatuSehatProvenance.Penanda>> petaPenanda() {
        Map<String, List<SatuSehatProvenance.Penanda>> peta = new java.util.LinkedHashMap<>();
        for (DokumenTte d : curDokumen) {
            if (nz(d.signerIhs).equals("")) {
                continue;
            }
            if (!peta.containsKey(d.targetRef)) {
                peta.put(d.targetRef, new ArrayList<SatuSehatProvenance.Penanda>());
            }
            peta.get(d.targetRef).add(new SatuSehatProvenance.Penanda(d.signerIhs, d.signer, d.peran));
        }
        return peta;
    }

    /** Tombol Tandatangani: buat Provenance placeholder utk berkas tercentang -> satu Task -> popup QR. */
    private void aksiTandatangani() {
        // Penjaga kedua di samping tombol yang dimatikan: aksi ini menerbitkan Provenance & Task
        // ke SATUSEHAT (tak bisa dibatalkan), jadi hak akses diperiksa lagi tepat sebelum jalan.
        if (!aksesTte) {
            JOptionPane.showMessageDialog(this,
                    "Maaf, Anda tidak punya hak akses untuk tanda tangan elektronik...!!!!");
            return;
        }
        final List<DokumenTte> dicentang = new ArrayList<>();
        for (int i = 0; i < curDokumen.size(); i++) {
            Boolean centang = (Boolean) modelDok.getValueAt(i, 0);
            DokumenTte d = curDokumen.get(i);
            if (Boolean.TRUE.equals(centang) && d.status != Status.SUDAH && !d.terkunci) {
                dicentang.add(d);
            }
        }
        if (dicentang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Centang minimal satu berkas yang belum ditandatangani.");
            return;
        }
        // Klasifikasi berkas tercentang (selaras webhook "taskSignatureDone"):
        //  - reuse : DITOLAK tapi Provenance placeholder masih ada -> cukup POST Task BARU yang me-REUSE
        //            Provenance itu. JANGAN lewat Bundle: Bundle membuat Provenance baru untuk target yang
        //            sama -> duplikat (RuleNumber 20002). (sesuai "Provenance bisa di reuse")
        //  - baru  : belum punya Provenance sama sekali -> Bundle (Provenance placeholder + Task, ATOMIK).
        //  - sudahAda: sudah punya Task (belum SUDAH/DITOLAK) -> cukup buka lagi QR-nya.
        final List<DokumenTte> reuse = new ArrayList<>();
        final List<DokumenTte> baru = new ArrayList<>();
        String uuidAda = "";
        for (DokumenTte d : dicentang) {
            if (d.status == Status.DITOLAK && !nz(d.idProvenance).equals("")) {
                reuse.add(d);
            } else if (d.status == Status.DITOLAK) {
                // Ditolak tapi id_provenance tak tersimpan -> terpaksa buat Provenance+Task baru.
                d.taskUuid = "";
                d.idProvenance = "";
                baru.add(d);
            } else if (d.taskUuid == null || d.taskUuid.equals("")) {
                baru.add(d);
            } else {
                uuidAda = d.taskUuid;
            }
        }
        if (reuse.isEmpty() && baru.isEmpty()) {
            // Semua berkas tercentang sudah pernah dibuatkan Task -> buka lagi QR-nya (hindari duplicate 20002).
            bukaPopupQr(uuidAda);
            return;
        }
        if (!reuse.isEmpty() && !baru.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Berkas DITOLAK (dibuat ulang) dan berkas baru harus ditandatangani terpisah.\n"
                    + "Centang salah satu kelompok saja dulu.");
            return;
        }
        final boolean modeReuse = !reuse.isEmpty();
        final List<DokumenTte> grup = modeReuse ? reuse : baru;
        // Satu QR dipindai satu dokter -> berkas dalam satu grup harus milik SATU dokter (signer sama).
        // Dikelompokkan lewat IHS Practitioner, BUKAN nama: dua orang bisa bernama sama, dan nama
        // kosong akan membuat mereka terlihat sebagai satu penanda lalu semua Task memakai IHS
        // baris pertama — tanda tangan atas nama orang lain.
        java.util.LinkedHashSet<String> dokterSet = new java.util.LinkedHashSet<>();
        java.util.LinkedHashSet<String> namaSet = new java.util.LinkedHashSet<>();
        for (DokumenTte d : grup) {
            dokterSet.add(nz(d.signerIhs));
            namaSet.add(nz(d.signer) + " (" + nz(d.signerIhs) + ")");
        }
        if (dokterSet.size() > 1) {
            JOptionPane.showMessageDialog(this,
                    "Berkas tercentang milik lebih dari satu penanda tangan:\n- "
                    + String.join("\n- ", namaSet)
                    + "\n\nTTE dipindai per dokter. Centang berkas untuk SATU dokter saja.");
            return;
        }
        final String grpNama = nz(grup.get(0).signer);
        final String grpIhs = nz(grup.get(0).signerIhs);
        if (grpIhs.equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Dokter \"" + grpNama + "\" (Practitioner IHS) belum ter-mapping. TTE dibatalkan.");
            return;
        }
        // Tanpa Encounter, TTE hanya bisa lewat varian bulk "electronic-sign-doc-out" — dan varian itu
        // MENSYARATKAN seluruh target berupa DiagnosticReport (RuleNumber 10871, diuji staging
        // 1 Agustus 2026: target Composition tanpa encounter ditolak). Selain itu tetap dibatalkan.
        List<String> targetTerpilih = new ArrayList<>();
        for (DokumenTte d : grup) {
            targetTerpilih.add(d.targetRef);
        }
        final boolean bulkOut = curIdEncounter.equals("")
                && SatuSehatTask.semuaTargetDiagnosticReport(targetTerpilih);
        if (curIdEncounter.equals("") && !bulkOut) {
            JOptionPane.showMessageDialog(this, "Encounter kunjungan ini belum terkirim ke SATUSEHAT. TTE dibatalkan.");
            return;
        }
        if (bulkOut) {
            logInfo("Encounter belum ada, tetapi seluruh berkas terpilih berupa DiagnosticReport "
                    + "-> memakai Task bulk lintas-encounter (electronic-sign-doc-out).");
        }
        final String noRawat = curNoRawat;
        final String idPractitioner = grpIhs;   // signer per-berkas (DPJP utk ranap, dokter reg utk ralan/SPRI)
        final String namaDpjp = grpNama;
        final String idEncounter = curIdEncounter;
        tombolTandatangani.setEnabled(false);
        new SwingWorker<String, Void>() {
            @Override protected String doInBackground() throws Exception {
                String uuid;
                if (modeReuse) {
                    // RETRY DITOLAK: Provenance placeholder masih ada di server -> cukup POST Task baru yang
                    // me-reference Provenance itu (reuse). Task lama yang rejected ditinggalkan begitu saja.
                    List<String> provIds = new ArrayList<>();
                    for (DokumenTte d : grup) {
                        provIds.add(d.idProvenance);
                    }
                    logInfo("Retry ditolak: reuse " + provIds.size() + " Provenance lama, buat Task baru"
                            + " (DPJP " + namaDpjp + ").");
                    try {
                        uuid = bulkOut
                                ? taskSender.buatTaskBulkDiagnosticReport(idPractitioner, namaDpjp, provIds)
                                : taskSender.buatTask(idPractitioner, namaDpjp, idEncounter, provIds);
                    } catch (Exception ex) {
                        // Arsipkan dulu payload yang ditolak, baru lempar ulang: bukti penolakan
                        // inilah yang dibutuhkan saat melapor ke Kemenkes.
                        store.simpanBundle("task", noRawat, "", idPractitioner, provIds.size(),
                                taskSender.terakhir);
                        throw ex;
                    }
                    store.simpanBundle("task", noRawat, uuid, idPractitioner, provIds.size(),
                            taskSender.terakhir);
                    logSukses("Task baru " + uuid + " dibuat untuk Provenance " + provIds + ".");
                    for (DokumenTte d : grup) {
                        d.taskUuid = uuid;
                        d.status = Status.BELUM;
                        store.simpan(noRawat, d.jenis, d.targetRef, d.idProvenance, uuid, "requested", idPractitioner);
                    }
                    return uuid;
                }
                // BARU & MULTI-PENANDA: model paralel/serial tidak bisa lewat Bundle karena
                // Provenance-nya dipakai bersama (paralel) atau butuh id pendahulunya (serial).
                // Jalur ini memakai SatuSehatSignatureFlow yang menanganinya satu per satu.
                if (adaMultiPenanda(grup)) {
                    Map<String, List<SatuSehatProvenance.Penanda>> penandaPerDok = petaPenanda();
                    logInfo("Kirim TTE multi-penanda: " + grup.size() + " tanda tangan, pasien " + noRawat
                            + ", penanda " + namaDpjp + ".");
                    SatuSehatSignatureFlow.Hasil hf = flowSender.kirim(noRawat, grup, penandaPerDok,
                            idPractitioner, namaDpjp, idEncounter, bulkOut, p -> logInfo(p));
                    if (!hf.berhasil) {
                        throw new IllegalStateException(hf.pesan);
                    }
                    logSukses(hf.pesan);
                    for (DokumenTte d : grup) {
                        d.status = Status.BELUM;
                    }
                    return hf.taskUuid;
                }

                // BARU: Bundle transaction ("02. Bundle Buat TTE"): seluruh Provenance placeholder + Task
                // dibuat ATOMIK -> tak ada Provenance yatim bila salah satu gagal.
                List<SatuSehatBundleProvenance.Target> targets = new ArrayList<>();
                for (DokumenTte d : grup) {
                    targets.add(new SatuSehatBundleProvenance.Target(d.targetRef, d.display, d.mulaiUtc, d.selesaiUtc));
                }
                logInfo("Kirim Bundle TTE: " + targets.size() + " berkas, pasien " + noRawat
                        + ", DPJP " + namaDpjp + ", Encounter " + idEncounter + ".");
                SatuSehatBundleProvenance.HasilBundle hb;
                try {
                    hb = bundleSender.kirimBundle(targets, idPractitioner, namaDpjp, idEncounter, bulkOut);
                } catch (Exception ex) {
                    // Arsipkan payload yang ditolak sebelum melempar ulang.
                    store.simpanBundle("bundle", noRawat, "", idPractitioner, targets.size(),
                            bundleSender.terakhir);
                    throw ex;
                }
                uuid = hb.taskId;
                store.simpanBundle("bundle", noRawat, uuid, idPractitioner, targets.size(),
                        bundleSender.terakhir);
                logSukses("Bundle diterima SATUSEHAT -> Task " + uuid
                        + ", Provenance " + hb.provIds + ".");
                if (hb.provIds.size() != targets.size()) {
                    logWarn("Jumlah Provenance (" + hb.provIds.size() + ") tidak sama dengan jumlah berkas ("
                            + targets.size() + ") - sebagian berkas mungkin tidak ikut tertandatangani.");
                }
                for (int i = 0; i < grup.size(); i++) {
                    DokumenTte d = grup.get(i);
                    d.idProvenance = (i < hb.provIds.size()) ? hb.provIds.get(i) : "";
                    d.taskUuid = uuid;
                    d.status = Status.BELUM;
                    store.simpan(noRawat, d.jenis, d.targetRef, d.idProvenance, uuid, "requested", idPractitioner);
                }
                return uuid;
            }
            @Override protected void done() {
                aktifkanTandatangani(true);
                try {
                    String uuid = get();
                    if (uuid == null || uuid.equals("")) {
                        JOptionPane.showMessageDialog(SatuSehatBridgingTTE.this, "Gagal membuat Task (UUID kosong).");
                        return;
                    }
                    refreshDokTabel();
                    bukaPopupQr(uuid);
                } catch (Exception ex) {
                    Throwable c = (ex.getCause() != null) ? ex.getCause() : ex;
                    String body = (c instanceof HttpClientErrorException)
                            ? ((HttpClientErrorException) c).getResponseBodyAsString() : "";
                    if (body.contains("duplicate") || body.contains("20002")) {
                        JOptionPane.showMessageDialog(SatuSehatBridgingTTE.this,
                                "Berkas ini sudah pernah dibuatkan permintaan TTE di SATUSEHAT.\n"
                                + "Tutup lalu buka ulang pasien untuk memuat status terbaru.");
                    } else {
                        JOptionPane.showMessageDialog(SatuSehatBridgingTTE.this, "Gagal membuat TTE:\n" + pesan(ex));
                    }
                    logError("Gagal membuat TTE: " + pesan(ex)
                            + (body.equals("") ? "" : " | respons server: " + body));
                }
            }
        }.execute();
    }

    /**
     * Tarik status TTE terbaru dari SATUSEHAT untuk berkas pasien yang sedang tampil (mode TANPA webhook).
     * GET Task per task_uuid unik yang belum final -> perbarui status_tte di DB + baris tabel. Tak mengubah
     * centang/filter (hanya kolom Status TTE + label ringkasan). Berguna bila webhook belum aktif / dialog QR
     * sudah ditutup padahal dokter baru menandatangani.
     */
    private void aksiUpdateStatus() {
        if (curDokumen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih pasien dulu untuk memuat berkas.");
            return;
        }
        final java.util.LinkedHashSet<String> uuids = new java.util.LinkedHashSet<>();
        for (DokumenTte d : curDokumen) {
            if (d.taskUuid != null && !d.taskUuid.equals("") && d.status != Status.SUDAH) {
                uuids.add(d.taskUuid);
            }
        }
        if (uuids.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tidak ada berkas yang perlu diperbarui (belum dibuatkan TTE, atau semua sudah TTE).");
            return;
        }
        tombolUpdate.setEnabled(false);
        tombolTandatangani.setEnabled(false);
        labelDok.setText("Memperbarui status " + uuids.size() + " permintaan TTE dari SATUSEHAT...");
        new SwingWorker<java.util.Map<String, HasilTarik>, Void>() {
            @Override protected java.util.Map<String, HasilTarik> doInBackground() {
                // GET Task per uuid di thread latar; penerapan ke tabel dilakukan di done() (EDT).
                // Per dokumen bila Task.output[] ada (TTE bulk bisa campuran), jika tidak -> Task.status.
                java.util.Map<String, HasilTarik> out = new java.util.LinkedHashMap<>();
                for (String uuid : uuids) {
                    try {
                        out.put(uuid, tarikStatusTTE(uuid, provDokumenTask(uuid), Pemicu.MANUAL));
                    } catch (Exception ex) {
                        System.out.println("Notifikasi DlgTTE aksiUpdateStatus(" + uuid + ") : " + ex);
                    }
                }
                return out;
            }
            @Override protected void done() {
                try {
                    for (java.util.Map.Entry<String, HasilTarik> e : get().entrySet()) {
                        terapkanHasil(e.getKey(), e.getValue());
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi DlgTTE aksiUpdateStatus done : " + ex);
                }
                aktifkanUpdate(!curDokumen.isEmpty());
                aktifkanTandatangani(!curDokumen.isEmpty());
                refreshDokTabel();
                int sudah = 0;
                for (DokumenTte d : curDokumen) {
                    if (d.status == Status.SUDAH) {
                        sudah++;
                    }
                }
                labelDok.setText("Berkas " + curNamaPasien + ": " + sudah + " sudah / "
                        + (curDokumen.size() - sudah) + " belum TTE"
                        + (sudah > 0 ? "   (klik dua kali berkas \"Sudah TTE\" untuk verifikasi)" : ""));
            }
        }.execute();
    }

    private void refreshDokTabel() {
        for (int i = 0; i < curDokumen.size() && i < modelDok.getRowCount(); i++) {
            modelDok.setValueAt(labelStatusBaris(curDokumen.get(i)), i, 5);
        }
    }

    /** Popup QR dinamis + polling status untuk task tertentu. */
    private void bukaPopupQr(String taskUuid) {
        this.taskAktif = taskUuid;
        this.qrIssuedSec = 0L;
        this.basisApplink = nz(koneksiDB.URLAPPLINKSATUSEHAT());
        tutupPopupQr();

        if (basisApplink.equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Basis deep-link SATUSEHAT tidak dikenali.\n"
                    + "Periksa URLFHIRSATUSEHAT di setting/database.xml, atau isi manual\n"
                    + "kunci URLAPPLINKSATUSEHAT. QR tidak diterbitkan agar tidak menunjuk\n"
                    + "lingkungan yang salah.\nTask: " + ringkas(taskUuid));
            return;
        }

        popupQr = new JDialog(this, "Scan QR TTE", false);
        popupQr.setUndecorated(true);
        JPanel isi = new JPanel();
        isi.setLayout(new javax.swing.BoxLayout(isi, javax.swing.BoxLayout.Y_AXIS));
        isi.setBackground(Color.WHITE);
        isi.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(205, 210, 215)),
                BorderFactory.createEmptyBorder(18, 26, 16, 26)));

        // --- Judul + garis pemisah ---
        JLabel judulQr = new JLabel("Scan QR TTE", SwingConstants.CENTER);
        judulQr.setFont(new Font("SansSerif", Font.PLAIN, 20));
        judulQr.setForeground(new Color(45, 55, 62));
        judulQr.setAlignmentX(Component.CENTER_ALIGNMENT);
        isi.add(judulQr);
        isi.add(javax.swing.Box.createVerticalStrut(14));
        isi.add(garisPemisah());
        isi.add(javax.swing.Box.createVerticalStrut(14));

        // --- Instruksi (HTML supaya bisa rata tengah & membungkus rapi) ---
        JLabel instruksi = new JLabel("<html><div style='text-align:center;width:340px;'>"
                + "Buka <b>TTE Mobile</b> di ponsel Anda, lalu pindai kode QR "
                + "berikut untuk menandatangani dokumen</div></html>", SwingConstants.CENTER);
        instruksi.setFont(new Font("SansSerif", Font.PLAIN, 15));
        instruksi.setForeground(new Color(55, 65, 72));
        instruksi.setAlignmentX(Component.CENTER_ALIGNMENT);
        isi.add(instruksi);
        isi.add(javax.swing.Box.createVerticalStrut(12));

        JLabel catatanTtl = new JLabel("Data QR diperbarui otomatis setiap "
                + SatuSehatSignatureState.TTL_QR_DETIK + " detik.", SwingConstants.CENTER);
        catatanTtl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        catatanTtl.setForeground(new Color(120, 135, 150));
        catatanTtl.setAlignmentX(Component.CENTER_ALIGNMENT);
        isi.add(catatanTtl);
        isi.add(javax.swing.Box.createVerticalStrut(16));

        // --- Kartu QR ---
        popupGambar = new JLabel("", SwingConstants.CENTER);
        popupGambar.setPreferredSize(new Dimension(QR_PX + 40, QR_PX + 40));
        JPanel kartuQr = new JPanel(new java.awt.GridBagLayout());
        kartuQr.setBackground(new Color(250, 250, 250));
        kartuQr.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 232, 236)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));
        kartuQr.add(popupGambar);
        kartuQr.setAlignmentX(Component.CENTER_ALIGNMENT);
        isi.add(kartuQr);
        isi.add(javax.swing.Box.createVerticalStrut(16));
        isi.add(garisPemisah());
        isi.add(javax.swing.Box.createVerticalStrut(12));

        // --- Hitung mundur + status tunggu ---
        popupInfo = new JLabel("", SwingConstants.CENTER);
        popupInfo.setFont(new Font("SansSerif", Font.PLAIN, 17));
        popupInfo.setForeground(new Color(45, 55, 62));
        popupInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        isi.add(popupInfo);
        isi.add(javax.swing.Box.createVerticalStrut(4));

        popupTunggu = new JLabel("Menunggu tanda tangan...", SwingConstants.CENTER);
        popupTunggu.setFont(new Font("SansSerif", Font.PLAIN, 12));
        popupTunggu.setForeground(new Color(150, 160, 170));
        popupTunggu.setAlignmentX(Component.CENTER_ALIGNMENT);
        isi.add(popupTunggu);
        isi.add(javax.swing.Box.createVerticalStrut(16));

        // --- Dua tombol lebar: Batal (merah) & Cek Status (oranye) ---
        JPanel barTombol = new JPanel(new java.awt.GridLayout(1, 2, 12, 0));
        barTombol.setOpaque(false);
        barTombol.setAlignmentX(Component.CENTER_ALIGNMENT);
        barTombol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        JButton bBatal = tombolWarna("Batal", new Color(240, 128, 118));
        bBatal.addActionListener(e -> tutupPopupQr());
        JButton bCek = tombolWarna("Cek Status", new Color(243, 166, 60));
        // Cek manual: operator tak perlu menunggu putaran polling berikutnya.
        bCek.addActionListener(e -> pollSekali());
        barTombol.add(bBatal);
        barTombol.add(bCek);
        isi.add(barTombol);

        popupQr.setContentPane(isi);
        popupQr.pack();
        popupQr.setLocationRelativeTo(this);
        popupQr.setVisible(true);

        timerQr = new Timer(1000, e -> tickQr());
        timerQr.setInitialDelay(0);
        timerQr.start();
        timerPoll = new Timer((int) (SatuSehatSignatureState.TTL_QR_DETIK * 1000L), e -> pollSekali());
        timerPoll.start();
    }

    /**
     * Tempatkan kedua pembagi panel. Acuan tinggi WAJIB tinggi splitUtama, bukan tinggi dialog —
     * dialog masih memuat bar filter/tombol (~88 px) di bawahnya, sehingga memakai getHeight()
     * membuat panel Log tergencet hampir hilang.
     */
    private void aturDivider(JSplitPane splitUtama, JSplitPane splitTabel) {
        int h = splitUtama.getHeight();
        if (h > TINGGI_LOG_PX + 200) {
            splitUtama.setDividerLocation(h - TINGGI_LOG_PX);
        }
        if (splitTabel.getWidth() > 400) {
            splitTabel.setDividerLocation(0.55);
        }
    }

    /** Garis pemisah tipis selebar dialog (pengganti JSeparator agar tinggi tak melar di BoxLayout). */
    private JPanel garisPemisah() {
        JPanel g = new JPanel();
        g.setBackground(new Color(232, 236, 240));
        // Ketiganya diset: BoxLayout memakai minimum/preferred/maximum, bila hanya preferred yang
        // diatur garisnya bisa melar beberapa piksel.
        g.setMinimumSize(new Dimension(10, 1));
        g.setPreferredSize(new Dimension(10, 1));
        g.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        g.setAlignmentX(Component.CENTER_ALIGNMENT);
        return g;
    }

    /** Tombol blok berwarna penuh (teks putih) untuk bar aksi popup QR. */
    private JButton tombolWarna(String teks, Color warna) {
        JButton b = new JButton(teks);
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        b.setForeground(Color.WHITE);
        b.setBackground(warna);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(150, 40));
        b.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        return b;
    }

    private void tickQr() {
        if (taskAktif.equals("") || popupGambar == null) {
            return;
        }
        long now = nowSec();
        if (qrIssuedSec == 0L || SatuSehatSignatureState.qrKedaluwarsa(qrIssuedSec, now)) {
            qrIssuedSec = now;
            try {
                BufferedImage img = qr.render(
                        SatuSehatSignatureState.qrPayload(basisApplink, taskAktif, now), QR_PX);
                popupGambar.setIcon(new ImageIcon(img));
                popupGambar.setText("");
            } catch (Exception ex) {
                popupGambar.setIcon(null);
                popupGambar.setText("QR gagal dirender");
                System.out.println("Notifikasi DlgTTE tickQr : " + ex);
            }
        }
        if (popupInfo != null) {
            if (qr.aktif()) {
                long sisa = Math.max(SatuSehatSignatureState.TTL_QR_DETIK - (now - qrIssuedSec), 0);
                popupInfo.setText("Kode diperbarui dalam " + sisa + " detik");
            } else {
                popupInfo.setText("QR belum aktif (pasang ZXing)");
            }
        }
    }

    /**
     * Tarik status TTE satu Task dari SATUSEHAT — TAHAN dua perilaku SSM/BSrE saat sukses pindai:
     *  (a) SSM menyelesaikan Task (Task.status=completed) — sesuai koleksi resmi "03. SSM"; ATAU
     *  (b) SSM HANYA menandatangani Provenance (Provenance.signature terisi, activity=SIGN) sedangkan
     *      Task.status TETAP "requested" — perilaku build SSM Practitioner. Maka Task.status saja TAK cukup;
     *      cek Provenance juga. Ini sebab "webhook sukses scan / update belum berfungsi" bila hanya baca Task.
     * Efek samping best-effort: simpan status_tte + trace-id ke satu_sehat_provenance. "" bila Task tak terbaca.
     */
    /** Hasil GET Task: status Task keseluruhan + peta outcome per Provenance (dari Task.output[]). */
    private static final class HasilTarik {
        String statusTask = "";                                                   // requested/in-progress/completed/rejected
        java.util.Map<String, String> perProv = java.util.Collections.emptyMap(); // provId -> signed/failed/rejected
        boolean adaOutput() {
            return !perProv.isEmpty();
        }
    }

    private HasilTarik tarikStatusTTE(String taskUuid, String idProvenanceHint, Pemicu pemicu) {
        HasilTarik hasil = new HasilTarik();
        JsonNode task = taskSender.bacaTask(taskUuid);
        if (task == null) {
            log("WARN", pemicu.tag() + " Task/" + taskUuid + " tidak terbaca dari SATUSEHAT");
            return hasil;
        }
        String stTask = taskSender.status(task).toLowerCase();
        hasil.statusTask = stTask;
        // Normalkan status Task: SSM bisa menandai penolakan/kegagalan dengan bentuk lain selain "rejected"
        // (cancelled/failed/entered-in-error/aborted) — semuanya dipetakan -> "rejected" agar berkas
        // ditandai DITOLAK & statusnya ter-persist (bukan tertinggal "requested"/BELUM saat reselect).
        String stNorm = normalisasiStatusTask(stTask);
        String statusReason = teksStatusReason(task);
        if (!stNorm.equals(stTask)) {
            log("INFO", pemicu.tag() + " Task/" + taskUuid + " Task.status=" + stTask
                    + " dinormalkan -> " + stNorm
                    + (statusReason.equals("") ? "" : " (statusReason: " + statusReason + ")"));
        }
        // trace-id (Task.meta.tag) diarsipkan bila SSM sudah mengisinya, terlepas dari cabang di bawah.
        String trace = taskSender.traceId(task);
        if (!trace.equals("")) {
            store.updateTraceByTask(taskUuid, trace);
        }

        // (1) SUMBER KEBENARAN per-dokumen: Task.output[] (signed/failed/rejected per Provenance).
        // SSM menjanjikan |output| == |input| saat Task dituntaskan; untuk TTE bulk, satu Task bisa
        // completed dengan CAMPURAN outcome, jadi status tiap dokumen diambil dari sini, bukan Task.status.
        java.util.Map<String, String> perProv = taskSender.hasilPerProvenance(task);
        if (!perProv.isEmpty()) {
            hasil.perProv = perProv;
            String timpang = taskSender.cekTimpangInputOutput(task);
            if (!timpang.equals("")) {
                log("WARN", pemicu.tag() + " Task/" + taskUuid + " " + timpang
                        + " — sebagian hasil dokumen mungkin belum dilaporkan SSM.");
            }
            StringBuilder rincian = new StringBuilder();
            int signed = 0, gagal = 0;
            for (java.util.Map.Entry<String, String> e : perProv.entrySet()) {
                // status_tte = ENUM('belum','requested','in-progress','completed','rejected') — tak punya
                // 'signed'/'failed'. Petakan dulu ke nilai ENUM sah, kalau tidak MySQL menolak (Data
                // truncated) & status per-dokumen TAK tersimpan (gejala: "Sudah TTE" balik "Belum" saat reselect).
                String dbSt = SatuSehatSignatureState.statusDbDariOutput(e.getValue());
                if (!dbSt.equals("")) {
                    store.updateStatusByProvenance(e.getKey(), dbSt); // status per-dokumen ke DB lokal
                }
                if (e.getValue().equals("signed")) {
                    signed++;
                } else {
                    gagal++;
                }
                if (rincian.length() > 0) {
                    rincian.append(", ");
                }
                rincian.append("Provenance/").append(e.getKey()).append('=').append(e.getValue());
            }
            log(gagal > 0 ? "WARN" : "OK",
                    pemicu.tag() + " Task/" + taskUuid + " Task.status=" + stTask
                    + "; Task.output[" + perProv.size() + "] (" + signed + " signed / " + gagal
                    + " gagal-tolak): " + rincian);
            return hasil;
        }

        // (2) Belum ada Task.output[]: TAHAN perilaku SSM yang hanya mengisi Provenance.signature tanpa
        // mengubah Task.status. Jejak Provenance dicatat agar operator tahu dokumen mana yang sudah TTE.
        String st = stNorm;   // status yang sudah dinormalkan (reject-like -> "rejected")
        String pid = nz(idProvenanceHint);
        String jejakProv;
        if (!st.equals("completed") && !st.equals("rejected")) {
            if (pid.equals("")) {
                pid = idProvenanceDariTask(task);
            }
            boolean sudahTtd = !pid.equals("")
                    && provSender.sudahDitandatangani(provSender.bacaProvenance(pid));
            jejakProv = pid.equals("")
                    ? "Provenance=? (id tidak diketahui)"
                    : "Provenance/" + pid + " Provenance.signature=" + (sudahTtd ? "1" : "0");
            if (sudahTtd) {
                st = "completed"; // Provenance sudah ditandatangani SSM walau Task.status belum diubah.
            }
        } else {
            jejakProv = pid.equals("")
                    ? "Provenance=- (Task.status final, tidak dicek)"
                    : "Provenance/" + pid + " (Task.status final, tidak dicek)";
        }
        // Format mengikuti penamaan JSON FHIR: referensi "Task/<id>", "Provenance/<id>", dan jalur
        // field "Task.status" / "Provenance.signature" — supaya baris log bisa dicocokkan langsung
        // dengan payload di Postman maupun di Audit Trail SSM.
        log(st.equals("rejected") ? "ERROR" : (st.equals("completed") ? "OK" : "INFO"),
                pemicu.tag() + " Task/" + taskUuid + " Task.status=" + stTask
                + (statusReason.equals("") ? "" : " (reason: " + statusReason + ")")
                + "; " + jejakProv + " -> status=" + st);
        store.updateStatusByTask(taskUuid, st);
        hasil.statusTask = st;
        return hasil;
    }

    /** Petakan Task.status SSM ke kanonik: completed; reject-like (rejected/cancelled/failed/entered-in-error/aborted) -> "rejected"; lainnya apa adanya. */
    private String normalisasiStatusTask(String st) {
        String s = (st == null) ? "" : st.trim().toLowerCase();
        switch (s) {
            case "completed":
                return "completed";
            case "rejected":
            case "cancelled":
            case "failed":
            case "entered-in-error":
            case "aborted":
                return "rejected";
            default:
                return s;   // requested / in-progress / received / accepted / dll
        }
    }

    /** Ambil teks alasan dari Task.statusReason (text / coding.display / coding.code); "" bila tak ada. */
    private String teksStatusReason(JsonNode task) {
        JsonNode sr = task.path("statusReason");
        if (!sr.isObject()) {
            return "";
        }
        String t = sr.path("text").asText();
        if (t != null && !t.trim().equals("")) {
            return t.trim();
        }
        JsonNode c = sr.path("coding");
        if (c.isArray() && c.size() > 0) {
            String d = c.get(0).path("display").asText();
            if (d != null && !d.trim().equals("")) {
                return d.trim();
            }
            String k = c.get(0).path("code").asText();
            return k == null ? "" : k.trim();
        }
        return "";
    }

    /**
     * Terapkan hasil GET Task ke status dokumen di tabel (dipanggil di EDT).
     * Per-dokumen bila Task.output[] ada; jika tidak, fallback ke Task.status keseluruhan.
     */
    private void terapkanHasil(String uuid, HasilTarik r) {
        if (r == null) {
            return;
        }
        for (DokumenTte d : curDokumen) {
            if (!uuid.equals(d.taskUuid)) {
                continue;
            }
            Status ui = null;
            if (r.adaOutput()) {
                String out = r.perProv.get(nz(d.idProvenance));
                if (out != null) {
                    ui = SatuSehatSignatureState.statusDariOutput(out);
                }
            }
            if (ui == null && !r.statusTask.equals("")) {
                ui = SatuSehatSignatureState.uiState(uuid, r.statusTask, false);
            }
            if (ui != null) {
                d.status = ui;
            }
        }
    }

    /** Ringkasan status seluruh dokumen satu Task untuk keputusan popup QR. null bila campuran / tanpa dokumen. */
    private Status statusAgregat(String uuid) {
        int total = 0, sudah = 0, tolak = 0, pending = 0;
        for (DokumenTte d : curDokumen) {
            if (!uuid.equals(d.taskUuid)) {
                continue;
            }
            total++;
            if (d.status == Status.SUDAH) {
                sudah++;
            } else if (d.status == Status.DITOLAK) {
                tolak++;
            } else {
                pending++;
            }
        }
        if (total == 0) {
            return null;
        }
        if (pending > 0) {
            return Status.DIPROSES;   // masih ada dokumen menunggu tanda tangan
        }
        if (sudah == total) {
            return Status.SUDAH;      // semua tertandatangani
        }
        if (tolak == total) {
            return Status.DITOLAK;    // semua gagal/ditolak
        }
        return null;                  // CAMPURAN (sebagian sukses) — pemanggil beri pesan rinci
    }

    /** Pesan ringkas untuk hasil TTE campuran (sebagian sukses, sebagian gagal/ditolak) pada satu Task. */
    private String ringkasHasilCampuran(String uuid) {
        int sudah = 0, gagal = 0;
        for (DokumenTte d : curDokumen) {
            if (!uuid.equals(d.taskUuid)) {
                continue;
            }
            if (d.status == Status.SUDAH) {
                sudah++;
            } else if (d.status == Status.DITOLAK) {
                gagal++;
            }
        }
        return "Sebagian berkas selesai: " + sudah + " ditandatangani, " + gagal
                + " gagal/ditolak.\nUlangi TTE untuk berkas yang gagal/ditolak.";
    }

    /** Segarkan status dokumen tabel dari DB lokal (dipakai setelah sinyal webhook diproses ke store). */
    private void segarkanStatusDokumenDariDb() {
        if (curDokumen == null || curDokumen.isEmpty() || curNoRawat.equals("")) {
            return;
        }
        for (DokumenTte d : curDokumen) {
            try {
                SatuSehatProvenanceStore.Baris b = store.ambil(curNoRawat, d.targetRef, d.signerIhs);
                if (b != null && !nz(b.status).equals("")) {
                    Status ui = SatuSehatSignatureState.uiState(nz(b.taskUuid), mapEnumKeStatus(b.status), false);
                    if (ui != null) {
                        d.status = ui;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi DlgTTE segarkanStatusDokumenDariDb : " + ex);
            }
        }
    }

    /** idProvenance dokumen (yang sedang tampil) milik satu Task; "" bila tak ada. Hint deteksi Provenance-signed. */
    private String provDokumenTask(String taskUuid) {
        for (DokumenTte d : curDokumen) {
            if (taskUuid.equals(d.taskUuid) && !nz(d.idProvenance).equals("")) {
                return d.idProvenance;
            }
        }
        return "";
    }

    private void pollSekali() {
        if (taskAktif.equals("")) {
            return;
        }
        final String uuid = taskAktif;
        final String provId = provDokumenTask(uuid);
        new SwingWorker<HasilTarik, Void>() {
            @Override protected HasilTarik doInBackground() {
                // Webhook hanya PEMICU; kebenaran per dokumen tetap dari GET Task (Task.output[]).
                // GET tiap tick wajar: sesi QR berumur pendek dan berhenti begitu seluruh dokumen final.
                return tarikStatusTTE(uuid, provId, Pemicu.QR_SESSION);
            }
            @Override protected void done() {
                try {
                    HasilTarik r = get();
                    if (r == null || r.statusTask.equals("")) {
                        return;
                    }
                    terapkanHasil(uuid, r);
                    refreshDokTabel();
                    Status agg = statusAgregat(uuid);
                    if (agg == Status.SUDAH) {
                        tutupPopupQr();
                        JOptionPane.showMessageDialog(SatuSehatBridgingTTE.this,
                                "Berkas sudah ditandatangani.\n"
                                + "Klik dua kali berkas \"Sudah TTE\" untuk melihat verifikasi tanda tangan.");
                    } else if (agg == Status.DITOLAK) {
                        tutupPopupQr();
                        JOptionPane.showMessageDialog(SatuSehatBridgingTTE.this, "Permintaan TTE ditolak/gagal. Silakan ulangi.");
                    } else if (agg == null && r.adaOutput()) {
                        // CAMPURAN: sebagian sukses, sebagian gagal/ditolak — sesi selesai, beri rincian.
                        tutupPopupQr();
                        JOptionPane.showMessageDialog(SatuSehatBridgingTTE.this, ringkasHasilCampuran(uuid));
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi DlgTTE pollSekali : " + ex);
                }
            }
        }.execute();
    }

    private void tutupPopupQr() {
        if (timerQr != null) {
            timerQr.stop();
        }
        if (timerPoll != null) {
            timerPoll.stop();
        }
        if (popupQr != null) {
            popupQr.dispose();
            popupQr = null;
        }
    }


    /**
     * Proses sinyal webhook yang masuk (tabel satu_sehat_task_webhook diisi penerima PHP).
     *
     * Sinyal HANYA dipakai sebagai pemicu: untuk tiap task_uuid yang disebut, kita tetap
     * GET Task ke SATUSEHAT lewat tarikStatusTTE(). Alasannya konkret — payload webhook bisa
     * mengklaim dokumen sudah ditandatangani padahal PUT Provenance dari SSM ditolak server
     * (kejadian 22 Juli 2026). Jadi SATUSEHAT tetap satu-satunya sumber kebenaran.
     *
     * Baris ditandai diproses SETELAH GET selesai, sehingga kegagalan di tengah jalan aman diulang
     * pada putaran berikutnya. Query-nya lokal (murah), karena itu jedanya jauh lebih rapat
     * daripada polling latar.
     */
    private void prosesSinyalWebhook() {
        if (sinyalBerjalan) {
            return;
        }
        final java.util.List<SatuSehatProvenanceStore.Sinyal> sinyal =
                store.ambilSinyalBelumDiproses(MAKS_SINYAL_PER_PUTARAN);
        if (sinyal.isEmpty()) {
            return;
        }
        sinyalBerjalan = true;
        new SwingWorker<Integer, Void>() {
            @Override protected Integer doInBackground() {
                java.util.List<Long> selesai = new java.util.ArrayList<>();
                java.util.Set<String> sudahDicek = new java.util.HashSet<>();  // 1 Task cukup 1x GET
                int ditindak = 0;
                for (SatuSehatProvenanceStore.Sinyal s : sinyal) {
                    try {
                        if (s.taskUuid.equals("")) {
                            // Payload tanpa task_uuid: tak ada yang bisa ditelusuri, tapi tetap
                            // ditandai selesai agar tidak menyumbat antrean. Payload mentahnya
                            // tersimpan di tabel untuk diperiksa manual.
                            logWarn(Pemicu.WEBHOOK.tag() + " notifikasi #" + s.id + " event=" + s.event
                                    + ") tanpa task_uuid - dilewati, periksa payload di satu_sehat_task_webhook.");
                        } else if (sudahDicek.add(s.taskUuid)) {
                            logInfo(Pemicu.WEBHOOK.tag() + " notifikasi #" + s.id + " event=" + s.event + " -> Task/"
                                    + s.taskUuid + " diverifikasi ke SATUSEHAT");
                            tarikStatusTTE(s.taskUuid, "", Pemicu.WEBHOOK);
                            ditindak++;
                        }
                        selesai.add(s.id);
                    } catch (Exception ex) {
                        // Jangan tandai selesai -> akan dicoba lagi putaran berikutnya.
                        System.out.println("Notifikasi DlgTTE prosesSinyalWebhook(#" + s.id + ") : " + ex);
                    }
                }
                store.tandaiSinyalDiproses(selesai);
                return ditindak;
            }
            @Override protected void done() {
                sinyalBerjalan = false;
                try {
                    if (get() > 0) {
                        // tarikStatusTTE sudah menulis status per-Provenance ke DB; sinkronkan tabel dari DB.
                        segarkanStatusDokumenDariDb();
                        refreshDokTabel();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi DlgTTE prosesSinyalWebhook done : " + ex);
                }
            }
        }.execute();
    }

    @Override public void dispose() {
        if (timerSinyal != null) {
            timerSinyal.stop();
        }
        tutupPopupQr();
        super.dispose();
    }

    private long nowSec() {
        return OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond();
    }

    private String ringkas(String s) {
        if (s == null) {
            return "";
        }
        return s.length() <= 12 ? s : s.substring(0, 8) + "...";
    }

    private String pesan(Throwable ex) {
        Throwable c = (ex.getCause() != null) ? ex.getCause() : ex;
        String m = c.getMessage();
        return (m == null || m.equals("")) ? c.toString() : m;
    }

    private String nz(String s) {
        return s == null ? "" : s;
    }

    // ================== Flow 5 : Verifikasi Tanda Tangan Elektronik ==================

    /**
     * Ambil Provenance signed berkas terpilih lalu tampilkan panel verifikasi.
     * Sumber id Provenance: {@code d.idProvenance} (disimpan saat placeholder dibuat);
     * bila kosong, ditelusuri dari Task.input (sesuai Flow 5: Task.output/input -> Provenance).
     */
    private void verifikasiDokumen(final DokumenTte d) {
        final JDialog tunggu = new JDialog(this, "Verifikasi", false);
        tunggu.add(label("  Mengambil data tanda tangan dari SATUSEHAT...", plainFont()));
        tunggu.pack();
        tunggu.setLocationRelativeTo(this);
        tunggu.setVisible(true);
        new SwingWorker<Object[], Void>() {
            @Override protected Object[] doInBackground() {
                // SUMBER UTAMA verifikasi: Provenance yang ditunjuk Task.output[code=signed], yaitu
                // d.idProvenance (dicatat saat penandatanganan). Provenance INILAH yang berisi signature/
                // JWS/device/GPS. JANGAN mengandalkan search-by-target lebih dulu: setelah TTE, target
                // Provenance terkunci ke referensi ber-versi ("Composition/{id}/_history/{v}") sehingga
                // search dengan referensi TANPA versi tak menjaringnya -- yang muncul malah placeholder
                // CREATE kosong, dan panel jadi "belum ada tanda tangan" padahal dokumen sudah TTE.
                String idProv = nz(d.idProvenance);
                if (idProv.equals("") && !nz(d.taskUuid).equals("")) {
                    idProv = idProvenanceDariTask(taskSender.bacaTask(d.taskUuid));
                }
                JsonNode prov = idProv.equals("") ? null : provSender.bacaProvenance(idProv);
                // Fallback: id tersimpan tak ada / belum bertanda-tangan (mis. dokumen lama sebelum
                // id_provenance dicatat, atau di-TTE ulang di luar alur ini) -> cari Provenance TERBARU
                // YANG BERTANDA-TANGAN via search (cariProvenanceTerbaru kini menyaring signed-only).
                if (prov == null || !provSender.sudahDitandatangani(prov)) {
                    String idAlt = provSender.cariProvenanceTerbaru(d.targetRef);
                    if (!idAlt.equals("")) {
                        idProv = idAlt;
                        prov = provSender.bacaProvenance(idProv);
                    }
                }
                SatuSehatSignatureVerifier.Hasil h = verifier.parse(prov);
                BufferedImage peta = h.adaKoordinat() ? unduhPetaStatis(h.lat, h.lon) : null;
                // versionId: (a) simpan versi yang DITANDATANGANI ke DB (audit); (b) ambil versi dokumen
                // TERKINI dari server & catat juga -> jejak perubahan. Status TTE TIDAK diubah (tetap "Sudah TTE").
                String versiSekarang = "";
                if (!nz(h.versiDitandatangani).equals("")) {
                    store.updateVersionByProvenance(idProv, h.versiDitandatangani);
                    versiSekarang = provSender.bacaVersiTerkini(h.targetRefBersih);
                    if (!versiSekarang.equals("")) {
                        store.updateVersionTerkiniByProvenance(idProv, versiSekarang);
                    }
                }
                return new Object[]{prov, h, peta, idProv, versiSekarang};
            }
            @Override protected void done() {
                tunggu.dispose();
                try {
                    Object[] r = get();
                    JsonNode prov = (JsonNode) r[0];
                    SatuSehatSignatureVerifier.Hasil h = (SatuSehatSignatureVerifier.Hasil) r[1];
                    BufferedImage peta = (BufferedImage) r[2];
                    String versiSekarang = (String) r[4];
                    if (prov == null) {
                        JOptionPane.showMessageDialog(SatuSehatBridgingTTE.this,
                                "Provenance berkas ini tidak dapat diambil dari SATUSEHAT.\n"
                                + "Pastikan berkas benar-benar sudah ditandatangani.");
                        return;
                    }
                    tampilkanVerifikasi(d, prov, h, peta, versiSekarang);
                } catch (Exception ex) {
                    System.out.println("Notifikasi DlgTTE verifikasiDokumen : " + ex);
                    JOptionPane.showMessageDialog(SatuSehatBridgingTTE.this,
                            "Gagal memuat verifikasi:\n" + pesan(ex));
                }
            }
        }.execute();
    }

    /** Telusuri referensi Provenance/xxx dari Task.output lalu Task.input. "" bila tak ada. */
    private String idProvenanceDariTask(JsonNode task) {
        if (task == null) {
            return "";
        }
        for (String bagian : new String[]{"output", "input"}) {
            JsonNode arr = task.path(bagian);
            if (arr.isArray()) {
                for (JsonNode el : arr) {
                    String ref = el.path("valueReference").path("reference").asText();
                    if (ref == null) {
                        ref = "";
                    }
                    if (ref.startsWith("Provenance/")) {
                        return ref.substring("Provenance/".length());
                    }
                }
            }
        }
        return "";
    }

    /** Bangun & tampilkan dialog panel verifikasi (KIRI: rincian, KANAN: peta lokasi). */
    private void tampilkanVerifikasi(DokumenTte d, JsonNode prov,
                                     SatuSehatSignatureVerifier.Hasil h, BufferedImage peta,
                                     String versiSekarang) {
        // Dokumen berubah setelah TTE? Bandingkan versi yang DITANDATANGANI vs versi dokumen TERKINI di server.
        final boolean versiBerubah = !nz(h.versiDitandatangani).equals("")
                && !nz(versiSekarang).equals("")
                && !nz(versiSekarang).equals(nz(h.versiDitandatangani));
        Font plain = plainFont();
        Font tebalKecil = new Font("SansSerif", Font.BOLD, 12);
        JDialog dlg = new JDialog(this, "Verifikasi Tanda Tangan — " + d.jenis, true);
        dlg.setLayout(new BorderLayout(10, 10));

        // Header status.
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JLabel status = new JLabel(h.ditandatangani
                ? "✔  Tanda tangan elektronik ditemukan (" + h.jumlahSignature + " signature)"
                : "⚠  Belum ada tanda tangan pada Provenance ini");
        status.setFont(new Font("SansSerif", Font.BOLD, 14));
        status.setForeground(h.ditandatangani ? HIJAU : ORANYE);
        header.add(status);
        JPanel utara = new JPanel(new BorderLayout());
        utara.add(header, BorderLayout.NORTH);
        if (versiBerubah) {
            // Catatan informatif (BUKAN peringatan gagal): berkas TETAP "Sudah TTE". Tanda tangan tetap sah
            // untuk versi yang ditandatangani; perubahan versi hanya DICATAT (version_id_terkini di DB).
            JLabel peringatan = new JLabel("<html>ℹ  <b>Notifikasi:</b> dokumen sudah berubah (ada versi lebih baru) "
                    + "setelah tanda tangan ini dibuat. Yang ditampilkan di sini adalah TTE terbaru; berkas tetap tercatat Sudah TTE.</html>");
            peringatan.setFont(plain);
            peringatan.setForeground(new java.awt.Color(0x8A, 0x5A, 0x12)); // oranye-tua informatif, bukan merah
            peringatan.setBorder(BorderFactory.createEmptyBorder(0, 14, 6, 10));
            utara.add(peringatan, BorderLayout.CENTER);
        }
        dlg.add(utara, BorderLayout.NORTH);

        // KIRI : rincian berlabel.
        JPanel kiri = new JPanel(new GridBagLayout());
        kiri.setBorder(BorderFactory.createEmptyBorder(4, 12, 8, 8));
        int[] baris = {0};
        barisVerif(kiri, baris, tebalKecil, plain, "Berkas", d.jenis + "  —  " + d.display);
        barisVerif(kiri, baris, tebalKecil, plain, "Aktivitas", teksAtau(h.aktivitas, "-"));
        if (!nz(h.versiDitandatangani).equals("")) {
            barisVerif(kiri, baris, tebalKecil, plain, "Versi ditandatangani",
                    ringkas(h.versiDitandatangani)
                    + (nz(h.versiWaktu).equals("") ? "" : "   (" + h.versiWaktu + ")"));
            if (versiBerubah) {
                barisVerif(kiri, baris, tebalKecil, plain, "Versi dokumen kini",
                        ringkas(versiSekarang) + "   (berbeda — dokumen diperbarui setelah TTE, tercatat di DB)");
            }
        }
        barisVerif(kiri, baris, tebalKecil, plain, "Ditandatangani oleh", teksAtau(h.penandatangan, d.signer));
        barisVerif(kiri, baris, tebalKecil, plain, "Tanggal tanda tangan", teksAtau(h.tglTandatangan, "-"));
        barisVerif(kiri, baris, tebalKecil, plain, "Tipe tanda tangan", teksAtau(h.tipeTandatangan, "-"));
        if (h.tandaTangan.size() > 1) {
            barisVerif(kiri, baris, tebalKecil, plain, "Semua tanda tangan", String.join("  ;  ", h.tandaTangan));
        }
        barisVerif(kiri, baris, tebalKecil, plain, "Format", teksAtau(h.sigFormat, "-"));
        barisVerif(kiri, baris, tebalKecil, plain, "Perangkat", teksAtau(h.perangkat, "-"));
        barisVerif(kiri, baris, tebalKecil, plain, "Lokasi",
                teksAtau(h.lokasiNama, "") + (h.adaKoordinat() ? "  (" + h.lat + ", " + h.lon + ")" : ""));
        if (!nz(h.penyediaStempel).equals("") || !nz(h.tglStempel).equals("")) {
            barisVerif(kiri, baris, tebalKecil, plain, "Stempel waktu",
                    teksAtau(h.penyediaStempel, "-") + (h.tglStempel.equals("") ? "" : "  " + h.tglStempel));
        }
        // JWT / JOSE.
        JLabel lJwt = new JLabel("JWT Signature (JOSE)");
        lJwt.setFont(tebalKecil);
        GridBagConstraints gj = new GridBagConstraints();
        gj.gridx = 0; gj.gridy = baris[0]++; gj.gridwidth = 2; gj.anchor = GridBagConstraints.WEST;
        gj.insets = new Insets(8, 0, 2, 0);
        kiri.add(lJwt, gj);
        JTextArea taJwt = new JTextArea(teksJose(h), 7, 34);
        taJwt.setFont(new Font("Monospaced", Font.PLAIN, 11));
        taJwt.setEditable(false);
        taJwt.setLineWrap(true);
        taJwt.setWrapStyleWord(false);
        GridBagConstraints gja = new GridBagConstraints();
        gja.gridx = 0; gja.gridy = baris[0]++; gja.gridwidth = 2;
        gja.fill = GridBagConstraints.BOTH; gja.weightx = 1; gja.weighty = 1;
        kiri.add(new JScrollPane(taJwt), gja);

        // KANAN : peta lokasi penandatanganan.
        JPanel kanan = new JPanel(new BorderLayout(4, 4));
        kanan.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 12));
        JLabel judulPeta = new JLabel("Lokasi Penandatanganan");
        judulPeta.setFont(tebalKecil);
        kanan.add(judulPeta, BorderLayout.NORTH);
        JLabel gambarPeta = new JLabel("", SwingConstants.CENTER);
        gambarPeta.setPreferredSize(new Dimension(320, 210));
        gambarPeta.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        if (peta != null) {
            gambarPeta.setIcon(new ImageIcon(peta));
        } else if (h.adaKoordinat()) {
            gambarPeta.setText("<html><div style='text-align:center'>Peta tidak dapat dimuat<br>"
                    + h.lat + ", " + h.lon + "</div></html>");
        } else {
            gambarPeta.setText("Koordinat lokasi tidak tersedia");
        }
        gambarPeta.setFont(plain);
        kanan.add(gambarPeta, BorderLayout.CENTER);
        JButton bukaPeta = new JButton("Buka di Peta");
        bukaPeta.setFont(plain);
        bukaPeta.setEnabled(h.adaKoordinat());
        bukaPeta.addActionListener(e -> bukaBrowser(
                "https://www.openstreetmap.org/?mlat=" + h.lat + "&mlon=" + h.lon + "#map=17/" + h.lat + "/" + h.lon));
        JPanel petaBawah = new JPanel(new FlowLayout(FlowLayout.CENTER));
        petaBawah.add(bukaPeta);
        kanan.add(petaBawah, BorderLayout.SOUTH);

        JSplitPane isi = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(kiri), kanan);
        isi.setResizeWeight(0.62);
        isi.setDividerLocation(430);
        dlg.add(isi, BorderLayout.CENTER);

        // Bawah : Lihat Provenance JSON + Tutup.
        JPanel bawah = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        JButton lihatJson = new JButton("Lihat Provenance JSON");
        lihatJson.setFont(plain);
        lihatJson.addActionListener(e -> tampilkanJson(dlg, prov));
        JButton tutup = new JButton("Tutup");
        tutup.setFont(plain);
        tutup.addActionListener(e -> dlg.dispose());
        JLabel catatan = new JLabel(
                "Validitas kriptografis dijamin BSrE saat penandatanganan; panel ini menampilkan isinya.");
        catatan.setFont(new Font("SansSerif", Font.ITALIC, 11));
        catatan.setForeground(new Color(120, 120, 120));
        bawah.add(catatan);
        bawah.add(lihatJson);
        bawah.add(tutup);
        dlg.add(bawah, BorderLayout.SOUTH);

        dlg.setSize(820, 560);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    /** Satu baris "Label : nilai" pada GridBag rincian verifikasi. */
    private void barisVerif(JPanel panel, int[] baris, Font fLabel, Font fNilai, String label, String nilai) {
        GridBagConstraints gl = new GridBagConstraints();
        gl.gridx = 0; gl.gridy = baris[0]; gl.anchor = GridBagConstraints.NORTHWEST;
        gl.insets = new Insets(3, 0, 3, 10);
        JLabel l = new JLabel(label);
        l.setFont(fLabel);
        panel.add(l, gl);
        GridBagConstraints gn = new GridBagConstraints();
        gn.gridx = 1; gn.gridy = baris[0]; gn.anchor = GridBagConstraints.NORTHWEST;
        gn.fill = GridBagConstraints.HORIZONTAL; gn.weightx = 1;
        gn.insets = new Insets(3, 0, 3, 0);
        JLabel v = new JLabel("<html><body style='width:220px'>" + escHtml(teksAtau(nilai, "-")) + "</body></html>");
        v.setFont(fNilai);
        panel.add(v, gn);
        baris[0]++;
    }

    /** Gabungan header + payload JOSE terbaca; fallback ke data base64 mentah. */
    private String teksJose(SatuSehatSignatureVerifier.Hasil h) {
        StringBuilder sb = new StringBuilder();
        if (!nz(h.joseHeader).equals("")) {
            sb.append("== JOSE Header ==\n").append(h.joseHeader).append("\n\n");
        }
        if (!nz(h.josePayload).equals("")) {
            sb.append("== JOSE Payload ==\n").append(h.josePayload).append("\n\n");
        }
        if (!nz(h.jwt).equals("")) {
            sb.append("== data (base64) ==\n").append(h.jwt);
        }
        return sb.length() == 0 ? "(tidak ada data tanda tangan)" : sb.toString();
    }

    /** Popup raw Provenance JSON (pretty). */
    private void tampilkanJson(JDialog induk, JsonNode prov) {
        String teks;
        try {
            teks = new com.fasterxml.jackson.databind.ObjectMapper()
                    .writerWithDefaultPrettyPrinter().writeValueAsString(prov);
        } catch (Exception e) {
            teks = String.valueOf(prov);
        }
        JTextArea ta = new JTextArea(teks, 26, 70);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 11));
        ta.setEditable(false);
        ta.setCaretPosition(0);
        JDialog dlg = new JDialog(induk, "Provenance JSON", true);
        dlg.add(new JScrollPane(ta));
        dlg.pack();
        dlg.setLocationRelativeTo(induk);
        dlg.setVisible(true);
    }

    /**
     * Unduh peta statis OpenStreetMap (marker di lokasi) sebagai gambar. Jaringan di
     * SwingWorker pemanggil. Kembalikan null bila gagal (panel jatuh ke teks koordinat).
     */
    private BufferedImage unduhPetaStatis(String lat, String lon) {
        try {
            String url = "https://staticmap.openstreetmap.de/staticmap.php?center=" + lat + "," + lon
                    + "&zoom=16&size=320x210&maptype=mapnik&markers=" + lat + "," + lon + ",red-pushpin";
            java.net.HttpURLConnection c = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
            c.setConnectTimeout(6000);
            c.setReadTimeout(6000);
            c.setRequestProperty("User-Agent", "SIMRSKhanza-TTE/1.0");
            c.connect();
            if (c.getResponseCode() != 200) {
                return null;
            }
            try (java.io.InputStream in = c.getInputStream()) {
                return javax.imageio.ImageIO.read(in);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi DlgTTE unduhPetaStatis : " + e);
            return null;
        }
    }

    private void bukaBrowser(String url) {
        try {
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
            } else {
                JOptionPane.showMessageDialog(this, url);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Buka manual:\n" + url);
        }
    }

    private Font plainFont() {
        return new Font("SansSerif", Font.PLAIN, 12);
    }

    private String teksAtau(String s, String bila) {
        return (s == null || s.trim().equals("")) ? bila : s;
    }

    private String escHtml(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    // ====================== TTE LANGSUNG DARI FORM RME ======================
    // Dipakai tombol "TTE" di form-form rekammedis.RM*: satu klik -> QR untuk SATU jenis berkas
    // kunjungan itu, tanpa membuka dialog ini. Sengaja static & mandiri: TIDAK ada satu pun
    // alur dialog di atas yang berubah.

    /**
     * Terbitkan QR TTE untuk satu jenis berkas kunjungan, lalu tampilkan popup QR-nya.
     *
     * Yang dikerjakan berurutan (bagian jaringan di thread pekerja):
     * <ol>
     *   <li>Rakit berkas kunjungan, saring yang jenisnya cocok. Kosong = Composition-nya belum ada
     *       di SATUSEHAT -&gt; suruh tekan tombol "Satu Sehat" dulu.</li>
     *   <li>Muat status tiap baris penanda dari {@code satu_sehat_provenance}.</li>
     *   <li>Hitung giliran (model serial): baris yang pendahulunya belum tanda tangan DIKUNCI.</li>
     *   <li>Ambil giliran aktif — urutan terkecil yang belum ditandatangani dan tidak terkunci —
     *       lalu batasi ke SATU penanda, karena satu QR dipindai satu orang.</li>
     *   <li>Sudah punya Task hidup -&gt; buka lagi QR-nya (jangan buat Task kedua, RuleNumber 20002).
     *       Ditolak tapi Provenance masih ada -&gt; cukup Task baru (reuse). Belum ada apa-apa -&gt;
     *       Bundle atomik untuk penanda tunggal, atau SignatureFlow untuk model paralel/serial.</li>
     * </ol>
     *
     * Catatan penting untuk dokumen model SERIAL (mis. Resume Medis Rawat Inap = author lalu
     * verifier): satu klik menerbitkan QR untuk GILIRAN YANG SEDANG BERJALAN saja. Setelah penanda
     * pertama selesai, tombol yang sama akan menerbitkan QR penanda berikutnya.
     *
     * @param pemilik komponen pemanggil (form RME); induk dialog & pemilik kursor tunggu
     * @param noRawat kunjungan yang sedang dibuka
     * @param jenis   nama jenis berkas persis seperti yang dipakai perakit, mis. "Laporan EKG"
     */
    public static void tteLangsung(final Component pemilik, final String noRawat, final String jenis) {
        // Jalur TTE langsung dari tombol "TTE" di form RME. Satu penjaga di sini menutup seluruh
        // form RME pemanggil sekaligus, sehingga tak perlu memeriksa hak akses di tiap form.
        if (!akses.getsatu_sehat_tanda_tangan_elektronik()) {
            JOptionPane.showMessageDialog(pemilik,
                    "Maaf, Anda tidak punya hak akses untuk tanda tangan elektronik...!!!!");
            return;
        }
        final String norwt = nzt(noRawat).trim();
        if (norwt.equals("")) {
            JOptionPane.showMessageDialog(pemilik, "No.Rawat belum terisi. Pilih dulu kunjungan pasiennya.");
            return;
        }
        pemilik.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
        new SwingWorker<SiapQr, Void>() {
            @Override protected SiapQr doInBackground() {
                return siapkanTte(norwt, jenis);
            }
            @Override protected void done() {
                pemilik.setCursor(java.awt.Cursor.getDefaultCursor());
                SiapQr s;
                try {
                    s = get();
                } catch (Exception ex) {
                    System.out.println("Notifikasi TTE langsung : " + ex);
                    JOptionPane.showMessageDialog(pemilik, "Gagal menyiapkan TTE :\n" + ex);
                    return;
                }
                if (!s.berhasil) {
                    JOptionPane.showMessageDialog(pemilik, s.pesan);
                    return;
                }
                new PopupQrCepat(pemilik, jenis, s.namaPenanda, s.peran, s.taskUuid).setVisible(true);
            }
        }.execute();
    }

    /** Hasil penyiapan TTE langsung: Task siap di-QR, atau alasan kenapa tidak bisa. */
    private static final class SiapQr {
        boolean berhasil = false;
        String pesan = "";
        String taskUuid = "";
        String namaPenanda = "";
        String peran = "author";
    }

    /** Seluruh keputusan TTE langsung; dijalankan di thread pekerja, tanpa menyentuh Swing. */
    private static SiapQr siapkanTte(String noRawat, String jenis) {
        SiapQr out = new SiapQr();
        SatuSehatProvenanceStore store = new SatuSehatProvenanceStore();

        SatuSehatSignatureAssembler.Hasil h = new SatuSehatSignatureAssembler().rakit(noRawat);
        List<DokumenTte> baris = new ArrayList<>();
        for (DokumenTte d : h.dokumen) {
            if (nzt(d.jenis).equalsIgnoreCase(nzt(jenis))) {
                baris.add(d);
            }
        }
        if (baris.isEmpty()) {
            out.pesan = "Berkas \"" + jenis + "\" belum ada di SATUSEHAT untuk No.Rawat " + noRawat
                    + ".\nTekan tombol \"Satu Sehat\" dulu untuk mengirimkannya, baru TTE.";
            return out;
        }
        if (nzt(h.idEncounter).equals("")) {
            out.pesan = "Encounter kunjungan ini belum ada di SATUSEHAT. TTE dibatalkan.";
            return out;
        }

        // Status per (dokumen, PENANDA) — memakai kunci tanpa penanda akan menyalin status orang lain.
        for (DokumenTte d : baris) {
            try {
                SatuSehatProvenanceStore.Baris b = store.ambil(noRawat, d.targetRef, d.signerIhs);
                if (b != null) {
                    d.idProvenance = b.idProvenance;
                    d.taskUuid = b.taskUuid;
                    d.idProvenanceSebelumnya = b.idProvenanceSebelumnya;
                    d.status = SatuSehatSignatureState.uiState(b.taskUuid, statusDb(b.status), false);
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi TTE langsung muatStatus : " + ex);
            }
        }
        kunciGiliran(baris);

        List<DokumenTte> siap = new ArrayList<>();
        for (DokumenTte d : baris) {
            if (d.status != Status.SUDAH && !d.terkunci) {
                siap.add(d);
            }
        }
        if (siap.isEmpty()) {
            String terkunci = "";
            for (DokumenTte d : baris) {
                if (d.terkunci) {
                    terkunci = d.alasanKunci;
                }
            }
            out.pesan = terkunci.equals("")
                    ? "\"" + jenis + "\" sudah ditandatangani seluruhnya."
                    : "Belum giliran Anda: " + terkunci + ".";
            return out;
        }

        // Satu QR dipindai satu orang -> pertahankan hanya baris milik penanda giliran terdepan.
        DokumenTte depan = siap.get(0);
        for (DokumenTte d : siap) {
            if (d.urutan < depan.urutan) {
                depan = d;
            }
        }
        final String idPenanda = nzt(depan.signerIhs);
        final String namaPenanda = nzt(depan.signer);
        if (idPenanda.equals("")) {
            out.pesan = "Practitioner IHS untuk penanda \"" + teks(namaPenanda, "(tanpa nama)")
                    + "\" belum ter-mapping. TTE dibatalkan.";
            return out;
        }
        List<DokumenTte> grup = new ArrayList<>();
        for (DokumenTte d : siap) {
            if (nzt(d.signerIhs).equals(idPenanda)) {
                grup.add(d);
            }
        }
        out.namaPenanda = namaPenanda;
        out.peran = nzt(depan.peran);

        // Sudah pernah dibuatkan Task dan belum ditolak -> tampilkan lagi QR-nya, jangan buat kedua.
        for (DokumenTte d : grup) {
            if (!nzt(d.taskUuid).equals("") && d.status != Status.DITOLAK) {
                out.berhasil = true;
                out.taskUuid = d.taskUuid;
                return out;
            }
        }

        try {
            List<DokumenTte> reuse = new ArrayList<>();
            List<DokumenTte> baru = new ArrayList<>();
            for (DokumenTte d : grup) {
                if (d.status == Status.DITOLAK && !nzt(d.idProvenance).equals("")) {
                    reuse.add(d);
                } else {
                    d.taskUuid = "";
                    if (d.status == Status.DITOLAK) {
                        d.idProvenance = "";
                    }
                    baru.add(d);
                }
            }
            // Kelompok reuse dan baru tak boleh dicampur dalam satu Task; dahulukan reuse.
            String uuid;
            if (!reuse.isEmpty()) {
                List<String> provIds = new ArrayList<>();
                for (DokumenTte d : reuse) {
                    provIds.add(d.idProvenance);
                }
                uuid = new SatuSehatTask().buatTask(idPenanda, namaPenanda, h.idEncounter, provIds);
                if (nzt(uuid).equals("")) {
                    out.pesan = "Task terkirim tetapi id-nya tidak terbaca dari respons server.";
                    return out;
                }
                for (DokumenTte d : reuse) {
                    store.simpan(noRawat, d.jenis, d.targetRef, d.idProvenance, uuid, "requested",
                            idPenanda, d.peran, d.urutan, d.idProvenanceSebelumnya);
                }
            } else if (multiPenanda(baru)) {
                // Model paralel/serial: Provenance dipakai bersama atau butuh id pendahulunya,
                // dua-duanya tak bisa dinyatakan dalam satu Bundle transaction.
                SatuSehatSignatureFlow.Hasil hf = new SatuSehatSignatureFlow().kirim(
                        noRawat, baru, petaPenandaDari(h, baru), idPenanda, namaPenanda,
                        h.idEncounter, false, new SatuSehatSignatureFlow.Log() {
                            @Override public void info(String pesan) {
                                System.out.println("TTE langsung : " + pesan);
                            }
                        });
                if (!hf.berhasil) {
                    out.pesan = hf.pesan;
                    return out;
                }
                uuid = hf.taskUuid;
            } else {
                // Penanda tunggal: Bundle transaction supaya Provenance + Task dibuat ATOMIK,
                // tak ada Provenance yatim bila Task gagal.
                List<SatuSehatBundleProvenance.Target> targets = new ArrayList<>();
                for (DokumenTte d : baru) {
                    targets.add(new SatuSehatBundleProvenance.Target(
                            d.targetRef, d.display, d.mulaiUtc, d.selesaiUtc));
                }
                SatuSehatBundleProvenance.HasilBundle hb =
                        new SatuSehatBundleProvenance().kirimBundle(targets, idPenanda, namaPenanda, h.idEncounter);
                uuid = hb.taskId;
                if (nzt(uuid).equals("")) {
                    out.pesan = "Bundle TTE terkirim tetapi id Task tidak terbaca dari respons server.";
                    return out;
                }
                for (int i = 0; i < baru.size(); i++) {
                    DokumenTte d = baru.get(i);
                    d.idProvenance = (i < hb.provIds.size()) ? hb.provIds.get(i) : "";
                    store.simpan(noRawat, d.jenis, d.targetRef, d.idProvenance, uuid, "requested",
                            idPenanda, d.peran, d.urutan, d.idProvenanceSebelumnya);
                }
            }
            out.berhasil = true;
            out.taskUuid = uuid;
        } catch (Exception ex) {
            System.out.println("Notifikasi TTE langsung buat : " + ex);
            out.pesan = "Gagal membuat TTE :\n" + ex;
        }
        return out;
    }

    /** true bila ada baris yang bukan model SINGLE (paralel/serial butuh jalur SignatureFlow). */
    private static boolean multiPenanda(List<DokumenTte> baris) {
        for (DokumenTte d : baris) {
            if (d.model != SatuSehatTteModel.Model.SINGLE) {
                return true;
            }
        }
        return false;
    }

    /** targetRef -> SELURUH penanda dokumen itu; dibutuhkan model paralel (satu Provenance bersama). */
    private static Map<String, List<SatuSehatProvenance.Penanda>> petaPenandaDari(
            SatuSehatSignatureAssembler.Hasil h, List<DokumenTte> baru) {
        Map<String, List<SatuSehatProvenance.Penanda>> peta = new java.util.LinkedHashMap<>();
        for (DokumenTte d : baru) {
            if (peta.containsKey(d.targetRef)) {
                continue;
            }
            List<SatuSehatProvenance.Penanda> semua = new ArrayList<>();
            for (DokumenTte lain : h.dokumen) {
                if (lain.targetRef.equals(d.targetRef) && !nzt(lain.signerIhs).equals("")) {
                    semua.add(new SatuSehatProvenance.Penanda(lain.signerIhs, lain.signer, lain.peran));
                }
            }
            peta.put(d.targetRef, semua);
        }
        return peta;
    }

    /**
     * Kunci baris model serial yang pendahulunya belum ditandatangani — Provenance giliran ke-n
     * WAJIB menunjuk Provenance giliran ke-(n-1), jadi giliran tak boleh dilompati.
     * Cerminan {@link #hitungKunciGiliran()} untuk daftar baris lepas.
     */
    private static void kunciGiliran(List<DokumenTte> baris) {
        for (DokumenTte d : baris) {
            d.terkunci = false;
            d.alasanKunci = "";
            if (d.model != SatuSehatTteModel.Model.SERIAL || d.urutan <= 1 || d.status == Status.SUDAH) {
                continue;
            }
            for (DokumenTte lain : baris) {
                if (!lain.targetRef.equals(d.targetRef) || lain.urutan >= d.urutan) {
                    continue;
                }
                if (lain.status != Status.SUDAH) {
                    d.terkunci = true;
                    d.alasanKunci = "menunggu "
                            + (nzt(lain.signer).equals("") ? "giliran " + lain.urutan : lain.signer);
                }
            }
        }
    }

    /** Samakan enum status di DB dengan yang dipahami SatuSehatSignatureState. */
    private static String statusDb(String statusDb) {
        String s = nzt(statusDb).toLowerCase();
        if (s.equals("signed") || s.equals("completed")) {
            return "completed";
        }
        if (s.equals("rejected") || s.equals("failed")) {
            return "rejected";
        }
        if (s.equals("requested") || s.equals("in-progress")) {
            return s;
        }
        return "";
    }

    private static String nzt(String s) {
        return s == null ? "" : s;
    }

    // ---------------------- STATUS & PREVIEW UNTUK PANEL FORM RME ----------------------

    /** Ringkasan status TTE satu jenis berkas pada satu kunjungan. */
    public static final class StatusTte {
        /** Teks siap tampil: Belum TTE / Proses TTE / Sudah TTE / Sudah TTE ada Perubahan / TTE Ditolak. */
        public String label = "Belum TTE";
        /** true bila berkas ini sudah pernah dikirim ke alur TTE (ada Task/Provenance). */
        public boolean adaTte = false;
    }

    /**
     * Baca status TTE dari {@code satu_sehat_provenance} — murni lokal, tanpa menyentuh jaringan,
     * jadi aman dipanggil tiap kali baris tabel dipilih.
     *
     * "Sudah TTE ada Perubahan" = versi dokumen yang DITANDATANGANI ({@code version_id}) berbeda
     * dengan versi terkini di server ({@code version_id_terkini}) — artinya isinya berubah setelah
     * ditandatangani. Kedua kolom itu baru terisi setelah verifikasi dijalankan, dan tombol
     * "Preview TTE" ({@link #previewTte}) itulah yang mengisinya.
     */
    public static StatusTte statusTteRME(String jenis, String noRawat) {
        StatusTte st = new StatusTte();
        String norwt = nzt(noRawat).trim();
        if (norwt.equals("") || nzt(jenis).equals("")) {
            return st;
        }
        int total = 0, selesai = 0, ditolak = 0;
        boolean berubah = false;
        try (PreparedStatement p = koneksiDB.condb().prepareStatement(
                "select ifnull(task_uuid,'') tu, status_tte, ifnull(version_id,'') v, "
                + "ifnull(version_id_terkini,'') vt "
                + "from satu_sehat_provenance where no_rawat=? and jenis_dokumen=?")) {
            p.setString(1, norwt);
            p.setString(2, jenis);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    total++;
                    if (!nzt(r.getString("tu")).equals("")) {
                        st.adaTte = true;
                    }
                    String s = nzt(r.getString("status_tte")).toLowerCase();
                    if (s.equals("completed")) {
                        selesai++;
                        String v = nzt(r.getString("v")), vt = nzt(r.getString("vt"));
                        if (!v.equals("") && !vt.equals("") && !v.equals(vt)) {
                            berubah = true;
                        }
                    } else if (s.equals("rejected")) {
                        ditolak++;
                    }
                }
            }
        } catch (Exception e) {
            // Tabel/kolom belum ada di instalasi lama -> anggap belum pernah di-TTE, jangan ganggu form.
            System.out.println("Notifikasi statusTteRME : " + e);
            return st;
        }
        if (total == 0) {
            st.label = "Belum TTE";
        } else if (ditolak > 0) {
            st.label = "TTE Ditolak";
        } else if (selesai == total) {
            st.label = berubah ? "Sudah TTE ada Perubahan" : "Sudah TTE";
        } else {
            st.label = "Proses TTE";
        }
        return st;
    }

    /** Satu baris tanda tangan yang tercatat lokal; dipakai previewTte untuk tahu apa yang diverifikasi. */
    private static final class BarisProv {
        String idProvenance = "", targetRef = "", taskUuid = "", peran = "author";
        int urutan = 1;
    }

    /** Baris provenance terakhir (urutan tertinggi) berkas ini; null bila belum ada sama sekali. */
    private static BarisProv barisProvTerakhir(String noRawat, String jenis) {
        BarisProv out = null;
        try (PreparedStatement p = koneksiDB.condb().prepareStatement(
                "select ifnull(id_provenance,'') ip, target_ref, ifnull(task_uuid,'') tu, "
                + "ifnull(peran,'author') pr, urutan "
                + "from satu_sehat_provenance where no_rawat=? and jenis_dokumen=? "
                + "order by urutan desc, updated_at desc limit 1")) {
            p.setString(1, nzt(noRawat).trim());
            p.setString(2, nzt(jenis));
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    out = new BarisProv();
                    out.idProvenance = nzt(r.getString("ip"));
                    out.targetRef = nzt(r.getString("target_ref"));
                    out.taskUuid = nzt(r.getString("tu"));
                    out.peran = nzt(r.getString("pr"));
                    out.urutan = r.getInt("urutan");
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi barisProvTerakhir : " + e);
        }
        return out;
    }

    /**
     * Tampilkan rincian tanda tangan satu berkas: siapa menandatangani, kapan, dengan perangkat apa,
     * stempel waktu BSrE, dan yang terpenting — apakah dokumennya berubah setelah ditandatangani.
     *
     * Sekaligus MEMUTAKHIRKAN {@code version_id} & {@code version_id_terkini} di
     * {@code satu_sehat_provenance}, sehingga label "Status TTE" di form ikut akurat sesudahnya.
     * Itu sebabnya tombol ini bukan sekadar hiasan: dialah yang menghidupkan deteksi perubahan.
     */
    public static void previewTte(final Component pemilik, final String noRawat, final String jenis) {
        final String norwt = nzt(noRawat).trim();
        if (norwt.equals("")) {
            JOptionPane.showMessageDialog(pemilik, "No.Rawat belum terisi. Pilih dulu kunjungan pasiennya.");
            return;
        }
        final BarisProv bp = barisProvTerakhir(norwt, jenis);
        if (bp == null || (bp.idProvenance.equals("") && bp.taskUuid.equals(""))) {
            JOptionPane.showMessageDialog(pemilik, "TTE Belum Dikirim.");
            return;
        }
        pemilik.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
        new SwingWorker<String, Void>() {
            @Override protected String doInBackground() {
                return rakitPreview(norwt, jenis, bp);
            }
            @Override protected void done() {
                pemilik.setCursor(java.awt.Cursor.getDefaultCursor());
                String teks;
                try {
                    teks = get();
                } catch (Exception ex) {
                    System.out.println("Notifikasi previewTte : " + ex);
                    JOptionPane.showMessageDialog(pemilik, "Gagal mengambil data tanda tangan :\n" + ex);
                    return;
                }
                JTextArea area = new JTextArea(teks, 18, 58);
                area.setEditable(false);
                area.setFont(new Font("Monospaced", Font.PLAIN, 12));
                area.setCaretPosition(0);
                JOptionPane.showMessageDialog(pemilik, new JScrollPane(area),
                        "Preview TTE - " + jenis, JOptionPane.PLAIN_MESSAGE);
            }
        }.execute();
    }

    /** Ambil Provenance dari SATUSEHAT, verifikasi, catat versi, lalu susun teksnya. */
    private static String rakitPreview(String noRawat, String jenis, BarisProv bp) {
        SatuSehatProvenance provSender = new SatuSehatProvenance();
        SatuSehatProvenanceStore store = new SatuSehatProvenanceStore();

        String idProv = bp.idProvenance;
        JsonNode prov = idProv.equals("") ? null : provSender.bacaProvenance(idProv);
        // Setelah TTE, target Provenance terkunci ke referensi ber-versi sehingga pencarian biasa
        // hanya menjaring placeholder kosong -> pakai pencari yang menyaring signed-only.
        if (prov == null || !provSender.sudahDitandatangani(prov)) {
            String idAlt = provSender.cariProvenanceTerbaru(bp.targetRef);
            if (!idAlt.equals("")) {
                idProv = idAlt;
                prov = provSender.bacaProvenance(idProv);
            }
        }
        if (prov == null) {
            return "Provenance berkas ini tidak dapat diambil dari SATUSEHAT.\n"
                    + "Pastikan berkas benar-benar sudah ditandatangani.\n\n"
                    + "Berkas   : " + jenis + "\n"
                    + "No.Rawat : " + noRawat + "\n"
                    + "Target   : " + teks(bp.targetRef, "-") + "\n"
                    + "Task     : " + teks(bp.taskUuid, "-");
        }

        SatuSehatSignatureVerifier.Hasil h = new SatuSehatSignatureVerifier().parse(prov);
        String versiSekarang = "";
        if (!nzt(h.versiDitandatangani).equals("")) {
            store.updateVersionByProvenance(idProv, h.versiDitandatangani);
            versiSekarang = provSender.bacaVersiTerkini(h.targetRefBersih);
            if (!versiSekarang.equals("")) {
                store.updateVersionTerkiniByProvenance(idProv, versiSekarang);
            }
        }

        String catatanVersi;
        if (nzt(h.versiDitandatangani).equals("") || versiSekarang.equals("")) {
            catatanVersi = "belum bisa dibandingkan";
        } else if (h.versiDitandatangani.equals(versiSekarang)) {
            catatanVersi = "SAMA - dokumen tidak berubah sejak ditandatangani";
        } else {
            catatanVersi = "BERBEDA - dokumen BERUBAH setelah ditandatangani, perlu TTE ulang";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Berkas            : ").append(jenis).append("\n");
        sb.append("No.Rawat          : ").append(noRawat).append("\n");
        sb.append("Peran penanda     : ").append(teks(bp.peran, "author"))
          .append(" (giliran ").append(bp.urutan).append(")\n");
        sb.append("Bertanda tangan   : ").append(h.ditandatangani ? "YA" : "BELUM")
          .append(" (").append(h.jumlahSignature).append(" signature)\n");
        sb.append("\n");
        sb.append("Ditandatangani oleh : ").append(teks(h.penandatangan, "-")).append("\n");
        sb.append("Referensi penanda   : ").append(teks(h.penandatanganRef, "-")).append("\n");
        sb.append("Waktu tanda tangan  : ").append(teks(h.tglTandatangan, "-")).append("\n");
        sb.append("Tipe tanda tangan   : ").append(teks(h.tipeTandatangan, "-")).append("\n");
        sb.append("Format              : ").append(teks(h.sigFormat, "-")).append("\n");
        sb.append("Aktivitas Provenance: ").append(teks(h.aktivitas, "-")).append("\n");
        sb.append("\n");
        sb.append("Stempel waktu       : ").append(teks(h.tglStempel, "-")).append("\n");
        sb.append("Penyedia stempel    : ").append(teks(h.penyediaStempel, "-")).append("\n");
        sb.append("Perangkat           : ").append(teks(h.perangkat, "-")).append("\n");
        sb.append("Lokasi              : ").append(teks(h.lokasiNama, "-"));
        if (h.adaKoordinat()) {
            sb.append(" (").append(h.lat).append(", ").append(h.lon).append(")");
        }
        sb.append("\n\n");
        sb.append("Versi ditandatangani: ").append(teks(h.versiDitandatangani, "-"));
        if (!nzt(h.versiWaktu).equals("")) {
            sb.append("  [").append(h.versiWaktu).append("]");
        }
        sb.append("\n");
        sb.append("Versi terkini server: ").append(teks(versiSekarang, "-")).append("\n");
        sb.append("Perbandingan versi  : ").append(catatanVersi).append("\n");
        sb.append("\n");
        sb.append("Provenance          : ").append(idProv).append("\n");
        sb.append("Target              : ").append(teks(h.targetRefBersih, bp.targetRef)).append("\n");
        sb.append("Task                : ").append(teks(bp.taskUuid, "-")).append("\n");
        if (!h.tandaTangan.isEmpty()) {
            sb.append("\nDaftar signature :\n");
            for (String s : h.tandaTangan) {
                sb.append("  - ").append(s).append("\n");
            }
        }
        return sb.toString();
    }

    private static String teks(String s, String bila) {
        return (s == null || s.trim().equals("")) ? bila : s;
    }

    /**
     * Popup QR ringkas untuk TTE langsung dari form RME.
     *
     * QR-nya berumur pendek ({@link SatuSehatSignatureState#TTL_QR_DETIK} detik) sehingga WAJIB
     * dirender ulang berkala — kalau tidak, dokter memindai kode kedaluwarsa.
     */
    private static final class PopupQrCepat extends JDialog {

        private final QrRenderer qr = QrRendererFactory.buat();
        private final String basisApplink;
        private final String taskUuid;
        private final JLabel gambar = new JLabel("", SwingConstants.CENTER);
        private final JLabel info = new JLabel("", SwingConstants.CENTER);
        private final JLabel statusLabel = new JLabel("Status : menunggu pindai", SwingConstants.CENTER);
        private final Timer timer;
        private long terbitDetik = 0L;

        PopupQrCepat(Component pemilik, String jenis, String namaPenanda, String peran, String taskUuid) {
            super(SwingUtilities.getWindowAncestor(pemilik), "TTE - " + jenis, ModalityType.APPLICATION_MODAL);
            this.taskUuid = nzt(taskUuid);
            this.basisApplink = nzt(koneksiDB.URLAPPLINKSATUSEHAT());

            Font plain = new Font("Tahoma", Font.PLAIN, 11);
            JPanel isi = new JPanel(new BorderLayout(6, 6));
            isi.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
            isi.setBackground(Color.WHITE);

            JLabel judul = new JLabel("<html><div style='text-align:center'>" + escStatik(jenis)
                    + "<br>Penanda : " + escStatik(teks(namaPenanda, "-"))
                    + " (" + escStatik(teks(peran, "author")) + ")</div></html>", SwingConstants.CENTER);
            judul.setFont(plain);
            isi.add(judul, BorderLayout.NORTH);

            gambar.setPreferredSize(new Dimension(QR_PX, QR_PX));
            isi.add(gambar, BorderLayout.CENTER);

            JPanel bawah = new JPanel(new java.awt.GridLayout(3, 1, 2, 2));
            bawah.setBackground(Color.WHITE);
            info.setFont(plain);
            statusLabel.setFont(plain);
            bawah.add(info);
            bawah.add(statusLabel);
            JPanel tombolBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
            tombolBar.setBackground(Color.WHITE);
            JButton cek = new JButton("Cek Status");
            cek.setFont(plain);
            cek.addActionListener(e -> cekStatus(cek));
            JButton tutup = new JButton("Tutup");
            tutup.setFont(plain);
            tutup.addActionListener(e -> dispose());
            tombolBar.add(cek);
            tombolBar.add(tutup);
            bawah.add(tombolBar);
            isi.add(bawah, BorderLayout.SOUTH);

            setContentPane(isi);
            pack();
            setLocationRelativeTo(pemilik);

            timer = new Timer(1000, e -> tick());
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override public void windowClosed(java.awt.event.WindowEvent e) {
                    timer.stop();   // tanpa ini timer terus hidup setelah popup ditutup
                }
            });
            tick();
            timer.start();
        }

        /** Render ulang QR begitu kedaluwarsa, plus hitung mundur. */
        private void tick() {
            long now = System.currentTimeMillis() / 1000L;
            if (terbitDetik == 0L || SatuSehatSignatureState.qrKedaluwarsa(terbitDetik, now)) {
                terbitDetik = now;
                try {
                    BufferedImage img = qr.render(
                            SatuSehatSignatureState.qrPayload(basisApplink, taskUuid, now), QR_PX);
                    gambar.setIcon(new ImageIcon(img));
                    gambar.setText("");
                } catch (Exception ex) {
                    gambar.setIcon(null);
                    gambar.setText("QR gagal dirender");
                    System.out.println("Notifikasi PopupQrCepat render : " + ex);
                }
            }
            if (qr.aktif()) {
                long sisa = Math.max(SatuSehatSignatureState.TTL_QR_DETIK - (now - terbitDetik), 0);
                info.setText("Kode diperbarui dalam " + sisa + " detik");
            } else {
                info.setText("QR belum aktif (pasang ZXing)");
            }
        }

        /** Tarik status Task dari SATUSEHAT; GET Task adalah sumber kebenaran, bukan DB lokal. */
        private void cekStatus(final JButton tombol) {
            tombol.setEnabled(false);
            statusLabel.setText("Status : memeriksa ...");
            new SwingWorker<String, Void>() {
                @Override protected String doInBackground() {
                    try {
                        SatuSehatTask t = new SatuSehatTask();
                        JsonNode task = t.bacaTask(taskUuid);
                        return (task == null) ? "" : nzt(t.status(task));
                    } catch (Exception ex) {
                        System.out.println("Notifikasi PopupQrCepat cekStatus : " + ex);
                        return "";
                    }
                }
                @Override protected void done() {
                    tombol.setEnabled(true);
                    String st;
                    try {
                        st = get();
                    } catch (Exception ex) {
                        st = "";
                    }
                    if (st.equals("")) {
                        statusLabel.setText("Status : belum terbaca dari SATUSEHAT");
                        return;
                    }
                    statusLabel.setText("Status : " + st);
                    if (st.equalsIgnoreCase("completed")) {
                        timer.stop();
                        JOptionPane.showMessageDialog(PopupQrCepat.this,
                                "Berkas sudah ditandatangani.");
                        dispose();
                    }
                }
            }.execute();
        }

        private static String escStatik(String s) {
            if (s == null) {
                return "";
            }
            return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.ComboBox cbBerkas;
    private widget.ComboBox cbDokterSign;
    private widget.ComboBox cbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel1;
    private widget.Label jLabel2;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label labelDok;
    private widget.Label labelJmlPasien;
    private widget.panelisi panelFilter;
    private javax.swing.JPanel panelFilterBerkas;
    private javax.swing.JPanel panelKanan;
    private javax.swing.JPanel panelKananAtas;
    private widget.panelisi panelTombol;
    private javax.swing.JMenuItem ppHapusSemua;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.ScrollPane spKanan;
    private widget.ScrollPane spKiri;
    private widget.ScrollPane spLog;
    private javax.swing.JSplitPane splitTabel;
    private javax.swing.JSplitPane splitUtama;
    private javax.swing.JTextArea taLog;
    private widget.Table tabelDok;
    private widget.Table tabelPasien;
    private widget.Tanggal tglDari;
    private widget.Tanggal tglSampai;
    private widget.Button tombolCari;
    private widget.Button tombolCariDokter;
    private widget.Button tombolSalinLog;
    private widget.Button tombolSemuaDokter;
    private widget.Button tombolTandatangani;
    private widget.Button tombolTutup;
    private widget.Button tombolUpdate;
    private widget.TextBox txtCari;
    private widget.TextBox txtFilterDokter;
    // End of variables declaration//GEN-END:variables

    /** Peluncur DEMO (butuh koneksi DB SIMRS). */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatBridgingTTE dlg = new SatuSehatBridgingTTE(new javax.swing.JFrame(), true);
            dlg.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dlg.setVisible(true);
        });
    }
}

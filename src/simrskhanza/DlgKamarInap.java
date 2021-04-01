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
package simrskhanza;

import bridging.BPJSDataSEP;
import bridging.BPJSNik;
import bridging.BPJSPeserta;
import bridging.CoronaPasien;
import bridging.DlgDataTB;
import bridging.DlgSKDPBPJS;
import bridging.INACBGPerawatanCorona;
import bridging.SisruteRujukanKeluar;
import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import informasi.InformasiAnalisaKamin;
import inventory.DlgInputStokPasien;
import inventory.DlgPeresepanDokter;
import inventory.DlgResepObat;
import inventory.DlgReturJual;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingRanap;
import keuangan.DlgKamar;
import keuangan.DlgLhtPiutang;
import keuangan.DlgPerkiraanBiayaRanap;
import laporan.DlgBerkasRawat;
import laporan.DlgDataHAIs;
import laporan.DlgDataInsidenKeselamatan;
import laporan.DlgDiagnosaPenyakit;
import permintaan.DlgBookingOperasi;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanRadiologi;
import rekammedis.RMDataAsuhanGizi;
import rekammedis.RMDataMonitoringAsuhanGizi;
import rekammedis.RMDataResumePasien;
import rekammedis.RMDetailRawat;
import rekammedis.RMRiwayatPerawatan;

/**
 *
 * @author perpustakaan
 */
public class DlgKamarInap extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabModeCoder;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    public DlgIKBBayi ikb = new DlgIKBBayi(null, false);
    public DlgKamar kamar = new DlgKamar(null, false);
    public DlgReg reg = new DlgReg(null, false);
    public DlgSpri spri = new DlgSpri(null, false);
    public DlgPasienMati pasienMeninggal = new DlgPasienMati(null, false);
    public DlgBilingRanap billing = new DlgBilingRanap(null, false);
    public DlgDiagnosaPenyakit diagnosa = new DlgDiagnosaPenyakit(null, false);
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date = new Date();
    private String now = dateFormat.format(date), kmr = "", key = "", tglmasuk, jammasuk, kd_pj, hariawal = "",
            pilihancetak = "", nonota = "", aktifkan_hapus_data_salah = "";
    private PreparedStatement ps, pssetjam, pscaripiutang, psdiagnosa, psibu, psanak, pstarif, psdpjp, pscariumur, ps_coder;
    private ResultSet rs, rs2, rssetjam, rs_coder;
    private int i, row = 0;
    private double lama = 0, persenbayi = 0;
    private String gabungkan = "", norawatgabung = "", kamaryangdigabung = "", dokterranap = "", bangsal = "",
            diagnosa_akhir = "", namakamar = "", umur = "0", sttsumur = "Th";
    private String validasicatatan = Sequel.cariIsi("select tampilkan_catatan from set_validasi_catatan");
    private Properties prop = new Properties();
    NumberFormat nf = NumberFormat.getNumberInstance();

    public DlgKamarInap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        DTPCari1.setDate(new Date());
        DTPCari2.setDate(new Date());
        DTPCari3.setDate(new Date());
        DTPCari4.setDate(new Date());

        tabModeCoder = new DefaultTableModel(null,
                new Object[]{"Tanggal", "Diagnosa", "Hak Kelas 1", "Hak Kelas 2", "Hak Kelas 3",
                    "Selisih", "Penjaminan", "id_coder"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tableCoder.setModel(tabModeCoder);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tableCoder.setPreferredScrollableViewportSize(new Dimension(500, 500));
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
//        tableCoder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tableCoder.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(105);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 1:
                    column.setPreferredWidth(180);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 2:
                    column.setPreferredWidth(80);
                    column.setCellRenderer(rightRenderer);
                    break;
                case 3:
                    column.setPreferredWidth(80);
                    column.setCellRenderer(rightRenderer);
                    break;
                case 4:
                    column.setPreferredWidth(80);
                    column.setCellRenderer(rightRenderer);
                    break;
                case 5:
                    column.setPreferredWidth(80);
                    column.setCellRenderer(rightRenderer);
                    break;
                case 6:
                    column.setPreferredWidth(80);
                    column.setCellRenderer(rightRenderer);
                    break;
                case 7:
                    column.setCellRenderer(leftRenderer);
                    break;
                default:
                    column.setCellRenderer(leftRenderer);
                    break;
            }
        }
        tableCoder.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode = new DefaultTableModel(null,
                new Object[]{"No.Rawat", "Nomer RM", "Nama Pasien", "Alamat Pasien", "Penanggung Jawab",
                    "Hubungan P.J.", "Jenis Bayar", "Kamar", "Tarif Kamar", "Diagnosa Awal", "Diagnosa Akhir",
                    "Tgl.Masuk", "Jam Masuk", "Tgl.Keluar", "Jam Keluar", "Ttl.Biaya", "Stts.Pulang", "Lama",
                    "Dokter P.J.", "Kamar", "Status Bayar", "Asal Unit"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }

            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbKamIn.setModel(tabMode);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKamIn.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamIn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 22; i++) {
            TableColumn column = tbKamIn.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(105);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 1:
                    column.setPreferredWidth(70);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 2:
                    column.setPreferredWidth(170);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 3:
                    column.setPreferredWidth(150);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 4:
                    column.setPreferredWidth(120);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 5:
                    column.setPreferredWidth(80);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 6:
                    column.setPreferredWidth(80);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 7:
                    column.setPreferredWidth(150);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 8:
                    column.setPreferredWidth(75);
                    column.setCellRenderer(rightRenderer);
                    break;
                case 9:
                    column.setPreferredWidth(90);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 10:
                    column.setPreferredWidth(90);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 11:
                    column.setPreferredWidth(70);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 12:
                    column.setPreferredWidth(60);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 13:
                    column.setPreferredWidth(70);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 14:
                    column.setPreferredWidth(60);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 15:
                    column.setPreferredWidth(80);
                    column.setCellRenderer(rightRenderer);
                    break;
                case 16:
                    column.setPreferredWidth(75);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 17:
                    column.setPreferredWidth(40);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 18:
                    column.setPreferredWidth(130);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 19:
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                    column.setCellRenderer(leftRenderer);
                    break;
                default:
                    column.setCellRenderer(leftRenderer);
                    break;
            }
        }
        tbKamIn.setDefaultRenderer(Object.class, new WarnaTable());

        norawat.setDocument(new batasInput((byte) 17).getKata(norawat));
        kdkamar.setDocument(new batasInput((byte) 15).getKata(kdkamar));
        kdkamarpindah.setDocument(new batasInput((byte) 15).getKata(kdkamarpindah));
        ttlbiaya.setDocument(new batasInput((byte) 25).getKata(ttlbiaya));
        diagnosaawal.setDocument(new batasInput((byte) 100).getKata(diagnosaawal));
        diagnosaakhir.setDocument(new batasInput((byte) 100).getKata(diagnosaakhir));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                        if (chkBaby.isSelected()) {
                            tampilBaby();
                        }
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                        if (chkBaby.isSelected()) {
                            tampilBaby();
                        }
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                        if (chkBaby.isSelected()) {
                            tampilBaby();
                        }
                    }
                }
            });
        }
        DlgCatatan.setSize(595, 34);
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            try {
                if (prop.getProperty("MENUTRANSPARAN").equals("yes")) {
                    DlgCatatan.setOpacity(0.90f);
                }
            } catch (Exception e) {
                System.out.println("Notif Tansparant : " + e.toString());

            }
        } catch (Exception ex) {
            System.out.println("Notif Load XML : " + ex);
        }

        TJmlHari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isjml();
            }
        });

        TTarif.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isjml();
            }
        });

        TTarifpindah.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isjml();
            }
        });

        Valid.LoadTahun(CmbTahun);
        Valid.LoadTahun(CmbTahunpindah);

        WindowInputKamar.setSize(675, 275);
        WindowInputKamar.setLocationRelativeTo(null);
        WindowPindahKamar.setSize(675, 285);
        WindowCaraBayar.setSize(630, 80);
        WindowDiagnosaAkhir.setSize(630, 80);
        WindowDiagnosaMasuk.setSize(630, 80);
        WindowRanapGabung.setSize(630, 120);

        CmbTahun.setSelectedItem(now.substring(0, 4));
        CmbBln.setSelectedItem(now.substring(5, 7));
        CmbTgl.setSelectedItem(now.substring(8, 10));
        cmbJam.setSelectedItem(now.substring(11, 13));
        cmbMnt.setSelectedItem(now.substring(14, 16));
        cmbDtk.setSelectedItem(now.substring(17, 19));

        reg.pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (reg.pasien.getTable().getSelectedRow() != -1) {
                        NoRmBayi.setText(
                                reg.pasien.getTable().getValueAt(reg.pasien.getTable().getSelectedRow(), 1).toString());
                        NmBayi.setText(
                                reg.pasien.getTable().getValueAt(reg.pasien.getTable().getSelectedRow(), 2).toString());
                    }
                    if (reg.pasien.getTable2().getSelectedRow() != -1) {
                        NoRmBayi.setText(reg.pasien.getTable2().getValueAt(reg.pasien.getTable2().getSelectedRow(), 1)
                                .toString());
                        NmBayi.setText(reg.pasien.getTable2().getValueAt(reg.pasien.getTable2().getSelectedRow(), 2)
                                .toString());
                    }
                    if (reg.pasien.getTable3().getSelectedRow() != -1) {
                        NoRmBayi.setText(reg.pasien.getTable3().getValueAt(reg.pasien.getTable3().getSelectedRow(), 1)
                                .toString());
                        NmBayi.setText(reg.pasien.getTable3().getValueAt(reg.pasien.getTable3().getSelectedRow(), 2)
                                .toString());
                    }
                    NoRmBayi.requestFocus();
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

        reg.pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        reg.pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        reg.pasien.getTable2().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        reg.pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        reg.pasien.getTable3().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        reg.pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        ikb.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (ikb.getTable().getSelectedRow() != -1) {
                        NoRmBayi.setText(ikb.getTable().getValueAt(ikb.getTable().getSelectedRow(), 0).toString());
                        NmBayi.setText(ikb.getTable().getValueAt(ikb.getTable().getSelectedRow(), 1).toString());
                    }
                    NoRmBayi.requestFocus();
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

        ikb.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        ikb.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        kamar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (kamar.getTable().getSelectedRow() != -1) {
                        kdkamar.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(), 1).toString());
                        kdkamarpindah
                                .setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(), 1).toString());
                        isKmr();
                        if ((WindowInputKamar.isVisible() == true) && (!TBangsal.getText().equals(""))
                                && (!norawat.getText().equals(""))) {
                            if (TIn.getText().equals("")) {
                                tglmasuk = CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-"
                                        + CmbTgl.getSelectedItem();
                                jammasuk = cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                        + cmbDtk.getSelectedItem();
                            } else {
                                tglmasuk = TIn.getText();
                                jammasuk = JamMasuk.getText();
                            }
                            if (hariawal.equals("Yes")) {
                                Sequel.cariIsi(
                                        "select (if(to_days('" + CmbTahun.getSelectedItem() + "-"
                                        + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " "
                                        + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                        + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk
                                        + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-"
                                        + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " "
                                        + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                        + cmbDtk.getSelectedItem() + "')-time_to_sec('" + tglmasuk + " "
                                        + jammasuk + "')>(3600*" + lama + "),1,0),to_days('"
                                        + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-"
                                        + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":"
                                        + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem()
                                        + "')-to_days('" + tglmasuk + " " + jammasuk + "'))+1) as lama",
                                        TJmlHari);
                            } else {
                                Sequel.cariIsi("select if(to_days('" + CmbTahun.getSelectedItem() + "-"
                                        + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " "
                                        + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                        + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk
                                        + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-"
                                        + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " "
                                        + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                        + cmbDtk.getSelectedItem() + "')-time_to_sec('" + tglmasuk + " " + jammasuk
                                        + "')>(3600*" + lama + "),1,0),to_days('" + CmbTahun.getSelectedItem() + "-"
                                        + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " "
                                        + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                                        + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk
                                        + "')) as lama", TJmlHari);
                            }
                        }
                    }
                    kdkamar.requestFocus();
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

        diagnosa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    try {
                        key = "";
                        psdiagnosa = koneksi.prepareStatement(
                                "select kd_penyakit from diagnosa_pasien where no_rawat=? order by prioritas asc");
                        try {
                            psdiagnosa.setString(1, norawat.getText());
                            rs = psdiagnosa.executeQuery();
                            while (rs.next()) {
                                key = rs.getString(1) + ", " + key;
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (psdiagnosa != null) {
                                psdiagnosa.close();
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }

                    if (WindowInputKamar.isVisible() == true) {
                        diagnosaakhir.setText(key);
                        diagnosaakhir.requestFocus();
                    } else if (WindowInputKamar.isVisible() == false) {
                        Sequel.mengedit("kamar_inap",
                                "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                                + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                                + "'",
                                "diagnosa_akhir='" + key + "'");
                        tampil();
                        if (chkBaby.isSelected()) {
                            tampilBaby();
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
                if (akses.getform().equals("DlgKamarInap")) {
                    try {
                        key = "";
                        psdiagnosa = koneksi.prepareStatement(
                                "select kd_penyakit from diagnosa_pasien where no_rawat=? order by prioritas asc");
                        try {
                            psdiagnosa.setString(1, norawat.getText());
                            rs = psdiagnosa.executeQuery();
                            while (rs.next()) {
                                key = rs.getString(1) + ", " + key;
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (psdiagnosa != null) {
                                psdiagnosa.close();
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }

                    if (WindowInputKamar.isVisible() == true) {
                        diagnosaakhir.setText(key);
                        diagnosaakhir.requestFocus();
                    } else if (WindowInputKamar.isVisible() == false) {
                        Sequel.mengedit("kamar_inap",
                                "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                                + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                                + "'",
                                "diagnosa_akhir='" + key + "'");
                        tampil();
                        if (chkBaby.isSelected()) {
                            tampilBaby();
                        }
                    }
                }
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        kamar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        kamar.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        kamar.bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (kamar.bangsal.getTable().getSelectedRow() != -1) {
                        BangsalCari.setText(kamar.bangsal.getTable()
                                .getValueAt(kamar.bangsal.getTable().getSelectedRow(), 1).toString());
                    }
                    BangsalCari.requestFocus();
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

        reg.pasien.penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (reg.pasien.penjab.getTable().getSelectedRow() != -1) {
                        kdpenjab.setText(reg.pasien.penjab.getTable()
                                .getValueAt(reg.pasien.penjab.getTable().getSelectedRow(), 1).toString());
                        nmpenjab.setText(reg.pasien.penjab.getTable()
                                .getValueAt(reg.pasien.penjab.getTable().getSelectedRow(), 2).toString());
                    }
                    kdpenjab.requestFocus();
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

        reg.pasien.penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        reg.pasien.penjab.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        billing.rawatinap.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    tbKamIn.requestFocus();
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

        reg.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                ;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (reg.dokter.getTable().getSelectedRow() != -1) {
                        CrDokter3.setText(
                                reg.dokter.getTable().getValueAt(reg.dokter.getTable().getSelectedRow(), 1).toString());
                        CrDokter3.requestFocus();
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

        reg.getButton().addActionListener((ActionEvent e) -> {
            if (akses.getform().equals("DlgKamarInap")) {
                norawat.setText(reg.getTextField().getText());
                Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, norawat.getText());
                Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
            }
        });

        try {
            pssetjam = koneksi.prepareStatement("select * from set_jam_minimal");
            try {
                rssetjam = pssetjam.executeQuery();
                while (rssetjam.next()) {
                    lama = rssetjam.getDouble("lamajam");
                    persenbayi = rssetjam.getDouble("bayi");
                    diagnosa_akhir = rssetjam.getString("diagnosaakhir");
                    hariawal = rssetjam.getString("hariawal");
                    aktifkan_hapus_data_salah = rssetjam.getString("aktifkan_hapus_data_salah");
                }
            } catch (Exception e) {
                System.out.println("Set Kamar Inap : " + e.toString());

            } finally {
                if (rssetjam != null) {
                    rssetjam.close();
                }
                if (pssetjam != null) {
                    pssetjam.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Set Kamar Inap : " + e.toString());

        }

        try {
            if (diagnosa_akhir.equals("Yes")) {
                diagnosaakhir.setEditable(true);
            } else {
                diagnosaakhir.setEditable(false);
            }
        } catch (Exception e) {
            diagnosaakhir.setEditable(false);
            System.out.println(e.toString());

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowInputKamar = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        norawat = new widget.TextBox();
        TPasien = new widget.TextBox();
        kdkamar = new widget.TextBox();
        jLabel10 = new widget.Label();
        ttlbiaya = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel12 = new widget.Label();
        btnReg = new widget.Button();
        TNoRM = new widget.TextBox();
        btnKamar = new widget.Button();
        TKdBngsal = new widget.TextBox();
        jLabel13 = new widget.Label();
        cmbDtk = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbJam = new widget.ComboBox();
        TBangsal = new widget.TextBox();
        jLabel11 = new widget.Label();
        TJmlHari = new widget.TextBox();
        jLabel15 = new widget.Label();
        TSttsKamar = new widget.TextBox();
        BtnCloseIn = new widget.Button();
        jLabel19 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        jLabel14 = new widget.Label();
        jLabel16 = new widget.Label();
        TTarif = new widget.TextBox();
        LblStts = new widget.Label();
        jLabel18 = new widget.Label();
        diagnosaawal = new widget.TextBox();
        diagnosaakhir = new widget.TextBox();
        jLabel23 = new widget.Label();
        CmbTahun = new widget.ComboBox();
        CmbBln = new widget.ComboBox();
        CmbTgl = new widget.ComboBox();
        jLabel24 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        jLabel26 = new widget.Label();
        btnDiagnosa = new widget.Button();
        upf = new widget.ComboBox();
        jLabel39 = new widget.Label();
        kirimanDari = new widget.ComboBox();
        jLabel40 = new widget.Label();
        TOut = new widget.TextBox();
        TIn = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDataRM = new javax.swing.JMenu();
        ppAsuhanGizi = new javax.swing.JMenuItem();
        ppMonitoringAsuhanGizi = new javax.swing.JMenuItem();
        ppResume = new javax.swing.JMenuItem();
        MnDiagnosa = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        detailRawat = new javax.swing.JMenuItem();
        MnPermintaan = new javax.swing.JMenu();
        MnJadwalOperasi = new javax.swing.JMenuItem();
        MnSKDPBPJS = new javax.swing.JMenuItem();
        MnPermintaanLab = new javax.swing.JMenuItem();
        MnPermintaanRadiologi = new javax.swing.JMenuItem();
        MnTindakan = new javax.swing.JMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnRawatInap = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnDiet = new javax.swing.JMenuItem();
        MnObat = new javax.swing.JMenu();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnInputResep = new javax.swing.JMenuItem();
        MnNoResep = new javax.swing.JMenuItem();
        MnResepDOkter = new javax.swing.JMenuItem();
        MnStokObatPasien = new javax.swing.JMenuItem();
        MnReturJual = new javax.swing.JMenuItem();
        MnResepPulang = new javax.swing.JMenuItem();
        MnDeposit = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        MnLaporan = new javax.swing.JMenu();
        MnRincianObat = new javax.swing.JMenuItem();
        MnRM2D = new javax.swing.JMenuItem();
        MnSensusRanap = new javax.swing.JMenuItem();
        MnRekapitulasiRanap = new javax.swing.JMenuItem();
        MnTilikBedah = new javax.swing.JMenuItem();
        MnAsuhanGizi = new javax.swing.JMenuItem();
        MnPenggunaanKamar = new javax.swing.JMenuItem();
        MnPengantarPulang = new javax.swing.JMenuItem();
        MnFormulirPenerimaan = new javax.swing.JMenuItem();
        MnFormulirPenerimaan1 = new javax.swing.JMenuItem();
        MnCetakSuratSakit1 = new javax.swing.JMenuItem();
        MnSuratJaminanPelayanan = new javax.swing.JMenuItem();
        MnLembarMasukKeluar = new javax.swing.JMenuItem();
        MnSuratPernyataan = new javax.swing.JMenuItem();
        MnSpri = new javax.swing.JMenuItem();
        MnSuratKematian = new javax.swing.JMenuItem();
        MnGelang = new javax.swing.JMenu();
        MnLabelTracker = new javax.swing.JMenuItem();
        MnLabelTracker1 = new javax.swing.JMenuItem();
        MnLabelTracker2 = new javax.swing.JMenuItem();
        MnLabelTracker3 = new javax.swing.JMenuItem();
        MnBarcode = new javax.swing.JMenuItem();
        MnBarcode1 = new javax.swing.JMenuItem();
        MnBarcodeRM9 = new javax.swing.JMenuItem();
        MnGelang5 = new javax.swing.JMenuItem();
        MnGelang6 = new javax.swing.JMenuItem();
        MnGelang7 = new javax.swing.JMenuItem();
        MnGelang8 = new javax.swing.JMenuItem();
        MnGelang9 = new javax.swing.JMenuItem();
        MnGelang10 = new javax.swing.JMenuItem();
        MnGelang11 = new javax.swing.JMenuItem();
        MnGelang1 = new javax.swing.JMenuItem();
        MnGelang2 = new javax.swing.JMenuItem();
        MnGelang3 = new javax.swing.JMenuItem();
        MnGelang4 = new javax.swing.JMenuItem();
        MnRanapGabung = new javax.swing.JMenuItem();
        MnGabungkanRanap = new javax.swing.JMenuItem();
        MnDPJP = new javax.swing.JMenuItem();
        MnDPJPRanap = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        MnRujukan = new javax.swing.JMenu();
        MnRujuk = new javax.swing.JMenuItem();
        MnRujukMasuk = new javax.swing.JMenuItem();
        MenuBPJS = new javax.swing.JMenu();
        MnCekKepesertaan = new javax.swing.JMenuItem();
        MnCekNIK = new javax.swing.JMenuItem();
        MnSEP = new javax.swing.JMenuItem();
        MnRujukSisrute = new javax.swing.JMenuItem();
        ppPasienCorona = new javax.swing.JMenuItem();
        ppPerawatanCorona = new javax.swing.JMenuItem();
        MnSJP = new javax.swing.JMenuItem();
        MenuInputData = new javax.swing.JMenu();
        ppBerkasDigital = new javax.swing.JMenuItem();
        ppCatatanPasien = new javax.swing.JMenuItem();
        ppDataHAIs = new javax.swing.JMenuItem();
        ppIKP = new javax.swing.JMenuItem();
        ppInputCoderBpjs = new javax.swing.JMenuItem();
        ppSpri = new javax.swing.JMenuItem();
        ppSuratKematian = new javax.swing.JMenuItem();
        MnTeridentifikasiTB = new javax.swing.JMenuItem();
        SetStatus = new javax.swing.JMenu();
        MnAtasPersetujuanDokter = new javax.swing.JMenuItem();
        MnStatusAPS = new javax.swing.JMenuItem();
        MnStatusRujuk = new javax.swing.JMenuItem();
        MnStatusMeninggal = new javax.swing.JMenuItem();
        MnKabur = new javax.swing.JMenuItem();
        MnStatusBelumLengkap = new javax.swing.JMenuItem();
        MnStatusBelumPulang = new javax.swing.JMenuItem();
        MnGanti = new javax.swing.JMenu();
        MnPenjab = new javax.swing.JMenuItem();
        MnDiagnosaMasuk = new javax.swing.JMenuItem();
        MnDiagnosaAkhir = new javax.swing.JMenuItem();
        MnWaktuMasuk = new javax.swing.JMenuItem();
        MnHapusData = new javax.swing.JMenu();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnHapusDataSalah = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        MnUpdateHari = new javax.swing.JMenuItem();
        MnPerkiraanBiaya = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        JamMasuk = new widget.TextBox();
        WindowPindahKamar = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        norawatpindah = new widget.TextBox();
        TPasienpindah = new widget.TextBox();
        kdkamarpindah = new widget.TextBox();
        ttlbiayapindah = new widget.TextBox();
        jLabel4 = new widget.Label();
        jLabel20 = new widget.Label();
        TNoRMpindah = new widget.TextBox();
        btnKamar2 = new widget.Button();
        TKdBngsalpindah = new widget.TextBox();
        jLabel27 = new widget.Label();
        cmbDtkpindah = new widget.ComboBox();
        cmbMntpindah = new widget.ComboBox();
        cmbJampindah = new widget.ComboBox();
        TBangsalpindah = new widget.TextBox();
        jLabel28 = new widget.Label();
        TJmlHaripindah = new widget.TextBox();
        jLabel29 = new widget.Label();
        TSttsKamarpindah = new widget.TextBox();
        BtnCloseInpindah = new widget.Button();
        jLabel30 = new widget.Label();
        BtnSimpanpindah = new widget.Button();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        TTarifpindah = new widget.TextBox();
        CmbTahunpindah = new widget.ComboBox();
        CmbBlnpindah = new widget.ComboBox();
        CmbTglpindah = new widget.ComboBox();
        jLabel35 = new widget.Label();
        Rganti3 = new widget.RadioButton();
        jLabel33 = new widget.Label();
        Rganti2 = new widget.RadioButton();
        Rganti1 = new widget.RadioButton();
        Rganti4 = new widget.RadioButton();
        buttonGroup2 = new javax.swing.ButtonGroup();
        WindowCaraBayar = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel17 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        btnBayar = new widget.Button();
        WindowRanapGabung = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        BtnCloseGabung = new widget.Button();
        BtnSimpanGabung = new widget.Button();
        jLabel34 = new widget.Label();
        NoRmBayi = new widget.TextBox();
        NmBayi = new widget.TextBox();
        btnPasienRanapGabung = new widget.Button();
        BtnHapusGabung = new widget.Button();
        NoRawatGabung = new widget.TextBox();
        btnPasienRanapGabung1 = new widget.Button();
        DlgSakit2 = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        panelBiasa4 = new widget.PanelBiasa();
        jLabel38 = new widget.Label();
        BtnPrint5 = new widget.Button();
        BtnKeluar4 = new widget.Button();
        NomorSurat = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        CrDokter3 = new widget.TextBox();
        jLabel36 = new widget.Label();
        DlgCatatan = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        LabelCatatan = new widget.Label();
        WindowDiagnosaMasuk = new javax.swing.JDialog();
        internalFrame9 = new widget.InternalFrame();
        BtnCloseIn5 = new widget.Button();
        BtnSimpan5 = new widget.Button();
        jLabel41 = new widget.Label();
        DiagnosaAwalSementara = new widget.TextBox();
        WindowDiagnosaAkhir = new javax.swing.JDialog();
        internalFrame10 = new widget.InternalFrame();
        BtnCloseIn6 = new widget.Button();
        BtnSimpan6 = new widget.Button();
        jLabel42 = new widget.Label();
        DiagnosaAkhirSementara = new widget.TextBox();
        WindowInputCoderBPJS = new javax.swing.JDialog();
        internalFrame11 = new widget.InternalFrame();
        BtnCloseCoder = new widget.Button();
        BtnSimpanCoder = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tableCoder = new widget.Table();
        label1 = new widget.Label();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel43 = new widget.Label();
        txtHak1 = new widget.TextBox();
        jLabel44 = new widget.Label();
        txtHak2 = new widget.TextBox();
        jLabel45 = new widget.Label();
        txtHak3 = new widget.TextBox();
        jLabel46 = new widget.Label();
        txtSelisih = new widget.TextBox();
        jLabel47 = new widget.Label();
        txtPenjaminan = new widget.TextBox();
        BtnSimpanCoder1 = new widget.Button();
        txtIdCoder = new javax.swing.JTextField();
        WindowWaktuMasuk = new javax.swing.JDialog();
        internalFrame12 = new widget.InternalFrame();
        BtnCloseIn7 = new widget.Button();
        BtnSimpan7 = new widget.Button();
        jLabel8 = new widget.Label();
        dateMasuk = new widget.Tanggal1();
        jLabel9 = new widget.Label();
        ComboJam = new widget.ComboBox();
        ComboMenit = new widget.ComboBox();
        ComboDetik = new widget.ComboBox();
        internalFrame1 = new widget.InternalFrame();
        PanelCariUtama = new javax.swing.JPanel();
        panelGlass10 = new widget.panelisi();
        BtnIn = new widget.Button();
        BtnOut = new widget.Button();
        btnPindah = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass11 = new widget.panelisi();
        jLabel21 = new widget.Label();
        BangsalCari = new widget.TextBox();
        btnBangsalCari = new widget.Button();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelCari = new widget.panelisi();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        DTPCari1 = new widget.Tanggal1();
        jLabel22 = new widget.Label();
        DTPCari2 = new widget.Tanggal1();
        R3 = new widget.RadioButton();
        DTPCari3 = new widget.Tanggal1();
        jLabel25 = new widget.Label();
        DTPCari4 = new widget.Tanggal1();
        chkBaby = new javax.swing.JCheckBox();
        Scroll = new widget.ScrollPane();
        tbKamIn = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel5 = new widget.Label();
        TNoRw1 = new widget.TextBox();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        jLabel37 = new widget.Label();
        cmbStatusBayar = new widget.ComboBox();

        WindowInputKamar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInputKamar.setName("WindowInputKamar"); // NOI18N
        WindowInputKamar.setUndecorated(true);
        WindowInputKamar.setResizable(false);
        WindowInputKamar.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                WindowInputKamarWindowActivated(evt);
            }
        });

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Input Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

        norawat.setHighlighter(null);
        norawat.setName("norawat"); // NOI18N
        norawat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                norawatFocusGained(evt);
            }
        });
        norawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatKeyPressed(evt);
            }
        });
        internalFrame2.add(norawat);
        norawat.setBounds(75, 25, 150, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        internalFrame2.add(TPasien);
        TPasien.setBounds(359, 25, 269, 23);

        kdkamar.setHighlighter(null);
        kdkamar.setName("kdkamar"); // NOI18N
        kdkamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkamarKeyPressed(evt);
            }
        });
        internalFrame2.add(kdkamar);
        kdkamar.setBounds(75, 55, 95, 23);

        jLabel10.setText("Proses :");
        jLabel10.setName("jLabel10"); // NOI18N
        internalFrame2.add(jLabel10);
        jLabel10.setBounds(0, 195, 72, 23);

        ttlbiaya.setEditable(false);
        ttlbiaya.setText("0");
        ttlbiaya.setHighlighter(null);
        ttlbiaya.setName("ttlbiaya"); // NOI18N
        ttlbiaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ttlbiayaKeyPressed(evt);
            }
        });
        internalFrame2.add(ttlbiaya);
        ttlbiaya.setBounds(368, 145, 290, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        internalFrame2.add(jLabel3);
        jLabel3.setBounds(0, 25, 72, 23);

        jLabel12.setText("Kamar :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame2.add(jLabel12);
        jLabel12.setBounds(0, 55, 72, 23);

        btnReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnReg.setMnemonic('1');
        btnReg.setToolTipText("Alt+1");
        btnReg.setName("btnReg"); // NOI18N
        btnReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegActionPerformed(evt);
            }
        });
        btnReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRegKeyPressed(evt);
            }
        });
        internalFrame2.add(btnReg);
        btnReg.setBounds(630, 25, 28, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        internalFrame2.add(TNoRM);
        TNoRM.setBounds(227, 25, 130, 23);

        btnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKamar.setMnemonic('2');
        btnKamar.setToolTipText("Alt+2");
        btnKamar.setName("btnKamar"); // NOI18N
        btnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarActionPerformed(evt);
            }
        });
        btnKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKamarKeyPressed(evt);
            }
        });
        internalFrame2.add(btnKamar);
        btnKamar.setBounds(428, 55, 28, 23);

        TKdBngsal.setEditable(false);
        TKdBngsal.setName("TKdBngsal"); // NOI18N
        internalFrame2.add(TKdBngsal);
        TKdBngsal.setBounds(172, 55, 82, 23);

        jLabel13.setText("Tanggal :");
        jLabel13.setName("jLabel13"); // NOI18N
        internalFrame2.add(jLabel13);
        jLabel13.setBounds(0, 85, 72, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbDtk);
        cmbDtk.setBounds(207, 115, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbMnt);
        cmbMnt.setBounds(141, 115, 62, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbJam);
        cmbJam.setBounds(75, 115, 62, 23);

        TBangsal.setEditable(false);
        TBangsal.setHighlighter(null);
        TBangsal.setName("TBangsal"); // NOI18N
        TBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBangsalKeyPressed(evt);
            }
        });
        internalFrame2.add(TBangsal);
        TBangsal.setBounds(256, 55, 170, 23);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("X");
        jLabel11.setName("jLabel11"); // NOI18N
        internalFrame2.add(jLabel11);
        jLabel11.setBounds(173, 145, 15, 23);

        TJmlHari.setText("0");
        TJmlHari.setHighlighter(null);
        TJmlHari.setName("TJmlHari"); // NOI18N
        internalFrame2.add(TJmlHari);
        TJmlHari.setBounds(75, 145, 96, 23);

        jLabel15.setText("=");
        jLabel15.setName("jLabel15"); // NOI18N
        internalFrame2.add(jLabel15);
        jLabel15.setBounds(342, 145, 20, 23);

        TSttsKamar.setEditable(false);
        TSttsKamar.setName("TSttsKamar"); // NOI18N
        internalFrame2.add(TSttsKamar);
        TSttsKamar.setBounds(548, 55, 110, 23);

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
        BtnCloseIn.setBounds(560, 230, 100, 30);

        jLabel19.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame2.add(jLabel19);
        jLabel19.setBounds(-10, 215, 850, 14);

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
        BtnSimpan.setBounds(14, 230, 100, 30);

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
        BtnBatal.setBounds(117, 230, 100, 30);

        jLabel14.setText("Stts.Kamar :");
        jLabel14.setName("jLabel14"); // NOI18N
        internalFrame2.add(jLabel14);
        jLabel14.setBounds(444, 55, 100, 23);

        jLabel16.setText("Biaya :");
        jLabel16.setName("jLabel16"); // NOI18N
        internalFrame2.add(jLabel16);
        jLabel16.setBounds(0, 145, 72, 23);

        TTarif.setText("0");
        TTarif.setHighlighter(null);
        TTarif.setName("TTarif"); // NOI18N
        internalFrame2.add(TTarif);
        TTarif.setBounds(188, 145, 160, 23);

        LblStts.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblStts.setText("Check In");
        LblStts.setName("LblStts"); // NOI18N
        internalFrame2.add(LblStts);
        LblStts.setBounds(75, 195, 180, 23);

        jLabel18.setText("Diagnosa Awal Masuk :");
        jLabel18.setName("jLabel18"); // NOI18N
        internalFrame2.add(jLabel18);
        jLabel18.setBounds(295, 85, 140, 23);

        diagnosaawal.setHighlighter(null);
        diagnosaawal.setName("diagnosaawal"); // NOI18N
        diagnosaawal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diagnosaawalKeyPressed(evt);
            }
        });
        internalFrame2.add(diagnosaawal);
        diagnosaawal.setBounds(438, 85, 220, 23);

        diagnosaakhir.setHighlighter(null);
        diagnosaakhir.setName("diagnosaakhir"); // NOI18N
        diagnosaakhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diagnosaakhirKeyPressed(evt);
            }
        });
        internalFrame2.add(diagnosaakhir);
        diagnosaakhir.setBounds(438, 115, 190, 23);

        jLabel23.setText("Diagnosa Akhir Keluar :");
        jLabel23.setName("jLabel23"); // NOI18N
        internalFrame2.add(jLabel23);
        jLabel23.setBounds(295, 115, 140, 23);

        CmbTahun.setName("CmbTahun"); // NOI18N
        CmbTahun.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        CmbTahun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbTahunKeyPressed(evt);
            }
        });
        internalFrame2.add(CmbTahun);
        CmbTahun.setBounds(207, 85, 90, 23);

        CmbBln.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        CmbBln.setName("CmbBln"); // NOI18N
        CmbBln.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        CmbBln.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbBlnKeyPressed(evt);
            }
        });
        internalFrame2.add(CmbBln);
        CmbBln.setBounds(141, 85, 62, 23);

        CmbTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        CmbTgl.setName("CmbTgl"); // NOI18N
        CmbTgl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        CmbTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbTglKeyPressed(evt);
            }
        });
        internalFrame2.add(CmbTgl);
        CmbTgl.setBounds(75, 85, 62, 23);

        jLabel24.setText("Jam :");
        jLabel24.setName("jLabel24"); // NOI18N
        internalFrame2.add(jLabel24);
        jLabel24.setBounds(0, 115, 72, 23);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Atas Persetujuan Dokter", "Atas Permintaan Sendiri", "Rujuk", "Meninggal", "Kabur" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbStatusKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbStatus);
        cmbStatus.setBounds(418, 195, 240, 20);

        jLabel26.setText("Status Pulang/Keluar :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame2.add(jLabel26);
        jLabel26.setBounds(275, 195, 140, 23);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa.setMnemonic('3');
        btnDiagnosa.setToolTipText("Alt+3");
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        btnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyPressed(evt);
            }
        });
        internalFrame2.add(btnDiagnosa);
        btnDiagnosa.setBounds(630, 115, 28, 23);

        upf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Penyakit Dalam", "Bedah", "Anak", "Kebidanan/Kandungan", "THT", "Mata", "JP", "Paru", "ICU", "Syaraf", "Lain-Lain", " " }));
        upf.setName("upf"); // NOI18N
        upf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                upfKeyPressed(evt);
            }
        });
        internalFrame2.add(upf);
        upf.setBounds(75, 170, 200, 20);

        jLabel39.setText("UPF :");
        jLabel39.setName("jLabel39"); // NOI18N
        internalFrame2.add(jLabel39);
        jLabel39.setBounds(0, 170, 72, 23);

        kirimanDari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Poliklinik", "IGD", "Puskesmas", "Dokter", "RS Pemerintah", "RS Swasta", "Lain-lain" }));
        kirimanDari.setName("kirimanDari"); // NOI18N
        kirimanDari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kirimanDariKeyPressed(evt);
            }
        });
        internalFrame2.add(kirimanDari);
        kirimanDari.setBounds(418, 170, 240, 20);

        jLabel40.setText("Kiriman dari :");
        jLabel40.setName("jLabel40"); // NOI18N
        internalFrame2.add(jLabel40);
        jLabel40.setBounds(275, 170, 140, 23);

        WindowInputKamar.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

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

        jPopupMenu1.setForeground(new java.awt.Color(50, 50, 50));
        jPopupMenu1.setAutoscrolls(true);
        jPopupMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPopupMenu1.setFocusTraversalPolicyProvider(true);
        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDataRM.setBackground(new java.awt.Color(250, 255, 245));
        MnDataRM.setForeground(new java.awt.Color(50, 50, 50));
        MnDataRM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataRM.setText("Data Rekam Medis");
        MnDataRM.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataRM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataRM.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataRM.setName("MnDataRM"); // NOI18N
        MnDataRM.setPreferredSize(new java.awt.Dimension(200, 26));

        ppAsuhanGizi.setBackground(new java.awt.Color(255, 255, 254));
        ppAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppAsuhanGizi.setForeground(new java.awt.Color(50, 50, 50));
        ppAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppAsuhanGizi.setText("Asuhan Gizi");
        ppAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppAsuhanGizi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppAsuhanGizi.setName("ppAsuhanGizi"); // NOI18N
        ppAsuhanGizi.setPreferredSize(new java.awt.Dimension(170, 26));
        ppAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppAsuhanGiziBtnPrintActionPerformed(evt);
            }
        });
        MnDataRM.add(ppAsuhanGizi);

        ppMonitoringAsuhanGizi.setBackground(new java.awt.Color(255, 255, 254));
        ppMonitoringAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppMonitoringAsuhanGizi.setForeground(new java.awt.Color(50, 50, 50));
        ppMonitoringAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppMonitoringAsuhanGizi.setText("Monitoring Gizi");
        ppMonitoringAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppMonitoringAsuhanGizi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppMonitoringAsuhanGizi.setName("ppMonitoringAsuhanGizi"); // NOI18N
        ppMonitoringAsuhanGizi.setPreferredSize(new java.awt.Dimension(170, 26));
        ppMonitoringAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppMonitoringAsuhanGiziBtnPrintActionPerformed(evt);
            }
        });
        MnDataRM.add(ppMonitoringAsuhanGizi);

        ppResume.setBackground(new java.awt.Color(255, 255, 254));
        ppResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppResume.setForeground(new java.awt.Color(50, 50, 50));
        ppResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppResume.setText("Resume");
        ppResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppResume.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppResume.setName("ppResume"); // NOI18N
        ppResume.setPreferredSize(new java.awt.Dimension(170, 26));
        ppResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppResumeBtnPrintActionPerformed(evt);
            }
        });
        MnDataRM.add(ppResume);

        MnDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        MnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa.setForeground(new java.awt.Color(50, 50, 50));
        MnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosa.setText("Diagnosa");
        MnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa.setName("MnDiagnosa"); // NOI18N
        MnDiagnosa.setPreferredSize(new java.awt.Dimension(170, 26));
        MnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosaActionPerformed(evt);
            }
        });
        MnDataRM.add(MnDiagnosa);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(50, 50, 50));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(170, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        MnDataRM.add(ppRiwayat);

        detailRawat.setBackground(new java.awt.Color(255, 255, 254));
        detailRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        detailRawat.setForeground(new java.awt.Color(50, 50, 50));
        detailRawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        detailRawat.setText("Detail Rawat");
        detailRawat.setName("detailRawat"); // NOI18N
        detailRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailRawatActionPerformed(evt);
            }
        });
        MnDataRM.add(detailRawat);

        jPopupMenu1.add(MnDataRM);

        MnPermintaan.setBackground(new java.awt.Color(250, 255, 245));
        MnPermintaan.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaan.setText("Permintaan");
        MnPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaan.setName("MnPermintaan"); // NOI18N
        MnPermintaan.setPreferredSize(new java.awt.Dimension(210, 26));

        MnJadwalOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadwalOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadwalOperasi.setText("Jadwal Operasi");
        MnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadwalOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadwalOperasi.setName("MnJadwalOperasi"); // NOI18N
        MnJadwalOperasi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadwalOperasiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnJadwalOperasi);

        MnSKDPBPJS.setBackground(new java.awt.Color(255, 255, 254));
        MnSKDPBPJS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSKDPBPJS.setForeground(new java.awt.Color(50, 50, 50));
        MnSKDPBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSKDPBPJS.setText("SKDP BPJS");
        MnSKDPBPJS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSKDPBPJS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSKDPBPJS.setName("MnSKDPBPJS"); // NOI18N
        MnSKDPBPJS.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSKDPBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSKDPBPJSActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnSKDPBPJS);

        MnPermintaanLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanLab.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanLab.setText("Pemeriksaan Lab");
        MnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanLab.setName("MnPermintaanLab"); // NOI18N
        MnPermintaanLab.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanLabActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanLab);

        MnPermintaanRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanRadiologi.setText("Pemeriksaan Radiologi");
        MnPermintaanRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRadiologi.setName("MnPermintaanRadiologi"); // NOI18N
        MnPermintaanRadiologi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRadiologiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanRadiologi);

        jPopupMenu1.add(MnPermintaan);

        MnTindakan.setBackground(new java.awt.Color(250, 255, 245));
        MnTindakan.setForeground(new java.awt.Color(50, 50, 50));
        MnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan.setText("Tindakan & Pemeriksaan");
        MnTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan.setName("MnTindakan"); // NOI18N
        MnTindakan.setPreferredSize(new java.awt.Dimension(220, 26));

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(new java.awt.Color(50, 50, 50));
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Data Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        MnTindakan.add(MnRawatJalan);

        MnRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatInap.setForeground(new java.awt.Color(50, 50, 50));
        MnRawatInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatInap.setText("Data Tagihan/Tindakan Rawat Inap");
        MnRawatInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatInap.setName("MnRawatInap"); // NOI18N
        MnRawatInap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRawatInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatInapActionPerformed(evt);
            }
        });
        MnTindakan.add(MnRawatInap);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Lab");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaLab);

        MnPeriksaRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi.setText("Periksa Radiologi");
        MnPeriksaRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi.setName("MnPeriksaRadiologi"); // NOI18N
        MnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPeriksaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologiActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaRadiologi);

        MnOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi/VK");
        MnOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        MnTindakan.add(MnOperasi);

        MnDiet.setBackground(new java.awt.Color(255, 255, 254));
        MnDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiet.setForeground(new java.awt.Color(50, 50, 50));
        MnDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiet.setText("Diet Pasien");
        MnDiet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiet.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiet.setName("MnDiet"); // NOI18N
        MnDiet.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDietActionPerformed(evt);
            }
        });
        MnTindakan.add(MnDiet);

        jPopupMenu1.add(MnTindakan);

        MnObat.setBackground(new java.awt.Color(250, 255, 245));
        MnObat.setForeground(new java.awt.Color(50, 50, 50));
        MnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObat.setText("Obat");
        MnObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat.setName("MnObat"); // NOI18N
        MnObat.setPreferredSize(new java.awt.Dimension(220, 26));

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(50, 50, 50));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Data Pemberian Obat/BHP");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        MnObat.add(MnPemberianObat);

        MnInputResep.setBackground(new java.awt.Color(255, 255, 254));
        MnInputResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputResep.setForeground(new java.awt.Color(50, 50, 50));
        MnInputResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputResep.setText("Input Resep Pulang");
        MnInputResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInputResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInputResep.setName("MnInputResep"); // NOI18N
        MnInputResep.setPreferredSize(new java.awt.Dimension(200, 26));
        MnInputResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputResepActionPerformed(evt);
            }
        });
        MnObat.add(MnInputResep);

        MnNoResep.setBackground(new java.awt.Color(255, 255, 254));
        MnNoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep.setForeground(new java.awt.Color(50, 50, 50));
        MnNoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep.setText("Input No.Resep");
        MnNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep.setName("MnNoResep"); // NOI18N
        MnNoResep.setPreferredSize(new java.awt.Dimension(200, 26));
        MnNoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResepActionPerformed(evt);
            }
        });
        MnObat.add(MnNoResep);

        MnResepDOkter.setBackground(new java.awt.Color(255, 255, 254));
        MnResepDOkter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepDOkter.setForeground(new java.awt.Color(50, 50, 50));
        MnResepDOkter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepDOkter.setText("Input Resep Dokter");
        MnResepDOkter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepDOkter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepDOkter.setName("MnResepDOkter"); // NOI18N
        MnResepDOkter.setPreferredSize(new java.awt.Dimension(190, 26));
        MnResepDOkter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkterActionPerformed(evt);
            }
        });
        MnObat.add(MnResepDOkter);

        MnStokObatPasien.setBackground(new java.awt.Color(255, 255, 254));
        MnStokObatPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStokObatPasien.setForeground(new java.awt.Color(50, 50, 50));
        MnStokObatPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStokObatPasien.setText("Stok Obat Pasien Ranap");
        MnStokObatPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStokObatPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStokObatPasien.setName("MnStokObatPasien"); // NOI18N
        MnStokObatPasien.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStokObatPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStokObatPasienActionPerformed(evt);
            }
        });
        MnObat.add(MnStokObatPasien);

        MnReturJual.setBackground(new java.awt.Color(255, 255, 254));
        MnReturJual.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnReturJual.setForeground(new java.awt.Color(50, 50, 50));
        MnReturJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnReturJual.setText("Retur Obat/Barang/Alkes");
        MnReturJual.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnReturJual.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnReturJual.setName("MnReturJual"); // NOI18N
        MnReturJual.setPreferredSize(new java.awt.Dimension(200, 26));
        MnReturJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnReturJualActionPerformed(evt);
            }
        });
        MnObat.add(MnReturJual);

        MnResepPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnResepPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepPulang.setForeground(new java.awt.Color(50, 50, 50));
        MnResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepPulang.setText("Data Resep Pulang");
        MnResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepPulang.setName("MnResepPulang"); // NOI18N
        MnResepPulang.setPreferredSize(new java.awt.Dimension(200, 26));
        MnResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepPulangActionPerformed(evt);
            }
        });
        MnObat.add(MnResepPulang);

        jPopupMenu1.add(MnObat);

        MnDeposit.setBackground(new java.awt.Color(255, 255, 254));
        MnDeposit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDeposit.setForeground(new java.awt.Color(50, 50, 50));
        MnDeposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDeposit.setText("Deposit/Titipan Pasien");
        MnDeposit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDeposit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDeposit.setName("MnDeposit"); // NOI18N
        MnDeposit.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDepositActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDeposit);

        MnBilling.setBackground(new java.awt.Color(255, 255, 254));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(new java.awt.Color(50, 50, 50));
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing/Pembayaran Pasien");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBilling);

        jSeparator12.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator12.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator12.setName("jSeparator12"); // NOI18N
        jSeparator12.setPreferredSize(new java.awt.Dimension(220, 1));
        jPopupMenu1.add(jSeparator12);

        MnLaporan.setBackground(new java.awt.Color(250, 255, 245));
        MnLaporan.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporan.setText("Laporan & Surat");
        MnLaporan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporan.setName("MnLaporan"); // NOI18N
        MnLaporan.setPreferredSize(new java.awt.Dimension(220, 26));

        MnRincianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnRincianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRincianObat.setForeground(new java.awt.Color(50, 50, 50));
        MnRincianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRincianObat.setText("Rincian Penggunaan Obat");
        MnRincianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRincianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRincianObat.setName("MnRincianObat"); // NOI18N
        MnRincianObat.setPreferredSize(new java.awt.Dimension(210, 26));
        MnRincianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRincianObatActionPerformed(evt);
            }
        });
        MnLaporan.add(MnRincianObat);

        MnRM2D.setBackground(new java.awt.Color(255, 255, 254));
        MnRM2D.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRM2D.setForeground(new java.awt.Color(50, 50, 50));
        MnRM2D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRM2D.setText("Asesment Pasien IGD");
        MnRM2D.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRM2D.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRM2D.setName("MnRM2D"); // NOI18N
        MnRM2D.setPreferredSize(new java.awt.Dimension(210, 26));
        MnRM2D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRM2DActionPerformed(evt);
            }
        });
        MnLaporan.add(MnRM2D);

        MnSensusRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnSensusRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSensusRanap.setForeground(new java.awt.Color(50, 50, 50));
        MnSensusRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSensusRanap.setText("Sensus Harian Ranap");
        MnSensusRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSensusRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSensusRanap.setName("MnSensusRanap"); // NOI18N
        MnSensusRanap.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSensusRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSensusRanapActionPerformed(evt);
            }
        });
        MnLaporan.add(MnSensusRanap);

        MnRekapitulasiRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapitulasiRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapitulasiRanap.setForeground(new java.awt.Color(50, 50, 50));
        MnRekapitulasiRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapitulasiRanap.setText("Rekapitulasi Sensus Ranap");
        MnRekapitulasiRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapitulasiRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapitulasiRanap.setName("MnRekapitulasiRanap"); // NOI18N
        MnRekapitulasiRanap.setPreferredSize(new java.awt.Dimension(210, 26));
        MnRekapitulasiRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapitulasiRanapActionPerformed(evt);
            }
        });
        MnLaporan.add(MnRekapitulasiRanap);

        MnTilikBedah.setBackground(new java.awt.Color(255, 255, 254));
        MnTilikBedah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTilikBedah.setForeground(new java.awt.Color(50, 50, 50));
        MnTilikBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTilikBedah.setText("Daftar Tilik Keselamatan Bedah");
        MnTilikBedah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTilikBedah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTilikBedah.setName("MnTilikBedah"); // NOI18N
        MnTilikBedah.setPreferredSize(new java.awt.Dimension(210, 26));
        MnTilikBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTilikBedahActionPerformed(evt);
            }
        });
        MnLaporan.add(MnTilikBedah);

        MnAsuhanGizi.setBackground(new java.awt.Color(255, 255, 254));
        MnAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAsuhanGizi.setForeground(new java.awt.Color(50, 50, 50));
        MnAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAsuhanGizi.setText("Assesment Gizi");
        MnAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnAsuhanGizi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnAsuhanGizi.setName("MnAsuhanGizi"); // NOI18N
        MnAsuhanGizi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAsuhanGiziActionPerformed(evt);
            }
        });
        MnLaporan.add(MnAsuhanGizi);

        MnPenggunaanKamar.setBackground(new java.awt.Color(255, 255, 254));
        MnPenggunaanKamar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenggunaanKamar.setForeground(new java.awt.Color(50, 50, 50));
        MnPenggunaanKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenggunaanKamar.setText("Penggunaan Kamar");
        MnPenggunaanKamar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenggunaanKamar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenggunaanKamar.setName("MnPenggunaanKamar"); // NOI18N
        MnPenggunaanKamar.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPenggunaanKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenggunaanKamarActionPerformed(evt);
            }
        });
        MnLaporan.add(MnPenggunaanKamar);

        MnPengantarPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnPengantarPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPengantarPulang.setForeground(new java.awt.Color(50, 50, 50));
        MnPengantarPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPengantarPulang.setText("Surat Pengantar Pulang");
        MnPengantarPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPengantarPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPengantarPulang.setName("MnPengantarPulang"); // NOI18N
        MnPengantarPulang.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPengantarPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPengantarPulangActionPerformed(evt);
            }
        });
        MnLaporan.add(MnPengantarPulang);

        MnFormulirPenerimaan.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulirPenerimaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulirPenerimaan.setForeground(new java.awt.Color(50, 50, 50));
        MnFormulirPenerimaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulirPenerimaan.setText("Formulir Penerimaan Pasien 1");
        MnFormulirPenerimaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnFormulirPenerimaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnFormulirPenerimaan.setName("MnFormulirPenerimaan"); // NOI18N
        MnFormulirPenerimaan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnFormulirPenerimaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirPenerimaanActionPerformed(evt);
            }
        });
        MnLaporan.add(MnFormulirPenerimaan);

        MnFormulirPenerimaan1.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulirPenerimaan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulirPenerimaan1.setForeground(new java.awt.Color(50, 50, 50));
        MnFormulirPenerimaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulirPenerimaan1.setText("Formulir Penerimaan Pasien 2");
        MnFormulirPenerimaan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnFormulirPenerimaan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnFormulirPenerimaan1.setName("MnFormulirPenerimaan1"); // NOI18N
        MnFormulirPenerimaan1.setPreferredSize(new java.awt.Dimension(210, 26));
        MnFormulirPenerimaan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirPenerimaan1ActionPerformed(evt);
            }
        });
        MnLaporan.add(MnFormulirPenerimaan1);

        MnCetakSuratSakit1.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakit1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit1.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSakit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit1.setText("Surat Keterangan Rawat Inap");
        MnCetakSuratSakit1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit1.setName("MnCetakSuratSakit1"); // NOI18N
        MnCetakSuratSakit1.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCetakSuratSakit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakit1ActionPerformed(evt);
            }
        });
        MnLaporan.add(MnCetakSuratSakit1);

        MnSuratJaminanPelayanan.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratJaminanPelayanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratJaminanPelayanan.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratJaminanPelayanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratJaminanPelayanan.setText("Surat Jaminan Pelayanan");
        MnSuratJaminanPelayanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratJaminanPelayanan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratJaminanPelayanan.setName("MnSuratJaminanPelayanan"); // NOI18N
        MnSuratJaminanPelayanan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSuratJaminanPelayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratJaminanPelayananActionPerformed(evt);
            }
        });
        MnLaporan.add(MnSuratJaminanPelayanan);

        MnLembarMasukKeluar.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarMasukKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarMasukKeluar.setForeground(new java.awt.Color(50, 50, 50));
        MnLembarMasukKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarMasukKeluar.setText("Lembar Masuk Keluar");
        MnLembarMasukKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarMasukKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarMasukKeluar.setName("MnLembarMasukKeluar"); // NOI18N
        MnLembarMasukKeluar.setPreferredSize(new java.awt.Dimension(210, 26));
        MnLembarMasukKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarMasukKeluarActionPerformed(evt);
            }
        });
        MnLaporan.add(MnLembarMasukKeluar);

        MnSuratPernyataan.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratPernyataan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratPernyataan.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratPernyataan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratPernyataan.setText("Lembar Surat Pernyataan");
        MnSuratPernyataan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratPernyataan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratPernyataan.setName("MnSuratPernyataan"); // NOI18N
        MnSuratPernyataan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSuratPernyataan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratPernyataanActionPerformed(evt);
            }
        });
        MnLaporan.add(MnSuratPernyataan);

        MnSpri.setBackground(new java.awt.Color(255, 255, 254));
        MnSpri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSpri.setForeground(new java.awt.Color(50, 50, 50));
        MnSpri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSpri.setText("Surat Permintaan Rawat Inap (SPRI)");
        MnSpri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSpri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSpri.setName("MnSpri"); // NOI18N
        MnSpri.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSpri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSpriActionPerformed(evt);
            }
        });
        MnLaporan.add(MnSpri);

        MnSuratKematian.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratKematian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratKematian.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratKematian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratKematian.setText("Surat Kematian");
        MnSuratKematian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratKematian.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratKematian.setName("MnSuratKematian"); // NOI18N
        MnSuratKematian.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSuratKematian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratKematianActionPerformed(evt);
            }
        });
        MnLaporan.add(MnSuratKematian);

        jPopupMenu1.add(MnLaporan);

        MnGelang.setBackground(new java.awt.Color(250, 255, 245));
        MnGelang.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang.setText("Label & Gelang Pasien");
        MnGelang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang.setName("MnGelang"); // NOI18N
        MnGelang.setPreferredSize(new java.awt.Dimension(220, 26));

        MnLabelTracker.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker.setText("Label Tracker 1");
        MnLabelTracker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker.setName("MnLabelTracker"); // NOI18N
        MnLabelTracker.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTrackerActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker);

        MnLabelTracker1.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker1.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker1.setText("Label Tracker 2");
        MnLabelTracker1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker1.setName("MnLabelTracker1"); // NOI18N
        MnLabelTracker1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker1ActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker1);

        MnLabelTracker2.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker2.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker2.setText("Label Tracker 3");
        MnLabelTracker2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker2.setName("MnLabelTracker2"); // NOI18N
        MnLabelTracker2.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker2ActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker2);

        MnLabelTracker3.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker3.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker3.setText("Label Tracker 4");
        MnLabelTracker3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker3.setName("MnLabelTracker3"); // NOI18N
        MnLabelTracker3.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker3ActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker3);

        MnBarcode.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode.setText("Barcode Perawatan 1");
        MnBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode.setName("MnBarcode"); // NOI18N
        MnBarcode.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcode);

        MnBarcode1.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode1.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcode1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode1.setText("Barcode Perawatan 2");
        MnBarcode1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode1.setName("MnBarcode1"); // NOI18N
        MnBarcode1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode1ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcode1);

        MnBarcodeRM9.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodeRM9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM9.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcodeRM9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM9.setMnemonic('L');
        MnBarcodeRM9.setText("Label Rekam Medis 10");
        MnBarcodeRM9.setToolTipText("L");
        MnBarcodeRM9.setName("MnBarcodeRM9"); // NOI18N
        MnBarcodeRM9.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcodeRM9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM9ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcodeRM9);

        MnGelang5.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang5.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang5.setText("Gelang Pasien 1");
        MnGelang5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang5.setName("MnGelang5"); // NOI18N
        MnGelang5.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang5ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang5);

        MnGelang6.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang6.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang6.setText("Gelang Pasien 2");
        MnGelang6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang6.setName("MnGelang6"); // NOI18N
        MnGelang6.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang6ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang6);

        MnGelang7.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang7.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang7.setText("Gelang Pasien 3");
        MnGelang7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang7.setName("MnGelang7"); // NOI18N
        MnGelang7.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang7ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang7);

        MnGelang8.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang8.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang8.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang8.setText("Gelang Pasien 4");
        MnGelang8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang8.setName("MnGelang8"); // NOI18N
        MnGelang8.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang8ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang8);

        MnGelang9.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang9.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang9.setText("Gelang Pasien 5");
        MnGelang9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang9.setName("MnGelang9"); // NOI18N
        MnGelang9.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang9ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang9);

        MnGelang10.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang10.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang10.setText("Gelang Pasien 6");
        MnGelang10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang10.setName("MnGelang10"); // NOI18N
        MnGelang10.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang10ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang10);

        MnGelang11.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang11.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang11.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang11.setText("Gelang Pasien 7");
        MnGelang11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang11.setName("MnGelang11"); // NOI18N
        MnGelang11.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang11ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang11);

        MnGelang1.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang1.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang1.setText("Gelang Pasien 8");
        MnGelang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang1.setName("MnGelang1"); // NOI18N
        MnGelang1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang1ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang1);

        MnGelang2.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang2.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang2.setText("Gelang Pasien 9");
        MnGelang2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang2.setName("MnGelang2"); // NOI18N
        MnGelang2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang2ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang2);

        MnGelang3.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang3.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang3.setText("Gelang Pasien Anak");
        MnGelang3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang3.setName("MnGelang3"); // NOI18N
        MnGelang3.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang3ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang3);

        MnGelang4.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang4.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang4.setText("Gelang Pasien Dewasa");
        MnGelang4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang4.setName("MnGelang4"); // NOI18N
        MnGelang4.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang4ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang4);

        jPopupMenu1.add(MnGelang);

        MnRanapGabung.setBackground(new java.awt.Color(255, 255, 254));
        MnRanapGabung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRanapGabung.setForeground(new java.awt.Color(50, 50, 50));
        MnRanapGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRanapGabung.setText("Ranap Gabung Ibu & Bayi");
        MnRanapGabung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRanapGabung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRanapGabung.setName("MnRanapGabung"); // NOI18N
        MnRanapGabung.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRanapGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRanapGabungActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRanapGabung);

        MnGabungkanRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnGabungkanRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGabungkanRanap.setForeground(new java.awt.Color(50, 50, 50));
        MnGabungkanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGabungkanRanap.setText("Gabungkan Ke Kamar Ibu");
        MnGabungkanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGabungkanRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGabungkanRanap.setName("MnGabungkanRanap"); // NOI18N
        MnGabungkanRanap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnGabungkanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGabungkanRanapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnGabungkanRanap);

        MnDPJP.setBackground(new java.awt.Color(255, 255, 254));
        MnDPJP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDPJP.setForeground(new java.awt.Color(50, 50, 50));
        MnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDPJP.setText("Input Dokter P.J. Ranap");
        MnDPJP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDPJP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDPJP.setName("MnDPJP"); // NOI18N
        MnDPJP.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDPJPActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDPJP);

        MnDPJPRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnDPJPRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDPJPRanap.setForeground(new java.awt.Color(50, 50, 50));
        MnDPJPRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDPJPRanap.setText("Tampilkan Dokter P.J. Ranap");
        MnDPJPRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDPJPRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDPJPRanap.setName("MnDPJPRanap"); // NOI18N
        MnDPJPRanap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDPJPRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDPJPRanapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDPJPRanap);

        jSeparator13.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator13.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator13.setName("jSeparator13"); // NOI18N
        jSeparator13.setPreferredSize(new java.awt.Dimension(220, 1));
        jPopupMenu1.add(jSeparator13);

        MnRujukan.setBackground(new java.awt.Color(250, 255, 245));
        MnRujukan.setForeground(new java.awt.Color(50, 50, 50));
        MnRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukan.setText("Rujukan");
        MnRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukan.setName("MnRujukan"); // NOI18N
        MnRujukan.setPreferredSize(new java.awt.Dimension(210, 26));

        MnRujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(new java.awt.Color(50, 50, 50));
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujuk.setText("Rujukan Keluar");
        MnRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(150, 26));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujuk);

        MnRujukMasuk.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukMasuk.setForeground(new java.awt.Color(50, 50, 50));
        MnRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukMasuk.setText("Rujukan Masuk");
        MnRujukMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukMasuk.setName("MnRujukMasuk"); // NOI18N
        MnRujukMasuk.setPreferredSize(new java.awt.Dimension(150, 26));
        MnRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukMasukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujukMasuk);

        jPopupMenu1.add(MnRujukan);

        MenuBPJS.setBackground(new java.awt.Color(250, 255, 245));
        MenuBPJS.setForeground(new java.awt.Color(50, 50, 50));
        MenuBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuBPJS.setText("Bridging");
        MenuBPJS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuBPJS.setName("MenuBPJS"); // NOI18N
        MenuBPJS.setPreferredSize(new java.awt.Dimension(220, 26));

        MnCekKepesertaan.setBackground(new java.awt.Color(255, 255, 254));
        MnCekKepesertaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCekKepesertaan.setForeground(new java.awt.Color(50, 50, 50));
        MnCekKepesertaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCekKepesertaan.setText("Pencarian Peserta BPJS Berdasarkan Nomor Kepesertaan");
        MnCekKepesertaan.setName("MnCekKepesertaan"); // NOI18N
        MnCekKepesertaan.setPreferredSize(new java.awt.Dimension(330, 26));
        MnCekKepesertaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCekKepesertaanActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnCekKepesertaan);

        MnCekNIK.setBackground(new java.awt.Color(255, 255, 254));
        MnCekNIK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCekNIK.setForeground(new java.awt.Color(50, 50, 50));
        MnCekNIK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCekNIK.setText("Pencarian Peserta BPJS Berdasarkan NIK/No.KTP");
        MnCekNIK.setName("MnCekNIK"); // NOI18N
        MnCekNIK.setPreferredSize(new java.awt.Dimension(330, 26));
        MnCekNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCekNIKActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnCekNIK);

        MnSEP.setBackground(new java.awt.Color(255, 255, 254));
        MnSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSEP.setForeground(new java.awt.Color(50, 50, 50));
        MnSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSEP.setText("Bridging SEP VClaim");
        MnSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSEP.setName("MnSEP"); // NOI18N
        MnSEP.setPreferredSize(new java.awt.Dimension(330, 26));
        MnSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSEPActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnSEP);

        MnRujukSisrute.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukSisrute.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukSisrute.setForeground(new java.awt.Color(50, 50, 50));
        MnRujukSisrute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukSisrute.setText("Rujuk Keluar Via Sisrute");
        MnRujukSisrute.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukSisrute.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukSisrute.setName("MnRujukSisrute"); // NOI18N
        MnRujukSisrute.setPreferredSize(new java.awt.Dimension(330, 26));
        MnRujukSisrute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukSisruteActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnRujukSisrute);

        ppPasienCorona.setBackground(new java.awt.Color(255, 255, 254));
        ppPasienCorona.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPasienCorona.setForeground(new java.awt.Color(50, 50, 50));
        ppPasienCorona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPasienCorona.setText("Pasien Corona Kemenkes");
        ppPasienCorona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPasienCorona.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPasienCorona.setName("ppPasienCorona"); // NOI18N
        ppPasienCorona.setPreferredSize(new java.awt.Dimension(330, 26));
        ppPasienCorona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPasienCoronaActionPerformed(evt);
            }
        });
        MenuBPJS.add(ppPasienCorona);

        ppPerawatanCorona.setBackground(new java.awt.Color(255, 255, 254));
        ppPerawatanCorona.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPerawatanCorona.setForeground(new java.awt.Color(50, 50, 50));
        ppPerawatanCorona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPerawatanCorona.setText("Perawatan Pasien Corona INACBG");
        ppPerawatanCorona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPerawatanCorona.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPerawatanCorona.setName("ppPerawatanCorona"); // NOI18N
        ppPerawatanCorona.setPreferredSize(new java.awt.Dimension(330, 26));
        ppPerawatanCorona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPerawatanCoronaActionPerformed(evt);
            }
        });
        MenuBPJS.add(ppPerawatanCorona);

        MnSJP.setBackground(new java.awt.Color(255, 255, 204));
        MnSJP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSJP.setForeground(new java.awt.Color(50, 50, 50));
        MnSJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSJP.setText("Bridging SJP Inhealth");
        MnSJP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSJP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSJP.setName("MnSJP"); // NOI18N
        MnSJP.setPreferredSize(new java.awt.Dimension(330, 26));
        MnSJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSJPActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnSJP);

        jPopupMenu1.add(MenuBPJS);

        MenuInputData.setBackground(new java.awt.Color(250, 255, 245));
        MenuInputData.setForeground(new java.awt.Color(50, 50, 50));
        MenuInputData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuInputData.setText("Input Data");
        MenuInputData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData.setName("MenuInputData"); // NOI18N
        MenuInputData.setPreferredSize(new java.awt.Dimension(220, 26));

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(200, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppBerkasDigital);

        ppCatatanPasien.setBackground(new java.awt.Color(255, 255, 254));
        ppCatatanPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCatatanPasien.setForeground(new java.awt.Color(50, 50, 50));
        ppCatatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCatatanPasien.setText("Catatan Untuk Pasien");
        ppCatatanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCatatanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCatatanPasien.setName("ppCatatanPasien"); // NOI18N
        ppCatatanPasien.setPreferredSize(new java.awt.Dimension(200, 26));
        ppCatatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCatatanPasienBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppCatatanPasien);

        ppDataHAIs.setBackground(new java.awt.Color(255, 255, 254));
        ppDataHAIs.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDataHAIs.setForeground(new java.awt.Color(50, 50, 50));
        ppDataHAIs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDataHAIs.setText("Data HAIs");
        ppDataHAIs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDataHAIs.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDataHAIs.setName("ppDataHAIs"); // NOI18N
        ppDataHAIs.setPreferredSize(new java.awt.Dimension(200, 26));
        ppDataHAIs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDataHAIsBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppDataHAIs);

        ppIKP.setBackground(new java.awt.Color(255, 255, 254));
        ppIKP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppIKP.setForeground(new java.awt.Color(50, 50, 50));
        ppIKP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppIKP.setText("Insiden Keselamatan Pasien");
        ppIKP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppIKP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppIKP.setName("ppIKP"); // NOI18N
        ppIKP.setPreferredSize(new java.awt.Dimension(200, 26));
        ppIKP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppIKPBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppIKP);

        ppInputCoderBpjs.setBackground(new java.awt.Color(255, 255, 254));
        ppInputCoderBpjs.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppInputCoderBpjs.setForeground(new java.awt.Color(50, 50, 50));
        ppInputCoderBpjs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppInputCoderBpjs.setText("Input Coder BPJS");
        ppInputCoderBpjs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppInputCoderBpjs.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppInputCoderBpjs.setName("ppInputCoderBpjs"); // NOI18N
        ppInputCoderBpjs.setPreferredSize(new java.awt.Dimension(200, 26));
        ppInputCoderBpjs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppInputCoderBpjsBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppInputCoderBpjs);

        ppSpri.setBackground(new java.awt.Color(255, 255, 254));
        ppSpri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSpri.setForeground(new java.awt.Color(50, 50, 50));
        ppSpri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSpri.setText("SPRI");
        ppSpri.setName("ppSpri"); // NOI18N
        ppSpri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSpriActionPerformed(evt);
            }
        });
        MenuInputData.add(ppSpri);

        ppSuratKematian.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratKematian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratKematian.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratKematian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratKematian.setText("Surat Kematian");
        ppSuratKematian.setName("ppSuratKematian"); // NOI18N
        ppSuratKematian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratKematianActionPerformed(evt);
            }
        });
        MenuInputData.add(ppSuratKematian);

        MnTeridentifikasiTB.setBackground(new java.awt.Color(255, 255, 254));
        MnTeridentifikasiTB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTeridentifikasiTB.setForeground(new java.awt.Color(50, 50, 50));
        MnTeridentifikasiTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTeridentifikasiTB.setText("Teridentifikasi TB");
        MnTeridentifikasiTB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTeridentifikasiTB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTeridentifikasiTB.setName("MnTeridentifikasiTB"); // NOI18N
        MnTeridentifikasiTB.setPreferredSize(new java.awt.Dimension(200, 26));
        MnTeridentifikasiTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTeridentifikasiTBActionPerformed(evt);
            }
        });
        MenuInputData.add(MnTeridentifikasiTB);

        jPopupMenu1.add(MenuInputData);

        SetStatus.setBackground(new java.awt.Color(250, 255, 245));
        SetStatus.setForeground(new java.awt.Color(50, 50, 50));
        SetStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        SetStatus.setText("Set Status Pulang");
        SetStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        SetStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SetStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        SetStatus.setName("SetStatus"); // NOI18N
        SetStatus.setPreferredSize(new java.awt.Dimension(220, 26));

        MnAtasPersetujuanDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnAtasPersetujuanDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAtasPersetujuanDokter.setForeground(new java.awt.Color(50, 50, 50));
        MnAtasPersetujuanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAtasPersetujuanDokter.setText("Atas Persetujuan Dokter");
        MnAtasPersetujuanDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnAtasPersetujuanDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnAtasPersetujuanDokter.setName("MnAtasPersetujuanDokter"); // NOI18N
        MnAtasPersetujuanDokter.setPreferredSize(new java.awt.Dimension(200, 26));
        MnAtasPersetujuanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAtasPersetujuanDokterActionPerformed(evt);
            }
        });
        SetStatus.add(MnAtasPersetujuanDokter);

        MnStatusAPS.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusAPS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusAPS.setForeground(new java.awt.Color(50, 50, 50));
        MnStatusAPS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusAPS.setText("Atas Permintaan Sendiri");
        MnStatusAPS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusAPS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusAPS.setName("MnStatusAPS"); // NOI18N
        MnStatusAPS.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusAPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusAPSActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusAPS);

        MnStatusRujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusRujuk.setForeground(new java.awt.Color(50, 50, 50));
        MnStatusRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusRujuk.setText("Rujuk");
        MnStatusRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusRujuk.setName("MnStatusRujuk"); // NOI18N
        MnStatusRujuk.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusRujukActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusRujuk);

        MnStatusMeninggal.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusMeninggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusMeninggal.setForeground(new java.awt.Color(50, 50, 50));
        MnStatusMeninggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusMeninggal.setText("Meninggal");
        MnStatusMeninggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusMeninggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusMeninggal.setName("MnStatusMeninggal"); // NOI18N
        MnStatusMeninggal.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusMeninggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusMeninggalActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusMeninggal);

        MnKabur.setBackground(new java.awt.Color(255, 255, 254));
        MnKabur.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKabur.setForeground(new java.awt.Color(50, 50, 50));
        MnKabur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKabur.setText("Kabur");
        MnKabur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKabur.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKabur.setName("MnKabur"); // NOI18N
        MnKabur.setPreferredSize(new java.awt.Dimension(200, 26));
        MnKabur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKaburActionPerformed(evt);
            }
        });
        SetStatus.add(MnKabur);

        MnStatusBelumLengkap.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusBelumLengkap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusBelumLengkap.setForeground(new java.awt.Color(50, 50, 50));
        MnStatusBelumLengkap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusBelumLengkap.setText("Status Belum Lengkap");
        MnStatusBelumLengkap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusBelumLengkap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusBelumLengkap.setName("MnStatusBelumLengkap"); // NOI18N
        MnStatusBelumLengkap.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusBelumLengkap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusBelumLengkapActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusBelumLengkap);

        MnStatusBelumPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusBelumPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusBelumPulang.setForeground(new java.awt.Color(50, 50, 50));
        MnStatusBelumPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusBelumPulang.setText("Belum Pulang");
        MnStatusBelumPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusBelumPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusBelumPulang.setName("MnStatusBelumPulang"); // NOI18N
        MnStatusBelumPulang.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusBelumPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusBelumPulangActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusBelumPulang);

        jPopupMenu1.add(SetStatus);

        MnGanti.setBackground(new java.awt.Color(250, 255, 245));
        MnGanti.setForeground(new java.awt.Color(50, 50, 50));
        MnGanti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGanti.setText("Ganti");
        MnGanti.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGanti.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGanti.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGanti.setName("MnGanti"); // NOI18N

        MnPenjab.setBackground(new java.awt.Color(255, 255, 254));
        MnPenjab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjab.setForeground(new java.awt.Color(50, 50, 50));
        MnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjab.setText("Ganti Jenis Bayar");
        MnPenjab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjab.setName("MnPenjab"); // NOI18N
        MnPenjab.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjabActionPerformed(evt);
            }
        });
        MnGanti.add(MnPenjab);

        MnDiagnosaMasuk.setBackground(new java.awt.Color(255, 255, 204));
        MnDiagnosaMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosaMasuk.setForeground(new java.awt.Color(50, 50, 50));
        MnDiagnosaMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosaMasuk.setText("Diagnosa Awal Sementara");
        MnDiagnosaMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosaMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosaMasuk.setName("MnDiagnosaMasuk"); // NOI18N
        MnDiagnosaMasuk.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDiagnosaMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosaMasukActionPerformed(evt);
            }
        });
        MnGanti.add(MnDiagnosaMasuk);

        MnDiagnosaAkhir.setBackground(new java.awt.Color(255, 255, 254));
        MnDiagnosaAkhir.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosaAkhir.setForeground(new java.awt.Color(50, 50, 50));
        MnDiagnosaAkhir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosaAkhir.setText("Diagnosa Akhir Sementara");
        MnDiagnosaAkhir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosaAkhir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosaAkhir.setName("MnDiagnosaAkhir"); // NOI18N
        MnDiagnosaAkhir.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDiagnosaAkhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosaAkhirActionPerformed(evt);
            }
        });
        MnGanti.add(MnDiagnosaAkhir);

        MnWaktuMasuk.setBackground(new java.awt.Color(255, 255, 204));
        MnWaktuMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnWaktuMasuk.setForeground(new java.awt.Color(50, 50, 50));
        MnWaktuMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnWaktuMasuk.setText("Tanggal & Jam Masuk");
        MnWaktuMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnWaktuMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnWaktuMasuk.setName("MnWaktuMasuk"); // NOI18N
        MnWaktuMasuk.setPreferredSize(new java.awt.Dimension(220, 26));
        MnWaktuMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnWaktuMasukActionPerformed(evt);
            }
        });
        MnGanti.add(MnWaktuMasuk);

        jPopupMenu1.add(MnGanti);

        MnHapusData.setBackground(new java.awt.Color(250, 255, 245));
        MnHapusData.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusData.setText("Hapus Data");
        MnHapusData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusData.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusData.setName("MnHapusData"); // NOI18N
        MnHapusData.setPreferredSize(new java.awt.Dimension(220, 26));

        MnHapusTagihanOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihanOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihanOperasi.setText("Hapus Tagihan Operasi");
        MnHapusTagihanOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTagihanOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTagihanOperasi.setName("MnHapusTagihanOperasi"); // NOI18N
        MnHapusTagihanOperasi.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusTagihanOperasi);

        MnHapusObatOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusObatOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObatOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusObatOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObatOperasi.setText("Hapus Obat Operasi");
        MnHapusObatOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusObatOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusObatOperasi.setName("MnHapusObatOperasi"); // NOI18N
        MnHapusObatOperasi.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusObatOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusObatOperasi);

        MnHapusDataSalah.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusDataSalah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDataSalah.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusDataSalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDataSalah.setText("Hapus Data Salah");
        MnHapusDataSalah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDataSalah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDataSalah.setName("MnHapusDataSalah"); // NOI18N
        MnHapusDataSalah.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusDataSalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDataSalahActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDataSalah);

        jPopupMenu1.add(MnHapusData);

        jSeparator14.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator14.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator14.setName("jSeparator14"); // NOI18N
        jSeparator14.setPreferredSize(new java.awt.Dimension(220, 1));
        jPopupMenu1.add(jSeparator14);

        MnUpdateHari.setBackground(new java.awt.Color(255, 255, 254));
        MnUpdateHari.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUpdateHari.setForeground(new java.awt.Color(50, 50, 50));
        MnUpdateHari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUpdateHari.setText("Update Hari Perawatan");
        MnUpdateHari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUpdateHari.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUpdateHari.setName("MnUpdateHari"); // NOI18N
        MnUpdateHari.setPreferredSize(new java.awt.Dimension(220, 26));
        MnUpdateHari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUpdateHariActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUpdateHari);

        MnPerkiraanBiaya.setBackground(new java.awt.Color(255, 255, 254));
        MnPerkiraanBiaya.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPerkiraanBiaya.setForeground(new java.awt.Color(50, 50, 50));
        MnPerkiraanBiaya.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPerkiraanBiaya.setText("Perkiraan Biaya Ranap");
        MnPerkiraanBiaya.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPerkiraanBiaya.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPerkiraanBiaya.setName("MnPerkiraanBiaya"); // NOI18N
        MnPerkiraanBiaya.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPerkiraanBiaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPerkiraanBiayaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPerkiraanBiaya);

        JamMasuk.setEditable(false);
        JamMasuk.setForeground(new java.awt.Color(255, 255, 255));
        JamMasuk.setHighlighter(null);
        JamMasuk.setName("JamMasuk"); // NOI18N
        JamMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMasukKeyPressed(evt);
            }
        });

        WindowPindahKamar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowPindahKamar.setName("WindowPindahKamar"); // NOI18N
        WindowPindahKamar.setUndecorated(true);
        WindowPindahKamar.setResizable(false);
        WindowPindahKamar.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                WindowPindahKamarWindowActivated(evt);
            }
        });

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Pindah Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(null);

        norawatpindah.setEditable(false);
        norawatpindah.setHighlighter(null);
        norawatpindah.setName("norawatpindah"); // NOI18N
        internalFrame3.add(norawatpindah);
        norawatpindah.setBounds(75, 25, 150, 23);

        TPasienpindah.setEditable(false);
        TPasienpindah.setHighlighter(null);
        TPasienpindah.setName("TPasienpindah"); // NOI18N
        internalFrame3.add(TPasienpindah);
        TPasienpindah.setBounds(359, 25, 299, 23);

        kdkamarpindah.setHighlighter(null);
        kdkamarpindah.setName("kdkamarpindah"); // NOI18N
        kdkamarpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkamarpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(kdkamarpindah);
        kdkamarpindah.setBounds(75, 55, 95, 23);

        ttlbiayapindah.setEditable(false);
        ttlbiayapindah.setText("0");
        ttlbiayapindah.setHighlighter(null);
        ttlbiayapindah.setName("ttlbiayapindah"); // NOI18N
        internalFrame3.add(ttlbiayapindah);
        ttlbiayapindah.setBounds(368, 115, 290, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        internalFrame3.add(jLabel4);
        jLabel4.setBounds(0, 25, 72, 23);

        jLabel20.setText("Kamar :");
        jLabel20.setName("jLabel20"); // NOI18N
        internalFrame3.add(jLabel20);
        jLabel20.setBounds(0, 55, 72, 23);

        TNoRMpindah.setEditable(false);
        TNoRMpindah.setHighlighter(null);
        TNoRMpindah.setName("TNoRMpindah"); // NOI18N
        internalFrame3.add(TNoRMpindah);
        TNoRMpindah.setBounds(227, 25, 130, 23);

        btnKamar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKamar2.setMnemonic('2');
        btnKamar2.setToolTipText("Alt+2");
        btnKamar2.setName("btnKamar2"); // NOI18N
        btnKamar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamar2ActionPerformed(evt);
            }
        });
        internalFrame3.add(btnKamar2);
        btnKamar2.setBounds(428, 55, 28, 23);

        TKdBngsalpindah.setEditable(false);
        TKdBngsalpindah.setName("TKdBngsalpindah"); // NOI18N
        internalFrame3.add(TKdBngsalpindah);
        TKdBngsalpindah.setBounds(172, 55, 82, 23);

        jLabel27.setText("Tanggal :");
        jLabel27.setName("jLabel27"); // NOI18N
        internalFrame3.add(jLabel27);
        jLabel27.setBounds(0, 85, 72, 23);

        cmbDtkpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtkpindah.setName("cmbDtkpindah"); // NOI18N
        internalFrame3.add(cmbDtkpindah);
        cmbDtkpindah.setBounds(508, 85, 62, 23);

        cmbMntpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMntpindah.setName("cmbMntpindah"); // NOI18N
        internalFrame3.add(cmbMntpindah);
        cmbMntpindah.setBounds(441, 85, 62, 23);

        cmbJampindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJampindah.setName("cmbJampindah"); // NOI18N
        internalFrame3.add(cmbJampindah);
        cmbJampindah.setBounds(374, 85, 62, 23);

        TBangsalpindah.setEditable(false);
        TBangsalpindah.setHighlighter(null);
        TBangsalpindah.setName("TBangsalpindah"); // NOI18N
        TBangsalpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBangsalpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(TBangsalpindah);
        TBangsalpindah.setBounds(256, 55, 170, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("X");
        jLabel28.setName("jLabel28"); // NOI18N
        internalFrame3.add(jLabel28);
        jLabel28.setBounds(173, 115, 15, 23);

        TJmlHaripindah.setText("1");
        TJmlHaripindah.setHighlighter(null);
        TJmlHaripindah.setName("TJmlHaripindah"); // NOI18N
        internalFrame3.add(TJmlHaripindah);
        TJmlHaripindah.setBounds(75, 115, 96, 23);

        jLabel29.setText("=");
        jLabel29.setName("jLabel29"); // NOI18N
        internalFrame3.add(jLabel29);
        jLabel29.setBounds(342, 115, 20, 23);

        TSttsKamarpindah.setEditable(false);
        TSttsKamarpindah.setName("TSttsKamarpindah"); // NOI18N
        internalFrame3.add(TSttsKamarpindah);
        TSttsKamarpindah.setBounds(548, 55, 110, 23);

        BtnCloseInpindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseInpindah.setMnemonic('U');
        BtnCloseInpindah.setText("Tutup");
        BtnCloseInpindah.setToolTipText("Alt+U");
        BtnCloseInpindah.setName("BtnCloseInpindah"); // NOI18N
        BtnCloseInpindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInpindahActionPerformed(evt);
            }
        });
        BtnCloseInpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(BtnCloseInpindah);
        BtnCloseInpindah.setBounds(560, 235, 100, 30);

        jLabel30.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel30.setName("jLabel30"); // NOI18N
        internalFrame3.add(jLabel30);
        jLabel30.setBounds(-10, 215, 850, 14);

        BtnSimpanpindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanpindah.setMnemonic('S');
        BtnSimpanpindah.setText("Simpan");
        BtnSimpanpindah.setToolTipText("Alt+S");
        BtnSimpanpindah.setName("BtnSimpanpindah"); // NOI18N
        BtnSimpanpindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanpindahActionPerformed(evt);
            }
        });
        BtnSimpanpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(BtnSimpanpindah);
        BtnSimpanpindah.setBounds(14, 235, 100, 30);

        jLabel31.setText("Stts.Kamar :");
        jLabel31.setName("jLabel31"); // NOI18N
        internalFrame3.add(jLabel31);
        jLabel31.setBounds(444, 55, 100, 23);

        jLabel32.setText("Pilihan :");
        jLabel32.setName("jLabel32"); // NOI18N
        internalFrame3.add(jLabel32);
        jLabel32.setBounds(0, 145, 72, 23);

        TTarifpindah.setEditable(false);
        TTarifpindah.setText("0");
        TTarifpindah.setHighlighter(null);
        TTarifpindah.setName("TTarifpindah"); // NOI18N
        internalFrame3.add(TTarifpindah);
        TTarifpindah.setBounds(188, 115, 160, 23);

        CmbTahunpindah.setName("CmbTahunpindah"); // NOI18N
        internalFrame3.add(CmbTahunpindah);
        CmbTahunpindah.setBounds(209, 85, 100, 23);

        CmbBlnpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        CmbBlnpindah.setName("CmbBlnpindah"); // NOI18N
        internalFrame3.add(CmbBlnpindah);
        CmbBlnpindah.setBounds(142, 85, 62, 23);

        CmbTglpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        CmbTglpindah.setName("CmbTglpindah"); // NOI18N
        internalFrame3.add(CmbTglpindah);
        CmbTglpindah.setBounds(75, 85, 62, 23);

        jLabel35.setText("Jam :");
        jLabel35.setName("jLabel35"); // NOI18N
        internalFrame3.add(jLabel35);
        jLabel35.setBounds(304, 85, 67, 23);

        buttonGroup2.add(Rganti3);
        Rganti3.setSelected(true);
        Rganti3.setText("3. Kamar Inap sebelumnya distatuskan pindah, lama inap dihitung dan pasien dimasukkan Ke Kamar inap yang baru");
        Rganti3.setName("Rganti3"); // NOI18N
        internalFrame3.add(Rganti3);
        Rganti3.setBounds(75, 176, 620, 20);

        jLabel33.setText("Biaya :");
        jLabel33.setName("jLabel33"); // NOI18N
        internalFrame3.add(jLabel33);
        jLabel33.setBounds(0, 115, 72, 23);

        buttonGroup2.add(Rganti2);
        Rganti2.setText("2. Kamar Inap sebelumnya diganti kamarnya dengan Kamar Inap terbaru dan harga kamar menyesuaikan harga baru");
        Rganti2.setName("Rganti2"); // NOI18N
        internalFrame3.add(Rganti2);
        Rganti2.setBounds(75, 159, 620, 20);

        buttonGroup2.add(Rganti1);
        Rganti1.setText("1. Kamar Inap sebelumnya dihapus dan pasien dihitung menginap mulai saat ini (Kamar Inap lama dihapus dari billing)");
        Rganti1.setName("Rganti1"); // NOI18N
        internalFrame3.add(Rganti1);
        Rganti1.setBounds(75, 142, 620, 20);

        buttonGroup2.add(Rganti4);
        Rganti4.setText("4. Seperti nomer 3, Kamar Inap sebelumnya mengikuti harga tertinggi");
        Rganti4.setName("Rganti4"); // NOI18N
        internalFrame3.add(Rganti4);
        Rganti4.setBounds(75, 193, 620, 20);

        WindowPindahKamar.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        WindowCaraBayar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCaraBayar.setName("WindowCaraBayar"); // NOI18N
        WindowCaraBayar.setUndecorated(true);
        WindowCaraBayar.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Jenis Bayar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(510, 30, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(405, 30, 100, 30);

        jLabel17.setText("Jenis Bayar :");
        jLabel17.setName("jLabel17"); // NOI18N
        internalFrame5.add(jLabel17);
        jLabel17.setBounds(0, 32, 77, 23);

        kdpenjab.setHighlighter(null);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        internalFrame5.add(kdpenjab);
        kdpenjab.setBounds(81, 32, 100, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        internalFrame5.add(nmpenjab);
        nmpenjab.setBounds(183, 32, 181, 23);

        btnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBayar.setMnemonic('7');
        btnBayar.setToolTipText("ALt+7");
        btnBayar.setName("btnBayar"); // NOI18N
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });
        internalFrame5.add(btnBayar);
        btnBayar.setBounds(366, 32, 28, 23);

        WindowCaraBayar.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        WindowRanapGabung.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowRanapGabung.setName("WindowRanapGabung"); // NOI18N
        WindowRanapGabung.setUndecorated(true);
        WindowRanapGabung.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ranap Gabung Ibu & Bayi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(null);

        BtnCloseGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseGabung.setMnemonic('U');
        BtnCloseGabung.setText("Tutup");
        BtnCloseGabung.setToolTipText("Alt+U");
        BtnCloseGabung.setName("BtnCloseGabung"); // NOI18N
        BtnCloseGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseGabungActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnCloseGabung);
        BtnCloseGabung.setBounds(510, 70, 100, 30);

        BtnSimpanGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanGabung.setMnemonic('S');
        BtnSimpanGabung.setText("Simpan");
        BtnSimpanGabung.setToolTipText("Alt+S");
        BtnSimpanGabung.setName("BtnSimpanGabung"); // NOI18N
        BtnSimpanGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanGabungActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnSimpanGabung);
        BtnSimpanGabung.setBounds(17, 70, 100, 30);

        jLabel34.setText("No.R.M.Bayi :");
        jLabel34.setName("jLabel34"); // NOI18N
        internalFrame6.add(jLabel34);
        jLabel34.setBounds(2, 30, 87, 23);

        NoRmBayi.setHighlighter(null);
        NoRmBayi.setName("NoRmBayi"); // NOI18N
        NoRmBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRmBayiKeyPressed(evt);
            }
        });
        internalFrame6.add(NoRmBayi);
        NoRmBayi.setBounds(92, 30, 100, 23);

        NmBayi.setEditable(false);
        NmBayi.setName("NmBayi"); // NOI18N
        internalFrame6.add(NmBayi);
        NmBayi.setBounds(193, 30, 350, 23);

        btnPasienRanapGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasienRanapGabung.setMnemonic('7');
        btnPasienRanapGabung.setToolTipText("ALt+7");
        btnPasienRanapGabung.setName("btnPasienRanapGabung"); // NOI18N
        btnPasienRanapGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienRanapGabungActionPerformed(evt);
            }
        });
        internalFrame6.add(btnPasienRanapGabung);
        btnPasienRanapGabung.setBounds(546, 30, 28, 23);

        BtnHapusGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusGabung.setMnemonic('H');
        BtnHapusGabung.setText("Hapus");
        BtnHapusGabung.setToolTipText("Alt+H");
        BtnHapusGabung.setName("BtnHapusGabung"); // NOI18N
        BtnHapusGabung.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapusGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusGabungActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnHapusGabung);
        BtnHapusGabung.setBounds(123, 70, 100, 30);

        NoRawatGabung.setHighlighter(null);
        NoRawatGabung.setName("NoRawatGabung"); // NOI18N
        internalFrame6.add(NoRawatGabung);
        NoRawatGabung.setBounds(230, 220, 190, 23);

        btnPasienRanapGabung1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasienRanapGabung1.setMnemonic('7');
        btnPasienRanapGabung1.setToolTipText("ALt+7");
        btnPasienRanapGabung1.setName("btnPasienRanapGabung1"); // NOI18N
        btnPasienRanapGabung1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienRanapGabung1ActionPerformed(evt);
            }
        });
        internalFrame6.add(btnPasienRanapGabung1);
        btnPasienRanapGabung1.setBounds(576, 30, 28, 23);

        WindowRanapGabung.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        DlgSakit2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit2.setName("DlgSakit2"); // NOI18N
        DlgSakit2.setUndecorated(true);
        DlgSakit2.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Keterangan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa4.setName("panelBiasa4"); // NOI18N
        panelBiasa4.setLayout(null);

        jLabel38.setText("Nomor Surat Keterangan :");
        jLabel38.setName("jLabel38"); // NOI18N
        panelBiasa4.add(jLabel38);
        jLabel38.setBounds(7, 10, 140, 23);

        BtnPrint5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint5.setMnemonic('T');
        BtnPrint5.setText("Cetak");
        BtnPrint5.setToolTipText("Alt+T");
        BtnPrint5.setName("BtnPrint5"); // NOI18N
        BtnPrint5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnPrint5);
        BtnPrint5.setBounds(10, 80, 100, 30);

        BtnKeluar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar4.setMnemonic('K');
        BtnKeluar4.setText("Keluar");
        BtnKeluar4.setToolTipText("Alt+K");
        BtnKeluar4.setName("BtnKeluar4"); // NOI18N
        BtnKeluar4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar4ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnKeluar4);
        BtnKeluar4.setBounds(430, 80, 100, 30);

        NomorSurat.setHighlighter(null);
        NomorSurat.setName("NomorSurat"); // NOI18N
        panelBiasa4.add(NomorSurat);
        NomorSurat.setBounds(150, 10, 370, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnSeek5);
        BtnSeek5.setBounds(492, 40, 28, 23);

        CrDokter3.setEditable(false);
        CrDokter3.setName("CrDokter3"); // NOI18N
        CrDokter3.setPreferredSize(new java.awt.Dimension(300, 23));
        panelBiasa4.add(CrDokter3);
        CrDokter3.setBounds(150, 40, 340, 23);

        jLabel36.setText("Dokter Png. Jawab :");
        jLabel36.setName("jLabel36"); // NOI18N
        jLabel36.setPreferredSize(new java.awt.Dimension(60, 23));
        panelBiasa4.add(jLabel36);
        jLabel36.setBounds(7, 40, 140, 23);

        internalFrame7.add(panelBiasa4, java.awt.BorderLayout.CENTER);

        DlgSakit2.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        DlgCatatan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgCatatan.setName("DlgCatatan"); // NOI18N
        DlgCatatan.setUndecorated(true);
        DlgCatatan.setResizable(false);

        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setWarnaAtas(new java.awt.Color(255, 51, 51));
        internalFrame8.setWarnaBawah(new java.awt.Color(255, 102, 102));
        internalFrame8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                internalFrame8MouseClicked(evt);
            }
        });
        internalFrame8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        LabelCatatan.setForeground(new java.awt.Color(255, 255, 255));
        LabelCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelCatatan.setText("Catatan");
        LabelCatatan.setName("LabelCatatan"); // NOI18N
        LabelCatatan.setPreferredSize(new java.awt.Dimension(580, 23));
        LabelCatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelCatatanMouseClicked(evt);
            }
        });
        internalFrame8.add(LabelCatatan);

        DlgCatatan.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        WindowDiagnosaMasuk.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowDiagnosaMasuk.setBackground(new java.awt.Color(153, 204, 255));
        WindowDiagnosaMasuk.setName("WindowDiagnosaMasuk"); // NOI18N
        WindowDiagnosaMasuk.setUndecorated(true);
        WindowDiagnosaMasuk.setResizable(false);

        internalFrame9.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "::[ Ganti Diagnosa Awal Sementara ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame9.setName("internalFrame9"); // NOI18N

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });

        BtnSimpan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan5.setMnemonic('S');
        BtnSimpan5.setText("Simpan");
        BtnSimpan5.setToolTipText("Alt+S");
        BtnSimpan5.setName("BtnSimpan5"); // NOI18N
        BtnSimpan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan5ActionPerformed(evt);
            }
        });

        jLabel41.setText("Diagnosa Awal :");
        jLabel41.setName("jLabel41"); // NOI18N

        DiagnosaAwalSementara.setHighlighter(null);
        DiagnosaAwalSementara.setName("DiagnosaAwalSementara"); // NOI18N
        DiagnosaAwalSementara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaAwalSementaraKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout internalFrame9Layout = new javax.swing.GroupLayout(internalFrame9);
        internalFrame9.setLayout(internalFrame9Layout);
        internalFrame9Layout.setHorizontalGroup(
            internalFrame9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DiagnosaAwalSementara, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnSimpan5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnCloseIn5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        internalFrame9Layout.setVerticalGroup(
            internalFrame9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame9Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(internalFrame9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DiagnosaAwalSementara, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnSimpan5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnCloseIn5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        WindowDiagnosaMasuk.getContentPane().add(internalFrame9, java.awt.BorderLayout.CENTER);

        WindowDiagnosaAkhir.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowDiagnosaAkhir.setName("WindowDiagnosaAkhir"); // NOI18N
        WindowDiagnosaAkhir.setUndecorated(true);
        WindowDiagnosaAkhir.setResizable(false);

        internalFrame10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "::[ Ganti Diagnosa Akhir Sementara ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame10.setName("internalFrame10"); // NOI18N

        BtnCloseIn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn6.setMnemonic('U');
        BtnCloseIn6.setText("Tutup");
        BtnCloseIn6.setToolTipText("Alt+U");
        BtnCloseIn6.setName("BtnCloseIn6"); // NOI18N
        BtnCloseIn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn6ActionPerformed(evt);
            }
        });

        BtnSimpan6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan6.setMnemonic('S');
        BtnSimpan6.setText("Simpan");
        BtnSimpan6.setToolTipText("Alt+S");
        BtnSimpan6.setName("BtnSimpan6"); // NOI18N
        BtnSimpan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan6ActionPerformed(evt);
            }
        });

        jLabel42.setText("Diagnosa Akhir :");
        jLabel42.setName("jLabel42"); // NOI18N

        DiagnosaAkhirSementara.setHighlighter(null);
        DiagnosaAkhirSementara.setName("DiagnosaAkhirSementara"); // NOI18N
        DiagnosaAkhirSementara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaAkhirSementaraKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout internalFrame10Layout = new javax.swing.GroupLayout(internalFrame10);
        internalFrame10.setLayout(internalFrame10Layout);
        internalFrame10Layout.setHorizontalGroup(
            internalFrame10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DiagnosaAkhirSementara, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnSimpan6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnCloseIn6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        internalFrame10Layout.setVerticalGroup(
            internalFrame10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(internalFrame10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DiagnosaAkhirSementara, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnSimpan6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnCloseIn6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        WindowDiagnosaAkhir.getContentPane().add(internalFrame10, java.awt.BorderLayout.CENTER);

        WindowInputCoderBPJS.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInputCoderBPJS.setIconImage(null);
        WindowInputCoderBPJS.setName("WindowInputCoderBPJS"); // NOI18N
        WindowInputCoderBPJS.setUndecorated(true);
        WindowInputCoderBPJS.setResizable(false);

        internalFrame11.setBackground(new java.awt.Color(204, 255, 255));
        internalFrame11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "::[ Input Coder BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame11.setName("internalFrame11"); // NOI18N
        internalFrame11.setOpaque(false);

        BtnCloseCoder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseCoder.setMnemonic('U');
        BtnCloseCoder.setText("Tutup");
        BtnCloseCoder.setToolTipText("Alt+U");
        BtnCloseCoder.setName("BtnCloseCoder"); // NOI18N
        BtnCloseCoder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseCoderActionPerformed(evt);
            }
        });

        BtnSimpanCoder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanCoder.setMnemonic('S');
        BtnSimpanCoder.setText("Simpan");
        BtnSimpanCoder.setToolTipText("Alt+S");
        BtnSimpanCoder.setName("BtnSimpanCoder"); // NOI18N
        BtnSimpanCoder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanCoderActionPerformed(evt);
            }
        });

        scrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        scrollPane1.setName("scrollPane1"); // NOI18N

        tableCoder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableCoder.setName("tableCoder"); // NOI18N
        tableCoder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCoderMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tableCoder);

        label1.setText("Riwayat Coder BPJS");
        label1.setName("label1"); // NOI18N

        jXTaskPane1.setTitle("Tambah Data");
        jXTaskPane1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jXTaskPane1.setName("jXTaskPane1"); // NOI18N
        jXTaskPane1.getContentPane().setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);

        jLabel43.setText("Hak Kelas 1 :");
        jLabel43.setName("jLabel43"); // NOI18N

        txtHak1.setText("0");
        txtHak1.setHighlighter(null);
        txtHak1.setName("txtHak1"); // NOI18N
        txtHak1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHak1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHak1KeyReleased(evt);
            }
        });

        jLabel44.setText("Hak Kelas 2 :");
        jLabel44.setName("jLabel44"); // NOI18N

        txtHak2.setText("0");
        txtHak2.setHighlighter(null);
        txtHak2.setName("txtHak2"); // NOI18N
        txtHak2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHak2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHak2KeyReleased(evt);
            }
        });

        jLabel45.setText("Hak Kelas 3 :");
        jLabel45.setName("jLabel45"); // NOI18N

        txtHak3.setText("0");
        txtHak3.setHighlighter(null);
        txtHak3.setName("txtHak3"); // NOI18N
        txtHak3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHak3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHak3KeyReleased(evt);
            }
        });

        jLabel46.setText("Selisih :");
        jLabel46.setName("jLabel46"); // NOI18N

        txtSelisih.setText("0");
        txtSelisih.setHighlighter(null);
        txtSelisih.setName("txtSelisih"); // NOI18N
        txtSelisih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSelisihKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSelisihKeyReleased(evt);
            }
        });

        jLabel47.setText("Penjaminan :");
        jLabel47.setName("jLabel47"); // NOI18N

        txtPenjaminan.setText("0");
        txtPenjaminan.setHighlighter(null);
        txtPenjaminan.setName("txtPenjaminan"); // NOI18N
        txtPenjaminan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPenjaminanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPenjaminanKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSelisih, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(txtHak1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHak2, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(txtPenjaminan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHak3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHak1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHak2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHak3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSelisih, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPenjaminan, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jXTaskPane1.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        BtnSimpanCoder1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edittrash.png"))); // NOI18N
        BtnSimpanCoder1.setMnemonic('S');
        BtnSimpanCoder1.setText("Hapus");
        BtnSimpanCoder1.setToolTipText("Alt+S");
        BtnSimpanCoder1.setName("BtnSimpanCoder1"); // NOI18N
        BtnSimpanCoder1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanCoder1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout internalFrame11Layout = new javax.swing.GroupLayout(internalFrame11);
        internalFrame11.setLayout(internalFrame11Layout);
        internalFrame11Layout.setHorizontalGroup(
            internalFrame11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(internalFrame11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, internalFrame11Layout.createSequentialGroup()
                        .addComponent(BtnSimpanCoder, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(BtnSimpanCoder1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnCloseCoder, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXTaskPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        internalFrame11Layout.setVerticalGroup(
            internalFrame11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame11Layout.createSequentialGroup()
                .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnSimpanCoder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnCloseCoder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnSimpanCoder1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        WindowInputCoderBPJS.getContentPane().add(internalFrame11, java.awt.BorderLayout.CENTER);

        txtIdCoder.setName("txtIdCoder"); // NOI18N

        WindowWaktuMasuk.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowWaktuMasuk.setName("WindowWaktuMasuk"); // NOI18N
        WindowWaktuMasuk.setUndecorated(true);
        WindowWaktuMasuk.setResizable(false);

        internalFrame12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "::[ Ganti Tanggal & Waktu Masuk ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame12.setName("internalFrame12"); // NOI18N

        BtnCloseIn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn7.setMnemonic('U');
        BtnCloseIn7.setText("Tutup");
        BtnCloseIn7.setToolTipText("Alt+U");
        BtnCloseIn7.setName("BtnCloseIn7"); // NOI18N
        BtnCloseIn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn7ActionPerformed(evt);
            }
        });

        BtnSimpan7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnSimpan7.setMnemonic('S');
        BtnSimpan7.setText("Ganti");
        BtnSimpan7.setToolTipText("Alt+S");
        BtnSimpan7.setName("BtnSimpan7"); // NOI18N
        BtnSimpan7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan7ActionPerformed(evt);
            }
        });

        jLabel8.setText("Tgl. Reg. :");
        jLabel8.setName("jLabel8"); // NOI18N

        dateMasuk.setName("dateMasuk"); // NOI18N
        dateMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateMasukKeyPressed(evt);
            }
        });

        jLabel9.setText("Jam :");
        jLabel9.setName("jLabel9"); // NOI18N

        ComboJam.setEditable(true);
        ComboJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        ComboJam.setName("ComboJam"); // NOI18N
        ComboJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboJamKeyPressed(evt);
            }
        });

        ComboMenit.setEditable(true);
        ComboMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        ComboMenit.setName("ComboMenit"); // NOI18N
        ComboMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboMenitKeyPressed(evt);
            }
        });

        ComboDetik.setEditable(true);
        ComboDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        ComboDetik.setName("ComboDetik"); // NOI18N
        ComboDetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboDetikKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout internalFrame12Layout = new javax.swing.GroupLayout(internalFrame12);
        internalFrame12.setLayout(internalFrame12Layout);
        internalFrame12Layout.setHorizontalGroup(
            internalFrame12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(internalFrame12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(internalFrame12Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(dateMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ComboJam, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(ComboMenit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(ComboDetik, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(internalFrame12Layout.createSequentialGroup()
                        .addComponent(BtnSimpan7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnCloseIn7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        internalFrame12Layout.setVerticalGroup(
            internalFrame12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(internalFrame12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboJam, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboMenit, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboDetik, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(internalFrame12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnSimpan7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnCloseIn7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        WindowWaktuMasuk.getContentPane().add(internalFrame12, java.awt.BorderLayout.CENTER);
        internalFrame12.getAccessibleContext().setAccessibleName("::[ Ganti Tanggal & Jam Masuk ]::");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 150));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnIn.setMnemonic('M');
        BtnIn.setText("Masuk");
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

        BtnOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnOut.setMnemonic('U');
        BtnOut.setText("Pulang");
        BtnOut.setToolTipText("Alt+U");
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

        btnPindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        btnPindah.setMnemonic('P');
        btnPindah.setText("Pindah");
        btnPindah.setToolTipText("Alt+P");
        btnPindah.setName("btnPindah"); // NOI18N
        btnPindah.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPindahActionPerformed(evt);
            }
        });
        btnPindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPindahKeyPressed(evt);
            }
        });
        panelGlass10.add(btnPindah);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(105, 23));
        panelGlass10.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(LCount);

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
        panelGlass11.setPreferredSize(new java.awt.Dimension(50, 50));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        jLabel21.setText("Kamar :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass11.add(jLabel21);

        BangsalCari.setName("BangsalCari"); // NOI18N
        BangsalCari.setPreferredSize(new java.awt.Dimension(230, 23));
        BangsalCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BangsalCariKeyPressed(evt);
            }
        });
        panelGlass11.add(BangsalCari);

        btnBangsalCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsalCari.setMnemonic('3');
        btnBangsalCari.setToolTipText("Alt+3");
        btnBangsalCari.setName("btnBangsalCari"); // NOI18N
        btnBangsalCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBangsalCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsalCariActionPerformed(evt);
            }
        });
        btnBangsalCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBangsalCariKeyPressed(evt);
            }
        });
        panelGlass11.add(btnBangsalCari);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass11.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(280, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass11.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 45));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        R1.setBackground(new java.awt.Color(240, 250, 230));
        R1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Belum Pulang");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelCari.add(R1);

        R2.setBackground(new java.awt.Color(240, 250, 230));
        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setText("Tgl.Masuk :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelCari.add(R2);

        DTPCari1.setName("DTPCari1"); // NOI18N
        panelCari.add(DTPCari1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel22);

        DTPCari2.setName("DTPCari2"); // NOI18N
        panelCari.add(DTPCari2);

        R3.setBackground(new java.awt.Color(240, 250, 230));
        R3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R3);
        R3.setText("Pulang :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(75, 23));
        panelCari.add(R3);

        DTPCari3.setName("DTPCari3"); // NOI18N
        panelCari.add(DTPCari3);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("s.d");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel25);

        DTPCari4.setName("DTPCari4"); // NOI18N
        panelCari.add(DTPCari4);

        chkBaby.setText("Baby Rooming In");
        chkBaby.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkBaby.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkBaby.setName("chkBaby"); // NOI18N
        chkBaby.setPreferredSize(new java.awt.Dimension(130, 18));
        panelCari.add(chkBaby);

        PanelCariUtama.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        Scroll.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamIn.setAutoCreateRowSorter(true);
        tbKamIn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbKamIn.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbKamIn.setComponentPopupMenu(jPopupMenu1);
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
        });
        Scroll.setViewportView(tbKamIn);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        jLabel5.setText("No. Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel5);

        TNoRw1.setEditable(false);
        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        TNoRw1.setPreferredSize(new java.awt.Dimension(130, 23));
        panelGlass9.add(TNoRw1);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(215, 23));
        panelGlass9.add(TPasien1);

        jLabel37.setText("Stts.Bayar :");
        jLabel37.setName("jLabel37"); // NOI18N
        jLabel37.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel37);

        cmbStatusBayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Bayar", "Belum Bayar" }));
        cmbStatusBayar.setName("cmbStatusBayar"); // NOI18N
        cmbStatusBayar.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass9.add(cmbStatusBayar);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void ppPasienCoronaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPasienCoronaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppPasienCoronaActionPerformed
    private void ppPerawatanCoronaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPerawatanCoronaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppPerawatanCoronaActionPerformed
    private void MnDiagnosaMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosaMasukActionPerformed
        // TODO add your handling code here:
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                WindowDiagnosaMasuk.setLocationRelativeTo(internalFrame1);
                WindowDiagnosaMasuk.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnDiagnosaMasukActionPerformed

    private void MnDiagnosaAkhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosaAkhirActionPerformed
        // TODO add your handling code here:
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                WindowDiagnosaAkhir.setLocationRelativeTo(internalFrame1);
                WindowDiagnosaAkhir.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnDiagnosaAkhirActionPerformed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        // TODO add your handling code here:
        WindowDiagnosaMasuk.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void BtnSimpan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan5ActionPerformed
        // TODO add your handling code here:
        if (norawat.getText().trim().equals("")) {
            Valid.textKosong(norawat, "No.Rawat");
        } else {
            Sequel.mengedit("kamar_inap", "no_rawat=?", " diagnosa_awal=?", 2, new String[]{DiagnosaAwalSementara.getText(), norawat.getText()});
            tampil();
            WindowDiagnosaMasuk.dispose();
        }
    }//GEN-LAST:event_BtnSimpan5ActionPerformed

    private void DiagnosaAwalSementaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaAwalSementaraKeyPressed
        // TODO add your handling code here:
        Valid.pindah(evt, BtnCloseIn5, BtnSimpan5);
    }//GEN-LAST:event_DiagnosaAwalSementaraKeyPressed

    private void BtnCloseIn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn6ActionPerformed
        // TODO add your handling code here:
        WindowDiagnosaAkhir.dispose();
    }//GEN-LAST:event_BtnCloseIn6ActionPerformed

    private void BtnSimpan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan6ActionPerformed
        // TODO add your handling code here:
        DiagnosaAkhirSementara.setText(diagnosaakhir.getText());
        if (norawat.getText().trim().equals("")) {
            Valid.textKosong(norawat, "No.Rawat");
        } else {
//            String string = diagnosaakhir.getText();
//            String[] parts = string.split(",");
//            for (int j = 0; j < parts.length; j++) {
//                Sequel.mengedit("diagnosa_pasien", "no_rawat=? and kd_penyakit=?", " kd_penyakit=?", 3, new String[]{parts[j],parts[j], norawat.getText()});
//            }

            Sequel.mengedit("kamar_inap", "no_rawat=?", " diagnosa_akhir=?", 2, new String[]{DiagnosaAkhirSementara.getText(), norawat.getText()});
            tampil();
            WindowDiagnosaAkhir.dispose();
        }
    }//GEN-LAST:event_BtnSimpan6ActionPerformed

    private void DiagnosaAkhirSementaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaAkhirSementaraKeyPressed
        // TODO add your handling code here:
        Valid.pindah(evt, BtnCloseIn6, BtnSimpan6);
    }//GEN-LAST:event_DiagnosaAkhirSementaraKeyPressed

    private void ppInputCoderBpjsBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppInputCoderBpjsBtnPrintActionPerformed
        // TODO add your handling code here:
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                WindowInputCoderBPJS.setSize(810, 358);
                WindowInputCoderBPJS.setLocationRelativeTo(internalFrame1);
                WindowInputCoderBPJS.setVisible(true);
                tampilCoder();
            }
        }
    }//GEN-LAST:event_ppInputCoderBpjsBtnPrintActionPerformed

    private void BtnCloseCoderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseCoderActionPerformed
        // TODO add your handling code here:
        WindowInputCoderBPJS.dispose();
    }//GEN-LAST:event_BtnCloseCoderActionPerformed

    private void BtnSimpanCoderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanCoderActionPerformed
        // TODO add your handling code here:
        if (norawat.getText().trim().equals("")) {
            Valid.textKosong(norawat, "No.Rawat");
        } else if (diagnosaakhir.getText().trim().equals("-") || diagnosaakhir.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Diagnosa akhir masih kosong.");
        } else {
            if (!txtHak1.getText().trim().equals("0") || !txtHak2.getText().trim().equals("0") || !txtHak3.getText().trim().equals("0")) {
                Sequel.menyimpan("coderbpjs", "?,?,?,?,?,?,?,?,?", "coder bpjs", 9,
                        new String[]{null, txtHak1.getText().replace(".", "").replace(",", ""), txtHak2.getText().replace(".", "").replace(",", ""),
                            txtHak3.getText().replace(".", "").replace(",", ""), norawat.getText(), Valid.SetDateTimeToString(new Date()),
                            diagnosaakhir.getText(), txtSelisih.getText().replace(".", "").replace(",", ""), txtPenjaminan.getText().replace(".", "").replace(",", "")});
                //WindowInputCoderBPJS.dispose();
                emptyText();
                tampilCoder();
            } else {
                txtHak1.requestFocus();
            }
        }
    }//GEN-LAST:event_BtnSimpanCoderActionPerformed

    private void txtHak1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHak1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHak1KeyPressed

    private void txtHak2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHak2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHak2KeyPressed

    private void txtHak3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHak3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHak3KeyPressed

    private void txtHak1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHak1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHak1KeyReleased

    private void txtHak2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHak2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHak2KeyReleased

    private void txtHak3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHak3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHak3KeyReleased

    private void txtSelisihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSelisihKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSelisihKeyPressed

    private void txtSelisihKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSelisihKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSelisihKeyReleased

    private void txtPenjaminanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPenjaminanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPenjaminanKeyPressed

    private void txtPenjaminanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPenjaminanKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPenjaminanKeyReleased

    private void BtnSimpanCoder1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanCoder1ActionPerformed
        // TODO add your handling code here:
        if (!txtIdCoder.getText().isEmpty()) {
            Sequel.meghapus("coderbpjs", "id_coder", txtIdCoder.getText());
            tampilCoder();
            emptyText();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Pilih data yang akan di hapus.");
        }
    }//GEN-LAST:event_BtnSimpanCoder1ActionPerformed

    private void tableCoderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCoderMouseClicked
        // TODO add your handling code here:
        getDataCoder();
    }//GEN-LAST:event_tableCoderMouseClicked

    private void MnWaktuMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnWaktuMasukActionPerformed
        // TODO add your handling code here:
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!", "Peringatan", JOptionPane.ERROR_MESSAGE);
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!", "Peringatan", JOptionPane.ERROR_MESSAGE);
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                DecimalFormat formatter = new DecimalFormat("00");
                Date cal = new Date();
                dateMasuk.setDate(cal);
                String jam = formatter.format(cal.getHours());
                String menit = formatter.format(cal.getMinutes());
                String detik = formatter.format(cal.getSeconds());
                ComboJam.setSelectedItem(jam);
                ComboMenit.setSelectedItem(menit);
                ComboDetik.setSelectedItem(detik);
                WindowWaktuMasuk.setSize(450, 120);
                WindowWaktuMasuk.setLocationRelativeTo(internalFrame1);
                WindowWaktuMasuk.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnWaktuMasukActionPerformed

    private void BtnCloseIn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn7ActionPerformed
        // TODO add your handling code here:
        WindowWaktuMasuk.dispose();
    }//GEN-LAST:event_BtnCloseIn7ActionPerformed

    private void BtnSimpan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan7ActionPerformed
        // TODO add your handling code here:
        if (norawat.getText().trim().equals("")) {
            Valid.textKosong(norawat, "No.Rawat");
        } else {
            Sequel.mengedit("kamar_inap", "no_rawat=?", " tgl_masuk=?, jam_masuk=?", 3,
                    new String[]{
                        Valid.SetDateToString(dateMasuk.getDate()),
                        ComboJam.getSelectedItem() + ":" + ComboMenit.getSelectedItem() + ":" + ComboDetik.getSelectedItem(),
                        norawat.getText()
                    });
            tampil();
            WindowWaktuMasuk.dispose();
        }
    }//GEN-LAST:event_BtnSimpan7ActionPerformed

    private void dateMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateMasukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateMasukKeyPressed

    private void ComboJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboJamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboJamKeyPressed

    private void ComboMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboMenitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboMenitKeyPressed

    private void ComboDetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboDetikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboDetikKeyPressed

    private void MnSuratKematianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratKematianActionPerformed
        // TODO add your handling code here:
        i = tbKamIn.getSelectedRow();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TCari.getText().trim().equals("") || TNoRM.getText().trim().equals("")) {
            BtnCariActionPerformed(evt);
        }
        if (!tabMode.getValueAt(i, 16).equals("Meninggal")) {
            JOptionPane.showMessageDialog(rootPane, "Status Pasien Belum meninggal", "Informasi", JOptionPane.OK_OPTION);
        } else if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            String norm = Sequel.cariIsi("Select no_rkm_medis from pasien_mati where no_rkm_medis='" + TNoRM.getText() + "'");
            if (!norm.trim().isEmpty()) {
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptSuratKematian.jasper", "report", "::[ Surat Kematian ]::",
                        "select tanggal,jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "
                        + "pasien.umur,pasien.alamat,jk,tmp_lahir,hari,tgl_lahir,gol_darah,stts_nikah, "
                        + "agama,keterangan,nomor from pasien_mati,pasien "
                        + "where pasien_mati.no_rkm_medis=pasien.no_rkm_medis "
                        + "and pasien_mati.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            } else {
                int conf = JOptionPane.showConfirmDialog(rootPane, "Data Pasien meninggal belum ada\nApakah mau di tambahkan?", "Pilihan", JOptionPane.OK_CANCEL_OPTION);
                if (conf == 0) {
                    pasienMeninggal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    pasienMeninggal.setLocationRelativeTo(internalFrame1);
                    pasienMeninggal.setNoRm(TNoRM.getText());
                    pasienMeninggal.tampil();
                    pasienMeninggal.setVisible(true);
                }
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSuratKematianActionPerformed

    private void MnSJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSJPActionPerformed
        // TODO add your handling code here: ini nanti setelah rawat jalan

    }//GEN-LAST:event_MnSJPActionPerformed

    private void getDataCoder() {
        if (tableCoder.getSelectedRow() != -1) {
            txtHak1.setText(tableCoder.getValueAt(tableCoder.getSelectedRow(), 2).toString().replace(".", ""));
            txtHak2.setText(tableCoder.getValueAt(tableCoder.getSelectedRow(), 3).toString().replace(".", ""));
            txtHak3.setText(tableCoder.getValueAt(tableCoder.getSelectedRow(), 4).toString().replace(".", ""));
            txtSelisih.setText(tableCoder.getValueAt(tableCoder.getSelectedRow(), 5).toString().replace(".", ""));
            txtPenjaminan.setText(tableCoder.getValueAt(tableCoder.getSelectedRow(), 6).toString().replace(".", ""));
            txtIdCoder.setText(tableCoder.getValueAt(tableCoder.getSelectedRow(), 7).toString());
            //JOptionPane.showMessageDialog(rootPane, tableCoder.getValueAt(tableCoder.getSelectedRow(), 7).toString());
        }
    }

    private void norawatKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, norawat.getText());
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnRegActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCloseIn, kdkamar);
        }
    }

    private void kdkamarKeyPressed(java.awt.event.KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
                i = 1;
                isKmr();
                break;
            case KeyEvent.VK_ENTER:
                CmbTahun.requestFocus();
                isKmr();
                break;
            case KeyEvent.VK_PAGE_UP:
                norawat.requestFocus();
                isKmr();
                break;
            case KeyEvent.VK_UP:
                btnKamarActionPerformed(null);
                break;
            default:
                break;
        }
    }

    private void ttlbiayaKeyPressed(java.awt.event.KeyEvent evt) {
        // Valid.pindah(evt,TKdOb,BtnSimpan);
    }

    private void btnRegActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRegActionPerformed
        akses.setform("DlgKamarInap");
        reg.emptTeks();
        reg.isCek();
        reg.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        reg.setLocationRelativeTo(internalFrame1);
        reg.setVisible(true);
    }// GEN-LAST:event_btnRegActionPerformed

    private void btnRegKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnRegKeyPressed
        Valid.pindah(evt, norawat, kdkamar);
    }// GEN-LAST:event_btnRegKeyPressed

    private void btnKamarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnKamarActionPerformed
        akses.setform("DlgKamarInap");
        i = 1;
        kamar.load();
        kamar.isCek();
        kamar.emptTeks();
        kamar.tampil();
        kamar.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kamar.setLocationRelativeTo(internalFrame1);
        kamar.setVisible(true);
    }// GEN-LAST:event_btnKamarActionPerformed

    private void btnKamarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnKamarKeyPressed
        Valid.pindah(evt, kdkamar, CmbTahun);
    }// GEN-LAST:event_btnKamarKeyPressed

    private void BtnInActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnInActionPerformed
        norawat.setEditable(true);
        kdkamar.setEditable(true);
        diagnosaawal.setEditable(true);
        diagnosaakhir.setVisible(false);
        btnDiagnosa.setVisible(false);
        cmbStatus.setVisible(false);
        jLabel26.setVisible(false);
        jLabel23.setVisible(false);
        LblStts.setText("Masuk/Check In");
        btnReg.setEnabled(true);
        btnKamar.setEnabled(true);
        emptTeks();
        lama = Sequel.cariIsiAngka("select lamajam from set_jam_minimal");
        hariawal = Sequel.cariIsi("select hariawal from set_jam_minimal");

        WindowInputKamar.setLocationRelativeTo(internalFrame1);
        WindowInputKamar.setVisible(true);
    }// GEN-LAST:event_BtnInActionPerformed

    private void BtnInKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnInKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnInActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnOut);
        }
    }// GEN-LAST:event_BtnInKeyPressed

    private void BtnOutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnOutActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane,
                    "Maaf, Silahkan anda pilih dulu data inap pasien yang mau pulang dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else if (TOut.getText().trim().length() > 0) {
            JOptionPane.showMessageDialog(rootPane,
                    "Maaf, pasien ini sudah pulang pada tanggal " + TOut.getText() + " ...!!!");
            emptTeks();
            tbKamIn.requestFocus();
        } else if ((TOut.getText().length() == 0) && (norawat.getText().length() > 0)) {
            norawat.setEditable(false);
            kdkamar.setEditable(false);
            i = 1;
            isKmr();
            diagnosaawal.setEditable(false);
            diagnosaakhir.setVisible(true);
            btnDiagnosa.setVisible(true);
            jLabel23.setVisible(true);
            diagnosaakhir.setText("");
            cmbStatus.setVisible(true);
            jLabel26.setVisible(true);
            lama = Sequel.cariIsiAngka("select lamajam from set_jam_minimal");
            hariawal = Sequel.cariIsi("select hariawal from set_jam_minimal");
            LblStts.setText("Pulang/Check Out");

            btnReg.setEnabled(false);
            btnKamar.setEnabled(false);
            date = new Date();
            now = dateFormat.format(date);
            CmbTahun.setSelectedItem(now.substring(0, 4));
            CmbBln.setSelectedItem(now.substring(5, 7));
            CmbTgl.setSelectedItem(now.substring(8, 10));
            cmbJam.setSelectedItem(now.substring(11, 13));
            cmbMnt.setSelectedItem(now.substring(14, 16));
            cmbDtk.setSelectedItem(now.substring(17, 19));
            tglmasuk = TIn.getText();
            jammasuk = JamMasuk.getText();
            if (hariawal.equals("Yes")) {
                Sequel.cariIsi("select (if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem()
                        + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":"
                        + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " "
                        + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-"
                        + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem()
                        + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('"
                        + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('"
                        + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem()
                        + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                        + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "'))+1) as lama",
                        TJmlHari);
            } else {
                Sequel.cariIsi("select (if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem()
                        + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":"
                        + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " "
                        + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-"
                        + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem()
                        + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('"
                        + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('"
                        + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem()
                        + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                        + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "'))) as lama",
                        TJmlHari);
            }

            // Sequel.cariIsi("select
            // (if(to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+"
            // "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+"
            // "+jammasuk+"')=0,if(time_to_sec('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+"
            // "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-time_to_sec('"+tglmasuk+"
            // "+jammasuk+"')>(3600*"+lama+"),1,0),to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+"
            // "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+"
            // "+jammasuk+"'))+1) as lama",TJmlHari);
            norawat.requestFocus();
            isjml();
            WindowInputKamar.setLocationRelativeTo(internalFrame1);
            WindowInputKamar.setVisible(true);
        }
    }// GEN-LAST:event_BtnOutActionPerformed

    private void BtnOutKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnOutKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnOutActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnIn, btnPindah);
        }
    }// GEN-LAST:event_BtnOutKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInputKamar.dispose();
        dispose();
    }// GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            WindowInputKamar.dispose();
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
    }// GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnPrintActionPerformed
        if (!TCari.getText().trim().equals("")) {
            BtnCariActionPerformed(evt);
        }
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane,
                    "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            if (R1.isSelected() == true) {
                kmr = " stts_pulang='-' and reg_periksa.status_bayar like '%"
                        + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                if (!BangsalCari.getText().equals("")) {
                    kmr = " stts_pulang='-' and bangsal.nm_bangsal='" + BangsalCari.getText()
                            + "' and reg_periksa.status_bayar like '%"
                            + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                }
            } else if (R2.isSelected() == true) {
                kmr = " kamar_inap.tgl_masuk between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '"
                        + Valid.SetDateToString(DTPCari2.getDate()) + "' and reg_periksa.status_bayar like '%"
                        + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                if (!BangsalCari.getText().equals("")) {
                    kmr = " kamar_inap.tgl_masuk between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '"
                            + Valid.SetDateToString(DTPCari2.getDate()) + "' and bangsal.nm_bangsal='"
                            + BangsalCari.getText() + "' and reg_periksa.status_bayar like '%"
                            + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                }
            } else if (R3.isSelected() == true) {
                kmr = " kamar_inap.tgl_keluar between '" + Valid.SetDateToString(DTPCari3.getDate()) + "' and '"
                        + Valid.SetDateToString(DTPCari4.getDate()) + "' and reg_periksa.status_bayar like '%"
                        + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                if (!BangsalCari.getText().equals("")) {
                    kmr = " kamar_inap.tgl_keluar between '" + Valid.SetDateToString(DTPCari3.getDate()) + "' and '"
                            + Valid.SetDateToString(DTPCari4.getDate()) + "' and bangsal.nm_bangsal='"
                            + BangsalCari.getText() + "' and reg_periksa.status_bayar like '%"
                            + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                }
            }

            key = kmr + " ";
            if (!TCari.getText().equals("")) {
                key = kmr + "and kamar_inap.no_rawat like '%" + TCari.getText().trim() + "%' or " + kmr
                        + "and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or " + kmr
                        + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or " + kmr
                        + "and concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and kamar_inap.kd_kamar like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and bangsal.nm_bangsal like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and kamar_inap.diagnosa_awal like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and kamar_inap.diagnosa_akhir like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and kamar_inap.trf_kamar like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and kamar_inap.tgl_masuk like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and dokter.nm_dokter like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and kamar_inap.stts_pulang like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and kamar_inap.tgl_keluar like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and penjab.png_jawab like '%"
                        + TCari.getText().trim() + "%' or " + kmr + "and kamar_inap.ttl_biaya like '%"
                        + TCari.getText().trim() + "%' ";
            }

            try {
                pilihancetak = (String) JOptionPane.showInputDialog(rootPane,
                        "Silahkan pilih laporan yang mau dicetak!", "Laporan", JOptionPane.QUESTION_MESSAGE, null,
                        new Object[]{"Laporan 1", "Laporan 2", "Laporan 3", "Lembar Bimbingan Rohani"}, "Laporan 1");
                switch (pilihancetak) {
                    case "Laporan 1":
                        Map<String, Object> param = new HashMap<>();
                        param.put("namars", akses.getnamars());
                        param.put("alamatrs", akses.getalamatrs());
                        param.put("kotars", akses.getkabupatenrs());
                        param.put("propinsirs", akses.getpropinsirs());
                        param.put("kontakrs", akses.getkontakrs());
                        param.put("emailrs", akses.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        param.put("doktermasuk", Sequel.cariIsi(
                                "select dokter.nm_dokter from reg_periksa inner join dokter on dokter.kd_dokter=reg_periksa.kd_dokter where no_rawat =?",
                                norawat.getText()));
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Valid.MyReportqry("rptKamarInap.jasper", "report", "::[ Data Kamar Inap Pasien ]::",
                                "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab),"
                                + "penjab.png_jawab,kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir, "
                                + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar, "
                                + "ifnull((select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=kamar_inap.no_rawat limit 1),'') as dpjp, "
                                + "if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,kamar_inap.ttl_biaya,kamar_inap.stts_pulang, lama,dokter.nm_dokter "
                                + "from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                + "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "
                                + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                                + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "
                                + "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where  " + key
                                + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk",
                                param);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                    case "Laporan 2":
                        Map<String, Object> param2 = new HashMap<>();
                        param2.put("namars", akses.getnamars());
                        param2.put("alamatrs", akses.getalamatrs());
                        param2.put("kotars", akses.getkabupatenrs());
                        param2.put("propinsirs", akses.getpropinsirs());
                        param2.put("kontakrs", akses.getkontakrs());
                        param2.put("emailrs", akses.getemailrs());
                        param2.put("logo", Sequel.cariGambar("select logo from setting"));
                        param2.put("doktermasuk", Sequel.cariIsi(
                                "select dokter.nm_dokter from reg_periksa inner join dokter on dokter.kd_dokter=reg_periksa.kd_dokter where no_rawat =?",
                                norawat.getText()));
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Valid.MyReportqry("rptKamarInap2.jasper", "report", "::[ Data Kamar Inap Pasien ]::",
                                "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,"
                                + "penjab.png_jawab,kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,"
                                + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar,"
                                + "ifnull((select perujuk from rujuk_masuk where rujuk_masuk.no_rawat=reg_periksa.no_rawat),'') as perujuk,"
                                + "ifnull((select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=kamar_inap.no_rawat limit 1),'') as dpjp,"
                                + "if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,kamar_inap.ttl_biaya,kamar_inap.stts_pulang, lama,dokter.nm_dokter "
                                + "from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                + "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "
                                + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                                + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "
                                + "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where  " + key
                                + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk",
                                param2);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                    case "Laporan 3":
                        Map<String, Object> param3 = new HashMap<>();
                        param3.put("namars", akses.getnamars());
                        param3.put("alamatrs", akses.getalamatrs());
                        param3.put("kotars", akses.getkabupatenrs());
                        param3.put("propinsirs", akses.getpropinsirs());
                        param3.put("kontakrs", akses.getkontakrs());
                        param3.put("emailrs", akses.getemailrs());
                        param3.put("logo", Sequel.cariGambar("select logo from setting"));
                        param3.put("doktermasuk", Sequel.cariIsi(
                                "select dokter.nm_dokter from reg_periksa inner join dokter on dokter.kd_dokter=reg_periksa.kd_dokter where no_rawat =?",
                                norawat.getText()));
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Valid.MyReportqry("rptKamarInap5.jasper", "report", "::[ Data Kamar Inap Pasien ]::",
                                "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.agama,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,"
                                + "penjab.png_jawab,kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,dokter.nm_dokter,kamar_inap.asal_kiriman,suku_bangsa.nama_suku_bangsa,"
                                + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar,"
                                + "ifnull((select perujuk from rujuk_masuk where rujuk_masuk.no_rawat=reg_periksa.no_rawat),'') as perujuk,"
                                + "ifnull((select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=kamar_inap.no_rawat limit 1),'') as dpjp,"
                                + "if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,kamar_inap.ttl_biaya,kamar_inap.stts_pulang, lama,dokter.nm_dokter "
                                + "from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                + "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "
                                + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                                + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join suku_bangsa on pasien.suku_bangsa=suku_bangsa.id "
                                + "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where  " + key
                                + " and order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk",
                                param3);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                    case "Lembar Bimbingan Rohani":
                        Map<String, Object> param4 = new HashMap<>();
                        param4.put("namars", akses.getnamars());
                        param4.put("alamatrs", akses.getalamatrs());
                        param4.put("kotars", akses.getkabupatenrs());
                        param4.put("propinsirs", akses.getpropinsirs());
                        param4.put("kontakrs", akses.getkontakrs());
                        param4.put("emailrs", akses.getemailrs());
                        param4.put("logo", Sequel.cariGambar("select logo from setting"));
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Valid.MyReportqry("rptKamarInap3.jasper", "report", "::[ Data Kamar Inap Pasien ]::",
                                "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,"
                                + "penjab.png_jawab,kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,pasien.agama,"
                                + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk, if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar, "
                                + "ifnull((select perujuk from rujuk_masuk where rujuk_masuk.no_rawat=reg_periksa.no_rawat),'') as perujuk,"
                                + "ifnull((select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=kamar_inap.no_rawat limit 1),'') as dpjp,"
                                + "kamar_inap.ttl_biaya,kamar_inap.stts_pulang, lama,dokter.nm_dokter "
                                + "from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                + "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "
                                + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                                + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "
                                + "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where  " + key
                                + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk",
                                param4);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e.toString());

            }
        }
    }// GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnAll, BtnKeluar);
        }
    }// GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbKamIn.requestFocus();
        }
    }// GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCariActionPerformed
        tampil();
        if (chkBaby.isSelected()) {
            tampilBaby();
        }
    }// GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }// GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        if (namakamar.equals("")) {
            BangsalCari.setText("");
        }

        cmbStatusBayar.setSelectedItem("Semua");
        tampil();
        if (chkBaby.isSelected()) {
            tampilBaby();
        }
    }// GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCari, BtnIn);
        }
    }// GEN-LAST:event_BtnAllKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt, kdkamar, cmbJam);
    }// GEN-LAST:event_DTPTglKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt, cmbMnt, diagnosaawal);
    }// GEN-LAST:event_cmbDtkKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt, cmbJam, cmbDtk);
    }// GEN-LAST:event_cmbMntKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt, CmbTgl, cmbMnt);
    }// GEN-LAST:event_cmbJamKeyPressed

    private void TBangsalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TBangsalKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TBangsalKeyPressed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCloseInActionPerformed
        TOut.setText("");
        akses.setstatus(false);
        WindowInputKamar.dispose();
    }// GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnCloseInKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            WindowInputKamar.dispose();
        } else {
            Valid.pindah(evt, BtnBatal, norawat);
        }
    }// GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSimpanActionPerformed
        if (TPasien.getText().trim().equals("")) {
            Valid.textKosong(norawat, "pasien");
        } else if (TKdBngsal.getText().trim().equals("")) {
            Valid.textKosong(kdkamar, "kamar");
        } else {
            if (norawat.isEditable() == true) {
                if (diagnosaawal.getText().equals("")) {
                    Valid.textKosong(diagnosaawal, "diagnosa awal masuk rawat inap");
                } else {
                    switch (TSttsKamar.getText().trim()) {
                        case "ISI":
                            JOptionPane.showMessageDialog(rootPane,
                                    "Maaf, Status kamar sudah terisi silahkan pilih kamar kosong..!!!!");
                            kdkamar.requestFocus();
                            break;
                        case "KOSONG":
                            if (Sequel.menyimpantf("kamar_inap",
                                    "'" + norawat.getText() + "','" + kdkamar.getText() + "','" + TTarif.getText()
                                    + "','" + diagnosaawal.getText() + "','" + diagnosaakhir.getText() + "','"
                                    + upf.getSelectedItem() + "','" + kirimanDari.getSelectedItem() + "','"
                                    + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-"
                                    + CmbTgl.getSelectedItem() + "','" + cmbJam.getSelectedItem() + ":"
                                    + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem()
                                    + "','0000-00-00','00:00:00','" + TJmlHari.getText() + "','"
                                    + ttlbiaya.getText() + "','-'",
                                    "No.Rawat") == true) {
                                Sequel.mengedit("reg_periksa", "no_rawat='" + norawat.getText() + "'",
                                        "status_lanjut='Ranap'");
                                Sequel.mengedit("kamar", "kd_kamar='" + kdkamar.getText() + "'", "status='ISI'");
                                // Sequel.mengedit("temp_spri",
                                // "norm='" + TNoRM.getText() + "'",
                                // "upf='" + upf.getSelectedItem()+ "'");
                                emptTeks();
                            }
                            break;
                    }
                    norawat.requestFocus();
                }
            } else if (norawat.isEditable() == false) {
                if (cmbStatus.getSelectedItem().equals("-")) {
                    Valid.textKosong(cmbStatus, "Status Pulang");
                } else if (diagnosaakhir.getText().equals("")) {
                    Valid.textKosong(diagnosaakhir, "Diagnosa Akhir");
                } else {
                    Sequel.mengedit("kamar_inap",
                            "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                            + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                            + "'",
                            "tgl_keluar='" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-"
                            + CmbTgl.getSelectedItem() + "',trf_kamar='" + TTarif.getText() + "',jam_keluar='"
                            + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                            + cmbDtk.getSelectedItem() + "',ttl_biaya='" + ttlbiaya.getText()
                            + "',stts_pulang='" + cmbStatus.getSelectedItem() + "',diagnosa_akhir='"
                            + diagnosaakhir.getText() + "',lama='" + TJmlHari.getText() + "'");
                    if (cmbStatus.getSelectedItem().equals("Meninggal")) {
                        DlgPasienMati dlgPasienMati = new DlgPasienMati(null, false);
                        dlgPasienMati.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgPasienMati.setLocationRelativeTo(internalFrame1);
                        dlgPasienMati.emptTeks();
                        dlgPasienMati.setNoRm(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString());
                        dlgPasienMati.isCek();
                        dlgPasienMati.setVisible(true);
                    } else if (cmbStatus.getSelectedItem().equals("Rujuk")) {
                        DlgRujuk dlgrjk = new DlgRujuk(null, false);
                        dlgrjk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgrjk.setLocationRelativeTo(internalFrame1);
                        dlgrjk.emptTeks();
                        dlgrjk.isCek();
                        dlgrjk.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                        dlgrjk.tampil();
                        dlgrjk.setVisible(true);
                    }
                    Sequel.mengedit("kamar", "kd_kamar='" + kdkamar.getText() + "'", "status='KOSONG'");
                    WindowInputKamar.dispose();
                    emptTeks();
                }
            }
            tampil();
            if (chkBaby.isSelected()) {
                tampilBaby();
            }
        }
    }// GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt, cmbDtk, BtnBatal);
    }// GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnBatalActionPerformed
        if (norawat.isEditable() == true) {
            emptTeks();
        } else if (norawat.isEditable() == false) {
            emptTeks();
            WindowInputKamar.dispose();
        }
    }// GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnCloseIn);
        }
    }// GEN-LAST:event_BtnBatalKeyPressed

    private void TInKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TInKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TInKeyPressed

    private void DTPTglItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_DTPTglItemStateChanged

    }// GEN-LAST:event_DTPTglItemStateChanged

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_DTPCari1ItemStateChanged
        R2.setSelected(true);

    }// GEN-LAST:event_DTPCari1ItemStateChanged

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPCari1KeyPressed
//        Valid.pindah(evt, BangsalCari, DTPCari2);
    }// GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPCari2KeyPressed
//        Valid.pindah(evt, DTPCari1, BangsalCari);
    }// GEN-LAST:event_DTPCari2KeyPressed

    private void diagnosaawalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_diagnosaawalKeyPressed
        Valid.pindah(evt, cmbDtk, diagnosaakhir);
    }// GEN-LAST:event_diagnosaawalKeyPressed

    private void diagnosaakhirKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_diagnosaakhirKeyPressed
        Valid.pindah(evt, diagnosaawal, BtnSimpan);
    }// GEN-LAST:event_diagnosaakhirKeyPressed

    private void CmbTahunItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_CmbTahunItemStateChanged
        if ((WindowInputKamar.isVisible() == true) && (!TBangsal.getText().equals(""))
                && (!norawat.getText().equals(""))) {
            if (TIn.getText().equals("")) {
                tglmasuk = CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem();
                jammasuk = cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem();
            } else {
                tglmasuk = TIn.getText();
                jammasuk = JamMasuk.getText();
            }
            if (hariawal.equals("Yes")) {
                Sequel.cariIsi("select (if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem()
                        + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":"
                        + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " "
                        + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-"
                        + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem()
                        + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('"
                        + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('"
                        + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem()
                        + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                        + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "'))+1) as lama",
                        TJmlHari);
            } else {
                Sequel.cariIsi("select (if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem()
                        + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":"
                        + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " "
                        + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-"
                        + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem()
                        + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('"
                        + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('"
                        + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem()
                        + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":"
                        + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "'))) as lama",
                        TJmlHari);
            }
            // Sequel.cariIsi("select
            // (if(to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+"
            // "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+"
            // "+jammasuk+"')=0,if(time_to_sec('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+"
            // "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-time_to_sec('"+tglmasuk+"
            // "+jammasuk+"')>(3600*"+lama+"),1,0),to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+"
            // "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+"
            // "+jammasuk+"'))+1) as lama",TJmlHari);
        }
    }// GEN-LAST:event_CmbTahunItemStateChanged

    private void CmbTahunKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_CmbTahunKeyPressed
        Valid.pindah(evt, kdkamar, CmbBln);
    }// GEN-LAST:event_CmbTahunKeyPressed

    private void CmbBlnKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_CmbBlnKeyPressed
        Valid.pindah(evt, CmbTahun, CmbTgl);
    }// GEN-LAST:event_CmbBlnKeyPressed

    private void CmbTglKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_CmbTglKeyPressed
        Valid.pindah(evt, CmbBln, cmbJam);
    }// GEN-LAST:event_CmbTglKeyPressed

    private void TOutKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TOutKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TOutKeyPressed

    private void tbKamInMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbKamInMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
                System.out.println(e.toString());

            }

            if (evt.getClickCount() == 1) {
                if (gabungkan.equals("gabung")) {
                    if (norawat.getText().equals(norawatgabung)) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Gabungkan ke ranap ibu gagal karena no perawatan ibu dan bayi yang dipilih sama..!!");
                        gabungkan = "";
                        norawatgabung = "";
                    } else {
                        int reply = JOptionPane.showConfirmDialog(rootPane,
                                "Eeiiiiiits, udah bener belum data yang mau digabung..??", "Konfirmasi",
                                JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            if (Sequel.menyimpantf("ranap_gabung", "?,?", "Data Ranap Gabung", 2,
                                    new String[]{norawat.getText(), norawatgabung}) == true) {
                                Sequel.queryu("delete from kamar_inap where no_rawat='" + norawatgabung + "'");
                                Sequel.mengedit("kamar", "kd_kamar='" + kamaryangdigabung + "'", "status='KOSONG'");
                                Sequel.mengedit("kamar_inap", "no_rawat='" + norawatgabung + "'",
                                        "no_rawat='" + norawat.getText() + "'");
                                Sequel.mengedit("reg_periksa", "no_rawat='" + norawatgabung + "'",
                                        "status_bayar='Sudah Bayar'");
                                gabungkan = "";
                                norawatgabung = "";
                                tampil();
                                if (chkBaby.isSelected()) {
                                    tampilBaby();
                                }
                            }
                        } else {
                            gabungkan = "";
                            norawatgabung = "";
                        }
                    }
                }

                i = tbKamIn.getSelectedColumn();

                if (i == 2) {

                    if (validasicatatan.equals("Yes")) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        LabelCatatan.setText(Sequel.cariIsi("select catatan from catatan_pasien where no_rkm_medis=?",
                                TNoRM.getText()));

                        if (!LabelCatatan.getText().equals("")) {
                            DlgCatatan.setLocationRelativeTo(DlgKamarInap.this);
                            DlgCatatan.setVisible(true);
                        } else {
                            DlgCatatan.setVisible(false);
                        }
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }

            if (evt.getClickCount() == 2) {
                i = tbKamIn.getSelectedColumn();
                if (i == 0) {
                    if (akses.gettindakan_ranap() == true) {
                        MnRawatInapActionPerformed(null);
                    }
                } else if (i == 1) {
                    if (akses.getberi_obat() == true) {
                        // MnPemberianObatActionPerformed(null);
                        if (tabMode.getRowCount() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
                            TCari.requestFocus();
                        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                            try {
                                psanak = koneksi.prepareStatement(
                                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                                try {
                                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                    rs2 = psanak.executeQuery();
                                    if (rs2.next()) {
                                        if (Sequel.cariRegistrasi(
                                                tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                                            JOptionPane.showMessageDialog(rootPane,
                                                    "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                            TCari.requestFocus();
                                        } else {
                                            akses.setform("DlgKamarInap");
                                            bangsal = Sequel.cariIsi(
                                                    "select kd_depo from set_depo_ranap where kd_bangsal=?",
                                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                            kdkamar.getText()));
                                            if (bangsal.equals("")) {
                                                if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                                        .equals("Gunakan Stok Bangsal")) {
                                                    akses.setkdbangsal(Sequel.cariIsi(
                                                            "select kd_bangsal from kamar where kd_kamar=?",
                                                            kdkamar.getText()));
                                                } else {
                                                    akses.setkdbangsal(
                                                            Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                                }
                                            } else {
                                                akses.setkdbangsal(bangsal);
                                            }
                                            panggilobat(rs2.getString("no_rawat2"));
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(rootPane,
                                                "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                        tbKamIn.requestFocus();
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Notifikasi : " + ex.toString());
                                } finally {
                                    if (rs2 != null) {
                                        rs2.close();
                                    }
                                    if (psanak != null) {
                                        psanak.close();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e.toString());

                            }
                        } else {
                            if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                                JOptionPane.showMessageDialog(rootPane,
                                        "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                TCari.requestFocus();
                            } else {
                                akses.setform("DlgKamarInap");
                                bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel
                                        .cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                if (bangsal.equals("")) {
                                    if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                            .equals("Gunakan Stok Bangsal")) {
                                        akses.setkdbangsal(Sequel.cariIsi(
                                                "select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                    } else {
                                        akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                    }
                                } else {
                                    akses.setkdbangsal(bangsal);
                                }
                                panggilobat(norawat.getText());
                            }
                        }
                    }
                } else if (i == 2) {
                    // if(var.getbilling_ranap()==true){
                    MnBillingActionPerformed(null);
                    // }
                } else if (i == 3) {
                    if (akses.getresep_pulang() == true) {
                        MnInputResepActionPerformed(null);
                    }
                } else if (i == 18) {
                    if (akses.getdpjp_ranap() == true) {
                        MnDPJPActionPerformed(null);
                    }
                }
            }
        }
    }// GEN-LAST:event_tbKamInMouseClicked

    private void tbKamInKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tbKamInKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                    System.out.println(e.toString());

                }
            }

            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                i = tbKamIn.getSelectedColumn();
                if (i == 0) {
                    if (akses.gettindakan_ranap() == true) {
                        MnRawatInapActionPerformed(null);
                    }
                } else if (i == 1) {
                    if (akses.getberi_obat() == true) {
                        // MnPemberianObatActionPerformed(null);
                        if (tabMode.getRowCount() == 0) {
                            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
                            TCari.requestFocus();
                        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                            try {
                                psanak = koneksi.prepareStatement(
                                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                                try {
                                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                    rs2 = psanak.executeQuery();
                                    if (rs2.next()) {
                                        akses.setform("DlgKamarInap");
                                        bangsal = Sequel.cariIsi(
                                                "select kd_depo from set_depo_ranap where kd_bangsal=?",
                                                Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                        kdkamar.getText()));
                                        if (bangsal.equals("")) {
                                            if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                                    .equals("Gunakan Stok Bangsal")) {
                                                akses.setkdbangsal(
                                                        Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                                kdkamar.getText()));
                                            } else {
                                                akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                            }
                                        } else {
                                            akses.setkdbangsal(bangsal);
                                        }

                                        if (Sequel.cariRegistrasi(
                                                tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                                            JOptionPane.showMessageDialog(rootPane,
                                                    "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                            TCari.requestFocus();
                                        } else {
                                            panggilobat(rs2.getString("no_rawat2"));
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(rootPane,
                                                "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                        tbKamIn.requestFocus();
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Notifikasi : " + ex.toString());
                                } finally {
                                    if (rs2 != null) {
                                        rs2.close();
                                    }
                                    if (psanak != null) {
                                        psanak.close();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e.toString());

                            }
                        } else {
                            akses.setform("DlgKamarInap");
                            bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                            if (bangsal.equals("")) {
                                if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                    akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                            kdkamar.getText()));
                                } else {
                                    akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                }
                            } else {
                                akses.setkdbangsal(bangsal);
                            }

                            if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                                JOptionPane.showMessageDialog(rootPane,
                                        "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                TCari.requestFocus();
                            } else {
                                panggilobat(norawat.getText());
                            }
                            // this.dispose();
                        }
                    }
                } else if (i == 2) {
                    // if(var.getbilling_ranap()==true){
                    MnBillingActionPerformed(null);
                    // }
                } else if (i == 3) {
                    if (akses.getresep_pulang() == true) {
                        MnInputResepActionPerformed(null);
                    }
                } else if (i == 18) {
                    if (akses.getdpjp_ranap() == true) {
                        MnDPJPActionPerformed(null);
                    }
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }// GEN-LAST:event_tbKamInKeyPressed

    private void btnBangsalCariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBangsalCariActionPerformed
        akses.setform("DlgKamarInap");
        kamar.bangsal.isCek();
        kamar.bangsal.emptTeks();
        kamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kamar.bangsal.setLocationRelativeTo(internalFrame1);
        kamar.bangsal.setVisible(true);
    }// GEN-LAST:event_btnBangsalCariActionPerformed

    private void btnBangsalCariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnBangsalCariKeyPressed
//        Valid.pindah(evt, DTPCari2, TCari);
    }// GEN-LAST:event_btnBangsalCariKeyPressed

    private void BangsalCariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BangsalCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampil();
            if (chkBaby.isSelected()) {
                tampilBaby();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnBangsalCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            DTPCari3.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TCari.requestFocus();
        }
    }// GEN-LAST:event_BangsalCariKeyPressed

    private void MnRawatInapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnRawatInapActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                            JOptionPane.showMessageDialog(rootPane,
                                    "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        } else {
                            psanak = koneksi.prepareStatement(
                                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                            try {
                                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                rs2 = psanak.executeQuery();
                                if (rs2.next()) {
                                    akses.setform("DlgKamarInap");
                                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                                            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                    kdkamar.getText()));
                                    if (bangsal.equals("")) {
                                        if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                                .equals("Gunakan Stok Bangsal")) {
                                            akses.setkdbangsal(
                                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                            kdkamar.getText()));
                                        } else {
                                            akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                        }
                                    } else {
                                        akses.setkdbangsal(bangsal);
                                    }
                                    billing.rawatinap.isCek();
                                    billing.rawatinap.setSize(internalFrame1.getWidth() - 20,
                                            internalFrame1.getHeight() - 20);
                                    billing.rawatinap.setLocationRelativeTo(internalFrame1);
                                    billing.rawatinap.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(),
                                            DTPCari2.getDate());
                                    billing.rawatinap.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                    tbKamIn.requestFocus();
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex.toString());
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (psanak != null) {
                                    psanak.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    akses.setform("DlgKamarInap");
                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                    if (bangsal.equals("")) {
                        if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                            akses.setkdbangsal(
                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        } else {
                            akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                        }
                    } else {
                        akses.setkdbangsal(bangsal);
                    }
                    billing.rawatinap.isCek();
                    billing.rawatinap.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    billing.rawatinap.setLocationRelativeTo(internalFrame1);
                    billing.rawatinap.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                    billing.rawatinap.setVisible(true);
                }
            }
        }
    }// GEN-LAST:event_MnRawatInapActionPerformed

    private void MnResepPulangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnResepPulangActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                akses.setform("DlgKamarInap");
                                bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel
                                        .cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                if (bangsal.equals("")) {
                                    if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                            .equals("Gunakan Stok Bangsal")) {
                                        akses.setkdbangsal(Sequel.cariIsi(
                                                "select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                    } else {
                                        akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                    }
                                } else {
                                    akses.setkdbangsal(bangsal);
                                }
                                billing.reseppulang.isCek();
                                billing.reseppulang.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(),
                                        DTPCari2.getDate());
                                billing.reseppulang.tampil();
                                billing.reseppulang.setSize(internalFrame1.getWidth() - 20,
                                        internalFrame1.getHeight() - 20);
                                billing.reseppulang.setLocationRelativeTo(internalFrame1);
                                billing.reseppulang.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    akses.setform("DlgKamarInap");
                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                    if (bangsal.equals("")) {
                        if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                            akses.setkdbangsal(
                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        } else {
                            akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                        }
                    } else {
                        akses.setkdbangsal(bangsal);
                    }
                    billing.reseppulang.isCek();
                    billing.reseppulang.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                    billing.reseppulang.tampil();
                    billing.reseppulang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    billing.reseppulang.setLocationRelativeTo(internalFrame1);
                    billing.reseppulang.setVisible(true);
                    // this.dispose();
                }
            }
        }
    }// GEN-LAST:event_MnResepPulangActionPerformed

    private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnRujukActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                DlgRujuk dlgrjk = new DlgRujuk(null, false);
                                dlgrjk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                dlgrjk.setLocationRelativeTo(internalFrame1);
                                dlgrjk.emptTeks();
                                dlgrjk.isCek();
                                dlgrjk.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                                dlgrjk.tampil();
                                dlgrjk.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    DlgRujuk dlgrjk = new DlgRujuk(null, false);
                    dlgrjk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgrjk.setLocationRelativeTo(internalFrame1);
                    dlgrjk.emptTeks();
                    dlgrjk.isCek();
                    dlgrjk.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                    dlgrjk.tampil();
                    dlgrjk.setVisible(true);
                    // this.dispose();
                }
            }
        }
    }

    private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                akses.setform("DlgKamarInap");
                                bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel
                                        .cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                if (bangsal.equals("")) {
                                    if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                            .equals("Gunakan Stok Bangsal")) {
                                        akses.setkdbangsal(Sequel.cariIsi(
                                                "select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                    } else {
                                        akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                    }
                                } else {
                                    akses.setkdbangsal(bangsal);
                                }

                                billing.beriobat.setSize(internalFrame1.getWidth() - 20,
                                        internalFrame1.getHeight() - 20);
                                billing.beriobat.setLocationRelativeTo(internalFrame1);
                                billing.beriobat.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(),
                                        DTPCari2.getDate(), "ranap");
                                billing.beriobat.isCek();
                                billing.beriobat.tampilPO();
                                billing.beriobat.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    akses.setform("DlgKamarInap");
                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                    if (bangsal.equals("")) {
                        if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                            akses.setkdbangsal(
                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        } else {
                            akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                        }
                    } else {
                        akses.setkdbangsal(bangsal);
                    }

                    billing.beriobat.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    billing.beriobat.setLocationRelativeTo(internalFrame1);
                    billing.beriobat.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate(), "ranap");
                    billing.beriobat.isCek();
                    billing.beriobat.tampilPO();
                    billing.beriobat.setVisible(true);
                    // this.dispose();
                }
            }
        }
    }

    private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {

                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                } else {
                    try {
                        pscaripiutang = koneksi.prepareStatement(
                                "select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                        try {
                            pscaripiutang.setString(1, TNoRM.getText());
                            rs = pscaripiutang.executeQuery();
                            if (rs.next()) {
                                i = JOptionPane.showConfirmDialog(rootPane,
                                        "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?", "Konfirmasi",
                                        JOptionPane.YES_NO_OPTION);
                                if (i == JOptionPane.YES_OPTION) {
                                    DlgLhtPiutang piutang = new DlgLhtPiutang(null, false);
                                    piutang.setNoRm(TNoRM.getText(), rs.getDate(1));
                                    piutang.tampil();
                                    piutang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                    piutang.setLocationRelativeTo(internalFrame1);
                                    piutang.setVisible(true);
                                } else {
                                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                                            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                    kdkamar.getText()));
                                    if (bangsal.equals("")) {
                                        if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                                .equals("Gunakan Stok Bangsal")) {
                                            akses.setkdbangsal(
                                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                            kdkamar.getText()));
                                        } else {
                                            akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                        }
                                    } else {
                                        akses.setkdbangsal(bangsal);
                                    }

                                    billing.TNoRw.setText(norawat.getText());
                                    billing.isCek();
                                    billing.isRawat();
                                    billing.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                    billing.setLocationRelativeTo(internalFrame1);
                                    billing.setVisible(true);
                                }
                            } else {
                                bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel
                                        .cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                if (bangsal.equals("")) {
                                    if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                            .equals("Gunakan Stok Bangsal")) {
                                        akses.setkdbangsal(Sequel.cariIsi(
                                                "select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                    } else {
                                        akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                    }
                                } else {
                                    akses.setkdbangsal(bangsal);
                                }

                                billing.TNoRw.setText(norawat.getText());
                                billing.isCek();
                                billing.isRawat();
                                billing.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                billing.setLocationRelativeTo(internalFrame1);
                                billing.setVisible(true);
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e.toString());

                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscaripiutang != null) {
                                pscaripiutang.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                }
            }
        }
    }// GEN-LAST:event_MnBillingActionPerformed

    private void MnDietActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnDietActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                DlgPemberianDiet rawatinap = new DlgPemberianDiet(null, false);
                                rawatinap.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                rawatinap.setLocationRelativeTo(internalFrame1);
                                rawatinap.emptTeks();
                                rawatinap.isCek();
                                rawatinap.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                                rawatinap.tampil();
                                rawatinap.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    DlgPemberianDiet rawatinap = new DlgPemberianDiet(null, false);
                    rawatinap.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    rawatinap.setLocationRelativeTo(internalFrame1);
                    rawatinap.emptTeks();
                    rawatinap.isCek();
                    rawatinap.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                    rawatinap.tampil();
                    rawatinap.setVisible(true);
                    // this.dispose();
                }
            }
        }

    }// GEN-LAST:event_MnDietActionPerformed

    private void DTPCari3ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_DTPCari3ItemStateChanged
        R3.setSelected(true);
    }// GEN-LAST:event_DTPCari3ItemStateChanged

    private void DTPCari3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPCari3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_DTPCari3KeyPressed

    private void cmbStatusKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_cmbStatusKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_cmbStatusKeyPressed

    private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnPeriksaLabActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                            JOptionPane.showMessageDialog(rootPane,
                                    "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        } else {
                            psanak = koneksi.prepareStatement(
                                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                            try {
                                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                rs2 = psanak.executeQuery();
                                if (rs2.next()) {
                                    akses.setform("DlgKamarInap");
                                    billing.periksalab.setSize(internalFrame1.getWidth() - 20,
                                            internalFrame1.getHeight() - 20);
                                    billing.periksalab.setLocationRelativeTo(internalFrame1);
                                    billing.periksalab.emptTeks();
                                    billing.periksalab.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                                    billing.periksalab.isCek();
                                    billing.periksalab.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                    tbKamIn.requestFocus();
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex.toString());
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (psanak != null) {
                                    psanak.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    akses.setform("DlgKamarInap");
                    billing.periksalab.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    billing.periksalab.setLocationRelativeTo(internalFrame1);
                    billing.periksalab.emptTeks();
                    billing.periksalab.setNoRm(norawat.getText(), "Ranap");
                    billing.periksalab.isCek();
                    billing.periksalab.setVisible(true);
                }
            }
        }
    }// GEN-LAST:event_MnPeriksaLabActionPerformed

    private void JamMasukKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_JamMasukKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_JamMasukKeyPressed

    private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnOperasiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                            JOptionPane.showMessageDialog(rootPane,
                                    "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        } else {
                            psanak = koneksi.prepareStatement(
                                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                            try {
                                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                rs2 = psanak.executeQuery();
                                if (rs2.next()) {
                                    DlgTagihanOperasi dlgro = new DlgTagihanOperasi(null, false);
                                    dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                    dlgro.setLocationRelativeTo(internalFrame1);
                                    dlgro.setNoRm(rs2.getString("no_rawat2"),
                                            rs2.getString("no_rkm_medis") + ", " + rs2.getString("nm_pasien"), "Ranap");
                                    dlgro.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                    tbKamIn.requestFocus();
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex.toString());
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (psanak != null) {
                                    psanak.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    DlgTagihanOperasi dlgro = new DlgTagihanOperasi(null, false);
                    dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.setNoRm(norawat.getText(), TNoRM.getText() + ", " + TPasien.getText(), "Ranap");
                    dlgro.setVisible(true);
                }
            }
        }

    }// GEN-LAST:event_MnOperasiActionPerformed

    private void MnHapusTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnHapusTagihanOperasiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                if (Sequel.cariRegistrasi(
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                                    JOptionPane.showMessageDialog(rootPane,
                                            "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                } else {
                                    Sequel.queryu(
                                            "delete from operasi where no_rawat='" + rs2.getString("no_rawat2") + "'");
                                    Sequel.queryu("delete from beri_obat_operasi where no_rawat='"
                                            + rs2.getString("no_rawat2") + "'");
                                }
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    if (Sequel.cariRegistrasi(norawat.getText().trim()) > 0) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    } else {
                        Sequel.queryu("delete from operasi where no_rawat='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0) + "'");
                        Sequel.queryu("delete from beri_obat_operasi where no_rawat='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0) + "'");
                    }

                }
            }
        }

    }// GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

    private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnHapusObatOperasiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                if (Sequel.cariRegistrasi(
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                                    JOptionPane.showMessageDialog(rootPane,
                                            "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                } else {
                                    Sequel.queryu("delete from beri_obat_operasi where no_rawat='"
                                            + rs2.getString("no_rawat2") + "'");
                                }

                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    if (Sequel.cariRegistrasi(norawat.getText().trim()) > 0) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    } else {
                        Sequel.queryu("delete from beri_obat_operasi where no_rawat='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0) + "'");
                    }

                }
            }
        }
    }// GEN-LAST:event_MnHapusObatOperasiActionPerformed

    private void MnRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnRujukMasukActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                akses.setform("DlgKamarInap");
                                DlgRujukMasuk rujukmasuk = new DlgRujukMasuk(null, false);
                                rujukmasuk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                rujukmasuk.setLocationRelativeTo(internalFrame1);
                                rujukmasuk.emptTeks();
                                rujukmasuk.isCek();
                                rujukmasuk.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                                rujukmasuk.tampil();
                                rujukmasuk.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    akses.setform("DlgKamarInap");
                    DlgRujukMasuk rujukmasuk = new DlgRujukMasuk(null, false);
                    rujukmasuk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    rujukmasuk.setLocationRelativeTo(internalFrame1);
                    rujukmasuk.emptTeks();
                    rujukmasuk.isCek();
                    rujukmasuk.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                    rujukmasuk.tampil();
                    rujukmasuk.setVisible(true);
                    // this.dispose();
                }
            }
        }
    }// GEN-LAST:event_MnRujukMasukActionPerformed

    private void btnPindahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPindahActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane,
                    "Maaf, Silahkan anda pilih dulu data inap pasien yang mau pindah dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else if (TOut.getText().trim().length() > 0) {
            JOptionPane.showMessageDialog(rootPane,
                    "Maaf, pasien ini sudah pulang pada tanggal " + TOut.getText() + " ...!!!");
            emptTeks();
            tbKamIn.requestFocus();
        } else if ((TOut.getText().length() == 0) && (norawat.getText().length() > 0)) {
            kdkamarpindah.setText("");
            TKdBngsalpindah.setText("");
            TBangsalpindah.setText("");
            TJmlHaripindah.setText("1");
            TTarifpindah.setText("0");
            ttlbiayapindah.setText("0");
            date = new Date();
            now = dateFormat.format(date);
            CmbTahunpindah.setSelectedItem(now.substring(0, 4));
            CmbBlnpindah.setSelectedItem(now.substring(5, 7));
            CmbTglpindah.setSelectedItem(now.substring(8, 10));
            cmbJampindah.setSelectedItem(now.substring(11, 13));
            cmbMntpindah.setSelectedItem(now.substring(14, 16));
            cmbDtkpindah.setSelectedItem(now.substring(17, 19));
            norawat.requestFocus();
            WindowPindahKamar.setLocationRelativeTo(internalFrame1);
            WindowPindahKamar.setVisible(true);
            lama = Sequel.cariIsiAngka("select lamajam from set_jam_minimal");
            i = 2;
            isKmr();
            isjml();
        }
    }// GEN-LAST:event_btnPindahActionPerformed

    private void btnPindahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnPindahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            btnPindahActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnOut, BtnAll);
        }
    }// GEN-LAST:event_btnPindahKeyPressed

    private void kdkamarpindahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_kdkamarpindahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            i = 2;
            isKmr();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CmbTahun.requestFocus();
            i = 2;
            isKmr();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnSimpanpindah.requestFocus();
            i = 2;
            isKmr();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnKamar2ActionPerformed(null);
        }
    }// GEN-LAST:event_kdkamarpindahKeyPressed

    private void btnKamar2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnKamar2ActionPerformed
        akses.setform("DlgKamarInap");
        i = 2;
        kamar.load();
        kamar.isCek();
        kamar.emptTeks();
        kamar.tampil();
        kamar.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kamar.setLocationRelativeTo(internalFrame1);
        kamar.setVisible(true);
    }// GEN-LAST:event_btnKamar2ActionPerformed

    private void TBangsalpindahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TBangsalpindahKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TBangsalpindahKeyPressed

    private void BtnCloseInpindahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCloseInpindahActionPerformed
        TOut.setText("");
        WindowPindahKamar.dispose();
    }// GEN-LAST:event_BtnCloseInpindahActionPerformed

    private void BtnCloseInpindahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnCloseInpindahKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_BtnCloseInpindahKeyPressed

    private void BtnSimpanpindahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSimpanpindahActionPerformed
        if (TPasienpindah.getText().trim().equals("")) {
            Valid.textKosong(norawatpindah, "pasien");
        } else if (TKdBngsalpindah.getText().trim().equals("")) {
            Valid.textKosong(kdkamarpindah, "kamar");
        } else {
            switch (TSttsKamarpindah.getText().trim()) {
                case "ISI":
                    JOptionPane.showMessageDialog(rootPane,
                            "Maaf, Status kamar sudah terisi silahkan pilih kamar kosong..!!!!");
                    kdkamar.requestFocus();
                    break;
                case "KOSONG":
                    if (Rganti1.isSelected() == true) {
                        Sequel.menyimpan("kamar_inap",
                                "'" + norawatpindah.getText() + "','" + kdkamarpindah.getText() + "','"
                                + TTarifpindah.getText() + "','" + diagnosaawal.getText() + "','"
                                + diagnosaakhir.getText() + "','" + upf.getSelectedItem() + "','"
                                + kirimanDari.getSelectedItem() + "','" + CmbTahunpindah.getSelectedItem() + "-"
                                + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + "','"
                                + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                + cmbDtkpindah.getSelectedItem() + "','0000-00-00','00:00:00','"
                                + TJmlHaripindah.getText() + "','" + ttlbiayapindah.getText() + "','-'",
                                "No.Rawat");
                        Sequel.mengedit("kamar", "kd_kamar='" + kdkamarpindah.getText() + "'", "status='ISI'");
                        Sequel.queryu("delete from kamar_inap where no_rawat='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString() + "' and kd_kamar='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "' and tgl_masuk='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + "' and jam_masuk='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "'");
                        Sequel.mengedit("kamar",
                                "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'",
                                "status='KOSONG'");
                    } else if (Rganti2.isSelected() == true) {
                        Sequel.queryu("update kamar_inap set kd_kamar='" + kdkamarpindah.getText() + "',trf_kamar='"
                                + TTarifpindah.getText() + "'," + "lama='" + TJmlHaripindah.getText() + "',ttl_biaya='"
                                + ttlbiayapindah.getText() + "' where no_rawat='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString() + "' and kd_kamar='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "' and tgl_masuk='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + "' and jam_masuk='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "'");
                        Sequel.mengedit("kamar", "kd_kamar='" + kdkamarpindah.getText() + "'", "status='ISI'");
                        Sequel.mengedit("kamar",
                                "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'",
                                "status='KOSONG'");
                    } else if (Rganti3.isSelected() == true) {
                        i = 1;
                        kdkamar.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString());
                        isKmr();
                        if (hariawal.equals("Yes")) {
                            Sequel.cariIsi("select (if(to_days('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-to_days('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + "')=0,if(time_to_sec('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-time_to_sec('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')>(3600*" + lama
                                    + "),1,0),to_days('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-to_days('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "'))+1) as lama",
                                    TJmlHari);
                        } else {
                            Sequel.cariIsi("select if(to_days('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-to_days('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + "')=0,if(time_to_sec('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-time_to_sec('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')>(3600*" + lama
                                    + "),1,0),to_days('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-to_days('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')) as lama",
                                    TJmlHari);
                        }

                        isjml();
                        Sequel.mengedit("kamar_inap", "no_rawat='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString() + "' and kd_kamar='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "' and tgl_masuk='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + "' and jam_masuk='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "'",
                                "trf_kamar='" + TTarif.getText() + "',tgl_keluar='" + CmbTahunpindah.getSelectedItem()
                                + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                + "',jam_keluar='" + cmbJampindah.getSelectedItem() + ":"
                                + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem()
                                + "',lama='" + TJmlHari.getText() + "',ttl_biaya='" + ttlbiaya.getText()
                                + "',stts_pulang='Pindah Kamar'");
                        Sequel.mengedit("kamar",
                                "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'",
                                "status='KOSONG'");
                        Sequel.menyimpan("kamar_inap",
                                "'" + norawatpindah.getText() + "','" + kdkamarpindah.getText() + "','"
                                + TTarifpindah.getText() + "','" + diagnosaawal.getText() + "','"
                                + diagnosaakhir.getText() + "','" + upf.getSelectedItem() + "','"
                                + kirimanDari.getSelectedItem() + "','" + CmbTahunpindah.getSelectedItem() + "-"
                                + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + "','"
                                + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                + cmbDtkpindah.getSelectedItem() + "','0000-00-00','00:00:00','"
                                + TJmlHaripindah.getText() + "','" + ttlbiayapindah.getText() + "','-'",
                                "No.Rawat");
                        Sequel.mengedit("kamar", "kd_kamar='" + kdkamarpindah.getText() + "'", "status='ISI'");
                    } else if (Rganti4.isSelected() == true) {
                        i = 1;
                        kdkamar.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString());
                        isKmr();
                        if (hariawal.equals("Yes")) {
                            Sequel.cariIsi("select (if(to_days('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-to_days('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + "')=0,if(time_to_sec('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-time_to_sec('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')>(3600*" + lama
                                    + "),1,0),to_days('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-to_days('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "'))+1) as lama",
                                    TJmlHari);
                        } else {
                            Sequel.cariIsi("select if(to_days('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-to_days('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + "')=0,if(time_to_sec('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-time_to_sec('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')>(3600*" + lama
                                    + "),1,0),to_days('" + CmbTahunpindah.getSelectedItem() + "-"
                                    + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + " "
                                    + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                    + cmbDtkpindah.getSelectedItem() + "')-to_days('"
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + " "
                                    + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')) as lama",
                                    TJmlHari);
                        }

                        DecimalFormat df2 = new DecimalFormat("####");
                        if ((!TJmlHari.getText().equals("")) && (!TTarif.getText().equals(""))) {
                            double x = Double.parseDouble(TJmlHari.getText().trim());
                            double y = 0;
                            if (Double.parseDouble(TTarif.getText().trim()) > Double
                                    .parseDouble(TTarifpindah.getText().trim())) {
                                y = Double.parseDouble(TTarif.getText().trim());
                            } else if (Double.parseDouble(TTarif.getText().trim()) < Double
                                    .parseDouble(TTarifpindah.getText().trim())) {
                                y = Double.parseDouble(TTarifpindah.getText().trim());
                            }
                            ttlbiaya.setText(df2.format(x * y));
                        }
                        Sequel.mengedit("kamar_inap", "no_rawat='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString() + "' and kd_kamar='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "' and tgl_masuk='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString() + "' and jam_masuk='"
                                + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "'",
                                "trf_kamar='" + TTarifpindah.getText() + "',tgl_keluar='"
                                + CmbTahunpindah.getSelectedItem() + "-" + CmbBlnpindah.getSelectedItem() + "-"
                                + CmbTglpindah.getSelectedItem() + "',jam_keluar='"
                                + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                + cmbDtkpindah.getSelectedItem() + "',ttl_biaya='" + ttlbiaya.getText()
                                + "',lama='" + TJmlHari.getText() + "',stts_pulang='Pindah Kamar'");
                        Sequel.mengedit("kamar",
                                "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'",
                                "status='KOSONG'");
                        Sequel.menyimpan("kamar_inap",
                                "'" + norawatpindah.getText() + "','" + kdkamarpindah.getText() + "','"
                                + TTarifpindah.getText() + "','" + diagnosaawal.getText() + "','"
                                + diagnosaakhir.getText() + "','" + upf.getSelectedItem() + "','"
                                + kirimanDari.getSelectedItem() + "','" + CmbTahunpindah.getSelectedItem() + "-"
                                + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + "','"
                                + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":"
                                + cmbDtkpindah.getSelectedItem() + "','0000-00-00','00:00:00','"
                                + TJmlHaripindah.getText() + "','" + ttlbiayapindah.getText() + "','-'",
                                "No.Rawat");
                        Sequel.mengedit("kamar", "kd_kamar='" + kdkamarpindah.getText() + "'", "status='ISI'");
                    }
                    tampil();
                    if (chkBaby.isSelected()) {
                        tampilBaby();
                    }
                    WindowPindahKamar.dispose();
                    break;
            }
        }
    }// GEN-LAST:event_BtnSimpanpindahActionPerformed

    private void BtnSimpanpindahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnSimpanpindahKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_BtnSimpanpindahKeyPressed

    private void MnHapusDataSalahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnHapusDataSalahActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data sudah habis...!!!!");
            norawat.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane,
                    "Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus. Klik data pada table untuk memilih.\nUntuk menghapus pasien bayi lewat ranap gabung!!!!");
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (!(norawat.getText().trim().equals(""))) {
                    if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    } else {
                        i = JOptionPane.showConfirmDialog(rootPane, "Yakin data mau dihapus..??", "Konfirmasi",
                                JOptionPane.YES_NO_OPTION);
                        if (i == JOptionPane.YES_OPTION) {
                            Sequel.queryu("delete from kamar_inap where no_rawat='" + norawat.getText()
                                    + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText()
                                    + "' and jam_masuk='" + JamMasuk.getText() + "'");
                            Sequel.mengedit("kamar", "kd_kamar='" + kdkamar.getText() + "'", "status='KOSONG'");
                            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",
                                    norawat.getText()) == 0) {
                                Sequel.mengedit("reg_periksa", "no_rawat='" + norawat.getText() + "'",
                                        "status_lanjut='Ralan'");
                            }
                            tampil();
                            if (chkBaby.isSelected()) {
                                tampilBaby();
                            }
                        }
                    }
                }
                emptTeks();
            }
        }
    }// GEN-LAST:event_MnHapusDataSalahActionPerformed

    private void MnStokObatPasienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnStokObatPasienActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                DlgInputStokPasien dlgrjk = new DlgInputStokPasien(null, false);
                                dlgrjk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                dlgrjk.setLocationRelativeTo(internalFrame1);
                                dlgrjk.isCek();
                                dlgrjk.setNoRm(rs2.getString("no_rawat2"), TNoRM.getText() + " " + TPasien.getText());
                                dlgrjk.tampil();
                                dlgrjk.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    DlgInputStokPasien dlgrjk = new DlgInputStokPasien(null, false);
                    dlgrjk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgrjk.setLocationRelativeTo(internalFrame1);
                    dlgrjk.isCek();
                    dlgrjk.setNoRm(norawat.getText(), TNoRM.getText() + " " + TPasien.getText());
                    dlgrjk.tampil();
                    dlgrjk.setVisible(true);
                    // this.dispose();
                }
            }
        }
    }// GEN-LAST:event_MnStokObatPasienActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowCaraBayar.dispose();
    }// GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSimpan4ActionPerformed
        if (norawat.getText().trim().equals("")) {
            Valid.textKosong(norawat, "No.Rawat");
        }
        if (kdpenjab.getText().trim().equals("") || nmpenjab.getText().trim().equals("")) {
            Valid.textKosong(kdpenjab, "Jenis Bayar");
        } else {
            // String kdpj=Sequel.cariIsi("select kd_pj from reg_periksa where
            // no_rawat=?",norawat.getText());

            // Sequel.meghapus("ubah_penjab","no_rawat",norawat.getText());
            Sequel.mengedit("reg_periksa", "no_rawat=?", " kd_pj=?", 2,
                    new String[]{kdpenjab.getText(), norawat.getText()});
            // Sequel.menyimpan("ubah_penjab","?,?,?,?","Ubah Jenis Bayar",4,new
            // String[]{norawat.getText(),now,kdpj,kdpenjab.getText()});

            tampil();
            if (chkBaby.isSelected()) {
                tampilBaby();
            }
            WindowCaraBayar.dispose();
        }
    }// GEN-LAST:event_BtnSimpan4ActionPerformed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_kdpenjabKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpenjab, kdpenjab.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnBayarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCloseIn4, BtnSimpan4);
        }
    }// GEN-LAST:event_kdpenjabKeyPressed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBayarActionPerformed
        akses.setform("DlgKamarInap");
        reg.pasien.penjab.emptTeks();
        reg.pasien.penjab.isCek();
        reg.pasien.penjab.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        reg.pasien.penjab.setLocationRelativeTo(internalFrame1);
        reg.pasien.penjab.setVisible(true);
    }// GEN-LAST:event_btnBayarActionPerformed

    private void MnPenjabActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnPenjabActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                WindowCaraBayar.setLocationRelativeTo(internalFrame1);
                WindowCaraBayar.setVisible(true);
            }
        }
    }// GEN-LAST:event_MnPenjabActionPerformed

    private void MnAtasPersetujuanDokterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnAtasPersetujuanDokterActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (norawat.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                } else if (TOut.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, pasien ini belum dipulangkan ...!!!");
                    emptTeks();
                    tbKamIn.requestFocus();
                } else {
                    Sequel.mengedit("kamar_inap",
                            "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                            + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                            + "'",
                            "stts_pulang='Atas Persetujuan Dokter'");
                    Sequel.mengedit("kamar",
                            "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'",
                            "status='KOSONG'");
                    tampil();
                    if (chkBaby.isSelected()) {
                        tampilBaby();
                    }
                }
            }
        }
    }// GEN-LAST:event_MnAtasPersetujuanDokterActionPerformed

    private void MnStatusRujukActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnStatusRujukActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (norawat.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                } else if (TOut.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, pasien ini belum dipulangkan ...!!!");
                    emptTeks();
                    tbKamIn.requestFocus();
                } else {
                    Sequel.mengedit("kamar_inap",
                            "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                            + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                            + "'",
                            "stts_pulang='Rujuk'");
                    Sequel.mengedit("kamar",
                            "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'",
                            "status='KOSONG'");
                    tampil();
                    if (chkBaby.isSelected()) {
                        tampilBaby();
                    }
                }
            }
        }
    }// GEN-LAST:event_MnStatusRujukActionPerformed

    private void MnStatusAPSActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnStatusAPSActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (norawat.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                } else if (TOut.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, pasien ini belum dipulangkan ...!!!");
                    emptTeks();
                    tbKamIn.requestFocus();
                } else {
                    Sequel.mengedit("kamar_inap",
                            "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                            + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                            + "'",
                            "stts_pulang='Atas Permintaan Sendiri'");
                    Sequel.mengedit("kamar",
                            "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'",
                            "status='KOSONG'");
                    tampil();
                    if (chkBaby.isSelected()) {
                        tampilBaby();
                    }
                }
            }
        }
    }// GEN-LAST:event_MnStatusAPSActionPerformed

    private void MnStatusMeninggalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnStatusMeninggalActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (norawat.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                } else if (TOut.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, pasien ini belum dipulangkan ...!!!");
                    emptTeks();
                    tbKamIn.requestFocus();
                } else {
                    Sequel.mengedit("kamar_inap",
                            "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                            + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                            + "'",
                            "stts_pulang='Meninggal'");
                    Sequel.mengedit("kamar",
                            "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'",
                            "status='KOSONG'");
                    tampil();
                    if (chkBaby.isSelected()) {
                        tampilBaby();
                    }
                }
            }
        }
    }// GEN-LAST:event_MnStatusMeninggalActionPerformed

    private void MnKaburActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnKaburActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (norawat.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                } else if (TOut.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, pasien ini belum dipulangkan ...!!!");
                    emptTeks();
                    tbKamIn.requestFocus();
                } else {
                    Sequel.mengedit("kamar_inap",
                            "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                            + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                            + "'",
                            "stts_pulang='Kabur'");
                    Sequel.mengedit("kamar",
                            "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'",
                            "status='KOSONG'");
                    tampil();
                    if (chkBaby.isSelected()) {
                        tampilBaby();
                    }
                }
            }
        }
    }// GEN-LAST:event_MnKaburActionPerformed

    private void MnSensusRanapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnSensusRanapActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            Valid.panggilUrl("billing/LaporanSensusHarian.php?tanggal1=" + Valid.SetDateToString(DTPCari1.getDate())
                    + "&tanggal2=" + Valid.SetDateToString(DTPCari2.getDate()) + "&kamar="
                    + BangsalCari.getText().replaceAll(" ", "_"));
        }
    }// GEN-LAST:event_MnSensusRanapActionPerformed

    private void MnRekapitulasiRanapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnRekapitulasiRanapActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_MnRekapitulasiRanapActionPerformed

    private void MnDepositActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnDepositActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                billing.deposit.setSize(internalFrame1.getWidth() - 20,
                                        internalFrame1.getHeight() - 20);
                                billing.deposit.setLocationRelativeTo(internalFrame1);
                                billing.deposit.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(),
                                        DTPCari2.getDate());
                                billing.deposit.tampil();
                                billing.deposit.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    billing.deposit.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    billing.deposit.setLocationRelativeTo(internalFrame1);
                    billing.deposit.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                    billing.deposit.tampil();
                    billing.deposit.setVisible(true);
                }
            }
        }
    }// GEN-LAST:event_MnDepositActionPerformed

    private void MnNoResepActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnNoResepActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                DlgResepObat resep = new DlgResepObat(null, false);
                                resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                resep.setLocationRelativeTo(internalFrame1);
                                resep.emptTeks();
                                resep.isCek();
                                resep.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate(),
                                        now.substring(11, 13), now.substring(14, 16), now.substring(17, 19), "ranap");
                                resep.tampil();
                                resep.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    DlgResepObat resep = new DlgResepObat(null, false);
                    resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.emptTeks();
                    resep.isCek();
                    resep.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate(), now.substring(11, 13),
                            now.substring(14, 16), now.substring(17, 19), "ranap");
                    resep.tampil();
                    resep.setVisible(true);
                }
            }
        }

    }// GEN-LAST:event_MnNoResepActionPerformed

    private void MnRM2DActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnRM2DActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                Map<String, Object> param = new HashMap<>();
                                param.put("nama", rs2.getString("nm_pasien"));
                                param.put("jkel", Sequel.cariIsi(
                                        "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("lahir", Sequel.cariIsi(
                                        "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("norm", rs2.getString("no_rkm_medis"));
                                param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString()
                                        .replaceAll("  ", " "));
                                param.put("kelas", Sequel.cariIsi(
                                        "select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ",
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));
                                param.put("tanggaldaftar", Sequel.cariIsi(
                                        "select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?",
                                        rs2.getString("no_rawat2")));
                                param.put("jamdaftar", Sequel.cariIsi(
                                        "select reg_periksa.jam_reg from reg_periksa where reg_periksa.no_rawat=?",
                                        rs2.getString("no_rawat2")));
                                param.put("noreg", Sequel.cariIsi(
                                        "select reg_periksa.no_reg from reg_periksa where reg_periksa.no_rawat=?",
                                        rs2.getString("no_rawat2")));
                                param.put("pendidikan", "-");
                                param.put("agama",
                                        Sequel.cariIsi("select pasien.agama from pasien where pasien.no_rkm_medis=?",
                                                rs2.getString("no_rkm_medis")));
                                param.put("bangsa", "Jawa/Indonesia");
                                param.put("pekerjaan",
                                        Sequel.cariIsi("select pasien.agama from pasien where pasien.no_rkm_medis=?",
                                                rs2.getString("no_rkm_medis")));
                                param.put("status", "Single");
                                param.put("alamat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 3).toString());
                                param.put("keluarga",
                                        Sequel.cariIsi(
                                                "select pasien.namakeluarga from pasien where pasien.no_rkm_medis=?",
                                                rs2.getString("no_rkm_medis")));
                                param.put("telp",
                                        Sequel.cariIsi("select pasien.no_tlp from pasien where pasien.no_rkm_medis=?",
                                                rs2.getString("no_rkm_medis")));
                                param.put("rujukandari", Sequel.cariIsi(
                                        "select rujuk_masuk.perujuk from rujuk_masuk where rujuk_masuk.no_rawat=?",
                                        rs2.getString("no_rawat2")));
                                param.put("chkri", Sequel.cariIsi(
                                        "select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? ",
                                        rs2.getString("no_rkm_medis")));
                                param.put("chkrj", Sequel.cariIsi(
                                        "select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? ",
                                        rs2.getString("no_rkm_medis")));
                                param.put("riterakhir", Sequel.cariIsi(
                                        "select if(count(kamar_inap.no_rawat)>1,DATE_FORMAT(kamar_inap.tgl_keluar,'%d-%m-%Y'),'') from reg_periksa inner join pasien inner join kamar_inap "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1",
                                        rs2.getString("no_rkm_medis")));
                                param.put("rjterakhir", Sequel.cariIsi(
                                        "select if(count(reg_periksa.no_rawat)>1,DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y'),'') from reg_periksa inner join pasien "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ",
                                        rs2.getString("no_rkm_medis")));
                                param.put("rike", Sequel.cariIsi(
                                        "select if(count(kamar_inap.no_rawat)>1,(count(kamar_inap.no_rawat)-1),'') from reg_periksa inner join pasien inner join kamar_inap "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? ",
                                        rs2.getString("no_rkm_medis")));
                                param.put("rjke", Sequel.cariIsi(
                                        "select if(count(reg_periksa.no_rawat)>1,(count(reg_periksa.no_rawat)-1),'') from reg_periksa inner join pasien "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? ",
                                        rs2.getString("no_rkm_medis")));
                                param.put("riruang", Sequel.cariIsi(
                                        "select if(count(kamar_inap.no_rawat)>1,kamar_inap.kd_kamar,'') from reg_periksa inner join pasien inner join kamar_inap "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1",
                                        rs2.getString("no_rkm_medis")));
                                param.put("rjruang", Sequel.cariIsi(
                                        "select if(count(reg_periksa.no_rawat)>1,kd_poli,'') from reg_periksa inner join pasien "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ",
                                        rs2.getString("no_rkm_medis")));
                                param.put("chkruang", Sequel.cariIsi(
                                        "select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1",
                                        rs2.getString("no_rkm_medis")));
                                param.put("chkbangsal", Sequel.cariIsi(
                                        "select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ",
                                        rs2.getString("no_rkm_medis")));
                                param.put("chkkelri", Sequel.cariIsi(
                                        "select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1",
                                        rs2.getString("no_rkm_medis")));
                                param.put("chkkelrj", Sequel.cariIsi(
                                        "select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                                        + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ",
                                        rs2.getString("no_rkm_medis")));
                                param.put("petugas",
                                        Sequel.cariIsi("select nama from petugas where nip=?", akses.getkode()));

                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReport("rptRM2D.jasper", "report", "::[ Lembar Assasmen ]::", param);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    Map<String, Object> param = new HashMap<>();
                    param.put("nama", TPasien.getText());
                    param.put("jkel", Sequel.cariIsi(
                            "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("lahir", Sequel.cariIsi(
                            "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("norm", TNoRM.getText());
                    param.put("ruang",
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
                    param.put("kelas", Sequel.cariIsi(
                            "select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ",
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));
                    param.put("tanggaldaftar", Sequel.cariIsi(
                            "select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?",
                            norawat.getText()));
                    param.put("jamdaftar",
                            Sequel.cariIsi("select reg_periksa.jam_reg from reg_periksa where reg_periksa.no_rawat=?",
                                    norawat.getText()));
                    param.put("noreg",
                            Sequel.cariIsi("select reg_periksa.no_reg from reg_periksa where reg_periksa.no_rawat=?",
                                    norawat.getText()));
                    param.put("pendidikan", Sequel.cariIsi("select pasien.pnd from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("agama", Sequel.cariIsi("select pasien.agama from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("bangsa", "Jawa/Indonesia");
                    param.put("pekerjaan", Sequel.cariIsi("select pasien.agama from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("status", Sequel.cariIsi(
                            "select pasien.stts_nikah from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
                    param.put("alamat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 3).toString());
                    param.put("keluarga", Sequel.cariIsi(
                            "select pasien.namakeluarga from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
                    param.put("telp", Sequel.cariIsi("select pasien.no_tlp from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("rujukandari",
                            Sequel.cariIsi("select rujuk_masuk.perujuk from rujuk_masuk where rujuk_masuk.no_rawat=?",
                                    norawat.getText()));
                    param.put("chkri", Sequel.cariIsi(
                            "select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? ",
                            TNoRM.getText()));
                    param.put("chkrj", Sequel.cariIsi(
                            "select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? ",
                            TNoRM.getText()));
                    param.put("riterakhir", Sequel.cariIsi(
                            "select if(count(kamar_inap.no_rawat)>1,DATE_FORMAT(kamar_inap.tgl_keluar,'%d-%m-%Y'),'') from reg_periksa inner join pasien inner join kamar_inap "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1",
                            TNoRM.getText()));
                    param.put("rjterakhir", Sequel.cariIsi(
                            "select if(count(reg_periksa.no_rawat)>1,DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y'),'') from reg_periksa inner join pasien "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ",
                            TNoRM.getText()));
                    param.put("rike", Sequel.cariIsi(
                            "select if(count(kamar_inap.no_rawat)>1,(count(kamar_inap.no_rawat)-1),'') from reg_periksa inner join pasien inner join kamar_inap "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? ",
                            TNoRM.getText()));
                    param.put("rjke", Sequel.cariIsi(
                            "select if(count(reg_periksa.no_rawat)>1,(count(reg_periksa.no_rawat)-1),'') from reg_periksa inner join pasien "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? ",
                            TNoRM.getText()));
                    param.put("riruang", Sequel.cariIsi(
                            "select if(count(kamar_inap.no_rawat)>1,kamar_inap.kd_kamar,'') from reg_periksa inner join pasien inner join kamar_inap "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1",
                            TNoRM.getText()));
                    param.put("rjruang", Sequel.cariIsi(
                            "select if(count(reg_periksa.no_rawat)>1,kd_poli,'') from reg_periksa inner join pasien "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ",
                            TNoRM.getText()));
                    param.put("chkruang", Sequel.cariIsi(
                            "select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1",
                            TNoRM.getText()));
                    param.put("chkbangsal", Sequel.cariIsi(
                            "select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ",
                            TNoRM.getText()));
                    param.put("chkkelri", Sequel.cariIsi(
                            "select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1",
                            TNoRM.getText()));
                    param.put("chkkelrj", Sequel.cariIsi(
                            "select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ",
                            TNoRM.getText()));
                    param.put("petugas", Sequel.cariIsi("select nama from petugas where nip=?", akses.getkode()));

                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptRM2D.jasper", "report", "::[ Lembar Assasmen ]::", param);
                }
            }
        }

    }// GEN-LAST:event_MnRM2DActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowActivated
        if ((!(TOut.getText().trim().length() > 0)) && (akses.getstatus() == true)) {
            WindowInputKamar.setVisible(true);
        }
    }// GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowOpened
        tampil();
        if (chkBaby.isSelected()) {
            tampilBaby();
        }
        loadDiagnosa();
    }// GEN-LAST:event_formWindowOpened

    private void loadDiagnosa() {

        try {
            key = "";
            psdiagnosa = koneksi.prepareStatement(
                    "select kd_penyakit from diagnosa_pasien where no_rawat=? order by prioritas asc");
            try {
                psdiagnosa.setString(1, norawat.getText());
                rs = psdiagnosa.executeQuery();
                while (rs.next()) {
                    key = rs.getString(1) + ", " + key;
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex.toString());
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (psdiagnosa != null) {
                    psdiagnosa.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        if (WindowInputKamar.isVisible() == true) {
            diagnosaakhir.setText(key);
            diagnosaakhir.requestFocus();
        } else if (WindowInputKamar.isVisible() == false) {
            Sequel.mengedit("kamar_inap",
                    "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                    + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                    + "'",
                    "diagnosa_akhir='" + key + "'");
            tampil();
            if (chkBaby.isSelected()) {
                tampilBaby();
            }
        }
    }

    private void MnInputResepActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnInputResepActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    } else {
                        try {
                            psanak = koneksi.prepareStatement(
                                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                            try {
                                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                rs2 = psanak.executeQuery();
                                if (rs2.next()) {
                                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                                            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                    kdkamar.getText()));
                                    if (bangsal.equals("")) {
                                        if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                                .equals("Gunakan Stok Bangsal")) {
                                            akses.setkdbangsal(
                                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                            kdkamar.getText()));
                                        } else {
                                            akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                        }
                                    } else {
                                        akses.setkdbangsal(bangsal);
                                    }
                                    billing.reseppulang.inputresep.isCek();
                                    billing.reseppulang.inputresep.setNoRm(rs2.getString("no_rawat2"), "-",
                                            Valid.SetDateToString(DTPCari1.getDate()),
                                            Sequel.cariIsi("select current_time()"));
                                    billing.reseppulang.inputresep.tampil();
                                    billing.reseppulang.inputresep.setSize(internalFrame1.getWidth() - 20,
                                            internalFrame1.getHeight() - 20);
                                    billing.reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
                                    billing.reseppulang.inputresep.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                    tbKamIn.requestFocus();
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex.toString());
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (psanak != null) {
                                    psanak.close();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.toString());

                        }
                    }
                } else {
                    if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    } else {
                        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                                Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        if (bangsal.equals("")) {
                            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                        kdkamar.getText()));
                            } else {
                                akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                            }
                        } else {
                            akses.setkdbangsal(bangsal);
                        }
                        billing.reseppulang.inputresep.isCek();
                        billing.reseppulang.inputresep.setNoRm(norawat.getText(), "-",
                                Valid.SetDateToString(DTPCari1.getDate()), Sequel.cariIsi("select current_time()"));
                        billing.reseppulang.inputresep.tampil();
                        billing.reseppulang.inputresep.setSize(internalFrame1.getWidth() - 20,
                                internalFrame1.getHeight() - 20);
                        billing.reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
                        billing.reseppulang.inputresep.setVisible(true);
                    }
                }
            }
        }
    }// GEN-LAST:event_MnInputResepActionPerformed

    private void MnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnPeriksaRadiologiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                            JOptionPane.showMessageDialog(rootPane,
                                    "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        } else {
                            psanak = koneksi.prepareStatement(
                                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                            try {
                                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                rs2 = psanak.executeQuery();
                                if (rs2.next()) {
                                    billing.periksarad.setSize(internalFrame1.getWidth() - 20,
                                            internalFrame1.getHeight() - 20);
                                    billing.periksarad.setLocationRelativeTo(internalFrame1);
                                    billing.periksarad.emptTeks();
                                    billing.periksarad.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                                    billing.periksarad.tampil();
                                    billing.periksarad.isCek();
                                    billing.periksarad.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                    tbKamIn.requestFocus();
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex.toString());
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (psanak != null) {
                                    psanak.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    billing.periksarad.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    billing.periksarad.setLocationRelativeTo(internalFrame1);
                    billing.periksarad.emptTeks();
                    billing.periksarad.setNoRm(norawat.getText(), "Ranap");
                    billing.periksarad.tampil();
                    billing.periksarad.isCek();
                    billing.periksarad.setVisible(true);
                }
            }
        }
    }// GEN-LAST:event_MnPeriksaRadiologiActionPerformed

    private void WindowInputKamarWindowActivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_WindowInputKamarWindowActivated
        akses.setstatus(false);
    }// GEN-LAST:event_WindowInputKamarWindowActivated

    private void WindowPindahKamarWindowActivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_WindowPindahKamarWindowActivated
        akses.setstatus(false);
    }// GEN-LAST:event_WindowPindahKamarWindowActivated

    private void MnTilikBedahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnTilikBedahActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                Map<String, Object> param = new HashMap<>();
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                param.put("nama", rs2.getString("nm_pasien"));
                                param.put("jkel", Sequel.cariIsi(
                                        "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("lahir", Sequel.cariIsi(
                                        "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("norm", rs2.getString("no_rkm_medis"));
                                param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString()
                                        .replaceAll("  ", " "));
                                param.put("kelas", Sequel.cariIsi(
                                        "select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ",
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));

                                Valid.MyReportqry("rptTilikBedah.jasper", "report", "::[ Lembar Tilik Bedah ]::",
                                        "select current_date() as tanggal, current_time() as jam", param);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    param.put("nama", TPasien.getText());
                    param.put("jkel", Sequel.cariIsi(
                            "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("lahir", Sequel.cariIsi(
                            "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("norm", TNoRM.getText());
                    param.put("ruang",
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
                    param.put("kelas", Sequel.cariIsi(
                            "select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ",
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));

                    Valid.MyReportqry("rptTilikBedah.jasper", "report", "::[ Lembar Tilik Bedah ]::",
                            "select current_date() as tanggal, current_time() as jam", param);
                }
            }
        }
    }// GEN-LAST:event_MnTilikBedahActionPerformed

    private void MnUpdateHariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnUpdateHariActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (R1.isSelected() == false) {
            JOptionPane.showMessageDialog(rootPane, "Tampilkan data yang belum pulang terlebih dahulu");
        } else {
            updateHari();
        }
    }// GEN-LAST:event_MnUpdateHariActionPerformed

    private void MnAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnAsuhanGiziActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("nama", rs2.getString("nm_pasien"));
                                param.put("jkel", Sequel.cariIsi(
                                        "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("lahir", Sequel.cariIsi(
                                        "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("norm", rs2.getString("no_rkm_medis"));
                                param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString()
                                        .replaceAll("  ", " "));
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                param.put("kelas", Sequel.cariIsi(
                                        "select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ",
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));
                                Valid.MyReportqry("rptAssesmentGizi.jasper", "report", "::[ Lembar Asuhan Gizi ]::",
                                        "select current_date() as tanggal, current_time() as jam", param);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("nama", TPasien.getText());
                    param.put("jkel", Sequel.cariIsi(
                            "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("lahir", Sequel.cariIsi(
                            "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("norm", TNoRM.getText());
                    param.put("ruang",
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    param.put("kelas", Sequel.cariIsi(
                            "select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ",
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));
                    Valid.MyReportqry("rptAssesmentGizi.jasper", "report", "::[ Lembar Asuhan Gizi ]::",
                            "select current_date() as tanggal, current_time() as jam", param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnAsuhanGiziActionPerformed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDiagnosaActionPerformed
        if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            akses.setform("DlgKamarInap");
            diagnosa.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            diagnosa.setLocationRelativeTo(internalFrame1);
            diagnosa.isCek();
            diagnosa.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate(), "Ranap");
            diagnosa.panelDiagnosa1.tampil();
            diagnosa.setVisible(true);
        }
    }// GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_btnDiagnosaKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnDiagnosaKeyPressed

    private void BtnCloseGabungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCloseGabungActionPerformed
        NoRawatGabung.setText("");
        NoRmBayi.setText("");
        NmBayi.setText("");
        WindowRanapGabung.dispose();
    }// GEN-LAST:event_BtnCloseGabungActionPerformed

    private void BtnSimpanGabungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSimpanGabungActionPerformed
        if (norawat.getText().trim().equals("")) {
            Valid.textKosong(NoRmBayi, "Pasien");
        } else {
            try {
                psibu = koneksi
                        .prepareStatement("select no_reg,tgl_registrasi,jam_reg,kd_dokter,no_rkm_medis,kd_poli,p_jawab,"
                                + "almt_pj,hubunganpj,biaya_reg,stts,stts_daftar,status_lanjut,kd_pj from reg_periksa where no_rawat=?");
                try {
                    psibu.setString(1, norawat.getText());
                    rs = psibu.executeQuery();
                    if (rs.next()) {
                        pscariumur = koneksi
                                .prepareStatement("select TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) as tahun, "
                                        + "(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12)) as bulan, "
                                        + "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari "
                                        + "from pasien where no_rkm_medis=?");
                        try {
                            pscariumur.setString(1, NoRmBayi.getText());
                            rs2 = pscariumur.executeQuery();
                            if (rs2.next()) {
                                umur = "0";
                                sttsumur = "Th";
                                if (rs2.getInt("tahun") > 0) {
                                    umur = rs2.getString("tahun");
                                    sttsumur = "Th";
                                } else if (rs2.getInt("tahun") == 0) {
                                    if (rs2.getInt("bulan") > 0) {
                                        umur = rs2.getString("bulan");
                                        sttsumur = "Bl";
                                    } else if (rs2.getInt("bulan") == 0) {
                                        umur = rs2.getString("hari");
                                        sttsumur = "Hr";
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Umur : " + e.toString());

                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (pscariumur != null) {
                                pscariumur.close();
                            }
                        }
                        Valid.autoNomer3(
                                "select (ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0)+1) from reg_periksa where tgl_registrasi='"
                                + rs.getString("tgl_registrasi") + "' ",
                                dateformat.format(rs.getDate("tgl_registrasi")) + "/", 6, NoRawatGabung);
                        if (Sequel.menyimpantf2("reg_periksa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Reg Periksa",
                                19,
                                new String[]{rs.getString("no_reg"), NoRawatGabung.getText(),
                                    rs.getString("tgl_registrasi"), rs.getString("jam_reg"),
                                    rs.getString("kd_dokter"), NoRmBayi.getText(), rs.getString("kd_poli"),
                                    rs.getString("p_jawab"), rs.getString("almt_pj"), rs.getString("hubunganpj"),
                                    rs.getString("biaya_reg"), "Belum", "Baru", "Ranap", rs.getString("kd_pj"),
                                    umur, sttsumur, "Sudah Bayar", "Baru"}) == true) {
                            Sequel.menyimpan("ranap_gabung", "?,?", "Data Ranap Gabung", 2,
                                    new String[]{norawat.getText(), NoRawatGabung.getText()});
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex.toString());
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psibu != null) {
                        psibu.close();
                    }
                }

                tampil();
                if (chkBaby.isSelected()) {
                    tampilBaby();
                }
            } catch (Exception e) {
                System.out.println(e.toString());

            }
            NoRawatGabung.setText("");
            NoRmBayi.setText("");
            NmBayi.setText("");
            WindowRanapGabung.dispose();
        }
    }// GEN-LAST:event_BtnSimpanGabungActionPerformed

    private void NoRmBayiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_NoRmBayiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", NmBayi, NoRmBayi.getText());
        }
    }// GEN-LAST:event_NoRmBayiKeyPressed

    private void btnPasienRanapGabungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPasienRanapGabungActionPerformed
        akses.setform("DlgKamarInap");
        reg.pasien.emptTeks();
        reg.pasien.isCek();
        reg.pasien.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        reg.pasien.setLocationRelativeTo(internalFrame1);
        reg.pasien.setVisible(true);
    }// GEN-LAST:event_btnPasienRanapGabungActionPerformed

    private void BtnHapusGabungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnHapusGabungActionPerformed
        Sequel.meghapus("ranap_gabung", "no_rawat", norawat.getText());
        NoRawatGabung.setText("");
        NoRmBayi.setText("");
        NmBayi.setText("");
        tampil();
        if (chkBaby.isSelected()) {
            tampilBaby();
        }
    }// GEN-LAST:event_BtnHapusGabungActionPerformed

    private void MnRanapGabungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnRanapGabungActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                NoRawatGabung.setText("");
                Sequel.cariIsi("select no_rawat2 from ranap_gabung where no_rawat=?", NoRawatGabung, norawat.getText());
                Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?", NoRmBayi,
                        NoRawatGabung.getText());
                Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", NmBayi, NoRmBayi.getText());
                WindowRanapGabung.setLocationRelativeTo(internalFrame1);
                WindowRanapGabung.setVisible(true);
            }
        }
    }// GEN-LAST:event_MnRanapGabungActionPerformed

    private void MnStatusBelumPulangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnStatusBelumPulangActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                    JOptionPane.showMessageDialog(rootPane,
                            "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                } else {
                    Sequel.mengedit("kamar_inap",
                            "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                            + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                            + "'",
                            "stts_pulang='-',tgl_keluar='0000-00-00',jam_keluar='00:00:00'");
                    tampil();
                    if (chkBaby.isSelected()) {
                        tampilBaby();
                    }
                }
            }
        }
    }// GEN-LAST:event_MnStatusBelumPulangActionPerformed

    private void MnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnDiagnosaActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                akses.setform("DlgKamarInap");
                                diagnosa.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                diagnosa.setLocationRelativeTo(internalFrame1);
                                diagnosa.isCek();
                                try {
                                    date = new SimpleDateFormat("yyyy-MM-dd").parse(TIn.getText());
                                } catch (Exception e) {
                                    date = DTPCari2.getDate();
                                }
                                diagnosa.setNoRm(rs2.getString("no_rawat2"), date, DTPCari2.getDate(), "Ranap");
                                diagnosa.panelDiagnosa1.tampil();
                                diagnosa.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    akses.setform("DlgKamarInap");
                    diagnosa.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    diagnosa.setLocationRelativeTo(internalFrame1);
                    diagnosa.isCek();
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(TIn.getText());
                    } catch (Exception e) {
                        date = DTPCari2.getDate();
                    }
                    diagnosa.setNoRm(norawat.getText(), date, DTPCari2.getDate(), "Ranap");
                    diagnosa.panelDiagnosa1.tampil();
                    diagnosa.setVisible(true);
                }
            }
        }

    }// GEN-LAST:event_MnDiagnosaActionPerformed

    private void DTPCari4ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_DTPCari4ItemStateChanged
        R3.setSelected(true);
    }// GEN-LAST:event_DTPCari4ItemStateChanged

    private void DTPCari4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPCari4KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_DTPCari4KeyPressed

    private void MnDPJPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnDPJPActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                DlgDpjp dpjp = new DlgDpjp(null, false);
                                dpjp.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                dpjp.setLocationRelativeTo(internalFrame1);
                                dpjp.isCek();
                                try {
                                    date = new SimpleDateFormat("yyyy-MM-dd").parse(TIn.getText());
                                } catch (Exception e) {
                                    date = DTPCari2.getDate();
                                }

                                dpjp.setNoRm(rs2.getString("no_rawat2"), date, DTPCari2.getDate(), TIn.getText());
                                dpjp.tampil();
                                dpjp.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    DlgDpjp dpjp = new DlgDpjp(null, false);
                    dpjp.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dpjp.setLocationRelativeTo(internalFrame1);
                    dpjp.isCek();
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(TIn.getText());
                    } catch (Exception e) {
                        date = DTPCari2.getDate();
                    }

                    dpjp.setNoRm(norawat.getText(), date, DTPCari2.getDate(), TIn.getText());
                    dpjp.tampil();
                    dpjp.setVisible(true);
                    // this.dispose();
                }
            }
        }
    }// GEN-LAST:event_MnDPJPActionPerformed

    private void MnPenggunaanKamarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnPenggunaanKamarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InformasiAnalisaKamin analisakamin = new InformasiAnalisaKamin(null, false);
        analisakamin.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        analisakamin.setLocationRelativeTo(internalFrame1);
        analisakamin.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }// GEN-LAST:event_MnPenggunaanKamarActionPerformed

    private void MnReturJualActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnReturJualActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    } else {
                        try {
                            psanak = koneksi.prepareStatement(
                                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                            try {
                                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                rs2 = psanak.executeQuery();
                                if (rs2.next()) {
                                    akses.setform("DlgKamarInap");
                                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                                            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                    kdkamar.getText()));
                                    if (bangsal.equals("")) {
                                        if (Sequel.cariIsi("select asal_stok from set_lokasi")
                                                .equals("Gunakan Stok Bangsal")) {
                                            akses.setkdbangsal(
                                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                                            kdkamar.getText()));
                                        } else {
                                            akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                        }
                                    } else {
                                        akses.setkdbangsal(bangsal);
                                    }
                                    DlgReturJual returjual = new DlgReturJual(null, false);
                                    returjual.emptTeks();
                                    returjual.isCek();
                                    returjual.setPasien(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString(),
                                            rs2.getString("no_rawat2"));
                                    returjual.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                    returjual.setLocationRelativeTo(internalFrame1);
                                    returjual.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                    tbKamIn.requestFocus();
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex.toString());
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (psanak != null) {
                                    psanak.close();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                } else {
                    if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    } else {
                        akses.setform("DlgKamarInap");
                        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                                Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        if (bangsal.equals("")) {
                            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                        kdkamar.getText()));
                            } else {
                                akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                            }
                        } else {
                            akses.setkdbangsal(bangsal);
                        }
                        DlgReturJual returjual = new DlgReturJual(null, false);
                        returjual.emptTeks();
                        returjual.isCek();
                        returjual.setPasien(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString(),
                                norawat.getText());
                        returjual.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        returjual.setLocationRelativeTo(internalFrame1);
                        returjual.setVisible(true);
                    }

                }
            }
        }

        this.setCursor(Cursor.getDefaultCursor());
    }// GEN-LAST:event_MnReturJualActionPerformed

    private void MnGelang1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang1ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", TIn.getText());
                param.put("kamar", kdkamar.getText() + " " + TBangsal.getText());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM4.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang1ActionPerformed

    private void MnStatusBelumLengkapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnStatusBelumLengkapActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (TOut.getText().trim().length() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf, pasien ini belum dipulangkan ...!!!");
                    emptTeks();
                    tbKamIn.requestFocus();
                } else {
                    Sequel.mengedit("kamar_inap",
                            "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText()
                            + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText()
                            + "'",
                            "stts_pulang='Status Belum Lengkap'");
                    tampil();
                    if (chkBaby.isSelected()) {
                        tampilBaby();
                    }
                }
            }
        }
    }// GEN-LAST:event_MnStatusBelumLengkapActionPerformed

    private void MnRincianObatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnRincianObatActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("namapasien", rs2.getString("nm_pasien"));
                                param.put("norm", rs2.getString("no_rkm_medis"));
                                param.put("nokartu", rs2.getString("no_peserta"));
                                param.put("umur", rs2.getString("umur"));
                                param.put("alamatpasien", rs2.getString("alamat"));
                                param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString()
                                        .replaceAll("  ", " "));
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReportqry("rptRincianRiwayatObat.jasper", "report",
                                        "::[ Rincian Penggunaan Obat ]::", "select * from temporary", param);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("namapasien", TPasien.getText());
                    param.put("norm", TNoRM.getText());
                    param.put("nokartu",
                            Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?", TNoRM.getText()));
                    param.put("umur", Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", TNoRM.getText()));
                    param.put("alamatpasien", Sequel.cariIsi(
                            "select concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) from pasien where no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("ruang",
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReportqry("rptRincianRiwayatObat.jasper", "report", "::[ Rincian Penggunaan Obat ]::",
                            "select * from temporary", param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }

    }// GEN-LAST:event_MnRincianObatActionPerformed

    private void MnGelang2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang2ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", TIn.getText());
                param.put("kamar", kdkamar.getText() + " " + TBangsal.getText());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM5.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang2ActionPerformed

    private void MnDPJPRanapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnDPJPRanapActionPerformed
        row = tbKamIn.getRowCount();
        for (i = 0; i < row; i++) {
            try {
                psdpjp = koneksi.prepareStatement("select dokter.nm_dokter from dpjp_ranap inner join dokter "
                        + "on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? ");
                dokterranap = "";
                try {
                    psdpjp.setString(1, tbKamIn.getValueAt(i, 0).toString());
                    rs = psdpjp.executeQuery();
                    while (rs.next()) {
                        dokterranap = rs.getString("nm_dokter") + ", " + dokterranap;
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e.toString());
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psdpjp != null) {
                        psdpjp.close();
                    }
                }
                tbKamIn.setValueAt(dokterranap, i, 18);
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e.toString());
            }
        }
    }// GEN-LAST:event_MnDPJPRanapActionPerformed

    private void MnSEPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnSEPActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                akses.setform("DlgKamarInap");
                                BPJSDataSEP dlgki = new BPJSDataSEP(null, false);
                                dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                dlgki.setLocationRelativeTo(internalFrame1);
                                dlgki.isCek();
                                dlgki.setNoRm(rs2.getString("no_rawat2"),
                                        Valid.SetTgl2(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()),
                                        "1. Ranap", "", "");
                                dlgki.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    akses.setform("DlgKamarInap");
                    BPJSDataSEP dlgki = new BPJSDataSEP(null, false);
                    dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgki.setLocationRelativeTo(internalFrame1);
                    dlgki.isCek();
                    dlgki.setNoRm(norawat.getText(),
                            Valid.SetTgl2(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()), "1. Ranap", "",
                            "");
                    dlgki.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnSEPActionPerformed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data pasien sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data kamar inap pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume = new RMRiwayatPerawatan(null, true);
            resume.setNoRm(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString(),
                    tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 2).toString());
            resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void ppCatatanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppCatatanPasienBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data pasien sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data kamar inap pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCatatan catatan = new DlgCatatan(null, true);
            catatan.setNoRm(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString());
            catatan.setSize(720, 330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_ppCatatanPasienBtnPrintActionPerformed

    private void ppDataHAIsBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppDataHAIsBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                DlgDataHAIs hais = new DlgDataHAIs(null, false);
                                hais.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                hais.setLocationRelativeTo(internalFrame1);
                                hais.emptTeks();
                                hais.isCek();
                                hais.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                                hais.tampil();
                                hais.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    DlgDataHAIs hais = new DlgDataHAIs(null, false);
                    hais.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    hais.setLocationRelativeTo(internalFrame1);
                    hais.emptTeks();
                    hais.isCek();
                    hais.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                    hais.tampil();
                    hais.setVisible(true);
                    // this.dispose();
                }
            }
        }
    }// GEN-LAST:event_ppDataHAIsBtnPrintActionPerformed

    private void btnPasienRanapGabung1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPasienRanapGabung1ActionPerformed
        akses.setform("DlgKamarInap");
        ikb.emptTeks();
        ikb.isCek();
        ikb.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ikb.setLocationRelativeTo(internalFrame1);
        ikb.setVisible(true);
    }// GEN-LAST:event_btnPasienRanapGabung1ActionPerformed

    private void MnPengantarPulangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnPengantarPulangActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("nama", rs2.getString("nm_pasien"));
                                param.put("jkel", Sequel.cariIsi(
                                        "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("lahir", Sequel.cariIsi(
                                        "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("norm", rs2.getString("no_rkm_medis"));
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("tanggalmasuk", Sequel.cariIsi(
                                        "select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()));
                                param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                                param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
                                param.put("emailrs", akses.getemailrs());
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReport("rptSuratPengantarPulang.jasper", param,
                                        "::[ Surat Pengantar Pulang ]::");
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("nama", TPasien.getText());
                    param.put("jkel", Sequel.cariIsi(
                            "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("lahir", Sequel.cariIsi(
                            "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("norm", TNoRM.getText());
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("tanggalmasuk",
                            Sequel.cariIsi(
                                    "select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                                    tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
                    param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                    param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptSuratPengantarPulang.jasper", param, "::[ Surat Pengantar Pulang ]::");
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }

    }// GEN-LAST:event_MnPengantarPulangActionPerformed

    private void MnGabungkanRanapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGabungkanRanapActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data pasien yang mau digabung...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                gabungkan = "gabung";
                norawatgabung = norawat.getText();
                kamaryangdigabung = kdkamar.getText();
                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih No.Rawat Ibu/No.Rawat Tujuan");
            }
        }

    }// GEN-LAST:event_MnGabungkanRanapActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgBerkasRawat berkas = new DlgBerkasRawat(null, true);
                                berkas.setJudul("::[ Berkas Digital Perawatan ]::", "berkasrawat/pages");
                                try {
                                    berkas.loadURL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB()
                                            + "/" + koneksiDB.HYBRIDWEB() + "/"
                                            + "berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="
                                            + rs2.getString("no_rawat2"));
                                } catch (Exception ex) {
                                    System.out.println("Notifikasi : " + ex.toString());
                                }

                                berkas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                berkas.setLocationRelativeTo(internalFrame1);
                                berkas.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgBerkasRawat berkas = new DlgBerkasRawat(null, true);
                    berkas.setJudul("::[ Berkas Digital Perawatan ]::", "berkasrawat/pages");
                    try {
                        berkas.loadURL("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/"
                                + koneksiDB.HYBRIDWEB() + "/"
                                + "berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="
                                + norawat.getText());
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex.toString());
                    }

                    berkas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    berkas.setLocationRelativeTo(internalFrame1);
                    berkas.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnRawatJalanActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                            JOptionPane.showMessageDialog(rootPane,
                                    "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        } else {
                            psanak = koneksi.prepareStatement(
                                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                            try {
                                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                rs2 = psanak.executeQuery();
                                if (rs2.next()) {
                                    DlgRawatJalan dlgrwjl = new DlgRawatJalan(null, false);
                                    dlgrwjl.isCek();
                                    dlgrwjl.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                    dlgrwjl.setLocationRelativeTo(internalFrame1);
                                    dlgrwjl.SetPoli(Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",
                                            rs2.getString("no_rawat2")));
                                    dlgrwjl.SetPj(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",
                                            rs2.getString("no_rawat2")));
                                    dlgrwjl.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), new Date());
                                    dlgrwjl.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                    tbKamIn.requestFocus();
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex.toString());
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (psanak != null) {
                                    psanak.close();
                                }
                            }
                        }

                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    DlgRawatJalan dlgrwjl = new DlgRawatJalan(null, false);
                    dlgrwjl.isCek();
                    dlgrwjl.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgrwjl.setLocationRelativeTo(internalFrame1);
                    dlgrwjl.SetPoli(
                            Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?", norawat.getText()));
                    dlgrwjl.SetPj(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norawat.getText()));
                    dlgrwjl.setNoRm(norawat.getText(), DTPCari1.getDate(), new Date());
                    dlgrwjl.setVisible(true);
                }
            }
        }
    }// GEN-LAST:event_MnRawatJalanActionPerformed

    private void MnGelang3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang3ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", TIn.getText());
                param.put("kamar", kdkamar.getText() + " " + TBangsal.getText());
                param.put("dpjp", Sequel.cariIsi(
                        "select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? ",
                        TNoRw1.getText()));
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptGelangPasienAnak.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang3ActionPerformed

    private void MnGelang4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang4ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", TIn.getText());
                param.put("kamar", kdkamar.getText() + " " + TBangsal.getText());
                param.put("dpjp", Sequel.cariIsi(
                        "select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? ",
                        TNoRw1.getText()));
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptGelangPasienDewasa.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang4ActionPerformed

    private void MnCekKepesertaanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnCekKepesertaanActionPerformed
        if (tbKamIn.getSelectedRow() > -1) {
            if (!TNoRM1.getText().equals("")) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                BPJSPeserta form = new BPJSPeserta(null, true);
                form.tampil(Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?", TNoRM.getText()));
                form.setSize(640, internalFrame1.getHeight() - 20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            } else {
                JOptionPane.showMessageDialog(rootPane, "Maaf, Nomor kepesertaan kosong...!!!");
            }
        }
    }// GEN-LAST:event_MnCekKepesertaanActionPerformed

    private void MnCekNIKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnCekNIKActionPerformed
        if (tbKamIn.getSelectedRow() > -1) {
            if (!TNoRM1.getText().equals("")) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                BPJSNik form = new BPJSNik(null, true);
                form.tampil(Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?", TNoRM.getText()));
                form.setSize(640, internalFrame1.getHeight() - 20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            } else {
                JOptionPane.showMessageDialog(rootPane, "Maaf, NIK KTP kosong...!!!");
            }
        }

    }// GEN-LAST:event_MnCekNIKActionPerformed

    private void MnFormulirPenerimaanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnFormulirPenerimaanActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("nama", rs2.getString("nm_pasien"));
                                param.put("jkel", Sequel.cariIsi(
                                        "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("lahir", Sequel.cariIsi(
                                        "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("norm", rs2.getString("no_rkm_medis"));
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("tanggalmasuk", Sequel.cariIsi(
                                        "select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()));
                                param.put("jammasuk", Sequel.cariIsi(
                                        "select jam_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()));
                                param.put("diagnosamasuk", Sequel.cariIsi(
                                        "select diagnosa_awal from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()));
                                param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                                param.put("norawat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
                                param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
                                param.put("emailrs", akses.getemailrs());
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReport("rptFormulirPenerimaanPasien2.jasper", param,
                                        "::[ Formulir Penerimaan Pasien ]::");
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("nama", TPasien.getText());
                    param.put("jkel", Sequel.cariIsi(
                            "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("lahir", Sequel.cariIsi(
                            "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("norm", TNoRM.getText());
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("tanggalmasuk",
                            Sequel.cariIsi(
                                    "select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                                    tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
                    param.put("jammasuk",
                            Sequel.cariIsi(
                                    "select jam_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                                    tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
                    param.put("diagnosamasuk", Sequel.cariIsi(
                            "select diagnosa_awal from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
                    param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                    param.put("norawat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
                    param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptFormulirPenerimaanPasien2.jasper", param, "::[ Formulir Penerimaan Pasien ]::");
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnFormulirPenerimaanActionPerformed

    private void ppIKPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppIKPBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgDataInsidenKeselamatan aplikasi = new DlgDataInsidenKeselamatan(null, false);
                                aplikasi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                aplikasi.setLocationRelativeTo(internalFrame1);
                                aplikasi.isCek();
                                aplikasi.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate(),
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                                aplikasi.tampil();
                                aplikasi.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgDataInsidenKeselamatan aplikasi = new DlgDataInsidenKeselamatan(null, false);
                    aplikasi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    aplikasi.setLocationRelativeTo(internalFrame1);
                    aplikasi.isCek();
                    aplikasi.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate(),
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                    aplikasi.tampil();
                    aplikasi.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_ppIKPBtnPrintActionPerformed

    private void MnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnJadwalOperasiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                DlgBookingOperasi form = new DlgBookingOperasi(null, false);
                                form.isCek();
                                form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.setNoRm(rs2.getString("no_rawat2"), rs2.getString("no_rkm_medis"),
                                        rs2.getString("nm_pasien"),
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString(), "Ranap");
                                form.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    DlgBookingOperasi form = new DlgBookingOperasi(null, false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setNoRm(norawat.getText(), TNoRM.getText(), TPasien.getText(),
                            tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString(), "Ranap");
                    form.setVisible(true);
                }
            }
        }
    }// GEN-LAST:event_MnJadwalOperasiActionPerformed

    private void MnLabelTrackerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnLabelTrackerActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                param.put("no_rawat", rs2.getString("no_rawat2"));
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReport("rptLabelTracker.jasper", param, "::[ Label Tracker ]::");
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("no_rawat", norawat.getText());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptLabelTracker.jasper", param, "::[ Label Tracker ]::");
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnLabelTrackerActionPerformed

    private void MnLabelTracker1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnLabelTracker1ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                param.put("no_rawat", rs2.getString("no_rawat2"));
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReport("rptLabelTracker2.jasper", param, "::[ Label Tracker ]::");
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("no_rawat", norawat.getText());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptLabelTracker2.jasper", param, "::[ Label Tracker ]::");
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnLabelTracker1ActionPerformed

    private void MnLabelTracker2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnLabelTracker2ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReportqry("rptLabelTracker3.jasper", "report", "::[ Label Tracker ]::",
                                        "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                                        + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                                        + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "
                                        + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                                        + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                        + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"
                                        + rs2.getString("no_rawat2") + "' ",
                                        param);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReportqry("rptLabelTracker3.jasper", "report", "::[ Label Tracker ]::",
                            "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                            + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                            + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "
                            + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                            + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"
                            + norawat.getText() + "' ",
                            param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnLabelTracker2ActionPerformed

    private void MnLabelTracker3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnLabelTracker3ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReportqry("rptLabelTracker4.jasper", "report", "::[ Label Tracker ]::",
                                        "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                                        + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                                        + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "
                                        + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                                        + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                        + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"
                                        + rs2.getString("no_rawat2") + "' ",
                                        param);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReportqry("rptLabelTracker4.jasper", "report", "::[ Label Tracker ]::",
                            "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                            + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                            + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "
                            + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                            + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"
                            + norawat.getText() + "' ",
                            param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnLabelTracker3ActionPerformed

    private void MnBarcodeActionPerformed(java.awt.event.ActionEvent evt) {
// GEN-FIRST:event_MnBarcodeActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Map<String, Object> param = new HashMap<>();
                        param.put("nama", TPasien.getText());
                        param.put("tgl",
                                Sequel.cariIsi(
                                        "select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",
                                        TNoRM.getText()));
                        param.put("alamat",
                                Sequel.cariIsi("select alamat from pasien where no_rkm_medis=?", TNoRM.getText()));
                        param.put("kelurahan", Sequel.cariIsi(
                                "select kelurahan.nm_kel from kelurahan inner join pasien on kelurahan.kd_kel=pasien.kd_kel where no_rkm_medis=?",
                                TNoRM.getText()));
                        param.put("norm", TNoRM.getText());
                        param.put("parameter", "%" + TCari.getText().trim() + "%");
                        param.put("namars", akses.getnamars());
                        param.put("alamatrs", akses.getalamatrs());
                        param.put("kotars", akses.getkabupatenrs());
                        param.put("propinsirs", akses.getpropinsirs());
                        param.put("kontakrs", akses.getkontakrs());
                        param.put("emailrs", akses.getemailrs());
                        Valid.MyReportqry("rptBarcodeRawat.jasper", "report", "::[ Barcode No.Rawat ]::",
                                "select reg_periksa.no_rawat,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat "
                                + " from reg_periksa,pasien,kelurahan,kecamatan,kabupaten "
                                + " where "
                                + "pasien.kd_kel=kelurahan.kd_kel and "
                                + "pasien.kd_kec=kecamatan.kd_kec and "
                                + "pasien.kd_kab=kabupaten.kd_kab and "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and no_rawat='"
                                + rs2.getString("no_rawat2") + "'",
                                param);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex.toString());
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama", TPasien.getText());
            param.put("tgl", Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",
                    TNoRM.getText()));
            param.put("alamat", Sequel.cariIsi("select alamat from pasien where no_rkm_medis=?", TNoRM.getText()));
            param.put("kelurahan", Sequel.cariIsi(
                    "select kelurahan.nm_kel from kelurahan inner join pasien on kelurahan.kd_kel=pasien.kd_kel where no_rkm_medis=?",
                    TNoRM.getText()));
            param.put("norm", TNoRM.getText());
            param.put("parameter", "%" + TCari.getText().trim() + "%");
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            Valid.MyReportqry("rptBarcodeRawat.jasper", "report", "::[ Barcode No.Rawat ]::",
                    "select reg_periksa.no_rawat,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat "
                    + " from reg_periksa,pasien,kelurahan,kecamatan,kabupaten "
                    + " where "
                    + "pasien.kd_kel=kelurahan.kd_kel and "
                    + "pasien.kd_kec=kecamatan.kd_kec and "
                    + "pasien.kd_kab=kabupaten.kd_kab and "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and no_rawat='" + norawat.getText() + "'", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_MnBarcodeActionPerformed

    private void MnBarcode1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnBarcode1ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("nama", TPasien.getText());
                                param.put("tgl", Sequel.cariIsi(
                                        "select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",
                                        TNoRM.getText()));
                                param.put("norm", TNoRM.getText());
                                param.put("parameter", "%" + TCari.getText().trim() + "%");
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                Valid.MyReportqry("rptBarcodeRawatRanap.jasper", "report", "::[ Barcode No.Rawat ]::",
                                        "select reg_periksa.no_rawat,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat "
                    + " from reg_periksa,pasien,kelurahan,kecamatan,kabupaten "
                    + " where "
                    + "pasien.kd_kel=kelurahan.kd_kel and "
                    + "pasien.kd_kec=kecamatan.kd_kec and "
                    + "pasien.kd_kab=kabupaten.kd_kab and "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and no_rawat='"
                                        + rs2.getString("no_rawat2") + "'",
                                        param);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("nama", TPasien.getText());
                    param.put("tgl",
                            Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",
                                    TNoRM.getText()));
                    param.put("norm", TNoRM.getText());
                    param.put("parameter", "%" + TCari.getText().trim() + "%");
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    Valid.MyReportqry("rptBarcodeRawatRanap.jasper", "report", "::[ Barcode No.Rawat ]::",
                            "select reg_periksa.no_rawat,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat "
                    + " from reg_periksa,pasien,kelurahan,kecamatan,kabupaten "
                    + " where "
                    + "pasien.kd_kel=kelurahan.kd_kel and "
                    + "pasien.kd_kec=kecamatan.kd_kec and "
                    + "pasien.kd_kab=kabupaten.kd_kab and "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and no_rawat='" + norawat.getText() + "'",
                            param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }

    }// GEN-LAST:event_MnBarcode1ActionPerformed

    private void MnBarcodeRM9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnBarcodeRM9ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane,
                    "Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM18.jasper", "report", "::[ Label Rekam Medis ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnBarcodeRM9ActionPerformed

    private void MnGelang5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang5ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", Valid.SetTgl3(TIn.getText()));
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM6.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang5ActionPerformed

    private void MnGelang6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang6ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", Valid.SetTgl3(TIn.getText()));
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM7.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang6ActionPerformed

    private void MnGelang7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang7ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", Valid.SetTgl3(TIn.getText()));
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM8.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang7ActionPerformed

    private void MnGelang8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang8ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", Valid.SetTgl3(TIn.getText()));
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM10.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang8ActionPerformed

    private void MnGelang9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang9ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", Valid.SetTgl3(TIn.getText()));
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM14.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang9ActionPerformed

    private void MnGelang10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang10ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", Valid.SetTgl3(TIn.getText()));
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM16.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang10ActionPerformed

    private void MnGelang11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnGelang11ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("tanggal", Valid.SetTgl3(TIn.getText()));
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReportqry("rptBarcodeRM19.jasper", "report", "::[ Gelang Pasien ]::",
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                        + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten "
                        + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                        + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"
                        + TNoRM.getText() + "' ",
                        param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }// GEN-LAST:event_MnGelang11ActionPerformed

    private void MnSKDPBPJSActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnSKDPBPJSActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                DlgSKDPBPJS form = new DlgSKDPBPJS(null, false);
                                form.isCek();
                                form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.emptTeks();
                                form.setNoRm(rs2.getString("no_rkm_medis"), rs2.getString("nm_pasien"));
                                form.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    DlgSKDPBPJS form = new DlgSKDPBPJS(null, false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.emptTeks();
                    form.setNoRm(TNoRM.getText(), TPasien.getText());
                    form.setVisible(true);
                }
            }
        }
    }// GEN-LAST:event_MnSKDPBPJSActionPerformed

    private void MnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnPermintaanLabActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgPermintaanLaboratorium dlgro = new DlgPermintaanLaboratorium(null, false);
                                dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                dlgro.setLocationRelativeTo(internalFrame1);
                                dlgro.emptTeks();
                                dlgro.isCek();
                                dlgro.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                                dlgro.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanLaboratorium dlgro = new DlgPermintaanLaboratorium(null, false);
                    dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(norawat.getText(), "Ranap");
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnPermintaanLabActionPerformed

    private void MnPermintaanRadiologiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnPermintaanRadiologiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgPermintaanRadiologi dlgro = new DlgPermintaanRadiologi(null, false);
                                dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                dlgro.setLocationRelativeTo(internalFrame1);
                                dlgro.emptTeks();
                                dlgro.isCek();
                                dlgro.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                                dlgro.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanRadiologi dlgro = new DlgPermintaanRadiologi(null, false);
                    dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(norawat.getText(), "Ranap");
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnPermintaanRadiologiActionPerformed

    private void MnResepDOkterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnResepDOkterActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                JOptionPane.showMessageDialog(rootPane,
                        "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            } else {
                try {
                    psanak = koneksi.prepareStatement(
                            "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                            + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                            + "from reg_periksa inner join pasien inner join ranap_gabung on "
                            + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                    try {
                        psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                        rs2 = psanak.executeQuery();
                        if (rs2.next()) {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                                    Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                            if (bangsal.equals("")) {
                                if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                    akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",
                                            kdkamar.getText()));
                                } else {
                                    akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                }
                            } else {
                                akses.setkdbangsal(bangsal);
                            }
                            DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
                            resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                            resep.setLocationRelativeTo(internalFrame1);
                            resep.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), "ranap");
                            resep.isCek();
                            resep.tampilobat();
                            resep.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                            tbKamIn.requestFocus();
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex.toString());
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (psanak != null) {
                            psanak.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }

        } else {
            if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                JOptionPane.showMessageDialog(rootPane,
                        "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",
                        Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                if (bangsal.equals("")) {
                    if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                        akses.setkdbangsal(
                                Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                    } else {
                        akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                    }
                } else {
                    akses.setkdbangsal(bangsal);
                }
                DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
                resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(norawat.getText(), DTPCari1.getDate(), "ranap");
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }

        }
    }// GEN-LAST:event_MnResepDOkterActionPerformed

    private void MnRujukSisruteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnRujukSisruteActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                akses.setform("DlgKamarInap");
                                SisruteRujukanKeluar dlgki = new SisruteRujukanKeluar(null, false);
                                dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                dlgki.setLocationRelativeTo(internalFrame1);
                                dlgki.isCek();
                                dlgki.setPasien2(rs2.getString("no_rawat2"));
                                dlgki.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex.toString());
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    akses.setform("DlgKamarInap");
                    SisruteRujukanKeluar dlgki = new SisruteRujukanKeluar(null, false);
                    dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgki.setLocationRelativeTo(internalFrame1);
                    dlgki.isCek();
                    dlgki.setPasien2(norawat.getText());
                    dlgki.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnRujukSisruteActionPerformed

    private void MnFormulirPenerimaan1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnFormulirPenerimaan1ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("nama", rs2.getString("nm_pasien"));
                                param.put("jkel", Sequel.cariIsi(
                                        "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("lahir", Sequel.cariIsi(
                                        "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                                        rs2.getString("no_rkm_medis")));
                                param.put("norm", rs2.getString("no_rkm_medis"));
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("tanggalmasuk", Sequel.cariIsi(
                                        "select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                                        tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()));
                                param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                                param.put("norawat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
                                param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
                                param.put("emailrs", akses.getemailrs());
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReport("rptFormulirPenerimaanPasien.jasper", param,
                                        "::[ Formulir Penerimaan Pasien ]::");
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("nama", TPasien.getText());
                    param.put("jkel", Sequel.cariIsi(
                            "select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("lahir", Sequel.cariIsi(
                            "select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?",
                            TNoRM.getText()));
                    param.put("norm", TNoRM.getText());
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("tanggalmasuk",
                            Sequel.cariIsi(
                                    "select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1",
                                    tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
                    param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                    param.put("norawat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
                    param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptFormulirPenerimaanPasien.jasper", param, "::[ Formulir Penerimaan Pasien ]::");
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnFormulirPenerimaan1ActionPerformed

    private void MnCetakSuratSakit1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnCetakSuratSakit1ActionPerformed
        DlgSakit2.setSize(550, 151);
        DlgSakit2.setLocationRelativeTo(internalFrame1);
        DlgSakit2.setVisible(true);
    }// GEN-LAST:event_MnCetakSuratSakit1ActionPerformed

    private void BtnPrint5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnPrint5ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("nomersurat", NomorSurat.getText());
            param.put("dokterpj", CrDokter3.getText());
            param.put("emailrs", akses.getemailrs());
            param.put("TanggalAwal", Sequel.cariIsi(
                    "select DATE_FORMAT(reg_periksa.tgl_registrasi, '%e %M %Y') from reg_periksa where reg_periksa.no_rawat='"
                    + TNoRw1.getText() + "'"));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptSuratSakit2.jasper", "report", "::[ Surat Sakit ]::",
                    "select reg_periksa.no_rkm_medis,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk,"
                    + " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat"
                    + " from reg_periksa inner join pasien inner join dokter"
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter  "
                    + "where reg_periksa.no_rawat='" + TNoRw1.getText() + "' ",
                    param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_BtnPrint5ActionPerformed

    private void BtnKeluar4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnKeluar4ActionPerformed
        DlgSakit2.dispose();
    }// GEN-LAST:event_BtnKeluar4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSeek5ActionPerformed
        akses.setform("DlgKamarInap");
        reg.dokter.isCek();
        reg.dokter.TCari.requestFocus();
        reg.dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        reg.dokter.setLocationRelativeTo(internalFrame1);
        reg.dokter.setVisible(true);
    }// GEN-LAST:event_BtnSeek5ActionPerformed

    private void MnTeridentifikasiTBActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnTeridentifikasiTBActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgDataTB resep = new DlgDataTB(null, false);
                                resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                resep.setLocationRelativeTo(internalFrame1);
                                resep.isCek();
                                resep.emptTeks();
                                resep.setNoRM(rs2.getString("no_rawat2"));
                                resep.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgDataTB resep = new DlgDataTB(null, false);
                    resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.isCek();
                    resep.emptTeks();
                    resep.setNoRM(norawat.getText());
                    resep.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_MnTeridentifikasiTBActionPerformed

    private void MnSuratJaminanPelayananActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnSuratJaminanPelayananActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                Map<String, Object> param = new HashMap<>();
                                param.put("namars", akses.getnamars());
                                param.put("alamatrs", akses.getalamatrs());
                                param.put("kotars", akses.getkabupatenrs());
                                param.put("propinsirs", akses.getpropinsirs());
                                param.put("kontakrs", akses.getkontakrs());
                                param.put("emailrs", akses.getemailrs());
                                param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                                param.put("tanggalmasuk", Sequel.cariIsi(
                                        "select concat(DATE_FORMAT(reg_periksa.tgl_registrasi, '%e %M %Y'),' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat='"
                                        + rs2.getString("no_rawat2") + "'"));
                                param.put("tanggalkeluar", Sequel.cariIsi(
                                        "select concat(if(kamar_inap.tgl_keluar='0000-00-00',DATE_FORMAT(CURDATE(), '%e %M %Y'),DATE_FORMAT(kamar_inap.tgl_keluar, '%e %M %Y')),' ',kamar_inap.jam_keluar) from kamar_inap where kamar_inap.no_rawat='"
                                        + tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()
                                        + "' order by kamar_inap.tgl_keluar desc limit 1"));
                                param.put("logo", Sequel.cariGambar("select logo from setting"));
                                Valid.MyReportqry("SuratJaminanPelayananInap.jasper", "report",
                                        "::[ Surat Jaminan Pelayanan ]::",
                                        "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg, "
                                        + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,poliklinik.nm_poli,"
                                        + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,"
                                        + "pasien.no_peserta,pasien.tgl_lahir from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                                        + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "
                                        + "and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"
                                        + rs2.getString("no_rawat2") + "'",
                                        param);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                    param.put("tanggalmasuk", Sequel.cariIsi(
                            "select concat(DATE_FORMAT(reg_periksa.tgl_registrasi, '%e %M %Y'),' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat='"
                            + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString() + "'"));
                    param.put("tanggalkeluar", Sequel.cariIsi(
                            "select concat(if(kamar_inap.tgl_keluar='0000-00-00',DATE_FORMAT(CURDATE(), '%e %M %Y'),DATE_FORMAT(kamar_inap.tgl_keluar, '%e %M %Y')),' ',kamar_inap.jam_keluar) from kamar_inap where kamar_inap.no_rawat='"
                            + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()
                            + "' order by kamar_inap.tgl_keluar desc limit 1"));
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReportqry("SuratJaminanPelayananInap.jasper", "report", "::[ Surat Jaminan Pelayanan ]::",
                            "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg, "
                            + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,poliklinik.nm_poli,"
                            + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,"
                            + "pasien.no_peserta,pasien.tgl_lahir from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                            + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "
                            + "and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"
                            + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString() + "'",
                            param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }

    }// GEN-LAST:event_MnSuratJaminanPelayananActionPerformed

    private void MnPerkiraanBiayaActionPerformed(java.awt.event.ActionEvent evt) {
        if (!TNoRw1.getText().isBlank()) {
            DlgPerkiraanBiayaRanap resep = new DlgPerkiraanBiayaRanap(null, false);
            resep.isCek();
            resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resep.setLocationRelativeTo(internalFrame1);
            resep.setRM(TNoRw1.getText());
            resep.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Pilih Data Pasien Dahulu!");
        }
    }

    private void ppResumeBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppResumeBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                RMDataResumePasien resume = new RMDataResumePasien(null, false);
                                resume.isCek();
                                resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                resume.setLocationRelativeTo(internalFrame1);
                                resume.setNoRm(rs2.getString("no_rawat2"), DTPCari2.getDate());
                                resume.tampil();
                                resume.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    RMDataResumePasien resume = new RMDataResumePasien(null, false);
                    resume.isCek();
                    resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    resume.setLocationRelativeTo(internalFrame1);
                    resume.setPj(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 18).toString());
                    resume.setNoRm(norawat.getText(), DTPCari2.getDate());
                    resume.tampil();
                    resume.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }

    }// GEN-LAST:event_ppResumeBtnPrintActionPerformed

    private void ppAsuhanGiziBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppAsuhanGiziBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                RMDataAsuhanGizi form = new RMDataAsuhanGizi(null, false);
                                form.isCek();
                                form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.setNoRm(rs2.getString("no_rawat2"), DTPCari2.getDate());
                                form.tampil();
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    RMDataAsuhanGizi form = new RMDataAsuhanGizi(null, false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setNoRm(norawat.getText(), DTPCari2.getDate());
                    form.tampil();
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_ppAsuhanGiziBtnPrintActionPerformed

    private void upfKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_upfKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_upfKeyPressed

    private void kirimanDariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_kirimanDariKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_kirimanDariKeyPressed

    private void MnLembarMasukKeluarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnLembarMasukKeluarActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("asal_kiriman",
                    Sequel.cariIsi("select asal_kiriman from kamar_inap where no_rawat =?", norawat.getText()));
            param.put("upf", Sequel.cariIsi("select upf from kamar_inap where no_rawat =?", norawat.getText()));
            param.put("doktermasuk", Sequel.cariIsi(
                    "select dokter.nm_dokter from reg_periksa inner join dokter on dokter.kd_dokter=reg_periksa.kd_dokter where no_rawat =?",
                    norawat.getText()));
            param.put("dirawatke", Sequel.cariIsi("select count(no_rkm_medis)+1 from reg_periksa where no_rkm_medis=?",
                    TNoRM.getText()));
            param.put("terakhirdirawat", Sequel.cariIsi(
                    "select DATE_FORMAT(tgl_registrasi,'%d/%m/%Y') from reg_periksa where no_rkm_medis=? order by tgl_registrasi desc limit 1",
                    TNoRM.getText()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptLembarKeluarMasuk3.jasper", "report", "::[ Ringkasan Masuk Keluar ]::",
                    "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj, suku_bangsa.nama_suku_bangsa,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj,', ',pasien.propinsipj) as alamatpj, kamar_inap.tgl_masuk, kamar_inap.jam_masuk, "
                    + "kamar.kelas, bangsal.nm_bangsal, kamar_inap.kd_kamar from reg_periksa "
                    + "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "
                    + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "
                    + "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                    + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "
                    + "inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj "
                    + "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "
                    + "inner join kamar_inap on kamar_inap.no_rawat=reg_periksa.no_rawat "
                    + "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "
                    + "inner join bangsal on bangsal.kd_bangsal=kamar.kd_bangsal "
                    + "where reg_periksa.no_rawat='" + norawat.getText() + "'",
                    param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_MnLembarMasukKeluarActionPerformed

    private void ppMonitoringAsuhanGiziBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppMonitoringAsuhanGiziBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                RMDataMonitoringAsuhanGizi form = new RMDataMonitoringAsuhanGizi(null, false);
                                form.isCek();
                                form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.setNoRm(rs2.getString("no_rawat2"), DTPCari2.getDate());
                                form.tampil();
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    RMDataMonitoringAsuhanGizi form = new RMDataMonitoringAsuhanGizi(null, false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setNoRm(norawat.getText(), DTPCari2.getDate());
                    form.tampil();
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }// GEN-LAST:event_ppMonitoringAsuhanGiziBtnPrintActionPerformed

    private void MnSuratPernyataanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnSuratPernyataanActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("terakhirdirawat", Sequel.cariIsi(
                    "select DATE_FORMAT(tgl_registrasi,'%d/%m/%Y') from reg_periksa where no_rkm_medis=? order by tgl_registrasi desc limit 1",
                    TNoRM.getText()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptLembarSuratPernyataan.jasper", "report", "::[ Surat Pernyataan ]::",
                    "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj, suku_bangsa.nama_suku_bangsa,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj,', ',pasien.propinsipj) as alamatpj, kamar_inap.tgl_masuk, kamar_inap.jam_masuk, "
                    + "kamar.kelas, bangsal.nm_bangsal, kamar_inap.kd_kamar from reg_periksa "
                    + "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "
                    + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "
                    + "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                    + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "
                    + "inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj "
                    + "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "
                    + "inner join kamar_inap on kamar_inap.no_rawat=reg_periksa.no_rawat "
                    + "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "
                    + "inner join bangsal on bangsal.kd_bangsal=kamar.kd_bangsal "
                    + "where reg_periksa.no_rawat='" + norawat.getText() + "'",
                    param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_MnSuratPernyataanActionPerformed

    private void detailRawatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_detailRawatActionPerformed
        // TODO add your handling code here:
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data pasien sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu data kamar inap pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDetailRawat resume = new RMDetailRawat(null, true);
            resume.setNoRm(TNoRw1.getText(), tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString(),
                    tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 2).toString());
            resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_detailRawatActionPerformed

    private void ppSuratKematianActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppSuratKematianActionPerformed
        // TODO add your handling code here:
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                pasienMeninggal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                pasienMeninggal.setLocationRelativeTo(internalFrame1);
                                pasienMeninggal.setNoRm(rs2.getString("no_rkm_medis"));
                                pasienMeninggal.tampil();
                                pasienMeninggal.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    pasienMeninggal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    pasienMeninggal.setLocationRelativeTo(internalFrame1);
                    pasienMeninggal.setNoRm(TNoRM.getText());
                    pasienMeninggal.tampil();
                    pasienMeninggal.setVisible(true);
                }
            }
        }
    }// GEN-LAST:event_ppSuratKematianActionPerformed

    private void ppSpriActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppSpriActionPerformed
        // TODO add your handling code here:
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() > -1) {
                if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                    try {
                        psanak = koneksi.prepareStatement(
                                "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                        try {
                            psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                            rs2 = psanak.executeQuery();
                            if (rs2.next()) {
                                spri.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                spri.setLocationRelativeTo(internalFrame1);
                                spri.setNoRm(rs2.getString("no_rkm_medis"));
                                spri.tampil();
                                spri.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                tbKamIn.requestFocus();
                            }
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psanak != null) {
                                psanak.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    spri.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    spri.setLocationRelativeTo(internalFrame1);
                    spri.setNoRm(TNoRM.getText());
                    spri.tampil();
                    spri.setVisible(true);
                }
            }
        }

    }// GEN-LAST:event_ppSpriActionPerformed

    private void norawatFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_norawatFocusGained
        // TODO add your handling code here:
        Sequel.cariIsi("Select upf from temp_spri where norm='" + TNoRM.getText() + "'", upf);
        Sequel.cariIsi("select keluhan from temp_spri where norm=? ", diagnosaawal, TNoRM.getText());
    }// GEN-LAST:event_norawatFocusGained

    private void MnSpriActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TCari.getText().trim().equals("") || TNoRM.getText().trim().equals("")) {
            BtnCariActionPerformed(evt);
        }
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            String tgl_masuk = TNoRw1.getText().substring(0, 10).replace("/", "-");
            System.out.println("tanggal masuk " + tgl_masuk);
            String id = Sequel.cariIsi("select id from temp_spri where norm='" + TNoRM.getText() + "' and tanggal='" + tgl_masuk + "'");
            if (!id.isEmpty()) {
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                String kd_dokter = Sequel.cariIsi(
                        "select dokter.kd_dokter from temp_spri inner join dokter on dokter.kd_dokter=temp_spri.kd_dokter where norm =?",
                        TNoRM.getText());
                param.put("ttd", Sequel.cariGambar("select ttd from ttd_dokter where kd_dokter ='" + kd_dokter + "'"));
                Valid.MyReportqry("rptSpri.jasper", "report", "::[ Surat Laporan Rawat Inap ]::",
                        "SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,if(temp_spri.norm='',temp_spri.nama,pasien.nm_pasien) as nm_pasien,"
                        + "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, "
                        + "CASE WHEN pasien.jk='' THEN '' WHEN pasien.jk='L' THEN 'Laki-laki' WHEN pasien.jk='P' THEN 'Perempuan' END as jk,pasien.tgl_lahir,"
                        + "temp_spri.rencana_perawatan,temp_spri.upf,temp_spri.nm_dokter,temp_spri.kd_dokter,temp_spri.keluhan "
                        + " FROM temp_spri inner join pasien on temp_spri.norm=pasien.no_rkm_medis "
                        + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "
                        + "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                        + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "
                        + " WHERE temp_spri.norm = '" + TNoRM1.getText().trim() + "' "
                        + "and temp_spri.tanggal='"+tgl_masuk+"'"
                        + " order by temp_spri.tanggal ",
                        param);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Data Pasien tidak terdaftar di SPRI, \nMohon tambahkan Data Pasien pada Menu SPRI.", "Informasi", JOptionPane.OK_OPTION);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void LabelCatatanMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_LabelCatatanMouseClicked
        DlgCatatan.dispose();
    }// GEN-LAST:event_LabelCatatanMouseClicked

    private void internalFrame8MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_internalFrame8MouseClicked
        DlgCatatan.dispose();
    }// GEN-LAST:event_internalFrame8MouseClicked

    private void ppPasienCoronaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppPasienCoronaBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane,
                    "Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            CoronaPasien form = new CoronaPasien(null, false);
            form.setPasien(TNoRM.getText());
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_ppPasienCoronaBtnPrintActionPerformed

    private void ppPerawatanCoronaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ppPerawatanCoronaBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane,
                    "Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            INACBGPerawatanCorona form = new INACBGPerawatanCorona(null, false);
            form.emptTeks();
            form.setPasien(TNoRw1.getText(), TNoRM1.getText(), TPasien1.getText());
            form.tampil();
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_ppPerawatanCoronaBtnPrintActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKamarInap dialog = new DlgKamarInap(new javax.swing.JFrame(), true);
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
    private widget.TextBox BangsalCari;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCloseCoder;
    private widget.Button BtnCloseGabung;
    private widget.Button BtnCloseIn;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnCloseIn6;
    private widget.Button BtnCloseIn7;
    private widget.Button BtnCloseInpindah;
    private widget.Button BtnHapusGabung;
    private widget.Button BtnIn;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar4;
    private widget.Button BtnOut;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint5;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpan5;
    private widget.Button BtnSimpan6;
    private widget.Button BtnSimpan7;
    private widget.Button BtnSimpanCoder;
    private widget.Button BtnSimpanCoder1;
    private widget.Button BtnSimpanGabung;
    private widget.Button BtnSimpanpindah;
    private widget.ComboBox CmbBln;
    private widget.ComboBox CmbBlnpindah;
    private widget.ComboBox CmbTahun;
    private widget.ComboBox CmbTahunpindah;
    private widget.ComboBox CmbTgl;
    private widget.ComboBox CmbTglpindah;
    private widget.ComboBox ComboDetik;
    private widget.ComboBox ComboJam;
    private widget.ComboBox ComboMenit;
    private widget.TextBox CrDokter3;
    private widget.Tanggal1 DTPCari1;
    private widget.Tanggal1 DTPCari2;
    private widget.Tanggal1 DTPCari3;
    private widget.Tanggal1 DTPCari4;
    private widget.TextBox DiagnosaAkhirSementara;
    private widget.TextBox DiagnosaAwalSementara;
    private javax.swing.JDialog DlgCatatan;
    private javax.swing.JDialog DlgSakit2;
    private widget.TextBox JamMasuk;
    private widget.Label LCount;
    private widget.Label LabelCatatan;
    private widget.Label LblStts;
    private javax.swing.JMenu MenuBPJS;
    private javax.swing.JMenu MenuInputData;
    private javax.swing.JMenuItem MnAsuhanGizi;
    private javax.swing.JMenuItem MnAtasPersetujuanDokter;
    private javax.swing.JMenuItem MnBarcode;
    private javax.swing.JMenuItem MnBarcode1;
    private javax.swing.JMenuItem MnBarcodeRM9;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnCekKepesertaan;
    private javax.swing.JMenuItem MnCekNIK;
    private javax.swing.JMenuItem MnCetakSuratSakit1;
    private javax.swing.JMenuItem MnDPJP;
    private javax.swing.JMenuItem MnDPJPRanap;
    private javax.swing.JMenu MnDataRM;
    private javax.swing.JMenuItem MnDeposit;
    private javax.swing.JMenuItem MnDiagnosa;
    private javax.swing.JMenuItem MnDiagnosaAkhir;
    private javax.swing.JMenuItem MnDiagnosaMasuk;
    private javax.swing.JMenuItem MnDiet;
    private javax.swing.JMenuItem MnFormulirPenerimaan;
    private javax.swing.JMenuItem MnFormulirPenerimaan1;
    private javax.swing.JMenuItem MnGabungkanRanap;
    private javax.swing.JMenu MnGanti;
    private javax.swing.JMenu MnGelang;
    private javax.swing.JMenuItem MnGelang1;
    private javax.swing.JMenuItem MnGelang10;
    private javax.swing.JMenuItem MnGelang11;
    private javax.swing.JMenuItem MnGelang2;
    private javax.swing.JMenuItem MnGelang3;
    private javax.swing.JMenuItem MnGelang4;
    private javax.swing.JMenuItem MnGelang5;
    private javax.swing.JMenuItem MnGelang6;
    private javax.swing.JMenuItem MnGelang7;
    private javax.swing.JMenuItem MnGelang8;
    private javax.swing.JMenuItem MnGelang9;
    private javax.swing.JMenu MnHapusData;
    private javax.swing.JMenuItem MnHapusDataSalah;
    private javax.swing.JMenuItem MnHapusObatOperasi;
    private javax.swing.JMenuItem MnHapusTagihanOperasi;
    private javax.swing.JMenuItem MnInputResep;
    private javax.swing.JMenuItem MnJadwalOperasi;
    private javax.swing.JMenuItem MnKabur;
    private javax.swing.JMenuItem MnLabelTracker;
    private javax.swing.JMenuItem MnLabelTracker1;
    private javax.swing.JMenuItem MnLabelTracker2;
    private javax.swing.JMenuItem MnLabelTracker3;
    private javax.swing.JMenu MnLaporan;
    private javax.swing.JMenuItem MnLembarMasukKeluar;
    private javax.swing.JMenuItem MnNoResep;
    private javax.swing.JMenu MnObat;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPengantarPulang;
    private javax.swing.JMenuItem MnPenggunaanKamar;
    private javax.swing.JMenuItem MnPenjab;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenuItem MnPerkiraanBiaya;
    private javax.swing.JMenu MnPermintaan;
    private javax.swing.JMenuItem MnPermintaanLab;
    private javax.swing.JMenuItem MnPermintaanRadiologi;
    private javax.swing.JMenuItem MnRM2D;
    private javax.swing.JMenuItem MnRanapGabung;
    private javax.swing.JMenuItem MnRawatInap;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnRekapitulasiRanap;
    private javax.swing.JMenuItem MnResepDOkter;
    private javax.swing.JMenuItem MnResepPulang;
    private javax.swing.JMenuItem MnReturJual;
    private javax.swing.JMenuItem MnRincianObat;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JMenuItem MnRujukMasuk;
    private javax.swing.JMenuItem MnRujukSisrute;
    private javax.swing.JMenu MnRujukan;
    private javax.swing.JMenuItem MnSEP;
    private javax.swing.JMenuItem MnSJP;
    private javax.swing.JMenuItem MnSKDPBPJS;
    private javax.swing.JMenuItem MnSensusRanap;
    private javax.swing.JMenuItem MnSpri;
    private javax.swing.JMenuItem MnStatusAPS;
    private javax.swing.JMenuItem MnStatusBelumLengkap;
    private javax.swing.JMenuItem MnStatusBelumPulang;
    private javax.swing.JMenuItem MnStatusMeninggal;
    private javax.swing.JMenuItem MnStatusRujuk;
    private javax.swing.JMenuItem MnStokObatPasien;
    private javax.swing.JMenuItem MnSuratJaminanPelayanan;
    private javax.swing.JMenuItem MnSuratKematian;
    private javax.swing.JMenuItem MnSuratPernyataan;
    private javax.swing.JMenuItem MnTeridentifikasiTB;
    private javax.swing.JMenuItem MnTilikBedah;
    private javax.swing.JMenu MnTindakan;
    private javax.swing.JMenuItem MnUpdateHari;
    private javax.swing.JMenuItem MnWaktuMasuk;
    private widget.TextBox NmBayi;
    private widget.TextBox NoRawatGabung;
    private widget.TextBox NoRmBayi;
    private widget.TextBox NomorSurat;
    private javax.swing.JPanel PanelCariUtama;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.RadioButton Rganti1;
    private widget.RadioButton Rganti2;
    private widget.RadioButton Rganti3;
    private widget.RadioButton Rganti4;
    private widget.ScrollPane Scroll;
    private javax.swing.JMenu SetStatus;
    private widget.TextBox TBangsal;
    private widget.TextBox TBangsalpindah;
    private widget.TextBox TCari;
    private widget.TextBox TIn;
    private widget.TextBox TJmlHari;
    private widget.TextBox TJmlHaripindah;
    private widget.TextBox TKdBngsal;
    private widget.TextBox TKdBngsalpindah;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRMpindah;
    private widget.TextBox TNoRw1;
    private widget.TextBox TOut;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private widget.TextBox TPasienpindah;
    private widget.TextBox TSttsKamar;
    private widget.TextBox TSttsKamarpindah;
    private widget.TextBox TTarif;
    private widget.TextBox TTarifpindah;
    private javax.swing.JDialog WindowCaraBayar;
    private javax.swing.JDialog WindowDiagnosaAkhir;
    private javax.swing.JDialog WindowDiagnosaMasuk;
    private javax.swing.JDialog WindowInputCoderBPJS;
    private javax.swing.JDialog WindowInputKamar;
    private javax.swing.JDialog WindowPindahKamar;
    private javax.swing.JDialog WindowRanapGabung;
    private javax.swing.JDialog WindowWaktuMasuk;
    private widget.Button btnBangsalCari;
    private widget.Button btnBayar;
    private widget.Button btnDiagnosa;
    private widget.Button btnKamar;
    private widget.Button btnKamar2;
    private widget.Button btnPasienRanapGabung;
    private widget.Button btnPasienRanapGabung1;
    private widget.Button btnPindah;
    private widget.Button btnReg;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox chkBaby;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbDtkpindah;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbJampindah;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbMntpindah;
    private widget.ComboBox cmbStatus;
    private widget.ComboBox cmbStatusBayar;
    private widget.Tanggal1 dateMasuk;
    private javax.swing.JMenuItem detailRawat;
    private widget.TextBox diagnosaakhir;
    private widget.TextBox diagnosaawal;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame10;
    private widget.InternalFrame internalFrame11;
    private widget.InternalFrame internalFrame12;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private widget.TextBox kdkamar;
    private widget.TextBox kdkamarpindah;
    private widget.TextBox kdpenjab;
    private widget.ComboBox kirimanDari;
    private widget.Label label1;
    private widget.TextBox nmpenjab;
    private widget.TextBox norawat;
    private widget.TextBox norawatpindah;
    private widget.PanelBiasa panelBiasa4;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppAsuhanGizi;
    private javax.swing.JMenuItem ppBerkasDigital;
    private javax.swing.JMenuItem ppCatatanPasien;
    private javax.swing.JMenuItem ppDataHAIs;
    private javax.swing.JMenuItem ppIKP;
    private javax.swing.JMenuItem ppInputCoderBpjs;
    private javax.swing.JMenuItem ppMonitoringAsuhanGizi;
    private javax.swing.JMenuItem ppPasienCorona;
    private javax.swing.JMenuItem ppPerawatanCorona;
    private javax.swing.JMenuItem ppResume;
    private javax.swing.JMenuItem ppRiwayat;
    private javax.swing.JMenuItem ppSpri;
    private javax.swing.JMenuItem ppSuratKematian;
    private widget.ScrollPane scrollPane1;
    private widget.Table tableCoder;
    private widget.Table tbKamIn;
    private widget.TextBox ttlbiaya;
    private widget.TextBox ttlbiayapindah;
    private widget.TextBox txtHak1;
    private widget.TextBox txtHak2;
    private widget.TextBox txtHak3;
    private javax.swing.JTextField txtIdCoder;
    private widget.TextBox txtPenjaminan;
    private widget.TextBox txtSelisih;
    private widget.ComboBox upf;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        if (R1.isSelected() == true) {
            kmr = " stts_pulang='-' and reg_periksa.status_bayar like '%"
                    + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' and bangsal.nm_bangsal like'%" + BangsalCari.getText() + "%' ";
            if (!BangsalCari.getText().equals("")) {
                kmr = kmr + " and bangsal.nm_bangsal='" + BangsalCari.getText() + "' ";
            }
        } else if (R2.isSelected() == true) {
            kmr = " kamar_inap.tgl_masuk between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '"
                    + Valid.SetDateToString(DTPCari2.getDate()) + "' and reg_periksa.status_bayar like '%"
                    + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' and bangsal.nm_bangsal like'%" + BangsalCari.getText() + "%' ";
            if (!BangsalCari.getText().equals("")) {
                kmr = kmr + " and bangsal.nm_bangsal='" + BangsalCari.getText() + "' ";
            }
        } else if (R3.isSelected() == true) {
            kmr = " kamar_inap.tgl_keluar between '" + Valid.SetDateToString(DTPCari3.getDate()) + "' and '"
                    + Valid.SetDateToString(DTPCari4.getDate()) + "' and reg_periksa.status_bayar like '%"
                    + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "")
                    + "%' and kamar_inap.stts_pulang not like '%Pindah Kamar%' and bangsal.nm_bangsal like'%" + BangsalCari.getText() + "%' ";
            if (!BangsalCari.getText().equals("")) {
                kmr = kmr + " and bangsal.nm_bangsal='" + BangsalCari.getText() + "' ";
            }
        }

        key = kmr + " ";
        if (!TCari.getText().equals("")) {
            key = kmr + "and kamar_inap.no_rawat like '%" + TCari.getText().trim() + "%' or "
                    + kmr + "and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                    + kmr + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                    + kmr + "and concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.kd_kamar like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and bangsal.nm_bangsal like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.diagnosa_awal like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.diagnosa_akhir like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.trf_kamar like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.tgl_masuk like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.stts_pulang like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.tgl_keluar like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and penjab.png_jawab like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.ttl_biaya like '%" + TCari.getText().trim() + "%' ";
        }

        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,reg_periksa.p_jawab,reg_periksa.hubunganpj,"
                    + "penjab.png_jawab,concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,"
                    + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar,if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,"
                    + "kamar_inap.ttl_biaya,kamar_inap.stts_pulang,kamar_inap.lama,dokter.nm_dokter,kamar_inap.kd_kamar,reg_periksa.kd_pj,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.status_bayar,nm_poli "
                    + "from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "
                    + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                    + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "
                    + "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "
                    + "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli where " + key
                    + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien") + " (" + rs.getString("umur") + ")", rs.getString("alamat"),
                        rs.getString("p_jawab"), rs.getString("hubunganpj"), rs.getString("png_jawab"),
                        rs.getString("kamar"), Valid.SetAngka(rs.getDouble("trf_kamar")),
                        rs.getString("diagnosa_awal"), rs.getString("diagnosa_akhir"), rs.getString("tgl_masuk"),
                        rs.getString("jam_masuk"), rs.getString("tgl_keluar"), rs.getString("jam_keluar"),
                        Valid.SetAngka(rs.getDouble("ttl_biaya")), rs.getString("stts_pulang"),
                        rs.getString("lama"), rs.getString("nm_dokter"), rs.getString("kd_kamar"),
                        rs.getString("status_bayar"), rs.getString("nm_poli")});
                    psanak = koneksi.prepareStatement(
                            "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                            + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat,nm_poli "
                            + "from reg_periksa inner join pasien inner join ranap_gabung inner join poliklinik "
                            + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "
                            + "and ranap_gabung.no_rawat2=reg_periksa.no_rawat "
                            + "and poliklinik.kd_poli=reg_periksa.kd_poli where ranap_gabung.no_rawat=?");
                    try {
                        psanak.setString(1, rs.getString(1));
                        rs2 = psanak.executeQuery();
                        if (rs2.next()) {
                            tabMode.addRow(new String[]{"", rs2.getString("no_rkm_medis"),
                                rs2.getString("nm_pasien") + " (" + rs2.getString("umur") + ")",
                                rs.getString("alamat"), rs.getString("p_jawab"), rs.getString("hubunganpj"),
                                rs.getString("png_jawab"), rs.getString("kamar"),
                                Valid.SetAngka(rs.getDouble("trf_kamar") * (persenbayi / 100)), "", "",
                                rs.getString("tgl_masuk"), rs.getString("jam_masuk"), rs.getString("tgl_keluar"),
                                rs.getString("jam_keluar"),
                                Valid.SetAngka(rs.getDouble("ttl_biaya") * (persenbayi / 100)),
                                rs.getString("stts_pulang"), rs.getString("lama"), rs.getString("nm_dokter"),
                                rs.getString("kd_kamar"), rs.getString("status_bayar"), rs.getString("nm_poli")});
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (psanak != null) {
                            psanak.close();
                        }
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
        LCount.setText("" + tabMode.getRowCount());
    }

    private void tampilBaby() {
        if (R1.isSelected() == true) {
//            kmr = " stts_pulang='-' and reg_periksa.status_bayar like '%"
//                    + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' and bangsal.nm_bangsal like'%" + BangsalCari.getText() + "%' ";

            kmr = " stts_pulang='-' and reg_periksa.status_bayar like '%"
                    + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' and bangsal.kd_bangsal like'%B0063%' "; //" and bangsal.kd_bangsal='B0063' ";

        } else if (R2.isSelected() == true) {
//           
            kmr = " kamar_inap.tgl_masuk between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '"
                    + Valid.SetDateToString(DTPCari2.getDate()) + "' and reg_periksa.status_bayar like '%"
                    + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' and bangsal.kd_bangsal like'%B0063%' ";// + " and bangsal.kd_bangsal='B0063' ";

        } else if (R3.isSelected() == true) {
//          
            kmr = " kamar_inap.tgl_keluar between '" + Valid.SetDateToString(DTPCari3.getDate()) + "' and '"
                    + Valid.SetDateToString(DTPCari4.getDate()) + "' and reg_periksa.status_bayar like '%"
                    + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "")
                    + "%' and kamar_inap.stts_pulang not like '%Pindah Kamar%' and bangsal.kd_bangsal like'%B0063%' ";// + " and bangsal.kd_bangsal='B0063' ";

        }

        key = kmr + " ";
        if (!TCari.getText().equals("")) {
            key = kmr + "and kamar_inap.no_rawat like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.kd_kamar like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and bangsal.nm_bangsal like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.diagnosa_awal like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.diagnosa_akhir like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.trf_kamar like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.tgl_masuk like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.stts_pulang like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.tgl_keluar like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and penjab.png_jawab like '%" + TCari.getText().trim() + "%' or " + kmr
                    + "and kamar_inap.ttl_biaya like '%" + TCari.getText().trim() + "%' ";
        }

//        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,reg_periksa.p_jawab,reg_periksa.hubunganpj,"
                    + "penjab.png_jawab,concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,"
                    + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar,if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,"
                    + "kamar_inap.ttl_biaya,kamar_inap.stts_pulang,kamar_inap.lama,dokter.nm_dokter,kamar_inap.kd_kamar,reg_periksa.kd_pj,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.status_bayar,nm_poli "
                    + "from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "
                    + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                    + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "
                    + "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "
                    + "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli where " + key
                    + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien") + " (" + rs.getString("umur") + ")", rs.getString("alamat"),
                        rs.getString("p_jawab"), rs.getString("hubunganpj"), rs.getString("png_jawab"),
                        rs.getString("kamar"), Valid.SetAngka(rs.getDouble("trf_kamar")),
                        rs.getString("diagnosa_awal"), rs.getString("diagnosa_akhir"), rs.getString("tgl_masuk"),
                        rs.getString("jam_masuk"), rs.getString("tgl_keluar"), rs.getString("jam_keluar"),
                        Valid.SetAngka(rs.getDouble("ttl_biaya")), rs.getString("stts_pulang"),
                        rs.getString("lama"), rs.getString("nm_dokter"), rs.getString("kd_kamar"),
                        rs.getString("status_bayar"), rs.getString("nm_poli")});
                    psanak = koneksi.prepareStatement(
                            "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                            + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                            + "from reg_periksa inner join pasien inner join ranap_gabung on "
                            + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                    try {
                        psanak.setString(1, rs.getString(1));
                        rs2 = psanak.executeQuery();
                        if (rs2.next()) {
                            tabMode.addRow(new String[]{"", rs2.getString("no_rkm_medis"),
                                rs2.getString("nm_pasien") + " (" + rs2.getString("umur") + ")",
                                rs.getString("alamat"), rs.getString("p_jawab"), rs.getString("hubunganpj"),
                                rs.getString("png_jawab"), rs.getString("kamar"),
                                Valid.SetAngka(rs.getDouble("trf_kamar") * (persenbayi / 100)), "", "",
                                rs.getString("tgl_masuk"), rs.getString("jam_masuk"), rs.getString("tgl_keluar"),
                                rs.getString("jam_keluar"),
                                Valid.SetAngka(rs.getDouble("ttl_biaya") * (persenbayi / 100)),
                                rs.getString("stts_pulang"), rs.getString("lama"), rs.getString("nm_dokter"),
                                rs.getString("kd_kamar"), rs.getString("status_bayar"), rs.getString("nm_poli")});
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (psanak != null) {
                            psanak.close();
                        }
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
        LCount.setText("" + tabMode.getRowCount());
        System.out.println("Kamar Baby=" + kmr);
    }

    private void tampilCoder() {
        try {
            Valid.tabelKosong(tabModeCoder);
            ps_coder = koneksi.prepareStatement("select * from coderbpjs "
                    + "where no_rawat = ? order by id_coder desc");
            ps_coder.setString(1, TNoRw1.getText());
            rs_coder = ps_coder.executeQuery();
            while (rs_coder.next()) {
                tabModeCoder.addRow(new Object[]{rs_coder.getString("tanggal"),
                    rs_coder.getString("diagnosa"),
                    Valid.SetAngka(rs_coder.getDouble("hak_kelas_1")),
                    Valid.SetAngka(rs_coder.getDouble("hak_kelas_2")),
                    Valid.SetAngka(rs_coder.getDouble("hak_kelas_3")),
                    Valid.SetAngka(rs_coder.getDouble("selisih")),
                    Valid.SetAngka(rs_coder.getDouble("penjaminan")),
                    rs_coder.getInt("id_coder")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgKamarInap.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void emptTeks() {
        norawat.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        kdkamar.setText("");
        TKdBngsal.setText("");
        TBangsal.setText("");
        diagnosaawal.setText("");
        diagnosaakhir.setText("");
        date = new Date();
        now = dateFormat.format(date);
        CmbTahun.setSelectedItem(now.substring(0, 4));
        CmbBln.setSelectedItem(now.substring(5, 7));
        CmbTgl.setSelectedItem(now.substring(8, 10));
        cmbJam.setSelectedItem(now.substring(11, 13));
        cmbMnt.setSelectedItem(now.substring(14, 16));
        cmbDtk.setSelectedItem(now.substring(17, 19));

        TOut.setText("");
        TIn.setText("");
        JamMasuk.setText("");
        TTarif.setText("0");
        TJmlHari.setText("0");
        ttlbiaya.setText("0");
        norawat.requestFocus();
    }

    private void getData() {
        TOut.setText("");
        TIn.setText("");
        JamMasuk.setText("");
        if (tbKamIn.getSelectedRow() != -1) {
            norawat.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
            TNoRM.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString());
            TPasien.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 2).toString());
            TNoRw1.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
            TNoRM1.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString());
            TPasien1.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 2).toString());
            norawatpindah.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
            TNoRMpindah.setText(TNoRM.getText());
            TPasienpindah.setText(TPasien.getText());
            kdkamar.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString());
            diagnosaawal.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 9).toString());
            diagnosaakhir.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 10).toString());
            TIn.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString());
            JamMasuk.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString());
            TOut.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString());
            ttlbiaya.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 15).toString());
            cmbStatus.setSelectedItem(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 16).toString());
        }
    }

    private void isKmr() {
        if (i == 1) {
            kd_pj = Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norawat.getText());
            String txtUpf = Sequel.cariIsi("select upf from kamar_inap where no_rawat=?", norawat.getText());
            String txtKiriman = Sequel.cariIsi("select asal_kiriman from kamar_inap where no_rawat=?",
                    norawat.getText());
            upf.setSelectedItem(txtUpf);
            kirimanDari.setSelectedItem(txtKiriman);
            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=? ", TKdBngsal, kdkamar.getText());
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=? ", TBangsal, TKdBngsal.getText());
            Sequel.cariIsi("select status from kamar where kd_kamar=? ", TSttsKamar, kdkamar.getText());
            try {
                pstarif = koneksi.prepareStatement("select tarif from set_harga_kamar where kd_kamar=? and kd_pj=?");
                try {
                    pstarif.setString(1, kdkamar.getText());
                    pstarif.setString(2, kd_pj);
                    rs = pstarif.executeQuery();
                    if (rs.next()) {
                        TTarif.setText(rs.getString(1));
                    } else {
                        Sequel.cariIsi("select trf_kamar from kamar where kd_kamar=? ", TTarif, kdkamar.getText());
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (pstarif != null) {
                        pstarif.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        } else {
            kd_pj = Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norawatpindah.getText());
            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=? ", TKdBngsalpindah, kdkamarpindah.getText());
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=? ", TBangsalpindah,
                    TKdBngsalpindah.getText());
            Sequel.cariIsi("select status from kamar where kd_kamar=? ", TSttsKamarpindah, kdkamarpindah.getText());
            Sequel.cariIsi("select trf_kamar from kamar where kd_kamar=? ", TTarifpindah, kdkamarpindah.getText());
            try {
                pstarif = koneksi.prepareStatement("select tarif from set_harga_kamar where kd_kamar=? and kd_pj=?");
                try {
                    pstarif.setString(1, kdkamarpindah.getText());
                    pstarif.setString(2, kd_pj);
                    rs = pstarif.executeQuery();
                    if (rs.next()) {
                        TTarifpindah.setText(rs.getString(1));
                    } else {
                        Sequel.cariIsi("select trf_kamar from kamar where kd_kamar=? ", TTarifpindah,
                                kdkamar.getText());
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (pstarif != null) {
                        pstarif.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private void isjml() {
        DecimalFormat df2 = new DecimalFormat("####");
        if ((!TJmlHari.getText().equals("")) && (!TTarif.getText().equals(""))) {
            double x = Double.parseDouble(TJmlHari.getText().trim());
            double y = Double.parseDouble(TTarif.getText().trim());
            ttlbiaya.setText(df2.format(x * y));
        }

        if ((!TJmlHaripindah.getText().equals("")) && (!TTarifpindah.getText().equals(""))) {
            double x = Double.parseDouble(TJmlHaripindah.getText().trim());
            double y = Double.parseDouble(TTarifpindah.getText().trim());
            ttlbiayapindah.setText(df2.format(x * y));
        }
    }

    /**
     *
     * @param norwt
     * @param norm
     * @param nmpasien
     */
    public void setNoRm(String norwt, String norm, String nmpasien) {
        norawat.setText(norwt);
        TNoRM.setText(norm);
        TPasien.setText(nmpasien);
        R1.setSelected(true);
        TCari.setText(norwt);
        try {
            ps = koneksi.prepareStatement("select no_rawat, kd_kamar, diagnosa_awal, diagnosa_akhir, tgl_masuk, jam_masuk, tgl_keluar, jam_keluar, ttl_biaya "
                    + "from kamar_inap where no_rawat=? order by tgl_masuk,jam_masuk desc limit 1 ");
            try {
                ps.setString(1, norawat.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    norawat.setEditable(false);
                    norawat.setText(rs.getString(1));
                    kdkamar.setText(rs.getString(2));
                    diagnosaawal.setText(rs.getString(3));
                    diagnosaakhir.setText(rs.getString(4));
                    TIn.setText(rs.getString(5));
                    JamMasuk.setText(rs.getString(6));
                    TOut.setText(rs.getString(7));
                    ttlbiaya.setText(rs.getString(8));

                    kdkamar.setEditable(false);
                    diagnosaawal.setEditable(false);
                    diagnosaakhir.setVisible(true);
                    btnDiagnosa.setVisible(true);
                    jLabel23.setVisible(true);
                    cmbStatus.setVisible(true);
                    jLabel26.setVisible(true);
                    diagnosaakhir.setText("");
                    LblStts.setText("Pulang/Check Out");
                    i = 1;
                    btnReg.setEnabled(false);
                    btnKamar.setEnabled(false);
                    CmbTahun.setSelectedItem(now.substring(0, 4));
                    CmbBln.setSelectedItem(now.substring(5, 7));
                    CmbTgl.setSelectedItem(now.substring(8, 10));
                } else {
                    norawat.setEditable(true);
                    kdkamar.setEditable(true);
                    diagnosaawal.setEditable(true);
                    diagnosaakhir.setVisible(false);
                    btnDiagnosa.setVisible(false);
                    TIn.setText("");
                    JamMasuk.setText("");
                    TOut.setText("");
                    ttlbiaya.setText("0");
                    jLabel23.setVisible(false);
                    cmbStatus.setVisible(false);
                    jLabel26.setVisible(false);
                    diagnosaakhir.setText("-");
                    LblStts.setText("Masuk/Check In");
                    btnReg.setEnabled(true);
                    btnKamar.setEnabled(true);
                    CmbTahun.setSelectedItem(now.substring(0, 4));
                    CmbBln.setSelectedItem(now.substring(5, 7));
                    CmbTgl.setSelectedItem(now.substring(8, 10));
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e.toString());

            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }

            if (kdkamar.isEditable() == false) {
                isKmr();
                isjml();
            }
            CmbTahunItemStateChanged(null);
        } catch (Exception e) {
            System.out.println(e.toString());

        }
    }

    /**
     *
     */
    public void isCek() {
        try {
            namakamar = koneksiDB.KAMARAKTIFRANAP();
        } catch (Exception ex) {
            namakamar = "";
        }

        if (!namakamar.equals("")) {
            if (akses.getkode().equals("Admin Utama")) {
                BangsalCari.setText("");
                btnBangsalCari.setEnabled(true);
                BangsalCari.setEditable(true);
            } else {
                BangsalCari.setText(namakamar);
                btnBangsalCari.setEnabled(false);
                BangsalCari.setEditable(false);
            }
        } else {
            btnBangsalCari.setEnabled(true);
            BangsalCari.setEditable(true);
        }

        BtnSimpan.setEnabled(akses.getkamar_inap());
        BtnSimpanpindah.setEnabled(akses.getkamar_inap());
        BtnPrint.setEnabled(akses.getkamar_inap());
        MnRawatInap.setEnabled(akses.gettindakan_ranap());
        MnRawatJalan.setEnabled(akses.gettindakan_ralan());
        MnPemberianObat.setEnabled(akses.getberi_obat());
        MnReturJual.setEnabled(akses.getretur_dari_pembeli());
        MnInputResep.setEnabled(akses.getresep_pulang());
        MnNoResep.setEnabled(akses.getresep_obat());
        MnDiet.setEnabled(akses.getdiet_pasien());
        MnPeriksaLab.setEnabled(akses.getperiksa_lab());
        MnPeriksaRadiologi.setEnabled(akses.getperiksa_radiologi());
        MnOperasi.setEnabled(akses.getoperasi());
        MnDeposit.setEnabled(akses.getdeposit_pasien());
        MnStokObatPasien.setEnabled(akses.getstok_obat_pasien());
        MnResepPulang.setEnabled(akses.getresep_pulang());
        MnRujuk.setEnabled(akses.getrujukan_keluar());
        MnRujukMasuk.setEnabled(akses.getrujukan_masuk());
        MnHapusTagihanOperasi.setEnabled(akses.getoperasi());
        MnHapusObatOperasi.setEnabled(akses.getoperasi());
        MnPenjab.setEnabled(akses.getkamar_inap());
        MnAtasPersetujuanDokter.setEnabled(akses.getkamar_inap());
        MnStatusRujuk.setEnabled(akses.getkamar_inap());
        MnStatusAPS.setEnabled(akses.getkamar_inap());
        MnRanapGabung.setEnabled(akses.getkamar_inap());
        MnStatusMeninggal.setEnabled(akses.getkamar_inap());
        MnKabur.setEnabled(akses.getkamar_inap());
        MnUpdateHari.setEnabled(akses.getkamar_inap());
        MnPerkiraanBiaya.setEnabled(akses.getkamar_inap());
        MnDiagnosa.setEnabled(akses.getdiagnosa_pasien());
        MnDPJP.setEnabled(akses.getdpjp_ranap());
        ppRiwayat.setEnabled(akses.getresume_pasien());
        ppCatatanPasien.setEnabled(akses.getcatatan_pasien());
        ppDataHAIs.setEnabled(akses.getdata_HAIs());
        MnSEP.setEnabled(akses.getbpjs_sep());
        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());
        ppIKP.setEnabled(akses.getinsiden_keselamatan_pasien());
        MnJadwalOperasi.setEnabled(akses.getbooking_operasi());
        MnSKDPBPJS.setEnabled(akses.getskdp_bpjs());
        MnPermintaanLab.setEnabled(akses.getpermintaan_lab());
        MnPermintaanRadiologi.setEnabled(akses.getpermintaan_radiologi());
        MnResepDOkter.setEnabled(akses.getresep_dokter());
        ppResume.setEnabled(akses.getdata_resume_pasien());
        MnPerkiraanBiaya.setEnabled(akses.getperkiraan_biaya_ranap());
        ppAsuhanGizi.setEnabled(akses.getasuhan_gizi());
        ppPasienCorona.setEnabled(akses.getpasien_corona());
        ppPerawatanCorona.setEnabled(akses.getpasien_corona());
        if (akses.getkode().equals("Admin Utama")) {
            MnHapusDataSalah.setEnabled(true);
        } else {
            if (aktifkan_hapus_data_salah.equals("Yes")) {
                MnHapusDataSalah.setEnabled(true);
            } else {
                MnHapusDataSalah.setEnabled(false);
            }
        }
    }

    private void updateHari() {
        if ((R1.isSelected() == true) && (akses.getstatus() == false)) {
            for (i = 0; i < tbKamIn.getRowCount(); i++) {
                if (tbKamIn.getValueAt(i, 13).toString().equals("")) {
                    if (hariawal.equals("Yes")) {
                        Sequel.mengedit(" kamar_inap ", " no_rawat='" + tbKamIn.getValueAt(i, 0).toString() + "' and "
                                + " kd_kamar='"
                                + Sequel.cariIsi(
                                        "select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",
                                        tbKamIn.getValueAt(i, 7).toString())
                                + "' " + " and tgl_masuk='" + tbKamIn.getValueAt(i, 11).toString() + "' and jam_masuk='"
                                + tbKamIn.getValueAt(i, 12).toString()
                                + "' and CAST(`tgl_keluar` AS CHAR(10)) = '0000-00-00'",
                                " lama=if(to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))=0,if(time_to_sec(NOW())-time_to_sec(concat(tgl_masuk,' ',jam_masuk))>(3600*"
                                + lama + "),1,0),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))+1,"
                                + " ttl_biaya=(if(to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))=0,if(time_to_sec(NOW())-time_to_sec(concat(tgl_masuk,' ',jam_masuk))>(3600*"
                                + lama
                                + "),1,0),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))+1)*trf_kamar");
                    } else {
                        Sequel.mengedit(" kamar_inap ", " no_rawat='" + tbKamIn.getValueAt(i, 0).toString() + "' and "
                                + " kd_kamar='"
                                + Sequel.cariIsi(
                                        "select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ",
                                        tbKamIn.getValueAt(i, 7).toString())
                                + "' " + " and tgl_masuk='" + tbKamIn.getValueAt(i, 11).toString() + "' and jam_masuk='"
                                + tbKamIn.getValueAt(i, 12).toString()
                                + "' and CAST(`tgl_keluar` AS CHAR(10)) = '0000-00-00'",
                                " lama=if(to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))=0,if(time_to_sec(NOW())-time_to_sec(concat(tgl_masuk,' ',jam_masuk))>(3600*"
                                + lama + "),1,0),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))),"
                                + " ttl_biaya=if(to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))=0,if(time_to_sec(NOW())-time_to_sec(concat(tgl_masuk,' ',jam_masuk))>(3600*"
                                + lama
                                + "),1,0),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))*trf_kamar");
                    }
                }
            }
        }
        tampil();
        if (chkBaby.isSelected()) {
            tampilBaby();
        }
    }

    /**
     *
     */
    public void setCariKosong() {
        TCari.setText("");
    }

    private void panggilobat(String norawat) {
        if (Sequel.cariInteger("select count(no_rawat) from stok_obat_pasien where no_rawat=? ", norawat) > 0) {
            billing.beriobat.dlgobt2.setNoRm(norawat, DTPCari1.getDate());
            billing.beriobat.dlgobt2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            billing.beriobat.dlgobt2.setLocationRelativeTo(internalFrame1);
            billing.beriobat.dlgobt2.setVisible(true);
        } else {
            billing.beriobat.dlgobt.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            billing.beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
            billing.beriobat.dlgobt.setNoRm(norawat, DTPCari1.getDate(), cmbJam.getSelectedItem().toString(),
                    cmbMnt.getSelectedItem().toString(), cmbDtk.getSelectedItem().toString(), false);
            billing.beriobat.dlgobt.isCek();
            billing.beriobat.dlgobt.tampil();
            billing.beriobat.dlgobt.setVisible(true);
        }
    }

    private void emptyText() {
        txtHak1.setText("0");
        txtHak2.setText("0");
        txtHak3.setText("0");
        txtSelisih.setText("0");
        txtPenjaminan.setText("0");
        txtHak1.requestFocus();
        txtIdCoder.setText("");
    }
}

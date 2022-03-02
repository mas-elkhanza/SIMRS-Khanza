/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import inventory.DlgCariObatResume;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import laporan.DlgDiagnosaPenyakit;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author perpustakaan
 */
public final class RMDataResumePasien extends javax.swing.JDialog {

    private final DefaultTableModel tabMode,tabMode1, tabMode2, tabMode3;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps1, ps2, ps3;
    private ResultSet rs, rs1, rs2, rs3;
    private int i = 0;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private RMCariKeluhan carikeluhan = new RMCariKeluhan(null, false);
    private RMCariHasilRadiologi cariradiologi = new RMCariHasilRadiologi(null, false);
    private DlgCariObatResume obatResume = new DlgCariObatResume(null, false);
    private RMCariHasilLaborat carilaborat = new RMCariHasilLaborat(null, false);
    private RMCariJumlahObat cariobat = new RMCariJumlahObat(null, false);
    private DlgDiagnosaPenyakit penyakit = new DlgDiagnosaPenyakit(null, false);
    Date now = new Date();
    String no_antrian = "";

    /**
     * Creates new form DlgRujuk
     *
     * @param frame
     * @param bln
     */
    public RMDataResumePasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        DateTglPulang.setDate(now);

        tabMode3 = new DefaultTableModel(null, new Object[]{"#", "Kode Barang", "Nama Obat", "Aturan Pakai"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0) || (colIndex == 3)) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tblObatPerawatan.setModel(tabMode3);

        tblObatPerawatan.setPreferredScrollableViewportSize(new Dimension(300, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        for (int j = 0; j < 4; j++) {
            TableColumn column = tblObatPerawatan.getColumnModel().getColumn(j);
            switch (j) {
                case 0:
                    column.setPreferredWidth(5);//tanggal
                    break;
                case 1:
                    column.setPreferredWidth(150);//jam
                    break;
                case 2:
                    column.setPreferredWidth(150);//jam
                    break;
                default:
                    column.setPreferredWidth(150);//jam
                    break;
            }
        }

        tblObatPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2 = new DefaultTableModel(null, new Object[]{"#", "Kode Barang", "Nama Obat", "Aturan Pakai"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0) || (colIndex == 3)) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tblObatSelamadiRumah.setModel(tabMode2);

        for (int j = 0; j < 4; j++) {
            TableColumn column = tblObatSelamadiRumah.getColumnModel().getColumn(j);
            switch (j) {
                case 0:
                    column.setPreferredWidth(5);//jam
                    break;
                case 1:
                    column.setPreferredWidth(150);//jam
                    break;
                case 2:
                    column.setPreferredWidth(150);//jam
                    break;
                default:
                    column.setPreferredWidth(150);//jam
                    break;
            }
        }
        tblObatSelamadiRumah.setDefaultRenderer(Object.class, new WarnaTable());

        tblObatSelamadiRumah.setPreferredScrollableViewportSize(new Dimension(300, 500));
        tblObatSelamadiRumah.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbObat.setPreferredScrollableViewportSize(new Dimension(300, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tabMode1 = new DefaultTableModel(null, new Object[]{"ICD", "Diagnosa"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblDiagnosa.setModel(tabMode1);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tblDiagnosa.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        for (int j = 0; j < 2; j++) {
            TableColumn column = tblDiagnosa.getColumnModel().getColumn(j);
            switch (j) {
                case 0:
                    column.setPreferredWidth(75);//tanggal
                    column.setCellRenderer(centerRenderer);
                    break;
                case 1:
                    column.setPreferredWidth(60);//jam
                    column.setCellRenderer(leftRenderer);
                    break;
                default:
                    break;
            }
        }

        tblDiagnosa.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode = new DefaultTableModel(null, new Object[]{
            "Tgl.Rawat", "Status", "No.Rawat", "No.RM", "Nama Pasien", "Kode Dokter", "Dokter Penanggung Jawab", "Kondisi Pulang", "Keluhan utama riwayat penyakit yang postif",
            "Jalannya penyakit selama perawatan", "Pemeriksaan penunjang yang positif", "Hasil laboratorium yang positif", "Diagnosa Utama", "ICD10 Utama", "Diagnosa Sekunder 1",
            "ICD10 Sek 1", "Diagnosa Sekunder 2", "ICD10 Sek 2", "Diagnosa Sekunder 3", "ICD10 Sek 3", "Diagnosa Sekunder 4", "ICD10 Sek 4", "Prosedur Utama", "ICD9 Utama",
            "Prosedur Sekunder 1", "ICD9 Sek1", "Prosedur Sekunder 2", "ICD9 Sek2", "Prosedur Sekunder 3", "ICD9 Sek3", "Kondisi Pulang", "Riwayat Penyakit", "Tindakan di RS", "Alasan Pulang", "Pemeriksaan Fisik", "Penyakit Penyerta", "nomor", "Tindakan Perawatan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 33; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(65);
            } else if (i == 1) {
                column.setPreferredWidth(40);
            } else if (i == 2) {
                column.setPreferredWidth(105);
            } else if (i == 3) {
                column.setPreferredWidth(65);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(250);
            } else if (i == 9) {
                column.setPreferredWidth(250);
            } else if (i == 10) {
                column.setPreferredWidth(250);
            } else if (i == 11) {
                column.setPreferredWidth(250);
            } else if (i == 12) {
                column.setPreferredWidth(170);
            } else if (i == 13) {
                column.setPreferredWidth(75);
            } else if (i == 14) {
                column.setPreferredWidth(170);
            } else if (i == 15) {
                column.setPreferredWidth(75);
            } else if (i == 16) {
                column.setPreferredWidth(170);
            } else if (i == 17) {
                column.setPreferredWidth(75);
            } else if (i == 18) {
                column.setPreferredWidth(170);
            } else if (i == 19) {
                column.setPreferredWidth(75);
            } else if (i == 20) {
                column.setPreferredWidth(170);
            } else if (i == 21) {
                column.setPreferredWidth(75);
            } else if (i == 22) {
                column.setPreferredWidth(170);
            } else if (i == 23) {
                column.setPreferredWidth(75);
            } else if (i == 24) {
                column.setPreferredWidth(170);
            } else if (i == 25) {
                column.setPreferredWidth(75);
            } else if (i == 26) {
                column.setPreferredWidth(170);
            } else if (i == 27) {
                column.setPreferredWidth(75);
            } else if (i == 28) {
                column.setPreferredWidth(170);
            } else if (i == 29) {
                column.setPreferredWidth(75);
            } else if (i == 30) {
                column.setPreferredWidth(250);
            } else if (i == 31) {
                column.setPreferredWidth(75);
            } else if (i == 32) {
                column.setPreferredWidth(75);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
//        Obat2an.setDocument(new batasInput((int) 1000).getKata(Obat2an));
//        DiagnosaUtama.setDocument(new batasInput((int) 80).getKata(DiagnosaUtama));
//        DiagnosaSekunder1.setDocument(new batasInput((int) 80).getKata(DiagnosaSekunder1));
//        DiagnosaSekunder2.setDocument(new batasInput((int) 80).getKata(DiagnosaSekunder2));
//        DiagnosaSekunder3.setDocument(new batasInput((int) 80).getKata(DiagnosaSekunder3));
//        DiagnosaSekunder4.setDocument(new batasInput((int) 80).getKata(DiagnosaSekunder4));
//        KodeDiagnosaUtama.setDocument(new batasInput((int) 10).getKata(KodeDiagnosaUtama));
//        KodeDiagnosaSekunder1.setDocument(new batasInput((int) 10).getKata(KodeDiagnosaSekunder1));
//        KodeDiagnosaSekunder2.setDocument(new batasInput((int) 10).getKata(KodeDiagnosaSekunder2));
//        KodeDiagnosaSekunder3.setDocument(new batasInput((int) 10).getKata(KodeDiagnosaSekunder3));
//        KodeDiagnosaSekunder4.setDocument(new batasInput((int) 10).getKata(KodeDiagnosaSekunder4));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                        tampilObat();
                        tampilObatPulang();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                        tampilObat();
                        tampilObatPulang();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                        tampilObat();
                        tampilObatPulang();
                    }
                }
            });
        }

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KodeDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    KeluhanUtama.requestFocus();
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

        carikeluhan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (carikeluhan.getTable().getSelectedRow() != -1) {
                    JalannyaPenyakit.append(carikeluhan.getTable().getValueAt(carikeluhan.getTable().getSelectedRow(), 2).toString() + ", ");
                    PemeriksaanFisik.append(carikeluhan.getTable().getValueAt(carikeluhan.getTable().getSelectedRow(), 3).toString() + ", ");
                    JalannyaPenyakit.requestFocus();
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

        cariradiologi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (cariradiologi.getTable().getSelectedRow() != -1) {
                    PemeriksaanPenunjang.append(cariradiologi.getTable().getValueAt(cariradiologi.getTable().getSelectedRow(), 2).toString() + ", ");
                    PemeriksaanPenunjang.requestFocus();
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

        carilaborat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (carilaborat.getTable().getSelectedRow() != -1) {
                    HasilLaborat.append(carilaborat.getTable().getValueAt(carilaborat.getTable().getSelectedRow(), 2).toString() + ", ");
                    HasilLaborat.requestFocus();
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

        cariobat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (cariobat.getTable().getSelectedRow() != -1) {
//                    Obat2an.append(cariobat.getTable().getValueAt(cariobat.getTable().getSelectedRow(), 2).toString() + ", ");
//                    Obat2an.requestFocus();
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

        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                tampil();
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

        ChkInput.setSelected(false);
        isForm();
        isCek();
        setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        setLocationRelativeTo(internalFrame1);
        isDiagnosaAhir();
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
        MnLaporanResume = new javax.swing.JMenuItem();
        MnInputDiagnosa = new javax.swing.JMenuItem();
        jLabel10 = new widget.Label();
        penyakitPenyerta = new widget.TextBox();
        scrollPane5 = new widget.ScrollPane();
        HasilLaborat = new widget.TextArea();
        jLabel16 = new widget.Label();
        dialogObat = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblObat = new javax.swing.JTable();
        internalFrame1 = new widget.InternalFrame();
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
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel8 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        JalannyaPenyakit = new widget.TextArea();
        scrollPane4 = new widget.ScrollPane();
        PemeriksaanFisik = new widget.TextArea();
        jLabel29 = new widget.Label();
        label14 = new widget.Label();
        KeluhanUtama = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        BtnDokter1 = new widget.Button();
        jLabel13 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        riwayatPenyakit = new widget.TextArea();
        KodeDokter = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDiagnosa = new widget.Table();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        BtnDokter2 = new widget.Button();
        scrollPane6 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        jLabel12 = new widget.Label();
        Kondisi = new widget.ComboBox();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblObatPerawatan = new widget.Table();
        jLabel31 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblObatSelamadiRumah = new widget.Table();
        jLabel11 = new widget.Label();
        jLabel17 = new widget.Label();
        tindakanRS = new widget.TextBox();
        jLabel18 = new widget.Label();
        kondisiSaatPulang = new widget.TextBox();
        tindakanLanjut = new widget.TextBox();
        jLabel20 = new widget.Label();
        DateRencanaKon = new widget.Tanggal1();
        jLabel22 = new widget.Label();
        DateTglPulang = new widget.Tanggal1();
        jLabel23 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        BtnTambahObat = new widget.Button();
        nomor = new widget.TextBox();
        jLabel9 = new widget.Label();
        BtnTambahObat1 = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLaporanResume.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResume.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResume.setText("Laporan Resume Pasien");
        MnLaporanResume.setName("MnLaporanResume"); // NOI18N
        MnLaporanResume.setPreferredSize(new java.awt.Dimension(220, 26));
        MnLaporanResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanResumeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanResume);

        MnInputDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        MnInputDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputDiagnosa.setForeground(new java.awt.Color(50, 50, 50));
        MnInputDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputDiagnosa.setText("Input Diagnosa Pasien");
        MnInputDiagnosa.setName("MnInputDiagnosa"); // NOI18N
        MnInputDiagnosa.setPreferredSize(new java.awt.Dimension(220, 26));
        MnInputDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputDiagnosaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnInputDiagnosa);

        jLabel10.setText("Penyakit Penyerta :");
        jLabel10.setName("jLabel10"); // NOI18N

        penyakitPenyerta.setText("-");
        penyakitPenyerta.setName("penyakitPenyerta"); // NOI18N
        penyakitPenyerta.setPreferredSize(new java.awt.Dimension(80, 23));
        penyakitPenyerta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                penyakitPenyertaActionPerformed(evt);
            }
        });
        penyakitPenyerta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                penyakitPenyertaKeyPressed(evt);
            }
        });

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        HasilLaborat.setBackground(new java.awt.Color(255, 255, 204));
        HasilLaborat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        HasilLaborat.setColumns(20);
        HasilLaborat.setRows(5);
        HasilLaborat.setName("HasilLaborat"); // NOI18N
        HasilLaborat.setOpaque(true);
        HasilLaborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilLaboratKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(HasilLaborat);

        jLabel16.setText("Hasil laboratorium :");
        jLabel16.setName("jLabel16"); // NOI18N

        dialogObat.setName("dialogObat"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Obat, Alkes/BHP"));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tblObat.setModel(new javax.swing.table.DefaultTableModel(
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
        tblObat.setName("tblObat"); // NOI18N
        jScrollPane4.setViewportView(tblObat);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout dialogObatLayout = new javax.swing.GroupLayout(dialogObat.getContentPane());
        dialogObat.getContentPane().setLayout(dialogObatLayout);
        dialogObatLayout.setHorizontalGroup(
            dialogObatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogObatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dialogObatLayout.setVerticalGroup(
            dialogObatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogObatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Resume Medis Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass8.add(BtnSimpan);

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
        panelGlass8.add(BtnBatal);

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
        panelGlass8.add(BtnHapus);

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
        panelGlass8.add(BtnEdit);

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
        panelGlass8.add(BtnPrint);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

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
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-03-2022" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-03-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariActionPerformed(evt);
            }
        });
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 848));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

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

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 1000));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1000));
        FormInput.setLayout(null);

        jLabel4.setText("Keluhan utama :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(10, 70, 130, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(151, 10, 140, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(410, 10, 210, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(300, 10, 100, 23);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(10, 10, 130, 23);

        jLabel8.setText("Riwayat Penyakit :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(10, 160, 130, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        JalannyaPenyakit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        JalannyaPenyakit.setColumns(20);
        JalannyaPenyakit.setRows(5);
        JalannyaPenyakit.setName("JalannyaPenyakit"); // NOI18N
        JalannyaPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalannyaPenyakitKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(JalannyaPenyakit);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(151, 100, 640, 50);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        PemeriksaanFisik.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanFisik.setColumns(20);
        PemeriksaanFisik.setRows(5);
        PemeriksaanFisik.setName("PemeriksaanFisik"); // NOI18N
        PemeriksaanFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanFisikKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(PemeriksaanFisik);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(150, 310, 640, 50);

        jLabel29.setText("Obat Selama di Rumah :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(10, 780, 130, 23);

        label14.setText("Dokter P.J. :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(10, 40, 130, 23);

        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.setPreferredSize(new java.awt.Dimension(80, 23));
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        FormInput.add(KeluhanUtama);
        KeluhanUtama.setBounds(150, 70, 640, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(270, 40, 210, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
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
        BtnDokter.setBounds(480, 40, 28, 23);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter1.setMnemonic('2');
        BtnDokter1.setToolTipText("Alt+2");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter1);
        BtnDokter1.setBounds(110, 120, 28, 23);

        jLabel13.setText("Alasan Dirawat :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(10, 100, 130, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        riwayatPenyakit.setBackground(new java.awt.Color(255, 255, 204));
        riwayatPenyakit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        riwayatPenyakit.setColumns(20);
        riwayatPenyakit.setRows(5);
        riwayatPenyakit.setName("riwayatPenyakit"); // NOI18N
        riwayatPenyakit.setOpaque(true);
        riwayatPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                riwayatPenyakitKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(riwayatPenyakit);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(150, 160, 640, 50);

        KodeDokter.setEditable(false);
        KodeDokter.setName("KodeDokter"); // NOI18N
        KodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterKeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(151, 40, 110, 23);

        scrollPane1.setName("scrollPane1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblDiagnosa.setAutoCreateRowSorter(true);
        tblDiagnosa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tblDiagnosa.setName("tblDiagnosa"); // NOI18N
        tblDiagnosa.getTableHeader().setReorderingAllowed(false);
        tblDiagnosa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDiagnosaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDiagnosa);

        scrollPane1.setViewportView(jScrollPane1);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(150, 490, 640, 120);

        jLabel14.setText("Pemeriksaan Fisik :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(10, 310, 130, 23);

        jLabel15.setText("Tindakan Selama di RS :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(10, 370, 130, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("Alt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(110, 250, 28, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        PemeriksaanPenunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanPenunjang.setColumns(20);
        PemeriksaanPenunjang.setRows(5);
        PemeriksaanPenunjang.setName("PemeriksaanPenunjang"); // NOI18N
        PemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(PemeriksaanPenunjang);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(150, 220, 640, 80);

        jLabel12.setText("Alasan Pasien Pulang :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(10, 460, 130, 23);

        Kondisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Atas Persetujuan Dokter", "Atas Permintaan Sendiri", "Rujuk", "Meninggal", "Kabur", "-" }));
        Kondisi.setName("Kondisi"); // NOI18N
        Kondisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKeyPressed(evt);
            }
        });
        FormInput.add(Kondisi);
        Kondisi.setBounds(150, 460, 160, 23);

        jLabel30.setText("Diagnosa Akhir :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(10, 490, 130, 23);

        scrollPane2.setName("scrollPane2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tblObatPerawatan.setAutoCreateRowSorter(true);
        tblObatPerawatan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblObatPerawatan.setName("tblObatPerawatan"); // NOI18N
        tblObatPerawatan.getTableHeader().setReorderingAllowed(false);
        tblObatPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblObatPerawatanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblObatPerawatan);

        scrollPane2.setViewportView(jScrollPane2);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(150, 620, 640, 150);

        jLabel31.setText("Obat Selama Perawatan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(10, 620, 130, 23);

        scrollPane8.setName("scrollPane8"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tblObatSelamadiRumah.setAutoCreateRowSorter(true);
        tblObatSelamadiRumah.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tblObatSelamadiRumah.setName("tblObatSelamadiRumah"); // NOI18N
        tblObatSelamadiRumah.getTableHeader().setReorderingAllowed(false);
        tblObatSelamadiRumah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblObatSelamadiRumahMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblObatSelamadiRumah);

        scrollPane8.setViewportView(jScrollPane3);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(150, 780, 640, 120);

        jLabel11.setText("Pemeriksaan penunjang :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(10, 220, 130, 23);

        jLabel17.setText("Kondisi Saat Pulang :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(10, 400, 130, 23);

        tindakanRS.setText("-");
        tindakanRS.setName("tindakanRS"); // NOI18N
        tindakanRS.setPreferredSize(new java.awt.Dimension(80, 23));
        tindakanRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tindakanRSActionPerformed(evt);
            }
        });
        tindakanRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tindakanRSKeyPressed(evt);
            }
        });
        FormInput.add(tindakanRS);
        tindakanRS.setBounds(150, 370, 640, 23);

        jLabel18.setText("Tindak Lanjut :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(10, 430, 130, 23);

        kondisiSaatPulang.setText("-");
        kondisiSaatPulang.setName("kondisiSaatPulang"); // NOI18N
        kondisiSaatPulang.setPreferredSize(new java.awt.Dimension(80, 23));
        kondisiSaatPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kondisiSaatPulangActionPerformed(evt);
            }
        });
        kondisiSaatPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kondisiSaatPulangKeyPressed(evt);
            }
        });
        FormInput.add(kondisiSaatPulang);
        kondisiSaatPulang.setBounds(150, 400, 640, 23);

        tindakanLanjut.setText("-");
        tindakanLanjut.setName("tindakanLanjut"); // NOI18N
        tindakanLanjut.setPreferredSize(new java.awt.Dimension(80, 23));
        tindakanLanjut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tindakanLanjutActionPerformed(evt);
            }
        });
        tindakanLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tindakanLanjutKeyPressed(evt);
            }
        });
        FormInput.add(tindakanLanjut);
        tindakanLanjut.setBounds(150, 430, 640, 23);

        jLabel20.setText("Rencana Kontrol :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(510, 40, 110, 23);

        DateRencanaKon.setName("DateRencanaKon"); // NOI18N
        DateRencanaKon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DateRencanaKonKeyPressed(evt);
            }
        });
        FormInput.add(DateRencanaKon);
        DateRencanaKon.setBounds(630, 40, 160, 20);

        jLabel22.setText("Tanggal Pulang :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(310, 460, 100, 23);

        DateTglPulang.setName("DateTglPulang"); // NOI18N
        DateTglPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DateTglPulangKeyPressed(evt);
            }
        });
        FormInput.add(DateTglPulang);
        DateTglPulang.setBounds(410, 460, 130, 20);

        jLabel23.setText("Jam Pulang :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(540, 460, 70, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(620, 460, 50, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "00", " " }));
        Menit.setName("Menit"); // NOI18N
        Menit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenitActionPerformed(evt);
            }
        });
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(680, 460, 50, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "00", " " }));
        Detik.setName("Detik"); // NOI18N
        Detik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DetikActionPerformed(evt);
            }
        });
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(740, 460, 50, 23);

        BtnTambahObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnTambahObat.setMnemonic('2');
        BtnTambahObat.setToolTipText("Alt+2");
        BtnTambahObat.setName("BtnTambahObat"); // NOI18N
        BtnTambahObat.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahObatActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahObat);
        BtnTambahObat.setBounds(110, 640, 30, 23);

        nomor.setHighlighter(null);
        nomor.setName("nomor"); // NOI18N
        nomor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nomorKeyPressed(evt);
            }
        });
        FormInput.add(nomor);
        nomor.setBounds(670, 10, 120, 23);

        jLabel9.setText("Nomor :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(620, 10, 50, 23);

        BtnTambahObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnTambahObat1.setMnemonic('2');
        BtnTambahObat1.setToolTipText("Alt+2");
        BtnTambahObat1.setName("BtnTambahObat1"); // NOI18N
        BtnTambahObat1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahObat1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahObat1);
        BtnTambahObat1.setBounds(110, 800, 30, 30);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
            isPsien();
        } else {
            Valid.pindah(evt, TCari, BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt, TCari, BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        String y = Sequel.cariIsi("select no_rawat from resume_pasien where no_rawat='" + TNoRw.getText() + "'");
        if (y.isBlank()) {
            Date tgl = DateRencanaKon.getDate();
            String tgl_kontrol = "0000-00-00";
            if (tgl == null) {
                tgl_kontrol = "0000-00-00";
            } else {
                tgl_kontrol = Valid.SetDateToString(tgl);
            }
            System.out.println("tgl " + tgl_kontrol);
            if (TNoRw.getText().equals("") || TNoRM.getText().equals("") || TPasien.getText().equals("")) {
                Valid.textKosong(TNoRw, "Pasien");
            } else if (KeluhanUtama.getText().equals("") || NamaDokter.getText().equals("")) {
                Valid.textKosong(BtnDokter, "Dokter Penanggung Jawab");
            } else if (KeluhanUtama.getText().equals("") || NamaDokter.getText().equals("")) {
                Valid.textKosong(BtnDokter, "Dokter Penanggung Jawab");
            } else if (KeluhanUtama.getText().equals("")) {
                Valid.textKosong(KeluhanUtama, "Keluhan utama riwayat penyakit yang postif");
            } else if (JalannyaPenyakit.getText().equals("")) {
                Valid.textKosong(JalannyaPenyakit, "Jalannya penyakit selama perawatan");
//            } else if (tblDiagnosa.getModel().getValueAt(0, 1).toString().equals("")) {
//                JOptionPane.showMessageDialog(rootPane, "Diagnosa Utama Masih Kosong.");
            } else {
                Date now1 = new Date();
                String dateNow = Valid.SetDateToString(now1);
                int no = Sequel.cariInteger("Select max(noresume) from resume_pasien where tgl_resume like '" + Valid.SetDateToString(now).substring(0, 8) + "%'");
                System.out.println("no_antrian resume :" + no);
//            String no_antrian = withLargeIntegers(no + 1) + "/RSUIHA/" + Valid.RomanNumerals(Integer.parseInt(dateNow.substring(5, 6))) + "/RANAP/" + dateNow.substring(0, 4);

                if (Sequel.menyimpantf("resume_pasien", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 36, new String[]{
                    TNoRw.getText(), KodeDokter.getText(), KeluhanUtama.getText(), JalannyaPenyakit.getText(), PemeriksaanPenunjang.getText(), HasilLaborat.getText(),
                    tblDiagnosa.getModel().getValueAt(0, 1).toString(), tblDiagnosa.getModel().getValueAt(0, 0).toString(),
                    tblDiagnosa.getModel().getValueAt(1, 1).toString(), tblDiagnosa.getModel().getValueAt(1, 0).toString(),
                    tblDiagnosa.getModel().getValueAt(2, 1).toString(), tblDiagnosa.getModel().getValueAt(2, 0).toString(),
                    tblDiagnosa.getModel().getValueAt(3, 1).toString(), tblDiagnosa.getModel().getValueAt(3, 0).toString(),
                    tblDiagnosa.getModel().getValueAt(4, 1).toString(), tblDiagnosa.getModel().getValueAt(4, 0).toString(),
                    "", "", "", "", "", "", "", "", kondisiSaatPulang.getText(), riwayatPenyakit.getText(), tindakanLanjut.getText(), PemeriksaanFisik.getText(),
                    Kondisi.getSelectedItem().toString(), penyakitPenyerta.getText(), nomor.getText(), Valid.SetDateToString(now), tindakanRS.getText(), tgl_kontrol,
                    Valid.SetDateToString(DateTglPulang.getDate()), Jam.getSelectedItem().toString() + ":" + Menit.getSelectedItem().toString() + ":" + Detik.getSelectedItem().toString()
                }) == true) {
                    for (int j = 0; j < tblObatPerawatan.getRowCount(); j++) {
                        if (tblObatPerawatan.getModel().getValueAt(j, 0).equals(true)) {
                            Sequel.menyimpan("obat_rs_resume",
                                    "'" + TNoRw.getText() + "','" + Valid.SetDateToString(now) + "','"
                                    + tblObatPerawatan.getModel().getValueAt(j, 1).toString() + "','"
                                    + tblObatPerawatan.getModel().getValueAt(j, 2).toString() + "','"
                                    + tblObatPerawatan.getModel().getValueAt(j, 3).toString() + "',''");
                        }
                    }
                    for (int j = 0; j < tblObatSelamadiRumah.getRowCount(); j++) {
                        if (tblObatSelamadiRumah.getModel().getValueAt(j, 0).equals(true)) {
                            Sequel.menyimpan("obat_pulang_resume",
                                    "'" + TNoRw.getText() + "','" + Valid.SetDateToString(now) + "','"
                                    + tblObatSelamadiRumah.getModel().getValueAt(j, 1).toString() + "','"
                                    + tblObatSelamadiRumah.getModel().getValueAt(j, 2).toString() + "','"
                                    + tblObatSelamadiRumah.getModel().getValueAt(j, 3).toString() + "',''");
                        }
                    }
                    tampil();
                    tampilObat();
                    tampilObatPulang();
                    emptTeks();

                    JOptionPane.showMessageDialog(rootPane, "Simpan Data Berhasil");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Data Resume udah ada!");
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        }
//        else {
//            Valid.pindah(evt, Obat2an, BtnBatal);
//        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
        Date now1 = new Date();
        String dateNow = Valid.SetDateToString(now1);
        int no = Sequel.cariInteger("Select max(noresume) from resume_pasien where tgl_resume like '" + Valid.SetDateToString(now).substring(0, 8) + "%'");
        no_antrian = withLargeIntegers(no + 1) + "/RSUIHA/RANAP/" + Valid.RomanNumerals(Integer.parseInt(Valid.SetDateToString(DateTglPulang.getDate()).substring(5, 6))) + "/" + dateNow.substring(0, 4);
        nomor.setText(no_antrian);
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            if (Sequel.queryu2tf("delete from resume_pasien where no_rawat=? and kd_dokter=?", 2, new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString()
            }) == true) {
                Sequel.meghapus("obat_rs_resume", "no_rawat", tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
                Sequel.meghapus("obat_pulang_resume", "no_rawat", tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
                tampil();
                emptTeks();
                JOptionPane.showMessageDialog(null, "Berhasil menghapus.");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
        }

}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        Date tgl = DateRencanaKon.getDate();
        System.out.println("Tanggal kontrol:" + tgl);
        String tgl_kontrol = "0000-00-00";
        if (tgl == null) {
            tgl_kontrol = "0000-00-00";
        } else {
            tgl_kontrol = Valid.SetDateToString(tgl);
        }
        System.out.println("Tanggal kontrol2:" + tgl_kontrol);
        if (TNoRw.getText().equals("") || TNoRM.getText().equals("") || TPasien.getText().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (KeluhanUtama.getText().equals("") || NamaDokter.getText().equals("")) {
            Valid.textKosong(BtnDokter, "Dokter Penanggung Jawab");
        } else if (KeluhanUtama.getText().equals("")) {
            Valid.textKosong(KeluhanUtama, "Keluhan utama riwayat penyakit yang postif");
        } else if (JalannyaPenyakit.getText().equals("")) {
            Valid.textKosong(JalannyaPenyakit, "Jalannya penyakit selama perawatan");
        } else if (tblDiagnosa.getModel().getValueAt(0, 1).toString().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Diagnosa Utama Masih Kosong.");
        } else {
//            if (tbObat.getSelectedRow() > -1) {
            if (Sequel.queryu2tf("UPDATE "
                    + "  `resume_pasien` "
                    + "SET "
                    + "  `kd_dokter` = ?,"
                    + "  `keluhan_utama` = ?,"
                    + "  `jalannya_penyakit` = ?,"
                    + "  `pemeriksaan_penunjang` = ?,"
                    + "  `hasil_laborat` = ?,"
                    + "  `kondisi_pulang` = ?,"
                    + "  `riwayat_penyakit` = ?,"
                    + "  `tindakan_perawatan` = ?,"
                    + "  `pemeriksaan_fisik` = ?,"
                    + "  `alasan_pulang` = ?,"
                    + "  `tindakan_rs` = ?,"
                    + "  `tgl_kontrol` = ?,"
                    + "  `tgl_pulang` = ?,"
                    + "  `jam_pulang` = ?, "
                    + " kd_diagnosa_utama=?,diagnosa_utama=?,"
                    + "kd_diagnosa_sekunder=?,diagnosa_sekunder=?,"
                    + "kd_diagnosa_sekunder2=?,diagnosa_sekunder2=?,"
                    + "kd_diagnosa_sekunder3=?,diagnosa_sekunder3=?,"
                    + "kd_diagnosa_sekunder4=?,diagnosa_sekunder4=?, "
                    + "noresume=?"
                    + " where no_rawat=? ", 26, new String[]{
                        KodeDokter.getText(), KeluhanUtama.getText(),
                        JalannyaPenyakit.getText(), PemeriksaanPenunjang.getText(), HasilLaborat.getText(),
                        kondisiSaatPulang.getText(), riwayatPenyakit.getText(), tindakanLanjut.getText(),
                        PemeriksaanFisik.getText(), Kondisi.getSelectedItem().toString(),
                        tindakanRS.getText(), tgl_kontrol,
                        Valid.SetDateToString(DateTglPulang.getDate()), Jam.getSelectedItem().toString() + ':' + Menit.getSelectedItem().toString() + ':' + Detik.getSelectedItem().toString(),
                        tblDiagnosa.getModel().getValueAt(0, 0).toString(), tblDiagnosa.getModel().getValueAt(0, 1).toString(),
                        tblDiagnosa.getModel().getValueAt(1, 0).toString(), tblDiagnosa.getModel().getValueAt(1, 1).toString(),
                        tblDiagnosa.getModel().getValueAt(2, 0).toString(), tblDiagnosa.getModel().getValueAt(2, 1).toString(),
                        tblDiagnosa.getModel().getValueAt(3, 0).toString(), tblDiagnosa.getModel().getValueAt(3, 1).toString(),
                        tblDiagnosa.getModel().getValueAt(4, 0).toString(), tblDiagnosa.getModel().getValueAt(4, 1).toString(),
                        nomor.getText(), TNoRw.getText()
                    }) == true) {
                if (Sequel.queryutf("DELETE FROM obat_rs_resume WHERE no_rawat = '" + TNoRw.getText() + "'") == true) {
                    for (int j = 0; j < tblObatPerawatan.getRowCount(); j++) {
                        if (tblObatPerawatan.getModel().getValueAt(j, 0).equals(true)) {
                            Sequel.menyimpan("obat_rs_resume",
                                    "'" + TNoRw.getText() + "','" + Valid.SetDateToString(now) + "','"
                                    + tblObatPerawatan.getModel().getValueAt(j, 1).toString() + "','"
                                    + tblObatPerawatan.getModel().getValueAt(j, 2).toString() + "','"
                                    + tblObatPerawatan.getModel().getValueAt(j, 3).toString() + "',''");
                        }
                    }
                }
                if (Sequel.queryutf("DELETE FROM obat_pulang_resume WHERE no_rawat = '" + TNoRw.getText() + "'") == true) {
                    for (int j = 0; j < tblObatSelamadiRumah.getRowCount(); j++) {
                        if (tblObatSelamadiRumah.getModel().getValueAt(j, 0).equals(true)) {
                            Sequel.menyimpan("obat_pulang_resume",
                                    "'" + TNoRw.getText() + "','" + Valid.SetDateToString(now) + "','"
                                    + tblObatSelamadiRumah.getModel().getValueAt(j, 1).toString() + "','"
                                    + tblObatSelamadiRumah.getModel().getValueAt(j, 2).toString() + "','"
                                    + tblObatSelamadiRumah.getModel().getValueAt(j, 3).toString() + "',''");
                        }
                    }
                }
                tampil();
                emptTeks();
                JOptionPane.showMessageDialog(rootPane, "Ubah Data Berhasil");
            }
//            } else {
//                JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
//            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dokter.dispose();
        carikeluhan.dispose();
        carilaborat.dispose();
        cariobat.dispose();
        cariradiologi.dispose();
        penyakit.dispose();
        obatResume.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed
    private void getPathReport(String reportDirName, String reportName) {
        Properties systemProp = System.getProperties();

        // Ambil current dir
        String currentDir = systemProp.getProperty("user.dir");

        File dir = new File(currentDir);

        File fileRpt;
        String fullPath = "";
        if (dir.isDirectory()) {
            String[] isiDir = dir.list();
            for (String iDir : isiDir) {
                fileRpt = new File(currentDir + File.separatorChar + iDir + File.separatorChar + reportDirName
                        + File.separatorChar + reportName);
                if (fileRpt.isFile()) { // Cek apakah file RptMaster.jrxml ada
                    fullPath = fileRpt.toString();
                    System.out.println("Found Report File at : " + fullPath);
                } // end if
            } // end for i
        } // end if
    }

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (TNoRw.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Maaf, Tidak ada data yang dipilih!");
        } else {
            if (tabMode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                BtnBatal.requestFocus();
            } else if (tabMode.getRowCount() != 0) {
                print();
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    public static String withLargeIntegers(int value) {
        String a = String.format("%06d", value);
        return a;
    }

    private void print() {
        String reportName = "subResumeObat.jasper", reportDirName = "report";
        try {
            String path = "./" + reportDirName + "/" + reportName;
            String reports = "./" + reportDirName + "/rptResumeRanap.jasper";
            Map param = new HashMap();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("no_rawat", TNoRw.getText());
            param.put("cari", "%" + TCari.getText() + "%");
            param.put("SUBREPORT_DIR", path);

            param.put("tgl_awal", Valid.SetDateToString(DTPCari1.getDate()));
            param.put("tgl_ahir", Valid.SetDateToString(DTPCari2.getDate()));
            System.out.println("Param " + param);
            System.out.println("Path " + path);
            JasperPrint print = JasperFillManager.fillReport(reports, param, koneksi);
            JasperViewer.viewReport(print, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Dokumen Tidak Ada" + ex);
            System.out.println("Error " + ex);
        }
    }

    private void PrintResume() {
        List<String> listKategori = new ArrayList<>();
        reportSub(listKategori);
        String reportName = "subResumeObat.jasper", reportDirName = "report";
        getPathReport(reportDirName, reportName);
        String namafile = "./" + reportDirName + "/" + reportName;
        //File reportDir = new File(namafile); //relative path
        //String absolutePath = reportDir.getAbsolutePath(); //Convert to absolute
        System.out.println("Nama File=" + namafile);
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        param.put("no_rawat", TNoRw.getText());
        param.put("SUBREPORT_DIR", namafile);
        param.put("dataSourceObat", listKategori);
        param.put("tgl_awal", Valid.SetDateToString(DTPCari1.getDate()));
        param.put("tgl_ahir", Valid.SetDateToString(DTPCari2.getDate()));
//            param.put("SUBREPORT_DIR", namafile);
        String no_antrian = Sequel.cariIsi("Select max(no_antrian) from skdp_bpjs where no_rkm_medis='" + TNoRM.getText() + "' and status='menunggu'");
        String bln = Sequel.cariIsi("Select max(tanggal_rujukan) from skdp_bpjs where no_rkm_medis='" + TNoRM.getText() + "' and status='menunggu'");
//        param.put("no_resume", no_antrian + "/RSUIHA/" + Valid.RomanNumerals(Integer.parseInt(bln.substring(5, 6))) + "/RANAP/" + bln.substring(0, 4));
        if (TCari.getText().equals("")) {
            Valid.MyReportqry("rptResumeRanap.jasper", "report", "::[ Data Resume Pasien ]::",
                    "SELECT reg_periksa.tgl_registrasi,"
                    + "	reg_periksa.no_rawat,"
                    + "	reg_periksa.status_lanjut,"
                    + "	reg_periksa.no_rkm_medis,"
                    + "	pasien.nm_pasien,"
                    + "	pasien.tgl_lahir,"
                    + "	pasien.alamat,"
                    + "	pasien.jk,"
                    + "	kamar_inap.tgl_masuk,"
                    + "	kamar_inap.tgl_keluar,"
                    + "	kamar_inap.diagnosa_awal,"
                    + "	bangsal.nm_bangsal,"
                    + "	resume_pasien.kd_dokter,"
                    + "	dokter.nm_dokter,"
                    + "	resume_pasien.kondisi_pulang,"
                    + "	resume_pasien.keluhan_utama,"
                    + "	resume_pasien.jalannya_penyakit,"
                    + "	resume_pasien.pemeriksaan_penunjang,"
                    + "	resume_pasien.hasil_laborat,"
                    + "	resume_pasien.diagnosa_utama,"
                    + "	resume_pasien.kd_diagnosa_utama,"
                    + "	resume_pasien.diagnosa_sekunder,"
                    + "	resume_pasien.kd_diagnosa_sekunder,"
                    + "	resume_pasien.diagnosa_sekunder2,"
                    + "	resume_pasien.kd_diagnosa_sekunder2,"
                    + "	resume_pasien.diagnosa_sekunder3,"
                    + "	resume_pasien.kd_diagnosa_sekunder3,"
                    + "	resume_pasien.diagnosa_sekunder4,"
                    + "	resume_pasien.kd_diagnosa_sekunder4,"
                    + "	resume_pasien.riwayat_penyakit,"
                    + "	pemeriksaan_ranap.keluhan,"
                    + "	pemeriksaan_ranap.pemeriksaan,"
                    + "	resume_pasien.tindakan_perawatan,"
                    + "	resep_obat.tgl_peresepan,"
                    + "	aturan_pakai.aturan AS aturan_pakai,"
                    + "	resep_pulang.aturan_pakai AS aturan_pakai_pulang "
                    + "FROM resume_pasien"
                    + "	inner join reg_periksa ON "
                    + "	 resume_pasien.no_rawat = reg_periksa.no_rawat "
                    + "	inner join pasien ON "
                    + "	 reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                    + "	inner join dokter ON "
                    + "	resume_pasien.kd_dokter = dokter.kd_dokter "
                    + "	inner join kamar_inap ON "
                    + "	reg_periksa.no_rawat = kamar_inap.no_rawat "
                    + "	inner join kamar ON "
                    + "	kamar.kd_kamar = kamar_inap.kd_kamar "
                    + "	inner join bangsal ON "
                    + "	kamar.kd_bangsal = bangsal.kd_bangsal "
                    + "	inner join pemeriksaan_ranap ON "
                    + "	reg_periksa.no_rawat = pemeriksaan_ranap.no_rawat "
                    + "where reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' "
                    + "group by reg_periksa.no_rawat ", param);
        } else {
            Valid.MyReportqry("rptResumeRanap.jasper", "report", "::[ Data Resume Pasien ]::",
                    "SELECT reg_periksa.tgl_registrasi,"
                    + "	reg_periksa.no_rawat,"
                    + "	reg_periksa.status_lanjut,"
                    + "	reg_periksa.no_rkm_medis,"
                    + "	pasien.nm_pasien,"
                    + "	pasien.tgl_lahir,"
                    + "	pasien.alamat,"
                    + "	pasien.jk,"
                    + "	kamar_inap.tgl_masuk,"
                    + "	kamar_inap.tgl_keluar,"
                    + "	kamar_inap.diagnosa_awal,"
                    + "	bangsal.nm_bangsal,"
                    + "	resume_pasien.kd_dokter,"
                    + "	dokter.nm_dokter,"
                    + "	resume_pasien.kondisi_pulang,"
                    + "	resume_pasien.keluhan_utama,"
                    + "	resume_pasien.jalannya_penyakit,"
                    + "	resume_pasien.pemeriksaan_penunjang,"
                    + "	resume_pasien.hasil_laborat,"
                    + "	resume_pasien.diagnosa_utama,"
                    + "	resume_pasien.kd_diagnosa_utama,"
                    + "	resume_pasien.diagnosa_sekunder,"
                    + "	resume_pasien.kd_diagnosa_sekunder,"
                    + "	resume_pasien.diagnosa_sekunder2,"
                    + "	resume_pasien.kd_diagnosa_sekunder2,"
                    + "	resume_pasien.diagnosa_sekunder3,"
                    + "	resume_pasien.kd_diagnosa_sekunder3,"
                    + "	resume_pasien.diagnosa_sekunder4,"
                    + "	resume_pasien.kd_diagnosa_sekunder4,"
                    + "	resume_pasien.riwayat_penyakit,"
                    + "	pemeriksaan_ranap.keluhan,"
                    + "	pemeriksaan_ranap.pemeriksaan,"
                    + "	resume_pasien.tindakan_perawatan,"
                    + "	resep_obat.tgl_peresepan,"
                    + "	aturan_pakai.aturan AS aturan_pakai,"
                    + "	resep_pulang.aturan_pakai AS aturan_pakai_pulang "
                    + "FROM resume_pasien"
                    + "	inner join reg_periksa ON "
                    + "	 resume_pasien.no_rawat = reg_periksa.no_rawat "
                    + "	inner join pasien ON "
                    + "	 reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                    + "	inner join dokter ON "
                    + "	 resume_pasien.kd_dokter = dokter.kd_dokter "
                    + "	inner join kamar_inap ON "
                    + "	 reg_periksa.no_rawat = kamar_inap.no_rawat "
                    + "	inner join kamar ON "
                    + "	 kamar.kd_kamar = kamar_inap.kd_kamar "
                    + "	inner join bangsal ON "
                    + "	 kamar.kd_bangsal = bangsal.kd_bangsal "
                    + "	inner join pemeriksaan_ranap ON "
                    + "	 reg_periksa.no_rawat = pemeriksaan_ranap.no_rawat "
                    + "	inner join resep_obat ON "
                    + "	 pemeriksaan_ranap.no_rawat = resep_obat.no_rawat "
                    + "	INNER JOIN detail_pemberian_obat ON "
                    + "	 resep_obat.no_rawat = detail_pemberian_obat.no_rawat "
                    + "	left join aturan_pakai ON "
                    + "	 resep_obat.no_rawat = aturan_pakai.no_rawat "
                    + "	INNER join resep_pulang ON "
                    + "	 resep_obat.no_rawat = resep_pulang.no_rawat "
                    + "	inner JOIN databarang AS barang ON "
                    + "	 resep_pulang.kode_brng = barang.kode_brng "
                    + "where reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and reg_periksa.status_lanjut like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and resume_pasien.kd_dokter like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and resume_pasien.kondisi_pulang like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and resume_pasien.kd_diagnosa_utama like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and resume_pasien.diagnosa_utama like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and resume_pasien.prosedur_utama like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and reg_periksa.no_rawat like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.tgl_registrasi between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' and resume_pasien.kd_prosedur_utama like '%" + TCari.getText().trim() + "%' "
                    + "group by reg_periksa.no_rawat ", param);
        }
    }

    private List<String> reportSub(List<String> listKategori) {
        listKategori = new ArrayList<>();
        try {
            ps = koneksi.prepareStatement("SELECT resep_obat.tgl_peresepan,"
                    + "databarang.nama_brng,"
                    + "aturan_pakai.aturan AS aturan_pakai "
                    + "FROM resep_obat "
                    + "INNER JOIN detail_pemberian_obat ON "
                    + "resep_obat.no_rawat = detail_pemberian_obat.no_rawat "
                    + "left join aturan_pakai ON "
                    + "resep_obat.no_rawat = aturan_pakai.no_rawat "
                    + "INNER JOIN databarang ON "
                    + "databarang.kode_brng = detail_pemberian_obat.kode_brng "
                    + "WHERE "
                    + "resep_obat.no_rawat = '" + TNoRw.getText() + "'"
                    + " AND databarang.kode_kategori = 'obat' "
                    + "GROUP BY databarang.kode_brng");

            rs = ps.executeQuery();
            while (rs.next()) {
                listKategori.add(rs.getString("resep_obat.tgl_peresepan"));
                listKategori.add(rs.getString("databarang.nama_brng"));
                listKategori.add(rs.getString("aturan_pakai"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        return listKategori;
    }

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
//        tampilObat();
//        tampilObatPulang();
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
        tampil();
//        tampilObat();
//        tampilObatPulang();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            tampilObat();
            tampilObatPulang();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    ChkInput.setSelected(true);
                    isForm();
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
//        Valid.pindah(evt, TCari, Kondisi);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
//        Valid.pindah(evt, TCari, Kondisi);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void JalannyaPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalannyaPenyakitKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (evt.isShiftDown()) {
                PemeriksaanFisik.requestFocus();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            KeluhanUtama.requestFocus();
        }
    }//GEN-LAST:event_JalannyaPenyakitKeyPressed

    private void PemeriksaanFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanFisikKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (evt.isShiftDown()) {
                HasilLaborat.requestFocus();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            JalannyaPenyakit.requestFocus();
        }
    }//GEN-LAST:event_PemeriksaanFisikKeyPressed

    private void HasilLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilLaboratKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (evt.isShiftDown()) {
                tblDiagnosa.requestFocus();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            PemeriksaanFisik.requestFocus();
        }
    }//GEN-LAST:event_HasilLaboratKeyPressed

    private void MnLaporanResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanResumeActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (TCari.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Pilih data dahulu.");
        }
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            print();
        }
        this.setCursor(Cursor.getDefaultCursor());
//        if (tbObat.getSelectedRow() > -1) {
//            Map<String, Object> param = new HashMap<>();
//            param.put("namars", akses.getnamars());
//            param.put("alamatrs", akses.getalamatrs());
//            param.put("kotars", akses.getkabupatenrs());
//            param.put("propinsirs", akses.getpropinsirs());
//            param.put("kontakrs", akses.getkontakrs());
//            param.put("emailrs", akses.getemailrs());
//            param.put("logo", Sequel.cariGambar("select logo from setting"));
//            param.put("norawat", tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
//            param.put("finger", Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString()));
//            if (tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString().equals("Ralan")) {
//                param.put("ruang", Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString()));
//                param.put("tanggalkeluar", Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString()));
//            } else {
//                param.put("ruang", Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ", tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString()));
//                param.put("tanggalkeluar", Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ", tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString()));
//            }
//            Valid.MyReport("rptLaporanResume.jasper", "report", "::[ Laporan Resume Pasien ]::", param);
//        }
    }//GEN-LAST:event_MnLaporanResumeActionPerformed

    private void MnInputDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputDiagnosaActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        } else {
            penyakit.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            penyakit.setLocationRelativeTo(internalFrame1);
            penyakit.isCek();
            penyakit.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate(), Sequel.cariIsi("select status_lanjut from reg_periksa where no_rawat=?", TNoRw.getText()));
            penyakit.panelDiagnosa1.tampil();
            penyakit.setVisible(true);
        }
    }//GEN-LAST:event_MnInputDiagnosaActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void riwayatPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_riwayatPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_riwayatPenyakitKeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        if (TNoRw.getText().equals("") && TNoRM.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Pasien masih kosong...!!!");
        } else {
            carikeluhan.setNoRawat(TNoRw.getText());
            carikeluhan.tampil();
            carikeluhan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            carikeluhan.setLocationRelativeTo(internalFrame1);
            carikeluhan.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void penyakitPenyertaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_penyakitPenyertaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_penyakitPenyertaKeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        if (TNoRw.getText().equals("") && TNoRM.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Pasien masih kosong...!!!");
        } else {
            cariradiologi.setNoRawat(TNoRw.getText());
            cariradiologi.tampil();
            cariradiologi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            cariradiologi.setLocationRelativeTo(internalFrame1);
            cariradiologi.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (evt.isShiftDown()) {
                HasilLaborat.requestFocus();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            JalannyaPenyakit.requestFocus();
        }
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void KondisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKeyPressed
//        Valid.pindah(evt, KodeDokter, Keluhan);
    }//GEN-LAST:event_KondisiKeyPressed

    private void tblObatPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblObatPerawatanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblObatPerawatanMouseClicked

    private void tblObatSelamadiRumahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblObatSelamadiRumahMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblObatSelamadiRumahMouseClicked

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void penyakitPenyertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_penyakitPenyertaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_penyakitPenyertaActionPerformed

    private void tindakanRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tindakanRSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tindakanRSActionPerformed

    private void tindakanRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tindakanRSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tindakanRSKeyPressed

    private void kondisiSaatPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kondisiSaatPulangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kondisiSaatPulangActionPerformed

    private void kondisiSaatPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kondisiSaatPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kondisiSaatPulangKeyPressed

    private void tindakanLanjutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tindakanLanjutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tindakanLanjutActionPerformed

    private void tindakanLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tindakanLanjutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tindakanLanjutKeyPressed

    private void DateRencanaKonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DateRencanaKonKeyPressed
        // TODO add your handling code here:
        Valid.pindah(evt, TNoRw, Jam);
    }//GEN-LAST:event_DateRencanaKonKeyPressed

    private void DateTglPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DateTglPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DateTglPulangKeyPressed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenitKeyPressed

    private void MenitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenitActionPerformed

    private void DetikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DetikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetikActionPerformed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetikKeyPressed

    private void BtnTambahObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahObatActionPerformed
        // TODO add your handling code here:
        obatResume.noRawat(TNoRw.getText(), "0000-00-00", "perawatan");
        obatResume.tampil();
        obatResume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        obatResume.setLocationRelativeTo(internalFrame1);
        obatResume.setVisible(true);
    }//GEN-LAST:event_BtnTambahObatActionPerformed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void nomorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomorKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        tampilObat();
        isDiagnosaAhir();
        tampilObatPulang();
    }//GEN-LAST:event_formWindowActivated

    private void BtnTambahObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahObat1ActionPerformed
        // TODO add your handling code here:
        obatResume.noRawat(TNoRw.getText(), "0000-00-00", "pulang");
        obatResume.tampil();
        obatResume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        obatResume.setLocationRelativeTo(internalFrame1);
        obatResume.setVisible(true);
    }//GEN-LAST:event_BtnTambahObat1ActionPerformed

    private void tblDiagnosaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDiagnosaMouseClicked
        // TODO add your handling code here:
        System.out.println("Klik = " + tblDiagnosa.getModel().getValueAt(0, 1));
    }//GEN-LAST:event_tblDiagnosaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataResumePasien dialog = new RMDataResumePasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter2;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahObat;
    private widget.Button BtnTambahObat1;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal1 DateRencanaKon;
    private widget.Tanggal1 DateTglPulang;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextArea HasilLaborat;
    private widget.TextArea JalannyaPenyakit;
    private widget.ComboBox Jam;
    private widget.TextBox KeluhanUtama;
    private widget.TextBox KodeDokter;
    private widget.ComboBox Kondisi;
    private widget.Label LCount;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnInputDiagnosa;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox NamaDokter;
    private javax.swing.JPanel PanelInput;
    private widget.TextArea PemeriksaanFisik;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JDialog dialogObat;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private widget.TextBox kondisiSaatPulang;
    private widget.Label label14;
    private widget.TextBox nomor;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TextBox penyakitPenyerta;
    private widget.TextArea riwayatPenyakit;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.Table tbObat;
    private widget.Table tblDiagnosa;
    private javax.swing.JTable tblObat;
    private widget.Table tblObatPerawatan;
    private widget.Table tblObatSelamadiRumah;
    private widget.TextBox tindakanLanjut;
    private widget.TextBox tindakanRS;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TCari.getText().equals("")) {
                ps = koneksi.prepareStatement(
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "
                        + "resume_pasien.kd_dokter,dokter.nm_dokter,resume_pasien.kondisi_pulang,resume_pasien.keluhan_utama,resume_pasien.jalannya_penyakit, "
                        + "resume_pasien.pemeriksaan_penunjang,resume_pasien.hasil_laborat,resume_pasien.diagnosa_utama,resume_pasien.kd_diagnosa_utama, "
                        + "resume_pasien.diagnosa_sekunder,resume_pasien.kd_diagnosa_sekunder,resume_pasien.diagnosa_sekunder2,resume_pasien.kd_diagnosa_sekunder2, "
                        + "resume_pasien.diagnosa_sekunder3,resume_pasien.kd_diagnosa_sekunder3,resume_pasien.diagnosa_sekunder4,resume_pasien.kd_diagnosa_sekunder4, "
                        + "resume_pasien.prosedur_utama,resume_pasien.kd_prosedur_utama,resume_pasien.prosedur_sekunder,resume_pasien.kd_prosedur_sekunder, "
                        + "resume_pasien.prosedur_sekunder2,resume_pasien.kd_prosedur_sekunder2,resume_pasien.prosedur_sekunder3,resume_pasien.kd_prosedur_sekunder3,"
                        + "resume_pasien.riwayat_penyakit,resume_pasien.tindakan_perawatan,resume_pasien.alasan_pulang,resume_pasien.kondisi_pulang,resume_pasien.pemeriksaan_fisik,resume_pasien.noresume,resume_pasien.tindakan_rs "
                        + "from resume_pasien inner join reg_periksa on resume_pasien.no_rawat=reg_periksa.no_rawat  "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien.kd_dokter=dokter.kd_dokter "
                        + "where resume_pasien.tgl_resume between ? and ? order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut");
            } else {
                ps = koneksi.prepareStatement(
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "
                        + "resume_pasien.kd_dokter,dokter.nm_dokter,resume_pasien.kondisi_pulang,resume_pasien.keluhan_utama,resume_pasien.jalannya_penyakit, "
                        + "resume_pasien.pemeriksaan_penunjang,resume_pasien.hasil_laborat,resume_pasien.diagnosa_utama,resume_pasien.kd_diagnosa_utama, "
                        + "resume_pasien.diagnosa_sekunder,resume_pasien.kd_diagnosa_sekunder,resume_pasien.diagnosa_sekunder2,resume_pasien.kd_diagnosa_sekunder2, "
                        + "resume_pasien.diagnosa_sekunder3,resume_pasien.kd_diagnosa_sekunder3,resume_pasien.diagnosa_sekunder4,resume_pasien.kd_diagnosa_sekunder4, "
                        + "resume_pasien.prosedur_utama,resume_pasien.kd_prosedur_utama,resume_pasien.prosedur_sekunder,resume_pasien.kd_prosedur_sekunder, "
                        + "resume_pasien.prosedur_sekunder2,resume_pasien.kd_prosedur_sekunder2,resume_pasien.prosedur_sekunder3,resume_pasien.kd_prosedur_sekunder3,"
                        + "resume_pasien.riwayat_penyakit,resume_pasien.tindakan_perawatan,resume_pasien.alasan_pulang,resume_pasien.kondisi_pulang,resume_pasien.pemeriksaan_fisik,resume_pasien.noresume,resume_pasien.tindakan_rs "
                        + "from resume_pasien inner join reg_periksa on resume_pasien.no_rawat=reg_periksa.no_rawat  "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien.kd_dokter=dokter.kd_dokter "
                        + "where resume_pasien.tgl_resume between ? and ? and reg_periksa.status_lanjut like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and reg_periksa.no_rkm_medis like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and pasien.nm_pasien like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and resume_pasien.kd_dokter like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and dokter.nm_dokter like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and resume_pasien.kondisi_pulang like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and resume_pasien.kd_diagnosa_utama like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and resume_pasien.diagnosa_utama like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and resume_pasien.prosedur_utama like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and reg_periksa.no_rawat like ? or "
                        + "resume_pasien.tgl_resume between ? and ? and resume_pasien.kd_prosedur_utama like ? "
                        + "order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut");
            }
            try {
                if (TCari.getText().equals("")) {
                    ps.setString(1, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(2, Valid.SetDateToString(DTPCari2.getDate()));
                } else {
                    ps.setString(1, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(2, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(5, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(6, "%" + TCari.getText() + "%");
                    ps.setString(7, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(8, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(9, "%" + TCari.getText() + "%");
                    ps.setString(10, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(11, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(12, "%" + TCari.getText() + "%");
                    ps.setString(13, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(14, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(15, "%" + TCari.getText() + "%");
                    ps.setString(16, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(17, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(18, "%" + TCari.getText() + "%");
                    ps.setString(19, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(20, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(21, "%" + TCari.getText() + "%");
                    ps.setString(22, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(23, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(24, "%" + TCari.getText() + "%");
                    ps.setString(25, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(26, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(27, "%" + TCari.getText() + "%");
                    ps.setString(28, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(29, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(30, "%" + TCari.getText() + "%");
                    ps.setString(31, Valid.SetDateToString(DTPCari1.getDate()));
                    ps.setString(32, Valid.SetDateToString(DTPCari2.getDate()));
                    ps.setString(33, "%" + TCari.getText() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("tgl_registrasi"), rs.getString("status_lanjut"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("kd_dokter"), rs.getString("nm_dokter"), rs.getString("kondisi_pulang"), rs.getString("keluhan_utama"),
                        rs.getString("jalannya_penyakit"), rs.getString("pemeriksaan_penunjang"), rs.getString("hasil_laborat"), rs.getString("diagnosa_utama"),
                        rs.getString("kd_diagnosa_utama"), rs.getString("diagnosa_sekunder"), rs.getString("kd_diagnosa_sekunder"), rs.getString("diagnosa_sekunder2"),
                        rs.getString("kd_diagnosa_sekunder2"), rs.getString("diagnosa_sekunder3"), rs.getString("kd_diagnosa_sekunder3"), rs.getString("diagnosa_sekunder4"),
                        rs.getString("kd_diagnosa_sekunder4"), rs.getString("prosedur_utama"), rs.getString("kd_prosedur_utama"), rs.getString("prosedur_sekunder"),
                        rs.getString("kd_prosedur_sekunder"), rs.getString("prosedur_sekunder2"), rs.getString("kd_prosedur_sekunder2"), rs.getString("prosedur_sekunder3"),
                        rs.getString("kd_prosedur_sekunder3"), rs.getString("kondisi_pulang"), rs.getString("riwayat_penyakit"), rs.getString("tindakan_rs"),
                        rs.getString("alasan_pulang"), rs.getString("pemeriksaan_fisik"), "", rs.getString("noresume"), rs.getString("tindakan_perawatan")
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
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
        int b = tabMode.getRowCount();
        LCount.setText("" + b);
    }

    private void tampilObat() {
        String obat = Sequel.cariIsi("select no_rawat from obat_rs_resume where no_rawat='" + TNoRw.getText() + "'");
        System.out.println("Obat :" + obat);
        if (obat.isBlank()) {
            Valid.tabelKosong(tabMode3);
            try {
                if (!TNoRw.getText().equals("")) {
                    ps3 = koneksi.prepareStatement(
                            "select min(aturan_pakai.tgl_perawatan), resep_obat.tgl_peresepan, barang.kode_brng, barang.nama_brng, barang.kapasitas, aturan_pakai.aturan as aturan_pakai "
                            + "from resep_obat "
                            + "inner join detail_pemberian_obat on resep_obat.no_rawat = detail_pemberian_obat.no_rawat "
                            + "inner join aturan_pakai on resep_obat.no_rawat = aturan_pakai.no_rawat AND aturan_pakai.kode_brng = detail_pemberian_obat.kode_brng "
                            + "inner join databarang as barang on barang.kode_brng = detail_pemberian_obat.kode_brng "
                            + "where "
                            + "aturan_pakai.no_rawat =? "
                            + "and barang.kode_kategori = 'obat' "
                            + "group by aturan_pakai.kode_brng");
                }
                try {
                    if (!TNoRw.getText().equals("")) {
                        ps3.setString(1, TNoRw.getText());
                        rs3 = ps3.executeQuery();
                        System.out.println("rs3 = " + rs3);
                        while (rs3.next()) {
                            tabMode3.addRow(new Object[]{
                                true, rs3.getString("kode_brng"), rs3.getString("nama_brng"), rs3.getString("aturan_pakai")
                            });
                        }
                    }
                } catch (Exception e) {
                    System.out.println("tampil obat : " + e);
                } finally {
                    if (rs3 != null) {
                        rs3.close();
                    }
                    if (ps3 != null) {
                        ps3.close();
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error tampil obat = " + ex);
            }
        } else {
            Valid.tabelKosong(tabMode3);
            try {
                if (!TNoRw.getText().equals("")) {
                    ps3 = koneksi.prepareStatement(
                            "select * from obat_rs_resume where obat_rs_resume.no_rawat =? ");
                }
                try {
                    if (!TNoRw.getText().equals("")) {
                        ps3.setString(1, TNoRw.getText());
                        rs3 = ps3.executeQuery();
                        System.out.println("rs3 = " + rs3);
                        while (rs3.next()) {
                            tabMode3.addRow(new Object[]{
                                true, rs3.getString("kode_brng"), rs3.getString("nama_brng"), rs3.getString("aturan_pakai")
                            });
                        }
                    }
                } catch (Exception e) {
                    System.out.println("tampil obat : " + e);
                } finally {
                    if (rs3 != null) {
                        rs3.close();
                    }
                    if (ps3 != null) {
                        ps3.close();
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error tampil obat = " + ex);
            }
        }
    }

    private void tampilObatPulang() {
        String obat = Sequel.cariIsi("select no_rawat from obat_pulang_resume where no_rawat='" + TNoRw.getText() + "'");
        System.out.println("Obat :" + obat);
        Valid.tabelKosong(tabMode2);
        try {
            if (!TNoRw.getText().equals("")) {
                if (obat.isBlank()) {
                    ps2 = koneksi.prepareStatement(
                            "select resep_pulang.aturan_pakai, barang.kode_brng, barang.nama_brng,barang.kapasitas "
                            + "from resep_pulang "
                            + "inner join databarang as barang ON resep_pulang.kode_brng = barang.kode_brng "
                            + "where "
                            + "resep_pulang.no_rawat =? "
                            + "and barang.kode_kategori = 'obat'");
                } else {
                    ps2 = koneksi.prepareStatement(
                            "select obat_pulang_resume.aturan_pakai, obat_pulang_resume.kode_brng, obat_pulang_resume.nama_brng "
                            + "from obat_pulang_resume "
                            //                        + "inner join databarang as barang ON resep_pulang.kode_brng = barang.kode_brng "
                            + "where "
                            + "obat_pulang_resume.no_rawat =? "
                    //                        + "and barang.kode_kategori = 'obat'"
                    );
                }
            }
            try {
                if (!TNoRw.getText().equals("")) {
                    if (obat.isBlank()) {
                        ps2.setString(1, TNoRw.getText());
                    } else {
                        ps2.setString(1, TNoRw.getText());
                    }
                }
                rs2 = ps2.executeQuery();
                System.out.println("rs2 = " + rs2);
                while (rs2.next()) {
                    tabMode2.addRow(new Object[]{
                        true, rs2.getString("kode_brng"), rs2.getString("nama_brng"), rs2.getString("aturan_pakai")
                    });
                }
            } catch (Exception e) {
                System.out.println("tampil obat pulang : " + e);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error tampil obat pulang = " + ex);
        }
    }

    /**
     *
     */
    public void emptTeks() {
        KeluhanUtama.setText("");
        JalannyaPenyakit.setText("");
        PemeriksaanFisik.setText("");
        HasilLaborat.setText("");
        PemeriksaanPenunjang.setText("");
        riwayatPenyakit.setText("");
        penyakitPenyerta.setText("");
        kondisiSaatPulang.setText("");
        tindakanLanjut.setText("");
//        Obat2an.setText("");
//        DiagnosaUtama.setText("");
//        DiagnosaSekunder1.setText("");
//        DiagnosaSekunder2.setText("");
//        DiagnosaSekunder3.setText("");
////        DiagnosaSekunder4.setText("");
//        ProsedurUtama.setText("");
//        ProsedurSekunder1.setText("");
//        ProsedurSekunder2.setText("");
//        ProsedurSekunder3.setText("");
//        KodeDiagnosaUtama.setText("");
//        KodeDiagnosaSekunder1.setText("");
//        KodeDiagnosaSekunder2.setText("");
//        KodeDiagnosaSekunder3.setText("");
//        KodeDiagnosaSekunder4.setText("");
//        KodeProsedurUtama.setText("");
//        KodeProsedurSekunder1.setText("");
//        KodeProsedurSekunder2.setText("");
//        KodeProsedurSekunder3.setText("");
//        Kondisi.requestFocus();
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            String jam_pulang = Sequel.cariIsi("select jam_pulang from resume_pasien where no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString() + "'");
            System.out.println("jam " + jam_pulang);
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TCari.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            JalannyaPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            PemeriksaanPenunjang.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            PemeriksaanFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString());
            tindakanRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
            HasilLaborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            kondisiSaatPulang.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString());
            riwayatPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString());
//            penyakitPenyerta.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
            Kondisi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString());
            nomor.setText(Sequel.cariIsi("select noresume from resume_pasien where no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString() + "'"));
            tindakanLanjut.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString());
            Jam.setSelectedItem(jam_pulang.substring(0, 2));
            Menit.setSelectedItem(jam_pulang.substring(3, 5));
            Detik.setSelectedItem(jam_pulang.substring(6, 8));
            DateTglPulang.setDate(Valid.SetStringToDate(Sequel.cariIsi("select tgl_pulang from resume_pasien where no_rawat='" + TNoRw.getText() + "'")));
            DateRencanaKon.setDate(Valid.SetStringToDate(Sequel.cariIsi("select tgl_kontrol from resume_pasien where no_rawat='" + TNoRw.getText() + "'")));
            tampilObat();
            tampilObatPulang();
            isDiagnosaAhir();
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='" + TNoRw.getText() + "' ", TNoRM);
    }

    private void isDiagnosaAwal() {
        Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat='" + TNoRw.getText() + "' ", KeluhanUtama);
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='" + TNoRM.getText() + "' ", TPasien);
    }

    public void isDiagnosaAhir() {
        Valid.tabelKosong(tabMode1);
        try {
            ps1 = koneksi.prepareStatement("select kd_penyakit, nm_penyakit "
                    + "from diagnosa_pasien where no_rawat='" + TNoRw.getText() + "' and status='Ranap' and kd_penyakit!='-' order by prioritas asc");
            try {
                rs1 = ps1.executeQuery();
                int x = 0, y = 5;
                while (rs1.next()) {
                    tabMode1.addRow(new Object[]{rs1.getString("kd_penyakit"), rs1.getString("nm_penyakit")});
                    x += 1;
                }
//                System.out.println("x=" + x);
                for (int j = 0; j < y - x; j++) {
                    tabMode1.addRow(new Object[]{"-", "-"});
                }
            } catch (Exception e) {
                System.out.println("Error " + e.toString());
            } finally {
                if (rs1 != null) {
                    rs1.close();
                }

                if (ps1 != null) {
                    ps1.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.toString());
        }
    }

    /**
     *
     * @param norwt
     * @param tgl2
     */
    public void setPj(String norawat) {
        String dpjp = Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat='" + norawat + "'");
        KodeDokter.setText(dpjp);
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='" + KodeDokter.getText() + "'", NamaDokter);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='" + norwt + "'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        isDiagnosaAwal();
//        isDiagnosaAhir();
        ChkInput.setSelected(true);
        isForm();
        tampilObat();
        tampilObatPulang();
//        Kondisi.requestFocus();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, this.getHeight() - 122));
            scrollInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            scrollInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    /**
     *
     */
    public void isCek() {
        BtnSimpan.setEnabled(akses.getdata_resume_pasien());
        BtnHapus.setEnabled(akses.getdata_resume_pasien());
        BtnEdit.setEnabled(akses.getdata_resume_pasien());
        BtnPrint.setEnabled(akses.getdata_resume_pasien());
        MnInputDiagnosa.setEnabled(akses.getdiagnosa_pasien());
//        if (akses.getjml2() >= 1) {
//            KeluhanUtama.setEditable(false);
//            BtnDokter.setEnabled(false);
//            KeluhanUtama.setText(akses.getkode());
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NamaDokter, KeluhanUtama.getText());
//            if (NamaDokter.getText().equals("")) {
//                KeluhanUtama.setText("");
//                JOptionPane.showMessageDialog(null, "User login bukan dokter...!!");
//            }
//        }
    }

}

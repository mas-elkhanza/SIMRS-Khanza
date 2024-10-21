package simrskhanza;

import bridging.BPJSDataSEP;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import bridging.BPJSRujukanKeluar;
import bridging.BPJSSPRI;
import bridging.BPJSSuratKontrol;
import bridging.ICareRiwayatPerawatan;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.SwingUtilities;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
//import rekammedis.DlgFormulirPemeriksaan;
//import rekammedis.RMDataCatatanKeperawatan;
import rekammedis.RMDataCatatanObservasiIGD;
import rekammedis.RMDeteksiDiniCorona;
import rekammedis.RMTriaseIGD;
//import rekammedis.RMProgramKFR;
import rekammedis.RMUjiFungsiKFR;
import rekammedis.RMPenilaianAwalMedisRalanPsikiatrik;
import rekammedis.RMPenilaianAwalMedisRalanMata;
//import rekammedis.DlgPermintaanAmbulance;
//import rekammedis.RMAwalMedisGiziKlinis;
//import rekammedis.RMPenilaianKeperawatanPasienGeriatri;
import rekammedis.RMPenilaianAwalMedisRalanDewasa;
//import rekammedis.RMLayananKFR;
import rekammedis.RMDataResumePasien;
import surat.SuratKontrol;
import surat.SuratSakit;
import setting.DlgCariJamDiet;
import java.io.FileOutputStream;
import java.util.Base64;

/**
 *
 * @author dosen
 */
public class DlgKompilasiBerkasKlaimBPJS extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2, psambiltindakan, psambillab, psambilrad;
    public DlgCariCaraBayar penjab = new DlgCariCaraBayar(null, false);
    private ResultSet rs, rs2, rsambiltindakan, rsambillab, rsambilrad;
    private int i = 0;
    private String finger = "", masalahkeperawatan = "",variabel="",tanggal="";
    private StringBuilder htmlContent;
    private WebEngine engine;
    private final JFXPanel jfxPanelicare = new JFXPanel();
    private final JFXPanel jfxinvoices = new JFXPanel();
    private final Properties prop = new Properties();

    /**
     * Creates new form DlgPemberianInfus
     *
     * @param parent
     * @param modal
     */
    public DlgKompilasiBerkasKlaimBPJS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tabMode = new DefaultTableModel(null, new Object[] {
                "No.Rawat", "No RM", "Nama Pasien", "Status Rawat", "Tanggal", "Waktu", "Poliklinik", "Diagnosa" }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[] {
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class

            };

            /*
             * Class[] types = new Class[] {
             * java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
             * java.lang.Object.class
             * };
             */
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        // Object[] row={"No.Rawat","No RM","Nama Pasien","Status
        // Rawat","Tanggal","Waktu","Poliklinik","Diagnosa"};

        // tabMode=new DefaultTableModel(null,row){
        // @Override public boolean isCellEditable(int rowIndex, int colIndex){return
        // false;}
        // };
        tbKompilasi.setModel(tabMode);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKompilasi.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKompilasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbKompilasi.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(0);
                column.setMaxWidth(10);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setPreferredWidth(100);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else if (i == 5) {
                column.setPreferredWidth(100);
            } else if (i == 6) {
                column.setPreferredWidth(100);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            }
        }
        tbKompilasi.setDefaultRenderer(Object.class, new WarnaTable());

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

        // penjab.addWindowListener(new WindowListener() {
        // @Override
        // public void windowOpened(WindowEvent e) {
        // }
        //
        // @Override
        // public void windowClosing(WindowEvent e) {
        // }
        //
        // @Override
        // public void windowClosed(WindowEvent e) {
        //
        // if (penjab.getTable().getSelectedRow() != -1) {
        // Kdpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),
        // 1).toString());
        // nmpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),
        // 2).toString());
        // }
        // Kdpnj.requestFocus();
        //
        // }
        //
        // @Override
        // public void windowIconified(WindowEvent e) {
        // }
        //
        // @Override
        // public void windowDeiconified(WindowEvent e) {
        // }
        //
        // @Override
        // public void windowActivated(WindowEvent e) {
        // }
        //
        // @Override
        // public void windowDeactivated(WindowEvent e) {
        // }
        // });
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        + ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                        + ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        + ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                        + ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                        + ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                        + ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                        + ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                        + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}");

        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));

        } catch (Exception ex) {

        }

        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);

    }

    private DlgCariDiet diet = new DlgCariDiet(null, false);
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, false);
    private DlgCariJamDiet jamdiet = new DlgCariJamDiet(null, false);

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        PilihSemua = new javax.swing.JMenuItem();
        ScrollHTML = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel8 = new widget.Label();
        CmbStts = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        lblCoderNIK = new widget.Label();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panelGlass11 = new widget.panelisi();
        Scroll = new widget.ScrollPane();
        tbKompilasi = new widget.Table();
        panelInvoices = new widget.panelisi();
        jPanel2 = new javax.swing.JPanel();
        scrollPane9 = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        jLabel14 = new widget.Label();
        lblNamaPasien = new widget.Label();
        jLabel15 = new widget.Label();
        lblNoRawat = new widget.Label();
        jLabel16 = new widget.Label();
        lblNoRM = new widget.Label();
        jLabel17 = new widget.Label();
        lblNoSEP = new widget.Label();
        jLabel18 = new widget.Label();
        btnSEPResume = new widget.Button();
        jLabel25 = new widget.Label();
        btnResumeRanap = new widget.Button();
        jLabel26 = new widget.Label();
        btnInvoice = new widget.Button();
        jLabel27 = new widget.Label();
        btnAwalMedisIGD = new widget.Button();
        jLabel28 = new widget.Label();
        btnHasilLab = new widget.Button();
        jLabel29 = new widget.Label();
        btnHasilRad = new widget.Button();
        jLabel30 = new widget.Label();
        btnSurkon = new widget.Button();
        jLabel31 = new widget.Label();
        btnSPRI = new widget.Button();
        jLabel32 = new widget.Label();
        btnPenunjang = new widget.Button();
        lblNoSEP22 = new widget.Label();
        panelDiagnosa1 = new laporan.PanelDiagnosa();
        jLabel20 = new widget.Label();
        lblStatusRawat = new widget.Label();
        BtnSimpanDiagnosa = new widget.Button();
        BtnHapusDiagnosa = new widget.Button();
        btnSEPResume1 = new widget.Button();
        btnSEPResume2 = new widget.Button();
        ChkSuratKontrol = new widget.CekBox();
        BtnValidasiQR = new widget.Button();
        jPanel5 = new javax.swing.JPanel();
        PanelContentIcareBPJS = new widget.panelisi();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        PilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        PilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        PilihSemua.setForeground(java.awt.Color.darkGray);
        PilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        PilihSemua.setText("Pilih Semua");
        PilihSemua.setName("PilihSemua"); // NOI18N
        PilihSemua.setPreferredSize(new java.awt.Dimension(150, 28));
        PilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(PilihSemua);

        ScrollHTML.setBorder(null);
        ScrollHTML.setName("ScrollHTML"); // NOI18N
        ScrollHTML.setOpaque(true);
        ScrollHTML.setPreferredSize(new java.awt.Dimension(470, 16));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        ScrollHTML.setViewportView(LoadHTML);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 2, true), "::[ Kompilasi Berkas Klaim BPJS ]::", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Reset");
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
        panelGlass8.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass10.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-10-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-10-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari2);

        jLabel8.setText("Status Rawat :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass10.add(jLabel8);

        CmbStts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Ralan", "Ranap" }));
        CmbStts.setLightWeightPopupEnabled(false);
        CmbStts.setMinimumSize(new java.awt.Dimension(75, 21));
        CmbStts.setName("CmbStts"); // NOI18N
        CmbStts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbSttsKeyPressed(evt);
            }
        });
        panelGlass10.add(CmbStts);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
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
        panelGlass10.add(BtnCari);

        lblCoderNIK.setText("coder");
        lblCoderNIK.setName("lblCoderNIK"); // NOI18N
        lblCoderNIK.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass10.add(lblCoderNIK);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 51, 0), 1, true), "Data Pasien", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(310, 143));
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 402));
        panelGlass11.setLayout(new java.awt.BorderLayout());

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(44, 402));

        tbKompilasi.setComponentPopupMenu(jPopupMenu1);
        tbKompilasi.setName("tbKompilasi"); // NOI18N
        tbKompilasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKompilasiMouseClicked(evt);
            }
        });
        tbKompilasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKompilasiKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKompilasi);

        panelGlass11.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel4.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        panelInvoices.setName("panelInvoices"); // NOI18N
        panelInvoices.setPreferredSize(new java.awt.Dimension(55, 100));
        panelInvoices.setLayout(new java.awt.BorderLayout());
        jPanel4.add(panelInvoices, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true), "Kelengkapan Data", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 102));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Data", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane9.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane9.setName("scrollPane9"); // NOI18N
        scrollPane9.setPreferredSize(new java.awt.Dimension(470, 800));

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(435, 800));
        FormMenu.setLayout(null);

        jLabel14.setText("Data Pasien: ");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel14);
        jLabel14.setBounds(0, 0, 120, 14);

        lblNamaPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNamaPasien.setText("NamaPasien");
        lblNamaPasien.setName("lblNamaPasien"); // NOI18N
        lblNamaPasien.setPreferredSize(new java.awt.Dimension(300, 14));
        FormMenu.add(lblNamaPasien);
        lblNamaPasien.setBounds(120, 0, 300, 14);

        jLabel15.setText("No Rawat : ");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel15);
        jLabel15.setBounds(0, 20, 120, 14);

        lblNoRawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNoRawat.setText("NoRawat");
        lblNoRawat.setName("lblNoRawat"); // NOI18N
        lblNoRawat.setPreferredSize(new java.awt.Dimension(300, 14));
        FormMenu.add(lblNoRawat);
        lblNoRawat.setBounds(120, 20, 300, 14);

        jLabel16.setText("No RM: ");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel16);
        jLabel16.setBounds(0, 60, 120, 14);

        lblNoRM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNoRM.setName("lblNoRM"); // NOI18N
        lblNoRM.setPreferredSize(new java.awt.Dimension(300, 14));
        FormMenu.add(lblNoRM);
        lblNoRM.setBounds(120, 60, 300, 14);

        jLabel17.setText("No SEP: ");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel17);
        jLabel17.setBounds(0, 80, 120, 14);

        lblNoSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNoSEP.setName("lblNoSEP"); // NOI18N
        lblNoSEP.setPreferredSize(new java.awt.Dimension(300, 14));
        FormMenu.add(lblNoSEP);
        lblNoSEP.setBounds(120, 80, 300, 14);

        jLabel18.setText("Data SEP: ");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel18);
        jLabel18.setBounds(0, 100, 120, 14);

        btnSEPResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSEPResume.setMnemonic('1');
        btnSEPResume.setText("Lihat");
        btnSEPResume.setToolTipText("ALt+1");
        btnSEPResume.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSEPResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSEPResume.setName("btnSEPResume"); // NOI18N
        btnSEPResume.setPreferredSize(new java.awt.Dimension(100, 14));
        btnSEPResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSEPResumeActionPerformed(evt);
            }
        });
        FormMenu.add(btnSEPResume);
        btnSEPResume.setBounds(120, 100, 100, 14);

        jLabel25.setText("Resume Ranap: ");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel25);
        jLabel25.setBounds(0, 120, 120, 14);

        btnResumeRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnResumeRanap.setMnemonic('1');
        btnResumeRanap.setText("Lihat");
        btnResumeRanap.setToolTipText("ALt+1");
        btnResumeRanap.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnResumeRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnResumeRanap.setName("btnResumeRanap"); // NOI18N
        btnResumeRanap.setPreferredSize(new java.awt.Dimension(100, 14));
        btnResumeRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumeRanapActionPerformed(evt);
            }
        });
        FormMenu.add(btnResumeRanap);
        btnResumeRanap.setBounds(120, 120, 100, 14);

        jLabel26.setText("Billing: ");
        jLabel26.setName("jLabel26"); // NOI18N
        jLabel26.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel26);
        jLabel26.setBounds(0, 140, 120, 14);

        btnInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInvoice.setMnemonic('1');
        btnInvoice.setText("Lihat");
        btnInvoice.setToolTipText("ALt+1");
        btnInvoice.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnInvoice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnInvoice.setName("btnInvoice"); // NOI18N
        btnInvoice.setPreferredSize(new java.awt.Dimension(100, 14));
        btnInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoiceActionPerformed(evt);
            }
        });
        FormMenu.add(btnInvoice);
        btnInvoice.setBounds(120, 140, 100, 14);

        jLabel27.setText("Awal Medis IGD: ");
        jLabel27.setName("jLabel27"); // NOI18N
        jLabel27.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel27);
        jLabel27.setBounds(0, 160, 120, 14);

        btnAwalMedisIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAwalMedisIGD.setMnemonic('1');
        btnAwalMedisIGD.setText("Lihat");
        btnAwalMedisIGD.setToolTipText("ALt+1");
        btnAwalMedisIGD.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnAwalMedisIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAwalMedisIGD.setName("btnAwalMedisIGD"); // NOI18N
        btnAwalMedisIGD.setPreferredSize(new java.awt.Dimension(100, 14));
        btnAwalMedisIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAwalMedisIGDActionPerformed(evt);
            }
        });
        FormMenu.add(btnAwalMedisIGD);
        btnAwalMedisIGD.setBounds(120, 160, 100, 14);

        jLabel28.setText("Hasil Lab: ");
        jLabel28.setName("jLabel28"); // NOI18N
        jLabel28.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel28);
        jLabel28.setBounds(0, 180, 120, 14);

        btnHasilLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnHasilLab.setMnemonic('1');
        btnHasilLab.setText("Lihat");
        btnHasilLab.setToolTipText("ALt+1");
        btnHasilLab.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnHasilLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHasilLab.setName("btnHasilLab"); // NOI18N
        btnHasilLab.setPreferredSize(new java.awt.Dimension(100, 14));
        btnHasilLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHasilLabActionPerformed(evt);
            }
        });
        FormMenu.add(btnHasilLab);
        btnHasilLab.setBounds(120, 180, 100, 14);

        jLabel29.setText("Hasil Radiologi: ");
        jLabel29.setName("jLabel29"); // NOI18N
        jLabel29.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel29);
        jLabel29.setBounds(0, 200, 120, 14);

        btnHasilRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnHasilRad.setMnemonic('1');
        btnHasilRad.setText("Lihat");
        btnHasilRad.setToolTipText("ALt+1");
        btnHasilRad.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnHasilRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHasilRad.setName("btnHasilRad"); // NOI18N
        btnHasilRad.setPreferredSize(new java.awt.Dimension(100, 14));
        btnHasilRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHasilRadActionPerformed(evt);
            }
        });
        FormMenu.add(btnHasilRad);
        btnHasilRad.setBounds(120, 200, 100, 14);

        jLabel30.setText("Surat Kontrol: ");
        jLabel30.setName("jLabel30"); // NOI18N
        jLabel30.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel30);
        jLabel30.setBounds(0, 220, 120, 14);

        btnSurkon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSurkon.setMnemonic('1');
        btnSurkon.setText("Lihat");
        btnSurkon.setToolTipText("ALt+1");
        btnSurkon.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSurkon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSurkon.setName("btnSurkon"); // NOI18N
        btnSurkon.setPreferredSize(new java.awt.Dimension(100, 14));
        btnSurkon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSurkonActionPerformed(evt);
            }
        });
        FormMenu.add(btnSurkon);
        btnSurkon.setBounds(120, 220, 100, 14);

        jLabel31.setText("SPRI: ");
        jLabel31.setName("jLabel31"); // NOI18N
        jLabel31.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel31);
        jLabel31.setBounds(0, 240, 120, 14);

        btnSPRI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSPRI.setMnemonic('1');
        btnSPRI.setText("Lihat");
        btnSPRI.setToolTipText("ALt+1");
        btnSPRI.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSPRI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSPRI.setName("btnSPRI"); // NOI18N
        btnSPRI.setPreferredSize(new java.awt.Dimension(100, 14));
        btnSPRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSPRIActionPerformed(evt);
            }
        });
        FormMenu.add(btnSPRI);
        btnSPRI.setBounds(120, 240, 100, 14);

        jLabel32.setText("Penunjang: ");
        jLabel32.setName("jLabel32"); // NOI18N
        jLabel32.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel32);
        jLabel32.setBounds(0, 260, 120, 14);

        btnPenunjang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenunjang.setMnemonic('1');
        btnPenunjang.setText("Lihat");
        btnPenunjang.setToolTipText("ALt+1");
        btnPenunjang.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnPenunjang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPenunjang.setName("btnPenunjang"); // NOI18N
        btnPenunjang.setPreferredSize(new java.awt.Dimension(100, 14));
        btnPenunjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenunjangActionPerformed(evt);
            }
        });
        FormMenu.add(btnPenunjang);
        btnPenunjang.setBounds(120, 260, 100, 14);

        lblNoSEP22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNoSEP22.setName("lblNoSEP22"); // NOI18N
        lblNoSEP22.setPreferredSize(new java.awt.Dimension(200, 14));
        FormMenu.add(lblNoSEP22);
        lblNoSEP22.setBounds(590, 250, 200, 14);

        panelDiagnosa1.setBorder(null);
        panelDiagnosa1.setName("panelDiagnosa1"); // NOI18N
        panelDiagnosa1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDiagnosa1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDiagnosa1MouseEntered(evt);
            }
        });
        FormMenu.add(panelDiagnosa1);
        panelDiagnosa1.setBounds(0, 280, 500, 450);

        jLabel20.setText("Status Rawat : ");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(120, 14));
        FormMenu.add(jLabel20);
        jLabel20.setBounds(0, 40, 120, 14);

        lblStatusRawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblStatusRawat.setText("Status");
        lblStatusRawat.setName("lblStatusRawat"); // NOI18N
        lblStatusRawat.setPreferredSize(new java.awt.Dimension(300, 14));
        FormMenu.add(lblStatusRawat);
        lblStatusRawat.setBounds(120, 40, 300, 14);

        BtnSimpanDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanDiagnosa.setMnemonic('S');
        BtnSimpanDiagnosa.setText("Simpan");
        BtnSimpanDiagnosa.setToolTipText("Alt+S");
        BtnSimpanDiagnosa.setAlignmentY(0.0F);
        BtnSimpanDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BtnSimpanDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSimpanDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnSimpanDiagnosa.setIconTextGap(0);
        BtnSimpanDiagnosa.setMargin(new java.awt.Insets(0, 2, 2, 0));
        BtnSimpanDiagnosa.setName("BtnSimpanDiagnosa"); // NOI18N
        BtnSimpanDiagnosa.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpanDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanDiagnosaActionPerformed(evt);
            }
        });
        BtnSimpanDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanDiagnosaKeyPressed(evt);
            }
        });
        FormMenu.add(BtnSimpanDiagnosa);
        BtnSimpanDiagnosa.setBounds(10, 740, 70, 30);

        BtnHapusDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusDiagnosa.setMnemonic('H');
        BtnHapusDiagnosa.setText("Hapus");
        BtnHapusDiagnosa.setToolTipText("Alt+H");
        BtnHapusDiagnosa.setAlignmentY(0.0F);
        BtnHapusDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BtnHapusDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHapusDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnHapusDiagnosa.setIconTextGap(0);
        BtnHapusDiagnosa.setMargin(new java.awt.Insets(0, 2, 2, 0));
        BtnHapusDiagnosa.setName("BtnHapusDiagnosa"); // NOI18N
        BtnHapusDiagnosa.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapusDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusDiagnosaActionPerformed(evt);
            }
        });
        BtnHapusDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusDiagnosaKeyPressed(evt);
            }
        });
        FormMenu.add(BtnHapusDiagnosa);
        BtnHapusDiagnosa.setBounds(340, 740, 70, 30);

        btnSEPResume1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSEPResume1.setMnemonic('1');
        btnSEPResume1.setText("PDF NCC");
        btnSEPResume1.setToolTipText("ALt+1");
        btnSEPResume1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSEPResume1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSEPResume1.setName("btnSEPResume1"); // NOI18N
        btnSEPResume1.setPreferredSize(new java.awt.Dimension(100, 14));
        btnSEPResume1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSEPResume1ActionPerformed(evt);
            }
        });
        FormMenu.add(btnSEPResume1);
        btnSEPResume1.setBounds(230, 100, 90, 14);

        btnSEPResume2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSEPResume2.setMnemonic('1');
        btnSEPResume2.setText("PDF Billing");
        btnSEPResume2.setToolTipText("ALt+1");
        btnSEPResume2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSEPResume2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSEPResume2.setName("btnSEPResume2"); // NOI18N
        btnSEPResume2.setPreferredSize(new java.awt.Dimension(100, 14));
        btnSEPResume2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSEPResume2ActionPerformed(evt);
            }
        });
        FormMenu.add(btnSEPResume2);
        btnSEPResume2.setBounds(230, 140, 110, 14);

        ChkSuratKontrol.setBorder(null);
        ChkSuratKontrol.setBorderPainted(true);
        ChkSuratKontrol.setBorderPaintedFlat(true);
        ChkSuratKontrol.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkSuratKontrol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkSuratKontrol.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkSuratKontrol.setName("ChkSuratKontrol"); // NOI18N
        ChkSuratKontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkSuratKontrolActionPerformed(evt);
            }
        });
        FormMenu.add(ChkSuratKontrol);
        ChkSuratKontrol.setBounds(220, 220, 23, 20);

        scrollPane9.setViewportView(FormMenu);

        jPanel2.add(scrollPane9, java.awt.BorderLayout.CENTER);

        BtnValidasiQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/aggregate.png"))); // NOI18N
        BtnValidasiQR.setMnemonic('T');
        BtnValidasiQR.setText("Kompilasi");
        BtnValidasiQR.setToolTipText("Alt+T");
        BtnValidasiQR.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnValidasiQR.setName("BtnValidasiQR"); // NOI18N
        BtnValidasiQR.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnValidasiQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnValidasiQRActionPerformed(evt);
            }
        });
        jPanel2.add(BtnValidasiQR, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true), "Detail billing Pembayaran", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(350, 102));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        PanelContentIcareBPJS.setName("PanelContentIcareBPJS"); // NOI18N
        PanelContentIcareBPJS.setPreferredSize(new java.awt.Dimension(55, 55));
        PanelContentIcareBPJS.setLayout(new java.awt.BorderLayout());
        jPanel5.add(PanelContentIcareBPJS);

        jPanel1.add(jPanel5);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }// GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {

        }
    }// GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }// GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnCariActionPerformed
        tampil();
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
        isCek();
//        tampil();
        emptTeks();
    }// GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        }
    }// GEN-LAST:event_BtnAllKeyPressed

    private void tbKompilasiMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbKompilasiMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }// GEN-LAST:event_tbKompilasiMouseClicked

    private void tbKompilasiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tbKompilasiKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }// GEN-LAST:event_tbKompilasiKeyPressed

    private void PilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_PilihSemuaActionPerformed
        int i = 0;
        for (i = 0; i < tbKompilasi.getRowCount(); i++) {
            tbKompilasi.setValueAt(true, i, 0);
        }
    }// GEN-LAST:event_PilihSemuaActionPerformed

    private void CmbSttsKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_CmbSttsKeyPressed

    }// GEN-LAST:event_CmbSttsKeyPressed

    private void BtnValidasiQRActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnValidasiQRActionPerformed
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            tampilInvoice();
            tampilicareBPJS();
            gabung(lblNoRawat.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_BtnValidasiQRActionPerformed

    private void btnSEPResumeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSEPResumeActionPerformed
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {
            if (lblStatusRawat.getText().equals("Ralan")) {
                String nomorsep = Sequel
                        .cariIsi("select bridging_sep.no_sep from bridging_sep where bridging_sep.no_rawat='"
                                + lblNoRawat.getText() + "' and bridging_sep.jnspelayanan='2'");
                String jnspelayanan = Sequel
                        .cariIsi("select bridging_sep.jnspelayanan from bridging_sep where bridging_sep.no_rawat='"
                                + nomorsep + "'");
                String kodedokterregis = Sequel
                        .cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat='"
                                + lblNoRawat.getText() + "'");
                String namadokterregis = Sequel.cariIsi(
                        "select dokter.nm_dokter from dokter where dokter.kd_dokter='" + kodedokterregis + "'");
                String norm= Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+lblNoRawat.getText()+"'");
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            
            //custom tambahan
            String sep = Sequel.cariIsi("select ifnull(no_sep,'-') from bridging_sep where jnspelayanan='2' and no_rawat=?",lblNoRawat.getText());
            param.put("suhu_tubuh",Sequel.cariIsi("select suhu_tubuh from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("tensi",Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("nadi",Sequel.cariIsi("select nadi from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("respirasi",Sequel.cariIsi("select respirasi from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("tinggi",Sequel.cariIsi("select tinggi from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("berat",Sequel.cariIsi("select berat from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("gcs",Sequel.cariIsi("select gcs from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
//            param.put("no_sep",sep);
            
            variabel="Atas Persetujuan Dokter";
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",lblNoRawat.getText())>0){
                variabel="MRS";
            }
            if(Sequel.cariInteger("select count(rujuk.no_rawat) from rujuk where rujuk.no_rawat=?",lblNoRawat.getText())>0){
                variabel="Dirujuk";
            }
            param.put("carapulang",variabel);
            variabel=Sequel.cariIsi("select bridging_sep.tujuankunjungan from bridging_sep where bridging_sep.no_rawat=?",lblNoRawat.getText());
            param.put("tujuankunjungan","- "+(variabel.equals("0")?"Konsultasi dokter(pertama)":"Kunjungan Kontrol(ulangan)"));
            variabel=Sequel.cariIsi("select bridging_sep.flagprosedur from bridging_sep where bridging_sep.no_rawat=?",lblNoRawat.getText());
            param.put("flagprosedur",variabel.replaceAll("0","- Prosedur Tidak Berkelanjutan").replaceAll("1","- Prosedur dan Terapi Berkelanjutan"));
            
            tanggal="";
            if(Sequel.cariIsi("select reg_periksa.status_lanjut from reg_periksa where reg_periksa.no_rawat=?",lblNoRawat.getText()).equals("Ralan")){
                param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?",lblNoRawat.getText()));
                tanggal=Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat=?",lblNoRawat.getText());
                param.put("tanggalkeluar",tanggal);
            }else{
                param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ",lblNoRawat.getText()));
                tanggal=Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ",lblNoRawat.getText());
                param.put("tanggalkeluar",tanggal);
            }
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kodedokterregis);
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namadokterregis+"\nID "+(finger.equals("")?kodedokterregis:finger)+"\n"+tanggal); 
            param.put("fingerpasien",Sequel.cariIsi("select sha1(sidikjaripasien.sidikjari) from sidikjaripasien where sidikjaripasien.no_rkm_medis=?",norm));

            try {
                //custom
                ps=koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"+
                    "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"+
                    "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                    "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan') as jnspelayanan,bridging_sep.catatan,bridging_sep.diagawal,"+
                    "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"+
                    "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')) as klsrawat,"+
                    "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "+
                    "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,"+
                    "bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.notelep,"+
                    "bridging_sep.tujuankunjungan,bridging_sep.flagprosedur,bridging_sep.klsnaik,bridging_sep.pembiayaan,"+
                    "bridging_sep.nmdpdjp,bridging_sep.lakalantas from bridging_sep where no_sep=?");
                try {
                    ps.setString(1,sep);
                    rs=ps.executeQuery();
                    if(rs.next()){
                        param.put("no_sep",rs.getString("no_sep"));
                        param.put("norawat",rs.getString("no_rawat"));
                        param.put("nomr",rs.getString("nomr"));
                        param.put("tglsep",rs.getDate("tglsep"));
                        param.put("nama_pasien",rs.getString("nama_pasien"));
                        param.put("notelep",rs.getString("notelep"));
                        param.put("nmpolitujuan",rs.getString("nmpolitujuan"));
                        param.put("nmdpdjp",rs.getString("nmdpdjp"));
                        param.put("nmppkrujukan",rs.getString("nmppkrujukan"));
                        param.put("nmdiagnosaawal",rs.getString("nmdiagnosaawal"));
                        param.put("catatan",rs.getString("catatan"));
                        param.put("peserta",rs.getString("peserta"));
                        param.put("no_kartu",rs.getString("no_kartu"));
                        param.put("klsnaik",rs.getString("klsnaik"));
                        param.put("klsrawat",rs.getString("klsrawat"));
                        param.put("lakalantas",rs.getString("lakalantas"));
                        param.put("klsrawat",rs.getString("klsrawat"));
                        param.put("jnspelayanan",rs.getString("jnspelayanan"));
                        param.put("tanggal_lahir",rs.getDate("tanggal_lahir"));
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
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
            
            param.put("logo",Sequel.cariGambar("select gambar.bpjs from gambar"));
            param.put("prb",Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?",sep));
            param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",lblNoRawat.getText()));
            
            Valid.MyReport("rptSBPK4.jasper","report","::[ Surat Bukti Pelayanan Kesehatan + SEP ]::",param);
//                Map<String, Object> param = new HashMap<>();
//                param.put("namars", akses.getnamars());
//                param.put("alamatrs", akses.getalamatrs());
//                param.put("kotars", akses.getkabupatenrs());
//                param.put("propinsirs", akses.getpropinsirs());
//                param.put("kontakrs", akses.getkontakrs());
//                param.put("logo", Sequel.cariGambar("select gambar.bpjs from gambar"));
//                param.put("prb", Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?", nomorsep));
//                if (Sequel.cariInteger(
//                        "select count(pemeriksaan_ralan.no_rawat) from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='"
//                                + lblNoRawat.getText() + "' and pemeriksaan_ralan.nip='" + kodedokterregis + "'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select pemeriksaan_ralan.penilaian from pemeriksaan_ralan where pemeriksaan_ralan.nip='"
//                                    + kodedokterregis + "' and pemeriksaan_ralan.no_rawat='" + lblNoRawat.getText()
//                                    + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select pemeriksaan_ralan.instruksi from pemeriksaan_ralan where pemeriksaan_ralan.nip='"
//                                    + kodedokterregis + "' and pemeriksaan_ralan.no_rawat='" + lblNoRawat.getText()
//                                    + "'"));
//                } else if (Sequel.cariInteger(
//                        "select count(penilaian_medis_ralan.no_rawat) from penilaian_medis_ralan where penilaian_medis_ralan.no_rawat='"
//                                + lblNoRawat.getText() + "' and penilaian_medis_ralan.kd_dokter='" + kodedokterregis
//                                + "'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select penilaian_medis_ralan.diagnosis from penilaian_medis_ralan where penilaian_medis_ralan.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan.kd_dokter='" + kodedokterregis
//                                    + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select penilaian_medis_ralan.konsulrujuk from penilaian_medis_ralan where penilaian_medis_ralan.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan.kd_dokter='" + kodedokterregis
//                                    + "'"));
//                } else if (Sequel.cariInteger(
//                        "select count(penilaian_medis_ralan_tht.no_rawat) from penilaian_medis_ralan_tht where penilaian_medis_ralan_tht.no_rawat='"
//                                + lblNoRawat.getText() + "' and penilaian_medis_ralan_tht.kd_dokter='" + kodedokterregis
//                                + "'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select penilaian_medis_ralan_tht.diagnosis from penilaian_medis_ralan_tht where penilaian_medis_ralan_tht.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan_tht.kd_dokter='"
//                                    + kodedokterregis + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select penilaian_medis_ralan_tht.konsulrujuk from penilaian_medis_ralan_tht where penilaian_medis_ralan_tht.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan_tht.kd_dokter='"
//                                    + kodedokterregis + "'"));
//                } else if (Sequel.cariInteger(
//                        "select count(penilaian_medis_ralan_psikiatrik.no_rawat) from penilaian_medis_ralan_psikiatrik where penilaian_medis_ralan_psikiatrik.no_rawat='"
//                                + lblNoRawat.getText() + "' and penilaian_medis_ralan_psikiatrik.kd_dokter='"
//                                + kodedokterregis + "'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select penilaian_medis_ralan_psikiatrik.diagnosis1 from penilaian_medis_ralan_psikiatrik where penilaian_medis_ralan_psikiatrik.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan_psikiatrik.kd_dokter='"
//                                    + kodedokterregis + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select penilaian_medis_ralan_psikiatrik.tata from penilaian_medis_ralan_psikiatrik where penilaian_medis_ralan_psikiatrik.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan_psikiatrik.kd_dokter='"
//                                    + kodedokterregis + "'"));
//                } else if (Sequel.cariInteger(
//                        "select count(pemeriksaan_ralan.no_rawat) from pemeriksaan_ralan inner join reg_periksa where pemeriksaan_ralan.no_rawat='"
//                                + lblNoRawat.getText() + "' and reg_periksa.kd_poli = 'U0009'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select pemeriksaan_ralan.penilaian from pemeriksaan_ralan where  pemeriksaan_ralan.no_rawat='"
//                                    + lblNoRawat.getText() + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select pemeriksaan_ralan.instruksi from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='"
//                                    + lblNoRawat.getText() + "'"));
//                }
//
//                // param.put("prosedurutama", Sequel.cariIsi("select pemeriksaan_ralan.rtl from
//                // pemeriksaan_ralan where pemeriksaan_ralan.nip='" + kodedokterregis + "' and
//                // pemeriksaan_ralan.no_rawat='" + lblNoRawat.getText() + "'"));
//                param.put("finger",
//                        "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs()
//                                + "\nDitandatangani secara elektronik oleh " + namadokterregis + "\nID "
//                                + kodedokterregis + "\n");
//                String tindakandr = "";
//                try {
//                    psambiltindakan = koneksi.prepareStatement(
//                            "SELECT\n"
//                                    + "jns_perawatan.nm_perawatan\n"
//                                    + "FROM\n"
//                                    + "	rawat_jl_dr\n"
//                                    + "	INNER JOIN jns_perawatan ON rawat_jl_dr.kd_jenis_prw = jns_perawatan.kd_jenis_prw \n"
//                                    + "WHERE\n"
//                                    + "	rawat_jl_dr.no_rawat=? order by rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat ");
//                    try {
//                        psambiltindakan.setString(1, lblNoRawat.getText());
//                        rsambiltindakan = psambiltindakan.executeQuery();
//                        while (rsambiltindakan.next()) {
//                            tindakandr = tindakandr + rsambiltindakan.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambiltindakan != null) {
//                            rsambiltindakan.close();
//                        }
//                        if (psambiltindakan != null) {
//                            psambiltindakan.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                try {
//                    psambiltindakan = koneksi.prepareStatement(
//                            "SELECT\n"
//                                    + "jns_perawatan.nm_perawatan\n"
//                                    + "FROM\n"
//                                    + "	rawat_jl_drpr\n"
//                                    + "	INNER JOIN jns_perawatan ON rawat_jl_drpr.kd_jenis_prw = jns_perawatan.kd_jenis_prw \n"
//                                    + "WHERE\n"
//                                    + "	rawat_jl_drpr.no_rawat=? order by rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat ");
//                    try {
//                        psambiltindakan.setString(1, lblNoRawat.getText());
//                        rsambiltindakan = psambiltindakan.executeQuery();
//                        while (rsambiltindakan.next()) {
//                            tindakandr = tindakandr + rsambiltindakan.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambiltindakan != null) {
//                            rsambiltindakan.close();
//                        }
//                        if (psambiltindakan != null) {
//                            psambiltindakan.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                try {
//                    psambiltindakan = koneksi.prepareStatement(
//                            "SELECT\n"
//                                    + "jns_perawatan.nm_perawatan\n"
//                                    + "FROM\n"
//                                    + "	rawat_jl_pr\n"
//                                    + "	INNER JOIN jns_perawatan ON rawat_jl_pr.kd_jenis_prw = jns_perawatan.kd_jenis_prw \n"
//                                    + "WHERE\n"
//                                    + "	rawat_jl_pr.no_rawat=? order by rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat ");
//                    try {
//                        psambiltindakan.setString(1, lblNoRawat.getText());
//                        rsambiltindakan = psambiltindakan.executeQuery();
//                        while (rsambiltindakan.next()) {
//                            tindakandr = tindakandr + rsambiltindakan.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambiltindakan != null) {
//                            rsambiltindakan.close();
//                        }
//                        if (psambiltindakan != null) {
//                            psambiltindakan.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                try {
//                    psambillab = koneksi.prepareStatement(
//                            "select jns_perawatan_lab.nm_perawatan "
//                                    + " from periksa_lab inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw where "
//                                    + " periksa_lab.no_rawat=? ");
//                    try {
//                        psambillab.setString(1, lblNoRawat.getText());
//                        rsambillab = psambillab.executeQuery();
//                        while (rsambillab.next()) {
//                            tindakandr = tindakandr + rsambillab.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambillab != null) {
//                            rsambillab.close();
//                        }
//                        if (psambillab != null) {
//                            psambillab.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                try {
//                    psambilrad = koneksi.prepareStatement(
//                            "select jns_perawatan_radiologi.nm_perawatan "
//                                    + " from periksa_radiologi inner join jns_perawatan_radiologi on jns_perawatan_radiologi.kd_jenis_prw=periksa_radiologi.kd_jenis_prw where "
//                                    + " periksa_radiologi.no_rawat=?");
//                    try {
//                        psambilrad.setString(1, lblNoRawat.getText());
//                        rsambilrad = psambilrad.executeQuery();
//                        while (rsambilrad.next()) {
//                            tindakandr = tindakandr + rsambilrad.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambilrad != null) {
//                            rsambilrad.close();
//                        }
//                        if (psambilrad != null) {
//                            psambilrad.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                param.put("prosedurutama", tindakandr);
//                param.put("dokterregis", namadokterregis);
//                param.put("parameter", nomorsep);
//                Valid.MyReport("rptBridgingSEP.jasper", "report", "::[ Cetak SEP ]::", param);
//                this.setCursor(Cursor.getDefaultCursor());
                // kondisi kalau pasien rawat inap
            } else {
                String nomorsep = Sequel.cariIsi(
                        "select bridging_sep.no_sep from bridging_sep where bridging_sep.jnspelayanan='1' and bridging_sep.no_rawat='"
                                + lblNoRawat.getText() + "'");
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("prb", Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?", nomorsep));
                param.put("logo", Sequel.cariGambar("select gambar.bpjs from gambar"));
                param.put("parameter", nomorsep);
                Valid.MyReport("rptBridgingSEP.jasper", "report", "::[ Cetak SEP ]::", param);
                this.setCursor(Cursor.getDefaultCursor());
            }

        }
    }// GEN-LAST:event_btnSEPResumeActionPerformed

    private void btnResumeRanapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnResumeRanapActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnResumeRanapActionPerformed

    private void btnInvoiceActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInvoiceActionPerformed
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {
            Valid.panggilUrl("berkasrawat/pages/billingtampil.php?norawat=" + lblNoRawat.getText() + "");
        }
    }// GEN-LAST:event_btnInvoiceActionPerformed

    private void btnAwalMedisIGDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAwalMedisIGDActionPerformed
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {

            String kodedokterregis = Sequel
                    .cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat='"
                            + lblNoRawat.getText() + "'");
            String namadokterregis = Sequel
                    .cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter='" + kodedokterregis + "'");
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("lokalis", Sequel.cariGambar("select lokalis from gambar"));
            finger = Sequel.cariIsi(
                    "select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",
                    kodedokterregis);
            param.put("finger",
                    "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh " + namadokterregis + "\nID "
                            + (finger.equals("") ? kodedokterregis : finger) + "\n");

            Valid.MyReportqry("rptCetakPenilaianAwalMedisIGD.jasper", "report",
                    "::[ Laporan Penilaian Awal Medis IGD ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_igd.tanggal,"
                            + "penilaian_medis_igd.kd_dokter,penilaian_medis_igd.anamnesis,penilaian_medis_igd.hubungan,penilaian_medis_igd.keluhan_utama,penilaian_medis_igd.rps,penilaian_medis_igd.rpk,penilaian_medis_igd.rpd,penilaian_medis_igd.rpo,penilaian_medis_igd.alergi,"
                            + "penilaian_medis_igd.keadaan,penilaian_medis_igd.gcs,penilaian_medis_igd.kesadaran,penilaian_medis_igd.td,penilaian_medis_igd.nadi,penilaian_medis_igd.rr,penilaian_medis_igd.suhu,penilaian_medis_igd.spo,penilaian_medis_igd.bb,penilaian_medis_igd.tb,"
                            + "penilaian_medis_igd.kepala,penilaian_medis_igd.mata,penilaian_medis_igd.mulut,penilaian_medis_igd.leher,penilaian_medis_igd.thoraks,penilaian_medis_igd.abdomen,penilaian_medis_igd.ekstremitas,penilaian_medis_igd.genital,penilaian_medis_igd.ket_fisik,"
                            + "penilaian_medis_igd.ket_lokalis,penilaian_medis_igd.ekg,penilaian_medis_igd.rad,penilaian_medis_igd.lab,penilaian_medis_igd.diagnosis,penilaian_medis_igd.tata,dokter.nm_dokter,penilaian_medis_igd.keadaan1,penilaian_medis_igd.kulit,penilaian_medis_igd.dada,penilaian_medis_igd.hidung,penilaian_medis_igd.telinga,penilaian_medis_igd.rencanaranap,penilaian_medis_igd.indikasiranap, penilaian_medis_igd.nyeri,penilaian_medis_igd.lasupdate "
                            + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "inner join penilaian_medis_igd on reg_periksa.no_rawat=penilaian_medis_igd.no_rawat "
                            + "inner join dokter on penilaian_medis_igd.kd_dokter=dokter.kd_dokter where penilaian_medis_igd.no_rawat='"
                            + lblNoRawat.getText() + "'",
                    param);
        }
    }// GEN-LAST:event_btnAwalMedisIGDActionPerformed

    private void btnHasilLabActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHasilLabActionPerformed
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCariPeriksaLab hasillab = new DlgCariPeriksaLab(null, false);
            hasillab.HasilLabPDFKlaimBPJS(lblNoRawat.getText(), lblNoRM.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_btnHasilLabActionPerformed

    private void btnHasilRadActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHasilRadActionPerformed
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCariPeriksaRadiologi hasilrad = new DlgCariPeriksaRadiologi(null, false);
            hasilrad.RadPdfKlaimBPJS(lblNoRawat.getText(), lblNoRM.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }// GEN-LAST:event_btnHasilRadActionPerformed

    private void btnSurkonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSurkonActionPerformed
        // String tglregis = Sequel.cariIsi("select reg_periksa.tgl_registrasi from
        // reg_periksa where reg_periksa.no_rawat='" + lblNoRawat.getText() + "'");
        // String kodepoli = Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa
        // where reg_periksa.no_rawat='" + lblNoRawat.getText() + "'");
        // String kodedokter = Sequel.cariIsi("select reg_periksa.kd_dokter from
        // reg_periksa where reg_periksa.no_rawat='" + lblNoRawat.getText() + "'");
        // String nomorrm = Sequel.cariIsi("select reg_periksa.no_rkm_medis from
        // reg_periksa where reg_periksa.no_rawat='" + lblNoRawat.getText() + "'");
        // String nomorrawatkontrol = Sequel.cariIsi("select skdp_bpjs_new.no_rawat from
        // skdp_bpjs_new where skdp_bpjs_new.no_rkm_medis='" + nomorrm + "' and
        // skdp_bpjs_new.tanggal_surat='" + tglregis + "' and skdp_bpjs_new.kd_dokter='"
        // + kodedokter + "' and skdp_bpjs_new.kd_poli='" + kodepoli + "'");
        String nosuratkontrol = Sequel.cariIsi(
                "select bridging_sep.noskdp from bridging_sep where bridging_sep.no_sep='" + lblNoSEP.getText() + "'");
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {
            if (Sequel.cariInteger("select count(no_sep) from bridging_surat_kontrol_bpjs where no_surat='"
                    + nosuratkontrol + "'") > 0) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                BPJSSuratKontrol suratkontrol = new BPJSSuratKontrol(null, false);
                suratkontrol.SuratKontrolKlaim(lblNoRawat.getText(), lblNoSEP.getText(), lblNoRM.getText()); //method nya belum fix
                this.setCursor(Cursor.getDefaultCursor());
            } else if (Sequel.cariInteger(
                    "select count(no_rawat) from skdp_bpjs_new where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
                String kodedokter = Sequel
                        .cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat='"
                                + lblNoRawat.getText() + "'");
                String kodepoli = Sequel
                        .cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat='"
                                + lblNoRawat.getText() + "'");
                String norawatpilih = Sequel.cariIsi(
                        "select reg_periksa.no_rawat from reg_periksa where reg_periksa.kd_dokter='" + kodedokter
                                + "' and reg_periksa.kd_poli='" + kodepoli + "' and reg_periksa.no_rkm_medis='"
                                + lblNoRM.getText() + "' and reg_periksa.no_rawat != '" + lblNoRawat.getText()
                                + "' order by reg_periksa.no_rawat desc limit 1");
                SuratKontrol suratkontrol = new SuratKontrol(null, false);
                suratkontrol.SuratKontrolKlaim(norawatpilih, lblNoRM.getText());
            }

        }

    }// GEN-LAST:event_btnSurkonActionPerformed

    private void btnSPRIActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSPRIActionPerformed
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {
            if (Sequel.cariInteger("select count(no_sep) from bridging_surat_kontrol_bpjs where no_sep='"
                    + lblNoSEP.getText() + "'") > 0) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                BPJSSPRI spri = new BPJSSPRI(null, false);
                spri.CetakSPRIKlaim(lblNoSEP.getText(), lblNoRawat.getText());
                this.setCursor(Cursor.getDefaultCursor());
            }

        }
    }// GEN-LAST:event_btnSPRIActionPerformed

    private void btnPenunjangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPenunjangActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnPenunjangActionPerformed

    private void BtnSimpanDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSimpanDiagnosaActionPerformed
        if (!lblNoRawat.getText().equals("")) {
            panelDiagnosa1.setRM(lblNoRawat.getText(), lblNoRM.getText(), Valid.SetTgl(DTPCari1.getSelectedItem() + ""),
                    Valid.SetTgl(DTPCari2.getSelectedItem() + ""), lblStatusRawat.getText(), TCari.getText().trim());
            panelDiagnosa1.simpan();
            tampilicareBPJS();
        }
    }// GEN-LAST:event_BtnSimpanDiagnosaActionPerformed

    private void BtnSimpanDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnSimpanDiagnosaKeyPressed

    }// GEN-LAST:event_BtnSimpanDiagnosaKeyPressed

    private void BtnHapusDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnHapusDiagnosaActionPerformed
        if (!lblNoRawat.getText().equals("")) {
            panelDiagnosa1.setRM(lblNoRawat.getText(), lblNoRM.getText(), Valid.SetTgl(DTPCari1.getSelectedItem() + ""),
                    Valid.SetTgl(DTPCari2.getSelectedItem() + ""), "Ralan", TCari.getText().trim());
            panelDiagnosa1.hapus();
            tampilicareBPJS();
        }
    }// GEN-LAST:event_BtnHapusDiagnosaActionPerformed

    private void BtnHapusDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnHapusDiagnosaKeyPressed

    }// GEN-LAST:event_BtnHapusDiagnosaKeyPressed

    private void panelDiagnosa1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_panelDiagnosa1MouseClicked
        panelDiagnosa1.setRM(lblNoRawat.getText(), lblNoRM.getText(), Valid.SetTgl(DTPCari1.getSelectedItem() + ""),
                Valid.SetTgl(DTPCari2.getSelectedItem() + ""), "Ralan", lblNoRawat.getText().trim());
        panelDiagnosa1.pilihTab();
        LCount.setText(panelDiagnosa1.getRecord() + "");
    }// GEN-LAST:event_panelDiagnosa1MouseClicked

    private void panelDiagnosa1MouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_panelDiagnosa1MouseEntered
        // TODO add your handling code here:
    }// GEN-LAST:event_panelDiagnosa1MouseEntered

    private void btnSEPResume1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSEPResume1ActionPerformed
        // Valid.panggilUrl("inacbg/login.php?act=login&usere=RsIndriati&passwordte=BoyoLali&page=LihatPDFNCC&nomorsep="
        // + lblNoSEP.getText());
        Base64ToPDF(lblNoSEP.getText());
    }// GEN-LAST:event_btnSEPResume1ActionPerformed

    private void btnSEPResume2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSEPResume2ActionPerformed
        Base64ToPDFInvoice(lblNoSEP.getText());
    }// GEN-LAST:event_btnSEPResume2ActionPerformed

    private void ChkSuratKontrolActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ChkSuratKontrolActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_ChkSuratKontrolActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKompilasiBerkasKlaimBPJS dialog = new DlgKompilasiBerkasKlaimBPJS(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapusDiagnosa;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpanDiagnosa;
    private widget.Button BtnValidasiQR;
    private widget.CekBox ChkSuratKontrol;
    private widget.ComboBox CmbStts;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormMenu;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.panelisi PanelContentIcareBPJS;
    private javax.swing.JMenuItem PilihSemua;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollHTML;
    private widget.TextBox TCari;
    private widget.Button btnAwalMedisIGD;
    private widget.Button btnHasilLab;
    private widget.Button btnHasilRad;
    private widget.Button btnInvoice;
    private widget.Button btnPenunjang;
    private widget.Button btnResumeRanap;
    private widget.Button btnSEPResume;
    private widget.Button btnSEPResume1;
    private widget.Button btnSEPResume2;
    private widget.Button btnSPRI;
    private widget.Button btnSurkon;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label lblCoderNIK;
    private widget.Label lblNamaPasien;
    private widget.Label lblNoRM;
    private widget.Label lblNoRawat;
    private widget.Label lblNoSEP;
    private widget.Label lblNoSEP22;
    private widget.Label lblStatusRawat;
    private laporan.PanelDiagnosa panelDiagnosa1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelInvoices;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbKompilasi;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String kodepenjamin = "and reg_periksa.kd_pj='BPJ'";
        String statusrawat = "";
        if (!CmbStts.getSelectedItem().toString().equals("Semua")) {
            statusrawat = " and reg_periksa.status_lanjut='" + CmbStts.getSelectedItem().toString() + "' ";
        }
        try {
            Valid.tabelKosong(tabMode);
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, reg_periksa.status_lanjut,reg_periksa.tgl_registrasi,reg_periksa.jam_reg, poliklinik.nm_poli from reg_periksa join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis"
                            + " join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "
                            + "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.status_bayar='Sudah Bayar' "
                            + kodepenjamin + statusrawat
                            + " and (reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or poliklinik.nm_poli like ?) "
                            + "order by reg_periksa.tgl_registrasi, reg_periksa.jam_reg");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    // System.out.println(GD001);
                    tabMode.addRow(new Object[] {
                            rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                            rs.getString(6), rs.getString(7),
                            Sequel.cariIsi(
                                    "select penyakit.nm_penyakit from diagnosa_pasien join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.prioritas='1' and diagnosa_pasien.no_rawat=?",
                                    rs.getString(1))
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
        LCount.setText("" + (tabMode.getRowCount() - 2));
    }

    public void emptTeks() {
        TCari.setText("");
        lblNamaPasien.setText("");
        lblNoRawat.setText("");
        lblNoRM.setText("");
        lblStatusRawat.setText("");
        lblNoSEP.setText("");

        if (Sequel.cariInteger(
                "select count(no_rawat) from bridging_sep where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            btnSEPResume.setText("Ada");
        } else {
            btnSEPResume.setText("Tidak Ada");
        }
        if (Sequel.cariInteger(
                "select count(no_rawat) from resume_pasien_ranap where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            btnResumeRanap.setText("Ada");
        } else {
            btnResumeRanap.setText("Tidak Ada");
        }
        if (Sequel
                .cariInteger("select count(no_rawat) from billing where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            btnInvoice.setText("Ada");
        } else {
            btnInvoice.setText("Tidak Ada");
        }
        if (Sequel.cariInteger(
                "select count(no_rawat) from penilaian_medis_igd where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            btnAwalMedisIGD.setText("Ada");
        } else {
            btnAwalMedisIGD.setText("Tidak Ada");
        }
        if (Sequel.cariInteger(
                "select count(no_rawat) from periksa_lab where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            btnHasilLab.setText("Ada");
        } else {
            btnHasilLab.setText("Tidak Ada");
        }
        if (Sequel.cariInteger(
                "select count(no_rawat) from periksa_radiologi where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            btnHasilRad.setText("Ada");
        } else {
            btnHasilRad.setText("Tidak Ada");
        }
        if (Sequel.cariInteger("select count(no_sep) from bridging_surat_kontrol_bpjs where no_sep='"
                + lblNoSEP.getText() + "'") > 0) {
            btnSurkon.setText("Ada");
        } else if (Sequel.cariInteger(
                "select count(no_rawat) from skdp_bpjs_new where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            btnSurkon.setText("Ada");
        } else {
            btnSurkon.setText("Tidak Ada");
        }
        if (Sequel.cariInteger("select count(no_rawat) from bridging_surat_pri_bpjs where no_rawat='"
                + lblNoRawat.getText() + "'") > 0) {
            btnSPRI.setText("Ada");
        } else {
            btnSPRI.setText("Tidak Ada");
        }
        if (Sequel.cariInteger(
                "select count(form_pemeriksaan.no_rawat) from form_pemeriksaan where form_pemeriksaan.no_rawat='"
                        + lblNoRawat.getText() + "'") > 0) {
            btnPenunjang.setText("Ada");
        } else {
            btnPenunjang.setText("Tidak Ada");
        }
        tampilicareBPJS();

    }

    private void getData() {
        if (tbKompilasi.getSelectedRow() != -1) {
            lblNamaPasien.setText(tbKompilasi.getValueAt(tbKompilasi.getSelectedRow(), 2).toString());
            lblNoRawat.setText(tbKompilasi.getValueAt(tbKompilasi.getSelectedRow(), 0).toString());
            lblNoRM.setText(tbKompilasi.getValueAt(tbKompilasi.getSelectedRow(), 1).toString());
            lblStatusRawat.setText(tbKompilasi.getValueAt(tbKompilasi.getSelectedRow(), 3).toString());
            lblNoSEP.setText(Sequel.cariIsi("select bridging_sep.no_sep from bridging_sep where bridging_sep.no_rawat='"
                    + lblNoRawat.getText() + "'"));
            String nosuratkontrol = Sequel
                    .cariIsi("select bridging_sep.noskdp from bridging_sep where bridging_sep.no_sep='"
                            + lblNoSEP.getText() + "'");
            if (Sequel.cariInteger(
                    "select count(no_rawat) from bridging_sep where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
                btnSEPResume.setText("Ada");
            } else {
                btnSEPResume.setText("Tidak Ada");
            }
            if (Sequel.cariInteger("select count(no_rawat) from resume_pasien_ranap where no_rawat='"
                    + lblNoRawat.getText() + "'") > 0) {
                btnResumeRanap.setText("Ada");
            } else {
                btnResumeRanap.setText("Tidak Ada");
            }
            if (Sequel.cariInteger(
                    "select count(no_rawat) from billing where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
                btnInvoice.setText("Ada");
            } else {
                btnInvoice.setText("Tidak Ada");
            }
            if (Sequel.cariInteger("select count(no_rawat) from penilaian_medis_igd where no_rawat='"
                    + lblNoRawat.getText() + "'") > 0) {
                btnAwalMedisIGD.setText("Ada");
            } else {
                btnAwalMedisIGD.setText("Tidak Ada");
            }
            if (Sequel.cariInteger(
                    "select count(no_rawat) from periksa_lab where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
                btnHasilLab.setText("Ada");
            } else {
                btnHasilLab.setText("Tidak Ada");
            }
            if (Sequel.cariInteger("select count(no_rawat) from periksa_radiologi where no_rawat='"
                    + lblNoRawat.getText() + "'") > 0) {
                btnHasilRad.setText("Ada");
            } else {
                btnHasilRad.setText("Tidak Ada");
            }

            String kodedokter = Sequel
                    .cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat='"
                            + lblNoRawat.getText() + "'");
            String kodepoli = Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat='"
                    + lblNoRawat.getText() + "'");
            String norawatpilih = Sequel
                    .cariIsi("select reg_periksa.no_rawat from reg_periksa where reg_periksa.kd_dokter='" + kodedokter
                            + "' and reg_periksa.kd_poli='" + kodepoli + "' and reg_periksa.no_rkm_medis='"
                            + lblNoRM.getText() + "' and reg_periksa.no_rawat != '" + lblNoRawat.getText()
                            + "' order by reg_periksa.no_rawat desc limit 1");

            if (Sequel.cariInteger("select count(no_surat) from bridging_surat_kontrol_bpjs where no_surat='"
                    + nosuratkontrol + "'") > 0) {
                btnSurkon.setText("Ada");
            } else if (Sequel.cariInteger(
                    "select count(no_rawat) from skdp_bpjs_new where no_rawat='" + norawatpilih + "'") > 0) {
                btnSurkon.setText("Ada");
            } else {
                btnSurkon.setText("Tidak Ada");
            }
            if (Sequel.cariInteger("select count(no_rawat) from bridging_surat_pri_bpjs where no_rawat='"
                    + lblNoRawat.getText() + "'") > 0) {
                btnSPRI.setText("Ada");
            } else {
                btnSPRI.setText("Tidak Ada");
            }
            if (Sequel.cariInteger(
                    "select count(form_pemeriksaan.no_rawat) from form_pemeriksaan where form_pemeriksaan.no_rawat='"
                            + lblNoRawat.getText() + "'") > 0) {
                btnPenunjang.setText("Ada");
            } else {
                btnPenunjang.setText("Tidak Ada");
            }
            panelDiagnosa1.setRM(lblNoRawat.getText(), lblNoRM.getText(), Valid.SetTgl(DTPCari1.getSelectedItem() + ""),
                    Valid.SetTgl(DTPCari2.getSelectedItem() + ""), "Ralan", lblNoRawat.getText().trim());
            panelDiagnosa1.pilihTab();
            tampilicareBPJS();
            tampilInvoice();
        }
    }

    private void isRawat() {

    }

    private void cetakAsesmenMedisIgd(String norawat) {
        String nomorrawat = norawat.replaceAll("/", "");
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        param.put("lokalis", Sequel.cariGambar("select lokalis from gambar"));
        // finger = Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join
        // pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",
        // tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
        param.put("finger", "Belum Final");
        System.out.println("fungsi cetak");

        Valid.ReportKompilasiBerkas("rptCetakPenilaianAwalMedisIGD.jasper", "report",
                "::[ Laporan Penilaian Awal Medis IGD ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_igd.tanggal,"
                        + "penilaian_medis_igd.kd_dokter,penilaian_medis_igd.anamnesis,penilaian_medis_igd.hubungan,penilaian_medis_igd.keluhan_utama,penilaian_medis_igd.rps,penilaian_medis_igd.rpk,penilaian_medis_igd.rpd,penilaian_medis_igd.rpo,penilaian_medis_igd.alergi,"
                        + "penilaian_medis_igd.keadaan,penilaian_medis_igd.gcs,penilaian_medis_igd.kesadaran,penilaian_medis_igd.td,penilaian_medis_igd.nadi,penilaian_medis_igd.rr,penilaian_medis_igd.suhu,penilaian_medis_igd.spo,penilaian_medis_igd.bb,penilaian_medis_igd.tb,"
                        + "penilaian_medis_igd.kepala,penilaian_medis_igd.mata,penilaian_medis_igd.mulut,penilaian_medis_igd.leher,penilaian_medis_igd.thoraks,penilaian_medis_igd.abdomen,penilaian_medis_igd.ekstremitas,penilaian_medis_igd.genital,penilaian_medis_igd.ket_fisik,"
                        + "penilaian_medis_igd.ket_lokalis,penilaian_medis_igd.ekg,penilaian_medis_igd.rad,penilaian_medis_igd.lab,penilaian_medis_igd.diagnosis,penilaian_medis_igd.tata,dokter.nm_dokter,penilaian_medis_igd.keadaan1,penilaian_medis_igd.kulit,penilaian_medis_igd.dada,penilaian_medis_igd.hidung,penilaian_medis_igd.telinga,penilaian_medis_igd.rencanaranap,penilaian_medis_igd.indikasiranap, penilaian_medis_igd.nyeri,penilaian_medis_igd.lasupdate "
                        + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join penilaian_medis_igd on reg_periksa.no_rawat=penilaian_medis_igd.no_rawat "
                        + "inner join dokter on penilaian_medis_igd.kd_dokter=dokter.kd_dokter where penilaian_medis_igd.no_rawat='"
                        + norawat + "'",
                param, norawat, norekammedis, "AWALMEDISIGD");
    }

    private void cetakTriaseIGD(String norawat) {
        RMTriaseIGD triase = new RMTriaseIGD(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //triase.BtnSimpanDokumenRMETriase(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakObsIGD(String norawat) {
        RMDataCatatanObservasiIGD obvIGD = new RMDataCatatanObservasiIGD(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        obvIGD.CetakObsIGD(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakObsIGDObat(String norawat) {
        RMDataCatatanObservasiIGD obvIGD = new RMDataCatatanObservasiIGD(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        obvIGD.CetakObsIGDObat(norawat, norekammedis); //methodnya belum ada
    }

//    private void cetakCatatanKeperawatan(String norawat) {
//        RMDataCatatanKeperawatan catkep = new RMDataCatatanKeperawatan(null, false);
//        String norekammedis = Sequel.cariIsi(
//                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        catkep.MnCatatanKeperawatan(norawat, norekammedis);
//    } //methodnya belum ada

    private void cetakSEP(String norawat) {
        BPJSDataSEP datasep = new BPJSDataSEP(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        datasep.kompilasiSEPPdf(norawat, norekammedis); //methodnya belum ada
    }

    private void ResumePasien(String norawat) {
        RMDataResumePasien resumepasien = new RMDataResumePasien(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        resumepasien.ResumePasienPdf(norawat, norekammedis); //methodnya belum ada
    }

    private void ResumePasienNCC(String norawat) {
        RMDataResumePasien resumepasien = new RMDataResumePasien(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        resumepasien.ResumePasienPdfNCC(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakObsIGDNyeri(String norawat) {
        RMDataCatatanObservasiIGD obvIGD = new RMDataCatatanObservasiIGD(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        obvIGD.CetakObsIGDNyeri(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakDatSOS(String norekammedis, String norawat) {
        DlgPasien pasien = new DlgPasien(null, false);
        String status = Sequel.cariIsi(
                "select reg_periksa.stts_daftar from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        String statuslanjut = Sequel.cariIsi(
                "select reg_periksa.status_lanjut from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        if (status.equals("Baru")) {
            if (statuslanjut.equals("Ralan")) {
//                pasien.CetakDatsosRajalRME(norawat, norekammedis); //methodnya belum ada
            }
            if (statuslanjut.equals("Ranap")) {
//                pasien.CetakDatsosRajalRME(norawat, norekammedis); //methodnya belum ada
            }
        }

    }

    private void cetakSkriningCovid(String norawat) {
        RMDeteksiDiniCorona skriningcov = new RMDeteksiDiniCorona(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        skriningcov.cetakSkriningCovidRME(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakAsesmenKeperawatanIGD(String norawat) {
        String nomorrawat = norawat.replaceAll("/", "");
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        param.put("nyeri", Sequel.cariGambar("select nyeri from gambar"));
        finger = "beta";
        param.put("finger", "Belum Final");
        try {
            masalahkeperawatan = "";
            ps2 = koneksi.prepareStatement(
                    "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "
                            + "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "
                            + "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
            try {
                ps2.setString(1, norawat);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    masalahkeperawatan = rs2.getString("nama_masalah") + ", " + masalahkeperawatan;
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
            System.out.println("Notif : " + e);
        }
        param.put("masalah", masalahkeperawatan);
        Valid.ReportKompilasiBerkas("rptCetakPenilaianAwalKeperawatanIGD.jasper", "report",
                "::[ Laporan Penilaian Awal Keperawatan Ralan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_igd.tanggal,"
                        + "penilaian_awal_keperawatan_igd.informasi,penilaian_awal_keperawatan_igd.td,penilaian_awal_keperawatan_igd.nadi,penilaian_awal_keperawatan_igd.rr,penilaian_awal_keperawatan_igd.suhu,penilaian_awal_keperawatan_igd.bb,penilaian_awal_keperawatan_igd.tb,"
                        + "penilaian_awal_keperawatan_igd.nadi,penilaian_awal_keperawatan_igd.rr,penilaian_awal_keperawatan_igd.suhu,penilaian_awal_keperawatan_igd.gcs,penilaian_awal_keperawatan_igd.bb,penilaian_awal_keperawatan_igd.tb,penilaian_awal_keperawatan_igd.bmi,penilaian_awal_keperawatan_igd.keluhan_utama,"
                        + "penilaian_awal_keperawatan_igd.rpd,penilaian_awal_keperawatan_igd.rpk,penilaian_awal_keperawatan_igd.rpo,penilaian_awal_keperawatan_igd.alergi,penilaian_awal_keperawatan_igd.alat_bantu,penilaian_awal_keperawatan_igd.ket_bantu,penilaian_awal_keperawatan_igd.prothesa,"
                        + "penilaian_awal_keperawatan_igd.ket_pro,penilaian_awal_keperawatan_igd.adl,penilaian_awal_keperawatan_igd.status_psiko,penilaian_awal_keperawatan_igd.ket_psiko,penilaian_awal_keperawatan_igd.hub_keluarga,penilaian_awal_keperawatan_igd.tinggal_dengan,"
                        + "penilaian_awal_keperawatan_igd.ket_tinggal,penilaian_awal_keperawatan_igd.ekonomi,penilaian_awal_keperawatan_igd.edukasi,penilaian_awal_keperawatan_igd.ket_edukasi,penilaian_awal_keperawatan_igd.berjalan_a,penilaian_awal_keperawatan_igd.berjalan_b,"
                        + "penilaian_awal_keperawatan_igd.berjalan_c,penilaian_awal_keperawatan_igd.hasil,penilaian_awal_keperawatan_igd.lapor,penilaian_awal_keperawatan_igd.ket_lapor,penilaian_awal_keperawatan_igd.sg1,penilaian_awal_keperawatan_igd.nilai1,penilaian_awal_keperawatan_igd.sg2,penilaian_awal_keperawatan_igd.nilai2,"
                        + "penilaian_awal_keperawatan_igd.total_hasil,penilaian_awal_keperawatan_igd.nyeri,penilaian_awal_keperawatan_igd.provokes,penilaian_awal_keperawatan_igd.ket_provokes,penilaian_awal_keperawatan_igd.quality,penilaian_awal_keperawatan_igd.ket_quality,penilaian_awal_keperawatan_igd.lokasi,penilaian_awal_keperawatan_igd.menyebar,"
                        + "penilaian_awal_keperawatan_igd.skala_nyeri,penilaian_awal_keperawatan_igd.durasi,penilaian_awal_keperawatan_igd.nyeri_hilang,penilaian_awal_keperawatan_igd.ket_nyeri,penilaian_awal_keperawatan_igd.pada_dokter_anak,penilaian_awal_keperawatan_igd.ket_dokter,penilaian_awal_keperawatan_igd.rencana, penilaian_awal_keperawatan_igd.tindakanskrining, penilaian_awal_keperawatan_igd.mews, penilaian_awal_keperawatan_igd.pews, penilaian_awal_keperawatan_igd.news,"
                        + "penilaian_awal_keperawatan_igd.nip,petugas.nama,penilaian_awal_keperawatan_igd.budaya,penilaian_awal_keperawatan_igd.ket_budaya,penilaian_awal_keperawatan_igd.caramasuk, penilaian_awal_keperawatan_igd.pada_dokter_dewasa "
                        + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join penilaian_awal_keperawatan_igd on reg_periksa.no_rawat=penilaian_awal_keperawatan_igd.no_rawat "
                        + "inner join petugas on penilaian_awal_keperawatan_igd.nip=petugas.nip "
                        + "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "
                        + "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where reg_periksa.no_rawat='"
                        + norawat + "'",
                param, norawat, norekammedis, "AWALKEPIGD");

    }

    private void cetakSummaryList(String norawat) {

    }

    private void cetakAsesmenMedisUmum(String norawat) {
        RMPenilaianAwalMedisRalanDewasa awalralan = new RMPenilaianAwalMedisRalanDewasa(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        awalralan.AwalMedisRalanPdf(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakAsesmenKeperawatanUmum(String norawat) {
        String nomorrawat = norawat.replaceAll("/", "");
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        param.put("nyeri", Sequel.cariGambar("select nyeri from gambar"));
        finger = "beta";
        param.put("finger", "Belum Final");
        try {
            masalahkeperawatan = "";
            ps2 = koneksi.prepareStatement(
                    "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "
                            + "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "
                            + "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
            try {
                ps2.setString(1, norawat);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    masalahkeperawatan = rs2.getString("nama_masalah") + ", " + masalahkeperawatan;
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
            System.out.println("Notif : " + e);
        }
        param.put("masalah", masalahkeperawatan);
        Valid.ReportKompilasiBerkas("rptCetakPenilaianAwalKeperawatanRalan.jasper", "report",
                "::[ Laporan Penilaian Awal Keperawatan Ralan ]::",
                "SELECT\n"
                        + "	reg_periksa.no_rawat, \n"
                        + "	pasien.no_rkm_medis, \n"
                        + "	pasien.nm_pasien, \n"
                        + "	IF\n"
                        + "	( pasien.jk = 'L', 'Laki-Laki', 'Perempuan' ) AS jk, \n"
                        + "	pasien.tgl_lahir, \n"
                        + "	pasien.agama, \n"
                        + "	bahasa_pasien.nama_bahasa, \n"
                        + "	cacat_fisik.nama_cacat, \n"
                        + "	penilaian_awal_keperawatan_ralan.tanggal, \n"
                        + "	penilaian_awal_keperawatan_ralan.informasi, \n"
                        + "	penilaian_awal_keperawatan_ralan.td, \n"
                        + "	penilaian_awal_keperawatan_ralan.nadi, \n"
                        + "	penilaian_awal_keperawatan_ralan.rr, \n"
                        + "	penilaian_awal_keperawatan_ralan.suhu, \n"
                        + "	penilaian_awal_keperawatan_ralan.bb, \n"
                        + "	penilaian_awal_keperawatan_ralan.tb, \n"
                        + "	penilaian_awal_keperawatan_ralan.nadi, \n"
                        + "	penilaian_awal_keperawatan_ralan.rr, \n"
                        + "	penilaian_awal_keperawatan_ralan.suhu, \n"
                        + "	penilaian_awal_keperawatan_ralan.gcs, \n"
                        + "	penilaian_awal_keperawatan_ralan.bb, \n"
                        + "	penilaian_awal_keperawatan_ralan.tb, \n"
                        + "	penilaian_awal_keperawatan_ralan.bmi, \n"
                        + "	penilaian_awal_keperawatan_ralan.keluhan_utama, \n"
                        + "	penilaian_awal_keperawatan_ralan.rpd, \n"
                        + "	penilaian_awal_keperawatan_ralan.rpk, \n"
                        + "	penilaian_awal_keperawatan_ralan.rpo, \n"
                        + "	penilaian_awal_keperawatan_ralan.alergi, \n"
                        + "	penilaian_awal_keperawatan_ralan.alat_bantu, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_bantu, \n"
                        + "	penilaian_awal_keperawatan_ralan.prothesa, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_pro, \n"
                        + "	penilaian_awal_keperawatan_ralan.adl, \n"
                        + "	penilaian_awal_keperawatan_ralan.status_psiko, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_psiko, \n"
                        + "	penilaian_awal_keperawatan_ralan.hub_keluarga, \n"
                        + "	penilaian_awal_keperawatan_ralan.tinggal_dengan, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_tinggal, \n"
                        + "	penilaian_awal_keperawatan_ralan.ekonomi, \n"
                        + "	penilaian_awal_keperawatan_ralan.edukasi, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_edukasi, \n"
                        + "	penilaian_awal_keperawatan_ralan.berjalan_a, \n"
                        + "	penilaian_awal_keperawatan_ralan.berjalan_b, \n"
                        + "	penilaian_awal_keperawatan_ralan.berjalan_c, \n"
                        + "	penilaian_awal_keperawatan_ralan.hasil, \n"
                        + "	penilaian_awal_keperawatan_ralan.lapor, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_lapor, \n"
                        + "	penilaian_awal_keperawatan_ralan.sg1, \n"
                        + "	penilaian_awal_keperawatan_ralan.nilai1, \n"
                        + "	penilaian_awal_keperawatan_ralan.sg2, \n"
                        + "	penilaian_awal_keperawatan_ralan.nilai2, \n"
                        + "	penilaian_awal_keperawatan_ralan.total_hasil, \n"
                        + "	penilaian_awal_keperawatan_ralan.nyeri, \n"
                        + "	penilaian_awal_keperawatan_ralan.provokes, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_provokes, \n"
                        + "	penilaian_awal_keperawatan_ralan.quality, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_quality, \n"
                        + "	penilaian_awal_keperawatan_ralan.lokasi, \n"
                        + "	penilaian_awal_keperawatan_ralan.menyebar, \n"
                        + "	penilaian_awal_keperawatan_ralan.skala_nyeri, \n"
                        + "	penilaian_awal_keperawatan_ralan.durasi, \n"
                        + "	penilaian_awal_keperawatan_ralan.nyeri_hilang, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_menyebar, \n"
                        + "	penilaian_awal_keperawatan_ralan.pada_dokter, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_dokter, \n"
                        + "	penilaian_awal_keperawatan_ralan.rencana, \n"
                        + "	penilaian_awal_keperawatan_ralan.nip, \n"
                        + "	petugas.nama, \n"
                        + "	penilaian_awal_keperawatan_ralan.budaya, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_budaya, \n"
                        + "	penilaian_awal_keperawatan_ralan.sg3, \n"
                        + "	penilaian_awal_keperawatan_ralan.nilai3, \n"
                        + "	penilaian_awal_keperawatan_ralan.sg4, \n"
                        + "	penilaian_awal_keperawatan_ralan.nilai4, \n"
                        + "	penilaian_awal_keperawatan_ralan.cacatfisik, \n"
                        + "	penilaian_awal_keperawatan_ralan.sg11, \n"
                        + "	penilaian_awal_keperawatan_ralan.nilai11, \n"
                        + "	penilaian_awal_keperawatan_ralan.sg22, \n"
                        + "	penilaian_awal_keperawatan_ralan.nilai22, \n"
                        + "	penilaian_awal_keperawatan_ralan.sg33, \n"
                        + "	penilaian_awal_keperawatan_ralan.nilai33, \n"
                        + "	penilaian_awal_keperawatan_ralan.total_hasil1, \n"
                        + "	penilaian_awal_keperawatan_ralan.cries_tangisan, \n"
                        + "	penilaian_awal_keperawatan_ralan.cries_keb_oksien, \n"
                        + "	penilaian_awal_keperawatan_ralan.cries_ttv, \n"
                        + "	penilaian_awal_keperawatan_ralan.cries_ekspresiwajah, \n"
                        + "	penilaian_awal_keperawatan_ralan.cries_susahtidur, \n"
                        + "	penilaian_awal_keperawatan_ralan.flascs_ekspresi, \n"
                        + "	penilaian_awal_keperawatan_ralan.flacs_kaki, \n"
                        + "	penilaian_awal_keperawatan_ralan.flacs_aktifitas, \n"
                        + "	penilaian_awal_keperawatan_ralan.flacs_tangisan, \n"
                        + "	penilaian_awal_keperawatan_ralan.flacs_emosional, \n"
                        + "	penilaian_awal_keperawatan_ralan.cpot_ekspresiwajah, \n"
                        + "	penilaian_awal_keperawatan_ralan.cpot_gerakantubuh, \n"
                        + "	penilaian_awal_keperawatan_ralan.cpot_kesesuaian, \n"
                        + "	penilaian_awal_keperawatan_ralan.cpot_ketegangan, \n"
                        + "	penilaian_awal_keperawatan_ralan.diagkhususgizi, \n"
                        + "	penilaian_awal_keperawatan_ralan.diagkhususgizi1, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_cries_tangisan, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_cries_keb_oksien, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_cries_ttv, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_cries_ekspresiwajah, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_cries_susahtidur, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_flacs_kaki, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_flascs_ekspresi, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_flacs_aktifitas, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_flacs_tangisan, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_flacs_emosional, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_cpot_ekspresiwajah, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_cpot_gerakantubuh, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_cpot_kesesuaian, \n"
                        + "	penilaian_awal_keperawatan_ralan.ket_cpot_ketegangan, \n"
                        + "	penilaian_awal_keperawatan_ralan.klinik, \n"
                        + "	penilaian_awal_keperawatan_ralan.cmbAlergi, \n"
                        + "	penilaian_awal_keperawatan_ralan.mengganggu, \n"
                        + "	penilaian_awal_keperawatan_ralan.frekuensi, \n"
                        + "	penilaian_awal_keperawatan_ralan.tindakanskrining, \n"
                        + "	penilaian_awal_keperawatan_ralan.spo, \n"
                        + "	penilaian_awal_keperawatan_ralan.ekprsemosi, \n"
                        + "	penilaian_awal_keperawatan_ralan.kontakmata, \n"
                        + "	penilaian_awal_keperawatan_ralan.taatberibadah, \n"
                        + "	penilaian_awal_keperawatan_ralan.bantuandirumah, \n"
                        + "	penilaian_awal_keperawatan_ralan.kelahiran, \n"
                        + "	penilaian_awal_keperawatan_ralan.ketKelahiran, \n"
                        + "	penilaian_awal_keperawatan_ralan.BBlahir, \n"
                        + "	penilaian_awal_keperawatan_ralan.PBL, \n"
                        + "	penilaian_awal_keperawatan_ralan.vaksin, \n"
                        + "	penilaian_awal_keperawatan_ralan.rokok, \n"
                        + "	penilaian_awal_keperawatan_ralan.alkohol, \n"
                        + "	penilaian_awal_keperawatan_ralan.obatTidur, \n"
                        + "	penilaian_awal_keperawatan_ralan.olahraga, \n"
                        + "	penilaian_awal_keperawatan_ralan.kebiasaan, \n"
                        + "	penilaian_awal_keperawatan_ralan.usiabayi, penilaian_awal_keperawatan_ralan.pada_dokter_dewasa\n"
                        + " FROM\n"
                        + "	reg_periksa\n"
                        + "	INNER JOIN\n"
                        + "	pasien\n"
                        + "	ON \n"
                        + "		reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n"
                        + "	INNER JOIN\n"
                        + "	penilaian_awal_keperawatan_ralan\n"
                        + "	ON \n"
                        + "		reg_periksa.no_rawat = penilaian_awal_keperawatan_ralan.no_rawat\n"
                        + "	INNER JOIN\n"
                        + "	petugas\n"
                        + "	ON \n"
                        + "		penilaian_awal_keperawatan_ralan.nip = petugas.nip\n"
                        + "	INNER JOIN\n"
                        + "	bahasa_pasien\n"
                        + "	ON \n"
                        + "		bahasa_pasien.id = pasien.bahasa_pasien\n"
                        + "	INNER JOIN\n"
                        + "	cacat_fisik\n"
                        + "	ON \n"
                        + "		cacat_fisik.id = pasien.cacat_fisik\n"
                        + "WHERE\n"
                        + "	reg_periksa.no_rawat='" + norawat + "'",
                param, norawat, norekammedis, "AWALKEPRALAN");

    }

    private void cetakEdukasiRalan(String norawat) {

    }

    // private void cetakPengkajianKepGeriatri(String norawat) {
    // RMPenilaianKeperawatanPasienGeriatri geriatri = new
    // RMPenilaianKeperawatanPasienGeriatri(null, false);
    // String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from
    // reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
    // geriatri.GeriatriPdf(norawat, norekammedis);
    // }
    private void cetakCPPTRalan(String norawat) {
        String nomorrawat = norawat.replaceAll("/", "");
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        // param.put("pemberiasuhan", Sequel.cariIsi("select petugas.nama from petugas
        // join pemeriksaan_ralan on petugas.nip=dpjp_ranap.kd_dokter join
        // pemeriksaan_ranap on dpjp_ranap.no_rawat=pemeriksaan_ranap.no_rawat where
        // dpjp_ranap.statusdpjp='Utama' and pemeriksaan_ranap.no_rawat=?",
        // TNoRw.getText()));
        // param.put("spesialisralan", Sequel.cariIsi("select concat('Dokter Spesialis
        // ',spesialis.nm_sps) from dokter join spesialis on
        // dokter.kd_sps=spesialis.kd_sps join dpjp_ranap on
        // dokter.kd_dokter=dpjp_ranap.kd_dokter join pemeriksaan_ranap on
        // dpjp_ranap.no_rawat=pemeriksaan_ranap.no_rawat where
        // dpjp_ranap.statusdpjp='Utama' and pemeriksaan_ranap.no_rawat=?",
        // TNoRw.getText()));
        String pas = " and reg_periksa.no_rkm_medis like '%" + norekammedis + "%' ";

        String tgl = " pemeriksaan_ralan.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "")
                + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
        Valid.ReportKompilasiBerkas("rptJalanPemeriksaan.jasper", "report", "::[ Data Pemeriksaan Rawat Jalan ]::",
                "select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y')as tgl_lahir, pasien.no_ktp as noktp,"
                        + "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,dokter.nm_dokter, "
                        + "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, "
                        + "pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.nyeri,pemeriksaan_ralan.keluhan, "
                        + "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.instruksi,pemeriksaan_ralan.alergi,pemeriksaan_ralan.imun_ke,"
                        + "pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,poliklinik.nm_poli,pegawai.jbtn,pegawai.nama, dokter.kd_dokter as nipdokter from dokter inner join pasien inner join poliklinik inner join reg_periksa inner join pemeriksaan_ralan inner join pegawai "
                        + "on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_poli=poliklinik.kd_poli and pegawai.nik=pemeriksaan_ralan.nip and reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "
                        + tgl + "and pemeriksaan_ralan.no_rawat like '%" + norawat + "%'"
                        + "order by pemeriksaan_ralan.no_rawat and pemeriksaan_ralan.jam_rawat desc",
                param, norawat, norekammedis, "CPPT");
    }

//    private void cetakAwalMedisGizi(String norawat) {
//        RMAwalMedisGiziKlinis awalgizi = new RMAwalMedisGiziKlinis(null, false);
//        String norekammedis = Sequel.cariIsi(
//                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        awalgizi.AwalMedisGiziPdf(norawat, norekammedis);
//    } //methodnya belum ada

    private void cetakPemeriksaanLab(String norawat) {
        DlgCariPeriksaLab hasillab = new DlgCariPeriksaLab(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        hasillab.HasilLabPDFRME(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakPermintaanRad(String norawat) {
        DlgCariPeriksaRadiologi hasilrad = new DlgCariPeriksaRadiologi(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        hasilrad.RadPdf(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakSuratSakit(String norawat) {
        SuratSakit suratsakit = new SuratSakit(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        suratsakit.SuratSakitPdf(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakSuratKontrol(String norawat) {
        SuratKontrol suratkontrol = new SuratKontrol(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        suratkontrol.SuratKontrolPdf(norawat, norekammedis); //methodnya belum ada
    }

    private void cetakSuratRujukBalik(String norawat) {
        BPJSRujukanKeluar rujukkeluar = new BPJSRujukanKeluar(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        rujukkeluar.CetakRujukKeluarRME(norawat, norekammedis); //methodnya belum ada
    }

//    private void cetakPermintaanPelayananAmbulance(String norawat) {
//        DlgPermintaanAmbulance permAmulance = new DlgPermintaanAmbulance(null, false);
//        String norekammedis = Sequel.cariIsi(
//                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        permAmulance.PermintaanAmbulancePdf(norawat, norekammedis);
//    } //methodnya belum ada

//    private void cetakFormulirTambahan(String norawat) {
//        DlgFormulirPemeriksaan pemtambahan = new DlgFormulirPemeriksaan(null, false);
//        String norekammedis = Sequel.cariIsi(
//                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        pemtambahan.MnPemeriksaanTambahanRMEPDF(norawat, norekammedis);
//    } //methodnya belum ada

    private void cetakAwalMedisMata(String norawat) {
        RMPenilaianAwalMedisRalanMata awalMata = new RMPenilaianAwalMedisRalanMata(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        awalMata.AwalMedisMataPdf(norawat, norekammedis); //methodnya belum ada
    }

//    private void cetakLayananKFR(String norawat) {
//        RMLayananKFR layanankfr = new RMLayananKFR(null, false);
//        String norekammedis = Sequel.cariIsi(
//                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        layanankfr.LayananKFRPdf(norawat, norekammedis);
//    } //methodnya belum ada

    private void cetakUjiFungsiKFR(String norawat) {
        RMUjiFungsiKFR ujikfr = new RMUjiFungsiKFR(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        ujikfr.UjiKFRPdf(norawat, norekammedis); //methodnya belum ada
    }

//    private void cetakProgramKFR(String norawat) {
//        RMProgramKFR programkfr = new RMProgramKFR(null, false);
//        String norekammedis = Sequel.cariIsi(
//                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        programkfr.ProgramKFRPdf(norawat, norekammedis);
//    } //methodnya belum ada

    private void cetakAwalMedisPsikiatri(String norawat) {
        RMPenilaianAwalMedisRalanPsikiatrik awalpsikiatri = new RMPenilaianAwalMedisRalanPsikiatrik(null, false);
        String norekammedis = Sequel.cariIsi(
                "select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        awalpsikiatri.AwalPsikiatriPdf(norawat, norekammedis); //methodnya belum ada
    }

    public void setNoRm(String norwt, Date tgl1, Date tgl2) {

        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
    }

    public void isCek() {
        tampil();
        lblCoderNIK.setText(
                Sequel.cariIsi("select inacbg_coder_nik.no_ik from inacbg_coder_nik where inacbg_coder_nik.nik='"
                        + akses.getkode() + "'"));
    }

    public void tampilicareBPJS() {
        String url = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + prop.getProperty("PORTWEB") + "/"
                + prop.getProperty("HYBRIDWEB") + "/"
                + "inacbg/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&page=DetailKirimDesktop&codernik="
                + lblCoderNIK.getText() + "&norawat=" + lblNoRawat.getText();//"&carabayar=BPJ";
        // System.out.println(url);
        Platform.runLater(() -> {
            WebView view = new WebView();
            engine = view.getEngine();
            engine.setJavaScriptEnabled(true);
            engine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
                @Override
                public WebEngine call(PopupFeatures p) {
                    Stage stage = new Stage(StageStyle.TRANSPARENT);
                    return view.getEngine();
                }
            });
            engine.getLoadWorker().exceptionProperty()
                    .addListener((ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) -> {
                        if (engine.getLoadWorker().getState() == FAILED) {
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(PanelContentIcareBPJS,
                                        (value != null)
                                                ? engine.getLocation() + "\n" + value.getMessage()
                                                : engine.getLocation() + "\nUnexpected Catatan.",
                                        "Loading Catatan...",
                                        JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    });

            jfxPanelicare.setScene(new Scene(view));

            try {
                engine.load(url);
                //System.out.println(url);
            } catch (Exception exception) {
                engine.load(url);
            }
        });

        PanelContentIcareBPJS.add(jfxPanelicare, BorderLayout.CENTER);

    }

    public void tampilInvoice() {
        String url = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + prop.getProperty("PORTWEB") + "/"
                + prop.getProperty("HYBRIDWEB") + "/" + "berkasrawat/pages/billingtampil.php?norawat="
                + lblNoRawat.getText() + "";
        // System.out.println(url);
        Platform.runLater(() -> {
            // System.out.println("panel invoice dipanggil : " + url);
            WebView view = new WebView();
            engine = view.getEngine();
            engine.setJavaScriptEnabled(true);
            engine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
                @Override
                public WebEngine call(PopupFeatures p) {
                    Stage stage = new Stage(StageStyle.TRANSPARENT);
                    return view.getEngine();
                }
            });
            engine.getLoadWorker().exceptionProperty()
                    .addListener((ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) -> {
                        if (engine.getLoadWorker().getState() == FAILED) {
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(panelInvoices,
                                        (value != null)
                                                ? engine.getLocation() + "\n" + value.getMessage()
                                                : engine.getLocation() + "\nUnexpected Catatan.",
                                        "Loading Catatan...",
                                        JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    });

            jfxinvoices.setScene(new Scene(view));

            try {
                engine.load(url);
                // System.out.println(alamat);
            } catch (Exception exception) {
                engine.load(url);
            }
        });

        panelInvoices.add(jfxinvoices, BorderLayout.CENTER);

    }

    private void gabung(String norawat) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Properties systemProp = System.getProperties();
        String currentDir = systemProp.getProperty("user.dir");
        String folderPath = currentDir + "/hasilkompilasiklaim/"; // Replace this with the actual folder path
        String outputPath = currentDir + "/hasilkompilasiklaim/HASILGABUNG" + norawat.replaceAll("/", "") + ".pdf"; // Replace
                                                                                                                    // this
                                                                                                                    // with
                                                                                                                    // the
                                                                                                                    // desired
                                                                                                                    // output
                                                                                                                    // path
        String nosuratkontrol = Sequel.cariIsi(
                "select bridging_sep.noskdp from bridging_sep where bridging_sep.no_sep='" + lblNoSEP.getText() + "'");
        // bersihkan dulu foldernya
        deleteFilesInFolder(folderPath);
        if (Sequel.cariInteger(
                "select count(inacbg_data_printclaim.no_sep) from inacbg_data_printclaim where inacbg_data_printclaim.no_sep='"
                        + lblNoSEP.getText() + "'") > 0) {

            Base64ToPDFForGabung(lblNoSEP.getText(), lblNoRawat.getText());
        }
        if (Sequel.cariInteger(
                "select count(inacbg_data_printinvoice.no_sep) from inacbg_data_printinvoice where inacbg_data_printinvoice.no_sep='"
                        + lblNoSEP.getText() + "'") > 0) {

            Base64ToPDFForGabungInVoice(lblNoSEP.getText(), lblNoRawat.getText());
        }

        if (Sequel.cariInteger(
                "select count(no_rawat) from bridging_sep where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            btnSEPResumePDF();
        }
        if (Sequel.cariInteger(
                "select count(no_rawat) from resume_pasien_ranap where no_rawat='" + lblNoRawat.getText() + "'") > 0) {

        }

        if (Sequel.cariInteger(
                "select count(no_rawat) from penilaian_medis_igd where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            btnAwalMedisIGDPDF();
        }

        if (Sequel.cariInteger(
                "select count(no_rawat) from periksa_lab where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            DlgCariPeriksaLab hasillab = new DlgCariPeriksaLab(null, false);
            hasillab.HasilLabPDFKlaimBPJSKompilasi(lblNoRawat.getText(), lblNoRM.getText());
        }
        if (Sequel.cariInteger(
                "select count(no_rawat) from periksa_radiologi where no_rawat='" + lblNoRawat.getText() + "'") > 0) {
            DlgCariPeriksaRadiologi hasilrad = new DlgCariPeriksaRadiologi(null, false);
            hasilrad.RadPdfKlaimBPJSKompilasi(lblNoRawat.getText(), lblNoRM.getText());
        }

        if (Sequel.cariInteger("select count(no_rawat) from bridging_surat_pri_bpjs where no_rawat='"
                + lblNoRawat.getText() + "'") > 0) {
            BPJSSPRI spri = new BPJSSPRI(null, false);
            spri.CetakSPRIKlaimPDF(lblNoSEP.getText(), lblNoRawat.getText());
        }

        String kodedokter = Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat='"
                + lblNoRawat.getText() + "'");
        String kodepoli = Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat='"
                + lblNoRawat.getText() + "'");
        String norawatpilih = Sequel
                .cariIsi("select reg_periksa.no_rawat from reg_periksa where reg_periksa.kd_dokter='" + kodedokter
                        + "' and reg_periksa.kd_poli='" + kodepoli + "' and reg_periksa.no_rkm_medis='"
                        + lblNoRM.getText() + "' and reg_periksa.no_rawat != '" + lblNoRawat.getText()
                        + "' order by reg_periksa.no_rawat desc limit 1");

        if (ChkSuratKontrol.isSelected() == true) {
            if (Sequel.cariInteger("select count(no_surat) from bridging_surat_kontrol_bpjs where no_surat='"
                    + nosuratkontrol + "'") > 0) {
                BPJSSuratKontrol suratkontrol = new BPJSSuratKontrol(null, false);
                suratkontrol.SuratKontrolKlaimPDF(lblNoRawat.getText(), lblNoSEP.getText(), lblNoRM.getText()); //methodnya belum ada
            } else if (Sequel.cariInteger(
                    "select count(no_rawat) from skdp_bpjs where no_rawat='" + norawatpilih + "'") > 0) {
                SuratKontrol suratkontrol = new SuratKontrol(null, false);
                suratkontrol.SuratKontrolKlaimPDF(norawatpilih, lblNoRM.getText());
            }
        }

//        if (Sequel.cariInteger(
//                "select count(form_pemeriksaan.no_rawat) from form_pemeriksaan where form_pemeriksaan.no_rawat='"
//                        + lblNoRawat.getText() + "'") > 0) {
//            DlgFormulirPemeriksaan pemtambahan = new DlgFormulirPemeriksaan(null, false);
//            pemtambahan.MnPemeriksaanTambahanRMEPDFKlaim(lblNoRawat.getText(), lblNoRM.getText());
//        }

        // tag merge
        mergePDFsFromFolder(folderPath, outputPath, norawat.replaceAll("/", ""));
        Sequel.queryu("truncate table inacbg_data_printclaim");
        Sequel.queryu("truncate table inacbg_data_printinvoice");
        // emptTeks();
        this.setCursor(Cursor.getDefaultCursor());

    }

    public static void mergePDFsFromFolder(String folderPath, String outputPath, String nomorrawat) {

        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        URL url;
        if (files != null) {
            Arrays.sort(files, (file1, file2) -> file1.getName().compareTo(file2.getName()));
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".pdf") && file.getName().contains(nomorrawat)) {
                    try {
                        pdfMerger.addSource(file);
                    } catch (IOException e) {
                        System.err.println("Error adding file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }

            pdfMerger.setDestinationFileName(outputPath);

            try {
                pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
                System.out.println("PDFs merged successfully!");
                File hasiljadi = new File(outputPath);
                Desktop.getDesktop().open(hasiljadi);
            } catch (IOException e) {
                System.err.println("Error merging PDFs.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No PDF files found in the folder: " + folderPath);
        }
    }

    public static void deleteFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.delete()) {
                        System.out.println("Deleted file: " + file.getName());
                    } else {
                        System.out.println("Failed to delete file: " + file.getName());
                    }
                }
            }
        } else {
            System.out.println("No files found in the folder: " + folderPath);
        }
    }

    private void btnSEPResumePDF() {
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {
            if (lblStatusRawat.getText().equals("Ralan")) {
                String nomorsep = Sequel
                        .cariIsi("select bridging_sep.no_sep from bridging_sep where bridging_sep.no_rawat='"
                                + lblNoRawat.getText() + "' and bridging_sep.jnspelayanan='2'");
                String jnspelayanan = Sequel
                        .cariIsi("select bridging_sep.jnspelayanan from bridging_sep where bridging_sep.no_rawat='"
                                + nomorsep + "'");
                String kodedokterregis = Sequel
                        .cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat='"
                                + lblNoRawat.getText() + "'");
                String namadokterregis = Sequel.cariIsi(
                        "select dokter.nm_dokter from dokter where dokter.kd_dokter='" + kodedokterregis + "'");
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            
            //custom tambahan
            String sep = Sequel.cariIsi("select ifnull(no_sep,'-') from bridging_sep where jnspelayanan='2' and no_rawat=?",lblNoRawat.getText());
            param.put("suhu_tubuh",Sequel.cariIsi("select suhu_tubuh from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("tensi",Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("nadi",Sequel.cariIsi("select nadi from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("respirasi",Sequel.cariIsi("select respirasi from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("tinggi",Sequel.cariIsi("select tinggi from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("berat",Sequel.cariIsi("select berat from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
            param.put("gcs",Sequel.cariIsi("select gcs from pemeriksaan_ralan where no_rawat=?",lblNoRawat.getText()));
//            param.put("no_sep",sep);
            
            variabel="Atas Persetujuan Dokter";
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",lblNoRawat.getText())>0){
                variabel="MRS";
            }
            if(Sequel.cariInteger("select count(rujuk.no_rawat) from rujuk where rujuk.no_rawat=?",lblNoRawat.getText())>0){
                variabel="Dirujuk";
            }
            param.put("carapulang",variabel);
            variabel=Sequel.cariIsi("select bridging_sep.tujuankunjungan from bridging_sep where bridging_sep.no_rawat=?",lblNoRawat.getText());
            param.put("tujuankunjungan","- "+(variabel.equals("0")?"Konsultasi dokter(pertama)":"Kunjungan Kontrol(ulangan)"));
            variabel=Sequel.cariIsi("select bridging_sep.flagprosedur from bridging_sep where bridging_sep.no_rawat=?",lblNoRawat.getText());
            param.put("flagprosedur",variabel.replaceAll("0","- Prosedur Tidak Berkelanjutan").replaceAll("1","- Prosedur dan Terapi Berkelanjutan"));
            
            tanggal="";
            if(Sequel.cariIsi("select reg_periksa.status_lanjut from reg_periksa where reg_periksa.no_rawat=?",lblNoRawat.getText()).equals("Ralan")){
                param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?",lblNoRawat.getText()));
                tanggal=Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat=?",lblNoRawat.getText());
                param.put("tanggalkeluar",tanggal);
            }else{
                param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ",lblNoRawat.getText()));
                tanggal=Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ",lblNoRawat.getText());
                param.put("tanggalkeluar",tanggal);
            }
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kodedokterregis);
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namadokterregis+"\nID "+(finger.equals("")?kodedokterregis:finger)+"\n"+tanggal); 
            param.put("fingerpasien",Sequel.cariIsi("select sha1(sidikjaripasien.sidikjari) from sidikjaripasien where sidikjaripasien.no_rkm_medis=?",lblNoRM.getText()));

            try {
                //custom
                ps=koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"+
                    "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"+
                    "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                    "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan') as jnspelayanan,bridging_sep.catatan,bridging_sep.diagawal,"+
                    "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"+
                    "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')) as klsrawat,"+
                    "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "+
                    "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,"+
                    "bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.notelep,"+
                    "bridging_sep.tujuankunjungan,bridging_sep.flagprosedur,bridging_sep.klsnaik,bridging_sep.pembiayaan,"+
                    "bridging_sep.nmdpdjp,bridging_sep.lakalantas from bridging_sep where no_sep=?");
                try {
                    ps.setString(1,sep);
                    rs=ps.executeQuery();
                    if(rs.next()){
                        param.put("no_sep",rs.getString("no_sep"));
                        param.put("norawat",rs.getString("no_rawat"));
                        param.put("nomr",rs.getString("nomr"));
                        param.put("tglsep",rs.getDate("tglsep"));
                        param.put("nama_pasien",rs.getString("nama_pasien"));
                        param.put("notelep",rs.getString("notelep"));
                        param.put("nmpolitujuan",rs.getString("nmpolitujuan"));
                        param.put("nmdpdjp",rs.getString("nmdpdjp"));
                        param.put("nmppkrujukan",rs.getString("nmppkrujukan"));
                        param.put("nmdiagnosaawal",rs.getString("nmdiagnosaawal"));
                        param.put("catatan",rs.getString("catatan"));
                        param.put("peserta",rs.getString("peserta"));
                        param.put("no_kartu",rs.getString("no_kartu"));
                        param.put("klsnaik",rs.getString("klsnaik"));
                        param.put("klsrawat",rs.getString("klsrawat"));
                        param.put("lakalantas",rs.getString("lakalantas"));
                        param.put("klsrawat",rs.getString("klsrawat"));
                        param.put("jnspelayanan",rs.getString("jnspelayanan"));
                        param.put("tanggal_lahir",rs.getDate("tanggal_lahir"));
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
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
            
            param.put("logo",Sequel.cariGambar("select gambar.bpjs from gambar"));
            param.put("prb",Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?",sep));
            param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",lblNoRawat.getText()));
            
            Valid.MyReportPDFKlaim("rptSBPK4.jasper","report","1SEP",param,"hasilkompilasiklaim",lblNoRawat.getText().replaceAll("/", ""));
                
//                Map<String, Object> param = new HashMap<>();
//                param.put("namars", akses.getnamars());
//                param.put("alamatrs", akses.getalamatrs());
//                param.put("kotars", akses.getkabupatenrs());
//                param.put("propinsirs", akses.getpropinsirs());
//                param.put("kontakrs", akses.getkontakrs());
//                param.put("logo", Sequel.cariGambar("select gambar.bpjs from gambar"));
//                param.put("prb", Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?", nomorsep));
//                if (Sequel.cariInteger(
//                        "select count(pemeriksaan_ralan.no_rawat) from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='"
//                                + lblNoRawat.getText() + "' and pemeriksaan_ralan.nip='" + kodedokterregis + "'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select pemeriksaan_ralan.penilaian from pemeriksaan_ralan where pemeriksaan_ralan.nip='"
//                                    + kodedokterregis + "' and pemeriksaan_ralan.no_rawat='" + lblNoRawat.getText()
//                                    + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select pemeriksaan_ralan.instruksi from pemeriksaan_ralan where pemeriksaan_ralan.nip='"
//                                    + kodedokterregis + "' and pemeriksaan_ralan.no_rawat='" + lblNoRawat.getText()
//                                    + "'"));
//                } else if (Sequel.cariInteger(
//                        "select count(penilaian_medis_ralan.no_rawat) from penilaian_medis_ralan where penilaian_medis_ralan.no_rawat='"
//                                + lblNoRawat.getText() + "' and penilaian_medis_ralan.kd_dokter='" + kodedokterregis
//                                + "'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select penilaian_medis_ralan.diagnosis from penilaian_medis_ralan where penilaian_medis_ralan.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan.kd_dokter='" + kodedokterregis
//                                    + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select penilaian_medis_ralan.konsulrujuk from penilaian_medis_ralan where penilaian_medis_ralan.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan.kd_dokter='" + kodedokterregis
//                                    + "'"));
//                } else if (Sequel.cariInteger(
//                        "select count(penilaian_medis_ralan_tht.no_rawat) from penilaian_medis_ralan_tht where penilaian_medis_ralan_tht.no_rawat='"
//                                + lblNoRawat.getText() + "' and penilaian_medis_ralan_tht.kd_dokter='" + kodedokterregis
//                                + "'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select penilaian_medis_ralan_tht.diagnosis from penilaian_medis_ralan_tht where penilaian_medis_ralan_tht.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan_tht.kd_dokter='"
//                                    + kodedokterregis + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select penilaian_medis_ralan_tht.konsulrujuk from penilaian_medis_ralan_tht where penilaian_medis_ralan_tht.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan_tht.kd_dokter='"
//                                    + kodedokterregis + "'"));
//                } else if (Sequel.cariInteger(
//                        "select count(penilaian_medis_ralan_psikiatrik.no_rawat) from penilaian_medis_ralan_psikiatrik where penilaian_medis_ralan_psikiatrik.no_rawat='"
//                                + lblNoRawat.getText() + "' and penilaian_medis_ralan_psikiatrik.kd_dokter='"
//                                + kodedokterregis + "'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select penilaian_medis_ralan_psikiatrik.diagnosis1 from penilaian_medis_ralan_psikiatrik where penilaian_medis_ralan_psikiatrik.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan_psikiatrik.kd_dokter='"
//                                    + kodedokterregis + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select penilaian_medis_ralan_psikiatrik.tata from penilaian_medis_ralan_psikiatrik where penilaian_medis_ralan_psikiatrik.no_rawat='"
//                                    + lblNoRawat.getText() + "' and penilaian_medis_ralan_psikiatrik.kd_dokter='"
//                                    + kodedokterregis + "'"));
//                } else if (Sequel.cariInteger(
//                        "select count(pemeriksaan_ralan.no_rawat) from pemeriksaan_ralan inner join reg_periksa where pemeriksaan_ralan.no_rawat='"
//                                + lblNoRawat.getText() + "' and reg_periksa.kd_poli = 'U0009'") > 0) {
//                    param.put("diagnosautama", Sequel.cariIsi(
//                            "select pemeriksaan_ralan.penilaian from pemeriksaan_ralan where  pemeriksaan_ralan.no_rawat='"
//                                    + lblNoRawat.getText() + "'"));
//                    param.put("uraian", Sequel.cariIsi(
//                            "select pemeriksaan_ralan.instruksi from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='"
//                                    + lblNoRawat.getText() + "'"));
//                }
//
//                // param.put("prosedurutama", Sequel.cariIsi("select pemeriksaan_ralan.rtl from
//                // pemeriksaan_ralan where pemeriksaan_ralan.nip='" + kodedokterregis + "' and
//                // pemeriksaan_ralan.no_rawat='" + lblNoRawat.getText() + "'"));
//                param.put("finger",
//                        "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs()
//                                + "\nDitandatangani secara elektronik oleh " + namadokterregis + "\nID "
//                                + kodedokterregis + "\n");
//                String tindakandr = "";
//                try {
//                    psambiltindakan = koneksi.prepareStatement(
//                            "SELECT\n"
//                                    + "jns_perawatan.nm_perawatan\n"
//                                    + "FROM\n"
//                                    + "	rawat_jl_dr\n"
//                                    + "	INNER JOIN jns_perawatan ON rawat_jl_dr.kd_jenis_prw = jns_perawatan.kd_jenis_prw \n"
//                                    + "WHERE\n"
//                                    + "	rawat_jl_dr.no_rawat=? order by rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat ");
//                    try {
//                        psambiltindakan.setString(1, lblNoRawat.getText());
//                        rsambiltindakan = psambiltindakan.executeQuery();
//                        while (rsambiltindakan.next()) {
//                            tindakandr = tindakandr + rsambiltindakan.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambiltindakan != null) {
//                            rsambiltindakan.close();
//                        }
//                        if (psambiltindakan != null) {
//                            psambiltindakan.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                try {
//                    psambiltindakan = koneksi.prepareStatement(
//                            "SELECT\n"
//                                    + "jns_perawatan.nm_perawatan\n"
//                                    + "FROM\n"
//                                    + "	rawat_jl_drpr\n"
//                                    + "	INNER JOIN jns_perawatan ON rawat_jl_drpr.kd_jenis_prw = jns_perawatan.kd_jenis_prw \n"
//                                    + "WHERE\n"
//                                    + "	rawat_jl_drpr.no_rawat=? order by rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat ");
//                    try {
//                        psambiltindakan.setString(1, lblNoRawat.getText());
//                        rsambiltindakan = psambiltindakan.executeQuery();
//                        while (rsambiltindakan.next()) {
//                            tindakandr = tindakandr + rsambiltindakan.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambiltindakan != null) {
//                            rsambiltindakan.close();
//                        }
//                        if (psambiltindakan != null) {
//                            psambiltindakan.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                try {
//                    psambiltindakan = koneksi.prepareStatement(
//                            "SELECT\n"
//                                    + "jns_perawatan.nm_perawatan\n"
//                                    + "FROM\n"
//                                    + "	rawat_jl_pr\n"
//                                    + "	INNER JOIN jns_perawatan ON rawat_jl_pr.kd_jenis_prw = jns_perawatan.kd_jenis_prw \n"
//                                    + "WHERE\n"
//                                    + "	rawat_jl_pr.no_rawat=? order by rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat ");
//                    try {
//                        psambiltindakan.setString(1, lblNoRawat.getText());
//                        rsambiltindakan = psambiltindakan.executeQuery();
//                        while (rsambiltindakan.next()) {
//                            tindakandr = tindakandr + rsambiltindakan.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambiltindakan != null) {
//                            rsambiltindakan.close();
//                        }
//                        if (psambiltindakan != null) {
//                            psambiltindakan.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                try {
//                    psambillab = koneksi.prepareStatement(
//                            "select jns_perawatan_lab.nm_perawatan "
//                                    + " from periksa_lab inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw where "
//                                    + " periksa_lab.no_rawat=? ");
//                    try {
//                        psambillab.setString(1, lblNoRawat.getText());
//                        rsambillab = psambillab.executeQuery();
//                        while (rsambillab.next()) {
//                            tindakandr = tindakandr + rsambillab.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambillab != null) {
//                            rsambillab.close();
//                        }
//                        if (psambillab != null) {
//                            psambillab.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                try {
//                    psambilrad = koneksi.prepareStatement(
//                            "select jns_perawatan_radiologi.nm_perawatan "
//                                    + " from periksa_radiologi inner join jns_perawatan_radiologi on jns_perawatan_radiologi.kd_jenis_prw=periksa_radiologi.kd_jenis_prw where "
//                                    + " periksa_radiologi.no_rawat=?");
//                    try {
//                        psambilrad.setString(1, lblNoRawat.getText());
//                        rsambilrad = psambilrad.executeQuery();
//                        while (rsambilrad.next()) {
//                            tindakandr = tindakandr + rsambilrad.getString(1) + "\n";
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rsambilrad != null) {
//                            rsambilrad.close();
//                        }
//                        if (psambilrad != null) {
//                            psambilrad.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                param.put("prosedurutama", tindakandr);
//                param.put("dokterregis", namadokterregis);
//                param.put("parameter", nomorsep);
//                Valid.MyReportPDFKlaim("rptBridgingSEP.jasper", "report", "1SEP", param, "hasilkompilasiklaim",
//                        lblNoRawat.getText().replaceAll("/", ""));
//                this.setCursor(Cursor.getDefaultCursor());
            } else {
                String nomorsep = Sequel.cariIsi(
                        "select bridging_sep.no_sep from bridging_sep where bridging_sep.jnspelayanan='1' and bridging_sep.no_rawat='"
                                + lblNoRawat.getText() + "'");
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("prb", Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?", nomorsep));
                param.put("logo", Sequel.cariGambar("select gambar.bpjs from gambar"));
                param.put("parameter", nomorsep);
                Valid.MyReportPDFKlaim("rptBridgingSEP.jasper", "report", "1SEP", param, "hasilkompilasiklaim",
                        lblNoRawat.getText().replaceAll("/", ""));
                this.setCursor(Cursor.getDefaultCursor());
            }

        }
    }

    private void btnAwalMedisIGDPDF() {
        if (lblNoRawat.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Maaf, silahkan pilih pasien terlebih dahulu");
        } else {

            String kodedokterregis = Sequel
                    .cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat='"
                            + lblNoRawat.getText() + "'");
            String namadokterregis = Sequel
                    .cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter='" + kodedokterregis + "'");
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("lokalis", Sequel.cariGambar("select lokalis from gambar"));
            finger = Sequel.cariIsi(
                    "select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",
                    kodedokterregis);
            param.put("finger",
                    "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs()
                            + "\nDitandatangani secara elektronik oleh " + namadokterregis + "\nID "
                            + (finger.equals("") ? kodedokterregis : finger) + "\n");

            Valid.MyReportqrypdfKlaim("rptCetakPenilaianAwalMedisIGD.jasper", "report", "9AWMEDIGD",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_igd.tanggal,"
                            + "penilaian_medis_igd.kd_dokter,penilaian_medis_igd.anamnesis,penilaian_medis_igd.hubungan,penilaian_medis_igd.keluhan_utama,penilaian_medis_igd.rps,penilaian_medis_igd.rpk,penilaian_medis_igd.rpd,penilaian_medis_igd.rpo,penilaian_medis_igd.alergi,"
                            + "penilaian_medis_igd.keadaan,penilaian_medis_igd.gcs,penilaian_medis_igd.kesadaran,penilaian_medis_igd.td,penilaian_medis_igd.nadi,penilaian_medis_igd.rr,penilaian_medis_igd.suhu,penilaian_medis_igd.spo,penilaian_medis_igd.bb,penilaian_medis_igd.tb,"
                            + "penilaian_medis_igd.kepala,penilaian_medis_igd.mata,penilaian_medis_igd.mulut,penilaian_medis_igd.leher,penilaian_medis_igd.thoraks,penilaian_medis_igd.abdomen,penilaian_medis_igd.ekstremitas,penilaian_medis_igd.genital,penilaian_medis_igd.ket_fisik,"
                            + "penilaian_medis_igd.ket_lokalis,penilaian_medis_igd.ekg,penilaian_medis_igd.rad,penilaian_medis_igd.lab,penilaian_medis_igd.diagnosis,penilaian_medis_igd.tata,dokter.nm_dokter,penilaian_medis_igd.keadaan1,penilaian_medis_igd.kulit,penilaian_medis_igd.dada,penilaian_medis_igd.hidung,penilaian_medis_igd.telinga,penilaian_medis_igd.rencanaranap,penilaian_medis_igd.indikasiranap, penilaian_medis_igd.nyeri,penilaian_medis_igd.lasupdate "
                            + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "inner join penilaian_medis_igd on reg_periksa.no_rawat=penilaian_medis_igd.no_rawat "
                            + "inner join dokter on penilaian_medis_igd.kd_dokter=dokter.kd_dokter where penilaian_medis_igd.no_rawat='"
                            + lblNoRawat.getText() + "'",
                    param, "hasilkompilasiklaim", lblNoRawat.getText().replaceAll("/", ""));
        }
    }

    private void Base64ToPDF(String nosep) {

        String base64EncodedData = Sequel.cariIsi(
                "select inacbg_data_printclaim.filepdf from inacbg_data_printclaim where inacbg_data_printclaim.no_sep='"
                        + nosep + "'");
        Properties systemProp = System.getProperties();
        String currentDir = systemProp.getProperty("user.dir");
        String folderPath = currentDir + "/hasilkompilasiklaim/"; // Replace this with the actual folder path
        String outputFilePath = currentDir + "/hasilkompilasiklaim/0PDFNCC" + nosep.replaceAll("/", "") + ".pdf"; // Replace
                                                                                                                  // this
                                                                                                                  // with
                                                                                                                  // the
                                                                                                                  // desired
                                                                                                                  // output
                                                                                                                  // path
        try {
            // Decode the Base64 data
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedData);

            // Create a FileOutputStream to write the decoded data to the PDF file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(decodedBytes);
            }
            File f = new File(outputFilePath);
            Desktop.getDesktop().open(f);

            System.out.println("PDF file has been successfully decoded and saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Base64ToPDFInvoice(String nosep) {

        String base64EncodedData = Sequel.cariIsi(
                "select inacbg_data_printinvoice.filepdf from inacbg_data_printinvoice where inacbg_data_printinvoice.no_sep='"
                        + nosep + "'");
        Properties systemProp = System.getProperties();
        String currentDir = systemProp.getProperty("user.dir");
        String folderPath = currentDir + "/hasilkompilasiklaim/"; // Replace this with the actual folder path
        String outputFilePath = currentDir + "/hasilkompilasiklaim/9INVOICE" + nosep.replaceAll("/", "") + ".pdf"; // Replace
                                                                                                                   // this
                                                                                                                   // with
                                                                                                                   // the
                                                                                                                   // desired
                                                                                                                   // output
                                                                                                                   // path
        try {
            // Decode the Base64 data
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedData);

            // Create a FileOutputStream to write the decoded data to the PDF file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(decodedBytes);
            }
            File f = new File(outputFilePath);
            Desktop.getDesktop().open(f);

            System.out.println("PDF file has been successfully decoded and saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Base64ToPDFForGabung(String nosep, String norawat) {

        String base64EncodedData = Sequel.cariIsi(
                "select inacbg_data_printclaim.filepdf from inacbg_data_printclaim where inacbg_data_printclaim.no_sep='"
                        + nosep + "'");
        Properties systemProp = System.getProperties();
        String currentDir = systemProp.getProperty("user.dir");
        String folderPath = currentDir + "/hasilkompilasiklaim/"; // Replace this with the actual folder path
        String outputFilePath = currentDir + "/hasilkompilasiklaim/0PDFNCC" + norawat.replaceAll("/", "") + ".pdf"; // Replace
                                                                                                                    // this
                                                                                                                    // with
                                                                                                                    // the
                                                                                                                    // desired
                                                                                                                    // output
                                                                                                                    // path
        try {
            // Decode the Base64 data
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedData);

            // Create a FileOutputStream to write the decoded data to the PDF file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(decodedBytes);
            }
            // File f = new File(outputFilePath);
            // Desktop.getDesktop().open(f);

            System.out.println("PDF file has been successfully decoded and saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Base64ToPDFForGabungInVoice(String nosep, String norawat) {

        String base64EncodedData = Sequel.cariIsi(
                "select inacbg_data_printinvoice.filepdf from inacbg_data_printinvoice where inacbg_data_printinvoice.no_sep='"
                        + nosep + "'");
        Properties systemProp = System.getProperties();
        String currentDir = systemProp.getProperty("user.dir");
        String folderPath = currentDir + "/hasilkompilasiklaim/"; // Replace this with the actual folder path
        String outputFilePath = currentDir + "/hasilkompilasiklaim/9INVOICE" + norawat.replaceAll("/", "") + ".pdf"; // Replace
                                                                                                                     // this
                                                                                                                     // with
                                                                                                                     // the
                                                                                                                     // desired
                                                                                                                     // output
                                                                                                                     // path
        try {
            // Decode the Base64 data
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedData);

            // Create a FileOutputStream to write the decoded data to the PDF file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(decodedBytes);
            }
            // File f = new File(outputFilePath);
            // Desktop.getDesktop().open(f);

            System.out.println("PDF file has been successfully decoded and saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

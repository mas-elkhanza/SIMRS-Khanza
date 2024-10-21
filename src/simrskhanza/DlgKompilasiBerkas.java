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
//import rekammedis.RMUjiFungsiKFR;
import rekammedis.RMDataResumePasien;
import surat.SuratKontrol;
import surat.SuratSakit;
import setting.DlgCariJamDiet;

/**
 *
 * @author dosen
 */
public class DlgKompilasiBerkas extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2;
    public DlgCariCaraBayar penjab = new DlgCariCaraBayar(null, false);
    private ResultSet rs, rs2;
    private int i = 0, pilih = 0, GD001int = 0, GD002int = 0, GD003int = 0, GD004int = 0, GD005int = 0, GD006int = 0, GD007int = 0, RJ013int = 0, RJ014int = 0, RJ015int = 0, RJ018int = 0, RJ019int = 0, RJ020int = 0, RJ023int = 0, RJ025int = 0, RJ028int = 0, RJ032int = 0, RJ033int = 0, RJ037int = 0, RJ039int = 0, RJ046int = 0, RJ050int = 0, RJ052int = 0, RJ053int = 0, RJ054int = 0,
            tdGD001int = 0, tdGD002int = 0, tdGD003int = 0, tdGD004int = 0, tdGD005int = 0, tdGD006int = 0, tdGD007int = 0, tdRJ013int = 0, tdRJ014int = 0, tdRJ015int = 0, tdRJ018int = 0, tdRJ019int = 0, tdRJ020int = 0, tdRJ023int = 0, tdRJ025int = 0, tdRJ028int = 0, tdRJ032int = 0, tdRJ033int = 0, tdRJ037int = 0, tdRJ039int = 0, tdRJ046int = 0, tdRJ050int = 0, tdRJ052int = 0, tdRJ053int = 0, tdRJ054int = 0;
    private String finger = "", masalahkeperawatan = "", GD001 = "", GD002 = "", GD003 = "", GD004 = "", GD005 = "", GD006 = "", GD007 = "", RJ013 = "", RJ014 = "", RJ015 = "", RJ018 = "", RJ019 = "", RJ020 = "", RJ023 = "", RJ025 = "", RJ028 = "", RJ032 = "", RJ033 = "", RJ037 = "", RJ039 = "", RJ046 = "", RJ050 = "", RJ052 = "", RJ053 = "", RJ054 = "";
    private StringBuilder htmlContent;

    /**
     * Creates new form DlgPemberianInfus
     *
     * @param parent
     * @param modal
     */
    public DlgKompilasiBerkas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tabMode = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No RM", "Nama Pasien", "Status Rawat", "Tanggal", "Waktu", "Poliklinik", "Diagnosa", "GD001", "GD002", "GD003", "GD004", "GD005", "GD006", "GD007",
            "RJ013", "RJ014", "RJ015", "RJ018", "RJ019", "RJ020", "RJ023", "RJ025", "RJ028", "RJ032", "RJ033", "RJ037", "RJ039", "RJ044", "RJ046", "RJ052", "RJ053", "RJ054"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class
            };

            /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
//        Object[] row={"No.Rawat","No RM","Nama Pasien","Status Rawat","Tanggal","Waktu","Poliklinik","Diagnosa"};

//        tabMode=new DefaultTableModel(null,row){
//              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
//        };
        tbKompilasi.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKompilasi.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKompilasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 33; i++) {
            TableColumn column = tbKompilasi.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
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
            } else if (i == 8) {
                column.setPreferredWidth(100);
            } else if (i == 9) {
                column.setPreferredWidth(75);
            } else if (i == 10) {
                column.setPreferredWidth(75);
            } else if (i == 11) {
                column.setPreferredWidth(75);
            } else if (i == 12) {
                column.setPreferredWidth(75);
            } else if (i == 13) {
                column.setPreferredWidth(75);
            } else if (i == 14) {
                column.setPreferredWidth(75);
            } else if (i == 15) {
                column.setPreferredWidth(75);
            } else if (i == 16) {
                column.setPreferredWidth(75);
            } else if (i == 17) {
                column.setPreferredWidth(75);
            } else if (i == 18) {
                column.setPreferredWidth(75);
            } else if (i == 19) {
                column.setPreferredWidth(75);
            } else if (i == 20) {
                column.setPreferredWidth(75);
            } else if (i == 21) {
                column.setPreferredWidth(75);
            } else if (i == 22) {
                column.setPreferredWidth(75);
            } else if (i == 23) {
                column.setPreferredWidth(75);
            } else if (i == 24) {
                column.setPreferredWidth(75);
            } else if (i == 25) {
                column.setPreferredWidth(75);
            } else if (i == 26) {
                column.setPreferredWidth(75);
            } else if (i == 27) {
                column.setPreferredWidth(75);
            } else if (i == 28) {
                column.setPreferredWidth(75);
            } else if (i == 29) {
                column.setPreferredWidth(75);
            } else if (i == 30) {
                column.setPreferredWidth(75);
            } else if (i == 31) {
                column.setPreferredWidth(75);
            } else if (i == 32) {
                column.setPreferredWidth(75);
            } else if (i == 33) {
                column.setPreferredWidth(75);
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

        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {

                if (penjab.getTable().getSelectedRow() != -1) {
                    Kdpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(), 1).toString());
                    nmpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(), 2).toString());
                }
                Kdpnj.requestFocus();

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
                + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        PilihSemua = new javax.swing.JMenuItem();
        ScrollHTML = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKompilasi = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnPrint = new widget.Button();
        BtnPrint1 = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        Kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        BtnPenjab = new widget.Button();
        CmbStts = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 2, true), "::[ Kompilasi Berkas Rekam Medis ]::", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Kompilasi Berkas");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(150, 30));
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

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Kompilasi Data Sosial");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(170, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint1);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-07-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-07-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari2);

        Kdpnj.setEditable(false);
        Kdpnj.setHighlighter(null);
        Kdpnj.setName("Kdpnj"); // NOI18N
        Kdpnj.setPreferredSize(new java.awt.Dimension(50, 23));
        Kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdpnjKeyPressed(evt);
            }
        });
        panelGlass10.add(Kdpnj);

        nmpnj.setEditable(false);
        nmpnj.setName("nmpnj"); // NOI18N
        nmpnj.setPreferredSize(new java.awt.Dimension(200, 23));
        panelGlass10.add(nmpnj);

        BtnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab.setMnemonic('1');
        BtnPenjab.setText("Penjamin");
        BtnPenjab.setToolTipText("ALt+1");
        BtnPenjab.setName("BtnPenjab"); // NOI18N
        BtnPenjab.setPreferredSize(new java.awt.Dimension(100, 23));
        BtnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjabActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnPenjab);

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

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));
        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, tidak ada data");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            int i = 0;
            for (i = 0; i < tbKompilasi.getRowCount(); i++) {
                if (tbKompilasi.getValueAt(i, 0).toString().equals("true")) {
                    String nomorrawat = tbKompilasi.getValueAt(i, 1).toString();

                    if (Sequel.cariInteger("select count(no_rawat) from penilaian_medis_igd where no_rawat='" + nomorrawat + "'") > 0) {
                        cetakAsesmenMedisIgd(nomorrawat);
                        System.out.println("cetak asesmen medis");
                    }
                    if (Sequel.cariInteger("select count(no_rawat) from penilaian_awal_keperawatan_igd where no_rawat='" + nomorrawat + "'") > 0) {
                        cetakAsesmenKeperawatanIGD(nomorrawat);
                    }
                    if (Sequel.cariInteger("select count(no_rawat) from data_triase_igd where no_rawat='" + nomorrawat + "'") > 0) {
                        cetakTriaseIGD(nomorrawat);
                    }
                    if (Sequel.cariInteger("select count(no_rawat) from catatan_observasi_igd where no_rawat='" + nomorrawat + "'") > 0) {
                        cetakObsIGD(nomorrawat);
                    }
                    if (Sequel.cariInteger("select count(no_rawat) from catatan_observasi_igd_obat where no_rawat='" + nomorrawat + "'") > 0) {
                        cetakObsIGDObat(nomorrawat);
                    }
                    if (Sequel.cariInteger("select count(no_rawat) from catatan_observasi_igd_nyeri where no_rawat='" + nomorrawat + "'") > 0) {
                        cetakObsIGDNyeri(nomorrawat);
                    }
                    if (Sequel.cariInteger("select count(no_rawat) from catatan_observasi_igd_nyeri where no_rawat='" + nomorrawat + "'") > 0) {
                        cetakSkriningCovid(nomorrawat);
                    }
                    //if (Sequel.cariInteger("select count(no_rawat) from catatan_keperawatan where no_rawat='" + nomorrawat + "'") > 0) {
                        //cetakCatatanKeperawatan(nomorrawat);
                    //}
                    if (Sequel.cariInteger("select count(no_rawat) from bridging_sep where no_rawat='" + nomorrawat + "'") > 0) {
                        cetakSEP(nomorrawat);
                    }
                    if (Sequel.cariInteger("select count(resume_pasien.no_rawat) from resume_pasien where resume_pasien.no_rawat='" + nomorrawat + "'") > 0) {
                        ResumePasien(nomorrawat);
                    }
                    if (Sequel.cariInteger("select count(resume_pasien_klaiminacbg.no_rawat) from resume_pasien_klaiminacbg where resume_pasien_klaiminacbg.no_rawat='" + nomorrawat + "'") > 0) {
                        ResumePasienNCC(nomorrawat);
                    }
                    //RJ013 summary list
//                    if (Sequel.cariInteger("select count(resume_pasien.no_rawat) from resume_pasien where resume_pasien.no_rawat='" + nomorrawat + "'") > 0) {
//                        cetakSummaryList(nomorrawat);
//                    }
                    //RJ014 ASESMEN AWAL MEDIS PASIEN RAWAT JALAN
                    if (Sequel.cariInteger("select count(penilaian_medis_ralan.no_rawat) from penilaian_medis_ralan where penilaian_medis_ralan.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakAsesmenMedisUmum(nomorrawat);
                    }

                    //RJ015 ASESMEN AWAL MEDIS PASIEN RAWAT JALAN
                    if (Sequel.cariInteger("select count(penilaian_awal_keperawatan_ralan.no_rawat) from penilaian_awal_keperawatan_ralan where penilaian_awal_keperawatan_ralan.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakAsesmenKeperawatanUmum(nomorrawat);
                    }
                    //RJ018 EDUKASI PASIEN DAN KELUARGA
//                    if (Sequel.cariInteger("select count(edukasi_pasien_rajal.no_rawat) from edukasi_pasien_rajal where edukasi_pasien_rajal.no_rawat='" + nomorrawat + "'") > 0) {
//                        cetakEdukasiRalan(nomorrawat);
//                    }
                    //RJ019 PENGKAJIAN KEPERAWATAN PASIEN GERIATRI
//                    if (Sequel.cariInteger("select count(penilaian_keperawatan_pasien_geriatri.no_rawat) from penilaian_keperawatan_pasien_geriatri where penilaian_keperawatan_pasien_geriatri.no_rawat='" + nomorrawat + "'") > 0) {
//                        cetakPengkajianKepGeriatri(nomorrawat);
//                    }
                    //RJ020 CPPT
                    if (Sequel.cariInteger("select count(pemeriksaan_ralan.no_rawat) from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakCPPTRalan(nomorrawat);
                    }
                    //RJ023 REKAM GIZI RAWAT JALAN
//                    if (Sequel.cariInteger("select count(awal_medis_giziklinis.no_rawat) from awal_medis_giziklinis where awal_medis_giziklinis.no_rawat='" + nomorrawat + "'") > 0) {
//                        cetakAwalMedisGizi(nomorrawat);
//                    }
                    //RJ025 PEMERIKSAAN LABORATORIUM
                    if (Sequel.cariInteger("select count(periksa_lab.no_rawat) from periksa_lab where periksa_lab.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakPemeriksaanLab(nomorrawat);
                    }
                    //RJ028 FORM PERMINTAAN PEMERIKSAAN RADIOLOGI
                    if (Sequel.cariInteger("select count(periksa_radiologi.no_rawat) from periksa_radiologi where periksa_radiologi.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakPermintaanRad(nomorrawat);
                    }
                    //RJ032 SURAT KETERANGAN DOKTER (SAKIT)
                    if (Sequel.cariInteger("select count(suratsakit.no_rawat) from suratsakit where suratsakit.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakSuratSakit(nomorrawat);
                    }
                    //RJ033 SURAT KETERANGAN KONTROL (RAJAL)
                    if (Sequel.cariInteger("select count(skdp_bpjs_new.no_rawat) from skdp_bpjs_new where skdp_bpjs_new.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakSuratKontrol(nomorrawat);
                    }
                    //RJ037 SURAT RUJUK BALIK
                    if (Sequel.cariInteger("SELECT COUNT(bridging_sep.no_rawat) FROM	bridging_sep INNER JOIN bridging_rujukan_bpjs ON bridging_sep.no_sep = bridging_rujukan_bpjs.no_sep where bridging_sep.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakSuratRujukBalik(nomorrawat);
                    }
                    //RJ039 FORMULIR PERMINTAAN PELAYANAN  AMBULANCE
//                    if (Sequel.cariInteger("select count(pelayanan_ambulance.no_rawat) from pelayanan_ambulance where pelayanan_ambulance.no_rawat='" + nomorrawat + "'") > 0) {
//                        cetakPermintaanPelayananAmbulance(nomorrawat);
//                    }
                    //RJ046 PEMERIKSAAN MEDIS MATA
                    if (Sequel.cariInteger("select count(penilaian_medis_ralan_mata.no_rawat) from penilaian_medis_ralan_mata where penilaian_medis_ralan_mata.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakAwalMedisMata(nomorrawat);
                    }
                    //RJ050 LayananKFR
//                    if (Sequel.cariInteger("select count(layanankfr.no_rawat) from layanankfr where layanankfr.no_rawat='" + nomorrawat + "'") > 0) {
//                        cetakLayananKFR(nomorrawat);
//                    }
                    //RJ052 UjiFungsiKFR
                    if (Sequel.cariInteger("select count(uji_fungsi_kfr.no_rawat) from uji_fungsi_kfr where uji_fungsi_kfr.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakUjiFungsiKFR(nomorrawat);
                    }
                    //RJ053 ProgramKFR
//                    if (Sequel.cariInteger("SELECT COUNT( reg_periksa.no_rawat ) FROM	program_kfr INNER JOIN reg_periksa ON program_kfr.no_rkm_medis = reg_periksa.no_rkm_medis where reg_periksa.no_rawat='" + nomorrawat + "'") > 0) {
//                        cetakProgramKFR(nomorrawat);
//                    }
                    //RJ054 AwalMedisPsikiatri
                    if (Sequel.cariInteger("select count(penilaian_medis_ralan_psikiatrik.no_rawat) from penilaian_medis_ralan_psikiatrik where penilaian_medis_ralan_psikiatrik.no_rawat='" + nomorrawat + "'") > 0) {
                        cetakAwalMedisPsikiatri(nomorrawat);
                    }

                    //Form Pemeriksaan Tambahan
//                    if (Sequel.cariInteger("select count(form_pemeriksaan.no_rawat) from form_pemeriksaan where form_pemeriksaan.no_rawat='" + nomorrawat + "'") > 0) {
//                        cetakFormulirTambahan(nomorrawat);
//                    }
                }

            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(rootPane, "Proses Kompilasi Selesai");
        }

}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed

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
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbKompilasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKompilasiMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKompilasiMouseClicked

    private void tbKompilasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKompilasiKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbKompilasiKeyPressed

    private void PilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PilihSemuaActionPerformed
        int i = 0;
        for (i = 0; i < tbKompilasi.getRowCount(); i++) {
            tbKompilasi.setValueAt(true, i, 0);
        }
    }//GEN-LAST:event_PilihSemuaActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, tidak ada data");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            int i = 0;
            for (i = 0; i < tbKompilasi.getRowCount(); i++) {
                if (tbKompilasi.getValueAt(i, 0).toString().equals("true")) {
                    String norm = tbKompilasi.getValueAt(i, 2).toString();
                    String norawat = tbKompilasi.getValueAt(i, 1).toString();
                    cetakDatSOS(norm, norawat);
                }

            }
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void KdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdpnjKeyPressed

    }//GEN-LAST:event_KdpnjKeyPressed

    private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
        akses.setform("DlgPasien");
        penjab.isCek();
        penjab.onCari();
        penjab.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnPenjabActionPerformed

    private void CmbSttsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbSttsKeyPressed

    }//GEN-LAST:event_CmbSttsKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKompilasiBerkas dialog = new DlgKompilasiBerkas(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPenjab;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.ComboBox CmbStts;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Kdpnj;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JMenuItem PilihSemua;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollHTML;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox nmpnj;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.Table tbKompilasi;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String kodepenjamin = "";
        if (!Kdpnj.getText().equals("")) {
            kodepenjamin = " and reg_periksa.kd_pj='" + Kdpnj.getText().toString() + "' ";
        }
        String statusrawat = "";
        if (!CmbStts.getSelectedItem().toString().equals("Semua")) {
            statusrawat = " and reg_periksa.status_lanjut='" + CmbStts.getSelectedItem().toString() + "' ";
        }
        try {
            Valid.tabelKosong(tabMode);
            ps = koneksi.prepareStatement("select reg_periksa.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, reg_periksa.status_lanjut,reg_periksa.tgl_registrasi,reg_periksa.jam_reg, poliklinik.nm_poli from reg_periksa join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis"
                    + " join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "
                    + "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.status_bayar='Sudah Bayar' " + kodepenjamin + statusrawat + " and (reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or poliklinik.nm_poli like ?) "
                    + "order by reg_periksa.tgl_registrasi, reg_periksa.jam_reg");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                GD001int = 0;
                GD002int = 0;
                GD003int = 0;
                GD004int = 0;
                GD005int = 0;
                GD006int = 0;
                GD007int = 0;
                RJ013int = 0;
                RJ014int = 0;
                RJ015int = 0;
                RJ018int = 0;
                RJ019int = 0;
                RJ020int = 0;
                RJ023int = 0;
                RJ025int = 0;
                RJ028int = 0;
                RJ032int = 0;
                RJ033int = 0;
                RJ037int = 0;
                RJ039int = 0;
                RJ046int = 0;
                RJ050int = 0;
                RJ052int = 0;
                RJ053int = 0;
                RJ054int = 0;

                tdGD001int = 0;
                tdGD002int = 0;
                tdGD003int = 0;
                tdGD004int = 0;
                tdGD005int = 0;
                tdGD006int = 0;
                tdGD007int = 0;
                tdRJ013int = 0;
                tdRJ014int = 0;
                tdRJ015int = 0;
                tdRJ018int = 0;
                tdRJ019int = 0;
                tdRJ020int = 0;
                tdRJ023int = 0;
                tdRJ025int = 0;
                tdRJ028int = 0;
                tdRJ032int = 0;
                tdRJ033int = 0;
                tdRJ037int = 0;
                tdRJ039int = 0;
                tdRJ046int = 0;
                tdRJ050int = 0;
                tdRJ052int = 0;
                tdRJ053int = 0;
                tdRJ054int = 0;
                rs = ps.executeQuery();
                while (rs.next()) {

                    if (Sequel.cariInteger("select count(data_triase_igd.no_rawat) from data_triase_igd where data_triase_igd.no_rawat='" + rs.getString(1) + "'") > 0) {
                        GD001 = "Ada";
                        GD001int = GD001int + 1;
                    } else {
                        GD001 = "Tidak ada";
                        tdGD001int = tdGD001int + 1;
                    }
                    if (Sequel.cariInteger("select count(penilaian_awal_keperawatan_igd.no_rawat) from penilaian_awal_keperawatan_igd where penilaian_awal_keperawatan_igd.no_rawat='" + rs.getString(1) + "'") > 0) {
                        GD002 = "Ada";
                        GD002int = GD002int + 1;
                    } else {
                        GD002 = "Tidak ada";
                        tdGD002int = tdGD002int + 1;
                    }
                    if (Sequel.cariInteger("select count(penilaian_medis_igd.no_rawat) from penilaian_medis_igd where penilaian_medis_igd.no_rawat='" + rs.getString(1) + "'") > 0) {
                        GD003 = "Ada";
                        GD003int = GD003int + 1;
                    } else {
                        GD003 = "Tidak ada";
                        tdGD003int = tdGD003int + 1;
                    }
                    if (Sequel.cariInteger("select count(catatan_observasi_igd.no_rawat) from catatan_observasi_igd where catatan_observasi_igd.no_rawat='" + rs.getString(1) + "'") > 0) {
                        GD004 = "Ada";
                        GD004int = GD004int + 1;
                    } else {
                        GD004 = "Tidak ada";
                        tdGD004int = tdGD004int + 1;
                    }
                    if (Sequel.cariInteger("select count(resume_pasien.no_rawat) from resume_pasien where resume_pasien.no_rawat='" + rs.getString(1) + "'") > 0) {
                        GD005 = "Ada";
                        GD005int = GD005int + 1;
                    } else {
                        GD005 = "Tidak ada";
                        tdGD005int = tdGD005int + 1;
                    }
                    if (Sequel.cariInteger("select count(edukasi_pasien_rajal.no_rawat) from edukasi_pasien_rajal where edukasi_pasien_rajal.no_rawat='" + rs.getString(1) + "'") > 0) {
                        GD006 = "Ada";
                        GD006int = GD006int + 1;
                    } else {
                        GD006 = "Tidak ada";
                        tdGD006int = tdGD006int + 1;
                    }
                    if (Sequel.cariInteger("select count(deteksi_dini_corona.no_rawat) from deteksi_dini_corona where deteksi_dini_corona.no_rawat='" + rs.getString(1) + "'") > 0) {
                        GD007 = "Ada";
                        GD007int = GD007int + 1;
                    } else {
                        GD007 = "Tidak ada";
                        tdGD007int = tdGD007int + 1;
                    }
                    if (Sequel.cariInteger("select count(resume_pasien.no_rawat) from resume_pasien where resume_pasien.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ013 = "Ada";
                        RJ013int = RJ013int + 1;
                    } else {
                        RJ013 = "Tidak ada";
                        tdRJ013int = tdRJ013int + 1;
                    }
                    if (Sequel.cariInteger("select count(penilaian_medis_ralan.no_rawat) from penilaian_medis_ralan where penilaian_medis_ralan.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ014 = "Ada";
                        RJ014int = RJ014int + 1;
                    } else {
                        RJ014 = "Tidak ada";
                        tdRJ014int = tdRJ014int + 1;
                    }
                    if (Sequel.cariInteger("select count(penilaian_awal_keperawatan_ralan.no_rawat) from penilaian_awal_keperawatan_ralan where penilaian_awal_keperawatan_ralan.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ015 = "Ada";
                        RJ015int = RJ015int + 1;
                    } else {
                        RJ015 = "Tidak ada";
                        tdRJ015int = tdRJ015int + 1;
                    }
                    if (Sequel.cariInteger("select count(edukasi_pasien_rajal.no_rawat) from edukasi_pasien_rajal where edukasi_pasien_rajal.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ018 = "Ada";
                        RJ018int = RJ018int + 1;
                    } else {
                        RJ018 = "Tidak ada";
                        tdRJ018int = tdRJ018int + 1;
                    }
                    if (Sequel.cariInteger("select count(penilaian_keperawatan_pasien_geriatri.no_rawat) from penilaian_keperawatan_pasien_geriatri where penilaian_keperawatan_pasien_geriatri.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ019 = "Ada";
                        RJ019int = RJ019int + 1;
                    } else {
                        RJ019 = "Tidak ada";
                        tdRJ019int = tdRJ019int + 1;
                    }
                    if (Sequel.cariInteger("select count(pemeriksaan_ralan.no_rawat) from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ020 = "Ada";
                        RJ020int = RJ020int + 1;
                    } else {
                        RJ020 = "Tidak ada";
                        tdRJ020int = tdRJ020int + 1;
                    }
                    if (Sequel.cariInteger("select count(awal_medis_giziklinis.no_rawat) from awal_medis_giziklinis where awal_medis_giziklinis.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ023 = "Ada";
                        RJ023int = RJ023int + 1;
                    } else {
                        RJ023 = "Tidak ada";
                        tdRJ023int = tdRJ023int + 1;
                    }
                    if (Sequel.cariInteger("select count(periksa_lab.no_rawat) from periksa_lab where periksa_lab.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ025 = "Ada";
                        RJ025int = RJ025int + 1;
                    } else {
                        RJ025 = "Tidak ada";
                        tdRJ025int = tdRJ025int + 1;
                    }
                    if (Sequel.cariInteger("select count(periksa_radiologi.no_rawat) from periksa_radiologi where periksa_radiologi.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ028 = "Ada";
                        RJ028int = RJ028int + 1;
                    } else {
                        RJ028 = "Tidak ada";
                        tdRJ028int = tdRJ028int + 1;
                    }
                    if (Sequel.cariInteger("select count(suratsakit.no_rawat) from suratsakit where suratsakit.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ032 = "Ada";
                        RJ032int = RJ032int + 1;
                    } else {
                        RJ032 = "Tidak ada";
                        tdRJ032int = tdRJ032int + 1;
                    }
                    if (Sequel.cariInteger("select count(skdp_bpjs_new.no_rawat) from skdp_bpjs_new where skdp_bpjs_new.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ033 = "Ada";
                        RJ033int = RJ033int + 1;
                    } else {
                        RJ033 = "Tidak ada";
                        tdRJ033int = tdRJ033int + 1;
                    }
                    if (Sequel.cariInteger("SELECT COUNT(bridging_sep.no_rawat) FROM	bridging_sep INNER JOIN bridging_rujukan_bpjs ON bridging_sep.no_sep = bridging_rujukan_bpjs.no_sep where bridging_sep.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ037 = "Ada";
                        RJ037int = RJ037int + 1;
                    } else {
                        RJ037 = "Tidak ada";
                        tdRJ037int = tdRJ037int + 1;
                    }
                    if (Sequel.cariInteger("select count(pelayanan_ambulance.no_rawat) from pelayanan_ambulance where pelayanan_ambulance.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ039 = "Ada";
                        RJ039int = RJ039int + 1;
                    } else {
                        RJ039 = "Tidak ada";
                        tdRJ039int = tdRJ039int + 1;
                    }
                    if (Sequel.cariInteger("select count(penilaian_medis_ralan_mata.no_rawat) from penilaian_medis_ralan_mata where penilaian_medis_ralan_mata.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ046 = "Ada";
                        RJ046int = RJ046int + 1;
                    } else {
                        RJ046 = "Tidak ada";
                        tdRJ046int = tdRJ046int + 1;
                    }
                    if (Sequel.cariInteger("select count(layanankfr.no_rawat) from layanankfr where layanankfr.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ050 = "Ada";
                        RJ050int = RJ050int + 1;
                    } else {
                        RJ050 = "Tidak ada";
                        tdRJ050int = tdRJ050int + 1;
                    }
                    if (Sequel.cariInteger("select count(uji_fungsi_kfr.no_rawat) from uji_fungsi_kfr where uji_fungsi_kfr.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ052 = "Ada";
                        RJ052int = RJ052int + 1;
                    } else {
                        RJ052 = "Tidak ada";
                        tdRJ052int = tdRJ052int + 1;
                    }
                    if (Sequel.cariInteger("SELECT COUNT( reg_periksa.no_rawat ) FROM	program_kfr	INNER JOIN reg_periksa ON program_kfr.no_rkm_medis = reg_periksa.no_rkm_medis where reg_periksa.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ053 = "Ada";
                        RJ053int = RJ053int + 1;
                    } else {
                        RJ053 = "Tidak ada";
                        tdRJ053int = tdRJ053int + 1;
                    }
                    if (Sequel.cariInteger("select count(penilaian_medis_ralan_psikiatrik.no_rawat) from penilaian_medis_ralan_psikiatrik where penilaian_medis_ralan_psikiatrik.no_rawat='" + rs.getString(1) + "'") > 0) {
                        RJ054 = "Ada";
                        RJ054int = RJ054int + 1;
                    } else {
                        RJ054 = "Tidak ada";
                        tdRJ054int = tdRJ054int + 1;
                    }
//                    System.out.println(GD001);
                    tabMode.addRow(new Object[]{
                        false, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        Sequel.cariIsi("select penyakit.nm_penyakit from diagnosa_pasien join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.prioritas='1' and diagnosa_pasien.no_rawat=?", rs.getString(1)), GD001, GD002, GD003, GD004, GD005, GD006, GD007, RJ013, RJ014, RJ015, RJ018, RJ019, RJ020, RJ023, RJ025, RJ028, RJ032, RJ033, RJ037, RJ039, RJ046, RJ050, RJ052, RJ053, RJ054
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
            tabMode.addRow(new Object[]{
                false, "", "", "", "", "", "", "Total",
                "Ada", GD001int, GD002int, GD003int, GD004int, GD005int, GD006int, GD007int, RJ013int, RJ014int, RJ015int, RJ018int, RJ019int, RJ020int, RJ023int, RJ025int, RJ028int, RJ032int, RJ033int, RJ037int, RJ039int, RJ046int, RJ050int, RJ052int, RJ053int, RJ054int
            });
            tabMode.addRow(new Object[]{
                false, "", "", "", "", "", "", "Total",
                "Tidak ada", tdGD001int, tdGD002int, tdGD003int, tdGD004int, tdGD005int, tdGD006int, tdGD007int, tdRJ013int, tdRJ014int, tdRJ015int, tdRJ018int, tdRJ019int, tdRJ020int, tdRJ023int, tdRJ025int, tdRJ028int, tdRJ032int, tdRJ033int, tdRJ037int, tdRJ039int, tdRJ046int, tdRJ050int, tdRJ052int, tdRJ053int, tdRJ054int
            });

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + (tabMode.getRowCount() - 2));
    }

    public void emptTeks() {
        TCari.setText("");

    }

    private void getData() {

    }

    private void isRawat() {

    }

    private void cetakAsesmenMedisIgd(String norawat) {
        String nomorrawat = norawat.replaceAll("/", "");
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        param.put("lokalis", Sequel.cariGambar("select lokalis from gambar"));
//          finger = Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
        param.put("finger", "Belum Final");
        System.out.println("fungsi cetak");

        Valid.ReportKompilasiBerkas("rptCetakPenilaianAwalMedisIGD.jasper", "report", "::[ Laporan Penilaian Awal Medis IGD ]::", "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_igd.tanggal,"
                + "penilaian_medis_igd.kd_dokter,penilaian_medis_igd.anamnesis,penilaian_medis_igd.hubungan,penilaian_medis_igd.keluhan_utama,penilaian_medis_igd.rps,penilaian_medis_igd.rpk,penilaian_medis_igd.rpd,penilaian_medis_igd.rpo,penilaian_medis_igd.alergi,"
                + "penilaian_medis_igd.keadaan,penilaian_medis_igd.gcs,penilaian_medis_igd.kesadaran,penilaian_medis_igd.td,penilaian_medis_igd.nadi,penilaian_medis_igd.rr,penilaian_medis_igd.suhu,penilaian_medis_igd.spo,penilaian_medis_igd.bb,penilaian_medis_igd.tb,"
                + "penilaian_medis_igd.kepala,penilaian_medis_igd.mata,penilaian_medis_igd.mulut,penilaian_medis_igd.leher,penilaian_medis_igd.thoraks,penilaian_medis_igd.abdomen,penilaian_medis_igd.ekstremitas,penilaian_medis_igd.genital,penilaian_medis_igd.ket_fisik,"
                + "penilaian_medis_igd.ket_lokalis,penilaian_medis_igd.ekg,penilaian_medis_igd.rad,penilaian_medis_igd.lab,penilaian_medis_igd.diagnosis,penilaian_medis_igd.tata,dokter.nm_dokter,penilaian_medis_igd.keadaan1,penilaian_medis_igd.kulit,penilaian_medis_igd.dada,penilaian_medis_igd.hidung,penilaian_medis_igd.telinga,penilaian_medis_igd.rencanaranap,penilaian_medis_igd.indikasiranap, penilaian_medis_igd.nyeri,penilaian_medis_igd.lasupdate "
                + "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                + "inner join penilaian_medis_igd on reg_periksa.no_rawat=penilaian_medis_igd.no_rawat "
                + "inner join dokter on penilaian_medis_igd.kd_dokter=dokter.kd_dokter where penilaian_medis_igd.no_rawat='" + norawat + "'", param, norawat, norekammedis, "AWALMEDISIGD");
    }

    private void cetakTriaseIGD(String norawat) {
        RMTriaseIGD triase = new RMTriaseIGD(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //triase.BtnSimpanDokumenRMETriase(norawat, norekammedis);
    }

    private void cetakObsIGD(String norawat) {
        RMDataCatatanObservasiIGD obvIGD = new RMDataCatatanObservasiIGD(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //obvIGD.CetakObsIGD(norawat, norekammedis);
    }

    private void cetakObsIGDObat(String norawat) {
        RMDataCatatanObservasiIGD obvIGD = new RMDataCatatanObservasiIGD(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //obvIGD.CetakObsIGDObat(norawat, norekammedis);
    }

//    private void cetakCatatanKeperawatan(String norawat) {
//        RMDataCatatanKeperawatan catkep = new RMDataCatatanKeperawatan(null, false);
//        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        catkep.MnCatatanKeperawatan(norawat, norekammedis);
//    }

    private void cetakSEP(String norawat) {
        BPJSDataSEP datasep = new BPJSDataSEP(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //datasep.kompilasiSEPPdf(norawat, norekammedis);
    }

    private void ResumePasien(String norawat) {
        RMDataResumePasien resumepasien = new RMDataResumePasien(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //resumepasien.ResumePasienPdf(norawat, norekammedis);
    }

    private void ResumePasienNCC(String norawat) {
        RMDataResumePasien resumepasien = new RMDataResumePasien(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //resumepasien.ResumePasienPdfNCC(norawat, norekammedis);
    }

    private void cetakObsIGDNyeri(String norawat) {
        RMDataCatatanObservasiIGD obvIGD = new RMDataCatatanObservasiIGD(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //obvIGD.CetakObsIGDNyeri(norawat, norekammedis);
    }

    private void cetakDatSOS(String norekammedis, String norawat) {
        DlgPasien pasien = new DlgPasien(null, false);
        String status = Sequel.cariIsi("select reg_periksa.stts_daftar from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        String statuslanjut = Sequel.cariIsi("select reg_periksa.status_lanjut from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        if (status.equals("Baru")) {
//            if (statuslanjut.equals("Ralan")) {
//                pasien.CetakDatsosRajalRME(norawat, norekammedis);
//            }
//            if (statuslanjut.equals("Ranap")) {
//                pasien.CetakDatsosRajalRME(norawat, norekammedis);
//            }
//        }

    }

    private void cetakSkriningCovid(String norawat) {
        RMDeteksiDiniCorona skriningcov = new RMDeteksiDiniCorona(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //skriningcov.cetakSkriningCovidRME(norawat, norekammedis);
    }

    private void cetakAsesmenKeperawatanIGD(String norawat) {
        String nomorrawat = norawat.replaceAll("/", "");
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
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
        Valid.ReportKompilasiBerkas("rptCetakPenilaianAwalKeperawatanIGD.jasper", "report", "::[ Laporan Penilaian Awal Keperawatan Ralan ]::",
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
                + "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where reg_periksa.no_rawat='" + norawat + "'", param, norawat, norekammedis, "AWALKEPIGD");

    }

    private void cetakSummaryList(String norawat) {

    }

    private void cetakAsesmenMedisUmum(String norawat) {
        RMPenilaianAwalMedisRalanDewasa awalralan = new RMPenilaianAwalMedisRalanDewasa(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //awalralan.AwalMedisRalanPdf(norawat, norekammedis);
    }

    private void cetakAsesmenKeperawatanUmum(String norawat) {
        String nomorrawat = norawat.replaceAll("/", "");
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
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
        Valid.ReportKompilasiBerkas("rptCetakPenilaianAwalKeperawatanRalan.jasper", "report", "::[ Laporan Penilaian Awal Keperawatan Ralan ]::",
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
                + "	reg_periksa.no_rawat='" + norawat + "'", param, norawat, norekammedis, "AWALKEPRALAN");

    }

    private void cetakEdukasiRalan(String norawat) {

    }

//    private void cetakPengkajianKepGeriatri(String norawat) {
//        RMPenilaianKeperawatanPasienGeriatri geriatri = new RMPenilaianKeperawatanPasienGeriatri(null, false);
//        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        geriatri.GeriatriPdf(norawat, norekammedis);
//    }
    private void cetakCPPTRalan(String norawat) {
        String nomorrawat = norawat.replaceAll("/", "");
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
//                    param.put("pemberiasuhan", Sequel.cariIsi("select petugas.nama from petugas join pemeriksaan_ralan on petugas.nip=dpjp_ranap.kd_dokter join pemeriksaan_ranap on dpjp_ranap.no_rawat=pemeriksaan_ranap.no_rawat where dpjp_ranap.statusdpjp='Utama' and pemeriksaan_ranap.no_rawat=?", TNoRw.getText()));
//                    param.put("spesialisralan", Sequel.cariIsi("select concat('Dokter Spesialis ',spesialis.nm_sps) from dokter join spesialis on dokter.kd_sps=spesialis.kd_sps join dpjp_ranap on dokter.kd_dokter=dpjp_ranap.kd_dokter join pemeriksaan_ranap on dpjp_ranap.no_rawat=pemeriksaan_ranap.no_rawat where dpjp_ranap.statusdpjp='Utama' and pemeriksaan_ranap.no_rawat=?", TNoRw.getText()));
        String pas = " and reg_periksa.no_rkm_medis like '%" + norekammedis + "%' ";

        String tgl = " pemeriksaan_ralan.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' " + pas;
        Valid.ReportKompilasiBerkas("rptJalanPemeriksaan.jasper", "report", "::[ Data Pemeriksaan Rawat Jalan ]::",
                "select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y')as tgl_lahir, pasien.no_ktp as noktp,"
                + "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,dokter.nm_dokter, "
                + "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, "
                + "pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.nyeri,pemeriksaan_ralan.keluhan, "
                + "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.instruksi,pemeriksaan_ralan.alergi,pemeriksaan_ralan.imun_ke,"
                + "pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,poliklinik.nm_poli,pegawai.jbtn,pegawai.nama, dokter.kd_dokter as nipdokter from dokter inner join pasien inner join poliklinik inner join reg_periksa inner join pemeriksaan_ralan inner join pegawai "
                + "on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_poli=poliklinik.kd_poli and pegawai.nik=pemeriksaan_ralan.nip and reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "
                + tgl + "and pemeriksaan_ralan.no_rawat like '%" + norawat + "%'"
                + "order by pemeriksaan_ralan.no_rawat and pemeriksaan_ralan.jam_rawat desc", param, norawat, norekammedis, "CPPT");
    }

//    private void cetakAwalMedisGizi(String norawat) {
//        RMAwalMedisGiziKlinis awalgizi = new RMAwalMedisGiziKlinis(null, false);
//        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        awalgizi.AwalMedisGiziPdf(norawat, norekammedis);
//    }

    private void cetakPemeriksaanLab(String norawat) {
        DlgCariPeriksaLab hasillab = new DlgCariPeriksaLab(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
       // hasillab.HasilLabPDFRME(norawat, norekammedis);
    }

    private void cetakPermintaanRad(String norawat) {
        DlgCariPeriksaRadiologi hasilrad = new DlgCariPeriksaRadiologi(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //hasilrad.RadPdf(norawat, norekammedis);
    }

    private void cetakSuratSakit(String norawat) {
        SuratSakit suratsakit = new SuratSakit(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //suratsakit.SuratSakitPdf(norawat, norekammedis);
    }

    private void cetakSuratKontrol(String norawat) {
        SuratKontrol suratkontrol = new SuratKontrol(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //suratkontrol.SuratKontrolPdf(norawat, norekammedis);
    }

    private void cetakSuratRujukBalik(String norawat) {
        BPJSRujukanKeluar rujukkeluar = new BPJSRujukanKeluar(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //rujukkeluar.CetakRujukKeluarRME(norawat, norekammedis);
    }

//    private void cetakPermintaanPelayananAmbulance(String norawat) {
//        DlgPermintaanAmbulance permAmulance = new DlgPermintaanAmbulance(null, false);
//        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        permAmulance.PermintaanAmbulancePdf(norawat, norekammedis);
//    }

//    private void cetakFormulirTambahan(String norawat) {
//        DlgFormulirPemeriksaan pemtambahan = new DlgFormulirPemeriksaan(null, false);
//        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        pemtambahan.MnPemeriksaanTambahanRMEPDF(norawat, norekammedis);
//    }

    private void cetakAwalMedisMata(String norawat) {
        RMPenilaianAwalMedisRalanMata awalMata = new RMPenilaianAwalMedisRalanMata(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //awalMata.AwalMedisMataPdf(norawat, norekammedis);
    }

//    private void cetakLayananKFR(String norawat) {
//        RMLayananKFR layanankfr = new RMLayananKFR(null, false);
//        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        layanankfr.LayananKFRPdf(norawat, norekammedis);
//    }

    private void cetakUjiFungsiKFR(String norawat) {
        RMUjiFungsiKFR ujikfr = new RMUjiFungsiKFR(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //ujikfr.UjiKFRPdf(norawat, norekammedis);
    }

//    private void cetakProgramKFR(String norawat) {
//        RMProgramKFR programkfr = new RMProgramKFR(null, false);
//        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
//        programkfr.ProgramKFRPdf(norawat, norekammedis);
//    }

    private void cetakAwalMedisPsikiatri(String norawat) {
        RMPenilaianAwalMedisRalanPsikiatrik awalpsikiatri = new RMPenilaianAwalMedisRalanPsikiatrik(null, false);
        String norekammedis = Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + norawat + "'");
        //awalpsikiatri.AwalPsikiatriPdf(norawat, norekammedis);
    }

    public void setNoRm(String norwt, Date tgl1, Date tgl2) {

        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
    }

    public void isCek() {
        BtnPrint.setEnabled(akses.getmutasi_berkas());
        BtnPrint1.setEnabled(akses.getmutasi_berkas());
        tampil();
    }

}

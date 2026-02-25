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
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class RMCatatanSBAR extends javax.swing.JDialog {

    private final DefaultTableModel tabModePemeriksaanSbar;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private String TANGGALMUNDUR = "yes";
    public DlgCariPegawai pegawai = new DlgCariPegawai(null, false);

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public RMCatatanSBAR(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(628, 674);

        tabModePemeriksaanSbar = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "No.R.M.", "Nama Pasien", "Tgl.Rawat", "Jam",
            "Situation", "Background", "Assesment", "Recommendation", "Advis", 
            "NIP", "Dokter/Paramedis", "Profesi/Jabatan", "Status Verifikasi"}) {
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
                java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPemeriksaanSbar.setModel(tabModePemeriksaanSbar);
        tbPemeriksaanSbar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaanSbar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbPemeriksaanSbar.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else if (i == 5) {
                column.setPreferredWidth(100);
            } else if (i == 6) {
                column.setPreferredWidth(190);
            } else if (i == 7) {
                column.setPreferredWidth(190);
            } else if (i == 8) {
                column.setPreferredWidth(190);
            } else if (i == 9) {
                column.setPreferredWidth(180);
            } else if (i == 10) {
                column.setPreferredWidth(100);
            } else if (i == 11) {
                column.setPreferredWidth(100);
            } else if (i == 12) {
                column.setPreferredWidth(100);
            } else if (i == 13) {
                column.setPreferredWidth(120);
            } else if (i == 14) {
                column.setPreferredWidth(120);
            }
        }
        tbPemeriksaanSbar.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        KdPeg.setDocument(new batasInput((byte) 20).getKata(KdPeg));
        TSituation.setDocument(new batasInput((int) 1000).getKata(TSituation));
        TBackground.setDocument(new batasInput((int) 1000).getKata(TBackground));
        TAssesment.setDocument(new batasInput((int) 1000).getKata(TAssesment));
        TRecommendation.setDocument(new batasInput((int) 1000).getKata(TRecommendation));
        TAdvis.setDocument(new batasInput((int) 1000).getKata(TAdvis));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampilPemeriksaanSbar();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampilPemeriksaanSbar();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampilPemeriksaanSbar();
                    }
                }
            });
        }

        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("RMCatatanSBAR")) {
                    if (pegawai.getTable().getSelectedRow() != -1) {
                        KdPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 0).toString());
                        TPegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 1).toString());
                        Jabatan.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(), 3).toString());
                        KdPeg.requestFocus();
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

        try {
            TANGGALMUNDUR = koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR = "yes";
        }

        ChkInput.setSelected(false);
        isForm();

        jam();
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
        MnCatatanADIME = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
        Umur = new widget.TextBox();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPemeriksaanSbar = new widget.Table();
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
        DTPTgl = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        scrollPane5 = new widget.ScrollPane();
        TSituation = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        TBackground = new widget.TextArea();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        TAssesment = new widget.TextArea();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        TRecommendation = new widget.TextArea();
        jLabel92 = new widget.Label();
        KdPeg = new widget.TextBox();
        TPegawai = new widget.TextBox();
        BtnSeekPegawai1 = new widget.Button();
        Jabatan = new widget.TextBox();
        jLabel93 = new widget.Label();
        jLabel94 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        TAdvis = new widget.TextArea();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCatatanADIME.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanADIME.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanADIME.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanADIME.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanADIME.setText("Formulir Catatan ADIME Gizi");
        MnCatatanADIME.setName("MnCatatanADIME"); // NOI18N
        MnCatatanADIME.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCatatanADIME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanADIMEActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCatatanADIME);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Catatan SBAR ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbPemeriksaanSbar.setAutoCreateRowSorter(true);
        tbPemeriksaanSbar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanSbar.setName("tbPemeriksaanSbar"); // NOI18N
        tbPemeriksaanSbar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanSbarMouseClicked(evt);
            }
        });
        tbPemeriksaanSbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanSbarKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbPemeriksaanSbar);

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

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2025" }));
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

        PanelInput.setToolTipText("");
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 450));
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
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 300));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 450, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2025" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(79, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(173, 40, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(238, 40, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        TSituation.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TSituation.setColumns(20);
        TSituation.setRows(5);
        TSituation.setName("TSituation"); // NOI18N
        TSituation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSituationKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(TSituation);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(120, 110, 530, 50);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        TBackground.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TBackground.setColumns(20);
        TBackground.setRows(5);
        TBackground.setName("TBackground"); // NOI18N
        TBackground.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBackgroundKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(TBackground);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(120, 170, 530, 50);

        jLabel88.setText("Situation :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(40, 110, 70, 23);

        jLabel89.setText("Background :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(40, 170, 70, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        TAssesment.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TAssesment.setColumns(20);
        TAssesment.setRows(5);
        TAssesment.setName("TAssesment"); // NOI18N
        TAssesment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAssesmentKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(TAssesment);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(120, 230, 530, 50);

        jLabel90.setText("Asesmen :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(20, 230, 90, 23);

        jLabel91.setText("Recommendation :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(10, 290, 100, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        TRecommendation.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TRecommendation.setColumns(20);
        TRecommendation.setRows(5);
        TRecommendation.setName("TRecommendation"); // NOI18N
        TRecommendation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRecommendationKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(TRecommendation);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(120, 290, 530, 50);

        jLabel92.setText("Dilakukan :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(10, 70, 70, 23);

        KdPeg.setHighlighter(null);
        KdPeg.setName("KdPeg"); // NOI18N
        KdPeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPegActionPerformed(evt);
            }
        });
        KdPeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPegKeyPressed(evt);
            }
        });
        FormInput.add(KdPeg);
        KdPeg.setBounds(90, 70, 115, 23);

        TPegawai.setEditable(false);
        TPegawai.setHighlighter(null);
        TPegawai.setName("TPegawai"); // NOI18N
        TPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPegawaiActionPerformed(evt);
            }
        });
        FormInput.add(TPegawai);
        TPegawai.setBounds(210, 70, 212, 23);

        BtnSeekPegawai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPegawai1.setMnemonic('4');
        BtnSeekPegawai1.setToolTipText("ALt+4");
        BtnSeekPegawai1.setName("BtnSeekPegawai1"); // NOI18N
        BtnSeekPegawai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPegawai1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeekPegawai1);
        BtnSeekPegawai1.setBounds(430, 70, 28, 23);

        Jabatan.setEditable(false);
        Jabatan.setHighlighter(null);
        Jabatan.setName("Jabatan"); // NOI18N
        FormInput.add(Jabatan);
        Jabatan.setBounds(640, 70, 209, 23);

        jLabel93.setText("Profesi / Jabatan / Departemen :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(440, 70, 190, 23);

        jLabel94.setText("Advis/ Intruksi :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 350, 100, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        TAdvis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TAdvis.setColumns(20);
        TAdvis.setRows(5);
        TAdvis.setName("TAdvis"); // NOI18N
        TAdvis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAdvisKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(TAdvis);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(120, 350, 530, 50);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, DTPTgl);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt, TCari, BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if ((!TSituation.getText().trim().equals("")) || (!TBackground.getText().trim().equals("")) || (!TAssesment.getText().trim().equals(""))
                || (!TRecommendation.getText().trim().equals(""))) {
            if (KdPeg.getText().trim().equals("") || TPegawai.getText().trim().equals("")) {
                Valid.textKosong(KdPeg, "Dokter/Paramedis masih kosong...!!");
            } else {
                if (akses.getkode().equals("Admin Utama")) {
                    Sequel.menyimpan("pemeriksaan_ranap_sbar", "?,?,?,?,?,?,?,?,?", "Data", 9, new String[]{
                        TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                        TSituation.getText(), TBackground.getText(), TAssesment.getText(), TRecommendation.getText(), TAdvis.getText(), KdPeg.getText()
                    });
                    tampilPemeriksaanSbar();
                    BtnBatalActionPerformed(evt);
                } else {
                    if (akses.getkode().equals(KdPeg.getText())) {
                        Sequel.menyimpan("pemeriksaan_ranap_sbar", "?,?,?,?,?,?,?,?,?", "Data", 9, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                            TSituation.getText(), TBackground.getText(), TAssesment.getText(), TRecommendation.getText(), TAdvis.getText(),KdPeg.getText()});
                        tampilPemeriksaanSbar();
                        BtnBatalActionPerformed(evt);
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa disimpan oleh dokter/petugas yang bersangkutan..!!");
                    }
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        /*    if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Instruksi,BtnBatal);
        } */
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tabModePemeriksaanSbar.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TNoRw.requestFocus();
        } else {
            for (i = 0; i < tbPemeriksaanSbar.getRowCount(); i++) {
                if (tbPemeriksaanSbar.getValueAt(i, 0).toString().equals("true")) {
                    if (akses.getkode().equals("Admin Utama")) {
                        Sequel.queryu("delete from pemeriksaan_ranap_sbar where no_rawat='" + tbPemeriksaanSbar.getValueAt(i, 1).toString()
                                + "' and tgl_perawatan='" + tbPemeriksaanSbar.getValueAt(i, 4).toString()
                                + "' and jam_rawat='" + tbPemeriksaanSbar.getValueAt(i, 5).toString() + "' ");
                    } else {
                        if (akses.getkode().equals(tbPemeriksaanSbar.getValueAt(i, 11).toString())) {
                            Sequel.queryu("delete from pemeriksaan_ranap_sbar where no_rawat='" + tbPemeriksaanSbar.getValueAt(i, 1).toString()
                                    + "' and tgl_perawatan='" + tbPemeriksaanSbar.getValueAt(i, 4).toString()
                                    + "' and jam_rawat='" + tbPemeriksaanSbar.getValueAt(i, 5).toString() + "' ");
                        } else {
                            JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh dokter/petugas yang bersangkutan..!!");
                        }
                    }
                }
            }
            tampilPemeriksaanSbar();
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
        if ((!TSituation.getText().trim().equals("")) || (!TBackground.getText().trim().equals("")) || (!TAssesment.getText().trim().equals(""))
                || (!TRecommendation.getText().trim().equals("")) || (!TAdvis.getText().trim().equals(""))) {
            if (tbPemeriksaanSbar.getSelectedRow() > -1) {
                if (akses.getkode().equals("Admin Utama")) {
                    Sequel.mengedit("pemeriksaan_ranap_sbar", "no_rawat='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 1)
                            + "' and tgl_perawatan='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 4)
                            + "' and jam_rawat='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5) + "'",
                            "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation.getText() + "',background='" + TBackground.getText() + "',"
                            + "assesment='" + TAssesment.getText() + "',recommendation='" + TRecommendation.getText() + "'," + "advis='" + TAdvis.getText() + "',"
                            + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                            + "jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "',"
                            + "nip='" + KdPeg.getText() + "'");
                    tampilPemeriksaanSbar();
                    BtnBatalActionPerformed(evt);
                } else {
                    if (akses.getkode().equals(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 11).toString())) {
                        Sequel.mengedit("pemeriksaan_ranap_sbar", "no_rawat='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 1)
                                + "' and tgl_perawatan='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 4)
                                + "' and jam_rawat='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5) + "'",
                                "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation.getText() + "',background='" + TBackground.getText() + "',"
                                + "assesment='" + TAssesment.getText() + "',recommendation='" + TRecommendation.getText() + "'," + "advis='" + TAdvis.getText() + "',"
                                + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                                + "jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'");

                        tampilPemeriksaanSbar();
                        BtnBatalActionPerformed(evt);
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                TCari.requestFocus();
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed

}//GEN-LAST:event_BtnPrintActionPerformed

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
        tampilPemeriksaanSbar();
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
        tampilPemeriksaanSbar();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampilPemeriksaanSbar();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah2(evt, TCari, cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt, DTPTgl, cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt, cmbJam, cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        //   Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void MnCatatanADIMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanADIMEActionPerformed
        if (tbPemeriksaanSbar.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("diagnosa", Sequel.cariIsi("select kamar_inap.diagnosa_awal from kamar_inap where kamar_inap.diagnosa_awal<>'' and kamar_inap.no_rawat=? ", TNoRw.getText()));
            Valid.MyReportqry("rptFormulirCatatanADIMEGizi.jasper", "report", "::[ Formulir Catatan ADIME Gizi Pasien ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"
                    + "pasien.jk,catatan_adime_gizi.tanggal,catatan_adime_gizi.asesmen,catatan_adime_gizi.diagnosis,"
                    + "catatan_adime_gizi.intervensi,catatan_adime_gizi.monitoring,catatan_adime_gizi.evaluasi,catatan_adime_gizi.instruksi,"
                    + "catatan_adime_gizi.nip,petugas.nama "
                    + "from catatan_adime_gizi inner join reg_periksa on catatan_adime_gizi.no_rawat=reg_periksa.no_rawat "
                    + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join petugas on catatan_adime_gizi.nip=petugas.nip where reg_periksa.no_rawat='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 0).toString() + "'", param);
        }
    }//GEN-LAST:event_MnCatatanADIMEActionPerformed

    private void TSituationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSituationKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TSituationKeyPressed

    private void TBackgroundKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBackgroundKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBackgroundKeyPressed

    private void TAssesmentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAssesmentKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TAssesmentKeyPressed

    private void TRecommendationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRecommendationKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TRecommendationKeyPressed

    private void KdPegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdPegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPegActionPerformed

    private void KdPegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPegKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?", TPegawai, KdPeg.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPegawai1ActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, TSituation);
        }
    }//GEN-LAST:event_KdPegKeyPressed

    private void TPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPegawaiActionPerformed

    private void BtnSeekPegawai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPegawai1ActionPerformed
        akses.setform("RMCatatanSBAR");
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnSeekPegawai1ActionPerformed

    private void tbPemeriksaanSbarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanSbarMouseClicked
        if (tabModePemeriksaanSbar.getRowCount() != 0) {
            try {
                getDataPemeriksaanSbar();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbPemeriksaanSbarMouseClicked

    private void tbPemeriksaanSbarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanSbarKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPemeriksaanSbarKeyReleased

    private void TAdvisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAdvisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TAdvisKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMCatatanSBAR dialog = new RMCatatanSBAR(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeekPegawai1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.TextBox Jabatan;
    private widget.TextBox KdPeg;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCatatanADIME;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextArea TAdvis;
    private widget.TextArea TAssesment;
    private widget.TextArea TBackground;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPegawai;
    private widget.TextArea TRecommendation;
    private widget.TextArea TSituation;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox Umur;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbPemeriksaanSbar;
    // End of variables declaration//GEN-END:variables

    public void tampilPemeriksaanSbar() {
        Valid.tabelKosong(tabModePemeriksaanSbar);
        try {
            ps = koneksi.prepareStatement("select pemeriksaan_ranap_sbar.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                    + "pemeriksaan_ranap_sbar.tgl_perawatan,pemeriksaan_ranap_sbar.jam_rawat,pemeriksaan_ranap_sbar.situation,pemeriksaan_ranap_sbar.background, "
                    + "pemeriksaan_ranap_sbar.assesment,pemeriksaan_ranap_sbar.recommendation,pemeriksaan_ranap_sbar.advis,pemeriksaan_ranap_sbar.nip,pegawai.nama,pegawai.jbtn, "
                    + "IF(vps.no_rawat IS NOT NULL, 'Tervalidasi', 'Belum Divalidasi') as status_validasi "
                    + "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join pemeriksaan_ranap_sbar on pemeriksaan_ranap_sbar.no_rawat=reg_periksa.no_rawat "
                    + "inner join pegawai on pemeriksaan_ranap_sbar.nip=pegawai.nik "
                    + "LEFT JOIN validasi_pemeriksaan_sbar vps ON CONCAT(pemeriksaan_ranap_sbar.no_rawat, pemeriksaan_ranap_sbar.tgl_perawatan, pemeriksaan_ranap_sbar.jam_rawat) = CONCAT(vps.no_rawat, vps.tgl_perawatan, vps.jam_rawat) "
                    + "where pemeriksaan_ranap_sbar.tgl_perawatan between ? and ? "
                    + (TCari.getText().trim().equals("") ? ""
                    : "and (reg_periksa.no_rkm_medis like ? or pemeriksaan_ranap_sbar.no_rawat like ? or pasien.nm_pasien like ? or "
                    + "pemeriksaan_ranap_sbar.situation like ? or pemeriksaan_ranap_sbar.background like ? or pemeriksaan_ranap_sbar.assesment like ? or "
                    + "pemeriksaan_ranap_sbar.recommendation like ? or pemeriksaan_ranap_sbar.advis like ?) ")
                    + "order by pemeriksaan_ranap_sbar.no_rawat,pemeriksaan_ranap_sbar.tgl_perawatan,pemeriksaan_ranap_sbar.jam_rawat desc");
            try {
                // Set parameter sesuai dengan placeholder
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                // Set parameter untuk pencarian jika ada
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(3, "%" + TCari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                    ps.setString(6, "%" + TCari.getText().trim() + "%");
                    ps.setString(7, "%" + TCari.getText().trim() + "%");
                    ps.setString(8, "%" + TCari.getText().trim() + "%");
                    ps.setString(9, "%" + TCari.getText().trim() + "%");                    
                    ps.setString(10, "%" + TCari.getText().trim() + "%");

                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModePemeriksaanSbar.addRow(new Object[]{
                        false, rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getString(14)
                    });
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
        LCount.setText("" + tabModePemeriksaanSbar.getRowCount());
    }

    public void emptTeks() {
        DTPTgl.setDate(new Date());
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        //KdPeg.setText("");
        //TPegawai.setText("");
        Jabatan.setText("");
        TSituation.setText("");
        TBackground.setText("");
        TAssesment.setText("");
        TRecommendation.setText("");        
        TAdvis.setText("");


    }

    private void getData() {
        if (tbPemeriksaanSbar.getSelectedRow() != -1) {
            TNoRw.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 0).toString());
            TNoRM.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 1).toString());
            TPasien.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 2).toString());
            Umur.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 3).toString());
            JK.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 4).toString());
            Valid.SetTgl(DTPTgl, tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString());
            cmbJam.setSelectedItem(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString().substring(11, 13));
            cmbMnt.setSelectedItem(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString().substring(14, 15));
            cmbDtk.setSelectedItem(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString().substring(17, 19));

        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.tgl_registrasi,"
                    + "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umurdaftar") + " " + rs.getString("sttsumur"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg"));
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
            System.out.println("Notif : " + e);
        }
    }

    public void setNoRm(String norwt, Date tgl2, String jenisRawat) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
        tampilPemeriksaanSbar();

    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            if (internalFrame1.getHeight() > 478) {
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH, 450));
                FormInput.setVisible(true);
                ChkInput.setVisible(true);
            } else {
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH, internalFrame1.getHeight() - 172));
                FormInput.setVisible(true);
                ChkInput.setVisible(true);
            }
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getcatatan_adime_gizi());
        BtnHapus.setEnabled(akses.getcatatan_adime_gizi());
        BtnEdit.setEnabled(akses.getcatatan_adime_gizi());
        BtnPrint.setEnabled(akses.getcatatan_adime_gizi());
        
        if(akses.getjml2()>=1){
            KdPeg.setText(akses.getkode());
            TPegawai.setText(pegawai.tampil3(KdPeg.getText()));
            Jabatan.setText(pegawai.tampilJbatan(KdPeg.getText()));
            BtnSeekPegawai1.setEnabled(false);
        }
       
        if (TANGGALMUNDUR.equals("no")) {
            if (!akses.getkode().equals("Admin Utama")) {
                DTPTgl.setEditable(false);
                DTPTgl.setEnabled(false);
                ChkKejadian.setEnabled(false);
                cmbJam.setEnabled(false);
                cmbMnt.setEnabled(false);
                cmbDtk.setEnabled(false);
            }
        }

    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";

                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkKejadian.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkKejadian.isSelected() == false) {
                    nilai_jam = cmbJam.getSelectedIndex();
                    nilai_menit = cmbMnt.getSelectedIndex();
                    nilai_detik = cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
        if ((!TSituation.getText().trim().equals("")) || (!TBackground.getText().trim().equals("")) || (!TAssesment.getText().trim().equals(""))
                || (!TRecommendation.getText().trim().equals(""))) {
            if (tbPemeriksaanSbar.getSelectedRow() > -1) {
                if (akses.getkode().equals("Admin Utama")) {
                    Sequel.mengedit("pemeriksaan_ranap_sbar", "no_rawat='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 1)
                            + "' and tgl_perawatan='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 4)
                            + "' and jam_rawat='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5) + "'",
                            "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation.getText() + "',background='" + TBackground.getText() + "',"
                            + "assesment='" + TAssesment.getText() + "',recommendation='" + TRecommendation.getText() + "',"
                            + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                            + "jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "',"
                            + "nip='" + KdPeg.getText() + "'");
                    tampilPemeriksaanSbar();
                    BtnBatalActionPerformed(null);
                } else {
                    if (akses.getkode().equals(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 10).toString())) {
                        Sequel.mengedit("pemeriksaan_ranap_sbar", "no_rawat='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 1)
                                + "' and tgl_perawatan='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 4)
                                + "' and jam_rawat='" + tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5) + "'",
                                "no_rawat='" + TNoRw.getText() + "',situation='" + TSituation.getText() + "',background='" + TBackground.getText() + "',"
                                + "assesment='" + TAssesment.getText() + "',recommendation='" + TRecommendation.getText() + "',"
                                + "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                                + "jam_rawat='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'");

                        tampilPemeriksaanSbar();
                        BtnBatalActionPerformed(null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                TCari.requestFocus();
            }
        }
    }

    private void hapus() {
        if (Sequel.queryu2tf("delete from catatan_adime_gizi where tanggal=? and no_rawat=?", 2, new String[]{
            tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString(), tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 0).toString()
        }) == true) {
            tabModePemeriksaanSbar.removeRow(tbPemeriksaanSbar.getSelectedRow());
            LCount.setText("" + tabModePemeriksaanSbar.getRowCount());
            emptTeks();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void simpan() {
        /*    if(Sequel.menyimpantf("catatan_adime_gizi","?,?,?,?,?,?,?,?,?","Data",9,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),Asesmen.getText(),Diagnosis.getText(),Intervensi.getText(),Monitoring.getText(),Evaluasi.getText(),Instruksi.getText(),KdPetugas.getText()
        })==true){
            tabModePemeriksaanSbar.addRow(new String[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                Asesmen.getText(),Diagnosis.getText(),Intervensi.getText(),Monitoring.getText(),Evaluasi.getText(),Instruksi.getText(),KdPetugas.getText(),NmPetugas.getText()
            });
            LCount.setText(""+tabModePemeriksaanSbar.getRowCount());
            emptTeks();
        } 
    } */
    }

    private void getDataPemeriksaanSbar() {
        if (tbPemeriksaanSbar.getSelectedRow() != -1) {
            TNoRw.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 1).toString());
            TNoRM.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 2).toString());
            TPasien.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 3).toString());
            TSituation.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 6).toString());
            TBackground.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 7).toString());
            TAssesment.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 8).toString());
            TRecommendation.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 9).toString());            
            TAdvis.setText(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 10).toString());
            cmbJam.setSelectedItem(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 5).toString().substring(6, 8));
            Valid.SetTgl(DTPTgl, tbPemeriksaanSbar.getValueAt(tbPemeriksaanSbar.getSelectedRow(), 4).toString());
        }
    }

}

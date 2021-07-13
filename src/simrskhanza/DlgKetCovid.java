/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
* DlgPasienMati.java
*
* Created on Aug 30, 2010, 7:46:01 AM
*/
package simrskhanza;

import fungsi.WarnaTable;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgKamar;

/**
 *
 * @author dosen3
 */
public class DlgKetCovid extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private DlgPasien pasien = new DlgPasien(null, false);
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql = " pasien_mati.no_rkm_medis=pasien.no_rkm_medis  ";
    public DlgKamar kamar = new DlgKamar(null, false);

    /**
     * Creates new form DlgPasienMati
     *
     * @param frame
     * @param bln
     */
    public DlgKetCovid(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10, 2);
        setSize(628, 674);

        Object[] row = { "dari tanggal", "sampai tanggal", "No.RM", "Nama Pasien", "J.K.", "Tmp.Lahir", "Tgl.Lahir",
                "Alamat", "Kamar", "", "No.Rawat", "No.Surat" };

        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbCovid.setModel(tabMode);

        // tbObat.setDefaultRenderer(Object.class, new
        // WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbCovid.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbCovid.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbCovid.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(100);
                    break;
                case 1:
                    column.setPreferredWidth(100);
                    break;
                case 2:
                    column.setPreferredWidth(75);
                    break;
                case 3:
                    column.setPreferredWidth(150);
                    break;
                case 4:
                    column.setPreferredWidth(30);
                    break;
                case 5:
                    column.setPreferredWidth(120);
                    break;
                case 6:
                    column.setPreferredWidth(75);
                    break;
                case 7:
                    column.setPreferredWidth(200);
                    break;
                case 8:
                    column.setPreferredWidth(90);
                    break;
                case 9:
                    column.setPreferredWidth(120);
                    break;
                case 10:
                    column.setMaxWidth(90);
                    column.setMinWidth(90);
                    break;
                case 11:
                    column.setMaxWidth(90);
                    column.setMinWidth(90);
                    break;
                default:
                    column.setMaxWidth(90);
                    column.setMinWidth(90);
                    break;
            }
        }
        tbCovid.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRM.setDocument(new batasInput((byte) 15).getKata(TNoRM));
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
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgPasienMati")) {
                    if (pasien.getTable().getSelectedRow() != -1) {
                        TNoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(), 1).toString());
                        TPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(), 2).toString());
                    }
                    if (pasien.getTable2().getSelectedRow() != -1) {
                        TNoRM.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(), 1).toString());
                        TPasien.setText(
                                pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(), 2).toString());
                    }
                    if (pasien.getTable3().getSelectedRow() != -1) {
                        TNoRM.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(), 1).toString());
                        TPasien.setText(
                                pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(), 2).toString());
                    }
                    TNoRM.requestFocus();
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

        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgPasienMati")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        pasien.getTable2().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgPasienMati")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        pasien.getTable3().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgPasienMati")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
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
                if (kamar.bangsal.getTable().getSelectedRow() != -1) {
                    kdKamar.setText(kamar.bangsal.getTable().getValueAt(kamar.bangsal.getTable().getSelectedRow(), 0)
                            .toString());
                    nmKamar.setText(kamar.bangsal.getTable().getValueAt(kamar.bangsal.getTable().getSelectedRow(), 1)
                            .toString());
                }
                TNoRM.requestFocus();
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakKetCovid = new javax.swing.JMenuItem();
        TNoSurat1 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbCovid = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnUbah = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel18 = new widget.Label();
        DTPTgl1 = new widget.Tanggal();
        jLabel19 = new widget.Label();
        DTPTgl2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelBiasa1 = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        BtnSeek = new widget.Button();
        jLabel10 = new widget.Label();
        TNorawat = new widget.TextBox();
        jLabel16 = new widget.Label();
        TNoSurat = new widget.TextBox();
        jLabel17 = new widget.Label();
        kdKamar = new widget.TextBox();
        nmKamar = new widget.TextBox();
        BtnKamar = new widget.Button();
        jLabel20 = new widget.Label();
        DTPTgl3 = new widget.Tanggal();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakKetCovid.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakKetCovid.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakKetCovid.setForeground(java.awt.Color.darkGray);
        MnCetakKetCovid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakKetCovid.setText("Surat Keterangan Covid");
        MnCetakKetCovid.setActionCommand("Cetak Surat Keterangan Covid");
        MnCetakKetCovid.setName("MnCetakKetCovid"); // NOI18N
        MnCetakKetCovid.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakKetCovid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakKetCovidActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakKetCovid);

        TNoSurat1.setEnabled(false);
        TNoSurat1.setHighlighter(null);
        TNoSurat1.setName("TNoSurat1"); // NOI18N
        TNoSurat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoSurat1KeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pasien COVID ]::",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbCovid.setAutoCreateRowSorter(true);
        tbCovid.setComponentPopupMenu(jPopupMenu1);
        tbCovid.setName("tbCovid"); // NOI18N
        tbCovid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCovidMouseClicked(evt);
            }
        });
        tbCovid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbCovidKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbCovid);

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

        BtnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/peminjaman.png"))); // NOI18N
        BtnUbah.setMnemonic('S');
        BtnUbah.setText("Ubah");
        BtnUbah.setToolTipText("Alt+S");
        BtnUbah.setName("BtnUbah"); // NOI18N
        BtnUbah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUbahActionPerformed(evt);
            }
        });
        BtnUbah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnUbahKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnUbah);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel18.setText("Tanggal : ");
        jLabel18.setName("jLabel18"); // NOI18N
        panelGlass9.add(jLabel18);

        DTPTgl1.setEditable(false);
        DTPTgl1.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-07-2021" }));
        DTPTgl1.setDisplayFormat("dd-MM-yyyy");
        DTPTgl1.setName("DTPTgl1"); // NOI18N
        DTPTgl1.setOpaque(false);
        DTPTgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTgl1KeyPressed(evt);
            }
        });
        panelGlass9.add(DTPTgl1);

        jLabel19.setText("s/d");
        jLabel19.setName("jLabel19"); // NOI18N
        panelGlass9.add(jLabel19);

        DTPTgl2.setEditable(false);
        DTPTgl2.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-07-2021" }));
        DTPTgl2.setDisplayFormat("dd-MM-yyyy");
        DTPTgl2.setName("DTPTgl2"); // NOI18N
        DTPTgl2.setOpaque(false);
        DTPTgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTgl2KeyPressed(evt);
            }
        });
        panelGlass9.add(DTPTgl2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(610, 100));
        panelBiasa1.setLayout(null);

        jLabel4.setText("Pasien :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelBiasa1.add(jLabel4);
        jLabel4.setBounds(10, 70, 115, 23);

        TPasien.setEditable(false);
        TPasien.setEnabled(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        panelBiasa1.add(TPasien);
        TPasien.setBounds(363, 70, 270, 23);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-07-2021" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        panelBiasa1.add(DTPTgl);
        DTPTgl.setBounds(130, 40, 100, 23);

        TNoRM.setEnabled(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        panelBiasa1.add(TNoRM);
        TNoRM.setBounds(130, 70, 90, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("Alt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        BtnSeek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeekKeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek);
        BtnSeek.setBounds(640, 70, 28, 23);

        jLabel10.setText("Kamar :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelBiasa1.add(jLabel10);
        jLabel10.setBounds(270, 10, 60, 23);

        TNorawat.setEnabled(false);
        TNorawat.setHighlighter(null);
        TNorawat.setName("TNorawat"); // NOI18N
        TNorawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNorawatKeyPressed(evt);
            }
        });
        panelBiasa1.add(TNorawat);
        TNorawat.setBounds(220, 70, 140, 23);

        jLabel16.setText("Tgl. Awal :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelBiasa1.add(jLabel16);
        jLabel16.setBounds(10, 40, 115, 23);

        TNoSurat.setEnabled(false);
        TNoSurat.setHighlighter(null);
        TNoSurat.setName("TNoSurat"); // NOI18N
        TNoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoSuratKeyPressed(evt);
            }
        });
        panelBiasa1.add(TNoSurat);
        TNoSurat.setBounds(130, 10, 140, 23);

        jLabel17.setText("No. Surat :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelBiasa1.add(jLabel17);
        jLabel17.setBounds(10, 10, 115, 23);

        kdKamar.setHighlighter(null);
        kdKamar.setName("kdKamar"); // NOI18N
        kdKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdKamarKeyPressed(evt);
            }
        });
        panelBiasa1.add(kdKamar);
        kdKamar.setBounds(330, 10, 90, 23);

        nmKamar.setEnabled(false);
        nmKamar.setHighlighter(null);
        nmKamar.setName("nmKamar"); // NOI18N
        nmKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmKamarKeyPressed(evt);
            }
        });
        panelBiasa1.add(nmKamar);
        nmKamar.setBounds(420, 10, 210, 23);

        BtnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKamar.setMnemonic('1');
        BtnKamar.setToolTipText("Alt+1");
        BtnKamar.setName("BtnKamar"); // NOI18N
        BtnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKamarActionPerformed(evt);
            }
        });
        BtnKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKamarKeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnKamar);
        BtnKamar.setBounds(640, 10, 30, 23);

        jLabel20.setText("Tgl. Akhir :");
        jLabel20.setName("jLabel20"); // NOI18N
        panelBiasa1.add(jLabel20);
        jLabel20.setBounds(240, 40, 70, 23);

        DTPTgl3.setEditable(false);
        DTPTgl3.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-07-2021" }));
        DTPTgl3.setDisplayFormat("dd-MM-yyyy");
        DTPTgl3.setName("DTPTgl3"); // NOI18N
        DTPTgl3.setOpaque(false);
        DTPTgl3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTgl3KeyPressed(evt);
            }
        });
        panelBiasa1.add(DTPTgl3);
        DTPTgl3.setBounds(320, 40, 110, 23);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPTglKeyPressed

    }// GEN-LAST:event_DTPTglKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSeekActionPerformed
        akses.setform("DlgPasienCovid");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }// GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnSeekKeyPressed

    }// GEN-LAST:event_BtnSeekKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRM.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "pasien");
        } else {
            // simpan
            if (simpan()) {
                JOptionPane.showMessageDialog(rootPane, "Simpan Sukses...");
            }
            tampil();
            emptTeks();
        }
    }// GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        }
    }// GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }// GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }// GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode, TNorawat, "surat_ket_covid", "surat_ket_covid.no_rawat");
        tampil();
        emptTeks();
    }// GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            // Valid.pindah(evt, BtnBatal, BtnPrint);
        }
    }// GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }// GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            // Valid.pindah(evt, BtnPrint, TCari);
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
        tampil();
    }// GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
    }// GEN-LAST:event_BtnAllKeyPressed

    private void tbCovidMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbCovidMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }// GEN-LAST:event_tbCovidMouseClicked

    private void MnCetakKetCovidActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MnCetakKetCovidActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TCari.getText().trim().equals("")) {
            BtnCariActionPerformed(evt);
        }
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptSkCovid.jasper", "report", "::[ Data Pasien COVID ]::",
                    "select surat_ket_covid.no_surat,surat_ket_covid.no_rawat,surat_ket_covid.tgl_awal,surat_ket_covid.tgl_ahir, "
                            + "pasien.no_rkm_medis, pasien.no_ktp, pasien.nm_pasien, pasien.jk, pasien.tmp_lahir,pasien.tgl_lahir, "
                            + "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab)as alamat, surat_ket_covid.bangsal "
                            + "from surat_ket_covid "
                            + "inner join kamar_inap on kamar_inap.no_rawat=surat_ket_covid.no_rawat "
                            + "inner join reg_periksa on reg_periksa.no_rawat = kamar_inap.no_rawat "
                            + "inner join pasien on pasien.no_rkm_medis = reg_periksa.no_rkm_medis "
                            + "left join kelurahan on kelurahan.kd_kel = pasien.kd_kel "
                            + "left join kecamatan on kecamatan.kd_kec = pasien.kd_kec "
                            + "left join kabupaten on kabupaten.kd_kab = pasien.kd_kab " + "where "
                            + " surat_ket_covid.no_rawat like '%" + TCari.getText().trim() + "%' "
                            + " order by tanggal ",
                    param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }// GEN-LAST:event_MnCetakKetCovidActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowOpened
        tampil();
        noSurat();
    }// GEN-LAST:event_formWindowOpened

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TNoRMKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekActionPerformed(null);
        }
    }// GEN-LAST:event_TNoRMKeyPressed

    private void TNorawatKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TNorawatKeyPressed

    }// GEN-LAST:event_TNorawatKeyPressed

    private void tbCovidKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tbCovidKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP)
                    || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }// GEN-LAST:event_tbCovidKeyReleased

    private void TNoSuratKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TNoSuratKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TNoSuratKeyPressed

    private void kdKamarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_kdKamarKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_kdKamarKeyPressed

    private void nmKamarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_nmKamarKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_nmKamarKeyPressed

    private void BtnKamarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnKamarActionPerformed
        // TODO add your handling code here:
        kamar.bangsal.isCek();
        kamar.bangsal.emptTeks();
        kamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kamar.bangsal.setLocationRelativeTo(internalFrame1);
        kamar.bangsal.setVisible(true);
    }// GEN-LAST:event_BtnKamarActionPerformed

    private void BtnKamarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnKamarKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_BtnKamarKeyPressed

    private void DTPTgl1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPTgl1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_DTPTgl1KeyPressed

    private void DTPTgl2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPTgl2KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_DTPTgl2KeyPressed

    private void DTPTgl3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_DTPTgl3KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_DTPTgl3KeyPressed

    private void TNoSurat1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_TNoSurat1KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_TNoSurat1KeyPressed

    private void BtnUbahActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_BtnUbahActionPerformed
        // TODO add your handling code here:
        if (TNoRM.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "pasien");
        } else {
            ubahData();
            tampil();
            emptTeks();
        }
    }// GEN-LAST:event_BtnUbahActionPerformed

    private void BtnUbahKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_BtnUbahKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_BtnUbahKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKetCovid dialog = new DlgKetCovid(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKamar;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek;
    private widget.Button BtnSimpan;
    private widget.Button BtnUbah;
    private widget.Tanggal DTPTgl;
    private widget.Tanggal DTPTgl1;
    private widget.Tanggal DTPTgl2;
    private widget.Tanggal DTPTgl3;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakKetCovid;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoSurat;
    private widget.TextBox TNoSurat1;
    private widget.TextBox TNorawat;
    private widget.TextBox TPasien;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdKamar;
    private widget.TextBox nmKamar;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbCovid;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    public void tampil() {
        Valid.tabelKosong(tabMode);
        String tgl = " and surat_ket_covid.tanggal between '" + Valid.SetDateToString(DTPTgl1.getDate()) + "' and '"
                + Valid.SetDateToString(DTPTgl2.getDate()) + "' ";
        String tgl1 = " surat_ket_covid.tanggal between '" + Valid.SetDateToString(DTPTgl1.getDate()) + "' and '"
                + Valid.SetDateToString(DTPTgl2.getDate()) + "' ";
        try {
            if (!TCari.getText().equals("")) {
                ps = koneksi.prepareStatement("select surat_ket_covid.tgl_awal,surat_ket_covid.tgl_ahir, "
                        + "pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tmp_lahir,pasien.tgl_lahir, "
                        + "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab)as alamat, surat_ket_covid.kd_bangsal, surat_ket_covid.bangsal,surat_ket_covid.no_rawat ,surat_ket_covid.no_surat "
                        + "from surat_ket_covid "
                        + "inner join kamar_inap on kamar_inap.no_rawat=surat_ket_covid.no_rawat "
                        + "inner join reg_periksa on reg_periksa.no_rawat = kamar_inap.no_rawat "
                        + "inner join pasien on pasien.no_rkm_medis = reg_periksa.no_rkm_medis "
                        + "left join kelurahan on kelurahan.kd_kel = pasien.kd_kel "
                        + "left join kecamatan on kecamatan.kd_kec = pasien.kd_kec "
                        + "left join kabupaten on kabupaten.kd_kab = pasien.kd_kab "
                        + "where surat_ket_covid.no_rawat = '" + TCari.getText() + "'" + tgl + " or "
                        + "reg_periksa.no_rkm_medis = '" + TCari.getText() + "'" + tgl + " "
                        + "group by surat_ket_covid.no_rawat " + "order by surat_ket_covid.tanggal");
            } else {
                ps = koneksi.prepareStatement("select surat_ket_covid.tgl_awal,surat_ket_covid.tgl_ahir, "
                        + "pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tmp_lahir,pasien.tgl_lahir, "
                        + "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab)as alamat, surat_ket_covid.kd_bangsal, surat_ket_covid.bangsal,surat_ket_covid.no_rawat,surat_ket_covid.no_surat "
                        + "from surat_ket_covid "
                        + "inner join kamar_inap on kamar_inap.no_rawat=surat_ket_covid.no_rawat "
                        + "inner join reg_periksa on reg_periksa.no_rawat = kamar_inap.no_rawat "
                        + "inner join pasien on pasien.no_rkm_medis = reg_periksa.no_rkm_medis "
                        + "left join kelurahan on kelurahan.kd_kel = pasien.kd_kel "
                        + "left join kecamatan on kecamatan.kd_kec = pasien.kd_kec "
                        + "left join kabupaten on kabupaten.kd_kab = pasien.kd_kab " + "where " + tgl1 + " "
                        + "group by surat_ket_covid.no_rawat " + "order by surat_ket_covid.tanggal");
            }
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                            rs.getString(10), rs.getString(11), rs.getString(12) });
                }
            } catch (Exception e) {
                System.out.println("simrskhanza.KetCovid.tampil() : " + e);
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

    /**
     *
     */
    public void emptTeks() {
        TNoRM.setText("");
        TPasien.setText("");
        TNorawat.setText("");
        kdKamar.setText("");
        nmKamar.setText("");
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
    }

    private void getData() {
        if (tbCovid.getSelectedRow() != -1) {
            TNoRM.setText(tbCovid.getValueAt(tbCovid.getSelectedRow(), 2).toString());
            kdKamar.setText(tbCovid.getValueAt(tbCovid.getSelectedRow(), 8).toString());
            nmKamar.setText(tbCovid.getValueAt(tbCovid.getSelectedRow(), 9).toString());
            TPasien.setText(tbCovid.getValueAt(tbCovid.getSelectedRow(), 3).toString());
            Valid.SetTgl(DTPTgl, tbCovid.getValueAt(tbCovid.getSelectedRow(), 0).toString());
            TNorawat.setText(tbCovid.getValueAt(tbCovid.getSelectedRow(), 10).toString());
        }
    }

    /**
     *
     */
    public void isCek() {
        // BtnSimpan.setEnabled(akses.getpasien_meninggal());
        // BtnHapus.setEnabled(akses.getpasien_meninggal());
        // BtnPrint.setEnabled(akses.getpasien_meninggal());
    }

    /**
     *
     * @param norm
     */
    public void setNoRm(String norm, String no_rawat, String nama, String kamar) {
        TNoRM.setText(norm);
        TNorawat.setText(no_rawat);
        TPasien.setText(nama);
        TCari.setText(no_rawat);
        kamar(kamar);
        noSurat();
        BtnSeek.setEnabled(false);
    }

    private void kamar(String kd_kamar) {
        // String nama = Sequel.cariIsi("select nm_bangsal from bangsal where
        // kd_bangsal='"+kd_kamar+"'");
        kdKamar.setText(kd_kamar);
        nmKamar.setText(Sequel.cariIsi(
                "select nm_bangsal from bangsal inner join kamar on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar='"
                        + kd_kamar + "'"));
    }

    private void noSurat() {
        Valid.newAutoNomerPM("select ifnull(MAX(no_surat),0) from surat_ket_covid where YEAR(tanggal)='"
                + Valid.SetDateToString(DTPTgl.getDate()).substring(0, 4) + "' ", "", 5, TNoSurat);
    }

    private boolean simpan() {
        String bln = Valid.SetDateToString(DTPTgl.getDate()).substring(5, 7);
        String thn = Valid.SetDateToString(DTPTgl.getDate()).substring(0, 4);
        String nomor = TNoSurat.getText() + "/RSUIHA/RI/" + Valid.RomanNumerals(Integer.parseInt(bln)) + "/" + thn;
        System.out.println("nomor = " + nomor);

        boolean status = Sequel.menyimpantf("surat_ket_covid",
                "'" + nomor + "','" + TNorawat.getText() + "','" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "','"
                        + Valid.SetTgl(DTPTgl3.getSelectedItem() + "") + "','"
                        + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "','" + nmKamar.getText() + "','"
                        + kdKamar.getText() + "'",
                "Simpan Covid");

        return status;
    }

    private void ubahData() {
        Valid.editTable(tabMode, "surat_ket_covid", "no_rawat", TNorawat,
                "tgl_awal='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',tgl_ahir='"
                        + Valid.SetTgl(DTPTgl3.getSelectedItem() + "") + "',tanggal='"
                        + Valid.SetDateToString(new Date()) + "',bangsal='" + nmKamar.getText() + "',kd_bangsal='"
                        + kdKamar.getText() + "'");
    }
}
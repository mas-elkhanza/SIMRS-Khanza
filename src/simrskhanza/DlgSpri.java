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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgCariPerawatanRanap;

/**
 *
 * @author dosen3
 */
public class DlgSpri extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private DlgPasien pasien = new DlgPasien(null, false);
    private PreparedStatement ps, psdiagnosa;
    private ResultSet rs;
    public DlgCariPerawatanRanap perawatan = new DlgCariPerawatanRanap(null, false);
    public DlgCariPenyakit diagnosa = new DlgCariPenyakit(null, false);
    private String sql = " spri.norm=pasien.no_rkm_medis  ";
    private String status = "";
    private String id = "";
    private final Connection connect = koneksiDB.condb();

    /**
     * Creates new form DlgPasienMati
     *
     * @param parent
     * @param modal
     */
    public DlgSpri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10, 2);
        setSize(628, 674);

        Object[] row = {"Tanggal", "Jam", "No.R.Medik", "Nama Pasien", "J.K.", "Tmp.Lahir",
            "Tgl.Lahir", "G.D.", "Stts.Nikah", "Agama", "Rencana Perawatan", "Ruangan",
            "Dokter", "Diagnosa", "kd_dokter", "kd_penyakit", "nama", "keluhan", "nm_penyakit", "id"};

        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbSpri.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSpri.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbSpri.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 20; i++) {
            TableColumn column = tbSpri.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);//tanggal
            } else if (i == 1) {
                column.setPreferredWidth(60);//jam
            } else if (i == 2) {
                column.setPreferredWidth(80);//norm
            } else if (i == 3) {
                column.setPreferredWidth(150);//nama
            } else if (i == 4) {
                column.setPreferredWidth(30);//jk
            } else if (i == 5) {
                column.setPreferredWidth(120);//ttl
            } else if (i == 6) {
                column.setPreferredWidth(75);//tgllahir
            } else if (i == 7) {
                column.setPreferredWidth(30);//gd
            } else if (i == 8) {
                column.setPreferredWidth(90);//stt nikah
            } else if (i == 9) {
                column.setPreferredWidth(90);//agama
            } else if (i == 10) {
                column.setPreferredWidth(120);//rencana perawatan
            } else if (i == 11) {
                column.setPreferredWidth(100);//ruangan
            } else if (i == 12) {
                column.setPreferredWidth(100);//dokter
            } else if (i == 13) {
                column.setPreferredWidth(200);//diagnosa
            } else if (i == 14) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 15) {
                column.setMinWidth(0);
                column.setMaxWidth(0);

            } else if (i == 16) {
                column.setMinWidth(0);
                column.setMaxWidth(0);

            } else if (i == 17) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 18) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 19) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }

        tbSpri.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRM.setDocument(new batasInput((byte) 15).getKata(TNoRM));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        txtRencanaPerawatan.setDocument(new batasInput((byte) 100).getKata(txtRencanaPerawatan));
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
                        TPasien.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(), 2).toString());
                    }
                    if (pasien.getTable3().getSelectedRow() != -1) {
                        TNoRM.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(), 1).toString());
                        TPasien.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(), 2).toString());
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

        perawatan.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatInap")) {
                    if (perawatan.dokter.getTable().getSelectedRow() != -1) {
                        txtKdDokter.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(), 0).toString());
                        txtNmDokter.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(), 1).toString());
                        txtRencanaPerawatan.requestFocus();

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

        diagnosa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (diagnosa.getTable().getSelectedRow() != -1) {
                    txtKdPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(), 0).toString());
                    txtNmPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(), 1).toString());
                    cmbUpf.requestFocus();
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
//        
//        Valid.setPlaceHolder(TPasien, "Nama Pasien");
//        Valid.setPlaceHolder(TNoRM, "No. RM");
//        Valid.setPlaceHolder(txtKdKamar, "Kode Kamar");
//        Valid.setPlaceHolder(txtNmKamar, "Nama Kamar");
//        Valid.setPlaceHolder(txtKdDokter, "Kode Dokter");
//        Valid.setPlaceHolder(txtNmDokter, "Nama Dokter");
//        Valid.setPlaceHolder(txtKdPenyakit, "Kode Penyakit");
//        Valid.setPlaceHolder(txtNmPenyakit, "Nama Penyakit");
    }

    public void setForm(String status) {
        this.status = status;
    }

    public void setPlaceHolder(JTextField teks, String pesan) {
        teks.setText(pesan);
        teks.setForeground(Color.GRAY);
        teks.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (teks.getText().equals("")) {
                    teks.setText(pesan);
                    teks.setForeground(Color.GRAY);
                } else if (teks.getText().equals(pesan)) {
                    teks.setText("");
                    teks.setForeground(Color.getColor("#323232"));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (teks.getText().equals(pesan) || teks.getText().equals("")) {
                    teks.setText(pesan);
                    teks.setForeground(Color.GRAY);
                }
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakSuratMati = new javax.swing.JMenuItem();
        MnAngkutJenazah = new javax.swing.JMenuItem();
        txtId = new javax.swing.JTextField();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbSpri = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        btnGanti = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelBiasa1 = new widget.PanelBiasa();
        jLabel8 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel9 = new widget.Label();
        txtRencanaPerawatan = new widget.TextBox();
        TPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        BtnSeek = new widget.Button();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        jLabel10 = new widget.Label();
        jLabel16 = new widget.Label();
        txtNmPenyakit = new widget.TextBox();
        txtKdPenyakit = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        jLabel17 = new widget.Label();
        txtKdDokter = new widget.TextBox();
        txtNmDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        cmbUpf = new widget.ComboBox();
        jLabel5 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSuratMati.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratMati.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratMati.setForeground(java.awt.Color.darkGray);
        MnCetakSuratMati.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratMati.setText("Surat Kematian");
        MnCetakSuratMati.setName("MnCetakSuratMati"); // NOI18N
        MnCetakSuratMati.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakSuratMati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratMatiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratMati);

        MnAngkutJenazah.setBackground(new java.awt.Color(255, 255, 254));
        MnAngkutJenazah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAngkutJenazah.setForeground(java.awt.Color.darkGray);
        MnAngkutJenazah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAngkutJenazah.setText("Surat Angkut Jenazah");
        MnAngkutJenazah.setName("MnAngkutJenazah"); // NOI18N
        MnAngkutJenazah.setPreferredSize(new java.awt.Dimension(250, 28));
        MnAngkutJenazah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAngkutJenazahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnAngkutJenazah);

        txtId.setText("jTextField1");
        txtId.setName("txtId"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Surat Permintaan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbSpri.setAutoCreateRowSorter(true);
        tbSpri.setComponentPopupMenu(jPopupMenu1);
        tbSpri.setName("tbSpri"); // NOI18N
        tbSpri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSpriMouseClicked(evt);
            }
        });
        tbSpri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbSpriKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbSpri);

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

        btnGanti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        btnGanti.setText("Ganti");
        btnGanti.setName("btnGanti"); // NOI18N
        btnGanti.setPreferredSize(new java.awt.Dimension(100, 30));
        btnGanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGantiActionPerformed(evt);
            }
        });
        panelGlass8.add(btnGanti);

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

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(500, 23));
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
        panelBiasa1.setPreferredSize(new java.awt.Dimension(610, 140));
        panelBiasa1.setLayout(null);

        jLabel8.setText("Jam :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelBiasa1.add(jLabel8);
        jLabel8.setBounds(209, 10, 39, 23);

        jLabel4.setText("Ruang Perawatan :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelBiasa1.add(jLabel4);
        jLabel4.setBounds(0, 70, 115, 20);

        jLabel9.setText("Rencana Perawatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelBiasa1.add(jLabel9);
        jLabel9.setBounds(0, 100, 115, 23);

        txtRencanaPerawatan.setHighlighter(null);
        txtRencanaPerawatan.setName("txtRencanaPerawatan"); // NOI18N
        txtRencanaPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRencanaPerawatanKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtRencanaPerawatan);
        txtRencanaPerawatan.setBounds(120, 100, 590, 23);

        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        panelBiasa1.add(TPasien);
        TPasien.setBounds(240, 40, 210, 23);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-04-2020" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        panelBiasa1.add(DTPTgl);
        DTPTgl.setBounds(118, 10, 90, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        panelBiasa1.add(TNoRM);
        TNoRM.setBounds(118, 40, 120, 23);

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
        BtnSeek.setBounds(450, 40, 28, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbJam);
        cmbJam.setBounds(251, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbMnt);
        cmbMnt.setBounds(316, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbDtk);
        cmbDtk.setBounds(381, 10, 62, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelBiasa1.add(jLabel10);
        jLabel10.setBounds(0, 10, 115, 23);

        jLabel16.setText("Dokter :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelBiasa1.add(jLabel16);
        jLabel16.setBounds(500, 70, 80, 23);

        txtNmPenyakit.setHighlighter(null);
        txtNmPenyakit.setName("txtNmPenyakit"); // NOI18N
        txtNmPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNmPenyakitKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtNmPenyakit);
        txtNmPenyakit.setBounds(710, 40, 210, 23);

        txtKdPenyakit.setHighlighter(null);
        txtKdPenyakit.setName("txtKdPenyakit"); // NOI18N
        txtKdPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdPenyakitKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtKdPenyakit);
        txtKdPenyakit.setBounds(590, 40, 120, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('1');
        BtnSeek1.setToolTipText("Alt+1");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        BtnSeek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek1KeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek1);
        BtnSeek1.setBounds(920, 40, 28, 23);

        jLabel17.setText("Diagnosa :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelBiasa1.add(jLabel17);
        jLabel17.setBounds(500, 40, 80, 23);

        txtKdDokter.setHighlighter(null);
        txtKdDokter.setName("txtKdDokter"); // NOI18N
        txtKdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdDokterKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtKdDokter);
        txtKdDokter.setBounds(590, 70, 120, 23);

        txtNmDokter.setEditable(false);
        txtNmDokter.setHighlighter(null);
        txtNmDokter.setName("txtNmDokter"); // NOI18N
        txtNmDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNmDokterKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtNmDokter);
        txtNmDokter.setBounds(710, 70, 210, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('1');
        BtnSeek3.setToolTipText("Alt+1");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek3);
        BtnSeek3.setBounds(920, 70, 28, 23);

        cmbUpf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kebidanan", "PD", "PD (Kemuning)", "Kesehatan Anak", "Bedah", "Isolasi", "Sakura", "HCU", "ICU", "ICCU", "PICU", "NICU", "Perinatologi" }));
        cmbUpf.setName("cmbUpf"); // NOI18N
        cmbUpf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbUpfKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbUpf);
        cmbUpf.setBounds(120, 70, 330, 23);

        jLabel5.setText("Pasien :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelBiasa1.add(jLabel5);
        jLabel5.setBounds(0, 40, 115, 23);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt, TCari, cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        akses.setform("DlgPasienMati");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt, TNoRM, txtRencanaPerawatan);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt, DTPTgl, cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt, cmbJam, cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt, cmbMnt, TNoRM);
}//GEN-LAST:event_cmbDtkKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "pasien");
        } else if (txtRencanaPerawatan.getText().trim().equals("")) {
            Valid.textKosong(txtRencanaPerawatan, "Rencana Perawatan");
        } else {
            if (status.equals("spriIgd")) {
                Sequel.menyimpan("temp_spri", "" + null + ",'" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "','"
                        + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "','"
                        + TNoRM.getText() + "','"
                        + txtKdPenyakit.getText() + "','"
                        + txtRencanaPerawatan.getText() + "','"
                        + cmbUpf.getSelectedItem() + "','"
                        + txtKdDokter.getText() + "','" + TPasien.getText() + "','" + txtNmPenyakit.getText() + "'", "spri");
            }
//            else {
//                Sequel.menyimpan("spri", "'" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "','"
//                        + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "','"
//                        + TNoRM.getText() + "','"
//                        + txtKdPenyakit.getText() + "','"
//                        + txtRencanaPerawatan.getText() + "','"
//                        + txtKdKamar.getText() + "','"
//                        + txtKdDokter.getText() + "'", "spri");
//            }
            emptTeks();
            tampil();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, txtRencanaPerawatan, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (TNoRM.getText().toString().trim().equals("")) {
            Valid.hapusTable(tabMode, txtId, "temp_spri", "temp_spri.id");
        } else if (!TNoRM.getText().toString().trim().equals("")) {
            Valid.hapusTable(tabMode, TNoRM, "temp_spri", "temp_spri.norm");
        }
        emptTeks();
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TCari.getText().trim().equals("") || TNoRM.getText().trim().equals("")) {
            BtnCariActionPerformed(evt);
        }
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            if (!TNoRM.getText().trim().toString().equals("")) {
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                param.put("ttd", Sequel.cariGambar("select ttd from ttd_dokter where kd_dokter ='" + txtKdDokter.getText() + "'"));
                System.out.println("ttd = "+Sequel.cariGambar("select logo from setting"));
                //param.put("ttd", "./setting/" + Sequel.cariIsi("select ttd from ttd_dokter where kd_dokter='" + txtKdDokter.getText() + "'"));
                Valid.MyReportqry("rptSpri.jasper", "report", "::[ Surat Laporan Rawat Inap ]::",
                        "SELECT temp_spri.tanggal,temp_spri.jam,temp_spri.norm,if(temp_spri.norm='',temp_spri.nama,pasien.nm_pasien) as nm_pasien,pasien.alamat, "
                        + "CASE WHEN pasien.jk='' THEN '' WHEN pasien.jk='L' THEN 'Laki-laki' WHEN pasien.jk='P' THEN 'Perempuan' END as jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                        + "pasien.agama,temp_spri.rencana_perawatan,temp_spri.upf,dokter.nm_dokter,penyakit.nm_penyakit,temp_spri.kd_dokter,temp_spri.keluhan "
                        + " FROM temp_spri left join pasien on temp_spri.norm=pasien.no_rkm_medis "
                        + "left join dokter on temp_spri.kd_dokter=dokter.kd_dokter "
                        + "left join penyakit on temp_spri.diagnosa=penyakit.kd_penyakit "
                        + " WHERE"
                        //                    + " spri.tanggal like '%" + TCari.getText().trim() + "%' or "
                        //                    + " spri.norm like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.jk like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.tmp_lahir like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.gol_darah like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.stts_nikah like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.agama like '%" + TCari.getText().trim() + "%' or "
                        //                    + " spri.rencana_perawatan like '%" + TCari.getText().trim() + "%' "
                        + " temp_spri.norm = '" + TNoRM.getText().trim() + "' "
                        + " order by temp_spri.tanggal ", param);
            } else if (TNoRM.getText().trim().toString().equals("")) {
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                param.put("ttd", Sequel.cariGambar("select ttd from ttd_dokter where kd_dokter ='" + txtKdDokter.getText() + "'"));
                System.out.println("ttd = "+Sequel.cariGambar("select ttd from ttd_dokter where kd_dokter ='" + txtKdDokter.getText() + "'"));
//                param.put("ttd", "./setting/" + Sequel.cariIsi("select ttd from ttd_dokter where kd_dokter='" + txtKdDokter.getText() + "'"));
                Valid.MyReportqry("rptSpri.jasper", "report", "::[ Surat Laporan Rawat Inap ]::",
                        "SELECT temp_spri.tanggal,temp_spri.jam,temp_spri.norm,if(temp_spri.norm='',temp_spri.nama,pasien.nm_pasien) as nm_pasien,pasien.alamat, "
                        + "CASE WHEN pasien.jk='' THEN '' WHEN pasien.jk='L' THEN 'Laki-laki' WHEN pasien.jk='P' THEN 'Perempuan' END as jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                        + "pasien.agama,temp_spri.rencana_perawatan,temp_spri.upf,dokter.nm_dokter,penyakit.nm_penyakit,temp_spri.kd_dokter,temp_spri.keluhan "
                        + " FROM temp_spri left join pasien on temp_spri.norm=pasien.no_rkm_medis "
                        + "left join dokter on temp_spri.kd_dokter=dokter.kd_dokter "
                        + "left join penyakit on temp_spri.diagnosa=penyakit.kd_penyakit "
                        + " WHERE"
                        //                    + " spri.tanggal like '%" + TCari.getText().trim() + "%' or "
                        //                    + " spri.norm like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.jk like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.tmp_lahir like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.gol_darah like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.stts_nikah like '%" + TCari.getText().trim() + "%' or "
                        //                    + " pasien.agama like '%" + TCari.getText().trim() + "%' or "
                        //                    + " spri.rencana_perawatan like '%" + TCari.getText().trim() + "%' "
                        + " temp_spri.id = '" + id + "' "
                        + " order by temp_spri.tanggal ", param);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TNoRM.setText("");
        TPasien.setText("");
        cmbUpf.setSelectedIndex(0);
        txtKdPenyakit.setText("");
        txtNmPenyakit.setText("");
        txtKdDokter.setText("");
        txtNmDokter.setText("");
        txtRencanaPerawatan.setText("");
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TNoRM.setText("");
            TPasien.setText("");
            cmbUpf.setSelectedIndex(0);
            txtKdPenyakit.setText("");
            txtNmPenyakit.setText("");
            txtKdDokter.setText("");
            txtNmDokter.setText("");
            TCari.setText("");
            txtRencanaPerawatan.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbSpriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSpriMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbSpriMouseClicked

private void MnCetakSuratMatiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratMatiActionPerformed
    if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
    } else {
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        Valid.MyReportqry("rptSuratKematian.jasper", "report", "::[ Surat Kematian ]::",
                "select tanggal,jam,spri.norm,pasien.nm_pasien, "
                + "pasien.umur,pasien.alamat,jk,tmp_lahir,tgl_lahir,gol_darah,stts_nikah, "
                + "agama,keterangan from pasien_mati,pasien "
                + "where spri.norm=pasien.no_rkm_medis "
                + "and spri.norm='" + TNoRM.getText() + "' ", param);
    }
}//GEN-LAST:event_MnCetakSuratMatiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnAngkutJenazahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAngkutJenazahActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptAngkutJenazah.jasper", "report", "::[ Surat Angkut Jenazah ]::",
                    "select tanggal,jam,spri.norm,pasien.nm_pasien,pasien.pekerjaan, "
                    + "pasien.umur,pasien.alamat,jk,tmp_lahir,tgl_lahir,gol_darah,stts_nikah, "
                    + "agama,keterangan from pasien_mati,pasien "
                    + "where spri.norm=pasien.no_rkm_medis "
                    + "and spri.norm='" + TNoRM.getText() + "' ", param);
        }
    }//GEN-LAST:event_MnAngkutJenazahActionPerformed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRM, cmbUpf);
        }
    }//GEN-LAST:event_TNoRMKeyPressed

    private void txtRencanaPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRencanaPerawatanKeyPressed
        Valid.pindah(evt, txtRencanaPerawatan, BtnSimpan);
    }//GEN-LAST:event_txtRencanaPerawatanKeyPressed

    private void tbSpriKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSpriKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbSpriKeyReleased

    private void txtNmPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNmPenyakitKeyPressed

    private void txtKdPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdPenyakitKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ", txtNmPenyakit, txtKdPenyakit.getText());

            Valid.pindah(evt, txtKdPenyakit, txtKdDokter);
        }
    }//GEN-LAST:event_txtKdPenyakitKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        // TODO add your handling code here:
        diagnosa.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        diagnosa.setLocationRelativeTo(internalFrame1);
        diagnosa.setVisible(true);
    }//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_BtnSeek1KeyPressed

    private void txtKdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdDokterKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=? ", txtNmDokter, txtKdDokter.getText());
            Valid.pindah(evt, txtKdDokter, txtRencanaPerawatan);
        }
    }//GEN-LAST:event_txtKdDokterKeyPressed

    private void txtNmDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNmDokterKeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        // TODO add your handling code here:
        akses.setform("DlgRawatInap");
        perawatan.dokter.emptTeks();
        perawatan.dokter.isCek();
        perawatan.dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        perawatan.dokter.setLocationRelativeTo(internalFrame1);
        perawatan.dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void btnGantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGantiActionPerformed
        // TODO add your handling code here:
        Sequel.mengedit("temp_spri", "id='" + id + "'", "tanggal='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',"
                + "jam='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "',"
                + "norm='" + TNoRM.getText() + "',"
                + "diagnosa='" + txtKdPenyakit.getText() + "',"
                + "rencana_perawatan='" + txtRencanaPerawatan.getText() + "',"
                + "upf='" + cmbUpf.getSelectedItem().toString() + "',"
                + "kd_dokter='" + txtKdDokter.getText() + "',"
                + "nama='" + TPasien.getText() + "',"
                + "keluhan='" + txtNmPenyakit.getText() + "'");

        emptTeks();
        tampil();
    }//GEN-LAST:event_btnGantiActionPerformed

    private void cmbUpfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbUpfKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUpfKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSpri dialog = new DlgSpri(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek3;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPTgl;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnAngkutJenazah;
    private javax.swing.JMenuItem MnCetakSuratMati;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TPasien;
    private widget.Button btnGanti;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbUpf;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbSpri;
    private javax.swing.JTextField txtId;
    private widget.TextBox txtKdDokter;
    private widget.TextBox txtKdPenyakit;
    private widget.TextBox txtNmDokter;
    private widget.TextBox txtNmPenyakit;
    private widget.TextBox txtRencanaPerawatan;
    // End of variables declaration//GEN-END:variables

//"Tanggal","Jam","No.R.Medik","Nama Pasien","J.K.","Tmp.Lahir",
//                      "Tgl.Lahir","G.D.","Stts.Nikah","Agama","Rencana Perawatan","Ruangan",
//                      "Dokter","Diagnosa"
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TNoRM.getText().equals("")
                    && TCari.getText().equals("")
                    && TPasien.getText().equals("")) {
                ps = koneksi.prepareStatement("SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,temp_spri.nama,"
                        + "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                        + "pasien.agama,temp_spri.rencana_perawatan,temp_spri.upf,"
                        + "dokter.nm_dokter,penyakit.kd_penyakit,penyakit.nm_penyakit,temp_spri.kd_dokter,temp_spri.diagnosa,temp_spri.keluhan "
                        + " FROM temp_spri left join pasien on temp_spri.norm=pasien.no_rkm_medis"
                        + " left join dokter on temp_spri.kd_dokter=dokter.kd_dokter"
                        + " left join penyakit on temp_spri.diagnosa=penyakit.kd_penyakit"
                        + " order by temp_spri.tanggal desc limit 10");
            } else {
                if (!TNoRM.getText().equals("")) {
                    ps = koneksi.prepareStatement("SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,temp_spri.nama,"
                            + "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                            + "pasien.agama,temp_spri.rencana_perawatan,temp_spri.upf,dokter.nm_dokter,penyakit.kd_penyakit,penyakit.nm_penyakit,temp_spri.kd_dokter,temp_spri.diagnosa,temp_spri.keluhan "
                            + " FROM temp_spri left join pasien on temp_spri.norm=pasien.no_rkm_medis"
                            + " left join dokter on temp_spri.kd_dokter=dokter.kd_dokter"
                            + " left join penyakit on temp_spri.diagnosa=penyakit.kd_penyakit where"
                            //                    + " temp_spri.tanggal like '%" + TCari.getText().trim() + "%' or "
                            //                    + " temp_spri.norm like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.jk like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.tmp_lahir like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.gol_darah like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.stts_nikah like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.agama like '%" + TCari.getText().trim() + "%' or "
                            //                    + " temp_spri.rencana_perawatan like '%" + TCari.getText().trim() + "%' "
                            + " temp_spri.norm like '%" + TNoRM.getText().trim() + "%' "
                            + " order by temp_spri.tanggal ");
                } else if (!txtKdDokter.getText().equals("")) {
                    ps = koneksi.prepareStatement("SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,temp_spri.nama,"
                            + "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                            + "pasien.agama,temp_spri.rencana_perawatan,temp_spri.upf,dokter.nm_dokter,penyakit.kd_penyakit,"
                            + "penyakit.nm_penyakit,temp_spri.kd_dokter,temp_spri.diagnosa,temp_spri.keluhan "
                            + " FROM temp_spri left join pasien on temp_spri.norm=pasien.no_rkm_medis"
                            + " left join dokter on temp_spri.kd_dokter=dokter.kd_dokter"
                            + " left join penyakit on temp_spri.diagnosa=penyakit.kd_penyakit where"
                            //                    + " temp_spri.tanggal like '%" + TCari.getText().trim() + "%' or "
                            //                    + " temp_spri.norm like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.jk like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.tmp_lahir like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.gol_darah like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.stts_nikah like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.agama like '%" + TCari.getText().trim() + "%' or "
                            //                    + " temp_spri.rencana_perawatan like '%" + TCari.getText().trim() + "%' "
                            + " dokter.kd_dokter like '%" + txtKdDokter.getText().trim() + "%' "
                            + " order by temp_spri.tanggal ");
                } else if (!txtKdPenyakit.getText().equals("")) {
                    ps = koneksi.prepareStatement("SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,temp_spri.nama,"
                            + "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                            + "pasien.agama,temp_spri.rencana_perawatan,temp_spri.upf,dokter.nm_dokter,penyakit.kd_penyakit,"
                            + "penyakit.nm_penyakit,temp_spri.kd_dokter,temp_spri.diagnosa,temp_spri.keluhan "
                            + " FROM temp_spri left join pasien on temp_spri.norm=pasien.no_rkm_medis"
                            + " left join dokter on temp_spri.kd_dokter=dokter.kd_dokter"
                            + " left join penyakit on temp_spri.diagnosa=penyakit.kd_penyakit where"
                            //                    + " temp_spri.tanggal like '%" + TCari.getText().trim() + "%' or "
                            //                    + " temp_spri.norm like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.jk like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.tmp_lahir like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.gol_darah like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.stts_nikah like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.agama like '%" + TCari.getText().trim() + "%' or "
                            //                    + " temp_spri.rencana_perawatan like '%" + TCari.getText().trim() + "%' "
                            + " temp_spri.diagnosa like '%" + txtKdPenyakit.getText().trim() + "%' "
                            + " order by temp_spri.tanggal ");
                } else if (!TCari.getText().trim().equals("")) {
                    ps = koneksi.prepareStatement("SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,temp_spri.nama,"
                            + "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                            + "pasien.agama,temp_spri.rencana_perawatan,temp_spri.upf,"
                            + "dokter.nm_dokter,penyakit.kd_penyakit,penyakit.nm_penyakit,temp_spri.kd_dokter,temp_spri.diagnosa,temp_spri.keluhan "
                            + " FROM temp_spri left join pasien on temp_spri.norm=pasien.no_rkm_medis"
                            + " left join dokter on temp_spri.kd_dokter=dokter.kd_dokter"
                            + " left join penyakit on temp_spri.diagnosa=penyakit.kd_penyakit where"
                            //                    + " temp_spri.tanggal like '%" + TCari.getText().trim() + "%' or "
                            //                    + " temp_spri.norm like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.jk like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.tmp_lahir like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.gol_darah like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.stts_nikah like '%" + TCari.getText().trim() + "%' or "
                            //                    + " pasien.agama like '%" + TCari.getText().trim() + "%' or "
                            //                    + " temp_spri.rencana_perawatan like '%" + TCari.getText().trim() + "%' "
                            + " temp_spri.norm like '%" + TCari.getText().trim() + "%' "
                            + " order by temp_spri.tanggal ");
                }
            }

            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getString("temp_spri.diagnosa").equals("")) {
                        tabMode.addRow(new Object[]{
                            rs.getString("tanggal"), rs.getString("jam"), rs.getString("norm"), rs.getString("temp_spri.nama"),
                            rs.getString("pasien.jk"), rs.getString("pasien.tmp_lahir"), rs.getString("pasien.tgl_lahir"), rs.getString("pasien.gol_darah"),
                            rs.getString("pasien.stts_nikah"), rs.getString("pasien.agama"), rs.getString("temp_spri.rencana_perawatan"),
                            rs.getString("temp_spri.upf"),
                            rs.getString("dokter.nm_dokter"), rs.getString("keluhan"), rs.getString("temp_spri.kd_dokter"), rs.getString("penyakit.kd_penyakit"),
                            rs.getString("nama"), rs.getString("keluhan"), rs.getString("penyakit.nm_penyakit"), rs.getString("temp_spri.id")
                        });
                    } else if (!rs.getString("temp_spri.diagnosa").equals("")) {
                        tabMode.addRow(new Object[]{
                            rs.getString("tanggal"), rs.getString("jam"), rs.getString("norm"), rs.getString("temp_spri.nama"),
                            rs.getString("pasien.jk"), rs.getString("pasien.tmp_lahir"), rs.getString("pasien.tgl_lahir"), rs.getString("pasien.gol_darah"),
                            rs.getString("pasien.stts_nikah"), rs.getString("pasien.agama"), rs.getString("temp_spri.rencana_perawatan"),
                            rs.getString("temp_spri.upf"),
                            rs.getString("dokter.nm_dokter"), rs.getString("temp_spri.diagnosa"), rs.getString("temp_spri.kd_dokter"), rs.getString("penyakit.kd_penyakit"),
                            rs.getString("nama"), rs.getString("keluhan"), rs.getString("penyakit.nm_penyakit"), rs.getString("temp_spri.id")
                        });
                    }
                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgSpri.tampil() : " + e);
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

    public void emptTeks() {
        TNoRM.setText("");
        TPasien.setText("");
        txtRencanaPerawatan.setText("");
        cmbUpf.setSelectedIndex(0);
        txtNmPenyakit.setText("");
        txtKdPenyakit.setText("");
        txtKdDokter.setText("");
        txtNmDokter.setText("");
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
    }

    private void getData() {
        if (tbSpri.getSelectedRow() != -1) {
            id = tbSpri.getValueAt(tbSpri.getSelectedRow(), 19).toString();
            txtId.setText(id);
            cmbJam.setSelectedItem(tbSpri.getValueAt(tbSpri.getSelectedRow(), 1).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbSpri.getValueAt(tbSpri.getSelectedRow(), 1).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbSpri.getValueAt(tbSpri.getSelectedRow(), 1).toString().substring(6, 8));
            TNoRM.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 2).toString());
            if (tbSpri.getValueAt(tbSpri.getSelectedRow(), 2).toString().equals("")) {
                TPasien.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 16).toString());
            } else {
                TPasien.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 3).toString());
            }
            txtRencanaPerawatan.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 10).toString());
            Valid.SetTgl(DTPTgl, tbSpri.getValueAt(tbSpri.getSelectedRow(), 0).toString());
            if (!tbSpri.getValueAt(tbSpri.getSelectedRow(), 11).toString().trim().equals("")) {
                cmbUpf.setSelectedItem(tbSpri.getValueAt(tbSpri.getSelectedRow(), 11).toString());
            } else {
                cmbUpf.setSelectedIndex(0);
            }
            txtKdPenyakit.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 15).toString());
            if (tbSpri.getValueAt(tbSpri.getSelectedRow(), 15).toString().equals("")) {
                txtKdPenyakit.setText("");
                txtNmPenyakit.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 17).toString());
            } else {
                txtNmPenyakit.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 18).toString());
            }
            txtKdDokter.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 14).toString());
            txtNmDokter.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 12).toString());
            TCari.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 2).toString());

        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getpasien_meninggal());
        BtnHapus.setEnabled(akses.getpasien_meninggal());
        BtnPrint.setEnabled(akses.getpasien_meninggal());
    }

    public void setNoRm(String norm) {
        TNoRM.setText(norm);
        TCari.setText(norm);
        isPsien();
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
    }

}

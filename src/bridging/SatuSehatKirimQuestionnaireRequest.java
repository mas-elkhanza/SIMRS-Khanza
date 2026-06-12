/*
  by Mas Elkhanza
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import simrskhanza.DlgPasien;

/**
 *
 * @author dosen
 */
public final class SatuSehatKirimQuestionnaireRequest extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private String link = "", json = "", idapt = "", idpasien = "";
    private java.util.Map<String, String> nipMap = new java.util.HashMap<>();
    private java.util.Map<String, String> statusMap = new java.util.HashMap<>();
    private ApiSatuSehat api = new ApiSatuSehat();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private SatuSehatCekNIK cekViaSatuSehat = new SatuSehatCekNIK();
    private StringBuilder htmlContent;
    private DlgPasien cekflagging = new DlgPasien(null, false);

    /**
     * Creates new form DlgKamar
     *
     * @param parent
     * @param modal
     */
    public SatuSehatKirimQuestionnaireRequest(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10, 2);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new String[]{
            "P", "Tgl Telaah", "No.Resep", "No.Rawat",
            "No.RM", "Nama Pasien", "No.KTP Pasien", "No. KTP Apt", "Nama Apoteker", "kd 1", "Administrasi Lengkap",
            "kd 2", "Resep Jelas", "kd 3", "Tanggal Resep", "kd 4", "Ruangan/Unit", "kd 1", "Nama Obat", "kd 2", "Dosis Obat",
            "kd 4", "Aturan Pakai", "kd1", "Ketepatan Indikasi", "Duplikasi", "Alergi", "Kontrakdiksi", "Dampak Interaksi", "ID Encounter", "ID Service Request"

        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbObat.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 31; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(110);
            } else if (i == 5) {
                column.setPreferredWidth(80);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(110);
            } else if (i == 8) {
                column.setPreferredWidth(210);
            } else if (i == 9) {
                column.setPreferredWidth(80);
            } else if (i == 10) {
                column.setPreferredWidth(120);
            } else if (i == 11) {
                column.setPreferredWidth(80);
            } else if (i == 12) {
                column.setPreferredWidth(150);
            } else if (i == 13) {
                column.setPreferredWidth(80);
            } else if (i == 14) {
                column.setPreferredWidth(150);
            } else if (i == 15) {
                column.setPreferredWidth(80);
            } else if (i == 16) {
                column.setPreferredWidth(150);
            } else if (i == 17) {
                column.setPreferredWidth(80);
            } else if (i == 18) {
                column.setPreferredWidth(150);
            } else if (i == 19) {
                column.setPreferredWidth(80);
            } else if (i == 20) {
                column.setPreferredWidth(150);
            } else if (i == 21) {
                column.setPreferredWidth(80);
            } else if (i == 22) {
                column.setPreferredWidth(150);
            } else if (i == 23) {
                column.setPreferredWidth(80);
            } else if (i == 24) {
                column.setPreferredWidth(150);
            } else if (i == 25) {
                column.setPreferredWidth(210);
            } else if (i == 26) {
                column.setPreferredWidth(210);
            } else if (i == 27) {
                column.setPreferredWidth(210);
            } else if (i == 28) {
                column.setPreferredWidth(210);
            } else if (i == 29) {
                column.setPreferredWidth(210);
            } else if (i == 30) {
                column.setPreferredWidth(210);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

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

        try {
            link = koneksiDB.URLFHIRSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppPilihSemua = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnAll = new widget.Button();
        BtnKirim = new widget.Button();
        BtnUpdate = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        ChkBelumTerkirim = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilihSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPilihSemua.setText("Pilih Semua");
        ppPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilihSemua.setName("ppPilihSemua"); // NOI18N
        ppPilihSemua.setPreferredSize(new java.awt.Dimension(150, 26));
        ppPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPilihSemua);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Hilangkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(150, 26));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBersihkan);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengiriman Quetionnaire Request Pengkajian Obat Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(53, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(LCount);

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

        BtnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        BtnKirim.setMnemonic('K');
        BtnKirim.setText("Kirim");
        BtnKirim.setToolTipText("Alt+K");
        BtnKirim.setName("BtnKirim"); // NOI18N
        BtnKirim.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnKirim);

        BtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnUpdate.setMnemonic('U');
        BtnUpdate.setText("Update");
        BtnUpdate.setToolTipText("Alt+U");
        BtnUpdate.setName("BtnUpdate"); // NOI18N
        BtnUpdate.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnUpdate);

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

        jLabel15.setText("Tgl.Registrasi :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass9.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d.");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel16.setText("Key Word :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel16);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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

        ChkBelumTerkirim.setBorder(null);
        ChkBelumTerkirim.setText("Data belum terkirim");
        ChkBelumTerkirim.setBorderPainted(true);
        ChkBelumTerkirim.setBorderPaintedFlat(true);
        ChkBelumTerkirim.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkBelumTerkirim.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkBelumTerkirim.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkBelumTerkirim.setIconTextGap(2);
        ChkBelumTerkirim.setName("ChkBelumTerkirim"); // NOI18N
        ChkBelumTerkirim.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkBelumTerkirimItemStateChanged(evt);
            }
        });
        ChkBelumTerkirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkBelumTerkirimActionPerformed(evt);
            }
        });
        panelGlass9.add(ChkBelumTerkirim);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

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

        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbObat.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {

        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimActionPerformed
        for (i = 0; i < tbObat.getRowCount(); i++) {
            if (tbObat.getValueAt(i, 0).toString().equals("true")) {
                try {
                    idapt = cekViaSatuSehat.tampilIDParktisi(tbObat.getValueAt(i, 7).toString());
                    idpasien = cekViaSatuSehat.tampilIDPasien(tbObat.getValueAt(i, 6).toString());
                    try {
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
                        json = "{\n"
                                + "    \"resourceType\": \"QuestionnaireResponse\",\n"
                                + "    \"questionnaire\": \"https://fhir.kemkes.go.id/Questionnaire/Q0007\",\n"
                                + "    \"status\": \"completed\",\n"
                                + "    \"subject\": {\n"
                                + "        \"reference\": \"Patient/" + idpasien + "\",\n"
                                + "        \"display\": \"" + tbObat.getValueAt(i, 5).toString() + "\"\n"
                                + "    },\n"
                                + "    \"encounter\": {\n"
                                + "        \"reference\": \"Encounter/" + tbObat.getValueAt(i, 29).toString() + "\"\n"
                                + "    },\n"
                                + "    \"authored\": \"" + tbObat.getValueAt(i, 1).toString() + "\",\n"
                                + "    \"author\": {\n"
                                + "        \"reference\": \"Practitioner/" + idapt + "\",\n"
                                + "        \"display\": \"" + tbObat.getValueAt(i, 8).toString() + "\"\n"
                                + "    },\n"
                                + "    \"source\": {\n"
                                + "        \"reference\": \"Patient/" + idpasien + "\"\n"
                                + "    },\n"
                                + "    \"item\": [\n"
                                + "        {\n"
                                + "            \"linkId\": \"1\",\n"
                                + "            \"text\": \"Persyaratan Administrasi\",\n"
                                + "            \"item\": [\n"
                                + "                {\n"
                                + "                    \"linkId\": \"1.1\",\n"
                                + "                    \"text\": \"Apakah nama, umur, jenis kelamin, berat badan dan tinggi badan pasien sudah sesuai?\",\n"
                                + "                    \"answer\": [\n"
                                + "                        {\n"
                                + "                            \"valueCoding\": {\n"
                                + "                                \"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\",\n"
                                + "                                \"code\": \"" + tbObat.getValueAt(i, 9).toString() + "\",\n"
                                + "                                \"display\": \"" + tbObat.getValueAt(i, 10).toString() + "\"\n"
                                + "                            }\n"
                                + "                        }\n"
                                + "                    ]\n"
                                + "                },\n"
                                + "                {\n"
                                + "                    \"linkId\": \"1.2\",\n"
                                + "                    \"text\": \"Apakah nama, nomor ijin, alamat dan paraf dokter sudah sesuai?\",\n"
                                + "                    \"answer\": [\n"
                                + "                        {\n"
                                + "                            \"valueCoding\": {\n"
                                + "                                \"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\",\n"
                                + "                                \"code\": \"" + tbObat.getValueAt(i, 11).toString() + "\",\n"
                                + "                                \"display\": \"" + tbObat.getValueAt(i, 12).toString() + "\"\n"
                                + "                            }\n"
                                + "                        }\n"
                                + "                    ]\n"
                                + "                },\n"
                                + "                {\n"
                                + "                    \"linkId\": \"1.3\",\n"
                                + "                    \"text\": \"Apakah tanggal resep sudah sesuai?\",\n"
                                + "                    \"answer\": [\n"
                                + "                        {\n"
                                + "                            \"valueCoding\": {\n"
                                + "                                \"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\",\n"
                                + "                                \"code\": \"" + tbObat.getValueAt(i, 13).toString() + "\",\n"
                                + "                                \"display\": \"" + tbObat.getValueAt(i, 14).toString() + "\"\n"
                                + "                            }\n"
                                + "                        }\n"
                                + "                    ]\n"
                                + "                },\n"
                                + "                {\n"
                                + "                    \"linkId\": \"1.4\",\n"
                                + "                    \"text\": \"Apakah ruangan/unit asal resep sudah sesuai?\",\n"
                                + "                    \"answer\": [\n"
                                + "                        {\n"
                                + "                            \"valueCoding\": {\n"
                                + "                                \"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\",\n"
                                + "                                \"code\": \"" + tbObat.getValueAt(i, 15).toString() + "\",\n"
                                + "                                \"display\": \"" + tbObat.getValueAt(i, 16).toString() + "\"\n"
                                + "                            }\n"
                                + "                        }\n"
                                + "                    ]\n"
                                + "                },\n"
                                + "                {\n"
                                + "                    \"linkId\": \"2\",\n"
                                + "                    \"text\": \"Persyaratan Farmasetik\",\n"
                                + "                    \"item\": [\n"
                                + "                        {\n"
                                + "                            \"linkId\": \"2.1\",\n"
                                + "                            \"text\": \"Apakah nama obat, bentuk dan kekuatan sediaan sudah sesuai?\",\n"
                                + "                            \"answer\": [\n"
                                + "                                {\n"
                                + "                                    \"valueCoding\": {\n"
                                + "                                        \"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\",\n"
                                + "                                        \"code\": \"" + tbObat.getValueAt(i, 17).toString() + "\",\n"
                                + "                                        \"display\": \"" + tbObat.getValueAt(i, 18).toString() + "\"\n"
                                + "                                    }\n"
                                + "                                }\n"
                                + "                            ]\n"
                                + "                        },\n"
                                + "                        {\n"
                                + "                            \"linkId\": \"2.2\",\n"
                                + "                            \"text\": \"Apakah dosis dan jumlah obat sudah sesuai?\",\n"
                                + "                            \"answer\": [\n"
                                + "                                {\n"
                                + "                                    \"valueCoding\": {\n"
                                + "                                        \"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\",\n"
                                + "                                        \"code\": \"" + tbObat.getValueAt(i, 19).toString() + "\",\n"
                                + "                                        \"display\": \"" + tbObat.getValueAt(i, 20).toString() + "\"\n"
                                + "                                    }\n"
                                + "                                }\n"
                                + "                            ]\n"
                                + "                        },\n"
                                + "                        {\n"
                                + "                            \"linkId\": \"2.3\",\n"
                                + "                            \"text\": \"Apakah aturan dan cara penggunaan obat sudah sesuai?\",\n"
                                + "                            \"answer\": [\n"
                                + "                                {\n"
                                + "                                    \"valueCoding\": {\n"
                                + "                                        \"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\",\n"
                                + "                                        \"code\": \"" + tbObat.getValueAt(i, 21).toString() + "\",\n"
                                + "                                        \"display\": \"" + tbObat.getValueAt(i, 22).toString() + "\"\n"
                                + "                                    }\n"
                                + "                                }\n"
                                + "                            ]\n"
                                + "                        }\n"
                                + "                    ]\n"
                                + "                },\n"
                                + "                {\n"
                                + "                    \"linkId\": \"3\",\n"
                                + "                    \"text\": \"Persyaratan Klinis\",\n"
                                + "                    \"item\": [\n"
                                + "                        {\n"
                                + "                            \"linkId\": \"3.1\",\n"
                                + "                            \"text\": \"Apakah ketepatan indikasi, dosis, dan waktu penggunaan obat sudah sesuai?\",\n"
                                + "                            \"answer\": [\n"
                                + "                                {\n"
                                + "                                    \"valueCoding\": {\n"
                                + "                                        \"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\",\n"
                                + "                                        \"code\": \"" + tbObat.getValueAt(i, 23).toString() + "\",\n"
                                + "                                        \"display\": \"" + tbObat.getValueAt(i, 24).toString() + "\"\n"
                                + "                                    }\n"
                                + "                                }\n"
                                + "                            ]\n"
                                + "                        },\n"
                                + "                        {\n"
                                + "                            \"linkId\": \"3.2\",\n"
                                + "                            \"text\": \"Apakah terdapat duplikasi pengobatan?\",\n"
                                + "                            \"answer\": [\n"
                                + "                                {\n"
                                + "                                    \"valueBoolean\": " + tbObat.getValueAt(i, 25).toString() + "\n"
                                + "                                }\n"
                                + "                            ]\n"
                                + "                        },\n"
                                + "                        {\n"
                                + "                            \"linkId\": \"3.3\",\n"
                                + "                            \"text\": \"Apakah terdapat alergi dan reaksi obat yang tidak dikehendaki (ROTD)?\",\n"
                                + "                            \"answer\": [\n"
                                + "                                {\n"
                                + "                                    \"valueBoolean\": " + tbObat.getValueAt(i, 26).toString() + "\n"
                                + "                                }\n"
                                + "                            ]\n"
                                + "                        },\n"
                                + "                        {\n"
                                + "                            \"linkId\": \"3.4\",\n"
                                + "                            \"text\": \"Apakah terdapat kontraindikasi pengobatan?\",\n"
                                + "                            \"answer\": [\n"
                                + "                                {\n"
                                + "                                    \"valueBoolean\": " + tbObat.getValueAt(i, 27).toString() + "\n"
                                + "                                }\n"
                                + "                            ]\n"
                                + "                        },\n"
                                + "                        {\n"
                                + "                            \"linkId\": \"3.5\",\n"
                                + "                            \"text\": \"Apakah terdapat dampak interaksi obat?\",\n"
                                + "                            \"answer\": [\n"
                                + "                                {\n"
                                + "                                    \"valueBoolean\": " + tbObat.getValueAt(i, 28).toString() + "\n"
                                + "                                }\n"
                                + "                            ]\n"
                                + "                        }\n"
                                + "                    ]\n"
                                + "                }\n"
                                + "            ]\n"
                                + "        }\n"
                                + "    ]\n"
                                + "}";
                        System.out.println("URL : " + link + "/QuestionnaireResponse");
                        System.out.println("Request JSON : " + json);
                        requestEntity = new HttpEntity(json, headers);
//                        System.out.println(headers.toString());
                        json = api.getRest().exchange(link + "/QuestionnaireResponse", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : " + json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if (!response.asText().equals("")) {
                            // Extract date and time from formatted string "2024-01-01T08:30:00+07:00"
                            String dateTimeStr = tbObat.getValueAt(i, 1).toString();
                            String dateOnly = dateTimeStr.substring(0, 10); // Extract date part: 2024-01-01
                            String timeOnly = dateTimeStr.substring(11, 19); // Extract time part: 08:30:00
                            
                            // Get stored NIP and status using key
                            String key = tbObat.getValueAt(i, 3).toString() + "_" + tbObat.getValueAt(i, 2).toString();
                            String nip = nipMap.getOrDefault(key, "");
                            String status = statusMap.getOrDefault(key, "Ralan");
                            
                            Sequel.menyimpantf2("satu_sehat_questionnairereq_pengkajian_obat", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat,Tgl.Perawatan,Jam,Status,No.Resep,NIP,ID Response,resep_identifikasi_pasien,resep_tepat_obat,resep_tepat_dosis,resep_tepat_cara_pemberian,resep_tepat_waktu_pemberian,resep_ada_tidak_duplikasi_obat,resep_interaksi_obat,resep_kontra_indikasi_obat,obat_tepat_pasien,obat_tepat_obat,obat_tepat_dosis,obat_tepat_cara_pemberian,obat_tepat_waktu_pemberian,catatan_khusus,tgl_input", 22, new String[]{
                                tbObat.getValueAt(i, 3).toString(),  // no_rawat
                                dateOnly,                             // tgl_perawatan
                                timeOnly,                             // jam_rawat  
                                status,                               // status
                                tbObat.getValueAt(i, 2).toString(),  // no_resep
                                nip,                                  // nip_petugas
                                response.asText(),                    // id_questionnaire_request
                                "Ya",                                 // resep_identifikasi_pasien (default)
                                "Ya",                                 // resep_tepat_obat (default)
                                "Ya",                                 // resep_tepat_dosis (default)
                                "Ya",                                 // resep_tepat_cara_pemberian (default)
                                "Ya",                                 // resep_tepat_waktu_pemberian (default)
                                "Ya",                                 // resep_ada_tidak_duplikasi_obat (default)
                                "Ya",                                 // resep_interaksi_obat (default)
                                "Ya",                                 // resep_kontra_indikasi_obat (default)
                                "Ya",                                 // obat_tepat_pasien (default)
                                "Ya",                                 // obat_tepat_obat (default)
                                "Ya",                                 // obat_tepat_dosis (default)
                                "Ya",                                 // obat_tepat_cara_pemberian (default)
                                "Ya",                                 // obat_tepat_waktu_pemberian (default)
                                "",                                   // catatan_khusus (empty)  
                                java.time.LocalDateTime.now().toString() // tgl_input (current timestamp)
                            }
                            );
                        }
                    } catch (HttpClientErrorException | HttpServerErrorException e) {
                        // Handle client and server errors
                        System.err.println("Error Response Status Code: " + e.getStatusCode());
//                            System.err.println("Error Response Body: " + e.getResponseBodyAsString());
                        // You can further parse the error response body if needed
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode errorResponse = mapper.readTree(e.getResponseBodyAsString());
                        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                        String prettyErrorResponse = writer.writeValueAsString(errorResponse);
                        System.err.println("Error Response JSON: \n" + prettyErrorResponse);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                }
            }
        }
    }//GEN-LAST:event_BtnKirimActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        for (i = 0; i < tbObat.getRowCount(); i++) {
            tbObat.setValueAt(true, i, 0);
        }
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for (i = 0; i < tbObat.getRowCount(); i++) {
            tbObat.setValueAt(false, i, 0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateActionPerformed
        for (i = 0; i < tbObat.getRowCount(); i++) {
            if (tbObat.getValueAt(i, 0).toString().equals("true") && (!tbObat.getValueAt(i, 4).toString().equals("")) && (!tbObat.getValueAt(i, 7).toString().equals("")) && (!tbObat.getValueAt(i, 16).toString().equals(""))) {
                try {
                    idapt = cekViaSatuSehat.tampilIDParktisi(tbObat.getValueAt(i, 7).toString());
                    idpasien = cekViaSatuSehat.tampilIDPasien(tbObat.getValueAt(i, 4).toString());
                    try {
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
                        json = "{"
                                + "\"resourceType\": \"ServiceRequest\","
                                + "\"id\": \"" + tbObat.getValueAt(i, 15).toString() + "\","
                                + "\"identifier\": ["
                                + "{"
                                + "\"system\": \"http://sys-ids.kemkes.go.id/servicerequest/" + koneksiDB.IDSATUSEHAT() + "\","
                                + "\"value\": \"" + tbObat.getValueAt(i, 9).toString() + "\""
                                + "}"
                                + "],"
                                + "\"status\": \"active\","
                                + "\"intent\": \"order\","
                                + "\"code\": {"
                                + "\"coding\": ["
                                + "{"
                                + "\"system\": \"" + tbObat.getValueAt(i, 14).toString() + "\","
                                + "\"code\": \"" + tbObat.getValueAt(i, 13).toString() + "\","
                                + "\"display\": \"" + tbObat.getValueAt(i, 15).toString() + "\""
                                + "}"
                                + "],"
                                + "\"text\": \"" + tbObat.getValueAt(i, 12).toString() + "\""
                                + "},"
                                + "\"subject\": {"
                                + "\"reference\": \"Patient/" + idpasien + "\""
                                + "},"
                                + "\"encounter\": {"
                                + "\"reference\": \"Encounter/" + tbObat.getValueAt(i, 8).toString() + "\","
                                + "\"display\": \"Permintaan " + tbObat.getValueAt(i, 12).toString() + " atas nama pasien " + tbObat.getValueAt(i, 3).toString() + " No.RM " + tbObat.getValueAt(i, 2).toString() + " No.Rawat " + tbObat.getValueAt(i, 1).toString() + ", pada tanggal " + tbObat.getValueAt(i, 10).toString() + "\""
                                + "},"
                                + "\"authoredOn\" : \"" + tbObat.getValueAt(i, 10).toString().replaceAll(" ", "T") + "+07:00\","
                                + "\"requester\": {"
                                + "\"reference\": \"Practitioner/" + idapt + "\","
                                + "\"display\": \"" + tbObat.getValueAt(i, 6).toString() + "\""
                                + "},"
                                + "\"reasonCode\": ["
                                + "{"
                                + "\"text\": \"" + tbObat.getValueAt(i, 11).toString() + "\""
                                + "}"
                                + "]"
                                + "}";
                        System.out.println("URL : " + link + "/ServiceRequest/" + tbObat.getValueAt(i, 16).toString());
                        System.out.println("Request JSON : " + json);
                        requestEntity = new HttpEntity(json, headers);
                        json = api.getRest().exchange(link + "/ServiceRequest/" + tbObat.getValueAt(i, 16).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : " + json);
                        tbObat.setValueAt(false, i, 0);
                    } catch (Exception e) {
                        System.out.println("Notifikasi Bridging : " + e);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                }
            }
        }
    }//GEN-LAST:event_BtnUpdateActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            tampil();
        } else {

        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ChkBelumTerkirimItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkBelumTerkirimItemStateChanged
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ChkBelumTerkirimItemStateChanged

    private void ChkBelumTerkirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkBelumTerkirimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkBelumTerkirimActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatKirimQuestionnaireRequest dialog = new SatuSehatKirimQuestionnaireRequest(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKirim;
    private widget.Button BtnUpdate;
    private widget.CekBox ChkBelumTerkirim;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    private void tampil() {

        String belumterkirim = "";
        if (ChkBelumTerkirim.isSelected() == true) {
            belumterkirim = " satu_sehat_questionnairereq_pengkajian_obat.id_questreq IS NULL and ";
        } else {
            belumterkirim = "";
        }
        Valid.tabelKosong(tabMode);
        nipMap.clear();
        statusMap.clear();
        try {
            ps = koneksi.prepareStatement(
                    "SELECT\n"
                    + "	reg_periksa.no_rawat,\n"
                    + "	reg_periksa.tgl_registrasi,\n"
                    + "	reg_periksa.jam_reg,\n"
                    + "	pasien.no_rkm_medis,\n"
                    + "	pasien.nm_pasien,\n"
                    + "	pasien.no_ktp,\n"
                    + "	telaah_farmasi.no_resep,\n"
                    + "	resep_obat.tgl_perawatan as tanggal,\n"
                    + "	resep_obat.jam,\n"
                    + "	resep_obat.status,\n"
                    + "	telaah_farmasi.nip ,\n"
                    + "	pegawai.nama,\n"
                    + "	pegawai.no_ktp as aptktp,\n"
                    + "	telaah_farmasi.resep_identifikasi_pasien,\n"
                    + "	telaah_farmasi.resep_tepat_obat,\n"
                    + "	telaah_farmasi.resep_tepat_dosis,\n"
                    + "	telaah_farmasi.resep_tepat_cara_pemberian,\n"
                    + "	telaah_farmasi.resep_tepat_waktu_pemberian,\n"
                    + "	telaah_farmasi.resep_ada_tidak_duplikasi_obat,\n"
                    + "	telaah_farmasi.resep_interaksi_obat,\n"
                    + "	telaah_farmasi.resep_kontra_indikasi_obat,\n"
                    + "	telaah_farmasi.obat_tepat_pasien,\n"
                    + "	telaah_farmasi.obat_tepat_obat,\n"
                    + "	telaah_farmasi.obat_tepat_dosis,\n"
                    + "	telaah_farmasi.obat_tepat_cara_pemberian,\n"
                    + "	telaah_farmasi.obat_tepat_waktu_pemberian,\n"
                    + "	satu_sehat_encounter.id_encounter,\n"
                    + "	ifnull( satu_sehat_questionnairereq_pengkajian_obat.id_questionnaire_request, '' ) AS id_questreq \n"
                    + "FROM\n"
                    + "	reg_periksa\n"
                    + "	INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n"
                    + "	INNER JOIN resep_obat ON reg_periksa.no_rawat = resep_obat.no_rawat\n"
                    + "	INNER JOIN telaah_farmasi ON resep_obat.no_resep = telaah_farmasi.no_resep\n"
                    + "	INNER JOIN satu_sehat_encounter ON reg_periksa.no_rawat = satu_sehat_encounter.no_rawat\n"
                    + "	LEFT JOIN satu_sehat_questionnairereq_pengkajian_obat ON reg_periksa.no_rawat = satu_sehat_questionnairereq_pengkajian_obat.no_rawat \n"
                    + "	AND resep_obat.tgl_perawatan = satu_sehat_questionnairereq_pengkajian_obat.tgl_perawatan\n"
                    + "	AND resep_obat.jam = satu_sehat_questionnairereq_pengkajian_obat.jam_rawat\n"
                    + "	AND resep_obat.status = satu_sehat_questionnairereq_pengkajian_obat.status\n"
                    + "	AND telaah_farmasi.no_resep = satu_sehat_questionnairereq_pengkajian_obat.no_resep\n"
                    + "	INNER JOIN pegawai ON telaah_farmasi.nip = pegawai.nik "
                    + "where " + belumterkirim + " reg_periksa.tgl_registrasi between ? and ? "
                    + (TCari.getText().equals("") ? "" : "and (reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "
                    + "pasien.nm_pasien like ? or pasien.no_ktp like ? or "
                    + "reg_periksa.stts like ? or reg_periksa.status_lanjut like ? or resep_obat.tgl_perawatan like ? or resep_obat.jam like ?)") + " "
                    + "GROUP BY\n"
                    + "	telaah_farmasi.no_resep \n"
                    + "ORDER BY\n"
                    + "	reg_periksa.tgl_registrasi ASC, reg_periksa.jam_reg ASC, reg_periksa.no_rawat ASC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                if (!TCari.getText().equals("")) {
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                    ps.setString(6, "%" + TCari.getText() + "%");
                    ps.setString(7, "%" + TCari.getText() + "%");
                    ps.setString(8, "%" + TCari.getText() + "%");
                    ps.setString(9, "%" + TCari.getText() + "%");
                    ps.setString(10, "%" + TCari.getText() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
               //     if (cekflagging.GeneralConsentSatuSehat(rs.getString("no_rkm_medis")) == true) {
                        if (rs.getString("id_questreq").equals("")) {
                            // Store NIP and status for later use in INSERT operation
                            String key = rs.getString("no_rawat") + "_" + rs.getString("no_resep");
                            nipMap.put(key, rs.getString("nip"));
                            statusMap.put(key, rs.getString("status"));
                            
                            tabMode.addRow(new Object[]{
                                true,
                                rs.getString("tanggal") + "T" + rs.getString("jam") + "+07:00",
                                rs.getString("no_resep"),
                                rs.getString("no_rawat"),
                                rs.getString("no_rkm_medis"),
                                rs.getString("nm_pasien"),
                                rs.getString("no_ktp"),
                                rs.getString("aptktp"),
                                rs.getString("nama"),
                                rs.getString("resep_identifikasi_pasien").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_identifikasi_pasien").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_tepat_obat").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_tepat_obat").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_tepat_dosis").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_tepat_dosis").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_tepat_cara_pemberian").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_tepat_cara_pemberian").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("obat_tepat_obat").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("obat_tepat_obat").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("obat_tepat_dosis").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("obat_tepat_dosis").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("obat_tepat_cara_pemberian").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("obat_tepat_cara_pemberian").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_tepat_obat").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_tepat_obat").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_ada_tidak_duplikasi_obat").equalsIgnoreCase("Tidak") ? "false" : "true",
                                rs.getString("resep_kontra_indikasi_obat").equalsIgnoreCase("Tidak") ? "false" : "true",
                                rs.getString("resep_interaksi_obat").equalsIgnoreCase("Tidak") ? "false" : "true",
                                rs.getString("resep_interaksi_obat").equalsIgnoreCase("Tidak") ? "false" : "true",
                                rs.getString("id_encounter"),
                                rs.getString("id_questreq")

                            });
                        } else {
                            // Store NIP and status for later use in INSERT operation
                            String key = rs.getString("no_rawat") + "_" + rs.getString("no_resep");
                            nipMap.put(key, rs.getString("nip"));
                            statusMap.put(key, rs.getString("status"));
                            
                            tabMode.addRow(new Object[]{
                                false,
                                rs.getString("tanggal") + "T" + rs.getString("jam") + "+07:00",
                                rs.getString("no_resep"),
                                rs.getString("no_rawat"),
                                rs.getString("no_rkm_medis"),
                                rs.getString("nm_pasien"),
                                rs.getString("no_ktp"),
                                rs.getString("aptktp"),
                                rs.getString("nama"),
                                rs.getString("resep_identifikasi_pasien").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_identifikasi_pasien").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_tepat_obat").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_tepat_obat").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_tepat_dosis").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_tepat_dosis").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_tepat_cara_pemberian").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_tepat_cara_pemberian").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("obat_tepat_obat").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("obat_tepat_obat").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("obat_tepat_dosis").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("obat_tepat_dosis").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("obat_tepat_cara_pemberian").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("obat_tepat_cara_pemberian").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_tepat_obat").equalsIgnoreCase("Ya") ? "OV000052" : "OV000053",
                                rs.getString("resep_tepat_obat").equalsIgnoreCase("Ya") ? "Sesuai" : "Tidak Sesuai",
                                rs.getString("resep_ada_tidak_duplikasi_obat").equalsIgnoreCase("Tidak") ? "false" : "true",
                                rs.getString("resep_kontra_indikasi_obat").equalsIgnoreCase("Tidak") ? "false" : "true",
                                rs.getString("resep_interaksi_obat").equalsIgnoreCase("Tidak") ? "false" : "true",
                                rs.getString("resep_interaksi_obat").equalsIgnoreCase("Tidak") ? "false" : "true",
                                rs.getString("id_encounter"),
                                rs.getString("id_questreq")

                            });
                      //  }

                    }
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

        LCount.setText(
                "" + tabMode.getRowCount());
    }

    public void isCek() {
        BtnKirim.setEnabled(akses.getsatu_sehat_kirim_encounter());
        ChkBelumTerkirim.setSelected(true);
    }

    public JTable getTable() {
        return tbObat;
    }
}

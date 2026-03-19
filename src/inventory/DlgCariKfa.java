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
package inventory;

import static antlr.build.ANTLR.root;
import bridging.ApiSatuSehat;
import bridging.SatuSehatMapingObatAlkes;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import static jdk.internal.net.http.common.Log.headers;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class DlgCariKfa extends javax.swing.JDialog {

    private DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private int i = 0;
    private ResultSet rs;
    private String link = "", json = "";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private ApiSatuSehat api = new ApiSatuSehat();
    private javax.swing.JDialog loadingDialog;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel loadingLabel;

    public DlgCariKfa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
            "KFA Code", "KFA Display", "Form Code", "Form Display", "Numerator Code", "Denomina Code", "Route Code", "Route Display", "Registrar", "Nama Dagang"
        }) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(80);
            } else if (i == 1) {
                column.setPreferredWidth(400);
            } else if (i == 2) {
                column.setPreferredWidth(80);
            } else if (i == 3) {
                column.setPreferredWidth(80);
            } else if (i == 4) {
                column.setPreferredWidth(80);
            } else if (i == 5) {
                column.setPreferredWidth(80);
            } else if (i == 6) {
                column.setPreferredWidth(80);
            } else if (i == 7) {
                column.setPreferredWidth(80);
            } else if (i == 8) {
                column.setPreferredWidth(200);
            } else if (i == 9) {
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        isForm();

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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi2 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        label13 = new widget.Label();
        TCari2 = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        jLabel6 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        BtnCariOnline = new widget.Button();
        BtnUpdateKFA = new widget.Button();
        BtnKeluar = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbObat = new widget.Table();

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 338));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0), 3), "::[ Data Cari KFA ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        label13.setText("Registrar:");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label13);

        TCari2.setName("TCari2"); // NOI18N
        TCari2.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari2KeyPressed(evt);
            }
        });
        panelisi2.add(TCari2);

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
        panelisi2.add(BtnCari);

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
        panelisi2.add(BtnAll);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(LCount);

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel6.setText("Limit Data :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(jLabel6);

        cmbHlm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20", "50", "100", "200", "300", "400", "500", "1000", "Semua" }));
        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi1.add(cmbHlm);

        BtnCariOnline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnCariOnline.setMnemonic('K');
        BtnCariOnline.setText("Cari Online");
        BtnCariOnline.setToolTipText("Alt+K");
        BtnCariOnline.setName("BtnCariOnline"); // NOI18N
        BtnCariOnline.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnCariOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariOnlineActionPerformed(evt);
            }
        });
        BtnCariOnline.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariOnlineKeyPressed(evt);
            }
        });
        panelisi1.add(BtnCariOnline);

        BtnUpdateKFA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Blackvariant-Button-Ui-Microsoft-Office-Apps-Microsoft-Sync.16.png"))); // NOI18N
        BtnUpdateKFA.setMnemonic('K');
        BtnUpdateKFA.setText("Update KFA");
        BtnUpdateKFA.setToolTipText("Alt+K");
        BtnUpdateKFA.setName("btnUpdateKFA"); // NOI18N
        BtnUpdateKFA.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnUpdateKFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateKFAActionPerformed(evt);
            }
        });
        BtnUpdateKFA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnUpdateKFAKeyPressed(evt);
            }
        });
        panelisi1.add(BtnUpdateKFA);

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
        panelisi1.add(BtnKeluar);

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
            }
        });
        scrollPane1.setViewportView(tbObat);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        tampil();
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();

}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        tampil();
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked

}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                java.awt.Window[] windows = java.awt.Window.getWindows();
                for (java.awt.Window window : windows) {
                    if (window instanceof SatuSehatMapingObatAlkes) {
                        SatuSehatMapingObatAlkes parentForm = (SatuSehatMapingObatAlkes) window;
                        parentForm.KFASystem.setText("http://sys-ids.kemkes.go.id/kfa");
                        parentForm.FormSystem.setText("http://terminology.kemkes.go.id/CodeSystem/medication-form");
                        parentForm.NemeratorSystem.setText("http://unitsofmeasure.org");
                        parentForm.DenominatorSystem.setText("http://terminology.hl7.org/CodeSystem/v3-orderableDrugForm");
                        parentForm.RouteSystem.setText("http://www.whocc.no/atc");

                        break;
                    }
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased

    }//GEN-LAST:event_tbObatKeyReleased

    private void TCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCari2KeyPressed
        tampil();
    }//GEN-LAST:event_TCari2KeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        dispose();
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TCari2.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            TCari2.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnCariOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariOnlineActionPerformed
        BtnUpdateKFACombinedActionPerformed(evt);
    }//GEN-LAST:event_BtnCariOnlineActionPerformed

    private void BtnCariOnlineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariOnlineKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariOnlineActionPerformed(null);
        }
    }//GEN-LAST:event_BtnCariOnlineKeyPressed

    private void BtnUpdateKFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateKFAActionPerformed
        // Pencarian KFA dari API Satu Sehat
        Valid.tabelKosong(tabMode);

        if (TCari.getText().trim().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null, "Masukkan keyword pencarian terlebih dahulu!");
            TCari.requestFocus();
            return;
        }

        link = "https://api-satusehat.kemkes.go.id/kfa-v2/";
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
            requestEntity = new HttpEntity(headers);

            // Encode keyword untuk URL
            String keyword = java.net.URLEncoder.encode(TCari.getText().trim(), "UTF-8");
            String registrar = "";

            // Jika ada filter registrar
            if (!TCari2.getText().trim().equals("")) {
                registrar = "&registrar=" + java.net.URLEncoder.encode(TCari2.getText().trim(), "UTF-8");
            }

            // Set limit dari combobox
            String limit = cmbHlm.getSelectedItem().toString();
            if (limit.equals("Semua")) {
                limit = "1000"; // Maksimal 1000 untuk API
            }

            // Panggil API dengan parameter pencarian
            String url = link + "products/all?page=1&size=" + limit + "&product_type=farmasi&name=" + keyword + registrar;
            System.out.println("URL API: " + url);

            json = api.getRest().exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
            root = mapper.readTree(json);

            // Parse response
            JsonNode dataArray = root.path("items").path("data");

            if (dataArray.isArray() && dataArray.size() > 0) {
                for (JsonNode data : dataArray) {
                    // Extract data dari response
                    String kfaCode = data.path("kfa_code").asText();
                    String name = data.path("name").asText();
                    String registrarData = data.path("registrar").asText();
                    String namaDagang = data.path("nama_dagang").asText();

                    // Ambil data dosage form
                    String dosageFormCode = data.path("dosage_form").path("code").asText();
                    String dosageFormName = data.path("dosage_form").path("name").asText();

                    // Untuk data detail seperti route dan ucum, perlu dicek dari product template atau data lain
                    // Karena API KFA v2 memiliki struktur yang berbeda
                    String ucumCode = "";
                    String routeCode = "";
                    String routeName = "";

                    // Tambahkan ke tabel
                    tabMode.addRow(new Object[]{
                        kfaCode,
                        name,
                        dosageFormCode,
                        dosageFormName,
                        ucumCode,
                        "", // Denomina code (kosong karena tidak ada di response)
                        routeCode,
                        routeName,
                        registrarData,
                        namaDagang
                    });
                }

                LCount.setText("" + tabMode.getRowCount());
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Berhasil mengambil " + tabMode.getRowCount() + " data dari API KFA");
            } else {
                LCount.setText("0");
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Data tidak ditemukan untuk keyword: " + TCari.getText().trim());
            }

        } catch (Exception ex) {
            System.out.println("Notifikasi Error Pencarian KFA Online: " + ex);
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Gagal melakukan pencarian online: " + ex.getMessage());
        }
    }//GEN-LAST:event_BtnUpdateKFAActionPerformed

    private void BtnUpdateKFAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnUpdateKFAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnUpdateKFAKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariKfa dialog = new DlgCariKfa(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCariOnline;
    private widget.Button BtnKeluar;
    private widget.Button BtnUpdateKFA;
    private widget.CekBox ChkInput;
    private widget.Label LCount;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox TCari;
    private widget.TextBox TCari2;
    private widget.ComboBox cmbHlm;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private javax.swing.JPanel jPanel2;
    private widget.Label label10;
    private widget.Label label13;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (cmbHlm.getSelectedItem().toString().equals("Semua")) {
                if (TCari.getText().trim().equals("")) {
                    ps = koneksi.prepareStatement(
                            "select satu_sehat_kfa_master.kfa_code, "
                            + "IFNULL(satu_sehat_kfa_master.name, '') as name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.dosage_form_code, '') as dosage_form_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.dosage_form_name, '') as dosage_form_name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.ucum_cs_code, '') as ucum_cs_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.uom_name, '') as uom_name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.rute_pemberian_code, '') as rute_pemberian_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.rute_pemberian_name, '') as rute_pemberian_name, "
                            + "IFNULL(satu_sehat_kfa_master.registrar, '') as registrar, "
                            + "IFNULL(satu_sehat_kfa_master.nama_dagang, '') as nama_dagang "
                            + "from satu_sehat_kfa_master "
                            + "inner join satu_sehat_kfa_master_detail on satu_sehat_kfa_master.kfa_code = satu_sehat_kfa_master_detail.kfa_code "
                            + "order by satu_sehat_kfa_master.kfa_code, satu_sehat_kfa_master.name asc");
                } else {
                    ps = koneksi.prepareStatement(
                            "select satu_sehat_kfa_master.kfa_code, "
                            + "IFNULL(satu_sehat_kfa_master.name, '') as name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.dosage_form_code, '') as dosage_form_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.dosage_form_name, '') as dosage_form_name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.ucum_cs_code, '') as ucum_cs_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.uom_name, '') as uom_name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.rute_pemberian_code, '') as rute_pemberian_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.rute_pemberian_name, '') as rute_pemberian_name, "
                            + "IFNULL(satu_sehat_kfa_master.registrar, '') as registrar, "
                            + "IFNULL(satu_sehat_kfa_master.nama_dagang, '') as nama_dagang "
                            + "from satu_sehat_kfa_master "
                            + "inner join satu_sehat_kfa_master_detail on satu_sehat_kfa_master.kfa_code = satu_sehat_kfa_master_detail.kfa_code "
                            + "where satu_sehat_kfa_master.registrar like ? and satu_sehat_kfa_master.name like ? "
                            + "order by satu_sehat_kfa_master.kfa_code, satu_sehat_kfa_master.name asc");
                    ps.setString(1, "%" + TCari2.getText().trim() + "%");
                    ps.setString(2, "%" + TCari.getText().trim() + "%");
                }
            } else {
                if (TCari.getText().trim().equals("")) {
                    ps = koneksi.prepareStatement(
                            "select satu_sehat_kfa_master.kfa_code, "
                            + "IFNULL(satu_sehat_kfa_master.name, '') as name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.dosage_form_code, '') as dosage_form_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.dosage_form_name, '') as dosage_form_name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.ucum_cs_code, '') as ucum_cs_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.uom_name, '') as uom_name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.rute_pemberian_code, '') as rute_pemberian_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.rute_pemberian_name, '') as rute_pemberian_name, "
                            + "IFNULL(satu_sehat_kfa_master.registrar, '') as registrar, "
                            + "IFNULL(satu_sehat_kfa_master.nama_dagang, '') as nama_dagang "
                            + "from satu_sehat_kfa_master "
                            + "inner join satu_sehat_kfa_master_detail on satu_sehat_kfa_master.kfa_code = satu_sehat_kfa_master_detail.kfa_code "
                            + "order by satu_sehat_kfa_master.kfa_code, satu_sehat_kfa_master.name asc LIMIT ?");
                    ps.setInt(1, Integer.parseInt(cmbHlm.getSelectedItem().toString()));
                } else {
                    ps = koneksi.prepareStatement(
                            "select satu_sehat_kfa_master.kfa_code, "
                            + "IFNULL(satu_sehat_kfa_master.name, '') as name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.dosage_form_code, '') as dosage_form_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.dosage_form_name, '') as dosage_form_name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.ucum_cs_code, '') as ucum_cs_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.uom_name, '') as uom_name, "
                            + "IFNULL(satu_sehat_kfa_master_detail.rute_pemberian_code, '') as rute_pemberian_code, "
                            + "IFNULL(satu_sehat_kfa_master_detail.rute_pemberian_name, '') as rute_pemberian_name, "
                            + "IFNULL(satu_sehat_kfa_master.registrar, '') as registrar, "
                            + "IFNULL(satu_sehat_kfa_master.nama_dagang, '') as nama_dagang "
                            + "from satu_sehat_kfa_master "
                            + "inner join satu_sehat_kfa_master_detail on satu_sehat_kfa_master.kfa_code = satu_sehat_kfa_master_detail.kfa_code "
                            + "where satu_sehat_kfa_master.registrar like ? and satu_sehat_kfa_master.name like ? "
                            + "order by satu_sehat_kfa_master.kfa_code, satu_sehat_kfa_master.name asc LIMIT ?");
                    ps.setString(1, "%" + TCari2.getText().trim() + "%");
                    ps.setString(2, "%" + TCari.getText().trim() + "%");
                    ps.setInt(3, Integer.parseInt(cmbHlm.getSelectedItem().toString()));
                }
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                tabMode.addRow(new Object[]{
                    rs.getString("kfa_code"),
                    rs.getString("name"),
                    rs.getString("dosage_form_code"),
                    rs.getString("dosage_form_name"),
                    rs.getString("ucum_cs_code"),
                    rs.getString("uom_name"),
                    rs.getString("rute_pemberian_code"),
                    rs.getString("rute_pemberian_name"),
                    rs.getString("registrar"),
                    rs.getString("nama_dagang")
                });
            }
            LCount.setText("" + tabMode.getRowCount());

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    System.out.println("Notifikasi rs: " + e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    System.out.println("Notifikasi ps: " + e);
                }
            }
        }
    }

    public JTable getTable() {
        return tbObat;
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 338));
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            //FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void isCek() {
        TCari.requestFocus();

    }

    // Method untuk menampilkan/menyembunyikan loading bar
    private void showLoadingBar(boolean show) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            if (show) {
                // Buat dialog loading
                loadingDialog = new javax.swing.JDialog(this, "Memproses Data", true);
                loadingDialog.setDefaultCloseOperation(javax.swing.JDialog.DO_NOTHING_ON_CLOSE);
                loadingDialog.setSize(400, 120);
                loadingDialog.setLocationRelativeTo(this);
                loadingDialog.setLayout(new java.awt.BorderLayout(10, 10));

                // Label pesan
                loadingLabel = new javax.swing.JLabel("Mempersiapkan...", javax.swing.JLabel.CENTER);
                loadingLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 0, 10));

                // Progress bar
                progressBar = new javax.swing.JProgressBar(0, 100);
                progressBar.setStringPainted(true);
                progressBar.setValue(0);
                progressBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));

                loadingDialog.add(loadingLabel, java.awt.BorderLayout.NORTH);
                loadingDialog.add(progressBar, java.awt.BorderLayout.CENTER);

                // Tampilkan di thread terpisah agar tidak blocking
                new Thread(() -> loadingDialog.setVisible(true)).start();
            } else {
                if (loadingDialog != null) {
                    loadingDialog.setVisible(false);
                    loadingDialog.dispose();
                    loadingDialog = null;
                }
            }
        });
    }

    private void updateLoadingMessage(String message) {
        if (loadingLabel != null) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                loadingLabel.setText(message);
            });
        }
    }

    private void updateLoadingProgress(int progress) {
        if (progressBar != null) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                progressBar.setValue(progress);
            });
        }
    }

    private void BtnUpdateKFACombinedActionPerformed(java.awt.event.ActionEvent evt) {
        Valid.tabelKosong(tabMode);

        if (TCari.getText().trim().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null, "Masukkan keyword pencarian terlebih dahulu!");
            TCari.requestFocus();
            return;
        }

        new Thread(() -> {
            try {
                showLoadingBar(true);
                updateLoadingMessage("Mempersiapkan koneksi ke API...");

                link = "https://api-satusehat.kemkes.go.id/kfa-v2/";

                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
                requestEntity = new HttpEntity(headers);

                String keyword = java.net.URLEncoder.encode(TCari.getText().trim(), "UTF-8");

                String filterRegistrar = TCari2.getText().trim().toLowerCase();

                String limit = cmbHlm.getSelectedItem().toString();
                if (limit.equals("Semua")) {
                    limit = "1000";
                }

                int totalData = 0;
                int totalDetail = 0;
                int totalFailed = 0;
                int totalFiltered = 0;

                int limitInt = Integer.parseInt(limit);
                int maxPages = (int) Math.ceil(limitInt / 100.0);

                updateLoadingMessage("Mengambil data dari API...");

                for (int i = 1; i <= maxPages; i++) {
                    updateLoadingMessage("Mengambil halaman " + i + " dari " + maxPages + "...");

                    String url = link + "products/all?page=" + i + "&size=100&product_type=farmasi&keyword=" + keyword;

                    json = api.getRest().exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();

                    root = mapper.readTree(json);
                    JsonNode dataArray = root.path("items").path("data");

                    if (!dataArray.isArray() || dataArray.size() == 0) {
                        break;
                    }

                    if (dataArray.isArray() && dataArray.size() > 0) {
                        for (JsonNode data : dataArray) {
                            totalData++;

                            String kfaCode = data.path("kfa_code").asText();
                            String name = data.path("name").asText();
                            String registrarData = data.path("registrar").asText();
                            String namaDagang = data.path("nama_dagang").asText();

                            if (!filterRegistrar.equals("")) {
                                String normalizedRegistrar = registrarData.trim().toLowerCase();

                                if (!normalizedRegistrar.contains(filterRegistrar)) {
                                    continue;
                                }
                            }

                            if (totalFiltered >= limitInt) {
                                break;
                            }

                            totalFiltered++;

                            String dosageFormCode = data.path("dosage_form").path("code").asText();
                            String dosageFormName = data.path("dosage_form").path("name").asText();

                            String ucumCsCode = "";
                            String uomName = "";
                            String rutePemberianCode = "";
                            String rutePemberianName = "";

                            updateLoadingMessage("Mengambil detail " + totalFiltered + "/" + limitInt + "...");

                            try {
                                Thread.sleep(200);

                                String detailUrl = link + "products?identifier=kfa&code=" + kfaCode;

                                String jsonDetail = api.getRest().exchange(
                                        detailUrl,
                                        HttpMethod.GET,
                                        requestEntity,
                                        String.class
                                ).getBody();

                                JsonNode detailRoot = mapper.readTree(jsonDetail);
                                JsonNode detail = detailRoot.path("result");

                                if (!detail.isMissingNode() && !detail.isNull()) {
                                    ucumCsCode = detail.path("ucum").path("cs_code").asText();
                                    uomName = detail.path("uom").path("name").asText();
                                    rutePemberianCode = detail.path("rute_pemberian").path("code").asText();
                                    rutePemberianName = detail.path("rute_pemberian").path("name").asText();

                                    totalDetail++;
                                } else {
                                    totalFailed++;
                                }

                            } catch (Exception eDetail) {
                                totalFailed++;
                            }

                            final String fKfaCode = kfaCode;
                            final String fName = name;
                            final String fDosageFormCode = dosageFormCode;
                            final String fDosageFormName = dosageFormName;
                            final String fUcumCsCode = ucumCsCode;
                            final String fUomName = uomName;
                            final String fRutePemberianCode = rutePemberianCode;
                            final String fRutePemberianName = rutePemberianName;
                            final String fRegistrarData = registrarData;
                            final String fNamaDagang = namaDagang;

                            javax.swing.SwingUtilities.invokeLater(() -> {
                                tabMode.addRow(new Object[]{
                                    fKfaCode,
                                    fName,
                                    fDosageFormCode,
                                    fDosageFormName,
                                    fUcumCsCode,
                                    fUomName,
                                    fRutePemberianCode,
                                    fRutePemberianName,
                                    fRegistrarData,
                                    fNamaDagang
                                });
                                LCount.setText("" + tabMode.getRowCount());
                            });

                            int progress = (int) ((totalFiltered * 100.0) / limitInt);
                            updateLoadingProgress(progress);
                        }

                        if (totalFiltered >= limitInt) {
                            break;
                        }
                    }

                    Thread.sleep(500);
                }

                final int finalCount = tabMode.getRowCount();
                final int finalTotal = totalData;
                final int finalFiltered = totalFiltered;

                javax.swing.SwingUtilities.invokeLater(() -> {
                    showLoadingBar(false);

                    if (finalCount > 0) {
                        LCount.setText("" + finalCount);
                        String message = "Berhasil mengambil " + finalCount + " data dari API KFA";
                        if (!filterRegistrar.equals("")) {
                            message += "\n(Total data: " + finalTotal + ", Setelah filter: " + finalFiltered + ")";
                        }
                        javax.swing.JOptionPane.showMessageDialog(null, message);
                    } else {
                        LCount.setText("0");
                        String message = "Data tidak ditemukan untuk keyword: " + TCari.getText().trim();
                        if (!filterRegistrar.equals("")) {
                            message += "\ndengan filter registrar: " + TCari2.getText().trim();
                        }
                        javax.swing.JOptionPane.showMessageDialog(null, message);
                    }
                });

            } catch (Exception ex) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    showLoadingBar(false);
                    javax.swing.JOptionPane.showMessageDialog(null,
                            "Gagal mengambil data KFA: " + ex.getMessage());
                });
            }
        }).start();
    }
}

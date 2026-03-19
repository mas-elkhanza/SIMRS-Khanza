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

package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author dosen
 */
public final class ApotekBPJSDaftarResepObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private int i=0, y=0,reply=0;
    private ApiApotekBPJS api=new ApiApotekBPJS();
    private String URL="",link="",utc="",requestJson="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public ApotekBPJSDaftarResepObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new String[]{
                "No.SEP Asal","No.SEP Apotek","Tanggal SEP","No.Rawat","No.RM","Nama Pasien","No.Kartu","Jenis","ID User SEP","No.Resep","Tgl.Resep","Tgl.Pelayanan","Iterasi","PPK Rujukan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(125);
            }else if(i==2){
                column.setPreferredWidth(117);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(130);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(117);
            }else if(i==11){
                column.setPreferredWidth(117);
            }else if(i==12){
                column.setPreferredWidth(85);
            }else if(i==13){
                column.setPreferredWidth(70);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        ChkAccor.setSelected(false);
        PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
        scrollPaneDetail.setVisible(false); 
        
        try {
            link=koneksiDB.URLAPIAPOTEKBPJS();
        } catch (Exception e) {
            System.out.println("E : "+e);
        }
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        panelGlass6 = new widget.panelisi();
        jLabel17 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabData = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        scrollPane1 = new widget.ScrollPane();
        tbKamar = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        scrollPaneDetail = new widget.ScrollPane();
        tbDetailPenugasan = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbRekapPenugasan = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Daftar Obat Apotek Online BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel17.setText("Resep :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass6.add(jLabel17);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-03-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass6.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-03-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(DTPCari2);

        jLabel16.setText("Keyword :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(jLabel16);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
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
        panelGlass6.add(TCari);

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
        panelGlass6.add(BtnCari);

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
        panelGlass6.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(LCount);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass6.add(BtnHapus);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass6.add(BtnKeluar);

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        TabData.setBackground(new java.awt.Color(255, 255, 253));
        TabData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setName("tbKamar"); // NOI18N
        scrollPane1.setViewportView(tbKamar);

        jPanel2.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        scrollPaneDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Detail Resep Obat :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPaneDetail.setName("scrollPaneDetail"); // NOI18N
        scrollPaneDetail.setOpaque(true);

        tbDetailPenugasan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailPenugasan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailPenugasan.setName("tbDetailPenugasan"); // NOI18N
        scrollPaneDetail.setViewportView(tbDetailPenugasan);

        PanelAccor.add(scrollPaneDetail, java.awt.BorderLayout.CENTER);

        jPanel2.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabData.addTab("Data Resep Obat", jPanel2);

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbRekapPenugasan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRekapPenugasan.setToolTipText("Silahkan klik untuk memilih data yang mau dihapus");
        tbRekapPenugasan.setName("tbRekapPenugasan"); // NOI18N
        scrollPane2.setViewportView(tbRekapPenugasan);

        TabData.addTab("Rekap Resep Obat", scrollPane2);

        internalFrame1.add(TabData, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnKeluar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            for(int i=0;i<tabMode.getRowCount();i++){  
                Sequel.menyimpan("temporary","'"+i+"','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','"+
                                tabMode.getValueAt(i,10).toString()+"','"+
                                tabMode.getValueAt(i,11).toString()+"','"+
                                tabMode.getValueAt(i,12).toString()+"','"+
                                tabMode.getValueAt(i,14).toString()+"','"+
                                tabMode.getValueAt(i,15).toString()+"','"+
                                tabMode.getValueAt(i,16).toString()+"','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Daftar Pelayanan Obat Apotek BPJS"); 
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            //param.put("peserta","No.Peserta : "+NoKartu.getText()+" Nama Peserta : "+NamaPasien.getText());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptApotekBPJSDaftarPelayananKlaim.jasper","report","[ Daftar Pelayanan Apotek BPJS ]","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampil());
            BtnPrint.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            runBackground(() ->tampil());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnCariActionPerformed(null);
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        runBackground(() ->tampil());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbKamar.getSelectedRow() != -1) {
            if (!tbKamar.getValueAt(tbKamar.getSelectedRow(), 8).toString().equals("")) {
                String kode_apotek_bpjs2 = Sequel.cariIsi("select kode_brng_apotek_bpjs from maping_obat_apotek_bpjs where kode_brng='" + tbKamar.getValueAt(tbKamar.getSelectedRow(), 8).toString() + "'");
                try {
                    bodyWithDeleteRequestPerobat(tbKamar.getValueAt(tbKamar.getSelectedRow(), 0).toString(), tbKamar.getValueAt(tbKamar.getSelectedRow(), 2).toString(), kode_apotek_bpjs2, "N", tbKamar.getValueAt(tbKamar.getSelectedRow(), 8).toString());
                } catch (Exception ex) {
                    Logger.getLogger(ApotekBPJSDaftarResepObat.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah yakin No.Apotek " + tbKamar.getValueAt(tbKamar.getSelectedRow(), 0).toString() + " dan semua obatnya mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        ps = koneksi.prepareStatement("SELECT maping_obat_apotek_bpjs.kode_brng_apotek_bpjs, bridging_apotek_bpjs_obat.kd_obat, bridging_apotek_bpjs_obat.racikan FROM bridging_apotek_bpjs_obat INNER JOIN maping_obat_apotek_bpjs ON "
                                + "bridging_apotek_bpjs_obat.kd_obat=maping_obat_apotek_bpjs.kode_brng WHERE bridging_apotek_bpjs_obat.no_sjp='" + tbKamar.getValueAt(tbKamar.getSelectedRow(), 0).toString() + "' AND bridging_apotek_bpjs_obat.racikan='0' ");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            bodyWithDeleteRequest(rs.getString("kode_brng_apotek_bpjs"), "N", rs.getString("kd_obat"));
                        }

                        if (Sequel.cariIsi("select no_sjp from bridging_apotek_bpjs_obat where no_sjp=? AND bridging_apotek_bpjs_obat.racikan='0' ", tbKamar.getValueAt(tbKamar.getSelectedRow(), 0).toString()).isEmpty()) {
                            Sequel.meghapus("bridging_apotek_bpjs_obat", "no_sjp", "racikan", tbKamar.getValueAt(tbKamar.getSelectedRow(), 0).toString(), "1");
                            bodyWithDeleteRequestResep();
                        }

                    } catch (Exception ex) {
                        System.out.println("Notifikasi Bridging : " + ex);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu data yang mau dihapus..!!");
        }
//        if(tbKamar.getSelectedRow()!= -1){
//             if (tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString().equals("")) {
//                    
//                } else {
//                }
//            reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah yakin No.Apotek "+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+" dan semua obatnya mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
//                if (reply == JOptionPane.YES_OPTION) {
//                    try {
//                    ps = koneksi.prepareStatement("SELECT maping_obat_apotek_bpjs.kode_brng_apotek_bpjs, bridging_apotek_bpjs_obat.kd_obat, bridging_apotek_bpjs_obat.racikan FROM bridging_apotek_bpjs_obat INNER JOIN maping_obat_apotek_bpjs ON "+
//                        "bridging_apotek_bpjs_obat.kd_obat=maping_obat_apotek_bpjs.kode_brng WHERE bridging_apotek_bpjs_obat.no_sep='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"' AND bridging_apotek_bpjs_obat.racikan='0' ");
//                    rs = ps.executeQuery();
//                    while (rs.next()) {
//                        bodyWithDeleteRequest(rs.getString("kode_brng_apotek_bpjs"),"N",rs.getString("kd_obat"));    
//                    }
//                    
//                    if (Sequel.cariIsi("select no_sep from bridging_apotek_bpjs_obat where no_sep=? AND bridging_apotek_bpjs_obat.racikan='0' ",tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()).isEmpty()) {
//                         Sequel.meghapus("bridging_apotek_bpjs_obat", "no_sep", "racikan",tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString(),"1");
//                        bodyWithDeleteRequestResep();
//                    }
//                   
//                }catch (Exception ex) {
//                    System.out.println("Notifikasi Bridging : "+ex);
//                }
//            }
//        }else{
//            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
//        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
            });
        } 
    }//GEN-LAST:event_formWindowOpened

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        runBackground(() ->tampil());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        /*if(tbPenugasan.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==true){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(670,HEIGHT));
                scrollPaneDetail.setVisible(true);
                ChkAccor.setVisible(true);
                Valid.tabelKosong(tabModeDetailPenugasan);
                try {
                    ps=koneksi.prepareStatement(
                        "select labkesling_detail_penugasan_pengujian_sampel.kode_parameter,labkesling_parameter_pengujian.nama_parameter,labkesling_parameter_pengujian.metode_pengujian,labkesling_parameter_pengujian.satuan,"+
                        "labkesling_parameter_pengujian.kategori,labkesling_nilai_normal_baku_mutu.nilai_normal from labkesling_detail_penugasan_pengujian_sampel inner join labkesling_parameter_pengujian "+
                        "on labkesling_detail_penugasan_pengujian_sampel.kode_parameter=labkesling_parameter_pengujian.kode_parameter inner join labkesling_nilai_normal_baku_mutu "+
                        "on labkesling_nilai_normal_baku_mutu.kode_parameter=labkesling_parameter_pengujian.kode_parameter where labkesling_detail_penugasan_pengujian_sampel.no_penugasan=? "+
                        "and labkesling_nilai_normal_baku_mutu.kode_sampel=? order by labkesling_detail_penugasan_pengujian_sampel.kode_parameter"
                    );
                    try {
                        ps.setString(1,tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),1).toString());
                        ps.setString(2,tbPenugasan.getValueAt(tbPenugasan.getSelectedRow(),9).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeDetailPenugasan.addRow(new Object[]{
                                rs.getString("kode_parameter"),rs.getString("nama_parameter"),rs.getString("metode_pengujian"),rs.getString("satuan"),rs.getString("kategori"),rs.getString("nilai_normal")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }else if(ChkAccor.isSelected()==false){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
                scrollPaneDetail.setVisible(false);
                ChkAccor.setVisible(true);
            }
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih data penugasan...!!!");
        }*/
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        /*if(TabData.getSelectedIndex()==0){
            runBackground(() ->tampil());
        }else if(TabData.getSelectedIndex()==1){
            runBackground(() ->tampil2());
        }*/
    }//GEN-LAST:event_TabDataMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ApotekBPJSDaftarResepObat dialog = new ApotekBPJSDaftarResepObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabData;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel21;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel2;
    private widget.panelisi panelGlass6;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPaneDetail;
    private widget.Table tbDetailPenugasan;
    private widget.Table tbKamar;
    private widget.Table tbRekapPenugasan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select bridging_resep_apotek_bpjs.no_sep,bridging_resep_apotek_bpjs.no_sep_apotek,bridging_resep_apotek_bpjs.tgl_sep,bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,"+
                "bridging_sep.no_kartu,bridging_resep_apotek_bpjs.kdjenis,bridging_resep_apotek_bpjs.id_user_sep,bridging_resep_apotek_bpjs.no_resep,bridging_resep_apotek_bpjs.tgl_resep,"+
                "bridging_resep_apotek_bpjs.tgl_pelayanan,bridging_resep_apotek_bpjs.iterasi,bridging_resep_apotek_bpjs.kdppkrujukan from bridging_resep_apotek_bpjs "+
                "inner join bridging_sep on bridging_sep.no_sep=bridging_resep_apotek_bpjs.no_sep where bridging_resep_apotek_bpjs.tgl_resep between ? and ? "+
                (TCari.getText().trim().equals("")?"":"and (bridging_resep_apotek_bpjs.no_sep like ? or bridging_resep_apotek_bpjs.no_sep_apotek like ? or bridging_sep.no_rawat like ? or "+
                "bridging_sep.nomr like ? or bridging_resep_apotek_bpjs.kdjenis like ? or bridging_resep_apotek_bpjs.no_resep like ? or bridging_resep_apotek_bpjs.id_user_sep like ?)")
            );
            try {
                ps.setString(1,Valid.SetTglJam(DTPCari1.getSelectedItem()+" 00:00:01"));
                ps.setString(2,Valid.SetTglJam(DTPCari2.getSelectedItem()+" 23:59:59"));
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_sep"),rs.getString("no_sep_apotek"),rs.getString("tgl_sep"),rs.getString("bridging_sep.no_rawat"),rs.getString("bridging_sep.nomr"),rs.getString("nama_pasien"),
                        rs.getString("bridging_sep.no_kartu"),rs.getString("kdjenis"),rs.getString("id_user_sep"),rs.getString("no_resep"),rs.getString("tgl_resep"),rs.getString("tgl_pelayanan"),
                        rs.getString("iterasi"),rs.getString("kdppkrujukan")
                    });
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }    

    public JTable getTable(){
        return tbKamar;
    }
    
    public static class HttpEntityEnclosingDeleteRequest extends HttpEntityEnclosingRequestBase {
        public HttpEntityEnclosingDeleteRequest(final URI uri) {
            super();
            setURI(uri);
        }

        @Override
        public String getMethod() {
            return "DELETE";
        }
    }

    @Test
    public void bodyWithDeleteRequest(String Kode_obat, String tipe_obat, String kode_brng) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        SSLSocketFactory sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme=new Scheme("https",443,sslFactory);
    
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory(){
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        };
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        restTemplate.setRequestFactory(factory);
        
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID", koneksiDB.CONSIDAPIAPOTEKBPJS());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("X-Timestamp", utc);
            headers.add("X-Signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIAPOTEKBPJS());
            URL = link+"/pelayanan/obat/hapus";
            System.out.println("URL : "+URL);
            requestJson ="{ "+
                            "\"nosepapotek\":\""+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"\","+
                            "\"noresep\":\""+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"\","+
                            "\"kodeobat\":\""+Kode_obat+"\","+
                            "\"tipeobat\":\""+tipe_obat+"\"}  ";      
            System.out.println("respone : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("bridging_apotek_bpjs_obat", "no_sjp", "kd_obat",tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString(),kode_brng);
                JOptionPane.showMessageDialog(null,"Obat "+Kode_obat+" "+Sequel.cariIsi("select nama_brng_apotek_bpjs from maping_obat_apotek_bpjs where kode_brng_apotek_bpjs=?",Kode_obat)+" Berhasil diHapus");
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        } catch (Exception e) {   
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    @Test
    public void bodyWithDeleteRequestPerobat(String no_apotek, String no_resep, String Kode_obat, String tipe_obat, String kode_brng) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        SSLSocketFactory sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme=new Scheme("https",443,sslFactory);
    
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory(){
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        };
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        restTemplate.setRequestFactory(factory);
        
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID", koneksiDB.CONSIDAPIAPOTEKBPJS());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("X-Timestamp", utc);
            headers.add("X-Signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIAPOTEKBPJS());
            URL = link+"/pelayanan/obat/hapus";
            System.out.println("URL : "+URL);
            requestJson ="{ "+
                            "\"nosepapotek\":\""+no_apotek+"\","+
                            "\"noresep\":\""+no_resep+"\","+
                            "\"kodeobat\":\""+Kode_obat+"\","+
                            "\"tipeobat\":\""+tipe_obat+"\"}  ";      
            System.out.println("respone : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("bridging_apotek_bpjs_obat", "no_sjp", "kd_obat",tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString());
                JOptionPane.showMessageDialog(null,"Obat "+Kode_obat+" "+Sequel.cariIsi("select nama_brng_apotek_bpjs from maping_obat_apotek_bpjs where kode_brng_apotek_bpjs=?",Kode_obat)+" Berhasil diHapus");
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        } catch (Exception e) {   
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    @Test
    public void bodyWithDeleteRequestResep() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        SSLSocketFactory sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme=new Scheme("https",443,sslFactory);
    
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory(){
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new BPJSDataSEP.HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        };
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        restTemplate.setRequestFactory(factory);
       
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",koneksiDB.CONSIDAPIAPOTEKBPJS());
            utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("X-Timestamp",utc);
	    headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIAPOTEKBPJS());
            URL = link+"/hapusresep";
            requestJson ="{ "+
                            "\"nosjp\":\""+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"\","+
                            "\"refasalsjp\":\""+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"\","+
                            "\"noresep\":\""+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"\"}  ";            
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("bridging_apotek_bpjs", "no_apotek", tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString());
                JOptionPane.showMessageDialog(null,"Resep "+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+" Berhasil diHapus");
                runBackground(() ->tampil());
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        } catch (Exception e) {   
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

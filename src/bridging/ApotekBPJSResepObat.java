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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
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
public final class ApotekBPJSResepObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private int i=0,reply=0;
    private ApiApotekBPJS api=new ApiApotekBPJS();
    private String URL="",link="",utc="",requestJson="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public ApotekBPJSResepObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new String[]{
                "No.SEP Apotek","No.SEP Asal","No.Resep","No.Kartu","Nama Peserta","Jenis Obat","Tgl.Entry","Tgl.Resep","Tgl.Layanan","Faskes Asal","ByTagRsp","ByVerRsp"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);

        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(125);
            }else if(i==2){
                column.setPreferredWidth(58);
            }else if(i==3){
                column.setPreferredWidth(88);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(65);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(80);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
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
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelGlass6 = new widget.panelisi();
        jLabel18 = new widget.Label();
        JnsObat = new javax.swing.JComboBox<>();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel25 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Daftar Resep Obat Apotek BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setName("tbKamar"); // NOI18N
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel18.setText("Jenis :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(38, 23));
        panelGlass6.add(jLabel18);

        JnsObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        JnsObat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "1. Obat PRB", "2. Obat Kronis", "3. Obat Kemoterapi" }));
        JnsObat.setName("JnsObat"); // NOI18N
        JnsObat.setPreferredSize(new java.awt.Dimension(145, 23));
        panelGlass6.add(JnsObat);

        jLabel19.setText("Resep :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(47, 23));
        panelGlass6.add(jLabel19);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-03-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(DTPCari1);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("s.d");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass6.add(jLabel25);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-03-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(63, 23));
        panelGlass6.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
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
                                tabMode.getValueAt(i,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Daftar Pelayanan Obat Apotek BPJS"); 
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
            Valid.MyReportqry("rptApotekBPJSResepObat.jasper","report","[ Daftar Resep Apotek BPJS ]","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        runBackground(() ->tampil());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,DTPCari1,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbKamar.getSelectedRow()!= -1){
            reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah yakin data No.SEP Apotek : "+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+",\nNo.SEP Asal : "+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+",No.Resep : "+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+" mau dihapus?\nData hanya dihapus di sisi BPJS, data yang disimpan lokal tidak ikut terhapus..!!\nGunakan fitur ini untuk perbaikan data Apotek Online BPJS..!!!", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    bodyWithDeleteRequest();
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

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

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        runBackground(() ->tampil());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            runBackground(() ->tampil());
        }else{
            Valid.pindah(evt, BtnCari, BtnHapus);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ApotekBPJSResepObat dialog = new ApotekBPJSResepObat(new javax.swing.JFrame(), true);
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
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JComboBox<String> JnsObat;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel25;
    private widget.Label jLabel6;
    private widget.panelisi panelGlass6;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try {
            Valid.tabelKosong(tabMode);
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    headers.add("x-cons-id",koneksiDB.CONSIDAPIAPOTEKBPJS());
	    utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("x-timestamp",utc);
	    headers.add("x-signature",api.getHmac(utc));
	    headers.add("user_key",koneksiDB.USERKEYAPIAPOTEKBPJS());
            if(JnsObat.getSelectedIndex()==0){
                requestJson = "{"+
                                "\"kdppk\":\""+koneksiDB.KODEPPKAPOTEKBPJS()+"\"," +
                                "\"KdJnsObat\":\"1\"," +
                                "\"JnsTgl\": \"TGLRSP\"," +
                                "\"TglMulai\": \""+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:01\"," +
                                "\"TglAkhir\": \""+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59\"" +
                              "}";            
                requestEntity = new HttpEntity(requestJson,headers);
                URL = link+"/daftarresep";	
                System.out.println(URL);
                System.out.println("JSON Dikirim : "+requestJson);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                if(nameNode.path("code").asText().equals("200")){
                    requestJson=api.Decrypt(root.path("response").asText(),utc);
                    System.out.println("Respon JSON : "+requestJson);
                    response = mapper.readTree(requestJson);
                    if(response.isArray()){
                        if(TCari.getText().trim().equals("")){
                            for(JsonNode list:response){
                                tabMode.addRow(new Object[]{
                                    list.path("NOAPOTIK").asText(),list.path("NOSEP_KUNJUNGAN").asText(),list.path("NORESEP").asText(),list.path("NOKARTU").asText(),list.path("NAMA").asText(),list.path("KDJNSOBAT").asText().replaceAll("1","Obat PRB").replaceAll("2","Obat Kronis").replaceAll("3","Obat Kemoterapi"),
                                    list.path("TGLENTRY").asText(),list.path("TGLRESEP").asText(),list.path("TGLPELRSP").asText(),list.path("FASKESASAL").asText(),Valid.SetAngka(list.path("BYTAGRSP").asDouble()),Valid.SetAngka(list.path("BYVERRSP").asDouble())
                                });
                            }
                        }else{
                            for(JsonNode list:response){
                                if(list.path("NOSEP_KUNJUNGAN").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||list.path("NOAPOTIK").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||
                                        list.path("NOKARTU").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||list.path("NAMA").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||
                                        list.path("FASKESASAL").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())
                                    ){
                                    tabMode.addRow(new Object[]{
                                        list.path("NOAPOTIK").asText(),list.path("NOSEP_KUNJUNGAN").asText(),list.path("NORESEP").asText(),list.path("NOKARTU").asText(),list.path("NAMA").asText(),list.path("KDJNSOBAT").asText().replaceAll("1","Obat PRB").replaceAll("2","Obat Kronis").replaceAll("3","Obat Kemoterapi"),
                                        list.path("TGLENTRY").asText(),list.path("TGLRESEP").asText(),list.path("TGLPELRSP").asText(),list.path("FASKESASAL").asText(),Valid.SetAngka(list.path("BYTAGRSP").asDouble()),Valid.SetAngka(list.path("BYVERRSP").asDouble())
                                    });
                                }
                            }
                        }
                    }
                } 
                
                requestJson = "{"+
                                "\"kdppk\":\""+koneksiDB.KODEPPKAPOTEKBPJS()+"\"," +
                                "\"KdJnsObat\":\"2\"," +
                                "\"JnsTgl\": \"TGLRSP\"," +
                                "\"TglMulai\": \""+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:01\"," +
                                "\"TglAkhir\": \""+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59\"" +
                              "}";            
                requestEntity = new HttpEntity(requestJson,headers);
                URL = link+"/daftarresep";	
                System.out.println(URL);
                System.out.println("JSON Dikirim : "+requestJson);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                if(nameNode.path("code").asText().equals("200")){
                    requestJson=api.Decrypt(root.path("response").asText(),utc);
                    System.out.println("Respon JSON : "+requestJson);
                    response = mapper.readTree(requestJson);
                    if(response.isArray()){
                        if(TCari.getText().trim().equals("")){
                            for(JsonNode list:response){
                                tabMode.addRow(new Object[]{
                                    list.path("NOAPOTIK").asText(),list.path("NOSEP_KUNJUNGAN").asText(),list.path("NORESEP").asText(),list.path("NOKARTU").asText(),list.path("NAMA").asText(),list.path("KDJNSOBAT").asText().replaceAll("1","Obat PRB").replaceAll("2","Obat Kronis").replaceAll("3","Obat Kemoterapi"),
                                    list.path("TGLENTRY").asText(),list.path("TGLRESEP").asText(),list.path("TGLPELRSP").asText(),list.path("FASKESASAL").asText(),Valid.SetAngka(list.path("BYTAGRSP").asDouble()),Valid.SetAngka(list.path("BYVERRSP").asDouble())
                                });
                            }
                        }else{
                            for(JsonNode list:response){
                                if(list.path("NOSEP_KUNJUNGAN").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||list.path("NOAPOTIK").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||
                                        list.path("NOKARTU").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||list.path("NAMA").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||
                                        list.path("FASKESASAL").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())
                                    ){
                                    tabMode.addRow(new Object[]{
                                        list.path("NOAPOTIK").asText(),list.path("NOSEP_KUNJUNGAN").asText(),list.path("NORESEP").asText(),list.path("NOKARTU").asText(),list.path("NAMA").asText(),list.path("KDJNSOBAT").asText().replaceAll("1","Obat PRB").replaceAll("2","Obat Kronis").replaceAll("3","Obat Kemoterapi"),
                                        list.path("TGLENTRY").asText(),list.path("TGLRESEP").asText(),list.path("TGLPELRSP").asText(),list.path("FASKESASAL").asText(),Valid.SetAngka(list.path("BYTAGRSP").asDouble()),Valid.SetAngka(list.path("BYVERRSP").asDouble())
                                    });
                                }
                            }
                        }
                    }
                }
                
                requestJson = "{"+
                                "\"kdppk\":\""+koneksiDB.KODEPPKAPOTEKBPJS()+"\"," +
                                "\"KdJnsObat\":\"3\"," +
                                "\"JnsTgl\": \"TGLRSP\"," +
                                "\"TglMulai\": \""+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:01\"," +
                                "\"TglAkhir\": \""+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59\"" +
                              "}";            
                requestEntity = new HttpEntity(requestJson,headers);
                URL = link+"/daftarresep";	
                System.out.println(URL);
                System.out.println("JSON Dikirim : "+requestJson);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                if(nameNode.path("code").asText().equals("200")){
                    requestJson=api.Decrypt(root.path("response").asText(),utc);
                    System.out.println("Respon JSON : "+requestJson);
                    response = mapper.readTree(requestJson);
                    if(response.isArray()){
                        if(TCari.getText().trim().equals("")){
                            for(JsonNode list:response){
                                tabMode.addRow(new Object[]{
                                    list.path("NOAPOTIK").asText(),list.path("NOSEP_KUNJUNGAN").asText(),list.path("NORESEP").asText(),list.path("NOKARTU").asText(),list.path("NAMA").asText(),list.path("KDJNSOBAT").asText().replaceAll("1","Obat PRB").replaceAll("2","Obat Kronis").replaceAll("3","Obat Kemoterapi"),
                                    list.path("TGLENTRY").asText(),list.path("TGLRESEP").asText(),list.path("TGLPELRSP").asText(),list.path("FASKESASAL").asText(),Valid.SetAngka(list.path("BYTAGRSP").asDouble()),Valid.SetAngka(list.path("BYVERRSP").asDouble())
                                });
                            }
                        }else{
                            for(JsonNode list:response){
                                if(list.path("NOSEP_KUNJUNGAN").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||list.path("NOAPOTIK").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||
                                        list.path("NOKARTU").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||list.path("NAMA").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||
                                        list.path("FASKESASAL").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())
                                    ){
                                    tabMode.addRow(new Object[]{
                                        list.path("NOAPOTIK").asText(),list.path("NOSEP_KUNJUNGAN").asText(),list.path("NORESEP").asText(),list.path("NOKARTU").asText(),list.path("NAMA").asText(),list.path("KDJNSOBAT").asText().replaceAll("1","Obat PRB").replaceAll("2","Obat Kronis").replaceAll("3","Obat Kemoterapi"),
                                        list.path("TGLENTRY").asText(),list.path("TGLRESEP").asText(),list.path("TGLPELRSP").asText(),list.path("FASKESASAL").asText(),Valid.SetAngka(list.path("BYTAGRSP").asDouble()),Valid.SetAngka(list.path("BYVERRSP").asDouble())
                                    });
                                }
                            }
                        }
                    }
                } 
            }else{
                requestJson = "{"+
                                "\"kdppk\":\""+koneksiDB.KODEPPKAPOTEKBPJS()+"\"," +
                                "\"KdJnsObat\":\""+JnsObat.getSelectedItem().toString().substring(0,1)+"\"," +
                                "\"JnsTgl\": \"TGLRSP\"," +
                                "\"TglMulai\": \""+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:01\"," +
                                "\"TglAkhir\": \""+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59\"" +
                              "}";            
                requestEntity = new HttpEntity(requestJson,headers);
                URL = link+"/daftarresep";	
                System.out.println(URL);
                System.out.println("JSON Dikirim : "+requestJson);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                if(nameNode.path("code").asText().equals("200")){
                    requestJson=api.Decrypt(root.path("response").asText(),utc);
                    System.out.println("Respon JSON : "+requestJson);
                    response = mapper.readTree(requestJson);
                    if(response.isArray()){
                        if(TCari.getText().trim().equals("")){
                            for(JsonNode list:response){
                                tabMode.addRow(new Object[]{
                                    list.path("NOAPOTIK").asText(),list.path("NOSEP_KUNJUNGAN").asText(),list.path("NORESEP").asText(),list.path("NOKARTU").asText(),list.path("NAMA").asText(),list.path("KDJNSOBAT").asText().replaceAll("1","Obat PRB").replaceAll("2","Obat Kronis").replaceAll("3","Obat Kemoterapi"),
                                    list.path("TGLENTRY").asText(),list.path("TGLRESEP").asText(),list.path("TGLPELRSP").asText(),list.path("FASKESASAL").asText(),Valid.SetAngka(list.path("BYTAGRSP").asDouble()),Valid.SetAngka(list.path("BYVERRSP").asDouble())
                                });
                            }
                        }else{
                            for(JsonNode list:response){
                                if(list.path("NOSEP_KUNJUNGAN").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||list.path("NOAPOTIK").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||
                                        list.path("NOKARTU").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||list.path("NAMA").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())||
                                        list.path("FASKESASAL").asText().toLowerCase().contains(TCari.getText().trim().toLowerCase())
                                    ){
                                    tabMode.addRow(new Object[]{
                                        list.path("NOAPOTIK").asText(),list.path("NOSEP_KUNJUNGAN").asText(),list.path("NORESEP").asText(),list.path("NOKARTU").asText(),list.path("NAMA").asText(),list.path("KDJNSOBAT").asText().replaceAll("1","Obat PRB").replaceAll("2","Obat Kronis").replaceAll("3","Obat Kemoterapi"),
                                        list.path("TGLENTRY").asText(),list.path("TGLRESEP").asText(),list.path("TGLPELRSP").asText(),list.path("FASKESASAL").asText(),Valid.SetAngka(list.path("BYTAGRSP").asDouble()),Valid.SetAngka(list.path("BYVERRSP").asDouble())
                                    });
                                }
                            }
                        }
                    }
                }else {
                    JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
                } 
            }     
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
            }
        }
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
    public void bodyWithDeleteRequest() throws Exception {
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
            headers.add("x-cons-id", koneksiDB.CONSIDAPIAPOTEKBPJS());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("x-timestamp", utc);
            headers.add("x-signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIAPOTEKBPJS());
            URL = link+"/hapusresep";
            requestJson ="{\"nosjp\":\""+tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()+"\",\"refasalsjp\":\""+tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()+"\",\"noresep\":\""+tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString()+"\"}";            
            System.out.println(URL);
            System.out.println("JSON Dikirim : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                tabMode.removeRow(tbKamar.getSelectedRow());
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

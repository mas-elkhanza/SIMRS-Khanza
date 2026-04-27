/*
  By Mas Elkhanza
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
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public final class SatuSehatCekImagingStudy extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String link="",json="";
    private ApiSatuSehat api=new ApiSatuSehat();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private StringBuilder htmlContent;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    // 0=P, 1=No.Rawat, 2=No.RM, 3=Nama Pasien, 4=Tgl Periksa, 5=Nama Pemeriksaan,
    // 6=ID ServiceRequest, 7=ID ImagingStudy, 8=Status, 9=kd_jenis_prw (hidden), 10=jam (hidden)
    public SatuSehatCekImagingStudy(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new String[]{
                "P","No.Rawat","No.RM","Nama Pasien","Tgl Periksa","Nama Pemeriksaan",
                "ID ServiceRequest","ID ImagingStudy","Status","kd_jenis_prw","jam"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){ return colIndex==0; }
             Class[] types = new Class[] {
                 java.lang.Boolean.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class
             };
             @Override public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
        };
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0) column.setPreferredWidth(20);
            else if(i==1) column.setPreferredWidth(105);
            else if(i==2) column.setPreferredWidth(70);
            else if(i==3) column.setPreferredWidth(150);
            else if(i==4) column.setPreferredWidth(130);
            else if(i==5) column.setPreferredWidth(200);
            else if(i==6) column.setPreferredWidth(220);
            else if(i==7) column.setPreferredWidth(220);
            else if(i==8) column.setPreferredWidth(80);
            else{ column.setMinWidth(0); column.setMaxWidth(0); }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        cmbStatus.addActionListener(e -> runBackground(() -> tampil()));
        try { link=koneksiDB.URLFHIRSATUSEHAT(); } catch (Exception e) { System.out.println("Notif : "+e); }
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true); LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}.isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}");
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }

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
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel18 = new widget.Label();
        cmbStatus = new widget.ComboBox();

        jPopupMenu1.setName("jPopupMenu1");
        ppPilihSemua.setBackground(new java.awt.Color(255,255,254)); ppPilihSemua.setFont(new java.awt.Font("Tahoma",0,11)); ppPilihSemua.setForeground(new java.awt.Color(50,50,50));
        ppPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); ppPilihSemua.setText("Pilih Semua");
        ppPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT); ppPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilihSemua.setName("ppPilihSemua"); ppPilihSemua.setPreferredSize(new java.awt.Dimension(150,26));
        ppPilihSemua.addActionListener(e -> { for(i=0;i<tbObat.getRowCount();i++) tbObat.setValueAt(true,i,0); });
        jPopupMenu1.add(ppPilihSemua);
        ppBersihkan.setBackground(new java.awt.Color(255,255,254)); ppBersihkan.setFont(new java.awt.Font("Tahoma",0,11)); ppBersihkan.setForeground(new java.awt.Color(50,50,50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); ppBersihkan.setText("Hilangkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT); ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); ppBersihkan.setPreferredSize(new java.awt.Dimension(150,26));
        ppBersihkan.addActionListener(e -> { for(i=0;i<tbObat.getRowCount();i++) tbObat.setValueAt(false,i,0); });
        jPopupMenu1.add(ppBersihkan);
        LoadHTML.setBorder(null); LoadHTML.setName("LoadHTML");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); setIconImage(null); setIconImages(null); setUndecorated(true); setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() { public void windowOpened(java.awt.event.WindowEvent evt) { formWindowOpened(evt); } });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240,245,235)), "::[ Cek Imaging Study Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma",0,11), new java.awt.Color(50,50,50)));
        internalFrame1.setFont(new java.awt.Font("Tahoma",0,11)); internalFrame1.setName("internalFrame1"); internalFrame1.setLayout(new java.awt.BorderLayout(1,1));
        Scroll.setComponentPopupMenu(jPopupMenu1); Scroll.setName("Scroll"); Scroll.setOpaque(true);
        tbObat.setComponentPopupMenu(jPopupMenu1); tbObat.setName("tbObat"); Scroll.setViewportView(tbObat);
        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);
        jPanel3.setName("jPanel3"); jPanel3.setOpaque(false); jPanel3.setPreferredSize(new java.awt.Dimension(44,100)); jPanel3.setLayout(new java.awt.BorderLayout(1,1));
        panelGlass8.setName("panelGlass8"); panelGlass8.setPreferredSize(new java.awt.Dimension(44,44)); panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT,5,9));
        jLabel7.setText("Record :"); jLabel7.setName("jLabel7"); jLabel7.setPreferredSize(new java.awt.Dimension(53,23)); panelGlass8.add(jLabel7);
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT); LCount.setText("0"); LCount.setName("LCount"); LCount.setPreferredSize(new java.awt.Dimension(60,23)); panelGlass8.add(LCount);
        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); BtnAll.setMnemonic('M'); BtnAll.setText("Semua"); BtnAll.setToolTipText("Alt+M"); BtnAll.setName("BtnAll"); BtnAll.setPreferredSize(new java.awt.Dimension(100,30));
        BtnAll.addActionListener(e -> { TCari.setText(""); runBackground(() -> tampil()); }); panelGlass8.add(BtnAll);
        BtnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); BtnKirim.setMnemonic('C'); BtnKirim.setText("Cek Status"); BtnKirim.setToolTipText("Alt+C"); BtnKirim.setName("BtnKirim"); BtnKirim.setPreferredSize(new java.awt.Dimension(100,30));
        BtnKirim.addActionListener(e -> BtnCekStatusActionPerformed(e)); panelGlass8.add(BtnKirim);
        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); BtnPrint.setMnemonic('T'); BtnPrint.setText("Cetak"); BtnPrint.setToolTipText("Alt+T"); BtnPrint.setName("BtnPrint"); BtnPrint.setPreferredSize(new java.awt.Dimension(100,30));
        BtnPrint.addActionListener(e -> BtnPrintActionPerformed(e)); panelGlass8.add(BtnPrint);
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); BtnKeluar.setMnemonic('K'); BtnKeluar.setText("Keluar"); BtnKeluar.setToolTipText("Alt+K"); BtnKeluar.setName("BtnKeluar"); BtnKeluar.setPreferredSize(new java.awt.Dimension(100,30));
        BtnKeluar.addActionListener(e -> dispose()); panelGlass8.add(BtnKeluar);
        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);
        panelGlass9.setName("panelGlass9"); panelGlass9.setPreferredSize(new java.awt.Dimension(44,44)); panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT,5,9));
        jLabel15.setText("Tgl.Periksa :"); jLabel15.setName("jLabel15"); jLabel15.setPreferredSize(new java.awt.Dimension(75,23)); panelGlass9.add(jLabel15);
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"08-04-2026"})); DTPCari1.setDisplayFormat("dd-MM-yyyy"); DTPCari1.setName("DTPCari1"); DTPCari1.setOpaque(false); DTPCari1.setPreferredSize(new java.awt.Dimension(95,23)); panelGlass9.add(DTPCari1);
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); jLabel17.setText("s.d."); jLabel17.setName("jLabel17"); jLabel17.setPreferredSize(new java.awt.Dimension(24,23)); panelGlass9.add(jLabel17);
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"08-04-2026"})); DTPCari2.setDisplayFormat("dd-MM-yyyy"); DTPCari2.setName("DTPCari2"); DTPCari2.setOpaque(false); DTPCari2.setPreferredSize(new java.awt.Dimension(95,23)); panelGlass9.add(DTPCari2);
        jLabel16.setText("Key Word :"); jLabel16.setName("jLabel16"); jLabel16.setPreferredSize(new java.awt.Dimension(70,23)); panelGlass9.add(jLabel16);
        TCari.setName("TCari"); TCari.setPreferredSize(new java.awt.Dimension(210,23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() { public void keyPressed(java.awt.event.KeyEvent evt) { if(evt.getKeyCode()==KeyEvent.VK_ENTER) runBackground(() -> tampil()); } });
        panelGlass9.add(TCari);
        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); BtnCari.setMnemonic('6'); BtnCari.setToolTipText("Alt+6"); BtnCari.setName("BtnCari"); BtnCari.setPreferredSize(new java.awt.Dimension(28,23));
        BtnCari.addActionListener(e -> runBackground(() -> tampil())); panelGlass9.add(BtnCari);
        jLabel18.setText("Status:"); jLabel18.setName("jLabel18"); jLabel18.setPreferredSize(new java.awt.Dimension(40,23)); panelGlass9.add(jLabel18);
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Semua","Belum tersedia","Sudah tersedia"})); cmbStatus.setName("cmbStatus"); panelGlass9.add(cmbStatus);
        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);
        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCekStatusActionPerformed(java.awt.event.ActionEvent evt) {
        for(i=0;i<tbObat.getRowCount();i++){
            if(tbObat.getValueAt(i,0).toString().equals("true")){
                String idServiceRequest=tbObat.getValueAt(i,6).toString();
                String noRawat=tbObat.getValueAt(i,1).toString();
                String kdJenisPrw=tbObat.getValueAt(i,9).toString();
                String tglPeriksa=tbObat.getValueAt(i,4).toString().substring(0,10);
                String jam=tbObat.getValueAt(i,10).toString();

                if(idServiceRequest.equals("")){
                    tbObat.setValueAt("ServiceRequest belum terkirim",i,8);
                    tbObat.setValueAt(false,i,0);
                    continue;
                }

                try{
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                    requestEntity = new HttpEntity(headers);
                    String url=link+"/ImagingStudy?based-on=ServiceRequest/"+idServiceRequest;
                    System.out.println("URL : "+url);
                    json=api.getRest().exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
                    System.out.println("Result JSON : "+json);
                    root = mapper.readTree(json);

                    String idImaging="";
                    String statusImaging="";
                    int total=root.path("total").asInt(0);

                    if(total>0){
                        for(JsonNode entry:root.path("entry")){
                            idImaging=entry.path("resource").path("id").asText();
                            statusImaging=entry.path("resource").path("status").asText();
                            break;
                        }
                    }

                    if(!idImaging.equals("")){
                        tbObat.setValueAt(idImaging,i,7);
                        tbObat.setValueAt(statusImaging,i,8);
                        Sequel.queryu2tf("insert into satu_sehat_imagingstudy values(?,?,?,?,?,?) on duplicate key update id_imagingstudy=values(id_imagingstudy),status=values(status)",
                            6,new String[]{noRawat,kdJenisPrw,tglPeriksa,jam,idImaging,statusImaging});
                    }else{
                        tbObat.setValueAt("",i,7);
                        tbObat.setValueAt("Belum ada",i,8);
                    }
                    tbObat.setValueAt(false,i,0);
                }catch(Exception e){
                    tbObat.setValueAt("Error: "+e.getMessage(),i,8);
                    tbObat.setValueAt(false,i,0);
                    System.out.println("Notifikasi Cek ImagingStudy : "+e);
                }
            }
        }
    }

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            htmlContent.append("<tr class='isi'><td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl Periksa</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID ServiceRequest</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID ImagingStudy</b></td><td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status</b></td></tr>");
            for (i = 0; i < tabMode.getRowCount(); i++) {
                htmlContent.append("<tr class='isi'><td valign='top'>"+tbObat.getValueAt(i,1)+"</td><td valign='top'>"+tbObat.getValueAt(i,3)+"</td><td valign='top'>"+tbObat.getValueAt(i,4)+"</td><td valign='top'>"+tbObat.getValueAt(i,5)+"</td><td valign='top'>"+tbObat.getValueAt(i,6)+"</td><td valign='top'>"+tbObat.getValueAt(i,7)+"</td><td valign='top'>"+tbObat.getValueAt(i,8)+"</td></tr>");
            }
            LoadHTML.setText("<html><table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+htmlContent.toString()+"</table></html>");
            htmlContent=null;
            File g = new File("file2.css"); BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}.isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}");
            bg.close();
            File f = new File("DataSatuSehatCekImagingStudy.html"); BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /><table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'><tr class='isi2'><td valign='top' align='center'><font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br><font size='2' face='Tahoma'>CEK IMAGING STUDY SATU SEHAT<br><br></font></td></tr></table>"));
            bw.close(); Desktop.getDesktop().browse(f.toURI());
        }catch(Exception e){ System.out.println("Notifikasi : "+e); }
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override public void insertUpdate(DocumentEvent e) { if(TCari.getText().length()>2) runBackground(() -> tampil()); }
                @Override public void removeUpdate(DocumentEvent e) { if(TCari.getText().length()>2) runBackground(() -> tampil()); }
                @Override public void changedUpdate(DocumentEvent e) { if(TCari.getText().length()>2) runBackground(() -> tampil()); }
            });
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatCekImagingStudy dialog = new SatuSehatCekImagingStudy(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() { @Override public void windowClosing(java.awt.event.WindowEvent e) { System.exit(0); } });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnKirim;
    private widget.Button BtnPrint;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
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
        Valid.tabelKosong(tabMode);
        try{
            try {
                ps = koneksi.prepareStatement("CREATE TABLE IF NOT EXISTS satu_sehat_imagingstudy (no_rawat varchar(17) NOT NULL, kd_jenis_prw varchar(15) NOT NULL, tgl_periksa date NOT NULL, jam time NOT NULL, id_imagingstudy varchar(50) DEFAULT NULL, status varchar(20) DEFAULT NULL, PRIMARY KEY (no_rawat,kd_jenis_prw,tgl_periksa,jam)) ENGINE=InnoDB DEFAULT CHARSET=latin1");
                ps.execute(); ps.close();
            } catch (Exception e) {}

            ps=koneksi.prepareStatement(
                   "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_jenis_prw,"+
                   "jns_perawatan_radiologi.nm_perawatan,"+
                   "ifnull(satu_sehat_servicerequest_radiologi.id_servicerequest,'') as id_servicerequest,"+
                   "ifnull(satu_sehat_imagingstudy.id_imagingstudy,'') as id_imagingstudy,"+
                   "ifnull(satu_sehat_imagingstudy.status,'') as status_imaging "+
                   "from periksa_radiologi "+
                   "inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                   "left join permintaan_radiologi on permintaan_radiologi.no_rawat=periksa_radiologi.no_rawat "+
                   "left join permintaan_pemeriksaan_radiologi on permintaan_pemeriksaan_radiologi.noorder=permintaan_radiologi.noorder and permintaan_pemeriksaan_radiologi.kd_jenis_prw=periksa_radiologi.kd_jenis_prw "+
                   "left join satu_sehat_servicerequest_radiologi on satu_sehat_servicerequest_radiologi.noorder=permintaan_pemeriksaan_radiologi.noorder and satu_sehat_servicerequest_radiologi.kd_jenis_prw=permintaan_pemeriksaan_radiologi.kd_jenis_prw "+
                   "left join satu_sehat_imagingstudy on satu_sehat_imagingstudy.no_rawat=periksa_radiologi.no_rawat and satu_sehat_imagingstudy.kd_jenis_prw=periksa_radiologi.kd_jenis_prw and satu_sehat_imagingstudy.tgl_periksa=periksa_radiologi.tgl_periksa and satu_sehat_imagingstudy.jam=periksa_radiologi.jam "+
                   "where periksa_radiologi.tgl_periksa between ? and ? "+
                   (cmbStatus.getSelectedItem().toString().equals("Belum tersedia")?"and (satu_sehat_imagingstudy.id_imagingstudy is null or satu_sehat_imagingstudy.id_imagingstudy='') ":"")+
                   (cmbStatus.getSelectedItem().toString().equals("Sudah tersedia")?"and satu_sehat_imagingstudy.id_imagingstudy is not null and satu_sehat_imagingstudy.id_imagingstudy!='' ":"")+
                   (TCari.getText().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ?) ")+
                   "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%"); ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("tgl_periksa")+" "+rs.getString("jam"),rs.getString("nm_perawatan"),
                        rs.getString("id_servicerequest"),rs.getString("id_imagingstudy"),
                        rs.getString("status_imaging").equals("")?(rs.getString("id_servicerequest").equals("")?"ServiceRequest belum terkirim":"Belum dicek"):rs.getString("status_imaging"),
                        rs.getString("kd_jenis_prw"),rs.getString("jam")
                    });
                }
            } catch (Exception e) { System.out.println("Notif : "+e);
            } finally{ if(rs!=null) rs.close(); if(ps!=null) ps.close(); }
        }catch(Exception e){ System.out.println("Notifikasi : "+e); }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void isCek(){
        BtnKirim.setEnabled(akses.getsatu_sehat_kirim_servicerequest_radiologi());
        BtnPrint.setEnabled(akses.getsatu_sehat_kirim_servicerequest_radiologi());
    }

    public JTable getTable(){ return tbObat; }

    private void runBackground(Runnable task) {
        if (ceksukses) return; if (executor.isShutdown() || executor.isTerminated()) return; if (!isDisplayable()) return;
        ceksukses = true; setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try { executor.submit(() -> { try { task.run(); } finally { ceksukses = false; SwingUtilities.invokeLater(() -> { if (isDisplayable()) setCursor(Cursor.getDefaultCursor()); }); } });
        } catch (RejectedExecutionException ex) { ceksukses = false; }
    }

    @Override public void dispose() { executor.shutdownNow(); super.dispose(); }
}

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

/**
 *
 * @author dosen
 */
public final class SatuSehatKirimRMERawatInap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0;
    private String link="",json="",idpasien="",iddokter="";
    private ApiSatuSehat api=new ApiSatuSehat();
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private SatuSehatCekNIK cekViaSatuSehat=new SatuSehatCekNIK();
    private StringBuilder htmlContent;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public SatuSehatKirimRMERawatInap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        // 0=P, 1=No.Rawat, 2=No.RM, 3=Nama Pasien, 4=No.KTP Pasien, 5=Kode Dokter,
        // 6=Nama Dokter, 7=No.KTP Dokter, 8=ID Encounter, 9=Tgl Registrasi,
        // 10=Diagnosa Awal, 11=Keluhan Utama, 12=Diagnosa Utama, 13=Kd Diagnosa Utama,
        // 14=Keadaan Pulang, 15=ID Composition,
        // 16-23=Diagnosa Sekunder 1-4 (nama+kode), 24-31=Prosedur utama+sekunder 1-3 (nama+kode),
        // 32=Alasan, 33=Pemeriksaan Fisik, 34=Jalannya Penyakit, 35=Pemeriksaan Penunjang,
        // 36=Hasil Laborat, 37=Tindakan dan Operasi, 38=Obat di RS, 39=Obat Pulang,
        // 40=Alergi, 41=Diet, 42=Edukasi, 43=Cara Keluar, 44=Dilanjutkan, 45=Kontrol
        tabMode=new DefaultTableModel(null,new String[]{
                "P","No.Rawat","No.RM","Nama Pasien","No.KTP Pasien","Kode Dokter",
                "Nama Dokter","No.KTP Dokter","ID Encounter","Tgl Registrasi",
                "Diagnosa Awal","Keluhan Utama","Diagnosa Utama","Kd Diagnosa Utama",
                "Keadaan Pulang","ID Composition",
                "Diagnosa Sekunder1","Kd Diagnosa Sekunder1","Diagnosa Sekunder2","Kd Diagnosa Sekunder2",
                "Diagnosa Sekunder3","Kd Diagnosa Sekunder3","Diagnosa Sekunder4","Kd Diagnosa Sekunder4",
                "Prosedur Utama","Kd Prosedur Utama","Prosedur Sekunder1","Kd Prosedur Sekunder1",
                "Prosedur Sekunder2","Kd Prosedur Sekunder2","Prosedur Sekunder3","Kd Prosedur Sekunder3",
                "Alasan","Pemeriksaan Fisik","Jalannya Penyakit","Pemeriksaan Penunjang",
                "Hasil Laborat","Tindakan dan Operasi","Obat di RS","Obat Pulang",
                "Alergi","Diet","Edukasi","Cara Keluar","Dilanjutkan","Kontrol"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 46; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(110);
            }else if(i==8){
                column.setPreferredWidth(215);
            }else if(i==9){
                column.setPreferredWidth(130);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(80);
            }else if(i==15){
                column.setPreferredWidth(220);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));

        cmbStatus.addActionListener(e -> runBackground(() -> tampil()));

        try {
            link=koneksiDB.URLFHIRSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }

        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
        );
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
        BtnUpdate = new widget.Button();
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
        ppPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11));
        ppPilihSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        ppPilihSemua.setText("Pilih Semua");
        ppPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilihSemua.setName("ppPilihSemua");
        ppPilihSemua.setPreferredSize(new java.awt.Dimension(150, 26));
        ppPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { ppPilihSemuaActionPerformed(evt); }
        });
        jPopupMenu1.add(ppPilihSemua);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11));
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        ppBersihkan.setText("Hilangkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan");
        ppBersihkan.setPreferredSize(new java.awt.Dimension(150, 26));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { ppBersihkanActionPerformed(evt); }
        });
        jPopupMenu1.add(ppBersihkan);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null); setIconImages(null); setUndecorated(true); setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) { formWindowOpened(evt); }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengiriman Data RME Rawat Inap Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 11));
        internalFrame1.setName("internalFrame1");
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1); Scroll.setName("Scroll"); Scroll.setOpaque(true);
        tbObat.setComponentPopupMenu(jPopupMenu1); tbObat.setName("tbObat");
        Scroll.setViewportView(tbObat);
        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8");
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :"); jLabel7.setName("jLabel7"); jLabel7.setPreferredSize(new java.awt.Dimension(53, 23));
        panelGlass8.add(jLabel7);
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT); LCount.setText("0"); LCount.setName("LCount"); LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(LCount);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        BtnAll.setMnemonic('M'); BtnAll.setText("Semua"); BtnAll.setToolTipText("Alt+M"); BtnAll.setName("BtnAll"); BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(e -> BtnAllActionPerformed(e));
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() { public void keyPressed(java.awt.event.KeyEvent evt) { BtnAllKeyPressed(evt); } });
        panelGlass8.add(BtnAll);

        BtnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png")));
        BtnKirim.setMnemonic('K'); BtnKirim.setText("Kirim"); BtnKirim.setToolTipText("Alt+K"); BtnKirim.setName("BtnKirim"); BtnKirim.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKirim.addActionListener(e -> BtnKirimActionPerformed(e));
        panelGlass8.add(BtnKirim);

        BtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png")));
        BtnUpdate.setMnemonic('U'); BtnUpdate.setText("Update"); BtnUpdate.setToolTipText("Alt+U"); BtnUpdate.setName("BtnUpdate"); BtnUpdate.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnUpdate.addActionListener(e -> BtnUpdateActionPerformed(e));
        panelGlass8.add(BtnUpdate);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        BtnPrint.setMnemonic('T'); BtnPrint.setText("Cetak"); BtnPrint.setToolTipText("Alt+T"); BtnPrint.setName("BtnPrint"); BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(e -> BtnPrintActionPerformed(e));
        panelGlass8.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K'); BtnKeluar.setText("Keluar"); BtnKeluar.setToolTipText("Alt+K"); BtnKeluar.setName("BtnKeluar"); BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(e -> BtnKeluarActionPerformed(e));
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() { public void keyPressed(java.awt.event.KeyEvent evt) { BtnKeluarKeyPressed(evt); } });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));
        jLabel15.setText("Tgl.Registrasi :"); jLabel15.setName("jLabel15"); jLabel15.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass9.add(jLabel15);
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-04-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy"); DTPCari1.setName("DTPCari1"); DTPCari1.setOpaque(false); DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); jLabel17.setText("s.d."); jLabel17.setName("jLabel17"); jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel17);
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-04-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy"); DTPCari2.setName("DTPCari2"); DTPCari2.setOpaque(false); DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);
        jLabel16.setText("Key Word :"); jLabel16.setName("jLabel16"); jLabel16.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel16);
        TCari.setName("TCari"); TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() { public void keyPressed(java.awt.event.KeyEvent evt) { TCariKeyPressed(evt); } });
        panelGlass9.add(TCari);
        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.setMnemonic('6'); BtnCari.setToolTipText("Alt+6"); BtnCari.setName("BtnCari"); BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(e -> BtnCariActionPerformed(e));
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() { public void keyPressed(java.awt.event.KeyEvent evt) { BtnCariKeyPressed(evt); } });
        panelGlass9.add(BtnCari);
        jLabel18.setText("Status Kirim:"); jLabel18.setName("jLabel18"); jLabel18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel18);
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Belum dikirim", "Sudah dikirim" }));
        cmbStatus.setName("cmbStatus");
        panelGlass9.add(cmbStatus);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);
        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){ dispose(); }else{ Valid.pindah(evt,BtnPrint,BtnKeluar); }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosa Utama</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Pulang</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID Composition</b></td>"+
                "</tr>"
            );
            for (i = 0; i < tabMode.getRowCount(); i++) {
                htmlContent.append(
                    "<tr class='isi'>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                    "</tr>");
            }
            LoadHTML.setText("<html><table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+htmlContent.toString()+"</table></html>");
            htmlContent=null;
            File g = new File("file2.css"); BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}.isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}");
            bg.close();
            File f = new File("DataSatuSehatRMERawatInap.html"); BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /><table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'><tr class='isi2'><td valign='top' align='center'><font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br><font size='2' face='Tahoma'>DATA PENGIRIMAN SATU SEHAT RME RAWAT INAP<br><br></font></td></tr></table>"));
            bw.close(); Desktop.getDesktop().browse(f.toURI());
        }catch(Exception e){ System.out.println("Notifikasi : "+e); }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){ BtnCariActionPerformed(null); }
        else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){ BtnCariActionPerformed(null); }
        else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){ BtnKeluar.requestFocus(); }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){ tbObat.requestFocus(); }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        runBackground(() ->tampil());
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){ BtnCariActionPerformed(null); }else{ Valid.pindah(evt,TCari,BtnPrint); }
    }//GEN-LAST:event_BtnCariKeyPressed

    private String esc(String s){ return s.replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t"," ").replaceAll("\"","'"); }

    private String buildCompositionJson(int row, String idpasien, String iddokter, String existingId) {
        String noRawat=tbObat.getValueAt(row,1).toString();
        String nmPasien=tbObat.getValueAt(row,3).toString();
        String idEncounter=tbObat.getValueAt(row,8).toString();
        String tglReg=tbObat.getValueAt(row,9).toString();
        String nmDokter=tbObat.getValueAt(row,6).toString();

        String diagnosaAwal=esc(tbObat.getValueAt(row,10).toString());
        String keluhanUtama=esc(tbObat.getValueAt(row,11).toString());
        String keadaanPulang=tbObat.getValueAt(row,14).toString();
        String alasan=esc(tbObat.getValueAt(row,32).toString());
        String pemeriksaanFisik=esc(tbObat.getValueAt(row,33).toString());
        String jalannyaPenyakit=esc(tbObat.getValueAt(row,34).toString());
        String pemeriksaanPenunjang=esc(tbObat.getValueAt(row,35).toString());
        String hasilLaborat=esc(tbObat.getValueAt(row,36).toString());
        String tindakanOperasi=esc(tbObat.getValueAt(row,37).toString());
        String obatDiRS=esc(tbObat.getValueAt(row,38).toString());
        String obatPulang=esc(tbObat.getValueAt(row,39).toString());
        String alergi=esc(tbObat.getValueAt(row,40).toString());
        String diet=esc(tbObat.getValueAt(row,41).toString());
        String edukasi=esc(tbObat.getValueAt(row,42).toString());
        String caraKeluar=tbObat.getValueAt(row,43).toString();
        String dilanjutkan=tbObat.getValueAt(row,44).toString();
        String kontrol=tbObat.getValueAt(row,45).toString();

        // Build diagnosa narrative
        StringBuilder diagNarrative=new StringBuilder();
        String dxUtama=tbObat.getValueAt(row,12).toString();
        String kdDxUtama=tbObat.getValueAt(row,13).toString();
        diagNarrative.append("Diagnosa Utama: ").append(dxUtama).append(" (").append(kdDxUtama).append(")");
        for(int d=0;d<4;d++){
            String dxNama=tbObat.getValueAt(row,16+(d*2)).toString();
            String dxKode=tbObat.getValueAt(row,17+(d*2)).toString();
            if(!dxNama.equals("")){
                diagNarrative.append("<br>Diagnosa Sekunder: ").append(dxNama).append(" (").append(dxKode).append(")");
            }
        }

        // Build prosedur narrative
        StringBuilder prosNarrative=new StringBuilder();
        String prosUtama=tbObat.getValueAt(row,24).toString();
        String kdProsUtama=tbObat.getValueAt(row,25).toString();
        if(!prosUtama.equals("")){
            prosNarrative.append("Prosedur Utama: ").append(prosUtama).append(" (").append(kdProsUtama).append(")");
        }
        for(int p=0;p<3;p++){
            String prNama=tbObat.getValueAt(row,26+(p*2)).toString();
            String prKode=tbObat.getValueAt(row,27+(p*2)).toString();
            if(!prNama.equals("")){
                if(prosNarrative.length()>0) prosNarrative.append("<br>");
                prosNarrative.append("Prosedur Sekunder: ").append(prNama).append(" (").append(prKode).append(")");
            }
        }
        if(!tindakanOperasi.equals("")){
            if(prosNarrative.length()>0) prosNarrative.append("<br>");
            prosNarrative.append("Tindakan/Operasi: ").append(tindakanOperasi);
        }

        // Query Condition references
        StringBuilder conditionEntries=new StringBuilder();
        try{
            ps2=koneksi.prepareStatement("select id_condition from satu_sehat_condition where no_rawat=? and id_condition is not null and id_condition<>''");
            ps2.setString(1,noRawat); rs2=ps2.executeQuery();
            while(rs2.next()){
                if(conditionEntries.length()>0) conditionEntries.append(",");
                conditionEntries.append("{\"reference\":\"Condition/").append(rs2.getString("id_condition")).append("\"}");
            }
            rs2.close();ps2.close();
        }catch(Exception e){}

        // Query Procedure references
        StringBuilder procedureEntries=new StringBuilder();
        try{
            ps2=koneksi.prepareStatement("select id_procedure from satu_sehat_procedure where no_rawat=? and id_procedure is not null and id_procedure<>''");
            ps2.setString(1,noRawat); rs2=ps2.executeQuery();
            while(rs2.next()){
                if(procedureEntries.length()>0) procedureEntries.append(",");
                procedureEntries.append("{\"reference\":\"Procedure/").append(rs2.getString("id_procedure")).append("\"}");
            }
            rs2.close();ps2.close();
        }catch(Exception e){}

        // Build JSON
        StringBuilder sb=new StringBuilder();
        sb.append("{\"resourceType\":\"Composition\",");
        if(existingId!=null&&!existingId.equals("")){
            sb.append("\"id\":\"").append(existingId).append("\",");
        }
        sb.append("\"identifier\":{\"system\":\"http://sys-ids.kemkes.go.id/composition/").append(koneksiDB.IDSATUSEHAT()).append("\",\"value\":\"").append(noRawat.replaceAll("/","")).append("\"},");
        sb.append("\"status\":\"final\",");
        sb.append("\"type\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"18842-5\",\"display\":\"Discharge summary\"}]},");
        sb.append("\"category\":[{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"LP173421-1\",\"display\":\"Report\"}]}],");
        sb.append("\"subject\":{\"reference\":\"Patient/").append(idpasien).append("\",\"display\":\"").append(nmPasien).append("\"},");
        sb.append("\"encounter\":{\"reference\":\"Encounter/").append(idEncounter).append("\",\"display\":\"Kunjungan ").append(nmPasien).append(" pada tanggal ").append(tglReg).append(" dengan nomor kunjungan ").append(noRawat).append("\"},");
        sb.append("\"date\":\"").append(tglReg.replaceAll(" ","T")).append("+07:00\",");
        sb.append("\"author\":[{\"reference\":\"Practitioner/").append(iddokter).append("\",\"display\":\"").append(nmDokter).append("\"}],");
        sb.append("\"title\":\"Resume Medis Rawat Inap\",");
        sb.append("\"custodian\":{\"reference\":\"Organization/").append(koneksiDB.IDSATUSEHAT()).append("\"},");
        sb.append("\"section\":[");

        // Section 1: Keluhan Utama / Anamnesis
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"10154-3\",\"display\":\"Chief complaint Narrative\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"Alasan Masuk: ").append(alasan.equals("")?"-":alasan).append("<br>Diagnosa Awal: ").append(diagnosaAwal.equals("")?"-":diagnosaAwal).append("<br>Keluhan Utama: ").append(keluhanUtama.equals("")?"-":keluhanUtama).append("\"}},");

        // Section 2: Pemeriksaan Fisik
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"29545-1\",\"display\":\"Physical findings Narrative\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"").append(pemeriksaanFisik.equals("")?"-":pemeriksaanFisik).append("\"}},");

        // Section 3: Diagnosa
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"29548-5\",\"display\":\"Diagnosis\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"").append(esc(diagNarrative.toString())).append("\"}");
        if(conditionEntries.length()>0){ sb.append(",\"entry\":[").append(conditionEntries).append("]"); }
        sb.append("},");

        // Section 4: Prosedur/Tindakan
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"29554-3\",\"display\":\"Procedure Narrative\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"").append(prosNarrative.length()>0?esc(prosNarrative.toString()):"-").append("\"}");
        if(procedureEntries.length()>0){ sb.append(",\"entry\":[").append(procedureEntries).append("]"); }
        sb.append("},");

        // Section 5: Perjalanan Penyakit
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"8648-8\",\"display\":\"Hospital course Narrative\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"").append(jalannyaPenyakit.equals("")?"-":jalannyaPenyakit).append("\"}},");

        // Section 6: Hasil Pemeriksaan Penunjang & Lab
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"30954-2\",\"display\":\"Relevant diagnostic tests/laboratory data Narrative\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"Pemeriksaan Penunjang: ").append(pemeriksaanPenunjang.equals("")?"-":pemeriksaanPenunjang).append("<br>Hasil Laborat: ").append(hasilLaborat.equals("")?"-":hasilLaborat).append("\"}},");

        // Section 7: Obat
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"10160-0\",\"display\":\"History of Medication use Narrative\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"Obat Selama di RS: ").append(obatDiRS.equals("")?"-":obatDiRS).append("<br>Obat Pulang: ").append(obatPulang.equals("")?"-":obatPulang).append("\"}},");

        // Section 8: Alergi
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"48765-2\",\"display\":\"Allergies and adverse reactions Document\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"").append(alergi.equals("")?"-":alergi).append("\"}},");

        // Section 9: Diet
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"42344-2\",\"display\":\"Discharge diet (narrative)\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"").append(diet.equals("")?"-":diet).append("\"}},");

        // Section 10: Edukasi & Rencana Tindak Lanjut
        sb.append("{\"code\":{\"coding\":[{\"system\":\"http://loinc.org\",\"code\":\"18776-5\",\"display\":\"Plan of care note\"}]},");
        sb.append("\"text\":{\"status\":\"additional\",\"div\":\"Edukasi: ").append(edukasi.equals("")?"-":edukasi);
        sb.append("<br>Cara Keluar: ").append(caraKeluar);
        sb.append("<br>Keadaan Pulang: ").append(keadaanPulang);
        sb.append("<br>Dilanjutkan: ").append(dilanjutkan);
        if(!kontrol.equals("")&&!kontrol.equals("0000-00-00 00:00:00")){
            sb.append("<br>Kontrol: ").append(kontrol);
        }
        sb.append("\"}}");

        sb.append("]}");
        return sb.toString();
    }

    private void BtnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){
            if(tbObat.getValueAt(i,0).toString().equals("true")&&(!tbObat.getValueAt(i,4).toString().equals(""))&&(!tbObat.getValueAt(i,7).toString().equals(""))&&(!tbObat.getValueAt(i,8).toString().equals(""))&&tbObat.getValueAt(i,15).toString().equals("")){
                try {
                    iddokter=cekViaSatuSehat.tampilIDParktisi(tbObat.getValueAt(i,7).toString());
                    idpasien=cekViaSatuSehat.tampilIDPasien(tbObat.getValueAt(i,4).toString());
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = buildCompositionJson(i, idpasien, iddokter, null);
                        System.out.println("URL : "+link+"/Composition");
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Composition", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.menyimpantf2("satu_sehat_rme_rawat_inap","?,?","RME Rawat Inap",2,new String[]{
                                tbObat.getValueAt(i,1).toString(),response.asText()
                            })==true){
                                tbObat.setValueAt(response.asText(),i,15);
                                tbObat.setValueAt(false,i,0);
                            }
                        }
                    }catch(Exception e){ System.out.println("Notifikasi Bridging : "+e); }
                } catch (Exception e) { System.out.println("Notifikasi : "+e); }
            }
        }
    }//GEN-LAST:event_BtnKirimActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){ tbObat.setValueAt(true,i,0); }
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){ tbObat.setValueAt(false,i,0); }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){
            if(tbObat.getValueAt(i,0).toString().equals("true")&&(!tbObat.getValueAt(i,4).toString().equals(""))&&(!tbObat.getValueAt(i,7).toString().equals(""))&&(!tbObat.getValueAt(i,8).toString().equals(""))&&(!tbObat.getValueAt(i,15).toString().equals(""))){
                try {
                    iddokter=cekViaSatuSehat.tampilIDParktisi(tbObat.getValueAt(i,7).toString());
                    idpasien=cekViaSatuSehat.tampilIDPasien(tbObat.getValueAt(i,4).toString());
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = buildCompositionJson(i, idpasien, iddokter, tbObat.getValueAt(i,15).toString());
                        System.out.println("URL : "+link+"/Composition/"+tbObat.getValueAt(i,15).toString());
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Composition/"+tbObat.getValueAt(i,15).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        tbObat.setValueAt(false,i,0);
                    }catch(Exception e){ System.out.println("Notifikasi Bridging : "+e); }
                } catch (Exception e) { System.out.println("Notifikasi : "+e); }
            }
        }
    }//GEN-LAST:event_BtnUpdateActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText(""); runBackground(() ->tampil());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){ TCari.setText(""); runBackground(() ->tampil()); }else{ Valid.pindah(evt, BtnPrint, BtnKeluar); }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override public void insertUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ runBackground(() ->tampil()); } }
                @Override public void removeUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ runBackground(() ->tampil()); } }
                @Override public void changedUpdate(DocumentEvent e) { if(TCari.getText().length()>2){ runBackground(() ->tampil()); } }
            });
        }
    }//GEN-LAST:event_formWindowOpened

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatKirimRMERawatInap dialog = new SatuSehatKirimRMERawatInap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnUpdate;
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
                ps = koneksi.prepareStatement("CREATE TABLE IF NOT EXISTS satu_sehat_rme_rawat_inap (no_rawat varchar(17) NOT NULL, id_composition varchar(50) DEFAULT NULL, PRIMARY KEY (no_rawat)) ENGINE=InnoDB DEFAULT CHARSET=latin1");
                ps.execute(); ps.close();
            } catch (Exception e) {}

            ps=koneksi.prepareStatement(
                   "select reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                   "pasien.nm_pasien,pasien.no_ktp,resume_pasien_ranap.kd_dokter,dokter.nm_dokter,pegawai.no_ktp as ktpdokter,"+
                   "satu_sehat_encounter.id_encounter,"+
                   "resume_pasien_ranap.diagnosa_awal,resume_pasien_ranap.alasan,resume_pasien_ranap.keluhan_utama,"+
                   "resume_pasien_ranap.pemeriksaan_fisik,resume_pasien_ranap.jalannya_penyakit,"+
                   "resume_pasien_ranap.pemeriksaan_penunjang,resume_pasien_ranap.hasil_laborat,"+
                   "resume_pasien_ranap.tindakan_dan_operasi,resume_pasien_ranap.obat_di_rs,resume_pasien_ranap.obat_pulang,"+
                   "resume_pasien_ranap.diagnosa_utama,resume_pasien_ranap.kd_diagnosa_utama,"+
                   "resume_pasien_ranap.diagnosa_sekunder,resume_pasien_ranap.kd_diagnosa_sekunder,"+
                   "resume_pasien_ranap.diagnosa_sekunder2,resume_pasien_ranap.kd_diagnosa_sekunder2,"+
                   "resume_pasien_ranap.diagnosa_sekunder3,resume_pasien_ranap.kd_diagnosa_sekunder3,"+
                   "resume_pasien_ranap.diagnosa_sekunder4,resume_pasien_ranap.kd_diagnosa_sekunder4,"+
                   "resume_pasien_ranap.prosedur_utama,resume_pasien_ranap.kd_prosedur_utama,"+
                   "resume_pasien_ranap.prosedur_sekunder,resume_pasien_ranap.kd_prosedur_sekunder,"+
                   "resume_pasien_ranap.prosedur_sekunder2,resume_pasien_ranap.kd_prosedur_sekunder2,"+
                   "resume_pasien_ranap.prosedur_sekunder3,resume_pasien_ranap.kd_prosedur_sekunder3,"+
                   "resume_pasien_ranap.alergi,resume_pasien_ranap.diet,resume_pasien_ranap.edukasi,"+
                   "resume_pasien_ranap.cara_keluar,resume_pasien_ranap.keadaan,"+
                   "resume_pasien_ranap.dilanjutkan,resume_pasien_ranap.kontrol,"+
                   "ifnull(satu_sehat_rme_rawat_inap.id_composition,'') as id_composition "+
                   "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join resume_pasien_ranap on resume_pasien_ranap.no_rawat=reg_periksa.no_rawat "+
                   "inner join dokter on dokter.kd_dokter=resume_pasien_ranap.kd_dokter "+
                   "inner join pegawai on pegawai.nik=dokter.kd_dokter "+
                   "inner join satu_sehat_encounter on satu_sehat_encounter.no_rawat=reg_periksa.no_rawat "+
                   "left join satu_sehat_rme_rawat_inap on satu_sehat_rme_rawat_inap.no_rawat=reg_periksa.no_rawat "+
                   "where reg_periksa.tgl_registrasi between ? and ? "+
                   (cmbStatus.getSelectedItem().toString().equals("Belum dikirim")?"and (satu_sehat_rme_rawat_inap.id_composition is null or satu_sehat_rme_rawat_inap.id_composition='') ":"")+
                   (cmbStatus.getSelectedItem().toString().equals("Sudah dikirim")?"and satu_sehat_rme_rawat_inap.id_composition is not null and satu_sehat_rme_rawat_inap.id_composition!='' ":"")+
                   (TCari.getText().equals("")?"":"and (reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                   "pasien.nm_pasien like ? or pasien.no_ktp like ? or dokter.nm_dokter like ? or resume_pasien_ranap.diagnosa_utama like ?) "));
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("no_ktp"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("ktpdokter"),
                        rs.getString("id_encounter"),
                        rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"),
                        rs.getString("diagnosa_awal"),rs.getString("keluhan_utama"),
                        rs.getString("diagnosa_utama"),rs.getString("kd_diagnosa_utama"),
                        rs.getString("keadaan"),rs.getString("id_composition"),
                        rs.getString("diagnosa_sekunder"),rs.getString("kd_diagnosa_sekunder"),
                        rs.getString("diagnosa_sekunder2"),rs.getString("kd_diagnosa_sekunder2"),
                        rs.getString("diagnosa_sekunder3"),rs.getString("kd_diagnosa_sekunder3"),
                        rs.getString("diagnosa_sekunder4"),rs.getString("kd_diagnosa_sekunder4"),
                        rs.getString("prosedur_utama"),rs.getString("kd_prosedur_utama"),
                        rs.getString("prosedur_sekunder"),rs.getString("kd_prosedur_sekunder"),
                        rs.getString("prosedur_sekunder2"),rs.getString("kd_prosedur_sekunder2"),
                        rs.getString("prosedur_sekunder3"),rs.getString("kd_prosedur_sekunder3"),
                        rs.getString("alasan"),rs.getString("pemeriksaan_fisik"),rs.getString("jalannya_penyakit"),
                        rs.getString("pemeriksaan_penunjang"),rs.getString("hasil_laborat"),
                        rs.getString("tindakan_dan_operasi"),rs.getString("obat_di_rs"),rs.getString("obat_pulang"),
                        rs.getString("alergi"),rs.getString("diet"),rs.getString("edukasi"),
                        rs.getString("cara_keluar"),rs.getString("dilanjutkan"),rs.getString("kontrol")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){ rs.close(); }
                if(ps!=null){ ps.close(); }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void isCek(){
        BtnKirim.setEnabled(akses.getsatu_sehat_kirim_condition());
        BtnUpdate.setEnabled(akses.getsatu_sehat_kirim_condition());
        BtnPrint.setEnabled(akses.getsatu_sehat_kirim_condition());
    }

    public JTable getTable(){
        return tbObat;
    }

    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;
        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            executor.submit(() -> {
                try { task.run(); } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> { if (isDisplayable()) { setCursor(Cursor.getDefaultCursor()); } });
                }
            });
        } catch (RejectedExecutionException ex) { ceksukses = false; }
    }

    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

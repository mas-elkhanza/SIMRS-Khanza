/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 *
 * @author perpustakaan
 */
public final class SisruteRujukanMasukan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String URL="",link="",norm="",statusreg="",statuspasien="",norujuk="";
    private final Properties prop = new Properties();
    private SisruteApi api=new SisruteApi();
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public SisruteRujukanMasukan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        tabMode=new DefaultTableModel(null,new String[]{
                "No.","RM Faskes","Nama Pasien","Kontak","Alamat","Tempat Lahir","Tgl.Lahir",
                "J.K.","No.Kartu JKN","NIK","No.Rujuk","Kode Asal","Nama Faskes Asal",
                "Kode Tujuan","Nama Faskes Tujuan","Jenis Rujukan","Alasan","Alasan Lainnya","Status",
                "Tgl.Rujuk","Dignosa Rujuk","Anamnesis & Pemeriksaan Fisik","Kesadaran",
                "Tensi","Nadi","Suhu","Respirasi","Nyeri","Keadaan Umum","Alergi",
                "Laboratorium","Radiologi","Terapi/Tindakan","Stts.Pasien","Stts.Registrasi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 35; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(160);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(30);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(130);
            }else if(i==10){
                column.setPreferredWidth(110);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(120);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(110);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(160);
            }else if(i==22){
                column.setPreferredWidth(70);
            }else if(i==23){
                column.setPreferredWidth(40);
            }else if(i==24){
                column.setPreferredWidth(40);
            }else if(i==25){
                column.setPreferredWidth(40);
            }else if(i==26){
                column.setPreferredWidth(55);
            }else if(i==27){
                column.setPreferredWidth(50);
            }else if(i==28){
                column.setPreferredWidth(140);
            }else if(i==29){
                column.setPreferredWidth(100);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(150);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(70);
            }else if(i==34){
                column.setPreferredWidth(100);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml")); 
            link=prop.getProperty("URLAPISISRUTE");
        } catch (Exception e) {
            System.out.println("E : "+e);
        }
        
        jam(); 
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
        tbBangsal = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        ChkJln = new widget.CekBox();
        Tanggal = new widget.Tanggal();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        panelGlass5 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel11 = new widget.Label();
        BtnEdit = new widget.Button();
        BtnCari1 = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rujukan Masuk Sisrute ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbBangsal.setName("tbBangsal"); // NOI18N
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setText("Tanggal :");
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass8.add(ChkJln);

        Tanggal.setEditable(false);
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(Tanggal);

        jLabel12.setText("Status Registrasi :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass8.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Teregistrasi", "Belum Teregistrasi" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setOpaque(false);
        cmbStatus.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass8.add(cmbStatus);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass8.add(LCount);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass5.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

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
        panelGlass5.add(BtnCari);

        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(20, 23));
        panelGlass5.add(jLabel11);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Regist");
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
        panelGlass5.add(BtnEdit);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari1.setMnemonic('E');
        BtnCari1.setText("Data SEP");
        BtnCari1.setToolTipText("Alt+E");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelGlass5.add(BtnCari1);

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
        panelGlass5.add(BtnKeluar);

        jPanel3.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,Tanggal);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));            
    tampil();
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            //Valid.pindah(evt, TKd, BtnPrint);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(tbBangsal.getSelectedRow()!= -1){
            if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),41).toString().equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                BPJSCekNoRujukanPCare form=new BPJSCekNoRujukanPCare(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.SetRujukan(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),4).toString());
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, SEP telah terbit...!!!!");
                BtnCari.requestFocus();
            }                    
        }else{            
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data rujukan...!!!!");
            BtnCari.requestFocus();
        }  
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSDataSEP form=new BPJSDataSEP(null,false);
        form.isCek();
        form.tampil();
        form.tutupInput();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        Valid.pindah(evt, BtnCari, BtnKeluar);
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SisruteRujukanMasukan dialog = new SisruteRujukanMasukan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnEdit;
    private widget.Button BtnKeluar;
    private widget.CekBox ChkJln;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel6;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass8;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try {
            URL = link+"/rujukan?tanggal="+Valid.SetTgl(Tanggal.getSelectedItem()+"");	

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-cons-id",prop.getProperty("IDSISRUTE"));
	    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
	    headers.add("X-signature",api.getHmac()); 
	    headers.add("Content-type","application/json");             
	    headers.add("Content-length",null);            
	    HttpEntity requestEntity = new HttpEntity(headers);
            //System.out.println(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("status");
            System.out.println("Result : "+root.path("status").asText());
            if(nameNode.asText().equals("200")){
                Valid.tabelKosong(tabMode);
                JsonNode response = root.path("data");
                if(response.isArray()){
                    i=1;
                    for(JsonNode list:response){
                        norujuk=Sequel.cariIsi("select no_rujuk from rujuk_masuk where no_rujuk=?",list.path("RUJUKAN").path("NOMOR").asText());
                        if(!norujuk.equals("")){
                            statusreg="Sudah Teregistrasi";
                        }else{
                            statusreg="Belum Teregistrasi";
                        }
                        norm=Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",list.path("PASIEN").path("NIK").asText());
                        statuspasien="Baru";
                        if(!norm.equals("")){
                            statuspasien="Lama";
                        }
                        if(statusreg.contains(cmbStatus.getSelectedItem().toString().replaceAll("Semua",""))){
                            tabMode.addRow(new Object[]{
                                i+".",list.path("PASIEN").path("NORM").asText(),list.path("PASIEN").path("NAMA").asText(),
                                list.path("PASIEN").path("KONTAK").asText(),list.path("PASIEN").path("ALAMAT").asText(),
                                list.path("PASIEN").path("TEMPAT_LAHIR").asText(),list.path("PASIEN").path("TANGGAL_LAHIR").asText(),
                                list.path("PASIEN").path("JENIS_KELAMIN").path("NAMA").asText().substring(0,1),
                                list.path("PASIEN").path("NO_KARTU_JKN").asText(),list.path("PASIEN").path("NIK").asText(),
                                list.path("RUJUKAN").path("NOMOR").asText(),list.path("RUJUKAN").path("FASKES_ASAL").path("KODE").asText(),
                                list.path("RUJUKAN").path("FASKES_ASAL").path("NAMA").asText(),list.path("RUJUKAN").path("FASKES_TUJUAN").path("KODE").asText(),
                                list.path("RUJUKAN").path("FASKES_TUJUAN").path("NAMA").asText(),list.path("RUJUKAN").path("JENIS_RUJUKAN").path("NAMA").asText(),
                                list.path("RUJUKAN").path("ALASAN").asText(),list.path("RUJUKAN").path("ALASAN_LAINNYA").asText(),
                                list.path("RUJUKAN").path("STATUS").path("NAMA").asText(),list.path("RUJUKAN").path("TANGGAL").asText(),
                                list.path("RUJUKAN").path("DIAGNOSA").asText(),list.path("KONDISI_UMUM").path("ANAMNESIS_DAN_PEMERIKSAAN_FISIK").asText(),
                                list.path("KONDISI_UMUM").path("KESADARAN").path("NAMA").asText(),list.path("KONDISI_UMUM").path("TEKANAN_DARAH").asText(),
                                list.path("KONDISI_UMUM").path("FREKUENSI_NADI").asText(),list.path("KONDISI_UMUM").path("SUHU").asText(),
                                list.path("KONDISI_UMUM").path("PERNAPASAN").asText(),list.path("KONDISI_UMUM").path("NYERI").path("NAMA").asText(),
                                list.path("KONDISI_UMUM").path("KEADAAN_UMUM").asText(),list.path("KONDISI_UMUM").path("ALERGI").asText(),
                                list.path("PENUNJANG").path("LABORATORIUM").asText(),list.path("PENUNJANG").path("RADIOLOGI").asText(),
                                list.path("PENUNJANG").path("TERAPI_ATAU_TINDAKAN").asText(),statuspasien,statusreg
                            });
                            i++;
                        }                            
                    }
                }                                       
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Kemenkes terputus....!");
            }else if(ex.toString().contains("404")){
                JOptionPane.showMessageDialog(rootPane,"Tidak ditemukan....!");
            }else if(ex.toString().contains("500")){
                JOptionPane.showMessageDialog(rootPane,"Server interenal error....!");
            }
        }
    }

    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();
                
                nilai_jam = now.getHours();
                nilai_menit = now.getMinutes();
                nilai_detik = now.getSeconds();
                

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
                if(ChkJln.isSelected()==true){
                    
                }
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    public void isCek(){
        BtnCari1.setEnabled(var.getbpjs_sep());
    }

}

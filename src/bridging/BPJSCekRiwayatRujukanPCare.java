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

import fungsi.WarnaTable;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import fungsi.koneksiDB;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import simrskhanza.DlgPasien;

/**
 *
 * @author dosen
 */
public final class BPJSCekRiwayatRujukanPCare extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private int i=0;
    private DlgPasien pasien=new DlgPasien(null,false);
    private ApiBPJS api=new ApiBPJS();
    private String URL="",link="";
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
        
    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public BPJSCekRiwayatRujukanPCare(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"","",""};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(170);
            }else if(i==2){
                column.setPreferredWidth(450);
            }
        }
        
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBPJSCekRiwayatPeserta")){
                    if(pasien.getTable().getSelectedRow()!= -1){                   
                        if(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),20).toString().equals("")){
                            JOptionPane.showMessageDialog(rootPane,"Maaf pasien tidak punya Nomor Kartu...!");
                        }else{
                            NoKartu.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),20).toString());
                            NamaPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),2).toString());
                        }                            
                    }  
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBPJSCekRiwayatPeserta")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        try {
            link=koneksiDB.URLAPIBPJS();
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
        jLabel16 = new widget.Label();
        NoKartu = new widget.TextBox();
        NamaPasien = new widget.TextBox();
        btnPasien = new widget.Button();
        BtnCari = new widget.Button();
        jLabel17 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pencarian Riwayat Rujukan PCare di VClaim Berdasarkan Nomor Kepesertaan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamar.setName("tbKamar"); // NOI18N
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel16.setText("No.Kartu :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(jLabel16);

        NoKartu.setEditable(false);
        NoKartu.setName("NoKartu"); // NOI18N
        NoKartu.setPreferredSize(new java.awt.Dimension(130, 23));
        NoKartu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKartuKeyPressed(evt);
            }
        });
        panelGlass6.add(NoKartu);

        NamaPasien.setEditable(false);
        NamaPasien.setName("NamaPasien"); // NOI18N
        NamaPasien.setPreferredSize(new java.awt.Dimension(240, 23));
        panelGlass6.add(NamaPasien);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('5');
        btnPasien.setToolTipText("Alt+5");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        btnPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPasienKeyPressed(evt);
            }
        });
        panelGlass6.add(btnPasien);

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

        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass6.add(jLabel17);

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
        panelGlass6.add(BtnPrint);

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
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString()+"','"+
                                tabMode.getValueAt(r,1).toString()+"','"+
                                tabMode.getValueAt(r,2).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Pengadaan Ipsrs"); 
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("peserta","No.Peserta : "+NoKartu.getText()+" Nama Peserta : "+NamaPasien.getText());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptCariBPJS10RiwayatPeserta.jasper","report","[ Pencarian Riwayat Rujukan Peserta ]",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void NoKartuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKartuKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnPrint.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            tampil(NoKartu.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnCariActionPerformed(null);
        }
    }//GEN-LAST:event_NoKartuKeyPressed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        akses.setform("DlgBPJSCekRiwayatPeserta");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt,NoKartu,BtnPrint);
    }//GEN-LAST:event_btnPasienKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil(NoKartu.getText());
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,NoKartu,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSCekRiwayatRujukanPCare dialog = new BPJSCekRiwayatRujukanPCare(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox NamaPasien;
    private widget.TextBox NoKartu;
    private widget.ScrollPane Scroll;
    private widget.Button btnPasien;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.panelisi panelGlass6;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil(String nomorrujukan) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
	    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
	    headers.add("X-Signature",api.getHmac());
	    requestEntity = new HttpEntity(headers);
            URL = link+"/Rujukan/List/Peserta/"+nomorrujukan;	
	    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            if(nameNode.path("code").asText().equals("200")){
                Valid.tabelKosong(tabMode);
                response = root.path("response").path("rujukan");
                if(response.isArray()){
                    i=1;
                    for(JsonNode list:response){
                        tabMode.addRow(new Object[]{
                            i+".","Diagnosa",": "+list.path("diagnosa").path("kode").asText()+" "+list.path("diagnosa").path("nama").asText()
                        });                   
                        tabMode.addRow(new Object[]{
                            "","Keluhan",": "+list.path("keluhan").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","No.Kunjungan",": "+list.path("noKunjungan").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","Pelayanan",": "+list.path("pelayanan").path("kode").asText()+" "+list.path("pelayanan").path("nama").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","Peserta",": "
                        }); 
                        tabMode.addRow(new Object[]{
                            "","       COB",": "
                        });
                        tabMode.addRow(new Object[]{
                            "","              Nama Asuransi",": "+list.path("peserta").path("cob").path("nmAsuransi").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","              No. Asuransi",": "+list.path("peserta").path("cob").path("noAsuransi").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","              Tanggal TAT",": "+list.path("peserta").path("cob").path("tglTAT").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","              Tanggal TMT",": "+list.path("peserta").path("cob").path("tglTMT").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","       Hak Kelas",": "+list.path("peserta").path("hakKelas").path("kode").asText()+". "+list.path("peserta").path("hakKelas").path("keterangan").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Informasi",": "
                        });
                        tabMode.addRow(new Object[]{
                            "","              Dinsos",": "+list.path("peserta").path("informasi").path("dinsos").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","              No.SKTM",": "+list.path("peserta").path("informasi").path("noSKTM").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","              Prolanis PRB",": "+list.path("peserta").path("informasi").path("prolanisPRB").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","       Jenis Peserta",": "+list.path("peserta").path("jenisPeserta").path("kode").asText()+". "+list.path("peserta").path("jenisPeserta").path("keterangan").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Medical Record",": "
                        });
                        tabMode.addRow(new Object[]{
                            "","              Nomor RM",": "+list.path("peserta").path("mr").path("noMR").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","              Nomor Telp",": "+list.path("peserta").path("mr").path("noTelepon").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Nama Pasien",": "+list.path("peserta").path("nama").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       NIK",": "+list.path("peserta").path("nik").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       No.Kartu",": "+list.path("peserta").path("noKartu").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Pisa",": "+list.path("peserta").path("pisa").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Provider",": "+list.path("peserta").path("provUmum").path("kdProvider").asText()+" "+list.path("peserta").path("provUmum").path("nmProvider").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Jenis Kelamin",": "+list.path("peserta").path("sex").asText().replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")
                        });
                        tabMode.addRow(new Object[]{
                            "","       Status Peserta",": "+list.path("peserta").path("statusPeserta").path("kode").asText()+" "+list.path("peserta").path("statusPeserta").path("keterangan").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Tgl. Cetak Kartu",": "+list.path("peserta").path("tglCetakKartu").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Tgl. Lahir",": "+list.path("peserta").path("tglLahir").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Tgl. TAT",": "+list.path("peserta").path("tglTAT").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Tgl. TMT",": "+list.path("peserta").path("tglTMT").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Umur Saat Pelayanan",": "+list.path("peserta").path("umur").path("umurSaatPelayanan").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","       Umur Sekarang",": "+list.path("peserta").path("umur").path("umurSekarang").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","Poli Rujukan",": "+list.path("poliRujukan").path("kode").asText()+" "+list.path("poliRujukan").path("nama").asText()
                        }); 
                        tabMode.addRow(new Object[]{
                            "","Provider Perujuk",": "+list.path("provPerujuk").path("kode").asText()+" "+list.path("provPerujuk").path("nama").asText()
                        });
                        tabMode.addRow(new Object[]{
                            "","Tanggal Kunjungan",": "+list.path("tglKunjungan").asText()
                        });  
                        i++;
                    }
                }
                                       
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
            }
        }
    }   
 
}

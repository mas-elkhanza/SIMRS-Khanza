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
import fungsi.validasi;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import simrskhanza.DlgIGD;
import simrskhanza.DlgPasien;
import simrskhanza.DlgReg;

/**
 *
 * @author dosen
 */
public final class BPJSHistoriPelayanan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private int i=0;
    private DlgPasien pasien=new DlgPasien(null,false);
    private BPJSApi api=new BPJSApi();
    private String URL="",link="";
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private String pilihan;
        
    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public BPJSHistoriPelayanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"No.","Diagnosa","Jenis Pelayanan","Kelas Rawat","Nama Peserta","No.Kartu","No.SEP","No.Rujukan","Poli","PPK Pelayanan","Pulang SEP","Tgl.SEP"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(230);
            }else if(i==2){
                column.setPreferredWidth(88);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(160);
            }else if(i==5){
                column.setPreferredWidth(85);
            }else if(i==6){
                column.setPreferredWidth(125);
            }else if(i==7){
                column.setPreferredWidth(125);
            }else if(i==8){
                column.setPreferredWidth(115);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setPreferredWidth(65);
            }
        }
        
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoKartu.setDocument(new batasInput((byte)100).getKata(NoKartu));
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){                   
                    if(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),20).toString().equals("")){
                        JOptionPane.showMessageDialog(rootPane,"Maaf pasien tidak punya Nomor Kartu...!");
                    }else{
                        NoKartu.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),20).toString());
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
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pasien.dispose();
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
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        NoKartu = new widget.TextBox();
        btnPasien = new widget.Button();
        BtnCari = new widget.Button();
        jLabel17 = new widget.Label();
        BtnRegist = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Pencarian Histori Pelayanan BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jLabel19.setText("Periode :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass6.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-06-2019" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-06-2019" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(DTPCari2);

        jLabel16.setText("No.Kartu :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(jLabel16);

        NoKartu.setName("NoKartu"); // NOI18N
        NoKartu.setPreferredSize(new java.awt.Dimension(130, 23));
        NoKartu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKartuKeyPressed(evt);
            }
        });
        panelGlass6.add(NoKartu);

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
        jLabel17.setPreferredSize(new java.awt.Dimension(15, 23));
        panelGlass6.add(jLabel17);

        BtnRegist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnRegist.setMnemonic('G');
        BtnRegist.setText("Regist");
        BtnRegist.setToolTipText("Alt+G");
        BtnRegist.setName("BtnRegist"); // NOI18N
        BtnRegist.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRegist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistActionPerformed(evt);
            }
        });
        BtnRegist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRegistKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnRegist);

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
        }else{Valid.pindah(evt,BtnRegist,BtnKeluar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void NoKartuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKartuKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnRegist.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPasienActionPerformed(null);
        }
    }//GEN-LAST:event_NoKartuKeyPressed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt,NoKartu,BtnRegist);
    }//GEN-LAST:event_btnPasienKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoKartu.getText().equals("")){
            Valid.textKosong(NoKartu,"No.Kartu");
        }else{
            tampil(NoKartu.getText());
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,NoKartu,BtnRegist);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnRegistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistActionPerformed
        if(NoKartu.getText().equals("")){
            Valid.textKosong(NoKartu,"No.Kartu");
        }else{
            try{
                pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih cara registrasi..!!","Pilihan Registrasi",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Via Cek No.Kartu VClaim","Via Cek Rujukan Kartu PCare di VClaim","Via Cek Rujukan Kartu RS di VClaim","Via Cek No.Rujukan PCare di VClaim","Via Cek No.Rujukan RS di VClaim"},"Via Cek No.Kartu VClaim");
                switch (pilihan) {
                    case "Via Cek No.Kartu VClaim":
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        BPJSCekKartu form=new BPJSCekKartu(null,false);
                        form.isCek();
                        form.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                        form.setLocationRelativeTo(internalFrame1);
                        form.SetNoKartu(NoKartu.getText());
                        form.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                    case "Via Cek Rujukan Kartu PCare di VClaim":
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        BPJSCekRujukanKartuPCare form2=new BPJSCekRujukanKartuPCare(null,false);
                        form2.isCek();
                        form2.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                        form2.setLocationRelativeTo(internalFrame1);
                        form2.SetNoKartu(NoKartu.getText());
                        form2.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                    case "Via Cek Rujukan Kartu RS di VClaim":
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        BPJSCekRujukanKartuRS form3=new BPJSCekRujukanKartuRS(null,false);
                        form3.isCek();
                        form3.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                        form3.setLocationRelativeTo(internalFrame1);
                        form3.SetNoKartu(NoKartu.getText());
                        form3.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                    case "Via Cek No.Rujukan PCare di VClaim":
                        if(tbKamar.getSelectedRow()> -1){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekNoRujukanPCare form4=new BPJSCekNoRujukanPCare(null,false);
                            form4.isCek();
                            form4.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                            form4.setLocationRelativeTo(internalFrame1);
                            form4.SetRujukan(tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString());
                            form4.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
                        }
                        break;
                    case "Via Cek No.Rujukan RS di VClaim":
                        if(tbKamar.getSelectedRow()> -1){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekNoRujukanRS form5=new BPJSCekNoRujukanRS(null,false);
                            form5.isCek();
                            form5.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                            form5.setLocationRelativeTo(internalFrame1);
                            form5.SetRujukan(tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString());
                            form5.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
                        }
                        break;
                    
                }
            }catch(Exception e){
                System.out.println("Notif : "+e);
            }
        } 
    }//GEN-LAST:event_BtnRegistActionPerformed

    private void BtnRegistKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRegistKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnRegistActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnRegistKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSHistoriPelayanan dialog = new BPJSHistoriPelayanan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnRegist;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox NoKartu;
    private widget.ScrollPane Scroll;
    private widget.Button btnPasien;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
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
            URL = link+"/monitoring/HistoriPelayanan/NoKartu/"+nomorrujukan+"/tglAwal/"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"/tglAkhir/"+Valid.SetTgl(DTPCari2.getSelectedItem()+"");	
	    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            if(nameNode.path("code").asText().equals("200")){
                Valid.tabelKosong(tabMode);
                response = root.path("response").path("histori");
                if(response.isArray()){
                    i=1;
                    for(JsonNode list:response){
                        tabMode.addRow(new Object[]{
                            i+".",list.path("diagnosa").asText(),list.path("jnsPelayanan").asText().replaceAll("1","Rawat Inap").replaceAll("2","Rawat Jalan"),
                            list.path("kelasRawat").asText(),list.path("namaPeserta").asText(),list.path("noKartu").asText(),list.path("noSep").asText(),
                            list.path("noRujukan").asText(),list.path("poli").asText(),list.path("ppkPelayanan").asText(),list.path("tglPlgSep").asText(),
                            list.path("tglSep").asText()
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
    
    public void isCek(){
        BtnRegist.setEnabled(akses.getbpjs_sep());
    }
 
}

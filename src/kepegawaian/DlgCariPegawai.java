package kepegawaian;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgCariPegawai extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private sekuel Sequel=new sekuel();
    private ExecutorService executor;
    private volatile boolean ceksukses = false;
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariPegawai(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"NIP","Nama","J.K.","Jabatan","Kode Jenjang","Departemen","Bidang","Status","Status Karyawan",
                      "NPWP","Pendidikan","Tmp.Lahir","Tgl.Lahir","Alamat","Kota","Mulai Kerja","Kode Ms Kerja",
                      "Kode Index","BPD","Rekening","Stts Aktif","Wajib Masuk","Mulai Kontrak","No.KTP"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 24; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(170);
            }else if(i==2){
                column.setPreferredWidth(50);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(60);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(110);
            }else if(i==10){
                column.setPreferredWidth(130);
            }else if(i==11){
                column.setPreferredWidth(110);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(65);
            }else if(i==16){
                column.setPreferredWidth(80);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(90);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(60);
            }else if(i==21){
                column.setPreferredWidth(70);
            }else if(i==22){
                column.setPreferredWidth(80);
            }else if(i==23){
                column.setPreferredWidth(120);
            }
        }
        
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil2());
                    }
                }
            });
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

        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pegawai ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(335, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
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
        panelisi3.add(BtnAll);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        runBackground(() ->tampil2());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        runBackground(() ->tampil());
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/pegawai.iyem")<30){
                runBackground(() ->tampil2());
            }else{
                runBackground(() ->tampil());
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                dispose();
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbKamarKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPegawai dialog = new DlgCariPegawai(new javax.swing.JFrame(), true);
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
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    public widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    private void tampil() {  
        Valid.tabelKosong(tabMode);
        try{
            file=new File("./cache/pegawai.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pegawai.nik,pegawai.nama,pegawai.jk,pegawai.jbtn,pegawai.jnj_jabatan,pegawai.departemen,pegawai.bidang,pegawai.stts_wp,pegawai.stts_kerja,"+
                 "pegawai.npwp,pegawai.pendidikan,pegawai.tmp_lahir,pegawai.tgl_lahir,pegawai.alamat,pegawai.kota,pegawai.mulai_kerja,pegawai.ms_kerja,"+
                 "pegawai.indexins,pegawai.bpd,pegawai.rekening,pegawai.stts_aktif,pegawai.wajibmasuk,pegawai.mulai_kontrak,pegawai.no_ktp from pegawai "+
                 "where pegawai.stts_aktif<>'KELUAR' order by pegawai.id ASC ");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                       rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),
                       rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),rs.getString(16),rs.getString(17),rs.getString(18),rs.getString(19),rs.getString(20),
                       rs.getString(21),rs.getString(22),rs.getString(23),rs.getString(24)
                    });
                    iyembuilder.append("{\"NIP\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2).replaceAll("\"","")).append("\",\"JK\":\"").append(rs.getString(3)).append("\",\"Jabatan\":\"").append(rs.getString(4)).append("\",\"KodeJenjang\":\"").append(rs.getString(5)).append("\",\"Departemen\":\"").append(rs.getString(6)).append("\",\"Bidang\":\"").append(rs.getString(7)).append("\",\"Status\":\"").append(rs.getString(8)).append("\",\"StatusKaryawan\":\"").append(rs.getString(9)).append("\",\"NPWP\":\"").append(rs.getString(10)).append("\",\"Pendidikan\":\"").append(rs.getString(11)).append("\",\"TmpLahir\":\"").append(rs.getString(12)).append("\",\"TglLahir\":\"").append(rs.getString(13)).append("\",\"Alamat\":\"").append(rs.getString(14).replaceAll("\"","")).append("\",\"Kota\":\"").append(rs.getString(15).replaceAll("\"","")).append("\",\"MulaiKerja\":\"").append(rs.getString(16)).append("\",\"KodeMsKerja\":\"").append(rs.getString(17)).append("\",\"KodeIndex\":\"").append(rs.getString(18)).append("\",\"BPD\":\"").append(rs.getString(19)).append("\",\"Rekening\":\"").append(rs.getString(20)).append("\",\"SttsAktif\":\"").append(rs.getString(21)).append("\",\"WajibMasuk\":\"").append(rs.getString(22)).append("\",\"MulaiKontrak\":\"").append(rs.getString(23)).append("\",\"NoKTP\":\"").append(rs.getString(24)).append("\"},");
                }
            } catch (Exception e) {
                System.out.println("Note : "+e);
            }finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }  
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pegawai\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        Kd2.setText("");   
        TCari.requestFocus();
    }

    public JTable getTable(){
        return tbKamar;
    }
    
    private void tampil2() {
        try {
            myObj = new FileReader("./cache/pegawai.iyem");
            root = mapper.readTree(myObj);
            Valid.tabelKosong(tabMode);
            response = root.path("pegawai");
            if(response.isArray()){
                if(TCari.getText().trim().equals("")){
                    for(JsonNode list:response){
                        tabMode.addRow(new Object[]{
                            list.path("NIP").asText(),list.path("Nama").asText(),list.path("JK").asText(),list.path("Jabatan").asText(),list.path("KodeJenjang").asText(),list.path("Departemen").asText(),list.path("Bidang").asText(),list.path("Status").asText(),list.path("StatusKaryawan").asText(),list.path("NPWP").asText(),list.path("Pendidikan").asText(),list.path("TmpLahir").asText(),list.path("TglLahir").asText(),list.path("Alamat").asText(),list.path("Kota").asText(),list.path("MulaiKerja").asText(),list.path("KodeMsKerja").asText(),list.path("KodeIndex").asText(),list.path("BPD").asText(),list.path("Rekening").asText(),list.path("SttsAktif").asText(),list.path("WajibMasuk").asText(),list.path("MulaiKontrak").asText(),list.path("NoKTP").asText()
                        });
                    }
                }else{
                    for(JsonNode list:response){
                        if(list.path("NIP").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("Jabatan").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("Bidang").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("Departemen").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                            tabMode.addRow(new Object[]{
                                list.path("NIP").asText(),list.path("Nama").asText(),list.path("JK").asText(),list.path("Jabatan").asText(),list.path("KodeJenjang").asText(),list.path("Departemen").asText(),list.path("Bidang").asText(),list.path("Status").asText(),list.path("StatusKaryawan").asText(),list.path("NPWP").asText(),list.path("Pendidikan").asText(),list.path("TmpLahir").asText(),list.path("TglLahir").asText(),list.path("Alamat").asText(),list.path("Kota").asText(),list.path("MulaiKerja").asText(),list.path("KodeMsKerja").asText(),list.path("KodeIndex").asText(),list.path("BPD").asText(),list.path("Rekening").asText(),list.path("SttsAktif").asText(),list.path("WajibMasuk").asText(),list.path("MulaiKontrak").asText(),list.path("NoKTP").asText()
                            });
                        }
                    }
                }
            }
            myObj.close();
        } catch (Exception ex) {
            if(ex.toString().contains("java.io.FileNotFoundException")){
                tampil();
            }else{
                System.out.println("Notifikasi : "+ex);
            }
        }
    } 
    
    public String tampil3(String kode) {
        try {
            if(Valid.daysOld("./cache/pegawai.iyem")>7){
                tampil();
            }
        } catch (Exception e) {
            if(e.toString().contains("No such file or directory")){
                tampil();
            }
        }
        
        String iyem="";
        try {
            myObj = new FileReader("./cache/pegawai.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pegawai");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("NIP").asText().equalsIgnoreCase(kode)){
                        iyem=list.path("Nama").asText();
                    }
                }
            }
            myObj.close();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        if(iyem.equals("")){
            iyem=Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",kode);
        }
        return iyem;
    }
    
    public String tampilJbatan(String kode) {
        try {
            if(Valid.daysOld("./cache/pegawai.iyem")>7){
                tampil();
            }
        } catch (Exception e) {
            if(e.toString().contains("No such file or directory")){
                tampil();
            }
        }
        
        String iyem="";
        try {
            myObj = new FileReader("./cache/pegawai.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pegawai");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("NIP").asText().toLowerCase().equals(kode)){
                        iyem=list.path("Jabatan").asText();
                    }
                }
            }
            myObj.close();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        if(iyem.equals("")){
            iyem=Sequel.cariIsi("select pegawai.jbtn from pegawai where pegawai.nik=?",kode);
        }
        return iyem;
    }
    
    public String tampilDepartemen(String kode) {
        try {
            if(Valid.daysOld("./cache/pegawai.iyem")>7){
                tampil();
            }
        } catch (Exception e) {
            if(e.toString().contains("No such file or directory")){
                tampil();
            }
        }
        
        String iyem="";
        try {
            myObj = new FileReader("./cache/pegawai.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pegawai");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("NIP").asText().toLowerCase().equals(kode)){
                        iyem=list.path("Departemen").asText();
                    }
                }
            }
            myObj.close();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        if(iyem.equals("")){
            iyem=Sequel.cariIsi("select pegawai.departemen from pegawai where pegawai.nik=?",kode);
        }
        return iyem;
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        ceksukses = true;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        if (executor == null || executor.isShutdown()) {
            executor = Executors.newSingleThreadExecutor();
        }
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
    }
    
    @Override
    public void dispose() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
        super.dispose();
    }
}

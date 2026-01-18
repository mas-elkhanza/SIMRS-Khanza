/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package kepegawaian;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
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
public final class DlgCariDokter2 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private Calendar cal = Calendar.getInstance();
    private int day = cal.get(Calendar.DAY_OF_WEEK);
    private String hari="",poli="";
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private ExecutorService executor;
    private volatile boolean ceksukses = false;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariDokter2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"Kode Dokter","Nama Dokter","J.K.","Tmp.Lahir","Tgl.Lahir","G.D.","Agama","Alamat Tinggal","No.HP/Telp","Stts.Nikah","Spesialis","Alumni","No.Ijin Praktek","Kuota"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 14; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(40);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(50);
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

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
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
        TCari.setPreferredSize(new java.awt.Dimension(312, 23));
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

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

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

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tabMode.getRowCount()!=0){
            if(evt.getClickCount()==2){
                dispose();
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        //nm_dokter.setModal(true);
        DlgDokter dokter=new DlgDokter(null,false);
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());   
        
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariDokter2 dialog = new DlgCariDokter2(new javax.swing.JFrame(), true);
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
    private widget.Button BtnTambah;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    public widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            file=new File("./cache/dokter2.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement(
                "select dokter.kd_dokter,dokter.nm_dokter,dokter.jk,dokter.tmp_lahir,dokter.tgl_lahir,dokter.gol_drh,dokter.agama,dokter.almt_tgl,dokter.no_telp, "+
                "dokter.stts_nikah,spesialis.nm_sps,dokter.alumni,dokter.no_ijn_praktek,jadwal.kuota from dokter inner join spesialis on dokter.kd_sps=spesialis.kd_sps "+
                "inner join jadwal on dokter.kd_dokter=jadwal.kd_dokter inner join poliklinik on poliklinik.kd_poli=jadwal.kd_poli "+
                "where jadwal.hari_kerja=? and poliklinik.nm_poli like ? and dokter.status='1' order by dokter.nm_dokter");
            try{
                if(day==1){
                    hari="AKHAD";
                }else if(day==2){
                    hari="SENIN";
                }else if(day==3){
                    hari="SELASA";
                }else if(day==4){
                    hari="RABU";
                }else if(day==5){
                    hari="KAMIS";
                }else if(day==6){
                    hari="JUMAT";
                }else if(day==7){
                    hari="SABTU";
                }
                ps.setString(1,hari);
                ps.setString(2,poli);
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getString(14)
                    });
                    iyembuilder.append("{\"KodeDokter\":\"").append(rs.getString(1)).append("\",\"NamaDokter\":\"").append(rs.getString(2).replaceAll("\"","")).append("\",\"JK\":\"").append(rs.getString(3)).append("\",\"TmpLahir\":\"").append(rs.getString(4).replaceAll("\"","")).append("\",\"TglLahir\":\"").append(rs.getString(5)).append("\",\"GD\":\"").append(rs.getString(6)).append("\",\"Agama\":\"").append(rs.getString(7)).append("\",\"AlamatTinggal\":\"").append(rs.getString(8).replaceAll("\"","")).append("\",\"NoTelp\":\"").append(rs.getString(9)).append("\",\"SttsNikah\":\"").append(rs.getString(10)).append("\",\"Spesialis\":\"").append(rs.getString(11)).append("\",\"Alumni\":\"").append(rs.getString(12).replaceAll("\"","")).append("\",\"NoIjinPraktek\":\"").append(rs.getString(13)).append("\",\"Kuota\":\"").append(rs.getString(14)).append("\"},");
                }
            }catch(SQLException e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if( rs != null ){
                    rs.close();
                }
                
                if( ps != null ){
                    ps.close();
                }
            }
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"dokter\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void tampil3() {
        runBackground(() ->tampil());
    }

    public void setPoli(String namapoli){
        this.poli=namapoli;
    }
    
    public void emptTeks() { 
        TCari.requestFocus();
    }

    public JTable getTable(){
        return tbKamar;
    }
    
    public void isCek(){        
        BtnTambah.setEnabled(akses.getdokter());
    }
    
    public void SetHari(Date tanggal){
        cal.setTime(tanggal);
        day=cal.get(Calendar.DAY_OF_WEEK);
    }
    
    private void tampil2() {
        try {
            myObj = new FileReader("./cache/dokter2.iyem");
            root = mapper.readTree(myObj);
            Valid.tabelKosong(tabMode);
            response = root.path("dokter");
            if(response.isArray()){
                if(TCari.getText().trim().equals("")){
                    for(JsonNode list:response){
                        tabMode.addRow(new Object[]{
                            list.path("KodeDokter").asText(),list.path("NamaDokter").asText(),list.path("JK").asText(),list.path("TmpLahir").asText(),list.path("TglLahir").asText(),list.path("GD").asText(),list.path("Agama").asText(),list.path("AlamatTinggal").asText(),list.path("NoTelp").asText(),list.path("SttsNikah").asText(),list.path("Spesialis").asText(),list.path("Alumni").asText(),list.path("NoIjinPraktek").asText(),list.path("Kuota").asText()
                        });
                    }
                }else{
                    for(JsonNode list:response){
                        if(list.path("KodeDokter").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("NamaDokter").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("Spesialis").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                            tabMode.addRow(new Object[]{
                                list.path("KodeDokter").asText(),list.path("NamaDokter").asText(),list.path("JK").asText(),list.path("TmpLahir").asText(),list.path("TglLahir").asText(),list.path("GD").asText(),list.path("Agama").asText(),list.path("AlamatTinggal").asText(),list.path("NoTelp").asText(),list.path("SttsNikah").asText(),list.path("Spesialis").asText(),list.path("Alumni").asText(),list.path("NoIjinPraktek").asText(),list.path("Kuota").asText()
                            });
                        }
                    }
                }
            }
            myObj.close();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
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

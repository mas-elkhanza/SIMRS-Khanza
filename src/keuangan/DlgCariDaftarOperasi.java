/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package keuangan;

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
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgCariDaftarOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstindakan,psset_tarif;
    private ResultSet rs,rsset_tarif;
    private int i=0,jml=0;
    private String kelas_operasi="Yes",kelas="",cara_bayar_operasi="Yes",kd_pj="";
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariDaftarOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);
        tabMode=new DefaultTableModel(null,new Object[]{
                "Kode Paket","Nama Operasi","Kategori","Operator 1","Operator 2","Operator 3",
                "Asisten Op 1","Asisten Op 2","Asisten Op 3","Instrumen","dr Anak","Perawat Resus","dr Anastesi",
                "Asisten Anast 1","Asisten Anast 2","Bidan 1","Bidan 2","Bidan 3","Perawat Luar","Alat","Sewa OK/VK",
                "Akomodasi","N.M.S.","Onloop 1","Onloop 2","Onloop 3","Onloop 4","Onloop 5",
                "Sarpras","dr Pj Anak","dr Umum","Total"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==1)||(colIndex==2)||(colIndex==3)||(colIndex==29)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar.setModel(tabMode);

        tbKamar.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 32; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else{
                column.setPreferredWidth(85);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
            });
        } 
        
        try {
            psset_tarif=koneksi.prepareStatement("select * from set_tarif");
            try {
                rsset_tarif=psset_tarif.executeQuery();
                if(rsset_tarif.next()){
                    cara_bayar_operasi=rsset_tarif.getString("cara_bayar_operasi");
                    kelas_operasi=rsset_tarif.getString("kelas_operasi");
                }else{
                    cara_bayar_operasi="Yes";
                    kelas_operasi="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsset_tarif != null){
                    rsset_tarif.close();
                }
                if(psset_tarif != null){
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
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
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Daftar Tindakan Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        tampil2();
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
        tampil();
        tampil2();
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
        DlgJnsPerawatanOperasi bangsal=new DlgJnsPerawatanOperasi(null,false);
        bangsal.emptTeks();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());   
        
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //tampil();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariDaftarOperasi dialog = new DlgCariDaftarOperasi(new javax.swing.JFrame(), true);
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
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    private void tampil() {  
        try{
            file=new File("./cache/paketoperasi.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem="";
            pstindakan=koneksi.prepareStatement("select kode_paket,nm_perawatan,kategori,kd_pj,kelas,operator1, operator2, operator3, "+
                   "asisten_operator1, asisten_operator2,asisten_operator3, instrumen, dokter_anak,perawaat_resusitas,"+
                   "dokter_anestesi, asisten_anestesi, asisten_anestesi2, bidan, bidan2, bidan3, perawat_luar, alat,"+
                   "sewa_ok,akomodasi,bagian_rs,omloop,omloop2,omloop3,omloop4,omloop5,sarpras,dokter_pjanak,dokter_umum,(operator1+operator2+operator3+"+
                   "asisten_operator1+asisten_operator2+asisten_operator3+instrumen+dokter_anak+perawaat_resusitas+"+
                   "alat+dokter_anestesi+asisten_anestesi+asisten_anestesi2+bidan+bidan2+bidan3+perawat_luar+sewa_ok+"+
                   "akomodasi+bagian_rs+omloop+omloop2+omloop3+omloop4+omloop5+sarpras+dokter_pjanak+dokter_umum) as jumlah "+
                   "from paket_operasi where status='1' order by nm_perawatan ");
            try {
                rs=pstindakan.executeQuery();
                while(rs.next()){
                    iyem=iyem+"{\"KodePaket\":\""+rs.getString("kode_paket")+"\",\"NamaOperasi\":\""+rs.getString("nm_perawatan").replaceAll("\"","")+"\",\"Kategori\":\""+rs.getString("kategori")+"\",\"Operator1\":\""+rs.getString("operator1")+
                            "\",\"Operator2\":\""+rs.getString("operator2")+"\",\"Operator3\":\""+rs.getString("operator3")+"\",\"AsistenOp1\":\""+rs.getString("asisten_operator1")+"\",\"AsistenOp2\":\""+rs.getString("asisten_operator2")+
                            "\",\"AsistenOp3\":\""+rs.getString("asisten_operator3")+"\",\"Instrumen\":\""+rs.getString("instrumen")+"\",\"drAnak\":\""+rs.getString("dokter_anak")+"\",\"PerawatResus\":\""+rs.getString("perawaat_resusitas")+
                            "\",\"drAnastesi\":\""+rs.getString("dokter_anestesi")+"\",\"AsistenAnast1\":\""+rs.getString("asisten_anestesi")+"\",\"AsistenAnast2\":\""+rs.getString("asisten_anestesi2")+"\",\"Bidan1\":\""+rs.getString("bidan")+
                            "\",\"Bidan2\":\""+rs.getString("bidan2")+"\",\"Bidan3\":\""+rs.getString("bidan3")+"\",\"PerawatLuar\":\""+rs.getString("perawat_luar")+"\",\"Alat\":\""+rs.getString("alat")+"\",\"SewaOK/VK\":\""+rs.getString("sewa_ok")+
                            "\",\"Akomodasi\":\""+rs.getString("akomodasi")+"\",\"NMS\":\""+rs.getString("bagian_rs")+"\",\"Onloop1\":\""+rs.getString("omloop")+"\",\"Onloop2\":\""+rs.getString("omloop2")+"\",\"Onloop3\":\""+rs.getString("omloop3")+
                            "\",\"Onloop4\":\""+rs.getString("omloop4")+"\",\"Onloop5\":\""+rs.getString("omloop5")+"\",\"Sarpras\":\""+rs.getString("sarpras")+"\",\"drPjAnak\":\""+rs.getString("dokter_pjanak")+"\",\"drUmum\":\""+rs.getString("dokter_umum")+
                            "\",\"Total\":\""+rs.getString("jumlah")+"\",\"KodePJ\":\""+rs.getString("kd_pj")+"\",\"Kelas\":\""+rs.getString("kelas")+"\"},";
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pstindakan!=null){
                    pstindakan.close();
                }
            }   
            fileWriter.write("{\"paketoperasi\":["+iyem.substring(0,iyem.length()-1)+"]}");
            fileWriter.flush();
            fileWriter.close();
            iyem=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampil2() {  
        try{
            myObj = new FileReader("./cache/paketoperasi.iyem");
            root = mapper.readTree(myObj);
            Valid.tabelKosong(tabMode);
            response = root.path("paketoperasi");
            if(cara_bayar_operasi.equals("Yes")&&kelas_operasi.equals("No")){
                if(response.isArray()){
                    for(JsonNode list:response){
                        if((list.path("KodePJ").asText().equals(kd_pj.trim())||list.path("KodePJ").asText().equals("-"))&&(list.path("KodePaket").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("NamaOperasi").asText().toLowerCase().contains(TCari.getText().toLowerCase()))){
                            tabMode.addRow(new Object[]{
                                list.path("KodePaket").asText(),list.path("NamaOperasi").asText(),list.path("Kategori").asText(),list.path("Operator1").asDouble(),list.path("Operator2").asDouble(),list.path("Operator3").asDouble(),list.path("AsistenOp1").asDouble(),list.path("AsistenOp2").asDouble(),list.path("AsistenOp3").asDouble(),list.path("Instrumen").asDouble(),list.path("drAnak").asDouble(),list.path("PerawatResus").asDouble(),list.path("drAnastesi").asDouble(),list.path("AsistenAnast1").asDouble(),list.path("AsistenAnast2").asDouble(),list.path("Bidan1").asDouble(),list.path("Bidan2").asDouble(),list.path("Bidan3").asDouble(),list.path("PerawatLuar").asDouble(),list.path("Alat").asDouble(),list.path("SewaOK/VK").asDouble(),list.path("Akomodasi").asDouble(),list.path("NMS").asDouble(),list.path("Onloop1").asDouble(),list.path("Onloop2").asDouble(),list.path("Onloop3").asDouble(),list.path("Onloop4").asDouble(),list.path("Onloop5").asDouble(),list.path("Sarpras").asDouble(),list.path("drPjAnak").asDouble(),list.path("drUmum").asDouble(),list.path("Total").asDouble()
                            });
                        }
                    }
                }
            }else if(cara_bayar_operasi.equals("No")&&kelas_operasi.equals("No")){
                if(response.isArray()){
                    for(JsonNode list:response){
                        if(list.path("KodePaket").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("NamaOperasi").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                            tabMode.addRow(new Object[]{
                                list.path("KodePaket").asText(),list.path("NamaOperasi").asText(),list.path("Kategori").asText(),list.path("Operator1").asDouble(),list.path("Operator2").asDouble(),list.path("Operator3").asDouble(),list.path("AsistenOp1").asDouble(),list.path("AsistenOp2").asDouble(),list.path("AsistenOp3").asDouble(),list.path("Instrumen").asDouble(),list.path("drAnak").asDouble(),list.path("PerawatResus").asDouble(),list.path("drAnastesi").asDouble(),list.path("AsistenAnast1").asDouble(),list.path("AsistenAnast2").asDouble(),list.path("Bidan1").asDouble(),list.path("Bidan2").asDouble(),list.path("Bidan3").asDouble(),list.path("PerawatLuar").asDouble(),list.path("Alat").asDouble(),list.path("SewaOK/VK").asDouble(),list.path("Akomodasi").asDouble(),list.path("NMS").asDouble(),list.path("Onloop1").asDouble(),list.path("Onloop2").asDouble(),list.path("Onloop3").asDouble(),list.path("Onloop4").asDouble(),list.path("Onloop5").asDouble(),list.path("Sarpras").asDouble(),list.path("drPjAnak").asDouble(),list.path("drUmum").asDouble(),list.path("Total").asDouble()
                            });
                        }
                    }
                }
            }else if(cara_bayar_operasi.equals("Yes")&&kelas_operasi.equals("Yes")){
                if(response.isArray()){
                    for(JsonNode list:response){
                        if((list.path("Kelas").asText().equals(kelas.trim())||list.path("Kelas").asText().equals("-"))&&(list.path("KodePJ").asText().equals(kd_pj.trim())||list.path("KodePJ").asText().equals("-"))&&(list.path("KodePaket").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("NamaOperasi").asText().toLowerCase().contains(TCari.getText().toLowerCase()))){
                            tabMode.addRow(new Object[]{
                                list.path("KodePaket").asText(),list.path("NamaOperasi").asText(),list.path("Kategori").asText(),list.path("Operator1").asDouble(),list.path("Operator2").asDouble(),list.path("Operator3").asDouble(),list.path("AsistenOp1").asDouble(),list.path("AsistenOp2").asDouble(),list.path("AsistenOp3").asDouble(),list.path("Instrumen").asDouble(),list.path("drAnak").asDouble(),list.path("PerawatResus").asDouble(),list.path("drAnastesi").asDouble(),list.path("AsistenAnast1").asDouble(),list.path("AsistenAnast2").asDouble(),list.path("Bidan1").asDouble(),list.path("Bidan2").asDouble(),list.path("Bidan3").asDouble(),list.path("PerawatLuar").asDouble(),list.path("Alat").asDouble(),list.path("SewaOK/VK").asDouble(),list.path("Akomodasi").asDouble(),list.path("NMS").asDouble(),list.path("Onloop1").asDouble(),list.path("Onloop2").asDouble(),list.path("Onloop3").asDouble(),list.path("Onloop4").asDouble(),list.path("Onloop5").asDouble(),list.path("Sarpras").asDouble(),list.path("drPjAnak").asDouble(),list.path("drUmum").asDouble(),list.path("Total").asDouble()
                            });
                        }
                    }
                }
            }else if(cara_bayar_operasi.equals("No")&&kelas_operasi.equals("Yes")){
                if(response.isArray()){
                    for(JsonNode list:response){
                        if((list.path("Kelas").asText().equals(kelas.trim())||list.path("Kelas").asText().equals("-"))&&(list.path("KodePaket").asText().toLowerCase().contains(TCari.getText().toLowerCase())||list.path("NamaOperasi").asText().toLowerCase().contains(TCari.getText().toLowerCase()))){
                            tabMode.addRow(new Object[]{
                                list.path("KodePaket").asText(),list.path("NamaOperasi").asText(),list.path("Kategori").asText(),list.path("Operator1").asDouble(),list.path("Operator2").asDouble(),list.path("Operator3").asDouble(),list.path("AsistenOp1").asDouble(),list.path("AsistenOp2").asDouble(),list.path("AsistenOp3").asDouble(),list.path("Instrumen").asDouble(),list.path("drAnak").asDouble(),list.path("PerawatResus").asDouble(),list.path("drAnastesi").asDouble(),list.path("AsistenAnast1").asDouble(),list.path("AsistenAnast2").asDouble(),list.path("Bidan1").asDouble(),list.path("Bidan2").asDouble(),list.path("Bidan3").asDouble(),list.path("PerawatLuar").asDouble(),list.path("Alat").asDouble(),list.path("SewaOK/VK").asDouble(),list.path("Akomodasi").asDouble(),list.path("NMS").asDouble(),list.path("Onloop1").asDouble(),list.path("Onloop2").asDouble(),list.path("Onloop3").asDouble(),list.path("Onloop4").asDouble(),list.path("Onloop5").asDouble(),list.path("Sarpras").asDouble(),list.path("drPjAnak").asDouble(),list.path("drUmum").asDouble(),list.path("Total").asDouble()
                            });
                        }
                    }
                }
            }   
            myObj.close();   
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TCari.requestFocus();
    }

    public JTable getTable(){
        return tbKamar;
    }
    
    public void setBayar(String penjab,String kelasoperasi){
        this.kd_pj=penjab;
        this.kelas=kelasoperasi;
    }
    public void isCek(){        
       BtnTambah.setEnabled(akses.gettarif_operasi());
    }
}

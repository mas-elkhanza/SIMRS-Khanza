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

import simrskhanza.*;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstindakan,pstindakan2,pstindakan3,pstindakan4,psset_tarif;
    private ResultSet rs,rsset_tarif;
    private int i=0,jml=0;
    private String kelas_operasi="Yes",kelas="",cara_bayar_operasi="Yes",kd_pj="";
    private DlgJnsPerawatanOperasi bangsal=new DlgJnsPerawatanOperasi(null,false);
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
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
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
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Daftar Tindakan Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label9.setText("Keyword :");
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
        tampil();
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
        //bangsal.setModal(true);
        bangsal.emptTeks();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
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

    public void tampil() {  
        Valid.tabelKosong(tabMode);
        try{
            pstindakan=koneksi.prepareStatement("select kode_paket, nm_perawatan,kategori, operator1, operator2, operator3, "+
                   "asisten_operator1, asisten_operator2,asisten_operator3, instrumen, dokter_anak,perawaat_resusitas,"+
                   "dokter_anestesi, asisten_anestesi, asisten_anestesi2, bidan, bidan2, bidan3, perawat_luar, alat,"+
                   "sewa_ok,akomodasi,bagian_rs,omloop,omloop2,omloop3,omloop4,omloop5,sarpras,dokter_pjanak,dokter_umum,(operator1+operator2+operator3+"+
                   "asisten_operator1+asisten_operator2+asisten_operator3+instrumen+dokter_anak+perawaat_resusitas+"+
                   "alat+dokter_anestesi+asisten_anestesi+asisten_anestesi2+bidan+bidan2+bidan3+perawat_luar+sewa_ok+"+
                   "akomodasi+bagian_rs+omloop+omloop2+omloop3+omloop4+omloop5+sarpras+dokter_pjanak+dokter_umum) as jumlah "+
                   "from paket_operasi "+
                   "where status='1' and (kd_pj=? or kd_pj='-') and kode_paket like ? or "+
                   "status='1' and (kd_pj=? or kd_pj='-') and nm_perawatan like ? order by nm_perawatan ");
            pstindakan2=koneksi.prepareStatement("select kode_paket, nm_perawatan,kategori, operator1, operator2, operator3, "+
                   "asisten_operator1, asisten_operator2,asisten_operator3, instrumen, dokter_anak,perawaat_resusitas,"+
                   "dokter_anestesi, asisten_anestesi, asisten_anestesi2, bidan, bidan2, bidan3, perawat_luar, alat,"+
                   "sewa_ok,akomodasi,bagian_rs,omloop,omloop2,omloop3,omloop4,omloop5,sarpras,dokter_pjanak,dokter_umum,(operator1+operator2+operator3+"+
                   "asisten_operator1+asisten_operator2+asisten_operator3+instrumen+dokter_anak+perawaat_resusitas+"+
                   "alat+dokter_anestesi+asisten_anestesi+asisten_anestesi2+bidan+bidan2+bidan3+perawat_luar+sewa_ok+"+
                   "akomodasi+bagian_rs+omloop+omloop2+omloop3+omloop4+omloop5+sarpras+dokter_pjanak+dokter_umum) as jumlah "+
                   "from paket_operasi "+
                   "where status='1' and kode_paket like ? or "+
                   "status='1' and nm_perawatan like ? order by nm_perawatan ");
            pstindakan3=koneksi.prepareStatement("select kode_paket, nm_perawatan,kategori, operator1, operator2, operator3, "+
                   "asisten_operator1, asisten_operator2,asisten_operator3, instrumen, dokter_anak,perawaat_resusitas,"+
                   "dokter_anestesi, asisten_anestesi, asisten_anestesi2, bidan, bidan2, bidan3, perawat_luar, alat,"+
                   "sewa_ok,akomodasi,bagian_rs,omloop,omloop2,omloop3,omloop4,omloop5,sarpras,dokter_pjanak,dokter_umum,(operator1+operator2+operator3+"+
                   "asisten_operator1+asisten_operator2+asisten_operator3+instrumen+dokter_anak+perawaat_resusitas+"+
                   "alat+dokter_anestesi+asisten_anestesi+asisten_anestesi2+bidan+bidan2+bidan3+perawat_luar+sewa_ok+"+
                   "akomodasi+bagian_rs+omloop+omloop2+omloop3+omloop4+omloop5+sarpras+dokter_pjanak+dokter_umum) as jumlah "+
                   "from paket_operasi "+
                   "where status='1' and (kd_pj=? or kd_pj='-') and (kelas=? or kelas='-') and kode_paket like ? or "+
                   "status='1' and (kd_pj=? or kd_pj='-') and (kelas=? or kelas='-') and nm_perawatan like ? order by nm_perawatan ");
            pstindakan4=koneksi.prepareStatement("select kode_paket, nm_perawatan,kategori, operator1, operator2, operator3, "+
                   "asisten_operator1, asisten_operator2,asisten_operator3, instrumen, dokter_anak,perawaat_resusitas,"+
                   "dokter_anestesi, asisten_anestesi, asisten_anestesi2, bidan, bidan2, bidan3, perawat_luar, alat,"+
                   "sewa_ok,akomodasi,bagian_rs,omloop,omloop2,omloop3,omloop4,omloop5,sarpras,dokter_pjanak,dokter_umum,(operator1+operator2+operator3+"+
                   "asisten_operator1+asisten_operator2+asisten_operator3+instrumen+dokter_anak+perawaat_resusitas+"+
                   "alat+dokter_anestesi+asisten_anestesi+asisten_anestesi2+bidan+bidan2+bidan3+perawat_luar+sewa_ok+"+
                   "akomodasi+bagian_rs+omloop+omloop2+omloop3+omloop4+omloop5+sarpras+dokter_pjanak+dokter_umum) as jumlah "+
                   "from paket_operasi "+
                   "where status='1' and (kelas=? or kelas='-') and kode_paket like ? or "+
                   "status='1' and (kelas=? or kelas='-') and nm_perawatan like ? order by nm_perawatan ");
            
            try {
                if(cara_bayar_operasi.equals("Yes")&&kelas_operasi.equals("No")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,"%"+TCari.getText()+"%");
                    pstindakan.setString(3,kd_pj.trim());
                    pstindakan.setString(4,"%"+TCari.getText()+"%");
                    rs=pstindakan.executeQuery();
                }else if(cara_bayar_operasi.equals("No")&&kelas_operasi.equals("No")){
                    pstindakan2.setString(1,"%"+TCari.getText()+"%");
                    pstindakan2.setString(2,"%"+TCari.getText()+"%");
                    rs=pstindakan2.executeQuery();
                }else if(cara_bayar_operasi.equals("Yes")&&kelas_operasi.equals("Yes")){
                    pstindakan3.setString(1,kd_pj.trim());
                    pstindakan3.setString(2,kelas.trim());
                    pstindakan3.setString(3,"%"+TCari.getText()+"%");
                    pstindakan3.setString(4,kd_pj.trim());
                    pstindakan3.setString(5,kelas.trim());
                    pstindakan3.setString(6,"%"+TCari.getText()+"%");
                    rs=pstindakan3.executeQuery();
                }else if(cara_bayar_operasi.equals("No")&&kelas_operasi.equals("Yes")){
                    pstindakan4.setString(1,kelas.trim());
                    pstindakan4.setString(2,"%"+TCari.getText()+"%");
                    pstindakan4.setString(3,kelas.trim());
                    pstindakan4.setString(4,"%"+TCari.getText()+"%");
                    rs=pstindakan4.executeQuery();
                }
                
                while(rs.next()){
                    tabMode.addRow(new Object[]{rs.getString("kode_paket"),
                                   rs.getString("nm_perawatan"),
                                   rs.getString("kategori"), 
                                   rs.getDouble("operator1"), 
                                   rs.getDouble("operator2"), 
                                   rs.getDouble("operator3"), 
                                   rs.getDouble("asisten_operator1"), 
                                   rs.getDouble("asisten_operator2"), 
                                   rs.getDouble("asisten_operator3"), 
                                   rs.getDouble("instrumen"), 
                                   rs.getDouble("dokter_anak"), 
                                   rs.getDouble("perawaat_resusitas"), 
                                   rs.getDouble("dokter_anestesi"), 
                                   rs.getDouble("asisten_anestesi"), 
                                   rs.getDouble("asisten_anestesi2"), 
                                   rs.getDouble("bidan"), 
                                   rs.getDouble("bidan2"), 
                                   rs.getDouble("bidan3"), 
                                   rs.getDouble("perawat_luar"), 
                                   rs.getDouble("alat"), 
                                   rs.getDouble("sewa_ok"), 
                                   rs.getDouble("akomodasi"), 
                                   rs.getDouble("bagian_rs"), 
                                   rs.getDouble("omloop"), 
                                   rs.getDouble("omloop2"), 
                                   rs.getDouble("omloop3"), 
                                   rs.getDouble("omloop4"), 
                                   rs.getDouble("omloop5"), 
                                   rs.getDouble("sarpras"), 
                                   rs.getDouble("dokter_pjanak"), 
                                   rs.getDouble("dokter_umum"), 
                                   rs.getDouble("jumlah")});
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
                if(pstindakan2!=null){
                    pstindakan2.close();
                }
                if(pstindakan3!=null){
                    pstindakan3.close();
                }
                if(pstindakan4!=null){
                    pstindakan4.close();
                }
            }                  
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
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

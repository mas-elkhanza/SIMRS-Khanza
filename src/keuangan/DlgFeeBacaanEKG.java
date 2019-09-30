package keuangan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;

public class DlgFeeBacaanEKG extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private int i=0,jmlbacaan=0,ttljmlbacaan=0;
    private double feebacaan=0,jasa=0,ttljasa=0;
    private PreparedStatement pskamar,psbacaanranap,psbacaanralan;
    private ResultSet rskamar,rsbacaanranap,rsbacaanralan;
    private String sjmlbacaan="",sfeebacaan="",sjasa="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgFeeBacaanEKG(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Tgl.Masuk","Tgl.Keluar","Nama Pasien","Ruang","Jenis Bayar",
                      "Jml.Bacaan","Fee(Rp)","Total Fee(Rp)"};
        
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Integer.class,java.lang.Double.class,java.lang.Double.class,
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(125);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else {
                column.setPreferredWidth(88);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kddokter.setDocument(new batasInput((byte)10).getKata(kddokter));
                
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    nmdokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    prosesCari();
                }   
                kddokter.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {dokter.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        try {            
            pskamar=koneksi.prepareStatement(
                    "select kamar_inap.no_rawat,pasien.nm_pasien,penjab.png_jawab,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.tgl_keluar "+
                    "from kamar_inap inner join kamar inner join bangsal inner join reg_periksa inner join pasien inner join penjab on kamar_inap.no_rawat=reg_periksa.no_rawat and "+
                    "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "where kamar_inap.tgl_keluar between ? and ? and kamar_inap.tgl_keluar<>'0000-00-00' group by kamar_inap.no_rawat");
            psbacaanranap=koneksi.prepareStatement(
                    "select count(rawat_inap_dr.kd_jenis_prw) as jml,"+
                    "rawat_inap_dr.tarif_tindakandr as tarif,"+
                    "sum(rawat_inap_dr.tarif_tindakandr)as bayardokter "+
                    "from rawat_inap_dr inner join jns_perawatan_inap "+
                    "on jns_perawatan_inap.kd_jenis_prw=rawat_inap_dr.kd_jenis_prw where "+
                    "rawat_inap_dr.tarif_tindakandr>0 and rawat_inap_dr.kd_dokter=? "+
                    "and rawat_inap_dr.no_rawat=? and jns_perawatan_inap.nm_perawatan like '%bacaan%' "+
                    "and jns_perawatan_inap.nm_perawatan like '%ekg%' ");
            psbacaanralan=koneksi.prepareStatement(
                    "select count(rawat_inap_dr.kd_jenis_prw) as jml,"+
                    "rawat_inap_dr.tarif_tindakandr as tarif,"+
                    "sum(rawat_inap_dr.tarif_tindakandr)as bayardokter "+
                    "from rawat_inap_dr inner join jns_perawatan_inap "+
                    "on jns_perawatan_inap.kd_jenis_prw=rawat_inap_dr.kd_jenis_prw where "+
                    "rawat_inap_dr.tarif_tindakandr>0 and rawat_inap_dr.kd_dokter=? "+
                    "and rawat_inap_dr.no_rawat=? and jns_perawatan_inap.nm_perawatan like '%bacaan%' "+
                    "and jns_perawatan_inap.nm_perawatan like '%ekg%' ");
            psbacaanralan=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,pasien.nm_pasien,penjab.png_jawab,"+
                    "count(rawat_jl_dr.kd_jenis_prw) as jml,rawat_jl_dr.tarif_tindakandr as tarif,"+
                    "sum(rawat_jl_dr.tarif_tindakandr) as bayardokter "+
                    "from rawat_jl_dr inner join reg_periksa inner join pasien "+
                    "inner join penjab inner join jns_perawatan  "+
                    "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                    "and jns_perawatan.kd_jenis_prw=rawat_jl_dr.kd_jenis_prw "+
                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and reg_periksa.kd_pj=penjab.kd_pj "+
                    "where rawat_jl_dr.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? "+
                    "and rawat_jl_dr.tarif_tindakandr>0 and jns_perawatan.nm_perawatan like '%bacaan%' "+
                    "and jns_perawatan.nm_perawatan like '%ekg%' group by rawat_jl_dr.no_rawat");
        } catch (Exception e) {
            System.out.println(e);
        }
     
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Bacaan EKG ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Pasien Keluar :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi4.add(Tgl2);

        label17.setText("Dokter :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);

        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(70, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        panelisi4.add(kddokter);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmdokter);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        panelisi4.add(BtnSeek2);

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
        panelisi4.add(BtnCari);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPrint);

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
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary");
            for(i=0;i<tabMode.getRowCount();i++){  
                try {
                    sjmlbacaan=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()));
                } catch (Exception e) {
                    sjmlbacaan="";
                }
                
                try {
                    sfeebacaan=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()));
                } catch (Exception e) {
                    sfeebacaan="";
                }
                
                try {
                    sjasa=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()));
                } catch (Exception e) {
                    sjasa="";
                }
                
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString().replaceAll("'","`") +"','"+
                                tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"','"+
                                sjmlbacaan+"','"+
                                sfeebacaan+"','"+
                                sjasa+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","JM Dokter"); 
            }
            
            Map<String, Object> param = new HashMap<>();   
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs()); 
                param.put("dokter",nmdokter.getText());
                param.put("periode",Tgl1.getSelectedItem()+" s/d "+Tgl2.getSelectedItem());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptFeeBacaanEkg.jasper","report","[ Rekap Fee Bacaan EKG ]",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,kddokter,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            BtnPrint.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kddokterKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(kddokter.getText().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih dokter terlebih dahulu..!!");
        }else{
            prosesCari();
        }
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kddokter, BtnPrint);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kddokter);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgFeeBacaanEKG dialog = new DlgFeeBacaanEKG(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSeek2;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari(){
        Valid.tabelKosong(tabMode);      
        try{  
            pskamar.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            pskamar.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            rskamar=pskamar.executeQuery();
            i=1;ttljmlbacaan=0;ttljasa=0;
            while(rskamar.next()){
                jmlbacaan=0;feebacaan=0;jasa=0;
                
                psbacaanranap.setString(1,kddokter.getText());
                psbacaanranap.setString(2,rskamar.getString("no_rawat"));
                rsbacaanranap=psbacaanranap.executeQuery();
                if(rsbacaanranap.next()){
                    jmlbacaan=rsbacaanranap.getInt("jml");
                    feebacaan=rsbacaanranap.getDouble("tarif");
                    jasa=rsbacaanranap.getDouble("bayardokter");
                }
                
                if(jasa>0){
                    tabMode.addRow(new Object[]{
                        i,rskamar.getString("tgl_masuk"),rskamar.getString("tgl_keluar"),
                        rskamar.getString("nm_pasien"),rskamar.getString("kd_kamar")+
                        " "+rskamar.getString("nm_bangsal"),rskamar.getString("png_jawab"),
                        jmlbacaan,feebacaan,jasa
                    });
                }
                i++;
                ttljmlbacaan=ttljmlbacaan+jmlbacaan;
                ttljasa=ttljasa+jasa;
            }
            
            psbacaanralan.setString(1,kddokter.getText());
            psbacaanralan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            psbacaanralan.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            rsbacaanralan=psbacaanralan.executeQuery();
            while(rsbacaanralan.next()){
                jmlbacaan=rsbacaanralan.getInt("jml");
                feebacaan=rsbacaanralan.getDouble("tarif");
                jasa=rsbacaanralan.getDouble("bayardokter");
                ttljmlbacaan=ttljmlbacaan+jmlbacaan;
                ttljasa=ttljasa+jasa;
                
                tabMode.addRow(new Object[]{
                    i,rsbacaanralan.getString("tgl_registrasi"),"",
                    rsbacaanralan.getString("nm_pasien"),"Poli",
                    rsbacaanralan.getString("png_jawab"),
                    jmlbacaan,feebacaan,jasa
                });
            }
            
            if(ttljasa>0){
                tabMode.addRow(new Object[]{
                    "","","","Jumlah :","","",ttljmlbacaan,0,ttljasa
                });
            }
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }
        
    }
    
    public void isCek(){
       // BtnPrint.setEnabled(var.getfee_bacaan_ekg());
    }
    
}

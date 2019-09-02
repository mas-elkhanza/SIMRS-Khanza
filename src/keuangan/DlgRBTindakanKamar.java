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
import java.awt.event.KeyListener;
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
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgPenanggungJawab;

public class DlgRBTindakanKamar extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement psbangsal,pspasien,psobat,psobatlangsung,psperiksalab,psdetailperiksalab,
            psoperasi,psranapdr,psranapdrpr,psranappr,pstambahan,pspotongan,psregistrasi,psradiologi,pskamar;
    private ResultSet rsbangsal,rspasien,rs; 
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private int i=0,a=0;
    private double obat=0,obatlangsung=0,laborat=0,operasi=0,radiologi=0,jm=0,jm2=0,jm3=0,ttlbiaya=0,detailobat=0,detailobatlangsung=0,ttlobat=0,ttlobatlangsung=0,ttllaborat=0,ttljm=0,
            detaillaborat=0,ttldetaillaborat=0,tambahan,potongan,detailtambahan,detailpotongan,registrasi=0,detailregistrasi,ttlpotongan=0,ttltambahan=0,
            ttlregistrasi=0,detailradiologi=0,ttlradiologi=0,kamar=0,ttlkamar=0;
    private String pilihancarabayar="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRBTindakanKamar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Bangsal","Tgl.Masuk","Tgl.Keluar","Cara Bayar","Obt+Emb+Tsl","Laborat","Paket Tindakan","Tambahan","Potongan","Registrasi","Radiologi","Kamar","Total"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 14; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(80);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdbangsal.setDocument(new batasInput((byte)8).getKata(kdbangsal));
                
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){
                    kdbangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    nmbangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                    prosesCari();
                }    
                kdbangsal.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {bangsal.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    pilihancarabayar=(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                }     
                prosesCari();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.onCari();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppTampilkanSeleksi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdbangsal = new widget.TextBox();
        nmbangsal = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppTampilkanSeleksi.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanSeleksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanSeleksi.setForeground(new java.awt.Color(50,50,50));
        ppTampilkanSeleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanSeleksi.setText("Tampilkan Pasien Per Jenis Bayar");
        ppTampilkanSeleksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanSeleksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanSeleksi.setName("ppTampilkanSeleksi"); // NOI18N
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(240, 25));
        ppTampilkanSeleksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanSeleksiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanSeleksi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Harian Per Bangsal/Kamar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
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
        tbDokter.setToolTipText("");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Keluar :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(65, 23));
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

        label17.setText("Bangsal :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);

        kdbangsal.setName("kdbangsal"); // NOI18N
        kdbangsal.setPreferredSize(new java.awt.Dimension(70, 23));
        kdbangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbangsalKeyPressed(evt);
            }
        });
        panelisi4.add(kdbangsal);

        nmbangsal.setEditable(false);
        nmbangsal.setName("nmbangsal"); // NOI18N
        nmbangsal.setPreferredSize(new java.awt.Dimension(223, 23));
        panelisi4.add(nmbangsal);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelisi1.add(BtnAll);

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

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(450, 30));
        panelisi1.add(label9);

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
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,10).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,11).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,12).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,13).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Pemasukan Perbangsal Dokter"); 
            }
            
            Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRBTindakanKamar.jasper","report","[ Rekap Harian Per Bangsal/Kamar ]",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
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

    private void kdbangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbangsalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_bangsal from bangsalklinik where kd_bangsal=?", nmbangsal,kdbangsal.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_bangsal from bangsalklinik where kd_bangsal=?", nmbangsal,kdbangsal.getText()); 
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_bangsal from bangsalklinik where kd_bangsal=?", nmbangsal,kdbangsal.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdbangsalKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kdbangsal.setText("");
        nmbangsal.setText("");
        pilihancarabayar="";
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        prosesCari();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kdbangsal, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void ppTampilkanSeleksiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanSeleksiBtnPrintActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
    }//GEN-LAST:event_ppTampilkanSeleksiBtnPrintActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdbangsal);
    }//GEN-LAST:event_Tgl2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRBTindakanKamar dialog = new DlgRBTindakanKamar(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbangsal;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmbangsal;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppTampilkanSeleksi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {            
        try{   
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
           Valid.tabelKosong(tabMode); 
           psbangsal=koneksi.prepareStatement("select kd_bangsal,nm_bangsal from bangsal where  kd_bangsal like ?");
           try {
                psbangsal.setString(1,"%"+kdbangsal.getText()+"%"); 
                rsbangsal=psbangsal.executeQuery();
                i=1;
                ttlbiaya=0;
                ttljm=0;
                ttllaborat=0;
                ttldetaillaborat=0;
                ttlobat=0;
                ttlobatlangsung=0;
                ttlpotongan=0;
                ttlregistrasi=0;
                ttltambahan=0;
                ttlradiologi=0;
                ttlkamar=0;
                while(rsbangsal.next()){
                    tabMode.addRow(new Object[]{i+". ",rsbangsal.getString(2),"","","","","","","","","","","",""});
                    a=1;
                    pspasien=koneksi.prepareStatement(
                       "select kamar_inap.no_rawat,pasien.nm_pasien,penjab.png_jawab,kamar_inap.kd_kamar,kamar_inap.tgl_masuk,kamar_inap.tgl_keluar "+
                       "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join penjab " +
                       "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                       "and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar "+
                       "where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar.kd_bangsal=? and "+
                       "kamar_inap.tgl_keluar between ? and ? and reg_periksa.kd_pj like ? "+
                       "group by kamar_inap.no_rawat order by kamar_inap.tgl_keluar");
                    try {
                        pspasien.setString(1,rsbangsal.getString(1));
                        pspasien.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        pspasien.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        pspasien.setString(4,"%"+pilihancarabayar+"%");
                        rspasien=pspasien.executeQuery();
                        while(rspasien.next()){
                            obat=0;
                            psobat=koneksi.prepareStatement("select sum(total) from detail_pemberian_obat where no_rawat=?");
                            try {
                                psobat.setString(1,rspasien.getString("no_rawat"));
                                rs=psobat.executeQuery();
                                if(rs.next()){
                                   obat=rs.getDouble(1);
                                   ttlobat=ttlobat+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Obat : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psobat!=null){
                                    psobat.close();
                                }
                            }
                            
                            obatlangsung=0;
                            psobatlangsung=koneksi.prepareStatement("select sum(besar_tagihan) from tagihan_obat_langsung where no_rawat=?");
                            try {
                                psobatlangsung.setString(1,rspasien.getString("no_rawat"));
                                rs=psobatlangsung.executeQuery();
                                if(rs.next()){
                                   obatlangsung=rs.getDouble(1);
                                   ttlobatlangsung=ttlobatlangsung+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Obat : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psobatlangsung!=null){
                                    psobatlangsung.close();
                                }
                            }
                            
                            laborat=0;
                            psperiksalab=koneksi.prepareStatement("select sum(biaya) from periksa_lab where no_rawat=?");
                            try {
                                psperiksalab.setString(1,rspasien.getString("no_rawat"));
                                rs=psperiksalab.executeQuery();
                                if(rs.next()){
                                   laborat=rs.getDouble(1);
                                   ttllaborat=ttllaborat+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Obat : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psperiksalab!=null){
                                    psperiksalab.close();
                                }
                            }
                            
                            detaillaborat=0;
                            psdetailperiksalab=koneksi.prepareStatement("select sum(biaya_item) from detail_periksa_lab where no_rawat=?");
                            try {
                                psdetailperiksalab.setString(1,rspasien.getString("no_rawat"));
                                rs=psdetailperiksalab.executeQuery();
                                if(rs.next()){
                                   detaillaborat=rs.getDouble(1);
                                   ttldetaillaborat=ttldetaillaborat+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Obat : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psdetailperiksalab!=null){
                                    psdetailperiksalab.close();
                                }
                            }
                            
                            jm=0;
                            psranapdr=koneksi.prepareStatement("select sum(biaya_rawat) from rawat_inap_dr where no_rawat=?");
                            try {
                                psranapdr.setString(1,rspasien.getString("no_rawat"));
                                rs=psranapdr.executeQuery();
                                if(rs.next()){
                                   jm=rs.getDouble(1);
                                   ttljm=ttljm+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Jm : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psranapdr!=null){
                                    psranapdr.close();
                                }
                            }
                            
                            jm2=0;
                            psranapdrpr=koneksi.prepareStatement("select sum(biaya_rawat) from rawat_inap_drpr where no_rawat=?");
                            try {
                                psranapdrpr.setString(1,rspasien.getString("no_rawat"));
                                rs=psranapdrpr.executeQuery();
                                if(rs.next()){
                                   jm2=rs.getDouble(1);
                                   ttljm=ttljm+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Jm : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psranapdrpr!=null){
                                    psranapdrpr.close();
                                }
                            }
                            
                            jm3=0;
                            psranappr=koneksi.prepareStatement("select sum(biaya_rawat) from rawat_inap_pr where no_rawat=?");
                            try {
                                psranappr.setString(1,rspasien.getString("no_rawat"));
                                rs=psranappr.executeQuery();
                                if(rs.next()){
                                   jm3=rs.getDouble(1);
                                   ttljm=ttljm+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Jm : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psranappr!=null){
                                    psranappr.close();
                                }
                            }
                            
                            operasi=0;
                            psoperasi=koneksi.prepareStatement("select sum(biayaoperator1+biayaoperator2+biayaoperator3+"+
                                "biayaasisten_operator1+biayaasisten_operator2+biayainstrumen+"+
                                "biayadokter_anak+biayaperawaat_resusitas+biayadokter_anestesi+"+
                                "biayaasisten_anestesi+biayabidan+biayabidan2+biayabidan3+"+
                                "biayaperawat_luar+biayaalat+biaya_dokter_pjanak+biaya_dokter_umum+"+
                                "biayasewaok+akomodasi+bagian_rs+biaya_omloop+biaya_omloop2+biaya_omloop3+biayasarpras) from operasi where no_rawat=?");
                            try {
                                psoperasi.setString(1,rspasien.getString("no_rawat"));
                                rs=psoperasi.executeQuery();
                                if(rs.next()){
                                   operasi=rs.getDouble(1);
                                   ttljm=ttljm+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Jm : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psoperasi!=null){
                                    psoperasi.close();
                                }
                            }
                            
                            tambahan=0;
                            pstambahan=koneksi.prepareStatement("select sum(besar_biaya) from tambahan_biaya where no_rawat=?");
                            try {
                                pstambahan.setString(1,rspasien.getString("no_rawat"));
                                rs=pstambahan.executeQuery();
                                if(rs.next()){
                                   tambahan=rs.getDouble(1);
                                   ttltambahan=ttltambahan+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Jm : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(pstambahan!=null){
                                    pstambahan.close();
                                }
                            }
                            
                            potongan=0;
                            pspotongan=koneksi.prepareStatement("select sum(besar_pengurangan) from pengurangan_biaya where no_rawat=?");
                            try {
                                pspotongan.setString(1,rspasien.getString("no_rawat"));
                                rs=pspotongan.executeQuery();
                                if(rs.next()){
                                   potongan=rs.getDouble(1);
                                   ttlpotongan=ttlpotongan+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Jm : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(pspotongan!=null){
                                    pspotongan.close();
                                }
                            }
                            
                            registrasi=0;
                            psregistrasi=koneksi.prepareStatement("select sum(biaya_reg) from reg_periksa where no_rawat=?");
                            try {
                                psregistrasi.setString(1,rspasien.getString("no_rawat"));
                                rs=psregistrasi.executeQuery();
                                if(rs.next()){
                                   registrasi=rs.getDouble(1);
                                   ttlregistrasi=ttlregistrasi+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Jm : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psregistrasi!=null){
                                    psregistrasi.close();
                                }
                            }
                            
                            radiologi=0;
                            psradiologi=koneksi.prepareStatement("select sum(biaya) from periksa_radiologi where no_rawat=?");
                            try {
                                psradiologi.setString(1,rspasien.getString("no_rawat"));
                                rs=psradiologi.executeQuery();
                                if(rs.next()){
                                   radiologi=rs.getDouble(1);
                                   ttlradiologi=ttlradiologi+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Jm : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(psradiologi!=null){
                                    psradiologi.close();
                                }
                            }
                            
                            kamar=0;
                            pskamar=koneksi.prepareStatement("select sum(ttl_biaya) from kamar_inap where no_rawat=?");
                            try {
                                pskamar.setString(1,rspasien.getString("no_rawat"));
                                rs=pskamar.executeQuery();
                                if(rs.next()){
                                   kamar=rs.getDouble(1);
                                   ttlkamar=ttlkamar+rs.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Jm : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(pskamar!=null){
                                    pskamar.close();
                                }
                            }
                            
                            ttlbiaya=ttlbiaya+obat+obatlangsung+laborat+detaillaborat+jm+jm2+jm3+operasi+tambahan-potongan+registrasi+radiologi+kamar;
                            tabMode.addRow(new Object[]{
                                "",a+". "+rspasien.getString("kd_kamar")+" "+rspasien.getString("nm_pasien"),
                                rspasien.getString("tgl_masuk"),rspasien.getString("tgl_keluar"),
                                rspasien.getString("png_jawab"),Valid.SetAngka(obat+obatlangsung),
                                Valid.SetAngka(laborat+detaillaborat),Valid.SetAngka(jm+jm2+jm3+operasi),
                                Valid.SetAngka(tambahan),Valid.SetAngka(potongan),Valid.SetAngka(registrasi),
                                Valid.SetAngka(radiologi),Valid.SetAngka(kamar),Valid.SetAngka(obat+obatlangsung+laborat+
                                        detaillaborat+jm+jm2+jm3+operasi+tambahan-potongan+registrasi+radiologi+kamar)
                            });
                            a++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notif Pasien : "+e);
                    } finally{
                        if(rspasien!=null){
                            rspasien.close();
                        }
                        if(pspasien!=null){
                            pspasien.close();
                        }
                    }
                    i++;
                }
                
                tabMode.addRow(new Object[]{
                    ">>","Total :","","","",Valid.SetAngka(ttlobat+ttlobatlangsung),
                    Valid.SetAngka(ttllaborat+ttldetaillaborat),Valid.SetAngka(ttljm),Valid.SetAngka(ttltambahan),
                    Valid.SetAngka(ttlpotongan),Valid.SetAngka(ttlregistrasi),Valid.SetAngka(ttlradiologi),
                    Valid.SetAngka(ttlkamar),Valid.SetAngka(ttlbiaya)
                }); 
           } catch (Exception e) {
               System.out.println("Notif Bangsal : "+e);
           } finally{
               if(rsbangsal!=null){
                   rsbangsal.close();
               }
               if(psbangsal!=null){
                   psbangsal.close();
               }
           }            
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }
        
    }
    
}

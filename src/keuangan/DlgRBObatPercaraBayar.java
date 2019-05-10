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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgPenanggungJawab;

public class DlgRBObatPercaraBayar extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement pspenjab,psresep,psresep2;
    private ResultSet rspenjab,rsresep; 
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize(); 
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private int i=0,a=0;
    private double subtotal=0,ttlbiaya=0,embalase=0,ttlembalase=0,tuslah=0,ttltuslah=0;
    private String carabayar="",jumlah,total,emb,tsl;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRBObatPercaraBayar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Cara Bayar","Tanggal","Nama Obat","Jml","Biaya Obat","Embalase","Tuslah"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 8; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(300);
            }else if(i==2){
                column.setPreferredWidth(120);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(80);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdpenjab.setDocument(new batasInput((byte)8).getKata(kdpenjab));
                
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                    prosesCari();
                }      
                kdpenjab.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.emptTeks();}
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
        
        try {
             pspenjab=koneksi.prepareStatement(
                     "select kd_pj,png_jawab from penjab where kd_pj like ?");             
             psresep=koneksi.prepareStatement(
                     "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,"+
                     "detail_pemberian_obat.no_rawat,pasien.nm_pasien,reg_periksa.kd_pj,databarang.nama_brng,"+
                     "detail_pemberian_obat.jml,detail_pemberian_obat.biaya_obat,detail_pemberian_obat.embalase,"+
                     "detail_pemberian_obat.tuslah,(detail_pemberian_obat.total-(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as total, "+
                     "databarang.kode_brng from reg_periksa inner join pasien inner join "+
                     "detail_pemberian_obat inner join databarang "+
                     "on detail_pemberian_obat.kode_brng=databarang.kode_brng and detail_pemberian_obat.no_rawat=reg_periksa.no_rawat and "+
                     "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.kd_pj=? and detail_pemberian_obat.tgl_perawatan between ? and ? order by detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam");             
             psresep2=koneksi.prepareStatement(
                     "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,"+
                     "detail_pemberian_obat.no_rawat,pasien.nm_pasien,reg_periksa.kd_pj,databarang.nama_brng,"+
                     "detail_pemberian_obat.jml,detail_pemberian_obat.biaya_obat,detail_pemberian_obat.embalase,"+
                     "detail_pemberian_obat.tuslah,(detail_pemberian_obat.total-(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as total, "+
                     "databarang.kode_brng from reg_periksa inner join pasien inner join "+
                     "detail_pemberian_obat inner join databarang "+
                     "on detail_pemberian_obat.kode_brng=databarang.kode_brng and detail_pemberian_obat.no_rawat=reg_periksa.no_rawat and "+
                     "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.kd_pj=? and detail_pemberian_obat.tgl_perawatan between ? and ? order by detail_pemberian_obat.no_rawat");             
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

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnUrut1 = new javax.swing.JMenuItem();
        MnUrut2 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnUrut1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrut1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut1.setText("Urutkan Berdasar Tanggal Pemberian");
        MnUrut1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut1.setName("MnUrut1"); // NOI18N
        MnUrut1.setPreferredSize(new java.awt.Dimension(250, 26));
        MnUrut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrut1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUrut1);

        MnUrut2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrut2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut2.setForeground(new java.awt.Color(70, 70, 70));
        MnUrut2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut2.setText("Urutkan Berdasar Nama Pasien");
        MnUrut2.setActionCommand("Urutkan Berdasar Nomor Perawatan");
        MnUrut2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut2.setName("MnUrut2"); // NOI18N
        MnUrut2.setPreferredSize(new java.awt.Dimension(250, 26));
        MnUrut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrut2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUrut2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Penggunaan Obat Per Cara Bayar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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

        label11.setText("Tgl.Beri Obat :");
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

        label17.setText("Cara Bayar :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi4.add(label17);

        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(70, 23));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        panelisi4.add(kdpenjab);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(199, 23));
        panelisi4.add(nmpenjab);

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
            for(i=0;i<tabMode.getRowCount();i++){  
                jumlah="";
                try {
                    jumlah=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,4).toString()));
                } catch (Exception e) {
                    jumlah="";
                }
                total="";
                try {
                    total=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,5).toString()));
                } catch (Exception e) {
                    total="";
                }
                emb="";
                try {
                    emb=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()));
                } catch (Exception e) {
                    emb="";
                }
                tsl="";
                try {
                    tsl=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()));
                } catch (Exception e) {
                    tsl="";
                }
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"','"+
                                jumlah+"','"+total+"','"+emb+"','"+tsl+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Obat Perpenjab Poli"); 
            }
                        
            Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRBObatPerCaraBayar.jasper","report","[ Rekap Obat Per Cara Bayar ]",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnKeluar);
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

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText()); 
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
       kdpenjab.setText("");
        nmpenjab.setText("");
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, kdpenjab, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
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
            Valid.pindah(evt, kdpenjab, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdpenjab);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void MnUrut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrut1ActionPerformed
        prosesCari();
    }//GEN-LAST:event_MnUrut1ActionPerformed

    private void MnUrut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrut2ActionPerformed
        prosesCari2();
    }//GEN-LAST:event_MnUrut2ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRBObatPercaraBayar dialog = new DlgRBObatPercaraBayar(new javax.swing.JFrame(), true);
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
    private widget.TextBox Kd2;
    private javax.swing.JMenuItem MnUrut1;
    private javax.swing.JMenuItem MnUrut2;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpenjab;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmpenjab;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {             
        try{   
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
           Valid.tabelKosong(tabMode);
           pspenjab.setString(1,"%"+kdpenjab.getText()+"%"); 
           rspenjab=pspenjab.executeQuery();
           i=1;
           ttlbiaya=0;
           ttlembalase=0;
           ttltuslah=0;
           while(rspenjab.next()){
               tabMode.addRow(new Object[]{i+". ",rspenjab.getString("png_jawab"),"","",null,null,null});
               psresep.setString(1,rspenjab.getString("kd_pj"));
               psresep.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
               psresep.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
               a=1;
               rsresep=psresep.executeQuery();               
               subtotal=0;
               embalase=0;
               tuslah=0;
               //Object[] row={"No.","Dokter","Tanggal","Nama Obat","Jml","Biaya Obat","Embalase","Tuslah"};
               while(rsresep.next()){
                   subtotal=subtotal+rsresep.getDouble("total");
                   ttlbiaya=ttlbiaya+rsresep.getDouble("total");
                   embalase=embalase+rsresep.getDouble("embalase");
                   ttlembalase=ttlembalase+rsresep.getDouble("embalase");
                   tuslah=tuslah+rsresep.getDouble("tuslah");
                   ttltuslah=ttltuslah+rsresep.getDouble("tuslah");
                   tabMode.addRow(new Object[]{
                       "","   "+a+". ("+rsresep.getString("no_rawat")+") "+rsresep.getString("nm_pasien"),
                       rsresep.getString("tgl_perawatan")+" "+rsresep.getString("jam"),rsresep.getString("kode_brng")+" "+rsresep.getString("nama_brng"),
                       rsresep.getDouble("jml"),rsresep.getDouble("total"),rsresep.getDouble("embalase"),rsresep.getDouble("tuslah")
                   });
                   a++;
               }
               if(subtotal>0){
                   tabMode.addRow(new Object[]{"","       "+"Subtotal ",":","",null,subtotal,embalase,tuslah});
               }  
               i++;
           }
           tabMode.addRow(new Object[]{">>","Total ",":","",null,ttlbiaya,ttlembalase,ttltuslah});
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }        
    }
    
    private void prosesCari2() {             
        try{   
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
           Valid.tabelKosong(tabMode);
           pspenjab.setString(1,"%"+kdpenjab.getText()+"%"); 
           rspenjab=pspenjab.executeQuery();
           i=1;
           ttlbiaya=0;
           ttlembalase=0;
           ttltuslah=0;
           while(rspenjab.next()){
               tabMode.addRow(new Object[]{i+". ",rspenjab.getString("png_jawab"),"","",null,null,null});
               psresep2.setString(1,rspenjab.getString("kd_pj"));
               psresep2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
               psresep2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
               a=1;
               rsresep=psresep2.executeQuery();               
               subtotal=0;
               embalase=0;
               tuslah=0;
               //Object[] row={"No.","Dokter","Tanggal","Nama Obat","Jml","Biaya Obat","Embalase","Tuslah"};
               while(rsresep.next()){
                   subtotal=subtotal+rsresep.getDouble("total");
                   ttlbiaya=ttlbiaya+rsresep.getDouble("total");
                   embalase=embalase+rsresep.getDouble("embalase");
                   ttlembalase=ttlembalase+rsresep.getDouble("embalase");
                   tuslah=tuslah+rsresep.getDouble("tuslah");
                   ttltuslah=ttltuslah+rsresep.getDouble("tuslah");
                   tabMode.addRow(new Object[]{
                       "","   "+a+". ("+rsresep.getString("no_rawat")+") "+rsresep.getString("nm_pasien"),
                       rsresep.getString("tgl_perawatan")+" "+rsresep.getString("jam"),rsresep.getString("kode_brng")+" "+rsresep.getString("nama_brng"),
                       rsresep.getDouble("jml"),rsresep.getDouble("total"),rsresep.getDouble("embalase"),rsresep.getDouble("tuslah")
                   });
                   a++;
               }
               if(subtotal>0){
                   tabMode.addRow(new Object[]{"","       "+"Subtotal ",":","",null,subtotal,embalase,tuslah});
               }  
               i++;
           }
           tabMode.addRow(new Object[]{">>","Total ",":","",null,ttlbiaya,ttlembalase,ttltuslah});
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }        
    }
    
}

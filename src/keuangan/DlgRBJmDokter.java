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

public class DlgRBJmDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgPenanggungJawab carabayar=new DlgPenanggungJawab(null,false);
    private int i=0,a=0;
    private double jm=0,totaljm=0;
    private PreparedStatement ps,psralandokter,psralandokterdrpr,psranapdokter,psranapdokterdrpr,psbiayaoperator1,psbiayaoperator2,psbiayaoperator3,psbiayadokter_anak,
            psbiayadokter_anestesi,psperiksa_lab,psdetaillab,psperiksa_lab2,psdetaillab2,psperiksa_radiologi,psperiksa_radiologi2,psbiaya_dokter_pjanak,
            psbiaya_dokter_umum;
    private ResultSet rs,rsralandokter,rsralandokterdrpr,rsranapdokter,rsranapdokterdrpr,rsbiayaoperator1,rsbiayaoperator2,rsbiayaoperator3,rsbiayadokter_anak,
            rsbiayadokter_anestesi,rsperiksa_lab,rsdetaillab,rsperiksa_radiologi,rsbiaya_dokter_pjanak,rsbiaya_dokter_umum;
    private String pilihancarabayar="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRBJmDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Nama Dokter","Tindakan Medis","Jumlah","Jasa Medis"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(220);
            }else if(i==2){
                column.setPreferredWidth(350);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(130);
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
        
        carabayar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carabayar.getTable().getSelectedRow()!= -1){
                    pilihancarabayar=carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(),1).toString();
                }     
                prosesCari();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {carabayar.onCari();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        carabayar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    carabayar.dispose();
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
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        chkRalan = new widget.CekBox();
        chkRanap = new widget.CekBox();
        chkOperasi = new widget.CekBox();
        chkLaborat = new widget.CekBox();
        chkRadiologi = new widget.CekBox();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppTampilkanSeleksi.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanSeleksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanSeleksi.setForeground(new java.awt.Color(50,50,50));
        ppTampilkanSeleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanSeleksi.setText("Tampilkan Per Jenis Bayar");
        ppTampilkanSeleksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanSeleksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanSeleksi.setName("ppTampilkanSeleksi"); // NOI18N
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(230, 26));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Bulanan Jasa Medis Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
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
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Tindakan :");
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

        chkRalan.setSelected(true);
        chkRalan.setText("Ralan");
        chkRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRalan.setName("chkRalan"); // NOI18N
        chkRalan.setOpaque(false);
        chkRalan.setPreferredSize(new java.awt.Dimension(85, 30));
        chkRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRalanActionPerformed(evt);
            }
        });
        panelisi1.add(chkRalan);

        chkRanap.setSelected(true);
        chkRanap.setText("Ranap");
        chkRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRanap.setName("chkRanap"); // NOI18N
        chkRanap.setOpaque(false);
        chkRanap.setPreferredSize(new java.awt.Dimension(85, 30));
        chkRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRanapActionPerformed(evt);
            }
        });
        panelisi1.add(chkRanap);

        chkOperasi.setSelected(true);
        chkOperasi.setText("Operasi");
        chkOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkOperasi.setName("chkOperasi"); // NOI18N
        chkOperasi.setOpaque(false);
        chkOperasi.setPreferredSize(new java.awt.Dimension(85, 30));
        chkOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOperasiActionPerformed(evt);
            }
        });
        panelisi1.add(chkOperasi);

        chkLaborat.setSelected(true);
        chkLaborat.setText("Laboratorium");
        chkLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLaborat.setName("chkLaborat"); // NOI18N
        chkLaborat.setOpaque(false);
        chkLaborat.setPreferredSize(new java.awt.Dimension(95, 30));
        chkLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLaboratActionPerformed(evt);
            }
        });
        panelisi1.add(chkLaborat);

        chkRadiologi.setSelected(true);
        chkRadiologi.setText("Radiologi");
        chkRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRadiologi.setName("chkRadiologi"); // NOI18N
        chkRadiologi.setOpaque(false);
        chkRadiologi.setPreferredSize(new java.awt.Dimension(85, 30));
        chkRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRadiologiActionPerformed(evt);
            }
        });
        panelisi1.add(chkRadiologi);

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
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian BulananDokter"); 
            }
            
            Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRBTindakanDokter.jasper","report","[ Rekap Bulanan Tindakan Dokter ]",param);
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

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kddokter.setText("");
        nmdokter.setText("");
        pilihancarabayar="";
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, kddokter, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

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
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    prosesCari();
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kddokter, BtnAll);
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

    private void chkRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRalanActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRalanActionPerformed

    private void chkRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRadiologiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRadiologiActionPerformed

    private void chkLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLaboratActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkLaboratActionPerformed

    private void chkOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOperasiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkOperasiActionPerformed

    private void chkRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRanapActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRanapActionPerformed

    private void ppTampilkanSeleksiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanSeleksiBtnPrintActionPerformed
        carabayar.isCek();
        carabayar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carabayar.setLocationRelativeTo(internalFrame1);
        carabayar.setVisible(true);
    }//GEN-LAST:event_ppTampilkanSeleksiBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRBJmDokter dialog = new DlgRBJmDokter(new javax.swing.JFrame(), true);
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
    private widget.CekBox chkLaborat;
    private widget.CekBox chkOperasi;
    private widget.CekBox chkRadiologi;
    private widget.CekBox chkRalan;
    private widget.CekBox chkRanap;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppTampilkanSeleksi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{  
           ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and kd_dokter like ? order by nm_dokter");
           try {
                ps.setString(1,"%"+kddokter.getText()+"%");
                rs=ps.executeQuery();
                i=1;
                totaljm=0;
                while(rs.next()){
                   tabMode.addRow(new Object[]{""+i+".",rs.getString("nm_dokter"),"","",""});   
                   jm=0;
                   a=0;
                   //rawat jalan dokter   
                   if(chkRalan.isSelected()==true){
                        psralandokter=koneksi.prepareStatement(
                            "select jns_perawatan.nm_perawatan,rawat_jl_dr.tarif_tindakandr,"+
                            "count(rawat_jl_dr.kd_jenis_prw) as jml,"+
                            "sum(rawat_jl_dr.tarif_tindakandr) as total,rawat_jl_dr.kd_jenis_prw "+
                            "from reg_periksa inner join jns_perawatan inner join rawat_jl_dr "+
                            "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                            "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_dokter=? "+
                            "and reg_periksa.kd_pj like ? and rawat_jl_dr.tarif_tindakandr>0 group by rawat_jl_dr.kd_jenis_prw order by jns_perawatan.nm_perawatan");   
                        psralandokterdrpr=koneksi.prepareStatement(
                            "select jns_perawatan.nm_perawatan,rawat_jl_drpr.tarif_tindakandr,"+
                            "count(rawat_jl_drpr.kd_jenis_prw) as jml,"+
                            "sum(rawat_jl_drpr.tarif_tindakandr) as total,rawat_jl_drpr.kd_jenis_prw "+
                            "from reg_periksa inner join jns_perawatan inner join rawat_jl_drpr "+
                            "on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                            "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                            "and reg_periksa.kd_pj like ? and rawat_jl_drpr.tarif_tindakandr>0 group by rawat_jl_drpr.kd_jenis_prw order by jns_perawatan.nm_perawatan");   
                        try {
                            psralandokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psralandokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psralandokter.setString(3,rs.getString("kd_dokter"));
                            psralandokter.setString(4,"%"+pilihancarabayar+"%");
                            rsralandokter=psralandokter.executeQuery();

                            psralandokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psralandokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psralandokterdrpr.setString(3,rs.getString("kd_dokter"));
                            psralandokterdrpr.setString(4,"%"+pilihancarabayar+"%");
                            rsralandokterdrpr=psralandokterdrpr.executeQuery();

                            if(rsralandokter.next()||rsralandokterdrpr.next()){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Rawat Jalan ","","",""});   
                            }

                            rsralandokter.beforeFirst();               
                            while(rsralandokter.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsralandokter.getString("nm_perawatan")+" ("+rsralandokter.getString("kd_jenis_prw")+")",
                                    rsralandokter.getString("jml"),Valid.SetAngka(rsralandokter.getDouble("total"))
                                });     
                                jm=jm+rsralandokter.getDouble("total");
                            }

                            rsralandokterdrpr.beforeFirst();               
                            while(rsralandokterdrpr.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsralandokterdrpr.getString("nm_perawatan")+" ("+rsralandokterdrpr.getString("kd_jenis_prw")+")",
                                    rsralandokterdrpr.getString("jml"),Valid.SetAngka(rsralandokterdrpr.getDouble("total"))
                                });     
                                jm=jm+rsralandokterdrpr.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Ralan : "+e);
                        } finally{
                            if(rsralandokter!=null){
                                rsralandokter.close();
                            }
                            if(psralandokter!=null){
                                psralandokter.close();
                            }
                            if(rsralandokterdrpr!=null){
                                rsralandokterdrpr.close();
                            }
                            if(psralandokterdrpr!=null){
                                psralandokterdrpr.close();
                            }
                        }
                   }                    

                   //rawat inap dokter   
                   if(chkRanap.isSelected()==true){
                        psranapdokter=koneksi.prepareStatement(
                            "select jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                            "count(rawat_inap_dr.kd_jenis_prw) as jml, " +
                            "sum(rawat_inap_dr.tarif_tindakandr) as total,rawat_inap_dr.kd_jenis_prw "+
                            "from jns_perawatan_inap inner join rawat_inap_dr inner join reg_periksa "+
                            "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                            "where rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.kd_dokter=? "+
                            "and reg_periksa.kd_pj like ? and rawat_inap_dr.tarif_tindakandr>0 group by jns_perawatan_inap.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan  ");
                        psranapdokterdrpr=koneksi.prepareStatement(
                            "select jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                            "count(rawat_inap_drpr.kd_jenis_prw) as jml, " +
                            "sum(rawat_inap_drpr.tarif_tindakandr) as total,rawat_inap_drpr.kd_jenis_prw "+
                            "from jns_perawatan_inap inner join rawat_inap_drpr inner join reg_periksa "+
                            "on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                            "where rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                            "and reg_periksa.kd_pj like ? and rawat_inap_drpr.tarif_tindakandr>0 group by jns_perawatan_inap.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan  ");
                        try {
                            psranapdokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psranapdokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psranapdokter.setString(3,rs.getString("kd_dokter"));
                            psranapdokter.setString(4,"%"+pilihancarabayar+"%");
                            rsranapdokter=psranapdokter.executeQuery();

                            psranapdokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psranapdokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psranapdokterdrpr.setString(3,rs.getString("kd_dokter"));
                            psranapdokterdrpr.setString(4,"%"+pilihancarabayar+"%");
                            rsranapdokterdrpr=psranapdokterdrpr.executeQuery();
                            if((rsranapdokterdrpr.next())||(rsranapdokter.next())){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Rawat Inap","","",""});  
                            }
                            rsranapdokter.beforeFirst();
                            while(rsranapdokter.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsranapdokter.getString("nm_perawatan")+" ("+rsranapdokter.getString("kd_jenis_prw")+")",
                                    rsranapdokter.getString("jml"),Valid.SetAngka(rsranapdokter.getDouble("total"))
                                });     
                                jm=jm+rsranapdokter.getDouble("total");
                            }
                            rsranapdokterdrpr.beforeFirst();
                            while(rsranapdokterdrpr.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsranapdokterdrpr.getString("nm_perawatan")+" ("+rsranapdokterdrpr.getString("kd_jenis_prw")+")",
                                    rsranapdokterdrpr.getString("jml"),Valid.SetAngka(rsranapdokterdrpr.getDouble("total"))
                                });     
                                jm=jm+rsranapdokterdrpr.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Ranap : "+e);
                        } finally{
                            if(rsranapdokter!=null){
                                rsranapdokter.close();
                            }
                            if(psranapdokter!=null){
                                psranapdokter.close();
                            }
                            if(rsranapdokterdrpr!=null){
                                rsranapdokterdrpr.close();
                            }
                            if(psranapdokterdrpr!=null){
                                psranapdokterdrpr.close();
                            }
                        }
                   } 

                   if(chkOperasi.isSelected()==true){
                        psbiayaoperator1=koneksi.prepareStatement(
                            "select paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                            "count(operasi.kode_paket) as jml, " +
                            "sum(operasi.biayaoperator1) as total,operasi.kode_paket "+
                            "from paket_operasi inner join operasi inner join reg_periksa "+
                            "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                            "where operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                            "and reg_periksa.kd_pj like ? and operasi.biayaoperator1>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
                        psbiayaoperator2=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                            "count(operasi.kode_paket) as jml, " +
                            "sum(operasi.biayaoperator2) as total,operasi.kode_paket "+
                            "from paket_operasi inner join operasi inner join reg_periksa "+
                            "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                            "where operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                            "and reg_periksa.kd_pj like ? and operasi.biayaoperator2>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
                        psbiayaoperator3=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                            "count(operasi.kode_paket) as jml, " +
                            "sum(operasi.biayaoperator3) as total,operasi.kode_paket "+
                            "from paket_operasi inner join operasi inner join reg_periksa "+
                            "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                            "where operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                            "and reg_periksa.kd_pj like ? and operasi.biayaoperator3>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
                        psbiayadokter_anak=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                            "count(operasi.kode_paket) as jml, " +
                            "sum(operasi.biayadokter_anak) as total,operasi.kode_paket "+
                            "from paket_operasi inner join operasi inner join reg_periksa "+
                            "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                            "where operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                            "and reg_periksa.kd_pj like ? and operasi.biayadokter_anak>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
                        psbiaya_dokter_umum=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                            "count(operasi.kode_paket) as jml, " +
                            "sum(operasi.biaya_dokter_umum) as total,operasi.kode_paket "+
                            "from paket_operasi inner join operasi inner join reg_periksa "+
                            "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                            "where operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                            "and reg_periksa.kd_pj like ? and operasi.biaya_dokter_umum>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
                        psbiaya_dokter_pjanak=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                            "count(operasi.kode_paket) as jml, " +
                            "sum(operasi.biaya_dokter_pjanak) as total,operasi.kode_paket "+
                            "from paket_operasi inner join operasi inner join reg_periksa "+
                            "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                            "where operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                            "and reg_periksa.kd_pj like ? and operasi.biaya_dokter_pjanak>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
                        psbiayadokter_anestesi=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                            "count(operasi.kode_paket) as jml, " +
                            "sum(operasi.biayadokter_anestesi) as total,operasi.kode_paket "+
                            "from paket_operasi inner join operasi inner join reg_periksa "+
                            "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                            "where operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                            "and reg_periksa.kd_pj like ? and operasi.biayadokter_anestesi>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
                        try {
                            psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaoperator1.setString(3,rs.getString("kd_dokter"));
                            psbiayaoperator1.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaoperator1=psbiayaoperator1.executeQuery();
                            
                            psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaoperator2.setString(3,rs.getString("kd_dokter"));
                            psbiayaoperator2.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaoperator2=psbiayaoperator2.executeQuery();
                            
                            psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaoperator3.setString(3,rs.getString("kd_dokter"));
                            psbiayaoperator3.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaoperator3=psbiayaoperator3.executeQuery(); 
                            
                            psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));
                            psbiayadokter_anak.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayadokter_anak=psbiayadokter_anak.executeQuery();   

                            psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));
                            psbiaya_dokter_pjanak.setString(4,"%"+pilihancarabayar+"%");
                            rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();  
                            
                            psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));
                            psbiaya_dokter_umum.setString(4,"%"+pilihancarabayar+"%");
                            rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();  
                            
                            psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));
                            psbiayadokter_anestesi.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                            
                            if((rsbiayaoperator1.next())||(rsbiayaoperator2.next())||(rsbiayaoperator3.next())||(rsbiayadokter_anak.next())||(rsbiaya_dokter_pjanak.next())||(rsbiaya_dokter_umum.next())||(rsbiayadokter_anestesi.next())){
                                 a++;
                                 tabMode.addRow(new Object[]{"","",a+". Operasi/VK","","",""});   
                            }

                            //dokter operasi               
                            rsbiayaoperator1.beforeFirst();
                            while(rsbiayaoperator1.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)",
                                    rsbiayaoperator1.getString("jml"),Valid.SetAngka(rsbiayaoperator1.getDouble("total"))
                                });        
                                jm=jm+rsbiayaoperator1.getDouble("total");
                            }

                            //dokter anasthesi               
                            rsbiayaoperator2.beforeFirst();
                            while(rsbiayaoperator2.next()){
                                tabMode.addRow(new Object[]{"","","     "+rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)",
                                    rsbiayaoperator2.getString("jml"),Valid.SetAngka(rsbiayaoperator2.getDouble("total"))
                                });      
                                jm=jm+rsbiayaoperator2.getDouble("total");
                            }
                            //rsbiayaoperator2.close();

                            //dokter anasthesi               
                            rsbiayaoperator3.beforeFirst();
                            while(rsbiayaoperator3.next()){
                                tabMode.addRow(new Object[]{"","","     "+rsbiayaoperator3.getString("nm_perawatan")+" ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)",
                                    rsbiayaoperator3.getString("jml"),Valid.SetAngka(rsbiayaoperator3.getDouble("total"))
                                });           
                                jm=jm+rsbiayaoperator3.getDouble("total");
                            }
                            //rsbiayaoperator3.close();

                            //dokter anasthesi               
                            rsbiayadokter_anak.beforeFirst();
                            while(rsbiayadokter_anak.next()){
                                tabMode.addRow(new Object[]{"","","     "+rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)",
                                    rsbiayadokter_anak.getString("jml"),Valid.SetAngka(rsbiayadokter_anak.getDouble("total"))
                                });       
                                jm=jm+rsbiayadokter_anak.getDouble("total");
                            }
                            //rsbiayadokter_anak.close();

                            //dokter anasthesi               
                            rsbiayadokter_anestesi.beforeFirst();
                            while(rsbiayadokter_anestesi.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)",
                                    rsbiayadokter_anestesi.getString("jml"),Valid.SetAngka(rsbiayadokter_anestesi.getDouble("total"))
                                });       
                                jm=jm+rsbiayadokter_anestesi.getDouble("total");
                            }

                            //dokter pj anak              
                            rsbiaya_dokter_pjanak.beforeFirst();
                            while(rsbiaya_dokter_pjanak.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)",
                                    rsbiaya_dokter_pjanak.getString("jml"),Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("total"))
                                });       
                                jm=jm+rsbiaya_dokter_pjanak.getDouble("total");
                            }
                            //rsbiaya_dokter_pjanak.close();

                            //dokter umum              
                            rsbiaya_dokter_umum.beforeFirst();
                            while(rsbiaya_dokter_umum.next()){
                                tabMode.addRow(new Object[]{"","","     "+rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)",
                                    rsbiaya_dokter_umum.getString("jml"),Valid.SetAngka(rsbiaya_dokter_umum.getDouble("total"))
                                });       
                                jm=jm+rsbiaya_dokter_umum.getDouble("total");
                            }
                            //rsbiaya_dokter_umum.close();
                        } catch (Exception e) {
                            System.out.println("Notifikasi Operasi : "+e);
                        } finally{
                            if(rsbiayaoperator1!=null){
                                rsbiayaoperator1.close();
                            }
                            if(psbiayaoperator1!=null){
                                psbiayaoperator1.close();
                            }
                            if(rsbiayaoperator2!=null){
                                rsbiayaoperator2.close();
                            }
                            if(psbiayaoperator2!=null){
                                psbiayaoperator2.close();
                            }
                            if(rsbiayaoperator3!=null){
                                rsbiayaoperator3.close();
                            }
                            if(psbiayaoperator3!=null){
                                psbiayaoperator3.close();
                            }                            
                            if(rsbiayadokter_anak!=null){
                                rsbiayadokter_anak.close();
                            }
                            if(psbiayadokter_anak!=null){
                                psbiayadokter_anak.close();
                            }                            
                            if(rsbiaya_dokter_pjanak!=null){
                                rsbiaya_dokter_pjanak.close();
                            }
                            if(psbiaya_dokter_pjanak!=null){
                                psbiaya_dokter_pjanak.close();
                            }
                            if(rsbiaya_dokter_umum!=null){
                                rsbiaya_dokter_umum.close();
                            }
                            if(psbiaya_dokter_umum!=null){
                                psbiaya_dokter_umum.close();
                            }
                            if(rsbiayadokter_anestesi!=null){
                                rsbiayadokter_anestesi.close();
                            }
                            if(psbiayadokter_anestesi!=null){
                                psbiayadokter_anestesi.close();
                            }
                        }   
                   }

                   if(chkLaborat.isSelected()==true){
                        psperiksa_lab=koneksi.prepareStatement(
                            "select jns_perawatan_lab.nm_perawatan,periksa_lab.tarif_tindakan_dokter,"+
                            "periksa_lab.kd_jenis_prw,count(periksa_lab.kd_jenis_prw) as jml, "+
                            "sum(periksa_lab.tarif_tindakan_dokter) as total,jns_perawatan_lab.kd_jenis_prw "+
                            " from periksa_lab inner join jns_perawatan_lab inner join reg_periksa "+
                            " on periksa_lab.no_rawat=reg_periksa.no_rawat and periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            " where periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? "+
                            " and reg_periksa.kd_pj like ? and periksa_lab.tarif_tindakan_dokter>0 group by periksa_lab.kd_jenis_prw order by jns_perawatan_lab.nm_perawatan  "); 
                        try {
                            psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                            psperiksa_lab.setString(4,"%"+pilihancarabayar+"%");
                            rsperiksa_lab=psperiksa_lab.executeQuery();
                            if(rsperiksa_lab.next()){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Pemeriksaan Lab ","","",""});   
                            }

                            rsperiksa_lab.beforeFirst();
                            while(rsperiksa_lab.next()){    
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",
                                    rsperiksa_lab.getString("jml"),Valid.SetAngka(rsperiksa_lab.getDouble("total"))
                                });      
                                jm=jm+rsperiksa_lab.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Periksa Lab : "+e);
                        } finally{
                            if(rsperiksa_lab!=null){
                                rsperiksa_lab.close();
                            }
                            if(psperiksa_lab!=null){
                                psperiksa_lab.close();
                            }
                        }
                        
                        psdetaillab=koneksi.prepareStatement(
                            "select sum(detail_periksa_lab.bagian_dokter) as total,"+
                            "template_laboratorium.Pemeriksaan,count(detail_periksa_lab.id_template) as jml, "+
                            "periksa_lab.kd_jenis_prw "+
                            "from detail_periksa_lab inner join periksa_lab "+
                            "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                            "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                            "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                            "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                            "and periksa_lab.jam=detail_periksa_lab.jam "+
                            "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                            "where detail_periksa_lab.tgl_periksa between ? and ? "+
                            "and periksa_lab.kd_dokter=? and reg_periksa.kd_pj like ? "+
                            "and detail_periksa_lab.bagian_dokter>0 group by detail_periksa_lab.id_template");
                        try {
                            psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetaillab.setString(3,rs.getString("kd_dokter"));
                            psdetaillab.setString(4,"%"+pilihancarabayar+"%");
                            rsdetaillab=psdetaillab.executeQuery();
                            rsdetaillab.last();
                            if(rsdetaillab.getRow()>0){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Detail Pemeriksaan Lab ","","",""});
                            } 
                            rsdetaillab.beforeFirst();
                            while(rsdetaillab.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",
                                    rsdetaillab.getString("jml"),Valid.SetAngka(rsdetaillab.getDouble("total"))
                                });    
                                jm=jm+rsdetaillab.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Detail Lab : "+e);
                        } finally{
                            if(rsdetaillab!=null){
                                rsdetaillab.close();
                            }
                            if(psdetaillab!=null){
                                psdetaillab.close();
                            }
                        }

                        //perujuk Lab
                        psperiksa_lab2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.nm_perawatan,periksa_lab.tarif_perujuk,"+
                            "periksa_lab.kd_jenis_prw,count(periksa_lab.kd_jenis_prw) as jml,periksa_lab.kd_jenis_prw, "+
                            "sum(periksa_lab.tarif_perujuk) as total "+
                            " from periksa_lab inner join jns_perawatan_lab inner join reg_periksa "+
                            " on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            " where periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? "+
                            " and reg_periksa.kd_pj like ? and periksa_lab.tarif_perujuk>0 group by periksa_lab.kd_jenis_prw order by jns_perawatan_lab.nm_perawatan  "); 
                        try {
                            psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                            psperiksa_lab2.setString(4,"%"+pilihancarabayar+"%");
                            rsperiksa_lab=psperiksa_lab2.executeQuery();
                            if(rsperiksa_lab.next()){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Perujuk Lab","","",""});   
                            }

                            rsperiksa_lab.beforeFirst();
                            while(rsperiksa_lab.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",
                                    rsperiksa_lab.getString("jml"),Valid.SetAngka(rsperiksa_lab.getDouble("total"))
                                });   
                                jm=jm+rsperiksa_lab.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Perujuk Lab : "+e);
                        } finally{
                            if(rsperiksa_lab!=null){
                                rsperiksa_lab.close();
                            }
                            if(psperiksa_lab2!=null){
                                psperiksa_lab2.close();
                            }
                        }
                        
                        
                        psdetaillab2=koneksi.prepareStatement(
                            "select sum(detail_periksa_lab.bagian_perujuk) as total, "+
                           "count(detail_periksa_lab.id_template)as jml,"+
                            "template_laboratorium.Pemeriksaan, "+
                            "periksa_lab.kd_jenis_prw "+
                            "from detail_periksa_lab inner join periksa_lab "+
                            "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                            "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                            "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                            "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                            "and periksa_lab.jam=detail_periksa_lab.jam "+
                            "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                            "where detail_periksa_lab.tgl_periksa between ? and ? "+
                            "and periksa_lab.dokter_perujuk=? and reg_periksa.kd_pj like ? "+
                            "and detail_periksa_lab.bagian_perujuk>0 group by detail_periksa_lab.id_template");
                        try {
                            psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetaillab2.setString(3,rs.getString("kd_dokter"));
                            psdetaillab2.setString(4,"%"+pilihancarabayar+"%");
                            rsdetaillab=psdetaillab2.executeQuery();
                            rsdetaillab.last();
                            if(rsdetaillab.getRow()>0){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Detail Perujuk Lab ","","",""});
                            } 
                            rsdetaillab.beforeFirst();
                            while(rsdetaillab.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",
                                    rsdetaillab.getString("jml"),rsdetaillab.getString("total")
                                });    
                                jm=jm+rsdetaillab.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Detail Perujuk : "+e);
                        } finally{
                            if(rsdetaillab!=null){
                                rsdetaillab.close();
                            }
                            if(psdetaillab2!=null){
                                psdetaillab2.close();
                            }
                        }
                   }

                   if(chkRadiologi.isSelected()==true){
                       //periksa radiologi
                        psperiksa_radiologi=koneksi.prepareStatement(
                            "select jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tarif_tindakan_dokter,"+
                            "periksa_radiologi.kd_jenis_prw,count(periksa_radiologi.kd_jenis_prw) as jml, "+
                            "sum(periksa_radiologi.tarif_tindakan_dokter) as total "+
                            " from periksa_radiologi inner join jns_perawatan_radiologi inner join reg_periksa"+
                            " on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw and periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                            " where periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.kd_dokter=? "+
                            " and reg_periksa.kd_pj like ? and periksa_radiologi.tarif_tindakan_dokter>0 group by periksa_radiologi.kd_jenis_prw order by jns_perawatan_radiologi.nm_perawatan  "); 
                        try {
                            psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                            psperiksa_radiologi.setString(4,"%"+pilihancarabayar+"%");
                            rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                            if(rsperiksa_radiologi.next()){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Pemeriksaan Radiologi","","",""});   
                            }

                            rsperiksa_radiologi.beforeFirst();
                            while(rsperiksa_radiologi.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",
                                    rsperiksa_radiologi.getString("jml"),Valid.SetAngka(rsperiksa_radiologi.getDouble("total"))
                                });             
                                jm=jm+rsperiksa_radiologi.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Radiologi : "+e);
                        } finally{
                            if(rsperiksa_radiologi!=null){
                                rsperiksa_radiologi.close();
                            }
                            if(psperiksa_radiologi!=null){
                                psperiksa_radiologi.close();
                            }
                        }

                        //perujuk radiologi
                        psperiksa_radiologi2=koneksi.prepareStatement("select jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tarif_perujuk,"+
                            "periksa_radiologi.kd_jenis_prw,count(periksa_radiologi.kd_jenis_prw) as jml, "+
                            "sum(periksa_radiologi.tarif_perujuk) as total "+
                            " from periksa_radiologi inner join jns_perawatan_radiologi inner join reg_periksa "+
                            " on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw and periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                            " where periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.dokter_perujuk=? "+
                            " and reg_periksa.kd_pj like ? >0 group by periksa_radiologi.kd_jenis_prw order by jns_perawatan_radiologi.nm_perawatan  "); 
                        try{
                            psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                            psperiksa_radiologi2.setString(4,"%"+pilihancarabayar+"%");
                            rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                            if(rsperiksa_radiologi.next()){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Perujuk Radiologi","","",""});   
                            }

                            rsperiksa_radiologi.beforeFirst();
                            while(rsperiksa_radiologi.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",
                                    rsperiksa_radiologi.getString("jml"),Valid.SetAngka(rsperiksa_radiologi.getDouble("total"))
                                });             
                                jm=jm+rsperiksa_radiologi.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Radiologi Perujuk : "+e);
                        } finally{
                            if(rsperiksa_radiologi!=null){
                                rsperiksa_radiologi.close();
                            }
                            if(psperiksa_radiologi2!=null){
                                psperiksa_radiologi2.close();
                            }
                        }
                   }    

                   totaljm=totaljm+jm;
                   if(jm>0){
                       tabMode.addRow(new Object[]{"","","Total : ","",Valid.SetAngka(jm)});
                   }    
                   i++;
                } 
           } catch (Exception e) {
               System.out.println("Notifikasi Perujuk Radiologi : "+e);
           } finally{
               if(rs!=null){
                   rs.close();
               }
               if(ps!=null){
                   ps.close();
               }
           }           
            
           if(totaljm>0){
               tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","","",Valid.SetAngka(totaljm)});
           }            
        }catch(SQLException e){
           System.out.println("Catatan  "+e);
        }
        
    }
    
    public void isCek(){
       // BtnPrint.setEnabled(var.getbulanan_dokter());
    }
    
}

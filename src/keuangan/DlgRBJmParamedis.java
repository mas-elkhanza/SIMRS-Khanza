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
import kepegawaian.DlgCariPetugas;
import simrskhanza.DlgPenanggungJawab;

public class DlgRBJmParamedis extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private DlgPenanggungJawab carabayar=new DlgPenanggungJawab(null,false);
    private PreparedStatement ps,psralanpr,psralandrpr,psperiksalab,psranappr,psranapdrpr,psbiayaasisten_operator1,
            psbiayaasisten_operator2,psbiayaasisten_operator3,psbiayainstrumen,psbiayaperawaat_resusitas,
            psbiayaasisten_anestesi,psbiayaasisten_anestesi2,psbiayabidan,psbiayabidan2,psbiayabidan3,psbiayaperawat_luar,
            psdetaillab,psperiksaradiologi,psomloop,psomloop2,psomloop3,psomloop4,psomloop5;
    private ResultSet rs,rsralanpr,rsralandrpr,rsperiksalab,rsranappr,rsranapdrpr,rsbiayaasisten_operator1,
            rsbiayaasisten_operator2,rsbiayaasisten_operator3,rsperiksaradiologi,rsbiayainstrumen,rsbiayaperawaat_resusitas,
            rsbiayaasisten_anestesi,rsbiayaasisten_anestesi2,rsbiayabidan,rsbiayabidan2,rsbiayabidan3,rsbiayaperawat_luar,
            rsdetaillab,rsomloop,rsomloop2,rsomloop3,rsomloop4,rsomloop5;
    private String pilihancarabayar="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRBJmParamedis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Nama Paramedis","Tindakan Medis","Jumlah","Jasa Medis"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int m = 0; m < 5; m++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(m);
            if(m==0){
                column.setPreferredWidth(35);
            }else if(m==1){
                column.setPreferredWidth(220);
            }else if(m==2){
                column.setPreferredWidth(450);
            }else if(m==3){
                column.setPreferredWidth(100);
            }else if(m==4){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdpetugas.setDocument(new batasInput((byte)5).getKata(kdpetugas));
                
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){                   
                    kdpetugas.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    nmparam.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                    prosesCari();
                }     
                kdpetugas.requestFocus();                
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
        
        bangsal.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    bangsal.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();  
    private DlgCariPetugas bangsal=new DlgCariPetugas(null,false);
    private int i=0,a=0;
    private double jm=0,totaljm=0,detaillab=0;

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
        kdpetugas = new widget.TextBox();
        nmparam = new widget.TextBox();
        btnBangsal = new widget.Button();
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
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(250, 26));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Bulanan Jasa Medis Paramedis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
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

        label17.setText("Paramedis :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);

        kdpetugas.setName("kdpetugas"); // NOI18N
        kdpetugas.setPreferredSize(new java.awt.Dimension(70, 23));
        kdpetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpetugasKeyPressed(evt);
            }
        });
        panelisi4.add(kdpetugas);

        nmparam.setEditable(false);
        nmparam.setName("nmparam"); // NOI18N
        nmparam.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmparam);

        btnBangsal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsal.setMnemonic('3');
        btnBangsal.setToolTipText("Alt+3");
        btnBangsal.setName("btnBangsal"); // NOI18N
        btnBangsal.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBangsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsalActionPerformed(evt);
            }
        });
        btnBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBangsalKeyPressed(evt);
            }
        });
        panelisi4.add(btnBangsal);

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
            
            Sequel.queryu("delete from temporary");
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
            Valid.MyReport("rptRBTindakanParamedis.jasper","report","[ Rekap Bulanan Tindakan Paramedis ]",param);
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

    private void kdpetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmparam,kdpetugas.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmparam,kdpetugas.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmparam,kdpetugas.getText()); 
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBangsalActionPerformed(null);
        }
    }//GEN-LAST:event_kdpetugasKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        //TCari.setText("");
        kdpetugas.setText("");
        nmparam.setText("");
        pilihancarabayar="";
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, kdpetugas, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void btnBangsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsalActionPerformed
        bangsal.isCek();       
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
}//GEN-LAST:event_btnBangsalActionPerformed

private void btnBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsalKeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_btnBangsalKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    prosesCari();
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kdpetugas, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdpetugas);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void chkRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRanapActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRanapActionPerformed

    private void chkOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOperasiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkOperasiActionPerformed

    private void chkLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLaboratActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkLaboratActionPerformed

    private void chkRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRadiologiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRadiologiActionPerformed

    private void chkRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRalanActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRalanActionPerformed

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
            DlgRBJmParamedis dialog = new DlgRBJmParamedis(new javax.swing.JFrame(), true);
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
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBangsal;
    private widget.CekBox chkLaborat;
    private widget.CekBox chkOperasi;
    private widget.CekBox chkRadiologi;
    private widget.CekBox chkRalan;
    private widget.CekBox chkRanap;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpetugas;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.TextBox nmparam;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppTampilkanSeleksi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{      
           ps=koneksi.prepareStatement("select nip,nama from petugas where petugas.status='1' and nip like ? order by nama");
           try {
                ps.setString(1,"%"+kdpetugas.getText()+"%");
                rs=ps.executeQuery();
                i=1;
                totaljm=0;
                while(rs.next()){
                   tabMode.addRow(new Object[]{""+i+".",rs.getString("nama"),"","",""});   
                   jm=0;
                   a=0;
                   //rawat jalan   
                   if(chkRalan.isSelected()==true){
                        psralanpr=koneksi.prepareStatement("select jns_perawatan.nm_perawatan,rawat_jl_pr.tarif_tindakanpr,"+
                            "count(rawat_jl_pr.kd_jenis_prw) as jml,"+
                            "sum(rawat_jl_pr.tarif_tindakanpr) as total "+
                            "from reg_periksa inner join jns_perawatan inner join rawat_jl_pr "+
                            "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                            "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.nip=? "+
                            "and reg_periksa.kd_pj like ? and rawat_jl_pr.biaya_rawat>0 "+
                            "group by rawat_jl_pr.kd_jenis_prw order by jns_perawatan.nm_perawatan");
                        psralandrpr=koneksi.prepareStatement("select jns_perawatan.nm_perawatan,rawat_jl_drpr.tarif_tindakanpr,"+
                            "count(rawat_jl_drpr.kd_jenis_prw) as jml,"+
                            "sum(rawat_jl_drpr.tarif_tindakanpr) as total "+
                            "from reg_periksa inner join jns_perawatan inner join rawat_jl_drpr "+
                            "on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                            "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.nip=? "+
                            "and reg_periksa.kd_pj like ? and rawat_jl_drpr.biaya_rawat>0 "+
                            "group by rawat_jl_drpr.kd_jenis_prw order by jns_perawatan.nm_perawatan");
                        try {
                            psralanpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psralanpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psralanpr.setString(3,rs.getString("nip"));
                            psralanpr.setString(4,"%"+pilihancarabayar+"%");
                            rsralanpr=psralanpr.executeQuery();

                            psralandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psralandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psralandrpr.setString(3,rs.getString("nip"));
                            psralandrpr.setString(4,"%"+pilihancarabayar+"%");
                            rsralandrpr=psralandrpr.executeQuery();

                            if(rsralanpr.next()||rsralandrpr.next()){ 
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Rawat Jalan","",""});   
                            }

                            rsralanpr.beforeFirst();
                            while(rsralanpr.next()){                    
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsralanpr.getString("nm_perawatan"),
                                    rsralanpr.getString("jml"),Valid.SetAngka(rsralanpr.getDouble("total"))
                                });        
                                jm=jm+rsralanpr.getDouble("total");
                            }

                            rsralandrpr.beforeFirst();
                            while(rsralandrpr.next()){                    
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsralandrpr.getString("nm_perawatan"),
                                    rsralandrpr.getString("jml"),Valid.SetAngka(rsralandrpr.getDouble("total"))
                                });        
                                jm=jm+rsralandrpr.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsralanpr!=null){
                                rsralanpr.close();
                            }
                            if(rsralandrpr!=null){
                                rsralandrpr.close();
                            }
                            if(psralanpr!=null){
                                psralanpr.close();
                            }
                            if(psralandrpr!=null){
                                psralandrpr.close();
                            }
                        }
                   }          

                   //rawat inap               
                   if(chkRanap.isSelected()==true){
                        psranappr=koneksi.prepareStatement("select jns_perawatan_inap.nm_perawatan,rawat_inap_pr.tarif_tindakanpr,"+
                                "count(rawat_inap_pr.kd_jenis_prw) as jml, " +
                                "sum(rawat_inap_pr.tarif_tindakanpr) as total "+
                                "from jns_perawatan_inap inner join rawat_inap_pr inner join reg_periksa "+
                                "on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                                "where rawat_inap_pr.tgl_perawatan between ? and ? and rawat_inap_pr.nip=? "+
                                "and reg_periksa.kd_pj like ? and rawat_inap_pr.biaya_rawat>0 "+
                                "group by rawat_inap_pr.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan  ");
                        psranapdrpr=koneksi.prepareStatement("select jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakanpr,"+
                                "count(rawat_inap_drpr.kd_jenis_prw) as jml, " +
                                "sum(rawat_inap_drpr.tarif_tindakanpr) as total "+
                                "from jns_perawatan_inap inner join rawat_inap_drpr inner join reg_periksa "+
                                "on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                                "where rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.nip=? "+
                                "and reg_periksa.kd_pj like ? and rawat_inap_drpr.biaya_rawat>0 "+
                                "group by rawat_inap_drpr.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan  ");
                        try {
                            psranappr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psranappr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psranappr.setString(3,rs.getString("nip"));
                            psranappr.setString(4,"%"+pilihancarabayar+"%");
                            rsranappr=psranappr.executeQuery();   

                            psranapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psranapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psranapdrpr.setString(3,rs.getString("nip"));
                            psranapdrpr.setString(4,"%"+pilihancarabayar+"%");
                            rsranapdrpr=psranapdrpr.executeQuery();
                            if((rsranapdrpr.next())||(rsranappr.next())){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Rawat Inap ","","",""}); 
                            }
                            //ranappr
                            rsranappr.beforeFirst();
                            while(rsranappr.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsranappr.getString("nm_perawatan"),
                                    rsranappr.getString("jml"),Valid.SetAngka(rsranappr.getDouble("total"))
                                });  
                                jm=jm+rsranappr.getDouble("total");
                            }

                            rsranapdrpr.beforeFirst();
                            while(rsranapdrpr.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsranapdrpr.getString("nm_perawatan"),
                                    rsranapdrpr.getString("jml"),Valid.SetAngka(rsranapdrpr.getDouble("total"))
                                });  
                                jm=jm+rsranapdrpr.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsranappr!=null){
                                rsranappr.close();
                            }
                            if(rsranapdrpr!=null){
                                rsranapdrpr.close();
                            }
                            if(psranappr!=null){
                                psranappr.close();
                            }
                            if(psranapdrpr!=null){
                                psranapdrpr.close();
                            }
                        }
                   }

                   if(chkOperasi.isSelected()==true){
                        psbiayaasisten_operator1=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaasisten_operator1,"+
                            "count(operasi.kode_paket) as jml, " +
                            "sum(operasi.biayaasisten_operator1) as total "+
                            "from paket_operasi inner join operasi inner join reg_periksa "+
                            "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                            "where operasi.tgl_operasi between ? and ? and operasi.asisten_operator1=? "+
                            "and reg_periksa.kd_pj like ? and operasi.biayaasisten_operator1>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayaasisten_operator2=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaasisten_operator2,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayaasisten_operator2) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.asisten_operator2=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayaasisten_operator2>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayaasisten_operator3=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaasisten_operator3,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayaasisten_operator3) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.asisten_operator3=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayaasisten_operator3>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayainstrumen=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayainstrumen,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayainstrumen) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.instrumen=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayainstrumen>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayaperawaat_resusitas=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaperawaat_resusitas,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayaperawaat_resusitas) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.perawaat_resusitas=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayaperawaat_resusitas>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayaasisten_anestesi=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaasisten_anestesi,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayaasisten_anestesi) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.asisten_anestesi=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayaasisten_anestesi>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayaasisten_anestesi2=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaasisten_anestesi2,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayaasisten_anestesi2) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.asisten_anestesi2=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayaasisten_anestesi2>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayabidan=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayabidan,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayabidan) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.bidan=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayabidan>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayabidan2=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayabidan2,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayabidan2) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.bidan2=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayabidan2>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayabidan3=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayabidan3,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayabidan3) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.bidan3=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayabidan3>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psbiayaperawat_luar=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaperawat_luar,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biayaperawat_luar) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.perawat_luar=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biayaperawat_luar>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psomloop=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biaya_omloop,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biaya_omloop) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.omloop=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biaya_omloop>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psomloop2=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biaya_omloop2,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biaya_omloop2) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.omloop2=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biaya_omloop2>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                        psomloop3=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biaya_omloop3,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biaya_omloop3) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.omloop3=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biaya_omloop3>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  "); 
                        psomloop4=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biaya_omloop4,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biaya_omloop4) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.omloop4=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biaya_omloop4>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");  
                        psomloop5=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biaya_omloop5,"+
                           "count(operasi.kode_paket) as jml, " +
                           "sum(operasi.biaya_omloop5) as total "+
                           "from paket_operasi inner join operasi inner join reg_periksa "+
                           "on operasi.kode_paket=paket_operasi.kode_paket and operasi.no_rawat=reg_periksa.no_rawat "+
                           "where operasi.tgl_operasi between ? and ? and operasi.omloop5=? "+
                           "and reg_periksa.kd_pj like ? and operasi.biaya_omloop5>0 "+
                           "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");  
                        try {
                            psbiayaasisten_operator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaasisten_operator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaasisten_operator1.setString(3,rs.getString("nip"));
                            psbiayaasisten_operator1.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaasisten_operator1=psbiayaasisten_operator1.executeQuery();               

                            psbiayaasisten_operator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaasisten_operator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaasisten_operator2.setString(3,rs.getString("nip"));
                            psbiayaasisten_operator2.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaasisten_operator2=psbiayaasisten_operator2.executeQuery();  
                            
                            psbiayaasisten_operator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaasisten_operator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaasisten_operator3.setString(3,rs.getString("nip"));
                            psbiayaasisten_operator3.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaasisten_operator3=psbiayaasisten_operator3.executeQuery(); 

                            psbiayainstrumen.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayainstrumen.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayainstrumen.setString(3,rs.getString("nip"));
                            psbiayainstrumen.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayainstrumen=psbiayainstrumen.executeQuery();               

                            psbiayaperawaat_resusitas.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaperawaat_resusitas.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaperawaat_resusitas.setString(3,rs.getString("nip"));
                            psbiayaperawaat_resusitas.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaperawaat_resusitas=psbiayaperawaat_resusitas.executeQuery();               

                            psbiayaasisten_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaasisten_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaasisten_anestesi.setString(3,rs.getString("nip"));
                            psbiayaasisten_anestesi.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaasisten_anestesi=psbiayaasisten_anestesi.executeQuery();
                            
                            psbiayaasisten_anestesi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaasisten_anestesi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaasisten_anestesi2.setString(3,rs.getString("nip"));
                            psbiayaasisten_anestesi2.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaasisten_anestesi2=psbiayaasisten_anestesi2.executeQuery();

                            psbiayabidan.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayabidan.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayabidan.setString(3,rs.getString("nip"));
                            psbiayabidan.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayabidan=psbiayabidan.executeQuery();

                            psbiayabidan2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayabidan2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayabidan2.setString(3,rs.getString("nip"));
                            psbiayabidan2.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayabidan2=psbiayabidan2.executeQuery();

                            psbiayabidan3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayabidan3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayabidan3.setString(3,rs.getString("nip"));
                            psbiayabidan3.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayabidan3=psbiayabidan3.executeQuery();

                            psomloop.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop.setString(3,rs.getString("nip"));
                            psomloop.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop=psomloop.executeQuery();

                            psomloop2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop2.setString(3,rs.getString("nip"));
                            psomloop2.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop2=psomloop2.executeQuery();

                            psomloop3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop3.setString(3,rs.getString("nip"));
                            psomloop3.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop3=psomloop3.executeQuery();
                            
                            psomloop4.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop4.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop4.setString(3,rs.getString("nip"));
                            psomloop4.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop4=psomloop4.executeQuery();
                            
                            psomloop5.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop5.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop5.setString(3,rs.getString("nip"));
                            psomloop5.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop5=psomloop5.executeQuery();

                            psbiayaperawat_luar.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbiayaperawat_luar.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbiayaperawat_luar.setString(3,rs.getString("nip"));
                            psbiayaperawat_luar.setString(4,"%"+pilihancarabayar+"%");
                            rsbiayaperawat_luar=psbiayaperawat_luar.executeQuery();

                            if((rsbiayaasisten_operator1.next())||(rsbiayaasisten_operator2.next())||(rsbiayaasisten_operator3.next())
                                    ||(rsbiayainstrumen.next())||(rsbiayaperawaat_resusitas.next())||(rsbiayaasisten_anestesi.next())||(rsbiayaasisten_anestesi2.next())
                                    ||(rsbiayabidan.next())||(rsbiayabidan2.next())||(rsbiayabidan3.next())||(rsbiayaperawat_luar.next())
                                    ||(rsomloop.next())||(rsomloop2.next())||(rsomloop3.next())||(rsomloop4.next())||(rsomloop5.next())){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Operasi/VK ","","",""});   
                            } 

                            //asisten operator              
                            rsbiayaasisten_operator1.beforeFirst();
                            while(rsbiayaasisten_operator1.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayaasisten_operator1.getString("nm_perawatan")+" (Asisten Operator 1)",
                                    rsbiayaasisten_operator1.getString("jml"),Valid.SetAngka(rsbiayaasisten_operator1.getDouble("total"))
                                });       
                                jm=jm+rsbiayaasisten_operator1.getDouble("total");
                            }

                            //asisten anasthesi              
                            rsbiayaasisten_operator2.beforeFirst();
                            while(rsbiayaasisten_operator2.next()){                   
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayaasisten_operator2.getString("nm_perawatan")+" (Asisten Operator 2)",
                                    rsbiayaasisten_operator2.getString("jml"),Valid.SetAngka(rsbiayaasisten_operator2.getDouble("total"))
                                });   
                                jm=jm+rsbiayaasisten_operator2.getDouble("total");
                            }
                            
                            rsbiayaasisten_operator3.beforeFirst();
                            while(rsbiayaasisten_operator3.next()){                   
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayaasisten_operator3.getString("nm_perawatan")+" (Asisten Operator 3)",
                                    rsbiayaasisten_operator3.getString("jml"),Valid.SetAngka(rsbiayaasisten_operator3.getDouble("total"))
                                });   
                                jm=jm+rsbiayaasisten_operator3.getDouble("total");
                            }

                            //perawat luar              
                            rsbiayainstrumen.beforeFirst();
                            while(rsbiayainstrumen.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayainstrumen.getString("nm_perawatan")+" (Instrumen)",
                                    rsbiayainstrumen.getString("jml"),Valid.SetAngka(rsbiayainstrumen.getDouble("total"))
                                });             
                                jm=jm+rsbiayainstrumen.getDouble("total");
                            }

                             //perawat luar              
                            rsbiayaperawaat_resusitas.beforeFirst();
                            while(rsbiayaperawaat_resusitas.next()){                    
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayaperawaat_resusitas.getString("nm_perawatan")+" (Perawat Resusitas)",
                                    rsbiayaperawaat_resusitas.getString("jml"),Valid.SetAngka(rsbiayaperawaat_resusitas.getDouble("total"))
                                });        
                                jm=jm+rsbiayaperawaat_resusitas.getDouble("total");
                            }

                            //perawat luar              
                            rsbiayaasisten_anestesi.beforeFirst();
                            while(rsbiayaasisten_anestesi.next()){                   
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayaasisten_anestesi.getString("nm_perawatan")+" (Asisten Anestesi 1)",
                                    rsbiayaasisten_anestesi.getString("jml"),Valid.SetAngka(rsbiayaasisten_anestesi.getDouble("total"))
                                });       
                                jm=jm+rsbiayaasisten_anestesi.getDouble("total");
                            }
                            
                            rsbiayaasisten_anestesi2.beforeFirst();
                            while(rsbiayaasisten_anestesi2.next()){                   
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayaasisten_anestesi2.getString("nm_perawatan")+" (Asisten Anestesi 2)",
                                    rsbiayaasisten_anestesi2.getString("jml"),Valid.SetAngka(rsbiayaasisten_anestesi2.getDouble("total"))
                                });       
                                jm=jm+rsbiayaasisten_anestesi2.getDouble("total");
                            }

                            //bidan              
                            rsbiayabidan.beforeFirst();
                            while(rsbiayabidan.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayabidan.getString("nm_perawatan")+" (Bidan 1)",
                                    rsbiayabidan.getString("jml"),Valid.SetAngka(rsbiayabidan.getDouble("total"))
                                });       
                                jm=jm+rsbiayabidan.getDouble("total");
                            }

                            rsbiayabidan2.beforeFirst();
                            while(rsbiayabidan2.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayabidan2.getString("nm_perawatan")+" (Bidan 2)",
                                    rsbiayabidan2.getString("jml"),Valid.SetAngka(rsbiayabidan2.getDouble("total"))
                                });       
                                jm=jm+rsbiayabidan2.getDouble("total");
                            }

                            rsbiayabidan3.beforeFirst();
                            while(rsbiayabidan3.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbiayabidan3.getString("nm_perawatan")+" (Bidan 3)",
                                    rsbiayabidan3.getString("jml"),Valid.SetAngka(rsbiayabidan3.getDouble("total"))
                                });       
                                jm=jm+rsbiayabidan3.getDouble("total");
                            }

                            rsomloop.beforeFirst();
                            while(rsomloop.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop.getString("nm_perawatan")+" (Onloop 1)",
                                    rsomloop.getString("jml"),Valid.SetAngka(rsomloop.getDouble("total"))
                                });       
                                jm=jm+rsomloop.getDouble("total");
                            }

                            rsomloop2.beforeFirst();
                            while(rsomloop2.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop2.getString("nm_perawatan")+" (Onloop 2)",
                                    rsomloop2.getString("jml"),Valid.SetAngka(rsomloop2.getDouble("total"))
                                });       
                                jm=jm+rsomloop2.getDouble("total");
                            }

                            rsomloop3.beforeFirst();
                            while(rsomloop3.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop3.getString("nm_perawatan")+" (Onloop 3)",
                                    rsomloop3.getString("jml"),Valid.SetAngka(rsomloop3.getDouble("total"))
                                });       
                                jm=jm+rsomloop3.getDouble("total");
                            }
                            
                            rsomloop4.beforeFirst();
                            while(rsomloop4.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop4.getString("nm_perawatan")+" (Onloop 4)",
                                    rsomloop4.getString("jml"),Valid.SetAngka(rsomloop4.getDouble("total"))
                                });       
                                jm=jm+rsomloop4.getDouble("total");
                            }
                            
                            rsomloop5.beforeFirst();
                            while(rsomloop5.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop5.getString("nm_perawatan")+" (Onloop 5)",
                                    rsomloop5.getString("jml"),Valid.SetAngka(rsomloop5.getDouble("total"))
                                });       
                                jm=jm+rsomloop5.getDouble("total");
                            }

                            //perawat luar              
                            rsbiayaperawat_luar.beforeFirst();
                            while(rsbiayaperawat_luar.next()){
                                tabMode.addRow(new Object[]{
                                    "","",a+". "+rsbiayaperawat_luar.getString("nm_perawatan")+" (Perawat Luar)",
                                    rsbiayaperawat_luar.getString("jml"),Valid.SetAngka(rsbiayaperawat_luar.getDouble("total"))
                                });    
                                jm=jm+rsbiayaperawat_luar.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsbiayaasisten_operator1!=null){
                                rsbiayaasisten_operator1.close();
                            }
                            if(rsbiayaasisten_operator2!=null){
                                rsbiayaasisten_operator2.close();
                            }
                            if(rsbiayaasisten_operator3!=null){
                                rsbiayaasisten_operator3.close();
                            }
                            if(rsbiayainstrumen!=null){
                                rsbiayainstrumen.close();
                            }
                            if(rsbiayaperawaat_resusitas!=null){
                                rsbiayaperawaat_resusitas.close();
                            }
                            if(rsbiayaasisten_anestesi!=null){
                                rsbiayaasisten_anestesi.close();
                            }
                            if(rsbiayaasisten_anestesi2!=null){
                                rsbiayaasisten_anestesi2.close();
                            }
                            if(rsbiayabidan!=null){
                                rsbiayabidan.close();
                            }
                            if(rsbiayabidan2!=null){
                                rsbiayabidan2.close();
                            }
                            if(rsbiayabidan3!=null){
                                rsbiayabidan3.close();
                            }
                            if(rsomloop!=null){
                                rsomloop.close();
                            }
                            if(rsomloop2!=null){
                                rsomloop2.close();
                            }
                            if(rsomloop3!=null){
                                rsomloop3.close();
                            }
                            if(rsomloop4!=null){
                                rsomloop4.close();
                            }
                            if(rsomloop5!=null){
                                rsomloop5.close();
                            }
                            if(rsbiayaperawat_luar!=null){
                                rsbiayaperawat_luar.close();
                            }
                            if(psbiayaasisten_operator1!=null){
                                psbiayaasisten_operator1.close();
                            }
                            if(psbiayaasisten_operator2!=null){
                                psbiayaasisten_operator2.close();
                            }
                            if(psbiayaasisten_operator3!=null){
                                psbiayaasisten_operator3.close();
                            }
                            if(psbiayainstrumen!=null){
                                psbiayainstrumen.close();
                            }
                            if(psbiayaperawaat_resusitas!=null){
                                psbiayaperawaat_resusitas.close();
                            }
                            if(psbiayaasisten_anestesi!=null){
                                psbiayaasisten_anestesi.close();
                            }
                            if(psbiayaasisten_anestesi2!=null){
                                psbiayaasisten_anestesi2.close();
                            }
                            if(psbiayabidan!=null){
                                psbiayabidan.close();
                            }
                            if(psbiayabidan2!=null){
                                psbiayabidan2.close();
                            }
                            if(psbiayabidan3!=null){
                                psbiayabidan3.close();
                            }
                            if(psomloop!=null){
                                psomloop.close();
                            }
                            if(psomloop2!=null){
                                psomloop2.close();
                            }
                            if(psomloop3!=null){
                                psomloop3.close();
                            }
                            if(psomloop4!=null){
                                psomloop4.close();
                            }
                            if(psomloop5!=null){
                                psomloop5.close();
                            }
                            if(psbiayaperawat_luar!=null){
                                psbiayaperawat_luar.close();
                            }
                        }
                   }

                   if(chkLaborat.isSelected()==true){
                        psperiksalab=koneksi.prepareStatement("select jns_perawatan_lab.nm_perawatan,periksa_lab.tarif_tindakan_petugas,"+
                            "count(periksa_lab.kd_jenis_prw) as jml, " +
                            "sum(periksa_lab.tarif_tindakan_petugas) as total, "+
                            "periksa_lab.kd_jenis_prw from jns_perawatan_lab inner join periksa_lab inner join reg_periksa "+
                            "on periksa_lab.no_rawat=reg_periksa.no_rawat and periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where periksa_lab.tgl_periksa between ? and ? and periksa_lab.nip=? "+
                            "and reg_periksa.kd_pj like ? group by periksa_lab.kd_jenis_prw order by jns_perawatan_lab.nm_perawatan  ");
                        try {
                            psperiksalab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psperiksalab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psperiksalab.setString(3,rs.getString("nip"));
                            psperiksalab.setString(4,"%"+pilihancarabayar+"%");
                            rsperiksalab=psperiksalab.executeQuery();
                            if(rsperiksalab.next()){
                                a++;
                                 tabMode.addRow(new Object[]{"","",a+". Periksa Lab ","",""}); 
                            }
                            rsperiksalab.beforeFirst();
                            while(rsperiksalab.next()){ 
                                detaillab=0;
                                psdetaillab=koneksi.prepareStatement(
                                    "select sum(detail_periksa_lab.bagian_laborat) as total from "+
                                    "detail_periksa_lab inner join jns_perawatan_lab inner join "+
                                    "template_laboratorium inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                                    "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and "+
                                    "periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                                    "and periksa_lab.jam=detail_periksa_lab.jam and "+
                                    "jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and "+
                                    "template_laboratorium.id_template=detail_periksa_lab.id_template where  "+
                                    "detail_periksa_lab.tgl_periksa between ? and ? and detail_periksa_lab.kd_jenis_prw=? and periksa_lab.nip=?");
                                try {
                                    psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    psdetaillab.setString(3,rsperiksalab.getString("kd_jenis_prw"));
                                    psdetaillab.setString(4,rs.getString("nip"));
                                    rsdetaillab=psdetaillab.executeQuery();
                                    while(rsdetaillab.next()){
                                        detaillab=rsdetaillab.getDouble("total");
                                    }
                                    tabMode.addRow(new Object[]{
                                        "","","     "+rsperiksalab.getString("nm_perawatan"),
                                        rsperiksalab.getString("jml"),Valid.SetAngka(rsperiksalab.getDouble("total")+detaillab)
                                    });     
                                    jm=jm+rsperiksalab.getDouble("total")+detaillab;
                                } catch (Exception e) {
                                    System.out.println(e);
                                } finally{
                                    if(rsdetaillab!=null){
                                        rsdetaillab.close();
                                    }
                                    if(psdetaillab!=null){
                                        psdetaillab.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        } finally{
                            if(rsperiksalab!=null){
                                rsperiksalab.close();
                            }
                            if(psperiksalab!=null){
                                psperiksalab.close();
                            }
                        }
                            
                   }               

                   //pemeriksaan radiologi
                   if(chkRadiologi.isSelected()==true){
                        psperiksaradiologi=koneksi.prepareStatement("select jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tarif_tindakan_petugas,"+
                            "count(periksa_radiologi.kd_jenis_prw) as jml, " +
                            "sum(periksa_radiologi.tarif_tindakan_petugas) as total, "+
                            "periksa_radiologi.kd_jenis_prw from jns_perawatan_radiologi inner join periksa_radiologi inner join reg_periksa "+
                            "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw and periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                            "where periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.nip=? "+
                            "and reg_periksa.kd_pj like ? group by periksa_radiologi.kd_jenis_prw order by jns_perawatan_radiologi.nm_perawatan  ");
                        try {
                            psperiksaradiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psperiksaradiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psperiksaradiologi.setString(3,rs.getString("nip"));
                            psperiksaradiologi.setString(4,"%"+pilihancarabayar+"%");
                            rsperiksaradiologi=psperiksaradiologi.executeQuery();
                            if(rsperiksaradiologi.next()){
                                a++;
                                 tabMode.addRow(new Object[]{"","",a+". Periksa Radiologi","",""}); 
                            }
                            rsperiksaradiologi.beforeFirst();
                            while(rsperiksaradiologi.next()){ 
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsperiksaradiologi.getString("nm_perawatan"),
                                    rsperiksaradiologi.getString("jml"),Valid.SetAngka(rsperiksaradiologi.getDouble("total"))
                                });             
                                jm=jm+rsperiksaradiologi.getDouble("total");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsperiksaradiologi!=null){
                                rsperiksaradiologi.close();
                            }
                            if(psperiksaradiologi!=null){
                                psperiksaradiologi.close();
                            }
                        }              
                   }   
                   
                   if(jm>0){
                        tabMode.addRow(new Object[]{"","","Total : ","",Valid.SetAngka(jm)});
                   }

                   totaljm=totaljm+jm;   
                   i++;
                } 
           } catch (Exception e) {
               System.out.println("Notifikasi : "+e);
           } finally{
               if(rs!=null){
                   rs.close();
               }
               if(ps!=null){
                   ps.close();
               }
           }
              
            if(totaljm>0){
                   tabMode.addRow(new Object[]{">> ","Total Jasa Medis :"," ","",Valid.SetAngka(totaljm)});
            }             
        }catch(SQLException e){
            System.out.println("Catatan  "+e);
        }
        
    }
    
    public void isCek(){
       // BtnPrint.setEnabled(var.getbulanan_paramedis());
    }
    
}

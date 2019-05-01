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

public class DlgRHJmParamedis extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement ps,pstindakanprjalan,pstindakandrprjalan,psdetaillab,pstindakanprinap,
            pstindakandrprinap,psperiksa_lab,psasisten_operator1,psasisten_operator2,psasisten_operator3,
            psinstrumen,psperawaat_resusitas,psasisten_anestesi,psasisten_anestesi2,psbidan,psbidan2,psbidan3,
            psperawatluar,psperiksa_radiologi,psomloop,psomloop2,psomloop3,psomloop4,psomloop5;
    private ResultSet rs,rstindakanprjalan,rstindakandrprjalan,rstindakanprinap,rstindakandrprinap,
            rsdetaillab,rsperiksa_lab,rsasisten_operator1,rsasisten_operator2,rsasisten_operator3,
            rsinstrumen,rsperawaat_resusitas,rsasisten_anestesi,rsasisten_anestesi2,rsbidan,rsbidan2,rsbidan3,
            rsperawatluar,rsperiksa_radiologi,rsomloop,rsomloop2,rsomloop3,rsomloop4,rsomloop5;
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();  
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private int i=0,a=0;
    private double total=0,totaljm=0,detaillab=0;
    private String pilihancarabayar="";
    private DlgPenanggungJawab carabayar=new DlgPenanggungJawab(null,false);

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRHJmParamedis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Nama Paramedis","Tgl.Tindakan","Nama Pasien","Tindakan Medis","Jasa Medis"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(135);
            }else if(i==3){
                column.setPreferredWidth(250);
            }else if(i==4){
                column.setPreferredWidth(250);
            }else if(i==5){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdptg.setDocument(new batasInput((byte)5).getKata(kdptg));
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    prosesCari();
                }     
                kdptg.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {petugas.emptTeks();}
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
     * WARNING: Do NOT modify this code. The content of this method is
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
        kdptg = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnParamedis = new widget.Button();
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
        ppTampilkanSeleksi.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanSeleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanSeleksi.setText("Tampilkan Per Jenis Bayar");
        ppTampilkanSeleksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanSeleksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanSeleksi.setIconTextGap(5);
        ppTampilkanSeleksi.setName("ppTampilkanSeleksi"); // NOI18N
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(240, 26));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Harian Jasa Medis Paramedis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(70, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi4.add(kdptg);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmptg);

        btnParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnParamedis.setMnemonic('3');
        btnParamedis.setToolTipText("Alt+3");
        btnParamedis.setName("btnParamedis"); // NOI18N
        btnParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        btnParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParamedisActionPerformed(evt);
            }
        });
        btnParamedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnParamedisKeyPressed(evt);
            }
        });
        panelisi4.add(btnParamedis);

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
                                tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
            }
                        
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRHTindakanParamedis.jasper","report","::[ Rekap Harian Tindakan Paramedis ]::",param);
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

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());  
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());  
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnParamedisActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        //TCari.setText("");
        kdptg.setText("");
        nmptg.setText("");
        pilihancarabayar="";
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, kdptg,BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void btnParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParamedisActionPerformed
        petugas.isCek();       
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
}//GEN-LAST:event_btnParamedisActionPerformed

private void btnParamedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnParamedisKeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_btnParamedisKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    prosesCari();
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kdptg, BtnAll);
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
        Valid.pindah(evt, Tgl1,kdptg);
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
            DlgRHJmParamedis dialog = new DlgRHJmParamedis(new javax.swing.JFrame(), true);
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
    private widget.Button btnParamedis;
    private widget.CekBox chkLaborat;
    private widget.CekBox chkOperasi;
    private widget.CekBox chkRadiologi;
    private widget.CekBox chkRalan;
    private widget.CekBox chkRanap;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdptg;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.TextBox nmptg;
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
                ps.setString(1,"%"+kdptg.getText()+"%");
                rs=ps.executeQuery();
                i=1;
                totaljm=0;
                while(rs.next()){ 
                   total=0; 
                   a=0;
                   tabMode.addRow(new Object[]{i+".",rs.getString("nama"),"","","",""});   
                   //rawat jalan       
                   if(chkRalan.isSelected()==true){
                        pstindakanprjalan=koneksi.prepareStatement(
                            "select pasien.nm_pasien,rawat_jl_pr.tarif_tindakanpr,"+
                            "jns_perawatan.nm_perawatan,reg_periksa.tgl_registrasi,reg_periksa.kd_pj "+
                            "from pasien inner join reg_periksa  "+
                            "inner join jns_perawatan inner join rawat_jl_pr "+
                            "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.nip=? "+
                            "and reg_periksa.kd_pj like ? and rawat_jl_pr.tarif_tindakanpr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                        pstindakandrprjalan=koneksi.prepareStatement(
                            "select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakanpr,"+
                            "jns_perawatan.nm_perawatan,reg_periksa.tgl_registrasi,reg_periksa.kd_pj "+
                            "from pasien inner join reg_periksa  "+
                            "inner join jns_perawatan inner join rawat_jl_drpr "+
                            "on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.nip=? "+
                            "and reg_periksa.kd_pj like ? and rawat_jl_drpr.tarif_tindakanpr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                        try {
                            pstindakanprjalan.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pstindakanprjalan.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pstindakanprjalan.setString(3,rs.getString("nip"));
                            pstindakanprjalan.setString(4,"%"+pilihancarabayar+"%");
                            rstindakanprjalan=pstindakanprjalan.executeQuery();
                            rstindakanprjalan.last();
                            pstindakandrprjalan.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pstindakandrprjalan.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pstindakandrprjalan.setString(3,rs.getString("nip"));
                            pstindakandrprjalan.setString(4,"%"+pilihancarabayar+"%");
                            rstindakandrprjalan=pstindakandrprjalan.executeQuery();
                            rstindakandrprjalan.last();
                            if((rstindakanprjalan.getRow()>0)||(rstindakandrprjalan.getRow()>0)){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Rawat Jalan","","",""});   
                            }
                            rstindakanprjalan.beforeFirst();
                            while(rstindakanprjalan.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rstindakanprjalan.getString("tgl_registrasi"),rstindakanprjalan.getString("nm_pasien")+" ("+rstindakanprjalan.getString("kd_pj")+")",
                                    rstindakanprjalan.getString("nm_perawatan"),Valid.SetAngka(rstindakanprjalan.getDouble("tarif_tindakanpr"))
                                });     
                                total=total+rstindakanprjalan.getDouble("tarif_tindakanpr");
                            }
                            rstindakandrprjalan.beforeFirst();
                            while(rstindakandrprjalan.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rstindakandrprjalan.getString("tgl_registrasi"),rstindakandrprjalan.getString("nm_pasien")+" ("+rstindakandrprjalan.getString("kd_pj")+")",
                                    rstindakandrprjalan.getString("nm_perawatan"),Valid.SetAngka(rstindakandrprjalan.getDouble("tarif_tindakanpr"))
                                });     
                                total=total+rstindakandrprjalan.getDouble("tarif_tindakanpr");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rstindakanprjalan!=null){
                                rstindakanprjalan.close();
                            }
                            if(rstindakandrprjalan!=null){
                                rstindakandrprjalan.close();
                            }
                            if(pstindakanprjalan!=null){
                                pstindakanprjalan.close();
                            }
                            if(pstindakandrprjalan!=null){
                                pstindakandrprjalan.close();
                            }
                        }
                   }

                   if(chkRanap.isSelected()==true){
                        pstindakanprinap=koneksi.prepareStatement(
                             "select rawat_inap_pr.tarif_tindakanpr,pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,"+
                            "rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,reg_periksa.kd_pj " +
                            "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join rawat_inap_pr "+
                            "on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "and rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                            "where rawat_inap_pr.tgl_perawatan between ? and ? and rawat_inap_pr.nip=? "+
                            "and reg_periksa.kd_pj like ? and rawat_inap_pr.tarif_tindakanpr>0 order by rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                        pstindakandrprinap=koneksi.prepareStatement(
                            "select rawat_inap_drpr.tarif_tindakanpr,pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,"+
                            "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj " +
                            "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join rawat_inap_drpr "+
                            "on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "and rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                            "where rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.nip=? "+
                            "and reg_periksa.kd_pj like ? and rawat_inap_drpr.tarif_tindakanpr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                        try {
                            pstindakanprinap.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pstindakanprinap.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pstindakanprinap.setString(3,rs.getString("nip"));
                            pstindakanprinap.setString(4,"%"+pilihancarabayar+"%");
                            rstindakanprinap=pstindakanprinap.executeQuery();
                            rstindakanprinap.last();

                            pstindakandrprinap.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pstindakandrprinap.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pstindakandrprinap.setString(3,rs.getString("nip"));
                            pstindakandrprinap.setString(4,"%"+pilihancarabayar+"%");
                            rstindakandrprinap=pstindakandrprinap.executeQuery();
                            rstindakandrprinap.last();
                            if((rstindakandrprinap.getRow()>0)||(rstindakanprinap.getRow()>0)){
                                a++; 
                                tabMode.addRow(new Object[]{"","",a+". Rawat Inap","","",""});
                            }
                            rstindakanprinap.beforeFirst();
                            while(rstindakanprinap.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rstindakanprinap.getString("tgl_perawatan")+" "+rstindakanprinap.getString("jam_rawat"),rstindakanprinap.getString("nm_pasien")+" ("+rstindakanprinap.getString("kd_pj")+")",
                                    rstindakanprinap.getString("nm_perawatan"),Valid.SetAngka(rstindakanprinap.getDouble("tarif_tindakanpr"))
                                }); 
                                total=total+rstindakanprinap.getDouble("tarif_tindakanpr");
                            }
                            rstindakandrprinap.beforeFirst();
                            while(rstindakandrprinap.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rstindakandrprinap.getString("tgl_perawatan")+" "+rstindakandrprinap.getString("jam_rawat"),rstindakandrprinap.getString("nm_pasien")+" ("+rstindakandrprinap.getString("kd_pj")+")",
                                    rstindakandrprinap.getString("nm_perawatan"),Valid.SetAngka(rstindakandrprinap.getDouble("tarif_tindakanpr"))
                                }); 
                                total=total+rstindakandrprinap.getDouble("tarif_tindakanpr");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rstindakanprinap!=null){
                                rstindakanprinap.close();
                            }
                            if(rstindakandrprinap!=null){
                                rstindakandrprinap.close();
                            }
                            if(pstindakanprinap!=null){
                                pstindakanprinap.close();
                            }
                            if(pstindakandrprinap!=null){
                                pstindakandrprinap.close();
                            }
                        }
                   }
                   
                   if(chkOperasi.isSelected()==true){
                        psasisten_operator1=koneksi.prepareStatement("select operasi.biayaasisten_operator1,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.asisten_operator1=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayaasisten_operator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");  
                        psasisten_operator2=koneksi.prepareStatement("select operasi.biayaasisten_operator2,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.asisten_operator2=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayaasisten_operator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psasisten_operator3=koneksi.prepareStatement("select operasi.biayaasisten_operator3,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.asisten_operator3=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayaasisten_operator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psinstrumen=koneksi.prepareStatement("select operasi.biayainstrumen,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.instrumen=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayainstrumen>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psperawaat_resusitas=koneksi.prepareStatement("select operasi.biayaperawaat_resusitas,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.perawaat_resusitas=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayaperawaat_resusitas>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");   
                        psasisten_anestesi=koneksi.prepareStatement("select operasi.biayaasisten_anestesi,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.asisten_anestesi=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayaasisten_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psasisten_anestesi2=koneksi.prepareStatement("select operasi.biayaasisten_anestesi2,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.asisten_anestesi2=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayaasisten_anestesi2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psbidan=koneksi.prepareStatement("select operasi.biayabidan,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.bidan=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayabidan>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psbidan2=koneksi.prepareStatement("select operasi.biayabidan2,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.bidan2=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayabidan2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psbidan3=koneksi.prepareStatement("select operasi.biayabidan3,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.bidan3=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayabidan3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psperawatluar=koneksi.prepareStatement("select operasi.biayaperawat_luar,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.perawat_luar=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biayaperawat_luar>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psomloop=koneksi.prepareStatement("select operasi.biaya_omloop,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.omloop=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biaya_omloop>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psomloop2=koneksi.prepareStatement("select operasi.biaya_omloop2,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.omloop2=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biaya_omloop2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psomloop3=koneksi.prepareStatement("select operasi.biaya_omloop3,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.omloop3=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biaya_omloop3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psomloop4=koneksi.prepareStatement("select operasi.biaya_omloop4,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.omloop4=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biaya_omloop4>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        psomloop5=koneksi.prepareStatement("select operasi.biaya_omloop5,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                               "operasi.tgl_operasi,reg_periksa.kd_pj from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                               "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                               "where operasi.tgl_operasi between ? and ? and operasi.omloop5=? "+
                               "and reg_periksa.kd_pj like ? and operasi.biaya_omloop5>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                        try {
                            psasisten_operator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psasisten_operator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psasisten_operator1.setString(3,rs.getString("nip"));
                            psasisten_operator1.setString(4,"%"+pilihancarabayar+"%");
                            rsasisten_operator1=psasisten_operator1.executeQuery();
                            rsasisten_operator1.last();

                            psasisten_operator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psasisten_operator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psasisten_operator2.setString(3,rs.getString("nip"));
                            psasisten_operator2.setString(4,"%"+pilihancarabayar+"%");
                            rsasisten_operator2=psasisten_operator2.executeQuery();
                            rsasisten_operator2.last();
                            
                            psasisten_operator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psasisten_operator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psasisten_operator3.setString(3,rs.getString("nip"));
                            psasisten_operator3.setString(4,"%"+pilihancarabayar+"%");
                            rsasisten_operator3=psasisten_operator3.executeQuery();
                            rsasisten_operator3.last();

                            psinstrumen.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psinstrumen.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psinstrumen.setString(3,rs.getString("nip"));
                            psinstrumen.setString(4,"%"+pilihancarabayar+"%");
                            rsinstrumen=psinstrumen.executeQuery();
                            rsinstrumen.last();               

                            psperawaat_resusitas.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psperawaat_resusitas.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psperawaat_resusitas.setString(3,rs.getString("nip"));
                            psperawaat_resusitas.setString(4,"%"+pilihancarabayar+"%");
                            rsperawaat_resusitas=psperawaat_resusitas.executeQuery();
                            rsperawaat_resusitas.last();               

                            psasisten_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psasisten_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psasisten_anestesi.setString(3,rs.getString("nip"));
                            psasisten_anestesi.setString(4,"%"+pilihancarabayar+"%");
                            rsasisten_anestesi=psasisten_anestesi.executeQuery();
                            rsasisten_anestesi.last();

                            psasisten_anestesi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psasisten_anestesi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psasisten_anestesi2.setString(3,rs.getString("nip"));
                            psasisten_anestesi2.setString(4,"%"+pilihancarabayar+"%");
                            rsasisten_anestesi2=psasisten_anestesi2.executeQuery();
                            rsasisten_anestesi2.last();
                            
                            psbidan.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbidan.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbidan.setString(3,rs.getString("nip"));
                            psbidan.setString(4,"%"+pilihancarabayar+"%");
                            rsbidan=psbidan.executeQuery();
                            rsbidan.last();

                            psbidan2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbidan2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbidan2.setString(3,rs.getString("nip"));
                            psbidan2.setString(4,"%"+pilihancarabayar+"%");
                            rsbidan2=psbidan2.executeQuery();
                            rsbidan2.last();

                            psbidan3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psbidan3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psbidan3.setString(3,rs.getString("nip"));
                            psbidan3.setString(4,"%"+pilihancarabayar+"%");
                            rsbidan3=psbidan3.executeQuery();
                            rsbidan3.last();

                            psperawatluar.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psperawatluar.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psperawatluar.setString(3,rs.getString("nip"));
                            psperawatluar.setString(4,"%"+pilihancarabayar+"%");
                            rsperawatluar=psperawatluar.executeQuery();
                            rsperawatluar.last();

                            psomloop.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop.setString(3,rs.getString("nip"));
                            psomloop.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop=psomloop.executeQuery();
                            rsomloop.last();

                            psomloop2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop2.setString(3,rs.getString("nip"));
                            psomloop2.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop2=psomloop2.executeQuery();
                            rsomloop2.last();

                            psomloop3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop3.setString(3,rs.getString("nip"));
                            psomloop3.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop3=psomloop3.executeQuery();
                            rsomloop3.last();
                            
                            psomloop4.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop4.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop4.setString(3,rs.getString("nip"));
                            psomloop4.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop4=psomloop4.executeQuery();
                            rsomloop4.last();
                            
                            psomloop5.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            psomloop5.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            psomloop5.setString(3,rs.getString("nip"));
                            psomloop5.setString(4,"%"+pilihancarabayar+"%");
                            rsomloop5=psomloop5.executeQuery();
                            rsomloop5.last();

                            if((rsasisten_operator1.getRow()>0)||(rsasisten_operator2.getRow()>0)||(rsasisten_operator3.getRow()>0)||(rsinstrumen.getRow()>0)||(rsperawaat_resusitas.getRow()>0)
                                    ||(rsasisten_anestesi.getRow()>0)||(rsasisten_anestesi2.getRow()>0)||(rsbidan.getRow()>0)||(rsbidan2.getRow()>0)||(rsbidan3.getRow()>0)
                                    ||(rsperawatluar.getRow()>0)||(rsomloop.getRow()>0)||(rsomloop2.getRow()>0)||(rsomloop3.getRow()>0)||(rsomloop4.getRow()>0)||(rsomloop5.getRow()>0)){
                                a++; 
                                tabMode.addRow(new Object[]{"","",a+". Operasi/VK","","",""});   
                            }

                            //asisten operator              
                            rsasisten_operator1.beforeFirst();
                            while(rsasisten_operator1.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsasisten_operator1.getString("tgl_operasi"),rsasisten_operator1.getString("nm_pasien")+" ("+rsasisten_operator1.getString("kd_pj")+")",
                                    rsasisten_operator1.getString("nm_perawatan")+" (Asisten Operator 1)",Valid.SetAngka(rsasisten_operator1.getDouble("biayaasisten_operator1"))
                                });        
                                total=total+rsasisten_operator1.getDouble("biayaasisten_operator1");
                            }

                            //asisten anasthesi              
                            rsasisten_operator2.beforeFirst();
                            while(rsasisten_operator2.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsasisten_operator2.getString("tgl_operasi"),rsasisten_operator2.getString("nm_pasien")+" ("+rsasisten_operator2.getString("kd_pj")+")",
                                    rsasisten_operator2.getString("nm_perawatan")+" (Asisten Operator 2)",Valid.SetAngka(rsasisten_operator2.getDouble("biayaasisten_operator2"))
                                });  
                                total=total+rsasisten_operator2.getDouble("biayaasisten_operator2");
                            }
                            
                            //asisten anasthesi              
                            rsasisten_operator3.beforeFirst();
                            while(rsasisten_operator3.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsasisten_operator3.getString("tgl_operasi"),rsasisten_operator3.getString("nm_pasien")+" ("+rsasisten_operator3.getString("kd_pj")+")",
                                    rsasisten_operator3.getString("nm_perawatan")+" (Asisten Operator 3)",Valid.SetAngka(rsasisten_operator3.getDouble("biayaasisten_operator3"))
                                });  
                                total=total+rsasisten_operator3.getDouble("biayaasisten_operator3");
                            }

                            //perawat luar              
                            rsinstrumen.beforeFirst();
                            while(rsinstrumen.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsinstrumen.getString("tgl_operasi"),rsinstrumen.getString("nm_pasien")+" ("+rsinstrumen.getString("kd_pj")+")",
                                    rsinstrumen.getString("nm_perawatan")+" (Instrumen)",Valid.SetAngka(rsinstrumen.getDouble("biayainstrumen"))
                                });             
                                total=total+rsinstrumen.getDouble("biayainstrumen");
                            }

                            //perawat luar              
                            rsperawaat_resusitas.beforeFirst();
                            while(rsperawaat_resusitas.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsperawaat_resusitas.getString("tgl_operasi"),rsperawaat_resusitas.getString("nm_pasien")+" ("+rsperawaat_resusitas.getString("kd_pj")+")",
                                    rsperawaat_resusitas.getString("nm_perawatan")+" (Perawat Resusitas)",Valid.SetAngka(rsperawaat_resusitas.getDouble("biayaperawaat_resusitas"))
                                });          
                                total=total+rsperawaat_resusitas.getDouble("biayaperawaat_resusitas");
                            }

                            //perawat luar              
                            rsasisten_anestesi.beforeFirst();
                            while(rsasisten_anestesi.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsasisten_anestesi.getString("tgl_operasi"),rsasisten_anestesi.getString("nm_pasien")+" ("+rsasisten_anestesi.getString("kd_pj")+")",
                                    rsasisten_anestesi.getString("nm_perawatan")+" (Asisten Anestesi 1)",Valid.SetAngka(rsasisten_anestesi.getDouble("biayaasisten_anestesi"))
                                });      
                                total=total+rsasisten_anestesi.getDouble("biayaasisten_anestesi");
                            }
                            
                            //perawat luar              
                            rsasisten_anestesi2.beforeFirst();
                            while(rsasisten_anestesi2.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsasisten_anestesi2.getString("tgl_operasi"),rsasisten_anestesi2.getString("nm_pasien")+" ("+rsasisten_anestesi2.getString("kd_pj")+")",
                                    rsasisten_anestesi2.getString("nm_perawatan")+" (Asisten Anestesi 2)",Valid.SetAngka(rsasisten_anestesi2.getDouble("biayaasisten_anestesi2"))
                                });      
                                total=total+rsasisten_anestesi2.getDouble("biayaasisten_anestesi2");
                            }

                            //bidan              
                            rsbidan.beforeFirst();
                            while(rsbidan.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbidan.getString("tgl_operasi"),rsbidan.getString("nm_pasien")+" ("+rsbidan.getString("kd_pj")+")",
                                    rsbidan.getString("nm_perawatan")+" (Bidan 1)",Valid.SetAngka(rsbidan.getDouble("biayabidan"))
                                }); 
                                total=total+rsbidan.getDouble("biayabidan");
                            }

                            rsbidan2.beforeFirst();
                            while(rsbidan2.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbidan2.getString("tgl_operasi"),rsbidan2.getString("nm_pasien")+" ("+rsbidan2.getString("kd_pj")+")",
                                    rsbidan2.getString("nm_perawatan")+" (Bidan 2)",Valid.SetAngka(rsbidan2.getDouble("biayabidan2"))
                                }); 
                                total=total+rsbidan2.getDouble("biayabidan2");
                            }

                            rsbidan3.beforeFirst();
                            while(rsbidan3.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsbidan3.getString("tgl_operasi"),rsbidan3.getString("nm_pasien")+" ("+rsbidan3.getString("kd_pj")+")",
                                    rsbidan3.getString("nm_perawatan")+" (Bidan 3)",Valid.SetAngka(rsbidan3.getDouble("biayabidan3"))
                                }); 
                                total=total+rsbidan3.getDouble("biayabidan3");
                            }

                            rsomloop.beforeFirst();
                            while(rsomloop.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop.getString("tgl_operasi"),rsomloop.getString("nm_pasien")+" ("+rsomloop.getString("kd_pj")+")",
                                    rsomloop.getString("nm_perawatan")+" (Onloop 1)",Valid.SetAngka(rsomloop.getDouble("biaya_omloop"))
                                }); 
                                total=total+rsomloop.getDouble("biaya_omloop");
                            }

                            rsomloop2.beforeFirst();
                            while(rsomloop2.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop2.getString("tgl_operasi"),rsomloop2.getString("nm_pasien")+" ("+rsomloop2.getString("kd_pj")+")",
                                    rsomloop2.getString("nm_perawatan")+" (Onloop 2)",Valid.SetAngka(rsomloop2.getDouble("biaya_omloop2"))
                                }); 
                                total=total+rsomloop2.getDouble("biaya_omloop2");
                            }

                            rsomloop3.beforeFirst();
                            while(rsomloop3.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop3.getString("tgl_operasi"),rsomloop3.getString("nm_pasien")+" ("+rsomloop3.getString("kd_pj")+")",
                                    rsomloop3.getString("nm_perawatan")+" (Onloop 3)",Valid.SetAngka(rsomloop3.getDouble("biaya_omloop3"))
                                }); 
                                total=total+rsomloop3.getDouble("biaya_omloop3");
                            }
                            
                            rsomloop4.beforeFirst();
                            while(rsomloop4.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop4.getString("tgl_operasi"),rsomloop4.getString("nm_pasien")+" ("+rsomloop4.getString("kd_pj")+")",
                                    rsomloop4.getString("nm_perawatan")+" (Onloop 4)",Valid.SetAngka(rsomloop4.getDouble("biaya_omloop4"))
                                }); 
                                total=total+rsomloop4.getDouble("biaya_omloop4");
                            }
                            
                            rsomloop5.beforeFirst();
                            while(rsomloop5.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsomloop5.getString("tgl_operasi"),rsomloop5.getString("nm_pasien")+" ("+rsomloop5.getString("kd_pj")+")",
                                    rsomloop5.getString("nm_perawatan")+" (Onloop 5)",Valid.SetAngka(rsomloop5.getDouble("biaya_omloop5"))
                                }); 
                                total=total+rsomloop5.getDouble("biaya_omloop5");
                            }

                            rsperawatluar.beforeFirst();
                            while(rsperawatluar.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsperawatluar.getString("tgl_operasi"),rsperawatluar.getString("nm_pasien")+" ("+rsperawatluar.getString("kd_pj")+")",
                                    rsperawatluar.getString("nm_perawatan")+" (Perawat Luar)",Valid.SetAngka(rsperawatluar.getDouble("biayaperawat_luar"))
                                });             
                                total=total+rsperawatluar.getDouble("biayaperawat_luar");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsasisten_operator1!=null){
                                rsasisten_operator1.close();
                            }
                            if(rsasisten_operator2!=null){
                                rsasisten_operator2.close();
                            }
                            if(rsasisten_operator3!=null){
                                rsasisten_operator3.close();
                            }
                            if(rsinstrumen!=null){
                                rsinstrumen.close();
                            }
                            if(rsperawaat_resusitas!=null){
                                rsperawaat_resusitas.close();
                            }
                            if(rsasisten_anestesi!=null){
                                rsasisten_anestesi.close();
                            }
                            if(rsasisten_anestesi2!=null){
                                rsasisten_anestesi2.close();
                            }
                            if(rsbidan!=null){
                                rsbidan.close();
                            }
                            if(rsbidan2!=null){
                                rsbidan2.close();
                            }
                            if(rsbidan3!=null){
                                rsbidan3.close();
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
                            if(rsperawatluar!=null){
                                rsperawatluar.close();
                            }
                            if(psasisten_operator1!=null){
                                psasisten_operator1.close();
                            }
                            if(psasisten_operator2!=null){
                                psasisten_operator2.close();
                            }
                            if(psasisten_operator3!=null){
                                psasisten_operator3.close();
                            }
                            if(psinstrumen!=null){
                                psinstrumen.close();
                            }
                            if(psperawaat_resusitas!=null){
                                psperawaat_resusitas.close();
                            }
                            if(psasisten_anestesi!=null){
                                psasisten_anestesi.close();
                            }
                            if(psasisten_anestesi2!=null){
                                psasisten_anestesi2.close();
                            }
                            if(psbidan!=null){
                                psbidan.close();
                            }
                            if(psbidan2!=null){
                                psbidan2.close();
                            }
                            if(psbidan3!=null){
                                psbidan3.close();
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
                            if(psperawatluar!=null){
                                psperawatluar.close();
                            }
                        }
                   }
                   
                   //periksa lab
                   if(chkLaborat.isSelected()==true){   
                        psperiksa_lab=koneksi.prepareStatement("select periksa_lab.tarif_tindakan_petugas,pasien.nm_pasien,"+
                            "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                            " from periksa_lab inner join reg_periksa inner join pasien inner join petugas inner join jns_perawatan_lab "+
                            " on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            " and periksa_lab.nip=petugas.nip and periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            " where periksa_lab.tgl_periksa between ? and ? and periksa_lab.nip=? and reg_periksa.kd_pj like ? "+
                            "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");
                        try {
                            psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psperiksa_lab.setString(3,rs.getString("nip"));
                            psperiksa_lab.setString(4,"%"+pilihancarabayar+"%");
                            rsperiksa_lab=psperiksa_lab.executeQuery();
                            rsperiksa_lab.last();
                            if(rsperiksa_lab.getRow()>0){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Pemeriksaan Lab ","","",""});
                            }               
                            rsperiksa_lab.beforeFirst();
                            while(rsperiksa_lab.next()){
                                detaillab=0;
                                psdetaillab=koneksi.prepareStatement(
                                    "select sum(detail_periksa_lab.bagian_laborat) as total from detail_periksa_lab "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? "+
                                    "and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=?");
                                try {
                                    psdetaillab.setString(1,rsperiksa_lab.getString("no_rawat"));
                                    psdetaillab.setString(2,rsperiksa_lab.getString("kd_jenis_prw"));
                                    psdetaillab.setString(3,rsperiksa_lab.getString("tgl_periksa"));
                                    psdetaillab.setString(4,rsperiksa_lab.getString("jam"));
                                    rsdetaillab=psdetaillab.executeQuery();
                                    while(rsdetaillab.next()){
                                        detaillab=rsdetaillab.getDouble("total");
                                    }
                                    tabMode.addRow(new Object[]{
                                        "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                        rsperiksa_lab.getString("nm_perawatan"),Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_petugas")+detaillab)
                                    });      
                                    total=total+rsperiksa_lab.getDouble("tarif_tindakan_petugas")+detaillab;
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : "+e);
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
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsperiksa_lab!=null){
                                rsperiksa_lab.close();
                            }
                            if(psperiksa_lab!=null){
                                psperiksa_lab.close();
                            }
                        }
                   }               

                   //periksa radiologi
                   if(chkRadiologi.isSelected()==true){
                        psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_petugas,pasien.nm_pasien,"+
                            "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                            " from periksa_radiologi inner join reg_periksa inner join pasien inner join petugas inner join jns_perawatan_radiologi "+
                            " on periksa_radiologi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            " and periksa_radiologi.nip=petugas.nip and periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                            " where periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.nip=? and reg_periksa.kd_pj like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");
                        try {
                            psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psperiksa_radiologi.setString(3,rs.getString("nip"));
                            psperiksa_radiologi.setString(4,"%"+pilihancarabayar+"%");
                            rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                            rsperiksa_radiologi.last();
                            if(rsperiksa_radiologi.getRow()>0){
                                a++;
                                tabMode.addRow(new Object[]{"","",a+". Pemeriksaan radiologi ","","",""});
                            }               
                            rsperiksa_radiologi.beforeFirst();
                            while(rsperiksa_radiologi.next()){
                                tabMode.addRow(new Object[]{
                                    "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                    rsperiksa_radiologi.getString("nm_perawatan"),Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_petugas"))
                                });       
                                total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_petugas");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsperiksa_radiologi!=null){
                                rsperiksa_radiologi.close();
                            }
                            if(psperiksa_radiologi!=null){
                                psperiksa_radiologi.close();
                            }
                        }
                   }

                   if(total>0){
                      tabMode.addRow(new Object[]{"","","Total :","","",Valid.SetAngka(total)});                    
                   }              
                   i++;
                   totaljm=totaljm+total;
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
               tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","","","",Valid.SetAngka(totaljm)});     
            }
                        
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    public void isCek(){
        //BtnPrint.setEnabled(var.getharian_paramedis());
    }
    
}

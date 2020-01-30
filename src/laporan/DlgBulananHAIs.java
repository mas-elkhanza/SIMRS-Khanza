package laporan;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgPenanggungJawab;

public class DlgBulananHAIs extends javax.swing.JDialog {
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private int i=0,deku=0,urine=0,sputum=0,darah=0,antibiotik=0,pasien=0,jmlpasien=0,ETT=0,CVL=0,IVL=0,UC=0,VAP=0,IAD=0,PLEB=0,
                ISK=0,ILO=0,ANTIBIOTIK=0,jmlHAP,jmlTinea,jmlScabies,jmlETT,jmlCVL,jmlIVL,jmlUC,jmlVAP,jmlIAD,jmlPLEB,jmlISK,
                HAP,Tinea,Scabies,jmlILO,jmldeku,jmlsputum,jmldarah,jmlurine,jmlANTIBIOTIK;
    private StringBuilder htmlContent;
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgBulananHAIs(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML.setDocument(doc);
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    NmPenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                }      
                NmPenjab.requestFocus();
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){                          
                    NmKamar.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                    NmKamar.requestFocus();                           
                }                         
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        btnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        NmKamar = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label19 = new widget.Label();
        NmPenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Laporan Bulanan HAIs ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi1.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi1.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi1.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi1.add(Tgl2);

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        btnCari.setMnemonic('2');
        btnCari.setToolTipText("Alt+2");
        btnCari.setName("btnCari"); // NOI18N
        btnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        btnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariKeyPressed(evt);
            }
        });
        panelisi1.add(btnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
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
        panelisi1.add(BtnAll);

        label9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(80, 30));
        panelisi1.add(label9);

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

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll.setViewportView(LoadHTML);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label17.setText("Ruang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label17);
        label17.setBounds(6, 10, 50, 23);

        NmKamar.setEditable(false);
        NmKamar.setName("NmKamar"); // NOI18N
        NmKamar.setPreferredSize(new java.awt.Dimension(215, 23));
        panelisi4.add(NmKamar);
        NmKamar.setBounds(60, 10, 200, 23);

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
        BtnSeek2.setBounds(263, 10, 28, 23);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(label19);
        label19.setBounds(309, 10, 100, 23);

        NmPenjab.setEditable(false);
        NmPenjab.setName("NmPenjab"); // NOI18N
        NmPenjab.setPreferredSize(new java.awt.Dimension(215, 23));
        panelisi4.add(NmPenjab);
        NmPenjab.setBounds(413, 10, 200, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        panelisi4.add(BtnSeek3);
        BtnSeek3.setBounds(616, 10, 28, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

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
        try {
            
            File g = new File("file2.css");            
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
            );
            bg.close();
            
            File f = new File("BulananHAIs.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
            bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                            "<tr class='isi2'>"+
                                "<td valign='top' align='center'>"+
                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+       
                                "</td>"+
                           "</tr>"+
                        "</table>")
            );
            bw.close();                         
            Desktop.getDesktop().browse(f.toURI());
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
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

private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
    prosesCari();
}//GEN-LAST:event_btnCariActionPerformed

private void btnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            btnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, Tgl2, BtnPrint);
        }
}//GEN-LAST:event_btnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        NmKamar.setText("");
        NmPenjab.setText("");
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            NmKamar.setText("");
            NmPenjab.setText("");
            prosesCari();
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBulananHAIs dialog = new DlgBulananHAIs(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.TextBox Kd2;
    private widget.editorpane LoadHTML;
    private widget.TextBox NmKamar;
    private widget.TextBox NmPenjab;
    private widget.ScrollPane Scroll;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%' rowspan='2'>No.</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%' rowspan='2'>Tanggal</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='2'>Jml.Pasien</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22%' colspan='4'>Hari Pemasangan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%' colspan='8'>Infeksi</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%' rowspan='2'>Deku</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='17%' colspan='3'>Hasil Kultur</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='2'>Antibiotik</td>"+
                "</tr>"+
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>ETT</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>CVL</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>IVL</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>UC</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>VAP</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>IAD</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pleb</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>ISK</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>ILO</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>HAP</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tinea</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Scabies</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Sputum</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Darah</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Urine</td>"+
                "</tr>"
            );     
            ps=koneksi.prepareStatement(
                    "select data_HAIs.tanggal from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where tanggal between ? and ? and bangsal.nm_bangsal like ? and penjab.png_jawab like ? group by tanggal order by tanggal");
            try {
                i=1;
                jmlETT=0;jmlCVL=0;jmlIVL=0;jmlUC=0;jmlVAP=0;jmlIAD=0;jmlPLEB=0;
                jmlpasien=0;jmlISK=0;jmlILO=0;jmldeku=0;jmlsputum=0;jmldarah=0;
                jmlurine=0;jmlHAP=0;jmlTinea=0;jmlScabies=0;jmlANTIBIOTIK=0;                        
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+NmKamar.getText().trim()+"%");
                ps.setString(4,"%"+NmPenjab.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    pasien=Sequel.cariInteger("select count(data_HAIs.no_rawat) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ? group by data_HAIs.no_rawat",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlpasien=jmlpasien+pasien;
                    ETT=Sequel.cariInteger("select sum(data_HAIs.ETT) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlETT=jmlETT+ETT;
                    CVL=Sequel.cariInteger("select sum(data_HAIs.CVL) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlCVL=jmlCVL+CVL;
                    IVL=Sequel.cariInteger("select sum(data_HAIs.IVL) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlIVL=jmlIVL+IVL;
                    UC=Sequel.cariInteger("select sum(data_HAIs.UC) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlUC=jmlUC+UC;
                    VAP=Sequel.cariInteger("select sum(data_HAIs.VAP) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlVAP=jmlVAP+VAP;
                    IAD=Sequel.cariInteger("select sum(data_HAIs.IAD) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlIAD=jmlIAD+IAD;
                    PLEB=Sequel.cariInteger("select sum(data_HAIs.PLEB) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlPLEB=jmlPLEB+PLEB;
                    ISK=Sequel.cariInteger("select sum(data_HAIs.ISK) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlISK=jmlISK+ISK;
                    ILO=Sequel.cariInteger("select sum(data_HAIs.ILO) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlILO=jmlILO+ILO;
                    HAP=Sequel.cariInteger("select sum(data_HAIs.HAP) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlHAP=jmlHAP+HAP;
                    Tinea=Sequel.cariInteger("select sum(data_HAIs.Tinea) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlTinea=jmlTinea+Tinea;
                    Scabies=Sequel.cariInteger("select sum(data_HAIs.Scabies) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlScabies=jmlScabies+Scabies;
                    deku=Sequel.cariInteger("select count(data_HAIs.no_rawat) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.DEKU='IYA' and tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmldeku=jmldeku+deku;
                    sputum=Sequel.cariInteger("select count(data_HAIs.no_rawat) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.SPUTUM<>'' and tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlsputum=jmlsputum+sputum;
                    darah=Sequel.cariInteger("select count(data_HAIs.no_rawat) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.DARAH<>'' and tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmldarah=jmldarah+darah;
                    urine=Sequel.cariInteger("select count(data_HAIs.no_rawat) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.URINE<>'' and tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlurine=jmlurine+urine;
                    ANTIBIOTIK=Sequel.cariInteger("select count(data_HAIs.no_rawat) from data_HAIs inner join reg_periksa inner join kamar inner join bangsal inner join penjab on data_HAIs.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj where data_HAIs.ANTIBIOTIK<>'' and tanggal=? and bangsal.nm_bangsal like ? and penjab.png_jawab like ?",rs.getString("tanggal"),"%"+NmKamar.getText().trim()+"%","%"+NmPenjab.getText().trim()+"%");
                    jmlANTIBIOTIK=jmlANTIBIOTIK+ANTIBIOTIK;
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'>"+i+"</td>"+
                            "<td valign='middle' align='center'>"+rs.getString("tanggal")+"</td>"+
                            "<td valign='middle' align='center'>"+pasien+"</td>"+
                            "<td valign='middle' align='center'>"+ETT+"</td>"+
                            "<td valign='middle' align='center'>"+CVL+"</td>"+
                            "<td valign='middle' align='center'>"+IVL+"</td>"+
                            "<td valign='middle' align='center'>"+UC+"</td>"+
                            "<td valign='middle' align='center'>"+VAP+"</td>"+
                            "<td valign='middle' align='center'>"+IAD+"</td>"+
                            "<td valign='middle' align='center'>"+PLEB+"</td>"+
                            "<td valign='middle' align='center'>"+ISK+"</td>"+
                            "<td valign='middle' align='center'>"+ILO+"</td>"+
                            "<td valign='middle' align='center'>"+HAP+"</td>"+
                            "<td valign='middle' align='center'>"+Tinea+"</td>"+
                            "<td valign='middle' align='center'>"+Scabies+"</td>"+
                            "<td valign='middle' align='center'>"+deku+"</td>"+
                            "<td valign='middle' align='center'>"+sputum+"</td>"+
                            "<td valign='middle' align='center'>"+darah+"</td>"+
                            "<td valign='middle' align='center'>"+urine+"</td>"+
                            "<td valign='middle' align='center'>"+ANTIBIOTIK+"</td>"+
                        "</tr>"
                    ); 
                    i++;
                }
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' align='right' colspan='2'>Total :</td>"+
                        "<td valign='middle' align='center'>"+jmlpasien+"</td>"+
                        "<td valign='middle' align='center'>"+jmlETT+"</td>"+
                        "<td valign='middle' align='center'>"+jmlCVL+"</td>"+
                        "<td valign='middle' align='center'>"+jmlIVL+"</td>"+
                        "<td valign='middle' align='center'>"+jmlUC+"</td>"+
                        "<td valign='middle' align='center'>"+jmlVAP+"</td>"+
                        "<td valign='middle' align='center'>"+jmlIAD+"</td>"+
                        "<td valign='middle' align='center'>"+jmlPLEB+"</td>"+
                        "<td valign='middle' align='center'>"+jmlISK+"</td>"+
                        "<td valign='middle' align='center'>"+jmlILO+"</td>"+
                        "<td valign='middle' align='center'>"+jmlHAP+"</td>"+
                        "<td valign='middle' align='center'>"+jmlTinea+"</td>"+
                        "<td valign='middle' align='center'>"+jmlScabies+"</td>"+
                        "<td valign='middle' align='center'>"+jmldeku+"</td>"+
                        "<td valign='middle' align='center'>"+jmlsputum+"</td>"+
                        "<td valign='middle' align='center'>"+jmldarah+"</td>"+
                        "<td valign='middle' align='center'>"+jmlurine+"</td>"+
                        "<td valign='middle' align='center'>"+jmlANTIBIOTIK+"</td>"+
                    "</tr>"
                ); 
            } catch (Exception e) {
                System.out.println("laporan.DlgHarianHAIs.prosesCari() : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("laporan.DlgRL4A.prosesCari() 5 : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getbulanan_HAIs());
    }
    
}

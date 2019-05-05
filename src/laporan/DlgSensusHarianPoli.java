/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package laporan;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
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
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgPenanggungJawab;

/**
 *
 * @author perpustakaan
 */
public final class DlgSensusHarianPoli extends javax.swing.JDialog {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private PreparedStatement pstanggal,pspoli,psreg,pspenyakit;
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private ResultSet rstanggal,rspoli,rsreg,rspenyakit;
    private int i=0,jmllama=0,jmlbaru=0,jmllaki=0,jmlper=0;
    private String lama="",baru="",rujukandari="",alamatrujukandari="",dirujukke="";
    private StringBuilder htmlContent;
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgSensusHarianPoli(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        
        if(koneksiDB.cariCepat().equals("aktif")){
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){
                    kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    nmpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                }      
                kdpoli.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {poli.emptTeks();}
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
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
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
        
        LoadHTML.setEditable(true);
        LoadHTML2.setEditable(true);
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#464646;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#464646;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#464646;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#464646;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML2.setDocument(doc);
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel8 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label19 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Sensus Harian Pasien Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass5.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(155, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

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
        panelGlass5.add(BtnCari);

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
        panelGlass5.add(BtnAll);

        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(jLabel8);

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
        panelGlass5.add(BtnPrint);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Poli :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label17);

        kdpoli.setEditable(false);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.setPreferredSize(new java.awt.Dimension(75, 23));
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        panelisi4.add(kdpoli);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setPreferredSize(new java.awt.Dimension(215, 23));
        panelisi4.add(nmpoli);

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

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(label19);

        kdpenjab.setEditable(false);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(75, 23));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        panelisi4.add(kdpenjab);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(215, 23));
        panelisi4.add(nmpenjab);

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

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll.setViewportView(LoadHTML);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Seluruh Data Registrasi", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll1.setViewportView(LoadHTML2);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Data Registrasi Non Batal", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            
            File g = new File("file2.css");            
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#464646;}"+
                    ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#464646;}"+                    
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#464646;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#464646;}"
            );
            bg.close();
            
            File f = new File("sensuspoli.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            if(TabRawat.getSelectedIndex()==0){
                bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>SENSUS HARIAN PASIEN POLIKLINIK<br>PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();
            }else if(TabRawat.getSelectedIndex()==1){
                bw.write(LoadHTML2.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>SENSUS HARIAN PASIEN POLIKLINIK<br>PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();
            }
                
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
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpoliKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

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

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TKd, BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdpoli.setText("");
        nmpoli.setText("");
        kdpenjab.setText("");
        nmpenjab.setText("");
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSensusHarianPoli dialog = new DlgSensusHarianPoli(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSeek3;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel6;
    private widget.Label jLabel8;
    private widget.TextBox kdpenjab;
    private widget.TextBox kdpoli;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelisi4;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            jmllama=0;jmlbaru=0;jmllaki=0;jmlper=0;
            htmlContent = new StringBuilder();
            pstanggal=koneksi.prepareStatement("select tgl_registrasi,DATE_FORMAT(tgl_registrasi,'%d-%m-%Y') as tanggal from reg_periksa where tgl_registrasi between ? and ? group by tgl_registrasi order by tgl_registrasi ");
            try {
                pstanggal.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                pstanggal.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                rstanggal=pstanggal.executeQuery();
                while(rstanggal.next()){
                    pspoli=koneksi.prepareStatement(
                            "select reg_periksa.kd_poli,poliklinik.nm_poli from reg_periksa "+
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                            "where tgl_registrasi=? and poliklinik.nm_poli like ? group by reg_periksa.kd_poli");
                    try {
                        pspoli.setString(1,rstanggal.getString("tgl_registrasi"));
                        pspoli.setString(2,"%"+nmpoli.getText().trim()+"%");
                        rspoli=pspoli.executeQuery();
                        while(rspoli.next()){
                            htmlContent.append(
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>&nbsp;</td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                "</tr>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' colspan='2'>Poliklinik</td><td valign='top' colspan='10'>: "+rspoli.getString("nm_poli")+"</td>"+
                                "</tr>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' colspan='2'>Tanggal</td><td valign='top' colspan='10'>: "+rstanggal.getString("tanggal")+"</td>"+
                                "</tr>"+
                                "<tr class='isi3'>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='3%' rowspan='2'>No.</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='6%' rowspan='2'>No.RM</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='13%' rowspan='2'>Nama Pasien</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='13%' rowspan='2'>Alamat</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='3%' rowspan='2'>L/P</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='3%' rowspan='2'>Umur</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='6%' colspan='2'>Pengunjung</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='10%' rowspan='2'>Cara Pembayaran</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='14%' rowspan='2'>Asal Rujukan &<br>Alamatnya</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='14%' rowspan='2'>Golongan Penyakit/<br>Sebab Penyakit</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='10%' rowspan='2'>Dirujuk Ke</td>"+
                                "</tr>"+
                                "<tr class='isi3'>"+                                        
                                    "<td valign='top' bgcolor='#fafff5' align='center' width='3%'>Lama</td>"+
                                    "<td valign='top' bgcolor='#fafff5' align='center' width='3%'>Baru</td>"+
                                "</tr>" 
                            );
                            psreg=koneksi.prepareStatement(
                                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.alamat,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,"+
                                    "reg_periksa.stts_daftar,penjab.png_jawab,reg_periksa.no_rawat from reg_periksa inner join pasien inner join penjab "+
                                    "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj where "+
                                    "reg_periksa.tgl_registrasi=? and reg_periksa.kd_poli=? and penjab.png_jawab like ? and reg_periksa.no_rkm_medis like ? or "+
                                    "reg_periksa.tgl_registrasi=? and reg_periksa.kd_poli=? and penjab.png_jawab like ? and pasien.nm_pasien like ? or "+
                                    "reg_periksa.tgl_registrasi=? and reg_periksa.kd_poli=? and penjab.png_jawab like ? and pasien.alamat like ? or "+
                                    "reg_periksa.tgl_registrasi=? and reg_periksa.kd_poli=? and penjab.png_jawab like ? and reg_periksa.stts_daftar like ? order by reg_periksa.no_reg ");
                            try {
                                i=1;
                                psreg.setString(1,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(2,rspoli.getString("kd_poli"));
                                psreg.setString(3,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(4,"%"+TCari.getText().trim()+"%");
                                psreg.setString(5,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(6,rspoli.getString("kd_poli"));
                                psreg.setString(7,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(8,"%"+TCari.getText().trim()+"%");
                                psreg.setString(9,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(10,rspoli.getString("kd_poli"));
                                psreg.setString(11,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(12,"%"+TCari.getText().trim()+"%");
                                psreg.setString(13,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(14,rspoli.getString("kd_poli"));
                                psreg.setString(15,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(16,"%"+TCari.getText().trim()+"%");
                                rsreg=psreg.executeQuery();
                                while(rsreg.next()){
                                    lama="";baru="";
                                    dirujukke=Sequel.cariIsi("select rujuk_ke from rujuk where no_rawat=?",rsreg.getString("no_rawat"));
                                    rujukandari=Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?",rsreg.getString("no_rawat"));
                                    alamatrujukandari=Sequel.cariIsi("select alamat from rujuk_masuk where no_rawat=?",rsreg.getString("no_rawat"));
                                    if(rsreg.getString("stts_daftar").equals("Baru")){
                                        baru="V";
                                        jmlbaru=jmlbaru+1;
                                    }else if(rsreg.getString("stts_daftar").equals("Lama")){
                                        lama="V";
                                        jmllama=jmllama+1;
                                    }
                                    if(rsreg.getString("jk").equals("L")){
                                        jmllaki=jmllaki+1;
                                    }else{
                                        jmlper=jmlper+1;
                                    }
                                    htmlContent.append(
                                        "<tr class='isi3'>"+
                                            "<td valign='top' align='center'>"+i+"</td>"+
                                            "<td valign='top' align='center'>"+rsreg.getString("no_rkm_medis")+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("nm_pasien")+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("alamat")+"</td>"+
                                            "<td valign='top' align='center'>"+rsreg.getString("jk")+"</td>"+
                                            "<td valign='top' align='center'>"+rsreg.getString("umur")+"</td>"+
                                            "<td valign='top' align='center'>"+lama+"</td>"+
                                            "<td valign='top' align='center'>"+baru+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("png_jawab")+"</td>"+
                                            "<td valign='top'>"+rujukandari+" "+alamatrujukandari+"</td>"+
                                            "<td valign='top'>"+
                                                "<table width='100%' border='0'>");
                                    pspenyakit=koneksi.prepareStatement(
                                            "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.status='Ralan' and diagnosa_pasien.no_rawat=?");
                                    try {
                                        pspenyakit.setString(1,rsreg.getString("no_rawat"));
                                        rspenyakit=pspenyakit.executeQuery();
                                        while(rspenyakit.next()){
                                            htmlContent.append("<tr class='isi4'><td width='25%'>"+rspenyakit.getString("kd_penyakit")+"</td><td width='75%'>"+rspenyakit.getString("nm_penyakit")+"</td></tr>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Note : "+e);
                                    } finally{
                                        if(rspenyakit!=null){
                                            rspenyakit.close();
                                        }
                                        if(pspenyakit!=null){
                                            pspenyakit.close();
                                        }
                                    }                                        
                                    htmlContent.append(
                                                "</table>"+
                                            "</td>"+
                                            "<td valign='top'>"+dirujukke+"</td>"+
                                        "</tr>"
                                    );        
                                    i++;
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Registrasi : "+e);
                            } finally{
                                if(rsreg!=null){
                                    rsreg.close();
                                }
                                if(psreg!=null){
                                    psreg.close();
                                }
                            }
                            htmlContent.append(
                                "<tr class='isi4'>"+
                                    "<td valign='top' align='center'>&nbsp;</td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                "</tr>"
                            ); 
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Poli : "+e);
                    } finally{
                        if(rspoli!=null){
                            rspoli.close();
                        }
                        if(pspoli!=null){
                            pspoli.close();
                        }
                    }                                        
                }
                if((jmlbaru+jmllama)>0){
                    htmlContent.append(
                        "<tr class='isi3'>"+
                            "<td valign='top' align='left' colspan='2'>Baru</td>"+
                            "<td valign='top' align='left' colspan='10'>: "+jmlbaru+"</td>"+
                        "</tr>"+
                        "<tr class='isi3'>"+
                            "<td valign='top' align='left' colspan='2'>Lama</td>"+
                            "<td valign='top' align='left' colspan='10'>: "+jmllama+"</td>"+
                        "</tr>"+
                        "<tr class='isi3'>"+
                            "<td valign='top' align='left' colspan='2'>Laki-Laki</td>"+
                            "<td valign='top' align='left' colspan='10'>: "+jmllaki+"</td>"+
                        "</tr>"+
                        "<tr class='isi3'>"+
                            "<td valign='top' align='left' colspan='2'>Perempuan</td>"+
                            "<td valign='top' align='left' colspan='10'>: "+jmlper+"</td>"+
                        "</tr>"+
                        "<tr class='isi3'>"+
                            "<td valign='top' align='left' colspan='2'>Total</td>"+
                            "<td valign='top' align='left' colspan='10'>: "+(jmllama+jmlbaru)+"</td>"+
                        "</tr>"
                     );
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Cari Tanggal : "+e);
            } finally{
                if(rstanggal!=null){
                    rstanggal.close();
                }
                if(pstanggal!=null){
                    pstanggal.close();
                }
            }
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    public void tampil2(){        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            jmllama=0;jmlbaru=0;jmllaki=0;jmlper=0;
            htmlContent = new StringBuilder();
            pstanggal=koneksi.prepareStatement("select tgl_registrasi,DATE_FORMAT(tgl_registrasi,'%d-%m-%Y') as tanggal from reg_periksa where stts<>'Batal' and tgl_registrasi between ? and ? group by tgl_registrasi order by tgl_registrasi ");
            try {
                pstanggal.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                pstanggal.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                rstanggal=pstanggal.executeQuery();
                while(rstanggal.next()){
                    pspoli=koneksi.prepareStatement(
                            "select reg_periksa.kd_poli,poliklinik.nm_poli from reg_periksa "+
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                            "where reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi=? and poliklinik.nm_poli like ? group by reg_periksa.kd_poli");
                    try {
                        pspoli.setString(1,rstanggal.getString("tgl_registrasi"));
                        pspoli.setString(2,"%"+nmpoli.getText().trim()+"%");
                        rspoli=pspoli.executeQuery();
                        while(rspoli.next()){
                            htmlContent.append(
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>&nbsp;</td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                "</tr>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' colspan='2'>Poliklinik</td><td valign='top' colspan='10'>: "+rspoli.getString("nm_poli")+"</td>"+
                                "</tr>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' colspan='2'>Tanggal</td><td valign='top' colspan='10'>: "+rstanggal.getString("tanggal")+"</td>"+
                                "</tr>"+
                                "<tr class='isi3'>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='3%' rowspan='2'>No.</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='6%' rowspan='2'>No.RM</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='13%' rowspan='2'>Nama Pasien</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='13%' rowspan='2'>Alamat</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='3%' rowspan='2'>L/P</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='3%' rowspan='2'>Umur</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='6%' colspan='2'>Pengunjung</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='10%' rowspan='2'>Cara Pembayaran</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='14%' rowspan='2'>Asal Rujukan &<br>Alamatnya</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='19%' rowspan='2'>Golongan Penyakit/<br>Sebab Penyakit</td>"+
                                    "<td valign='middle' bgcolor='#fafff5' align='center' width='10%' rowspan='2'>Dirujuk Ke</td>"+
                                "</tr>"+
                                "<tr class='isi3'>"+                                        
                                    "<td valign='top' bgcolor='#fafff5' align='center' width='3%'>Lama</td>"+
                                    "<td valign='top' bgcolor='#fafff5' align='center' width='3%'>Baru</td>"+
                                "</tr>" 
                            );
                            psreg=koneksi.prepareStatement(
                                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.alamat,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,"+
                                    "reg_periksa.stts_daftar,penjab.png_jawab,reg_periksa.no_rawat from reg_periksa inner join pasien inner join penjab "+
                                    "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj where "+
                                    "reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi=? and reg_periksa.kd_poli=? and penjab.png_jawab like ? and reg_periksa.no_rkm_medis like ? or "+
                                    "reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi=? and reg_periksa.kd_poli=? and penjab.png_jawab like ? and pasien.nm_pasien like ? or "+
                                    "reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi=? and reg_periksa.kd_poli=? and penjab.png_jawab like ? and pasien.alamat like ? or "+
                                    "reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi=? and reg_periksa.kd_poli=? and penjab.png_jawab like ? and reg_periksa.stts_daftar like ? order by reg_periksa.no_reg ");
                            try {
                                i=1;
                                psreg.setString(1,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(2,rspoli.getString("kd_poli"));
                                psreg.setString(3,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(4,"%"+TCari.getText().trim()+"%");
                                psreg.setString(5,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(6,rspoli.getString("kd_poli"));
                                psreg.setString(7,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(8,"%"+TCari.getText().trim()+"%");
                                psreg.setString(9,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(10,rspoli.getString("kd_poli"));
                                psreg.setString(11,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(12,"%"+TCari.getText().trim()+"%");
                                psreg.setString(13,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(14,rspoli.getString("kd_poli"));
                                psreg.setString(15,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(16,"%"+TCari.getText().trim()+"%");
                                rsreg=psreg.executeQuery();
                                while(rsreg.next()){
                                    lama="";baru="";
                                    dirujukke=Sequel.cariIsi("select rujuk_ke from rujuk where no_rawat=?",rsreg.getString("no_rawat"));
                                    rujukandari=Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?",rsreg.getString("no_rawat"));
                                    alamatrujukandari=Sequel.cariIsi("select alamat from rujuk_masuk where no_rawat=?",rsreg.getString("no_rawat"));
                                    if(rsreg.getString("stts_daftar").equals("Baru")){
                                        baru="V";
                                        jmlbaru=jmlbaru+1;
                                    }else if(rsreg.getString("stts_daftar").equals("Lama")){
                                        lama="V";
                                        jmllama=jmllama+1;
                                    }
                                    if(rsreg.getString("jk").equals("L")){
                                        jmllaki=jmllaki+1;
                                    }else{
                                        jmlper=jmlper+1;
                                    }
                                    htmlContent.append(
                                        "<tr class='isi3'>"+
                                            "<td valign='top' align='center'>"+i+"</td>"+
                                            "<td valign='top' align='center'>"+rsreg.getString("no_rkm_medis")+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("nm_pasien")+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("alamat")+"</td>"+
                                            "<td valign='top' align='center'>"+rsreg.getString("jk")+"</td>"+
                                            "<td valign='top' align='center'>"+rsreg.getString("umur")+"</td>"+
                                            "<td valign='top' align='center'>"+lama+"</td>"+
                                            "<td valign='top' align='center'>"+baru+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("png_jawab")+"</td>"+
                                            "<td valign='top'>"+rujukandari+" "+alamatrujukandari+"</td>"+
                                            "<td valign='top'>"+
                                                "<table width='100%' border='0'>");
                                    pspenyakit=koneksi.prepareStatement(
                                            "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.status='Ralan' and diagnosa_pasien.no_rawat=?");
                                    try {
                                        pspenyakit.setString(1,rsreg.getString("no_rawat"));
                                        rspenyakit=pspenyakit.executeQuery();
                                        while(rspenyakit.next()){
                                            htmlContent.append("<tr class='isi4'><td width='25%'>"+rspenyakit.getString("kd_penyakit")+"</td><td width='75%'>"+rspenyakit.getString("nm_penyakit")+"</td></tr>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Note : "+e);
                                    } finally{
                                        if(rspenyakit!=null){
                                            rspenyakit.close();
                                        }
                                        if(pspenyakit!=null){
                                            pspenyakit.close();
                                        }
                                    }                                        
                                    htmlContent.append(
                                                "</table>"+
                                            "</td>"+
                                            "<td valign='top'>"+dirujukke+"</td>"+
                                        "</tr>"
                                    );         
                                    i++;
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Registrasi : "+e);
                            } finally{
                                if(rsreg!=null){
                                    rsreg.close();
                                }
                                if(psreg!=null){
                                    psreg.close();
                                }
                            }
                            htmlContent.append(
                                "<tr class='isi4'>"+
                                    "<td valign='top' align='center'>&nbsp;</td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' align='center'></td>"+
                                "</tr>"
                            );    
                            
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Poli : "+e);
                    } finally{
                        if(rspoli!=null){
                            rspoli.close();
                        }
                        if(pspoli!=null){
                            pspoli.close();
                        }
                    }                                        
                }
                if((jmlbaru+jmllama)>0){
                    htmlContent.append(
                                "<tr class='isi3'>"+
                                    "<td valign='top' align='left' colspan='2'>Baru</td>"+
                                    "<td valign='top' align='left' colspan='10'>: "+jmlbaru+"</td>"+
                                "</tr>"+
                                "<tr class='isi3'>"+
                                    "<td valign='top' align='left' colspan='2'>Lama</td>"+
                                    "<td valign='top' align='left' colspan='10'>: "+jmllama+"</td>"+
                                "</tr>"+
                                "<tr class='isi3'>"+
                                    "<td valign='top' align='left' colspan='2'>Laki-Laki</td>"+
                                    "<td valign='top' align='left' colspan='10'>: "+jmllaki+"</td>"+
                                "</tr>"+
                                "<tr class='isi3'>"+
                                    "<td valign='top' align='left' colspan='2'>Perempuan</td>"+
                                    "<td valign='top' align='left' colspan='10'>: "+jmlper+"</td>"+
                                "</tr>"+
                                "<tr class='isi3'>"+
                                    "<td valign='top' align='left' colspan='2'>Total</td>"+
                                    "<td valign='top' align='left' colspan='10'>: "+(jmllama+jmlbaru)+"</td>"+
                                "</tr>"
                     );
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Cari Tanggal : "+e);
            } finally{
                if(rstanggal!=null){
                    rstanggal.close();
                }
                if(pstanggal!=null){
                    pstanggal.close();
                }
            }
            LoadHTML2.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

}

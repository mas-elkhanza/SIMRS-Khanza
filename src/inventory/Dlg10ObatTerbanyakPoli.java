/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package inventory;

import fungsi.akses;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
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
import java.sql.SQLException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgPenanggungJawab;

/**
 *
 * @author perpustakaan
 */
public final class Dlg10ObatTerbanyakPoli extends javax.swing.JDialog {
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement pspoli,psobat,pspenjab;
    private ResultSet rspoli,rsobat,rspenjab;
    private int i=0,a=0,c;   
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private StringBuilder htmlContent;
    
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public Dlg10ObatTerbanyakPoli(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML1.setEditable(true);
        LoadHTML1.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
            ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
            ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
            ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
            ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML1.setDocument(doc);
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
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        Scroll1 = new widget.ScrollPane();
        LoadHTML1 = new widget.editorpane();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        BtnAll = new widget.Button();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data 10 Obat Terbanyak Poli ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setBorder(null);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll.setViewportView(LoadHTML);

        TabRawat.addTab("Berdasar Jumlah Obat Terbanyak", Scroll);

        Scroll1.setBorder(null);
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        LoadHTML1.setBorder(null);
        LoadHTML1.setName("LoadHTML1"); // NOI18N
        Scroll1.setViewportView(LoadHTML1);

        TabRawat.addTab("Berdasar Nilai Obat Terbesar", Scroll1);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
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

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(jLabel7);

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
        panelGlass5.add(BtnAll);

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
        label17.setPreferredSize(new java.awt.Dimension(37, 23));
        panelisi4.add(label17);

        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.setPreferredSize(new java.awt.Dimension(60, 23));
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        panelisi4.add(kdpoli);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setPreferredSize(new java.awt.Dimension(168, 23));
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
        label19.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi4.add(label19);

        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(60, 23));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        panelisi4.add(kdpenjab);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(168, 23));
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            
            File g = new File("file2.css");            
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
            );
            bg.close();
            
            File f = new File("rl4a.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            if(TabRawat.getSelectedIndex()==0){
                bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA 10 OBAT TERBANYAK POLI<br>PERIODE REGISTRASI PASIEN "+Tgl1.getSelectedItem()+" - "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();
            }else if(TabRawat.getSelectedIndex()==1){
                bw.write(LoadHTML1.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA 10 OBAT TERBANYAK POLI<br>PERIODE REGISTRASI PASIEN "+Tgl1.getSelectedItem()+" - "+Tgl2.getSelectedItem()+"<br><br></font>"+        
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
        }else{Valid.pindah(evt,BtnKeluar,BtnAll);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

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

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kdpoli.setText("");
        nmpoli.setText("");
        kdpenjab.getText();
        nmpenjab.getText();
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, kdpoli, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

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

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            Dlg10ObatTerbanyakPoli dialog = new Dlg10ObatTerbanyakPoli(new javax.swing.JFrame(), true);
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
    private widget.editorpane LoadHTML1;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel7;
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
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            htmlContent = new StringBuilder();
            pspoli=koneksi.prepareStatement("select kd_poli,nm_poli from poliklinik where concat(kd_poli,nm_poli) like ?");
            try {
                pspoli.setString(1,"%"+kdpoli.getText()+nmpoli.getText()+"%"); 
                rspoli=pspoli.executeQuery();
                i=1;
                if(rspoli.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#fafff5' width='3%'>No.</td>"+
                            "<td valign='middle' bgcolor='#fafff5' width='97%'>Nama Poli</td>"+
                        "</tr>"
                    );
                }
                rspoli.beforeFirst();
                while(rspoli.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='middle'>"+i+"</td>"+
                            "<td valign='middle'>"+rspoli.getString("nm_poli")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+
                            "<td align='center' valign='middle'></td>"+
                            "<td align='left' valign='middle'>"
                    );
                    
                    c=0;
                    pspenjab=koneksi.prepareStatement("select kd_pj,png_jawab from penjab where concat(kd_pj, png_jawab) like ?");
                    try {
                        pspenjab.setString(1,"%"+kdpenjab.getText()+nmpenjab.getText()+"%"); 
                        rspenjab=pspenjab.executeQuery();
                        while(rspenjab.next()){
                            psobat=koneksi.prepareStatement(
                                "select detail_pemberian_obat.kode_brng,databarang.nama_brng,databarang.kode_sat,sum(detail_pemberian_obat.jml) as jml,"+
                                "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya "+
                                "from detail_pemberian_obat inner join reg_periksa inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng and "+
                                "detail_pemberian_obat.no_rawat=reg_periksa.no_rawat where detail_pemberian_obat.status='Ralan' and reg_periksa.kd_pj=? "+
                                "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=? group by detail_pemberian_obat.kode_brng "+
                                "order by jml desc limit 10");   
                            try {
                                psobat.setString(1,rspenjab.getString("kd_pj"));
                                psobat.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                psobat.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                psobat.setString(4,rspoli.getString("kd_poli"));
                                rsobat=psobat.executeQuery();
                                if(rsobat.next()){
                                    c++;
                                    htmlContent.append(
                                        "<table width='100%' border='0' align='center' cellspacing='0'>"+
                                            "<caption>"+rspenjab.getString("png_jawab")+"</caption>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='4%'>No.</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='15%'>Kode Barang</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='43%'>Nama Barang</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='7%'>Satuan</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='7%'>Jml.Obat</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='7%'>Jml.Resep</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='17%'>Nilai Obat</td>"+
                                            "</tr>"
                                    );
                                    rsobat.beforeFirst();
                                    a=1;
                                    while(rsobat.next()){
                                        htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td>"+a+"</td>"+
                                                "<td>"+rsobat.getString("kode_brng")+"</td>"+
                                                "<td>"+rsobat.getString("nama_brng")+"</td>"+
                                                "<td>"+rsobat.getString("kode_sat")+"</td>"+
                                                "<td align='right'>"+rsobat.getString("jml")+"</td>"+
                                                "<td align='right'>"+Sequel.cariIsiAngka(
                                                        "select count(resep_obat.no_resep) from resep_obat inner join detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.tgl_perawatan=resep_obat.tgl_perawatan "+
                                                        "and detail_pemberian_obat.jam=resep_obat.jam and detail_pemberian_obat.no_rawat=resep_obat.no_rawat and resep_obat.no_rawat=reg_periksa.no_rawat "+
                                                        "where detail_pemberian_obat.kode_brng='"+rsobat.getString("kode_brng")+"' and detail_pemberian_obat.status='Ralan' and reg_periksa.kd_pj='"+rspenjab.getString("kd_pj")+"' "+
                                                        "and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and reg_periksa.kd_poli='"+rspoli.getString("kd_poli")+"' "+
                                                        "group by resep_obat.no_resep")+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(rsobat.getDouble("biaya"))+"</td>"+
                                            "</tr>");
                                        a++;
                                    }
                                    htmlContent.append(
                                         "</table>"
                                    );
                                    if(c>1){
                                        htmlContent.append("<br><br>");
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif 3 : "+e);
                            } finally{
                                if(rsobat!=null){
                                    rsobat.close();
                                }
                                if(psobat!=null){
                                    psobat.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rspenjab!=null){
                            rspenjab.close();
                        }
                        if(pspenjab!=null){
                            pspenjab.close();
                        }
                    }
                    htmlContent.append(
                            "</td>"+
                        "</tr>"
                    );
                   
                    i++;
                }
            } catch (Exception e) {
                System.out.println("Notif 1 : "+e);
            } finally{
                if(rspoli!=null){
                    rspoli.close();
                }
                if(pspoli!=null){
                    pspoli.close();
                }
            }   
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            this.setCursor(Cursor.getDefaultCursor());             
        }catch(SQLException e){
            System.out.println("Catatan  "+e);
        }  
    }

    public void tampil2(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            htmlContent = new StringBuilder();
            pspoli=koneksi.prepareStatement("select kd_poli,nm_poli from poliklinik where concat(kd_poli,nm_poli) like ?");
            try {
                pspoli.setString(1,"%"+kdpoli.getText()+nmpoli.getText()+"%"); 
                rspoli=pspoli.executeQuery();
                i=1;
                if(rspoli.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#fafff5' width='3%'>No.</td>"+
                            "<td valign='middle' bgcolor='#fafff5' width='97%'>Nama Poli</td>"+
                        "</tr>"
                    );
                }
                rspoli.beforeFirst();
                while(rspoli.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='middle'>"+i+"</td>"+
                            "<td valign='middle'>"+rspoli.getString("nm_poli")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+
                            "<td align='center' valign='middle'></td>"+
                            "<td align='left' valign='middle'>"
                    );
                    
                    c=0;
                    pspenjab=koneksi.prepareStatement("select kd_pj,png_jawab from penjab where concat(kd_pj, png_jawab) like ?");
                    try {
                        pspenjab.setString(1,"%"+kdpenjab.getText()+nmpenjab.getText()+"%"); 
                        rspenjab=pspenjab.executeQuery();
                        while(rspenjab.next()){
                            psobat=koneksi.prepareStatement(
                                "select detail_pemberian_obat.kode_brng,databarang.nama_brng,databarang.kode_sat,sum(detail_pemberian_obat.jml) as jml,"+
                                "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya "+
                                "from detail_pemberian_obat inner join reg_periksa inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng and "+
                                "detail_pemberian_obat.no_rawat=reg_periksa.no_rawat where detail_pemberian_obat.status='Ralan' and reg_periksa.kd_pj=? "+
                                "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=? group by detail_pemberian_obat.kode_brng "+
                                "order by biaya desc limit 10");   
                            try {
                                psobat.setString(1,rspenjab.getString("kd_pj"));
                                psobat.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                psobat.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                psobat.setString(4,rspoli.getString("kd_poli"));
                                rsobat=psobat.executeQuery();
                                if(rsobat.next()){
                                    c++;
                                    htmlContent.append(
                                        "<table width='100%' border='0' align='center' cellspacing='0'>"+
                                            "<caption>"+rspenjab.getString("png_jawab")+"</caption>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='4%'>No.</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='15%'>Kode Barang</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='43%'>Nama Barang</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='7%'>Satuan</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='7%'>Jml.Obat</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='7%'>Jml.Resep</td>"+
                                                "<td valign='middle' bgcolor='#fafff5' align='center' width='17%'>Nilai Obat</td>"+
                                            "</tr>"
                                    );
                                    rsobat.beforeFirst();
                                    a=1;
                                    while(rsobat.next()){
                                        htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td>"+a+"</td>"+
                                                "<td>"+rsobat.getString("kode_brng")+"</td>"+
                                                "<td>"+rsobat.getString("nama_brng")+"</td>"+
                                                "<td>"+rsobat.getString("kode_sat")+"</td>"+
                                                "<td align='right'>"+rsobat.getString("jml")+"</td>"+
                                                "<td align='right'>"+Sequel.cariIsiAngka(
                                                        "select count(resep_obat.no_resep) from resep_obat inner join detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.tgl_perawatan=resep_obat.tgl_perawatan "+
                                                        "and detail_pemberian_obat.jam=resep_obat.jam and detail_pemberian_obat.no_rawat=resep_obat.no_rawat and resep_obat.no_rawat=reg_periksa.no_rawat "+
                                                        "where detail_pemberian_obat.kode_brng='"+rsobat.getString("kode_brng")+"' and detail_pemberian_obat.status='Ralan' and reg_periksa.kd_pj='"+rspenjab.getString("kd_pj")+"' "+
                                                        "and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and reg_periksa.kd_poli='"+rspoli.getString("kd_poli")+"' "+
                                                        "group by resep_obat.no_resep")+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(rsobat.getDouble("biaya"))+"</td>"+
                                            "</tr>");
                                        a++;
                                    }
                                    htmlContent.append(
                                         "</table>"
                                    );
                                    if(c>1){
                                        htmlContent.append("<br><br>");
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif 3 : "+e);
                            } finally{
                                if(rsobat!=null){
                                    rsobat.close();
                                }
                                if(psobat!=null){
                                    psobat.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rspenjab!=null){
                            rspenjab.close();
                        }
                        if(pspenjab!=null){
                            pspenjab.close();
                        }
                    }
                    htmlContent.append(
                            "</td>"+
                        "</tr>"
                    );
                   
                    i++;
                }
            } catch (Exception e) {
                System.out.println("Notif 1 : "+e);
            } finally{
                if(rspoli!=null){
                    rspoli.close();
                }
                if(pspoli!=null){
                    pspoli.close();
                }
            }   
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            this.setCursor(Cursor.getDefaultCursor());             
        }catch(SQLException e){
            System.out.println("Catatan  "+e);
        } 
    }

}

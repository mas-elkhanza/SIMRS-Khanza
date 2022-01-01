/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package keuangan;

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
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgCariCaraBayar;

/**
 *
 * @author maskhanza
 */
public final class KeuanganRekapPoliAnak extends javax.swing.JDialog {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private PreparedStatement pstanggal,psdokter,psreg,pstindakandokter;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    private ResultSet rstanggal,rsdokter,rsreg,rstindakandokter;
    private int i=0,jmlh0s6l=0,jmlh0s6p=0,jmlh7s28l=0,jmlh7s28p=0,jmlh29st1l=0,
            jmlh29st1p=0,jmlt1s4l=0,jmlt1s4p=0,jmlt5s14l=0,jmlt5s14p=0,
            ttlh0s6l=0,ttlh0s6p=0,ttlh7s28l=0,ttlh7s28p=0,ttlh29st1l=0,
            ttlh29st1p=0,ttlt1s4l=0,ttlt1s4p=0,ttlt5s14l=0,ttlt5s14p=0;
    private String h0s6l="",h0s6p="",h7s28l="",h7s28p="",h29st1l="",h29st1p="",
            t1s4l="",t1s4p="",t5s14l="",t5s14p="";
    
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganRekapPoliAnak(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        
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
        HTMLEditorKit kit = new HTMLEditorKit();
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
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label19 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Poli Anak ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label17.setText("Dokter :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(47, 23));
        panelisi4.add(label17);

        kddokter.setEditable(false);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(75, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        panelisi4.add(kddokter);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(215, 23));
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

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(90, 23));
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

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll.setViewportView(LoadHTML);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            
            File f = new File("sensusdokter.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                            "<tr class='isi2'>"+
                                "<td valign='top' align='center'>"+
                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                    "<font size='2' face='Tahoma'>REKAP HARIAN POLI ANAK<br><br></font>"+        
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

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokterklinik where kd_dokter=?", nmdokter,kddokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_dokter from dokterklinik where kd_dokter=?", nmdokter,kddokter.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_dokter from dokterklinik where kd_dokter=?", nmdokter,kddokter.getText());
            Tgl2.requestFocus();
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
        tampil();
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
        kddokter.setText("");
        nmdokter.setText("");
        kdpenjab.setText("");
        nmpenjab.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{

        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganRekapPoliAnak dialog = new KeuanganRekapPoliAnak(new javax.swing.JFrame(), true);
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
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel8;
    private widget.TextBox kddokter;
    private widget.TextBox kdpenjab;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.TextBox nmdokter;
    private widget.TextBox nmpenjab;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelisi4;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            ttlh0s6l=0;ttlh0s6p=0;ttlh7s28l=0;ttlh7s28p=0;ttlh29st1l=0;
            ttlh29st1p=0;ttlt1s4l=0;ttlt1s4p=0;ttlt5s14l=0;ttlt5s14p=0;
            StringBuilder htmlContent = new StringBuilder();
            pstanggal=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') as tanggal "+
                    "from reg_periksa inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "where reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like '%anak%' "+
                    "group by reg_periksa.tgl_registrasi order by reg_periksa.tgl_registrasi ");
            try {
                pstanggal.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                pstanggal.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                rstanggal=pstanggal.executeQuery();
                while(rstanggal.next()){
                    psdokter=koneksi.prepareStatement(
                            "select reg_periksa.kd_dokter,dokter.nm_dokter from reg_periksa "+
                            "inner join dokter inner join poliklinik on reg_periksa.kd_dokter=dokter.kd_dokter "+
                            "and reg_periksa.kd_poli=poliklinik.kd_poli "+
                            "where poliklinik.nm_poli like '%anak%' and tgl_registrasi=? and dokter.nm_dokter like ? group by reg_periksa.kd_dokter");
                    try {                        
                        psdokter.setString(1,rstanggal.getString("tgl_registrasi"));
                        psdokter.setString(2,"%"+nmdokter.getText().trim()+"%");
                        rsdokter=psdokter.executeQuery();
                        while(rsdokter.next()){
                            htmlContent.append(                                
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='left'>TANGGAL : "+rstanggal.getString("tanggal")+" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DOKTER : "+rsdokter.getString("kd_dokter")+" "+rsdokter.getString("nm_dokter")+"</td>"+
                                "</tr>"+
                                "<tr class='isi2'>"+
                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                       "<tr class='isi3'>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%' rowspan='3'>NO.</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='3'>NO.RM</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%' rowspan='3'>NAMA IBU</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%' rowspan='3'>NAMA ANAK</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='12%' rowspan='3'>ALAMAT</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%' rowspan='3'>TGL.LAHIR</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>LAHIR</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='1%' rowspan='2'>L/B</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='25%' colspan='10'>USIA</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%' rowspan='2'>TINDAKAN & HARGA</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='4%'>IMUN KE</td>"+
                                        "</tr>"+
                                        "<tr class='isi3'>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>DIMANA</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>0-6 hari</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>7-28 hari</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>28-1 thn</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>1-4 thn</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>5-14 thn</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>BERAPA</td>"+
                                        "</tr>"+
                                        "<tr class='isi3'>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>&nbsp;</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>&nbsp;</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>&nbsp;</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>&nbsp;</td>"+
                                        "</tr>");
                            psreg=koneksi.prepareStatement(
                                    "select reg_periksa.no_rkm_medis,pasien.nm_ibu,pasien.nm_pasien,pasien.alamat,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,"+
                                    "reg_periksa.stts_daftar,penjab.png_jawab,reg_periksa.no_rawat,reg_periksa.almt_pj,pasien.tgl_lahir,pasien.tmp_lahir,reg_periksa.stts_daftar,"+
                                    "reg_periksa.umurdaftar,reg_periksa.sttsumur from reg_periksa inner join pasien inner join penjab "+
                                    "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj where "+
                                    "reg_periksa.tgl_registrasi=? and reg_periksa.kd_dokter=? and penjab.png_jawab like ? and reg_periksa.no_rkm_medis like ? or "+
                                    "reg_periksa.tgl_registrasi=? and reg_periksa.kd_dokter=? and penjab.png_jawab like ? and pasien.nm_pasien like ? or "+
                                    "reg_periksa.tgl_registrasi=? and reg_periksa.kd_dokter=? and penjab.png_jawab like ? and pasien.alamat like ? or "+
                                    "reg_periksa.tgl_registrasi=? and reg_periksa.kd_dokter=? and penjab.png_jawab like ? and reg_periksa.stts_daftar like ? order by reg_periksa.no_reg ");
                            try {
                                i=1;
                                jmlh0s6l=0;jmlh0s6p=0;jmlh7s28l=0;jmlh7s28p=0;jmlh29st1l=0;
                                jmlh29st1p=0;jmlt1s4l=0;jmlt1s4p=0;jmlt5s14l=0;jmlt5s14p=0;
                                psreg.setString(1,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(2,rsdokter.getString("kd_dokter"));
                                psreg.setString(3,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(4,"%"+TCari.getText().trim()+"%");
                                psreg.setString(5,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(6,rsdokter.getString("kd_dokter"));
                                psreg.setString(7,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(8,"%"+TCari.getText().trim()+"%");
                                psreg.setString(9,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(10,rsdokter.getString("kd_dokter"));
                                psreg.setString(11,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(12,"%"+TCari.getText().trim()+"%");
                                psreg.setString(13,rstanggal.getString("tgl_registrasi"));
                                psreg.setString(14,rsdokter.getString("kd_dokter"));
                                psreg.setString(15,"%"+nmpenjab.getText().trim()+"%");
                                psreg.setString(16,"%"+TCari.getText().trim()+"%");
                                rsreg=psreg.executeQuery();
                                while(rsreg.next()){
                                    h0s6l="";h0s6p="";h7s28l="";h7s28p="";h29st1l="";
                                    h29st1p="";t1s4l="";t1s4p="";t5s14l="";t5s14p="";
                                    if(rsreg.getString("sttsumur").equals("Hr")){
                                        if((rsreg.getInt("umurdaftar")>=0)&&(rsreg.getInt("umurdaftar")<=6)){
                                            if(rsreg.getString("jk").equals("L")){
                                                h0s6l="V";
                                                jmlh0s6l++;
                                                ttlh0s6l++;
                                            }else if(rsreg.getString("jk").equals("P")){
                                                h0s6p="V";
                                                jmlh0s6p++;
                                                ttlh0s6p++;
                                            }
                                        }else if((rsreg.getInt("umurdaftar")>=7)&&(rsreg.getInt("umurdaftar")<=28)){
                                            if(rsreg.getString("jk").equals("L")){
                                                h7s28l="V";
                                                jmlh7s28l++;
                                                ttlh7s28l++;
                                            }else if(rsreg.getString("jk").equals("P")){
                                                h7s28p="V";
                                                jmlh7s28p++;
                                                ttlh7s28p++;
                                            }
                                        }else if(rsreg.getInt("umurdaftar")>28){
                                            if(rsreg.getString("jk").equals("L")){
                                                h29st1l="V";
                                                jmlh29st1l++;
                                                ttlh29st1l++;
                                            }else if(rsreg.getString("jk").equals("P")){
                                                h29st1p="V";
                                                jmlh29st1p++;
                                                ttlh29st1p++;
                                            }
                                        }
                                    }else if(rsreg.getString("sttsumur").equals("Bl")){
                                        if(rsreg.getString("jk").equals("L")){
                                            h29st1l="V";
                                            jmlh29st1l++;
                                            ttlh29st1l++;
                                        }else if(rsreg.getString("jk").equals("P")){
                                            h29st1p="V";
                                            jmlh29st1p++;
                                            ttlh29st1p++;
                                        }
                                    }else if(rsreg.getString("sttsumur").equals("Th")){
                                        if((rsreg.getInt("umurdaftar")>=0)&&(rsreg.getInt("umurdaftar")<=4)){
                                            if(rsreg.getString("jk").equals("L")){
                                                t1s4l="V";
                                                jmlt1s4l++;
                                                ttlt1s4l++;
                                            }else if(rsreg.getString("jk").equals("P")){
                                                t1s4p="V";
                                                jmlt1s4p++;
                                                ttlt1s4p++;
                                            }
                                        }else if((rsreg.getInt("umurdaftar")>=5)&&(rsreg.getInt("umurdaftar")<=14)){
                                            if(rsreg.getString("jk").equals("L")){
                                                t5s14l="V";
                                                jmlt5s14l++;
                                                ttlt5s14l++;
                                            }else if(rsreg.getString("jk").equals("P")){
                                                t5s14p="V";
                                                jmlt5s14p++;
                                                ttlt5s14p++;
                                            }
                                        }
                                    }
                                    htmlContent.append(
                                        "<tr class='isi3'>"+
                                            "<td valign='top' align='center'>"+i+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("no_rkm_medis")+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("nm_ibu")+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("nm_pasien")+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("almt_pj")+"</td>"+
                                            "<td valign='top' align='center'>"+rsreg.getString("tgl_lahir")+"</td>"+
                                            "<td valign='top'>"+rsreg.getString("tmp_lahir")+"</td>"+
                                            "<td valign='top' align='center'>"+rsreg.getString("stts_daftar").substring(0,1)+"</td>"+
                                            "<td valign='top' align='center'>"+h0s6l+"</td>"+
                                            "<td valign='top' align='center'>"+h0s6p+"</td>"+
                                            "<td valign='top' align='center'>"+h7s28l+"</td>"+
                                            "<td valign='top' align='center'>"+h7s28p+"</td>"+
                                            "<td valign='top' align='center'>"+h29st1l+"</td>"+
                                            "<td valign='top' align='center'>"+h29st1p+"</td>"+
                                            "<td valign='top' align='center'>"+t1s4l+"</td>"+
                                            "<td valign='top' align='center'>"+t1s4p+"</td>"+
                                            "<td valign='top' align='center'>"+t5s14l+"</td>"+
                                            "<td valign='top' align='center'>"+t5s14p+"</td>"+
                                            "<td valign='top'>"+
                                                "<table width='100%' border='0' align='center' cellpadding='0px' cellspacing='0' class='tbl_form'>");
                                    pstindakandokter=koneksi.prepareStatement(
                                            "select jns_perawatan.nm_perawatan,rawat_jl_dr.biaya_rawat "+
                                            "from reg_periksa inner join rawat_jl_dr inner join jns_perawatan "+
                                            "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                                            "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                            "where rawat_jl_dr.kd_dokter=? and rawat_jl_dr.no_rawat=?");
                                    try {
                                        pstindakandokter.setString(1,rsdokter.getString("kd_dokter"));
                                        pstindakandokter.setString(2,rsreg.getString("no_rawat"));
                                        rstindakandokter=pstindakandokter.executeQuery();
                                        while (rstindakandokter.next()) {
                                            htmlContent.append("<tr class='isi3'>"+
                                                                    "<td valign='top' align='left' width='69%'>&nbsp;"+rstindakandokter.getString("nm_perawatan")+"</td>"+
                                                                    "<td valign='top' align='right' width='30%'>"+Valid.SetAngka(rstindakandokter.getDouble("biaya_rawat"))+"</td>"+
                                                               "</tr>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("keuangan.DlgRekapPoliAnak.tampil() : "+e);
                                    } finally{
                                        if(rstindakandokter!=null){
                                            rstindakandokter.close();
                                        }
                                        if(pstindakandokter!=null){
                                            pstindakandokter.close();
                                        }
                                    }
                                    htmlContent.append(
                                                "</table>"+
                                            "</td>"+                                                        
                                            "<td valign='top' align='center'>"+Sequel.cariIsi("select imun_ke  from pemeriksaan_ralan where no_rawat=?",rsreg.getString("no_rawat"))+"</td>"+
                                        "</tr>");
                                    i++;
                                }
                            } catch (Exception e) {
                                System.out.println("keuangan.DlgRekapPoliAnak.tampil() : "+e);
                            } finally{
                                if(rsreg!=null){
                                    rsreg.close();
                                }
                                if(psreg!=null){
                                    psreg.close();
                                }
                            }
                            htmlContent.append(
                                "<tr class='isi3'>"+
                                    "<td valign='top' align='left' colspan='8'>Total : </td>"+
                                    "<td valign='top' align='center'>"+jmlh0s6l+"</td>"+
                                    "<td valign='top' align='center'>"+jmlh0s6p+"</td>"+
                                    "<td valign='top' align='center'>"+jmlh7s28l+"</td>"+
                                    "<td valign='top' align='center'>"+jmlh7s28p+"</td>"+
                                    "<td valign='top' align='center'>"+jmlh29st1l+"</td>"+
                                    "<td valign='top' align='center'>"+jmlh29st1p+"</td>"+
                                    "<td valign='top' align='center'>"+jmlt1s4l+"</td>"+
                                    "<td valign='top' align='center'>"+jmlt1s4p+"</td>"+
                                    "<td valign='top' align='center'>"+jmlt5s14l+"</td>"+
                                    "<td valign='top' align='center'>"+jmlt5s14p+"</td>"+
                                    "<td valign='top' colspan='2'></td>"+
                                "</tr>"+
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
                            htmlContent.append(
                                    "</table>"+
                                "</tr>");                                                            
                        }                        
                    } catch (Exception e) {
                        System.out.println("keuangan.DlgRekapPoliAnak.tampil() : "+e);
                    } finally{
                        if(rsdokter!=null){
                            rsdokter.close();
                        }
                        if(psdokter!=null){
                            psdokter.close();
                        }
                    }                                                        
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
            if((ttlh0s6l+ttlh0s6p+ttlh7s28l+ttlh7s28p+ttlh29st1l+ttlh29st1p+ttlt1s4l+ttlt1s4p+ttlt5s14l+ttlt5s14p)>0){
                htmlContent.append(
                    "<tr class='isi2'>"+
                        "<td valign='top' align='left'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>"+
                    "</tr>"+
                    "<tr class='isi2'>"+
                        "<td valign='top' align='left'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>"+
                    "</tr>"+
                    "<tr class='isi2'>"+
                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                            "<tr class='isi3'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='right' width='10%'>Usia : </td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='18%' colspan='2'>0-6 hari</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='18%' colspan='2'>7-28 hari</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='18%' colspan='2'>28-1 thn</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='18%' colspan='2'>1-4 thn</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='18%' colspan='2'>5-14 thn</td>"+
                            "</tr>"+
                            "<tr class='isi3'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='right'>Jenis Kelamin :</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                            "</tr>"+
                            "<tr class='isi3'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='right'>Jumlah Total :</td>"+
                                "<td valign='top' align='center'>"+ttlh0s6l+"</td>"+
                                "<td valign='top' align='center'>"+ttlh0s6p+"</td>"+
                                "<td valign='top' align='center'>"+ttlh7s28l+"</td>"+
                                "<td valign='top' align='center'>"+ttlh7s28p+"</td>"+
                                "<td valign='top' align='center'>"+ttlh29st1l+"</td>"+
                                "<td valign='top' align='center'>"+ttlh29st1p+"</td>"+
                                "<td valign='top' align='center'>"+ttlt1s4l+"</td>"+
                                "<td valign='top' align='center'>"+ttlt1s4p+"</td>"+
                                "<td valign='top' align='center'>"+ttlt5s14l+"</td>"+
                                "<td valign='top' align='center'>"+ttlt5s14p+"</td>"+
                            "</tr>"+
                         "</table>"+
                    "</tr>");
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

    

}

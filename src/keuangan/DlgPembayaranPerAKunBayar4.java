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
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
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

/**
 *
 * @author perpustakaan
 */
public final class DlgPembayaranPerAKunBayar4 extends javax.swing.JDialog {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private PreparedStatement ps,psakunbayar;
    private ResultSet rs,rsakunbayar;
    private double all=0,bayar=0;
    private int i,kolom=0,no=0,kolom2=0;
    private String shift="",tanggal2="",petugas="";
    private StringBuilder htmlContent;
    private String[] namabayar,akunrekening,namarekening;
    private double[] totalbayar,totalbayar2;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgPembayaranPerAKunBayar4(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        User.setDocument(new batasInput((byte)100).getKata(User));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            tampil();
                        }else if(TabRawat.getSelectedIndex()==1){
                            tampil2();
                        }
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            tampil();
                        }else if(TabRawat.getSelectedIndex()==1){
                            tampil2();
                        }
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            tampil();
                        }else if(TabRawat.getSelectedIndex()==1){
                            tampil2();
                        }
                    }
                }
            });
            User.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(User.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            tampil();
                        }else if(TabRawat.getSelectedIndex()==1){
                            tampil2();
                        }
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(User.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            tampil();
                        }else if(TabRawat.getSelectedIndex()==1){
                            tampil2();
                        }
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(User.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            tampil();
                        }else if(TabRawat.getSelectedIndex()==1){
                            tampil2();
                        }
                    }
                }
            });
        }  
        LoadHTML.setEditable(true);
        LoadHTML2.setEditable(true);
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"
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

        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        label19 = new widget.Label();
        User = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel11 = new javax.swing.JLabel();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass6 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        jLabel9 = new widget.Label();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        label12 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel10 = new widget.Label();
        CmbJam2 = new widget.ComboBox();
        CmbMenit2 = new widget.ComboBox();
        CmbDetik2 = new widget.ComboBox();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        Scroll1 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pembayaran Per Akun Bayar 4 ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

        label19.setText("User :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(label19);

        User.setName("User"); // NOI18N
        User.setPreferredSize(new java.awt.Dimension(150, 23));
        User.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UserKeyPressed(evt);
            }
        });
        panelGlass5.add(User);

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

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(50, 50, 50));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(jLabel11);

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

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 45));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Periode :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass6.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(Tgl1);

        jLabel9.setText("Jam :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(40, 23));
        panelGlass6.add(jLabel9);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.setPreferredSize(new java.awt.Dimension(62, 23));
        CmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamKeyPressed(evt);
            }
        });
        panelGlass6.add(CmbJam);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.setPreferredSize(new java.awt.Dimension(62, 23));
        CmbMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitKeyPressed(evt);
            }
        });
        panelGlass6.add(CmbMenit);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.setPreferredSize(new java.awt.Dimension(62, 23));
        CmbDetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikKeyPressed(evt);
            }
        });
        panelGlass6.add(CmbDetik);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass6.add(label12);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(Tgl2);

        jLabel10.setText("Jam :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(40, 23));
        panelGlass6.add(jLabel10);

        CmbJam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam2.setSelectedIndex(23);
        CmbJam2.setName("CmbJam2"); // NOI18N
        CmbJam2.setPreferredSize(new java.awt.Dimension(62, 23));
        CmbJam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJam2KeyPressed(evt);
            }
        });
        panelGlass6.add(CmbJam2);

        CmbMenit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit2.setSelectedIndex(59);
        CmbMenit2.setName("CmbMenit2"); // NOI18N
        CmbMenit2.setPreferredSize(new java.awt.Dimension(62, 23));
        CmbMenit2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenit2KeyPressed(evt);
            }
        });
        panelGlass6.add(CmbMenit2);

        CmbDetik2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik2.setSelectedIndex(59);
        CmbDetik2.setName("CmbDetik2"); // NOI18N
        CmbDetik2.setPreferredSize(new java.awt.Dimension(62, 23));
        CmbDetik2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetik2KeyPressed(evt);
            }
        });
        panelGlass6.add(CmbDetik2);

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll.setViewportView(LoadHTML);

        TabRawat.addTab("Model 1", Scroll);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll1.setViewportView(LoadHTML2);

        TabRawat.addTab("Model 2", Scroll1);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {            
            File g = new File("fileakunbayar.css");            
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"
            );
            bg.close();
            
            File f = new File("PembayaranPerAkunBayar.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
            bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"fileakunbayar.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                            "<tr class='isi2'>"+
                                "<td valign='top' align='center'>"+
                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                    "<font size='2' face='Tahoma'>PEMBAYARAN PER AKUN BAYAR<br>TANGGAL "+Tgl1.getSelectedItem()+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem()+"<br><br></font>"+        
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
            Valid.pindah(evt, Tgl1,BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else{
            tampil2();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(TabRawat.getSelectedIndex()==0){
                tampil();
            }else{
                tampil2();
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt,TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else{
            tampil2();
        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void UserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UserKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UserKeyPressed

    private void CmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamKeyPressed
        Valid.pindah(evt,Tgl1,CmbMenit);
    }//GEN-LAST:event_CmbJamKeyPressed

    private void CmbMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitKeyPressed
        Valid.pindah(evt,CmbJam,CmbDetik);
    }//GEN-LAST:event_CmbMenitKeyPressed

    private void CmbDetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikKeyPressed
        Valid.pindah(evt,CmbMenit,Tgl2);
    }//GEN-LAST:event_CmbDetikKeyPressed

    private void CmbJam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJam2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJam2KeyPressed

    private void CmbMenit2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenit2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbMenit2KeyPressed

    private void CmbDetik2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetik2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbDetik2KeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try{        
            htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='head'>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='27px'>No.</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='110px'>Tanggal</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>No.Rawat/No.Nota</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='220px'>Nama Pasien</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>Jenis/Cara Bayar</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'>Pembayaran</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='130px'>Petugas</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='400px'>Akun Bayar</td>"+
                "</tr>"
            );           
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPembayaranPerAKunBayar4 dialog = new DlgPembayaranPerAKunBayar4(new javax.swing.JFrame(), true);
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
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbDetik2;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbJam2;
    private widget.ComboBox CmbMenit;
    private widget.ComboBox CmbMenit2;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.TextBox User;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private javax.swing.JLabel jLabel11;
    private widget.Label jLabel9;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label17;
    private widget.Label label19;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass6;
    // End of variables declaration//GEN-END:variables

    private void tampil(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        try{        
            htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='head'>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='27px'>No.</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='110px'>Tanggal</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>No.Rawat/No.Nota</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='220px'>Nama Pasien</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>Jenis/Cara Bayar</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'>Pembayaran</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='130px'>Petugas</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='400px'>Akun Bayar</td>"+
                "</tr>"
            );   
            
            kolom=0;
            i=Sequel.cariInteger("select count(akun_bayar.nama_bayar) from akun_bayar");
            namabayar=new String[i];
            psakunbayar=koneksi.prepareStatement("select akun_bayar.nama_bayar from akun_bayar order by akun_bayar.nama_bayar");
            try {
                rsakunbayar=psakunbayar.executeQuery();
                while(rsakunbayar.next()){
                    namabayar[kolom]=rsakunbayar.getString("nama_bayar");
                    kolom++;
                }
            } catch (Exception e) {
                System.out.println("Akun Bayar : "+e);
            } finally{
                if(rsakunbayar!=null){
                    rsakunbayar.close();
                }
                if(psakunbayar!=null){
                    psakunbayar.close();
                }
            }
            
            totalbayar=new double[kolom]; 
            
            all=0;
            //rawat inap
            ps= koneksi.prepareStatement(
                    "select nota_inap.no_nota,penjab.png_jawab,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas,nota_inap.no_rawat "+
                    "from tagihan_sadewa inner join nota_inap on tagihan_sadewa.no_nota=nota_inap.no_rawat "+
                    "inner join reg_periksa on nota_inap.no_rawat=reg_periksa.no_rawat "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                no=1;
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("png_jawab")+"</td>"+
                                "<td valign='middle' align='right'>"+Valid.SetAngka(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom;i++){
                            bayar=Sequel.cariIsiAngka("select sum(detail_nota_inap.besar_bayar) from detail_nota_inap where detail_nota_inap.no_rawat='"+rs.getString("no_rawat")+"' and detail_nota_inap.nama_bayar='"+namabayar[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namabayar[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Valid.SetAngka(bayar)+"</td></tr>");
                                totalbayar[i]=totalbayar[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            //rawat jalan
            ps= koneksi.prepareStatement(
                    "select nota_jalan.no_nota,penjab.png_jawab,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas,nota_jalan.no_rawat "+
                    "from tagihan_sadewa inner join nota_jalan on tagihan_sadewa.no_nota=nota_jalan.no_rawat "+
                    "inner join reg_periksa on nota_jalan.no_rawat=reg_periksa.no_rawat "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("png_jawab")+"</td>"+
                                "<td valign='middle' align='right'>"+Valid.SetAngka(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom;i++){
                            bayar=Sequel.cariIsiAngka("select sum(detail_nota_jalan.besar_bayar) from detail_nota_jalan where detail_nota_jalan.no_rawat='"+rs.getString("no_rawat")+"' and detail_nota_jalan.nama_bayar='"+namabayar[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namabayar[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Valid.SetAngka(bayar)+"</td></tr>");
                                totalbayar[i]=totalbayar[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            //penjualan bebas
            ps= koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas "+
                    "from tagihan_sadewa inner join penjualan on tagihan_sadewa.no_nota=penjualan.nota_jual "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>Penjualan Apotek</td>"+
                                "<td valign='middle' align='right'>"+Valid.SetAngka(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom;i++){
                            bayar=Sequel.cariIsiAngka("select (sum(detailjual.total)+penjualan.ongkir+penjualan.ppn) from detailjual inner join penjualan on penjualan.nota_jual=detailjual.nota_jual where penjualan.nota_jual='"+rs.getString("no_nota")+"' and penjualan.nama_bayar='"+namabayar[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namabayar[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Valid.SetAngka(bayar)+"</td></tr>");
                                totalbayar[i]=totalbayar[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            //Deposit
            ps= koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas "+
                    "from tagihan_sadewa inner join deposit on tagihan_sadewa.no_nota=deposit.no_deposit "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>Deposit Pasien</td>"+
                                "<td valign='middle' align='right'>"+Valid.SetAngka(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom;i++){
                            bayar=Sequel.cariIsiAngka("select sum(deposit.besar_deposit) from deposit where deposit.no_deposit='"+rs.getString("no_nota")+"' and deposit.nama_bayar='"+namabayar[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namabayar[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Valid.SetAngka(bayar)+"</td></tr>");
                                totalbayar[i]=totalbayar[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            kolom2=0;
            i=Sequel.cariInteger("select count(rekening.kd_rek) from rekening where rekening.kd_rek in (select kategori_pemasukan_lain.kd_rek2 from kategori_pemasukan_lain group by kategori_pemasukan_lain.kd_rek2)");
            akunrekening=new String[i];
            namarekening=new String[i];
            psakunbayar=koneksi.prepareStatement("select rekening.kd_rek,rekening.nm_rek from rekening where rekening.kd_rek in (select kategori_pemasukan_lain.kd_rek2 from kategori_pemasukan_lain group by kategori_pemasukan_lain.kd_rek2) order by rekening.nm_rek");
            try {
                rsakunbayar=psakunbayar.executeQuery();
                while(rsakunbayar.next()){
                    akunrekening[kolom2]=rsakunbayar.getString("kd_rek");
                    namarekening[kolom2]=rsakunbayar.getString("nm_rek");
                    kolom2++;
                }
            } catch (Exception e) {
                System.out.println("Akun Bayar : "+e);
            } finally{
                if(rsakunbayar!=null){
                    rsakunbayar.close();
                }
                if(psakunbayar!=null){
                    psakunbayar.close();
                }
            }
            
            totalbayar2=new double[kolom2]; 
            
            //pemasukanlain2
            ps= koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas "+
                    "from tagihan_sadewa inner join pemasukan_lain on tagihan_sadewa.no_nota=pemasukan_lain.no_masuk "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>Pemasukan Lain-lain</td>"+
                                "<td valign='middle' align='right'>"+Valid.SetAngka(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom2;i++){
                            bayar=Sequel.cariIsiAngka("select sum(pemasukan_lain.besar) from pemasukan_lain inner join kategori_pemasukan_lain on kategori_pemasukan_lain.kode_kategori=pemasukan_lain.kode_kategori where pemasukan_lain.no_masuk='"+rs.getString("no_nota")+"' and kategori_pemasukan_lain.kd_rek2='"+akunrekening[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namarekening[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Valid.SetAngka(bayar)+"</td></tr>");
                                totalbayar2[i]=totalbayar2[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            for(i=0;i<kolom;i++){
                if(totalbayar[i]>0){
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'></td>"+
                            "<td valign='middle' align='left' colspan='6'>Total "+namabayar[i]+"</td>"+
                            "<td valign='middle' align='right'>"+Valid.SetAngka(totalbayar[i])+"</td>"+
                        "</tr>"
                    ); 
                }  
            }
            
            for(i=0;i<kolom2;i++){
                if(totalbayar2[i]>0){
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'></td>"+
                            "<td valign='middle' align='left' colspan='6'>Total "+namarekening[i]+"</td>"+
                            "<td valign='middle' align='right'>"+Valid.SetAngka(totalbayar2[i])+"</td>"+
                        "</tr>"
                    ); 
                }  
            }
            
            if(all>0){
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' align='center'></td>"+
                        "<td valign='middle' align='left' colspan='6'><b>Jumlah Total<b></td>"+
                        "<td valign='middle' align='right'><b>"+Valid.SetAngka(all)+"<b></td>"+
                    "</tr>"
                ); 
            }
                         
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }    

    private void tampil2(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        try{        
            htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='head'>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='27px'>No.</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='110px'>Tanggal</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>No.Rawat/No.Nota</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='220px'>Nama Pasien</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>Jenis/Cara Bayar</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'>Pembayaran</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='130px'>Petugas</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='400px'>Akun Bayar</td>"+
                "</tr>"
            );   
            
            kolom=0;
            i=Sequel.cariInteger("select count(akun_bayar.nama_bayar) from akun_bayar");
            namabayar=new String[i];
            psakunbayar=koneksi.prepareStatement("select akun_bayar.nama_bayar from akun_bayar order by akun_bayar.nama_bayar");
            try {
                rsakunbayar=psakunbayar.executeQuery();
                while(rsakunbayar.next()){
                    namabayar[kolom]=rsakunbayar.getString("nama_bayar");
                    kolom++;
                }
            } catch (Exception e) {
                System.out.println("Akun Bayar : "+e);
            } finally{
                if(rsakunbayar!=null){
                    rsakunbayar.close();
                }
                if(psakunbayar!=null){
                    psakunbayar.close();
                }
            }
            
            totalbayar=new double[kolom]; 
            
            all=0;
            //rawat inap
            ps= koneksi.prepareStatement(
                    "select nota_inap.no_nota,penjab.png_jawab,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas,nota_inap.no_rawat "+
                    "from tagihan_sadewa inner join nota_inap on tagihan_sadewa.no_nota=nota_inap.no_rawat "+
                    "inner join reg_periksa on nota_inap.no_rawat=reg_periksa.no_rawat "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                no=1;
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("png_jawab")+"</td>"+
                                "<td valign='middle' align='right'>"+Math.round(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom;i++){
                            bayar=Sequel.cariIsiAngka("select sum(detail_nota_inap.besar_bayar) from detail_nota_inap where detail_nota_inap.no_rawat='"+rs.getString("no_rawat")+"' and detail_nota_inap.nama_bayar='"+namabayar[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namabayar[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Math.round(bayar)+"</td></tr>");
                                totalbayar[i]=totalbayar[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            //rawat jalan
            ps= koneksi.prepareStatement(
                    "select nota_jalan.no_nota,penjab.png_jawab,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas,nota_jalan.no_rawat "+
                    "from tagihan_sadewa inner join nota_jalan on tagihan_sadewa.no_nota=nota_jalan.no_rawat "+
                    "inner join reg_periksa on nota_jalan.no_rawat=reg_periksa.no_rawat "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("png_jawab")+"</td>"+
                                "<td valign='middle' align='right'>"+Math.round(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom;i++){
                            bayar=Sequel.cariIsiAngka("select sum(detail_nota_jalan.besar_bayar) from detail_nota_jalan where detail_nota_jalan.no_rawat='"+rs.getString("no_rawat")+"' and detail_nota_jalan.nama_bayar='"+namabayar[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namabayar[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Math.round(bayar)+"</td></tr>");
                                totalbayar[i]=totalbayar[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            //penjualan bebas
            ps= koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas "+
                    "from tagihan_sadewa inner join penjualan on tagihan_sadewa.no_nota=penjualan.nota_jual "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>Penjualan Apotek</td>"+
                                "<td valign='middle' align='right'>"+Math.round(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom;i++){
                            bayar=Sequel.cariIsiAngka("select (sum(detailjual.total)+penjualan.ongkir+penjualan.ppn) from detailjual inner join penjualan on penjualan.nota_jual=detailjual.nota_jual where penjualan.nota_jual='"+rs.getString("no_nota")+"' and penjualan.nama_bayar='"+namabayar[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namabayar[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Math.round(bayar)+"</td></tr>");
                                totalbayar[i]=totalbayar[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            //Deposit
            ps= koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas "+
                    "from tagihan_sadewa inner join deposit on tagihan_sadewa.no_nota=deposit.no_deposit "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>Deposit Pasien</td>"+
                                "<td valign='middle' align='right'>"+Math.round(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom;i++){
                            bayar=Sequel.cariIsiAngka("select sum(deposit.besar_deposit) from deposit where deposit.no_deposit='"+rs.getString("no_nota")+"' and deposit.nama_bayar='"+namabayar[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namabayar[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Math.round(bayar)+"</td></tr>");
                                totalbayar[i]=totalbayar[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            kolom2=0;
            i=Sequel.cariInteger("select count(rekening.kd_rek) from rekening where rekening.kd_rek in (select kategori_pemasukan_lain.kd_rek2 from kategori_pemasukan_lain group by kategori_pemasukan_lain.kd_rek2)");
            akunrekening=new String[i];
            namarekening=new String[i];
            psakunbayar=koneksi.prepareStatement("select rekening.kd_rek,rekening.nm_rek from rekening where rekening.kd_rek in (select kategori_pemasukan_lain.kd_rek2 from kategori_pemasukan_lain group by kategori_pemasukan_lain.kd_rek2) order by rekening.nm_rek");
            try {
                rsakunbayar=psakunbayar.executeQuery();
                while(rsakunbayar.next()){
                    akunrekening[kolom2]=rsakunbayar.getString("kd_rek");
                    namarekening[kolom2]=rsakunbayar.getString("nm_rek");
                    kolom2++;
                }
            } catch (Exception e) {
                System.out.println("Akun Bayar : "+e);
            } finally{
                if(rsakunbayar!=null){
                    rsakunbayar.close();
                }
                if(psakunbayar!=null){
                    psakunbayar.close();
                }
            }
            
            totalbayar2=new double[kolom2]; 
            
            //pemasukanlain2
            ps= koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,tagihan_sadewa.tgl_bayar,tagihan_sadewa.nama_pasien,tagihan_sadewa.jumlah_bayar,tagihan_sadewa.petugas "+
                    "from tagihan_sadewa inner join pemasukan_lain on tagihan_sadewa.no_nota=pemasukan_lain.no_masuk "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                rs=ps.executeQuery();
                while(rs.next()){                            
                    petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",rs.getString("petugas"));
                    
                    if((petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim()))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||rs.getString("no_nota").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        all=all+rs.getDouble("jumlah_bayar");
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+no+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("tgl_bayar")+"</td>"+
                                "<td valign='middle' align='center'>"+rs.getString("no_nota")+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama_pasien")+"</td>"+
                                "<td valign='middle' align='center'>Pemasukan Lain-lain</td>"+
                                "<td valign='middle' align='right'>"+Math.round(rs.getDouble("jumlah_bayar"))+"</td>"+
                                "<td valign='middle' align='left'>"+petugas+"</td>"+
                                "<td>"+
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom2;i++){
                            bayar=Sequel.cariIsiAngka("select sum(pemasukan_lain.besar) from pemasukan_lain inner join kategori_pemasukan_lain on kategori_pemasukan_lain.kode_kategori=pemasukan_lain.kode_kategori where pemasukan_lain.no_masuk='"+rs.getString("no_nota")+"' and kategori_pemasukan_lain.kd_rek2='"+akunrekening[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>"+namarekening[i]+"</td><td valign='middle' width='30%' align='right' border='0'>"+Math.round(bayar)+"</td></tr>");
                                totalbayar2[i]=totalbayar2[i]+bayar;
                            }
                        }
                        htmlContent.append( 
                                    "</table>"+
                                "</td>"+
                            "</tr>"
                        ); 
                        no++;
                    }                                
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
            
            for(i=0;i<kolom;i++){
                if(totalbayar[i]>0){
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'></td>"+
                            "<td valign='middle' align='left' colspan='6'>Total "+namabayar[i]+"</td>"+
                            "<td valign='middle' align='right'>"+Math.round(totalbayar[i])+"</td>"+
                        "</tr>"
                    ); 
                }  
            }
            
            for(i=0;i<kolom2;i++){
                if(totalbayar2[i]>0){
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'></td>"+
                            "<td valign='middle' align='left' colspan='6'>Total "+namarekening[i]+"</td>"+
                            "<td valign='middle' align='right'>"+Math.round(totalbayar2[i])+"</td>"+
                        "</tr>"
                    ); 
                }  
            }
            
            if(all>0){
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' align='center'></td>"+
                        "<td valign='middle' align='left' colspan='6'><b>Jumlah Total<b></td>"+
                        "<td valign='middle' align='right'><b>"+Math.round(all)+"<b></td>"+
                    "</tr>"
                ); 
            }
                         
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    } 
}

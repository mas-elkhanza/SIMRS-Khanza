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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author perpustakaan
 */
public final class DlgPendapatanPerAKunClosing extends javax.swing.JDialog {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private PreparedStatement ps,psakunbayar,psakunpiutang;
    private ResultSet rs,rsakunbayar,rsakunpiutang;
    private double all=0,bayar=0,piutangobat=0;
    private int i,kolom=0,kolom2=0,kolompiutang=0,no=0;
    private String nonota="",norawatjalan="",norawatinap="",notajual="",carabayar="",nodeposit="",notakesling="",
                   akunpiutangobat=Sequel.cariIsi("select rekening.nm_rek from rekening where rekening.kd_rek=?",Sequel.cariIsi("select set_akun.Piutang_Obat from set_akun"));
    private String[] namabayar,namapiutang,akunrekening,namarekening;
    private double[] totalbayar,totalpiutang,totalbayar2;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgPendapatanPerAKunClosing(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            runBackground(() ->tampil());
                        }else if(TabRawat.getSelectedIndex()==1){
                            runBackground(() ->tampil2());
                        }
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            runBackground(() ->tampil());
                        }else if(TabRawat.getSelectedIndex()==1){
                            runBackground(() ->tampil2());
                        }
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            runBackground(() ->tampil());
                        }else if(TabRawat.getSelectedIndex()==1){
                            runBackground(() ->tampil2());
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
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label12 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel11 = new javax.swing.JLabel();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pendapatan Per Akun Closing ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Periode :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl1);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(label12);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl2);

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass5.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
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
            
            BufferedWriter bw;
            File f;
            
            String pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (XLS)"},"Laporan 1 (HTML)");
            switch (pilihan) {
                case "Laporan 1 (HTML)":
                    f = new File("PendapatanPerAkunClosing.html");            
                    bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"fileakunbayar.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>PENDAPATAN PER AKUN CLOSING<br>TANGGAL "+Tgl1.getSelectedItem()+"<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
                    break;
                case "Laporan 2 (WPS)":
                    f = new File("PendapatanPerAkunClosing.wps");            
                    bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText());
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
                    break;
                case "Laporan 3 (XLS)":
                    f= new File("PendapatanPerAkunClosing.xls");            
                    bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText());
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
                    break;

            }
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
            runBackground(() ->tampil());
        }else{
            runBackground(() ->tampil2());
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
                runBackground(() ->tampil());
            }else{
                runBackground(() ->tampil2());
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt,TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            runBackground(() ->tampil());
        }else{
            runBackground(() ->tampil2());
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

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            runBackground(() ->tampil());
        }else if(TabRawat.getSelectedIndex()==1){
            runBackground(() ->tampil2());
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try{        
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='head'>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='3%'>No.</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='9%'>Tanggal</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='10%'>No.Rawat/No.Nota</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='33%'>Nama Pasien</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='15%'>Jenis/Cara Bayar</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='30%'>Akun Bayar</td>").append(
                "</tr>"
            );           
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            htmlContent=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPendapatanPerAKunClosing dialog = new DlgPendapatanPerAKunClosing(new javax.swing.JFrame(), true);
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
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel11;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label17;
    private widget.panelisi panelGlass5;
    // End of variables declaration//GEN-END:variables

    private void tampil(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        try{        
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='head'>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='3%'>No.</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='9%'>Tanggal</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='10%'>No.Rawat/No.Nota</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='33%'>Nama Pasien</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='15%'>Jenis/Cara Bayar</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='30%'>Akun Closing</td>").append(
                "</tr>"
            );   
            
            kolom=0;
            psakunbayar=koneksi.prepareStatement("select akun_bayar.nama_bayar from akun_bayar order by akun_bayar.nama_bayar");
            try {
                rsakunbayar=psakunbayar.executeQuery();
                rsakunbayar.last();
                i=rsakunbayar.getRow();
                namabayar=new String[i];
                rsakunbayar.beforeFirst();
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
            
            kolompiutang=0;
            psakunpiutang=koneksi.prepareStatement("select akun_piutang.nama_bayar from akun_piutang order by akun_piutang.nama_bayar");
            try {
                rsakunpiutang=psakunpiutang.executeQuery();
                rsakunpiutang.last();
                i=rsakunpiutang.getRow();
                namapiutang=new String[i];
                rsakunpiutang.beforeFirst();
                while(rsakunpiutang.next()){
                    namapiutang[kolompiutang]=rsakunpiutang.getString("nama_bayar");
                    kolompiutang++;
                }
            } catch (Exception e) {
                System.out.println("Akun Piutang : "+e);
            } finally{
                if(rsakunpiutang!=null){
                    rsakunpiutang.close();
                }
                if(psakunpiutang!=null){
                    psakunpiutang.close();
                }
            }
            
            totalpiutang=new double[kolompiutang]; 
            
            kolom2=0;
            psakunbayar=koneksi.prepareStatement("select rekening.kd_rek,rekening.nm_rek from rekening where rekening.kd_rek in (select kategori_pemasukan_lain.kd_rek2 from kategori_pemasukan_lain group by kategori_pemasukan_lain.kd_rek2) order by rekening.nm_rek");
            try {
                rsakunbayar=psakunbayar.executeQuery();
                rsakunbayar.last();
                i=rsakunbayar.getRow();
                akunrekening=new String[i];
                namarekening=new String[i];
                rsakunbayar.beforeFirst();
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
            
            all=0;
            ps=koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,date_format(tagihan_sadewa.tgl_bayar,'%Y-%m-%d') as tgl_bayar,tagihan_sadewa.nama_pasien "+
                    "from tagihan_sadewa where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                rs=ps.executeQuery();
                no=1;
                while(rs.next()){                            
                    norawatinap="";
                    norawatjalan="";
                    notajual="";
                    carabayar="";
                    nodeposit="";
                    notakesling="";
                    nonota=Sequel.cariIsi("select nota_inap.no_nota from nota_inap where nota_inap.no_rawat=?",rs.getString("no_nota"));
                    if(!nonota.equals("")){
                        norawatinap=rs.getString("no_nota");
                        carabayar=Sequel.cariIsi("select penjab.png_jawab from penjab inner join reg_periksa on penjab.kd_pj=reg_periksa.kd_pj where reg_periksa.no_rawat=?",rs.getString("no_nota"));
                    }else if(nonota.equals("")){
                        nonota=Sequel.cariIsi("select nota_jalan.no_nota from nota_jalan where nota_jalan.no_rawat=?",rs.getString("no_nota"));
                        if(!nonota.equals("")){
                            norawatjalan=rs.getString("no_nota");
                            carabayar=Sequel.cariIsi("select penjab.png_jawab from penjab inner join reg_periksa on penjab.kd_pj=reg_periksa.kd_pj where reg_periksa.no_rawat=?",rs.getString("no_nota"));
                        }else if(nonota.equals("")){
                            nonota=Sequel.cariIsi("select penjualan.nota_jual from penjualan where penjualan.nota_jual=?",rs.getString("no_nota"));
                            if(!nonota.equals("")){
                                notajual=rs.getString("no_nota");
                                carabayar="Penjualan Apotek";
                            }else if(nonota.equals("")){
                                nonota=Sequel.cariIsi("select deposit.no_deposit from deposit where deposit.no_deposit=?",rs.getString("no_nota"));
                                if(!nonota.equals("")){
                                    nodeposit=rs.getString("no_nota");
                                    carabayar="Deposit Pasien";
                                }else{
                                    nonota=Sequel.cariIsi("select labkesling_pembayaran_pengujian_sampel.no_pembayaran from labkesling_pembayaran_pengujian_sampel where labkesling_pembayaran_pengujian_sampel.no_pembayaran=?",rs.getString("no_nota"));
                                    if(!nonota.equals("")){
                                        notakesling=rs.getString("no_nota");
                                        carabayar="Lab Kesehatan Lingkungan";
                                    }else{
                                        notakesling="";
                                        carabayar="Transaksi Tidak Ditemukan";
                                    }
                                }
                            }                                             
                        }
                    }
                    
                    if((!carabayar.equals("Transaksi Tidak Ditemukan"))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||nonota.toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        htmlContent.append(                             
                            "<tr class='isi'>").append(
                                "<td valign='middle' align='center'>").append(no).append("</td>").append(
                                "<td valign='middle' align='center'>").append(rs.getString("tgl_bayar")).append("</td>").append(
                                "<td valign='middle' align='center'>").append(nonota).append("</td>").append(
                                "<td valign='middle' align='left'>").append(rs.getString("nama_pasien")).append("</td>").append(
                                "<td valign='middle' align='center'>").append(carabayar).append("</td>").append(
                                "<td>").append(
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                        );
                        
                        for(i=0;i<kolom;i++){
                            bayar=0;
                            if(!norawatinap.equals("")){
                                bayar=Sequel.cariIsiAngka("select sum(detail_nota_inap.besar_bayar) from detail_nota_inap where detail_nota_inap.no_rawat='"+norawatinap+"' and detail_nota_inap.nama_bayar='"+namabayar[i]+"'");
                            }else if(!norawatjalan.equals("")){
                                bayar=Sequel.cariIsiAngka("select sum(detail_nota_jalan.besar_bayar) from detail_nota_jalan where detail_nota_jalan.no_rawat='"+norawatjalan+"' and detail_nota_jalan.nama_bayar='"+namabayar[i]+"'");
                            }else if(!notajual.equals("")){
                                bayar=Sequel.cariIsiAngka("select (sum(detailjual.total)+penjualan.ongkir+penjualan.ppn) from detailjual inner join penjualan on penjualan.nota_jual=detailjual.nota_jual where penjualan.nota_jual='"+notajual+"' and penjualan.nama_bayar='"+namabayar[i]+"'");
                            }else if(!nodeposit.equals("")){
                                bayar=Sequel.cariIsiAngka("select sum(deposit.besar_deposit) from deposit where deposit.no_deposit='"+nodeposit+"' and deposit.nama_bayar='"+namabayar[i]+"'");
                            }else if(!notakesling.equals("")){
                                bayar=Sequel.cariIsiAngka("select sum(labkesling_detail_pembayaran_pengujian_sampel.besar_bayar) from labkesling_detail_pembayaran_pengujian_sampel where labkesling_detail_pembayaran_pengujian_sampel.no_pembayaran='"+notakesling+"' and labkesling_detail_pembayaran_pengujian_sampel.nama_bayar='"+namabayar[i]+"'");
                            }else{
                                bayar=0;
                            }
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(namabayar[i]).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Valid.SetAngka(bayar)).append("</td></tr>");
                            }
                            all=all+bayar;
                            totalbayar[i]=totalbayar[i]+bayar;
                        }
                        
                        for(i=0;i<kolompiutang;i++){
                            bayar=Sequel.cariIsiAngka("select sum(detail_piutang_pasien.totalpiutang) from detail_piutang_pasien where detail_piutang_pasien.no_rawat='"+rs.getString("no_nota")+"' and detail_piutang_pasien.nama_bayar='"+namapiutang[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(namapiutang[i]).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Valid.SetAngka(bayar)).append("</td></tr>");
                            }
                            all=all+bayar;
                            totalpiutang[i]=totalpiutang[i]+bayar;
                        }
                        
                        htmlContent.append( 
                                    "</table>").append(
                                "</td>").append(
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
            
            //pemasukanlain2
            ps= koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,date_format(tagihan_sadewa.tgl_bayar,'%Y-%m-%d') as tgl_bayar,tagihan_sadewa.nama_pasien "+
                    "from tagihan_sadewa inner join pemasukan_lain on tagihan_sadewa.no_nota=pemasukan_lain.no_masuk "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                rs=ps.executeQuery();
                while(rs.next()){                            
                    if((rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||nonota.toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        htmlContent.append(                             
                            "<tr class='isi'>").append(
                                "<td valign='middle' align='center'>").append(no).append("</td>").append(
                                "<td valign='middle' align='center'>").append(rs.getString("tgl_bayar")).append("</td>").append(
                                "<td valign='middle' align='center'>").append(nonota).append("</td>").append(
                                "<td valign='middle' align='left'>").append(rs.getString("nama_pasien")).append("</td>").append(
                                "<td valign='middle' align='center'>Pemasukan Lain-lain</td>").append(
                                "<td>").append(
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom2;i++){
                            bayar=Sequel.cariIsiAngka("select sum(pemasukan_lain.besar) from pemasukan_lain inner join kategori_pemasukan_lain on kategori_pemasukan_lain.kode_kategori=pemasukan_lain.kode_kategori where pemasukan_lain.no_masuk='"+rs.getString("no_nota")+"' and kategori_pemasukan_lain.kd_rek2='"+akunrekening[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(namarekening[i]).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Valid.SetAngka(bayar)).append("</td></tr>");
                            }
                            all=all+bayar;
                            totalbayar2[i]=totalbayar2[i]+bayar;
                        }
                        htmlContent.append( 
                                    "</table>").append(
                                "</td>").append(
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
            
            ps=koneksi.prepareStatement(
                "select piutang_pasien.no_rawat,piutang_pasien.tgl_piutang,pasien.nm_pasien from piutang_pasien inner join pasien on piutang_pasien.no_rkm_medis=pasien.no_rkm_medis "+
                "where piutang_pasien.uangmuka='0' and piutang_pasien.tgl_piutang between ? and ? "+(!TCari.getText().trim().equals("")?" and (piutang_pasien.no_rawat like ? or pasien.nm_pasien like ?) ":"")+
                "order by piutang_pasien.tgl_piutang,piutang_pasien.no_rawat");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'>").append(no).append("</td>").append(
                            "<td valign='middle' align='center'>").append(rs.getString("tgl_piutang")).append("</td>").append(
                            "<td valign='middle' align='center'>").append(rs.getString("no_rawat")).append("</td>").append(
                            "<td valign='middle' align='left'>").append(rs.getString("nm_pasien")).append("</td>").append(
                            "<td valign='middle' align='center'>Piutang Perawatan</td>").append(
                            "<td>").append(
                                "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                    );

                    for(i=0;i<kolompiutang;i++){
                        bayar=Sequel.cariIsiAngka("select sum(detail_piutang_pasien.totalpiutang) from detail_piutang_pasien where detail_piutang_pasien.no_rawat='"+rs.getString("no_rawat")+"' and detail_piutang_pasien.nama_bayar='"+namapiutang[i]+"'");
                        if(bayar>0){
                            htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(namapiutang[i]).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Valid.SetAngka(bayar)).append("</td></tr>");
                        }
                        all=all+bayar;
                        totalpiutang[i]=totalpiutang[i]+bayar;
                    }

                    htmlContent.append( 
                                "</table>").append(
                            "</td>").append(
                        "</tr>"
                    ); 
                    no++;                            
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
            
            ps=koneksi.prepareStatement(
                "select piutang.nota_piutang,piutang.tgl_piutang,piutang.nm_pasien,piutang.sisapiutang from piutang "+
                "where piutang.tgl_piutang between ? and ? "+(!TCari.getText().trim().equals("")?" and (piutang.nota_piutang like ? or pasien.nm_pasien like ?) ":"")+
                "order by piutang.tgl_piutang,piutang.nota_piutang");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                piutangobat=0;
                while(rs.next()){
                    piutangobat=piutangobat+rs.getDouble("sisapiutang");
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'>").append(no).append("</td>").append(
                            "<td valign='middle' align='center'>").append(rs.getString("tgl_piutang")).append("</td>").append(
                            "<td valign='middle' align='center'>").append(rs.getString("nota_piutang")).append("</td>").append(
                            "<td valign='middle' align='left'>").append(rs.getString("nm_pasien")).append("</td>").append(
                            "<td valign='middle' align='center'>Piutang Obat</td>").append(
                            "<td>").append(
                                "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                    "<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(akunpiutangobat).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Valid.SetAngka(rs.getDouble("sisapiutang"))).append("</td></tr>").append(
                                "</table>").append(
                            "</td>").append(
                        "</tr>"
                    ); 
                    no++;                            
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
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'></td>").append(
                            "<td valign='middle' align='left' colspan='4'>Total ").append(namabayar[i]).append("</td>").append(
                            "<td valign='middle' align='right'>").append(Valid.SetAngka(totalbayar[i])).append("</td>").append(
                        "</tr>"
                    ); 
                }  
            }
            
            for(i=0;i<kolom2;i++){
                if(totalbayar2[i]>0){
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'></td>").append(
                            "<td valign='middle' align='left' colspan='4'>Total ").append(namarekening[i]).append("</td>").append(
                            "<td valign='middle' align='right'>").append(Valid.SetAngka(totalbayar2[i])).append("</td>").append(
                        "</tr>"
                    ); 
                }  
            }
            
            for(i=0;i<kolompiutang;i++){
                if(totalpiutang[i]>0){
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'></td>").append(
                            "<td valign='middle' align='left' colspan='4'>Total ").append(namapiutang[i]).append("</td>").append(
                            "<td valign='middle' align='right'>").append(Valid.SetAngka(totalpiutang[i])).append("</td>").append(
                        "</tr>"
                    ); 
                }  
            }
            
            if(piutangobat>0){
                htmlContent.append(                             
                    "<tr class='isi'>").append(
                        "<td valign='middle' align='center'></td>").append(
                        "<td valign='middle' align='left' colspan='4'>Total ").append(akunpiutangobat).append("</td>").append(
                        "<td valign='middle' align='right'>").append(Valid.SetAngka(piutangobat)).append("</td>").append(
                    "</tr>"
                ); 
            }
            
            if(all>0){
                htmlContent.append(                             
                    "<tr class='isi'>").append(
                        "<td valign='middle' align='center'></td>").append(
                        "<td valign='middle' align='left' colspan='4'><b>Jumlah Total<b></td>").append(
                        "<td valign='middle' align='right'><b>").append(Valid.SetAngka(all+piutangobat)).append("<b></td>").append(
                    "</tr>"
                ); 
            }
                         
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            htmlContent=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }    
    
    private void tampil2(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        try{        
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='head'>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='3%'>No.</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='9%'>Tanggal</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='10%'>No.Rawat/No.Nota</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='33%'>Nama Pasien</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='15%'>Jenis/Cara Bayar</td>").append(
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='30%'>Akun Closing</td>").append(
                "</tr>"
            );   
            
            kolom=0;
            psakunbayar=koneksi.prepareStatement("select akun_bayar.nama_bayar from akun_bayar order by akun_bayar.nama_bayar");
            try {
                rsakunbayar=psakunbayar.executeQuery();
                rsakunbayar.last();
                i=rsakunbayar.getRow();
                namabayar=new String[i];
                rsakunbayar.beforeFirst();
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
            
            kolompiutang=0;
            psakunpiutang=koneksi.prepareStatement("select akun_piutang.nama_bayar from akun_piutang order by akun_piutang.nama_bayar");
            try {
                rsakunpiutang=psakunpiutang.executeQuery();
                rsakunpiutang.last();
                i=rsakunpiutang.getRow();
                namapiutang=new String[i];
                rsakunpiutang.beforeFirst();
                while(rsakunpiutang.next()){
                    namapiutang[kolompiutang]=rsakunpiutang.getString("nama_bayar");
                    kolompiutang++;
                }
            } catch (Exception e) {
                System.out.println("Akun Piutang : "+e);
            } finally{
                if(rsakunpiutang!=null){
                    rsakunpiutang.close();
                }
                if(psakunpiutang!=null){
                    psakunpiutang.close();
                }
            }
            
            totalpiutang=new double[kolompiutang]; 
            
            kolom2=0;
            psakunbayar=koneksi.prepareStatement("select rekening.kd_rek,rekening.nm_rek from rekening where rekening.kd_rek in (select kategori_pemasukan_lain.kd_rek2 from kategori_pemasukan_lain group by kategori_pemasukan_lain.kd_rek2) order by rekening.nm_rek");
            try {
                rsakunbayar=psakunbayar.executeQuery();
                rsakunbayar.last();
                i=rsakunbayar.getRow();
                akunrekening=new String[i];
                namarekening=new String[i];
                rsakunbayar.beforeFirst();
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
            
            all=0;
            ps=koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,date_format(tagihan_sadewa.tgl_bayar,'%Y-%m-%d') as tgl_bayar,tagihan_sadewa.nama_pasien "+
                    "from tagihan_sadewa where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                rs=ps.executeQuery();
                no=1;
                while(rs.next()){                            
                    norawatinap="";
                    norawatjalan="";
                    notajual="";
                    carabayar="";
                    nodeposit="";
                    notakesling="";
                    nonota=Sequel.cariIsi("select nota_inap.no_nota from nota_inap where nota_inap.no_rawat=?",rs.getString("no_nota"));
                    if(!nonota.equals("")){
                        norawatinap=rs.getString("no_nota");
                        carabayar=Sequel.cariIsi("select penjab.png_jawab from penjab inner join reg_periksa on penjab.kd_pj=reg_periksa.kd_pj where reg_periksa.no_rawat=?",rs.getString("no_nota"));
                    }else if(nonota.equals("")){
                        nonota=Sequel.cariIsi("select nota_jalan.no_nota from nota_jalan where nota_jalan.no_rawat=?",rs.getString("no_nota"));
                        if(!nonota.equals("")){
                            norawatjalan=rs.getString("no_nota");
                            carabayar=Sequel.cariIsi("select penjab.png_jawab from penjab inner join reg_periksa on penjab.kd_pj=reg_periksa.kd_pj where reg_periksa.no_rawat=?",rs.getString("no_nota"));
                        }else if(nonota.equals("")){
                            nonota=Sequel.cariIsi("select penjualan.nota_jual from penjualan where penjualan.nota_jual=?",rs.getString("no_nota"));
                            if(!nonota.equals("")){
                                notajual=rs.getString("no_nota");
                                carabayar="Penjualan Apotek";
                            }else if(nonota.equals("")){
                                nonota=Sequel.cariIsi("select deposit.no_deposit from deposit where deposit.no_deposit=?",rs.getString("no_nota"));
                                if(!nonota.equals("")){
                                    nodeposit=rs.getString("no_nota");
                                    carabayar="Deposit Pasien";
                                }else{
                                    nonota=Sequel.cariIsi("select labkesling_pembayaran_pengujian_sampel.no_pembayaran from labkesling_pembayaran_pengujian_sampel where labkesling_pembayaran_pengujian_sampel.no_pembayaran=?",rs.getString("no_nota"));
                                    if(!nonota.equals("")){
                                        notakesling=rs.getString("no_nota");
                                        carabayar="Lab Kesehatan Lingkungan";
                                    }else{
                                        notakesling="";
                                        carabayar="Transaksi Tidak Ditemukan";
                                    }
                                }
                            }                                             
                        }
                    }
                    
                    if((!carabayar.equals("Transaksi Tidak Ditemukan"))&&(rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||nonota.toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        htmlContent.append(                             
                            "<tr class='isi'>").append(
                                "<td valign='middle' align='center'>").append(no).append("</td>").append(
                                "<td valign='middle' align='center'>").append(rs.getString("tgl_bayar")).append("</td>").append(
                                "<td valign='middle' align='center'>").append(nonota).append("</td>").append(
                                "<td valign='middle' align='left'>").append(rs.getString("nama_pasien")).append("</td>").append(
                                "<td valign='middle' align='center'>").append(carabayar).append("</td>").append(
                                "<td>").append(
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                        );
                        
                        for(i=0;i<kolom;i++){
                            bayar=0;
                            if(!norawatinap.equals("")){
                                bayar=Sequel.cariIsiAngka("select sum(detail_nota_inap.besar_bayar) from detail_nota_inap where detail_nota_inap.no_rawat='"+norawatinap+"' and detail_nota_inap.nama_bayar='"+namabayar[i]+"'");
                            }else if(!norawatjalan.equals("")){
                                bayar=Sequel.cariIsiAngka("select sum(detail_nota_jalan.besar_bayar) from detail_nota_jalan where detail_nota_jalan.no_rawat='"+norawatjalan+"' and detail_nota_jalan.nama_bayar='"+namabayar[i]+"'");
                            }else if(!notajual.equals("")){
                                bayar=Sequel.cariIsiAngka("select (sum(detailjual.total)+penjualan.ongkir+penjualan.ppn) from detailjual inner join penjualan on penjualan.nota_jual=detailjual.nota_jual where penjualan.nota_jual='"+notajual+"' and penjualan.nama_bayar='"+namabayar[i]+"'");
                            }else if(!nodeposit.equals("")){
                                bayar=Sequel.cariIsiAngka("select sum(deposit.besar_deposit) from deposit where deposit.no_deposit='"+nodeposit+"' and deposit.nama_bayar='"+namabayar[i]+"'");
                            }else if(!notakesling.equals("")){
                                bayar=Sequel.cariIsiAngka("select sum(labkesling_detail_pembayaran_pengujian_sampel.besar_bayar) from labkesling_detail_pembayaran_pengujian_sampel where labkesling_detail_pembayaran_pengujian_sampel.no_pembayaran='"+notakesling+"' and labkesling_detail_pembayaran_pengujian_sampel.nama_bayar='"+namabayar[i]+"'");
                            }else{
                                bayar=0;
                            }
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(namabayar[i]).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Math.round(bayar)).append("</td></tr>");
                            }
                            all=all+bayar;
                            totalbayar[i]=totalbayar[i]+bayar;
                        }
                        
                        for(i=0;i<kolompiutang;i++){
                            bayar=Sequel.cariIsiAngka("select sum(detail_piutang_pasien.totalpiutang) from detail_piutang_pasien where detail_piutang_pasien.no_rawat='"+rs.getString("no_nota")+"' and detail_piutang_pasien.nama_bayar='"+namapiutang[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(namapiutang[i]).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Math.round(bayar)).append("</td></tr>");
                            }
                            all=all+bayar;
                            totalpiutang[i]=totalpiutang[i]+bayar;
                        }
                        
                        htmlContent.append( 
                                    "</table>").append(
                                "</td>").append(
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
            
            //pemasukanlain2
            ps= koneksi.prepareStatement(
                    "select tagihan_sadewa.no_nota,date_format(tagihan_sadewa.tgl_bayar,'%Y-%m-%d') as tgl_bayar,tagihan_sadewa.nama_pasien "+
                    "from tagihan_sadewa inner join pemasukan_lain on tagihan_sadewa.no_nota=pemasukan_lain.no_masuk "+
                    "where tagihan_sadewa.tgl_bayar between ? and ? order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                rs=ps.executeQuery();
                while(rs.next()){                            
                    if((rs.getString("nama_pasien").toLowerCase().trim().contains(TCari.getText().toLowerCase().trim())||nonota.toLowerCase().trim().contains(TCari.getText().toLowerCase().trim()))){
                        htmlContent.append(                             
                            "<tr class='isi'>").append(
                                "<td valign='middle' align='center'>").append(no).append("</td>").append(
                                "<td valign='middle' align='center'>").append(rs.getString("tgl_bayar")).append("</td>").append(
                                "<td valign='middle' align='center'>").append(nonota).append("</td>").append(
                                "<td valign='middle' align='left'>").append(rs.getString("nama_pasien")).append("</td>").append(
                                "<td valign='middle' align='center'>Pemasukan Lain-lain</td>").append(
                                "<td>").append(
                                    "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>");
                        for(i=0;i<kolom2;i++){
                            bayar=Sequel.cariIsiAngka("select sum(pemasukan_lain.besar) from pemasukan_lain inner join kategori_pemasukan_lain on kategori_pemasukan_lain.kode_kategori=pemasukan_lain.kode_kategori where pemasukan_lain.no_masuk='"+rs.getString("no_nota")+"' and kategori_pemasukan_lain.kd_rek2='"+akunrekening[i]+"'");
                            if(bayar>0){
                                htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(namarekening[i]).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Math.round(bayar)).append("</td></tr>");
                            }
                            all=all+bayar;
                            totalbayar2[i]=totalbayar2[i]+bayar;
                        }
                        htmlContent.append( 
                                    "</table>").append(
                                "</td>").append(
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
            
            ps=koneksi.prepareStatement(
                "select piutang_pasien.no_rawat,piutang_pasien.tgl_piutang,pasien.nm_pasien from piutang_pasien inner join pasien on piutang_pasien.no_rkm_medis=pasien.no_rkm_medis "+
                "where piutang_pasien.uangmuka='0' and piutang_pasien.tgl_piutang between ? and ? "+(!TCari.getText().trim().equals("")?" and (piutang_pasien.no_rawat like ? or pasien.nm_pasien like ?) ":"")+
                "order by piutang_pasien.tgl_piutang,piutang_pasien.no_rawat");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'>").append(no).append("</td>").append(
                            "<td valign='middle' align='center'>").append(rs.getString("tgl_piutang")).append("</td>").append(
                            "<td valign='middle' align='center'>").append(rs.getString("no_rawat")).append("</td>").append(
                            "<td valign='middle' align='left'>").append(rs.getString("nm_pasien")).append("</td>").append(
                            "<td valign='middle' align='center'>Piutang Perawatan</td>").append(
                            "<td>").append(
                                "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                    );

                    for(i=0;i<kolompiutang;i++){
                        bayar=Sequel.cariIsiAngka("select sum(detail_piutang_pasien.totalpiutang) from detail_piutang_pasien where detail_piutang_pasien.no_rawat='"+rs.getString("no_rawat")+"' and detail_piutang_pasien.nama_bayar='"+namapiutang[i]+"'");
                        if(bayar>0){
                            htmlContent.append("<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(namapiutang[i]).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Math.round(bayar)).append("</td></tr>");
                        }
                        all=all+bayar;
                        totalpiutang[i]=totalpiutang[i]+bayar;
                    }

                    htmlContent.append( 
                                "</table>").append(
                            "</td>").append(
                        "</tr>"
                    ); 
                    no++;                            
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
            
            ps=koneksi.prepareStatement(
                "select piutang.nota_piutang,piutang.tgl_piutang,piutang.nm_pasien,piutang.sisapiutang from piutang "+
                "where piutang.tgl_piutang between ? and ? "+(!TCari.getText().trim().equals("")?" and (piutang.nota_piutang like ? or pasien.nm_pasien like ?) ":"")+
                "order by piutang.tgl_piutang,piutang.nota_piutang");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                piutangobat=0;
                while(rs.next()){
                    piutangobat=piutangobat+rs.getDouble("sisapiutang");
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'>").append(no).append("</td>").append(
                            "<td valign='middle' align='center'>").append(rs.getString("tgl_piutang")).append("</td>").append(
                            "<td valign='middle' align='center'>").append(rs.getString("nota_piutang")).append("</td>").append(
                            "<td valign='middle' align='left'>").append(rs.getString("nm_pasien")).append("</td>").append(
                            "<td valign='middle' align='center'>Piutang Obat</td>").append(
                            "<td>").append(
                                "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                    "<tr class='isi'><td valign='middle' width='70%' align='left' border='0'>").append(akunpiutangobat).append("</td><td valign='middle' width='30%' align='right' border='0'>").append(Math.round(rs.getDouble("sisapiutang"))).append("</td></tr>").append(
                                "</table>").append(
                            "</td>").append(
                        "</tr>"
                    ); 
                    no++;                            
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
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'></td>").append(
                            "<td valign='middle' align='left' colspan='4'>Total ").append(namabayar[i]).append("</td>").append(
                            "<td valign='middle' align='right'>").append(Math.round(totalbayar[i])).append("</td>").append(
                        "</tr>"
                    ); 
                }  
            }
            
            for(i=0;i<kolom2;i++){
                if(totalbayar2[i]>0){
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'></td>").append(
                            "<td valign='middle' align='left' colspan='4'>Total ").append(namarekening[i]).append("</td>").append(
                            "<td valign='middle' align='right'>").append(Math.round(totalbayar2[i])).append("</td>").append(
                        "</tr>"
                    ); 
                }  
            }
            
            for(i=0;i<kolompiutang;i++){
                if(totalpiutang[i]>0){
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='middle' align='center'></td>").append(
                            "<td valign='middle' align='left' colspan='4'>Total ").append(namapiutang[i]).append("</td>").append(
                            "<td valign='middle' align='right'>").append(Math.round(totalpiutang[i])).append("</td>").append(
                        "</tr>"
                    ); 
                }  
            }
            
            if(piutangobat>0){
                htmlContent.append(                             
                    "<tr class='isi'>").append(
                        "<td valign='middle' align='center'></td>").append(
                        "<td valign='middle' align='left' colspan='4'>Total ").append(akunpiutangobat).append("</td>").append(
                        "<td valign='middle' align='right'>").append(Math.round(piutangobat)).append("</td>").append(
                    "</tr>"
                ); 
            }
            
            if(all>0){
                htmlContent.append(                             
                    "<tr class='isi'>").append(
                        "<td valign='middle' align='center'></td>").append(
                        "<td valign='middle' align='left' colspan='4'><b>Jumlah Total<b></td>").append(
                        "<td valign='middle' align='right'><b>").append(Math.round(all+piutangobat)).append("<b></td>").append(
                    "</tr>"
                ); 
            }
                         
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            htmlContent=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    } 
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        ceksukses = true;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        executor.submit(() -> {
            try {
                task.run();
            } finally {
                ceksukses = false;
                SwingUtilities.invokeLater(() -> {
                    this.setCursor(Cursor.getDefaultCursor());
                });
            }
        });
    }
}

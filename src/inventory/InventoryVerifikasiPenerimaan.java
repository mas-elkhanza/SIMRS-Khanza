package inventory;
import fungsi.batasInput;
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
import kepegawaian.DlgCariPetugas;

public class InventoryVerifikasiPenerimaan extends javax.swing.JDialog {
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  InventoryCariSuplier suplier=new InventoryCariSuplier(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private PreparedStatement ps,pssub,pspenerimaan,pssubpenerimaan,pssisipan;
    private ResultSet rs,rssub,rspenerimaan,rssubpenerimaan,rssisipan;
    private double tagihan=0;
    private StringBuilder htmlContent;
    private String carifaktur="",carisuplier="",caripegawai="",carikeyword="",satuan,jumlah="0",status="";
    private double h_pesan=0,subtotal=0,dis=0,besardis=0,total=0,index=0,jumlah2=0,sisipantotal1=0,sisipanpotongan=0,
            sisipantotal2=0,indextotal=0,jmltotaltagihan=0,jmlsisipan=0,jmlharikirim=0,jmlindextotal=0;
    private int i=0,jmlfaktur=0,faktursisipan=0,jmli=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public InventoryVerifikasiPenerimaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        NoFaktur.setDocument(new batasInput((byte)25).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));  
        
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
        
        suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(suplier.getTable().getSelectedRow()!= -1){                   
                        kdsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),0).toString());                    
                        nmsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
                    }  
                    kdsup.requestFocus();
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
        
        suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        suplier.dispose();
                    }    
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }            
                    kdptg.requestFocus();
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
        
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
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

        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label11 = new widget.Label();
        TglBeli1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label12 = new widget.Label();
        TglBeli2 = new widget.Tanggal();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Verifikasi Penerimaan Obat/Alkes/BHP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Faktur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 80, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(84, 10, 219, 23);

        label11.setText("Tgl.Datang :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 80, 23);

        TglBeli1.setDisplayFormat("dd-MM-yyyy");
        TglBeli1.setName("TglBeli1"); // NOI18N
        TglBeli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli1KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli1);
        TglBeli1.setBounds(84, 40, 95, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(305, 10, 80, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(305, 40, 80, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(389, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(389, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(471, 10, 260, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(471, 40, 260, 23);

        btnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSuplier.setMnemonic('1');
        btnSuplier.setToolTipText("Alt+1");
        btnSuplier.setName("btnSuplier"); // NOI18N
        btnSuplier.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierActionPerformed(evt);
            }
        });
        panelisi3.add(btnSuplier);
        btnSuplier.setBounds(734, 10, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(734, 40, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(179, 40, 27, 23);

        TglBeli2.setDisplayFormat("dd-MM-yyyy");
        TglBeli2.setName("TglBeli2"); // NOI18N
        TglBeli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli2KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli2);
        TglBeli2.setBounds(208, 40, 95, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll.setViewportView(LoadHTML);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(290, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelisi1.add(BtnCari);

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 30));
        panelisi1.add(label9);

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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        suplier.dispose();
        petugas.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        akses.setform("DlgCariPemesanan");
        suplier.emptTeks();
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgCariPemesanan");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void TglBeli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli1KeyPressed
        Valid.pindah(evt,NoFaktur,kdsup);
    }//GEN-LAST:event_TglBeli1KeyPressed

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSuplierActionPerformed(null);
        }
    }//GEN-LAST:event_kdsupKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnKeluar, kdsup);
    }//GEN-LAST:event_NoFakturKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());     
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            TCari.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void TglBeli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBeli2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugas.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        NoFaktur.setText("");
        kdsup.setText("");
        nmsup.setText("");
        kdptg.setText("");
        nmptg.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

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
            
            File f = new File("VerifikasiFaktur.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));    
            if(internalFrame1.getWidth()>1370){
                bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA VERIFIKASI PENERIMAAN OBAT/ALKES/BHP<br>PERIODE "+TglBeli1.getSelectedItem()+" s.d. "+TglBeli2.getSelectedItem()+"<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();
            }else{
                bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='1050' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA VERIFIKASI PENERIMAAN OBAT/ALKES/BHP<br>PERIODE "+TglBeli1.getSelectedItem()+" s.d. "+TglBeli2.getSelectedItem()+"<br><br></font>"+        
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
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        htmlContent = new StringBuilder();
        htmlContent.append(                             
            "<tr class='isi'>"+
                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%' colspan='9'>Data Surat Pemesanan Obat, Alkes & BHP Medis</td>"+
                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%' colspan='7'>Data Penerimaan Obat, Alkes & BHP Medis</td>"+
                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%'>Keterangan</td>"+
            "</tr>");
        LoadHTML.setText(
            "<html>"+
              "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
               htmlContent.toString()+
              "</table>"+
            "</html>");
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventoryVerifikasiPenerimaan dialog = new InventoryVerifikasiPenerimaan(new javax.swing.JFrame(), true);
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
    private widget.TextBox NoFaktur;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal TglBeli1;
    private widget.Tanggal TglBeli2;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label9;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%' colspan='9'>Data Surat Pemesanan Obat, Alkes & BHP Medis</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%' colspan='7'>Data Penerimaan Obat, Alkes & BHP Medis</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%'>Keterangan</td>"+
                "</tr>");
            
            carifaktur="";carisuplier="";caripegawai="";carikeyword="";
            if(!NoFaktur.getText().trim().equals("")){
                carifaktur=" and surat_pemesanan_medis.no_pemesanan='"+NoFaktur.getText()+"' ";
            }
            if(!nmsup.getText().trim().equals("")){
                carisuplier=" and surat_pemesanan_medis.kode_suplier='"+kdsup.getText()+"' ";
            }
            if(!nmptg.getText().trim().equals("")){
                caripegawai=" and surat_pemesanan_medis.nip='"+kdptg.getText()+"' ";
            }
            if(!TCari.getText().trim().equals("")){
                carikeyword=" and (surat_pemesanan_medis.no_pemesanan like '%"+TCari.getText().trim()+"%' or "+
                            " surat_pemesanan_medis.kode_suplier like '%"+TCari.getText().trim()+"%' or "+
                            " datasuplier.nama_suplier like '%"+TCari.getText().trim()+"%' or "+
                            " surat_pemesanan_medis.nip like '%"+TCari.getText().trim()+"%' or "+
                            " pegawai.nama like '%"+TCari.getText().trim()+"%') ";
            }
            
            ps=koneksi.prepareStatement("select date_format(surat_pemesanan_medis.tanggal,'%d/%m/%Y') as tanggal,surat_pemesanan_medis.no_pemesanan, "+
                    "surat_pemesanan_medis.kode_suplier,datasuplier.nama_suplier,surat_pemesanan_medis.nip,pegawai.nama,surat_pemesanan_medis.total1,"+
                    "surat_pemesanan_medis.status,surat_pemesanan_medis.total2,surat_pemesanan_medis.ppn,surat_pemesanan_medis.meterai,"+
                    "surat_pemesanan_medis.potongan,surat_pemesanan_medis.tanggal as tglpesan,surat_pemesanan_medis.tagihan from surat_pemesanan_medis "+
                    "inner join datasuplier on surat_pemesanan_medis.kode_suplier=datasuplier.kode_suplier "+
                    "inner join pegawai on surat_pemesanan_medis.nip=pegawai.nik where surat_pemesanan_medis.status='Sudah Datang' "+
                    "and surat_pemesanan_medis.tanggal between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' "+
                    carifaktur+carisuplier+caripegawai+carikeyword+" order by surat_pemesanan_medis.tanggal,surat_pemesanan_medis.no_pemesanan ");
            jmltotaltagihan=0;jmlfaktur=0;jmlsisipan=0;faktursisipan=0;jmlharikirim=0;jmlindextotal=0;jmli=0;
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    pspenerimaan=koneksi.prepareStatement(
                            "select date_format(pemesanan.tgl_pesan,'%d/%m/%Y') as tanggal,pemesanan.no_faktur,pemesanan.total1,pemesanan.potongan, "+
                            "pemesanan.nip,petugas.nama,bangsal.nm_bangsal,date_format(pemesanan.tgl_faktur,'%d/%m/%Y') as tgl_faktur, "+
                            "date_format(pemesanan.tgl_tempo,'%d/%m/%Y') as tgl_tempo,pemesanan.status,pemesanan.total2,pemesanan.ppn,"+
                            "pemesanan.meterai,pemesanan.tagihan,pemesanan.no_order,(to_days(pemesanan.tgl_pesan)-to_days('"+rs.getString("tglpesan")+"')) as lama "+
                            "from pemesanan inner join petugas on pemesanan.nip=petugas.nip "+
                            "inner join bangsal on pemesanan.kd_bangsal=bangsal.kd_bangsal "+
                            "where pemesanan.no_order='"+rs.getString("no_pemesanan")+"'");
                    try {
                        rspenerimaan=pspenerimaan.executeQuery();
                        if(rspenerimaan.next()){
                           indextotal=0;i=0;
                           jmlharikirim=jmlharikirim+rspenerimaan.getInt("lama");
                           htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='left' colspan='2'>No.Pemesanan</td>"+
                                    "<td valign='middle' colspan='7'>: "+rs.getString("no_pemesanan")+"</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='left' colspan='3'>No.Faktur</td>"+
                                    "<td valign='middle' colspan='4'>: "+rspenerimaan.getString("no_faktur")+"</td>"+
                                    "<td valign='middle' align='center'>Tgl.Faktur : "+rspenerimaan.getString("tgl_faktur")+"</td>"+
                                "</tr>"+
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='left' colspan='2'>Tgl.Pemesanan</td>"+
                                    "<td valign='middle' colspan='7'>: "+rs.getString("tanggal")+"</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='left' colspan='3'>Tgl.Penerimaan</td>"+
                                    "<td valign='middle' colspan='4'>: "+rspenerimaan.getString("tanggal")+"</td>"+
                                    "<td valign='middle' align='center'>Lama : "+rspenerimaan.getString("lama")+" Hari</td>"+
                                "</tr>"+
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='left' colspan='2'>Suplier</td>"+
                                    "<td valign='middle' colspan='7'>: "+rs.getString("nama_suplier")+"</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='left' colspan='3'>Jatuh Tempo</td>"+
                                    "<td valign='middle' colspan='4'>: "+rspenerimaan.getString("tgl_tempo")+"</td>"+
                                    "<td valign='middle' align='center'>Status : "+rspenerimaan.getString("status")+"</td>"+
                                "</tr>"+
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='left' colspan='2'>Petugas</td>"+
                                    "<td valign='middle' colspan='7'>: "+rs.getString("nip")+" "+rs.getString("nama")+"</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='left' colspan='3'>Penerima</td>"+
                                    "<td valign='middle' colspan='4'>: "+rspenerimaan.getString("nip")+" "+rspenerimaan.getString("nama")+"</td>"+
                                    "<td valign='middle' align='center'>Depo : "+rspenerimaan.getString("nm_bangsal")+"</td>"+
                                "</tr>"+                          
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='5%'>Kode Barang</td>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='14%'>Nama Barang</td>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='3%'>Satuan</td>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='1%'>Jml</td>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='5%'>Harga(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='6%'>SubTotal(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%'>Disk(%)</td>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='5%'>Disk(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='7%'>Total(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='center' width='3%'>Satuan</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='center' width='2%'>Jml</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='center' width='5%'>Harga(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='center' width='7%'>SubTotal(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='center' width='4%'>Disk(%)</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='center' width='6%'>Disk(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFF8FF' align='center' width='8%'>Total(Rp)</td>"+
                                    "<td valign='middle' bgcolor='#FFF8F8' align='center' width='15%'>Index Ketepatan</td>"+
                                "</tr>");
                            pssub=koneksi.prepareStatement(
                                    "select detail_surat_pemesanan_medis.kode_brng,databarang.nama_brng,detail_surat_pemesanan_medis.jumlah2,"+
                                    "kodesatuan.satuan,detail_surat_pemesanan_medis.jumlah,detail_surat_pemesanan_medis.h_pesan, "+
                                    "detail_surat_pemesanan_medis.subtotal,detail_surat_pemesanan_medis.dis,detail_surat_pemesanan_medis.besardis,detail_surat_pemesanan_medis.total "+
                                    "from detail_surat_pemesanan_medis inner join databarang on detail_surat_pemesanan_medis.kode_brng=databarang.kode_brng "+
                                    "inner join kodesatuan on detail_surat_pemesanan_medis.kode_sat=kodesatuan.kode_sat where detail_surat_pemesanan_medis.no_pemesanan='"+rs.getString("no_pemesanan")+"'");
                            try {
                                rssub=pssub.executeQuery();
                                while(rssub.next()){
                                    satuan=rssub.getString("satuan");jumlah="0";h_pesan=0;subtotal=0;dis=0;besardis=0;total=0;status="";index=0;jumlah2=0;
                                    pssubpenerimaan=koneksi.prepareStatement(
                                        "select kodesatuan.satuan,detailpesan.jumlah,detailpesan.h_pesan,detailpesan.jumlah2,"+
                                        "detailpesan.subtotal,detailpesan.dis,detailpesan.besardis,detailpesan.total "+
                                        "from detailpesan inner join kodesatuan on detailpesan.kode_sat=kodesatuan.kode_sat "+
                                        "where detailpesan.no_faktur='"+rspenerimaan.getString("no_faktur")+"' and detailpesan.kode_brng='"+rssub.getString("kode_brng")+"'");
                                    try {
                                        rssubpenerimaan=pssubpenerimaan.executeQuery();
                                        if(rssubpenerimaan.next()){
                                            satuan=rssubpenerimaan.getString("satuan");
                                            jumlah=rssubpenerimaan.getString("jumlah");
                                            h_pesan=rssubpenerimaan.getDouble("h_pesan");
                                            subtotal=rssubpenerimaan.getDouble("subtotal");
                                            dis=rssubpenerimaan.getDouble("dis");
                                            besardis=rssubpenerimaan.getDouble("besardis");
                                            total=rssubpenerimaan.getDouble("total");
                                            jumlah2=rssubpenerimaan.getDouble("jumlah2");
                                        }
                                        
                                        if(jumlah2==rssub.getDouble("jumlah2")){
                                            status="Tepat";
                                            index=2;
                                        }else if(jumlah2==0){
                                            status="Kosong";
                                            index=0;
                                        }else if(jumlah2>rssub.getDouble("jumlah2")){
                                            status="Lebih";
                                            index=1;
                                        }else if(jumlah2<rssub.getDouble("jumlah2")){
                                            status="Kurang";
                                            index=1;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif Sub Penerimaan : "+e);
                                    } finally{
                                        if(rssubpenerimaan!=null){
                                            rssubpenerimaan.close();
                                        }
                                        if(pssubpenerimaan!=null){
                                            pssubpenerimaan.close();
                                        }
                                    }
                                    indextotal=indextotal+index;
                                    jmlindextotal=jmlindextotal+index;
                                    htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td align='center'>"+rssub.getString("kode_brng")+"</td>"+
                                                "<td align='left'>"+rssub.getString("nama_brng")+"</td>"+
                                                "<td align='center'>"+rssub.getString("satuan")+"</td>"+
                                                "<td align='center'>"+rssub.getString("jumlah")+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(rssub.getDouble("h_pesan"))+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(rssub.getDouble("subtotal"))+"</td>"+
                                                "<td align='center'>"+Valid.SetAngka(rssub.getDouble("dis"))+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(rssub.getDouble("besardis"))+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(rssub.getDouble("total"))+"</td>"+
                                                "<td align='center'>"+satuan+"</td>"+
                                                "<td align='center'>"+jumlah+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(h_pesan)+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                                                "<td align='center'>"+Valid.SetAngka(dis)+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(besardis)+"</td>"+
                                                "<td align='right'>"+Valid.SetAngka(total)+"</td>"+
                                                "<td align='center'>"+index+" ("+status+")</td>"+
                                            "</tr>");  
                                    i++;
                                    jmli++;
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Sub : "+e);
                            } finally{
                                if(rssub!=null){
                                    rssub.close();
                                }
                                if(pssub!=null){
                                    pssub.close();
                                }
                            }
                            
                            sisipantotal1=0;sisipanpotongan=0;sisipantotal2=0;status="Tidak Ada";index=2;
                            pssisipan=koneksi.prepareStatement(
                                    "select sum(subtotal),sum(besardis),sum(total) from detailpesan where no_faktur='"+rspenerimaan.getString("no_faktur")+"' "+
                                    "and kode_brng not in (select kode_brng from detail_surat_pemesanan_medis where no_pemesanan='"+rs.getString("no_pemesanan")+"')");
                            try {
                                rssisipan=pssisipan.executeQuery();
                                if(rssisipan.next()){
                                    sisipantotal1=rssisipan.getDouble(1);
                                    sisipanpotongan=rssisipan.getDouble(2);
                                    sisipantotal2=rssisipan.getDouble(3);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Sisipan : "+e);
                            } finally{
                                if(rssisipan!=null){
                                    rssisipan.close();
                                }
                                if(pssisipan!=null){
                                    pssisipan.close();
                                }
                            }
                            
                            if(sisipantotal1>0){
                                status="Ada";
                                index=0;
                                jmlsisipan=jmlsisipan+sisipantotal2;
                                faktursisipan++;
                            }
                            
                            indextotal=indextotal+index;
                            jmlindextotal=jmlindextotal+index;
                            i++;
                            jmli++;
                            
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td align='right' colspan='4'>Sisipan :</td>"+
                                    "<td align='right' colspan='2'></td>"+
                                    "<td align='right' colspan='2'></td>"+
                                    "<td align='right'></td>"+
                                    "<td align='right' colspan='2'></td>"+
                                    "<td align='right' colspan='2'>"+Valid.SetAngka(sisipantotal1)+"</td>"+
                                    "<td align='right' colspan='2'>"+Valid.SetAngka(sisipanpotongan)+"</td>"+
                                    "<td align='right'>"+Valid.SetAngka(sisipantotal2)+"</td>"+
                                    "<td align='center'>"+index+" ("+status+")</td>"+
                                "</tr>"+ 
                                "<tr class='isi'>"+
                                    "<td align='right' colspan='4'>Total : </td>"+
                                    "<td align='right' colspan='2'>"+Valid.SetAngka(rs.getDouble("total1"))+"</td>"+
                                    "<td align='right' colspan='2'>"+Valid.SetAngka(rs.getDouble("potongan"))+"</td>"+
                                    "<td align='right'>"+Valid.SetAngka(rs.getDouble("total2"))+"</td>"+
                                    "<td align='right' colspan='2'></td>"+
                                    "<td align='right' colspan='2'>"+Valid.SetAngka(rspenerimaan.getDouble("total1"))+"</td>"+
                                    "<td align='right' colspan='2'>"+Valid.SetAngka(rspenerimaan.getDouble("potongan"))+"</td>"+
                                    "<td align='right'>"+Valid.SetAngka(rspenerimaan.getDouble("total2"))+"</td>"+
                                    "<td align='center'>"+indextotal+"</td>"+
                                "</tr>"+ 
                                "<tr class='isi'>"+
                                    "<td align='right' colspan='4'>PPN : </td>"+
                                    "<td align='right' colspan='4'></td>"+
                                    "<td align='right'>"+Valid.SetAngka(rs.getDouble("ppn"))+"</td>"+
                                    "<td align='right' colspan='2'></td>"+
                                    "<td align='right' colspan='4'></td>"+
                                    "<td align='right'>"+Valid.SetAngka(rspenerimaan.getDouble("ppn"))+"</td>"+
                                    "<td align='center' valign='middle' bgcolor='#FFF8F8' rowspan='3'>"+(Math.round(((indextotal/(i*2))*100)))+" %</td>"+
                                "</tr>"+
                                "<tr class='isi'>"+
                                    "<td align='right' colspan='4'>Meterai : </td>"+
                                    "<td align='right' colspan='4'></td>"+
                                    "<td align='right'>"+Valid.SetAngka(rs.getDouble("meterai"))+"</td>"+
                                    "<td align='right' colspan='2'></td>"+
                                    "<td align='right' colspan='4'></td>"+
                                    "<td align='right'>"+Valid.SetAngka(rspenerimaan.getDouble("meterai"))+"</td>"+
                                "</tr>"+
                                "<tr class='isi'>"+
                                    "<td align='right' colspan='4'>Total Tagihan : </td>"+
                                    "<td align='right' colspan='4'></td>"+
                                    "<td align='right'>"+Valid.SetAngka(rs.getDouble("tagihan"))+"</td>"+
                                    "<td align='right' colspan='2'></td>"+
                                    "<td align='right' colspan='4'></td>"+
                                    "<td align='right'>"+Valid.SetAngka(rspenerimaan.getDouble("tagihan"))+"</td>"+
                                "</tr>"+
                                "<tr class='isi'>"+
                                    "<td bgcolor='#FFFCFB' colspan='18'></td>"+
                                "</tr>");
                            jmltotaltagihan=jmltotaltagihan+rspenerimaan.getDouble("tagihan");
                            jmlfaktur++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notif Penerimaan : "+e);
                    } finally{
                        if(rspenerimaan!=null){
                            rspenerimaan.close();
                        }
                        if(pspenerimaan!=null){
                            pspenerimaan.close();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            htmlContent.append(
                    "<tr class='isi'>"+
                        "<td align='center' valign='middle' bgcolor='#FFFAF8' colspan='4'>Jml.Total Tagihan : "+Valid.SetAngka(jmltotaltagihan)+"</td>"+
                        "<td align='center' valign='middle' bgcolor='#FFFAF8' colspan='2'>Jumlah Faktur : "+jmlfaktur+"</td>"+
                        "<td align='center' valign='middle' bgcolor='#FFFAF8' colspan='4'>Jml.Total Sisipan : "+Valid.SetAngka(jmlsisipan)+"</td>"+
                        "<td align='center' valign='middle' bgcolor='#FFFAF8' colspan='3'>Faktur Sisipan : "+faktursisipan+"</td>"+
                        "<td align='center' valign='middle' bgcolor='#FFFAF8' colspan='2'>Kirim Rata-rata : "+(Math.round(jmlharikirim/jmlfaktur))+" Hari</td>"+
                        "<td align='center' valign='middle' bgcolor='#FFFAF8' colspan='2'>Ketepatan Kirim : "+(Math.round(((jmlindextotal/(jmli*2))*100)))+" %</td>"+
                    "</tr>");
            
            if(internalFrame1.getWidth()>1370){
                LoadHTML.setText(
                            "<html>"+
                              "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                               htmlContent.toString()+
                              "</table>"+
                            "</html>");
            }else{
                LoadHTML.setText(
                            "<html>"+
                              "<table width='1050px' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                               htmlContent.toString()+
                              "</table>"+
                            "</html>");
            }
                
        } catch (Exception e) {
            System.out.println("laporan.DlgRL4A.prosesCari() 5 : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }
}

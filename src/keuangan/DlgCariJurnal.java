package keuangan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DlgCariJurnal extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private ResultSet rs,rs2;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariJurnal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Jurnal",
                    "No.Bukti",
                    "Tgl.Jurnal",
                    "Jenis",
                    "Keterangan",
                    "Rekening",
                    "Debet",
                    "Kredit"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(250);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoJur.setDocument(new batasInput((byte)8).getKata(NoJur));
        kdrek.setDocument(new batasInput((byte)15).getKata(kdrek));
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
        rekening.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariJurnal")){
                    if(rekening.getTabel().getSelectedRow()!= -1){      
                        kdrek.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString());
                        nmrek.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString());                        
                        tipe.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),3).toString());                        
                        balance.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),4).toString());                        
                        saldoawal.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),5).toString());                        
                        kdrek.requestFocus();
                    }                 
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
        
        rekening.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariJurnal")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        rekening.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
               
    }
    private DlgRekeningTahun rekening=new DlgRekeningTahun(null,false);
    private double ttldebet=0,ttlkredit=0,subttldebet=0,subttlkredit=0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdrek = new widget.TextBox();
        nmrek = new widget.TextBox();
        BtnCari6 = new widget.Button();
        label25 = new widget.Label();
        label26 = new widget.Label();
        label21 = new widget.Label();
        tipe = new widget.TextBox();
        label23 = new widget.Label();
        balance = new widget.TextBox();
        label22 = new widget.Label();
        saldoawal = new widget.TextBox();
        debet = new widget.Label();
        kredit = new widget.Label();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoJur = new widget.TextBox();
        label11 = new widget.Label();
        TglJurnal2 = new widget.Tanggal();
        label32 = new widget.Label();
        Jenis = new widget.ComboBox();
        label12 = new widget.Label();
        TglJurnal1 = new widget.Tanggal();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Posting Jurnal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 130));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label17.setText("Rekening :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 10, 70, 23);

        kdrek.setName("kdrek"); // NOI18N
        kdrek.setPreferredSize(new java.awt.Dimension(80, 23));
        kdrek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdrekKeyPressed(evt);
            }
        });
        panelisi4.add(kdrek);
        kdrek.setBounds(74, 10, 110, 23);

        nmrek.setEditable(false);
        nmrek.setName("nmrek"); // NOI18N
        nmrek.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmrek);
        nmrek.setBounds(186, 10, 300, 23);

        BtnCari6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCari6.setMnemonic('1');
        BtnCari6.setToolTipText("Alt+1");
        BtnCari6.setName("BtnCari6"); // NOI18N
        BtnCari6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari6ActionPerformed(evt);
            }
        });
        panelisi4.add(BtnCari6);
        BtnCari6.setBounds(488, 10, 28, 23);

        label25.setText("Ttl.Kredit : Rp.");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label25);
        label25.setBounds(525, 40, 90, 23);

        label26.setText("Ttl.Debet : Rp.");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label26);
        label26.setBounds(525, 10, 90, 23);

        label21.setText("Tipe :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label21);
        label21.setBounds(0, 40, 70, 23);

        tipe.setEditable(false);
        tipe.setName("tipe"); // NOI18N
        tipe.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(tipe);
        tipe.setBounds(74, 40, 70, 23);

        label23.setText("Balance :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label23);
        label23.setBounds(147, 40, 60, 23);

        balance.setEditable(false);
        balance.setName("balance"); // NOI18N
        balance.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(balance);
        balance.setBounds(211, 40, 70, 23);

        label22.setText("Saldo Awal :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label22);
        label22.setBounds(286, 40, 80, 23);

        saldoawal.setEditable(false);
        saldoawal.setName("saldoawal"); // NOI18N
        saldoawal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(saldoawal);
        saldoawal.setBounds(370, 40, 116, 23);

        debet.setText("0");
        debet.setName("debet"); // NOI18N
        debet.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(debet);
        debet.setBounds(620, 10, 150, 23);

        kredit.setText("0");
        kredit.setName("kredit"); // NOI18N
        kredit.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(kredit);
        kredit.setBounds(620, 40, 150, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(305, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

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
        panelisi1.add(BtnCari);

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(40, 23));
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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 47));
        panelisi3.setLayout(null);

        label15.setText("No.Jurnal :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 12, 75, 23);

        NoJur.setName("NoJur"); // NOI18N
        NoJur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoJur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoJurKeyPressed(evt);
            }
        });
        panelisi3.add(NoJur);
        NoJur.setBounds(79, 12, 110, 23);

        label11.setText("Tgl.Jurnal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(226, 12, 75, 23);

        TglJurnal2.setDisplayFormat("dd-MM-yyyy");
        TglJurnal2.setName("TglJurnal2"); // NOI18N
        TglJurnal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglJurnal2KeyPressed(evt);
            }
        });
        panelisi3.add(TglJurnal2);
        TglJurnal2.setBounds(430, 12, 95, 23);

        label32.setText("Jenis Jurnal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label32);
        label32.setBounds(546, 12, 90, 23);

        Jenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Umum", "Penyesuaian" }));
        Jenis.setName("Jenis"); // NOI18N
        Jenis.setPreferredSize(new java.awt.Dimension(45, 23));
        Jenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKeyPressed(evt);
            }
        });
        panelisi3.add(Jenis);
        Jenis.setBounds(640, 12, 130, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(402, 12, 27, 23);

        TglJurnal1.setDisplayFormat("dd-MM-yyyy");
        TglJurnal1.setName("TglJurnal1"); // NOI18N
        TglJurnal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglJurnal1KeyPressed(evt);
            }
        });
        panelisi3.add(TglJurnal1);
        TglJurnal1.setBounds(305, 12, 95, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void TglJurnal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglJurnal2KeyPressed
        Valid.pindah(evt, TglJurnal1,Jenis);
    }//GEN-LAST:event_TglJurnal2KeyPressed

    private void BtnCari6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari6ActionPerformed
        akses.setform("DlgCariJurnal");
        rekening.emptTeks();
        rekening.tampil();
        rekening.isCek();
        rekening.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rekening.setLocationRelativeTo(internalFrame1);
        rekening.setVisible(true);
    }//GEN-LAST:event_BtnCari6ActionPerformed

    private void NoJurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoJurKeyPressed
        Valid.pindah(evt, BtnKeluar, TglJurnal1);
    }//GEN-LAST:event_NoJurKeyPressed

    private void kdrekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdrekKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            try {
                    Statement stat=koneksiDB.condb().createStatement();
                    ResultSet rs=stat.executeQuery("select nm_rek, tipe, balance from rekening where kd_rek='"+kdrek.getText()+"'");
                    while(rs.next()){
                        nmrek.setText(rs.getString(1));
                        tipe.setText(rs.getString(2));
                        balance.setText(rs.getString(3));
                    }
                    saldoawal.setText(rekening.getSaldo().getText());
            } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
            }         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            try {
                    Statement stat=koneksiDB.condb().createStatement();
                    ResultSet rs=stat.executeQuery("select nm_rek, tipe, balance from rekening where kd_rek='"+kdrek.getText()+"'");
                    while(rs.next()){
                        nmrek.setText(rs.getString(1));
                        tipe.setText(rs.getString(2));
                        balance.setText(rs.getString(3));
                    }
                    saldoawal.setText(rekening.getSaldo().getText());
            } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
            }
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try {
                    Statement stat=koneksiDB.condb().createStatement();
                    ResultSet rs=stat.executeQuery("select nm_rek, tipe, balance from rekening where kd_rek='"+kdrek.getText()+"'");
                    while(rs.next()){
                        nmrek.setText(rs.getString(1));
                        tipe.setText(rs.getString(2));
                        balance.setText(rs.getString(3));
                    }
                    saldoawal.setText(rekening.getSaldo().getText());
            } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
            }
            Jenis.requestFocus();   
        }
    }//GEN-LAST:event_kdrekKeyPressed

    private void JenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKeyPressed
        Valid.pindah(evt, TglJurnal2, kdrek);
    }//GEN-LAST:event_JenisKeyPressed

    private void TglJurnal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglJurnal1KeyPressed
        Valid.pindah(evt, NoJur,TglJurnal2);
    }//GEN-LAST:event_TglJurnal1KeyPressed

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
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        NoJur.setText("");
        kdrek.setText("");
        nmrek.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary");
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Posting Jurnal");             }
            Sequel.menyimpan("temporary","'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Posting Jurnal"); 
            Sequel.menyimpan("temporary","'0','Jml.Total :','','','','','','"+debet.getText()+"','"+kredit.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            
            Valid.MyReport("rptJurnal.jasper","report","::[ Transaksi Posting Jurnal ]::",param);
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,kdrek);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        BtnPrint.setEnabled(akses.getposting_jurnal());         
    }//GEN-LAST:event_formWindowActivated

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariJurnal dialog = new DlgCariJurnal(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari6;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ComboBox Jenis;
    private widget.TextBox Kd2;
    private widget.TextBox NoJur;
    private widget.TextBox TCari;
    private widget.Tanggal TglJurnal1;
    private widget.Tanggal TglJurnal2;
    private widget.TextBox balance;
    private widget.Label debet;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdrek;
    private widget.Label kredit;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label15;
    private widget.Label label17;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label32;
    private widget.Label label9;
    private widget.TextBox nmrek;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.TextBox saldoawal;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    private widget.TextBox tipe;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        String tanggal=" jurnal.tgl_jurnal between '"+Valid.SetTgl(TglJurnal1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglJurnal2.getSelectedItem()+"")+"' "+
                       " and jenis='"+Jenis.getSelectedItem().toString().substring(0,1)+"' ";
        String nojur="",rek="";
        if(!NoJur.getText().equals("")){
            nojur=" and  jurnal.no_jurnal='"+NoJur.getText()+"' ";
        }        
        if(!nmrek.getText().equals("")){
            rek=" and rekening.nm_rek='"+nmrek.getText()+"' ";
        }        
        
        Valid.tabelKosong(tabMode);
        try{
            rs=koneksi.prepareStatement("select jurnal.no_jurnal, jurnal.no_bukti, jurnal.tgl_jurnal, jurnal.jenis, jurnal.keterangan,"+
                   "detailjurnal.kd_rek,rekening.nm_rek from jurnal inner join detailjurnal inner join rekening  on "+
                   "detailjurnal.no_jurnal=jurnal.no_jurnal and detailjurnal.kd_rek=rekening.kd_rek where "+
                   tanggal+nojur+rek+" and jurnal.no_jurnal like '%"+TCari.getText()+"%' or "+
                   tanggal+nojur+rek+" and jurnal.no_bukti like '%"+TCari.getText()+"%' or "+
                   tanggal+nojur+rek+" and jurnal.keterangan like '%"+TCari.getText()+"%' or "+
                   tanggal+nojur+rek+" and detailjurnal.kd_rek like '%"+TCari.getText()+"%' or "+
                   tanggal+nojur+rek+" and rekening.nm_rek like '%"+TCari.getText()+"%' "+
                   " group by jurnal.no_jurnal order by jurnal.tgl_jurnal,jurnal.no_jurnal ").executeQuery();
            ttldebet=0;ttlkredit=0;
            while(rs.next()){
                String jns="Umum";
                switch (rs.getString(4)) {
                    case "U":
                        jns="Umum";
                        break;
                    case "P":
                        jns="Penyelesaian";
                        break;
                }
                
                tabMode.addRow(new Object[]{
                    rs.getString(1),rs.getString(2),rs.getString(3),jns,rs.getString(5),"Posting Jurnal :","",""
                });
                
                rs2=koneksi.prepareStatement("select detailjurnal.kd_rek,rekening.nm_rek,detailjurnal.debet,detailjurnal.kredit "+
                        " from detailjurnal inner join rekening "+
                        " on detailjurnal.kd_rek=rekening.kd_rek where "+
                        " detailjurnal.no_jurnal='"+rs.getString(1)+"' "+rek+" and detailjurnal.kd_rek like '%"+TCari.getText()+"%' or "+
                        " detailjurnal.no_jurnal='"+rs.getString(1)+"' "+rek+" and rekening.nm_rek like '%"+TCari.getText()+"%'  order by detailjurnal.debet desc  ").executeQuery();
                subttldebet=0;subttlkredit=0;
                int no=1;
                while(rs2.next()){
                    ttldebet=ttldebet+rs2.getDouble(3);
                    subttldebet=subttldebet+rs2.getDouble(3);
                    ttlkredit=ttlkredit+rs2.getDouble(4);
                    subttlkredit=subttlkredit+rs2.getDouble(4);                    
                    tabMode.addRow(new Object[]{"","","","","",no+". "+rs2.getString(1)+", "+rs2.getString(2),Valid.SetAngka(rs2.getDouble(3)),Valid.SetAngka(rs2.getDouble(4))});
                    no++;
                }
                tabMode.addRow(new Object[]{"","","","","Total :","",Valid.SetAngka(subttldebet),Valid.SetAngka(subttlkredit)});                
            }                   
            debet.setText(Valid.SetAngka(ttldebet));
            kredit.setText(Valid.SetAngka(ttlkredit));
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    public void emptTeks() {
        kdrek.setText("");
        nmrek.setText("");
        tipe.setText("");
        debet.setText("0");
        saldoawal.setText("");
        balance.setText("");
        kredit.setText("0");
        kdrek.requestFocus();        
    }

   
 
}

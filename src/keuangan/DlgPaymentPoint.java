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
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public final class DlgPaymentPoint extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,psjamshift;
    private ResultSet rs,rsjamshift;
    private double all=0,pagi=0,siang=0,sore=0,malam=0;
    private int i;
    private String shift="",tanggal2="",nonota="",petugas="";

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgPaymentPoint(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        Object[] rowRwJlDr={"No.","Tanggal","Shift","No.Rawat/No.Nota","Nama Pasien","Pembayaran","Petugas"};
        tabMode=new DefaultTableModel(null,rowRwJlDr){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.Double.class,
                java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        Tabel.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        Tabel.setPreferredScrollableViewportSize(new Dimension(500,500));
        Tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = Tabel.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(125);
            }else if(i==4){
                column.setPreferredWidth(300);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(100);
            }
        }
        Tabel.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        User.setDocument(new batasInput((byte)100).getKata(User));
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
            User.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(User.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(User.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(User.getText().length()>2){
                        tampil();
                    }
                }
            });
        }  
        InputModalAwal.setDocument(new batasInput((byte)16).getOnlyAngka(InputModalAwal));
        Sequel.cariIsiAngka("select modal_awal from set_modal_payment",ModalAwal);
    }    
    
     

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowModalAwal = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        InputModalAwal = new widget.TextBox();
        jLabel8 = new widget.Label();
        BtnCloseIn = new widget.Button();
        BtnSimpan2 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        Tabel = new widget.Table();
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
        CmbStatus = new widget.ComboBox();
        label18 = new widget.Label();
        ModalAwal = new widget.TextBox();
        BtnSeek4 = new widget.Button();

        WindowModalAwal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowModalAwal.setName("WindowModalAwal"); // NOI18N
        WindowModalAwal.setUndecorated(true);
        WindowModalAwal.setResizable(false);

        internalFrame2.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Modal Awal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame2.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

        InputModalAwal.setHighlighter(null);
        InputModalAwal.setName("InputModalAwal"); // NOI18N
        InputModalAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InputModalAwalKeyPressed(evt);
            }
        });
        internalFrame2.add(InputModalAwal);
        InputModalAwal.setBounds(84, 27, 170, 23);

        jLabel8.setText("Modal Awal :");
        jLabel8.setName("jLabel8"); // NOI18N
        internalFrame2.add(jLabel8);
        jLabel8.setBounds(0, 27, 80, 23);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('U');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+U");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(380, 25, 100, 30);

        BtnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan2.setMnemonic('S');
        BtnSimpan2.setText("Simpan");
        BtnSimpan2.setToolTipText("Alt+S");
        BtnSimpan2.setName("BtnSimpan2"); // NOI18N
        BtnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan2ActionPerformed(evt);
            }
        });
        BtnSimpan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan2KeyPressed(evt);
            }
        });
        internalFrame2.add(BtnSimpan2);
        BtnSimpan2.setBounds(275, 25, 100, 30);

        WindowModalAwal.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Payment Point ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        Tabel.setName("Tabel"); // NOI18N
        Scroll.setViewportView(Tabel);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

        label19.setText("User :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(40, 23));
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
        jLabel11.setForeground(new java.awt.Color(70, 70, 70));
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

        label11.setText("Tanggal Bayar :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass6.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(Tgl1);

        jLabel9.setText("Shift :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(jLabel9);

        CmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Pagi", "Siang", "Sore", "Malam" }));
        CmbStatus.setName("CmbStatus"); // NOI18N
        CmbStatus.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass6.add(CmbStatus);

        label18.setText("Modal Awal :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(130, 23));
        panelGlass6.add(label18);

        ModalAwal.setEditable(false);
        ModalAwal.setName("ModalAwal"); // NOI18N
        ModalAwal.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass6.add(ModalAwal);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnSeek4);

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary_payment");
            for(int r=0;r<tabMode.getRowCount();r++){  
                Sequel.menyimpan("temporary_payment","'0',?,?,?,?,?,?,?,'','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''",7,new String[]{
                    tabMode.getValueAt(r,0).toString(),tabMode.getValueAt(r,1).toString(),
                    tabMode.getValueAt(r,2).toString(),tabMode.getValueAt(r,3).toString(),
                    tabMode.getValueAt(r,4).toString(),Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(r,5).toString())),
                    tabMode.getValueAt(r,6).toString()                    
                });
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("shift",CmbStatus.getSelectedItem().toString());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptPaymentPoint.jasper","report","::[ Payment Point ]::",param);
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
        User.setText("");
        tampil();
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
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt,TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
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

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        InputModalAwal.setText(Sequel.cariIsi("select modal_awal from set_modal_payment"));  
        WindowModalAwal.setSize(500,80);
        WindowModalAwal.setLocationRelativeTo(ModalAwal);
        InputModalAwal.requestFocus();
        WindowModalAwal.setAlwaysOnTop(false);
        WindowModalAwal.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void InputModalAwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InputModalAwalKeyPressed
        Valid.pindah(evt,BtnCloseIn,TCari);
    }//GEN-LAST:event_InputModalAwalKeyPressed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowModalAwal.dispose();
    }//GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowModalAwal.dispose();
        }else{Valid.pindah(evt, BtnSimpan2, InputModalAwal);}
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan2ActionPerformed
        if(InputModalAwal.getText().trim().equals("")){
            Valid.textKosong(InputModalAwal,"Modal Awal");
        }else{
            Sequel.queryu("delete from set_modal_payment");
            Sequel.menyimpan("set_modal_payment","'"+InputModalAwal.getText()+"'","Modal Awal");
            WindowModalAwal.setVisible(false);
            ModalAwal.setText(InputModalAwal.getText());
        }
    }//GEN-LAST:event_BtnSimpan2ActionPerformed

    private void BtnSimpan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan2KeyPressed
        Valid.pindah(evt,InputModalAwal,BtnCloseIn);
    }//GEN-LAST:event_BtnSimpan2KeyPressed

    private void UserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UserKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCari.requestFocus();
        }
    }//GEN-LAST:event_UserKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPaymentPoint dialog = new DlgPaymentPoint(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan2;
    private widget.ComboBox CmbStatus;
    private widget.TextBox InputModalAwal;
    private widget.TextBox ModalAwal;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Table Tabel;
    private widget.Tanggal Tgl1;
    private widget.TextBox User;
    private javax.swing.JDialog WindowModalAwal;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private javax.swing.JLabel jLabel11;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass6;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        Valid.tabelKosong(tabMode);
        try{        
            psjamshift=koneksi.prepareStatement("select * from closing_kasir");
            try {
                rsjamshift=psjamshift.executeQuery();
                all=0;
                pagi=0;
                siang=0;
                sore=0;
                malam=0;
                while(rsjamshift.next()){ 
                    ps= koneksi.prepareStatement(
                            "select no_nota,tgl_bayar,nama_pasien,jumlah_bayar,petugas from tagihan_sadewa "+
                            "where tgl_bayar between ? and ? and nama_pasien like ? or "+
                            "tgl_bayar between ? and ? and no_nota like ? order by tgl_bayar,no_nota");
                    try {
                        ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rsjamshift.getString("jam_masuk"));                        
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rsjamshift.getString("jam_masuk"));
                        if(rsjamshift.getString("shift").equals("Malam")){
                            tanggal2=Sequel.cariIsi("select DATE_ADD('"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rsjamshift.getString("jam_pulang")+"',INTERVAL 1 DAY)");
                            ps.setString(2,tanggal2);
                            ps.setString(5,tanggal2);
                        }else{
                            ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rsjamshift.getString("jam_pulang"));
                            ps.setString(5,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+rsjamshift.getString("jam_pulang"));
                        }
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                        rs=ps.executeQuery();
                        i=1;
                        while(rs.next()){                            
                            petugas=rs.getString("petugas")+" "+Sequel.cariIsi("select nama from pegawai where nik=?",rs.getString("petugas"));
                            if(CmbStatus.getSelectedItem().toString().equals("Semua")){
                                nonota=Sequel.cariIsi("select no_nota from nota_inap where no_rawat=?",rs.getString("no_nota"));
                                if(nonota.equals("")){
                                    nonota=Sequel.cariIsi("select no_nota from nota_jalan where no_rawat=?",rs.getString("no_nota"));
                                    if(nonota.equals("")){
                                        nonota=rs.getString("no_nota");
                                    }
                                }
                                if(petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim())){
                                    if(rsjamshift.getString("shift").equals("Pagi")){
                                        pagi=pagi+rs.getDouble("jumlah_bayar");
                                    }else if(rsjamshift.getString("shift").equals("Siang")){
                                        siang=siang+rs.getDouble("jumlah_bayar");
                                    }else if(rsjamshift.getString("shift").equals("Sore")){
                                        sore=sore+rs.getDouble("jumlah_bayar");
                                    }else if(rsjamshift.getString("shift").equals("Malam")){
                                        malam=malam+rs.getDouble("jumlah_bayar");
                                    }
                                    all=all+rs.getDouble("jumlah_bayar");
                                    tabMode.addRow(new Object[]{
                                        i,rs.getString("tgl_bayar"),rsjamshift.getString("shift"),nonota,rs.getString("nama_pasien"),rs.getDouble("jumlah_bayar"),petugas
                                    });
                                }                                    
                            }else if(rsjamshift.getString("shift").equals(CmbStatus.getSelectedItem().toString())){
                                nonota=Sequel.cariIsi("select no_nota from nota_inap where no_rawat=?",rs.getString("no_nota"));
                                if(nonota.equals("")){
                                    nonota=Sequel.cariIsi("select no_nota from nota_jalan where no_rawat=?",rs.getString("no_nota"));
                                    if(nonota.equals("")){
                                        nonota=rs.getString("no_nota");
                                    }
                                }
                                if(petugas.toLowerCase().trim().contains(User.getText().toLowerCase().trim())){
                                    if(rsjamshift.getString("shift").equals("Pagi")){
                                        pagi=pagi+rs.getDouble("jumlah_bayar");
                                    }else if(rsjamshift.getString("shift").equals("Siang")){
                                        siang=siang+rs.getDouble("jumlah_bayar");
                                    }else if(rsjamshift.getString("shift").equals("Sore")){
                                        sore=sore+rs.getDouble("jumlah_bayar");
                                    }else if(rsjamshift.getString("shift").equals("Malam")){
                                        malam=malam+rs.getDouble("jumlah_bayar");
                                    }
                                    all=all+rs.getDouble("jumlah_bayar");
                                    tabMode.addRow(new Object[]{
                                        i,rs.getString("tgl_bayar"),rsjamshift.getString("shift"),nonota,rs.getString("nama_pasien"),rs.getDouble("jumlah_bayar"),petugas
                                    });
                                }                                    
                            }
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
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsjamshift!=null){
                    rsjamshift.close();
                }
                if(psjamshift!=null){
                    psjamshift.close();
                }
            }
            if(CmbStatus.getSelectedItem().toString().equals("Semua")){
                tabMode.addRow(new Object[]{
                        "","Modal Awal",":","","",Double.parseDouble(ModalAwal.getText()),""
                });
                tabMode.addRow(new Object[]{
                        "","Uang Masuk",":","","",all,""
                });
                tabMode.addRow(new Object[]{
                        "",">> Total",":","","",(all+Double.parseDouble(ModalAwal.getText())),""
                });
            }else if(CmbStatus.getSelectedItem().toString().equals("Pagi")){
                tabMode.addRow(new Object[]{
                        "","Modal Awal",":","","",Double.parseDouble(ModalAwal.getText()),""
                });
                tabMode.addRow(new Object[]{
                        "","Uang Masuk",":","","",pagi,""
                });
                tabMode.addRow(new Object[]{
                        "",">> Total",":","","",(pagi+Double.parseDouble(ModalAwal.getText())),""
                });
            }else if(CmbStatus.getSelectedItem().toString().equals("Siang")){
                tabMode.addRow(new Object[]{
                        "","Modal Awal",":","","",(Double.parseDouble(ModalAwal.getText())+pagi),""
                });
                tabMode.addRow(new Object[]{
                        "","Uang Masuk",":","","",siang,""
                });
                tabMode.addRow(new Object[]{
                        "",">> Total",":","","",(pagi+siang+Double.parseDouble(ModalAwal.getText())),""
                });
            }else if(CmbStatus.getSelectedItem().toString().equals("Sore")){
                tabMode.addRow(new Object[]{
                        "","Modal Awal",":","","",(Double.parseDouble(ModalAwal.getText())+pagi+siang),""
                });
                tabMode.addRow(new Object[]{
                        "","Uang Masuk",":","","",sore,""
                });
                tabMode.addRow(new Object[]{
                        "",">> Total",":","","",(pagi+siang+sore+Double.parseDouble(ModalAwal.getText())),""
                });
            }else if(CmbStatus.getSelectedItem().toString().equals("Malam")){
                tabMode.addRow(new Object[]{
                        "","Modal Awal",":","","",(Double.parseDouble(ModalAwal.getText())+pagi+siang+sore),""
                });
                tabMode.addRow(new Object[]{
                        "","Uang Masuk",":","","",malam,""
                });
                tabMode.addRow(new Object[]{
                        "",">> Total",":","","",(pagi+siang+sore+malam+Double.parseDouble(ModalAwal.getText())),""
                });
            }                
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }    

}

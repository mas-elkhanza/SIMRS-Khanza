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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgPenanggungJawab;

/**
 *
 * @author perpustakaan
 */
public final class DlgPembayaranRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private double all=0,Laborat=0,Radiologi=0,Operasi=0,Obat=0,Ranap_Dokter=0,Ranap_Paramedis=0,Ranap_Dokter_Paramedis=0,Ralan_Dokter=0,
             Ralan_Paramedis=0,Ralan_Dokter_Paramedis=0,Tambahan=0,Potongan=0,Kamar=0,Registrasi=0,Harian=0,Retur_Obat=0,Resep_Pulang=0,
             Service=0,ttlLaborat=0,ttlRadiologi=0,ttlOperasi=0,ttlObat=0,ttlRanap_Dokter=0,ttlRanap_Paramedis=0,ttlRalan_Dokter=0,
             ttlRalan_Paramedis=0,ttlTambahan=0,ttlPotongan=0,ttlKamar=0,ttlRegistrasi=0,ttlHarian=0,ttlRetur_Obat=0,ttlResep_Pulang=0,ttlService=0;
    private String sqlps2="select sum(totalbiaya) from billing where no_rawat=? and status=? ";
    
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgPembayaranRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        Object[] rowRwJlDr={"Tgl.Pulang","No.Nota","Nama Pasien","Kamar/Bangsal","Perujuk","Registrasi","Tindakan","Obt+Emb+Tsl","Retur Obat","Resep Pulang",
                            "Laborat","Radiologi","Potongan","Tambahan","Kamar+Service","Operasi","Harian","Total"};
        tabMode=new DefaultTableModel(null,rowRwJlDr){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 18; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){
                    kdbangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    nmbangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                }      
                kdbangsal.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {bangsal.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        bangsal.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    bangsal.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    KdCaraBayar.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    NmCaraBayar.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                    BtnCaraBayar.requestFocus();
                }      
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
        
        ChkInput.setSelected(false);
        isForm();

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
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnBilling = new javax.swing.JMenuItem();
        kdbangsal = new widget.TextBox();
        KdCaraBayar = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        BtnCari1 = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label20 = new widget.Label();
        NmCaraBayar = new widget.TextBox();
        BtnCaraBayar = new widget.Button();
        label17 = new widget.Label();
        nmbangsal = new widget.TextBox();
        BtnSeek2 = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnBilling.setBackground(new java.awt.Color(255, 255, 254));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(new java.awt.Color(50, 50, 50));
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing/Pembayaran Pasien");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBilling);

        kdbangsal.setName("kdbangsal"); // NOI18N
        kdbangsal.setPreferredSize(new java.awt.Dimension(70, 23));
        kdbangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbangsalKeyPressed(evt);
            }
        });

        KdCaraBayar.setEditable(false);
        KdCaraBayar.setName("KdCaraBayar"); // NOI18N
        KdCaraBayar.setPreferredSize(new java.awt.Dimension(50, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pembayaran Pasien Ranap Per Tanggal Pulang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setComponentPopupMenu(jPopupMenu1);
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBangsalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

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
        label18.setPreferredSize(new java.awt.Dimension(27, 23));
        panelGlass5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl2);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelGlass5.add(BtnCari1);

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

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass5.add(LCount);

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

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 66));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label20.setText("Cara Bayar :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label20);

        NmCaraBayar.setEditable(false);
        NmCaraBayar.setName("NmCaraBayar"); // NOI18N
        NmCaraBayar.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(NmCaraBayar);

        BtnCaraBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayar.setMnemonic('3');
        BtnCaraBayar.setToolTipText("Alt+3");
        BtnCaraBayar.setName("BtnCaraBayar"); // NOI18N
        BtnCaraBayar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarActionPerformed(evt);
            }
        });
        FormInput.add(BtnCaraBayar);

        label17.setText("Kamar/Bangsal :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(170, 23));
        FormInput.add(label17);

        nmbangsal.setEditable(false);
        nmbangsal.setName("nmbangsal"); // NOI18N
        nmbangsal.setPreferredSize(new java.awt.Dimension(260, 23));
        FormInput.add(nmbangsal);

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
        FormInput.add(BtnSeek2);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            for(int r=0;r<tabMode.getRowCount();r++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                    tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,10).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,11).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,12).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,13).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,14).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,15).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,16).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,17).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRTagihanRanap.jasper","report","::[ Rekap Tagihan Ranap Masuk ]::",param);
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

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbBangsalKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TKd, BtnPrint);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kdbangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbangsalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal,kdbangsal.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal,kdbangsal.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal,kdbangsal.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdbangsalKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kdbangsal.setText("");
        nmbangsal.setText("");
        KdCaraBayar.setText("");
        NmCaraBayar.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, kdbangsal, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
        if(TKd.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            DlgBilingRanap billing=new DlgBilingRanap(null,false);
            billing.TNoRw.setText(Sequel.cariIsi("select no_rawat from nota_inap where no_nota=?",TKd.getText()));            
            billing.isCek();
            billing.isRawat();
            billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            billing.setLocationRelativeTo(internalFrame1);
            billing.setVisible(true); 
        }
    }//GEN-LAST:event_MnBillingActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnCaraBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnCaraBayarActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPembayaranRanap dialog = new DlgPembayaranRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCaraBayar;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox KdCaraBayar;
    private javax.swing.JLabel LCount;
    private javax.swing.JMenuItem MnBilling;
    private widget.TextBox NmCaraBayar;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TKd;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbangsal;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label20;
    private widget.TextBox nmbangsal;
    private widget.panelisi panelGlass5;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        Valid.tabelKosong(tabMode);
        try{      
            if(nmbangsal.getText().equals("")&&NmCaraBayar.getText().equals("")){
                ps= koneksi.prepareStatement(
                    "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,kamar_inap.tgl_keluar,bangsal.nm_bangsal,kamar_inap.stts_pulang, "+
                    "kamar.kd_kamar from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar between ? and ? and "+
                    "reg_periksa.no_rawat not in (select piutang_pasien.no_rawat from piutang_pasien where piutang_pasien.no_rawat=reg_periksa.no_rawat) "+
                    "order by kamar_inap.tgl_keluar,kamar_inap.jam_keluar");
            }else{
                ps= koneksi.prepareStatement(
                    "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,kamar_inap.tgl_keluar,bangsal.nm_bangsal,kamar_inap.stts_pulang, "+
                    "kamar.kd_kamar from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar between ? and ? and concat(kamar.kd_bangsal,bangsal.nm_bangsal) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and "+
                    "reg_periksa.no_rawat not in (select piutang_pasien.no_rawat from piutang_pasien where piutang_pasien.no_rawat=reg_periksa.no_rawat) "+
                    "order by kamar_inap.tgl_keluar,kamar_inap.jam_keluar");
            }
                
            try {
                if(nmbangsal.getText().equals("")&&NmCaraBayar.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+kdbangsal.getText()+nmbangsal.getText()+"%");
                    ps.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                all=0;ttlLaborat=0;ttlRadiologi=0;ttlOperasi=0;ttlObat=0;
                ttlRanap_Dokter=0;ttlRanap_Paramedis=0;ttlRalan_Dokter=0;
                ttlRalan_Paramedis=0;ttlTambahan=0;ttlPotongan=0;ttlKamar=0;
                ttlRegistrasi=0;ttlHarian=0;ttlRetur_Obat=0;ttlResep_Pulang=0;
                ttlService=0;
                while(rs.next()){
                    if(!rs.getString("stts_pulang").equals("-")){
                    if(!rs.getString("stts_pulang").equals("Pindah Kamar")){
                        Laborat=0;   
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Laborat");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlLaborat=ttlLaborat+rs2.getDouble(1);
                                Laborat=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Radiologi=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Radiologi");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRadiologi=ttlRadiologi+rs2.getDouble(1);
                                Radiologi=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Operasi=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Operasi");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlOperasi=ttlOperasi+rs2.getDouble(1);
                                Operasi=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Obat=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Obat");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlObat=ttlObat+rs2.getDouble(1);
                                Obat=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ranap_Dokter=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ranap Dokter");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRanap_Dokter=ttlRanap_Dokter+rs2.getDouble(1);
                                Ranap_Dokter=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }                        

                        Ranap_Dokter_Paramedis=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ranap Dokter Paramedis");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRanap_Dokter=ttlRanap_Dokter+rs2.getDouble(1);
                                Ranap_Dokter_Paramedis=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ranap_Paramedis=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ranap Paramedis");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRanap_Paramedis=ttlRanap_Paramedis+rs2.getDouble(1);
                                Ranap_Paramedis=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ralan_Dokter=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ralan Dokter");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRalan_Dokter=ttlRalan_Dokter+rs2.getDouble(1);
                                Ralan_Dokter=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ralan_Dokter_Paramedis=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ralan Dokter Paramedis");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRalan_Dokter=ttlRalan_Dokter+rs2.getDouble(1);
                                Ralan_Dokter_Paramedis=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ralan_Paramedis=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ralan Paramedis");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRalan_Paramedis=ttlRalan_Paramedis+rs2.getDouble(1);
                                Ralan_Paramedis=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Tambahan=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Tambahan");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlTambahan=ttlTambahan+rs2.getDouble(1);
                                Tambahan=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Potongan=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Potongan");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlPotongan=ttlPotongan+rs2.getDouble(1);
                                Potongan=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Kamar=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Kamar");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlKamar=ttlKamar+rs2.getDouble(1);
                                Kamar=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Registrasi=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Registrasi");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRegistrasi=ttlRegistrasi+rs2.getDouble(1);
                                Registrasi=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Harian=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Harian");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlHarian=ttlHarian+rs2.getDouble(1);
                                Harian=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Retur_Obat=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Retur Obat");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRetur_Obat=ttlRetur_Obat+rs2.getDouble(1);
                                Retur_Obat=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Resep_Pulang=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Resep Pulang");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlResep_Pulang=ttlResep_Pulang+rs2.getDouble(1);
                                Resep_Pulang=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Service=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Service");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlService=ttlService+rs2.getDouble(1);
                                Service=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }


                        tabMode.addRow(new Object[]{
                            rs.getString("tgl_keluar"),Sequel.cariIsi("select no_nota from nota_inap where no_rawat=?",rs.getString("no_rawat")),
                            rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),rs.getString("kd_kamar")+" "+rs.getString("nm_bangsal"),
                            Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?",rs.getString("no_rawat")),Valid.SetAngka(Registrasi),
                            Valid.SetAngka(Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis),
                            Valid.SetAngka(Obat),Valid.SetAngka(Retur_Obat),Valid.SetAngka(Resep_Pulang),Valid.SetAngka(Laborat),Valid.SetAngka(Radiologi),Valid.SetAngka(Potongan),
                            Valid.SetAngka(Tambahan),Valid.SetAngka(Kamar+Service),Valid.SetAngka(Operasi),Valid.SetAngka(Harian),Valid.SetAngka(Laborat+Radiologi+Operasi+Obat+Ranap_Dokter+
                                    Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Kamar+Registrasi+Harian+Retur_Obat+Resep_Pulang+Service)
                        });
                        all=all+Laborat+Radiologi+Operasi+Obat+Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Kamar+Registrasi+Harian+Retur_Obat+Resep_Pulang+Service;
                    }}                
                }
                tabMode.addRow(new Object[]{
                        ">> Total ",":","","","",Valid.SetAngka(ttlRegistrasi),Valid.SetAngka(ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis),
                        Valid.SetAngka(ttlObat),Valid.SetAngka(ttlRetur_Obat),Valid.SetAngka(ttlResep_Pulang),Valid.SetAngka(ttlLaborat),Valid.SetAngka(ttlRadiologi),Valid.SetAngka(ttlPotongan),
                        Valid.SetAngka(ttlTambahan),Valid.SetAngka(ttlKamar+ttlService),Valid.SetAngka(ttlOperasi),Valid.SetAngka(ttlHarian),Valid.SetAngka(all)
                });
                LCount.setText(Valid.SetAngka(all));
            } catch (Exception e) {
                System.out.println("Notif 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getData() {
        int row=tbBangsal.getSelectedRow();
        if(row!= -1){
            TKd.setText(tabMode.getValueAt(row,1).toString());
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,65));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }

}

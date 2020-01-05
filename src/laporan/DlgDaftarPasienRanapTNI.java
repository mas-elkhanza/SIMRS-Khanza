package laporan;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import fungsi.sekuel;
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
import simrskhanza.DlgGolonganTNI;
import simrskhanza.DlgJabatanTNI;
import simrskhanza.DlgPangkatTNI;
import simrskhanza.DlgSatuanTNI;

public class DlgDaftarPasienRanapTNI extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private final validasi Valid=new validasi();
    private final Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i;
    private String tglkeluar,harirawat,kamar;
    public  DlgGolonganTNI golongantni=new DlgGolonganTNI(null,false);
    public  DlgSatuanTNI satuantni=new DlgSatuanTNI(null,false);
    public  DlgPangkatTNI pangkattni=new DlgPangkatTNI(null,false);
    public  DlgJabatanTNI jabatantni=new DlgJabatanTNI(null,false);
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgDaftarPasienRanapTNI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Object[] rowRwJlDr={"No.","No.RM","Nama Pasien","Cara Bayar","Tgl.Masuk","Tgl.Pulang","Ruangan","H.P.","Pangkat","Satuan","Golongan","Jabatan"};
        tabMode=new DefaultTableModel(null,rowRwJlDr){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(35);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }
        }
        
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());
        Valid.LoadTahun(ThnCari);
        
        golongantni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(golongantni.getTable().getSelectedRow()!= -1){
                    Golongan.setText(golongantni.getTable().getValueAt(golongantni.getTable().getSelectedRow(),1).toString());
                }  
                Golongan.requestFocus();
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
        
        golongantni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    golongantni.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        jabatantni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jabatantni.getTable().getSelectedRow()!= -1){
                    Jabatan.setText(jabatantni.getTable().getValueAt(jabatantni.getTable().getSelectedRow(),1).toString());
                }  
                Jabatan.requestFocus();
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
        
        jabatantni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jabatantni.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        satuantni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(satuantni.getTable().getSelectedRow()!= -1){
                    Satuan.setText(satuantni.getTable().getValueAt(satuantni.getTable().getSelectedRow(),1).toString());
                }  
                Satuan.requestFocus();
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
        
        satuantni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        satuantni.dispose();
                    }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pangkattni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pangkattni.getTable().getSelectedRow()!= -1){
                    Pangkat.setText(pangkattni.getTable().getValueAt(pangkattni.getTable().getSelectedRow(),1).toString());
                }  
                Pangkat.requestFocus();
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
        
        pangkattni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pangkattni.dispose();
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
        ThnCari = new widget.ComboBox();
        BlnCari = new widget.ComboBox();
        btnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        Satuan = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label20 = new widget.Label();
        Golongan = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        label21 = new widget.Label();
        Jabatan = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        label22 = new widget.Label();
        Pangkat = new widget.TextBox();
        BtnSeek6 = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Daftar Nama Pasien Rawat Inap TNI ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tahun & Bulan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi1.add(label11);

        ThnCari.setName("ThnCari"); // NOI18N
        ThnCari.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi1.add(ThnCari);

        BlnCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        BlnCari.setName("BlnCari"); // NOI18N
        BlnCari.setPreferredSize(new java.awt.Dimension(62, 23));
        panelisi1.add(BlnCari);

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
        label9.setPreferredSize(new java.awt.Dimension(100, 30));
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

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 95));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 104));
        FormInput.setLayout(null);

        label17.setText("Satuan :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label17);
        label17.setBounds(0, 10, 70, 23);

        Satuan.setEditable(false);
        Satuan.setName("Satuan"); // NOI18N
        Satuan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Satuan);
        Satuan.setBounds(74, 10, 200, 23);

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
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(277, 10, 28, 23);

        label20.setText("Golongan :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label20);
        label20.setBounds(0, 40, 70, 23);

        Golongan.setEditable(false);
        Golongan.setName("Golongan"); // NOI18N
        Golongan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Golongan);
        Golongan.setBounds(74, 40, 200, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('3');
        BtnSeek4.setToolTipText("Alt+3");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(277, 40, 28, 23);

        label21.setText("Jabatan :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label21);
        label21.setBounds(309, 10, 87, 23);

        Jabatan.setEditable(false);
        Jabatan.setName("Jabatan"); // NOI18N
        Jabatan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Jabatan);
        Jabatan.setBounds(399, 10, 200, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('3');
        BtnSeek5.setToolTipText("Alt+3");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek5);
        BtnSeek5.setBounds(602, 10, 28, 23);

        label22.setText("Pangkat :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label22);
        label22.setBounds(309, 40, 87, 23);

        Pangkat.setEditable(false);
        Pangkat.setName("Pangkat"); // NOI18N
        Pangkat.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Pangkat);
        Pangkat.setBounds(399, 40, 200, 23);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('3');
        BtnSeek6.setToolTipText("Alt+3");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek6);
        BtnSeek6.setBounds(602, 40, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,ThnCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,ThnCari,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();         
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("periode","BULAN "+BlnCari.getSelectedItem()+" TAHUN "+ThnCari.getSelectedItem());  
            Sequel.queryu("truncate table temporary");
            for(int r=0;r<tabMode.getRowCount();r++){ 
                Sequel.menyimpan("temporary","'0','"+
                    tabMode.getValueAt(r,0).toString()+"','"+
                    tabMode.getValueAt(r,1).toString()+"','"+
                    tabMode.getValueAt(r,2).toString()+"','"+
                    tabMode.getValueAt(r,3).toString()+"','"+
                    tabMode.getValueAt(r,4).toString()+"','"+
                    tabMode.getValueAt(r,5).toString()+"','"+
                    tabMode.getValueAt(r,6).toString()+"','"+
                    tabMode.getValueAt(r,7).toString()+"','"+
                    tabMode.getValueAt(r,8).toString()+"','"+
                    tabMode.getValueAt(r,9).toString()+"','"+
                    tabMode.getValueAt(r,10).toString()+"','"+
                    tabMode.getValueAt(r,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Daftar Pasien"
                );
            }
               
            Valid.MyReport("rptDaftarPasienRanapTNI.jasper","report","::[ Laporan Daftar Nama Pasien Ranap TNI ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void btnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            btnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, ThnCari, BtnPrint);
        }
    }//GEN-LAST:event_btnCariKeyPressed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        prosesCari();
    }//GEN-LAST:event_btnCariActionPerformed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        
    }//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        
    }//GEN-LAST:event_tbBangsalKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        satuantni.isCek();
        satuantni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        satuantni.setLocationRelativeTo(internalFrame1);
        satuantni.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        golongantni.isCek();
        golongantni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        golongantni.setLocationRelativeTo(internalFrame1);
        golongantni.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        jabatantni.isCek();
        jabatantni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jabatantni.setLocationRelativeTo(internalFrame1);
        jabatantni.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        pangkattni.isCek();
        pangkattni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pangkattni.setLocationRelativeTo(internalFrame1);
        pangkattni.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        Satuan.setText("");
        Pangkat.setText("");
        Jabatan.setText("");
        Golongan.setText("");
        prosesCari();
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
            DlgDaftarPasienRanapTNI dialog = new DlgDaftarPasienRanapTNI(new javax.swing.JFrame(), true);
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
    private widget.ComboBox BlnCari;
    private widget.Button BtnAll;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox Golongan;
    private widget.TextBox Jabatan;
    private widget.TextBox Kd2;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pangkat;
    private widget.TextBox Satuan;
    private widget.ScrollPane Scroll;
    private widget.ComboBox ThnCari;
    private widget.Button btnCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,penjab.png_jawab,reg_periksa.tgl_registrasi,reg_periksa.no_rawat, "+
                    "pangkat_tni.nama_pangkat,satuan_tni.nama_satuan,golongan_tni.nama_golongan,jabatan_tni.nama_jabatan "+
                    "from pasien inner join reg_periksa inner join penjab inner join pangkat_tni inner join satuan_tni inner join pasien_tni "+
                    "inner join golongan_tni inner join jabatan_tni on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and pasien_tni.pangkat_tni=pangkat_tni.id and pasien_tni.satuan_tni=satuan_tni.id and pasien_tni.no_rkm_medis=pasien.no_rkm_medis "+
                    "and pasien_tni.golongan_tni=golongan_tni.id and pasien_tni.jabatan_tni=jabatan_tni.id and reg_periksa.kd_pj=penjab.kd_pj "+
                    "where reg_periksa.tgl_registrasi like ? and status_lanjut='Ranap' and pangkat_tni.nama_pangkat like ? and satuan_tni.nama_satuan like ? and golongan_tni.nama_golongan like ? and jabatan_tni.nama_jabatan like ? order by reg_periksa.tgl_registrasi");
            try {
                ps.setString(1,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
                ps.setString(2,"%"+Pangkat.getText().trim()+"%");
                ps.setString(3,"%"+Satuan.getText().trim()+"%");
                ps.setString(4,"%"+Golongan.getText().trim()+"%");
                ps.setString(5,"%"+Jabatan.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    tglkeluar="Belum Pulang";
                    harirawat="0";
                    ps2=koneksi.prepareStatement(
                            "select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) as tgl_keluar,"+
                            "(datediff(tgl_keluar,?)+1) as lama from kamar_inap "+
                            "where no_rawat=? and stts_pulang<>'Pindah Kamar' order by tgl_keluar desc limit 1");
                    try {
                        ps2.setString(1,rs.getString("tgl_registrasi"));
                        ps2.setString(2,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            tglkeluar=rs2.getString("tgl_keluar");
                            if(Valid.SetAngka(rs2.getString("lama"))>0){
                                harirawat=rs2.getString("lama");
                            }else{
                                harirawat="";
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    kamar="";
                    if(!tglkeluar.equals("Belum Pulang")){
                        ps2=koneksi.prepareStatement(
                            "select bangsal.nm_bangsal from kamar inner join bangsal inner join kamar_inap on "+
                            "kamar.kd_bangsal=bangsal.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where kamar_inap.no_rawat=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                kamar=rs2.getString("nm_bangsal")+","+kamar;
                            }                        
                        } catch (Exception e) {
                            System.out.println("Notif 3 :"+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }    

                        if(kamar.endsWith(",")){
                            kamar = kamar.substring(0,kamar.length() - 1);
                        }
                    }
                    
                    tabMode.addRow(new String[]{
                        i+"",rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("png_jawab"),rs.getString("tgl_registrasi"),tglkeluar,
                        kamar,harirawat,rs.getString("nama_pangkat"),rs.getString("nama_satuan"),rs.getString("nama_golongan"),rs.getString("nama_jabatan")
                    });
                    i++;
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getdaftar_pasien_ranap());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,95));
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

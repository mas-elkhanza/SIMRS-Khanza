/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJadwal.java
 *
 * Created on May 22, 2010, 10:25:16 PM
 */

package laporan;
import fungsi.WarnaTable;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgPenanggungJawab;

/**
 *
 * @author dosen
 */
public class LaporanKedatanganPasienPerJam extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private String dateString,dayOfWeek,hari;
    private double h1=0,h2=0,h3=0,h4=0,h5=0,h6=0,h7=0,h8=0,h9=0,h10=0,h11=0,h12=0,h13=0,
                   h14=0,h15=0,h16=0,h17=0,h18=0,h19=0,h20=0,h21=0,h22=0,h23=0,h24=0,h25=0,h26=0,h27=0,h28=0,h29=0,h30=0,h31=0 ;
    private Date date = null;
    private int i=0;
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);

    /** Creates new form DlgJadwal
     * @param parent
     * @param modal */
    public LaporanKedatanganPasienPerJam(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        tbJadwal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJadwal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Valid.LoadTahun(ThnCari);
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    BtnDokterRalanDokter.requestFocus();
                }      
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
        
        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dokter.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    BtnPoliRalanDokter.requestFocus();
                }      
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
                    KdCaraBayar.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    NmCaraBayar.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                    BtnCaraBayarRalanDokter.requestFocus();
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

        KdCaraBayar = new widget.TextBox();
        KdPoli = new widget.TextBox();
        KdDokter = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJadwal = new widget.Table();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        ThnCari = new widget.ComboBox();
        BlnCari = new widget.ComboBox();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        BtnCari = new widget.Button();
        label12 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        NmDokter = new widget.TextBox();
        BtnDokterRalanDokter = new widget.Button();
        label19 = new widget.Label();
        NmCaraBayar = new widget.TextBox();
        BtnCaraBayarRalanDokter = new widget.Button();
        label20 = new widget.Label();
        NmPoli = new widget.TextBox();
        BtnPoliRalanDokter = new widget.Button();

        KdCaraBayar.setEditable(false);
        KdCaraBayar.setName("KdCaraBayar"); // NOI18N
        KdCaraBayar.setPreferredSize(new java.awt.Dimension(50, 23));

        KdPoli.setEditable(false);
        KdPoli.setName("KdPoli"); // NOI18N
        KdPoli.setPreferredSize(new java.awt.Dimension(50, 23));

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kedatangan Pasien Per Jam ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJadwal.setName("tbJadwal"); // NOI18N
        tbJadwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJadwalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJadwal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tahun & Bulan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(82, 23));
        panelGlass8.add(label11);

        ThnCari.setName("ThnCari"); // NOI18N
        ThnCari.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(ThnCari);

        BlnCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        BlnCari.setName("BlnCari"); // NOI18N
        BlnCari.setPreferredSize(new java.awt.Dimension(62, 23));
        panelGlass8.add(BlnCari);

        jLabel12.setText("Stts.Daftar :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Baru", "Lama" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass8.add(cmbStatus);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        panelGlass8.add(BtnCari);

        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(22, 23));
        panelGlass8.add(label12);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('4');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+4");
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
        panelGlass8.add(BtnAll);

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
        panelGlass8.add(BtnPrint);

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
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

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

        label17.setText("Dokter :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(label17);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmDokter);

        BtnDokterRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterRalanDokter.setMnemonic('3');
        BtnDokterRalanDokter.setToolTipText("Alt+3");
        BtnDokterRalanDokter.setName("BtnDokterRalanDokter"); // NOI18N
        BtnDokterRalanDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterRalanDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokterRalanDokter);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(85, 23));
        FormInput.add(label19);

        NmCaraBayar.setEditable(false);
        NmCaraBayar.setName("NmCaraBayar"); // NOI18N
        NmCaraBayar.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmCaraBayar);

        BtnCaraBayarRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRalanDokter.setMnemonic('3');
        BtnCaraBayarRalanDokter.setToolTipText("Alt+3");
        BtnCaraBayarRalanDokter.setName("BtnCaraBayarRalanDokter"); // NOI18N
        BtnCaraBayarRalanDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRalanDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnCaraBayarRalanDokter);

        label20.setText("Unit/Poli :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label20);

        NmPoli.setEditable(false);
        NmPoli.setName("NmPoli"); // NOI18N
        NmPoli.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmPoli);

        BtnPoliRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoliRalanDokter.setMnemonic('3');
        BtnPoliRalanDokter.setToolTipText("Alt+3");
        BtnPoliRalanDokter.setName("BtnPoliRalanDokter"); // NOI18N
        BtnPoliRalanDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPoliRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliRalanDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnPoliRalanDokter);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BlnCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        KdDokter.setText("");
        NmDokter.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        KdCaraBayar.setText("");
        NmCaraBayar.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            BtnAllActionPerformed(null);
            tampil();
        }else{
            Valid.pindah(evt, BtnCari,BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void tbJadwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJadwalKeyPressed
        
    }//GEN-LAST:event_tbJadwalKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnAll, BlnCari);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString()+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,1).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,2).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,3).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,4).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,5).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,6).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,7).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,8).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,9).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,10).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,11).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,12).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,13).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,14).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,15).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,16).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,17).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,18).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,19).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,20).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,21).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,22).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,23).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,24).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,25).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,26).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,27).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,28).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,29).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,30).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,31).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,32).toString()))+"','','','',''","kedatangan pasien per jam"); 
            }
            
            Map<String, Object> param = new HashMap<>();   
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("periode","01 - 31 BULAN "+BlnCari.getSelectedItem()+" TAHUN "+ThnCari.getSelectedItem());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                param.put("jd1","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),1)+")");
                param.put("jd2","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),2)+")");
                param.put("jd3","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),3)+")");
                param.put("jd4","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),4)+")");
                param.put("jd5","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),5)+")");
                param.put("jd6","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),6)+")");
                param.put("jd7","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),7)+")");
                param.put("jd8","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),8)+")");
                param.put("jd9","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),9)+")");
                param.put("jd10","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),10)+")");
                param.put("jd11","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),11)+")");
                param.put("jd12","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),12)+")");
                param.put("jd13","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),13)+")");
                param.put("jd14","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),14)+")");
                param.put("jd15","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),15)+")");
                param.put("jd16","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),16)+")");
                param.put("jd17","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),17)+")");
                param.put("jd18","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),18)+")");
                param.put("jd19","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),19)+")");
                param.put("jd20","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),20)+")");
                param.put("jd21","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),21)+")");
                param.put("jd22","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),22)+")");
                param.put("jd23","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),23)+")");
                param.put("jd24","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),24)+")");
                param.put("jd25","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),25)+")");
                param.put("jd26","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),26)+")");
                param.put("jd27","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),27)+")");
                param.put("jd28","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),28)+")");
                param.put("jd29","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),29)+")");
                param.put("jd30","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),30)+")");
                param.put("jd31","("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),31)+")");
                Valid.MyReport("rptKedatanganPasienPerJam.jasper","report","::[ Kedatangan Pasien Per Jam ]::",param);   
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
           // Valid.pindah(evt, BtnEdit, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnDokterRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterRalanDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterRalanDokterActionPerformed

    private void BtnCaraBayarRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRalanDokterActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnCaraBayarRalanDokterActionPerformed

    private void BtnPoliRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliRalanDokterActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnPoliRalanDokterActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LaporanKedatanganPasienPerJam dialog = new LaporanKedatanganPasienPerJam(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCaraBayarRalanDokter;
    private widget.Button BtnCari;
    private widget.Button BtnDokterRalanDokter;
    private widget.Button BtnKeluar;
    private widget.Button BtnPoliRalanDokter;
    private widget.Button BtnPrint;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox KdCaraBayar;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPoli;
    private widget.TextBox NmCaraBayar;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPoli;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ComboBox ThnCari;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label17;
    private widget.Label label19;
    private widget.Label label20;
    private widget.panelisi panelGlass8;
    private widget.Table tbJadwal;
    // End of variables declaration//GEN-END:variables

    private void tampil() {  
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Object[] row={"Jam",
            "1("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),1)+")",
            "2("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),2)+")",
            "3("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),3)+")",
            "4("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),4)+")",
            "5("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),5)+")",
            "6("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),6)+")",
            "7("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),7)+")",
            "8("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),8)+")",
            "9("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),9)+")",
            "10("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),10)+")",
            "11("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),11)+")",
            "12("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),12)+")",
            "13("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),13)+")",
            "14("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),14)+")",
            "15("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),15)+")",
            "16("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),16)+")",
            "17("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),17)+")",
            "18("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),18)+")",
            "19("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),19)+")",
            "20("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),20)+")",
            "21("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),21)+")",
            "22("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),22)+")",
            "23("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),23)+")",
            "24("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),24)+")",
            "25("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),25)+")",
            "26("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),26)+")",
            "27("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),27)+")",
            "28("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),28)+")",
            "29("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),29)+")",
            "30("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),30)+")",
            "31("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),31)+")",
            "Total"
        };
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class,java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class,java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class,java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class,java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class,java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class,java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbJadwal.setModel(tabMode);
        
        for (i = 0; i < 33; i++) {
            TableColumn column = tbJadwal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else{
                column.setPreferredWidth(45);
            }
        }
        tbJadwal.setDefaultRenderer(Object.class, new WarnaTable());
        
        Valid.tabelKosong(tabMode);
        
        h1=JmlPasien("01","00:00:00","01:00:00");
        h2=JmlPasien("02","00:00:00","01:00:00");
        h3=JmlPasien("03","00:00:00","01:00:00");
        h4=JmlPasien("04","00:00:00","01:00:00");
        h5=JmlPasien("05","00:00:00","01:00:00");
        h6=JmlPasien("06","00:00:00","01:00:00");
        h7=JmlPasien("07","00:00:00","01:00:00");
        h8=JmlPasien("08","00:00:00","01:00:00");
        h9=JmlPasien("09","00:00:00","01:00:00");
        h10=JmlPasien("10","00:00:00","01:00:00");
        h11=JmlPasien("11","00:00:00","01:00:00");
        h12=JmlPasien("12","00:00:00","01:00:00");
        h13=JmlPasien("13","00:00:00","01:00:00");
        h14=JmlPasien("14","00:00:00","01:00:00");
        h15=JmlPasien("15","00:00:00","01:00:00");
        h16=JmlPasien("16","00:00:00","01:00:00");
        h17=JmlPasien("17","00:00:00","01:00:00");
        h18=JmlPasien("18","00:00:00","01:00:00");
        h19=JmlPasien("19","00:00:00","01:00:00");
        h20=JmlPasien("20","00:00:00","01:00:00");
        h21=JmlPasien("21","00:00:00","01:00:00");
        h22=JmlPasien("22","00:00:00","01:00:00");
        h23=JmlPasien("23","00:00:00","01:00:00");
        h24=JmlPasien("24","00:00:00","01:00:00");
        h25=JmlPasien("25","00:00:00","01:00:00");
        h26=JmlPasien("26","00:00:00","01:00:00");
        h27=JmlPasien("27","00:00:00","01:00:00");
        h28=JmlPasien("28","00:00:00","01:00:00");
        h29=JmlPasien("29","00:00:00","01:00:00");
        h30=JmlPasien("30","00:00:00","01:00:00");
        h31=JmlPasien("31","00:00:00","01:00:00");
        tabMode.addRow(new Object[]{
            "01",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","01:00:01","02:00:00");
        h2=JmlPasien("02","01:00:01","02:00:00");
        h3=JmlPasien("03","01:00:01","02:00:00");
        h4=JmlPasien("04","01:00:01","02:00:00");
        h5=JmlPasien("05","01:00:01","02:00:00");
        h6=JmlPasien("06","01:00:01","02:00:00");
        h7=JmlPasien("07","01:00:01","02:00:00");
        h8=JmlPasien("08","01:00:01","02:00:00");
        h9=JmlPasien("09","01:00:01","02:00:00");
        h10=JmlPasien("10","01:00:01","02:00:00");
        h11=JmlPasien("11","01:00:01","02:00:00");
        h12=JmlPasien("12","01:00:01","02:00:00");
        h13=JmlPasien("13","01:00:01","02:00:00");
        h14=JmlPasien("14","01:00:01","02:00:00");
        h15=JmlPasien("15","01:00:01","02:00:00");
        h16=JmlPasien("16","01:00:01","02:00:00");
        h17=JmlPasien("17","01:00:01","02:00:00");
        h18=JmlPasien("18","01:00:01","02:00:00");
        h19=JmlPasien("19","01:00:01","02:00:00");
        h20=JmlPasien("20","01:00:01","02:00:00");
        h21=JmlPasien("21","01:00:01","02:00:00");
        h22=JmlPasien("22","01:00:01","02:00:00");
        h23=JmlPasien("23","01:00:01","02:00:00");
        h24=JmlPasien("24","01:00:01","02:00:00");
        h25=JmlPasien("25","01:00:01","02:00:00");
        h26=JmlPasien("26","01:00:01","02:00:00");
        h27=JmlPasien("27","01:00:01","02:00:00");
        h28=JmlPasien("28","01:00:01","02:00:00");
        h29=JmlPasien("29","01:00:01","02:00:00");
        h30=JmlPasien("30","01:00:01","02:00:00");
        h31=JmlPasien("31","01:00:01","02:00:00");
        tabMode.addRow(new Object[]{
            "02",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","02:00:01","03:00:00");
        h2=JmlPasien("02","02:00:01","03:00:00");
        h3=JmlPasien("03","02:00:01","03:00:00");
        h4=JmlPasien("04","02:00:01","03:00:00");
        h5=JmlPasien("05","02:00:01","03:00:00");
        h6=JmlPasien("06","02:00:01","03:00:00");
        h7=JmlPasien("07","02:00:01","03:00:00");
        h8=JmlPasien("08","02:00:01","03:00:00");
        h9=JmlPasien("09","02:00:01","03:00:00");
        h10=JmlPasien("10","02:00:01","03:00:00");
        h11=JmlPasien("11","02:00:01","03:00:00");
        h12=JmlPasien("12","02:00:01","03:00:00");
        h13=JmlPasien("13","02:00:01","03:00:00");
        h14=JmlPasien("14","02:00:01","03:00:00");
        h15=JmlPasien("15","02:00:01","03:00:00");
        h16=JmlPasien("16","02:00:01","03:00:00");
        h17=JmlPasien("17","02:00:01","03:00:00");
        h18=JmlPasien("18","02:00:01","03:00:00");
        h19=JmlPasien("19","02:00:01","03:00:00");
        h20=JmlPasien("20","02:00:01","03:00:00");
        h21=JmlPasien("21","02:00:01","03:00:00");
        h22=JmlPasien("22","02:00:01","03:00:00");
        h23=JmlPasien("23","02:00:01","03:00:00");
        h24=JmlPasien("24","02:00:01","03:00:00");
        h25=JmlPasien("25","02:00:01","03:00:00");
        h26=JmlPasien("26","02:00:01","03:00:00");
        h27=JmlPasien("27","02:00:01","03:00:00");
        h28=JmlPasien("28","02:00:01","03:00:00");
        h29=JmlPasien("29","02:00:01","03:00:00");
        h30=JmlPasien("30","02:00:01","03:00:00");
        h31=JmlPasien("31","02:00:01","03:00:00");
        tabMode.addRow(new Object[]{
            "03",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","03:00:01","04:00:00");
        h2=JmlPasien("02","03:00:01","04:00:00");
        h3=JmlPasien("03","03:00:01","04:00:00");
        h4=JmlPasien("04","03:00:01","04:00:00");
        h5=JmlPasien("05","03:00:01","04:00:00");
        h6=JmlPasien("06","03:00:01","04:00:00");
        h7=JmlPasien("07","03:00:01","04:00:00");
        h8=JmlPasien("08","03:00:01","04:00:00");
        h9=JmlPasien("09","03:00:01","04:00:00");
        h10=JmlPasien("10","03:00:01","04:00:00");
        h11=JmlPasien("11","03:00:01","04:00:00");
        h12=JmlPasien("12","03:00:01","04:00:00");
        h13=JmlPasien("13","03:00:01","04:00:00");
        h14=JmlPasien("14","03:00:01","04:00:00");
        h15=JmlPasien("15","03:00:01","04:00:00");
        h16=JmlPasien("16","03:00:01","04:00:00");
        h17=JmlPasien("17","03:00:01","04:00:00");
        h18=JmlPasien("18","03:00:01","04:00:00");
        h19=JmlPasien("19","03:00:01","04:00:00");
        h20=JmlPasien("20","03:00:01","04:00:00");
        h21=JmlPasien("21","03:00:01","04:00:00");
        h22=JmlPasien("22","03:00:01","04:00:00");
        h23=JmlPasien("23","03:00:01","04:00:00");
        h24=JmlPasien("24","03:00:01","04:00:00");
        h25=JmlPasien("25","03:00:01","04:00:00");
        h26=JmlPasien("26","03:00:01","04:00:00");
        h27=JmlPasien("27","03:00:01","04:00:00");
        h28=JmlPasien("28","03:00:01","04:00:00");
        h29=JmlPasien("29","03:00:01","04:00:00");
        h30=JmlPasien("30","03:00:01","04:00:00");
        h31=JmlPasien("31","03:00:01","04:00:00");
        tabMode.addRow(new Object[]{
            "04",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","04:00:01","05:00:00");
        h2=JmlPasien("02","04:00:01","05:00:00");
        h3=JmlPasien("03","04:00:01","05:00:00");
        h4=JmlPasien("04","04:00:01","05:00:00");
        h5=JmlPasien("05","04:00:01","05:00:00");
        h6=JmlPasien("06","04:00:01","05:00:00");
        h7=JmlPasien("07","04:00:01","05:00:00");
        h8=JmlPasien("08","04:00:01","05:00:00");
        h9=JmlPasien("09","04:00:01","05:00:00");
        h10=JmlPasien("10","04:00:01","05:00:00");
        h11=JmlPasien("11","04:00:01","05:00:00");
        h12=JmlPasien("12","04:00:01","05:00:00");
        h13=JmlPasien("13","04:00:01","05:00:00");
        h14=JmlPasien("14","04:00:01","05:00:00");
        h15=JmlPasien("15","04:00:01","05:00:00");
        h16=JmlPasien("16","04:00:01","05:00:00");
        h17=JmlPasien("17","04:00:01","05:00:00");
        h18=JmlPasien("18","04:00:01","05:00:00");
        h19=JmlPasien("19","04:00:01","05:00:00");
        h20=JmlPasien("20","04:00:01","05:00:00");
        h21=JmlPasien("21","04:00:01","05:00:00");
        h22=JmlPasien("22","04:00:01","05:00:00");
        h23=JmlPasien("23","04:00:01","05:00:00");
        h24=JmlPasien("24","04:00:01","05:00:00");
        h25=JmlPasien("25","04:00:01","05:00:00");
        h26=JmlPasien("26","04:00:01","05:00:00");
        h27=JmlPasien("27","04:00:01","05:00:00");
        h28=JmlPasien("28","04:00:01","05:00:00");
        h29=JmlPasien("29","04:00:01","05:00:00");
        h30=JmlPasien("30","04:00:01","05:00:00");
        h31=JmlPasien("31","04:00:01","05:00:00");
        tabMode.addRow(new Object[]{
            "05",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","05:00:01","06:00:00");
        h2=JmlPasien("02","05:00:01","06:00:00");
        h3=JmlPasien("03","05:00:01","06:00:00");
        h4=JmlPasien("04","05:00:01","06:00:00");
        h5=JmlPasien("05","05:00:01","06:00:00");
        h6=JmlPasien("06","05:00:01","06:00:00");
        h7=JmlPasien("07","05:00:01","06:00:00");
        h8=JmlPasien("08","05:00:01","06:00:00");
        h9=JmlPasien("09","05:00:01","06:00:00");
        h10=JmlPasien("10","05:00:01","06:00:00");
        h11=JmlPasien("11","05:00:01","06:00:00");
        h12=JmlPasien("12","05:00:01","06:00:00");
        h13=JmlPasien("13","05:00:01","06:00:00");
        h14=JmlPasien("14","05:00:01","06:00:00");
        h15=JmlPasien("15","05:00:01","06:00:00");
        h16=JmlPasien("16","05:00:01","06:00:00");
        h17=JmlPasien("17","05:00:01","06:00:00");
        h18=JmlPasien("18","05:00:01","06:00:00");
        h19=JmlPasien("19","05:00:01","06:00:00");
        h20=JmlPasien("20","05:00:01","06:00:00");
        h21=JmlPasien("21","05:00:01","06:00:00");
        h22=JmlPasien("22","05:00:01","06:00:00");
        h23=JmlPasien("23","05:00:01","06:00:00");
        h24=JmlPasien("24","05:00:01","06:00:00");
        h25=JmlPasien("25","05:00:01","06:00:00");
        h26=JmlPasien("26","05:00:01","06:00:00");
        h27=JmlPasien("27","05:00:01","06:00:00");
        h28=JmlPasien("28","05:00:01","06:00:00");
        h29=JmlPasien("29","05:00:01","06:00:00");
        h30=JmlPasien("30","05:00:01","06:00:00");
        h31=JmlPasien("31","05:00:01","06:00:00");
        tabMode.addRow(new Object[]{
            "06",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","06:00:01","07:00:00");
        h2=JmlPasien("02","06:00:01","07:00:00");
        h3=JmlPasien("03","06:00:01","07:00:00");
        h4=JmlPasien("04","06:00:01","07:00:00");
        h5=JmlPasien("05","06:00:01","07:00:00");
        h6=JmlPasien("06","06:00:01","07:00:00");
        h7=JmlPasien("07","06:00:01","07:00:00");
        h8=JmlPasien("08","06:00:01","07:00:00");
        h9=JmlPasien("09","06:00:01","07:00:00");
        h10=JmlPasien("10","06:00:01","07:00:00");
        h11=JmlPasien("11","06:00:01","07:00:00");
        h12=JmlPasien("12","06:00:01","07:00:00");
        h13=JmlPasien("13","06:00:01","07:00:00");
        h14=JmlPasien("14","06:00:01","07:00:00");
        h15=JmlPasien("15","06:00:01","07:00:00");
        h16=JmlPasien("16","06:00:01","07:00:00");
        h17=JmlPasien("17","06:00:01","07:00:00");
        h18=JmlPasien("18","06:00:01","07:00:00");
        h19=JmlPasien("19","06:00:01","07:00:00");
        h20=JmlPasien("20","06:00:01","07:00:00");
        h21=JmlPasien("21","06:00:01","07:00:00");
        h22=JmlPasien("22","06:00:01","07:00:00");
        h23=JmlPasien("23","06:00:01","07:00:00");
        h24=JmlPasien("24","06:00:01","07:00:00");
        h25=JmlPasien("25","06:00:01","07:00:00");
        h26=JmlPasien("26","06:00:01","07:00:00");
        h27=JmlPasien("27","06:00:01","07:00:00");
        h28=JmlPasien("28","06:00:01","07:00:00");
        h29=JmlPasien("29","06:00:01","07:00:00");
        h30=JmlPasien("30","06:00:01","07:00:00");
        h31=JmlPasien("31","06:00:01","07:00:00");
        tabMode.addRow(new Object[]{
            "07",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","07:00:01","08:00:00");
        h2=JmlPasien("02","07:00:01","08:00:00");
        h3=JmlPasien("03","07:00:01","08:00:00");
        h4=JmlPasien("04","07:00:01","08:00:00");
        h5=JmlPasien("05","07:00:01","08:00:00");
        h6=JmlPasien("06","07:00:01","08:00:00");
        h7=JmlPasien("07","07:00:01","08:00:00");
        h8=JmlPasien("08","07:00:01","08:00:00");
        h9=JmlPasien("09","07:00:01","08:00:00");
        h10=JmlPasien("10","07:00:01","08:00:00");
        h11=JmlPasien("11","07:00:01","08:00:00");
        h12=JmlPasien("12","07:00:01","08:00:00");
        h13=JmlPasien("13","07:00:01","08:00:00");
        h14=JmlPasien("14","07:00:01","08:00:00");
        h15=JmlPasien("15","07:00:01","08:00:00");
        h16=JmlPasien("16","07:00:01","08:00:00");
        h17=JmlPasien("17","07:00:01","08:00:00");
        h18=JmlPasien("18","07:00:01","08:00:00");
        h19=JmlPasien("19","07:00:01","08:00:00");
        h20=JmlPasien("20","07:00:01","08:00:00");
        h21=JmlPasien("21","07:00:01","08:00:00");
        h22=JmlPasien("22","07:00:01","08:00:00");
        h23=JmlPasien("23","07:00:01","08:00:00");
        h24=JmlPasien("24","07:00:01","08:00:00");
        h25=JmlPasien("25","07:00:01","08:00:00");
        h26=JmlPasien("26","07:00:01","08:00:00");
        h27=JmlPasien("27","07:00:01","08:00:00");
        h28=JmlPasien("28","07:00:01","08:00:00");
        h29=JmlPasien("29","07:00:01","08:00:00");
        h30=JmlPasien("30","07:00:01","08:00:00");
        h31=JmlPasien("31","07:00:01","08:00:00");
        tabMode.addRow(new Object[]{
            "08",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","08:00:01","09:00:00");
        h2=JmlPasien("02","08:00:01","09:00:00");
        h3=JmlPasien("03","08:00:01","09:00:00");
        h4=JmlPasien("04","08:00:01","09:00:00");
        h5=JmlPasien("05","08:00:01","09:00:00");
        h6=JmlPasien("06","08:00:01","09:00:00");
        h7=JmlPasien("07","08:00:01","09:00:00");
        h8=JmlPasien("08","08:00:01","09:00:00");
        h9=JmlPasien("09","08:00:01","09:00:00");
        h10=JmlPasien("10","08:00:01","09:00:00");
        h11=JmlPasien("11","08:00:01","09:00:00");
        h12=JmlPasien("12","08:00:01","09:00:00");
        h13=JmlPasien("13","08:00:01","09:00:00");
        h14=JmlPasien("14","08:00:01","09:00:00");
        h15=JmlPasien("15","08:00:01","09:00:00");
        h16=JmlPasien("16","08:00:01","09:00:00");
        h17=JmlPasien("17","08:00:01","09:00:00");
        h18=JmlPasien("18","08:00:01","09:00:00");
        h19=JmlPasien("19","08:00:01","09:00:00");
        h20=JmlPasien("20","08:00:01","09:00:00");
        h21=JmlPasien("21","08:00:01","09:00:00");
        h22=JmlPasien("22","08:00:01","09:00:00");
        h23=JmlPasien("23","08:00:01","09:00:00");
        h24=JmlPasien("24","08:00:01","09:00:00");
        h25=JmlPasien("25","08:00:01","09:00:00");
        h26=JmlPasien("26","08:00:01","09:00:00");
        h27=JmlPasien("27","08:00:01","09:00:00");
        h28=JmlPasien("28","08:00:01","09:00:00");
        h29=JmlPasien("29","08:00:01","09:00:00");
        h30=JmlPasien("30","08:00:01","09:00:00");
        h31=JmlPasien("31","08:00:01","09:00:00");
        tabMode.addRow(new Object[]{
            "09",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","09:00:01","10:00:00");
        h2=JmlPasien("02","09:00:01","10:00:00");
        h3=JmlPasien("03","09:00:01","10:00:00");
        h4=JmlPasien("04","09:00:01","10:00:00");
        h5=JmlPasien("05","09:00:01","10:00:00");
        h6=JmlPasien("06","09:00:01","10:00:00");
        h7=JmlPasien("07","09:00:01","10:00:00");
        h8=JmlPasien("08","09:00:01","10:00:00");
        h9=JmlPasien("09","09:00:01","10:00:00");
        h10=JmlPasien("10","09:00:01","10:00:00");
        h11=JmlPasien("11","09:00:01","10:00:00");
        h12=JmlPasien("12","09:00:01","10:00:00");
        h13=JmlPasien("13","09:00:01","10:00:00");
        h14=JmlPasien("14","09:00:01","10:00:00");
        h15=JmlPasien("15","09:00:01","10:00:00");
        h16=JmlPasien("16","09:00:01","10:00:00");
        h17=JmlPasien("17","09:00:01","10:00:00");
        h18=JmlPasien("18","09:00:01","10:00:00");
        h19=JmlPasien("19","09:00:01","10:00:00");
        h20=JmlPasien("20","09:00:01","10:00:00");
        h21=JmlPasien("21","09:00:01","10:00:00");
        h22=JmlPasien("22","09:00:01","10:00:00");
        h23=JmlPasien("23","09:00:01","10:00:00");
        h24=JmlPasien("24","09:00:01","10:00:00");
        h25=JmlPasien("25","09:00:01","10:00:00");
        h26=JmlPasien("26","09:00:01","10:00:00");
        h27=JmlPasien("27","09:00:01","10:00:00");
        h28=JmlPasien("28","09:00:01","10:00:00");
        h29=JmlPasien("29","09:00:01","10:00:00");
        h30=JmlPasien("30","09:00:01","10:00:00");
        h31=JmlPasien("31","09:00:01","10:00:00");
        tabMode.addRow(new Object[]{
            "10",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","10:00:01","11:00:00");
        h2=JmlPasien("02","10:00:01","11:00:00");
        h3=JmlPasien("03","10:00:01","11:00:00");
        h4=JmlPasien("04","10:00:01","11:00:00");
        h5=JmlPasien("05","10:00:01","11:00:00");
        h6=JmlPasien("06","10:00:01","11:00:00");
        h7=JmlPasien("07","10:00:01","11:00:00");
        h8=JmlPasien("08","10:00:01","11:00:00");
        h9=JmlPasien("09","10:00:01","11:00:00");
        h10=JmlPasien("10","10:00:01","11:00:00");
        h11=JmlPasien("11","10:00:01","11:00:00");
        h12=JmlPasien("12","10:00:01","11:00:00");
        h13=JmlPasien("13","10:00:01","11:00:00");
        h14=JmlPasien("14","10:00:01","11:00:00");
        h15=JmlPasien("15","10:00:01","11:00:00");
        h16=JmlPasien("16","10:00:01","11:00:00");
        h17=JmlPasien("17","10:00:01","11:00:00");
        h18=JmlPasien("18","10:00:01","11:00:00");
        h19=JmlPasien("19","10:00:01","11:00:00");
        h20=JmlPasien("20","10:00:01","11:00:00");
        h21=JmlPasien("21","10:00:01","11:00:00");
        h22=JmlPasien("22","10:00:01","11:00:00");
        h23=JmlPasien("23","10:00:01","11:00:00");
        h24=JmlPasien("24","10:00:01","11:00:00");
        h25=JmlPasien("25","10:00:01","11:00:00");
        h26=JmlPasien("26","10:00:01","11:00:00");
        h27=JmlPasien("27","10:00:01","11:00:00");
        h28=JmlPasien("28","10:00:01","11:00:00");
        h29=JmlPasien("29","10:00:01","11:00:00");
        h30=JmlPasien("30","10:00:01","11:00:00");
        h31=JmlPasien("31","10:00:01","11:00:00");
        tabMode.addRow(new Object[]{
            "11",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","11:00:01","12:00:00");
        h2=JmlPasien("02","11:00:01","12:00:00");
        h3=JmlPasien("03","11:00:01","12:00:00");
        h4=JmlPasien("04","11:00:01","12:00:00");
        h5=JmlPasien("05","11:00:01","12:00:00");
        h6=JmlPasien("06","11:00:01","12:00:00");
        h7=JmlPasien("07","11:00:01","12:00:00");
        h8=JmlPasien("08","11:00:01","12:00:00");
        h9=JmlPasien("09","11:00:01","12:00:00");
        h10=JmlPasien("10","11:00:01","12:00:00");
        h11=JmlPasien("11","11:00:01","12:00:00");
        h12=JmlPasien("12","11:00:01","12:00:00");
        h13=JmlPasien("13","11:00:01","12:00:00");
        h14=JmlPasien("14","11:00:01","12:00:00");
        h15=JmlPasien("15","11:00:01","12:00:00");
        h16=JmlPasien("16","11:00:01","12:00:00");
        h17=JmlPasien("17","11:00:01","12:00:00");
        h18=JmlPasien("18","11:00:01","12:00:00");
        h19=JmlPasien("19","11:00:01","12:00:00");
        h20=JmlPasien("20","11:00:01","12:00:00");
        h21=JmlPasien("21","11:00:01","12:00:00");
        h22=JmlPasien("22","11:00:01","12:00:00");
        h23=JmlPasien("23","11:00:01","12:00:00");
        h24=JmlPasien("24","11:00:01","12:00:00");
        h25=JmlPasien("25","11:00:01","12:00:00");
        h26=JmlPasien("26","11:00:01","12:00:00");
        h27=JmlPasien("27","11:00:01","12:00:00");
        h28=JmlPasien("28","11:00:01","12:00:00");
        h29=JmlPasien("29","11:00:01","12:00:00");
        h30=JmlPasien("30","11:00:01","12:00:00");
        h31=JmlPasien("31","11:00:01","12:00:00");
        tabMode.addRow(new Object[]{
            "12",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","12:00:01","13:00:00");
        h2=JmlPasien("02","12:00:01","13:00:00");
        h3=JmlPasien("03","12:00:01","13:00:00");
        h4=JmlPasien("04","12:00:01","13:00:00");
        h5=JmlPasien("05","12:00:01","13:00:00");
        h6=JmlPasien("06","12:00:01","13:00:00");
        h7=JmlPasien("07","12:00:01","13:00:00");
        h8=JmlPasien("08","12:00:01","13:00:00");
        h9=JmlPasien("09","12:00:01","13:00:00");
        h10=JmlPasien("10","12:00:01","13:00:00");
        h11=JmlPasien("11","12:00:01","13:00:00");
        h12=JmlPasien("12","12:00:01","13:00:00");
        h13=JmlPasien("13","12:00:01","13:00:00");
        h14=JmlPasien("14","12:00:01","13:00:00");
        h15=JmlPasien("15","12:00:01","13:00:00");
        h16=JmlPasien("16","12:00:01","13:00:00");
        h17=JmlPasien("17","12:00:01","13:00:00");
        h18=JmlPasien("18","12:00:01","13:00:00");
        h19=JmlPasien("19","12:00:01","13:00:00");
        h20=JmlPasien("20","12:00:01","13:00:00");
        h21=JmlPasien("21","12:00:01","13:00:00");
        h22=JmlPasien("22","12:00:01","13:00:00");
        h23=JmlPasien("23","12:00:01","13:00:00");
        h24=JmlPasien("24","12:00:01","13:00:00");
        h25=JmlPasien("25","12:00:01","13:00:00");
        h26=JmlPasien("26","12:00:01","13:00:00");
        h27=JmlPasien("27","12:00:01","13:00:00");
        h28=JmlPasien("28","12:00:01","13:00:00");
        h29=JmlPasien("29","12:00:01","13:00:00");
        h30=JmlPasien("30","12:00:01","13:00:00");
        h31=JmlPasien("31","12:00:01","13:00:00");
        tabMode.addRow(new Object[]{
            "13",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","13:00:01","14:00:00");
        h2=JmlPasien("02","13:00:01","14:00:00");
        h3=JmlPasien("03","13:00:01","14:00:00");
        h4=JmlPasien("04","13:00:01","14:00:00");
        h5=JmlPasien("05","13:00:01","14:00:00");
        h6=JmlPasien("06","13:00:01","14:00:00");
        h7=JmlPasien("07","13:00:01","14:00:00");
        h8=JmlPasien("08","13:00:01","14:00:00");
        h9=JmlPasien("09","13:00:01","14:00:00");
        h10=JmlPasien("10","13:00:01","14:00:00");
        h11=JmlPasien("11","13:00:01","14:00:00");
        h12=JmlPasien("12","13:00:01","14:00:00");
        h13=JmlPasien("13","13:00:01","14:00:00");
        h14=JmlPasien("14","13:00:01","14:00:00");
        h15=JmlPasien("15","13:00:01","14:00:00");
        h16=JmlPasien("16","13:00:01","14:00:00");
        h17=JmlPasien("17","13:00:01","14:00:00");
        h18=JmlPasien("18","13:00:01","14:00:00");
        h19=JmlPasien("19","13:00:01","14:00:00");
        h20=JmlPasien("20","13:00:01","14:00:00");
        h21=JmlPasien("21","13:00:01","14:00:00");
        h22=JmlPasien("22","13:00:01","14:00:00");
        h23=JmlPasien("23","13:00:01","14:00:00");
        h24=JmlPasien("24","13:00:01","14:00:00");
        h25=JmlPasien("25","13:00:01","14:00:00");
        h26=JmlPasien("26","13:00:01","14:00:00");
        h27=JmlPasien("27","13:00:01","14:00:00");
        h28=JmlPasien("28","13:00:01","14:00:00");
        h29=JmlPasien("29","13:00:01","14:00:00");
        h30=JmlPasien("30","13:00:01","14:00:00");
        h31=JmlPasien("31","13:00:01","14:00:00");
        tabMode.addRow(new Object[]{
            "14",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","14:00:01","15:00:00");
        h2=JmlPasien("02","14:00:01","15:00:00");
        h3=JmlPasien("03","14:00:01","15:00:00");
        h4=JmlPasien("04","14:00:01","15:00:00");
        h5=JmlPasien("05","14:00:01","15:00:00");
        h6=JmlPasien("06","14:00:01","15:00:00");
        h7=JmlPasien("07","14:00:01","15:00:00");
        h8=JmlPasien("08","14:00:01","15:00:00");
        h9=JmlPasien("09","14:00:01","15:00:00");
        h10=JmlPasien("10","14:00:01","15:00:00");
        h11=JmlPasien("11","14:00:01","15:00:00");
        h12=JmlPasien("12","14:00:01","15:00:00");
        h13=JmlPasien("13","14:00:01","15:00:00");
        h14=JmlPasien("14","14:00:01","15:00:00");
        h15=JmlPasien("15","14:00:01","15:00:00");
        h16=JmlPasien("16","14:00:01","15:00:00");
        h17=JmlPasien("17","14:00:01","15:00:00");
        h18=JmlPasien("18","14:00:01","15:00:00");
        h19=JmlPasien("19","14:00:01","15:00:00");
        h20=JmlPasien("20","14:00:01","15:00:00");
        h21=JmlPasien("21","14:00:01","15:00:00");
        h22=JmlPasien("22","14:00:01","15:00:00");
        h23=JmlPasien("23","14:00:01","15:00:00");
        h24=JmlPasien("24","14:00:01","15:00:00");
        h25=JmlPasien("25","14:00:01","15:00:00");
        h26=JmlPasien("26","14:00:01","15:00:00");
        h27=JmlPasien("27","14:00:01","15:00:00");
        h28=JmlPasien("28","14:00:01","15:00:00");
        h29=JmlPasien("29","14:00:01","15:00:00");
        h30=JmlPasien("30","14:00:01","15:00:00");
        h31=JmlPasien("31","14:00:01","15:00:00");
        tabMode.addRow(new Object[]{
            "15",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","15:00:01","16:00:00");
        h2=JmlPasien("02","15:00:01","16:00:00");
        h3=JmlPasien("03","15:00:01","16:00:00");
        h4=JmlPasien("04","15:00:01","16:00:00");
        h5=JmlPasien("05","15:00:01","16:00:00");
        h6=JmlPasien("06","15:00:01","16:00:00");
        h7=JmlPasien("07","15:00:01","16:00:00");
        h8=JmlPasien("08","15:00:01","16:00:00");
        h9=JmlPasien("09","15:00:01","16:00:00");
        h10=JmlPasien("10","15:00:01","16:00:00");
        h11=JmlPasien("11","15:00:01","16:00:00");
        h12=JmlPasien("12","15:00:01","16:00:00");
        h13=JmlPasien("13","15:00:01","16:00:00");
        h14=JmlPasien("14","15:00:01","16:00:00");
        h15=JmlPasien("15","15:00:01","16:00:00");
        h16=JmlPasien("16","15:00:01","16:00:00");
        h17=JmlPasien("17","15:00:01","16:00:00");
        h18=JmlPasien("18","15:00:01","16:00:00");
        h19=JmlPasien("19","15:00:01","16:00:00");
        h20=JmlPasien("20","15:00:01","16:00:00");
        h21=JmlPasien("21","15:00:01","16:00:00");
        h22=JmlPasien("22","15:00:01","16:00:00");
        h23=JmlPasien("23","15:00:01","16:00:00");
        h24=JmlPasien("24","15:00:01","16:00:00");
        h25=JmlPasien("25","15:00:01","16:00:00");
        h26=JmlPasien("26","15:00:01","16:00:00");
        h27=JmlPasien("27","15:00:01","16:00:00");
        h28=JmlPasien("28","15:00:01","16:00:00");
        h29=JmlPasien("29","15:00:01","16:00:00");
        h30=JmlPasien("30","15:00:01","16:00:00");
        h31=JmlPasien("31","15:00:01","16:00:00");
        tabMode.addRow(new Object[]{
            "16",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","16:00:01","17:00:00");
        h2=JmlPasien("02","16:00:01","17:00:00");
        h3=JmlPasien("03","16:00:01","17:00:00");
        h4=JmlPasien("04","16:00:01","17:00:00");
        h5=JmlPasien("05","16:00:01","17:00:00");
        h6=JmlPasien("06","16:00:01","17:00:00");
        h7=JmlPasien("07","16:00:01","17:00:00");
        h8=JmlPasien("08","16:00:01","17:00:00");
        h9=JmlPasien("09","16:00:01","17:00:00");
        h10=JmlPasien("10","16:00:01","17:00:00");
        h11=JmlPasien("11","16:00:01","17:00:00");
        h12=JmlPasien("12","16:00:01","17:00:00");
        h13=JmlPasien("13","16:00:01","17:00:00");
        h14=JmlPasien("14","16:00:01","17:00:00");
        h15=JmlPasien("15","16:00:01","17:00:00");
        h16=JmlPasien("16","16:00:01","17:00:00");
        h17=JmlPasien("17","16:00:01","17:00:00");
        h18=JmlPasien("18","16:00:01","17:00:00");
        h19=JmlPasien("19","16:00:01","17:00:00");
        h20=JmlPasien("20","16:00:01","17:00:00");
        h21=JmlPasien("21","16:00:01","17:00:00");
        h22=JmlPasien("22","16:00:01","17:00:00");
        h23=JmlPasien("23","16:00:01","17:00:00");
        h24=JmlPasien("24","16:00:01","17:00:00");
        h25=JmlPasien("25","16:00:01","17:00:00");
        h26=JmlPasien("26","16:00:01","17:00:00");
        h27=JmlPasien("27","16:00:01","17:00:00");
        h28=JmlPasien("28","16:00:01","17:00:00");
        h29=JmlPasien("29","16:00:01","17:00:00");
        h30=JmlPasien("30","16:00:01","17:00:00");
        h31=JmlPasien("31","16:00:01","17:00:00");
        tabMode.addRow(new Object[]{
            "17",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","17:00:01","18:00:00");
        h2=JmlPasien("02","17:00:01","18:00:00");
        h3=JmlPasien("03","17:00:01","18:00:00");
        h4=JmlPasien("04","17:00:01","18:00:00");
        h5=JmlPasien("05","17:00:01","18:00:00");
        h6=JmlPasien("06","17:00:01","18:00:00");
        h7=JmlPasien("07","17:00:01","18:00:00");
        h8=JmlPasien("08","17:00:01","18:00:00");
        h9=JmlPasien("09","17:00:01","18:00:00");
        h10=JmlPasien("10","17:00:01","18:00:00");
        h11=JmlPasien("11","17:00:01","18:00:00");
        h12=JmlPasien("12","17:00:01","18:00:00");
        h13=JmlPasien("13","17:00:01","18:00:00");
        h14=JmlPasien("14","17:00:01","18:00:00");
        h15=JmlPasien("15","17:00:01","18:00:00");
        h16=JmlPasien("16","17:00:01","18:00:00");
        h17=JmlPasien("17","17:00:01","18:00:00");
        h18=JmlPasien("18","17:00:01","18:00:00");
        h19=JmlPasien("19","17:00:01","18:00:00");
        h20=JmlPasien("20","17:00:01","18:00:00");
        h21=JmlPasien("21","17:00:01","18:00:00");
        h22=JmlPasien("22","17:00:01","18:00:00");
        h23=JmlPasien("23","17:00:01","18:00:00");
        h24=JmlPasien("24","17:00:01","18:00:00");
        h25=JmlPasien("25","17:00:01","18:00:00");
        h26=JmlPasien("26","17:00:01","18:00:00");
        h27=JmlPasien("27","17:00:01","18:00:00");
        h28=JmlPasien("28","17:00:01","18:00:00");
        h29=JmlPasien("29","17:00:01","18:00:00");
        h30=JmlPasien("30","17:00:01","18:00:00");
        h31=JmlPasien("31","17:00:01","18:00:00");
        tabMode.addRow(new Object[]{
            "18",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","18:00:01","19:00:00");
        h2=JmlPasien("02","18:00:01","19:00:00");
        h3=JmlPasien("03","18:00:01","19:00:00");
        h4=JmlPasien("04","18:00:01","19:00:00");
        h5=JmlPasien("05","18:00:01","19:00:00");
        h6=JmlPasien("06","18:00:01","19:00:00");
        h7=JmlPasien("07","18:00:01","19:00:00");
        h8=JmlPasien("08","18:00:01","19:00:00");
        h9=JmlPasien("09","18:00:01","19:00:00");
        h10=JmlPasien("10","18:00:01","19:00:00");
        h11=JmlPasien("11","18:00:01","19:00:00");
        h12=JmlPasien("12","18:00:01","19:00:00");
        h13=JmlPasien("13","18:00:01","19:00:00");
        h14=JmlPasien("14","18:00:01","19:00:00");
        h15=JmlPasien("15","18:00:01","19:00:00");
        h16=JmlPasien("16","18:00:01","19:00:00");
        h17=JmlPasien("17","18:00:01","19:00:00");
        h18=JmlPasien("18","18:00:01","19:00:00");
        h19=JmlPasien("19","18:00:01","19:00:00");
        h20=JmlPasien("20","18:00:01","19:00:00");
        h21=JmlPasien("21","18:00:01","19:00:00");
        h22=JmlPasien("22","18:00:01","19:00:00");
        h23=JmlPasien("23","18:00:01","19:00:00");
        h24=JmlPasien("24","18:00:01","19:00:00");
        h25=JmlPasien("25","18:00:01","19:00:00");
        h26=JmlPasien("26","18:00:01","19:00:00");
        h27=JmlPasien("27","18:00:01","19:00:00");
        h28=JmlPasien("28","18:00:01","19:00:00");
        h29=JmlPasien("29","18:00:01","19:00:00");
        h30=JmlPasien("30","18:00:01","19:00:00");
        h31=JmlPasien("31","18:00:01","19:00:00");
        tabMode.addRow(new Object[]{
            "19",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","19:00:01","20:00:00");
        h2=JmlPasien("02","19:00:01","20:00:00");
        h3=JmlPasien("03","19:00:01","20:00:00");
        h4=JmlPasien("04","19:00:01","20:00:00");
        h5=JmlPasien("05","19:00:01","20:00:00");
        h6=JmlPasien("06","19:00:01","20:00:00");
        h7=JmlPasien("07","19:00:01","20:00:00");
        h8=JmlPasien("08","19:00:01","20:00:00");
        h9=JmlPasien("09","19:00:01","20:00:00");
        h10=JmlPasien("10","19:00:01","20:00:00");
        h11=JmlPasien("11","19:00:01","20:00:00");
        h12=JmlPasien("12","19:00:01","20:00:00");
        h13=JmlPasien("13","19:00:01","20:00:00");
        h14=JmlPasien("14","19:00:01","20:00:00");
        h15=JmlPasien("15","19:00:01","20:00:00");
        h16=JmlPasien("16","19:00:01","20:00:00");
        h17=JmlPasien("17","19:00:01","20:00:00");
        h18=JmlPasien("18","19:00:01","20:00:00");
        h19=JmlPasien("19","19:00:01","20:00:00");
        h20=JmlPasien("20","19:00:01","20:00:00");
        h21=JmlPasien("21","19:00:01","20:00:00");
        h22=JmlPasien("22","19:00:01","20:00:00");
        h23=JmlPasien("23","19:00:01","20:00:00");
        h24=JmlPasien("24","19:00:01","20:00:00");
        h25=JmlPasien("25","19:00:01","20:00:00");
        h26=JmlPasien("26","19:00:01","20:00:00");
        h27=JmlPasien("27","19:00:01","20:00:00");
        h28=JmlPasien("28","19:00:01","20:00:00");
        h29=JmlPasien("29","19:00:01","20:00:00");
        h30=JmlPasien("30","19:00:01","20:00:00");
        h31=JmlPasien("31","19:00:01","20:00:00");
        tabMode.addRow(new Object[]{
            "20",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","20:00:01","21:00:00");
        h2=JmlPasien("02","20:00:01","21:00:00");
        h3=JmlPasien("03","20:00:01","21:00:00");
        h4=JmlPasien("04","20:00:01","21:00:00");
        h5=JmlPasien("05","20:00:01","21:00:00");
        h6=JmlPasien("06","20:00:01","21:00:00");
        h7=JmlPasien("07","20:00:01","21:00:00");
        h8=JmlPasien("08","20:00:01","21:00:00");
        h9=JmlPasien("09","20:00:01","21:00:00");
        h10=JmlPasien("10","20:00:01","21:00:00");
        h11=JmlPasien("11","20:00:01","21:00:00");
        h12=JmlPasien("12","20:00:01","21:00:00");
        h13=JmlPasien("13","20:00:01","21:00:00");
        h14=JmlPasien("14","20:00:01","21:00:00");
        h15=JmlPasien("15","20:00:01","21:00:00");
        h16=JmlPasien("16","20:00:01","21:00:00");
        h17=JmlPasien("17","20:00:01","21:00:00");
        h18=JmlPasien("18","20:00:01","21:00:00");
        h19=JmlPasien("19","20:00:01","21:00:00");
        h20=JmlPasien("20","20:00:01","21:00:00");
        h21=JmlPasien("21","20:00:01","21:00:00");
        h22=JmlPasien("22","20:00:01","21:00:00");
        h23=JmlPasien("23","20:00:01","21:00:00");
        h24=JmlPasien("24","20:00:01","21:00:00");
        h25=JmlPasien("25","20:00:01","21:00:00");
        h26=JmlPasien("26","20:00:01","21:00:00");
        h27=JmlPasien("27","20:00:01","21:00:00");
        h28=JmlPasien("28","20:00:01","21:00:00");
        h29=JmlPasien("29","20:00:01","21:00:00");
        h30=JmlPasien("30","20:00:01","21:00:00");
        h31=JmlPasien("31","20:00:01","21:00:00");
        tabMode.addRow(new Object[]{
            "21",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","21:00:01","22:00:00");
        h2=JmlPasien("02","21:00:01","22:00:00");
        h3=JmlPasien("03","21:00:01","22:00:00");
        h4=JmlPasien("04","21:00:01","22:00:00");
        h5=JmlPasien("05","21:00:01","22:00:00");
        h6=JmlPasien("06","21:00:01","22:00:00");
        h7=JmlPasien("07","21:00:01","22:00:00");
        h8=JmlPasien("08","21:00:01","22:00:00");
        h9=JmlPasien("09","21:00:01","22:00:00");
        h10=JmlPasien("10","21:00:01","22:00:00");
        h11=JmlPasien("11","21:00:01","22:00:00");
        h12=JmlPasien("12","21:00:01","22:00:00");
        h13=JmlPasien("13","21:00:01","22:00:00");
        h14=JmlPasien("14","21:00:01","22:00:00");
        h15=JmlPasien("15","21:00:01","22:00:00");
        h16=JmlPasien("16","21:00:01","22:00:00");
        h17=JmlPasien("17","21:00:01","22:00:00");
        h18=JmlPasien("18","21:00:01","22:00:00");
        h19=JmlPasien("19","21:00:01","22:00:00");
        h20=JmlPasien("20","21:00:01","22:00:00");
        h21=JmlPasien("21","21:00:01","22:00:00");
        h22=JmlPasien("22","21:00:01","22:00:00");
        h23=JmlPasien("23","21:00:01","22:00:00");
        h24=JmlPasien("24","21:00:01","22:00:00");
        h25=JmlPasien("25","21:00:01","22:00:00");
        h26=JmlPasien("26","21:00:01","22:00:00");
        h27=JmlPasien("27","21:00:01","22:00:00");
        h28=JmlPasien("28","21:00:01","22:00:00");
        h29=JmlPasien("29","21:00:01","22:00:00");
        h30=JmlPasien("30","21:00:01","22:00:00");
        h31=JmlPasien("31","21:00:01","22:00:00");
        tabMode.addRow(new Object[]{
            "22",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","22:00:01","23:00:00");
        h2=JmlPasien("02","22:00:01","23:00:00");
        h3=JmlPasien("03","22:00:01","23:00:00");
        h4=JmlPasien("04","22:00:01","23:00:00");
        h5=JmlPasien("05","22:00:01","23:00:00");
        h6=JmlPasien("06","22:00:01","23:00:00");
        h7=JmlPasien("07","22:00:01","23:00:00");
        h8=JmlPasien("08","22:00:01","23:00:00");
        h9=JmlPasien("09","22:00:01","23:00:00");
        h10=JmlPasien("10","22:00:01","23:00:00");
        h11=JmlPasien("11","22:00:01","23:00:00");
        h12=JmlPasien("12","22:00:01","23:00:00");
        h13=JmlPasien("13","22:00:01","23:00:00");
        h14=JmlPasien("14","22:00:01","23:00:00");
        h15=JmlPasien("15","22:00:01","23:00:00");
        h16=JmlPasien("16","22:00:01","23:00:00");
        h17=JmlPasien("17","22:00:01","23:00:00");
        h18=JmlPasien("18","22:00:01","23:00:00");
        h19=JmlPasien("19","22:00:01","23:00:00");
        h20=JmlPasien("20","22:00:01","23:00:00");
        h21=JmlPasien("21","22:00:01","23:00:00");
        h22=JmlPasien("22","22:00:01","23:00:00");
        h23=JmlPasien("23","22:00:01","23:00:00");
        h24=JmlPasien("24","22:00:01","23:00:00");
        h25=JmlPasien("25","22:00:01","23:00:00");
        h26=JmlPasien("26","22:00:01","23:00:00");
        h27=JmlPasien("27","22:00:01","23:00:00");
        h28=JmlPasien("28","22:00:01","23:00:00");
        h29=JmlPasien("29","22:00:01","23:00:00");
        h30=JmlPasien("30","22:00:01","23:00:00");
        h31=JmlPasien("31","22:00:01","23:00:00");
        tabMode.addRow(new Object[]{
            "23",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        
        h1=JmlPasien("01","23:00:01","24:00:00");
        h2=JmlPasien("02","23:00:01","24:00:00");
        h3=JmlPasien("03","23:00:01","24:00:00");
        h4=JmlPasien("04","23:00:01","24:00:00");
        h5=JmlPasien("05","23:00:01","24:00:00");
        h6=JmlPasien("06","23:00:01","24:00:00");
        h7=JmlPasien("07","23:00:01","24:00:00");
        h8=JmlPasien("08","23:00:01","24:00:00");
        h9=JmlPasien("09","23:00:01","24:00:00");
        h10=JmlPasien("10","23:00:01","24:00:00");
        h11=JmlPasien("11","23:00:01","24:00:00");
        h12=JmlPasien("12","23:00:01","24:00:00");
        h13=JmlPasien("13","23:00:01","24:00:00");
        h14=JmlPasien("14","23:00:01","24:00:00");
        h15=JmlPasien("15","23:00:01","24:00:00");
        h16=JmlPasien("16","23:00:01","24:00:00");
        h17=JmlPasien("17","23:00:01","24:00:00");
        h18=JmlPasien("18","23:00:01","24:00:00");
        h19=JmlPasien("19","23:00:01","24:00:00");
        h20=JmlPasien("20","23:00:01","24:00:00");
        h21=JmlPasien("21","23:00:01","24:00:00");
        h22=JmlPasien("22","23:00:01","24:00:00");
        h23=JmlPasien("23","23:00:01","24:00:00");
        h24=JmlPasien("24","23:00:01","24:00:00");
        h25=JmlPasien("25","23:00:01","24:00:00");
        h26=JmlPasien("26","23:00:01","24:00:00");
        h27=JmlPasien("27","23:00:01","24:00:00");
        h28=JmlPasien("28","23:00:01","24:00:00");
        h29=JmlPasien("29","23:00:01","24:00:00");
        h30=JmlPasien("30","23:00:01","24:00:00");
        h31=JmlPasien("31","23:00:01","24:00:00");
        tabMode.addRow(new Object[]{
            "24",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    
    String konversi(int year, int month, int day){
        dateString = String.format("%d-%d-%d", year, month, day);        
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (Exception ex) {
            Logger.getLogger(LaporanKedatanganPasienPerJam.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Then get the day of week from the Date based on specific locale.
        dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);

        switch (dayOfWeek) {
            case "Monday":
                hari="Sn";
                break;
            case "Tuesday":
                hari="Sl";
                break;
            case "Wednesday":
                hari="Rb";
                break;
            case "Thursday":
                hari="Km";
                break;
            case "Friday":
                hari="Jm";
                break;
            case "Saturday":
                hari="Sb";
                break;
            case "Sunday":
                hari="Mg";
                break;
        }
        return hari;
    }
    
    private double JmlPasien(String tanggal,String jam1,String jam2){
        return Sequel.cariInteger(
                "select count(*) from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.tgl_registrasi='"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-"+tanggal+"' and reg_periksa.jam_reg between '"+jam1+"' and '"+jam2+"' "+
                "and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoli.getText()+NmPoli.getText()+"%' and concat(reg_periksa.kd_dokter,dokter.nm_dokter) like '%"+KdDokter.getText()+NmDokter.getText()+"%' "+
                "and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%' and reg_periksa.stts_daftar like '%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%'");
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

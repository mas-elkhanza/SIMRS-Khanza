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
public class KedatanganPasienPerJam extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
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
    public KedatanganPasienPerJam(java.awt.Frame parent, boolean modal) {
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
                    KdDokterRalanDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokterRalanDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
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
                    KdPoliRalanDokter.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NmPoliRalanDokter.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
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
                    KdCaraBayarRalanDokter.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    NmCaraBayarRalanDokter.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
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

        KdCaraBayarRalanDokter = new widget.TextBox();
        KdPoliRalanDokter = new widget.TextBox();
        KdDokterRalanDokter = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJadwal = new widget.Table();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        ThnCari = new widget.ComboBox();
        BlnCari = new widget.ComboBox();
        BtnCari = new widget.Button();
        label12 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        NmDokterRalanDokter = new widget.TextBox();
        BtnDokterRalanDokter = new widget.Button();
        label19 = new widget.Label();
        NmCaraBayarRalanDokter = new widget.TextBox();
        BtnCaraBayarRalanDokter = new widget.Button();
        label20 = new widget.Label();
        NmPoliRalanDokter = new widget.TextBox();
        BtnPoliRalanDokter = new widget.Button();

        KdCaraBayarRalanDokter.setEditable(false);
        KdCaraBayarRalanDokter.setName("KdCaraBayarRalanDokter"); // NOI18N
        KdCaraBayarRalanDokter.setPreferredSize(new java.awt.Dimension(50, 23));

        KdPoliRalanDokter.setEditable(false);
        KdPoliRalanDokter.setName("KdPoliRalanDokter"); // NOI18N
        KdPoliRalanDokter.setPreferredSize(new java.awt.Dimension(50, 23));

        KdDokterRalanDokter.setEditable(false);
        KdDokterRalanDokter.setName("KdDokterRalanDokter"); // NOI18N
        KdDokterRalanDokter.setPreferredSize(new java.awt.Dimension(80, 23));

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
        label12.setPreferredSize(new java.awt.Dimension(42, 23));
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

        NmDokterRalanDokter.setEditable(false);
        NmDokterRalanDokter.setName("NmDokterRalanDokter"); // NOI18N
        NmDokterRalanDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmDokterRalanDokter);

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

        NmCaraBayarRalanDokter.setEditable(false);
        NmCaraBayarRalanDokter.setName("NmCaraBayarRalanDokter"); // NOI18N
        NmCaraBayarRalanDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmCaraBayarRalanDokter);

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

        NmPoliRalanDokter.setEditable(false);
        NmPoliRalanDokter.setName("NmPoliRalanDokter"); // NOI18N
        NmPoliRalanDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmPoliRalanDokter);

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
        KdDokterRalanDokter.setText("");
        NmDokterRalanDokter.setText("");
        KdPoliRalanDokter.setText("");
        NmPoliRalanDokter.setText("");
        KdCaraBayarRalanDokter.setText("");
        NmCaraBayarRalanDokter.setText("");
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
                                tabMode.getValueAt(r,1).toString()+"','"+
                                tabMode.getValueAt(r,4).toString()+"','"+
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
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,32).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,33).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,34).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,35).toString()))+"','"+
                                Valid.SetAngka3(Double.parseDouble(tabMode.getValueAt(r,36).toString()))+"','',''","Rekap Presensi"); 
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
            KedatanganPasienPerJam dialog = new KedatanganPasienPerJam(new javax.swing.JFrame(), true);
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
    private widget.TextBox KdCaraBayarRalanDokter;
    private widget.TextBox KdDokterRalanDokter;
    private widget.TextBox KdPoliRalanDokter;
    private widget.TextBox NmCaraBayarRalanDokter;
    private widget.TextBox NmDokterRalanDokter;
    private widget.TextBox NmPoliRalanDokter;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ComboBox ThnCari;
    private widget.InternalFrame internalFrame1;
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
        
        for (int i = 0; i < 33; i++) {
            TableColumn column = tbJadwal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else{
                column.setPreferredWidth(65);
            }
        }
        tbJadwal.setDefaultRenderer(Object.class, new WarnaTable());
        
        Valid.tabelKosong(tabMode);
        /*h1=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-01",rs.getString("kode_brng"));
        h2=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-02",rs.getString("kode_brng"));
        h3=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-03",rs.getString("kode_brng"));
        h4=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-04",rs.getString("kode_brng"));
        h5=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-05",rs.getString("kode_brng"));
        h6=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-06",rs.getString("kode_brng"));
        h7=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-07",rs.getString("kode_brng"));
        h8=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-08",rs.getString("kode_brng"));
        h9=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-09",rs.getString("kode_brng"));
        h10=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-10",rs.getString("kode_brng"));
        h11=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-11",rs.getString("kode_brng"));
        h12=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-12",rs.getString("kode_brng"));
        h13=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-13",rs.getString("kode_brng"));
        h14=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-14",rs.getString("kode_brng"));
        h15=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-15",rs.getString("kode_brng"));
        h16=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-16",rs.getString("kode_brng"));
        h17=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-17",rs.getString("kode_brng"));
        h18=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-18",rs.getString("kode_brng"));
        h19=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-19",rs.getString("kode_brng"));
        h20=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-20",rs.getString("kode_brng"));
        h21=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-21",rs.getString("kode_brng"));
        h22=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-22",rs.getString("kode_brng"));
        h23=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-23",rs.getString("kode_brng"));
        h24=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-24",rs.getString("kode_brng"));
        h25=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-25",rs.getString("kode_brng"));
        h26=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-26",rs.getString("kode_brng"));
        h27=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-27",rs.getString("kode_brng"));
        h28=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-28",rs.getString("kode_brng"));
        h29=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-29",rs.getString("kode_brng"));
        h30=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-30",rs.getString("kode_brng"));
        h31=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-31",rs.getString("kode_brng"));*/

        tabMode.addRow(new Object[]{
            "01",h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
            (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
        });
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    
    String konversi(int year, int month, int day){
        dateString = String.format("%d-%d-%d", year, month, day);        
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (Exception ex) {
            Logger.getLogger(KedatanganPasienPerJam.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Then get the day of week from the Date based on specific locale.
        dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);

        switch (dayOfWeek) {
            case "Monday":
                hari="Senin";
                break;
            case "Tuesday":
                hari="Selasa";
                break;
            case "Wednesday":
                hari="Rabu";
                break;
            case "Thursday":
                hari="Kamis";
                break;
            case "Friday":
                hari="Jumat";
                break;
            case "Saturday":
                hari="Sabtu";
                break;
            case "Sunday":
                hari="Minggu";
                break;
        }
        return hari;
    }
    
    private double JmlObat(String tanggal,String kodebarang){
        return Sequel.cariIsiAngka("select sum(detailjual.jumlah)"+
                        " from penjualan inner join detailjual "+
                        " on penjualan.nota_jual=detailjual.nota_jual "+
                        " where detailjual.kode_brng='"+kodebarang+"' and penjualan.tgl_jual=?",tanggal);
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

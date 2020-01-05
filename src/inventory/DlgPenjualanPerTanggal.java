/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJadwal.java
 *
 * Created on May 22, 2010, 10:25:16 PM
 */

package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;

/**
 *
 * @author dosen
 */
public class DlgPenjualanPerTanggal extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String dateString,dayOfWeek,hari,lokasi="";
    private double h1=0,h2=0,h3=0,h4=0,h5=0,h6=0,h7=0,h8=0,h9=0,h10=0,h11=0,h12=0,h13=0,
                   h14=0,h15=0,h16=0,h17=0,h18=0,h19=0,h20=0,h21=0,h22=0,h23=0,h24=0,h25=0,h26=0,h27=0,h28=0,h29=0,h30=0,h31=0 ;
    private Date date = null;
    private int i=0;
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, false);

    /** Creates new form DlgJadwal
     * @param parent
     * @param modal */
    public DlgPenjualanPerTanggal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        tbJadwal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJadwal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Valid.LoadTahun(ThnCari);


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
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }
                
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kategori.getTable().getSelectedRow() != -1) {
                    nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (bangsal.getTable().getSelectedRow() != -1) {
                    lokasi=bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString();
                    tampil(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                bangsal.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
    }
   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppLokasi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJadwal = new widget.Table();
        panelBiasa1 = new widget.PanelBiasa();
        label20 = new widget.Label();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label22 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label23 = new widget.Label();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        ThnCari = new widget.ComboBox();
        BlnCari = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppLokasi.setBackground(new java.awt.Color(255, 255, 254));
        ppLokasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppLokasi.setForeground(new java.awt.Color(50, 50, 50));
        ppLokasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppLokasi.setText("Tampilkan Per Lokasi");
        ppLokasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppLokasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppLokasi.setName("ppLokasi"); // NOI18N
        ppLokasi.setPreferredSize(new java.awt.Dimension(180, 25));
        ppLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppLokasiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppLokasi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Penjualan Bebas Obat/Alkes/BHP Per Tanggal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJadwal.setComponentPopupMenu(jPopupMenu1);
        tbJadwal.setName("tbJadwal"); // NOI18N
        tbJadwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJadwalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJadwal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(1023, 47));
        panelBiasa1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 10));

        label20.setText("Jenis :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(40, 23));
        panelBiasa1.add(label20);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(206, 23));
        panelBiasa1.add(nmjns);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnJenis);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        panelBiasa1.add(label22);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(206, 23));
        panelBiasa1.add(nmkategori);

        BtnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKategori.setMnemonic('2');
        BtnKategori.setToolTipText("Alt+2");
        BtnKategori.setName("BtnKategori"); // NOI18N
        BtnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKategoriActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnKategori);

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        panelBiasa1.add(label23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(206, 23));
        panelBiasa1.add(nmgolongan);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnGolongan);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

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

        jLabel6.setText("Keyword :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('4');
        BtnAll.setToolTipText("Alt+4");
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
        panelGlass8.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass8.add(LCount);

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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        lokasi="";
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
        nmgolongan.setText("");
        nmjns.setText("");
        nmkategori.setText("");
        lokasi="";
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari,BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void tbJadwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJadwalKeyPressed
        
    }//GEN-LAST:event_tbJadwalKeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnAll, TCari);
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
                if(lokasi.equals("")){
                    Valid.MyReport("rptPenjualanPerTanggal.jasper","report","::[ Penjualan Bebas Obat/Alkes/BHP Per Tanggal ]::",param);            
                }else if(!lokasi.equals("")){                    
                    param.put("bangsal",lokasi);
                    Valid.MyReport("rptPenjualanPerTanggal2.jasper","report","::[ Penjualan Bebas Obat/Alkes/BHP Per Tanggal ]::",param);            
                }
                                         
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

    private void ppLokasiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppLokasiBtnPrintActionPerformed
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_ppLokasiBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPenjualanPerTanggal dialog = new DlgPenjualanPerTanggal(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnGolongan;
    private widget.Button BtnJenis;
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.ComboBox ThnCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label11;
    private widget.Label label20;
    private widget.Label label22;
    private widget.Label label23;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppLokasi;
    private widget.Table tbJadwal;
    // End of variables declaration//GEN-END:variables

    private void tampil() {  
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Object[] row={"Nama Obat/Alkes/BHP","Satuan","Jenis","Kategori","Golongan",
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
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbJadwal.setModel(tabMode);
        
        for (int i = 0; i < 37; i++) {
            TableColumn column = tbJadwal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(250);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else{
                column.setPreferredWidth(60);
            }
        }
        tbJadwal.setDefaultRenderer(Object.class, new WarnaTable());
        
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                  "select databarang.kode_brng, databarang.nama_brng, "
                + " kodesatuan.satuan,jenis.nama as jenis,kategori_barang.nama as kategori,"
                + " golongan_barang.nama as golongan from databarang inner join kodesatuan "
                + " inner join jenis inner join golongan_barang inner join kategori_barang "
                + " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns "
                + " and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "
                + " where databarang.status='1' and jenis.nama like ? and kategori_barang.nama like ? and golongan_barang.nama like ? and databarang.kode_brng like ? or "
                + " databarang.status='1' and jenis.nama like ? and kategori_barang.nama like ? and golongan_barang.nama like ? and databarang.nama_brng like ? or "
                + " databarang.status='1' and jenis.nama like ? and kategori_barang.nama like ? and golongan_barang.nama like ? and kodesatuan.satuan like ? "
                + " order by databarang.nama_brng");
            try {
                ps.setString(1,"%"+nmjns.getText().trim()+"%");
                ps.setString(2,"%"+nmkategori.getText().trim()+"%");
                ps.setString(3,"%"+nmgolongan.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+nmjns.getText().trim()+"%");
                ps.setString(6,"%"+nmkategori.getText().trim()+"%");
                ps.setString(7,"%"+nmgolongan.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                ps.setString(9,"%"+nmjns.getText().trim()+"%");
                ps.setString(10,"%"+nmkategori.getText().trim()+"%");
                ps.setString(11,"%"+nmgolongan.getText().trim()+"%");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    h1=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-01",rs.getString("kode_brng"));
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
                    h31=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-31",rs.getString("kode_brng"));
                    
                    tabMode.addRow(new Object[]{
                        rs.getString("kode_brng")+" "+rs.getString("nama_brng"),rs.getString("satuan"),rs.getString("jenis"),rs.getString("kategori"),rs.getString("golongan"),
                        h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
                        (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
                    });
                    i++;
                }
            } catch (Exception e) {
                System.out.println("inventory.DlgObatPerTanggal.tampil() : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void tampil(String lokasi) {  
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Object[] row={"Nama Obat/Alkes/BHP","Satuan","Jenis","Kategori","Golongan",
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
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbJadwal.setModel(tabMode);
        
        for (int i = 0; i < 37; i++) {
            TableColumn column = tbJadwal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(250);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else{
                column.setPreferredWidth(60);
            }
        }
        tbJadwal.setDefaultRenderer(Object.class, new WarnaTable());
        
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                  "select databarang.kode_brng, databarang.nama_brng, "
                + " kodesatuan.satuan,jenis.nama as jenis,kategori_barang.nama as kategori,"
                + " golongan_barang.nama as golongan from databarang inner join kodesatuan "
                + " inner join jenis inner join golongan_barang inner join kategori_barang "
                + " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns "
                + " and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "
                + " where databarang.status='1' and jenis.nama like ? and kategori_barang.nama like ? and golongan_barang.nama like ? and databarang.kode_brng like ? or "
                + " databarang.status='1' and jenis.nama like ? and kategori_barang.nama like ? and golongan_barang.nama like ? and databarang.nama_brng like ? or "
                + " databarang.status='1' and jenis.nama like ? and kategori_barang.nama like ? and golongan_barang.nama like ? and kodesatuan.satuan like ? "
                + " order by databarang.nama_brng");
            try {
                ps.setString(1,"%"+nmjns.getText().trim()+"%");
                ps.setString(2,"%"+nmkategori.getText().trim()+"%");
                ps.setString(3,"%"+nmgolongan.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+nmjns.getText().trim()+"%");
                ps.setString(6,"%"+nmkategori.getText().trim()+"%");
                ps.setString(7,"%"+nmgolongan.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                ps.setString(9,"%"+nmjns.getText().trim()+"%");
                ps.setString(10,"%"+nmkategori.getText().trim()+"%");
                ps.setString(11,"%"+nmgolongan.getText().trim()+"%");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    h1=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-01",rs.getString("kode_brng"),lokasi);
                    h2=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-02",rs.getString("kode_brng"),lokasi);
                    h3=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-03",rs.getString("kode_brng"),lokasi);
                    h4=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-04",rs.getString("kode_brng"),lokasi);
                    h5=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-05",rs.getString("kode_brng"),lokasi);
                    h6=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-06",rs.getString("kode_brng"),lokasi);
                    h7=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-07",rs.getString("kode_brng"),lokasi);
                    h8=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-08",rs.getString("kode_brng"),lokasi);
                    h9=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-09",rs.getString("kode_brng"),lokasi);
                    h10=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-10",rs.getString("kode_brng"),lokasi);
                    h11=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-11",rs.getString("kode_brng"),lokasi);
                    h12=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-12",rs.getString("kode_brng"),lokasi);
                    h13=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-13",rs.getString("kode_brng"),lokasi);
                    h14=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-14",rs.getString("kode_brng"),lokasi);
                    h15=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-15",rs.getString("kode_brng"),lokasi);
                    h16=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-16",rs.getString("kode_brng"),lokasi);
                    h17=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-17",rs.getString("kode_brng"),lokasi);
                    h18=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-18",rs.getString("kode_brng"),lokasi);
                    h19=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-19",rs.getString("kode_brng"),lokasi);
                    h20=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-20",rs.getString("kode_brng"),lokasi);
                    h21=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-21",rs.getString("kode_brng"),lokasi);
                    h22=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-22",rs.getString("kode_brng"),lokasi);
                    h23=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-23",rs.getString("kode_brng"),lokasi);
                    h24=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-24",rs.getString("kode_brng"),lokasi);
                    h25=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-25",rs.getString("kode_brng"),lokasi);
                    h26=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-26",rs.getString("kode_brng"),lokasi);
                    h27=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-27",rs.getString("kode_brng"),lokasi);
                    h28=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-28",rs.getString("kode_brng"),lokasi);
                    h29=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-29",rs.getString("kode_brng"),lokasi);
                    h30=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-30",rs.getString("kode_brng"),lokasi);
                    h31=JmlObat(ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"-31",rs.getString("kode_brng"),lokasi);
                    
                    tabMode.addRow(new Object[]{
                        rs.getString("kode_brng")+" "+rs.getString("nama_brng"),rs.getString("satuan"),rs.getString("jenis"),rs.getString("kategori"),rs.getString("golongan"),
                        h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31,
                        (h1+h2+h3+h4+h5+h6+h7+h8+h9+h10+h11+h12+h13+h14+h15+h16+h17+h18+h19+h20+h21+h22+h23+h24+h25+h26+h27+h28+h29+h30+h31)
                    });
                    i++;
                }
            } catch (Exception e) {
                System.out.println("inventory.DlgObatPerTanggal.tampil() : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    String konversi(int year, int month, int day){
        dateString = String.format("%d-%d-%d", year, month, day);        
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (Exception ex) {
            Logger.getLogger(DlgPenjualanPerTanggal.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private double JmlObat(String tanggal,String kodebarang,String lokasi){
        return Sequel.cariIsiAngka("select sum(detailjual.jumlah)"+
                        " from penjualan inner join detailjual "+
                        " on penjualan.nota_jual=detailjual.nota_jual "+
                        " where detailjual.kode_brng='"+kodebarang+"' and kd_bangsal='"+lokasi+"' and penjualan.tgl_jual=?",tanggal);
    }
    
}

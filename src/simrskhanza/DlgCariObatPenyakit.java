/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package simrskhanza;

import laporan.DlgCariPenyakit;
import inventory.DlgObatPenyakit;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.riwayatobat;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgCariObatPenyakit extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private riwayatobat Trackobat=new riwayatobat();
    private WarnaTable2 warna=new WarnaTable2();
    private String bangsal="",awal="0";
    private PreparedStatement ps;
    private ResultSet rs;
    private double jumlah,x,i,kenaikan=0;
    private int z=0,row;
    private String aktifkanbatch="no";
    private boolean sukses=true;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariObatPenyakit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        tabMode=new DefaultTableModel(null,new Object[]{
            "Kode Obat","Nama Obat","Jenis Obat","Harga Obat","Jml","Embalase","Tuslah","Total Biaya",
            "Kode Penyakit","Nama Penyakit","Ciri-ciri Penyakit","Keterangan","Kategori Penyakit",
            "Ciri-ciri Umum", "Referensi","H.Beli","Stok","No.Batch","No.Faktur"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if ((colIndex==4)||(colIndex==5)||(colIndex==6)||(colIndex==17)||(colIndex==18)) {
                        a=true;
                    }
                    return a;
              }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (z = 0; z < 19; z++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(z);
            if(z==0){
                column.setPreferredWidth(100);
            }else if(z==1){
                column.setPreferredWidth(170);
            }else if(z==2){
                column.setPreferredWidth(90);
            }else if(z==3){
                column.setPreferredWidth(80);
            }else if(z==4){
                column.setPreferredWidth(40);
            }else if(z==5){
                column.setPreferredWidth(60);
            }else if(z==6){
                column.setPreferredWidth(60);
            }else if(z==7){
                column.setPreferredWidth(90);
            }else if(z==8){
                column.setPreferredWidth(90);
            }else if(z==9){
                column.setPreferredWidth(170);
            }else if(z==10){
                column.setPreferredWidth(150);
            }else if(z==11){
                column.setPreferredWidth(150);
            }else if(z==12){
                column.setPreferredWidth(150);
            }else if(z==13){
                column.setPreferredWidth(150);
            }else if(z==14){
                column.setPreferredWidth(150);
            }else if(z==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(z==16){
                column.setPreferredWidth(40);
            }else if(z==17){
                column.setPreferredWidth(70);
            }else if(z==18){
                column.setPreferredWidth(100);
            }
        }
        warna.kolom=4;
        tbKamar.setDefaultRenderer(Object.class,warna);
        TNoRw.setDocument(new batasInput((byte)20).getKata(TNoRw));        
        
        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penyakit.getTable().getSelectedRow()!= -1){                   
                    PenyakitCari.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                }    
                PenyakitCari.requestFocus();
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
        
        penyakit.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penyakit.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
         
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
        }

        jam();
    }
    public DlgCariPenyakit penyakit=new DlgCariPenyakit(null,false);
    
    private String[] hlm;


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
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelisi3 = new widget.panelisi();
        jLabel11 = new widget.Label();
        PenyakitCari = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSimpan = new widget.Button();
        jLabel12 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel7 = new widget.Label();
        DTPBeri = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Obat Penyakit/Alkes Dibutuhkan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbKamarPropertyChange(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel11.setText("Penyakit :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(63, 23));
        panelisi3.add(jLabel11);

        PenyakitCari.setName("PenyakitCari"); // NOI18N
        PenyakitCari.setPreferredSize(new java.awt.Dimension(270, 23));
        PenyakitCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitCariKeyPressed(evt);
            }
        });
        panelisi3.add(PenyakitCari);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('4');
        BtnSeek3.setToolTipText("Alt+4");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        panelisi3.add(BtnSeek3);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
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
        panelisi3.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        jLabel12.setText("Halaman :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(jLabel12);

        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi3.add(cmbHlm);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 44));
        FormInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel3.setText("Pasien :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(jLabel3);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setPreferredSize(new java.awt.Dimension(120, 23));
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(230, 23));
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);

        jLabel7.setText("Tgl.Beri :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(jLabel7);

        DTPBeri.setEditable(false);
        DTPBeri.setForeground(new java.awt.Color(50, 70, 50));
        DTPBeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2019-12-19" }));
        DTPBeri.setDisplayFormat("yyyy-MM-dd");
        DTPBeri.setName("DTPBeri"); // NOI18N
        DTPBeri.setOpaque(false);
        DTPBeri.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPBeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPBeriKeyPressed(evt);
            }
        });
        FormInput.add(DTPBeri);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(62, 23));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(62, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(62, 23));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(23, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

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
           Valid.pindah(evt, PenyakitCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        PenyakitCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, PenyakitCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgObatPenyakit obatpenyakit=new DlgObatPenyakit(null,false);
        obatpenyakit.emptTeks();
        obatpenyakit.isCek();
        obatpenyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        obatpenyakit.setLocationRelativeTo(internalFrame1);
        obatpenyakit.setAlwaysOnTop(false);
        obatpenyakit.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());           
    }//GEN-LAST:event_BtnTambahActionPerformed

private void PenyakitCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitCariKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
        }
}//GEN-LAST:event_PenyakitCariKeyPressed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        penyakit.emptTeks();
        penyakit.isCek();
        penyakit.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setAlwaysOnTop(false);
        penyakit.setVisible(true);
}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek3KeyPressed

private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        Valid.pindah(evt,PenyakitCari,DTPBeri);
}//GEN-LAST:event_TNoRwKeyPressed

private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_TPasienKeyPressed

private void DTPBeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPBeriKeyPressed
        Valid.pindah(evt,TNoRw,cmbJam);
}//GEN-LAST:event_DTPBeriKeyPressed

private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPBeri,cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,PenyakitCari);
}//GEN-LAST:event_cmbDtkKeyPressed

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
       if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
            PenyakitCari.setText("");
            PenyakitCari.requestFocus();
        }
}//GEN-LAST:event_tbKamarKeyPressed

private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
       if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(bangsal.equals("")){
            Valid.textKosong(PenyakitCari,"Lokasi");
        }else{
            try {
                Sequel.AutoComitFalse();
                sukses=true;
                   
                row=tabMode.getRowCount();
                for(int r=0;r<row;r++){ 
                    if(Valid.SetAngka(tabMode.getValueAt(r,4).toString())>0){
                        if(Sequel.menyimpantf("detail_pemberian_obat","'"+DTPBeri.getSelectedItem()+"','"+
                                cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"','"+
                                TNoRw.getText()+"','"+
                                tabMode.getValueAt(r,0).toString()+"','"+
                                Valid.SetAngka(tabMode.getValueAt(r,15).toString())+"','"+
                                Valid.SetAngka(tabMode.getValueAt(r,3).toString())+"','"+
                                Valid.SetAngka(tabMode.getValueAt(r,4).toString())+"','"+
                                Valid.SetAngka(tabMode.getValueAt(r,5).toString())+"','"+
                                Valid.SetAngka(tabMode.getValueAt(r,6).toString())+"','"+
                                Valid.SetAngka(tabMode.getValueAt(r,7).toString())+"','Ralan','"+bangsal+"','"+
                                tabMode.getValueAt(r,17).toString()+"','"+
                                tabMode.getValueAt(r,18).toString()+"'","data")==true){
                            Trackobat.catatRiwayat(tabMode.getValueAt(r,0).toString(),0,Valid.SetAngka(tabMode.getValueAt(r,4).toString()),"Pemberian Obat",akses.getkode(),bangsal,"Simpan",tabMode.getValueAt(r,17).toString(),tabMode.getValueAt(r,18).toString());
                            Sequel.menyimpan("gudangbarang","'"+tabMode.getValueAt(r,0).toString()+"','"+bangsal+"','-"+tabMode.getValueAt(r,4).toString()+"','"+tabMode.getValueAt(r,17).toString()+"','"+tabMode.getValueAt(r,18).toString()+"'", 
                                            "stok=stok-'"+tabMode.getValueAt(r,4).toString()+"'","kode_brng='"+tabMode.getValueAt(r,0).toString()+"' and kd_bangsal='"+bangsal+"' and no_batch='"+tabMode.getValueAt(r,17).toString()+"' and no_faktur='"+tabMode.getValueAt(r,18).toString()+"'");
                            if(aktifkanbatch.equals("yes")){
                                Sequel.mengedit("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                    ""+(tabMode.getValueAt(r,4).toString()),tabMode.getValueAt(r,17).toString(),tabMode.getValueAt(r,0).toString(),tabMode.getValueAt(r,18).toString()
                                });
                            }
                        }else{
                            sukses=false;
                        }                        
                    }                           
                } 
                
                if(sukses==true){
                    Sequel.Commit();
                }else{
                    sukses=false;
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
                ChkJln.setSelected(true);
                
                if(sukses==true){
                    JOptionPane.showMessageDialog(null,"Proses menyimpan berhasil...!");
                    dispose();
                }
                
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbKamarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbKamarPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbKamarPropertyChange

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariObatPenyakit dialog = new DlgCariObatPenyakit(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSeek3;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPBeri;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private widget.TextBox PenyakitCari;
    private widget.ScrollPane Scroll;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbHlm;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private widget.Label jLabel7;
    private widget.Label label10;
    private widget.panelisi panelisi3;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(aktifkanbatch.equals("yes")){
                if(kenaikan>0){
                    ps=koneksi.prepareStatement(
                           "select obat_penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                           "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum,obat_penyakit.kode_brng,databarang.nama_brng,"+
                           "jenis.nama,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,obat_penyakit.referensi,databarang.dasar,gudangbarang.stok,gudangbarang.no_batch,gudangbarang.no_faktur "+
                           "from obat_penyakit inner join penyakit on obat_penyakit.kd_penyakit=penyakit.kd_penyakit "+
                           "inner join kategori_penyakit on penyakit.kd_ktg=kategori_penyakit.kd_ktg "+
                           "inner join databarang on obat_penyakit.kode_brng=databarang.kode_brng "+
                           "inner join data_batch on data_batch.kode_brng=databarang.kode_brng "+
                           "inner join jenis on databarang.kdjns=jenis.kdjns "+
                           "inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                           "where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and penyakit.nm_penyakit like ? order by databarang.nama_brng LIMIT "+awal+",500");
                }else{
                    ps=koneksi.prepareStatement(
                           "select obat_penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                           "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum,obat_penyakit.kode_brng,databarang.nama_brng,"+
                           "jenis.nama,databarang.ralan,obat_penyakit.referensi,databarang.dasar,gudangbarang.stok,gudangbarang.no_batch,gudangbarang.no_faktur "+
                           "from obat_penyakit inner join penyakit on obat_penyakit.kd_penyakit=penyakit.kd_penyakit "+
                           "inner join kategori_penyakit on penyakit.kd_ktg=kategori_penyakit.kd_ktg "+
                           "inner join databarang on obat_penyakit.kode_brng=databarang.kode_brng "+
                           "inner join data_batch on data_batch.kode_brng=databarang.kode_brng "+
                           "inner join jenis on databarang.kdjns=jenis.kdjns "+
                           "inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                           "where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and penyakit.nm_penyakit like ? order by databarang.nama_brng LIMIT "+awal+",500");
                }
                    
                try {
                    awal="0";
                    if(cmbHlm.getItemCount()>0){
                        awal=hlm[Integer.parseInt(cmbHlm.getSelectedItem().toString())];
                    }

                    if(kenaikan>0){
                        ps.setDouble(1,kenaikan);
                        ps.setString(2,bangsal);
                        ps.setString(3,"%"+PenyakitCari.getText()+"%");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabMode.addRow(new String[]{
                                rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("harga"),"","0","0","0",
                                rs.getString("kd_penyakit"),rs.getString("nm_penyakit"),rs.getString("ciri_ciri"),rs.getString("keterangan"),rs.getString("nm_kategori"),
                                rs.getString("ciri_umum"),rs.getString("referensi"),rs.getString("dasar"),rs.getString("stok"),rs.getString("no_batch"),rs.getString("no_faktur")
                            });
                        }
                    }else{
                        ps.setString(1,bangsal);
                        ps.setString(2,"%"+PenyakitCari.getText()+"%");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabMode.addRow(new String[]{
                                rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("ralan"),"","0","0","0",
                                rs.getString("kd_penyakit"),rs.getString("nm_penyakit"),rs.getString("ciri_ciri"),rs.getString("keterangan"),rs.getString("nm_kategori"),
                                rs.getString("ciri_umum"),rs.getString("referensi"),rs.getString("dasar"),rs.getString("stok"),rs.getString("no_batch"),rs.getString("no_faktur")
                            });
                        }
                    }
                        

                    cmbHlm.removeAllItems();
                    rs.last();
                    jumlah=rs.getRow();
                    x=jumlah/499;
                    i=Math.ceil(x);
                    z=(int) i;

                    hlm=new String[z+1];
                    for(int j=1;j<=i;j++){
                         int mulai=((j-1)*499+j)-1;
                         hlm[j]=Integer.toString(mulai);
                         cmbHlm.addItem(j);
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
            }else{
                if(kenaikan>0){
                    ps=koneksi.prepareStatement(
                           "select obat_penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                           "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum,obat_penyakit.kode_brng,databarang.nama_brng,"+
                           "jenis.nama,(databarang.h_beli+(databarang.h_beli*?)) as harga,obat_penyakit.referensi,databarang.dasar,gudangbarang.stok "+
                           "from obat_penyakit inner join penyakit on obat_penyakit.kd_penyakit=penyakit.kd_penyakit "+
                           "inner join kategori_penyakit on penyakit.kd_ktg=kategori_penyakit.kd_ktg "+
                           "inner join databarang on obat_penyakit.kode_brng=databarang.kode_brng "+
                           "inner join jenis on databarang.kdjns=jenis.kdjns "+
                           "inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                           "where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and penyakit.nm_penyakit like ? order by databarang.nama_brng LIMIT "+awal+",500");
                }else{
                    ps=koneksi.prepareStatement(
                           "select obat_penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                           "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum,obat_penyakit.kode_brng,databarang.nama_brng,"+
                           "jenis.nama,databarang.ralan,obat_penyakit.referensi,databarang.dasar,gudangbarang.stok "+
                           "from obat_penyakit inner join penyakit on obat_penyakit.kd_penyakit=penyakit.kd_penyakit "+
                           "inner join kategori_penyakit on penyakit.kd_ktg=kategori_penyakit.kd_ktg "+
                           "inner join databarang on obat_penyakit.kode_brng=databarang.kode_brng "+
                           "inner join jenis on databarang.kdjns=jenis.kdjns "+
                           "inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                           "where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and penyakit.nm_penyakit like ? order by databarang.nama_brng LIMIT "+awal+",500");
                }
                    
                try {
                    awal="0";
                    if(cmbHlm.getItemCount()>0){
                        awal=hlm[Integer.parseInt(cmbHlm.getSelectedItem().toString())];
                    }

                    if(kenaikan>0){
                        ps.setDouble(1,kenaikan);
                        ps.setString(2,bangsal);
                        ps.setString(3,"%"+PenyakitCari.getText()+"%");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabMode.addRow(new String[]{
                                rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("harga"),"","0","0","0",
                                rs.getString("kd_penyakit"),rs.getString("nm_penyakit"),rs.getString("ciri_ciri"),rs.getString("keterangan"),rs.getString("nm_kategori"),
                                rs.getString("ciri_umum"),rs.getString("referensi"),rs.getString("dasar"),rs.getString("stok"),"",""
                            });
                        }
                    }else{
                        ps.setString(1,bangsal);
                        ps.setString(2,"%"+PenyakitCari.getText()+"%");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabMode.addRow(new String[]{
                                rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("ralan"),"","0","0","0",
                                rs.getString("kd_penyakit"),rs.getString("nm_penyakit"),rs.getString("ciri_ciri"),rs.getString("keterangan"),rs.getString("nm_kategori"),
                                rs.getString("ciri_umum"),rs.getString("referensi"),rs.getString("dasar"),rs.getString("stok"),"",""
                            });
                        }
                    }
                        

                    cmbHlm.removeAllItems();
                    rs.last();
                    jumlah=rs.getRow();
                    x=jumlah/499;
                    i=Math.ceil(x);
                    z=(int) i;

                    hlm=new String[z+1];
                    for(int j=1;j<=i;j++){
                         int mulai=((j-1)*499+j)-1;
                         hlm[j]=Integer.toString(mulai);
                         cmbHlm.addItem(j);
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
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        Kd2.setText("");      
    }

    private void getData() {
        int row=tbKamar.getSelectedRow();
        if(bangsal.trim().equals("")){
             Valid.textKosong(PenyakitCari,"Asal Stok");
        }else if(row!= -1){
            if(!tabMode.getValueAt(row,4).toString().equals("")){
                try {
                    if(Double.parseDouble(tabMode.getValueAt(row,4).toString())>0){
                        jumlah=0;   
                        ps=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                        try {
                            ps.setString(1,bangsal);
                            ps.setString(2,tbKamar.getValueAt(row,0).toString());
                            ps.setString(3,tbKamar.getValueAt(row,17).toString());
                            ps.setString(4,tbKamar.getValueAt(row,18).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                jumlah=rs.getDouble(1);
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

                        tbKamar.setValueAt(jumlah,row,16);
                        
                        x=0;
                        try {
                            x=Double.parseDouble(tabMode.getValueAt(row,4).toString());
                        } catch (Exception e) {
                            x=0;
                        }
                        
                        if(jumlah<x){
                            JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                            PenyakitCari.requestFocus();
                            tabMode.setValueAt("", row,4);
                            tabMode.setValueAt(0, row,7);
                        } else{
                            tabMode.setValueAt(Double.toString(Valid.SetAngka(tabMode.getValueAt(row,3).toString())*Valid.SetAngka(tabMode.getValueAt(row,4).toString())+
                                    Valid.SetAngka(tabMode.getValueAt(row,5).toString())+Valid.SetAngka(tabMode.getValueAt(row,6).toString())), row,7);
                        }
                    }
                }catch (Exception e) {
                    tabMode.setValueAt("", row,4);
                    tabMode.setValueAt(0, row,7);
                }
            }
                            
        }
    }

    public JTextField getTextField(){
        return Kd2;
    }

    public JTable getTable(){
        return tbKamar;
    }
    
    public void isCek(){        
        BtnTambah.setEnabled(akses.getobat_penyakit());
    }
    
    public void setNoRm(String norwt,String penyakit) {
        PenyakitCari.setText(penyakit);
        tampil();
        TNoRw.setText(norwt);
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",Kd2,TNoRw.getText());
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,Kd2.getText());
        
        kenaikan=Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ralan where kd_pj='"+Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt)+"'");
        
        bangsal=Sequel.cariIsi("select kd_bangsal from set_depo_ralan where kd_poli=?",Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",TNoRw.getText()));
        if(bangsal.equals("")){
            bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
        }
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Calendar now = Calendar.getInstance();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.get(Calendar.HOUR);
                    nilai_menit = now.get(Calendar.MINUTE);
                    nilai_detik = now.get(Calendar.SECOND);
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    

}

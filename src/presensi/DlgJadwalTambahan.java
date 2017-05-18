/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJadwal.java
 *
 * Created on May 22, 2010, 10:25:16 PM
 */

package presensi;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
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
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public class DlgJadwalTambahan extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String pilihan="",dateString,dayOfWeek,hari,h1="",h2="",h3="",h4="",h5="",h6="",h7="",h8="",h9="",h10="",h11="",h12="",h13="",
                   h14="",h15="",h16="",h17="",h18="",h19="",h20="",h21="",h22="",h23="",h24="",h25="",h26="",h27="",h28="",h29="",h30="",h31="" ;
    private Date date = null;
    private DlgJamMasuk jammasuk=new DlgJamMasuk(null,false);
    private int i=0;

    /** Creates new form DlgJadwal
     * @param parent
     * @param modal */
    public DlgJadwalTambahan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        tbJadwal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJadwal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Valid.LoadTahun(ThnCari);
        Valid.loadCombo(Departemen,"nama","departemen");
        Departemen.addItem("Semua");
        Departemen.setSelectedItem("Semua");

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));  
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        }  
        
        jammasuk.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jammasuk.getTable().getSelectedRow()!= -1){    
                    if(tbJadwal.getSelectedColumn()>4){
                        tabMode.setValueAt(jammasuk.getTable().getValueAt(jammasuk.getTable().getSelectedRow(),1).toString(),tbJadwal.getSelectedRow(),tbJadwal.getSelectedColumn());
                    }                    
                }   
                tbJadwal.requestFocus();
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
        
        jammasuk.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jammasuk.dispose();
                }   
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            ps=koneksi.prepareStatement(
                    "select pegawai.id,pegawai.nik,pegawai.nama,pegawai.pendidikan,departemen.nama as departemen from pegawai inner join departemen "+
                    "on pegawai.departemen=departemen.dep_id where  pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and pegawai.nik like ? or "+
                    " pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and pegawai.nama like ? order by pegawai.nama");
            ps2=koneksi.prepareStatement("select jadwal_tambahan.h1,jadwal_tambahan.h2,jadwal_tambahan.h3,jadwal_tambahan.h4,jadwal_tambahan.h5,"+
                    "jadwal_tambahan.h6,jadwal_tambahan.h7,jadwal_tambahan.h8,jadwal_tambahan.h9,jadwal_tambahan.h10,"+
                    "jadwal_tambahan.h11,jadwal_tambahan.h12,jadwal_tambahan.h13,jadwal_tambahan.h14,jadwal_tambahan.h15,"+
                    "jadwal_tambahan.h16,jadwal_tambahan.h17,jadwal_tambahan.h18,jadwal_tambahan.h19,jadwal_tambahan.h20,"+
                    "jadwal_tambahan.h21,jadwal_tambahan.h22,jadwal_tambahan.h23,jadwal_tambahan.h24,jadwal_tambahan.h25,"+
                    "jadwal_tambahan.h26,jadwal_tambahan.h27,jadwal_tambahan.h28,jadwal_tambahan.h29,jadwal_tambahan.h30,"+
                    "jadwal_tambahan.h31 from jadwal_tambahan where jadwal_tambahan.id=? and jadwal_tambahan.tahun=? and jadwal_tambahan.bulan=?  ");
        } catch (Exception e) {
            System.out.println(e);
        }
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
        Scroll = new widget.ScrollPane();
        tbJadwal = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        LCount = new widget.Label();
        jLabel7 = new widget.Label();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelBiasa1 = new widget.PanelBiasa();
        label11 = new widget.Label();
        ThnCari = new widget.ComboBox();
        BlnCari = new widget.ComboBox();
        label12 = new widget.Label();
        Departemen = new widget.ComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengaturan Jadwal Tambahan Pegawai ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJadwal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJadwal.setName("tbJadwal"); // NOI18N
        tbJadwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJadwalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJadwal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(null);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Update");
        BtnSimpan.setToolTipText("Alt+U");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);
        BtnSimpan.setBounds(6, 10, 100, 30);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);
        BtnHapus.setBounds(108, 10, 100, 30);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
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
        BtnPrint.setBounds(414, 10, 100, 30);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
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
        BtnKeluar.setBounds(518, 10, 100, 30);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(LCount);
        LCount.setBounds(310, 15, 90, 23);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel7);
        jLabel7.setBounds(225, 15, 80, 23);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(1023, 47));
        panelBiasa1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 10));

        label11.setText("Tahun & Bulan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(90, 23));
        panelBiasa1.add(label11);

        ThnCari.setName("ThnCari"); // NOI18N
        ThnCari.setPreferredSize(new java.awt.Dimension(85, 23));
        ThnCari.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ThnCariItemStateChanged(evt);
            }
        });
        panelBiasa1.add(ThnCari);

        BlnCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        BlnCari.setName("BlnCari"); // NOI18N
        BlnCari.setPreferredSize(new java.awt.Dimension(55, 23));
        BlnCari.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BlnCariItemStateChanged(evt);
            }
        });
        panelBiasa1.add(BlnCari);

        label12.setText("Departemen :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(130, 23));
        panelBiasa1.add(label12);

        Departemen.setName("Departemen"); // NOI18N
        Departemen.setPreferredSize(new java.awt.Dimension(220, 23));
        Departemen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DepartemenItemStateChanged(evt);
            }
        });
        panelBiasa1.add(Departemen);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        Sequel.AutoComitFalse();
        for(i=0;i<tabMode.getRowCount();i++){ 
            Sequel.menyimpan2("jadwal_tambahan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 34,
                new String[]{
                    tabMode.getValueAt(i,1).toString(),
                    ThnCari.getSelectedItem().toString(),
                    BlnCari.getSelectedItem().toString(),
                    tabMode.getValueAt(i,5).toString(),
                    tabMode.getValueAt(i,6).toString(),
                    tabMode.getValueAt(i,7).toString(),
                    tabMode.getValueAt(i,8).toString(),
                    tabMode.getValueAt(i,9).toString(),tabMode.getValueAt(i,10).toString(),
                    tabMode.getValueAt(i,11).toString(),tabMode.getValueAt(i,12).toString(),tabMode.getValueAt(i,13).toString(),
                    tabMode.getValueAt(i,14).toString(),tabMode.getValueAt(i,15).toString(),tabMode.getValueAt(i,16).toString(),
                    tabMode.getValueAt(i,17).toString(),tabMode.getValueAt(i,18).toString(),tabMode.getValueAt(i,19).toString(),
                    tabMode.getValueAt(i,20).toString(),tabMode.getValueAt(i,21).toString(),tabMode.getValueAt(i,22).toString(),
                    tabMode.getValueAt(i,23).toString(),tabMode.getValueAt(i,24).toString(),tabMode.getValueAt(i,25).toString(),
                    tabMode.getValueAt(i,26).toString(),tabMode.getValueAt(i,27).toString(),tabMode.getValueAt(i,28).toString(),
                    tabMode.getValueAt(i,29).toString(),tabMode.getValueAt(i,30).toString(),tabMode.getValueAt(i,31).toString(),
                    tabMode.getValueAt(i,32).toString(),tabMode.getValueAt(i,33).toString(),tabMode.getValueAt(i,34).toString(),
                    tabMode.getValueAt(i,35).toString()
                },
                "id=? and tahun=? and bulan=?","h1=?,h2=?,h3=?,h4=?,h5=?,h6=?,h7=?,h8=?,h9=?,h10=?,h11=?,h12=?,h13=?,h14=?,"+
                "h15=?,h16=?,h17=?,h18=?,h19=?,h20=?,h21=?,h22=?,h23=?,h24=?,h25=?,h26=?,h27=?,h28=?,h29=?,h30=?,h31=?",34,
                new String[]{
                    tabMode.getValueAt(i,5).toString(),
                    tabMode.getValueAt(i,6).toString(),
                    tabMode.getValueAt(i,7).toString(),
                    tabMode.getValueAt(i,8).toString(),
                    tabMode.getValueAt(i,9).toString(),tabMode.getValueAt(i,10).toString(),
                    tabMode.getValueAt(i,11).toString(),tabMode.getValueAt(i,12).toString(),tabMode.getValueAt(i,13).toString(),
                    tabMode.getValueAt(i,14).toString(),tabMode.getValueAt(i,15).toString(),tabMode.getValueAt(i,16).toString(),
                    tabMode.getValueAt(i,17).toString(),tabMode.getValueAt(i,18).toString(),tabMode.getValueAt(i,19).toString(),
                    tabMode.getValueAt(i,20).toString(),tabMode.getValueAt(i,21).toString(),tabMode.getValueAt(i,22).toString(),
                    tabMode.getValueAt(i,23).toString(),tabMode.getValueAt(i,24).toString(),tabMode.getValueAt(i,25).toString(),
                    tabMode.getValueAt(i,26).toString(),tabMode.getValueAt(i,27).toString(),tabMode.getValueAt(i,28).toString(),
                    tabMode.getValueAt(i,29).toString(),tabMode.getValueAt(i,30).toString(),tabMode.getValueAt(i,31).toString(),
                    tabMode.getValueAt(i,32).toString(),tabMode.getValueAt(i,33).toString(),tabMode.getValueAt(i,34).toString(),
                    tabMode.getValueAt(i,35).toString(),tabMode.getValueAt(i,1).toString(),ThnCari.getSelectedItem().toString(),
                    BlnCari.getSelectedItem().toString()
                });            
        } 
        Sequel.AutoComitTrue();
        JOptionPane.showMessageDialog(null,"Proses selesai...!!!!");
        tampil();
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,BtnHapus);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()!=0){
            if(tbJadwal.getSelectedRow()!= -1){
                Sequel.queryu2("delete from jadwal_tambahan where id=? and tahun=? and bulan=?",3,new String[]{
                        tabMode.getValueAt(tbJadwal.getSelectedRow(),1).toString(),
                        ThnCari.getSelectedItem().toString(),BlnCari.getSelectedItem().toString()
                });
                Sequel.menyimpan("trackersql","now(),'delete from jadwal_tambahan where id="+tabMode.getValueAt(tbJadwal.getSelectedRow(),1).toString()+" "+
                        "and tahun="+ThnCari.getSelectedItem().toString()+" and bulan="+BlnCari.getSelectedItem().toString()+"','','jadwal pegawai','hapus'");
                tampil();
            }
        }            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
           Valid.pindah(evt, BtnSimpan, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
        }else if(tabMode.getRowCount()!=0){
            Sequel.AutoComitFalse();
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,5).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,6).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,7).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,8).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,9).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,10).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,11).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,12).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,13).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,14).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,15).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,16).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,17).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,18).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,19).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,20).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,21).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,22).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,23).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,24).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,25).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,26).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,27).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,28).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,29).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,30).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,31).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,32).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,33).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,34).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','"+
                                tabMode.getValueAt(r,35).toString().replaceAll("Midle ","").replaceAll("1","").replaceAll("2","").replaceAll("3","").replaceAll("4","").replaceAll("5","").replaceAll("6","").replaceAll("7","").replaceAll("8","").replaceAll("9","").replaceAll("0","").replaceAll("agi","").replaceAll("iang","").replaceAll("alam","").replaceAll(" ","")+"','','',''","Rekap Presensi"); 
            }
            Sequel.AutoComitTrue();
            Map<String, Object> param = new HashMap<>();   
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("departemen",Departemen.getSelectedItem().toString().replaceAll("Semua",""));
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
                try{
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih model cetak..!","Jadwal",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Tampilkan Semua", "Tanpa departemen & jabatan"},"Tampilkan Semua");
                    switch (pilihan) {
                        case "Tampilkan Semua":
                            Valid.MyReport("rptJadwalPegawai.jrxml","report","::[ Jadwal Masuk Tambahan Pegawai ]::","select * from temporary",param);            
                            break;
                        case "Tanpa departemen & jabatan":
                            Valid.MyReport("rptJadwalPegawai2.jrxml","report","::[ Jadwal Masuk Tambahan Pegawai ]::","select * from temporary",param);            
                            break;
                    }
                }catch(Exception e){
                    System.out.println(e);
                }                 
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbJadwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJadwalKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                jammasuk.isCek();
                jammasuk.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight());
                jammasuk.setLocationRelativeTo(internalFrame1);
                jammasuk.setVisible(true);
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                tabMode.setValueAt("",tbJadwal.getSelectedRow(),tbJadwal.getSelectedColumn());
            }
        }
    }//GEN-LAST:event_tbJadwalKeyPressed

    private void BlnCariItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BlnCariItemStateChanged
        if(this.isActive()==true){
            tampil();
        }
    }//GEN-LAST:event_BlnCariItemStateChanged

    private void ThnCariItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ThnCariItemStateChanged
        if(this.isActive()==true){
            System.out.println("coba");
            tampil();
        }
    }//GEN-LAST:event_ThnCariItemStateChanged

    private void DepartemenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DepartemenItemStateChanged
        if(this.isActive()==true){
            tampil();
        }
    }//GEN-LAST:event_DepartemenItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgJadwalTambahan dialog = new DlgJadwalTambahan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox Departemen;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.ComboBox ThnCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJadwal;
    // End of variables declaration//GEN-END:variables

    private void tampil() {        
        Object[] row={"No","ID","Nama","Pendidikan","Departemen",
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
            "31("+konversi(Integer.parseInt(ThnCari.getSelectedItem().toString()),Integer.parseInt(BlnCari.getSelectedItem().toString()),31)+")"
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
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbJadwal.setModel(tabMode);
        
        for (int i = 0; i < 36; i++) {
            TableColumn column = tbJadwal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(50);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbJadwal.setDefaultRenderer(Object.class, new WarnaTable());
        
        Valid.tabelKosong(tabMode);
        try{
            ps.setString(1,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(4,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            i=1;
            while(rs.next()){
                h1="";h2="";h3="";h4="";h5="";h6="";h7="";h8="";h9="";h10="";h11="";h12="";h13="";
                h14="";h15="";h16="";h17="";h18="";h19="";h20="";h21="";h22="";h23="";h24="";
                h25="";h26="";h27="";h28="";h29="";h30="";h31="";
                ps2.setString(1,rs.getString("id"));
                ps2.setString(2,ThnCari.getSelectedItem().toString());
                ps2.setString(3,BlnCari.getSelectedItem().toString());
                rs2=ps2.executeQuery();
                if(rs2.next()){
                    h1=rs2.getString("h1");
                    h2=rs2.getString("h2");
                    h3=rs2.getString("h3");
                    h4=rs2.getString("h4");
                    h5=rs2.getString("h5");
                    h6=rs2.getString("h6");
                    h7=rs2.getString("h7");
                    h8=rs2.getString("h8");
                    h9=rs2.getString("h9");
                    h10=rs2.getString("h10");
                    h11=rs2.getString("h11");
                    h12=rs2.getString("h12");
                    h13=rs2.getString("h13");
                    h14=rs2.getString("h14");
                    h15=rs2.getString("h15");
                    h16=rs2.getString("h16");
                    h17=rs2.getString("h17");
                    h18=rs2.getString("h18");
                    h19=rs2.getString("h19");
                    h20=rs2.getString("h20");
                    h21=rs2.getString("h21");
                    h22=rs2.getString("h22");
                    h23=rs2.getString("h23");
                    h24=rs2.getString("h24");
                    h25=rs2.getString("h25");
                    h26=rs2.getString("h26");
                    h27=rs2.getString("h27");
                    h28=rs2.getString("h28");
                    h29=rs2.getString("h29");
                    h30=rs2.getString("h30");
                    h31=rs2.getString("h31"); 
                }
                tabMode.addRow(new Object[]{
                    " "+i+".",rs.getString("id"),rs.getString("nama"),rs.getString("pendidikan"),rs.getString("departemen"),
                    h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,h20,h21,h22,h23,h24,h25,h26,h27,h28,h29,h30,h31 
                });
                i++;
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getjadwal_pegawai());
        BtnHapus.setEnabled(var.getjadwal_pegawai());
    }
    
    String konversi(int year, int month, int day){
        dateString = String.format("%d-%d-%d", year, month, day);        
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (Exception ex) {
            Logger.getLogger(DlgJadwalTambahan.class.getName()).log(Level.SEVERE, null, ex);
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
    
}



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
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;

/**
 *
 * @author perpustakaan
 */
public final class KeuanganValidasiPersetujuanPengajuanBiaya extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private int i;
    private double belumdivalidasi=0;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganValidasiPersetujuanPengajuanBiaya(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Pengajuan","Tanggal","NIP","Diajukan Oleh","Bidang","Departemen","Urgensi","Uraian","Tujuan",
                "Target Sasaran","Lokasi","Jml","Harga", "Total", "Keterangan", "NIP P.J.","P.J. Terkait"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                  java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                  java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                  java.lang.String.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.String.class,
                  java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBangsal.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(85);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(35);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(140);
            }else if(i==15){
                column.setPreferredWidth(85);
            }else if(i==16){
                column.setPreferredWidth(160);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){
                    kdpegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    nmpegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    tampil();
                }      
                kdpegawai.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {pegawai.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        pegawai.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pegawai.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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

        TKd = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label19 = new widget.Label();
        kdpegawai = new widget.TextBox();
        nmpegawai = new widget.TextBox();
        BtnPegawai = new widget.Button();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        LCount1 = new javax.swing.JLabel();
        BtnBayar = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Validasi Persetujuan Pengajuan Biaya/Anggaran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 101));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label19.setText("P.J.Terkait :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label19);

        kdpegawai.setEditable(false);
        kdpegawai.setName("kdpegawai"); // NOI18N
        kdpegawai.setPreferredSize(new java.awt.Dimension(80, 23));
        kdpegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpegawaiKeyPressed(evt);
            }
        });
        panelisi3.add(kdpegawai);

        nmpegawai.setEditable(false);
        nmpegawai.setName("nmpegawai"); // NOI18N
        nmpegawai.setPreferredSize(new java.awt.Dimension(190, 23));
        panelisi3.add(nmpegawai);

        BtnPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPegawai.setMnemonic('3');
        BtnPegawai.setToolTipText("Alt+3");
        BtnPegawai.setName("BtnPegawai"); // NOI18N
        BtnPegawai.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPegawaiActionPerformed(evt);
            }
        });
        BtnPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPegawaiKeyPressed(evt);
            }
        });
        panelisi3.add(BtnPegawai);

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi3.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

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
        panelisi3.add(BtnCari);

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
        panelisi3.add(BtnAll);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Belum Divalidasi :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(140, 23));
        panelisi1.add(LCount);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(50, 50, 50));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Dipilih :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(jLabel12);

        LCount1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount1.setForeground(new java.awt.Color(50, 50, 50));
        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(130, 23));
        panelisi1.add(LCount1);

        BtnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnBayar.setMnemonic('s');
        BtnBayar.setText("Validasi");
        BtnBayar.setToolTipText("Alt+S");
        BtnBayar.setName("BtnBayar"); // NOI18N
        BtnBayar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBayarActionPerformed(evt);
            }
        });
        BtnBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBayarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBayar);

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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptValidasiPersetujuanPengajuanBiaya.jasper","report","::[ Data Pengajuan Biaya Yang Disetujui ]::",
                   "select pengajuan_biaya.no_pengajuan,pengajuan_biaya.tanggal,pengajuan_biaya.nik,peg1.nama as namapengaju,"+
                   "peg1.bidang,peg1.departemen,pengajuan_biaya.urgensi,pengajuan_biaya.uraian_latar_belakang,pengajuan_biaya.tujuan_pengajuan,"+
                   "pengajuan_biaya.target_sasaran,pengajuan_biaya.lokasi_kegiatan,pengajuan_biaya_disetujui.jumlah,pengajuan_biaya_disetujui.harga,"+
                   "pengajuan_biaya_disetujui.total,pengajuan_biaya.keterangan,pengajuan_biaya.nik_pj,peg2.nama as namapj,pengajuan_biaya.status "+
                   "from pengajuan_biaya inner join pegawai as peg1 on pengajuan_biaya.nik=peg1.nik "+
                   "inner join pegawai as peg2 on pengajuan_biaya.nik_pj=peg2.nik "+
                   "inner join pengajuan_biaya_disetujui on pengajuan_biaya_disetujui.no_pengajuan=pengajuan_biaya.no_pengajuan "+
                   "where pengajuan_biaya.status='Disetujui' and pengajuan_biaya.nik_pj like '%"+kdpegawai.getText().trim()+"%' "+
                   (TCari.getText().trim().equals("")?"":"and (pengajuan_biaya.no_pengajuan like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.nik like '%"+TCari.getText().trim()+"%' or peg1.nama like '%"+TCari.getText().trim()+"%' or "+
                   "peg1.bidang like '%"+TCari.getText().trim()+"%' or peg1.departemen like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.urgensi like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.uraian_latar_belakang like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_biaya.tujuan_pengajuan like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.lokasi_kegiatan like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.keterangan like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_biaya.nik_pj like '%"+TCari.getText().trim()+"%' or peg2.nama like '%"+TCari.getText().trim()+"%')")+" order by pengajuan_biaya.tanggal",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBayar, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        pegawai.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdpegawai.setText("");
        nmpegawai.setText("");
        tampil();

}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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
            tampil();
        }else{
            Valid.pindah(evt, TKd, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBayarActionPerformed
        if(tbBangsal.getSelectedRow()> -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPengeluaranHarian form=new DlgPengeluaranHarian(null,false);
            form.isCek();
            form.emptTeks();
            form.setPengajuan(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString(),"Validasi pengajuan No."+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString()+" tanggal "+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString()+" oleh "+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),3).toString()+" NIP "+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),2).toString(),Valid.SetAngka2(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString())));
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            tabMode.removeRow(tbBangsal.getSelectedRow());
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pengajuan yang diajukan..!!");
        }
    }//GEN-LAST:event_BtnBayarActionPerformed

    private void BtnBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBayarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBayarActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnPrint);
        }
    }//GEN-LAST:event_BtnBayarKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tbBangsal.getSelectedRow()!= -1){  
            LCount1.setText(Valid.SetAngka(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString()))+"");
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    private void BtnPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPegawaiActionPerformed
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPegawaiActionPerformed

    private void BtnPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPegawaiKeyPressed
        Valid.pindah(evt,BtnKeluar,TCari);
    }//GEN-LAST:event_BtnPegawaiKeyPressed

    private void kdpegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpegawaiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPegawaiActionPerformed(null);
        }
    }//GEN-LAST:event_kdpegawaiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganValidasiPersetujuanPengajuanBiaya dialog = new KeuanganValidasiPersetujuanPengajuanBiaya(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBayar;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPegawai;
    private widget.Button BtnPrint;
    private javax.swing.JLabel LCount;
    private javax.swing.JLabel LCount1;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdpegawai;
    private widget.Label label17;
    private widget.Label label19;
    private widget.TextBox nmpegawai;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    private void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                   "select pengajuan_biaya.no_pengajuan,pengajuan_biaya.tanggal,pengajuan_biaya.nik,peg1.nama as namapengaju,"+
                   "peg1.bidang,peg1.departemen,pengajuan_biaya.urgensi,pengajuan_biaya.uraian_latar_belakang,pengajuan_biaya.tujuan_pengajuan,"+
                   "pengajuan_biaya.target_sasaran,pengajuan_biaya.lokasi_kegiatan,pengajuan_biaya_disetujui.jumlah,pengajuan_biaya_disetujui.harga,"+
                   "pengajuan_biaya_disetujui.total,pengajuan_biaya.keterangan,pengajuan_biaya.nik_pj,peg2.nama as namapj,pengajuan_biaya.status "+
                   "from pengajuan_biaya inner join pegawai as peg1 on pengajuan_biaya.nik=peg1.nik "+
                   "inner join pegawai as peg2 on pengajuan_biaya.nik_pj=peg2.nik "+
                   "inner join pengajuan_biaya_disetujui on pengajuan_biaya_disetujui.no_pengajuan=pengajuan_biaya.no_pengajuan "+
                   "where pengajuan_biaya.status='Disetujui' and pengajuan_biaya.nik_pj like ? "+
                   (TCari.getText().trim().equals("")?"":"and (pengajuan_biaya.no_pengajuan like ? or pengajuan_biaya.nik like ? or peg1.nama like ? or "+
                   "peg1.bidang like ? or peg1.departemen like ? or pengajuan_biaya.urgensi like ? or pengajuan_biaya.uraian_latar_belakang like ? or "+
                   "pengajuan_biaya.tujuan_pengajuan like ? or pengajuan_biaya.lokasi_kegiatan like ? or pengajuan_biaya.keterangan like ? or "+
                   "pengajuan_biaya.nik_pj like ? or peg2.nama like ?)")+" order by pengajuan_biaya.tanggal");
            try {
                ps.setString(1,"%"+kdpegawai.getText().trim()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+TCari.getText().trim()+"%");
                    ps.setString(14,"%"+TCari.getText().trim()+"%");
                }   
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_pengajuan"),rs.getString("tanggal"),rs.getString("nik"),rs.getString("namapengaju"),rs.getString("bidang"),
                        rs.getString("departemen"),rs.getString("urgensi"),rs.getString("uraian_latar_belakang"),rs.getString("tujuan_pengajuan"),rs.getString("target_sasaran"),
                        rs.getString("lokasi_kegiatan"),rs.getDouble("jumlah"),rs.getDouble("harga"),rs.getDouble("total"),rs.getString("keterangan"),
                        rs.getString("nik_pj"),rs.getString("namapj")
                    });
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
            hitung();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void hitung(){
        belumdivalidasi=0;
        for(i=0;i<tabMode.getRowCount();i++){
            belumdivalidasi=belumdivalidasi+Valid.SetAngka(tbBangsal.getValueAt(i,13).toString());
        }
        LCount.setText(""+Valid.SetAngka(belumdivalidasi));
    }
    
    public void isCek(){
        BtnBayar.setEnabled(akses.getpengeluaran());
    }
}

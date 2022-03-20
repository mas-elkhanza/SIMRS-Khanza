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
import simrskhanza.DlgCariBangsal;

public class DlgRiwayatBarangMedis extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();    
    private DlgBarang barang=new DlgBarang(null,false);
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);

    /** 
     * @param parent
     * @param modal */
    public DlgRiwayatBarangMedis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
            "Barang","Awal","Masuk","Keluar","Akhir","Posisi",
            "Tanggal","Jam","Petugas","Lokasi","Status","No.Batch","No.Faktur","Keterangan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 14; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(230);
            }else if(i==1){
                column.setPreferredWidth(55);
            }else if(i==2){
                column.setPreferredWidth(55);
            }else if(i==3){
                column.setPreferredWidth(55);
            }else if(i==4){
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(55);
            }else if(i==8){
                column.setPreferredWidth(110);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(60);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(300);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());         
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
            });
        }   
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRiwayatBarangMedis")){
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                        nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                    }  
                    kdbar.requestFocus();
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
        
        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgRiwayatBarangMedis")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();                    
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRiwayatBarangMedis")){
                    if(bangsal.getTable().getSelectedRow()!= -1){                   
                        KdGudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        NmGudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                    }  
                    KdGudang.requestFocus();
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
        
    }    
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
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label19 = new widget.Label();
        KdGudang = new widget.TextBox();
        NmGudang = new widget.TextBox();
        btnBarang1 = new widget.Button();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Riwayat Obat, Alkes & BHP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
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

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label17);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang.setMnemonic('1');
        btnBarang.setToolTipText("Alt+1");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        panelisi4.add(btnBarang);

        label19.setText("Lokasi :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi4.add(label19);

        KdGudang.setName("KdGudang"); // NOI18N
        KdGudang.setPreferredSize(new java.awt.Dimension(80, 23));
        KdGudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdGudangKeyPressed(evt);
            }
        });
        panelisi4.add(KdGudang);

        NmGudang.setEditable(false);
        NmGudang.setName("NmGudang"); // NOI18N
        NmGudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NmGudang);

        btnBarang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang1.setMnemonic('1');
        btnBarang1.setToolTipText("Alt+1");
        btnBarang1.setName("btnBarang1"); // NOI18N
        btnBarang1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarang1ActionPerformed(evt);
            }
        });
        panelisi4.add(btnBarang1);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(53, 23));
        panelisi1.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi1.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelisi1.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi1.add(Tgl2);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setToolTipText("Alt+A");
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

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(19, 30));
        panelisi1.add(label9);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+P");
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            if(nmbar.getText().trim().equals("")&&NmGudang.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptRiwayatBarangMedis.jasper","report","::[ Riwayat Obat, Alkes & BHP ]::",
                    "select riwayat_barang_medis.kode_brng,databarang.nama_brng,"+
                    "riwayat_barang_medis.stok_awal,riwayat_barang_medis.masuk,"+
                    "riwayat_barang_medis.keluar,riwayat_barang_medis.stok_akhir,"+
                    "riwayat_barang_medis.posisi,riwayat_barang_medis.tanggal,"+
                    "riwayat_barang_medis.jam,riwayat_barang_medis.petugas,"+
                    "riwayat_barang_medis.kd_bangsal,bangsal.nm_bangsal,"+
                    "riwayat_barang_medis.status,riwayat_barang_medis.no_batch,"+
                    "riwayat_barang_medis.no_faktur,riwayat_barang_medis.keterangan from riwayat_barang_medis "+
                    "inner join bangsal inner join databarang on "+
                    "riwayat_barang_medis.kode_brng=databarang.kode_brng and "+
                    "riwayat_barang_medis.kd_bangsal=bangsal.kd_bangsal where "+
                    "riwayat_barang_medis.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by riwayat_barang_medis.tanggal,riwayat_barang_medis.jam ",param);
            }else{
                Valid.MyReportqry("rptRiwayatBarangMedis.jasper","report","::[ Riwayat Obat, Alkes & BHP ]::",
                    "select riwayat_barang_medis.kode_brng,databarang.nama_brng,"+
                        "riwayat_barang_medis.stok_awal,riwayat_barang_medis.masuk,"+
                        "riwayat_barang_medis.keluar,riwayat_barang_medis.stok_akhir,"+
                        "riwayat_barang_medis.posisi,riwayat_barang_medis.tanggal,"+
                        "riwayat_barang_medis.jam,riwayat_barang_medis.petugas,"+
                        "riwayat_barang_medis.kd_bangsal,bangsal.nm_bangsal,"+
                        "riwayat_barang_medis.status,riwayat_barang_medis.no_batch,"+
                        "riwayat_barang_medis.no_faktur,riwayat_barang_medis.keterangan from riwayat_barang_medis "+
                        "inner join bangsal inner join databarang on "+
                        "riwayat_barang_medis.kode_brng=databarang.kode_brng and "+
                        "riwayat_barang_medis.kd_bangsal=bangsal.kd_bangsal where "+
                        "riwayat_barang_medis.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and databarang.nama_brng like '%"+nmbar.getText()+"%' and bangsal.nm_bangsal like '%"+NmGudang.getText()+"%' and "+
                        "(riwayat_barang_medis.kode_brng like '%"+TCari.getText().trim()+"%' or databarang.nama_brng like '%"+TCari.getText().trim()+"%' or riwayat_barang_medis.petugas like '%"+TCari.getText().trim()+"%' or riwayat_barang_medis.no_batch like '%"+TCari.getText().trim()+"%' or "+
                        "riwayat_barang_medis.no_faktur like '%"+TCari.getText().trim()+"%' or bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' or riwayat_barang_medis.kd_bangsal like '%"+TCari.getText().trim()+"%' or riwayat_barang_medis.status like '%"+TCari.getText().trim()+"%' or "+
                        "riwayat_barang_medis.keterangan like '%"+TCari.getText().trim()+"%' or riwayat_barang_medis.posisi like '%"+TCari.getText().trim()+"%') order by riwayat_barang_medis.tanggal,riwayat_barang_medis.jam ",param);
            }
                
            this.setCursor(Cursor.getDefaultCursor());
        }
        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("DlgRiwayatBarangMedis");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdbar.setText("");
        nmbar.setText("");
        KdGudang.setText("");
        NmGudang.setText("");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void btnBarang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarang1ActionPerformed
        akses.setform("DlgRiwayatBarangMedis");
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnBarang1ActionPerformed

    private void KdGudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdGudangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdGudangKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRiwayatBarangMedis dialog = new DlgRiwayatBarangMedis(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private widget.TextBox KdGudang;
    private widget.TextBox NmGudang;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBarang;
    private widget.Button btnBarang1;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdbar;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{   
            if(nmbar.getText().trim().equals("")&&NmGudang.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select riwayat_barang_medis.kode_brng,databarang.nama_brng,"+
                    "riwayat_barang_medis.stok_awal,riwayat_barang_medis.masuk,"+
                    "riwayat_barang_medis.keluar,riwayat_barang_medis.stok_akhir,"+
                    "riwayat_barang_medis.posisi,riwayat_barang_medis.tanggal,"+
                    "riwayat_barang_medis.jam,riwayat_barang_medis.petugas,"+
                    "riwayat_barang_medis.kd_bangsal,bangsal.nm_bangsal,"+
                    "riwayat_barang_medis.status,riwayat_barang_medis.no_batch,"+
                    "riwayat_barang_medis.no_faktur,riwayat_barang_medis.keterangan from riwayat_barang_medis "+
                    "inner join bangsal inner join databarang on "+
                    "riwayat_barang_medis.kode_brng=databarang.kode_brng and "+
                    "riwayat_barang_medis.kd_bangsal=bangsal.kd_bangsal where "+
                    "riwayat_barang_medis.tanggal between ? and ? order by riwayat_barang_medis.tanggal,riwayat_barang_medis.jam ");
            }else{
                ps=koneksi.prepareStatement(
                    "select riwayat_barang_medis.kode_brng,databarang.nama_brng,"+
                    "riwayat_barang_medis.stok_awal,riwayat_barang_medis.masuk,"+
                    "riwayat_barang_medis.keluar,riwayat_barang_medis.stok_akhir,"+
                    "riwayat_barang_medis.posisi,riwayat_barang_medis.tanggal,"+
                    "riwayat_barang_medis.jam,riwayat_barang_medis.petugas,"+
                    "riwayat_barang_medis.kd_bangsal,bangsal.nm_bangsal,"+
                    "riwayat_barang_medis.status,riwayat_barang_medis.no_batch,"+
                    "riwayat_barang_medis.no_faktur,riwayat_barang_medis.keterangan from riwayat_barang_medis "+
                    "inner join bangsal inner join databarang on "+
                    "riwayat_barang_medis.kode_brng=databarang.kode_brng and "+
                    "riwayat_barang_medis.kd_bangsal=bangsal.kd_bangsal where "+
                    "riwayat_barang_medis.tanggal between ? and ? and databarang.nama_brng like ? and bangsal.nm_bangsal like ? and "+
                    "(riwayat_barang_medis.kode_brng like ? or databarang.nama_brng like ? or riwayat_barang_medis.petugas like ? or "+
                    "bangsal.nm_bangsal like ? or riwayat_barang_medis.no_batch like ? or riwayat_barang_medis.no_faktur like ? or "+
                    "riwayat_barang_medis.kd_bangsal like ? or riwayat_barang_medis.status like ? or riwayat_barang_medis.keterangan like ? or "+
                    "riwayat_barang_medis.posisi like ?) "+"order by riwayat_barang_medis.tanggal,riwayat_barang_medis.jam ");
            }
                
            try {
                if(nmbar.getText().trim().equals("")&&NmGudang.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+nmbar.getText()+"%");
                    ps.setString(4,"%"+NmGudang.getText()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+TCari.getText().trim()+"%");
                    ps.setString(14,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();            
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("kode_brng")+" "+rs.getString("nama_brng"),
                        rs.getString("stok_awal"),rs.getString("masuk"),
                        rs.getString("keluar"),rs.getString("stok_akhir"),
                        rs.getString("posisi"),rs.getString("tanggal"),
                        rs.getString("jam"),rs.getString("petugas"),
                        rs.getString("kd_bangsal")+" "+rs.getString("nm_bangsal"),
                        rs.getString("status"),rs.getString("no_batch"),
                        rs.getString("no_faktur"),rs.getString("keterangan")
                    });                    
                }    
            } catch (Exception e) {
                System.out.println("Notifikasi Data Barang : "+e);
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
        
    }
    
    public void isCek(){
         BtnPrint.setEnabled(akses.getriwayat_obat_alkes_bhp());
    }
    
}

package toko;
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

public class TokoSirkulasi2 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private double ttltotalbeli=0,totalbeli=0,stok=0,totalstok=0,jumlahbeli=0,jumlahpiutang=0,totalpiutang=0,ttltotalpiutang=0,ttltotalpesan=0,totalpesan=0,jumlahpesan=0,
            jumlahpenjualan=0,totalpenjualan=0,ttltotalpenjualan=0,jumlahretursup=0,totalretursup=0,ttltotalretursup=0,jumlahreturjual=0,totalreturjual=0,ttltotalreturjual=0,
            jumlahreturpiutang=0,totalreturpiutang=0,ttltotalreturpiutang=0,stokakhir,totalstokakhir=0,ttltotalstokakhir=0,stokawal,totalstokawal=0,ttltotalstokawal=0;
    private TokoBarang barang=new TokoBarang(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String tglopname="";

    /** 
     * @param parent
     * @param modal */
    public TokoSirkulasi2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Kode Barang","Nama Barang","Satuan","Tgl.Opname","Stok Awal","Stok Awal(Rp)","Pengadaan","Pengadaan(Rp)","Penerimaan","Penerimaan(Rp)","Penjualan","Penjualan(Rp)","Piutang","Piutang(Rp)","Retur Sup","Retur Sup(Rp)","Retur Jual","Retur Jual(Rp)","Retur Piutang","Retur Piutang(Rp)","Stok Akhir","Stok Akhir(Rp)"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i <22; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(170);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(100);
            }else if(i==14){
                column.setPreferredWidth(70);
            }else if(i==15){
                column.setPreferredWidth(100);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(70);
            }else if(i==21){
                column.setPreferredWidth(100);
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
                if(akses.getform().equals("DlgSirkulasiBarang")){
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
                if(akses.getform().equals("DlgSirkulasiBarang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();                    
                    }                
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

        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Sirkulasi Barang Toko ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label11.setText("Tanggal Transaksi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(113, 23));
        panelisi4.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl2);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(85, 23));
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

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
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

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(79, 30));
        panelisi1.add(label9);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+A");
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
        panelisi1.add(BtnAll);

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
            
            Sequel.queryu("truncate table temporary_toko");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary_toko","'0',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'','','','','','','','','','','','','','',''",22,new String[]{
                    tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,2).toString(),
                    tabMode.getValueAt(i,3).toString(),tabMode.getValueAt(i,4).toString(),tabMode.getValueAt(i,5).toString(),
                    tabMode.getValueAt(i,6).toString(),tabMode.getValueAt(i,7).toString(),tabMode.getValueAt(i,8).toString(),
                    tabMode.getValueAt(i,9).toString(),tabMode.getValueAt(i,10).toString(),tabMode.getValueAt(i,11).toString(),
                    tabMode.getValueAt(i,12).toString(),tabMode.getValueAt(i,13).toString(),tabMode.getValueAt(i,14).toString(),
                    tabMode.getValueAt(i,15).toString(),tabMode.getValueAt(i,16).toString(),tabMode.getValueAt(i,17).toString(),
                    tabMode.getValueAt(i,18).toString(),tabMode.getValueAt(i,19).toString(),tabMode.getValueAt(i,20).toString(),
                    tabMode.getValueAt(i,21).toString()
                }); 
            }
            
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.MyReport("rptSirkulasiToko2.jasper","report","::[ Sirkulasi Barang Toko ]::",param);
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
            Sequel.cariIsi("select nama_brng from tokobarang where kode_brng=?",nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_brng from tokobarang where kode_brng=?",nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_brng from tokobarang where kode_brng=?",nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("DlgSirkulasiBarang");
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
        
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            TokoSirkulasi2 dialog = new TokoSirkulasi2(new javax.swing.JFrame(), true);
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
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBarang;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdbar;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
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
            ps=koneksi.prepareStatement("select tokobarang.kode_brng,tokobarang.nama_brng, "+
                        "kodesatuan.satuan,tokobarang.stok,(tokobarang.stok*tokobarang.dasar) as totalstok "+
                        "from tokobarang inner join kodesatuan on tokobarang.kode_sat=kodesatuan.kode_sat "+
                        "where tokobarang.nama_brng like ? and tokobarang.kode_brng like ? or "+
                        "tokobarang.nama_brng like ? and tokobarang.nama_brng like ? or "+
                        "tokobarang.nama_brng like ? and kodesatuan.satuan like ? "+
                        " order by tokobarang.kode_brng");
            try {
                ttltotalbeli=0;ttltotalpesan=0;ttltotalpenjualan=0;ttltotalstokakhir=0;ttltotalpiutang=0;ttltotalretursup=0;ttltotalreturjual=0;ttltotalreturpiutang=0;ttltotalstokawal=0;
                ps.setString(1,"%"+nmbar.getText()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+nmbar.getText()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+nmbar.getText()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();            
                while(rs.next()){
                    totalbeli=0;jumlahbeli=0;totalpesan=0;jumlahpesan=0;jumlahpenjualan=0;
                    totalpenjualan=0;stok=0;totalstok=0;jumlahpiutang=0;totalpiutang=0;jumlahretursup=0;
                    totalretursup=0;jumlahreturjual=0;totalreturjual=0;jumlahreturpiutang=0;
                    totalreturpiutang=0;stokakhir=0;totalstokakhir=0;stokakhir=0;totalstokakhir=0;
                    tglopname="0000-00-00";
                    tglopname=Sequel.cariIsi("select tanggal from tokoopname where kode_brng='"+rs.getString(1)+"' and tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by tanggal asc limit 1");

                    stok=rs.getDouble("stok");
                    totalstok=rs.getDouble("totalstok");
                    
                    ps2=koneksi.prepareStatement("select sum(toko_detail_beli.jumlah), sum(toko_detail_beli.total) "+
                        " from tokopembelian inner join toko_detail_beli "+
                        " on tokopembelian.no_faktur=toko_detail_beli.no_faktur "+
                        " where toko_detail_beli.kode_brng=? and tokopembelian.tgl_beli "+
                        " between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahbeli=rs2.getDouble(1);
                            totalbeli=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }          
                    
                    //tokopemesanan
                    ps2=koneksi.prepareStatement("select sum(toko_detail_pesan.jumlah), sum(toko_detail_pesan.total) "+
                        " from tokopemesanan inner join toko_detail_pesan "+
                        " on tokopemesanan.no_faktur=toko_detail_pesan.no_faktur "+
                        " where toko_detail_pesan.kode_brng=? and tokopemesanan.tgl_pesan "+
                        " between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpesan=rs2.getDouble(1);
                            totalpesan=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Pemesanan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }  
                    
                    ps2=koneksi.prepareStatement("select sum(toko_detail_jual.jumlah), sum(toko_detail_jual.total) "+
                        " from tokopenjualan inner join toko_detail_jual "+
                        " on tokopenjualan.nota_jual=toko_detail_jual.nota_jual "+
                        " where toko_detail_jual.kode_brng=? and "+
                        " tokopenjualan.tgl_jual between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpenjualan=rs2.getDouble(1);
                            totalpenjualan=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas penjualan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(toko_detail_piutang.jumlah), sum(toko_detail_piutang.total) "+
                        " from tokopiutang inner join toko_detail_piutang "+
                        " on tokopiutang.nota_piutang=toko_detail_piutang.nota_piutang "+
                        " where toko_detail_piutang.kode_brng=? and "+
                        " tokopiutang.tgl_piutang between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpiutang=rs2.getDouble(1);
                            totalpiutang=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas piutang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(toko_detail_returbeli.jml_retur), sum(toko_detail_returbeli.total) "+
                        " from tokoreturbeli inner join toko_detail_returbeli "+
                        " on tokoreturbeli.no_retur_beli=toko_detail_returbeli.no_retur_beli "+
                        " where toko_detail_returbeli.kode_brng=? and "+
                        " tokoreturbeli.tgl_retur between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretursup=rs2.getDouble(1);
                            totalretursup=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas piutang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(toko_detail_returjual.jml_retur), sum(toko_detail_returjual.total) "+
                        " from tokoreturjual inner join toko_detail_returjual "+
                        " on tokoreturjual.no_retur_jual=toko_detail_returjual.no_retur_jual "+
                        " where toko_detail_returjual.kode_brng=? and "+
                        " tokoreturjual.tgl_retur between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahreturjual=rs2.getDouble(1);
                            totalreturjual=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas piutang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(toko_detail_returpiutang.jml_retur), sum(toko_detail_returpiutang.total) "+
                        " from tokoreturpiutang inner join toko_detail_returpiutang "+
                        " on tokoreturpiutang.no_retur_piutang=toko_detail_returpiutang.no_retur_piutang "+
                        " where toko_detail_returpiutang.kode_brng=? and "+
                        " tokoreturpiutang.tgl_retur between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahreturpiutang=rs2.getDouble(1);
                            totalreturpiutang=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas piutang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(tokoopname.real),(tokoopname.real*tokoopname.dasar) from tokoopname where kode_brng=? and tanggal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,tglopname);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stokawal=rs2.getDouble(1);
                            totalstokawal=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Note : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    if((stok>0)||(jumlahbeli>0)||(jumlahpesan>0)||(jumlahpenjualan>0)||(jumlahpiutang>0)||(jumlahretursup>0)||(jumlahreturjual>0)||(jumlahreturpiutang>0)){
                        if(stokawal<=0){
                            stokawal=stok-jumlahbeli-jumlahpesan+jumlahpenjualan+jumlahpiutang+jumlahretursup-jumlahreturjual-jumlahreturpiutang;
                            stokakhir=stok;
                            totalstokawal=totalstok-totalbeli-totalpesan+totalpenjualan+totalpiutang+totalretursup-totalreturjual-totalreturpiutang;
                            totalstokakhir=totalstok;
                        }else{
                            stokakhir=stokawal+jumlahbeli+jumlahpesan-jumlahpenjualan-jumlahpiutang-jumlahretursup+jumlahreturjual+jumlahreturpiutang;
                            totalstokakhir=totalstokawal+totalbeli+totalpesan-totalpenjualan-totalpiutang-totalretursup+totalreturjual+totalreturpiutang;
                        }
                        
                        tabMode.addRow(new Object[]{rs.getString(1),rs.getString(2),
                           rs.getString(3),tglopname,Valid.SetAngka(stokawal),Valid.SetAngka(totalstokawal),
                           Valid.SetAngka(jumlahbeli),Valid.SetAngka(totalbeli),
                           Valid.SetAngka(jumlahpesan),Valid.SetAngka(totalpesan),
                           Valid.SetAngka(jumlahpenjualan),Valid.SetAngka(totalpenjualan),
                           Valid.SetAngka(jumlahpiutang),Valid.SetAngka(totalpiutang),
                           Valid.SetAngka(jumlahretursup),Valid.SetAngka(totalretursup),
                           Valid.SetAngka(jumlahreturjual),Valid.SetAngka(totalreturjual),
                           Valid.SetAngka(jumlahreturpiutang),Valid.SetAngka(totalreturpiutang),
                           Valid.SetAngka(stokakhir),Valid.SetAngka(totalstokakhir)
                        }); 
                        ttltotalbeli=ttltotalbeli+totalbeli;
                        ttltotalpesan=ttltotalpesan+totalpesan;
                        ttltotalpenjualan=ttltotalpenjualan+totalpenjualan;
                        ttltotalpiutang=ttltotalpiutang+totalpiutang;
                        ttltotalretursup=ttltotalretursup+totalretursup;
                        ttltotalreturjual=ttltotalreturjual+totalreturjual;
                        ttltotalreturpiutang=ttltotalreturpiutang+totalreturpiutang;
                        ttltotalstokawal=ttltotalstokawal+totalstokawal;
                        ttltotalstokakhir=ttltotalstokakhir+totalstokakhir;
                    }
                }   
                tabMode.addRow(new Object[]{"","","","","","","","","","","","","","","","","","","","","",""}); 
                tabMode.addRow(new Object[]{
                    "<>>","Total :","","","",Valid.SetAngka(ttltotalstokawal),"",Valid.SetAngka(ttltotalbeli),"",Valid.SetAngka(ttltotalpesan),"",Valid.SetAngka(ttltotalpenjualan),"",Valid.SetAngka(ttltotalpiutang),"",Valid.SetAngka(ttltotalretursup),"",Valid.SetAngka(ttltotalreturjual),"",Valid.SetAngka(ttltotalreturpiutang),"",Valid.SetAngka(ttltotalstokakhir)
                }); 
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
         BtnPrint.setEnabled(akses.gettoko_sirkulasi());
    }
    
}

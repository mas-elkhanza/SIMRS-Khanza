/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package inventaris;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author dosen
 */
public final class InventarisCariBarangCSSD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private String namaruang="";
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;


    /** Creates new form DlgJnsPerawatan
     * @param parent
     * @param modal */
    public InventarisCariBarangCSSD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"No.Inventaris","Kode Barang","Nama Barang","Produsen",
                      "Merk","Thn.Produksi","Barcode SN","Kategori","Jenis",
                      "Asal Barang","Tgl.Pengadaan","Harga","Stts.Barang","Ruang",
                      "No.Rak","No.Box"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 16; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(160);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(120);
            }else if(i==14){
                column.setPreferredWidth(60);
            }else if(i==15){
                column.setPreferredWidth(60);
            }
        }
        
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCari.requestFocus();
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil2();
                    }
                }
            });
        }
        
        ChkAccor.setSelected(false);
        isPhoto();
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"+
                ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
        );
        
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
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
        tbJnsPerawatan = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        panelGlass9 = new widget.panelisi();
        label23 = new widget.Label();
        NmRuangan = new widget.TextBox();
        btnRuang1 = new widget.Button();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Barang CSSD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Photo Aset Inventaris : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label23.setText("Ruang :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass9.add(label23);

        NmRuangan.setEditable(false);
        NmRuangan.setName("NmRuangan"); // NOI18N
        NmRuangan.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass9.add(NmRuangan);

        btnRuang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRuang1.setMnemonic('1');
        btnRuang1.setToolTipText("Alt+1");
        btnRuang1.setName("btnRuang1"); // NOI18N
        btnRuang1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnRuang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuang1ActionPerformed(evt);
            }
        });
        panelGlass9.add(btnRuang1);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel6);

        TCari.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        panelGlass9.add(BtnAll);

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
        panelGlass9.add(BtnTambah);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

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
        panelGlass9.add(BtnKeluar);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

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
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbJnsPerawatan.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil2();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
            }            
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/inentariscssd.iyem")<30){
                tampil2();
            }else{
                tampil();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnRuang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuang1ActionPerformed
        InventarisRuang ruang=new InventarisRuang(null,false); 
        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruang.getTable().getSelectedRow()!= -1){ 
                    NmRuangan.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),1).toString());
                    TCari.requestFocus();                   
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
        
        ruang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    ruang.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        ruang.isCek();
        ruang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setAlwaysOnTop(false);
        ruang.setVisible(true);
    }//GEN-LAST:event_btnRuang1ActionPerformed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Inventaris...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventarisBarangCSSD form=new InventarisBarangCSSD(null,false);
        form.emptTeks();
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventarisCariBarangCSSD dialog = new InventarisCariBarangCSSD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnTambah;
    private widget.CekBox ChkAccor;
    private widget.PanelBiasa FormPhoto;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox NmRuangan;
    private widget.PanelBiasa PanelAccor;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private widget.Button btnRuang1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label label23;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        try {
            file=new File("./cache/inentariscssd.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select inventaris.no_inventaris,inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
                        "inventaris_produsen.nama_produsen, inventaris_merk.nama_merk, inventaris_barang.thn_produksi, inventaris_barang.isbn,"+
                        "inventaris_kategori.nama_kategori, cssd_barang.jenis_barang,inventaris.asal_barang,inventaris.tgl_pengadaan,"+
                        "inventaris.harga,inventaris.status_barang,inventaris_ruang.nama_ruang,inventaris.no_rak,inventaris.no_box "+
                        "from inventaris inner join inventaris_barang on inventaris_barang.kode_barang=inventaris.kode_barang "+
                        "inner join inventaris_produsen on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen "+
                        "inner join inventaris_ruang on inventaris.id_ruang=inventaris_ruang.id_ruang "+
                        "inner join inventaris_kategori on inventaris_barang.id_kategori=inventaris_kategori.id_kategori "+
                        "inner join inventaris_merk on inventaris_barang.id_merk=inventaris_merk.id_merk "+
                        "inner join cssd_barang on inventaris.no_inventaris=cssd_barang.no_inventaris "+
                        "order by inventaris_barang.kode_barang,inventaris.no_inventaris");
             Valid.tabelKosong(tabMode);
             try{            
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_inventaris"),rs.getString("kode_barang"),rs.getString("nama_barang"),rs.getString("nama_produsen"),
                        rs.getString("nama_merk"),rs.getString("thn_produksi").substring(0,4),rs.getString("isbn"),rs.getString("nama_kategori"),
                        rs.getString("jenis_barang"),rs.getString("asal_barang"),rs.getString("tgl_pengadaan"),Valid.SetAngka(rs.getDouble("harga")),
                        rs.getString("status_barang"),rs.getString("nama_ruang"),rs.getString("no_rak"),rs.getString("no_box")
                    });
                    iyembuilder.append("{\"NoInventaris\":\"").append(rs.getString("no_inventaris")).append("\",\"KodeBarang\":\"").append(rs.getString("kode_barang")).
                                append("\",\"NamaBarang\":\"").append(rs.getString("nama_barang")).append("\",\"Produsen\":\"").append(rs.getString("nama_produsen")).
                                append("\",\"Merk\":\"").append(rs.getString("nama_merk")).append("\",\"ThnProduksi\":\"").append(rs.getString("thn_produksi").substring(0,4)).
                                append("\",\"BarcodeSN\":\"").append(rs.getString("isbn")).append("\",\"Kategori\":\"").append(rs.getString("nama_kategori")).
                                append("\",\"Jenis\":\"").append(rs.getString("jenis_barang")).append("\",\"AsalBarang\":\"").append(rs.getString("asal_barang")).
                                append("\",\"TglPengadaan\":\"").append(rs.getString("tgl_pengadaan")).append("\",\"Harga\":\"").append(Valid.SetAngka(rs.getDouble("harga"))).
                                append("\",\"SttsBarang\":\"").append(rs.getString("status_barang")).append("\",\"Ruang\":\"").append(rs.getString("nama_ruang")).
                                append("\",\"NoRak\":\"").append(rs.getString("no_rak")).append("\",\"NoBox\":\"").append(rs.getString("no_box")).append("\"},");
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"inentariscssd\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
            LCount.setText(tabMode.getRowCount()+"");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }        
    }
    
    private void tampil2() {
        try {
            myObj = new FileReader("./cache/inentariscssd.iyem");
            root = mapper.readTree(myObj);
            Valid.tabelKosong(tabMode);
            response = root.path("inentariscssd");
            if(response.isArray()){
                if(NmRuangan.getText().trim().equals("")){
                    if(TCari.getText().trim().equals("")){
                        for(JsonNode list:response){
                            tabMode.addRow(new Object[]{
                                list.path("NoInventaris").asText(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Produsen").asText(),list.path("Merk").asText(),list.path("ThnProduksi").asText(),list.path("BarcodeSN").asText(),list.path("Kategori").asText(),list.path("Jenis").asText(),list.path("AsalBarang").asText(),list.path("TglPengadaan").asText(),list.path("Harga").asText(),list.path("SttsBarang").asText(),list.path("Ruang").asText(),list.path("NoRak").asText(),list.path("NoBox").asText()
                            });
                        }
                    }else{
                        for(JsonNode list:response){
                            if(list.path("NoInventaris").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("KodeBarang").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("NamaBarang").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("Produsen").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("Merk").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("BarcodeSN").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("Kategori").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("Jenis").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                                tabMode.addRow(new Object[]{
                                    list.path("NoInventaris").asText(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Produsen").asText(),list.path("Merk").asText(),list.path("ThnProduksi").asText(),list.path("BarcodeSN").asText(),list.path("Kategori").asText(),list.path("Jenis").asText(),list.path("AsalBarang").asText(),list.path("TglPengadaan").asText(),list.path("Harga").asText(),list.path("SttsBarang").asText(),list.path("Ruang").asText(),list.path("NoRak").asText(),list.path("NoBox").asText()
                                });
                            }
                        }
                    }
                }else{
                    if(TCari.getText().trim().equals("")){
                        for(JsonNode list:response){
                            if(list.path("Ruang").asText().equals(NmRuangan.getText())){
                                tabMode.addRow(new Object[]{
                                    list.path("NoInventaris").asText(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Produsen").asText(),list.path("Merk").asText(),list.path("ThnProduksi").asText(),list.path("BarcodeSN").asText(),list.path("Kategori").asText(),list.path("Jenis").asText(),list.path("AsalBarang").asText(),list.path("TglPengadaan").asText(),list.path("Harga").asText(),list.path("SttsBarang").asText(),list.path("Ruang").asText(),list.path("NoRak").asText(),list.path("NoBox").asText()
                                });
                            }
                        }
                    }else{
                        for(JsonNode list:response){
                            if(list.path("NoInventaris").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("KodeBarang").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("NamaBarang").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("Produsen").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("Merk").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("BarcodeSN").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("Kategori").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("Jenis").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                                if(list.path("Ruang").asText().equals(NmRuangan.getText())){
                                    tabMode.addRow(new Object[]{
                                        list.path("NoInventaris").asText(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Produsen").asText(),list.path("Merk").asText(),list.path("ThnProduksi").asText(),list.path("BarcodeSN").asText(),list.path("Kategori").asText(),list.path("Jenis").asText(),list.path("AsalBarang").asText(),list.path("TglPengadaan").asText(),list.path("Harga").asText(),list.path("SttsBarang").asText(),list.path("Ruang").asText(),list.path("NoRak").asText(),list.path("NoBox").asText()
                                    });
                                }
                            }
                        }
                    }
                }
            }
            myObj.close();
        } catch (Exception ex) {
            if(ex.toString().contains("java.io.FileNotFoundException")){
                tampil();
            }else{
                System.out.println("Notifikasi : "+ex);
            }
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public JTable getTable(){
        return tbJnsPerawatan;
    }
    
    public void isCek(){
        try {
            namaruang=koneksiDB.KAMARAKTIFRANAP();
        } catch (Exception ex) {
            namaruang="";
        }
        
        if(!namaruang.equals("")){
            if(akses.getkode().equals("Admin Utama")){
                NmRuangan.setText("");
                btnRuang1.setEnabled(true);
                NmRuangan.setEditable(true);
            }else{
                NmRuangan.setText(namaruang);
                btnRuang1.setEnabled(false);
                NmRuangan.setEditable(false);
            }                
        }else{
            btnRuang1.setEnabled(true);
            NmRuangan.setEditable(true);
        }
        BtnTambah.setEnabled(akses.getbarang_cssd());
        TCari.requestFocus();
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(internalFrame1.getWidth()-300,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }
    
    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select inventaris_gambar.photo from inventaris_gambar where inventaris_gambar.no_inventaris=?");
                try {
                    ps.setString(1,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/inventaris/"+rs.getString("photo")+"' alt='photo' width='"+(internalFrame1.getWidth()-330)+"' height='"+(internalFrame1.getHeight()-260)+"'/></center></body></html>");
                        }  
                    }else{
                        LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
        }
    }
}

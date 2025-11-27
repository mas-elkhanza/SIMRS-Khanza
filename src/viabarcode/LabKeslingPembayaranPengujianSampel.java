package viabarcode;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
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
import kepegawaian.DlgCariPetugas;

public class LabKeslingPembayaranPengujianSampel extends javax.swing.JDialog {
    private final DefaultTableModel tabModeTidakDapatDilayani,tabModeDetailPermintaan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public LabKeslingPembayaranPengujianSampel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabModeTidakDapatDilayani=new DefaultTableModel(null,new Object[]{
                "Waktu Diterima","No.Permintaan","No.Pelanggan","Nama Pelanggan","Kode Sampel","Nama Sampel","Unsur Kaji Ulang/Abnornalitas Sampel/Keterangan Lainnya"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbTidakDapatDilayani.setModel(tabModeTidakDapatDilayani);

        tbTidakDapatDilayani.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbTidakDapatDilayani.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbTidakDapatDilayani.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(118);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(76);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(130);
            }else if(i==6){
                column.setPreferredWidth(700);
            }
        }
        tbTidakDapatDilayani.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailPermintaan=new DefaultTableModel(null,new Object[]{
                "Kode","Nama Parameter","Metode Pengujian","Satuan","Kategori","Nilai Normal"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailPermintaan.setModel(tabModeDetailPermintaan);
        tbDetailPermintaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDetailPermintaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(220);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(80);
            }
        }
        tbDetailPermintaan.setDefaultRenderer(Object.class, new WarnaTable());

        NoPermintaan.setDocument(new batasInput((byte)20).getKata(NoPermintaan));
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
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        
        ChkAccor.setSelected(false);
        PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
        scrollPaneDetail.setVisible(false); 
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        KodeSampel = new widget.TextBox();
        KodePelanggan = new widget.TextBox();
        KodePetugas = new widget.TextBox();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnHapus1 = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tanggal1 = new widget.Tanggal();
        label12 = new widget.Label();
        Tanggal2 = new widget.Tanggal();
        label14 = new widget.Label();
        Status = new widget.ComboBox();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        NamaPelanggan = new widget.TextBox();
        btnPelanggan = new widget.Button();
        label7 = new widget.Label();
        NamaSampel = new widget.TextBox();
        btnSampel = new widget.Button();
        label15 = new widget.Label();
        NoPermintaan = new widget.TextBox();
        scrollPane3 = new widget.ScrollPane();
        tbTidakDapatDilayani = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        scrollPaneDetail = new widget.ScrollPane();
        tbDetailPermintaan = new widget.Table();

        KodeSampel.setName("KodeSampel"); // NOI18N
        KodeSampel.setPreferredSize(new java.awt.Dimension(207, 23));

        KodePelanggan.setName("KodePelanggan"); // NOI18N
        KodePelanggan.setPreferredSize(new java.awt.Dimension(80, 23));

        KodePetugas.setName("KodePetugas"); // NOI18N
        KodePetugas.setPreferredSize(new java.awt.Dimension(80, 23));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pembayaran Pengujian Sampel Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Record :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(48, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi1.add(LTotal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/gaji.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Bayar Tagihan");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(145, 30));
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
        panelisi1.add(BtnHapus);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
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

        BtnHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnHapus1.setMnemonic('R');
        BtnHapus1.setText("Rekap Pembayaran");
        BtnHapus1.setToolTipText("Alt+R");
        BtnHapus1.setName("BtnHapus1"); // NOI18N
        BtnHapus1.setPreferredSize(new java.awt.Dimension(165, 30));
        BtnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapus1ActionPerformed(evt);
            }
        });
        BtnHapus1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapus1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnHapus1);

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

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Validasi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(71, 23));
        panelisi4.add(label11);

        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal1);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(26, 23));
        panelisi4.add(label12);

        Tanggal2.setDisplayFormat("dd-MM-yyyy");
        Tanggal2.setName("Tanggal2"); // NOI18N
        Tanggal2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal2KeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal2);

        label14.setText("Status :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi4.add(label14);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Bayar", "Belum Bayar" }));
        Status.setName("Status"); // NOI18N
        Status.setPreferredSize(new java.awt.Dimension(113, 23));
        panelisi4.add(Status);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(199, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi4.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelisi4.add(BtnCari);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Pelanggan :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label17);

        NamaPelanggan.setEditable(false);
        NamaPelanggan.setName("NamaPelanggan"); // NOI18N
        NamaPelanggan.setPreferredSize(new java.awt.Dimension(184, 23));
        panelisi3.add(NamaPelanggan);

        btnPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPelanggan.setMnemonic('4');
        btnPelanggan.setToolTipText("Alt+4");
        btnPelanggan.setName("btnPelanggan"); // NOI18N
        btnPelanggan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPelangganActionPerformed(evt);
            }
        });
        panelisi3.add(btnPelanggan);

        label7.setText("Sampel :");
        label7.setName("label7"); // NOI18N
        label7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label7);

        NamaSampel.setEditable(false);
        NamaSampel.setName("NamaSampel"); // NOI18N
        NamaSampel.setPreferredSize(new java.awt.Dimension(144, 23));
        panelisi3.add(NamaSampel);

        btnSampel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSampel.setMnemonic('1');
        btnSampel.setToolTipText("Alt+1");
        btnSampel.setName("btnSampel"); // NOI18N
        btnSampel.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSampel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSampelActionPerformed(evt);
            }
        });
        panelisi3.add(btnSampel);

        label15.setText("No.Permintaan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(91, 23));
        panelisi3.add(label15);

        NoPermintaan.setName("NoPermintaan"); // NOI18N
        NoPermintaan.setPreferredSize(new java.awt.Dimension(142, 23));
        NoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPermintaanKeyPressed(evt);
            }
        });
        panelisi3.add(NoPermintaan);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbTidakDapatDilayani.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTidakDapatDilayani.setToolTipText("Silahkan klik untuk memilih data yang mau dihapus");
        tbTidakDapatDilayani.setName("tbTidakDapatDilayani"); // NOI18N
        scrollPane3.setViewportView(tbTidakDapatDilayani);

        internalFrame1.add(scrollPane3, java.awt.BorderLayout.CENTER);

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

        scrollPaneDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Detail Pemeriksaan :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPaneDetail.setName("scrollPaneDetail"); // NOI18N
        scrollPaneDetail.setOpaque(true);

        tbDetailPermintaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailPermintaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailPermintaan.setName("tbDetailPermintaan"); // NOI18N
        scrollPaneDetail.setViewportView(tbDetailPermintaan);

        PanelAccor.add(scrollPaneDetail, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,KodePelanggan);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            Tanggal1.requestFocus();
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
        NoPermintaan.setText("");
        KodeSampel.setText("");
        NamaSampel.setText("");
        KodePelanggan.setText("");
        NamaPelanggan.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabModeTidakDapatDilayani.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabModeTidakDapatDilayani.getRowCount()!=0){
            try{
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw;
                StringBuilder htmlContent;

                String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Diterima</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Unsur Kaji Ulang/Abnornalitas Sampel/Keterangan Lainnya</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabModeTidakDapatDilayani.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,6).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPermintaanPengujianSampelTidakDilayani.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PERMINTAAN PENGUJIAN SAMPEL TIDAK DAPAT DILAYANI<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Diterima</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Unsur Kaji Ulang/Abnornalitas Sampel/Keterangan Lainnya</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabModeTidakDapatDilayani.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbTidakDapatDilayani.getValueAt(i,6).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPermintaanPengujianSampelTidakDilayani.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PERMINTAAN PENGUJIAN SAMPEL TIDAK DAPAT DILAYANI<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"Waktu Diterima\";\"No.Permintaan\";\"No.Pelanggan\";\"Nama Pelanggan\";\"Kode Sampel\";\"Nama Sampel\";\"Unsur Kaji Ulang/Abnornalitas Sampel/Keterangan Lainnya\"\n"
                            ); 
                            for (int i = 0; i < tabModeTidakDapatDilayani.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbTidakDapatDilayani.getValueAt(i,0).toString()).append("\";\"").append(tbTidakDapatDilayani.getValueAt(i,1).toString()).append("\";\"").append(tbTidakDapatDilayani.getValueAt(i,2).toString()).append("\";\"").append(tbTidakDapatDilayani.getValueAt(i,3).toString()).append("\";\"").append(tbTidakDapatDilayani.getValueAt(i,4).toString()).append("\";\"").append(tbTidakDapatDilayani.getValueAt(i,5).toString()).append("\";\"").append(tbTidakDapatDilayani.getValueAt(i,6).toString()).append("\"\n");
                            }
                            f = new File("DataPermintaanPengujianSampelTidakDilayani.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                }   
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbTidakDapatDilayani.getSelectedRow()!= -1){
            if(Sequel.queryutf("delete from labkesling_permintaan_pengujian_sampel_tidak_dilayani where no_permintaan='"+tbTidakDapatDilayani.getValueAt(tbTidakDapatDilayani.getSelectedRow(),1).toString()+"'")==true){
                Sequel.queryu("update labkesling_permintaan_pengujian_sampel set status='Permintaan Baru' where no_permintaan='"+tbTidakDapatDilayani.getValueAt(tbTidakDapatDilayani.getSelectedRow(),1).toString()+"'");
                tabModeTidakDapatDilayani.removeRow(tbTidakDapatDilayani.getSelectedRow());
                Valid.tabelKosong(tabModeDetailPermintaan);
                LTotal.setText(tabModeTidakDapatDilayani.getRowCount()+"");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data tidak dapat dilayani...!!!");
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbTidakDapatDilayani.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==true){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(670,HEIGHT));
                scrollPaneDetail.setVisible(true);
                ChkAccor.setVisible(true);
                Valid.tabelKosong(tabModeDetailPermintaan);
                try {
                    ps=koneksi.prepareStatement(
                        "select labkesling_detail_permintaan_pengujian_sampel.kode_parameter,labkesling_parameter_pengujian.nama_parameter,labkesling_parameter_pengujian.metode_pengujian,labkesling_parameter_pengujian.satuan,"+
                        "labkesling_parameter_pengujian.kategori,labkesling_nilai_normal_baku_mutu.nilai_normal from labkesling_detail_permintaan_pengujian_sampel inner join labkesling_parameter_pengujian "+
                        "on labkesling_detail_permintaan_pengujian_sampel.kode_parameter=labkesling_parameter_pengujian.kode_parameter inner join labkesling_nilai_normal_baku_mutu "+
                        "on labkesling_nilai_normal_baku_mutu.kode_parameter=labkesling_parameter_pengujian.kode_parameter where labkesling_detail_permintaan_pengujian_sampel.no_permintaan=? "+
                        "and labkesling_nilai_normal_baku_mutu.kode_sampel=? order by labkesling_detail_permintaan_pengujian_sampel.kode_parameter"
                    );
                    try {
                        ps.setString(1,tbTidakDapatDilayani.getValueAt(tbTidakDapatDilayani.getSelectedRow(),1).toString());
                        ps.setString(2,tbTidakDapatDilayani.getValueAt(tbTidakDapatDilayani.getSelectedRow(),4).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeDetailPermintaan.addRow(new Object[]{
                                rs.getString("kode_parameter"),rs.getString("nama_parameter"),rs.getString("metode_pengujian"),rs.getString("satuan"),rs.getString("kategori"),rs.getString("nilai_normal")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }else if(ChkAccor.isSelected()==false){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
                scrollPaneDetail.setVisible(false);
                ChkAccor.setVisible(true);
            }
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih data permintaan...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        Valid.pindah(evt,NoPermintaan,Status);
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void Tanggal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal2KeyPressed

    private void btnPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPelangganActionPerformed
        LabKeslingPelanggan pelanggan=new LabKeslingPelanggan(null,false);
        pelanggan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pelanggan.getTable().getSelectedRow()!= -1){
                    KodePelanggan.setText(pelanggan.getTable().getValueAt(pelanggan.getTable().getSelectedRow(),0).toString());
                    NamaPelanggan.setText(pelanggan.getTable().getValueAt(pelanggan.getTable().getSelectedRow(),1).toString());
                }
                btnPelanggan.requestFocus();
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

        pelanggan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pelanggan.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        pelanggan.isCek();
        pelanggan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pelanggan.setLocationRelativeTo(internalFrame1);
        pelanggan.setVisible(true);
    }//GEN-LAST:event_btnPelangganActionPerformed

    private void btnSampelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSampelActionPerformed
        LabKeslingCariMasterSampelBakuMutu sampel=new LabKeslingCariMasterSampelBakuMutu(null,false);
        sampel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(sampel.getTable().getSelectedRow()!= -1){
                    KodeSampel.setText(sampel.getTable().getValueAt(sampel.getTable().getSelectedRow(),0).toString());
                    NamaSampel.setText(sampel.getTable().getValueAt(sampel.getTable().getSelectedRow(),1).toString());
                }
                btnSampel.requestFocus();
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

        sampel.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    sampel.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        sampel.isCek();
        sampel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        sampel.setLocationRelativeTo(internalFrame1);
        sampel.setVisible(true);
    }//GEN-LAST:event_btnSampelActionPerformed

    private void NoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPermintaanKeyPressed
        Valid.pindah(evt, BtnKeluar,Status);
    }//GEN-LAST:event_NoPermintaanKeyPressed

    private void BtnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapus1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHapus1ActionPerformed

    private void BtnHapus1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapus1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHapus1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingPembayaranPengujianSampel dialog = new LabKeslingPembayaranPengujianSampel(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnHapus1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.CekBox ChkAccor;
    private widget.TextBox KodePelanggan;
    private widget.TextBox KodePetugas;
    private widget.TextBox KodeSampel;
    private widget.Label LTotal;
    private widget.editorpane LoadHTML;
    private widget.TextBox NamaPelanggan;
    private widget.TextBox NamaSampel;
    private widget.TextBox NoPermintaan;
    private widget.PanelBiasa PanelAccor;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal1;
    private widget.Tanggal Tanggal2;
    private widget.Button btnPelanggan;
    private widget.Button btnSampel;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label17;
    private widget.Label label7;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPaneDetail;
    private widget.Table tbDetailPermintaan;
    private widget.Table tbTidakDapatDilayani;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabModeTidakDapatDilayani);
        try{  
            /*ps=koneksi.prepareStatement(
                        "select labkesling_permintaan_pengujian_sampel.no_permintaan,labkesling_permintaan_pengujian_sampel.kode_pelanggan,labkesling_pelanggan.nama_pelanggan,labkesling_permintaan_pengujian_sampel.waktu_diterima,"+
                        "labkesling_permintaan_pengujian_sampel.kode_sampel,labkesling_master_sampel.nama_sampel,labkesling_permintaan_pengujian_sampel_tidak_dilayani.keterangan from labkesling_permintaan_pengujian_sampel "+
                        "inner join labkesling_pelanggan on labkesling_permintaan_pengujian_sampel.kode_pelanggan=labkesling_pelanggan.kode_pelanggan "+
                        "inner join labkesling_master_sampel on labkesling_permintaan_pengujian_sampel.kode_sampel=labkesling_master_sampel.kode_sampel "+
                        "inner join labkesling_permintaan_pengujian_sampel_tidak_dilayani on labkesling_permintaan_pengujian_sampel_tidak_dilayani.no_permintaan=labkesling_permintaan_pengujian_sampel.no_permintaan "+
                        "where labkesling_permintaan_pengujian_sampel.waktu_diterima between ? and ? "+(NoPermintaan.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.no_permintaan='"+NoPermintaan.getText()+"' ")+
                        (NamaPetugas.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.nip='"+KodePetugas.getText()+"' ")+
                        (NamaPelanggan.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_pelanggan='"+KodePelanggan.getText()+"' ")+
                        (NamaSampel.getText().trim().equals("")?"":" and labkesling_permintaan_pengujian_sampel.kode_sampel='"+KodeSampel.getText()+"' ")+
                        (TCari.getText().trim().equals("")?"":" and (labkesling_permintaan_pengujian_sampel.lokasi_sampling like ? or labkesling_permintaan_pengujian_sampel.deskripsi_sampel like ? or labkesling_permintaan_pengujian_sampel.jenis_sampel like ? "+
                        "or labkesling_permintaan_pengujian_sampel.sampling_dilakukan_oleh like ? or labkesling_permintaan_pengujian_sampel.wadah_sampel like ? or labkesling_permintaan_pengujian_sampel.kondisi_wadah_sampel like ? "+
                        "or labkesling_permintaan_pengujian_sampel_tidak_dilayani.keterangan like ?) ")+"order by labkesling_permintaan_pengujian_sampel.waktu_diterima,labkesling_permintaan_pengujian_sampel.no_permintaan");
                
            try {
                ps.setString(1,Valid.SetTgl(Tanggal1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tanggal2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeTidakDapatDilayani.addRow(new Object[]{
                        rs.getString("waktu_diterima"),rs.getString("no_permintaan"),rs.getString("kode_pelanggan"),rs.getString("nama_pelanggan"),rs.getString("kode_sampel"),rs.getString("nama_sampel"),rs.getString("keterangan")
                    }); 
                }        
                LTotal.setText(tabModeTidakDapatDilayani.getRowCount()+"");
            } catch (Exception e) {
                System.out.println("Note : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }   */         
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }        
    }
    
    public void isCek(){
        TCari.requestFocus();
        BtnPrint.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
        BtnHapus.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
    }
}

/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package bridging;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author dosen
 */
public final class ApotekBPJSDaftarPermintaanResepIterasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDetail;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private int i=0;
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public ApotekBPJSDaftarPermintaanResepIterasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new String[]{
                "No.SEP Asal","No.Rawat","No.RM","Nama Pasien","No.Kartu","No.Resep Awal","Tgl.Resep Awal","No.Resep Iter","Status Iter","Dokter Peresep","Tgl.Iterasi","Tgl & Jam Penyerahan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbResep.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(87);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(117);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setPreferredWidth(117);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetail=new DefaultTableModel(null,new Object[]{
                "No.Racik","Kode Obat","Nama Obat","Aturan Pakai","Jml.Obat"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailResep.setModel(tabModeDetail);
        tbDetailResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbDetailResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(60);
            }
        }
        tbDetailResep.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        ChkAccor.setSelected(false);
        PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
        scrollPaneDetail.setVisible(false); 
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTMLPhoto.setEditable(true);
        LoadHTMLPhoto.setEditorKit(kit);
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
        LoadHTMLPhoto.setDocument(doc);
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        panelGlass6 = new widget.panelisi();
        jLabel17 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnPrint1 = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        TabData = new javax.swing.JTabbedPane();
        scrollPaneDetail = new widget.ScrollPane();
        tbDetailResep = new widget.Table();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTMLPhoto = new widget.editorpane();
        scrollPane1 = new widget.ScrollPane();
        tbResep = new widget.Table();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Daftar Permintaan Resep Iterasi Apotek Online BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel17.setText("Resep Awal :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(72, 23));
        panelGlass6.add(jLabel17);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass6.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(DTPCari2);

        jLabel16.setText("Keyword :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(jLabel16);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass6.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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
        panelGlass6.add(BtnCari);

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
        panelGlass6.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(LCount);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/3079288_adobe file extensions_adobe fireworks_document_extension icon_file_icon.png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnPrint1);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass6.add(BtnKeluar);

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

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

        TabData.setBackground(new java.awt.Color(254, 255, 254));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        scrollPaneDetail.setName("scrollPaneDetail"); // NOI18N
        scrollPaneDetail.setOpaque(true);

        tbDetailResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailResep.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailResep.setName("tbDetailResep"); // NOI18N
        scrollPaneDetail.setViewportView(tbDetailResep);

        TabData.addTab("Detail Resep Iterasi", scrollPaneDetail);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(null);
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass2.setBackground(new java.awt.Color(255, 255, 255));
        FormPass2.setBorder(null);
        FormPass2.setName("FormPass2"); // NOI18N
        FormPass2.setPreferredSize(new java.awt.Dimension(115, 40));

        BtnRefreshPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto.setMnemonic('U');
        BtnRefreshPhoto.setText("Refresh");
        BtnRefreshPhoto.setToolTipText("Alt+U");
        BtnRefreshPhoto.setName("BtnRefreshPhoto"); // NOI18N
        BtnRefreshPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(BtnRefreshPhoto);

        FormPhoto.add(FormPass2, java.awt.BorderLayout.PAGE_END);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTMLPhoto.setBorder(null);
        LoadHTMLPhoto.setName("LoadHTMLPhoto"); // NOI18N
        Scroll4.setViewportView(LoadHTMLPhoto);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabData.addTab("Photo Penyerahan Resep", FormPhoto);

        PanelAccor.add(TabData, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbResep.setAutoCreateRowSorter(true);
        tbResep.setName("tbResep"); // NOI18N
        tbResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbResep);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnKeluar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
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
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Asal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Kartu</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Resep Awal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Resep Awal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Resep Iter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Iter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dokter Peresep</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Iterasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Penyerahan</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,11).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPermintaanResepIterasi.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA RESEP APOTEK ONLINE BPJS<br><br></font>"+        
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
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Asal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Kartu</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Resep Awal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Resep Awal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Resep Iter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Iter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dokter Peresep</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Iterasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Penyerahan</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbResep.getValueAt(i,11).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPermintaanResepIterasi.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA RESEP APOTEK ONLINE BPJS<br><br></font>"+        
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
                                "\"No.SEP Asal\";\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"No.Kartu\";\"No.Resep Awal\";\"Tgl.Resep Awal\";\"No.Resep Iter\";\"Status Iter\";\"Dokter Peresep\";\"Tgl.Iterasi\";\"Tgl & Jam Penyerahan\"\n"
                            ); 
                            for (int i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbResep.getValueAt(i,0).toString()).append("\";\"").append(tbResep.getValueAt(i,1).toString()).append("\";\"").append(tbResep.getValueAt(i,2).toString()).append("\";\"").append(tbResep.getValueAt(i,3).toString()).append("\";\"").append(tbResep.getValueAt(i,4).toString()).append("\";\"").append(tbResep.getValueAt(i,5).toString()).append("\";\"").append(tbResep.getValueAt(i,6).toString()).append("\";\"").append(tbResep.getValueAt(i,7).toString()).append("\";\"").append(tbResep.getValueAt(i,8).toString()).append("\";\"").append(tbResep.getValueAt(i,9).toString()).append("\";\"").append(tbResep.getValueAt(i,10).toString()).append("\";\"").append(tbResep.getValueAt(i,11).toString()).append("\"\n");
                            }
                            f = new File("DataPermintaanResepIterasi.csv");            
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

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnPrint.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnCariActionPerformed(null);
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        runBackground(() ->tampil());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
           runBackground(() ->tampil());
        }else{
            Valid.pindah(evt,TCari,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
            });
        } 
    }//GEN-LAST:event_formWindowOpened

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        runBackground(() ->tampil());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbResep.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==true){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(670,HEIGHT));
                scrollPaneDetail.setVisible(true);
                ChkAccor.setVisible(true);
                TabDataMouseClicked(null);
            }else if(ChkAccor.isSelected()==false){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
                scrollPaneDetail.setVisible(false);
                ChkAccor.setVisible(true);
            }
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih data resep...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void tbResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepMouseClicked
        ChkAccorActionPerformed(null);
    }//GEN-LAST:event_tbResepMouseClicked

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(tbResep.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBuktiAntrianIterasiObat.jasper","report","::[ Bukti Antrian Iterasi Obat ]::",
                   "select reg_periksa.no_rawat,resep_obat.tgl_peresepan,resep_obat.no_resep,pasien.no_tlp,"+
                   "dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur as umur,reg_periksa.almt_pj,"+
                   "penjab.png_jawab,permintaan_resep_iterasi_bpjs.status_iter from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                   "inner join resep_obat on reg_periksa.no_rawat=resep_obat.no_rawat "+
                   "inner join permintaan_resep_iterasi_bpjs on permintaan_resep_iterasi_bpjs.no_resep=resep_obat.no_resep "+
                   "where resep_obat.no_resep='"+tbResep.getValueAt(tbResep.getSelectedRow(),7).toString()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih data permintaan resep iterasi...!!!");
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(TabData.getSelectedIndex()==0){
            panggilresep();
        }else if(TabData.getSelectedIndex()==1){
            panggilPhoto();
        }
    }//GEN-LAST:event_TabDataMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ApotekBPJSDaftarPermintaanResepIterasi dialog = new ApotekBPJSDaftarPermintaanResepIterasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTMLPhoto;
    private widget.PanelBiasa PanelAccor;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabData;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel21;
    private widget.Label jLabel7;
    private widget.panelisi panelGlass6;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPaneDetail;
    private widget.Table tbDetailResep;
    private widget.Table tbResep;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select bridging_resep_apotek_bpjs.no_sep,bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.no_kartu,permintaan_resep_iterasi_bpjs.no_resep_awal,bridging_resep_apotek_bpjs.tgl_resep as tgl_resep_awal,"+
                "permintaan_resep_iterasi_bpjs.no_resep,permintaan_resep_iterasi_bpjs.status_iter,bridging_sep.nmdpdjp,resep_obat.tgl_peresepan,if(resep_obat.tgl_penyerahan='0000-00-00','',resep_obat.tgl_penyerahan) as tgl_penyerahan,"+
                "if(resep_obat.jam_penyerahan='00:00:00','',resep_obat.jam_penyerahan) as jam_penyerahan from bridging_resep_apotek_bpjs inner join bridging_sep on bridging_sep.no_sep=bridging_resep_apotek_bpjs.no_sep "+
                "inner join permintaan_resep_iterasi_bpjs on permintaan_resep_iterasi_bpjs.no_resep_awal=bridging_resep_apotek_bpjs.no_resep inner join resep_obat on resep_obat.no_resep=permintaan_resep_iterasi_bpjs.no_resep "+
                "where bridging_resep_apotek_bpjs.tgl_resep between ? and ? "+(TCari.getText().trim().equals("")?"":"and (bridging_resep_apotek_bpjs.no_sep like ? or bridging_sep.no_rawat like ? or bridging_sep.nama_pasien like ? or "+
                "bridging_sep.nomr like ? or permintaan_resep_iterasi_bpjs.no_resep_awal like ? or permintaan_resep_iterasi_bpjs.no_resep like ? or permintaan_resep_iterasi_bpjs.status_iter like ? or bridging_sep.nmdpdjp like ? or "+
                "resep_obat.tgl_peresepan like ?) ")+"order by resep_obat.tgl_peresepan"
            );
            try {
                ps.setString(1,Valid.SetTglJam(DTPCari1.getSelectedItem()+" 00:00:01"));
                ps.setString(2,Valid.SetTglJam(DTPCari2.getSelectedItem()+" 23:59:59"));
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_sep"),rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),rs.getString("no_kartu"),rs.getString("no_resep_awal"),rs.getString("tgl_resep_awal"),
                        rs.getString("no_resep"),rs.getString("status_iter"),rs.getString("nmdpdjp"),rs.getString("tgl_peresepan"),rs.getString("tgl_penyerahan")+" "+rs.getString("jam_penyerahan")
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }  
    
    private void panggilPhoto() {
        if((TabData.isVisible()==true)&&(TabData.getSelectedIndex()==1)){
            try {
                ps=koneksi.prepareStatement("select bukti_penyerahan_resep_obat.photo from bukti_penyerahan_resep_obat where bukti_penyerahan_resep_obat.no_resep=?");
                try {
                    ps.setString(1,tbResep.getValueAt(tbResep.getSelectedRow(),7).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTMLPhoto.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTMLPhoto.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penyerahanresep/"+rs.getString("photo")+"' alt='photo' width='550' height='500'/></center></body></html>");
                        }  
                    }else{
                        LoadHTMLPhoto.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
    
    private void panggilresep(){
        Valid.tabelKosong(tabModeDetail);
        try {
            ps=koneksi.prepareStatement(
                "select resep_dokter_racikan_detail.no_racik,resep_dokter_racikan_detail.kode_brng,databarang.nama_brng,resep_dokter_racikan.aturan_pakai,"+
                "resep_dokter_racikan_detail.jml from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                "inner join resep_dokter_racikan on resep_dokter_racikan.no_resep=resep_dokter_racikan_detail.no_resep where resep_dokter_racikan_detail.no_resep=?"
            );
            try {
                ps.setString(1,tbResep.getValueAt(tbResep.getSelectedRow(),7).toString());
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeDetail.addRow(new Object[]{
                        rs.getString("no_racik"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("aturan_pakai"),rs.getString("jml")
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

        try {
            ps=koneksi.prepareStatement(
                "select resep_dokter.kode_brng,databarang.nama_brng,resep_dokter.aturan_pakai,resep_dokter.jml from resep_dokter "+
                "inner join databarang on databarang.kode_brng=resep_dokter.kode_brng where resep_dokter.no_resep=?"
            );
            try {
                ps.setString(1,tbResep.getValueAt(tbResep.getSelectedRow(),7).toString());
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeDetail.addRow(new Object[]{
                        "N",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("aturan_pakai"),rs.getString("jml")
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
    }

    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable2;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import java.awt.Cursor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dosen
 */
public final class ApotekBPJSPermintaanIterResep extends javax.swing.JDialog {
    private final DefaultTableModel tabModeobat,tabModeObatRacikan,tabModeDetailObatRacikan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();
    private WarnaTable2 warna3=new WarnaTable2();
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private boolean sukses=true;
    private DlgCariDokter dokter;
    private String iterasi,noresepawal,noiterasi="";
    private int i;
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public ApotekBPJSPermintaanIterResep(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        tabModeobat=new DefaultTableModel(null,new Object[]{
                "Jml","Kode Barang","Nama Barang","Aturan Pakai"
            }){
            @Override 
            public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==3)) {
                    a=true;
                }
                return a;
            }
            
            Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
            };
             
            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        
        tbObat.setModel(tabModeobat);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 4; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(85);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(100);
            }              
        }
        warna.kolom=0;
        tbObat.setDefaultRenderer(Object.class,warna);
        
        tabModeObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Nama Racikan","Kode Racik","Metode Racik","Jml.Racik","Aturan Pakai","Keterangan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                return true;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObatRacikan.setModel(tabModeObatRacikan);
        tbObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(160);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(57);
            }else if(i==5){
                column.setPreferredWidth(85);
            }else if(i==6){
                column.setPreferredWidth(200);
            }
        }

        warna2.kolom=4;
        tbObatRacikan.setDefaultRenderer(Object.class,warna2);
        
        tabModeDetailObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Jml","Kode Barang","Nama Barang"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==1)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        tbDetailObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 4; i++) {
            TableColumn column = tbDetailObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(35);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(200);
            }
        }

        warna3.kolom=1;
        tbDetailObatRacikan.setDefaultRenderer(Object.class,warna3);
        
        jam();
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
        panelisi3 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel13 = new widget.Label();
        btnDokter = new widget.Button();
        jLabel11 = new widget.Label();
        NoResep = new widget.TextBox();
        jLabel8 = new widget.Label();
        DTPBeri = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkRM = new widget.CekBox();
        ChkJln = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Obat Iterasi BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelisi3.add(BtnHapus);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        Scroll.setViewportView(tbObat);

        TabRawat.addTab("Umum", Scroll);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(454, 90));

        tbObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        Scroll1.setViewportView(tbObatRacikan);

        jPanel3.add(Scroll1, java.awt.BorderLayout.PAGE_START);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDetailObatRacikan.setAutoCreateRowSorter(true);
        tbDetailObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        Scroll2.setViewportView(tbDetailObatRacikan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Racikan", jPanel3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 107));
        FormInput.setLayout(null);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(75, 12, 120, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(196, 12, 427, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(75, 72, 120, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(196, 72, 230, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 72, 23);

        jLabel13.setText("Peresep :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 72, 72, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('3');
        btnDokter.setToolTipText("Alt+3");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(428, 72, 28, 23);

        jLabel11.setText("No.Resep :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(395, 42, 70, 23);

        NoResep.setHighlighter(null);
        NoResep.setName("NoResep"); // NOI18N
        NoResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepKeyPressed(evt);
            }
        });
        FormInput.add(NoResep);
        NoResep.setBounds(468, 42, 130, 23);

        jLabel8.setText("Tgl.Iterasi :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 42, 72, 23);

        DTPBeri.setForeground(new java.awt.Color(50, 70, 50));
        DTPBeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-03-2026" }));
        DTPBeri.setDisplayFormat("dd-MM-yyyy");
        DTPBeri.setName("DTPBeri"); // NOI18N
        DTPBeri.setOpaque(false);
        DTPBeri.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPBeri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPBeriItemStateChanged(evt);
            }
        });
        DTPBeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPBeriKeyPressed(evt);
            }
        });
        FormInput.add(DTPBeri);
        DTPBeri.setBounds(75, 42, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setSelectedIndex(8);
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(168, 42, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(233, 42, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(298, 42, 62, 23);

        ChkRM.setBorder(null);
        ChkRM.setSelected(true);
        ChkRM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRM.setName("ChkRM"); // NOI18N
        ChkRM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRMItemStateChanged(evt);
            }
        });
        FormInput.add(ChkRM);
        ChkRM.setBounds(600, 42, 23, 23);

        ChkJln.setBorder(null);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(363, 42, 23, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed
    
private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(NoResep.getText().trim().equals("")){
            Valid.textKosong(NoResep,"No.Resep");
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {                 
                ChkJln.setSelected(false);    
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                    NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                    TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                    "ralan","0000-00-00","00:00:00"
                    })==true){
                        simpandata();
                }else{
                    emptTeksobat2();
                    if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                        NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        "ralan","0000-00-00","00:00:00"
                        })==true){
                            simpandata();
                    }else{
                        emptTeksobat2();
                        if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                            NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            "ralan","0000-00-00","00:00:00"
                            })==true){
                                simpandata();
                        }else{
                            emptTeksobat2();
                            if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                                NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                "ralan","0000-00-00","00:00:00"
                                })==true){
                                    simpandata();
                            }else{
                                emptTeksobat();
                                if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                                    NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                    TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                    "ralan","0000-00-00","00:00:00"
                                    })==true){
                                        simpandata();
                                }else{
                                    emptTeksobat2();
                                    if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                                        NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                        TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                        "ralan","0000-00-00","00:00:00"
                                        })==true){
                                            simpandata();
                                    }else{
                                        emptTeksobat2();
                                        if(Sequel.menyimpantf2("resep_obat","?,?,?,?,?,?,?,?,?,?","Nomer Resep",10,new String[]{
                                            NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                            TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                            "ralan","0000-00-00","00:00:00"
                                            })==true){
                                                simpandata();
                                        }else{
                                            sukses=false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                if(sukses==true){
                    if(noiterasi.equals("")){
                        noiterasi="Iterasi Ke 1";
                        if(Sequel.menyimpantf2("permintaan_resep_iterasi_bpjs","?,?,?","Nomer Resep",3,new String[]{noresepawal,NoResep.getText(),"Iterasi Ke 1"})==false){
                            sukses=false;
                            JOptionPane.showMessageDialog(null,"Gagal menyimpan iterasi, kemungkinan sudah terbit sebelumnya..!");
                        }
                    }else{
                        noiterasi="Iterasi Ke 2";
                        if(Sequel.menyimpantf2("permintaan_resep_iterasi_bpjs","?,?,?","Nomer Resep",3,new String[]{noresepawal,NoResep.getText(),"Iterasi Ke 2"})==false){
                            sukses=false;
                            JOptionPane.showMessageDialog(null,"Gagal menyimpan iterasi, kemungkinan sudah terbit sebelumnya..!");
                        }
                    }  
                }
                
                if(sukses==true){
                    Sequel.Commit();
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                
                if(sukses==true){
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
                           "where resep_obat.no_resep='"+NoResep.getText()+"'",param);
                    this.setCursor(Cursor.getDefaultCursor());
                    
                    if(iterasi.equals("2. Iterasi 2x")){
                        if(noiterasi.equals("Iterasi Ke 1")){
                            JOptionPane.showMessageDialog(null,"Resep iterasi ke 1 berhasil dibuat,\nsilahkan lanjutkan untuk menyimpan resep iterasi ke 2...!");
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(DTPBeri.getDate());
                            cal.add(Calendar.DATE, 30);
                            DTPBeri.setDate(cal.getTime());
                            emptTeksobat();
                            sukses=false;
                        }else{
                            sukses=true;
                        }   
                    }
                    
                    if(sukses==true){
                        dispose();
                    }
                }
            } 
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(tbObat.getSelectedRow()!=-1){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Yakin mau dihapus obat "+tbObat.getValueAt(tbObat.getSelectedRow(),2)+"...?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    tabModeobat.removeRow(tbObat.getSelectedRow());
                }
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tbDetailObatRacikan.getSelectedRow()!=-1){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Yakin mau dihapus obat "+tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 3)+"...?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    tabModeDetailObatRacikan.removeRow(tbDetailObatRacikan.getSelectedRow());
                }
            }
        } 
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnKeluar, BtnHapus);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,NoResep,BtnSimpan);
        }
    }//GEN-LAST:event_KdDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){
                        KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }
                    KdDokter.requestFocus();
                    dokter=null;
                }
            });
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
        }
        if (dokter == null) return;
        if (!dokter.isVisible()) {
            dokter.isCek();
            dokter.emptTeks();
        }
        if (dokter.isVisible()) {
            dokter.toFront();
            return;
        }
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        Valid.pindah(evt,KdDokter,BtnSimpan);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void NoResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepKeyPressed
        Valid.pindah(evt,cmbDtk,KdDokter);
    }//GEN-LAST:event_NoResepKeyPressed

    private void DTPBeriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPBeriItemStateChanged
        if(this.isActive()==true){
            emptTeksobat();
        }
    }//GEN-LAST:event_DTPBeriItemStateChanged

    private void DTPBeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPBeriKeyPressed
        try {
            emptTeksobat();
        } catch (Exception e) {
        }
        Valid.pindah(evt,TNoRw,cmbJam);
    }//GEN-LAST:event_DTPBeriKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPBeri,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,NoResep);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkRMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRMItemStateChanged
        if(ChkRM.isSelected()==true){
            NoResep.setEditable(false);
            NoResep.setBackground(new Color(245,250,240));
            try {
                emptTeksobat();
            } catch (Exception e) {
            }
        }else if(ChkRM.isSelected()==false){
            NoResep.setEditable(true);
            NoResep.setBackground(new Color(250,255,245));
            NoResep.setText("");
        }
    }//GEN-LAST:event_ChkRMItemStateChanged

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ApotekBPJSPermintaanIterResep dialog = new ApotekBPJSPermintaanIterResep(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkRM;
    private widget.Tanggal DTPBeri;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDokter;
    private widget.TextBox NmDokter;
    private widget.TextBox NoResep;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Button btnDokter;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel3;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelisi3;
    private widget.Table tbDetailObatRacikan;
    private widget.Table tbObat;
    private widget.Table tbObatRacikan;
    // End of variables declaration//GEN-END:variables
    
    public void setNoRm(String norwt,String norm,String nama,Date tanggal,String kodedokter,String iterasi,String resepawal) {  
        TNoRw.setText(norwt);
        TPasien.setText(norm+" "+nama);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tanggal);
        cal.add(Calendar.DATE, 30);
        DTPBeri.setDate(cal.getTime());
        emptTeksobat();
        this.iterasi=iterasi;
        this.noresepawal=resepawal;
        KdDokter.setText(Sequel.cariIsi("select maping_dokter_dpjpvclaim.kd_dokter from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter_bpjs=?",kodedokter));
        NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
        if(KdDokter.getText().trim().equals("")){
            KdDokter.setText(Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",norwt));
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
        }
        
        try {
            Valid.tabelKosong(tabModeobat);
            myObj = new FileReader("./cache/resepnonracikaniter.iyem");
            root = mapper.readTree(myObj);
            response = root.path("resepnonracikaniter");
            if(response.isArray()){
                for(JsonNode list:response){
                    tabModeobat.addRow(new Object[]{
                        list.path("Jml").asText(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("AturanPakai").asText()
                    });
                }
            }
            myObj.close();
            
            Valid.tabelKosong(tabModeObatRacikan);
            myObj = new FileReader("./cache/resepracikaniter.iyem");
            root = mapper.readTree(myObj);
            response = root.path("resepracikaniter");
            if(response.isArray()){
                for(JsonNode list:response){
                    tabModeObatRacikan.addRow(new Object[]{
                        list.path("No").asText(),list.path("NamaRacikan").asText(),list.path("KodeRacik").asText(),list.path("MetodeRacik").asText(),list.path("JmlRacik").asText(),list.path("AturanPakai").asText(),list.path("Keterangan").asText()
                    });
                }
            }
            myObj.close();
            
            Valid.tabelKosong(tabModeDetailObatRacikan);
            myObj = new FileReader("./cache/resepdetailracikaniter.iyem");
            root = mapper.readTree(myObj);
            response = root.path("resepdetailracikaniter");
            if(response.isArray()){
                for(JsonNode list:response){
                    tabModeDetailObatRacikan.addRow(new Object[]{
                        list.path("No").asText(),list.path("Jml").asText(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText()
                    });
                }
            }
            myObj.close();
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }finally {
            if (myObj != null) try { myObj.close(); } catch (Exception e) {}
            response = null;
            root = null;
        } 
    }
    
    public void emptTeksobat() {
        if(ChkRM.isSelected()==true){
            Valid.autoNomer3(
                "select ifnull(MAX(CONVERT(RIGHT(resep_obat.no_resep,4),signed)),0) from resep_obat where resep_obat.tgl_peresepan='"+Valid.SetTgl(DTPBeri.getSelectedItem()+"")+"'",
                DTPBeri.getSelectedItem().toString().substring(6,10)+DTPBeri.getSelectedItem().toString().substring(3,5)+DTPBeri.getSelectedItem().toString().substring(0,2),4,NoResep
            );        
        } 
    }
    
    private void emptTeksobat2() {
        if(ChkRM.isSelected()==true){
            Valid.autoNomer7(NoResep.getText().substring(NoResep.getText().length()-4),DTPBeri.getSelectedItem().toString().substring(6,10)+DTPBeri.getSelectedItem().toString().substring(3,5)+DTPBeri.getSelectedItem().toString().substring(0,2),4,NoResep);  
        } 
    }
    
    private void simpandata() {
        try {
            for(i=0;i<tbObat.getRowCount();i++){ 
                if(Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0){  
                    if(Sequel.menyimpantf2("resep_dokter","?,?,?,?","data",4,new String[]{
                        NoResep.getText(),tbObat.getValueAt(i,1).toString(),tbObat.getValueAt(i,0).toString(),tbObat.getValueAt(i,3).toString()
                    })==false){
                        sukses=false;
                    }                  
                }
            } 

            for(i=0;i<tbObatRacikan.getRowCount();i++){ 
                if(Valid.SetAngka(tbObatRacikan.getValueAt(i,4).toString())>0){ 
                    if(Sequel.menyimpantf2("resep_dokter_racikan","?,?,?,?,?,?,?","resep obat racikan",7,new String[]{
                       NoResep.getText(),tbObatRacikan.getValueAt(i,0).toString(),tbObatRacikan.getValueAt(i,1).toString(),
                       tbObatRacikan.getValueAt(i,2).toString(),tbObatRacikan.getValueAt(i,4).toString(),
                       tbObatRacikan.getValueAt(i,5).toString(),tbObatRacikan.getValueAt(i,6).toString()
                    })==false){
                        sukses=false;
                    } 
                }
            }
            
            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,1).toString())>0){
                    if(Sequel.menyimpantf2("resep_dokter_racikan_detail","?,?,?,?,?,?,?","resep dokter racikan detail",7,new String[]{
                        NoResep.getText(),tbDetailObatRacikan.getValueAt(i,0).toString(),tbDetailObatRacikan.getValueAt(i,2).toString(),
                        "1","1","",tbDetailObatRacikan.getValueAt(i,1).toString()
                    })==false){
                        sukses=false;
                    } 
                }
            }
        } catch (Exception e) {
            sukses=false;
            System.out.println("Notif : "+e);
        } 
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
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

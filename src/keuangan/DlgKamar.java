/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgKamar.java
 *
 * Created on May 23, 2010, 12:07:21 AM
 */

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
import restore.DlgRestoreKamar;
import simrskhanza.DlgCariBangsal;

/**
 *
 * @author dosen
 */
public final class DlgKamar extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String asalform="",ubah_status_kamar=Sequel.cariIsi("select ubah_status_kamar from set_jam_minimal");

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public DlgKamar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);
        tabMode=new DefaultTableModel(null,new String[]{
                "P","Nomer Bed","Kode Kamar","Nama Kamar","Kelas","Tarif Kamar","Status Kamar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                 column.setPreferredWidth(90);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)15).getKata(TKd));
        TTarif.setDocument(new batasInput((byte)16).getOnlyAngka(TTarif));
        kd_bangsal.setDocument(new batasInput((byte)5).getKata(kd_bangsal));
        kdbangsalcari.setDocument(new batasInput((byte)5).getKata(kdbangsalcari));
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
        panelCari.setVisible(false);
        posisi();        
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgKamar")){
                    load();
                    if(bangsal.getTable().getSelectedRow()!= -1){                   
                        if(pilihan==1){
                           kd_bangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                           TBangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                           kd_bangsal.requestFocus();
                        }else if(pilihan==2){
                            kdbangsalcari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                            BangsalCari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                            kdbangsalcari.requestFocus();
                        }    
                    }                 
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
    
    public DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private int pilihan=0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnRestore = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        PanelCariUtama = new javax.swing.JPanel();
        panelGlass5 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass6 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelCariKategori = new javax.swing.JPanel();
        panelCari = new widget.panelisi();
        jLabel13 = new widget.Label();
        kdbangsalcari = new widget.TextBox();
        BangsalCari = new widget.TextBox();
        btnKamarCari = new widget.Button();
        jLabel11 = new widget.Label();
        CmbCrIsi = new widget.ComboBox();
        ChkCari = new widget.CekBox();
        panelGlass4 = new widget.panelisi();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TKd = new widget.TextBox();
        TBangsal = new widget.TextBox();
        jLabel8 = new widget.Label();
        TTarif = new widget.TextBox();
        jLabel9 = new widget.Label();
        CmbStatus = new widget.ComboBox();
        btnKamar = new widget.Button();
        kd_bangsal = new widget.TextBox();
        jLabel5 = new widget.Label();
        Kelas = new widget.ComboBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRestore.setBackground(new java.awt.Color(255, 255, 254));
        MnRestore.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRestore.setForeground(new java.awt.Color(70, 70, 70));
        MnRestore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRestore.setText("Data Sampah");
        MnRestore.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRestore.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRestore.setName("MnRestore"); // NOI18N
        MnRestore.setPreferredSize(new java.awt.Dimension(200, 28));
        MnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRestoreActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRestore);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Nomor Kamar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamar.setComponentPopupMenu(jPopupMenu1);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbKamarKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelCariUtama.setBackground(new java.awt.Color(255, 255, 255));
        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 162));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnBatal);

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
        panelGlass5.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnEdit);

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
        panelGlass5.add(BtnPrint);

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
        panelGlass5.add(BtnKeluar);

        PanelCariUtama.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass6.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TCariKeyTyped(evt);
            }
        });
        panelGlass6.add(TCari);

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
        panelGlass6.add(BtnCari);

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
        panelGlass6.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass6.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass6.add(LCount);

        PanelCariUtama.add(panelGlass6, java.awt.BorderLayout.CENTER);

        panelCariKategori.setName("panelCariKategori"); // NOI18N
        panelCariKategori.setOpaque(false);
        panelCariKategori.setPreferredSize(new java.awt.Dimension(100, 62));
        panelCariKategori.setLayout(new java.awt.BorderLayout(1, 1));

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 44));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        jLabel13.setText("Kamar :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelCari.add(jLabel13);

        kdbangsalcari.setHighlighter(null);
        kdbangsalcari.setName("kdbangsalcari"); // NOI18N
        kdbangsalcari.setPreferredSize(new java.awt.Dimension(90, 23));
        kdbangsalcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbangsalcariKeyPressed(evt);
            }
        });
        panelCari.add(kdbangsalcari);

        BangsalCari.setEditable(false);
        BangsalCari.setName("BangsalCari"); // NOI18N
        BangsalCari.setPreferredSize(new java.awt.Dimension(190, 23));
        panelCari.add(BangsalCari);

        btnKamarCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKamarCari.setMnemonic('2');
        btnKamarCari.setToolTipText("Alt+2");
        btnKamarCari.setName("btnKamarCari"); // NOI18N
        btnKamarCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKamarCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarCariActionPerformed(evt);
            }
        });
        btnKamarCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKamarCariKeyPressed(evt);
            }
        });
        panelCari.add(btnKamarCari);

        jLabel11.setText("Stts.Kamar :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(85, 23));
        panelCari.add(jLabel11);

        CmbCrIsi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "ISI", "KOSONG", "DIBERSIHKAN", "DIBOOKING" }));
        CmbCrIsi.setName("CmbCrIsi"); // NOI18N
        CmbCrIsi.setPreferredSize(new java.awt.Dimension(125, 23));
        CmbCrIsi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbCrIsiItemStateChanged(evt);
            }
        });
        CmbCrIsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCrIsiKeyPressed(evt);
            }
        });
        panelCari.add(CmbCrIsi);

        panelCariKategori.add(panelCari, java.awt.BorderLayout.CENTER);

        ChkCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkCari.setMnemonic('I');
        ChkCari.setText("  .: Pencarian Data");
        ChkCari.setToolTipText("Alt+I");
        ChkCari.setBorderPainted(true);
        ChkCari.setBorderPaintedFlat(true);
        ChkCari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkCari.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkCari.setIconTextGap(2);
        ChkCari.setName("ChkCari"); // NOI18N
        ChkCari.setPreferredSize(new java.awt.Dimension(632, 22));
        ChkCari.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkCari.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkCari.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkCariActionPerformed(evt);
            }
        });
        panelCariKategori.add(ChkCari, java.awt.BorderLayout.PAGE_START);

        PanelCariUtama.add(panelCariKategori, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        panelGlass4.setName("panelGlass4"); // NOI18N
        panelGlass4.setPreferredSize(new java.awt.Dimension(0, 77));
        panelGlass4.setLayout(null);

        jLabel3.setText("Nomer Bed :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass4.add(jLabel3);
        jLabel3.setBounds(0, 12, 82, 23);

        jLabel4.setText("Kamar :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass4.add(jLabel4);
        jLabel4.setBounds(0, 42, 82, 23);

        TKd.setHighlighter(null);
        TKd.setName("TKd"); // NOI18N
        TKd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdKeyPressed(evt);
            }
        });
        panelGlass4.add(TKd);
        TKd.setBounds(86, 12, 100, 23);

        TBangsal.setEditable(false);
        TBangsal.setName("TBangsal"); // NOI18N
        TBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBangsalKeyPressed(evt);
            }
        });
        panelGlass4.add(TBangsal);
        TBangsal.setBounds(158, 42, 230, 23);

        jLabel8.setText("Tarif/Hari : Rp.");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass4.add(jLabel8);
        jLabel8.setBounds(184, 12, 110, 23);

        TTarif.setHighlighter(null);
        TTarif.setName("TTarif"); // NOI18N
        TTarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTarifKeyPressed(evt);
            }
        });
        panelGlass4.add(TTarif);
        TTarif.setBounds(299, 12, 120, 23);

        jLabel9.setText("Stts.Kamar :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass4.add(jLabel9);
        jLabel9.setBounds(420, 12, 70, 23);

        CmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ISI", "KOSONG", "DIBERSIHKAN", "DIBOOKING" }));
        CmbStatus.setName("CmbStatus"); // NOI18N
        CmbStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbStatusKeyPressed(evt);
            }
        });
        panelGlass4.add(CmbStatus);
        CmbStatus.setBounds(494, 12, 125, 23);

        btnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKamar.setMnemonic('1');
        btnKamar.setToolTipText("Alt+1");
        btnKamar.setName("btnKamar"); // NOI18N
        btnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarActionPerformed(evt);
            }
        });
        btnKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKamarKeyPressed(evt);
            }
        });
        panelGlass4.add(btnKamar);
        btnKamar.setBounds(391, 42, 28, 23);

        kd_bangsal.setHighlighter(null);
        kd_bangsal.setName("kd_bangsal"); // NOI18N
        kd_bangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kd_bangsalKeyPressed(evt);
            }
        });
        panelGlass4.add(kd_bangsal);
        kd_bangsal.setBounds(86, 42, 70, 23);

        jLabel5.setText("Kelas :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass4.add(jLabel5);
        jLabel5.setBounds(420, 42, 70, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Kelas Utama", "Kelas VIP", "Kelas VVIP" }));
        Kelas.setName("Kelas"); // NOI18N
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        panelGlass4.add(Kelas);
        Kelas.setBounds(494, 42, 125, 23);

        internalFrame1.add(panelGlass4, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TKdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
        Valid.pindah(evt,TTarif,kd_bangsal,TCari);
}//GEN-LAST:event_TKdKeyPressed

    private void TBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBangsalKeyPressed
        Valid.pindah(evt,TKd,BtnSimpan);
}//GEN-LAST:event_TBangsalKeyPressed

    private void TTarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTarifKeyPressed
        Valid.pindah(evt,kd_bangsal,CmbStatus);
}//GEN-LAST:event_TTarifKeyPressed

    private void CmbStatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbStatusKeyPressed
        Valid.pindah(evt,TTarif,Kelas);
}//GEN-LAST:event_CmbStatusKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TKd.getText().trim().equals("")){
            Valid.textKosong(TKd,"Kode Kamar");
        }else if(TBangsal.getText().trim().equals("")){
            Valid.textKosong(TBangsal,"Bangsal");
        }else if(TTarif.getText().trim().equals("")){
            Valid.textKosong(TTarif,"Tarif");
        }else{
            Sequel.menyimpan("kamar","?,?,?,?,?,?","Nomer Bad/Kamar",6,new String[]{
                TKd.getText(),kd_bangsal.getText(),TTarif.getText(),CmbStatus.getSelectedItem().toString(),Kelas.getSelectedItem().toString(),"1"
            });
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Kelas,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for(i=0;i<tbKamar.getRowCount();i++){ 
            if(tbKamar.getValueAt(i,0).toString().equals("true")){
                Sequel.mengedit("kamar","kd_kamar='"+tbKamar.getValueAt(i,1).toString()+"'","statusdata='0'");
            }
        } 
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TKd.getText().trim().equals("")){
            Valid.textKosong(TKd,"No Bed");
        }else if(TBangsal.getText().trim().equals("")){
            Valid.textKosong(TBangsal,"Bangsal");
        }else if(TTarif.getText().trim().equals("")){
            Valid.textKosong(TTarif,"Tarif");
        }else{
            if(tbKamar.getSelectedRow()>-1){
                Sequel.mengedit("kamar","kd_kamar=?","kd_bangsal=?,trf_kamar=?,status=?,kelas=?,kd_kamar=?",6,new String[]{
                    kd_bangsal.getText(),TTarif.getText(),CmbStatus.getSelectedItem().toString(),Kelas.getSelectedItem().toString(),
                    TKd.getText(),tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString()
                });
                tampil();
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            if((TCari.getText().trim().equals(""))&&(panelCari.isVisible()==false)){
                Valid.MyReportqry("rptKamar.jasper","report","::[ Data Nomor Kamar ]::",
                        "select kd_kamar,kamar.kd_bangsal,bangsal.nm_bangsal, "+
                               "kamar.kelas,trf_kamar,kamar.status "+
                               "from bangsal,kamar "+
                               "where kamar.statusdata='1' and kamar.kd_bangsal=bangsal.kd_bangsal "+
                               "order by kd_kamar",param);
            }else if((! TCari.getText().trim().equals(""))&&(panelCari.isVisible()==false)){
                String sql="kamar.statusdata='1' and kamar.kd_bangsal=bangsal.kd_bangsal ";
                Valid.MyReportqry("rptKamar.jasper","report","::[ Data Nomor Kamar ]::","select kd_kamar,kamar.kd_bangsal,bangsal.nm_bangsal, "+
                               "kamar.kelas,trf_kamar,kamar.status "+
                               "from bangsal,kamar where  "+
                               sql+"and kd_kamar like '%"+TCari.getText().trim()+"%' or "+
                               sql+"and kamar.kd_bangsal like '%"+TCari.getText().trim()+"%' or "+
                               sql+"and bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' or "+
                               sql+"and kamar.kelas like '%"+TCari.getText().trim()+"%' or "+
                               sql+"and trf_kamar like '%"+TCari.getText().trim()+"%' or "+
                               sql+"and kamar.status like '%"+TCari.getText().trim()+"%' "+
                               "order by kd_kamar ",param);
            }else if((TCari.getText().trim().equals(""))&&(panelCari.isVisible()==true)&&(CmbCrIsi.getItemCount()>0)){
                String key="and kamar.statusdata='1'";
                if(BangsalCari.getText().equals("")&&CmbCrIsi.getSelectedItem().toString().equals(" ")){
                     key="and kamar.statusdata='1'";
                }else if((! BangsalCari.getText().equals(""))&&CmbCrIsi.getSelectedItem().toString().equals(" ")){
                     key="and kamar.statusdata='1' and bangsal.nm_bangsal like '%"+BangsalCari.getText().trim()+"%' ";
                }else if((! BangsalCari.getText().equals(""))&&(! CmbCrIsi.getSelectedItem().toString().equals(" "))){
                     key="and kamar.statusdata='1' and bangsal.nm_bangsal like '%"+BangsalCari.getText().trim()+"%' "+
                         "and status like '%"+CmbCrIsi.getSelectedItem().toString().trim()+"%' ";
                }else if((BangsalCari.getText().equals(""))&&(! CmbCrIsi.getSelectedItem().toString().equals(" "))){
                     key="and kamar.statusdata='1' and kamar.status like '%"+CmbCrIsi.getSelectedItem().toString().trim()+"%' ";
                }     
                Valid.MyReportqry("rptKamar.jasper","report","::[ Data Nomor Kamar ]::","select kd_kamar,kamar.kd_bangsal,bangsal.nm_bangsal, "+
                               "kamar.kelas,trf_kamar,kamar.status "+
                               "from bangsal,kamar "+
                               "where kamar.kd_bangsal=bangsal.kd_bangsal "+key+
                               " order by kd_kamar",param);
            }
        }

        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
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
        CmbCrIsi.setSelectedItem(" ");
        BangsalCari.setText("");
        kdbangsalcari.setText("");
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, kd_bangsal);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void CmbCrIsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCrIsiKeyPressed
        Valid.pindah(evt,kdbangsalcari, TCari);
}//GEN-LAST:event_CmbCrIsiKeyPressed

    private void ChkCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkCariActionPerformed
        if(ChkCari.isSelected()==true){
            panelCari.setVisible(true);
            TCari.setText("");
            tampil();
            posisi();
        }else if(ChkCari.isSelected()==false){
            panelCari.setVisible(false);
            BtnCariActionPerformed(null);
            posisi();
        }
}//GEN-LAST:event_ChkCariActionPerformed

    private void TCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyTyped
        if(ChkCari.isSelected()==true){
            ChkCari.setSelected(false);
            panelCari.setVisible(false);
            posisi();
        }
    }//GEN-LAST:event_TCariKeyTyped

    private void btnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarActionPerformed
        akses.setform("DlgKamar");
        pilihan=1;
        bangsal.isCek();
        bangsal.emptTeks();        
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
}//GEN-LAST:event_btnKamarActionPerformed

    private void btnKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKamarKeyPressed
        Valid.pindah(evt,kd_bangsal,BtnSimpan);
}//GEN-LAST:event_btnKamarKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
        }
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                akses.setform(asalform);
            }
        }
}//GEN-LAST:event_tbKamarKeyPressed

private void kd_bangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kd_bangsalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", TBangsal,kd_bangsal.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKamarActionPerformed(null);
        }else{
            Valid.pindah(evt, TKd,TTarif);
        }
}//GEN-LAST:event_kd_bangsalKeyPressed

private void kdbangsalcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbangsalcariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",BangsalCari,kdbangsalcari.getText());
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKamarCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,CmbCrIsi);
        }
}//GEN-LAST:event_kdbangsalcariKeyPressed

private void btnKamarCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarCariActionPerformed
        akses.setform("DlgKamar");
        pilihan=2;        
        bangsal.isCek();
        bangsal.emptTeks();        
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
}//GEN-LAST:event_btnKamarCariActionPerformed

private void btnKamarCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKamarCariKeyPressed
   Valid.pindah(evt,kdbangsalcari,CmbCrIsi);
}//GEN-LAST:event_btnKamarCariKeyPressed

private void CmbCrIsiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbCrIsiItemStateChanged
    tampil();
}//GEN-LAST:event_CmbCrIsiItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KelasKeyPressed

    private void MnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRestoreActionPerformed
        DlgRestoreKamar restore=new DlgRestoreKamar(null,true);
        restore.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        restore.setLocationRelativeTo(internalFrame1);
        restore.setVisible(true);
    }//GEN-LAST:event_MnRestoreActionPerformed

    private void tbKamarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbKamarKeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKamar dialog = new DlgKamar(new javax.swing.JFrame(), true);
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
    private widget.TextBox BangsalCari;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkCari;
    private widget.ComboBox CmbCrIsi;
    private widget.ComboBox CmbStatus;
    private widget.ComboBox Kelas;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnRestore;
    private javax.swing.JPanel PanelCariUtama;
    private widget.ScrollPane Scroll;
    private widget.TextBox TBangsal;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.TextBox TTarif;
    private widget.Button btnKamar;
    private widget.Button btnKamarCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kd_bangsal;
    private widget.TextBox kdbangsalcari;
    private widget.panelisi panelCari;
    private javax.swing.JPanel panelCariKategori;
    private widget.panelisi panelGlass4;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass6;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{     
            ps=koneksi.prepareStatement("select kamar.kd_kamar,kamar.kd_bangsal,bangsal.nm_bangsal, "+
                       "kamar.kelas,kamar.trf_kamar,kamar.status from bangsal inner join kamar "+
                       "on kamar.kd_bangsal=bangsal.kd_bangsal where "+
                       "kamar.statusdata='1' and bangsal.nm_bangsal like ? and kamar.status like ? and kamar.kd_kamar like ? or "+
                       "kamar.statusdata='1' and bangsal.nm_bangsal like ? and kamar.status like ? and kamar.kd_bangsal like ? or "+
                       "kamar.statusdata='1' and bangsal.nm_bangsal like ? and kamar.status like ? and bangsal.nm_bangsal like ? or "+
                       "kamar.statusdata='1' and bangsal.nm_bangsal like ? and kamar.status like ? and kamar.kelas like ? or "+
                       "kamar.statusdata='1' and bangsal.nm_bangsal like ? and kamar.status like ? and kamar.trf_kamar like ? or "+
                       "kamar.statusdata='1' and bangsal.nm_bangsal like ? and kamar.status like ? and kamar.status like ? "+
                       "order by bangsal.nm_bangsal");
            try {                
                ps.setString(1,"%"+BangsalCari.getText().trim()+"%");
                ps.setString(2,"%"+CmbCrIsi.getSelectedItem().toString().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+BangsalCari.getText().trim()+"%");
                ps.setString(5,"%"+CmbCrIsi.getSelectedItem().toString().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+BangsalCari.getText().trim()+"%");
                ps.setString(8,"%"+CmbCrIsi.getSelectedItem().toString().trim()+"%");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,"%"+BangsalCari.getText().trim()+"%");
                ps.setString(11,"%"+CmbCrIsi.getSelectedItem().toString().trim()+"%");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,"%"+BangsalCari.getText().trim()+"%");
                ps.setString(14,"%"+CmbCrIsi.getSelectedItem().toString().trim()+"%");
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,"%"+BangsalCari.getText().trim()+"%");
                ps.setString(17,"%"+CmbCrIsi.getSelectedItem().toString().trim()+"%");
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getDouble(5),
                                   rs.getString(6)});
                }
            } catch (Exception e) {
                System.out.println("Notif Kamar : "+e);
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

    private void posisi() {
        if(panelCari.isVisible()==false){
            panelCariKategori.setPreferredSize(new Dimension(WIDTH,22));
            PanelCariUtama.setPreferredSize(new Dimension(WIDTH,122));
        }else if(panelCari.isVisible()==true){
            panelCariKategori.setPreferredSize(new Dimension(WIDTH,61));
            PanelCariUtama.setPreferredSize(new Dimension(WIDTH,161));
        }
    }

    public void emptTeks() {
        TKd.setText("");
        TTarif.setText("");
        kd_bangsal.setText("");
        TBangsal.setText("");
        CmbStatus.setSelectedIndex(0);
        Valid.autoNomer(" kamar ","K",7,TKd);
        TKd.requestFocus();
    }

    private void getData() {
        int row=tbKamar.getSelectedRow();
        if(row!= -1){
            TKd.setText(tbKamar.getValueAt(row,1).toString());
            kd_bangsal.setText(tbKamar.getValueAt(row,2).toString());
            TBangsal.setText(tbKamar.getValueAt(row,3).toString());
            Kelas.setSelectedItem(tbKamar.getValueAt(row,4).toString());
            TTarif.setText(Valid.SetAngka2(Double.parseDouble(tbKamar.getValueAt(row,5).toString())));
            CmbStatus.setSelectedItem(tbKamar.getValueAt(row,6).toString());
        }

    }

    public void load() {
        kdbangsalcari.setText("");
        BangsalCari.setText("");
        CmbCrIsi.setSelectedItem(" ");
    }    

    public JTable getTable(){
        return tbKamar;
    }
       
     public void isCek(){
        BtnSimpan.setEnabled(akses.getkamar());
        BtnHapus.setEnabled(akses.getkamar());
        BtnPrint.setEnabled(akses.getkamar());
        TKd.setEditable(akses.getkamar());
        TTarif.setEditable(akses.getkamar());
        kd_bangsal.setEditable(akses.getkamar());
        Kelas.setEnabled(akses.getkamar());        
        asalform=akses.getform();
        if(akses.getkode().equals("Admin Utama")){
            MnRestore.setEnabled(true);
            BtnEdit.setEnabled(true);
        }else{
            if(ubah_status_kamar.equals("No")){
                BtnEdit.setEnabled(false);
            }
            MnRestore.setEnabled(false);
        }
     }
     
    

 
}

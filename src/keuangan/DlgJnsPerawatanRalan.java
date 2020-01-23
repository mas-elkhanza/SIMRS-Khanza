/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatanRalan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package keuangan;
import restore.DlgRestoreTarifRalan;
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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgKtgPerawatan;
import simrskhanza.DlgPenanggungJawab;

/**
 *
 * @author dosen
 */
public final class DlgJnsPerawatanRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;

    /** Creates new form DlgJnsPerawatanRalan
     * @param parent
     * @param modal */
    public DlgJnsPerawatanRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"P","Kode Tindakan",
                      "Nama Tnd/Prw/Tagihan",
                      "Kategori",
                      "J.S.Rmh Sakit",
                      "BHP/Paket Obat",
                      "J.Medis Dr",
                      "J.Medis Pr",
                      "KSO",
                      "Menejemen",
                      "Ttl Biaya Dr",
                      "Ttl Biaya Pr",
                      "Ttl Biaya Dr & Pr",
                      "Jenis Bayar",
                      "Unit/Poli"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class,java.lang.Double.class,java.lang.Double.class, 
                java.lang.Double.class, java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else{
                column.setPreferredWidth(85);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)15).getKata(TKd));
        TNm.setDocument(new batasInput((byte)80).getKata(TNm));
        KdKtg.setDocument(new batasInput((byte)5).getKata(KdKtg));
        BagianRs.setDocument(new batasInput((int)15).getOnlyAngka(BagianRs));
        BhpMedis.setDocument(new batasInput((int)15).getOnlyAngka(BhpMedis));
        TTndDr.setDocument(new batasInput((int)15).getOnlyAngka(TTndDr));
        TTndPr.setDocument(new batasInput((int)15).getOnlyAngka(TTndPr));
        TJmlDr.setDocument(new batasInput((int)20).getOnlyAngka(TJmlDr));
        TJmlPr.setDocument(new batasInput((int)20).getOnlyAngka(TJmlPr));
        TJmlDrPr.setDocument(new batasInput((int)20).getOnlyAngka(TJmlPr));
        KSO.setDocument(new batasInput((int)20).getOnlyAngka(KSO));
        Menejemen.setDocument(new batasInput((int)20).getOnlyAngka(Menejemen));
        kdpnj.setDocument(new batasInput((int)3).getKata(kdpnj));
        kdpoli.setDocument(new batasInput((int)5).getKata(kdpoli));
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
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){
                    kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                }  
                kdpoli.requestFocus();
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
        
        ktg.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ktg.getTable().getSelectedRow()!= -1){                   
                    KdKtg.setText(ktg.getTable().getValueAt(ktg.getTable().getSelectedRow(),1).toString());
                    NmKtg.setText(ktg.getTable().getValueAt(ktg.getTable().getSelectedRow(),2).toString());
                }     
                KdKtg.requestFocus();
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
        
        ktg.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    ktg.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
     
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                } 
                kdpnj.requestFocus();
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
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
       
        ChkInput.setSelected(false);
        isForm(); 
    
    }
    
    public DlgKtgPerawatan ktg=new DlgKtgPerawatan(null,false);
    public DlgCariPoli poli=new DlgCariPoli(null,false);
    public DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);

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
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TKd = new widget.TextBox();
        jLabel8 = new widget.Label();
        TNm = new widget.TextBox();
        jLabel4 = new widget.Label();
        KdKtg = new widget.TextBox();
        BagianRs = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel10 = new widget.Label();
        TJmlDr = new widget.TextBox();
        jLabel11 = new widget.Label();
        TJmlPr = new widget.TextBox();
        NmKtg = new widget.TextBox();
        btnKategori = new widget.Button();
        TTndDr = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        TTndPr = new widget.TextBox();
        kdpoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        BtnPoli = new widget.Button();
        jLabel19 = new widget.Label();
        jLabel18 = new widget.Label();
        kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        BtnPenjab = new widget.Button();
        jLabel14 = new widget.Label();
        TJmlDrPr = new widget.TextBox();
        BhpMedis = new widget.TextBox();
        jLabel15 = new widget.Label();
        KSO = new widget.TextBox();
        jLabel16 = new widget.Label();
        Menejemen = new widget.TextBox();
        jLabel17 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRestore.setBackground(new java.awt.Color(255, 255, 254));
        MnRestore.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRestore.setForeground(new java.awt.Color(50, 50, 50));
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
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Tarif Tindakan/Perawatan/Tagihan Rawat Jalan]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silakan klik untuk memilih data yang hendak diedit, untuk menghapus data silakan centang kemudian klik hapus");
        tbJnsPerawatan.setComponentPopupMenu(jPopupMenu1);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
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
        panelGlass8.add(BtnBatal);

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
        panelGlass8.add(BtnHapus);

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
        panelGlass8.add(BtnEdit);

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
        panelGlass8.add(BtnPrint);

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
        panelGlass8.add(BtnAll);

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
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Keyword :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setToolTipText("Silakan masukkan kata kunci pencarian kemudian tekan ENTER atau tekan tombol centang. Pencarian bisa berdasar kode, nama perawatan, kategori, poli dan jenis bayar");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 248));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 197));
        FormInput.setLayout(null);

        jLabel3.setText("Kode Tnd/Prw :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(4, 12, 92, 23);

        TKd.setToolTipText("Wajib diisi....! Cateeet yaaa ...! harus unix dan ga boleh sama dengan yang lain. Tekan ENTER buat lanjut ke field berikutnya, tekan PAGE UP buat ke field sebelumnya");
        TKd.setHighlighter(null);
        TKd.setName("TKd"); // NOI18N
        TKd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdKeyPressed(evt);
            }
        });
        FormInput.add(TKd);
        TKd.setBounds(100, 12, 130, 23);

        jLabel8.setText("Nama Tnd/Prw/Tagihan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(280, 12, 160, 23);

        TNm.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya");
        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        FormInput.add(TNm);
        TNm.setBounds(443, 12, 283, 23);

        jLabel4.setText("Kategori :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(4, 42, 92, 23);

        KdKtg.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, Tekan UP untuk menampilkan data Kategori Perawatan");
        KdKtg.setHighlighter(null);
        KdKtg.setName("KdKtg"); // NOI18N
        KdKtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKtgKeyPressed(evt);
            }
        });
        FormInput.add(KdKtg);
        KdKtg.setBounds(100, 42, 60, 23);

        BagianRs.setText("0");
        BagianRs.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, hanya bisa angka");
        BagianRs.setHighlighter(null);
        BagianRs.setName("BagianRs"); // NOI18N
        BagianRs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BagianRsKeyPressed(evt);
            }
        });
        FormInput.add(BagianRs);
        BagianRs.setBounds(525, 72, 200, 23);

        jLabel9.setText("J.S. Rumah Sakit : Rp.");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(412, 72, 110, 23);

        jLabel10.setText("Total Biaya Dokter : Rp.");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(4, 192, 155, 23);

        TJmlDr.setText("0");
        TJmlDr.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, hanya bisa angka");
        TJmlDr.setHighlighter(null);
        TJmlDr.setName("TJmlDr"); // NOI18N
        TJmlDr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TJmlDrKeyPressed(evt);
            }
        });
        FormInput.add(TJmlDr);
        TJmlDr.setBounds(162, 192, 200, 23);

        jLabel11.setText("Total Biaya Pr : Rp.");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(392, 162, 130, 23);

        TJmlPr.setText("0");
        TJmlPr.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, hanya bisa angka");
        TJmlPr.setHighlighter(null);
        TJmlPr.setName("TJmlPr"); // NOI18N
        TJmlPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TJmlPrKeyPressed(evt);
            }
        });
        FormInput.add(TJmlPr);
        TJmlPr.setBounds(525, 162, 200, 23);

        NmKtg.setEditable(false);
        NmKtg.setHighlighter(null);
        NmKtg.setName("NmKtg"); // NOI18N
        NmKtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKtgKeyPressed(evt);
            }
        });
        FormInput.add(NmKtg);
        NmKtg.setBounds(162, 42, 200, 23);

        btnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKategori.setMnemonic('1');
        btnKategori.setToolTipText("Alt+1");
        btnKategori.setName("btnKategori"); // NOI18N
        btnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriActionPerformed(evt);
            }
        });
        btnKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKategoriKeyPressed(evt);
            }
        });
        FormInput.add(btnKategori);
        btnKategori.setBounds(364, 42, 28, 23);

        TTndDr.setText("0");
        TTndDr.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, hanya bisa angka");
        TTndDr.setHighlighter(null);
        TTndDr.setName("TTndDr"); // NOI18N
        TTndDr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TTndDrActionPerformed(evt);
            }
        });
        TTndDr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTndDrKeyPressed(evt);
            }
        });
        FormInput.add(TTndDr);
        TTndDr.setBounds(162, 162, 200, 23);

        jLabel12.setText("Jasa Medis Dokter : Rp.");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(4, 162, 155, 23);

        jLabel13.setText("Jasa Medis Pr : Rp.");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(392, 132, 130, 23);

        TTndPr.setText("0");
        TTndPr.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, hanya bisa angka");
        TTndPr.setHighlighter(null);
        TTndPr.setName("TTndPr"); // NOI18N
        TTndPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTndPrKeyPressed(evt);
            }
        });
        FormInput.add(TTndPr);
        TTndPr.setBounds(525, 132, 200, 23);

        kdpoli.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, Tekan UP untuk menampilkan data Poliklinik");
        kdpoli.setHighlighter(null);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        FormInput.add(kdpoli);
        kdpoli.setBounds(100, 72, 60, 23);

        NmPoli.setEditable(false);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(162, 72, 200, 23);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli.setMnemonic('3');
        BtnPoli.setToolTipText("ALt+3");
        BtnPoli.setName("BtnPoli"); // NOI18N
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliActionPerformed(evt);
            }
        });
        FormInput.add(BtnPoli);
        BtnPoli.setBounds(364, 72, 28, 23);

        jLabel19.setText("Unit/Poli :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(4, 72, 92, 23);

        jLabel18.setText("Bayar :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(390, 42, 50, 23);

        kdpnj.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, Tekan UP untuk menampilkan data Jenis Pembayaran");
        kdpnj.setHighlighter(null);
        kdpnj.setName("kdpnj"); // NOI18N
        kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpnjKeyPressed(evt);
            }
        });
        FormInput.add(kdpnj);
        kdpnj.setBounds(443, 42, 60, 23);

        nmpnj.setEditable(false);
        nmpnj.setName("nmpnj"); // NOI18N
        FormInput.add(nmpnj);
        nmpnj.setBounds(505, 42, 192, 23);

        BtnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab.setMnemonic('2');
        BtnPenjab.setToolTipText("ALt+2");
        BtnPenjab.setName("BtnPenjab"); // NOI18N
        BtnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjabActionPerformed(evt);
            }
        });
        FormInput.add(BtnPenjab);
        BtnPenjab.setBounds(698, 42, 28, 23);

        jLabel14.setText("Total Biaya Dokter & Pr : Rp.");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(372, 192, 150, 23);

        TJmlDrPr.setText("0");
        TJmlDrPr.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, hanya bisa angka");
        TJmlDrPr.setHighlighter(null);
        TJmlDrPr.setName("TJmlDrPr"); // NOI18N
        TJmlDrPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TJmlDrPrKeyPressed(evt);
            }
        });
        FormInput.add(TJmlDrPr);
        TJmlDrPr.setBounds(525, 192, 200, 23);

        BhpMedis.setText("0");
        BhpMedis.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, hanya bisa angka");
        BhpMedis.setHighlighter(null);
        BhpMedis.setName("BhpMedis"); // NOI18N
        BhpMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BhpMedisKeyPressed(evt);
            }
        });
        FormInput.add(BhpMedis);
        BhpMedis.setBounds(162, 102, 200, 23);

        jLabel15.setText("BHP Medis/Paket Obat : Rp.");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(4, 102, 155, 23);

        KSO.setText("0");
        KSO.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, hanya bisa angka");
        KSO.setHighlighter(null);
        KSO.setName("KSO"); // NOI18N
        KSO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KSOKeyPressed(evt);
            }
        });
        FormInput.add(KSO);
        KSO.setBounds(162, 132, 200, 23);

        jLabel16.setText("K.S.O. : Rp.");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(4, 132, 155, 23);

        Menejemen.setText("0");
        Menejemen.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, hanya bisa angka");
        Menejemen.setHighlighter(null);
        Menejemen.setName("Menejemen"); // NOI18N
        Menejemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenejemenKeyPressed(evt);
            }
        });
        FormInput.add(Menejemen);
        Menejemen.setBounds(525, 102, 200, 23);

        jLabel17.setText("Menejemen : Rp.");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(412, 102, 110, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TKdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
        Valid.pindah(evt,TCari,TNm);
}//GEN-LAST:event_TKdKeyPressed

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        Valid.pindah(evt,TKd,KdKtg);
}//GEN-LAST:event_TNmKeyPressed

    private void KdKtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKtgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isktg();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKategoriActionPerformed(null);
        }else{
            Valid.pindah(evt,TNm,kdpnj);
        }
}//GEN-LAST:event_KdKtgKeyPressed

    private void BagianRsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BagianRsKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            BhpMedis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            kdpoli.requestFocus();
        }
}//GEN-LAST:event_BagianRsKeyPressed

    private void TJmlDrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TJmlDrKeyPressed
        Valid.pindah(evt,BagianRs,TJmlPr);
}//GEN-LAST:event_TJmlDrKeyPressed

    private void TJmlPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TJmlPrKeyPressed
        Valid.pindah(evt,TJmlDr,TJmlDrPr);
}//GEN-LAST:event_TJmlPrKeyPressed

    private void NmKtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKtgKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_NmKtgKeyPressed

    private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
        ktg.emptTeks();
        ktg.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ktg.setLocationRelativeTo(internalFrame1);
        ktg.setVisible(true);
}//GEN-LAST:event_btnKategoriActionPerformed

    private void btnKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKategoriKeyPressed
        Valid.pindah(evt,KdKtg,BagianRs);
}//GEN-LAST:event_btnKategoriKeyPressed

    private void TTndDrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTndDrKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            TTndPr.requestFocus();
        }
}//GEN-LAST:event_TTndDrKeyPressed

    private void TTndPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTndPrKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TTndDr.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            KSO.requestFocus();
        }
}//GEN-LAST:event_TTndPrKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TKd.getText().trim().equals("")){
            Valid.textKosong(TKd,"Kode Jenis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Nama Perawatan");
        }else if(KdKtg.getText().trim().equals("")||NmKtg.getText().trim().equals("")){
            Valid.textKosong(KdKtg,"Kategori Perawatan");
        }else if(kdpoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
            Valid.textKosong(kdpoli,"Unit Poli");
        }else if(kdpnj.getText().trim().equals("")||nmpnj.getText().trim().equals("")){
            Valid.textKosong(kdpnj,"Jenis Bayar");
        }else if(BagianRs.getText().trim().equals("")){
            Valid.textKosong(BagianRs,"Bagian RS");
        }else if(BhpMedis.getText().trim().equals("")){
            Valid.textKosong(BhpMedis,"BHP Medis/Paket Obat");
        }else if(TTndDr.getText().trim().equals("")){
            Valid.textKosong(TTndDr,"Tarif tindakan dokter");
        }else if(TTndPr.getText().trim().equals("")){
            Valid.textKosong(TTndPr,"Tarif tindakan perawat");
        }else if(KSO.getText().trim().equals("")){
            Valid.textKosong(KSO,"K.S.O.");
        }else if(Menejemen.getText().trim().equals("")){
            Valid.textKosong(Menejemen,"Menejemen");
        }else{
            Sequel.menyimpan("jns_perawatan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Kode Tarif",15,new String[]{
                TKd.getText(),TNm.getText(),KdKtg.getText(),BagianRs.getText(),BhpMedis.getText(),TTndDr.getText(),
                TTndPr.getText(),KSO.getText(),Menejemen.getText(),TJmlDr.getText(),TJmlPr.getText(),TJmlDrPr.getText(),
                kdpnj.getText(),kdpoli.getText(),"1"
            });
            BtnCariActionPerformed(evt);
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TTndDr,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for(i=0;i<tbJnsPerawatan.getRowCount();i++){ 
            if(tbJnsPerawatan.getValueAt(i,0).toString().equals("true")){
                Sequel.mengedit("jns_perawatan","kd_jenis_prw='"+tbJnsPerawatan.getValueAt(i,1).toString()+"'","status='0'");
            }
        }        
        BtnCariActionPerformed(evt);
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
            Valid.textKosong(TKd,"Kode Jenis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Nama Perawatan");
        }else if(KdKtg.getText().trim().equals("")||NmKtg.getText().trim().equals("")){
            Valid.textKosong(KdKtg,"Kategori Perawatan");
        }else if(BagianRs.getText().trim().equals("")){
            Valid.textKosong(BagianRs,"Bagian RS");
        }else if(BhpMedis.getText().trim().equals("")){
            Valid.textKosong(BhpMedis,"BHP Medis/Paket Obat");
        }else if(TTndDr.getText().trim().equals("")){
            Valid.textKosong(TTndDr,"Tarif tindakan dokter");
        }else if(TTndPr.getText().trim().equals("")){
            Valid.textKosong(TTndPr,"Tarif tindakan perawat");
        }else if(KSO.getText().trim().equals("")){
            Valid.textKosong(KSO,"K.S.O.");
        }else if(Menejemen.getText().trim().equals("")){
            Valid.textKosong(Menejemen,"Menejemen");
        }else{
            Sequel.mengedit("jns_perawatan","kd_jenis_prw=?","kd_jenis_prw=?,nm_perawatan=?,kd_kategori=?,material=?,"+
                    "tarif_tindakandr=?,tarif_tindakanpr=?,total_byrdr=?,total_byrpr=?,kd_pj=?,kd_poli=?,total_byrdrpr=?,bhp=?,kso=?,menejemen=?",15,new String[]{
                        TKd.getText(),TNm.getText(),KdKtg.getText(),BagianRs.getText(),TTndDr.getText(),TTndPr.getText(),TJmlDr.getText(),TJmlPr.getText(),
                        kdpnj.getText(),kdpoli.getText(),TJmlDrPr.getText(),BhpMedis.getText(),KSO.getText(),Menejemen.getText(),
                        tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString()
                    });
            if(tabMode.getRowCount()!=0){BtnCariActionPerformed(evt);}
            emptTeks();
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
                Valid.MyReportqry("rptJnsPrw.jasper","report","::[ Data Tarif Perawatan ]::","select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.material,jns_perawatan.bhp,jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.kso,jns_perawatan.menejemen  "+
                   "from jns_perawatan inner join kategori_perawatan inner join penjab inner join poliklinik  "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori "+
                   "and poliklinik.kd_poli=jns_perawatan.kd_poli "+
                   "and penjab.kd_pj=jns_perawatan.kd_pj where "+
                    "jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                    " jns_perawatan.status='1' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                    " jns_perawatan.status='1' and kategori_perawatan.nm_kategori like '%"+TCari.getText().trim()+"%' or "+
                    " jns_perawatan.status='1' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                    " jns_perawatan.status='1' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%'  "+
                    "order by jns_perawatan.kd_jenis_prw",param);            
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TJmlPr);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_A){                
                for(i=0;i<tbJnsPerawatan.getRowCount();i++){ 
                    tbJnsPerawatan.setValueAt(true,i,0);
                }
            }          
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", NmPoli,kdpoli.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPoliActionPerformed(null);
        }else{
            Valid.pindah(evt,kdpnj,BagianRs);
        }
}//GEN-LAST:event_kdpoliKeyPressed

private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        poli.emptTeks();
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
}//GEN-LAST:event_BtnPoliActionPerformed

private void kdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpnjKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());     
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
            KdKtg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
            kdpoli.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPenjabActionPerformed(null);
        }
}//GEN-LAST:event_kdpnjKeyPressed

private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
        penjab.isCek();
        penjab.onCari();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
}//GEN-LAST:event_BtnPenjabActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TJmlDrPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TJmlDrPrKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TJmlDrPrKeyPressed

    private void BhpMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BhpMedisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            Menejemen.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            BagianRs.requestFocus();
        }
    }//GEN-LAST:event_BhpMedisKeyPressed

    private void MnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRestoreActionPerformed
        DlgRestoreTarifRalan restore=new DlgRestoreTarifRalan(null,true);
        restore.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        restore.setLocationRelativeTo(internalFrame1);
        restore.setVisible(true);         
    }//GEN-LAST:event_MnRestoreActionPerformed

    private void TTndDrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TTndDrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TTndDrActionPerformed

    private void KSOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KSOKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TTndPr.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            Menejemen.requestFocus();
        }
    }//GEN-LAST:event_KSOKeyPressed

    private void MenejemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenejemenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            KSO.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            isjml();
            BhpMedis.requestFocus();
        }
    }//GEN-LAST:event_MenejemenKeyPressed

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }         
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgJnsPerawatanRalan dialog = new DlgJnsPerawatanRalan(new javax.swing.JFrame(), true);
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
    private widget.TextBox BagianRs;
    private widget.TextBox BhpMedis;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPenjab;
    private widget.Button BtnPoli;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KSO;
    private widget.TextBox KdKtg;
    private widget.Label LCount;
    private widget.TextBox Menejemen;
    private javax.swing.JMenuItem MnRestore;
    private widget.TextBox NmKtg;
    private widget.TextBox NmPoli;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TJmlDr;
    private widget.TextBox TJmlDrPr;
    private widget.TextBox TJmlPr;
    private widget.TextBox TKd;
    private widget.TextBox TNm;
    private widget.TextBox TTndDr;
    private widget.TextBox TTndPr;
    private widget.Button btnKategori;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpnj;
    private widget.TextBox kdpoli;
    private widget.TextBox nmpnj;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            ps=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.material,jns_perawatan.bhp,jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,penjab.png_jawab,poliklinik.nm_poli "+
                       "from jns_perawatan inner join kategori_perawatan inner join penjab inner join poliklinik  "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori "+
                       "and poliklinik.kd_poli=jns_perawatan.kd_poli "+
                       "and penjab.kd_pj=jns_perawatan.kd_pj where "+
                        "jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? or "+
                        "jns_perawatan.status='1' and penjab.png_jawab like ? or "+
                        "jns_perawatan.status='1' and poliklinik.nm_poli like ?  "+
                        "order by jns_perawatan.kd_jenis_prw");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),
                        rs.getDouble(5),rs.getDouble(6),rs.getDouble(7),rs.getDouble(8),
                        rs.getDouble(9),rs.getDouble(10),rs.getDouble(11),rs.getDouble(12),
                        rs.getString(13),rs.getString(14)
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TKd.setText("");
        TNm.setText("");
        kdpoli.setText("");
        NmPoli.setText("");
        kdpnj.setText("");
        nmpnj.setText("");
        KdKtg.setText("");
        NmKtg.setText("");
        BagianRs.setText("0");
        BhpMedis.setText("0");
        KSO.setText("0");
        Menejemen.setText("0");
        TTndDr.setText("0");
        TTndPr.setText("0");
        TJmlDr.setText("0");
        TJmlPr.setText("0");
        TJmlDrPr.setText("0");
        //Valid.autoNomer(" jns_perawatan ","JP",6,TKd);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kd_jenis_prw,5),signed)),0) from jns_perawatan  ","RJ",5,TKd);
        TKd.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            TKd.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            TNm.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
            Sequel.cariIsi("select kd_kategori from jns_perawatan where kd_jenis_prw=?", KdKtg,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            NmKtg.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
            BagianRs.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString())));
            BhpMedis.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),5).toString())));
            TTndDr.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),6).toString())));
            TTndPr.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString())));
            KSO.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString())));
            Menejemen.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),9).toString())));
            TJmlDr.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),10).toString())));
            TJmlPr.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),11).toString())));
            TJmlDrPr.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),12).toString())));
            Sequel.cariIsi("select kd_pj from jns_perawatan where kd_jenis_prw=?", kdpnj,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            nmpnj.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),13).toString());
            Sequel.cariIsi("select kd_poli from jns_perawatan where kd_jenis_prw=?", kdpoli,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            NmPoli.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),14).toString());
        }
    }

    private void isktg(){
        Sequel.cariIsi("select nm_kategori from kategori_perawatan where kd_kategori=? ",NmKtg,KdKtg.getText());
    }

    private void isjml(){
        DecimalFormat df2 = new DecimalFormat("####");

        if((! BagianRs.getText().equals(""))&&(! TTndDr.getText().equals(""))&&(! BhpMedis.getText().equals(""))&&(! KSO.getText().equals(""))&&(! Menejemen.getText().equals(""))){
            double x=Double.parseDouble(BagianRs.getText().trim());
            double y=Double.parseDouble(TTndDr.getText().trim());
            double z=Double.parseDouble(BhpMedis.getText().trim());
            double p=Double.parseDouble(KSO.getText().trim());
            double q=Double.parseDouble(Menejemen.getText().trim());
            TJmlDr.setText(df2.format(x+y+z+p+q));
        }

        if((! BagianRs.getText().equals(""))&&(! TTndPr.getText().equals(""))&&(! BhpMedis.getText().equals(""))&&(! KSO.getText().equals(""))&&(! Menejemen.getText().equals(""))){
            double x=Double.parseDouble(BagianRs.getText().trim());
            double y=Double.parseDouble(TTndPr.getText().trim());
            double z=Double.parseDouble(BhpMedis.getText().trim());
            double p=Double.parseDouble(KSO.getText().trim());
            double q=Double.parseDouble(Menejemen.getText().trim());
            TJmlPr.setText(df2.format(x+y+z+p+q));
        }
        
        if((! BagianRs.getText().equals(""))&&(! TTndPr.getText().equals(""))&&(! BhpMedis.getText().equals(""))&&(! TTndDr.getText().equals(""))&&(! KSO.getText().equals(""))&&(! Menejemen.getText().equals(""))){
            double x=Double.parseDouble(BagianRs.getText().trim());
            double y=Double.parseDouble(TTndPr.getText().trim());
            double z=Double.parseDouble(TTndDr.getText().trim());
            double v=Double.parseDouble(BhpMedis.getText().trim());
            double p=Double.parseDouble(KSO.getText().trim());
            double q=Double.parseDouble(Menejemen.getText().trim());
            TJmlDrPr.setText(df2.format(x+y+z+v+p+q));
        }
    }
    
    public JTextField getTextField(){
        return TKd;
    }
   
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,249));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettarif_ralan());
        BtnHapus.setEnabled(akses.gettarif_ralan());
        BtnEdit.setEnabled(akses.gettarif_ralan());
        BtnPrint.setEnabled(akses.gettarif_ralan());
        if(akses.getkode().equals("Admin Utama")){
            MnRestore.setEnabled(true);
        }else{
            MnRestore.setEnabled(false);
        } 
    }
    
    public JTable getTable(){
        return tbJnsPerawatan;
    }

    
}

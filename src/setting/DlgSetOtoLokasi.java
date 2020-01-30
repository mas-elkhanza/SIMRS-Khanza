/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgAdmin.java
 *
 * Created on 21 Jun 10, 20:53:44
 */

package setting;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;

/**
 *
 * @author perpustakaan
 */
public class DlgSetOtoLokasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeRalan,tabModeRanap;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);    
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private int i, pilihan=0;

    /** Creates new form DlgAdmin
     * @param parent
     * @param modal */
    public DlgSetOtoLokasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,10);
        setSize(457,249);
        tabMode=new DefaultTableModel(null,new Object[]{"Kode Lokasi","Nama Lokasi","Penggunaan Stok Ranap"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbAdmin.setModel(tabMode);
        tbAdmin.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbAdmin.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbAdmin.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(200);
            }
        }

        tbAdmin.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeRalan=new DefaultTableModel(null,new Object[]{"Kode Poli","Poliklinik","Kode Depo","Depo Obat"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbRalan.setModel(tabModeRalan);
        tbRalan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }
        }

        tbRalan.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeRanap=new DefaultTableModel(null,new Object[]{"Kode","Bangsal/Kamar","Kode Depo","Depo Obat"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbRanap.setModel(tabModeRanap);
        tbRanap.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbRanap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }
        }

        tbRanap.setDefaultRenderer(Object.class, new WarnaTable());

        kdbangsal.setDocument(new batasInput((byte)5).getKata(kdbangsal));
        KodePoli.setDocument(new batasInput((byte)5).getKata(KodePoli)); 
        KodeDepoRalan.setDocument(new batasInput((byte)5).getKata(KodeDepoRalan)); 
        KodeDepoRanap.setDocument(new batasInput((byte)5).getKata(KodeDepoRanap)); 
        KodeBangsalRanap.setDocument(new batasInput((byte)5).getKata(KodeBangsalRanap)); 
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){  
                    if(pilihan==1){
                        kdbangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        nmbangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        kdbangsal.requestFocus();
                    }else if(pilihan==2){
                        KodeDepoRalan.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        NmDepoRalan.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        KodeDepoRalan.requestFocus();
                    }else if(pilihan==3){
                        KodeBangsalRanap.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        NamaBangsalRanap.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        KodeBangsalRanap.requestFocus();
                    }else if(pilihan==4){
                        KodeDepoRanap.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        NamaDepoRanap.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        KodeDepoRanap.requestFocus();
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){        
                    KodePoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    KodePoli.requestFocus();
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

        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass7 = new widget.panelisi();
        jLabel4 = new widget.Label();
        kdbangsal = new widget.TextBox();
        nmbangsal = new widget.TextBox();
        btnBangsal = new widget.Button();
        jLabel5 = new widget.Label();
        cmbstok = new widget.ComboBox();
        Scroll = new widget.ScrollPane();
        tbAdmin = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        jLabel6 = new widget.Label();
        KodePoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        btnBangsal1 = new widget.Button();
        jLabel7 = new widget.Label();
        KodeDepoRalan = new widget.TextBox();
        NmDepoRalan = new widget.TextBox();
        btnBangsal3 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbRalan = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        panelGlass9 = new widget.panelisi();
        btnBangsal2 = new widget.Button();
        NamaBangsalRanap = new widget.TextBox();
        KodeBangsalRanap = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        KodeDepoRanap = new widget.TextBox();
        NamaDepoRanap = new widget.TextBox();
        btnBangsal4 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbRanap = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Setup Otomatis Lokasi Stok Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        BtnEdit.setIconTextGap(3);
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

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239,244,234)));
        TabRawat.setForeground(new java.awt.Color(50,50,50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(74, 77));
        panelGlass7.setLayout(null);

        jLabel4.setText("Lokasi Stok Utama Obat :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass7.add(jLabel4);
        jLabel4.setBounds(0, 12, 142, 23);

        kdbangsal.setHighlighter(null);
        kdbangsal.setName("kdbangsal"); // NOI18N
        kdbangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbangsalKeyPressed(evt);
            }
        });
        panelGlass7.add(kdbangsal);
        kdbangsal.setBounds(146, 12, 70, 23);

        nmbangsal.setEditable(false);
        nmbangsal.setName("nmbangsal"); // NOI18N
        nmbangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmbangsalKeyPressed(evt);
            }
        });
        panelGlass7.add(nmbangsal);
        nmbangsal.setBounds(218, 12, 240, 23);

        btnBangsal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsal.setMnemonic('1');
        btnBangsal.setToolTipText("Alt+1");
        btnBangsal.setName("btnBangsal"); // NOI18N
        btnBangsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsalActionPerformed(evt);
            }
        });
        btnBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBangsalKeyPressed(evt);
            }
        });
        panelGlass7.add(btnBangsal);
        btnBangsal.setBounds(461, 12, 28, 23);

        jLabel5.setText("Penggunaan Stok Ranap :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 42, 142, 23);

        cmbstok.setBackground(new java.awt.Color(255, 255, 254));
        cmbstok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gunakan Stok Utama Obat", "Gunakan Stok Bangsal" }));
        cmbstok.setName("cmbstok"); // NOI18N
        cmbstok.setOpaque(false);
        cmbstok.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbstok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbstokKeyPressed(evt);
            }
        });
        panelGlass7.add(cmbstok);
        cmbstok.setBounds(146, 42, 312, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbAdmin.setAutoCreateRowSorter(true);
        tbAdmin.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbAdmin.setName("tbAdmin"); // NOI18N
        tbAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAdminMouseClicked(evt);
            }
        });
        tbAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAdminKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbAdmin);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Pengaturan Umum", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(74, 77));
        panelGlass8.setLayout(null);

        jLabel6.setText("Poliklinik :");
        jLabel6.setName("jLabel6"); // NOI18N
        panelGlass8.add(jLabel6);
        jLabel6.setBounds(0, 12, 75, 23);

        KodePoli.setHighlighter(null);
        KodePoli.setName("KodePoli"); // NOI18N
        KodePoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePoliKeyPressed(evt);
            }
        });
        panelGlass8.add(KodePoli);
        KodePoli.setBounds(78, 12, 75, 23);

        NmPoli.setEditable(false);
        NmPoli.setName("NmPoli"); // NOI18N
        NmPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmPoliKeyPressed(evt);
            }
        });
        panelGlass8.add(NmPoli);
        NmPoli.setBounds(155, 12, 322, 23);

        btnBangsal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsal1.setMnemonic('1');
        btnBangsal1.setToolTipText("Alt+1");
        btnBangsal1.setName("btnBangsal1"); // NOI18N
        btnBangsal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsal1ActionPerformed(evt);
            }
        });
        btnBangsal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBangsal1KeyPressed(evt);
            }
        });
        panelGlass8.add(btnBangsal1);
        btnBangsal1.setBounds(481, 12, 28, 23);

        jLabel7.setText("Depo Obat :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass8.add(jLabel7);
        jLabel7.setBounds(0, 42, 75, 23);

        KodeDepoRalan.setHighlighter(null);
        KodeDepoRalan.setName("KodeDepoRalan"); // NOI18N
        KodeDepoRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDepoRalanKeyPressed(evt);
            }
        });
        panelGlass8.add(KodeDepoRalan);
        KodeDepoRalan.setBounds(78, 42, 75, 23);

        NmDepoRalan.setEditable(false);
        NmDepoRalan.setName("NmDepoRalan"); // NOI18N
        NmDepoRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmDepoRalanKeyPressed(evt);
            }
        });
        panelGlass8.add(NmDepoRalan);
        NmDepoRalan.setBounds(155, 42, 322, 23);

        btnBangsal3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsal3.setMnemonic('1');
        btnBangsal3.setToolTipText("Alt+1");
        btnBangsal3.setName("btnBangsal3"); // NOI18N
        btnBangsal3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsal3ActionPerformed(evt);
            }
        });
        btnBangsal3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBangsal3KeyPressed(evt);
            }
        });
        panelGlass8.add(btnBangsal3);
        btnBangsal3.setBounds(481, 42, 28, 23);

        internalFrame3.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbRalan.setAutoCreateRowSorter(true);
        tbRalan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRalan.setName("tbRalan"); // NOI18N
        tbRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRalanMouseClicked(evt);
            }
        });
        tbRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRalanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbRalan);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Pengaturan Depo Ralan", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(74, 77));
        panelGlass9.setLayout(null);

        btnBangsal2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsal2.setMnemonic('1');
        btnBangsal2.setToolTipText("Alt+1");
        btnBangsal2.setName("btnBangsal2"); // NOI18N
        btnBangsal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsal2ActionPerformed(evt);
            }
        });
        btnBangsal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBangsal2KeyPressed(evt);
            }
        });
        panelGlass9.add(btnBangsal2);
        btnBangsal2.setBounds(481, 12, 28, 23);

        NamaBangsalRanap.setEditable(false);
        NamaBangsalRanap.setName("NamaBangsalRanap"); // NOI18N
        NamaBangsalRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaBangsalRanapKeyPressed(evt);
            }
        });
        panelGlass9.add(NamaBangsalRanap);
        NamaBangsalRanap.setBounds(175, 12, 302, 23);

        KodeBangsalRanap.setHighlighter(null);
        KodeBangsalRanap.setName("KodeBangsalRanap"); // NOI18N
        KodeBangsalRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeBangsalRanapKeyPressed(evt);
            }
        });
        panelGlass9.add(KodeBangsalRanap);
        KodeBangsalRanap.setBounds(98, 12, 75, 23);

        jLabel8.setText("Kamar/Bangsal :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass9.add(jLabel8);
        jLabel8.setBounds(0, 12, 95, 23);

        jLabel9.setText("Depo Obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass9.add(jLabel9);
        jLabel9.setBounds(0, 42, 95, 23);

        KodeDepoRanap.setHighlighter(null);
        KodeDepoRanap.setName("KodeDepoRanap"); // NOI18N
        KodeDepoRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDepoRanapKeyPressed(evt);
            }
        });
        panelGlass9.add(KodeDepoRanap);
        KodeDepoRanap.setBounds(98, 42, 75, 23);

        NamaDepoRanap.setEditable(false);
        NamaDepoRanap.setName("NamaDepoRanap"); // NOI18N
        NamaDepoRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaDepoRanapKeyPressed(evt);
            }
        });
        panelGlass9.add(NamaDepoRanap);
        NamaDepoRanap.setBounds(175, 42, 302, 23);

        btnBangsal4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsal4.setMnemonic('1');
        btnBangsal4.setToolTipText("Alt+1");
        btnBangsal4.setName("btnBangsal4"); // NOI18N
        btnBangsal4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsal4ActionPerformed(evt);
            }
        });
        btnBangsal4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBangsal4KeyPressed(evt);
            }
        });
        panelGlass9.add(btnBangsal4);
        btnBangsal4.setBounds(481, 42, 28, 23);

        internalFrame4.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbRanap.setAutoCreateRowSorter(true);
        tbRanap.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRanap.setName("tbRanap"); // NOI18N
        tbRanap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRanapMouseClicked(evt);
            }
        });
        tbRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRanapKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbRanap);

        internalFrame4.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Pengaturan Depo Ranap", internalFrame4);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(kdbangsal.getText().trim().equals("")){
                Valid.textKosong(kdbangsal,"Kode Lokasi");
            }else if(nmbangsal.getText().trim().equals("")){
                Valid.textKosong(nmbangsal,"Nama Lokasi");
            }else if(tabMode.getRowCount()==0){
                Sequel.menyimpan("set_lokasi","'"+kdbangsal.getText()+"','"+cmbstok.getSelectedItem()+"'","Lokasi");
                tampil();
                emptTeks();
            }else if(tabMode.getRowCount()>0){
                JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu lokasi ...!!!!");
                kdbangsal.requestFocus();
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(KodePoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
                Valid.textKosong(KodePoli,"Poliklinik");
            }else if(KodeDepoRalan.getText().trim().equals("")||NmDepoRalan.getText().trim().equals("")){
                Valid.textKosong(KodeDepoRalan,"Depo Obat");
            }else {
                Sequel.menyimpan("set_depo_ralan","'"+KodePoli.getText()+"','"+KodeDepoRalan.getText()+"'","Depo Rawat Jalan");
                tampilralan();
                emptTeks();
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(KodeBangsalRanap.getText().trim().equals("")||NamaBangsalRanap.getText().trim().equals("")){
                Valid.textKosong(KodeBangsalRanap,"Bangsal/Kamar");
            }else if(KodeDepoRanap.getText().trim().equals("")||NamaDepoRanap.getText().trim().equals("")){
                Valid.textKosong(KodeDepoRanap,"Depo Obat");
            }else {
                Sequel.menyimpan("set_depo_ranap","'"+KodeBangsalRanap.getText()+"','"+KodeDepoRanap.getText()+"'","Depo Rawat Inap");
                tampilranap();
                emptTeks();
            }
        }
            
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,nmbangsal,BtnBatal);
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
        if(TabRawat.getSelectedIndex()==0){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                kdbangsal.requestFocus();
            }else if(nmbangsal.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(! nmbangsal.getText().trim().equals("")){
                Sequel.queryu("delete from set_lokasi where kd_bangsal='"+kdbangsal.getText()+"'");
                tampil();
                emptTeks();
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabModeRalan.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                KodePoli.requestFocus();
            }else if(NmPoli.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(! NmPoli.getText().trim().equals("")){
                Sequel.queryu("delete from set_depo_ralan where kd_bangsal='"+KodeDepoRalan.getText()+"' and kd_poli='"+KodePoli.getText()+"'");
                tampilralan();
                emptTeks();
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tabModeRanap.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                KodeBangsalRanap.requestFocus();
            }else if(NamaBangsalRanap.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(! NamaBangsalRanap.getText().trim().equals("")){
                Sequel.queryu("delete from set_depo_ranap where kd_bangsal='"+KodeBangsalRanap.getText()+"' and kd_depo='"+KodeDepoRanap.getText()+"'");
                tampilranap();
                emptTeks();
            }
        }
            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnKeluar);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data admin tidak boleh kosong ...!!!!");
            kdbangsal.requestFocus();
        }else if(! (tabMode.getRowCount()==0)) {
            dispose();
        }
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnBatal,BtnKeluar);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAdminMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbAdminMouseClicked

    private void tbAdminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAdminKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbAdminKeyPressed

private void kdbangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbangsalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmbangsal,kdbangsal.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBangsalActionPerformed(null);
        }else{
            Valid.pindah(evt, kdbangsal,cmbstok);
        }
}//GEN-LAST:event_kdbangsalKeyPressed

private void nmbangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmbangsalKeyPressed
        Valid.pindah(evt,kdbangsal,BtnSimpan);
}//GEN-LAST:event_nmbangsalKeyPressed

private void btnBangsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsalActionPerformed
        pilihan=1;
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
}//GEN-LAST:event_btnBangsalActionPerformed

private void btnBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsalKeyPressed
        Valid.pindah(evt,kdbangsal,BtnSimpan);
}//GEN-LAST:event_btnBangsalKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void cmbstokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbstokKeyPressed
        Valid.pindah(evt, kdbangsal,BtnSimpan);
    }//GEN-LAST:event_cmbstokKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(kdbangsal.getText().trim().equals("")){
                Valid.textKosong(kdbangsal,"Kode Lokasi");
            }else if(nmbangsal.getText().trim().equals("")){
                Valid.textKosong(nmbangsal,"Nama Lokasi");
            }else{
                Sequel.queryu("delete from set_lokasi where kd_bangsal='"+kdbangsal.getText()+"'");
                Sequel.menyimpan("set_lokasi","'"+kdbangsal.getText()+"','"+cmbstok.getSelectedItem()+"'","Lokasi");
                tampil();
                emptTeks();
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabModeRalan.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                KodePoli.requestFocus();
            }else if(NmPoli.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal mengedit. Pilih dulu data yang mau diedit.\nKlik data pada table untuk memilih...!!!!");
            }else if(! NmPoli.getText().trim().equals("")){
                Sequel.queryu("update set_depo_ralan set kd_bangsal='"+KodeDepoRalan.getText()+"', kd_poli='"+KodePoli.getText()+"' "+
                              "where kd_bangsal='"+tbRalan.getValueAt(tbRalan.getSelectedRow(),2).toString()+"' and "+
                              "kd_poli='"+tbRalan.getValueAt(tbRalan.getSelectedRow(),0).toString()+"'");
                tampilralan();
                emptTeks();
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tabModeRanap.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                KodeBangsalRanap.requestFocus();
            }else if(NamaBangsalRanap.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal mengedit. Pilih dulu data yang mau diedit.\nKlik data pada table untuk memilih...!!!!");
            }else if(! NamaBangsalRanap.getText().trim().equals("")){
                Sequel.queryu("update set_depo_ranap set kd_bangsal='"+KodeBangsalRanap.getText()+"',kd_depo='"+KodeDepoRanap.getText()+"' "+
                              "where kd_bangsal='"+tbRanap.getValueAt(tbRanap.getSelectedRow(),0).toString()+"' and "+
                              "kd_depo='"+tbRanap.getValueAt(tbRanap.getSelectedRow(),2).toString()+"'");
                tampilranap();
                emptTeks();
            }
        }
            
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilralan();
        }else if(TabRawat.getSelectedIndex()==2){
            tampilranap();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void KodePoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePoliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodePoliKeyPressed

    private void NmPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmPoliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPoliKeyPressed

    private void btnBangsal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsal1ActionPerformed
        poli.isCek();        
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_btnBangsal1ActionPerformed

    private void btnBangsal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBangsal1KeyPressed

    private void tbRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRalanMouseClicked
        if(tabModeRalan.getRowCount()!=0){
            try {
                getDataRalan();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbRalanMouseClicked

    private void tbRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRalanKeyPressed
        if(tabModeRalan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataRalan();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }        
    }//GEN-LAST:event_tbRalanKeyPressed

    private void tbRanapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRanapMouseClicked
        if(tabModeRanap.getRowCount()!=0){
            try {
                getDataRanap();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbRanapMouseClicked

    private void tbRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRanapKeyPressed
        if(tabModeRanap.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataRanap();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }     
    }//GEN-LAST:event_tbRanapKeyPressed

    private void KodeDepoRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDepoRalanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDepoRalanKeyPressed

    private void NmDepoRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmDepoRalanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmDepoRalanKeyPressed

    private void btnBangsal3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsal3ActionPerformed
        pilihan=2;
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnBangsal3ActionPerformed

    private void btnBangsal3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsal3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBangsal3KeyPressed

    private void btnBangsal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsal2ActionPerformed
        pilihan=3;
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnBangsal2ActionPerformed

    private void btnBangsal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBangsal2KeyPressed

    private void NamaBangsalRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaBangsalRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NamaBangsalRanapKeyPressed

    private void KodeBangsalRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeBangsalRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeBangsalRanapKeyPressed

    private void KodeDepoRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDepoRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDepoRanapKeyPressed

    private void NamaDepoRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaDepoRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NamaDepoRanapKeyPressed

    private void btnBangsal4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsal4ActionPerformed
        pilihan=4;
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnBangsal4ActionPerformed

    private void btnBangsal4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsal4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBangsal4KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSetOtoLokasi dialog = new DlgSetOtoLokasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.TextBox KodeBangsalRanap;
    private widget.TextBox KodeDepoRalan;
    private widget.TextBox KodeDepoRanap;
    private widget.TextBox KodePoli;
    private widget.TextBox NamaBangsalRanap;
    private widget.TextBox NamaDepoRanap;
    private widget.TextBox NmDepoRalan;
    private widget.TextBox NmPoli;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Button btnBangsal;
    private widget.Button btnBangsal1;
    private widget.Button btnBangsal2;
    private widget.Button btnBangsal3;
    private widget.Button btnBangsal4;
    private widget.ComboBox cmbstok;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.TextBox kdbangsal;
    private widget.TextBox nmbangsal;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbAdmin;
    private widget.Table tbRalan;
    private widget.Table tbRanap;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{   
            ps=koneksi.prepareStatement("select set_lokasi.kd_bangsal,nm_bangsal,asal_stok from set_lokasi "+
                       "inner join bangsal on set_lokasi.kd_bangsal=bangsal.kd_bangsal");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3)});
                }
            } catch (SQLException e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getData() {
        int row=tbAdmin.getSelectedRow();
        if(row!= -1){
            kdbangsal.setText(tbAdmin.getValueAt(row,0).toString());
            nmbangsal.setText(tbAdmin.getValueAt(row,1).toString());
            cmbstok.setSelectedItem(tbAdmin.getValueAt(row,2).toString());
        }
    }

    public void emptTeks() {
        if(TabRawat.getSelectedIndex()==0){
            kdbangsal.setText("");
            nmbangsal.setText("");
            kdbangsal.requestFocus();
        }else if(TabRawat.getSelectedIndex()==1){
            KodePoli.setText("");
            NmPoli.setText("");
            KodeDepoRalan.setText("");
            NmDepoRalan.setText("");
        }else if(TabRawat.getSelectedIndex()==2){
            KodeBangsalRanap.setText("");
            NamaBangsalRanap.setText("");
            KodeDepoRanap.setText("");
            NamaDepoRanap.setText("");
        }
            
    }

    private void tampilralan() {
        Valid.tabelKosong(tabModeRalan);
        try{   
            ps=koneksi.prepareStatement(
                    "select set_depo_ralan.kd_poli,poliklinik.nm_poli,set_depo_ralan.kd_bangsal,bangsal.nm_bangsal "+
                    "from set_depo_ralan inner join poliklinik inner join bangsal on set_depo_ralan.kd_poli=poliklinik.kd_poli "+
                    "and set_depo_ralan.kd_bangsal=bangsal.kd_bangsal order by set_depo_ralan.kd_poli");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRalan.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
                }
            } catch (SQLException e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void tampilranap() {
        Valid.tabelKosong(tabModeRanap);
        try{   
            ps=koneksi.prepareStatement(
                    "select set_depo_ranap.kd_bangsal,bangsal.nm_bangsal,set_depo_ranap.kd_depo "+
                    "from set_depo_ranap inner join bangsal on set_depo_ranap.kd_bangsal=bangsal.kd_bangsal "+
                    "order by set_depo_ranap.kd_bangsal");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRanap.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?",rs.getString(3))
                    });
                }
            } catch (SQLException e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getDataRalan() {
        int row=tbRalan.getSelectedRow();
        if(row!= -1){
            KodePoli.setText(tbRalan.getValueAt(row,0).toString());
            NmPoli.setText(tbRalan.getValueAt(row,1).toString());
            KodeDepoRalan.setText(tbRalan.getValueAt(row,2).toString());
            NmDepoRalan.setText(tbRalan.getValueAt(row,3).toString());
        }
    }
    
    private void getDataRanap() {
        int row=tbRanap.getSelectedRow();
        if(row!= -1){
            KodeBangsalRanap.setText(tbRanap.getValueAt(row,0).toString());
            NamaBangsalRanap.setText(tbRanap.getValueAt(row,1).toString());
            KodeDepoRanap.setText(tbRanap.getValueAt(row,2).toString());
            NamaDepoRanap.setText(tbRanap.getValueAt(row,3).toString());
        }
    }
}

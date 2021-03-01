/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgObatPenyakit.java
 *
 * Created on May 23, 2010, 12:40:35 AM
 */

package setting;

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import keuangan.DlgCariPerawatanRalan;
import keuangan.DlgJnsPerawatanRalan;
import simrskhanza.DlgCariCaraBayar;

/**
 *
 * @author dosen
 */
public final class DlgSetOtoRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3;
    private sekuel Sequel=new sekuel();
    private PreparedStatement ps;
    private ResultSet rs;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgJnsPerawatanRalan datatindakan=new DlgJnsPerawatanRalan(null,false);
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);

    /** Creates new form DlgObatPenyakit
     * @param parent
     * @param modal */
    public DlgSetOtoRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);
 
        tabMode=new DefaultTableModel(null,new String[]{
            "Kode Dokter","Nama Dokter","Kode Tindakan","Nama Tindakan","Kode Bayar","Nama Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        Table1.setModel(tabMode);

        //tbObatPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObatPenyakit.getBackground()));
        Table1.setPreferredScrollableViewportSize(new Dimension(800,800));
        Table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

         for (int i = 0; i < 6; i++) {
            TableColumn column = Table1.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(200);
            }
        }
        Table1.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new String[]{
            "Kode Tindakan","Nama Tindakan","Kode Bayar","Nama Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        Table2.setModel(tabMode2);

        //tbObatPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObatPenyakit.getBackground()));
        Table2.setPreferredScrollableViewportSize(new Dimension(800,800));
        Table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

         for (int i = 0; i < 4; i++) {
            TableColumn column = Table2.getColumnModel().getColumn(i);
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
        Table2.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode3=new DefaultTableModel(null,new String[]{
            "Kode Dokter","Nama Dokter","Kode Tindakan","Nama Tindakan","Kode Bayar","Nama Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        Table3.setModel(tabMode3);

        //tbObatPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObatPenyakit.getBackground()));
        Table3.setPreferredScrollableViewportSize(new Dimension(800,800));
        Table3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

         for (int i = 0; i < 6; i++) {
            TableColumn column = Table3.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(200);
            }
        }
        Table3.setDefaultRenderer(Object.class, new WarnaTable());
        

        kdtindakan.setDocument(new batasInput((byte)15).getKata(kdtindakan));
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));
        kdpj.setDocument(new batasInput((byte)3).getKata(kdpj));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));

        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    if(TabRawat.getSelectedIndex()==0){
                        kdpj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                        nmpj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                        kdpj.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==1){
                        kdpj2.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                        nmpj2.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                        kdpj2.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==2){
                        kdpj3.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                        nmpj3.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                        kdpj3.requestFocus();
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
        
        datatindakan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(datatindakan.getTable().getSelectedRow()!= -1){    
                    if(TabRawat.getSelectedIndex()==0){
                        kdtindakan.setText(datatindakan.getTable().getValueAt(datatindakan.getTable().getSelectedRow(),1).toString());   
                        nmtindakan.setText(datatindakan.getTable().getValueAt(datatindakan.getTable().getSelectedRow(),2).toString()); 
                        kdtindakan.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==1){
                        kdtindakan2.setText(datatindakan.getTable().getValueAt(datatindakan.getTable().getSelectedRow(),1).toString());   
                        nmtindakan2.setText(datatindakan.getTable().getValueAt(datatindakan.getTable().getSelectedRow(),2).toString()); 
                        kdtindakan2.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==2){
                        kdtindakan3.setText(datatindakan.getTable().getValueAt(datatindakan.getTable().getSelectedRow(),1).toString());   
                        nmtindakan3.setText(datatindakan.getTable().getValueAt(datatindakan.getTable().getSelectedRow(),2).toString()); 
                        kdtindakan3.requestFocus();
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
        
        datatindakan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    datatindakan.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    if(TabRawat.getSelectedIndex()==0){
                        kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmdokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kddokter.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==2){
                        kddokter3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmdokter3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kddokter3.requestFocus();
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        Table1 = new widget.Table();
        panelGlass2 = new widget.panelisi();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        nmdokter = new widget.TextBox();
        nmtindakan = new widget.TextBox();
        BtnSeek = new widget.Button();
        BtnSeek1 = new widget.Button();
        kddokter = new widget.TextBox();
        kdtindakan = new widget.TextBox();
        jLabel5 = new widget.Label();
        kdpj = new widget.TextBox();
        nmpj = new widget.TextBox();
        BtnPenjab = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        Table2 = new widget.Table();
        panelGlass3 = new widget.panelisi();
        jLabel8 = new widget.Label();
        nmtindakan2 = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        kdtindakan2 = new widget.TextBox();
        jLabel9 = new widget.Label();
        kdpj2 = new widget.TextBox();
        nmpj2 = new widget.TextBox();
        BtnPenjab1 = new widget.Button();
        internalFrame4 = new widget.InternalFrame();
        panelGlass4 = new widget.panelisi();
        jLabel7 = new widget.Label();
        jLabel11 = new widget.Label();
        nmdokter3 = new widget.TextBox();
        nmtindakan3 = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnSeek4 = new widget.Button();
        kddokter3 = new widget.TextBox();
        kdtindakan3 = new widget.TextBox();
        jLabel12 = new widget.Label();
        kdpj3 = new widget.TextBox();
        nmpj3 = new widget.TextBox();
        BtnPenjab2 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        Table3 = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Setup Tindakan Otomatis Ralan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

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
        panelGlass9.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
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

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        Table1.setAutoCreateRowSorter(true);
        Table1.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        Table1.setName("Table1"); // NOI18N
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
        });
        Table1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Table1KeyPressed(evt);
            }
        });
        Scroll.setViewportView(Table1);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass2.setName("panelGlass2"); // NOI18N
        panelGlass2.setPreferredSize(new java.awt.Dimension(711, 107));
        panelGlass2.setLayout(null);

        jLabel3.setText("Dokter Rawat Jalan :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass2.add(jLabel3);
        jLabel3.setBounds(0, 12, 120, 23);

        jLabel4.setText("Tindakan Otomatis :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass2.add(jLabel4);
        jLabel4.setBounds(0, 42, 120, 23);

        nmdokter.setEditable(false);
        nmdokter.setHighlighter(null);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmdokterKeyPressed(evt);
            }
        });
        panelGlass2.add(nmdokter);
        nmdokter.setBounds(225, 12, 252, 23);

        nmtindakan.setEditable(false);
        nmtindakan.setName("nmtindakan"); // NOI18N
        nmtindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmtindakanKeyPressed(evt);
            }
        });
        panelGlass2.add(nmtindakan);
        nmtindakan.setBounds(225, 42, 252, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("Alt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        BtnSeek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeekKeyPressed(evt);
            }
        });
        panelGlass2.add(BtnSeek);
        BtnSeek.setBounds(480, 12, 28, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('2');
        BtnSeek1.setToolTipText("Alt+2");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        BtnSeek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek1KeyPressed(evt);
            }
        });
        panelGlass2.add(BtnSeek1);
        BtnSeek1.setBounds(480, 42, 28, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        panelGlass2.add(kddokter);
        kddokter.setBounds(123, 12, 100, 23);

        kdtindakan.setHighlighter(null);
        kdtindakan.setName("kdtindakan"); // NOI18N
        kdtindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdtindakanKeyPressed(evt);
            }
        });
        panelGlass2.add(kdtindakan);
        kdtindakan.setBounds(123, 42, 100, 23);

        jLabel5.setText("Cara Bayar :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass2.add(jLabel5);
        jLabel5.setBounds(0, 72, 120, 23);

        kdpj.setHighlighter(null);
        kdpj.setName("kdpj"); // NOI18N
        kdpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpjKeyPressed(evt);
            }
        });
        panelGlass2.add(kdpj);
        kdpj.setBounds(123, 72, 100, 23);

        nmpj.setEditable(false);
        nmpj.setHighlighter(null);
        nmpj.setName("nmpj"); // NOI18N
        nmpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmpjKeyPressed(evt);
            }
        });
        panelGlass2.add(nmpj);
        nmpj.setBounds(225, 72, 252, 23);

        BtnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab.setMnemonic('1');
        BtnPenjab.setToolTipText("Alt+1");
        BtnPenjab.setName("BtnPenjab"); // NOI18N
        BtnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjabActionPerformed(evt);
            }
        });
        panelGlass2.add(BtnPenjab);
        BtnPenjab.setBounds(480, 72, 28, 23);

        internalFrame2.add(panelGlass2, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Tindakan Dokter", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        Table2.setAutoCreateRowSorter(true);
        Table2.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        Table2.setName("Table2"); // NOI18N
        Table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table2MouseClicked(evt);
            }
        });
        Table2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Table2KeyPressed(evt);
            }
        });
        Scroll1.setViewportView(Table2);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelGlass3.setName("panelGlass3"); // NOI18N
        panelGlass3.setPreferredSize(new java.awt.Dimension(711, 77));
        panelGlass3.setLayout(null);

        jLabel8.setText("Tindakan Otomatis :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass3.add(jLabel8);
        jLabel8.setBounds(0, 12, 120, 23);

        nmtindakan2.setEditable(false);
        nmtindakan2.setName("nmtindakan2"); // NOI18N
        nmtindakan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmtindakan2KeyPressed(evt);
            }
        });
        panelGlass3.add(nmtindakan2);
        nmtindakan2.setBounds(225, 12, 252, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('2');
        BtnSeek3.setToolTipText("Alt+2");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        panelGlass3.add(BtnSeek3);
        BtnSeek3.setBounds(480, 12, 28, 23);

        kdtindakan2.setHighlighter(null);
        kdtindakan2.setName("kdtindakan2"); // NOI18N
        kdtindakan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdtindakan2KeyPressed(evt);
            }
        });
        panelGlass3.add(kdtindakan2);
        kdtindakan2.setBounds(123, 12, 100, 23);

        jLabel9.setText("Cara Bayar :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass3.add(jLabel9);
        jLabel9.setBounds(0, 42, 120, 23);

        kdpj2.setHighlighter(null);
        kdpj2.setName("kdpj2"); // NOI18N
        kdpj2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpj2KeyPressed(evt);
            }
        });
        panelGlass3.add(kdpj2);
        kdpj2.setBounds(123, 42, 100, 23);

        nmpj2.setEditable(false);
        nmpj2.setHighlighter(null);
        nmpj2.setName("nmpj2"); // NOI18N
        nmpj2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmpj2KeyPressed(evt);
            }
        });
        panelGlass3.add(nmpj2);
        nmpj2.setBounds(225, 42, 252, 23);

        BtnPenjab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab1.setMnemonic('1');
        BtnPenjab1.setToolTipText("Alt+1");
        BtnPenjab1.setName("BtnPenjab1"); // NOI18N
        BtnPenjab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjab1ActionPerformed(evt);
            }
        });
        panelGlass3.add(BtnPenjab1);
        BtnPenjab1.setBounds(480, 42, 28, 23);

        internalFrame3.add(panelGlass3, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Tindakan Petugas", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass4.setName("panelGlass4"); // NOI18N
        panelGlass4.setPreferredSize(new java.awt.Dimension(711, 107));
        panelGlass4.setLayout(null);

        jLabel7.setText("Dokter Rawat Jalan :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass4.add(jLabel7);
        jLabel7.setBounds(0, 12, 120, 23);

        jLabel11.setText("Tindakan Otomatis :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass4.add(jLabel11);
        jLabel11.setBounds(0, 42, 120, 23);

        nmdokter3.setEditable(false);
        nmdokter3.setHighlighter(null);
        nmdokter3.setName("nmdokter3"); // NOI18N
        nmdokter3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmdokter3KeyPressed(evt);
            }
        });
        panelGlass4.add(nmdokter3);
        nmdokter3.setBounds(225, 12, 252, 23);

        nmtindakan3.setEditable(false);
        nmtindakan3.setName("nmtindakan3"); // NOI18N
        nmtindakan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmtindakan3KeyPressed(evt);
            }
        });
        panelGlass4.add(nmtindakan3);
        nmtindakan3.setBounds(225, 42, 252, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('1');
        BtnSeek2.setToolTipText("Alt+1");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        panelGlass4.add(BtnSeek2);
        BtnSeek2.setBounds(480, 12, 28, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('2');
        BtnSeek4.setToolTipText("Alt+2");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        panelGlass4.add(BtnSeek4);
        BtnSeek4.setBounds(480, 42, 28, 23);

        kddokter3.setHighlighter(null);
        kddokter3.setName("kddokter3"); // NOI18N
        kddokter3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokter3KeyPressed(evt);
            }
        });
        panelGlass4.add(kddokter3);
        kddokter3.setBounds(123, 12, 100, 23);

        kdtindakan3.setHighlighter(null);
        kdtindakan3.setName("kdtindakan3"); // NOI18N
        kdtindakan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdtindakan3KeyPressed(evt);
            }
        });
        panelGlass4.add(kdtindakan3);
        kdtindakan3.setBounds(123, 42, 100, 23);

        jLabel12.setText("Cara Bayar :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass4.add(jLabel12);
        jLabel12.setBounds(0, 72, 120, 23);

        kdpj3.setHighlighter(null);
        kdpj3.setName("kdpj3"); // NOI18N
        kdpj3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpj3KeyPressed(evt);
            }
        });
        panelGlass4.add(kdpj3);
        kdpj3.setBounds(123, 72, 100, 23);

        nmpj3.setEditable(false);
        nmpj3.setHighlighter(null);
        nmpj3.setName("nmpj3"); // NOI18N
        nmpj3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmpj3KeyPressed(evt);
            }
        });
        panelGlass4.add(nmpj3);
        nmpj3.setBounds(225, 72, 252, 23);

        BtnPenjab2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab2.setMnemonic('1');
        BtnPenjab2.setToolTipText("Alt+1");
        BtnPenjab2.setName("BtnPenjab2"); // NOI18N
        BtnPenjab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjab2ActionPerformed(evt);
            }
        });
        panelGlass4.add(BtnPenjab2);
        BtnPenjab2.setBounds(480, 72, 28, 23);

        internalFrame4.add(panelGlass4, java.awt.BorderLayout.PAGE_START);

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        Table3.setAutoCreateRowSorter(true);
        Table3.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        Table3.setName("Table3"); // NOI18N
        Table3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table3MouseClicked(evt);
            }
        });
        Table3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Table3KeyPressed(evt);
            }
        });
        Scroll2.setViewportView(Table3);

        internalFrame4.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakan Dokter & Petugas", internalFrame4);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nmdokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmdokterKeyPressed
        //Valid.pindah(evt,TCari,cmbBangsal);
}//GEN-LAST:event_nmdokterKeyPressed

    private void nmtindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmtindakanKeyPressed
        //Valid.pindah(evt,TKd,BtnSimpan);
}//GEN-LAST:event_nmtindakanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(kddokter.getText().trim().equals("")||nmdokter.getText().trim().equals("")){
                Valid.textKosong(kddokter,"Dokter");
            }else if(kdtindakan.getText().trim().equals("")||nmtindakan.getText().trim().equals("")){
                Valid.textKosong(kdtindakan,"Tindakan");
            }else if(kdpj.getText().trim().equals("")||nmpj.getText().trim().equals("")){
                Valid.textKosong(kdpj,"Cara Bayar");
            }else{
                if(Sequel.menyimpantf(
                        "set_otomatis_tindakan_ralan","'"+kddokter.getText()+"','"+kdtindakan.getText()+"','"+kdpj.getText()+"'",
                        "Tindakan")==true){
                    tampil();
                    emptTeks();
                }                
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(kdtindakan2.getText().trim().equals("")||nmtindakan2.getText().trim().equals("")){
                Valid.textKosong(kdtindakan2,"Tindakan");
            }else if(kdpj2.getText().trim().equals("")||nmpj2.getText().trim().equals("")){
                Valid.textKosong(kdpj2,"Cara Bayar");
            }else{
                if(Sequel.menyimpantf(
                        "set_otomatis_tindakan_ralan_petugas","'"+kdtindakan2.getText()+"','"+kdpj2.getText()+"'","Tindakan")==true){
                    tampil2();
                    emptTeks2();
                }                
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(kddokter3.getText().trim().equals("")||nmdokter3.getText().trim().equals("")){
                Valid.textKosong(kddokter3,"Dokter");
            }else if(kdtindakan3.getText().trim().equals("")||nmtindakan3.getText().trim().equals("")){
                Valid.textKosong(kdtindakan3,"Tindakan");
            }else if(kdpj3.getText().trim().equals("")||nmpj3.getText().trim().equals("")){
                Valid.textKosong(kdpj3,"Cara Bayar");
            }else{
                if(Sequel.menyimpantf(
                        "set_otomatis_tindakan_ralan_dokterpetugas","'"+kddokter3.getText()+"','"+kdtindakan3.getText()+"','"+kdpj3.getText()+"'",
                        "Tindakan")==true){
                    tampil3();
                    emptTeks3();
                }                
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,kdtindakan,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,kdtindakan2,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,kdtindakan3,BtnBatal);
            }                
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            emptTeks();
        }else if(TabRawat.getSelectedIndex()==1){
            emptTeks2();
        }else if(TabRawat.getSelectedIndex()==2){
            emptTeks3();
        }
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
                kddokter.requestFocus();
            }else if(nmtindakan.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(nmtindakan.getText().trim().equals(""))){
                Valid.hapusTable(tabMode,kddokter,"set_otomatis_tindakan_ralan","kd_pj='"+kdpj.getText()+"' and kd_jenis_prw='"+kdtindakan.getText()+"' and kd_dokter");
                tampil();
                emptTeks();
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabMode2.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                kdpj2.requestFocus();
            }else if(nmtindakan2.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(nmtindakan2.getText().trim().equals(""))){
                Valid.hapusTable(tabMode2,kdtindakan2,"set_otomatis_tindakan_ralan_petugas","kd_pj='"+kdpj2.getText()+"' and kd_jenis_prw");
                tampil2();
                emptTeks2();
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tabMode3.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                kddokter3.requestFocus();
            }else if(nmtindakan3.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(nmtindakan3.getText().trim().equals(""))){
                Valid.hapusTable(tabMode3,kddokter3,"set_otomatis_tindakan_ralan_dokterpetugas","kd_pj='"+kdpj3.getText()+"' and kd_jenis_prw='"+kdtindakan3.getText()+"' and kd_dokter");
                tampil3();
                emptTeks3();
            }
        }
            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
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
                    Valid.MyReportqry("rptOtoRalan.jasper","report","::[ Data Tindakan Otomatis Dokter Ralan ]::","select set_otomatis_tindakan_ralan.kd_dokter,dokter.nm_dokter, set_otomatis_tindakan_ralan.kd_jenis_prw,jns_perawatan.nm_perawatan "+
                   "from set_otomatis_tindakan_ralan inner join dokter inner join jns_perawatan on  "+
                   "set_otomatis_tindakan_ralan.kd_dokter=dokter.kd_dokter and "+
                   "set_otomatis_tindakan_ralan.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "where set_otomatis_tindakan_ralan.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                   "set_otomatis_tindakan_ralan.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                   "jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' "+
                   " order by dokter.nm_dokter",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,BtnSimpan);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TabRawatMouseClicked(null);
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
        TabRawatMouseClicked(null);
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        dokter.isCek();
        dokter.emptTeks();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt,kddokter,kdtindakan);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        datatindakan.isCek();
        datatindakan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        datatindakan.setLocationRelativeTo(internalFrame1);
        datatindakan.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,kdtindakan,BtnSimpan);
}//GEN-LAST:event_BtnSeek1KeyPressed

    private void Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_Table1MouseClicked

    private void Table1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table1KeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_Table1KeyPressed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
             Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='"+kddokter.getText()+"'", nmdokter);
        }else{
             Valid.pindah(evt,TCari,kdtindakan);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void kdtindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdtindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
             Sequel.cariIsi("select nm_perawatan from jns_perawatan where kd_jenis_prw=? ",nmtindakan,kdtindakan.getText());
        }else{
             Valid.pindah(evt,kddokter,kdpj);
        }
}//GEN-LAST:event_kdtindakanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kdpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpj,kdpj.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPenjabActionPerformed(null);
        }else{
            Valid.pindah(evt,kdtindakan,BtnSimpan);
        }
    }//GEN-LAST:event_kdpjKeyPressed

    private void nmpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmpjKeyPressed
        //Valid.pindah(evt,TCari,cmbBangsal);
    }//GEN-LAST:event_nmpjKeyPressed

    private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
        penjab.isCek();
        penjab.emptTeks();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnPenjabActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }else if(TabRawat.getSelectedIndex()==2){
            tampil3();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void Table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MouseClicked
        if(tabMode2.getRowCount()!=0){
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_Table2MouseClicked

    private void Table2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table2KeyPressed
        if(tabMode2.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_Table2KeyPressed

    private void nmtindakan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmtindakan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmtindakan2KeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        datatindakan.isCek();
        datatindakan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        datatindakan.setLocationRelativeTo(internalFrame1);
        datatindakan.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void kdtindakan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdtindakan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdtindakan2KeyPressed

    private void kdpj2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpj2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpj2,kdpj2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPenjabActionPerformed(null);
        }else{
            Valid.pindah(evt,kdtindakan2,BtnSimpan);
        }
    }//GEN-LAST:event_kdpj2KeyPressed

    private void nmpj2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmpj2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmpj2KeyPressed

    private void BtnPenjab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjab1ActionPerformed
        penjab.isCek();
        penjab.emptTeks();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnPenjab1ActionPerformed

    private void nmdokter3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmdokter3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmdokter3KeyPressed

    private void nmtindakan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmtindakan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmtindakan3KeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        dokter.isCek();
        dokter.emptTeks();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        datatindakan.isCek();
        datatindakan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        datatindakan.setLocationRelativeTo(internalFrame1);
        datatindakan.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void kddokter3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokter3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
             Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='"+kddokter3.getText()+"'", nmdokter3);
        }else{
             Valid.pindah(evt,TCari,kdtindakan3);
        }
    }//GEN-LAST:event_kddokter3KeyPressed

    private void kdtindakan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdtindakan3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
             Sequel.cariIsi("select nm_perawatan from jns_perawatan where kd_jenis_prw=? ",nmtindakan3,kdtindakan3.getText());
        }else{
             Valid.pindah(evt,kddokter3,kdpj3);
        }
    }//GEN-LAST:event_kdtindakan3KeyPressed

    private void kdpj3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpj3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpj3,kdpj3.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPenjabActionPerformed(null);
        }else{
            Valid.pindah(evt,kdtindakan3,BtnSimpan);
        }
    }//GEN-LAST:event_kdpj3KeyPressed

    private void nmpj3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmpj3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmpj3KeyPressed

    private void BtnPenjab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjab2ActionPerformed
        penjab.isCek();
        penjab.emptTeks();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnPenjab2ActionPerformed

    private void Table3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table3MouseClicked
        if(tabMode3.getRowCount()!=0){
            try {
                getData3();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_Table3MouseClicked

    private void Table3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table3KeyPressed
        if(tabMode3.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData3();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_Table3KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSetOtoRalan dialog = new DlgSetOtoRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPenjab;
    private widget.Button BtnPenjab1;
    private widget.Button BtnPenjab2;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Table Table1;
    private widget.Table Table2;
    private widget.Table Table3;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kddokter;
    private widget.TextBox kddokter3;
    private widget.TextBox kdpj;
    private widget.TextBox kdpj2;
    private widget.TextBox kdpj3;
    private widget.TextBox kdtindakan;
    private widget.TextBox kdtindakan2;
    private widget.TextBox kdtindakan3;
    private widget.TextBox nmdokter;
    private widget.TextBox nmdokter3;
    private widget.TextBox nmpj;
    private widget.TextBox nmpj2;
    private widget.TextBox nmpj3;
    private widget.TextBox nmtindakan;
    private widget.TextBox nmtindakan2;
    private widget.TextBox nmtindakan3;
    private widget.panelisi panelGlass2;
    private widget.panelisi panelGlass3;
    private widget.panelisi panelGlass4;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    // End of variables declaration//GEN-END:variables


    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps=koneksi.prepareStatement(
               "select set_otomatis_tindakan_ralan.kd_dokter,dokter.nm_dokter, "+
               "set_otomatis_tindakan_ralan.kd_jenis_prw,jns_perawatan.nm_perawatan, "+
               "set_otomatis_tindakan_ralan.kd_pj,penjab.png_jawab "+
               "from set_otomatis_tindakan_ralan inner join dokter "+
               "inner join jns_perawatan inner join penjab on  "+
               "set_otomatis_tindakan_ralan.kd_dokter=dokter.kd_dokter and "+
               "set_otomatis_tindakan_ralan.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "+
               "set_otomatis_tindakan_ralan.kd_pj=penjab.kd_pj "+
               "where set_otomatis_tindakan_ralan.kd_dokter like ? or "+
               "dokter.nm_dokter like ? or "+
               "set_otomatis_tindakan_ralan.kd_jenis_prw like ? or "+
               "jns_perawatan.nm_perawatan like ? or "+
               "set_otomatis_tindakan_ralan.kd_pj like ? or "+
               "penjab.png_jawab like ? "+
               " order by dokter.nm_dokter");
            try{            
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6)
                    });
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("setting.DlgSetOtoRalan.tampil() : "+e);
        } 
            
        LCount.setText(""+tabMode.getRowCount());
    }
    
    private void tampil2() {
        Valid.tabelKosong(tabMode2);
        try {
            ps=koneksi.prepareStatement(
               "select set_otomatis_tindakan_ralan_petugas.kd_jenis_prw,jns_perawatan.nm_perawatan, "+
               "set_otomatis_tindakan_ralan_petugas.kd_pj,penjab.png_jawab "+
               "from set_otomatis_tindakan_ralan_petugas inner join jns_perawatan inner join penjab on  "+
               "set_otomatis_tindakan_ralan_petugas.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "+
               "set_otomatis_tindakan_ralan_petugas.kd_pj=penjab.kd_pj "+
               "where set_otomatis_tindakan_ralan_petugas.kd_jenis_prw like ? or "+
               "jns_perawatan.nm_perawatan like ? or "+
               "set_otomatis_tindakan_ralan_petugas.kd_pj like ? or "+
               "penjab.png_jawab like ? "+
               " order by set_otomatis_tindakan_ralan_petugas.kd_jenis_prw ");
            try{            
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode2.addRow(new String[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4)
                    });
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("setting.DlgSetOtoRalan.tampil2() : "+e);
        } 
            
        LCount.setText(""+tabMode2.getRowCount());
    }
    
    private void tampil3() {
        Valid.tabelKosong(tabMode3);
        try {
            ps=koneksi.prepareStatement(
               "select set_otomatis_tindakan_ralan_dokterpetugas.kd_dokter,dokter.nm_dokter, "+
               "set_otomatis_tindakan_ralan_dokterpetugas.kd_jenis_prw,jns_perawatan.nm_perawatan, "+
               "set_otomatis_tindakan_ralan_dokterpetugas.kd_pj,penjab.png_jawab "+
               "from set_otomatis_tindakan_ralan_dokterpetugas inner join dokter "+
               "inner join jns_perawatan inner join penjab on  "+
               "set_otomatis_tindakan_ralan_dokterpetugas.kd_dokter=dokter.kd_dokter and "+
               "set_otomatis_tindakan_ralan_dokterpetugas.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "+
               "set_otomatis_tindakan_ralan_dokterpetugas.kd_pj=penjab.kd_pj "+
               "where set_otomatis_tindakan_ralan_dokterpetugas.kd_dokter like ? or "+
               "dokter.nm_dokter like ? or "+
               "set_otomatis_tindakan_ralan_dokterpetugas.kd_jenis_prw like ? or "+
               "jns_perawatan.nm_perawatan like ? or "+
               "set_otomatis_tindakan_ralan_dokterpetugas.kd_pj like ? or "+
               "penjab.png_jawab like ? "+
               " order by dokter.nm_dokter");
            try{            
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode3.addRow(new String[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6)
                    });
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("setting.DlgSetOtoRalan.tampil3() : "+e);
        } 
            
        LCount.setText(""+tabMode3.getRowCount());
    }

    public void emptTeks() {
        kdtindakan.setText("");
        nmtindakan.setText("");
        kdtindakan.requestFocus();
    }
    
    public void emptTeks2() {
        kdtindakan2.setText("");
        nmtindakan2.setText("");
        kdtindakan2.requestFocus();
    }
    
    public void emptTeks3() {
        kdtindakan3.setText("");
        nmtindakan3.setText("");
        kdtindakan3.requestFocus();
    }

    private void getData() {
        int row=Table1.getSelectedRow();
        if(row!= -1){
            kddokter.setText(Table1.getValueAt(row,0).toString());
            nmdokter.setText(Table1.getValueAt(row,1).toString());
            kdtindakan.setText(Table1.getValueAt(row,2).toString());
            nmtindakan.setText(Table1.getValueAt(row,3).toString());
            kdpj.setText(Table1.getValueAt(row,4).toString());
            nmpj.setText(Table1.getValueAt(row,5).toString());
        }
    }
    
    private void getData2() {
        int row=Table2.getSelectedRow();
        if(row!= -1){
            kdtindakan2.setText(Table2.getValueAt(row,0).toString());
            nmtindakan2.setText(Table2.getValueAt(row,1).toString());
            kdpj2.setText(Table2.getValueAt(row,2).toString());
            nmpj2.setText(Table2.getValueAt(row,3).toString());
        }
    }
    
    private void getData3() {
        int row=Table3.getSelectedRow();
        if(row!= -1){
            kddokter3.setText(Table3.getValueAt(row,0).toString());
            nmdokter3.setText(Table3.getValueAt(row,1).toString());
            kdtindakan3.setText(Table3.getValueAt(row,2).toString());
            nmtindakan3.setText(Table3.getValueAt(row,3).toString());
            kdpj3.setText(Table3.getValueAt(row,4).toString());
            nmpj3.setText(Table3.getValueAt(row,5).toString());
        }
    }

}

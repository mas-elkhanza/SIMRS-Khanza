    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgSpesialis.java
 *
 * Created on May 23, 2010, 1:25:13 AM
 */

package perpustakaan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public class PerpustakaanBayarDenda extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private PerpustakaanInventaris perpustakaan_inventaris=new PerpustakaanInventaris(null,false);
    private PerpustakaanAnggota anggota=new PerpustakaanAnggota(null,false);
    private PerpustakaanDenda denda=new PerpustakaanDenda(null,false);
    private double perhari=Sequel.cariIsiAngka("select denda_perhari from perpustakaan_set_peminjaman");

    /** Creates new form DlgSpesialis
     * @param parent
     * @param modal */
    public PerpustakaanBayarDenda(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Tgl.Denda","No.Anggota","Nama Anggota","No.Inventaris",
                "Judul Koleksi","Terlambat(Hari)","Denda(Rp)"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbBayarDenda.setModel(tabMode);

        tbBayarDenda.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBayarDenda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbBayarDenda.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(100);
            }
        }
        tbBayarDenda.setDefaultRenderer(Object.class, new WarnaTable());
        //------------------denda lain------------------------------------

        tabMode2=new DefaultTableModel(null,new Object[]{
                "Tgl.Denda","No.Anggota","Nama Anggota","No.Inventaris",
                "Judul Koleksi","Jenis Denda","Besar Denda(Rp)","Keterangan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbDendaLain.setModel(tabMode2);

        tbDendaLain.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDendaLain.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbDendaLain.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(150);
            }
        }
        tbDendaLain.setDefaultRenderer(Object.class, new WarnaTable());

        TNoA.setDocument(new batasInput((byte)10).getFilter(TNoA));
        TNoI.setDocument(new batasInput((byte)20).getKata(TNoI));
        Keterlambatan.setDocument(new batasInput((byte)10).getOnlyAngka(Keterlambatan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        if(koneksiDB.cariCepat().equals("aktif")){
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
        
        perpustakaan_inventaris.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(perpustakaan_inventaris.getTable().getSelectedRow()!= -1){  
                    TNoI.setText(perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),0).toString());
                    TJudul.setText(perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),1).toString()+", "+perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),2).toString());
                    Harga.setText(perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),11).toString());
                    TNoI.requestFocus();
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
        
        perpustakaan_inventaris.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    perpustakaan_inventaris.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        anggota.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(anggota.getTable().getSelectedRow()!= -1){                   
                    TNoA.setText(anggota.getTable().getValueAt(anggota.getTable().getSelectedRow(),0).toString());
                    TNmA.setText(anggota.getTable().getValueAt(anggota.getTable().getSelectedRow(),1).toString());
                }   
                TNoA.requestFocus();
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
        
        anggota.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    anggota.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        denda.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(denda.getTable().getSelectedRow()!= -1){                   
                    KdJenisDenda.setText(denda.getTable().getValueAt(denda.getTable().getSelectedRow(),0).toString());
                    JenisDenda.setText(denda.getTable().getValueAt(denda.getTable().getSelectedRow(),1).toString());
                    DendaLain.setText(denda.getTable().getValueAt(denda.getTable().getSelectedRow(),2).toString());
                }   
                KdJenisDenda.requestFocus();
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
        
        denda.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    denda.dispose();
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

        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        jLabel9 = new widget.Label();
        LCount1 = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel8 = new widget.Label();
        TglPinjam2 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        TglPinjam1 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelGlass7 = new widget.panelisi();
        LblTgl = new widget.Label();
        tgl = new widget.Tanggal();
        label9 = new widget.Label();
        TNoA = new widget.TextBox();
        TNmA = new widget.TextBox();
        btnAng = new widget.Button();
        label1 = new widget.Label();
        TNoI = new widget.TextBox();
        TJudul = new widget.TextBox();
        btnInv = new widget.Button();
        Harga = new widget.TextBox();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBayarDenda = new widget.Table();
        panelGlass10 = new widget.panelisi();
        jLabel10 = new widget.Label();
        Keterlambatan = new widget.TextBox();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        DendaKeterlambatan = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbDendaLain = new widget.Table();
        panelGlass11 = new widget.panelisi();
        label10 = new widget.Label();
        KdJenisDenda = new widget.TextBox();
        JenisDenda = new widget.TextBox();
        BtnDenda = new widget.Button();
        jLabel13 = new widget.Label();
        DendaLain = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel16 = new widget.Label();
        BesarDendaLain = new widget.TextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Bayar Denda Peminjaman Perpustakaan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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

        jLabel9.setText("Ttl.Denda :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel9);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass8.add(LCount1);

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
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel8.setText("Periode :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(52, 23));
        panelGlass9.add(jLabel8);

        TglPinjam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-04-2019" }));
        TglPinjam2.setDisplayFormat("dd-MM-yyyy");
        TglPinjam2.setName("TglPinjam2"); // NOI18N
        TglPinjam2.setOpaque(false);
        TglPinjam2.setPreferredSize(new java.awt.Dimension(90, 23));
        TglPinjam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPinjam2KeyPressed(evt);
            }
        });
        panelGlass9.add(TglPinjam2);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass9.add(jLabel22);

        TglPinjam1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-04-2019" }));
        TglPinjam1.setDisplayFormat("dd-MM-yyyy");
        TglPinjam1.setName("TglPinjam1"); // NOI18N
        TglPinjam1.setOpaque(false);
        TglPinjam1.setPreferredSize(new java.awt.Dimension(90, 23));
        TglPinjam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPinjam1KeyPressed(evt);
            }
        });
        panelGlass9.add(TglPinjam1);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BtnCariKeyReleased(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 75));
        panelGlass7.setLayout(null);

        LblTgl.setText("Tgl.Denda :");
        LblTgl.setName("LblTgl"); // NOI18N
        panelGlass7.add(LblTgl);
        LblTgl.setBounds(0, 10, 85, 23);

        tgl.setForeground(new java.awt.Color(50, 70, 50));
        tgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-04-2019" }));
        tgl.setDisplayFormat("dd-MM-yyyy");
        tgl.setName("tgl"); // NOI18N
        tgl.setOpaque(false);
        tgl.setPreferredSize(new java.awt.Dimension(90, 23));
        tgl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tglItemStateChanged(evt);
            }
        });
        tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tglKeyPressed(evt);
            }
        });
        panelGlass7.add(tgl);
        tgl.setBounds(88, 10, 90, 23);

        label9.setText("Peminjam :");
        label9.setName("label9"); // NOI18N
        panelGlass7.add(label9);
        label9.setBounds(191, 10, 90, 23);

        TNoA.setName("TNoA"); // NOI18N
        TNoA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoAKeyPressed(evt);
            }
        });
        panelGlass7.add(TNoA);
        TNoA.setBounds(284, 10, 100, 23);

        TNmA.setEditable(false);
        TNmA.setName("TNmA"); // NOI18N
        panelGlass7.add(TNmA);
        TNmA.setBounds(387, 10, 280, 23);

        btnAng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAng.setMnemonic('3');
        btnAng.setToolTipText("Alt+3");
        btnAng.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAng.setName("btnAng"); // NOI18N
        btnAng.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAngActionPerformed(evt);
            }
        });
        panelGlass7.add(btnAng);
        btnAng.setBounds(670, 10, 25, 23);

        label1.setText("No.Inventaris :");
        label1.setName("label1"); // NOI18N
        panelGlass7.add(label1);
        label1.setBounds(0, 40, 85, 23);

        TNoI.setName("TNoI"); // NOI18N
        TNoI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoIKeyPressed(evt);
            }
        });
        panelGlass7.add(TNoI);
        TNoI.setBounds(88, 40, 130, 23);

        TJudul.setEditable(false);
        TJudul.setName("TJudul"); // NOI18N
        panelGlass7.add(TJudul);
        TJudul.setBounds(221, 40, 333, 23);

        btnInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInv.setMnemonic('1');
        btnInv.setToolTipText("Alt+1");
        btnInv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInv.setName("btnInv"); // NOI18N
        btnInv.setPreferredSize(new java.awt.Dimension(100, 30));
        btnInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvActionPerformed(evt);
            }
        });
        panelGlass7.add(btnInv);
        btnInv.setBounds(670, 40, 25, 23);

        Harga.setEditable(false);
        Harga.setName("Harga"); // NOI18N
        panelGlass7.add(Harga);
        Harga.setBounds(557, 40, 110, 23);

        internalFrame1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
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

        tbBayarDenda.setAutoCreateRowSorter(true);
        tbBayarDenda.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbBayarDenda.setName("tbBayarDenda"); // NOI18N
        tbBayarDenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBayarDendaMouseClicked(evt);
            }
        });
        tbBayarDenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbBayarDendaKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbBayarDenda);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel10.setText("Keterlambatan Peminjaman :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(155, 23));
        panelGlass10.add(jLabel10);

        Keterlambatan.setName("Keterlambatan"); // NOI18N
        Keterlambatan.setPreferredSize(new java.awt.Dimension(70, 23));
        Keterlambatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeterlambatanKeyPressed(evt);
            }
        });
        panelGlass10.add(Keterlambatan);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Hari");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass10.add(jLabel11);

        jLabel12.setText("Jumlah Denda Keterlambatan : Rp.");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass10.add(jLabel12);

        DendaKeterlambatan.setEditable(false);
        DendaKeterlambatan.setName("DendaKeterlambatan"); // NOI18N
        DendaKeterlambatan.setPreferredSize(new java.awt.Dimension(150, 23));
        DendaKeterlambatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DendaKeterlambatanKeyPressed(evt);
            }
        });
        panelGlass10.add(DendaKeterlambatan);

        internalFrame2.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Denda Keterlambatan", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbDendaLain.setAutoCreateRowSorter(true);
        tbDendaLain.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDendaLain.setName("tbDendaLain"); // NOI18N
        tbDendaLain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDendaLainMouseClicked(evt);
            }
        });
        tbDendaLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDendaLainKeyReleased(evt);
            }
        });
        Scroll1.setViewportView(tbDendaLain);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 74));
        panelGlass11.setLayout(null);

        label10.setText("Jenis Denda :");
        label10.setName("label10"); // NOI18N
        panelGlass11.add(label10);
        label10.setBounds(0, 10, 81, 23);

        KdJenisDenda.setEditable(false);
        KdJenisDenda.setName("KdJenisDenda"); // NOI18N
        KdJenisDenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdJenisDendaKeyPressed(evt);
            }
        });
        panelGlass11.add(KdJenisDenda);
        KdJenisDenda.setBounds(84, 10, 65, 23);

        JenisDenda.setEditable(false);
        JenisDenda.setName("JenisDenda"); // NOI18N
        panelGlass11.add(JenisDenda);
        JenisDenda.setBounds(152, 10, 215, 23);

        BtnDenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDenda.setMnemonic('3');
        BtnDenda.setToolTipText("Alt+3");
        BtnDenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnDenda.setName("BtnDenda"); // NOI18N
        BtnDenda.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnDenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDendaActionPerformed(evt);
            }
        });
        BtnDenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDendaKeyPressed(evt);
            }
        });
        panelGlass11.add(BtnDenda);
        BtnDenda.setBounds(370, 10, 25, 23);

        jLabel13.setText("Besar Denda :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass11.add(jLabel13);
        jLabel13.setBounds(415, 10, 95, 23);

        DendaLain.setEditable(false);
        DendaLain.setText("0");
        DendaLain.setName("DendaLain"); // NOI18N
        DendaLain.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass11.add(DendaLain);
        DendaLain.setBounds(513, 10, 50, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("% dari harga buku");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass11.add(jLabel14);
        jLabel14.setBounds(570, 10, 120, 23);

        jLabel15.setText("Keterangan :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass11.add(jLabel15);
        jLabel15.setBounds(0, 40, 81, 23);

        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.setPreferredSize(new java.awt.Dimension(80, 23));
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelGlass11.add(Keterangan);
        Keterangan.setBounds(84, 40, 311, 23);

        jLabel16.setText("Rp .");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass11.add(jLabel16);
        jLabel16.setBounds(415, 40, 95, 23);

        BesarDendaLain.setEditable(false);
        BesarDendaLain.setText("0");
        BesarDendaLain.setName("BesarDendaLain"); // NOI18N
        BesarDendaLain.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass11.add(BesarDendaLain);
        BesarDendaLain.setBounds(513, 40, 160, 23);

        internalFrame3.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Denda Lain-lain", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
       
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        
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
        
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
       
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        
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

    private void BtnCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyReleased
        // TODO add your handling code here:
}//GEN-LAST:event_BtnCariKeyReleased

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
       emptTeks();
       tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        
}//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tglItemStateChanged
       
    }//GEN-LAST:event_tglItemStateChanged

    private void tglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tglKeyPressed
        
    }//GEN-LAST:event_tglKeyPressed

    private void TNoAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoAKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TNoI.requestFocus();
            Sequel.cariIsi("select nama_anggota from perpustakaan_anggota where no_anggota='"+TNoA.getText()+"'", TNmA);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAngActionPerformed(null);
        }
    }//GEN-LAST:event_TNoAKeyPressed

    private void btnAngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAngActionPerformed
        anggota.isCek();
        anggota.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        anggota.setLocationRelativeTo(internalFrame1);
        anggota.setAlwaysOnTop(false);
        anggota.setVisible(true); 
    }//GEN-LAST:event_btnAngActionPerformed

    private void TNoIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoIKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TNoA.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isInventaris();
            if(TabRawat.getSelectedIndex()==0){
                Keterlambatan.requestFocus();
            }else if(TabRawat.getSelectedIndex()==1){
                BtnDenda.requestFocus();
            }
        } else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnInvActionPerformed(null);
        }
    }//GEN-LAST:event_TNoIKeyPressed

    private void btnInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvActionPerformed
        perpustakaan_inventaris.isCek();
        perpustakaan_inventaris.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        perpustakaan_inventaris.setLocationRelativeTo(internalFrame1);
        perpustakaan_inventaris.setAlwaysOnTop(false);
        perpustakaan_inventaris.setVisible(true); 
    }//GEN-LAST:event_btnInvActionPerformed

    private void TglPinjam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam2KeyPressed
       
    }//GEN-LAST:event_TglPinjam2KeyPressed

    private void TglPinjam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam1KeyPressed
        
    }//GEN-LAST:event_TglPinjam1KeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            //tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbBayarDendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBayarDendaMouseClicked
       
    }//GEN-LAST:event_tbBayarDendaMouseClicked

    private void tbBayarDendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBayarDendaKeyReleased
       
    }//GEN-LAST:event_tbBayarDendaKeyReleased

    private void tbDendaLainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDendaLainMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDendaLainMouseClicked

    private void tbDendaLainKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDendaLainKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDendaLainKeyReleased

    private void KeterlambatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeterlambatanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TNoI.requestFocus();
            if(!Keterlambatan.getText().equals("")){
                DendaKeterlambatan.setText(Double.toString(Double.parseDouble(Keterlambatan.getText())*perhari));
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnSimpan.requestFocus();
        }
    }//GEN-LAST:event_KeterlambatanKeyPressed

    private void DendaKeterlambatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DendaKeterlambatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DendaKeterlambatanKeyPressed

    private void KdJenisDendaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdJenisDendaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TNoI.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnDendaActionPerformed(null);
        }
    }//GEN-LAST:event_KdJenisDendaKeyPressed

    private void BtnDendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDendaActionPerformed
        denda.isCek();
        denda.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        denda.setLocationRelativeTo(internalFrame1);
        denda.setAlwaysOnTop(false);
        denda.setVisible(true); 
    }//GEN-LAST:event_BtnDendaActionPerformed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt, KdJenisDenda, BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void BtnDendaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDendaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDendaKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PerpustakaanBayarDenda dialog = new PerpustakaanBayarDenda(new javax.swing.JFrame(), true);
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
    private widget.TextBox BesarDendaLain;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDenda;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.TextBox DendaKeterlambatan;
    private widget.TextBox DendaLain;
    private widget.TextBox Harga;
    private widget.TextBox JenisDenda;
    private widget.TextBox KdJenisDenda;
    private widget.TextBox Keterangan;
    private widget.TextBox Keterlambatan;
    private widget.Label LCount;
    private widget.Label LCount1;
    private widget.Label LblTgl;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TJudul;
    private widget.TextBox TNmA;
    private widget.TextBox TNoA;
    private widget.TextBox TNoI;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglPinjam1;
    private widget.Tanggal TglPinjam2;
    private widget.Button btnAng;
    private widget.Button btnInv;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel22;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbBayarDenda;
    private widget.Table tbDendaLain;
    private widget.Tanggal tgl;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        
    }

    public void emptTeks() {
        TNoI.setText("");
        TJudul.setText("");
        Harga.setText("");
        if(TabRawat.getSelectedIndex()==0){
            Keterlambatan.setText("");
            DendaKeterlambatan.setText("0");
        }else if(TabRawat.getSelectedIndex()==1){
            Keterangan.setText("");
            BesarDendaLain.setText("0");
        }
        TNoA.requestFocus();
    }

    private void getData() {
        
    }
    
    public void isInventaris(){
        try {
            ps=koneksi.prepareStatement(
               "select perpustakaan_inventaris.no_inventaris,perpustakaan_buku.kode_buku, perpustakaan_buku.judul_buku, "+
                "perpustakaan_inventaris.harga from perpustakaan_inventaris inner join perpustakaan_buku on "+
                "perpustakaan_buku.kode_buku=perpustakaan_inventaris.kode_buku where perpustakaan_inventaris.no_inventaris=?");
            try{
                ps.setString(1,TNoI.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TJudul.setText(rs.getString("kode_buku")+", "+rs.getString("judul_buku"));
                    Harga.setText(rs.getString("harga"));
                }else{
                    TJudul.setText("");
                    Harga.setText("");
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
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }

    public void isCek(){
       BtnSimpan.setEnabled(var.getbayar_denda_perpustakaan());
       BtnHapus.setEnabled(var.getbayar_denda_perpustakaan());
       BtnEdit.setEnabled(var.getbayar_denda_perpustakaan());
    }
}

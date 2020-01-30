/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgObatPenyakit.java
 *
 * Created on May 23, 2010, 12:40:35 AM
 */

package inventory;

import laporan.DlgKtgPenyakit;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import laporan.DlgCariPenyakit;

/**
 *
 * @author dosen
 */
public final class DlgObatPenyakit extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String sql;

    /** Creates new form DlgObatPenyakit
     * @param parent
     * @param modal */
    public DlgObatPenyakit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"P","Kode Penyakit",
                "Nama Penyakit",
                "Ciri-ciri Penyakit",
                "Keterangan",
                "Kategori Penyakit",
                "Ciri-ciri Umum",
                "Kode Obat",
                "Nama Obat",
                "Jenis Obat",
                "Harga Beli",
                "Referensi"};
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
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObatPenyakit.setModel(tabMode);

        //tbObatPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObatPenyakit.getBackground()));
        tbObatPenyakit.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbObatPenyakit.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

         for (i = 0; i < 12; i++) {
            TableColumn column = tbObatPenyakit.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(170);
            }else if(i==6){
                column.setPreferredWidth(170);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(170);
            }else if(i==9){
                column.setPreferredWidth(170);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(170);
            }
        }
        tbObatPenyakit.setDefaultRenderer(Object.class, new WarnaTable());

        kdobat.setDocument(new batasInput((byte)15).getKata(kdobat));
        kdpenyakit.setDocument(new batasInput((byte)10).getKata(kdpenyakit));
        TRef.setDocument(new batasInput((byte)60).getKata(TRef));
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
        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgObatPenyakit")){
                    if( penyakit.getTable().getSelectedRow()!= -1){                   
                        kdpenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString());
                        TPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                        PenyakitCari.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                        tampil();
                    }  
                    kdpenyakit.requestFocus();
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
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgObatPenyakit")){
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        kdobat.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                        nmobat.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                    }    
                    kdobat.requestFocus();
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
                if(akses.getform().equals("DlgObatPenyakit")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        
        ktg.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgObatPenyakit")){
                    if(ktg.getTable().getSelectedRow()!= -1){                                   
                        KtgCari.setText(ktg.getTable().getValueAt(ktg.getTable().getSelectedRow(),1).toString());
                    }   
                    KtgCari.requestFocus();
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
        
        ktg.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgObatPenyakit")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        ktg.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

    }
    
    private DlgCariPenyakit penyakit=new DlgCariPenyakit(null,false);
    private DlgKtgPenyakit ktg=new DlgKtgPenyakit(null,false);
    private DlgBarang barang=new DlgBarang(null,false);

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
        tbObatPenyakit = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel7 = new widget.Label();
        KtgCari = new widget.TextBox();
        btnKategoriCari = new widget.Button();
        jLabel11 = new widget.Label();
        PenyakitCari = new widget.TextBox();
        btnPenyakitCari = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelGlass2 = new widget.panelisi();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TPenyakit = new widget.TextBox();
        nmobat = new widget.TextBox();
        jLabel12 = new widget.Label();
        TRef = new widget.TextBox();
        btnpenyakit = new widget.Button();
        btnobat = new widget.Button();
        kdpenyakit = new widget.TextBox();
        kdobat = new widget.TextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat Penyakit/Alkes Dibutuhkan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObatPenyakit.setAutoCreateRowSorter(true);
        tbObatPenyakit.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObatPenyakit.setName("tbObatPenyakit"); // NOI18N
        tbObatPenyakit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatPenyakitMouseClicked(evt);
            }
        });
        tbObatPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatPenyakitKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObatPenyakit);

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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(108, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(90, 30));
        panelGlass8.add(LCount);

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

        jLabel7.setText("Ktg.Penyakit :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel7);

        KtgCari.setName("KtgCari"); // NOI18N
        KtgCari.setPreferredSize(new java.awt.Dimension(150, 23));
        KtgCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KtgCariKeyPressed(evt);
            }
        });
        panelGlass9.add(KtgCari);

        btnKategoriCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKategoriCari.setMnemonic('3');
        btnKategoriCari.setToolTipText("Alt+3");
        btnKategoriCari.setName("btnKategoriCari"); // NOI18N
        btnKategoriCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKategoriCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriCariActionPerformed(evt);
            }
        });
        btnKategoriCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKategoriCariKeyPressed(evt);
            }
        });
        panelGlass9.add(btnKategoriCari);

        jLabel11.setText("Penyakit :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel11);

        PenyakitCari.setName("PenyakitCari"); // NOI18N
        PenyakitCari.setPreferredSize(new java.awt.Dimension(150, 23));
        PenyakitCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitCariKeyPressed(evt);
            }
        });
        panelGlass9.add(PenyakitCari);

        btnPenyakitCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenyakitCari.setMnemonic('4');
        btnPenyakitCari.setToolTipText("Alt+4");
        btnPenyakitCari.setName("btnPenyakitCari"); // NOI18N
        btnPenyakitCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPenyakitCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenyakitCariActionPerformed(evt);
            }
        });
        btnPenyakitCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPenyakitCariKeyPressed(evt);
            }
        });
        panelGlass9.add(btnPenyakitCari);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TCariKeyTyped(evt);
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelGlass2.setName("panelGlass2"); // NOI18N
        panelGlass2.setPreferredSize(new java.awt.Dimension(711, 77));
        panelGlass2.setLayout(null);

        jLabel3.setText("Penyakit Yang Diderita :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass2.add(jLabel3);
        jLabel3.setBounds(-8, 12, 160, 23);

        jLabel4.setText("Obat/Alkes Dibutuhkan :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass2.add(jLabel4);
        jLabel4.setBounds(-8, 42, 160, 23);

        TPenyakit.setEditable(false);
        TPenyakit.setHighlighter(null);
        TPenyakit.setName("TPenyakit"); // NOI18N
        TPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenyakitKeyPressed(evt);
            }
        });
        panelGlass2.add(TPenyakit);
        TPenyakit.setBounds(257, 12, 250, 23);

        nmobat.setEditable(false);
        nmobat.setName("nmobat"); // NOI18N
        nmobat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmobatKeyPressed(evt);
            }
        });
        panelGlass2.add(nmobat);
        nmobat.setBounds(257, 42, 250, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Referensi/Sumber Informasi :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass2.add(jLabel12);
        jLabel12.setBounds(569, 12, 180, 23);

        TRef.setHighlighter(null);
        TRef.setName("TRef"); // NOI18N
        TRef.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRefKeyPressed(evt);
            }
        });
        panelGlass2.add(TRef);
        TRef.setBounds(569, 42, 258, 23);

        btnpenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnpenyakit.setMnemonic('1');
        btnpenyakit.setToolTipText("Alt+1");
        btnpenyakit.setName("btnpenyakit"); // NOI18N
        btnpenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpenyakitActionPerformed(evt);
            }
        });
        btnpenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnpenyakitKeyPressed(evt);
            }
        });
        panelGlass2.add(btnpenyakit);
        btnpenyakit.setBounds(510, 12, 28, 23);

        btnobat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnobat.setMnemonic('2');
        btnobat.setToolTipText("Alt+2");
        btnobat.setName("btnobat"); // NOI18N
        btnobat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnobatActionPerformed(evt);
            }
        });
        btnobat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnobatKeyPressed(evt);
            }
        });
        panelGlass2.add(btnobat);
        btnobat.setBounds(510, 42, 28, 23);

        kdpenyakit.setHighlighter(null);
        kdpenyakit.setName("kdpenyakit"); // NOI18N
        kdpenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenyakitKeyPressed(evt);
            }
        });
        panelGlass2.add(kdpenyakit);
        kdpenyakit.setBounds(155, 12, 100, 23);

        kdobat.setHighlighter(null);
        kdobat.setName("kdobat"); // NOI18N
        kdobat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdobatKeyPressed(evt);
            }
        });
        panelGlass2.add(kdobat);
        kdobat.setBounds(155, 42, 100, 23);

        internalFrame1.add(panelGlass2, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenyakitKeyPressed
        //Valid.pindah(evt,TCari,cmbBangsal);
}//GEN-LAST:event_TPenyakitKeyPressed

    private void nmobatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmobatKeyPressed
        //Valid.pindah(evt,TKd,BtnSimpan);
}//GEN-LAST:event_nmobatKeyPressed

    private void TRefKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRefKeyPressed
        Valid.pindah(evt,kdobat,BtnSimpan);
}//GEN-LAST:event_TRefKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TPenyakit.getText().trim().equals("")){
            Valid.textKosong(TPenyakit,"Penyakit Yang Diderita");
        }else if(nmobat.getText().trim().equals("")){
            Valid.textKosong(nmobat,"Obat/Penawar Penyakit");
        }else if(TRef.getText().trim().equals("")){
            Valid.textKosong(TRef,"Referensi/Sumber Informasi");
        }else{
            try{
                java.sql.Statement stat=koneksi.createStatement();
                ResultSet rs=stat.executeQuery("select * from obat_penyakit where kd_penyakit='"+kdpenyakit.getText()+"' and kode_brng='"+kdobat.getText()+"'");
                rs.last();
                if(rs.getRow()>=1){
                    JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan. Ada data yang sama disimpan sebelumnya...!");
                }else if(rs.getRow()==0){
                    //menyimpan-------------------------------------------------
                    Sequel.menyimpan("obat_penyakit","'"+kdpenyakit.getText()+"','"+kdobat.getText()+"','"+TRef.getText()+"'","Data Sama");
                    //----------------------------------------------------------
                    tampil();
                    emptTeks();
                }
                kdpenyakit.requestFocus();
            }catch(SQLException e){
                System.out.println("Notifikasi : "+e);
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TRef,BtnBatal);
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
        for(i=0;i<tbObatPenyakit.getRowCount();i++){ 
            if(tbObatPenyakit.getValueAt(i,0).toString().equals("true")){
                Sequel.queryu2("delete from obat_penyakit where kd_penyakit=? and kode_brng=?",2,new String[]{
                    tbObatPenyakit.getValueAt(i,1).toString(),tbObatPenyakit.getValueAt(i,7).toString()
                });
            }
        } 
        tampil();
        emptTeks();
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
        if((! TCari.getText().trim().equals(""))&&(KtgCari.getText().equals(""))&&(PenyakitCari.getText().equals(""))){
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
                sql=" nm_kategori like '%"+KtgCari.getText()+"%' "+
                " and nm_penyakit like '%"+PenyakitCari.getText()+"%' ";

                Valid.MyReportqry("rptObtPenyakit.jasper","report","::[ Data Obat Penyakit ]::",
                        "select obat_penyakit.kd_penyakit,nm_penyakit,ciri_ciri,penyakit.keterangan, "+
                        "nm_kategori,ciri_umum,obat_penyakit.kode_brng,nama_brng,jenis.nama,h_beli,referensi "+
                        "from obat_penyakit inner join penyakit inner join kategori_penyakit inner join databarang inner join jenis "+
                        "on penyakit.kd_ktg=kategori_penyakit.kd_ktg and "+
                        "databarang.kdjns=jenis.kdjns and "+
                        "obat_penyakit.kd_penyakit=penyakit.kd_penyakit and "+
                        "obat_penyakit.kode_brng=databarang.kode_brng where "+
                         sql+"and obat_penyakit.kd_penyakit like '%"+TCari.getText().trim()+"%' or "+
                         sql+"and nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                         sql+"and ciri_ciri like '%"+TCari.getText().trim()+"%' or "+
                         sql+"and penyakit.keterangan like '%"+TCari.getText().trim()+"%' or "+
                         sql+"and nm_kategori like '%"+TCari.getText().trim()+"%' or "+
                         sql+"and ciri_umum like '%"+TCari.getText().trim()+"%' or "+
                         sql+"and obat_penyakit.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                         sql+"and nama_brng like '%"+TCari.getText().trim()+"%' or "+
                         sql+"and jenis.nama like '%"+TCari.getText().trim()+"%' or "+
                         sql+"and referensi like '%"+TCari.getText().trim()+"%' "+
                         "order by obat_penyakit.kd_penyakit ",param);

                
            
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
        }else{Valid.pindah(evt,BtnAll,KtgCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        KtgCari.setText("");
        PenyakitCari.setText("");
        tampil();
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
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyTyped
        if((! KtgCari.getText().equals("")) ||(! PenyakitCari.getText().equals(""))){
            KtgCari.setText("");
            PenyakitCari.setText("");
        }
    }//GEN-LAST:event_TCariKeyTyped

    private void btnpenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpenyakitActionPerformed
        akses.setform("DlgObatPenyakit");
        penyakit.isCek();
        penyakit.emptTeks();
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
}//GEN-LAST:event_btnpenyakitActionPerformed

    private void btnpenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnpenyakitKeyPressed
        Valid.pindah(evt,kdpenyakit,kdobat);
}//GEN-LAST:event_btnpenyakitKeyPressed

    private void btnobatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnobatActionPerformed
        akses.setform("DlgObatPenyakit");
        barang.isCek();
        barang.emptTeks();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setVisible(true);
}//GEN-LAST:event_btnobatActionPerformed

    private void btnobatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnobatKeyPressed
        Valid.pindah(evt,kdobat,BtnSimpan);
}//GEN-LAST:event_btnobatKeyPressed

    private void tbObatPenyakitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatPenyakitMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatPenyakitMouseClicked

    private void tbObatPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatPenyakitKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatPenyakitKeyPressed

private void kdpenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenyakitKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_penyakit from penyakit where  kd_penyakit=?", TPenyakit,kdpenyakit.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnpenyakitActionPerformed(null);
        }else{
             Valid.pindah(evt,TCari,kdobat);
        }
}//GEN-LAST:event_kdpenyakitKeyPressed

private void kdobatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdobatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
             Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmobat,kdobat.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnobatActionPerformed(null);
        }else{
             Valid.pindah(evt,kdpenyakit,TRef);
        }
}//GEN-LAST:event_kdobatKeyPressed

private void KtgCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KtgCariKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_UP){
       btnKategoriCariActionPerformed(null);
   }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       tampil();
   }
}//GEN-LAST:event_KtgCariKeyPressed

private void btnKategoriCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriCariActionPerformed
        akses.setform("DlgObatPenyakit");
        ktg.emptTeks();        
        ktg.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ktg.setLocationRelativeTo(internalFrame1);
        ktg.setVisible(true);
}//GEN-LAST:event_btnKategoriCariActionPerformed

private void btnKategoriCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKategoriCariKeyPressed
   Valid.pindah(evt,btnPenyakitCari,TCari);
}//GEN-LAST:event_btnKategoriCariKeyPressed

private void PenyakitCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitCariKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_UP){
       btnPenyakitCariActionPerformed(null);
   }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       tampil();
   }
}//GEN-LAST:event_PenyakitCariKeyPressed

private void btnPenyakitCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenyakitCariActionPerformed
        akses.setform("DlgObatPenyakit");
        penyakit.emptTeks();
        penyakit.isCek();
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
}//GEN-LAST:event_btnPenyakitCariActionPerformed

private void btnPenyakitCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPenyakitCariKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_btnPenyakitCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgObatPenyakit dialog = new DlgObatPenyakit(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox KtgCari;
    private widget.Label LCount;
    private widget.TextBox PenyakitCari;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TPenyakit;
    private widget.TextBox TRef;
    private widget.Button btnKategoriCari;
    private widget.Button btnPenyakitCari;
    private widget.Button btnobat;
    private widget.Button btnpenyakit;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private widget.TextBox kdobat;
    private widget.TextBox kdpenyakit;
    private widget.TextBox nmobat;
    private widget.panelisi panelGlass2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObatPenyakit;
    // End of variables declaration//GEN-END:variables


    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            sql=" nm_kategori like '%"+KtgCari.getText()+"%' "+
                " and nm_penyakit like '%"+PenyakitCari.getText()+"%' ";
            ps=koneksi.prepareStatement("select obat_penyakit.kd_penyakit,nm_penyakit,ciri_ciri,penyakit.keterangan, "+
                   "nm_kategori,ciri_umum,obat_penyakit.kode_brng,nama_brng,jenis.nama,h_beli,referensi "+
                   "from obat_penyakit inner join penyakit inner join kategori_penyakit inner join databarang inner join jenis "+
                   "on penyakit.kd_ktg=kategori_penyakit.kd_ktg and "+
                   "databarang.kdjns=jenis.kdjns and "+
                   "obat_penyakit.kd_penyakit=penyakit.kd_penyakit and "+
                   "obat_penyakit.kode_brng=databarang.kode_brng where "+
                    sql+"and obat_penyakit.kd_penyakit like '%"+TCari.getText().trim()+"%' or "+
                    sql+"and nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                    sql+"and ciri_ciri like '%"+TCari.getText().trim()+"%' or "+
                    sql+"and penyakit.keterangan like '%"+TCari.getText().trim()+"%' or "+
                    sql+"and nm_kategori like '%"+TCari.getText().trim()+"%' or "+
                    sql+"and ciri_umum like '%"+TCari.getText().trim()+"%' or "+
                    sql+"and obat_penyakit.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                    sql+"and nama_brng like '%"+TCari.getText().trim()+"%' or "+
                    sql+"and jenis.nama like '%"+TCari.getText().trim()+"%' or "+
                    sql+"and referensi like '%"+TCari.getText().trim()+"%' "+
                    "order by obat_penyakit.kd_penyakit ");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getDouble(10),rs.getString(11)
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

    public void emptTeks() {
        TRef.setText("");
        kdpenyakit.setText("");
        TPenyakit.setText("");
        kdobat.setText("");
        nmobat.setText("");
        TPenyakit.setText("");
        kdpenyakit.requestFocus();
    }

    private void getData() {
        int row=tbObatPenyakit.getSelectedRow();
        if(row!= -1){
            kdpenyakit.setText(tbObatPenyakit.getValueAt(row,1).toString());
            TPenyakit.setText(tbObatPenyakit.getValueAt(row,2).toString());
            kdobat.setText(tbObatPenyakit.getValueAt(row,7).toString());
            nmobat.setText(tbObatPenyakit.getValueAt(row,8).toString());
            TRef.setText(tbObatPenyakit.getValueAt(row,11).toString());
        }
    }  
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getobat_penyakit());
        BtnHapus.setEnabled(akses.getobat_penyakit());
        BtnPrint.setEnabled(akses.getobat_penyakit());
    }
}

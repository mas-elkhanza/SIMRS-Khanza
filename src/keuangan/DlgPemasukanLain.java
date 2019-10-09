/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgResepObat.java
 *
 * Created on 31 Mei 10, 11:27:40
 */

package keuangan;
import kepegawaian.DlgCariPetugas;
import simrskhanza.*;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


/**
 *
 * @author perpustakaan
 */
public final class DlgPemasukanLain extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private PreparedStatement ps,psakun;
    private ResultSet rs;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgKategoriPemasukan kategori=new DlgKategoriPemasukan(null,false);
    private double total=0;

    /** Creates new form DlgResepObat 
     *@param parent
     *@param modal*/
    public DlgPemasukanLain(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"Nomor","Tanggal","Kategori","Petugas","Pemasukan","Terima Dari","Keperluan","Kode","NIP"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, 
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbResep.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());

        Keperluan.setDocument(new batasInput((byte)70).getKata(Keperluan));
        Keterangan.setDocument(new batasInput((byte)50).getKata(Keterangan));
        KdPtg.setDocument(new batasInput((byte)20).getKata(KdPtg));
        pemasukan.setDocument(new batasInput((byte)15).getKata(pemasukan));
        Nomor.setDocument(new batasInput((byte)15).getKata(Nomor));
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPemasukanLain")){
                    if(petugas.getTable().getSelectedRow()!= -1){        
                        KdPtg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPtg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        KdPtg.requestFocus();
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
        
        
        kategori.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kategori.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPemasukanLain")){
                    if(kategori.getTabel().getSelectedRow()!= -1){        
                        KdKategori.setText(kategori.getTabel().getValueAt(kategori.getTabel().getSelectedRow(),0).toString());
                        NmKategori.setText(kategori.getTabel().getValueAt(kategori.getTabel().getSelectedRow(),1).toString());
                        KdKategori.requestFocus();
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
        
        ChkInput.setSelected(false);        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        cetakkwitansi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbResep = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        Keperluan = new widget.TextBox();
        KdPtg = new widget.TextBox();
        NmPtg = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel13 = new widget.Label();
        btnPetugas = new widget.Button();
        jLabel14 = new widget.Label();
        KdKategori = new widget.TextBox();
        NmKategori = new widget.TextBox();
        btnKategori = new widget.Button();
        Tanggal = new widget.Tanggal();
        jLabel8 = new widget.Label();
        jLabel11 = new widget.Label();
        pemasukan = new widget.TextBox();
        Keterangan = new widget.TextBox();
        jLabel12 = new widget.Label();
        Nomor = new widget.TextBox();
        jLabel4 = new widget.Label();
        ChkInput = new widget.CekBox();

        Popup.setName("Popup"); // NOI18N

        cetakkwitansi.setBackground(new java.awt.Color(255, 255, 254));
        cetakkwitansi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        cetakkwitansi.setForeground(java.awt.Color.darkGray);
        cetakkwitansi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        cetakkwitansi.setText("Cetak Kwitansi Penerimaan");
        cetakkwitansi.setName("cetakkwitansi"); // NOI18N
        cetakkwitansi.setPreferredSize(new java.awt.Dimension(250, 25));
        cetakkwitansi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakkwitansiActionPerformed(evt);
            }
        });
        Popup.add(cetakkwitansi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemasukan Lain-Lain ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbResep.setAutoCreateRowSorter(true);
        tbResep.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbResep.setComponentPopupMenu(Popup);
        tbResep.setName("tbResep"); // NOI18N
        tbResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepMouseClicked(evt);
            }
        });
        tbResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbResep);

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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 30));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(52, 30));
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
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2019" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2019" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(312, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(440, 128));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 77));
        FormInput.setLayout(null);

        Keperluan.setHighlighter(null);
        Keperluan.setName("Keperluan"); // NOI18N
        Keperluan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeperluanKeyPressed(evt);
            }
        });
        FormInput.add(Keperluan);
        Keperluan.setBounds(545, 70, 190, 23);

        KdPtg.setHighlighter(null);
        KdPtg.setName("KdPtg"); // NOI18N
        KdPtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPtgKeyPressed(evt);
            }
        });
        FormInput.add(KdPtg);
        KdPtg.setBounds(83, 42, 100, 23);

        NmPtg.setEditable(false);
        NmPtg.setHighlighter(null);
        NmPtg.setName("NmPtg"); // NOI18N
        FormInput.add(NmPtg);
        NmPtg.setBounds(185, 42, 221, 23);

        jLabel3.setText("Terima Dari :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 72, 80, 23);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 42, 80, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('3');
        btnPetugas.setToolTipText("Alt+3");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(409, 42, 28, 23);

        jLabel14.setText("Ketegori :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 12, 80, 23);

        KdKategori.setHighlighter(null);
        KdKategori.setName("KdKategori"); // NOI18N
        KdKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKategoriKeyPressed(evt);
            }
        });
        FormInput.add(KdKategori);
        KdKategori.setBounds(83, 12, 70, 23);

        NmKategori.setEditable(false);
        NmKategori.setHighlighter(null);
        NmKategori.setName("NmKategori"); // NOI18N
        FormInput.add(NmKategori);
        NmKategori.setBounds(155, 12, 251, 23);

        btnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKategori.setMnemonic('3');
        btnKategori.setToolTipText("Alt+3");
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
        btnKategori.setBounds(409, 12, 28, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-02-2019 09:00:40" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(100, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(545, 10, 190, 23);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(470, 10, 73, 23);

        jLabel11.setText(" Keperluan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(470, 70, 73, 23);

        pemasukan.setText("0");
        pemasukan.setHighlighter(null);
        pemasukan.setName("pemasukan"); // NOI18N
        pemasukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pemasukanKeyPressed(evt);
            }
        });
        FormInput.add(pemasukan);
        pemasukan.setBounds(563, 40, 172, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(83, 72, 170, 23);

        jLabel12.setText("Pemasukan : Rp.");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(470, 40, 93, 23);

        Nomor.setHighlighter(null);
        Nomor.setName("Nomor"); // NOI18N
        Nomor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorKeyPressed(evt);
            }
        });
        FormInput.add(Nomor);
        Nomor.setBounds(317, 72, 120, 23);

        jLabel4.setText("Nomor :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(264, 72, 50, 23);

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

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(Keperluan.getText().trim().equals("")){
            Valid.textKosong(Keperluan,"Keperluan");
        }else if(Nomor.getText().trim().equals("")){
            Valid.textKosong(Nomor,"Nomor Transaksi");
        }else if(KdPtg.getText().trim().equals("")||NmPtg.getText().trim().equals("")){
            Valid.textKosong(KdPtg,"Petugas Keuangan");
        }else if(KdKategori.getText().trim().equals("")||NmKategori.getText().trim().equals("")){
            Valid.textKosong(KdKategori,"Kategori Pemasukkan");
        }else if(pemasukan.getText().trim().equals("")||pemasukan.getText().trim().equals("0")){
            Valid.textKosong(pemasukan,"Pengeluaran");
        }else{
                        
            try {
                if(Sequel.menyimpantf("pemasukan_lain","?,?,?,?,?,?,?","Pemasukan",7,new String[]{
                    Nomor.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    KdKategori.getText(),pemasukan.getText(),KdPtg.getText(),Keterangan.getText(),Keperluan.getText()
                })==true){
                    Sequel.queryu("delete from tampjurnal");
                    psakun=koneksi.prepareStatement(
                        "select kd_rek,'Akun',kd_rek2,'Kontra Akun' from kategori_pemasukan_lain where kode_kategori=?");
                    try {
                        psakun.setString(1,KdKategori.getText());
                        rs=psakun.executeQuery();
                        if(rs.next()){
                            Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{rs.getString(1),rs.getString(2),"0",pemasukan.getText()});
                            Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{rs.getString(3),rs.getString(4),pemasukan.getText(),"0"}); 
                            jur.simpanJurnal(Nomor.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),"U","PEMASUKAN LAIN-LAIN"+", OLEH "+akses.getkode());
                        }
                    } catch (Exception e) {
                        System.out.println("Jurnal : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(psakun!=null){
                            psakun.close();
                        }
                    }
                    Sequel.menyimpan2("tagihan_sadewa","'"+Nomor.getText()+"','-','"+Keterangan.getText()+"','-',concat('"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+
                        "',' ',CURTIME()),'Pelunasan','"+total+"','"+pemasukan.getText()+"','Sudah','"+akses.getkode()+"'","No.Transaksi");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }            
            
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,pemasukan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             Keterangan.requestFocus();
        }else if(Keterangan.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(Keterangan.getText().trim().equals(""))){
            if(tbResep.getSelectedRow()>-1){
                                
                try {
                    if(Sequel.queryu2tf("delete from pemasukan_lain where no_masuk=?",1,new String[]{
                        tbResep.getValueAt(tbResep.getSelectedRow(),0).toString()
                    })==true){
                        Sequel.queryu("delete from tampjurnal");
                        psakun=koneksi.prepareStatement(
                            "select kd_rek,'Akun',kd_rek2,'Kontra Akun' from kategori_pemasukan_lain where kode_kategori=?");
                        try{
                            psakun.setString(1,KdKategori.getText());
                            rs=psakun.executeQuery();
                            if(rs.next()){
                                Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{rs.getString(1),rs.getString(2),pemasukan.getText(),"0"});
                                Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{rs.getString(3),rs.getString(4),"0",pemasukan.getText()}); 
                                jur.simpanJurnal("-",Valid.SetTgl(Tanggal.getSelectedItem()+""),"U","PEMBATALAN PEMASUKAN LAIN-LAIN"+", OLEH "+akses.getkode());
                            } 
                        } catch (Exception e) {
                            System.out.println("Jurnal : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(psakun!=null){
                                psakun.close();
                            }
                        }
                        Valid.hapusTable(tabMode,Nomor,"tagihan_sadewa","no_nota");
                    }                                           
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
                
                tampil();
                emptTeks();
            }                
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal,BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptPemasukanLain.jasper","report","::[ Data Pemasukan Lain-Lain ]::",
                "select pemasukan_lain.tanggal, pemasukan_lain.keterangan, pemasukan_lain.keperluan, pemasukan_lain.besar, pemasukan_lain.nip, "+
                    "petugas.nama,pemasukan_lain.kode_kategori,kategori_pemasukan_lain.nama_kategori "+
                    "from pemasukan_lain inner join petugas inner join kategori_pemasukan_lain on pemasukan_lain.nip=petugas.nip "+
                    "and pemasukan_lain.kode_kategori=kategori_pemasukan_lain.kode_kategori where "+
                        "pemasukan_lain.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pemasukan_lain.keperluan like '%"+TCari.getText().trim()+"%' or "+
                    "pemasukan_lain.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pemasukan_lain.keterangan like '%"+TCari.getText().trim()+"%' or "+
                    "pemasukan_lain.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pemasukan_lain.nip like '%"+TCari.getText().trim()+"%' or "+
                    "pemasukan_lain.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                    "pemasukan_lain.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pemasukan_lain.kode_kategori like '%"+TCari.getText().trim()+"%' or "+
                    "pemasukan_lain.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and kategori_pemasukan_lain.nama_kategori like '%"+TCari.getText().trim()+"%' order by pemasukan_lain.tanggal ",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
            Valid.pindah(evt, BtnCari, NmPtg);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbResepMouseClicked

    private void tbResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbResepKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,Keperluan,pemasukan);
    }//GEN-LAST:event_TanggalKeyPressed

    private void pemasukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pemasukanKeyPressed
        Valid.pindah(evt,Tanggal,BtnSimpan);
    }//GEN-LAST:event_pemasukanKeyPressed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,KdPtg,BtnSimpan);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgPemasukanLain");
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.isCek();
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void KdPtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPtgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",NmPtg,KdPtg.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,KdKategori,Keperluan);
        }
    }//GEN-LAST:event_KdPtgKeyPressed

    private void KeperluanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeperluanKeyPressed
        Valid.pindah(evt,KdPtg,Tanggal);
    }//GEN-LAST:event_KeperluanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void KdKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKategoriKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_kategori from kategori_pemasukan_lain where kode_kategori=?",NmKategori,KdKategori.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKategoriActionPerformed(null);
        }else{            
            Valid.pindah(evt,TCari,KdPtg);
        }
    }//GEN-LAST:event_KdKategoriKeyPressed

    private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
        akses.setform("DlgPemasukanLain");
        kategori.emptTeks();
        kategori.tampil();
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setVisible(true);
    }//GEN-LAST:event_btnKategoriActionPerformed

    private void btnKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKategoriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKategoriKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKeyPressed

    private void cetakkwitansiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakkwitansiActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(Keperluan.getText().trim().equals("")){
            Valid.textKosong(Keperluan,"Keperluan");
        }else if(KdPtg.getText().trim().equals("")||NmPtg.getText().trim().equals("")){
            Valid.textKosong(KdPtg,"Petugas");
        }else if(KdKategori.getText().trim().equals("")||NmKategori.getText().trim().equals("")){
            Valid.textKosong(KdKategori,"Kode Kategori");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            tbResep.requestFocus();
        }else {
            
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                try {
                    if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                           Sequel.menyimpan("temporary","'0','"+
                                   tabMode.getValueAt(i,0).toString()+"','"+
                                   tabMode.getValueAt(i,1).toString()+"','"+
                                   tabMode.getValueAt(i,2).toString()+"','"+
                                   tabMode.getValueAt(i,3).toString()+"','"+
                                   tabMode.getValueAt(i,4).toString()+"','"+
                                   tabMode.getValueAt(i,5).toString()+"','"+
                                   tabMode.getValueAt(i,6).toString()+"','"+
                                   tabMode.getValueAt(i,8).toString()+"','"+
                                   tabMode.getValueAt(i,10).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penjualan"); 
                    }
                } catch (Exception e) {
                }                
            }
            
            Valid.panggilUrl("billing/LaporanKeuangan.php?kode="+Nomor.getText()+"&Tanggal="+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"&nama="+NmKategori.getText().replaceAll(" ","_")+"&petugas="+NmPtg.getText().replaceAll(" ","_")+"&keperluan="+Keperluan.getText().replaceAll(" ","_")+"&keterangan="+Keterangan.getText().replaceAll(" ","_")+"&nom="+pemasukan.getText().replaceAll(" ","_")+"&kategori="+NmKategori.getText().replaceAll(" ","_"));
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_cetakkwitansiActionPerformed

    private void NomorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomorKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPemasukanLain dialog = new DlgPemasukanLain(new javax.swing.JFrame(), true);
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
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdKategori;
    private widget.TextBox KdPtg;
    private widget.TextBox Keperluan;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.TextBox NmKategori;
    private widget.TextBox NmPtg;
    private widget.TextBox Nomor;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnKategori;
    private widget.Button btnPetugas;
    private javax.swing.JMenuItem cetakkwitansi;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TextBox pemasukan;
    private widget.Table tbResep;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{   
            ps=koneksi.prepareStatement(
                "select pemasukan_lain.no_masuk,pemasukan_lain.tanggal, pemasukan_lain.keterangan, pemasukan_lain.keperluan, pemasukan_lain.besar, pemasukan_lain.nip, "+
                "petugas.nama,pemasukan_lain.kode_kategori,kategori_pemasukan_lain.nama_kategori "+
                "from pemasukan_lain inner join petugas inner join kategori_pemasukan_lain on pemasukan_lain.nip=petugas.nip "+
                "and pemasukan_lain.kode_kategori=kategori_pemasukan_lain.kode_kategori where "+
                "pemasukan_lain.tanggal between ? and ? and pemasukan_lain.keterangan like ? or "+
                "pemasukan_lain.tanggal between ? and ? and pemasukan_lain.nip like ? or "+
                "pemasukan_lain.tanggal between ? and ? and petugas.nama like ? or "+
                "pemasukan_lain.tanggal between ? and ? and pemasukan_lain.kode_kategori like ? or "+
                "pemasukan_lain.tanggal between ? and ? and kategori_pemasukan_lain.nama_kategori like ? order by pemasukan_lain.tanggal");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                total=0;
                while(rs.next()){                
                    tabMode.addRow(new Object[]{
                        rs.getString("no_masuk"),rs.getString("tanggal"),rs.getString("kode_kategori")+" "+rs.getString("nama_kategori"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getDouble("besar"),rs.getString("keterangan"),rs.getString("keperluan"),
                        rs.getString("kode_kategori"),rs.getString("nip")
                    });
                    total=total+rs.getDouble("besar");
                }
            } catch (Exception e){
                System.out.println(e);
            }finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
                
            if(total>0){
                tabMode.addRow(new Object[]{"",">>","Jumlah Total Pemasukan :","",total,"","",""}); 
            }        
            LCount.setText((""+(tabMode.getRowCount()-1)).replaceAll("-1","0"));                        
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }        
    }

    public void emptTeks() {
        pemasukan.setText("0");
        Keperluan.setText("");
        KdKategori.setText("");
        NmKategori.setText("");
        Tanggal.setDate(new Date());
        KdKategori.requestFocus();
        autoNomor();
    }

    private void getData() {
        if(tbResep.getSelectedRow()!= -1){
            Nomor.setText(tbResep.getValueAt(tbResep.getSelectedRow(),0).toString().replaceAll(tbResep.getValueAt(tbResep.getSelectedRow(),6).toString()+" ",""));
            NmKategori.setText(tbResep.getValueAt(tbResep.getSelectedRow(),2).toString().replaceAll(tbResep.getValueAt(tbResep.getSelectedRow(),6).toString()+" ",""));
            NmPtg.setText(tbResep.getValueAt(tbResep.getSelectedRow(),3).toString().replaceAll(tbResep.getValueAt(tbResep.getSelectedRow(),8).toString()+" ",""));
            pemasukan.setText(String.valueOf(tbResep.getValueAt(tbResep.getSelectedRow(),4).toString()));
            Keterangan.setText(tbResep.getValueAt(tbResep.getSelectedRow(),5).toString());
            Keperluan.setText(tbResep.getValueAt(tbResep.getSelectedRow(),6).toString());
            KdKategori.setText(tbResep.getValueAt(tbResep.getSelectedRow(),7).toString());
            KdPtg.setText(tbResep.getValueAt(tbResep.getSelectedRow(),8).toString());
            Valid.SetTgl(Tanggal,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString());
        }
    }
   
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,128));
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
        if(akses.getjml2()>=1){
            KdPtg.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPtg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getpemasukan_lain());
            BtnHapus.setEnabled(akses.getpemasukan_lain());
            BtnPrint.setEnabled(akses.getpemasukan_lain());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPtg,KdPtg.getText());
        }      
        
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_masuk,3),signed)),0) from pemasukan_lain where tanggal like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"%' ",
                "PL"+Tanggal.getSelectedItem().toString().substring(8,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,Nomor); 
    }
}

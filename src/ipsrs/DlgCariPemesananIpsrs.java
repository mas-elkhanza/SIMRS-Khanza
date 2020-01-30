package ipsrs;
import inventory.riwayatobat;
import inventory.*;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import keuangan.DlgBayarPemesanan;
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;
import keuangan.DlgBayarPemesananNonMedis;

public class DlgCariPemesananIpsrs extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private riwayatobat Trackobat=new riwayatobat();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    public  DlgSuplierIPSRS suplier=new DlgSuplierIPSRS(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgBarangIPSRS barang=new DlgBarangIPSRS(null,false);
    private PreparedStatement ps,ps2,pscaripesan,psipsrsdetailpesan;
    private ResultSet rs,rs2;
    private double tagihan=0;
    private Jurnal jur=new Jurnal();

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPemesananIpsrs(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Faktur","Suplier","Petugas","Jml","Harga Beli(Rp)","SubTotal(Rp)",
                    "Disk(%)","Bsr.Disk(Rp)","Total(Rp)"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(35);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoFaktur.setDocument(new batasInput((byte)25).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        kdjenis.setDocument(new batasInput((byte)4).getKata(kdjenis));
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
        
        suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPemesananIPSRS")){
                    if(suplier.getTable().getSelectedRow()!= -1){                   
                        kdsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),0).toString());                    
                        nmsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
                    }  
                    kdsup.requestFocus();
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
        
        suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariPemesananIPSRS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        suplier.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });         
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPemesananIPSRS")){
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }            
                    kdptg.requestFocus();
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
                if(akses.getform().equals("DlgCariPemesananIPSRS")){
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),0).toString());                    
                        nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());
                    }   
                    kdbar.requestFocus();
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
                if(akses.getform().equals("DlgCariPemesananIPSRS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        barang.jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPemesananIPSRS")){
                    if(barang.jenis.getTable().getSelectedRow()!= -1){                   
                        kdjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),0).toString());                    
                        nmjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),1).toString());
                    }   
                    kdjenis.requestFocus();
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
        
        try {
            pscaripesan=koneksi.prepareStatement("select no_faktur, tagihan, tgl_faktur,status from ipsrspemesanan where no_faktur=?");
            psipsrsdetailpesan=koneksi.prepareStatement("select kode_brng,jumlah from ipsrsdetailpesan where no_faktur=? ");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppHapus = new javax.swing.JMenuItem();
        ppBayar = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label24 = new widget.Label();
        kdjenis = new widget.TextBox();
        btnSatuan = new widget.Button();
        nmjenis = new widget.TextBox();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label11 = new widget.Label();
        TglBeli1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label12 = new widget.Label();
        TglBeli2 = new widget.Tanggal();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50,50,50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Data Penerimaan");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(165, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        ppBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBayar.setForeground(new java.awt.Color(50,50,50));
        ppBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBayar.setText("Bayar Tagihan");
        ppBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBayar.setName("ppBayar"); // NOI18N
        ppBayar.setPreferredSize(new java.awt.Dimension(165, 25));
        ppBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBayarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBayar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Penerimaan Barang Non Medis dan Penunjang ( Lab & RO ) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

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
        panelisi1.add(BtnCari);

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(120, 30));
        panelisi1.add(LTotal);

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
        panelisi4.setLayout(null);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(295, 10, 90, 23);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);
        kdbar.setBounds(389, 10, 110, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(501, 10, 230, 23);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang.setMnemonic('4');
        btnBarang.setToolTipText("Alt+4");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        panelisi4.add(btnBarang);
        btnBarang.setBounds(734, 10, 28, 23);

        label24.setText("Jenis :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(48, 23));
        panelisi4.add(label24);
        label24.setBounds(0, 10, 45, 23);

        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });
        panelisi4.add(kdjenis);
        kdjenis.setBounds(48, 10, 53, 23);

        btnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSatuan.setMnemonic('3');
        btnSatuan.setToolTipText("Alt+3");
        btnSatuan.setName("btnSatuan"); // NOI18N
        btnSatuan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatuanActionPerformed(evt);
            }
        });
        panelisi4.add(btnSatuan);
        btnSatuan.setBounds(273, 10, 28, 23);

        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        nmjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmjenisKeyPressed(evt);
            }
        });
        panelisi4.add(nmjenis);
        nmjenis.setBounds(103, 10, 167, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Faktur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 80, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(84, 10, 219, 23);

        label11.setText("Tgl.Datang :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 80, 23);

        TglBeli1.setDisplayFormat("dd-MM-yyyy");
        TglBeli1.setName("TglBeli1"); // NOI18N
        TglBeli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli1KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli1);
        TglBeli1.setBounds(84, 40, 95, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(305, 10, 80, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(305, 40, 80, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(389, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(389, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(471, 10, 260, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(471, 40, 260, 23);

        btnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSuplier.setMnemonic('1');
        btnSuplier.setToolTipText("Alt+1");
        btnSuplier.setName("btnSuplier"); // NOI18N
        btnSuplier.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierActionPerformed(evt);
            }
        });
        panelisi3.add(btnSuplier);
        btnSuplier.setBounds(734, 10, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(734, 40, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(179, 40, 27, 23);

        TglBeli2.setDisplayFormat("dd-MM-yyyy");
        TglBeli2.setName("TglBeli2"); // NOI18N
        TglBeli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli2KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli2);
        TglBeli2.setBounds(208, 40, 95, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        suplier.dispose();
        petugas.dispose();
        barang.jenis.dispose();
        barang.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,kdbar);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        akses.setform("DlgCariPemesananIPSRS");
        suplier.emptTeks();
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgCariPemesananIPSRS");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void TglBeli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli1KeyPressed
        Valid.pindah(evt,NoFaktur,kdsup);
    }//GEN-LAST:event_TglBeli1KeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("DlgCariPemesananIPSRS");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        akses.setform("DlgCariPemesananIPSRS");
        barang.jenis.emptTeks();
        barang.jenis.isCek();
        barang.jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.jenis.setLocationRelativeTo(internalFrame1);
        barang.jenis.setAlwaysOnTop(false);
        barang.jenis.setVisible(true);
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from ipsrssuplier where kode_suplier=?", nmsup,kdsup.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from ipsrssuplier where kode_suplier=?", nmsup,kdsup.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_suplier from ipsrssuplier where kode_suplier=?", nmsup,kdsup.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSuplierActionPerformed(null);
        }
    }//GEN-LAST:event_kdsupKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnKeluar, kdsup);
    }//GEN-LAST:event_NoFakturKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());     
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            kdbar.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from ipsrsbarang where kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){            
            Sequel.cariIsi("select nama_brng from ipsrsbarang where kode_brng=?", nmbar,kdbar.getText());
            kdjenis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){            
            Sequel.cariIsi("select nama_brng from ipsrsbarang where kode_brng=?", nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_jenis from ipsrsjenisbarang  where kd_jenis=?", nmjenis,kdjenis.getText());         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_jenis from ipsrsjenisbarang  where kd_jenis=?", nmjenis,kdjenis.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_jenis from ipsrsjenisbarang  where kd_jenis=?", nmjenis,kdjenis.getText());
            kdbar.requestFocus();   
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSatuanActionPerformed(null);
        }
    }//GEN-LAST:event_kdjenisKeyPressed

    private void TglBeli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBeli2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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
        NoFaktur.setText("");
        kdbar.setText("");
        nmbar.setText("");
        kdjenis.setText("");
        nmjenis.setText("");
        kdsup.setText("");
        nmsup.setText("");
        kdptg.setText("");
        nmptg.setText("");
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
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penerimaan"); 
            }
            Sequel.menyimpan("temporary","'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penerimaan"); 
            Sequel.menyimpan("temporary","'0','Jml.Total :','','','','','','','','"+LTotal.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penerimaan"); 
            
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptPemesananIPSRS.jasper","report","::[ Transaksi Penerimaan Barang Non Medis ]::",param);
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

    private void nmjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmjenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmjenisKeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
  if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString().trim().equals("")){
      Valid.textKosong(TCari,"No.Faktur");
  }else{
     try {
         
         pscaripesan.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
         rs=pscaripesan.executeQuery();
         while(rs.next()){
             psipsrsdetailpesan.setString(1,rs.getString(1));
             rs2=psipsrsdetailpesan.executeQuery();
             while(rs2.next()){
                 Sequel.mengedit("ipsrsbarang","kode_brng=?","stok=stok-?",2,new String[]{
                        rs2.getString("jumlah"),rs2.getString("kode_brng")
                 });
             }
             Sequel.queryu("delete from tampjurnal");
             Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                 Sequel.cariIsi("select Penerimaan_NonMedis from set_akun"),"PERSEDIAAN BARANG NON MEDIS","0",rs.getString("tagihan")
             });    
             Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                 Sequel.cariIsi("select Kontra_Penerimaan_NonMedis from set_akun"),"HUTANG BARANG NON MEDIS",rs.getString("tagihan"),"0"
             }); 
             jur.simpanJurnal(rs.getString("no_faktur"),Sequel.cariIsi("select current_date()"),"U","BATAL TRANSAKSI PENERIMAAN BARANG NON MEDIS"+", OLEH "+akses.getkode());
         }          
         Sequel.queryu2("delete from ipsrspemesanan where no_faktur=?",1,new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()});
         
         tampil();
     } catch (SQLException ex) {
         System.out.println(ex);
     }      
  }       
    
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ppBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBayarActionPerformed
        if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString().trim().equals("")){
            Valid.textKosong(TCari,"No.Faktur");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBayarPemesananNonMedis bayarpesan=new DlgBayarPemesananNonMedis(null,false);
            bayarpesan.setData(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
            bayarpesan.tampil();
            bayarpesan.isCek();
            bayarpesan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            bayarpesan.setLocationRelativeTo(internalFrame1);
            bayarpesan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }   
    }//GEN-LAST:event_ppBayarActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPemesananIpsrs dialog = new DlgCariPemesananIpsrs(new javax.swing.JFrame(), true);
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
    private widget.Label LTotal;
    private widget.TextBox NoFaktur;
    private widget.TextBox TCari;
    private widget.Tanggal TglBeli1;
    private widget.Tanggal TglBeli2;
    private widget.Button btnBarang;
    private widget.Button btnPetugas;
    private widget.Button btnSatuan;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdjenis;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmjenis;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppBayar;
    private javax.swing.JMenuItem ppHapus;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       Valid.tabelKosong(tabMode);
        try{   
            ps=koneksi.prepareStatement(
                    "select ipsrspemesanan.tgl_pesan,ipsrspemesanan.no_faktur, "+
                    "ipsrspemesanan.kode_suplier,ipsrssuplier.nama_suplier, "+
                    "ipsrspemesanan.nip,petugas.nama,ipsrspemesanan.tgl_faktur, "+
                    "ipsrspemesanan.tgl_tempo,ipsrspemesanan.status,ipsrspemesanan.total2,ipsrspemesanan.ppn,"+
                    "ipsrspemesanan.meterai,ipsrspemesanan.tagihan,ipsrspemesanan.no_order "+
                    " from ipsrspemesanan inner join ipsrssuplier inner join petugas  "+
                    " inner join ipsrsdetailpesan inner join ipsrsbarang inner join kodesatuan "+
                    " inner join ipsrsjenisbarang "+
                    " on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng "+
                    " and ipsrsdetailpesan.kode_sat=kodesatuan.kode_sat "+
                    " and ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "+
                    " and ipsrspemesanan.kode_suplier=ipsrssuplier.kode_suplier "+
                    " and ipsrspemesanan.nip=petugas.nip and ipsrsbarang.jenis=ipsrsjenisbarang.kd_jenis"+
                    " where ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrspemesanan.no_faktur like ? or "+
                    " ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrspemesanan.kode_suplier like ? or "+
                    " ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrssuplier.nama_suplier like ? or "+
                    " ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrspemesanan.nip like ? or "+
                    " ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and petugas.nama like ? or "+
                    " ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsdetailpesan.kode_brng like ? or "+
                    " ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsbarang.nama_brng like ? or "+
                    " ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsdetailpesan.kode_sat like ? or "+
                    " ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrspemesanan.no_order like ? or "+
                    " ipsrspemesanan.tgl_pesan between ? and ? and ipsrspemesanan.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsjenisbarang.nm_jenis like ? "+
                    " group by ipsrspemesanan.no_faktur order by ipsrspemesanan.tgl_pesan,ipsrspemesanan.no_faktur ");
            try {
                ps.setString(1,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(3,"%"+NoFaktur.getText()+"%");
                ps.setString(4,"%"+nmsup.getText()+"%");
                ps.setString(5,"%"+nmptg.getText()+"%");
                ps.setString(6,"%"+nmjenis.getText()+"%");
                ps.setString(7,"%"+nmbar.getText()+"%");
                ps.setString(8,"%"+TCari.getText()+"%");
                ps.setString(9,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(10,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(11,"%"+NoFaktur.getText()+"%");
                ps.setString(12,"%"+nmsup.getText()+"%");
                ps.setString(13,"%"+nmptg.getText()+"%");
                ps.setString(14,"%"+nmjenis.getText()+"%");
                ps.setString(15,"%"+nmbar.getText()+"%");
                ps.setString(16,"%"+TCari.getText()+"%");
                ps.setString(17,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(18,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(19,"%"+NoFaktur.getText()+"%");
                ps.setString(20,"%"+nmsup.getText()+"%");
                ps.setString(21,"%"+nmptg.getText()+"%");
                ps.setString(22,"%"+nmjenis.getText()+"%");
                ps.setString(23,"%"+nmbar.getText()+"%");
                ps.setString(24,"%"+TCari.getText()+"%");
                ps.setString(25,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(27,"%"+NoFaktur.getText()+"%");
                ps.setString(28,"%"+nmsup.getText()+"%");
                ps.setString(29,"%"+nmptg.getText()+"%");
                ps.setString(30,"%"+nmjenis.getText()+"%");
                ps.setString(31,"%"+nmbar.getText()+"%");
                ps.setString(32,"%"+TCari.getText()+"%");
                ps.setString(33,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(34,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(35,"%"+NoFaktur.getText()+"%");
                ps.setString(36,"%"+nmsup.getText()+"%");
                ps.setString(37,"%"+nmptg.getText()+"%");
                ps.setString(38,"%"+nmjenis.getText()+"%");
                ps.setString(39,"%"+nmbar.getText()+"%");
                ps.setString(40,"%"+TCari.getText()+"%");
                ps.setString(41,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(42,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(43,"%"+NoFaktur.getText()+"%");
                ps.setString(44,"%"+nmsup.getText()+"%");
                ps.setString(45,"%"+nmptg.getText()+"%");
                ps.setString(46,"%"+nmjenis.getText()+"%");
                ps.setString(47,"%"+nmbar.getText()+"%");
                ps.setString(48,"%"+TCari.getText()+"%");
                ps.setString(49,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(50,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(51,"%"+NoFaktur.getText()+"%");
                ps.setString(52,"%"+nmsup.getText()+"%");
                ps.setString(53,"%"+nmptg.getText()+"%");
                ps.setString(54,"%"+nmjenis.getText()+"%");
                ps.setString(55,"%"+nmbar.getText()+"%");
                ps.setString(56,"%"+TCari.getText()+"%");
                ps.setString(57,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(58,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(59,"%"+NoFaktur.getText()+"%");
                ps.setString(60,"%"+nmsup.getText()+"%");
                ps.setString(61,"%"+nmptg.getText()+"%");
                ps.setString(62,"%"+nmjenis.getText()+"%");
                ps.setString(63,"%"+nmbar.getText()+"%");
                ps.setString(64,"%"+TCari.getText()+"%");
                ps.setString(65,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(66,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(67,"%"+NoFaktur.getText()+"%");
                ps.setString(68,"%"+nmsup.getText()+"%");
                ps.setString(69,"%"+nmptg.getText()+"%");
                ps.setString(70,"%"+nmjenis.getText()+"%");
                ps.setString(71,"%"+nmbar.getText()+"%");
                ps.setString(72,"%"+TCari.getText()+"%");
                ps.setString(73,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
                ps.setString(74,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
                ps.setString(75,"%"+NoFaktur.getText()+"%");
                ps.setString(76,"%"+nmsup.getText()+"%");
                ps.setString(77,"%"+nmptg.getText()+"%");
                ps.setString(78,"%"+nmjenis.getText()+"%");
                ps.setString(79,"%"+nmbar.getText()+"%");
                ps.setString(80,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                tagihan=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{rs.getString(2),rs.getString(3)+", "+rs.getString(4),
                          rs.getString(5)+", "+rs.getString(6),"","","","","",""
                    });  
                    
                    ps2=koneksi.prepareStatement("select ipsrsdetailpesan.kode_brng,ipsrsbarang.nama_brng, "+
                        "ipsrsdetailpesan.kode_sat,kodesatuan.satuan,ipsrsdetailpesan.jumlah,ipsrsdetailpesan.harga, "+
                        "ipsrsdetailpesan.subtotal,ipsrsdetailpesan.dis,ipsrsdetailpesan.besardis,ipsrsdetailpesan.total "+
                        "from ipsrsdetailpesan inner join ipsrsbarang inner join kodesatuan inner join ipsrsjenisbarang "+
                        " on ipsrsdetailpesan.kode_brng=ipsrsbarang.kode_brng and ipsrsbarang.jenis=ipsrsjenisbarang.kd_jenis "+
                        " and ipsrsdetailpesan.kode_sat=kodesatuan.kode_sat where "+
                        " ipsrsdetailpesan.no_faktur=? and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsdetailpesan.kode_brng like ? or "+
                        " ipsrsdetailpesan.no_faktur=? and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsbarang.nama_brng like ? or "+
                        " ipsrsdetailpesan.no_faktur=? and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsdetailpesan.kode_sat like ? or "+
                        " ipsrsdetailpesan.no_faktur=? and ipsrsjenisbarang.nm_jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsjenisbarang.nm_jenis like ? order by ipsrsdetailpesan.kode_brng  ");
                    try {
                        ps2.setString(1,rs.getString(2));
                        ps2.setString(2,"%"+nmjenis.getText()+"%");
                        ps2.setString(3,"%"+nmbar.getText()+"%");
                        ps2.setString(4,"%"+TCari.getText()+"%");
                        ps2.setString(5,rs.getString(2));
                        ps2.setString(6,"%"+nmjenis.getText()+"%");
                        ps2.setString(7,"%"+nmbar.getText()+"%");
                        ps2.setString(8,"%"+TCari.getText()+"%");
                        ps2.setString(9,rs.getString(2));
                        ps2.setString(10,"%"+nmjenis.getText()+"%");
                        ps2.setString(11,"%"+nmbar.getText()+"%");
                        ps2.setString(12,"%"+TCari.getText()+"%");
                        ps2.setString(13,rs.getString(2));
                        ps2.setString(14,"%"+nmjenis.getText()+"%");
                        ps2.setString(15,"%"+nmbar.getText()+"%");
                        ps2.setString(16,"%"+TCari.getText()+"%");
                        rs2=ps2.executeQuery();
                        int no=1;
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                no+". "+rs2.getString(1),rs2.getString(2),rs2.getString(3)+", "+rs2.getString(4),
                                rs2.getString(5),Valid.SetAngka(rs2.getDouble(6)),Valid.SetAngka(rs2.getDouble(7)),
                                Valid.SetAngka(rs2.getDouble(8)),Valid.SetAngka(rs2.getDouble(9)),
                                Valid.SetAngka(rs2.getDouble(10))
                            });
                            no++;
                        }                        
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    tabMode.addRow(new Object[]{rs.getString("no_order"),"Tgl.Faktur",": "+rs.getString("tgl_faktur"),"","","","Total",":",Valid.SetAngka(rs.getDouble("total2"))});
                    tabMode.addRow(new Object[]{"","Tgl.Datang",": "+rs.getString("tgl_pesan"),"","","","Meterai",":",Valid.SetAngka(rs.getDouble("meterai"))});
                    tabMode.addRow(new Object[]{"","Jth.Tempo",": "+rs.getString("tgl_tempo"),"","","","PPN",":",Valid.SetAngka(rs.getDouble("ppn"))});
                    tabMode.addRow(new Object[]{"","Status Bayar",": "+rs.getString("status"),"","","","Tagihan",":",Valid.SetAngka(rs.getDouble("tagihan"))});
                    tagihan=tagihan+rs.getDouble("tagihan");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                                
            LTotal.setText(Valid.SetAngka(tagihan));
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }        
    }

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdbar.requestFocus();        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getpenerimaan_non_medis());
        if(akses.getkode().equals("Admin Utama")){
            ppHapus.setEnabled(true);
        }else{
            ppHapus.setEnabled(false);
        }        
        ppBayar.setEnabled(akses.getbayar_pesan_non_medis());
    }
    
}

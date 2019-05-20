package ipsrs;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgSuplier;
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
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;

public class DlgCariPembelianIpsrs extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    public  DlgSuplierIPSRS suplier=new DlgSuplierIPSRS(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgBarangIPSRS barang=new DlgBarangIPSRS(null,false);
    private PreparedStatement ps,ps2,pscaribeli,psipsrsdetailbeli;
    private ResultSet rs,rs2;
    private double tagihan=0;
    private Jurnal jur=new Jurnal();

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPembelianIpsrs(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Tgl.Beli","No.Faktur","Suplier","Petugas","Barang",
                    "Satuan","Jml","Harga(Rp)","SubTotal(Rp)",
                    "Disk(%)","Bsr.Disk(Rp)","Total(Rp)"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(27);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(90);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoFaktur.setDocument(new batasInput((byte)15).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
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
        suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPembelianIpsrs")){
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
                if(akses.getform().equals("DlgCariPembelianIpsrs")){
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
                if(akses.getform().equals("DlgCariPembelianIpsrs")){
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
                if(akses.getform().equals("DlgCariPembelianIpsrs")){
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
                if(akses.getform().equals("DlgCariPembelianIpsrs")){
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
                if(akses.getform().equals("DlgCariPembelianIpsrs")){
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
            ps=koneksi.prepareStatement("select ipsrspembelian.tgl_beli,ipsrspembelian.no_faktur, "+
                    "ipsrspembelian.kode_suplier,ipsrssuplier.nama_suplier, "+
                    "ipsrspembelian.nip,petugas.nama,ipsrspembelian.subtotal,ipsrspembelian.potongan,ipsrspembelian.total, "+
                    "ipsrspembelian.ppn,ipsrspembelian.tagihan,ipsrspembelian.meterai from ipsrspembelian inner join ipsrssuplier inner join petugas inner join kodesatuan  "+
                    " inner join ipsrsdetailbeli inner join ipsrsbarang "+
                    " on ipsrsdetailbeli.kode_brng=ipsrsbarang.kode_brng "+
                    " and ipsrsbarang.kode_sat=kodesatuan.kode_sat "+
                    " and ipsrspembelian.no_faktur=ipsrsdetailbeli.no_faktur "+
                    " and ipsrspembelian.kode_suplier=ipsrssuplier.kode_suplier "+
                    " and ipsrspembelian.nip=petugas.nip"+
                    " where ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and ipsrspembelian.no_faktur like ? or "+
                    " ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and ipsrspembelian.kode_suplier like ? or "+
                    " ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and ipsrssuplier.nama_suplier like ? or "+
                    " ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and ipsrspembelian.nip like ? or "+
                    " ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and petugas.nama like ? or "+
                    " ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsbarang.jenis like ? or "+
                    " ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsdetailbeli.kode_brng like ? or "+
                    " ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsbarang.nama_brng like ? or "+
                    " ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and ipsrsdetailbeli.kode_sat like ? or "+
                    " ipsrspembelian.tgl_beli between ? and ? and ipsrspembelian.no_faktur like ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ?  and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? and kodesatuan.satuan like ? "+
                    " group by ipsrspembelian.no_faktur order by ipsrspembelian.tgl_beli,ipsrspembelian.no_faktur ");
            ps2=koneksi.prepareStatement("select ipsrsdetailbeli.kode_brng,ipsrsbarang.nama_brng, "+
                        "ipsrsdetailbeli.kode_sat,kodesatuan.satuan,ipsrsdetailbeli.jumlah,ipsrsdetailbeli.harga, "+
                        "ipsrsdetailbeli.subtotal,ipsrsdetailbeli.dis,ipsrsdetailbeli.besardis,ipsrsdetailbeli.total "+
                        "from ipsrsdetailbeli inner join ipsrsbarang inner join kodesatuan "+
                        " on ipsrsdetailbeli.kode_brng=ipsrsbarang.kode_brng "+
                        " and ipsrsdetailbeli.kode_sat=kodesatuan.kode_sat where "+
                        " ipsrsdetailbeli.no_faktur=? and ipsrsbarang.nama_brng like ? and ipsrsbarang.jenis like ? and ipsrsdetailbeli.kode_brng like ? or "+
                        " ipsrsdetailbeli.no_faktur=? and ipsrsbarang.nama_brng like ? and ipsrsbarang.jenis like ? and ipsrsbarang.nama_brng like ? or "+
                        " ipsrsdetailbeli.no_faktur=? and ipsrsbarang.nama_brng like ? and ipsrsbarang.jenis like ? and ipsrsdetailbeli.kode_sat like ? or "+
                        " ipsrsdetailbeli.no_faktur=? and ipsrsbarang.nama_brng like ? and ipsrsbarang.jenis like ? and ipsrsbarang.jenis like ? order by ipsrsdetailbeli.kode_brng  ");
            pscaribeli=koneksi.prepareStatement("select no_faktur, tagihan,tgl_beli from ipsrspembelian where no_faktur=?");
            psipsrsdetailbeli=koneksi.prepareStatement("select kode_brng,jumlah from ipsrsdetailbeli where no_faktur=? ");
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
        label7 = new widget.Label();
        kdjenis = new widget.TextBox();
        nmjenis = new widget.TextBox();
        btnJenis = new widget.Button();
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
        ppHapus.setForeground(new java.awt.Color(70, 70, 70));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Data Pengadaan Barang");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(200, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Pengadaan Barang Non Medis dan Penunjang ( Lab & RO ) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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
        label17.setBounds(325, 10, 60, 23);

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

        label7.setText("Jenis :");
        label7.setName("label7"); // NOI18N
        panelisi4.add(label7);
        label7.setBounds(0, 10, 42, 23);

        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(207, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });
        panelisi4.add(kdjenis);
        kdjenis.setBounds(45, 10, 61, 23);

        nmjenis.setEditable(false);
        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmjenis);
        nmjenis.setBounds(108, 10, 180, 23);

        btnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnJenis.setMnemonic('1');
        btnJenis.setToolTipText("Alt+1");
        btnJenis.setName("btnJenis"); // NOI18N
        btnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        btnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisActionPerformed(evt);
            }
        });
        panelisi4.add(btnJenis);
        btnJenis.setBounds(290, 10, 25, 23);

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

        label11.setText("Tgl.Beli :");
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
        akses.setform("DlgCariPembelianIpsrs");
        suplier.emptTeks();
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgCariPembelianIpsrs");
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
        akses.setform("DlgCariPembelianIpsrs");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

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
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kdbar.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from ipsrsbarang where kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){  
            kdjenis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){    
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

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
        kdjenis.setText("");
        nmjenis.setText("");
        nmbar.setText("");
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
            
            Sequel.queryu("delete from temporary");
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
                                tabMode.getValueAt(i,9).toString()+"','"+
                                tabMode.getValueAt(i,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            }
            Sequel.menyimpan("temporary","'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            Sequel.menyimpan("temporary","'0','Jml.Total :','','','','','','',' ','"+LTotal.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptPembelian.jasper","report","::[ Transaksi Pembelian Barang ]::",param);
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

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
  if(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim().equals("")){
      Valid.textKosong(TCari,tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
  }else{
     try {
         
         pscaribeli.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
         rs=pscaribeli.executeQuery();
         while(rs.next()){
             psipsrsdetailbeli.setString(1,rs.getString(1));
             rs2=psipsrsdetailbeli.executeQuery();
             while(rs2.next()){
                 Sequel.mengedit("ipsrsbarang","kode_brng=?","stok=stok-?",2,new String[]{
                        rs2.getString("jumlah"),rs2.getString("kode_brng")
                 });
             }
             Sequel.queryu("delete from tampjurnal");
             Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                 Sequel.cariIsi("select Pengadaan_Ipsrs from set_akun"),"PEMBELIAN","0",rs.getString("tagihan")
             });    
             Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                 Sequel.cariIsi("select kd_rek from ipsrspembelian where no_faktur =?",rs.getString("no_faktur")),"KAS DI TANGAN",rs.getString("tagihan"),"0"
             }); 
             jur.simpanJurnal(rs.getString("no_faktur"),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PENGGUNAAN BARANG NON MEDIS DAN PENUNJANG (LAB & RAD)");
         }          
         Sequel.queryu2("delete from ipsrspembelian where no_faktur=?",1,new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()});
         
         tampil();
     } catch (SQLException ex) {
         System.out.println(ex);
     }      
  }        
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_jenis from ipsrsjenisbarang where kd_jenis=?", nmjenis,kdjenis.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kdbar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnJenisActionPerformed(null);
        }
    }//GEN-LAST:event_kdjenisKeyPressed

    private void btnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisActionPerformed
        akses.setform("DlgCariPembelianIpsrs");
        barang.jenis.isCek();
        barang.jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.jenis.setLocationRelativeTo(internalFrame1);
        barang.jenis.setVisible(true);
    }//GEN-LAST:event_btnJenisActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPembelianIpsrs dialog = new DlgCariPembelianIpsrs(new javax.swing.JFrame(), true);
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
    private widget.Button btnJenis;
    private widget.Button btnPetugas;
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
    private widget.Label label7;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmjenis;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppHapus;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       Valid.tabelKosong(tabMode);
        try{            
            ps.setString(1,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(2,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(3,"%"+NoFaktur.getText()+"%");
            ps.setString(4,"%"+nmsup.getText()+"%");
            ps.setString(5,"%"+nmptg.getText()+"%");
            ps.setString(6,"%"+kdjenis.getText()+"%");
            ps.setString(7,"%"+nmbar.getText()+"%");
            ps.setString(8,"%"+TCari.getText()+"%");
            ps.setString(9,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(10,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(11,"%"+NoFaktur.getText()+"%");
            ps.setString(12,"%"+nmsup.getText()+"%");
            ps.setString(13,"%"+nmptg.getText()+"%");
            ps.setString(14,"%"+kdjenis.getText()+"%");
            ps.setString(15,"%"+nmbar.getText()+"%");
            ps.setString(16,"%"+TCari.getText()+"%");
            ps.setString(17,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(18,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(19,"%"+NoFaktur.getText()+"%");
            ps.setString(20,"%"+nmsup.getText()+"%");
            ps.setString(21,"%"+nmptg.getText()+"%");
            ps.setString(22,"%"+kdjenis.getText()+"%");
            ps.setString(23,"%"+nmbar.getText()+"%");
            ps.setString(24,"%"+TCari.getText()+"%");
            ps.setString(25,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(26,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(27,"%"+NoFaktur.getText()+"%");
            ps.setString(28,"%"+nmsup.getText()+"%");
            ps.setString(29,"%"+nmptg.getText()+"%");
            ps.setString(30,"%"+kdjenis.getText()+"%");
            ps.setString(31,"%"+nmbar.getText()+"%");
            ps.setString(32,"%"+TCari.getText()+"%");
            ps.setString(33,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(34,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(35,"%"+NoFaktur.getText()+"%");
            ps.setString(36,"%"+nmsup.getText()+"%");
            ps.setString(37,"%"+nmptg.getText()+"%");
            ps.setString(38,"%"+kdjenis.getText()+"%");
            ps.setString(39,"%"+nmbar.getText()+"%");
            ps.setString(40,"%"+TCari.getText()+"%");
            ps.setString(41,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(42,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(43,"%"+NoFaktur.getText()+"%");
            ps.setString(44,"%"+nmsup.getText()+"%");
            ps.setString(45,"%"+nmptg.getText()+"%");
            ps.setString(46,"%"+kdjenis.getText()+"%");
            ps.setString(47,"%"+nmbar.getText()+"%");
            ps.setString(48,"%"+TCari.getText()+"%");
            ps.setString(49,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(50,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(51,"%"+NoFaktur.getText()+"%");
            ps.setString(52,"%"+nmsup.getText()+"%");
            ps.setString(53,"%"+nmptg.getText()+"%");
            ps.setString(54,"%"+kdjenis.getText()+"%");
            ps.setString(55,"%"+nmbar.getText()+"%");
            ps.setString(56,"%"+TCari.getText()+"%");
            ps.setString(57,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(58,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(59,"%"+NoFaktur.getText()+"%");
            ps.setString(60,"%"+nmsup.getText()+"%");
            ps.setString(61,"%"+nmptg.getText()+"%");
            ps.setString(62,"%"+kdjenis.getText()+"%");
            ps.setString(63,"%"+nmbar.getText()+"%");
            ps.setString(64,"%"+TCari.getText()+"%");
            ps.setString(65,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(66,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(67,"%"+NoFaktur.getText()+"%");
            ps.setString(68,"%"+nmsup.getText()+"%");
            ps.setString(69,"%"+nmptg.getText()+"%");
            ps.setString(70,"%"+kdjenis.getText()+"%");
            ps.setString(71,"%"+nmbar.getText()+"%");
            ps.setString(72,"%"+TCari.getText()+"%");
            ps.setString(73,Valid.SetTgl(TglBeli1.getSelectedItem()+""));
            ps.setString(74,Valid.SetTgl(TglBeli2.getSelectedItem()+""));
            ps.setString(75,"%"+NoFaktur.getText()+"%");
            ps.setString(76,"%"+nmsup.getText()+"%");
            ps.setString(77,"%"+nmptg.getText()+"%");
            ps.setString(78,"%"+kdjenis.getText()+"%");
            ps.setString(79,"%"+nmbar.getText()+"%");
            ps.setString(80,"%"+TCari.getText()+"%");
            rs=ps.executeQuery();
            tagihan=0;
            while(rs.next()){
                tabMode.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3)+", "+rs.getString(4),
                      rs.getString(5)+", "+rs.getString(6),"Pembelian :","","","","","","",""
                });                
                ps2.setString(1,rs.getString(2));
                ps2.setString(2,"%"+nmbar.getText()+"%");
                ps2.setString(3,"%"+kdjenis.getText()+"%");
                ps2.setString(4,"%"+TCari.getText()+"%");
                ps2.setString(5,rs.getString(2));
                ps2.setString(6,"%"+nmbar.getText()+"%");
                ps2.setString(7,"%"+kdjenis.getText()+"%");
                ps2.setString(8,"%"+TCari.getText()+"%");
                ps2.setString(9,rs.getString(2));
                ps2.setString(10,"%"+nmbar.getText()+"%");
                ps2.setString(11,"%"+kdjenis.getText()+"%");
                ps2.setString(12,"%"+TCari.getText()+"%");
                ps2.setString(13,rs.getString(2));
                ps2.setString(14,"%"+nmbar.getText()+"%");
                ps2.setString(15,"%"+kdjenis.getText()+"%");
                ps2.setString(16,"%"+TCari.getText()+"%");
                rs2=ps2.executeQuery();
                int no=1;
                while(rs2.next()){
                    tabMode.addRow(new Object[]{"","","","",no+". "+rs2.getString(1)+", "+rs2.getString(2),
                                    rs2.getString(3)+", "+rs2.getString(4),
                                    rs2.getString(5),Valid.SetAngka(rs2.getDouble(6)),Valid.SetAngka(rs2.getDouble(7)),
                                    Valid.SetAngka(rs2.getDouble(8)),Valid.SetAngka(rs2.getDouble(9)),Valid.SetAngka(rs2.getDouble(10))});
                    no++;
                }
                tabMode.addRow(new Object[]{"","","","","","Total",":","",Valid.SetAngka(rs.getDouble("subtotal")),"",Valid.SetAngka(rs.getDouble("potongan")),Valid.SetAngka(rs.getDouble("total"))});
                tabMode.addRow(new Object[]{"","","","","","PPN",":","","","","",Valid.SetAngka(rs.getDouble("ppn"))});
                tabMode.addRow(new Object[]{"","","","","","Meterai",":","","","","",Valid.SetAngka(rs.getDouble("meterai"))});
                tabMode.addRow(new Object[]{"","","","","","Tagihan",":","","","","",Valid.SetAngka(rs.getDouble("tagihan"))});
                tagihan=tagihan+rs.getDouble("total");
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
        BtnPrint.setEnabled(akses.getipsrs_pengadaan_barang());
    }
    
}

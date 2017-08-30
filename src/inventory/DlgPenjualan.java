package inventory;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

public class DlgPenjualan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private riwayatobat Trackobat=new riwayatobat();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariPenjualan carijual=new DlgCariPenjualan(null,false);
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private double ttl=0,y=0,stokbarang=0,bayar=0,total=0,ppn=0,besarppn=0,tagihanppn=0;;
    private int jml=0,i=0,row;
    private String verifikasi_penjualan_di_kasir=Sequel.cariIsi(
            "select verifikasi_penjualan_di_kasir from set_nota"),status="Belum Dibayar";
    private PreparedStatement ps,psstok;
    private ResultSet rs,rsstok;
    private String[] kodebarang,namabarang,kategori,satuan;
    private double[] harga,jumlah,subtotal,diskon,besardiskon,totaljual,tambahan,stok;
    private WarnaTable2 warna=new WarnaTable2();

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgPenjualan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Jml","Kode Barang","Nama Barang","Kategori","Satuan","Harga(Rp)",
                    "Subtotal(Rp)","Ptg(%)","Potongan(Rp)","Tambahan(Rp)","Total(Rp)","Stok"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==7)||(colIndex==8)||(colIndex==9)) {
                    a=true;
                }
                return a;
            }
            
            Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
               return types [columnIndex];
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(42);
            }else if(i==1){
                column.setPreferredWidth(85);
            }else if(i==2){
                column.setPreferredWidth(190);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(40);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(85);
            }else if(i==11){
                column.setPreferredWidth(35);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        NoNota.setDocument(new batasInput((byte)20).getKata(NoNota));
        kdmem.setDocument(new batasInput((byte)15).getKata(kdmem));
        kdgudang.setDocument(new batasInput((byte)5).getKata(kdgudang));
        catatan.setDocument(new batasInput((byte)40).getKata(catatan));
        Bayar.setDocument(new batasInput((byte)14).getOnlyAngka(Bayar));   
        TCari.setDocument(new batasInput((byte)100).getKata(TCari)); 
        
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        }
        
        Bayar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void removeUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void changedUpdate(DocumentEvent e) {isKembali();}
        });
        
        carijual.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_jual,6),signed)),0) from penjualan ","PJ",6,NoNota); 
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
        
        carijual.pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgPenjualan")){
                    if(carijual.pasien.getTable().getSelectedRow()!= -1){                   
                        kdmem.setText(carijual.pasien.getTable().getValueAt(carijual.pasien.getTable().getSelectedRow(),1).toString());
                        nmmem.setText(carijual.pasien.getTable().getValueAt(carijual.pasien.getTable().getSelectedRow(),2).toString());
                    }  
                    kdmem.requestFocus();
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
        
        carijual.pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgPenjualan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        carijual.pasien.dispose();
                        carijual.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });       
        
        carijual.petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgPenjualan")){
                    if(carijual.petugas.getTable().getSelectedRow()!= -1){                   
                        kdptg.setText(carijual.petugas.getTable().getValueAt(carijual.petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(carijual.petugas.getTable().getValueAt(carijual.petugas.getTable().getSelectedRow(),1).toString());
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgPenjualan")){
                    if(bangsal.getTable().getSelectedRow()!= -1){                   
                        kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        tampil();
                    } 
                    kdgudang.requestFocus();
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
        
        TCari.requestFocus();
        
        Valid.loadCombo(CmbAkun,"nama_bayar","akun_bayar");
        try {
            PPN.setText(Sequel.cariIsi("select ppn from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString()));
        } catch (Exception e) {
            PPN.setText("0");
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

        Kd2 = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnNota = new widget.Button();
        BtnSimpan = new widget.Button();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnTambah = new widget.Button();
        label22 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi5 = new widget.panelisi();
        label10 = new widget.Label();
        LTotal = new widget.Label();
        label19 = new widget.Label();
        Bayar = new widget.TextBox();
        label20 = new widget.Label();
        LKembali = new widget.Label();
        jLabel10 = new widget.Label();
        CmbAkun = new widget.ComboBox();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        TagihanPPn = new widget.Label();
        BesarPPN = new widget.TextBox();
        PPN = new widget.TextBox();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoNota = new widget.TextBox();
        label14 = new widget.Label();
        kdmem = new widget.TextBox();
        kdptg = new widget.TextBox();
        label16 = new widget.Label();
        nmmem = new widget.TextBox();
        nmptg = new widget.TextBox();
        BtnMem = new widget.Button();
        BtnPtg = new widget.Button();
        Jenisjual = new widget.ComboBox();
        label18 = new widget.Label();
        catatan = new widget.TextBox();
        label12 = new widget.Label();
        label11 = new widget.Label();
        Tgl = new widget.Tanggal();
        label21 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(102, 51, 0));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppStok.setBackground(new java.awt.Color(255, 255, 255));
        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(102, 51, 0));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok.setText("Tampilkan Semua Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setIconTextGap(8);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Penjualan Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
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
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 132));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnNota.setMnemonic('S');
        BtnNota.setText("Nota");
        BtnNota.setToolTipText("Alt+S");
        BtnNota.setName("BtnNota"); // NOI18N
        BtnNota.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNotaActionPerformed(evt);
            }
        });
        BtnNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNotaKeyPressed(evt);
            }
        });
        panelisi1.add(BtnNota);

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
        panelisi1.add(BtnSimpan);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(220, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi1.add(BtnTambah);

        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(15, 23));
        panelisi1.add(label22);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('E');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+E");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 54));
        panelisi5.setLayout(null);

        label10.setText("Grand Total :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi5.add(label10);
        label10.setBounds(0, 10, 90, 23);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(200, 23));
        panelisi5.add(LTotal);
        LTotal.setBounds(94, 10, 160, 23);

        label19.setText("Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi5.add(label19);
        label19.setBounds(256, 40, 80, 23);

        Bayar.setText("0");
        Bayar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Bayar.setName("Bayar"); // NOI18N
        Bayar.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi5.add(Bayar);
        Bayar.setBounds(340, 40, 200, 23);

        label20.setText("Kembali :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(130, 23));
        panelisi5.add(label20);
        label20.setBounds(556, 40, 80, 23);

        LKembali.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LKembali.setText("0");
        LKembali.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LKembali.setName("LKembali"); // NOI18N
        LKembali.setPreferredSize(new java.awt.Dimension(120, 23));
        panelisi5.add(LKembali);
        LKembali.setBounds(640, 40, 170, 23);

        jLabel10.setText("Akun Bayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi5.add(jLabel10);
        jLabel10.setBounds(256, 10, 80, 23);

        CmbAkun.setForeground(new java.awt.Color(153, 0, 51));
        CmbAkun.setName("CmbAkun"); // NOI18N
        CmbAkun.setOpaque(false);
        CmbAkun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbAkunKeyPressed(evt);
            }
        });
        panelisi5.add(CmbAkun);
        CmbAkun.setBounds(340, 10, 200, 23);

        jLabel11.setText("PPN(%) :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi5.add(jLabel11);
        jLabel11.setBounds(556, 10, 80, 23);

        jLabel12.setText("Tagihan + PPN :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi5.add(jLabel12);
        jLabel12.setBounds(0, 40, 90, 23);

        TagihanPPn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TagihanPPn.setText("0");
        TagihanPPn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TagihanPPn.setName("TagihanPPn"); // NOI18N
        TagihanPPn.setPreferredSize(new java.awt.Dimension(200, 23));
        panelisi5.add(TagihanPPn);
        TagihanPPn.setBounds(94, 40, 160, 23);

        BesarPPN.setText("0");
        BesarPPN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BesarPPN.setName("BesarPPN"); // NOI18N
        BesarPPN.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi5.add(BesarPPN);
        BesarPPN.setBounds(682, 10, 120, 23);

        PPN.setText("0");
        PPN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PPN.setName("PPN"); // NOI18N
        PPN.setPreferredSize(new java.awt.Dimension(150, 23));
        PPN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PPNKeyPressed(evt);
            }
        });
        panelisi5.add(PPN);
        PPN.setBounds(640, 10, 40, 23);

        jPanel1.add(panelisi5, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(89, 104));
        panelisi3.setLayout(null);

        label15.setText("No.Nota :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoNota.setName("NoNota"); // NOI18N
        NoNota.setPreferredSize(new java.awt.Dimension(207, 23));
        NoNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoNotaKeyPressed(evt);
            }
        });
        panelisi3.add(NoNota);
        NoNota.setBounds(74, 10, 260, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(376, 40, 60, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(439, 10, 100, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(439, 40, 100, 23);

        label16.setText("Pasien :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(376, 10, 60, 23);

        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(540, 10, 232, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(540, 40, 232, 23);

        BtnMem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMem.setMnemonic('1');
        BtnMem.setToolTipText("Alt+1");
        BtnMem.setName("BtnMem"); // NOI18N
        BtnMem.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnMem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMemActionPerformed(evt);
            }
        });
        panelisi3.add(BtnMem);
        BtnMem.setBounds(774, 10, 28, 23);

        BtnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg.setMnemonic('2');
        BtnPtg.setToolTipText("Alt+2");
        BtnPtg.setName("BtnPtg"); // NOI18N
        BtnPtg.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtgActionPerformed(evt);
            }
        });
        panelisi3.add(BtnPtg);
        BtnPtg.setBounds(774, 40, 28, 23);

        Jenisjual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jual Bebas", "Karyawan", "Beli Luar", "Rawat Jalan", "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP" }));
        Jenisjual.setName("Jenisjual"); // NOI18N
        Jenisjual.setPreferredSize(new java.awt.Dimension(40, 23));
        Jenisjual.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisjualItemStateChanged(evt);
            }
        });
        Jenisjual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisjualKeyPressed(evt);
            }
        });
        panelisi3.add(Jenisjual);
        Jenisjual.setBounds(239, 70, 95, 23);

        label18.setText("Catatan :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 70, 23);

        catatan.setName("catatan"); // NOI18N
        catatan.setPreferredSize(new java.awt.Dimension(207, 23));
        catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                catatanKeyPressed(evt);
            }
        });
        panelisi3.add(catatan);
        catatan.setBounds(74, 40, 260, 23);

        label12.setText("Jns.Jual :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(170, 70, 65, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 70, 70, 23);

        Tgl.setEditable(false);
        Tgl.setDisplayFormat("dd-MM-yyyy");
        Tgl.setName("Tgl"); // NOI18N
        Tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKeyPressed(evt);
            }
        });
        panelisi3.add(Tgl);
        Tgl.setBounds(74, 70, 95, 23);

        label21.setText("Lokasi :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label21);
        label21.setBounds(376, 70, 60, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(439, 70, 100, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(540, 70, 232, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        panelisi3.add(BtnGudang);
        BtnGudang.setBounds(774, 70, 28, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {                  
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                                     
                    getData();                     
                    TCari.setText("");
                    TCari.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)){
                try {                                     
                    getData();           
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    switch (tbDokter.getSelectedColumn()) {
                        case 0:
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),0);
                            break;
                        case 5:
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),5);
                            break;
                        case 7:
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),7);
                            break;
                        case 8:
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),8);
                            break;
                        case 9:
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),9);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                } 
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_BACK_SPACE){
                try {
                    switch (tbDokter.getSelectedColumn()) {
                        case 0:
                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("0,0")){
                                tbDokter.setValueAt("", tbDokter.getSelectedRow(),0);
                            }   break;
                        case 5:
                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("0,0")){
                                tbDokter.setValueAt("", tbDokter.getSelectedRow(),5);
                            }   break;
                        case 7:
                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString().equals("0,0")){
                                tbDokter.setValueAt("", tbDokter.getSelectedRow(),7);
                            }   break;
                        case 8:
                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString().equals("0,0")){
                                tbDokter.setValueAt("", tbDokter.getSelectedRow(),8);
                            }   break;
                        case 9:
                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString().equals("0,0")){
                                tbDokter.setValueAt("", tbDokter.getSelectedRow(),9);
                            }   break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                }       
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        carijual.emptTeks();      
        carijual.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        carijual.setLocationRelativeTo(internalFrame1);
        carijual.setAlwaysOnTop(false);
        carijual.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        carijual.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnCari,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoNota.getText().trim().equals("")){
            Valid.textKosong(NoNota,"No.Nota");
        }else if(nmmem.getText().trim().equals("")||kdmem.getText().trim().equals("")){
            Valid.textKosong(kdmem,"Pasien");
        }else if(nmptg.getText().trim().equals("")||nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(nmgudang.getText().trim().equals("")||kdgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Gudang");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            tbDokter.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan penjualan...!!!!");
            tbDokter.requestFocus();
        }else{
            if(verifikasi_penjualan_di_kasir.equals("No")){
                status="Sudah Dibayar";
            }else{
                status="Belum Dibayar";
            }
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if(Sequel.menyimpantf("penjualan","?,?,?,?,?,?,?,?,?,?,?","No.Nota",11,new String[]{
                    NoNota.getText(),Valid.SetTgl(Tgl.getSelectedItem()+""),kdptg.getText(),kdmem.getText(),nmmem.getText(),
                    catatan.getText(),Jenisjual.getSelectedItem().toString(),Double.toString(besarppn),status,kdgudang.getText(),
                    Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString())}
                   )==true){
                        Sequel.AutoComitFalse();
                        row=tabMode.getRowCount();
                        for(i=0;i<row;i++){  
                            try {
                                if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                                   if(Sequel.menyimpantf("detailjual","'"+NoNota.getText()+"','"+
                                           tabMode.getValueAt(i,1).toString()+"','"+
                                           tabMode.getValueAt(i,4).toString()+"','"+
                                           tabMode.getValueAt(i,5).toString()+"','"+
                                           Sequel.cariIsi("select h_beli from databarang where kode_brng=?",tabMode.getValueAt(i,1).toString())+"','"+
                                           tabMode.getValueAt(i,0).toString()+"','"+
                                           tabMode.getValueAt(i,6).toString()+"','"+
                                           tabMode.getValueAt(i,7).toString()+"','"+
                                           tabMode.getValueAt(i,8).toString()+"','"+
                                           tabMode.getValueAt(i,9).toString()+"','"+
                                           tabMode.getValueAt(i,10).toString()+"'","Transaksi Penjualan")==true){
                                        if(verifikasi_penjualan_di_kasir.equals("No")){
                                            Trackobat.catatRiwayat(tabMode.getValueAt(i,1).toString(),0,Valid.SetAngka(tabMode.getValueAt(i,0).toString()),"Penjualan",var.getkode(),kdgudang.getText(),"Simpan");
                                            Sequel.menyimpan("gudangbarang","'"+tabMode.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tabMode.getValueAt(i,0).toString()+"'", 
                                                 "stok=stok-'"+tabMode.getValueAt(i,0).toString()+"'","kode_brng='"+tabMode.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"'");   
                                        }                                       
                                   }                                    
                                }
                            } catch (Exception e) {
                            }                
                        }   
                    
                        if(verifikasi_penjualan_di_kasir.equals("No")){
                            Sequel.queryu("delete from tampjurnal");                    
                            Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select Penjualan_Obat from set_akun")+"','PENJUALAN OBAT BEBAS','0','"+ttl+"'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString())+"','CARA BAYAR','"+ttl+"','0'","Rekening"); 
                            jur.simpanJurnal(NoNota.getText(),Valid.SetTgl(Tgl.getSelectedItem()+""),"U","PENJUALAN DI "+nmgudang.getText().toUpperCase());                                                
                            Sequel.menyimpan("tagihan_sadewa","'"+NoNota.getText()+"','"+kdmem.getText()+"','"+nmmem.getText()+"','-',concat('"+Valid.SetTgl(Tgl.getSelectedItem()+"")+
                                    "',' ',CURTIME()),'Pelunasan','"+ttl+"','"+ttl+"','Sudah','"+var.getkode()+"'","No.Nota");
                        }
                        
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_jual,6),signed)),0) from penjualan ","PJ",6,NoNota);                             
                        Sequel.AutoComitTrue();
                        row=tabMode.getRowCount();
                        for(int r=0;r<row;r++){ 
                            try {
                                if(!tabMode.getValueAt(r,0).toString().isEmpty()){
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(r,11).toString())-Double.parseDouble(tabMode.getValueAt(r,0).toString()),r,11);
                                }
                            } catch (Exception e) {
                            }

                            tabMode.setValueAt("",r,0);
                            tabMode.setValueAt(0,r,6);
                            tabMode.setValueAt(0,r,7);
                            tabMode.setValueAt(0,r,8);  
                            tabMode.setValueAt(0,r,9);  
                            tabMode.setValueAt(0,r,10);           
                        }
                        tagihanppn=0;
                        ttl=0;
                        bayar=0;
                        besarppn=0;
                        total=0;
                        ppn=0;     
                        LTotal.setText("0");
                        Bayar.setText("0");
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan No.Nota sudah ada sebelumnya...!!");
                } 
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,kdgudang,BtnCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoNota.getText().trim().equals("")){
            Valid.textKosong(NoNota,"No.Nota");
        }else if(nmmem.getText().trim().equals("")||kdmem.getText().trim().equals("")){
            Valid.textKosong(kdmem,"Pasien");
        }else if(nmptg.getText().trim().equals("")||nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            tbDokter.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan penjualan...!!!!");
            tbDokter.requestFocus();
        }else {
            Sequel.AutoComitFalse();
            Sequel.queryu("delete from temporary");
            row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
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
            Sequel.AutoComitTrue();
            Valid.panggilUrl("billing/NotaApotek.php?nonota="+NoNota.getText()+"&besarppn="+besarppn+"&bayar="+Bayar.getText()+"&tanggal="+Valid.SetTgl(Tgl.getSelectedItem()+"")+"&catatan="+catatan.getText().replaceAll(" ","_")+"&petugas="+nmptg.getText().replaceAll(" ","_")+"&pasien="+nmmem.getText().replaceAll(" ","_")+"&norm="+kdmem.getText().replaceAll(" ","_"));
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnNotaActionPerformed

    private void BtnNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNotaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNotaKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, TCari, Bayar);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void NoNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoNotaKeyPressed
        Valid.pindah(evt,TCari, Tgl);
}//GEN-LAST:event_NoNotaKeyPressed

private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
                Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
                break;
            case KeyEvent.VK_PAGE_UP:
                Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
                Tgl.requestFocus();
                break;
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
                catatan.requestFocus();
                break;
            case KeyEvent.VK_UP:
                BtnMemActionPerformed(null);
                break;
            default:
                break;
        }
}//GEN-LAST:event_kdmemKeyPressed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
                Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
                break;
            case KeyEvent.VK_PAGE_UP:
                Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
                Jenisjual.requestFocus();
                break;
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
                TCari.requestFocus();
                break;
            case KeyEvent.VK_UP:
                BtnPtgActionPerformed(null);
                break;
            default:
                break;
        }
}//GEN-LAST:event_kdptgKeyPressed

private void BtnMemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMemActionPerformed
        var.setform("DlgPenjualan");
        carijual.pasien.isCek();
        carijual.pasien.emptTeks();
        carijual.pasien.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        carijual.pasien.setLocationRelativeTo(internalFrame1);
        carijual.pasien.setAlwaysOnTop(false);
        carijual.pasien.setVisible(true);
}//GEN-LAST:event_BtnMemActionPerformed

private void BtnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtgActionPerformed
        var.setform("DlgPenjualan");
        carijual.petugas.emptTeks();
        carijual.petugas.isCek();
        carijual.petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        carijual.petugas.setLocationRelativeTo(internalFrame1);
        carijual.petugas.setAlwaysOnTop(false);
        carijual.petugas.setVisible(true);
}//GEN-LAST:event_BtnPtgActionPerformed

private void JenisjualItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisjualItemStateChanged
       tampil();
}//GEN-LAST:event_JenisjualItemStateChanged

private void JenisjualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisjualKeyPressed
        Valid.pindah(evt, catatan, kdptg);
}//GEN-LAST:event_JenisjualKeyPressed

private void catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catatanKeyPressed
        Valid.pindah(evt, kdmem, Jenisjual);
}//GEN-LAST:event_catatanKeyPressed

private void TglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKeyPressed
        Valid.pindah(evt,NoNota,kdmem);
}//GEN-LAST:event_TglKeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tabMode.getRowCount();
            for(int r=0;r<row2;r++){ 
                tabMode.setValueAt("",r,0);
                tabMode.setValueAt(0,r,6);
                tabMode.setValueAt(0,r,7);
                tabMode.setValueAt(0,r,8);
                tabMode.setValueAt(0,r,9);
                tabMode.setValueAt(0,r,10);
                tabMode.setValueAt(0,r,11);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
                tampil();
                break;
            case KeyEvent.VK_PAGE_UP:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
                tampil();
                kdptg.requestFocus();
                break;
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
                tampil();
                BtnSimpan.requestFocus();
                break;
            case KeyEvent.VK_UP:
                BtnGudangActionPerformed(null);
                break;
            default:
                break;
        }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
    var.setform("DlgPenjualan");
    bangsal.isCek();
    bangsal.emptTeks();
    bangsal.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
    bangsal.setLocationRelativeTo(internalFrame1);
    bangsal.setAlwaysOnTop(false);
    bangsal.setVisible(true);
}//GEN-LAST:event_BtnGudangActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarang barang=new DlgBarang(null,false);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){
            try {
                stokbarang=0;    
                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                try {
                    psstok.setString(1,kdgudang.getText());
                    psstok.setString(2,tbDokter.getValueAt(i,1).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stokbarang=rsstok.getDouble(1);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rsstok!=null){
                        rsstok.close();
                    }
                    if(psstok!=null){
                        psstok.close();
                    }
                }                    
                tbDokter.setValueAt(stokbarang,i,11);
            } catch (Exception e) {
                tbDokter.setValueAt(0,i,10);
            }
        }
    }//GEN-LAST:event_ppStokActionPerformed

    private void CmbAkunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbAkunKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            PPN.setText(Sequel.cariIsi("select ppn from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString()));
            isKembali();
            Bayar.requestFocus();
        }
    }//GEN-LAST:event_CmbAkunKeyPressed

    private void PPNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PPNKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isKembali();
            Bayar.requestFocus();
        }
    }//GEN-LAST:event_PPNKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPenjualan dialog = new DlgPenjualan(new javax.swing.JFrame(), true);
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
    private widget.TextBox Bayar;
    private widget.TextBox BesarPPN;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnGudang;
    private widget.Button BtnKeluar;
    private widget.Button BtnMem;
    private widget.Button BtnNota;
    private widget.Button BtnPtg;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.ComboBox CmbAkun;
    private widget.ComboBox Jenisjual;
    private widget.TextBox Kd2;
    private widget.Label LKembali;
    private widget.Label LTotal;
    private widget.TextBox NoNota;
    private widget.TextBox PPN;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Label TagihanPPn;
    private widget.Tanggal Tgl;
    private widget.TextBox catatan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdgudang;
    private widget.TextBox kdmem;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        row=tabMode.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>0){
                    jml++;
                }
            } catch (Exception e) {
                jml=jml+0;
            } 
        }
        
        kodebarang=new String[jml];
        namabarang=new String[jml];
        kategori=new String[jml];
        satuan=new String[jml];
        harga=new double[jml];
        jumlah=new double[jml];
        subtotal=new double[jml];
        diskon=new double[jml];
        besardiskon=new double[jml];
        tambahan=new double[jml];
        totaljual=new double[jml];
        stok=new double[jml];
        int index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>0){
                    jumlah[index]=Double.parseDouble(tabMode.getValueAt(i,0).toString());
                    kodebarang[index]=tabMode.getValueAt(i,1).toString();
                    namabarang[index]=tabMode.getValueAt(i,2).toString();
                    kategori[index]=tabMode.getValueAt(i,3).toString();
                    satuan[index]=tabMode.getValueAt(i,4).toString();
                    harga[index]=Double.parseDouble(tabMode.getValueAt(i,5).toString());
                    subtotal[index]=Double.parseDouble(tabMode.getValueAt(i,6).toString());
                    diskon[index]=Double.parseDouble(tabMode.getValueAt(i,7).toString());
                    besardiskon[index]=Double.parseDouble(tabMode.getValueAt(i,8).toString());
                    tambahan[index]=Double.parseDouble(tabMode.getValueAt(i,9).toString());
                    totaljual[index]=Double.parseDouble(tabMode.getValueAt(i,10).toString());
                    stok[index]=Double.parseDouble(tabMode.getValueAt(i,11).toString());
                    index++;
                }
            } catch (Exception e) {
            }                
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){            
            tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],harga[i],subtotal[i],diskon[i],besardiskon[i],tambahan[i],totaljual[i],stok[i]});
        }
        try{
            ps=koneksi.prepareStatement(
                "select databarang.kode_brng, databarang.nama_brng,jenis.nama, "+
                " databarang.kode_sat, databarang.jualbebas, databarang.karyawan,"+
                " databarang.ralan,databarang.beliluar,"+
                " databarang.kelas1,databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip "+
                " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                " where databarang.status='1' and databarang.kode_brng like ? or "+
                " databarang.status='1' and databarang.nama_brng like ? or "+
                " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                if(Jenisjual.getSelectedItem().equals("Karyawan")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("karyawan"),0,0,0,0,0,0});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("jualbebas"),0,0,0,0,0,0});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("beliluar"),0,0,0,0,0,0});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("ralan"),0,0,0,0,0,0});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("kelas1"),0,0,0,0,0,0});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("kelas2"),0,0,0,0,0,0});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("kelas3"),0,0,0,0,0,0});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("utama"),0,0,0,0,0,0});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VIP")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("vip"),0,0,0,0,0,0});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("vvip"),0,0,0,0,0,0});
                    } 
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
       
    private void getData(){
        row=tbDokter.getSelectedRow();
        if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(row!= -1){ 
            try {
                if(Valid.SetAngka(tabMode.getValueAt(row,0).toString())>0){
                    int kolom=tbDokter.getSelectedColumn();  
                    if(kolom==1){    
                        try {
                            stokbarang=0;   
                            psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                            try {
                                psstok.setString(1,kdgudang.getText());
                                psstok.setString(2,tbDokter.getValueAt(row,1).toString());
                                rsstok=psstok.executeQuery();
                                if(rsstok.next()){
                                    stokbarang=rsstok.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rsstok!=null){
                                    rsstok.close();
                                }
                                if(psstok!=null){
                                    psstok.close();
                                }
                            }  
                                
                            tbDokter.setValueAt(stokbarang,row,11);
                            y=0;
                            try {
                                y=Double.parseDouble(tabMode.getValueAt(row,0).toString());
                            } catch (Exception e) {
                                y=0;
                            }
                            if(stokbarang<y){
                                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                tbDokter.setValueAt("",row,0);
                            }
                        } catch (Exception e) {
                            tbDokter.setValueAt(0,row,11);
                        }                            

                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,0).toString())*Double.parseDouble(tabMode.getValueAt(row,5).toString()), row,6);                   
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,6);                   
                        }

                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,0).toString())*Double.parseDouble(tabMode.getValueAt(row,5).toString()), row,10);                                                     
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,10);                                                     
                        }              
                    }else if(kolom==5){    
                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,0).toString())*Double.parseDouble(tabMode.getValueAt(row,5).toString()), row,6);                   
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,6);                   
                        }

                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,0).toString())*Double.parseDouble(tabMode.getValueAt(row,5).toString()), row,10);                                                     
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,10);                                                     
                        }              
                    }else if(kolom==7){
                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())*(Double.parseDouble(tabMode.getValueAt(row,7).toString())/100), row,8); 
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString()), row,10);
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,8); 
                            tabMode.setValueAt(0, row,10);
                            tabMode.setValueAt(0, row,7); 
                        }             
                    }else if(kolom==8){
                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString()), row,10);
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,10);
                        }                 
                    }else if(kolom==9){
                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString()), row,10);
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,10);
                        }              
                    }
                }
            } catch (Exception e) {
            }             
            
            ttl=0;
            y=0;
            int row2=tabMode.getRowCount();
            for(int r=0;r<row2;r++){ 
                try {
                    y=Double.parseDouble(tabMode.getValueAt(r,10).toString()); 
                } catch (Exception e) {
                    y=0;
                }
                ttl=ttl+y;
            }
            LTotal.setText(Valid.SetAngka(ttl));
            isKembali();
        }
    }
    
    
    private void isKembali(){
        if(!Bayar.getText().trim().equals("")) {
            bayar=Double.parseDouble(Bayar.getText()); 
        }
        if(ttl>0) {
            total=ttl; 
        }
        if(!PPN.getText().trim().equals("")) {
            ppn=Double.parseDouble(PPN.getText()); 
        }
        if(ppn>0){
            besarppn=(ppn/100)*total;
            BesarPPN.setText(Valid.SetAngka(besarppn));
        }else{
            besarppn=0;
            BesarPPN.setText("0");
        }
        
        tagihanppn=besarppn+total;
        TagihanPPn.setText(Valid.SetAngka(tagihanppn));        
        LKembali.setText(Valid.SetAngka(bayar-tagihanppn));     
    }
    
    public void isCek(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_jual,6),signed)),0) from penjualan ","PJ",6,NoNota); 
        TCari.requestFocus();
        Sequel.cariIsi("select kd_bangsal from set_lokasi",kdgudang);
        Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?",nmgudang,kdgudang.getText());
        if(var.getjml2()>=1){
            kdptg.setEditable(false);
            BtnPtg.setEnabled(false);
            BtnSimpan.setEnabled(var.getpenjualan_obat());
            BtnTambah.setEnabled(var.getobat());
            kdptg.setText(var.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }      
    }
    
    public void setPasien(String norm){
        kdmem.setText(norm);
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
    }
    
    

 
}

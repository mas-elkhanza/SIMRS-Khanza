package toko;
import fungsi.WarnaTable2;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

public class TokoPenjualan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private riwayattoko Trackbarang=new riwayattoko();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private double ttl=0,ttlhpp=0,y=0,z=0,stokbarang=0,bayar=0,total=0,ppn=0,besarppn=0,tagihanppn=0,ongkir=0;
    private int jml=0,i=0,row,kolom=0,reply,index;
    private String Penjualan_Toko=Sequel.cariIsi("select Penjualan_Toko from set_akun"),
            HPP_Barang_Toko=Sequel.cariIsi("select HPP_Barang_Toko from set_akun"),
            Persediaan_Barang_Toko=Sequel.cariIsi("select Persediaan_Barang_Toko from set_akun");
    private PreparedStatement ps;
    private ResultSet rs;
    private String[] kodebarang,namabarang,kategori,satuan;
    private double[] harga,hbeli,jumlah,subtotal,diskon,besardiskon,totaljual,tambahan,stok;
    private WarnaTable2 warna=new WarnaTable2();
    private String notatoko="No";
    private boolean sukses=true;
    private TokoCariPenjualan carijual=new TokoCariPenjualan(null,false);
    
    

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public TokoPenjualan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "Jml","Kode Barang","Nama Barang","Kategori","Satuan","Harga(Rp)",
                "Subtotal(Rp)","Ptg(%)","Ptg(Rp)","Tmb(Rp)","Total(Rp)","Stok","H Beli"
        }){
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
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
               return types [columnIndex];
            }
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(42);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else if(i==4){
                column.setPreferredWidth(45);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(45);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(35);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        warna.kolom=0;
        tbObat.setDefaultRenderer(Object.class,warna);

        
        NoNota.setDocument(new batasInput((byte)15).getKata(NoNota));
        kdmem.setDocument(new batasInput((byte)10).getKata(kdmem));
        catatan.setDocument(new batasInput((byte)40).getKata(catatan));
        Bayar.setDocument(new batasInput((byte)14).getOnlyAngka(Bayar));
        Ongkir.setDocument(new batasInput((byte)14).getOnlyAngka(Ongkir));     
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
        
        Bayar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void removeUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void changedUpdate(DocumentEvent e) {isKembali();}
        });
        
        TCari.requestFocus();
        
        carijual.member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("Penjualan")){
                    if(carijual.member.getTable().getSelectedRow()!= -1){                   
                        kdmem.setText(carijual.member.getTable().getValueAt(carijual.member.getTable().getSelectedRow(),0).toString());
                        nmmem.setText(carijual.member.getTable().getValueAt(carijual.member.getTable().getSelectedRow(),1).toString());
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
        
        carijual.member.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("Penjualan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        carijual.member.dispose();
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
                if(akses.getform().equals("Penjualan")){
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
        
        Valid.loadCombo(CmbAkun,"nama_bayar","akun_bayar");
        try {
            PPN.setText(Sequel.cariIsi("select ppn from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString()));
        } catch (Exception e) {
            PPN.setText("0");
        }
        
        try {
            notatoko=Sequel.cariIsi("select cetaknotasimpantoko from set_nota");
            if(notatoko.equals("")){
                notatoko="No";
            }
        } catch (Exception e) {
            notatoko="No"; 
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
        internalFrame1 = new widget.InternalFrame();
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
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        TagihanPPn = new widget.Label();
        BesarPPN = new widget.TextBox();
        PPN = new widget.TextBox();
        label21 = new widget.Label();
        Ongkir = new widget.TextBox();
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
        jLabel10 = new widget.Label();
        CmbAkun = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        tbObat = new widget.Table();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(180, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Penjualan Barang Toko / Minimarket / Koperasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        label10.setText("Jumlah Total :");
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

        jLabel11.setText("PPN(%) :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi5.add(jLabel11);
        jLabel11.setBounds(256, 10, 80, 23);

        jLabel12.setText("Total Tagihan :");
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
        BesarPPN.setBounds(382, 10, 158, 23);

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
        PPN.setBounds(340, 10, 40, 23);

        label21.setText("Ongkir :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi5.add(label21);
        label21.setBounds(556, 10, 80, 23);

        Ongkir.setText("0");
        Ongkir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Ongkir.setName("Ongkir"); // NOI18N
        Ongkir.setPreferredSize(new java.awt.Dimension(150, 23));
        Ongkir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OngkirKeyPressed(evt);
            }
        });
        panelisi5.add(Ongkir);
        Ongkir.setBounds(640, 10, 157, 23);

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
        NoNota.setBounds(74, 10, 140, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(365, 40, 80, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(449, 10, 100, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(449, 40, 100, 23);

        label16.setText("Member :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(365, 10, 80, 23);

        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(550, 10, 222, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(550, 40, 222, 23);

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

        Jenisjual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Distributor", "Grosir", "Retail" }));
        Jenisjual.setSelectedIndex(2);
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
        Jenisjual.setBounds(229, 70, 120, 23);

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
        catatan.setBounds(74, 40, 275, 23);

        label12.setText("Jns.Jual :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(170, 70, 55, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 70, 70, 23);

        Tgl.setDisplayFormat("dd-MM-yyyy");
        Tgl.setName("Tgl"); // NOI18N
        Tgl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglItemStateChanged(evt);
            }
        });
        Tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKeyPressed(evt);
            }
        });
        panelisi3.add(Tgl);
        Tgl.setBounds(74, 70, 95, 23);

        jLabel10.setText("Akun Bayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi3.add(jLabel10);
        jLabel10.setBounds(365, 70, 80, 23);

        CmbAkun.setName("CmbAkun"); // NOI18N
        CmbAkun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbAkunKeyPressed(evt);
            }
        });
        panelisi3.add(CmbAkun);
        CmbAkun.setBounds(449, 70, 353, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbObatPropertyChange(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbObat);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {                  
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                                     
                    getData();                     
                    TCari.setText("");
                    TCari.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    switch (tbObat.getSelectedColumn()) {
                        case 0:
                            tbObat.setValueAt("", tbObat.getSelectedRow(),0);
                            break;
                        case 7:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),7);
                            break;
                        case 8:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),8);
                            break;
                        case 9:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),9);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                } 
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_BACK_SPACE){
                try {
                    switch (tbObat.getSelectedColumn()) {
                        case 0:
                            tbObat.setValueAt("", tbObat.getSelectedRow(),0);
                            break;
                        case 7:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),7);
                            break;
                        case 8:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),8);
                            break;
                        case 9:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),9);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                }     
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        carijual.emptTeks();  
        carijual.isCek();
        carijual.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carijual.setLocationRelativeTo(internalFrame1);
        carijual.setAlwaysOnTop(false);
        carijual.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        carijual.barang.dispose();
        carijual.member.dispose();
        carijual.petugas.dispose();
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
            Valid.textKosong(kdmem,"Member");
        }else if(nmptg.getText().trim().equals("")||nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            tbObat.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan penjualan...!!!!");
            tbObat.requestFocus();
        }else {
            reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;    
                if(Sequel.menyimpantf2("tokopenjualan","?,?,?,?,?,?,?,?,?,?,?,?","No.Nota",12,new String[]{
                        NoNota.getText(),Valid.SetTgl(Tgl.getSelectedItem()+""),kdptg.getText(),kdmem.getText(),nmmem.getText(),catatan.getText(),Jenisjual.getSelectedItem().toString(),
                        ongkir+"",besarppn+"",Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString()),tagihanppn+"",CmbAkun.getSelectedItem().toString()
                    })==true){
                        isSimpan();
                }else{
                    autoNomor();
                    if(Sequel.menyimpantf2("tokopenjualan","?,?,?,?,?,?,?,?,?,?,?,?","No.Nota",12,new String[]{
                            NoNota.getText(),Valid.SetTgl(Tgl.getSelectedItem()+""),kdptg.getText(),kdmem.getText(),nmmem.getText(),catatan.getText(),Jenisjual.getSelectedItem().toString(),
                            ongkir+"",besarppn+"",Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString()),tagihanppn+"",CmbAkun.getSelectedItem().toString()
                        })==true){
                            isSimpan();
                    }else{
                        sukses=false;
                        autoNomor();
                    } 
                }
                
                if(sukses==true){
                    if(notatoko.equals("Yes")){
                        BtnNotaActionPerformed(null);
                    }
                    Sequel.Commit();
                    Valid.tabelKosong(tabMode);
                    tampil();
                    tagihanppn=0;
                    ttl=0;
                    ttlhpp=0;
                    bayar=0;
                    besarppn=0;
                    total=0;
                    ppn=0;
                    ongkir=0;
                    LTotal.setText("0");
                    Bayar.setText("0");
                    Ongkir.setText("0");
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                autoNomor();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,Bayar,BtnCari);
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
        if(NoNota.getText().trim().equals("")){
            Valid.textKosong(NoNota,"No.Nota");
        }else if(nmmem.getText().trim().equals("")||kdmem.getText().trim().equals("")){
            Valid.textKosong(kdmem,"Member");
        }else if(nmptg.getText().trim().equals("")||nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            tbObat.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan penjualan...!!!!");
            tbObat.requestFocus();
        }else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.queryu("truncate table temporary_toko");
            for(i=0;i<tabMode.getRowCount();i++){  
                try {
                    if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                           Sequel.menyimpan2("temporary_toko","'0','"+
                                   tabMode.getValueAt(i,0).toString()+"','"+
                                   tabMode.getValueAt(i,1).toString()+"','"+
                                   tabMode.getValueAt(i,2).toString()+"','"+
                                   tabMode.getValueAt(i,3).toString()+"','"+
                                   tabMode.getValueAt(i,4).toString()+"','"+
                                   tabMode.getValueAt(i,5).toString()+"','"+
                                   tabMode.getValueAt(i,6).toString()+"','"+
                                   tabMode.getValueAt(i,8).toString()+"','"+
                                   tabMode.getValueAt(i,9).toString()+"','"+
                                   tabMode.getValueAt(i,10).toString()+"','"+
                                   tabMode.getValueAt(i,11).toString()+"','"+
                                   tabMode.getValueAt(i,12).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penjualan"); 
                    }
                } catch (Exception e) {
                }                
            }
            this.setCursor(Cursor.getDefaultCursor());
            Valid.panggilUrl("billing/NotaToko.php?nonota="+NoNota.getText()+"&besarppn="+besarppn+"&bayar="+Bayar.getText()+"&ongkir="+Ongkir.getText()+"&tanggal="+Valid.SetTgl(Tgl.getSelectedItem()+"")+"&catatan="+catatan.getText().replaceAll(" ","_")+"&petugas="+nmptg.getText().replaceAll(" ","_")+"&member="+nmmem.getText().replaceAll(" ","_")+"&nomember="+kdmem.getText().replaceAll(" ","_"));
        }
    }//GEN-LAST:event_BtnNotaActionPerformed

    private void BtnNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNotaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNotaKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari1ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbObat.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
    tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
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
        akses.setform("Penjualan");
        carijual.member.isCek();
        carijual.member.emptTeks();
        carijual.member.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carijual.member.setLocationRelativeTo(internalFrame1);
        carijual.member.setAlwaysOnTop(false);
        carijual.member.setVisible(true);
}//GEN-LAST:event_BtnMemActionPerformed

private void BtnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtgActionPerformed
        akses.setform("Penjualan");
        carijual.petugas.emptTeks();
        carijual.petugas.isCek();
        carijual.petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        carijual.barang.emptTeks();
        carijual.barang.isCek();
        carijual.barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carijual.barang.setLocationRelativeTo(internalFrame1);
        carijual.barang.setAlwaysOnTop(false);
        carijual.barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

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
            Ongkir.requestFocus();
        }
    }//GEN-LAST:event_PPNKeyPressed

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void OngkirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OngkirKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isKembali();
            Bayar.requestFocus();
        }
    }//GEN-LAST:event_OngkirKeyPressed

    private void TglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TglItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            TokoPenjualan dialog = new TokoPenjualan(new javax.swing.JFrame(), true);
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
    private widget.TextBox Ongkir;
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
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       row=tabMode.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbObat.getValueAt(i,0).toString())>0){
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
        hbeli=new double[jml];
        
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbObat.getValueAt(i,0).toString())>0){
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
                    hbeli[index]=Double.parseDouble(tabMode.getValueAt(i,12).toString());
                    index++;
                }
            } catch (Exception e) {
            }                
        }
        
        Valid.tabelKosong(tabMode);
        
        for(i=0;i<jml;i++){            
            tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],harga[i],subtotal[i],diskon[i],besardiskon[i],tambahan[i],totaljual[i],stok[i],hbeli[i]});
        }
        
        try{
            ps=koneksi.prepareStatement(
                "select tokobarang.kode_brng,tokobarang.nama_brng,tokojenisbarang.nm_jenis,tokobarang.stok, "+
                "tokobarang.kode_sat,tokobarang.distributor,tokobarang.grosir,tokobarang.retail,tokobarang.dasar "+
                "from tokobarang inner join tokojenisbarang on tokobarang.jenis=tokojenisbarang.kd_jenis "+
                "where tokobarang.stok>0 and tokobarang.status='1' and "+
                "(tokobarang.kode_brng like ? or tokobarang.nama_brng like ? or tokojenisbarang.nm_jenis like ?) order by tokobarang.nama_brng");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                if(Jenisjual.getSelectedItem().equals("Distributor")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nm_jenis"),rs.getString("kode_sat"),
                            rs.getDouble("distributor"),0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Grosir")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nm_jenis"),rs.getString("kode_sat"),
                            rs.getDouble("grosir"),0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Retail")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nm_jenis"),rs.getString("kode_sat"),
                            rs.getDouble("retail"),0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar")
                        });
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
        row=tbObat.getSelectedRow();
        if(row!= -1){ 
            if(!tbObat.getValueAt(row,0).toString().equals("")){
                kolom=tbObat.getSelectedColumn();
                if(Double.parseDouble(tabMode.getValueAt(row,0).toString())>0){
                    stokbarang=Double.parseDouble(tabMode.getValueAt(row,11).toString());
                    y=Valid.SetAngka(tbObat.getValueAt(row,0).toString());
                    if(stokbarang<y){
                        tabMode.setValueAt("",row,0);
                        JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                        tbObat.requestFocus();
                    }
                    if((kolom==0)||(kolom==1)||(kolom==2)||(kolom==3)||(kolom==4)||(kolom==5)){    
                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,0).toString())*Double.parseDouble(tabMode.getValueAt(row,5).toString()), row,6);                   
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,6);                   
                        }

                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString()), row,10);
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,10);                                                     
                        }              
                    }else if(kolom==7){ 
                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())*(Double.parseDouble(tabMode.getValueAt(row,7).toString())/100), row,8);                  
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,8);                   
                        }
                        
                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString()), row,10);
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,10);                                                     
                        }
                    }else if((kolom==8)||(kolom==9)){ 
                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString()), row,10);
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,10);                                                     
                        }
                    }
                }
            }else{
                tabMode.setValueAt(0, row,6);
                tabMode.setValueAt(0, row,7);
                tabMode.setValueAt(0, row,8);
                tabMode.setValueAt(0, row,9);
                tabMode.setValueAt(0, row,10);
            }
        }
        ttl=0;
        ttlhpp=0;
        y=0;
        z=0;
        
        for(int r=0;r<tabMode.getRowCount();r++){ 
            try {
                y=Double.parseDouble(tabMode.getValueAt(r,10).toString()); 
            } catch (Exception e) {
                y=0;
            }
            ttl=ttl+y;

            try {
                z=Double.parseDouble(tabMode.getValueAt(r,12).toString())*Double.parseDouble(tabMode.getValueAt(r,0).toString()); 
            } catch (Exception e) {
                z=0;
            }
            ttlhpp=ttlhpp+z;
        }
        
        LTotal.setText(Valid.SetAngka(ttl));
        isKembali();
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
        if(!Ongkir.getText().trim().equals("")) {
            ongkir=Double.parseDouble(Ongkir.getText()); 
        }
        if(ppn>0){
            besarppn=(ppn/100)*total;
            BesarPPN.setText(Valid.SetAngka(besarppn));
        }else{
            besarppn=0;
            BesarPPN.setText("0");
        }
        
        tagihanppn=besarppn+total+ongkir;
        TagihanPPn.setText(Valid.SetAngka(tagihanppn));        
        LKembali.setText(Valid.SetAngka(bayar-tagihanppn));     
    }
    
    public void isCek(){
        autoNomor();
        Ongkir.setText("0");
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            BtnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.gettoko_penjualan());
            BtnTambah.setEnabled(akses.gettoko_barang());
            kdptg.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }    
        if(Sequel.cariIsi("select tampilkan_tombol_nota_toko from set_nota").equals("Yes")){
            BtnNota.setVisible(true);
        }else{
            if(akses.getkode().equals("Admin Utama")){
                BtnNota.setVisible(true);
            }else{
                BtnNota.setVisible(false);
            }            
        }
    }
    
    public void autoNomor(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_jual,5),signed)),0) from tokopenjualan where tgl_jual='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' ",
                "TJ"+Tgl.getSelectedItem().toString().substring(6,10)+Tgl.getSelectedItem().toString().substring(3,5)+Tgl.getSelectedItem().toString().substring(0,2),5,NoNota); 
    }

    private void isSimpan() {
        for(i=0;i<tabMode.getRowCount();i++){  
            if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                if(Sequel.menyimpantf2("toko_detail_jual","?,?,?,?,?,?,?,?,?,?,?","Barang",11,new String[]{
                        NoNota.getText(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,4).toString(),tabMode.getValueAt(i,5).toString(), 
                        tabMode.getValueAt(i,12).toString(),tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,6).toString(),
                        tabMode.getValueAt(i,7).toString(),tabMode.getValueAt(i,8).toString(),tabMode.getValueAt(i,9).toString(),
                        tabMode.getValueAt(i,10).toString()
                    })==true){
                    Trackbarang.catatRiwayat(tabMode.getValueAt(i,1).toString(),0,Valid.SetAngka(tabMode.getValueAt(i,0).toString()),"Penjualan", akses.getkode(),"Simpan");
                    Sequel.mengedit("tokobarang","kode_brng=?","stok=stok-?",2,new String[]{
                        tbObat.getValueAt(i,0).toString(),tbObat.getValueAt(i,1).toString()
                    });
                }else{
                    sukses=false;
                }
            }
        }
        if(sukses==true){
            Sequel.queryu("delete from tampjurnal");                    
            Sequel.menyimpan2("tampjurnal","'"+Penjualan_Toko+"','PENJUALAN TOKO','0','"+tagihanppn+"'","Rekening");    
            Sequel.menyimpan2("tampjurnal","'"+Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",CmbAkun.getSelectedItem().toString())+"','CARA BAYAR','"+tagihanppn+"','0'","Rekening"); 
            Sequel.menyimpan2("tampjurnal","'"+HPP_Barang_Toko+"','HPP Barang Toko','"+ttlhpp+"','0'","Rekening");    
            Sequel.menyimpan2("tampjurnal","'"+Persediaan_Barang_Toko+"','Persediaan Barang Toko','0','"+ttlhpp+"'","Rekening");                              
            sukses=jur.simpanJurnal(NoNota.getText(),Valid.SetTgl(Tgl.getSelectedItem()+""),"U","PENJUALAN TOKO / MINIMARKET / KOPERASI, OLEH "+akses.getkode());     
         }
    }

    
}

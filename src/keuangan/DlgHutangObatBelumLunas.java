

package keuangan;

import fungsi.WarnaTable3;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgSuplier;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class DlgHutangObatBelumLunas extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs;
    private DlgSuplier suplier=new DlgSuplier(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private int row=0;
    private String koderekening="",tanggaldatang="",tanggaltempo="";
    private double sisahutang=0,cicilan=0;
    private Jurnal jur=new Jurnal();
    private WarnaTable3 warna=new WarnaTable3();

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgHutangObatBelumLunas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        Object[] rowRwJlDr={
            "P","No.Faktur","No.Order","Supplier","Petugas Penerima",
            "Tgl.Faktur","Tgl.Datang","Tgl.Tempo","Posisi Barang","Tagihan",
            "Sisa Hutang","Pembayaran","Sisa","Bank Suplier","No.Rekening"
        };
        tabMode=new DefaultTableModel(null,rowRwJlDr){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==11)||(colIndex==0)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 15; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(22);
            }else if(i==1){
                column.setPreferredWidth(85);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(140);
            }else if(i==4){
                column.setPreferredWidth(140);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(75);
            }else if(i==8){
                column.setPreferredWidth(140);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setPreferredWidth(110);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else{
                column.setPreferredWidth(80);
            }
        }
        warna.kolom=11;
        tbBangsal.setDefaultRenderer(Object.class,warna);

        no_bukti.setDocument(new batasInput((byte)20).getKata(no_bukti));
        nip.setDocument(new batasInput((byte)20).getKata(nip));
        keterangan.setDocument(new batasInput((byte)100).getKata(keterangan));        
        
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
                if(suplier.getTable().getSelectedRow()!= -1){
                    kdsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),0).toString());
                    nmsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
                    tampil();
                }      
                kdsup.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {suplier.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    suplier.dispose();
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
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    nama_petugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }            
                nip.requestFocus();                
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
        
        Valid.loadCombo(nama_bayar,"nama_bayar","akun_bayar");

    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label19 = new widget.Label();
        kdsup = new widget.TextBox();
        nmsup = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        BtnBayar = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi5 = new widget.panelisi();
        ChkTanggalDatang = new widget.CekBox();
        TglDatang1 = new widget.Tanggal();
        label18 = new widget.Label();
        TglDatang2 = new widget.Tanggal();
        label21 = new widget.Label();
        ChkTanggalTempo = new widget.CekBox();
        TglTempo1 = new widget.Tanggal();
        label20 = new widget.Label();
        TglTempo2 = new widget.Tanggal();
        panelisi4 = new widget.panelisi();
        label32 = new widget.Label();
        label36 = new widget.Label();
        no_bukti = new widget.TextBox();
        label16 = new widget.Label();
        nip = new widget.TextBox();
        nama_petugas = new widget.TextBox();
        tgl_bayar = new widget.Tanggal();
        BtnPetugas = new widget.Button();
        jLabel12 = new widget.Label();
        nama_bayar = new widget.ComboBox();
        keterangan = new widget.TextBox();
        label39 = new widget.Label();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(70, 70, 70));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(70, 70, 70));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Hutang Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setToolTipText("Silahkan centang di depan untuk memilih data hutang yang mau dibayar");
        tbBangsal.setComponentPopupMenu(Popup);
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBangsalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 145));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label19.setText("Supplier :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label19);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(70, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(180, 23));
        panelisi3.add(nmsup);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi3.add(BtnSeek2);

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi3.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

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
        panelisi3.add(BtnCari);

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
        panelisi3.add(BtnAll);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(70, 70, 70));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total Hutang Ke Supplier :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(133, 23));
        panelisi1.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        LCount.setForeground(new java.awt.Color(70, 70, 70));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(295, 23));
        panelisi1.add(LCount);

        BtnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnBayar.setMnemonic('B');
        BtnBayar.setText("Bayar");
        BtnBayar.setToolTipText("Alt+B");
        BtnBayar.setName("BtnBayar"); // NOI18N
        BtnBayar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBayarActionPerformed(evt);
            }
        });
        BtnBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBayarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBayar);

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

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        ChkTanggalDatang.setText("Tgl.Datang :");
        ChkTanggalDatang.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkTanggalDatang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkTanggalDatang.setName("ChkTanggalDatang"); // NOI18N
        ChkTanggalDatang.setOpaque(false);
        ChkTanggalDatang.setPreferredSize(new java.awt.Dimension(93, 23));
        ChkTanggalDatang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkTanggalDatangItemStateChanged(evt);
            }
        });
        panelisi5.add(ChkTanggalDatang);

        TglDatang1.setDisplayFormat("dd-MM-yyyy");
        TglDatang1.setName("TglDatang1"); // NOI18N
        TglDatang1.setPreferredSize(new java.awt.Dimension(97, 23));
        TglDatang1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglDatang1KeyPressed(evt);
            }
        });
        panelisi5.add(TglDatang1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi5.add(label18);

        TglDatang2.setDisplayFormat("dd-MM-yyyy");
        TglDatang2.setName("TglDatang2"); // NOI18N
        TglDatang2.setPreferredSize(new java.awt.Dimension(97, 23));
        TglDatang2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglDatang2KeyPressed(evt);
            }
        });
        panelisi5.add(TglDatang2);

        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi5.add(label21);

        ChkTanggalTempo.setText("Tgl.Tempo");
        ChkTanggalTempo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkTanggalTempo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkTanggalTempo.setName("ChkTanggalTempo"); // NOI18N
        ChkTanggalTempo.setOpaque(false);
        ChkTanggalTempo.setPreferredSize(new java.awt.Dimension(85, 23));
        ChkTanggalTempo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkTanggalTempoItemStateChanged(evt);
            }
        });
        panelisi5.add(ChkTanggalTempo);

        TglTempo1.setDisplayFormat("dd-MM-yyyy");
        TglTempo1.setName("TglTempo1"); // NOI18N
        TglTempo1.setPreferredSize(new java.awt.Dimension(97, 23));
        TglTempo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglTempo1KeyPressed(evt);
            }
        });
        panelisi5.add(TglTempo1);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label20.setText("s.d.");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi5.add(label20);

        TglTempo2.setDisplayFormat("dd-MM-yyyy");
        TglTempo2.setName("TglTempo2"); // NOI18N
        TglTempo2.setPreferredSize(new java.awt.Dimension(97, 23));
        TglTempo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglTempo2KeyPressed(evt);
            }
        });
        panelisi5.add(TglTempo2);

        jPanel1.add(panelisi5, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 74));
        panelisi4.setLayout(null);

        label32.setText("Tgl.Bayar :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(167, 10, 70, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(335, 40, 80, 23);

        no_bukti.setHighlighter(null);
        no_bukti.setName("no_bukti"); // NOI18N
        no_bukti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                no_buktiKeyPressed(evt);
            }
        });
        panelisi4.add(no_bukti);
        no_bukti.setBounds(78, 10, 90, 23);

        label16.setText("Petugas :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label16);
        label16.setBounds(335, 10, 80, 23);

        nip.setName("nip"); // NOI18N
        nip.setPreferredSize(new java.awt.Dimension(80, 23));
        nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nipKeyPressed(evt);
            }
        });
        panelisi4.add(nip);
        nip.setBounds(418, 10, 100, 23);

        nama_petugas.setEditable(false);
        nama_petugas.setName("nama_petugas"); // NOI18N
        nama_petugas.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nama_petugas);
        nama_petugas.setBounds(520, 10, 200, 23);

        tgl_bayar.setDisplayFormat("dd-MM-yyyy");
        tgl_bayar.setName("tgl_bayar"); // NOI18N
        tgl_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_bayarKeyPressed(evt);
            }
        });
        panelisi4.add(tgl_bayar);
        tgl_bayar.setBounds(240, 10, 90, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('1');
        BtnPetugas.setToolTipText("ALt+1");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        panelisi4.add(BtnPetugas);
        BtnPetugas.setBounds(722, 10, 28, 23);

        jLabel12.setText("Akun Bayar :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelisi4.add(jLabel12);
        jLabel12.setBounds(0, 40, 75, 23);

        nama_bayar.setName("nama_bayar"); // NOI18N
        nama_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nama_bayarKeyPressed(evt);
            }
        });
        panelisi4.add(nama_bayar);
        nama_bayar.setBounds(78, 40, 251, 23);

        keterangan.setHighlighter(null);
        keterangan.setName("keterangan"); // NOI18N
        keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keteranganKeyPressed(evt);
            }
        });
        panelisi4.add(keterangan);
        keterangan.setBounds(418, 40, 332, 23);

        label39.setText("No.Bukti :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(0, 10, 75, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                    Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,10).toString()))+"','"+
                                tabMode.getValueAt(i,13).toString()+"','"+
                                tabMode.getValueAt(i,14).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Piutang Pasien"); 
            }
            Sequel.menyimpan("temporary","'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
            Sequel.menyimpan("temporary","'0','','','TOTAL HUTANG :','"+LCount.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
            
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptHutangBelumLunas.jasper","report","::[ Data Hutang Obat, Alkes & BHP Medis ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBayar, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdsup.setText("");
        nmsup.setText("");
        tampil();

}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(tbBangsal.getSelectedColumn()==0){
                    if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString().equals("0")){
                        tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString()), tbBangsal.getSelectedRow(),11);
                    }
                    if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                        tbBangsal.setValueAt(
                            (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString())-
                            Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString()))
                            ,tbBangsal.getSelectedRow(),10);
                    }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),11);
                        tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString()),tbBangsal.getSelectedRow(),10);
                    }
                }                
            }
        }
}//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                    tbBangsal.setValueAt(
                            (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString())-
                            Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString()))
                            ,tbBangsal.getSelectedRow(),10);
                }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                    tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString()),tbBangsal.getSelectedRow(),10);
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                if(tbBangsal.getSelectedColumn()==11){
                   tbBangsal.setValueAt(0, tbBangsal.getSelectedRow(),11); 
                }
            }
        }
}//GEN-LAST:event_tbBangsalKeyPressed

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
            tampil();
        }else{
            Valid.pindah(evt, TKd, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        no_bukti.requestFocus();
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmsup,kdsup.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmsup,kdsup.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmsup,kdsup.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdsupKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBayarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(no_bukti.getText().trim().equals("")){
            Valid.textKosong(no_bukti,"No.Bukti");
        }else if(nip.getText().trim().equals("")||nama_petugas.getText().trim().equals("")){
            Valid.textKosong(nip,"Petugas");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
        }else if(keterangan.getText().trim().equals("")){
            Valid.textKosong(keterangan,"Keterangan");
        }else if(tabMode.getRowCount()!=0){
            koderekening=Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",nama_bayar.getSelectedItem().toString());
            
            row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                if(tabMode.getValueAt(i,0).toString().equals("true")){
                    if(Double.parseDouble(tbBangsal.getValueAt(i,11).toString())>0){
                        if(Sequel.menyimpantf("bayar_pemesanan","?,?,?,?,?,?,?","Data", 7,new String[]{
                            Valid.SetTgl(tgl_bayar.getSelectedItem()+""),tabMode.getValueAt(i,1).toString(),nip.getText(),
                            tabMode.getValueAt(i,11).toString(),keterangan.getText(),nama_bayar.getSelectedItem().toString(),
                            no_bukti.getText()
                        })==true){
                            if((Double.parseDouble(tabMode.getValueAt(i,10).toString())<=0)||(Double.parseDouble(tabMode.getValueAt(i,10).toString())<=-0)){
                                Sequel.mengedit("pemesanan","no_faktur='"+tabMode.getValueAt(i,1).toString()+"'","status='Sudah Dibayar'");
                            }else{
                                Sequel.mengedit("pemesanan","no_faktur='"+tabMode.getValueAt(i,1).toString()+"'","status='Belum Lunas'");
                            } 
                            Sequel.queryu("delete from tampjurnal");
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                Sequel.cariIsi("select Bayar_Pemesanan_Obat from set_akun"),"HUTANG USAHA",tabMode.getValueAt(i,11).toString(),"0"
                            });                     
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                koderekening,nama_bayar.getSelectedItem().toString(),"0",tabMode.getValueAt(i,11).toString()
                            });    
                            jur.simpanJurnal(no_bukti.getText(),Valid.SetTgl(tgl_bayar.getSelectedItem()+""),"U","BAYAR PELUNASAN HUTANG OBAT/BHP/ALKES NO.FAKTUR "+tabMode.getValueAt(i,1).toString());                            
                        }
                    }                        
                }
            }
            
            tampil();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnBayarActionPerformed

    private void BtnBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBayarKeyPressed

    private void no_buktiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_no_buktiKeyPressed
       Valid.pindah(evt,TCari,tgl_bayar);
    }//GEN-LAST:event_no_buktiKeyPressed

    private void nipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nama_petugas,nip.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            nama_bayar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_nipKeyPressed

    private void tgl_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_bayarKeyPressed
        Valid.pindah(evt,no_bukti,nama_bayar);
    }//GEN-LAST:event_tgl_bayarKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void nama_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nama_bayarKeyPressed
        Valid.pindah(evt,tgl_bayar,nip);
    }//GEN-LAST:event_nama_bayarKeyPressed

    private void keteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keteranganKeyPressed
        Valid.pindah(evt,nip,BtnBayar);
    }//GEN-LAST:event_keteranganKeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(row=0;row<tbBangsal.getRowCount();row++){
            tbBangsal.setValueAt(false,row,0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for(row=0;row<tbBangsal.getRowCount();row++){
            tbBangsal.setValueAt(true,row,0);
        }
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void ChkTanggalDatangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkTanggalDatangItemStateChanged
        
    }//GEN-LAST:event_ChkTanggalDatangItemStateChanged

    private void TglDatang1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglDatang1KeyPressed
        Valid.pindah(evt, BtnKeluar, TglDatang2);
    }//GEN-LAST:event_TglDatang1KeyPressed

    private void TglDatang2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglDatang2KeyPressed
        Valid.pindah(evt, TglDatang1,TCari);
    }//GEN-LAST:event_TglDatang2KeyPressed

    private void ChkTanggalTempoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkTanggalTempoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkTanggalTempoItemStateChanged

    private void TglTempo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTempo1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTempo1KeyPressed

    private void TglTempo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTempo2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTempo2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgHutangObatBelumLunas dialog = new DlgHutangObatBelumLunas(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBayar;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.CekBox ChkTanggalDatang;
    private widget.CekBox ChkTanggalTempo;
    private javax.swing.JLabel LCount;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal TglDatang1;
    private widget.Tanggal TglDatang2;
    private widget.Tanggal TglTempo1;
    private widget.Tanggal TglTempo2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private widget.Label jLabel12;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdsup;
    private widget.TextBox keterangan;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label32;
    private widget.Label label36;
    private widget.Label label39;
    private widget.ComboBox nama_bayar;
    private widget.TextBox nama_petugas;
    private widget.TextBox nip;
    private widget.TextBox nmsup;
    private widget.TextBox no_bukti;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSemua;
    private widget.Table tbBangsal;
    private widget.Tanggal tgl_bayar;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            if(ChkTanggalDatang.isSelected()==true){
                tanggaldatang=" pemesanan.tgl_pesan between '"+Valid.SetTgl(TglDatang1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglDatang2.getSelectedItem()+"")+"' and ";
            }
            if(ChkTanggalTempo.isSelected()==true){
                tanggaltempo=" pemesanan.tgl_tempo between '"+Valid.SetTgl(TglTempo1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglTempo2.getSelectedItem()+"")+"' and ";
            }
            ps=koneksi.prepareStatement(
                    "select pemesanan.no_faktur,pemesanan.no_order,datasuplier.nama_suplier, "+
                    "petugas.nama,pemesanan.tgl_tempo,pemesanan.tgl_pesan,pemesanan.tgl_faktur,bangsal.nm_bangsal,pemesanan.tagihan,"+
                    "(SELECT ifnull(SUM(besar_bayar),0) FROM bayar_pemesanan where bayar_pemesanan.no_faktur=pemesanan.no_faktur) as bayar, "+
                    "datasuplier.nama_bank,datasuplier.rekening from pemesanan inner join datasuplier inner join bangsal inner join petugas "+
                    "on pemesanan.kode_suplier=datasuplier.kode_suplier "+
                    "and pemesanan.nip=petugas.nip and pemesanan.kd_bangsal=bangsal.kd_bangsal where "+
                    tanggaldatang+tanggaltempo+"pemesanan.status<>'Sudah Dibayar' and datasuplier.nama_suplier like ? and pemesanan.no_faktur like ? or "+
                    tanggaldatang+tanggaltempo+"pemesanan.status<>'Sudah Dibayar' and datasuplier.nama_suplier like ? and pemesanan.no_order like ? or "+
                    tanggaldatang+tanggaltempo+"pemesanan.status<>'Sudah Dibayar' and datasuplier.nama_suplier like ? and pemesanan.tgl_tempo like ? or "+
                    tanggaldatang+tanggaltempo+"pemesanan.status<>'Sudah Dibayar' and datasuplier.nama_suplier like ? and datasuplier.nama_suplier like ? or "+
                    tanggaldatang+tanggaltempo+"pemesanan.status<>'Sudah Dibayar' and datasuplier.nama_suplier like ? and bangsal.nm_bangsal like ? or "+
                    tanggaldatang+tanggaltempo+"pemesanan.status<>'Sudah Dibayar' and datasuplier.nama_suplier like ? and petugas.nama like ? order by pemesanan.tgl_tempo ");
            try {
                ps.setString(1,"%"+nmsup.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+nmsup.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+nmsup.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+nmsup.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                ps.setString(9,"%"+nmsup.getText().trim()+"%");
                ps.setString(10,"%"+TCari.getText().trim()+"%");
                ps.setString(11,"%"+nmsup.getText().trim()+"%");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                sisahutang=0;
                cicilan=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_faktur"),rs.getString("no_order"),
                        rs.getString("nama_suplier"),rs.getString("nama"),rs.getString("tgl_faktur"),
                        rs.getString("tgl_pesan"),rs.getString("tgl_tempo"),rs.getString("nm_bangsal"),
                        rs.getDouble("tagihan"),(rs.getDouble("tagihan")-rs.getDouble("bayar")),
                        0,(rs.getDouble("tagihan")-rs.getDouble("bayar")),rs.getString("nama_bank"),rs.getString("rekening")
                    });
                    sisahutang=sisahutang+rs.getDouble("tagihan");
                    cicilan=cicilan+rs.getDouble("bayar");
                }
                LCount.setText(Valid.SetAngka(sisahutang-cicilan));
            } catch (Exception e) {
                System.out.println("Notifikasi Data Hutang: "+e);
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
}



package keuangan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

/**
 *
 * @author perpustakaan
 */
public final class KeuanganPiutangBelumLunas extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int row=0,i;
    private String koderekening="",notagihan="",nomorpemasukan="",carabayar="";
    private Jurnal jur=new Jurnal();
    private String status="",Diskon_Piutang="",Piutang_Tidak_Terbayar="",Lebih_Bayar_Piutang="";
    private double total=0,lebihbayar=0;
    private boolean sukses=true;
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganPiutangBelumLunas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        
        WindowHitungPaket.setSize(320,230);

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","No.Rawat/No.Tagihan","Tgl.Piutang","Pasien","Status","Total Piutang",
                "Uang Muka","Cicilan+Disk+T.Terbayar","Sisa Piutang","Jatuh Tempo","Cara Bayar","Bayar",
                "Diskon Bayar","Tidak Terbayar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==11)||(colIndex==12)||(colIndex==13)||(colIndex==0)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
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

        for (i = 0; i < 14; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(95);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(130);
            }else if(i==8){
                column.setPreferredWidth(95);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(120);
            }else if(i==11){
                column.setPreferredWidth(85);
            }else if(i==12){
                column.setPreferredWidth(85);
            }else if(i==13){
                column.setPreferredWidth(85);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        NilaiKapitasiPaket.setDocument(new batasInput((byte)20).getKata(NilaiKapitasiPaket));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(nmpenjab.getText().equals("")){
                            tampil();
                        }else{
                            tampilperakun();
                        }
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(nmpenjab.getText().equals("")){
                            tampil();
                        }else{
                            tampilperakun();
                        }
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(nmpenjab.getText().equals("")){
                            tampil();
                        }else{
                            tampilperakun();
                        }
                    }
                }
            });
        }  
        
        try {
            ps=koneksi.prepareStatement(
                    "select set_akun.Diskon_Piutang,set_akun.Piutang_Tidak_Terbayar,set_akun.Lebih_Bayar_Piutang from set_akun");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    Diskon_Piutang=rs.getString("Diskon_Piutang");
                    Piutang_Tidak_Terbayar=rs.getString("Piutang_Tidak_Terbayar");
                    Lebih_Bayar_Piutang=rs.getString("Lebih_Bayar_Piutang");
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    

     double sisapiutang=0,cicilan=0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDetailPiutang = new javax.swing.JMenuItem();
        ppPilihSemua = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        ppKapitasiPaket = new javax.swing.JMenuItem();
        WindowHitungPaket = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        jLabel42 = new widget.Label();
        panelisi5 = new widget.panelisi();
        BtnCloseHitungKapitasi = new widget.Button();
        BtnHitungKapitasi = new widget.Button();
        jLabel43 = new widget.Label();
        LebihBayarPiutang = new widget.TextBox();
        PersentaseBayarPaket = new widget.TextBox();
        jLabel41 = new widget.Label();
        NilaiKapitasiPaket = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel39 = new widget.Label();
        PiutangBelumDibayar = new widget.TextBox();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        PilihanKekurangan = new widget.ComboBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelisi4 = new widget.panelisi();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel11 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        BtnAll1 = new widget.Button();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label19 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        LCount1 = new javax.swing.JLabel();
        BtnBayar = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDetailPiutang.setBackground(new java.awt.Color(255, 255, 254));
        MnDetailPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDetailPiutang.setForeground(new java.awt.Color(50, 50, 50));
        MnDetailPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDetailPiutang.setText("Detail Piutang");
        MnDetailPiutang.setName("MnDetailPiutang"); // NOI18N
        MnDetailPiutang.setPreferredSize(new java.awt.Dimension(170, 26));
        MnDetailPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDetailPiutangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDetailPiutang);

        ppPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilihSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPilihSemua.setText("Pilih Semua");
        ppPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilihSemua.setName("ppPilihSemua"); // NOI18N
        ppPilihSemua.setPreferredSize(new java.awt.Dimension(170, 26));
        ppPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPilihSemua);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Hilangkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(170, 26));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBersihkan);

        ppKapitasiPaket.setBackground(new java.awt.Color(255, 255, 254));
        ppKapitasiPaket.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppKapitasiPaket.setForeground(new java.awt.Color(50, 50, 50));
        ppKapitasiPaket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppKapitasiPaket.setText("Hitung Kapitasi/Paket");
        ppKapitasiPaket.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppKapitasiPaket.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppKapitasiPaket.setName("ppKapitasiPaket"); // NOI18N
        ppKapitasiPaket.setPreferredSize(new java.awt.Dimension(170, 26));
        ppKapitasiPaket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppKapitasiPaketActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppKapitasiPaket);

        WindowHitungPaket.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowHitungPaket.setName("WindowHitungPaket"); // NOI18N
        WindowHitungPaket.setUndecorated(true);
        WindowHitungPaket.setResizable(false);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Hitung Kapitasi/Paket Pembayaran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout());

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("%");
        jLabel42.setName("jLabel42"); // NOI18N
        internalFrame8.add(jLabel42, java.awt.BorderLayout.CENTER);

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi5.setLayout(null);

        BtnCloseHitungKapitasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseHitungKapitasi.setMnemonic('U');
        BtnCloseHitungKapitasi.setText("Tutup");
        BtnCloseHitungKapitasi.setToolTipText("Alt+U");
        BtnCloseHitungKapitasi.setName("BtnCloseHitungKapitasi"); // NOI18N
        BtnCloseHitungKapitasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseHitungKapitasiActionPerformed(evt);
            }
        });
        panelisi5.add(BtnCloseHitungKapitasi);
        BtnCloseHitungKapitasi.setBounds(200, 165, 100, 30);

        BtnHitungKapitasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/gaji.png"))); // NOI18N
        BtnHitungKapitasi.setMnemonic('S');
        BtnHitungKapitasi.setText("Hitung");
        BtnHitungKapitasi.setToolTipText("Alt+S");
        BtnHitungKapitasi.setName("BtnHitungKapitasi"); // NOI18N
        BtnHitungKapitasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHitungKapitasiActionPerformed(evt);
            }
        });
        panelisi5.add(BtnHitungKapitasi);
        BtnHitungKapitasi.setBounds(10, 165, 100, 30);

        jLabel43.setText("Lebih Bayar Piutang :");
        jLabel43.setName("jLabel43"); // NOI18N
        panelisi5.add(jLabel43);
        jLabel43.setBounds(0, 100, 140, 23);

        LebihBayarPiutang.setText("0");
        LebihBayarPiutang.setHighlighter(null);
        LebihBayarPiutang.setName("LebihBayarPiutang"); // NOI18N
        panelisi5.add(LebihBayarPiutang);
        LebihBayarPiutang.setBounds(144, 100, 150, 23);

        PersentaseBayarPaket.setEditable(false);
        PersentaseBayarPaket.setText("0");
        PersentaseBayarPaket.setHighlighter(null);
        PersentaseBayarPaket.setName("PersentaseBayarPaket"); // NOI18N
        panelisi5.add(PersentaseBayarPaket);
        PersentaseBayarPaket.setBounds(144, 70, 136, 23);

        jLabel41.setText("Persentase Pembayaran :");
        jLabel41.setName("jLabel41"); // NOI18N
        panelisi5.add(jLabel41);
        jLabel41.setBounds(0, 70, 140, 23);

        NilaiKapitasiPaket.setText("0");
        NilaiKapitasiPaket.setHighlighter(null);
        NilaiKapitasiPaket.setName("NilaiKapitasiPaket"); // NOI18N
        NilaiKapitasiPaket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiKapitasiPaketKeyPressed(evt);
            }
        });
        panelisi5.add(NilaiKapitasiPaket);
        NilaiKapitasiPaket.setBounds(144, 40, 150, 23);

        jLabel40.setText("Nilai Kapitasi/Paket :");
        jLabel40.setName("jLabel40"); // NOI18N
        panelisi5.add(jLabel40);
        jLabel40.setBounds(0, 40, 140, 23);

        jLabel39.setText("Piutang Belum Dibayar :");
        jLabel39.setName("jLabel39"); // NOI18N
        panelisi5.add(jLabel39);
        jLabel39.setBounds(0, 10, 140, 23);

        PiutangBelumDibayar.setEditable(false);
        PiutangBelumDibayar.setText("0");
        PiutangBelumDibayar.setHighlighter(null);
        PiutangBelumDibayar.setName("PiutangBelumDibayar"); // NOI18N
        panelisi5.add(PiutangBelumDibayar);
        PiutangBelumDibayar.setBounds(144, 10, 150, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("%");
        jLabel44.setName("jLabel44"); // NOI18N
        panelisi5.add(jLabel44);
        jLabel44.setBounds(281, 70, 50, 23);

        jLabel45.setText("Kekurangan Jadikan :");
        jLabel45.setName("jLabel45"); // NOI18N
        panelisi5.add(jLabel45);
        jLabel45.setBounds(0, 130, 140, 23);

        PilihanKekurangan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diskon", "Tidak Terbayar", "Kekurangan Bayar" }));
        PilihanKekurangan.setName("PilihanKekurangan"); // NOI18N
        PilihanKekurangan.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi5.add(PilihanKekurangan);
        PilihanKekurangan.setBounds(144, 130, 150, 23);

        internalFrame8.add(panelisi5, java.awt.BorderLayout.CENTER);

        WindowHitungPaket.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Piutang Belum Lunas ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setComponentPopupMenu(jPopupMenu1);
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbBangsalPropertyChange(evt);
            }
        });
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBangsalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label32.setText("Tanggal Bayar :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi4.add(label32);

        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setPreferredSize(new java.awt.Dimension(140, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);

        jLabel11.setText("Akun Bayar :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(120, 23));
        panelisi4.add(jLabel11);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.setPreferredSize(new java.awt.Dimension(337, 23));
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        panelisi4.add(AkunBayar);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnAll1.setMnemonic('M');
        BtnAll1.setToolTipText("Alt+M");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        panelisi4.add(BtnAll1);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 101));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label19.setText("Asal Piutang :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label19);

        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(60, 23));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        panelisi3.add(kdpenjab);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(190, 23));
        panelisi3.add(nmpenjab);

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
        TCari.setPreferredSize(new java.awt.Dimension(220, 23));
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
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Belum Dibayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(87, 23));
        panelisi1.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi1.add(LCount);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(50, 50, 50));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Dipilih :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(jLabel12);

        LCount1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount1.setForeground(new java.awt.Color(50, 50, 50));
        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(130, 23));
        panelisi1.add(LCount1);

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

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                    Sequel.menyimpan("temporary","'"+i+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,5).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()))+"','"+
                                tabMode.getValueAt(i,9).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Piutang Pasien"); 
            }
            i++;
            Sequel.menyimpan("temporary","'"+i+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
            i++;
            Sequel.menyimpan("temporary","'"+i+"','TOTAL PIUTANG :','','','','','','','"+LCount.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
            
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptRPiutangMasuk.jasper","report","::[ Rekap Piutang Masuk ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
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
        kdpenjab.setText("");
        nmpenjab.setText("");
        carabayar="";
        tampil();

}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                getdata();
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
        if(nmpenjab.getText().equals("")){
            tampil();
        }else{
            tampilperakun();
        }
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            if(nmpenjab.getText().equals("")){
                tampil();
            }else{
                tampilperakun();
            }
        }else{
            Valid.pindah(evt, TKd, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void MnDetailPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDetailPiutangActionPerformed
    if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    }else{
         if(tbBangsal.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            status=Sequel.cariIsi("select reg_periksa.status_lanjut from reg_periksa where reg_periksa.no_rawat=?",tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());   
            if(status.equals("Ralan")){
                DlgBilingRalan billing=new DlgBilingRalan(null,false);
                billing.TNoRw.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                billing.isCek();
                billing.isRawat();
                if(Sequel.cariInteger("select count(piutang_pasien.no_rawat) from piutang_pasien where piutang_pasien.no_rawat=?",billing.TNoRw.getText())>0){
                    billing.setPiutang();
                }
                billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                billing.setLocationRelativeTo(internalFrame1);
                billing.setVisible(true); 
            }else if(status.equals("Ranap")){
                DlgBilingRanap billing=new DlgBilingRanap(null,false);
                billing.TNoRw.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());            
                billing.isCek();
                billing.isRawat();
                billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                billing.setLocationRelativeTo(internalFrame1);
                billing.setVisible(true); 
            }
            this.setCursor(Cursor.getDefaultCursor());
         }else{
             JOptionPane.showMessageDialog(null,"Silahkan pilih data terlebih dahulu...!!");
         }                   
    } 
}//GEN-LAST:event_MnDetailPiutangActionPerformed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        DlgAkunPiutang penjab=new DlgAkunPiutang(null,false);
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    carabayar=penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),4).toString();
                    tampilperakun();
                }      
                kdpenjab.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.emptTeks();}
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
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBayarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
        }else if(total<=0){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih piutang yang mau dibayar..!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.AutoComitFalse();
            sukses=true;
            
            koderekening="";
            try {
                myObj = new FileReader("./cache/akunbayar.iyem");
                root = mapper.readTree(myObj);
                response = root.path("akunbayar");
                if(response.isArray()){
                   for(JsonNode list:response){
                       if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                            koderekening=list.path("KodeRek").asText();  
                       }
                   }
                }
                myObj.close();
            } catch (Exception e) {
                sukses=false;
            } 
            
            row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                if(tabMode.getValueAt(i,0).toString().equals("true")){
                    if(Double.parseDouble(tabMode.getValueAt(i,8).toString())<0){
                        JOptionPane.showMessageDialog(null,"Nilai pelunasan lebih besar dari sisa piutang...!!");
                        tbBangsal.setValueAt(false,i,0);
                        sukses=false;
                    }else{
                        if(Sequel.menyimpantf("bayar_piutang","?,?,?,?,?,?,?,?,?,?,?","Data",11,new String[]{
                            Valid.SetTgl(Tanggal.getSelectedItem()+""),Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?",tabMode.getValueAt(i,1).toString()),
                            tabMode.getValueAt(i,11).toString(),"diverifikasi oleh "+akses.getkode(),tabMode.getValueAt(i,1).toString(),koderekening,kdpenjab.getText(),tabMode.getValueAt(i,12).toString(),
                            Diskon_Piutang,tabMode.getValueAt(i,13).toString(),Piutang_Tidak_Terbayar
                        })==true){
                            sisapiutang=(Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang_pasien.sisapiutang),0) FROM piutang_pasien where piutang_pasien.no_rawat=?",tabMode.getValueAt(i,1).toString())
                                -Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",tabMode.getValueAt(i,1).toString())
                                -Double.parseDouble(tabMode.getValueAt(i,11).toString())-Double.parseDouble(tabMode.getValueAt(i,12).toString())-Double.parseDouble(tabMode.getValueAt(i,13).toString()));
                            if(sisapiutang<=1){
                                Sequel.mengedit("piutang_pasien","no_rawat='"+tabMode.getValueAt(i,1).toString()+"'","status='Lunas'");
                            }    
                            Sequel.mengedit("detail_piutang_pasien","no_rawat='"+tabMode.getValueAt(i,1).toString()+"' and nama_bayar='"+nmpenjab.getText()+"'","sisapiutang=sisapiutang-"+(Double.parseDouble(tabMode.getValueAt(i,11).toString())+Double.parseDouble(tabMode.getValueAt(i,12).toString())+Double.parseDouble(tabMode.getValueAt(i,13).toString())));
                            Sequel.queryu("delete from tampjurnal");                    
                            if(Sequel.menyimpantf2("tampjurnal","'"+kdpenjab.getText()+"','BAYAR PIUTANG','0','"+(Double.parseDouble(tabMode.getValueAt(i,11).toString())+Double.parseDouble(tabMode.getValueAt(i,12).toString())+Double.parseDouble(tabMode.getValueAt(i,13).toString()))+"'","Rekening")==false){
                                sukses=false;
                            }    
                            if(Double.parseDouble(tabMode.getValueAt(i,11).toString())>0){
                                if(Sequel.menyimpantf2("tampjurnal","'"+koderekening+"','"+AkunBayar.getSelectedItem()+"','"+tabMode.getValueAt(i,11).toString()+"','0'","Rekening")==false){
                                    sukses=false;
                                } 
                            }
                            if(Double.parseDouble(tabMode.getValueAt(i,12).toString())>0){
                                if(Sequel.menyimpantf2("tampjurnal","'"+Diskon_Piutang+"','DISKON BAYAR','"+tabMode.getValueAt(i,12).toString()+"','0'","Rekening")==false){
                                    sukses=false;
                                } 
                            }
                            if(Double.parseDouble(tabMode.getValueAt(i,13).toString())>0){
                                if(Sequel.menyimpantf2("tampjurnal","'"+Piutang_Tidak_Terbayar+"','PIUTANG TIDAK TERBAYAR','"+tabMode.getValueAt(i,13).toString()+"','0'","Rekening")==false){
                                    sukses=false;
                                }  
                            }   
                            if(sukses==true){
                                sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","BAYAR PIUTANG"+", OLEH "+akses.getkode());  
                            }                 
                        }else{
                            sukses=false;
                        }
                    }   
                }
            }
            
            if(sukses==true){
                if(lebihbayar>0){
                    status=Sequel.cariIsi("select kategori_pemasukan_lain.kode_kategori from kategori_pemasukan_lain where kategori_pemasukan_lain.kd_rek='"+Lebih_Bayar_Piutang+"' and kategori_pemasukan_lain.kd_rek2='"+koderekening+"'");
                    if(!status.equals("")){
                        nomorpemasukan=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pemasukan_lain.no_masuk,3),signed)),0) from pemasukan_lain where pemasukan_lain.tanggal like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"%' ","PL"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3);
                        if(Sequel.menyimpantf("pemasukan_lain","?,?,?,?,?,?,?","Pemasukan",7,new String[]{
                            nomorpemasukan,Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),status,Double.toString(lebihbayar),akses.getkode().replaceAll("Admin Utama","-"),carabayar,"Pendapatan Lebih Bayar Piutang Pasien"
                        })==true){
                            if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{Lebih_Bayar_Piutang,"Lebih Bayar Piutang","0",Double.toString(lebihbayar)})==false){
                                sukses=false;
                            } 
                            if(Sequel.menyimpantf2("tampjurnal","?,?,?,?",4,new String[]{koderekening,AkunBayar.getSelectedItem().toString(),Double.toString(lebihbayar),"0"})==false){
                                sukses=false;
                            } 
                            if(sukses==true){
                                sukses=jur.simpanJurnal(nomorpemasukan,"U","PEMASUKAN LAIN-LAIN OLEH "+akses.getkode());
                            }
                            if(sukses==true){
                                lebihbayar=0;
                                carabayar="";
                            }
                        }else{
                            sukses=false;
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Akun Pendapatan Lebih Bayar Piutang dengan pembayaran "+AkunBayar.getSelectedItem().toString()+"\nbelum terdaftar di Kategori Pemasukan Lain-lain..!!!");
                        sukses=false;
                    }
                }
            }
            
            if(sukses==true){
                Sequel.Commit();
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();
            
            if(sukses==true){
                tampilperakun();
                if(!notagihan.equals("")){
                    Sequel.queryu("update penagihan_piutang set status='Sudah Dibayar' where no_tagihan=?",notagihan);
                }
                notagihan="";
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnBayarActionPerformed

    private void BtnBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBayarKeyPressed

    private void AkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkunBayarKeyPressed
        Valid.pindah(evt,Tanggal,TCari);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,kdpenjab,AkunBayar);
    }//GEN-LAST:event_TanggalKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            if(tbBangsal.getSelectedColumn()==0){
                getdata();
            }
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbBangsalPropertyChange
        if(this.isVisible()==true){
            total=0;
            try {
                if(tbBangsal.getSelectedRow()!= -1){
                    if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                        if(Double.parseDouble(tabMode.getValueAt(tbBangsal.getSelectedRow(),8).toString())<0){
                            JOptionPane.showMessageDialog(null,"Nilai pelunasan lebih besar dari sisa piutang...!!");
                            tbBangsal.setValueAt(false,tbBangsal.getSelectedRow(),0);
                        }else{
                            tbBangsal.setValueAt(
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())-
                                    (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString())),
                                    tbBangsal.getSelectedRow(),8);
                        }
                    }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                        tbBangsal.setValueAt(
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())-
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString())),
                                tbBangsal.getSelectedRow(),8);
                        tbBangsal.setValueAt(
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())-
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString())),
                                tbBangsal.getSelectedRow(),11);
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),12);
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),13);
                    }
                }  
            } catch (Exception e) {
            }
            row=tbBangsal.getRowCount();
            for(i=0;i<row;i++){  
                if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                     total=total+Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-
                            (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(i,7).toString()));     
                }
            }
            LCount1.setText(Valid.SetAngka(total));
        }
    }//GEN-LAST:event_tbBangsalPropertyChange

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/akunbayar.iyem")<8){
                tampilAkunBayar2();
            }else{
                tampilAkunBayar();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        tampilAkunBayar();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(kdpenjab.getText().equals("")||nmpenjab.getText().equals("")){
            if(tbBangsal.getSelectedRow()!= -1){
                tbBangsal.setValueAt(false,tbBangsal.getSelectedRow(),0);
            }
            JOptionPane.showMessageDialog(null,"Silahkan pilih cara bayar terlebih dahulu");
        }else{
            total=0;
            for(i=0;i<tbBangsal.getRowCount();i++){
                tbBangsal.setValueAt(true,i,0);
                getdata(i);
            }
            row=tbBangsal.getRowCount();
            for(i=0;i<row;i++){  
                if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                     total=total+Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-
                            (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(i,7).toString()));     
                }
            }
            LCount1.setText(Valid.SetAngka(total));
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(kdpenjab.getText().equals("")||nmpenjab.getText().equals("")){
            if(tbBangsal.getSelectedRow()!= -1){
                tbBangsal.setValueAt(false,tbBangsal.getSelectedRow(),0);
            }
            JOptionPane.showMessageDialog(null,"Silahkan pilih cara bayar terlebih dahulu");
        }else{
            total=0;
            for(i=0;i<tbBangsal.getRowCount();i++){
                tbBangsal.setValueAt(false,i,0);
                getdata(i);
            }
            row=tbBangsal.getRowCount();
            for(i=0;i<row;i++){  
                if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                     total=total+Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-
                            (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(i,7).toString()));     
                }
            }
            LCount1.setText(Valid.SetAngka(total));
        }    
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppKapitasiPaketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppKapitasiPaketActionPerformed
        if(kdpenjab.getText().equals("")||nmpenjab.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih cara bayar terlebih dahulu");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else {
            LCount1.setText(Valid.SetAngka(total));  
            PiutangBelumDibayar.setText(Valid.SetAngka(total));
            NilaiKapitasiPaket.setText("");
            PersentaseBayarPaket.setText("0");
            LebihBayarPiutang.setText("0");
            lebihbayar=0;
            WindowHitungPaket.setLocationRelativeTo(internalFrame1);
            WindowHitungPaket.setVisible(true);
            NilaiKapitasiPaket.requestFocus();
        } 
    }//GEN-LAST:event_ppKapitasiPaketActionPerformed

    private void BtnCloseHitungKapitasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseHitungKapitasiActionPerformed
        WindowHitungPaket.dispose();
    }//GEN-LAST:event_BtnCloseHitungKapitasiActionPerformed

    private void BtnHitungKapitasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHitungKapitasiActionPerformed
        try {
            if((!PiutangBelumDibayar.getText().equals(""))&&(!NilaiKapitasiPaket.getText().equals(""))){ 
                PersentaseBayarPaket.setText(Double.toString((Double.parseDouble(NilaiKapitasiPaket.getText())/total)*100));
            }
        } catch (Exception e) {
            PersentaseBayarPaket.setText("0");
            LebihBayarPiutang.setText("0");
            System.out.println("Notif : "+e);
        }
        if(NilaiKapitasiPaket.getText().equals("")||NilaiKapitasiPaket.getText().equals("0")){
            JOptionPane.showMessageDialog(null,"Silahkan masukkan Nilai Kapitasi/Paket..!!");
            NilaiKapitasiPaket.requestFocus();
        }else if(Double.parseDouble(PersentaseBayarPaket.getText())==0){
            JOptionPane.showMessageDialog(null,"Persentase pembayaran masih 0..!!");
            NilaiKapitasiPaket.requestFocus();
        }else if(Double.parseDouble(PersentaseBayarPaket.getText())==100){
            JOptionPane.showMessageDialog(null,"Persentase pembayaran 100% tidak perlu menggunakan kapitasi/paket..!!");
            NilaiKapitasiPaket.requestFocus();
        }else if(Double.parseDouble(PersentaseBayarPaket.getText())>100){
            LebihBayarPiutang.setText(Valid.SetAngka3(Double.parseDouble(NilaiKapitasiPaket.getText())-total));
            lebihbayar=Double.parseDouble(NilaiKapitasiPaket.getText())-total;
            for(i=0;i<tbBangsal.getRowCount();i++){
                try {
                    tbBangsal.setValueAt(
                            Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-
                            (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(i,7).toString())),
                            i,8);
                    tbBangsal.setValueAt(
                            Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-
                            (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(i,7).toString())),
                            i,11);
                    tbBangsal.setValueAt(0,i,12);
                    tbBangsal.setValueAt(0,i,13);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan penghitungan, silahkan ulangi perhitungan..!!");
                    System.out.println("Notif : "+e);
                }
            }
        }else if(Double.parseDouble(PersentaseBayarPaket.getText())<100){
            lebihbayar=0;
            LebihBayarPiutang.setText("0");
            for(i=0;i<tbBangsal.getRowCount();i++){
                try {
                    if(PilihanKekurangan.getSelectedIndex()==0){
                        tbBangsal.setValueAt(
                            Math.round((Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-(Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+Double.parseDouble(tbBangsal.getValueAt(i,7).toString())))-
                            ((Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-(Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+Double.parseDouble(tbBangsal.getValueAt(i,7).toString())))*(Double.parseDouble(PersentaseBayarPaket.getText())/100)))
                        ,i,12);
                        tbBangsal.setValueAt(0,i,13);
                    }else if(PilihanKekurangan.getSelectedIndex()==1){
                        tbBangsal.setValueAt(0,i,12);
                        tbBangsal.setValueAt(
                            Math.round((Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-(Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+Double.parseDouble(tbBangsal.getValueAt(i,7).toString())))-
                            ((Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-(Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+Double.parseDouble(tbBangsal.getValueAt(i,7).toString())))*(Double.parseDouble(PersentaseBayarPaket.getText())/100)))
                        ,i,13);
                    }else if(PilihanKekurangan.getSelectedIndex()==2){
                        tbBangsal.setValueAt(0,i,12);
                        tbBangsal.setValueAt(0,i,13);
                    }   
                    tbBangsal.setValueAt(
                        Math.round((Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-
                        (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                        Double.parseDouble(tbBangsal.getValueAt(i,7).toString())))
                        *(Double.parseDouble(PersentaseBayarPaket.getText())/100))
                    ,i,11);
                    tbBangsal.setValueAt(
                        Math.round(Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-
                        (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                        Double.parseDouble(tbBangsal.getValueAt(i,7).toString())+
                        Double.parseDouble(tbBangsal.getValueAt(i,11).toString())+
                        Double.parseDouble(tbBangsal.getValueAt(i,12).toString())+
                        Double.parseDouble(tbBangsal.getValueAt(i,13).toString()))),
                    i,8);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan penghitungan, silahkan ulangi perhitungan..!!");
                    System.out.println("Notif : "+e);
                }
            }
        } 
    }//GEN-LAST:event_BtnHitungKapitasiActionPerformed

    private void NilaiKapitasiPaketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiKapitasiPaketKeyPressed
        Valid.pindah(evt,BtnCloseHitungKapitasi,BtnHitungKapitasi);
    }//GEN-LAST:event_NilaiKapitasiPaketKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganPiutangBelumLunas dialog = new KeuanganPiutangBelumLunas(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AkunBayar;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnBayar;
    private widget.Button BtnCari;
    private widget.Button BtnCloseHitungKapitasi;
    private widget.Button BtnHitungKapitasi;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private javax.swing.JLabel LCount;
    private javax.swing.JLabel LCount1;
    private widget.TextBox LebihBayarPiutang;
    private javax.swing.JMenuItem MnDetailPiutang;
    private widget.TextBox NilaiKapitasiPaket;
    private widget.TextBox PersentaseBayarPaket;
    private widget.ComboBox PilihanKekurangan;
    private widget.TextBox PiutangBelumDibayar;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal Tanggal;
    private javax.swing.JDialog WindowHitungPaket;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame8;
    private javax.swing.JLabel jLabel10;
    private widget.Label jLabel11;
    private javax.swing.JLabel jLabel12;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpenjab;
    private widget.Label label17;
    private widget.Label label19;
    private widget.Label label32;
    private widget.TextBox nmpenjab;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppKapitasiPaket;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    private void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            sisapiutang=0;
            ps=koneksi.prepareStatement("select piutang_pasien.no_rawat, piutang_pasien.tgl_piutang, concat(piutang_pasien.no_rkm_medis,' ',pasien.nm_pasien), "+
                       "piutang_pasien.status,piutang_pasien.totalpiutang, piutang_pasien.uangmuka, piutang_pasien.sisapiutang, piutang_pasien.tgltempo,penjab.png_jawab "+
                       "from piutang_pasien inner join pasien inner join reg_periksa inner join penjab on  "+
                       "piutang_pasien.no_rkm_medis=pasien.no_rkm_medis and "+
                       "piutang_pasien.no_rawat=reg_periksa.no_rawat and "+
                       "reg_periksa.kd_pj=penjab.kd_pj where piutang_pasien.status='Belum Lunas' "+
                       (TCari.getText().trim().equals("")?"":" and (piutang_pasien.no_rawat like ? or piutang_pasien.no_rkm_medis like ? or "+
                       "pasien.nm_pasien like ? or piutang_pasien.status like ?)")+" order by piutang_pasien.tgl_piutang");
            try {
                if(!TCari.getText().trim().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    cicilan=Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",rs.getString(1));
                    tabMode.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getDouble(6),
                        cicilan,(rs.getDouble(7)-cicilan),rs.getString(8),rs.getString(9),(rs.getDouble(7)-cicilan),0,0
                    });
                    sisapiutang=sisapiutang+rs.getDouble(7)-cicilan;
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            LCount.setText(Valid.SetAngka(sisapiutang));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void tampilperakun() {
        Valid.tabelKosong(tabMode);
        try{
            sisapiutang=0;
            ps=koneksi.prepareStatement("select piutang_pasien.no_rawat, piutang_pasien.tgl_piutang, concat(piutang_pasien.no_rkm_medis,' ',pasien.nm_pasien), "+
                       "piutang_pasien.status,detail_piutang_pasien.totalpiutang,0, detail_piutang_pasien.sisapiutang, piutang_pasien.tgltempo,detail_piutang_pasien.nama_bayar "+
                       "from piutang_pasien inner join pasien inner join reg_periksa inner join penjab inner join detail_piutang_pasien on  "+
                       "piutang_pasien.no_rkm_medis=pasien.no_rkm_medis and piutang_pasien.no_rawat=reg_periksa.no_rawat and "+
                       "reg_periksa.kd_pj=penjab.kd_pj and piutang_pasien.no_rawat=detail_piutang_pasien.no_rawat where "+
                       "detail_piutang_pasien.sisapiutang>=1 and detail_piutang_pasien.nama_bayar like ? and "+
                       "(piutang_pasien.no_rawat like ? or piutang_pasien.no_rkm_medis like ? or "+
                       "pasien.nm_pasien like ? or piutang_pasien.status like ?) order by piutang_pasien.tgl_piutang");
            try {
                ps.setString(1,"%"+nmpenjab.getText()+"%");
                ps.setString(2,"%"+TCari.getText()+"%");
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,"%"+TCari.getText()+"%");
                ps.setString(5,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    cicilan=Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rawat='"+rs.getString(1)+"' and bayar_piutang.kd_rek_kontra='"+kdpenjab.getText()+"'");
                    tabMode.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getDouble(6),
                        cicilan,rs.getDouble(7),rs.getString(8),rs.getString(9),rs.getDouble(7),0,0
                    });
                    sisapiutang=sisapiutang+rs.getDouble(7)-cicilan;
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            LCount.setText(Valid.SetAngka(sisapiutang));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampiltagihan(String notagihan) {
        this.notagihan=notagihan;
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select akun_piutang.kd_rek,akun_piutang.nama_bayar,akun_bayar.nama_bayar,"+
                    "penagihan_piutang.kd_pj from penagihan_piutang inner join akun_piutang on penagihan_piutang.kd_pj=akun_piutang.kd_pj "+
                    "inner join akun_bayar on penagihan_piutang.kd_rek=akun_bayar.kd_rek where penagihan_piutang.no_tagihan=?");
            try {
                ps.setString(1,notagihan);
                rs=ps.executeQuery();
                if(rs.next()){
                    kdpenjab.setText(rs.getString(1));
                    nmpenjab.setText(rs.getString(2));
                    AkunBayar.setSelectedItem(rs.getString(3));
                    carabayar=rs.getString("kd_pj");
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            sisapiutang=0;
            ps=koneksi.prepareStatement("select piutang_pasien.no_rawat, piutang_pasien.tgl_piutang, concat(piutang_pasien.no_rkm_medis,' ',pasien.nm_pasien), "+
                       "piutang_pasien.status,detail_piutang_pasien.totalpiutang,0, detail_piutang_pasien.sisapiutang, piutang_pasien.tgltempo,detail_piutang_pasien.nama_bayar "+
                       "from piutang_pasien inner join pasien inner join reg_periksa inner join penjab inner join detail_piutang_pasien on  "+
                       "piutang_pasien.no_rkm_medis=pasien.no_rkm_medis and piutang_pasien.no_rawat=reg_periksa.no_rawat and "+
                       "reg_periksa.kd_pj=penjab.kd_pj and piutang_pasien.no_rawat=detail_piutang_pasien.no_rawat where "+
                       "detail_piutang_pasien.sisapiutang>=1 and detail_piutang_pasien.kd_pj='"+carabayar+"' and piutang_pasien.no_rawat in "+
                       "(select detail_penagihan_piutang.no_rawat from detail_penagihan_piutang where detail_penagihan_piutang.no_tagihan=?) order by piutang_pasien.tgl_piutang");
            try {
                ps.setString(1,notagihan);
                rs=ps.executeQuery();
                while(rs.next()){
                    cicilan=Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rawat='"+rs.getString(1)+"' and bayar_piutang.kd_rek_kontra='"+kdpenjab.getText()+"'");
                    tabMode.addRow(new Object[]{
                        true,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getDouble(6),
                        cicilan,rs.getDouble(7),rs.getString(8),rs.getString(9),rs.getDouble(7),0,0
                    });
                    sisapiutang=sisapiutang+rs.getDouble(7)-cicilan;
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            LCount.setText(Valid.SetAngka(sisapiutang));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getdata() {
        if(kdpenjab.getText().equals("")||nmpenjab.getText().equals("")){
            if(tbBangsal.getSelectedRow()!= -1){
                tbBangsal.setValueAt(false,tbBangsal.getSelectedRow(),0);
            }
            JOptionPane.showMessageDialog(null,"Silahkan pilih cara bayar terlebih dahulu");
        }else{
            total=0;
            try {
                if(tbBangsal.getSelectedRow()!= -1){
                    if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                        if(Double.parseDouble(tabMode.getValueAt(tbBangsal.getSelectedRow(),8).toString())<0){
                            JOptionPane.showMessageDialog(null,"Nilai pelunasan lebih besar dari sisa piutang...!!");
                            tbBangsal.setValueAt(false,tbBangsal.getSelectedRow(),0);
                        }else{
                            tbBangsal.setValueAt(
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())-
                                    (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString())),
                                    tbBangsal.getSelectedRow(),8);
                        }
                    }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                        tbBangsal.setValueAt(
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())-
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString())),
                                tbBangsal.getSelectedRow(),8);
                        tbBangsal.setValueAt(
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())-
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString())),
                                tbBangsal.getSelectedRow(),11);
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),12);
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),13);
                    }
                }  
            } catch (Exception e) {
            }
            row=tbBangsal.getRowCount();
            for(i=0;i<row;i++){  
                if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                     total=total+Double.parseDouble(tbBangsal.getValueAt(i,5).toString())-
                            (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(i,7).toString()));     
                }
            }
            LCount1.setText(Valid.SetAngka(total));
        }   
    }
    
    private void getdata(int pilih) {
        try {
            if(pilih!= -1){
                if(tbBangsal.getValueAt(pilih,0).toString().equals("true")){
                    if(Double.parseDouble(tabMode.getValueAt(pilih,8).toString())<0){
                            JOptionPane.showMessageDialog(null,"Nilai pelunasan lebih besar dari sisa piutang...!!");
                            tbBangsal.setValueAt(false,pilih,0);
                    }else{
                        tbBangsal.setValueAt(
                                Double.parseDouble(tbBangsal.getValueAt(pilih,5).toString())-
                                (Double.parseDouble(tbBangsal.getValueAt(pilih,6).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(pilih,7).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(pilih,11).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(pilih,12).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(pilih,13).toString())),
                                pilih,8);
                    }
                        
                }else if(tbBangsal.getValueAt(pilih,0).toString().equals("false")){
                    tbBangsal.setValueAt(
                            Double.parseDouble(tbBangsal.getValueAt(pilih,5).toString())-
                            (Double.parseDouble(tbBangsal.getValueAt(pilih,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(pilih,7).toString())),
                            pilih,8);
                    tbBangsal.setValueAt(
                            Double.parseDouble(tbBangsal.getValueAt(pilih,5).toString())-
                            (Double.parseDouble(tbBangsal.getValueAt(pilih,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(pilih,7).toString())),
                            pilih,11);
                    tbBangsal.setValueAt(0,pilih,12);
                    tbBangsal.setValueAt(0,pilih,13);
                }
            }  
        } catch (Exception e) {
        }
    }
    
    public void isCek(){
        TCari.requestFocus();
        BtnBayar.setEnabled(akses.getbayar_piutang());
    }
    
    private void tampilAkunBayar() {         
         try{      
             file=new File("./cache/akunbayar.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             StringBuilder iyembuilder = new StringBuilder();
             ps=koneksi.prepareStatement("select * from akun_bayar order by akun_bayar.nama_bayar");
             try{
                 rs=ps.executeQuery();
                 AkunBayar.removeAllItems();
                 while(rs.next()){    
                     AkunBayar.addItem(rs.getString(1).replaceAll("\"",""));
                     iyembuilder.append("{\"NamaAkun\":\"").append(rs.getString(1).replaceAll("\"","")).append("\",\"KodeRek\":\"").append(rs.getString(2)).append("\",\"PPN\":\"").append(rs.getDouble(3)).append("\"},");
                 }
             }catch (Exception e) {
                 System.out.println("Notifikasi : "+e);
             } finally{
                 if(rs != null){
                     rs.close();
                 } 
                 if(ps != null){
                     ps.close();
                 } 
             }
             
             if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"akunbayar\":["+iyembuilder+"]}");
                fileWriter.flush();
             }
            
             fileWriter.close();
             iyembuilder=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilAkunBayar2() {
        try {
            myObj = new FileReader("./cache/akunbayar.iyem");
            root = mapper.readTree(myObj);
            response = root.path("akunbayar");
            if(response.isArray()){
                for(JsonNode list:response){
                    AkunBayar.addItem(list.path("NamaAkun").asText().replaceAll("\"",""));
                }
            }
            myObj.close();
        } catch (Exception ex) {
            if(ex.toString().contains("java.io.FileNotFoundException")){
                tampilAkunBayar();
            }else{
                System.out.println("Notifikasi : "+ex);
            }
        }
    } 
}

package inventory;


import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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

public class DlgUbahPemesanan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,pskonversi,pscaripesan,psdetailpesan,ps2;
    private ResultSet rs,rskonversi,rs2;
    private riwayatobat Trackobat=new riwayatobat();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariSatuan satuanbarang=new DlgCariSatuan(null,false);
    private double saldoawal=0,mutasi=0,ttl=0,y=0,w=0,ttldisk=0,sbttl=0,ppn=0,tagihan=0,jmlkonversi=0,hargappn=0;
    private int jml=0,i=0,row=0,index=0;
    private String[] kodebarang,namabarang,satuan,satuanbeli,kadaluwarsa,no_batch;
    private double[] harga,jumlah,subtotal,diskon,besardiskon,jmltotal,jmlstok;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgUbahPemesanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
            "Jml","Satuan Beli","Kode Barang","Nama Barang","Satuan","G","Kadaluwarsa",
            "Harga(Rp)","Subtotal(Rp)","Disk(%)","Diskon(Rp)","Ttl.Beli","Jml.Stok","No.Batch"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
               boolean a = false;
               if ((colIndex==0)||(colIndex==5)||(colIndex==6)||(colIndex==7)||(colIndex==9)||(colIndex==10)||(colIndex==13)) {
                   a=true;
               }
               return a;
             }
              
             Class[] types = new Class[] {
                java.lang.Double.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.Boolean.class,java.lang.String.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.String.class  
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);        
        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(240);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(20);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(120);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setPreferredWidth(80);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoFaktur.setDocument(new batasInput((byte)20).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
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
        
        satuanbarang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(satuanbarang.getTable().getSelectedRow()!= -1){    
                    tbDokter.setValueAt(satuanbarang.getTable().getValueAt(satuanbarang.getTable().getSelectedRow(),0).toString(),tbDokter.getSelectedRow(),1);
                }   
                tbDokter.requestFocus();
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
        
        try{            
            ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,databarang.kode_sat, databarang.h_beli, "+
                " ifnull(date_format(databarang.expire,'%d-%m-%Y'),'00-00-0000') as expire from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                " where databarang.status='1' and databarang.kode_brng like ? or "+ 
                " databarang.status='1' and databarang.nama_brng like ? or "+
                " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            ps2=koneksi.prepareStatement("select detailpesan.kode_brng,databarang.nama_brng,ifnull(date_format(databarang.expire,'%d-%m-%Y'),'00-00-0000') as expire, "+
                        "detailpesan.kode_sat,kodesatuan.satuan,detailpesan.jumlah,detailpesan.h_pesan, "+
                        "detailpesan.subtotal,detailpesan.dis,detailpesan.besardis,detailpesan.total,detailpesan.no_batch, "+
                        "databarang.kode_sat as satbar from detailpesan inner join databarang inner join kodesatuan inner join jenis "+
                        " on detailpesan.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "+
                        " and detailpesan.kode_sat=kodesatuan.kode_sat where detailpesan.no_faktur=? ");
            pskonversi=koneksi.prepareStatement("select nilai,nilai_konversi from konver_sat where kode_sat=? and sat_konversi=?");
            pscaripesan=koneksi.prepareStatement("select no_faktur, tagihan, kd_bangsal,tgl_faktur,status from pemesanan where no_faktur=?");
            psdetailpesan=koneksi.prepareStatement("select kode_brng,jumlah2 from detailpesan where no_faktur=? ");
        }catch(Exception e){
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

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        label9 = new widget.Label();
        LSubtotal = new widget.Label();
        BtnKeluar = new widget.Button();
        label12 = new widget.Label();
        LPotongan = new widget.Label();
        LTotal2 = new widget.Label();
        label14 = new widget.Label();
        label17 = new widget.Label();
        LPpn = new widget.Label();
        label19 = new widget.Label();
        LTagiha = new widget.Label();
        tppn = new widget.TextBox();
        label21 = new widget.Label();
        BtnTambah = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label11 = new widget.Label();
        TglPesan = new widget.Tanggal();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        label20 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        TglTempo = new widget.Tanggal();
        label18 = new widget.Label();
        label22 = new widget.Label();
        TglFaktur = new widget.Tanggal();
        NoOrder = new widget.TextBox();
        label23 = new widget.Label();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(102, 51, 0));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Pemesanan Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
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

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 107));
        panelisi1.setLayout(null);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        BtnSimpan.setBounds(10, 62, 100, 30);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label10);
        label10.setBounds(110, 65, 75, 23);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);
        TCari.setBounds(190, 65, 280, 23);

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
        BtnCari1.setBounds(472, 65, 28, 23);

        label9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label9.setText("Potongan :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label9);
        label9.setBounds(130, 0, 90, 30);

        LSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LSubtotal.setText("0");
        LSubtotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LSubtotal.setName("LSubtotal"); // NOI18N
        LSubtotal.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LSubtotal);
        LSubtotal.setBounds(10, 20, 120, 30);

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
        BtnKeluar.setBounds(633, 62, 100, 30);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label12.setText("Total 1 :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label12);
        label12.setBounds(10, 0, 60, 30);

        LPotongan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPotongan.setText("0");
        LPotongan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPotongan.setName("LPotongan"); // NOI18N
        LPotongan.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LPotongan);
        LPotongan.setBounds(130, 20, 120, 30);

        LTotal2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal2.setText("0");
        LTotal2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal2.setName("LTotal2"); // NOI18N
        LTotal2.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LTotal2);
        LTotal2.setBounds(250, 20, 120, 30);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("Total 2 :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label14);
        label14.setBounds(250, 0, 90, 30);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("PPN :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label17);
        label17.setBounds(370, 0, 90, 30);

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LPpn);
        LPpn.setBounds(470, 20, 120, 30);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("Jumlah Tagihan :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label19);
        label19.setBounds(590, 0, 130, 30);

        LTagiha.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTagiha.setText("0");
        LTagiha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTagiha.setName("LTagiha"); // NOI18N
        LTagiha.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LTagiha);
        LTagiha.setBounds(590, 20, 120, 30);

        tppn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tppn.setName("tppn"); // NOI18N
        tppn.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppnKeyPressed(evt);
            }
        });
        panelisi1.add(tppn);
        tppn.setBounds(370, 26, 60, 23);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("%");
        label21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label21);
        label21.setBounds(433, 26, 40, 23);

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
        BtnTambah.setBounds(501, 65, 28, 23);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label15.setText("No.Faktur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoFaktur.setEditable(false);
        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(78, 10, 230, 23);

        label11.setText("Tgl.Datang :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 75, 23);

        TglPesan.setEditable(false);
        TglPesan.setDisplayFormat("dd-MM-yyyy");
        TglPesan.setName("TglPesan"); // NOI18N
        TglPesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPesanKeyPressed(evt);
            }
        });
        panelisi3.add(TglPesan);
        TglPesan.setBounds(78, 40, 95, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(337, 40, 70, 23);

        kdsup.setEditable(false);
        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(409, 10, 80, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(327, 10, 80, 23);

        kdptg.setEditable(false);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(409, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(491, 10, 240, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(491, 40, 240, 23);

        label20.setText("Lokasi :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label20);
        label20.setBounds(337, 70, 70, 23);

        kdgudang.setEditable(false);
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(409, 70, 80, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(491, 70, 240, 23);

        TglTempo.setEditable(false);
        TglTempo.setDisplayFormat("dd-MM-yyyy");
        TglTempo.setName("TglTempo"); // NOI18N
        TglTempo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglTempoKeyPressed(evt);
            }
        });
        panelisi3.add(TglTempo);
        TglTempo.setBounds(243, 70, 95, 23);

        label18.setText("Jth.Tempo :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(180, 70, 60, 23);

        label22.setText("Tgl.Faktur :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label22);
        label22.setBounds(180, 40, 60, 23);

        TglFaktur.setEditable(false);
        TglFaktur.setDisplayFormat("dd-MM-yyyy");
        TglFaktur.setName("TglFaktur"); // NOI18N
        TglFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglFakturKeyPressed(evt);
            }
        });
        panelisi3.add(TglFaktur);
        TglFaktur.setBounds(243, 40, 95, 23);

        NoOrder.setEditable(false);
        NoOrder.setName("NoOrder"); // NOI18N
        NoOrder.setPreferredSize(new java.awt.Dimension(207, 23));
        NoOrder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoOrderKeyPressed(evt);
            }
        });
        panelisi3.add(NoOrder);
        NoOrder.setBounds(78, 70, 95, 23);

        label23.setText("SP/Order :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label23);
        label23.setBounds(0, 70, 75, 23);

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
        }else{Valid.pindah(evt,BtnSimpan,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoFaktur.getText().trim().equals("")){
            Valid.textKosong(NoFaktur,"No.Faktur");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Supplier");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan pemesanan...!!!!");
            tbDokter.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                    Sequel.AutoComitFalse();
                    try {
                        pscaripesan.setString(1,NoFaktur.getText());
                        rs=pscaripesan.executeQuery();
                        if(rs.next()){
                            psdetailpesan.setString(1,rs.getString(1));
                            rs2=psdetailpesan.executeQuery();
                            while(rs2.next()){
                                Trackobat.catatRiwayat(rs2.getString("kode_brng"),0,rs2.getDouble("jumlah2"),"Pemesanan",var.getkode(),rs.getString("kd_bangsal") ,"Hapus");
                                Sequel.menyimpan("gudangbarang","'"+rs2.getString("kode_brng") +"','"+rs.getString("kd_bangsal") +"','-"+rs2.getString("jumlah2") +"'", 
                                                       "stok=stok-'"+rs2.getString("jumlah2") +"'","kode_brng='"+rs2.getString("kode_brng")+"' and kd_bangsal='"+rs.getString("kd_bangsal") +"'");
                            }
                            Sequel.queryu("delete from tampjurnal");
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                Sequel.cariIsi("select Pemesanan_Obat from set_akun"),"PERSEDIAAN BARANG","0",rs.getString("tagihan")
                            });    
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                Sequel.cariIsi("select Kontra_Pemesanan_Obat from set_akun"),"HUTANG USAHA",rs.getString("tagihan"),"0"
                            }); 
                            jur.simpanJurnal(rs.getString("no_faktur"),Sequel.cariIsi("select current_date()"),"U","BATAL TRANSAKSI PEMESANAN BARANG DI "+Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?",rs.getString("kd_bangsal")).toUpperCase());
                        }
                        Sequel.queryu2("delete from pemesanan where no_faktur=?",1,new String[]{NoFaktur.getText()});
                    } catch (Exception e) {
                    }
                              
                    if(Sequel.menyimpantf2("pemesanan","?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Nota",14,new String[]{
                        NoFaktur.getText(),NoOrder.getText(),kdsup.getText(),kdptg.getText(),Valid.SetTgl(TglPesan.getSelectedItem()+""),
                        Valid.SetTgl(TglFaktur.getSelectedItem()+""),Valid.SetTgl(TglTempo.getSelectedItem()+""),""+sbttl,""+ttldisk,""+ttl,
                        ""+ppn,""+(ttl+ppn),kdgudang.getText(),"Belum Dibayar"
                    })==true){
                        jml=tbDokter.getRowCount();
                        for(i=0;i<jml;i++){  
                            try {
                                if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                                    if(Sequel.menyimpantf2("detailpesan","?,?,?,?,?,?,?,?,?,?,?","Transaksi Pemesanan",11,new String[]{
                                           NoFaktur.getText(),
                                           tbDokter.getValueAt(i,2).toString(),
                                           tbDokter.getValueAt(i,1).toString(),
                                           tbDokter.getValueAt(i,0).toString(),
                                           tbDokter.getValueAt(i,7).toString(),
                                           tbDokter.getValueAt(i,8).toString(),
                                           tbDokter.getValueAt(i,9).toString(),
                                           tbDokter.getValueAt(i,10).toString(),
                                           tbDokter.getValueAt(i,11).toString(),
                                           tbDokter.getValueAt(i,13).toString(),
                                           tbDokter.getValueAt(i,12).toString()
                                    })==true){
                                        Trackobat.catatRiwayat(tbDokter.getValueAt(i,2).toString(),Valid.SetAngka(tbDokter.getValueAt(i,12).toString()),0,"Pemesanan",var.getkode(),kdgudang.getText(),"Simpan");
                                        Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,2).toString()+"','"+kdgudang.getText()+"','"+tbDokter.getValueAt(i,12).toString()+"'", 
                                                   "stok=stok+'"+tbDokter.getValueAt(i,12).toString()+"'","kode_brng='"+tbDokter.getValueAt(i,2).toString()+"' and kd_bangsal='"+kdgudang.getText()+"'");
                                    }
                                        
                                }
                            } catch (Exception e) {
                            }                
                        } 

                        Sequel.queryu("delete from tampjurnal");
                        Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Pemesanan_Obat from set_akun"),"PERSEDIAAN BARANG",""+(ttl+ppn),"0"});
                        Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Kontra_Pemesanan_Obat from set_akun"),"HUTANG USAHA","0",""+(ttl+ppn)}); 
                        jur.simpanJurnal(NoFaktur.getText(),Valid.SetTgl(TglPesan.getSelectedItem()+""),"U","PEMESANAN BARANG DI "+nmgudang.getText().toUpperCase());
                        jml=tbDokter.getRowCount();
                        for(i=0;i<jml;i++){ 
                            tbDokter.setValueAt(null,i,0);
                            tbDokter.setValueAt(0,i,8);
                            tbDokter.setValueAt(0,i,9);
                            tbDokter.setValueAt(0,i,10);
                            tbDokter.setValueAt(0,i,11);
                            tbDokter.setValueAt(0,i,12);
                            tbDokter.setValueAt("",i,13);
                        }
                        getData();
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan No.Nota sudah ada sebelumnya...!!");
                    }                        
                    Sequel.AutoComitTrue();
                    JOptionPane.showMessageDialog(rootPane, "Proses simpan selesai...!!");
                    dispose();
            }
        }        
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,TCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kdsup.requestFocus();
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
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbDokter.getRowCount();i++){ 
                tbDokter.setValueAt(null,i,0);
                tbDokter.setValueAt(0,i,8);
                tbDokter.setValueAt(0,i,9);
                tbDokter.setValueAt(0,i,10);
                tbDokter.setValueAt(0,i,11);
                tbDokter.setValueAt(0,i,12);
                tbDokter.setValueAt(0,i,13);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tbDokter.getRowCount()!=0){
            try {
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==7)||(tbDokter.getSelectedColumn()==8)||(tbDokter.getSelectedColumn()==10)){                       
                        getData();  
                   }else if(tbDokter.getSelectedColumn()==9){
                       try {
                           tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString())/100),tbDokter.getSelectedRow(),10);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),10);
                       }
                       getData(); 
                   }
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tbDokter.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                  
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==7)||(tbDokter.getSelectedColumn()==8)||(tbDokter.getSelectedColumn()==10)){                       
                        try{
                            rs=koneksi.prepareStatement("select * from setpenjualan").executeQuery();
                            if(rs.next()){
                                if(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().equals(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString())){
                                    try {
                                        jmlkonversi=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                    } catch (Exception e) {
                                        jmlkonversi=0;
                                    }
                                    tbDokter.setValueAt(jmlkonversi,tbDokter.getSelectedRow(),12);
                                    hargappn=0;
                                    if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("true")){
                                        try {
                                            hargappn=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())+((Double.parseDouble(tppn.getText())/100)*Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString()));
                                        } catch (Exception e) {
                                            hargappn=0;
                                        }
                                        
                                        Sequel.mengedit("databarang","kode_brng=?","expire=?,h_beli=?,ralan=?,kelas1=?,kelas2=?,kelas3=?,utama=?,vip=?,vvip=?,beliluar=?,jualbebas=?,karyawan=?",13,new String[]{
                                            Valid.SetTgl(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()),
                                            hargappn+"",
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("ralan")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("kelas1")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("kelas2")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("kelas3")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("utama")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("vip")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("vvip")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("beliluar")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("jualbebas")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("karyawan")/100))),
                                            tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString()
                                        });  
                                    }                                       
                                }else{                                
                                    try{
                                        pskonversi.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                                        pskonversi.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                                        rskonversi=pskonversi.executeQuery();
                                        if(rskonversi.next()){
                                            try {
                                                jmlkonversi=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())*(rskonversi.getDouble(1)/rskonversi.getDouble(2));
                                            } catch (Exception e) {
                                                jmlkonversi=0;
                                            }
                                            tbDokter.setValueAt(jmlkonversi,tbDokter.getSelectedRow(),12);
                                            hargappn=0;
                                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("true")){
                                                try {
                                                    hargappn=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())+((Double.parseDouble(tppn.getText())/100)*Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString()));
                                                } catch (Exception e) {
                                                    hargappn=0;
                                                }
                                                Sequel.mengedit("databarang","kode_brng=?","expire=?,h_beli=?,ralan=?,kelas1=?,kelas2=?,kelas3=?,utama=?,vip=?,vvip=?,beliluar=?,jualbebas=?,karyawan=?",13,new String[]{
                                                    Valid.SetTgl(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()),
                                                    hargappn+"",
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("ralan")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("kelas1")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("kelas2")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("kelas3")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("utama")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("vip")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("vvip")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("beliluar")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("jualbebas")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("karyawan")/100))),
                                                    tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString()
                                                });
                                            }
                                        }else if(!rs.next()){
                                            tbDokter.setValueAt(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString(),tbDokter.getSelectedRow(),1 );
                                            int reply = JOptionPane.showConfirmDialog(rootPane,"Maaf, konversi satuan tidak ditemukan. Apa anda ingin menambahkan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                                            if (reply == JOptionPane.YES_OPTION) {
                                                DlgKonversi konv=new DlgKonversi(null,false);
                                                konv.emptTeks();
                                                konv.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                                                konv.setLocationRelativeTo(internalFrame1);
                                                konv.setVisible(true);
                                            }                            
                                         }   
                                    }catch(Exception ex){
                                        System.out.println(ex);
                                    }
                                }  
                            }                                                              
                        }catch(Exception e){
                            System.out.println("Notifikasi : "+e);                             
                        } 
                        getData();  
                        TCari.setText("");
                        TCari.requestFocus();
                                                
                   }else if(tbDokter.getSelectedColumn()==8){
                       try {
                           tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString())/100),tbDokter.getSelectedRow(),9);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),9);
                       }
                       
                       getData();
                   }else if((tbDokter.getSelectedColumn()==10)||(tbDokter.getSelectedColumn()==11)||(tbDokter.getSelectedColumn()==13)){                       
                       TCari.setText("");
                       TCari.requestFocus();
                   }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    if(tbDokter.getSelectedColumn()==0){
                        tbDokter.setValueAt(null, tbDokter.getSelectedRow(),0);
                    }else if(tbDokter.getSelectedColumn()==7){
                        tbDokter.setValueAt("", tbDokter.getSelectedRow(),7);
                    }else if(tbDokter.getSelectedColumn()==9){
                        tbDokter.setValueAt("", tbDokter.getSelectedRow(),9);
                    }else if(tbDokter.getSelectedColumn()==10){
                        tbDokter.setValueAt("", tbDokter.getSelectedRow(),10);
                    }
                } catch (Exception e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_BACK_SPACE){
                try {
                    if(tbDokter.getSelectedColumn()==0){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("0,0")){
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),0);
                        }
                    }else if(tbDokter.getSelectedColumn()==7){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString().equals("0,0")){
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),7);
                        }
                    }else if(tbDokter.getSelectedColumn()==9){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString().equals("0,0")){
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),9);
                        }
                    }else if(tbDokter.getSelectedColumn()==10){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString().equals("0,0")){
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),10);
                        }
                    }
                } catch (Exception e) {                    
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==7)||(tbDokter.getSelectedColumn()==8)||(tbDokter.getSelectedColumn()==10)){                                              
                       getData();  
                   }else if(tbDokter.getSelectedColumn()==9){
                       try {
                           tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString())/100),tbDokter.getSelectedRow(),10);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),10);
                       }
                       
                       getData();
                   }else if(tbDokter.getSelectedColumn()==1){
                       try{
                            rs=koneksi.prepareStatement("select * from setpenjualan").executeQuery();
                            if(rs.next()){
                                if(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().equals(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString())){
                                    try {
                                        jmlkonversi=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                    } catch (Exception e) {
                                        jmlkonversi=0;
                                    }
                                    tbDokter.setValueAt(jmlkonversi,tbDokter.getSelectedRow(),12);
                                    hargappn=0;
                                    if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("true")){
                                        try {
                                            hargappn=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())+((Double.parseDouble(tppn.getText())/100)*Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString()));
                                        } catch (Exception e) {
                                            hargappn=0;
                                        }
                                        
                                        Sequel.mengedit("databarang","kode_brng=?","expire=?,h_beli=?,ralan=?,kelas1=?,kelas2=?,kelas3=?,utama=?,vip=?,vvip=?,beliluar=?,jualbebas=?,karyawan=?",13,new String[]{
                                            Valid.SetTgl(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()),
                                            hargappn+"",
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("ralan")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("kelas1")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("kelas2")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("kelas3")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("utama")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("vip")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("vvip")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("beliluar")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("jualbebas")/100))),
                                            Double.toString(hargappn+(hargappn*(rs.getDouble("karyawan")/100))),
                                            tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString()
                                        });  
                                    }                                       
                                }else{                                
                                    try{
                                        pskonversi.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                                        pskonversi.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                                        rskonversi=pskonversi.executeQuery();
                                        if(rskonversi.next()){
                                            try {
                                                jmlkonversi=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())*(rskonversi.getDouble(1)/rskonversi.getDouble(2));
                                            } catch (Exception e) {
                                                jmlkonversi=0;
                                            }
                                            tbDokter.setValueAt(jmlkonversi,tbDokter.getSelectedRow(),12);
                                            hargappn=0;
                                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("true")){
                                                try {
                                                    hargappn=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())+((Double.parseDouble(tppn.getText())/100)*Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString()));
                                                } catch (Exception e) {
                                                    hargappn=0;
                                                }
                                                
                                                Sequel.mengedit("databarang","kode_brng=?","expire=?,h_beli=?,ralan=?,kelas1=?,kelas2=?,kelas3=?,utama=?,vip=?,vvip=?,beliluar=?,jualbebas=?,karyawan=?",13,new String[]{
                                                    Valid.SetTgl(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()),
                                                    hargappn+"",
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("ralan")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("kelas1")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("kelas2")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("kelas3")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("utama")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("vip")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("vvip")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("beliluar")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("jualbebas")/100))),
                                                    Double.toString(hargappn+(hargappn*(rs.getDouble("karyawan")/100))),
                                                    tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString()
                                                });
                                            }
                                        }else if(!rs.next()){
                                            tbDokter.setValueAt(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString(),tbDokter.getSelectedRow(),1 );
                                            int reply = JOptionPane.showConfirmDialog(rootPane,"Maaf, konversi satuan tidak ditemukan. Apa anda ingin menambahkan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                                            if (reply == JOptionPane.YES_OPTION) {
                                                DlgKonversi konv=new DlgKonversi(null,false);
                                                konv.emptTeks();
                                                konv.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                                                konv.setLocationRelativeTo(internalFrame1);
                                                konv.setVisible(true);
                                            }                            
                                         }   
                                    }catch(Exception ex){
                                        System.out.println(ex);
                                    }
                                }  
                            }                                                              
                        }catch(Exception e){
                            System.out.println("Notifikasi : "+e);                             
                        } 
                        getData();  
                   }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                if(tbDokter.getSelectedColumn()==1){
                    y=0;
                    try {
                        y=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());                        
                    } catch (Exception e) {
                        y=0;
                    }
                    if(y>0){
                        satuanbarang.isCek();
                        satuanbarang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight());
                        satuanbarang.setLocationRelativeTo(internalFrame1);
                        satuanbarang.setVisible(true);                        
                    }else{
                        JOptionPane.showMessageDialog(null,"Silahkan masukkan jumlah pembelian terelebih dahulu..!!");
                    }
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnSimpan, kdsup);
}//GEN-LAST:event_NoFakturKeyPressed

private void TglPesanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPesanKeyPressed
        Valid.pindah(evt,NoFaktur,kdsup);
}//GEN-LAST:event_TglPesanKeyPressed

private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            kdptg.requestFocus(); 
        }
}//GEN-LAST:event_kdsupKeyPressed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());          
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            kdgudang.requestFocus();  
        }
}//GEN-LAST:event_kdptgKeyPressed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());        
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            TCari.requestFocus();    
        }
}//GEN-LAST:event_kdgudangKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void tppnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppnKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getData();
        }
    }//GEN-LAST:event_tppnKeyPressed

    private void TglTempoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTempoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTempoKeyPressed

    private void TglFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglFakturKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglFakturKeyPressed

    private void NoOrderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoOrderKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoOrderKeyPressed

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

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgUbahPemesanan dialog = new DlgUbahPemesanan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Label LPotongan;
    private widget.Label LPpn;
    private widget.Label LSubtotal;
    private widget.Label LTagiha;
    private widget.Label LTotal2;
    private widget.TextBox NoFaktur;
    private widget.TextBox NoOrder;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal TglFaktur;
    private widget.Tanggal TglPesan;
    private widget.Tanggal TglTempo;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdgudang;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    private widget.TextBox tppn;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        row=tbDokter.getRowCount();
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
        satuan=new String[jml];
        satuanbeli=new String[jml];
        kadaluwarsa=new String[jml];
        no_batch=new String[jml];
        harga=new double[jml];
        jumlah=new double[jml];
        subtotal=new double[jml];
        diskon=new double[jml];
        besardiskon=new double[jml];
        jmltotal=new double[jml];
        jmlstok=new double[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>0){
                    jumlah[index]=Double.parseDouble(tbDokter.getValueAt(i,0).toString());
                    satuanbeli[index]=tbDokter.getValueAt(i,1).toString();
                    kodebarang[index]=tbDokter.getValueAt(i,2).toString();
                    namabarang[index]=tbDokter.getValueAt(i,3).toString();
                    satuan[index]=tbDokter.getValueAt(i,4).toString();
                    kadaluwarsa[index]=tbDokter.getValueAt(i,6).toString();
                    harga[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                    subtotal[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                    diskon[index]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                    besardiskon[index]=Double.parseDouble(tbDokter.getValueAt(i,10).toString());
                    jmltotal[index]=Double.parseDouble(tbDokter.getValueAt(i,11).toString());
                    jmlstok[index]=Double.parseDouble(tbDokter.getValueAt(i,12).toString());
                    no_batch[index]=tbDokter.getValueAt(i,13).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{jumlah[i],satuanbeli[i],kodebarang[i],namabarang[i],satuan[i],true,kadaluwarsa[i],harga[i],subtotal[i],diskon[i],besardiskon[i],jmltotal[i],jmlstok[i],no_batch[i]});
        }
        
        try{
            ps.setString(1,"%"+TCari.getText().trim()+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                tabMode.addRow(new Object[]{
                    null,rs.getString(3),rs.getString(1),
                    rs.getString(2),rs.getString(3),true,
                    rs.getString(5),rs.getDouble(4),0,0,0,0,0,""
                });
            }                 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    private void getData(){        
        row=tbDokter.getSelectedRow();
        if(row!= -1){              
                try {
                    if(!tbDokter.getValueAt(row,0).toString().isEmpty()){                        
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,12).toString())*Double.parseDouble(tbDokter.getValueAt(row,7).toString()), row,8);                
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,8).toString())-Double.parseDouble(tbDokter.getValueAt(row,10).toString()), row,11);           
                    } 
                } catch (Exception e) {
                    tbDokter.setValueAt(0, row,8);                
                    tbDokter.setValueAt(0, row,11);           
                }
                
                ttl=0;sbttl=0;ppn=0;ttldisk=0;
                y=0;w=0;
                jml=tbDokter.getRowCount();
                for(i=0;i<jml;i++){                 
                    try {
                        w=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                    } catch (Exception e) {
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
                        w=0;                
                    }
                    sbttl=sbttl+w;                
                    
                    try {
                        y=Double.parseDouble(tbDokter.getValueAt(i,10).toString()); 
                    } catch (Exception e) {
                        tbDokter.setValueAt(0,tbDokter.getSelectedRow(),10);
                        y=0;
                    }
                    ttldisk=ttldisk+y;
                }
                LSubtotal.setText(Valid.SetAngka(sbttl));
                LPotongan.setText(Valid.SetAngka(ttldisk));
                ttl=sbttl-ttldisk;
                LTotal2.setText(Valid.SetAngka(ttl));
                ppn=0;
                if(!tppn.getText().equals("")){
                    ppn=(Double.parseDouble(tppn.getText())/100) *(ttl);
                    LPpn.setText(Valid.SetAngka(ppn));
                    LTagiha.setText(Valid.SetAngka(ttl+ppn));
                }
        }
    }
    
    public void isCek(String Faktur){
        try {
            rs=koneksi.prepareStatement("select pemesanan.tgl_pesan,pemesanan.no_faktur, "+
                    "pemesanan.kode_suplier,datasuplier.nama_suplier,pemesanan.no_order, "+
                    "pemesanan.nip,petugas.nama,pemesanan.kd_bangsal,bangsal.nm_bangsal,pemesanan.tgl_faktur, "+
                    "pemesanan.tgl_tempo,pemesanan.status,pemesanan.total1,pemesanan.potongan,pemesanan.total2,"+
                    "pemesanan.ppn,pemesanan.tagihan "+
                    " from pemesanan inner join datasuplier inner join petugas inner join bangsal  "+
                    " on  pemesanan.kd_bangsal=bangsal.kd_bangsal "+
                    " and pemesanan.kode_suplier=datasuplier.kode_suplier "+
                    " and pemesanan.nip=petugas.nip"+
                    " where no_faktur='"+Faktur+"'").executeQuery();
            if(rs.next()){
                NoFaktur.setText(Faktur);
                TglPesan.setDate(rs.getDate("tgl_pesan"));
                TglFaktur.setDate(rs.getDate("tgl_faktur"));
                TglTempo.setDate(rs.getDate("tgl_tempo"));
                NoOrder.setText(rs.getString("no_order"));
                kdsup.setText(rs.getString("kode_suplier"));
                nmsup.setText(rs.getString("nama_suplier"));
                kdptg.setText(rs.getString("nip"));
                nmptg.setText(rs.getString("nama"));
                kdgudang.setText(rs.getString("kd_bangsal"));
                nmgudang.setText(rs.getString("nm_bangsal"));
                sbttl=rs.getDouble("total1");
                LSubtotal.setText(Valid.SetAngka(sbttl));
                ttldisk=rs.getDouble("potongan");
                LPotongan.setText(Valid.SetAngka(ttldisk));
                ttl=sbttl-ttldisk;
                LTotal2.setText(Valid.SetAngka(ttl));
                ppn=rs.getDouble("ppn");
                LPpn.setText(Double.toString(ppn));
                tagihan=rs.getDouble("tagihan");
                LTagiha.setText(Valid.SetAngka(tagihan));
            }
            ps2.setString(1,Faktur);
            rs=ps2.executeQuery();
            rs.last();
            jml=rs.getRow();
            kodebarang=new String[jml];
            namabarang=new String[jml];
            satuan=new String[jml];
            satuanbeli=new String[jml];
            kadaluwarsa=new String[jml];
            no_batch=new String[jml];
            harga=new double[jml];
            jumlah=new double[jml];
            subtotal=new double[jml];
            diskon=new double[jml];
            besardiskon=new double[jml];
            jmltotal=new double[jml];
            jmlstok=new double[jml];
            index=0;    
            rs.beforeFirst();
            while(rs.next()){
                jumlah[index]=rs.getDouble("jumlah");
                satuanbeli[index]=rs.getString("kode_sat");
                kodebarang[index]=rs.getString("kode_brng");
                namabarang[index]=rs.getString("nama_brng");
                satuan[index]=rs.getString("satbar");
                kadaluwarsa[index]=rs.getString("expire");
                harga[index]=rs.getDouble("h_pesan");
                subtotal[index]=rs.getDouble("subtotal");
                diskon[index]=rs.getDouble("dis");
                besardiskon[index]=rs.getDouble("besardis");
                jmltotal[index]=rs.getDouble("total");
                jmlstok[index]=0;
                no_batch[index]=rs.getString("no_batch");
                index++;
            }           
            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[]{jumlah[i],satuanbeli[i],kodebarang[i],namabarang[i],satuan[i],true,kadaluwarsa[i],harga[i],subtotal[i],diskon[i],besardiskon[i],jmltotal[i],jmlstok[i],no_batch[i]});
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        TCari.requestFocus();
        tppn.setText("10");
    }

 
}

/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package inventory;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;

public class DlgPermintaanStokPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstampil;
    private ResultSet rstampil;
    private WarnaTable2 warna=new WarnaTable2();
    public DlgCariDokter dokter=new DlgCariDokter(null,false);
    public DlgCariAturanPakai aturanpakaiobat=new DlgCariAturanPakai(null,false);
    private double ttl=0,y=0,ppnobat=0,stokobat,kenaikan=0;
    private int jml=0,i=0,index=0;
    private String norawatibu,tampilkan_ppnobat_ranap="",aktifkanbatch="no",kelas="",bangsal="",kamar="",hppfarmasi="";
    private String[] keranap,kodebarang,namabarang,kategori,satuan,aturanpakai;
    private Double[] kapasitas,stok,harga,hargabeli,subtotal;
    private boolean[] pg,sg,sr,ml;
    private boolean sukses=false;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgPermintaanStokPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Jumlah","Kode Barang","Nama Barang","Jenis","Satuan","Kps","Stok","Harga","HargaBeli","Subtotal","Aturan Pakai","Pg","Sg","Sr","Ml"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==10)||(colIndex==11)||(colIndex==12)||(colIndex==13)||(colIndex==14)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Object.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(45);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(20);
            }else if(i==12){
                column.setPreferredWidth(20);
            }else if(i==13){
                column.setPreferredWidth(20);
            }else if(i==14){
                column.setPreferredWidth(20);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

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
        
        aturanpakaiobat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturanpakaiobat.getTable().getSelectedRow()!= -1){  
                    tbDokter.setValueAt(aturanpakaiobat.getTable().getValueAt(aturanpakaiobat.getTable().getSelectedRow(),0).toString(),tbDokter.getSelectedRow(),10);
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
        
        jam();
           
        try {  
            tampilkan_ppnobat_ranap=Sequel.cariIsi("select tampilkan_ppnobat_ranap from set_nota"); 
            
            try {
                aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
            } catch (Exception e) {
                System.out.println("E : "+e);
                aktifkanbatch = "no";
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try {
            hppfarmasi=koneksiDB.HPPFARMASI();
        } catch (Exception e) {
            hppfarmasi="dasar";
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
        KdPj = new widget.TextBox();
        LPpn = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        label10 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel13 = new widget.Label();
        btnDokter = new widget.Button();
        jLabel11 = new widget.Label();
        NoResep = new widget.TextBox();
        jLabel8 = new widget.Label();
        DTPBeri = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkRM = new widget.CekBox();
        ChkJln = new widget.CekBox();
        jLabel5 = new widget.Label();
        LTotal = new widget.Label();
        jLabel7 = new widget.Label();
        LTotalTagihan = new widget.Label();

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
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(65, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Permintaan Stok Obat & BHP Medis Pasien Di Ranap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDokterPropertyChange(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDokterKeyReleased(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi1.add(label12);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP", "Beli Luar", "Karyawan" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(130, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        panelisi1.add(Jeniskelas);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
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

        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(15, 23));
        panelisi1.add(label10);

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

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+C");
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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 107));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(75, 12, 120, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(196, 12, 487, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(75, 72, 120, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(196, 72, 210, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 72, 23);

        jLabel13.setText("Dokter :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 72, 72, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('3');
        btnDokter.setToolTipText("Alt+3");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(408, 72, 28, 23);

        jLabel11.setText("No.Permintaan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(435, 72, 90, 23);

        NoResep.setHighlighter(null);
        NoResep.setName("NoResep"); // NOI18N
        NoResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepKeyPressed(evt);
            }
        });
        FormInput.add(NoResep);
        NoResep.setBounds(528, 72, 130, 23);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 42, 72, 23);

        DTPBeri.setForeground(new java.awt.Color(50, 70, 50));
        DTPBeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-09-2020" }));
        DTPBeri.setDisplayFormat("dd-MM-yyyy");
        DTPBeri.setName("DTPBeri"); // NOI18N
        DTPBeri.setOpaque(false);
        DTPBeri.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPBeri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPBeriItemStateChanged(evt);
            }
        });
        DTPBeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPBeriKeyPressed(evt);
            }
        });
        FormInput.add(DTPBeri);
        DTPBeri.setBounds(75, 42, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(168, 42, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(233, 42, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(298, 42, 62, 23);

        ChkRM.setBorder(null);
        ChkRM.setSelected(true);
        ChkRM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRM.setName("ChkRM"); // NOI18N
        ChkRM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRMItemStateChanged(evt);
            }
        });
        FormInput.add(ChkRM);
        ChkRM.setBounds(660, 72, 23, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(363, 42, 23, 23);

        jLabel5.setText("Total :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(45, 23));
        FormInput.add(jLabel5);
        jLabel5.setBounds(385, 42, 45, 23);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(LTotal);
        LTotal.setBounds(433, 42, 85, 23);

        jLabel7.setText("Total+PPN :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(jLabel7);
        jLabel7.setBounds(520, 42, 65, 23);

        LTotalTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotalTagihan.setText("0");
        LTotalTagihan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotalTagihan.setName("LTotalTagihan"); // NOI18N
        LTotalTagihan.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(LTotalTagihan);
        LTotalTagihan.setBounds(588, 42, 95, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(NoResep.getText().trim().equals("")){
            Valid.textKosong(NoResep,"No.Resep");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kosong..!!!!");
            tbDokter.requestFocus();
        }else{            
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                if(ttl>0){
                    int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) { 
                        ChkJln.setSelected(false);    
                        Sequel.AutoComitFalse();
                        sukses=true;
                        
                        if(Sequel.menyimpantf2("permintaan_stok_obat_pasien","?,?,?,?,?,?,'0000-00-00','00:00:00'","Nomer Permintaan",6,new String[]{
                                NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),KdDokter.getText(),"Belum"
                            })==true){
                            simpandata();
                        }else{
                            emptTeksobat();
                            if(Sequel.menyimpantf2("permintaan_stok_obat_pasien","?,?,?,?,?,?,'0000-00-00','00:00:00'","Nomer Permintaan",6,new String[]{
                                    NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),KdDokter.getText(),"Belum"
                                })==true){
                                simpandata();
                            }else{
                                emptTeksobat();
                                if(Sequel.menyimpantf2("permintaan_stok_obat_pasien","?,?,?,?,?,?,'0000-00-00','00:00:00'","Nomer Permintaan",6,new String[]{
                                        NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),KdDokter.getText(),"Belum"
                                    })==true){
                                    simpandata();
                                }else{
                                    emptTeksobat();
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
                            for(index=0;index<tbDokter.getRowCount();index++){   
                                tbDokter.setValueAt("",index,0); 
                                tbDokter.setValueAt(0,index,9);  
                                tbDokter.setValueAt("",index,10);  
                                tbDokter.setValueAt(false,index,11);
                                tbDokter.setValueAt(false,index,12);
                                tbDokter.setValueAt(false,index,13);
                                tbDokter.setValueAt(false,index,14);  
                            }
                            ttl=0;
                            LTotal.setText("0");
                            LPpn.setText("0");
                            LTotalTagihan.setText("0");
                        }
                        ChkJln.setSelected(true);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan masukkan obat yang mau diberikan...!!!");
                    TCari.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           //Valid.pindah(evt,kdgudang,BtnCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

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
            Valid.pindah(evt, TCari, BtnSimpan);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tbDokter.getRowCount();
            for(int r=0;r<row2;r++){ 
                tbDokter.setValueAt("",r,0);
                tbDokter.setValueAt("",r,10);
            }
            
            LTotal.setText("0");
            LTotalTagihan.setText("0");
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariPermintaanStokPasien opname=new DlgCariPermintaanStokPasien(null,false);
        opname.isCek();
        opname.setRM(Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?",TNoRw.getText()),KdDokter.getText());
        opname.tampil();
        opname.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        opname.setLocationRelativeTo(internalFrame1);
        opname.setAlwaysOnTop(false);
        opname.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                int row=tbDokter.getSelectedRow();
                if(row!= -1){
                    tabMode.setValueAt("", row,0);
                    tabMode.setValueAt("", row,10);
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbDokter.getSelectedColumn();
                if(i==10){
                    aturanpakaiobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aturanpakaiobat.setLocationRelativeTo(internalFrame1);
                    aturanpakaiobat.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {                  
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
        tampil();
    }//GEN-LAST:event_JeniskelasItemStateChanged

    private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
        Valid.pindah(evt, TNoRw,TCari);
    }//GEN-LAST:event_JeniskelasKeyPressed

    private void tbDokterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyReleased
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                                     
                    getData();                     
                    TCari.setText("");
                    TCari.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {                                     
                    getData();           
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyReleased

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TPasien,TNoRw.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            TCari.requestFocus();
        }else{
            Valid.pindah(evt,KdDokter,DTPBeri);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",NmDokter,KdDokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,NoResep,BtnSimpan);
        }
    }//GEN-LAST:event_KdDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.isCek();
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        Valid.pindah(evt,KdDokter,BtnSimpan);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void NoResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepKeyPressed
        Valid.pindah(evt,cmbDtk,KdDokter);
    }//GEN-LAST:event_NoResepKeyPressed

    private void DTPBeriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPBeriItemStateChanged
        try {
            emptTeksobat();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_DTPBeriItemStateChanged

    private void DTPBeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPBeriKeyPressed
        Valid.pindah(evt,TNoRw,cmbJam);
    }//GEN-LAST:event_DTPBeriKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPBeri,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,NoResep);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkRMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRMItemStateChanged
        if(ChkRM.isSelected()==true){
            NoResep.setEditable(false);
            NoResep.setBackground(new Color(245,250,240));
            emptTeksobat();
        }else if(ChkRM.isSelected()==false){
            NoResep.setEditable(true);
            NoResep.setBackground(new Color(250,255,245));
            NoResep.setText("");
        }
    }//GEN-LAST:event_ChkRMItemStateChanged

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaanStokPasien dialog = new DlgPermintaanStokPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkRM;
    private widget.Tanggal DTPBeri;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox Kd2;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPj;
    private widget.Label LPpn;
    private widget.Label LTotal;
    private widget.Label LTotalTagihan;
    private widget.TextBox NmDokter;
    private widget.TextBox NoResep;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Button btnDokter;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel3;
    private widget.Label jLabel5;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        jml=0;
        for(i=0;i<tbDokter.getRowCount();i++){
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        keranap=null;
        keranap=new String[jml];
        kodebarang=null;
        kodebarang=new String[jml];
        namabarang=null;
        namabarang=new String[jml];
        kategori=null;
        kategori=new String[jml];
        satuan=null;
        satuan=new String[jml];
        kapasitas=null;
        kapasitas=new Double[jml];
        stok=null;
        stok=new Double[jml];
        harga=null;
        harga=new Double[jml];
        hargabeli=null;
        hargabeli=new Double[jml];
        subtotal=null;
        subtotal=new Double[jml];
        aturanpakai=null;
        aturanpakai=new String[jml];
        pg=null;
        pg=new boolean[jml];
        sg=null;
        sg=new boolean[jml];
        sr=null;
        sr=new boolean[jml];
        ml=null;
        ml=new boolean[jml];
        
        index=0;        
        for(i=0;i<tbDokter.getRowCount();i++){
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
                keranap[index]=tbDokter.getValueAt(i,0).toString();
                kodebarang[index]=tbDokter.getValueAt(i,1).toString();
                namabarang[index]=tbDokter.getValueAt(i,2).toString();
                kategori[index]=tbDokter.getValueAt(i,3).toString();
                satuan[index]=tbDokter.getValueAt(i,4).toString();
                kapasitas[index]=Double.parseDouble(tbDokter.getValueAt(i,5).toString());
                stok[index]=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
                harga[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                hargabeli[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                subtotal[index]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                aturanpakai[index]=tbDokter.getValueAt(i,10).toString();
                pg[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,11).toString());
                sg[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,12).toString());
                sr[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,13).toString());
                ml[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,14).toString());
                index++;
            }
        }
        
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{keranap[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],kapasitas[i],stok[i],harga[i],hargabeli[i],subtotal[i],aturanpakai[i],pg[i],sg[i],sr[i],ml[i]});
        }
        try{  
            if(kenaikan>0){
                if(aktifkanbatch.equals("yes")){
                    pstampil=koneksi.prepareStatement(
                        "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                        " (databarang.h_beli+(databarang.h_beli*?)) as harga,databarang."+hppfarmasi+" as dasar,sum(gudangbarang.stok) as stok, "+
                        " databarang.kapasitas from databarang inner join jenis inner join gudangbarang "+
                        " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                        " where  databarang.status='1' and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>'' and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                        "  databarang.status='1' and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>'' and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                        "  databarang.status='1' and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>'' and gudangbarang.kd_bangsal=? and jenis.nama like ? group by gudangbarang.kode_brng order by databarang.nama_brng");
                }else{
                    pstampil=koneksi.prepareStatement(
                        "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                        " (databarang.h_beli+(databarang.h_beli*?)) as harga,databarang."+hppfarmasi+" as dasar,gudangbarang.stok, "+
                        " databarang.kapasitas from databarang inner join jenis inner join gudangbarang "+
                        " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                        " where  databarang.status='1' and gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                        "  databarang.status='1' and gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                        "  databarang.status='1' and gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                }
                
                try{
                    pstampil.setDouble(1,kenaikan);
                    pstampil.setString(2,bangsal);
                    pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(4,bangsal);
                    pstampil.setString(5,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(6,bangsal);
                    pstampil.setString(7,"%"+TCari.getText().trim()+"%");
                    rstampil=pstampil.executeQuery();
                    while(rstampil.next()){
                        tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                           rstampil.getString("nama_brng"),
                           rstampil.getString("nama"),
                           rstampil.getString("kode_sat"),
                           rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                           Valid.roundUp(rstampil.getDouble("harga"),100),
                           rstampil.getDouble("dasar"),0,"",false,false,false,false
                        });
                    }                  
                }catch(Exception e){
                    System.out.println("Notif Data Barang : "+e);
                }finally{
                    if(rstampil!=null){
                        rstampil.close();
                    }
                    if(pstampil!=null){
                        pstampil.close();
                    }
                }
            }else{
                if(aktifkanbatch.equals("yes")){
                    pstampil=koneksi.prepareStatement(
                        "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                        " databarang.karyawan,databarang."+hppfarmasi+" as dasar,databarang.beliluar,databarang.kelas1," +
                        " databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,databarang.kapasitas,"+
                        " databarang.letak_barang,databarang.utama,databarang.h_beli,sum(gudangbarang.stok) as stok "+
                        " from databarang inner join jenis inner join gudangbarang "+
                        " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                        " where  databarang.status='1' and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>'' and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                        "  databarang.status='1' and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>'' and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                        "  databarang.status='1' and gudangbarang.no_batch<>'' and gudangbarang.no_faktur<>'' and gudangbarang.kd_bangsal=? and jenis.nama like ? group by gudangbarang.kode_brng order by databarang.nama_brng");
                }else{
                    pstampil=koneksi.prepareStatement(
                        "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                        " databarang.karyawan,databarang."+hppfarmasi+" as dasar,databarang.beliluar,databarang.kelas1," +
                        " databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,databarang.kapasitas,"+
                        " databarang.letak_barang,databarang.utama,databarang.h_beli,gudangbarang.stok "+
                        " from databarang inner join jenis inner join gudangbarang "+
                        " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=gudangbarang.kode_brng "+
                        " where  databarang.status='1' and gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                        "  databarang.status='1' and gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                        "  databarang.status='1' and gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");
                }

                try{
                    pstampil.setString(1,bangsal);
                    pstampil.setString(2,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(3,bangsal);
                    pstampil.setString(4,"%"+TCari.getText().trim()+"%");
                    pstampil.setString(5,bangsal);
                    pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                    rstampil=pstampil.executeQuery();
                    while(rstampil.next()){
                        if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                            tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                               rstampil.getString("nama_brng"),
                               rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),
                               rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                               Valid.roundUp(rstampil.getDouble("kelas1"),100),
                               rstampil.getDouble("dasar"),0,"",false,false,false,false
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                            tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                               rstampil.getString("nama_brng"),
                               rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),
                               rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                               Valid.roundUp(rstampil.getDouble("kelas2"),100),
                               rstampil.getDouble("dasar"),0,"",false,false,false,false
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                            tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                               rstampil.getString("nama_brng"),
                               rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),
                               rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                               Valid.roundUp(rstampil.getDouble("kelas3"),100),
                               rstampil.getDouble("dasar"),0,"",false,false,false,false
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                            tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                               rstampil.getString("nama_brng"),
                               rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),
                               rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                               Valid.roundUp(rstampil.getDouble("utama"),100),
                               rstampil.getDouble("dasar"),0,"",false,false,false,false
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                            tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                               rstampil.getString("nama_brng"),
                               rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),
                               rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                               Valid.roundUp(rstampil.getDouble("vip"),100),
                               rstampil.getDouble("dasar"),0,"",false,false,false,false
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                            tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                               rstampil.getString("nama_brng"),
                               rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),
                               rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                               Valid.roundUp(rstampil.getDouble("vvip"),100),
                               rstampil.getDouble("dasar"),0,"",false,false,false,false
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                               rstampil.getString("nama_brng"),
                               rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),
                               rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                               Valid.roundUp(rstampil.getDouble("beliluar"),100),
                               rstampil.getDouble("dasar"),0,"",false,false,false,false
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                               rstampil.getString("nama_brng"),
                               rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),
                               rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                               Valid.roundUp(rstampil.getDouble("karyawan"),100),
                               rstampil.getDouble("dasar"),0,"",false,false,false,false
                            });
                        }
                    }                  
                }catch(Exception e){
                    System.out.println("Notif Data Barang : "+e);
                }finally{
                    if(rstampil!=null){
                        rstampil.close();
                    }
                    if(pstampil!=null){
                        pstampil.close();
                    }
                }
            }   
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
     
    public void isCek(){
         BtnSimpan.setEnabled(akses.getpermintaan_stok_obat_pasien());   
    }

    public void setNoRm(String no_rawat,Date tanggal){
        TNoRw.setText(no_rawat);
        Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where no_rawat=? ",TPasien,TNoRw.getText());
        
        DTPBeri.setDate(tanggal);
        KdDokter.setText(Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=?",no_rawat));
        if(KdDokter.getText().equals("")){
            KdDokter.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",no_rawat));
        }
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",NmDokter,KdDokter.getText());
        
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",no_rawat));
        norawatibu=Sequel.cariIsi("select no_rawat from ranap_gabung where no_rawat2=?",no_rawat);
        if(!norawatibu.equals("")){
            kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",norawatibu);
        }else{
            kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",TNoRw.getText());
        }
        if(!norawatibu.equals("")){
            kelas=Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap "+
                "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                "and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norawatibu);
        }else{
            kelas=Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap "+
                "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                "and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
        }                
        if(kelas.equals("Kelas 1")){
            Jeniskelas.setSelectedItem("Kelas 1");
        }else if(kelas.equals("Kelas 2")){
            Jeniskelas.setSelectedItem("Kelas 2");
        }else if(kelas.equals("Kelas 3")){
            Jeniskelas.setSelectedItem("Kelas 3");
        }else if(kelas.equals("Kelas Utama")){
            Jeniskelas.setSelectedItem("Utama/BPJS");
        }else if(kelas.equals("Kelas VIP")){
            Jeniskelas.setSelectedItem("VIP");
        }else if(kelas.equals("Kelas VVIP")){
            Jeniskelas.setSelectedItem("VVIP");
        } 
        kenaikan=Sequel.cariIsiAngka2("select (hargajual/100) from  set_harga_obat_ranap where kd_pj=? and kelas=?",KdPj.getText(),kelas);
        TCari.requestFocus();
        bangsal=akses.getkdbangsal();
    }

    private void getData() {
        if(tbDokter.getSelectedRow()!= -1){
            try {
                if(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                    if(Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>Valid.SetAngka(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString())){
                        JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                        tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
                    }else{
                        tbDokter.setValueAt(Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())*Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),7).toString()),tbDokter.getSelectedRow(),9);
                    }
                }
            } catch (Exception e) {
                tbDokter.setValueAt("",tbDokter.getSelectedRow(),0);
            }   
            
            ttl=0;
            for(int r=0;r<tabMode.getRowCount();r++){ 
                 y=0;
                 try {
                     y=Double.parseDouble(tabMode.getValueAt(r,0).toString())*
                       Double.parseDouble(tabMode.getValueAt(r,7).toString());
                 } catch (Exception e) {
                     y=0;
                 }
                 ttl=ttl+y;
            }
            LTotal.setText(Valid.SetAngka(ttl));
            ppnobat=0;
            if(tampilkan_ppnobat_ranap.equals("Yes")){
                 ppnobat=ttl*0.1;
                 ttl=ttl+ppnobat;
                 LPpn.setText(Valid.SetAngka(ppnobat));
            }
            LTotalTagihan.setText(Valid.SetAngka(ttl));
        } 
    }
    
    
    public void emptTeksobat() {
        if(ChkRM.isSelected()==true){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_permintaan,4),signed)),0) from permintaan_stok_obat_pasien where tgl_permintaan='"+Valid.SetTgl(DTPBeri.getSelectedItem()+"")+"' ",
                "P"+DTPBeri.getSelectedItem().toString().substring(6,10)+DTPBeri.getSelectedItem().toString().substring(3,5)+DTPBeri.getSelectedItem().toString().substring(0,2),4,NoResep);        
        } 
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    private void simpandata() {
        for(i=0;i<tbDokter.getRowCount();i++){
            if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                if(Sequel.menyimpantf2("detail_permintaan_stok_obat_pasien","?,?,?,?,?,?,?,?","Permintaan Stok Obat Pasien",8,new String[]{                            
                        NoResep.getText(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,10).toString(),
                        tabMode.getValueAt(i,11).toString(),tabMode.getValueAt(i,12).toString(),tabMode.getValueAt(i,13).toString(),tabMode.getValueAt(i,14).toString()
                    })==false){
                    sukses=false;
                } 
            }
        }
    }
}

package inventory;



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
import simrskhanza.DlgCariBangsal;

public class HibahObatBHP extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private riwayatobat Trackobat=new riwayatobat();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private CariHibahObatBHP form=new CariHibahObatBHP(null,false);
    private DlgCariDataKonversi datakonversi=new DlgCariDataKonversi(null,false);
    private double hargakonversi=0,ttl=0,ttl2=0,y=0,w=0,jmlkonversi=0,nilaibarang;
    private int jml=0,i=0,row=0,index=0;
    private String[] kodebarang,namabarang,satuan,satuanhibah,kadaluwarsa,nobatch;
    private double[] harga,jumlah,subtotal,harga2,subtotal2,jmlstok,isi,jmlbesar,
                     ralan,kelas1,kelas2,kelas3,utama,vip,vvip,beliluar,jualbebas,karyawan,dasar;
    private WarnaTable2 warna=new WarnaTable2();
    private String aktifkanbatch="no",pengaturanharga=Sequel.cariIsi("select setharga from set_harga_obat");
    private boolean sukses=true;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public HibahObatBHP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
        }

        tabMode=new DefaultTableModel(null,new Object[]{
            "Jml","Sat Hibah","Kode Barang","Nama Barang","Satuan","Kadaluwarsa","Nilai Hibah(Rp)",
            "Subttl Hibah(Rp)","Nilai Diakui(Rp)","Subttl Diakui(Rp)","Jml2","No.Batch",
            "Ralan", "Kelas 1", "Kelas 2", "Kelas 3", "Utama", "VIP", "VVIP", "Beli Luar", 
            "Jual Bebas", "Karyawan","Isi","Isibesar","Dasar"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
               boolean a = false;
               if ((colIndex==0)||(colIndex==5)||(colIndex==6)||(colIndex==8)||(colIndex==11)) {
                   a=true;
               }
               return a;
             }
              
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 25; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(42);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(190);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(95);
            }else if(i==8){
                column.setPreferredWidth(85);
            }else if(i==9){
                column.setPreferredWidth(95);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);                   
            }    
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        NoFaktur.setDocument(new batasInput((byte)20).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
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
        
        form.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                autoNomor();
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
                if(akses.getform().equals("HibahObatBHP")){
                    if(bangsal.getTable().getSelectedRow()!= -1){                   
                        kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
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
        
        form.suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("HibahObatBHP")){
                    if(form.suplier.getTable().getSelectedRow()!= -1){                   
                        kdsup.setText(form.suplier.getTable().getValueAt(form.suplier.getTable().getSelectedRow(),0).toString());                    
                        nmsup.setText(form.suplier.getTable().getValueAt(form.suplier.getTable().getSelectedRow(),1).toString());
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
        
        form.suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("HibahObatBHP")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        form.suplier.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });            
        
        form.petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("HibahObatBHP")){
                    if(form.petugas.getTable().getSelectedRow()!= -1){                   
                        kdptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(),1).toString());
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
        
        datakonversi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(datakonversi.getTable().getSelectedRow()!= -1){    
                    tbDokter.setValueAt(datakonversi.getTable().getValueAt(datakonversi.getTable().getSelectedRow(),1).toString(),tbDokter.getSelectedRow(),1);
                    try{
                        tbDokter.setValueAt(Double.parseDouble(datakonversi.getTable().getValueAt(datakonversi.getTable().getSelectedRow(),3).toString()),tbDokter.getSelectedRow(),25);
                    }catch(Exception er){
                        JOptionPane.showMessageDialog(null,"Gagal mengambil nilai konversi, nilai barang satuan kecil dianggap bernilai 1..!!");
                        tbDokter.setValueAt(1,tbDokter.getSelectedRow(),25);
                    }
                    
                    try{
                        tbDokter.setValueAt(Double.parseDouble(datakonversi.getTable().getValueAt(datakonversi.getTable().getSelectedRow(),0).toString()),tbDokter.getSelectedRow(),26);
                    }catch(Exception er){
                        JOptionPane.showMessageDialog(null,"Gagal mengambil nilai konversi, nilai barang satuan besar dianggap bernilai 1..!!");
                        tbDokter.setValueAt(1,tbDokter.getSelectedRow(),26);
                    }   
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
        
        datakonversi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    datakonversi.dispose();
                }     
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
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
        ppStok1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        LTotal1 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        label12 = new widget.Label();
        LTotal2 = new widget.Label();
        label14 = new widget.Label();
        BtnTambah = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label11 = new widget.Label();
        TglBeli = new widget.Tanggal();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label20 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        btnGudang = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
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

        ppStok1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok1.setForeground(new java.awt.Color(50, 50, 50));
        ppStok1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok1.setText("Cek Stok Lokasi");
        ppStok1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok1.setName("ppStok1"); // NOI18N
        ppStok1.setPreferredSize(new java.awt.Dimension(180, 25));
        ppStok1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStok1ActionPerformed(evt);
            }
        });
        Popup.add(ppStok1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Hibah Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        tbDokter.setToolTipText("Masukkan jumlah geser ke kanan");
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
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 77));
        panelisi1.setLayout(null);

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
        BtnSimpan.setBounds(10, 32, 100, 30);

        label10.setText("Keyword :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label10);
        label10.setBounds(110, 35, 75, 23);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);
        TCari.setBounds(190, 35, 280, 23);

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
        BtnCari1.setBounds(472, 35, 28, 23);

        LTotal1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal1.setText("0");
        LTotal1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal1.setName("LTotal1"); // NOI18N
        LTotal1.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LTotal1);
        LTotal1.setBounds(109, 5, 200, 23);

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
        BtnCari.setBounds(560, 32, 100, 30);

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
        BtnKeluar.setBounds(670, 32, 100, 30);

        label12.setText("Total Nilai Hibah :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label12);
        label12.setBounds(0, 5, 105, 23);

        LTotal2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal2.setText("0");
        LTotal2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal2.setName("LTotal2"); // NOI18N
        LTotal2.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LTotal2);
        LTotal2.setBounds(534, 5, 200, 23);

        label14.setText("Total Pengakuan Nilai Persediaan Hibah :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label14);
        label14.setBounds(320, 5, 210, 23);

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
        BtnTambah.setBounds(501, 35, 28, 23);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Hibah :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 67, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(71, 10, 130, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(205, 10, 60, 23);

        TglBeli.setDisplayFormat("dd-MM-yyyy");
        TglBeli.setName("TglBeli"); // NOI18N
        TglBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeliKeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli);
        TglBeli.setBounds(269, 10, 90, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(370, 40, 75, 23);

        kdsup.setEditable(false);
        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(449, 10, 80, 23);

        label16.setText("Asal Hibah :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(370, 10, 75, 23);

        kdptg.setEditable(false);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(449, 40, 100, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(531, 10, 200, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(551, 40, 180, 23);

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

        label20.setText("Lokasi :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label20);
        label20.setBounds(0, 40, 67, 23);

        kdgudang.setEditable(false);
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(71, 40, 80, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(153, 40, 174, 23);

        btnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnGudang.setMnemonic('2');
        btnGudang.setToolTipText("Alt+2");
        btnGudang.setName("btnGudang"); // NOI18N
        btnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGudangActionPerformed(evt);
            }
        });
        panelisi3.add(btnGudang);
        btnGudang.setBounds(330, 40, 28, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.emptTeks();    
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

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
        jml=tbDokter.getRowCount();
        index=0;
        for(i=0;i<jml;i++){
            if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                index++;
            }
        }
        if(aktifkanbatch.equals("yes")){
            row=0;
            for(i=0;i<jml;i++){
                if((Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0)&&tbDokter.getValueAt(i,11).toString().trim().equals("")){
                    row++;
                }
            }
        }
        
        if(NoFaktur.getText().trim().equals("")){
            Valid.textKosong(NoFaktur,"No.Hibah");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Supplier");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(aktifkanbatch.equals("yes")&&(row>0)){
            Valid.textKosong(TCari,"No.Batch");
        }else if(index<=0){
            JOptionPane.showMessageDialog(null,"Maaf, silakan masukkan data hibah...!!!!");
            tbDokter.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang hendak disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    Sequel.AutoComitFalse();
                    sukses=true;
                    if(Sequel.menyimpantf2("hibah_obat_bhp","?,?,?,?,?,?,?","No.Hibah",7,new String[]{
                            NoFaktur.getText(),kdsup.getText(),kdptg.getText(),Valid.SetTgl(TglBeli.getSelectedItem()+""),""+ttl,""+ttl2,kdgudang.getText()
                        })==true){
                        jml=tbDokter.getRowCount();
                        for(i=0;i<jml;i++){  
                            setKonversi(i);
                            try {
                                if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                                    if(Sequel.menyimpantf2("detailhibah_obat_bhp","?,?,?,?,?,?,?,?,?,?,?","Transaksi Hibah",11,new String[]{
                                           NoFaktur.getText(),
                                           tbDokter.getValueAt(i,2).toString(),
                                           tbDokter.getValueAt(i,1).toString(),
                                           tbDokter.getValueAt(i,0).toString(),
                                           tbDokter.getValueAt(i,6).toString(),
                                           tbDokter.getValueAt(i,7).toString(),
                                           tbDokter.getValueAt(i,8).toString(),
                                           tbDokter.getValueAt(i,9).toString(),
                                           tbDokter.getValueAt(i,11).toString(),
                                           tbDokter.getValueAt(i,10).toString(),
                                           Valid.SetTgl(tbDokter.getValueAt(i,5).toString()+"")
                                    })==true){
                                        if(aktifkanbatch.equals("yes")){
                                            Trackobat.catatRiwayat(tbDokter.getValueAt(i,2).toString(),Valid.SetAngka(tbDokter.getValueAt(i,10).toString()),0,"Hibah",akses.getkode(),kdgudang.getText(),"Simpan",tbDokter.getValueAt(i,11).toString(),NoFaktur.getText());
                                            Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,2).toString()+"','"+kdgudang.getText()+"','"+tbDokter.getValueAt(i,10).toString()+"','"+tbDokter.getValueAt(i,11).toString()+"','"+NoFaktur.getText()+"'", 
                                                       "stok=stok+'"+tbDokter.getValueAt(i,10).toString()+"'","kode_brng='"+tbDokter.getValueAt(i,2).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='"+tbDokter.getValueAt(i,11).toString()+"' and no_hibah='"+NoFaktur.getText()+"'");
                                        }else{
                                            Trackobat.catatRiwayat(tbDokter.getValueAt(i,2).toString(),Valid.SetAngka(tbDokter.getValueAt(i,10).toString()),0,"Hibah",akses.getkode(),kdgudang.getText(),"Simpan","","");
                                            Sequel.menyimpan("gudangbarang","'"+tbDokter.getValueAt(i,2).toString()+"','"+kdgudang.getText()+"','"+tbDokter.getValueAt(i,10).toString()+"','',''", 
                                                       "stok=stok+'"+tbDokter.getValueAt(i,10).toString()+"'","kode_brng='"+tbDokter.getValueAt(i,2).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_hibah=''");
                                        }   
                                        simpanbatch();
                                    }else{
                                        sukses=false;
                                    }                                     
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            }                
                        }  
                        if(sukses==true){
                            if(ttl2>0){
                                Sequel.queryu("delete from tampjurnal");
                                Sequel.menyimpan2("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Hibah_Obat from set_akun"),"PERSEDIAAN HIBAH OBAT & BHP",""+(ttl2),"0"});
                                Sequel.menyimpan2("tampjurnal","?,?,?,?",4,new String[]{Sequel.cariIsi("select Kontra_Hibah_Obat from set_akun"),"PENDAPATAN HIBAH","0",""+(ttl2)}); 
                                sukses=jur.simpanJurnal(NoFaktur.getText(),Valid.SetTgl(TglBeli.getSelectedItem()+""),"U","HIBAH OBAT & BHP DI "+nmgudang.getText().toUpperCase()+", OLEH "+akses.getkode());  
                            }   
                        }
                    }else{
                        sukses=false;
                        JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan No.Hibah sudah ada sebelumnya...!!");
                    } 
                    
                    if(sukses==true){
                        Sequel.Commit();
                        jml=tbDokter.getRowCount();
                        for(i=0;i<jml;i++){ 
                            tbDokter.setValueAt("",i,0);
                            tbDokter.setValueAt(0,i,7);
                            tbDokter.setValueAt(0,i,9);
                            tbDokter.setValueAt(0,i,10);
                            tbDokter.setValueAt("",i,11);
                        }
                        
                        LTotal1.setText("0");
                        LTotal2.setText("0");
                    }else{
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                    autoNomor();
                } catch (Exception ex) {
                    System.out.println(ex);                    
                }
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

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

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
                tbDokter.setValueAt("",i,0);
                tbDokter.setValueAt(0,i,7);
                tbDokter.setValueAt(0,i,9);
                tbDokter.setValueAt(0,i,10);
                tbDokter.setValueAt("",i,11);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnSimpan, kdsup);
}//GEN-LAST:event_NoFakturKeyPressed

private void TglBeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeliKeyPressed
        Valid.pindah(evt,NoFaktur,kdsup);
}//GEN-LAST:event_TglBeliKeyPressed

private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            kdptg.requestFocus(); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSuplierActionPerformed(null);
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
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        akses.setform("HibahObatBHP");
        form.suplier.emptTeks();
        form.suplier.isCek();
        form.suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.suplier.setLocationRelativeTo(internalFrame1);
        form.suplier.setAlwaysOnTop(false);
        form.suplier.setVisible(true);
}//GEN-LAST:event_btnSuplierActionPerformed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("HibahObatBHP");
        form.petugas.emptTeks();
        form.petugas.isCek();
        form.petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.petugas.setLocationRelativeTo(internalFrame1);
        form.petugas.setAlwaysOnTop(false);
        form.petugas.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());        
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            TCari.requestFocus();    
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnGudangActionPerformed(null);
        }
}//GEN-LAST:event_kdgudangKeyPressed

private void btnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGudangActionPerformed
        akses.setform("HibahObatBHP");
        bangsal.isCek();
        bangsal.emptTeks();        
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
}//GEN-LAST:event_btnGudangActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarang barang=new DlgBarang(null,false);
        if(!nmgudang.getText().trim().equals("")){
            akses.setform("tampil3");            
            barang.setLokasi(kdgudang.getText(),nmgudang.getText());
        }else{
            akses.setform("tampil");
        }        
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tbDokter.getRowCount()!=0){
            try {
                if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==5)||(tbDokter.getSelectedColumn()==7)||(tbDokter.getSelectedColumn()==8)||(tbDokter.getSelectedColumn()==10)){
                    setKonversi(tbDokter.getSelectedRow());
                    getData();
                }else if(tbDokter.getSelectedColumn()==9){
                    setKonversi(tbDokter.getSelectedRow());
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

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if(this.isVisible()==true){
            getData();
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tbDokter.getRowCount()!=0){
                                    /*"Jml"0,"Satuan Hibah"1,"Kode Barang"2,"Nama Barang"3,"Satuan"4,"Kadaluwarsa"5,"Nilai Hibah(Rp)"6,
                    "Subttl Hibah(Rp)"7,"Nilai Diakui(Rp)"8,"Subttl Diakui(Rp)"9,"Jml2"10,"No.Batch"11,
                    "Ralan"12, "Kelas 1"13, "Kelas 2"14, "Kelas 3"15, "Utama"16, "VIP"17, "VVIP"18, "Beli Luar"19, 
                    "Jual Bebas"20, "Karyawan"21,"Isi"22,"Isibesar"23,"Dasar"24*/
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    try {
                        if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==6)||(tbDokter.getSelectedColumn()==8)||(tbDokter.getSelectedColumn()==5)){
                            setKonversi(tbDokter.getSelectedRow());
                            getData();
                            TCari.setText("");
                            TCari.requestFocus();
                        }else if(tbDokter.getSelectedColumn()==11){
                            TCari.setText("");
                            TCari.requestFocus();
                        }
                    } catch (Exception e) {
                    }   break;
                case KeyEvent.VK_DELETE:
                    try {
                        if(tbDokter.getSelectedColumn()==0){
                            tbDokter.setValueAt(null, tbDokter.getSelectedRow(),0);
                        }else if(tbDokter.getSelectedColumn()==6){
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),6);
                        }else if(tbDokter.getSelectedColumn()==8){
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),8);
                        }else if(tbDokter.getSelectedColumn()==11){
                            tbDokter.setValueAt("", tbDokter.getSelectedRow(),11);
                        }
                    } catch (Exception e) {
                    }   break;
                case KeyEvent.VK_BACK_SPACE:
                    try {
                        if(tbDokter.getSelectedColumn()==0){                        
                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("0,0")){
                                tbDokter.setValueAt("", tbDokter.getSelectedRow(),0);
                            }
                        }else if(tbDokter.getSelectedColumn()==6){
                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString().equals("0,0")){
                                tbDokter.setValueAt("", tbDokter.getSelectedRow(),6);
                            }
                        }else if(tbDokter.getSelectedColumn()==8){
                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString().equals("0,0")){
                                tbDokter.setValueAt("", tbDokter.getSelectedRow(),8);
                            }
                        }else if(tbDokter.getSelectedColumn()==11){
                            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),11).toString().equals("0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),11).toString().equals("0.0")||tbDokter.getValueAt(tbDokter.getSelectedRow(),11).toString().equals("0,0")){
                                tbDokter.setValueAt("", tbDokter.getSelectedRow(),11);
                            }
                        }
                    } catch (Exception e) {
                    }   break;
                case KeyEvent.VK_SHIFT:
                    TCari.setText("");
                    TCari.requestFocus();
                    break;
                case KeyEvent.VK_RIGHT:
                        setKonversi(tbDokter.getSelectedRow());
                        getData();
                    break;
                case KeyEvent.VK_SPACE:
                    if(tbDokter.getSelectedColumn()==1){
                        y=0;
                        try {
                            y=Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        } catch (Exception e) {
                            y=0;
                        }
                        if(y>0){
                            datakonversi.setSatuanKecil(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            datakonversi.isCek();
                            datakonversi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight());
                            datakonversi.setLocationRelativeTo(internalFrame1);
                            datakonversi.setVisible(true);
                        }else{
                            JOptionPane.showMessageDialog(null,"Silakan masukkan jumlah hibah obat & bhp terelebih dahulu..!!");
                        }
                    }   break;
                default:
                    break;
            }
        }    
    }//GEN-LAST:event_tbDokterKeyPressed

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCekStok ceksetok=new DlgCekStok(null,false);
        ceksetok.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ceksetok.setLocationRelativeTo(internalFrame1);
        ceksetok.setAlwaysOnTop(false);
        ceksetok.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppStok1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            HibahObatBHP dialog = new HibahObatBHP(new javax.swing.JFrame(), true);
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
    private widget.Button BtnTambah;
    private widget.TextBox Kd2;
    private widget.Label LTotal1;
    private widget.Label LTotal2;
    private widget.TextBox NoFaktur;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal TglBeli;
    private widget.Button btnGudang;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
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
    private widget.Label label20;
    private widget.TextBox nmgudang;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
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
        satuanhibah=new String[jml];
        kadaluwarsa=new String[jml];
        nobatch=new String[jml];
        harga=new double[jml];
        jumlah=new double[jml];
        subtotal=new double[jml];
        harga2=new double[jml];
        subtotal2=new double[jml];
        jmlstok=new double[jml];
        ralan=new double[jml];
        kelas1=new double[jml];
        kelas2=new double[jml];
        kelas3=new double[jml];
        utama=new double[jml];
        vip=new double[jml];
        vvip=new double[jml];
        beliluar=new double[jml];
        jualbebas=new double[jml];
        karyawan=new double[jml]; 
        isi=new double[jml]; 
        jmlbesar=new double[jml];
        dasar=new double[jml];        
        
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                    jumlah[index]=Double.parseDouble(tbDokter.getValueAt(i,0).toString());
                    satuanhibah[index]=tbDokter.getValueAt(i,1).toString();
                    kodebarang[index]=tbDokter.getValueAt(i,2).toString();
                    namabarang[index]=tbDokter.getValueAt(i,3).toString();
                    satuan[index]=tbDokter.getValueAt(i,4).toString();
                    kadaluwarsa[index]=tbDokter.getValueAt(i,5).toString();
                    harga[index]=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
                    subtotal[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                    harga2[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                    subtotal2[index]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                    jmlstok[index]=Double.parseDouble(tbDokter.getValueAt(i,10).toString());
                    nobatch[index]=tbDokter.getValueAt(i,11).toString();
                    ralan[index]=Double.parseDouble(tbDokter.getValueAt(i,12).toString());
                    kelas1[index]=Double.parseDouble(tbDokter.getValueAt(i,13).toString());
                    kelas2[index]=Double.parseDouble(tbDokter.getValueAt(i,14).toString());
                    kelas3[index]=Double.parseDouble(tbDokter.getValueAt(i,15).toString());
                    utama[index]=Double.parseDouble(tbDokter.getValueAt(i,16).toString());
                    vip[index]=Double.parseDouble(tbDokter.getValueAt(i,17).toString());
                    vvip[index]=Double.parseDouble(tbDokter.getValueAt(i,18).toString());
                    beliluar[index]=Double.parseDouble(tbDokter.getValueAt(i,19).toString());
                    jualbebas[index]=Double.parseDouble(tbDokter.getValueAt(i,20).toString());
                    karyawan[index]=Double.parseDouble(tbDokter.getValueAt(i,21).toString());
                    isi[index]=Double.parseDouble(tbDokter.getValueAt(i,22).toString());
                    jmlbesar[index]=Double.parseDouble(tbDokter.getValueAt(i,23).toString());
                    dasar[index]=Double.parseDouble(tbDokter.getValueAt(i,24).toString());
                    index++;
                }
            } catch (Exception e) {
                System.out.println("e "+e);
            }
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{
                jumlah[i],satuanhibah[i],kodebarang[i],namabarang[i],satuan[i],kadaluwarsa[i],harga[i],subtotal[i],harga2[i],subtotal2[i],
                jmlstok[i],nobatch[i],ralan[i],kelas1[i],kelas2[i],kelas3[i],utama[i],vip[i],vvip[i],beliluar[i],jualbebas[i],karyawan[i],
                isi[i],jmlbesar[i],dasar[i]
            });
        }
        
        try{
            ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,databarang.kode_sat, databarang.h_beli, "+
                " databarang.kode_satbesar,databarang.isi,(databarang.h_beli*databarang.isi) as hargabesar "+
                " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                " where databarang.status='1' and databarang.kode_brng like ? or "+
                " databarang.status='1' and databarang.nama_brng like ? or "+
                " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        "",rs.getString("kode_satbesar"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("kode_sat"),"00-00-0000",
                        rs.getDouble("hargabesar"),0,rs.getDouble("hargabesar"),0,0,"",0,0,0,0,0,0,0,0,0,0,rs.getDouble("isi"),1,0
                    });
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
        if(row!= -1){ 
            if(!tbDokter.getValueAt(row,0).toString().equals("")){
                try {
                    if(Double.parseDouble(tbDokter.getValueAt(row,0).toString())>0){                        
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*Double.parseDouble(tbDokter.getValueAt(row,6).toString()), row,7);  
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*Double.parseDouble(tbDokter.getValueAt(row,8).toString()), row,9);  
                    } 
                } catch (Exception e) {
                    tbDokter.setValueAt("", row,0);
                    tbDokter.setValueAt(0, row,7);
                    tbDokter.setValueAt(0, row,9);         
                }
            }else{
                tbDokter.setValueAt(0, row,7);                
                tbDokter.setValueAt(0, row,9);    
            }   
        }
                
        ttl=0;ttl2=0;y=0;w=0;
        
        jml=tbDokter.getRowCount();
        for(i=0;i<jml;i++){                 
            try {
                w=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
            } catch (Exception e) {
                tbDokter.setValueAt(0,tbDokter.getSelectedRow(),7);
                w=0;                
            }
            ttl=ttl+w;                

            try {
                y=Double.parseDouble(tbDokter.getValueAt(i,9).toString()); 
            } catch (Exception e) {
                tbDokter.setValueAt(0,tbDokter.getSelectedRow(),9);
                y=0;
            }
            ttl2=ttl2+y;
        }
        LTotal1.setText(Valid.SetAngka(ttl));
        LTotal2.setText(Valid.SetAngka(ttl2));
    }
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            btnPetugas.setEnabled(false);
            kdptg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.gethibah_obat_bhp());
            BtnTambah.setEnabled(akses.getobat());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }        
    }

    private void autoNomor(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_hibah,3),signed)),0) from hibah_obat_bhp where tgl_hibah='"+Valid.SetTgl(TglBeli.getSelectedItem()+"")+"'","HO"+TglBeli.getSelectedItem().toString().substring(6,10)+TglBeli.getSelectedItem().toString().substring(3,5)+TglBeli.getSelectedItem().toString().substring(0,2),3,NoFaktur); 
    }
    
    private void simpanbatch(){    
        if((!tbDokter.getValueAt(i,11).toString().equals(""))&&(!tbDokter.getValueAt(i,12).toString().equals(""))&&
            (!tbDokter.getValueAt(i,13).toString().equals(""))&&(!tbDokter.getValueAt(i,14).toString().equals(""))&&
            (!tbDokter.getValueAt(i,15).toString().equals(""))&&(!tbDokter.getValueAt(i,16).toString().equals(""))&&
            (!tbDokter.getValueAt(i,17).toString().equals(""))&&(!tbDokter.getValueAt(i,18).toString().equals(""))&&
            (!tbDokter.getValueAt(i,19).toString().equals(""))&&(!tbDokter.getValueAt(i,20).toString().equals(""))&&
            (!tbDokter.getValueAt(i,21).toString().equals(""))&&(!tbDokter.getValueAt(i,24).toString().equals(""))){
            Sequel.menyimpan2("data_batch","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Batch",20,new String[]{
                tbDokter.getValueAt(i,11).toString(),tbDokter.getValueAt(i,2).toString(),Valid.SetTgl(TglBeli.getSelectedItem()+""),Valid.SetTgl(tbDokter.getValueAt(i,5).toString()),"Hibah",NoFaktur.getText(),
                tbDokter.getValueAt(i,24).toString(),tbDokter.getValueAt(i,24).toString(),tbDokter.getValueAt(i,12).toString(),tbDokter.getValueAt(i,13).toString(),tbDokter.getValueAt(i,14).toString(),tbDokter.getValueAt(i,15).toString(),
                tbDokter.getValueAt(i,16).toString(),tbDokter.getValueAt(i,17).toString(),tbDokter.getValueAt(i,18).toString(),tbDokter.getValueAt(i,19).toString(),tbDokter.getValueAt(i,20).toString(),tbDokter.getValueAt(i,21).toString(),
                tbDokter.getValueAt(i,10).toString(),tbDokter.getValueAt(i,10).toString()
            });
        }
    }
    
    private void setKonversi(int baris){        
        try {
            if(Valid.SetAngka(tbDokter.getValueAt(baris,0).toString())>0){
                switch (pengaturanharga) {
                    case "Per Jenis":
                        try{
                            rs=koneksi.prepareStatement("select * from setpenjualan where kdjns='"+Sequel.cariIsi("select kdjns from databarang where kode_brng='"+tbDokter.getValueAt(baris,2).toString()+"'")+"'").executeQuery();
                            if(rs.next()){
                                if(tbDokter.getValueAt(baris,1).toString().equals(tbDokter.getValueAt(baris,4).toString())){
                                    try {
                                        jmlkonversi=Double.parseDouble(tbDokter.getValueAt(baris,0).toString());
                                    } catch (Exception e) {
                                        jmlkonversi=0;
                                    }
                                    tbDokter.setValueAt(jmlkonversi,baris,10);
                                    
                                    nilaibarang=0;
                                    try {
                                        nilaibarang=Double.parseDouble(tbDokter.getValueAt(baris,8).toString());
                                    } catch (Exception e) {
                                        nilaibarang=0;
                                    }
                                    
                                    tbDokter.setValueAt(Math.round(nilaibarang),baris,24);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("ralan")/100)),100),baris,12);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("kelas1")/100)),100),baris,13);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("kelas2")/100)),100),baris,14);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("kelas3")/100)),100),baris,15);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("utama")/100)),100),baris,16);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("vip")/100)),100),baris,17);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("vvip")/100)),100),baris,18);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("beliluar")/100)),100),baris,19);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("jualbebas")/100)),100),baris,20);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("karyawan")/100)),100),baris,21);
                                }else{
                                    try {
                                        jmlkonversi=Double.parseDouble(tbDokter.getValueAt(baris,0).toString())*(Double.parseDouble(tbDokter.getValueAt(baris,22).toString())/Double.parseDouble(tbDokter.getValueAt(baris,23).toString()));
                                    } catch (Exception e) {
                                        jmlkonversi=0;
                                    }
                                    tbDokter.setValueAt(jmlkonversi,baris,10);
                                    
                                    nilaibarang=0;
                                    try {
                                        nilaibarang=Double.parseDouble(tbDokter.getValueAt(baris,9).toString());
                                    } catch (Exception e) {
                                        nilaibarang=0;
                                    }

                                    hargakonversi=nilaibarang/jmlkonversi;
                                    tbDokter.setValueAt(Math.round(hargakonversi),baris,24);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("ralan")/100)),100),baris,12);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("kelas1")/100)),100),baris,13);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("kelas2")/100)),100),baris,14);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("kelas3")/100)),100),baris,15);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("utama")/100)),100),baris,16);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("vip")/100)),100),baris,17);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("vvip")/100)),100),baris,18);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("beliluar")/100)),100),baris,19);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("jualbebas")/100)),100),baris,20);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("karyawan")/100)),100),baris,21);
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Pengaturan untuk jenis obat/alkes/bhp ini tidak ditemukan..!!");
                                TCari.requestFocus();
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs!=null){
                                rs.close();
                            }
                        }   break;
                    case "Umum":
                        try{
                            rs=koneksi.prepareStatement("select * from setpenjualanumum").executeQuery();
                            if(rs.next()){
                                if(tbDokter.getValueAt(baris,1).toString().equals(tbDokter.getValueAt(baris,4).toString())){
                                    try {
                                        jmlkonversi=Double.parseDouble(tbDokter.getValueAt(baris,0).toString());
                                    } catch (Exception e) {
                                        jmlkonversi=0;
                                    }
                                    tbDokter.setValueAt(jmlkonversi,baris,10);
                                    
                                    nilaibarang=0;
                                    try {
                                        nilaibarang=Double.parseDouble(tbDokter.getValueAt(baris,8).toString());
                                    } catch (Exception e) {
                                        nilaibarang=0;
                                    }

                                    tbDokter.setValueAt(Math.round(nilaibarang),baris,24);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("ralan")/100)),100),baris,12);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("kelas1")/100)),100),baris,13);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("kelas2")/100)),100),baris,14);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("kelas3")/100)),100),baris,15);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("utama")/100)),100),baris,16);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("vip")/100)),100),baris,17);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("vvip")/100)),100),baris,18);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("beliluar")/100)),100),baris,19);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("jualbebas")/100)),100),baris,20);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("karyawan")/100)),100),baris,21);
                                }else{
                                    try {
                                        jmlkonversi=Double.parseDouble(tbDokter.getValueAt(baris,0).toString())*(Double.parseDouble(tbDokter.getValueAt(baris,22).toString())/Double.parseDouble(tbDokter.getValueAt(baris,23).toString()));
                                    } catch (Exception e) {
                                        jmlkonversi=0;
                                    }
                                    tbDokter.setValueAt(jmlkonversi,baris,10);
                                    
                                    nilaibarang=0;
                                    try {
                                        nilaibarang=Double.parseDouble(tbDokter.getValueAt(baris,9).toString());
                                    } catch (Exception e) {
                                        nilaibarang=0;
                                    }

                                    hargakonversi=nilaibarang/jmlkonversi;
                                    tbDokter.setValueAt(Math.round(hargakonversi),baris,24);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("ralan")/100)),100),baris,12);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("kelas1")/100)),100),baris,13);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("kelas2")/100)),100),baris,14);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("kelas3")/100)),100),baris,15);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("utama")/100)),100),baris,16);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("vip")/100)),100),baris,17);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("vvip")/100)),100),baris,18);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("beliluar")/100)),100),baris,19);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("jualbebas")/100)),100),baris,20);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("karyawan")/100)),100),baris,21);
                                }
                            }else{                                
                                JOptionPane.showMessageDialog(null,"Pengaturan harga umum masih kosong..!!");
                                TCari.requestFocus();
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs!=null){
                                rs.close();
                            }
                        }   break;
                    case "Per Barang":
                        try{
                            rs=koneksi.prepareStatement("select * from setpenjualanperbarang where kode_brng='"+tbDokter.getValueAt(baris,2).toString()+"'").executeQuery();
                            if(rs.next()){
                                if(tbDokter.getValueAt(baris,1).toString().equals(tbDokter.getValueAt(baris,4).toString())){
                                    try {
                                        jmlkonversi=Double.parseDouble(tbDokter.getValueAt(baris,0).toString());
                                    } catch (Exception e) {
                                        jmlkonversi=0;
                                    }
                                    tbDokter.setValueAt(jmlkonversi,baris,10);
                                    
                                    nilaibarang=0;
                                    try {
                                        nilaibarang=Double.parseDouble(tbDokter.getValueAt(baris,8).toString());
                                    } catch (Exception e) {
                                        nilaibarang=0;
                                    }

                                    tbDokter.setValueAt(Math.round(nilaibarang),baris,24);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("ralan")/100)),100),baris,12);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("kelas1")/100)),100),baris,13);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("kelas2")/100)),100),baris,14);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("kelas3")/100)),100),baris,15);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("utama")/100)),100),baris,16);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("vip")/100)),100),baris,17);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("vvip")/100)),100),baris,18);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("beliluar")/100)),100),baris,19);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("jualbebas")/100)),100),baris,20);
                                    tbDokter.setValueAt(Valid.roundUp(nilaibarang+(nilaibarang*(rs.getDouble("karyawan")/100)),100),baris,21);
                                }else{
                                    try {
                                        jmlkonversi=Double.parseDouble(tbDokter.getValueAt(baris,0).toString())*(Double.parseDouble(tbDokter.getValueAt(baris,22).toString())/Double.parseDouble(tbDokter.getValueAt(baris,23).toString()));
                                    } catch (Exception e) {
                                        jmlkonversi=0;
                                    }
                                    tbDokter.setValueAt(jmlkonversi,baris,10);
                                    
                                    nilaibarang=0;
                                    try {
                                        nilaibarang=Double.parseDouble(tbDokter.getValueAt(baris,9).toString());
                                    } catch (Exception e) {
                                        nilaibarang=0;
                                    }

                                    hargakonversi=nilaibarang/jmlkonversi;
                                    tbDokter.setValueAt(Math.round(hargakonversi),baris,24);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("ralan")/100)),100),baris,12);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("kelas1")/100)),100),baris,13);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("kelas2")/100)),100),baris,14);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("kelas3")/100)),100),baris,15);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("utama")/100)),100),baris,16);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("vip")/100)),100),baris,17);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("vvip")/100)),100),baris,18);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("beliluar")/100)),100),baris,19);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("jualbebas")/100)),100),baris,20);
                                    tbDokter.setValueAt(Valid.roundUp(hargakonversi+(hargakonversi*(rs.getDouble("karyawan")/100)),100),baris,21);
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Pengaturan untuk obat/alkes/bhp ini tidak ditemukan..!!");
                                TCari.requestFocus();
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs!=null){
                                rs.close();
                            }
                        }   break;
                    default:                                 
                        JOptionPane.showMessageDialog(null,"Pengaturan untuk obat/alkes/bhp belum disetting..!!");
                        break;                    
                }                      
            }                                 
        } catch (Exception e) {
            tbDokter.setValueAt("",baris,0);
        }
    }
}

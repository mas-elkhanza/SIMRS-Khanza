package inventaris;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

public class InventarisPemesanan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private InventarisCariPemesanan form=new InventarisCariPemesanan(null,false);
    private double ttl=0,y=0,w=0,ttldisk=0,sbttl=0,ppn=0,meterai=0;
    private int jml=0,i=0,row=0,index=0;
    private String[] kodebarang,namabarang,produsen,merk,kategori,jenis;
    private double[] harga,jumlah,subtotal,diskon,besardiskon,jmltotal;
    private WarnaTable2 warna=new WarnaTable2();
    public boolean tampikan=true;
    private boolean sukses=true;
    private File file;
    private FileWriter fileWriter;
    private String iyem,akunaset="",Kontra_Penerimaan_AsetInventaris=Sequel.cariIsi("select Kontra_Penerimaan_AsetInventaris from set_akun");
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public InventarisPemesanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] judul={"Jml","Kode Barang","Nama Barang","Produsen","Merk","Kategori","Jenis","Hrg.Beli(Rp)","Subtotal Beli(Rp)","Disk(%)","Diskon(Rp)","Ttl.Beli"};
        tabMode=new DefaultTableModel(null,judul){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==7)||(colIndex==9)||(colIndex==10)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class 
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
                column.setPreferredWidth(38);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(95);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        NoFaktur.setDocument(new batasInput((byte)20).getKata(NoFaktur));
        NoOrder.setDocument(new batasInput((byte)20).getKata(NoOrder));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg)); 
        Meterai.setDocument(new batasInput((byte)15).getOnlyAngka(Meterai));        
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
        
        form.suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("InventarisPemesanan")){
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
                if(akses.getform().equals("InventarisPemesanan")){
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
                if(akses.getform().equals("InventarisPemesanan")){
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
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnTambah = new widget.Button();
        label12 = new widget.Label();
        LSubtotal = new widget.Label();
        label9 = new widget.Label();
        LPotongan = new widget.Label();
        label20 = new widget.Label();
        LTotal2 = new widget.Label();
        label17 = new widget.Label();
        tppn = new widget.TextBox();
        LPpn = new widget.Label();
        label24 = new widget.Label();
        Meterai = new widget.TextBox();
        label19 = new widget.Label();
        LTagiha = new widget.Label();
        label21 = new widget.Label();
        BtnAll = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label11 = new widget.Label();
        TglPesan = new widget.Tanggal();
        label22 = new widget.Label();
        TglFaktur = new widget.Tanggal();
        TglTempo = new widget.Tanggal();
        label18 = new widget.Label();
        NoOrder = new widget.TextBox();
        label23 = new widget.Label();
        jLabel11 = new widget.Label();
        AkunAset = new widget.ComboBox();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Penerimaan Barang Aset/Inventaris ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        tbDokter.setToolTipText("Masukkan jumlah pengajuan di ujung paling kiri pada warna biru kemudian geser kanan");
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
        TCari.setBounds(190, 65, 260, 23);

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
        BtnCari1.setBounds(452, 65, 28, 23);

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
        BtnCari.setBounds(560, 62, 100, 30);

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
        BtnKeluar.setBounds(670, 62, 100, 30);

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
        BtnTambah.setBounds(510, 65, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label12.setText("Total 1 :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label12);
        label12.setBounds(10, 0, 60, 30);

        LSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LSubtotal.setText("0");
        LSubtotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LSubtotal.setName("LSubtotal"); // NOI18N
        LSubtotal.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LSubtotal);
        LSubtotal.setBounds(10, 20, 100, 30);

        label9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label9.setText("Potongan :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label9);
        label9.setBounds(120, 0, 90, 30);

        LPotongan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPotongan.setText("0");
        LPotongan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPotongan.setName("LPotongan"); // NOI18N
        LPotongan.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LPotongan);
        LPotongan.setBounds(120, 20, 100, 30);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label20.setText("Total 2 :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label20);
        label20.setBounds(230, 0, 90, 30);

        LTotal2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal2.setText("0");
        LTotal2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal2.setName("LTotal2"); // NOI18N
        LTotal2.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LTotal2);
        LTotal2.setBounds(230, 20, 100, 30);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("PPN :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label17);
        label17.setBounds(340, 0, 40, 30);

        tppn.setText("11");
        tppn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tppn.setName("tppn"); // NOI18N
        tppn.setPreferredSize(new java.awt.Dimension(80, 23));
        tppn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tppnKeyPressed(evt);
            }
        });
        panelisi1.add(tppn);
        tppn.setBounds(340, 26, 45, 23);

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LPpn);
        LPpn.setBounds(410, 20, 100, 30);

        label24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label24.setText("Biaya Tambahan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label24);
        label24.setBounds(520, 0, 90, 30);

        Meterai.setText("0");
        Meterai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Meterai.setName("Meterai"); // NOI18N
        Meterai.setPreferredSize(new java.awt.Dimension(80, 23));
        Meterai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MeteraiKeyPressed(evt);
            }
        });
        panelisi1.add(Meterai);
        Meterai.setBounds(520, 26, 100, 23);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("Jumlah Tagihan :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label19);
        label19.setBounds(630, 0, 130, 30);

        LTagiha.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTagiha.setText("0");
        LTagiha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTagiha.setName("LTagiha"); // NOI18N
        LTagiha.setPreferredSize(new java.awt.Dimension(110, 30));
        panelisi1.add(LTagiha);
        LTagiha.setBounds(630, 20, 150, 30);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("%");
        label21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label21);
        label21.setBounds(387, 26, 30, 23);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
        BtnAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        panelisi1.add(BtnAll);
        BtnAll.setBounds(482, 65, 28, 23);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label15.setText("No.Faktur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(79, 10, 210, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(345, 40, 100, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(449, 10, 80, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(345, 10, 100, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(449, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(531, 10, 200, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(531, 40, 200, 23);

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

        label11.setText("Tgl.Datang :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 75, 23);

        TglPesan.setDisplayFormat("dd-MM-yyyy");
        TglPesan.setName("TglPesan"); // NOI18N
        TglPesan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglPesanItemStateChanged(evt);
            }
        });
        TglPesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPesanKeyPressed(evt);
            }
        });
        panelisi3.add(TglPesan);
        TglPesan.setBounds(78, 40, 95, 23);

        label22.setText("Tgl.Faktur :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label22);
        label22.setBounds(180, 40, 60, 23);

        TglFaktur.setDisplayFormat("dd-MM-yyyy");
        TglFaktur.setName("TglFaktur"); // NOI18N
        TglFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglFakturKeyPressed(evt);
            }
        });
        panelisi3.add(TglFaktur);
        TglFaktur.setBounds(243, 40, 95, 23);

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

        jLabel11.setText("Akun Jenis :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelisi3.add(jLabel11);
        jLabel11.setBounds(345, 70, 100, 23);

        AkunAset.setName("AkunAset"); // NOI18N
        AkunAset.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AkunAsetItemStateChanged(evt);
            }
        });
        panelisi3.add(AkunAset);
        AkunAset.setBounds(449, 70, 313, 23);

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
        if(NoFaktur.getText().trim().equals("")){
            Valid.textKosong(NoFaktur,"No.Faktur");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Supplier");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(NoOrder.getText().trim().equals("")){
            Valid.textKosong(NoOrder,"No.Order");
        }else if(Meterai.getText().trim().equals("")){
            Valid.textKosong(Meterai,"Biaya Tambahan");
        }else if(AkunAset.getSelectedItem().toString().trim().equals("")){
            Valid.textKosong(AkunAset,"Akun Aset");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan pembelian...!!!!");
            tbDokter.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                
                akunaset="";
                try {
                    myObj = new FileReader("./cache/akunaset.iyem");
                    root = mapper.readTree(myObj);
                    response = root.path("akunaset");
                    if(response.isArray()){
                       for(JsonNode list:response){
                           if(list.path("NamaAkun").asText().equals(AkunAset.getSelectedItem().toString())){
                                akunaset=list.path("KodeRek").asText();  
                           }
                       }
                    }
                    myObj.close();
                } catch (Exception e) {
                    sukses=false;
                } 
                
                if(Sequel.menyimpantf2("inventaris_pemesanan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Faktur",15,new String[]{
                    NoFaktur.getText(),NoOrder.getText(),kdsup.getText(),kdptg.getText(),Valid.SetTgl(TglPesan.getSelectedItem()+""),
                    Valid.SetTgl(TglFaktur.getSelectedItem()+""),Valid.SetTgl(TglTempo.getSelectedItem()+""),""+sbttl,""+ttldisk,""+ttl,
                    ""+ppn,""+meterai,""+(ttl+ppn+meterai),"Belum Dibayar",akunaset
                })==true){
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){  
                        if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                            if(Sequel.menyimpantf2("inventaris_detail_pesan","?,?,?,?,?,?,?,?","Transaksi Penerimaan",8,new String[]{
                                NoFaktur.getText(),tbDokter.getValueAt(i,1).toString(),tbDokter.getValueAt(i,0).toString(),
                                tbDokter.getValueAt(i,7).toString(),tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,9).toString(),
                                tbDokter.getValueAt(i,10).toString(),tbDokter.getValueAt(i,11).toString()
                            })==false){
                                sukses=false;
                            }                                  
                        }                
                    }
                }else{
                    sukses=false;
                }                        
                   
                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");
                    Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{akunaset,"PENERIMAAN ASET INVENTARIS",""+(ttl+ppn+meterai),"0"});
                    Sequel.menyimpan("tampjurnal","?,?,?,?",4,new String[]{Kontra_Penerimaan_AsetInventaris,"HUTANG BARANG ASET/INVENTARIS","0",""+(ttl+ppn+meterai)}); 
                    sukses=jur.simpanJurnal(NoFaktur.getText(),"U","PENERIMAAN BARANG ASET/INVENTARIS"+", OLEH "+akses.getkode());
                }
                
                if(sukses==true){
                    Sequel.Commit();
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){ 
                        tbDokter.setValueAt("",i,0);
                        tbDokter.setValueAt(0,i,7);
                        tbDokter.setValueAt(0,i,8);
                        tbDokter.setValueAt(0,i,9);
                        tbDokter.setValueAt(0,i,10);
                        tbDokter.setValueAt(0,i,11);
                    }
                    Meterai.setText("0");
                    getData();
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
                tbDokter.setValueAt(false,i,4);
                tbDokter.setValueAt(0,i,6);
                tbDokter.setValueAt(0,i,7);
                tbDokter.setValueAt(0,i,8);
                tbDokter.setValueAt(0,i,9);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnSimpan, kdsup);
}//GEN-LAST:event_NoFakturKeyPressed

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

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());          
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            BtnSimpan.requestFocus();  
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        akses.setform("InventarisPemesanan");
        form.suplier.emptTeks();
        form.suplier.isCek();
        form.suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.suplier.setLocationRelativeTo(internalFrame1);
        form.suplier.setAlwaysOnTop(false);
        form.suplier.setVisible(true);
}//GEN-LAST:event_btnSuplierActionPerformed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("InventarisPemesanan");
        form.petugas.emptTeks();
        form.petugas.isCek();
        form.petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.petugas.setLocationRelativeTo(internalFrame1);
        form.petugas.setAlwaysOnTop(false);
        form.petugas.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(tampikan==true){
            tampilAkun();
            tampil();
        }
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        akses.setform("InventarisPemesanan");
        InventarisBarang barang=new InventarisBarang(null,false);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void tppnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tppnKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getData();
        }
    }//GEN-LAST:event_tppnKeyPressed

    private void MeteraiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MeteraiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            getData();
        }
    }//GEN-LAST:event_MeteraiKeyPressed

    private void TglPesanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglPesanItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TglPesanItemStateChanged

    private void TglPesanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPesanKeyPressed
        Valid.pindah(evt,NoFaktur,kdsup);
    }//GEN-LAST:event_TglPesanKeyPressed

    private void TglFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglFakturKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglFakturKeyPressed

    private void TglTempoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTempoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTempoKeyPressed

    private void NoOrderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoOrderKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoOrderKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
        LSubtotal.setText("0");
        LPotongan.setText("0");
        LTotal2.setText("0");
        LPpn.setText("0");
        LTagiha.setText("0");
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void AkunAsetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AkunAsetItemStateChanged
        Valid.tabelKosong(tabMode);
        tampil();
        LSubtotal.setText("0");
        LPotongan.setText("0");
        LTotal2.setText("0");
        LPpn.setText("0");
        LTagiha.setText("0");
    }//GEN-LAST:event_AkunAsetItemStateChanged

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tbDokter.getRowCount()!=0){
            try {
                if((tbDokter.getSelectedColumn()==1)||(tbDokter.getSelectedColumn()==7)||(tbDokter.getSelectedColumn()==8)||(tbDokter.getSelectedColumn()==10)){
                    getData();
                }else if(tbDokter.getSelectedColumn()==9){
                    tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString())*
                        (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString())/100),tbDokter.getSelectedRow(),10);
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
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    if((tbDokter.getSelectedColumn()==1)||(tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==7)||(tbDokter.getSelectedColumn()==8)||(tbDokter.getSelectedColumn()==10)){
                        getData();
                        TCari.setText("");
                        TCari.requestFocus();
                    }else if(tbDokter.getSelectedColumn()==9){
                        if(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString())>0){
                            tbDokter.setValueAt((Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString())*
                                (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString())/100)),tbDokter.getSelectedRow(),10);
                    }
                    getData();
                }
            } catch (java.lang.NullPointerException e) {
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
            i=tbDokter.getSelectedRow();
            if(i!= -1){
                tbDokter.setValueAt("", i,0);
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
            TCari.setText("");
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
            if((tbDokter.getSelectedColumn()==1)||(tbDokter.getSelectedColumn()==7)||(tbDokter.getSelectedColumn()==8)){
                getData();
            }else if((tbDokter.getSelectedColumn()==9)||(tbDokter.getSelectedColumn()==10)){
                if(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString())>0){
                    tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString())*
                        (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString())/100),tbDokter.getSelectedRow(),10);
                }
                getData();
            }
        }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventarisPemesanan dialog = new InventarisPemesanan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AkunAset;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Kd2;
    private widget.Label LPotongan;
    private widget.Label LPpn;
    private widget.Label LSubtotal;
    private widget.Label LTagiha;
    private widget.Label LTotal2;
    private widget.TextBox Meterai;
    private widget.TextBox NoFaktur;
    private widget.TextBox NoOrder;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal TglFaktur;
    private widget.Tanggal TglPesan;
    private widget.Tanggal TglTempo;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label9;
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
        produsen=new String[jml];
        merk=new String[jml];
        kategori=new String[jml];
        jenis=new String[jml];
        harga=new double[jml];
        jumlah=new double[jml];
        subtotal=new double[jml];
        diskon=new double[jml];
        besardiskon=new double[jml];
        jmltotal=new double[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbDokter.getValueAt(i,0).toString())>0){
                    jumlah[index]=Double.parseDouble(tbDokter.getValueAt(i,0).toString());
                    kodebarang[index]=tbDokter.getValueAt(i,1).toString();
                    namabarang[index]=tbDokter.getValueAt(i,2).toString();
                    produsen[index]=tbDokter.getValueAt(i,3).toString();
                    merk[index]=tbDokter.getValueAt(i,4).toString();
                    kategori[index]=tbDokter.getValueAt(i,5).toString();
                    jenis[index]=tbDokter.getValueAt(i,6).toString();
                    harga[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                    subtotal[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                    diskon[index]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                    besardiskon[index]=Double.parseDouble(tbDokter.getValueAt(i,10).toString());
                    jmltotal[index]=Double.parseDouble(tbDokter.getValueAt(i,11).toString());
                    index++;
                }
            } catch (Exception e) {
            }
        }
        
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],produsen[i],merk[i],kategori[i],jenis[i],harga[i],subtotal[i],diskon[i],besardiskon[i],jmltotal[i]});
        }
        
        try{
            try {
                myObj = new FileReader("./cache/akunaset.iyem");
                root = mapper.readTree(myObj);
                response = root.path("akunaset");
                if(response.isArray()){
                   for(JsonNode list:response){
                       if(list.path("NamaAkun").asText().equals(AkunAset.getSelectedItem().toString())){
                           ps=koneksi.prepareStatement(
                                    "select inventaris_barang.kode_barang,inventaris_barang.nama_barang,inventaris_produsen.nama_produsen,"+
                                    "inventaris_merk.nama_merk,inventaris_kategori.nama_kategori,inventaris_jenis.nama_jenis "+
                                    "from inventaris_barang inner join inventaris_produsen on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen "+
                                    "inner join inventaris_jenis on inventaris_barang.id_jenis=inventaris_jenis.id_jenis "+
                                    "inner join inventaris_kategori on inventaris_barang.id_kategori=inventaris_kategori.id_kategori "+
                                    "inner join inventaris_merk on inventaris_barang.id_merk=inventaris_merk.id_merk "+
                                    "inner join akun_aset_inventaris on akun_aset_inventaris.id_jenis=inventaris_jenis.id_jenis "+
                                    "where akun_aset_inventaris.kd_rek='"+list.path("KodeRek").asText()+"' "+
                                    (TCari.getText().trim().equals("")?"":" and (inventaris_barang.kode_barang like '%"+TCari.getText().trim()+"%' "+
                                    "or inventaris_barang.nama_barang like '%"+TCari.getText().trim()+"%' "+
                                    "or inventaris_produsen.nama_produsen like '%"+TCari.getText().trim()+"%' "+
                                    "or inventaris_merk.nama_merk like '%"+TCari.getText().trim()+"%' "+
                                    "or inventaris_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' "+
                                    "or inventaris_jenis.nama_jenis like '%"+TCari.getText().trim()+"%') ")+
                                    "order by inventaris_barang.kode_barang");
                            try{   
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    tabMode.addRow(new Object[]{
                                        "",rs.getString("kode_barang"),rs.getString("nama_barang"),rs.getString("nama_produsen"),
                                        rs.getString("nama_merk"),rs.getString("nama_kategori"),rs.getString("nama_jenis"),0,0,0,0,0
                                    });
                                }   
                            }catch(Exception e){
                                System.out.println(e);
                            }finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }         
                       }
                   }
                }
                myObj.close();
            } catch (Exception e) {
                sukses=false;
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
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*Double.parseDouble(tbDokter.getValueAt(row,7).toString()), row,8);                
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,8).toString())-Double.parseDouble(tbDokter.getValueAt(row,10).toString()), row,11);           
                    }
                } catch (Exception e) {
                    tbDokter.setValueAt("",row,0);
                    tbDokter.setValueAt(0,row,8);   
                    tbDokter.setValueAt(0,row,9);   
                    tbDokter.setValueAt(0,row,10);                
                    tbDokter.setValueAt(0,row,11);    
                } 
            }else{
                tbDokter.setValueAt(0,row,8);   
                tbDokter.setValueAt(0,row,9);   
                tbDokter.setValueAt(0,row,10);                
                tbDokter.setValueAt(0,row,11);   
            }               
        }
        
        ttl=0;sbttl=0;ttldisk=0;
        y=0;w=0;
        meterai=0;
        if(!Meterai.getText().equals("")){
            meterai=Double.parseDouble(Meterai.getText());
        }
        
        jml=tbDokter.getRowCount();
        for(i=0;i<jml;i++){                 
            try {
                w=Double.parseDouble(tbDokter.getValueAt(i,8).toString());                
            }catch (Exception e) {
                w=0;                
            }
            sbttl=sbttl+w;                
            try {
                y=Double.parseDouble(tbDokter.getValueAt(i,10).toString());                
            }catch (Exception e) {
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
            LTagiha.setText(Valid.SetAngka(ttl+ppn+meterai));
        }
    }
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        tppn.setText("11");
        Meterai.setText("0");
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            btnPetugas.setEnabled(false);
            kdptg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getpenerimaan_aset_inventaris());
            BtnTambah.setEnabled(akses.getinventaris_koleksi());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }        
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_faktur,3),signed)),0) from inventaris_pemesanan where tgl_pesan='"+Valid.SetTgl(TglPesan.getSelectedItem()+"")+"'","PAI"+TglPesan.getSelectedItem().toString().substring(6,10)+TglPesan.getSelectedItem().toString().substring(3,5)+TglPesan.getSelectedItem().toString().substring(0,2),3,NoFaktur); 
    }

    public void tampil(String noorder) {
        /*NoOrder.setText(noorder);
        kdsup.setText(Sequel.cariIsi("select kode_suplier from surat_pemesanan_non_medis where no_pemesanan=?",noorder));
        nmsup.setText(Sequel.cariIsi("select nama_suplier from ipsrssuplier where kode_suplier=?",kdsup.getText()));
        meterai=Sequel.cariIsiAngka("select meterai from surat_pemesanan_non_medis where no_pemesanan=?",noorder);
        ppn=Sequel.cariIsiAngka("select ppn from surat_pemesanan_non_medis where no_pemesanan=?",noorder);
        Meterai.setText(Valid.SetAngka2(meterai));
        try{
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                "select detail_surat_pemesanan_non_medis.kode_brng,concat(ipsrsbarang.nama_brng,' (',ipsrsbarang.jenis,')') as nama_brng, "+
                "detail_surat_pemesanan_non_medis.kode_sat,detail_surat_pemesanan_non_medis.jumlah,detail_surat_pemesanan_non_medis.h_pesan, "+
                "detail_surat_pemesanan_non_medis.subtotal,detail_surat_pemesanan_non_medis.dis,detail_surat_pemesanan_non_medis.besardis,detail_surat_pemesanan_non_medis.total "+
                "from detail_surat_pemesanan_non_medis inner join ipsrsbarang "+
                " on detail_surat_pemesanan_non_medis.kode_brng=ipsrsbarang.kode_brng "+
                " where detail_surat_pemesanan_non_medis.no_pemesanan=? order by ipsrsbarang.nama_brng");
            try {
                ps.setString(1,noorder);
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("jumlah"),rs.getString("kode_brng"),rs.getString("nama_brng"),
                        rs.getString("kode_sat"),true,rs.getDouble("h_pesan"),rs.getDouble("subtotal"),
                        rs.getDouble("dis"),rs.getDouble("besardis"),rs.getDouble("total"),
                    });
                } 
                getData();
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
        }*/
    }
    
    private void tampilAkun() {         
        try{      
             file=new File("./cache/akunaset.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             iyem="";
             ps=koneksi.prepareStatement("select rekening.nm_rek,akun_aset_inventaris.kd_rek from akun_aset_inventaris inner join rekening on akun_aset_inventaris.kd_rek=rekening.kd_rek group by rekening.nm_rek order by rekening.nm_rek");
             try{
                 rs=ps.executeQuery();
                 AkunAset.removeAllItems();
                 while(rs.next()){    
                     AkunAset.addItem(rs.getString(1).replaceAll("\"",""));
                     iyem=iyem+"{\"NamaAkun\":\""+rs.getString(1).replaceAll("\"","")+"\",\"KodeRek\":\""+rs.getString(2)+"\"},";
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

             fileWriter.write("{\"akunaset\":["+iyem.substring(0,iyem.length()-1)+"]}");
             fileWriter.flush();
             fileWriter.close();
             iyem=null;
        } catch (Exception e) {
            if(e.toString().contains("begin")){
                System.out.println("Notifikasi Akun Aset : Data tidak ditemukan..!!");
            }else{
                System.out.println("Notifikasi Akun Aset : "+e);
            }
        }
    }
 
}

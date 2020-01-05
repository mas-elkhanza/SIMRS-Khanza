package inventory;


import fungsi.WarnaTable2;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import kepegawaian.DlgCariPegawai;

public class DlgSuratPemesanan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private WarnaTable2 warna=new WarnaTable2();
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private DlgSuplier suplier=new DlgSuplier(null,false);
    private DlgCariSuratPemesanan form=new DlgCariSuratPemesanan(null,false);
    private double meterai=0,ttl=0,y=0,w=0,ttldisk=0,sbttl=0,ppn=0,jmlkonversi=0;
    private int jml=0,i=0,row=0,index=0,pilihan=1;
    private String[] kodebarang,namabarang,satuan,satuanbeli;
    private double[] harga,jumlah,subtotal,diskon,besardiskon,jmltotal,jmlstok,isi,isibesar;
    public boolean tampilkan=true;
    private DlgBarang barang=new DlgBarang(null,false);    
    private boolean sukses=true;    
    private DlgCariDataKonversi datakonversi=new DlgCariDataKonversi(null,false);

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgSuratPemesanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Jml","Satuan Beli","Kode Barang","Nama Barang","Satuan","Harga(Rp)","Subtotal(Rp)",
                "Disk(%)","Diskon(Rp)","Total","Jml.Stok","Isi","Isi Besar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
               boolean a = false;
               if ((colIndex==0)||(colIndex==5)||(colIndex==7)||(colIndex==8)) {
                   a=true;
               }
               return a;
             }
              
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class ,java.lang.Double.class ,java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(42);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(190);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(85);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        NoPemesanan.setDocument(new batasInput((byte)20).getKata(NoPemesanan));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)20).getKata(kdptg));        
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
                if(suplier.getTable().getSelectedRow()!= -1){                   
                    kdsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),0).toString());
                    nmsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
                } 
                kdsup.requestFocus();
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
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    suplier.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){  
                    if(pilihan==1){
                        kdptg.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                        nmptg.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                        Departemen.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),5).toString());
                        kdptg.requestFocus();
                    }else if(pilihan==2){
                        Apoteker.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());                        
                    }else if(pilihan==3){
                        KabidKeu.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());                        
                    }                        
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
        
        pegawai.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pegawai.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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
                        tbDokter.setValueAt(Double.parseDouble(datakonversi.getTable().getValueAt(datakonversi.getTable().getSelectedRow(),3).toString()),tbDokter.getSelectedRow(),11);
                    }catch(Exception er){
                        JOptionPane.showMessageDialog(null,"Gagal mengambil nilai konversi, nilai barang satuan kecil dianggap bernilai 1..!!");
                        tbDokter.setValueAt(1,tbDokter.getSelectedRow(),11);
                    }
                    
                    try{
                        tbDokter.setValueAt(Double.parseDouble(datakonversi.getTable().getValueAt(datakonversi.getTable().getSelectedRow(),0).toString()),tbDokter.getSelectedRow(),12);
                    }catch(Exception er){
                        JOptionPane.showMessageDialog(null,"Gagal mengambil nilai konversi, nilai barang satuan besar dianggap bernilai 1..!!");
                        tbDokter.setValueAt(1,tbDokter.getSelectedRow(),12);
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
        
        DlgCetak.setSize(550,145);
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
        ppStok1 = new javax.swing.JMenuItem();
        DlgCetak = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        panelBiasa4 = new widget.PanelBiasa();
        BtnPrint5 = new widget.Button();
        BtnKeluar4 = new widget.Button();
        BtnSeek5 = new widget.Button();
        KabidKeu = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        Apoteker = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoPemesanan = new widget.TextBox();
        label11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        label16 = new widget.Label();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label14 = new widget.Label();
        Departemen = new widget.TextBox();
        panelisi1 = new widget.panelisi();
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
        Meterai = new widget.TextBox();
        label24 = new widget.Label();
        label19 = new widget.Label();
        LTagiha = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnPrint = new widget.Button();

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

        DlgCetak.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgCetak.setName("DlgCetak"); // NOI18N
        DlgCetak.setUndecorated(true);
        DlgCetak.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Pemesanan Obat/Alkes/BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa4.setName("panelBiasa4"); // NOI18N
        panelBiasa4.setLayout(null);

        BtnPrint5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint5.setMnemonic('T');
        BtnPrint5.setText("Cetak");
        BtnPrint5.setToolTipText("Alt+T");
        BtnPrint5.setName("BtnPrint5"); // NOI18N
        BtnPrint5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnPrint5);
        BtnPrint5.setBounds(10, 80, 100, 30);

        BtnKeluar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar4.setMnemonic('K');
        BtnKeluar4.setText("Tutup");
        BtnKeluar4.setToolTipText("Alt+K");
        BtnKeluar4.setName("BtnKeluar4"); // NOI18N
        BtnKeluar4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar4ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnKeluar4);
        BtnKeluar4.setBounds(430, 80, 100, 30);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnSeek5);
        BtnSeek5.setBounds(492, 40, 28, 23);

        KabidKeu.setEditable(false);
        KabidKeu.setName("KabidKeu"); // NOI18N
        KabidKeu.setPreferredSize(new java.awt.Dimension(300, 23));
        panelBiasa4.add(KabidKeu);
        KabidKeu.setBounds(150, 40, 340, 23);

        jLabel24.setText("Kepala Bagian Keuangan :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(60, 23));
        panelBiasa4.add(jLabel24);
        jLabel24.setBounds(7, 40, 140, 23);

        jLabel25.setText("Mengetahui Apoteker :");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(60, 23));
        panelBiasa4.add(jLabel25);
        jLabel25.setBounds(7, 10, 140, 23);

        Apoteker.setEditable(false);
        Apoteker.setName("Apoteker"); // NOI18N
        Apoteker.setPreferredSize(new java.awt.Dimension(300, 23));
        panelBiasa4.add(Apoteker);
        Apoteker.setBounds(150, 10, 340, 23);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('6');
        BtnSeek6.setToolTipText("ALt+6");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnSeek6);
        BtnSeek6.setBounds(492, 10, 28, 23);

        internalFrame5.add(panelBiasa4, java.awt.BorderLayout.CENTER);

        DlgCetak.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Surat Pemesanan Obat/Alkes/BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Pemesanan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 92, 23);

        NoPemesanan.setName("NoPemesanan"); // NOI18N
        NoPemesanan.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPemesanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPemesananKeyPressed(evt);
            }
        });
        panelisi3.add(NoPemesanan);
        NoPemesanan.setBounds(95, 10, 120, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(label11);
        label11.setBounds(220, 10, 55, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalItemStateChanged(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal);
        Tanggal.setBounds(279, 10, 90, 23);

        label13.setText("Pegawai :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(385, 10, 75, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(95, 40, 70, 23);

        label16.setText("Suplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(0, 40, 92, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(463, 10, 76, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(167, 40, 170, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(541, 10, 190, 23);

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
        btnSuplier.setBounds(340, 40, 28, 23);

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
        btnPetugas.setBounds(734, 10, 28, 23);

        label14.setText("Departemen :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(385, 40, 75, 23);

        Departemen.setEditable(false);
        Departemen.setName("Departemen"); // NOI18N
        Departemen.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(Departemen);
        Departemen.setBounds(463, 40, 299, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 107));
        panelisi1.setLayout(null);

        label10.setText("Keyword :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label10);
        label10.setBounds(210, 65, 75, 23);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);
        TCari.setBounds(289, 65, 200, 23);

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
        BtnCari1.setBounds(492, 65, 28, 23);

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
        BtnCari.setBounds(565, 62, 100, 30);

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
        BtnTambah.setBounds(521, 65, 28, 23);

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

        tppn.setText("10");
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

        label24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label24.setText("Meterai :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(60, 30));
        panelisi1.add(label24);
        label24.setBounds(520, 0, 90, 30);

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
        BtnSimpan.setBounds(5, 62, 100, 30);

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
        BtnPrint.setBounds(110, 62, 100, 30);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for(i=0;i<tbDokter.getRowCount();i++){ 
        tbDokter.setValueAt("",i,0);
        tbDokter.setValueAt(0,i,6);
        tbDokter.setValueAt(0,i,7);
        tbDokter.setValueAt(0,i,8);
        tbDokter.setValueAt(0,i,9);
        tbDokter.setValueAt(0,i,10);
    }
    Meterai.setText("0");
    getData();
}//GEN-LAST:event_ppBersihkanActionPerformed

private void NoPemesananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPemesananKeyPressed
        Valid.pindah(evt, BtnSimpan, kdsup);
}//GEN-LAST:event_NoPemesananKeyPressed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NoPemesanan,kdsup);
}//GEN-LAST:event_TanggalKeyPressed

private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            NoPemesanan.requestFocus();
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
            BtnSimpan.requestFocus();  
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        suplier.isCek();
        suplier.emptTeks();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
}//GEN-LAST:event_btnSuplierActionPerformed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        pilihan=1;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(tampilkan==true){
            tampil();
        }            
    }//GEN-LAST:event_formWindowOpened

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

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        DlgCetak.dispose();
        pegawai.dispose();
        suplier.dispose();
        barang.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnSimpan,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        } 
    }//GEN-LAST:event_TanggalItemStateChanged

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tbDokter.getRowCount()!=0){
            try {
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==5)||(tbDokter.getSelectedColumn()==6)||(tbDokter.getSelectedColumn()==8)){                       
                        getData();  
                   }else if(tbDokter.getSelectedColumn()==7){
                       try {
                           tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())/100),tbDokter.getSelectedRow(),8);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
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
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                  
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==3)||(tbDokter.getSelectedColumn()==6)||(tbDokter.getSelectedColumn()==9)){                       
                        getData();  
                        TCari.setText("");
                        TCari.requestFocus();                                                
                   }else if(tbDokter.getSelectedColumn()==7){
                       try {
                           tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())/100),tbDokter.getSelectedRow(),8);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
                       }
                       
                       getData();
                   }else if((tbDokter.getSelectedColumn()==9)||(tbDokter.getSelectedColumn()==10)){                       
                       TCari.setText("");
                       TCari.requestFocus();
                   }
                } catch (Exception e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    if(tbDokter.getSelectedColumn()==0){
                        tbDokter.setValueAt("", tbDokter.getSelectedRow(),0);
                    }else if(tbDokter.getSelectedColumn()==5){
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),5);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),6);
                    }else if(tbDokter.getSelectedColumn()==7){
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),7);
                        tbDokter.setValueAt(0, tbDokter.getSelectedRow(),8);
                    }
                } catch (Exception e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if((evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_LEFT)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                   if((tbDokter.getSelectedColumn()==2)||(tbDokter.getSelectedColumn()==3)||(tbDokter.getSelectedColumn()==6)||(tbDokter.getSelectedColumn()==9)){                                              
                       getData();  
                   }else if(tbDokter.getSelectedColumn()==7){
                       try {
                           tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString())*
                               (Double.parseDouble(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString())/100),tbDokter.getSelectedRow(),8);
                       } catch (Exception e) {
                           tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
                       }                       
                       getData();
                   }else if(tbDokter.getSelectedColumn()==1){
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
                        datakonversi.setSatuanKecil(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                        datakonversi.isCek();
                        datakonversi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight());
                        datakonversi.setLocationRelativeTo(internalFrame1);
                        datakonversi.setVisible(true);                        
                    }else{
                        JOptionPane.showMessageDialog(null,"Silahkan masukkan jumlah pemesanan terelebih dahulu..!!");
                    }
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoPemesanan.getText().trim().equals("")){
            Valid.textKosong(NoPemesanan,"No.Pemesanan");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Supplier");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(Meterai.getText().trim().equals("")){
            Valid.textKosong(Meterai,"Meterai");
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
                sukses=true;
                if(Sequel.menyimpantf2("surat_pemesanan_medis","?,?,?,?,?,?,?,?,?,?,?","No.Pemesanan",11,new String[]{
                    NoPemesanan.getText(),kdsup.getText(),kdptg.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),
                    ""+sbttl,""+ttldisk,""+ttl,
                    ""+ppn,""+meterai,""+(ttl+ppn+meterai),"Proses Pesan"
                })==true){
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){
                        try {
                            if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                                if(Sequel.menyimpantf2("detail_surat_pemesanan_medis","?,?,?,?,?,?,?,?,?,?","Transaksi Pemesanan",10,new String[]{
                                    NoPemesanan.getText(),
                                    tbDokter.getValueAt(i,2).toString(),
                                    tbDokter.getValueAt(i,1).toString(),
                                    tbDokter.getValueAt(i,0).toString(),
                                    tbDokter.getValueAt(i,5).toString(),
                                    tbDokter.getValueAt(i,6).toString(),
                                    tbDokter.getValueAt(i,7).toString(),
                                    tbDokter.getValueAt(i,8).toString(),
                                    tbDokter.getValueAt(i,9).toString(),
                                    tbDokter.getValueAt(i,10).toString()
                                })==false){
                                    sukses=false;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }
                    }
                }else{
                    sukses=false;
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan No.Pemesanan sudah ada sebelumnya...!!");
                }
                if(sukses==true){
                    Sequel.Commit();
                    jml=tbDokter.getRowCount();
                    for(i=0;i<jml;i++){
                        tbDokter.setValueAt("",i,0);
                        tbDokter.setValueAt(0,i,6);
                        tbDokter.setValueAt(0,i,7);
                        tbDokter.setValueAt(0,i,8);
                        tbDokter.setValueAt(0,i,9);
                        tbDokter.setValueAt(0,i,10);
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

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        DlgCetak.setLocationRelativeTo(internalFrame1);
        DlgCetak.setVisible(true);
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSimpan,TCari);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnPrint5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint5ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoPemesanan.getText().trim().equals("")){
            Valid.textKosong(NoPemesanan,"No.Pemesanan");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Supplier");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(Meterai.getText().trim().equals("")){
            Valid.textKosong(Meterai,"Meterai");
        }else if(Apoteker.getText().trim().equals("")){
            Valid.textKosong(Apoteker,"Apoteker");
        }else if(KabidKeu.getText().trim().equals("")){
            Valid.textKosong(KabidKeu,"Kepala Bagian Keuangan");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan pemesanan...!!!!");
            tbDokter.requestFocus();
        }else if(tbDokter.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                    Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,5).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()))+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString()))+"','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pemesanan"); 
                }                    
            }
            
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                param.put("suplier",nmsup.getText());  
                param.put("nomorpesan",NoPemesanan.getText());  
                param.put("total",LTotal2.getText());  
                param.put("ppn",LPpn.getText());  
                param.put("meterai",Valid.SetAngka(meterai));  
                param.put("tagihan",LTagiha.getText());  
                param.put("tanggal",akses.getkabupatenrs()+", "+Tanggal.getSelectedItem());  
                param.put("apoteker",Apoteker.getText()); 
                param.put("petugas",nmptg.getText()); 
                param.put("kabidkeu",KabidKeu.getText()); 
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptSuratPemesanan.jasper","report","::[ Transaksi Pemesanan Barang ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrint5ActionPerformed

    private void BtnKeluar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar4ActionPerformed
        DlgCetak.dispose();
    }//GEN-LAST:event_BtnKeluar4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        pilihan=3;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        pilihan=2;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

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
            DlgSuratPemesanan dialog = new DlgSuratPemesanan(new javax.swing.JFrame(), true);
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
    private widget.TextBox Apoteker;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar4;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint5;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Departemen;
    private javax.swing.JDialog DlgCetak;
    private widget.TextBox KabidKeu;
    private widget.Label LPotongan;
    private widget.Label LPpn;
    private widget.Label LSubtotal;
    private widget.Label LTagiha;
    private widget.Label LTotal2;
    private widget.TextBox Meterai;
    private widget.TextBox NoPemesanan;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
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
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.PanelBiasa panelBiasa4;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok1;
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
        
        kodebarang=null;
        namabarang=null;
        satuan=null;
        satuanbeli=null;
        harga=null;
        jumlah=null;
        subtotal=null;
        diskon=null;
        besardiskon=null;
        jmltotal=null;
        jmlstok=null;
        
        kodebarang=new String[jml];
        namabarang=new String[jml];
        satuan=new String[jml];
        satuanbeli=new String[jml];
        harga=new double[jml];
        jumlah=new double[jml];
        subtotal=new double[jml];
        diskon=new double[jml];
        besardiskon=new double[jml];
        jmltotal=new double[jml];
        jmlstok=new double[jml];
        isi=new double[jml];
        isibesar=new double[jml];
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
                    harga[index]=Double.parseDouble(tbDokter.getValueAt(i,5).toString());
                    subtotal[index]=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
                    diskon[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                    besardiskon[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                    jmltotal[index]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                    jmlstok[index]=Double.parseDouble(tbDokter.getValueAt(i,10).toString());
                    isi[index]=Double.parseDouble(tbDokter.getValueAt(i,11).toString());
                    isibesar[index]=Double.parseDouble(tbDokter.getValueAt(i,12).toString());
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{jumlah[i],satuanbeli[i],kodebarang[i],namabarang[i],satuan[i],harga[i],subtotal[i],diskon[i],besardiskon[i],jmltotal[i],jmlstok[i],isi[i],isibesar[i]});
        }
        
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar, "+
                    " (databarang.h_beli*databarang.isi) as harga,databarang.kode_sat,databarang.isi "+
                    " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " where databarang.status='1' order by databarang.nama_brng");
            }else{
                ps=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar, "+
                    " (databarang.h_beli*databarang.isi) as harga,databarang.kode_sat,databarang.isi "+
                    " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " where databarang.status='1' and databarang.kode_brng like ? or "+
                    " databarang.status='1' and databarang.nama_brng like ? or "+
                    " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            }
                
            try {
                if(TCari.getText().trim().equals("")){}else{
                    ps.setString(1,"%"+TCari.getText().trim()+"%");
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        "",rs.getString("kode_satbesar"),rs.getString("kode_brng"),rs.getString("nama_brng"),
                        rs.getString("kode_sat"),rs.getDouble("harga"),0,0,0,0,0,rs.getDouble("isi"),1
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
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*Double.parseDouble(tbDokter.getValueAt(row,5).toString()), row,6);                
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,6).toString())-Double.parseDouble(tbDokter.getValueAt(row,8).toString()), row,9);  
                        tbDokter.setValueAt(Double.parseDouble(tbDokter.getValueAt(row,0).toString())*(Double.parseDouble(tbDokter.getValueAt(row,11).toString())/Double.parseDouble(tbDokter.getValueAt(row,12).toString())), row,10); 
                    } 
                } catch (Exception e) {
                    tbDokter.setValueAt("",row,0);
                    tbDokter.setValueAt(0,row,6);
                    tbDokter.setValueAt(0,row,7);
                    tbDokter.setValueAt(0,row,8);
                    tbDokter.setValueAt(0,row,9);
                    tbDokter.setValueAt(0,row,10);          
                }
            }else{
                tbDokter.setValueAt(0,row,6);
                tbDokter.setValueAt(0,row,7);
                tbDokter.setValueAt(0,row,8);
                tbDokter.setValueAt(0,row,9);
                tbDokter.setValueAt(0,row,10);  
            }
        }
                
        ttl=0;sbttl=0;ppn=0;ttldisk=0;
        y=0;w=0;
        meterai=0;
        if(!Meterai.getText().equals("")){
            meterai=Double.parseDouble(Meterai.getText());
        }

        jml=tbDokter.getRowCount();
        for(i=0;i<jml;i++){                 
            try {
                w=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
            } catch (Exception e) {
                tbDokter.setValueAt(0,tbDokter.getSelectedRow(),6);
                w=0;                
            }
            sbttl=sbttl+w;                

            try {
                y=Double.parseDouble(tbDokter.getValueAt(i,8).toString()); 
            } catch (Exception e) {
                tbDokter.setValueAt(0,tbDokter.getSelectedRow(),8);
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
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            btnPetugas.setEnabled(false);
            kdptg.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getsurat_pemesanan_medis());
            BtnTambah.setEnabled(akses.getobat());
            Sequel.cariIsi("select nama from pegawai where nik=?", nmptg,kdptg.getText());
            Sequel.cariIsi("select departemen from pegawai where nik=?",Departemen,kdptg.getText());
        }        
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_pemesanan,3),signed)),0) from surat_pemesanan_medis where tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "SPM"+Tanggal.getSelectedItem().toString().substring(8,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoPemesanan); 
    }

    public DefaultTableModel tabMode(){
        return tabMode;
    }
    
    public void panggilgetData(){
        getData();
    }
    
    public void panggilgetData(String nopengajuan){
        try{
            ps=koneksi.prepareStatement(
                "select databarang.kode_brng, databarang.nama_brng,detail_pengajuan_barang_medis.kode_sat as satbesar,databarang.kode_sat,"+
                "detail_pengajuan_barang_medis.h_pengajuan,detail_pengajuan_barang_medis.jumlah,detail_pengajuan_barang_medis.total, "+
                "detail_pengajuan_barang_medis.jumlah2,databarang.isi from databarang inner join jenis inner join detail_pengajuan_barang_medis "+
                " on databarang.kdjns=jenis.kdjns and databarang.kode_brng=detail_pengajuan_barang_medis.kode_brng "+
                " where detail_pengajuan_barang_medis.no_pengajuan=?");
            try {
                ps.setString(1,nopengajuan);
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("jumlah"),rs.getString("satbesar"),rs.getString("kode_brng"),
                        rs.getString("nama_brng"),rs.getString("kode_sat"),rs.getDouble("h_pengajuan"),
                        rs.getDouble("total"),0,0,rs.getDouble("total"),rs.getDouble("jumlah2"),rs.getDouble("isi"),
                        (rs.getDouble("isi")/(rs.getDouble("jumlah2")/rs.getDouble("jumlah")))
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
        getData();
    }
}

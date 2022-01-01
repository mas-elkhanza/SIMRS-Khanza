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
import java.awt.event.KeyListener;
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
import widget.Button;

/**
 *
 * @author dosen
 */
public final class InventoryResepLuar extends javax.swing.JDialog {
    private final DefaultTableModel tabModeResep,tabModeDetailResepRacikan,tabModeResepRacikan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psresep;
    private ResultSet rsobat;
    private double jumlahracik=0,persenracik=0,kapasitasracik=0;
    private int i=0,z=0,jmlobat=0,r=0;
    private double[] jumlah,kapasitas,p1,p2;
    private boolean sukses=true;
    private String[] no,kodebarang,namabarang,kodesatuan,kandungan,letakbarang,namajenis,aturan,industri,komposisi;
    public DlgCariAturanPakai aturanpakai=new DlgCariAturanPakai(null,false);
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();
    private WarnaTable2 warna3=new WarnaTable2();
    private DlgCariMetodeRacik metoderacik=new DlgCariMetodeRacik(null,false);
    public DlgCariDokter dokter=new DlgCariDokter(null,false);
    private String noracik="";
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public InventoryResepLuar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);
        tabModeResep=new DefaultTableModel(null,new Object[]{
                "Jumlah","Kode Barang","Nama Barang","Satuan","Komposisi","Jenis Obat","Aturan Pakai","I.F."
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==6)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbResep.setModel(tabModeResep);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 8; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(45);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(85);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(130);
            }else if(i==7){
                column.setPreferredWidth(100);
            }               
        }
        warna.kolom=0;
        tbResep.setDefaultRenderer(Object.class,warna);
        
        tabModeResepRacikan=new DefaultTableModel(null,new Object[]{
                "No","Nama Racikan","Kode Racik","Metode Racik","Jml.Racik","Aturan Pakai","Keterangan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==0)||(colIndex==2)||(colIndex==3)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObatResepRacikan.setModel(tabModeResepRacikan);
        tbObatResepRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatResepRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatResepRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(250);
            }
        }

        warna2.kolom=4;
        tbObatResepRacikan.setDefaultRenderer(Object.class,warna2);
        
        tabModeDetailResepRacikan=new DefaultTableModel(null,new Object[]{
                "No","Kode Barang","Nama Barang","Satuan","Jenis Obat","Kps","P1","/","P2","Kandungan","Jml","I.F.","Komposisi"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==6)||(colIndex==8)||(colIndex==9)||(colIndex==10)) {
                    a=true;
                }
                return a;
             }             
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbDetailResepObatRacikan.setModel(tabModeDetailResepRacikan);
        tbDetailResepObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailResepObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 13; i++) {
            TableColumn column = tbDetailResepObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(45);
            }else if(i==4){
                column.setPreferredWidth(85);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(25);
            }else if(i==7){
                column.setPreferredWidth(10);
            }else if(i==8){
                column.setPreferredWidth(25);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==10){
                column.setPreferredWidth(40);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(150);
            }
        }

        warna3.kolom=9;
        tbDetailResepObatRacikan.setDefaultRenderer(Object.class,warna3);
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampilobat();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampilobat();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampilobat();
                    }
                }
            });
        }
        
        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturanpakai.getTable().getSelectedRow()!= -1){  
                    if(TabRawat.getSelectedIndex()==0){
                        tbResep.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbResep.getSelectedRow(),6);
                    }else if(TabRawat.getSelectedIndex()==1){
                        tbObatResepRacikan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbObatResepRacikan.getSelectedRow(),5);
                        tbObatResepRacikan.requestFocus();
                    }   
                }   
                tbResep.requestFocus();
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){        
                     KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                     NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }  
                KdDokter.requestFocus();
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
        
        metoderacik.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(metoderacik.getTable().getSelectedRow()!= -1){  
                    tbObatResepRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),1).toString(),tbObatResepRacikan.getSelectedRow(),2);
                    tbObatResepRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),2).toString(),tbObatResepRacikan.getSelectedRow(),3);
                    tbObatResepRacikan.requestFocus();
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
        
        metoderacik.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    metoderacik.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        jam();
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
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSeek5 = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnTambah1 = new widget.Button();
        BtnHapus = new widget.Button();
        BtnCari1 = new widget.Button();
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
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbResep = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbObatResepRacikan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbDetailResepObatRacikan = new widget.Table();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Resep Luar Obat/BHP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(734, 56));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(245, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
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
        panelisi3.add(BtnTambah);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnSeek5.setMnemonic('4');
        BtnSeek5.setToolTipText("Alt+4");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        panelisi3.add(BtnSeek5);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        BtnTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        BtnTambah1.setMnemonic('3');
        BtnTambah1.setToolTipText("Alt+3");
        BtnTambah1.setName("BtnTambah1"); // NOI18N
        BtnTambah1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambah1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah1);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelisi3.add(BtnHapus);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari1.setMnemonic('C');
        BtnCari1.setText("Cari");
        BtnCari1.setToolTipText("Alt+C");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelisi3.add(BtnCari1);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

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
        NmDokter.setBounds(196, 72, 457, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 72, 23);

        jLabel13.setText("Peresep :");
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
        btnDokter.setBounds(655, 72, 28, 23);

        jLabel11.setText("No.Resep :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(455, 42, 70, 23);

        NoResep.setHighlighter(null);
        NoResep.setName("NoResep"); // NOI18N
        NoResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepKeyPressed(evt);
            }
        });
        FormInput.add(NoResep);
        NoResep.setBounds(528, 42, 130, 23);

        jLabel8.setText("Tgl.Resep :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 42, 72, 23);

        DTPBeri.setForeground(new java.awt.Color(50, 70, 50));
        DTPBeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-09-2021" }));
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
        ChkRM.setBounds(660, 42, 23, 23);

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

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbResep.setComponentPopupMenu(Popup);
        tbResep.setName("tbResep"); // NOI18N
        tbResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbResep);

        TabRawat.addTab("Umum", Scroll);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(454, 90));

        tbObatResepRacikan.setName("tbObatResepRacikan"); // NOI18N
        tbObatResepRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatResepRacikanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbObatResepRacikan);

        jPanel3.add(Scroll1, java.awt.BorderLayout.PAGE_START);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setComponentPopupMenu(Popup);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDetailResepObatRacikan.setAutoCreateRowSorter(true);
        tbDetailResepObatRacikan.setComponentPopupMenu(Popup);
        tbDetailResepObatRacikan.setName("tbDetailResepObatRacikan"); // NOI18N
        tbDetailResepObatRacikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDetailResepObatRacikanPropertyChange(evt);
            }
        });
        tbDetailResepObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailResepObatRacikanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbDetailResepObatRacikan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Racikan", jPanel3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbResep.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            tampilobat();
        }else if(TabRawat.getSelectedIndex()==1){
            if(tbObatResepRacikan.getRowCount()!=0){
                if(tbObatResepRacikan.getSelectedRow()!= -1){
                    if(tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),1).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),2).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),3).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),4).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),5).toString().equals("")||
                            tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),6).toString().equals("")){
                        JOptionPane.showMessageDialog(null,"Silahkan lengkapi data racikan..!!");
                    }else{
                        tampildetailracikanresep();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan pilih racikan..!!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan masukkan racikan..!!");
            }
        }  
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
        BtnCariActionPerformed(evt);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepKeyPressed
        if(tbResep.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                i=tbResep.getSelectedColumn();
                if((i==0)||(i==6)){
                    if(tbResep.getSelectedRow()!= -1){
                        tbResep.setValueAt("",tbResep.getSelectedRow(),i);
                    }
                }   
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                i=tbResep.getSelectedColumn();
                if(i!=7){
                    TCari.requestFocus();
                }                
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbResep.getSelectedColumn();
                if(i==6){
                    akses.setform("DlgCariObat");
                    aturanpakai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }
            }   
        }
}//GEN-LAST:event_tbResepKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarang barang=new DlgBarang(null,false);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());           
    }//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        jmlobat=0;
        for(i=0;i<tbResep.getRowCount();i++){
            if(Valid.SetAngka(tbResep.getValueAt(i,0).toString())>0){
                jmlobat++;
            }
        }
        for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){ 
            if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(i,10).toString())>0){
                jmlobat++;
            }
        }
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(NoResep.getText().trim().equals("")){
            Valid.textKosong(NoResep,"No.Resep");
        }else if(jmlobat<=0){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan masukkan terlebih dahulu obat yang mau diberikan...!!!");
            TCari.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {                 
                ChkJln.setSelected(false);    
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("resep_luar","?,?,?,?,?,?,?","Nomer Resep",7,new String[]{
                    NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                    cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                    TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                    cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()
                    })==true){
                        simpandata();
                }else{
                    emptTeksobat();
                    if(Sequel.menyimpantf2("resep_luar","?,?,?,?,?,?,?","Nomer Resep",7,new String[]{
                        NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()
                        })==true){
                            simpandata();
                    }else{
                        emptTeksobat();
                        if(Sequel.menyimpantf2("resep_luar","?,?,?,?,?,?,?","Nomer Resep",7,new String[]{
                            NoResep.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                            cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(DTPBeri.getSelectedItem()+""),
                            cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()
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
                    for(i=0;i<tbResep.getRowCount();i++){
                        tbResep.setValueAt("",i,0);
                    }
                    Valid.tabelKosong(tabModeResepRacikan);
                    Valid.tabelKosong(tabModeDetailResepRacikan);
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                ChkJln.setSelected(true);
            }                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
    DlgCariKonversi carikonversi=new DlgCariKonversi(null,false);
    carikonversi.setLocationRelativeTo(internalFrame1);
    carikonversi.setAlwaysOnTop(false);
    carikonversi.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek5KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    if(TabRawat.getSelectedIndex()==0){
        for(i=0;i<tbResep.getRowCount();i++){ 
            tbResep.setValueAt("",i,1);
            tbResep.setValueAt("",i,6);
        }
    }else if(TabRawat.getSelectedIndex()==1){
        for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){ 
            tbDetailResepObatRacikan.setValueAt(1,i,6);
            tbDetailResepObatRacikan.setValueAt(1,i,8);
            tbDetailResepObatRacikan.setValueAt("",i,9);
            tbDetailResepObatRacikan.setValueAt(0,i,10);
        }
    }  
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        TabRawatMouseClicked(null);
        emptTeksobat();
            
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilobat();
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            BtnTambah1.setVisible(false);
            BtnHapus.setVisible(false);
            TCari.setPreferredSize(new Dimension(235, 23));
        }else if(TabRawat.getSelectedIndex()==1){
            BtnTambah1.setVisible(true);
            BtnHapus.setVisible(true);
            TCari.setPreferredSize(new Dimension(171, 23));
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbObatResepRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatResepRacikanKeyPressed
        if(tbObatResepRacikan.getRowCount()!=0){
            i=tbObatResepRacikan.getSelectedColumn();
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                if(i==5){
                    akses.setform("DlgCariObat");
                    aturanpakai.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }else if(i==3){
                    if(tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),1).equals("")){
                        JOptionPane.showMessageDialog(null,"Silahkan masukkan nama racikan..!!");
                        tbObatResepRacikan.requestFocus();
                    }else{
                        metoderacik.isCek();
                        metoderacik.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        metoderacik.setLocationRelativeTo(internalFrame1);
                        metoderacik.setVisible(true);
                    }
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                if(i==6){
                    TCari.requestFocus();
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(i==6){
                    tampildetailracikanresep();
                }
            }
        }
    }//GEN-LAST:event_tbObatResepRacikanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),1).equals("")&&tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),4).equals("")&&tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),5).equals("")&&tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),6).equals("")){
            tabModeResepRacikan.removeRow(tbObatResepRacikan.getSelectedRow());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf sudah terisi, gak boleh dihapus..!!");
        }

    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambah1ActionPerformed
        i=tabModeResepRacikan.getRowCount()+1;
        if(i==99){
            JOptionPane.showMessageDialog(null,"Maksimal 98 Racikan..!!");
        }else{
            tabModeResepRacikan.addRow(new Object[]{""+i,"","","","","",""});
        }
    }//GEN-LAST:event_BtnTambah1ActionPerformed

    private void tbDetailResepObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailResepObatRacikanKeyPressed
        if(tbDetailResepObatRacikan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                i=tbDetailResepObatRacikan.getSelectedColumn();
                if((i==6)||(i==8)){
                    try {
                        if(!tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),6).toString().equals(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),8).toString())){
                            if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),5).toString())==0){
                                JOptionPane.showMessageDialog(null,"Kapasitas obat masih kosong..!!!");
                            }else{
                                tbDetailResepObatRacikan.setValueAt(Valid.SetAngka8(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),5).toString())*
                                    (Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),6).toString())/Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),8).toString())),1),
                                        tbDetailResepObatRacikan.getSelectedRow(),9);
                            }                                
                        }
                    } catch (Exception e) {
                        tbDetailResepObatRacikan.setValueAt(0,tbDetailResepObatRacikan.getSelectedRow(),9);
                    }      
                }else if(i==9){
                    if(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),9).toString().contains("%")){
                        getDatadetailresepracikan2();
                    }else{
                        getDatadetailresepracikan();
                    }  
                }
            }
        }
    }//GEN-LAST:event_tbDetailResepObatRacikanKeyPressed

    private void tbDetailResepObatRacikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDetailResepObatRacikanPropertyChange
        if(this.isVisible()==true){
            try {
                if(tbDetailResepObatRacikan.getSelectedRow()!= -1){
                    if(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),9).toString().contains("%")){
                        getDatadetailresepracikan2();
                    }
                }else{
                    getDatadetailresepracikan();
                }  
            } catch (Exception e) {
            }   
        }
    }//GEN-LAST:event_tbDetailResepObatRacikanPropertyChange

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

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

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,NoResep);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPBeri,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void DTPBeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPBeriKeyPressed
        Valid.pindah(evt,TNoRw,cmbJam);
    }//GEN-LAST:event_DTPBeriKeyPressed

    private void DTPBeriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPBeriItemStateChanged
        try {
            emptTeksobat();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_DTPBeriItemStateChanged

    private void NoResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepKeyPressed
        Valid.pindah(evt,cmbDtk,KdDokter);
    }//GEN-LAST:event_NoResepKeyPressed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        Valid.pindah(evt,KdDokter,BtnSimpan);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.isCek();
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",NmDokter,KdDokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,NoResep,BtnSimpan);
        }
    }//GEN-LAST:event_KdDokterKeyPressed

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

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InventoryCariResepLuar form=new InventoryCariResepLuar(null,false);
        form.setNoRm(TNoRw.getText(),DTPBeri.getDate());
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCari1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventoryResepLuar dialog = new InventoryResepLuar(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambah1;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkRM;
    private widget.Tanggal DTPBeri;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDokter;
    private widget.TextBox NmDokter;
    private widget.TextBox NoResep;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Button btnDokter;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel3;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.Table tbDetailResepObatRacikan;
    private widget.Table tbObatResepRacikan;
    private widget.Table tbResep;
    // End of variables declaration//GEN-END:variables

    public void tampilobat() {        
        z=0;
        for(i=0;i<tbResep.getRowCount();i++){
            if(!tbResep.getValueAt(i,0).toString().equals("")){
                z++;
            }
        }    
        
        jumlah=null;
        jumlah=new double[z];
        kodebarang=null;
        kodebarang=new String[z];
        namabarang=null;
        namabarang=new String[z];
        kodesatuan=null;
        kodesatuan=new String[z];
        letakbarang=null;
        letakbarang=new String[z];
        namajenis=null;
        namajenis=new String[z];                   
        aturan=null;
        aturan=new String[z];           
        industri=null;
        industri=new String[z];   
        z=0;        
        for(i=0;i<tbResep.getRowCount();i++){
            if(!tbResep.getValueAt(i,0).toString().equals("")){
                try {
                    jumlah[z]=Double.parseDouble(tbResep.getValueAt(i,0).toString());
                } catch (Exception e) {
                    jumlah[z]=0;
                }  
                kodebarang[z]=tbResep.getValueAt(i,1).toString();
                namabarang[z]=tbResep.getValueAt(i,2).toString();
                kodesatuan[z]=tbResep.getValueAt(i,3).toString();
                letakbarang[z]=tbResep.getValueAt(i,4).toString();
                namajenis[z]=tbResep.getValueAt(i,5).toString();
                aturan[z]=tbResep.getValueAt(i,6).toString();
                industri[z]=tbResep.getValueAt(i,7).toString();
                z++;
            }
        }
        
        Valid.tabelKosong(tabModeResep);             
        
        for(i=0;i<z;i++){
            tabModeResep.addRow(new Object[] {
                jumlah[i],kodebarang[i],namabarang[i],kodesatuan[i],letakbarang[i],namajenis[i],aturan[i],industri[i]
            });
        }
        
        try {
            psresep=koneksi.prepareStatement(
                "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,"+
                " databarang.letak_barang,industrifarmasi.nama_industri "+
                " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                " inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri "+
                " where databarang.status='1' and (databarang.kode_brng like ? or databarang.nama_brng like ? or jenis.nama like ? or databarang.letak_barang like ?) order by databarang.nama_brng");
            try{
                psresep.setString(1,"%"+TCari.getText().trim()+"%");
                psresep.setString(2,"%"+TCari.getText().trim()+"%");
                psresep.setString(3,"%"+TCari.getText().trim()+"%");
                psresep.setString(4,"%"+TCari.getText().trim()+"%");
                rsobat=psresep.executeQuery();
                while(rsobat.next()){
                    tabModeResep.addRow(new Object[] {
                       "",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),rsobat.getString("nama"),"",rsobat.getString("nama_industri")
                    });
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsobat != null){
                    rsobat.close();
                }

                if(psresep != null){
                    psresep.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }            
    }

    public void emptTeksobat() {
        if(ChkRM.isSelected()==true){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_resep,3),signed)),0) from resep_luar where tgl_perawatan='"+Valid.SetTgl(DTPBeri.getSelectedItem()+"")+"' ",
                "RL"+DTPBeri.getSelectedItem().toString().substring(6,10)+DTPBeri.getSelectedItem().toString().substring(3,5)+DTPBeri.getSelectedItem().toString().substring(0,2),3,NoResep);        
        } 
    }

    public JTable getTable(){
        return tbResep;
    }
    
    public Button getButton(){
        return BtnSimpan;
    }
    
    public void isCek(){   
        BtnTambah.setEnabled(akses.getresep_luar());
        TCari.requestFocus();
    }
    
    public void setNoRm(String norwt,String KodeDokter,String NamaDokter,String Pasien) {        
        TNoRw.setText(norwt);
        TPasien.setText(Pasien);
        KdDokter.setText(KodeDokter);
        NmDokter.setText(NamaDokter);
        TCari.requestFocus();
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
    
    public void tampildetailracikanresep() {        
        z=0;
        for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){
            if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(i,10).toString())>0){
                z++;
            }
        }    
        
        jumlah=null;
        jumlah=new double[z];
        p1=null;
        p1=new double[z];
        p2=null;
        p2=new double[z];
        kodebarang=null;
        kodebarang=new String[z];
        namabarang=null;
        namabarang=new String[z];
        kodesatuan=null;
        kodesatuan=new String[z];
        letakbarang=null;
        letakbarang=new String[z];
        no=null;
        no=new String[z];
        namajenis=null;
        namajenis=new String[z];        
        industri=null;
        industri=new String[z];          
        komposisi=null;
        komposisi=new String[z];   
        kapasitas=null;
        kapasitas=new double[z];   
        kandungan=null;
        kandungan=new String[z];
        z=0;        
        for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){
            if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(i,10).toString())>0){
                no[z]=tbDetailResepObatRacikan.getValueAt(i,0).toString();
                kodebarang[z]=tbDetailResepObatRacikan.getValueAt(i,1).toString();
                namabarang[z]=tbDetailResepObatRacikan.getValueAt(i,2).toString();
                kodesatuan[z]=tbDetailResepObatRacikan.getValueAt(i,3).toString();      
                namajenis[z]=tbDetailResepObatRacikan.getValueAt(i,4).toString();           
                try {
                    kapasitas[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,5).toString());
                } catch (Exception e) {
                    kapasitas[z]=0;
                }          
                try {
                    p1[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,6).toString());
                } catch (Exception e) {
                    p1[z]=0;
                } 
                try {
                    p2[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,8).toString());
                } catch (Exception e) {
                    p2[z]=0;
                } 
                kandungan[z]=tbDetailResepObatRacikan.getValueAt(i,9).toString();
                try {
                    jumlah[z]=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,10).toString());
                } catch (Exception e) {
                    jumlah[z]=0;
                }                 
                industri[z]=tbDetailResepObatRacikan.getValueAt(i,11).toString();
                komposisi[z]=tbDetailResepObatRacikan.getValueAt(i,12).toString();
                z++;
            }
        }
        
        Valid.tabelKosong(tabModeDetailResepRacikan);             
        
        for(i=0;i<z;i++){
            tabModeDetailResepRacikan.addRow(new Object[] {
                no[i],kodebarang[i],namabarang[i],kodesatuan[i],namajenis[i],kapasitas[i],p1[i],"/",p2[i],kandungan[i],jumlah[i],industri[i],komposisi[i]
            });
        }
        
        try {
            psresep=koneksi.prepareStatement(
                "select databarang.kode_brng,databarang.nama_brng,jenis.nama,databarang.kode_sat,"+
                " databarang.letak_barang,industrifarmasi.nama_industri,databarang.kapasitas "+
                " from databarang inner join jenis inner join industrifarmasi "+
                " on databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri "+
                " where databarang.status='1' and (databarang.kode_brng like ? or databarang.nama_brng like ? or jenis.nama like ? or databarang.letak_barang like ?) order by databarang.nama_brng");
            try{ 
                psresep.setString(1,"%"+TCari.getText().trim()+"%");
                psresep.setString(2,"%"+TCari.getText().trim()+"%");
                psresep.setString(3,"%"+TCari.getText().trim()+"%");
                psresep.setString(4,"%"+TCari.getText().trim()+"%");
                rsobat=psresep.executeQuery();
                while(rsobat.next()){
                    tabModeDetailResepRacikan.addRow(new Object[] {
                        tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),0).toString(),
                        rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),rsobat.getString("kode_sat"),rsobat.getString("nama"),
                        rsobat.getDouble("kapasitas"),1,"/",1,"",0,rsobat.getString("nama_industri"),rsobat.getString("letak_barang")
                    }); 
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsobat != null){
                    rsobat.close();
                }
                if(psresep != null){
                    psresep.close();
                }
            }   
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }            
    }

    private void getDatadetailresepracikan() {
        if(tbDetailResepObatRacikan.getSelectedRow()!= -1){
            try {
                tbDetailResepObatRacikan.setValueAt(Valid.SetAngka8((Double.parseDouble(tbObatResepRacikan.getValueAt(tbObatResepRacikan.getSelectedRow(),4).toString())
                                *Double.parseDouble(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),9).toString()))
                                /Double.parseDouble(tbDetailResepObatRacikan.getValueAt(tbDetailResepObatRacikan.getSelectedRow(),5).toString()),1)
                                ,tbDetailResepObatRacikan.getSelectedRow(),10);
            } catch (Exception e) {
                tbDetailResepObatRacikan.setValueAt(0,tbDetailResepObatRacikan.getSelectedRow(),10);
            }
        }
    }
    
    private void getDatadetailresepracikan2() {
        if(tbDetailResepObatRacikan.getSelectedRow()!= -1){
            try {
                r=tbDetailResepObatRacikan.getSelectedRow();
                noracik=tbDetailResepObatRacikan.getValueAt(r,0).toString();
                jumlahracik=0;
                persenracik=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(r,9).toString().replaceAll("%",""));
                kapasitasracik=Double.parseDouble(tbDetailResepObatRacikan.getValueAt(r,5).toString());
                for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){ 
                    if(noracik==tbDetailResepObatRacikan.getValueAt(i,0).toString()){
                        if(!tbDetailResepObatRacikan.getValueAt(i,9).toString().contains("%")){
                            jumlahracik=jumlahracik+(Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,5).toString())*
                                    Double.parseDouble(tbDetailResepObatRacikan.getValueAt(i,10).toString()));
                        }
                    }
                }
                tbDetailResepObatRacikan.setValueAt(Valid.SetAngka8((jumlahracik*(persenracik/100))/kapasitasracik,1),r,10);
            } catch (Exception e) {
                tbDetailResepObatRacikan.setValueAt(0,r,10);
            }
        }
    }
    
    
    private void simpandata() {
        try {
            for(i=0;i<tbResep.getRowCount();i++){ 
                if(Valid.SetAngka(tbResep.getValueAt(i,0).toString())>0){   
                    if(Sequel.menyimpantf2("resep_luar_obat","?,?,?,?","data",4,new String[]{
                        NoResep.getText(),tbResep.getValueAt(i,1).toString(),
                        ""+(Double.parseDouble(tbResep.getValueAt(i,0).toString())),
                        tbResep.getValueAt(i,6).toString()
                    })==false){
                        sukses=false;
                    }                  
                }
            } 

            for(i=0;i<tbObatResepRacikan.getRowCount();i++){ 
                if(Valid.SetAngka(tbObatResepRacikan.getValueAt(i,4).toString())>0){ 
                    if(Sequel.menyimpantf2("resep_luar_racikan","?,?,?,?,?,?,?","resep luar racikan",7,new String[]{
                       NoResep.getText(),tbObatResepRacikan.getValueAt(i,0).toString(),tbObatResepRacikan.getValueAt(i,1).toString(),
                       tbObatResepRacikan.getValueAt(i,2).toString(),tbObatResepRacikan.getValueAt(i,4).toString(),
                       tbObatResepRacikan.getValueAt(i,5).toString(),tbObatResepRacikan.getValueAt(i,6).toString()
                    })==false){
                        sukses=false;
                    } 
                }
            }

            for(i=0;i<tbDetailResepObatRacikan.getRowCount();i++){ 
                if(Valid.SetAngka(tbDetailResepObatRacikan.getValueAt(i,10).toString())>0){
                    if(Sequel.menyimpantf2("resep_luar_racikan_detail","?,?,?,?,?,?,?","resep luar racikan detail",7,new String[]{
                        NoResep.getText(),tbDetailResepObatRacikan.getValueAt(i,0).toString(),tbDetailResepObatRacikan.getValueAt(i,1).toString(),
                        tbDetailResepObatRacikan.getValueAt(i,6).toString(),tbDetailResepObatRacikan.getValueAt(i,8).toString(),
                        tbDetailResepObatRacikan.getValueAt(i,9).toString(),tbDetailResepObatRacikan.getValueAt(i,10).toString()
                    })==false){
                        sukses=false;
                    } 
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
    }
    
    public void MatikanJam(){
        ChkJln.setSelected(false);
    }
    
}

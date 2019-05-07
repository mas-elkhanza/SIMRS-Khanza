package parkir;
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
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgPetugas;

/**
 *
 * @author perpustakaan
 */
public class DlgParkirMasuk extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private int i;
    private DlgParkirJenis jenis=new DlgParkirJenis(null,false);
    private DlgPetugas petugas=new DlgPetugas(null,false);

    /** Creates new form DlgKamarInap
     * @param parent
       @param modal */
    public DlgParkirMasuk(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"No.Rawat","Nomer RM","Nama Pasien","Alamat Pasien","Jenis Bayar","Kamar","Tarif Kamar",
                    "Diagnosa Awal","Diagnosa Akhir","Tgl.Masuk","Jam Masuk","Tgl.Keluar","Jam Keluar",
                    "Ttl.Biaya","Stts.Pulang","Lama","Dokter P.J."};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamIn.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKamIn.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamIn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbKamIn.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(75);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setPreferredWidth(75);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(100);
            }else if(i==14){
                column.setPreferredWidth(80);
            }else if(i==15){
                column.setPreferredWidth(45);
            }else if(i==16){
                column.setPreferredWidth(150);
            }
        }
        tbKamIn.setDefaultRenderer(Object.class, new WarnaTable());

        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        KdJenis.setDocument(new batasInput((byte)5).getKata(KdJenis));
        Tarif.setDocument(new batasInput((byte)10).getOnlyAngka(Tarif));
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
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
        WindowInputParkir.setSize(445,200);
        WindowInputParkir.setLocationRelativeTo(null);
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jenis.getTable().getSelectedRow()!= -1){                   
                    KdJenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),0).toString());                    
                    NmJenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),1).toString());                    
                    Tarif.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),2).toString());                    
                    SIstem.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(),3).toString());                    
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
        
        jenis.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jenis.dispose();
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
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());                    
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());                   
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
        
        petugas.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    petugas.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            //  
        } catch (Exception e) {
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

        WindowInputParkir = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        NomorKendaraan = new widget.TextBox();
        jLabel3 = new widget.Label();
        BtnCloseIn = new widget.Button();
        jLabel4 = new widget.Label();
        NomorKartu = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        PanelCariUtama = new javax.swing.JPanel();
        panelGlass10 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        LCount2 = new widget.Label();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass11 = new widget.panelisi();
        jLabel21 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        jLabel8 = new widget.Label();
        LCount1 = new widget.Label();
        panelGlass12 = new widget.panelisi();
        jLabel22 = new widget.Label();
        Barcode = new widget.TextBox();
        jLabel25 = new widget.Label();
        KdJenis = new widget.TextBox();
        NmJenis = new widget.TextBox();
        Tarif = new widget.TextBox();
        SIstem = new widget.TextBox();
        btnJenis = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbKamIn = new widget.Table();

        WindowInputParkir.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInputParkir.setName("WindowInputParkir"); // NOI18N
        WindowInputParkir.setUndecorated(true);
        WindowInputParkir.setResizable(false);
        WindowInputParkir.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                WindowInputParkirWindowActivated(evt);
            }
        });

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Input Parkir Masuk ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70,70,70))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame2.setLayout(null);

        NomorKendaraan.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        NomorKendaraan.setHighlighter(null);
        NomorKendaraan.setName("NomorKendaraan"); // NOI18N
        NomorKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NomorKendaraanActionPerformed(evt);
            }
        });
        NomorKendaraan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorKendaraanKeyPressed(evt);
            }
        });
        internalFrame2.add(NomorKendaraan);
        NomorKendaraan.setBounds(20, 110, 400, 70);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Nomor Kendaraan :");
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        internalFrame2.add(jLabel3);
        jLabel3.setBounds(20, 80, 195, 30);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('U');
        BtnCloseIn.setToolTipText("Alt+U");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(390, 14, 30, 30);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Nomor Kartu :");
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        internalFrame2.add(jLabel4);
        jLabel4.setBounds(20, 20, 195, 30);

        NomorKartu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        NomorKartu.setHighlighter(null);
        NomorKartu.setName("NomorKartu"); // NOI18N
        NomorKartu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorKartuKeyPressed(evt);
            }
        });
        internalFrame2.add(NomorKartu);
        NomorKartu.setBounds(20, 50, 250, 30);

        WindowInputParkir.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Parkir Masuk ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70,70,70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 143));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(230, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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
        panelGlass10.add(BtnCari);

        LCount2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount2.setName("LCount2"); // NOI18N
        LCount2.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount2);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnHapus);

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
        panelGlass10.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass10.add(BtnAll);

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
        panelGlass10.add(BtnKeluar);

        PanelCariUtama.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass11.setLayout(null);

        jLabel21.setText("Petugas :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass11.add(jLabel21);
        jLabel21.setBounds(6, 10, 65, 23);

        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(100, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        panelGlass11.add(KdPetugas);
        KdPetugas.setBounds(76, 10, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(230, 23));
        NmPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmPetugasKeyPressed(evt);
            }
        });
        panelGlass11.add(NmPetugas);
        NmPetugas.setBounds(178, 10, 235, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('3');
        btnPetugas.setToolTipText("Alt+3");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        panelGlass11.add(btnPetugas);
        btnPetugas.setBounds(416, 10, 28, 23);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass11.add(jLabel7);
        jLabel7.setBounds(449, 10, 65, 23);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass11.add(LCount);
        LCount.setBounds(519, 10, 50, 23);

        jLabel8.setText("Pendapatan :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(77, 23));
        panelGlass11.add(jLabel8);
        jLabel8.setBounds(574, 10, 77, 23);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass11.add(LCount1);
        LCount1.setBounds(656, 10, 150, 23);

        PanelCariUtama.add(panelGlass11, java.awt.BorderLayout.CENTER);

        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass12.setLayout(null);

        jLabel22.setText("Barcode :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass12.add(jLabel22);
        jLabel22.setBounds(6, 10, 65, 23);

        Barcode.setName("Barcode"); // NOI18N
        Barcode.setPreferredSize(new java.awt.Dimension(150, 23));
        Barcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BarcodeKeyPressed(evt);
            }
        });
        panelGlass12.add(Barcode);
        Barcode.setBounds(76, 10, 170, 23);

        jLabel25.setText("Jenis :");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass12.add(jLabel25);
        jLabel25.setBounds(246, 10, 50, 23);

        KdJenis.setName("KdJenis"); // NOI18N
        KdJenis.setPreferredSize(new java.awt.Dimension(70, 23));
        KdJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdJenisKeyPressed(evt);
            }
        });
        panelGlass12.add(KdJenis);
        KdJenis.setBounds(301, 10, 70, 23);

        NmJenis.setEditable(false);
        NmJenis.setName("NmJenis"); // NOI18N
        NmJenis.setPreferredSize(new java.awt.Dimension(220, 23));
        NmJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmJenisActionPerformed(evt);
            }
        });
        NmJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmJenisKeyPressed(evt);
            }
        });
        panelGlass12.add(NmJenis);
        NmJenis.setBounds(373, 10, 228, 23);

        Tarif.setEditable(false);
        Tarif.setName("Tarif"); // NOI18N
        Tarif.setPreferredSize(new java.awt.Dimension(80, 23));
        Tarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TarifKeyPressed(evt);
            }
        });
        panelGlass12.add(Tarif);
        Tarif.setBounds(603, 10, 82, 23);

        SIstem.setEditable(false);
        SIstem.setName("SIstem"); // NOI18N
        SIstem.setPreferredSize(new java.awt.Dimension(80, 23));
        SIstem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SIstemKeyPressed(evt);
            }
        });
        panelGlass12.add(SIstem);
        SIstem.setBounds(687, 10, 82, 23);

        btnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnJenis.setMnemonic('3');
        btnJenis.setToolTipText("Alt+3");
        btnJenis.setName("btnJenis"); // NOI18N
        btnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        btnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisActionPerformed(evt);
            }
        });
        btnJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnJenisKeyPressed(evt);
            }
        });
        panelGlass12.add(btnJenis);
        btnJenis.setBounds(771, 10, 28, 23);

        PanelCariUtama.add(panelGlass12, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamIn.setAutoCreateRowSorter(true);
        tbKamIn.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamIn.setName("tbKamIn"); // NOI18N
        tbKamIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamInMouseClicked(evt);
            }
        });
        tbKamIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamInKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamIn);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NomorKendaraanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorKendaraanKeyPressed
        
}//GEN-LAST:event_NomorKendaraanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            //Valid.pindah(evt, btnPindah,BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInputParkir.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInputParkir.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            
        }else if(tabMode.getRowCount()!=0){

                /*String sql="select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab),"+
                   "penjab.png_jawab,kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir," +
                   "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar,"+
                   "if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,kamar_inap.ttl_biaya,kamar_inap.stts_pulang, lama,dokter.nm_dokter "+
                   "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join kelurahan inner join kecamatan inner join kabupaten inner join dokter inner join penjab " +
                   "on kamar_inap.no_rawat=reg_periksa.no_rawat " +
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                   "and reg_periksa.kd_dokter=dokter.kd_dokter " +
                   "and reg_periksa.kd_pj=penjab.kd_pj " +
                   "and kamar_inap.kd_kamar=kamar.kd_kamar " +
                   "and kamar.kd_bangsal=bangsal.kd_bangsal and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab " +
                   "where  "+key+" order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk";
                
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptKamarInap.jasper","report","::[ Data Kamar Inap Pasien ]::",sql,param);*/

        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        KdPetugas.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnCari, BtnIn);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed

}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
//        TOut.setText("");
        akses.setstatus(false);
        WindowInputParkir.dispose();
    }//GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInputParkir.dispose();
        }else{Valid.pindah(evt, NomorKendaraan, NomorKendaraan);}
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void DTPTglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTglItemStateChanged
        
    }//GEN-LAST:event_DTPTglItemStateChanged

    private void tbKamInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamInMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
            }
        }
}//GEN-LAST:event_tbKamInMouseClicked

    private void tbKamInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                i=tbKamIn.getSelectedColumn();
            }
        }
}//GEN-LAST:event_tbKamInKeyPressed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
    petugas.isCek();
    petugas.emptTeks(); 
    petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    petugas.setLocationRelativeTo(internalFrame1);
    petugas.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
//   Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_btnPetugasKeyPressed

private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",NmPetugas,KdPetugas.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,KdJenis,TCari);
        }
}//GEN-LAST:event_KdPetugasKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Barcode.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();        
    }//GEN-LAST:event_formWindowOpened

    private void WindowInputParkirWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_WindowInputParkirWindowActivated
         akses.setstatus(false);
    }//GEN-LAST:event_WindowInputParkirWindowActivated

    private void BarcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BarcodeKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(KdJenis.getText().trim().equals("")||NmJenis.getText().trim().equals("")||Tarif.getText().trim().equals("")){
                Valid.textKosong(KdJenis,"Jenis Parkir");
            }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
                Valid.textKosong(KdPetugas,"Petugas");
            }else if(Barcode.getText().trim().equals("")){
                Valid.textKosong(Barcode,"Barcode");
            }else{
                Sequel.cariIsi("select nomer_kartu from parkir_barcode where kode_barcode=?",NomorKartu,Barcode.getText());
                if(NomorKartu.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null,"Maaf, kartu tidak teridentifikasi. Silahkan cek ...!!!");
                    Barcode.requestFocus();
                }else{
                    WindowInputParkir.setVisible(true);
                    NomorKendaraan.requestFocus();
                }            
            }
        }else{
            Valid.pindah(evt,KdJenis,TCari);
        }            
    }//GEN-LAST:event_BarcodeKeyPressed

    private void KdJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdJenisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select jns_parkir from parkir_jenis where kd_parkir=?",NmJenis,KdJenis.getText());
            Sequel.cariIsi("select biaya from parkir_jenis where kd_parkir=?",Tarif,KdJenis.getText());
            Sequel.cariIsi("select jenis from parkir_jenis where kd_parkir=?",NmJenis,KdJenis.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnJenisActionPerformed(null);
        }else{
            Valid.pindah(evt,Barcode,KdPetugas);
        }
    }//GEN-LAST:event_KdJenisKeyPressed

    private void btnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisActionPerformed
        jenis.isCek();
        jenis.emptTeks();        
        jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setVisible(true);
    }//GEN-LAST:event_btnJenisActionPerformed

    private void btnJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnJenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnJenisKeyPressed

    private void NmJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmJenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmJenisKeyPressed

    private void NmJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmJenisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmJenisActionPerformed

    private void TarifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TarifKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TarifKeyPressed

    private void SIstemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SIstemKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SIstemKeyPressed

    private void NmPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmPetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugasKeyPressed

    private void NomorKartuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorKartuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomorKartuKeyPressed

    private void NomorKendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NomorKendaraanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomorKendaraanActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgParkirMasuk dialog = new DlgParkirMasuk(new javax.swing.JFrame(), true);
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
    private widget.TextBox Barcode;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox KdJenis;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.Label LCount1;
    private widget.Label LCount2;
    private widget.TextBox NmJenis;
    private widget.TextBox NmPetugas;
    private widget.TextBox NomorKartu;
    private widget.TextBox NomorKendaraan;
    private javax.swing.JPanel PanelCariUtama;
    private widget.TextBox SIstem;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox Tarif;
    private javax.swing.JDialog WindowInputParkir;
    private widget.Button btnJenis;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel25;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass12;
    private widget.Table tbKamIn;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        
        Valid.tabelKosong(tabMode);
        try{
            /*if(stts_pulang='Pindah Kamar',(IFNULL(to_days(concat(tgl_keluar,' ',jam_keluar))-to_days(concat(tgl_masuk,' ',jam_masuk)),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))),"+
                   "(IFNULL(to_days(concat(tgl_keluar,' ',jam_keluar))-to_days(concat(tgl_masuk,' ',jam_masuk)),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))+1)) as */
            /*rs=koneksi.prepareStatement(
                   "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab),"+
                   "penjab.png_jawab,concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal),kamar.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir," +
                   "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar),"+
                   "if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar),kamar_inap.ttl_biaya,kamar_inap.stts_pulang, lama,dokter.nm_dokter "+
                   "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join kelurahan inner join kecamatan inner join kabupaten inner join dokter inner join penjab " +
                   "on kamar_inap.no_rawat=reg_periksa.no_rawat " +
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                   "and reg_periksa.kd_dokter=dokter.kd_dokter " +
                   "and reg_periksa.kd_pj=penjab.kd_pj " +
                   "and kamar_inap.kd_kamar=kamar.kd_kamar " +
                   "and kamar.kd_bangsal=bangsal.kd_bangsal and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab " +
                   "where  "+key+" order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk").executeQuery();
            while(rs.next()){
                tabMode.addRow(new String[]{rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               Valid.SetAngka(rs.getDouble(7)),
                               rs.getString(8),
                               rs.getString(9),
                               rs.getString(10),
                               rs.getString(11),
                               rs.getString(12),
                               rs.getString(13),
                               Valid.SetAngka(rs.getDouble(14)),
                               rs.getString(15),
                               rs.getString(16),
                               rs.getString(17)});
                psanak.setString(1,rs.getString(1));
                rs2=psanak.executeQuery();
                if(rs2.next()){
                    tabMode.addRow(new String[]{"",
                                    rs2.getString("no_rkm_medis"),
                                    rs2.getString("nm_pasien"),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    "0","-","-",
                                    rs.getString(10),
                                    rs.getString(11),
                                    rs.getString(12),
                                    rs.getString(13),
                                    "0",
                                    rs.getString(15),
                                    rs.getString(16),
                                    rs.getString(17)});
                }
                
                
            }
            //rs.close();*/
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {       
        NomorKendaraan.setText("");
        NomorKendaraan.requestFocus();
    }

    private void getData() {
        if(tbKamIn.getSelectedRow()!= -1){
            NomorKendaraan.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString());
        }
    }

    
   

    
    
    public void isCek(){
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            NmPetugas.setEnabled(false);
            btnPetugas.setEnabled(false);
            BtnHapus.setEnabled(akses.getparkir_in());
            BtnPrint.setEnabled(akses.getparkir_in());
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?",NmPetugas,KdPetugas.getText());
        }else{
            KdPetugas.setEditable(true);
            NmPetugas.setEnabled(true);
            KdPetugas.setText("");
            NmPetugas.setText("");
            btnPetugas.setEnabled(true);
        } 
        
   }
    
    private void updateHari(){
        tampil();
    }

}

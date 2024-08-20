package permintaan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariPerusahaan;
import simrskhanza.DlgCariPoli;
import fungsi.sekuel;

public class DlgCariBookingMCUPerusahaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private PreparedStatement ps;
    private ResultSet rs;
    private Connection koneksi=koneksiDB.condb();
    private int i=0;
    private DlgCariPerusahaan perusahaan=new DlgCariPerusahaan(null,false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String pilihan="";
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariBookingMCUPerusahaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","No.RM","Nama Pasien","JK","Umur","No.HP","No.KTP","No.Pegawai","No.Booking","Tgl.Booking","Jam Booking","Status MCU","Kode Instansi","Nama Instansi",
                "No.Pelayanan","No.Urut","Kode Dokter","Nama Dokter MCU","Kode Unit","Nama Unit/Poli MCU","Kode Bayar","Cara/Jenis Bayar","Stts Daftar","Stts Poli"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(25);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(110);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(160);
            }else if(i==14){
                column.setPreferredWidth(105);
            }else if(i==15){
                column.setPreferredWidth(47);
            }else if(i==16){
                column.setPreferredWidth(90);
            }else if(i==17){
                column.setPreferredWidth(160);
            }else if(i==18){
                column.setPreferredWidth(60);
            }else if(i==19){
                column.setPreferredWidth(140);
            }else if(i==20){
                column.setPreferredWidth(65);
            }else if(i==21){
                column.setPreferredWidth(130);
            }else if(i==22){
                column.setPreferredWidth(60);
            }else if(i==23){
                column.setPreferredWidth(55);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

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
        
        perusahaan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(perusahaan.getTable().getSelectedRow()!= -1){
                    KdPerusahaan.setText(perusahaan.getTable().getValueAt(perusahaan.getTable().getSelectedRow(),0).toString());
                    NmPerusahaan.setText(perusahaan.getTable().getValueAt(perusahaan.getTable().getSelectedRow(),1).toString());
                    BtnPerusahaan.requestFocus();
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
        
        perusahaan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    perusahaan.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    BtnPoli.requestFocus();
                }      
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {poli.emptTeks();}
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
                    BtnDokter.requestFocus();
                }      
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {dokter.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dokter.dispose();
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

        Popup1 = new javax.swing.JPopupMenu();
        ppPilih = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        ppBatalMCU = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        KdDokter = new widget.TextBox();
        KdPerusahaan = new widget.TextBox();
        NmDokter = new widget.TextBox();
        NmPerusahaan = new widget.TextBox();
        BtnDokter = new widget.Button();
        BtnPerusahaan = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label25 = new widget.Label();
        KdPoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        BtnPoli = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
        LCount = new widget.Label();
        BtnMenunggu = new widget.Button();
        BtnSelesai = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Popup1.setName("Popup1"); // NOI18N

        ppPilih.setBackground(new java.awt.Color(255, 255, 254));
        ppPilih.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilih.setForeground(new java.awt.Color(50, 50, 50));
        ppPilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPilih.setText("Pilih Semua");
        ppPilih.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilih.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilih.setName("ppPilih"); // NOI18N
        ppPilih.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihActionPerformed(evt);
            }
        });
        Popup1.add(ppPilih);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
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
        Popup1.add(ppBersihkan);

        ppBatalMCU.setBackground(new java.awt.Color(255, 255, 254));
        ppBatalMCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBatalMCU.setForeground(new java.awt.Color(50, 50, 50));
        ppBatalMCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBatalMCU.setText("Batalkan Pendaftaran MCU");
        ppBatalMCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBatalMCU.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBatalMCU.setName("ppBatalMCU"); // NOI18N
        ppBatalMCU.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBatalMCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBatalMCUActionPerformed(evt);
            }
        });
        Popup1.add(ppBatalMCU);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Booking MCU Perusahaan/Instansi Yang Sudah Tervalidasi Di Registrasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label11.setText("Tanggal MCU :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 90, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(94, 40, 100, 23);

        label16.setText("Dokter MCU :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(335, 10, 110, 23);

        label13.setText("Instansi MCU :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(345, 40, 100, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(KdDokter);
        KdDokter.setBounds(449, 10, 110, 23);

        KdPerusahaan.setEditable(false);
        KdPerusahaan.setName("KdPerusahaan"); // NOI18N
        KdPerusahaan.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(KdPerusahaan);
        KdPerusahaan.setBounds(449, 40, 80, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmDokter);
        NmDokter.setBounds(561, 10, 215, 23);

        NmPerusahaan.setEditable(false);
        NmPerusahaan.setName("NmPerusahaan"); // NOI18N
        NmPerusahaan.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmPerusahaan);
        NmPerusahaan.setBounds(531, 40, 245, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('1');
        BtnDokter.setToolTipText("Alt+1");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        panelisi3.add(BtnDokter);
        BtnDokter.setBounds(779, 10, 28, 23);

        BtnPerusahaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPerusahaan.setMnemonic('2');
        BtnPerusahaan.setToolTipText("Alt+2");
        BtnPerusahaan.setName("BtnPerusahaan"); // NOI18N
        BtnPerusahaan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerusahaanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnPerusahaan);
        BtnPerusahaan.setBounds(779, 40, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(197, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(230, 40, 100, 23);

        label25.setText("Unit/Poli MCU :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label25);
        label25.setBounds(0, 10, 90, 23);

        KdPoli.setEditable(false);
        KdPoli.setName("KdPoli"); // NOI18N
        KdPoli.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(KdPoli);
        KdPoli.setBounds(94, 10, 53, 23);

        NmPoli.setEditable(false);
        NmPoli.setName("NmPoli"); // NOI18N
        NmPoli.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(NmPoli);
        NmPoli.setBounds(149, 10, 150, 23);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli.setMnemonic('3');
        BtnPoli.setToolTipText("Alt+3");
        BtnPoli.setName("BtnPoli"); // NOI18N
        BtnPoli.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliActionPerformed(evt);
            }
        });
        panelisi3.add(BtnPoli);
        BtnPoli.setBounds(302, 10, 28, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        scrollPane1.setComponentPopupMenu(Popup1);
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
        tbDokter.setComponentPopupMenu(Popup1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(130, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelisi1.add(BtnCari);

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
        panelisi1.add(BtnAll);

        label9.setText("Record :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(label9);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(LCount);

        BtnMenunggu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/19.png"))); // NOI18N
        BtnMenunggu.setMnemonic('M');
        BtnMenunggu.setText("Menunggu");
        BtnMenunggu.setToolTipText("Alt+M");
        BtnMenunggu.setName("BtnMenunggu"); // NOI18N
        BtnMenunggu.setPreferredSize(new java.awt.Dimension(110, 30));
        BtnMenunggu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenungguActionPerformed(evt);
            }
        });
        BtnMenunggu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnMenungguKeyPressed(evt);
            }
        });
        panelisi1.add(BtnMenunggu);

        BtnSelesai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/lock.png"))); // NOI18N
        BtnSelesai.setMnemonic('S');
        BtnSelesai.setText("Selesai");
        BtnSelesai.setToolTipText("Alt+S");
        BtnSelesai.setName("BtnSelesai"); // NOI18N
        BtnSelesai.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSelesaiActionPerformed(evt);
            }
        });
        BtnSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSelesaiKeyPressed(evt);
            }
        });
        panelisi1.add(BtnSelesai);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerusahaanActionPerformed
        perusahaan.isCek();
        perusahaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        perusahaan.setLocationRelativeTo(internalFrame1);
        perusahaan.setVisible(true);
    }//GEN-LAST:event_BtnPerusahaanActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,KdDokter,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,KdPerusahaan);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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
        KdPoli.setText("");
        NmPoli.setText("");
        KdDokter.setText("");
        NmDokter.setText("");
        KdPerusahaan.setText("");
        NmPerusahaan.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnMenunggu);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnPrint.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {            
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw; 

                pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.RM</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Pasien</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>JK</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Umur</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.HP</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.KTP</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Pegawai</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Booking</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Tgl.Booking</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Jam Booking</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Status MCU</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Instansi</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Instansi</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Pelayanan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Urut</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Dokter</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Dokter MCU</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Unit</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Unit/Poli MCU</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Bayar</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Cara/Jenis Bayar</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stts Daftar</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stts Poli</td>"+
                                "</tr>"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,1)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,2)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,3)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,4)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,5)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,6)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,7)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,8)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,9)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,10)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,11)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,12)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,13)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,14)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,15)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,16)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,17)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,18)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,19)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,20)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,21)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,22)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,23)+"</td>"+
                                    "</tr>"
                                ); 
                            }            

                            f = new File("LaporanMCUPerusahaan.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>LAPORAN MCU PERUSAHAAN PERIODE "+Tgl1.getSelectedItem()+" S.D. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.RM</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Pasien</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>JK</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Umur</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.HP</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.KTP</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Pegawai</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Booking</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Tgl.Booking</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Jam Booking</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Status MCU</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Instansi</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Instansi</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Pelayanan</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>No.Urut</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Dokter</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Dokter MCU</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Unit</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Nama Unit/Poli MCU</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Kode Bayar</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Cara/Jenis Bayar</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stts Daftar</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'>Stts Poli</td>"+
                                "</tr>"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,1)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,2)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,3)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,4)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,5)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,6)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,7)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,8)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,9)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,10)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,11)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,12)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,13)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,14)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,15)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,16)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,17)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,18)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,19)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,20)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,21)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,22)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,23)+"</td>"+
                                    "</tr>"
                                ); 
                            }            

                            f = new File("LaporanMCUPerusahaan.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>LAPORAN MCU PERUSAHAAN PERIODE "+Tgl1.getSelectedItem()+" S.D. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.RM\";\"Nama Pasien\";\"JK\";\"Umur\";\"No.HP\";\"No.KTP\";\"No.Pegawai\";\"No.Booking\";\"Tgl.Booking\";\"Jam Booking\";\"Status MCU\";\"Kode Instansi\";\"Nama Instansi\";\"No.Pelayanan\";\"No.Urut\";\"Kode Dokter\";\"Nama Dokter MCU\";\"Kode Unit\";\"Nama Unit/Poli MCU\";\"Kode Bayar\";\"Cara/Jenis Bayar\";\"Stts Daftar\";\"Stts Poli\"\n"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "\""+tabMode.getValueAt(i,1)+"\";\""+tabMode.getValueAt(i,2)+"\";\""+tabMode.getValueAt(i,3)+"\";\""+tabMode.getValueAt(i,4)+"\";\""+tabMode.getValueAt(i,5)+"\";\""+tabMode.getValueAt(i,6)+"\";\""+tabMode.getValueAt(i,7)+"\";\""+tabMode.getValueAt(i,8)+"\";\""+tabMode.getValueAt(i,9)+"\";\""+tabMode.getValueAt(i,10)+"\";\""+tabMode.getValueAt(i,11)+"\";\""+tabMode.getValueAt(i,12)+"\";\""+tabMode.getValueAt(i,13)+"\";\""+tabMode.getValueAt(i,14)+"\";\""+tabMode.getValueAt(i,15)+"\";\""+tabMode.getValueAt(i,16)+"\";\""+tabMode.getValueAt(i,17)+"\";\""+tabMode.getValueAt(i,18)+"\";\""+tabMode.getValueAt(i,19)+"\";\""+tabMode.getValueAt(i,20)+"\";\""+tabMode.getValueAt(i,21)+"\";\""+tabMode.getValueAt(i,22)+"\";\""+tabMode.getValueAt(i,23)+"\"\n"
                                ); 
                            }            

                            f = new File("LaporanMCUPerusahaan.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                }                 
            } catch (Exception e) {
            }     
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSelesai,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dokter.dispose();
        poli.dispose();
        perusahaan.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnPoliActionPerformed

    private void BtnSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSelesaiActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){ 
            if(tbDokter.getValueAt(i,0).toString().equals("true")){
                Sequel.mengedit3("booking_mcu_perusahaan","no_mcu=?","status='Selesai'",1,new String[]{tbDokter.getValueAt(i,8).toString()});
                tbDokter.setValueAt("Selesai",i, 11);
            }
        }
    }//GEN-LAST:event_BtnSelesaiActionPerformed

    private void BtnSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSelesaiKeyPressed
        Valid.pindah(evt,BtnMenunggu,BtnPrint);
    }//GEN-LAST:event_BtnSelesaiKeyPressed

    private void BtnMenungguActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenungguActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){ 
            if(tbDokter.getValueAt(i,0).toString().equals("true")){
                Sequel.mengedit3("booking_mcu_perusahaan","no_mcu=?","status='Menunggu Hasil'",1,new String[]{tbDokter.getValueAt(i,8).toString()});
                tbDokter.setValueAt("Menunggu Hasil",i, 11);
            }
        }
    }//GEN-LAST:event_BtnMenungguActionPerformed

    private void BtnMenungguKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnMenungguKeyPressed
        Valid.pindah(evt, BtnAll, BtnSelesai);
    }//GEN-LAST:event_BtnMenungguKeyPressed

    private void ppPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){
            tbDokter.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppPilihActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){
            tbDokter.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppBatalMCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBatalMCUActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){ 
            if(tbDokter.getValueAt(i,0).toString().equals("true")){
                if(Sequel.meghapustf("reg_periksa","no_rawat",tbDokter.getValueAt(i,14).toString())==true){
                    Sequel.mengedit2("booking_mcu_perusahaan","no_mcu=?","status='Terdaftar'",1,new String[]{tbDokter.getValueAt(i,8).toString()});
                    tabMode.removeRow(i);
                    i--;
                }  
            }
        }
        LCount.setText(""+tabMode.getRowCount());
    }//GEN-LAST:event_ppBatalMCUActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariBookingMCUPerusahaan dialog = new DlgCariBookingMCUPerusahaan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnDokter;
    private widget.Button BtnKeluar;
    private widget.Button BtnMenunggu;
    private widget.Button BtnPerusahaan;
    private widget.Button BtnPoli;
    private widget.Button BtnPrint;
    private widget.Button BtnSelesai;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPerusahaan;
    private widget.TextBox KdPoli;
    private widget.Label LCount;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPerusahaan;
    private widget.TextBox NmPoli;
    private javax.swing.JPopupMenu Popup1;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label25;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBatalMCU;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPilih;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(NmPoli.getText().trim().equals("")&&NmDokter.getText().trim().equals("")&&NmPerusahaan.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_tlp,pasien.no_ktp,pasien.nip,"+
                        "booking_mcu_perusahaan.no_mcu,booking_mcu_perusahaan.tanggal_booking,booking_mcu_perusahaan.jam_booking,booking_mcu_perusahaan.status as statusmcu," +
                        "booking_mcu_perusahaan.kode_perusahaan,perusahaan_pasien.nama_perusahaan,booking_mcu_perusahaan_berhasil_registrasi.no_rawat,reg_periksa.no_reg,"+
                        "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.kd_poli,poliklinik.nm_poli,reg_periksa.kd_pj,penjab.png_jawab,reg_periksa.stts_daftar,reg_periksa.status_poli "+
                        "from pasien inner join booking_mcu_perusahaan on booking_mcu_perusahaan.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join perusahaan_pasien on booking_mcu_perusahaan.kode_perusahaan=perusahaan_pasien.kode_perusahaan "+
                        "inner join booking_mcu_perusahaan_berhasil_registrasi on booking_mcu_perusahaan_berhasil_registrasi.no_mcu=booking_mcu_perusahaan.no_mcu "+
                        "inner join reg_periksa on reg_periksa.no_rawat=booking_mcu_perusahaan_berhasil_registrasi.no_rawat "+
                        "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                        "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "where booking_mcu_perusahaan.tanggal_mcu between ? and ? order by booking_mcu_perusahaan.tanggal_mcu,booking_mcu_perusahaan.no_mcu");
            }else{
                ps=koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_tlp,pasien.no_ktp,pasien.nip,"+
                        "booking_mcu_perusahaan.no_mcu,booking_mcu_perusahaan.tanggal_booking,booking_mcu_perusahaan.jam_booking,booking_mcu_perusahaan.status as statusmcu," +
                        "booking_mcu_perusahaan.kode_perusahaan,perusahaan_pasien.nama_perusahaan,booking_mcu_perusahaan_berhasil_registrasi.no_rawat,reg_periksa.no_reg,"+
                        "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.kd_poli,poliklinik.nm_poli,reg_periksa.kd_pj,penjab.png_jawab,reg_periksa.stts_daftar,reg_periksa.status_poli "+
                        "from pasien inner join booking_mcu_perusahaan on booking_mcu_perusahaan.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join perusahaan_pasien on booking_mcu_perusahaan.kode_perusahaan=perusahaan_pasien.kode_perusahaan "+
                        "inner join booking_mcu_perusahaan_berhasil_registrasi on booking_mcu_perusahaan_berhasil_registrasi.no_mcu=booking_mcu_perusahaan.no_mcu "+
                        "inner join reg_periksa on reg_periksa.no_rawat=booking_mcu_perusahaan_berhasil_registrasi.no_rawat "+
                        "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                        "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "where booking_mcu_perusahaan.tanggal_mcu between ? and ? and booking_mcu_perusahaan.kode_perusahaan like ? and reg_periksa.kd_poli like ? "+
                        "and reg_periksa.kd_dokter like ? and (pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or pasien.no_ktp like ? "+
                        "or pasien.nip like ? or booking_mcu_perusahaan.no_mcu like ? or booking_mcu_perusahaan.status like ?) order by booking_mcu_perusahaan.tanggal_mcu,booking_mcu_perusahaan.no_mcu");
            }
                
            try {
                if(NmPoli.getText().trim().equals("")&&NmDokter.getText().trim().equals("")&&NmPerusahaan.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdPerusahaan.getText()+"%");
                    ps.setString(4,"%"+KdPoli.getText()+"%");
                    ps.setString(5,"%"+KdDokter.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("no_tlp"),
                        rs.getString("no_ktp"),rs.getString("nip"),rs.getString("no_mcu"),rs.getString("tanggal_booking"),rs.getString("jam_booking"),rs.getString("statusmcu"),
                        rs.getString("kode_perusahaan"),rs.getString("nama_perusahaan"),rs.getString("no_rawat"),rs.getString("no_reg"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),
                        rs.getString("kd_poli"),rs.getString("nm_poli"),rs.getString("kd_pj"),rs.getString("png_jawab"),rs.getString("stts_daftar"),rs.getString("status_poli")
                    });
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
            LCount.setText(""+tabMode.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }   
    }
    
    public void emptTeks() {
               
    }   
    
    public void isCek(){
        BtnSelesai.setEnabled(akses.getbooking_mcu_perusahaan());
        BtnMenunggu.setEnabled(akses.getbooking_mcu_perusahaan());
        BtnPrint.setEnabled(akses.getbooking_mcu_perusahaan());
        if(akses.getkode().equals("Admin Utama")){
            ppBatalMCU.setEnabled(true);
        }else{
            ppBatalMCU.setEnabled(false);
        } 
    }
}

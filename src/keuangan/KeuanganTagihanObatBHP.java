

package keuangan;

import fungsi.WarnaTable3;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import inventory.InventoryCariSuplier;
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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class KeuanganTagihanObatBHP extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private InventoryCariSuplier suplier=new InventoryCariSuplier(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private int row=0,i=0;
    private String tanggaldatang="",tanggaltempo="";
    private double sisahutang=0,cicilan=0,bayar=0;
    private WarnaTable3 warna=new WarnaTable3();
    private boolean sukses=true;    
    

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganTagihanObatBHP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        Object[] rowRwJlDr={
            "P","No.Faktur","No.Order","Supplier","Petugas Penerima",
            "Tgl.Faktur","Tgl.Datang","Tgl.Tempo","Posisi Barang","Tagihan",
            "Sisa Hutang"
        };
        tabMode=new DefaultTableModel(null,rowRwJlDr){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Double.class,java.lang.Double.class
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

        for (int i = 0; i < 11; i++) {
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
            }else{
                column.setPreferredWidth(80);
            }
        }
        warna.kolom=11;
        tbBangsal.setDefaultRenderer(Object.class,warna);

        NoTagihan.setDocument(new batasInput((byte)20).getKata(NoTagihan));
        nip.setDocument(new batasInput((byte)20).getKata(nip));
        Keterangan.setDocument(new batasInput((int)150).getKata(Keterangan));        
        
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
        
        ChkAccor.setSelected(false);
        isPhoto();
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);

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
        BtnSuplier = new widget.Button();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LCount1 = new javax.swing.JLabel();
        BtnSimpan = new widget.Button();
        BtnCari1 = new widget.Button();
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
        NoTagihan = new widget.TextBox();
        label16 = new widget.Label();
        nip = new widget.TextBox();
        nama_petugas = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        BtnPetugas = new widget.Button();
        Keterangan = new widget.TextBox();
        label39 = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        Popup.setName("Popup"); // NOI18N

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
        Popup.add(ppBersihkan);

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(50, 50, 50));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Titip Faktur/Tagihan Obat & BHP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        BtnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSuplier.setMnemonic('3');
        BtnSuplier.setToolTipText("Alt+3");
        BtnSuplier.setName("BtnSuplier"); // NOI18N
        BtnSuplier.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSuplierActionPerformed(evt);
            }
        });
        BtnSuplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSuplierKeyPressed(evt);
            }
        });
        panelisi3.add(BtnSuplier);

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
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total Hutang :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(76, 23));
        panelisi1.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(140, 23));
        panelisi1.add(LCount);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(50, 50, 50));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Ditagihkan :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(76, 23));
        panelisi1.add(jLabel11);

        LCount1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount1.setForeground(new java.awt.Color(50, 50, 50));
        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(125, 23));
        panelisi1.add(LCount1);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('B');
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
        panelisi1.add(BtnCari1);

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

        ChkTanggalTempo.setText("Tgl.Tempo :");
        ChkTanggalTempo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkTanggalTempo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkTanggalTempo.setName("ChkTanggalTempo"); // NOI18N
        ChkTanggalTempo.setOpaque(false);
        ChkTanggalTempo.setPreferredSize(new java.awt.Dimension(89, 23));
        panelisi5.add(ChkTanggalTempo);

        TglTempo1.setDisplayFormat("dd-MM-yyyy");
        TglTempo1.setName("TglTempo1"); // NOI18N
        TglTempo1.setPreferredSize(new java.awt.Dimension(97, 23));
        panelisi5.add(TglTempo1);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label20.setText("s.d.");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi5.add(label20);

        TglTempo2.setDisplayFormat("dd-MM-yyyy");
        TglTempo2.setName("TglTempo2"); // NOI18N
        TglTempo2.setPreferredSize(new java.awt.Dimension(97, 23));
        panelisi5.add(TglTempo2);

        jPanel1.add(panelisi5, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 74));
        panelisi4.setLayout(null);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(197, 10, 60, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(0, 40, 75, 23);

        NoTagihan.setHighlighter(null);
        NoTagihan.setName("NoTagihan"); // NOI18N
        NoTagihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTagihanKeyPressed(evt);
            }
        });
        panelisi4.add(NoTagihan);
        NoTagihan.setBounds(78, 10, 120, 23);

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

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);
        Tanggal.setBounds(260, 10, 90, 23);

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

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi4.add(Keterangan);
        Keterangan.setBounds(78, 40, 672, 23);

        label39.setText("No.Tagihan :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(0, 10, 75, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Photo Faktur : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            Valid.pindah(evt, BtnCari1, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            if(kdsup.getText().trim().equals("")||nmsup.getText().trim().equals("")){
                tbBangsal.setValueAt(false,tbBangsal.getSelectedRow(),0);
                Valid.textKosong(BtnSuplier,"Suplier");
            }else{
                if(tbBangsal.getSelectedRow()!= -1){
                    if(evt.getClickCount()==1){
                        if(tbBangsal.getSelectedColumn()==0){
                            getData();
                        }
                        panggilPhoto();
                    }    
                }
            }
        }
}//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if(tbBangsal.getSelectedRow()!= -1){
                if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                    getData();
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
        NoTagihan.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSuplierActionPerformed(null);
        }
    }//GEN-LAST:event_kdsupKeyPressed

    private void BtnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSuplierActionPerformed
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_BtnSuplierActionPerformed

    private void BtnSuplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSuplierKeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSuplierKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoTagihan.getText().trim().equals("")){
            Valid.textKosong(NoTagihan,"No.Tagihan");
        }else if(nip.getText().trim().equals("")||nama_petugas.getText().trim().equals("")){
            Valid.textKosong(nip,"Petugas");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda simpan...!!!!");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(bayar<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan tagihan...!!!!");
            tbBangsal.requestFocus();
        }else{
            i = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("titip_faktur","?,?,?,?,'Ditagihkan'","No.Tagihan",4,new String[]{
                    NoTagihan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),nip.getText(),Keterangan.getText()
                })==true){
                    row=tbBangsal.getRowCount();
                    for(i=0;i<row;i++){
                        if(tabMode.getValueAt(i,0).toString().equals("true")){
                            if(Sequel.menyimpantf2("detail_titip_faktur","?,?","Detail Tagihan",2,new String[]{
                                NoTagihan.getText(),tbBangsal.getValueAt(i,1).toString(),
                            })==false){
                                sukses=false;
                            }else{
                                tabMode.setValueAt(false,i,0);
                            }
                        }
                    }
                    bayar=0;
                    LCount1.setText("0");
                }else{
                    sukses=false;
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan No.Tagihan sudah ada sebelumnya...!!");
                }
                if(sukses==true){
                    autoNomor();
                    Sequel.Commit();
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt,TCari,BtnCari1);
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void NoTagihanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTagihanKeyPressed
       Valid.pindah(evt,TCari,Tanggal);
    }//GEN-LAST:event_NoTagihanKeyPressed

    private void nipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_nipKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NoTagihan,Keterangan);
    }//GEN-LAST:event_TanggalKeyPressed

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

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,nip,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

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

    private void TglDatang1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglDatang1KeyPressed
        Valid.pindah(evt, BtnKeluar, TglDatang2);
    }//GEN-LAST:event_TglDatang1KeyPressed

    private void TglDatang2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglDatang2KeyPressed
        Valid.pindah(evt, TglDatang1,TCari);
    }//GEN-LAST:event_TglDatang2KeyPressed

    private void tbBangsalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbBangsalPropertyChange
        if(this.isVisible()==true){
            if(tbBangsal.getSelectedRow()!= -1){
                getData();
            }
        }
    }//GEN-LAST:event_tbBangsalPropertyChange

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbBangsal.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Faktur Penerimaan...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        KeuanganCariTagihanObatBHP form=new KeuanganCariTagihanObatBHP(null,false);
        form.emptTeks();
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        Valid.pindah(evt,BtnSimpan,BtnKeluar);
    }//GEN-LAST:event_BtnCari1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganTagihanObatBHP dialog = new KeuanganTagihanObatBHP(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnSimpan;
    private widget.Button BtnSuplier;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkTanggalDatang;
    private widget.CekBox ChkTanggalTempo;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox Keterangan;
    private javax.swing.JLabel LCount;
    private javax.swing.JLabel LCount1;
    private widget.editorpane LoadHTML;
    private widget.TextBox NoTagihan;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TglDatang1;
    private widget.Tanggal TglDatang2;
    private widget.Tanggal TglTempo1;
    private widget.Tanggal TglTempo2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdsup;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label32;
    private widget.Label label36;
    private widget.Label label39;
    private widget.TextBox nama_petugas;
    private widget.TextBox nip;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSemua;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            tanggaldatang="";
            tanggaltempo="";
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
                        rs.getDouble("tagihan"),(rs.getDouble("tagihan")-rs.getDouble("bayar"))
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
    
    private void getData(){
        row=tbBangsal.getRowCount();
        bayar=0;
        for(i=0;i<row;i++){  
            if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                 bayar=bayar+Double.parseDouble(tbBangsal.getValueAt(i,10).toString());     
            }
        }
        LCount1.setText(Valid.SetAngka(bayar));
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(internalFrame1.getWidth()-300,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }
    
    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select photo from bukti_pemesanan where no_faktur=?");
                try {
                    ps.setString(1,tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penerimaanapotek/"+rs.getString("photo")+"' alt='photo' width='"+(internalFrame1.getWidth()-340)+"' height='"+(internalFrame1.getHeight()-275)+"'/></center></body></html>");
                        }  
                    }else{
                        LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_tagihan,3),signed)),0) from titip_faktur where tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "TH"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoTagihan); 
    }
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            nip.setEditable(false);
            BtnPetugas.setEnabled(false);
            nip.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.gettagihan_hutang_obat());
            Sequel.cariIsi("select nama from petugas where nip=?",nama_petugas,nip.getText());
        }        
    }
}

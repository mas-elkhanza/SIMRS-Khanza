/*
  Dilarang keras memperjualbelikan/mengambil keuntungan dari Software 
  ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import keuangan.KeuanganBayarPemesananFarmasi;
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;

public class DlgCariPemesanan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private riwayatobat Trackobat=new riwayatobat();
    private Connection koneksi=koneksiDB.condb();
    public  InventoryCariSuplier suplier=new InventoryCariSuplier(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgBarang barang=new DlgBarang(null,false);
    private PreparedStatement ps,ps2,pscaripesan,psdetailpesan;
    private ResultSet rs,rs2;
    private double tagihan=0;
    private Jurnal jur=new Jurnal();
    private String aktifkanbatch="no",tanggal= "",datanofaktur="",datasuplier="",datapetugas="",datajenis="",databarang="",dataindustri="",datacari="",statusbayar="";
    private boolean sukses=true;
    private int i=0;
    private StringBuilder htmlContent;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPemesanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
        }
        
        Object[] row={"No.Faktur","Suplier","Petugas","Barang",
                    "Satuan","Jml","Harga(Rp)","SubTotal(Rp)",
                    "Disk(%)","Bsr.Disk(Rp)","Total(Rp)"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoFaktur.setDocument(new batasInput((byte)25).getKata(NoFaktur));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        KdIF.setDocument(new batasInput((byte)15).getKata(KdIF));
        kdjenis.setDocument(new batasInput((byte)4).getKata(kdjenis));
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
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(suplier.getTable().getSelectedRow()!= -1){                   
                        kdsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),0).toString());                    
                        nmsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
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
        
        suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        suplier.dispose();
                    }    
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        barang.jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(barang.jenis.getTable().getSelectedRow()!= -1){                          
                        kdjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),0).toString());                    
                        nmjenis.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),1).toString());                        
                    }                    
                    kdjenis.requestFocus();
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
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
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                        nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                    }   
                    kdbar.requestFocus();
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
        
        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();
                    }
                }                                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        barang.industri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(barang.industri.getTable().getSelectedRow()!= -1){                   
                        KdIF.setText(barang.industri.getTable().getValueAt(barang.industri.getTable().getSelectedRow(),0).toString());                    
                        NmIF.setText(barang.industri.getTable().getValueAt(barang.industri.getTable().getSelectedRow(),1).toString());                                        
                    }   
                    KdIF.requestFocus();
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
        
        barang.industri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariPemesanan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.industri.dispose();
                    }      
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        ChkAccor.setSelected(false);
        isPhoto();
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
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
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"+
                ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
        );
        
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML2.setDocument(doc);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppHapus = new javax.swing.JMenuItem();
        ppBayar = new javax.swing.JMenuItem();
        ppUbah = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label24 = new widget.Label();
        kdjenis = new widget.TextBox();
        btnSatuan = new widget.Button();
        nmjenis = new widget.TextBox();
        label21 = new widget.Label();
        Status = new widget.ComboBox();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoFaktur = new widget.TextBox();
        TglBeli1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdsup = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmsup = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label12 = new widget.Label();
        TglBeli2 = new widget.Tanggal();
        label14 = new widget.Label();
        KdIF = new widget.TextBox();
        NmIF = new widget.TextBox();
        btnIF = new widget.Button();
        RDatang = new widget.RadioButton();
        RFaktur = new widget.RadioButton();
        TglFaktur1 = new widget.Tanggal();
        TglFaktur2 = new widget.Tanggal();
        label18 = new widget.Label();
        label19 = new widget.Label();
        label20 = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        btnAmbilPhoto = new widget.Button();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        TabRawat = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        Scroll = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50, 50, 50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Data Penerimaan");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(195, 26));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        ppBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBayar.setText("Bayar Tagihan");
        ppBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBayar.setName("ppBayar"); // NOI18N
        ppBayar.setPreferredSize(new java.awt.Dimension(195, 26));
        ppBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBayarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBayar);

        ppUbah.setBackground(new java.awt.Color(255, 255, 254));
        ppUbah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUbah.setForeground(new java.awt.Color(50, 50, 50));
        ppUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUbah.setText("Ubah Penerimaan");
        ppUbah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUbah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUbah.setName("ppUbah"); // NOI18N
        ppUbah.setPreferredSize(new java.awt.Dimension(195, 26));
        ppUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUbahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppUbah);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Penerimaan Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
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

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(155, 30));
        panelisi1.add(LTotal);

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
        panelisi1.add(BtnAll);

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

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(445, 10, 50, 23);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);
        kdbar.setBounds(499, 10, 100, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(601, 10, 170, 23);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang.setMnemonic('4');
        btnBarang.setToolTipText("Alt+4");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        panelisi4.add(btnBarang);
        btnBarang.setBounds(774, 10, 28, 23);

        label24.setText("Jenis :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(48, 23));
        panelisi4.add(label24);
        label24.setBounds(183, 10, 45, 23);

        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });
        panelisi4.add(kdjenis);
        kdjenis.setBounds(232, 10, 53, 23);

        btnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSatuan.setMnemonic('3');
        btnSatuan.setToolTipText("Alt+3");
        btnSatuan.setName("btnSatuan"); // NOI18N
        btnSatuan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatuanActionPerformed(evt);
            }
        });
        panelisi4.add(btnSatuan);
        btnSatuan.setBounds(406, 10, 28, 23);

        nmjenis.setName("nmjenis"); // NOI18N
        nmjenis.setPreferredSize(new java.awt.Dimension(80, 23));
        nmjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmjenisKeyPressed(evt);
            }
        });
        panelisi4.add(nmjenis);
        nmjenis.setBounds(287, 10, 116, 23);

        label21.setText("Status :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label21);
        label21.setBounds(0, 10, 53, 23);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Dibayar", "Belum Dibayar", "Belum Lunas", "Titip Faktur" }));
        Status.setName("Status"); // NOI18N
        Status.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi4.add(Status);
        Status.setBounds(58, 10, 120, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label15.setText("No.Faktur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi3.add(NoFaktur);
        NoFaktur.setBounds(74, 10, 229, 23);

        TglBeli1.setDisplayFormat("dd-MM-yyyy");
        TglBeli1.setName("TglBeli1"); // NOI18N
        TglBeli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli1KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli1);
        TglBeli1.setBounds(96, 40, 90, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(325, 10, 100, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(325, 40, 100, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi3.add(kdsup);
        kdsup.setBounds(429, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(429, 40, 80, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmsup);
        nmsup.setBounds(511, 10, 260, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(511, 40, 260, 23);

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
        btnSuplier.setBounds(774, 10, 28, 23);

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
        btnPetugas.setBounds(774, 40, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(186, 40, 25, 23);

        TglBeli2.setDisplayFormat("dd-MM-yyyy");
        TglBeli2.setName("TglBeli2"); // NOI18N
        TglBeli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBeli2KeyPressed(evt);
            }
        });
        panelisi3.add(TglBeli2);
        TglBeli2.setBounds(213, 40, 90, 23);

        label14.setText("Industri Farmasi :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(325, 70, 100, 23);

        KdIF.setName("KdIF"); // NOI18N
        KdIF.setPreferredSize(new java.awt.Dimension(80, 23));
        KdIF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdIFKeyPressed(evt);
            }
        });
        panelisi3.add(KdIF);
        KdIF.setBounds(429, 70, 80, 23);

        NmIF.setEditable(false);
        NmIF.setName("NmIF"); // NOI18N
        NmIF.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmIF);
        NmIF.setBounds(511, 70, 260, 23);

        btnIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnIF.setMnemonic('2');
        btnIF.setToolTipText("Alt+2");
        btnIF.setName("btnIF"); // NOI18N
        btnIF.setPreferredSize(new java.awt.Dimension(28, 23));
        btnIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIFActionPerformed(evt);
            }
        });
        panelisi3.add(btnIF);
        btnIF.setBounds(774, 70, 28, 23);

        buttonGroup1.add(RDatang);
        RDatang.setSelected(true);
        RDatang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        RDatang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        RDatang.setName("RDatang"); // NOI18N
        RDatang.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(RDatang);
        RDatang.setBounds(12, 40, 20, 23);

        buttonGroup1.add(RFaktur);
        RFaktur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        RFaktur.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        RFaktur.setName("RFaktur"); // NOI18N
        RFaktur.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(RFaktur);
        RFaktur.setBounds(12, 70, 20, 23);

        TglFaktur1.setDisplayFormat("dd-MM-yyyy");
        TglFaktur1.setName("TglFaktur1"); // NOI18N
        TglFaktur1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglFaktur1KeyPressed(evt);
            }
        });
        panelisi3.add(TglFaktur1);
        TglFaktur1.setBounds(96, 70, 90, 23);

        TglFaktur2.setDisplayFormat("dd-MM-yyyy");
        TglFaktur2.setName("TglFaktur2"); // NOI18N
        TglFaktur2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglFaktur2KeyPressed(evt);
            }
        });
        panelisi3.add(TglFaktur2);
        TglFaktur2.setBounds(213, 70, 90, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(186, 70, 25, 23);

        label19.setText("Tgl.Datang :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label19);
        label19.setBounds(30, 40, 62, 23);

        label20.setText("Tgl.Faktur :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label20);
        label20.setBounds(30, 70, 62, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
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

        FormPass2.setBackground(new java.awt.Color(255, 255, 255));
        FormPass2.setBorder(null);
        FormPass2.setName("FormPass2"); // NOI18N
        FormPass2.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbilPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbilPhoto.setMnemonic('U');
        btnAmbilPhoto.setText("Ambil");
        btnAmbilPhoto.setToolTipText("Alt+U");
        btnAmbilPhoto.setName("btnAmbilPhoto"); // NOI18N
        btnAmbilPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbilPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(btnAmbilPhoto);

        BtnRefreshPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto.setMnemonic('U');
        BtnRefreshPhoto.setText("Refresh");
        BtnRefreshPhoto.setToolTipText("Alt+U");
        BtnRefreshPhoto.setName("BtnRefreshPhoto"); // NOI18N
        BtnRefreshPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(BtnRefreshPhoto);

        FormPhoto.add(FormPass2, java.awt.BorderLayout.PAGE_END);

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

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
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
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        TabRawat.addTab("Laporan 1", scrollPane1);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll.setViewportView(LoadHTML2);

        TabRawat.addTab("Laporan 2", Scroll);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        suplier.dispose();
        petugas.dispose();
        barang.industri.dispose();
        barang.jenis.dispose();
        barang.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,kdbar);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        akses.setform("DlgCariPemesanan");
        suplier.emptTeks();
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgCariPemesanan");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void TglBeli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli1KeyPressed
        Valid.pindah(evt,NoFaktur,kdsup);
    }//GEN-LAST:event_TglBeli1KeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("DlgCariPemesanan");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        akses.setform("DlgCariPemesanan");
        barang.jenis.emptTeks();
        barang.jenis.isCek();
        barang.jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.jenis.setLocationRelativeTo(internalFrame1);
        barang.jenis.setAlwaysOnTop(false);
        barang.jenis.setVisible(true);
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select datasuplier.nama_suplier from datasuplier where datasuplier.kode_suplier=?", nmsup,kdsup.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select datasuplier.nama_suplier from datasuplier where datasuplier.kode_suplier=?", nmsup,kdsup.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select datasuplier.nama_suplier from datasuplier where datasuplier.kode_suplier=?", nmsup,kdsup.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSuplierActionPerformed(null);
        }
    }//GEN-LAST:event_kdsupKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, BtnKeluar, kdsup);
    }//GEN-LAST:event_NoFakturKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            nmptg.setText(petugas.tampil3(kdptg.getText()));     
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
            kdsup.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
            kdbar.requestFocus();       
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){            
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
            kdjenis.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){            
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from jenis  where kdjns=?", nmjenis,kdjenis.getText());         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from jenis  where kdjns=?", nmjenis,kdjenis.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from jenis  where kdjns=?", nmjenis,kdjenis.getText());
            kdbar.requestFocus();   
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSatuanActionPerformed(null);
        }
    }//GEN-LAST:event_kdjenisKeyPressed

    private void TglBeli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBeli2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBeli2KeyPressed

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
        TabRawatMouseClicked(null);
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
        NoFaktur.setText("");
        kdbar.setText("");
        nmbar.setText("");
        kdjenis.setText("");
        nmjenis.setText("");
        kdsup.setText("");
        nmsup.setText("");
        kdptg.setText("");
        nmptg.setText("");
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                BtnCariActionPerformed(evt);
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode.getRowCount()!=0){
                    Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                    int row=tabMode.getRowCount();
                    for(i=0;i<row;i++){  
                        Sequel.menyimpan("temporary","'"+i+"','"+
                                        tabMode.getValueAt(i,0).toString()+"','"+
                                        tabMode.getValueAt(i,1).toString()+"','"+
                                        tabMode.getValueAt(i,2).toString()+"','"+
                                        tabMode.getValueAt(i,3).toString()+"','"+
                                        tabMode.getValueAt(i,4).toString()+"','"+
                                        tabMode.getValueAt(i,5).toString()+"','"+
                                        tabMode.getValueAt(i,6).toString()+"','"+
                                        tabMode.getValueAt(i,10).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Penerimaan"); 
                    }
                    i++;
                    Sequel.menyimpan("temporary","'"+i+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Penerimaan"); 
                    i++;
                    Sequel.menyimpan("temporary","'"+i+"','Jml.Total :','','','','','','','"+LTotal.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Penerimaan"); 

                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptPemesanan.jasper","report","::[ Transaksi Penerimaan Barang ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
                }  
                break;
            case 1:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {

                    File g = new File("filepenerimaan.css");            
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;}"+
                        ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                    );
                    bg.close();

                    File f = new File("LaporanPenerimaan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML2.getText().replaceAll("<head>","<head>"+
                            "<link href=\"filepenerimaan.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='1700px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>Data Penerimaan Obat<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
                this.setCursor(Cursor.getDefaultCursor());
                break;
            default:
                break;
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void nmjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmjenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmjenisKeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
    if(tbDokter.getSelectedRow()!= -1){  
        if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString().trim().equals("")){
            Valid.textKosong(TCari,"No.Faktur");
        }else{
          int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, yakin mau dihapus...??","Konfirmasi",JOptionPane.YES_NO_OPTION);
          if (reply == JOptionPane.YES_OPTION) {
              try {
                  pscaripesan=koneksi.prepareStatement(
                        "select pemesanan.no_faktur, pemesanan.tagihan, pemesanan.kd_bangsal,pemesanan.tgl_faktur,pemesanan.status,pemesanan.no_order,pemesanan.ppn,(pemesanan.total2+pemesanan.meterai) as total from pemesanan where pemesanan.no_faktur=?");
                  try {
                     pscaripesan.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                     rs=pscaripesan.executeQuery();
                     if(rs.next()){
                         Sequel.AutoComitFalse();
                         sukses=true;
                         psdetailpesan=koneksi.prepareStatement("select detailpesan.kode_brng,detailpesan.jumlah2,detailpesan.no_batch,detailpesan.no_faktur from detailpesan where detailpesan.no_faktur=? ");
                         try {
                             psdetailpesan.setString(1,rs.getString(1));
                             rs2=psdetailpesan.executeQuery();
                             while(rs2.next()){
                                 if(aktifkanbatch.equals("yes")){
                                    Trackobat.catatRiwayat(rs2.getString("kode_brng"),0,rs2.getDouble("jumlah2"),"Penerimaan",akses.getkode(),rs.getString("kd_bangsal"),"Hapus",rs2.getString("no_batch"),rs2.getString("no_faktur"),rs2.getString("no_faktur")+" "+rs.getString("no_order"));
                                    Sequel.menyimpan("gudangbarang","'"+rs2.getString("kode_brng") +"','"+rs.getString("kd_bangsal") +"','-"+rs2.getString("jumlah2")+"','"+rs2.getString("no_batch")+"','"+rs2.getString("no_faktur")+"'", 
                                                      "stok=stok-'"+rs2.getString("jumlah2") +"'","kode_brng='"+rs2.getString("kode_brng")+"' and kd_bangsal='"+rs.getString("kd_bangsal") +"' and no_batch='"+rs2.getString("no_batch")+"' and no_faktur='"+rs2.getString("no_faktur")+"'");
                                    Sequel.queryu3("delete from data_batch where kode_brng=? and no_batch=? and no_faktur=?",3,new String[]{
                                        rs2.getString("kode_brng"),rs2.getString("no_batch"),rs2.getString("no_faktur")
                                    }); 
                                 }else{
                                    Trackobat.catatRiwayat(rs2.getString("kode_brng"),0,rs2.getDouble("jumlah2"),"Penerimaan",akses.getkode(),rs.getString("kd_bangsal"),"Hapus","","",rs2.getString("no_faktur")+" "+rs.getString("no_order"));
                                    Sequel.menyimpan("gudangbarang","'"+rs2.getString("kode_brng") +"','"+rs.getString("kd_bangsal") +"','-"+rs2.getString("jumlah2")+"','',''", 
                                                      "stok=stok-'"+rs2.getString("jumlah2") +"'","kode_brng='"+rs2.getString("kode_brng")+"' and kd_bangsal='"+rs.getString("kd_bangsal") +"' and no_batch='' and no_faktur=''");
                                    Sequel.queryu3("delete from data_batch where kode_brng=? and no_batch=? and no_faktur=?",3,new String[]{
                                        rs2.getString("kode_brng"),"",""
                                    }); 
                                 }  
                             }
                             sukses=true;
                         } catch (Exception e) {
                             sukses=false;
                             System.out.println("Notif 2 : "+e);
                         } finally{
                             if(rs2!=null){
                                 rs2.close();
                             }
                             if(psdetailpesan!=null){
                                 psdetailpesan.close();
                             }
                         }

                         if(sukses==true){
                            Sequel.queryu("delete from tampjurnal");
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                Sequel.cariIsi("select set_akun.Pemesanan_Obat from set_akun"),"PERSEDIAAN BARANG","0",rs.getString("total")
                            });   
                            if(rs.getDouble("ppn")>0){
                                Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                    Sequel.cariIsi("select set_akun.PPN_Masukan from set_akun"),"PPN Masukan Obat","0",rs.getString("ppn")
                                }); 
                            }
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                Sequel.cariIsi("select set_akun.Kontra_Pemesanan_Obat from set_akun"),"HUTANG USAHA",rs.getString("tagihan"),"0"
                            }); 
                            sukses=jur.simpanJurnal(rs.getString("no_faktur"),"U","BATAL TRANSAKSI PENERIMAAN BARANG DI "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",rs.getString("kd_bangsal")).toUpperCase()+", OLEH "+akses.getkode()); 
                         }

                         if(sukses==true){
                            Sequel.queryu2("delete from pemesanan where no_faktur=?",1,new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()});
                            Sequel.Commit();
                         }else{
                            sukses=false;
                            JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                         }  
                         Sequel.AutoComitTrue();
                     } 
                     if(sukses==true){
                        tampil();
                     }
                  } catch (Exception e) {
                      System.out.println("Notif : "+e);
                  } finally{
                      if(rs!=null){
                          rs.close();
                      }
                      if(pscaripesan!=null){
                          pscaripesan.close();
                      }
                  }
              } catch (Exception ex) {
                  System.out.println(ex);
              }  
          }
        } 
    }else{
        JOptionPane.showMessageDialog(null,"Silahkan pilih No.Faktur Penerimaan...!!!");
    }
          
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ppBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBayarActionPerformed
        if(tbDokter.getSelectedRow()!= -1){  
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Faktur");
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                KeuanganBayarPemesananFarmasi bayarpesan=new KeuanganBayarPemesananFarmasi(null,false);
                bayarpesan.setData(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                bayarpesan.tampil();
                bayarpesan.isCek();
                bayarpesan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                bayarpesan.setLocationRelativeTo(internalFrame1);
                bayarpesan.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }   
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Faktur Penerimaan...!!!");
        }
            
    }//GEN-LAST:event_ppBayarActionPerformed

    private void ppUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUbahActionPerformed
        if(tbDokter.getSelectedRow()!= -1){  
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Faktur");
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgUbahPemesanan ubahpesan=new DlgUbahPemesanan(null,false);
                ubahpesan.isCek(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                ubahpesan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                ubahpesan.setLocationRelativeTo(internalFrame1);
                ubahpesan.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }  
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Faktur Penerimaan...!!!");
        }
            
    }//GEN-LAST:event_ppUbahActionPerformed

    private void KdIFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdIFKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_industri from industrifarmasi where nip=?",NmIF,KdIF.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_industri from industrifarmasi where nip=?",NmIF,KdIF.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_industri from industrifarmasi where nip=?",NmIF,KdIF.getText());
            kdbar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnIFActionPerformed(null);
        }
    }//GEN-LAST:event_KdIFKeyPressed

    private void btnIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIFActionPerformed
        akses.setform("DlgCariPemesanan");
        barang.industri.emptTeks();
        barang.industri.isCek();
        barang.industri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.industri.setLocationRelativeTo(internalFrame1);
        barang.industri.setAlwaysOnTop(false);
        barang.industri.setVisible(true);
    }//GEN-LAST:event_btnIFActionPerformed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbDokter.getSelectedRow()!= -1){  
            if(TabRawat.getSelectedIndex()==0){
                if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString().trim().equals("")){
                    Valid.textKosong(TCari,"No.Faktur");
                }else{
                    isPhoto();
                    panggilPhoto();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan buka Laporan 1...!!!");
            }   
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Faktur Penerimaan...!!!");
        }   
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilPhotoActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Faktur");
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Sequel.meghapus("bukti_pemesanan", "no_faktur",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                Sequel.menyimpan2("bukti_pemesanan","?,''",1,new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()});
                Valid.panggilUrl("penerimaanapotek/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&nofaktur="+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_btnAmbilPhotoActionPerformed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tbDokter.getSelectedRow()!= -1){  
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString().trim().equals("")){
                isPhoto();
                panggilPhoto();
            }else{
                ChkAccor.setSelected(false);
                isPhoto();
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void TglFaktur1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglFaktur1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglFaktur1KeyPressed

    private void TglFaktur2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglFaktur2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglFaktur2KeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPemesanan dialog = new DlgCariPemesanan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.CekBox ChkAccor;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox KdIF;
    private widget.Label LTotal;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.TextBox NmIF;
    private widget.TextBox NoFaktur;
    private widget.PanelBiasa PanelAccor;
    private widget.RadioButton RDatang;
    private widget.RadioButton RFaktur;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglBeli1;
    private widget.Tanggal TglBeli2;
    private widget.Tanggal TglFaktur1;
    private widget.Tanggal TglFaktur2;
    private widget.Button btnAmbilPhoto;
    private widget.Button btnBarang;
    private widget.Button btnIF;
    private widget.Button btnPetugas;
    private widget.Button btnSatuan;
    private widget.Button btnSuplier;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdjenis;
    private widget.TextBox kdptg;
    private widget.TextBox kdsup;
    private widget.Label label10;
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
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmjenis;
    private widget.TextBox nmptg;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppBayar;
    private javax.swing.JMenuItem ppHapus;
    private javax.swing.JMenuItem ppUbah;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       Valid.tabelKosong(tabMode);
        try{   
            if(RDatang.isSelected()==true){
                tanggal=" pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' ";
            }else if(RFaktur.isSelected()==true){
                tanggal=" pemesanan.tgl_faktur between '"+Valid.SetTgl(TglFaktur1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglFaktur2.getSelectedItem()+"")+"' ";
            }
            
            datanofaktur="";
            if(!NoFaktur.getText().equals("")){
                datanofaktur=" and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' ";
            }
            
            datasuplier="";
            if(!nmsup.getText().equals("")){
                datasuplier=" and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' ";
            }
            
            datapetugas="";
            if(!nmptg.getText().equals("")){
                datapetugas=" and petugas.nama like '%"+nmptg.getText()+"%' ";
            }
            
            datajenis="";
            if(!nmjenis.getText().equals("")){
                datajenis=" and jenis.nama like '%"+nmjenis.getText()+"%' ";
            }
            
            databarang="";
            if(!nmbar.getText().equals("")){
                databarang=" and databarang.nama_brng like '%"+nmbar.getText()+"%' ";
            }
            
            dataindustri="";
            if(!NmIF.getText().equals("")){
                dataindustri=" and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' ";
            }
            
            statusbayar="";
            if(!Status.getSelectedItem().toString().equals("Semua")){
                statusbayar=" and pemesanan.status='"+Status.getSelectedItem().toString()+"' ";
            }
            
            datacari="";
            if(!TCari.getText().trim().equals("")){
                datacari=" and (pemesanan.no_faktur like '%"+TCari.getText()+"%' or pemesanan.kode_suplier like '%"+TCari.getText()+"%' or datasuplier.nama_suplier like '%"+TCari.getText()+"%' or pemesanan.nip like '%"+TCari.getText()+"%' or "+
                         " petugas.nama like '%"+TCari.getText()+"%' or bangsal.nm_bangsal like '%"+TCari.getText()+"%' or detailpesan.kode_brng like '%"+TCari.getText()+"%' or databarang.nama_brng like '%"+TCari.getText()+"%' or detailpesan.kode_sat like '%"+TCari.getText()+"%' or "+
                         " detailpesan.no_batch like '%"+TCari.getText()+"%' or industrifarmasi.nama_industri like '%"+TCari.getText()+"%' or pemesanan.no_order like '%"+TCari.getText()+"%' or jenis.nama like '%"+TCari.getText()+"%') ";
            }
            
            ps=koneksi.prepareStatement("select pemesanan.tgl_pesan,pemesanan.no_faktur, "+
                    "pemesanan.kode_suplier,datasuplier.nama_suplier, "+
                    "pemesanan.nip,petugas.nama,bangsal.nm_bangsal,pemesanan.tgl_faktur, "+
                    "pemesanan.tgl_tempo,pemesanan.status,pemesanan.total2,pemesanan.ppn,"+
                    "pemesanan.meterai,pemesanan.tagihan,pemesanan.no_order "+
                    " from pemesanan inner join datasuplier inner join petugas inner join bangsal  "+
                    " inner join detailpesan inner join databarang inner join kodesatuan "+
                    " inner join jenis inner join industrifarmasi "+
                    " on detailpesan.kode_brng=databarang.kode_brng "+
                    " and detailpesan.kode_sat=kodesatuan.kode_sat "+
                    " and pemesanan.kd_bangsal=bangsal.kd_bangsal "+
                    " and pemesanan.no_faktur=detailpesan.no_faktur "+
                    " and pemesanan.kode_suplier=datasuplier.kode_suplier "+
                    " and databarang.kode_industri=industrifarmasi.kode_industri "+
                    " and pemesanan.nip=petugas.nip and databarang.kdjns=jenis.kdjns"+
                    " where "+tanggal+datanofaktur+datasuplier+datapetugas+datajenis+databarang+dataindustri+statusbayar+datacari+
                    " group by pemesanan.no_faktur order by pemesanan.tgl_pesan,pemesanan.no_faktur ");
            try {
                rs=ps.executeQuery();
                tagihan=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{rs.getString(2),rs.getString(3)+", "+rs.getString(4),
                          rs.getString(5)+", "+rs.getString(6),"Pengadaan di "+rs.getString(7) +" :","","","","","","",""
                    });  
                    
                    ps2=koneksi.prepareStatement("select detailpesan.kode_brng,databarang.nama_brng, "+
                        "detailpesan.kode_sat,kodesatuan.satuan,detailpesan.jumlah,detailpesan.h_pesan, "+
                        "detailpesan.subtotal,detailpesan.dis,detailpesan.besardis,detailpesan.total,"+
                        "detailpesan.no_batch,industrifarmasi.nama_industri,detailpesan.kadaluarsa "+
                        "from detailpesan inner join databarang inner join kodesatuan inner join jenis inner join industrifarmasi "+
                        " on detailpesan.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "+
                        " and databarang.kode_industri=industrifarmasi.kode_industri and detailpesan.kode_sat=kodesatuan.kode_sat where "+
                        " detailpesan.no_faktur=? and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and "+
                        " (detailpesan.kode_brng like ? or databarang.nama_brng like ? or detailpesan.kode_sat like ? or detailpesan.no_batch like ? or "+
                        " industrifarmasi.nama_industri like ? or jenis.nama like ?) order by detailpesan.kode_brng  ");
                    
                    try {
                        ps2.setString(1,rs.getString(2));
                        ps2.setString(2,"%"+nmjenis.getText()+"%");
                        ps2.setString(3,"%"+nmbar.getText()+"%");
                        ps2.setString(4,"%"+NmIF.getText()+"%");
                        ps2.setString(5,"%"+TCari.getText()+"%");
                        ps2.setString(6,"%"+TCari.getText()+"%");
                        ps2.setString(7,"%"+TCari.getText()+"%");
                        ps2.setString(8,"%"+TCari.getText()+"%");
                        ps2.setString(9,"%"+TCari.getText()+"%");
                        ps2.setString(10,"%"+TCari.getText()+"%");
                        rs2=ps2.executeQuery();
                        int no=1;
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                no+". Batch "+rs2.getString("no_batch"),"Exp : "+rs2.getString("kadaluarsa"),"I.F. : "+rs2.getString("nama_industri"),rs2.getString(1)+", "+rs2.getString(2),
                                rs2.getString(3)+", "+rs2.getString(4),rs2.getString(5),Valid.SetAngka(rs2.getDouble(6)),Valid.SetAngka(rs2.getDouble(7)),
                                Valid.SetAngka(rs2.getDouble(8)),Valid.SetAngka(rs2.getDouble(9)),Valid.SetAngka(rs2.getDouble(10))
                            });
                            no++;
                        }                        
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    tabMode.addRow(new Object[]{rs.getString("no_order"),"Tgl.Faktur",": "+rs.getString("tgl_faktur"),"","","","","","Total",":",Valid.SetAngka(rs.getDouble("total2"))});
                    tabMode.addRow(new Object[]{"","Tgl.Datang",": "+rs.getString("tgl_pesan"),"","","","","","Meterai",":",Valid.SetAngka(rs.getDouble("meterai"))});
                    tabMode.addRow(new Object[]{"","Jth.Tempo",": "+rs.getString("tgl_tempo"),"","","","","","PPN",":",Valid.SetAngka(rs.getDouble("ppn"))});
                    tabMode.addRow(new Object[]{"","Status Bayar",": "+rs.getString("status"),"","","","","","Tagihan",":",Valid.SetAngka(rs.getDouble("tagihan"))});
                    tabMode.addRow(new Object[]{"","","","","","","","","","",""});
                    tagihan=tagihan+rs.getDouble("tagihan");
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
            LTotal.setText(Valid.SetAngka(tagihan));
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }        
    }
    
    private void tampil2() {
        try {
            if(RDatang.isSelected()==true){
                tanggal=" pemesanan.tgl_pesan between '"+Valid.SetTgl(TglBeli1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglBeli2.getSelectedItem()+"")+"' ";
            }else if(RFaktur.isSelected()==true){
                tanggal=" pemesanan.tgl_faktur between '"+Valid.SetTgl(TglFaktur1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglFaktur2.getSelectedItem()+"")+"' ";
            }
            
            datanofaktur="";
            if(!NoFaktur.getText().equals("")){
                datanofaktur=" and pemesanan.no_faktur like '%"+NoFaktur.getText()+"%' ";
            }
            
            datasuplier="";
            if(!nmsup.getText().equals("")){
                datasuplier=" and datasuplier.nama_suplier like '%"+nmsup.getText()+"%' ";
            }
            
            datapetugas="";
            if(!nmptg.getText().equals("")){
                datapetugas=" and petugas.nama like '%"+nmptg.getText()+"%' ";
            }
            
            datajenis="";
            if(!nmjenis.getText().equals("")){
                datajenis=" and jenis.nama like '%"+nmjenis.getText()+"%' ";
            }
            
            databarang="";
            if(!nmbar.getText().equals("")){
                databarang=" and databarang.nama_brng like '%"+nmbar.getText()+"%' ";
            }
            
            dataindustri="";
            if(!NmIF.getText().equals("")){
                dataindustri=" and industrifarmasi.nama_industri like '%"+NmIF.getText()+"%' ";
            }
            
            statusbayar="";
            if(!Status.getSelectedItem().toString().equals("Semua")){
                statusbayar=" and pemesanan.status='"+Status.getSelectedItem().toString()+"' ";
            }
            
            datacari="";
            if(!TCari.getText().trim().equals("")){
                datacari=" and (pemesanan.no_faktur like '%"+TCari.getText()+"%' or pemesanan.kode_suplier like '%"+TCari.getText()+"%' or datasuplier.nama_suplier like '%"+TCari.getText()+"%' or pemesanan.nip like '%"+TCari.getText()+"%' or "+
                         " petugas.nama like '%"+TCari.getText()+"%' or bangsal.nm_bangsal like '%"+TCari.getText()+"%' or detailpesan.kode_brng like '%"+TCari.getText()+"%' or databarang.nama_brng like '%"+TCari.getText()+"%' or detailpesan.kode_sat like '%"+TCari.getText()+"%' or "+
                         " detailpesan.no_batch like '%"+TCari.getText()+"%' or industrifarmasi.nama_industri like '%"+TCari.getText()+"%' or pemesanan.no_order like '%"+TCari.getText()+"%' or jenis.nama like '%"+TCari.getText()+"%') ";
            }
            
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='head'>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='90px'>No.Faktur</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='70px'>Tgl.Faktur</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='70px'>Tgl.Datang</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='70px'>Jth.Tempo</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='80px'>Status Bayar</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='180px'>Suplier</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='180px'>Petugas</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='300px'>Barang</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='70px'>Satuan</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='40px'>Jml</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='75px'>Harga(Rp)</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='80px'>Subtotal(Rp)</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='40px'>Disk(%)</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='75px'>Besar Disk(Rp)</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='80px'>Total(Rp)</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='80px'>Penerimaan(Rp)</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='65px'>Meterai(Rp)</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='65px'>PPN(Rp)</td>"+
                    "<td valign='top' bgcolor='#FFFAFA' align='center' width='85px'>Tagihan(Rp)</td>"+
                "</tr>"); 
            ps=koneksi.prepareStatement("select pemesanan.tgl_pesan,pemesanan.no_faktur, "+
                    "pemesanan.kode_suplier,datasuplier.nama_suplier, "+
                    "pemesanan.nip,petugas.nama,bangsal.nm_bangsal,pemesanan.tgl_faktur, "+
                    "pemesanan.tgl_tempo,pemesanan.status,pemesanan.total2,pemesanan.ppn,"+
                    "pemesanan.meterai,pemesanan.tagihan,pemesanan.no_order "+
                    " from pemesanan inner join datasuplier inner join petugas inner join bangsal  "+
                    " inner join detailpesan inner join databarang inner join kodesatuan "+
                    " inner join jenis inner join industrifarmasi "+
                    " on detailpesan.kode_brng=databarang.kode_brng "+
                    " and detailpesan.kode_sat=kodesatuan.kode_sat "+
                    " and pemesanan.kd_bangsal=bangsal.kd_bangsal "+
                    " and pemesanan.no_faktur=detailpesan.no_faktur "+
                    " and pemesanan.kode_suplier=datasuplier.kode_suplier "+
                    " and databarang.kode_industri=industrifarmasi.kode_industri "+
                    " and pemesanan.nip=petugas.nip and databarang.kdjns=jenis.kdjns"+
                    " where "+tanggal+datanofaktur+datasuplier+datapetugas+datajenis+databarang+dataindustri+statusbayar+datacari+
                    " group by pemesanan.no_faktur order by pemesanan.tgl_pesan,pemesanan.no_faktur ");
            try {
                rs=ps.executeQuery();
                tagihan=0;
                while(rs.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+rs.getString("no_faktur")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("tgl_faktur")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("tgl_pesan")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("tgl_tempo")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("status")+"</td>"+
                            "<td valign='top' align='left'>"+rs.getString("kode_suplier")+", "+rs.getString("nama_suplier")+"</td>"+
                            "<td valign='top' align='left'>"+rs.getString("nip")+", "+rs.getString("nama")+"</td>"+
                            "<td valign='top' align='left' colspan='8'>"+"Pengadaan di "+rs.getString("nm_bangsal") +" :"+"</td>"+
                            "<td valign='top' align='right'>"+rs.getString("total2")+"</td>"+
                            "<td valign='top' align='right'>"+rs.getString("meterai")+"</td>"+
                            "<td valign='top' align='right'>"+rs.getString("ppn")+"</td>"+
                            "<td valign='top' align='right'>"+rs.getString("tagihan")+"</td>"+
                        "</tr>");  
                    
                    ps2=koneksi.prepareStatement("select detailpesan.kode_brng,databarang.nama_brng, "+
                        "detailpesan.kode_sat,kodesatuan.satuan,detailpesan.jumlah,detailpesan.h_pesan, "+
                        "detailpesan.subtotal,detailpesan.dis,detailpesan.besardis,detailpesan.total,"+
                        "detailpesan.no_batch,industrifarmasi.nama_industri,detailpesan.kadaluarsa "+
                        "from detailpesan inner join databarang inner join kodesatuan inner join jenis inner join industrifarmasi "+
                        " on detailpesan.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "+
                        " and databarang.kode_industri=industrifarmasi.kode_industri and detailpesan.kode_sat=kodesatuan.kode_sat where "+
                        " detailpesan.no_faktur=? and jenis.nama like ? and databarang.nama_brng like ? and industrifarmasi.nama_industri like ? and "+
                        " (detailpesan.kode_brng like ? or databarang.nama_brng like ? or detailpesan.kode_sat like ? or detailpesan.no_batch like ? or "+
                        " industrifarmasi.nama_industri like ? or jenis.nama like ?) order by detailpesan.kode_brng  ");
                    
                    try {
                        ps2.setString(1,rs.getString(2));
                        ps2.setString(2,"%"+nmjenis.getText()+"%");
                        ps2.setString(3,"%"+nmbar.getText()+"%");
                        ps2.setString(4,"%"+NmIF.getText()+"%");
                        ps2.setString(5,"%"+TCari.getText()+"%");
                        ps2.setString(6,"%"+TCari.getText()+"%");
                        ps2.setString(7,"%"+TCari.getText()+"%");
                        ps2.setString(8,"%"+TCari.getText()+"%");
                        ps2.setString(9,"%"+TCari.getText()+"%");
                        ps2.setString(10,"%"+TCari.getText()+"%");
                        rs2=ps2.executeQuery();
                        int no=1;
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='top' align='center'>&nbsp;</td>"+
                                    "<td valign='top' align='left' colspan='2'>"+no+". No.Batch : "+rs2.getString("no_batch")+"</td>"+
                                    "<td valign='top' align='left' colspan='2'>Exp : "+rs2.getString("kadaluarsa")+"</td>"+
                                    "<td valign='top' align='left' colspan='2'>I.F. : "+rs2.getString("nama_industri")+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("kode_brng")+", "+rs2.getString("nama_brng")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("satuan")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("jumlah")+"</td>"+
                                    "<td valign='top' align='right'>"+rs2.getString("h_pesan")+"</td>"+
                                    "<td valign='top' align='right'>"+rs2.getString("subtotal")+"</td>"+
                                    "<td valign='top' align='right'>"+rs2.getString("dis")+"</td>"+
                                    "<td valign='top' align='right'>"+rs2.getString("besardis")+"</td>"+
                                    "<td valign='top' align='right'>"+rs2.getString("total")+"</td>"+
                                    "<td valign='top' align='center'>&nbsp;</td>"+
                                    "<td valign='top' align='center'>&nbsp;</td>"+
                                    "<td valign='top' align='center'>&nbsp;</td>"+
                                    "<td valign='top' align='center'>&nbsp;</td>"+
                                "</tr>"); 
                            no++;
                        }                        
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    tagihan=tagihan+rs.getDouble("tagihan");
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
            LTotal.setText(Valid.SetAngka(tagihan));
            LoadHTML2.setText(
                    "<html>"+
                      "<table width='1700px' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdjenis.setText("");
        kdbar.requestFocus();        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getpemesanan_obat());
        if(akses.getkode().equals("Admin Utama")){
            ppHapus.setEnabled(true);
        }else{
            ppHapus.setEnabled(false);
        }        
        ppBayar.setEnabled(akses.getbayar_pemesanan_obat());
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
                ps=koneksi.prepareStatement("select bukti_pemesanan.photo from bukti_pemesanan where bukti_pemesanan.no_faktur=?");
                try {
                    ps.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penerimaanapotek/"+rs.getString("photo")+"' alt='photo' width='"+(internalFrame1.getWidth()-330)+"' height='"+(internalFrame1.getHeight()-315)+"'/></center></body></html>");
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
}

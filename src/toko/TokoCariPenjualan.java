package toko;
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
import java.text.DecimalFormat;
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
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;

public class TokoCariPenjualan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,pscarijual,pstoko_detail_jual;
    private ResultSet rs,rs2;
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private riwayattoko Trackbarang=new riwayattoko();
    private int i=0,no=1;
    public  TokoMember member=new TokoMember(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  TokoBarang barang=new TokoBarang(null,false);
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");    
    private double ttljual=0,ttlppn=0,ttlongkir=0,ttldisc=0,ttltambahan=0,ttlsubttl=0,subttljual=0,subttldisc=0,subttlall=0,subttltambahan=0,ttlhpp=0;
    private String totaljual="",nofak="",mem="",ptg="",sat="",bar="",tanggal="",
            Penjualan_Toko=Sequel.cariIsi("select Penjualan_Toko from set_akun"),
            HPP_Barang_Toko=Sequel.cariIsi("select HPP_Barang_Toko from set_akun"),
            Persediaan_Barang_Toko=Sequel.cariIsi("select Persediaan_Barang_Toko from set_akun");
    private StringBuilder htmlContent;
    private boolean sukses=true;
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public TokoCariPenjualan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Object[] row={"Tanggal","No.Nota","Petugas","Member","Catatan","Jns.Jual","Cara Bayar"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(120);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoNota.setDocument(new batasInput((byte)25).getKata(NoNota));
        kdmem.setDocument(new batasInput((byte)15).getKata(kdmem));
        nmmem.setDocument(new batasInput((byte)70).getKata(nmmem));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        kdsat.setDocument(new batasInput((byte)3).getKata(kdsat));
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
        
        member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("CariPenjualan")){
                    if(member.getTable().getSelectedRow()!= -1){                   
                        kdmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),0).toString());
                        nmmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),1).toString());
                    }  
                    kdmem.requestFocus();
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
        
        member.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("CariPenjualan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        member.dispose();
                    }
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
                if(akses.getform().equals("CariPenjualan")){
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
                if(akses.getform().equals("CariPenjualan")){
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),0).toString());                    
                        nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());
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
                if(akses.getform().equals("CariPenjualan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();
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
                if(akses.getform().equals("CariPenjualan")){
                    if(barang.jenis.getTable().getSelectedRow()!= -1){
                        kdsat.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),0).toString());
                        nmsat.setText(barang.jenis.getTable().getValueAt(barang.jenis.getTable().getSelectedRow(),1).toString());
                    }                
                    kdsat.requestFocus();
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML1.setEditable(true);
        LoadHTML1.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML1.setDocument(doc);
        LoadHTML1.setEditable(false);
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
        ppCetakNota = new javax.swing.JMenuItem();
        ppHapus = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        label24 = new widget.Label();
        kdsat = new widget.TextBox();
        btnSatuan = new widget.Button();
        nmsat = new widget.TextBox();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoNota = new widget.TextBox();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdmem = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmmem = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPasien = new widget.Button();
        btnPetugas = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        TabRawat = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        Scroll = new widget.ScrollPane();
        LoadHTML1 = new widget.editorpane();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppCetakNota.setBackground(new java.awt.Color(255, 255, 254));
        ppCetakNota.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCetakNota.setForeground(new java.awt.Color(50, 50, 50));
        ppCetakNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCetakNota.setText("Cetak Ulang Nota");
        ppCetakNota.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCetakNota.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCetakNota.setName("ppCetakNota"); // NOI18N
        ppCetakNota.setPreferredSize(new java.awt.Dimension(190, 25));
        ppCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCetakNotaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppCetakNota);

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50, 50, 50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Penjualan");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(190, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Penjualan Barang Toko / Minimarket / Koperasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(295, 10, 90, 23);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);
        kdbar.setBounds(389, 10, 110, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(501, 10, 270, 23);

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
        label24.setPreferredSize(new java.awt.Dimension(63, 23));
        panelisi4.add(label24);
        label24.setBounds(0, 10, 64, 23);

        kdsat.setName("kdsat"); // NOI18N
        kdsat.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsatKeyPressed(evt);
            }
        });
        panelisi4.add(kdsat);
        kdsat.setBounds(69, 10, 53, 23);

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
        btnSatuan.setBounds(255, 10, 28, 23);

        nmsat.setName("nmsat"); // NOI18N
        nmsat.setPreferredSize(new java.awt.Dimension(80, 23));
        nmsat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmsatKeyPressed(evt);
            }
        });
        panelisi4.add(nmsat);
        nmsat.setBounds(124, 10, 126, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
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

        label9.setText("Jumlah Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(80, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(130, 30));
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

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Nota :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoNota.setName("NoNota"); // NOI18N
        NoNota.setPreferredSize(new java.awt.Dimension(207, 23));
        NoNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoNotaKeyPressed(evt);
            }
        });
        panelisi3.add(NoNota);
        NoNota.setBounds(74, 10, 210, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 70, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(74, 40, 90, 23);

        label16.setText("Member :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(385, 10, 60, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(385, 40, 60, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(449, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(449, 40, 80, 23);

        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(531, 10, 240, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(531, 40, 240, 23);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('1');
        btnPasien.setToolTipText("Alt+1");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        panelisi3.add(btnPasien);
        btnPasien.setBounds(774, 10, 28, 23);

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

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(163, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(195, 40, 90, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

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

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
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
        scrollPane1.setViewportView(tbDokter);

        TabRawat.addTab("Laporan 1", scrollPane1);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML1.setBorder(null);
        LoadHTML1.setName("LoadHTML1"); // NOI18N
        Scroll.setViewportView(LoadHTML1);

        TabRawat.addTab("Laporan 2", Scroll);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        akses.setform("CariPenjualan");
        member.emptTeks();
        member.isCek();
        member.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        member.setLocationRelativeTo(internalFrame1);
        member.setAlwaysOnTop(false);
        member.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("CariPenjualan");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,kdmem,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_member from member where no_member=?", nmmem,kdmem.getText());      
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_member from member where no_member=?", nmmem,kdmem.getText());   
            NoNota.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_member from member where no_member=?", nmmem,kdmem.getText());   
            Tgl1.requestFocus();      
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPasienActionPerformed(null);
        }
    }//GEN-LAST:event_kdmemKeyPressed

    private void NoNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoNotaKeyPressed
        Valid.pindah(evt, BtnKeluar, kdptg);
    }//GEN-LAST:event_NoNotaKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText()); 
            kdbar.requestFocus(); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdptg);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from tokobarang where kode_brng=?", nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_brng from tokobarang where kode_brng=?", nmbar,kdbar.getText());
            kdsat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_brng from tokobarang where kode_brng=?", nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("CariPenjualan");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void kdsatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select satuan from kodesatuan where kode_sat=?", nmsat,kdsat.getText());
            kdbar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnSatuanActionPerformed(null);
        }
    }//GEN-LAST:event_kdsatKeyPressed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        akses.setform("CariPenjualan");
        barang.jenis.emptTeks();
        barang.jenis.isCek();
        barang.jenis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.jenis.setLocationRelativeTo(internalFrame1);
        barang.jenis.setAlwaysOnTop(false);
        barang.jenis.setVisible(true);
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void nmsatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmsatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmsatKeyPressed

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
        NoNota.setText("");
        kdbar.setText("");
        nmbar.setText("");
        kdsat.setText("");
        nmsat.setText("");
        kdmem.setText("");
        nmmem.setText("");
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
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode.getRowCount()!=0){
                    Sequel.queryu("truncate table temporary_toko");
                    int row=tabMode.getRowCount();
                    for(int i=0;i<row;i++){  
                        Sequel.menyimpan("temporary_toko","'0','"+
                                        tabMode.getValueAt(i,0).toString()+"','"+
                                        tabMode.getValueAt(i,1).toString()+"','"+
                                        tabMode.getValueAt(i,2).toString()+"','"+
                                        tabMode.getValueAt(i,3).toString()+"','"+
                                        tabMode.getValueAt(i,4).toString()+"','"+
                                        tabMode.getValueAt(i,5).toString()+"','"+
                                        tabMode.getValueAt(i,6).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penjualan"); 
                    }
                    Sequel.menyimpan("temporary_toko","'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penjualan"); 
                    Sequel.menyimpan("temporary_toko","'0','','Total PPN : "+df2.format(ttlppn)+"','Total Ongkir : "+df2.format(ttlongkir)+"','','','Jumlah Total :','"+LTotal.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penjualan"); 
                   
                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptPenjualanToko.jasper","report","::[ Transaksi Penjualan Barang Toko ]::",param);
                }  break;
            case 1:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {

                    File g = new File("filetokopenjualan.css");            
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

                    File f = new File("LaporanPenjualan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML1.getText().replaceAll("<head>","<head>"+
                            "<link href=\"filetokopenjualan.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>Data Penjualan Barang Toko / Minimarket / Koperasi Periode "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,kdbar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void ppCetakNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCetakNotaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.panggilUrl("billing/NotaToko2.php?nonota="+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim());          
            this.setCursor(Cursor.getDefaultCursor());
        }            
    }//GEN-LAST:event_ppCetakNotaActionPerformed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
    if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        TCari.requestFocus();
    }else if(tbDokter.getSelectedRow()<= -1){
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
    }else{
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       try {
           pscarijual=koneksi.prepareStatement(
                   "select nota_jual,kd_rek,total from tokopenjualan where nota_jual=?");
           try {
              pscarijual.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().trim());
              rs=pscarijual.executeQuery();
              if(rs.next()){
                  Sequel.AutoComitFalse();
                  sukses=true;
                  pstoko_detail_jual=koneksi.prepareStatement(
                       "select kode_brng,jumlah from toko_detail_jual where nota_jual=? ");
                  try {
                      pstoko_detail_jual.setString(1,rs.getString(1));                
                      rs2=pstoko_detail_jual.executeQuery();
                      while(rs2.next()){
                          Trackbarang.catatRiwayat(rs2.getString("kode_brng"),rs2.getDouble("jumlah"),0,"Penjualan", akses.getkode(),"Hapus");
                          Sequel.mengedit("tokobarang","kode_brng=?","stok=stok+?",2,new String[]{
                               rs2.getString("jumlah"),rs2.getString("kode_brng")
                          });
                      }
                  } catch (Exception e) {
                      sukses=false;
                      System.out.println("Notifikasi : "+e);
                  } finally{
                      if(rs2!=null){
                          rs2.close();
                      }
                      if(pstoko_detail_jual!=null){
                          pstoko_detail_jual.close();
                      }
                  }

                  if(sukses==true){
                        ttljual=rs.getDouble("total");
                        ttlhpp=Sequel.cariIsiAngka("select sum(h_beli*jumlah) from toko_detail_jual where nota_jual=?",rs.getString("nota_jual"));

                        Sequel.queryu("delete from tampjurnal");
                        Sequel.menyimpan("tampjurnal","'"+Penjualan_Toko+"','PENJUALAN','"+ttljual+"','0'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+rs.getString("kd_rek")+"','KAS DI TANGAN','0','"+ttljual+"'","Rekening"); 
                        Sequel.menyimpan("tampjurnal","'"+HPP_Barang_Toko+"','HPP Barang Toko','0','"+ttlhpp+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_Barang_Toko+"','Persediaan Barang Toko','"+ttlhpp+"','0'","Rekening");                              
                        sukses=jur.simpanJurnal(rs.getString("nota_jual"),Sequel.cariIsi("select current_date()"),"U","BATAL PENJUALAN BARANG TOKO / MINIMARKET / KOPERASI, OLEH "+akses.getkode());
                  } 

                  if(sukses==true){
                      Sequel.queryu("delete from tokopenjualan where nota_jual='"+rs.getString("nota_jual")+"'");     
                      Sequel.Commit();
                  }else{
                      sukses=false;
                      JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menghapus..!!");
                      Sequel.RollBack();
                  }

                  Sequel.AutoComitTrue();
                  if(sukses==true){
                      tampil();
                  } 
              }         
           } catch (Exception e) {
               System.out.println("Notifikasi : "+e);
           } finally{
               if(rs!=null){
                   rs.close();
               }
               if(pscarijual!=null){
                   pscarijual.close();
               }
           }            
       } catch (Exception ex) {
           System.out.println(ex);
       }  
       this.setCursor(Cursor.getDefaultCursor());
    }           
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

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
            TokoCariPenjualan dialog = new TokoCariPenjualan(new javax.swing.JFrame(), true);
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
    private widget.Label LTotal;
    private widget.editorpane LoadHTML1;
    private widget.TextBox NoNota;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBarang;
    private widget.Button btnPasien;
    private widget.Button btnPetugas;
    private widget.Button btnSatuan;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.TextBox kdmem;
    private widget.TextBox kdptg;
    private widget.TextBox kdsat;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.TextBox nmsat;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppCetakNota;
    private javax.swing.JMenuItem ppHapus;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        tanggal=" tokopenjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ";
        nofak="";mem="";ptg="";sat="";bar="";
        if(!NoNota.getText().equals("")){
            nofak=" and tokopenjualan.nota_jual='"+NoNota.getText()+"' ";
        }        
        if(!nmmem.getText().equals("")){
            mem=" and tokopenjualan.nm_member='"+nmmem.getText()+"' ";
        }
        if(!nmptg.getText().equals("")){
            ptg=" and petugas.nama='"+nmptg.getText()+"' ";
        }
        if(!nmsat.getText().equals("")){
            sat=" and tokojenisbarang.nm_jenis='"+nmsat.getText()+"' ";
        }
        if(!nmbar.getText().equals("")){
            bar=" and tokobarang.nama_brng='"+nmbar.getText()+"' ";
        }

        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select tokopenjualan.nota_jual, tokopenjualan.tgl_jual, "+
                "tokopenjualan.nip,petugas.nama,tokopenjualan.ongkir,tokopenjualan.total, "+
                "tokopenjualan.no_member,tokopenjualan.nm_member,tokopenjualan.keterangan, "+
                "tokopenjualan.jns_jual, tokopenjualan.ppn,tokopenjualan.nama_bayar "+
                "from tokopenjualan inner join petugas on tokopenjualan.nip=petugas.nip "+
                "inner join toko_detail_jual on tokopenjualan.nota_jual=toko_detail_jual.nota_jual "+
                "inner join tokobarang on toko_detail_jual.kode_brng=tokobarang.kode_brng "+
                "inner join tokojenisbarang on tokobarang.jenis=tokojenisbarang.kd_jenis "+
                "inner join kodesatuan on toko_detail_jual.kode_sat=kodesatuan.kode_sat "+
                "where "+tanggal+nofak+mem+ptg+sat+bar+" and "+
                "(tokopenjualan.nota_jual like '%"+TCari.getText()+"%' or tokopenjualan.no_member like '%"+TCari.getText()+"%' or "+
                "tokopenjualan.nm_member like '%"+TCari.getText()+"%' or tokopenjualan.nip like '%"+TCari.getText()+"%' or "+
                "petugas.nama like '%"+TCari.getText()+"%' or tokopenjualan.keterangan like '%"+TCari.getText()+"%' or "+
                "tokopenjualan.jns_jual like '%"+TCari.getText()+"%' or toko_detail_jual.kode_brng like '%"+TCari.getText()+"%' or "+
                "tokobarang.nama_brng like '%"+TCari.getText()+"%' or toko_detail_jual.kode_sat like '%"+TCari.getText()+"%' or "+
                "tokojenisbarang.nm_jenis like '%"+TCari.getText()+"%') "+
                "group by tokopenjualan.nota_jual order by tokopenjualan.tgl_jual,tokopenjualan.nota_jual ");
            try {
                rs=ps.executeQuery();
                ttljual=0;
                ttlsubttl=0;
                ttlppn=0;
                ttltambahan=0;
                ttldisc=0;
                ttlongkir=0;
                while(rs.next()){        
                    tabMode.addRow(new String[]{
                        rs.getString("tgl_jual"),rs.getString("nota_jual"),rs.getString("nip")+", "+rs.getString("nama"),
                        rs.getString("no_member")+" "+rs.getString("nm_member"),rs.getString("keterangan"),rs.getString("jns_jual"),rs.getString("nama_bayar")
                    });
                    tabMode.addRow(new String[]{
                        "Kode Barang","Nama Barang","Jml & Satuan & Harga(Rp)","Subtotal(Rp)","Ptg(%) Potongan(Rp)","Tambahan(Rp)","Total"
                    });
                    ttlppn=ttlppn+rs.getDouble("ppn");
                    ttlongkir=ttlongkir+rs.getDouble("ongkir");
                    ps2=koneksi.prepareStatement(
                        "select toko_detail_jual.kode_brng,tokobarang.nama_brng, toko_detail_jual.kode_sat,"+
                        " kodesatuan.satuan,toko_detail_jual.h_jual,toko_detail_jual.jumlah, "+
                        " toko_detail_jual.subtotal,toko_detail_jual.dis,toko_detail_jual.bsr_dis,"+
                        " toko_detail_jual.tambahan,toko_detail_jual.total from "+
                        " toko_detail_jual inner join tokobarang on toko_detail_jual.kode_brng=tokobarang.kode_brng "+
                        " inner join kodesatuan on toko_detail_jual.kode_sat=kodesatuan.kode_sat "+
                        " inner join tokojenisbarang on tokobarang.jenis=tokojenisbarang.kd_jenis "+
                        " where toko_detail_jual.nota_jual='"+rs.getString(1)+"' "+sat+bar+" and "+
                        " (toko_detail_jual.kode_brng like '%"+TCari.getText()+"%' or tokobarang.nama_brng like '%"+TCari.getText()+"%' or "+
                        " toko_detail_jual.kode_sat like '%"+TCari.getText()+"%' or tokojenisbarang.nm_jenis like '%"+TCari.getText()+"%')"+
                        " order by toko_detail_jual.kode_brng");
                    try {
                        rs2=ps2.executeQuery();
                        subttlall=0;
                        subttldisc=0;
                        subttljual=0;
                        subttltambahan=0;
                        no=1;
                        while(rs2.next()){
                            subttlall=subttlall+rs2.getDouble("subtotal");
                            ttlsubttl=ttlsubttl+rs2.getDouble("subtotal");
                            subttldisc=subttldisc+rs2.getDouble("bsr_dis");
                            ttldisc=ttldisc+rs2.getDouble("bsr_dis");
                            subttltambahan=subttltambahan+rs2.getDouble("tambahan");
                            ttltambahan=ttltambahan+rs2.getDouble("tambahan");
                            subttljual=subttljual+rs2.getDouble("total");
                            ttljual=ttljual+rs2.getDouble("total");
                            tabMode.addRow(new String[]{
                                no+". "+rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("jumlah")+" "+rs2.getString("satuan")+" x "+df2.format(rs2.getDouble("h_jual")),
                                df2.format(rs2.getDouble("subtotal")),"("+df2.format(rs2.getDouble("dis"))+" %) "+df2.format(rs2.getDouble("bsr_dis")),df2.format(rs2.getDouble("tambahan")),
                                df2.format(rs2.getDouble("total"))
                            });
                            no++;
                        }                        
                        tabMode.addRow(new String[]{
                            "","Total",":",df2.format(subttlall),df2.format(subttldisc),df2.format(subttltambahan),df2.format(subttljual)
                        });
                        if(rs.getDouble("ppn")>0){
                            tabMode.addRow(new String[]{
                                "","PPN",":","","","",df2.format(rs.getDouble("ppn"))
                            });
                        }
                        if(rs.getDouble("ongkir")>0){
                            tabMode.addRow(new String[]{
                                "","Ongkir",":","","","",df2.format(rs.getDouble("ongkir"))
                            });
                        }   
                        tabMode.addRow(new String[]{
                            "","Total+PPN+Ongkir",":","","","",df2.format(rs.getDouble("ppn")+rs.getDouble("ongkir")+subttljual)
                        });    
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
            LTotal.setText(df2.format(ttljual+ttlppn+ttlongkir));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }        
    }
    
    public void tampil2(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tanggal="  tokopenjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ";
        nofak="";mem="";ptg="";sat="";bar="";
        if(!NoNota.getText().equals("")){
            nofak=" and tokopenjualan.nota_jual='"+NoNota.getText()+"' ";
        }        
        if(!nmmem.getText().equals("")){
            mem=" and tokopenjualan.nm_member='"+nmmem.getText()+"' ";
        }
        if(!nmptg.getText().equals("")){
            ptg=" and petugas.nama='"+nmptg.getText()+"' ";
        }
        if(!nmsat.getText().equals("")){
            sat=" and tokojenisbarang.nm_jenis='"+nmsat.getText()+"' ";
        }
        if(!nmbar.getText().equals("")){
            bar=" and tokobarang.nama_brng='"+nmbar.getText()+"' ";
        }
        
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='head'>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='7%'>No.Nota</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='6%'>Tanggal</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='26%'>Petugas</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='26%'>Member</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='7%'>Jenis Jual</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='10%'>Catatan</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='18%'>Cara Bayar</td>"+
                "</tr>"); 
            ps=koneksi.prepareStatement(
                    "select tokopenjualan.nota_jual, tokopenjualan.tgl_jual,tokopenjualan.kd_rek, "+
                    "tokopenjualan.nip,petugas.nama,tokopenjualan.ongkir,tokopenjualan.total, "+
                    "tokopenjualan.no_member,tokopenjualan.nm_member,tokopenjualan.keterangan, "+
                    "tokopenjualan.jns_jual, tokopenjualan.ppn,tokopenjualan.nama_bayar "+
                    "from tokopenjualan inner join petugas on tokopenjualan.nip=petugas.nip "+
                    "inner join toko_detail_jual on tokopenjualan.nota_jual=toko_detail_jual.nota_jual "+
                    "inner join tokobarang on toko_detail_jual.kode_brng=tokobarang.kode_brng "+
                    "inner join tokojenisbarang on tokobarang.jenis=tokojenisbarang.kd_jenis "+
                    "inner join kodesatuan on toko_detail_jual.kode_sat=kodesatuan.kode_sat "+
                    "where "+tanggal+nofak+mem+ptg+sat+bar+" and "+
                    "(tokopenjualan.nota_jual like '%"+TCari.getText()+"%' or tokopenjualan.no_member like '%"+TCari.getText()+"%' or "+
                    "tokopenjualan.nm_member like '%"+TCari.getText()+"%' or tokopenjualan.nip like '%"+TCari.getText()+"%' or "+
                    "petugas.nama like '%"+TCari.getText()+"%' or tokopenjualan.keterangan like '%"+TCari.getText()+"%' or "+
                    "tokopenjualan.jns_jual like '%"+TCari.getText()+"%' or toko_detail_jual.kode_brng like '%"+TCari.getText()+"%' or "+
                    "tokobarang.nama_brng like '%"+TCari.getText()+"%' or toko_detail_jual.kode_sat like '%"+TCari.getText()+"%' or "+
                    "tokojenisbarang.nm_jenis like '%"+TCari.getText()+"%') "+
                    "group by tokopenjualan.nota_jual order by tokopenjualan.tgl_jual,tokopenjualan.nota_jual ");
            try {
                rs=ps.executeQuery();
                ttljual=0;
                ttlsubttl=0;
                ttlppn=0;
                ttltambahan=0;
                ttldisc=0;
                ttlongkir=0;
                while(rs.next()){ 
                    ttlppn=ttlppn+rs.getDouble("ppn");
                    ttlongkir=ttlongkir+rs.getDouble("ongkir");
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+rs.getString("nota_jual")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("tgl_jual")+"</td>"+
                            "<td valign='top'>"+rs.getString("nip")+" "+rs.getString("nama")+"</td>"+
                            "<td valign='top'>"+rs.getString("no_member")+" "+rs.getString("nm_member")+"</td>"+
                            "<td valign='top'>"+rs.getString("jns_jual")+"</td>"+
                            "<td valign='top'>"+rs.getString("keterangan")+"</td>"+
                            "<td valign='top'>"+rs.getString("nama_bayar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+
                            "<td></td>"+
                            "<td colspan='6'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='1%'>No.</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='8%'>Kode Barang</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='27%'>Nama Barang</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='4%'>Jml</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='5%'>Satuan</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='9%'>Harga(Rp)</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='10%'>Subtotal(Rp)</td>"+    
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='3%'>Ptg(%)</td>"+    
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='10%'>Potongan(Rp)</td>"+    
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='10%'>Tambahan(Rp)</td>"+        
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='13%'>Total(Rp)</td>"+                                      
                                    "</tr>");
                    ps2=koneksi.prepareStatement(
                        "select toko_detail_jual.kode_brng,tokobarang.nama_brng, toko_detail_jual.kode_sat,"+
                        " kodesatuan.satuan,toko_detail_jual.h_jual,toko_detail_jual.jumlah, "+
                        " toko_detail_jual.subtotal,toko_detail_jual.dis,toko_detail_jual.bsr_dis,"+
                        " toko_detail_jual.tambahan,toko_detail_jual.total from "+
                        " toko_detail_jual inner join tokobarang on toko_detail_jual.kode_brng=tokobarang.kode_brng "+
                        " inner join kodesatuan on toko_detail_jual.kode_sat=kodesatuan.kode_sat "+
                        " inner join tokojenisbarang on tokobarang.jenis=tokojenisbarang.kd_jenis "+
                        " where toko_detail_jual.nota_jual='"+rs.getString(1)+"' "+sat+bar+" and "+
                        " (toko_detail_jual.kode_brng like '%"+TCari.getText()+"%' or tokobarang.nama_brng like '%"+TCari.getText()+"%' or "+
                        " toko_detail_jual.kode_sat like '%"+TCari.getText()+"%' or tokojenisbarang.nm_jenis like '%"+TCari.getText()+"%')"+
                        " order by toko_detail_jual.kode_brng");
                    try {
                        rs2=ps2.executeQuery();
                        subttlall=0;
                        subttldisc=0;
                        subttljual=0;
                        subttltambahan=0;
                        no=1;
                        while(rs2.next()){
                            subttlall=subttlall+rs2.getDouble("subtotal");
                            ttlsubttl=ttlsubttl+rs2.getDouble("subtotal");
                            subttldisc=subttldisc+rs2.getDouble("bsr_dis");
                            ttldisc=ttldisc+rs2.getDouble("bsr_dis");
                            subttltambahan=subttltambahan+rs2.getDouble("tambahan");
                            ttltambahan=ttltambahan+rs2.getDouble("tambahan");
                            subttljual=subttljual+rs2.getDouble("total");
                            ttljual=ttljual+rs2.getDouble("total");
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='top' align='center'>"+no+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("kode_brng")+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("nama_brng")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("jumlah")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("satuan")+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("h_jual"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("subtotal"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("dis"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("bsr_dis"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("tambahan"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("total"))+"</td>"+
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
                    
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center' colspan='5'>PPN : "+df2.format(rs.getDouble("ppn"))+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ongkir : "+df2.format(rs.getDouble("ongkir"))+"</td>"+
                            "<td valign='top' align='right'>Total :</td>"+
                            "<td valign='top' align='right'>"+df2.format(subttlall)+"</td>"+
                            "<td valign='top' align='right'></td>"+
                            "<td valign='top' align='right'>"+df2.format(subttldisc)+"</td>"+
                            "<td valign='top' align='right'>"+df2.format(subttltambahan)+"</td>"+
                            "<td valign='top' align='right'>"+df2.format(subttljual+rs.getDouble("ppn")+rs.getDouble("ongkir"))+"</td>"+
                        "</tr>");
                    htmlContent.append(
                                "</table>"+
                            "</td>"+
                        "</tr>");
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
            if(ttljual>0){
                totaljual=
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                          "<tr class='isi'>"+
                             "<td valign='top' align='right' width='16%'>Jml.Ongkir : "+df2.format(ttlongkir)+"</td>"+
                             "<td valign='top' align='right' width='16%'>Jml.PPN : "+df2.format(ttlppn)+"</td>"+
                             "<td valign='top' align='right' width='16%'>Jml.Subtotal : "+df2.format(ttlsubttl)+"</td>"+
                             "<td valign='top' align='right' width='16%'>Jml.Diskon : "+df2.format(ttldisc)+"</td>"+
                             "<td valign='top' align='right' width='16%'>Jml.Tambahan : "+df2.format(ttltambahan)+"</td>"+
                             "<td valign='top' align='right' width='16%'>Jml.Total : "+df2.format(ttljual+ttlongkir+ttlppn)+"</td>"+
                           "</tr>"+
                      "</table>";
            }
            LoadHTML1.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+totaljual+
                    "</html>");
            LTotal.setText(df2.format(ttljual+ttlppn+ttlongkir));
        }catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdsat.setText("");
        kdbar.requestFocus();        
    }   
    
    public void isCek(){
        ppCetakNota.setEnabled(akses.gettoko_penjualan());
        if(akses.getkode().equals("Admin Utama")){
            ppHapus.setEnabled(true);
        }else{
            ppHapus.setEnabled(false);
        }  
    }
}

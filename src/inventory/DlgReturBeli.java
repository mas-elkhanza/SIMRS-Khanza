package inventory;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

public class DlgReturBeli extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private riwayatobat Trackobat=new riwayatobat();
    private DlgSuplier suplier=new DlgSuplier(null,false);
    private DlgCariReturBeli form=new DlgCariReturBeli(null,false);
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private PreparedStatement ps;
    private ResultSet rs;
    private double ttlretur=0,jumlahretur=0,stokobat=0;
    private String aktifkanbatch="no";
    private boolean sukses=true;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgReturBeli(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
        }

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Faktur","Kode Barang","Nama Barang","Satuan","Hrg.Retur(Rp)","Jml.Retur","SubtotalRetur(Rp)","No.Batch","Kadaluarsa"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
            Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.String.class,
                java.lang.String.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
               return types [columnIndex];
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(60);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(60);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(70);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());


        NoRetur.setDocument(new batasInput((byte)20).getKata(NoRetur));
        NoFaktur.setDocument(new batasInput((byte)20).getKata(NoFaktur));
        Kdptg.setDocument(new batasInput((byte)25).getKata(Kdptg));
        kdsup.setDocument(new batasInput((byte)5).getKata(kdsup));
        Kdbar.setDocument(new batasInput((byte)15).getKata(Kdbar));
        NoBatch.setDocument(new batasInput((byte)20).getKata(NoBatch));
        //Jmlbeli.setDocument(new batasInput((byte)13).getKata(Jmlbeli));
        Jmlretur.setDocument(new batasInput((byte)13).getKata(Jmlretur));
        Hargaretur.setDocument(new batasInput((byte)13).getKata(Hargaretur));

        Jmlretur.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        Hargaretur.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });

        form.barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgReturBeli")){
                    if(form.barang.getTable().getSelectedRow()!= -1){
                        Kdbar.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),1).toString());
                        nmbar.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),2).toString());
                        Satuanbar.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),6).toString());
                        satuanretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),6).toString());
                        Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),10).toString());
                        if(aktifkanbatch.equals("yes")){
                            NoBatch.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),32).toString());
                            NoFaktur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),33).toString());
                            try{
                                Kadaluwarsa.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),25).toString());
                            }catch(Exception ex){
                                Kadaluwarsa.setText("0000-00-00");
                            }
                        }
                    }
                    Kdbar.requestFocus();
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

        form.barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgReturBeli")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        form.barang.dispose();
                        Kdbar.requestFocus();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        form.barang.satuan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgReturBeli")){
                    if(form.barang.satuan.getTable().getSelectedRow()!= -1){
                        satuanretur.setText(form.barang.satuan.getTable().getValueAt(form.barang.satuan.getTable().getSelectedRow(),0).toString());
                        isHitung();
                    }
                    satuanretur.requestFocus();
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

        form.petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgReturBeli")){
                    if(form.petugas.getTable().getSelectedRow()!= -1){
                        Kdptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(),0).toString());
                        Nmptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(),1).toString());
                    }
                    Kdptg.requestFocus();
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

        suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgReturBeli")){
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
                if(akses.getform().equals("DlgReturBeli")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        suplier.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgReturBeli")){
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
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnTambah = new widget.Button();
        BtnHapus = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label25 = new widget.Label();
        Subtotal = new widget.TextBox();
        label27 = new widget.Label();
        Hargaretur = new widget.TextBox();
        BtnBrg = new widget.Button();
        BtnKnv = new widget.Button();
        satuanretur = new widget.TextBox();
        Jmlretur = new widget.TextBox();
        label26 = new widget.Label();
        label17 = new widget.Label();
        Kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        Satuanbar = new widget.TextBox();
        label21 = new widget.Label();
        NoBatch = new widget.TextBox();
        label22 = new widget.Label();
        Kadaluwarsa = new widget.TextBox();
        panelisiBeli = new widget.panelisi();
        label15 = new widget.Label();
        NoRetur = new widget.TextBox();
        label18 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label13 = new widget.Label();
        Kdptg = new widget.TextBox();
        Nmptg = new widget.TextBox();
        BtnPtg = new widget.Button();
        label11 = new widget.Label();
        TglRetur = new widget.Tanggal();
        label16 = new widget.Label();
        kdsup = new widget.TextBox();
        nmsup = new widget.TextBox();
        BtnSpl = new widget.Button();
        label32 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Transaksi Retur Obat, Alkes & BHP Medis Ke Suplier ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 130));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambah.setMnemonic('T');
        BtnTambah.setText("Tambah");
        BtnTambah.setToolTipText("Alt+T");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        BtnTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTambahKeyPressed(evt);
            }
        });
        panelisi1.add(BtnTambah);

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
        panelisi1.add(BtnHapus);

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(LTotal);

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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Clear");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBatal);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('E');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+E");
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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label25.setText("SubTotal :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label25);
        label25.setBounds(572, 40, 80, 23);

        Subtotal.setEditable(false);
        Subtotal.setName("Subtotal"); // NOI18N
        Subtotal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(Subtotal);
        Subtotal.setBounds(656, 40, 117, 23);

        label27.setText("Hrga.Retur :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label27);
        label27.setBounds(572, 10, 80, 23);

        Hargaretur.setName("Hargaretur"); // NOI18N
        Hargaretur.setPreferredSize(new java.awt.Dimension(80, 23));
        Hargaretur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HargareturKeyPressed(evt);
            }
        });
        panelisi4.add(Hargaretur);
        Hargaretur.setBounds(656, 10, 117, 23);

        BtnBrg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnBrg.setMnemonic('3');
        BtnBrg.setToolTipText("Alt+3");
        BtnBrg.setName("BtnBrg"); // NOI18N
        BtnBrg.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBrgActionPerformed(evt);
            }
        });
        panelisi4.add(BtnBrg);
        BtnBrg.setBounds(526, 10, 28, 23);

        BtnKnv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKnv.setMnemonic('4');
        BtnKnv.setToolTipText("Alt+4");
        BtnKnv.setName("BtnKnv"); // NOI18N
        BtnKnv.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKnv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKnvActionPerformed(evt);
            }
        });
        panelisi4.add(BtnKnv);
        BtnKnv.setBounds(526, 40, 28, 23);

        satuanretur.setName("satuanretur"); // NOI18N
        satuanretur.setPreferredSize(new java.awt.Dimension(80, 23));
        satuanretur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                satuanreturKeyPressed(evt);
            }
        });
        panelisi4.add(satuanretur);
        satuanretur.setBounds(471, 40, 53, 23);

        Jmlretur.setName("Jmlretur"); // NOI18N
        Jmlretur.setPreferredSize(new java.awt.Dimension(80, 23));
        Jmlretur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JmlreturKeyPressed(evt);
            }
        });
        panelisi4.add(Jmlretur);
        Jmlretur.setBounds(414, 40, 55, 23);

        label26.setText("Jml.Retur :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label26);
        label26.setBounds(340, 40, 70, 23);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 10, 63, 23);

        Kdbar.setName("Kdbar"); // NOI18N
        Kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        Kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdbarKeyPressed(evt);
            }
        });
        panelisi4.add(Kdbar);
        Kdbar.setBounds(67, 10, 100, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(169, 10, 300, 23);

        Satuanbar.setEditable(false);
        Satuanbar.setName("Satuanbar"); // NOI18N
        Satuanbar.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(Satuanbar);
        Satuanbar.setBounds(471, 10, 53, 23);

        label21.setText("No.Batch :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label21);
        label21.setBounds(0, 40, 63, 23);

        NoBatch.setName("NoBatch"); // NOI18N
        NoBatch.setPreferredSize(new java.awt.Dimension(80, 23));
        NoBatch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBatchKeyPressed(evt);
            }
        });
        panelisi4.add(NoBatch);
        NoBatch.setBounds(67, 40, 100, 23);

        label22.setText("Kadaluwarsa :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label22);
        label22.setBounds(170, 40, 80, 23);

        Kadaluwarsa.setName("Kadaluwarsa"); // NOI18N
        Kadaluwarsa.setPreferredSize(new java.awt.Dimension(80, 23));
        Kadaluwarsa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KadaluwarsaKeyPressed(evt);
            }
        });
        panelisi4.add(Kadaluwarsa);
        Kadaluwarsa.setBounds(254, 40, 90, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisiBeli.setName("panelisiBeli"); // NOI18N
        panelisiBeli.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisiBeli.setLayout(null);

        label15.setText("No.Retur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisiBeli.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoRetur.setName("NoRetur"); // NOI18N
        NoRetur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoReturKeyPressed(evt);
            }
        });
        panelisiBeli.add(NoRetur);
        NoRetur.setBounds(79, 10, 210, 23);

        label18.setText("No.Faktur :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisiBeli.add(label18);
        label18.setBounds(0, 40, 75, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisiBeli.add(NoFaktur);
        NoFaktur.setBounds(79, 40, 210, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label13);
        label13.setBounds(310, 40, 100, 23);

        Kdptg.setName("Kdptg"); // NOI18N
        Kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        Kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdptgKeyPressed(evt);
            }
        });
        panelisiBeli.add(Kdptg);
        Kdptg.setBounds(414, 40, 105, 23);

        Nmptg.setEditable(false);
        Nmptg.setName("Nmptg"); // NOI18N
        Nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisiBeli.add(Nmptg);
        Nmptg.setBounds(521, 40, 220, 23);

        BtnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg.setMnemonic('2');
        BtnPtg.setToolTipText("Alt+2");
        BtnPtg.setName("BtnPtg"); // NOI18N
        BtnPtg.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtgActionPerformed(evt);
            }
        });
        panelisiBeli.add(BtnPtg);
        BtnPtg.setBounds(744, 40, 28, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label11);
        label11.setBounds(0, 70, 75, 23);

        TglRetur.setDisplayFormat("dd-MM-yyyy");
        TglRetur.setName("TglRetur"); // NOI18N
        TglRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglReturKeyPressed(evt);
            }
        });
        panelisiBeli.add(TglRetur);
        TglRetur.setBounds(79, 70, 110, 23);

        label16.setText("Supplier :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisiBeli.add(label16);
        label16.setBounds(310, 10, 100, 23);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(80, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisiBeli.add(kdsup);
        kdsup.setBounds(414, 10, 105, 23);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisiBeli.add(nmsup);
        nmsup.setBounds(521, 10, 220, 23);

        BtnSpl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSpl.setMnemonic('1');
        BtnSpl.setToolTipText("Alt+1");
        BtnSpl.setName("BtnSpl"); // NOI18N
        BtnSpl.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSpl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSplActionPerformed(evt);
            }
        });
        panelisiBeli.add(BtnSpl);
        BtnSpl.setBounds(744, 10, 28, 23);

        label32.setText("Lokasi :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label32);
        label32.setBounds(310, 70, 100, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisiBeli.add(kdgudang);
        kdgudang.setBounds(414, 70, 105, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisiBeli.add(nmgudang);
        nmgudang.setBounds(521, 70, 220, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        panelisiBeli.add(BtnGudang);
        BtnGudang.setBounds(744, 70, 28, 23);

        internalFrame1.add(panelisiBeli, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        if(nmbar.getText().trim().equals("")){
            Valid.textKosong(Kdbar,"barang");
        }else if(Jmlretur.getText().trim().equals("")){
            Valid.textKosong(Jmlretur,"jumlah retur");
        }else if(kdgudang.getText().trim().equals("")||nmgudang.getText().trim().equals("")){
            Valid.textKosong(BtnGudang,"Depo/Ruang");
        }else if(Jmlretur.getText().trim().equals("0")){
            Valid.textKosong(Jmlretur,"jumlah retur");
        }else if(aktifkanbatch.equals("yes")&&NoFaktur.getText().equals("")){
            Valid.textKosong(NoFaktur,"No.Faktur");
        }else if(aktifkanbatch.equals("yes")&&NoBatch.getText().equals("")){
            Valid.textKosong(NoBatch,"No.Batch");
        }else if(Hargaretur.getText().trim().equals("")){
            Valid.textKosong(Hargaretur,"harga retur");
        }else{
            try {
                stokobat=0;
                ps=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                try {
                    ps.setString(1,kdgudang.getText());
                    ps.setString(2,Kdbar.getText());
                    ps.setString(3,NoBatch.getText());
                    ps.setString(4,NoFaktur.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        stokobat=rs.getDouble(1);
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

                jumlahretur=Valid.SetAngka(Jmlretur.getText());
                if(jumlahretur>stokobat){
                    try {
                        Jmlretur.setText(""+stokobat);
                    } catch (Exception e) {
                    }
                    JOptionPane.showMessageDialog(null,"Maaf, stok tidak mencukupi. Maksimal yang bisa diretur "+stokobat+" "+satuanretur.getText());
                }else{
                    Sequel.menyimpan("tampreturbeli","'"+NoFaktur.getText()+"','"+Kdbar.getText()+"','"+nmbar.getText()+"','"+Satuanbar.getText()+
                                 "','0','0','"+Hargaretur.getText()+"','"+Jmlretur.getText()+"','"+Subtotal.getText()+"','"+NoBatch.getText()+"','"+jumlahretur+"','"+Kadaluwarsa.getText()+"','"+akses.getkode()+"'",
                                 "nama_brng='"+nmbar.getText()+"',satuan='"+Satuanbar.getText()+"',h_beli='0',jml_beli='0',h_retur='"+
                                  Hargaretur.getText()+"',jml_retur='"+jumlahretur+"',total='"+Subtotal.getText()+"',no_batch='"+NoBatch.getText()+"',jml_retur2='"+jumlahretur+"'",
                                 "kode_brng='"+Kdbar.getText()+"' and no_faktur='"+NoFaktur.getText()+"'");
                    emptTeks();
                    tampil();
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }
}//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnTambahActionPerformed(null);
        }else{
            Valid.pindah(evt,Hargaretur, BtnHapus);
        }
}//GEN-LAST:event_BtnTambahKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(nmbar.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.hapusTable(tabMode,Kdbar," tampreturbeli","no_batch='"+NoBatch.getText()+"' and no_faktur='"+NoFaktur.getText()+"' and petugas='"+akses.getkode()+"' and kode_brng");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnTambah, BtnCari);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
        }else{Valid.pindah(evt,BtnBatal,Kdbar);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtgActionPerformed
        akses.setform("DlgReturBeli");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.petugas.emptTeks();
        form.petugas.isCek();
        form.petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.petugas.setLocationRelativeTo(internalFrame1);
        form.petugas.setAlwaysOnTop(false);
        form.petugas.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPtgActionPerformed

    private void TglReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglReturKeyPressed
        Valid.pindah(evt,NoRetur,Kdptg);
    }//GEN-LAST:event_TglReturKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRetur.getText().trim().equals("")){
            Valid.textKosong(NoRetur,"No.Retur");
        }else if(Nmptg.getText().trim().equals("")){
            Valid.textKosong(Kdptg,"Petugas");
        }else if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(nmsup.getText().trim().equals("")){
            Valid.textKosong(kdsup,"Suplier");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            Kdbar.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang hendak disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("returbeli","'"+NoRetur.getText()+"','"+Valid.SetTgl(TglRetur.getSelectedItem()+"")+"','"+Kdptg.getText()+"','"+kdsup.getText()+"','"+kdgudang.getText()+"'","data")==true){
                    simpan();
                }else{
                    autonomer();
                    if(Sequel.menyimpantf2("returbeli","'"+NoRetur.getText()+"','"+Valid.SetTgl(TglRetur.getSelectedItem()+"")+"','"+Kdptg.getText()+"','"+kdsup.getText()+"','"+kdgudang.getText()+"'","data")==true){
                        simpan();
                    }else{
                        autonomer();
                        sukses=false;
                    }
                }
                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");
                    Sequel.menyimpan2("tampjurnal","'"+Sequel.cariIsi("select Retur_Ke_Suplayer from set_akun")+"','RETUR PEMBELIAN','0','"+ttlretur+"'","Rekening");
                    Sequel.menyimpan2("tampjurnal","'"+Sequel.cariIsi("select Kontra_Retur_Ke_Suplayer from set_akun")+"','KAS DI TANGAN','"+ttlretur+"','0'","Rekening");
                    sukses=jur.simpanJurnal(NoRetur.getText(),Valid.SetTgl(TglRetur.getSelectedItem()+""),"U","RETUR PEMBELIAN DI "+nmgudang.getText().toUpperCase()+", OLEH "+akses.getkode());
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
                    BtnBatalActionPerformed(evt);
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Kdbar,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        Sequel.queryu("delete from tampreturbeli where petugas='"+akses.getkode()+"'");
        tampil();
        autonomer();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void NoReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoReturKeyPressed
        Valid.pindah(evt, BtnTambah, TglRetur);
    }//GEN-LAST:event_NoReturKeyPressed

    private void KdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", Nmptg,Kdptg.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", Nmptg,Kdptg.getText());
            TglRetur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", Nmptg,Kdptg.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPtgActionPerformed(null);
        }
    }//GEN-LAST:event_KdptgKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            //BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, Kdbar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
            Valid.pindah(evt, Kdptg, Jmlretur);
    }//GEN-LAST:event_NoFakturKeyPressed

    private void HargareturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HargareturKeyPressed
        Valid.pindah(evt, Jmlretur, BtnTambah);
    }//GEN-LAST:event_HargareturKeyPressed

private void BtnBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBrgActionPerformed
        akses.setform("DlgReturBeli");
        if(aktifkanbatch.equals("yes")){
            form.barang.aktifkanbatch="yes";
            form.barang.isBatch();
        }
        form.barang.emptTeks();
        form.barang.isCek();
        form.barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.barang.setLocationRelativeTo(internalFrame1);
        form.barang.setAlwaysOnTop(false);
        form.barang.setVisible(true);
}//GEN-LAST:event_BtnBrgActionPerformed

private void BtnKnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKnvActionPerformed
        akses.setform("DlgReturBeli");
        form.barang.satuan.emptTeks();
        form.barang.satuan.isCek();
        form.barang.satuan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.barang.satuan.setLocationRelativeTo(internalFrame1);
        form.barang.satuan.setAlwaysOnTop(false);
        form.barang.satuan.setVisible(true);
}//GEN-LAST:event_BtnKnvActionPerformed

private void satuanreturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_satuanreturKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isHitung();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isHitung();
            Jmlretur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isHitung();
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKnvActionPerformed(null);
        }
}//GEN-LAST:event_satuanreturKeyPressed

private void JmlreturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JmlreturKeyPressed
        Valid.pindah(evt, NoBatch,Hargaretur);
}//GEN-LAST:event_JmlreturKeyPressed

private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            NoFaktur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_suplier from datasuplier where kode_suplier=?", nmsup,kdsup.getText());
            Kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSplActionPerformed(null);
        }
}//GEN-LAST:event_kdsupKeyPressed

private void BtnSplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSplActionPerformed
        akses.setform("DlgReturBeli");
        suplier.emptTeks();
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
}//GEN-LAST:event_BtnSplActionPerformed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        Kdptg.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        BtnGudangActionPerformed(null);
    }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
    akses.setform("DlgReturBeli");
    bangsal.isCek();
    bangsal.emptTeks();
    bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    bangsal.setLocationRelativeTo(internalFrame1);
    bangsal.setAlwaysOnTop(false);
    bangsal.setVisible(true);
}//GEN-LAST:event_BtnGudangActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void KdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            cariBarang();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            cariBarang();
            Kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            cariBarang();
            Jmlretur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnBrgActionPerformed(null);
        }
    }//GEN-LAST:event_KdbarKeyPressed

    private void NoBatchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBatchKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            cariBatch();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            cariBatch();
            Kdbar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            cariBatch();
            Jmlretur.requestFocus();
        }
    }//GEN-LAST:event_NoBatchKeyPressed

    private void KadaluwarsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KadaluwarsaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KadaluwarsaKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgReturBeli dialog = new DlgReturBeli(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnBrg;
    private widget.Button BtnCari;
    private widget.Button BtnGudang;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKnv;
    private widget.Button BtnPtg;
    private widget.Button BtnSimpan;
    private widget.Button BtnSpl;
    private widget.Button BtnTambah;
    private widget.TextBox Hargaretur;
    private widget.TextBox Jmlretur;
    private widget.TextBox Kadaluwarsa;
    private widget.TextBox Kd2;
    private widget.TextBox Kdbar;
    private widget.TextBox Kdptg;
    private widget.Label LTotal;
    private widget.TextBox Nmptg;
    private widget.TextBox NoBatch;
    private widget.TextBox NoFaktur;
    private widget.TextBox NoRetur;
    private widget.TextBox Satuanbar;
    private widget.TextBox Subtotal;
    private widget.Tanggal TglRetur;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdgudang;
    private widget.TextBox kdsup;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label32;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmgudang;
    private widget.TextBox nmsup;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisiBeli;
    private widget.TextBox satuanretur;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
       Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select  no_faktur, kode_brng, nama_brng, satuan, h_beli, jml_beli, h_retur, jml_retur, total,no_batch,	kadaluarsa from  tampreturbeli where petugas=?");
            try {
                ttlretur=0;
                ps.setString(1,akses.getkode());
                rs=ps.executeQuery();
                while(rs.next()){
                    ttlretur=ttlretur+rs.getDouble(9);
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getDouble(7),
                        rs.getDouble(8),rs.getDouble(9),
                        rs.getString(10),rs.getString(11)
                    });
                }
                LTotal.setText(Valid.SetAngka(ttlretur));
            } catch (Exception e) {
                System.out.println("Notif Tampilkan Barang : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }

    }

    public void emptTeks() {
        Kdbar.setText("");
        nmbar.setText("");
        Satuanbar.setText("");
        satuanretur.setText("");
        Hargaretur.setText("0");
        Jmlretur.setText("0");
        Subtotal.setText("0");
        NoBatch.setText("");
        Kadaluwarsa.setText("");
        Kdbar.requestFocus();
    }

    private void getData() {
       int row=tbDokter.getSelectedRow();
        if(row!= -1){
             NoFaktur.setText(tabMode.getValueAt(row,0).toString());
             Kdbar.setText(tabMode.getValueAt(row,1).toString());
             nmbar.setText(tabMode.getValueAt(row,2).toString());
             Satuanbar.setText(tabMode.getValueAt(row,3).toString());
             Hargaretur.setText(tabMode.getValueAt(row,4).toString());
             Jmlretur.setText(tabMode.getValueAt(row,5).toString());
             NoBatch.setText(tabMode.getValueAt(row,7).toString());
             Kadaluwarsa.setText(tabMode.getValueAt(row,8).toString());
             isHitung();
        }
    }


    private void isHitung(){
        if((!Jmlretur.getText().equals(""))&&(!Hargaretur.getText().equals(""))){
            jumlahretur=0;
            if(satuanretur.getText().equals("")||satuanretur.getText().equals(Satuanbar.getText())){
                Subtotal.setText(Valid.SetAngka2(Double.parseDouble(Jmlretur.getText())*Double.parseDouble(Hargaretur.getText())));
                jumlahretur=Double.parseDouble(Jmlretur.getText());
            }else if(!satuanretur.getText().equals(Satuanbar.getText())){
                try {
                    ps=koneksi.prepareStatement(
                            "select nilai,nilai_konversi from konver_sat where kode_sat=? and sat_konversi=?");
                    try {
                        ps.setString(1,Satuanbar.getText());
                        ps.setString(2,satuanretur.getText());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            Subtotal.setText(Valid.SetAngka2(Double.parseDouble(Jmlretur.getText())*Double.parseDouble(Hargaretur.getText())*(rs.getDouble(1)/rs.getDouble(2))));
                            jumlahretur=Double.parseDouble(Jmlretur.getText())*(rs.getDouble(1)/rs.getDouble(2));
                        }else if(!rs.next()){
                            int reply = JOptionPane.showConfirmDialog(rootPane,"Maaf, konversi satuan tidak ditemukan. Apa anda ingin menambahkan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgKonversi formkonversi=new DlgKonversi(null,false);
                                formkonversi.emptTeks();
                                formkonversi.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                                formkonversi.setLocationRelativeTo(internalFrame1);
                                formkonversi.setAlwaysOnTop(false);
                                formkonversi.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }else{
                                satuanretur.setText(Satuanbar.getText());
                            }
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
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }else{
            Subtotal.setText("0");
        }
    }

    public void isCek(){
        autonomer();
        Sequel.cariIsi("select kd_bangsal from set_lokasi",kdgudang);
        Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?",nmgudang,kdgudang.getText());
        if(akses.getjml2()>=1){
            Kdptg.setEditable(false);
            BtnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.getretur_ke_suplier());
            BtnTambah.setEnabled(akses.getretur_ke_suplier());
            BtnHapus.setEnabled(akses.getretur_ke_suplier());
            BtnBatal.setEnabled(akses.getretur_ke_suplier());
            Kdptg.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip='"+Kdptg.getText()+"'", Nmptg);
        }
    }


    private void cariBarang(){
        try {
            ps=koneksi.prepareStatement("select * from databarang where kode_brng=?");
            try {
                ps.setString(1,Kdbar.getText());
                rs=ps.executeQuery();
                while(rs.next()){
                    nmbar.setText(rs.getString("nama_brng"));
                    Satuanbar.setText(rs.getString("kode_sat"));
                    Hargaretur.setText(rs.getString("dasar"));
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
        } catch (Exception ex) {
            System.out.println("Catatan barang : "+ex);
        }
    }

    private void cariBatch() {
        try {
            ps=koneksi.prepareStatement("select * from data_batch where no_batch=? and kode_brng=? and no_faktur=?");
            try {
                ps.setString(1,NoBatch.getText());
                ps.setString(2,Kdbar.getText());
                ps.setString(3,NoFaktur.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    Kadaluwarsa.setText(rs.getString("tgl_kadaluarsa"));
                    if(aktifkanbatch.equals("yes")){
                        Hargaretur.setText(rs.getString("dasar"));
                    }
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

    private void autonomer() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_retur_beli,3),signed)),0) from returbeli where tgl_retur='"+Valid.SetTgl(TglRetur.getSelectedItem()+"")+"' ",
                "RB"+TglRetur.getSelectedItem().toString().substring(8,10)+TglRetur.getSelectedItem().toString().substring(3,5)+TglRetur.getSelectedItem().toString().substring(0,2),3,NoRetur);
    }

    private void simpan() {
        try {
            ps=koneksi.prepareStatement("select no_faktur, kode_brng, satuan, h_beli, jml_beli, h_retur, jml_retur, total,no_batch, jml_retur2 from tampreturbeli where petugas=?");
            try {
                ps.setString(1,akses.getkode());
                rs=ps.executeQuery();
                while(rs.next()){
                    if(Sequel.menyimpantf2("detreturbeli","'"+NoRetur.getText()+"','"+rs.getString(1) +"','"+rs.getString(2) +"','"+rs.getString(3) +
                        "','"+rs.getString(4)+"','"+rs.getString(5)+"','"+rs.getString(6)+"','"+rs.getString(7)+"','"+rs.getString(8)+"','"+rs.getString(9)+"','"+rs.getString(10)+"'","data")==true){
                        Trackobat.catatRiwayat(rs.getString(2),0,rs.getDouble(10),"Retur Beli",akses.getkode(),kdgudang.getText(),"Simpan",rs.getString("no_batch"),rs.getString("no_faktur"));
                        Sequel.menyimpan("gudangbarang","'"+rs.getString(2)+"','"+kdgudang.getText()+"','-"+rs.getString(10)+"','"+rs.getString("no_batch")+"','"+rs.getString("no_faktur")+"'",
                                    "stok=stok-'"+rs.getString(10)+"'","kode_brng='"+rs.getString(2)+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='"+rs.getString("no_batch")+"' and no_faktur='"+rs.getString("no_faktur")+"'");
                        if(aktifkanbatch.equals("yes")){
                            Sequel.mengedit("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                rs.getString(10),rs.getString(9),rs.getString(2),rs.getString("no_faktur")
                            });
                        }
                    }else{
                       sukses=false;
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
        } catch (Exception ex) {
            System.out.println("Notif : "+ex);
        }
    }
}

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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

public class DlgPiutang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private DlgCariPiutang form=new DlgCariPiutang(null,false);
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private riwayatobat Trackobat=new riwayatobat();
    private double ttljual=0,stok,jumlah;
    private PreparedStatement ps;
    private ResultSet rs;
    private String aktifkanbatch="no";
    private boolean sukses=true;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgPiutang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
        }

        Object[] row={"Kode Barang","Nama Barang","Satuan","Harga(Rp)","Jml",
                    "Subtotal(Rp)","Diskon(%)","Diskon(Rp)","Total(Rp)","No.Batch","No.Faktur"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.String.class,java.lang.String.class
             };  
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }  
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(60);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(95);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoNota.setDocument(new batasInput((byte)20).getKata(NoNota));
        kdpasien.setDocument(new batasInput((byte)15).getKata(kdpasien));
        nmpasien.setDocument(new batasInput((byte)50).getKata(nmpasien));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        kdbar.setDocument(new batasInput((byte)15).getKata(kdbar));
        catatan.setDocument(new batasInput((byte)40).getKata(catatan));
        Ongkir.setDocument(new batasInput((byte)14).getKata(Ongkir));
        UangMuka.setDocument(new batasInput((byte)14).getKata(UangMuka));
        Tambah.setDocument(new batasInput((byte)14).getKata(Tambah));
        Kurang.setDocument(new batasInput((byte)14).getKata(Kurang));
        Disc.setDocument(new batasInput((byte)14).getKata(Disc));   
        Jmljual.setDocument(new batasInput((byte)13).getKata(Jmljual));  
        NoBatch.setDocument(new batasInput((byte)20).getKata(NoBatch));     
        NoFaktur.setDocument(new batasInput((byte)20).getKata(NoFaktur));     
        
        Jmljual.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        Tambah.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        Kurang.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        HrgJual.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        Disc.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isDiskon();}
            @Override
            public void removeUpdate(DocumentEvent e) {isDiskon();}
            @Override
            public void changedUpdate(DocumentEvent e) {isDiskon();}
        });        
        Bsrdisc.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });     
        UangMuka.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        }); 
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPiutang")){
                    if(bangsal.getTable().getSelectedRow()!= -1){                   
                        kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        Stok.setText(Double.toString(Sequel.cariIsiAngka("select stok from gudangbarang where kd_bangsal='"+kdgudang.getText()+"' and kode_brng='"+kdbar.getText()+"'")));
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
        
        bangsal.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPiutang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        bangsal.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        form.member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPiutang")){
                    if(form.member.getTable().getSelectedRow()!= -1){                   
                        kdpasien.setText(form.member.getTable().getValueAt(form.member.getTable().getSelectedRow(),1).toString());
                        nmpasien.setText(form.member.getTable().getValueAt(form.member.getTable().getSelectedRow(),2).toString());
                    }  
                    kdpasien.requestFocus();
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
        
        form.member.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPiutang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        form.member.dispose();
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
                if(akses.getform().equals("DlgPiutang")){
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
        
        form.barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPiutang")){
                    if(form.barang.getTable().getSelectedRow()!= -1){                   
                        kdbar.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),1).toString());                    
                        nmbar.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),2).toString());
                        satuanbar.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),6).toString());
                        HrgBeli.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),10).toString());
                        if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),20).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Karyawan")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),21).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),19).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),12).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),13).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),14).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),15).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Utama")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),16).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("VIP")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),17).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),18).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Harga Beli")){
                               HrgJual.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),11).toString()); 
                        } 
                        
                        if(aktifkanbatch.equals("yes")){
                            NoBatch.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),32).toString());
                            NoFaktur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),33).toString());
                        }
                        Stok.setText(Double.toString(Sequel.cariIsiAngka("select stok from gudangbarang where kd_bangsal='"+kdgudang.getText()+"' and kode_brng='"+kdbar.getText()+"' and no_batch='"+NoBatch.getText()+"' and no_faktur='"+NoFaktur.getText()+"'")));
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
        
        form.barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPiutang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        form.barang.dispose();
                    }                
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
        HrgBeli = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnTambah = new widget.Button();
        BtnHapus = new widget.Button();
        label9 = new widget.Label();
        BtnNota = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi5 = new widget.panelisi();
        label10 = new widget.Label();
        LTotal = new widget.Label();
        label14 = new widget.Label();
        Ongkir = new widget.TextBox();
        label19 = new widget.Label();
        UangMuka = new widget.TextBox();
        panelisi21 = new widget.panelisi2();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        BtnBrg = new widget.Button();
        satuanbar = new widget.TextBox();
        label22 = new widget.Label();
        HrgJual = new widget.TextBox();
        label24 = new widget.Label();
        Jmljual = new widget.TextBox();
        label25 = new widget.Label();
        subtotal = new widget.TextBox();
        label23 = new widget.Label();
        Stok = new widget.TextBox();
        label26 = new widget.Label();
        Tambah = new widget.TextBox();
        Kurang = new widget.TextBox();
        label27 = new widget.Label();
        label28 = new widget.Label();
        Bsrdisc = new widget.TextBox();
        label29 = new widget.Label();
        Total = new widget.TextBox();
        label30 = new widget.Label();
        Disc = new widget.TextBox();
        label31 = new widget.Label();
        Sisa = new widget.TextBox();
        label21 = new widget.Label();
        NoBatch = new widget.TextBox();
        label33 = new widget.Label();
        NoFaktur = new widget.TextBox();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoNota = new widget.TextBox();
        label11 = new widget.Label();
        TglJual = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdpasien = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmpasien = new widget.TextBox();
        nmptg = new widget.TextBox();
        BtnPasien = new widget.Button();
        BtnPtg = new widget.Button();
        label18 = new widget.Label();
        catatan = new widget.TextBox();
        Jenisjual = new widget.ComboBox();
        label12 = new widget.Label();
        label20 = new widget.Label();
        TglTempo = new widget.Tanggal();
        label32 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        HrgBeli.setEditable(false);
        HrgBeli.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        HrgBeli.setName("HrgBeli"); // NOI18N
        HrgBeli.setPreferredSize(new java.awt.Dimension(80, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Piutang Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 207));
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

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label9);

        BtnNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnNota.setMnemonic('S');
        BtnNota.setText("Nota");
        BtnNota.setToolTipText("Alt+S");
        BtnNota.setName("BtnNota"); // NOI18N
        BtnNota.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNotaActionPerformed(evt);
            }
        });
        BtnNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNotaKeyPressed(evt);
            }
        });
        panelisi1.add(BtnNota);

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

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 54));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Grand Total :");
        label10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(107, 26));
        panelisi5.add(label10);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(155, 26));
        panelisi5.add(LTotal);

        label14.setText("Ongkir :");
        label14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(60, 26));
        panelisi5.add(label14);

        Ongkir.setText("0");
        Ongkir.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        Ongkir.setName("Ongkir"); // NOI18N
        Ongkir.setPreferredSize(new java.awt.Dimension(130, 26));
        panelisi5.add(Ongkir);

        label19.setText("Uang Muka :");
        label19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(125, 26));
        panelisi5.add(label19);

        UangMuka.setText("0");
        UangMuka.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        UangMuka.setName("UangMuka"); // NOI18N
        UangMuka.setPreferredSize(new java.awt.Dimension(150, 26));
        panelisi5.add(UangMuka);

        jPanel1.add(panelisi5, java.awt.BorderLayout.CENTER);

        panelisi21.setName("panelisi21"); // NOI18N
        panelisi21.setPreferredSize(new java.awt.Dimension(2172, 103));
        panelisi21.setLayout(null);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label17);
        label17.setBounds(0, 10, 66, 23);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi21.add(kdbar);
        kdbar.setBounds(70, 10, 100, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi21.add(nmbar);
        nmbar.setBounds(172, 10, 320, 23);

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
        panelisi21.add(BtnBrg);
        BtnBrg.setBounds(567, 10, 28, 23);

        satuanbar.setEditable(false);
        satuanbar.setName("satuanbar"); // NOI18N
        satuanbar.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi21.add(satuanbar);
        satuanbar.setBounds(494, 10, 70, 23);

        label22.setText("Harga Brg :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label22);
        label22.setBounds(0, 70, 66, 23);

        HrgJual.setEditable(false);
        HrgJual.setName("HrgJual"); // NOI18N
        HrgJual.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi21.add(HrgJual);
        HrgJual.setBounds(70, 70, 110, 23);

        label24.setText("Jml.Barang :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label24);
        label24.setBounds(445, 40, 70, 23);

        Jmljual.setText("0");
        Jmljual.setName("Jmljual"); // NOI18N
        Jmljual.setPreferredSize(new java.awt.Dimension(80, 23));
        Jmljual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JmljualKeyPressed(evt);
            }
        });
        panelisi21.add(Jmljual);
        Jmljual.setBounds(519, 40, 45, 23);

        label25.setText("SubTotal :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label25);
        label25.setBounds(360, 70, 80, 23);

        subtotal.setEditable(false);
        subtotal.setText("0");
        subtotal.setName("subtotal"); // NOI18N
        subtotal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi21.add(subtotal);
        subtotal.setBounds(444, 70, 120, 23);

        label23.setText("Stok :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label23);
        label23.setBounds(345, 40, 37, 23);

        Stok.setEditable(false);
        Stok.setName("Stok"); // NOI18N
        Stok.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi21.add(Stok);
        Stok.setBounds(386, 40, 50, 23);

        label26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label26.setText("+");
        label26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label26);
        label26.setBounds(180, 70, 20, 23);

        Tambah.setText("0");
        Tambah.setName("Tambah"); // NOI18N
        Tambah.setPreferredSize(new java.awt.Dimension(80, 23));
        Tambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TambahKeyPressed(evt);
            }
        });
        panelisi21.add(Tambah);
        Tambah.setBounds(199, 70, 70, 23);

        Kurang.setText("0");
        Kurang.setName("Kurang"); // NOI18N
        Kurang.setPreferredSize(new java.awt.Dimension(80, 23));
        Kurang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KurangKeyPressed(evt);
            }
        });
        panelisi21.add(Kurang);
        Kurang.setBounds(283, 70, 70, 23);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label27.setText("-");
        label27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label27);
        label27.setBounds(266, 70, 20, 23);

        label28.setText("Total :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label28);
        label28.setBounds(600, 40, 60, 23);

        Bsrdisc.setText("0");
        Bsrdisc.setName("Bsrdisc"); // NOI18N
        Bsrdisc.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi21.add(Bsrdisc);
        Bsrdisc.setBounds(722, 10, 80, 23);

        label29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label29.setText("%");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label29);
        label29.setBounds(703, 10, 20, 23);

        Total.setEditable(false);
        Total.setText("0");
        Total.setName("Total"); // NOI18N
        Total.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi21.add(Total);
        Total.setBounds(664, 40, 138, 23);

        label30.setText("Diskon :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label30);
        label30.setBounds(600, 10, 60, 23);

        Disc.setText("0");
        Disc.setName("Disc"); // NOI18N
        Disc.setPreferredSize(new java.awt.Dimension(80, 23));
        Disc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiscKeyPressed(evt);
            }
        });
        panelisi21.add(Disc);
        Disc.setBounds(664, 10, 40, 23);

        label31.setText("Sisa Piutang :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label31);
        label31.setBounds(570, 70, 90, 23);

        Sisa.setEditable(false);
        Sisa.setText("0");
        Sisa.setName("Sisa"); // NOI18N
        Sisa.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi21.add(Sisa);
        Sisa.setBounds(664, 70, 138, 23);

        label21.setText("No.Batch :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label21);
        label21.setBounds(0, 40, 66, 23);

        NoBatch.setName("NoBatch"); // NOI18N
        NoBatch.setPreferredSize(new java.awt.Dimension(80, 23));
        NoBatch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBatchKeyPressed(evt);
            }
        });
        panelisi21.add(NoBatch);
        NoBatch.setBounds(70, 40, 90, 23);

        label33.setText("No.Faktur :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi21.add(label33);
        label33.setBounds(160, 40, 65, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(80, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi21.add(NoFaktur);
        NoFaktur.setBounds(229, 40, 115, 23);

        jPanel1.add(panelisi21, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
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
        NoNota.setBounds(74, 10, 110, 23);

        label11.setText("Tgl.Jual :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 70, 70, 23);

        TglJual.setDisplayFormat("dd-MM-yyyy");
        TglJual.setName("TglJual"); // NOI18N
        TglJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglJualKeyPressed(evt);
            }
        });
        panelisi3.add(TglJual);
        TglJual.setBounds(74, 70, 100, 23);

        label16.setText("Pasien :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(366, 10, 70, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(366, 40, 70, 23);

        kdpasien.setName("kdpasien"); // NOI18N
        kdpasien.setPreferredSize(new java.awt.Dimension(80, 23));
        kdpasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpasienKeyPressed(evt);
            }
        });
        panelisi3.add(kdpasien);
        kdpasien.setBounds(439, 10, 100, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(439, 40, 100, 23);

        nmpasien.setName("nmpasien"); // NOI18N
        nmpasien.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmpasien);
        nmpasien.setBounds(541, 10, 230, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(541, 40, 230, 23);

        BtnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPasien.setMnemonic('1');
        BtnPasien.setToolTipText("Alt+1");
        BtnPasien.setName("BtnPasien"); // NOI18N
        BtnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPasienActionPerformed(evt);
            }
        });
        panelisi3.add(BtnPasien);
        BtnPasien.setBounds(774, 10, 28, 23);

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
        panelisi3.add(BtnPtg);
        BtnPtg.setBounds(774, 40, 28, 23);

        label18.setText("Catatan :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 70, 23);

        catatan.setName("catatan"); // NOI18N
        catatan.setPreferredSize(new java.awt.Dimension(207, 23));
        catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                catatanKeyPressed(evt);
            }
        });
        panelisi3.add(catatan);
        catatan.setBounds(74, 40, 286, 23);

        Jenisjual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jual Bebas", "Harga Beli", "Karyawan", "Beli Luar", "Rawat Jalan", "Kelas 1", "Kelas 2", "Kelas 3", "Utama", "VIP", "VVIP" }));
        Jenisjual.setName("Jenisjual"); // NOI18N
        Jenisjual.setPreferredSize(new java.awt.Dimension(45, 23));
        Jenisjual.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisjualItemStateChanged(evt);
            }
        });
        Jenisjual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisjualKeyPressed(evt);
            }
        });
        panelisi3.add(Jenisjual);
        Jenisjual.setBounds(240, 10, 120, 23);

        label12.setText("Jns.Jual :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(177, 10, 60, 23);

        label20.setText("Jatuh Tempo :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label20);
        label20.setBounds(177, 70, 80, 23);

        TglTempo.setDisplayFormat("dd-MM-yyyy");
        TglTempo.setName("TglTempo"); // NOI18N
        TglTempo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglTempoKeyPressed(evt);
            }
        });
        panelisi3.add(TglTempo);
        TglTempo.setBounds(260, 70, 100, 23);

        label32.setText("Lokasi :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label32);
        label32.setBounds(366, 70, 70, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(439, 70, 100, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(541, 70, 230, 23);

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
        panelisi3.add(BtnGudang);
        BtnGudang.setBounds(774, 70, 28, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

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
        if(aktifkanbatch.equals("yes")&&NoFaktur.getText().equals("")){
            Valid.textKosong(NoFaktur,"No.Faktur");
        }else if(aktifkanbatch.equals("yes")&&NoBatch.getText().equals("")){
            Valid.textKosong(NoBatch,"No.Batch");
        }else if(nmbar.getText().trim().equals("")){
            Valid.textKosong(kdbar,"barang");
        }else if(Jmljual.getText().trim().equals("")||Jmljual.getText().trim().equals("0")){
            Valid.textKosong(Jmljual,"jumlah barang");
        }else if(Disc.getText().trim().equals("")||Bsrdisc.getText().trim().equals("")){
            Valid.textKosong(kdbar,"barang");
        }else{     
             try {
                stok=0;   
                ps=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                try {
                    ps.setString(1,kdgudang.getText());
                    ps.setString(2,kdbar.getText());
                    ps.setString(3,NoBatch.getText());
                    ps.setString(4,NoFaktur.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        stok=rs.getDouble(1);
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
                
                jumlah=Valid.SetAngka(Jmljual.getText());
                if(jumlah>stok){
                    Stok.setText(""+stok);
                    Jmljual.setText(""+stok);
                    JOptionPane.showMessageDialog(null,"Maaf, stok tidak mencukupi. Maksimal yang bisa diretur "+stok+" "+satuanbar.getText());
                }else{
                    if(Sequel.menyimpantf("tamppiutang","'"+kdbar.getText()+"','"+nmbar.getText()+"','"+satuanbar.getText()+"','"+HrgJual.getText()+"','"+HrgBeli.getText()+"','"+Jmljual.getText()+"','"+subtotal.getText()+"','"+Disc.getText()+"','"+Bsrdisc.getText()+"','"+Total.getText()+"','"+NoBatch.getText()+"','"+akses.getkode()+"','"+NoFaktur.getText()+"'","Kode Barang, No.Batch, No.Faktur")==true){
                        emptTeks();            
                        tampil();
                    }
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
            Valid.pindah(evt,Disc, BtnHapus);
        }
}//GEN-LAST:event_BtnTambahKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(nmbar.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.hapusTable(tabMode,kdbar,"tamppiutang","no_batch='"+NoBatch.getText()+"' and no_faktur='"+NoFaktur.getText()+"' and petugas='"+akses.getkode()+"' and kode_brng");
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
        form.emptTeks();     
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
        }else{Valid.pindah(evt,BtnBatal,kdbar);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasienActionPerformed
        akses.setform("DlgPiutang");
        form.member.emptTeks();
        form.member.isCek();
        form.member.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.member.setLocationRelativeTo(internalFrame1);
        form.member.setAlwaysOnTop(false);
        form.member.setVisible(true);
    }//GEN-LAST:event_BtnPasienActionPerformed

    private void BtnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtgActionPerformed
        akses.setform("DlgPiutang");
        form.petugas.emptTeks();
        form.petugas.isCek();
        form.petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.petugas.setLocationRelativeTo(internalFrame1);
        form.petugas.setAlwaysOnTop(false);
        form.petugas.setVisible(true);
    }//GEN-LAST:event_BtnPtgActionPerformed

    private void TglJualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglJualKeyPressed
        Valid.pindah(evt,NoNota,kdpasien);
    }//GEN-LAST:event_TglJualKeyPressed

    private void BtnBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBrgActionPerformed
        if(nmgudang.getText().equals("")){
            Valid.textKosong(kdgudang,"Gudang");
        }else{
            if(aktifkanbatch.equals("yes")){
                form.barang.aktifkanbatch="yes";
            }
            akses.setform("DlgPiutang");
            form.barang.emptTeks();
            form.barang.isCek();
            form.barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.barang.setLocationRelativeTo(internalFrame1);
            form.barang.setAlwaysOnTop(false);
            form.barang.setVisible(true);
        }        
    }//GEN-LAST:event_BtnBrgActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        double ongkir=0,uangmuka=0;
                
        if(!Ongkir.getText().trim().equals("")){
            ongkir=Double.parseDouble(Ongkir.getText());
        }
        if(!UangMuka.getText().trim().equals("")){
            uangmuka=Double.parseDouble(UangMuka.getText());
        }
        if(NoNota.getText().trim().equals("")){
            Valid.textKosong(NoNota,"No.Nota");
        }else if(nmpasien.getText().trim().equals("")){
            Valid.textKosong(kdpasien,"Pembeli");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(UangMuka.getText().trim().equals("")){
            Valid.textKosong(UangMuka,"Bayar");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            kdbar.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("piutang","'"+NoNota.getText()+"','"+Valid.SetTgl(TglJual.getSelectedItem()+"")+"','"+kdptg.getText()+"','"+kdpasien.getText()+
                        "','"+nmpasien.getText()+"','"+catatan.getText()+"','"+Jenisjual.getSelectedItem()+"','"+ongkir+"','"+uangmuka+"','"+(ttljual+ongkir-uangmuka)+
                        "','Umum','"+Valid.SetTgl(TglTempo.getSelectedItem()+"")+"','"+kdgudang.getText()+"'","No.Nota")==true){
                    simpan();
                }else{
                    autoNomor();
                    if(Sequel.menyimpantf2("piutang","'"+NoNota.getText()+"','"+Valid.SetTgl(TglJual.getSelectedItem()+"")+"','"+kdptg.getText()+"','"+kdpasien.getText()+
                            "','"+nmpasien.getText()+"','"+catatan.getText()+"','"+Jenisjual.getSelectedItem()+"','"+ongkir+"','"+uangmuka+"','"+(ttljual+ongkir-uangmuka)+
                            "','Umum','"+Valid.SetTgl(TglTempo.getSelectedItem()+"")+"','"+kdgudang.getText()+"'","No.Nota")==true){
                        simpan();
                    }else{
                        autoNomor();
                        sukses=false;
                    }
                } 
                
                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");
                    Sequel.menyimpan2("tampjurnal","'"+Sequel.cariIsi("select Piutang_Obat from set_akun")+"','PIUTANG OBAT','"+(ttljual+ongkir-uangmuka)+"','0'","Rekening");    
                    Sequel.menyimpan2("tampjurnal","'"+Sequel.cariIsi("select Kontra_Piutang_Obat from set_akun")+"','KAS DI TANGAN','0','"+(ttljual+ongkir-uangmuka)+"'","Rekening"); 
                    sukses=jur.simpanJurnal(NoNota.getText(),Valid.SetTgl(TglJual.getSelectedItem()+""),"U","PIUTANG DI "+nmgudang.getText().toUpperCase()+", OLEH "+akses.getkode());   
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
            Valid.pindah(evt,kdbar,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        Sequel.queryu("delete from tamppiutang where petugas='"+akses.getkode()+"'");
        tampil();
        autoNomor();
        Ongkir.setText("0");
        UangMuka.setText("0");        
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void kdpasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpasienKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmpasien,kdpasien.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmpasien,kdpasien.getText());
            TglJual.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmpasien,kdpasien.getText());
            catatan.requestFocus();            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPasienActionPerformed(null);
        }
    }//GEN-LAST:event_kdpasienKeyPressed

    private void NoNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoNotaKeyPressed
        Valid.pindah(evt, BtnTambah, TglJual);
    }//GEN-LAST:event_NoNotaKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());    
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            Jenisjual.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            kdbar.requestFocus();        
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPtgActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            cariBatch();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            cariBatch();
            kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            cariBatch();
            Jmljual.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnBrgActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void JmljualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JmljualKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());  
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            Disc.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptg,kdptg.getText());
            Kurang.requestFocus();          
        }
    }//GEN-LAST:event_JmljualKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            //BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, kdbar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catatanKeyPressed
        Valid.pindah(evt, kdpasien, Jenisjual);
    }//GEN-LAST:event_catatanKeyPressed

    private void JenisjualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisjualKeyPressed
        Valid.pindah(evt, catatan, kdptg);
    }//GEN-LAST:event_JenisjualKeyPressed

    private void TambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TambahKeyPressed
        Valid.pindah(evt, kdbar,Kurang);
    }//GEN-LAST:event_TambahKeyPressed

    private void KurangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KurangKeyPressed
        Valid.pindah(evt, Tambah, Jmljual);
    }//GEN-LAST:event_KurangKeyPressed

    private void DiscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiscKeyPressed
        Valid.pindah(evt, Jmljual, BtnTambah);
    }//GEN-LAST:event_DiscKeyPressed

    private void JenisjualItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisjualItemStateChanged
         cariBatch();  
    }//GEN-LAST:event_JenisjualItemStateChanged

    private void BtnNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoNota.getText().trim().equals("")){
            Valid.textKosong(NoNota,"No.Nota");
        }else if(nmpasien.getText().trim().equals("")){
            Valid.textKosong(kdpasien,"Pasien");
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            kdbar.requestFocus();
        }else {
            Valid.panggilUrl("billing/NotaApotek3.php?nonota="+NoNota.getText()+"&kdptg="+kdptg.getText()+"&muka="+UangMuka.getText()+"&ongkir="+Ongkir.getText()+"&tanggal="+Valid.SetTgl(TglJual.getSelectedItem()+"")+"&nm_member="+nmpasien.getText().replaceAll(" ","_")+"&tgltempo="+Valid.SetTgl(TglTempo.getSelectedItem()+"")+"&catatan="+catatan.getText().replaceAll(" ","_"));                                           
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnNotaActionPerformed

    private void BtnNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNotaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNotaKeyPressed

    private void TglTempoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTempoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTempoKeyPressed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        Stok.setText(Double.toString(Sequel.cariIsiAngka("select stok from gudangbarang where kd_bangsal='"+kdgudang.getText()+"' and kode_brng='"+kdbar.getText()+"'")));        
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        Stok.setText(Double.toString(Sequel.cariIsiAngka("select stok from gudangbarang where kd_bangsal='"+kdgudang.getText()+"' and kode_brng='"+kdbar.getText()+"'")));
        kdptg.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        Stok.setText(Double.toString(Sequel.cariIsiAngka("select stok from gudangbarang where kd_bangsal='"+kdgudang.getText()+"' and kode_brng='"+kdbar.getText()+"'")));
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        BtnGudangActionPerformed(null);
    }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
    akses.setform("DlgPiutang");
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

    private void NoBatchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBatchKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            cariBatch();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            cariBatch();
            kdbar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            cariBatch();
            Jmljual.requestFocus();
        }
    }//GEN-LAST:event_NoBatchKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoFakturKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPiutang dialog = new DlgPiutang(new javax.swing.JFrame(), true);
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
    private widget.TextBox Bsrdisc;
    private widget.Button BtnBatal;
    private widget.Button BtnBrg;
    private widget.Button BtnCari;
    private widget.Button BtnGudang;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnNota;
    private widget.Button BtnPasien;
    private widget.Button BtnPtg;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Disc;
    private widget.TextBox HrgBeli;
    private widget.TextBox HrgJual;
    private widget.ComboBox Jenisjual;
    private widget.TextBox Jmljual;
    private widget.TextBox Kd2;
    private widget.TextBox Kurang;
    private widget.Label LTotal;
    private widget.TextBox NoBatch;
    private widget.TextBox NoFaktur;
    private widget.TextBox NoNota;
    private widget.TextBox Ongkir;
    private widget.TextBox Sisa;
    private widget.TextBox Stok;
    private widget.TextBox Tambah;
    private widget.Tanggal TglJual;
    private widget.Tanggal TglTempo;
    private widget.TextBox Total;
    private widget.TextBox UangMuka;
    private widget.TextBox catatan;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdbar;
    private widget.TextBox kdgudang;
    private widget.TextBox kdpasien;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
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
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmgudang;
    private widget.TextBox nmpasien;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi2 panelisi21;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi5;
    private widget.TextBox satuanbar;
    private widget.ScrollPane scrollPane1;
    private widget.TextBox subtotal;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select kode_brng,nama_brng,satuan,h_jual,jumlah,subtotal,dis,bsr_dis,total,no_batch,no_faktur from tamppiutang where petugas=?");
            try {
                ps.setString(1,akses.getkode());
                rs=ps.executeQuery();
                ttljual=0;
                while(rs.next()){
                    ttljual=ttljual+rs.getDouble(9);
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getDouble(4),rs.getDouble(5),rs.getDouble(6),
                        rs.getDouble(7),rs.getDouble(8),rs.getDouble(9),
                        rs.getString(10),rs.getString(11)
                    });
                }                
                LTotal.setText(Valid.SetAngka(ttljual));
            } catch (Exception e) {
                System.out.println("Notif Temp Piutang : "+e);
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

    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        satuanbar.setText("");
        Stok.setText("0");
        HrgJual.setText("0");        
        Tambah.setText("0");       
        Kurang.setText("0"); 
        Jmljual.setText("0");  
        subtotal.setText("0");
        Disc.setText("0");
        Bsrdisc.setText("0");
        NoFaktur.setText("");
        NoBatch.setText("");
        Total.setText("0");
        kdbar.requestFocus(); 
    }

    private void getData() {
       int row=tbDokter.getSelectedRow();
        if(row!= -1){
            kdbar.setText(tabMode.getValueAt(row,0).toString());
            nmbar.setText(tabMode.getValueAt(row,1).toString());
            satuanbar.setText(tabMode.getValueAt(row,2).toString());
            HrgJual.setText(tabMode.getValueAt(row,3).toString());
            Jmljual.setText(tabMode.getValueAt(row,4).toString());
            subtotal.setText(tabMode.getValueAt(row,5).toString());
            Disc.setText(tabMode.getValueAt(row,6).toString());
            Bsrdisc.setText(tabMode.getValueAt(row,7).toString());
            Total.setText(tabMode.getValueAt(row,8).toString());
            NoBatch.setText(tabMode.getValueAt(row,9).toString());
            NoFaktur.setText(tabMode.getValueAt(row,10).toString());
        }
    }
    
    private void isHitung(){
        double hargajual=0,tambah=0,kurang=0,jumlah=0,stok=0,diskon=0;
        if(!HrgJual.getText().trim().equals("")){
            hargajual=Double.parseDouble(HrgJual.getText());
        }
        
        try{
            if(!Tambah.getText().trim().equals("")){
                tambah=Double.parseDouble(Tambah.getText());
            }
        } catch (Exception e) {
            tambah=0;
            Tambah.setText("0");
            if(e.toString().contains("java.lang.NumberFormatException")){
                JOptionPane.showMessageDialog(null,"Perhatikan format angka yang dipakai oleh komputer..!!");
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat menghitung tambah...!!");
            }
        }
        
        try{
            if(!Kurang.getText().trim().equals("")){
                kurang=Double.parseDouble(Kurang.getText());
            }
        } catch (Exception e) {
            kurang=0;
            Kurang.setText("0");
            if(e.toString().contains("java.lang.NumberFormatException")){
                JOptionPane.showMessageDialog(null,"Perhatikan format angka yang dipakai oleh komputer..!!");
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat menghitung kurang...!!");
            }
        }
        
        try {
            if(!Jmljual.getText().trim().equals("")) {
                jumlah=Double.parseDouble(Jmljual.getText());        
            } 
        } catch (Exception e) {
            jumlah=0;
            Jmljual.setText("0");
            if(e.toString().contains("java.lang.NumberFormatException")){
                JOptionPane.showMessageDialog(null,"Perhatikan format angka yang dipakai oleh komputer..!!");
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat menghitung jumlah...!!");
            }
        }
                     
        subtotal.setText(Valid.SetAngka2((hargajual+tambah-kurang)*jumlah)); 
        if(!Stok.getText().trim().equals("")) {
            stok=Double.parseDouble(Stok.getText());        
        } 
        
        try{
            if(!Bsrdisc.getText().trim().equals("")) {
                diskon=Double.parseDouble(Bsrdisc.getText()); 
            }
        } catch (Exception e) {
            diskon=0;
            Bsrdisc.setText("0");
            if(e.toString().contains("java.lang.NumberFormatException")){
                JOptionPane.showMessageDialog(null,"Perhatikan format angka yang dipakai oleh komputer..!!");
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat menghitung besar diskon...!!");
            }
        }
        
        Total.setText(Valid.SetAngka2(Double.parseDouble(subtotal.getText())-diskon));    
        if(!UangMuka.getText().trim().equals("")){
           Sisa.setText(Valid.SetAngka2(Double.parseDouble(subtotal.getText())-Double.parseDouble(UangMuka.getText())));   
        }
    }
    
    private void isDiskon(){
        if(!Disc.getText().trim().equals("")) {
            try {
                Bsrdisc.setText(Valid.SetAngka2((Double.parseDouble(Disc.getText())/100)*Double.parseDouble(subtotal.getText()))); 
            } catch (Exception e) {
                Disc.setText("0");
                Bsrdisc.setText("0");
                if(e.toString().contains("java.lang.NumberFormatException")){
                    JOptionPane.showMessageDialog(null,"Perhatikan format angka yang dipakai oleh komputer..!!");
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat menghitung diskon...!!");
                }
            }   
        }        
    }
   
    public void isCek(){
        autoNomor();
        Ongkir.setText("0");
        UangMuka.setText("0");
        Sequel.cariIsi("select kd_bangsal from set_lokasi",kdgudang);
        Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?",nmgudang,kdgudang.getText());
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            BtnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.getpiutang_obat());
            kdptg.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }        
    }
    
    
    private void cariBatch() {
        try {
            if(aktifkanbatch.equals("yes")){
                ps=koneksi.prepareStatement(
                        "select data_batch.no_batch, data_batch.kode_brng, data_batch.tgl_beli, data_batch.tgl_kadaluarsa, data_batch.asal, data_batch.no_faktur, "+
                        "data_batch.h_beli, data_batch.ralan, data_batch.kelas1, data_batch.kelas2, data_batch.kelas3, data_batch.utama, data_batch.vip, data_batch.vvip, data_batch.beliluar, "+
                        "data_batch.jualbebas, data_batch.karyawan, data_batch.jumlahbeli,gudangbarang.stok,data_batch.dasar from data_batch inner join gudangbarang "+
                        "on data_batch.kode_brng=gudangbarang.kode_brng and data_batch.no_batch=gudangbarang.no_batch and data_batch.no_faktur=gudangbarang.no_faktur where "+
                        "data_batch.kode_brng=? and gudangbarang.no_batch=? and gudangbarang.no_faktur=? and gudangbarang.kd_bangsal=?");
                try {
                    ps.setString(1,kdbar.getText());
                    ps.setString(2,NoBatch.getText());
                    ps.setString(3,NoFaktur.getText());
                    ps.setString(4,kdgudang.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(Jenisjual.getSelectedItem().equals("Karyawan")){
                            HrgJual.setText(rs.getString("karyawan"));
                        }else if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                            HrgJual.setText(rs.getString("jualbebas"));
                        }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                            HrgJual.setText(rs.getString("beliluar"));
                        }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                            HrgJual.setText(rs.getString("ralan"));
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                            HrgJual.setText(rs.getString("kelas1"));
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                            HrgJual.setText(rs.getString("kelas2"));
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                            HrgJual.setText(rs.getString("kelas3"));
                        }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                            HrgJual.setText(rs.getString("utama"));
                        }else if(Jenisjual.getSelectedItem().equals("VIP")){
                            HrgJual.setText(rs.getString("vip"));
                        }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                            HrgJual.setText(rs.getString("vvip"));
                        }else if(Jenisjual.getSelectedItem().equals("Harga Beli")){
                            HrgJual.setText(rs.getString("h_beli"));
                        }
                        HrgBeli.setText(rs.getString("dasar"));
                        Stok.setText(""+rs.getDouble("stok"));
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
            }else{
                ps=koneksi.prepareStatement(
                        "select databarang.kode_brng,databarang.h_beli, databarang.ralan, databarang.kelas1, databarang.kelas2, databarang.kelas3, databarang.utama, "+
                        "databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,databarang.karyawan,gudangbarang.stok,databarang.dasar from "+
                        "databarang inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng where gudangbarang.no_batch='' and gudangbarang.no_faktur='' "+
                        "and gudangbarang.kode_brng=? and gudangbarang.kd_bangsal=?");
                try {
                    ps.setString(1,kdbar.getText());
                    ps.setString(2,kdgudang.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(Jenisjual.getSelectedItem().equals("Karyawan")){
                            HrgJual.setText(rs.getString("karyawan"));
                        }else if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                            HrgJual.setText(rs.getString("jualbebas"));
                        }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                            HrgJual.setText(rs.getString("beliluar"));
                        }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                            HrgJual.setText(rs.getString("ralan"));
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                            HrgJual.setText(rs.getString("kelas1"));
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                            HrgJual.setText(rs.getString("kelas2"));
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                            HrgJual.setText(rs.getString("kelas3"));
                        }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                            HrgJual.setText(rs.getString("utama"));
                        }else if(Jenisjual.getSelectedItem().equals("VIP")){
                            HrgJual.setText(rs.getString("vip"));
                        }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                            HrgJual.setText(rs.getString("vvip"));
                        }else if(Jenisjual.getSelectedItem().equals("Harga Beli")){
                            HrgJual.setText(rs.getString("h_beli"));
                        }
                        HrgBeli.setText(rs.getString("dasar"));
                        Stok.setText(""+rs.getDouble("stok"));
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
            }
                
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    public void setPasien(String norawat,String norm,String pasien) {
        NoNota.setText(norawat);
        kdpasien.setText(norm);
        nmpasien.setText(pasien);
    }

    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_piutang,3),signed)),0) from piutang where tgl_piutang='"+Valid.SetTgl(TglJual.getSelectedItem()+"")+"' ",
                "PD"+TglJual.getSelectedItem().toString().substring(8,10)+TglJual.getSelectedItem().toString().substring(3,5)+TglJual.getSelectedItem().toString().substring(0,2),3,NoNota); 
    }

    private void simpan() {
        try {
            ps=koneksi.prepareStatement("select kode_brng, satuan, h_jual,h_beli, jumlah, subtotal, dis, bsr_dis, total,no_batch,no_faktur from tamppiutang where petugas=?");
            try {
                ps.setString(1,akses.getkode());
                rs=ps.executeQuery();
                while(rs.next()){
                    if(Sequel.menyimpantf2("detailpiutang","'"+NoNota.getText()+"','"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(3)+
                        "','"+rs.getString(4)+"','"+rs.getString(5)+"','"+rs.getString(6)+"','"+rs.getString(7)+"','"+rs.getString(8)+"','"+rs.getString(9)+"','"+rs.getString("no_batch")+"','"+rs.getString("no_faktur")+"'","Obat/BHP/Alkes")==true){
                        Trackobat.catatRiwayat(rs.getString(1),0,rs.getDouble(5),"Piutang",akses.getkode(),kdgudang.getText(),"Simpan",rs.getString("no_batch"),rs.getString("no_faktur"));
                        Sequel.menyimpan("gudangbarang","'"+rs.getString(1)+"','"+kdgudang.getText()+"','-"+rs.getString(5)+"','"+rs.getString("no_batch")+"','"+rs.getString("no_faktur")+"'", 
                                        "stok=stok-'"+rs.getString(5)+"'","kode_brng='"+rs.getString(1)+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='"+rs.getString("no_batch")+"' and no_faktur='"+rs.getString("no_faktur")+"'");
                        if(aktifkanbatch.equals("yes")){
                            Sequel.mengedit("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                rs.getString(5),rs.getString("no_batch"),rs.getString(1),rs.getString("no_faktur")
                            });
                        } 
                    }else{
                       sukses=false;
                    }                                  
                }
            } catch (Exception e) {
                System.out.println("Notif Tamp Piutang 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                       
        } catch (Exception e) {
            System.out.println("Notif Tamp Piutang 2 : "+e);
        }
                    
    }
}

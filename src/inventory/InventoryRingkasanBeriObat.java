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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariCaraBayar;

public class InventoryRingkasanBeriObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private Connection koneksi=koneksiDB.condb();
    private int i=0;
    private double total=0;
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    private DlgCariBangsal asalstok=new DlgCariBangsal(null,false);
    public  DlgBarang barang=new DlgBarang(null,false);
    private String status="",carabayar="",depo="",jenis="",bar="",tanggal="",order="order by databarang.nama_brng";
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public InventoryRingkasanBeriObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode Barang","Nama Barang","Satuan","Jenis","Jumlah","Total","Kode Sat"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class,java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(95);
            }else if(i==1){
                column.setPreferredWidth(280);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(140);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        kdpenjab.setDocument(new batasInput((byte)15).getKata(kdpenjab));
        nmpenjab.setDocument(new batasInput((byte)70).getKata(nmpenjab));
        kddepo.setDocument(new batasInput((byte)25).getKata(kddepo));
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
        
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPenjualan")){
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
                if(akses.getform().equals("DlgCariPenjualan")){
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
                if(akses.getform().equals("DlgCariPenjualan")){
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
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                }      
                kdpenjab.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        asalstok.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(asalstok.getTable().getSelectedRow()!= -1){                   
                    kddepo.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),0).toString());                    
                    nmdepo.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),1).toString());
                }  
                kddepo.requestFocus();
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
        
        asalstok.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    asalstok.dispose();
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
        MnKodeBarangDesc = new javax.swing.JMenuItem();
        MnKodeBarangAsc = new javax.swing.JMenuItem();
        MnNamaBarangDesc = new javax.swing.JMenuItem();
        MnNamaBarangAsc = new javax.swing.JMenuItem();
        MnKategoriAsc = new javax.swing.JMenuItem();
        MnKategoriDesc = new javax.swing.JMenuItem();
        MnSatuanDesc = new javax.swing.JMenuItem();
        MnSatuanAsc = new javax.swing.JMenuItem();
        MnTotalAsc = new javax.swing.JMenuItem();
        MnTotalDesc = new javax.swing.JMenuItem();
        MnJumlahAsc = new javax.swing.JMenuItem();
        MnJumlahDesc = new javax.swing.JMenuItem();
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
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdpenjab = new widget.TextBox();
        kddepo = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        nmdepo = new widget.TextBox();
        btnpenjab = new widget.Button();
        btndepo = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel18 = new widget.Label();
        Status = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();

        Popup1.setName("Popup1"); // NOI18N

        MnKodeBarangDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnKodeBarangDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKodeBarangDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnKodeBarangDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKodeBarangDesc.setText("Urutkan Berdasar Kode Barang Descending");
        MnKodeBarangDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKodeBarangDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKodeBarangDesc.setName("MnKodeBarangDesc"); // NOI18N
        MnKodeBarangDesc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnKodeBarangDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKodeBarangDescActionPerformed(evt);
            }
        });
        Popup1.add(MnKodeBarangDesc);

        MnKodeBarangAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnKodeBarangAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKodeBarangAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnKodeBarangAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKodeBarangAsc.setText("Urutkan Berdasar Kode Barang Ascending");
        MnKodeBarangAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKodeBarangAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKodeBarangAsc.setName("MnKodeBarangAsc"); // NOI18N
        MnKodeBarangAsc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnKodeBarangAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKodeBarangAscActionPerformed(evt);
            }
        });
        Popup1.add(MnKodeBarangAsc);

        MnNamaBarangDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnNamaBarangDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNamaBarangDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnNamaBarangDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNamaBarangDesc.setText("Urutkan Berdasar Nama Barang Descending");
        MnNamaBarangDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNamaBarangDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNamaBarangDesc.setName("MnNamaBarangDesc"); // NOI18N
        MnNamaBarangDesc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnNamaBarangDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNamaBarangDescActionPerformed(evt);
            }
        });
        Popup1.add(MnNamaBarangDesc);

        MnNamaBarangAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnNamaBarangAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNamaBarangAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnNamaBarangAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNamaBarangAsc.setText("Urutkan Berdasar Nama Barang Ascending");
        MnNamaBarangAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNamaBarangAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNamaBarangAsc.setName("MnNamaBarangAsc"); // NOI18N
        MnNamaBarangAsc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnNamaBarangAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNamaBarangAscActionPerformed(evt);
            }
        });
        Popup1.add(MnNamaBarangAsc);

        MnKategoriAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnKategoriAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKategoriAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnKategoriAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKategoriAsc.setText("Urutkan Berdasar Jenis Ascending");
        MnKategoriAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKategoriAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKategoriAsc.setName("MnKategoriAsc"); // NOI18N
        MnKategoriAsc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnKategoriAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKategoriAscActionPerformed(evt);
            }
        });
        Popup1.add(MnKategoriAsc);

        MnKategoriDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnKategoriDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKategoriDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnKategoriDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKategoriDesc.setText("Urutkan Berdasar Jenis Descending");
        MnKategoriDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKategoriDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKategoriDesc.setName("MnKategoriDesc"); // NOI18N
        MnKategoriDesc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnKategoriDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKategoriDescActionPerformed(evt);
            }
        });
        Popup1.add(MnKategoriDesc);

        MnSatuanDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnSatuanDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSatuanDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnSatuanDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSatuanDesc.setText("Urutkan Berdasar Satuan Descending");
        MnSatuanDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSatuanDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSatuanDesc.setName("MnSatuanDesc"); // NOI18N
        MnSatuanDesc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSatuanDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSatuanDescActionPerformed(evt);
            }
        });
        Popup1.add(MnSatuanDesc);

        MnSatuanAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnSatuanAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSatuanAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnSatuanAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSatuanAsc.setText("Urutkan Berdasar Satuan Ascending");
        MnSatuanAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSatuanAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSatuanAsc.setName("MnSatuanAsc"); // NOI18N
        MnSatuanAsc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSatuanAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSatuanAscActionPerformed(evt);
            }
        });
        Popup1.add(MnSatuanAsc);

        MnTotalAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnTotalAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTotalAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnTotalAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTotalAsc.setText("Urutkan Berdasar Total Ascending");
        MnTotalAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTotalAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTotalAsc.setName("MnTotalAsc"); // NOI18N
        MnTotalAsc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnTotalAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTotalAscActionPerformed(evt);
            }
        });
        Popup1.add(MnTotalAsc);

        MnTotalDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnTotalDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTotalDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnTotalDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTotalDesc.setText("Urutkan Berdasar Total Descending");
        MnTotalDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTotalDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTotalDesc.setName("MnTotalDesc"); // NOI18N
        MnTotalDesc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnTotalDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTotalDescActionPerformed(evt);
            }
        });
        Popup1.add(MnTotalDesc);

        MnJumlahAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnJumlahAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJumlahAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnJumlahAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJumlahAsc.setText("Urutkan Berdasar Jumlah Ascending");
        MnJumlahAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJumlahAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJumlahAsc.setName("MnJumlahAsc"); // NOI18N
        MnJumlahAsc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnJumlahAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJumlahAscActionPerformed(evt);
            }
        });
        Popup1.add(MnJumlahAsc);

        MnJumlahDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnJumlahDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJumlahDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnJumlahDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJumlahDesc.setText("Urutkan Berdasar Jumlah Descending");
        MnJumlahDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJumlahDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJumlahDesc.setName("MnJumlahDesc"); // NOI18N
        MnJumlahDesc.setPreferredSize(new java.awt.Dimension(280, 26));
        MnJumlahDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJumlahDescActionPerformed(evt);
            }
        });
        Popup1.add(MnJumlahDesc);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ringkasan Beri Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        kdbar.setEditable(false);
        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
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
        label24.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi4.add(label24);
        label24.setBounds(0, 10, 74, 23);

        kdsat.setEditable(false);
        kdsat.setName("kdsat"); // NOI18N
        kdsat.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(kdsat);
        kdsat.setBounds(79, 10, 53, 23);

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
        nmsat.setBounds(134, 10, 116, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
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

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(55, 30));
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

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 60, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(64, 40, 90, 23);

        label16.setText("Cara/Jenis Bayar :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(335, 10, 110, 23);

        label13.setText("Depo/Asal Stok :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(325, 40, 120, 23);

        kdpenjab.setEditable(false);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(kdpenjab);
        kdpenjab.setBounds(449, 10, 80, 23);

        kddepo.setEditable(false);
        kddepo.setName("kddepo"); // NOI18N
        kddepo.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(kddepo);
        kddepo.setBounds(449, 40, 80, 23);

        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmpenjab);
        nmpenjab.setBounds(531, 10, 240, 23);

        nmdepo.setEditable(false);
        nmdepo.setName("nmdepo"); // NOI18N
        nmdepo.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmdepo);
        nmdepo.setBounds(531, 40, 240, 23);

        btnpenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnpenjab.setMnemonic('1');
        btnpenjab.setToolTipText("Alt+1");
        btnpenjab.setName("btnpenjab"); // NOI18N
        btnpenjab.setPreferredSize(new java.awt.Dimension(28, 23));
        btnpenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpenjabActionPerformed(evt);
            }
        });
        panelisi3.add(btnpenjab);
        btnpenjab.setBounds(774, 10, 28, 23);

        btndepo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btndepo.setMnemonic('2');
        btndepo.setToolTipText("Alt+2");
        btndepo.setName("btndepo"); // NOI18N
        btndepo.setPreferredSize(new java.awt.Dimension(28, 23));
        btndepo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndepoActionPerformed(evt);
            }
        });
        panelisi3.add(btndepo);
        btndepo.setBounds(774, 40, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(153, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(185, 40, 90, 23);

        jLabel18.setText("Status :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelisi3.add(jLabel18);
        jLabel18.setBounds(0, 10, 60, 23);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Ralan", "Ranap" }));
        Status.setLightWeightPopupEnabled(false);
        Status.setName("Status"); // NOI18N
        panelisi3.add(Status);
        Status.setBounds(64, 10, 150, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnpenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpenjabActionPerformed
        akses.setform("DlgCariPenjualan");
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_btnpenjabActionPerformed

    private void btndepoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndepoActionPerformed
        akses.setform("DlgCariPenjualan");
        asalstok.isCek();
        asalstok.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        asalstok.setLocationRelativeTo(internalFrame1);
        asalstok.setAlwaysOnTop(false);
        asalstok.setVisible(true);
    }//GEN-LAST:event_btndepoActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,kdpenjab,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kddepo);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akses.setform("DlgCariPenjualan");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        akses.setform("DlgCariPenjualan");
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
        kdbar.setText("");
        nmbar.setText("");
        kdsat.setText("");
        nmsat.setText("");
        kdpenjab.setText("");
        nmpenjab.setText("");
        kddepo.setText("");
        nmdepo.setText("");
        Status.setSelectedIndex(0);
        tampil();
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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());  
            param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));  
            param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));  
            param.put("parameter","%"+TCari.getText().trim()+"%");   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            tanggal=" detail_pemberian_obat.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ";
            status="";carabayar="";depo="";jenis="";bar="";

            if(!nmpenjab.getText().equals("")){
                carabayar=" and penjab.png_jawab='"+nmpenjab.getText()+"' ";
            }
            if(!Status.getSelectedItem().toString().equals("Semua")){
                status=" and detail_pemberian_obat.status='"+Status.getSelectedItem()+"' ";
            }
            if(!nmdepo.getText().equals("")){
                depo=" and bangsal.nm_bangsal='"+nmdepo.getText()+"' ";
            }
            if(!nmsat.getText().equals("")){
                jenis=" and jenis.nama='"+nmsat.getText()+"' ";
            }
            if(!nmbar.getText().equals("")){
                bar=" and databarang.nama_brng='"+nmbar.getText()+"' ";
            }
            Valid.MyReportqry("rptRingkasanBeriObat.jasper","report","::[ Laporan Ringkasan Pemberian Obat/Alkes/BHP Medis ]::",
                    "select detail_pemberian_obat.kode_brng,databarang.nama_brng, databarang.kode_sat,"+
                    " kodesatuan.satuan,jenis.nama as namajenis,sum(detail_pemberian_obat.jml) as jumlah,sum(detail_pemberian_obat.total) as total "+
                    " from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    " inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                    " inner join bangsal on detail_pemberian_obat.kd_bangsal=bangsal.kd_bangsal "+
                    " inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                    " where "+tanggal+carabayar+depo+jenis+bar+status+" and "+
                    "(detail_pemberian_obat.kode_brng like '%"+TCari.getText()+"%' or databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                    "kodesatuan.satuan like '%"+TCari.getText()+"%' or jenis.nama like '%"+TCari.getText()+"%') "+
                    " group by detail_pemberian_obat.kode_brng "+order,param); 
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnKodeBarangDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKodeBarangDescActionPerformed
        order="order by databarang.kode_brng desc";
        tampil();
    }//GEN-LAST:event_MnKodeBarangDescActionPerformed

    private void MnKodeBarangAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKodeBarangAscActionPerformed
        order="order by databarang.kode_brng asc";
        tampil();
    }//GEN-LAST:event_MnKodeBarangAscActionPerformed

    private void MnNamaBarangDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNamaBarangDescActionPerformed
        order="order by databarang.nama_brng desc";
        tampil();
    }//GEN-LAST:event_MnNamaBarangDescActionPerformed

    private void MnNamaBarangAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNamaBarangAscActionPerformed
        order="order by databarang.nama_brng asc";
        tampil();
    }//GEN-LAST:event_MnNamaBarangAscActionPerformed

    private void MnKategoriAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKategoriAscActionPerformed
        order="order by jenis.nama desc";
        tampil();
    }//GEN-LAST:event_MnKategoriAscActionPerformed

    private void MnKategoriDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKategoriDescActionPerformed
        order="order by jenis.nama asc";
        tampil();
    }//GEN-LAST:event_MnKategoriDescActionPerformed

    private void MnSatuanDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSatuanDescActionPerformed
        order="order by databarang.kode_sat desc";
        tampil();
    }//GEN-LAST:event_MnSatuanDescActionPerformed

    private void MnSatuanAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSatuanAscActionPerformed
        order="order by databarang.kode_sat asc";
        tampil();
    }//GEN-LAST:event_MnSatuanAscActionPerformed

    private void MnTotalAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTotalAscActionPerformed
        order="order by sum(detail_pemberian_obat.total) asc";
        tampil();
    }//GEN-LAST:event_MnTotalAscActionPerformed

    private void MnTotalDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTotalDescActionPerformed
        order="order by sum(detail_pemberian_obat.total) desc";
        tampil();
    }//GEN-LAST:event_MnTotalDescActionPerformed

    private void MnJumlahAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJumlahAscActionPerformed
        order="order by sum(detail_pemberian_obat.jml) asc";
        tampil();
    }//GEN-LAST:event_MnJumlahAscActionPerformed

    private void MnJumlahDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJumlahDescActionPerformed
        order="order by sum(detail_pemberian_obat.jml) desc";
        tampil();
    }//GEN-LAST:event_MnJumlahDescActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventoryRingkasanBeriObat dialog = new InventoryRingkasanBeriObat(new javax.swing.JFrame(), true);
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
    private javax.swing.JMenuItem MnJumlahAsc;
    private javax.swing.JMenuItem MnJumlahDesc;
    private javax.swing.JMenuItem MnKategoriAsc;
    private javax.swing.JMenuItem MnKategoriDesc;
    private javax.swing.JMenuItem MnKodeBarangAsc;
    private javax.swing.JMenuItem MnKodeBarangDesc;
    private javax.swing.JMenuItem MnNamaBarangAsc;
    private javax.swing.JMenuItem MnNamaBarangDesc;
    private javax.swing.JMenuItem MnSatuanAsc;
    private javax.swing.JMenuItem MnSatuanDesc;
    private javax.swing.JMenuItem MnTotalAsc;
    private javax.swing.JMenuItem MnTotalDesc;
    private javax.swing.JPopupMenu Popup1;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBarang;
    private widget.Button btnSatuan;
    private widget.Button btndepo;
    private widget.Button btnpenjab;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel18;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdbar;
    private widget.TextBox kddepo;
    private widget.TextBox kdpenjab;
    private widget.TextBox kdsat;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmdepo;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmsat;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        tanggal=" detail_pemberian_obat.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' ";
        status="";carabayar="";depo="";jenis="";bar="";

        if(!nmpenjab.getText().equals("")){
            carabayar=" and penjab.png_jawab='"+nmpenjab.getText()+"' ";
        }
        if(!Status.getSelectedItem().toString().equals("Semua")){
            status=" and detail_pemberian_obat.status='"+Status.getSelectedItem()+"' ";
        }
        if(!nmdepo.getText().equals("")){
            depo=" and bangsal.nm_bangsal='"+nmdepo.getText()+"' ";
        }
        if(!nmsat.getText().equals("")){
            jenis=" and jenis.nama='"+nmsat.getText()+"' ";
        }
        if(!nmbar.getText().equals("")){
            bar=" and databarang.nama_brng='"+nmbar.getText()+"' ";
        }

        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select detail_pemberian_obat.kode_brng,databarang.nama_brng, databarang.kode_sat,"+
                    " kodesatuan.satuan,jenis.nama as namajenis,sum(detail_pemberian_obat.jml) as jumlah,sum(detail_pemberian_obat.total) as total "+
                    " from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                    " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    " inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                    " inner join bangsal on detail_pemberian_obat.kd_bangsal=bangsal.kd_bangsal "+
                    " inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                    " where "+tanggal+carabayar+depo+jenis+bar+status+" and "+
                    "(detail_pemberian_obat.kode_brng like '%"+TCari.getText()+"%' or databarang.nama_brng like '%"+TCari.getText()+"%' or "+
                    "kodesatuan.satuan like '%"+TCari.getText()+"%' or jenis.nama like '%"+TCari.getText()+"%') "+
                    " group by detail_pemberian_obat.kode_brng "+order);
            try {
                rs=ps.executeQuery();
                total=0;
                while(rs.next()){                    
                    total=total+rs.getDouble("total");
                    tabMode.addRow(new Object[]{
                        rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("satuan"),rs.getString("namajenis"),rs.getDouble("jumlah"),rs.getDouble("total"),rs.getString("kode_sat")
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
            LTotal.setText(Valid.SetAngka(total));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }        
    }
    
    public void emptTeks() {
        kdbar.setText("");
        nmbar.setText("");
        kdsat.setText("");
        kdbar.requestFocus();        
    }   
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getringkasan_beri_obat());
    }
}

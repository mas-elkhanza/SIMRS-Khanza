/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package keuangan;
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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import restore.DlgRestoreTarifOperasi;
import simrskhanza.DlgPenanggungJawab;

/**
 *
 * @author dosen
 */
public final class DlgJnsPerawatanOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DecimalFormat df2 = new DecimalFormat("####");
    private double operator1=0,operator2=0,operator3=0,
               asistenoperator1=0,asistenoperator2=0,asistenoperator3=0,instrumen=0,
               dokteranak=0,perawatresusitas=0,bidan1=0,bidan2=0,bidan3=0,
               alat=0,anastesi=0,perawatluar=0,asistenanas=0,asistenanas2=0,sewaok=0,sewavk=0,
               bagianrs=0,omloop1=0,omloop2=0,omloop3=0,omloop4=0,omloop5=0,sarpras=0,dokterumum=0,dokterpjanak=0;
    private PreparedStatement pstampil;
    private ResultSet rs;
    private int i=0;
    public DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);

    /** Creates new form DlgJnsPerawatan
     * @param parent
     * @param modal */
    public DlgJnsPerawatanOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"P","Kode Paket","Nama Operasi","Kategori","Operator 1","Operator 2","Operator 3",
                      "Asisten Op 1","Asisten Op 2","Asisten Op 3","Instrumen","dr Anestesi","Asisten Anes 1","Asisten Anes 2","dr Anak",
                      "Perawat Resus","Bidan 1","Bidan 2","Bidan 3","Perawat Luar","Alat","Sewa OK/VK",
                      "Akomodasi","N.M.S.","Onloop 1","Onloop 2","Onloop 3","Onloop 4","Onloop 5","Sarpras","dr Pj Anak","dr Umum",
                      "Total","Jenis Bayar","Kelas"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 35; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(220);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==33){
                column.setPreferredWidth(200);
            }else if(i==34){
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(85);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        

        TKd.setDocument(new batasInput((byte)15).getKata(TKd));
        TNm.setDocument(new batasInput((byte)80).getKata(TNm));
        TOperator1.setDocument(new batasInput((byte)13).getOnlyAngka(TOperator1));
        TOperator2.setDocument(new batasInput((byte)13).getOnlyAngka(TOperator2));
        TOperator3.setDocument(new batasInput((byte)13).getOnlyAngka(TOperator3));
        TAsisOperator1.setDocument(new batasInput((byte)13).getOnlyAngka(TAsisOperator1));
        TAsisOperator2.setDocument(new batasInput((byte)13).getOnlyAngka(TAsisOperator2));
        TAsisOperator3.setDocument(new batasInput((byte)13).getOnlyAngka(TAsisOperator3));
        TInstrumen.setDocument(new batasInput((byte)13).getOnlyAngka(TInstrumen));
        TAnastesi.setDocument(new batasInput((byte)13).getOnlyAngka(TAnastesi));
        TAsisAnastesi.setDocument(new batasInput((byte)13).getOnlyAngka(TAsisAnastesi));
        TAsisAnastesi1.setDocument(new batasInput((byte)13).getOnlyAngka(TAsisAnastesi1));
        TAnak.setDocument(new batasInput((byte)13).getOnlyAngka(TAnak));
        TResusitas.setDocument(new batasInput((byte)13).getOnlyAngka(TResusitas));
        TBidan1.setDocument(new batasInput((byte)13).getOnlyAngka(TBidan1));
        TBidan2.setDocument(new batasInput((byte)13).getOnlyAngka(TBidan2));
        TBidan3.setDocument(new batasInput((byte)13).getOnlyAngka(TBidan3));
        TAlat.setDocument(new batasInput((byte)13).getOnlyAngka(TAlat));
        TPerawatLuar.setDocument(new batasInput((byte)13).getOnlyAngka(TPerawatLuar));
        TSewaOK.setDocument(new batasInput((byte)13).getOnlyAngka(TSewaOK));
        TAkomodasi.setDocument(new batasInput((byte)13).getOnlyAngka(TAkomodasi));
        TBagianRS.setDocument(new batasInput((byte)13).getOnlyAngka(TBagianRS));
        TOmloop1.setDocument(new batasInput((byte)13).getOnlyAngka(TOmloop1));
        TOmloop2.setDocument(new batasInput((byte)13).getOnlyAngka(TOmloop2));
        TOmloop3.setDocument(new batasInput((byte)13).getOnlyAngka(TOmloop3));
        TOmloop4.setDocument(new batasInput((byte)13).getOnlyAngka(TOmloop4));
        TOmloop5.setDocument(new batasInput((byte)13).getOnlyAngka(TOmloop5));
        TSarpras.setDocument(new batasInput((byte)13).getOnlyAngka(TSarpras));
        TdrPJAnak.setDocument(new batasInput((byte)13).getOnlyAngka(TdrPJAnak));        
        TdrUmum.setDocument(new batasInput((byte)13).getOnlyAngka(TdrUmum));
        
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
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                } 
                kdpnj.requestFocus();
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
        MnRestore = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TKd = new widget.TextBox();
        jLabel8 = new widget.Label();
        TNm = new widget.TextBox();
        TOperator1 = new widget.TextBox();
        jLabel9 = new widget.Label();
        TAsisOperator1 = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        TAlat = new widget.TextBox();
        TSewaOK = new widget.TextBox();
        jLabel10 = new widget.Label();
        TAnastesi = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        TAsisAnastesi = new widget.TextBox();
        jLabel11 = new widget.Label();
        TPerawatLuar = new widget.TextBox();
        jLabel16 = new widget.Label();
        TTotal = new widget.TextBox();
        jLabel17 = new widget.Label();
        TOperator2 = new widget.TextBox();
        jLabel18 = new widget.Label();
        TOperator3 = new widget.TextBox();
        jLabel19 = new widget.Label();
        TAsisOperator2 = new widget.TextBox();
        jLabel20 = new widget.Label();
        TInstrumen = new widget.TextBox();
        jLabel21 = new widget.Label();
        TAnak = new widget.TextBox();
        jLabel22 = new widget.Label();
        TResusitas = new widget.TextBox();
        jLabel23 = new widget.Label();
        TBidan1 = new widget.TextBox();
        jLabel24 = new widget.Label();
        TBagianRS = new widget.TextBox();
        jLabel25 = new widget.Label();
        TAkomodasi = new widget.TextBox();
        jLabel26 = new widget.Label();
        TOmloop1 = new widget.TextBox();
        jLabel4 = new widget.Label();
        Kategori = new widget.ComboBox();
        jLabel27 = new widget.Label();
        TBidan2 = new widget.TextBox();
        jLabel28 = new widget.Label();
        TBidan3 = new widget.TextBox();
        jLabel29 = new widget.Label();
        TOmloop2 = new widget.TextBox();
        jLabel30 = new widget.Label();
        TOmloop3 = new widget.TextBox();
        jLabel31 = new widget.Label();
        TSarpras = new widget.TextBox();
        jLabel32 = new widget.Label();
        TdrUmum = new widget.TextBox();
        jLabel33 = new widget.Label();
        TdrPJAnak = new widget.TextBox();
        jLabel34 = new widget.Label();
        kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        BtnPenjab = new widget.Button();
        jLabel35 = new widget.Label();
        TAsisOperator3 = new widget.TextBox();
        jLabel36 = new widget.Label();
        TAsisAnastesi1 = new widget.TextBox();
        jLabel37 = new widget.Label();
        TOmloop4 = new widget.TextBox();
        jLabel38 = new widget.Label();
        TOmloop5 = new widget.TextBox();
        jLabel5 = new widget.Label();
        Kelas = new widget.ComboBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRestore.setBackground(new java.awt.Color(255, 255, 254));
        MnRestore.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRestore.setForeground(new java.awt.Color(70, 70, 70));
        MnRestore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRestore.setText("Data Sampah");
        MnRestore.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRestore.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRestore.setName("MnRestore"); // NOI18N
        MnRestore.setPreferredSize(new java.awt.Dimension(200, 28));
        MnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRestoreActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRestore);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Paket Tindakan Operasi/VK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
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
        panelGlass8.add(BtnBatal);

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
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

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
        panelGlass8.add(BtnPrint);

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
        panelGlass8.add(BtnAll);

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
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 257));
        FormInput.setLayout(null);

        jLabel3.setText("Kode Paket :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 96, 23);

        TKd.setHighlighter(null);
        TKd.setName("TKd"); // NOI18N
        TKd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdKeyPressed(evt);
            }
        });
        FormInput.add(TKd);
        TKd.setBounds(100, 12, 100, 23);

        jLabel8.setText("Nama Tnd/Prw/Tagihan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(206, 12, 160, 23);

        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        FormInput.add(TNm);
        TNm.setBounds(370, 12, 350, 23);

        TOperator1.setText("0");
        TOperator1.setHighlighter(null);
        TOperator1.setName("TOperator1"); // NOI18N
        TOperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOperator1KeyPressed(evt);
            }
        });
        FormInput.add(TOperator1);
        TOperator1.setBounds(100, 72, 122, 23);

        jLabel9.setText("Operator 1 : Rp");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 72, 96, 23);

        TAsisOperator1.setText("0");
        TAsisOperator1.setHighlighter(null);
        TAsisOperator1.setName("TAsisOperator1"); // NOI18N
        TAsisOperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAsisOperator1KeyPressed(evt);
            }
        });
        FormInput.add(TAsisOperator1);
        TAsisOperator1.setBounds(370, 72, 122, 23);

        jLabel12.setText("Asisten Operator 1 : Rp");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(225, 72, 140, 23);

        jLabel13.setText("Alat : Rp");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 252, 96, 23);

        TAlat.setText("0");
        TAlat.setHighlighter(null);
        TAlat.setName("TAlat"); // NOI18N
        TAlat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlatKeyPressed(evt);
            }
        });
        FormInput.add(TAlat);
        TAlat.setBounds(100, 252, 122, 23);

        TSewaOK.setText("0");
        TSewaOK.setHighlighter(null);
        TSewaOK.setName("TSewaOK"); // NOI18N
        TSewaOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSewaOKKeyPressed(evt);
            }
        });
        FormInput.add(TSewaOK);
        TSewaOK.setBounds(370, 282, 122, 23);

        jLabel10.setText("Sewa Ok/VK : RP");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(225, 282, 140, 23);

        TAnastesi.setText("0");
        TAnastesi.setHighlighter(null);
        TAnastesi.setName("TAnastesi"); // NOI18N
        TAnastesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAnastesiKeyPressed(evt);
            }
        });
        FormInput.add(TAnastesi);
        TAnastesi.setBounds(100, 162, 122, 23);

        jLabel14.setText("dr Anestesi : Rp");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 162, 96, 23);

        jLabel15.setText("Asisten Anestesi 1 : Rp");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(225, 162, 140, 23);

        TAsisAnastesi.setText("0");
        TAsisAnastesi.setHighlighter(null);
        TAsisAnastesi.setName("TAsisAnastesi"); // NOI18N
        TAsisAnastesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAsisAnastesiKeyPressed(evt);
            }
        });
        FormInput.add(TAsisAnastesi);
        TAsisAnastesi.setBounds(370, 162, 122, 23);

        jLabel11.setText("Prw Luar : RP");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(225, 312, 140, 23);

        TPerawatLuar.setText("0");
        TPerawatLuar.setHighlighter(null);
        TPerawatLuar.setName("TPerawatLuar"); // NOI18N
        TPerawatLuar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPerawatLuarKeyPressed(evt);
            }
        });
        FormInput.add(TPerawatLuar);
        TPerawatLuar.setBounds(370, 312, 122, 23);

        jLabel16.setText("Total : Rp");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(495, 312, 98, 23);

        TTotal.setEditable(false);
        TTotal.setText("0");
        TTotal.setHighlighter(null);
        TTotal.setName("TTotal"); // NOI18N
        FormInput.add(TTotal);
        TTotal.setBounds(598, 312, 122, 23);

        jLabel17.setText("Operator 2 : Rp");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 102, 96, 23);

        TOperator2.setText("0");
        TOperator2.setHighlighter(null);
        TOperator2.setName("TOperator2"); // NOI18N
        TOperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOperator2KeyPressed(evt);
            }
        });
        FormInput.add(TOperator2);
        TOperator2.setBounds(100, 102, 122, 23);

        jLabel18.setText("Operator 3 : Rp");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 132, 96, 23);

        TOperator3.setText("0");
        TOperator3.setHighlighter(null);
        TOperator3.setName("TOperator3"); // NOI18N
        TOperator3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOperator3KeyPressed(evt);
            }
        });
        FormInput.add(TOperator3);
        TOperator3.setBounds(100, 132, 122, 23);

        jLabel19.setText("Asisten Operator 2 : Rp");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(225, 102, 140, 23);

        TAsisOperator2.setText("0");
        TAsisOperator2.setHighlighter(null);
        TAsisOperator2.setName("TAsisOperator2"); // NOI18N
        TAsisOperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAsisOperator2KeyPressed(evt);
            }
        });
        FormInput.add(TAsisOperator2);
        TAsisOperator2.setBounds(370, 102, 122, 23);

        jLabel20.setText("Instrumen : Rp");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 340, 96, 23);

        TInstrumen.setText("0");
        TInstrumen.setHighlighter(null);
        TInstrumen.setName("TInstrumen"); // NOI18N
        TInstrumen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInstrumenKeyPressed(evt);
            }
        });
        FormInput.add(TInstrumen);
        TInstrumen.setBounds(100, 342, 122, 23);

        jLabel21.setText("dr Anak : Rp");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(0, 192, 96, 23);

        TAnak.setText("0");
        TAnak.setHighlighter(null);
        TAnak.setName("TAnak"); // NOI18N
        TAnak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAnakKeyPressed(evt);
            }
        });
        FormInput.add(TAnak);
        TAnak.setBounds(100, 192, 122, 23);

        jLabel22.setText("Perawat Resusitasi : Rp");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(225, 222, 140, 23);

        TResusitas.setText("0");
        TResusitas.setHighlighter(null);
        TResusitas.setName("TResusitas"); // NOI18N
        TResusitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TResusitasKeyPressed(evt);
            }
        });
        FormInput.add(TResusitas);
        TResusitas.setBounds(370, 222, 122, 23);

        jLabel23.setText("Bidan 1 : Rp");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(495, 72, 98, 23);

        TBidan1.setText("0");
        TBidan1.setHighlighter(null);
        TBidan1.setName("TBidan1"); // NOI18N
        TBidan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBidan1KeyPressed(evt);
            }
        });
        FormInput.add(TBidan1);
        TBidan1.setBounds(598, 72, 122, 23);

        jLabel24.setText("N.M.S. : Rp");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(0, 222, 96, 23);

        TBagianRS.setText("0");
        TBagianRS.setHighlighter(null);
        TBagianRS.setName("TBagianRS"); // NOI18N
        TBagianRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBagianRSKeyPressed(evt);
            }
        });
        FormInput.add(TBagianRS);
        TBagianRS.setBounds(100, 222, 122, 23);

        jLabel25.setText("Akomodasi : Rp");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(225, 252, 140, 23);

        TAkomodasi.setText("0");
        TAkomodasi.setHighlighter(null);
        TAkomodasi.setName("TAkomodasi"); // NOI18N
        TAkomodasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAkomodasiKeyPressed(evt);
            }
        });
        FormInput.add(TAkomodasi);
        TAkomodasi.setBounds(370, 252, 122, 23);

        jLabel26.setText("Onloop 1 : Rp");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(495, 162, 98, 23);

        TOmloop1.setText("0");
        TOmloop1.setHighlighter(null);
        TOmloop1.setName("TOmloop1"); // NOI18N
        TOmloop1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOmloop1KeyPressed(evt);
            }
        });
        FormInput.add(TOmloop1);
        TOmloop1.setBounds(598, 162, 122, 23);

        jLabel4.setText("Kategori :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 42, 96, 23);

        Kategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kebidanan", "Operasi" }));
        Kategori.setName("Kategori"); // NOI18N
        Kategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KategoriKeyPressed(evt);
            }
        });
        FormInput.add(Kategori);
        Kategori.setBounds(100, 42, 122, 23);

        jLabel27.setText("Bidan 2 : Rp");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(495, 102, 98, 23);

        TBidan2.setText("0");
        TBidan2.setHighlighter(null);
        TBidan2.setName("TBidan2"); // NOI18N
        TBidan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBidan2KeyPressed(evt);
            }
        });
        FormInput.add(TBidan2);
        TBidan2.setBounds(598, 102, 122, 23);

        jLabel28.setText("Bidan 3 : Rp");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(495, 132, 98, 23);

        TBidan3.setText("0");
        TBidan3.setHighlighter(null);
        TBidan3.setName("TBidan3"); // NOI18N
        TBidan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBidan3KeyPressed(evt);
            }
        });
        FormInput.add(TBidan3);
        TBidan3.setBounds(598, 132, 122, 23);

        jLabel29.setText("Onloop 2 : Rp");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(495, 192, 98, 23);

        TOmloop2.setText("0");
        TOmloop2.setHighlighter(null);
        TOmloop2.setName("TOmloop2"); // NOI18N
        TOmloop2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOmloop2KeyPressed(evt);
            }
        });
        FormInput.add(TOmloop2);
        TOmloop2.setBounds(598, 192, 122, 23);

        jLabel30.setText("Onloop 3 : Rp");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(495, 222, 98, 23);

        TOmloop3.setText("0");
        TOmloop3.setHighlighter(null);
        TOmloop3.setName("TOmloop3"); // NOI18N
        TOmloop3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOmloop3KeyPressed(evt);
            }
        });
        FormInput.add(TOmloop3);
        TOmloop3.setBounds(598, 222, 122, 23);

        jLabel31.setText("Sarpras : Rp");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(270, 342, 98, 23);

        TSarpras.setText("0");
        TSarpras.setHighlighter(null);
        TSarpras.setName("TSarpras"); // NOI18N
        TSarpras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSarprasKeyPressed(evt);
            }
        });
        FormInput.add(TSarpras);
        TSarpras.setBounds(370, 342, 122, 23);

        jLabel32.setText("dr Umum : Rp");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 282, 96, 23);

        TdrUmum.setText("0");
        TdrUmum.setHighlighter(null);
        TdrUmum.setName("TdrUmum"); // NOI18N
        TdrUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TdrUmumKeyPressed(evt);
            }
        });
        FormInput.add(TdrUmum);
        TdrUmum.setBounds(100, 282, 122, 23);

        jLabel33.setText("dr Pj Anak : RP");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 312, 96, 23);

        TdrPJAnak.setText("0");
        TdrPJAnak.setHighlighter(null);
        TdrPJAnak.setName("TdrPJAnak"); // NOI18N
        TdrPJAnak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TdrPJAnakKeyPressed(evt);
            }
        });
        FormInput.add(TdrPJAnak);
        TdrPJAnak.setBounds(100, 312, 122, 23);

        jLabel34.setText("Bayar :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(225, 42, 140, 23);

        kdpnj.setToolTipText("Tekan ENTER untuk lanjut ke field berikutnya, tekan PAGE UP untuk ke field sebelumnya, Tekan UP untuk menampilkan data Jenis Pembayaran");
        kdpnj.setHighlighter(null);
        kdpnj.setName("kdpnj"); // NOI18N
        kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpnjKeyPressed(evt);
            }
        });
        FormInput.add(kdpnj);
        kdpnj.setBounds(370, 42, 60, 23);

        nmpnj.setEditable(false);
        nmpnj.setName("nmpnj"); // NOI18N
        FormInput.add(nmpnj);
        nmpnj.setBounds(432, 42, 258, 23);

        BtnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab.setMnemonic('2');
        BtnPenjab.setToolTipText("ALt+2");
        BtnPenjab.setName("BtnPenjab"); // NOI18N
        BtnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjabActionPerformed(evt);
            }
        });
        FormInput.add(BtnPenjab);
        BtnPenjab.setBounds(692, 42, 28, 23);

        jLabel35.setText("Asisten Operator 3 : Rp");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(225, 132, 140, 23);

        TAsisOperator3.setText("0");
        TAsisOperator3.setHighlighter(null);
        TAsisOperator3.setName("TAsisOperator3"); // NOI18N
        TAsisOperator3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAsisOperator3KeyPressed(evt);
            }
        });
        FormInput.add(TAsisOperator3);
        TAsisOperator3.setBounds(370, 132, 122, 23);

        jLabel36.setText("Asisten Anestesi 2 : Rp");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(225, 192, 140, 23);

        TAsisAnastesi1.setText("0");
        TAsisAnastesi1.setHighlighter(null);
        TAsisAnastesi1.setName("TAsisAnastesi1"); // NOI18N
        TAsisAnastesi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAsisAnastesi1KeyPressed(evt);
            }
        });
        FormInput.add(TAsisAnastesi1);
        TAsisAnastesi1.setBounds(370, 192, 122, 23);

        jLabel37.setText("Onloop 4 : Rp");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(495, 252, 98, 23);

        TOmloop4.setText("0");
        TOmloop4.setHighlighter(null);
        TOmloop4.setName("TOmloop4"); // NOI18N
        TOmloop4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOmloop4KeyPressed(evt);
            }
        });
        FormInput.add(TOmloop4);
        TOmloop4.setBounds(598, 252, 122, 23);

        jLabel38.setText("Onloop 5 : Rp");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(495, 282, 98, 23);

        TOmloop5.setText("0");
        TOmloop5.setHighlighter(null);
        TOmloop5.setName("TOmloop5"); // NOI18N
        TOmloop5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOmloop5KeyPressed(evt);
            }
        });
        FormInput.add(TOmloop5);
        TOmloop5.setBounds(598, 282, 122, 23);

        jLabel5.setText("Kelas :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(495, 342, 98, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Rawat Jalan", "Kelas 1", "Kelas 2", "Kelas 3", "Kelas Utama", "Kelas VIP", "Kelas VVIP" }));
        Kelas.setName("Kelas"); // NOI18N
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormInput.add(Kelas);
        Kelas.setBounds(598, 342, 122, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Paket", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setComponentPopupMenu(jPopupMenu1);
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Daftar Paket", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TKdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
        Valid.pindah(evt,TCari,TNm);
}//GEN-LAST:event_TKdKeyPressed

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        Valid.pindah(evt,TKd,Kategori);
}//GEN-LAST:event_TNmKeyPressed

    private void TOperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOperator1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            kdpnj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOperator2.requestFocus();
        }
}//GEN-LAST:event_TOperator1KeyPressed

    private void TAsisOperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAsisOperator1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TInstrumen.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAsisOperator2.requestFocus();
        }
}//GEN-LAST:event_TAsisOperator1KeyPressed

    private void TAlatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TBagianRS.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TdrUmum.requestFocus();
        }
}//GEN-LAST:event_TAlatKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TKd.getText().trim().equals("")){
            Valid.textKosong(TKd,"Kode Jenis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Nama Perawatan");
        }else if(TOperator1.getText().trim().equals("")){
            Valid.textKosong(TOperator1,"Operator 1");
        }else if(TOperator2.getText().trim().equals("")){
            Valid.textKosong(TOperator2,"Operator 2");
        }else if(TOperator3.getText().trim().equals("")){
            Valid.textKosong(TOperator3,"Operator 3");
        }else if(TAsisOperator1.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 1");
        }else if(TAsisOperator2.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator2,"Asisten Operator 2");
        }else if(TAsisOperator3.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator3,"Asisten Operator 2");
        }else if(TInstrumen.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 3");
        }else if(TAlat.getText().trim().equals("")){
            Valid.textKosong(TAlat,"Alat OK");
        }else if(TAnastesi.getText().trim().equals("")){
            Valid.textKosong(TAnastesi,"Dokter Anetesi");
        }else if(TAsisAnastesi.getText().trim().equals("")){
            Valid.textKosong(TAsisAnastesi,"Asisten Anestesi 1");
        }else if(TAsisAnastesi1.getText().trim().equals("")){
            Valid.textKosong(TAsisAnastesi1,"Asisten Anestesi 2");
        }else if(TAnak.getText().trim().equals("")){
            Valid.textKosong(TAnak,"Dokter Anak");
        }else if(TResusitas.getText().trim().equals("")){
            Valid.textKosong(TResusitas,"Perawat Resusitas");
        }else if(TPerawatLuar.getText().trim().equals("")){
            Valid.textKosong(TPerawatLuar,"Perawat Luar");
        }else if(TBidan1.getText().trim().equals("")){
            Valid.textKosong(TBidan1,"Bidan 1");
        }else if(TBidan2.getText().trim().equals("")){
            Valid.textKosong(TBidan2,"Bidan 2");
        }else if(TBidan3.getText().trim().equals("")){
            Valid.textKosong(TBidan3,"Bidan 3");
        }else if(TSewaOK.getText().trim().equals("")){
            Valid.textKosong(TSewaOK,"Sewa OK");
        }else if(TAkomodasi.getText().trim().equals("")){
            Valid.textKosong(TAkomodasi,"Sewa VK");
        }else if(TBagianRS.getText().trim().equals("")){
            Valid.textKosong(TBagianRS,"Bagian RS");
        }else if(TOmloop1.getText().trim().equals("")){
            Valid.textKosong(TOmloop1,"Onloop 1");
        }else if(TOmloop2.getText().trim().equals("")){
            Valid.textKosong(TOmloop2,"Onloop 2");
        }else if(TOmloop3.getText().trim().equals("")){
            Valid.textKosong(TOmloop3,"Onloop 3");
        }else if(TOmloop4.getText().trim().equals("")){
            Valid.textKosong(TOmloop4,"Onloop 4");
        }else if(TOmloop5.getText().trim().equals("")){
            Valid.textKosong(TOmloop5,"Onloop 5");
        }else if(TSarpras.getText().trim().equals("")){
            Valid.textKosong(TSarpras,"Sarpras");
        }else if(TdrPJAnak.getText().trim().equals("")){
            Valid.textKosong(TdrPJAnak,"Dokter Pj Anak");
        }else if(TdrUmum.getText().trim().equals("")){
            Valid.textKosong(TdrUmum,"Dokter Umum");
        }else if(kdpnj.getText().trim().equals("")||nmpnj.getText().trim().equals("")){
            Valid.textKosong(kdpnj,"Jenis Bayar");
        }else{
            Sequel.menyimpan("paket_operasi","'"+TKd.getText()+"', '"+TNm.getText()+"','"+Kategori.getSelectedItem()+"','"+TOperator1.getText()+
                    "','"+TOperator2.getText()+"','"+TOperator3.getText()+"','"+TAsisOperator1.getText()+
                    "','"+TAsisOperator2.getText()+"','"+TAsisOperator3.getText()+"','"+TInstrumen.getText()+"','"+TAnak.getText()+
                    "','"+TResusitas.getText()+"','"+TAnastesi.getText()+"','"+TAsisAnastesi.getText()+"','"+TAsisAnastesi1.getText()+
                    "','"+TBidan1.getText()+"','"+TBidan2.getText()+"','"+TBidan3.getText()+"','"+TPerawatLuar.getText()+"','"+TSewaOK.getText()+
                    "','"+TAlat.getText()+"','"+TAkomodasi.getText()+"','"+TBagianRS.getText()+"','"+TOmloop1.getText()+"','"+TOmloop2.getText()+
                    "','"+TOmloop3.getText()+"','"+TOmloop4.getText()+"','"+TOmloop5.getText()+"','"+TSarpras.getText()+"','"+TdrPJAnak.getText()+
                    "','"+TdrUmum.getText()+"','"+kdpnj.getText()+"','1','"+Kelas.getSelectedItem()+"'","Kode Jenis");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Kelas,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed

        emptTeks();        
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for(i=0;i<tbJnsPerawatan.getRowCount();i++){ 
            if(tbJnsPerawatan.getValueAt(i,0).toString().equals("true")){
                Sequel.mengedit("paket_operasi","kode_paket='"+tbJnsPerawatan.getValueAt(i,1).toString()+"'","status='0'");
            }
        } 
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TKd.getText().trim().equals("")){
            Valid.textKosong(TKd,"Kode Jenis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Nama Perawatan");
        }else if(TOperator1.getText().trim().equals("")){
            Valid.textKosong(TOperator1,"Operator 1");
        }else if(TOperator2.getText().trim().equals("")){
            Valid.textKosong(TOperator2,"Operator 2");
        }else if(TOperator3.getText().trim().equals("")){
            Valid.textKosong(TOperator3,"Operator 3");
        }else if(TAsisOperator1.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 1");
        }else if(TAsisOperator2.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator2,"Asisten Operator 2");
        }else if(TAsisOperator3.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator3,"Asisten Operator 2");
        }else if(TInstrumen.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 3");
        }else if(TAlat.getText().trim().equals("")){
            Valid.textKosong(TAlat,"Alat OK");
        }else if(TAnastesi.getText().trim().equals("")){
            Valid.textKosong(TAnastesi,"Dokter Anetesi");
        }else if(TAsisAnastesi.getText().trim().equals("")){
            Valid.textKosong(TAsisAnastesi,"Asisten Anestesi 1");
        }else if(TAsisAnastesi1.getText().trim().equals("")){
            Valid.textKosong(TAsisAnastesi1,"Asisten Anestesi 2");
        }else if(TAnak.getText().trim().equals("")){
            Valid.textKosong(TAnak,"Dokter Anak");
        }else if(TResusitas.getText().trim().equals("")){
            Valid.textKosong(TResusitas,"Perawat Resusitas");
        }else if(TPerawatLuar.getText().trim().equals("")){
            Valid.textKosong(TPerawatLuar,"Perawat Luar");
        }else if(TBidan1.getText().trim().equals("")){
            Valid.textKosong(TBidan1,"Bidan 1");
        }else if(TBidan2.getText().trim().equals("")){
            Valid.textKosong(TBidan2,"Bidan 2");
        }else if(TBidan3.getText().trim().equals("")){
            Valid.textKosong(TBidan3,"Bidan 3");
        }else if(TSewaOK.getText().trim().equals("")){
            Valid.textKosong(TSewaOK,"Sewa OK");
        }else if(TAkomodasi.getText().trim().equals("")){
            Valid.textKosong(TAkomodasi,"Sewa VK");
        }else if(TBagianRS.getText().trim().equals("")){
            Valid.textKosong(TBagianRS,"Bagian RS");
        }else if(TOmloop1.getText().trim().equals("")){
            Valid.textKosong(TOmloop1,"Onloop 1");
        }else if(TOmloop2.getText().trim().equals("")){
            Valid.textKosong(TOmloop2,"Onloop 2");
        }else if(TOmloop3.getText().trim().equals("")){
            Valid.textKosong(TOmloop3,"Onloop 3");
        }else if(TOmloop4.getText().trim().equals("")){
            Valid.textKosong(TOmloop4,"Onloop 4");
        }else if(TOmloop5.getText().trim().equals("")){
            Valid.textKosong(TOmloop5,"Onloop 5");
        }else if(TSarpras.getText().trim().equals("")){
            Valid.textKosong(TSarpras,"Sarpras");
        }else if(TdrPJAnak.getText().trim().equals("")){
            Valid.textKosong(TdrPJAnak,"Dokter Pj Anak");
        }else if(TdrUmum.getText().trim().equals("")){
            Valid.textKosong(TdrUmum,"Dokter Umum");
        }else if(kdpnj.getText().trim().equals("")||nmpnj.getText().trim().equals("")){
            Valid.textKosong(kdpnj,"Jenis Bayar");
        }else{
            if(tbJnsPerawatan.getSelectedRow()!= -1){
                Sequel.mengedit("paket_operasi","kode_paket='"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1) +"'",
                        "kode_paket='"+TKd.getText()+"',nm_perawatan='"+TNm.getText()+"',operator1='"+TOperator1.getText()+"',operator2='"+TOperator2.getText()+"',operator3='"+TOperator3.getText()+
                        "',asisten_operator1='"+TAsisOperator1.getText()+"',asisten_operator2='"+TAsisOperator2.getText()+"',asisten_operator3='"+TAsisOperator3.getText()+"',instrumen='"+TInstrumen.getText()+
                        "',dokter_anak='"+TAnak.getText()+"',perawaat_resusitas='"+TResusitas.getText()+"',kategori='"+Kategori.getSelectedItem()+
                        "',alat='"+TAlat.getText()+"',dokter_anestesi='"+TAnastesi.getText()+"',asisten_anestesi='"+TAsisAnastesi.getText()+"',asisten_anestesi2='"+TAsisAnastesi1.getText()+
                        "',bidan='"+TBidan1.getText()+"',bidan2='"+TBidan2.getText()+"',bidan3='"+TBidan3.getText()+"',perawat_luar='"+TPerawatLuar.getText()+"'"+
                        ",sewa_ok='"+TSewaOK.getText()+"',akomodasi='"+TAkomodasi.getText()+"',bagian_rs='"+TBagianRS.getText()+"',omloop='"+TOmloop1.getText()+"',"+
                        "omloop2='"+TOmloop2.getText()+"',omloop3='"+TOmloop3.getText()+"',omloop4='"+TOmloop4.getText()+"',omloop5='"+TOmloop5.getText()+"',sarpras='"+TSarpras.getText()+"',dokter_pjanak='"+TdrPJAnak.getText()+"',"+
                        "dokter_umum='"+TdrUmum.getText()+"',kd_pj='"+kdpnj.getText()+"',kelas='"+Kelas.getSelectedItem()+"'");
                if(tabMode.getRowCount()!=0){tampil();}
                emptTeks();
            }                
        }
        
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptPaketOperasi.jasper","report","::[ Data Paket Operasi ]::",
                   "select paket_operasi.kode_paket, paket_operasi.nm_perawatan,(paket_operasi.operator1+paket_operasi.operator2+paket_operasi.operator3+"+
                       "paket_operasi.asisten_operator1+paket_operasi.asisten_operator2+paket_operasi.asisten_operator3+paket_operasi.instrumen+"+
                       "paket_operasi.dokter_anak+paket_operasi.perawaat_resusitas+"+
                       "paket_operasi.alat+paket_operasi.dokter_anestesi+paket_operasi.asisten_anestesi+paket_operasi.asisten_anestesi2+"+
                       "paket_operasi.bidan+paket_operasi.bidan2+paket_operasi.bidan3+paket_operasi.perawat_luar+"+
                       "paket_operasi.sewa_ok+paket_operasi.akomodasi+paket_operasi.bagian_rs+"+
                       "paket_operasi.omloop+paket_operasi.omloop2+paket_operasi.omloop3+paket_operasi.omloop4+paket_operasi.omloop5+"+
                       "paket_operasi.sarpras+paket_operasi.dokter_pjanak+paket_operasi.dokter_umum) as jumlah "+
                   "from paket_operasi inner join penjab on penjab.kd_pj=paket_operasi.kd_pj "+
                   "where paket_operasi.status='1' and paket_operasi.kode_paket like '%"+TCari.getText()+"%' or "+
                   "paket_operasi.status='1' and paket_operasi.nm_perawatan like '%"+TCari.getText()+"%' or "+
                   "paket_operasi.status='1' and paket_operasi.kelas like '%"+TCari.getText()+"%' or "+
                   "paket_operasi.status='1' and penjab.png_jawab like '%"+TCari.getText()+"%' order by paket_operasi.kode_paket ",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnAll);
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
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
                if(evt.getClickCount()==2){
                    TabRawat.setSelectedIndex(0);
                    Kelas.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),34).toString());
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                    Kelas.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),34).toString());
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_A){                
                for(i=0;i<tbJnsPerawatan.getRowCount();i++){ 
                    tbJnsPerawatan.setValueAt(true,i,0);
                }
            } 
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void TSewaOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSewaOKKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAkomodasi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TPerawatLuar.requestFocus();
        }
}//GEN-LAST:event_TSewaOKKeyPressed

private void TAnastesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAnastesiKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOperator3.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAnak.requestFocus();
        }
}//GEN-LAST:event_TAnastesiKeyPressed

private void TAsisAnastesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAsisAnastesiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAsisOperator3.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAsisAnastesi1.requestFocus();
        }
}//GEN-LAST:event_TAsisAnastesiKeyPressed

private void TPerawatLuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPerawatLuarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TSewaOK.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TSarpras.requestFocus();
        }
}//GEN-LAST:event_TPerawatLuarKeyPressed

private void TOperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOperator2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOperator1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOperator3.requestFocus();
        }
}//GEN-LAST:event_TOperator2KeyPressed

private void TOperator3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOperator3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOperator2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAnastesi.requestFocus();
        }
}//GEN-LAST:event_TOperator3KeyPressed

private void TAsisOperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAsisOperator2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAsisOperator1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAsisOperator3.requestFocus();
        }
}//GEN-LAST:event_TAsisOperator2KeyPressed

private void TInstrumenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInstrumenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAsisOperator2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAsisOperator1.requestFocus();
        }
}//GEN-LAST:event_TInstrumenKeyPressed

private void TAnakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAnakKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAnastesi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TBagianRS.requestFocus();
        }
}//GEN-LAST:event_TAnakKeyPressed

private void TResusitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TResusitasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAsisAnastesi1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAkomodasi.requestFocus();
        } 
}//GEN-LAST:event_TResusitasKeyPressed

private void TBidan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBidan1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TPerawatLuar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TBidan2.requestFocus();
        }
}//GEN-LAST:event_TBidan1KeyPressed

private void TBagianRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBagianRSKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAnak.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAlat.requestFocus();
        }
}//GEN-LAST:event_TBagianRSKeyPressed

private void TAkomodasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAkomodasiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TResusitas.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TSewaOK.requestFocus();
        }
}//GEN-LAST:event_TAkomodasiKeyPressed

private void TOmloop1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOmloop1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TBidan3.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOmloop2.requestFocus();
        }
}//GEN-LAST:event_TOmloop1KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void KategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KategoriKeyPressed
        Valid.pindah(evt,TNm,kdpnj);
    }//GEN-LAST:event_KategoriKeyPressed

    private void TBidan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBidan2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TBidan1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TBidan3.requestFocus();
        }
    }//GEN-LAST:event_TBidan2KeyPressed

    private void TBidan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBidan3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TBidan2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOmloop1.requestFocus();
        }
    }//GEN-LAST:event_TBidan3KeyPressed

    private void TOmloop2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOmloop2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOmloop1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOmloop3.requestFocus();
        }
    }//GEN-LAST:event_TOmloop2KeyPressed

    private void TOmloop3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOmloop3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOmloop2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOmloop4.requestFocus();
        }
    }//GEN-LAST:event_TOmloop3KeyPressed

    private void TSarprasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSarprasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TPerawatLuar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TBidan1.requestFocus();
        }
    }//GEN-LAST:event_TSarprasKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TdrUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TdrUmumKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAlat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TdrPJAnak.requestFocus();
        }
    }//GEN-LAST:event_TdrUmumKeyPressed

    private void TdrPJAnakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TdrPJAnakKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TdrUmum.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TInstrumen.requestFocus();
        }
    }//GEN-LAST:event_TdrPJAnakKeyPressed

    private void kdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpnjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
            Kategori.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
            TOperator1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPenjabActionPerformed(null);
        }
    }//GEN-LAST:event_kdpnjKeyPressed

    private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
        penjab.isCek();
        penjab.onCari();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnPenjabActionPerformed

    private void MnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRestoreActionPerformed
        DlgRestoreTarifOperasi restore=new DlgRestoreTarifOperasi(null,true);
        restore.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        restore.setLocationRelativeTo(internalFrame1);
        restore.setVisible(true);
    }//GEN-LAST:event_MnRestoreActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(this.getHeight()<540){   
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormInput.setPreferredSize(new Dimension(FormInput.WIDTH,390));
            if(this.getWidth()<760){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(740,390));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }else{
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);            
            if(this.getWidth()<760){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(740,FormInput.HEIGHT));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void TAsisOperator3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAsisOperator3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAsisOperator2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAsisAnastesi.requestFocus();
        }
    }//GEN-LAST:event_TAsisOperator3KeyPressed

    private void TAsisAnastesi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAsisAnastesi1KeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAsisAnastesi1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TResusitas.requestFocus();
        }
    }//GEN-LAST:event_TAsisAnastesi1KeyPressed

    private void TOmloop4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOmloop4KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOmloop4.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOmloop5.requestFocus();
        }
    }//GEN-LAST:event_TOmloop4KeyPressed

    private void TOmloop5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOmloop5KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOmloop4.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            Kelas.requestFocus();
        }
    }//GEN-LAST:event_TOmloop5KeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt,TOmloop5,BtnSimpan);
    }//GEN-LAST:event_KelasKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgJnsPerawatanOperasi dialog = new DlgJnsPerawatanOperasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPenjab;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Kategori;
    private widget.ComboBox Kelas;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnRestore;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TAkomodasi;
    private widget.TextBox TAlat;
    private widget.TextBox TAnak;
    private widget.TextBox TAnastesi;
    private widget.TextBox TAsisAnastesi;
    private widget.TextBox TAsisAnastesi1;
    private widget.TextBox TAsisOperator1;
    private widget.TextBox TAsisOperator2;
    private widget.TextBox TAsisOperator3;
    private widget.TextBox TBagianRS;
    private widget.TextBox TBidan1;
    private widget.TextBox TBidan2;
    private widget.TextBox TBidan3;
    private widget.TextBox TCari;
    private widget.TextBox TInstrumen;
    private widget.TextBox TKd;
    private widget.TextBox TNm;
    private widget.TextBox TOmloop1;
    private widget.TextBox TOmloop2;
    private widget.TextBox TOmloop3;
    private widget.TextBox TOmloop4;
    private widget.TextBox TOmloop5;
    private widget.TextBox TOperator1;
    private widget.TextBox TOperator2;
    private widget.TextBox TOperator3;
    private widget.TextBox TPerawatLuar;
    private widget.TextBox TResusitas;
    private widget.TextBox TSarpras;
    private widget.TextBox TSewaOK;
    private widget.TextBox TTotal;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TdrPJAnak;
    private widget.TextBox TdrUmum;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpnj;
    private widget.TextBox nmpnj;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            pstampil=koneksi.prepareStatement(
                       "select paket_operasi.kode_paket, paket_operasi.nm_perawatan,paket_operasi.kategori,"+
                       "paket_operasi.operator1, paket_operasi.operator2, paket_operasi.operator3, "+
                       "paket_operasi.asisten_operator1, paket_operasi.asisten_operator2,paket_operasi.asisten_operator3,"+
                       "paket_operasi.instrumen, paket_operasi.dokter_anestesi,paket_operasi.asisten_anestesi,paket_operasi.asisten_anestesi2,"+
                       "paket_operasi.dokter_anak,paket_operasi.perawaat_resusitas, paket_operasi.bidan, "+
                       "paket_operasi.bidan2, paket_operasi.bidan3, paket_operasi.perawat_luar, paket_operasi.alat,"+
                       "paket_operasi.sewa_ok,paket_operasi.akomodasi,paket_operasi.bagian_rs,"+
                       "paket_operasi.omloop,paket_operasi.omloop2,paket_operasi.omloop3,paket_operasi.omloop4,paket_operasi.omloop5,"+
                       "paket_operasi.sarpras,paket_operasi.dokter_pjanak,paket_operasi.dokter_umum, "+
                       "(paket_operasi.operator1+paket_operasi.operator2+paket_operasi.operator3+"+
                       "paket_operasi.asisten_operator1+paket_operasi.asisten_operator2+paket_operasi.asisten_operator3+paket_operasi.instrumen+"+
                       "paket_operasi.dokter_anak+paket_operasi.perawaat_resusitas+"+
                       "paket_operasi.alat+paket_operasi.dokter_anestesi+paket_operasi.asisten_anestesi+paket_operasi.asisten_anestesi2+"+
                       "paket_operasi.bidan+paket_operasi.bidan2+paket_operasi.bidan3+paket_operasi.perawat_luar+"+
                       "paket_operasi.sewa_ok+paket_operasi.akomodasi+paket_operasi.bagian_rs+"+
                       "paket_operasi.omloop+paket_operasi.omloop2+paket_operasi.omloop3+paket_operasi.omloop4+paket_operasi.omloop5+"+
                       "paket_operasi.sarpras+paket_operasi.dokter_pjanak+paket_operasi.dokter_umum) as jumlah, "+
                       "penjab.png_jawab,paket_operasi.kelas from paket_operasi inner join penjab on penjab.kd_pj=paket_operasi.kd_pj "+
                       "where paket_operasi.status='1' and paket_operasi.kode_paket like ? or "+
                       "paket_operasi.status='1' and paket_operasi.nm_perawatan like ? or "+
                       "paket_operasi.status='1' and paket_operasi.kelas like ? or "+
                       "paket_operasi.status='1' and penjab.png_jawab like ? order by paket_operasi.kode_paket ");
            try{
                pstampil.setString(1,"%"+TCari.getText()+"%");
                pstampil.setString(2,"%"+TCari.getText()+"%");
                pstampil.setString(3,"%"+TCari.getText()+"%");
                pstampil.setString(4,"%"+TCari.getText()+"%");
                rs=pstampil.executeQuery();
                while(rs.next()){                    
                    tabMode.addRow(new Object[]{false,rs.getString("kode_paket"),
                                   rs.getString("nm_perawatan"),
                                   rs.getString("kategori"), 
                                   rs.getDouble("operator1"), 
                                   rs.getDouble("operator2"), 
                                   rs.getDouble("operator3"), 
                                   rs.getDouble("asisten_operator1"), 
                                   rs.getDouble("asisten_operator2"), 
                                   rs.getDouble("asisten_operator3"), 
                                   rs.getDouble("instrumen"), 
                                   rs.getDouble("dokter_anestesi"), 
                                   rs.getDouble("asisten_anestesi"),
                                   rs.getDouble("asisten_anestesi2"), 
                                   rs.getDouble("dokter_anak"), 
                                   rs.getDouble("perawaat_resusitas"), 
                                   rs.getDouble("bidan"), 
                                   rs.getDouble("bidan2"), 
                                   rs.getDouble("bidan3"), 
                                   rs.getDouble("perawat_luar"), 
                                   rs.getDouble("alat"), 
                                   rs.getDouble("sewa_ok"), 
                                   rs.getDouble("akomodasi"), 
                                   rs.getDouble("bagian_rs"), 
                                   rs.getDouble("omloop"), 
                                   rs.getDouble("omloop2"), 
                                   rs.getDouble("omloop3"), 
                                   rs.getDouble("omloop4"), 
                                   rs.getDouble("omloop5"), 
                                   rs.getDouble("sarpras"), 
                                   rs.getDouble("dokter_pjanak"), 
                                   rs.getDouble("dokter_umum"), 
                                   rs.getDouble("jumlah"),
                                   rs.getString("png_jawab"),
                                   rs.getString("kelas")
                    });
                }  
            } catch(Exception e){
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pstampil!=null){
                    pstampil.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TKd.setText("");
        TNm.setText("");
        TOperator1.setText("0");
        TOperator2.setText("0");
        TOperator3.setText("0");
        TAsisOperator1.setText("0");
        TAsisOperator2.setText("0");
        TAsisOperator3.setText("0");
        TInstrumen.setText("0");
        TAnastesi.setText("0");
        TAsisAnastesi.setText("0");        
        TAsisAnastesi1.setText("0");
        TAnak.setText("0");
        TResusitas.setText("0");
        TBidan1.setText("0");
        TBidan2.setText("0");
        TBidan3.setText("0");
        TAlat.setText("0");
        Kategori.setSelectedIndex(0);
        TPerawatLuar.setText("0");
        TSewaOK.setText("0");
        TAkomodasi.setText("0");
        TBagianRS.setText("0");
        TOmloop1.setText("0");
        TOmloop2.setText("0");
        TOmloop3.setText("0");
        TOmloop4.setText("0");
        TOmloop5.setText("0");
        TSarpras.setText("0");
        TdrPJAnak.setText("0");        
        TdrUmum.setText("0");
        TTotal.setText("0");
        Kelas.setSelectedIndex(0);
        //Valid.autoNomer(" paket_operasi ","JP",6,TKd);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kode_paket,5),signed)),0) from paket_operasi  ","PK",6,TKd);
        TKd.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            TKd.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            TNm.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
            Kategori.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
            TOperator1.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString())));
            TOperator2.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),5).toString())));
            TOperator3.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),6).toString())));
            TAsisOperator1.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString())));
            TAsisOperator2.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString())));
            TAsisOperator3.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),9).toString())));
            TInstrumen.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),10).toString())));
            TAnastesi.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),11).toString())));
            TAsisAnastesi.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),12).toString())));
            TAsisAnastesi1.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),13).toString())));
            TAnak.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),14).toString())));
            TResusitas.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),15).toString())));
            TBidan1.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),16).toString())));
            TBidan2.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),17).toString())));
            TBidan3.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),18).toString())));
            TPerawatLuar.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),19).toString())));
            TAlat.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),20).toString())));
            TSewaOK.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),21).toString())));
            TAkomodasi.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),22).toString())));
            TBagianRS.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),23).toString())));
            TOmloop1.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),24).toString())));
            TOmloop2.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),25).toString())));
            TOmloop3.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),26).toString())));
            TOmloop4.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),27).toString())));
            TOmloop5.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),28).toString())));
            TSarpras.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),29).toString())));
            TdrPJAnak.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),30).toString())));
            TdrUmum.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),31).toString())));
            TTotal.setText(Valid.SetAngka2(Double.parseDouble(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),32).toString())));
            Sequel.cariIsi("select kd_pj from paket_operasi where kode_paket=?", kdpnj,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            nmpnj.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),33).toString());
        }
    }


    private void isjml(){       

        if((!TOperator1.getText().equals(""))){
           operator1=Double.parseDouble(TOperator1.getText().trim());            
        }
        if((!TOperator2.getText().equals(""))){
           operator2=Double.parseDouble(TOperator2.getText().trim());            
        }
        if((!TOperator3.getText().equals(""))){
           operator3=Double.parseDouble(TOperator3.getText().trim());            
        }
        if((!TAsisOperator1.getText().equals(""))){
           asistenoperator1=Double.parseDouble(TAsisOperator1.getText().trim());            
        }
        if((!TAsisOperator2.getText().equals(""))){
           asistenoperator2=Double.parseDouble(TAsisOperator2.getText().trim());            
        }
        if((!TAsisOperator3.getText().equals(""))){
           asistenoperator3=Double.parseDouble(TAsisOperator3.getText().trim());            
        }
        if((!TInstrumen.getText().equals(""))){
           instrumen=Double.parseDouble(TInstrumen.getText().trim());            
        }
        if((!TAnastesi.getText().equals(""))){
           anastesi=Double.parseDouble(TAnastesi.getText().trim());            
        }
        if((!TAsisAnastesi.getText().equals(""))){
           asistenanas=Double.parseDouble(TAsisAnastesi.getText().trim());            
        }
        if((!TAsisAnastesi1.getText().equals(""))){
           asistenanas2=Double.parseDouble(TAsisAnastesi1.getText().trim());            
        }
        if((!TAnak.getText().equals(""))){
           dokteranak=Double.parseDouble(TAnak.getText().trim());            
        }
        if((!TResusitas.getText().equals(""))){
           perawatresusitas=Double.parseDouble(TResusitas.getText().trim());            
        }
        if((!TBidan1.getText().equals(""))){
           bidan1=Double.parseDouble(TBidan1.getText().trim());            
        }
        if((!TBidan2.getText().equals(""))){
           bidan2=Double.parseDouble(TBidan2.getText().trim());            
        }
        if((!TBidan3.getText().equals(""))){
           bidan3=Double.parseDouble(TBidan3.getText().trim());            
        }
        if((!TAlat.getText().equals(""))){
           alat=Double.parseDouble(TAlat.getText().trim());            
        }
        if((!TSewaOK.getText().equals(""))){
           sewaok=Double.parseDouble(TSewaOK.getText().trim());            
        }
        if((!TPerawatLuar.getText().equals(""))){
           perawatluar=Double.parseDouble(TPerawatLuar.getText().trim());            
        }
        if((!TAkomodasi.getText().equals(""))){
           sewavk=Double.parseDouble(TAkomodasi.getText().trim());            
        }
        if((!TBagianRS.getText().equals(""))){
           bagianrs=Double.parseDouble(TBagianRS.getText().trim());            
        }
        if((!TOmloop1.getText().equals(""))){
           omloop1=Double.parseDouble(TOmloop1.getText().trim());            
        }
        if((!TOmloop2.getText().equals(""))){
           omloop2=Double.parseDouble(TOmloop2.getText().trim());            
        }
        if((!TOmloop3.getText().equals(""))){
           omloop3=Double.parseDouble(TOmloop3.getText().trim());            
        }
        if((!TOmloop4.getText().equals(""))){
           omloop4=Double.parseDouble(TOmloop4.getText().trim());            
        }
        if((!TOmloop5.getText().equals(""))){
           omloop5=Double.parseDouble(TOmloop5.getText().trim());            
        }
        if((!TSarpras.getText().equals(""))){
           sarpras=Double.parseDouble(TSarpras.getText().trim());            
        }
        if((!TdrPJAnak.getText().equals(""))){
           dokterpjanak=Double.parseDouble(TdrPJAnak.getText().trim());            
        }
        if((!TdrUmum.getText().equals(""))){
           dokterumum=Double.parseDouble(TdrUmum.getText().trim());            
        }
        TTotal.setText(Valid.SetAngka2(operator1+operator2+operator3+asistenoperator1+
                asistenoperator2+asistenoperator3+instrumen+dokteranak+perawatresusitas+bidan1+bidan2+bidan3+
                alat+anastesi+perawatluar+asistenanas+asistenanas2+sewaok+sewavk+bagianrs+omloop1+omloop2+
                omloop3+omloop4+omloop5+sarpras+dokterpjanak+dokterumum));
    }
    
    public JTextField getTextField(){
        return TKd;
    }

    public JButton getButton(){
        return BtnKeluar;
    }    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettarif_operasi());
        BtnHapus.setEnabled(akses.gettarif_operasi());
        BtnEdit.setEnabled(akses.gettarif_operasi());
        BtnPrint.setEnabled(akses.gettarif_operasi());
        if(akses.getkode().equals("Admin Utama")){
            MnRestore.setEnabled(true);
        }else{
            MnRestore.setEnabled(false);
        } 
    }
    
    public JTable getTable(){
        return tbJnsPerawatan;
    }

    
}

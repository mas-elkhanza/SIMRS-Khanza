package simrskhanza;
import keuangan.DlgJnsPerawatanOperasi;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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

public class DlgTagihanOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstindakan,pstindakan2,psobat,psset_tarif;
    private ResultSet rs,rsset_tarif;
    private DlgCariPetugas petugas=new DlgCariPetugas( null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private String cara_bayar_operasi="Yes",kd_pj="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal*/
    public DlgTagihanOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"P","Kode Paket","Nama Operasi","Kategori","Operator 1","Operator 2","Operator 3",
                      "Asisten Op 1","Asisten Op 2","Instrumen","dr Anak","Perawat Resus","dr Anastesi",
                      "Asisten Anastesi","Bidan 1","Bidan 2","Bidan 3","Perawat Luar","Alat","Sewa OK/VK",
                      "Akomodasi","N.M.S.","Onloop 1","Onloop 2","Onloop 3","Sarpras","dr Pj Anak","dr Umum",
                      "Total"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==1)||(colIndex==2)||(colIndex==3)||(colIndex==29)) {
                    a=false;
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
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbtindakan.setModel(tabMode);

        tbtindakan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbtindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 29; i++) {
            TableColumn column = tbtindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbtindakan.setDefaultRenderer(Object.class, new WarnaTable());
        
        //tagihan obat
        Object[] row2={"Jumlah",
        "Kode",
        "Nama",
        "Satuan",
        "Harga",
        "Total"};
        
        tabMode2=new DefaultTableModel(null,row2){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==4)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObat.setModel(tabMode2);
        //tampil();

        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(90);
            }
        }

        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        jenis.setDocument(new batasInput((byte)8).getKata(jenis));
        kdoperator1.setDocument(new batasInput((byte)20).getKata(kdoperator1));
        kdoperator2.setDocument(new batasInput((byte)20).getKata(kdoperator2));
        kdoperator3.setDocument(new batasInput((byte)20).getKata(kdoperator3));
        kdasistoperator1.setDocument(new batasInput((byte)20).getKata(kdasistoperator1));
        kdasistoperator2.setDocument(new batasInput((byte)20).getKata(kdasistoperator2));
        kdInstrumen.setDocument(new batasInput((byte)20).getKata(kdInstrumen));
        kdanestesi.setDocument(new batasInput((byte)20).getKata(kdanestesi));
        kdasistanestesi.setDocument(new batasInput((byte)20).getKata(kdasistanestesi));
        kddranak.setDocument(new batasInput((byte)20).getKata(kddranak));
        kdprwresust.setDocument(new batasInput((byte)20).getKata(kdprwresust));
        kdbidan.setDocument(new batasInput((byte)20).getKata(kdbidan));
        kdbidan2.setDocument(new batasInput((byte)20).getKata(kdbidan2));
        kdbidan3.setDocument(new batasInput((byte)20).getKata(kdbidan3));
        kdprwluar.setDocument(new batasInput((byte)20).getKata(kdprwluar));
        kdonloop1.setDocument(new batasInput((byte)20).getKata(kdonloop1));
        kdonloop2.setDocument(new batasInput((byte)20).getKata(kdonloop2));
        kdonloop3.setDocument(new batasInput((byte)20).getKata(kdonloop3));
        kdpjanak.setDocument(new batasInput((byte)20).getKata(kdpjanak));        
        kddrumum.setDocument(new batasInput((byte)20).getKata(kddrumum));
        
        TCariPaket.setDocument(new batasInput((byte)100).getKata(TCari)); 
        TCari.setDocument(new batasInput((byte)100).getKata(TCari)); 
        if(koneksiDB.cariCepat().equals("aktif")){
            TCariPaket.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        }  
        
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil2();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil2();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil2();}
            });
        }  
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    if(pilihan==1){
                        kdoperator1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmoperator1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdoperator1.requestFocus();
                    }else if(pilihan==2){
                        kdoperator2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmoperator2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdoperator2.requestFocus();
                    }else if(pilihan==3){
                        kdoperator3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmoperator3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdoperator3.requestFocus();
                    }else if(pilihan==4){
                        kdanestesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmanestesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdanestesi.requestFocus();
                    }else if(pilihan==5){
                        kddranak.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmdranak.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kddranak.requestFocus();
                    }else if(pilihan==6){
                        kdpjanak.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmpjanak.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdpjanak.requestFocus();
                    }else if(pilihan==7){
                        kddrumum.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmdrumum.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kddrumum.requestFocus();
                    }                   
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
                if(petugas.getTable().getSelectedRow()!= -1){    
                    if(pilihan==1){
                        kdasistoperator1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmasistoperator1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdasistoperator1.requestFocus();
                    }else if(pilihan==2){
                        kdasistoperator2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmasistoperator2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdasistoperator2.requestFocus();
                    }else if(pilihan==3){
                        kdInstrumen.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nminstrumen.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdInstrumen.requestFocus();
                    }else if(pilihan==4){
                        kdasistanestesi.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmasistanestesi.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdasistanestesi.requestFocus();
                    }else if(pilihan==5){
                        kdprwresust.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmprwresust.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdprwresust.requestFocus();
                    }else if(pilihan==6){
                        kdprwluar.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmprwluar.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdprwluar.requestFocus();
                    }else if(pilihan==7){
                        kdbidan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmbidan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdbidan.requestFocus();
                    }else if(pilihan==8){
                        kdbidan2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmbidan2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdbidan2.requestFocus();
                    }else if(pilihan==9){
                        kdbidan3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmbidan3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdbidan3.requestFocus();
                    }else if(pilihan==10){
                        kdonloop1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmonloop1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdonloop1.requestFocus();
                    }else if(pilihan==11){
                        kdonloop2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmonloop2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdonloop2.requestFocus();
                    }else if(pilihan==12){
                        kdonloop3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmonloop3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdonloop3.requestFocus();
                    }              
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
        
        TCari.requestFocus();
        ChkInput.setSelected(false);
        isForm();
        
    }
    private double y=0,biayatindakan=0,biayaobat=0;
    private int jml=0,pilihan=0,i=0,index=0;
    private boolean[] pilih; 
    private String[] kode_paket, nm_perawatan,kategori,kd_obat,nm_obat, satuan;
    private double[] operator1, operator2, operator3, asisten_operator1, asisten_operator2,
                  instrumen, dokter_anak, perawaat_resusitas, dokter_anestesi, asisten_anestesi, bidan,bidan2,bidan3, 
                  perawat_luar, sewa_ok, alat,akomodasi,bagian_rs,omloop,omloop2,omloop3,sarpras,ttltindakan,jmlobat,hargasatuan,ttlobat;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelisi5 = new widget.panelisi();
        label10 = new widget.Label();
        TCariPaket = new widget.TextBox();
        BtnCari2 = new widget.Button();
        BtnAll1 = new widget.Button();
        BtnTambahOperasi = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbtindakan = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelisi4 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        LTotal = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollPane1 = new widget.ScrollPane();
        FormInput = new widget.panelisi();
        label14 = new widget.Label();
        kdoperator1 = new widget.TextBox();
        nmoperator1 = new widget.TextBox();
        BtnOperator1 = new widget.Button();
        label11 = new widget.Label();
        tgl = new widget.Tanggal();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel4 = new widget.Label();
        jenis = new widget.TextBox();
        label17 = new widget.Label();
        kdasistoperator1 = new widget.TextBox();
        nmasistoperator1 = new widget.TextBox();
        btnAsis1 = new widget.Button();
        label19 = new widget.Label();
        kdoperator2 = new widget.TextBox();
        nmoperator2 = new widget.TextBox();
        BtnOperator2 = new widget.Button();
        label20 = new widget.Label();
        kdoperator3 = new widget.TextBox();
        nmoperator3 = new widget.TextBox();
        btnInstrumen = new widget.Button();
        label21 = new widget.Label();
        kdanestesi = new widget.TextBox();
        nmanestesi = new widget.TextBox();
        BtnAnastesi = new widget.Button();
        label22 = new widget.Label();
        kddranak = new widget.TextBox();
        nmdranak = new widget.TextBox();
        btnAnak = new widget.Button();
        btnAsis2 = new widget.Button();
        nmasistoperator2 = new widget.TextBox();
        kdasistoperator2 = new widget.TextBox();
        label18 = new widget.Label();
        btnAsis3 = new widget.Button();
        nminstrumen = new widget.TextBox();
        kdInstrumen = new widget.TextBox();
        label23 = new widget.Label();
        btnPrwRes = new widget.Button();
        nmprwresust = new widget.TextBox();
        kdprwresust = new widget.TextBox();
        label24 = new widget.Label();
        label26 = new widget.Label();
        kdasistanestesi = new widget.TextBox();
        nmasistanestesi = new widget.TextBox();
        BtnAsnes = new widget.Button();
        label27 = new widget.Label();
        kdbidan = new widget.TextBox();
        nmbidan = new widget.TextBox();
        btnBidan = new widget.Button();
        label28 = new widget.Label();
        kdprwluar = new widget.TextBox();
        nmprwluar = new widget.TextBox();
        btnPrwLuar = new widget.Button();
        jLabel5 = new widget.Label();
        Kategori = new widget.ComboBox();
        btnBidan2 = new widget.Button();
        nmbidan2 = new widget.TextBox();
        kdbidan2 = new widget.TextBox();
        label29 = new widget.Label();
        label30 = new widget.Label();
        kdbidan3 = new widget.TextBox();
        nmbidan3 = new widget.TextBox();
        btnBidan3 = new widget.Button();
        label25 = new widget.Label();
        kdonloop1 = new widget.TextBox();
        nmonloop1 = new widget.TextBox();
        btnOnloop1 = new widget.Button();
        btnOnloop2 = new widget.Button();
        nmonloop2 = new widget.TextBox();
        kdonloop2 = new widget.TextBox();
        label31 = new widget.Label();
        label32 = new widget.Label();
        btnOnloop3 = new widget.Button();
        nmonloop3 = new widget.TextBox();
        kdonloop3 = new widget.TextBox();
        label33 = new widget.Label();
        kdpjanak = new widget.TextBox();
        nmpjanak = new widget.TextBox();
        btndrpjanak = new widget.Button();
        label34 = new widget.Label();
        kddrumum = new widget.TextBox();
        nmdrumum = new widget.TextBox();
        btndrumum = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(102, 51, 0));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
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
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tagihan Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Tindakan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi5.add(label10);

        TCariPaket.setToolTipText("Alt+C");
        TCariPaket.setName("TCariPaket"); // NOI18N
        TCariPaket.setPreferredSize(new java.awt.Dimension(215, 23));
        TCariPaket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPaketKeyPressed(evt);
            }
        });
        panelisi5.add(TCariPaket);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('1');
        BtnCari2.setToolTipText("Alt+1");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        panelisi5.add(BtnCari2);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll1.setMnemonic('2');
        BtnAll1.setToolTipText("Alt+2");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        BtnAll1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll1KeyPressed(evt);
            }
        });
        panelisi5.add(BtnAll1);

        BtnTambahOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahOperasi.setMnemonic('3');
        BtnTambahOperasi.setToolTipText("Alt+3");
        BtnTambahOperasi.setName("BtnTambahOperasi"); // NOI18N
        BtnTambahOperasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahOperasiActionPerformed(evt);
            }
        });
        panelisi5.add(BtnTambahOperasi);

        jPanel3.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbtindakan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbtindakan.setName("tbtindakan"); // NOI18N
        tbtindakan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbtindakanMouseClicked(evt);
            }
        });
        tbtindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbtindakanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbtindakan);

        jPanel3.add(Scroll1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Obat & BHP ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 102));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setBorder(null);
        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi4.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(215, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi4.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi4.add(BtnCari1);

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
        panelisi4.add(BtnAll);

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
        panelisi4.add(BtnTambah);

        jPanel2.add(panelisi4, java.awt.BorderLayout.PAGE_END);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        jPanel2.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LTotal.setText("Total Biaya : 0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(500, 30));
        panelisi1.add(LTotal);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 244));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setName("scrollPane1"); // NOI18N

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(89, 374));
        FormInput.setLayout(null);

        label14.setText("Operator 1 :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 70, 82, 23);

        kdoperator1.setName("kdoperator1"); // NOI18N
        kdoperator1.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator1KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator1);
        kdoperator1.setBounds(84, 70, 100, 23);

        nmoperator1.setEditable(false);
        nmoperator1.setName("nmoperator1"); // NOI18N
        nmoperator1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator1);
        nmoperator1.setBounds(185, 70, 190, 23);

        BtnOperator1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator1.setMnemonic('2');
        BtnOperator1.setToolTipText("Alt+2");
        BtnOperator1.setName("BtnOperator1"); // NOI18N
        BtnOperator1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator1);
        BtnOperator1.setBounds(376, 70, 28, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(406, 40, 102, 23);

        tgl.setEditable(false);
        tgl.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        tgl.setName("tgl"); // NOI18N
        tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tglKeyPressed(evt);
            }
        });
        FormInput.add(tgl);
        tgl.setBounds(510, 40, 150, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 82, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 180, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(265, 10, 535, 23);

        jLabel4.setText("Jenis Anasthesi :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(220, 40, 90, 23);

        jenis.setHighlighter(null);
        jenis.setName("jenis"); // NOI18N
        jenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jenisKeyPressed(evt);
            }
        });
        FormInput.add(jenis);
        jenis.setBounds(312, 40, 92, 23);

        label17.setText("Ast. Operator 1 :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(406, 70, 102, 23);

        kdasistoperator1.setName("kdasistoperator1"); // NOI18N
        kdasistoperator1.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistoperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistoperator1KeyPressed(evt);
            }
        });
        FormInput.add(kdasistoperator1);
        kdasistoperator1.setBounds(510, 70, 100, 23);

        nmasistoperator1.setEditable(false);
        nmasistoperator1.setName("nmasistoperator1"); // NOI18N
        nmasistoperator1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistoperator1);
        nmasistoperator1.setBounds(611, 70, 190, 23);

        btnAsis1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAsis1.setMnemonic('2');
        btnAsis1.setToolTipText("Alt+2");
        btnAsis1.setName("btnAsis1"); // NOI18N
        btnAsis1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAsis1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsis1ActionPerformed(evt);
            }
        });
        FormInput.add(btnAsis1);
        btnAsis1.setBounds(802, 70, 28, 23);

        label19.setText("Operator 2 :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(0, 100, 82, 23);

        kdoperator2.setName("kdoperator2"); // NOI18N
        kdoperator2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator2KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator2);
        kdoperator2.setBounds(84, 100, 100, 23);

        nmoperator2.setEditable(false);
        nmoperator2.setName("nmoperator2"); // NOI18N
        nmoperator2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator2);
        nmoperator2.setBounds(185, 100, 190, 23);

        BtnOperator2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator2.setMnemonic('2');
        BtnOperator2.setToolTipText("Alt+2");
        BtnOperator2.setName("BtnOperator2"); // NOI18N
        BtnOperator2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator2);
        BtnOperator2.setBounds(376, 100, 28, 23);

        label20.setText("Operator 3 :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(0, 130, 82, 23);

        kdoperator3.setName("kdoperator3"); // NOI18N
        kdoperator3.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator3KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator3);
        kdoperator3.setBounds(84, 130, 100, 23);

        nmoperator3.setEditable(false);
        nmoperator3.setName("nmoperator3"); // NOI18N
        nmoperator3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator3);
        nmoperator3.setBounds(185, 130, 190, 23);

        btnInstrumen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInstrumen.setMnemonic('2');
        btnInstrumen.setToolTipText("Alt+2");
        btnInstrumen.setName("btnInstrumen"); // NOI18N
        btnInstrumen.setPreferredSize(new java.awt.Dimension(28, 23));
        btnInstrumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInstrumenActionPerformed(evt);
            }
        });
        FormInput.add(btnInstrumen);
        btnInstrumen.setBounds(376, 130, 28, 23);

        label21.setText("dr Anestesi :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(0, 160, 82, 23);

        kdanestesi.setName("kdanestesi"); // NOI18N
        kdanestesi.setPreferredSize(new java.awt.Dimension(80, 23));
        kdanestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdanestesiKeyPressed(evt);
            }
        });
        FormInput.add(kdanestesi);
        kdanestesi.setBounds(84, 160, 100, 23);

        nmanestesi.setEditable(false);
        nmanestesi.setName("nmanestesi"); // NOI18N
        nmanestesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmanestesi);
        nmanestesi.setBounds(185, 160, 190, 23);

        BtnAnastesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAnastesi.setMnemonic('2');
        BtnAnastesi.setToolTipText("Alt+2");
        BtnAnastesi.setName("BtnAnastesi"); // NOI18N
        BtnAnastesi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAnastesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAnastesiActionPerformed(evt);
            }
        });
        FormInput.add(BtnAnastesi);
        BtnAnastesi.setBounds(376, 160, 28, 23);

        label22.setText("dr Anak :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(0, 190, 82, 23);

        kddranak.setName("kddranak"); // NOI18N
        kddranak.setPreferredSize(new java.awt.Dimension(80, 23));
        kddranak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddranakKeyPressed(evt);
            }
        });
        FormInput.add(kddranak);
        kddranak.setBounds(84, 190, 100, 23);

        nmdranak.setEditable(false);
        nmdranak.setName("nmdranak"); // NOI18N
        nmdranak.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmdranak);
        nmdranak.setBounds(185, 190, 190, 23);

        btnAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAnak.setMnemonic('2');
        btnAnak.setToolTipText("Alt+2");
        btnAnak.setName("btnAnak"); // NOI18N
        btnAnak.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnakActionPerformed(evt);
            }
        });
        FormInput.add(btnAnak);
        btnAnak.setBounds(376, 190, 28, 23);

        btnAsis2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAsis2.setMnemonic('2');
        btnAsis2.setToolTipText("Alt+2");
        btnAsis2.setName("btnAsis2"); // NOI18N
        btnAsis2.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAsis2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsis2ActionPerformed(evt);
            }
        });
        FormInput.add(btnAsis2);
        btnAsis2.setBounds(802, 100, 28, 23);

        nmasistoperator2.setEditable(false);
        nmasistoperator2.setName("nmasistoperator2"); // NOI18N
        nmasistoperator2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistoperator2);
        nmasistoperator2.setBounds(611, 100, 190, 23);

        kdasistoperator2.setName("kdasistoperator2"); // NOI18N
        kdasistoperator2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistoperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistoperator2KeyPressed(evt);
            }
        });
        FormInput.add(kdasistoperator2);
        kdasistoperator2.setBounds(510, 100, 100, 23);

        label18.setText("Ast. Operator 2 :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(406, 100, 102, 23);

        btnAsis3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAsis3.setMnemonic('2');
        btnAsis3.setToolTipText("Alt+2");
        btnAsis3.setName("btnAsis3"); // NOI18N
        btnAsis3.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAsis3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsis3ActionPerformed(evt);
            }
        });
        FormInput.add(btnAsis3);
        btnAsis3.setBounds(376, 340, 28, 23);

        nminstrumen.setEditable(false);
        nminstrumen.setName("nminstrumen"); // NOI18N
        nminstrumen.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nminstrumen);
        nminstrumen.setBounds(185, 340, 190, 23);

        kdInstrumen.setName("kdInstrumen"); // NOI18N
        kdInstrumen.setPreferredSize(new java.awt.Dimension(80, 23));
        kdInstrumen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdInstrumenKeyPressed(evt);
            }
        });
        FormInput.add(kdInstrumen);
        kdInstrumen.setBounds(84, 340, 100, 23);

        label23.setText("Instrumen :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(0, 340, 82, 23);

        btnPrwRes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPrwRes.setMnemonic('2');
        btnPrwRes.setToolTipText("Alt+2");
        btnPrwRes.setName("btnPrwRes"); // NOI18N
        btnPrwRes.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPrwRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrwResActionPerformed(evt);
            }
        });
        FormInput.add(btnPrwRes);
        btnPrwRes.setBounds(802, 160, 28, 23);

        nmprwresust.setEditable(false);
        nmprwresust.setName("nmprwresust"); // NOI18N
        nmprwresust.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmprwresust);
        nmprwresust.setBounds(611, 160, 190, 23);

        kdprwresust.setName("kdprwresust"); // NOI18N
        kdprwresust.setPreferredSize(new java.awt.Dimension(80, 23));
        kdprwresust.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdprwresustKeyPressed(evt);
            }
        });
        FormInput.add(kdprwresust);
        kdprwresust.setBounds(510, 160, 100, 23);

        label24.setText("Prw.Resusitasi :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(406, 160, 102, 23);

        label26.setText("Ast. Anestesi :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(406, 130, 102, 23);

        kdasistanestesi.setName("kdasistanestesi"); // NOI18N
        kdasistanestesi.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistanestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistanestesiKeyPressed(evt);
            }
        });
        FormInput.add(kdasistanestesi);
        kdasistanestesi.setBounds(510, 130, 100, 23);

        nmasistanestesi.setEditable(false);
        nmasistanestesi.setName("nmasistanestesi"); // NOI18N
        nmasistanestesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistanestesi);
        nmasistanestesi.setBounds(611, 130, 190, 23);

        BtnAsnes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAsnes.setMnemonic('2');
        BtnAsnes.setToolTipText("Alt+2");
        BtnAsnes.setName("BtnAsnes"); // NOI18N
        BtnAsnes.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAsnes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsnesActionPerformed(evt);
            }
        });
        FormInput.add(BtnAsnes);
        BtnAsnes.setBounds(802, 130, 28, 23);

        label27.setText("Bidan 1 :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(0, 220, 82, 23);

        kdbidan.setName("kdbidan"); // NOI18N
        kdbidan.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbidan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbidanKeyPressed(evt);
            }
        });
        FormInput.add(kdbidan);
        kdbidan.setBounds(84, 220, 100, 23);

        nmbidan.setEditable(false);
        nmbidan.setName("nmbidan"); // NOI18N
        nmbidan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmbidan);
        nmbidan.setBounds(185, 220, 190, 23);

        btnBidan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBidan.setMnemonic('2');
        btnBidan.setToolTipText("Alt+2");
        btnBidan.setName("btnBidan"); // NOI18N
        btnBidan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBidan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBidanActionPerformed(evt);
            }
        });
        FormInput.add(btnBidan);
        btnBidan.setBounds(376, 220, 28, 23);

        label28.setText("Prwat Luar :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(0, 310, 82, 23);

        kdprwluar.setName("kdprwluar"); // NOI18N
        kdprwluar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdprwluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdprwluarKeyPressed(evt);
            }
        });
        FormInput.add(kdprwluar);
        kdprwluar.setBounds(84, 310, 100, 23);

        nmprwluar.setEditable(false);
        nmprwluar.setName("nmprwluar"); // NOI18N
        nmprwluar.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmprwluar);
        nmprwluar.setBounds(185, 310, 190, 23);

        btnPrwLuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPrwLuar.setMnemonic('2');
        btnPrwLuar.setToolTipText("Alt+2");
        btnPrwLuar.setName("btnPrwLuar"); // NOI18N
        btnPrwLuar.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPrwLuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrwLuarActionPerformed(evt);
            }
        });
        FormInput.add(btnPrwLuar);
        btnPrwLuar.setBounds(376, 310, 28, 23);

        jLabel5.setText("Kategori :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 40, 82, 23);

        Kategori.setForeground(new java.awt.Color(153, 0, 51));
        Kategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Khusus", "Besar", "Sedang", "Kecil" }));
        Kategori.setName("Kategori"); // NOI18N
        Kategori.setOpaque(false);
        Kategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KategoriKeyPressed(evt);
            }
        });
        FormInput.add(Kategori);
        Kategori.setBounds(84, 40, 122, 23);

        btnBidan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBidan2.setMnemonic('2');
        btnBidan2.setToolTipText("Alt+2");
        btnBidan2.setName("btnBidan2"); // NOI18N
        btnBidan2.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBidan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBidan2ActionPerformed(evt);
            }
        });
        FormInput.add(btnBidan2);
        btnBidan2.setBounds(376, 250, 28, 23);

        nmbidan2.setEditable(false);
        nmbidan2.setName("nmbidan2"); // NOI18N
        nmbidan2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmbidan2);
        nmbidan2.setBounds(185, 250, 190, 23);

        kdbidan2.setName("kdbidan2"); // NOI18N
        kdbidan2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbidan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbidan2KeyPressed(evt);
            }
        });
        FormInput.add(kdbidan2);
        kdbidan2.setBounds(84, 250, 100, 23);

        label29.setText("Bidan 2 :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(0, 250, 82, 23);

        label30.setText("Bidan 3 :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(0, 280, 82, 23);

        kdbidan3.setName("kdbidan3"); // NOI18N
        kdbidan3.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbidan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbidan3KeyPressed(evt);
            }
        });
        FormInput.add(kdbidan3);
        kdbidan3.setBounds(84, 280, 100, 23);

        nmbidan3.setEditable(false);
        nmbidan3.setName("nmbidan3"); // NOI18N
        nmbidan3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmbidan3);
        nmbidan3.setBounds(185, 280, 190, 23);

        btnBidan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBidan3.setMnemonic('2');
        btnBidan3.setToolTipText("Alt+2");
        btnBidan3.setName("btnBidan3"); // NOI18N
        btnBidan3.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBidan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBidan3ActionPerformed(evt);
            }
        });
        FormInput.add(btnBidan3);
        btnBidan3.setBounds(376, 280, 28, 23);

        label25.setText("Onloop 1 :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(406, 190, 102, 23);

        kdonloop1.setName("kdonloop1"); // NOI18N
        kdonloop1.setPreferredSize(new java.awt.Dimension(80, 23));
        kdonloop1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdonloop1KeyPressed(evt);
            }
        });
        FormInput.add(kdonloop1);
        kdonloop1.setBounds(510, 190, 100, 23);

        nmonloop1.setEditable(false);
        nmonloop1.setName("nmonloop1"); // NOI18N
        nmonloop1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmonloop1);
        nmonloop1.setBounds(611, 190, 190, 23);

        btnOnloop1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnOnloop1.setMnemonic('2');
        btnOnloop1.setToolTipText("Alt+2");
        btnOnloop1.setName("btnOnloop1"); // NOI18N
        btnOnloop1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnOnloop1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnloop1ActionPerformed(evt);
            }
        });
        FormInput.add(btnOnloop1);
        btnOnloop1.setBounds(802, 190, 28, 23);

        btnOnloop2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnOnloop2.setMnemonic('2');
        btnOnloop2.setToolTipText("Alt+2");
        btnOnloop2.setName("btnOnloop2"); // NOI18N
        btnOnloop2.setPreferredSize(new java.awt.Dimension(28, 23));
        btnOnloop2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnloop2ActionPerformed(evt);
            }
        });
        FormInput.add(btnOnloop2);
        btnOnloop2.setBounds(802, 220, 28, 23);

        nmonloop2.setEditable(false);
        nmonloop2.setName("nmonloop2"); // NOI18N
        nmonloop2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmonloop2);
        nmonloop2.setBounds(611, 220, 190, 23);

        kdonloop2.setName("kdonloop2"); // NOI18N
        kdonloop2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdonloop2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdonloop2KeyPressed(evt);
            }
        });
        FormInput.add(kdonloop2);
        kdonloop2.setBounds(510, 220, 100, 23);

        label31.setText("Onloop 2 :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(406, 220, 102, 23);

        label32.setText("Onloop 3 :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(406, 250, 102, 23);

        btnOnloop3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnOnloop3.setMnemonic('2');
        btnOnloop3.setToolTipText("Alt+2");
        btnOnloop3.setName("btnOnloop3"); // NOI18N
        btnOnloop3.setPreferredSize(new java.awt.Dimension(28, 23));
        btnOnloop3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnloop3ActionPerformed(evt);
            }
        });
        FormInput.add(btnOnloop3);
        btnOnloop3.setBounds(802, 250, 28, 23);

        nmonloop3.setEditable(false);
        nmonloop3.setName("nmonloop3"); // NOI18N
        nmonloop3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmonloop3);
        nmonloop3.setBounds(611, 250, 190, 23);

        kdonloop3.setName("kdonloop3"); // NOI18N
        kdonloop3.setPreferredSize(new java.awt.Dimension(80, 23));
        kdonloop3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdonloop3KeyPressed(evt);
            }
        });
        FormInput.add(kdonloop3);
        kdonloop3.setBounds(510, 250, 100, 23);

        label33.setText("dr Pj. Anak :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(406, 280, 102, 23);

        kdpjanak.setName("kdpjanak"); // NOI18N
        kdpjanak.setPreferredSize(new java.awt.Dimension(80, 23));
        kdpjanak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpjanakKeyPressed(evt);
            }
        });
        FormInput.add(kdpjanak);
        kdpjanak.setBounds(510, 280, 100, 23);

        nmpjanak.setEditable(false);
        nmpjanak.setName("nmpjanak"); // NOI18N
        nmpjanak.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmpjanak);
        nmpjanak.setBounds(611, 280, 190, 23);

        btndrpjanak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btndrpjanak.setMnemonic('2');
        btndrpjanak.setToolTipText("Alt+2");
        btndrpjanak.setName("btndrpjanak"); // NOI18N
        btndrpjanak.setPreferredSize(new java.awt.Dimension(28, 23));
        btndrpjanak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndrpjanakActionPerformed(evt);
            }
        });
        FormInput.add(btndrpjanak);
        btndrpjanak.setBounds(802, 280, 28, 23);

        label34.setText("dr Umum :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(406, 310, 102, 23);

        kddrumum.setName("kddrumum"); // NOI18N
        kddrumum.setPreferredSize(new java.awt.Dimension(80, 23));
        kddrumum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddrumumKeyPressed(evt);
            }
        });
        FormInput.add(kddrumum);
        kddrumum.setBounds(510, 310, 100, 23);

        nmdrumum.setEditable(false);
        nmdrumum.setName("nmdrumum"); // NOI18N
        nmdrumum.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmdrumum);
        nmdrumum.setBounds(611, 310, 190, 23);

        btndrumum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btndrumum.setMnemonic('2');
        btndrumum.setToolTipText("Alt+2");
        btndrumum.setName("btndrumum"); // NOI18N
        btndrumum.setPreferredSize(new java.awt.Dimension(28, 23));
        btndrumum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndrumumActionPerformed(evt);
            }
        });
        FormInput.add(btndrumum);
        btndrumum.setBounds(802, 310, 28, 23);

        scrollPane1.setViewportView(FormInput);

        PanelInput.add(scrollPane1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariTagihanOperasi form=new DlgCariTagihanOperasi(null,false);
        //form.emptTeks();      
        form.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,TCariPaket,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void kdoperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator1,kdoperator1.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnOperator1ActionPerformed(null);
        }else{
            Valid.pindah(evt,tgl,kdoperator2);
        }
}//GEN-LAST:event_kdoperator1KeyPressed

private void BtnOperator1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator1ActionPerformed
        pilihan=1;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnOperator1ActionPerformed

private void tglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tglKeyPressed
        Valid.pindah(evt,jenis,kdoperator1);
}//GEN-LAST:event_tglKeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tabMode.getRowCount();
            for(int r=0;r<row2;r++){ 
                tabMode.setValueAt("",r,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select concat(pasien.no_rkm_medis,', ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                        " on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
        }else{            
            Valid.pindah(evt,TCari,kdoperator1);
        }
}//GEN-LAST:event_TNoRwKeyPressed

private void jenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jenisKeyPressed
    Valid.pindah(evt,Kategori,tgl);
}//GEN-LAST:event_jenisKeyPressed

private void kdasistoperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistoperator1KeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator1,kdasistoperator1.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAsis1ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdInstrumen,kdasistoperator2);
        }
          
}//GEN-LAST:event_kdasistoperator1KeyPressed

private void btnAsis1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsis1ActionPerformed
   pilihan=1;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_btnAsis1ActionPerformed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil2();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil2();
}//GEN-LAST:event_BtnAllActionPerformed

private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgObatOperasi produsen=new DlgObatOperasi(null,false);
        produsen.emptTeks();   
        produsen.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        produsen.setLocationRelativeTo(internalFrame1);
        produsen.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());         
}//GEN-LAST:event_BtnTambahActionPerformed

private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tbObat.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
        LTotal.setText("Total Biaya : "+Valid.SetAngka(biayaobat+biayatindakan));
}//GEN-LAST:event_tbObatMouseClicked

private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tbObat.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    getData();
                    int row=tbObat.getSelectedColumn();
                    if(row==1){
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                int row=tbObat.getSelectedRow();
                if(row!= -1){
                    tabMode.setValueAt("", row,0);
                }
            }            
        }
        LTotal.setText("Total Biaya : "+Valid.SetAngka(biayaobat+biayatindakan));
}//GEN-LAST:event_tbObatKeyPressed

private void TCariPaketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPaketKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari2ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariPaketKeyPressed

private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
   tampil();
}//GEN-LAST:event_BtnCari2ActionPerformed

private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnCari2KeyPressed

private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
  TCariPaket.setText("");
  tampil();
}//GEN-LAST:event_BtnAll1ActionPerformed

private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnAll1KeyPressed

private void BtnTambahOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahOperasiActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanOperasi produsen=new DlgJnsPerawatanOperasi(null,false);
        produsen.emptTeks();   
        produsen.isCek();
        produsen.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        produsen.setLocationRelativeTo(internalFrame1);
        produsen.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor()); 
}//GEN-LAST:event_BtnTambahOperasiActionPerformed

private void tbtindakanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbtindakanMouseClicked
       if(tbtindakan.getRowCount()!=0){
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
                System.out.println(e);
            }
        }
       
        LTotal.setText("Total Biaya : "+Valid.SetAngka(biayaobat+biayatindakan));
}//GEN-LAST:event_tbtindakanMouseClicked

private void tbtindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbtindakanKeyPressed
    if(tbtindakan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    int row=tbtindakan.getSelectedColumn();
                    if((row!=0)||(row!=28)){
                        if(tbtindakan.getSelectedRow()>-1){
                          tbtindakan.setValueAt(true,tbtindakan.getSelectedRow(),0);   
                        }                               
                        TCariPaket.setText("");
                        TCariPaket.requestFocus();
                    }                    
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
    }
    LTotal.setText("Total Biaya : "+Valid.SetAngka(biayaobat+biayatindakan));
}//GEN-LAST:event_tbtindakanKeyPressed

private void kdoperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator2KeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator2,kdoperator2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnOperator2ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdoperator1,kdoperator3);
        }
}//GEN-LAST:event_kdoperator2KeyPressed

private void BtnOperator2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator2ActionPerformed
      pilihan=2;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnOperator2ActionPerformed

private void kdoperator3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator3KeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator3,kdoperator3.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnInstrumenActionPerformed(null);
        }else{
            Valid.pindah(evt,kdoperator2,kdanestesi);
        }
}//GEN-LAST:event_kdoperator3KeyPressed

private void btnInstrumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInstrumenActionPerformed
  pilihan=3;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_btnInstrumenActionPerformed

private void kdanestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdanestesiKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmanestesi,kdanestesi.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnAnastesiActionPerformed(null);
        }else{
            Valid.pindah(evt,kdoperator3,kddranak);
        }
}//GEN-LAST:event_kdanestesiKeyPressed

private void BtnAnastesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAnastesiActionPerformed
        pilihan=4;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnAnastesiActionPerformed

private void kddranakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddranakKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdranak,kddranak.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAnakActionPerformed(null);
        }else{
            Valid.pindah(evt,kdanestesi,kdbidan);
        }
}//GEN-LAST:event_kddranakKeyPressed

private void btnAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnakActionPerformed
        pilihan=5;
        dokter.emptTeks();        
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_btnAnakActionPerformed

private void btnAsis2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsis2ActionPerformed
   pilihan=2;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_btnAsis2ActionPerformed

private void kdasistoperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistoperator2KeyPressed
     if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator2,kdasistoperator2.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAsis2ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdasistoperator1,kdasistanestesi);
        }
}//GEN-LAST:event_kdasistoperator2KeyPressed

private void btnAsis3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsis3ActionPerformed
  pilihan=3;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_btnAsis3ActionPerformed

private void kdInstrumenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdInstrumenKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip='"+kdInstrumen.getText()+"'",nminstrumen);            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAsis3ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdprwluar,kdasistoperator1);
        }         
}//GEN-LAST:event_kdInstrumenKeyPressed

private void btnPrwResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrwResActionPerformed
    pilihan=5;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_btnPrwResActionPerformed

private void kdprwresustKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdprwresustKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmprwresust,kdprwresust.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPrwResActionPerformed(null);
        }else{
            Valid.pindah(evt,kdasistanestesi,kdonloop1);
        }
}//GEN-LAST:event_kdprwresustKeyPressed

private void kdasistanestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistanestesiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmasistanestesi,kdasistanestesi.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnAsnesActionPerformed(null);
        }else{
            Valid.pindah(evt,kdasistoperator2,kdprwresust);
        }
}//GEN-LAST:event_kdasistanestesiKeyPressed

private void BtnAsnesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsnesActionPerformed
   pilihan=4;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnAsnesActionPerformed

private void kdbidanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbidanKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan,kdbidan.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBidanActionPerformed(null);
        }else{
            Valid.pindah(evt,kddranak,kdbidan2);
        }
}//GEN-LAST:event_kdbidanKeyPressed

private void btnBidanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBidanActionPerformed
    pilihan=7;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_btnBidanActionPerformed

private void kdprwluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdprwluarKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmprwluar,kdprwluar.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPrwLuarActionPerformed(null);
        }else{
            Valid.pindah(evt,kdbidan3,kdInstrumen);
        }
}//GEN-LAST:event_kdprwluarKeyPressed

private void btnPrwLuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrwLuarActionPerformed
   pilihan=6;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_btnPrwLuarActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
   isForm();  
}//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        tampil2();
    }//GEN-LAST:event_formWindowOpened

    private void KategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KategoriKeyPressed
        Valid.pindah(evt,TCariPaket,jenis);
    }//GEN-LAST:event_KategoriKeyPressed

    private void btnBidan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBidan2ActionPerformed
        pilihan=8;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnBidan2ActionPerformed

    private void kdbidan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbidan2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan2,kdbidan2.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBidan2ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdbidan,kdbidan3);
        }
    }//GEN-LAST:event_kdbidan2KeyPressed

    private void kdbidan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbidan3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan3,kdbidan3.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBidan3ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdbidan2,kdprwluar);
        }
    }//GEN-LAST:event_kdbidan3KeyPressed

    private void btnBidan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBidan3ActionPerformed
        pilihan=9;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnBidan3ActionPerformed

    private void kdonloop1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdonloop1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop1,kdonloop1.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnOnloop1ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdprwresust,kdonloop2);
        }
    }//GEN-LAST:event_kdonloop1KeyPressed

    private void btnOnloop1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnloop1ActionPerformed
        pilihan=10;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnOnloop1ActionPerformed

    private void btnOnloop2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnloop2ActionPerformed
        pilihan=11;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnOnloop2ActionPerformed

    private void kdonloop2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdonloop2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop2,kdonloop2.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnOnloop1ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdonloop1,kdonloop3);
        }
    }//GEN-LAST:event_kdonloop2KeyPressed

    private void btnOnloop3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnloop3ActionPerformed
        pilihan=12;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnOnloop3ActionPerformed

    private void kdonloop3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdonloop3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop3,kdonloop3.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnOnloop1ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdonloop2,kdpjanak);
        }
    }//GEN-LAST:event_kdonloop3KeyPressed

    private void kdpjanakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpjanakKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmpjanak,kdpjanak.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btndrpjanakActionPerformed(null);
        }else{
            Valid.pindah(evt,kdonloop3,kddrumum);
        }
    }//GEN-LAST:event_kdpjanakKeyPressed

    private void btndrpjanakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndrpjanakActionPerformed
        pilihan=6;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btndrpjanakActionPerformed

    private void kddrumumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddrumumKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdranak,kddranak.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btndrumumActionPerformed(null);
        }else{
            Valid.pindah(evt,kdpjanak,BtnSimpan);
        }
    }//GEN-LAST:event_kddrumumKeyPressed

    private void btndrumumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndrumumActionPerformed
        pilihan=7;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btndrumumActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,kddrumum,BtnKeluar);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(kdoperator2.getText().trim().equals("")||nmoperator2.getText().trim().equals("")){
            kdoperator2.setText("-");
            nmoperator2.setText("-");
        }

        if(kdoperator3.getText().trim().equals("")||nmoperator3.getText().trim().equals("")){
            kdoperator3.setText("-");
            nmoperator3.setText("-");
        }

        if(kdanestesi.getText().trim().equals("")||nmanestesi.getText().trim().equals("")){
            kdanestesi.setText("-");
            nmanestesi.setText("-");
        }

        if(kddranak.getText().trim().equals("")||nmdranak.getText().trim().equals("")){
            kddranak.setText("-");
            nmdranak.setText("-");
        }

        if(kdbidan.getText().trim().equals("")||nmbidan.getText().trim().equals("")){
            kdbidan.setText("-");
            nmbidan.setText("-");
        }

        if(kdbidan2.getText().trim().equals("")||nmbidan2.getText().trim().equals("")){
            kdbidan2.setText("-");
            nmbidan2.setText("-");
        }

        if(kdbidan3.getText().trim().equals("")||nmbidan3.getText().trim().equals("")){
            kdbidan3.setText("-");
            nmbidan3.setText("-");
        }

        if(kdonloop1.getText().trim().equals("")||nmonloop1.getText().trim().equals("")){
            kdonloop1.setText("-");
            nmonloop1.setText("-");
        }

        if(kdonloop2.getText().trim().equals("")||nmonloop2.getText().trim().equals("")){
            kdonloop2.setText("-");
            nmonloop2.setText("-");
        }

        if(kdonloop3.getText().trim().equals("")||nmonloop3.getText().trim().equals("")){
            kdonloop3.setText("-");
            nmonloop3.setText("-");
        }

        if(kdasistoperator1.getText().trim().equals("")||nmasistoperator1.getText().trim().equals("")){
            kdasistoperator1.setText("-");
            nmasistoperator1.setText("-");
        }

        if(kdasistoperator2.getText().trim().equals("")||nmasistoperator2.getText().trim().equals("")){
            kdasistoperator2.setText("-");
            nmasistoperator2.setText("-");
        }

        if(kdInstrumen.getText().trim().equals("")||nminstrumen.getText().trim().equals("")){
            kdInstrumen.setText("-");
            nminstrumen.setText("-");
        }

        if(kdasistanestesi.getText().trim().equals("")||nmasistanestesi.getText().trim().equals("")){
            kdasistanestesi.setText("-");
            nmasistanestesi.setText("-");
        }

        if(kdprwresust.getText().trim().equals("")||nmprwresust.getText().trim().equals("")){
            kdprwresust.setText("-");
            nmprwresust.setText("-");
        }

        if(kdprwluar.getText().trim().equals("")||nmprwluar.getText().trim().equals("")){
            kdprwluar.setText("-");
            nmprwluar.setText("-");
        }
        
        if(kdpjanak.getText().trim().equals("")||nmpjanak.getText().trim().equals("")){
            kdpjanak.setText("-");
            nmpjanak.setText("-");
        }
        
        if(kddrumum.getText().trim().equals("")||nmdrumum.getText().trim().equals("")){
            kddrumum.setText("-");
            nmdrumum.setText("-");
        }

        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(jenis.getText().trim().equals("")){
            Valid.textKosong(jenis,"Jenis");
        }else if(kdoperator1.getText().trim().equals("")||nmoperator1.getText().trim().equals("")){
            Valid.textKosong(kdoperator1,"Operator 1");
        }else if(kdoperator2.getText().trim().equals("")||nmoperator2.getText().trim().equals("")){
            Valid.textKosong(kdoperator2,"Operator 2");
        }else if(kdoperator3.getText().trim().equals("")||nmoperator3.getText().trim().equals("")){
            Valid.textKosong(kdoperator3,"Operator 3");
        }else if(kdanestesi.getText().trim().equals("")||nmanestesi.getText().trim().equals("")){
            Valid.textKosong(kdanestesi,"dr Anestesi");
        }else if(kddranak.getText().trim().equals("")||nmdranak.getText().trim().equals("")){
            Valid.textKosong(kddranak,"dr Anak");
        }else if(kdbidan.getText().trim().equals("")||nmbidan.getText().trim().equals("")){
            Valid.textKosong(kdbidan,"Bidan 1");
        }else if(kdbidan2.getText().trim().equals("")||nmbidan2.getText().trim().equals("")){
            Valid.textKosong(kdbidan,"Bidan 2");
        }else if(kdbidan3.getText().trim().equals("")||nmbidan3.getText().trim().equals("")){
            Valid.textKosong(kdbidan,"Bidan 3");
        }else if(kdonloop1.getText().trim().equals("")||nmonloop1.getText().trim().equals("")){
            Valid.textKosong(kdonloop1,"Onloop 1");
        }else if(kdonloop2.getText().trim().equals("")||nmonloop2.getText().trim().equals("")){
            Valid.textKosong(kdonloop2,"Onloop 2");
        }else if(kdonloop3.getText().trim().equals("")||nmonloop3.getText().trim().equals("")){
            Valid.textKosong(kdonloop3,"Onloop 3");
        }else if(kdasistoperator1.getText().trim().equals("")||nmasistoperator1.getText().trim().equals("")){
            Valid.textKosong(kdasistoperator1,"Asisten Operator 1");
        }else if(kdasistoperator2.getText().trim().equals("")||nmasistoperator2.getText().trim().equals("")){
            Valid.textKosong(kdasistoperator2,"Asisten Operator 2");
        }else if(kdInstrumen.getText().trim().equals("")||nminstrumen.getText().trim().equals("")){
            Valid.textKosong(kdInstrumen,"Asisten Operator 3");
        }else if(kdasistanestesi.getText().trim().equals("")||nmasistanestesi.getText().trim().equals("")){
            Valid.textKosong(kdasistanestesi,"Asisten Anastesi");
        }else if(kdprwresust.getText().trim().equals("")||nmprwresust.getText().trim().equals("")){
            Valid.textKosong(kdprwresust,"Perawat Resusitas");
        }else if(kdprwluar.getText().trim().equals("")||nmprwluar.getText().trim().equals("")){
            Valid.textKosong(kdprwluar,"Perawat Luar");
        }else if(kdpjanak.getText().trim().equals("")||nmpjanak.getText().trim().equals("")){
            Valid.textKosong(kdpjanak,"dr Pj Anak");
        }else if(kddrumum.getText().trim().equals("")||nmdrumum.getText().trim().equals("")){
            Valid.textKosong(kddrumum,"dr Umum");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            for(i=0;i<tbtindakan.getRowCount();i++){
                if(tabMode.getValueAt(i,0).toString().equals("true")){
                    Sequel.menyimpan("operasi","'"+TNoRw.getText()+"','"+Valid.SetTgl(tgl.getSelectedItem()+"")+" "+tgl.getSelectedItem().toString().substring(11,19)
                        +"','"+jenis.getText()+"','"+Kategori.getSelectedItem()+"','"+kdoperator1.getText()+"','"+kdoperator2.getText()+"','"+kdoperator3.getText()
                        +"','"+kdasistoperator1.getText()+"','"+kdasistoperator2.getText()+"','"+kdInstrumen.getText()
                        +"','"+kddranak.getText()+"','"+kdprwresust.getText()+"','"+kdanestesi.getText()+"','"+kdasistanestesi.getText()
                        +"','"+kdbidan.getText()+"','"+kdbidan2.getText()+"','"+kdbidan3.getText()+"','"+kdprwluar.getText()
                        +"','"+kdonloop1.getText()+"','"+kdonloop2.getText()+"','"+kdonloop3.getText()
                        +"','"+kdpjanak.getText()+"','"+kddrumum.getText()
                        +"','"+tbtindakan.getValueAt(i,1).toString()
                        +"','"+tbtindakan.getValueAt(i,4).toString()
                        +"','"+tbtindakan.getValueAt(i,5).toString()
                        +"','"+tbtindakan.getValueAt(i,6).toString()
                        +"','"+tbtindakan.getValueAt(i,7).toString()
                        +"','"+tbtindakan.getValueAt(i,8).toString()
                        +"','"+tbtindakan.getValueAt(i,9).toString()
                        +"','"+tbtindakan.getValueAt(i,10).toString()
                        +"','"+tbtindakan.getValueAt(i,11).toString()
                        +"','"+tbtindakan.getValueAt(i,12).toString()
                        +"','"+tbtindakan.getValueAt(i,13).toString()
                        +"','"+tbtindakan.getValueAt(i,14).toString()
                        +"','"+tbtindakan.getValueAt(i,15).toString()
                        +"','"+tbtindakan.getValueAt(i,16).toString()
                        +"','"+tbtindakan.getValueAt(i,17).toString()
                        +"','"+tbtindakan.getValueAt(i,18).toString()
                        +"','"+tbtindakan.getValueAt(i,19).toString()
                        +"','"+tbtindakan.getValueAt(i,20).toString()
                        +"','"+tbtindakan.getValueAt(i,21).toString()
                        +"','"+tbtindakan.getValueAt(i,22).toString()
                        +"','"+tbtindakan.getValueAt(i,23).toString()
                        +"','"+tbtindakan.getValueAt(i,24).toString()
                        +"','"+tbtindakan.getValueAt(i,25).toString()
                        +"','"+tbtindakan.getValueAt(i,26).toString()
                        +"','"+tbtindakan.getValueAt(i,27).toString()+"'","data");
                }
            }
            for(int r=0;r<tbObat.getRowCount();r++){
                if(Valid.SetAngka(tbObat.getValueAt(r,0).toString())>0){
                    Sequel.menyimpan("beri_obat_operasi","'"+TNoRw.getText()+"','"+Valid.SetTgl(tgl.getSelectedItem()+"")+" "+tgl.getSelectedItem().toString().substring(11,19)+
                        "','"+tbObat.getValueAt(r,1).toString()+"','"+tbObat.getValueAt(r,4).toString()+
                        "','"+tbObat.getValueAt(r,0).toString()+"'","data");
                }
            }
            for(int r=0;r<tbtindakan.getRowCount();r++){
                tbtindakan.setValueAt(false,r,0);
            }
            tampil();
            for(int r=0;r<tbObat.getRowCount();r++){
                tbObat.setValueAt("",r,0);
            }
            tampil2();
            LTotal.setText("Total Biaya : 0");
            JOptionPane.showMessageDialog(rootPane,"Proses simpan selesai...!");
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgTagihanOperasi dialog = new DlgTagihanOperasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll1;
    private widget.Button BtnAnastesi;
    private widget.Button BtnAsnes;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnCari2;
    private widget.Button BtnKeluar;
    private widget.Button BtnOperator1;
    private widget.Button BtnOperator2;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambahOperasi;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.ComboBox Kategori;
    private widget.TextBox Kd2;
    private widget.Label LTotal;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TCariPaket;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Button btnAnak;
    private widget.Button btnAsis1;
    private widget.Button btnAsis2;
    private widget.Button btnAsis3;
    private widget.Button btnBidan;
    private widget.Button btnBidan2;
    private widget.Button btnBidan3;
    private widget.Button btnInstrumen;
    private widget.Button btnOnloop1;
    private widget.Button btnOnloop2;
    private widget.Button btnOnloop3;
    private widget.Button btnPrwLuar;
    private widget.Button btnPrwRes;
    private widget.Button btndrpjanak;
    private widget.Button btndrumum;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox jenis;
    private widget.TextBox kdInstrumen;
    private widget.TextBox kdanestesi;
    private widget.TextBox kdasistanestesi;
    private widget.TextBox kdasistoperator1;
    private widget.TextBox kdasistoperator2;
    private widget.TextBox kdbidan;
    private widget.TextBox kdbidan2;
    private widget.TextBox kdbidan3;
    private widget.TextBox kddranak;
    private widget.TextBox kddrumum;
    private widget.TextBox kdonloop1;
    private widget.TextBox kdonloop2;
    private widget.TextBox kdonloop3;
    private widget.TextBox kdoperator1;
    private widget.TextBox kdoperator2;
    private widget.TextBox kdoperator3;
    private widget.TextBox kdpjanak;
    private widget.TextBox kdprwluar;
    private widget.TextBox kdprwresust;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label14;
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
    private widget.Label label34;
    private widget.Label label9;
    private widget.TextBox nmanestesi;
    private widget.TextBox nmasistanestesi;
    private widget.TextBox nmasistoperator1;
    private widget.TextBox nmasistoperator2;
    private widget.TextBox nmbidan;
    private widget.TextBox nmbidan2;
    private widget.TextBox nmbidan3;
    private widget.TextBox nmdranak;
    private widget.TextBox nmdrumum;
    private widget.TextBox nminstrumen;
    private widget.TextBox nmonloop1;
    private widget.TextBox nmonloop2;
    private widget.TextBox nmonloop3;
    private widget.TextBox nmoperator1;
    private widget.TextBox nmoperator2;
    private widget.TextBox nmoperator3;
    private widget.TextBox nmpjanak;
    private widget.TextBox nmprwluar;
    private widget.TextBox nmprwresust;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbObat;
    private widget.Table tbtindakan;
    private widget.Tanggal tgl;
    // End of variables declaration//GEN-END:variables

    private void tampil() {  
        jml=0;
        for(i=0;i<tabMode.getRowCount();i++){
            if(tabMode.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        
        pilih=null;
        pilih=new boolean[jml]; 
        kode_paket=null;
        kode_paket=new String[jml];
        kategori=null;
        kategori=new String[jml];
        nm_perawatan=null;
        nm_perawatan=new String[jml];
        operator1=null;
        operator1=new double[jml];
        operator2=null;
        operator2=new double[jml];
        operator3=null;
        operator3=new double[jml];
        asisten_operator1=null;
        asisten_operator1=new double[jml];
        asisten_operator2=null;
        asisten_operator2=new double[jml];
        instrumen=null;
        instrumen=new double[jml];
        dokter_anak=null;
        dokter_anak=new double[jml];
        perawaat_resusitas=null;
        perawaat_resusitas=new double[jml];
        dokter_anestesi=null;
        dokter_anestesi=new double[jml];
        asisten_anestesi=null;
        asisten_anestesi=new double[jml];
        bidan=null;
        bidan=new double[jml];
        bidan2=null;
        bidan2=new double[jml];
        bidan3=null;
        bidan3=new double[jml];
        bidan=new double[jml];
        perawat_luar=null;
        perawat_luar=new double[jml];   
        sewa_ok=null;
        sewa_ok=new double[jml];
        akomodasi=null;
        akomodasi=new double[jml];
        bagian_rs=null;
        bagian_rs=new double[jml];
        omloop=null;
        omloop=new double[jml];
        omloop2=null;
        omloop2=new double[jml];
        omloop3=null;
        omloop3=new double[jml];
        sarpras=null;
        sarpras=new double[jml];
        alat=null;
        alat=new double[jml];   
        ttltindakan=null;
        ttltindakan=new double[jml];        
        index=0;        
        for(i=0;i<tabMode.getRowCount();i++){
            if(tabMode.getValueAt(i,0).toString().equals("true")){
                pilih[index]=true;
                kode_paket[index]=tabMode.getValueAt(i,1).toString();
                nm_perawatan[index]=tabMode.getValueAt(i,2).toString();
                kategori[index]=tabMode.getValueAt(i,3).toString();
                operator1[index]=Double.parseDouble(tabMode.getValueAt(i,4).toString());
                operator2[index]=Double.parseDouble(tabMode.getValueAt(i,5).toString());
                operator3[index]=Double.parseDouble(tabMode.getValueAt(i,6).toString());
                asisten_operator1[index]=Double.parseDouble(tabMode.getValueAt(i,7).toString());
                asisten_operator2[index]=Double.parseDouble(tabMode.getValueAt(i,8).toString());
                instrumen[index]=Double.parseDouble(tabMode.getValueAt(i,9).toString());
                dokter_anak[index]=Double.parseDouble(tabMode.getValueAt(i,10).toString());
                perawaat_resusitas[index]=Double.parseDouble(tabMode.getValueAt(i,11).toString());
                dokter_anestesi[index]=Double.parseDouble(tabMode.getValueAt(i,12).toString());
                asisten_anestesi[index]=Double.parseDouble(tabMode.getValueAt(i,13).toString());
                bidan[index]=Double.parseDouble(tabMode.getValueAt(i,14).toString());
                bidan2[index]=Double.parseDouble(tabMode.getValueAt(i,15).toString());
                bidan3[index]=Double.parseDouble(tabMode.getValueAt(i,16).toString());
                perawat_luar[index]=Double.parseDouble(tabMode.getValueAt(i,17).toString());
                alat[index]=Double.parseDouble(tabMode.getValueAt(i,18).toString());   
                sewa_ok[index]=Double.parseDouble(tabMode.getValueAt(i,19).toString());
                akomodasi[index]=Double.parseDouble(tabMode.getValueAt(i,20).toString());  
                bagian_rs[index]=Double.parseDouble(tabMode.getValueAt(i,21).toString());  
                omloop[index]=Double.parseDouble(tabMode.getValueAt(i,22).toString()); 
                omloop2[index]=Double.parseDouble(tabMode.getValueAt(i,23).toString()); 
                omloop3[index]=Double.parseDouble(tabMode.getValueAt(i,24).toString());   
                sarpras[index]=Double.parseDouble(tabMode.getValueAt(i,25).toString()); 
                ttltindakan[index]=Double.parseDouble(tabMode.getValueAt(i,26).toString());                
                index++;
            }
        }
        
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{pilih[i],kode_paket[i],nm_perawatan[i],kategori[i],operator1[i],
                operator2[i],operator3[i],asisten_operator1[i],asisten_operator2[i],
                instrumen[i],dokter_anak[i],perawaat_resusitas[i],dokter_anestesi[i],
                asisten_anestesi[i],bidan[i],bidan2[i],bidan3[i],perawat_luar[i],
                alat[i],sewa_ok[i],akomodasi[i],bagian_rs[i],omloop[i],omloop2[i],
                omloop3[i],sarpras[i],ttltindakan[i]
            });
        }
        
        try{
            pstindakan=koneksi.prepareStatement("select kode_paket, nm_perawatan,kategori, operator1, operator2, operator3, "+
                   "asisten_operator1, asisten_operator2, instrumen, dokter_anak,perawaat_resusitas,"+
                   "dokter_anestesi, asisten_anestesi, bidan, bidan2, bidan3, perawat_luar, alat,"+
                   "sewa_ok,akomodasi,bagian_rs,omloop,omloop2,omloop3,sarpras,dokter_pjanak,dokter_umum,(operator1+operator2+operator3+"+
                   "asisten_operator1+asisten_operator2+instrumen+dokter_anak+perawaat_resusitas+"+
                   "alat+dokter_anestesi+asisten_anestesi+bidan+bidan2+bidan3+perawat_luar+sewa_ok+"+
                   "akomodasi+bagian_rs+omloop+omloop2+omloop3+sarpras+dokter_pjanak+dokter_umum) as jumlah "+
                   "from paket_operasi "+
                   "where status='1' and (kd_pj=? or kd_pj='-') and kode_paket like ? or "+
                   "status='1' and (kd_pj=? or kd_pj='-') and nm_perawatan like ? order by nm_perawatan ");
            pstindakan2=koneksi.prepareStatement("select kode_paket, nm_perawatan,kategori, operator1, operator2, operator3, "+
                   "asisten_operator1, asisten_operator2, instrumen, dokter_anak,perawaat_resusitas,"+
                   "dokter_anestesi, asisten_anestesi, bidan, bidan2, bidan3, perawat_luar, alat,"+
                   "sewa_ok,akomodasi,bagian_rs,omloop,omloop2,omloop3,sarpras,dokter_pjanak,dokter_umum,(operator1+operator2+operator3+"+
                   "asisten_operator1+asisten_operator2+instrumen+dokter_anak+perawaat_resusitas+"+
                   "alat+dokter_anestesi+asisten_anestesi+bidan+bidan2+bidan3+perawat_luar+sewa_ok+"+
                   "akomodasi+bagian_rs+omloop+omloop2+omloop3+sarpras+dokter_pjanak+dokter_umum) as jumlah "+
                   "from paket_operasi "+
                   "where status='1' and kode_paket like ? or "+
                   "status='1' and nm_perawatan like ? order by nm_perawatan ");
            try {
                if(cara_bayar_operasi.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,"%"+TCariPaket.getText()+"%");
                    pstindakan.setString(3,kd_pj.trim());
                    pstindakan.setString(4,"%"+TCariPaket.getText()+"%");
                    rs=pstindakan.executeQuery();
                }else if(cara_bayar_operasi.equals("No")){
                    pstindakan2.setString(1,"%"+TCariPaket.getText()+"%");
                    pstindakan2.setString(2,"%"+TCariPaket.getText()+"%");
                    rs=pstindakan2.executeQuery();
                }
                
                while(rs.next()){
                    tabMode.addRow(new Object[]{false,rs.getString("kode_paket"),
                                   rs.getString("nm_perawatan"),
                                   rs.getString("kategori"), 
                                   rs.getDouble("operator1"), 
                                   rs.getDouble("operator2"), 
                                   rs.getDouble("operator3"), 
                                   rs.getDouble("asisten_operator1"), 
                                   rs.getDouble("asisten_operator2"), 
                                   rs.getDouble("instrumen"), 
                                   rs.getDouble("dokter_anak"), 
                                   rs.getDouble("perawaat_resusitas"), 
                                   rs.getDouble("dokter_anestesi"), 
                                   rs.getDouble("asisten_anestesi"), 
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
                                   rs.getDouble("sarpras"), 
                                   rs.getDouble("dokter_pjanak"), 
                                   rs.getDouble("dokter_umum"), 
                                   rs.getDouble("jumlah")});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pstindakan!=null){
                    pstindakan.close();
                }
                if(pstindakan2!=null){
                    pstindakan2.close();
                }
            }                  
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    //obat
    
    private void tampil2() {
        jml=0;
        for(i=0;i<tbObat.getRowCount();i++){
            //System.out.println(tbObat.getValueAt(i,0).toString());
            if(!tbObat.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        
        jmlobat=new double[jml];
        kd_obat=new String[jml];
        nm_obat=new String[jml];
        satuan=new String[jml];
        hargasatuan=new double[jml];
        ttlobat=new double[jml];
        
        index=0;        
        for(i=0;i<tabMode2.getRowCount();i++){
            if(!tabMode2.getValueAt(i,0).toString().equals("")){
                jmlobat[index]=Double.parseDouble(tabMode2.getValueAt(i,0).toString());
                kd_obat[index]=tabMode2.getValueAt(i,1).toString();
                nm_obat[index]=tabMode2.getValueAt(i,2).toString();
                satuan[index]=tabMode2.getValueAt(i,3).toString();
                hargasatuan[index]=Double.parseDouble(tabMode2.getValueAt(i,4).toString());
                ttlobat[index]=Double.parseDouble(tabMode2.getValueAt(i,5).toString());
                index++;
            }
        }
        
        Valid.tabelKosong(tabMode2);
        
        for(i=0;i<jml;i++){
            tabMode2.addRow(new Object[]{jmlobat[i],kd_obat[i],nm_obat[i],satuan[i],hargasatuan[i],ttlobat[i]});
        }
        
        try{    
            psobat=koneksi.prepareStatement("select obatbhp_ok.kd_obat, obatbhp_ok.nm_obat, kodesatuan.satuan, "+
                       "obatbhp_ok.hargasatuan from obatbhp_ok inner join kodesatuan "+
                       "on obatbhp_ok.kode_sat=kodesatuan.kode_sat "+
                       "where obatbhp_ok.kd_obat like ? or "+
                       "obatbhp_ok.nm_obat like ? or "+
                       "kodesatuan.satuan like ? "+
                       "order by obatbhp_ok.kd_obat");
            try{
                psobat.setString(1,"%"+TCari.getText()+"%");
                psobat.setString(2,"%"+TCari.getText()+"%");
                psobat.setString(3,"%"+TCari.getText()+"%");
                rs=psobat.executeQuery();
                while(rs.next()){
                    tabMode2.addRow(new Object[]{"",rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),0});
                }
            }catch(SQLException e){
                System.out.println(e);
            }finally{
                if(rs!=null){
                    rs.close();
                }
                if(psobat!=null){
                    psobat.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }

    
       
    private void getData(){
       int row=tbObat.getSelectedRow();
        if(row!= -1){         
           int kolom=tbObat.getSelectedColumn();  
           if((kolom==0)||(kolom==1)){    
               if(!tbObat.getValueAt(row,0).toString().equals("")){
                   try {
                       tbObat.setValueAt(Valid.SetAngka2(Double.parseDouble(tbObat.getValueAt(row,0).toString())*Double.parseDouble(tbObat.getValueAt(row,4).toString())), row,5);                    
                   } catch (Exception e) {
                       tbObat.setValueAt(0, row,5);                    
                   }
                }else if(tbObat.getValueAt(row,0).toString().equals("")){
                    tbObat.setValueAt(0, row,5);   
                }                 
            }              
            
            biayaobat=0;
            y=0;
            int row2=tbObat.getRowCount();
            for(int r=0;r<row2;r++){ 
                if(!tbObat.getValueAt(r,5).toString().isEmpty()){
                    try {
                        y=Double.parseDouble(tbObat.getValueAt(r,5).toString()); 
                    } catch (Exception e) {
                        y=0;
                    }                                   
                }else if(tbObat.getValueAt(r,5).toString().isEmpty()){
                    y=0;                
                }
                biayaobat=biayaobat+y;
            }
        }
    }
    
    private void getData2(){
       int row=tbtindakan.getSelectedRow();
        if(row!= -1){         
            if(tbtindakan.getValueAt(tbtindakan.getSelectedRow(),0).toString().equals("true")){
                try {
                    tbtindakan.setValueAt(Double.parseDouble(tbtindakan.getValueAt(row,4).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,5).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,6).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,7).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,8).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,9).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,10).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,11).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,12).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,13).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,14).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,15).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,16).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,17).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,18).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,19).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,20).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,21).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,22).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,23).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,24).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,25).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,26).toString())+
                                     Double.parseDouble(tbtindakan.getValueAt(row,27).toString()), row,28);
                } catch (Exception e) {
                    tbtindakan.setValueAt(0, row,28);
                }                    
            }
           
            biayatindakan=0;
            y=0;
            int row2=tbtindakan.getRowCount();
            for(int r=0;r<row2;r++){ 
                switch (tbtindakan.getValueAt(r,0).toString()) {
                    case "true":
                        try {
                            y=Double.parseDouble(tbtindakan.getValueAt(r,28).toString());
                        } catch (Exception e) {
                            y=0;
                        }                        
                        break;                
                    case "false":
                        y=0;
                        break;
                }
                biayatindakan=biayatindakan+y;
            }            
        }
    }
    

    
    public void isCek(){
       // Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_jual,6),signed)),0) from penjualan ","PJ",6,NoNota); 
        TCari.requestFocus();
        if(var.getjml2()>=1){
            BtnSimpan.setEnabled(var.getoperasi());
            BtnTambahOperasi.setEnabled(var.gettarif_operasi());
            BtnTambah.setEnabled(var.getoperasi());
        }        
    }
    
    public void setNoRm(String norm,String nama){
        TNoRw.setText(norm);
        TPasien.setText(nama);
        this.kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norm);
        try {
            psset_tarif=koneksi.prepareStatement("select * from set_tarif");
            try {
                rsset_tarif=psset_tarif.executeQuery();
                if(rsset_tarif.next()){
                    cara_bayar_operasi=rsset_tarif.getString("cara_bayar_operasi");
                }else{
                    cara_bayar_operasi="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsset_tarif != null){
                    rsset_tarif.close();
                }
                if(psset_tarif != null){
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        } 
    }
    
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,244));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
 
}

package tranfusidarah;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgKabupaten;
import simrskhanza.DlgKecamatan;
import simrskhanza.DlgKelurahan;
import simrskhanza.DlgPropinsi;

public class UTDPendonor extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i;
    private String kdkel="",kdkec="",kdkab="",kdprop="",sql="";
    private DlgKabupaten kab=new DlgKabupaten(null,false);
    private DlgKecamatan kec=new DlgKecamatan(null,false);
    private DlgKelurahan kel=new DlgKelurahan(null,false);
    private DlgPropinsi prop=new DlgPropinsi(null,false);

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public UTDPendonor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.ID","Nama Pendonor","No.KTP","J.K.","Tempat Lahir","Tgl.Lahir","Alamat","Kd.Kel","Kelurahan",
                "Kd.Kec","Kecamatan","Kd.Kab","Kabupaten","Kd.Prop","Propinsi","G.D.","Resus","No.Telp"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(175);
            }else if(i==2){
                column.setPreferredWidth(105);
            }else if(i==3){
                column.setPreferredWidth(25);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(175);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(40);
            }else if(i==16){
                column.setPreferredWidth(40);
            }else if(i==17){
                column.setPreferredWidth(90);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoId.setDocument(new batasInput((byte)15).getKata(NoId));
        Nama.setDocument(new batasInput((byte)40).getKata(Nama));
        KTP.setDocument(new batasInput((byte)20).getKata(KTP));
        Lahir.setDocument(new batasInput((byte)15).getKata(Lahir));
        Alamat.setDocument(new batasInput((int)100).getKata(Alamat));
        Telp.setDocument(new batasInput((byte)40).getKata(Telp));
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
        
        prop.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(prop.getTable().getSelectedRow()!= -1){
                    Propinsi.setText(prop.getTable().getValueAt(prop.getTable().getSelectedRow(),0).toString());
                    kdprop=prop.getTable().getValueAt(prop.getTable().getSelectedRow(),1).toString();
                    BtnPropinsi.requestFocus();
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
        
        kab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kab.getTable().getSelectedRow()!= -1){
                    Kabupaten.setText(kab.getTable().getValueAt(kab.getTable().getSelectedRow(),0).toString());
                    kdkab=kab.getTable().getValueAt(kab.getTable().getSelectedRow(),1).toString();
                    BtnKabupaten.requestFocus();
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
        
        kec.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kec.getTable().getSelectedRow()!= -1){
                    Kecamatan.setText(kec.getTable().getValueAt(kec.getTable().getSelectedRow(),0).toString());
                    kdkec=kec.getTable().getValueAt(kec.getTable().getSelectedRow(),1).toString();
                    BtnKecamatan.requestFocus();
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
        
        kel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kel.getTable().getSelectedRow()!= -1){
                    Kelurahan.setText(kel.getTable().getValueAt(kel.getTable().getSelectedRow(),0).toString());
                    kdkel=kel.getTable().getValueAt(kel.getTable().getSelectedRow(),1).toString();
                    BtnKeluar.requestFocus();
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
        
        ChkInput.setSelected(false);
        isForm();           
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
        MnKartu1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi2 = new widget.panelisi();
        jLabel11 = new widget.Label();
        GDCari = new widget.ComboBox();
        jLabel12 = new widget.Label();
        ResusCari = new widget.ComboBox();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel6 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        NoId = new widget.TextBox();
        Nama = new widget.TextBox();
        label18 = new widget.Label();
        label26 = new widget.Label();
        Telp = new widget.TextBox();
        label31 = new widget.Label();
        KTP = new widget.TextBox();
        jLabel8 = new widget.Label();
        JK = new widget.ComboBox();
        jLabel9 = new widget.Label();
        GD = new widget.ComboBox();
        DTPLahir = new widget.Tanggal();
        jLabel13 = new widget.Label();
        Lahir = new widget.TextBox();
        jLabel10 = new widget.Label();
        Resus = new widget.ComboBox();
        Alamat = new widget.TextBox();
        BtnKelurahan = new widget.Button();
        Kelurahan = new widget.TextBox();
        Kecamatan = new widget.TextBox();
        BtnKecamatan = new widget.Button();
        Kabupaten = new widget.TextBox();
        BtnKabupaten = new widget.Button();
        jLabel21 = new widget.Label();
        Propinsi = new widget.TextBox();
        BtnPropinsi = new widget.Button();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKartu1.setBackground(new java.awt.Color(255, 255, 254));
        MnKartu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu1.setForeground(new java.awt.Color(50, 50, 50));
        MnKartu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu1.setText("Kartu Donor Darah");
        MnKartu1.setName("MnKartu1"); // NOI18N
        MnKartu1.setPreferredSize(new java.awt.Dimension(150, 26));
        MnKartu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartu1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKartu1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pendonor ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel11.setText("G.D. :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi2.add(jLabel11);

        GDCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "A", "B", "AB", "O" }));
        GDCari.setName("GDCari"); // NOI18N
        GDCari.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi2.add(GDCari);

        jLabel12.setText("Resus :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi2.add(jLabel12);

        ResusCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "(+)", "(-)" }));
        ResusCari.setName("ResusCari"); // NOI18N
        ResusCari.setPreferredSize(new java.awt.Dimension(85, 23));
        ResusCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResusCariKeyPressed(evt);
            }
        });
        panelisi2.add(ResusCari);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi2.add(BtnCari);

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
        panelisi2.add(BtnAll);

        jLabel6.setText("Limit Data :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(jLabel6);

        cmbHlm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100", "200", "300", "400", "500", "1000", "Semua" }));
        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi2.add(cmbHlm);

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
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
        panelisi1.add(BtnBatal);

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
        panelisi1.add(BtnEdit);

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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi1.add(LCount);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

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
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 155));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(660, 107));
        FormInput.setLayout(null);

        label12.setText("No. ID :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label12);
        label12.setBounds(0, 10, 60, 23);

        NoId.setName("NoId"); // NOI18N
        NoId.setPreferredSize(new java.awt.Dimension(207, 23));
        NoId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoIdKeyPressed(evt);
            }
        });
        FormInput.add(NoId);
        NoId.setBounds(64, 10, 140, 23);

        Nama.setName("Nama"); // NOI18N
        Nama.setPreferredSize(new java.awt.Dimension(207, 23));
        Nama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaKeyPressed(evt);
            }
        });
        FormInput.add(Nama);
        Nama.setBounds(64, 40, 310, 23);

        label18.setText("Nama :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label18);
        label18.setBounds(0, 40, 60, 23);

        label26.setText("No.Telp :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label26);
        label26.setBounds(205, 10, 65, 23);

        Telp.setName("Telp"); // NOI18N
        Telp.setPreferredSize(new java.awt.Dimension(207, 23));
        Telp.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TelpMouseMoved(evt);
            }
        });
        Telp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TelpMouseExited(evt);
            }
        });
        Telp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TelpKeyPressed(evt);
            }
        });
        FormInput.add(Telp);
        Telp.setBounds(274, 10, 100, 23);

        label31.setText("No. KTP :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label31);
        label31.setBounds(0, 70, 60, 23);

        KTP.setName("KTP"); // NOI18N
        KTP.setPreferredSize(new java.awt.Dimension(207, 23));
        KTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KTPKeyPressed(evt);
            }
        });
        FormInput.add(KTP);
        KTP.setBounds(64, 70, 160, 23);

        jLabel8.setText("J.K. :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(220, 70, 50, 23);

        JK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });
        FormInput.add(JK);
        JK.setBounds(274, 70, 100, 23);

        jLabel9.setText("G.D. :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(380, 10, 70, 23);

        GD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "AB", "O" }));
        GD.setName("GD"); // NOI18N
        GD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GDKeyPressed(evt);
            }
        });
        FormInput.add(GD);
        GD.setBounds(454, 10, 80, 23);

        DTPLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-03-2020" }));
        DTPLahir.setDisplayFormat("dd-MM-yyyy");
        DTPLahir.setName("DTPLahir"); // NOI18N
        DTPLahir.setOpaque(false);
        DTPLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPLahirKeyPressed(evt);
            }
        });
        FormInput.add(DTPLahir);
        DTPLahir.setBounds(274, 100, 100, 23);

        jLabel13.setText("Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 100, 60, 23);

        Lahir.setName("Lahir"); // NOI18N
        Lahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LahirKeyPressed(evt);
            }
        });
        FormInput.add(Lahir);
        Lahir.setBounds(64, 100, 206, 23);

        jLabel10.setText("Resus :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(596, 10, 40, 23);

        Resus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(+)", "(-)" }));
        Resus.setName("Resus"); // NOI18N
        Resus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResusKeyPressed(evt);
            }
        });
        FormInput.add(Resus);
        Resus.setBounds(640, 10, 80, 23);

        Alamat.setText("ALAMAT");
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AlamatMouseMoved(evt);
            }
        });
        Alamat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AlamatMouseExited(evt);
            }
        });
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        FormInput.add(Alamat);
        Alamat.setBounds(454, 40, 333, 23);

        BtnKelurahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahan.setMnemonic('2');
        BtnKelurahan.setToolTipText("ALt+2");
        BtnKelurahan.setName("BtnKelurahan"); // NOI18N
        BtnKelurahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanActionPerformed(evt);
            }
        });
        FormInput.add(BtnKelurahan);
        BtnKelurahan.setBounds(591, 70, 28, 23);

        Kelurahan.setEditable(false);
        Kelurahan.setText("KELURAHAN");
        Kelurahan.setHighlighter(null);
        Kelurahan.setName("Kelurahan"); // NOI18N
        Kelurahan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KelurahanMouseMoved(evt);
            }
        });
        Kelurahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KelurahanMouseExited(evt);
            }
        });
        Kelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelurahanKeyPressed(evt);
            }
        });
        FormInput.add(Kelurahan);
        Kelurahan.setBounds(454, 70, 135, 23);

        Kecamatan.setEditable(false);
        Kecamatan.setText("KECAMATAN");
        Kecamatan.setHighlighter(null);
        Kecamatan.setName("Kecamatan"); // NOI18N
        Kecamatan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KecamatanMouseMoved(evt);
            }
        });
        Kecamatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KecamatanMouseExited(evt);
            }
        });
        Kecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecamatanKeyPressed(evt);
            }
        });
        FormInput.add(Kecamatan);
        Kecamatan.setBounds(622, 70, 135, 23);

        BtnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatan.setMnemonic('3');
        BtnKecamatan.setToolTipText("ALt+3");
        BtnKecamatan.setName("BtnKecamatan"); // NOI18N
        BtnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanActionPerformed(evt);
            }
        });
        FormInput.add(BtnKecamatan);
        BtnKecamatan.setBounds(759, 70, 28, 23);

        Kabupaten.setEditable(false);
        Kabupaten.setText("KABUPATEN");
        Kabupaten.setHighlighter(null);
        Kabupaten.setName("Kabupaten"); // NOI18N
        Kabupaten.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KabupatenMouseMoved(evt);
            }
        });
        Kabupaten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KabupatenMouseExited(evt);
            }
        });
        Kabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenKeyPressed(evt);
            }
        });
        FormInput.add(Kabupaten);
        Kabupaten.setBounds(454, 100, 135, 23);

        BtnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupaten.setMnemonic('4');
        BtnKabupaten.setToolTipText("ALt+4");
        BtnKabupaten.setName("BtnKabupaten"); // NOI18N
        BtnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenActionPerformed(evt);
            }
        });
        FormInput.add(BtnKabupaten);
        BtnKabupaten.setBounds(591, 100, 28, 23);

        jLabel21.setText("Alamat :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(380, 40, 70, 23);

        Propinsi.setEditable(false);
        Propinsi.setText("PROPINSI");
        Propinsi.setHighlighter(null);
        Propinsi.setName("Propinsi"); // NOI18N
        Propinsi.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                PropinsiMouseMoved(evt);
            }
        });
        Propinsi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PropinsiMouseExited(evt);
            }
        });
        Propinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PropinsiKeyPressed(evt);
            }
        });
        FormInput.add(Propinsi);
        Propinsi.setBounds(622, 100, 135, 23);

        BtnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPropinsi.setMnemonic('4');
        BtnPropinsi.setToolTipText("ALt+4");
        BtnPropinsi.setName("BtnPropinsi"); // NOI18N
        BtnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPropinsiActionPerformed(evt);
            }
        });
        FormInput.add(BtnPropinsi);
        BtnPropinsi.setBounds(759, 100, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void NamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaKeyPressed
       Valid.pindah(evt,Telp,KTP);
}//GEN-LAST:event_NamaKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(Nama.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.hapusTable(tabMode,NoId,"utd_pendonor","no_pendonor");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoId.getText().trim().equals("")){
            Valid.textKosong(NoId,"No.ID Pendonor");
        }else if(Nama.getText().trim().equals("")){
            Valid.textKosong(Nama,"Nama Pendonor");
        }else if(KTP.getText().trim().equals("")){
            Valid.textKosong(KTP,"No.KTP");
        }else if(Telp.getText().trim().equals("")){
            Valid.textKosong(Telp,"No.Telp");
        }else if(Alamat.getText().trim().equals("")){
            Valid.textKosong(Alamat,"Alamat");
        }else if(Kecamatan.getText().trim().equals("")||Kecamatan.getText().trim().equals("KECAMATAN")){
            Valid.textKosong(Kecamatan,"Kecamatan");
        }else if(Kelurahan.getText().trim().equals("")||Kelurahan.getText().trim().equals("KELURAHAN")){
            Valid.textKosong(Kelurahan,"Kelurahan");
        }else if(Kabupaten.getText().trim().equals("")||Kabupaten.getText().trim().equals("KABUPATEN")){
            Valid.textKosong(Kabupaten,"Kabupaten");
        }else if(Propinsi.getText().trim().equals("")||Propinsi.getText().trim().equals("PROPINSI")){
            Valid.textKosong(Propinsi,"Propinsi");
        }else if(Lahir.getText().trim().equals("")){
            Valid.textKosong(Lahir,"Tempat Lahir");
        }else{
            if(tbDokter.getSelectedRow()!= -1){
                Valid.editTable(tabMode,"utd_pendonor","no_pendonor","?","no_pendonor=?,nama=?,no_ktp=?,jk=?,tmp_lahir=?,tgl_lahir=?,alamat=?,kd_kel=?,kd_kec=?,kd_kab=?,kd_prop=?,golongan_darah=?,resus=?,no_telp=?",15,new String[]{
                    NoId.getText(),Nama.getText(),KTP.getText(),JK.getSelectedItem().toString().substring(0,1),Lahir.getText(),Valid.SetTgl(DTPLahir.getSelectedItem()+""),
                    Alamat.getText(),kdkel,kdkec,kdkab,kdprop,GD.getSelectedItem().toString(),Resus.getSelectedItem().toString(),Telp.getText(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()
                });
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

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
            sql="";
            if(cmbHlm.getSelectedItem().toString().equals("Semua")){
                if(TCari.getText().equals("")&&GDCari.getSelectedItem().toString().equals("Semua")&&ResusCari.getSelectedItem().toString().equals("Semua")){
                    sql="select utd_pendonor.no_pendonor,utd_pendonor.nama,utd_pendonor.no_ktp,utd_pendonor.jk,utd_pendonor.tmp_lahir,utd_pendonor.tgl_lahir,"+
                        "utd_pendonor.alamat,utd_pendonor.kd_kel,utd_pendonor.kd_kec,utd_pendonor.kd_kab,utd_pendonor.kd_prop,utd_pendonor.golongan_darah,"+
                        "utd_pendonor.resus,utd_pendonor.no_telp,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                        "from utd_pendonor inner join kelurahan on utd_pendonor.kd_kel=kelurahan.kd_kel inner join kecamatan on utd_pendonor.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on utd_pendonor.kd_kab=kabupaten.kd_kab inner join propinsi on utd_pendonor.kd_prop=propinsi.kd_prop "+
                        "order by utd_pendonor.no_pendonor desc";
                }else{
                    sql="select utd_pendonor.no_pendonor,utd_pendonor.nama,utd_pendonor.no_ktp,utd_pendonor.jk,utd_pendonor.tmp_lahir,utd_pendonor.tgl_lahir,"+
                        "utd_pendonor.alamat,utd_pendonor.kd_kel,utd_pendonor.kd_kec,utd_pendonor.kd_kab,utd_pendonor.kd_prop,utd_pendonor.golongan_darah,"+
                        "utd_pendonor.resus,utd_pendonor.no_telp,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                        "from utd_pendonor inner join kelurahan on utd_pendonor.kd_kel=kelurahan.kd_kel inner join kecamatan on utd_pendonor.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on utd_pendonor.kd_kab=kabupaten.kd_kab inner join propinsi on utd_pendonor.kd_prop=propinsi.kd_prop "+
                        "where utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_pendonor like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.nama like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_ktp like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.alamat like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_telp like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kelurahan.nm_kel like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kecamatan.nm_kec like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kabupaten.nm_kab like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and propinsi.nm_prop like '%"+TCari.getText()+"%' "+
                        "order by utd_pendonor.no_pendonor desc";
                }   
            }else{
                if(TCari.getText().equals("")&&GDCari.getSelectedItem().toString().equals("Semua")&&ResusCari.getSelectedItem().toString().equals("Semua")){
                    sql="select utd_pendonor.no_pendonor,utd_pendonor.nama,utd_pendonor.no_ktp,utd_pendonor.jk,utd_pendonor.tmp_lahir,utd_pendonor.tgl_lahir,"+
                        "utd_pendonor.alamat,utd_pendonor.kd_kel,utd_pendonor.kd_kec,utd_pendonor.kd_kab,utd_pendonor.kd_prop,utd_pendonor.golongan_darah,"+
                        "utd_pendonor.resus,utd_pendonor.no_telp,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                        "from utd_pendonor inner join kelurahan on utd_pendonor.kd_kel=kelurahan.kd_kel inner join kecamatan on utd_pendonor.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on utd_pendonor.kd_kab=kabupaten.kd_kab inner join propinsi on utd_pendonor.kd_prop=propinsi.kd_prop "+
                        "order by utd_pendonor.no_pendonor desc limit "+cmbHlm.getSelectedItem().toString();
                }else{
                    sql="select utd_pendonor.no_pendonor,utd_pendonor.nama,utd_pendonor.no_ktp,utd_pendonor.jk,utd_pendonor.tmp_lahir,utd_pendonor.tgl_lahir,"+
                        "utd_pendonor.alamat,utd_pendonor.kd_kel,utd_pendonor.kd_kec,utd_pendonor.kd_kab,utd_pendonor.kd_prop,utd_pendonor.golongan_darah,"+
                        "utd_pendonor.resus,utd_pendonor.no_telp,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                        "from utd_pendonor inner join kelurahan on utd_pendonor.kd_kel=kelurahan.kd_kel inner join kecamatan on utd_pendonor.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on utd_pendonor.kd_kab=kabupaten.kd_kab inner join propinsi on utd_pendonor.kd_prop=propinsi.kd_prop "+
                        "where utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_pendonor like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.nama like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_ktp like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.alamat like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_telp like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kelurahan.nm_kel like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kecamatan.nm_kec like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kabupaten.nm_kab like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and propinsi.nm_prop like '%"+TCari.getText()+"%' "+
                        "order by utd_pendonor.no_pendonor desc limit "+cmbHlm.getSelectedItem().toString();
                }
            }
            Valid.MyReportqry("rptDataPendonorDarah.jasper","report","::[ Data Pendonor Darah ]::",sql,param);            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnEdit,BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoId.getText().trim().equals("")){
            Valid.textKosong(NoId,"No.ID Pendonor");
        }else if(Nama.getText().trim().equals("")){
            Valid.textKosong(Nama,"Nama Pendonor");
        }else if(KTP.getText().trim().equals("")){
            Valid.textKosong(KTP,"No.KTP");
        }else if(Telp.getText().trim().equals("")){
            Valid.textKosong(Telp,"No.Telp");
        }else if(Alamat.getText().trim().equals("")){
            Valid.textKosong(Alamat,"Alamat");
        }else if(Kecamatan.getText().trim().equals("")||Kecamatan.getText().trim().equals("KECAMATAN")){
            Valid.textKosong(Kecamatan,"Kecamatan");
        }else if(Kelurahan.getText().trim().equals("")||Kelurahan.getText().trim().equals("KELURAHAN")){
            Valid.textKosong(Kelurahan,"Kelurahan");
        }else if(Kabupaten.getText().trim().equals("")||Kabupaten.getText().trim().equals("KABUPATEN")){
            Valid.textKosong(Kabupaten,"Kabupaten");
        }else if(Propinsi.getText().trim().equals("")||Propinsi.getText().trim().equals("PROPINSI")){
            Valid.textKosong(Propinsi,"Propinsi");
        }else if(Lahir.getText().trim().equals("")){
            Valid.textKosong(Lahir,"Tempat Lahir");
        }else{
            if(Sequel.menyimpantf("utd_pendonor","?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.ID Pendonor",14,new String[]{
                NoId.getText(),Nama.getText(),KTP.getText(),JK.getSelectedItem().toString().substring(0,1),Lahir.getText(),Valid.SetTgl(DTPLahir.getSelectedItem()+""),
                Alamat.getText(),kdkel,kdkec,kdkab,kdprop,GD.getSelectedItem().toString(),Resus.getSelectedItem().toString(),Telp.getText()
            })==true){
                tampil();
                emptTeks();
            }               
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,NoRek,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void TelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TelpKeyPressed
         Valid.pindah(evt,NoId,Nama);
    }//GEN-LAST:event_TelpKeyPressed

private void KTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KTPKeyPressed
        Valid.pindah(evt,Nama,JK);
}//GEN-LAST:event_KTPKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void NoIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoIdKeyPressed
        Valid.pindah(evt,Alamat,Telp,TCari);
    }//GEN-LAST:event_NoIdKeyPressed

    private void TelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TelpMouseExited
        if(Telp.getText().equals("")){
            Telp.setText("0");
        }
    }//GEN-LAST:event_TelpMouseExited

    private void TelpMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TelpMouseMoved
        if(Telp.getText().equals("0")||Telp.getText().equals("0.0")){
            Telp.setText("");
        }
    }//GEN-LAST:event_TelpMouseMoved

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        Valid.pindah(evt,KTP,Lahir);
    }//GEN-LAST:event_JKKeyPressed

    private void DTPLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPLahirKeyPressed
        Valid.pindah(evt,Lahir,GD);
    }//GEN-LAST:event_DTPLahirKeyPressed

    private void LahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LahirKeyPressed
        Valid.pindah(evt,JK,DTPLahir);
    }//GEN-LAST:event_LahirKeyPressed

    private void ResusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResusKeyPressed
        Valid.pindah(evt,GD,Alamat);
    }//GEN-LAST:event_ResusKeyPressed

    private void AlamatMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseMoved
        if(KTP.getText().equals("ALAMAT")){
            KTP.setText("");
        }
    }//GEN-LAST:event_AlamatMouseMoved

    private void AlamatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseExited
        if(KTP.getText().equals("")){
            KTP.setText("ALAMAT");
        }
    }//GEN-LAST:event_AlamatMouseExited

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(KTP.getText().equals("")){
                KTP.setText("ALAMAT");
            }
            if(Kelurahan.getText().equals("KELURAHAN")){
                Kelurahan.setText("");
            }
            BtnKelurahan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(KTP.getText().equals("")){
                KTP.setText("ALAMAT");
            }
            Resus.requestFocus();
        }
    }//GEN-LAST:event_AlamatKeyPressed

    private void BtnKelurahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanActionPerformed
        kel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kel.setLocationRelativeTo(internalFrame1);
        kel.setVisible(true);
    }//GEN-LAST:event_BtnKelurahanActionPerformed

    private void KelurahanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseMoved
        if(Kelurahan.getText().equals("KELURAHAN")){
            Kelurahan.setText("");
        }
    }//GEN-LAST:event_KelurahanMouseMoved

    private void KelurahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseExited
        if(Kelurahan.getText().equals("")){
            Kelurahan.setText("KELURAHAN");
        }
    }//GEN-LAST:event_KelurahanMouseExited

    private void KelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelurahanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Kelurahan.getText().equals("")){
                Kelurahan.setText("KELURAHAN");
            }
            if(Kecamatan.getText().equals("KECAMATAN")){
                Kecamatan.setText("");
            }
            BtnKecamatan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Kelurahan.getText().equals("")){
                Kelurahan.setText("KELURAHAN");
            }
            if(Alamat.getText().equals("ALAMAT")){
                Alamat.setText("");
            }
            Alamat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKelurahanActionPerformed(null);
        }
    }//GEN-LAST:event_KelurahanKeyPressed

    private void KecamatanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseMoved
        if(Kecamatan.getText().equals("KECAMATAN")){
            Kecamatan.setText("");
        }
    }//GEN-LAST:event_KecamatanMouseMoved

    private void KecamatanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseExited
        if(Kecamatan.getText().equals("")){
            Kecamatan.setText("KECAMATAN");
        }
    }//GEN-LAST:event_KecamatanMouseExited

    private void KecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecamatanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Kecamatan.getText().equals("")){
                Kecamatan.setText("KECAMATAN");
            }
            if(Kabupaten.getText().equals("KABUPATEN")){
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Kecamatan.getText().equals("")){
                Kecamatan.setText("KECAMATAN");
            }
            if(Kelurahan.getText().equals("KELURAHAN")){
                Kelurahan.setText("");
            }
            Kelurahan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKecamatanActionPerformed(null);
        }
    }//GEN-LAST:event_KecamatanKeyPressed

    private void BtnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanActionPerformed
        kec.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);
    }//GEN-LAST:event_BtnKecamatanActionPerformed

    private void KabupatenMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseMoved
        if(Kabupaten.getText().equals("KABUPATEN")){
            Kabupaten.setText("");
        }
    }//GEN-LAST:event_KabupatenMouseMoved

    private void KabupatenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseExited
        if(Kabupaten.getText().equals("")){
            Kabupaten.setText("KABUPATEN");
        }
    }//GEN-LAST:event_KabupatenMouseExited

    private void KabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Kabupaten.getText().equals("")){
                Kabupaten.setText("KABUPATEN");
            }
            if(Propinsi.getText().equals("PROPINSI")){
                Propinsi.setText("");
            }
            Propinsi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Kabupaten.getText().equals("")){
                Kabupaten.setText("KABUPATEN");
            }
            if(Kecamatan.getText().equals("KECAMATAN")){
                Kecamatan.setText("");
            }
            Kecamatan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKabupatenActionPerformed(null);
        }
    }//GEN-LAST:event_KabupatenKeyPressed

    private void BtnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenActionPerformed
        kab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);
    }//GEN-LAST:event_BtnKabupatenActionPerformed

    private void PropinsiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseMoved
        if(Propinsi.getText().equals("PROPINSI")){
            Propinsi.setText("");
        }
    }//GEN-LAST:event_PropinsiMouseMoved

    private void PropinsiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseExited
        if(Propinsi.getText().equals("")){
            Propinsi.setText("PROPINSI");
        }
    }//GEN-LAST:event_PropinsiMouseExited

    private void PropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PropinsiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Propinsi.getText().equals("")){
                Propinsi.setText("PROPINSI");
            }
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Propinsi.getText().equals("")){
                Propinsi.setText("PROPINSI");
            }
            if(Kabupaten.getText().equals("KABUPATEN")){
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPropinsiActionPerformed(null);
        }
    }//GEN-LAST:event_PropinsiKeyPressed

    private void BtnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPropinsiActionPerformed
        prop.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        prop.setLocationRelativeTo(internalFrame1);
        prop.setVisible(true);
    }//GEN-LAST:event_BtnPropinsiActionPerformed

    private void ResusCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResusCariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResusCariKeyPressed

    private void GDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GDKeyPressed
        Valid.pindah(evt,Lahir,Resus);
    }//GEN-LAST:event_GDKeyPressed

    private void MnKartu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartu1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            NoId.requestFocus();
        }else if(Nama.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbDokter.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            Valid.MyReportqry("rptKartuDonorDarah.jasper","report","::[ Kartu Donor Darah ]::",
                    "select utd_pendonor.no_pendonor,utd_pendonor.nama,utd_pendonor.no_ktp,utd_pendonor.jk,utd_pendonor.tmp_lahir,utd_pendonor.tgl_lahir,"+
                    "utd_pendonor.alamat,utd_pendonor.kd_kel,utd_pendonor.kd_kec,utd_pendonor.kd_kab,utd_pendonor.kd_prop,utd_pendonor.golongan_darah,"+
                    "utd_pendonor.resus,utd_pendonor.no_telp,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                    "from utd_pendonor inner join kelurahan on utd_pendonor.kd_kel=kelurahan.kd_kel inner join kecamatan on utd_pendonor.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on utd_pendonor.kd_kab=kabupaten.kd_kab inner join propinsi on utd_pendonor.kd_prop=propinsi.kd_prop where utd_pendonor.no_pendonor='"+NoId.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnKartu1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UTDPendonor dialog = new UTDPendonor(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKabupaten;
    private widget.Button BtnKecamatan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKelurahan;
    private widget.Button BtnPrint;
    private widget.Button BtnPropinsi;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPLahir;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox GD;
    private widget.ComboBox GDCari;
    private widget.ComboBox JK;
    private widget.TextBox KTP;
    private widget.TextBox Kabupaten;
    private widget.TextBox Kecamatan;
    private widget.TextBox Kelurahan;
    private widget.Label LCount;
    private widget.TextBox Lahir;
    private javax.swing.JMenuItem MnKartu1;
    private widget.TextBox Nama;
    private widget.TextBox NoId;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Propinsi;
    private widget.ComboBox Resus;
    private widget.ComboBox ResusCari;
    private widget.TextBox TCari;
    private widget.TextBox Telp;
    private widget.ComboBox cmbHlm;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label18;
    private widget.Label label26;
    private widget.Label label31;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            sql="";
            if(cmbHlm.getSelectedItem().toString().equals("Semua")){
                if(TCari.getText().equals("")&&GDCari.getSelectedItem().toString().equals("Semua")&&ResusCari.getSelectedItem().toString().equals("Semua")){
                    sql="select utd_pendonor.no_pendonor,utd_pendonor.nama,utd_pendonor.no_ktp,utd_pendonor.jk,utd_pendonor.tmp_lahir,utd_pendonor.tgl_lahir,"+
                        "utd_pendonor.alamat,utd_pendonor.kd_kel,utd_pendonor.kd_kec,utd_pendonor.kd_kab,utd_pendonor.kd_prop,utd_pendonor.golongan_darah,"+
                        "utd_pendonor.resus,utd_pendonor.no_telp,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                        "from utd_pendonor inner join kelurahan on utd_pendonor.kd_kel=kelurahan.kd_kel inner join kecamatan on utd_pendonor.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on utd_pendonor.kd_kab=kabupaten.kd_kab inner join propinsi on utd_pendonor.kd_prop=propinsi.kd_prop "+
                        "order by utd_pendonor.no_pendonor desc";
                }else{
                    sql="select utd_pendonor.no_pendonor,utd_pendonor.nama,utd_pendonor.no_ktp,utd_pendonor.jk,utd_pendonor.tmp_lahir,utd_pendonor.tgl_lahir,"+
                        "utd_pendonor.alamat,utd_pendonor.kd_kel,utd_pendonor.kd_kec,utd_pendonor.kd_kab,utd_pendonor.kd_prop,utd_pendonor.golongan_darah,"+
                        "utd_pendonor.resus,utd_pendonor.no_telp,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                        "from utd_pendonor inner join kelurahan on utd_pendonor.kd_kel=kelurahan.kd_kel inner join kecamatan on utd_pendonor.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on utd_pendonor.kd_kab=kabupaten.kd_kab inner join propinsi on utd_pendonor.kd_prop=propinsi.kd_prop "+
                        "where utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_pendonor like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.nama like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_ktp like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.alamat like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_telp like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kelurahan.nm_kel like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kecamatan.nm_kec like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kabupaten.nm_kab like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and propinsi.nm_prop like '%"+TCari.getText()+"%' "+
                        "order by utd_pendonor.no_pendonor desc";
                }   
            }else{
                if(TCari.getText().equals("")&&GDCari.getSelectedItem().toString().equals("Semua")&&ResusCari.getSelectedItem().toString().equals("Semua")){
                    sql="select utd_pendonor.no_pendonor,utd_pendonor.nama,utd_pendonor.no_ktp,utd_pendonor.jk,utd_pendonor.tmp_lahir,utd_pendonor.tgl_lahir,"+
                        "utd_pendonor.alamat,utd_pendonor.kd_kel,utd_pendonor.kd_kec,utd_pendonor.kd_kab,utd_pendonor.kd_prop,utd_pendonor.golongan_darah,"+
                        "utd_pendonor.resus,utd_pendonor.no_telp,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                        "from utd_pendonor inner join kelurahan on utd_pendonor.kd_kel=kelurahan.kd_kel inner join kecamatan on utd_pendonor.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on utd_pendonor.kd_kab=kabupaten.kd_kab inner join propinsi on utd_pendonor.kd_prop=propinsi.kd_prop "+
                        "order by utd_pendonor.no_pendonor desc limit "+cmbHlm.getSelectedItem().toString();
                }else{
                    sql="select utd_pendonor.no_pendonor,utd_pendonor.nama,utd_pendonor.no_ktp,utd_pendonor.jk,utd_pendonor.tmp_lahir,utd_pendonor.tgl_lahir,"+
                        "utd_pendonor.alamat,utd_pendonor.kd_kel,utd_pendonor.kd_kec,utd_pendonor.kd_kab,utd_pendonor.kd_prop,utd_pendonor.golongan_darah,"+
                        "utd_pendonor.resus,utd_pendonor.no_telp,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                        "from utd_pendonor inner join kelurahan on utd_pendonor.kd_kel=kelurahan.kd_kel inner join kecamatan on utd_pendonor.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on utd_pendonor.kd_kab=kabupaten.kd_kab inner join propinsi on utd_pendonor.kd_prop=propinsi.kd_prop "+
                        "where utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_pendonor like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.nama like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_ktp like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.alamat like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and utd_pendonor.no_telp like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kelurahan.nm_kel like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kecamatan.nm_kec like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and kabupaten.nm_kab like '%"+TCari.getText()+"%' or "+
                        "utd_pendonor.golongan_darah like '%"+GDCari.getSelectedItem()+"%' and utd_pendonor.resus like '%"+ResusCari.getSelectedItem()+"%' and propinsi.nm_prop like '%"+TCari.getText()+"%' "+
                        "order by utd_pendonor.no_pendonor desc limit "+cmbHlm.getSelectedItem().toString();
                }
            }
            ps=koneksi.prepareStatement(sql);
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_pendonor"),rs.getString("nama"),rs.getString("no_ktp"),rs.getString("jk"),
                        rs.getString("tmp_lahir"),rs.getString("tgl_lahir"),rs.getString("alamat"),rs.getString("kd_kel"),
                        rs.getString("nm_kel"),rs.getString("kd_kec"),rs.getString("nm_kec"),rs.getString("kd_kab"),
                        rs.getString("nm_kab"),rs.getString("kd_prop"),rs.getString("nm_prop"),rs.getString("golongan_darah"),
                        rs.getString("resus"),rs.getString("no_telp")
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
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
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        NoId.setText("");
        Nama.setText("");
        KTP.setText("");
        Telp.setText("0");
        Lahir.setText("");
        Alamat.setText("");
        Kelurahan.setText("KELURAHAN");
        Kabupaten.setText("KABUPATEN");
        Kecamatan.setText("KECAMATAN");
        Propinsi.setText("PROPINSI");
        DTPLahir.setDate(new Date());
        NoId.requestFocus();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_pendonor,6),signed)),0) from utd_pendonor","UTD",6,NoId);
    }

    private void getData() {
        if(tbDokter.getSelectedRow()!= -1){
            NoId.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
            Nama.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),1).toString());
            KTP.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),2).toString());
            JK.setSelectedItem(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString().replaceAll("P","PEREMPUAN").replaceAll("L","LAKI-LAKI"));
            Lahir.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),4).toString());
            Valid.SetTgl(DTPLahir,tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString());
            Alamat.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),6).toString());
            kdkel=tabMode.getValueAt(tbDokter.getSelectedRow(),7).toString();
            Kelurahan.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),8).toString());
            kdkec=tabMode.getValueAt(tbDokter.getSelectedRow(),9).toString();
            Kecamatan.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),10).toString());
            kdkab=tabMode.getValueAt(tbDokter.getSelectedRow(),11).toString();
            Kabupaten.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),12).toString());
            kdprop=tabMode.getValueAt(tbDokter.getSelectedRow(),13).toString();
            Propinsi.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),14).toString());
            GD.setSelectedItem(tabMode.getValueAt(tbDokter.getSelectedRow(),15).toString());
            Resus.setSelectedItem(tabMode.getValueAt(tbDokter.getSelectedRow(),16).toString());
            Telp.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),17).toString());
        }
    }

    public JTable getTable(){
        return tbDokter;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getutd_pendonor());
        BtnHapus.setEnabled(akses.getutd_pendonor());
        BtnEdit.setEnabled(akses.getutd_pendonor());
        BtnPrint.setEnabled(akses.getutd_pendonor());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,155));
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

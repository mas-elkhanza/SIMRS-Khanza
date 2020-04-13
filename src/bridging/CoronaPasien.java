package bridging;

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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author dosen
 */
public class CoronaPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String pilihan="";
    private CoronaReferensiJK jk=new CoronaReferensiJK(null,false);
    private CoronaReferensiKewarganegaraan kewarganegaraan=new CoronaReferensiKewarganegaraan(null,false);
    private CoronaReferensiSumberPenularan penularan=new CoronaReferensiSumberPenularan(null,false);
    private CoronaReferensiKecamatan kecamatan=new CoronaReferensiKecamatan(null,false);
    private CoronaReferensiStatusKeluar statuskeluar=new CoronaReferensiStatusKeluar(null,false);
    private CoronaReferensiStatusRawat statusrawat=new CoronaReferensiStatusRawat(null,false);
    private CoronaReferensiStatusIsolasi statusisolasi=new CoronaReferensiStatusIsolasi(null,false);
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public CoronaPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Tgl.Skrining","Jam Skrining","No.R.M.","Nama Pasien","Tgl.Lahir","Ibu Kandung","J.K.","Geriatri",
                "Kesadaran","Pernafasan","Nyeri Dada","Skala Nyeri","Keputusan","NIP","Nama Petugas"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(140);
            }else if(i==6){
                column.setPreferredWidth(30);
            }else if(i==7){
                column.setPreferredWidth(45);
            }else if(i==8){
                column.setPreferredWidth(170);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(170);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());


        TNoRM.setDocument(new batasInput((byte)17).getKata(TNoRM));
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
        
        ChkInput.setSelected(false);
        isForm();
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    kdjk.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    nmjk.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                kdjk.requestFocus();
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
        
        jk.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jk.getTable().getSelectedRow()!= -1){                   
                    kdjk.setText(jk.getTable().getValueAt(jk.getTable().getSelectedRow(),0).toString());
                    nmjk.setText(jk.getTable().getValueAt(jk.getTable().getSelectedRow(),1).toString());
                    BtnJK.requestFocus();
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
        
        kewarganegaraan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kewarganegaraan.getTable().getSelectedRow()!= -1){                   
                    KodeKewarganegaraan.setText(kewarganegaraan.getTable().getValueAt(kewarganegaraan.getTable().getSelectedRow(),0).toString());
                    NamaKewarganegaraan.setText(kewarganegaraan.getTable().getValueAt(kewarganegaraan.getTable().getSelectedRow(),1).toString());
                    BtnKewarganegaraan.requestFocus();
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
        
        penularan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penularan.getTable().getSelectedRow()!= -1){                   
                    KodePenularan.setText(penularan.getTable().getValueAt(penularan.getTable().getSelectedRow(),0).toString());
                    NamaPenularan.setText(penularan.getTable().getValueAt(penularan.getTable().getSelectedRow(),1).toString());
                    BtnSumberPenularan.requestFocus();
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
        
        statuskeluar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(statuskeluar.getTable().getSelectedRow()!= -1){                   
                    KodeStatusKeluar.setText(statuskeluar.getTable().getValueAt(statuskeluar.getTable().getSelectedRow(),0).toString());
                    NamaStatusKeluar.setText(statuskeluar.getTable().getValueAt(statuskeluar.getTable().getSelectedRow(),1).toString());
                    BtnStatusKeluar.requestFocus();
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
        
        statusrawat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(statusrawat.getTable().getSelectedRow()!= -1){                   
                    KodeStatusRawat.setText(statusrawat.getTable().getValueAt(statusrawat.getTable().getSelectedRow(),0).toString());
                    NamaStatusRawat.setText(statusrawat.getTable().getValueAt(statusrawat.getTable().getSelectedRow(),1).toString());
                    BtnStatusRawat.requestFocus();
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
        
        
        statusisolasi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(statusisolasi.getTable().getSelectedRow()!= -1){                   
                    KodeStatusIsolasi.setText(statusisolasi.getTable().getValueAt(statusisolasi.getTable().getSelectedRow(),0).toString());
                    NamaStatusIsolasi.setText(statusisolasi.getTable().getValueAt(statusisolasi.getTable().getSelectedRow(),1).toString());
                    BtnStatusIsolasi.requestFocus();
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

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        Lahir = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel20 = new widget.Label();
        kdjk = new widget.TextBox();
        nmjk = new widget.TextBox();
        BtnJK = new widget.Button();
        jLabel7 = new widget.Label();
        NoKTP = new widget.TextBox();
        jLabel11 = new widget.Label();
        DTPReg = new widget.Tanggal();
        jLabel12 = new widget.Label();
        DTPReg1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        KodeKewarganegaraan = new widget.TextBox();
        NamaKewarganegaraan = new widget.TextBox();
        BtnKewarganegaraan = new widget.Button();
        jLabel22 = new widget.Label();
        KodePenularan = new widget.TextBox();
        NamaPenularan = new widget.TextBox();
        BtnSumberPenularan = new widget.Button();
        jLabel23 = new widget.Label();
        KodePenularan1 = new widget.TextBox();
        NamaPenularan1 = new widget.TextBox();
        BtnSumberPenularan1 = new widget.Button();
        jLabel13 = new widget.Label();
        DTPReg2 = new widget.Tanggal();
        jLabel24 = new widget.Label();
        KodeStatusKeluar = new widget.TextBox();
        NamaStatusKeluar = new widget.TextBox();
        BtnStatusKeluar = new widget.Button();
        jLabel14 = new widget.Label();
        DTPReg3 = new widget.Tanggal();
        jLabel25 = new widget.Label();
        KodeStatusRawat = new widget.TextBox();
        NamaStatusRawat = new widget.TextBox();
        BtnStatusRawat = new widget.Button();
        jLabel26 = new widget.Label();
        KodeStatusIsolasi = new widget.TextBox();
        NamaStatusIsolasi = new widget.TextBox();
        BtnStatusIsolasi = new widget.Button();
        jLabel8 = new widget.Label();
        Email = new widget.TextBox();
        jLabel9 = new widget.Label();
        NoTelp = new widget.TextBox();
        jLabel16 = new widget.Label();
        Lahir3 = new widget.TextBox();
        jLabel27 = new widget.Label();
        KodePenularan2 = new widget.TextBox();
        NamaPenularan2 = new widget.TextBox();
        BtnSumberPenularan2 = new widget.Button();
        jLabel28 = new widget.Label();
        KodeKabupaten = new widget.TextBox();
        NamaKabupaten = new widget.TextBox();
        BtnSumberPenularan3 = new widget.Button();
        jLabel29 = new widget.Label();
        KodePropinsi = new widget.TextBox();
        NamaPropinsi = new widget.TextBox();
        BtnPropinsi = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pasien Teridentifikasi Corona ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Tgl.Masuk :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(63, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-04-2020" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-04-2020" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(360, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass7.add(BtnCari);

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
        panelGlass7.add(BtnAll);

        jPanel3.add(panelGlass7, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 276));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(190, 107));
        FormInput.setLayout(null);

        jLabel4.setText("Pasien :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 40, 110, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(114, 40, 90, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(206, 40, 220, 23);

        Lahir.setEditable(false);
        Lahir.setHighlighter(null);
        Lahir.setName("Lahir"); // NOI18N
        FormInput.add(Lahir);
        Lahir.setBounds(114, 70, 95, 23);

        jLabel5.setText("Inisial :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 70, 110, 23);

        jLabel20.setText("J.K. :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(209, 70, 35, 23);

        kdjk.setEditable(false);
        kdjk.setHighlighter(null);
        kdjk.setName("kdjk"); // NOI18N
        FormInput.add(kdjk);
        kdjk.setBounds(248, 70, 35, 23);

        nmjk.setEditable(false);
        nmjk.setHighlighter(null);
        nmjk.setName("nmjk"); // NOI18N
        FormInput.add(nmjk);
        nmjk.setBounds(285, 70, 110, 23);

        BtnJK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJK.setMnemonic('X');
        BtnJK.setToolTipText("Alt+X");
        BtnJK.setName("BtnJK"); // NOI18N
        BtnJK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJKActionPerformed(evt);
            }
        });
        BtnJK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnJKKeyPressed(evt);
            }
        });
        FormInput.add(BtnJK);
        BtnJK.setBounds(398, 70, 28, 23);

        jLabel7.setText("No.KTP/Paspor :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(0, 10, 110, 23);

        NoKTP.setHighlighter(null);
        NoKTP.setName("NoKTP"); // NOI18N
        NoKTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKTPKeyPressed(evt);
            }
        });
        FormInput.add(NoKTP);
        NoKTP.setBounds(114, 10, 170, 23);

        jLabel11.setText("Tanggal Masuk :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 130, 110, 23);

        DTPReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-04-2020" }));
        DTPReg.setDisplayFormat("dd-MM-yyyy");
        DTPReg.setName("DTPReg"); // NOI18N
        DTPReg.setOpaque(false);
        FormInput.add(DTPReg);
        DTPReg.setBounds(114, 130, 90, 23);

        jLabel12.setText("Tgl.Lahir :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 100, 110, 23);

        DTPReg1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-04-2020" }));
        DTPReg1.setDisplayFormat("dd-MM-yyyy");
        DTPReg1.setName("DTPReg1"); // NOI18N
        DTPReg1.setOpaque(false);
        FormInput.add(DTPReg1);
        DTPReg1.setBounds(114, 100, 90, 23);

        jLabel21.setText("Kewarganegaraan :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(0, 190, 110, 23);

        KodeKewarganegaraan.setEditable(false);
        KodeKewarganegaraan.setHighlighter(null);
        KodeKewarganegaraan.setName("KodeKewarganegaraan"); // NOI18N
        FormInput.add(KodeKewarganegaraan);
        KodeKewarganegaraan.setBounds(114, 190, 50, 23);

        NamaKewarganegaraan.setEditable(false);
        NamaKewarganegaraan.setHighlighter(null);
        NamaKewarganegaraan.setName("NamaKewarganegaraan"); // NOI18N
        FormInput.add(NamaKewarganegaraan);
        NamaKewarganegaraan.setBounds(166, 190, 229, 23);

        BtnKewarganegaraan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKewarganegaraan.setMnemonic('X');
        BtnKewarganegaraan.setToolTipText("Alt+X");
        BtnKewarganegaraan.setName("BtnKewarganegaraan"); // NOI18N
        BtnKewarganegaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKewarganegaraanActionPerformed(evt);
            }
        });
        BtnKewarganegaraan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKewarganegaraanKeyPressed(evt);
            }
        });
        FormInput.add(BtnKewarganegaraan);
        BtnKewarganegaraan.setBounds(398, 190, 28, 23);

        jLabel22.setText("Sumber Penularan :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 220, 110, 23);

        KodePenularan.setEditable(false);
        KodePenularan.setHighlighter(null);
        KodePenularan.setName("KodePenularan"); // NOI18N
        FormInput.add(KodePenularan);
        KodePenularan.setBounds(114, 220, 50, 23);

        NamaPenularan.setEditable(false);
        NamaPenularan.setHighlighter(null);
        NamaPenularan.setName("NamaPenularan"); // NOI18N
        FormInput.add(NamaPenularan);
        NamaPenularan.setBounds(166, 220, 229, 23);

        BtnSumberPenularan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSumberPenularan.setMnemonic('X');
        BtnSumberPenularan.setToolTipText("Alt+X");
        BtnSumberPenularan.setName("BtnSumberPenularan"); // NOI18N
        BtnSumberPenularan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSumberPenularanActionPerformed(evt);
            }
        });
        BtnSumberPenularan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSumberPenularanKeyPressed(evt);
            }
        });
        FormInput.add(BtnSumberPenularan);
        BtnSumberPenularan.setBounds(398, 220, 28, 23);

        jLabel23.setText("Kecamatan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(435, 40, 89, 23);

        KodePenularan1.setEditable(false);
        KodePenularan1.setHighlighter(null);
        KodePenularan1.setName("KodePenularan1"); // NOI18N
        FormInput.add(KodePenularan1);
        KodePenularan1.setBounds(528, 40, 50, 23);

        NamaPenularan1.setEditable(false);
        NamaPenularan1.setHighlighter(null);
        NamaPenularan1.setName("NamaPenularan1"); // NOI18N
        FormInput.add(NamaPenularan1);
        NamaPenularan1.setBounds(580, 40, 210, 23);

        BtnSumberPenularan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSumberPenularan1.setMnemonic('X');
        BtnSumberPenularan1.setToolTipText("Alt+X");
        BtnSumberPenularan1.setName("BtnSumberPenularan1"); // NOI18N
        BtnSumberPenularan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSumberPenularan1ActionPerformed(evt);
            }
        });
        BtnSumberPenularan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSumberPenularan1KeyPressed(evt);
            }
        });
        FormInput.add(BtnSumberPenularan1);
        BtnSumberPenularan1.setBounds(793, 40, 28, 23);

        jLabel13.setText("Tanggal Keluar :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(237, 130, 95, 23);

        DTPReg2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-04-2020" }));
        DTPReg2.setDisplayFormat("dd-MM-yyyy");
        DTPReg2.setName("DTPReg2"); // NOI18N
        DTPReg2.setOpaque(false);
        FormInput.add(DTPReg2);
        DTPReg2.setBounds(336, 130, 90, 23);

        jLabel24.setText("Status Keluar :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(435, 130, 89, 23);

        KodeStatusKeluar.setEditable(false);
        KodeStatusKeluar.setHighlighter(null);
        KodeStatusKeluar.setName("KodeStatusKeluar"); // NOI18N
        FormInput.add(KodeStatusKeluar);
        KodeStatusKeluar.setBounds(528, 130, 50, 23);

        NamaStatusKeluar.setEditable(false);
        NamaStatusKeluar.setHighlighter(null);
        NamaStatusKeluar.setName("NamaStatusKeluar"); // NOI18N
        FormInput.add(NamaStatusKeluar);
        NamaStatusKeluar.setBounds(580, 130, 210, 23);

        BtnStatusKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnStatusKeluar.setMnemonic('X');
        BtnStatusKeluar.setToolTipText("Alt+X");
        BtnStatusKeluar.setName("BtnStatusKeluar"); // NOI18N
        BtnStatusKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnStatusKeluarActionPerformed(evt);
            }
        });
        BtnStatusKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnStatusKeluarKeyPressed(evt);
            }
        });
        FormInput.add(BtnStatusKeluar);
        BtnStatusKeluar.setBounds(793, 130, 28, 23);

        jLabel14.setText("Tgl. Lapor :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(217, 100, 70, 23);

        DTPReg3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-04-2020 09:44:00" }));
        DTPReg3.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DTPReg3.setName("DTPReg3"); // NOI18N
        DTPReg3.setOpaque(false);
        FormInput.add(DTPReg3);
        DTPReg3.setBounds(291, 100, 135, 23);

        jLabel25.setText("Status Rawat :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(435, 160, 89, 23);

        KodeStatusRawat.setEditable(false);
        KodeStatusRawat.setHighlighter(null);
        KodeStatusRawat.setName("KodeStatusRawat"); // NOI18N
        FormInput.add(KodeStatusRawat);
        KodeStatusRawat.setBounds(528, 160, 50, 23);

        NamaStatusRawat.setEditable(false);
        NamaStatusRawat.setHighlighter(null);
        NamaStatusRawat.setName("NamaStatusRawat"); // NOI18N
        FormInput.add(NamaStatusRawat);
        NamaStatusRawat.setBounds(580, 160, 210, 23);

        BtnStatusRawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnStatusRawat.setMnemonic('X');
        BtnStatusRawat.setToolTipText("Alt+X");
        BtnStatusRawat.setName("BtnStatusRawat"); // NOI18N
        BtnStatusRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnStatusRawatActionPerformed(evt);
            }
        });
        BtnStatusRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnStatusRawatKeyPressed(evt);
            }
        });
        FormInput.add(BtnStatusRawat);
        BtnStatusRawat.setBounds(793, 160, 28, 23);

        jLabel26.setText("Status Isolasi :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(435, 190, 89, 23);

        KodeStatusIsolasi.setEditable(false);
        KodeStatusIsolasi.setHighlighter(null);
        KodeStatusIsolasi.setName("KodeStatusIsolasi"); // NOI18N
        FormInput.add(KodeStatusIsolasi);
        KodeStatusIsolasi.setBounds(528, 190, 50, 23);

        NamaStatusIsolasi.setEditable(false);
        NamaStatusIsolasi.setHighlighter(null);
        NamaStatusIsolasi.setName("NamaStatusIsolasi"); // NOI18N
        FormInput.add(NamaStatusIsolasi);
        NamaStatusIsolasi.setBounds(580, 190, 210, 23);

        BtnStatusIsolasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnStatusIsolasi.setMnemonic('X');
        BtnStatusIsolasi.setToolTipText("Alt+X");
        BtnStatusIsolasi.setName("BtnStatusIsolasi"); // NOI18N
        BtnStatusIsolasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnStatusIsolasiActionPerformed(evt);
            }
        });
        BtnStatusIsolasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnStatusIsolasiKeyPressed(evt);
            }
        });
        FormInput.add(BtnStatusIsolasi);
        BtnStatusIsolasi.setBounds(793, 190, 28, 23);

        jLabel8.setText("Email :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 160, 110, 23);

        Email.setEditable(false);
        Email.setHighlighter(null);
        Email.setName("Email"); // NOI18N
        FormInput.add(Email);
        Email.setBounds(114, 160, 150, 23);

        jLabel9.setText("No.Telp :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(267, 160, 50, 23);

        NoTelp.setEditable(false);
        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        FormInput.add(NoTelp);
        NoTelp.setBounds(321, 160, 105, 23);

        jLabel16.setText("Sebab Kematian :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(435, 220, 89, 23);

        Lahir3.setEditable(false);
        Lahir3.setHighlighter(null);
        Lahir3.setName("Lahir3"); // NOI18N
        FormInput.add(Lahir3);
        Lahir3.setBounds(528, 220, 293, 23);

        jLabel27.setText("Kelurahan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(435, 10, 89, 23);

        KodePenularan2.setEditable(false);
        KodePenularan2.setHighlighter(null);
        KodePenularan2.setName("KodePenularan2"); // NOI18N
        FormInput.add(KodePenularan2);
        KodePenularan2.setBounds(528, 10, 50, 23);

        NamaPenularan2.setEditable(false);
        NamaPenularan2.setHighlighter(null);
        NamaPenularan2.setName("NamaPenularan2"); // NOI18N
        FormInput.add(NamaPenularan2);
        NamaPenularan2.setBounds(580, 10, 210, 23);

        BtnSumberPenularan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSumberPenularan2.setMnemonic('X');
        BtnSumberPenularan2.setToolTipText("Alt+X");
        BtnSumberPenularan2.setName("BtnSumberPenularan2"); // NOI18N
        BtnSumberPenularan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSumberPenularan2ActionPerformed(evt);
            }
        });
        BtnSumberPenularan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSumberPenularan2KeyPressed(evt);
            }
        });
        FormInput.add(BtnSumberPenularan2);
        BtnSumberPenularan2.setBounds(793, 10, 28, 23);

        jLabel28.setText("Kabupaten :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(435, 70, 89, 23);

        KodeKabupaten.setEditable(false);
        KodeKabupaten.setHighlighter(null);
        KodeKabupaten.setName("KodeKabupaten"); // NOI18N
        FormInput.add(KodeKabupaten);
        KodeKabupaten.setBounds(528, 70, 50, 23);

        NamaKabupaten.setEditable(false);
        NamaKabupaten.setHighlighter(null);
        NamaKabupaten.setName("NamaKabupaten"); // NOI18N
        FormInput.add(NamaKabupaten);
        NamaKabupaten.setBounds(580, 70, 210, 23);

        BtnSumberPenularan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSumberPenularan3.setMnemonic('X');
        BtnSumberPenularan3.setToolTipText("Alt+X");
        BtnSumberPenularan3.setName("BtnSumberPenularan3"); // NOI18N
        BtnSumberPenularan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSumberPenularan3ActionPerformed(evt);
            }
        });
        BtnSumberPenularan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSumberPenularan3KeyPressed(evt);
            }
        });
        FormInput.add(BtnSumberPenularan3);
        BtnSumberPenularan3.setBounds(793, 70, 28, 23);

        jLabel29.setText("Propinsi :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(435, 100, 89, 23);

        KodePropinsi.setEditable(false);
        KodePropinsi.setHighlighter(null);
        KodePropinsi.setName("KodePropinsi"); // NOI18N
        FormInput.add(KodePropinsi);
        KodePropinsi.setBounds(528, 100, 50, 23);

        NamaPropinsi.setEditable(false);
        NamaPropinsi.setHighlighter(null);
        NamaPropinsi.setName("NamaPropinsi"); // NOI18N
        FormInput.add(NamaPropinsi);
        NamaPropinsi.setBounds(580, 100, 210, 23);

        BtnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPropinsi.setMnemonic('X');
        BtnPropinsi.setToolTipText("Alt+X");
        BtnPropinsi.setName("BtnPropinsi"); // NOI18N
        BtnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPropinsiActionPerformed(evt);
            }
        });
        BtnPropinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPropinsiKeyPressed(evt);
            }
        });
        FormInput.add(BtnPropinsi);
        BtnPropinsi.setBounds(793, 100, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        }
        
}//GEN-LAST:event_TNoRMKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"Pasien");
        }else if(kdjk.getText().trim().equals("")||nmjk.getText().trim().equals("")){
            Valid.textKosong(BtnJK,"Petugas");
        }else{
            
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,BtnJK,BtnBatal);
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

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()> -1){ 
            if(Sequel.queryu2tf("delete from skrining_rawat_jalan where tanggal=? and jam=? and no_rkm_medis=?",3,new String[]{
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()
                })==true){
                tampil();
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

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
            Valid.MyReportqry("rptSkriningRalan.jasper","report","::[ Data Skrining Rawat Jalan ]::",
                    "select skrining_rawat_jalan.tanggal,skrining_rawat_jalan.jam,skrining_rawat_jalan.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"+
                    "pasien.nm_ibu,pasien.jk,skrining_rawat_jalan.geriatri,skrining_rawat_jalan.kesadaran,skrining_rawat_jalan.pernapasan,"+
                    "skrining_rawat_jalan.nyeri_dada,skrining_rawat_jalan.skala_nyeri,skrining_rawat_jalan.keputusan,skrining_rawat_jalan.nip,petugas.nama "+
                    "from skrining_rawat_jalan inner join pasien inner join petugas on skrining_rawat_jalan.no_rkm_medis=pasien.no_rkm_medis and skrining_rawat_jalan.nip=petugas.nip where "+
                    "skrining_rawat_jalan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_rawat_jalan.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    "skrining_rawat_jalan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "skrining_rawat_jalan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_ibu like '%"+TCari.getText().trim()+"%' or "+
                    "skrining_rawat_jalan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_rawat_jalan.kesadaran like '%"+TCari.getText().trim()+"%' or "+
                    "skrining_rawat_jalan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_rawat_jalan.pernapasan like '%"+TCari.getText().trim()+"%' or "+
                    "skrining_rawat_jalan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_rawat_jalan.nyeri_dada like '%"+TCari.getText().trim()+"%' or "+
                    "skrining_rawat_jalan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_rawat_jalan.keputusan like '%"+TCari.getText().trim()+"%' or "+
                    "skrining_rawat_jalan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and skrining_rawat_jalan.nip like '%"+TCari.getText().trim()+"%' or "+
                    "skrining_rawat_jalan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and petugas.nama like '%"+TCari.getText().trim()+"%' order by skrining_rawat_jalan.tanggal desc",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

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

    private void BtnJKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJKActionPerformed
        jk.setSize(300,300);
        jk.setLocationRelativeTo(null);
        jk.setVisible(true);
    }//GEN-LAST:event_BtnJKActionPerformed

    private void BtnJKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnJKKeyPressed
        //Valid.pindah(evt,Keputusan,BtnSimpan);
    }//GEN-LAST:event_BtnJKKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void NoKTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKTPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoKTPKeyPressed

    private void BtnKewarganegaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKewarganegaraanActionPerformed
        kewarganegaraan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kewarganegaraan.setLocationRelativeTo(internalFrame1);
        kewarganegaraan.setVisible(true);
    }//GEN-LAST:event_BtnKewarganegaraanActionPerformed

    private void BtnKewarganegaraanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKewarganegaraanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKewarganegaraanKeyPressed

    private void BtnSumberPenularanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSumberPenularanActionPerformed
        penularan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penularan.setLocationRelativeTo(internalFrame1);
        penularan.setVisible(true);
    }//GEN-LAST:event_BtnSumberPenularanActionPerformed

    private void BtnSumberPenularanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSumberPenularanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSumberPenularanKeyPressed

    private void BtnSumberPenularan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSumberPenularan1ActionPerformed
        kecamatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kecamatan.setLocationRelativeTo(internalFrame1);
        kecamatan.setVisible(true);
    }//GEN-LAST:event_BtnSumberPenularan1ActionPerformed

    private void BtnSumberPenularan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSumberPenularan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSumberPenularan1KeyPressed

    private void BtnStatusKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnStatusKeluarActionPerformed
        statuskeluar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        statuskeluar.setLocationRelativeTo(internalFrame1);
        statuskeluar.setVisible(true);
    }//GEN-LAST:event_BtnStatusKeluarActionPerformed

    private void BtnStatusKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnStatusKeluarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnStatusKeluarKeyPressed

    private void BtnStatusRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnStatusRawatActionPerformed
        statusrawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        statusrawat.setLocationRelativeTo(internalFrame1);
        statusrawat.setVisible(true);
    }//GEN-LAST:event_BtnStatusRawatActionPerformed

    private void BtnStatusRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnStatusRawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnStatusRawatKeyPressed

    private void BtnStatusIsolasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnStatusIsolasiActionPerformed
        statusisolasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        statusisolasi.setLocationRelativeTo(internalFrame1);
        statusisolasi.setVisible(true);
    }//GEN-LAST:event_BtnStatusIsolasiActionPerformed

    private void BtnStatusIsolasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnStatusIsolasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnStatusIsolasiKeyPressed

    private void BtnSumberPenularan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSumberPenularan2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSumberPenularan2ActionPerformed

    private void BtnSumberPenularan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSumberPenularan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSumberPenularan2KeyPressed

    private void BtnSumberPenularan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSumberPenularan3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSumberPenularan3ActionPerformed

    private void BtnSumberPenularan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSumberPenularan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSumberPenularan3KeyPressed

    private void BtnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPropinsiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPropinsiActionPerformed

    private void BtnPropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPropinsiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPropinsiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            CoronaPasien dialog = new CoronaPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnJK;
    private widget.Button BtnKeluar;
    private widget.Button BtnKewarganegaraan;
    private widget.Button BtnPrint;
    private widget.Button BtnPropinsi;
    private widget.Button BtnSimpan;
    private widget.Button BtnStatusIsolasi;
    private widget.Button BtnStatusKeluar;
    private widget.Button BtnStatusRawat;
    private widget.Button BtnSumberPenularan;
    private widget.Button BtnSumberPenularan1;
    private widget.Button BtnSumberPenularan2;
    private widget.Button BtnSumberPenularan3;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPReg;
    private widget.Tanggal DTPReg1;
    private widget.Tanggal DTPReg2;
    private widget.Tanggal DTPReg3;
    private widget.TextBox Email;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KodeKabupaten;
    private widget.TextBox KodeKewarganegaraan;
    private widget.TextBox KodePenularan;
    private widget.TextBox KodePenularan1;
    private widget.TextBox KodePenularan2;
    private widget.TextBox KodePropinsi;
    private widget.TextBox KodeStatusIsolasi;
    private widget.TextBox KodeStatusKeluar;
    private widget.TextBox KodeStatusRawat;
    private widget.Label LCount;
    private widget.TextBox Lahir;
    private widget.TextBox Lahir3;
    private widget.TextBox NamaKabupaten;
    private widget.TextBox NamaKewarganegaraan;
    private widget.TextBox NamaPenularan;
    private widget.TextBox NamaPenularan1;
    private widget.TextBox NamaPenularan2;
    private widget.TextBox NamaPropinsi;
    private widget.TextBox NamaStatusIsolasi;
    private widget.TextBox NamaStatusKeluar;
    private widget.TextBox NamaStatusRawat;
    private widget.TextBox NoKTP;
    private widget.TextBox NoTelp;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TPasien;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdjk;
    private widget.TextBox nmjk;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {     
        try {
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                    "select skrining_rawat_jalan.tanggal,skrining_rawat_jalan.jam,skrining_rawat_jalan.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"+
                    "pasien.nm_ibu,pasien.jk,skrining_rawat_jalan.geriatri,skrining_rawat_jalan.kesadaran,skrining_rawat_jalan.pernapasan,"+
                    "skrining_rawat_jalan.nyeri_dada,skrining_rawat_jalan.skala_nyeri,skrining_rawat_jalan.keputusan,skrining_rawat_jalan.nip,petugas.nama "+
                    "from skrining_rawat_jalan inner join pasien inner join petugas on skrining_rawat_jalan.no_rkm_medis=pasien.no_rkm_medis and "+
                    "skrining_rawat_jalan.nip=petugas.nip where "+
                    "skrining_rawat_jalan.tanggal between ? and ? and skrining_rawat_jalan.no_rkm_medis like ? or "+
                    "skrining_rawat_jalan.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                    "skrining_rawat_jalan.tanggal between ? and ? and pasien.nm_ibu like ? or "+
                    "skrining_rawat_jalan.tanggal between ? and ? and skrining_rawat_jalan.kesadaran like ? or "+
                    "skrining_rawat_jalan.tanggal between ? and ? and skrining_rawat_jalan.pernapasan like ? or "+
                    "skrining_rawat_jalan.tanggal between ? and ? and skrining_rawat_jalan.nyeri_dada like ? or "+
                    "skrining_rawat_jalan.tanggal between ? and ? and skrining_rawat_jalan.keputusan like ? or "+
                    "skrining_rawat_jalan.tanggal between ? and ? and skrining_rawat_jalan.nip like ? or "+
                    "skrining_rawat_jalan.tanggal between ? and ? and petugas.nama like ? order by skrining_rawat_jalan.tanggal desc");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("tanggal"),rs.getString("jam"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),
                        rs.getString("nm_ibu"),rs.getString("jk"),rs.getString("geriatri"),rs.getString("kesadaran"),rs.getString("pernapasan"),
                        rs.getString("nyeri_dada"),rs.getString("skala_nyeri"),rs.getString("keputusan"),rs.getString("nip"),rs.getString("nama")
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    public void emptTeks() {
        TNoRM.setText("");
        TPasien.setText("");
        Lahir.setText("");
        TNoRM.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){       
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,276));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpasien_corona());
        BtnHapus.setEnabled(akses.getpasien_corona());
        BtnEdit.setEnabled(akses.getpasien_corona());
        if(akses.getjml2()>=1){
            kdjk.setEditable(false);
            BtnJK.setEnabled(false);
            kdjk.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmjk,kdjk.getText());
        }   
    }

    

}

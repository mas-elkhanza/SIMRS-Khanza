/*
 * Kontribusi RS Handayani Lampung
 */


package rekammedis;
import java.awt.Color;
import fungsi.WarnaTablePEWSD;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPemantauanEWSD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPemantauanEWSD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Umur","JK","Tanggal","Skor Respirasi","Skor 1","Skor Oksigen","Skor 2","Skor Suplemen Oksigen","Skor 3",
            "Skor Tekanan Darah","Skor 4","Skor Laju Jantung","Skor 5","Skor Kesadaran","Skor 6","Skor Temperatur","Skor 7","Ttl.Skor","Kesimpulan","NIP","Petugas","Tgl.Lahir"
        }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable;
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 25; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                 column.setPreferredWidth(105);
            }else if(i==1){
                 column.setPreferredWidth(65);
            }else if(i==2){
                 column.setPreferredWidth(160);
            }else if(i==3){
                 column.setPreferredWidth(35);
            }else if(i==4){
                 column.setPreferredWidth(20);
            }else if(i==5){
                 column.setPreferredWidth(120);
            }else if(i==6){
                 column.setPreferredWidth(240);
            }else if(i==7){
                 column.setPreferredWidth(39);
            }else if(i==8){
                 column.setPreferredWidth(122);
            }else if(i==9){
                 column.setPreferredWidth(39);
            }else if(i==10){
                 column.setPreferredWidth(145);
            }else if(i==11){
                 column.setPreferredWidth(39);
            }else if(i==12){
                 column.setPreferredWidth(145);
            }else if(i==13){
                 column.setPreferredWidth(39);
            }else if(i==14){
                 column.setPreferredWidth(145);
            }else if(i==15){
                 column.setPreferredWidth(39);
            }else if(i==16){
                 column.setPreferredWidth(145);
            }else if(i==17){
                 column.setPreferredWidth(39);
            }else if(i==18){
                 column.setPreferredWidth(145);     
            }else if(i==19){
                 column.setPreferredWidth(39);
            }else if(i==20){
                 column.setPreferredWidth(45);
            }else if(i==21){
                 column.setPreferredWidth(450);
            }else if(i==22){
                 column.setPreferredWidth(80);
            }else if(i==23){
                 column.setPreferredWidth(150);
            }else{
                 column.setMinWidth(0);
                 column.setMaxWidth(0);
            } 
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTablePEWSD());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                KdPetugas.requestFocus();
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
        jam();
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
        MnPemantauanPEWS = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
        Umur = new widget.TextBox();
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
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        NmPetugas = new widget.TextBox();
        KdPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel14 = new widget.Label();
        cmbSkor1 = new widget.ComboBox();
        jLabel17 = new widget.Label();
        cmbSkor2 = new widget.ComboBox();
        Skor1 = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        Skor2 = new widget.TextBox();
        jLabel23 = new widget.Label();
        cmbSkor3 = new widget.ComboBox();
        jLabel26 = new widget.Label();
        Skor3 = new widget.TextBox();
        jLabel27 = new widget.Label();
        TotalSkor = new widget.TextBox();
        ParameterSkor = new widget.TextBox();
        jLabel28 = new widget.Label();
        cmbSkor4 = new widget.ComboBox();
        jLabel24 = new widget.Label();
        cmbSkor5 = new widget.ComboBox();
        jLabel25 = new widget.Label();
        cmbSkor6 = new widget.ComboBox();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        Skor4 = new widget.TextBox();
        Skor5 = new widget.TextBox();
        Skor6 = new widget.TextBox();
        jLabel33 = new widget.Label();
        cmbSkor7 = new widget.ComboBox();
        Skor7 = new widget.TextBox();
        jLabel34 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPemantauanPEWS.setBackground(new java.awt.Color(255, 255, 254));
        MnPemantauanPEWS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemantauanPEWS.setForeground(new java.awt.Color(50, 50, 50));
        MnPemantauanPEWS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemantauanPEWS.setText("Lembar Pemantauan EWS");
        MnPemantauanPEWS.setName("MnPemantauanPEWS"); // NOI18N
        MnPemantauanPEWS.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPemantauanPEWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemantauanPEWSActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemantauanPEWS);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pemantauan EWS Pasien Dewasa ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(559, 334));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(300, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-06-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-06-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(315, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 265));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 780));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-06-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(571, 40, 187, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 40, 94, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(761, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("1. Laju Respirasi/Menit");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(40, 90, 140, 23);

        cmbSkor1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<= 5", "6 - 8", "9 - 11", "12 - 20", "21 - 24", "25 - 34", ">= 35" }));
        cmbSkor1.setSelectedIndex(3);
        cmbSkor1.setName("cmbSkor1"); // NOI18N
        cmbSkor1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor1ItemStateChanged(evt);
            }
        });
        cmbSkor1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor1KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor1);
        cmbSkor1.setBounds(180, 90, 115, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("2. Saturasi Oksigen");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(40, 120, 140, 23);

        cmbSkor2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ">= 95", "94 - 95", "92 - 93", "<= 92" }));
        cmbSkor2.setName("cmbSkor2"); // NOI18N
        cmbSkor2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor2ItemStateChanged(evt);
            }
        });
        cmbSkor2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor2KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor2);
        cmbSkor2.setBounds(180, 120, 115, 23);

        Skor1.setEditable(false);
        Skor1.setText("0");
        Skor1.setFocusTraversalPolicyProvider(true);
        Skor1.setName("Skor1"); // NOI18N
        Skor1.setOpaque(true);
        FormInput.add(Skor1);
        Skor1.setBounds(345, 90, 44, 23);

        jLabel20.setText("Skor :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(290, 90, 50, 23);

        jLabel22.setText("Skor :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(290, 120, 50, 23);

        Skor2.setEditable(false);
        Skor2.setText("0");
        Skor2.setFocusTraversalPolicyProvider(true);
        Skor2.setName("Skor2"); // NOI18N
        Skor2.setOpaque(true);
        FormInput.add(Skor2);
        Skor2.setBounds(345, 120, 44, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("7. Temperatur");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(460, 150, 120, 23);

        cmbSkor3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        cmbSkor3.setName("cmbSkor3"); // NOI18N
        cmbSkor3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor3ItemStateChanged(evt);
            }
        });
        cmbSkor3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor3KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor3);
        cmbSkor3.setBounds(180, 150, 115, 23);

        jLabel26.setText("Skor :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(690, 150, 50, 23);

        Skor3.setEditable(false);
        Skor3.setText("0");
        Skor3.setFocusTraversalPolicyProvider(true);
        Skor3.setName("Skor3"); // NOI18N
        Skor3.setOpaque(true);
        FormInput.add(Skor3);
        Skor3.setBounds(345, 150, 44, 23);

        jLabel27.setText("Total Skor :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 210, 75, 23);

        TotalSkor.setEditable(false);
        TotalSkor.setText("0");
        TotalSkor.setFocusTraversalPolicyProvider(true);
        TotalSkor.setName("TotalSkor"); // NOI18N
        FormInput.add(TotalSkor);
        TotalSkor.setBounds(79, 210, 40, 23);

        ParameterSkor.setEditable(false);
        ParameterSkor.setFocusTraversalPolicyProvider(true);
        ParameterSkor.setName("ParameterSkor"); // NOI18N
        FormInput.add(ParameterSkor);
        ParameterSkor.setBounds(121, 210, 668, 23);

        jLabel28.setText("Parameter :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 70, 75, 23);

        cmbSkor4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ">= 220", "181 - 220", "111 - 180", "101 - 110", "91 - 100", "71 - 90", "<= 70" }));
        cmbSkor4.setSelectedIndex(2);
        cmbSkor4.setName("cmbSkor4"); // NOI18N
        cmbSkor4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor4ItemStateChanged(evt);
            }
        });
        cmbSkor4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor4KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor4);
        cmbSkor4.setBounds(180, 180, 115, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("3. Suplemen Oksigen");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(40, 150, 140, 23);

        cmbSkor5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ">= 140", "131 - 140", "111 - 130", "91 - 110", "51 - 90", "41 - 50", "<= 40" }));
        cmbSkor5.setSelectedIndex(4);
        cmbSkor5.setName("cmbSkor5"); // NOI18N
        cmbSkor5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor5ItemStateChanged(evt);
            }
        });
        cmbSkor5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor5KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor5);
        cmbSkor5.setBounds(580, 90, 115, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("4. Tekanan Darah Sistolik");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(40, 180, 140, 23);

        cmbSkor6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sadar", "Nyeri/Verbal", "Unrespon" }));
        cmbSkor6.setName("cmbSkor6"); // NOI18N
        cmbSkor6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor6ItemStateChanged(evt);
            }
        });
        cmbSkor6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor6KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor6);
        cmbSkor6.setBounds(580, 120, 115, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("5. Laju Jatung/Menit");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(460, 90, 120, 23);

        jLabel30.setText("Skor :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(290, 150, 50, 23);

        jLabel31.setText("Skor :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(290, 180, 50, 23);

        jLabel32.setText("Skor :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(690, 90, 50, 23);

        Skor4.setEditable(false);
        Skor4.setText("0");
        Skor4.setFocusTraversalPolicyProvider(true);
        Skor4.setName("Skor4"); // NOI18N
        Skor4.setOpaque(true);
        FormInput.add(Skor4);
        Skor4.setBounds(345, 180, 44, 23);

        Skor5.setEditable(false);
        Skor5.setText("0");
        Skor5.setFocusTraversalPolicyProvider(true);
        Skor5.setName("Skor5"); // NOI18N
        Skor5.setOpaque(true);
        FormInput.add(Skor5);
        Skor5.setBounds(745, 90, 44, 23);

        Skor6.setEditable(false);
        Skor6.setText("0");
        Skor6.setFocusTraversalPolicyProvider(true);
        Skor6.setName("Skor6"); // NOI18N
        Skor6.setOpaque(true);
        FormInput.add(Skor6);
        Skor6.setBounds(745, 120, 44, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("6. Kesadaran");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(460, 120, 120, 23);

        cmbSkor7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<= 35", "35.1 - 36", "36.1 - 38", "38.1 - 39", ">= 39" }));
        cmbSkor7.setSelectedIndex(2);
        cmbSkor7.setName("cmbSkor7"); // NOI18N
        cmbSkor7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkor7ItemStateChanged(evt);
            }
        });
        cmbSkor7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbSkor7KeyPressed(evt);
            }
        });
        FormInput.add(cmbSkor7);
        cmbSkor7.setBounds(580, 150, 115, 23);

        Skor7.setEditable(false);
        Skor7.setText("0");
        Skor7.setFocusTraversalPolicyProvider(true);
        Skor7.setName("Skor7"); // NOI18N
        Skor7.setOpaque(true);
        FormInput.add(Skor7);
        Skor7.setBounds(745, 150, 44, 23);

        jLabel34.setText("Skor :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(690, 120, 50, 23);

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

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(Skor1.getText().trim().equals("")){
            Valid.textKosong(Skor1,"Skor 1");
        }else if(Skor2.getText().trim().equals("")){
            Valid.textKosong(Skor2,"Skor 2");
        }else if(Skor3.getText().trim().equals("")){
            Valid.textKosong(Skor3,"Skor 3");
        }else if(Skor4.getText().trim().equals("")){
            Valid.textKosong(Skor4,"Skor 4");
        }else if(Skor5.getText().trim().equals("")){
            Valid.textKosong(Skor5,"Skor 5");
        }else if(Skor6.getText().trim().equals("")){
            Valid.textKosong(Skor6,"Skor 6");
        }else if(Skor7.getText().trim().equals("")){
            Valid.textKosong(Skor1,"Skor 7");    
        }else{
            isCombo1();
            isCombo2();
            isCombo3();
            isCombo4();
            isCombo5();
            isCombo6();
            isCombo7();
            isjml();
            isHitung();
            if(Sequel.menyimpantf("pemantauan_pews_dewasa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",19,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                cmbSkor1.getSelectedItem().toString(),Skor1.getText(),cmbSkor2.getSelectedItem().toString(),Skor2.getText(),
                cmbSkor3.getSelectedItem().toString(),Skor3.getText(),cmbSkor4.getSelectedItem().toString(),Skor4.getText(),
                cmbSkor5.getSelectedItem().toString(),Skor5.getText(),cmbSkor6.getSelectedItem().toString(),Skor6.getText(),
                cmbSkor7.getSelectedItem().toString(),Skor7.getText(),TotalSkor.getText(),ParameterSkor.getText(),KdPetugas.getText()
            })==true){
                tabMode.addRow(new String[]{
                   TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    cmbSkor1.getSelectedItem().toString(),Skor1.getText(),cmbSkor2.getSelectedItem().toString(),Skor2.getText(),cmbSkor3.getSelectedItem().toString(),Skor3.getText(),cmbSkor4.getSelectedItem().toString(),Skor4.getText(),
                    cmbSkor5.getSelectedItem().toString(),Skor5.getText(),cmbSkor6.getSelectedItem().toString(),Skor6.getText(),cmbSkor7.getSelectedItem().toString(),Skor7.getText(),TotalSkor.getText(),ParameterSkor.getText(),
                    KdPetugas.getText(),NmPetugas.getText(),TglLahir.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }   
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,cmbSkor6,BtnBatal);
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
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }          
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
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
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            if(TCari.getText().trim().equals("")){
              Valid.MyReportqry("rptDataPemantauanPEWSDewasa.jasper","report","::[ Data Pemantauan PEWS Dewasa ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pemantauan_pews_dewasa.tanggal,pemantauan_pews_dewasa.parameter_laju_respirasi,pemantauan_pews_dewasa.skor_laju_respirasi,pemantauan_pews_dewasa.parameter_saturasi_oksigen,"+
                    "pemantauan_pews_dewasa.skor_saturasi_oksigen,pemantauan_pews_dewasa.parameter_suplemen_oksigen,pemantauan_pews_dewasa.skor_suplemen_oksigen,pemantauan_pews_dewasa.parameter_tekanan_darah_sistolik,"+
                    "pemantauan_pews_dewasa.skor_tekanan_darah_sistolik,pemantauan_pews_dewasa.parameter_laju_jantung,pemantauan_pews_dewasa.skor_laju_jantung,pemantauan_pews_dewasa.parameter_kesadaran,"+
                    "pemantauan_pews_dewasa.skor_kesadaran,pemantauan_pews_dewasa.parameter_temperatur,pemantauan_pews_dewasa.skor_temperatur,pemantauan_pews_dewasa.skor_total,pemantauan_pews_dewasa.parameter_total,pemantauan_pews_dewasa.nip,petugas.nama,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from pemantauan_pews_dewasa inner join reg_periksa on pemantauan_pews_dewasa.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_dewasa.nip=petugas.nip where "+
                    "pemantauan_pews_dewasa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' order by pemantauan_pews_dewasa.tanggal ",param);
            }else{
                Valid.MyReportqry("rptDataPemantauanPEWSDewasa.jasper","report","::[ Data Pemantauan PEWS Dewasa ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pemantauan_pews_dewasa.tanggal,pemantauan_pews_dewasa.parameter_laju_respirasi,pemantauan_pews_dewasa.skor_laju_respirasi,pemantauan_pews_dewasa.parameter_saturasi_oksigen,"+
                    "pemantauan_pews_dewasa.skor_saturasi_oksigen,pemantauan_pews_dewasa.parameter_suplemen_oksigen,pemantauan_pews_dewasa.skor_suplemen_oksigen,pemantauan_pews_dewasa.parameter_tekanan_darah_sistolik,"+
                    "pemantauan_pews_dewasa.skor_tekanan_darah_sistolik,pemantauan_pews_dewasa.parameter_laju_jantung,pemantauan_pews_dewasa.skor_laju_jantung,pemantauan_pews_dewasa.parameter_kesadaran,"+
                    "pemantauan_pews_dewasa.skor_kesadaran,pemantauan_pews_dewasa.parameter_temperatur,pemantauan_pews_dewasa.skor_temperatur,pemantauan_pews_dewasa.skor_total,pemantauan_pews_dewasa.parameter_total,pemantauan_pews_dewasa.nip,petugas.nama,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from pemantauan_pews_dewasa inner join reg_periksa on pemantauan_pews_dewasa.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_dewasa.nip=petugas.nip "+
                    "where pemantauan_pews_dewasa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and "+
                    "(reg_periksa.no_rawat like '%"+TCari.getText()+"%' or pasien.no_rkm_medis like '%"+TCari.getText()+"%' or pasien.nm_pasien like '%"+TCari.getText()+"%' or pemantauan_pews_dewasa.nip like '%"+TCari.getText()+"%' or petugas.nama like '%"+TCari.getText()+"%') "+
                    "order by pemantauan_pews_dewasa.tanggal ",param);
            }  
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

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

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Detik,cmbSkor1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnPemantauanPEWSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemantauanPEWSActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("diagnosa",Sequel.cariIsi("select kamar_inap.diagnosa_awal from kamar_inap where kamar_inap.diagnosa_awal<>'' and kamar_inap.no_rawat=? ",TNoRw.getText()));
            Valid.MyReportqry("rptFormulirPemantauanPEWSD.jasper","report","::[ Pemantauan EWS Dewasa ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pemantauan_pews_dewasa.tanggal,pemantauan_pews_dewasa.parameter_laju_respirasi,pemantauan_pews_dewasa.skor_laju_respirasi,pemantauan_pews_dewasa.parameter_saturasi_oksigen,"+
                    "pemantauan_pews_dewasa.skor_saturasi_oksigen,pemantauan_pews_dewasa.parameter_suplemen_oksigen,pemantauan_pews_dewasa.skor_suplemen_oksigen,pemantauan_pews_dewasa.parameter_tekanan_darah_sistolik,"+
                    "pemantauan_pews_dewasa.skor_tekanan_darah_sistolik,pemantauan_pews_dewasa.parameter_laju_jantung,pemantauan_pews_dewasa.skor_laju_jantung,pemantauan_pews_dewasa.parameter_kesadaran,"+
                    "pemantauan_pews_dewasa.skor_kesadaran,pemantauan_pews_dewasa.parameter_temperatur,pemantauan_pews_dewasa.skor_temperatur,pemantauan_pews_dewasa.skor_total,pemantauan_pews_dewasa.parameter_total,pemantauan_pews_dewasa.nip,petugas.nama,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from pemantauan_pews_dewasa inner join reg_periksa on pemantauan_pews_dewasa.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_dewasa.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPemantauanPEWSActionPerformed

    private void cmbSkor1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor1KeyPressed
        Valid.pindah(evt,btnPetugas,cmbSkor2);
    }//GEN-LAST:event_cmbSkor1KeyPressed

    private void cmbSkor2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor2KeyPressed
        Valid.pindah(evt, cmbSkor1, cmbSkor3);
    }//GEN-LAST:event_cmbSkor2KeyPressed

    private void cmbSkor3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor3KeyPressed
        Valid.pindah(evt, cmbSkor2,cmbSkor4);
    }//GEN-LAST:event_cmbSkor3KeyPressed

    private void cmbSkor1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor1ItemStateChanged
        isCombo1();
        isjml();
        isHitung();
    }//GEN-LAST:event_cmbSkor1ItemStateChanged

    private void cmbSkor2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor2ItemStateChanged
        isCombo2();
        isjml();
        isHitung();
    }//GEN-LAST:event_cmbSkor2ItemStateChanged

    private void cmbSkor3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor3ItemStateChanged
        isCombo3();
        isjml();
        isHitung();
    }//GEN-LAST:event_cmbSkor3ItemStateChanged

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else if(Skor1.getText().trim().equals("")){
            Valid.textKosong(Skor1,"Skor 1");
        }else if(Skor2.getText().trim().equals("")){
            Valid.textKosong(Skor2,"Skor 2");
        }else if(Skor3.getText().trim().equals("")){
            Valid.textKosong(Skor3,"Skor 3");
        }else if(Skor4.getText().trim().equals("")){
            Valid.textKosong(Skor4,"Skor 4");
        }else if(Skor5.getText().trim().equals("")){
            Valid.textKosong(Skor5,"Skor 5");
        }else if(Skor6.getText().trim().equals("")){
            Valid.textKosong(Skor6,"Skor 6");
        }else if(Skor7.getText().trim().equals("")){
            Valid.textKosong(Skor1,"Skor 7");    
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            } 
        } 
    }//GEN-LAST:event_BtnEditActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbObat.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void cmbSkor4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor4ItemStateChanged
        isCombo4();
        isjml();
        isHitung();    
    }//GEN-LAST:event_cmbSkor4ItemStateChanged

    private void cmbSkor4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor4KeyPressed
        Valid.pindah(evt, cmbSkor3,cmbSkor5);    // TODO add your handling code here:
    }//GEN-LAST:event_cmbSkor4KeyPressed

    private void cmbSkor5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor5ItemStateChanged
        isCombo5();
        isjml();
        isHitung();    
    }//GEN-LAST:event_cmbSkor5ItemStateChanged

    private void cmbSkor5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor5KeyPressed
        Valid.pindah(evt, cmbSkor4,cmbSkor6);   
    }//GEN-LAST:event_cmbSkor5KeyPressed

    private void cmbSkor6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor6ItemStateChanged
        isCombo6();
        isjml();
        isHitung();
    }//GEN-LAST:event_cmbSkor6ItemStateChanged

    private void cmbSkor6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor6KeyPressed
        Valid.pindah(evt, cmbSkor5,cmbSkor7);
    }//GEN-LAST:event_cmbSkor6KeyPressed

    private void cmbSkor7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkor7ItemStateChanged
        isCombo7();
        isjml();
        isHitung();
    }//GEN-LAST:event_cmbSkor7ItemStateChanged

    private void cmbSkor7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSkor7KeyPressed
        Valid.pindah(evt, cmbSkor6,BtnSimpan);
    }//GEN-LAST:event_cmbSkor7KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPemantauanEWSD dialog = new RMPemantauanEWSD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnPemantauanPEWS;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox ParameterSkor;
    private widget.ScrollPane Scroll;
    private widget.TextBox Skor1;
    private widget.TextBox Skor2;
    private widget.TextBox Skor3;
    private widget.TextBox Skor4;
    private widget.TextBox Skor5;
    private widget.TextBox Skor6;
    private widget.TextBox Skor7;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalSkor;
    private widget.TextBox Umur;
    private widget.Button btnPetugas;
    private widget.ComboBox cmbSkor1;
    private widget.ComboBox cmbSkor2;
    private widget.ComboBox cmbSkor3;
    private widget.ComboBox cmbSkor4;
    private widget.ComboBox cmbSkor5;
    private widget.ComboBox cmbSkor6;
    private widget.ComboBox cmbSkor7;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel14;
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
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pemantauan_pews_dewasa.tanggal,pemantauan_pews_dewasa.parameter_laju_respirasi,pemantauan_pews_dewasa.skor_laju_respirasi,pemantauan_pews_dewasa.parameter_saturasi_oksigen,"+
                    "pemantauan_pews_dewasa.skor_saturasi_oksigen,pemantauan_pews_dewasa.parameter_suplemen_oksigen,pemantauan_pews_dewasa.skor_suplemen_oksigen,pemantauan_pews_dewasa.parameter_tekanan_darah_sistolik,"+
                    "pemantauan_pews_dewasa.skor_tekanan_darah_sistolik,pemantauan_pews_dewasa.parameter_laju_jantung,pemantauan_pews_dewasa.skor_laju_jantung,pemantauan_pews_dewasa.parameter_kesadaran,"+
                    "pemantauan_pews_dewasa.skor_kesadaran,pemantauan_pews_dewasa.parameter_temperatur,pemantauan_pews_dewasa.skor_temperatur,pemantauan_pews_dewasa.skor_total,pemantauan_pews_dewasa.parameter_total,pemantauan_pews_dewasa.nip,petugas.nama,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from pemantauan_pews_dewasa inner join reg_periksa on pemantauan_pews_dewasa.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_dewasa.nip=petugas.nip where "+
                    "pemantauan_pews_dewasa.tanggal between ? and ? order by pemantauan_pews_dewasa.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk,pemantauan_pews_dewasa.tanggal,pemantauan_pews_dewasa.parameter_laju_respirasi,pemantauan_pews_dewasa.skor_laju_respirasi,pemantauan_pews_dewasa.parameter_saturasi_oksigen,"+
                    "pemantauan_pews_dewasa.skor_saturasi_oksigen,pemantauan_pews_dewasa.parameter_suplemen_oksigen,pemantauan_pews_dewasa.skor_suplemen_oksigen,pemantauan_pews_dewasa.parameter_tekanan_darah_sistolik,"+
                    "pemantauan_pews_dewasa.skor_tekanan_darah_sistolik,pemantauan_pews_dewasa.parameter_laju_jantung,pemantauan_pews_dewasa.skor_laju_jantung,pemantauan_pews_dewasa.parameter_kesadaran,"+
                    "pemantauan_pews_dewasa.skor_kesadaran,pemantauan_pews_dewasa.parameter_temperatur,pemantauan_pews_dewasa.skor_temperatur,pemantauan_pews_dewasa.skor_total,pemantauan_pews_dewasa.parameter_total,pemantauan_pews_dewasa.nip,petugas.nama,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    "from pemantauan_pews_dewasa inner join reg_periksa on pemantauan_pews_dewasa.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on pemantauan_pews_dewasa.nip=petugas.nip "+
                    "where pemantauan_pews_dewasa.tanggal between ? and ? and "+
                    "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or pemantauan_pews_dewasa.nip like ? or petugas.nama like ?) "+
                    "order by pemantauan_pews_dewasa.tanggal ");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("parameter_laju_respirasi"),rs.getString("skor_laju_respirasi"),
                        rs.getString("parameter_saturasi_oksigen"),rs.getString("skor_saturasi_oksigen"),rs.getString("parameter_suplemen_oksigen"),
                        rs.getString("skor_suplemen_oksigen"),rs.getString("parameter_tekanan_darah_sistolik"),rs.getString("skor_tekanan_darah_sistolik"),
                        rs.getString("parameter_laju_jantung"),rs.getString("skor_laju_jantung"),rs.getString("parameter_kesadaran"),
                        rs.getString("skor_kesadaran"),rs.getString("parameter_temperatur"),rs.getString("skor_temperatur"),rs.getString("skor_total"),
                        rs.getString("parameter_total"),rs.getString("nip"),rs.getString("nama"),rs.getString("lahir")
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    private void isCombo1(){
        if(cmbSkor1.getSelectedItem().equals("<= 5")){
            Skor1.setBackground(Color.BLUE);
            Skor1.setForeground(Color.WHITE);
            Skor1.setText("7");
        }else if(cmbSkor1.getSelectedItem().equals("6 - 8")){
            Skor1.setBackground(Color.RED);
            Skor1.setForeground(Color.WHITE);
            Skor1.setText("3");
        }else if(cmbSkor1.getSelectedItem().equals("9 - 11")){
            Skor1.setBackground(Color.YELLOW);
            Skor1.setForeground(Color.GREEN);
            Skor1.setText("1");
        }else if(cmbSkor1.getSelectedItem().equals("12 - 20")){
            Skor1.setBackground(Color.WHITE);
            Skor1.setForeground(new Color(50,50,50));
            Skor1.setText("0");
        }else if(cmbSkor1.getSelectedItem().equals("21 - 24")){
            Skor1.setBackground(Color.ORANGE);
            Skor1.setForeground(Color.WHITE);
            Skor1.setText("2");
        }else if(cmbSkor1.getSelectedItem().equals("25 - 34")){
            Skor1.setBackground(Color.RED);
            Skor1.setForeground(Color.WHITE);
            Skor1.setText("3");
        }else if(cmbSkor1.getSelectedItem().equals(">= 35")){
            Skor1.setBackground(Color.BLUE);
            Skor1.setForeground(Color.WHITE);
            Skor1.setText("7");    
        }
    }
    
    private void isCombo2(){
        if(cmbSkor2.getSelectedItem().equals(">= 95")){
            Skor2.setBackground(Color.WHITE);
            Skor2.setForeground(new Color(50,50,50));
            Skor2.setText("0");
        }else if(cmbSkor2.getSelectedItem().equals("94 - 95")){
            Skor2.setBackground(Color.YELLOW);
            Skor2.setForeground(Color.GREEN);
            Skor2.setText("1");
        }else if(cmbSkor2.getSelectedItem().equals("92 - 93")){
            Skor2.setBackground(Color.ORANGE);
            Skor2.setForeground(Color.WHITE); 
            Skor2.setText("2");
        }else if(cmbSkor2.getSelectedItem().equals("<= 92")){
            Skor2.setBackground(Color.RED);
            Skor2.setForeground(Color.WHITE); 
            Skor2.setText("3");    
        }
    }
    
    private void isCombo3(){
        if(cmbSkor3.getSelectedItem().equals("Ya")){
            Skor3.setBackground(Color.RED);
            Skor3.setForeground(Color.WHITE);
            Skor3.setText("2");   
        }else{
            Skor3.setBackground(Color.WHITE);
            Skor3.setForeground(new Color(50,50,50));
            Skor3.setText("0");
        }
    }
    
    private void isCombo4(){
        if(cmbSkor4.getSelectedItem().equals(">= 220")){
            Skor4.setBackground(Color.RED);
            Skor4.setForeground(Color.WHITE);
            Skor4.setText("3");
        }else if(cmbSkor4.getSelectedItem().equals("181 - 220")){
            Skor4.setBackground(Color.ORANGE);
            Skor4.setForeground(Color.WHITE);
            Skor4.setText("2");
        }else if(cmbSkor4.getSelectedItem().equals("111 - 180")){
            Skor4.setBackground(Color.WHITE);
            Skor4.setForeground(new Color(50,50,50));
            Skor4.setText("0");
        }else if(cmbSkor4.getSelectedItem().equals("101 - 110")){
            Skor4.setBackground(Color.YELLOW);
            Skor4.setForeground(Color.GREEN);
            Skor4.setText("1");
        }else if(cmbSkor4.getSelectedItem().equals("91 - 100")){
            Skor4.setBackground(Color.ORANGE);
            Skor4.setForeground(Color.WHITE);
            Skor4.setText("2");
        }else if(cmbSkor4.getSelectedItem().equals("71 - 90")){
            Skor4.setBackground(Color.RED);
            Skor4.setForeground(Color.WHITE);
            Skor4.setText("3");
        }else if(cmbSkor4.getSelectedItem().equals("<= 70")){
            Skor4.setBackground(Color.BLUE);
            Skor4.setForeground(Color.WHITE);
            Skor4.setText("7");    
        }
    }
    
    private void isCombo5(){
        if(cmbSkor5.getSelectedItem().equals(">= 140")){
            Skor5.setBackground(Color.BLUE);
            Skor5.setForeground(Color.WHITE);
            Skor5.setText("7");
        }else if(cmbSkor5.getSelectedItem().equals("131 - 140")){
            Skor5.setBackground(Color.RED);
            Skor5.setForeground(Color.WHITE);
            Skor5.setText("3");
        }else if(cmbSkor5.getSelectedItem().equals("111 - 130")){
            Skor5.setBackground(Color.ORANGE);
            Skor5.setForeground(Color.WHITE);
            Skor5.setText("2");
        }else if(cmbSkor5.getSelectedItem().equals("91 - 110")){
            Skor5.setBackground(Color.YELLOW);
            Skor5.setForeground(Color.GREEN);
            Skor5.setText("1");
        }else if(cmbSkor5.getSelectedItem().equals("51 - 90")){
            Skor5.setBackground(Color.WHITE);
            Skor5.setForeground(new Color(50,50,50));
            Skor5.setText("0");
        }else if(cmbSkor5.getSelectedItem().equals("41 - 50")){
            Skor5.setBackground(Color.ORANGE);
            Skor5.setForeground(Color.WHITE);
            Skor5.setText("2");
        }else if(cmbSkor5.getSelectedItem().equals("<= 40")){
            Skor5.setBackground(Color.BLUE);
            Skor5.setForeground(Color.WHITE);
            Skor5.setText("7");    
        }
    }
    
    private void isCombo6(){
        if(cmbSkor6.getSelectedItem().equals("Nyeri/Verbal")){
            Skor6.setBackground(Color.RED);
            Skor6.setForeground(Color.WHITE);
            Skor6.setText("3");
        }else if(cmbSkor6.getSelectedItem().equals("Unrespon")){
            Skor6.setBackground(Color.BLUE);
            Skor6.setForeground(Color.WHITE);
            Skor6.setText("7");    
        }else{
            Skor6.setBackground(Color.WHITE);
            Skor6.setForeground(new Color(50,50,50));
            Skor6.setText("0");
        }
    } 
    private void isCombo7(){
        if(cmbSkor7.getSelectedItem().equals("<= 35")){
            Skor7.setBackground(Color.RED);
            Skor7.setForeground(Color.WHITE);
            Skor7.setText("3");
        }else if(cmbSkor7.getSelectedItem().equals("35.1 - 36")){
            Skor7.setBackground(Color.YELLOW);
            Skor7.setForeground(Color.GREEN);
            Skor7.setText("1");
        }else if(cmbSkor7.getSelectedItem().equals("36.1 - 38")){
            Skor7.setBackground(Color.WHITE);
            Skor7.setForeground(new Color(50,50,50));
            Skor7.setText("0");
        }else if(cmbSkor7.getSelectedItem().equals("38.1 - 39")){
            Skor7.setBackground(Color.YELLOW);
            Skor7.setForeground(Color.GREEN);
            Skor7.setText("1");
        }else if(cmbSkor7.getSelectedItem().equals(">= 39")){
            Skor7.setBackground(Color.ORANGE);
            Skor7.setForeground(Color.WHITE);
            Skor7.setText("2");    
        }
    }     
    
    private void isjml(){
        if((!Skor1.getText().equals(""))&&(!Skor2.getText().equals(""))&&(!Skor3.getText().equals(""))&&(!Skor4.getText().equals(""))&&(!Skor5.getText().equals(""))&&(!Skor6.getText().equals(""))&&(!Skor7.getText().equals(""))){
            TotalSkor.setText(Valid.SetAngka2(
                    Double.parseDouble(Skor1.getText().trim())+
                    Double.parseDouble(Skor2.getText().trim())+
                    Double.parseDouble(Skor3.getText().trim())+
                    Double.parseDouble(Skor4.getText().trim())+
                    Double.parseDouble(Skor5.getText().trim())+
                    Double.parseDouble(Skor6.getText().trim())+
                    Double.parseDouble(Skor7.getText().trim())
            ));
        }
    }
    
    private void isHitung(){
        i=0;
        if(cmbSkor1.getSelectedItem().equals("<= 5")||cmbSkor1.getSelectedItem().equals(">= 35")){
            i++;
        }
        if(cmbSkor4.getSelectedItem().equals("<= 70")){
            i++;
        }
        if(cmbSkor5.getSelectedItem().equals(">= 140")||cmbSkor5.getSelectedItem().equals("<= 40")){
            i++;
        }
        if(cmbSkor6.getSelectedItem().equals("Unrespon")){
            i++;
        }
        
        if(Integer.parseInt(TotalSkor.getText())>8){
            ParameterSkor.setText("Lakukan RJP oleh petugas/tim primer, aktivasi code blue henti jantung (...), respon Tim Medis Emergency (TME) segera, maksimal 5 menit, informasikan dan konsultasikan ke DPJP");
        }else if((Integer.parseInt(TotalSkor.getText())>=7)||(i>0)){
            ParameterSkor.setText("Resusitasi dan monitoring secara kontinyu ole dokter jaga dan perawat senior, aktivasi code blue kegawatan medis (....) respon Tim Medis Emergency (TME) segera, maksimal 10 menit, informasikr dan konsultasikan ke DPJP");
        }else if(Integer.parseInt(TotalSkor.getText())>=5){
            ParameterSkor.setText("Asesment segera oleh dokter jaga (respon segera, maks 5 menit) konsultasi DPJP dan spesialis terkait, eskalasi perawatan dan monitoring tap jam, pertimbangkan perawatan dengan monitoring yang sesua (HCU)");
        }else if(Integer.parseInt(TotalSkor.getText())>0){
            ParameterSkor.setText("Assement segera oleh perawat senior, respon segera, maks 5 menit, eskalasi perawat dan frekuensi monitoring per 4-6 jam, jika diperlukan assesment oleh dokter jaga bangsal");
        }else if(Integer.parseInt(TotalSkor.getText())==0){
            ParameterSkor.setText("Beresiko rendah, ulangi setiap 7 jam");
        }
    }
    
    public void emptTeks() {
        Tanggal.setDate(new Date());
        cmbSkor1.setSelectedIndex(3);
        Skor1.setText("0");
        Skor1.setBackground(Color.WHITE);
        Skor1.setForeground(new Color(50,50,50));
        cmbSkor2.setSelectedIndex(0);
        Skor2.setText("0");
        Skor2.setBackground(Color.WHITE);
        Skor2.setForeground(new Color(50,50,50));
        cmbSkor3.setSelectedIndex(0);
        Skor3.setText("0");
        Skor3.setBackground(Color.WHITE);
        Skor3.setForeground(new Color(50,50,50));
        cmbSkor4.setSelectedIndex(2);
        Skor4.setText("0");
        Skor4.setBackground(Color.WHITE);
        Skor4.setForeground(new Color(50,50,50));
        cmbSkor5.setSelectedIndex(4);
        Skor5.setText("0");
        Skor5.setBackground(Color.WHITE);
        Skor5.setForeground(new Color(50,50,50));
        cmbSkor6.setSelectedIndex(0);
        Skor6.setText("0");
        Skor6.setBackground(Color.WHITE);
        Skor6.setForeground(new Color(50,50,50));
        cmbSkor7.setSelectedIndex(2);
        Skor7.setText("0");
        Skor7.setBackground(Color.WHITE);
        Skor7.setForeground(new Color(50,50,50));
        TotalSkor.setText("0");
        ParameterSkor.setText("Beresiko rendah, ulangi setiap 7 jam");
        cmbSkor1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            cmbSkor1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Skor1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
            cmbSkor2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Skor2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());  
            cmbSkor3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Skor3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            cmbSkor4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Skor4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            cmbSkor5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Skor5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            cmbSkor6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Skor6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            cmbSkor7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Skor7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            TotalSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());  
            ParameterSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());  
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());  
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(14,16));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(17,19));
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,265));
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
        BtnSimpan.setEnabled(akses.getpemantauan_pews_dewasa());
        BtnHapus.setEnabled(akses.getpemantauan_pews_dewasa());
        BtnEdit.setEnabled(akses.getpemantauan_pews_dewasa());
        BtnPrint.setEnabled(akses.getpemantauan_pews_dewasa()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    
    }
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    

    private void ganti() {
        isCombo1();
        isCombo2();
        isCombo3();
        isCombo4();
        isCombo5();
        isCombo6();
        isCombo7();
        isjml();
        isHitung();
        if(Sequel.mengedittf("pemantauan_pews_dewasa","tanggal=? and no_rawat=?","no_rawat=?,tanggal=?,parameter_laju_respirasi=?,skor_laju_respirasi=?,parameter_saturasi_oksigen=?,"+
                "skor_saturasi_oksigen=?,parameter_suplemen_oksigen=?,skor_suplemen_oksigen=?,parameter_tekanan_darah_sistolik=?,skor_tekanan_darah_sistolik=?,"+
                "parameter_laju_jantung=?,skor_laju_jantung=?,parameter_kesadaran=?,skor_kesadaran=?,parameter_temperatur=?,skor_temperatur=?,skor_total=?,"+
                "parameter_total=?,nip=?",21,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            cmbSkor1.getSelectedItem().toString(),Skor1.getText(),cmbSkor2.getSelectedItem().toString(),Skor2.getText(),
            cmbSkor3.getSelectedItem().toString(),Skor3.getText(),cmbSkor4.getSelectedItem().toString(),Skor4.getText(),
            cmbSkor5.getSelectedItem().toString(),Skor5.getText(),cmbSkor6.getSelectedItem().toString(),Skor6.getText(),
            cmbSkor7.getSelectedItem().toString(),Skor7.getText(),TotalSkor.getText(),ParameterSkor.getText(),KdPetugas.getText(),
            tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(cmbSkor1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Skor1.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(cmbSkor2.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Skor2.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(cmbSkor3.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Skor3.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(cmbSkor4.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Skor4.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(cmbSkor5.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(Skor5.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(cmbSkor6.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(Skor6.getText(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(cmbSkor7.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(Skor7.getText(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(TotalSkor.getText(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(ParameterSkor.getText(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),24);
            emptTeks();
        }
    }
    
    private void hapus() {
        if(Sequel.queryu2tf("delete from pemantauan_pews_dewasa where tanggal=? and no_rawat=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
}


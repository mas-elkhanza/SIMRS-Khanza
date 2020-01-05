package simrskhanza;

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

/**
 *
 * @author dosen
 */
public class DlgPemberianDiet extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs;
    private int i=0;
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgPemberianDiet(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"No.Rawat","Nama Pasien","Kamar","Tanggal","Waktu","Diet","Diagnosa"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(120);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());


        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdDiet.setDocument(new batasInput((byte)3).getKata(KdDiet));
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
        
        diet.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPemberianObat")){
                    if(diet.getTable().getSelectedRow()!= -1){  
                        KdDiet.setText(diet.getTable().getValueAt(diet.getTable().getSelectedRow(),0).toString());
                        NmDiet.setText(diet.getTable().getValueAt(diet.getTable().getSelectedRow(),01).toString());
                        KdDiet.requestFocus();                           
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPemberianObat")){
                    if(bangsal.getTable().getSelectedRow()!= -1){                          
                        NmBangsalCari.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        NmBangsalCari.requestFocus();                           
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
        
    }
    
    private DlgCariDiet diet=new DlgCariDiet(null,false);
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnLabelDiet = new javax.swing.JMenuItem();
        MnLabelDiet1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel11 = new widget.Label();
        cmbJamCari = new widget.ComboBox();
        jLabel12 = new widget.Label();
        NmBangsalCari = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        jLabel9 = new widget.Label();
        NmDiet = new widget.TextBox();
        TPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        jLabel10 = new widget.Label();
        KdDiet = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        Kamar = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLabelDiet.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelDiet.setForeground(java.awt.Color.darkGray);
        MnLabelDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelDiet.setText("Label Diet");
        MnLabelDiet.setName("MnLabelDiet"); // NOI18N
        MnLabelDiet.setPreferredSize(new java.awt.Dimension(150, 28));
        MnLabelDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelDietActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLabelDiet);

        MnLabelDiet1.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelDiet1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelDiet1.setForeground(java.awt.Color.darkGray);
        MnLabelDiet1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelDiet1.setText("Semua Label Diet");
        MnLabelDiet1.setName("MnLabelDiet1"); // NOI18N
        MnLabelDiet1.setPreferredSize(new java.awt.Dimension(150, 28));
        MnLabelDiet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelDiet1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLabelDiet1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Diet Harian Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
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
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
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

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel11.setText("Waktu Diet :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel11);

        cmbJamCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Pagi", "Siang", "Sore", "Malam" }));
        cmbJamCari.setName("cmbJamCari"); // NOI18N
        cmbJamCari.setPreferredSize(new java.awt.Dimension(150, 23));
        cmbJamCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamCariKeyPressed(evt);
            }
        });
        panelGlass9.add(cmbJamCari);

        jLabel12.setText("Bangsal :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(163, 23));
        panelGlass9.add(jLabel12);

        NmBangsalCari.setEditable(false);
        NmBangsalCari.setHighlighter(null);
        NmBangsalCari.setName("NmBangsalCari"); // NOI18N
        NmBangsalCari.setPreferredSize(new java.awt.Dimension(310, 23));
        panelGlass9.add(NmBangsalCari);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('X');
        BtnSeek2.setToolTipText("Alt+X");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        panelGlass9.add(BtnSeek2);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass10.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2019" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2019" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass10.add(DTPCari2);

        jLabel6.setText("Keyword :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
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
        panelGlass10.add(BtnCari);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
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
        FormInput.setPreferredSize(new java.awt.Dimension(160, 77));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 12, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(78, 12, 125, 23);

        jLabel9.setText("Diet :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(330, 42, 77, 23);

        NmDiet.setEditable(false);
        NmDiet.setHighlighter(null);
        NmDiet.setName("NmDiet"); // NOI18N
        FormInput.add(NmDiet);
        NmDiet.setBounds(497, 42, 212, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(205, 12, 290, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2019" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(78, 42, 125, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pagi", "Siang", "Sore", "Malam" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(205, 42, 100, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 42, 75, 23);

        KdDiet.setHighlighter(null);
        KdDiet.setName("KdDiet"); // NOI18N
        KdDiet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDietKeyPressed(evt);
            }
        });
        FormInput.add(KdDiet);
        KdDiet.setBounds(410, 42, 85, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('X');
        BtnSeek1.setToolTipText("Alt+X");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        BtnSeek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek1KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek1);
        BtnSeek1.setBounds(712, 42, 28, 23);

        Kamar.setEditable(false);
        Kamar.setHighlighter(null);
        Kamar.setName("Kamar"); // NOI18N
        FormInput.add(Kamar);
        Kamar.setBounds(497, 12, 243, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,cmbJam,KdDiet);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,TCari,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NmDiet.getText().trim().equals("")){
            Valid.textKosong(KdDiet,"diet");
        }else{
            Sequel.menyimpan("detail_beri_diet","'"+TNoRw.getText()+"','"+Kamar.getText()+"','"+
                    Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"','"+
                    cmbJam.getSelectedItem()+"','"+
                    KdDiet.getText()+"'","data");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TPasien,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            DTPTgl.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            try{
                Sequel.queryu("delete from detail_beri_diet " +
                        "where no_rawat='"+TNoRw.getText()+"' " +
                        "and tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' " +
                        "and waktu='"+cmbJam.getSelectedItem()+"' " +
                        "and kd_diet='"+KdDiet.getText()+"'");
                tampil();
                emptTeks();
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
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
            Valid.MyReportqry("rptBrDiet.jasper","report","::[ Data Pemberian Diet ]::","select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, " +
                "concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal) as kd_kamar,detail_beri_diet.tanggal,detail_beri_diet.waktu,diet.nama_diet " +
                "from detail_beri_diet inner join reg_periksa inner join pasien inner join diet inner join kamar inner join bangsal " +
                "on detail_beri_diet.no_rawat=reg_periksa.no_rawat " +
                "and detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                "and kamar.kd_bangsal=bangsal.kd_bangsal "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and detail_beri_diet.kd_diet=diet.kd_diet " +
                "where detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and detail_beri_diet.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                "detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                "detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                "order by bangsal.nm_bangsal,diet.nama_diet",param);
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
        NmBangsalCari.setText("");
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

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,KdDiet);
}//GEN-LAST:event_cmbJamKeyPressed

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

private void KdDietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDietKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_diet from diet where kd_diet=? ",NmDiet,KdDiet.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek1ActionPerformed(null);
        }else{
            Valid.pindah(evt,TNoRw,BtnSimpan);
        }
}//GEN-LAST:event_KdDietKeyPressed

private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        akses.setform("DlgPemberianObat");        
        diet.emptTeks();
        diet.isCek();
        diet.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        diet.setLocationRelativeTo(internalFrame1);
        diet.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,KdDiet,BtnSimpan);
}//GEN-LAST:event_BtnSeek1KeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void cmbJamCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamCariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbJamCariKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        akses.setform("DlgPemberianObat");
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void MnLabelDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelDietActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptLabelDiet.jasper","report","::[ Label Diet ]::",
                "select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir, " +
                "concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal),detail_beri_diet.tanggal,detail_beri_diet.waktu,diet.nama_diet " +
                "from detail_beri_diet inner join reg_periksa inner join pasien inner join diet inner join kamar inner join bangsal " +
                "on detail_beri_diet.no_rawat=reg_periksa.no_rawat " +
                "and detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                "and kamar.kd_bangsal=bangsal.kd_bangsal "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and detail_beri_diet.kd_diet=diet.kd_diet " +
                "where detail_beri_diet.tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' and detail_beri_diet.waktu='"+cmbJam.getSelectedItem()+"' "+
                "and detail_beri_diet.no_rawat='"+TNoRw.getText()+"' and diet.nama_diet='"+NmDiet.getText()+"'",param);
        }
    }//GEN-LAST:event_MnLabelDietActionPerformed

    private void MnLabelDiet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelDiet1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptLabelDiet.jasper","report","::[ Label Diet ]::",
                "select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir, " +
                "concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal),detail_beri_diet.tanggal,detail_beri_diet.waktu,diet.nama_diet " +
                "from detail_beri_diet inner join reg_periksa inner join pasien inner join diet inner join kamar inner join bangsal " +
                "on detail_beri_diet.no_rawat=reg_periksa.no_rawat and detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                "and kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and detail_beri_diet.kd_diet=diet.kd_diet " +
                "where detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and detail_beri_diet.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                "detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                "detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                "order by bangsal.nm_bangsal,diet.nama_diet",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelDiet1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPemberianDiet dialog = new DlgPemberianDiet(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kamar;
    private widget.TextBox KdDiet;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnLabelDiet;
    private javax.swing.JMenuItem MnLabelDiet1;
    private widget.TextBox NmBangsalCari;
    private widget.TextBox NmDiet;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbJamCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {     
        try{
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement("select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, " +
                "concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal),detail_beri_diet.tanggal,detail_beri_diet.waktu,diet.nama_diet " +
                "from detail_beri_diet inner join reg_periksa inner join pasien inner join diet inner join kamar inner join bangsal " +
                "on detail_beri_diet.no_rawat=reg_periksa.no_rawat " +
                "and detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                "and kamar.kd_bangsal=bangsal.kd_bangsal "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and detail_beri_diet.kd_diet=diet.kd_diet " +
                "where detail_beri_diet.tanggal between ? and ? and detail_beri_diet.waktu like ? and bangsal.nm_bangsal like ? and detail_beri_diet.no_rawat like ? or "+
                "detail_beri_diet.tanggal between ? and ? and detail_beri_diet.waktu like ? and bangsal.nm_bangsal like ? and reg_periksa.no_rkm_medis like ? or "+
                "detail_beri_diet.tanggal between ? and ? and detail_beri_diet.waktu like ? and bangsal.nm_bangsal like ? and pasien.nm_pasien like ? "+
                "order by bangsal.nm_bangsal,diet.nama_diet");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%");
                ps.setString(4,"%"+NmBangsalCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(7,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(8,"%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%");
                ps.setString(9,"%"+NmBangsalCari.getText().trim()+"%");
                ps.setString(10,"%"+TCari.getText().trim()+"%");
                ps.setString(11,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(12,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(13,"%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%");
                ps.setString(14,"%"+NmBangsalCari.getText().trim()+"%");
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString(1),rs.getString(2)+", "+rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
                        Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat=? order by tgl_masuk desc",rs.getString(1))
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
        
        try{
            if(tabMode.getRowCount()>0){
                tabMode.addRow(new String[]{"","","","","","",""});
            }   
            ps2=koneksi.prepareStatement("select diet.nama_diet, count(diet.nama_diet) as jumlah " +
                "from detail_beri_diet inner join reg_periksa inner join pasien inner join diet inner join kamar inner join bangsal " +
                "on detail_beri_diet.no_rawat=reg_periksa.no_rawat " +
                "and detail_beri_diet.kd_kamar=kamar.kd_kamar "+
                "and kamar.kd_bangsal=bangsal.kd_bangsal "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and detail_beri_diet.kd_diet=diet.kd_diet " +
                "where detail_beri_diet.tanggal between ? and ? and detail_beri_diet.waktu like ? and bangsal.nm_bangsal like ? and detail_beri_diet.no_rawat like ? or "+
                "detail_beri_diet.tanggal between ? and ? and detail_beri_diet.waktu like ? and bangsal.nm_bangsal like ? and reg_periksa.no_rkm_medis like ? or "+
                "detail_beri_diet.tanggal between ? and ? and detail_beri_diet.waktu like ? and bangsal.nm_bangsal like ? and pasien.nm_pasien like ? "+
                "group by diet.nama_diet order by bangsal.nm_bangsal,diet.nama_diet");
            try {
                ps2.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(3,"%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%");
                ps2.setString(4,"%"+NmBangsalCari.getText().trim()+"%");
                ps2.setString(5,"%"+TCari.getText().trim()+"%");
                ps2.setString(6,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(7,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(8,"%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%");
                ps2.setString(9,"%"+NmBangsalCari.getText().trim()+"%");
                ps2.setString(10,"%"+TCari.getText().trim()+"%");
                ps2.setString(11,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(12,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(13,"%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%");
                ps2.setString(14,"%"+NmBangsalCari.getText().trim()+"%");
                ps2.setString(15,"%"+TCari.getText().trim()+"%");
                rs=ps2.executeQuery();
                i=1;
                while(rs.next()){
                     tabMode.addRow(new String[]{"",i+". "+rs.getString(1),": "+rs.getString(2),"","","",""});i++;                   
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }


    public void emptTeks() {
        KdDiet.setText("");
        NmDiet.setText("");
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            isRawat();            
            cmbJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            NmDiet.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            KdDiet.setText(Sequel.cariString("select kd_diet from diet where nama_diet='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"'"));
            Valid.SetTgl(DTPTgl,tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select pasien.nm_pasien from reg_periksa inner join pasien "+
                        "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
         Sequel.cariIsi("select kd_kamar from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",Kamar,TNoRw.getText());
    }
    
    public void setNoRm(String norwt,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        isRawat();
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,99));
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
        BtnSimpan.setEnabled(akses.getdiet_pasien());
        BtnHapus.setEnabled(akses.getdiet_pasien());
        BtnPrint.setEnabled(akses.getdiet_pasien());
    }

    
}

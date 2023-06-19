package laporan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import setting.DlgCariJamDiet;
import simrskhanza.DlgCariBangsal;
import java.awt.Cursor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dosen
 */
public class LaporanSisaDietPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,x=0,pilih=0,hewani=0,nabati=0,karbo=0,sayur=0,buah=0;

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public LaporanSisaDietPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","Nama Pasien","Kamar","Tanggal","Waktu","Jam","Karbohidrat(%)","Hewani(%)","Nabati(%)","Sayur(%)","Buah(%)","Kode Kamar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataDiet.setModel(tabMode);
        tbDataDiet.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataDiet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbDataDiet.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(230);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(55);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(53);
            }else if(i==10){
                column.setPreferredWidth(53);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDataDiet.setDefaultRenderer(Object.class, new WarnaTable());
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        Hewani.setDocument(new batasInput((byte)3).getOnlyAngka(Hewani));
        Nabati.setDocument(new batasInput((byte)3).getOnlyAngka(Nabati));
        Karbo.setDocument(new batasInput((byte)3).getOnlyAngka(Karbo));
        Sayur.setDocument(new batasInput((byte)3).getOnlyAngka(Sayur));
        Buah.setDocument(new batasInput((byte)3).getOnlyAngka(Buah));
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("LaporanSisaDietPasien")){
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
        
        jamdiet.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("LaporanSisaDietPasien")){
                    if(jamdiet.getTable().getSelectedRow()!= -1){  
                        if(pilih==1){
                            WaktuDiet.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),0).toString());
                            JamDiet.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),1).toString());
                        }else if(pilih==2){
                            WaktuDiet2.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),0).toString());
                            JamDiet2.setText(jamdiet.getTable().getValueAt(jamdiet.getTable().getSelectedRow(),1).toString());
                            BtnJam2.requestFocus(); 
                        }                             
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
    
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private DlgCariJamDiet jamdiet=new DlgCariJamDiet(null,false);
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Ruang = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
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
        WaktuDiet2 = new widget.TextBox();
        JamDiet2 = new widget.TextBox();
        BtnJam2 = new widget.Button();
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
        TPasien = new widget.TextBox();
        jLabel10 = new widget.Label();
        Kamar = new widget.TextBox();
        WaktuDiet = new widget.TextBox();
        JamDiet = new widget.TextBox();
        Tanggal = new widget.TextBox();
        jLabel13 = new widget.Label();
        Karbo = new widget.TextBox();
        jLabel14 = new widget.Label();
        Sayur = new widget.TextBox();
        jLabel15 = new widget.Label();
        Hewani = new widget.TextBox();
        jLabel16 = new widget.Label();
        Buah = new widget.TextBox();
        jLabel17 = new widget.Label();
        Nabati = new widget.TextBox();
        jLabel18 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        Scroll = new widget.ScrollPane();
        tbDataDiet = new widget.Table();

        Ruang.setEditable(false);
        Ruang.setHighlighter(null);
        Ruang.setName("Ruang"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Sisa Diet Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        jLabel11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel11);

        WaktuDiet2.setEditable(false);
        WaktuDiet2.setHighlighter(null);
        WaktuDiet2.setName("WaktuDiet2"); // NOI18N
        WaktuDiet2.setPreferredSize(new java.awt.Dimension(90, 23));
        WaktuDiet2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuDiet2KeyPressed(evt);
            }
        });
        panelGlass9.add(WaktuDiet2);

        JamDiet2.setEditable(false);
        JamDiet2.setHighlighter(null);
        JamDiet2.setName("JamDiet2"); // NOI18N
        JamDiet2.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(JamDiet2);

        BtnJam2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJam2.setMnemonic('X');
        BtnJam2.setToolTipText("Alt+X");
        BtnJam2.setName("BtnJam2"); // NOI18N
        BtnJam2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJam2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJam2ActionPerformed(evt);
            }
        });
        BtnJam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnJam2KeyPressed(evt);
            }
        });
        panelGlass9.add(BtnJam2);

        jLabel12.setText("Bangsal :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(103, 23));
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-05-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-05-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass10.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 128));
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
        jLabel4.setBounds(0, 12, 74, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(78, 12, 125, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(205, 12, 290, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 42, 74, 23);

        Kamar.setEditable(false);
        Kamar.setHighlighter(null);
        Kamar.setName("Kamar"); // NOI18N
        FormInput.add(Kamar);
        Kamar.setBounds(497, 12, 243, 23);

        WaktuDiet.setEditable(false);
        WaktuDiet.setHighlighter(null);
        WaktuDiet.setName("WaktuDiet"); // NOI18N
        FormInput.add(WaktuDiet);
        WaktuDiet.setBounds(181, 42, 75, 23);

        JamDiet.setEditable(false);
        JamDiet.setHighlighter(null);
        JamDiet.setName("JamDiet"); // NOI18N
        FormInput.add(JamDiet);
        JamDiet.setBounds(258, 42, 75, 23);

        Tanggal.setEditable(false);
        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        FormInput.add(Tanggal);
        Tanggal.setBounds(78, 42, 101, 23);

        jLabel13.setText("Karbohidrat :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(179, 70, 100, 23);

        Karbo.setHighlighter(null);
        Karbo.setName("Karbo"); // NOI18N
        Karbo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KarboKeyPressed(evt);
            }
        });
        FormInput.add(Karbo);
        Karbo.setBounds(283, 70, 50, 23);

        jLabel14.setText("Sayur :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 70, 74, 23);

        Sayur.setHighlighter(null);
        Sayur.setName("Sayur"); // NOI18N
        Sayur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SayurKeyPressed(evt);
            }
        });
        FormInput.add(Sayur);
        Sayur.setBounds(78, 70, 50, 23);

        jLabel15.setText("Protein Hewani :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(360, 40, 110, 23);

        Hewani.setHighlighter(null);
        Hewani.setName("Hewani"); // NOI18N
        Hewani.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HewaniKeyPressed(evt);
            }
        });
        FormInput.add(Hewani);
        Hewani.setBounds(474, 40, 50, 23);

        jLabel16.setText("Buah :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(390, 70, 80, 23);

        Buah.setHighlighter(null);
        Buah.setName("Buah"); // NOI18N
        Buah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuahKeyPressed(evt);
            }
        });
        FormInput.add(Buah);
        Buah.setBounds(474, 70, 50, 23);

        jLabel17.setText("Protein Nabati :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(580, 40, 90, 23);

        Nabati.setHighlighter(null);
        Nabati.setName("Nabati"); // NOI18N
        Nabati.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NabatiKeyPressed(evt);
            }
        });
        FormInput.add(Nabati);
        Nabati.setBounds(674, 40, 50, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("%");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(527, 40, 20, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("%");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(727, 40, 20, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("%");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(130, 70, 20, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("%");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(336, 70, 20, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("%");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(527, 70, 20, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbDataDiet.setName("tbDataDiet"); // NOI18N
        tbDataDiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataDietMouseClicked(evt);
            }
        });
        tbDataDiet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataDietKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDataDiet);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,WaktuDiet,TCari);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(JamDiet.getText().trim().equals("")){
            Valid.textKosong(WaktuDiet,"Waktu Diet");
        }else if(Hewani.getText().trim().equals("")){
            Valid.textKosong(Hewani,"Protein Hewani");
        }else if(Nabati.getText().trim().equals("")){
            Valid.textKosong(Nabati,"Protein Nabati");
        }else if(Sayur.getText().trim().equals("")){
            Valid.textKosong(Sayur,"Sayur");
        }else if(Karbo.getText().trim().equals("")){
            Valid.textKosong(Karbo,"Karbohidrat");
        }else if(Buah.getText().trim().equals("")){
            Valid.textKosong(Buah,"Buah");
        }else{
            if(Sequel.menyimpantf("sisa_diet_pasien","'"+TNoRw.getText()+"','"+Kamar.getText()+"','"+Valid.SetTgl(Tanggal.getText())+"',"+
                    "'"+WaktuDiet.getText()+"','"+Karbo.getText()+"','"+Hewani.getText()+"','"+Nabati.getText()+"','"+Sayur.getText()+"','"+Buah.getText()+"'","data")==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TPasien.getText(),Ruang.getText(),Valid.SetTgl(Tanggal.getText()+""),WaktuDiet.getText(),JamDiet.getText(),Karbo.getText(),Hewani.getText(),Nabati.getText(),Sayur.getText(),Buah.getText(),Kamar.getText()
                });
                emptTeks();
                hitung();
            }
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
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            if(tbDataDiet.getSelectedRow()!= -1){
                if(Sequel.queryutf("delete from sisa_diet_pasien " +
                        "where no_rawat='"+TNoRw.getText()+"' " +
                        "and tanggal='"+Valid.SetTgl(Tanggal.getText()+"")+"' " +
                        "and waktu='"+WaktuDiet.getText()+"' " +
                        "and kd_kamar='"+Kamar.getText()+"'")==true){
                    tabMode.removeRow(tbDataDiet.getSelectedRow());
                    hitung();
                }
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
            param.put("karbo",(karbo/i)+"");   
            param.put("hewani",(hewani/i)+"");   
            param.put("nabati",(nabati/i)+"");   
            param.put("sayur",(sayur/i)+"");   
            param.put("buah",(buah/i)+"");    
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptSisaDiet.jasper","report","::[ Data Pemberian Diet ]::",
                "select sisa_diet_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(sisa_diet_pasien.kd_kamar,', ',bangsal.nm_bangsal) as namakamar,"+
                "sisa_diet_pasien.tanggal,sisa_diet_pasien.waktu,jam_diet_pasien.jam,sisa_diet_pasien.kd_kamar,sisa_diet_pasien.karbohidrat,"+
                "sisa_diet_pasien.hewani,sisa_diet_pasien.nabati,sisa_diet_pasien.sayur,sisa_diet_pasien.buah " +
                "from sisa_diet_pasien inner join reg_periksa on sisa_diet_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join kamar on sisa_diet_pasien.kd_kamar=kamar.kd_kamar "+
                "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                "inner join jam_diet_pasien on sisa_diet_pasien.waktu=jam_diet_pasien.waktu " +
                "where sisa_diet_pasien.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and sisa_diet_pasien.waktu like '%"+WaktuDiet2.getText().trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' "+
                (TCari.getText().trim().equals("")?"":"and (sisa_diet_pasien.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%') ")+
                "order by bangsal.nm_bangsal" ,param);
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
        WaktuDiet2.setText("");
        JamDiet2.setText("");
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

    private void tbDataDietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataDietMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDataDietMouseClicked

    private void tbDataDietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataDietKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDataDietKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        akses.setform("LaporanSisaDietPasien");
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnJam2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJam2ActionPerformed
        akses.setform("LaporanSisaDietPasien");  
        pilih=2;
        jamdiet.emptTeks();
        jamdiet.isCek();
        jamdiet.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jamdiet.setLocationRelativeTo(internalFrame1);
        jamdiet.setVisible(true);
    }//GEN-LAST:event_BtnJam2ActionPerformed

    private void BtnJam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnJam2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnJam2KeyPressed

    private void WaktuDiet2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuDiet2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WaktuDiet2KeyPressed

    private void KarboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KarboKeyPressed
        Valid.pindah(evt,Sayur,Buah);
    }//GEN-LAST:event_KarboKeyPressed

    private void SayurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SayurKeyPressed
        Valid.pindah(evt,Nabati,Karbo);
    }//GEN-LAST:event_SayurKeyPressed

    private void HewaniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HewaniKeyPressed
        Valid.pindah(evt,TCari,Nabati);
    }//GEN-LAST:event_HewaniKeyPressed

    private void BuahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuahKeyPressed
        Valid.pindah(evt,Karbo,BtnSimpan);
    }//GEN-LAST:event_BuahKeyPressed

    private void NabatiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NabatiKeyPressed
        Valid.pindah(evt,Hewani,Sayur);
    }//GEN-LAST:event_NabatiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LaporanSisaDietPasien dialog = new LaporanSisaDietPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnJam2;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.TextBox Buah;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Hewani;
    private widget.TextBox JamDiet;
    private widget.TextBox JamDiet2;
    private widget.TextBox Kamar;
    private widget.TextBox Karbo;
    private widget.Label LCount;
    private widget.TextBox Nabati;
    private widget.TextBox NmBangsalCari;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Ruang;
    private widget.TextBox Sayur;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox Tanggal;
    private widget.TextBox WaktuDiet;
    private widget.TextBox WaktuDiet2;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbDataDiet;
    // End of variables declaration//GEN-END:variables

    public void tampil() {   
        try{
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                "select sisa_diet_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(sisa_diet_pasien.kd_kamar,', ',bangsal.nm_bangsal) as kamar,"+
                "sisa_diet_pasien.tanggal,sisa_diet_pasien.waktu,jam_diet_pasien.jam,sisa_diet_pasien.kd_kamar,sisa_diet_pasien.karbohidrat,"+
                "sisa_diet_pasien.hewani,sisa_diet_pasien.nabati,sisa_diet_pasien.sayur,sisa_diet_pasien.buah " +
                "from sisa_diet_pasien inner join reg_periksa on sisa_diet_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join kamar on sisa_diet_pasien.kd_kamar=kamar.kd_kamar "+
                "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                "inner join jam_diet_pasien on sisa_diet_pasien.waktu=jam_diet_pasien.waktu " +
                "where sisa_diet_pasien.tanggal between ? and ? and sisa_diet_pasien.waktu like ? and bangsal.nm_bangsal like ? "+
                (TCari.getText().trim().equals("")?"":"and (sisa_diet_pasien.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ?) ")+
                "order by bangsal.nm_bangsal");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+WaktuDiet2.getText().trim()+"%");
                ps.setString(4,"%"+NmBangsalCari.getText().trim()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),rs.getString("kamar"),rs.getString("tanggal"),
                        rs.getString("waktu"),rs.getString("jam"),rs.getString("karbohidrat"),rs.getString("hewani"),rs.getString("nabati"),rs.getString("sayur"),
                        rs.getString("buah"),rs.getString("kd_kamar")
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
            hitung();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void hitung(){
        hewani=0;nabati=0;karbo=0;sayur=0;buah=0;x=0;
        for(i=0;i<tabMode.getRowCount();i++){
            if(!tbDataDiet.getValueAt(i,0).toString().equals("")){
                karbo=karbo+Valid.SetInteger(tbDataDiet.getValueAt(i,6).toString());
                hewani=hewani+Valid.SetInteger(tbDataDiet.getValueAt(i,7).toString());
                nabati=nabati+Valid.SetInteger(tbDataDiet.getValueAt(i,8).toString());
                sayur=sayur+Valid.SetInteger(tbDataDiet.getValueAt(i,9).toString());
                buah=buah+Valid.SetInteger(tbDataDiet.getValueAt(i,10).toString());
                x++;
            }else{
                tabMode.removeRow(i);
                i--;
            }
        }
        LCount.setText(""+tabMode.getRowCount());
        if(tabMode.getRowCount()>0){
            tabMode.addRow(new String[]{
                "","Rata-rata Persentase Sisa Makanan","","","","",(karbo/x)+"",(hewani/x)+"",(nabati/x)+"",(sayur/x)+"",(buah/x)+"",""
            });
        }
    }
    
    public void emptTeks() {
        Hewani.setText("0");
        Nabati.setText("0");
        Karbo.setText("0");
        Sayur.setText("0");
        Buah.setText("0");
        Hewani.requestFocus();
    }

    private void getData() {
        if(tbDataDiet.getSelectedRow()!= -1){
            if(!tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),0).toString().equals("")){
                TNoRw.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),0).toString()); 
                TPasien.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),1).toString());    
                Ruang.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),2).toString());      
                Tanggal.setText(Valid.SetTgl3(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),3).toString()));           
                WaktuDiet.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),4).toString());
                JamDiet.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),5).toString());
                Karbo.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),6).toString());
                Hewani.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),7).toString());
                Nabati.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),8).toString());
                Sayur.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),9).toString());
                Buah.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),10).toString());
                Kamar.setText(tbDataDiet.getValueAt(tbDataDiet.getSelectedRow(),11).toString());
            }else{
                TNoRw.setText(""); 
                TPasien.setText("");    
                Ruang.setText("");      
                Tanggal.setText("");           
                WaktuDiet.setText("");
                JamDiet.setText("");
                Karbo.setText("");
                Hewani.setText("");
                Nabati.setText("");
                Sayur.setText("");
                Buah.setText("");
                Kamar.setText("");
            }
        }
    }
    
    private void isRawat() {
         Sequel.cariIsi("select pasien.nm_pasien from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
         Sequel.cariIsi("select kamar_inap.kd_kamar from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1",Kamar,TNoRw.getText());
    }

    public void setNoRm(String norwt,String pasien,String kamar,String bangsal,Date tgl1,Date tgl2,String tanggal,String waktudiet,String jamdiet) {
        TNoRw.setText(norwt);
        TPasien.setText(pasien);
        Kamar.setText(kamar);
        Ruang.setText(bangsal);
        TCari.setText(norwt);
        Tanggal.setText(tanggal);
        WaktuDiet.setText(waktudiet);
        JamDiet.setText(jamdiet);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,128));
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
        BtnSimpan.setEnabled(akses.getsisa_diet_pasien());
        BtnHapus.setEnabled(akses.getsisa_diet_pasien());
        BtnPrint.setEnabled(akses.getsisa_diet_pasien());
    }
}

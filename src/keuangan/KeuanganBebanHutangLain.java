/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package keuangan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author dosen
 */
public final class KeuanganBebanHutangLain extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();    
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private String koderekening=Sequel.cariIsi("select Beban_Hutang_Lain from set_akun"),kontraakun="",namakontraakun="";
    private boolean sukses=true;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private double total=0,sisahutang=0;
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public KeuanganBebanHutangLain(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Nota","Tgl.Piutang","NIP","Nama Petugas","Kode","Nama Pemberi Hutang","Keterangan","Tgl.Tempo","Nilai Hutang","Sisa Piutang","Status"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(70);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoHutang.setDocument(new batasInput((byte)20).getKata(NoHutang));
        Keterangan.setDocument(new batasInput((byte)100).getKata(Keterangan));
        NilaiHutang.setDocument(new batasInput((byte)15).getOnlyAngka(NilaiHutang));
        
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
        
        petugas.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    petugas.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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

        Kd2 = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBayarPiutang = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        label12 = new widget.Label();
        LTotal = new widget.Label();
        label13 = new widget.Label();
        LTotal1 = new widget.Label();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        NoHutang = new widget.TextBox();
        label36 = new widget.Label();
        Keterangan = new widget.TextBox();
        label16 = new widget.Label();
        KdPemberiHutang = new widget.TextBox();
        NmPemberiHutang = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        BtnPemberiHutang = new widget.Button();
        label17 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        label33 = new widget.Label();
        Tempo = new widget.Tanggal();
        NilaiHutang = new widget.TextBox();
        label37 = new widget.Label();

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        Popup.setName("Popup"); // NOI18N

        ppBayarPiutang.setBackground(new java.awt.Color(255, 255, 254));
        ppBayarPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBayarPiutang.setForeground(new java.awt.Color(50, 50, 50));
        ppBayarPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/bantuan.png"))); // NOI18N
        ppBayarPiutang.setText("Bayar Piutang");
        ppBayarPiutang.setComponentPopupMenu(Popup);
        ppBayarPiutang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBayarPiutang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBayarPiutang.setName("ppBayarPiutang"); // NOI18N
        ppBayarPiutang.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBayarPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBayarPiutangBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppBayarPiutang);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Beban Hutang Lain ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setComponentPopupMenu(Popup);
        tbKamar.setName("tbKamar"); // NOI18N
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(27, 23));
        panelisi3.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(Tgl2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('1');
        BtnAll.setToolTipText("Alt+1");
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
        panelisi3.add(BtnAll);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(LCount);

        jPanel1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
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

        label12.setText("Total :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(40, 30));
        panelisi1.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(90, 30));
        panelisi1.add(LTotal);

        label13.setText("Sisa :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(35, 30));
        panelisi1.add(label13);

        LTotal1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal1.setText("0");
        LTotal1.setName("LTotal1"); // NOI18N
        LTotal1.setPreferredSize(new java.awt.Dimension(90, 30));
        panelisi1.add(LTotal1);

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

        jPanel1.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 124));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 194));
        FormInput.setLayout(null);

        label34.setText("No.Hutang :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label34);
        label34.setBounds(0, 10, 75, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label32);
        label32.setBounds(0, 40, 75, 23);

        NoHutang.setHighlighter(null);
        NoHutang.setName("NoHutang"); // NOI18N
        NoHutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoHutangKeyPressed(evt);
            }
        });
        FormInput.add(NoHutang);
        NoHutang.setBounds(78, 10, 236, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label36);
        label36.setBounds(0, 70, 75, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(78, 70, 470, 23);

        label16.setText("Pemberi Hutang :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(330, 10, 100, 23);

        KdPemberiHutang.setEditable(false);
        KdPemberiHutang.setName("KdPemberiHutang"); // NOI18N
        KdPemberiHutang.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPemberiHutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPemberiHutangKeyPressed(evt);
            }
        });
        FormInput.add(KdPemberiHutang);
        KdPemberiHutang.setBounds(434, 10, 70, 23);

        NmPemberiHutang.setEditable(false);
        NmPemberiHutang.setName("NmPemberiHutang"); // NOI18N
        NmPemberiHutang.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPemberiHutang);
        NmPemberiHutang.setBounds(506, 10, 254, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(78, 40, 90, 23);

        BtnPemberiHutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPemberiHutang.setMnemonic('1');
        BtnPemberiHutang.setToolTipText("ALt+1");
        BtnPemberiHutang.setName("BtnPemberiHutang"); // NOI18N
        BtnPemberiHutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemberiHutangActionPerformed(evt);
            }
        });
        FormInput.add(BtnPemberiHutang);
        BtnPemberiHutang.setBounds(762, 10, 28, 23);

        label17.setText("Petugas :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label17);
        label17.setBounds(330, 40, 100, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(434, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(536, 40, 223, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('1');
        BtnPetugas.setToolTipText("ALt+1");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(762, 40, 28, 23);

        label33.setText("Tempo :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label33);
        label33.setBounds(170, 40, 50, 23);

        Tempo.setDisplayFormat("dd-MM-yyyy");
        Tempo.setName("Tempo"); // NOI18N
        Tempo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempoKeyPressed(evt);
            }
        });
        FormInput.add(Tempo);
        Tempo.setBounds(224, 40, 90, 23);

        NilaiHutang.setHighlighter(null);
        NilaiHutang.setName("NilaiHutang"); // NOI18N
        NilaiHutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiHutangKeyPressed(evt);
            }
        });
        FormInput.add(NilaiHutang);
        NilaiHutang.setBounds(670, 70, 120, 23);

        label37.setText("Nilai Hutang : Rp.");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label37);
        label37.setBounds(558, 70, 110, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NoHutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoHutangKeyPressed
        Valid.pindah(evt,TCari,NoHutang);
}//GEN-LAST:event_NoHutangKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
       if(NoHutang.getText().trim().equals("")){
            Valid.textKosong(NoHutang,"No.Hutang");
        }else if(NilaiHutang.getText().trim().equals("")||NilaiHutang.getText().trim().equals("0")){
            Valid.textKosong(NilaiHutang,"Nominal Hutang");
        }else if(KdPemberiHutang.getText().trim().equals("")||NmPemberiHutang.getText().trim().equals("")){
            Valid.textKosong(KdPemberiHutang,"Pemberi Hutang");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{            
            Sequel.AutoComitFalse();
            sukses=true;
            if(Sequel.menyimpantf("beban_hutang_lain","?,?,?,?,?,?,?,?,?,'Belum Lunas'","No.Nota",9,new String[]{
                    NoHutang.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPetugas.getText(),KdPemberiHutang.getText(),koderekening,
                    Keterangan.getText(),Valid.SetTgl(Tempo.getSelectedItem()+""),NilaiHutang.getText(),NilaiHutang.getText()
                })==true){
                    Sequel.queryu("delete from tampjurnal");                    
                    if(Sequel.menyimpantf2("tampjurnal","'"+kontraakun+"','"+namakontraakun+"','0','"+NilaiHutang.getText()+"'","Rekening")==false){
                        sukses=false;
                    }     
                    if(Sequel.menyimpantf2("tampjurnal","'"+koderekening+"','BEBAN HUTANG LAIN','"+NilaiHutang.getText()+"','0'","Rekening")==false){
                        sukses=false;
                    } 
                    if(sukses==true){
                        sukses=jur.simpanJurnal(NoHutang.getText(),"U","BEBAN HUTANG LAIN"+", OLEH "+akses.getkode());
                    }                   
            }else{
                sukses=false;
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
                BtnCariActionPerformed(evt);
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,NilaiHutang,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbKamar.getSelectedRow()>-1){
            Sequel.AutoComitFalse();
            sukses=true;

            if(Sequel.queryu2tf("delete from beban_hutang_lain where no_hutang=?", 1,new String[]{
                tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()
            })==true){
                kontraakun="";
                namakontraakun="";
                try {
                    myObj = new FileReader("./cache/pemberihutang.iyem");
                    root = mapper.readTree(myObj);
                    response = root.path("pemberihutang");
                    if(response.isArray()){
                       for(JsonNode list:response){
                           if(list.path("Kode").asText().equals(tbKamar.getValueAt(tbKamar.getSelectedRow(),4).toString())){
                                kontraakun=list.path("KodeRekening").asText();  
                                namakontraakun=list.path("NamaRekening").asText();  
                           }
                       }
                    }
                    myObj.close();
                } catch (Exception e) {
                    sukses=false;
                }
                Sequel.queryu("delete from tampjurnal");                    
                if(Sequel.menyimpantf2("tampjurnal","'"+koderekening+"','BEBAN HUTANG LAIN','0','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString()+"'","Rekening")==false){
                    sukses=false;
                }     
                if(Sequel.menyimpantf2("tampjurnal","'"+kontraakun+"','"+namakontraakun+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString()+"','0'","Rekening")==false){
                    sukses=false;
                } 
                if(sukses==true){
                    sukses=jur.simpanJurnal(NoHutang.getText(),"U","PEMBATALAN BEBAN HUTANG LAIN"+", OLEH "+akses.getkode()); 
                }    
            }else{
                sukses=false;
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
                BtnCariActionPerformed(evt);
                emptTeks();
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!");
        }  
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnKeluar, BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

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
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnKeluar.requestFocus();
        }else if(tabMode.getRowCount()!=0){     
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());        
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));   
            Valid.MyReportqry("rptBebanHutangLain.jasper","report","::[ Data Beban Hutang Lain ]::",
                    "select beban_hutang_lain.no_hutang,beban_hutang_lain.tgl_hutang,beban_hutang_lain.nip,petugas.nama,beban_hutang_lain.kode_pemberi_hutang,"+
                    "pemberi_hutang_lain.nama_pemberi_hutang,beban_hutang_lain.keterangan,beban_hutang_lain.tgltempo,beban_hutang_lain.nominal,beban_hutang_lain.sisahutang,beban_hutang_lain.status "+
                    "from beban_hutang_lain inner join petugas on petugas.nip=beban_hutang_lain.nip inner join pemberi_hutang_lain on beban_hutang_lain.kode_pemberi_hutang=pemberi_hutang_lain.kode_pemberi_hutang "+
                    "where beban_hutang_lain.tgl_hutang between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":"and (beban_hutang_lain.nip like '%"+TCari.getText()+"%' or petugas.nama like '%"+TCari.getText()+"%' or beban_hutang_lain.kode_pemberi_hutang like '%"+TCari.getText()+"%' or "+
                    "pemberi_hutang_lain.nama_pemberi_hutang like '%"+TCari.getText()+"%' or beban_hutang_lain.keterangan like '%"+TCari.getText()+"%' or beban_hutang_lain.status like '%"+TCari.getText()+"%')")+
                    "order by beban_hutang_lain.tgl_hutang",param);
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
            tampil();
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
   Valid.pindah(evt,KdPemberiHutang,NilaiHutang);
}//GEN-LAST:event_KeteranganKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah2(evt,NoHutang,Tempo);
    }//GEN-LAST:event_TanggalKeyPressed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

private void BtnPemberiHutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemberiHutangActionPerformed
        kontraakun="";
        namakontraakun="";
        DlgCariPemberiHutang peminjam=new DlgCariPemberiHutang(null,false);
        peminjam.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(peminjam.getTable().getSelectedRow()!= -1){
                    KdPemberiHutang.setText(peminjam.getTable().getValueAt(peminjam.getTable().getSelectedRow(),0).toString());
                    NmPemberiHutang.setText(peminjam.getTable().getValueAt(peminjam.getTable().getSelectedRow(),1).toString());
                    kontraakun=peminjam.getTable().getValueAt(peminjam.getTable().getSelectedRow(),4).toString();
                    namakontraakun=peminjam.getTable().getValueAt(peminjam.getTable().getSelectedRow(),5).toString();
                    BtnPemberiHutang.requestFocus();
                }      
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {peminjam.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        peminjam.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    peminjam.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        peminjam.emptTeks();
        peminjam.isCek();
        peminjam.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        peminjam.setLocationRelativeTo(internalFrame1);
        peminjam.setAlwaysOnTop(false);
        peminjam.setVisible(true);
}//GEN-LAST:event_BtnPemberiHutangActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void TempoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempoKeyPressed
        //Valid.pindah2(evt,Tanggal,AkunBayar);
    }//GEN-LAST:event_TempoKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void NilaiHutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiHutangKeyPressed
        Valid.pindah(evt,Keterangan,BtnSimpan);
    }//GEN-LAST:event_NilaiHutangKeyPressed

    private void KdPemberiHutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPemberiHutangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPemberiHutangActionPerformed(null);
        }else{
            //Valid.pindah(evt,AkunBayar,Keterangan);
        }
    }//GEN-LAST:event_KdPemberiHutangKeyPressed

    private void ppBayarPiutangBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBayarPiutangBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbKamar.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            KeuanganBayarPiutangPeminjamanUang bayarpiutang=new KeuanganBayarPiutangPeminjamanUang(null,false);
            bayarpiutang.emptTeks();
            bayarpiutang.setData(tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),4).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString());
            bayarpiutang.isCek();
            bayarpiutang.tampil();
            bayarpiutang.setSize(this.getWidth()-20,this.getHeight()-20);
            bayarpiutang.setLocationRelativeTo(this);
            bayarpiutang.setAlwaysOnTop(false);
            bayarpiutang.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppBayarPiutangBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganBebanHutangLain dialog = new KeuanganBebanHutangLain(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPemberiHutang;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.TextBox KdPemberiHutang;
    private widget.TextBox KdPetugas;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LTotal;
    private widget.Label LTotal1;
    private widget.TextBox NilaiHutang;
    private widget.TextBox NmPemberiHutang;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoHutang;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tempo;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBayarPiutang;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            //"No.Nota","Tgl.Piutang","NIP","Nama Petugas","Kode","Nama Pemberi Hutang","Keterangan","Tgl.Tempo","Nilai Hutang","Sisa Piutang","Status"
            ps=koneksi.prepareStatement(
                    "select beban_hutang_lain.no_hutang,beban_hutang_lain.tgl_hutang,beban_hutang_lain.nip,petugas.nama,beban_hutang_lain.kode_pemberi_hutang,"+
                    "pemberi_hutang_lain.nama_pemberi_hutang,beban_hutang_lain.keterangan,beban_hutang_lain.tgltempo,beban_hutang_lain.nominal,beban_hutang_lain.sisahutang,beban_hutang_lain.status "+
                    "from beban_hutang_lain inner join petugas on petugas.nip=beban_hutang_lain.nip inner join pemberi_hutang_lain on beban_hutang_lain.kode_pemberi_hutang=pemberi_hutang_lain.kode_pemberi_hutang "+
                    "where beban_hutang_lain.tgl_hutang between ? and ? "+(TCari.getText().trim().equals("")?"":"and (beban_hutang_lain.nip like ? or petugas.nama like ? or "+
                    "beban_hutang_lain.kode_pemberi_hutang like ? or pemberi_hutang_lain.nama_pemberi_hutang like ? or beban_hutang_lain.keterangan like ? or beban_hutang_lain.status like ?)")+
                    "order by beban_hutang_lain.tgl_hutang");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                total=0;
                sisahutang=0;
                while(rs.next()){                
                    total=total+rs.getDouble("nominal");
                    sisahutang=sisahutang+rs.getDouble("sisahutang");
                    tabMode.addRow(new Object[]{
                        rs.getString("no_hutang"),rs.getString("tgl_hutang"),rs.getString("nip"),rs.getString("nama"),rs.getString("kode_pemberi_hutang"),
                        rs.getString("nama_pemberi_hutang"),rs.getString("keterangan"),rs.getString("tgltempo"),rs.getDouble("nominal"),rs.getDouble("sisahutang"),
                        rs.getString("status")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif :"+e);
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
        LTotal.setText(Valid.SetAngka(total));
        LTotal1.setText(Valid.SetAngka(sisahutang));
    }

    public void emptTeks() {
        ChkInput.setSelected(true);
        isForm();
        NilaiHutang.setText("");
        Tanggal.setDate(new Date());
        Tempo.setDate(new Date());
        Keterangan.setText("");
        KdPemberiHutang.setText("");
        NmPemberiHutang.setText("");
        kontraakun="";
        namakontraakun="";
        autoNomor();
        Tanggal.requestFocus();
    }

    public JTextField getTextField(){
        return NoHutang;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbeban_hutang_lain());
        BtnHapus.setEnabled(akses.getbeban_hutang_lain());
        BtnPrint.setEnabled(akses.getbeban_hutang_lain());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
        }else if(akses.getjml1()>=1){
            KdPetugas.setEditable(true);
            BtnPetugas.setEnabled(true);
            BtnSimpan.setEnabled(true);
        } 
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,124));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(beban_hutang_lain.no_hutang,4),signed)),0) from beban_hutang_lain where beban_hutang_lain.tgl_hutang like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"%' ",
                "BHL"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),4,NoHutang); 
    }
}

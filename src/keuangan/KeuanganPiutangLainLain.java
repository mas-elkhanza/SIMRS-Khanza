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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
public final class KeuanganPiutangLainLain extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();    
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private String koderekening="",kontraakun="",namakontraakun="";
    private boolean sukses=true;
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private DlgCariPeminjamPiutang peminjam=new DlgCariPeminjamPiutang(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private double total=0;
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public KeuanganPiutangLainLain(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Nota","Tgl.Piutang","NIP","Nama Petugas","Kode","Nama Peminjam/Perusahaan","Kode Rek","Akun","Keterangan","Tgl.Tempo","Nominal"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.Double.class, 
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
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(65);
            }else if(i==10){
                column.setPreferredWidth(90);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoNota.setDocument(new batasInput((byte)20).getKata(NoNota));
        Keterangan.setDocument(new batasInput((byte)100).getKata(Keterangan));
        NominalPinjam.setDocument(new batasInput((byte)15).getOnlyAngka(NominalPinjam));
        
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
        
        peminjam.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(peminjam.getTable().getSelectedRow()!= -1){
                    KdPeminjam.setText(peminjam.getTable().getValueAt(peminjam.getTable().getSelectedRow(),0).toString());
                    NmPeminjam.setText(peminjam.getTable().getValueAt(peminjam.getTable().getSelectedRow(),1).toString());
                    kontraakun=peminjam.getTable().getValueAt(peminjam.getTable().getSelectedRow(),4).toString();
                    namakontraakun=peminjam.getTable().getValueAt(peminjam.getTable().getSelectedRow(),5).toString();
                    BtnPeminjam.requestFocus();
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

        Popup = new javax.swing.JPopupMenu();
        ppNotaPiutang = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
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
        label12 = new widget.Label();
        LTotal = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        NoNota = new widget.TextBox();
        label36 = new widget.Label();
        Keterangan = new widget.TextBox();
        label16 = new widget.Label();
        KdPeminjam = new widget.TextBox();
        NmPeminjam = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        BtnPeminjam = new widget.Button();
        jLabel10 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        label17 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        label33 = new widget.Label();
        Tempo = new widget.Tanggal();
        NominalPinjam = new widget.TextBox();
        label37 = new widget.Label();

        Popup.setName("Popup"); // NOI18N

        ppNotaPiutang.setBackground(new java.awt.Color(255, 255, 254));
        ppNotaPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppNotaPiutang.setForeground(new java.awt.Color(50, 50, 50));
        ppNotaPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppNotaPiutang.setText("Nota Bayar Piutang");
        ppNotaPiutang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppNotaPiutang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppNotaPiutang.setName("ppNotaPiutang"); // NOI18N
        ppNotaPiutang.setPreferredSize(new java.awt.Dimension(160, 25));
        ppNotaPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppNotaPiutangBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppNotaPiutang);

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Piutang Perusahaan/Lain-lain ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
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
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
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

        label12.setText("Total :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(140, 23));
        panelisi3.add(LTotal);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('1');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+1");
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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(55, 30));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(65, 30));
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

        label34.setText("No.Nota :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label34);
        label34.setBounds(0, 10, 60, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label32);
        label32.setBounds(0, 40, 60, 23);

        NoNota.setHighlighter(null);
        NoNota.setName("NoNota"); // NOI18N
        NoNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoNotaKeyPressed(evt);
            }
        });
        FormInput.add(NoNota);
        NoNota.setBounds(64, 10, 236, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label36);
        label36.setBounds(320, 70, 75, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(399, 70, 170, 23);

        label16.setText("Peminjam :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(320, 10, 75, 23);

        KdPeminjam.setEditable(false);
        KdPeminjam.setName("KdPeminjam"); // NOI18N
        KdPeminjam.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPeminjam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPeminjamKeyPressed(evt);
            }
        });
        FormInput.add(KdPeminjam);
        KdPeminjam.setBounds(399, 10, 60, 23);

        NmPeminjam.setEditable(false);
        NmPeminjam.setName("NmPeminjam"); // NOI18N
        NmPeminjam.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPeminjam);
        NmPeminjam.setBounds(461, 10, 268, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(64, 40, 90, 23);

        BtnPeminjam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPeminjam.setMnemonic('1');
        BtnPeminjam.setToolTipText("ALt+1");
        BtnPeminjam.setName("BtnPeminjam"); // NOI18N
        BtnPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPeminjamActionPerformed(evt);
            }
        });
        FormInput.add(BtnPeminjam);
        BtnPeminjam.setBounds(732, 10, 28, 23);

        jLabel10.setText("Akun :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 70, 60, 23);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        FormInput.add(AkunBayar);
        AkunBayar.setBounds(64, 70, 236, 23);

        label17.setText("Petugas :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label17);
        label17.setBounds(320, 40, 75, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(399, 40, 90, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(491, 40, 238, 23);

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
        BtnPetugas.setBounds(732, 40, 28, 23);

        label33.setText("Tempo :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label33);
        label33.setBounds(156, 40, 50, 23);

        Tempo.setDisplayFormat("dd-MM-yyyy");
        Tempo.setName("Tempo"); // NOI18N
        Tempo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempoKeyPressed(evt);
            }
        });
        FormInput.add(Tempo);
        Tempo.setBounds(210, 40, 90, 23);

        NominalPinjam.setHighlighter(null);
        NominalPinjam.setName("NominalPinjam"); // NOI18N
        NominalPinjam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NominalPinjamKeyPressed(evt);
            }
        });
        FormInput.add(NominalPinjam);
        NominalPinjam.setBounds(650, 70, 110, 23);

        label37.setText("Pinjam : Rp.");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label37);
        label37.setBounds(578, 70, 70, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NoNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoNotaKeyPressed
        Valid.pindah(evt,TCari,NoNota);
}//GEN-LAST:event_NoNotaKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
       if(NoNota.getText().trim().equals("")){
            Valid.textKosong(NoNota,"No.Tagihan/No.Rawat");
        }else if(NominalPinjam.getText().trim().equals("")||NominalPinjam.getText().trim().equals("0")){
            Valid.textKosong(NominalPinjam,"Nominal Pinjam");
        }else if(KdPeminjam.getText().trim().equals("")||NmPeminjam.getText().trim().equals("")){
            Valid.textKosong(KdPeminjam,"Peminjam");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{            
            koderekening="";
            try {
                myObj = new FileReader("./cache/akunbayar.iyem");
                root = mapper.readTree(myObj);
                response = root.path("akunbayar");
                if(response.isArray()){
                   for(JsonNode list:response){
                       if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                            koderekening=list.path("KodeRek").asText();  
                       }
                   }
                }
                myObj.close();
            } catch (Exception e) {
                sukses=false;
            }
            Sequel.AutoComitFalse();
            sukses=true;
            if(Sequel.menyimpantf("piutang_lainlain","?,?,?,?,?,?,?,?,?","No.Nota",9,new String[]{
                    NoNota.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPetugas.getText(),KdPeminjam.getText(),koderekening,
                    AkunBayar.getSelectedItem().toString(),Keterangan.getText(),Valid.SetTgl(Tempo.getSelectedItem()+""),NominalPinjam.getText()
                })==true){
                    Sequel.queryu("delete from tampjurnal");                    
                    Sequel.menyimpan("tampjurnal","'"+kontraakun+"','"+namakontraakun+"','"+NominalPinjam.getText()+"','0'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'"+koderekening+"','"+AkunBayar.getSelectedItem()+"','0','"+NominalPinjam.getText()+"'","Rekening"); 
                    sukses=jur.simpanJurnal(NoNota.getText(),"U","PIUTANG PERUSAHAAN/LAIN-LAIN"+", OLEH "+akses.getkode());                   
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
            Valid.pindah(evt,NominalPinjam,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbKamar.getSelectedRow()>-1){
            Sequel.AutoComitFalse();
            sukses=true;

            if(Sequel.queryu2tf("delete from piutang_lainlain where nota_piutang=?", 1,new String[]{
                tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString()
            })==true){
                kontraakun="";
                namakontraakun="";
                try {
                    myObj = new FileReader("./cache/peminjampiutang.iyem");
                    root = mapper.readTree(myObj);
                    response = root.path("peminjampiutang");
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
                Sequel.menyimpan("tampjurnal","'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString()+"','0'","Rekening");    
                Sequel.menyimpan("tampjurnal","'"+kontraakun+"','"+namakontraakun+"','0','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString()+"'","Rekening"); 
                sukses=jur.simpanJurnal(NoNota.getText(),"U","PEMBATALAN PIUTANG LAIN-LAIN"+", OLEH "+akses.getkode());     
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
            param.put("logo",Sequel.cariGambar("select logo from setting"));   
            Valid.MyReportqry("rptPiutangLainLain.jasper","report","::[ Data Piutang Perusahaan/Piutang Lain-Lain ]::",
                    "select piutang_lainlain.nota_piutang,piutang_lainlain.tgl_piutang,piutang_lainlain.nip,petugas.nama,piutang_lainlain.kode_peminjam,"+
                    "peminjampiutang.nama_peminjam,piutang_lainlain.kd_rek,piutang_lainlain.nama_bayar,piutang_lainlain.keterangan,piutang_lainlain.tgltempo,"+
                    "piutang_lainlain.nominal from piutang_lainlain inner join petugas on petugas.nip=piutang_lainlain.nip "+
                    "inner join peminjampiutang on piutang_lainlain.kode_peminjam=peminjampiutang.kode_peminjam where piutang_lainlain.tgl_piutang between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":"and (piutang_lainlain.nip like '%"+TCari.getText()+"%' or petugas.nama like '%"+TCari.getText()+"%' or piutang_lainlain.kode_peminjam like '%"+TCari.getText()+"%' or "+
                    "peminjampiutang.nama_peminjam like '%"+TCari.getText()+"%' or piutang_lainlain.nama_bayar like '%"+TCari.getText()+"%' or piutang_lainlain.keterangan like '%"+TCari.getText()+"%' )")+
                    "order by piutang_lainlain.tgl_piutang",param);
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

private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
   Valid.pindah(evt,KdPeminjam,NominalPinjam);
}//GEN-LAST:event_KeteranganKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah2(evt,NoNota,Tempo);
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

private void ppNotaPiutangBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppNotaPiutangBtnPrintActionPerformed
    /*this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnKeluar.requestFocus();
        }else if(Cicilan.getText().equals("")||Cicilan.getText().equals("0")){
            JOptionPane.showMessageDialog(null,"Maaf, pilih dulu data yang mau dicetak notanya...!!!!");
            tbKamar.requestFocus();
        }else if(tabMode.getRowCount()!=0){  
            if(tbKamar.getSelectedRow()>-1){
                Sequel.queryu("truncate table temporary");
                Sequel.menyimpan("temporary","'0','No.Rawat',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),5).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'0','Tgl.Bayar',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),0).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'0','No.Rekam Medik',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),1).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'0','Nama Pasien',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),2).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'0','Keterangan',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),4).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'0','Besar Cicilan',': "+Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(tbKamar.getSelectedRow(),3).toString())) +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 

                Valid.panggilUrl("billing/LaporanPiutang.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+Tanggal.getSelectedItem().toString().replaceAll(" ","_"));
            }   
        }
        this.setCursor(Cursor.getDefaultCursor());  */  
}//GEN-LAST:event_ppNotaPiutangBtnPrintActionPerformed

private void BtnPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPeminjamActionPerformed
        kontraakun="";
        namakontraakun="";
        peminjam.emptTeks();
        peminjam.isCek();
        peminjam.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        peminjam.setLocationRelativeTo(internalFrame1);
        peminjam.setAlwaysOnTop(false);
        peminjam.setVisible(true);
}//GEN-LAST:event_BtnPeminjamActionPerformed

    private void AkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkunBayarKeyPressed
        Valid.pindah(evt,Tempo,KdPeminjam);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilAkunBayar();
    }//GEN-LAST:event_formWindowOpened

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void TempoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempoKeyPressed
        Valid.pindah2(evt,Tanggal,AkunBayar);
    }//GEN-LAST:event_TempoKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void NominalPinjamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NominalPinjamKeyPressed
        Valid.pindah(evt,Keterangan,BtnSimpan);
    }//GEN-LAST:event_NominalPinjamKeyPressed

    private void KdPeminjamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPeminjamKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPeminjamActionPerformed(null);
        }else{
            Valid.pindah(evt,AkunBayar,Keterangan);
        }
    }//GEN-LAST:event_KdPeminjamKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganPiutangLainLain dialog = new KeuanganPiutangLainLain(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AkunBayar;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPeminjam;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.TextBox KdPeminjam;
    private widget.TextBox KdPetugas;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LTotal;
    private widget.TextBox NmPeminjam;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoNota;
    private widget.TextBox NominalPinjam;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tempo;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
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
    private javax.swing.JMenuItem ppNotaPiutang;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            ps=koneksi.prepareStatement(
                    "select piutang_lainlain.nota_piutang,piutang_lainlain.tgl_piutang,piutang_lainlain.nip,petugas.nama,piutang_lainlain.kode_peminjam,"+
                    "peminjampiutang.nama_peminjam,piutang_lainlain.kd_rek,piutang_lainlain.nama_bayar,piutang_lainlain.keterangan,piutang_lainlain.tgltempo,"+
                    "piutang_lainlain.nominal from piutang_lainlain inner join petugas on petugas.nip=piutang_lainlain.nip "+
                    "inner join peminjampiutang on piutang_lainlain.kode_peminjam=peminjampiutang.kode_peminjam where piutang_lainlain.tgl_piutang between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (piutang_lainlain.nip like ? or petugas.nama like ? or piutang_lainlain.kode_peminjam like ? or "+
                    "peminjampiutang.nama_peminjam like ? or piutang_lainlain.nama_bayar like ? or piutang_lainlain.keterangan like ? )")+
                    "order by piutang_lainlain.tgl_piutang");
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
                while(rs.next()){                
                    total=total+rs.getDouble("nominal");
                    tabMode.addRow(new Object[]{
                        rs.getString("nota_piutang"),rs.getString("tgl_piutang"),rs.getString("nip"),rs.getString("nama"),rs.getString("kode_peminjam"),
                        rs.getString("nama_peminjam"),rs.getString("kd_rek"),rs.getString("nama_bayar"),rs.getString("keterangan"),rs.getString("tgltempo"),
                        rs.getDouble("nominal")
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
    }

    public void emptTeks() {
        ChkInput.setSelected(true);
        isForm();
        NominalPinjam.setText("");
        Tanggal.setDate(new Date());
        Tempo.setDate(new Date());
        Keterangan.setText("");
        KdPeminjam.setText("");
        NmPeminjam.setText("");
        kontraakun="";
        namakontraakun="";
        autoNomor();
        Tanggal.requestFocus();
    }

    public JTextField getTextField(){
        return NoNota;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpiutang_lainlain());
        BtnHapus.setEnabled(akses.getpiutang_lainlain());
        BtnPrint.setEnabled(akses.getpiutang_lainlain());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?",NmPetugas,KdPetugas.getText());
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
    
    private void tampilAkunBayar() {         
         try{      
             file=new File("./cache/akunbayar.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             iyem="";
             ps=koneksi.prepareStatement("select * from akun_bayar order by nama_bayar");
             try{
                 rs=ps.executeQuery();
                 AkunBayar.removeAllItems();
                 while(rs.next()){    
                     AkunBayar.addItem(rs.getString(1).replaceAll("\"",""));
                     iyem=iyem+"{\"NamaAkun\":\""+rs.getString(1).replaceAll("\"","")+"\",\"KodeRek\":\""+rs.getString(2)+"\",\"PPN\":\""+rs.getDouble(3)+"\"},";
                 }
             }catch (Exception e) {
                 System.out.println("Notifikasi : "+e);
             } finally{
                 if(rs != null){
                     rs.close();
                 } 
                 if(ps != null){
                     ps.close();
                 } 
             }

             fileWriter.write("{\"akunbayar\":["+iyem.substring(0,iyem.length()-1)+"]}");
             fileWriter.flush();
             fileWriter.close();
             iyem=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_piutang,4),signed)),0) from piutang_lainlain where tgl_piutang like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"%' ",
                "PLL"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),4,NoNota); 
    }
}

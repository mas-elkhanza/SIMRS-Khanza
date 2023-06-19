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

/**
 *
 * @author dosen
 */
public final class KeuanganBayarPiutangLain extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();    
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPeminjamPiutang peminjam=new DlgCariPeminjamPiutang(null,false);
    private double total=0,sisapiutang=0;
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
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public KeuanganBayarPiutangLain(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "Tgl.Bayar","Kode","Peminjam","Cicilan(Rp)","Keterangan","No.Nota","Kode Akun","Akun Bayar"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(150);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoNota.setDocument(new batasInput((byte)17).getKata(NoNota));
        Cicilan.setDocument(new batasInput((byte)15).getOnlyAngka(Cicilan));
        Keterangan.setDocument(new batasInput((byte)100).getKata(Keterangan));
        KdPeminjam.setDocument(new batasInput((byte)5).getKata(KdPeminjam));
        
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
            
            Cicilan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    Sisa.setText(Valid.SetAngka(sisapiutang));
                    if(!Cicilan.getText().equals("")){                           
                         Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())));                           
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    Sisa.setText(Valid.SetAngka(sisapiutang));
                    if(!Cicilan.getText().equals("")){                           
                         Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())));                           
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    Sisa.setText(Valid.SetAngka(sisapiutang));
                    if(!Cicilan.getText().equals("")){                           
                         Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())));                           
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
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        label12 = new widget.Label();
        LTotal = new widget.Label();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        NoNota = new widget.TextBox();
        label36 = new widget.Label();
        Keterangan = new widget.TextBox();
        label35 = new widget.Label();
        Cicilan = new widget.TextBox();
        label16 = new widget.Label();
        KdPeminjam = new widget.TextBox();
        NmPeminjam = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        label38 = new widget.Label();
        Sisa = new widget.TextBox();
        BtnPeminjam = new widget.Button();
        jLabel10 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        BtnCari1 = new widget.Button();
        BtnAll1 = new widget.Button();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Bayar Piutang Perusahaan/Lain-lain ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setComponentPopupMenu(Popup);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tgl.Bayar :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
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
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
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

        label12.setText("Total :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(55, 30));
        panelisi1.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(140, 30));
        panelisi1.add(LTotal);

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
        label34.setBounds(0, 10, 75, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label32);
        label32.setBounds(0, 40, 75, 23);

        NoNota.setHighlighter(null);
        NoNota.setName("NoNota"); // NOI18N
        NoNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoNotaKeyPressed(evt);
            }
        });
        FormInput.add(NoNota);
        NoNota.setBounds(79, 10, 180, 23);

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
        Keterangan.setBounds(79, 70, 210, 23);

        label35.setText("Cicilan :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label35);
        label35.setBounds(301, 40, 79, 23);

        Cicilan.setHighlighter(null);
        Cicilan.setName("Cicilan"); // NOI18N
        Cicilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CicilanKeyPressed(evt);
            }
        });
        FormInput.add(Cicilan);
        Cicilan.setBounds(384, 40, 120, 23);

        label16.setText("Peminjam :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(301, 10, 79, 23);

        KdPeminjam.setEditable(false);
        KdPeminjam.setName("KdPeminjam"); // NOI18N
        KdPeminjam.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPeminjam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPeminjamKeyPressed(evt);
            }
        });
        FormInput.add(KdPeminjam);
        KdPeminjam.setBounds(384, 10, 90, 23);

        NmPeminjam.setEditable(false);
        NmPeminjam.setName("NmPeminjam"); // NOI18N
        NmPeminjam.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPeminjam);
        NmPeminjam.setBounds(476, 10, 204, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 110, 23);

        label38.setText("Sisa Piutang :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label38);
        label38.setBounds(510, 40, 77, 23);

        Sisa.setHighlighter(null);
        Sisa.setName("Sisa"); // NOI18N
        Sisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SisaKeyPressed(evt);
            }
        });
        FormInput.add(Sisa);
        Sisa.setBounds(590, 40, 120, 23);

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
        BtnPeminjam.setBounds(682, 10, 28, 23);

        jLabel10.setText("Akun Bayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(301, 70, 79, 23);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        FormInput.add(AkunBayar);
        AkunBayar.setBounds(384, 70, 296, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCari1);
        BtnCari1.setBounds(261, 10, 28, 23);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnAll1.setMnemonic('M');
        BtnAll1.setToolTipText("Alt+M");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnAll1);
        BtnAll1.setBounds(682, 70, 28, 23);

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
        }else if(Cicilan.getText().trim().equals("")||Cicilan.getText().trim().equals("0")){
            Valid.textKosong(Cicilan,"Besar Cicilan");
        }else if(NmPeminjam.getText().trim().equals("")){
            Valid.textKosong(KdPeminjam,"Member");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(AkunBayar.getSelectedItem()==null){
            Valid.textKosong(AkunBayar,"Akun Bayar");
        }else{         
            if(Sequel.cariInteger("select count(nota_piutang) from piutang_lainlain where nota_piutang=? and kode_peminjam=?",NoNota.getText(),KdPeminjam.getText())>0){
                sisapiutang=(Sequel.cariIsiAngka("SELECT sisapiutang FROM piutang_lainlain where nota_piutang=?",NoNota.getText())-Double.parseDouble(Cicilan.getText()));
                
                Sequel.AutoComitFalse();
                sukses=true;
                
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
                
                kontraakun="";
                namakontraakun="";
                try {
                    myObj = new FileReader("./cache/peminjampiutang.iyem");
                    root = mapper.readTree(myObj);
                    response = root.path("peminjampiutang");
                    if(response.isArray()){
                       for(JsonNode list:response){
                           if(list.path("Kode").asText().equals(KdPeminjam.getText())){
                                kontraakun=list.path("KodeRekening").asText();  
                                namakontraakun=list.path("NamaRekening").asText();  
                           }
                       }
                    }
                    myObj.close();
                } catch (Exception e) {
                    sukses=false;
                }
                
                if(Sequel.menyimpantf("bayar_piutang_lainlain","?,?,?,?,?,?,?","Pembayaran",7,new String[]{
                        Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPeminjam.getText(),Cicilan.getText(),
                        Keterangan.getText(),NoNota.getText(),koderekening,AkunBayar.getSelectedItem().toString()
                    })==true){
                        if(sisapiutang<=1){
                            Sequel.mengedit("piutang_lainlain","nota_piutang='"+NoNota.getText()+"'","status='Sudah Lunas'");
                        }   
                        Sequel.mengedit("piutang_lainlain","nota_piutang='"+NoNota.getText()+"'","sisapiutang=sisapiutang-"+Cicilan.getText());
                        Sequel.queryu("delete from tampjurnal");                    
                        Sequel.menyimpan("tampjurnal","'"+kontraakun+"','"+namakontraakun+"','0','"+Cicilan.getText()+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+koderekening+"','"+AkunBayar.getSelectedItem()+"','"+Cicilan.getText()+"','0'","Rekening"); 
                        sukses=jur.simpanJurnal(NoNota.getText(),"U","BAYAR PIUTANG PERUSAHAAN/LAIN-LAIN"+", OLEH "+akses.getkode());                   
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
                JOptionPane.showMessageDialog(null,"No.Nota Piutang untuk peminjam "+KdPeminjam.getText()+" "+NmPeminjam.getText()+" tidak ditemukan......!!!");
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Sisa,BtnKeluar);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbKamar.getSelectedRow()>-1){
            Sequel.AutoComitFalse();
            sukses=true;

            if(Sequel.queryu2tf("delete from bayar_piutang_lainlain where tgl_bayar=? and kode_peminjam=? and nota_piutang=? and kd_rek=? and nama_bayar=?", 5,new String[]{
                tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString(),
                tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()
            })==true){
                kontraakun="";
                namakontraakun="";
                try {
                    myObj = new FileReader("./cache/peminjampiutang.iyem");
                    root = mapper.readTree(myObj);
                    response = root.path("peminjampiutang");
                    if(response.isArray()){
                       for(JsonNode list:response){
                           if(list.path("Kode").asText().equals(tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString())){
                                kontraakun=list.path("KodeRekening").asText();  
                                namakontraakun=list.path("NamaRekening").asText();  
                           }
                       }
                    }
                    myObj.close();
                } catch (Exception e) {
                    sukses=false;
                }
                Sequel.mengedit("piutang_lainlain","nota_piutang='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()+"'","status='Belum Lunas', sisapiutang=sisapiutang+"+tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString());                      
                Sequel.queryu("delete from tampjurnal");                    
                Sequel.menyimpan("tampjurnal","'"+kontraakun+"','"+namakontraakun+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString()+"','0'","Rekening");    
                Sequel.menyimpan("tampjurnal","'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString()+"','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"','0','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString()+"'","Rekening"); 
                sukses=jur.simpanJurnal(NoNota.getText(),"U","PEMBATALAN BAYAR PIUTANG PERUSAHAAN/LAIN-LAIN"+", OLEH "+akses.getkode());     
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
            Valid.MyReportqry("rptBayarPiutangLain.jasper","report","::[ Bayar Piutang Perusahaan/Lain-lain ]::",
                "select bayar_piutang_lainlain.tgl_bayar, bayar_piutang_lainlain.kode_peminjam,peminjampiutang.nama_peminjam, bayar_piutang_lainlain.besar_cicilan,"+
                "bayar_piutang_lainlain.keterangan, bayar_piutang_lainlain.nota_piutang,bayar_piutang_lainlain.nama_bayar from bayar_piutang_lainlain "+
                "inner join peminjampiutang on bayar_piutang_lainlain.kode_peminjam=peminjampiutang.kode_peminjam where "+
                "bayar_piutang_lainlain.tgl_bayar between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and bayar_piutang_lainlain.nota_piutang like '%"+TCari.getText()+"%' or "+
                "bayar_piutang_lainlain.tgl_bayar between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and bayar_piutang_lainlain.kode_peminjam like '%"+TCari.getText()+"%' or "+
                "bayar_piutang_lainlain.tgl_bayar between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and peminjampiutang.nama_peminjam like '%"+TCari.getText()+"%' or "+
                "bayar_piutang_lainlain.tgl_bayar between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and bayar_piutang_lainlain.tgl_bayar like '%"+TCari.getText()+"%' "+
                "order by bayar_piutang_lainlain.tgl_bayar,bayar_piutang_lainlain.kode_peminjam,bayar_piutang_lainlain.nota_piutang ",param);
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

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbKamarKeyPressed

private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
   Valid.pindah(evt,Cicilan,KdPeminjam);
}//GEN-LAST:event_KeteranganKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NoNota,KdPeminjam);
    }//GEN-LAST:event_TanggalKeyPressed

    private void CicilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CicilanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if(!Cicilan.getText().equals("")){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())));                           
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if(!Cicilan.getText().equals("")){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())));                           
            }
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if(!Cicilan.getText().equals("")){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())));                           
            }
            BtnSimpan.requestFocus();
        }
    }//GEN-LAST:event_CicilanKeyPressed

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

    private void SisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SisaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SisaKeyPressed

private void ppNotaPiutangBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppNotaPiutangBtnPrintActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnKeluar.requestFocus();
        }else if(Cicilan.getText().equals("")||Cicilan.getText().equals("0")){
            JOptionPane.showMessageDialog(null,"Maaf, pilih dulu data yang mau dicetak notanya...!!!!");
            tbKamar.requestFocus();
        }else if(tabMode.getRowCount()!=0){  
            if(tbKamar.getSelectedRow()>-1){
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                Sequel.menyimpan("temporary","'0','No. Nota',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),5).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'1','Tgl. Bayar',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),0).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'2','Kode Peminjam',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),1).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'3','Nama Peminjam',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),2).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'4','Keterangan',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),4).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'5','Besar Cicilan',': "+Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(tbKamar.getSelectedRow(),3).toString())) +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 

                Valid.panggilUrl("billing/LaporanPiutang.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+Tanggal.getSelectedItem().toString().replaceAll(" ","_")+"&alamatip="+akses.getalamatip()+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());
            }   
        }
        this.setCursor(Cursor.getDefaultCursor());    
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
        Valid.pindah(evt,Tanggal,NoNota);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        if(NoNota.getText().trim().equals("")){
            Valid.textKosong(NoNota,"No.Nota");
        }else{
            if(Sequel.cariInteger("select count(nota_piutang) from piutang_lainlain where nota_piutang=?",NoNota.getText())>0){
                KdPeminjam.setText(Sequel.cariIsi("select kode_peminjam from piutang_lainlain where nota_piutang=?",NoNota.getText()));
                NmPeminjam.setText(Sequel.cariIsi("select nama_peminjam from peminjampiutang where kode_peminjam=?",KdPeminjam.getText()));
                sisapiutang=Sequel.cariIsiAngka("SELECT sisapiutang FROM piutang_lainlain where nota_piutang=?",NoNota.getText());
                Sisa.setText(Valid.SetAngka(Valid.roundUp(sisapiutang,100)));
            }else{
                JOptionPane.showMessageDialog(null,"No.Nota Piutang tidak ditemukan......!!!");
            }
        }
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/akunbayar.iyem")<8){
                tampilAkunBayar2();
            }else{
                tampilAkunBayar();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void KdPeminjamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPeminjamKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPeminjamActionPerformed(null);
        }else{
            Valid.pindah(evt,AkunBayar,Keterangan);
        }
    }//GEN-LAST:event_KdPeminjamKeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        tampilAkunBayar();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganBayarPiutangLain dialog = new KeuanganBayarPiutangLain(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll1;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPeminjam;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.TextBox Cicilan;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.TextBox KdPeminjam;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LTotal;
    private widget.TextBox NmPeminjam;
    private widget.TextBox NoNota;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox Sisa;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label32;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label38;
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
                    "select bayar_piutang_lainlain.tgl_bayar, bayar_piutang_lainlain.kode_peminjam,peminjampiutang.nama_peminjam, bayar_piutang_lainlain.besar_cicilan,"+
                    "bayar_piutang_lainlain.keterangan, bayar_piutang_lainlain.nota_piutang,bayar_piutang_lainlain.kd_rek,bayar_piutang_lainlain.nama_bayar from bayar_piutang_lainlain "+
                    "inner join peminjampiutang on bayar_piutang_lainlain.kode_peminjam=peminjampiutang.kode_peminjam where "+
                    "bayar_piutang_lainlain.tgl_bayar between ? and ? and bayar_piutang_lainlain.nota_piutang like ? or "+
                    "bayar_piutang_lainlain.tgl_bayar between ? and ? and bayar_piutang_lainlain.kode_peminjam like ? or "+
                    "bayar_piutang_lainlain.tgl_bayar between ? and ? and peminjampiutang.nama_peminjam like ? or "+
                    "bayar_piutang_lainlain.tgl_bayar between ? and ? and bayar_piutang_lainlain.tgl_bayar like ? "+
                    "order by bayar_piutang_lainlain.tgl_bayar,bayar_piutang_lainlain.kode_peminjam");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                total=0;
                while(rs.next()){                
                    total=total+rs.getDouble(4);
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)
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
        Kd2.setText("");
        Cicilan.setText("0");
        KdPeminjam.setText("");
        NmPeminjam.setText("");
        Sisa.setText("0");
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        sisapiutang=0;
        NoNota.requestFocus();
    }
    
    public void setData(String nonota,String kodepeminjam,String namapeminjam){
        NoNota.setText(nonota);
        KdPeminjam.setText(kodepeminjam);
        NmPeminjam.setText(namapeminjam);
        TCari.setText(nonota);
        ChkInput.setSelected(true);
        isForm();
        sisapiutang=Sequel.cariIsiAngka("SELECT piutang_lainlain.sisapiutang FROM piutang_lainlain where piutang_lainlain.nota_piutang=?",NoNota.getText());
        Sisa.setText(Valid.SetAngka(Valid.roundUp(sisapiutang,100)));
        if(sisapiutang<=0){
            JOptionPane.showMessageDialog(null,"Piutang sudah lunas..!!");
        }
    }

    private void getData() {
        int row=tbKamar.getSelectedRow();
        if(row!= -1){
            KdPeminjam.setText(tbKamar.getValueAt(row,1).toString());
            NmPeminjam.setText(tbKamar.getValueAt(row,2).toString());
            Cicilan.setText(tbKamar.getValueAt(row,3).toString());
            Keterangan.setText(tbKamar.getValueAt(row,4).toString());
            NoNota.setText(tbKamar.getValueAt(row,5).toString());
            Valid.SetTgl(Tanggal,tbKamar.getValueAt(row,0).toString());
        }
    }

    public JTextField getTextField(){
        return NoNota;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbayar_piutang_lain());
        BtnHapus.setEnabled(akses.getbayar_piutang_lain());
        BtnPrint.setEnabled(akses.getbayar_piutang_lain());
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
             ps=koneksi.prepareStatement("select * from akun_bayar order by akun_bayar.nama_bayar");
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
    
    private void tampilAkunBayar2() {
        try {
            myObj = new FileReader("./cache/akunbayar.iyem");
            root = mapper.readTree(myObj);
            response = root.path("akunbayar");
            if(response.isArray()){
                for(JsonNode list:response){
                    AkunBayar.addItem(list.path("NamaAkun").asText().replaceAll("\"",""));
                }
            }
            myObj.close();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    } 
}

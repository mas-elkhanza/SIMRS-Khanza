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

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgSuplier;
import ipsrs.DlgSuplierIPSRS;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public final class DlgBayarPemesananNonMedis extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();    
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgSuplierIPSRS suplier=new DlgSuplierIPSRS(null,false);
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private double total=0,hutang=0,sisahutang=0;
    private String koderekening="";
    private PreparedStatement ps;
    private ResultSet rs;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgBayarPemesananNonMedis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"Tgl.Bayar","Tgl.Faktur","Tgl.Datang","Tgl.Tempo","No.Faktur","Supplier","Akun Bayar","No.Bukti","Pembayaran","Keterangan","Petugas","Nip","Nama"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class, 
                java.lang.Object.class,java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
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

        for (int i = 0; i < 13; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(140);
            }else if(i==6){
                column.setPreferredWidth(140);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(140);
            }else if(i==10){
                column.setPreferredWidth(180);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        no_faktur.setDocument(new batasInput((byte)20).getKata(no_faktur));
        besar_bayar.setDocument(new batasInput((byte)15).getKata(besar_bayar));
        no_bukti.setDocument(new batasInput((byte)20).getKata(no_bukti));
        nip.setDocument(new batasInput((byte)20).getKata(nip));
        keterangan.setDocument(new batasInput((byte)100).getKata(keterangan));
        
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
                    nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    nama_petugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());                    
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
        
        suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(suplier.getTable().getSelectedRow()!= -1){
                    kdsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),0).toString());
                    nmsup.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
                    tampil();
                }      
                kdsup.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {suplier.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        suplier.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    suplier.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        Valid.loadCombo(nama_bayar,"nama_bayar","akun_bayar");
        
        
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
        internalFrame1 = new widget.InternalFrame();
        panelisi4 = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        no_faktur = new widget.TextBox();
        label36 = new widget.Label();
        no_bukti = new widget.TextBox();
        label35 = new widget.Label();
        besar_bayar = new widget.TextBox();
        label16 = new widget.Label();
        nip = new widget.TextBox();
        nama_petugas = new widget.TextBox();
        tgl_bayar = new widget.Tanggal();
        label38 = new widget.Label();
        sisa_hutang = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel10 = new widget.Label();
        nama_bayar = new widget.ComboBox();
        keterangan = new widget.TextBox();
        label39 = new widget.Label();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        label12 = new widget.Label();
        LTotal = new widget.Label();
        BtnKeluar = new widget.Button();
        panelisi5 = new widget.panelisi();
        label33 = new widget.Label();
        TglCari1 = new widget.Tanggal();
        label37 = new widget.Label();
        TglCari2 = new widget.Tanggal();
        label19 = new widget.Label();
        kdsup = new widget.TextBox();
        nmsup = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Bayar Pemesanan Barang Non Medis dan Penunjang ( Lab & RO ) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 104));
        panelisi4.setLayout(null);

        label34.setText("No.Faktur :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label34);
        label34.setBounds(0, 10, 77, 23);

        label32.setText("Tgl.Bayar :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(0, 40, 77, 23);

        no_faktur.setEditable(false);
        no_faktur.setHighlighter(null);
        no_faktur.setName("no_faktur"); // NOI18N
        no_faktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                no_fakturKeyPressed(evt);
            }
        });
        panelisi4.add(no_faktur);
        no_faktur.setBounds(80, 10, 170, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(285, 40, 90, 23);

        no_bukti.setHighlighter(null);
        no_bukti.setName("no_bukti"); // NOI18N
        no_bukti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_buktiActionPerformed(evt);
            }
        });
        no_bukti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                no_buktiKeyPressed(evt);
            }
        });
        panelisi4.add(no_bukti);
        no_bukti.setBounds(610, 40, 100, 23);

        label35.setText("Pembayaran :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label35);
        label35.setBounds(285, 70, 90, 23);

        besar_bayar.setText("0");
        besar_bayar.setHighlighter(null);
        besar_bayar.setName("besar_bayar"); // NOI18N
        besar_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                besar_bayarKeyPressed(evt);
            }
        });
        panelisi4.add(besar_bayar);
        besar_bayar.setBounds(378, 70, 100, 23);

        label16.setText("Petugas :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label16);
        label16.setBounds(285, 10, 90, 23);

        nip.setName("nip"); // NOI18N
        nip.setPreferredSize(new java.awt.Dimension(80, 23));
        nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nipKeyPressed(evt);
            }
        });
        panelisi4.add(nip);
        nip.setBounds(378, 10, 100, 23);

        nama_petugas.setEditable(false);
        nama_petugas.setName("nama_petugas"); // NOI18N
        nama_petugas.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nama_petugas);
        nama_petugas.setBounds(480, 10, 200, 23);

        tgl_bayar.setDisplayFormat("dd-MM-yyyy");
        tgl_bayar.setName("tgl_bayar"); // NOI18N
        tgl_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_bayarKeyPressed(evt);
            }
        });
        panelisi4.add(tgl_bayar);
        tgl_bayar.setBounds(80, 40, 100, 23);

        label38.setText("Sisa Hutang :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label38);
        label38.setBounds(530, 70, 77, 23);

        sisa_hutang.setEditable(false);
        sisa_hutang.setText("0");
        sisa_hutang.setHighlighter(null);
        sisa_hutang.setName("sisa_hutang"); // NOI18N
        sisa_hutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sisa_hutangKeyPressed(evt);
            }
        });
        panelisi4.add(sisa_hutang);
        sisa_hutang.setBounds(610, 70, 100, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('1');
        BtnPetugas.setToolTipText("ALt+1");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        panelisi4.add(BtnPetugas);
        BtnPetugas.setBounds(682, 10, 28, 23);

        jLabel10.setText("Akun Bayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi4.add(jLabel10);
        jLabel10.setBounds(0, 70, 77, 23);

        nama_bayar.setName("nama_bayar"); // NOI18N
        nama_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nama_bayarKeyPressed(evt);
            }
        });
        panelisi4.add(nama_bayar);
        nama_bayar.setBounds(80, 70, 200, 23);

        keterangan.setHighlighter(null);
        keterangan.setName("keterangan"); // NOI18N
        keterangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keteranganActionPerformed(evt);
            }
        });
        keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keteranganKeyPressed(evt);
            }
        });
        panelisi4.add(keterangan);
        keterangan.setBounds(378, 40, 160, 23);

        label39.setText("No.Bukti :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(530, 40, 77, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 145));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
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
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(LCount);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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

        label12.setText("Total :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(105, 30));
        panelisi1.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(195, 30));
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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label33.setText("Tgl.Bayar :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi5.add(label33);

        TglCari1.setDisplayFormat("dd-MM-yyyy");
        TglCari1.setName("TglCari1"); // NOI18N
        TglCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        TglCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglCari1KeyPressed(evt);
            }
        });
        panelisi5.add(TglCari1);

        label37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label37.setText("s.d.");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(25, 23));
        panelisi5.add(label37);

        TglCari2.setDisplayFormat("dd-MM-yyyy");
        TglCari2.setName("TglCari2"); // NOI18N
        TglCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        TglCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglCari2KeyPressed(evt);
            }
        });
        panelisi5.add(TglCari2);

        label19.setText("Supplier :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi5.add(label19);

        kdsup.setName("kdsup"); // NOI18N
        kdsup.setPreferredSize(new java.awt.Dimension(75, 23));
        kdsup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdsupKeyPressed(evt);
            }
        });
        panelisi5.add(kdsup);

        nmsup.setEditable(false);
        nmsup.setName("nmsup"); // NOI18N
        nmsup.setPreferredSize(new java.awt.Dimension(200, 23));
        panelisi5.add(nmsup);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
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
        panelisi5.add(BtnSeek2);

        jPanel1.add(panelisi5, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void no_fakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_no_fakturKeyPressed
        Valid.pindah(evt,TCari,tgl_bayar);
}//GEN-LAST:event_no_fakturKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(no_faktur.getText().trim().equals("")){
            Valid.textKosong(no_faktur,"No.Faktur");
        }else if(besar_bayar.getText().trim().equals("")||besar_bayar.getText().trim().equals("0")){
            Valid.textKosong(besar_bayar,"Pembayaran");
        }else if(nama_petugas.getText().trim().equals("")){
            Valid.textKosong(nip,"Petugas");
        }else if(no_bukti.getText().trim().equals("")){
            Valid.textKosong(no_bukti,"No.Bukti");
        }else{            
            try {
                    
                if(sisahutang>0){
                    koderekening=Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",nama_bayar.getSelectedItem().toString());
                    Sequel.queryu("delete from tampjurnal");
                    Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                        Sequel.cariIsi("select Bayar_Pemesanan_Non_Medis from set_akun"),"HUTANG USAHA",besar_bayar.getText(),"0"
                    });                     
                    Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                        koderekening,nama_bayar.getSelectedItem().toString(),"0",besar_bayar.getText()
                    });    
                    jur.simpanJurnal(no_bukti.getText(),Valid.SetTgl(tgl_bayar.getSelectedItem()+""),"U","BAYAR PELUNASAN BARANG NON MEDIS NO.FAKTUR "+no_faktur.getText()+", OLEH "+akses.getkode());
                    if((sisahutang<=Double.parseDouble(besar_bayar.getText()))||(sisahutang<=-Double.parseDouble(besar_bayar.getText()))){
                        Sequel.mengedit("ipsrspemesanan","no_faktur=?","status='Sudah Dibayar'",1,new String[]{no_faktur.getText()});
                    }else{
                        Sequel.mengedit("ipsrspemesanan","no_faktur=?","status='Belum Lunas'",1,new String[]{no_faktur.getText()});
                    }
                    Sequel.menyimpan("bayar_pemesanan_non_medis","?,?,?,?,?,?,?", 7,new String[]{
                        Valid.SetTgl(tgl_bayar.getSelectedItem()+""),no_faktur.getText(),nip.getText(),
                        besar_bayar.getText(),keterangan.getText(),nama_bayar.getSelectedItem().toString(),
                        no_bukti.getText()
                    });
                }else{
                    //Sequel.mengedit("ipsrspemesanan","no_faktur=?","status='Belum Dibayar'",1,new String[]{no_faktur.getText()});
                    JOptionPane.showMessageDialog(rootPane,"Maaf sudah dilakukan pembayaran..!!!");
                    TCari.requestFocus();
                }                   
                
           }catch (Exception ex) {
               System.out.println(ex);
           }                     
           BtnCariActionPerformed(evt);
           emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,sisa_hutang,BtnKeluar);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        try {
              
            Sequel.queryu2("delete from bayar_pemesanan_non_medis where tgl_bayar=? and no_faktur=? and "+
                    "nip=? and besar_bayar=? and keterangan=? and nama_bayar=? and no_bukti=?",7,new String[]{
                tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),4).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),11).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),9).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()
            });
            koderekening=Sequel.cariIsi("select kd_rek from akun_bayar where nama_bayar=?",nama_bayar.getSelectedItem().toString());
            Sequel.queryu("delete from tampjurnal");
            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                koderekening,nama_bayar.getSelectedItem().toString(),besar_bayar.getText(),"0"
            });    
            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                Sequel.cariIsi("select Bayar_Pemesanan_Non_Medis from set_akun"),"HUTANG USAHA","0",besar_bayar.getText()
            }); 
            jur.simpanJurnal(no_bukti.getText(),Valid.SetTgl(tgl_bayar.getSelectedItem()+""),"U","BATAL BAYAR PELUNASAN BARANG NON MEDIS NO.FAKTUR "+no_faktur.getText()+", OLEH "+akses.getkode());            
            if(Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString())==Double.parseDouble(besar_bayar.getText())){
                Sequel.mengedit("ipsrspemesanan","no_faktur=?","status='Belum Dibayar'",1,new String[]{no_faktur.getText()});
            }else{
                Sequel.mengedit("ipsrspemesanan","no_faktur=?","status='Belum Lunas'",1,new String[]{no_faktur.getText()});
            }
            
        }catch (Exception ex) {
            System.out.println(ex);
        }              
        BtnCariActionPerformed(evt);
        emptTeks();
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
            Valid.MyReportqry("rptBayarPemesanan.jasper","report","::[ Bayar Pemesanan ]::",
                    "select bayar_pemesanan_non_medis.tgl_bayar,ipsrspemesanan.tgl_faktur,ipsrspemesanan.tgl_pesan,"+
                    "ipsrspemesanan.tgl_tempo, bayar_pemesanan_non_medis.no_faktur,"+
                    "ipsrssuplier.nama_suplier,bayar_pemesanan_non_medis.nama_bayar,bayar_pemesanan_non_medis.no_bukti,"+
                    "bayar_pemesanan_non_medis.besar_bayar,bayar_pemesanan_non_medis.keterangan,"+
                    "bayar_pemesanan_non_medis.nip,petugas.nama from bayar_pemesanan_non_medis inner join petugas "+
                    "inner join ipsrspemesanan inner join ipsrssuplier on bayar_pemesanan_non_medis.nip=petugas.nip "+
                    "and bayar_pemesanan_non_medis.no_faktur=ipsrspemesanan.no_faktur "+
                    "and ipsrspemesanan.kode_suplier=ipsrssuplier.kode_suplier where "+
                    "bayar_pemesanan_non_medis.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and ipsrssuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and bayar_pemesanan_non_medis.no_faktur like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and ipsrssuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and ipsrssuplier.nama_suplier like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and ipsrssuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and bayar_pemesanan_non_medis.nama_bayar like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and ipsrssuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and bayar_pemesanan_non_medis.no_bukti like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and ipsrssuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and bayar_pemesanan_non_medis.keterangan like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and ipsrssuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' "+
                    " order by bayar_pemesanan_non_medis.tgl_bayar",param);
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

private void no_buktiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_no_buktiKeyPressed
   Valid.pindah(evt,keterangan,besar_bayar);
}//GEN-LAST:event_no_buktiKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void tgl_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_bayarKeyPressed
        Valid.pindah(evt,no_faktur,nama_bayar);
    }//GEN-LAST:event_tgl_bayarKeyPressed

    private void besar_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_besar_bayarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){            
            if(!besar_bayar.getText().equals("")){   
                try {
                    sisa_hutang.setText(Valid.SetAngka(sisahutang-Double.parseDouble(besar_bayar.getText())));                           
                } catch (Exception e) {
                    sisa_hutang.setText("0");
                }
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(!besar_bayar.getText().equals("")){   
                try {
                    sisa_hutang.setText(Valid.SetAngka(sisahutang-Double.parseDouble(besar_bayar.getText())));                           
                } catch (Exception e) {
                    sisa_hutang.setText("0");
                }
            }
            no_bukti.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(!besar_bayar.getText().equals("")){   
                try {
                    sisa_hutang.setText(Valid.SetAngka(sisahutang-Double.parseDouble(besar_bayar.getText())));                           
                } catch (Exception e) {
                    sisa_hutang.setText("0");
                }
            }
            BtnSimpan.requestFocus();
        }
    }//GEN-LAST:event_besar_bayarKeyPressed

    private void nipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nama_petugas,nip.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            nama_bayar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            keterangan.requestFocus();
        }
    }//GEN-LAST:event_nipKeyPressed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdsup.setText("");
        nmsup.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void sisa_hutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sisa_hutangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sisa_hutangKeyPressed

private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPetugasActionPerformed

    private void nama_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nama_bayarKeyPressed
        Valid.pindah(evt,tgl_bayar,nip);
    }//GEN-LAST:event_nama_bayarKeyPressed

    private void no_buktiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_no_buktiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_no_buktiActionPerformed

    private void keteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keteranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keteranganActionPerformed

    private void keteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keteranganKeyPressed
        Valid.pindah(evt,nip,no_bukti);
    }//GEN-LAST:event_keteranganKeyPressed

    private void TglCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglCari1KeyPressed

    private void TglCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglCari2KeyPressed

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmsup,kdsup.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmsup,kdsup.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmsup,kdsup.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdsupKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        suplier.isCek();
        suplier.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suplier.setLocationRelativeTo(internalFrame1);
        suplier.setAlwaysOnTop(false);
        suplier.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBayarPemesananNonMedis dialog = new DlgBayarPemesananNonMedis(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private widget.Label LTotal;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal TglCari1;
    private widget.Tanggal TglCari2;
    private widget.TextBox besar_bayar;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdsup;
    private widget.TextBox keterangan;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label16;
    private widget.Label label19;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label9;
    private widget.ComboBox nama_bayar;
    private widget.TextBox nama_petugas;
    private widget.TextBox nip;
    private widget.TextBox nmsup;
    private widget.TextBox no_bukti;
    private widget.TextBox no_faktur;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private widget.TextBox sisa_hutang;
    private widget.Table tbKamar;
    private widget.Tanggal tgl_bayar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{           
            ps=koneksi.prepareStatement(
                    "select bayar_pemesanan_non_medis.tgl_bayar,ipsrspemesanan.tgl_faktur,ipsrspemesanan.tgl_pesan,"+
                    "ipsrspemesanan.tgl_tempo, bayar_pemesanan_non_medis.no_faktur,"+
                    "ipsrssuplier.nama_suplier,bayar_pemesanan_non_medis.nama_bayar,bayar_pemesanan_non_medis.no_bukti,"+
                    "bayar_pemesanan_non_medis.besar_bayar,bayar_pemesanan_non_medis.keterangan,"+
                    "bayar_pemesanan_non_medis.nip,petugas.nama from bayar_pemesanan_non_medis inner join petugas "+
                    "inner join ipsrspemesanan inner join ipsrssuplier on bayar_pemesanan_non_medis.nip=petugas.nip "+
                    "and bayar_pemesanan_non_medis.no_faktur=ipsrspemesanan.no_faktur "+
                    "and ipsrspemesanan.kode_suplier=ipsrssuplier.kode_suplier where "+
                    "bayar_pemesanan_non_medis.tgl_bayar between ? and ? and ipsrssuplier.nama_suplier like ? and bayar_pemesanan_non_medis.no_faktur like ? or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between ? and ? and ipsrssuplier.nama_suplier like ? and ipsrssuplier.nama_suplier like ? or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between ? and ? and ipsrssuplier.nama_suplier like ? and bayar_pemesanan_non_medis.nama_bayar like ? or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between ? and ? and ipsrssuplier.nama_suplier like ? and bayar_pemesanan_non_medis.no_bukti like ? or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between ? and ? and ipsrssuplier.nama_suplier like ? and bayar_pemesanan_non_medis.keterangan like ? or "+
                    "bayar_pemesanan_non_medis.tgl_bayar between ? and ? and ipsrssuplier.nama_suplier like ? and petugas.nama like ? "+
                    " order by bayar_pemesanan_non_medis.tgl_bayar");
            try {            
                ps.setString(1,Valid.SetTgl(TglCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(TglCari2.getSelectedItem()+""));
                ps.setString(3,"%"+nmsup.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText()+"%");
                ps.setString(5,Valid.SetTgl(TglCari1.getSelectedItem()+""));
                ps.setString(6,Valid.SetTgl(TglCari2.getSelectedItem()+""));
                ps.setString(7,"%"+nmsup.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText()+"%");
                ps.setString(9,Valid.SetTgl(TglCari1.getSelectedItem()+""));
                ps.setString(10,Valid.SetTgl(TglCari2.getSelectedItem()+""));
                ps.setString(11,"%"+nmsup.getText().trim()+"%");
                ps.setString(12,"%"+TCari.getText()+"%");
                ps.setString(13,Valid.SetTgl(TglCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(TglCari2.getSelectedItem()+""));
                ps.setString(15,"%"+nmsup.getText().trim()+"%");
                ps.setString(16,"%"+TCari.getText()+"%");
                ps.setString(17,Valid.SetTgl(TglCari1.getSelectedItem()+""));
                ps.setString(18,Valid.SetTgl(TglCari2.getSelectedItem()+""));
                ps.setString(19,"%"+nmsup.getText().trim()+"%");
                ps.setString(20,"%"+TCari.getText()+"%");
                ps.setString(21,Valid.SetTgl(TglCari1.getSelectedItem()+""));
                ps.setString(22,Valid.SetTgl(TglCari2.getSelectedItem()+""));
                ps.setString(23,"%"+nmsup.getText().trim()+"%");
                ps.setString(24,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                total=0;
                while(rs.next()){                
                    total=total+rs.getDouble("besar_bayar");
                    tabMode.addRow(new Object[]{
                        rs.getString("tgl_bayar"),rs.getString("tgl_faktur"),rs.getString("tgl_pesan"),
                        rs.getString("tgl_tempo"),rs.getString("no_faktur"),rs.getString("nama_suplier"),rs.getString("nama_bayar"),
                        rs.getString("no_bukti"),rs.getDouble("besar_bayar"),rs.getString("keterangan"),
                        rs.getString("nip")+" "+rs.getString("nama"),rs.getString("nip"),rs.getString("nama")}
                    );
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
        LTotal.setText(Valid.SetAngka(total));
    }

    public void emptTeks() {
        Kd2.setText("");
        besar_bayar.setText("0");
        setData(no_faktur.getText());
        no_bukti.setText("");
        keterangan.setText("");
        tgl_bayar.setDate(new Date());
        tgl_bayar.requestFocus();
    }
    
    public void setData(String nofaktur){
        no_faktur.setText(nofaktur);
        TCari.setText(nofaktur);
        sisahutang=Math.round(Sequel.cariIsiAngka("SELECT tagihan FROM ipsrspemesanan where no_faktur=?",nofaktur)
                   -Sequel.cariIsiAngka("SELECT ifnull(SUM(besar_bayar),0) FROM bayar_pemesanan_non_medis where no_faktur=?",nofaktur));
        sisa_hutang.setText(Valid.SetAngka(sisahutang));
        besar_bayar.setText("0");
    }

    private void getData() {
        int row=tbKamar.getSelectedRow();
        if(row!= -1){
            Valid.SetTgl(tgl_bayar,tbKamar.getValueAt(row,0).toString());
            no_faktur.setText(tbKamar.getValueAt(row,4).toString());
            //setData(no_faktur.getText());
            nama_bayar.setSelectedItem(tbKamar.getValueAt(row,6).toString());
            no_bukti.setText(tbKamar.getValueAt(row,7).toString());
            besar_bayar.setText(Valid.SetAngka5(Double.parseDouble(tbKamar.getValueAt(row,8).toString())));
            keterangan.setText(tbKamar.getValueAt(row,9).toString());
        }
    }

    public JTextField getTextField(){
        return no_faktur;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbayar_pesan_non_medis());
        BtnHapus.setEnabled(akses.getbayar_pesan_non_medis());
        BtnPrint.setEnabled(akses.getbayar_pesan_non_medis());
        if(akses.getjml2()>=1){
            nip.setEditable(false);
            BtnPetugas.setEnabled(false);
            nip.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getbayar_pesan_non_medis());
            Sequel.cariIsi("select nama from petugas where nip=?", nama_petugas,nip.getText());
        }else if(akses.getjml1()>=1){
            nip.setEditable(true);
            BtnPetugas.setEnabled(true);
            BtnSimpan.setEnabled(true);
        }   
    }
}

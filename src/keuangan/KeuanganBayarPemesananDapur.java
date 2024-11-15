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

import bridging.MandiriCariKodeTransaksiTujuanTransfer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import dapur.DapurCariSuplier;
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
public final class KeuanganBayarPemesananDapur extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();    
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DapurCariSuplier suplier=new DapurCariSuplier(null,false);
    private boolean sukses=false;
    private double total=0,sisahutang=0;
    private MandiriCariKodeTransaksiTujuanTransfer kodetransaksibank=new MandiriCariKodeTransaksiTujuanTransfer(null, false);
    private String koderekening="";
    private PreparedStatement ps;
    private ResultSet rs;
    private File file;
    private FileWriter fileWriter;
    private String iyem,Bayar_Pemesanan_Dapur=Sequel.cariIsi("select set_akun2.Bayar_Pemesanan_Dapur from set_akun2"),Host_to_Host_Bank_Mandiri="",Akun_Biaya_Mandiri="",kodemcm="",norekening="";
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public KeuanganBayarPemesananDapur(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        DlgBayarMandiri.setSize(562,163);

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
                column.setPreferredWidth(170);
            }else if(i==7){
                column.setPreferredWidth(150);
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
        
        NoFaktur.setDocument(new batasInput((byte)20).getKata(NoFaktur));
        BesarBayar.setDocument(new batasInput((byte)15).getKata(BesarBayar));
        NoBukti.setDocument(new batasInput((byte)30).getKata(NoBukti));
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
        
        kodetransaksibank.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kodetransaksibank.getTable().getSelectedRow()!= -1){                   
                    KodeMetode.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),0).toString());   
                    MetodePembayaran.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),1).toString());   
                    BiayaTransaksi.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),2).toString());   
                    KodeBank.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),3).toString());   
                    BankTujuan.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),4).toString());   
                    KodeTransaksi.setText(kodetransaksibank.getTable().getValueAt(kodetransaksibank.getTable().getSelectedRow(),5).toString());                  
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
        
        kodetransaksibank.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kodetransaksibank.dispose();
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
        DlgBayarMandiri = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        jLabel99 = new widget.Label();
        BtnKeluarMandiri = new widget.Button();
        BtnSimpanMandiri = new widget.Button();
        NoRekening = new widget.TextBox();
        RekeningAtasNama = new widget.TextBox();
        KotaAtasNamaRekening = new widget.TextBox();
        BtnPetugas1 = new widget.Button();
        jLabel102 = new widget.Label();
        BiayaTransaksi = new widget.TextBox();
        KodeMetode = new widget.TextBox();
        MetodePembayaran = new widget.TextBox();
        jLabel103 = new widget.Label();
        KodeBank = new widget.TextBox();
        BankTujuan = new widget.TextBox();
        KodeTransaksi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi4 = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        NoFaktur = new widget.TextBox();
        label36 = new widget.Label();
        NoBukti = new widget.TextBox();
        label35 = new widget.Label();
        BesarBayar = new widget.TextBox();
        label16 = new widget.Label();
        nip = new widget.TextBox();
        nama_petugas = new widget.TextBox();
        TglBayar = new widget.Tanggal();
        sisa_hutang = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel10 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        keterangan = new widget.TextBox();
        label39 = new widget.Label();
        BtnAll1 = new widget.Button();
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

        DlgBayarMandiri.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgBayarMandiri.setName("DlgBayarMandiri"); // NOI18N
        DlgBayarMandiri.setUndecorated(true);
        DlgBayarMandiri.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Pembayaran Pihak Ke 3 Bank Mandir ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        jLabel99.setText("No.Rekening :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelBiasa2.add(jLabel99);
        jLabel99.setBounds(0, 10, 80, 23);

        BtnKeluarMandiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarMandiri.setMnemonic('U');
        BtnKeluarMandiri.setText("Batal");
        BtnKeluarMandiri.setToolTipText("Alt+U");
        BtnKeluarMandiri.setName("BtnKeluarMandiri"); // NOI18N
        BtnKeluarMandiri.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarMandiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarMandiriActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluarMandiri);
        BtnKeluarMandiri.setBounds(442, 100, 100, 30);

        BtnSimpanMandiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanMandiri.setMnemonic('S');
        BtnSimpanMandiri.setText("Simpan");
        BtnSimpanMandiri.setToolTipText("Alt+S");
        BtnSimpanMandiri.setName("BtnSimpanMandiri"); // NOI18N
        BtnSimpanMandiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanMandiriActionPerformed(evt);
            }
        });
        BtnSimpanMandiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanMandiriKeyPressed(evt);
            }
        });
        panelBiasa2.add(BtnSimpanMandiri);
        BtnSimpanMandiri.setBounds(10, 100, 100, 30);

        NoRekening.setEditable(false);
        NoRekening.setHighlighter(null);
        NoRekening.setName("NoRekening"); // NOI18N
        panelBiasa2.add(NoRekening);
        NoRekening.setBounds(84, 10, 130, 23);

        RekeningAtasNama.setEditable(false);
        RekeningAtasNama.setHighlighter(null);
        RekeningAtasNama.setName("RekeningAtasNama"); // NOI18N
        panelBiasa2.add(RekeningAtasNama);
        RekeningAtasNama.setBounds(217, 10, 200, 23);

        KotaAtasNamaRekening.setEditable(false);
        KotaAtasNamaRekening.setHighlighter(null);
        KotaAtasNamaRekening.setName("KotaAtasNamaRekening"); // NOI18N
        panelBiasa2.add(KotaAtasNamaRekening);
        KotaAtasNamaRekening.setBounds(420, 10, 120, 23);

        BtnPetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas1.setMnemonic('1');
        BtnPetugas1.setToolTipText("ALt+1");
        BtnPetugas1.setName("BtnPetugas1"); // NOI18N
        BtnPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugas1ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPetugas1);
        BtnPetugas1.setBounds(512, 40, 28, 23);

        jLabel102.setText("Metode :");
        jLabel102.setName("jLabel102"); // NOI18N
        panelBiasa2.add(jLabel102);
        jLabel102.setBounds(0, 40, 80, 23);

        BiayaTransaksi.setEditable(false);
        BiayaTransaksi.setHighlighter(null);
        BiayaTransaksi.setName("BiayaTransaksi"); // NOI18N
        panelBiasa2.add(BiayaTransaksi);
        BiayaTransaksi.setBounds(399, 40, 110, 23);

        KodeMetode.setEditable(false);
        KodeMetode.setHighlighter(null);
        KodeMetode.setName("KodeMetode"); // NOI18N
        panelBiasa2.add(KodeMetode);
        KodeMetode.setBounds(84, 40, 64, 23);

        MetodePembayaran.setEditable(false);
        MetodePembayaran.setHighlighter(null);
        MetodePembayaran.setName("MetodePembayaran"); // NOI18N
        panelBiasa2.add(MetodePembayaran);
        MetodePembayaran.setBounds(151, 40, 245, 23);

        jLabel103.setText("Bank Tujuan :");
        jLabel103.setName("jLabel103"); // NOI18N
        panelBiasa2.add(jLabel103);
        jLabel103.setBounds(0, 70, 80, 23);

        KodeBank.setEditable(false);
        KodeBank.setHighlighter(null);
        KodeBank.setName("KodeBank"); // NOI18N
        panelBiasa2.add(KodeBank);
        KodeBank.setBounds(84, 70, 64, 23);

        BankTujuan.setEditable(false);
        BankTujuan.setHighlighter(null);
        BankTujuan.setName("BankTujuan"); // NOI18N
        panelBiasa2.add(BankTujuan);
        BankTujuan.setBounds(151, 70, 245, 23);

        KodeTransaksi.setEditable(false);
        KodeTransaksi.setHighlighter(null);
        KodeTransaksi.setName("KodeTransaksi"); // NOI18N
        panelBiasa2.add(KodeTransaksi);
        KodeTransaksi.setBounds(399, 70, 141, 23);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgBayarMandiri.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Bayar Pemesanan Barang Dapur Kering & Basah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 104));
        panelisi4.setLayout(null);

        label34.setText("No.Faktur :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label34);
        label34.setBounds(0, 10, 70, 23);

        label32.setText("Tgl.Bayar :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(0, 40, 70, 23);

        NoFaktur.setEditable(false);
        NoFaktur.setHighlighter(null);
        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisi4.add(NoFaktur);
        NoFaktur.setBounds(74, 10, 191, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(294, 40, 90, 23);

        NoBukti.setHighlighter(null);
        NoBukti.setName("NoBukti"); // NOI18N
        NoBukti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoBuktiActionPerformed(evt);
            }
        });
        NoBukti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBuktiKeyPressed(evt);
            }
        });
        panelisi4.add(NoBukti);
        NoBukti.setBounds(610, 40, 100, 23);

        label35.setText("Bayar :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label35);
        label35.setBounds(0, 70, 70, 23);

        BesarBayar.setText("0");
        BesarBayar.setHighlighter(null);
        BesarBayar.setName("BesarBayar"); // NOI18N
        BesarBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BesarBayarKeyPressed(evt);
            }
        });
        panelisi4.add(BesarBayar);
        BesarBayar.setBounds(74, 70, 94, 23);

        label16.setText("Petugas :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label16);
        label16.setBounds(294, 10, 90, 23);

        nip.setName("nip"); // NOI18N
        nip.setPreferredSize(new java.awt.Dimension(80, 23));
        nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nipKeyPressed(evt);
            }
        });
        panelisi4.add(nip);
        nip.setBounds(388, 10, 100, 23);

        nama_petugas.setEditable(false);
        nama_petugas.setName("nama_petugas"); // NOI18N
        nama_petugas.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nama_petugas);
        nama_petugas.setBounds(490, 10, 190, 23);

        TglBayar.setDisplayFormat("dd-MM-yyyy");
        TglBayar.setName("TglBayar"); // NOI18N
        TglBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBayarKeyPressed(evt);
            }
        });
        panelisi4.add(TglBayar);
        TglBayar.setBounds(74, 40, 100, 23);

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
        sisa_hutang.setBounds(171, 70, 94, 23);

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
        jLabel10.setBounds(294, 70, 90, 23);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        panelisi4.add(AkunBayar);
        AkunBayar.setBounds(388, 70, 292, 23);

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
        keterangan.setBounds(388, 40, 160, 23);

        label39.setText("No.Bukti :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(530, 40, 77, 23);

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
        panelisi4.add(BtnAll1);
        BtnAll1.setBounds(682, 70, 28, 23);

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

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt,TCari,TglBayar);
}//GEN-LAST:event_NoFakturKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoFaktur.getText().trim().equals("")){
            Valid.textKosong(NoFaktur,"No.Faktur");
        }else if(BesarBayar.getText().trim().equals("")||BesarBayar.getText().trim().equals("0")){
            Valid.textKosong(BesarBayar,"Pembayaran");
        }else if(nama_petugas.getText().trim().equals("")){
            Valid.textKosong(nip,"Petugas");
        }else if(AkunBayar.getSelectedItem().toString().trim().equals("")){
            Valid.textKosong(AkunBayar,"Akun Bayar");
        }else if(NoBukti.getText().trim().equals("")){
            Valid.textKosong(NoBukti,"No.Bukti");
        }else{            
            try {   
                if(sisahutang>0){
                    koderekening="";
                    try {
                        myObj = new FileReader("./cache/akunbayarhutang.iyem");
                        root = mapper.readTree(myObj);
                        response = root.path("akunbayarhutang");
                        if(response.isArray()){
                           for(JsonNode list:response){
                               if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                                    koderekening=list.path("KodeRek").asText();  
                               }
                           }
                        }
                        myObj.close();
                    } catch (Exception e) {
                        koderekening="";
                    }
                    
                    if(koderekening.equals("")){
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan akun bayar, silahkan hubungi administrator..!!");
                    }else{
                        if(koderekening.equals(Host_to_Host_Bank_Mandiri)){
                            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pembayaran_pihak_ke3_bankmandiri.nomor_pembayaran,6),signed)),0) from pembayaran_pihak_ke3_bankmandiri where left(pembayaran_pihak_ke3_bankmandiri.tgl_pembayaran,10)='"+Valid.SetTgl(TglBayar.getSelectedItem()+"")+"' ",kodemcm+"14"+TglBayar.getSelectedItem().toString().replaceAll("-",""),6,NoBukti); 
                            ps=koneksi.prepareStatement(
                                    "select dapursuplier.nama_suplier,dapursuplier.kota,dapursuplier.nama_bank,dapursuplier.rekening "+
                                    "from dapurpemesanan inner join dapursuplier on dapurpemesanan.kode_suplier=dapursuplier.kode_suplier "+
                                    "where dapurpemesanan.no_faktur=?");
                            try{
                                ps.setString(1,NoFaktur.getText());
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    RekeningAtasNama.setText(rs.getString("nama_suplier"));
                                    KotaAtasNamaRekening.setText(rs.getString("kota"));
                                    NoRekening.setText(rs.getString("rekening"));
                                    BankTujuan.setText(rs.getString("nama_bank"));
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
                            
                            DlgBayarMandiri.setLocationRelativeTo(internalFrame1);
                            DlgBayarMandiri.setVisible(true);
                        }else{
                            Sequel.AutoComitFalse();
                            sukses=true;

                            Sequel.queryu("delete from tampjurnal");
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                Bayar_Pemesanan_Dapur,"HUTANG USAHA",BesarBayar.getText(),"0"
                            });                     
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                koderekening,AkunBayar.getSelectedItem().toString(),"0",BesarBayar.getText()
                            });    
                            sukses=jur.simpanJurnal(NoBukti.getText(),"U","BAYAR PELUNASAN BARANG DAPUR NO.FAKTUR "+NoFaktur.getText()+", OLEH "+akses.getkode());

                            if(sukses==true){
                                if((sisahutang<=Double.parseDouble(BesarBayar.getText()))||(sisahutang<=-Double.parseDouble(BesarBayar.getText()))){
                                    Sequel.mengedit("dapurpemesanan","no_faktur=?","status='Sudah Dibayar'",1,new String[]{NoFaktur.getText()});
                                }else{
                                    Sequel.mengedit("dapurpemesanan","no_faktur=?","status='Belum Lunas'",1,new String[]{NoFaktur.getText()});
                                }
                                if(Sequel.menyimpantf2("bayar_pemesanan_dapur","?,?,?,?,?,?,?","data", 7,new String[]{
                                    Valid.SetTgl(TglBayar.getSelectedItem()+""),NoFaktur.getText(),nip.getText(),
                                    BesarBayar.getText(),keterangan.getText(),AkunBayar.getSelectedItem().toString(),
                                    NoBukti.getText()
                                })==false){
                                    sukses=false;
                                }
                            }

                            if(sukses==true){
                                Sequel.Commit();
                                BtnCariActionPerformed(evt);
                                emptTeks();
                            }else{
                                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                                Sequel.RollBack();
                            }

                            Sequel.AutoComitTrue();
                        }
                    }   
                }else{
                    JOptionPane.showMessageDialog(rootPane,"Maaf sudah dilakukan pembayaran..!!!");
                    TCari.requestFocus();
                }                   
                
           }catch (Exception ex) {
               System.out.println(ex);
           }             
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
            Sequel.AutoComitFalse();
            sukses=true;   
            if(Sequel.queryu2tf("delete from bayar_pemesanan_dapur where tgl_bayar=? and no_faktur=? and "+
                    "nip=? and besar_bayar=? and keterangan=? and nama_bayar=? and no_bukti=?",7,new String[]{
                tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),4).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),11).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),9).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString(),       
                tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()
            })==true){
                if(Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString())==Double.parseDouble(BesarBayar.getText())){
                    Sequel.mengedit("dapurpemesanan","no_faktur=?","status='Belum Dibayar'",1,new String[]{NoFaktur.getText()});
                }else{
                    Sequel.mengedit("dapurpemesanan","no_faktur=?","status='Belum Lunas'",1,new String[]{NoFaktur.getText()});
                }
                
                koderekening="";
                try {
                    myObj = new FileReader("./cache/akunbayarhutang.iyem");
                    root = mapper.readTree(myObj);
                    response = root.path("akunbayarhutang");
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
                
                if(koderekening.equals("")){
                    sukses=false; 
                }else{
                    total=0;
                    if(koderekening.equals(Host_to_Host_Bank_Mandiri)){
                        total=Sequel.cariIsiAngka("select metode_pembayaran_bankmandiri.biaya_transaksi from metode_pembayaran_bankmandiri inner join pembayaran_pihak_ke3_bankmandiri on pembayaran_pihak_ke3_bankmandiri.kode_metode=metode_pembayaran_bankmandiri.kode_metode where pembayaran_pihak_ke3_bankmandiri.nomor_pembayaran=?",tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString());
                        Sequel.meghapus("pembayaran_pihak_ke3_bankmandiri","nomor_pembayaran",tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString());
                    }
                    Sequel.queryu("delete from tampjurnal");
                    if(total>0){
                        Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                            Akun_Biaya_Mandiri,"BIAYA TRANSAKSI","0",total+""
                        });
                    }
                    Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                        koderekening,AkunBayar.getSelectedItem().toString(),(Valid.SetAngka(BesarBayar.getText())+total)+"","0"
                    });    
                    Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                        Bayar_Pemesanan_Dapur,"HUTANG USAHA","0",BesarBayar.getText()
                    }); 
                    sukses=jur.simpanJurnal(NoBukti.getText(),"U","BATAL BAYAR PELUNASAN BARANG DAPUR NO.FAKTUR "+NoFaktur.getText()+", OLEH "+akses.getkode()); 
                }
            }else{
                sukses=false;
            }
                           
            if(sukses==true){
                Sequel.Commit();
                tabMode.removeRow(tbKamar.getSelectedRow());
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();
        }catch (Exception ex) {
            System.out.println(ex);
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
            Valid.MyReportqry("rptBayarPemesananNonMedis.jasper","report","::[ Bayar Pemesanan ]::",
                    "select bayar_pemesanan_dapur.tgl_bayar,dapurpemesanan.tgl_faktur,dapurpemesanan.tgl_pesan,"+
                    "dapurpemesanan.tgl_tempo, bayar_pemesanan_dapur.no_faktur,"+
                    "dapursuplier.nama_suplier,bayar_pemesanan_dapur.nama_bayar,bayar_pemesanan_dapur.no_bukti,"+
                    "bayar_pemesanan_dapur.besar_bayar,bayar_pemesanan_dapur.keterangan,"+
                    "bayar_pemesanan_dapur.nip,petugas.nama from bayar_pemesanan_dapur inner join petugas "+
                    "inner join dapurpemesanan inner join dapursuplier on bayar_pemesanan_dapur.nip=petugas.nip "+
                    "and bayar_pemesanan_dapur.no_faktur=dapurpemesanan.no_faktur "+
                    "and dapurpemesanan.kode_suplier=dapursuplier.kode_suplier where "+
                    "bayar_pemesanan_dapur.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and dapursuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and bayar_pemesanan_dapur.no_faktur like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_dapur.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and dapursuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and dapursuplier.nama_suplier like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_dapur.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and dapursuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and bayar_pemesanan_dapur.nama_bayar like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_dapur.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and dapursuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and bayar_pemesanan_dapur.no_bukti like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_dapur.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and dapursuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and bayar_pemesanan_dapur.keterangan like '%"+TCari.getText().trim()+"%' or "+
                    "bayar_pemesanan_dapur.tgl_bayar between '"+Valid.SetTgl(TglCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglCari2.getSelectedItem()+"")+"' and dapursuplier.nama_suplier like '%"+nmsup.getText().trim()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' "+
                    " order by bayar_pemesanan_dapur.tgl_bayar",param);
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

private void NoBuktiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBuktiKeyPressed
   Valid.pindah(evt,keterangan,BesarBayar);
}//GEN-LAST:event_NoBuktiKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void TglBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBayarKeyPressed
        Valid.pindah(evt,NoFaktur,AkunBayar);
    }//GEN-LAST:event_TglBayarKeyPressed

    private void BesarBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BesarBayarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){            
            if(!BesarBayar.getText().equals("")){   
                try {
                    sisa_hutang.setText(Valid.SetAngka(sisahutang-Double.parseDouble(BesarBayar.getText())));                           
                } catch (Exception e) {
                    sisa_hutang.setText("0");
                }
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(!BesarBayar.getText().equals("")){   
                try {
                    sisa_hutang.setText(Valid.SetAngka(sisahutang-Double.parseDouble(BesarBayar.getText())));                           
                } catch (Exception e) {
                    sisa_hutang.setText("0");
                }
            }
            NoBukti.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(!BesarBayar.getText().equals("")){   
                try {
                    sisa_hutang.setText(Valid.SetAngka(sisahutang-Double.parseDouble(BesarBayar.getText())));                           
                } catch (Exception e) {
                    sisa_hutang.setText("0");
                }
            }
            BtnSimpan.requestFocus();
        }
    }//GEN-LAST:event_BesarBayarKeyPressed

    private void nipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            nama_petugas.setText(petugas.tampil3(nip.getText())); 
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            AkunBayar.requestFocus();
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

    private void AkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkunBayarKeyPressed
        Valid.pindah(evt,TglBayar,nip);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void NoBuktiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoBuktiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoBuktiActionPerformed

    private void keteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keteranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keteranganActionPerformed

    private void keteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keteranganKeyPressed
        Valid.pindah(evt,nip,NoBukti);
    }//GEN-LAST:event_keteranganKeyPressed

    private void TglCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglCari1KeyPressed

    private void TglCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglCari2KeyPressed

    private void kdsupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdsupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/akunbayarhutang.iyem")<30){
                tampilAkunBayar2();
            }else{
                tampilAkunBayar();
            }
        } catch (Exception e) {
        }
        
        try {
            if(Valid.daysOld("./cache/akunbankmandiri.iyem")<30){
                tampilAkunBankMandiri2();
            }else{
                tampilAkunBankMandiri();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        tampilAkunBayar();
        tampilAkunBankMandiri();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void BtnKeluarMandiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarMandiriActionPerformed
        NoRekening.setText("");
        RekeningAtasNama.setText("");
        KotaAtasNamaRekening.setText("");
        KodeMetode.setText("");
        MetodePembayaran.setText("");
        BiayaTransaksi.setText("0");
        KodeBank.setText("");
        BankTujuan.setText("");
        KodeTransaksi.setText("");
        DlgBayarMandiri.dispose();
    }//GEN-LAST:event_BtnKeluarMandiriActionPerformed

    private void BtnSimpanMandiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanMandiriActionPerformed
        if(NoRekening.getText().trim().equals("")){
            Valid.textKosong(NoRekening,"No.Rekening Tujuan");
        }else if(RekeningAtasNama.getText().trim().equals("")){
            Valid.textKosong(RekeningAtasNama,"Rekening Atas Nama");
        }else if(KotaAtasNamaRekening.getText().trim().equals("")){
            Valid.textKosong(KotaAtasNamaRekening,"Kota Atas Nama Rekening");
        }else if(KodeMetode.getText().trim().equals("")){
            Valid.textKosong(KodeMetode,"Kode Metode Pembayaran");
        }else if(MetodePembayaran.getText().trim().equals("")){
            Valid.textKosong(MetodePembayaran,"Metode Pembayaran");
        }else if(BiayaTransaksi.getText().trim().equals("")){
            Valid.textKosong(BiayaTransaksi,"Biaya Traksasi");
        }else if(KodeBank.getText().trim().equals("")){
            Valid.textKosong(KodeBank,"Kode Bank Tujuan");
        }else if(BankTujuan.getText().trim().equals("")){
            Valid.textKosong(BankTujuan,"Bank Tujuan");
        }else{
            try {
                Sequel.AutoComitFalse();
                sukses=true;

                Sequel.queryu("delete from tampjurnal");
                if(Valid.SetInteger(BiayaTransaksi.getText())>0){
                    Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                        Akun_Biaya_Mandiri,"BIAYA TRANSAKSI",BiayaTransaksi.getText(),"0"
                    });
                }
                Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                    Bayar_Pemesanan_Dapur,"HUTANG USAHA",BesarBayar.getText(),"0"
                });                     
                Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                    koderekening,AkunBayar.getSelectedItem().toString(),"0",(Valid.SetAngka(BiayaTransaksi.getText())+Valid.SetAngka(BesarBayar.getText()))+""
                });    
                sukses=jur.simpanJurnal(NoBukti.getText(),"U","BAYAR PELUNASAN BARANG DAPUR NO.FAKTUR "+NoFaktur.getText()+", OLEH "+akses.getkode());

                if(sukses==true){
                    if((sisahutang<=Double.parseDouble(BesarBayar.getText()))||(sisahutang<=-Double.parseDouble(BesarBayar.getText()))){
                        Sequel.mengedit("dapurpemesanan","no_faktur=?","status='Sudah Dibayar'",1,new String[]{NoFaktur.getText()});
                    }else{
                        Sequel.mengedit("dapurpemesanan","no_faktur=?","status='Belum Lunas'",1,new String[]{NoFaktur.getText()});
                    }
                    if(Sequel.menyimpantf2("bayar_pemesanan_dapur","?,?,?,?,?,?,?","data", 7,new String[]{
                            Valid.SetTgl(TglBayar.getSelectedItem()+""),NoFaktur.getText(),nip.getText(),
                            BesarBayar.getText(),keterangan.getText(),AkunBayar.getSelectedItem().toString(),
                            NoBukti.getText()
                        })==true){
                        if(Sequel.menyimpantf("pembayaran_pihak_ke3_bankmandiri","?,now(),?,?,?,?,?,?,?,?,?,?,?","No.Bukti", 12,new String[]{
                                NoBukti.getText(),norekening,NoRekening.getText(),RekeningAtasNama.getText(),KotaAtasNamaRekening.getText(),BesarBayar.getText(),NoFaktur.getText(),KodeMetode.getText(),KodeBank.getText(),KodeTransaksi.getText(),"Bayar Pesan Dapur","Baru"
                            })==false){
                            sukses=false;
                        }
                    }else{
                        sukses=false;
                    }
                }

                if(sukses==true){
                    Sequel.Commit();
                    BtnCariActionPerformed(evt);
                    emptTeks();
                    DlgBayarMandiri.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
            }catch (Exception ex) {
                System.out.println("Notif Simpan Mandiri : "+ex);
            }
        }
    }//GEN-LAST:event_BtnSimpanMandiriActionPerformed

    private void BtnSimpanMandiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanMandiriKeyPressed

    }//GEN-LAST:event_BtnSimpanMandiriKeyPressed

    private void BtnPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugas1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kodetransaksibank.setCari(BankTujuan.getText());
        kodetransaksibank.isCek();
        kodetransaksibank.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kodetransaksibank.setLocationRelativeTo(internalFrame1);
        kodetransaksibank.setAlwaysOnTop(false);
        kodetransaksibank.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPetugas1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganBayarPemesananDapur dialog = new KeuanganBayarPemesananDapur(new javax.swing.JFrame(), true);
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
    private widget.TextBox BankTujuan;
    private widget.TextBox BesarBayar;
    private widget.TextBox BiayaTransaksi;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarMandiri;
    private widget.Button BtnPetugas;
    private widget.Button BtnPetugas1;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanMandiri;
    private javax.swing.JDialog DlgBayarMandiri;
    private widget.TextBox Kd2;
    private widget.TextBox KodeBank;
    private widget.TextBox KodeMetode;
    private widget.TextBox KodeTransaksi;
    private widget.TextBox KotaAtasNamaRekening;
    private widget.Label LCount;
    private widget.Label LTotal;
    private widget.TextBox MetodePembayaran;
    private widget.TextBox NoBukti;
    private widget.TextBox NoFaktur;
    private widget.TextBox NoRekening;
    private widget.TextBox RekeningAtasNama;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal TglBayar;
    private widget.Tanggal TglCari1;
    private widget.Tanggal TglCari2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel99;
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
    private widget.Label label39;
    private widget.Label label9;
    private widget.TextBox nama_petugas;
    private widget.TextBox nip;
    private widget.TextBox nmsup;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private widget.TextBox sisa_hutang;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{           
            ps=koneksi.prepareStatement(
                    "select bayar_pemesanan_dapur.tgl_bayar,dapurpemesanan.tgl_faktur,dapurpemesanan.tgl_pesan,"+
                    "dapurpemesanan.tgl_tempo, bayar_pemesanan_dapur.no_faktur,"+
                    "dapursuplier.nama_suplier,bayar_pemesanan_dapur.nama_bayar,bayar_pemesanan_dapur.no_bukti,"+
                    "bayar_pemesanan_dapur.besar_bayar,bayar_pemesanan_dapur.keterangan,"+
                    "bayar_pemesanan_dapur.nip,petugas.nama from bayar_pemesanan_dapur inner join petugas "+
                    "inner join dapurpemesanan inner join dapursuplier on bayar_pemesanan_dapur.nip=petugas.nip "+
                    "and bayar_pemesanan_dapur.no_faktur=dapurpemesanan.no_faktur "+
                    "and dapurpemesanan.kode_suplier=dapursuplier.kode_suplier where "+
                    "bayar_pemesanan_dapur.tgl_bayar between ? and ? and dapursuplier.nama_suplier like ? and bayar_pemesanan_dapur.no_faktur like ? or "+
                    "bayar_pemesanan_dapur.tgl_bayar between ? and ? and dapursuplier.nama_suplier like ? and dapursuplier.nama_suplier like ? or "+
                    "bayar_pemesanan_dapur.tgl_bayar between ? and ? and dapursuplier.nama_suplier like ? and bayar_pemesanan_dapur.nama_bayar like ? or "+
                    "bayar_pemesanan_dapur.tgl_bayar between ? and ? and dapursuplier.nama_suplier like ? and bayar_pemesanan_dapur.no_bukti like ? or "+
                    "bayar_pemesanan_dapur.tgl_bayar between ? and ? and dapursuplier.nama_suplier like ? and bayar_pemesanan_dapur.keterangan like ? or "+
                    "bayar_pemesanan_dapur.tgl_bayar between ? and ? and dapursuplier.nama_suplier like ? and petugas.nama like ? "+
                    " order by bayar_pemesanan_dapur.tgl_bayar");
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
        BesarBayar.setText("0");
        setData(NoFaktur.getText());
        NoBukti.setText("");
        keterangan.setText("");
        TglBayar.setDate(new Date());
        TglBayar.requestFocus();
    }
    
    public void setData(String nofaktur){
        NoFaktur.setText(nofaktur);
        TCari.setText(nofaktur);
        sisahutang=Math.round(Sequel.cariIsiAngka("SELECT dapurpemesanan.tagihan FROM dapurpemesanan where dapurpemesanan.no_faktur=?",nofaktur)
                   -Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_pemesanan_dapur.besar_bayar),0) FROM bayar_pemesanan_dapur where bayar_pemesanan_dapur.no_faktur=?",nofaktur));
        sisa_hutang.setText(Valid.SetAngka(sisahutang));
        BesarBayar.setText("0");
    }

    private void getData() {
        int row=tbKamar.getSelectedRow();
        if(row!= -1){
            Valid.SetTgl(TglBayar,tbKamar.getValueAt(row,0).toString());
            NoFaktur.setText(tbKamar.getValueAt(row,4).toString());
            //setData(no_faktur.getText());
            AkunBayar.setSelectedItem(tbKamar.getValueAt(row,6).toString());
            NoBukti.setText(tbKamar.getValueAt(row,7).toString());
            BesarBayar.setText(Valid.SetAngka5(Double.parseDouble(tbKamar.getValueAt(row,8).toString())));
            keterangan.setText(tbKamar.getValueAt(row,9).toString());
        }
    }

    public JTextField getTextField(){
        return NoFaktur;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbayar_pesan_dapur());
        BtnHapus.setEnabled(akses.getbayar_pesan_dapur());
        BtnPrint.setEnabled(akses.getbayar_pesan_dapur());
        if(akses.getjml2()>=1){
            nip.setEditable(false);
            BtnPetugas.setEnabled(false);
            nip.setText(akses.getkode());
            BtnSimpan.setEnabled(akses.getbayar_pesan_dapur());
            nama_petugas.setText(petugas.tampil3(nip.getText()));
        }else if(akses.getjml1()>=1){
            nip.setEditable(true);
            BtnPetugas.setEnabled(true);
            BtnSimpan.setEnabled(true);
        }   
    }
    
    private void tampilAkunBayar() {         
         try{      
             file=new File("./cache/akunbayarhutang.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             iyem="";
             ps=koneksi.prepareStatement("select * from akun_bayar_hutang order by akun_bayar_hutang.nama_bayar");
             try{
                 rs=ps.executeQuery();
                 AkunBayar.removeAllItems();
                 while(rs.next()){    
                     AkunBayar.addItem(rs.getString(1).replaceAll("\"",""));
                     iyem=iyem+"{\"NamaAkun\":\""+rs.getString(1).replaceAll("\"","")+"\",\"KodeRek\":\""+rs.getString(2)+"\"},";
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

             fileWriter.write("{\"akunbayarhutang\":["+iyem.substring(0,iyem.length()-1)+"]}");
             fileWriter.flush();
             fileWriter.close();
             iyem=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilAkunBayar2() {
        try {
            myObj = new FileReader("./cache/akunbayarhutang.iyem");
            root = mapper.readTree(myObj);
            response = root.path("akunbayarhutang");
            if(response.isArray()){
                for(JsonNode list:response){
                    AkunBayar.addItem(list.path("NamaAkun").asText().replaceAll("\"",""));
                }
            }
            myObj.close();
        } catch (Exception ex) {
            if(ex.toString().contains("java.io.FileNotFoundException")){
                tampilAkunBayar();
            }else{
                System.out.println("Notifikasi : "+ex);
            }
        }
    } 
    
    private void tampilAkunBankMandiri() { 
        try{     
            ps=koneksi.prepareStatement(
                    "select set_akun_mandiri.kd_rek,set_akun_mandiri.kd_rek_biaya,set_akun_mandiri.kode_mcm,set_akun_mandiri.no_rekening from set_akun_mandiri");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    file=new File("./cache/akunbankmandiri.iyem");
                    file.createNewFile();
                    fileWriter = new FileWriter(file);
                    Host_to_Host_Bank_Mandiri=rs.getString("kd_rek");
                    Akun_Biaya_Mandiri=rs.getString("kd_rek_biaya");
                    kodemcm=rs.getString("kode_mcm");
                    norekening=rs.getString("no_rekening");
                    fileWriter.write("{\"akunbankmandiri\":\""+Host_to_Host_Bank_Mandiri+"\",\"kodemcm\":\""+kodemcm+"\",\"akunbiayabankmandiri\":\""+Akun_Biaya_Mandiri+"\",\"norekening\":\""+norekening+"\"}");
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (Exception e) {
                Host_to_Host_Bank_Mandiri="";
                Akun_Biaya_Mandiri="";
                kodemcm="";
                norekening="";
                System.out.println("Notif Set Nota : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
             Host_to_Host_Bank_Mandiri="";
             Akun_Biaya_Mandiri="";
             kodemcm="";
             norekening="";
        }
    }
    
    private void tampilAkunBankMandiri2() { 
        try{      
             myObj = new FileReader("./cache/akunbankmandiri.iyem");
             root = mapper.readTree(myObj);
             response = root.path("akunbankmandiri");
             Host_to_Host_Bank_Mandiri=response.asText();
             response = root.path("akunbiayabankmandiri");
             Akun_Biaya_Mandiri=response.asText();
             response = root.path("kodemcm");
             kodemcm=response.asText();
             response = root.path("norekening");
             norekening=response.asText();
             myObj.close();
        } catch (Exception e) {
             Host_to_Host_Bank_Mandiri="";
             Akun_Biaya_Mandiri="";
             kodemcm="";
             norekening="";
        }
    }
}

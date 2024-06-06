

package keuangan;

import bridging.MandiriCariKodeTransaksiTujuanTransfer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable3;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import ipsrs.IPSRSCariSuplier;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class KeuanganHutangNonMedisBelumLunas extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private IPSRSCariSuplier suplier=new IPSRSCariSuplier(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private MandiriCariKodeTransaksiTujuanTransfer kodetransaksibank=new MandiriCariKodeTransaksiTujuanTransfer(null, false);
    private int row=0,i;
    private String koderekening="",tanggaldatang="",tanggaltempo="",Bayar_Pemesanan_Non_Medis=Sequel.cariIsi("select set_akun.Bayar_Pemesanan_Non_Medis from set_akun"),Host_to_Host_Bank_Mandiri="",Akun_Biaya_Mandiri="",kodemcm="",norekening="";
    private double sisahutang=0,cicilan=0,bayar=0;
    private Jurnal jur=new Jurnal();
    private WarnaTable3 warna=new WarnaTable3();
    private boolean sukses=false;
    private File file;
    private FileWriter fileWriter;
    private String iyem,notagihan="";
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganHutangNonMedisBelumLunas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        DlgBayarMandiri.setSize(562,163);

        Object[] rowRwJlDr={
            "P","No.Faktur","No.Order","Supplier","Petugas Penerima",
            "Tgl.Faktur","Tgl.Datang","Tgl.Tempo","Tagihan",
            "Sisa Hutang","Pembayaran","Sisa","Bank Suplier","No.Rekening"
        };
        tabMode=new DefaultTableModel(null,rowRwJlDr){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==10)||(colIndex==0)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 14; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(22);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(95);
            }else if(i==3){
                column.setPreferredWidth(140);
            }else if(i==4){
                column.setPreferredWidth(140);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setPreferredWidth(110);
            }else if(i==13){
                column.setPreferredWidth(110);
            }else{
                column.setPreferredWidth(80);
            }
        }
        warna.kolom=11;
        tbBangsal.setDefaultRenderer(Object.class,warna);

        NoBukti.setDocument(new batasInput((byte)20).getKata(NoBukti));
        Nip.setDocument(new batasInput((byte)20).getKata(Nip));
        keterangan.setDocument(new batasInput((byte)100).getKata(keterangan));        
        
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
        
        suplier.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(suplier.getTable().getSelectedRow()!= -1){
                    KodeSuplier.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),0).toString());
                    NamaSuplier.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
                    RekeningAtasNama.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),1).toString());
                    KotaAtasNamaRekening.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),3).toString());
                    NoRekening.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),6).toString());
                    BankTujuan.setText(suplier.getTable().getValueAt(suplier.getTable().getSelectedRow(),5).toString());
                    tampil();
                }      
                KodeSuplier.requestFocus();
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    Nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }            
                Nip.requestFocus();                
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
        
        ChkAccor.setSelected(false);
        isPhoto();
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);

    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
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
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label19 = new widget.Label();
        KodeSuplier = new widget.TextBox();
        NamaSuplier = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LCount1 = new javax.swing.JLabel();
        BtnBayar = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi5 = new widget.panelisi();
        ChkTanggalDatang = new widget.CekBox();
        TglDatang1 = new widget.Tanggal();
        label18 = new widget.Label();
        TglDatang2 = new widget.Tanggal();
        label21 = new widget.Label();
        ChkTanggalTempo = new widget.CekBox();
        TglTempo1 = new widget.Tanggal();
        label20 = new widget.Label();
        TglTempo2 = new widget.Tanggal();
        panelisi4 = new widget.panelisi();
        label32 = new widget.Label();
        label36 = new widget.Label();
        NoBukti = new widget.TextBox();
        label16 = new widget.Label();
        Nip = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        TglBayar = new widget.Tanggal();
        BtnPetugas = new widget.Button();
        jLabel12 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        keterangan = new widget.TextBox();
        label39 = new widget.Label();
        BtnAll1 = new widget.Button();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Hutang Barang Non Medis dan Penunjang ( Lab & RO ) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setToolTipText("Silahkan centang di depan untuk memilih data hutang yang mau dibayar");
        tbBangsal.setComponentPopupMenu(Popup);
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbBangsalPropertyChange(evt);
            }
        });
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBangsalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setMinimumSize(new java.awt.Dimension(816, 145));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(810, 145));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(95, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label19.setText("Supplier :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label19);

        KodeSuplier.setEditable(false);
        KodeSuplier.setName("KodeSuplier"); // NOI18N
        KodeSuplier.setPreferredSize(new java.awt.Dimension(70, 23));
        KodeSuplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeSuplierKeyPressed(evt);
            }
        });
        panelisi3.add(KodeSuplier);

        NamaSuplier.setEditable(false);
        NamaSuplier.setName("NamaSuplier"); // NOI18N
        NamaSuplier.setPreferredSize(new java.awt.Dimension(180, 23));
        panelisi3.add(NamaSuplier);

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
        panelisi3.add(BtnSeek2);

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi3.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
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
        panelisi3.add(BtnAll);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(760, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total Hutang :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(76, 23));
        panelisi1.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi1.add(LCount);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(50, 50, 50));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Bayar :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(56, 23));
        panelisi1.add(jLabel11);

        LCount1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount1.setForeground(new java.awt.Color(50, 50, 50));
        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(135, 23));
        panelisi1.add(LCount1);

        BtnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnBayar.setMnemonic('B');
        BtnBayar.setText("Bayar");
        BtnBayar.setToolTipText("Alt+B");
        BtnBayar.setName("BtnBayar"); // NOI18N
        BtnBayar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBayarActionPerformed(evt);
            }
        });
        BtnBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBayarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBayar);

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

        ChkTanggalDatang.setText("Tgl.Datang :");
        ChkTanggalDatang.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkTanggalDatang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkTanggalDatang.setName("ChkTanggalDatang"); // NOI18N
        ChkTanggalDatang.setOpaque(false);
        ChkTanggalDatang.setPreferredSize(new java.awt.Dimension(93, 23));
        ChkTanggalDatang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkTanggalDatangItemStateChanged(evt);
            }
        });
        panelisi5.add(ChkTanggalDatang);

        TglDatang1.setDisplayFormat("dd-MM-yyyy");
        TglDatang1.setName("TglDatang1"); // NOI18N
        TglDatang1.setPreferredSize(new java.awt.Dimension(97, 23));
        TglDatang1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglDatang1KeyPressed(evt);
            }
        });
        panelisi5.add(TglDatang1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi5.add(label18);

        TglDatang2.setDisplayFormat("dd-MM-yyyy");
        TglDatang2.setName("TglDatang2"); // NOI18N
        TglDatang2.setPreferredSize(new java.awt.Dimension(97, 23));
        TglDatang2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglDatang2KeyPressed(evt);
            }
        });
        panelisi5.add(TglDatang2);

        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi5.add(label21);

        ChkTanggalTempo.setText("Tgl.Tempo");
        ChkTanggalTempo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkTanggalTempo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkTanggalTempo.setName("ChkTanggalTempo"); // NOI18N
        ChkTanggalTempo.setOpaque(false);
        ChkTanggalTempo.setPreferredSize(new java.awt.Dimension(80, 23));
        ChkTanggalTempo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkTanggalTempoItemStateChanged(evt);
            }
        });
        panelisi5.add(ChkTanggalTempo);

        TglTempo1.setDisplayFormat("dd-MM-yyyy");
        TglTempo1.setName("TglTempo1"); // NOI18N
        TglTempo1.setPreferredSize(new java.awt.Dimension(97, 23));
        TglTempo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglTempo1KeyPressed(evt);
            }
        });
        panelisi5.add(TglTempo1);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label20.setText("s.d.");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi5.add(label20);

        TglTempo2.setDisplayFormat("dd-MM-yyyy");
        TglTempo2.setName("TglTempo2"); // NOI18N
        TglTempo2.setPreferredSize(new java.awt.Dimension(97, 23));
        TglTempo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglTempo2KeyPressed(evt);
            }
        });
        panelisi5.add(TglTempo2);

        jPanel1.add(panelisi5, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 74));
        panelisi4.setLayout(null);

        label32.setText("Tgl.Bayar :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(167, 10, 70, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(345, 40, 80, 23);

        NoBukti.setHighlighter(null);
        NoBukti.setName("NoBukti"); // NOI18N
        NoBukti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBuktiKeyPressed(evt);
            }
        });
        panelisi4.add(NoBukti);
        NoBukti.setBounds(78, 10, 90, 23);

        label16.setText("Petugas :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label16);
        label16.setBounds(345, 10, 80, 23);

        Nip.setName("Nip"); // NOI18N
        Nip.setPreferredSize(new java.awt.Dimension(80, 23));
        Nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NipKeyPressed(evt);
            }
        });
        panelisi4.add(Nip);
        Nip.setBounds(428, 10, 100, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        NamaPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NamaPetugas);
        NamaPetugas.setBounds(530, 10, 190, 23);

        TglBayar.setDisplayFormat("dd-MM-yyyy");
        TglBayar.setName("TglBayar"); // NOI18N
        TglBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBayarKeyPressed(evt);
            }
        });
        panelisi4.add(TglBayar);
        TglBayar.setBounds(240, 10, 90, 23);

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
        BtnPetugas.setBounds(722, 10, 28, 23);

        jLabel12.setText("Akun Bayar :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelisi4.add(jLabel12);
        jLabel12.setBounds(0, 40, 75, 23);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        panelisi4.add(AkunBayar);
        AkunBayar.setBounds(78, 40, 222, 23);

        keterangan.setHighlighter(null);
        keterangan.setName("keterangan"); // NOI18N
        keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keteranganKeyPressed(evt);
            }
        });
        panelisi4.add(keterangan);
        keterangan.setBounds(428, 40, 322, 23);

        label39.setText("No.Bukti :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(0, 10, 75, 23);

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
        BtnAll1.setBounds(302, 40, 28, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Photo Faktur : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                    Sequel.menyimpan("temporary","'"+i+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString()))+"','"+
                                tabMode.getValueAt(i,12).toString()+"','"+
                                tabMode.getValueAt(i,13).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Piutang Pasien"); 
            }
            i++;
            Sequel.menyimpan("temporary","'"+i+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
            i++;
            Sequel.menyimpan("temporary","'"+i+"','','','TOTAL HUTANG :','','','','','"+LCount.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
            
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptHutangNonMedisBelumLunas.jasper","report","::[ Data Hutang Barang Non Medis ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBayar, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        KodeSuplier.setText("");
        NamaSuplier.setText("");
        tampil();

}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(tbBangsal.getSelectedColumn()==0){
                    if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString().equals("0")){
                        tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString()), tbBangsal.getSelectedRow(),10);
                    }
                    if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                        tbBangsal.setValueAt(
                            (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString())-
                            Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString()))
                            ,tbBangsal.getSelectedRow(),9);
                    }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),10);
                        tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString()),tbBangsal.getSelectedRow(),9);
                    }
                }  
                panggilPhoto();
            }
        }
}//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                    tbBangsal.setValueAt(
                            (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString())-
                            Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString()))
                            ,tbBangsal.getSelectedRow(),9);
                }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                    tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString()),tbBangsal.getSelectedRow(),9);
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                if(tbBangsal.getSelectedColumn()==10){
                   tbBangsal.setValueAt(0, tbBangsal.getSelectedRow(),10); 
                }
            }
        }
}//GEN-LAST:event_tbBangsalKeyPressed

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
            Valid.pindah(evt, TKd, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        NoBukti.requestFocus();
        try {
            if(Valid.daysOld("./cache/akunbayarhutang.iyem")<8){
                tampilAkunBayar2();
            }else{
                tampilAkunBayar();
            }
            if(Valid.daysOld("./cache/akunbankmandiri.iyem")<30){
                tampilAkunBankMandiri2();
            }else{
                tampilAkunBankMandiri();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void KodeSuplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeSuplierKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_KodeSuplierKeyPressed

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

    private void BtnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBayarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoBukti.getText().trim().equals("")){
            Valid.textKosong(NoBukti,"No.Bukti");
        }else if(Nip.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(Nip,"Petugas");
        }else if(AkunBayar.getSelectedItem().toString().trim().equals("")){
            Valid.textKosong(AkunBayar,"Akun Bayar");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda bayar...!!!!");
        }else if(keterangan.getText().trim().equals("")){
            Valid.textKosong(keterangan,"Keterangan");
        }else if(tabMode.getRowCount()!=0){
            Sequel.AutoComitFalse();
            sukses=true;
            
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

            row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                if(tabMode.getValueAt(i,0).toString().equals("true")){
                    if(Double.parseDouble(tbBangsal.getValueAt(i,10).toString())>0){
                        if(Sequel.menyimpantf("bayar_pemesanan_non_medis","?,?,?,?,?,?,?","Data", 7,new String[]{
                            Valid.SetTgl(TglBayar.getSelectedItem()+""),tabMode.getValueAt(i,1).toString(),Nip.getText(),
                            tabMode.getValueAt(i,10).toString(),keterangan.getText(),AkunBayar.getSelectedItem().toString(),
                            NoBukti.getText()
                        })==true){
                            if((Double.parseDouble(tabMode.getValueAt(i,9).toString())<=0)||(Double.parseDouble(tabMode.getValueAt(i,9).toString())<=-0)){
                                Sequel.mengedit("ipsrspemesanan","no_faktur='"+tabMode.getValueAt(i,1).toString()+"'","status='Sudah Dibayar'");
                            }else{
                                Sequel.mengedit("ipsrspemesanan","no_faktur='"+tabMode.getValueAt(i,1).toString()+"'","status='Belum Lunas'");
                            } 
                            Sequel.queryu("delete from tampjurnal");
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                Bayar_Pemesanan_Non_Medis,"HUTANG BARANG NON MEDIS",tabMode.getValueAt(i,10).toString(),"0"
                            });                     
                            Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                koderekening,AkunBayar.getSelectedItem().toString(),"0",tabMode.getValueAt(i,10).toString()
                            });    
                            if(jur.simpanJurnal(NoBukti.getText(),"U","BAYAR PELUNASAN HUTANG BARANG NON MEDIS NO.FAKTUR "+tabMode.getValueAt(i,1).toString()+", OLEH "+akses.getkode())==false){
                                sukses=false;
                            }                           
                        }else{
                            sukses=false;
                        }
                    }                        
                }else{
                    if(!notagihan.equals("")){
                        Sequel.mengedit("ipsrspemesanan","no_faktur='"+tabMode.getValueAt(i,1).toString()+"'","status='Belum Lunas'");
                    }
                }
            }
            
            if(sukses==true){
                if(!notagihan.equals("")){
                    Sequel.queryu("update ipsrs_titip_faktur set status='Dibayar' where no_tagihan=?",notagihan);
                    notagihan="";
                }
                Sequel.Commit();
                bayar=0;
                LCount1.setText("0");
                for(i=0;i<tbBangsal.getRowCount();i++){  
                    if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                        tabMode.removeRow(i);
                        i--;
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnBayarActionPerformed

    private void BtnBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBayarKeyPressed

    private void NoBuktiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBuktiKeyPressed
       Valid.pindah(evt,TCari,TglBayar);
    }//GEN-LAST:event_NoBuktiKeyPressed

    private void NipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NamaPetugas.setText(petugas.tampil3(Nip.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            AkunBayar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_NipKeyPressed

    private void TglBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBayarKeyPressed
        Valid.pindah(evt,NoBukti,AkunBayar);
    }//GEN-LAST:event_TglBayarKeyPressed

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
        Valid.pindah(evt,TglBayar,Nip);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void keteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keteranganKeyPressed
        Valid.pindah(evt,Nip,BtnBayar);
    }//GEN-LAST:event_keteranganKeyPressed

    private void ChkTanggalDatangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkTanggalDatangItemStateChanged

    }//GEN-LAST:event_ChkTanggalDatangItemStateChanged

    private void TglDatang1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglDatang1KeyPressed
        Valid.pindah(evt, BtnKeluar, TglDatang2);
    }//GEN-LAST:event_TglDatang1KeyPressed

    private void TglDatang2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglDatang2KeyPressed
        Valid.pindah(evt, TglDatang1,TCari);
    }//GEN-LAST:event_TglDatang2KeyPressed

    private void ChkTanggalTempoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkTanggalTempoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkTanggalTempoItemStateChanged

    private void TglTempo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTempo1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTempo1KeyPressed

    private void TglTempo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTempo2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTempo2KeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(row=0;row<tbBangsal.getRowCount();row++){
            tbBangsal.setValueAt(false,row,0);
        }
        bayar=0;
        LCount1.setText("0");
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for(row=0;row<tbBangsal.getRowCount();row++){
            tbBangsal.setValueAt(true,row,0);
            if(tbBangsal.getValueAt(row,10).toString().equals("0")){
                tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(row,11).toString()), row,10);
            }
            if(tbBangsal.getValueAt(row,0).toString().equals("true")){
                tbBangsal.setValueAt(
                    (Double.parseDouble(tbBangsal.getValueAt(row,11).toString())-
                    Double.parseDouble(tbBangsal.getValueAt(row,10).toString()))
                    ,row,9);
            }else if(tbBangsal.getValueAt(row,0).toString().equals("false")){
                tbBangsal.setValueAt(0,row,10);
                tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(row,11).toString()),row,9);
            }
        }
        getData();
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void tbBangsalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbBangsalPropertyChange
        if(this.isVisible()==true){
            if(tbBangsal.getSelectedRow()!= -1){
                if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString().equals("0")){
                    tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString()), tbBangsal.getSelectedRow(),10);
                }
                if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                    tbBangsal.setValueAt(
                        (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString())-
                        Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString()))
                        ,tbBangsal.getSelectedRow(),9);
                }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                    tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),10);
                    tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString()),tbBangsal.getSelectedRow(),9);
                }
                getData();
            }
        }
    }//GEN-LAST:event_tbBangsalPropertyChange

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbBangsal.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Faktur Penerimaan...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

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
            /*try{
                Sequel.AutoComitFalse();
                sukses=true;
                row=tabMode.getRowCount();
                for(i=0;i<row;i++){
                    if(tabMode.getValueAt(i,0).toString().equals("true")){
                        if(Double.parseDouble(tbBangsal.getValueAt(i,11).toString())>0){
                            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pembayaran_pihak_ke3_bankmandiri.nomor_pembayaran,6),signed)),0) from pembayaran_pihak_ke3_bankmandiri where left(pembayaran_pihak_ke3_bankmandiri.tgl_pembayaran,10)='"+Valid.SetTgl(TglBayar.getSelectedItem()+"")+"' ",kodemcm+"14"+TglBayar.getSelectedItem().toString().replaceAll("-",""),6,NoBukti);
                            if(Sequel.menyimpantf("bayar_pemesanan","?,?,?,?,?,?,?","Data", 7,new String[]{
                                Valid.SetTgl(TglBayar.getSelectedItem()+""),tabMode.getValueAt(i,1).toString(),Nip.getText(),
                                tabMode.getValueAt(i,11).toString(),Keterangan.getText(),AkunBayar.getSelectedItem().toString(),
                                NoBukti.getText()
                            })==true){
                                if((Double.parseDouble(tabMode.getValueAt(i,10).toString())<=0)||(Double.parseDouble(tabMode.getValueAt(i,10).toString())<=-0)){
                                    Sequel.mengedit("pemesanan","no_faktur='"+tabMode.getValueAt(i,1).toString()+"'","status='Sudah Dibayar'");
                                }else{
                                    Sequel.mengedit("pemesanan","no_faktur='"+tabMode.getValueAt(i,1).toString()+"'","status='Belum Lunas'");
                                }
                                if(Sequel.menyimpantf("pembayaran_pihak_ke3_bankmandiri","?,now(),?,?,?,?,?,?,?,?,?,?,?","No.Bukti", 12,new String[]{
                                    NoBukti.getText(),norekening,NoRekening.getText(),RekeningAtasNama.getText(),KotaAtasNamaRekening.getText(),tabMode.getValueAt(i,11).toString(),tabMode.getValueAt(i,1).toString(),KodeMetode.getText(),KodeBank.getText(),KodeTransaksi.getText(),"Bayar Pesan Obat/BHP","Baru"
                                })==false){
                                    sukses=false;
                                }else{
                                    Sequel.queryu("delete from tampjurnal");
                                    if(Valid.SetInteger(BiayaTransaksi.getText())>0){
                                        Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                            Akun_Biaya_Mandiri,"BIAYA TRANSAKSI",BiayaTransaksi.getText(),"0"
                                        });
                                    }
                                    Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                        Bayar_Pemesanan_Obat,"HUTANG USAHA",tabMode.getValueAt(i,11).toString(),"0"
                                    });
                                    Sequel.menyimpan("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                        koderekening,AkunBayar.getSelectedItem().toString(),"0",(Valid.SetAngka(BiayaTransaksi.getText())+Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+""
                                    });
                                    if(jur.simpanJurnal(NoBukti.getText(),"U","BAYAR PELUNASAN HUTANG OBAT/BHP/ALKES NO.FAKTUR "+tabMode.getValueAt(i,1).toString()+", OLEH "+akses.getkode())==false){
                                        sukses=false;
                                    }
                                }
                            }else{
                                sukses=false;
                            }
                        }
                    }else{
                        if(!notagihan.equals("")){
                            Sequel.mengedit("pemesanan","no_faktur='"+tabMode.getValueAt(i,1).toString()+"'","status='Belum Lunas'");
                        }
                    }
                }

                if(sukses==true){
                    if(!notagihan.equals("")){
                        Sequel.queryu("update titip_faktur set status='Dibayar' where no_tagihan=?",notagihan);
                        notagihan="";
                    }
                    Sequel.Commit();
                    bayar=0;
                    LCount1.setText("0");
                    for(i=0;i<tbBangsal.getRowCount();i++){
                        if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                            tabMode.removeRow(i);
                            i--;
                        }
                    }
                    NoBukti.setText("");
                    Keterangan.setText("");
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
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
            }catch (Exception ex) {
                System.out.println("Notif Simpan Mandiri : "+ex);
            }*/
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
            KeuanganHutangNonMedisBelumLunas dialog = new KeuanganHutangNonMedisBelumLunas(new javax.swing.JFrame(), true);
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
    private widget.TextBox BiayaTransaksi;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnBayar;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarMandiri;
    private widget.Button BtnPetugas;
    private widget.Button BtnPetugas1;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpanMandiri;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkTanggalDatang;
    private widget.CekBox ChkTanggalTempo;
    private javax.swing.JDialog DlgBayarMandiri;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox KodeBank;
    private widget.TextBox KodeMetode;
    private widget.TextBox KodeSuplier;
    private widget.TextBox KodeTransaksi;
    private widget.TextBox KotaAtasNamaRekening;
    private javax.swing.JLabel LCount;
    private javax.swing.JLabel LCount1;
    private widget.editorpane LoadHTML;
    private widget.TextBox MetodePembayaran;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NamaSuplier;
    private widget.TextBox Nip;
    private widget.TextBox NoBukti;
    private widget.TextBox NoRekening;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox RekeningAtasNama;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal TglBayar;
    private widget.Tanggal TglDatang1;
    private widget.Tanggal TglDatang2;
    private widget.Tanggal TglTempo1;
    private widget.Tanggal TglTempo2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private javax.swing.JLabel jLabel10;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private javax.swing.JLabel jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox keterangan;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label32;
    private widget.Label label36;
    private widget.Label label39;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSemua;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            tanggaldatang="";
            tanggaltempo="";
            if(ChkTanggalDatang.isSelected()==true){
                tanggaldatang=" ipsrspemesanan.tgl_pesan between '"+Valid.SetTgl(TglDatang1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglDatang2.getSelectedItem()+"")+"' and ";
            }
            if(ChkTanggalTempo.isSelected()==true){
                tanggaltempo=" ipsrspemesanan.tgl_tempo between '"+Valid.SetTgl(TglTempo1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglTempo2.getSelectedItem()+"")+"' and ";
            }
            ps=koneksi.prepareStatement(
                    "select ipsrspemesanan.no_faktur,ipsrspemesanan.no_order,ipsrssuplier.nama_suplier, "+
                    "petugas.nama,ipsrspemesanan.tgl_tempo,ipsrspemesanan.tgl_pesan,ipsrspemesanan.tgl_faktur,ipsrspemesanan.tagihan,"+
                    "(SELECT ifnull(SUM(besar_bayar),0) FROM bayar_pemesanan_non_medis where bayar_pemesanan_non_medis.no_faktur=ipsrspemesanan.no_faktur) as bayar, "+
                    "ipsrssuplier.nama_bank,ipsrssuplier.rekening from ipsrspemesanan inner join ipsrssuplier inner join petugas "+
                    "on ipsrspemesanan.kode_suplier=ipsrssuplier.kode_suplier "+
                    "and ipsrspemesanan.nip=petugas.nip where "+
                    tanggaldatang+tanggaltempo+"(ipsrspemesanan.status='Belum Dibayar' or ipsrspemesanan.status='Belum Lunas') and ipsrssuplier.nama_suplier like ? "+
                    (TCari.getText().trim().equals("")?"":"and (ipsrspemesanan.no_faktur like ? or ipsrspemesanan.no_order like ? or ipsrspemesanan.tgl_tempo like ? or "+
                    "ipsrssuplier.nama_suplier like ? or petugas.nama like ?)")+" order by ipsrspemesanan.tgl_tempo ");
            try {
                ps.setString(1,"%"+NamaSuplier.getText().trim()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                sisahutang=0;
                cicilan=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_faktur"),rs.getString("no_order"),
                        rs.getString("nama_suplier"),rs.getString("nama"),rs.getString("tgl_faktur"),
                        rs.getString("tgl_pesan"),rs.getString("tgl_tempo"),
                        rs.getDouble("tagihan"),(rs.getDouble("tagihan")-rs.getDouble("bayar")),
                        0,(rs.getDouble("tagihan")-rs.getDouble("bayar")),rs.getString("nama_bank"),
                        rs.getString("rekening")
                    });
                    sisahutang=sisahutang+rs.getDouble("tagihan");
                    cicilan=cicilan+rs.getDouble("bayar");
                }
                LCount.setText(Valid.SetAngka(sisahutang-cicilan));
            } catch (Exception e) {
                System.out.println("Notifikasi Data Hutang: "+e);
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
    }
    
    private void getData(){
        row=tbBangsal.getRowCount();
        bayar=0;
        for(i=0;i<row;i++){  
            if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                 bayar=bayar+Double.parseDouble(tbBangsal.getValueAt(i,10).toString());     
            }
        }
        LCount1.setText(Valid.SetAngka(bayar));
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(internalFrame1.getWidth()-300,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }
    
    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select photo from bukti_pemesanan_logistik where no_faktur=?");
                try {
                    ps.setString(1,tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penerimaanlogistik/"+rs.getString("photo")+"' alt='photo' width='"+(internalFrame1.getWidth()-340)+"' height='"+(internalFrame1.getHeight()-275)+"'/></center></body></html>");
                        }  
                    }else{
                        LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
            System.out.println("Notifikasi : "+ex);
        }
    } 
    
    public void isCek(){
        BtnBayar.setEnabled(akses.getbayar_pesan_non_medis());
        if(akses.getjml2()>=1){
            Nip.setEditable(false);
            BtnPetugas.setEnabled(false);
            Nip.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(Nip.getText()));
        }  
    }
    
    public void tampilTagihan(String notagihan){
        this.notagihan=notagihan;
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select ipsrspemesanan.no_faktur,ipsrspemesanan.no_order,ipsrssuplier.nama_suplier,ipsrspemesanan.kode_suplier, "+
                    "petugas.nama,ipsrspemesanan.tgl_tempo,ipsrspemesanan.tgl_pesan,ipsrspemesanan.tgl_faktur,ipsrspemesanan.tagihan,"+
                    "(SELECT ifnull(SUM(besar_bayar),0) FROM bayar_pemesanan_non_medis where bayar_pemesanan_non_medis.no_faktur=ipsrspemesanan.no_faktur) as bayar, "+
                    "ipsrssuplier.nama_bank,ipsrssuplier.rekening from ipsrspemesanan "+
                    "inner join ipsrssuplier on ipsrspemesanan.kode_suplier=ipsrssuplier.kode_suplier "+
                    "inner join petugas on ipsrspemesanan.nip=petugas.nip "+
                    "inner join ipsrs_detail_titip_faktur on ipsrs_detail_titip_faktur.no_faktur=ipsrspemesanan.no_faktur "+
                    "where ipsrspemesanan.status<>'Sudah Dibayar' and ipsrs_detail_titip_faktur.no_tagihan=?");
            try {
                ps.setString(1,notagihan);
                rs=ps.executeQuery();
                sisahutang=0;
                cicilan=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_faktur"),rs.getString("no_order"),
                        rs.getString("nama_suplier"),rs.getString("nama"),rs.getString("tgl_faktur"),
                        rs.getString("tgl_pesan"),rs.getString("tgl_tempo"),
                        rs.getDouble("tagihan"),(rs.getDouble("tagihan")-rs.getDouble("bayar")),
                        0,(rs.getDouble("tagihan")-rs.getDouble("bayar")),rs.getString("nama_bank"),rs.getString("rekening")
                    });
                    sisahutang=sisahutang+rs.getDouble("tagihan");
                    cicilan=cicilan+rs.getDouble("bayar");
                    KodeSuplier.setText(rs.getString("kode_suplier"));
                    NamaSuplier.setText(rs.getString("nama_suplier"));
                }
                LCount.setText(Valid.SetAngka(sisahutang-cicilan));
                ppSemuaActionPerformed(null);
            } catch (Exception e) {
                System.out.println("Notifikasi Data Hutang: "+e);
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

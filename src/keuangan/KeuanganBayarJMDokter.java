

package keuangan;

import bridging.MandiriCariKodeTransaksiTujuanTransfer;
import com.fasterxml.jackson.databind.JsonNode;
import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
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
import simrskhanza.DlgCariCaraBayar;

/**
 *
 * @author perpustakaan
 */
public final class KeuanganBayarJMDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,psrawatjalandr,psrawatjalandrpr,psrawatinapdr,psrawatinapdrpr,psbiayaoperator1,psbiayaoperator2,psbiayaoperator3,psbiayadokter_anak,
            psbiayadokter_anestesi,psdetaillab,psperiksa_lab,psperiksa_radiologi,psperiksa_lab2,psdetaillab2,psperiksa_radiologi2,psbiaya_dokter_pjanak,psbiaya_dokter_umum;
    private ResultSet rs,rsrawatjalandr,rsrawatjalandrpr,rsrawatinapdr,rsrawatinapdrpr,rsbiayaoperator1,rsbiayaoperator2,rsbiayaoperator3,rsbiayadokter_anak,
            rsbiayadokter_anestesi,rsdetaillab,rsperiksa_lab,rsperiksa_radiologi,rsbiaya_dokter_pjanak,rsbiaya_dokter_umum;
    private int row=0,i=0;
    private double total=0,bayar=0,totalrawatjalan=0,totalrawatinap=0,totallabrawatjalan=0,totallabrawatinap=0,totalradrawatjalan=0,totalradrawatinap=0,totaloperasirawatjalan=0,totaloperasirawatinap=0;
    private boolean sukses=true;  
    private KeuanganCariBayarJMDokter form=new KeuanganCariBayarJMDokter(null,false);

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganBayarJMDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        DlgBayarMandiri.setSize(562,193);

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","Tanggal","Jam","No.Rawat","No.RM","Nama Pasien","Kode/ID","Tindakan Medis","Status","Jasa Medis","Id Detail"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Double.class,java.lang.Object.class
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

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(22);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(55);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(240);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        NoTagihan.setDocument(new batasInput((byte)17).getKata(NoTagihan));
        Keterangan.setDocument(new batasInput((int)150).getKata(Keterangan));        
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
            });
        }  
        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        NoTagihan.setDocument(new batasInput((int)17).getKata(NoTagihan));
        Keterangan.setDocument(new batasInput((int)70).getKata(Keterangan));
                
        form.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("KeuanganBayarJMDokter")){
                    if(form.dokter.getTable().getSelectedRow()!= -1){
                        kddokter.setText(form.dokter.getTable().getValueAt(form.dokter.getTable().getSelectedRow(),0).toString());
                        nmdokter.setText(form.dokter.getTable().getValueAt(form.dokter.getTable().getSelectedRow(),1).toString());
                    }   
                    kddokter.requestFocus();
                }   
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {form.dokter.emptTeks();}
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

        TKd = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
        KdCaraBayar = new widget.TextBox();
        DlgBayarMandiri = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        jLabel99 = new widget.Label();
        BtnKeluarMandiri = new widget.Button();
        BtnSimpanMandiri = new widget.Button();
        NoRekening = new widget.TextBox();
        RekeningAtasNama = new widget.TextBox();
        KotaAtasNamaRekening = new widget.TextBox();
        BtnMetode = new widget.Button();
        jLabel102 = new widget.Label();
        BiayaTransaksi = new widget.TextBox();
        KodeMetode = new widget.TextBox();
        MetodePembayaran = new widget.TextBox();
        jLabel103 = new widget.Label();
        KodeBank = new widget.TextBox();
        BankTujuan = new widget.TextBox();
        KodeTransaksi = new widget.TextBox();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label19 = new widget.Label();
        NmCaraBayar = new widget.TextBox();
        BtnCaraBayarRalanDokter = new widget.Button();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnGaji = new widget.Button();
        panelisi1 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LCount1 = new javax.swing.JLabel();
        BtnSimpan = new widget.Button();
        BtnCari1 = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi5 = new widget.panelisi();
        chkRalan = new widget.CekBox();
        chkRanap = new widget.CekBox();
        chkOperasi = new widget.CekBox();
        chkLaborat = new widget.CekBox();
        chkRadiologi = new widget.CekBox();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        panelisi4 = new widget.panelisi();
        label32 = new widget.Label();
        label36 = new widget.Label();
        NoTagihan = new widget.TextBox();
        label16 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        BtnPetugas = new widget.Button();
        Keterangan = new widget.TextBox();
        label39 = new widget.Label();
        jLabel13 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        BtnAll1 = new widget.Button();

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

        KdCaraBayar.setEditable(false);
        KdCaraBayar.setName("KdCaraBayar"); // NOI18N
        KdCaraBayar.setPreferredSize(new java.awt.Dimension(50, 23));

        DlgBayarMandiri.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgBayarMandiri.setName("DlgBayarMandiri"); // NOI18N
        DlgBayarMandiri.setUndecorated(true);
        DlgBayarMandiri.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Pembayaran Pihak Ke 3 Bank Mandiri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        jLabel99.setText("Kota Bank Tujuan :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelBiasa2.add(jLabel99);
        jLabel99.setBounds(226, 10, 120, 23);

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
        BtnKeluarMandiri.setBounds(442, 130, 100, 30);

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
        BtnSimpanMandiri.setBounds(10, 130, 100, 30);

        NoRekening.setEditable(false);
        NoRekening.setHighlighter(null);
        NoRekening.setName("NoRekening"); // NOI18N
        NoRekening.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRekeningKeyPressed(evt);
            }
        });
        panelBiasa2.add(NoRekening);
        NoRekening.setBounds(84, 10, 130, 23);

        RekeningAtasNama.setEditable(false);
        RekeningAtasNama.setHighlighter(null);
        RekeningAtasNama.setName("RekeningAtasNama"); // NOI18N
        RekeningAtasNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RekeningAtasNamaKeyPressed(evt);
            }
        });
        panelBiasa2.add(RekeningAtasNama);
        RekeningAtasNama.setBounds(84, 40, 456, 23);

        KotaAtasNamaRekening.setEditable(false);
        KotaAtasNamaRekening.setHighlighter(null);
        KotaAtasNamaRekening.setName("KotaAtasNamaRekening"); // NOI18N
        KotaAtasNamaRekening.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KotaAtasNamaRekeningKeyPressed(evt);
            }
        });
        panelBiasa2.add(KotaAtasNamaRekening);
        KotaAtasNamaRekening.setBounds(350, 10, 190, 23);

        BtnMetode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMetode.setMnemonic('1');
        BtnMetode.setToolTipText("ALt+1");
        BtnMetode.setName("BtnMetode"); // NOI18N
        BtnMetode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMetodeActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnMetode);
        BtnMetode.setBounds(512, 70, 28, 23);

        jLabel102.setText("Metode :");
        jLabel102.setName("jLabel102"); // NOI18N
        panelBiasa2.add(jLabel102);
        jLabel102.setBounds(0, 70, 80, 23);

        BiayaTransaksi.setEditable(false);
        BiayaTransaksi.setHighlighter(null);
        BiayaTransaksi.setName("BiayaTransaksi"); // NOI18N
        panelBiasa2.add(BiayaTransaksi);
        BiayaTransaksi.setBounds(399, 70, 110, 23);

        KodeMetode.setEditable(false);
        KodeMetode.setHighlighter(null);
        KodeMetode.setName("KodeMetode"); // NOI18N
        panelBiasa2.add(KodeMetode);
        KodeMetode.setBounds(84, 70, 64, 23);

        MetodePembayaran.setEditable(false);
        MetodePembayaran.setHighlighter(null);
        MetodePembayaran.setName("MetodePembayaran"); // NOI18N
        panelBiasa2.add(MetodePembayaran);
        MetodePembayaran.setBounds(151, 70, 245, 23);

        jLabel103.setText("Bank Tujuan :");
        jLabel103.setName("jLabel103"); // NOI18N
        panelBiasa2.add(jLabel103);
        jLabel103.setBounds(0, 100, 80, 23);

        KodeBank.setEditable(false);
        KodeBank.setHighlighter(null);
        KodeBank.setName("KodeBank"); // NOI18N
        panelBiasa2.add(KodeBank);
        KodeBank.setBounds(84, 100, 64, 23);

        BankTujuan.setEditable(false);
        BankTujuan.setHighlighter(null);
        BankTujuan.setName("BankTujuan"); // NOI18N
        panelBiasa2.add(BankTujuan);
        BankTujuan.setBounds(151, 100, 245, 23);

        KodeTransaksi.setEditable(false);
        KodeTransaksi.setHighlighter(null);
        KodeTransaksi.setName("KodeTransaksi"); // NOI18N
        panelBiasa2.add(KodeTransaksi);
        KodeTransaksi.setBounds(399, 100, 141, 23);

        jLabel100.setText("Atas Nama :");
        jLabel100.setName("jLabel100"); // NOI18N
        panelBiasa2.add(jLabel100);
        jLabel100.setBounds(0, 40, 80, 23);

        jLabel101.setText("No.Rekening :");
        jLabel101.setName("jLabel101"); // NOI18N
        panelBiasa2.add(jLabel101);
        jLabel101.setBounds(0, 10, 80, 23);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Bayar Jasa Medis Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 145));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label19);

        NmCaraBayar.setEditable(false);
        NmCaraBayar.setName("NmCaraBayar"); // NOI18N
        NmCaraBayar.setPreferredSize(new java.awt.Dimension(175, 23));
        panelisi3.add(NmCaraBayar);

        BtnCaraBayarRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRalanDokter.setMnemonic('3');
        BtnCaraBayarRalanDokter.setToolTipText("Alt+3");
        BtnCaraBayarRalanDokter.setName("BtnCaraBayarRalanDokter"); // NOI18N
        BtnCaraBayarRalanDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRalanDokterActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCaraBayarRalanDokter);

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi3.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(270, 23));
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

        BtnGaji.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/gaji.png"))); // NOI18N
        BtnGaji.setMnemonic('2');
        BtnGaji.setToolTipText("Alt+2");
        BtnGaji.setName("BtnGaji"); // NOI18N
        BtnGaji.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGajiActionPerformed(evt);
            }
        });
        BtnGaji.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnGajiKeyPressed(evt);
            }
        });
        panelisi3.add(BtnGaji);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total J.M. :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(66, 23));
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
        jLabel11.setText("Dibayar :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(76, 23));
        panelisi1.add(jLabel11);

        LCount1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount1.setForeground(new java.awt.Color(50, 50, 50));
        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(125, 23));
        panelisi1.add(LCount1);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('B');
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

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari1.setMnemonic('C');
        BtnCari1.setText("Cari");
        BtnCari1.setToolTipText("Alt+C");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);

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

        chkRalan.setSelected(true);
        chkRalan.setText("Rawat Jalan");
        chkRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRalan.setName("chkRalan"); // NOI18N
        chkRalan.setOpaque(false);
        chkRalan.setPreferredSize(new java.awt.Dimension(100, 23));
        chkRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRalanActionPerformed(evt);
            }
        });
        panelisi5.add(chkRalan);

        chkRanap.setSelected(true);
        chkRanap.setText("Rawat Inap");
        chkRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRanap.setName("chkRanap"); // NOI18N
        chkRanap.setOpaque(false);
        chkRanap.setPreferredSize(new java.awt.Dimension(100, 23));
        chkRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRanapActionPerformed(evt);
            }
        });
        panelisi5.add(chkRanap);

        chkOperasi.setSelected(true);
        chkOperasi.setText("Operasi");
        chkOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkOperasi.setName("chkOperasi"); // NOI18N
        chkOperasi.setOpaque(false);
        chkOperasi.setPreferredSize(new java.awt.Dimension(85, 23));
        chkOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOperasiActionPerformed(evt);
            }
        });
        panelisi5.add(chkOperasi);

        chkLaborat.setSelected(true);
        chkLaborat.setText("Laboratorium");
        chkLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLaborat.setName("chkLaborat"); // NOI18N
        chkLaborat.setOpaque(false);
        chkLaborat.setPreferredSize(new java.awt.Dimension(110, 23));
        chkLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLaboratActionPerformed(evt);
            }
        });
        panelisi5.add(chkLaborat);

        chkRadiologi.setSelected(true);
        chkRadiologi.setText("Radiologi");
        chkRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRadiologi.setName("chkRadiologi"); // NOI18N
        chkRadiologi.setOpaque(false);
        chkRadiologi.setPreferredSize(new java.awt.Dimension(90, 23));
        chkRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRadiologiActionPerformed(evt);
            }
        });
        panelisi5.add(chkRadiologi);

        jLabel12.setText("Status :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi5.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Piutang Belum Lunas", "Piutang Sudah Lunas", "Sudah Bayar Non Piutang", "Belum Terclosing Kasir" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(180, 23));
        panelisi5.add(cmbStatus);

        jPanel1.add(panelisi5, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 74));
        panelisi4.setLayout(null);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(197, 10, 59, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(0, 40, 74, 23);

        NoTagihan.setHighlighter(null);
        NoTagihan.setName("NoTagihan"); // NOI18N
        NoTagihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTagihanKeyPressed(evt);
            }
        });
        panelisi4.add(NoTagihan);
        NoTagihan.setBounds(78, 10, 120, 23);

        label16.setText("Dokter :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(label16);
        label16.setBounds(363, 10, 70, 23);

        kddokter.setEditable(false);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(80, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        panelisi4.add(kddokter);
        kddokter.setBounds(437, 10, 100, 23);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmdokter);
        nmdokter.setBounds(539, 10, 181, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);
        Tanggal.setBounds(260, 10, 90, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('1');
        BtnPetugas.setToolTipText("ALt+1");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        panelisi4.add(BtnPetugas);
        BtnPetugas.setBounds(722, 10, 28, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi4.add(Keterangan);
        Keterangan.setBounds(78, 40, 272, 23);

        label39.setText("Nomor J.M. :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(0, 10, 74, 23);

        jLabel13.setText("Akun Bayar :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelisi4.add(jLabel13);
        jLabel13.setBounds(363, 40, 70, 23);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        panelisi4.add(AkunBayar);
        AkunBayar.setBounds(437, 40, 283, 23);

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
        BtnAll1.setBounds(722, 40, 28, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        KdCaraBayar.setText("");
        NmCaraBayar.setText("");
        prosesCari();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari1, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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
        prosesCari();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            prosesCari();
        }else{
            Valid.pindah(evt, TKd, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/akunbayarhutang.iyem")<30){
                tampilAkunBayar2();
            }else{
                tampilAkunBayar();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoTagihan.getText().trim().equals("")){
            Valid.textKosong(NoTagihan,"No.Bayar");
        }else if(kddokter.getText().trim().equals("")||nmdokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"Petugas");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda simpan...!!!!");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(bayar<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan tagihan...!!!!");
            tbBangsal.requestFocus();
        }else{
            try {
                form.koderekening="";
                try {
                    form.myObj = new FileReader("./cache/akunbayarhutang.iyem");
                    form.root = form.mapper.readTree(form.myObj);
                    form.response = form.root.path("akunbayarhutang");
                    if(form.response.isArray()){
                       for(JsonNode list:form.response){
                           if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                                form.koderekening=list.path("KodeRek").asText();  
                           }
                       }
                    }
                    form.myObj.close();
                } catch (Exception e) {
                    form.koderekening="";
                } 
                if(form.koderekening.equals("")){
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan akun bayar, silahkan hubungi administrator..!!");
                }else{
                    if(form.koderekening.equals(form.Host_to_Host_Bank_Mandiri)){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pembayaran_pihak_ke3_bankmandiri.nomor_pembayaran,6),signed)),0) from pembayaran_pihak_ke3_bankmandiri where left(pembayaran_pihak_ke3_bankmandiri.tgl_pembayaran,10)='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",form.kodemcm+"14"+Tanggal.getSelectedItem().toString().substring(0,10).replaceAll("-",""),6,NoTagihan); 
                        form.myObj = new FileReader("./cache/pegawai.iyem");
                        form.root = form.mapper.readTree(form.myObj);
                        Valid.tabelKosong(tabMode);
                        form.response = form.root.path("pegawai");
                        if(form.response.isArray()){
                            for(JsonNode list:form.response){
                                if(list.path("NIP").asText().equals(kddokter.getText())){
                                    RekeningAtasNama.setText(list.path("Nama").asText());
                                    KotaAtasNamaRekening.setText(list.path("Kota").asText());
                                    NoRekening.setText(list.path("Rekening").asText());
                                    BankTujuan.setText(list.path("BPD").asText());
                                }
                            }
                        }
                        form.myObj.close();
                        DlgBayarMandiri.setLocationRelativeTo(internalFrame1);
                        DlgBayarMandiri.setVisible(true);
                    }else{
                        i = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if (i == JOptionPane.YES_OPTION) {
                            Sequel.AutoComitFalse();
                            sukses=true;
                            if(Sequel.menyimpantf2("bayar_jm_dokter","?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Tagihan",14,new String[]{
                                NoTagihan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),kddokter.getText(),bayar+"",AkunBayar.getSelectedItem().toString(),Keterangan.getText(),
                                totalrawatjalan+"",totalrawatinap+"",totallabrawatjalan+"",totallabrawatinap+"",totalradrawatjalan+"",totalradrawatinap+"",totaloperasirawatjalan+"",totaloperasirawatinap+""
                            })==true){
                                row=tbBangsal.getRowCount();
                                for(i=0;i<row;i++){
                                    if(tabMode.getValueAt(i,0).toString().equals("true")){
                                        if(tbBangsal.getValueAt(i,8).toString().equals("Rawat Jalan Dr")){
                                            if(Sequel.menyimpantf2("bayar_rawat_jl_dr","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Rawat Jalan DrPr")){
                                            if(Sequel.menyimpantf2("bayar_rawat_jl_drpr","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Rawat Inap Dr")){
                                            if(Sequel.menyimpantf2("bayar_rawat_inap_dr","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Rawat Inap DrPr")){
                                            if(Sequel.menyimpantf2("bayar_rawat_inap_drpr","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            } 
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ralan PJ")){
                                            if(Sequel.menyimpantf2("bayar_periksa_lab","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ralan PJ Detail")){
                                            if(Sequel.menyimpantf2("bayar_detail_periksa_lab","?,?,?,?,?,?,?",7,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,10).toString(),
                                                tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ralan Perujuk")){
                                            if(Sequel.menyimpantf2("bayar_periksa_lab_perujuk","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ralan Perujuk Detail")){
                                            if(Sequel.menyimpantf2("bayar_detail_periksa_lab_perujuk","?,?,?,?,?,?,?",7,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,10).toString(),
                                                tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ranap PJ")){
                                            if(Sequel.menyimpantf2("bayar_periksa_lab","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ranap PJ Detail")){
                                            if(Sequel.menyimpantf2("bayar_detail_periksa_lab","?,?,?,?,?,?,?",7,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,10).toString(),
                                                tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ranap Perujuk")){
                                            if(Sequel.menyimpantf2("bayar_periksa_lab_perujuk","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ranap Perujuk Detail")){
                                            if(Sequel.menyimpantf2("bayar_detail_periksa_lab_perujuk","?,?,?,?,?,?,?",7,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,10).toString(),
                                                tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Radiologi Ralan PJ")){
                                            if(Sequel.menyimpantf2("bayar_periksa_radiologi","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Radiologi Ralan Perujuk")){
                                            if(Sequel.menyimpantf2("bayar_periksa_radiologi_perujuk","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Radiologi Ranap PJ")){
                                            if(Sequel.menyimpantf2("bayar_periksa_radiologi","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Radiologi Ranap Perujuk")){
                                            if(Sequel.menyimpantf2("bayar_periksa_radiologi_perujuk","?,?,?,?,?,?",6,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan Op1")){
                                            if(Sequel.menyimpantf2("bayar_operasi_operator1","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan Op2")){
                                            if(Sequel.menyimpantf2("bayar_operasi_operator2","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan Op3")){
                                            if(Sequel.menyimpantf2("bayar_operasi_operator3","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan dr Anak")){
                                            if(Sequel.menyimpantf2("bayar_operasi_dokter_anak","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan dr Anastesi")){
                                            if(Sequel.menyimpantf2("bayar_operasi_dokter_anestesi","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan dr PJ Anak")){
                                            if(Sequel.menyimpantf2("bayar_operasi_dokter_pjanak","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan dr Umum")){
                                            if(Sequel.menyimpantf2("bayar_operasi_dokter_umum","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap Op1")){
                                            if(Sequel.menyimpantf2("bayar_operasi_operator1","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap Op2")){
                                            if(Sequel.menyimpantf2("bayar_operasi_operator2","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap Op3")){
                                            if(Sequel.menyimpantf2("bayar_operasi_operator3","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap dr Anak")){
                                            if(Sequel.menyimpantf2("bayar_operasi_dokter_anak","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap dr Anastesi")){
                                            if(Sequel.menyimpantf2("bayar_operasi_dokter_anestesi","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap dr PJ Anak")){
                                            if(Sequel.menyimpantf2("bayar_operasi_dokter_pjanak","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap dr Umum")){
                                            if(Sequel.menyimpantf2("bayar_operasi_dokter_umum","?,?,?,?,?",5,new String[]{
                                                NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                                tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                              })==false){
                                                sukses=false;
                                            }
                                        }
                                    }
                                }
                            }else{
                                sukses=false;
                                JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan Nomor J.M. sudah ada sebelumnya...!!");
                            }

                            if(sukses==true){
                                Sequel.queryu("delete from tampjurnal"); 
                                if(totalrawatjalan>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','"+totalrawatjalan+"','0'","debet=debet+'"+(totalrawatjalan)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                                        sukses=false;
                                    }                        
                                }
                                if(totalrawatinap>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+totalrawatinap+"','0'","debet=debet+'"+(totalrawatinap)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(totallabrawatjalan>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang Jasa Medik Dokter Laborat Ralan','"+totallabrawatjalan+"','0'","debet=debet+'"+(totallabrawatjalan)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'")==false){
                                        sukses=false;
                                    }    
                                }
                                if(totallabrawatinap>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang Jasa Medik Dokter Laborat Ranap','"+totallabrawatinap+"','0'","debet=debet+'"+(totallabrawatinap)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(totalradrawatjalan>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Utang Jasa Medik Dokter Radiologi Ralan','"+totalradrawatjalan+"','0'","debet=debet+'"+(totalradrawatjalan)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(totalradrawatinap>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"','Utang Jasa Medik Dokter Radiologi Ranap','"+totalradrawatinap+"','0'","debet=debet+'"+(totalradrawatinap)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(totaloperasirawatjalan>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Operasi_Ralan+"','Utang Jasa Medik Dokter Operasi Ralan','"+totaloperasirawatjalan+"','0'","debet=debet+'"+totaloperasirawatjalan+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Operasi_Ralan+"'")==false){
                                        sukses=false;
                                    }                             
                                }
                                if(totaloperasirawatinap>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang Jasa Medik Dokter Operasi Ranap','"+totaloperasirawatinap+"','0'","debet=debet+'"+totaloperasirawatinap+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Operasi_Ranap+"'")==false){
                                        sukses=false;
                                    }                             
                                }
                                if(bayar>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+form.koderekening+"','"+AkunBayar.getSelectedItem().toString()+"','0','"+bayar+"'","kredit=kredit+'"+(bayar)+"'","kd_rek='"+form.koderekening+"'")==false){
                                        sukses=false;
                                    }  
                                }
                                if(sukses==true){
                                    sukses=form.jur.simpanJurnal(NoTagihan.getText(),"U","PEMBAYARAN JASA MEDIS DOKTER "+kddokter.getText()+" "+nmdokter.getText()+", OLEH "+akses.getkode());
                                }
                            }

                            if(sukses==true){
                                autoNomor();
                                for(i=0;i<tbBangsal.getRowCount();i++){  
                                    if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                                        tabMode.removeRow(i);
                                        i--;
                                    }
                                }
                                bayar=0;totalrawatjalan=0;totalrawatinap=0;totallabrawatjalan=0;totallabrawatinap=0;totalradrawatjalan=0;totalradrawatinap=0;totaloperasirawatjalan=0;totaloperasirawatinap=0;
                                LCount1.setText("0");
                                Sequel.Commit();
                            }else{
                                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                                Sequel.RollBack();
                            }

                            Sequel.AutoComitTrue();
                        }
                    }
                }
            } catch (Exception ex) {
               System.out.println(ex);
            } 
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt,TCari,BtnCari1);
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void NoTagihanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTagihanKeyPressed
       Valid.pindah(evt,TCari,Tanggal);
    }//GEN-LAST:event_NoTagihanKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah2(evt,NoTagihan,AkunBayar);
    }//GEN-LAST:event_TanggalKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,BtnPetugas,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(row=0;row<tbBangsal.getRowCount();row++){
            tbBangsal.setValueAt(false,row,0);
        }
        getData();
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for(row=0;row<tbBangsal.getRowCount();row++){
            tbBangsal.setValueAt(true,row,0);
        }
        getData();
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.emptTeks();
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        Valid.pindah(evt,BtnSimpan,BtnKeluar);
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void chkRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRalanActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRalanActionPerformed

    private void chkRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRanapActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRanapActionPerformed

    private void chkOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOperasiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkOperasiActionPerformed

    private void chkLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLaboratActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkLaboratActionPerformed

    private void chkRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRadiologiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRadiologiActionPerformed

    private void BtnCaraBayarRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRalanDokterActionPerformed
        DlgCariCaraBayar carabayar=new DlgCariCaraBayar(null,false);
        carabayar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carabayar.getTable().getSelectedRow()!= -1){
                    KdCaraBayar.setText(carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(),1).toString());
                    NmCaraBayar.setText(carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(),2).toString());
                }     
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {carabayar.onCari();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        carabayar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    carabayar.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        carabayar.isCek();
        carabayar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carabayar.setLocationRelativeTo(internalFrame1);
        carabayar.setAlwaysOnTop(false);
        carabayar.setVisible(true);
    }//GEN-LAST:event_BtnCaraBayarRalanDokterActionPerformed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        akses.setform("KeuanganBayarJMDokter");
        form.dokter.emptTeks();
        form.dokter.isCek();
        form.dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.dokter.setLocationRelativeTo(internalFrame1);
        form.dokter.setAlwaysOnTop(false);
        form.dokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void AkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkunBayarKeyPressed
        Valid.pindah(evt,Tanggal,BtnPetugas);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                getData();
            }
        }
    }//GEN-LAST:event_tbBangsalKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            if(tbBangsal.getSelectedColumn()==0){
                getData();
            }
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbBangsalPropertyChange
        if(this.isVisible()==true){
            getData();
        }
    }//GEN-LAST:event_tbBangsalPropertyChange

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,AkunBayar,Keterangan);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void BtnGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGajiActionPerformed
        if(kddokter.getText().trim().equals("")||nmdokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"Dokter");
        }else{
            if(bayar==0){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih jasa medis yang mau dibayar...!!!!");
            }else if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(tabMode.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                //"P","Tanggal","Jam","No.Rawat","No.RM","Nama Pasien","Kode","Tindakan Medis","Status","Jasa Medis","Id Detail"
                row=1;
                for(i=0;i<tabMode.getRowCount();i++){  
                    if(tabMode.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("temporary","'"+row+"','"+
                            tabMode.getValueAt(i,1).toString().replaceAll("'","`")+" "+tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"','"+
                            tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"','"+
                            tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"','"+
                            tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"','"+
                            tabMode.getValueAt(i,6).toString().replaceAll("'","`")+"','"+
                            tabMode.getValueAt(i,7).toString().replaceAll("'","`")+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString().replaceAll("'","`")))+
                            "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                        );
                        row++;
                    } 
                }
                row++;
                Sequel.menyimpan("temporary","'"+row+"','>> Jumlah :','','','','','','"+LCount1.getText()+
                    "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                ); 

                Map<String, Object> param = new HashMap<>();   
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("dokter",nmdokter.getText());   
                param.put("bulan",Tanggal.getSelectedItem().toString().substring(3,10));   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptSlipBayarJMDokter.jasper","report","[ Slip J.M. Dokter  ]","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_BtnGajiActionPerformed

    private void BtnGajiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnGajiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnGajiActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnAll);
        }
    }//GEN-LAST:event_BtnGajiKeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        tampilAkunBayar();
        form.tampilAkunBankMandiri();
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
            i = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                if(Sequel.menyimpantf2("bayar_jm_dokter","?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Tagihan",14,new String[]{
                    NoTagihan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),kddokter.getText(),bayar+"",AkunBayar.getSelectedItem().toString(),Keterangan.getText(),
                    totalrawatjalan+"",totalrawatinap+"",totallabrawatjalan+"",totallabrawatinap+"",totalradrawatjalan+"",totalradrawatinap+"",totaloperasirawatjalan+"",totaloperasirawatinap+""
                })==true){
                    row=tbBangsal.getRowCount();
                    for(i=0;i<row;i++){
                        if(tabMode.getValueAt(i,0).toString().equals("true")){
                            if(tbBangsal.getValueAt(i,8).toString().equals("Rawat Jalan Dr")){
                                if(Sequel.menyimpantf2("bayar_rawat_jl_dr","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Rawat Jalan DrPr")){
                                if(Sequel.menyimpantf2("bayar_rawat_jl_drpr","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Rawat Inap Dr")){
                                if(Sequel.menyimpantf2("bayar_rawat_inap_dr","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Rawat Inap DrPr")){
                                if(Sequel.menyimpantf2("bayar_rawat_inap_drpr","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                } 
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ralan PJ")){
                                if(Sequel.menyimpantf2("bayar_periksa_lab","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ralan PJ Detail")){
                                if(Sequel.menyimpantf2("bayar_detail_periksa_lab","?,?,?,?,?,?,?",7,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,10).toString(),
                                    tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ralan Perujuk")){
                                if(Sequel.menyimpantf2("bayar_periksa_lab_perujuk","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ralan Perujuk Detail")){
                                if(Sequel.menyimpantf2("bayar_detail_periksa_lab_perujuk","?,?,?,?,?,?,?",7,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,10).toString(),
                                    tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ranap PJ")){
                                if(Sequel.menyimpantf2("bayar_periksa_lab","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ranap PJ Detail")){
                                if(Sequel.menyimpantf2("bayar_detail_periksa_lab","?,?,?,?,?,?,?",7,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,10).toString(),
                                    tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ranap Perujuk")){
                                if(Sequel.menyimpantf2("bayar_periksa_lab_perujuk","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Laborat Ranap Perujuk Detail")){
                                if(Sequel.menyimpantf2("bayar_detail_periksa_lab_perujuk","?,?,?,?,?,?,?",7,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,10).toString(),
                                    tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Radiologi Ralan PJ")){
                                if(Sequel.menyimpantf2("bayar_periksa_radiologi","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Radiologi Ralan Perujuk")){
                                if(Sequel.menyimpantf2("bayar_periksa_radiologi_perujuk","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Radiologi Ranap PJ")){
                                if(Sequel.menyimpantf2("bayar_periksa_radiologi","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Radiologi Ranap Perujuk")){
                                if(Sequel.menyimpantf2("bayar_periksa_radiologi_perujuk","?,?,?,?,?,?",6,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString(),tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan Op1")){
                                if(Sequel.menyimpantf2("bayar_operasi_operator1","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan Op2")){
                                if(Sequel.menyimpantf2("bayar_operasi_operator2","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan Op3")){
                                if(Sequel.menyimpantf2("bayar_operasi_operator3","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan dr Anak")){
                                if(Sequel.menyimpantf2("bayar_operasi_dokter_anak","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan dr Anastesi")){
                                if(Sequel.menyimpantf2("bayar_operasi_dokter_anestesi","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan dr PJ Anak")){
                                if(Sequel.menyimpantf2("bayar_operasi_dokter_pjanak","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ralan dr Umum")){
                                if(Sequel.menyimpantf2("bayar_operasi_dokter_umum","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap Op1")){
                                if(Sequel.menyimpantf2("bayar_operasi_operator1","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap Op2")){
                                if(Sequel.menyimpantf2("bayar_operasi_operator2","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap Op3")){
                                if(Sequel.menyimpantf2("bayar_operasi_operator3","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap dr Anak")){
                                if(Sequel.menyimpantf2("bayar_operasi_dokter_anak","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap dr Anastesi")){
                                if(Sequel.menyimpantf2("bayar_operasi_dokter_anestesi","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap dr PJ Anak")){
                                if(Sequel.menyimpantf2("bayar_operasi_dokter_pjanak","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }else if(tbBangsal.getValueAt(i,8).toString().equals("Operasi Ranap dr Umum")){
                                if(Sequel.menyimpantf2("bayar_operasi_dokter_umum","?,?,?,?,?",5,new String[]{
                                    NoTagihan.getText(),tbBangsal.getValueAt(i,3).toString(),tbBangsal.getValueAt(i,6).toString(), 
                                    tbBangsal.getValueAt(i,1).toString()+" "+tbBangsal.getValueAt(i,2).toString(),tbBangsal.getValueAt(i,9).toString()
                                  })==false){
                                    sukses=false;
                                }
                            }
                        }
                    }
                }else{
                    sukses=false;
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan, kemungkinan Nomor J.M. sudah ada sebelumnya...!!");
                }

                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal"); 
                    if(Valid.SetInteger(BiayaTransaksi.getText())>0){
                        if(Sequel.menyimpantf2("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                            form.Akun_Biaya_Mandiri,"BIAYA TRANSAKSI",BiayaTransaksi.getText(),"0"
                        })==false){
                            sukses=false;
                        }
                    }
                    if(totalrawatjalan>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','"+totalrawatjalan+"','0'","debet=debet+'"+(totalrawatjalan)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                            sukses=false;
                        }                               
                    }
                    if(totalrawatinap>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+totalrawatinap+"','0'","debet=debet+'"+(totalrawatinap)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }                               
                    }
                    if(totallabrawatjalan>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang Jasa Medik Dokter Laborat Ralan','"+totallabrawatjalan+"','0'","debet=debet+'"+(totallabrawatjalan)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'")==false){
                            sukses=false;
                        }    
                    }
                    if(totallabrawatinap>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang Jasa Medik Dokter Laborat Ranap','"+totallabrawatinap+"','0'","debet=debet+'"+(totallabrawatinap)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'")==false){
                            sukses=false;
                        }                           
                    }
                    if(totalradrawatjalan>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Utang Jasa Medik Dokter Radiologi Ralan','"+totalradrawatjalan+"','0'","debet=debet+'"+(totalradrawatjalan)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'")==false){
                            sukses=false;
                        }                           
                    }
                    if(totalradrawatinap>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"','Utang Jasa Medik Dokter Radiologi Ranap','"+totalradrawatinap+"','0'","debet=debet+'"+(totalradrawatinap)+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"'")==false){
                            sukses=false;
                        }                           
                    }
                    if(totaloperasirawatjalan>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Operasi_Ralan+"','Utang Jasa Medik Dokter Operasi Ralan','"+totaloperasirawatjalan+"','0'","debet=debet+'"+totaloperasirawatjalan+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Operasi_Ralan+"'")==false){
                            sukses=false;
                        }                             
                    }
                    if(totaloperasirawatinap>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+form.Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang Jasa Medik Dokter Operasi Ranap','"+totaloperasirawatinap+"','0'","debet=debet+'"+totaloperasirawatinap+"'","kd_rek='"+form.Utang_Jasa_Medik_Dokter_Operasi_Ranap+"'")==false){
                            sukses=false;
                        }                             
                    }
                    if(bayar>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+form.koderekening+"','"+AkunBayar.getSelectedItem().toString()+"','0','"+(Valid.SetAngka(BiayaTransaksi.getText())+bayar)+"'","kredit=kredit+'"+((Valid.SetAngka(BiayaTransaksi.getText())+bayar))+"'","kd_rek='"+form.koderekening+"'")==false){
                            sukses=false;
                        }  
                    }
                    if(sukses==true){
                        sukses=form.jur.simpanJurnal(NoTagihan.getText(),"U","PEMBAYARAN JASA MEDIS DOKTER "+kddokter.getText()+" "+nmdokter.getText()+", OLEH "+akses.getkode());
                    }
                    if(sukses==true){
                        if(Sequel.menyimpantf("pembayaran_pihak_ke3_bankmandiri","?,now(),?,?,?,?,?,?,?,?,?,?,?","No.Bukti", 12,new String[]{
                                NoTagihan.getText(),form.norekening,NoRekening.getText(),RekeningAtasNama.getText(),KotaAtasNamaRekening.getText(),bayar+"",NoTagihan.getText(),KodeMetode.getText(),KodeBank.getText(),KodeTransaksi.getText(),"Bayar JM Dokter","Baru"
                            })==false){
                            sukses=false;
                        }
                    }
                }

                if(sukses==true){
                    autoNomor();
                    for(i=0;i<tbBangsal.getRowCount();i++){  
                        if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                            tabMode.removeRow(i);
                            i--;
                        }
                    }
                    bayar=0;totalrawatjalan=0;totalrawatinap=0;totallabrawatjalan=0;totallabrawatinap=0;totalradrawatjalan=0;totalradrawatinap=0;totaloperasirawatjalan=0;totaloperasirawatinap=0;
                    LCount1.setText("0");
                    Sequel.Commit();
                    DlgBayarMandiri.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
            }
        }
    }//GEN-LAST:event_BtnSimpanMandiriActionPerformed

    private void BtnSimpanMandiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanMandiriKeyPressed

    }//GEN-LAST:event_BtnSimpanMandiriKeyPressed

    private void NoRekeningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRekeningKeyPressed
        Valid.pindah(evt,BtnKeluarMandiri,KotaAtasNamaRekening);
    }//GEN-LAST:event_NoRekeningKeyPressed

    private void RekeningAtasNamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RekeningAtasNamaKeyPressed
        Valid.pindah(evt,KotaAtasNamaRekening,BtnMetode);
    }//GEN-LAST:event_RekeningAtasNamaKeyPressed

    private void KotaAtasNamaRekeningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KotaAtasNamaRekeningKeyPressed
        Valid.pindah(evt,NoRekening,RekeningAtasNama);
    }//GEN-LAST:event_KotaAtasNamaRekeningKeyPressed

    private void BtnMetodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMetodeActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MandiriCariKodeTransaksiTujuanTransfer kodetransaksibank=new MandiriCariKodeTransaksiTujuanTransfer(null, false);
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
        kodetransaksibank.setCari(BankTujuan.getText());
        kodetransaksibank.isCek();
        kodetransaksibank.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kodetransaksibank.setLocationRelativeTo(internalFrame1);
        kodetransaksibank.setAlwaysOnTop(false);
        kodetransaksibank.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnMetodeActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganBayarJMDokter dialog = new KeuanganBayarJMDokter(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCaraBayarRalanDokter;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnGaji;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarMandiri;
    private widget.Button BtnMetode;
    private widget.Button BtnPetugas;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanMandiri;
    private javax.swing.JDialog DlgBayarMandiri;
    private widget.TextBox KdCaraBayar;
    private widget.TextBox Keterangan;
    private widget.TextBox KodeBank;
    private widget.TextBox KodeMetode;
    private widget.TextBox KodeTransaksi;
    private widget.TextBox KotaAtasNamaRekening;
    private javax.swing.JLabel LCount;
    private javax.swing.JLabel LCount1;
    private widget.TextBox MetodePembayaran;
    private widget.TextBox NmCaraBayar;
    private widget.TextBox NoRekening;
    private widget.TextBox NoTagihan;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox RekeningAtasNama;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal Tanggal;
    private widget.CekBox chkLaborat;
    private widget.CekBox chkOperasi;
    private widget.CekBox chkRadiologi;
    private widget.CekBox chkRalan;
    private widget.CekBox chkRanap;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private javax.swing.JLabel jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private javax.swing.JLabel jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kddokter;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label19;
    private widget.Label label32;
    private widget.Label label36;
    private widget.Label label39;
    private widget.TextBox nmdokter;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSemua;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        if(kddokter.getText().trim().equals("")||nmdokter.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih dokter yang mau dibayarkan jasa medisnya....!!!");
        }else{
            Valid.tabelKosong(tabMode);
            if(cmbStatus.getSelectedItem().equals("Semua")){
                prosesCariSemua();
            }else if(cmbStatus.getSelectedItem().equals("Piutang Belum Lunas")){
                prosesCariPiutangBelumLunas();
            }else if(cmbStatus.getSelectedItem().equals("Piutang Sudah Lunas")){
                prosesCariPiutangSudahLunas();
            }else if(cmbStatus.getSelectedItem().equals("Sudah Bayar Non Piutang")){
                prosesCariSudahBayarNonPiutang();
            }else if(cmbStatus.getSelectedItem().equals("Belum Terclosing Kasir")){
                prosesCariBelumTerclosing();
            }
        }
    }
    
    private void getData(){
        row=tbBangsal.getRowCount();
        bayar=0;totalrawatjalan=0;totalrawatinap=0;totallabrawatjalan=0;totallabrawatinap=0;totalradrawatjalan=0;totalradrawatinap=0;totaloperasirawatjalan=0;totaloperasirawatinap=0;
        for(i=0;i<row;i++){  
            if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                if(tbBangsal.getValueAt(i,8).toString().contains("Rawat Jalan")){
                    totalrawatjalan=totalrawatjalan+Double.parseDouble(tbBangsal.getValueAt(i,9).toString());   
                }else if(tbBangsal.getValueAt(i,8).toString().contains("Rawat Inap")){
                    totalrawatinap=totalrawatinap+Double.parseDouble(tbBangsal.getValueAt(i,9).toString());   
                }else if(tbBangsal.getValueAt(i,8).toString().contains("Laborat Ralan")){
                    totallabrawatjalan=totallabrawatjalan+Double.parseDouble(tbBangsal.getValueAt(i,9).toString());   
                }else if(tbBangsal.getValueAt(i,8).toString().contains("Laborat Ranap")){
                    totallabrawatinap=totallabrawatinap+Double.parseDouble(tbBangsal.getValueAt(i,9).toString());   
                }else if(tbBangsal.getValueAt(i,8).toString().contains("Radiologi Ralan")){
                    totalradrawatjalan=totalradrawatjalan+Double.parseDouble(tbBangsal.getValueAt(i,9).toString());   
                }else if(tbBangsal.getValueAt(i,8).toString().contains("Radiologi Ranap")){
                    totalradrawatinap=totalradrawatinap+Double.parseDouble(tbBangsal.getValueAt(i,9).toString());   
                }else if(tbBangsal.getValueAt(i,8).toString().contains("Operasi Ralan")){
                    totaloperasirawatjalan=totaloperasirawatjalan+Double.parseDouble(tbBangsal.getValueAt(i,9).toString());   
                }else if(tbBangsal.getValueAt(i,8).toString().contains("Operasi Ranap")){
                    totaloperasirawatinap=totaloperasirawatinap+Double.parseDouble(tbBangsal.getValueAt(i,9).toString());   
                }
                
                bayar=bayar+Double.parseDouble(tbBangsal.getValueAt(i,9).toString());     
            }
        }
        LCount1.setText(Valid.SetAngka(bayar));
    }
    
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(bayar_jm_dokter.no_bayar,3),signed)),0) from bayar_jm_dokter where bayar_jm_dokter.tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "JMD"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoTagihan); 
    }
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        BtnSimpan.setEnabled(akses.getbayar_jm_dokter());
    }
    
    private void tampilAkunBayar() {         
         try{      
             form.file=new File("./cache/akunbayarhutang.iyem");
             form.file.createNewFile();
             form.fileWriter = new FileWriter(form.file);
             form.iyem="";
             ps=koneksi.prepareStatement("select * from akun_bayar_hutang order by akun_bayar_hutang.nama_bayar");
             try{
                 rs=ps.executeQuery();
                 AkunBayar.removeAllItems();
                 while(rs.next()){    
                     AkunBayar.addItem(rs.getString(1).replaceAll("\"",""));
                     form.iyem=form.iyem+"{\"NamaAkun\":\""+rs.getString(1).replaceAll("\"","")+"\",\"KodeRek\":\""+rs.getString(2)+"\"},";
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

             form.fileWriter.write("{\"akunbayarhutang\":["+form.iyem.substring(0,form.iyem.length()-1)+"]}");
             form.fileWriter.flush();
             form.fileWriter.close();
             form.iyem=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilAkunBayar2() {
        try {
            form.myObj = new FileReader("./cache/akunbayarhutang.iyem");
            form.root = form.mapper.readTree(form.myObj);
            form.response = form.root.path("akunbayarhutang");
            if(form.response.isArray()){
                for(JsonNode list:form.response){
                    AkunBayar.addItem(list.path("NamaAkun").asText().replaceAll("\"",""));
                }
            }
            form.myObj.close();
        } catch (Exception ex) {
            if(ex.toString().contains("java.io.FileNotFoundException")){
                tampilAkunBayar();
            }else{
                System.out.println("Notifikasi : "+ex);
            }
        }
    } 
    
    private void prosesCariSemua() {
         try{         
            total=0;  
            if(chkRalan.isSelected()==true){
                 //rawat jalan     
                 psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where rawat_jl_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_dr.no_rawat,rawat_jl_dr.kd_jenis_prw,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_dr.no_rawat,bayar_rawat_jl_dr.kd_jenis_prw,bayar_rawat_jl_dr.tgl_perawatan,bayar_rawat_jl_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_dr on bayar_rawat_jl_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_dr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where rawat_jl_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_drpr.no_rawat,rawat_jl_drpr.kd_jenis_prw,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_drpr.no_rawat,bayar_rawat_jl_drpr.kd_jenis_prw,bayar_rawat_jl_drpr.tgl_perawatan,bayar_rawat_jl_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_drpr on bayar_rawat_jl_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_drpr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 try {
                     psrawatjalandr.setString(1,kddokter.getText());
                     psrawatjalandr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandr=psrawatjalandr.executeQuery();
                     
                     psrawatjalandrpr.setString(1,kddokter.getText());
                     psrawatjalandrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                     
                     while(rsrawatjalandr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandr.getString("tgl_perawatan"),rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),
                             rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",rsrawatjalandr.getString("kd_jenis_prw"),rsrawatjalandr.getString("nm_perawatan"),
                             "Rawat Jalan Dr",rsrawatjalandr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                     }  

                     while(rsrawatjalandrpr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandrpr.getString("tgl_perawatan"),rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),
                             rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",rsrawatjalandrpr.getString("kd_jenis_prw"),rsrawatjalandrpr.getString("nm_perawatan"),
                             "Rawat Jalan DrPr",rsrawatjalandrpr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi RJ : "+e);
                 } finally{
                     if(rsrawatjalandr!=null){
                        rsrawatjalandr.close(); 
                     }
                     if(psrawatjalandr!=null){
                        psrawatjalandr.close(); 
                     }
                     if(rsrawatjalandrpr!=null){
                        rsrawatjalandrpr.close(); 
                     }
                     if(psrawatjalandrpr!=null){
                        psrawatjalandrpr.close(); 
                     }
                 }
            }

            if(chkRanap.isSelected()==true){
                 //rawat inap    
                 psrawatinapdr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                     "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where rawat_inap_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_dr.no_rawat,rawat_inap_dr.kd_jenis_prw,rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_dr.no_rawat,bayar_rawat_inap_dr.kd_jenis_prw,bayar_rawat_inap_dr.tgl_perawatan,bayar_rawat_inap_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_dr on bayar_rawat_inap_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_dr.tgl_perawatan like ?)")+
                     "order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 psrawatinapdrpr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                     "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where rawat_inap_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_drpr.no_rawat,rawat_inap_drpr.kd_jenis_prw,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_drpr.no_rawat,bayar_rawat_inap_drpr.kd_jenis_prw,bayar_rawat_inap_drpr.tgl_perawatan,bayar_rawat_inap_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_drpr on bayar_rawat_inap_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_drpr.tgl_perawatan like ?)")+
                     "order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 try {                            
                     psrawatinapdr.setString(1,kddokter.getText());
                     psrawatinapdr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdr=psrawatinapdr.executeQuery();
                     
                     psrawatinapdrpr.setString(1,kddokter.getText());
                     psrawatinapdrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                     
                     while(rsrawatinapdr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdr.getString("tgl_perawatan"),rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),
                             rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",rsrawatinapdr.getString("kd_jenis_prw"),rsrawatinapdr.getString("nm_perawatan"),
                             "Rawat Inap Dr",rsrawatinapdr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                     }

                     while(rsrawatinapdrpr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdrpr.getString("tgl_perawatan"),rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),
                             rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",rsrawatinapdrpr.getString("kd_jenis_prw"),rsrawatinapdrpr.getString("nm_perawatan"),
                             "Rawat Inap DrPr",rsrawatinapdrpr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Ranap : "+e);
                 } finally{
                     if(rsrawatinapdr!=null){
                         rsrawatinapdr.close();
                     }
                     if(psrawatinapdr!=null){
                         psrawatinapdr.close();
                     }
                     if(rsrawatinapdrpr!=null){
                         rsrawatinapdrpr.close();
                     }
                     if(psrawatinapdrpr!=null){
                         psrawatinapdrpr.close();
                     }
                 }
            }               
            
            if(chkOperasi.isSelected()==true){
                 psbiayaoperator1=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where operasi.operator1=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator1) not in "+
                     "(select concat(bayar_operasi_operator1.no_rawat,bayar_operasi_operator1.kode_paket,bayar_operasi_operator1.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator1 on bayar_operasi_operator1.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator2=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where operasi.operator2=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator2) not in "+
                     "(select concat(bayar_operasi_operator2.no_rawat,bayar_operasi_operator2.kode_paket,bayar_operasi_operator2.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator2 on bayar_operasi_operator2.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator3=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where operasi.operator3=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator3) not in "+
                     "(select concat(bayar_operasi_operator3.no_rawat,bayar_operasi_operator3.kode_paket,bayar_operasi_operator3.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator3 on bayar_operasi_operator3.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where operasi.dokter_anak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anak) not in "+
                     "(select concat(bayar_operasi_dokter_anak.no_rawat,bayar_operasi_dokter_anak.kode_paket,bayar_operasi_dokter_anak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anak on bayar_operasi_dokter_anak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_umum=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where operasi.dokter_umum=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_umum) not in "+
                     "(select concat(bayar_operasi_dokter_umum.no_rawat,bayar_operasi_dokter_umum.kode_paket,bayar_operasi_dokter_umum.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_umum on bayar_operasi_dokter_umum.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_pjanak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where operasi.dokter_pjanak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_pjanak) not in "+
                     "(select concat(bayar_operasi_dokter_pjanak.no_rawat,bayar_operasi_dokter_pjanak.kode_paket,bayar_operasi_dokter_pjanak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_pjanak on bayar_operasi_dokter_pjanak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anestesi=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where operasi.dokter_anestesi=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anestesi) not in "+
                     "(select concat(bayar_operasi_dokter_anestesi.no_rawat,bayar_operasi_dokter_anestesi.kode_paket,bayar_operasi_dokter_anestesi.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anestesi on bayar_operasi_dokter_anestesi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 try {
                     psbiayaoperator1.setString(1,kddokter.getText());               
                     psbiayaoperator1.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator1.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator1=psbiayaoperator1.executeQuery();
                     
                     psbiayaoperator2.setString(1,kddokter.getText());               
                     psbiayaoperator2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator2.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator2=psbiayaoperator2.executeQuery();
                     
                     psbiayaoperator3.setString(1,kddokter.getText());  
                     psbiayaoperator3.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator3.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator3=psbiayaoperator3.executeQuery();
                     
                     psbiayadokter_anak.setString(1,kddokter.getText());  
                     psbiayadokter_anak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                     
                     psbiaya_dokter_umum.setString(1,kddokter.getText());  
                     psbiaya_dokter_umum.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_umum.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                     
                     psbiaya_dokter_pjanak.setString(1,kddokter.getText());  
                     psbiaya_dokter_pjanak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_pjanak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                     
                     psbiayadokter_anestesi.setString(1,kddokter.getText());                 
                     psbiayadokter_anestesi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anestesi.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                     
                     while(rsbiayaoperator1.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator1.getString("tgl_operasi").substring(0,10),rsbiayaoperator1.getString("tgl_operasi").substring(11,19),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),
                             rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",rsbiayaoperator1.getString("kode_paket"),rsbiayaoperator1.getString("nm_perawatan")+"(Operator 1)",
                             "Operasi "+rsbiayaoperator1.getString("status")+" Op1",rsbiayaoperator1.getDouble("biayaoperator1")
                         });      
                         total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                     }

                     while(rsbiayaoperator2.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator2.getString("tgl_operasi").substring(0,10),rsbiayaoperator2.getString("tgl_operasi").substring(11,19),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),
                             rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",rsbiayaoperator2.getString("kode_paket"),rsbiayaoperator2.getString("nm_perawatan")+"(Operator 2)",
                             "Operasi "+rsbiayaoperator2.getString("status")+" Op2",rsbiayaoperator2.getDouble("biayaoperator2")
                         });  
                         total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                     }

                     while(rsbiayaoperator3.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator3.getString("tgl_operasi").substring(0,10),rsbiayaoperator3.getString("tgl_operasi").substring(11,19),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),
                             rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",rsbiayaoperator3.getString("kode_paket"),rsbiayaoperator3.getString("nm_perawatan")+"(Operator 3)",
                             "Operasi "+rsbiayaoperator3.getString("status")+" Op3",rsbiayaoperator3.getDouble("biayaoperator3")
                         });       
                         total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                     }

                     while(rsbiayadokter_anak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anak.getString("tgl_operasi").substring(0,10),rsbiayadokter_anak.getString("tgl_operasi").substring(11,19),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),
                             rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",rsbiayadokter_anak.getString("kode_paket"),rsbiayadokter_anak.getString("nm_perawatan")+"(dr Anak)",
                             "Operasi "+rsbiayadokter_anak.getString("status")+" dr Anak",rsbiayadokter_anak.getDouble("biayadokter_anak")
                         });    
                         total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                     }

                     while(rsbiayadokter_anestesi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anestesi.getString("tgl_operasi").substring(0,10),rsbiayadokter_anestesi.getString("tgl_operasi").substring(11,19),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),
                             rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",rsbiayadokter_anestesi.getString("kode_paket"),rsbiayadokter_anestesi.getString("nm_perawatan")+"(dr Anestesi)",
                             "Operasi "+rsbiayadokter_anestesi.getString("status")+" dr Anastesi",rsbiayadokter_anestesi.getDouble("biayadokter_anestesi")
                         });      
                         total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                     }

                     while(rsbiaya_dokter_pjanak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),
                             rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",rsbiaya_dokter_pjanak.getString("kode_paket"),rsbiaya_dokter_pjanak.getString("nm_perawatan")+"(dr Pj Anak)",
                             "Operasi "+rsbiaya_dokter_pjanak.getString("status")+" dr PJ Anak",rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak")
                         });    
                         total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                     }

                     while(rsbiaya_dokter_umum.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_umum.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_umum.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),
                             rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",rsbiaya_dokter_umum.getString("kode_paket"),rsbiaya_dokter_umum.getString("nm_perawatan")+"(dr Umum)",
                             "Operasi "+rsbiaya_dokter_umum.getString("status")+" dr Umum",rsbiaya_dokter_umum.getDouble("biaya_dokter_umum")
                         });    
                         total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Operasi : "+e);
                 } finally{
                     if(rsbiayaoperator1!=null){
                         rsbiayaoperator1.close();
                     }
                     if(psbiayaoperator1!=null){
                         psbiayaoperator1.close();
                     }
                     if(rsbiayaoperator2!=null){
                         rsbiayaoperator2.close();
                     }
                     if(psbiayaoperator2!=null){
                         psbiayaoperator2.close();
                     }
                     if(rsbiayaoperator3!=null){
                         rsbiayaoperator3.close();
                     }
                     if(psbiayaoperator3!=null){
                         psbiayaoperator3.close();
                     }
                     if(rsbiayadokter_anak!=null){
                         rsbiayadokter_anak.close();
                     }
                     if(psbiayadokter_anak!=null){
                         psbiayadokter_anak.close();
                     }
                     if(rsbiaya_dokter_umum!=null){
                         rsbiaya_dokter_umum.close();
                     }
                     if(psbiaya_dokter_umum!=null){
                         psbiaya_dokter_umum.close();
                     }
                     if(rsbiaya_dokter_pjanak!=null){
                         rsbiaya_dokter_pjanak.close();
                     }
                     if(psbiaya_dokter_pjanak!=null){
                         psbiaya_dokter_pjanak.close();
                     }
                     if(rsbiayadokter_anestesi!=null){
                         rsbiayadokter_anestesi.close();
                     }
                     if(psbiayadokter_anestesi!=null){
                         psbiayadokter_anestesi.close();
                     }
                 }   
            }
            
            if(chkLaborat.isSelected()==true){
                //periksa lab  
                psperiksa_lab=koneksi.prepareStatement(
                     "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_dokter) not in "+
                     " (select concat(bayar_periksa_lab.no_rawat,bayar_periksa_lab.kd_jenis_prw,bayar_periksa_lab.tgl_periksa,bayar_periksa_lab.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab on bayar_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab.setString(1,kddokter.getText());
                     psperiksa_lab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab.executeQuery();
                     
                     while(rsperiksa_lab.next()){                                
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" PJ",rsperiksa_lab.getDouble("tarif_tindakan_dokter")
                         });    
                         total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab!=null){
                         psperiksa_lab.close();
                     }
                 }

                 //detail periksa lab
                 psdetaillab=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_dokter>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.kd_dokter) not in "+
                     "(select concat(bayar_detail_periksa_lab.no_rawat,bayar_detail_periksa_lab.kd_jenis_prw,bayar_detail_periksa_lab.tgl_periksa,bayar_detail_periksa_lab.jam,bayar_detail_periksa_lab.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab on bayar_detail_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab.setString(1,kddokter.getText());
                     psdetaillab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" PJ Detail",rsdetaillab.getDouble("bagian_dokter"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Lab : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab!=null){
                         psdetaillab.close();
                     }
                 }

                 //periksa lab perujuk                         
                 psperiksa_lab2=koneksi.prepareStatement(
                     "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where  periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_lab_perujuk.no_rawat,bayar_periksa_lab_perujuk.kd_jenis_prw,bayar_periksa_lab_perujuk.tgl_periksa,bayar_periksa_lab_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab_perujuk on bayar_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab2.setString(1,kddokter.getText());
                     psperiksa_lab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab2.executeQuery();
                     
                     while(rsperiksa_lab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" Perujuk",rsperiksa_lab.getDouble("tarif_perujuk")
                         });        
                         total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab2!=null){
                         psperiksa_lab2.close();
                     }
                 }

                 psdetaillab2=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_perujuk>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.dokter_perujuk) not in "+
                     "(select concat(bayar_detail_periksa_lab_perujuk.no_rawat,bayar_detail_periksa_lab_perujuk.kd_jenis_prw,bayar_detail_periksa_lab_perujuk.tgl_periksa,bayar_detail_periksa_lab_perujuk.jam,bayar_detail_periksa_lab_perujuk.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab_perujuk on bayar_detail_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab2.setString(1,kddokter.getText());
                     psdetaillab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab2.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" Perujuk Detail",rsdetaillab.getDouble("bagian_perujuk"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Perujuk : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab2!=null){
                         psdetaillab2.close();
                     }
                 }
            }

            if(chkRadiologi.isSelected()==true){
                //periksa radiologi
                 psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 and"+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_dokter) not in "+
                     " (select concat(bayar_periksa_radiologi.no_rawat,bayar_periksa_radiologi.kd_jenis_prw,bayar_periksa_radiologi.tgl_periksa,bayar_periksa_radiologi.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi on bayar_periksa_radiologi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi.setString(1,kddokter.getText());
                     psperiksa_radiologi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" PJ",rsperiksa_radiologi.getDouble("tarif_tindakan_dokter")
                         });      
                         total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi!=null){
                         psperiksa_radiologi.close();
                     }
                 }

                 //periksa radiologi
                 psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_perujuk>0 and "+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_radiologi_perujuk.no_rawat,bayar_periksa_radiologi_perujuk.kd_jenis_prw,bayar_periksa_radiologi_perujuk.tgl_periksa,bayar_periksa_radiologi_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi_perujuk on bayar_periksa_radiologi_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi2.setString(1,kddokter.getText());
                     psperiksa_radiologi2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" Perujuk",rsperiksa_radiologi.getDouble("tarif_perujuk")
                         });     
                         total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi2!=null){
                         psperiksa_radiologi2.close();
                     }
                 }
            }              

            LCount.setText(Valid.SetAngka(total)); 
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    private void prosesCariPiutangBelumLunas(){
        try{         
            total=0;  
            if(chkRalan.isSelected()==true){
                 //rawat jalan     
                 psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_dr.no_rawat,rawat_jl_dr.kd_jenis_prw,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_dr.no_rawat,bayar_rawat_jl_dr.kd_jenis_prw,bayar_rawat_jl_dr.tgl_perawatan,bayar_rawat_jl_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_dr on bayar_rawat_jl_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_dr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_drpr.no_rawat,rawat_jl_drpr.kd_jenis_prw,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_drpr.no_rawat,bayar_rawat_jl_drpr.kd_jenis_prw,bayar_rawat_jl_drpr.tgl_perawatan,bayar_rawat_jl_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_drpr on bayar_rawat_jl_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_drpr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 try {
                     psrawatjalandr.setString(1,kddokter.getText());
                     psrawatjalandr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandr=psrawatjalandr.executeQuery();
                     
                     psrawatjalandrpr.setString(1,kddokter.getText());
                     psrawatjalandrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                     
                     while(rsrawatjalandr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandr.getString("tgl_perawatan"),rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),
                             rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",rsrawatjalandr.getString("kd_jenis_prw"),rsrawatjalandr.getString("nm_perawatan"),
                             "Rawat Jalan Dr",rsrawatjalandr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                     }  

                     while(rsrawatjalandrpr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandrpr.getString("tgl_perawatan"),rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),
                             rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",rsrawatjalandrpr.getString("kd_jenis_prw"),rsrawatjalandrpr.getString("nm_perawatan"),
                             "Rawat Jalan DrPr",rsrawatjalandrpr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi RJ : "+e);
                 } finally{
                     if(rsrawatjalandr!=null){
                        rsrawatjalandr.close(); 
                     }
                     if(psrawatjalandr!=null){
                        psrawatjalandr.close(); 
                     }
                     if(rsrawatjalandrpr!=null){
                        rsrawatjalandrpr.close(); 
                     }
                     if(psrawatjalandrpr!=null){
                        psrawatjalandrpr.close(); 
                     }
                 }
            }

            if(chkRanap.isSelected()==true){
                 //rawat inap    
                 psrawatinapdr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                     "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_dr.no_rawat,rawat_inap_dr.kd_jenis_prw,rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_dr.no_rawat,bayar_rawat_inap_dr.kd_jenis_prw,bayar_rawat_inap_dr.tgl_perawatan,bayar_rawat_inap_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_dr on bayar_rawat_inap_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_dr.tgl_perawatan like ?)")+
                     "order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 psrawatinapdrpr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                     "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_drpr.no_rawat,rawat_inap_drpr.kd_jenis_prw,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_drpr.no_rawat,bayar_rawat_inap_drpr.kd_jenis_prw,bayar_rawat_inap_drpr.tgl_perawatan,bayar_rawat_inap_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_drpr on bayar_rawat_inap_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_drpr.tgl_perawatan like ?)")+
                     "order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 try {                            
                     psrawatinapdr.setString(1,kddokter.getText());
                     psrawatinapdr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdr=psrawatinapdr.executeQuery();
                     
                     psrawatinapdrpr.setString(1,kddokter.getText());
                     psrawatinapdrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                     
                     while(rsrawatinapdr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdr.getString("tgl_perawatan"),rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),
                             rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",rsrawatinapdr.getString("kd_jenis_prw"),rsrawatinapdr.getString("nm_perawatan"),
                             "Rawat Inap Dr",rsrawatinapdr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                     }

                     while(rsrawatinapdrpr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdrpr.getString("tgl_perawatan"),rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),
                             rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",rsrawatinapdrpr.getString("kd_jenis_prw"),rsrawatinapdrpr.getString("nm_perawatan"),
                             "Rawat Inap DrPr",rsrawatinapdrpr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Ranap : "+e);
                 } finally{
                     if(rsrawatinapdr!=null){
                         rsrawatinapdr.close();
                     }
                     if(psrawatinapdr!=null){
                         psrawatinapdr.close();
                     }
                     if(rsrawatinapdrpr!=null){
                         rsrawatinapdrpr.close();
                     }
                     if(psrawatinapdrpr!=null){
                         psrawatinapdrpr.close();
                     }
                 }
            }               
            
            if(chkOperasi.isSelected()==true){
                 psbiayaoperator1=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.operator1=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator1) not in "+
                     "(select concat(bayar_operasi_operator1.no_rawat,bayar_operasi_operator1.kode_paket,bayar_operasi_operator1.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator1 on bayar_operasi_operator1.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator2=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.operator2=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator2) not in "+
                     "(select concat(bayar_operasi_operator2.no_rawat,bayar_operasi_operator2.kode_paket,bayar_operasi_operator2.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator2 on bayar_operasi_operator2.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator3=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.operator3=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator3) not in "+
                     "(select concat(bayar_operasi_operator3.no_rawat,bayar_operasi_operator3.kode_paket,bayar_operasi_operator3.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator3 on bayar_operasi_operator3.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.dokter_anak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anak) not in "+
                     "(select concat(bayar_operasi_dokter_anak.no_rawat,bayar_operasi_dokter_anak.kode_paket,bayar_operasi_dokter_anak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anak on bayar_operasi_dokter_anak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_umum=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.dokter_umum=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_umum) not in "+
                     "(select concat(bayar_operasi_dokter_umum.no_rawat,bayar_operasi_dokter_umum.kode_paket,bayar_operasi_dokter_umum.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_umum on bayar_operasi_dokter_umum.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_pjanak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.dokter_pjanak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_pjanak) not in "+
                     "(select concat(bayar_operasi_dokter_pjanak.no_rawat,bayar_operasi_dokter_pjanak.kode_paket,bayar_operasi_dokter_pjanak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_pjanak on bayar_operasi_dokter_pjanak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anestesi=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.dokter_anestesi=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anestesi) not in "+
                     "(select concat(bayar_operasi_dokter_anestesi.no_rawat,bayar_operasi_dokter_anestesi.kode_paket,bayar_operasi_dokter_anestesi.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anestesi on bayar_operasi_dokter_anestesi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 try {
                     psbiayaoperator1.setString(1,kddokter.getText());               
                     psbiayaoperator1.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator1.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator1=psbiayaoperator1.executeQuery();
                     
                     psbiayaoperator2.setString(1,kddokter.getText());               
                     psbiayaoperator2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator2.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator2=psbiayaoperator2.executeQuery();
                     
                     psbiayaoperator3.setString(1,kddokter.getText());  
                     psbiayaoperator3.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator3.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator3=psbiayaoperator3.executeQuery();
                     
                     psbiayadokter_anak.setString(1,kddokter.getText());  
                     psbiayadokter_anak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                     
                     psbiaya_dokter_umum.setString(1,kddokter.getText());  
                     psbiaya_dokter_umum.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_umum.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                     
                     psbiaya_dokter_pjanak.setString(1,kddokter.getText());  
                     psbiaya_dokter_pjanak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_pjanak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                     
                     psbiayadokter_anestesi.setString(1,kddokter.getText());                 
                     psbiayadokter_anestesi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anestesi.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                     
                     while(rsbiayaoperator1.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator1.getString("tgl_operasi").substring(0,10),rsbiayaoperator1.getString("tgl_operasi").substring(11,19),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),
                             rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",rsbiayaoperator1.getString("kode_paket"),rsbiayaoperator1.getString("nm_perawatan")+"(Operator 1)",
                             "Operasi "+rsbiayaoperator1.getString("status")+" Op1",rsbiayaoperator1.getDouble("biayaoperator1")
                         });      
                         total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                     }

                     while(rsbiayaoperator2.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator2.getString("tgl_operasi").substring(0,10),rsbiayaoperator2.getString("tgl_operasi").substring(11,19),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),
                             rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",rsbiayaoperator2.getString("kode_paket"),rsbiayaoperator2.getString("nm_perawatan")+"(Operator 2)",
                             "Operasi "+rsbiayaoperator2.getString("status")+" Op2",rsbiayaoperator2.getDouble("biayaoperator2")
                         });  
                         total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                     }

                     while(rsbiayaoperator3.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator3.getString("tgl_operasi").substring(0,10),rsbiayaoperator3.getString("tgl_operasi").substring(11,19),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),
                             rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",rsbiayaoperator3.getString("kode_paket"),rsbiayaoperator3.getString("nm_perawatan")+"(Operator 3)",
                             "Operasi "+rsbiayaoperator3.getString("status")+" Op3",rsbiayaoperator3.getDouble("biayaoperator3")
                         });       
                         total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                     }

                     while(rsbiayadokter_anak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anak.getString("tgl_operasi").substring(0,10),rsbiayadokter_anak.getString("tgl_operasi").substring(11,19),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),
                             rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",rsbiayadokter_anak.getString("kode_paket"),rsbiayadokter_anak.getString("nm_perawatan")+"(dr Anak)",
                             "Operasi "+rsbiayadokter_anak.getString("status")+" dr Anak",rsbiayadokter_anak.getDouble("biayadokter_anak")
                         });    
                         total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                     }

                     while(rsbiayadokter_anestesi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anestesi.getString("tgl_operasi").substring(0,10),rsbiayadokter_anestesi.getString("tgl_operasi").substring(11,19),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),
                             rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",rsbiayadokter_anestesi.getString("kode_paket"),rsbiayadokter_anestesi.getString("nm_perawatan")+"(dr Anestesi)",
                             "Operasi "+rsbiayadokter_anestesi.getString("status")+" dr Anastesi",rsbiayadokter_anestesi.getDouble("biayadokter_anestesi")
                         });      
                         total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                     }

                     while(rsbiaya_dokter_pjanak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),
                             rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",rsbiaya_dokter_pjanak.getString("kode_paket"),rsbiaya_dokter_pjanak.getString("nm_perawatan")+"(dr Pj Anak)",
                             "Operasi "+rsbiaya_dokter_pjanak.getString("status")+" dr PJ Anak",rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak")
                         });    
                         total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                     }

                     while(rsbiaya_dokter_umum.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_umum.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_umum.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),
                             rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",rsbiaya_dokter_umum.getString("kode_paket"),rsbiaya_dokter_umum.getString("nm_perawatan")+"(dr Umum)",
                             "Operasi "+rsbiaya_dokter_umum.getString("status")+" dr Umum",rsbiaya_dokter_umum.getDouble("biaya_dokter_umum")
                         });    
                         total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Operasi : "+e);
                 } finally{
                     if(rsbiayaoperator1!=null){
                         rsbiayaoperator1.close();
                     }
                     if(psbiayaoperator1!=null){
                         psbiayaoperator1.close();
                     }
                     if(rsbiayaoperator2!=null){
                         rsbiayaoperator2.close();
                     }
                     if(psbiayaoperator2!=null){
                         psbiayaoperator2.close();
                     }
                     if(rsbiayaoperator3!=null){
                         rsbiayaoperator3.close();
                     }
                     if(psbiayaoperator3!=null){
                         psbiayaoperator3.close();
                     }
                     if(rsbiayadokter_anak!=null){
                         rsbiayadokter_anak.close();
                     }
                     if(psbiayadokter_anak!=null){
                         psbiayadokter_anak.close();
                     }
                     if(rsbiaya_dokter_umum!=null){
                         rsbiaya_dokter_umum.close();
                     }
                     if(psbiaya_dokter_umum!=null){
                         psbiaya_dokter_umum.close();
                     }
                     if(rsbiaya_dokter_pjanak!=null){
                         rsbiaya_dokter_pjanak.close();
                     }
                     if(psbiaya_dokter_pjanak!=null){
                         psbiaya_dokter_pjanak.close();
                     }
                     if(rsbiayadokter_anestesi!=null){
                         rsbiayadokter_anestesi.close();
                     }
                     if(psbiayadokter_anestesi!=null){
                         psbiayadokter_anestesi.close();
                     }
                 }   
            }
            
            if(chkLaborat.isSelected()==true){
                //periksa lab  
                psperiksa_lab=koneksi.prepareStatement(
                     "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_dokter) not in "+
                     " (select concat(bayar_periksa_lab.no_rawat,bayar_periksa_lab.kd_jenis_prw,bayar_periksa_lab.tgl_periksa,bayar_periksa_lab.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab on bayar_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab.setString(1,kddokter.getText());
                     psperiksa_lab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab.executeQuery();
                     
                     while(rsperiksa_lab.next()){                                
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" PJ",rsperiksa_lab.getDouble("tarif_tindakan_dokter")
                         });    
                         total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab!=null){
                         psperiksa_lab.close();
                     }
                 }

                 //detail periksa lab
                 psdetaillab=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_dokter>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.kd_dokter) not in "+
                     "(select concat(bayar_detail_periksa_lab.no_rawat,bayar_detail_periksa_lab.kd_jenis_prw,bayar_detail_periksa_lab.tgl_periksa,bayar_detail_periksa_lab.jam,bayar_detail_periksa_lab.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab on bayar_detail_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab.setString(1,kddokter.getText());
                     psdetaillab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" PJ Detail",rsdetaillab.getDouble("bagian_dokter"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Lab : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab!=null){
                         psdetaillab.close();
                     }
                 }

                 //periksa lab perujuk                         
                 psperiksa_lab2=koneksi.prepareStatement(
                     "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_lab_perujuk.no_rawat,bayar_periksa_lab_perujuk.kd_jenis_prw,bayar_periksa_lab_perujuk.tgl_periksa,bayar_periksa_lab_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab_perujuk on bayar_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab2.setString(1,kddokter.getText());
                     psperiksa_lab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab2.executeQuery();
                     
                     while(rsperiksa_lab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" Perujuk",rsperiksa_lab.getDouble("tarif_perujuk")
                         });        
                         total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab2!=null){
                         psperiksa_lab2.close();
                     }
                 }

                 psdetaillab2=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_perujuk>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.dokter_perujuk) not in "+
                     "(select concat(bayar_detail_periksa_lab_perujuk.no_rawat,bayar_detail_periksa_lab_perujuk.kd_jenis_prw,bayar_detail_periksa_lab_perujuk.tgl_periksa,bayar_detail_periksa_lab_perujuk.jam,bayar_detail_periksa_lab_perujuk.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab_perujuk on bayar_detail_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab2.setString(1,kddokter.getText());
                     psdetaillab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab2.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" Perujuk Detail",rsdetaillab.getDouble("bagian_perujuk"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Perujuk : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab2!=null){
                         psdetaillab2.close();
                     }
                 }
            }

            if(chkRadiologi.isSelected()==true){
                //periksa radiologi
                 psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 and"+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_dokter) not in "+
                     " (select concat(bayar_periksa_radiologi.no_rawat,bayar_periksa_radiologi.kd_jenis_prw,bayar_periksa_radiologi.tgl_periksa,bayar_periksa_radiologi.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi on bayar_periksa_radiologi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi.setString(1,kddokter.getText());
                     psperiksa_radiologi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" PJ",rsperiksa_radiologi.getDouble("tarif_tindakan_dokter")
                         });      
                         total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi!=null){
                         psperiksa_radiologi.close();
                     }
                 }

                 //periksa radiologi
                 psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_perujuk>0 and "+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_radiologi_perujuk.no_rawat,bayar_periksa_radiologi_perujuk.kd_jenis_prw,bayar_periksa_radiologi_perujuk.tgl_periksa,bayar_periksa_radiologi_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi_perujuk on bayar_periksa_radiologi_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi2.setString(1,kddokter.getText());
                     psperiksa_radiologi2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" Perujuk",rsperiksa_radiologi.getDouble("tarif_perujuk")
                         });     
                         total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi2!=null){
                         psperiksa_radiologi2.close();
                     }
                 }
            }              

            LCount.setText(Valid.SetAngka(total));   
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    private void prosesCariPiutangSudahLunas(){
        try{         
            total=0;  
            if(chkRalan.isSelected()==true){
                 //rawat jalan     
                 psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and rawat_jl_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_dr.no_rawat,rawat_jl_dr.kd_jenis_prw,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_dr.no_rawat,bayar_rawat_jl_dr.kd_jenis_prw,bayar_rawat_jl_dr.tgl_perawatan,bayar_rawat_jl_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_dr on bayar_rawat_jl_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_dr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and rawat_jl_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_drpr.no_rawat,rawat_jl_drpr.kd_jenis_prw,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_drpr.no_rawat,bayar_rawat_jl_drpr.kd_jenis_prw,bayar_rawat_jl_drpr.tgl_perawatan,bayar_rawat_jl_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_drpr on bayar_rawat_jl_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_drpr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 try {
                     psrawatjalandr.setString(1,kddokter.getText());
                     psrawatjalandr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandr=psrawatjalandr.executeQuery();
                     
                     psrawatjalandrpr.setString(1,kddokter.getText());
                     psrawatjalandrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                     
                     while(rsrawatjalandr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandr.getString("tgl_perawatan"),rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),
                             rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",rsrawatjalandr.getString("kd_jenis_prw"),rsrawatjalandr.getString("nm_perawatan"),
                             "Rawat Jalan Dr",rsrawatjalandr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                     }  

                     while(rsrawatjalandrpr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandrpr.getString("tgl_perawatan"),rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),
                             rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",rsrawatjalandrpr.getString("kd_jenis_prw"),rsrawatjalandrpr.getString("nm_perawatan"),
                             "Rawat Jalan DrPr",rsrawatjalandrpr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi RJ : "+e);
                 } finally{
                     if(rsrawatjalandr!=null){
                        rsrawatjalandr.close(); 
                     }
                     if(psrawatjalandr!=null){
                        psrawatjalandr.close(); 
                     }
                     if(rsrawatjalandrpr!=null){
                        rsrawatjalandrpr.close(); 
                     }
                     if(psrawatjalandrpr!=null){
                        psrawatjalandrpr.close(); 
                     }
                 }
            }

            if(chkRanap.isSelected()==true){
                 //rawat inap    
                 psrawatinapdr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                     "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and rawat_inap_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_dr.no_rawat,rawat_inap_dr.kd_jenis_prw,rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_dr.no_rawat,bayar_rawat_inap_dr.kd_jenis_prw,bayar_rawat_inap_dr.tgl_perawatan,bayar_rawat_inap_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_dr on bayar_rawat_inap_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_dr.tgl_perawatan like ?)")+
                     "order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 psrawatinapdrpr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                     "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and rawat_inap_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_drpr.no_rawat,rawat_inap_drpr.kd_jenis_prw,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_drpr.no_rawat,bayar_rawat_inap_drpr.kd_jenis_prw,bayar_rawat_inap_drpr.tgl_perawatan,bayar_rawat_inap_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_drpr on bayar_rawat_inap_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_drpr.tgl_perawatan like ?)")+
                     "order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 try {                            
                     psrawatinapdr.setString(1,kddokter.getText());
                     psrawatinapdr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdr=psrawatinapdr.executeQuery();
                     
                     psrawatinapdrpr.setString(1,kddokter.getText());
                     psrawatinapdrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                     
                     while(rsrawatinapdr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdr.getString("tgl_perawatan"),rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),
                             rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",rsrawatinapdr.getString("kd_jenis_prw"),rsrawatinapdr.getString("nm_perawatan"),
                             "Rawat Inap Dr",rsrawatinapdr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                     }

                     while(rsrawatinapdrpr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdrpr.getString("tgl_perawatan"),rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),
                             rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",rsrawatinapdrpr.getString("kd_jenis_prw"),rsrawatinapdrpr.getString("nm_perawatan"),
                             "Rawat Inap DrPr",rsrawatinapdrpr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Ranap : "+e);
                 } finally{
                     if(rsrawatinapdr!=null){
                         rsrawatinapdr.close();
                     }
                     if(psrawatinapdr!=null){
                         psrawatinapdr.close();
                     }
                     if(rsrawatinapdrpr!=null){
                         rsrawatinapdrpr.close();
                     }
                     if(psrawatinapdrpr!=null){
                         psrawatinapdrpr.close();
                     }
                 }
            }               
            
            if(chkOperasi.isSelected()==true){
                 psbiayaoperator1=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.operator1=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator1) not in "+
                     "(select concat(bayar_operasi_operator1.no_rawat,bayar_operasi_operator1.kode_paket,bayar_operasi_operator1.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator1 on bayar_operasi_operator1.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator2=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.operator2=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator2) not in "+
                     "(select concat(bayar_operasi_operator2.no_rawat,bayar_operasi_operator2.kode_paket,bayar_operasi_operator2.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator2 on bayar_operasi_operator2.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator3=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.operator3=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator3) not in "+
                     "(select concat(bayar_operasi_operator3.no_rawat,bayar_operasi_operator3.kode_paket,bayar_operasi_operator3.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator3 on bayar_operasi_operator3.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.dokter_anak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anak) not in "+
                     "(select concat(bayar_operasi_dokter_anak.no_rawat,bayar_operasi_dokter_anak.kode_paket,bayar_operasi_dokter_anak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anak on bayar_operasi_dokter_anak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_umum=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.dokter_umum=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_umum) not in "+
                     "(select concat(bayar_operasi_dokter_umum.no_rawat,bayar_operasi_dokter_umum.kode_paket,bayar_operasi_dokter_umum.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_umum on bayar_operasi_dokter_umum.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_pjanak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.dokter_pjanak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_pjanak) not in "+
                     "(select concat(bayar_operasi_dokter_pjanak.no_rawat,bayar_operasi_dokter_pjanak.kode_paket,bayar_operasi_dokter_pjanak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_pjanak on bayar_operasi_dokter_pjanak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anestesi=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.dokter_anestesi=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anestesi) not in "+
                     "(select concat(bayar_operasi_dokter_anestesi.no_rawat,bayar_operasi_dokter_anestesi.kode_paket,bayar_operasi_dokter_anestesi.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anestesi on bayar_operasi_dokter_anestesi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 try {
                     psbiayaoperator1.setString(1,kddokter.getText());               
                     psbiayaoperator1.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator1.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator1=psbiayaoperator1.executeQuery();
                     
                     psbiayaoperator2.setString(1,kddokter.getText());               
                     psbiayaoperator2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator2.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator2=psbiayaoperator2.executeQuery();
                     
                     psbiayaoperator3.setString(1,kddokter.getText());  
                     psbiayaoperator3.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator3.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator3=psbiayaoperator3.executeQuery();
                     
                     psbiayadokter_anak.setString(1,kddokter.getText());  
                     psbiayadokter_anak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                     
                     psbiaya_dokter_umum.setString(1,kddokter.getText());  
                     psbiaya_dokter_umum.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_umum.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                     
                     psbiaya_dokter_pjanak.setString(1,kddokter.getText());  
                     psbiaya_dokter_pjanak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_pjanak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                     
                     psbiayadokter_anestesi.setString(1,kddokter.getText());                 
                     psbiayadokter_anestesi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anestesi.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                     
                     while(rsbiayaoperator1.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator1.getString("tgl_operasi").substring(0,10),rsbiayaoperator1.getString("tgl_operasi").substring(11,19),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),
                             rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",rsbiayaoperator1.getString("kode_paket"),rsbiayaoperator1.getString("nm_perawatan")+"(Operator 1)",
                             "Operasi "+rsbiayaoperator1.getString("status")+" Op1",rsbiayaoperator1.getDouble("biayaoperator1")
                         });      
                         total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                     }

                     while(rsbiayaoperator2.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator2.getString("tgl_operasi").substring(0,10),rsbiayaoperator2.getString("tgl_operasi").substring(11,19),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),
                             rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",rsbiayaoperator2.getString("kode_paket"),rsbiayaoperator2.getString("nm_perawatan")+"(Operator 2)",
                             "Operasi "+rsbiayaoperator2.getString("status")+" Op2",rsbiayaoperator2.getDouble("biayaoperator2")
                         });  
                         total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                     }

                     while(rsbiayaoperator3.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator3.getString("tgl_operasi").substring(0,10),rsbiayaoperator3.getString("tgl_operasi").substring(11,19),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),
                             rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",rsbiayaoperator3.getString("kode_paket"),rsbiayaoperator3.getString("nm_perawatan")+"(Operator 3)",
                             "Operasi "+rsbiayaoperator3.getString("status")+" Op3",rsbiayaoperator3.getDouble("biayaoperator3")
                         });       
                         total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                     }

                     while(rsbiayadokter_anak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anak.getString("tgl_operasi").substring(0,10),rsbiayadokter_anak.getString("tgl_operasi").substring(11,19),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),
                             rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",rsbiayadokter_anak.getString("kode_paket"),rsbiayadokter_anak.getString("nm_perawatan")+"(dr Anak)",
                             "Operasi "+rsbiayadokter_anak.getString("status")+" dr Anak",rsbiayadokter_anak.getDouble("biayadokter_anak")
                         });    
                         total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                     }

                     while(rsbiayadokter_anestesi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anestesi.getString("tgl_operasi").substring(0,10),rsbiayadokter_anestesi.getString("tgl_operasi").substring(11,19),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),
                             rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",rsbiayadokter_anestesi.getString("kode_paket"),rsbiayadokter_anestesi.getString("nm_perawatan")+"(dr Anestesi)",
                             "Operasi "+rsbiayadokter_anestesi.getString("status")+" dr Anastesi",rsbiayadokter_anestesi.getDouble("biayadokter_anestesi")
                         });      
                         total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                     }

                     while(rsbiaya_dokter_pjanak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),
                             rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",rsbiaya_dokter_pjanak.getString("kode_paket"),rsbiaya_dokter_pjanak.getString("nm_perawatan")+"(dr Pj Anak)",
                             "Operasi "+rsbiaya_dokter_pjanak.getString("status")+" dr PJ Anak",rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak")
                         });    
                         total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                     }

                     while(rsbiaya_dokter_umum.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_umum.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_umum.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),
                             rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",rsbiaya_dokter_umum.getString("kode_paket"),rsbiaya_dokter_umum.getString("nm_perawatan")+"(dr Umum)",
                             "Operasi "+rsbiaya_dokter_umum.getString("status")+" dr Umum",rsbiaya_dokter_umum.getDouble("biaya_dokter_umum")
                         });    
                         total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Operasi : "+e);
                 } finally{
                     if(rsbiayaoperator1!=null){
                         rsbiayaoperator1.close();
                     }
                     if(psbiayaoperator1!=null){
                         psbiayaoperator1.close();
                     }
                     if(rsbiayaoperator2!=null){
                         rsbiayaoperator2.close();
                     }
                     if(psbiayaoperator2!=null){
                         psbiayaoperator2.close();
                     }
                     if(rsbiayaoperator3!=null){
                         rsbiayaoperator3.close();
                     }
                     if(psbiayaoperator3!=null){
                         psbiayaoperator3.close();
                     }
                     if(rsbiayadokter_anak!=null){
                         rsbiayadokter_anak.close();
                     }
                     if(psbiayadokter_anak!=null){
                         psbiayadokter_anak.close();
                     }
                     if(rsbiaya_dokter_umum!=null){
                         rsbiaya_dokter_umum.close();
                     }
                     if(psbiaya_dokter_umum!=null){
                         psbiaya_dokter_umum.close();
                     }
                     if(rsbiaya_dokter_pjanak!=null){
                         rsbiaya_dokter_pjanak.close();
                     }
                     if(psbiaya_dokter_pjanak!=null){
                         psbiaya_dokter_pjanak.close();
                     }
                     if(rsbiayadokter_anestesi!=null){
                         rsbiayadokter_anestesi.close();
                     }
                     if(psbiayadokter_anestesi!=null){
                         psbiayadokter_anestesi.close();
                     }
                 }   
            }
            
            if(chkLaborat.isSelected()==true){
                //periksa lab  
                psperiksa_lab=koneksi.prepareStatement(
                     "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_dokter) not in "+
                     " (select concat(bayar_periksa_lab.no_rawat,bayar_periksa_lab.kd_jenis_prw,bayar_periksa_lab.tgl_periksa,bayar_periksa_lab.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab on bayar_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab.setString(1,kddokter.getText());
                     psperiksa_lab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab.executeQuery();
                     
                     while(rsperiksa_lab.next()){                                
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" PJ",rsperiksa_lab.getDouble("tarif_tindakan_dokter")
                         });    
                         total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab!=null){
                         psperiksa_lab.close();
                     }
                 }

                 //detail periksa lab
                 psdetaillab=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_dokter>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.kd_dokter) not in "+
                     "(select concat(bayar_detail_periksa_lab.no_rawat,bayar_detail_periksa_lab.kd_jenis_prw,bayar_detail_periksa_lab.tgl_periksa,bayar_detail_periksa_lab.jam,bayar_detail_periksa_lab.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab on bayar_detail_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab.setString(1,kddokter.getText());
                     psdetaillab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" PJ Detail",rsdetaillab.getDouble("bagian_dokter"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Lab : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab!=null){
                         psdetaillab.close();
                     }
                 }

                 //periksa lab perujuk                         
                 psperiksa_lab2=koneksi.prepareStatement(
                     "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_lab_perujuk.no_rawat,bayar_periksa_lab_perujuk.kd_jenis_prw,bayar_periksa_lab_perujuk.tgl_periksa,bayar_periksa_lab_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab_perujuk on bayar_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab2.setString(1,kddokter.getText());
                     psperiksa_lab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab2.executeQuery();
                     
                     while(rsperiksa_lab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" Perujuk",rsperiksa_lab.getDouble("tarif_perujuk")
                         });        
                         total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab2!=null){
                         psperiksa_lab2.close();
                     }
                 }

                 psdetaillab2=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_perujuk>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.dokter_perujuk) not in "+
                     "(select concat(bayar_detail_periksa_lab_perujuk.no_rawat,bayar_detail_periksa_lab_perujuk.kd_jenis_prw,bayar_detail_periksa_lab_perujuk.tgl_periksa,bayar_detail_periksa_lab_perujuk.jam,bayar_detail_periksa_lab_perujuk.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab_perujuk on bayar_detail_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab2.setString(1,kddokter.getText());
                     psdetaillab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab2.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" Perujuk Detail",rsdetaillab.getDouble("bagian_perujuk"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Perujuk : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab2!=null){
                         psdetaillab2.close();
                     }
                 }
            }

            if(chkRadiologi.isSelected()==true){
                //periksa radiologi
                 psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 and"+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_dokter) not in "+
                     " (select concat(bayar_periksa_radiologi.no_rawat,bayar_periksa_radiologi.kd_jenis_prw,bayar_periksa_radiologi.tgl_periksa,bayar_periksa_radiologi.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi on bayar_periksa_radiologi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi.setString(1,kddokter.getText());
                     psperiksa_radiologi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" PJ",rsperiksa_radiologi.getDouble("tarif_tindakan_dokter")
                         });      
                         total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi!=null){
                         psperiksa_radiologi.close();
                     }
                 }

                 //periksa radiologi
                 psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_perujuk>0 and "+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_radiologi_perujuk.no_rawat,bayar_periksa_radiologi_perujuk.kd_jenis_prw,bayar_periksa_radiologi_perujuk.tgl_periksa,bayar_periksa_radiologi_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi_perujuk on bayar_periksa_radiologi_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi2.setString(1,kddokter.getText());
                     psperiksa_radiologi2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" Perujuk",rsperiksa_radiologi.getDouble("tarif_perujuk")
                         });     
                         total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi2!=null){
                         psperiksa_radiologi2.close();
                     }
                 }
            }              

            LCount.setText(Valid.SetAngka(total));    
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    private void prosesCariSudahBayarNonPiutang(){
        try{         
            total=0;  
            if(chkRalan.isSelected()==true){
                 //rawat jalan     
                 psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and rawat_jl_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_dr.no_rawat,rawat_jl_dr.kd_jenis_prw,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_dr.no_rawat,bayar_rawat_jl_dr.kd_jenis_prw,bayar_rawat_jl_dr.tgl_perawatan,bayar_rawat_jl_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_dr on bayar_rawat_jl_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_dr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and rawat_jl_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_drpr.no_rawat,rawat_jl_drpr.kd_jenis_prw,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_drpr.no_rawat,bayar_rawat_jl_drpr.kd_jenis_prw,bayar_rawat_jl_drpr.tgl_perawatan,bayar_rawat_jl_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_drpr on bayar_rawat_jl_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_drpr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 try {
                     psrawatjalandr.setString(1,kddokter.getText());
                     psrawatjalandr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandr=psrawatjalandr.executeQuery();
                     
                     psrawatjalandrpr.setString(1,kddokter.getText());
                     psrawatjalandrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                     
                     while(rsrawatjalandr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandr.getString("tgl_perawatan"),rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),
                             rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",rsrawatjalandr.getString("kd_jenis_prw"),rsrawatjalandr.getString("nm_perawatan"),
                             "Rawat Jalan Dr",rsrawatjalandr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                     }  

                     while(rsrawatjalandrpr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandrpr.getString("tgl_perawatan"),rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),
                             rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",rsrawatjalandrpr.getString("kd_jenis_prw"),rsrawatjalandrpr.getString("nm_perawatan"),
                             "Rawat Jalan DrPr",rsrawatjalandrpr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi RJ : "+e);
                 } finally{
                     if(rsrawatjalandr!=null){
                        rsrawatjalandr.close(); 
                     }
                     if(psrawatjalandr!=null){
                        psrawatjalandr.close(); 
                     }
                     if(rsrawatjalandrpr!=null){
                        rsrawatjalandrpr.close(); 
                     }
                     if(psrawatjalandrpr!=null){
                        psrawatjalandrpr.close(); 
                     }
                 }
            }

            if(chkRanap.isSelected()==true){
                 //rawat inap    
                 psrawatinapdr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                     "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and rawat_inap_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_dr.no_rawat,rawat_inap_dr.kd_jenis_prw,rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_dr.no_rawat,bayar_rawat_inap_dr.kd_jenis_prw,bayar_rawat_inap_dr.tgl_perawatan,bayar_rawat_inap_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_dr on bayar_rawat_inap_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_dr.tgl_perawatan like ?)")+
                     "order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 psrawatinapdrpr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                     "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and rawat_inap_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_drpr.no_rawat,rawat_inap_drpr.kd_jenis_prw,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_drpr.no_rawat,bayar_rawat_inap_drpr.kd_jenis_prw,bayar_rawat_inap_drpr.tgl_perawatan,bayar_rawat_inap_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_drpr on bayar_rawat_inap_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_drpr.tgl_perawatan like ?)")+
                     "order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 try {                            
                     psrawatinapdr.setString(1,kddokter.getText());
                     psrawatinapdr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdr=psrawatinapdr.executeQuery();
                     
                     psrawatinapdrpr.setString(1,kddokter.getText());
                     psrawatinapdrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                     
                     while(rsrawatinapdr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdr.getString("tgl_perawatan"),rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),
                             rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",rsrawatinapdr.getString("kd_jenis_prw"),rsrawatinapdr.getString("nm_perawatan"),
                             "Rawat Inap Dr",rsrawatinapdr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                     }

                     while(rsrawatinapdrpr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdrpr.getString("tgl_perawatan"),rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),
                             rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",rsrawatinapdrpr.getString("kd_jenis_prw"),rsrawatinapdrpr.getString("nm_perawatan"),
                             "Rawat Inap DrPr",rsrawatinapdrpr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Ranap : "+e);
                 } finally{
                     if(rsrawatinapdr!=null){
                         rsrawatinapdr.close();
                     }
                     if(psrawatinapdr!=null){
                         psrawatinapdr.close();
                     }
                     if(rsrawatinapdrpr!=null){
                         rsrawatinapdrpr.close();
                     }
                     if(psrawatinapdrpr!=null){
                         psrawatinapdrpr.close();
                     }
                 }
            }               
            
            if(chkOperasi.isSelected()==true){
                 psbiayaoperator1=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.operator1=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator1) not in "+
                     "(select concat(bayar_operasi_operator1.no_rawat,bayar_operasi_operator1.kode_paket,bayar_operasi_operator1.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator1 on bayar_operasi_operator1.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator2=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.operator2=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator2) not in "+
                     "(select concat(bayar_operasi_operator2.no_rawat,bayar_operasi_operator2.kode_paket,bayar_operasi_operator2.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator2 on bayar_operasi_operator2.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator3=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.operator3=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator3) not in "+
                     "(select concat(bayar_operasi_operator3.no_rawat,bayar_operasi_operator3.kode_paket,bayar_operasi_operator3.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator3 on bayar_operasi_operator3.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.dokter_anak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anak) not in "+
                     "(select concat(bayar_operasi_dokter_anak.no_rawat,bayar_operasi_dokter_anak.kode_paket,bayar_operasi_dokter_anak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anak on bayar_operasi_dokter_anak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_umum=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.dokter_umum=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_umum) not in "+
                     "(select concat(bayar_operasi_dokter_umum.no_rawat,bayar_operasi_dokter_umum.kode_paket,bayar_operasi_dokter_umum.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_umum on bayar_operasi_dokter_umum.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_pjanak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.dokter_pjanak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_pjanak) not in "+
                     "(select concat(bayar_operasi_dokter_pjanak.no_rawat,bayar_operasi_dokter_pjanak.kode_paket,bayar_operasi_dokter_pjanak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_pjanak on bayar_operasi_dokter_pjanak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anestesi=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.dokter_anestesi=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anestesi) not in "+
                     "(select concat(bayar_operasi_dokter_anestesi.no_rawat,bayar_operasi_dokter_anestesi.kode_paket,bayar_operasi_dokter_anestesi.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anestesi on bayar_operasi_dokter_anestesi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 try {
                     psbiayaoperator1.setString(1,kddokter.getText());               
                     psbiayaoperator1.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator1.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator1=psbiayaoperator1.executeQuery();
                     
                     psbiayaoperator2.setString(1,kddokter.getText());               
                     psbiayaoperator2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator2.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator2=psbiayaoperator2.executeQuery();
                     
                     psbiayaoperator3.setString(1,kddokter.getText());  
                     psbiayaoperator3.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator3.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator3=psbiayaoperator3.executeQuery();
                     
                     psbiayadokter_anak.setString(1,kddokter.getText());  
                     psbiayadokter_anak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                     
                     psbiaya_dokter_umum.setString(1,kddokter.getText());  
                     psbiaya_dokter_umum.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_umum.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                     
                     psbiaya_dokter_pjanak.setString(1,kddokter.getText());  
                     psbiaya_dokter_pjanak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_pjanak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                     
                     psbiayadokter_anestesi.setString(1,kddokter.getText());                 
                     psbiayadokter_anestesi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anestesi.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                     
                     while(rsbiayaoperator1.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator1.getString("tgl_operasi").substring(0,10),rsbiayaoperator1.getString("tgl_operasi").substring(11,19),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),
                             rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",rsbiayaoperator1.getString("kode_paket"),rsbiayaoperator1.getString("nm_perawatan")+"(Operator 1)",
                             "Operasi "+rsbiayaoperator1.getString("status")+" Op1",rsbiayaoperator1.getDouble("biayaoperator1")
                         });      
                         total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                     }

                     while(rsbiayaoperator2.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator2.getString("tgl_operasi").substring(0,10),rsbiayaoperator2.getString("tgl_operasi").substring(11,19),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),
                             rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",rsbiayaoperator2.getString("kode_paket"),rsbiayaoperator2.getString("nm_perawatan")+"(Operator 2)",
                             "Operasi "+rsbiayaoperator2.getString("status")+" Op2",rsbiayaoperator2.getDouble("biayaoperator2")
                         });  
                         total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                     }

                     while(rsbiayaoperator3.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator3.getString("tgl_operasi").substring(0,10),rsbiayaoperator3.getString("tgl_operasi").substring(11,19),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),
                             rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",rsbiayaoperator3.getString("kode_paket"),rsbiayaoperator3.getString("nm_perawatan")+"(Operator 3)",
                             "Operasi "+rsbiayaoperator3.getString("status")+" Op3",rsbiayaoperator3.getDouble("biayaoperator3")
                         });       
                         total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                     }

                     while(rsbiayadokter_anak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anak.getString("tgl_operasi").substring(0,10),rsbiayadokter_anak.getString("tgl_operasi").substring(11,19),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),
                             rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",rsbiayadokter_anak.getString("kode_paket"),rsbiayadokter_anak.getString("nm_perawatan")+"(dr Anak)",
                             "Operasi "+rsbiayadokter_anak.getString("status")+" dr Anak",rsbiayadokter_anak.getDouble("biayadokter_anak")
                         });    
                         total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                     }

                     while(rsbiayadokter_anestesi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anestesi.getString("tgl_operasi").substring(0,10),rsbiayadokter_anestesi.getString("tgl_operasi").substring(11,19),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),
                             rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",rsbiayadokter_anestesi.getString("kode_paket"),rsbiayadokter_anestesi.getString("nm_perawatan")+"(dr Anestesi)",
                             "Operasi "+rsbiayadokter_anestesi.getString("status")+" dr Anastesi",rsbiayadokter_anestesi.getDouble("biayadokter_anestesi")
                         });      
                         total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                     }

                     while(rsbiaya_dokter_pjanak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),
                             rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",rsbiaya_dokter_pjanak.getString("kode_paket"),rsbiaya_dokter_pjanak.getString("nm_perawatan")+"(dr Pj Anak)",
                             "Operasi "+rsbiaya_dokter_pjanak.getString("status")+" dr PJ Anak",rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak")
                         });    
                         total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                     }

                     while(rsbiaya_dokter_umum.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_umum.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_umum.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),
                             rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",rsbiaya_dokter_umum.getString("kode_paket"),rsbiaya_dokter_umum.getString("nm_perawatan")+"(dr Umum)",
                             "Operasi "+rsbiaya_dokter_umum.getString("status")+" dr Umum",rsbiaya_dokter_umum.getDouble("biaya_dokter_umum")
                         });    
                         total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Operasi : "+e);
                 } finally{
                     if(rsbiayaoperator1!=null){
                         rsbiayaoperator1.close();
                     }
                     if(psbiayaoperator1!=null){
                         psbiayaoperator1.close();
                     }
                     if(rsbiayaoperator2!=null){
                         rsbiayaoperator2.close();
                     }
                     if(psbiayaoperator2!=null){
                         psbiayaoperator2.close();
                     }
                     if(rsbiayaoperator3!=null){
                         rsbiayaoperator3.close();
                     }
                     if(psbiayaoperator3!=null){
                         psbiayaoperator3.close();
                     }
                     if(rsbiayadokter_anak!=null){
                         rsbiayadokter_anak.close();
                     }
                     if(psbiayadokter_anak!=null){
                         psbiayadokter_anak.close();
                     }
                     if(rsbiaya_dokter_umum!=null){
                         rsbiaya_dokter_umum.close();
                     }
                     if(psbiaya_dokter_umum!=null){
                         psbiaya_dokter_umum.close();
                     }
                     if(rsbiaya_dokter_pjanak!=null){
                         rsbiaya_dokter_pjanak.close();
                     }
                     if(psbiaya_dokter_pjanak!=null){
                         psbiaya_dokter_pjanak.close();
                     }
                     if(rsbiayadokter_anestesi!=null){
                         rsbiayadokter_anestesi.close();
                     }
                     if(psbiayadokter_anestesi!=null){
                         psbiayadokter_anestesi.close();
                     }
                 }   
            }
            
            if(chkLaborat.isSelected()==true){
                //periksa lab  
                psperiksa_lab=koneksi.prepareStatement(
                     "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_dokter) not in "+
                     " (select concat(bayar_periksa_lab.no_rawat,bayar_periksa_lab.kd_jenis_prw,bayar_periksa_lab.tgl_periksa,bayar_periksa_lab.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab on bayar_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab.setString(1,kddokter.getText());
                     psperiksa_lab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab.executeQuery();
                     
                     while(rsperiksa_lab.next()){                                
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" PJ",rsperiksa_lab.getDouble("tarif_tindakan_dokter")
                         });    
                         total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab!=null){
                         psperiksa_lab.close();
                     }
                 }

                 //detail periksa lab
                 psdetaillab=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_dokter>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.kd_dokter) not in "+
                     "(select concat(bayar_detail_periksa_lab.no_rawat,bayar_detail_periksa_lab.kd_jenis_prw,bayar_detail_periksa_lab.tgl_periksa,bayar_detail_periksa_lab.jam,bayar_detail_periksa_lab.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab on bayar_detail_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab.setString(1,kddokter.getText());
                     psdetaillab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" PJ Detail",rsdetaillab.getDouble("bagian_dokter"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Lab : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab!=null){
                         psdetaillab.close();
                     }
                 }

                 //periksa lab perujuk                         
                 psperiksa_lab2=koneksi.prepareStatement(
                     "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_lab_perujuk.no_rawat,bayar_periksa_lab_perujuk.kd_jenis_prw,bayar_periksa_lab_perujuk.tgl_periksa,bayar_periksa_lab_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab_perujuk on bayar_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab2.setString(1,kddokter.getText());
                     psperiksa_lab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab2.executeQuery();
                     
                     while(rsperiksa_lab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" Perujuk",rsperiksa_lab.getDouble("tarif_perujuk")
                         });        
                         total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab2!=null){
                         psperiksa_lab2.close();
                     }
                 }

                 psdetaillab2=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_perujuk>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.dokter_perujuk) not in "+
                     "(select concat(bayar_detail_periksa_lab_perujuk.no_rawat,bayar_detail_periksa_lab_perujuk.kd_jenis_prw,bayar_detail_periksa_lab_perujuk.tgl_periksa,bayar_detail_periksa_lab_perujuk.jam,bayar_detail_periksa_lab_perujuk.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab_perujuk on bayar_detail_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab2.setString(1,kddokter.getText());
                     psdetaillab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab2.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" Perujuk Detail",rsdetaillab.getDouble("bagian_perujuk"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Perujuk : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab2!=null){
                         psdetaillab2.close();
                     }
                 }
            }

            if(chkRadiologi.isSelected()==true){
                //periksa radiologi
                 psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 and"+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_dokter) not in "+
                     " (select concat(bayar_periksa_radiologi.no_rawat,bayar_periksa_radiologi.kd_jenis_prw,bayar_periksa_radiologi.tgl_periksa,bayar_periksa_radiologi.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi on bayar_periksa_radiologi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi.setString(1,kddokter.getText());
                     psperiksa_radiologi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" PJ",rsperiksa_radiologi.getDouble("tarif_tindakan_dokter")
                         });      
                         total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi!=null){
                         psperiksa_radiologi.close();
                     }
                 }

                 //periksa radiologi
                 psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_perujuk>0 and "+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_radiologi_perujuk.no_rawat,bayar_periksa_radiologi_perujuk.kd_jenis_prw,bayar_periksa_radiologi_perujuk.tgl_periksa,bayar_periksa_radiologi_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi_perujuk on bayar_periksa_radiologi_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi2.setString(1,kddokter.getText());
                     psperiksa_radiologi2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" Perujuk",rsperiksa_radiologi.getDouble("tarif_perujuk")
                         });     
                         total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi2!=null){
                         psperiksa_radiologi2.close();
                     }
                 }
            }              

            LCount.setText(Valid.SetAngka(total));   
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    private void prosesCariBelumTerclosing() {
        try{         
            total=0;  
            if(chkRalan.isSelected()==true){
                 //rawat jalan     
                 psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and rawat_jl_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_dr.no_rawat,rawat_jl_dr.kd_jenis_prw,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_dr.no_rawat,bayar_rawat_jl_dr.kd_jenis_prw,bayar_rawat_jl_dr.tgl_perawatan,bayar_rawat_jl_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_dr on bayar_rawat_jl_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_dr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                     "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and rawat_jl_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_jl_drpr.no_rawat,rawat_jl_drpr.kd_jenis_prw,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_jl_drpr.no_rawat,bayar_rawat_jl_drpr.kd_jenis_prw,bayar_rawat_jl_drpr.tgl_perawatan,bayar_rawat_jl_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_jl_drpr on bayar_rawat_jl_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_jl_drpr.tgl_perawatan like ?)")+
                     "order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                 try {
                     psrawatjalandr.setString(1,kddokter.getText());
                     psrawatjalandr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandr=psrawatjalandr.executeQuery();
                     
                     psrawatjalandrpr.setString(1,kddokter.getText());
                     psrawatjalandrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatjalandrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatjalandrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                     
                     while(rsrawatjalandr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandr.getString("tgl_perawatan"),rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),
                             rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",rsrawatjalandr.getString("kd_jenis_prw"),rsrawatjalandr.getString("nm_perawatan"),
                             "Rawat Jalan Dr",rsrawatjalandr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                     }  

                     while(rsrawatjalandrpr.next()){
                         tabMode.addRow(new Object[]{
                             false,rsrawatjalandrpr.getString("tgl_perawatan"),rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),
                             rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",rsrawatjalandrpr.getString("kd_jenis_prw"),rsrawatjalandrpr.getString("nm_perawatan"),
                             "Rawat Jalan DrPr",rsrawatjalandrpr.getDouble("tarif_tindakandr")
                         });                   
                         total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi RJ : "+e);
                 } finally{
                     if(rsrawatjalandr!=null){
                        rsrawatjalandr.close(); 
                     }
                     if(psrawatjalandr!=null){
                        psrawatjalandr.close(); 
                     }
                     if(rsrawatjalandrpr!=null){
                        rsrawatjalandrpr.close(); 
                     }
                     if(psrawatjalandrpr!=null){
                        psrawatjalandrpr.close(); 
                     }
                 }
            }

            if(chkRanap.isSelected()==true){
                 //rawat inap    
                 psrawatinapdr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                     "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and rawat_inap_dr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_dr.no_rawat,rawat_inap_dr.kd_jenis_prw,rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_dr.no_rawat,bayar_rawat_inap_dr.kd_jenis_prw,bayar_rawat_inap_dr.tgl_perawatan,bayar_rawat_inap_dr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_dr on bayar_rawat_inap_dr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_dr.tgl_perawatan like ?)")+
                     "order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 psrawatinapdrpr=koneksi.prepareStatement(
                     "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                     "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                     "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                     "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and rawat_inap_drpr.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 and "+
                     "concat(rawat_inap_drpr.no_rawat,rawat_inap_drpr.kd_jenis_prw,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_dokter) not in "+
                     "(select concat(bayar_rawat_inap_drpr.no_rawat,bayar_rawat_inap_drpr.kd_jenis_prw,bayar_rawat_inap_drpr.tgl_perawatan,bayar_rawat_inap_drpr.jam_rawat,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_rawat_inap_drpr on bayar_rawat_inap_drpr.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_inap.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or rawat_inap_drpr.tgl_perawatan like ?)")+
                     "order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                 try {                            
                     psrawatinapdr.setString(1,kddokter.getText());
                     psrawatinapdr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdr=psrawatinapdr.executeQuery();
                     
                     psrawatinapdrpr.setString(1,kddokter.getText());
                     psrawatinapdrpr.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psrawatinapdrpr.setString(3,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(4,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(5,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(6,"%"+TCari.getText().trim()+"%");
                         psrawatinapdrpr.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                     
                     while(rsrawatinapdr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdr.getString("tgl_perawatan"),rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),
                             rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",rsrawatinapdr.getString("kd_jenis_prw"),rsrawatinapdr.getString("nm_perawatan"),
                             "Rawat Inap Dr",rsrawatinapdr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                     }

                     while(rsrawatinapdrpr.next()){ 
                         tabMode.addRow(new Object[]{
                             false,rsrawatinapdrpr.getString("tgl_perawatan"),rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),
                             rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",rsrawatinapdrpr.getString("kd_jenis_prw"),rsrawatinapdrpr.getString("nm_perawatan"),
                             "Rawat Inap DrPr",rsrawatinapdrpr.getDouble("tarif_tindakandr")
                         });          
                         total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Ranap : "+e);
                 } finally{
                     if(rsrawatinapdr!=null){
                         rsrawatinapdr.close();
                     }
                     if(psrawatinapdr!=null){
                         psrawatinapdr.close();
                     }
                     if(rsrawatinapdrpr!=null){
                         rsrawatinapdrpr.close();
                     }
                     if(psrawatinapdrpr!=null){
                         psrawatinapdrpr.close();
                     }
                 }
            }               
            
            if(chkOperasi.isSelected()==true){
                 psbiayaoperator1=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and operasi.operator1=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator1) not in "+
                     "(select concat(bayar_operasi_operator1.no_rawat,bayar_operasi_operator1.kode_paket,bayar_operasi_operator1.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator1 on bayar_operasi_operator1.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator2=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and operasi.operator2=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator2) not in "+
                     "(select concat(bayar_operasi_operator2.no_rawat,bayar_operasi_operator2.kode_paket,bayar_operasi_operator2.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator2 on bayar_operasi_operator2.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayaoperator3=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and operasi.operator3=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.operator3) not in "+
                     "(select concat(bayar_operasi_operator3.no_rawat,bayar_operasi_operator3.kode_paket,bayar_operasi_operator3.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_operator3 on bayar_operasi_operator3.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and operasi.dokter_anak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anak) not in "+
                     "(select concat(bayar_operasi_dokter_anak.no_rawat,bayar_operasi_dokter_anak.kode_paket,bayar_operasi_dokter_anak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anak on bayar_operasi_dokter_anak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_umum=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and operasi.dokter_umum=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_umum) not in "+
                     "(select concat(bayar_operasi_dokter_umum.no_rawat,bayar_operasi_dokter_umum.kode_paket,bayar_operasi_dokter_umum.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_umum on bayar_operasi_dokter_umum.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiaya_dokter_pjanak=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and operasi.dokter_pjanak=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_pjanak) not in "+
                     "(select concat(bayar_operasi_dokter_pjanak.no_rawat,bayar_operasi_dokter_pjanak.kode_paket,bayar_operasi_dokter_pjanak.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_pjanak on bayar_operasi_dokter_pjanak.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 psbiayadokter_anestesi=koneksi.prepareStatement(
                     "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,operasi.status,"+
                     "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                     "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and operasi.dokter_anestesi=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 and "+
                     "concat(operasi.no_rawat,operasi.kode_paket,operasi.tgl_operasi,operasi.dokter_anestesi) not in "+
                     "(select concat(bayar_operasi_dokter_anestesi.no_rawat,bayar_operasi_dokter_anestesi.kode_paket,bayar_operasi_dokter_anestesi.tgl_operasi,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_operasi_dokter_anestesi on bayar_operasi_dokter_anestesi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or paket_operasi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or operasi.tgl_operasi like ?)")+
                     "order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
                 try {
                     psbiayaoperator1.setString(1,kddokter.getText());               
                     psbiayaoperator1.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator1.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator1.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator1=psbiayaoperator1.executeQuery();
                     
                     psbiayaoperator2.setString(1,kddokter.getText());               
                     psbiayaoperator2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator2.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator2=psbiayaoperator2.executeQuery();
                     
                     psbiayaoperator3.setString(1,kddokter.getText());  
                     psbiayaoperator3.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayaoperator3.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayaoperator3.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayaoperator3=psbiayaoperator3.executeQuery();
                     
                     psbiayadokter_anak.setString(1,kddokter.getText());  
                     psbiayadokter_anak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                     
                     psbiaya_dokter_umum.setString(1,kddokter.getText());  
                     psbiaya_dokter_umum.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_umum.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_umum.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                     
                     psbiaya_dokter_pjanak.setString(1,kddokter.getText());  
                     psbiaya_dokter_pjanak.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiaya_dokter_pjanak.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiaya_dokter_pjanak.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                     
                     psbiayadokter_anestesi.setString(1,kddokter.getText());                 
                     psbiayadokter_anestesi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psbiayadokter_anestesi.setString(3,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(4,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(5,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(6,"%"+TCari.getText().trim()+"%");
                         psbiayadokter_anestesi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                     
                     while(rsbiayaoperator1.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator1.getString("tgl_operasi").substring(0,10),rsbiayaoperator1.getString("tgl_operasi").substring(11,19),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),
                             rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",rsbiayaoperator1.getString("kode_paket"),rsbiayaoperator1.getString("nm_perawatan")+"(Operator 1)",
                             "Operasi "+rsbiayaoperator1.getString("status")+" Op1",rsbiayaoperator1.getDouble("biayaoperator1")
                         });      
                         total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                     }

                     while(rsbiayaoperator2.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator2.getString("tgl_operasi").substring(0,10),rsbiayaoperator2.getString("tgl_operasi").substring(11,19),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),
                             rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",rsbiayaoperator2.getString("kode_paket"),rsbiayaoperator2.getString("nm_perawatan")+"(Operator 2)",
                             "Operasi "+rsbiayaoperator2.getString("status")+" Op2",rsbiayaoperator2.getDouble("biayaoperator2")
                         });  
                         total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                     }

                     while(rsbiayaoperator3.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayaoperator3.getString("tgl_operasi").substring(0,10),rsbiayaoperator3.getString("tgl_operasi").substring(11,19),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),
                             rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",rsbiayaoperator3.getString("kode_paket"),rsbiayaoperator3.getString("nm_perawatan")+"(Operator 3)",
                             "Operasi "+rsbiayaoperator3.getString("status")+" Op3",rsbiayaoperator3.getDouble("biayaoperator3")
                         });       
                         total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                     }

                     while(rsbiayadokter_anak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anak.getString("tgl_operasi").substring(0,10),rsbiayadokter_anak.getString("tgl_operasi").substring(11,19),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),
                             rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",rsbiayadokter_anak.getString("kode_paket"),rsbiayadokter_anak.getString("nm_perawatan")+"(dr Anak)",
                             "Operasi "+rsbiayadokter_anak.getString("status")+" dr Anak",rsbiayadokter_anak.getDouble("biayadokter_anak")
                         });    
                         total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                     }

                     while(rsbiayadokter_anestesi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiayadokter_anestesi.getString("tgl_operasi").substring(0,10),rsbiayadokter_anestesi.getString("tgl_operasi").substring(11,19),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),
                             rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",rsbiayadokter_anestesi.getString("kode_paket"),rsbiayadokter_anestesi.getString("nm_perawatan")+"(dr Anestesi)",
                             "Operasi "+rsbiayadokter_anestesi.getString("status")+" dr Anastesi",rsbiayadokter_anestesi.getDouble("biayadokter_anestesi")
                         });      
                         total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                     }

                     while(rsbiaya_dokter_pjanak.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_pjanak.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),
                             rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",rsbiaya_dokter_pjanak.getString("kode_paket"),rsbiaya_dokter_pjanak.getString("nm_perawatan")+"(dr Pj Anak)",
                             "Operasi "+rsbiaya_dokter_pjanak.getString("status")+" dr PJ Anak",rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak")
                         });    
                         total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                     }

                     while(rsbiaya_dokter_umum.next()){
                         tabMode.addRow(new Object[]{
                             false,rsbiaya_dokter_umum.getString("tgl_operasi").substring(0,10),rsbiaya_dokter_umum.getString("tgl_operasi").substring(11,19),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),
                             rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",rsbiaya_dokter_umum.getString("kode_paket"),rsbiaya_dokter_umum.getString("nm_perawatan")+"(dr Umum)",
                             "Operasi "+rsbiaya_dokter_umum.getString("status")+" dr Umum",rsbiaya_dokter_umum.getDouble("biaya_dokter_umum")
                         });    
                         total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Operasi : "+e);
                 } finally{
                     if(rsbiayaoperator1!=null){
                         rsbiayaoperator1.close();
                     }
                     if(psbiayaoperator1!=null){
                         psbiayaoperator1.close();
                     }
                     if(rsbiayaoperator2!=null){
                         rsbiayaoperator2.close();
                     }
                     if(psbiayaoperator2!=null){
                         psbiayaoperator2.close();
                     }
                     if(rsbiayaoperator3!=null){
                         rsbiayaoperator3.close();
                     }
                     if(psbiayaoperator3!=null){
                         psbiayaoperator3.close();
                     }
                     if(rsbiayadokter_anak!=null){
                         rsbiayadokter_anak.close();
                     }
                     if(psbiayadokter_anak!=null){
                         psbiayadokter_anak.close();
                     }
                     if(rsbiaya_dokter_umum!=null){
                         rsbiaya_dokter_umum.close();
                     }
                     if(psbiaya_dokter_umum!=null){
                         psbiaya_dokter_umum.close();
                     }
                     if(rsbiaya_dokter_pjanak!=null){
                         rsbiaya_dokter_pjanak.close();
                     }
                     if(psbiaya_dokter_pjanak!=null){
                         psbiaya_dokter_pjanak.close();
                     }
                     if(rsbiayadokter_anestesi!=null){
                         rsbiayadokter_anestesi.close();
                     }
                     if(psbiayadokter_anestesi!=null){
                         psbiayadokter_anestesi.close();
                     }
                 }   
            }
            
            if(chkLaborat.isSelected()==true){
                //periksa lab  
                psperiksa_lab=koneksi.prepareStatement(
                     "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where reg_periksa.status_bayar='Belum Bayar' and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_dokter) not in "+
                     " (select concat(bayar_periksa_lab.no_rawat,bayar_periksa_lab.kd_jenis_prw,bayar_periksa_lab.tgl_periksa,bayar_periksa_lab.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab on bayar_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab.setString(1,kddokter.getText());
                     psperiksa_lab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab.executeQuery();
                     
                     while(rsperiksa_lab.next()){                                
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" PJ",rsperiksa_lab.getDouble("tarif_tindakan_dokter")
                         });    
                         total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab!=null){
                         psperiksa_lab.close();
                     }
                 }

                 //detail periksa lab
                 psdetaillab=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_dokter>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.kd_dokter) not in "+
                     "(select concat(bayar_detail_periksa_lab.no_rawat,bayar_detail_periksa_lab.kd_jenis_prw,bayar_detail_periksa_lab.tgl_periksa,bayar_detail_periksa_lab.jam,bayar_detail_periksa_lab.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab on bayar_detail_periksa_lab.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab.setString(1,kddokter.getText());
                     psdetaillab.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" PJ Detail",rsdetaillab.getDouble("bagian_dokter"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Lab : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab!=null){
                         psdetaillab.close();
                     }
                 }

                 //periksa lab perujuk                         
                 psperiksa_lab2=koneksi.prepareStatement(
                     "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.status,"+
                     "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where reg_periksa.status_bayar='Belum Bayar' and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 and "+
                     " concat(periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_lab_perujuk.no_rawat,bayar_periksa_lab_perujuk.kd_jenis_prw,bayar_periksa_lab_perujuk.tgl_periksa,bayar_periksa_lab_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_lab_perujuk on bayar_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or  jns_perawatan_lab.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                 try {
                     psperiksa_lab2.setString(1,kddokter.getText());
                     psperiksa_lab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_lab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_lab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_lab=psperiksa_lab2.executeQuery();
                     
                     while(rsperiksa_lab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_lab.getString("tgl_periksa"),rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),
                             rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",rsperiksa_lab.getString("kd_jenis_prw"),rsperiksa_lab.getString("nm_perawatan"),
                             "Laborat "+rsperiksa_lab.getString("status")+" Perujuk",rsperiksa_lab.getDouble("tarif_perujuk")
                         });        
                         total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Lab : "+e);
                 } finally{
                     if(rsperiksa_lab!=null){
                         rsperiksa_lab.close();
                     }
                     if(psperiksa_lab2!=null){
                         psperiksa_lab2.close();
                     }
                 }

                 psdetaillab2=koneksi.prepareStatement(
                     "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,periksa_lab.status,detail_periksa_lab.id_template,"+
                     "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                     "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                     "from detail_periksa_lab inner join periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                     "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                     "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                     "and periksa_lab.jam=detail_periksa_lab.jam "+
                     "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                     "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     "where reg_periksa.status_bayar='Belum Bayar' and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.bagian_perujuk>0 and "+
                     "concat(detail_periksa_lab.no_rawat,detail_periksa_lab.kd_jenis_prw,detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,detail_periksa_lab.id_template,periksa_lab.dokter_perujuk) not in "+
                     "(select concat(bayar_detail_periksa_lab_perujuk.no_rawat,bayar_detail_periksa_lab_perujuk.kd_jenis_prw,bayar_detail_periksa_lab_perujuk.tgl_periksa,bayar_detail_periksa_lab_perujuk.jam,bayar_detail_periksa_lab_perujuk.id_template,bayar_jm_dokter.kd_dokter) "+
                     "from bayar_jm_dokter inner join bayar_detail_periksa_lab_perujuk on bayar_detail_periksa_lab_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or template_laboratorium.Pemeriksaan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_lab.tgl_periksa like ?)")+
                     "order by periksa_lab.tgl_periksa,periksa_lab.jam");
                 try {
                     psdetaillab2.setString(1,kddokter.getText());
                     psdetaillab2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psdetaillab2.setString(3,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(4,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(5,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(6,"%"+TCari.getText().trim()+"%");
                         psdetaillab2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsdetaillab=psdetaillab2.executeQuery();
                     
                     while(rsdetaillab.next()){
                         tabMode.addRow(new Object[]{
                             false,rsdetaillab.getString("tgl_periksa"),rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                             rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",rsdetaillab.getString("kd_jenis_prw"),rsdetaillab.getString("Pemeriksaan"),
                             "Laborat "+rsdetaillab.getString("status")+" Perujuk Detail",rsdetaillab.getDouble("bagian_perujuk"),rsdetaillab.getString("id_template")
                         });    
                         total=total+rsdetaillab.getDouble("bagian_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Detail Perujuk : "+e);
                 } finally{
                     if(rsdetaillab!=null){
                         rsdetaillab.close();
                     }
                     if(psdetaillab2!=null){
                         psdetaillab2.close();
                     }
                 }
            }

            if(chkRadiologi.isSelected()==true){
                //periksa radiologi
                 psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where reg_periksa.status_bayar='Belum Bayar' and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 and"+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_dokter) not in "+
                     " (select concat(bayar_periksa_radiologi.no_rawat,bayar_periksa_radiologi.kd_jenis_prw,bayar_periksa_radiologi.tgl_periksa,bayar_periksa_radiologi.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi on bayar_periksa_radiologi.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi.setString(1,kddokter.getText());
                     psperiksa_radiologi.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" PJ",rsperiksa_radiologi.getDouble("tarif_tindakan_dokter")
                         });      
                         total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi!=null){
                         psperiksa_radiologi.close();
                     }
                 }

                 //periksa radiologi
                 psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.status,"+
                     "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                     " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                     " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                     " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                     " where reg_periksa.status_bayar='Belum Bayar' and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_perujuk>0 and "+
                     " concat(periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.dokter_perujuk) not in "+
                     " (select concat(bayar_periksa_radiologi_perujuk.no_rawat,bayar_periksa_radiologi_perujuk.kd_jenis_prw,bayar_periksa_radiologi_perujuk.tgl_periksa,bayar_periksa_radiologi_perujuk.jam,bayar_jm_dokter.kd_dokter) "+
                     " from bayar_jm_dokter inner join bayar_periksa_radiologi_perujuk on bayar_periksa_radiologi_perujuk.no_bayar=bayar_jm_dokter.no_bayar) "+(TCari.getText().trim().equals("")?"":
                     " and (pasien.nm_pasien like ? or jns_perawatan_radiologi.nm_perawatan like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or periksa_radiologi.tgl_periksa like ?)")+
                     "order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                 try {
                     psperiksa_radiologi2.setString(1,kddokter.getText());
                     psperiksa_radiologi2.setString(2,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                     if(!TCari.getText().trim().equals("")){
                         psperiksa_radiologi2.setString(3,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(4,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(5,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(6,"%"+TCari.getText().trim()+"%");
                         psperiksa_radiologi2.setString(7,"%"+TCari.getText().trim()+"%");
                     }
                     rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                     
                     while(rsperiksa_radiologi.next()){
                         tabMode.addRow(new Object[]{
                             false,rsperiksa_radiologi.getString("tgl_periksa"),rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),
                             rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",rsperiksa_radiologi.getString("kd_jenis_prw"),rsperiksa_radiologi.getString("nm_perawatan"),
                             "Radiologi "+rsperiksa_radiologi.getString("status")+" Perujuk",rsperiksa_radiologi.getDouble("tarif_perujuk")
                         });     
                         total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                     }
                 } catch (Exception e) {
                     System.out.println("Notifikasi Perujuk Radiologi : "+e);
                 } finally{
                     if(rsperiksa_radiologi!=null){
                         rsperiksa_radiologi.close();
                     }
                     if(psperiksa_radiologi2!=null){
                         psperiksa_radiologi2.close();
                     }
                 }
            }              

            LCount.setText(Valid.SetAngka(total));    
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }
}

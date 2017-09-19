package simrskhanza;
import laporan.DlgDiagnosaPenyakit;
import keuangan.DlgBilingRalan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgPenjualan;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgLhtPiutang;
import keuangan.DlgRBObatPoli;
import keuangan.DlgRBJmDokter;
import keuangan.DlgRBJmParamedis;
import keuangan.DlgRBTindakanPoli;
import keuangan.DlgRHJmDokter;
import keuangan.DlgRHJmParamedis;

/**
 *
 * @author dosen
 */
public final class DlgKasirRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabModekasir;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psotomatis,psotomatis2,pskasir,pscaripiutang;
    private ResultSet rskasir;
    private final Properties prop = new Properties();
    private Date cal=new Date();
    private String bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1"),nonota="",
            sqlpsotomatis2="insert into rawat_jl_dr values (?,?,?,?,?,?,?,?,?,?,?)",
            sqlpsotomatis=
                "select jns_perawatan.kd_jenis_prw,jns_perawatan.material,jns_perawatan.bhp,"+
                "jns_perawatan.tarif_tindakandr,jns_perawatan.total_byrdr,jns_perawatan.kso,jns_perawatan.menejemen from set_otomatis_tindakan_ralan "+
                "inner join jns_perawatan on set_otomatis_tindakan_ralan.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                "where set_otomatis_tindakan_ralan.kd_dokter=? and set_otomatis_tindakan_ralan.kd_pj=?",
            namadokter="",namapoli="";
    public  DlgBilingRalan billing=new DlgBilingRalan(null,false);
    private int i=0,pilihan=0,sudah=0;
    public DlgKamarInap kamarinap=new DlgKamarInap(null,false);

    /** Creates new form DlgReg
     * @param parent
     * @param modal */
    public DlgKasirRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] rowkasir={"Kd.Dokter","Dokter Dituju","Nomer RM","Pasien","Poliklinik","Penanggung Jawab","Alamat P.J.","Hubungan P.J.",
                           "Biaya Regristrasi","Jenis Bayar","Status","No.Rawat","Tanggal","Jam","No.Reg"};
        tabModekasir=new DefaultTableModel(null,rowkasir){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKasirRalan.setModel(tabModekasir);

        tbKasirRalan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbKasirRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbKasirRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(140);
            }else if(i==5){
                column.setPreferredWidth(140);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(105);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(47);
            }
        }
        tbKasirRalan.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        CrPoli.setDocument(new batasInput((byte)100).getKata(CrPoli));
        TotalObat.setDocument(new batasInput((byte)20).getOnlyAngka(TotalObat));
        CrPtg.setDocument(new batasInput((byte)100).getKata(CrPtg)); 
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampilkasir();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampilkasir();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampilkasir();}
            });
        }         
         
        billing.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgKasirRalan")){
                    if(billing.dokter.getTable().getSelectedRow()!= -1){
                        if(pilihan==1){
                            kddokter.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),0).toString());
                            TDokter.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),1).toString());
                            kddokter.requestFocus();
                        }else if(pilihan==2){
                            CrPtg.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),1).toString());
                            tampilkasir();
                            CrPtg.requestFocus();
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
        
        
        billing.poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgKasirRalan")){
                    if(billing.poli.getTable().getSelectedRow()!= -1){
                        if(pilihan==1){
                            kdpoli.setText(billing.poli.getTable().getValueAt(billing.poli.getTable().getSelectedRow(),0).toString());
                            nmpoli.setText(billing.poli.getTable().getValueAt(billing.poli.getTable().getSelectedRow(),1).toString());
                            kdpoli.requestFocus();
                        }else if(pilihan==2){
                            CrPoli.setText(billing.poli.getTable().getValueAt(billing.poli.getTable().getSelectedRow(),1).toString());
                            CrPoli.requestFocus();                            
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
        
        billing.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                tampilkasir();
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
        
        billing.penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgKasirRalan")){
                    if(billing.penjab.getTable().getSelectedRow()!= -1){
                        kdpenjab.setText(billing.penjab.getTable().getValueAt(billing.penjab.getTable().getSelectedRow(),1).toString());
                        nmpenjab.setText(billing.penjab.getTable().getValueAt(billing.penjab.getTable().getSelectedRow(),2).toString());
                    } 
                    kdpenjab.requestFocus();
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
        
        billing.penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgKasirRalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        billing.penjab.dispose();
                    }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnKamarInap = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnNoResep = new javax.swing.JMenuItem();
        MnObatLangsung = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        MnDataRalan = new javax.swing.JMenuItem();
        MnDataPemberianObat = new javax.swing.JMenuItem();
        MnPoli = new javax.swing.JMenuItem();
        MnDokter = new javax.swing.JMenuItem();
        MnPenjab = new javax.swing.JMenuItem();
        MnPenjualan = new javax.swing.JMenuItem();
        MnDiagnosa = new javax.swing.JMenuItem();
        MnRekap = new javax.swing.JMenu();
        MnRekapHarianDokter = new javax.swing.JMenuItem();
        MnRekapHarianParamedis = new javax.swing.JMenuItem();
        MnRekapBulananDokter = new javax.swing.JMenuItem();
        MnRekapBulananParamedis = new javax.swing.JMenuItem();
        MnRekapHarianPoli = new javax.swing.JMenuItem();
        MnRekapHarianObat = new javax.swing.JMenuItem();
        MnStatus = new javax.swing.JMenu();
        ppBerkas = new javax.swing.JMenuItem();
        MnSudah = new javax.swing.JMenuItem();
        MnBelum = new javax.swing.JMenuItem();
        MnBayar = new javax.swing.JMenuItem();
        MnBatal = new javax.swing.JMenuItem();
        MnDirujuk = new javax.swing.JMenuItem();
        MnDIrawat = new javax.swing.JMenuItem();
        MnMeninggal = new javax.swing.JMenuItem();
        MnHapusData = new javax.swing.JMenu();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnHapusBilling = new javax.swing.JMenuItem();
        MnHapusDeposit = new javax.swing.JMenuItem();
        MnHapusDiet = new javax.swing.JMenuItem();
        MnHapusDiagnosa = new javax.swing.JMenuItem();
        MnHapusDpjp = new javax.swing.JMenuItem();
        MnHapusHemodialisa = new javax.swing.JMenuItem();
        MnHapusKamarInap = new javax.swing.JMenuItem();
        MnHapusPotongan = new javax.swing.JMenuItem();
        MnHapusPiutang = new javax.swing.JMenuItem();
        MnHapusProsedur = new javax.swing.JMenuItem();
        MnHapusRanapGabung = new javax.swing.JMenuItem();
        MnHapusRujukKeluar = new javax.swing.JMenuItem();
        MnHapusRujukMasuk = new javax.swing.JMenuItem();
        MnHapusTambahan = new javax.swing.JMenuItem();
        MnTindakan = new javax.swing.JMenu();
        MnHapusTindakanRanapDokter = new javax.swing.JMenuItem();
        MnHapusTindakanRanapDokterParamedis = new javax.swing.JMenuItem();
        MnHapusTindakanRanapParamedis = new javax.swing.JMenuItem();
        MnHapusTindakanRalanDokter = new javax.swing.JMenuItem();
        MnHapusTindakanRalanDokterParamedis = new javax.swing.JMenuItem();
        MnHapusTindakanRalanParamedis = new javax.swing.JMenuItem();
        MnPemeriksaan = new javax.swing.JMenu();
        MnHapusPemeriksaanRalan = new javax.swing.JMenuItem();
        MnHapusPemeriksaanRanap = new javax.swing.JMenuItem();
        MnHapusLab = new javax.swing.JMenuItem();
        MnHapusRadiologi = new javax.swing.JMenuItem();
        MnObat = new javax.swing.JMenu();
        MnHapusAturanPkaiObat = new javax.swing.JMenuItem();
        MnHapusObat = new javax.swing.JMenuItem();
        MnHapusResepObat = new javax.swing.JMenuItem();
        MnHapusResepPulang = new javax.swing.JMenuItem();
        MnHapusReturObat = new javax.swing.JMenuItem();
        MnHapusStokObatRanap = new javax.swing.JMenuItem();
        MnHapusSemua = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        MnRujuk = new javax.swing.JMenuItem();
        ppCatatanPasien = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        WindowObatBhp = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        TotalObat = new widget.TextBox();
        jLabel3 = new widget.Label();
        BtnCloseIn = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        WindowGantiDokter = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        BtnCloseIn1 = new widget.Button();
        BtnSimpan1 = new widget.Button();
        jLabel13 = new widget.Label();
        kddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        btnCariDokter = new widget.Button();
        Kd2 = new widget.TextBox();
        TKdPny = new widget.TextBox();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        WindowGantiPoli = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel18 = new widget.Label();
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        btnCariPoli = new widget.Button();
        WindowCaraBayar = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        BtnCloseIn5 = new widget.Button();
        BtnSimpan5 = new widget.Button();
        jLabel19 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        btnBayar = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKasirRalan = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrPtg = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        panelGlass8 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        PanelInput = new javax.swing.JPanel();

        jPopupMenu1.setForeground(new java.awt.Color(60, 80, 50));
        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 255));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(new java.awt.Color(60, 80, 50));
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawatJalan);

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(60, 80, 50));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat);

        MnKamarInap.setBackground(new java.awt.Color(255, 255, 255));
        MnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap.setForeground(new java.awt.Color(60, 80, 50));
        MnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap.setText("Kamar Inap");
        MnKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKamarInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKamarInap.setName("MnKamarInap"); // NOI18N
        MnKamarInap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKamarInap);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(new java.awt.Color(60, 80, 50));
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Lab");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPeriksaLab);

        MnPeriksaRadiologi.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi.setForeground(new java.awt.Color(60, 80, 50));
        MnPeriksaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi.setText("Periksa Radiologi");
        MnPeriksaRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi.setName("MnPeriksaRadiologi"); // NOI18N
        MnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPeriksaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPeriksaRadiologi);

        MnNoResep.setBackground(new java.awt.Color(255, 255, 255));
        MnNoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep.setForeground(new java.awt.Color(60, 80, 50));
        MnNoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep.setText("Input No.Resep");
        MnNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep.setName("MnNoResep"); // NOI18N
        MnNoResep.setPreferredSize(new java.awt.Dimension(220, 26));
        MnNoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResepActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnNoResep);

        MnObatLangsung.setBackground(new java.awt.Color(255, 255, 255));
        MnObatLangsung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatLangsung.setForeground(new java.awt.Color(60, 80, 50));
        MnObatLangsung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatLangsung.setText("Total Tagihan Obat");
        MnObatLangsung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObatLangsung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObatLangsung.setName("MnObatLangsung"); // NOI18N
        MnObatLangsung.setPreferredSize(new java.awt.Dimension(220, 26));
        MnObatLangsung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnObatLangsungActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnObatLangsung);

        MnOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(new java.awt.Color(60, 80, 50));
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi/VK");
        MnOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnOperasi);

        MnBilling.setBackground(new java.awt.Color(255, 255, 255));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(new java.awt.Color(60, 80, 50));
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing/Pembayaran Pasien");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBilling);

        MnDataRalan.setBackground(new java.awt.Color(255, 255, 255));
        MnDataRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataRalan.setForeground(new java.awt.Color(60, 80, 50));
        MnDataRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataRalan.setText("Data Tindakan Rawat Jalan");
        MnDataRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataRalan.setName("MnDataRalan"); // NOI18N
        MnDataRalan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDataRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataRalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDataRalan);

        MnDataPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnDataPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataPemberianObat.setForeground(new java.awt.Color(60, 80, 50));
        MnDataPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataPemberianObat.setText("Data Pemberian Obat");
        MnDataPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataPemberianObat.setName("MnDataPemberianObat"); // NOI18N
        MnDataPemberianObat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDataPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDataPemberianObat);

        MnPoli.setBackground(new java.awt.Color(255, 255, 255));
        MnPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPoli.setForeground(new java.awt.Color(60, 80, 50));
        MnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPoli.setText("Ganti Poliklinik");
        MnPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPoli.setName("MnPoli"); // NOI18N
        MnPoli.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPoliActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPoli);

        MnDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDokter.setForeground(new java.awt.Color(60, 80, 50));
        MnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDokter.setText("Ganti Dokter Poli");
        MnDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDokter.setName("MnDokter"); // NOI18N
        MnDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDokterActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDokter);

        MnPenjab.setBackground(new java.awt.Color(255, 255, 255));
        MnPenjab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjab.setForeground(new java.awt.Color(60, 80, 50));
        MnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjab.setText("Ganti Jenis Bayar");
        MnPenjab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjab.setName("MnPenjab"); // NOI18N
        MnPenjab.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenjab);

        MnPenjualan.setBackground(new java.awt.Color(255, 255, 255));
        MnPenjualan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjualan.setForeground(new java.awt.Color(60, 80, 50));
        MnPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjualan.setText("Penjualan Obat/Alkes/Barang");
        MnPenjualan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjualan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjualan.setName("MnPenjualan"); // NOI18N
        MnPenjualan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjualanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenjualan);

        MnDiagnosa.setBackground(new java.awt.Color(255, 255, 255));
        MnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa.setForeground(new java.awt.Color(60, 80, 50));
        MnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosa.setText("Diagnosa Pasien");
        MnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa.setName("MnDiagnosa"); // NOI18N
        MnDiagnosa.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDiagnosa);

        MnRekap.setBackground(new java.awt.Color(248, 253, 243));
        MnRekap.setForeground(new java.awt.Color(60, 80, 50));
        MnRekap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekap.setText("Rekap Data");
        MnRekap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekap.setName("MnRekap"); // NOI18N
        MnRekap.setOpaque(true);
        MnRekap.setPreferredSize(new java.awt.Dimension(220, 26));

        MnRekapHarianDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianDokter.setForeground(new java.awt.Color(60, 80, 50));
        MnRekapHarianDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianDokter.setText("Rekap Harian Dokter ");
        MnRekapHarianDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianDokter.setName("MnRekapHarianDokter"); // NOI18N
        MnRekapHarianDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianDokterActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianDokter);

        MnRekapHarianParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianParamedis.setForeground(new java.awt.Color(60, 80, 50));
        MnRekapHarianParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianParamedis.setText("Rekap Harian Paramedis");
        MnRekapHarianParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianParamedis.setName("MnRekapHarianParamedis"); // NOI18N
        MnRekapHarianParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianParamedisActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianParamedis);

        MnRekapBulananDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapBulananDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulananDokter.setForeground(new java.awt.Color(60, 80, 50));
        MnRekapBulananDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapBulananDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapBulananDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapBulananDokter.setLabel("Rekap Bulanan Dokter ");
        MnRekapBulananDokter.setName("MnRekapBulananDokter"); // NOI18N
        MnRekapBulananDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapBulananDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulananDokterActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapBulananDokter);

        MnRekapBulananParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapBulananParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulananParamedis.setForeground(new java.awt.Color(60, 80, 50));
        MnRekapBulananParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapBulananParamedis.setText("Rekap Bulanan Paramedis");
        MnRekapBulananParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapBulananParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapBulananParamedis.setName("MnRekapBulananParamedis"); // NOI18N
        MnRekapBulananParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapBulananParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulananParamedisActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapBulananParamedis);

        MnRekapHarianPoli.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianPoli.setForeground(new java.awt.Color(60, 80, 50));
        MnRekapHarianPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianPoli.setText("Rekap Harian Poli");
        MnRekapHarianPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianPoli.setName("MnRekapHarianPoli"); // NOI18N
        MnRekapHarianPoli.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianPoliActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianPoli);

        MnRekapHarianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianObat.setForeground(new java.awt.Color(60, 80, 50));
        MnRekapHarianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianObat.setText("Rekap Harian Obat");
        MnRekapHarianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianObat.setName("MnRekapHarianObat"); // NOI18N
        MnRekapHarianObat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianObatActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianObat);

        jPopupMenu1.add(MnRekap);

        MnStatus.setBackground(new java.awt.Color(248, 253, 243));
        MnStatus.setForeground(new java.awt.Color(60, 80, 50));
        MnStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatus.setText("Set Status");
        MnStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatus.setName("MnStatus"); // NOI18N
        MnStatus.setOpaque(true);
        MnStatus.setPreferredSize(new java.awt.Dimension(220, 26));

        ppBerkas.setBackground(new java.awt.Color(255, 255, 255));
        ppBerkas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkas.setForeground(new java.awt.Color(60, 80, 50));
        ppBerkas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkas.setText("Berkas R.M. Diterima");
        ppBerkas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkas.setName("ppBerkas"); // NOI18N
        ppBerkas.setPreferredSize(new java.awt.Dimension(220, 26));
        ppBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(ppBerkas);

        MnSudah.setBackground(new java.awt.Color(255, 255, 255));
        MnSudah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudah.setForeground(new java.awt.Color(60, 80, 50));
        MnSudah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSudah.setText("Sudah Periksa");
        MnSudah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSudah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSudah.setName("MnSudah"); // NOI18N
        MnSudah.setPreferredSize(new java.awt.Dimension(220, 26));
        MnSudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahActionPerformed(evt);
            }
        });
        MnStatus.add(MnSudah);

        MnBelum.setBackground(new java.awt.Color(255, 255, 255));
        MnBelum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelum.setForeground(new java.awt.Color(60, 80, 50));
        MnBelum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBelum.setText("Belum Periksa");
        MnBelum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBelum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBelum.setName("MnBelum"); // NOI18N
        MnBelum.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBelum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumActionPerformed(evt);
            }
        });
        MnStatus.add(MnBelum);

        MnBayar.setBackground(new java.awt.Color(255, 255, 255));
        MnBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBayar.setForeground(new java.awt.Color(60, 80, 50));
        MnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBayar.setText("Sudah Bayar");
        MnBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBayar.setName("MnBayar"); // NOI18N
        MnBayar.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBayarActionPerformed(evt);
            }
        });
        MnStatus.add(MnBayar);

        MnBatal.setBackground(new java.awt.Color(255, 255, 255));
        MnBatal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBatal.setForeground(new java.awt.Color(60, 80, 50));
        MnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBatal.setText("Batal Periksa");
        MnBatal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBatal.setName("MnBatal"); // NOI18N
        MnBatal.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBatalActionPerformed(evt);
            }
        });
        MnStatus.add(MnBatal);

        MnDirujuk.setBackground(new java.awt.Color(255, 255, 255));
        MnDirujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDirujuk.setForeground(new java.awt.Color(60, 80, 50));
        MnDirujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDirujuk.setText("Dirujuk");
        MnDirujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDirujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDirujuk.setName("MnDirujuk"); // NOI18N
        MnDirujuk.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDirujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDirujukActionPerformed(evt);
            }
        });
        MnStatus.add(MnDirujuk);

        MnDIrawat.setBackground(new java.awt.Color(255, 255, 255));
        MnDIrawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDIrawat.setForeground(new java.awt.Color(60, 80, 50));
        MnDIrawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDIrawat.setText("Dirawat");
        MnDIrawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDIrawat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDIrawat.setName("MnDIrawat"); // NOI18N
        MnDIrawat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDIrawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDIrawatActionPerformed(evt);
            }
        });
        MnStatus.add(MnDIrawat);

        MnMeninggal.setBackground(new java.awt.Color(255, 255, 255));
        MnMeninggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnMeninggal.setForeground(new java.awt.Color(60, 80, 50));
        MnMeninggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnMeninggal.setText("Meninggal");
        MnMeninggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnMeninggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnMeninggal.setName("MnMeninggal"); // NOI18N
        MnMeninggal.setPreferredSize(new java.awt.Dimension(220, 26));
        MnMeninggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnMeninggalActionPerformed(evt);
            }
        });
        MnStatus.add(MnMeninggal);

        jPopupMenu1.add(MnStatus);

        MnHapusData.setBackground(new java.awt.Color(248, 253, 243));
        MnHapusData.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusData.setText("Hapus Data");
        MnHapusData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusData.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusData.setName("MnHapusData"); // NOI18N
        MnHapusData.setOpaque(true);
        MnHapusData.setPreferredSize(new java.awt.Dimension(220, 26));

        MnHapusTagihanOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihanOperasi.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihanOperasi.setText("Tagihan Operasi");
        MnHapusTagihanOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTagihanOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTagihanOperasi.setName("MnHapusTagihanOperasi"); // NOI18N
        MnHapusTagihanOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusTagihanOperasi);

        MnHapusObatOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusObatOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObatOperasi.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusObatOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObatOperasi.setText("Obat Operasi");
        MnHapusObatOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusObatOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusObatOperasi.setName("MnHapusObatOperasi"); // NOI18N
        MnHapusObatOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusObatOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusObatOperasi);

        MnHapusBilling.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusBilling.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusBilling.setText("Billing");
        MnHapusBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusBilling.setName("MnHapusBilling"); // NOI18N
        MnHapusBilling.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusBillingActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusBilling);

        MnHapusDeposit.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusDeposit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDeposit.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusDeposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDeposit.setText("Deposit");
        MnHapusDeposit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDeposit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDeposit.setName("MnHapusDeposit"); // NOI18N
        MnHapusDeposit.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDepositActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDeposit);

        MnHapusDiet.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDiet.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDiet.setText("Pemberian Diet");
        MnHapusDiet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDiet.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDiet.setName("MnHapusDiet"); // NOI18N
        MnHapusDiet.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDietActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDiet);

        MnHapusDiagnosa.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDiagnosa.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDiagnosa.setText("Diagnosa/Penyakit");
        MnHapusDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDiagnosa.setName("MnHapusDiagnosa"); // NOI18N
        MnHapusDiagnosa.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDiagnosaActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDiagnosa);

        MnHapusDpjp.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusDpjp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDpjp.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusDpjp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDpjp.setText("DPJP Ranap");
        MnHapusDpjp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDpjp.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDpjp.setName("MnHapusDpjp"); // NOI18N
        MnHapusDpjp.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusDpjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDpjpActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDpjp);

        MnHapusHemodialisa.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusHemodialisa.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusHemodialisa.setText("Hemodialisa");
        MnHapusHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusHemodialisa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusHemodialisa.setName("MnHapusHemodialisa"); // NOI18N
        MnHapusHemodialisa.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusHemodialisaActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusHemodialisa);

        MnHapusKamarInap.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusKamarInap.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusKamarInap.setText("Kamar Inap");
        MnHapusKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusKamarInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusKamarInap.setName("MnHapusKamarInap"); // NOI18N
        MnHapusKamarInap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusKamarInapActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusKamarInap);

        MnHapusPotongan.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusPotongan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPotongan.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPotongan.setText("Potongan Biaya");
        MnHapusPotongan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPotongan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPotongan.setName("MnHapusPotongan"); // NOI18N
        MnHapusPotongan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPotonganActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusPotongan);

        MnHapusPiutang.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPiutang.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPiutang.setText("Piutang Pasien");
        MnHapusPiutang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPiutang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPiutang.setName("MnHapusPiutang"); // NOI18N
        MnHapusPiutang.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPiutangActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusPiutang);

        MnHapusProsedur.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusProsedur.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusProsedur.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusProsedur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusProsedur.setText("Prosedur Tindakan");
        MnHapusProsedur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusProsedur.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusProsedur.setName("MnHapusProsedur"); // NOI18N
        MnHapusProsedur.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusProsedur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusProsedurActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusProsedur);

        MnHapusRanapGabung.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusRanapGabung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRanapGabung.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusRanapGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRanapGabung.setText("Ranap Gabung");
        MnHapusRanapGabung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRanapGabung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRanapGabung.setName("MnHapusRanapGabung"); // NOI18N
        MnHapusRanapGabung.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusRanapGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRanapGabungActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusRanapGabung);

        MnHapusRujukKeluar.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusRujukKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRujukKeluar.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusRujukKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRujukKeluar.setText("Rujuk Keluar");
        MnHapusRujukKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRujukKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRujukKeluar.setName("MnHapusRujukKeluar"); // NOI18N
        MnHapusRujukKeluar.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusRujukKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRujukKeluarActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusRujukKeluar);

        MnHapusRujukMasuk.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRujukMasuk.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRujukMasuk.setText("Rujuk Masuk");
        MnHapusRujukMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRujukMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRujukMasuk.setName("MnHapusRujukMasuk"); // NOI18N
        MnHapusRujukMasuk.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRujukMasukActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusRujukMasuk);

        MnHapusTambahan.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTambahan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTambahan.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusTambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTambahan.setText("Tambahan Biaya");
        MnHapusTambahan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTambahan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTambahan.setName("MnHapusTambahan"); // NOI18N
        MnHapusTambahan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTambahanActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusTambahan);

        MnTindakan.setBackground(new java.awt.Color(248, 253, 243));
        MnTindakan.setForeground(new java.awt.Color(60, 80, 50));
        MnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan.setText("Tindakan");
        MnTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan.setName("MnTindakan"); // NOI18N
        MnTindakan.setOpaque(true);
        MnTindakan.setPreferredSize(new java.awt.Dimension(220, 26));

        MnHapusTindakanRanapDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRanapDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRanapDokter.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusTindakanRanapDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRanapDokter.setText("Ranap Dokter");
        MnHapusTindakanRanapDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRanapDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRanapDokter.setName("MnHapusTindakanRanapDokter"); // NOI18N
        MnHapusTindakanRanapDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRanapDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRanapDokterActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRanapDokter);

        MnHapusTindakanRanapDokterParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRanapDokterParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRanapDokterParamedis.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusTindakanRanapDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRanapDokterParamedis.setText("Ranap Dokter & Paramedis");
        MnHapusTindakanRanapDokterParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRanapDokterParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRanapDokterParamedis.setName("MnHapusTindakanRanapDokterParamedis"); // NOI18N
        MnHapusTindakanRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRanapDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRanapDokterParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRanapDokterParamedis);

        MnHapusTindakanRanapParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRanapParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRanapParamedis.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusTindakanRanapParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRanapParamedis.setText("Ranap Paramedis");
        MnHapusTindakanRanapParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRanapParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRanapParamedis.setName("MnHapusTindakanRanapParamedis"); // NOI18N
        MnHapusTindakanRanapParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRanapParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRanapParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRanapParamedis);

        MnHapusTindakanRalanDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRalanDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRalanDokter.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusTindakanRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRalanDokter.setText("Ralan Dokter");
        MnHapusTindakanRalanDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRalanDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRalanDokter.setName("MnHapusTindakanRalanDokter"); // NOI18N
        MnHapusTindakanRalanDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRalanDokterActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRalanDokter);

        MnHapusTindakanRalanDokterParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRalanDokterParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRalanDokterParamedis.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusTindakanRalanDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRalanDokterParamedis.setText("Ralan Dokter & Paramedis");
        MnHapusTindakanRalanDokterParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRalanDokterParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRalanDokterParamedis.setName("MnHapusTindakanRalanDokterParamedis"); // NOI18N
        MnHapusTindakanRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRalanDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRalanDokterParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRalanDokterParamedis);

        MnHapusTindakanRalanParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRalanParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRalanParamedis.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusTindakanRalanParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRalanParamedis.setText("Ralan Paramedis");
        MnHapusTindakanRalanParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRalanParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRalanParamedis.setName("MnHapusTindakanRalanParamedis"); // NOI18N
        MnHapusTindakanRalanParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRalanParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRalanParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRalanParamedis);

        MnHapusData.add(MnTindakan);

        MnPemeriksaan.setBackground(new java.awt.Color(248, 253, 243));
        MnPemeriksaan.setForeground(new java.awt.Color(60, 80, 50));
        MnPemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemeriksaan.setText("Pemeriksaan");
        MnPemeriksaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemeriksaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemeriksaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemeriksaan.setName("MnPemeriksaan"); // NOI18N
        MnPemeriksaan.setOpaque(true);
        MnPemeriksaan.setPreferredSize(new java.awt.Dimension(220, 26));

        MnHapusPemeriksaanRalan.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusPemeriksaanRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPemeriksaanRalan.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusPemeriksaanRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPemeriksaanRalan.setText("Ralan");
        MnHapusPemeriksaanRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPemeriksaanRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPemeriksaanRalan.setName("MnHapusPemeriksaanRalan"); // NOI18N
        MnHapusPemeriksaanRalan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusPemeriksaanRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPemeriksaanRalanActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusPemeriksaanRalan);

        MnHapusPemeriksaanRanap.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusPemeriksaanRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPemeriksaanRanap.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusPemeriksaanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPemeriksaanRanap.setText("Ranap");
        MnHapusPemeriksaanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPemeriksaanRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPemeriksaanRanap.setName("MnHapusPemeriksaanRanap"); // NOI18N
        MnHapusPemeriksaanRanap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusPemeriksaanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPemeriksaanRanapActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusPemeriksaanRanap);

        MnHapusLab.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusLab.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusLab.setText("Laborat");
        MnHapusLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusLab.setName("MnHapusLab"); // NOI18N
        MnHapusLab.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusLabActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusLab);

        MnHapusRadiologi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRadiologi.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRadiologi.setText("Radiologi");
        MnHapusRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRadiologi.setName("MnHapusRadiologi"); // NOI18N
        MnHapusRadiologi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRadiologiActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusRadiologi);

        MnHapusData.add(MnPemeriksaan);

        MnObat.setBackground(new java.awt.Color(248, 253, 243));
        MnObat.setForeground(new java.awt.Color(60, 80, 50));
        MnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObat.setText("Obat");
        MnObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat.setName("MnObat"); // NOI18N
        MnObat.setOpaque(true);
        MnObat.setPreferredSize(new java.awt.Dimension(220, 26));

        MnHapusAturanPkaiObat.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusAturanPkaiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusAturanPkaiObat.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusAturanPkaiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusAturanPkaiObat.setText("Aturan Pakai Obat");
        MnHapusAturanPkaiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusAturanPkaiObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusAturanPkaiObat.setName("MnHapusAturanPkaiObat"); // NOI18N
        MnHapusAturanPkaiObat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusAturanPkaiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusAturanPkaiObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusAturanPkaiObat);

        MnHapusObat.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObat.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObat.setText("Pemberian Obat");
        MnHapusObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusObat.setName("MnHapusObat"); // NOI18N
        MnHapusObat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusObat);

        MnHapusResepObat.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusResepObat.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusResepObat.setText("Resep Obat");
        MnHapusResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusResepObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusResepObat.setName("MnHapusResepObat"); // NOI18N
        MnHapusResepObat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusResepObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusResepObat);

        MnHapusResepPulang.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusResepPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusResepPulang.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusResepPulang.setText("Resep Pulang");
        MnHapusResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusResepPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusResepPulang.setName("MnHapusResepPulang"); // NOI18N
        MnHapusResepPulang.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusResepPulangActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusResepPulang);

        MnHapusReturObat.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusReturObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusReturObat.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusReturObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusReturObat.setText("Retur Obat Pasien");
        MnHapusReturObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusReturObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusReturObat.setName("MnHapusReturObat"); // NOI18N
        MnHapusReturObat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusReturObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusReturObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusReturObat);

        MnHapusStokObatRanap.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusStokObatRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusStokObatRanap.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusStokObatRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusStokObatRanap.setText("Stok Obat Ranap");
        MnHapusStokObatRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusStokObatRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusStokObatRanap.setName("MnHapusStokObatRanap"); // NOI18N
        MnHapusStokObatRanap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusStokObatRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusStokObatRanapActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusStokObatRanap);

        MnHapusData.add(MnObat);

        MnHapusSemua.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusSemua.setForeground(new java.awt.Color(60, 80, 50));
        MnHapusSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusSemua.setText("Semua Transaksi & Registrasi");
        MnHapusSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusSemua.setName("MnHapusSemua"); // NOI18N
        MnHapusSemua.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusSemuaActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusSemua);

        jPopupMenu1.add(MnHapusData);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 255));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(60, 80, 50));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(220, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

        MnRujuk.setBackground(new java.awt.Color(255, 255, 255));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(new java.awt.Color(60, 80, 50));
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujuk.setText("Rujukan Keluar");
        MnRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRujuk);

        ppCatatanPasien.setBackground(new java.awt.Color(255, 255, 255));
        ppCatatanPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCatatanPasien.setForeground(new java.awt.Color(60, 80, 50));
        ppCatatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCatatanPasien.setText("Catatan Untuk Pasien");
        ppCatatanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCatatanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCatatanPasien.setName("ppCatatanPasien"); // NOI18N
        ppCatatanPasien.setPreferredSize(new java.awt.Dimension(220, 26));
        ppCatatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCatatanPasienBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppCatatanPasien);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setSelectionColor(new java.awt.Color(255, 255, 255));
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });

        WindowObatBhp.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowObatBhp.setName("WindowObatBhp"); // NOI18N
        WindowObatBhp.setUndecorated(true);
        WindowObatBhp.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Total Tagihan Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame2.setLayout(null);

        TotalObat.setHighlighter(null);
        TotalObat.setName("TotalObat"); // NOI18N
        TotalObat.setSelectionColor(new java.awt.Color(255, 255, 255));
        TotalObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalObatKeyPressed(evt);
            }
        });
        internalFrame2.add(TotalObat);
        TotalObat.setBounds(60, 30, 180, 23);

        jLabel3.setText("Total :");
        jLabel3.setName("jLabel3"); // NOI18N
        internalFrame2.add(jLabel3);
        jLabel3.setBounds(0, 30, 57, 23);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('U');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+U");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(465, 30, 100, 30);

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
        internalFrame2.add(BtnSimpan);
        BtnSimpan.setBounds(255, 30, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnBatal.setMnemonic('H');
        BtnBatal.setText("Hapus");
        BtnBatal.setToolTipText("Alt+H");
        BtnBatal.setName("BtnBatal"); // NOI18N
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
        internalFrame2.add(BtnBatal);
        BtnBatal.setBounds(360, 30, 100, 30);

        WindowObatBhp.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        WindowGantiDokter.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiDokter.setName("WindowGantiDokter"); // NOI18N
        WindowGantiDokter.setUndecorated(true);
        WindowGantiDokter.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Dokter Poli ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame3.setLayout(null);

        BtnCloseIn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn1.setMnemonic('U');
        BtnCloseIn1.setText("Tutup");
        BtnCloseIn1.setToolTipText("Alt+U");
        BtnCloseIn1.setName("BtnCloseIn1"); // NOI18N
        BtnCloseIn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn1ActionPerformed(evt);
            }
        });
        BtnCloseIn1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseIn1KeyPressed(evt);
            }
        });
        internalFrame3.add(BtnCloseIn1);
        BtnCloseIn1.setBounds(510, 30, 100, 30);

        BtnSimpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan1.setMnemonic('S');
        BtnSimpan1.setText("Simpan");
        BtnSimpan1.setToolTipText("Alt+S");
        BtnSimpan1.setName("BtnSimpan1"); // NOI18N
        BtnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan1ActionPerformed(evt);
            }
        });
        BtnSimpan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan1KeyPressed(evt);
            }
        });
        internalFrame3.add(BtnSimpan1);
        BtnSimpan1.setBounds(405, 30, 100, 30);

        jLabel13.setText("Dr Dituju :");
        jLabel13.setName("jLabel13"); // NOI18N
        internalFrame3.add(jLabel13);
        jLabel13.setBounds(0, 32, 77, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.setSelectionColor(new java.awt.Color(255, 255, 255));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        internalFrame3.add(kddokter);
        kddokter.setBounds(81, 32, 100, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        TDokter.setSelectionColor(new java.awt.Color(255, 255, 255));
        internalFrame3.add(TDokter);
        TDokter.setBounds(183, 32, 181, 23);

        btnCariDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnCariDokter.setMnemonic('7');
        btnCariDokter.setToolTipText("ALt+7");
        btnCariDokter.setName("btnCariDokter"); // NOI18N
        btnCariDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariDokterActionPerformed(evt);
            }
        });
        internalFrame3.add(btnCariDokter);
        btnCariDokter.setBounds(366, 32, 28, 23);

        WindowGantiDokter.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setSelectionColor(new java.awt.Color(255, 255, 255));
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.setSelectionColor(new java.awt.Color(255, 255, 255));

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setSelectionColor(new java.awt.Color(255, 255, 255));

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N
        Jam.setSelectionColor(new java.awt.Color(255, 255, 255));

        WindowGantiPoli.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiPoli.setName("WindowGantiPoli"); // NOI18N
        WindowGantiPoli.setUndecorated(true);
        WindowGantiPoli.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(510, 30, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(405, 30, 100, 30);

        jLabel18.setText("Poli Dituju :");
        jLabel18.setName("jLabel18"); // NOI18N
        internalFrame5.add(jLabel18);
        jLabel18.setBounds(0, 32, 77, 23);

        kdpoli.setHighlighter(null);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.setSelectionColor(new java.awt.Color(255, 255, 255));
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        internalFrame5.add(kdpoli);
        kdpoli.setBounds(81, 32, 100, 23);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setSelectionColor(new java.awt.Color(255, 255, 255));
        internalFrame5.add(nmpoli);
        nmpoli.setBounds(183, 32, 181, 23);

        btnCariPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnCariPoli.setMnemonic('7');
        btnCariPoli.setToolTipText("ALt+7");
        btnCariPoli.setName("btnCariPoli"); // NOI18N
        btnCariPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPoliActionPerformed(evt);
            }
        });
        internalFrame5.add(btnCariPoli);
        btnCariPoli.setBounds(366, 32, 28, 23);

        WindowGantiPoli.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        WindowCaraBayar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCaraBayar.setName("WindowCaraBayar"); // NOI18N
        WindowCaraBayar.setUndecorated(true);
        WindowCaraBayar.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Jenis Bayar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame6.setLayout(null);

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnCloseIn5);
        BtnCloseIn5.setBounds(510, 30, 100, 30);

        BtnSimpan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan5.setMnemonic('S');
        BtnSimpan5.setText("Simpan");
        BtnSimpan5.setToolTipText("Alt+S");
        BtnSimpan5.setName("BtnSimpan5"); // NOI18N
        BtnSimpan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan5ActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnSimpan5);
        BtnSimpan5.setBounds(405, 30, 100, 30);

        jLabel19.setText("Jenis Bayar :");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame6.add(jLabel19);
        jLabel19.setBounds(0, 32, 77, 23);

        kdpenjab.setHighlighter(null);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setSelectionColor(new java.awt.Color(255, 255, 255));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        internalFrame6.add(kdpenjab);
        kdpenjab.setBounds(81, 32, 100, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setSelectionColor(new java.awt.Color(255, 255, 255));
        internalFrame6.add(nmpenjab);
        nmpenjab.setBounds(183, 32, 181, 23);

        btnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBayar.setMnemonic('7');
        btnBayar.setToolTipText("ALt+7");
        btnBayar.setName("btnBayar"); // NOI18N
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });
        internalFrame6.add(btnBayar);
        btnBayar.setBounds(366, 32, 28, 23);

        WindowCaraBayar.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kasir Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setToolTipText("Klik 2X Kd.Dokter= Jendela Tindakan, Dokter Dituju=Jendela Obat, Nomer RM=Jendela Billing, Pasien=Jendela Total Obat, Poliklinik=Set Sudah Periksa, Penanggung Jawab=Masukan tindakan otomatis");
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKasirRalan.setToolTipText("Klik 2X Kd.Dokter= Jendela Tindakan, Dokter Dituju=Jendela Obat, Nomer RM=Jendela Billing, Pasien=Jendela Total Obat, Poliklinik=Set Sudah Periksa, Penanggung Jawab=Masukan tindakan otomatis");
        tbKasirRalan.setComponentPopupMenu(jPopupMenu1);
        tbKasirRalan.setName("tbKasirRalan"); // NOI18N
        tbKasirRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKasirRalanMouseClicked(evt);
            }
        });
        tbKasirRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKasirRalanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKasirRalan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass6.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(405, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass6.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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
        panelGlass6.add(BtnCari);

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
        panelGlass6.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(100, 30));
        panelGlass6.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(100, 30));
        panelGlass6.add(LCount);

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
        panelGlass6.add(BtnKeluar);

        jPanel2.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass7.add(jLabel14);

        CrPtg.setEditable(false);
        CrPtg.setName("CrPtg"); // NOI18N
        CrPtg.setPreferredSize(new java.awt.Dimension(280, 23));
        panelGlass7.add(CrPtg);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('4');
        BtnSeek3.setToolTipText("ALt+4");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeek3);

        jLabel16.setText("Poliklinik :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass7.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(280, 23));
        panelGlass7.add(CrPoli);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeek4);

        jPanel2.add(panelGlass7, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(jLabel15);

        DTPCari1.setEditable(false);
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-09-2017" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(140, 23));
        DTPCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari1KeyPressed(evt);
            }
        });
        panelGlass8.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass8.add(jLabel17);

        DTPCari2.setEditable(false);
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-09-2017" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(140, 23));
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelGlass8.add(DTPCari2);

        jLabel12.setText("Status Periksa :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass8.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Berkas Diterima", "Sudah", "Belum", "Bayar", "Batal" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setOpaque(false);
        cmbStatus.setPreferredSize(new java.awt.Dimension(308, 23));
        cmbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusItemStateChanged(evt);
            }
        });
        panelGlass8.add(cmbStatus);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));
        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,cmbStatus,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        CrPoli.setText("");
        CrPtg.setText("");
        TCari.setText("");
        tampilkasir();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampilkasir();
            TCari.setText("");
        }else{
            Valid.pindah(evt, TCari, BtnKeluar);
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
        tampilkasir();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_DTPCari2KeyPressed

    private void tbKasirRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKasirRalanMouseClicked
        if(tabModekasir.getRowCount()!=0){
            try {
                getDatakasir();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                i=tbKasirRalan.getSelectedColumn();
                if(i==0){
                    if(var.gettindakan_ralan()==true){
                        MnRawatJalanActionPerformed(null);                        
                    }
                }else if(i==1){
                    if(var.getberi_obat()==true){
                        MnPemberianObatActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBillingActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(var.getkamar_inap()==true){
                        MnKamarInapActionPerformed(null);
                    }                    
                }else if(i==4){
                    if(var.getkasir_ralan()==true){
                        MnSudahActionPerformed(null);
                        tampilkasir();
                    }                    
                }
            }
        }
}//GEN-LAST:event_tbKasirRalanMouseClicked

    private void tbKasirRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKasirRalanKeyPressed
        if(tabModekasir.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDatakasir();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                i=tbKasirRalan.getSelectedColumn();
                if(i==0){
                    if(var.gettindakan_ralan()==true){
                        MnRawatJalanActionPerformed(null);                        
                    }
                }else if(i==1){
                    if(var.getberi_obat()==true){
                        MnPemberianObatActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBillingActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(var.getkamar_inap()==true){
                        MnKamarInapActionPerformed(null);
                    }                    
                }else if(i==4){
                    if(var.getkasir_ralan()==true){
                        MnSudahActionPerformed(null);
                        tampilkasir();
                    }                    
                }
            }
        }
}//GEN-LAST:event_tbKasirRalanKeyPressed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
       var.setform("DlgKasirRalan");
       pilihan=2;
        billing.dokter.isCek();
        billing.dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        billing.dokter.setLocationRelativeTo(internalFrame1);
        billing.dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
       var.setform("DlgKasirRalan");
       pilihan=2;
        billing.poli.isCek();
        billing.poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        billing.poli.setLocationRelativeTo(internalFrame1);
        billing.poli.setVisible(true);
}//GEN-LAST:event_BtnSeek4ActionPerformed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
       if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{      
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else{
                if(var.getkode().equals("Admin Utama")){
                    billing.dlgrwjl.perawatan.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),
                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),"rawat_jl_dr","","","","","","","-","-","","","","","","");
                    billing.dlgrwjl.perawatan.isCek();
                    billing.dlgrwjl.perawatan.tampil();
                    billing.dlgrwjl.perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                    billing.dlgrwjl.perawatan.setLocationRelativeTo(internalFrame1);
                    billing.dlgrwjl.perawatan.setVisible(true);
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{
                        billing.dlgrwjl.perawatan.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),
                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),"rawat_jl_dr","","","","","","","-","-","","","","","","");
                        billing.dlgrwjl.perawatan.isCek();
                        billing.dlgrwjl.perawatan.tampil();
                        billing.dlgrwjl.perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                        billing.dlgrwjl.perawatan.setLocationRelativeTo(internalFrame1);
                        billing.dlgrwjl.perawatan.setVisible(true);
                    }
                }
                    
            }            
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                if(var.getkode().equals("Admin Utama")){
                    TKdPny.setText("-");
                    billing.dlgobt.setNoRm(TNoRw.getText(),Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+TNoRw.getText()+"'"),
                                        Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+TNoRw.getText()+"'"));
                    billing.dlgobt.isCek();
                    billing.dlgobt.tampilobat();
                    billing.dlgobt.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                    billing.dlgobt.setLocationRelativeTo(internalFrame1);
                    billing.dlgobt.setVisible(true);
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        TKdPny.setText("-");
                        billing.dlgobt.setNoRm(TNoRw.getText(),Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+TNoRw.getText()+"'"),
                                            Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+TNoRw.getText()+"'"));
                        billing.dlgobt.isCek();
                        billing.dlgobt.tampilobat();
                        billing.dlgobt.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                        billing.dlgobt.setLocationRelativeTo(internalFrame1);
                        billing.dlgobt.setVisible(true);
                    }
                }                    
            }            
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{          
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                try {
                    sudah=Sequel.cariInteger("select count(billing.no_rawat) from billing where billing.no_rawat=?",TNoRw.getText());
                    pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                    try{                                                
                        pscaripiutang.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString());
                        rskasir=pscaripiutang.executeQuery();
                        if(rskasir.next()){
                            i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                            if(i==JOptionPane.YES_OPTION){
                                 DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                                 piutang.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),rskasir.getDate(1));
                                 piutang.tampil();
                                 piutang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                                 piutang.setLocationRelativeTo(internalFrame1);
                                 piutang.setVisible(true);
                            }else{
                                try {
                                    psotomatis=koneksi.prepareStatement(sqlpsotomatis);
                                    try {
                                        psotomatis.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                        psotomatis.setString(2,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()));
                                        rskasir=psotomatis.executeQuery();
                                        if(rskasir.next()){    
                                            if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_dr where "+
                                               "no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                                               "and kd_dokter='"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString()+"'")==0){
                                                psotomatis2=koneksi.prepareStatement(sqlpsotomatis2);
                                                try {
                                                    psotomatis2.setString(1,TNoRw.getText()); 
                                                    psotomatis2.setString(2,rskasir.getString(1));
                                                    psotomatis2.setString(3,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                                    psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                                                    psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                                                    psotomatis2.setDouble(6,rskasir.getDouble("material"));
                                                    psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                                                    psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakandr"));
                                                    psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                                                    psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                                                    psotomatis2.setDouble(11,rskasir.getDouble("total_byrdr"));
                                                    psotomatis2.executeUpdate();
                                                } catch (Exception e) {
                                                    System.out.println("proses input data "+e);
                                                } finally{
                                                    if(psotomatis2!=null){
                                                        psotomatis2.close();
                                                    }
                                                }
                                                MnSudahActionPerformed(null);
                                            }
                                        }else{
                                            psotomatis.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                            psotomatis.setString(2,"-");
                                            rskasir=psotomatis.executeQuery();
                                            if(rskasir.next()){    
                                                if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_dr where "+
                                                   "no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                                                   "and kd_dokter='"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString()+"'")==0){
                                                    psotomatis2=koneksi.prepareStatement(sqlpsotomatis2);
                                                    try {
                                                        psotomatis2.setString(1,TNoRw.getText()); 
                                                        psotomatis2.setString(2,rskasir.getString(1));
                                                        psotomatis2.setString(3,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                                        psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                                                        psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                                                        psotomatis2.setDouble(6,rskasir.getDouble("material"));
                                                        psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                                                        psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakandr"));
                                                        psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                                                        psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                                                        psotomatis2.setDouble(11,rskasir.getDouble("total_byrdr"));
                                                        psotomatis2.executeUpdate();
                                                    } catch (Exception e) {
                                                        System.out.println("proses input data "+e);
                                                    } finally{
                                                        if(psotomatis2!=null){
                                                            psotomatis2.close();
                                                        }
                                                    }
                                                    MnSudahActionPerformed(null);
                                                }
                                            }
                                        }   
                                    } catch (Exception e) {
                                        System.out.println("Notifikasi : "+e);
                                    } finally {
                                        if(rskasir!=null){
                                            rskasir.close();
                                        }
                                        if(psotomatis!=null){
                                            psotomatis.close();
                                        }
                                    }                                                                 
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : "+e);
                                }

                                billing.TNoRw.setText(TNoRw.getText());  
                                billing.isCek();
                                billing.isRawat(); 
                                if(sudah>0){
                                    billing.setPiutang();
                                }
                                billing.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                                billing.setLocationRelativeTo(internalFrame1);
                                tampilkasir();
                                billing.setVisible(true);
                            }
                        }else{
                            try {
                                psotomatis=koneksi.prepareStatement(sqlpsotomatis);
                                try {
                                    psotomatis.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                    psotomatis.setString(2,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()));
                                    rskasir=psotomatis.executeQuery();
                                    if(rskasir.next()){                            
                                        if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_dr where "+
                                           "no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                                           "and kd_dokter='"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString()+"'")==0){
                                            psotomatis2=koneksi.prepareStatement(sqlpsotomatis2);
                                            try {
                                                psotomatis2.setString(1,TNoRw.getText()); 
                                                psotomatis2.setString(2,rskasir.getString(1));
                                                psotomatis2.setString(3,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                                psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                                                psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                                                psotomatis2.setDouble(6,rskasir.getDouble("material"));
                                                psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                                                psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakandr"));
                                                psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                                                psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                                                psotomatis2.setDouble(11,rskasir.getDouble("total_byrdr"));
                                                psotomatis2.executeUpdate();
                                            } catch (Exception e) {
                                                System.out.println("proses input data "+e);
                                            } finally{
                                                if(psotomatis2!=null){
                                                    psotomatis2.close();
                                                }
                                            }
                                            MnSudahActionPerformed(null);
                                        }
                                    }else{
                                        psotomatis.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                        psotomatis.setString(2,"-");
                                        rskasir=psotomatis.executeQuery();
                                        if(rskasir.next()){                            
                                            if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_dr where "+
                                               "no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                                               "and kd_dokter='"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString()+"'")==0){
                                                psotomatis2=koneksi.prepareStatement(sqlpsotomatis2);
                                                try {
                                                    psotomatis2.setString(1,TNoRw.getText()); 
                                                    psotomatis2.setString(2,rskasir.getString(1));
                                                    psotomatis2.setString(3,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                                    psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                                                    psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                                                    psotomatis2.setDouble(6,rskasir.getDouble("material"));
                                                    psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                                                    psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakandr"));                                                    
                                                    psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                                                    psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                                                    psotomatis2.setDouble(11,rskasir.getDouble("total_byrdr"));
                                                    psotomatis2.executeUpdate();
                                                } catch (Exception e) {
                                                    System.out.println("proses input data "+e);
                                                } finally{
                                                    if(psotomatis2!=null){
                                                        psotomatis2.close();
                                                    }
                                                }
                                                MnSudahActionPerformed(null);
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(rskasir!=null){
                                        rskasir.close();
                                    }
                                    if(psotomatis!=null){
                                        psotomatis.close();
                                    }
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            }                        

                            billing.TNoRw.setText(TNoRw.getText());  
                            billing.isCek();
                            billing.isRawat(); 
                            billing.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            billing.setLocationRelativeTo(internalFrame1);
                            tampilkasir();
                            billing.setVisible(true);
                        }
                    }catch(Exception ex){
                        System.out.println("Notifikasi : "+ex);
                    } finally{
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(pscaripiutang!=null){
                            pscaripiutang.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }            
        }
}//GEN-LAST:event_MnBillingActionPerformed

private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        //Valid.pindah(evt,TNoReg,DTPReg);
}//GEN-LAST:event_TNoRwKeyPressed

private void MnSudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahActionPerformed
       if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
               Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Sudah'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
            
        }
}//GEN-LAST:event_MnSudahActionPerformed

private void MnBelumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBelumActionPerformed
       if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
           if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
           }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Belum'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
           }
            
        }
}//GEN-LAST:event_MnBelumActionPerformed

private void MnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBayarActionPerformed
      if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Bayar'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
            
        }
}//GEN-LAST:event_MnBayarActionPerformed

private void MnDataRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataRalanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgRawatJalan dlgrwjl2=new DlgRawatJalan(null,false);
                dlgrwjl2.isCek();
                dlgrwjl2.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dlgrwjl2.setLocationRelativeTo(internalFrame1);
            
                dlgrwjl2.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());    
                dlgrwjl2.tampilDr();
                dlgrwjl2.setVisible(true);
            }                
        }
}//GEN-LAST:event_MnDataRalanActionPerformed

private void TotalObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalObatKeyPressed
        Valid.pindah(evt,BtnCloseIn,BtnSimpan);

}//GEN-LAST:event_TotalObatKeyPressed

private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowObatBhp.dispose();
}//GEN-LAST:event_BtnCloseInActionPerformed

private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowObatBhp.dispose();
        }else{Valid.pindah(evt, BtnBatal, TotalObat);}
}//GEN-LAST:event_BtnCloseInKeyPressed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.menyimpan("tagihan_obat_langsung","'"+TNoRw.getText()+"','"+TotalObat.getText()+"'","No.Rawat");
            WindowObatBhp.setVisible(false);
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt,TotalObat,BtnBatal);
}//GEN-LAST:event_BtnSimpanKeyPressed

private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.queryu("delete from tagihan_obat_langsung where no_rawat=? ",TNoRw.getText());
            WindowObatBhp.setVisible(false);
        }
}//GEN-LAST:event_BtnBatalActionPerformed

private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
}//GEN-LAST:event_BtnBatalKeyPressed

private void MnObatLangsungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnObatLangsungActionPerformed
    if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
    }else {
        TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",TNoRw.getText()));
        WindowObatBhp.setSize(590,80);
        WindowObatBhp.setLocationRelativeTo(internalFrame1);
        WindowObatBhp.setVisible(true);
    }  
    
}//GEN-LAST:event_MnObatLangsungActionPerformed

private void BtnCloseIn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn1ActionPerformed
    WindowGantiDokter.dispose();
}//GEN-LAST:event_BtnCloseIn1ActionPerformed

private void BtnCloseIn1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseIn1KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnCloseIn1KeyPressed

private void BtnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }if(kddokter.getText().trim().equals("")||TDokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"Dokter");
        }else{
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            Valid.editTable(tabModekasir,"rawat_jl_dr","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            Valid.editTable(tabModekasir,"rawat_jl_drpr","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            tampilkasir();
            WindowGantiDokter.dispose();
        }
}//GEN-LAST:event_BtnSimpan1ActionPerformed

private void BtnSimpan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan1KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSimpan1KeyPressed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
           Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnCariDokterActionPerformed(null);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void btnCariDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDokterActionPerformed
       var.setform("DlgKasirRalan");
       pilihan=1;
       billing.dokter.emptTeks();
        billing.dokter.isCek();
        billing.dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        billing.dokter.setLocationRelativeTo(internalFrame1);
        billing.dokter.setVisible(true);
}//GEN-LAST:event_btnCariDokterActionPerformed

private void MnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDokterActionPerformed
    if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
    }else {
        WindowGantiDokter.setSize(630,80);
        WindowGantiDokter.setLocationRelativeTo(internalFrame1);
        WindowGantiDokter.setVisible(true);
    }
    
}//GEN-LAST:event_MnDokterActionPerformed

private void MnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjualanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{  
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPenjualan penjualan=new DlgPenjualan(null,false);
                penjualan.isCek();
                penjualan.setPasien(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"'"));  
                penjualan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                penjualan.setLocationRelativeTo(internalFrame1);
                penjualan.setVisible(true);
            }                
        }
}//GEN-LAST:event_MnPenjualanActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
       if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                kamarinap.billing.periksalab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                kamarinap.billing.periksalab.setLocationRelativeTo(internalFrame1);
                kamarinap.billing.periksalab.emptTeks();
                kamarinap.billing.periksalab.setNoRm(TNoRw.getText(),"Ralan"); 
                kamarinap.billing.periksalab.tampiltarif();
                kamarinap.billing.periksalab.tampil(); 
                kamarinap.billing.periksalab.isCek();
                kamarinap.billing.periksalab.setVisible(true);
            }            
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void MnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInapActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(var.getkode().equals("Admin Utama")){
                var.setstatus(true);
                kamarinap.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                kamarinap.setLocationRelativeTo(internalFrame1);
                kamarinap.emptTeks();
                kamarinap.isCek();
                kamarinap.setNoRm(TNoRw.getText());   
                kamarinap.tampil();
                kamarinap.setVisible(true);
            }else{
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                }else{ 
                    var.setstatus(true);
                    kamarinap.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                    kamarinap.setLocationRelativeTo(internalFrame1);
                    kamarinap.emptTeks();
                    kamarinap.isCek();
                    kamarinap.setNoRm(TNoRw.getText());   
                    kamarinap.tampil();
                    kamarinap.setVisible(true);                    
                }
            }                
        }
}//GEN-LAST:event_MnKamarInapActionPerformed

private void cmbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusItemStateChanged
    tampilkasir();
}//GEN-LAST:event_cmbStatusItemStateChanged

private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_Kd2KeyPressed

private void MnRekapHarianDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianDokterActionPerformed
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHJmDokter rhtindakandokter=new DlgRHJmDokter(null,false);
        rhtindakandokter.isCek();    
        rhtindakandokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapHarianDokterActionPerformed

private void MnRekapHarianParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianParamedisActionPerformed
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHJmParamedis rhtindakanparamedis=new DlgRHJmParamedis(null,false);
        rhtindakanparamedis.isCek();    
        rhtindakanparamedis.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakanparamedis.setLocationRelativeTo(internalFrame1);
        rhtindakanparamedis.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapHarianParamedisActionPerformed

private void MnRekapBulananDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapBulananDokterActionPerformed
   this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBJmDokter rhtindakandokter=new DlgRBJmDokter(null,false);
        rhtindakandokter.isCek();    
        rhtindakandokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapBulananDokterActionPerformed

private void MnRekapBulananParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapBulananParamedisActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBJmParamedis rhtindakanparamedis=new DlgRBJmParamedis(null,false);
        rhtindakanparamedis.isCek();    
        rhtindakanparamedis.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakanparamedis.setLocationRelativeTo(internalFrame1);
        rhtindakanparamedis.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapBulananParamedisActionPerformed

private void MnRekapHarianPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianPoliActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanPoli rhtindakandokter=new DlgRBTindakanPoli(null,false);
        rhtindakandokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapHarianPoliActionPerformed

private void MnDataPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataPemberianObatActionPerformed
     if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
                dlgrwinap.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dlgrwinap.setLocationRelativeTo(internalFrame1);
                dlgrwinap.isCek();
                dlgrwinap.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ralan"); 
                dlgrwinap.tampilPO();
                dlgrwinap.setVisible(true);
            }                
        }
}//GEN-LAST:event_MnDataPemberianObatActionPerformed

    private void MnRekapHarianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianObatActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBObatPoli rhtindakandokter=new DlgRBObatPoli(null,false);
        rhtindakandokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapHarianObatActionPerformed

    private void MnNoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNoResepActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgResepObat resep=new DlgResepObat(null,false);
                resep.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                resep.setLocationRelativeTo(internalFrame1);
                resep.emptTeks();
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),Jam.getText().substring(0,2),Jam.getText().substring(3,5),Jam.getText().substring(6,8));
                resep.tampil();
                resep.setVisible(true);
            }            
        }
    }//GEN-LAST:event_MnNoResepActionPerformed

    private void MnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologiActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                kamarinap.billing.periksarad.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                kamarinap.billing.periksarad.setLocationRelativeTo(internalFrame1);
                kamarinap.billing.periksarad.emptTeks();
                kamarinap.billing.periksarad.setNoRm(TNoRw.getText(),"Ralan"); 
                kamarinap.billing.periksarad.tampil(); 
                kamarinap.billing.periksarad.isCek();
                kamarinap.billing.periksarad.setVisible(true);
            }            
        }
    }//GEN-LAST:event_MnPeriksaRadiologiActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowGantiPoli.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }if(kdpoli.getText().trim().equals("")||nmpoli.getText().trim().equals("")){
            Valid.textKosong(kdpoli,"Poli");
        }else{
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw," kd_poli='"+kdpoli.getText()+"'");
            tampilkasir();
            WindowGantiPoli.dispose();
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnCariPoliActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnCloseIn4,BtnSimpan4);
        }
    }//GEN-LAST:event_kdpoliKeyPressed

    private void btnCariPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPoliActionPerformed
        var.setform("DlgKasirRalan");
        pilihan=1;
        billing.poli.isCek();
        billing.poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        billing.poli.setLocationRelativeTo(internalFrame1);
        billing.poli.setAlwaysOnTop(false);
        billing.poli.setVisible(true);
    }//GEN-LAST:event_btnCariPoliActionPerformed

    private void MnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPoliActionPerformed
        WindowGantiPoli.setSize(630,80);
        WindowGantiPoli.setLocationRelativeTo(internalFrame1);
        WindowGantiPoli.setAlwaysOnTop(false);
        WindowGantiPoli.setVisible(true);
    }//GEN-LAST:event_MnPoliActionPerformed

    private void MnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Masukkan diagnosa lewat kamar inap..!!!");
            }else {
                DlgDiagnosaPenyakit resep=new DlgDiagnosaPenyakit(null,false);
                resep.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Ralan");
                resep.tampil();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnDiagnosaActionPerformed

    private void MnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBatalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Batal'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
        }
    }//GEN-LAST:event_MnBatalActionPerformed

    private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            DlgTagihanOperasi dlgro=new DlgTagihanOperasi(null,false);
            dlgro.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString()+", "+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString(),"Ralan");
            dlgro.setVisible(true);
        }
    }//GEN-LAST:event_MnOperasiActionPerformed

    private void MnHapusTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from operasi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

    private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusObatOperasiActionPerformed

    private void MnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjabActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKasirRalan.requestFocus();
        }else{
            WindowCaraBayar.setSize(630,80);
            WindowCaraBayar.setLocationRelativeTo(internalFrame1);
            WindowCaraBayar.setVisible(true);
        }
    }//GEN-LAST:event_MnPenjabActionPerformed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowCaraBayar.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void BtnSimpan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan5ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TCari,"No.Rawat");
        }if(kdpenjab.getText().trim().equals("")||nmpenjab.getText().trim().equals("")){
            Valid.textKosong(kdpenjab,"Jenis Bayar");
        }else{
            //String kdpj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norawat.getText());
            Sequel.AutoComitFalse();
            //Sequel.meghapus("ubah_penjab","no_rawat",norawat.getText());
            Sequel.mengedit("reg_periksa","no_rawat=?"," kd_pj=?",2,new String[]{kdpenjab.getText(),TNoRw.getText()});
            //Sequel.menyimpan("ubah_penjab","?,?,?,?","Ubah Jenis Bayar",4,new String[]{norawat.getText(),now,kdpj,kdpenjab.getText()});
            Sequel.AutoComitTrue();
            tampilkasir();
            WindowCaraBayar.dispose();
        }
    }//GEN-LAST:event_BtnSimpan5ActionPerformed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpenjab,kdpenjab.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBayarActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnCloseIn4,BtnSimpan4);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        var.setform("DlgKasirRalan");
        billing.penjab.emptTeks();
        billing.penjab.isCek();
        billing.penjab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        billing.penjab.setLocationRelativeTo(internalFrame1);
        billing.penjab.setVisible(true);
    }//GEN-LAST:event_btnBayarActionPerformed

    private void MnHapusAturanPkaiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusAturanPkaiObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from aturan_pakai where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusAturanPkaiObatActionPerformed

    private void MnHapusBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusBillingActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from billing where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from nota_inap where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from nota_jalan where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusBillingActionPerformed

    private void MnHapusDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDepositActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from deposit where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusDepositActionPerformed

    private void MnHapusDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDietActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from detail_beri_diet where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusDietActionPerformed

    private void MnHapusObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from detail_pemberian_obat where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from tagihan_obat_langsung where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusObatActionPerformed

    private void MnHapusLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusLabActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from detail_periksa_lab where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from periksa_lab where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusLabActionPerformed

    private void MnHapusDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDiagnosaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from diagnosa_pasien where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusDiagnosaActionPerformed

    private void MnHapusDpjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDpjpActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from dpjp_ranap where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusDpjpActionPerformed

    private void MnHapusHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusHemodialisaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from hemodialisa where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusHemodialisaActionPerformed

    private void MnHapusKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusKamarInapActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from kamar_inap where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusKamarInapActionPerformed

    private void MnHapusPemeriksaanRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusPemeriksaanRalanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusPemeriksaanRalanActionPerformed

    private void MnHapusPemeriksaanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusPemeriksaanRanapActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from pemeriksaan_ranap where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusPemeriksaanRanapActionPerformed

    private void MnHapusPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusPotonganActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from pengurangan_biaya where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusPotonganActionPerformed

    private void MnHapusRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRadiologiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from beri_bhp_radiologi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from periksa_radiologi where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusRadiologiActionPerformed

    private void MnHapusPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusPiutangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusPiutangActionPerformed

    private void MnHapusProsedurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusProsedurActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from prosedur_pasien where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusProsedurActionPerformed

    private void MnHapusRanapGabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRanapGabungActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from ranap_gabung where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusRanapGabungActionPerformed

    private void MnHapusTindakanRanapDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRanapDokterActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_inap_dr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRanapDokterActionPerformed

    private void MnHapusTindakanRanapDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRanapDokterParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_inap_drpr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRanapDokterParamedisActionPerformed

    private void MnHapusTindakanRanapParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRanapParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_inap_pr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRanapParamedisActionPerformed

    private void MnHapusTindakanRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRalanDokterActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_jl_dr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRalanDokterActionPerformed

    private void MnHapusTindakanRalanDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRalanDokterParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_jl_drpr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRalanDokterParamedisActionPerformed

    private void MnHapusTindakanRalanParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRalanParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_jl_pr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRalanParamedisActionPerformed

    private void MnHapusResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusResepObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from resep_obat where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusResepObatActionPerformed

    private void MnHapusResepPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusResepPulangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from resep_pulang where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusResepPulangActionPerformed

    private void MnHapusReturObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusReturObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from returpasien where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusReturObatActionPerformed

    private void MnHapusRujukKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRujukKeluarActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rujuk where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusRujukKeluarActionPerformed

    private void MnHapusRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRujukMasukActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rujuk_masuk where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusRujukMasukActionPerformed

    private void MnHapusStokObatRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusStokObatRanapActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from stok_obat_pasien where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusStokObatRanapActionPerformed

    private void MnHapusTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTambahanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from tambahan_biaya where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTambahanActionPerformed

    private void MnHapusSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusSemuaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.AutoComitFalse();
            Sequel.queryu("delete from operasi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from billing where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from nota_inap where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from nota_jalan where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from deposit where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from detail_beri_diet where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from diagnosa_pasien where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from dpjp_ranap where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from hemodialisa where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from kamar_inap where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from pengurangan_biaya where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from prosedur_pasien where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from ranap_gabung where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from rujuk where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from rujuk_masuk where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from tambahan_biaya where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from rawat_inap_dr where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from rawat_inap_drpr where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from rawat_inap_pr where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from rawat_jl_dr where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from rawat_jl_drpr where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from rawat_jl_pr where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from pemeriksaan_ranap where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from detail_periksa_lab where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from periksa_lab where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from beri_bhp_radiologi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from periksa_radiologi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from aturan_pakai where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from detail_pemberian_obat where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from tagihan_obat_langsung where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from resep_obat where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from resep_pulang where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from returpasien where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from stok_obat_pasien where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from detail_nota_jalan where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from detail_piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from mutasi_berkas where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from reg_periksa where no_rawat='"+TNoRw.getText()+"'");
            Sequel.AutoComitTrue();
            tampilkasir();
        }
    }//GEN-LAST:event_MnHapusSemuaActionPerformed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgResumePerawatan resume=new DlgResumePerawatan(null,true);
            resume.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString());
            resume.tampil();
            resume.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void ppBerkasBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            Sequel.menyimpan("mutasi_berkas","'"+TNoRw.getText()+"','Sudah Diterima',now(),now(),'0000-00-00 00:00:00','0000-00-00 00:00:00'","status='Sudah Diterima',diterima=now()","no_rawat='"+TNoRw.getText()+"'");
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Berkas Diterima'");
            if(tabModekasir.getRowCount()!=0){tampilkasir();}
        }
    }//GEN-LAST:event_ppBerkasBtnPrintActionPerformed

    private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgRujuk dlgrjk=new DlgRujuk(null,false);
            dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrjk.setLocationRelativeTo(internalFrame1);
            dlgrjk.emptTeks();
            dlgrjk.isCek();
            dlgrjk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
            dlgrjk.tampil();
            dlgrjk.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnRujukActionPerformed

    private void ppCatatanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCatatanPasienBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCatatan catatan=new DlgCatatan(null,true);
            catatan.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString());
            catatan.setSize(720,330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppCatatanPasienBtnPrintActionPerformed

    private void MnDirujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDirujukActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Dirujuk'");
                MnRujukActionPerformed(evt);
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
            
        }
    }//GEN-LAST:event_MnDirujukActionPerformed

    private void MnDIrawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDIrawatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Dirawat'"); 
                MnKamarInapActionPerformed(evt);
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
            
        }
    }//GEN-LAST:event_MnDIrawatActionPerformed

    private void MnMeninggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnMeninggalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Meninggal'");
                DlgPasienMati dlgPasienMati=new DlgPasienMati(null,false);
                dlgPasienMati.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dlgPasienMati.setLocationRelativeTo(internalFrame1);
                dlgPasienMati.emptTeks();
                dlgPasienMati.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString()); 
                dlgPasienMati.isCek();
                dlgPasienMati.setVisible(true);                
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
            
        }
    }//GEN-LAST:event_MnMeninggalActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKasirRalan dialog = new DlgKasirRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn;
    private widget.Button BtnCloseIn1;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan1;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpan5;
    private widget.TextBox CrPoli;
    private widget.TextBox CrPtg;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Jam;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnBatal;
    private javax.swing.JMenuItem MnBayar;
    private javax.swing.JMenuItem MnBelum;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnDIrawat;
    private javax.swing.JMenuItem MnDataPemberianObat;
    private javax.swing.JMenuItem MnDataRalan;
    private javax.swing.JMenuItem MnDiagnosa;
    private javax.swing.JMenuItem MnDirujuk;
    private javax.swing.JMenuItem MnDokter;
    private javax.swing.JMenuItem MnHapusAturanPkaiObat;
    private javax.swing.JMenuItem MnHapusBilling;
    private javax.swing.JMenu MnHapusData;
    private javax.swing.JMenuItem MnHapusDeposit;
    private javax.swing.JMenuItem MnHapusDiagnosa;
    private javax.swing.JMenuItem MnHapusDiet;
    private javax.swing.JMenuItem MnHapusDpjp;
    private javax.swing.JMenuItem MnHapusHemodialisa;
    private javax.swing.JMenuItem MnHapusKamarInap;
    private javax.swing.JMenuItem MnHapusLab;
    private javax.swing.JMenuItem MnHapusObat;
    private javax.swing.JMenuItem MnHapusObatOperasi;
    private javax.swing.JMenuItem MnHapusPemeriksaanRalan;
    private javax.swing.JMenuItem MnHapusPemeriksaanRanap;
    private javax.swing.JMenuItem MnHapusPiutang;
    private javax.swing.JMenuItem MnHapusPotongan;
    private javax.swing.JMenuItem MnHapusProsedur;
    private javax.swing.JMenuItem MnHapusRadiologi;
    private javax.swing.JMenuItem MnHapusRanapGabung;
    private javax.swing.JMenuItem MnHapusResepObat;
    private javax.swing.JMenuItem MnHapusResepPulang;
    private javax.swing.JMenuItem MnHapusReturObat;
    private javax.swing.JMenuItem MnHapusRujukKeluar;
    private javax.swing.JMenuItem MnHapusRujukMasuk;
    private javax.swing.JMenuItem MnHapusSemua;
    private javax.swing.JMenuItem MnHapusStokObatRanap;
    private javax.swing.JMenuItem MnHapusTagihanOperasi;
    private javax.swing.JMenuItem MnHapusTambahan;
    private javax.swing.JMenuItem MnHapusTindakanRalanDokter;
    private javax.swing.JMenuItem MnHapusTindakanRalanDokterParamedis;
    private javax.swing.JMenuItem MnHapusTindakanRalanParamedis;
    private javax.swing.JMenuItem MnHapusTindakanRanapDokter;
    private javax.swing.JMenuItem MnHapusTindakanRanapDokterParamedis;
    private javax.swing.JMenuItem MnHapusTindakanRanapParamedis;
    private javax.swing.JMenuItem MnKamarInap;
    private javax.swing.JMenuItem MnMeninggal;
    private javax.swing.JMenuItem MnNoResep;
    private javax.swing.JMenu MnObat;
    private javax.swing.JMenuItem MnObatLangsung;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenu MnPemeriksaan;
    private javax.swing.JMenuItem MnPenjab;
    private javax.swing.JMenuItem MnPenjualan;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenuItem MnPoli;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenu MnRekap;
    private javax.swing.JMenuItem MnRekapBulananDokter;
    private javax.swing.JMenuItem MnRekapBulananParamedis;
    private javax.swing.JMenuItem MnRekapHarianDokter;
    private javax.swing.JMenuItem MnRekapHarianObat;
    private javax.swing.JMenuItem MnRekapHarianParamedis;
    private javax.swing.JMenuItem MnRekapHarianPoli;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JMenu MnStatus;
    private javax.swing.JMenuItem MnSudah;
    private javax.swing.JMenu MnTindakan;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox TKdPny;
    private widget.TextBox TNoRw;
    private widget.TextBox Tanggal;
    private widget.TextBox TotalObat;
    private javax.swing.JDialog WindowCaraBayar;
    private javax.swing.JDialog WindowGantiDokter;
    private javax.swing.JDialog WindowGantiPoli;
    private javax.swing.JDialog WindowObatBhp;
    private widget.Button btnBayar;
    private widget.Button btnCariDokter;
    private widget.Button btnCariPoli;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel10;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.TextBox kdpenjab;
    private widget.TextBox kdpoli;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppBerkas;
    private javax.swing.JMenuItem ppCatatanPasien;
    private javax.swing.JMenuItem ppRiwayat;
    private widget.Table tbKasirRalan;
    // End of variables declaration//GEN-END:variables

    public void tampilkasir() {                   
        Valid.tabelKosong(tabModekasir);
        try{   
            pskasir=koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts,penjab.png_jawab "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_poli=poliklinik.kd_poli  where  "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_reg like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rawat like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.tgl_registrasi like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.kd_dokter like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  dokter.nm_dokter like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rkm_medis like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  pasien.nm_pasien like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  poliklinik.nm_poli like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.p_jawab like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  penjab.png_jawab like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.almt_pj like ? or "+
                " reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.hubunganpj like ? order by reg_periksa.no_rawat desc");
            try{
                pskasir.setString(1,"%"+CrPoli.getText()+"%");
                pskasir.setString(2,"%"+CrPtg.getText()+"%");
                pskasir.setString(3,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(6,"%"+TCari.getText().trim()+"%");
                pskasir.setString(7,"%"+CrPoli.getText()+"%");
                pskasir.setString(8,"%"+CrPtg.getText()+"%");
                pskasir.setString(9,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(12,"%"+TCari.getText().trim()+"%");
                pskasir.setString(13,"%"+CrPoli.getText()+"%");
                pskasir.setString(14,"%"+CrPtg.getText()+"%");
                pskasir.setString(15,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(18,"%"+TCari.getText().trim()+"%");
                pskasir.setString(19,"%"+CrPoli.getText()+"%");
                pskasir.setString(20,"%"+CrPtg.getText()+"%");
                pskasir.setString(21,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(24,"%"+TCari.getText().trim()+"%");
                pskasir.setString(25,"%"+CrPoli.getText()+"%");
                pskasir.setString(26,"%"+CrPtg.getText()+"%");
                pskasir.setString(27,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(30,"%"+TCari.getText().trim()+"%");
                pskasir.setString(31,"%"+CrPoli.getText()+"%");
                pskasir.setString(32,"%"+CrPtg.getText()+"%");
                pskasir.setString(33,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(36,"%"+TCari.getText().trim()+"%");
                pskasir.setString(37,"%"+CrPoli.getText()+"%");
                pskasir.setString(38,"%"+CrPtg.getText()+"%");
                pskasir.setString(39,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(40,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(41,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(42,"%"+TCari.getText().trim()+"%");
                pskasir.setString(43,"%"+CrPoli.getText()+"%");
                pskasir.setString(44,"%"+CrPtg.getText()+"%");
                pskasir.setString(45,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(46,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(47,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(48,"%"+TCari.getText().trim()+"%");
                pskasir.setString(49,"%"+CrPoli.getText()+"%");
                pskasir.setString(50,"%"+CrPtg.getText()+"%");
                pskasir.setString(51,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(52,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(53,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(54,"%"+TCari.getText().trim()+"%");
                pskasir.setString(55,"%"+CrPoli.getText()+"%");
                pskasir.setString(56,"%"+CrPtg.getText()+"%");
                pskasir.setString(57,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(58,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(59,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(60,"%"+TCari.getText().trim()+"%");
                pskasir.setString(61,"%"+CrPoli.getText()+"%");
                pskasir.setString(62,"%"+CrPtg.getText()+"%");
                pskasir.setString(63,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(64,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(65,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(66,"%"+TCari.getText().trim()+"%");
                pskasir.setString(67,"%"+CrPoli.getText()+"%");
                pskasir.setString(68,"%"+CrPtg.getText()+"%");
                pskasir.setString(69,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(70,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(71,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(72,"%"+TCari.getText().trim()+"%");
                rskasir=pskasir.executeQuery();
                while(rskasir.next()){
                    tabModekasir.addRow(new String[] {rskasir.getString(5),
                                   rskasir.getString(6),
                                   rskasir.getString(7),
                                   rskasir.getString(8),
                                   rskasir.getString(9),
                                   rskasir.getString(10),
                                   rskasir.getString(11),
                                   rskasir.getString(12),
                                   Valid.SetAngka(rskasir.getDouble(13)),
                                   rskasir.getString("png_jawab"),
                                   rskasir.getString(14),
                                   rskasir.getString("no_rawat"),
                                   rskasir.getString("tgl_registrasi"),
                                   rskasir.getString("jam_reg"),rskasir.getString(1)});
                }                
            } catch(Exception e){
                System.out.println("Notifikasi : "+e);
            } finally{
               if(rskasir!=null){
                   rskasir.close();
               } 
               if(pskasir!=null){
                   pskasir.close();
               } 
            }            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModekasir.getRowCount());
    }


    private void getDatakasir() {
        if(tbKasirRalan.getSelectedRow()!= -1){
            TNoRw.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString());
            Tanggal.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),12).toString());
            Jam.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),13).toString());
        }
    }

    public JTextField getTextField(){
        return TNoRw;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        MnRawatJalan.setEnabled(var.gettindakan_ralan());
        MnPemberianObat.setEnabled(var.getberi_obat());
        MnKamarInap.setEnabled(var.getkamar_inap());
        MnPeriksaLab.setEnabled(var.getperiksa_lab());
        MnPeriksaRadiologi.setEnabled(var.getperiksa_radiologi());
        MnOperasi.setEnabled(var.getoperasi());
        MnNoResep.setEnabled(var.getresep_obat());
        MnObatLangsung.setEnabled(var.getberi_obat());
        //MnBilling.setEnabled(var.getbilling_ralan());
        MnSudah.setEnabled(var.getkasir_ralan());
        MnBelum.setEnabled(var.getkasir_ralan());
        MnBayar.setEnabled(var.getkasir_ralan());
        MnDataRalan.setEnabled(var.gettindakan_ralan());
        MnDataPemberianObat.setEnabled(var.getberi_obat());
        MnDokter.setEnabled(var.getkasir_ralan());
        MnPenjab.setEnabled(var.getkasir_ralan());
        MnPenjualan.setEnabled(var.getpenjualan_obat());
        MnRekapHarianDokter.setEnabled(var.getharian_dokter());
        MnRekapHarianParamedis.setEnabled(var.getharian_paramedis());
        MnRekapBulananDokter.setEnabled(var.getbulanan_dokter());
        MnRekapBulananParamedis.setEnabled(var.getbulanan_paramedis());
        MnRekapHarianPoli.setEnabled(var.getharian_tindakan_poli());
        MnRekapHarianObat.setEnabled(var.getobat_per_poli());
        MnDiagnosa.setEnabled(var.getdiagnosa_pasien());   
        ppRiwayat.setEnabled(var.getresume_pasien());   
        if(var.getkode().equals("Admin Utama")){
            MnHapusData.setEnabled(true);
        }else{
            MnHapusData.setEnabled(false);
        } 
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            namadokter=prop.getProperty("DOKTERAKTIFKASIRRALAN");
            namapoli=prop.getProperty("POLIAKTIFKASIRRALAN");
        } catch (Exception ex) {
            namadokter="";
            namapoli="";
        }
        
        if(!namadokter.equals("")){
            if(var.getkode().equals("Admin Utama")){
                CrPtg.setText("");
                BtnSeek3.setEnabled(true);
                CrPtg.setEditable(true);
            }else{
                CrPtg.setText(namadokter);
                BtnSeek3.setEnabled(false);
                CrPtg.setEditable(false);
            }                
        }else{
            BtnSeek3.setEnabled(true);
            CrPtg.setEditable(true);
        }
        
        if(!namapoli.equals("")){
            if(var.getkode().equals("Admin Utama")){
                CrPoli.setText("");
                BtnSeek4.setEnabled(true);
                CrPoli.setEditable(true);
            }else{
                CrPoli.setText(namapoli);
                BtnSeek4.setEnabled(false);
                CrPoli.setEditable(false);
            }                
        }else{
            BtnSeek4.setEnabled(true);
            CrPoli.setEditable(true);
        }
    }
    
}

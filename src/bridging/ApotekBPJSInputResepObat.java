/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package bridging;

import inventory.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable2;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import simrskhanza.DlgCariBangsal;
import widget.Button;

/**
 *
 * @author dosen
 */
public final class ApotekBPJSInputResepObat extends javax.swing.JDialog {
    private final DefaultTableModel tabModeobat,tabModeObatRacikan,tabModeDetailObatRacikan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psobat,psracikan,psstok,ps2,psbatch,psrekening,psobatracikan,psdiagnosa;
    private ResultSet rsobat,rsracikan,rsstok,rs2,rsbatch,rsrekening,rscariobat,rsdiagnosa,rsobatracikan;
    private double h_belicari=0, hargacari=0, sisacari=0,x=0,y=0,embalase=Sequel.cariIsiAngka("select set_embalase.embalase_per_obat from set_embalase"),
                   tuslah=Sequel.cariIsiAngka("select set_embalase.tuslah_per_obat from set_embalase"),kenaikan=0,stokbarang=0,ttl=0,ppnobat=0,ttlhpp,ttljual,Hari=0;
    private int i=0,z=0,row=0,row2,r,subttl;
    private Jurnal jur=new Jurnal();
    private boolean[] pilih; 
    private double[] jumlah,harga,eb,ts,stok,beli,kapasitas,kandungan;
    private String[] kodebarang,namabarang,kodesatuan,letakbarang,namajenis,aturan,industri,kategori,golongan,no,nobatch,nofaktur,kadaluarsa,keterangan,signa_cari1,signa_cari2,signa_racikan1,signa_racikan2;
    private String signa1="1",utc="",pesan="",link=koneksiDB.URLAPIAPOTEKBPJS(),signa2="1",nokunjungan="",kdObatSK="",requestJson="",URL="",otorisasi,sql="",aktifpcare="no",no_batchcari="", tgl_kadaluarsacari="", no_fakturcari="", aktifkanbatch="no",kodedokter="",namadokter="",noresep="",bangsal="",bangsaldefault=Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi limit 1"),tampilkan_ppnobat_ralan="",
        Suspen_Piutang_Obat_Ralan="",Obat_Ralan="",HPP_Obat_Rawat_Jalan="",Persediaan_Obat_Rawat_Jalan="",hppfarmasi="",VALIDASIULANGBERIOBAT="",DEPOAKTIFOBAT="",NORESEPAKTIF="no",diagnosa_pasien, no_claim,ObatRutin,
        sqlpscariobat2="select databarang.nama_brng,jenis.nama,detail_pemberian_obat.biaya_obat, detail_pemberian_obat.jml, detail_pemberian_obat.total, databarang.kode_brng, aturan_pakai.aturan "
                        + "from detail_pemberian_obat inner join databarang inner join jenis on detail_pemberian_obat.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns LEFT JOIN aturan_pakai ON detail_pemberian_obat.no_rawat=aturan_pakai.no_rawat AND "
                        + "databarang.kode_brng=aturan_pakai.kode_brng and detail_pemberian_obat.jam=aturan_pakai.jam where detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.tgl_perawatan=? AND detail_pemberian_obat.jam=? group by detail_pemberian_obat.kode_brng order by jenis.nama";
    private DlgCariBangsal caribangsal=new DlgCariBangsal(null,false);
    public DlgCariAturanPakai aturanpakai=new DlgCariAturanPakai(null,false);
    private DlgCariMetodeRacik metoderacik=new DlgCariMetodeRacik(null,false);
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();
    private WarnaTable2 warna3=new WarnaTable2();
    private riwayatobat Trackobat=new riwayatobat();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private String[] arrSplit;
    private boolean sukses=true;
    private ApotekBPJSCekReferensiDPHO refobatbpjs = new ApotekBPJSCekReferensiDPHO(null, false);
    private ApiApotekBPJS api = new ApiApotekBPJS();
    
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public ApotekBPJSInputResepObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        tabModeobat=new DefaultTableModel(null,new Object[]{
                "K","Jumlah","Kode Barang","Nama Barang","Signa 1","Signa 2","Jumlah Hari"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0) || (colIndex==4) || (colIndex==5) || (colIndex==6)) {
                    a=true;
                }
                return a;
             }
            
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        
        tbObat.setModel(tabModeobat);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                try {
                    ps2=koneksi.prepareStatement("select kode_ppk from setting");

                    try {
                        rs2=ps2.executeQuery();
                        rs2.next();

                        if(rs2.getString("kode_ppk").equals("0090R034")){
                            column.setPreferredWidth(20);
                        }else {
                            column.setMinWidth(0);
                            column.setMaxWidth(0);
                        }

                    } catch (Exception e) {
                        System.out.println("Notif : "+rs2);
                    } finally{
                        rs2.close();  
                    }

                } catch (Exception e) {
                    System.out.println("Notif : "+rs2);
                }
            }else if(i==1){
                column.setPreferredWidth(45);
            }else if(i==2){
                column.setPreferredWidth(75);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(100);
            }            
        }
        warna.kolom=1;
        tbObat.setDefaultRenderer(Object.class,warna);
        
        tabModeObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Nama Racikan","Kode Racik","Metode Racik","Jml.Racik",
                "Aturan Pakai","Keterangan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
//                if ((colIndex==0)||(colIndex==2)||(colIndex==3)) {
//                    a=false;
//                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObatRacikan.setModel(tabModeObatRacikan);
        tbObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(250);
            }
        }

        warna2.kolom=4;
        tbObatRacikan.setDefaultRenderer(Object.class,warna2);
        
        tabModeDetailObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Jumlah","Kode Barang","Nama Barang","Signa 1","Signa 2","Jumlah Hari"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0) || (colIndex==4) || (colIndex==5) || (colIndex==6)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        tbDetailObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbDetailObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(40);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(100);
            }   
        }

        warna3.kolom=9;
        tbDetailObatRacikan.setDefaultRenderer(Object.class,warna3);
        
               
        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturanpakai.getTable().getSelectedRow()!= -1){  
                    if(TabRawat.getSelectedIndex()==0){
                        tbObat.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbObat.getSelectedRow(),11);
                        tbObat.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==1){
                        tbObatRacikan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbObatRacikan.getSelectedRow(),5);
                        tbObatRacikan.requestFocus();
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
        
        metoderacik.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(metoderacik.getTable().getSelectedRow()!= -1){  
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),1).toString(),tbObatRacikan.getSelectedRow(),2);
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),2).toString(),tbObatRacikan.getSelectedRow(),3);
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
        
        metoderacik.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    metoderacik.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        try {
            hppfarmasi=koneksiDB.HPPFARMASI();
        } catch (Exception e) {
            hppfarmasi="dasar";
        }
        
        try {
            VALIDASIULANGBERIOBAT=koneksiDB.VALIDASIULANGBERIOBAT();
        } catch (Exception e) {
            VALIDASIULANGBERIOBAT="no";
        }
        
        try {
            DEPOAKTIFOBAT = koneksiDB.DEPOAKTIFOBAT();
        } catch (Exception e) {
            System.out.println("E : "+e);
            DEPOAKTIFOBAT = "";
        }
        
        try {
            psrekening=koneksi.prepareStatement("select * from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Obat_Ralan=rsrekening.getString("Suspen_Piutang_Obat_Ralan");
                    Obat_Ralan=rsrekening.getString("Obat_Ralan");
                    HPP_Obat_Rawat_Jalan=rsrekening.getString("HPP_Obat_Rawat_Jalan");
                    Persediaan_Obat_Rawat_Jalan=rsrekening.getString("Persediaan_Obat_Rawat_Jalan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        /*try {
            NORESEPAKTIF=koneksiDB.NORESEPAKTIF();
        } catch (Exception e) {
            NORESEPAKTIF="no";
        }
        */
        tampilkan_ppnobat_ralan=Sequel.cariIsi("select set_nota.tampilkan_ppnobat_ralan from set_nota"); 
        jam();
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
        TNoRw = new widget.TextBox();
        Tanggal = new widget.TextBox();
        KdPj = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        jLabel5 = new widget.Label();
        LTotal = new widget.Label();
        jLabel7 = new widget.Label();
        LTotalTagihan = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        CariDataObat = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel8 = new widget.Label();
        Jam = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        TanggalPelayanan = new widget.Tanggal();
        jLabel20 = new widget.Label();
        NmPoli = new widget.TextBox();
        KdPoli = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel15 = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        jLabel14 = new widget.Label();
        TResep = new widget.TextBox();
        Iterasi = new javax.swing.JComboBox<>();
        jLabel16 = new widget.Label();
        JnsObat = new javax.swing.JComboBox<>();
        jLabel17 = new widget.Label();
        jLabel4 = new widget.Label();
        NoKartu = new widget.TextBox();
        NoSEP = new widget.TextBox();
        jLabel18 = new widget.Label();
        TNoRw1 = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel3 = new widget.Label();
        NoSEP1 = new widget.TextBox();
        jLabel19 = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat Apotek BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel5.setText("Total :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi3.add(jLabel5);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(LTotal);

        jLabel7.setText("Total+PPN :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(jLabel7);

        LTotalTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotalTagihan.setText("0");
        LTotalTagihan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotalTagihan.setName("LTotalTagihan"); // NOI18N
        LTotalTagihan.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(LTotalTagihan);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi3.add(BtnHapus);

        CariDataObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        CariDataObat.setToolTipText("");
        CariDataObat.setName("CariDataObat"); // NOI18N
        CariDataObat.setPreferredSize(new java.awt.Dimension(28, 23));
        CariDataObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CariDataObatActionPerformed(evt);
            }
        });
        CariDataObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariDataObatKeyPressed(evt);
            }
        });
        panelisi3.add(CariDataObat);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

        jLabel8.setText("Pelayanan :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel8);
        jLabel8.setBounds(330, 100, 90, 23);

        Jam.setEditable(false);
        Jam.setText("2026-01-01 00:00:00");
        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N
        FormInput.add(Jam);
        Jam.setBounds(400, 40, 130, 24);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-02-2026" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(430, 100, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(520, 100, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(590, 100, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(650, 100, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(720, 100, 22, 23);

        TanggalPelayanan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-02-2026" }));
        TanggalPelayanan.setDisplayFormat("dd-MM-yyyy");
        TanggalPelayanan.setName("TanggalPelayanan"); // NOI18N
        TanggalPelayanan.setOpaque(false);
        TanggalPelayanan.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalPelayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalPelayananActionPerformed(evt);
            }
        });
        TanggalPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPelayananKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPelayanan);
        TanggalPelayanan.setBounds(300, 40, 90, 23);

        jLabel20.setText("Tgl.Resep :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel20);
        jLabel20.setBounds(210, 40, 80, 23);

        NmPoli.setEditable(false);
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(160, 130, 170, 23);

        KdPoli.setEditable(false);
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormInput.add(KdPoli);
        KdPoli.setBounds(84, 130, 75, 23);

        jLabel13.setText("Asal Poli :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 130, 80, 23);

        jLabel15.setText("Dokter DPJP :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 100, 80, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setHighlighter(null);
        KdDPJP.setName("KdDPJP"); // NOI18N
        FormInput.add(KdDPJP);
        KdDPJP.setBounds(84, 100, 75, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setHighlighter(null);
        NmDPJP.setName("NmDPJP"); // NOI18N
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(160, 100, 170, 23);

        jLabel14.setText("No.Resep :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(510, 40, 80, 23);

        TResep.setHighlighter(null);
        TResep.setName("TResep"); // NOI18N
        TResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TResepKeyPressed(evt);
            }
        });
        FormInput.add(TResep);
        TResep.setBounds(590, 40, 145, 23);

        Iterasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Iterasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0. Tanpa Iterasi", "1. Dengan Iterasi" }));
        Iterasi.setName("Iterasi"); // NOI18N
        FormInput.add(Iterasi);
        Iterasi.setBounds(380, 130, 135, 24);

        jLabel16.setText("Iterasi :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(330, 130, 50, 23);

        JnsObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        JnsObat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Obat PRB", "2. Obat Kronis Belum Stabil", "3. Obat Kemoterapi" }));
        JnsObat.setSelectedIndex(1);
        JnsObat.setName("JnsObat"); // NOI18N
        FormInput.add(JnsObat);
        JnsObat.setBounds(530, 70, 185, 24);

        jLabel17.setText("Jenis :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(480, 70, 50, 23);

        jLabel4.setText("No.Kartu :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 40, 80, 23);

        NoKartu.setEditable(false);
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        NoKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoKartuActionPerformed(evt);
            }
        });
        FormInput.add(NoKartu);
        NoKartu.setBounds(84, 40, 140, 23);

        NoSEP.setEditable(false);
        NoSEP.setHighlighter(null);
        NoSEP.setName("NoSEP"); // NOI18N
        FormInput.add(NoSEP);
        NoSEP.setBounds(280, 70, 190, 23);

        jLabel18.setText("No.SEP :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(230, 70, 50, 23);

        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        TNoRw1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRw1KeyPressed(evt);
            }
        });
        FormInput.add(TNoRw1);
        TNoRw1.setBounds(84, 10, 120, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(210, 10, 487, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 80, 23);

        NoSEP1.setEditable(false);
        NoSEP1.setHighlighter(null);
        NoSEP1.setName("NoSEP1"); // NOI18N
        FormInput.add(NoSEP1);
        NoSEP1.setBounds(80, 70, 130, 23);

        jLabel19.setText("Tgl.SEP :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(30, 70, 50, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ScrollMouseClicked(evt);
            }
        });

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbObatPropertyChange(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        TabRawat.addTab("Umum", Scroll);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(454, 90));

        tbObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        tbObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatRacikanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbObatRacikan);

        jPanel3.add(Scroll1, java.awt.BorderLayout.PAGE_START);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDetailObatRacikan.setAutoCreateRowSorter(true);
        tbDetailObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        tbDetailObatRacikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailObatRacikanMouseClicked(evt);
            }
        });
        tbDetailObatRacikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDetailObatRacikanPropertyChange(evt);
            }
        });
        tbDetailObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailObatRacikanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbDetailObatRacikan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Racikan", jPanel3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tbObat.getRowCount()!=0){
            try {
                getDataobat();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                if(akses.getform().equals("DlgPemberianObat")){
                    dispose();
                }
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        
}//GEN-LAST:event_tbObatKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed
    
private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    /*if(TNoRw.getText().trim().equals("")){
        Valid.textKosong(TNoRw,"No Rawat");
    }else if(NoSEP.getText().trim().equals("")){
        Valid.textKosong(NoSEP,"Nomor Sep");                                      
    }else if(KdDPJP.getText().trim().equals("")){
        Valid.textKosong(KdDPJP,"Dokter");                                      
    }else if(KdPoli.getText().trim().equals("")){
        Valid.textKosong(KdPoli,"Poliklinik");                                      
    }else if(TResep.getText().trim().equals("")){
        Valid.textKosong(TResep,"Nomor Resep");                                      
    }else if(Lahir.getText().trim().equals("")){
        Valid.textKosong(Lahir,"Lahir");                                      
    }else{
        int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {  
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("x-cons-id", koneksiDB.CONSIDAPIAPOTEKBPJS());
                utc = String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("x-timestamp", utc);
                headers.add("x-signature", api.getHmac(utc));
                headers.add("user_key", koneksiDB.USERKEYAPIAPOTEKBPJS());
                requestEntity = new HttpEntity(headers);
                
//                cek resep yang sdh ada                
                if (Sequel.cariIsi("select no_sep from bridging_apotek_bpjs where no_sep='"+NoSEP.getText()+"'").isEmpty()) {
                    URL = link + "/sjpresep/v3/insert";
                    System.out.println(URL);
                    requestJson = "{"
//                                    + "\"TGLSJP\": \"" + Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+Jam.getText()+ "\","+
                                    + "\"TGLSJP\": \"" + Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+ "\","
                                    + "\"REFASALSJP\": \"" + NoSEP.getText() + "\","
                                    + "\"POLIRSP\": \"" + KdPoli.getText() + "\","
                                    + "\"KDJNSOBAT\": \"" + JnsObat.getSelectedItem().toString().substring(0, 1) + "\","
                                    + "\"NORESEP\": \"" + TResep.getText()+ "\", "
                                    + "\"IDUSERSJP\": \"RS_" + akses.getkode() + "\","
                                    + "\"TGLRSP\": \"" + Valid.SetTgl(TanggalPelayanan.getSelectedItem()+"")+" 00:00:00\", "
                                    + "\"TGLPELRSP\": \"" + Valid.SetTgl(TanggalPelayanan.getSelectedItem()+"")+" 00:00:00\","
                                    + "\"KdDokter\": \"0\","
                                    + "\"iterasi\":\"" + Iterasi.getSelectedItem().toString().substring(0, 1) + "\""
                                + "}  ";
                    System.out.println("Resep : "+requestJson);
                    requestEntity = new HttpEntity(requestJson, headers);
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    System.out.println("data = "+nameNode);
                    if (nameNode.path("code").asText().equals("200")) {
                        response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc));
                        System.out.println("Response : "+response);
                        if (Sequel.menyimpantf2("bridging_apotek_bpjs", "?,?,?,?,?,?,?,?,?,?,?,?", "data", 12,
                            new String[]{
                                response.path("noSep_Kunjungan").asText(),
                                response.path("noApotik").asText(),
                                TResep.getText(),
                                Valid.SetTgl(TanggalPelayanan.getSelectedItem()+"")+" "+Jam.getText(),
                                Valid.SetTgl(TanggalPelayanan.getSelectedItem()+""),
                                JnsObat.getSelectedItem().toString().substring(0, 1),
                                Iterasi.getSelectedItem().toString().substring(0, 1),
                                KdPoli.getText(),
                                NmPoli.getText(),
                                KdDPJP.getText(),
                                NmDPJP.getText(),
                                akses.getkode(),
                            }) == true) {
                                System.out.println("Simpan No Resep Selesai");
                                JOptionPane.showMessageDialog(null, "Resep Apotek "+response.path("noApotik").asText()+" Berhasil disimpan ");

                                URL = link + "/obatnonracikan/v3/insert";
                                System.out.println(URL);
                                for(i=0;i<tbObat.getRowCount();i++){ 
                                    if(Valid.SetAngka(tbObat.getValueAt(i,1).toString())>0){   
                                        try {

                                            requestJson = "{\n"
                                                    + "            \"NOSJP\": \"" + response.path("noApotik").asText() + "\",\n"
                                                    + "            \"NORESEP\": \"" + TResep.getText() + "\",\n"
                                                    + "            \"KDOBT\": \"" + Sequel.cariIsi("SELECT kode_brng_apotek_bpjs FROM maping_obat_apotek_bpjs WHERE kode_brng=?",tbObat.getValueAt(i,2).toString()) + "\",\n"
                                                    + "            \"NMOBAT\": \"" + Sequel.cariIsi("SELECT nama_brng_apotek_bpjs FROM maping_obat_apotek_bpjs WHERE kode_brng=?",tbObat.getValueAt(i,2).toString()) + "\",\n"
                                                    + "            \"SIGNA1OBT\": " + tbObat.getValueAt(i,4).toString() + ",\n"
                                                    + "            \"SIGNA2OBT\": " + tbObat.getValueAt(i,5).toString() + ",\n"
                                                    + "            \"JMLOBT\": " + tbObat.getValueAt(i,1).toString() + ",\n"
                                                    + "            \"JHO\": " + tbObat.getValueAt(i,6).toString() + ",\n"
                                                    + "            \"CatKhsObt\": \"non racikan\"\n"
                                                    + "        }     ";
                                            System.out.println("Detail Obat : "+requestJson);
                                            requestEntity = new HttpEntity(requestJson, headers);
                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                            nameNode = root.path("metaData");
                                            System.out.println("data = "+nameNode);
                                            if (nameNode.path("code").asText().equals("200")) {
                                                if (Sequel.menyimpantf("bridging_apotek_bpjs_obat", "?,?,?,?,?,?,?,?", "Simpan Obat Apotek BPJS", 8, new String[]{
                                                    response.path("noSep_Kunjungan").asText(),
                                                    TResep.getText(),
                                                    tbObat.getValueAt(i,2).toString(),
                                                    tbObat.getValueAt(i,3).toString(),
                                                    tbObat.getValueAt(i,1).toString(),
                                                    tbObat.getValueAt(i,4).toString(),
                                                    tbObat.getValueAt(i,5).toString(),
                                                    "0"
                                                }) == true) {
                                                    System.out.println("Obat "+tbObat.getValueAt(i,3).toString()+" Berhasil disimpan");
                                                    JOptionPane.showMessageDialog(null, "Obat "+tbObat.getValueAt(i,3).toString()+" Berhasil disimpan");
                                                }
                                            } else {
                                                System.out.println("Obat Gagal Simpan, "+nameNode.path("message").asText());
                                                JOptionPane.showMessageDialog(null, "Obat Gagal Simpan, "+nameNode.path("message").asText());
                                            }

                                            System.out.println("non racikan = \n\n"+requestJson);
                                        } catch (Exception ex) {
                                            System.out.println("Notifikasi : " + ex);
                                            if (ex.toString().contains("UnknownHostException")) {
                                                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                                            }
                                        }            
                                    }
                                }  


    //                            racikan
                                URL = link + "/obatracikan/v3/insert";
                                System.out.println(URL);
                                for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,1).toString())>0){
                                        try {
                                            requestJson = "{\n"
                                                    + "            \"NOSJP\": \"" + Sequel.cariIsi("select no_apotek from bridging_apotek_bpjs where no_sep='"+NoSEP.getText()+"'") + "\",\n"
                                                    + "            \"NORESEP\": \"" + TResep.getText() + "\",\n"
                                                    + "            \"JNSROBT\": \"R.0"+(tbDetailObatRacikan.getValueAt(i,0).toString())+"\",\n"
                                                    + "            \"KDOBT\": \"" + Sequel.cariIsi("SELECT kode_brng_apotek_bpjs FROM maping_obat_apotek_bpjs WHERE kode_brng=?",tbDetailObatRacikan.getValueAt(i,2).toString()) + "\",\n"
                                                    + "            \"NMOBAT\": \"" + Sequel.cariIsi("SELECT nama_brng_apotek_bpjs FROM maping_obat_apotek_bpjs WHERE kode_brng=?",tbDetailObatRacikan.getValueAt(i,2).toString()) + "\",\n"
                                                    + "            \"SIGNA1OBT\": " + tbDetailObatRacikan.getValueAt(i,4).toString() + ",\n"
                                                    + "            \"SIGNA2OBT\": " + tbDetailObatRacikan.getValueAt(i,5).toString() + ",\n"
                                                    + "            \"PERMINTAAN\": " + tbDetailObatRacikan.getValueAt(i,1).toString() + ",\n"
                                                    + "            \"JMLOBT\": " + tbDetailObatRacikan.getValueAt(i,1).toString() + ",\n"
                                                    + "            \"JHO\": " + tbDetailObatRacikan.getValueAt(i,6).toString() + ",\n"
                                                    + "            \"CatKhsObt\": \"RACIKAN "+(i+1)+"\"\n"
                                                    + "        }     ";
                                            requestEntity = new HttpEntity(requestJson, headers);
                                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                            nameNode = root.path("metaData");
                                            System.out.println("data = "+nameNode);
                                            if (nameNode.path("code").asText().equals("200")) {
                                                if (Sequel.menyimpantf("bridging_apotek_bpjs_obat", "?,?,?,?,?,?,?,?", "Simpan Obat Apotek BPJS Racikan", 8, new String[]{
                                                    response.path("noSep_Kunjungan").asText(),
                                                    TResep.getText(),
                                                    tbDetailObatRacikan.getValueAt(i,2).toString(),
                                                    tbDetailObatRacikan.getValueAt(i,3).toString(),
                                                    tbDetailObatRacikan.getValueAt(i,1).toString(),
                                                    tbDetailObatRacikan.getValueAt(i,4).toString(),
                                                    tbDetailObatRacikan.getValueAt(i,5).toString(),
                                                    "1"
                                                }) == true) {
                                                    System.out.println("Obat "+tbDetailObatRacikan.getValueAt(i,3).toString()+" Berhasil disimpan");
                                                    JOptionPane.showMessageDialog(null, "Obat racikan"+tbDetailObatRacikan.getValueAt(i,3).toString()+" Berhasil disimpan");
                                                }
                                            } else {
                                                System.out.println("Obat Gagal Simpan, "+nameNode.path("message").asText());
                                                JOptionPane.showMessageDialog(null, "Obat Gagal Simpan, "+nameNode.path("message").asText());
                                            }

                                            System.out.println("racikan = \n\n"+requestJson);
                                        } catch (Exception ex) {
                                            System.out.println("Notifikasi : " + ex);
                                            if (ex.toString().contains("UnknownHostException")) {
                                                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                                            }
                                        }  
                                    }
                                }
                            }else{
                                JOptionPane.showMessageDialog(rootPane,"Gagal menyimpan resep apotek BPJS !!!!!");
                            }

                            dispose();
                    }else{
                        JOptionPane.showMessageDialog(rootPane, nameNode.path("message").asText());
                    }
                } else {
//                    sdh ada nomor apotek
                    URL = link + "/obatnonracikan/v3/insert";
                    System.out.println(URL);
                    for(i=0;i<tbObat.getRowCount();i++){ 
                        if(Valid.SetAngka(tbObat.getValueAt(i,1).toString())>0){   
                            try {

                                requestJson = "{\n"
                                        + "            \"NOSJP\": \"" + Sequel.cariIsi("select no_apotek from bridging_apotek_bpjs where no_sep='"+NoSEP.getText()+"'") + "\",\n"
                                        + "            \"NORESEP\": \"" + TResep.getText() + "\",\n"
                                        + "            \"KDOBT\": \"" + Sequel.cariIsi("SELECT kode_brng_apotek_bpjs FROM maping_obat_apotek_bpjs WHERE kode_brng=?",tbObat.getValueAt(i,2).toString()) + "\",\n"
                                        + "            \"NMOBAT\": \"" + Sequel.cariIsi("SELECT nama_brng_apotek_bpjs FROM maping_obat_apotek_bpjs WHERE kode_brng=?",tbObat.getValueAt(i,2).toString()) + "\",\n"
                                        + "            \"SIGNA1OBT\": " + tbObat.getValueAt(i,4).toString() + ",\n"
                                        + "            \"SIGNA2OBT\": " + tbObat.getValueAt(i,5).toString() + ",\n"
                                        + "            \"JMLOBT\": " + tbObat.getValueAt(i,1).toString() + ",\n"
                                        + "            \"JHO\": " + tbObat.getValueAt(i,6).toString() + ",\n"
                                        + "            \"CatKhsObt\": \"non racikan\"\n"
                                        + "        }     ";
                                requestEntity = new HttpEntity(requestJson, headers);
                                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                nameNode = root.path("metaData");
                                System.out.println("data = "+nameNode);
                                if (nameNode.path("code").asText().equals("200")) {
                                    if (Sequel.menyimpantf("bridging_apotek_bpjs_obat", "?,?,?,?,?,?,?,?", "Simpan Obat Apotek BPJS", 8, new String[]{
                                        response.path("noSep_Kunjungan").asText(),
                                        TResep.getText(),
                                        tbObat.getValueAt(i,2).toString(),
                                        tbObat.getValueAt(i,3).toString(),
                                        tbObat.getValueAt(i,1).toString(),
                                        tbObat.getValueAt(i,4).toString(),
                                        tbObat.getValueAt(i,5).toString(),
                                        "0"
                                    }) == true) {
                                        System.out.println("Obat "+tbObat.getValueAt(i,3).toString()+" Berhasil disimpan");
                                        JOptionPane.showMessageDialog(null, "Obat "+tbObat.getValueAt(i,3).toString()+" Berhasil disimpan");
                                    }
                                } else {
                                    System.out.println("Obat Gagal Simpan, "+nameNode.path("message").asText());
                                    JOptionPane.showMessageDialog(null, "Obat Gagal Simpan, "+nameNode.path("message").asText());
                                }

                                System.out.println("non racikan = \n\n"+requestJson);
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex);
                                if (ex.toString().contains("UnknownHostException")) {
                                    JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                                }
                            }            
                        }
                    }  

//                            racikan
                    URL = link + "/obatracikan/v3/insert";
                    System.out.println(URL);
                    for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                        if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,1).toString())>0){
                            try {
                                requestJson = "{\n"
                                        + "            \"NOSJP\": \"" + Sequel.cariIsi("select no_apotek from bridging_apotek_bpjs where no_sep='"+NoSEP.getText()+"'") + "\",\n"
                                        + "            \"NORESEP\": \"" + TResep.getText() + "\",\n"
                                        + "            \"JNSROBT\": \"R.0"+(tbDetailObatRacikan.getValueAt(i,0).toString())+"\",\n"
                                        + "            \"KDOBT\": \"" + Sequel.cariIsi("SELECT kode_brng_apotek_bpjs FROM maping_obat_apotek_bpjs WHERE kode_brng=?",tbDetailObatRacikan.getValueAt(i,2).toString()) + "\",\n"
                                        + "            \"NMOBAT\": \"" + Sequel.cariIsi("SELECT nama_brng_apotek_bpjs FROM maping_obat_apotek_bpjs WHERE kode_brng=?",tbDetailObatRacikan.getValueAt(i,2).toString()) + "\",\n"
                                        + "            \"SIGNA1OBT\": " + tbDetailObatRacikan.getValueAt(i,4).toString() + ",\n"
                                        + "            \"SIGNA2OBT\": " + tbDetailObatRacikan.getValueAt(i,5).toString() + ",\n"
                                        + "            \"PERMINTAAN\": " + tbDetailObatRacikan.getValueAt(i,1).toString() + ",\n"
                                        + "            \"JMLOBT\": " + tbDetailObatRacikan.getValueAt(i,1).toString() + ",\n"
                                        + "            \"JHO\": " + tbDetailObatRacikan.getValueAt(i,6).toString() + ",\n"
                                        + "            \"CatKhsObt\": \"RACIKAN "+(i+1)+"\"\n"
                                        + "        }     ";
                                requestEntity = new HttpEntity(requestJson, headers);
                                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                nameNode = root.path("metaData");
                                System.out.println("data = "+nameNode);
                                if (nameNode.path("code").asText().equals("200")) {
                                    if (Sequel.menyimpantf("bridging_apotek_bpjs_obat", "?,?,?,?,?,?,?,?", "Simpan Obat Apotek BPJS Racikan", 8, new String[]{
                                        response.path("noSep_Kunjungan").asText(),
                                        TResep.getText(),
                                        tbDetailObatRacikan.getValueAt(i,2).toString(),
                                        tbDetailObatRacikan.getValueAt(i,3).toString(),
                                        tbDetailObatRacikan.getValueAt(i,1).toString(),
                                        tbDetailObatRacikan.getValueAt(i,4).toString(),
                                        tbDetailObatRacikan.getValueAt(i,5).toString(),
                                        "1"
                                    }) == true) {
                                        System.out.println("Obat "+tbDetailObatRacikan.getValueAt(i,3).toString()+" Berhasil disimpan");
                                        JOptionPane.showMessageDialog(null, "Obat racikan"+tbDetailObatRacikan.getValueAt(i,3).toString()+" Berhasil disimpan");
                                    }
                                } else {
                                    System.out.println("Obat Gagal Simpan, "+nameNode.path("message").asText());
                                    JOptionPane.showMessageDialog(null, "Obat Gagal Simpan, "+nameNode.path("message").asText());
                                }

                                System.out.println("racikan = \n\n"+requestJson);
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : " + ex);
                                if (ex.toString().contains("UnknownHostException")) {
                                    JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                                }
                            }  
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);  
                if (ex.toString().contains("UnknownHostException")) {
                    JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                }
            }
        }                  
    }*/
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void CekObatApotekBPJS(String kode_obat) {
        try {  
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("x-cons-id", koneksiDB.CONSIDAPIAPOTEKBPJS());
                utc = String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("x-timestamp", utc);
                headers.add("x-signature", api.getHmac(utc));
                headers.add("user_key", koneksiDB.USERKEYAPIAPOTEKBPJS());
                requestEntity = new HttpEntity(headers);

                URL = link + "/referensi/obat/"+JnsObat.getSelectedItem().toString().substring(0, 1)+"/"+Valid.SetTgl(TanggalPelayanan.getSelectedItem()+"")+"/"+kode_obat;
     
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");

                if (nameNode.path("code").asText().equals("200")) {
                    response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc));
                    
                }else{
                    JOptionPane.showMessageDialog(rootPane, nameNode.path("message").asText());
                }
            } catch (Exception ex) {
                System.out.println(ex);  
                if (ex.toString().contains("UnknownHostException")) {
                    JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                }
            }
    }
    
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
           
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(noresep.equals("")){
            tampilobat();
        }            
    }//GEN-LAST:event_formWindowOpened

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,BtnKeluar,cmbJam);
    }//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if(this.isVisible()==true){
            getDataobat();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
       
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatRacikanKeyPressed
       
    }//GEN-LAST:event_tbObatRacikanKeyPressed

    private void tbDetailObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanKeyPressed
        
    }//GEN-LAST:event_tbDetailObatRacikanKeyPressed

    private void tbDetailObatRacikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanPropertyChange
        if(this.isVisible()==true){
            getDatadetailobatracikan();
        }
    }//GEN-LAST:event_tbDetailObatRacikanPropertyChange

    private void tbDetailObatRacikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanMouseClicked
        if(tbObat.getRowCount()!=0){
            try {
                getDatadetailobatracikan();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tbDetailObatRacikanMouseClicked

    private void ScrollMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScrollMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ScrollMouseClicked

    private void TanggalPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPelayananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPelayananKeyPressed

    private void TResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TResepKeyPressed

    }//GEN-LAST:event_TResepKeyPressed

    private void NoKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoKartuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoKartuActionPerformed

    private void TanggalPelayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalPelayananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPelayananActionPerformed

    private void CariDataObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CariDataObatActionPerformed
        /*this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ApotekBPJSDaftarPelayananObat2 resume=new ApotekBPJSDaftarPelayananObat2(null,true);
        resume.setNoRm(NoSEP.getText());
        resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        resume.setLocationRelativeTo(internalFrame1);
        resume.setVisible(true);
        resume.tampil();
        this.setCursor(Cursor.getDefaultCursor()); */
    }//GEN-LAST:event_CariDataObatActionPerformed

    private void CariDataObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariDataObatKeyPressed
      
    }//GEN-LAST:event_CariDataObatKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObat.getSelectedRow()!= -1) {
            int reply = JOptionPane.showConfirmDialog(rootPane,"Yakin mau dihapus obat "+tbObat.getValueAt(tbObat.getSelectedRow(), 3)+" ("+tbObat.getValueAt(tbObat.getSelectedRow(), 1)+") ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                tabModeobat.removeRow(tbObat.getSelectedRow());
            }
        }
        
        if (tbDetailObatRacikan.getSelectedRow()!= -1) {
            int reply = JOptionPane.showConfirmDialog(rootPane,"Yakin mau dihapus obat RACIKAN "+tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 3)+" ("+tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 1)+") ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                tabModeDetailObatRacikan.removeRow(tbDetailObatRacikan.getSelectedRow());
            }
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnKeluar, CariDataObat);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void TNoRw1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRw1KeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            TCari.requestFocus();
        }else{
            Valid.pindah(evt,KdDokter,DTPBeri);
        }*/
    }//GEN-LAST:event_TNoRw1KeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ApotekBPJSInputResepObat dialog = new ApotekBPJSInputResepObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button CariDataObat;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private javax.swing.JComboBox<String> Iterasi;
    private widget.TextBox Jam;
    private javax.swing.JComboBox<String> JnsObat;
    private widget.TextBox Kd2;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdPj;
    private widget.TextBox KdPoli;
    private widget.Label LTotal;
    private widget.Label LTotalTagihan;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmPoli;
    private widget.TextBox NoKartu;
    private widget.TextBox NoSEP;
    private widget.TextBox NoSEP1;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw1;
    private widget.TextBox TPasien;
    private widget.TextBox TResep;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox Tanggal;
    private widget.Tanggal TanggalPelayanan;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelisi3;
    private widget.Table tbDetailObatRacikan;
    private widget.Table tbObat;
    private widget.Table tbObatRacikan;
    // End of variables declaration//GEN-END:variables
    public void tampilobat() {        
//        z=0;
//        for(i=0;i<tbObat.getRowCount();i++){
//            if(!tbObat.getValueAt(i,0).toString().equals("")){
//                z++;
//            }
//        }    
//        
//        pilih=null;
//        pilih=new boolean[z]; 
//        jumlah=null;
//        jumlah=new double[z];
//        kodebarang=null;
//        kodebarang=new String[z];
//        namabarang=null;
//        namabarang=new String[z];
//        signa_cari1=new String[z];
//        signa_cari2=new String[z];
//        z=0;        
//        for(i=0;i<tbObat.getRowCount();i++){
//            if(!tbObat.getValueAt(i,1).toString().equals("")){
//                pilih[z]=Boolean.parseBoolean(tbObat.getValueAt(i,0).toString());                
//                try {
//                    jumlah[z]=Double.parseDouble(tbObat.getValueAt(i,1).toString());
//                } catch (Exception e) {
//                    jumlah[z]=0;
//                }  
//                kodebarang[z]=tbObat.getValueAt(i,2).toString();
//                namabarang[z]=tbObat.getValueAt(i,3).toString();
//                
//                if (tbObat.getValueAt(i,1).toString().equals("60")) {
//                    signa_cari1[z]="2";
//                    signa_cari2[z]="1";
//                } else if (tbObat.getValueAt(i,1).toString().equals("90")) {
//                    signa_cari1[z]="3";
//                    signa_cari2[z]="1";  
//                } else if (tbObat.getValueAt(i,1).toString().equals("120")) {
//                    signa_cari1[z]="4";
//                    signa_cari2[z]="1";
//                } else {
//                    signa_cari1[z]="1";
//                    signa_cari2[z]="1";
//                }  
//                    
//                z++;
//            }
//        }
//        
//        Valid.tabelKosong(tabModeobat);             
//        
//        for(i=0;i<z;i++){
//            tabModeobat.addRow(new Object[] {
//                pilih[i],jumlah[i],kodebarang[i],namabarang[i],signa_cari1[i],signa_cari2[i]
//            });
//        }
//        
//        try {
//            psobat=koneksi.prepareStatement(
//                "select databarang.kode_brng, databarang.nama_brng from databarang inner join maping_obat_apotek_bpjs ON databarang.kode_brng=maping_obat_apotek_bpjs.kode_brng WHERE databarang.nama_brng LIKE ? ");
//            try{
//                    
//                psobat.setString(1,"%"+TCari.getText().trim()+"%");
//                
//                rsobat=psobat.executeQuery();
//                    while(rsobat.next()){
//                            tabModeobat.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),"",""
//                        });
//                    }   
//            }catch(Exception e){
//                System.out.println("Notifikasi : "+e);
//            }finally{
//                if(rsobat != null){
//                    rsobat.close();
//                }
//                if(psobat != null){
//                    psobat.close();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Notifikasi : "+e);
//        }            
    }
    
    public void tampildetailracikanobat() {        
//        z=0;
//        for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
//            if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,1).toString())>0){
//                z++;
//            }
//        }    
//        
//        pilih=null;
//        pilih=new boolean[z]; 
//        jumlah=null;
//        jumlah=new double[z];
//        kodebarang=null;
//        kodebarang=new String[z];
//        namabarang=null;
//        namabarang=new String[z];
//        signa_racikan1=null;
//        signa_racikan1=new String[z];
//        signa_racikan2=null;
//        signa_racikan2=new String[z];
//        no=null;
//        no=new String[z];
//        
//        z=0;    
//        
//        for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
//            if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,1).toString())>0){
//                no[z]=tbDetailObatRacikan.getValueAt(i,0).toString();
//                jumlah[z]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,1).toString());
//                kodebarang[z]=tbDetailObatRacikan.getValueAt(i,2).toString();
//                namabarang[z]=tbDetailObatRacikan.getValueAt(i,3).toString();
//                if (tbDetailObatRacikan.getValueAt(i,1).equals("60")) {
//                    signa_racikan1[z]="2";
//                    signa_racikan2[z]="1";
//                } else if (tbDetailObatRacikan.getValueAt(i,1).equals("90")) {
//                    signa_racikan1[z]="3";
//                    signa_racikan2[z]="1";
//                } else if (tbDetailObatRacikan.getValueAt(i,1).equals("120")) {
//                    signa_racikan1[z]="4";
//                    signa_racikan2[z]="1";
//                } else {
//                   signa_racikan1[z]="1";
//                   signa_racikan2[z]="1"; 
//                }
//                
//                z++;
//            }
//        }
//        
//        Valid.tabelKosong(tabModeDetailObatRacikan);             
//        
//        for(i=0;i<z;i++){
//            tabModeDetailObatRacikan.addRow(new Object[] {
//                no[i],jumlah[i],kodebarang[i],namabarang[i],signa_racikan1[i],signa_racikan2[i]
//            });
//        }
//        
//        try{
//            psobat=koneksi.prepareStatement(
//                "select databarang.kode_brng, databarang.nama_brng from databarang inner join maping_obat_apotek_bpjs ON databarang.kode_brng=maping_obat_apotek_bpjs.kode_brng WHERE databarang.nama_brng LIKE ? ");
//
//            try{
//                psobat.setString(1,"%"+TCari.getText().trim()+"%");
//
//                rsobat=psobat.executeQuery();
//                    while(rsobat.next()){
//                        if (rootPaneCheckingEnabled) {
//                            
//                        }
//                        tabModeDetailObatRacikan.addRow(new Object[] {tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),"",""});
//                    }   
//            }catch(Exception e){
//                System.out.println("Notifikasi : "+e);
//            }finally{
//                if(rsobat != null){
//                    rsobat.close();
//                }
//                if(psobat != null){
//                    psobat.close();
//                }
//            } 
//        } catch (Exception e) {
//            System.out.println("Notifikasi : "+e);
//        } 
    }
    
    public void tampilobat2(String no_resep) {     
        this.noresep=no_resep; 
        try {
            Valid.tabelKosong(tabModeobat);
            Valid.tabelKosong(tabModeObatRacikan);
            Valid.tabelKosong(tabModeDetailObatRacikan);
            
            ps2=koneksi.prepareStatement(
                "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam, resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter from resep_obat inner join reg_periksa inner join pasien inner join dokter on "+
                "resep_obat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter where resep_obat.no_resep=?");

            try {
                ps2.setString(1,no_resep);
                rs2=ps2.executeQuery();
                
                while (rs2.next()) { 
                    
                    psobat=koneksi.prepareStatement(
                        "select databarang.kode_brng,databarang.nama_brng,detail_pemberian_obat.jml from detail_pemberian_obat inner join maping_obat_apotek_bpjs on detail_pemberian_obat.kode_brng=maping_obat_apotek_bpjs.kode_brng inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? "+
                        "and detail_pemberian_obat.no_rawat=? and databarang.kode_brng not in (select detail_obat_racikan.kode_brng from detail_obat_racikan where detail_obat_racikan.tgl_perawatan=? and detail_obat_racikan.jam=? and detail_obat_racikan.no_rawat=?) order by databarang.kode_brng");
                    try{
                        psobat.setString(1,rs2.getString("tgl_perawatan"));
                        psobat.setString(2,rs2.getString("jam"));
                        psobat.setString(3,rs2.getString("no_rawat"));
                        psobat.setString(4,rs2.getString("tgl_perawatan"));
                        psobat.setString(5,rs2.getString("jam"));
                        psobat.setString(6,rs2.getString("no_rawat"));
                        rsobat=psobat.executeQuery();
                        while(rsobat.next()){
                            if (rsobat.getString("jml").equals("15")) {
                                tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),1,0.5,30}); 
                            } else if (rsobat.getString("jml").equals("45")) {
                                tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),1,1.5,30}); 
                            } else if (rsobat.getString("jml").equals("60")) {
                                tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),2,1,30}); 
                            } else if (rsobat.getString("jml").equals("90")) {
                                tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),3,1,30}); 
                            } else if (rsobat.getString("jml").equals("120")) {
                                tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),4,1,30}); 
                            } else {
                                tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),1,1,30}); 
                            }            
                        }
                    }catch(Exception e){
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rsobat != null){
                            rsobat.close();
                        }

                        if(psobat != null){
                            psobat.close();
                        }
                    }

                    for(i=0;i<tbObat.getRowCount();i++){
                        getDataobat(i);
                    }
                    
//                    racikan
                    psracikan=koneksi.prepareStatement(
                            "select obat_racikan.no_racik,obat_racikan.nama_racik,"+
                            "obat_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                            "obat_racikan.jml_dr,obat_racikan.aturan_pakai,"+
                            "obat_racikan.keterangan from obat_racikan inner join metode_racik "+
                            "on obat_racikan.kd_racik=metode_racik.kd_racik where "+
                            "obat_racikan.tgl_perawatan=? and obat_racikan.jam=? "+
                            "and obat_racikan.no_rawat=? ");
                    try {
                        psracikan.setString(1,rs2.getString("tgl_perawatan"));
                        psracikan.setString(2,rs2.getString("jam"));
                        psracikan.setString(3,rs2.getString("no_rawat"));
                        rsracikan=psracikan.executeQuery();
                        while(rsracikan.next()){
                            tabModeObatRacikan.addRow(new String[]{
                                rsracikan.getString("no_racik"),rsracikan.getString("nama_racik"),rsracikan.getString("kd_racik"),
                                rsracikan.getString("metode"),rsracikan.getString("jml_dr"),rsracikan.getString("aturan_pakai"),
                                rsracikan.getString("keterangan")
                            });
                            
                            psobat=koneksi.prepareStatement(
                                "select databarang.kode_brng,databarang.nama_brng,detail_pemberian_obat.jml from "+
                                "detail_pemberian_obat inner join maping_obat_apotek_bpjs on detail_pemberian_obat.kode_brng=maping_obat_apotek_bpjs.kode_brng inner join "+
                                "databarang inner join detail_obat_racikan "+
                                "on detail_pemberian_obat.kode_brng=databarang.kode_brng and "+
                                "detail_pemberian_obat.kode_brng=detail_obat_racikan.kode_brng and "+
                                "detail_pemberian_obat.tgl_perawatan=detail_obat_racikan.tgl_perawatan and "+
                                "detail_pemberian_obat.jam=detail_obat_racikan.jam and "+
                                "detail_pemberian_obat.no_rawat=detail_obat_racikan.no_rawat "+
                                "where detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? and "+
                                "detail_pemberian_obat.no_rawat=? and detail_obat_racikan.no_racik=? order by databarang.kode_brng");
                            try {
                                psobat.setString(1,rs2.getString("tgl_perawatan"));
                                psobat.setString(2,rs2.getString("jam"));
                                psobat.setString(3,rs2.getString("no_rawat"));
                                psobat.setString(4,rsracikan.getString("no_racik"));
                                rsobat=psobat.executeQuery();
                                
                                while(rsobat.next()){
                                    if (rsobat.getString("jml").equals("15")) {
                                        tabModeDetailObatRacikan.addRow(new Object[] {rsracikan.getString("no_racik"),rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),1,0.5,30}); 
                                    } else if (rsobat.getString("jml").equals("45")) {
                                        tabModeDetailObatRacikan.addRow(new Object[] {rsracikan.getString("no_racik"),rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),1,1.5,30}); 
                                    } else if (rsobat.getString("jml").equals("60")) {
                                        tabModeDetailObatRacikan.addRow(new Object[] {rsracikan.getString("no_racik"),rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),2,1,30}); 
                                    } else if (rsobat.getString("jml").equals("90")) {
                                        tabModeDetailObatRacikan.addRow(new Object[] {rsracikan.getString("no_racik"),rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),3,1,30}); 
                                    } else if (rsobat.getString("jml").equals("120")) {
                                        tabModeDetailObatRacikan.addRow(new Object[] {rsracikan.getString("no_racik"),rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),4,1,30}); 
                                    } else {
                                        tabModeDetailObatRacikan.addRow(new Object[] {rsracikan.getString("no_racik"),rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),1,1,30}); 
                                    }  
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi Detail Racikan : "+e);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif Racikan : "+e);
                    } finally{
                        if(rsracikan!=null){
                            rsracikan.close();
                        }
                        if(psracikan!=null){
                            psracikan.close();
                        }
                    }

                    for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
                        getDatadetailobatracikan(i);
                    }
                }
            } catch (Exception e) {
                 System.out.println("Notifikasi racikan : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(rs2!=null){
                    rs2.close();
                }
            }
            
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }            
    }

    public void emptTeksobat() {
        Kd2.setText(""); 
    }

    private void getDataobat() {
        if(tbObat.getSelectedRow()!= -1){
            row=tbObat.getSelectedRow();
            if(!tbObat.getValueAt(row,1).toString().equals("")){
                if(Double.parseDouble(tbObat.getValueAt(row,1).toString())>0){
                    Double.parseDouble(tbObat.getValueAt(row,1).toString());
                } 
            }
        }            
    }
    
    private void getDataobat(int data) {        
        Double.parseDouble(tbObat.getValueAt(data,1).toString());
    }

    public JTextField getTextField(){
        return Kd2;
    }

    public JTable getTable(){
        return tbObat;
    }
    
    public Button getButton(){
        return BtnSimpan;
    }
    

    
    public void setNoRm(String norwt,String norm,String nama,String tanggal, String jam, String Resep) {        
        /*aktifpcare="no";
        TNoRw.setText(norwt);
        LblNoRawat.setText(norwt);
        TNoRM.setText(norm);
        TPasien.setText(nama);
        noresep="";
        Valid.SetTgl(TanggalPelayanan,tanggal);
        Jam.setText(jam);  
        KdPj.setText(Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",norwt));
        kenaikan=Sequel.cariIsiAngka("select (set_harga_obat_ralan.hargajual/100) from set_harga_obat_ralan where set_harga_obat_ralan.kd_pj=?",KdPj.getText());
        TResep.setText(Resep);
        
        try{     
            ps2=koneksi.prepareStatement("SELECT no_sep, tanggal_lahir, no_kartu, tglsep, kdpolitujuan, nmpolitujuan, kddpjp, nmdpdjp from bridging_sep where no_rawat = ?");
            try {
                ps2.setString(1,norwt);
                rs2=ps2.executeQuery();
                while(rs2.next()){
                    NoSEP.setText(rs2.getString("no_sep"));
                    KdDPJP.setText(rs2.getString("kddpjp"));
                    NmDPJP.setText(rs2.getString("nmdpdjp"));
                    NoKartu.setText(rs2.getString("no_kartu"));
                    KdPoli.setText(rs2.getString("kdpolitujuan"));
                    NmPoli.setText(rs2.getString("nmpolitujuan"));
                    Lahir.setText(rs2.getString("tanggal_lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }*/
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    public void setDokter(String kodedokter,String namadokter){
        this.kodedokter=kodedokter;
        this.namadokter=namadokter;
    }
    
    private void getDatadetailobatracikan() {
        if(tbDetailObatRacikan.getSelectedRow()!= -1){
            row=tbDetailObatRacikan.getSelectedRow();
            try {
                if(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,1).toString())>0){
                    Double.parseDouble(tbDetailObatRacikan.getValueAt(row,1).toString());
                } 
            } catch (Exception e) {
//                System.out.println("Notif Racikan : "+e);
            }   
        }
    }
    
    private void getDatadetailobatracikan(int data) {       
       Double.parseDouble(tbDetailObatRacikan.getValueAt(data,1).toString());
    }
}

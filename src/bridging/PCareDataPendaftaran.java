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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import simrskhanza.DlgKamarInap;


/**
 *
 * @author perpustakaan
 */
public final class PCareDataPendaftaran extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=1,reply=0;
    private final Properties prop = new Properties();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd"); 
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public PCareDataPendaftaran(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Tgl.SEP","Tgl.Rujukan", 
                "No.Rujukan","Kode PPK Rujukan","Nama PPK Rujukan","Kode PPK Pelayanan", 
                "Nama PPK Pelayanan","Jenis Layanan","Catatan", "Kode Diagnosa", 
                "Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat", "Laka Lantas", 
                "User Input","Tgl.Lahir","Peserta","J.K","No.Kartu","Tanggal Pulang",
                "Asal Rujukan","Eksekutif","COB","Penjamin","No.Telp","Katarak",
                "Tanggal KKL","Keterangan KKL","Suplesi","No.SEP Suplesi","Kd Prop",
                "Propinsi","Kd Kab","Kabupaten","Kd Kec","Kecamatan","No.SKDP",
                "Kd DPJP","DPJP"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 44; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setPreferredWidth(125);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==20){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(25);
            }else if(i==23){
                column.setPreferredWidth(90);
            }else if(i==24){
                column.setPreferredWidth(120);
            }else if(i==25){
                column.setPreferredWidth(80);
            }else if(i==26){
                column.setPreferredWidth(60);
            }else if(i==27){
                column.setPreferredWidth(60);
            }else if(i==28){
                column.setPreferredWidth(130);
            }else if(i==29){
                column.setPreferredWidth(85);
            }else if(i==30){
                column.setPreferredWidth(55);
            }else if(i==31){
                column.setPreferredWidth(70);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(55);
            }else if(i==34){
                column.setPreferredWidth(120);
            }else if(i==35){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==36){
                column.setPreferredWidth(135);
            }else if(i==37){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==38){
                column.setPreferredWidth(135);
            }else if(i==39){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==40){
                column.setPreferredWidth(135);
            }else if(i==41){
                column.setPreferredWidth(60);
            }else if(i==42){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==43){
                column.setPreferredWidth(135);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        }  
        
        
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        NoKartu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TanggalRujuk = new widget.Tanggal();
        jLabel14 = new widget.Label();
        Catatan = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel18 = new widget.Label();
        JK = new widget.TextBox();
        jLabel24 = new widget.Label();
        JenisPeserta = new widget.TextBox();
        jLabel25 = new widget.Label();
        Status = new widget.TextBox();
        jLabel27 = new widget.Label();
        AsalRujukan = new widget.ComboBox();
        AsalRujukan1 = new widget.ComboBox();
        jLabel28 = new widget.Label();
        LabelPoli = new widget.Label();
        KdPoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        btnPoli = new widget.Button();
        LabelPoli2 = new widget.Label();
        jLabel15 = new widget.Label();
        Catatan2 = new widget.TextBox();
        Catatan3 = new widget.TextBox();
        jLabel16 = new widget.Label();
        LabelPoli3 = new widget.Label();
        jLabel17 = new widget.Label();
        Catatan4 = new widget.TextBox();
        Catatan5 = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel29 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        Catatan6 = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        Catatan7 = new widget.TextBox();
        jLabel41 = new widget.Label();
        chkTNI = new widget.CekBox();
        jLabel26 = new widget.Label();
        TanggalRujuk1 = new widget.Tanggal();
        LabelPoli4 = new widget.Label();
        KdPoli1 = new widget.TextBox();
        NmPoli1 = new widget.TextBox();
        btnPoli1 = new widget.Button();
        jLabel30 = new widget.Label();
        Catatan1 = new widget.TextBox();
        LabelPoli5 = new widget.Label();
        KdPoli2 = new widget.TextBox();
        NmPoli2 = new widget.TextBox();
        btnPoli2 = new widget.Button();
        TanggalRujuk2 = new widget.Tanggal();
        jLabel31 = new widget.Label();
        LabelPoli6 = new widget.Label();
        KdPoli3 = new widget.TextBox();
        NmPoli3 = new widget.TextBox();
        btnPoli3 = new widget.Button();
        LabelPoli7 = new widget.Label();
        KdPoli4 = new widget.TextBox();
        NmPoli4 = new widget.TextBox();
        btnPoli4 = new widget.Button();
        LabelPoli8 = new widget.Label();
        KdPoli5 = new widget.TextBox();
        NmPoli5 = new widget.TextBox();
        btnPoli5 = new widget.Button();
        LabelPoli9 = new widget.Label();
        KdPoli6 = new widget.TextBox();
        NmPoli6 = new widget.TextBox();
        btnPoli6 = new widget.Button();
        KdPoli7 = new widget.TextBox();
        NmPoli7 = new widget.TextBox();
        btnPoli7 = new widget.Button();
        jLabel32 = new widget.Label();
        TanggalRujuk3 = new widget.Tanggal();
        LabelPoli12 = new widget.Label();
        KdPoli8 = new widget.TextBox();
        NmPoli8 = new widget.TextBox();
        btnPoli8 = new widget.Button();
        chkTNI1 = new widget.CekBox();
        chkTNI2 = new widget.CekBox();
        chkTNI3 = new widget.CekBox();
        KdPoli9 = new widget.TextBox();
        NmPoli9 = new widget.TextBox();
        btnPoli9 = new widget.Button();
        LabelPoli10 = new widget.Label();
        KdPoli10 = new widget.TextBox();
        NmPoli10 = new widget.TextBox();
        btnPoli10 = new widget.Button();
        chkTNI4 = new widget.CekBox();
        KdPoli11 = new widget.TextBox();
        NmPoli11 = new widget.TextBox();
        btnPoli11 = new widget.Button();
        btnPoli12 = new widget.Button();
        NmPoli12 = new widget.TextBox();
        KdPoli12 = new widget.TextBox();
        LabelPoli11 = new widget.Label();
        jLabel33 = new widget.Label();
        Catatan8 = new widget.TextBox();
        jLabel34 = new widget.Label();
        AsalRujukan2 = new widget.ComboBox();
        jLabel35 = new widget.Label();
        AsalRujukan3 = new widget.ComboBox();
        internalFrame4 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pendaftaran PCare ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(100, 80, 80))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)));
        TabRawat.setForeground(new java.awt.Color(100, 80, 80));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 257));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 12, 90, 23);

        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(93, 12, 152, 23);

        TPasien.setEditable(false);
        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(359, 12, 368, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(247, 12, 110, 23);

        jLabel5.setText("No.Kartu :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 72, 90, 23);

        NoKartu.setEditable(false);
        NoKartu.setBackground(new java.awt.Color(245, 250, 240));
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        FormInput.add(NoKartu);
        NoKartu.setBounds(93, 72, 152, 23);

        jLabel22.setText("Tgl.Daftar :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 102, 90, 23);

        TanggalRujuk.setEditable(false);
        TanggalRujuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "31-10-2018" }));
        TanggalRujuk.setDisplayFormat("dd-MM-yyyy");
        TanggalRujuk.setName("TanggalRujuk"); // NOI18N
        TanggalRujuk.setOpaque(false);
        TanggalRujuk.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujukKeyPressed(evt);
            }
        });
        FormInput.add(TanggalRujuk);
        TanggalRujuk.setBounds(93, 102, 95, 23);

        jLabel14.setText("Keluhan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(384, 132, 80, 23);

        Catatan.setHighlighter(null);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        FormInput.add(Catatan);
        Catatan.setBounds(467, 132, 260, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 42, 90, 23);

        TglLahir.setEditable(false);
        TglLahir.setBackground(new java.awt.Color(245, 250, 240));
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(93, 42, 152, 23);

        jLabel18.setText("J.K.:");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(504, 42, 70, 23);

        JK.setEditable(false);
        JK.setBackground(new java.awt.Color(245, 250, 240));
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(577, 42, 150, 23);

        jLabel24.setText("Peserta :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel24);
        jLabel24.setBounds(278, 42, 60, 23);

        JenisPeserta.setEditable(false);
        JenisPeserta.setBackground(new java.awt.Color(245, 250, 240));
        JenisPeserta.setHighlighter(null);
        JenisPeserta.setName("JenisPeserta"); // NOI18N
        FormInput.add(JenisPeserta);
        JenisPeserta.setBounds(341, 42, 150, 23);

        jLabel25.setText("Status :");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel25);
        jLabel25.setBounds(278, 72, 60, 23);

        Status.setEditable(false);
        Status.setBackground(new java.awt.Color(245, 250, 240));
        Status.setHighlighter(null);
        Status.setName("Status"); // NOI18N
        FormInput.add(Status);
        Status.setBounds(341, 72, 150, 23);

        jLabel27.setText("Jenis Kunjungan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(230, 102, 108, 23);

        AsalRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kunjungan Sakit", "Kunjungan Sehat" }));
        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.setOpaque(false);
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan);
        AsalRujukan.setBounds(341, 102, 150, 23);

        AsalRujukan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10 Rawat Jalan", "20 Rawat Inap", "50 Promotif Preventif" }));
        AsalRujukan1.setName("AsalRujukan1"); // NOI18N
        AsalRujukan1.setOpaque(false);
        AsalRujukan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukan1KeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan1);
        AsalRujukan1.setBounds(577, 102, 150, 23);

        jLabel28.setText("Perawatan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(504, 102, 70, 23);

        LabelPoli.setText("Pemeriksaan Fisik :");
        LabelPoli.setName("LabelPoli"); // NOI18N
        FormInput.add(LabelPoli);
        LabelPoli.setBounds(0, 162, 110, 23);

        KdPoli.setEditable(false);
        KdPoli.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormInput.add(KdPoli);
        KdPoli.setBounds(93, 132, 75, 23);

        NmPoli.setEditable(false);
        NmPoli.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(170, 132, 180, 23);

        btnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli.setMnemonic('X');
        btnPoli.setToolTipText("Alt+X");
        btnPoli.setName("btnPoli"); // NOI18N
        btnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliActionPerformed(evt);
            }
        });
        btnPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoliKeyPressed(evt);
            }
        });
        FormInput.add(btnPoli);
        btnPoli.setBounds(350, 132, 28, 23);

        LabelPoli2.setText("Poli Tujuan :");
        LabelPoli2.setName("LabelPoli2"); // NOI18N
        FormInput.add(LabelPoli2);
        LabelPoli2.setBounds(0, 132, 90, 23);

        jLabel15.setText("Tinggi Badan :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(37, 182, 90, 23);

        Catatan2.setHighlighter(null);
        Catatan2.setName("Catatan2"); // NOI18N
        Catatan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan2KeyPressed(evt);
            }
        });
        FormInput.add(Catatan2);
        Catatan2.setBounds(130, 182, 60, 23);

        Catatan3.setHighlighter(null);
        Catatan3.setName("Catatan3"); // NOI18N
        Catatan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan3KeyPressed(evt);
            }
        });
        FormInput.add(Catatan3);
        Catatan3.setBounds(130, 212, 60, 23);

        jLabel16.setText("Berat Badan :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(37, 212, 90, 23);

        LabelPoli3.setText("Tekanan Darah :");
        LabelPoli3.setName("LabelPoli3"); // NOI18N
        FormInput.add(LabelPoli3);
        LabelPoli3.setBounds(210, 162, 110, 23);

        jLabel17.setText("Sistole :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(247, 182, 90, 23);

        Catatan4.setHighlighter(null);
        Catatan4.setName("Catatan4"); // NOI18N
        Catatan4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan4KeyPressed(evt);
            }
        });
        FormInput.add(Catatan4);
        Catatan4.setBounds(340, 182, 60, 23);

        Catatan5.setHighlighter(null);
        Catatan5.setName("Catatan5"); // NOI18N
        Catatan5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan5KeyPressed(evt);
            }
        });
        FormInput.add(Catatan5);
        Catatan5.setBounds(340, 212, 60, 23);

        jLabel20.setText("Diastole :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(247, 212, 90, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(410, 212, 40, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("cm");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(200, 182, 30, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("kg");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(200, 212, 30, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("mmHg");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(410, 182, 40, 23);

        jLabel38.setText("Respiratory Rate :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(464, 162, 110, 23);

        Catatan6.setHighlighter(null);
        Catatan6.setName("Catatan6"); // NOI18N
        Catatan6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan6KeyPressed(evt);
            }
        });
        FormInput.add(Catatan6);
        Catatan6.setBounds(577, 162, 60, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("per minute");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(650, 162, 80, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("bpm");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(650, 192, 80, 23);

        Catatan7.setHighlighter(null);
        Catatan7.setName("Catatan7"); // NOI18N
        Catatan7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan7KeyPressed(evt);
            }
        });
        FormInput.add(Catatan7);
        Catatan7.setBounds(577, 192, 60, 23);

        jLabel41.setText("Heart Rate :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(464, 192, 110, 23);

        chkTNI.setText("Subspesilias :");
        chkTNI.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkTNI.setName("chkTNI"); // NOI18N
        chkTNI.setOpaque(false);
        chkTNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTNIActionPerformed(evt);
            }
        });
        FormInput.add(chkTNI);
        chkTNI.setBounds(7, 440, 120, 23);

        jLabel26.setText("Tgl.Kunjungan :");
        jLabel26.setName("jLabel26"); // NOI18N
        jLabel26.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel26);
        jLabel26.setBounds(30, 270, 97, 23);

        TanggalRujuk1.setEditable(false);
        TanggalRujuk1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujuk1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "31-10-2018" }));
        TanggalRujuk1.setDisplayFormat("dd-MM-yyyy");
        TanggalRujuk1.setName("TanggalRujuk1"); // NOI18N
        TanggalRujuk1.setOpaque(false);
        TanggalRujuk1.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujuk1KeyPressed(evt);
            }
        });
        FormInput.add(TanggalRujuk1);
        TanggalRujuk1.setBounds(130, 270, 90, 23);

        LabelPoli4.setText("Kesadaran :");
        LabelPoli4.setName("LabelPoli4"); // NOI18N
        FormInput.add(LabelPoli4);
        LabelPoli4.setBounds(30, 300, 97, 23);

        KdPoli1.setEditable(false);
        KdPoli1.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli1.setHighlighter(null);
        KdPoli1.setName("KdPoli1"); // NOI18N
        FormInput.add(KdPoli1);
        KdPoli1.setBounds(130, 300, 50, 23);

        NmPoli1.setEditable(false);
        NmPoli1.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli1.setHighlighter(null);
        NmPoli1.setName("NmPoli1"); // NOI18N
        FormInput.add(NmPoli1);
        NmPoli1.setBounds(182, 300, 170, 23);

        btnPoli1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli1.setMnemonic('X');
        btnPoli1.setToolTipText("Alt+X");
        btnPoli1.setName("btnPoli1"); // NOI18N
        btnPoli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli1ActionPerformed(evt);
            }
        });
        btnPoli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli1KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli1);
        btnPoli1.setBounds(354, 300, 28, 23);

        jLabel30.setText("Terapi :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(30, 330, 97, 23);

        Catatan1.setHighlighter(null);
        Catatan1.setName("Catatan1"); // NOI18N
        Catatan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan1KeyPressed(evt);
            }
        });
        FormInput.add(Catatan1);
        Catatan1.setBounds(130, 330, 252, 23);

        LabelPoli5.setText("Status Pulang :");
        LabelPoli5.setName("LabelPoli5"); // NOI18N
        FormInput.add(LabelPoli5);
        LabelPoli5.setBounds(30, 360, 97, 23);

        KdPoli2.setEditable(false);
        KdPoli2.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli2.setHighlighter(null);
        KdPoli2.setName("KdPoli2"); // NOI18N
        FormInput.add(KdPoli2);
        KdPoli2.setBounds(130, 360, 50, 23);

        NmPoli2.setEditable(false);
        NmPoli2.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli2.setHighlighter(null);
        NmPoli2.setName("NmPoli2"); // NOI18N
        FormInput.add(NmPoli2);
        NmPoli2.setBounds(182, 360, 170, 23);

        btnPoli2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli2.setMnemonic('X');
        btnPoli2.setToolTipText("Alt+X");
        btnPoli2.setName("btnPoli2"); // NOI18N
        btnPoli2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli2ActionPerformed(evt);
            }
        });
        btnPoli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli2KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli2);
        btnPoli2.setBounds(354, 360, 28, 23);

        TanggalRujuk2.setEditable(false);
        TanggalRujuk2.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujuk2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "31-10-2018" }));
        TanggalRujuk2.setDisplayFormat("dd-MM-yyyy");
        TanggalRujuk2.setName("TanggalRujuk2"); // NOI18N
        TanggalRujuk2.setOpaque(false);
        TanggalRujuk2.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujuk2KeyPressed(evt);
            }
        });
        FormInput.add(TanggalRujuk2);
        TanggalRujuk2.setBounds(293, 270, 90, 23);

        jLabel31.setText("Tgl.Pulang :");
        jLabel31.setName("jLabel31"); // NOI18N
        jLabel31.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel31);
        jLabel31.setBounds(220, 270, 70, 23);

        LabelPoli6.setText("Tenaga Medis :");
        LabelPoli6.setName("LabelPoli6"); // NOI18N
        FormInput.add(LabelPoli6);
        LabelPoli6.setBounds(380, 270, 94, 23);

        KdPoli3.setEditable(false);
        KdPoli3.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli3.setHighlighter(null);
        KdPoli3.setName("KdPoli3"); // NOI18N
        FormInput.add(KdPoli3);
        KdPoli3.setBounds(477, 270, 50, 23);

        NmPoli3.setEditable(false);
        NmPoli3.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli3.setHighlighter(null);
        NmPoli3.setName("NmPoli3"); // NOI18N
        FormInput.add(NmPoli3);
        NmPoli3.setBounds(528, 270, 170, 23);

        btnPoli3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli3.setMnemonic('X');
        btnPoli3.setToolTipText("Alt+X");
        btnPoli3.setName("btnPoli3"); // NOI18N
        btnPoli3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli3ActionPerformed(evt);
            }
        });
        btnPoli3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli3KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli3);
        btnPoli3.setBounds(700, 270, 28, 23);

        LabelPoli7.setText("Diganosa 1 :");
        LabelPoli7.setName("LabelPoli7"); // NOI18N
        FormInput.add(LabelPoli7);
        LabelPoli7.setBounds(380, 300, 94, 23);

        KdPoli4.setEditable(false);
        KdPoli4.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli4.setHighlighter(null);
        KdPoli4.setName("KdPoli4"); // NOI18N
        FormInput.add(KdPoli4);
        KdPoli4.setBounds(477, 300, 50, 23);

        NmPoli4.setEditable(false);
        NmPoli4.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli4.setHighlighter(null);
        NmPoli4.setName("NmPoli4"); // NOI18N
        FormInput.add(NmPoli4);
        NmPoli4.setBounds(528, 300, 170, 23);

        btnPoli4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli4.setMnemonic('X');
        btnPoli4.setToolTipText("Alt+X");
        btnPoli4.setName("btnPoli4"); // NOI18N
        btnPoli4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli4ActionPerformed(evt);
            }
        });
        btnPoli4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli4KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli4);
        btnPoli4.setBounds(700, 300, 28, 23);

        LabelPoli8.setText("Diganosa 2 :");
        LabelPoli8.setName("LabelPoli8"); // NOI18N
        FormInput.add(LabelPoli8);
        LabelPoli8.setBounds(380, 330, 94, 23);

        KdPoli5.setEditable(false);
        KdPoli5.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli5.setHighlighter(null);
        KdPoli5.setName("KdPoli5"); // NOI18N
        FormInput.add(KdPoli5);
        KdPoli5.setBounds(477, 330, 50, 23);

        NmPoli5.setEditable(false);
        NmPoli5.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli5.setHighlighter(null);
        NmPoli5.setName("NmPoli5"); // NOI18N
        FormInput.add(NmPoli5);
        NmPoli5.setBounds(528, 330, 170, 23);

        btnPoli5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli5.setMnemonic('X');
        btnPoli5.setToolTipText("Alt+X");
        btnPoli5.setName("btnPoli5"); // NOI18N
        btnPoli5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli5ActionPerformed(evt);
            }
        });
        btnPoli5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli5KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli5);
        btnPoli5.setBounds(700, 330, 28, 23);

        LabelPoli9.setText("Diganosa 3 :");
        LabelPoli9.setName("LabelPoli9"); // NOI18N
        FormInput.add(LabelPoli9);
        LabelPoli9.setBounds(380, 360, 94, 23);

        KdPoli6.setEditable(false);
        KdPoli6.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli6.setHighlighter(null);
        KdPoli6.setName("KdPoli6"); // NOI18N
        FormInput.add(KdPoli6);
        KdPoli6.setBounds(477, 360, 50, 23);

        NmPoli6.setEditable(false);
        NmPoli6.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli6.setHighlighter(null);
        NmPoli6.setName("NmPoli6"); // NOI18N
        FormInput.add(NmPoli6);
        NmPoli6.setBounds(528, 360, 170, 23);

        btnPoli6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli6.setMnemonic('X');
        btnPoli6.setToolTipText("Alt+X");
        btnPoli6.setName("btnPoli6"); // NOI18N
        btnPoli6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli6ActionPerformed(evt);
            }
        });
        btnPoli6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli6KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli6);
        btnPoli6.setBounds(700, 360, 28, 23);

        KdPoli7.setEditable(false);
        KdPoli7.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli7.setHighlighter(null);
        KdPoli7.setName("KdPoli7"); // NOI18N
        FormInput.add(KdPoli7);
        KdPoli7.setBounds(130, 500, 50, 23);

        NmPoli7.setEditable(false);
        NmPoli7.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli7.setHighlighter(null);
        NmPoli7.setName("NmPoli7"); // NOI18N
        FormInput.add(NmPoli7);
        NmPoli7.setBounds(182, 500, 170, 23);

        btnPoli7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli7.setMnemonic('X');
        btnPoli7.setToolTipText("Alt+X");
        btnPoli7.setName("btnPoli7"); // NOI18N
        btnPoli7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli7ActionPerformed(evt);
            }
        });
        btnPoli7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli7KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli7);
        btnPoli7.setBounds(354, 500, 28, 23);

        jLabel32.setText("Tgl.Est Rujukan :");
        jLabel32.setName("jLabel32"); // NOI18N
        jLabel32.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel32);
        jLabel32.setBounds(90, 410, 95, 23);

        TanggalRujuk3.setEditable(false);
        TanggalRujuk3.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujuk3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "31-10-2018" }));
        TanggalRujuk3.setDisplayFormat("dd-MM-yyyy");
        TanggalRujuk3.setName("TanggalRujuk3"); // NOI18N
        TanggalRujuk3.setOpaque(false);
        TanggalRujuk3.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujuk3KeyPressed(evt);
            }
        });
        FormInput.add(TanggalRujuk3);
        TanggalRujuk3.setBounds(188, 410, 90, 23);

        LabelPoli12.setText("PPK Rujukan :");
        LabelPoli12.setName("LabelPoli12"); // NOI18N
        FormInput.add(LabelPoli12);
        LabelPoli12.setBounds(290, 410, 80, 23);

        KdPoli8.setEditable(false);
        KdPoli8.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli8.setHighlighter(null);
        KdPoli8.setName("KdPoli8"); // NOI18N
        FormInput.add(KdPoli8);
        KdPoli8.setBounds(373, 410, 90, 23);

        NmPoli8.setEditable(false);
        NmPoli8.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli8.setHighlighter(null);
        NmPoli8.setName("NmPoli8"); // NOI18N
        FormInput.add(NmPoli8);
        NmPoli8.setBounds(465, 410, 233, 23);

        btnPoli8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli8.setMnemonic('X');
        btnPoli8.setToolTipText("Alt+X");
        btnPoli8.setName("btnPoli8"); // NOI18N
        btnPoli8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli8ActionPerformed(evt);
            }
        });
        btnPoli8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli8KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli8);
        btnPoli8.setBounds(700, 410, 28, 23);

        chkTNI1.setText("Kunjungan :");
        chkTNI1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkTNI1.setName("chkTNI1"); // NOI18N
        chkTNI1.setOpaque(false);
        chkTNI1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTNI1ActionPerformed(evt);
            }
        });
        FormInput.add(chkTNI1);
        chkTNI1.setBounds(0, 250, 90, 23);

        chkTNI2.setText("Internal :");
        chkTNI2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkTNI2.setName("chkTNI2"); // NOI18N
        chkTNI2.setOpaque(false);
        chkTNI2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTNI2ActionPerformed(evt);
            }
        });
        FormInput.add(chkTNI2);
        chkTNI2.setBounds(7, 500, 120, 23);

        chkTNI3.setText("Rujuk Lanjut :");
        chkTNI3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkTNI3.setName("chkTNI3"); // NOI18N
        chkTNI3.setOpaque(false);
        chkTNI3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTNI3ActionPerformed(evt);
            }
        });
        FormInput.add(chkTNI3);
        chkTNI3.setBounds(0, 410, 100, 23);

        KdPoli9.setEditable(false);
        KdPoli9.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli9.setHighlighter(null);
        KdPoli9.setName("KdPoli9"); // NOI18N
        FormInput.add(KdPoli9);
        KdPoli9.setBounds(130, 440, 50, 23);

        NmPoli9.setEditable(false);
        NmPoli9.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli9.setHighlighter(null);
        NmPoli9.setName("NmPoli9"); // NOI18N
        FormInput.add(NmPoli9);
        NmPoli9.setBounds(182, 440, 170, 23);

        btnPoli9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli9.setMnemonic('X');
        btnPoli9.setToolTipText("Alt+X");
        btnPoli9.setName("btnPoli9"); // NOI18N
        btnPoli9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli9ActionPerformed(evt);
            }
        });
        btnPoli9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli9KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli9);
        btnPoli9.setBounds(354, 440, 28, 23);

        LabelPoli10.setText("Sarana :");
        LabelPoli10.setName("LabelPoli10"); // NOI18N
        FormInput.add(LabelPoli10);
        LabelPoli10.setBounds(7, 470, 120, 23);

        KdPoli10.setEditable(false);
        KdPoli10.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli10.setHighlighter(null);
        KdPoli10.setName("KdPoli10"); // NOI18N
        FormInput.add(KdPoli10);
        KdPoli10.setBounds(130, 470, 50, 23);

        NmPoli10.setEditable(false);
        NmPoli10.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli10.setHighlighter(null);
        NmPoli10.setName("NmPoli10"); // NOI18N
        FormInput.add(NmPoli10);
        NmPoli10.setBounds(182, 470, 170, 23);

        btnPoli10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli10.setMnemonic('X');
        btnPoli10.setToolTipText("Alt+X");
        btnPoli10.setName("btnPoli10"); // NOI18N
        btnPoli10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli10ActionPerformed(evt);
            }
        });
        btnPoli10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli10KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli10);
        btnPoli10.setBounds(354, 470, 28, 23);

        chkTNI4.setText("Khusus :");
        chkTNI4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkTNI4.setName("chkTNI4"); // NOI18N
        chkTNI4.setOpaque(false);
        chkTNI4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTNI4ActionPerformed(evt);
            }
        });
        FormInput.add(chkTNI4);
        chkTNI4.setBounds(380, 440, 95, 23);

        KdPoli11.setEditable(false);
        KdPoli11.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli11.setHighlighter(null);
        KdPoli11.setName("KdPoli11"); // NOI18N
        FormInput.add(KdPoli11);
        KdPoli11.setBounds(477, 440, 50, 23);

        NmPoli11.setEditable(false);
        NmPoli11.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli11.setHighlighter(null);
        NmPoli11.setName("NmPoli11"); // NOI18N
        FormInput.add(NmPoli11);
        NmPoli11.setBounds(528, 440, 170, 23);

        btnPoli11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli11.setMnemonic('X');
        btnPoli11.setToolTipText("Alt+X");
        btnPoli11.setName("btnPoli11"); // NOI18N
        btnPoli11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli11ActionPerformed(evt);
            }
        });
        btnPoli11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli11KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli11);
        btnPoli11.setBounds(700, 440, 28, 23);

        btnPoli12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli12.setMnemonic('X');
        btnPoli12.setToolTipText("Alt+X");
        btnPoli12.setName("btnPoli12"); // NOI18N
        btnPoli12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli12ActionPerformed(evt);
            }
        });
        btnPoli12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli12KeyPressed(evt);
            }
        });
        FormInput.add(btnPoli12);
        btnPoli12.setBounds(700, 470, 28, 23);

        NmPoli12.setEditable(false);
        NmPoli12.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli12.setHighlighter(null);
        NmPoli12.setName("NmPoli12"); // NOI18N
        FormInput.add(NmPoli12);
        NmPoli12.setBounds(528, 470, 170, 23);

        KdPoli12.setEditable(false);
        KdPoli12.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli12.setHighlighter(null);
        KdPoli12.setName("KdPoli12"); // NOI18N
        FormInput.add(KdPoli12);
        KdPoli12.setBounds(477, 470, 50, 23);

        LabelPoli11.setText("Subspesilis :");
        LabelPoli11.setName("LabelPoli11"); // NOI18N
        FormInput.add(LabelPoli11);
        LabelPoli11.setBounds(380, 470, 94, 23);

        jLabel33.setText("Catatan :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(380, 500, 94, 23);

        Catatan8.setHighlighter(null);
        Catatan8.setName("Catatan8"); // NOI18N
        Catatan8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan8KeyPressed(evt);
            }
        });
        FormInput.add(Catatan8);
        Catatan8.setBounds(477, 500, 251, 23);

        jLabel34.setText("TACC :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 530, 99, 23);

        AsalRujukan2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 Tanpa TACC", "1 Time", "2 Age", "3 Complication", "4 Comorbidity" }));
        AsalRujukan2.setName("AsalRujukan2"); // NOI18N
        AsalRujukan2.setOpaque(false);
        AsalRujukan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukan2KeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan2);
        AsalRujukan2.setBounds(103, 530, 170, 23);

        jLabel35.setText("Alasan TACC :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(290, 530, 80, 23);

        AsalRujukan3.setName("AsalRujukan3"); // NOI18N
        AsalRujukan3.setOpaque(false);
        AsalRujukan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukan3KeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan3);
        AsalRujukan3.setBounds(373, 530, 355, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pendaftaran & Kunjungan", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame4.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl. SEP :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "31-10-2018" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "31-10-2018" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        internalFrame4.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Pendaftaran", internalFrame4);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
         
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
                   
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
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

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        
}//GEN-LAST:event_tbObatKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        
    }//GEN-LAST:event_CatatanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void TanggalRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujukKeyPressed
        
    }//GEN-LAST:event_TanggalRujukKeyPressed

    private void AsalRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukanKeyPressed
        
    }//GEN-LAST:event_AsalRujukanKeyPressed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(this.getHeight()<700){   
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormInput.setPreferredSize(new Dimension(FormInput.WIDTH,580));
            if(this.getWidth()<780){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(770,580));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }else{
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);            
            if(this.getWidth()<780){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(770,FormInput.HEIGHT));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void AsalRujukan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AsalRujukan1KeyPressed

    private void btnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliActionPerformed
        
    }//GEN-LAST:event_btnPoliActionPerformed

    private void btnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliKeyPressed
        
    }//GEN-LAST:event_btnPoliKeyPressed

    private void Catatan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan2KeyPressed

    private void Catatan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan3KeyPressed

    private void Catatan4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan4KeyPressed

    private void Catatan5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan5KeyPressed

    private void Catatan6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan6KeyPressed

    private void Catatan7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan7KeyPressed

    private void chkTNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTNIActionPerformed
        
    }//GEN-LAST:event_chkTNIActionPerformed

    private void TanggalRujuk1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujuk1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalRujuk1KeyPressed

    private void btnPoli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli1ActionPerformed

    private void btnPoli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli1KeyPressed

    private void Catatan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan1KeyPressed

    private void btnPoli2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli2ActionPerformed

    private void btnPoli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli2KeyPressed

    private void TanggalRujuk2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujuk2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalRujuk2KeyPressed

    private void btnPoli3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli3ActionPerformed

    private void btnPoli3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli3KeyPressed

    private void btnPoli4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli4ActionPerformed

    private void btnPoli4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli4KeyPressed

    private void btnPoli5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli5ActionPerformed

    private void btnPoli5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli5KeyPressed

    private void btnPoli6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli6ActionPerformed

    private void btnPoli6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli6KeyPressed

    private void btnPoli7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli7ActionPerformed

    private void btnPoli7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli7KeyPressed

    private void TanggalRujuk3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujuk3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalRujuk3KeyPressed

    private void btnPoli8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli8ActionPerformed

    private void btnPoli8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli8KeyPressed

    private void chkTNI1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTNI1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTNI1ActionPerformed

    private void chkTNI2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTNI2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTNI2ActionPerformed

    private void chkTNI3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTNI3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTNI3ActionPerformed

    private void btnPoli9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli9ActionPerformed

    private void btnPoli9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli9KeyPressed

    private void btnPoli10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli10ActionPerformed

    private void btnPoli10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli10KeyPressed

    private void chkTNI4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTNI4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTNI4ActionPerformed

    private void btnPoli11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli11ActionPerformed

    private void btnPoli11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli11KeyPressed

    private void btnPoli12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli12ActionPerformed

    private void btnPoli12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli12KeyPressed

    private void Catatan8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan8KeyPressed

    private void AsalRujukan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AsalRujukan2KeyPressed

    private void AsalRujukan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AsalRujukan3KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PCareDataPendaftaran dialog = new PCareDataPendaftaran(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AsalRujukan;
    private widget.ComboBox AsalRujukan1;
    private widget.ComboBox AsalRujukan2;
    private widget.ComboBox AsalRujukan3;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox Catatan;
    private widget.TextBox Catatan1;
    private widget.TextBox Catatan2;
    private widget.TextBox Catatan3;
    private widget.TextBox Catatan4;
    private widget.TextBox Catatan5;
    private widget.TextBox Catatan6;
    private widget.TextBox Catatan7;
    private widget.TextBox Catatan8;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.TextBox JenisPeserta;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPoli1;
    private widget.TextBox KdPoli10;
    private widget.TextBox KdPoli11;
    private widget.TextBox KdPoli12;
    private widget.TextBox KdPoli2;
    private widget.TextBox KdPoli3;
    private widget.TextBox KdPoli4;
    private widget.TextBox KdPoli5;
    private widget.TextBox KdPoli6;
    private widget.TextBox KdPoli7;
    private widget.TextBox KdPoli8;
    private widget.TextBox KdPoli9;
    private widget.Label LCount;
    private widget.Label LabelPoli;
    private widget.Label LabelPoli10;
    private widget.Label LabelPoli11;
    private widget.Label LabelPoli12;
    private widget.Label LabelPoli2;
    private widget.Label LabelPoli3;
    private widget.Label LabelPoli4;
    private widget.Label LabelPoli5;
    private widget.Label LabelPoli6;
    private widget.Label LabelPoli7;
    private widget.Label LabelPoli8;
    private widget.Label LabelPoli9;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPoli1;
    private widget.TextBox NmPoli10;
    private widget.TextBox NmPoli11;
    private widget.TextBox NmPoli12;
    private widget.TextBox NmPoli2;
    private widget.TextBox NmPoli3;
    private widget.TextBox NmPoli4;
    private widget.TextBox NmPoli5;
    private widget.TextBox NmPoli6;
    private widget.TextBox NmPoli7;
    private widget.TextBox NmPoli8;
    private widget.TextBox NmPoli9;
    private widget.TextBox NoKartu;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalRujuk;
    private widget.Tanggal TanggalRujuk1;
    private widget.Tanggal TanggalRujuk2;
    private widget.Tanggal TanggalRujuk3;
    private widget.TextBox TglLahir;
    private widget.Button btnPoli;
    private widget.Button btnPoli1;
    private widget.Button btnPoli10;
    private widget.Button btnPoli11;
    private widget.Button btnPoli12;
    private widget.Button btnPoli2;
    private widget.Button btnPoli3;
    private widget.Button btnPoli4;
    private widget.Button btnPoli5;
    private widget.Button btnPoli6;
    private widget.Button btnPoli7;
    private widget.Button btnPoli8;
    private widget.Button btnPoli9;
    private widget.CekBox chkTNI;
    private widget.CekBox chkTNI1;
    private widget.CekBox chkTNI2;
    private widget.CekBox chkTNI3;
    private widget.CekBox chkTNI4;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame4;
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
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());  
        Catatan.setText("-");
    }
    
    private void emptTeks(){
        TNoRw.setText("");
        TPasien.setText("");
        TanggalRujuk.setDate(new Date());
        TglLahir.setText("");
        NoKartu.setText("");
        JenisPeserta.setText("");
        Status.setText("");
        JK.setText("");
        Catatan.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        TNoRM.setText("");
    }
    
    public void setNoRm(String norwt, Date tgl1,String status,String kdpoli,String namapoli) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdPoli.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?",kdpoli));
        NmPoli.setText(Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?",KdPoli.getText()));
        isRawat();            
    }
      
    
    public void isCek(){
    }
    
    public void tutupInput(){
        TabRawat.setSelectedIndex(1);
    }
    
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
           
        }
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgResepObat.java
 *
 * Created on 31 Mei 10, 11:27:40
 */

package bridging;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
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


/**
 *
 * @author perpustakaan
 */
public final class BPJSRujukanKhusus extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int jml=0,i=0,index=0;
    private WarnaTable2 warna=new WarnaTable2();
    private boolean [] pilih;
    private String[] nama,kode;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private ApiBPJS api=new ApiBPJS();
    private String URL="",link="",utc="",requestJson="",dx="",pros="",user="",idrujukan="";

    /** Creates new form DlgResepObat 
     *@param parent
     *@param modal*/
    public BPJSRujukanKhusus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rujukan","No.Peserta","Nama Pasien","Tgl.Ruj.Awal","Tgl.Ruj.Akhir"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbProgramPRB.setModel(tabMode);

        tbProgramPRB.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbProgramPRB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbProgramPRB.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(80);
            }
        }
        tbProgramPRB.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
            "P","Kode ICD X","Nama Penyakit"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDiagnosa.setModel(tabMode2);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbDiagnosa.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(410);
            }
        }
        warna.kolom=0;
        tbDiagnosa.setDefaultRenderer(Object.class,warna);
        tbDiagnosa.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDiagnosa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        tabMode3=new DefaultTableModel(null,new Object[]{
            "P","Kode Prosedur","Nama Prosedur"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbProsedur.setModel(tabMode3);
        tbProsedur.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbProsedur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbProsedur.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(410);
            }
        }
        warna.kolom=0;
        tbProsedur.setDefaultRenderer(Object.class,warna);

        NoPeserta.setDocument(new batasInput((byte)17).getKata(NoPeserta));
        NoRujukan.setDocument(new batasInput((int)200).getKata(NoRujukan));
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
        ChkInput.setSelected(false);
        isForm();
        
        try {
            user=akses.getkode().replace(" ","").substring(0,9);
        } catch (Exception e) {
            user=akses.getkode();
        }
        
        try {
            link=koneksiDB.URLAPIBPJS();
        } catch (Exception e) {
            System.out.println("E : "+e);
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
        Scroll = new widget.ScrollPane();
        tbProgramPRB = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        Scroll3 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        NoPeserta = new widget.TextBox();
        NoRM = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel13 = new widget.Label();
        NmPasien = new widget.TextBox();
        NoRujukan = new widget.TextBox();
        Diagnosa = new widget.TextBox();
        BtnCari2 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbDiagnosa = new widget.Table();
        jLabel16 = new widget.Label();
        Prosedur = new widget.TextBox();
        BtnCari3 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbProsedur = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rujukan Khusus VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbProgramPRB.setAutoCreateRowSorter(true);
        tbProgramPRB.setName("tbProgramPRB"); // NOI18N
        tbProgramPRB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProgramPRBMouseClicked(evt);
            }
        });
        tbProgramPRB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbProgramPRBKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbProgramPRB);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(53, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(142, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2021" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-11-2021" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(400, 426));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
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
        ChkInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ChkInputKeyPressed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll3.setName("Scroll3"); // NOI18N

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 403));
        FormInput.setLayout(null);

        NoPeserta.setHighlighter(null);
        NoPeserta.setName("NoPeserta"); // NOI18N
        FormInput.add(NoPeserta);
        NoPeserta.setBounds(225, 10, 115, 23);

        NoRM.setEditable(false);
        NoRM.setHighlighter(null);
        NoRM.setName("NoRM"); // NOI18N
        FormInput.add(NoRM);
        NoRM.setBounds(342, 10, 85, 23);

        jLabel3.setText("No.Rujukan :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 79, 23);

        jLabel13.setText("Diagnosa :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 40, 79, 23);

        NmPasien.setEditable(false);
        NmPasien.setHighlighter(null);
        NmPasien.setName("NmPasien"); // NOI18N
        FormInput.add(NmPasien);
        NmPasien.setBounds(429, 10, 194, 23);

        NoRujukan.setHighlighter(null);
        NoRujukan.setName("NoRujukan"); // NOI18N
        NoRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRujukanKeyPressed(evt);
            }
        });
        FormInput.add(NoRujukan);
        NoRujukan.setBounds(83, 10, 140, 23);

        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.setPreferredSize(new java.awt.Dimension(578, 23));
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(83, 40, 509, 23);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('4');
        BtnCari2.setToolTipText("Alt+4");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        FormInput.add(BtnCari2);
        BtnCari2.setBounds(595, 40, 28, 23);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N

        tbDiagnosa.setName("tbDiagnosa"); // NOI18N
        tbDiagnosa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDiagnosaMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbDiagnosa);

        FormInput.add(Scroll1);
        Scroll1.setBounds(83, 67, 540, 140);

        jLabel16.setText("Prosedur :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 214, 79, 23);

        Prosedur.setName("Prosedur"); // NOI18N
        Prosedur.setPreferredSize(new java.awt.Dimension(578, 23));
        Prosedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurKeyPressed(evt);
            }
        });
        FormInput.add(Prosedur);
        Prosedur.setBounds(83, 214, 509, 23);

        BtnCari3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari3.setMnemonic('4');
        BtnCari3.setToolTipText("Alt+4");
        BtnCari3.setName("BtnCari3"); // NOI18N
        BtnCari3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari3ActionPerformed(evt);
            }
        });
        BtnCari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari3KeyPressed(evt);
            }
        });
        FormInput.add(BtnCari3);
        BtnCari3.setBounds(595, 214, 28, 23);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setName("Scroll2"); // NOI18N

        tbProsedur.setName("tbProsedur"); // NOI18N
        tbProsedur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProsedurMouseClicked(evt);
            }
        });
        Scroll2.setViewportView(tbProsedur);

        FormInput.add(Scroll2);
        Scroll2.setBounds(83, 241, 540, 150);

        Scroll3.setViewportView(FormInput);

        PanelInput.add(Scroll3, java.awt.BorderLayout.CENTER);

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
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString()+"','"+
                                tabMode.getValueAt(r,1).toString()+"','"+
                                tabMode.getValueAt(r,2).toString()+"','"+
                                tabMode.getValueAt(r,3).toString()+"','"+
                                tabMode.getValueAt(r,4).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data"); 
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptCariBPJSRujukanKhusus.jasper","report","[ Data Rujukan Khusus BPJS ]",param);
            this.setCursor(Cursor.getDefaultCursor());
        } 
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSimpan, BtnKeluar);
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
            Valid.pindah(evt, BtnCari, Diagnosa);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbProgramPRBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProgramPRBMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbProgramPRBMouseClicked

    private void tbProgramPRBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbProgramPRBKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbProgramPRBKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRujukan.getText().trim().equals("")){
            Valid.textKosong(NoRujukan,"No.Rujukan");
        }else{
            try{
                dx="";
                jml=0;        
                for(i=0;i<tbDiagnosa.getRowCount();i++){
                    if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                        jml++;
                        if(jml==1){
                            dx=dx+"{\"kode\":\"P;"+tbDiagnosa.getValueAt(i,1).toString()+"\"},";
                        }else{
                            dx=dx+"{\"kode\":\"S;"+tbDiagnosa.getValueAt(i,1).toString()+"\"},";
                        }
                    }
                }
                dx="["+dx.substring(0,dx.length()-1)+"]";
      
                pros="";
                for(i=0;i<tbProsedur.getRowCount();i++){
                    if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                        jml++;
                        pros=pros+"{\"kode\":\""+tbProsedur.getValueAt(i,1).toString()+"\"},";
                    }
                }
                pros="["+pros.substring(0,pros.length()-1)+"]";
                    
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                requestEntity = new HttpEntity(headers);
                URL = link+"/Rujukan/Khusus/insert";
                requestJson ="{" +
                                "\"noRujukan\":\""+NoRujukan.getText()+"\"," +
                                "\"diagnosa\":"+dx+"," +
                                "\"procedure\":"+pros+"," +
                                "\"user\":\""+user+"\"" +
                             "}";
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText()); 
                if(nameNode.path("code").asText().equals("200")){
                    response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                    Sequel.menyimpan("bridging_rujukan_bpjs_khusus","?,?,?,?,?",5,new String[]{
                        NoRujukan.getText(),NoPeserta.getText(),NmPasien.getText(),response.path("rujukan").path("tglrujukan_awal").asText(),response.path("rujukan").path("tglrujukan_berakhir").asText()
                    }); 
                    if(jml>0){
                        jml=0;
                        for(i=0;i<tbDiagnosa.getRowCount();i++){
                            if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                                jml++;
                                if(jml==1){
                                    Sequel.menyimpan("bridging_rujukan_bpjs_khusus_diagnosa","?,?,?,?",4,new String[]{
                                        NoRujukan.getText(),"P",tbDiagnosa.getValueAt(i,1).toString(),tbDiagnosa.getValueAt(i,2).toString()
                                    });  
                                }else{
                                    Sequel.menyimpan("bridging_rujukan_bpjs_khusus_diagnosa","?,?,?,?",4,new String[]{
                                        NoRujukan.getText(),"S",tbDiagnosa.getValueAt(i,1).toString(),tbDiagnosa.getValueAt(i,2).toString()
                                    }); 
                                }
                            }
                        }
                        for(i=0;i<tbProsedur.getRowCount();i++){
                            if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                                Sequel.menyimpan("bridging_rujukan_bpjs_khusus_prosedur","?,?,?",3,new String[]{
                                    NoRujukan.getText(),tbProsedur.getValueAt(i,1).toString(),tbProsedur.getValueAt(i,2).toString()
                                });  
                            }
                        }
                    }
                    emptTeks();
                    tampil();
                }  
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                }
            }
        }

    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Diagnosa,BtnKeluar);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void tbDiagnosaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDiagnosaMouseClicked
        /*if(tabMode2.getRowCount()!=0){
            try {
                Valid.tabelKosong(tabMode);
                tampil();
            } catch (java.lang.NullPointerException e) {
            }
        }*/
    }//GEN-LAST:event_tbDiagnosaMouseClicked

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            //Valid.pindah(evt, TCari,Pemeriksaan);
        }
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
       if(Diagnosa.getText().trim().equals("")){
           JOptionPane.showMessageDialog(null,"Silahkan masukkan pencarian diagnosa ..!!");
       }else{
           tampilDiagnosa();
       }
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari2ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Diagnosa.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_TAB){
            Prosedur.requestFocus();
        }
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void NoRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRujukanKeyPressed
        Valid.pindah(evt,TCari,BtnCari);
    }//GEN-LAST:event_NoRujukanKeyPressed

    private void ProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari3ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari3.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Prosedur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_TAB){
            BtnSimpan.requestFocus();
        }
    }//GEN-LAST:event_ProsedurKeyPressed

    private void BtnCari3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari3ActionPerformed
        if(Diagnosa.getText().trim().equals("")){
           JOptionPane.showMessageDialog(null,"Silahkan masukkan pencarian prosedur ..!!");
       }else{
           tampilProsedur();
       }
    }//GEN-LAST:event_BtnCari3ActionPerformed

    private void BtnCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari3KeyPressed

    private void tbProsedurMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProsedurMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbProsedurMouseClicked

    private void ChkInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ChkInputKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkInputKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbProgramPRB.getSelectedRow()!= -1){
            idrujukan="";
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-Id",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                requestEntity = new HttpEntity(headers);
                URL = link+"/Rujukan/Khusus/List/Bulan/"+Integer.parseInt(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),3).toString().substring(5,7))+"/Tahun/"+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),3).toString().substring(0,4);	
                System.out.println(URL);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                if(nameNode.path("code").asText().equals("200")){
                    Valid.tabelKosong(tabMode);
                    response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                    //response = root.path("response");
                    if(response.path("rujukan").isArray()){
                        for(JsonNode list:response.path("rujukan")){
                            if(list.path("norujukan").asText().equals(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),0).toString())){
                                idrujukan=list.path("idrujukan").asText();
                            }
                        }
                    }
                }else {
                    JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                }
            }
            if(!idrujukan.equals("")){
                try {
                    bodyWithDeleteRequest();
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSRujukanKhusus dialog = new BPJSRujukanKhusus(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari2;
    private widget.Button BtnCari3;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Diagnosa;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private widget.TextBox NmPasien;
    private widget.TextBox NoPeserta;
    private widget.TextBox NoRM;
    private widget.TextBox NoRujukan;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Prosedur;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbDiagnosa;
    private widget.Table tbProgramPRB;
    private widget.Table tbProsedur;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{     
            ps=koneksi.prepareStatement("SELECT * FROM bridging_rujukan_bpjs_khusus where tglrujukan_awal between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (no_rujukan like ? or nokapst like ? or nmpst like ?)")+" order by tglrujukan_awal");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rujukan"),rs.getString("nokapst"),rs.getString("nmpst"),rs.getString("tglrujukan_awal"),rs.getString("tglrujukan_berakhir")
                    });
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
            LCount.setText(""+tabMode.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }       
    }

    private void getData() {
        if(tbProgramPRB.getSelectedRow()!= -1){
            NoRujukan.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),0).toString()); 
            NoPeserta.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),1).toString()); 
            NmPasien.setText(tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),2).toString());
            Valid.tabelKosong(tabMode2);
            Valid.tabelKosong(tabMode3);
            try{     
                ps=koneksi.prepareStatement("select * from bridging_rujukan_bpjs_khusus_diagnosa where no_rujukan=? order by status");
                try {
                    ps.setString(1,tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode2.addRow(new Object[]{
                            true,rs.getString("kode_diagnosa"),rs.getString("nama_diagnosa")
                        });
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
                
                ps=koneksi.prepareStatement("select * from bridging_rujukan_bpjs_khusus_prosedur where no_rujukan=?");
                try {
                    ps.setString(1,tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode3.addRow(new Object[]{
                            true,rs.getString("kode_prosedur"),rs.getString("nama_prosedur")
                        });
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
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            } 
        }
    }
   
    public void setNoRm(String nokartu,String norm,String namapasien,String norujukan) {
        NoPeserta.setText(nokartu);
        NoRM.setText(norm);
        NmPasien.setText(namapasien);
        NoRujukan.setText(norujukan);
        TCari.setText(norujukan);
        ChkInput.setSelected(true);
        isForm();
        tampil();
    }
    
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,270));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }

    private void tampilDiagnosa() {
        try {
            jml=0;
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(Valid.SetAngka(tbDiagnosa.getValueAt(i,0).toString())>0){
                    jml++;
                }
            }   
            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            index=0; 
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbDiagnosa.getValueAt(i,1).toString();
                    nama[index]=tbDiagnosa.getValueAt(i,2).toString();
                }
            }
            
            Valid.tabelKosong(tabMode2); 
            for(i=0;i<jml;i++){
                tabMode2.addRow(new Object[] {
                    pilih[i],kode[i],nama[i]
                });
            }
            
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
	    utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("X-Timestamp",utc);
	    headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
            requestEntity = new HttpEntity(headers);
            URL = link+"/referensi/diagnosa/"+Diagnosa.getText();
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            if(nameNode.path("code").asText().equals("200")){
                response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                //response = root.path("response");
                if(response.path("diagnosa").isArray()){
                    for(JsonNode list:response.path("diagnosa")){
                        tabMode2.addRow(new Object[]{
                            false,list.path("kode").asText(),list.path("nama").asText()
                        });
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void tampilProsedur() {
        try {
            jml=0;
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(Valid.SetAngka(tbProsedur.getValueAt(i,0).toString())>0){
                    jml++;
                }
            }   
            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            index=0; 
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbProsedur.getValueAt(i,1).toString();
                    nama[index]=tbProsedur.getValueAt(i,2).toString();
                }
            }
            
            Valid.tabelKosong(tabMode3); 
            
            for(i=0;i<jml;i++){
                tabMode3.addRow(new Object[] {
                    pilih[i],kode[i],nama[i]
                });
            }
            
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
	    utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("X-Timestamp",utc);
	    headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
            requestEntity = new HttpEntity(headers);
            URL = link+"/referensi/procedure/"+Prosedur.getText();	
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            if(nameNode.path("code").asText().equals("200")){
                response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                //response = root.path("response");
                if(response.path("procedure").isArray()){
                    for(JsonNode list:response.path("procedure")){
                        tabMode3.addRow(new Object[]{
                            false,list.path("kode").asText(),list.path("nama").asText()
                        });
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    private void emptTeks() {
        NoRujukan.setText("");
        NoPeserta.setText("");
        NoRM.setText("");
        NmPasien.setText("");
        Diagnosa.setText("");
        Prosedur.setText("");
        Valid.tabelKosong(tabMode2);
        Valid.tabelKosong(tabMode3);
        NoRujukan.requestFocus();
    }
    
    public static class HttpEntityEnclosingDeleteRequest extends HttpEntityEnclosingRequestBase {
        public HttpEntityEnclosingDeleteRequest(final URI uri) {
            super();
            setURI(uri);
        }

        @Override
        public String getMethod() {
            return "DELETE";
        }
    }
       
    
    @Test
    public void bodyWithDeleteRequest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        SSLSocketFactory sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme=new Scheme("https",443,sslFactory);
    
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory(){
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new BPJSRujukanKeluar.HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        };
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        restTemplate.setRequestFactory(factory);
        
        try {
            URL = link+"/Rujukan/Khusus/delete";	
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
            utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("X-Timestamp",utc);
	    headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
            requestJson ="{" +
                            "\"request\": {" +
                                "\"t_rujukan\": {" +
                                    "\"idRujukan\": \""+idrujukan+"\"," +
                                    "\"noRujukan\": \""+tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),0).toString()+"\"," +
                                    "\"user\": \""+user+"\"" +
                                "}" +
                            "}" +
                         "}";            
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,new HttpEntity<String>(requestJson,headers), String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("bridging_rujukan_bpjs_khusus_diagnosa","no_rujukan",tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),0).toString());
                Sequel.meghapus("bridging_rujukan_bpjs_khusus_prosedur","no_rujukan",tbProgramPRB.getValueAt(tbProgramPRB.getSelectedRow(),0).toString());
                tampil();
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        } catch (Exception e) {            
        }
    }
}

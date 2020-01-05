/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package bridging;

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
import java.io.FileInputStream;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


/**
 *
 * @author perpustakaan
 */
public final class BPJSRujukanKeluar extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private BPJSApi api=new BPJSApi();
    private Date date = new Date();
    private BPJSCekReferensiFaskes faskes=new BPJSCekReferensiFaskes(null,false);
    private BPJSCekReferensiPenyakit penyakit=new BPJSCekReferensiPenyakit(null,false);
    private BPJSCekReferensiPoli poli=new BPJSCekReferensiPoli(null,false);
    private String URL="",requestJson,user="",link="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public BPJSRujukanKeluar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);


        Object[] row={
            "No.SEP","No.Rawat","No.RM","Nama Pasien","Tgl.Rujukan", 
            "No.Rujukan","Kode PPK","Nama PPK Rujukan","Jenis Pelayanan","Tipe Rujukan","Catatan",
            "Kode Diagnosa","Nama Diagnosa", "Kode Poli", "Nama Poli"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(130);
            }else if(i==9){
                column.setPreferredWidth(130);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(180);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

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
        
        faskes.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(faskes.getTable().getSelectedRow()!= -1){  
                    KdPpkRujukan1.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),1).toString());
                    NmPpkRujukan1.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),2).toString());
                    KdPpkRujukan1.requestFocus();
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
        
        faskes.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    faskes.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penyakit.getTable().getSelectedRow()!= -1){   
                    KdPenyakit1.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                    NmPenyakit1.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),2).toString());
                    KdPenyakit1.requestFocus();
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
        
        penyakit.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penyakit.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){  
                    KdPoli1.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    NmPoli1.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                    KdPoli1.requestFocus();
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
        
        poli.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    poli.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        try {
            link=koneksiDB.URLAPIBPJS();
        } catch (Exception e) {
            System.out.println("E : "+e);
        }
        
        try {
            user=akses.getkode().replace(" ","").substring(0,9);
        } catch (Exception e) {
            user=akses.getkode();
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSuratRujukan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        internalFrame6 = new widget.InternalFrame();
        jLabel30 = new widget.Label();
        TanggalRujukKeluar = new widget.Tanggal();
        jLabel12 = new widget.Label();
        KdPpkRujukan1 = new widget.TextBox();
        NmPpkRujukan1 = new widget.TextBox();
        btnPPKRujukan1 = new widget.Button();
        jLabel31 = new widget.Label();
        JenisPelayanan1 = new widget.ComboBox();
        jLabel32 = new widget.Label();
        KdPenyakit1 = new widget.TextBox();
        NmPenyakit1 = new widget.TextBox();
        btnDiagnosa1 = new widget.Button();
        LabelPoli1 = new widget.Label();
        KdPoli1 = new widget.TextBox();
        NmPoli1 = new widget.TextBox();
        btnPoli1 = new widget.Button();
        jLabel33 = new widget.Label();
        TipeRujukan = new widget.ComboBox();
        jLabel34 = new widget.Label();
        Catatan1 = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSuratRujukan.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratRujukan.setForeground(java.awt.Color.darkGray);
        MnSuratRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratRujukan.setText("Surat Rujukan");
        MnSuratRujukan.setName("MnSuratRujukan"); // NOI18N
        MnSuratRujukan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnSuratRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratRujukanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSuratRujukan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Data Rujukan Keluar VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rujuk :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass8.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass8.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(DTPCari2);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(53, 23));
        panelGlass8.add(jLabel22);

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

        jLabel6.setText("Keyword :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setPreferredSize(new java.awt.Dimension(0, 110));
        internalFrame6.setLayout(null);

        jLabel30.setText("Tanggal Rujukan :");
        jLabel30.setName("jLabel30"); // NOI18N
        internalFrame6.add(jLabel30);
        jLabel30.setBounds(0, 12, 102, 23);

        TanggalRujukKeluar.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujukKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019" }));
        TanggalRujukKeluar.setDisplayFormat("dd-MM-yyyy");
        TanggalRujukKeluar.setName("TanggalRujukKeluar"); // NOI18N
        TanggalRujukKeluar.setOpaque(false);
        TanggalRujukKeluar.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame6.add(TanggalRujukKeluar);
        TanggalRujukKeluar.setBounds(105, 12, 90, 23);

        jLabel12.setText("PPK Rujukan :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame6.add(jLabel12);
        jLabel12.setBounds(0, 42, 102, 23);

        KdPpkRujukan1.setEditable(false);
        KdPpkRujukan1.setBackground(new java.awt.Color(245, 250, 240));
        KdPpkRujukan1.setHighlighter(null);
        KdPpkRujukan1.setName("KdPpkRujukan1"); // NOI18N
        internalFrame6.add(KdPpkRujukan1);
        KdPpkRujukan1.setBounds(105, 42, 75, 23);

        NmPpkRujukan1.setEditable(false);
        NmPpkRujukan1.setBackground(new java.awt.Color(245, 250, 240));
        NmPpkRujukan1.setHighlighter(null);
        NmPpkRujukan1.setName("NmPpkRujukan1"); // NOI18N
        internalFrame6.add(NmPpkRujukan1);
        NmPpkRujukan1.setBounds(182, 42, 200, 23);

        btnPPKRujukan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPPKRujukan1.setMnemonic('X');
        btnPPKRujukan1.setToolTipText("Alt+X");
        btnPPKRujukan1.setName("btnPPKRujukan1"); // NOI18N
        btnPPKRujukan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPKRujukan1ActionPerformed(evt);
            }
        });
        btnPPKRujukan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPPKRujukan1KeyPressed(evt);
            }
        });
        internalFrame6.add(btnPPKRujukan1);
        btnPPKRujukan1.setBounds(385, 42, 28, 23);

        jLabel31.setText("Jns.Pelayanan :");
        jLabel31.setName("jLabel31"); // NOI18N
        internalFrame6.add(jLabel31);
        jLabel31.setBounds(425, 42, 85, 23);

        JenisPelayanan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Rawat Inap", "2. Rawat Jalan" }));
        JenisPelayanan1.setSelectedIndex(1);
        JenisPelayanan1.setName("JenisPelayanan1"); // NOI18N
        JenisPelayanan1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisPelayanan1ItemStateChanged(evt);
            }
        });
        JenisPelayanan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPelayanan1KeyPressed(evt);
            }
        });
        internalFrame6.add(JenisPelayanan1);
        JenisPelayanan1.setBounds(514, 42, 278, 23);

        jLabel32.setText("Diagnosa Rujuk :");
        jLabel32.setName("jLabel32"); // NOI18N
        internalFrame6.add(jLabel32);
        jLabel32.setBounds(0, 72, 102, 23);

        KdPenyakit1.setEditable(false);
        KdPenyakit1.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit1.setHighlighter(null);
        KdPenyakit1.setName("KdPenyakit1"); // NOI18N
        internalFrame6.add(KdPenyakit1);
        KdPenyakit1.setBounds(105, 72, 75, 23);

        NmPenyakit1.setEditable(false);
        NmPenyakit1.setBackground(new java.awt.Color(245, 250, 240));
        NmPenyakit1.setHighlighter(null);
        NmPenyakit1.setName("NmPenyakit1"); // NOI18N
        internalFrame6.add(NmPenyakit1);
        NmPenyakit1.setBounds(182, 72, 200, 23);

        btnDiagnosa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa1.setMnemonic('X');
        btnDiagnosa1.setToolTipText("Alt+X");
        btnDiagnosa1.setName("btnDiagnosa1"); // NOI18N
        btnDiagnosa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosa1ActionPerformed(evt);
            }
        });
        btnDiagnosa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosa1KeyPressed(evt);
            }
        });
        internalFrame6.add(btnDiagnosa1);
        btnDiagnosa1.setBounds(385, 72, 28, 23);

        LabelPoli1.setText("Poli Tujuan :");
        LabelPoli1.setName("LabelPoli1"); // NOI18N
        internalFrame6.add(LabelPoli1);
        LabelPoli1.setBounds(425, 72, 85, 23);

        KdPoli1.setEditable(false);
        KdPoli1.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli1.setHighlighter(null);
        KdPoli1.setName("KdPoli1"); // NOI18N
        internalFrame6.add(KdPoli1);
        KdPoli1.setBounds(514, 72, 65, 23);

        NmPoli1.setEditable(false);
        NmPoli1.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli1.setHighlighter(null);
        NmPoli1.setName("NmPoli1"); // NOI18N
        internalFrame6.add(NmPoli1);
        NmPoli1.setBounds(581, 72, 180, 23);

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
        internalFrame6.add(btnPoli1);
        btnPoli1.setBounds(764, 72, 28, 23);

        jLabel33.setText("Tipe Rujukan :");
        jLabel33.setName("jLabel33"); // NOI18N
        internalFrame6.add(jLabel33);
        jLabel33.setBounds(200, 12, 80, 23);

        TipeRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Penuh", "1. Partial", "2. Rujuk Balik" }));
        TipeRujukan.setName("TipeRujukan"); // NOI18N
        TipeRujukan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TipeRujukanItemStateChanged(evt);
            }
        });
        TipeRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipeRujukanActionPerformed(evt);
            }
        });
        TipeRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TipeRujukanKeyPressed(evt);
            }
        });
        internalFrame6.add(TipeRujukan);
        TipeRujukan.setBounds(283, 12, 130, 23);

        jLabel34.setText("Catatan :");
        jLabel34.setName("jLabel34"); // NOI18N
        internalFrame6.add(jLabel34);
        jLabel34.setBounds(425, 12, 85, 23);

        Catatan1.setHighlighter(null);
        Catatan1.setName("Catatan1"); // NOI18N
        Catatan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan1KeyPressed(evt);
            }
        });
        internalFrame6.add(Catatan1);
        Catatan1.setBounds(514, 12, 278, 23);

        internalFrame1.add(internalFrame6, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            try {
                bodyWithDeleteRequest();
            }catch (Exception ex) {
                System.out.println("Notifikasi Bridging : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
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

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            if (KdPpkRujukan1.getText().trim().equals("")||NmPpkRujukan1.getText().trim().equals("")) {
                Valid.textKosong(KdPpkRujukan1, "PPK Rujukan");
            }else if (KdPenyakit1.getText().trim().equals("")||NmPenyakit1.getText().trim().equals("")) {
                Valid.textKosong(KdPenyakit1, "Diagnosa");
            }else if (Catatan1.getText().trim().equals("")) {
                Valid.textKosong(Catatan1, "Catatan");
            }else if(KdPoli1.getText().trim().equals("")||NmPoli1.getText().trim().equals("")){
                Valid.textKosong(KdPoli1, "Poli Tujuan");        
            }else{  
                try {
                    URL = link+"/Rujukan/update";	
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                    headers.add("X-Signature",api.getHmac());
                    requestJson ="{" +
                                    "\"request\": {" +
                                        "\"t_rujukan\": {" +
                                            "\"noRujukan\": \""+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"\"," +
                                            "\"ppkDirujuk\": \""+KdPpkRujukan1.getText()+"\"," +
                                            "\"tipe\": \""+TipeRujukan.getSelectedItem().toString().substring(0,1)+"\"," +
                                            "\"jnsPelayanan\": \""+JenisPelayanan1.getSelectedItem().toString().substring(0,1)+"\"," +
                                            "\"catatan\": \""+Catatan1.getText()+"\"," +
                                            "\"diagRujukan\": \""+KdPenyakit1.getText()+"\"," +
                                            "\"tipeRujukan\": \""+TipeRujukan.getSelectedItem().toString().substring(0,1)+"\"," +
                                            "\"poliRujukan\": \""+KdPoli1.getText()+"\"," +
                                            "\"user\": \""+user+"\"" +
                                        "}" +
                                    "}" +
                                  "}";
                    requestEntity = new HttpEntity(requestJson,headers);
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    System.out.println("code : "+nameNode.path("code").asText());
                    System.out.println("message : "+nameNode.path("message").asText());
                    response = root.path("response");
                    if(nameNode.path("code").asText().equals("200")){
                        Sequel.mengedit3("bridging_rujukan_bpjs","no_rujukan=?","ppkDirujuk=?,tipeRujukan=?,jnsPelayanan=?,catatan=?,diagRujukan=?,poliRujukan=?,nm_ppkDirujuk=?,nama_diagRujukan=?,nama_poliRujukan=?",10,new String[]{
                            KdPpkRujukan1.getText(),TipeRujukan.getSelectedItem().toString(),JenisPelayanan1.getSelectedItem().toString().substring(0,1),
                            Catatan1.getText(),KdPenyakit1.getText(),KdPoli1.getText(),NmPpkRujukan1.getText(),NmPenyakit1.getText(),NmPoli1.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
                        });
                        Sequel.mengedit3("rujuk","no_rujuk=?","keterangan_diagnosa=?,keterangan=?",3,new String[]{
                            NmPenyakit1.getText(),Catatan1.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
                        });
                        tampil();
                        emptTeks();
                    }else{
                        JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                    }
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
                    }
                }
            }
        }     
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                param.put("tanggal1",Valid.SetTgl(DTPCari1.getSelectedItem()+"")); 
                param.put("tanggal2",Valid.SetTgl(DTPCari2.getSelectedItem()+"")); 
                param.put("parameter","%"+TCari.getText().trim()+"%"); 
            Valid.MyReport("rptBridgingDaftarRujukan.jasper","report","::[ Data Bridging Rujukan VClaim ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
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
            //Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void MnSuratRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratRujukanActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("norujuk",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            Valid.MyReport("rptBridgingRujukanBPJS.jasper",param,"::[ Surat Rujukan Keluar VClaim ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
        }   
    }//GEN-LAST:event_MnSuratRujukanActionPerformed

    private void btnPPKRujukan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukan1ActionPerformed
        faskes.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        faskes.setLocationRelativeTo(internalFrame1);
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukan1ActionPerformed

    private void btnPPKRujukan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPPKRujukan1KeyPressed

    private void JenisPelayanan1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisPelayanan1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisPelayanan1ItemStateChanged

    private void JenisPelayanan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayanan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisPelayanan1KeyPressed

    private void btnDiagnosa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosa1ActionPerformed
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosa1ActionPerformed

    private void btnDiagnosa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosa1KeyPressed

    private void btnPoli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli1ActionPerformed
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoli1ActionPerformed

    private void btnPoli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli1KeyPressed

    private void TipeRujukanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TipeRujukanItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TipeRujukanItemStateChanged

    private void TipeRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipeRujukanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipeRujukanActionPerformed

    private void TipeRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TipeRujukanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipeRujukanKeyPressed

    private void Catatan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan1KeyPressed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSRujukanKeluar dialog = new BPJSRujukanKeluar(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Catatan1;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox JenisPelayanan1;
    private widget.TextBox KdPenyakit1;
    private widget.TextBox KdPoli1;
    private widget.TextBox KdPpkRujukan1;
    private widget.Label LCount;
    private widget.Label LabelPoli1;
    private javax.swing.JMenuItem MnSuratRujukan;
    private widget.TextBox NmPenyakit1;
    private widget.TextBox NmPoli1;
    private widget.TextBox NmPpkRujukan1;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal TanggalRujukKeluar;
    private widget.ComboBox TipeRujukan;
    private widget.Button btnDiagnosa1;
    private widget.Button btnPPKRujukan1;
    private widget.Button btnPoli1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel12;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select bridging_rujukan_bpjs.no_sep, bridging_sep.no_rawat,"+
                    "bridging_sep.nomr,bridging_sep.nama_pasien,bridging_rujukan_bpjs.tglRujukan,"+
                    "bridging_rujukan_bpjs.no_rujukan,bridging_rujukan_bpjs.ppkDirujuk,"+
                    "bridging_rujukan_bpjs.nm_ppkDirujuk,"+
                    "if(bridging_rujukan_bpjs.jnsPelayanan='1','1. Rawat Inap','2. Rawat Jalan'),"+
                    "bridging_rujukan_bpjs.tipeRujukan,bridging_rujukan_bpjs.catatan,"+
                    "bridging_rujukan_bpjs.diagRujukan,bridging_rujukan_bpjs.nama_diagRujukan,"+
                    "bridging_rujukan_bpjs.poliRujukan,bridging_rujukan_bpjs.nama_poliRujukan  "+
                    "from bridging_sep inner join bridging_rujukan_bpjs on bridging_rujukan_bpjs.no_sep=bridging_sep.no_sep where "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_rujukan_bpjs.no_sep like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_sep.no_rawat like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_sep.nomr like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_sep.nama_pasien like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_rujukan_bpjs.no_rujukan like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_rujukan_bpjs.ppkDirujuk like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_rujukan_bpjs.nm_ppkDirujuk like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_rujukan_bpjs.diagRujukan like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_rujukan_bpjs.nama_diagRujukan like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_rujukan_bpjs.poliRujukan like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_rujukan_bpjs.nama_poliRujukan like ? or "+
                    "bridging_rujukan_bpjs.tglRujukan between ? and ? and bridging_rujukan_bpjs.catatan like ? order by bridging_rujukan_bpjs.tglRujukan");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getString(14),rs.getString(15)
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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        KdPpkRujukan1.setText("");
        NmPpkRujukan1.setText("");
        KdPoli1.setText("");
        NmPoli1.setText("");
        KdPenyakit1.setText("");
        NmPenyakit1.setText("");
        Catatan1.setText("");
    }

    

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TipeRujukan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KdPpkRujukan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NmPpkRujukan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KdPenyakit1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            NmPenyakit1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KdPoli1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            NmPoli1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            JenisPelayanan1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Catatan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Valid.SetTgl(TanggalRujukKeluar,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
        }
    }

    
    
    public void isCek(){
        BtnHapus.setEnabled(akses.getbpjs_rujukan_keluar());
        BtnEdit.setEnabled(akses.getbpjs_rujukan_keluar());
        BtnPrint.setEnabled(akses.getbpjs_rujukan_keluar());
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
            URL = link+"/Rujukan/delete";	

            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            requestJson ="{" +
                            "\"request\": {" +
                                "\"t_rujukan\": {" +
                                    "\"noRujukan\": \""+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"\"," +
                                    "\"user\": \""+user+"\"" +
                                "}" +
                            "}" +
                         "}";            
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,new HttpEntity<String>(requestJson,headers), String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("bridging_rujukan_bpjs","no_rujukan",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                Sequel.meghapus("rujuk","no_rujuk",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        } catch (Exception e) {            
        }
    }
   
}

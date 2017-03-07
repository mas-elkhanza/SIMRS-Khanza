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
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class BPJSMonitoringKlaim extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private PreparedStatement ps;
    private ResultSet rs;
    private final Properties prop = new Properties();
    private BPJSApi api=new BPJSApi();
    private BPJSCekReferensiFaskes faskes=new BPJSCekReferensiFaskes(null,false);
    private BPJSCekReferensiPenyakit penyakit=new BPJSCekReferensiPenyakit(null,false);
    private BPJSCekReferensiPoli poli=new BPJSCekReferensiPoli(null,false);
    private int i=0;
    private String URL="";
    private double tagihan=0,gruper=0,tarifrs=0;
   
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public BPJSMonitoringKlaim(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Tanggal SEP","Tanggal Rujukan", 
                "No.Rujukan","Kode PPK Rujukan","Nama PPK Rujukan","Kode PPK Pelayanan", 
                "Nama PPK Pelayanan","Jenis Pelayanan","Catatan", "Kode Diagnosa", 
                "Diagnosa Awal", "Kode Poli", "Nama Poli", "Kelas Rawat", "Laka Lantas", 
                "Lokasi Laka Lantas", "User Input","Tgl.Lahir","Peserta",
                "J.Kel","No.Kartu","Tanggal Pulang","Kode INACBG","Severity","Status SEP",
                "Tagihan","Gruper","Tarif RS","Untung/Rugi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        //tbDokter.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbDokter.getBackground()));
        tbDokter.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 33; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(200);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setPreferredWidth(180);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==19){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==20){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==21){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==22){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==23){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==24){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==25){
                column.setPreferredWidth(120);
            }else if(i==26){
                column.setPreferredWidth(170);
            }else if(i==27){
                column.setPreferredWidth(60);
            }else if(i==28){
                column.setPreferredWidth(100);
            }else if(i==29){
                column.setPreferredWidth(85);
            }else if(i==30){
                column.setPreferredWidth(85);
            }else if(i==31){
                column.setPreferredWidth(85);
            }else if(i==32){
                column.setPreferredWidth(85);
            }else{
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        KdPpkRujukan.setDocument(new batasInput((byte)5).getKata(KdPpkRujukan));
        KdPenyakit.setDocument(new batasInput((byte)25).getKata(KdPenyakit));        
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
        
        faskes.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(faskes.getTable().getSelectedRow()!= -1){                   
                    KdPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),3).toString());
                    NmPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),4).toString());
                }  
                KdPpkRujukan.requestFocus();
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
                    KdPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                    NmPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),2).toString());
                }  
                KdPenyakit.requestFocus();
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
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                }  
                KdPoli.requestFocus();
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
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        TglSEP1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        KdPpkRujukan = new widget.TextBox();
        KdPenyakit = new widget.TextBox();
        NmPpkRujukan = new widget.TextBox();
        NmPenyakit = new widget.TextBox();
        btnPPKRujukan = new widget.Button();
        btnPenyakit = new widget.Button();
        label12 = new widget.Label();
        TglSEP2 = new widget.Tanggal();
        label14 = new widget.Label();
        KdPoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        btnPoli = new widget.Button();
        jLabel13 = new widget.Label();
        JenisPelayanan = new widget.ComboBox();
        jLabel15 = new widget.Label();
        Kelas = new widget.ComboBox();
        jLabel14 = new widget.Label();
        Status = new widget.ComboBox();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Monitoring Verifikasi Klaim SEP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label11.setText("Tgl.SEP :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 10, 90, 23);

        TglSEP1.setEditable(false);
        TglSEP1.setDisplayFormat("dd-MM-yyyy");
        TglSEP1.setName("TglSEP1"); // NOI18N
        TglSEP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSEP1KeyPressed(evt);
            }
        });
        panelisi3.add(TglSEP1);
        TglSEP1.setBounds(93, 10, 110, 23);

        label16.setText("PPK Rujukan :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(351, 10, 99, 23);

        label13.setText("Diagnosa Awal :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(351, 40, 99, 23);

        KdPpkRujukan.setEditable(false);
        KdPpkRujukan.setName("KdPpkRujukan"); // NOI18N
        KdPpkRujukan.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(KdPpkRujukan);
        KdPpkRujukan.setBounds(453, 10, 75, 23);

        KdPenyakit.setEditable(false);
        KdPenyakit.setName("KdPenyakit"); // NOI18N
        KdPenyakit.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(KdPenyakit);
        KdPenyakit.setBounds(453, 40, 75, 23);

        NmPpkRujukan.setEditable(false);
        NmPpkRujukan.setName("NmPpkRujukan"); // NOI18N
        NmPpkRujukan.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmPpkRujukan);
        NmPpkRujukan.setBounds(530, 10, 200, 23);

        NmPenyakit.setEditable(false);
        NmPenyakit.setName("NmPenyakit"); // NOI18N
        NmPenyakit.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmPenyakit);
        NmPenyakit.setBounds(530, 40, 200, 23);

        btnPPKRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPPKRujukan.setMnemonic('1');
        btnPPKRujukan.setToolTipText("Alt+1");
        btnPPKRujukan.setName("btnPPKRujukan"); // NOI18N
        btnPPKRujukan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPPKRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPKRujukanActionPerformed(evt);
            }
        });
        panelisi3.add(btnPPKRujukan);
        btnPPKRujukan.setBounds(734, 10, 28, 23);

        btnPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenyakit.setMnemonic('2');
        btnPenyakit.setToolTipText("Alt+2");
        btnPenyakit.setName("btnPenyakit"); // NOI18N
        btnPenyakit.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenyakitActionPerformed(evt);
            }
        });
        panelisi3.add(btnPenyakit);
        btnPenyakit.setBounds(734, 40, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(206, 10, 27, 23);

        TglSEP2.setEditable(false);
        TglSEP2.setDisplayFormat("dd-MM-yyyy");
        TglSEP2.setName("TglSEP2"); // NOI18N
        TglSEP2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSEP2KeyPressed(evt);
            }
        });
        panelisi3.add(TglSEP2);
        TglSEP2.setBounds(235, 10, 110, 23);

        label14.setText("Poli Tujuan :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(351, 70, 99, 23);

        KdPoli.setEditable(false);
        KdPoli.setName("KdPoli"); // NOI18N
        KdPoli.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(KdPoli);
        KdPoli.setBounds(453, 70, 75, 23);

        NmPoli.setEditable(false);
        NmPoli.setName("NmPoli"); // NOI18N
        NmPoli.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmPoli);
        NmPoli.setBounds(530, 70, 200, 23);

        btnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli.setMnemonic('2');
        btnPoli.setToolTipText("Alt+2");
        btnPoli.setName("btnPoli"); // NOI18N
        btnPoli.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliActionPerformed(evt);
            }
        });
        panelisi3.add(btnPoli);
        btnPoli.setBounds(734, 70, 28, 23);

        jLabel13.setText("Jns.Pelayanan :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelisi3.add(jLabel13);
        jLabel13.setBounds(0, 40, 90, 23);

        JenisPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "1. Rawat Inap", "2. Rawat Jalan" }));
        JenisPelayanan.setName("JenisPelayanan"); // NOI18N
        JenisPelayanan.setOpaque(false);
        JenisPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPelayananKeyPressed(evt);
            }
        });
        panelisi3.add(JenisPelayanan);
        JenisPelayanan.setBounds(93, 40, 110, 23);

        jLabel15.setText("Kelas :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelisi3.add(jLabel15);
        jLabel15.setBounds(207, 40, 40, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "1. Kelas 1", "2. Kelas 2", "3. kelas 3" }));
        Kelas.setName("Kelas"); // NOI18N
        Kelas.setOpaque(false);
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        panelisi3.add(Kelas);
        Kelas.setBounds(250, 40, 95, 23);

        jLabel14.setText("Status :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelisi3.add(jLabel14);
        jLabel14.setBounds(0, 70, 90, 23);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "00 Klaim Baru", "10 Klaim Terima CBG", "21 Klaim Layak", "22 Klaim Tidak Layak", "23 Klaim Pending", "30 TerVerifikasi", "40 Proses Cabang" }));
        Status.setName("Status"); // NOI18N
        Status.setOpaque(false);
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        panelisi3.add(Status);
        Status.setBounds(93, 70, 252, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(290, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelisi1.add(BtnCari);

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 30));
        panelisi1.add(label9);

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
        panelisi1.add(BtnAll);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,TglSEP1);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPPKRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukanActionPerformed
        faskes.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        faskes.setLocationRelativeTo(internalFrame1);        
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukanActionPerformed

    private void btnPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenyakitActionPerformed
        penyakit.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penyakit.setLocationRelativeTo(internalFrame1);        
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnPenyakitActionPerformed

    private void TglSEP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSEP1KeyPressed
        Valid.pindah(evt,TCari,KdPpkRujukan);
    }//GEN-LAST:event_TglSEP1KeyPressed

    private void TglSEP2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSEP2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSEP2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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
        KdPpkRujukan.setText("");
        NmPpkRujukan.setText("");
        KdPenyakit.setText("");
        NmPenyakit.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        JenisPelayanan.setSelectedIndex(0);
        Kelas.setSelectedIndex(0);
        Status.setSelectedIndex(0);
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,11).toString()+"','"+
                                tabMode.getValueAt(i,29).toString()+"','"+
                                tabMode.getValueAt(i,30).toString()+"','"+
                                tabMode.getValueAt(i,31).toString()+"','"+
                                tabMode.getValueAt(i,32).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            }
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptBridgingMonitoringSEP.jrxml","report","::[ Monitoring Klaim SEP ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc",param);
            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void btnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliActionPerformed
        poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        poli.setLocationRelativeTo(internalFrame1);        
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoliActionPerformed

    private void JenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayananKeyPressed
        Valid.pindah(evt,TglSEP2,Kelas);
    }//GEN-LAST:event_JenisPelayananKeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt,JenisPelayanan,Status);
    }//GEN-LAST:event_KelasKeyPressed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSMonitoringKlaim dialog = new BPJSMonitoringKlaim(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ComboBox JenisPelayanan;
    private widget.TextBox KdPenyakit;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPpkRujukan;
    private widget.ComboBox Kelas;
    private widget.TextBox NmPenyakit;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPpkRujukan;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.Tanggal TglSEP1;
    private widget.Tanggal TglSEP2;
    private widget.Button btnPPKRujukan;
    private widget.Button btnPenyakit;
    private widget.Button btnPoli;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label16;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            tagihan=0;gruper=0;tarifrs=0;
            ps=koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"+
                    "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"+
                    "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                    "if(bridging_sep.jnspelayanan='1','1. Rawat Inap','2. Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"+
                    "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"+
                    "if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')),"+
                    "if(bridging_sep.lakalantas='1','1. Kasus Kecelakaan','2. Bukan Kasus Kecelakaan'),bridging_sep.lokasilaka,bridging_sep.user, "+
                    "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang from bridging_sep where "+
                    "bridging_sep.tglsep between ? and ? and jnspelayanan like ? and klsrawat like ? and nmppkrujukan like ? and nmdiagnosaawal like ? and nmpolitujuan like ? and bridging_sep.no_sep like ? or "+
                    "bridging_sep.tglsep between ? and ? and jnspelayanan like ? and klsrawat like ? and nmppkrujukan like ? and nmdiagnosaawal like ? and nmpolitujuan like ? and bridging_sep.nomr like ? or "+
                    "bridging_sep.tglsep between ? and ? and jnspelayanan like ? and klsrawat like ? and nmppkrujukan like ? and nmdiagnosaawal like ? and nmpolitujuan like ? and bridging_sep.nama_pasien like ? or "+
                    "bridging_sep.tglsep between ? and ? and jnspelayanan like ? and klsrawat like ? and nmppkrujukan like ? and nmdiagnosaawal like ? and nmpolitujuan like ? and bridging_sep.nmppkrujukan like ? or "+
                    "bridging_sep.tglsep between ? and ? and jnspelayanan like ? and klsrawat like ? and nmppkrujukan like ? and nmdiagnosaawal like ? and nmpolitujuan like ? and bridging_sep.diagawal like ? or "+
                    "bridging_sep.tglsep between ? and ? and jnspelayanan like ? and klsrawat like ? and nmppkrujukan like ? and nmdiagnosaawal like ? and nmpolitujuan like ? and bridging_sep.nmdiagnosaawal like ? or "+
                    "bridging_sep.tglsep between ? and ? and jnspelayanan like ? and klsrawat like ? and nmppkrujukan like ? and nmdiagnosaawal like ? and nmpolitujuan like ? and bridging_sep.no_rawat like ? or "+
                    "bridging_sep.tglsep between ? and ? and jnspelayanan like ? and klsrawat like ? and nmppkrujukan like ? and nmdiagnosaawal like ? and nmpolitujuan like ? and bridging_sep.nmpolitujuan like ? order by bridging_sep.tglsep");
            try {
                ps.setString(1,Valid.SetTgl(TglSEP1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(TglSEP2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(3,"%"+JenisPelayanan.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(4,"%"+Kelas.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(5,"%"+NmPpkRujukan.getText()+"%");
                ps.setString(6,"%"+NmPenyakit.getText()+"%");
                ps.setString(7,"%"+NmPoli.getText()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                ps.setString(9,Valid.SetTgl(TglSEP1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(10,Valid.SetTgl(TglSEP2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(11,"%"+JenisPelayanan.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(12,"%"+Kelas.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(13,"%"+NmPpkRujukan.getText()+"%");
                ps.setString(14,"%"+NmPenyakit.getText()+"%");
                ps.setString(15,"%"+NmPoli.getText()+"%");
                ps.setString(16,"%"+TCari.getText().trim()+"%");
                ps.setString(17,Valid.SetTgl(TglSEP1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(18,Valid.SetTgl(TglSEP2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(19,"%"+JenisPelayanan.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(20,"%"+Kelas.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(21,"%"+NmPpkRujukan.getText()+"%");
                ps.setString(22,"%"+NmPenyakit.getText()+"%");
                ps.setString(23,"%"+NmPoli.getText()+"%");
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(TglSEP1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(26,Valid.SetTgl(TglSEP2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(27,"%"+JenisPelayanan.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(28,"%"+Kelas.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(29,"%"+NmPpkRujukan.getText()+"%");
                ps.setString(30,"%"+NmPenyakit.getText()+"%");
                ps.setString(31,"%"+NmPoli.getText()+"%");
                ps.setString(32,"%"+TCari.getText().trim()+"%");
                ps.setString(33,Valid.SetTgl(TglSEP1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(34,Valid.SetTgl(TglSEP2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(35,"%"+JenisPelayanan.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(36,"%"+Kelas.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(37,"%"+NmPpkRujukan.getText()+"%");
                ps.setString(38,"%"+NmPenyakit.getText()+"%");
                ps.setString(39,"%"+NmPoli.getText()+"%");
                ps.setString(40,"%"+TCari.getText().trim()+"%");
                ps.setString(41,Valid.SetTgl(TglSEP1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(42,Valid.SetTgl(TglSEP2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(43,"%"+JenisPelayanan.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(44,"%"+Kelas.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(45,"%"+NmPpkRujukan.getText()+"%");
                ps.setString(46,"%"+NmPenyakit.getText()+"%");
                ps.setString(47,"%"+NmPoli.getText()+"%");
                ps.setString(48,"%"+TCari.getText().trim()+"%");
                ps.setString(49,Valid.SetTgl(TglSEP1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(50,Valid.SetTgl(TglSEP2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(51,"%"+JenisPelayanan.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(52,"%"+Kelas.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(53,"%"+NmPpkRujukan.getText()+"%");
                ps.setString(54,"%"+NmPenyakit.getText()+"%");
                ps.setString(55,"%"+NmPoli.getText()+"%");
                ps.setString(56,"%"+TCari.getText().trim()+"%");
                ps.setString(57,Valid.SetTgl(TglSEP1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(58,Valid.SetTgl(TglSEP2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(59,"%"+JenisPelayanan.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(60,"%"+Kelas.getSelectedItem().toString().substring(0,1).replaceAll("S","")+"%");
                ps.setString(61,"%"+NmPpkRujukan.getText()+"%");
                ps.setString(62,"%"+NmPenyakit.getText()+"%");
                ps.setString(63,"%"+NmPoli.getText()+"%");
                ps.setString(64,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){                    
                    try {
                        prop.loadFromXML(new FileInputStream("setting/database.xml"));
                        URL = prop.getProperty("URLAPIBPJS")+"/sep/integrated/Kunjungan/sep/"+rs.getString(1);	

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                        headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                        headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                        headers.add("X-Signature",api.getHmac());                        
                        HttpEntity requestEntity = new HttpEntity(headers);
                        RestTemplate rest = new RestTemplate();	

                        //System.out.println("Notifikasi : "+rs.getString(1));
                        //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                        JsonNode nameNode = root.path("metadata");
                        //System.out.println("code : "+nameNode.path("code").asText());
                        //System.out.println("message : "+nameNode.path("message").asText());
                        if(nameNode.path("message").asText().equals("OK")){
                            //"Kode INACBG","Severity","Status SEP","Tagihan","Gruper","Tarif RS"                            
                            JsonNode response = root.path("response");
                            if(response.path("list").isArray()){
                                for(JsonNode list:response.path("list")){                                    
                                    if(!Status.getSelectedItem().toString().equals("Semua")){
                                        if(list.path("statSep").path("kdStatSep").asText().equals(Status.getSelectedItem().toString().substring(0,2))){
                                            tabMode.addRow(new Object[]{
                                                rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                                                rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                                                rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
                                                rs.getString(13),rs.getString(14),rs.getString(14)+" "+rs.getString(15),rs.getString(16),
                                                rs.getString(17),rs.getString(18),rs.getString(19),rs.getString(20),
                                                rs.getString(21),rs.getString(22),rs.getString(23),rs.getString(24),
                                                rs.getString(25),rs.getString(26),
                                                list.path("Inacbg").path("kdInacbg").asText()+" "+list.path("Inacbg").path("nmInacbg").asText(),
                                                list.path("Inacbg").path("kdSeverity").asText(),list.path("statSep").path("nmStatSep").asText(),
                                                Valid.SetAngka(list.path("byTagihan").asDouble()),Valid.SetAngka(list.path("byTarifGruper").asDouble()),
                                                Valid.SetAngka(list.path("byTarifRS").asDouble()),Valid.SetAngka((list.path("byTagihan").asDouble()-list.path("byTarifRS").asDouble()))
                                            }); 
                                            tagihan=list.path("byTagihan").asDouble();
                                            gruper=list.path("byTarifGruper").asDouble();
                                            tarifrs=list.path("byTarifRS").asDouble();
                                        }
                                    }else{
                                        tabMode.addRow(new Object[]{
                                            rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                                            rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                                            rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
                                            rs.getString(13),rs.getString(14),rs.getString(14)+" "+rs.getString(15),rs.getString(16),
                                            rs.getString(17),rs.getString(18),rs.getString(19),rs.getString(20),
                                            rs.getString(21),rs.getString(22),rs.getString(23),rs.getString(24),
                                            rs.getString(25),rs.getString(26),
                                            list.path("Inacbg").path("kdInacbg").asText()+" "+list.path("Inacbg").path("nmInacbg").asText(),
                                            list.path("Inacbg").path("kdSeverity").asText(),list.path("statSep").path("nmStatSep").asText(),
                                            Valid.SetAngka(list.path("byTagihan").asDouble()),Valid.SetAngka(list.path("byTarifGruper").asDouble()),
                                            Valid.SetAngka(list.path("byTarifRS").asDouble()),Valid.SetAngka((list.path("byTagihan").asDouble()-list.path("byTarifRS").asDouble()))
                                        }); 
                                        tagihan=list.path("byTagihan").asDouble();
                                        gruper=list.path("byTarifGruper").asDouble();
                                        tarifrs=list.path("byTarifRS").asDouble();
                                    }                                        
                                }
                            }
                        }else {
                            JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
                        }  
                    } catch (Exception e) {
                        System.out.println("Notifikasi Peserta : "+e);
                        if(e.toString().contains("UnknownHostException")){
                            JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                        }
                    }               
                }
                tabMode.addRow(new Object[]{
                    "Total",":","","","","","","","","","","","","","","","","","","","","","","","","","","","",
                    Valid.SetAngka(tagihan),Valid.SetAngka(gruper),Valid.SetAngka(tarifrs),Valid.SetAngka((tagihan-tarifrs))
                }); 
                                   
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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

    public void emptTeks() {
        TCari.requestFocus();        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(var.getbpjs_monitoring_klaim());
    }
    
}

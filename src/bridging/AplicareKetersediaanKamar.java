/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatanRalan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package bridging;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import fungsi.koneksiDB;
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
import simrskhanza.DlgCariBangsal;

/**
 *
 * @author dosen
 */
public final class AplicareKetersediaanKamar extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;    
    private int i=0;
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private AplicareCekReferensiKamar referensi=new AplicareCekReferensiKamar(null,false);
    private String requestJson,URL="",kodeppk=Sequel.cariIsi("select kode_ppk from setting"),CONSIDAPIAPLICARE="";
    private BPJSApiAplicare api=new BPJSApiAplicare();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper= new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;

    /** Creates new form DlgJnsPerawatanRalan
     * @param parent
     * @param modal */
    public AplicareKetersediaanKamar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"P","Kode Kelas Aplicare","Kode Ruang","Kamar/Ruang","Kelas","Kapasitas","Tersedia",
                      "Tersedia Pria & Wanita","Tersedia Pria","Tersedia Wanita"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(115);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(90);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        Tersedia.setDocument(new batasInput((byte)4).getOnlyAngka(Tersedia)); 
        Kapasitas.setDocument(new batasInput((byte)4).getOnlyAngka(Kapasitas)); 
        TersediaPW.setDocument(new batasInput((byte)4).getOnlyAngka(TersediaPW)); 
        TersediaPria.setDocument(new batasInput((byte)4).getOnlyAngka(TersediaPria)); 
        TersediaWanita.setDocument(new batasInput((byte)4).getOnlyAngka(TersediaWanita)); 
        KdKelas.setDocument(new batasInput((byte)15).getKata(KdKelas)); 
        KdKamar.setDocument(new batasInput((byte)5).getKata(KdKamar)); 
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){                   
                    KdKamar.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    NmKamar.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                }     
                isCariKetersediaan();
                KdKamar.requestFocus();
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
        
        referensi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(referensi.getTable().getSelectedRow()!= -1){                   
                    KdKelas.setText(referensi.getTable().getValueAt(referensi.getTable().getSelectedRow(),1).toString());
                    NmKelas.setText(referensi.getTable().getValueAt(referensi.getTable().getSelectedRow(),2).toString());
                }     
                KdKamar.requestFocus();
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
        
        referensi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    referensi.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            URL = koneksiDB.URLAPIAPLICARE();	
            CONSIDAPIAPLICARE=koneksiDB.CONSIDAPIAPLICARE();
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
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel8 = new widget.Label();
        Kapasitas = new widget.TextBox();
        jLabel4 = new widget.Label();
        KdKelas = new widget.TextBox();
        NmKelas = new widget.TextBox();
        btnKelas = new widget.Button();
        jLabel19 = new widget.Label();
        KdKamar = new widget.TextBox();
        NmKamar = new widget.TextBox();
        btnKamar = new widget.Button();
        jLabel5 = new widget.Label();
        Kelas = new widget.ComboBox();
        jLabel9 = new widget.Label();
        Tersedia = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        TersediaPW = new widget.TextBox();
        TersediaPria = new widget.TextBox();
        jLabel12 = new widget.Label();
        TersediaWanita = new widget.TextBox();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Data Ketersediaan Kamar Aplicare BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 130));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 197));
        FormInput.setLayout(null);

        jLabel8.setText("Kapasitas/Jumlah Bed :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(244, 72, 120, 23);

        Kapasitas.setText("0");
        Kapasitas.setHighlighter(null);
        Kapasitas.setName("Kapasitas"); // NOI18N
        Kapasitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KapasitasKeyPressed(evt);
            }
        });
        FormInput.add(Kapasitas);
        Kapasitas.setBounds(367, 72, 50, 23);

        jLabel4.setText("Kode Kelas Aplicare :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 12, 112, 23);

        KdKelas.setEditable(false);
        KdKelas.setHighlighter(null);
        KdKelas.setName("KdKelas"); // NOI18N
        KdKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdKelasActionPerformed(evt);
            }
        });
        KdKelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKelasKeyPressed(evt);
            }
        });
        FormInput.add(KdKelas);
        KdKelas.setBounds(116, 12, 77, 23);

        NmKelas.setEditable(false);
        NmKelas.setHighlighter(null);
        NmKelas.setName("NmKelas"); // NOI18N
        NmKelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKelasKeyPressed(evt);
            }
        });
        FormInput.add(NmKelas);
        NmKelas.setBounds(196, 12, 190, 23);

        btnKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKelas.setMnemonic('1');
        btnKelas.setToolTipText("Alt+1");
        btnKelas.setName("btnKelas"); // NOI18N
        btnKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelasActionPerformed(evt);
            }
        });
        btnKelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKelasKeyPressed(evt);
            }
        });
        FormInput.add(btnKelas);
        btnKelas.setBounds(389, 12, 28, 23);

        jLabel19.setText("Kamar/Ruang :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 42, 112, 23);

        KdKamar.setEditable(false);
        KdKamar.setHighlighter(null);
        KdKamar.setName("KdKamar"); // NOI18N
        KdKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKamarKeyPressed(evt);
            }
        });
        FormInput.add(KdKamar);
        KdKamar.setBounds(116, 42, 77, 23);

        NmKamar.setEditable(false);
        NmKamar.setName("NmKamar"); // NOI18N
        FormInput.add(NmKamar);
        NmKamar.setBounds(196, 42, 190, 23);

        btnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKamar.setMnemonic('3');
        btnKamar.setToolTipText("ALt+3");
        btnKamar.setName("btnKamar"); // NOI18N
        btnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarActionPerformed(evt);
            }
        });
        FormInput.add(btnKamar);
        btnKamar.setBounds(389, 42, 28, 23);

        jLabel5.setText("Kelas :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 72, 112, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Kelas Utama", "Kelas VIP", "Kelas VVIP" }));
        Kelas.setName("Kelas"); // NOI18N
        Kelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KelasItemStateChanged(evt);
            }
        });
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormInput.add(Kelas);
        Kelas.setBounds(116, 72, 120, 23);

        jLabel9.setText("Tersedia :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(418, 12, 77, 23);

        Tersedia.setText("0");
        Tersedia.setHighlighter(null);
        Tersedia.setName("Tersedia"); // NOI18N
        Tersedia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TersediaKeyPressed(evt);
            }
        });
        FormInput.add(Tersedia);
        Tersedia.setBounds(498, 12, 50, 23);

        jLabel10.setText("Tersedia Pria :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(418, 42, 77, 23);

        jLabel11.setText("Tersedia Pria & Wanita :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(548, 12, 125, 23);

        TersediaPW.setText("0");
        TersediaPW.setHighlighter(null);
        TersediaPW.setName("TersediaPW"); // NOI18N
        TersediaPW.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TersediaPWKeyPressed(evt);
            }
        });
        FormInput.add(TersediaPW);
        TersediaPW.setBounds(676, 12, 50, 23);

        TersediaPria.setText("0");
        TersediaPria.setHighlighter(null);
        TersediaPria.setName("TersediaPria"); // NOI18N
        TersediaPria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TersediaPriaKeyPressed(evt);
            }
        });
        FormInput.add(TersediaPria);
        TersediaPria.setBounds(498, 42, 50, 23);

        jLabel12.setText("Tersedia Wanita :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(548, 42, 125, 23);

        TersediaWanita.setText("0");
        TersediaWanita.setHighlighter(null);
        TersediaWanita.setName("TersediaWanita"); // NOI18N
        TersediaWanita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TersediaWanitaKeyPressed(evt);
            }
        });
        FormInput.add(TersediaWanita);
        TersediaWanita.setBounds(676, 42, 50, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void KapasitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KapasitasKeyPressed
        Valid.pindah(evt,Kelas,Tersedia);
}//GEN-LAST:event_KapasitasKeyPressed

    private void KdKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdKelasActionPerformed

}//GEN-LAST:event_KdKelasActionPerformed

    private void KdKelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKelasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKelasActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,KdKamar);
        }
}//GEN-LAST:event_KdKelasKeyPressed

    private void NmKelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKelasKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_NmKelasKeyPressed

    private void btnKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelasActionPerformed
        referensi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        referensi.setLocationRelativeTo(internalFrame1);
        referensi.setVisible(true);
}//GEN-LAST:event_btnKelasActionPerformed

    private void btnKelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKelasKeyPressed
        
}//GEN-LAST:event_btnKelasKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(KdKelas.getText().trim().equals("")||NmKelas.getText().trim().equals("")){
            Valid.textKosong(KdKelas,"Kode Kelas Aplicare");
        }else if(KdKamar.getText().trim().equals("")||NmKamar.getText().trim().equals("")){
            Valid.textKosong(KdKamar,"Kode Kamar/Ruang");
        }else if(Kapasitas.getText().trim().equals("")){
            Valid.textKosong(Kapasitas,"Kapasitas");
        }else if(Tersedia.getText().trim().equals("")){
            Valid.textKosong(Tersedia,"Tersedia");
        }else if(TersediaPW.getText().trim().equals("")){
            Valid.textKosong(TersediaPW,"Tersedia Pria & Wanita");
        }else if(TersediaPria.getText().trim().equals("")){
            Valid.textKosong(TersediaPria,"Tersedia Pria");
        }else if(TersediaWanita.getText().trim().equals("")){
            Valid.textKosong(TersediaWanita,"Tersedia Wanita");
        }else{
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("X-Cons-ID",CONSIDAPIAPLICARE);
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
                requestJson ="{\"kodekelas\":\""+KdKelas.getText()+"\", "+
                              "\"koderuang\":\""+KdKamar.getText()+"\","+ 
                              "\"namaruang\":\""+NmKamar.getText()+"\","+ 
                              "\"kapasitas\":\""+Kapasitas.getText()+"\","+ 
                              "\"tersedia\":\""+Tersedia.getText()+"\","+
                              "\"tersediapria\":\""+TersediaPria.getText()+"\","+ 
                              "\"tersediawanita\":\""+TersediaWanita.getText()+"\","+ 
                              "\"tersediapriawanita\":\""+TersediaPW.getText()+"\""+
                              "}";
                requestEntity = new HttpEntity(requestJson,headers);
                //System.out.println(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                root = mapper.readTree(api.getRest().exchange(URL+"/rest/bed/create/"+kodeppk, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metadata");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                response = root.path("response");
                if(nameNode.path("message").asText().equals("Data berhasil disimpan.")){
                    if(Sequel.menyimpantf("aplicare_ketersediaan_kamar","?,?,?,?,?,?,?,?","Data",8,new String[]{
                            KdKelas.getText(),KdKamar.getText(),Kelas.getSelectedItem().toString(),Kapasitas.getText(),
                            Tersedia.getText(),TersediaPria.getText(),TersediaWanita.getText(),TersediaPW.getText()
                        })==true){
                            emptTeks();
                            tampil();
                    }                     
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
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{Valid.pindah(evt, TersediaWanita, BtnBatal);}
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for(i=0;i<tbJnsPerawatan.getRowCount();i++){ 
            if(tbJnsPerawatan.getValueAt(i,0).toString().equals("true")){
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("X-Cons-ID",CONSIDAPIAPLICARE);
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                    headers.add("X-Signature",api.getHmac());
                    requestJson ="{\"kodekelas\":\""+tbJnsPerawatan.getValueAt(i,1).toString()+"\", "+
                                  "\"koderuang\":\""+tbJnsPerawatan.getValueAt(i,2).toString()+"\""+ 
                                  "}";
                    requestEntity = new HttpEntity(requestJson,headers);
                    //System.out.println(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                    root = mapper.readTree(api.getRest().exchange(URL+"/rest/bed/delete/"+kodeppk, HttpMethod.POST, requestEntity, String.class).getBody());
                    nameNode = root.path("metadata");
                    //System.out.println("code : "+nameNode.path("code").asText());
                    //System.out.println("message : "+nameNode.path("message").asText());
                    response = root.path("response");
                    if(nameNode.path("message").asText().equals("Data berhasil dihapus.")){
                        Sequel.queryu2("delete from aplicare_ketersediaan_kamar where kode_kelas_aplicare=? and kd_bangsal=? and kelas=?",3,new String[]{
                            tbJnsPerawatan.getValueAt(i,1).toString(),tbJnsPerawatan.getValueAt(i,2).toString(),tbJnsPerawatan.getValueAt(i,4).toString()
                        });
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
        BtnCariActionPerformed(evt);
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(KdKelas.getText().trim().equals("")){
            Valid.textKosong(KdKelas,"Kode Kelas Aplicare");
        }else if(KdKamar.getText().trim().equals("")||NmKamar.getText().trim().equals("")){
            Valid.textKosong(KdKamar,"Kode Kamar/Ruang");
        }else if(Kapasitas.getText().trim().equals("")){
            Valid.textKosong(Kapasitas,"Kapasitas");
        }else if(Tersedia.getText().trim().equals("")){
            Valid.textKosong(Tersedia,"Tersedia");
        }else if(TersediaPW.getText().trim().equals("")){
            Valid.textKosong(TersediaPW,"Tersedia Pria & Wanita");
        }else if(TersediaPria.getText().trim().equals("")){
            Valid.textKosong(TersediaPria,"Tersedia Pria");
        }else if(TersediaWanita.getText().trim().equals("")){
            Valid.textKosong(TersediaWanita,"Tersedia Wanita");
        }else{
            try {     
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("X-Cons-ID",CONSIDAPIAPLICARE);
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
                requestJson ="{\"kodekelas\":\""+KdKelas.getText()+"\", "+
                              "\"koderuang\":\""+KdKamar.getText()+"\","+ 
                              "\"namaruang\":\""+NmKamar.getText()+"\","+ 
                              "\"kapasitas\":\""+Kapasitas.getText()+"\","+ 
                              "\"tersedia\":\""+Tersedia.getText()+"\","+
                              "\"tersediapria\":\""+TersediaPria.getText()+"\","+ 
                              "\"tersediawanita\":\""+TersediaWanita.getText()+"\","+ 
                              "\"tersediapriawanita\":\""+TersediaPW.getText()+"\""+
                              "}";
                requestEntity = new HttpEntity(requestJson,headers);
                //System.out.println(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                root = mapper.readTree(api.getRest().exchange(URL+"/rest/bed/update/"+kodeppk, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metadata");
                //System.out.println("code : "+nameNode.path("code").asText());
                //System.out.println("message : "+nameNode.path("message").asText());
                response = root.path("response");
                if(nameNode.path("message").asText().equals("Data berhasil diupdate.")){
                    if(Sequel.mengedittf("aplicare_ketersediaan_kamar","kode_kelas_aplicare=? and kd_bangsal=? and kelas=?",
                        "kode_kelas_aplicare=?,kd_bangsal=?,kelas=?,kapasitas=?,tersedia=?,tersediapria=?,tersediawanita=?,tersediapriawanita=?",11,new String[]{
                        KdKelas.getText(),KdKamar.getText(),Kelas.getSelectedItem().toString(),Kapasitas.getText(),
                        Tersedia.getText(),TersediaPria.getText(),TersediaWanita.getText(),TersediaPW.getText(),
                        tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString(),
                        tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString(),
                        tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString()
                      })==true){
                        emptTeks();
                        tampil();
                    }                     
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
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                param.put("parameter","%"+TCari.getText().trim()+"%"); 
                Valid.MyReport("rptKamarAplicare.jasper","report","::[ Data Ketersediaan Kamar Aplicare]::",param);            
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

        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void KdKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKamarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKamarActionPerformed(null);
        }else{
            Valid.pindah(evt,KdKelas,Kelas);
        }
}//GEN-LAST:event_KdKamarKeyPressed

private void btnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarActionPerformed
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
}//GEN-LAST:event_btnKamarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        emptTeks();
    }//GEN-LAST:event_formWindowOpened

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isCariKetersediaan();
            Kapasitas.requestFocus();            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KdKamar.requestFocus();
        }
    }//GEN-LAST:event_KelasKeyPressed

    private void TersediaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TersediaKeyPressed
        Valid.pindah(evt,Kapasitas,TersediaPW);
    }//GEN-LAST:event_TersediaKeyPressed

    private void TersediaPWKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TersediaPWKeyPressed
        Valid.pindah(evt,Tersedia,TersediaPria);
    }//GEN-LAST:event_TersediaPWKeyPressed

    private void TersediaPriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TersediaPriaKeyPressed
        Valid.pindah(evt,TersediaPW,TersediaWanita);
    }//GEN-LAST:event_TersediaPriaKeyPressed

    private void TersediaWanitaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TersediaWanitaKeyPressed
        Valid.pindah(evt,TersediaPria,BtnSimpan);
    }//GEN-LAST:event_TersediaWanitaKeyPressed

    private void KelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KelasItemStateChanged
        isCariKetersediaan();
    }//GEN-LAST:event_KelasItemStateChanged

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            AplicareKetersediaanKamar dialog = new AplicareKetersediaanKamar(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kapasitas;
    private widget.TextBox KdKamar;
    private widget.TextBox KdKelas;
    private widget.ComboBox Kelas;
    private widget.Label LCount;
    private widget.TextBox NmKamar;
    private widget.TextBox NmKelas;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox Tersedia;
    private widget.TextBox TersediaPW;
    private widget.TextBox TersediaPria;
    private widget.TextBox TersediaWanita;
    private widget.Button btnKamar;
    private widget.Button btnKelas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel19;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
           ps=koneksi.prepareStatement(
                   "select aplicare_ketersediaan_kamar.kode_kelas_aplicare,aplicare_ketersediaan_kamar.kd_bangsal,"+
                   "bangsal.nm_bangsal,aplicare_ketersediaan_kamar.kelas,aplicare_ketersediaan_kamar.kapasitas,"+
                   "aplicare_ketersediaan_kamar.tersedia,aplicare_ketersediaan_kamar.tersediapria,"+
                   "aplicare_ketersediaan_kamar.tersediawanita,aplicare_ketersediaan_kamar.tersediapriawanita "+
                   "from aplicare_ketersediaan_kamar inner join bangsal on aplicare_ketersediaan_kamar.kd_bangsal=bangsal.kd_bangsal where "+
                   "aplicare_ketersediaan_kamar.kode_kelas_aplicare like ? or "+
                   "aplicare_ketersediaan_kamar.kd_bangsal like ? or "+
                   "bangsal.nm_bangsal like ? or "+
                   "aplicare_ketersediaan_kamar.kelas like ? order by aplicare_ketersediaan_kamar.kode_kelas_aplicare");
            try {
                ps.setString(1,"%"+TCari.getText()+"%");
                ps.setString(2,"%"+TCari.getText()+"%");
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("kode_kelas_aplicare"),rs.getString("kd_bangsal"),
                        rs.getString("nm_bangsal"),rs.getString("kelas"),rs.getString("kapasitas"),
                        rs.getString("tersedia"),rs.getString("tersediapriawanita"),
                        rs.getString("tersediapria"),rs.getString("tersediawanita"),
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif Ketersediaan : "+e);
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
    }

    public void emptTeks() {
        KdKelas.setText("");
        NmKelas.setText("");
        KdKamar.setText("");
        NmKamar.setText("");
        Kapasitas.setText("0");
        Tersedia.setText("0");
        TersediaPW.setText("0");
        TersediaPria.setText("0");
        TersediaWanita.setText("0");
        KdKelas.requestFocus();
    }

    private void getData() {
       if(tbJnsPerawatan.getSelectedRow()!= -1){
           KdKelas.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
           KdKamar.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
           NmKamar.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
           Kelas.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString());
           Kapasitas.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),5).toString());
           Tersedia.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),6).toString());
           TersediaPW.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
           TersediaPria.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString());
           TersediaWanita.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),9).toString());
       }
    }

    
   
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,130));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getaplicare_ketersediaan_kamar());
        BtnHapus.setEnabled(akses.getaplicare_ketersediaan_kamar());
        BtnEdit.setEnabled(akses.getaplicare_ketersediaan_kamar());
        BtnPrint.setEnabled(akses.getaplicare_ketersediaan_kamar());
    }
    
    public JTable getTable(){
        return tbJnsPerawatan;
    }    

    private void isCariKetersediaan() {
        if(!KdKamar.getText().equals("")){
            Kapasitas.setText(Sequel.cariIsi("select count(kd_kamar) from kamar where statusdata='1' and kelas='"+Kelas.getSelectedItem()+"' and kd_bangsal=?",KdKamar.getText()));
            Tersedia.setText(Sequel.cariIsi("select count(kd_kamar) from kamar where statusdata='1' and kelas='"+Kelas.getSelectedItem()+"' and status='KOSONG' and kd_bangsal=?",KdKamar.getText()));
            TersediaPW.setText(Tersedia.getText());
        }
    }
    
    
    

    
}

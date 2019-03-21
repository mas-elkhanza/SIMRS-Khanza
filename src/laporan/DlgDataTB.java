/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package laporan;
import bridging.YaskiReferensiKabupaten;
import bridging.YaskiReferensiKecamatan;
import bridging.YaskiReferensiKelurahan;
import bridging.YaskiReferensiPropinsi;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import restore.DlgRestoreTarifOperasi;

/**
 *
 * @author dosen
 */
public final class DlgDataTB extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private RestTemplate rest;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private YaskiReferensiPropinsi propinsi=new YaskiReferensiPropinsi(null,false);
    private YaskiReferensiKabupaten kabupaten=new YaskiReferensiKabupaten(null,false);
    private YaskiReferensiKecamatan kecamatan=new YaskiReferensiKecamatan(null,false);
    private YaskiReferensiKelurahan kelurahan=new YaskiReferensiKelurahan(null,false);

    /** Creates new form DlgJnsPerawatan
     * @param parent
     * @param modal */
    public DlgDataTB(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"P","Kode Paket","Nama Operasi","Kategori","Operator 1","Operator 2","Operator 3",
                      "Asisten Op 1","Asisten Op 2","Asisten Op 3","Instrumen","dr Anestesi","Asisten Anes 1","Asisten Anes 2","dr Anak",
                      "Perawat Resus","Bidan 1","Bidan 2","Bidan 3","Perawat Luar","Alat","Sewa OK/VK",
                      "Akomodasi","N.M.S.","Onloop 1","Onloop 2","Onloop 3","Onloop 4","Onloop 5","Sarpras","dr Pj Anak","dr Umum",
                      "Total","Jenis Bayar","Kelas"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.String.class,java.lang.String.class
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

        for (i = 0; i < 35; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(220);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==33){
                column.setPreferredWidth(200);
            }else if(i==34){
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(85);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdProp.setDocument(new batasInput((byte)10).getOnlyAngka(KdProp));
        if(koneksiDB.cariCepat().equals("aktif")){
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
        
        propinsi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(propinsi.getTable().getSelectedRow()!= -1){                   
                    KdProp.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(),1).toString());
                    Propinsi.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(),2).toString().toUpperCase());
                    KdProp.requestFocus();
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
        
        propinsi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    propinsi.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        kabupaten.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kabupaten.getTable().getSelectedRow()!= -1){                   
                    KdKab.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(),1).toString());
                    Kabupaten.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(),2).toString().toUpperCase());
                    KdKab.requestFocus();
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
        
        kabupaten.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kabupaten.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        kecamatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kecamatan.getTable().getSelectedRow()!= -1){                   
                    KdKec.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(),1).toString());
                    Kecamatan.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(),2).toString().toUpperCase());
                    KdKec.requestFocus();
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
        
        kecamatan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kecamatan.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kelurahan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kelurahan.getTable().getSelectedRow()!= -1){                   
                    KdKel.setText(kelurahan.getTable().getValueAt(kelurahan.getTable().getSelectedRow(),1).toString());
                    Kelurahan.setText(kelurahan.getTable().getValueAt(kelurahan.getTable().getSelectedRow(),2).toString().toUpperCase());
                    KdKel.requestFocus();
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
        
        kelurahan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kelurahan.dispose();
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnRestore = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
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
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TNmPasien = new widget.TextBox();
        JK = new widget.TextBox();
        jLabel4 = new widget.Label();
        Tanggal = new widget.TextBox();
        jLabel5 = new widget.Label();
        Umur = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        NoKartu = new widget.TextBox();
        jLabel10 = new widget.Label();
        NIK = new widget.TextBox();
        jLabel11 = new widget.Label();
        CmbJk = new widget.ComboBox();
        jLabel12 = new widget.Label();
        DTPLahir = new widget.Tanggal();
        jLabel13 = new widget.Label();
        Alamat = new widget.TextBox();
        Kelurahan = new widget.TextBox();
        BtnKelurahan = new widget.Button();
        Kecamatan = new widget.TextBox();
        BtnKecamatan = new widget.Button();
        BtnPropinsi = new widget.Button();
        Propinsi = new widget.TextBox();
        BtnKabupaten = new widget.Button();
        Kabupaten = new widget.TextBox();
        BtnCari1 = new widget.Button();
        KdProp = new widget.TextBox();
        BtnCari2 = new widget.Button();
        KdKel = new widget.TextBox();
        KdKec = new widget.TextBox();
        KdKab = new widget.TextBox();
        BtnCari3 = new widget.Button();
        BtnCari4 = new widget.Button();
        CmbJk1 = new widget.ComboBox();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        Alamat1 = new widget.TextBox();
        jLabel16 = new widget.Label();
        CmbJk2 = new widget.ComboBox();
        jLabel17 = new widget.Label();
        CmbJk3 = new widget.ComboBox();
        jLabel18 = new widget.Label();
        CmbJk4 = new widget.ComboBox();
        jLabel19 = new widget.Label();
        CmbJk5 = new widget.ComboBox();
        jLabel20 = new widget.Label();
        CmbJk6 = new widget.ComboBox();
        jLabel21 = new widget.Label();
        CmbJk7 = new widget.ComboBox();
        jLabel22 = new widget.Label();
        CmbJk8 = new widget.ComboBox();
        jLabel23 = new widget.Label();
        DTPLahir1 = new widget.Tanggal();
        jLabel24 = new widget.Label();
        Alamat2 = new widget.TextBox();
        jLabel25 = new widget.Label();
        CmbJk9 = new widget.ComboBox();
        Alamat3 = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        CmbJk10 = new widget.ComboBox();
        jLabel28 = new widget.Label();
        CmbJk11 = new widget.ComboBox();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        CmbJk12 = new widget.ComboBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        CmbJk14 = new widget.ComboBox();
        jLabel36 = new widget.Label();
        Alamat5 = new widget.TextBox();
        Alamat6 = new widget.TextBox();
        jLabel37 = new widget.Label();
        CmbJk15 = new widget.ComboBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRestore.setBackground(new java.awt.Color(255, 255, 254));
        MnRestore.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRestore.setForeground(new java.awt.Color(70, 70, 70));
        MnRestore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRestore.setText("Data Sampah");
        MnRestore.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRestore.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRestore.setName("MnRestore"); // NOI18N
        MnRestore.setPreferredSize(new java.awt.Dimension(200, 28));
        MnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRestoreActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRestore);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pasien Teridentifikasi TB ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        jLabel6.setText("Key Word :");
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

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
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

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 257));
        FormInput.setLayout(null);

        jLabel3.setText("Pasien :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 81, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 140, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(226, 10, 80, 23);

        TNmPasien.setEditable(false);
        TNmPasien.setHighlighter(null);
        TNmPasien.setName("TNmPasien"); // NOI18N
        FormInput.add(TNmPasien);
        TNmPasien.setBounds(308, 10, 300, 23);

        JK.setEditable(false);
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(610, 10, 95, 23);

        jLabel4.setText("Tgl.Lahir :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 40, 81, 23);

        Tanggal.setEditable(false);
        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(84, 40, 100, 23);

        jLabel5.setText("Umur :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(199, 40, 50, 23);

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        Umur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurKeyPressed(evt);
            }
        });
        FormInput.add(Umur);
        Umur.setBounds(252, 40, 45, 23);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Th");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(299, 40, 30, 23);

        jLabel9.setText("No.JKN :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(330, 40, 55, 23);

        NoKartu.setEditable(false);
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        NoKartu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKartuKeyPressed(evt);
            }
        });
        FormInput.add(NoKartu);
        NoKartu.setBounds(388, 40, 120, 23);

        jLabel10.setText("NIK :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(517, 40, 40, 23);

        NIK.setEditable(false);
        NIK.setHighlighter(null);
        NIK.setName("NIK"); // NOI18N
        NIK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIKKeyPressed(evt);
            }
        });
        FormInput.add(NIK);
        NIK.setBounds(560, 40, 145, 23);

        jLabel11.setText("Periode Laporan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 160, 100, 23);

        CmbJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1=Januari - Maret", "2=April - Juni", "3=Juli - September", "4=Oktober - Desember" }));
        CmbJk.setName("CmbJk"); // NOI18N
        CmbJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJkKeyPressed(evt);
            }
        });
        FormInput.add(CmbJk);
        CmbJk.setBounds(103, 160, 163, 23);

        jLabel12.setText("Tanggal :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(270, 160, 54, 23);

        DTPLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-03-2019 06:22:28" }));
        DTPLahir.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DTPLahir.setName("DTPLahir"); // NOI18N
        DTPLahir.setOpaque(false);
        DTPLahir.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPLahirItemStateChanged(evt);
            }
        });
        DTPLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPLahirKeyPressed(evt);
            }
        });
        FormInput.add(DTPLahir);
        DTPLahir.setBounds(327, 160, 130, 23);

        jLabel13.setText("Alamat :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 70, 81, 23);

        Alamat.setEditable(false);
        Alamat.setText("ALAMAT");
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AlamatMouseMoved(evt);
            }
        });
        Alamat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AlamatMouseExited(evt);
            }
        });
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        FormInput.add(Alamat);
        Alamat.setBounds(84, 70, 621, 23);

        Kelurahan.setEditable(false);
        Kelurahan.setText("KELURAHAN");
        Kelurahan.setHighlighter(null);
        Kelurahan.setName("Kelurahan"); // NOI18N
        Kelurahan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KelurahanMouseMoved(evt);
            }
        });
        Kelurahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KelurahanMouseExited(evt);
            }
        });
        Kelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelurahanKeyPressed(evt);
            }
        });
        FormInput.add(Kelurahan);
        Kelurahan.setBounds(146, 100, 180, 23);

        BtnKelurahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahan.setMnemonic('2');
        BtnKelurahan.setToolTipText("ALt+2");
        BtnKelurahan.setName("BtnKelurahan"); // NOI18N
        BtnKelurahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanActionPerformed(evt);
            }
        });
        FormInput.add(BtnKelurahan);
        BtnKelurahan.setBounds(360, 100, 28, 23);

        Kecamatan.setEditable(false);
        Kecamatan.setText("KECAMATAN");
        Kecamatan.setHighlighter(null);
        Kecamatan.setName("Kecamatan"); // NOI18N
        Kecamatan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KecamatanMouseMoved(evt);
            }
        });
        Kecamatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KecamatanMouseExited(evt);
            }
        });
        Kecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecamatanKeyPressed(evt);
            }
        });
        FormInput.add(Kecamatan);
        Kecamatan.setBounds(463, 100, 180, 23);

        BtnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatan.setMnemonic('3');
        BtnKecamatan.setToolTipText("ALt+3");
        BtnKecamatan.setName("BtnKecamatan"); // NOI18N
        BtnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanActionPerformed(evt);
            }
        });
        FormInput.add(BtnKecamatan);
        BtnKecamatan.setBounds(677, 100, 28, 23);

        BtnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPropinsi.setMnemonic('4');
        BtnPropinsi.setToolTipText("ALt+4");
        BtnPropinsi.setName("BtnPropinsi"); // NOI18N
        BtnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPropinsiActionPerformed(evt);
            }
        });
        FormInput.add(BtnPropinsi);
        BtnPropinsi.setBounds(677, 130, 28, 23);

        Propinsi.setEditable(false);
        Propinsi.setText("PROPINSI");
        Propinsi.setHighlighter(null);
        Propinsi.setName("Propinsi"); // NOI18N
        Propinsi.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                PropinsiMouseMoved(evt);
            }
        });
        Propinsi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PropinsiMouseExited(evt);
            }
        });
        Propinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PropinsiKeyPressed(evt);
            }
        });
        FormInput.add(Propinsi);
        Propinsi.setBounds(463, 130, 180, 23);

        BtnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupaten.setMnemonic('4');
        BtnKabupaten.setToolTipText("ALt+4");
        BtnKabupaten.setName("BtnKabupaten"); // NOI18N
        BtnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenActionPerformed(evt);
            }
        });
        FormInput.add(BtnKabupaten);
        BtnKabupaten.setBounds(360, 130, 28, 23);

        Kabupaten.setEditable(false);
        Kabupaten.setText("KABUPATEN");
        Kabupaten.setHighlighter(null);
        Kabupaten.setName("Kabupaten"); // NOI18N
        Kabupaten.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KabupatenMouseMoved(evt);
            }
        });
        Kabupaten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KabupatenMouseExited(evt);
            }
        });
        Kabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenKeyPressed(evt);
            }
        });
        FormInput.add(Kabupaten);
        Kabupaten.setBounds(146, 130, 180, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
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
        FormInput.add(BtnCari1);
        BtnCari1.setBounds(646, 130, 28, 23);

        KdProp.setHighlighter(null);
        KdProp.setName("KdProp"); // NOI18N
        KdProp.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KdPropMouseMoved(evt);
            }
        });
        KdProp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KdPropMouseExited(evt);
            }
        });
        KdProp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPropKeyPressed(evt);
            }
        });
        FormInput.add(KdProp);
        KdProp.setBounds(401, 130, 60, 23);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('2');
        BtnCari2.setToolTipText("Alt+2");
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
        BtnCari2.setBounds(329, 100, 28, 23);

        KdKel.setHighlighter(null);
        KdKel.setName("KdKel"); // NOI18N
        KdKel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KdKelMouseMoved(evt);
            }
        });
        KdKel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KdKelMouseExited(evt);
            }
        });
        KdKel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKelKeyPressed(evt);
            }
        });
        FormInput.add(KdKel);
        KdKel.setBounds(84, 100, 60, 23);

        KdKec.setHighlighter(null);
        KdKec.setName("KdKec"); // NOI18N
        KdKec.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KdKecMouseMoved(evt);
            }
        });
        KdKec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KdKecMouseExited(evt);
            }
        });
        KdKec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKecKeyPressed(evt);
            }
        });
        FormInput.add(KdKec);
        KdKec.setBounds(401, 100, 60, 23);

        KdKab.setHighlighter(null);
        KdKab.setName("KdKab"); // NOI18N
        KdKab.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KdKabMouseMoved(evt);
            }
        });
        KdKab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KdKabMouseExited(evt);
            }
        });
        KdKab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKabKeyPressed(evt);
            }
        });
        FormInput.add(KdKab);
        KdKab.setBounds(84, 130, 60, 23);

        BtnCari3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari3.setMnemonic('2');
        BtnCari3.setToolTipText("Alt+2");
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
        BtnCari3.setBounds(646, 100, 28, 23);

        BtnCari4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari4.setMnemonic('2');
        BtnCari4.setToolTipText("Alt+2");
        BtnCari4.setName("BtnCari4"); // NOI18N
        BtnCari4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari4ActionPerformed(evt);
            }
        });
        BtnCari4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari4KeyPressed(evt);
            }
        });
        FormInput.add(BtnCari4);
        BtnCari4.setBounds(329, 130, 28, 23);

        CmbJk1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1.Inisiatif pasien/Keluarga", "2.Anggota Masyarakat/Kader", "3.Faskes", "4.Dokter Praktek Mandiri", "5.Poli lain", "6.Lain-lain" }));
        CmbJk1.setName("CmbJk1"); // NOI18N
        CmbJk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk1KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk1);
        CmbJk1.setBounds(515, 160, 190, 23);

        jLabel14.setText("Rujukan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(459, 160, 53, 23);

        jLabel15.setText("Ktrg.Rujukan :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 190, 100, 23);

        Alamat1.setHighlighter(null);
        Alamat1.setName("Alamat1"); // NOI18N
        Alamat1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                Alamat1MouseMoved(evt);
            }
        });
        Alamat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Alamat1MouseExited(evt);
            }
        });
        Alamat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alamat1KeyPressed(evt);
            }
        });
        FormInput.add(Alamat1);
        Alamat1.setBounds(103, 190, 140, 23);

        jLabel16.setText("Tipe Diagnosis :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 220, 100, 23);

        CmbJk2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Terkonfirmasi bakteriologis", "Terdiagnosis klinis" }));
        CmbJk2.setName("CmbJk2"); // NOI18N
        CmbJk2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk2KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk2);
        CmbJk2.setBounds(103, 220, 187, 23);

        jLabel17.setText("Lokasi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(557, 190, 40, 23);

        CmbJk3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Paru", "Ekstraparu" }));
        CmbJk3.setName("CmbJk3"); // NOI18N
        CmbJk3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk3KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk3);
        CmbJk3.setBounds(600, 190, 105, 23);

        jLabel18.setText("Riwayat :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(248, 190, 50, 23);

        CmbJk4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1.Baru", "2.Kambuh", "3.Diobati setelah gagal", "4.Diobati Setelah Putus Berobat", "5.Lain-lain", "6.Riwayat Pengobatan Sebelumnya Tidak Diketahui", "7.Pindahan" }));
        CmbJk4.setName("CmbJk4"); // NOI18N
        CmbJk4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk4KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk4);
        CmbJk4.setBounds(301, 190, 250, 23);

        jLabel19.setText("Status HIV :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(293, 220, 68, 23);

        CmbJk5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Positif", "Negatif", "Tidak diketahui" }));
        CmbJk5.setName("CmbJk5"); // NOI18N
        CmbJk5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk5KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk5);
        CmbJk5.setBounds(363, 220, 127, 23);

        jLabel20.setText("Skoring Anak :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(488, 220, 84, 23);

        CmbJk6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "Tidak dilakukan" }));
        CmbJk6.setName("CmbJk6"); // NOI18N
        CmbJk6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk6KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk6);
        CmbJk6.setBounds(575, 220, 130, 23);

        jLabel21.setText("Skoring 5 :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(0, 250, 100, 23);

        CmbJk7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Uji Tuberkulin Positif", "Ada Kontak TB Paru", "Uji Tuberkulin Negatif", "Tidak Ada Kontak TB Paru" }));
        CmbJk7.setName("CmbJk7"); // NOI18N
        CmbJk7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk7KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk7);
        CmbJk7.setBounds(103, 250, 175, 23);

        jLabel22.setText("Skoring 6 :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(282, 250, 60, 23);

        CmbJk8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada Kontak TB Paru", "Tidak Ada", "Tidak Jelas Kontak TB Paru" }));
        CmbJk8.setName("CmbJk8"); // NOI18N
        CmbJk8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk8KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk8);
        CmbJk8.setBounds(345, 250, 180, 23);

        jLabel23.setText("Mulai Berobat :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(532, 250, 80, 23);

        DTPLahir1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-03-2019" }));
        DTPLahir1.setDisplayFormat("dd-MM-yyyy");
        DTPLahir1.setName("DTPLahir1"); // NOI18N
        DTPLahir1.setOpaque(false);
        DTPLahir1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPLahir1ItemStateChanged(evt);
            }
        });
        DTPLahir1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPLahir1KeyPressed(evt);
            }
        });
        FormInput.add(DTPLahir1);
        DTPLahir1.setBounds(615, 250, 90, 23);

        jLabel24.setText("Paduan OAT :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(0, 280, 100, 23);

        Alamat2.setHighlighter(null);
        Alamat2.setName("Alamat2"); // NOI18N
        Alamat2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                Alamat2MouseMoved(evt);
            }
        });
        Alamat2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Alamat2MouseExited(evt);
            }
        });
        Alamat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alamat2KeyPressed(evt);
            }
        });
        FormInput.add(Alamat2);
        Alamat2.setBounds(103, 280, 175, 23);

        jLabel25.setText("Sumber Obat :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(282, 280, 80, 23);

        CmbJk9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Program TB", "Bayar Sendiri", "Asuransi", "Lain-lain" }));
        CmbJk9.setName("CmbJk9"); // NOI18N
        CmbJk9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk9KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk9);
        CmbJk9.setBounds(365, 280, 115, 23);

        Alamat3.setHighlighter(null);
        Alamat3.setName("Alamat3"); // NOI18N
        Alamat3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                Alamat3MouseMoved(evt);
            }
        });
        Alamat3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Alamat3MouseExited(evt);
            }
        });
        Alamat3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alamat3KeyPressed(evt);
            }
        });
        FormInput.add(Alamat3);
        Alamat3.setBounds(585, 280, 120, 23);

        jLabel26.setText("Keterangan S.O. :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(482, 280, 100, 23);

        jLabel27.setText("Sebelum Pengobatan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 310, 120, 23);

        CmbJk10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Positif", "Negatif", "Tidak dilakukan" }));
        CmbJk10.setName("CmbJk10"); // NOI18N
        CmbJk10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk10KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk10);
        CmbJk10.setBounds(194, 310, 125, 23);

        jLabel28.setText("Tes Cepat :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(314, 310, 70, 23);

        CmbJk11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rif sensitif", "Rif resisten", "Negatif", "Rif Indeterminated", "Invalid", "Error", "No Result", "Tidak dilakukan" }));
        CmbJk11.setName("CmbJk11"); // NOI18N
        CmbJk11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk11KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk11);
        CmbJk11.setBounds(387, 310, 143, 23);

        jLabel29.setText("Mikroskopis :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(121, 310, 70, 23);

        jLabel30.setText("Biakan :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(527, 310, 50, 23);

        CmbJk12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif", "1-19 BTA", "1+", "2+", "3+", "4+", "NTM", "Kontaminasi", "Tidak dilakukan" }));
        CmbJk12.setName("CmbJk12"); // NOI18N
        CmbJk12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk12KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk12);
        CmbJk12.setBounds(580, 310, 125, 23);

        jLabel34.setText("Pemeriksaan Laborat Bulan ke 2 :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 340, 174, 23);

        jLabel35.setText("Mikroskopis :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 360, 100, 23);

        CmbJk14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Positif", "Negatif", "Tidak dilakukan" }));
        CmbJk14.setName("CmbJk14"); // NOI18N
        CmbJk14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk14KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk14);
        CmbJk14.setBounds(103, 360, 125, 23);

        jLabel36.setText("No.Reg :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(230, 360, 50, 23);

        Alamat5.setHighlighter(null);
        Alamat5.setName("Alamat5"); // NOI18N
        Alamat5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                Alamat5MouseMoved(evt);
            }
        });
        Alamat5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Alamat5MouseExited(evt);
            }
        });
        Alamat5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alamat5KeyPressed(evt);
            }
        });
        FormInput.add(Alamat5);
        Alamat5.setBounds(283, 360, 72, 23);

        Alamat6.setHighlighter(null);
        Alamat6.setName("Alamat6"); // NOI18N
        Alamat6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                Alamat6MouseMoved(evt);
            }
        });
        Alamat6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Alamat6MouseExited(evt);
            }
        });
        Alamat6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alamat6KeyPressed(evt);
            }
        });
        FormInput.add(Alamat6);
        Alamat6.setBounds(633, 360, 72, 23);

        jLabel37.setText("No.Reg :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(580, 360, 50, 23);

        CmbJk15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Positif", "Negatif", "Tidak dilakukan" }));
        CmbJk15.setName("CmbJk15"); // NOI18N
        CmbJk15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJk15KeyPressed(evt);
            }
        });
        FormInput.add(CmbJk15);
        CmbJk15.setBounds(453, 360, 125, 23);

        jLabel38.setText("Mikroskopis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(350, 360, 100, 23);

        jLabel39.setText("Pemeriksaan Laborat Bulan ke 3 :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(350, 340, 174, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Data Pasien Teridentifikasi TB", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setComponentPopupMenu(jPopupMenu1);
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Daftar Pasien Teridentifikasi TB", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Kelas,BtnBatal);
        }
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
        for(i=0;i<tbJnsPerawatan.getRowCount();i++){ 
            if(tbJnsPerawatan.getValueAt(i,0).toString().equals("true")){
                Sequel.mengedit("paket_operasi","kode_paket='"+tbJnsPerawatan.getValueAt(i,1).toString()+"'","status='0'");
            }
        } 
        tampil();
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
        }else{Valid.pindah(evt,BtnAll,TCari);}
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
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptPaketOperasi.jrxml","report","::[ Data Paket Operasi ]::",
                   "select paket_operasi.kode_paket, paket_operasi.nm_perawatan,(paket_operasi.operator1+paket_operasi.operator2+paket_operasi.operator3+"+
                       "paket_operasi.asisten_operator1+paket_operasi.asisten_operator2+paket_operasi.asisten_operator3+paket_operasi.instrumen+"+
                       "paket_operasi.dokter_anak+paket_operasi.perawaat_resusitas+"+
                       "paket_operasi.alat+paket_operasi.dokter_anestesi+paket_operasi.asisten_anestesi+paket_operasi.asisten_anestesi2+"+
                       "paket_operasi.bidan+paket_operasi.bidan2+paket_operasi.bidan3+paket_operasi.perawat_luar+"+
                       "paket_operasi.sewa_ok+paket_operasi.akomodasi+paket_operasi.bagian_rs+"+
                       "paket_operasi.omloop+paket_operasi.omloop2+paket_operasi.omloop3+paket_operasi.omloop4+paket_operasi.omloop5+"+
                       "paket_operasi.sarpras+paket_operasi.dokter_pjanak+paket_operasi.dokter_umum) as jumlah "+
                   "from paket_operasi inner join penjab on penjab.kd_pj=paket_operasi.kd_pj "+
                   "where paket_operasi.status='1' and paket_operasi.kode_paket like '%"+TCari.getText()+"%' or "+
                   "paket_operasi.status='1' and paket_operasi.nm_perawatan like '%"+TCari.getText()+"%' or "+
                   "paket_operasi.status='1' and paket_operasi.kelas like '%"+TCari.getText()+"%' or "+
                   "paket_operasi.status='1' and penjab.png_jawab like '%"+TCari.getText()+"%' order by paket_operasi.kode_paket ",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnAll);
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
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
                if(evt.getClickCount()==2){
                    TabRawat.setSelectedIndex(0);
                   
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_A){                
                for(i=0;i<tbJnsPerawatan.getRowCount();i++){ 
                    tbJnsPerawatan.setValueAt(true,i,0);
                }
            } 
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void MnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRestoreActionPerformed
        DlgRestoreTarifOperasi restore=new DlgRestoreTarifOperasi(null,true);
        restore.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        restore.setLocationRelativeTo(internalFrame1);
        restore.setVisible(true);
    }//GEN-LAST:event_MnRestoreActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(FormInput.getHeight()<390){   
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormInput.setPreferredSize(new Dimension(FormInput.WIDTH,390));
            if(FormInput.getWidth()<740){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(740,390));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }else{
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);            
            if(FormInput.getWidth()<740){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(740,FormInput.WIDTH));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select concat(pasien.no_rkm_medis,', ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                " on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TNoRM,TNoRw.getText());
        }else{
            //Valid.pindah(evt,TCari,kdoperator1);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalKeyPressed

    private void UmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UmurKeyPressed

    private void NoKartuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKartuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoKartuKeyPressed

    private void NIKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIKKeyPressed

    private void CmbJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJkKeyPressed
        //Valid.pindah(evt,TNm,CMbGd);
    }//GEN-LAST:event_CmbJkKeyPressed

    private void DTPLahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPLahirItemStateChanged
        
    }//GEN-LAST:event_DTPLahirItemStateChanged

    private void DTPLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPLahirKeyPressed
        
    }//GEN-LAST:event_DTPLahirKeyPressed

    private void AlamatMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseMoved
        if(Alamat.getText().equals("ALAMAT")){
            Alamat.setText("");
        }
    }//GEN-LAST:event_AlamatMouseMoved

    private void AlamatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseExited
        if(Alamat.getText().equals("")){
            Alamat.setText("ALAMAT");
        }
    }//GEN-LAST:event_AlamatMouseExited

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Alamat.getText().equals("")){
                Alamat.setText("ALAMAT");
            }
            if(Kelurahan.getText().equals("KELURAHAN")){
                Kelurahan.setText("");
            }
            Kelurahan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Alamat.getText().equals("")){
                Alamat.setText("ALAMAT");
            }
            //TKtp.requestFocus();
        }
    }//GEN-LAST:event_AlamatKeyPressed

    private void KelurahanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseMoved
        if(Kelurahan.getText().equals("KELURAHAN")){
            Kelurahan.setText("");
        }
    }//GEN-LAST:event_KelurahanMouseMoved

    private void KelurahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseExited
        if(Kelurahan.getText().equals("")){
            Kelurahan.setText("KELURAHAN");
        }
    }//GEN-LAST:event_KelurahanMouseExited

    private void KelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelurahanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Kelurahan.getText().equals("")){
                Kelurahan.setText("KELURAHAN");
            }
            if(Kecamatan.getText().equals("KECAMATAN")){
                Kecamatan.setText("");
            }
            Kecamatan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Kelurahan.getText().equals("")){
                Kelurahan.setText("KELURAHAN");
            }
            if(Alamat.getText().equals("ALAMAT")){
                Alamat.setText("");
            }
            Alamat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKelurahanActionPerformed(null);
        }
    }//GEN-LAST:event_KelurahanKeyPressed

    private void BtnKelurahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanActionPerformed
        if(KdKec.getText().trim().equals("")||Kecamatan.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih kecamatan dulu..!!");
            BtnKelurahan.requestFocus();
        }else{
            kelurahan.setPropinsi(KdKec.getText(),Kecamatan.getText());
            kelurahan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kelurahan.setLocationRelativeTo(internalFrame1);
            kelurahan.setVisible(true);
        }
    }//GEN-LAST:event_BtnKelurahanActionPerformed

    private void KecamatanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseMoved
        if(Kecamatan.getText().equals("KECAMATAN")){
            Kecamatan.setText("");
        }
    }//GEN-LAST:event_KecamatanMouseMoved

    private void KecamatanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseExited
        if(Kecamatan.getText().equals("")){
            Kecamatan.setText("KECAMATAN");
        }
    }//GEN-LAST:event_KecamatanMouseExited

    private void KecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecamatanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Kecamatan.getText().equals("")){
                Kecamatan.setText("KECAMATAN");
            }
            if(Kabupaten.getText().equals("KABUPATEN")){
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Kecamatan.getText().equals("")){
                Kecamatan.setText("KECAMATAN");
            }
            if(Kelurahan.getText().equals("KELURAHAN")){
                Kelurahan.setText("");
            }
            Kelurahan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKecamatanActionPerformed(null);
        }
    }//GEN-LAST:event_KecamatanKeyPressed

    private void BtnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanActionPerformed
        if(KdKab.getText().trim().equals("")||Kabupaten.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih kabupaten dulu..!!");
            BtnKabupaten.requestFocus();
        }else{
            kecamatan.setPropinsi(KdKab.getText(),Kabupaten.getText());
            kecamatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kecamatan.setLocationRelativeTo(internalFrame1);
            kecamatan.setVisible(true);
        }
    }//GEN-LAST:event_BtnKecamatanActionPerformed

    private void BtnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPropinsiActionPerformed
        propinsi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        propinsi.setLocationRelativeTo(internalFrame1);
        propinsi.setVisible(true);
    }//GEN-LAST:event_BtnPropinsiActionPerformed

    private void PropinsiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseMoved
        if(Propinsi.getText().equals("PROPINSI")){
            Propinsi.setText("");
        }
    }//GEN-LAST:event_PropinsiMouseMoved

    private void PropinsiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseExited
        if(Propinsi.getText().equals("")){
            Propinsi.setText("PROPINSI");
        }
    }//GEN-LAST:event_PropinsiMouseExited

    private void PropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PropinsiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Propinsi.getText().equals("")){
                Propinsi.setText("PROPINSI");
            }
            /*if(AlamatPj.getText().equals("ALAMAT")){
                AlamatPj.setText("");
            }
            AlamatPj.requestFocus();*/
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Propinsi.getText().equals("")){
                Propinsi.setText("PROPINSI");
            }
            if(Kabupaten.getText().equals("KABUPATEN")){
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPropinsiActionPerformed(null);
        }
    }//GEN-LAST:event_PropinsiKeyPressed

    private void BtnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenActionPerformed
        if(KdProp.getText().trim().equals("")||Propinsi.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih propinsi dulu..!!");
            BtnPropinsi.requestFocus();
        }else{
            kabupaten.setPropinsi(KdProp.getText(),Propinsi.getText());
            kabupaten.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kabupaten.setLocationRelativeTo(internalFrame1);
            kabupaten.setVisible(true);
        }   
    }//GEN-LAST:event_BtnKabupatenActionPerformed

    private void KabupatenMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseMoved
        if(Kabupaten.getText().equals("KABUPATEN")){
            Kabupaten.setText("");
        }
    }//GEN-LAST:event_KabupatenMouseMoved

    private void KabupatenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseExited
        if(Kabupaten.getText().equals("")){
            Kabupaten.setText("KABUPATEN");
        }
    }//GEN-LAST:event_KabupatenMouseExited

    private void KabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Kabupaten.getText().equals("")){
                Kabupaten.setText("KABUPATEN");
            }
            if(Propinsi.getText().equals("PROPINSI")){
                Propinsi.setText("");
            }
            Propinsi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Kabupaten.getText().equals("")){
                Kabupaten.setText("KABUPATEN");
            }
            if(Kecamatan.getText().equals("KECAMATAN")){
                Kecamatan.setText("");
            }
            Kecamatan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKabupatenActionPerformed(null);
        }
    }//GEN-LAST:event_KabupatenKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        if(Propinsi.getText().equals("")||Propinsi.getText().equals("-")||Propinsi.getText().equals("PROPINSI")){
            JOptionPane.showMessageDialog(null,"Nama Propinsi masih kosong, silahkan pilih propinsi yang ada...!!!");
            BtnPropinsi.requestFocus();
        }else{
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                requestEntity = new HttpEntity(headers);
                rest=new RestTemplate();
                root = mapper.readTree(rest.exchange("http://yaski.or.id:8888/provinsi/?search="+Propinsi.getText(), HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode = root.path("status");
                if(nameNode.asText().equals("ok")){
                    response = root.path("data");
                    if(response.isArray()){
                        i=0;
                        for(JsonNode list:response){
                            if(list.path("nama").asText().toLowerCase().contains(Propinsi.getText().toLowerCase())){
                                KdProp.setText(list.path("id_prov").asText());
                                i++;
                            }
                        }
                        if(i==0){
                            KdProp.setText("");
                            JOptionPane.showMessageDialog(null,"Data Propinsi tidak ditemukan..!!");
                        }
                    }
                }else {
                    JOptionPane.showMessageDialog(null,nameNode.path("status").asText());                
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server YASKI terputus...!");
                }
            }
        }
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void KdPropMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KdPropMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPropMouseMoved

    private void KdPropMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KdPropMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPropMouseExited

    private void KdPropKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPropKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPropKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        if(KdKec.getText().equals("")||KdKec.getText().equals("0")){
            JOptionPane.showMessageDialog(null,"Kode Kecamatan masih kosong, silahkan pilih kecamatan yang ada...!!!");
            BtnKecamatan.requestFocus();
        }else{
            if(Kelurahan.getText().equals("")||Kelurahan.getText().equals("-")||Kelurahan.getText().equals("KELURAHAN")){
                JOptionPane.showMessageDialog(null,"Nama Kelurahan masih kosong, silahkan pilih kelurahan yang ada...!!!");
                BtnKelurahan.requestFocus();
            }else{
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    requestEntity = new HttpEntity(headers);
                    rest=new RestTemplate();
                    root = mapper.readTree(rest.exchange("http://yaski.or.id:8888/kecamatan/"+KdKec.getText()+"/kelurahan", HttpMethod.GET, requestEntity, String.class).getBody());
                    nameNode = root.path("status");
                    if(nameNode.asText().equals("ok")){
                        response = root.path("data");
                        if(response.isArray()){
                            i=0;
                            for(JsonNode list:response){
                                System.out.println(list.path("nama").asText().toLowerCase());
                                if(list.path("nama").asText().toLowerCase().contains(Kelurahan.getText().toLowerCase())){
                                    KdKel.setText(list.path("id_kel").asText());
                                    i++;
                                }
                            }
                            if(i==0){
                                KdKel.setText("");
                                JOptionPane.showMessageDialog(null,"Data Kelurahan tidak ditemukan..!!");
                            }
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,nameNode.path("status").asText());                
                    }   
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(rootPane,"Koneksi ke server YASKI terputus...!");
                    }
                }
            }
        }
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void KdKelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KdKelMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKelMouseMoved

    private void KdKelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KdKelMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKelMouseExited

    private void KdKelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKelKeyPressed

    private void KdKecMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KdKecMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKecMouseMoved

    private void KdKecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KdKecMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKecMouseExited

    private void KdKecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKecKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKecKeyPressed

    private void KdKabMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KdKabMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKabMouseMoved

    private void KdKabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KdKabMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKabMouseExited

    private void KdKabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdKabKeyPressed

    private void BtnCari3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari3ActionPerformed
        if(KdKab.getText().equals("")||KdKab.getText().equals("0")){
            JOptionPane.showMessageDialog(null,"Kode Kabupaten masih kosong, silahkan pilih kabupaten yang ada...!!!");
            BtnKabupaten.requestFocus();
        }else{
            if(Kecamatan.getText().equals("")||Kecamatan.getText().equals("-")||Kecamatan.getText().equals("KECAMATAN")){
                JOptionPane.showMessageDialog(null,"Nama Kecamatan masih kosong, silahkan pilih kecamatan yang ada...!!!");
                BtnKecamatan.requestFocus();
            }else{
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    requestEntity = new HttpEntity(headers);
                    rest=new RestTemplate();
                    root = mapper.readTree(rest.exchange("http://yaski.or.id:8888/kabupaten/"+KdKab.getText()+"/kecamatan", HttpMethod.GET, requestEntity, String.class).getBody());
                    nameNode = root.path("status");
                    if(nameNode.asText().equals("ok")){
                        response = root.path("data");
                        if(response.isArray()){
                            i=0;
                            for(JsonNode list:response){
                                if(list.path("nama").asText().toLowerCase().contains(Kecamatan.getText().toLowerCase())){
                                    KdKec.setText(list.path("id_kec").asText());
                                    i++;
                                }
                            }
                            if(i==0){
                                KdKec.setText("");
                                JOptionPane.showMessageDialog(null,"Data Kecamatan tidak ditemukan..!!");
                            }
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,nameNode.path("status").asText());                
                    }   
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(rootPane,"Koneksi ke server YASKI terputus...!");
                    }
                }
            }
        }
    }//GEN-LAST:event_BtnCari3ActionPerformed

    private void BtnCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari3KeyPressed

    private void BtnCari4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari4ActionPerformed
        if(KdProp.getText().equals("")||KdProp.getText().equals("0")){
            JOptionPane.showMessageDialog(null,"Kode Propinsi masih kosong, silahkan pilih propinsi yang ada...!!!");
            BtnPropinsi.requestFocus();
        }else{
            if(Kabupaten.getText().equals("")||Kabupaten.getText().equals("-")||Kabupaten.getText().equals("KABUPATEN")){
                JOptionPane.showMessageDialog(null,"Nama Kabupaten masih kosong, silahkan pilih kabupaten yang ada...!!!");
                BtnPropinsi.requestFocus();
            }else{
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    requestEntity = new HttpEntity(headers);
                    rest=new RestTemplate();
                    root = mapper.readTree(rest.exchange("http://yaski.or.id:8888/provinsi/"+KdProp.getText()+"/kabupaten", HttpMethod.GET, requestEntity, String.class).getBody());
                    nameNode = root.path("status");
                    if(nameNode.asText().equals("ok")){
                        response = root.path("data");
                        if(response.isArray()){
                            i=0;
                            for(JsonNode list:response){
                                if(list.path("nama").asText().toLowerCase().contains(Kabupaten.getText().toLowerCase())){
                                    KdKab.setText(list.path("id_kab").asText());
                                    i++;
                                }
                            }
                            if(i==0){
                                KdKab.setText("");
                                JOptionPane.showMessageDialog(null,"Data Kabupaten tidak ditemukan..!!");
                            }
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,nameNode.path("status").asText());                
                    }   
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(rootPane,"Koneksi ke server YASKI terputus...!");
                    }
                }
            }
        }
    }//GEN-LAST:event_BtnCari4ActionPerformed

    private void BtnCari4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari4KeyPressed

    private void CmbJk1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk1KeyPressed

    private void Alamat1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat1MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat1MouseMoved

    private void Alamat1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat1MouseExited

    private void Alamat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alamat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat1KeyPressed

    private void CmbJk2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk2KeyPressed

    private void CmbJk3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk3KeyPressed

    private void CmbJk4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk4KeyPressed

    private void CmbJk5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk5KeyPressed

    private void CmbJk6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk6KeyPressed

    private void CmbJk7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk7KeyPressed

    private void CmbJk8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk8KeyPressed

    private void DTPLahir1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPLahir1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPLahir1ItemStateChanged

    private void DTPLahir1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPLahir1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPLahir1KeyPressed

    private void Alamat2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat2MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat2MouseMoved

    private void Alamat2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat2MouseExited

    private void Alamat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alamat2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat2KeyPressed

    private void CmbJk9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk9KeyPressed

    private void Alamat3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat3MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat3MouseMoved

    private void Alamat3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat3MouseExited

    private void Alamat3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alamat3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat3KeyPressed

    private void CmbJk10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk10KeyPressed

    private void CmbJk11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk11KeyPressed

    private void CmbJk12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk12KeyPressed

    private void CmbJk14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk14KeyPressed

    private void Alamat5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat5MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat5MouseMoved

    private void Alamat5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat5MouseExited

    private void Alamat5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alamat5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat5KeyPressed

    private void Alamat6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat6MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat6MouseMoved

    private void Alamat6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Alamat6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat6MouseExited

    private void Alamat6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alamat6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alamat6KeyPressed

    private void CmbJk15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJk15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbJk15KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDataTB dialog = new DlgDataTB(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.TextBox Alamat1;
    private widget.TextBox Alamat2;
    private widget.TextBox Alamat3;
    private widget.TextBox Alamat5;
    private widget.TextBox Alamat6;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnCari2;
    private widget.Button BtnCari3;
    private widget.Button BtnCari4;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKabupaten;
    private widget.Button BtnKecamatan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKelurahan;
    private widget.Button BtnPrint;
    private widget.Button BtnPropinsi;
    private widget.Button BtnSimpan;
    private widget.ComboBox CmbJk;
    private widget.ComboBox CmbJk1;
    private widget.ComboBox CmbJk10;
    private widget.ComboBox CmbJk11;
    private widget.ComboBox CmbJk12;
    private widget.ComboBox CmbJk14;
    private widget.ComboBox CmbJk15;
    private widget.ComboBox CmbJk2;
    private widget.ComboBox CmbJk3;
    private widget.ComboBox CmbJk4;
    private widget.ComboBox CmbJk5;
    private widget.ComboBox CmbJk6;
    private widget.ComboBox CmbJk7;
    private widget.ComboBox CmbJk8;
    private widget.ComboBox CmbJk9;
    private widget.Tanggal DTPLahir;
    private widget.Tanggal DTPLahir1;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.TextBox Kabupaten;
    private widget.TextBox KdKab;
    private widget.TextBox KdKec;
    private widget.TextBox KdKel;
    private widget.TextBox KdProp;
    private widget.TextBox Kecamatan;
    private widget.TextBox Kelurahan;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnRestore;
    private widget.TextBox NIK;
    private widget.TextBox NoKartu;
    private widget.TextBox Propinsi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TNmPasien;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox Tanggal;
    private widget.TextBox Umur;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
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
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            ps=koneksi.prepareStatement(
                       "select paket_operasi.kode_paket, paket_operasi.nm_perawatan,paket_operasi.kategori,"+
                       "paket_operasi.operator1, paket_operasi.operator2, paket_operasi.operator3, "+
                       "paket_operasi.asisten_operator1, paket_operasi.asisten_operator2,paket_operasi.asisten_operator3,"+
                       "paket_operasi.instrumen, paket_operasi.dokter_anestesi,paket_operasi.asisten_anestesi,paket_operasi.asisten_anestesi2,"+
                       "paket_operasi.dokter_anak,paket_operasi.perawaat_resusitas, paket_operasi.bidan, "+
                       "paket_operasi.bidan2, paket_operasi.bidan3, paket_operasi.perawat_luar, paket_operasi.alat,"+
                       "paket_operasi.sewa_ok,paket_operasi.akomodasi,paket_operasi.bagian_rs,"+
                       "paket_operasi.omloop,paket_operasi.omloop2,paket_operasi.omloop3,paket_operasi.omloop4,paket_operasi.omloop5,"+
                       "paket_operasi.sarpras,paket_operasi.dokter_pjanak,paket_operasi.dokter_umum, "+
                       "(paket_operasi.operator1+paket_operasi.operator2+paket_operasi.operator3+"+
                       "paket_operasi.asisten_operator1+paket_operasi.asisten_operator2+paket_operasi.asisten_operator3+paket_operasi.instrumen+"+
                       "paket_operasi.dokter_anak+paket_operasi.perawaat_resusitas+"+
                       "paket_operasi.alat+paket_operasi.dokter_anestesi+paket_operasi.asisten_anestesi+paket_operasi.asisten_anestesi2+"+
                       "paket_operasi.bidan+paket_operasi.bidan2+paket_operasi.bidan3+paket_operasi.perawat_luar+"+
                       "paket_operasi.sewa_ok+paket_operasi.akomodasi+paket_operasi.bagian_rs+"+
                       "paket_operasi.omloop+paket_operasi.omloop2+paket_operasi.omloop3+paket_operasi.omloop4+paket_operasi.omloop5+"+
                       "paket_operasi.sarpras+paket_operasi.dokter_pjanak+paket_operasi.dokter_umum) as jumlah, "+
                       "penjab.png_jawab,paket_operasi.kelas from paket_operasi inner join penjab on penjab.kd_pj=paket_operasi.kd_pj "+
                       "where paket_operasi.status='1' and paket_operasi.kode_paket like ? or "+
                       "paket_operasi.status='1' and paket_operasi.nm_perawatan like ? or "+
                       "paket_operasi.status='1' and paket_operasi.kelas like ? or "+
                       "paket_operasi.status='1' and penjab.png_jawab like ? order by paket_operasi.kode_paket ");
            try{
                ps.setString(1,"%"+TCari.getText()+"%");
                ps.setString(2,"%"+TCari.getText()+"%");
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){                    
                    tabMode.addRow(new Object[]{false,rs.getString("kode_paket"),
                                   rs.getString("nm_perawatan"),
                                   rs.getString("kategori"), 
                                   rs.getDouble("operator1"), 
                                   rs.getDouble("operator2"), 
                                   rs.getDouble("operator3"), 
                                   rs.getDouble("asisten_operator1"), 
                                   rs.getDouble("asisten_operator2"), 
                                   rs.getDouble("asisten_operator3"), 
                                   rs.getDouble("instrumen"), 
                                   rs.getDouble("dokter_anestesi"), 
                                   rs.getDouble("asisten_anestesi"),
                                   rs.getDouble("asisten_anestesi2"), 
                                   rs.getDouble("dokter_anak"), 
                                   rs.getDouble("perawaat_resusitas"), 
                                   rs.getDouble("bidan"), 
                                   rs.getDouble("bidan2"), 
                                   rs.getDouble("bidan3"), 
                                   rs.getDouble("perawat_luar"), 
                                   rs.getDouble("alat"), 
                                   rs.getDouble("sewa_ok"), 
                                   rs.getDouble("akomodasi"), 
                                   rs.getDouble("bagian_rs"), 
                                   rs.getDouble("omloop"), 
                                   rs.getDouble("omloop2"), 
                                   rs.getDouble("omloop3"), 
                                   rs.getDouble("omloop4"), 
                                   rs.getDouble("omloop5"), 
                                   rs.getDouble("sarpras"), 
                                   rs.getDouble("dokter_pjanak"), 
                                   rs.getDouble("dokter_umum"), 
                                   rs.getDouble("jumlah"),
                                   rs.getString("png_jawab"),
                                   rs.getString("kelas")
                    });
                }  
            } catch(Exception e){
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
    }

    public void emptTeks() {
        
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            //TKd.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            
        }
    }
    
    public void isCek(){
        TabRawat.setSelectedIndex(1);
        BtnSimpan.setEnabled(var.getkemenkes_sitt());
        BtnHapus.setEnabled(var.getkemenkes_sitt());
        BtnEdit.setEnabled(var.getkemenkes_sitt());
        BtnPrint.setEnabled(var.getkemenkes_sitt());
    }
    
    public void setNoRM(String norawat){
        TabRawat.setSelectedIndex(0);
        TNoRw.setText(norawat);
        try {
            ps=koneksi.prepareStatement(
                "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.umurdaftar, "+
                "reg_periksa.sttsumur,pasien.no_peserta,pasien.no_ktp,pasien.alamat,pasien.kd_kel,kelurahan.nm_kel,"+
                "pasien.kd_kec,kecamatan.nm_kec,pasien.kd_kab,kabupaten.nm_kab,pasien.kd_prop,propinsi.nm_prop "+
                "from pasien inner join reg_periksa inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join propinsi on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and pasien.kd_prop=propinsi.kd_prop "+
                "where reg_periksa.no_rawat=?"
            );
            try {
                ps.setString(1, norawat);
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TNmPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk").replaceAll("P","Perempuan").replaceAll("L","Laki-Laki"));
                    Tanggal.setText(rs.getString("tgl_lahir"));
                    if(rs.getString("sttsumur").equals("Th")){
                        Umur.setText(rs.getString("umurdaftar"));
                    }else{
                        Umur.setText("0");
                    }
                    NoKartu.setText(rs.getString("no_peserta"));
                    NIK.setText(rs.getString("no_ktp"));
                    Alamat.setText(rs.getString("alamat"));    
                    KdKel.setText(rs.getString("kd_kel"));  
                    Kelurahan.setText(rs.getString("nm_kel"));  
                    KdKec.setText(rs.getString("kd_kec"));    
                    Kecamatan.setText(rs.getString("nm_kec"));  
                    KdKab.setText(rs.getString("kd_kab"));    
                    Kabupaten.setText(rs.getString("nm_kab"));  
                    KdProp.setText(rs.getString("kd_prop"));    
                    Propinsi.setText(rs.getString("nm_prop"));    
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

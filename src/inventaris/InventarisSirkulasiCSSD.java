

package inventaris;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
public class InventarisSirkulasiCSSD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String aksi="";

    /** Creates new form DlgKamarInap
     * @param parent
     * @param modal */
    public InventarisSirkulasiCSSD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Sirkulasi","No.Inventaris","Kode Barang","Nama Barang","Produsen","Type/Merk","Thn.Produksi",
                "ISBN","Jenis Barang","Keterangan Diambil","NIP Pengambil","Petugas Pengambil","Tgl & Jam Ambil",
                "Keterangan Sterilisasi","NIP Penyeteril","Petugas Penyeteril","Tgl & Jam Starilisasi",
                "Keterangan Kembali","NIP Pengembali","Petugas Pengembali","Tgl & Jam Kembali"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamIn.setModel(tabMode);

        //tbKamIn.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamIn.getBackground()));
        tbKamIn.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamIn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 21; i++) {
            TableColumn column = tbKamIn.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(87);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(115);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(115);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(90);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(115);
            }
        }
        tbKamIn.setDefaultRenderer(Object.class, new WarnaTable());

        Keterangan.setDocument(new batasInput((byte)50).getKata(Keterangan));
        NoSirkulasi.setDocument(new batasInput((byte)13).getKata(NoSirkulasi));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
        NoInventaris.setDocument(new batasInput((byte)30).getKata(NoInventaris));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
     
        
        WindowInput.setSize(735,245);
        WindowInput.setLocationRelativeTo(null);  
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }   
                NIP.requestFocus();
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

    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        BtnCloseIn = new widget.Button();
        jLabel19 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        NoInventaris = new widget.TextBox();
        label1 = new widget.Label();
        Status = new widget.TextBox();
        Merk = new widget.TextBox();
        Keterangan = new widget.TextBox();
        LblTgl = new widget.Label();
        label7 = new widget.Label();
        nama_barang = new widget.TextBox();
        btnInv = new widget.Button();
        Jenis = new widget.TextBox();
        label11 = new widget.Label();
        NoSirkulasi = new widget.TextBox();
        label6 = new widget.Label();
        NIP = new widget.TextBox();
        label8 = new widget.Label();
        NamaPetugas = new widget.TextBox();
        btnPtg = new widget.Button();
        label12 = new widget.Label();
        Tanggal = new widget.Tanggal();
        LblTgl1 = new widget.Label();
        TOut = new widget.TextBox();
        TIn = new widget.TextBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        PanelCariUtama = new javax.swing.JPanel();
        panelGlass10 = new widget.panelisi();
        BtnIn = new widget.Button();
        BtnSteril = new widget.Button();
        BtnOut = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass11 = new widget.panelisi();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        TglPinjam1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        TglPinjam2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbKamIn = new widget.Table();

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Transaksi Sirkulasi Barang CSSD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

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
        BtnCloseIn.setBounds(620, 195, 100, 30);

        jLabel19.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame2.add(jLabel19);
        jLabel19.setBounds(-10, 175, 850, 14);

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
        BtnSimpan.setBounds(14, 195, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Batal");
        BtnBatal.setToolTipText("Alt+B");
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
        BtnBatal.setBounds(117, 195, 100, 30);

        NoInventaris.setName("NoInventaris"); // NOI18N
        NoInventaris.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoInventarisKeyPressed(evt);
            }
        });
        internalFrame2.add(NoInventaris);
        NoInventaris.setBounds(103, 25, 130, 23);

        label1.setText("No.Inventaris :");
        label1.setName("label1"); // NOI18N
        internalFrame2.add(label1);
        label1.setBounds(0, 25, 100, 23);

        Status.setEditable(false);
        Status.setName("Status"); // NOI18N
        internalFrame2.add(Status);
        Status.setBounds(103, 85, 150, 23);

        Merk.setEditable(false);
        Merk.setName("Merk"); // NOI18N
        internalFrame2.add(Merk);
        Merk.setBounds(425, 55, 260, 23);

        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        internalFrame2.add(Keterangan);
        Keterangan.setBounds(103, 115, 582, 23);

        LblTgl.setText("Tanggal :");
        LblTgl.setName("LblTgl"); // NOI18N
        internalFrame2.add(LblTgl);
        LblTgl.setBounds(255, 85, 70, 23);

        label7.setText("Keterangan :");
        label7.setName("label7"); // NOI18N
        internalFrame2.add(label7);
        label7.setBounds(0, 115, 100, 23);

        nama_barang.setEditable(false);
        nama_barang.setName("nama_barang"); // NOI18N
        internalFrame2.add(nama_barang);
        nama_barang.setBounds(235, 25, 450, 23);

        btnInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInv.setMnemonic('1');
        btnInv.setToolTipText("Alt+1");
        btnInv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInv.setName("btnInv"); // NOI18N
        btnInv.setPreferredSize(new java.awt.Dimension(100, 30));
        btnInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvActionPerformed(evt);
            }
        });
        internalFrame2.add(btnInv);
        btnInv.setBounds(690, 25, 25, 23);

        Jenis.setEditable(false);
        Jenis.setName("Jenis"); // NOI18N
        internalFrame2.add(Jenis);
        Jenis.setBounds(103, 55, 260, 23);

        label11.setText("Jenis :");
        label11.setName("label11"); // NOI18N
        internalFrame2.add(label11);
        label11.setBounds(0, 55, 100, 23);

        NoSirkulasi.setName("NoSirkulasi"); // NOI18N
        NoSirkulasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSirkulasiKeyPressed(evt);
            }
        });
        internalFrame2.add(NoSirkulasi);
        NoSirkulasi.setBounds(548, 85, 137, 23);

        label6.setText("Status :");
        label6.setName("label6"); // NOI18N
        internalFrame2.add(label6);
        label6.setBounds(0, 85, 100, 23);

        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        internalFrame2.add(NIP);
        NIP.setBounds(103, 145, 130, 23);

        label8.setText("Petugas :");
        label8.setName("label8"); // NOI18N
        internalFrame2.add(label8);
        label8.setBounds(0, 145, 100, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        internalFrame2.add(NamaPetugas);
        NamaPetugas.setBounds(235, 145, 450, 23);

        btnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPtg.setMnemonic('3');
        btnPtg.setToolTipText("Alt+3");
        btnPtg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPtg.setName("btnPtg"); // NOI18N
        btnPtg.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPtgActionPerformed(evt);
            }
        });
        internalFrame2.add(btnPtg);
        btnPtg.setBounds(690, 145, 25, 23);

        label12.setText("Merk :");
        label12.setName("label12"); // NOI18N
        internalFrame2.add(label12);
        label12.setBounds(362, 55, 60, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2025 13:00:36" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        internalFrame2.add(Tanggal);
        Tanggal.setBounds(328, 85, 130, 23);

        LblTgl1.setText("No.Sirkulasi :");
        LblTgl1.setName("LblTgl1"); // NOI18N
        internalFrame2.add(LblTgl1);
        LblTgl1.setBounds(465, 85, 80, 23);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        TOut.setEditable(false);
        TOut.setForeground(new java.awt.Color(255, 255, 255));
        TOut.setHighlighter(null);
        TOut.setName("TOut"); // NOI18N

        TIn.setEditable(false);
        TIn.setForeground(new java.awt.Color(255, 255, 255));
        TIn.setHighlighter(null);
        TIn.setName("TIn"); // NOI18N

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Sirkulasi Barang CSSD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 100));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        BtnIn.setMnemonic('M');
        BtnIn.setText("Ambil");
        BtnIn.setToolTipText("Alt+M");
        BtnIn.setName("BtnIn"); // NOI18N
        BtnIn.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInActionPerformed(evt);
            }
        });
        BtnIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnInKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnIn);

        BtnSteril.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edittrash.png"))); // NOI18N
        BtnSteril.setMnemonic('M');
        BtnSteril.setText("Steril");
        BtnSteril.setToolTipText("Alt+M");
        BtnSteril.setName("BtnSteril"); // NOI18N
        BtnSteril.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSteril.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSterilActionPerformed(evt);
            }
        });
        BtnSteril.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSterilKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnSteril);

        BtnOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/36.png"))); // NOI18N
        BtnOut.setMnemonic('K');
        BtnOut.setText("Kembali");
        BtnOut.setToolTipText("Alt+K");
        BtnOut.setName("BtnOut"); // NOI18N
        BtnOut.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOutActionPerformed(evt);
            }
        });
        BtnOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOutKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnOut);

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
        panelGlass10.add(BtnHapus);

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
        panelGlass10.add(BtnPrint);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount);

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
        panelGlass10.add(BtnKeluar);

        PanelCariUtama.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Belum Kembali");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass11.add(R1);

        buttonGroup1.add(R2);
        R2.setText("Tgl.Ambil :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass11.add(R2);

        TglPinjam1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2025" }));
        TglPinjam1.setDisplayFormat("dd-MM-yyyy");
        TglPinjam1.setName("TglPinjam1"); // NOI18N
        TglPinjam1.setOpaque(false);
        TglPinjam1.setPreferredSize(new java.awt.Dimension(90, 23));
        TglPinjam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPinjam1KeyPressed(evt);
            }
        });
        panelGlass11.add(TglPinjam1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass11.add(jLabel22);

        TglPinjam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-05-2025" }));
        TglPinjam2.setDisplayFormat("dd-MM-yyyy");
        TglPinjam2.setName("TglPinjam2"); // NOI18N
        TglPinjam2.setOpaque(false);
        TglPinjam2.setPreferredSize(new java.awt.Dimension(90, 23));
        TglPinjam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPinjam2KeyPressed(evt);
            }
        });
        panelGlass11.add(TglPinjam2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass11.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass11.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        panelGlass11.add(BtnCari);

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
        panelGlass11.add(BtnAll);

        PanelCariUtama.add(panelGlass11, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamIn.setAutoCreateRowSorter(true);
        tbKamIn.setName("tbKamIn"); // NOI18N
        tbKamIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamInMouseClicked(evt);
            }
        });
        tbKamIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamInKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbKamInKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbKamIn);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInActionPerformed
        aksi="ambil";
        NoSirkulasi.setEditable(true);
        btnInv.setEnabled(true);
        emptTeks();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(inventaris_ambil_cssd.no_sirkulasi,3),signed)),0) from inventaris_ambil_cssd where DATE_FORMAT(inventaris_ambil_cssd.tgl_ambil, '%Y-%m-%d')='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ","AC"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoSirkulasi);
        WindowInput.setAlwaysOnTop(false);
        WindowInput.setVisible(true);
}//GEN-LAST:event_BtnInActionPerformed

    private void BtnInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnInActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnSteril);
        }
}//GEN-LAST:event_BtnInKeyPressed

    private void BtnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOutActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Tabel kosong. Tidak ada data yang bisa Anda edit..!!!!");
            BtnIn.requestFocus();
        }else if(Merk.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data barang inventaris CSSD yang akan diseterilisasi dengan menklik data pada tabel...!!!");
            tbKamIn.requestFocus();
        }else if(! TOut.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, barang inventaris CSSD sudah dikembalikan pada tanggal "+TOut.getText()+"...!!!");
            tbKamIn.requestFocus();
        }else if((! Merk.getText().trim().equals(""))&&(TOut.getText().trim().equals(""))){
            if(tbKamIn.getSelectedRow()>-1){
                if(!tbKamIn.getValueAt(tbKamIn.getSelectedRow(),16).toString().equals("")){
                    aksi="kembali";
                    NoInventaris.setEditable(false);
                    Sequel.cariIsi("select inventaris.status_barang from inventaris where inventaris.no_inventaris='"+NoInventaris.getText()+"'",Status);
                    NoSirkulasi.setEditable(false);
                    btnInv.setEnabled(false);
                    WindowInput.setAlwaysOnTop(false);
                    WindowInput.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, barang inventaris CSSD belum dilakukan sterilisasi..!!!");
                }
            }
        }
}//GEN-LAST:event_BtnOutActionPerformed

    private void BtnOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOutKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnOutActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSteril,BtnHapus);
        }
}//GEN-LAST:event_BtnOutKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            NoInventaris.requestFocus();
        }else if(nama_barang.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(nama_barang.getText().trim().equals(""))){
            if(Sequel.meghapustf("inventaris_ambil_cssd","no_sirkulasi",NoSirkulasi.getText())==true){
                Sequel.queryu("update inventaris set status_barang='Ada' where no_inventaris='"+NoInventaris.getText()+"'");
                tabMode.removeRow(tbKamIn.getSelectedRow());
                LCount.setText(""+tabMode.getRowCount());
            }
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnOut,BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInput.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
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
                bg.close();

                File f;            
                BufferedWriter bw;
                StringBuilder htmlContent;
                
                String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Sirkulasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Inventaris</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Barang</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Barang</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Produsen</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Type/Merk</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Thn.Produksi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ISBN</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Barang</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Diambil</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Pengambil</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Pengambil</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Ambil</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Sterilisasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Penyeteril</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Penyeteril</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Starilisasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kembali</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Pengembali</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Pengembali</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Kembali</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,14).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,15).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,16).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,17).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,18).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,19).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,20).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );
                            
                            f = new File("DataSirkulasiCSSD.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA SIRKULASI CSSD<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Sirkulasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Inventaris</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Barang</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Barang</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Produsen</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Type/Merk</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Thn.Produksi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ISBN</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Barang</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Diambil</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Pengambil</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Pengambil</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Ambil</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Sterilisasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Penyeteril</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Penyeteril</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Starilisasi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kembali</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Pengembali</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Pengembali</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Kembali</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,14).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,15).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,16).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,17).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,18).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,19).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbKamIn.getValueAt(i,20).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='2000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );
                            
                            f = new File("DataSirkulasiCSSD.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA SIRKULASI CSSD<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.Sirkulasi\";\"No.Inventaris\";\"Kode Barang\";\"Nama Barang\";\"Produsen\";\"Type/Merk\";\"Thn.Produksi\";\"ISBN\";\"Jenis Barang\";\"Keterangan Diambil\";\"NIP Pengambil\";\"Petugas Pengambil\";\"Tgl & Jam Ambil\";\"Keterangan Sterilisasi\";\"NIP Penyeteril\";\"Petugas Penyeteril\";\"Tgl & Jam Starilisasi\";\"Keterangan Kembali\";\"NIP Pengembali\";\"Petugas Pengembali\";\"Tgl & Jam Kembali\"\n"
                            ); 
                            for (int i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbKamIn.getValueAt(i,0).toString()).append("\";\"").append(tbKamIn.getValueAt(i,1).toString()).append("\";\"").append(tbKamIn.getValueAt(i,2).toString()).append("\";\"").append(tbKamIn.getValueAt(i,3).toString()).append("\";\"").append(tbKamIn.getValueAt(i,4).toString()).append("\";\"").append(tbKamIn.getValueAt(i,5).toString()).append("\";\"").append(tbKamIn.getValueAt(i,6).toString()).append("\";\"").append(tbKamIn.getValueAt(i,7).toString()).append("\";\"").append(tbKamIn.getValueAt(i,8).toString()).append("\";\"").append(tbKamIn.getValueAt(i,9).toString()).append("\";\"").append(tbKamIn.getValueAt(i,10).toString()).append("\";\"").append(tbKamIn.getValueAt(i,11).toString()).append("\";\"").append(tbKamIn.getValueAt(i,12).toString()).append("\";\"").append(tbKamIn.getValueAt(i,13).toString()).append("\";\"").append(tbKamIn.getValueAt(i,14).toString()).append("\";\"").append(tbKamIn.getValueAt(i,15).toString()).append(tbKamIn.getValueAt(i,16).toString()).append("\";\"").append(tbKamIn.getValueAt(i,17).toString()).append("\";\"").append(tbKamIn.getValueAt(i,18).toString()).append("\";\"").append(tbKamIn.getValueAt(i,19).toString()).append("\";\"").append(tbKamIn.getValueAt(i,20).toString()).append("\"\n");
                            }
                            f = new File("DataSirkulasiCSSD.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                }   
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnIn);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowInput.dispose();
    }//GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal, NoInventaris);}
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoInventaris.getText().trim().equals("")||nama_barang.getText().trim().equals("")){
            Valid.textKosong(NoInventaris,"Inventaris");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Peminjam");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else {
            if(aksi.equals("ambil")){
                if(!Status.getText().trim().equals("Ada")){
                    JOptionPane.showMessageDialog(null,"Maaf, Barang "+Status.getText()+"...!!!");
                    NoInventaris.requestFocus();
                }else if(Status.getText().trim().equals("Ada")){
                    if(Sequel.menyimpantf("inventaris_ambil_cssd","'"+Keterangan.getText()+"','"+NoSirkulasi.getText()+"','"+NoInventaris.getText()+"','"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19)+"','"+NIP.getText()+"'","No.Sirkulasi")==true){
                        Sequel.queryu("update inventaris set status_barang='Dipinjam' where no_inventaris='"+NoInventaris.getText()+"'");
                        NoInventaris.requestFocus();  
                        emptTeks();
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(inventaris_ambil_cssd.no_sirkulasi,3),signed)),0) from inventaris_ambil_cssd where DATE_FORMAT(inventaris_ambil_cssd.tgl_ambil, '%Y-%m-%d')='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ","AC"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoSirkulasi);
                        tampil();
                    }   
                }
            }else if(aksi.equals("steril")){
                if(Sequel.menyimpantf("inventaris_sterilisasi_cssd","'"+Keterangan.getText()+"','"+NoSirkulasi.getText()+"','"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19)+"','"+NIP.getText()+"'","No.Sirkulasi")==true){
                    tbKamIn.setValueAt(Keterangan.getText(),tbKamIn.getSelectedRow(),13);
                    tbKamIn.setValueAt(NIP.getText(),tbKamIn.getSelectedRow(),14);
                    tbKamIn.setValueAt(NamaPetugas.getText(),tbKamIn.getSelectedRow(),15);
                    tbKamIn.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbKamIn.getSelectedRow(),16);
                    WindowInput.dispose();
                }
            }else if(aksi.equals("kembali")){
                if(Sequel.menyimpantf("inventaris_kembali_cssd","'"+Keterangan.getText()+"','"+NoSirkulasi.getText()+"','"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19)+"','"+NIP.getText()+"'","No.Sirkulasi")==true){
                    tabMode.removeRow(tbKamIn.getSelectedRow());
                    LCount.setText(""+tabMode.getRowCount());
                    Sequel.queryu("update inventaris set status_barang='Ada' where no_inventaris='"+NoInventaris.getText()+"'");
                    WindowInput.dispose();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt,btnPtg,BtnBatal);
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(aksi.equals("ambil")){
            emptTeks();
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(inventaris_ambil_cssd.no_sirkulasi,3),signed)),0) from inventaris_ambil_cssd where DATE_FORMAT(inventaris_ambil_cssd.tgl_ambil, '%Y-%m-%d')='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ","AC"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoSirkulasi);
        }else if(aksi.equals("steril")){
            emptTeks();
            WindowInput.dispose();
        }else if(aksi.equals("kembali")){
            emptTeks();
            WindowInput.dispose();
        }
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void TglPinjam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam1KeyPressed
        Valid.pindah(evt,TCari,TglPinjam2);
}//GEN-LAST:event_TglPinjam1KeyPressed

    private void TglPinjam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam2KeyPressed
        Valid.pindah(evt,TglPinjam1,TCari);
}//GEN-LAST:event_TglPinjam2KeyPressed

    private void tbKamInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamInMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
            
        }
}//GEN-LAST:event_tbKamInMouseClicked

    private void tbKamInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.requestFocus();
            }                    
        }
}//GEN-LAST:event_tbKamInKeyPressed

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    tampil();
}//GEN-LAST:event_formWindowOpened

private void NoInventarisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoInventarisKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        BtnCloseIn.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        isInventaris();
        Keterangan.requestFocus();
    } else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnInvActionPerformed(null);
    }
}//GEN-LAST:event_NoInventarisKeyPressed

private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,NoSirkulasi,btnPtg);
}//GEN-LAST:event_KeteranganKeyPressed

private void btnInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvActionPerformed
    InventarisCariBarangCSSD inventaris=new InventarisCariBarangCSSD(null,false);
    inventaris.addWindowListener(new WindowListener() {
        @Override
        public void windowOpened(WindowEvent e) {}
        @Override
        public void windowClosing(WindowEvent e) {}
        @Override
        public void windowClosed(WindowEvent e) {
            if(inventaris.getTable().getSelectedRow()!= -1){                   
                NoInventaris.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),0).toString());
                nama_barang.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),1).toString()+", "+inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),2).toString());
                Merk.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),4).toString());
                Jenis.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(),8).toString());
                Sequel.cariIsi("select inventaris.status_barang from inventaris where inventaris.no_inventaris='"+NoInventaris.getText()+"'",Status);                       
                NoInventaris.requestFocus();
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

    inventaris.getTable().addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                inventaris.dispose();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {}
    });
    inventaris.isCek();
    inventaris.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    inventaris.setLocationRelativeTo(internalFrame1);
    inventaris.setAlwaysOnTop(false);
    inventaris.setVisible(true);        
}//GEN-LAST:event_btnInvActionPerformed

private void NIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        NamaPetugas.setText(petugas.tampil3(NIP.getText()));
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        NamaPetugas.setText(petugas.tampil3(NIP.getText()));
        NoSirkulasi.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        NamaPetugas.setText(petugas.tampil3(NIP.getText()));
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnPtgActionPerformed(null);
    }
}//GEN-LAST:event_NIPKeyPressed

private void btnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPtgActionPerformed
    petugas.isCek();
    petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    petugas.setLocationRelativeTo(internalFrame1);
    petugas.setAlwaysOnTop(false);
    petugas.setVisible(true);
}//GEN-LAST:event_btnPtgActionPerformed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
    Valid.pindah(evt,btnInv,NoSirkulasi);
}//GEN-LAST:event_TanggalKeyPressed

private void NoSirkulasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSirkulasiKeyPressed
    Valid.pindah(evt,Tanggal,Keterangan);
}//GEN-LAST:event_NoSirkulasiKeyPressed

    private void tbKamInKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }                  
        }
    }//GEN-LAST:event_tbKamInKeyReleased

    private void BtnSterilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSterilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Tabel kosong. Tidak ada data yang bisa Anda edit..!!!!");
            BtnIn.requestFocus();
        }else if(Merk.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data barang inventaris CSSD yang akan diseterilisasi dengan menklik data pada tabel...!!!");
            tbKamIn.requestFocus();
        }else if(! TOut.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, barang inventaris CSSD sudah dikembalikan pada tanggal "+TOut.getText()+"...!!!");
            tbKamIn.requestFocus();
        }else if((! Merk.getText().trim().equals(""))&&(TOut.getText().trim().equals(""))){
            if(tbKamIn.getSelectedRow()>-1){
                if(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),16).toString().equals("")){
                    aksi="steril";
                    NoInventaris.setEditable(false);
                    Sequel.cariIsi("select inventaris.status_barang from inventaris where inventaris.no_inventaris='"+NoInventaris.getText()+"'",Status);
                    NoSirkulasi.setEditable(false);
                    btnInv.setEnabled(false);
                    WindowInput.setAlwaysOnTop(false);
                    WindowInput.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, sudah dilakukan sterilisasi pada tanggal "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),16).toString()+"...!!!");
                }
            }
        }
    }//GEN-LAST:event_BtnSterilActionPerformed

    private void BtnSterilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSterilKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSterilActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnIn,BtnOut);
        }
    }//GEN-LAST:event_BtnSterilKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventarisSirkulasiCSSD dialog = new InventarisSirkulasiCSSD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnIn;
    private widget.Button BtnKeluar;
    private widget.Button BtnOut;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSteril;
    private widget.TextBox Jenis;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LblTgl;
    private widget.Label LblTgl1;
    private widget.editorpane LoadHTML;
    private widget.TextBox Merk;
    private widget.TextBox NIP;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NoInventaris;
    private widget.TextBox NoSirkulasi;
    private javax.swing.JPanel PanelCariUtama;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.ScrollPane Scroll;
    private widget.TextBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TIn;
    private widget.TextBox TOut;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TglPinjam1;
    private widget.Tanggal TglPinjam2;
    private javax.swing.JDialog WindowInput;
    private widget.Button btnInv;
    private widget.Button btnPtg;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel19;
    private widget.Label jLabel22;
    private widget.Label jLabel6;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label6;
    private widget.Label label7;
    private widget.Label label8;
    private widget.TextBox nama_barang;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.Table tbKamIn;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            /*"No.Sirkulasi","No.Inventaris","Kode Barang","Nama Barang","Produsen","Type/Merk","Thn.Produksi",
                "ISBN","Jenis Barang","Keterangan Diambil","NIP Pengambil","Petugas Pengambil","Tgl & Jam Ambil",
                "Keterangan Sterilisasi","NIP Penyeteril","Petugas Penyeteril","Tgl & Jam Starilisasi",
                "Keterangan Kembali","NIP Pengembali","Petugas Pengembali","Tgl & Jam Kembali"*/
            if(R1.isSelected()==true){
                ps=koneksi.prepareStatement(
                       "select inventaris_ambil_cssd.no_sirkulasi,inventaris_ambil_cssd.no_inventaris,inventaris.kode_barang,inventaris_barang.nama_barang,inventaris_produsen.nama_produsen,"+
                       "inventaris_merk.nama_merk,inventaris_barang.thn_produksi,inventaris_barang.isbn,cssd_barang.jenis_barang,inventaris_ambil_cssd.keterangan_ambil,inventaris_ambil_cssd.tgl_ambil,"+
                       "inventaris_ambil_cssd.nip as nippengambil,petugasambil.nama as namapengambil,inventaris_sterilisasi_cssd.keterangan_strerilisasi,ifnull(inventaris_sterilisasi_cssd.tgl_sterilisasi,'') as tgl_sterilisasi,"+
                       "inventaris_sterilisasi_cssd.nip as nippenyeteril,petugaspenyeteril.nama as namapenyeteril from inventaris_ambil_cssd inner join cssd_barang on inventaris_ambil_cssd.no_inventaris=cssd_barang.no_inventaris "+
                       "inner join inventaris on cssd_barang.no_inventaris=inventaris.no_inventaris inner join inventaris_barang on inventaris_barang.kode_barang=inventaris.kode_barang "+
                       "inner join inventaris_produsen on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen inner join inventaris_merk on inventaris_barang.id_merk=inventaris_merk.id_merk "+
                       "inner join inventaris_kategori on inventaris_barang.id_kategori=inventaris_kategori.id_kategori inner join petugas as petugasambil on petugasambil.nip=inventaris_ambil_cssd.nip "+
                       "left join inventaris_sterilisasi_cssd on inventaris_sterilisasi_cssd.no_sirkulasi=inventaris_ambil_cssd.no_sirkulasi left join petugas as petugaspenyeteril on petugaspenyeteril.nip=inventaris_sterilisasi_cssd.nip "+
                       "where inventaris_ambil_cssd.no_sirkulasi not in (select inventaris_kembali_cssd.no_sirkulasi from inventaris_kembali_cssd) "+
                       (TCari.getText().trim().equals("")?"":" and (inventaris_ambil_cssd.no_sirkulasi like ? or inventaris_ambil_cssd.no_inventaris like ? or "+
                       "inventaris.kode_barang like ? or inventaris_barang.nama_barang like ?) ")+"order by inventaris_ambil_cssd.tgl_ambil");
                try {
                    if(!TCari.getText().trim().equals("")){
                        ps.setString(1,"%"+TCari.getText().trim()+"%");
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{
                            rs.getString("no_sirkulasi"),rs.getString("no_inventaris"),rs.getString("kode_barang"),rs.getString("nama_barang"),rs.getString("nama_produsen"),
                            rs.getString("nama_merk"),rs.getString("thn_produksi").substring(0,4),rs.getString("isbn"),rs.getString("jenis_barang"),rs.getString("keterangan_ambil"),
                            rs.getString("nippengambil"),rs.getString("namapengambil"),rs.getString("tgl_ambil"),rs.getString("keterangan_strerilisasi"),rs.getString("nippenyeteril"),
                            rs.getString("namapenyeteril"),rs.getString("tgl_sterilisasi"),"","","",""
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
            }else if(R2.isSelected()==true){
                ps=koneksi.prepareStatement(
                       "select inventaris_ambil_cssd.no_sirkulasi,inventaris_ambil_cssd.no_inventaris,inventaris.kode_barang,inventaris_barang.nama_barang,inventaris_produsen.nama_produsen,"+
                       "inventaris_merk.nama_merk,inventaris_barang.thn_produksi,inventaris_barang.isbn,cssd_barang.jenis_barang,inventaris_ambil_cssd.keterangan_ambil,inventaris_ambil_cssd.tgl_ambil,"+
                       "inventaris_ambil_cssd.nip as nippengambil,petugasambil.nama as namapengambil,inventaris_sterilisasi_cssd.keterangan_strerilisasi,ifnull(inventaris_sterilisasi_cssd.tgl_sterilisasi,'') as tgl_sterilisasi,"+
                       "inventaris_sterilisasi_cssd.nip as nippenyeteril,petugaspenyeteril.nama as namapenyeteril,inventaris_kembali_cssd.keterangan_kembali,ifnull(inventaris_kembali_cssd.tgl_kembali,'') as tgl_kembali,"+
                       "inventaris_kembali_cssd.nip as nippengembali,petugaspengembali.nama as namapengembali from inventaris_ambil_cssd inner join cssd_barang on inventaris_ambil_cssd.no_inventaris=cssd_barang.no_inventaris "+
                       "inner join inventaris on cssd_barang.no_inventaris=inventaris.no_inventaris inner join inventaris_barang on inventaris_barang.kode_barang=inventaris.kode_barang "+
                       "inner join inventaris_produsen on inventaris_barang.kode_produsen=inventaris_produsen.kode_produsen inner join inventaris_merk on inventaris_barang.id_merk=inventaris_merk.id_merk "+
                       "inner join inventaris_kategori on inventaris_barang.id_kategori=inventaris_kategori.id_kategori inner join petugas as petugasambil on petugasambil.nip=inventaris_ambil_cssd.nip "+
                       "left join inventaris_sterilisasi_cssd on inventaris_sterilisasi_cssd.no_sirkulasi=inventaris_ambil_cssd.no_sirkulasi left join petugas as petugaspenyeteril on petugaspenyeteril.nip=inventaris_sterilisasi_cssd.nip "+
                       "left join inventaris_kembali_cssd on inventaris_kembali_cssd.no_sirkulasi=inventaris_ambil_cssd.no_sirkulasi left join petugas as petugaspengembali on petugaspengembali.nip=inventaris_kembali_cssd.nip "+
                       "where inventaris_ambil_cssd.tgl_ambil between ? and ? "+
                       (TCari.getText().trim().equals("")?"":" and (inventaris_ambil_cssd.no_sirkulasi like ? or inventaris_ambil_cssd.no_inventaris like ? or "+
                       "inventaris.kode_barang like ? or inventaris_barang.nama_barang like ?) ")+"order by inventaris_ambil_cssd.tgl_ambil");
                try {
                    ps.setString(1,Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+" 23:59:59");
                    if(!TCari.getText().trim().equals("")){
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{
                            rs.getString("no_sirkulasi"),rs.getString("no_inventaris"),rs.getString("kode_barang"),rs.getString("nama_barang"),rs.getString("nama_produsen"),
                            rs.getString("nama_merk"),rs.getString("thn_produksi").substring(0,4),rs.getString("isbn"),rs.getString("jenis_barang"),rs.getString("keterangan_ambil"),
                            rs.getString("nippengambil"),rs.getString("namapengambil"),rs.getString("tgl_ambil"),rs.getString("keterangan_strerilisasi"),rs.getString("nippenyeteril"),
                            rs.getString("namapenyeteril"),rs.getString("tgl_sterilisasi"),rs.getString("keterangan_kembali"),rs.getString("nippengembali"),rs.getString("namapengembali"),
                            rs.getString("tgl_kembali")
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
            }  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {       
        NoInventaris.setText("");
        nama_barang.setText("");
        Jenis.setText("");
        Merk.setText("");
        Status.setText("");
        Keterangan.setText("");
        NoSirkulasi.setText("");
        Tanggal.setDate(new Date());
        NoInventaris.requestFocus();
    }

    private void getData() {
        TOut.setText("");
        TIn.setText("");
        if(tbKamIn.getSelectedRow()!= -1){
            NoSirkulasi.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString());
            NoInventaris.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),1).toString());
            nama_barang.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),2).toString()+", "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),3).toString());
            Jenis.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString());
            Merk.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString());
            //TIn.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),11).toString());
            TOut.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),20).toString());            
        }
    }

    
    public void isCek(){
        if(akses.getjml2()>=1){
            NIP.setEditable(false);
            btnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.getsirkulasi_cssd());
            BtnIn.setEnabled(akses.getsirkulasi_cssd());
            BtnOut.setEnabled(akses.getsirkulasi_cssd());
            BtnSteril.setEnabled(akses.getsirkulasi_cssd());
            NIP.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
        } 
    }
    
    public void isInventaris(){
        try {
            ps=koneksi.prepareStatement(
               "select inventaris.no_inventaris,inventaris_barang.kode_barang, inventaris_barang.nama_barang, "+
               "inventaris_merk.nama_merk,cssd_barang.jenis_barang,inventaris.status_barang "+
               "from inventaris inner join inventaris_barang inner join cssd_barang inner join inventaris_merk "+
               "on inventaris_barang.id_merk=inventaris_merk.id_merk and inventaris_barang.id_jenis=cssd_barang.id_jenis "+
               "and inventaris_barang.kode_barang=inventaris.kode_barang where inventaris.no_inventaris=?");
            try{
                ps.setString(1,NoInventaris.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    nama_barang.setText(rs.getString("kode_barang")+", "+rs.getString("nama_barang"));
                    Merk.setText(rs.getString("nama_merk"));
                    Jenis.setText(rs.getString("jenis_barang"));
                    Status.setText(rs.getString("status_barang"));
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
        } catch (SQLException ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
}
